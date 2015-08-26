package com.dxytech.demo2;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
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
 * 用户信息管理工具类
 * Created by Administrator on 2015/8/17.
 */
public class UserInfoUtils {

    private static String TGA = "UserInfoUtils";

    public static final String BASE_URL = "http://api.caowei.name/user";

    /**
     *
     * @param userName 用户姓名
     * @param password 密码
     * @param realname 姓名
     * @param phonenumber 电话号码
     * @param email 电子邮箱
     * @return
     * @throws Exception
     */
    public static String post (final String userName,
                               final String password,
                               final String realname,
                               final String phonenumber,
                               final String email) throws Exception{
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String rePost = "";

                try {
                    DefaultHttpClient client = new DefaultHttpClient();
                    org.apache.http.client.methods.HttpPost request = new org.apache.http.client.methods.HttpPost(BASE_URL);

                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("loginname",userName);
                    jsonObj.put("loginpassword",password);
                    jsonObj.put("realname",realname);
                    jsonObj.put("phonenumber",phonenumber);
                    jsonObj.put("email",email);
                    jsonObj.put("roleid",0);

                    Log.d(TGA + "--json",jsonObj.toString() );
                    //设置请求参数
                    request.setHeader("Cookie", "PHPSESSID=" + HttpUtil.PHPSESSID);
                    request.setEntity(new StringEntity(jsonObj.toString()));
                    request.setHeader(HTTP.CONTENT_TYPE, "application/json");

                    //执行请求
                    HttpResponse response = client.execute(request);
                    //获取返回数据
                    rePost = EntityUtils.toString(response.getEntity());

                    Log.d(TGA + "--rePost", rePost);
                    return rePost;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        new Thread(task).start();
        Log.d(TGA, task.get());
        return task.get();
    }

    /**
     * get请求获取用户信息
     * @return 服务器返回的数据
     * @throws Exception
     */
    public static String get()throws Exception{
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String reGet = "";
                try {
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet(BASE_URL + "/" + MainActivity.userName);

                    //设置请求参数(头文件)
                    request.setHeader("Cookie", "PHPSESSID=" + HttpUtil.PHPSESSID);

                    //执行请求
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
        Log.d(TGA, task.get());
        return task.get();
    }

    /**
     * put修改账号信息
     * @param passwordMD5 密码
     * @param realname 姓名
     * @param phonenumber 电话
     * @param email email
     * @return
     */
    public static String put(final String passwordMD5,
                             final String realname,
                             final String phonenumber,
                             final String email) throws Exception {
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() {
                try {
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpPut put = new HttpPut(BASE_URL + "/" + MainActivity.userName);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("loginname", MainActivity.userName);
                    jsonObject.put("loginpassword", passwordMD5);
                    jsonObject.put("realname", realname);
                    jsonObject.put("phonenumber", phonenumber);
                    jsonObject.put("email", email);
                    jsonObject.put("roleid",0);

                    //设置请求参数
                    put.setHeader("Cookie", "PHPSESSID=" + HttpUtil.PHPSESSID);
                    put.setEntity(new StringEntity(jsonObject.toString()));
                    put.setHeader(HTTP.CONTENT_TYPE, "application/json");

                    //执行请求
                    HttpResponse response = client.execute(put);
                    //获取返回数据
                    String rePut = EntityUtils.toString(response.getEntity());
                    Log.d(TGA, rePut);
                    return rePut;
                }catch (Exception e){

                }
                return null;
            }
        });
        new Thread(task).start();
        Log.d(TGA, task.get());
        return task.get();
    }
    /**
     * 注销登录状态
     * @return 服务器返回的数据
     * @throws Exception
     */
    public static String delete()throws Exception{
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String reDelete = "";

                HttpClient client = new DefaultHttpClient();
                HttpDelete delete = new HttpDelete(BASE_URL + "/" + MainActivity.userName);
               delete.setHeader("Cookie", "PHPSESSID=" + HttpUtil.PHPSESSID);

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
        Log.d(TGA, task.get());
        return task.get();
    }
}
