package com.whm.media.utils;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 文件类型枚举
 * 提供文件后缀名与文件类型的映射关系
 *
 * @author 吴华明
 * @since 2025/10/1 19:50
 */
@Getter
public enum FileType implements BaseEnumType {

    Image("图片", "bmp,dib,gif,jfif,jpe,jpeg,jpg,png,tif,tiff,ico,psd,svg,webp"),
    Audio("音频", "mp3,ogg,wav,ape,cda,au,midi,mac,aac,flac,wma"),
    ZipFile("压缩包", "rar,zip,cab,arj,7z,tar,gz,gzip,jar,iso,z,uue,ace,lzh,bz2,bz,xz"),
    Document("文档", "pptx,docx,xlsx,doc,wps,xls,ppt,txt,sql,htm,html,pdf,dwg,md,json,xml,csv"),
    Video("视频", "wmv,asf,asx,rm,rmvb,mpg,mpeg,mpe,3gp,mov,mp4,m4v,avi,dat,mkv,flv,vob,swf,webm,ts"),
    File("文件", "");

    private static final Map<String, FileType> SUFFIX_LOOKUP_MAP;

    static {
        Map<String, FileType> lookupMap = new HashMap<>(128);
        for (FileType type : values()) {
            type.suffixSet.forEach(suffix -> lookupMap.put(suffix, type));
        }
        SUFFIX_LOOKUP_MAP = Collections.unmodifiableMap(lookupMap);
    }

    private final String name;
    private final Set<String> suffixSet;

    FileType(String name, String suffixStr) {
        this.name = name;
        this.suffixSet = (suffixStr == null || suffixStr.isEmpty())
                ? Collections.emptySet()
                : Collections.unmodifiableSet(
                Arrays.stream(suffixStr.split(","))
                        .map(String::trim)
                        .collect(Collectors.toSet()));
    }

    /**
     * 根据文件后缀名获取文件类型
     *
     * @param suffixName 文件后缀名（可带点或不带点）
     * @return 对应的文件类型，未找到则返回 {@link #File}
     */
    public static FileType getFileType(String suffixName) {
        if (suffixName == null || suffixName.trim().isEmpty()) {
            return File;
        }
        return SUFFIX_LOOKUP_MAP.getOrDefault(normalizeSuffix(suffixName), File);
    }

    /**
     * 检查指定后缀名是否属于当前文件类型
     *
     * @param suffixName 文件后缀名
     * @return 如果后缀名属于当前类型则返回true
     */
    public boolean matches(String suffixName) {
        if (suffixName == null || suffixName.isEmpty()) {
            return false;
        }
        return suffixSet.contains(normalizeSuffix(suffixName));
    }

    /**
     * 标准化后缀名：移除前导点并转小写
     */
    private static String normalizeSuffix(String suffix) {
        String s = suffix.startsWith(".") ? suffix.substring(1) : suffix;
        return s.toLowerCase(Locale.ROOT);
    }

    /**
     * @deprecated 请使用 {@link #getSuffixSet()} 代替
     */
    @Deprecated
    public Map<String, String> getSuffixMap() {
        return suffixSet.stream().collect(Collectors.toMap(s -> s, s -> s));
    }
}
