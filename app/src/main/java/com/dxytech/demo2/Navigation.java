package com.dxytech.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 我要去哪
 * Created by Administrator on 2015/8/10.
 */
public class Navigation extends Activity {

    private ImageView img_close;

    private Button bt_navigation; //一键导航
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.navigation_alertdialog);

        ButtonListener buttonListener = new ButtonListener();

        //返回上一页
        img_close = (ImageView)findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //一键导航
        bt_navigation = (Button)findViewById(R.id.bt_navigation);
        bt_navigation.setOnClickListener(buttonListener);
    }

    class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_navigation:
                    Intent intent1 = new Intent();
                    intent1.setClass(getApplication(),com.dxytech.baidu.bt_navigation.class);
                    startActivity(intent1);
                    break;
            }
        }
    }
}
