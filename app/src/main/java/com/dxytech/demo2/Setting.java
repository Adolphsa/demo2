package com.dxytech.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2015/8/13.
 */
public class Setting extends Activity {

    private Button btn_clickback; //返回
    private Button btn_logout; //退出当前账户

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

        //退出当前账户
        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HttpUtil.delete();
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
