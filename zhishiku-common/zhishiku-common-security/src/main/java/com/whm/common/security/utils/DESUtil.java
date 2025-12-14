package com.whm.common.security.utils;


import com.whm.common.core.exception.service.ServiceException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * DES加解密工具类
 *
 * @author : 吴华明
 * @since 2025-12-14  14:35
 */
public class DESUtil {
    /**
     * 加密算法
     */
    private static final String ALGORITHM = "desede";
    /**
     * 加密/解密算法-工作模式-填充模式
     */
    private static final String CIPHER_ALGORITHM = "desede/CBC/PKCS5Padding";


    /**
     * 使用3DES算法对明文进行加密
     *
     * @param plainText 待加密的明文字符串，如果为null则返回null
     * @param secretKey 加密密钥字符串，如果为null则返回null
     * @return 加密后的十六进制字符串形式，加密失败时抛出ServiceException异常
     */
    public static String encrypt(String plainText, String secretKey) {
        try {
            if (plainText == null || secretKey == null) {
                return null;
            }
            // 构造3DES密钥规范
            DESedeKeySpec spec = new DESedeKeySpec(build3DesKey(secretKey));
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(ALGORITHM);
            // 初始化加密器
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher
                    .init(Cipher.ENCRYPT_MODE, keyfactory.generateSecret(spec), new IvParameterSpec(secretKey.getBytes()));
            StringBuffer sb = new StringBuffer();
            // 执行加密操作
            byte[] mdata = cipher.doFinal(plainText.getBytes());
            // 将加密结果转换为十六进制字符串
            for (byte mdatum : mdata) {
                sb.append(String.format("%2s", Integer.toHexString(mdatum & 0xFF).toUpperCase()).replace(' ', '0'));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new ServiceException("加密失败");
        }
    }


    /**
     * 构建3DES加密所需的24字节密钥
     *
     * @param keyStr 原始密钥字符串
     * @return 24字节的3DES密钥数组
     * @throws UnsupportedEncodingException 当字符编码不支持时抛出此异常
     */
    public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes(StandardCharsets.UTF_8);
        //如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
        //如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
        System.arraycopy(temp, 0, key, 0, Math.min(key.length, temp.length));
        return key;
    }


    /**
     * 解密函数
     * 使用DESede算法对密文进行解密操作
     *
     * @param plainText 待解密的密文字符串，以十六进制形式表示
     * @param secretKey 解密使用的密钥字符串
     * @return 解密后的明文字符串
     * @throws Exception 当解密过程中发生错误时抛出异常
     */
    public static String decrypt(String plainText, String secretKey) throws Exception {
        try {
            // 参数校验，空值直接返回null
            if (plainText == null || secretKey == null) {
                return null;
            }

            // 构造DESede密钥规范并初始化解密器
            DESedeKeySpec spec = new DESedeKeySpec(build3DesKey(secretKey));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(secretKey.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyFactory.generateSecret(spec), iv);

            // 执行解密操作并返回结果
            return new String(cipher.doFinal(hex2byte(plainText)));
        } catch (Exception e) {
            throw new ServiceException("解密失败");
        }

    }


    /**
     * 将十六进制字符串转换为字节数组
     *
     * @param hexStr 十六进制字符串，不能为null
     * @return 转换后的字节数组，如果输入无效则返回null
     */
    public static byte[] hex2byte(String hexStr) {
        if (hexStr == null) {
            return null;
        }
        hexStr = hexStr.trim();
        int len = hexStr.length();
        // 检查字符串长度是否有效（非空且为偶数）
        if (len == 0 || len % 2 == 1) {
            return null;
        }
        byte[] digest = new byte[len / 2];
        try {
            // 每两个字符转换为一个字节
            for (int i = 0; i < hexStr.length(); i += 2) {
                digest[i / 2] = (byte) Integer.decode("0x" + hexStr.substring(i, i + 2)).intValue();
            }
            return digest;
        } catch (Exception e) {
            return null;
        }
    }


        /**
     * 验证原始密码与加密后密码是否匹配
     *
     * @param rawPassword 原始密码字符串
     * @param encodedPassword 已加密的密码字符串
     * @return 如果原始密码加密后与已加密密码匹配则返回true，否则返回false
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        return encodedPassword.equals(encrypt(rawPassword, "lsAa2020"));
    }


}
