/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

import com.viettel.im.client.form.AccountBalanceForm;
import java.util.Date;

/**
 *
 * @author TrongLV
 */
public class VSimtkFull {

    Long shopId;
    String shopCode;
    String shopName;
    Long staffId;
    String staffCode;
    String staffName;
    Long channelTypeId;
    String channelTypeName;
    String objectType;
    String isVtUnit;
    Long ownerId;
    String ownerCode;
    String ownerName;
    Long ownerType;
    String idNo;
    String address;
    Long status;
    Long accountId;
    Long checkIsdn;
    String msisdn;
    String iccid;
    private String newMsisdn;
    private String newIccid;
    String imei;
    String mpin;
    String mpin2;
    Long accountStatus;//lay tu bang detail len : 0 khong hieu luc : 1 hieu luc : 2 tam khoa
    Boolean createAccountBalance;
    Boolean createAccountAnypay;
    Boolean createAccountPayment;
    AccountBalanceForm accountBalance;
    AccountBalanceForm accountAnypay;
    AccountBalanceForm accountPayment;
    Boolean isViewAccountDetail;
    Date birthday;
    Date idIssueDate;
    String idIssuePlace;
    String province;
    String district;
    String precinct;
    String provinceName;
    String districtName;
    String precinctName;
    private String streetBlockName;
    private String streetName;
    private String home;
    String tradeName;
    String contactName;
    String secureQuestion;
    Date mpinExpireDate;
    Long mpinStatus;
    Long loginFailureCount;
    Long agentId;
    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public VSimtkFull() {
    }

    public String getNewIccid() {
        return newIccid;
    }

    public void setNewIccid(String newIccid) {
        this.newIccid = newIccid;
    }

    public String getNewMsisdn() {
        return newMsisdn;
    }

    public void setNewMsisdn(String newMsisdn) {
        this.newMsisdn = newMsisdn;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getStreetBlockName() {
        return streetBlockName;
    }

    public void setStreetBlockName(String streetBlockName) {
        this.streetBlockName = streetBlockName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public AccountBalanceForm getAccountAnypay() {
        return accountAnypay;
    }

    public void setAccountAnypay(AccountBalanceForm accountAnypay) {
        this.accountAnypay = accountAnypay;
    }

    public AccountBalanceForm getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(AccountBalanceForm accountBalance) {
        this.accountBalance = accountBalance;
    }

    public AccountBalanceForm getAccountPayment() {
        return accountPayment;
    }

    public void setAccountPayment(AccountBalanceForm accountPayment) {
        this.accountPayment = accountPayment;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    public Long getCheckIsdn() {
        return checkIsdn;
    }

    public void setCheckIsdn(Long checkIsdn) {
        this.checkIsdn = checkIsdn;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getIsVtUnit() {
        return isVtUnit;
    }

    public void setIsVtUnit(String isVtUnit) {
        this.isVtUnit = isVtUnit;
    }

    public String getMpin() {
        return mpin;
    }

    public void setMpin(String mpin) {
        this.mpin = mpin;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Long ownerType) {
        this.ownerType = ownerType;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Boolean getCreateAccountAnypay() {
        return createAccountAnypay;
    }

    public void setCreateAccountAnypay(Boolean createAccountAnypay) {
        this.createAccountAnypay = createAccountAnypay;
    }

    public Boolean getCreateAccountBalance() {
        return createAccountBalance;
    }

    public void setCreateAccountBalance(Boolean createAccountBalance) {
        this.createAccountBalance = createAccountBalance;
    }

    public Long getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Long accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Boolean getCreateAccountPayment() {
        return createAccountPayment;
    }

    public void setCreateAccountPayment(Boolean createAccountPayment) {
        this.createAccountPayment = createAccountPayment;
    }

    public Boolean getIsViewAccountDetail() {
        return isViewAccountDetail;
    }

    public void setIsViewAccountDetail(Boolean isViewAccountDetail) {
        this.isViewAccountDetail = isViewAccountDetail;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Date getIdIssueDate() {
        return idIssueDate;
    }

    public void setIdIssueDate(Date idIssueDate) {
        this.idIssueDate = idIssueDate;
    }

    public String getIdIssuePlace() {
        return idIssuePlace;
    }

    public void setIdIssuePlace(String idIssuePlace) {
        this.idIssuePlace = idIssuePlace;
    }

    public String getPrecinct() {
        return precinct;
    }

    public void setPrecinct(String precinct) {
        this.precinct = precinct;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public Long getLoginFailureCount() {
        return loginFailureCount;
    }

    public void setLoginFailureCount(Long loginFailureCount) {
        this.loginFailureCount = loginFailureCount;
    }

    public Date getMpinExpireDate() {
        return mpinExpireDate;
    }

    public void setMpinExpireDate(Date mpinExpireDate) {
        this.mpinExpireDate = mpinExpireDate;
    }

    public Long getMpinStatus() {
        return mpinStatus;
    }

    public void setMpinStatus(Long mpinStatus) {
        this.mpinStatus = mpinStatus;
    }

    public String getSecureQuestion() {
        return secureQuestion;
    }

    public void setSecureQuestion(String secureQuestion) {
        this.secureQuestion = secureQuestion;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getPrecinctName() {
        return precinctName;
    }

    public void setPrecinctName(String precinctName) {
        this.precinctName = precinctName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getMpin2() {
        return mpin2;
    }

    public void setMpin2(String mpin2) {
        this.mpin2 = mpin2;
    }
}
