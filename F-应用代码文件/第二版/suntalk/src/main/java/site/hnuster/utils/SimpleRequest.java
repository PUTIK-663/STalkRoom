package site.hnuster.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 封装的网络请求请求工具类
 */
public class SimpleRequest {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36";
    private SimpleRequest(){};
    /**
     * 发送get请求
     * @param url 请求的目标地址
     * @param cookie 携带的cookie
     * @return HttpResponse:响应对象response
     * */
    public static HttpResponse get(String url, String cookie) throws Exception{
        BasicCookieStore cookieStore = new BasicCookieStore();
        RequestConfig defaultConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        HttpClient client = HttpClients.custom().setDefaultRequestConfig(defaultConfig)
                .setDefaultCookieStore(cookieStore).build();

        HttpGet httpGet = new HttpGet(url);
        //httpGet.setConfig(RequestConfig.custom().setRedirectsEnabled(redirectsEnabled).build());
        httpGet.addHeader("connection","keep-alive");
        httpGet.addHeader("Content-Type","application/x-www-form-urlencoded");
        httpGet.addHeader("charset","UTF-8");
        httpGet.addHeader("User-Agent", USER_AGENT);
        httpGet.addHeader("Cookie",cookie);
        HttpResponse resp = null;
        resp = client.execute(httpGet);
        return resp;
    }

    /**
     * 发送post请求
     * @param url 请求的目标地址
     * @param body 携带的参数
     * @param cookie 携带的cookie
     * @param host host地址
     * @return HttpResponse:响应对象response
     */
    public static HttpResponse post(String url, String body, String cookie,String host) throws Exception{
        StringEntity entityBody = new StringEntity(body,"UTF-8");
        RequestConfig defaultConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        HttpClient client = HttpClients.custom().setDefaultRequestConfig(defaultConfig).build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("connection","keep-alive");
        httpPost.addHeader("Host",host);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
        httpPost.addHeader("charset","UTF-8");
        httpPost.addHeader("User-Agent", USER_AGENT);
        httpPost.addHeader("Cookie",cookie);
        httpPost.setEntity(entityBody);
        HttpResponse resp = null;
        resp = client.execute(httpPost);
        //校验响应码
        //if(resp.getStatusLine().getStatusCode()<400){
        //    entity = resp.getEntity();
        //} else{
        //    entity = resp.getEntity();
        //}
        return resp;
    }

    /**
     * 将响应内容转换为JSON对象（如果可以的话）
     */
    public static JSONObject toJSON(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        String respContent = EntityUtils.toString(entity, "UTF-8");
        //查询当前URI
        //HttpUriRequest realRequest = (HttpUriRequest) httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
        //System.out.println("当前网络地址："+realRequest.getURI());
        return JSON.parseObject(respContent);
    }

}
