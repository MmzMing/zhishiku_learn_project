package com.whm.media.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FFmpeg视频转码工具类
 * 提供视频编码转换为MP4格式的功能
 *
 * @author 吴华明
 * @since 2025-12-05 12:25
 */
@Slf4j
public class FfmpegVideoUtil extends VideoUtil {

    private static final String SUCCESS = "success";
    private static final String VIDEO_CODEC = "libx264";
    private static final String PIXEL_FORMAT = "yuv420p";
    private static final String DEFAULT_RESOLUTION = "1280x720";
    private static final String DEFAULT_AUDIO_BITRATE = "63k";
    private static final String DEFAULT_VIDEO_BITRATE = "753k";
    private static final String DEFAULT_FRAME_RATE = "18";

    private final String ffmpegPath;
    private final String videoPath;
    private final String outputPath;

    public FfmpegVideoUtil(String ffmpegPath, String videoPath, String mp4Name, String outputPath) {
        super(ffmpegPath);
        this.ffmpegPath = ffmpegPath;
        this.videoPath = videoPath;
        this.outputPath = outputPath;
    }

    /**
     * 视频编码，生成MP4文件
     *
     * @return 成功返回 "success"，失败返回FFmpeg输出日志
     */
    public String generateMp4() {
        clearExistingFile(outputPath);

        List<String> command = buildFfmpegCommand();
        String output = executeCommand(command);

        boolean timeMatched = checkVideoTime(videoPath, outputPath);
        return timeMatched ? SUCCESS : output;
    }

    /**
     * 构建FFmpeg转码命令
     */
    private List<String> buildFfmpegCommand() {
        return new ArrayList<>(Arrays.asList(
                ffmpegPath,
                "-i", videoPath,
                "-c:v", VIDEO_CODEC,
                "-y",
                "-s", DEFAULT_RESOLUTION,
                "-pix_fmt", PIXEL_FORMAT,
                "-b:a", DEFAULT_AUDIO_BITRATE,
                "-b:v", DEFAULT_VIDEO_BITRATE,
                "-r", DEFAULT_FRAME_RATE,
                outputPath
        ));
    }

    /**
     * 执行FFmpeg命令
     */
    private String executeCommand(List<String> command) {
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            return waitForProcess(process);
        } catch (Exception e) {
            log.error("执行FFmpeg命令失败", e);
            return e.getMessage();
        }
    }

    /**
     * 清除已存在的输出文件
     */
    private void clearExistingFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile() && file.delete()) {
            log.debug("已删除旧文件: {}", filePath);
        }
    }
}
