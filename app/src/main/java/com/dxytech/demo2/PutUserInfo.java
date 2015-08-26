package com.dxytech.demo2;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/8/25.
 */
public class PutUserInfo extends Activity {
    private static String TGA = "PutUserInfo";
    private ImageView img_close;

    private TextView tv_put_account;
    private EditText et_put_password;
    private EditText et_put_userName;
    private EditText et_put_numphone;
    private EditText et_put_email;

    private String put_password;
    private String put_userName;
    private String put_numphone;
    private String put_email;

    private Button btn_putUserInfo_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.putuserinfo);

        tv_put_account = (TextView)findViewById(R.id.tv_put_account);
        et_put_password = (EditText)findViewById(R.id.et_put_password);
        et_put_userName = (EditText)findViewById(R.id.et_put_userName);
        et_put_numphone = (EditText)findViewById(R.id.et_put_numphone);
        et_put_email = (EditText)findViewById(R.id.et_put_email);

        tv_put_account.setText(MainActivity.userName);

        btn_putUserInfo_ok = (Button)findViewById(R.id.btn_putUserInfo_ok);
        btn_putUserInfo_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    check();
                    String passwordMd5 = MD5.getMD5(put_password);
                    String result = UserInfoUtils.put(passwordMd5, put_userName, put_numphone, put_email);
                    Log.d(TGA,result);
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.get("issuccess").equals(true)){
                        Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        img_close = (ImageView)findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void check(){

        //密码
        put_password = et_put_password.getText().toString().trim();
        //姓名
        put_userName = et_put_userName.getText().toString().trim();
        //电话
        put_numphone = et_put_numphone.getText().toString().trim();
        //邮箱
        put_email = et_put_email.getText().toString().trim();


    }
}
