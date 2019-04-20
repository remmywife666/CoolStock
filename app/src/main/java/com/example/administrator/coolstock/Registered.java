package com.example.administrator.coolstock;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.administrator.coolstock.RegisteredThread.SendMailThread;

public class Registered extends AppCompatActivity {

    private EditText confirmMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        //实例化check_code控件
        TextView sendCode=(TextView)findViewById(R.id.send_code);
        //实例化user_email控件用来向用户的邮箱发送验证信息
        confirmMail=(EditText)findViewById(R.id.user_email);



        /**
         * 设置checkCode的点击事件
         */
        sendCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               sendMail();
            }
        });


    }

    /**
     * 开启子线程来实现http请求，安卓版本现在已经不允许在主线程中发起http请求
     */

     private void sendMail(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String userMail=confirmMail.getText().toString();
                    SendMailThread d=new SendMailThread(userMail,"验证码是:123456",
                            "验证信息");
                    d.run();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
