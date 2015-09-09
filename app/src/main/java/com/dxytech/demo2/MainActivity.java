package com.dxytech.demo2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.igexin.sdk.PushManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//登录界面
public class MainActivity extends Activity {

    private String TGA = "MainActivity";

    public  static String userName;
    private  String password;
    private  String passwordMD5;

    private EditText edt_account;
    private EditText edt_password;

    private TextView textView_register; //立即注册
    private TextView textView_password; //找回密码

    private Button btn_demoLogin; //演示登录
    private Button btn_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //个推
        PushManager.getInstance().initialize(this.getApplicationContext());

        Logout_Manager.getInstance().addActivity(this);

        Listener listener= new Listener();

        //注册和忘记密码
        textView_register = (TextView)findViewById(R.id.textView_register);
        textView_password = (TextView)findViewById(R.id.textView_password);
        textView_register.setOnClickListener(listener);
        textView_password.setOnClickListener(listener);

        //账号和密码
        edt_account = (EditText)findViewById(R.id.edt_account);
        edt_password = (EditText)findViewById(R.id.edt_password);

        //演示登录和登录
        btn_demoLogin = (Button)findViewById(R.id.btn_demoLogin);
        btn_Login = (Button)findViewById(R.id.btn_Login);
        btn_demoLogin.setOnClickListener(listener);
        btn_Login.setOnClickListener(listener);

        //自动填充账号密码
        SharedPreferences preferences =getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        Boolean b = preferences.getBoolean("isFirst",false);
        Log.d("第一次登录",b.toString());
        if (preferences.getBoolean("isFirst",false)) {
            Map<String, String> userInfo = new HashMap<String, String>();
            userInfo = SharePreference.getUserInfo(getApplicationContext());

            edt_account.setText(userInfo.get("userName"));
            Log.d(TGA + "--userName", userInfo.get("userName"));
            edt_password.setText(userInfo.get("password"));
            Log.d(TGA + "--password", userInfo.get("password"));
        }else{
            preferences.edit().putBoolean("isFirst", true).commit();
        }

    }
    class Listener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                //注册
                case R.id.textView_register:
                    Intent intent1 = new Intent();
                    intent1.setClass(MainActivity.this, RegisterActivity.class);
                    startActivity(intent1);
                    break;
                //找回密码
                case R.id.textView_password:
                    Intent intent2 = new Intent();
                    intent2.setClass(MainActivity.this, ReturnpwdActivity.class);
                    startActivity(intent2);
                    break;
                //演示登录
                case R.id.btn_demoLogin:
                    Intent intent3 = new Intent();
                    intent3.setClass(MainActivity.this,MainMenuActivity.class);
                    startActivity(intent3);
                    break;
                //登录
                case R.id.btn_Login:
                    Log.d(TGA + "--login", "我被点击了");
                    if (validate()){
                        //登录成功
                        if (loginPro()){
                            //启动到用户界面
                            try {
                                String resultGet = HttpUtil.get();
                                Log.d("MainActivity", resultGet);
                                Intent intent = new Intent();
                                intent.setClass(getApplication(),MainMenuActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_SHORT).show();
                                //结束该activity
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(),"账号或密码错误",Toast.LENGTH_SHORT).show();
                            Log.d("Login", "账号或密码错误");
                        }
                    }
                    break;
            }
        }
    }

    //验证账号
    private boolean loginPro(){
        userName = edt_account.getText().toString();
        password = edt_password.getText().toString();
        try {
            passwordMD5 = MD5.getMD5(password);
            Log.d(TGA + "--passwordMD5",passwordMD5);
            String resultPost = HttpUtil.post(userName, passwordMD5);
            Log.d(TGA + "--resultPost",resultPost);
            JSONObject jsonObject = new JSONObject(resultPost);
            Log.d(TGA + "--jsonObject",jsonObject.toString());
            if (jsonObject.get("loginstatus").equals("success")){
                //保存用户数据
                SharePreference.saveUserInfo(getApplicationContext(),userName,password);
                Log.d(TGA, "数据保存成功");
                return true;
            }else{
                Toast.makeText(getApplicationContext(),"网络异常",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    //检查账号密码是否为空
    private boolean validate(){
        userName = edt_account.getText().toString().trim();
        if (userName.equals("")){
            Log.d(TGA + "--login", "账号是必填项");
            Toast.makeText(getApplicationContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        password = edt_password.getText().toString().trim();
        if (password.equals("")){
            Log.d(TGA + "--login","密码是必填项");
            Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
