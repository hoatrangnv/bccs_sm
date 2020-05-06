/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.util.Date;

/**
 *
 * @author ThanhNC
 */
public class CollAccountManagmentForm {

    private Long shopId;
    private Long staffManage;
    private String staffManageCode;
    private String staffManageName;
    private String collCode;
    private String collName;
    private Long collId;
    private Long accountStatus;
    private Long staffId;
    private String namestaff;
    private Long shopIdStaff;
    private String nameShopIdStaff;
    private String address;
    private String namearea;
    private String idNo;
    private String tel;
    private String userNameCreate;
    private Date createDate;
    private Date startDate;
    private Date endDate;
    private String moneyAmount;
    private Long accountId;
    private String shopcode;
    private String shopName;
    private Date birthday;
    private String email;
    private String realDebit;
    private Double maxCreditNum;
    private Long accountStatusAdd;
    private String collCodeAdd;
    private Long channelTypeId;
    private Long shopSelect;
    private Long typeSearch;
    private String isdn;
    private String serial;
    private String shopSelectCode;
    private String shopSelectName;
    //dung cho accountAgent
    private Long accountIdAgent;
    private String userNameCreateAccountAgent;
    private Date createDateAccountAgent;
    private Date startDateAccountAgent;
    private Date endDateAccountAgent;
    private String password;
    private String ownerCode;
    private Long statusAgent;
    //Thong tin tai khoan
    private Long accountType;//
    private String shopCodeAgent;
    private String shopNameAgent;
    private String staffCode;
    private String staffName;
    private String provinceCode;
    private String provinceName;
    private String districtCode;
    private String districtName;
    private String wardCode;
    private String wardName;
    private String nameAccount;
    private String namerepresentative;
    private String birthDate;
    //private String idNo;
    private String makeDate;
    private String makeAddress;
    private String phoneNumber;
    private String fax;
    //private String email;
    //private String password;
    private String rePassword;
    private String datePassword;
    private String secretQuestion;
    private Long status;
    private Long reasonId;
    private Long amount;
    //private String address;
    private Long agent_id;
    //private String isdn;
    private String iccid;
    private Long parent_agent;
    private String tin;
    private Long centrerId;
    //private Long staffId;
    private String dateCreate;
    //private String serial;
    private String serialNew;
    private String accountName;
    private Long statusAcc;
    private String passAcc;
    private String saveSerialOld;
    private String saveSerialNew;
    private Long amountAccountAnyPayFPT;
    private String amountAccountAnyPayFPTDisplay;
    private Long statusAccountAnyPayFPT;
    private Long checkAccountAnyPayFPT;
    private Long reasonIdAnyPay;
    private Boolean createTKTT; //tao tai khoan TT
    private Boolean createAnyPay; //tao tai khoan anypay
    private Long checkShowViewAccount;
    private Long checkVat;
    private String isdnSearch;
    private Long telecomserviceId;
    private String staffCodeReset;
    private String staffNameReset;
    private String staffManagementCode;
    private String staffManagementName;
    private String shopParentcode;
    private String shopParentName;
    private Long checkIsdn;
    // tutm1 : 28/09/2011 cho phep gach no
    private String imei;
    private Boolean checkPayment;
    private Boolean checkPaymentTmp;
    private Double currentDebit;
    private String currentDebitStr;
    private Double limitDebit;
    private String limitDebitStr;
    

    public String getAmountAccountAnyPayFPTDisplay() {
        return amountAccountAnyPayFPTDisplay;
    }

    public void setAmountAccountAnyPayFPTDisplay(String amountAccountAnyPayFPTDisplay) {
        this.amountAccountAnyPayFPTDisplay = amountAccountAnyPayFPTDisplay;
    }

    public Long getCheckIsdn() {
        return checkIsdn;
    }

    public void setCheckIsdn(Long checkIsdn) {
        this.checkIsdn = checkIsdn;
    }

    public String getStaffCodeReset() {
        return staffCodeReset;
    }

