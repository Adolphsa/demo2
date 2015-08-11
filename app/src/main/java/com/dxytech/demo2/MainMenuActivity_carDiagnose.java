package com.dxytech.demo2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 车辆诊断
 * Created by Administrator on 2015/6/25.
 */
public class MainMenuActivity_carDiagnose extends Activity {

    private Button btn_carDiagnose_clickback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardiagnose);

        btn_carDiagnose_clickback = (Button)findViewById(R.id.btn_carDiagnose_clickback);
        btn_carDiagnose_clickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
