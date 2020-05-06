package com.viettel.im.database.BO;

import java.util.Date;

/**
 * Shop entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Shop implements java.io.Serializable {
    // Fields

    private Long shopId;
    private String name;
    private Long parentShopId;
    private String account;
    private String bankName;
    private String address;
    private String tel;
    private String fax;
    private String shopCode;
    private String contactName;
    private String contactTitle;
    private String telNumber;
    private String email;
    private String description;
    private String province;
    private String parShopCode;
    private String centerCode;
    private String oldShopCode;
    private String company;
    private String tin;
    private String shop;
    private String provinceCode;
    private String payComm;
    private Date createDate;
    private Long channelTypeId;
    private String discountPolicy;
    private String pricePolicy;
    private Long status;
    private String shopPath;
    private String shopType;
    //
    private String shopCodeAndName; //tamdt1, 09/06/2009, them thuoc tinh phuc vu viec hien thi tren list
    private String channelTypeName;
    private String discountPolicyName;
    private String pricePolicyName;
        private String provinceName;

private String provinceShopCode;


   private String lastUpdateUser;
    private String lastUpdateIpAddress;
    private Date lastUpdateTime;
    private String lastUpdateKey;
    
    private Long syncStatus;
    
    
    //haint41 22/11/2011 : them cac truong cho dai ly
    private String district;
    private String precinct;        
    private Long profileState;
    private Date registryDate;
    private String tradeName;
    private String usefulWidth;
    private String surfaceArea;
    private Long boardState;
    private Long checkVAT;
    private Long agentType;
    private String idNo;
    
    private String streetBlockName;
    private String streetName;
    private String home;

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
    
    
    
    public Long getAgentType() {
        return agentType;
    }

    public void setAgentType(Long agentType) {
        this.agentType = agentType;
    }

    public Long getBoardState() {
        return boardState;
    }

    public void setBoardState(Long boardState) {
        this.boardState = boardState;
    }

    public Long getCheckVAT() {
        return checkVAT;
    }

    public void setCheckVAT(Long checkVAT) {
        this.checkVAT = checkVAT;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getPrecinct() {
        return precinct;
    }

    public void setPrecinct(String precinct) {
        this.precinct = precinct;
    }

    public Long getProfileState() {
        return profileState;
    }

    public void setProfileState(Long profileState) {
        this.profileState = profileState;
    }

    public Date getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(Date registryDate) {
        this.registryDate = registryDate;
    }

    public String getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(String surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public String getUsefulWidth() {
        return usefulWidth;
    }

    public void setUsefulWidth(String usefulWidth) {
        this.usefulWidth = usefulWidth;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    
    // Constructors
    /** default constructor */
    public Shop() {
    }

    public Shop(Long shopId, String name) {
        this.shopId = shopId;
        this.name = name;
    }

    public Shop(Long shopId, String shopCode, String name, String shopCodeAndName) {
        this.shopId = shopId;
        this.shopCode = shopCode;
        this.name = name;
        this.shopCodeAndName = shopCodeAndName;
    }

    /** minimal constructor */
    public Shop(Long shopId, String name, Long status, String shopCode) {
        this.shopId = shopId;
        this.name = name;
        this.status = status;
        this.shopCode = shopCode;
    }

    /** full constructor */
    public Shop(Long shopId, String name, Long parentShopId, String account, String bankName,
            String address, String tel, String fax, String shopCode, String contactName,
            String contactTitle, String telNumber, String email, String description,
            String province, String parShopCode, String centerCode, String oldShopCode,
            String company, String tin, String shop, String provinceCode, String payComm,
            Date createDate, Long channelTypeId, String discountPolicy, String pricePolicy,
            Long status, String shopPath, String channelTypeName) {
        this.shopId = shopId;
        this.name = name;
        this.parentShopId = parentShopId;
        this.account = account;
        this.bankName = bankName;
        this.address = address;
        this.tel = tel;
        this.fax = fax;
        this.shopCode = shopCode;
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.telNumber = telNumber;
        this.email = email;
        this.description = description;
        this.province = province;
        this.parShopCode = parShopCode;
        this.centerCode = centerCode;
        this.oldShopCode = oldShopCode;
        this.company = company;
        this.tin = tin;
        this.shop = shop;
        this.provinceCode = provinceCode;
        this.payComm = payComm;
        this.createDate = createDate;
        this.channelTypeId = channelTypeId;
        this.discountPolicy = discountPolicy;
        this.pricePolicy = pricePolicy;
        this.status = status;
        this.shopPath = shopPath;
        this.channelTypeName = channelTypeName;
    }
