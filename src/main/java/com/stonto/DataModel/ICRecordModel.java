package com.stonto.DataModel;

import java.io.Serializable;
/***
 * IC卡交易数据
 * @author Isme
 *
 */
public class ICRecordModel implements Serializable {

    private String TradeType;	//交易类型：06/08/B4/B5/B9/F0/F6
    private String TradeCnt;	//	交易人次(除F6外均为1)
    private String LineNo;	//	IC卡线路号
    private String LineID;	//	线路ID
    private String BusNo;	//	车辆自编号
    private String BusID;	//	车辆ID
    private String CardID;	//	卡序列号
    private String TradeTime;	//	交易时间：yyyymmddhh24miss
    private String IsUpTrade;	//	是否是上车交易(0和1)
    private String UpTradeTime;	//	上车刷卡时间
    private String DownTradeTime;	//	下车刷卡时间
    private String TradeStation;	//	交易站号
    private String UpTradeStation;	//	上车交易站号
    private String DownTradeStation;	//	下车交易站号
    private String MarkTime;	//	标注时间：yyyymmddhh24miss
    private String MarkStation;	//	标注站号
    private String TradeAttr;	//	交易属性
    private String TradeDate;	//	交易行车日期
    private String ReceiveTime;	//	接收时间
    public String getTradeType() {
        return TradeType;
    }

    public void setTradeType(String tradeType) {
        this.TradeType = tradeType;
    }

    public String getTradeCnt() {
        return TradeCnt;
    }

    public void setTradeCnt(String tradeCnt) {
        this.TradeCnt = tradeCnt;
    }

    public String getLineNo() {
        return LineNo;
    }

    public void setLineNo(String lineNo) {
        this.LineNo = lineNo;
    }

    public String getLineID() {
        return LineID;
    }

    public void setLineID(String lineID) {
        this.LineID = lineID;
    }

    public String getBusNo() {
        return BusNo;
    }

    public void setBusNo(String busNo) {
        this.BusNo = busNo;
    }

    public String getBusID() {
        return BusID;
    }

    public void setBusID(String busID) {
        this.BusID = busID;
    }

    public String getCardID() {
        return CardID;
    }

    public void setCardID(String cardID) {
        this.CardID = cardID;
    }

    public String getTradeTime() {
        return TradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.TradeTime = tradeTime;
    }

    public String getIsUpTrade() {
        return IsUpTrade;
    }

    public void setIsUpTrade(String isUpTrade) {
        this.IsUpTrade = isUpTrade;
    }

    public String getUpTradeTime() {
        return UpTradeTime;
    }

    public void setUpTradeTime(String upTradeTime) {
        this.UpTradeTime = upTradeTime;
    }

    public String getDownTradeTime() {
        return DownTradeTime;
    }

    public void setDownTradeTime(String downTradeTime) {
        this.DownTradeTime = downTradeTime;
    }

    public String getTradeStation() {
        return TradeStation;
    }

    public void setTradeStation(String tradeStation) {
        this.TradeStation = tradeStation;
    }

    public String getUpTradeStation() {
        return UpTradeStation;
    }

    public void setUpTradeStation(String upTradeStation) {
        this.UpTradeStation = upTradeStation;
    }

    public String getDownTradeStation() {
        return DownTradeStation;
    }

    public void setDownTradeStation(String downTradeStation) {
        this.DownTradeStation = downTradeStation;
    }

    public String getMarkTime() {
        return MarkTime;
    }

    public void setMarkTime(String markTime) {
        this.MarkTime = markTime;
    }

    public String getMarkStation() {
        return MarkStation;
    }

    public void setMarkStation(String markStation) {
        this.MarkStation = markStation;
    }

    public String getTradeAttr() {
        return TradeAttr;
    }

    public void setTradeAttr(String tradeAttr) {
        this.TradeAttr = tradeAttr;
    }

    public String getTradeDate() {
        return TradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.TradeDate = tradeDate;
    }

    public String getReceiveTime() {
        return ReceiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.ReceiveTime = receiveTime;
    }


}
