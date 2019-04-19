package com.example.administrator.coolstock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    private EditText userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //实例化TextView控件
        TextView registeredFunction=(TextView)findViewById(R.id.registered);
        /**
         * 实现点击文本进入注册活动功能
         */
        registeredFunction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent(LoginActivity.this,Registered.class);
                startActivity(intent);
            }
        });

        userName=(EditText)findViewById(R.id.user_name);
        EditText userPassword=(EditText)findViewById(R.id.user_password);

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户名
                String uName=userName.getText().toString();
                Log.d("username",uName);
            }
        });
    }
}
