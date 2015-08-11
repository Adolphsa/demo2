package com.dxytech.demo2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 信息查询
 * Created by Administrator on 2015/6/26.
 */
public class MainMenuActivity_info_query extends Activity {
    private Context mContext = null;
    private TextView btn_car_type;
    private LinearLayout linear_carType;

    private Button btn_infoQuery_clickback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_query);

        mContext = this;

        btn_infoQuery_clickback = (Button)findViewById(R.id.btn_infoQuery_clickback);
        btn_infoQuery_clickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linear_carType = (LinearLayout)findViewById(R.id.linear_carType);
        btn_car_type = (TextView)findViewById(R.id.btn_car_type);
        linear_carType.setOnClickListener(new MyListener2());


    }


//    public void initPopupWindowView(View view){
//
//        View customView = getLayoutInflater().inflate(R.layout.info_query_car_type, null);
//
//       final PopupWindow popupWindow = new PopupWindow(customView,
//               ViewGroup.LayoutParams.FILL_PARENT,
//               700);
//        popupWindow.setBackgroundDrawable(new ColorDrawable());
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setFocusable(true);
//        popupWindow.setTouchable(true);
//
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                return true;
//            }
//
//
//        });
//        popupWindow.showAsDropDown(btn_car_type,0,20);
//
//    }
//    class MyListener implements View.OnClickListener{
//
//        @Override
//        public void onClick(View v) {
//            initPopupWindowView(v);
//        }
//    }
    class MyListener2 implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            View customView = getLayoutInflater().inflate(R.layout.info_query_car_type,null);
            final PopupWindow popupWindow = new PopupWindow(
                    customView,
                    ViewGroup.LayoutParams.FILL_PARENT,
                    700);
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
            popupWindow.showAsDropDown(btn_car_type,0,20);
        }
    }
    }
