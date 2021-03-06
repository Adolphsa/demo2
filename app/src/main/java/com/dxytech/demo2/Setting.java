package com.dxytech.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import io.vov.vitamio.activity.InitActivity;

/**
 * Created by Administrator on 2015/8/13.
 */
public class Setting extends Activity {

    private LinearLayout ll_carInfoManage; //账号管理

    private Button btn_clickback; //返回
    private Button btn_logout; //退出当前账户

    private LinearLayout ll_videoPlayer; //播放器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        //返回
        btn_clickback = (Button)findViewById(R.id.btn_clickback);
        btn_clickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //账号管理
        ll_carInfoManage = (LinearLayout)findViewById(R.id.ll_carInfoManage);
        ll_carInfoManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_carInfoManage = new Intent();
                intent_carInfoManage.setClass(getApplication(),CarInfoManage.class);
                startActivity(intent_carInfoManage);
            }
        });

        //播放器
        ll_videoPlayer = (LinearLayout)findViewById(R.id.ll_videoPlayer);
        ll_videoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplication(), InitActivity.class);
                startActivity(intent);
            }
        });

        //退出当前账户
        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HttpUtil.delete(MainActivity.userName);
                    Intent intent_logout = new Intent();
                    intent_logout.setClass(getApplication(),MainActivity.class);
                    startActivity(intent_logout);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
