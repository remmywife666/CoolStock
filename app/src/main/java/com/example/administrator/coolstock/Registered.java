package com.example.administrator.coolstock;

import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.coolstock.Handler.RegisteredInfoHandler;
import com.example.administrator.coolstock.RegisteredP.SendMailThread;
import com.example.administrator.coolstock.mySQLConnector.DBOpenHelper;
import com.example.administrator.coolstock.mySQLConnector.DBService;
import com.example.administrator.coolstock.mySQLConnector.RegisteredMSQL;
import com.example.administrator.coolstock.mySQLConnector.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.StringReader;
import java.net.ContentHandler;
import java.net.HttpURLConnection;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.Thread.sleep;

public class Registered extends AppCompatActivity {


    //实例化Edittext控件用来记录用户输入邮箱
    private EditText confirmMail;
    /**
     * 实例化Edittext控件记录用户输入验证码
     * 密码
     * 以及检查密码
     */
    private EditText checkCode;
    private EditText registeredP;
    private EditText registeredP1;
    private TextView registeredR;

    //声明随机数
    private int randomNum;

    //dbhandle 在主线程中创建，所以自动绑定主线程
    private Handler dbhandle =new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        //实例化check_code控件
        TextView sendCode = (TextView) findViewById(R.id.send_code);
        //绑定记录邮箱Edittext id
        confirmMail = (EditText) findViewById(R.id.user_email);
        /**
         * 得到用户输入的验证码
         * 和用户设置的密码
         */
        checkCode = (EditText) findViewById(R.id.check_code);
        registeredP = (EditText) findViewById(R.id.registered_password);
        registeredP1 = (EditText) findViewById(R.id.registered_password1);
        registeredR=(TextView)findViewById(R.id.registered_result);


        /**
         * 设置checkCode的点击事件
         */
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMail = confirmMail.getText().toString();
                if (userMail.matches("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$")) {

                    //产生一个随机数
                    randomNum();
                    //启动sendmail线程发送Http请求用来发送邮件
                    sendMail();
                    Toast.makeText(Registered.this, "邮件已发送，请注意查收，若未收到邮件请检查" +
                            "邮箱地址是否填写正确", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Registered.this, "邮箱格式输入错误，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //实例化注册按钮控件
        final Button registered = (Button) findViewById(R.id.registered_account);
        //设计点击事件
        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = "" + randomNum;
                String ck1 = registeredP.getText().toString();
                String ck2 = registeredP1.getText().toString();
                if (c.equals(checkCode.getText().toString())&&ck1.equals(ck2)){
                Toast.makeText(Registered.this, "Success！", Toast.LENGTH_SHORT).show();

                RegisteredWithHttpURLConnection();
            }
                else{ Toast.makeText(Registered.this,"验证码错误或两次输入密码不一致",Toast.LENGTH_SHORT).show();}
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
                    d.start();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 发送HTTP请求用来注册账户
     */
    private void RegisteredWithHttpURLConnection(){

        //开启线程发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //得到注册邮箱
                String userMail = confirmMail.getText().toString();
                //得到注册密码
                String userPassword=registeredP.getText().toString();
                    //使用OKHttpClient发起HTTP请求
                    OkHttpClient client=new OkHttpClient();
                    //创建RequestBody对象存放需要提交的参数（账户信息）
                    RequestBody requestBody=new FormBody.Builder()
                        .add("userId",userMail)
                        .add("userPassword",userPassword)
                        .build();
                    //在Request中调用post方法，将RequestBody传入
                    Request request=new Request.Builder()
                        .url("http://192.168.1.104:8080/coolStocks/Registered")
                        .post(requestBody)
                        .build();
                //创建Response获取返回对象
                Response response=client.newCall(request).execute();
                String responseData=response.body().string();
                    parseJSONWithJSONObject(responseData);
                    showResponse(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 解析json返回值
     */
    private void parseJSONWithJSONObject(String jsonData){
        try {
            JSONObject jsonObject =new JSONObject(jsonData);
            String userId=jsonObject.getString("userId");
            String message=jsonObject.getString("message");
            Log.d("json",userId);
            Log.d("json",message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//    /**
//     * 解析XML
//     */
//    private void parseXMLWithSAX(String xmlData){
//        try {
//            SAXParserFactory factory=SAXParserFactory.newInstance();
//            XMLReader xmlReader=factory.newSAXParser().getXMLReader();
//            RegisteredInfoHandler handler=new RegisteredInfoHandler();
//            //将ContentHandler的实例设置到XMLReader中
//            xmlReader.setContentHandler(handler);
//            //开始执行解析
////            xmlReader.parse(new InputSource(new StringReader(xmlData)));
//
//
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }
//
//    }
    /**
     * UI操作
     */
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //UI操作
                registeredR.setText(response);
            }
        });
    }



    /**
     * 随机验证码发生器
     */
    private void randomNum(){
        Random ran=new Random();
        randomNum=ran.nextInt(8999)+1000;
    }


}
