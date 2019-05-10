package com.example.administrator.coolstock.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.coolstock.stockDB.StockInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    /**
     * 解析和处理服务器返回的股票数据
     */

    public static boolean handleStockResponse(String response){

        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject stData = new JSONObject(response);
                int status = stData.getInt("error_code");
                JSONArray allStock = stData.getJSONArray("data");
                if (status == 0) {
                    for (int i = 0; i < allStock.length(); i++) {
                        String s = allStock.getString(i);
                        JSONObject jsonObject = new JSONObject(s);

                        StockInfo stockInfo =new StockInfo();
                        stockInfo.setStockCode(jsonObject.getInt("code")); //代码
                        stockInfo.setStockName( jsonObject.getString("name")); //名称
                        stockInfo.setChangepercent(jsonObject.getDouble("changepercent")); //涨跌幅
                        stockInfo.setTrade(jsonObject.getDouble("trade")); //现价
                        stockInfo.setOpen(jsonObject.getDouble("open")); //开盘价
                        stockInfo.setHigh(jsonObject.getDouble("high")); //最高价
                        stockInfo.setLow(jsonObject.getDouble("low")); //最低价
                        stockInfo.setSettlement(jsonObject.getDouble("settlement")); //昨日收盘价
                        stockInfo.setVolume(jsonObject.getDouble("volume")); //成交量
                        stockInfo.setTurnoverratio(jsonObject.getDouble("turnoverratio")); //换手率
                        stockInfo.setAmount1(jsonObject.getDouble("amount")); //成交额
                        stockInfo.setPb(jsonObject.getDouble("pb")); //市净率
                        stockInfo.setMktcap(jsonObject.getDouble("mktcap")); //总市值
                        stockInfo.setNmc(jsonObject.getDouble("nmc")); //流通市值
                        stockInfo.save();
                    }
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
