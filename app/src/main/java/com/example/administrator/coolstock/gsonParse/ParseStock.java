package com.example.administrator.coolstock.gsonParse;

import com.google.gson.annotations.SerializedName;

public class ParseStock {
    public String error_code;
    @SerializedName("data")
    public Stockdata stockdata;
    public String reason;
    public class Stockdata{
        @SerializedName("code")
        public String stockCode;
        @SerializedName("name")
        public String stockName; //名称
        public String changepercent; //涨跌幅
        public String trade; //现价
        public String open; //开盘价
        public String high; //最高价
        public String low; //最低价
        public String settlement; //昨日收盘价
        public String volume; //成交量
        public String turnoverratio; //换手率
        @SerializedName("amount")
        public String amount1; //成交额
        public String pb; //市净率
        public String mktcap; //总市值
        public String nmc; //流通市值
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public Stockdata getStockdata() {
        return stockdata;
    }

    public void setStockdata(Stockdata stockdata) {
        this.stockdata = stockdata;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
