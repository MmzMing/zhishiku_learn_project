package com.whm.media.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 视频文件处理工具类，提供：
 * 1、查看视频时长
 * 2、校验两个视频的时长是否相等
 *
 * @author 吴华明
 * @since 2025-12-05 12:26
 */
@Slf4j
public class VideoUtil {

    private static final int MAX_RETRY_COUNT = 600;
    private static final int RETRY_INTERVAL_MS = 1000;
    private static final String ERROR_RESULT = "error";
    private static final Pattern DURATION_PATTERN = Pattern.compile("Duration:\\s*(\\d{2}:\\d{2}:\\d{2}\\.\\d+)");

    private final String ffmpegPath;

    public VideoUtil(String ffmpegPath) {
        this.ffmpegPath = ffmpegPath;
    }

    /**
     * 检查两个视频的时长是否一致（忽略毫秒）
     *
     * @param source 源视频路径
     * @param target 目标视频路径
     * @return 时长是否一致
     */
    public boolean checkVideoTime(String source, String target) {
        String sourceTime = getVideoTime(source);
        String targetTime = getVideoTime(target);

        if (sourceTime == null || targetTime == null) {
            return false;
        }

        // 取出时分秒（去除毫秒部分）
        String sourceHms = extractHms(sourceTime);
        String targetHms = extractHms(targetTime);

        return sourceHms != null && sourceHms.equals(targetHms);
    }

    /**
     * 提取时分秒部分（去除毫秒）
     */
    private String extractHms(String time) {
        int dotIndex = time.lastIndexOf('.');
        return dotIndex > 0 ? time.substring(0, dotIndex) : time;
    }

    /**
     * 获取视频时长（格式：时:分:秒.毫秒）
     *
     * @param videoPath 视频文件路径
     * @return 视频时长字符串，获取失败返回null
     */
    public String getVideoTime(String videoPath) {
        List<String> command = Arrays.asList(ffmpegPath, "-i", videoPath);

        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            String output = waitForProcess(process);
            log.debug("FFmpeg output: {}", output);

            return parseDuration(output);
        } catch (Exception e) {
            log.error("获取视频时长失败: {}", videoPath, e);
            return null;
        }
    }

    /**
     * 从FFmpeg输出中解析视频时长
     */
    private String parseDuration(String output) {
        if (output == null) {
            return null;
        }
        Matcher matcher = DURATION_PATTERN.matcher(output);
        return matcher.find() ? matcher.group(1).trim() : null;
    }

    /**
     * 等待进程执行完成并获取输出
     */
    public String waitForProcess(Process process) {
        StringBuilder output = new StringBuilder();

        try (InputStream inputStream = process.getInputStream();
             InputStream errorStream = process.getErrorStream()) {

            int retryCount = 0;
            while (retryCount <= MAX_RETRY_COUNT) {
                // 读取标准输出
                readAvailableBytes(inputStream, output);
                // 读取错误输出
                readAvailableBytes(errorStream, output);

                try {
                    process.exitValue();
                    return output.toString();
                } catch (IllegalThreadStateException e) {
                    Thread.sleep(RETRY_INTERVAL_MS);
                    retryCount++;
                }
            }
            log.warn("进程执行超时");
            return ERROR_RESULT;
        } catch (Exception e) {
            log.error("等待进程执行异常", e);
            return ERROR_RESULT;
        }
    }

    /**
     * 读取输入流中可用的字节
     */
    private void readAvailableBytes(InputStream stream, StringBuilder output) throws IOException {
        while (stream.available() > 0) {
            output.append((char) stream.read());
        }
    }
}
