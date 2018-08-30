package com.jason.common.file;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: xieyong
 * @date: 2018/8/29 16:57
 * @Description: 配置获取
 */
@Component
public class QiniuProperties implements InitializingBean {

    @Value("${qiniu.accessKey}")
    private static String accessKey;

    @Value("${qiniu.secretKey}")
    private static String secretKey;

    @Value("${qiniu.nameSpace}")
    private static String nameSpace;

    @Value("${qiniu.filePath}")
    private static String filePath;

    public static String QINIU_ACCESSKEY;

    public static String QINIU_SECRETKEY;

    public static String QINIU_NAMESPACE;

    public static String QINIU_FILEPATH;

    @Override
    public void afterPropertiesSet() throws Exception {
        QINIU_ACCESSKEY = accessKey;

        QINIU_FILEPATH = filePath;

        QINIU_NAMESPACE = nameSpace;

        QINIU_SECRETKEY = secretKey;
    }

    public static String getAccessKey() {
        return accessKey;
    }

    public static void setAccessKey(String accessKey) {
        QiniuProperties.accessKey = accessKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public static void setSecretKey(String secretKey) {
        QiniuProperties.secretKey = secretKey;
    }

    public static String getNameSpace() {
        return nameSpace;
    }

    public static void setNameSpace(String nameSpace) {
        QiniuProperties.nameSpace = nameSpace;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        QiniuProperties.filePath = filePath;
    }
}
