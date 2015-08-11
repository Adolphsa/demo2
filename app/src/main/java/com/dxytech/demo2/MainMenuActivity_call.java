package com.dxytech.demo2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 一键呼叫
 * Created by Administrator on 2015/6/18.
 */
public class MainMenuActivity_call extends Activity{

    private RelativeLayout relativeLayout;
    private Button mainmenu_button;
    private TextView textView_mainmeu_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu_call);

        textView_mainmeu_phone = (TextView)findViewById(R.id.textView_mainmeu_phone);

        //一键呼叫
        relativeLayout = (RelativeLayout)findViewById(R.id.mainmenu_call_relative);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberPhone = textView_mainmeu_phone.getText().toString();
                Intent intent_phone = new Intent();
                intent_phone.setAction("android.intent.action.CALL");
                //intent_phone.addCategory("android.intent.category.DEFAULT");
                intent_phone.setData(Uri.parse("tel:" + numberPhone));
                startActivity(intent_phone);
            }
        });

        //返回上一级菜单
        mainmenu_button = (Button)findViewById(R.id.btn_clickback);
        mainmenu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
