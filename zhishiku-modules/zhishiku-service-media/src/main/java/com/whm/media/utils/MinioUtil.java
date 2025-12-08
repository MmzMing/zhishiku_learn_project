package com.whm.media.utils;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.whm.common.core.exception.service.ServiceException;
import com.whm.common.core.utils.DateUtils;
import com.whm.common.core.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * MinIO工具类
 * 提供文件上传相关的工具方法，包括文件类型识别、MD5计算、文件路径生成等
 *
 * @author 吴华明
 * @since 2025/9/16
 */
@Slf4j
public class MinioUtil {

    private static final String DEFAULT_PATH = "unknown";
    private static final String CHUNK_DIR = "chunk";
    private static final Map<FileType, String> FILE_TYPE_PATH_MAP;

    static {
        FILE_TYPE_PATH_MAP = new EnumMap<>(FileType.class);
        FILE_TYPE_PATH_MAP.put(FileType.Image, "image");
        FILE_TYPE_PATH_MAP.put(FileType.Video, "video");
        FILE_TYPE_PATH_MAP.put(FileType.Audio, "audio");
        FILE_TYPE_PATH_MAP.put(FileType.Document, "doc");
        FILE_TYPE_PATH_MAP.put(FileType.ZipFile, "archive");
        FILE_TYPE_PATH_MAP.put(FileType.File, DEFAULT_PATH);
    }

    private MinioUtil() {
    }

    /**
     * 文件路径信息封装类
     */
    @Getter
    @AllArgsConstructor
    public static class FilePathInfo {
        /**
         * 文件存储路径
         */
        private final String path;
        /**
         * 文件类型分类
         */
        private final String fileType;
    }


    /**
     * 根据文件名生成随机的文件存储路径
     * 文件存储在指定目录下，文件名为文件的md5值加上扩展名
     *
     * @param fileName 文件名
     * @return 随机生成的文件存储路径信息
     */
    public static FilePathInfo getRandomFilePathByName(String fileName, String fileMd5) {
        String extension = getFileExtension(fileName);
        FileType type = FileType.getFileType(extension);
        String pathCategory = FILE_TYPE_PATH_MAP.getOrDefault(type, DEFAULT_PATH);
        // 子目录 + 日期 + 文件的md5值 + 扩展名
        String fullPath = buildPath(pathCategory, DateUtils.datePath(), fileMd5 + "." + extension);
        return new FilePathInfo(fullPath, pathCategory);
    }


    /**
     * 从文件名中提取文件扩展名
     *
     * @param fileName 文件名
     * @return 文件扩展名，如果没有扩展名则返回空字符串
     */
    private static String getFileExtension(String fileName) {
        return Optional.ofNullable(fileName)
                .filter(name -> name.contains("."))
                .map(name -> name.substring(name.lastIndexOf('.') + 1).toLowerCase(Locale.ROOT))
                .orElse("");
    }

    /**
     * 根据文件扩展名获取对应的MIME类型
     *
     * @param extension 文件扩展名，例如 "jpg"、"png" 等
     * @return 返回对应的MIME类型字符串，如果找不到匹配的MIME类型则返回默认的字节流类型
     */
    public static String getMimeType(String extension) {
        return Optional.ofNullable(extension)
                .map(ContentInfoUtil::findExtensionMatch)
                .map(ContentInfo::getMimeType)
                .orElse(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    /**
     * 获取文件的MD5值
     *
     * @param file MultipartFile对象
     * @return 文件的MD5值
     */
    public static String getFileMd5(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            return DigestUtils.md5Hex(inputStream);
        } catch (Exception e) {
            log.error("获取文件MD5值异常", e);
            throw new ServiceException(StringUtils.format("获取文件MD5值异常：{}", e.getMessage()));
        }
    }

    /**
     * 根据文件MD5值和扩展名生成文件存储路径
     * 采用两级目录结构，第一级为MD5值的第1个字符，第二级为MD5值的第2个字符
     * 文件存储在二级目录下，文件名为MD5值加上扩展名
     *
     * @param fileMd5 文件的MD5值，用于构建目录结构
     * @param fileExt 文件扩展名，包含点号（如".jpg"）
     * @return 返回基于MD5的文件存储路径，格式为：第一个字符/第二个字符/完整MD5/完整MD5+扩展名
     */
    public static String getChunkFilePathByMd5(String fileMd5, String fileExt) {
        return buildPath(
                fileMd5.substring(0, 1),
                fileMd5.substring(1, 2),
                fileMd5,
                fileMd5 + fileExt
        );
    }

    /**
     * 获取分块文件的存储目录路径
     * 根据文件MD5值生成多级目录结构，用于存储文件分块
     * 目录结构格式：第一个字符/第二个字符/完整MD5值/chunk/
     *
     * @param fileMd5 文件的MD5值，用于生成唯一的目录路径
     * @return 返回分块文件的目录路径字符串
     */
    public static String getChunkFilePath(String fileMd5) {
        return buildPath(
                fileMd5.substring(0, 1),
                fileMd5.substring(1, 2),
                fileMd5,
                CHUNK_DIR
        ) + "/";
    }

    private static String buildPath(String... parts) {
        return Paths.get("", parts).toString().replace("\\", "/");
    }
}