/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.BO;

/**
 *
 * @author MrSun
 */
public class WsRechargeAgentBO {

    private String serialID;
    private String agentCode;
    private String msisdn;
    private String transSerial;
    private String serviceType;
    private long amount;
    private String digest;

    private long blcAfter;
    private String transID;
    
    private String result;
    private String resInfo;
    private String resDigest;
    
    private String createdUser;
    private String methodName;    

    public WsRechargeAgentBO() {
    }

    public WsRechargeAgentBO(String serialID, String agentCode, String msisdn, String transSerial, String serviceType, long amount, String digest) {
        this.serialID = serialID;
        this.agentCode = agentCode;
        this.msisdn = msisdn;
        this.transSerial = transSerial;
        this.serviceType = serviceType;
        this.amount = amount;
        this.digest = digest;
    }

    
    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getSerialID() {
        return serialID;
    }

    public void setSerialID(String serialID) {
        this.serialID = serialID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getTransSerial() {
        return transSerial;
    }

    public void setTransSerial(String transSerial) {
        this.transSerial = transSerial;
    }

    public String getResInfo() {
        return resInfo;
    }

    public void setResInfo(String resInfo) {
        this.resInfo = resInfo;
    }

    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }

    public long getBlcAfter() {
        return blcAfter;
    }

    public void setBlcAfter(long blcAfter) {
        this.blcAfter = blcAfter;
    }

    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }

    public String getResDigest() {
        return resDigest;
    }

    public void setResDigest(String resDigest) {
        this.resDigest = resDigest;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    

    
}
