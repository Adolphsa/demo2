package com.dxytech.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * 注册界面
 * Created by Administrator on 2015/6/15.
 */

public class RegisterActivity extends Activity {

    private static String TGA = "RegisterActivity";

    private String register_acconut;
    private String register_password;
    private String register_surepwd;
    private String register_numphone;
    private String register_email;
    private String register_realname;

    private EditText edt_register_acconut;
    private EditText edt_register_password;
    private EditText edt_register_surepwd;
    private EditText edt_register_numphone;
    private EditText edt_register_email;
    private EditText edt_register_realname;


    private Button btn_clickback;
    private Button btn_register_ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edt_register_acconut = (EditText)findViewById(R.id.edt_register_acconut);
        edt_register_password = (EditText)findViewById(R.id.edt_register_password);
        edt_register_surepwd = (EditText)findViewById(R.id.edt_register_surepwd);
        edt_register_numphone = (EditText)findViewById(R.id.edt_register_numphone);
        edt_register_email = (EditText)findViewById(R.id.edt_register_email);
        edt_register_realname = (EditText)findViewById(R.id.edt_register_realname);

        //返回前一页
        btn_clickback = (Button)findViewById(R.id.btn_clickback);
        btn_clickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_register_ok = (Button)findViewById(R.id.btn_register_ok);
        btn_register_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    try {
                        String register_passwordMD5 = MD5.getMD5(register_password);
                        String result = UserInfoUtils.post(register_acconut,
                                register_passwordMD5,
                                register_realname,
                                register_numphone,
                                register_email);
                        Log.d(TGA + "post",result);
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.get("issuccess").equals(true)){
                            Log.d(TGA + "issuccess", "注册成功");
                            Intent intent = new Intent();
                            intent.setClass(getApplication(),MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //检查账号密码是否为空
    private boolean validate(){
        //账号
        register_acconut = edt_register_acconut.getText().toString().trim();
        Log.d(TGA + "--register_acconut",register_acconut);
        if (TextUtils.isEmpty(register_acconut)){
            Log.d(TGA + "--validate", "账号是必填项");
            Toast.makeText(getApplicationContext(), "账号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        //密码
        register_password = edt_register_password.getText().toString().trim();
        Log.d(TGA + "--register_password",register_password);
        if (TextUtils.isEmpty(register_password)){
            Log.d(TGA + "--validate","密码是必填项");
            Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }

        //确认密码
        register_surepwd = edt_register_surepwd.getText().toString().trim();
        if (TextUtils.isEmpty(register_surepwd)){
            Toast.makeText(getApplicationContext(),"请再次输入密码",Toast.LENGTH_SHORT).show();
        }
        Log.d(TGA + "--register_surepwd",register_surepwd);

        //判断两次输入的密码是否一致
        if (!(register_password.equals(register_surepwd))){
            Log.d(TGA + "--register_password",register_password);
            Log.d(TGA + "--register_surepwd",register_surepwd);
            Log.d(TGA + "--validate","两次密码输入不一致");
            Toast.makeText(getApplicationContext(),"两次密码输入不一致",Toast.LENGTH_SHORT).show();
            return false;
        }

        //电话
        register_numphone = edt_register_numphone.getText().toString().trim();
        Log.d(TGA + "--register_numphone",register_numphone);
        if (TextUtils.isEmpty(register_numphone)){
            Log.d(TGA + "--validate","电话是必填项");
            Toast.makeText(getApplicationContext(),"电话不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }

        //姓名
        register_realname = edt_register_realname.getText().toString().trim();
        Log.d(TGA + "--register_realname",register_realname);
        if (TextUtils.isEmpty(register_realname)){
            Log.d(TGA + "--validate","姓名是必填项");
            Toast.makeText(getApplicationContext(),"姓名不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }

//        //注册邮箱
//        register_numphone = edt_register_numphone.getText().toString().trim();
//        if (register_password.equals("")){
//            Log.d(TGA + "--validate","邮箱可选");
//            Toast.makeText(getApplicationContext(),"邮箱可选",Toast.LENGTH_SHORT).show();
//            return false;
//        }


        return true;
    }
}
