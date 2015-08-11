package com.dxytech.demo2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 违章查询
 * Created by Administrator on 2015/6/26.
 */
public class MainMenuActivity_violation_query extends Activity {

    private Button btn_violationQuery_clickback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.violation_query);

        btn_violationQuery_clickback = (Button)findViewById(R.id.btn_violationQuery_clickback);
        btn_violationQuery_clickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
