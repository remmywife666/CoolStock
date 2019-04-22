package com.example.administrator.coolstock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.coolstock.RegisteredP.SendMailThread;

import java.util.Random;

public class Registered extends AppCompatActivity {

    //实例化Edittext控件用来记录用户输入邮箱
    private EditText confirmMail;

    //声明随机数
    private int randomNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        //实例化check_code控件
        TextView sendCode=(TextView)findViewById(R.id.send_code);
        //绑定记录邮箱Edittext id
        confirmMail=(EditText)findViewById(R.id.user_email);

         /**
         * 设置checkCode的点击事件
         */
        sendCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String userMail=confirmMail.getText().toString();
                if (userMail.matches("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$")){

                    //产生一个随机数
                    randomNum();
                    //启动sendmail线程发送Http请求用来发送邮件
                    sendMail();
                    Toast.makeText(Registered.this, "邮件已发送，请注意查收，若未收到邮件请检查" +
                            "邮箱地址是否填写正确", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Registered.this, "邮箱格式输入错误，请重新输入", Toast.LENGTH_SHORT).show();
                }
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
                    SendMailThread d=new SendMailThread(userMail,"您的验证码是"+randomNum,
                            "验证信息");
                    d.run();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 随机验证码发生器
     */
    private void randomNum(){
        Random ran=new Random();
        randomNum=ran.nextInt(8999)+1000;
    }
}
