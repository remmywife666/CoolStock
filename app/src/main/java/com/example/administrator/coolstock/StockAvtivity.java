package com.example.administrator.coolstock;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.coolstock.stockDB.StockInfo;
import com.example.administrator.coolstock.util.HttpUtil;
import com.example.administrator.coolstock.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.litepal.LitePalApplication.getContext;

public class StockAvtivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;

    private Button navButton;

    private ScrollView stockLayout;

    private TextView titleStockCode;

    private TextView titleUpdateTime;

    private TextView stockName;

    private LinearLayout stockinfoLayout;

    private TextView stockVolume;

    private TextView stockAmount;

    private TextView stockTurnoverRatio;

    private TextView stockChangeP;

    private TextView stockOpenTV;

    private TextView stockHighTV;

    private TextView stockLowTV;

    private ImageView bingPicImg;

    public SwipeRefreshLayout swipeRefresh;



    /**
     * 股票信息列表
     */
    private List<StockInfo> stockInfoList;

    private ProgressDialog progressDialog;


    private ArrayAdapter<String> adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        /**
         * 初始化各控件
         */
        stockLayout=(ScrollView)findViewById(R.id.stock_layout);

        titleStockCode=(TextView)findViewById(R.id.title_stock_code);

        titleUpdateTime=(TextView)findViewById(R.id.title_update_time);

        stockName=(TextView)findViewById(R.id.stock_name);

        stockinfoLayout=(LinearLayout)findViewById(R.id.stockinfo_layout);

        stockVolume=(TextView)findViewById(R.id.stock_volume);

        stockAmount=(TextView)findViewById(R.id.stock_amount);

        stockTurnoverRatio=(TextView)findViewById(R.id.stock_turnoverratio);

        stockChangeP=(TextView)findViewById(R.id.stock_changepercent);
        stockOpenTV=(TextView)findViewById(R.id.stock_open);
        stockHighTV=(TextView)findViewById(R.id.stock_high);
        stockLowTV=(TextView)findViewById(R.id.stock_low);
        bingPicImg=(ImageView)findViewById(R.id.bing_pic_img);

        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navButton=(Button)findViewById(R.id.nav_button);

        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);

        String stockString=prefs.getString("stock",null);


        String bingPic=prefs.getString("bing_pic",null);
        if (bingPic!=null){
            Glide.with(this).load(bingPic).into(bingPicImg);
        }else{
            loadBingPic();
        }


        queryStock();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String adress="https://api.shenjian.io/?appid=ec68429f2164e4061cc89ba4e0dcff9b";
                queryFromServer(adress,"stock");
            }
        });

        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        navButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void queryStock(){
        stockInfoList=DataSupport.findAll(StockInfo.class);

        if (stockInfoList.size()>0){
            String stockId1=getIntent().getStringExtra("stock_id");

            List<StockInfo> stockInfos=DataSupport.where("stockCode=?",stockId1).find(StockInfo.class);

            titleStockCode.setText(stockId1);
           for (StockInfo stockInfo:stockInfos){
               String stockName1=stockInfo.getStockName();
               Double stockChangepercent=stockInfo.getChangepercent();
               Double stockOpen=stockInfo.getOpen();
               Double stockHigh=stockInfo.getHigh();
               Double stockLow=stockInfo.getLow();
               Double stockVolume1=stockInfo.getVolume();
               Double stockAmount1=stockInfo.getAmount1();
               Double stockTurnoverRatio1=stockInfo.getTurnoverratio();


               stockName.setText(stockName1);

               stockVolume.setText(""+stockVolume1);

               stockAmount.setText(""+stockAmount1);

               stockTurnoverRatio.setText(""+stockTurnoverRatio1);

               stockChangeP.setText("涨跌幅"+stockChangepercent);

               stockOpenTV.setText("开盘价 "+stockOpen);

               stockHighTV.setText("最高价 "+stockHigh);

               stockLowTV.setText("最低价 "+stockLow);

           }

        }else{
            String adress="https://api.shenjian.io/?appid=ec68429f2164e4061cc89ba4e0dcff9b";
            queryFromServer(adress,"stock");

        }
    }

    /**
     *加载必应每日一图
     */
    private void loadBingPic(){
        String requestBingPic="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onResponse(Call call,Response response)throws IOException{
                final String bingPic=response.body().string();
                SharedPreferences.Editor editor=PreferenceManager.
                        getDefaultSharedPreferences(StockAvtivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(StockAvtivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * 根据传入的地址和类型从服务器上查询数据
     */
    private void queryFromServer(String adress,final String type){
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(adress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //通过runOnUiThread()方法回到主线程处理
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();
                boolean result=false;
                if ("stock".equals(type)){
                    result=Utility.handleStockResponse(responseText);
                }

                if (result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("stock".equals(type)){
                                queryStock();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if (progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭对话进度框
     */
    private void closeProgressDialog(){
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }

}
