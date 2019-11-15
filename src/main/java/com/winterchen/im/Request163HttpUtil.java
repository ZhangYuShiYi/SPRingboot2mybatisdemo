package com.winterchen.im;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Request163HttpUtil {
    /**
     * @param url
     * @param nvps//请求参数
     * APP内唯一
     * @return
     */
    public static HashMap<Object,Object> httpRequest(String url, List<NameValuePair> nvps) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        ResourceBundle resource = ResourceBundle.getBundle("config/wangyiyunxin");
        String appKey = resource.getString("AppKey");
        String appSecret = resource.getString("AppSecret");
        String nonce = UUID.randomUUID().toString();
        String curTime = String.valueOf((new Date()).getTime()/1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HashMap hm = new HashMap<String,Object>();
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            // 执行请求
            HttpResponse response = httpClient.execute(httpPost);
            // 执行结果
            String entity = EntityUtils.toString(response.getEntity(), "utf-8");
            Map<String, Object> map=JSON.parseObject(entity, Map.class);
            hm.put("result",entity);
            hm.put("code",map.get("code"));
            hm.put("info",map.get("info"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return hm;
    }
}
