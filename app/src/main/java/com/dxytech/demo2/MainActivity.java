package com.dxytech.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

//登录界面
public class MainActivity extends Activity {

    private TextView textView_register; //立即注册
    private TextView textView_password; //找回密码

    private Button btn_demoLogin; //演示登录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        textView_register = (TextView)findViewById(R.id.textView_register);
        textView_password = (TextView)findViewById(R.id.textView_password);

        btn_demoLogin = (Button)findViewById(R.id.btn_demoLogin);

        Listener listener= new Listener();

        textView_register.setOnClickListener(listener);
        textView_password.setOnClickListener(listener);
        btn_demoLogin.setOnClickListener(listener);
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
                //登录
                case R.id.btn_demoLogin:
                    Intent intent3 = new Intent();
                    intent3.setClass(MainActivity.this,MainMenuActivity.class);
                    startActivity(intent3);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
