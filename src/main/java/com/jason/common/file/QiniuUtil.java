package com.jason.common.file;

import cn.hutool.core.util.CharUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jason.common.result.JasonResult;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.UrlSafeBase64;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.FileInputStream;


/**
 * @author: xieyong
 * @date: 2018/8/29 16:10
 * @Description: file工具类-上传七牛云m
 */

public class QiniuUtil {


    public static String uploadSingleFile(byte[] base64, String key) {
        //————http上传,指定zone的具体区域——
        //Zone.zone0:华东
        //Zone.zone1:华北
        //Zone.zone2:华南
        //Zone.zoneNa0:北美
        //———http上传，自动识别上传区域——
        //Zone.httpAutoZone
        //———https上传，自动识别上传区域——
        //Zone.httpsAutoZone
        String upToken = getUpToken();
        try {
            String file64 = Base64.encodeToString(base64, 0);
            Integer l = base64.length;
            String url = "http://upload.qiniu.com/putb64/" + l + "/key/" + UrlSafeBase64.encodeToString(key);
            //非华东空间需要根据注意事项 1 修改上传域名
            RequestBody rb = RequestBody.create(null, file64);
            Request request = new Request.Builder().
                    url(url).
                    addHeader("Content-Type", "application/octet-stream")
                    .addHeader("Authorization", "UpToken " + upToken)
                    .post(rb).build();
            OkHttpClient client = new OkHttpClient();
            okhttp3.Response response = client.newCall(request).execute();
            System.out.println(JSONObject.toJSON(response));
            //            http://pe82xhy10.bkt.clouddn.com/x
        } catch (Exception e) {
            e.printStackTrace();
        }
        return QiniuProperties.QINIU_FILEPATH + key;
    }

    /**
     *   FileInputStream inputStream = (FileInputStream) file.getInputStream();
     * 将图片上传到七牛云
     */
    public static JasonResult uploadQNImg(FileInputStream file, String key) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传

        try {
            String upToken = getUpToken();
            try {
                Response response = uploadManager.put(file, key, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                StringBuffer buffer = new StringBuffer();
                buffer.append(QiniuProperties.QINIU_FILEPATH);
                buffer.append(CharUtil.SLASH);
                buffer.append(putRet.key);

                return JasonResult.ok(buffer.toString());
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return JasonResult.error("文件上传失败！");
    }

    /**
     * 获取上传凭证
     *
     * @return
     */
    public static String getUpToken() {
        Auth auth = Auth.create(QiniuProperties.QINIU_ACCESSKEY, QiniuProperties.QINIU_SECRETKEY);
        String upToken = auth.uploadToken(QiniuProperties.QINIU_FILEPATH);
        return upToken;
    }

}
