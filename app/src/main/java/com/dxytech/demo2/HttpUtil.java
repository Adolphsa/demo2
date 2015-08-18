package com.dxytech.demo2;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2015/8/17.
 */
public class HttpUtil {

    private static String PHPSESSID;

    public static HttpClient httpClient = new DefaultHttpClient();
    public static final String BASE_URL = "http://api.caowei.name/login";

    /**
     * @param url 发送请求的URL
     * @return 服务器响应的字符
     * @throws Exception
     */
    public static String getRequest (final String url)throws Exception{
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //创建请求对象
                HttpGet get = new HttpGet(url);
                //发送get请求
                HttpResponse response = httpClient.execute(get);
                if (response.getStatusLine().getStatusCode() == 200){
                    //获取服务器响应字符串
                    String result = EntityUtils.toString(response.getEntity());
                    return result;
                }
                return null;
            }
        });
        new Thread(task).start();
        Log.d("HttpUtil", task.get());
        return task.get();
    }

    /**
     * @param url 发送请求的URL
     * @param rawParams 请求参数
     * @return 服务器响应的字符
     * @throws Exception
     */
    public static String postRequest(final String url,final Map<String,String> rawParams)throws Exception{
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //创建HttpPost对象
                HttpPost post = new HttpPost(url);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                for (String key : rawParams.keySet()){
                    //封装请求参数
                    params.add(new BasicNameValuePair(key,rawParams.get(key)));
                }
              //发送post请求
                HttpResponse response = httpClient.execute(post);
                //如果服务器成功返回响应
                if (response.getStatusLine().getStatusCode() == 200){
                    String result = EntityUtils.toString(response.getEntity());
                    return result;
                }
                return null;
            }
        });
        new Thread(task).start();
        Log.d("HttpUtil", task.get());
        return task.get();

    }

    /**
     * POST 登录
     * @return rePost
     */
    public static String postTest (final String userName,final String password) throws Exception{
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String rePost = "";

                try {
                    DefaultHttpClient client = new DefaultHttpClient();
                    org.apache.http.client.methods.HttpPost request = new org.apache.http.client.methods.HttpPost("http://api.caowei.name/login");

                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("username",userName);
                    jsonObj.put("password",password);

                    if(null != PHPSESSID){
                        request.setHeader("Cookie","PHPSESSID=" + PHPSESSID);
                    }

                    request.setEntity(new StringEntity(jsonObj.toString()));
                    request.setHeader(HTTP.CONTENT_TYPE, "application/json");

                    HttpResponse response = client.execute(request);
                    rePost = EntityUtils.toString(response.getEntity());


                    List<Cookie> cookies = client.getCookieStore().getCookies();

                    Log.d("HttpPostTest",cookies.toString() );

                    for (int i=0; i<cookies.size(); i++){
                        if ("PHPSESSID".equals(cookies.get(i).getName())){
                            PHPSESSID = cookies.get(i).getValue();
                            break;
                        }

                    }

                    Log.d("HttpPostTest", rePost);
                    Log.d("HttpPostTest", PHPSESSID);
                    return rePost;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        new Thread(task).start();
        Log.d("HttpUtil", task.get());
        return task.get();


    }
}
