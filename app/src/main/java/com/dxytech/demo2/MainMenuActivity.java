package com.dxytech.demo2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 主界面
 * Created by Administrator on 2015/6/16.
 */

public class MainMenuActivity extends Activity {

    private String TGA = "MainMenuActivity";

    private ImageButton igb_mainmenu_call;
    private ImageButton igb_mainmenu_setting; //设置
    private ImageButton igb_mainmenu_choose_car; //选择车


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

        Logout_Manager.getInstance().addActivity(this);

        listener = new Listener();

        //一键呼叫
        igb_mainmenu_call = (ImageButton)findViewById(R.id.igb_mainmenu_call);
        igb_mainmenu_call.setOnClickListener(listener);

        //设置
        igb_mainmenu_setting = (ImageButton)findViewById(R.id.igb_mainmenu_setting);
        igb_mainmenu_setting.setOnClickListener(listener);

        //选择车
        igb_mainmenu_choose_car =(ImageButton)findViewById(R.id.igb_mainmenu_choose_car);
        igb_mainmenu_choose_car.setOnClickListener(listener);

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

                //设置
                case R.id.igb_mainmenu_setting:
                    Log.d(TGA,"点击设置");
                    Intent intent_setting = new Intent();
                    intent_setting.setClass(MainMenuActivity.this,Setting.class);
                    startActivity(intent_setting);
                    break;

                //选择车
                case R.id.igb_mainmenu_choose_car:

                    View choose_car = getLayoutInflater().inflate(R.layout.choose_car,null);
                    //popupWindow里面的控件
                    final RadioButton choose_car_first = (RadioButton)choose_car.findViewById(R.id.choose_car_first);
                    RadioButton choose_car_sencod = (RadioButton)choose_car.findViewById(R.id.choose_car_second);
                    RadioButton choose_car_three = (RadioButton)choose_car.findViewById(R.id.choose_car_three);

                    final PopupWindow popupWindow = new PopupWindow(choose_car,330,300);

                    //可触摸
                    popupWindow.setTouchable(true);
                    //可聚焦
                    popupWindow.setFocusable(true);
                    // 设置允许在外点击消失
                    popupWindow.setOutsideTouchable(true);

                    //设置背景
                    popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_linearl_shape));
                    popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    Log.d("popupWindow", "显示popupWindow");
                    popupWindow.showAsDropDown(igb_mainmenu_choose_car, -2, 5);
                    Log.d("popupWindow", "显示popupWindow2");

                    class ChooseCar implements View.OnClickListener{

                        @Override
                        public void onClick(View v) {
                            Log.d("popupWindow","被点击");
                            boolean aa = choose_car_first.isChecked();
                            if (aa){
                                Log.d("popupWindow","ture");
                            }else{
                                Log.d("popupWindow","false");
                            }
                            popupWindow.dismiss();
                        }
                    }
                    //控件的监听事件
                    choose_car_first.setOnClickListener(new ChooseCar());
                    choose_car_sencod.setOnClickListener(new ChooseCar());
                    choose_car_three.setOnClickListener(new ChooseCar());
                    break;

                //我的爱车
                case R.id.btn_mainmenu_myLoveCar:
                    Log.d(TGA,"点击我的爱车");
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

    //按返回键弹出退出对话框
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent_logout = new Intent();
            intent_logout.setClass(getApplication(),Logout.class);
            startActivity(intent_logout);
        }
        return super.onKeyDown(keyCode, event);
    }

}
