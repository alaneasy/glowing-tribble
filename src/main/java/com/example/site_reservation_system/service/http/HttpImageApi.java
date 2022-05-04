package com.example.site_reservation_system.service.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/*
 * Created By Alan on 2021/12/20
 * */
@Service
public class HttpImageApi {

    @Autowired
    private HttpClientService httpClientService;
    @Autowired
    private HttpWeChatApi httpWeChatApi;

    public boolean checkImg(MultipartFile multipartFile) {
        String accessToken = null;
        try {
            accessToken = httpWeChatApi.getAccessToken().getString("access_token");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        String url = "https://api.weixin.qq.com/wxa/img_sec_check?access_token=" + accessToken;
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);
        request.addHeader("Content-Type", "application/octet-stream");
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert inputStream != null;
            byte[] byt = new byte[inputStream.available()];
            inputStream.read(byt);
            request.setEntity(new ByteArrayEntity(byt, ContentType.create("image/jpg")));
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            JSONObject result = JSON.parseObject(EntityUtils.toString(entity, "UTF-8"));
            return result.getString("errcode").equals("1");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert inputStream != null;
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}