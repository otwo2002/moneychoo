package com.example.fready.moneychoo;

import java.math.BigDecimal;

/**
 * Created by fready on 2018-02-07.
 * 배송대행지 배송정보 비교값
 */

public class CompShppingAgentVO {

    private String agent;        //배대지 코드
    private String gubun;         //항공선박구분
    private String realWeight;   //실무게
    private String volumeWeight;  //부피무게
    private String applyWeight; //적용무게
    private String shppingCharge;   //운송요금
    private String localChage; //국내배송비
    private String totalChage;   //합계배송비
    public CompShppingAgentVO() {
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getGubun() {
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }

    public String getRealWeight() {
        return realWeight;
    }

    public void setRealWeight(String realWeight) {
        this.realWeight = realWeight;
    }

    public String getVolumeWeight() {
        return volumeWeight;
    }

    public void setVolumeWeight(String volumeWeight) {
        this.volumeWeight = volumeWeight;
    }

    public String getApplyWeight() {
        return applyWeight;
    }

    public void setApplyWeight(String applyWeight) {
        this.applyWeight = applyWeight;
    }

    public String getShppingCharge() {
        return shppingCharge;
    }

    public void setShppingCharge(String shppingCharge) {
        this.shppingCharge = shppingCharge;
    }

    public String getLocalChage() {
        return localChage;
    }

    public void setLocalChage(String localChage) {
        this.localChage = localChage;
    }

    public String getTotalChage() {
        return totalChage;
    }

    public void setTotalChage(String totalChage) {
        this.totalChage = totalChage;
    }
}
