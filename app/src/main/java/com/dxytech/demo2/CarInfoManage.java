package com.dxytech.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2015/8/25.
 */
public class CarInfoManage extends Activity {
    private Button btn_clickback;

    private LinearLayout ll_accountInfo; //查看账号信息
    private LinearLayout ll_putUserInfo; //修改账号信息
    private LinearLayout ll_deleteUserInfo; //删除账号信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carinfomanage);

        btn_clickback = (Button)findViewById(R.id.btn_clickback);
        btn_clickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //用户信息
        ll_accountInfo = (LinearLayout)findViewById(R.id.ll_accountInfo);
        ll_accountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplication(),GetUserInfo.class);
                startActivity(intent);
            }
        });

        //修改用户信息
        ll_putUserInfo = (LinearLayout)findViewById(R.id.ll_putUserInfo);
        ll_putUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplication(),PutUserInfo.class);
                startActivity(intent);
            }
        });

        //删除账号信息
        ll_deleteUserInfo = (LinearLayout)findViewById(R.id.ll_deleteUserInfo);
        ll_deleteUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplication(),DeleteUserInfo.class);
                startActivity(intent);
            }
        });
    }
}
