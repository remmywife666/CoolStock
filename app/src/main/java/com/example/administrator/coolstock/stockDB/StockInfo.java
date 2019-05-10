package com.example.administrator.coolstock.stockDB;

import org.litepal.crud.DataSupport;

public class StockInfo extends DataSupport {

    public int stockCode;  //股票代码
    public String stockName; //名称
    public double changepercent; //涨跌幅
    public double trade; //现价
    public double open; //开盘价
    public double high; //最高价
    public double low; //最低价
    public double settlement; //昨日收盘价
    public double volume; //成交量
    public double turnoverratio; //换手率
    public double amount1; //成交额
    public double pb; //市净率
    public double mktcap; //总市值
    public double nmc; //流通市值

    public int getStockCode() {
        return stockCode;
    }

    public void setStockCode(int stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getChangepercent() {
        return changepercent;
    }

    public void setChangepercent(double changepercent) {
        this.changepercent = changepercent;
    }

    public double getTrade() {
        return trade;
    }

    public void setTrade(double trade) {
        this.trade = trade;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getSettlement() {
        return settlement;
    }

    public void setSettlement(double settlement) {
        this.settlement = settlement;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getTurnoverratio() {
        return turnoverratio;
    }

    public void setTurnoverratio(double turnoverratio) {
        this.turnoverratio = turnoverratio;
    }

    public double getAmount1() {
        return amount1;
    }

    public void setAmount1(double amount1) {
        this.amount1 = amount1;
    }

    public double getPb() {
        return pb;
    }

    public void setPb(double pb) {
        this.pb = pb;
    }

    public double getMktcap() {
        return mktcap;
    }

    public void setMktcap(double mktcap) {
        this.mktcap = mktcap;
    }

    public double getNmc() {
        return nmc;
    }

    public void setNmc(double nmc) {
        this.nmc = nmc;
    }
}
