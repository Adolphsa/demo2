package com.dxytech.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * 主界面
 * Created by Administrator on 2015/6/16.
 */

public class MainMenuActivity extends Activity {

    private ImageButton igb_mainmenu_call;


    private Button btn_mainmenu_myLoveCar;
    private Button btn_mainmenu_navigation;
    private Button btn_mainmenu_carDiagnose;
    private Button btn_mainmenu_violationQuery;
    private Button btn_mainmenu_infoQuery;
    private Button btn_mainmenu_newInfo;

    private Listener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        listener = new Listener();

        //一键呼叫
        igb_mainmenu_call = (ImageButton)findViewById(R.id.igb_mainmenu_call);
        igb_mainmenu_call.setOnClickListener(listener);


        //我的爱车
        btn_mainmenu_myLoveCar = (Button)findViewById(R.id.btn_mainmenu_myLoveCar);
        btn_mainmenu_myLoveCar.setOnClickListener(listener);

        //我要去哪
        btn_mainmenu_navigation = (Button)findViewById(R.id.btn_mainmenu_navigation);
        btn_mainmenu_navigation.setOnClickListener(listener);

        //车辆诊断
        btn_mainmenu_carDiagnose = (Button)findViewById(R.id.btn_maimenu_carDiagnose);
        btn_mainmenu_carDiagnose.setOnClickListener(listener);

        //违章查询
        btn_mainmenu_violationQuery = (Button)findViewById(R.id.btn_mainmenu_violationQuery);
        btn_mainmenu_violationQuery.setOnClickListener(listener);

        //信息查询
        btn_mainmenu_infoQuery = (Button)findViewById(R.id.btn_mainmenu_infoQuery);
        btn_mainmenu_infoQuery.setOnClickListener(listener);

    }
    class Listener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //一键呼叫
                case R.id.igb_mainmenu_call:
                    Intent intent_mainmenu_call = new Intent();
                    intent_mainmenu_call.setClass(MainMenuActivity.this, MainMenuActivity_call.class);
                    startActivity(intent_mainmenu_call);
                    break;

                //我的爱车
                case R.id.btn_mainmenu_myLoveCar:
                    Intent intent_myLoveCar = new Intent();
                    intent_myLoveCar.setClass(getApplication(), MyLoveCar.class);
                    startActivity(intent_myLoveCar);
                    break;

                //我要去哪
                case R.id.btn_mainmenu_navigation:
                    Intent intent_navigation = new Intent();
                    intent_navigation.setClass(getApplication(), Navigation.class);
                    startActivity(intent_navigation);
                    break;

                //车辆诊断
                case R.id.btn_maimenu_carDiagnose:
                    Intent intent_mainmenu_carDiagnose = new Intent();
                    intent_mainmenu_carDiagnose.setClass(MainMenuActivity.this, MainMenuActivity_carDiagnose.class);
                    startActivity(intent_mainmenu_carDiagnose);
                    break;

                //违章查询
                case R.id.btn_mainmenu_violationQuery:
                    Intent intent_mainmenu_violationQuery = new Intent();
                    intent_mainmenu_violationQuery.setClass(MainMenuActivity.this, MainMenuActivity_violation_query.class);
                    startActivity(intent_mainmenu_violationQuery);
                    break;

                //信息查询
                case R.id.btn_mainmenu_infoQuery:
                    Intent intent_mainmenu_infoQuery = new Intent();
                    intent_mainmenu_infoQuery.setClass(MainMenuActivity.this, MainMenuActivity_info_query.class);
                    startActivity(intent_mainmenu_infoQuery);
            }
        }
    }


}
