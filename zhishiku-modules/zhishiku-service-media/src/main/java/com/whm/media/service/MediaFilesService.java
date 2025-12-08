package com.whm.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.media.domain.po.MediaFiles;
import org.springframework.web.multipart.MultipartFile;


/**
 * 媒资信息 表服务接口
 *
 * @author : 吴华明
 * @since : 2025-09-16 18:46:04
 */
public interface MediaFilesService extends IService<MediaFiles> {


    /**
     * 上传文件
     *
     * @param file       文件信息
     * @param objectName 存储路径
     * @return Boolean
     */
    Boolean uploadFile(MultipartFile file, String objectName);

    /**
     * 上传分块
     *
     * @param file       文件信息
     * @param fileMd5    文件md5
     * @param chunkIndex 分块序号
     * @return Boolean
     */
    Boolean uploadChunks(MultipartFile file, String fileMd5, int chunkIndex);

    /**
     * 合并分块
     *
     * @param fileMd5    文件md5
     * @param fileName   文件名称
     * @param chunkTotal 分块总和
     * @return Boolean
     */
    Boolean mergeChunks(String fileMd5, String fileName, int chunkTotal);

    /**
     * 校验文件是否存在
     *
     * @param filesMd5 文件md5
     * @return Boolean
     */
    Boolean checkFiles(String filesMd5);

    /**
     * 校验分块是否存在
     *
     * @param fileMd5    文件md5
     * @param chunkIndex 分块序号
     * @return Boolean
     */
    Boolean checkChunk(String fileMd5, int chunkIndex);
    /**
     * 根据本地文件路径上传到MinIO存储服务
     *
     * @param localFilePath 本地文件路径
     * @param mimeType 文件的媒体类型
     * @param bucket MinIO存储桶名称
     * @param objectName 存储对象名称（可包含子目录路径）
     * @return 上传成功返回true，失败返回false
     */
    void addFilesForPathToMinIo(String localFilePath,String mimeType,String bucket, String objectName);
    /**
     * 保存到数据库
     *
     * @param mediaFiles 媒体文件信息
     * @param fileMd5    文件md5
     * @param fileName   文件名称
     * @param fileSize   文件大小
     * @param bucket     桶
     * @param objectName 对象名称
     * @param extension  扩展名
     * @param mimeType   媒体类型
     * @return Boolean
     */
    Boolean addMediaFilesToDb(MediaFiles mediaFiles, String fileMd5, String fileName, long fileSize, String bucket, String objectName, String extension, String mimeType);
}
