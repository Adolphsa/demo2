package com.dxytech.demo2;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/18.
 */
public class SharePreference {
    /**
     * 保存用户信息
     * @param context 上下文
     * @param userName 用户名
     * @param password 密码
     * @return 真假
     */
    public static Boolean saveUserInfo(Context context,String userName,String password){
       try{
           SharedPreferences preferences = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
           //获得一个编辑对象
           SharedPreferences.Editor editor = preferences.edit();
           //存数据
           editor.putString("userName",userName);
           editor.putString("password", password);
           //提交
           editor.commit();

           return true;
       }catch (Exception e){
           e.printStackTrace();
       }
        return false;
    }

    /**
     * 获取用户信息
     * @param context 上下文
     * @return 用户信息
     */
    public static Map<String,String> getUserInfo(Context context){
        SharedPreferences preferences = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        String userName  = preferences.getString("userName", null);
        String password = preferences.getString("password",null);

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)){
            Map<String,String> userInfoMap = new HashMap<String,String>();
            userInfoMap.put("userName",userName);
            userInfoMap.put("password",password);

            return userInfoMap;
        }
        return null;
    }
}
