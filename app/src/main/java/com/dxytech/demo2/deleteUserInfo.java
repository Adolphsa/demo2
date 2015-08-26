package com.dxytech.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * 删除账号
 * Created by Administrator on 2015/8/26.
 */
public class DeleteUserInfo extends Activity {

    private Button btn_deleteSure; //确定删除
    private Button btn_deleteCancel; //取消删除

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.deleteuserinfo);

        //删除账号
        btn_deleteSure = (Button)findViewById(R.id.btn_deleteSure);
        btn_deleteSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String result = UserInfoUtils.delete();
                    Log.d("DeleteUserInfo",result);
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.get("issuccess").equals(true)) {
                        Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //取消删除
        btn_deleteCancel = (Button)findViewById(R.id.btn_deleteCancel);
        btn_deleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
