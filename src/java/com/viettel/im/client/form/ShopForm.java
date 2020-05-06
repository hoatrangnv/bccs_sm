package com.viettel.im.client.form;

import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Shop;
import java.util.Date;

/**
 *
 * @author tamdt1 date 19/04/2009
 *
 */
public class ShopForm {
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
    private String staffCodeSearch;
    private Long searchType;
    //loint
    private Double TotalMaxDebit;
    private String totalMaxDebitDisplay;
    private Double TotalCurrentDebit;
    private String totalCurrentDebitDisplay;
    private Double TotalDebit;
    private String TotalDebitDisplay;
    private Double MaxDebit;
    private String MaxDebitDisplay;
    private Double MaxDebitEmployees;
    private String MaxDebitEmployeesDisplay;
    private Double TotalMaxCurrentDebit;
    private String TotalMaxCurrentDebitDisplay;
    private Double MaxCurrentDebit;
    private String MaxCurrentDebitDisplay;
    private Double MaxdCurrentDebitEmployees;
    private String MaxdCurrentDebitEmployeesDisplay;
    private Long authenStatus;
    public String[] selectedShopAuthenIds;
    public String[] selectedShopRemoveIds;
    private String imageUrl;

    public String[] getSelectedShopAuthenIds() {
        return selectedShopAuthenIds;
    }

    public void setSelectedShopAuthenIds(String[] selectedShopAuthenIds) {
        this.selectedShopAuthenIds = selectedShopAuthenIds;
    }

    public String[] getSelectedShopRemoveIds() {
        return selectedShopRemoveIds;
    }

    public void setSelectedShopRemoveIds(String[] selectedShopRemoveIds) {
        this.selectedShopRemoveIds = selectedShopRemoveIds;
    }

    public Long getAuthenStatus() {
        return authenStatus;
    }

    public void setAuthenStatus(Long authenStatus) {
        this.authenStatus = authenStatus;
    }

    public Double getMaxCurrentDebit() {
        return MaxCurrentDebit;
    }

    public void setMaxCurrentDebit(Double MaxCurrentDebit) {
        this.MaxCurrentDebit = MaxCurrentDebit;
    }

    public String getMaxCurrentDebitDisplay() {
        return MaxCurrentDebitDisplay;
    }

    public void setMaxCurrentDebitDisplay(String MaxCurrentDebitDisplay) {
        this.MaxCurrentDebitDisplay = MaxCurrentDebitDisplay;
    }

    public Double getMaxDebit() {
        return MaxDebit;
    }

    public void setMaxDebit(Double MaxDebit) {
        this.MaxDebit = MaxDebit;
    }

    public String getMaxDebitDisplay() {
        return MaxDebitDisplay;
    }

    public void setMaxDebitDisplay(String MaxDebitDisplay) {
        this.MaxDebitDisplay = MaxDebitDisplay;
    }

    public Double getMaxDebitEmployees() {
        return MaxDebitEmployees;
    }

    public void setMaxDebitEmployees(Double MaxDebitEmployees) {
        this.MaxDebitEmployees = MaxDebitEmployees;
    }

    public String getMaxDebitEmployeesDisplay() {
        return MaxDebitEmployeesDisplay;
    }

    public void setMaxDebitEmployeesDisplay(String MaxDebitEmployeesDisplay) {
        this.MaxDebitEmployeesDisplay = MaxDebitEmployeesDisplay;
    }

    public Double getMaxdCurrentDebitEmployees() {
        return MaxdCurrentDebitEmployees;
    }

    public void setMaxdCurrentDebitEmployees(Double MaxdCurrentDebitEmployees) {
        this.MaxdCurrentDebitEmployees = MaxdCurrentDebitEmployees;
    }

    public String getMaxdCurrentDebitEmployeesDisplay() {
        return MaxdCurrentDebitEmployeesDisplay;
    }

    public void setMaxdCurrentDebitEmployeesDisplay(String MaxdCurrentDebitEmployeesDisplay) {
        this.MaxdCurrentDebitEmployeesDisplay = MaxdCurrentDebitEmployeesDisplay;
    }

    public Double getTotalCurrentDebit() {
        return TotalCurrentDebit;
    }

    public void setTotalCurrentDebit(Double TotalCurrentDebit) {
        this.TotalCurrentDebit = TotalCurrentDebit;
    }

    public Double getTotalDebit() {
        return TotalDebit;
    }

    public void setTotalDebit(Double TotalDebit) {
        this.TotalDebit = TotalDebit;
    }

    public String getTotalDebitDisplay() {
        return TotalDebitDisplay;
    }

    public void setTotalDebitDisplay(String TotalDebitDisplay) {
        this.TotalDebitDisplay = TotalDebitDisplay;
    }

    public Double getTotalMaxCurrentDebit() {
        return TotalMaxCurrentDebit;
    }

    public void setTotalMaxCurrentDebit(Double TotalMaxCurrentDebit) {
        this.TotalMaxCurrentDebit = TotalMaxCurrentDebit;
    }

    public String getTotalMaxCurrentDebitDisplay() {
        return TotalMaxCurrentDebitDisplay;
    }

    public void setTotalMaxCurrentDebitDisplay(String TotalMaxCurrentDebitDisplay) {
        this.TotalMaxCurrentDebitDisplay = TotalMaxCurrentDebitDisplay;
    }

    public Double getTotalMaxDebit() {
        return TotalMaxDebit;
    }

    public void setTotalMaxDebit(Double TotalMaxDebit) {
        this.TotalMaxDebit = TotalMaxDebit;
    }

