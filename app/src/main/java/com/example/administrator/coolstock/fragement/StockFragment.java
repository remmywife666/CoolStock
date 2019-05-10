package com.example.administrator.coolstock.fragement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.coolstock.R;
import com.example.administrator.coolstock.gsonParse.ParseStock;
import com.example.administrator.coolstock.stockDB.StockInfo;
import com.example.administrator.coolstock.util.HttpUtil;
import com.example.administrator.coolstock.util.Utility;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class StockFragment extends Fragment {

    private ListView listView;

    private ArrayAdapter<String>adapter;

    private List<String>dataList=new ArrayList<>();

    /**
     * 股票信息列表
     */
    private List<StockInfo>stockInfoList;

    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

          View view= inflater.inflate(R.layout.fragment_stock, container, false);

          listView=(ListView)view.findViewById(R.id.stock_listview);
          adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1
          ,dataList);
          listView.setAdapter(adapter);

          return view;
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);



        queryStock();
        Button parseStock=(Button)getActivity().findViewById(R.id.start_get_stock_info);
        parseStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStockInfo();
            }
        });

    }

    private void queryStock(){
        stockInfoList=DataSupport.findAll(StockInfo.class);
        if (stockInfoList.size()>0){
            dataList.clear();
            for (StockInfo stockInfo:stockInfoList){
                dataList.add(stockInfo.getStockName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
        }else{
            String adress="https://api.shenjian.io/?appid=ec68429f2164e4061cc89ba4e0dcff9b";
            queryFromServer(adress,"stock");
        }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
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
                    getActivity().runOnUiThread(new Runnable() {
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
            progressDialog=new ProgressDialog(getActivity());
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
    public void requestStockInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient.Builder()
                            .connectTimeout(2,TimeUnit.SECONDS)
                            .readTimeout(300,TimeUnit.SECONDS)
                            .writeTimeout(300,TimeUnit.SECONDS)
                            .build();

                    Request request =new Request.Builder()
                            .url("https://api.shenjian.io/?appid=ec68429f2164e4061cc89ba4e0dcff9b")
                            .build();
                    Response response = client.newCall(request).execute();

                    String stockData=response.body().string();

                    parseStockJSON(stockData);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }



    /**
     * 解析股票数据
     */

    private void parseStockJSON(String stockData) {
        try {
            JSONObject stData = new JSONObject(stockData);
            int status = stData.getInt("error_code");
            JSONArray jsonArray1 = stData.getJSONArray("data");
            String name1[]=new String[jsonArray1.length()];
            if (status == 0) {
            for (int i = 0; i < jsonArray1.length(); i++) {
                String s = jsonArray1.getString(i);
                JSONObject jsonObject = new JSONObject(s);
                int code = jsonObject.getInt("code"); //代码
                String name = jsonObject.getString("name"); //名称
                Double changepercent = jsonObject.getDouble("changepercent"); //涨跌幅
                Double trade = jsonObject.getDouble("trade"); //现价
                Double open = jsonObject.getDouble("open"); //开盘价
                Double high = jsonObject.getDouble("high"); //最高价
                Double low = jsonObject.getDouble("low"); //最低价
                Double settlement = jsonObject.getDouble("settlement"); //昨日收盘价
                Double volume = jsonObject.getDouble("volume"); //成交量
                Double turnoverratio = jsonObject.getDouble("turnoverratio"); //换手率
                Double amount = jsonObject.getDouble("amount"); //成交额
                Double pb = jsonObject.getDouble("pb"); //市净率
                Double mktcap = jsonObject.getDouble("mktcap"); //总市值
                Double nmc = jsonObject.getDouble("nmc"); //流通市值

                name1[i]=jsonObject.getString("name");

                Log.d("testparseStock", name1[i]+" "+i+" "+mktcap);
            }



        }
            } catch(JSONException e){
                e.printStackTrace();
            }

        }


}