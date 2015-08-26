package com.dxytech.demo2;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 登录工具类
 * Created by Administrator on 2015/8/17.
 */
public class HttpUtil {

    private static String TGA = "HttpUtil";

    public static String PHPSESSID;

    public static final String BASE_URL = "http://api.caowei.name/login";

    /**
     * POST 登录
     * @return rePost
     */
    public static String post (final String userName,final String password) throws Exception{
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String rePost = "";

                try {
                    DefaultHttpClient client = new DefaultHttpClient();
                    org.apache.http.client.methods.HttpPost request = new org.apache.http.client.methods.HttpPost(BASE_URL);

                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("username",userName);
                    jsonObj.put("password",password);

//                    if(null != PHPSESSID){
//                        request.setHeader("Cookie","PHPSESSID=" + PHPSESSID);
//                    }

                    //设置请求参数
                    request.setEntity(new StringEntity(jsonObj.toString()));
                    request.setHeader(HTTP.CONTENT_TYPE, "application/json");

                    //执行请求
                    HttpResponse response = client.execute(request);
                    //获取返回数据
                    rePost = EntityUtils.toString(response.getEntity());


                    List<Cookie> cookies = client.getCookieStore().getCookies();

                    Log.d(TGA + "--cookies",cookies.toString() );

                    for (int i=0; i<cookies.size(); i++){
                        if ("PHPSESSID".equals(cookies.get(i).getName())){
                            PHPSESSID = cookies.get(i).getValue();
                            break;
                        }

                    }
                    Log.d(TGA + "--rePost", rePost);
                    Log.d(TGA + "--PHPSESSID", PHPSESSID);
                    return rePost;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        new Thread(task).start();
        Log.d(TGA + "--taskPost", task.get());
        return task.get();
    }

    /**
     * get请求获取登录的状态
     * @return 服务器返回的数据
     * @throws Exception
     */
    public static String get()throws Exception{
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String reGet = "";

                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet(BASE_URL + "/" + MainActivity.userName);
                request.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);

                try {
                    HttpResponse response = client.execute(request);

                    reGet = EntityUtils.toString(response.getEntity());
                    Log.d(TGA + "--reGet",reGet);

                    List<Cookie> cookies = client.getCookieStore().getCookies();
                    if (cookies != null){
                        Log.d(TGA + "--cookies",cookies.toString());
                    }else{
                        Log.d(TGA + "--cookies","cookies为空");
                    }
                    return reGet;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        new Thread(task).start();
        Log.d(TGA + "--taskGet", task.get());
        return task.get();
    }

    /**
     * 注销登录状态
     * @return 服务器返回的数据
     * @throws Exception
     */
    public static String delete(final String username)throws Exception{
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String reDelete = "";

                HttpClient client = new DefaultHttpClient();
                HttpDelete delete = new HttpDelete(BASE_URL + "/" + username);
                delete.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);

                try {
                    HttpResponse request = client.execute(delete);

                    reDelete = EntityUtils.toString(request.getEntity());
                    Log.d(TGA + "--reDelete", reDelete);
                    return reDelete;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        new Thread(task).start();
        Log.d(TGA + "--taskDelete", task.get());
        return task.get();
    }
}