    public void setStaffCodeReset(String staffCodeReset) {
        this.staffCodeReset = staffCodeReset;
    }

    public String getStaffNameReset() {
        return staffNameReset;
    }

    public void setStaffNameReset(String staffNameReset) {
        this.staffNameReset = staffNameReset;
    }

    public Long getTelecomserviceId() {
        return telecomserviceId;
    }

    public void setTelecomserviceId(Long telecomserviceId) {
        this.telecomserviceId = telecomserviceId;
    }

    public String getIsdnSearch() {
        return isdnSearch;
    }

    public void setIsdnSearch(String isdnSearch) {
        this.isdnSearch = isdnSearch;
    }

    public Double getMaxCreditNum() {
        return maxCreditNum;
    }

    public void setMaxCreditNum(Double maxCreditNum) {
        this.maxCreditNum = maxCreditNum;
    }

    public Long getStatusAgent() {
        return statusAgent;
    }

    public void setStatusAgent(Long statusAgent) {
        this.statusAgent = statusAgent;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public Long getAccountIdAgent() {
        return accountIdAgent;
    }

    public void setAccountIdAgent(Long accountIdAgent) {
        this.accountIdAgent = accountIdAgent;
    }

    public Date getCreateDateAccountAgent() {
        return createDateAccountAgent;
    }

    public void setCreateDateAccountAgent(Date createDateAccountAgent) {
        this.createDateAccountAgent = createDateAccountAgent;
    }

    public Date getEndDateAccountAgent() {
        return endDateAccountAgent;
    }

    public void setEndDateAccountAgent(Date endDateAccountAgent) {
        this.endDateAccountAgent = endDateAccountAgent;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getStartDateAccountAgent() {
        return startDateAccountAgent;
    }

    public void setStartDateAccountAgent(Date startDateAccountAgent) {
        this.startDateAccountAgent = startDateAccountAgent;
    }

    public String getUserNameCreateAccountAgent() {
        return userNameCreateAccountAgent;
    }

    public void setUserNameCreateAccountAgent(String userNameCreateAccountAgent) {
        this.userNameCreateAccountAgent = userNameCreateAccountAgent;
    }
    /*
     *TrongLV
     */
    private AccountAnyPayManagementForm pForm;
    private AnyPayTransManagementForm pTransForm;

    public AccountAnyPayManagementForm getPForm() {
        return pForm;
    }

    public void setPForm(AccountAnyPayManagementForm pForm) {
        this.pForm = pForm;
    }

    public AnyPayTransManagementForm getPTransForm() {
        return pTransForm;
    }

    public void setPTransForm(AnyPayTransManagementForm pTransForm) {
        this.pTransForm = pTransForm;
    }

    public CollAccountManagmentForm() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Long accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCollCode() {
        return collCode;
    }

    public void setCollCode(String collCode) {
        this.collCode = collCode;
    }

    public Long getCollId() {
        return collId;
    }

    public void setCollId(Long collId) {
        this.collId = collId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(String moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getNameShopIdStaff() {
        return nameShopIdStaff;
    }

    public void setNameShopIdStaff(String nameShopIdStaff) {
        this.nameShopIdStaff = nameShopIdStaff;
    }

    public String getNamearea() {
        return namearea;
    }

    public void setNamearea(String namearea) {
        this.namearea = namearea;
    }

    public String getNamestaff() {
        return namestaff;
    }

    public void setNamestaff(String namestaff) {
        this.namestaff = namestaff;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopIdStaff() {
        return shopIdStaff;
    }

    public void setShopIdStaff(Long shopIdStaff) {
        this.shopIdStaff = shopIdStaff;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getStaffManage() {
        return staffManage;
    }

    public void setStaffManage(Long staffManage) {
        this.staffManage = staffManage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserNameCreate() {
        return userNameCreate;
    }

    public void setUserNameCreate(String userNameCreate) {
        this.userNameCreate = userNameCreate;
    }

    public String getShopcode() {
        return shopcode;
    }

    public void setShopcode(String shopcode) {
        this.shopcode = shopcode;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealDebit() {
        return realDebit;
    }

    public void setRealDebit(String realDebit) {
        this.realDebit = realDebit;
    }

    public Long getAccountStatusAdd() {
        return accountStatusAdd;
    }

    public void setAccountStatusAdd(Long accountStatusAdd) {
        this.accountStatusAdd = accountStatusAdd;
    }

    public String getCollCodeAdd() {
        return collCodeAdd;
    }

    public void setCollCodeAdd(String collCodeAdd) {
        this.collCodeAdd = collCodeAdd;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public Long getShopSelect() {
        return shopSelect;
    }

    public void setShopSelect(Long shopSelect) {
        this.shopSelect = shopSelect;
    }

    public Long getTypeSearch() {
        return typeSearch;
    }

    public void setTypeSearch(Long typeSearch) {
        this.typeSearch = typeSearch;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCollName() {
        return collName;
    }

    public void setCollName(String collName) {
        this.collName = collName;
    }

    public String getShopSelectCode() {
        return shopSelectCode;
    }

    public void setShopSelectCode(String shopSelectCode) {
        this.shopSelectCode = shopSelectCode;
    }

    public String getShopSelectName() {
        return shopSelectName;
    }

    public void setShopSelectName(String shopSelectName) {
        this.shopSelectName = shopSelectName;
    }

    public String getStaffManageCode() {
        return staffManageCode;
    }

    public void setStaffManageCode(String staffManageCode) {
        this.staffManageCode = staffManageCode;
    }

    public String getStaffManageName() {
        return staffManageName;
    }

    public void setStaffManageName(String staffManageName) {
        this.staffManageName = staffManageName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    //thong tin tai khoan
    public Long getAccountType() {
        return accountType;
    }

    public void setAccountType(Long accountType) {
        this.accountType = accountType;
    }

    public String getShopCodeAgent() {
        return shopCodeAgent;
    }

    public void setShopCodeAgent(String shopCodeAgent) {
        this.shopCodeAgent = shopCodeAgent;
    }

    public String getShopNameAgent() {
        return shopNameAgent;
    }

    public void setShopNameAgent(String shopNameAgent) {
        this.shopNameAgent = shopNameAgent;
    }

    public Long getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(Long agent_id) {
        this.agent_id = agent_id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Long getCentrerId() {
        return centrerId;
    }

    public void setCentrerId(Long centrerId) {
        this.centrerId = centrerId;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getDatePassword() {
        return datePassword;
    }

    public void setDatePassword(String datePassword) {
        this.datePassword = datePassword;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getMakeAddress() {
        return makeAddress;
    }

    public void setMakeAddress(String makeAddress) {
        this.makeAddress = makeAddress;
    }

    public String getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(String makeDate) {
        this.makeDate = makeDate;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }

    public String getNamerepresentative() {
        return namerepresentative;
    }

    public void setNamerepresentative(String namerepresentative) {
        this.namerepresentative = namerepresentative;
    }

    public Long getParent_agent() {
        return parent_agent;
    }

    public void setParent_agent(Long parent_agent) {
        this.parent_agent = parent_agent;
    }

    public String getPassAcc() {
        return passAcc;
    }

    public void setPassAcc(String passAcc) {
        this.passAcc = passAcc;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public String getSaveSerialNew() {
        return saveSerialNew;
    }

    public void setSaveSerialNew(String saveSerialNew) {
        this.saveSerialNew = saveSerialNew;
    }

    public String getSaveSerialOld() {
        return saveSerialOld;
    }

    public void setSaveSerialOld(String saveSerialOld) {
        this.saveSerialOld = saveSerialOld;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSerialNew() {
        return serialNew;
    }

    public void setSerialNew(String serialNew) {
        this.serialNew = serialNew;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
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

    public Long getStatusAcc() {
        return statusAcc;
    }

    public void setStatusAcc(Long statusAcc) {
        this.statusAcc = statusAcc;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public Long getAmountAccountAnyPayFPT() {
        return amountAccountAnyPayFPT;
    }

    public void setAmountAccountAnyPayFPT(Long amountAccountAnyPayFPT) {
        this.amountAccountAnyPayFPT = amountAccountAnyPayFPT;
    }

    public Long getStatusAccountAnyPayFPT() {
        return statusAccountAnyPayFPT;
    }

    public void setStatusAccountAnyPayFPT(Long statusAccountAnyPayFPT) {
        this.statusAccountAnyPayFPT = statusAccountAnyPayFPT;
    }

    public Long getCheckAccountAnyPayFPT() {
        return checkAccountAnyPayFPT;
    }

    public void setCheckAccountAnyPayFPT(Long checkAccountAnyPayFPT) {
        this.checkAccountAnyPayFPT = checkAccountAnyPayFPT;
    }

    public Long getReasonIdAnyPay() {
        return reasonIdAnyPay;
    }

    public void setReasonIdAnyPay(Long reasonIdAnyPay) {
        this.reasonIdAnyPay = reasonIdAnyPay;
    }

    public Boolean getCreateAnyPay() {
        return createAnyPay;
    }

    public void setCreateAnyPay(Boolean createAnyPay) {
        this.createAnyPay = createAnyPay;
    }

    public Boolean getCreateTKTT() {
        return createTKTT;
    }

    public void setCreateTKTT(Boolean createTKTT) {
        this.createTKTT = createTKTT;
    }

    public Long getCheckShowViewAccount() {
        return checkShowViewAccount;
    }

    public void setCheckShowViewAccount(Long checkShowViewAccount) {
        this.checkShowViewAccount = checkShowViewAccount;
    }

    public Long getCheckVat() {
        return checkVat;
    }

    public void setCheckVat(Long checkVat) {
        this.checkVat = checkVat;
    }

    public String getStaffManagementCode() {
        return staffManagementCode;
    }

    public void setStaffManagementCode(String staffManagementCode) {
        this.staffManagementCode = staffManagementCode;
    }

    public String getStaffManagementName() {
        return staffManagementName;
    }

    public void setStaffManagementName(String staffManagementName) {
        this.staffManagementName = staffManagementName;
    }

    public String getShopParentName() {
        return shopParentName;
    }

    public void setShopParentName(String shopParentName) {
        this.shopParentName = shopParentName;
    }

    public String getShopParentcode() {
        return shopParentcode;
    }

    public void setShopParentcode(String shopParentcode) {
        this.shopParentcode = shopParentcode;
    }

    public Boolean getCheckPayment() {
        return checkPayment;
    }

    public void setCheckPayment(Boolean checkPayment) {
        this.checkPayment = checkPayment;
    }

    public Double getCurrentDebit() {
        return currentDebit;
    }

    public void setCurrentDebit(Double currentDebit) {
        if (currentDebit != null) {
            this.currentDebitStr = String.format("%22.5f", currentDebit);
            this.currentDebitStr = currentDebitStr.trim();
        }
        else 
            this.currentDebitStr = "0";
        
        this.currentDebit = currentDebit;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Double getLimitDebit() {
        return limitDebit;
    }

    public void setLimitDebit(Double limitDebit) {
        if (limitDebit != null) {
            this.limitDebitStr = String.format("%22.5f", limitDebit).trim();
            this.currentDebitStr = currentDebitStr.trim();
        }
        else
            this.limitDebitStr = "0";
        this.limitDebit = limitDebit;
    }

    public String getLimitDebitStr() {
        return limitDebitStr;
    }

    public void setLimitDebitStr(String limitDebitStr) {
        this.limitDebitStr = limitDebitStr;
    }

    public String getCurrentDebitStr() {
        return currentDebitStr;
    }

    public void setCurrentDebitStr(String currentDebitStr) {
        this.currentDebitStr = currentDebitStr;
    }

    public Boolean getCheckPaymentTmp() {
        return checkPaymentTmp;
    }

    public void setCheckPaymentTmp(Boolean checkPaymentTmp) {
        this.checkPaymentTmp = checkPaymentTmp;
    }
    
}
