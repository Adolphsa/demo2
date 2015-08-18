package com.dxytech.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//登录界面
public class MainActivity extends Activity {

    private  String userName;
    private  String password;

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


        textView_register = (TextView)findViewById(R.id.textView_register);
        textView_password = (TextView)findViewById(R.id.textView_password);

        edt_account = (EditText)findViewById(R.id.edt_account);
        edt_password = (EditText)findViewById(R.id.edt_password);

        btn_demoLogin = (Button)findViewById(R.id.btn_demoLogin);
        btn_Login = (Button)findViewById(R.id.btn_Login);

        Listener listener= new Listener();

        textView_register.setOnClickListener(listener);
        textView_password.setOnClickListener(listener);
        btn_demoLogin.setOnClickListener(listener);
        btn_Login.setOnClickListener(listener);
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
                    Log.d("Login", "我被点击了");
                    if (validate()){
                        //登录成功
                        if (loginPro()){
                            //启动到用户界面
                            Intent intent = new Intent();
                            intent.setClass(getApplication(),MainMenuActivity.class);
                            startActivity(intent);
                            //结束该activity
                            finish();
                        }else{
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
            String result = HttpUtil.postTest(userName,password);
            Log.d("MainActivity",result);
            JSONObject jsonObject = new JSONObject(result);
            Log.d("MainActivity",jsonObject.toString());
            if (jsonObject.get("loginstatus").equals("success")){
                return true;
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
            Log.d("Login","账号是必填项");
            return false;
        }
        password = edt_password.getText().toString().trim();
        if (password.equals("")){
            Log.d("Login","密码是必填项");
            return false;
        }
        return true;
    }


}
