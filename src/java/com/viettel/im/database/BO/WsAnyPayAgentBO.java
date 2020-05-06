/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.BO;

/**
 *
 * @author MrSun
 */
public class WsAnyPayAgentBO {

    //fields must input
    private String serialID;    //(*)
    private String agentCode;   //(*)
    private String msisdn;      //(*)
    private String digest;      //(*)
    
    private String iccid;       //(*)
    private String serviceType;
    private long balance;       //(*)

    private String balanceInfo;
    
    private String password;    //(*)
    private String agentType;
    private String createdDate;
    private String state;
    private String accessType;
    private String validDate;
    private String userName;
    private String residentID;
    private String address;
    private String postcode;
    private String email;
    private String contactPh;
    private String serviceAddr;
    
    //fields can modified
    private String newMSISDN;
    private String newICCID;
    private String newPassword;
    private String newState;
    private String newAccessType;
    private String newValidDate;
    private String newUserName;
    private String newResidentID;
    private String newAddress;
    private String newPostcode;
    private String newEmail;
    private String newContactPh;
    private String newServiceAddr;
            
    private String result;
    private String resInfo;
    private String resDigest;
    
    private String createdUser;
    private String methodName;

    public WsAnyPayAgentBO() {
        methodName = "";                
    }

    public WsAnyPayAgentBO(String serialID, String agentCode, String msisdn, String iccid, String serviceType, long balance, String password, String agentType, String createdDate, String state, String accessType, String validDate, String userName, String residentID, String address, String postcode, String email, String contactPh, String serviceAddr, String digest) {
        this.serialID = serialID;
        this.agentCode = agentCode;
        this.msisdn = msisdn;
        this.iccid = iccid;
        this.serviceType = serviceType;
        this.balance = balance;
        this.password = password;
        this.agentType = agentType;
        this.createdDate = createdDate;
        this.state = state;
        this.accessType = accessType;
        this.validDate = validDate;
        this.userName = userName;
        this.residentID = residentID;
        this.address = address;
        this.postcode = postcode;
        this.email = email;
        this.contactPh = contactPh;
        this.serviceAddr = serviceAddr;
        this.digest = digest;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getContactPh() {
        return contactPh;
    }

    public void setContactPh(String contactPh) {
        this.contactPh = contactPh;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getResidentID() {
        return residentID;
    }

    public void setResidentID(String residentID) {
        this.residentID = residentID;
    }

    public String getSerialID() {
        return serialID;
    }

    public void setSerialID(String serialID) {
        this.serialID = serialID;
    }

    public String getServiceAddr() {
        return serviceAddr;
    }

    public void setServiceAddr(String serviceAddr) {
        this.serviceAddr = serviceAddr;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
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

    public String getNewAccessType() {
        return newAccessType;
    }

    public void setNewAccessType(String newAccessType) {
        this.newAccessType = newAccessType;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }

    public String getNewContactPh() {
        return newContactPh;
    }

    public void setNewContactPh(String newContactPh) {
        this.newContactPh = newContactPh;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewICCID() {
        return newICCID;
    }

    public void setNewICCID(String newICCID) {
        this.newICCID = newICCID;
    }

    public String getNewMSISDN() {
        return newMSISDN;
    }

    public void setNewMSISDN(String newMSISDN) {
        this.newMSISDN = newMSISDN;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPostcode() {
        return newPostcode;
    }

    public void setNewPostcode(String newPostcode) {
        this.newPostcode = newPostcode;
    }

    public String getNewResidentID() {
        return newResidentID;
    }

    public void setNewResidentID(String newResidentID) {
        this.newResidentID = newResidentID;
    }

    public String getNewServiceAddr() {
        return newServiceAddr;
    }

    public void setNewServiceAddr(String newServiceAddr) {
        this.newServiceAddr = newServiceAddr;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }

    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    public String getNewValidDate() {
        return newValidDate;
    }

    public void setNewValidDate(String newValidDate) {
        this.newValidDate = newValidDate;
    }

    public String getBalanceInfo() {
        return balanceInfo;
    }

    public void setBalanceInfo(String balanceInfo) {
        this.balanceInfo = balanceInfo;
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
        if (this.methodName == null)
            this.methodName = "";
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        if (methodName == null)
            methodName = "";
        this.methodName = methodName;
    }
    
    
}