    public String getTotalCurrentDebitDisplay() {
        return totalCurrentDebitDisplay;
    }

    public void setTotalCurrentDebitDisplay(String totalCurrentDebitDisplay) {
        this.totalCurrentDebitDisplay = totalCurrentDebitDisplay;
    }

    public String getTotalMaxDebitDisplay() {
        return totalMaxDebitDisplay;
    }

    public void setTotalMaxDebitDisplay(String totalMaxDebitDisplay) {
        this.totalMaxDebitDisplay = totalMaxDebitDisplay;
    }

    public ShopForm() {
        resetForm();
    }

    public void copyDataFromBO(Shop shop) {
        this.setShopId(shop.getShopId());
        this.setName(shop.getName());
        if (shop.getParentShopId() == null) {
            //neu la kenh cap 1 -> thiet lap parentId = -888L de xu ly tren code
            this.setParentShopId(Constant.ROOT_CHANNEL_ID);
        } else {
            this.setParentShopId(shop.getParentShopId());
        }
        this.setAccount(shop.getAccount());
        this.setBankName(shop.getBankName());
        this.setAddress(shop.getAddress());
        this.setTel(shop.getTel());
        this.setFax(shop.getFax());
        this.setShopCode(shop.getShopCode());
        this.setContactName(shop.getContactName());
        this.setContactTitle(shop.getContactTitle());
        this.setTelNumber(shop.getTelNumber());
        this.setEmail(shop.getEmail());
        this.setDescription(shop.getDescription());
        this.setProvince(shop.getProvince());
        this.setParShopCode(shop.getParShopCode());
        this.setCenterCode(shop.getCenterCode());
        this.setOldShopCode(shop.getOldShopCode());
        this.setCompany(shop.getCompany());
        this.setTin(shop.getTin());
        this.setShop(shop.getShop());
        this.setProvinceCode(shop.getProvinceCode());
        this.setPayComm(shop.getPayComm());
        this.setCreateDate(shop.getCreateDate());
        this.setChannelTypeId(shop.getChannelTypeId());
        this.setDiscountPolicy(shop.getDiscountPolicy());
        this.setPricePolicy(shop.getPricePolicy());
        this.setStatus(shop.getStatus());
    }

    public void copyDataToBO(Shop shop) {
        shop.setShopId(this.getShopId());
        shop.setName(this.getName());
        if ((this.getParentShopId() == null) || this.getParentShopId().equals(Constant.ROOT_CHANNEL_ID)) {
            shop.setParentShopId(null);
        } else {
            shop.setParentShopId(this.getParentShopId());
        }

        shop.setAccount(this.getAccount());
        shop.setBankName(this.getBankName());
        shop.setAddress(this.getAddress());
        shop.setTel(this.getTel());
        shop.setFax(this.getFax());
        shop.setShopCode(this.getShopCode());
        shop.setContactName(this.getContactName());
        shop.setContactTitle(this.getContactTitle());
        shop.setTelNumber(this.getTelNumber());
        shop.setEmail(this.getEmail());
        shop.setDescription(this.getDescription());
        shop.setProvince(this.getProvince());
        shop.setParShopCode(this.getParShopCode());
        shop.setCenterCode(this.getCenterCode());
        shop.setOldShopCode(this.getOldShopCode());
        shop.setCompany(this.getCompany());
        shop.setTin(this.getTin());
        shop.setShop(this.getShop());
        shop.setProvinceCode(this.getProvinceCode());
        shop.setPayComm(this.getPayComm());
        shop.setCreateDate(this.getCreateDate());
        shop.setChannelTypeId(this.getChannelTypeId());
        shop.setDiscountPolicy(this.getDiscountPolicy());
        shop.setPricePolicy(this.getPricePolicy());
        shop.setStatus(this.getStatus());
    }

    public void resetForm() {
        shopId = 0L;
        name = "";
        parentShopId = 0L;
        account = "";
        bankName = "";
        address = "";
        tel = "";
        fax = "";
        shopCode = "";
        contactName = "";
        contactTitle = "";
        telNumber = "";
        email = "";
        description = "";
        province = "";
        parShopCode = "";
        centerCode = "";
        oldShopCode = "";
        company = "";
        tin = "";
        shop = "";
        provinceCode = "";
        payComm = "";
        createDate = new Date();
        channelTypeId = null;
        discountPolicy = "";
        pricePolicy = "";
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCenterCode() {
        return centerCode;
    }

    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(String discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldShopCode() {
        return oldShopCode;
    }

    public void setOldShopCode(String oldShopCode) {
        this.oldShopCode = oldShopCode;
    }

    public String getParShopCode() {
        return parShopCode;
    }

    public void setParShopCode(String parShopCode) {
        this.parShopCode = parShopCode;
    }

    public Long getParentShopId() {
        return parentShopId;
    }

    public void setParentShopId(Long parentShopId) {
        this.parentShopId = parentShopId;
    }

    public String getPayComm() {
        return payComm;
    }

    public void setPayComm(String payComm) {
        this.payComm = payComm;
    }

    public String getPricePolicy() {
        return pricePolicy;
    }

    public void setPricePolicy(String pricePolicy) {
        this.pricePolicy = pricePolicy;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getStaffCodeSearch() {
        return staffCodeSearch;
    }

    public void setStaffCodeSearch(String staffCodeSearch) {
        this.staffCodeSearch = staffCodeSearch;
    }

    public Long getSearchType() {
        return searchType;
    }

    public void setSearchType(Long searchType) {
        this.searchType = searchType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
}
