package com.dxytech.demo2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/8/25.
 */
public class GetUserInfo extends Activity {

    private static String TGA = "UserInfo";

    private ImageView img_close;

    private TextView tv_account; //账号
    private TextView tv_userName; //姓名
    private TextView tv_numphone; //电话号码
    private TextView tv_email; //email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.getuserinfo);

        tv_account = (TextView)findViewById(R.id.tv_account);
        tv_userName = (TextView)findViewById(R.id.tv_userName);
        tv_numphone = (TextView)findViewById(R.id.tv_numphone);
        tv_email = (TextView)findViewById(R.id.tv_email);

        img_close = (ImageView)findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            String result = UserInfoUtils.get();
            JSONObject jsonObject = new JSONObject(result);
            Log.d(TGA, result);
            tv_account.setText(jsonObject.get("loginname").toString());
            tv_userName.setText(jsonObject.get("realname").toString());
            tv_numphone.setText(jsonObject.get("phonenumber").toString());
            tv_email.setText(jsonObject.get("email").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
