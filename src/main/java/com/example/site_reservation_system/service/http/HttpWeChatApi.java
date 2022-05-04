package com.example.site_reservation_system.service.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.site_reservation_system.config.WeChatConfig;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

@Service
public class HttpWeChatApi {

    @Autowired
    private HttpClientService httpClientService;
    @Autowired
    private WeChatConfig weChatConfig;

    public JSONObject getSessionKeyOrOpenId(String code) throws IOException, URISyntaxException {
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap<String, String> requestUrlParam = new HashMap<>();
        requestUrlParam.put("appid", weChatConfig.getAppid());
        requestUrlParam.put("secret", weChatConfig.getSecret());
        requestUrlParam.put("js_code", code);
        requestUrlParam.put("grant_type", "authorization_code");
        String result = httpClientService.doGet(requestUrl, requestUrlParam);
        return JSON.parseObject(result);
    }

    public JSONObject getAccessToken() throws IOException, URISyntaxException {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token";
        HashMap<String, String> requestUrlParam = new HashMap<>();
        requestUrlParam.put("appid", weChatConfig.getAppid());
        requestUrlParam.put("secret", weChatConfig.getSecret());
        requestUrlParam.put("grant_type", "client_credential");
        String result = httpClientService.doGet(requestUrl, requestUrlParam);
        return JSON.parseObject(result);
    }

    public JSONObject getUserPhoneNumber(String code) throws IOException, URISyntaxException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost request = new HttpPost("https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + getAccessToken().getString("access_token"));
        request.addHeader("Content-Type", "application/json");
        JsonObject json = new JsonObject();
        json.addProperty("code", code);
        request.setEntity(new StringEntity(json.toString(), "utf-8"));
        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        return JSON.parseObject(EntityUtils.toString(entity, "UTF-8"));
    }
}