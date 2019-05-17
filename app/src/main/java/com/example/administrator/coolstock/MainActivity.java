package com.example.administrator.coolstock;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.coolstock.fragement.CateFragment;
import com.example.administrator.coolstock.fragement.HomeFragment;
import com.example.administrator.coolstock.fragement.NewsFragment;
import com.example.administrator.coolstock.fragement.StockFragment;
import com.example.administrator.coolstock.fragement.UserFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String fragmentTag;
    private TextView home,stockInfo,news,user;
    private Fragment homeFragment=new HomeFragment(),
                      stockInfoFragment=new StockFragment(),
                      newsFragment=new NewsFragment(),
                      userFragment=new UserFragment(),
                      cateFragment=new CateFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportFragmentManager()         //获取管理类
                .beginTransaction()                  //开启事务
                .add(R.id.main_content,homeFragment,"homeFragment")
                .add(R.id.main_content,stockInfoFragment,"stockInfoFragment")
                .add(R.id.main_content,newsFragment,"newsFragment")
                .add(R.id.main_content,userFragment,"userFragment")
                .add(R.id.main_content,cateFragment,"cateFragment")
                .hide(cateFragment)
                .hide(stockInfoFragment)
                .hide(newsFragment)
                .hide(userFragment)
                .commit();
        this.fragmentTag="homeFragment";
        initView();

    }




    public void switchFragment(String toTag){
        MyFragmentActivity mf = new MyFragmentActivity();
        mf.switchFragment(getSupportFragmentManager(),toTag, this.fragmentTag);
        this.fragmentTag = toTag;
    }

    /**
     *  创建一个继承自FragmentActivity的类，并定义一个方法来实现Fragment页面间的切换
     */
    class MyFragmentActivity extends FragmentActivity {
        // 定义一个方法：实现Fragment页面间的切换
        public void switchFragment(FragmentManager fm, String toTag, String foTag) {
            Fragment fo = fm.findFragmentByTag(foTag);
            Fragment to = fm.findFragmentByTag(toTag);
            if (fo != to) {
                fm.beginTransaction().hide(fo).show(to).commit();
            }
        }
    }
    /**
     * 初始化4个按钮，绑定监听器
     */
    private void initView(){
        home=findViewById(R.id.home);
        stockInfo=findViewById(R.id.stock_info);
        news=findViewById(R.id.news);
        user= findViewById(R.id.user);

        home.setOnClickListener(this);
        stockInfo.setOnClickListener(this);
        news.setOnClickListener(this);
        user.setOnClickListener(this);
    }
    /**
     * 监听器对应方法
     */
    public void onClick(View v){
        switch (v.getId()){
            case R.id.home:
                this.switchFragment("homeFragment");
                break;
            case R.id.stock_info:
//                Intent intent=new Intent(MainActivity.this,StockAvtivity.class);
//                startActivity(intent);
                this.switchFragment("stockInfoFragment");
                break;
            case R.id.news:
                this.switchFragment("newsFragment");
                break;
            case R.id.user:
                this.switchFragment("userFragment");
                break;
        }
    }
}
