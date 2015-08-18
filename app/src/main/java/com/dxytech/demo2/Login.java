//package com.dxytech.demo2;
//
//import android.app.Activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//
//
//
//import org.apache.http.client.CookieStore;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
///**
// * Created by Administrator on 2015/8/17.
// */
//public class Login extends Activity {
//
//    private String userName;
//    private String password;
//
//    private EditText edt_account;
//    private EditText edt_password;
//    private Button btn_Login;
//
//    public CookieStore cookieStore= null;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        edt_account = (EditText)findViewById(R.id.edt_account);
//        edt_password = (EditText)findViewById(R.id.edt_password);
//        btn_Login = (Button)findViewById(R.id.btn_Login);
//        btn_Login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("Login","账号或密码错误");
//                //执行输入校验
//                if (validate()){
//                    //登录成功
//                    if (loginPro()){
//                        //启动到用户界面
//                        Intent intent = new Intent();
//                        intent.setClass(getApplication(),MainMenuActivity.class);
//                        startActivity(intent);
//                        //结束该activity
//                        finish();
//                    }else{
//                        Log.d("Login","账号或密码错误");
//                    }
//                }
//            }
//        });
//    }
//
//    private boolean loginPro(){
//        //获取用户输入的账号和密码
//        userName = edt_account.getText().toString();
//        password = edt_password.getText().toString();
//        JSONObject jsonObj;
//
//        try {
//            jsonObj = query(userName,password);
//            if (jsonObj.getInt("userId") > 0){
//                return true;
//            }
//        } catch (Exception e) {
//            Log.d("Login","服务器响应异常");
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    private boolean validate(){
//        userName = edt_account.getText().toString().trim();
//        if (userName.equals("")){
//            Log.d("Login","账号是必填项");
//            return false;
//        }
//        password = edt_password.getText().toString().trim();
//        if (password.equals("")){
//            Log.d("Login","密码是必填项");
//            return false;
//        }
//        return true;
//    }
//
//    //定义发送请求的方法
//    private JSONObject query(String userName,String password)throws Exception{
//        Map<String,String> map = new HashMap<String,String>();
//        map.put("user",userName);
//        map.put("password",password);
//        String url = HttpUtil.BASE_URL;
//        return new JSONObject(HttpUtil.postRequest(url,map));
//    }
//
//}