public Shop(Long shopId, String name, Long parentShopId, String account, String bankName,
            String address, String tel, String fax, String shopCode, String contactName,
            String contactTitle, String telNumber, String email, String description,
            String province, String parShopCode, String centerCode, String oldShopCode,
            String company, String tin, String shop, String provinceCode, String payComm,
            Date createDate, Long channelTypeId, String discountPolicy, String pricePolicy,
            Long status, String shopPath,String discountPolicyName,String pricePolicyName,String provinceName) {
        this.shopId = shopId;
        this.name = name;
        this.parentShopId = parentShopId;
        this.account = account;
        this.bankName = bankName;
        this.address = address;
        this.tel = tel;
        this.fax = fax;
        this.shopCode = shopCode;
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.telNumber = telNumber;
        this.email = email;
        this.description = description;
        this.province = province;
        this.parShopCode = parShopCode;
        this.centerCode = centerCode;
        this.oldShopCode = oldShopCode;
        this.company = company;
        this.tin = tin;
        this.shop = shop;
        this.provinceCode = provinceCode;
        this.payComm = payComm;
        this.createDate = createDate;
        this.channelTypeId = channelTypeId;
        this.discountPolicy = discountPolicy;
        this.pricePolicy = pricePolicy;
        this.status = status;
        this.shopPath = shopPath;
        this.discountPolicyName=discountPolicyName;
        this.pricePolicyName=pricePolicyName;
        this.provinceName=provinceName;
    }


     public Shop(Long shopId, String name, Long parentShopId, String account, String bankName,
            String address, String tel, String fax, String shopCode, String contactName,
            String contactTitle, String telNumber, String email, String description,
            String province, String parShopCode, String centerCode, String oldShopCode,
            String company, String tin, String shop, String provinceCode, String payComm,
            Date createDate, Long channelTypeId, String discountPolicy, String pricePolicy,
            Long status, String shopPath, String channelTypeName,String discountPolicyName,String pricePolicyName,String provinceName) {
        this.shopId = shopId;
        this.name = name;
        this.parentShopId = parentShopId;
        this.account = account;
        this.bankName = bankName;
        this.address = address;
        this.tel = tel;
        this.fax = fax;
        this.shopCode = shopCode;
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.telNumber = telNumber;
        this.email = email;
        this.description = description;
        this.province = province;
        this.parShopCode = parShopCode;
        this.centerCode = centerCode;
        this.oldShopCode = oldShopCode;
        this.company = company;
        this.tin = tin;
        this.shop = shop;
        this.provinceCode = provinceCode;
        this.payComm = payComm;
        this.createDate = createDate;
        this.channelTypeId = channelTypeId;
        this.discountPolicy = discountPolicy;
        this.pricePolicy = pricePolicy;
        this.status = status;
        this.shopPath = shopPath;
        this.channelTypeName = channelTypeName;
        this.discountPolicyName=discountPolicyName;
        this.pricePolicyName=pricePolicyName;
        this.provinceName=provinceName;
    }

     
    public Shop(Long shopId, String name, Long parentShopId, String account, String bankName,
            String address, String tel, String fax, String shopCode, String contactName,
            String contactTitle, String telNumber, String email, String description,
            String province, String parShopCode, String centerCode, String oldShopCode,
            String company, String tin, String shop, String provinceCode, String payComm,
            Date createDate, Long channelTypeId,
            Long status, String shopPath) {
        this.shopId = shopId;
        this.name = name;
        this.parentShopId = parentShopId;
        this.account = account;
        this.bankName = bankName;
        this.address = address;
        this.tel = tel;
        this.fax = fax;
        this.shopCode = shopCode;
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.telNumber = telNumber;
        this.email = email;
        this.description = description;
        this.province = province;
        this.parShopCode = parShopCode;
        this.centerCode = centerCode;
        this.oldShopCode = oldShopCode;
        this.company = company;
        this.tin = tin;
        this.shop = shop;
        this.provinceCode = provinceCode;
        this.payComm = payComm;
        this.createDate = createDate;
        this.channelTypeId = channelTypeId;
        this.status = status;
        this.shopPath = shopPath;
    } 

    public String getShopPath() {
        return shopPath;
    }

    public void setShopPath(String shopPath) {
        this.shopPath = shopPath;
    }

    public Long getShopId() {
        return this.shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getParentShopId() {
        return this.parentShopId;
    }

    public void setParentShopId(Long parentShopId) {
        this.parentShopId = parentShopId;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getShopCode() {
        return this.shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTitle() {
        return this.contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getTelNumber() {
        return this.telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getParShopCode() {
        return this.parShopCode;
    }

    public void setParShopCode(String parShopCode) {
        this.parShopCode = parShopCode;
    }

    public String getCenterCode() {
        return this.centerCode;
    }

    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }

    public String getOldShopCode() {
        return this.oldShopCode;
    }

    public void setOldShopCode(String oldShopCode) {
        this.oldShopCode = oldShopCode;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTin() {
        return this.tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getShop() {
        return this.shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getProvinceCode() {
        return this.provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getPayComm() {
        return this.payComm;
    }

    public void setPayComm(String payComm) {
        this.payComm = payComm;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(String discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public String getPricePolicy() {
        return pricePolicy;
    }

    public void setPricePolicy(String pricePolicy) {
        this.pricePolicy = pricePolicy;
    }

    public String getShopCodeAndName() {
        return shopCodeAndName;
    }

    public void setShopCodeAndName(String shopCodeAndName) {
        this.shopCodeAndName = shopCodeAndName;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    public String getDiscountPolicyName() {
        return discountPolicyName;
    }

    public void setDiscountPolicyName(String discountPolicyName) {
        this.discountPolicyName = discountPolicyName;
    }

    public String getPricePolicyName() {
        return pricePolicyName;
    }

    public void setPricePolicyName(String pricePolicyName) {
        this.pricePolicyName = pricePolicyName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getProvinceShopCode() {
        return provinceShopCode;
    }

    public void setProvinceShopCode(String provinceShopCode) {
        this.provinceShopCode = provinceShopCode;
    }

    public String getLastUpdateIpAddress() {
        return lastUpdateIpAddress;
    }

    public void setLastUpdateIpAddress(String lastUpdateIpAddress) {
        this.lastUpdateIpAddress = lastUpdateIpAddress;
    }

    public String getLastUpdateKey() {
        return lastUpdateKey;
    }

    public void setLastUpdateKey(String lastUpdateKey) {
        this.lastUpdateKey = lastUpdateKey;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Long getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Long syncStatus) {
        this.syncStatus = syncStatus;
    }



}
