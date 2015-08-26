package com.dxytech.demo2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


/**
 * 退出程序
 * Created by Administrator on 2015/8/19.
 */
public class Logout extends Activity {

    private Button btn_logout;
    private Button btn_logout__false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.logout);

        Logout_Manager.getInstance().addActivity(this);

        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout__false = (Button)findViewById(R.id.btn_logout_false);

        //确认退出
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout_Manager.getInstance().exit();
            }
        });

        //取消退出
        btn_logout__false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
