package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import java.util.Date;

/**
 *
 * @author tamdt1
 * form bao cao doanh thu
 *
 */
public class ReportRevenueForm {

    private Long shopId;
    private String shopCode;
    private String shopName;
    private String shopPath;
    private String shopAddress;

    private Long staffId;
    private String staffCode;
    private Long stockTypeId;
    private Long stockModelId;
    private String fromDate;
    private String toDate;
    private String saleTransType; //loai giao dich
    private Long channelTypeId; //doi tuong bao cao (Dai ly, Cua hang)
    private Long reasonId; //ly do
    private Long telecomServiceId; //dich vu (mobile, hp, ap)
    private Boolean notBilledSaleTrans; //trang thai giao dich: chua duoc lap hoa don
    private Boolean billedSaleTrans; //trang thai giao dich: da lap hoa don
    private Long reportType; //loai bao cao
    //chuyen tu radio box thanh check box, them 4 thuoc tinh thay cho reportType o tren
    private Boolean reportIncludeShop;
    private Boolean reportIncludeCollaborator;
    private Boolean reportIncludePointOfSale;
    private Boolean reportIncludeAgent;
    private Boolean cancelSaleTrans; //trang thai giao dich: huy
    private Long groupType; //kieu gom nhom (tong hop, chi tiet cap duoi, chi tiet theo nhan vien)
    private Boolean hasMoney; //dang bao cao: co thu tien
    private Boolean noMoney; //dang bao cao: khogn thu tien
    private Long groupBySaleTransType; //co phan nhom theo loai giao dich hay khong
    
    //StartDongdv
    private Boolean exported;
    private Boolean notConfirm;
    //End Dongdv    
    private String staffName;
    private String goodsCode;
    private String goodsName;
    private Long ownerId;
    private String ownerCode;
    private String ownerName;
    private Long objectType; //loai doi tuong
    private String payMethod; //hinh thuc thanh toan
    //vunt - bo sung
    private String staffManageCode;
    private String staffManageName;
    private Long requestTypeId;
    private Boolean reportStockModelInSaleService;

    private String provinceCode;
    private String provinceName;

    private Long methode;
    private String isdnType;
    private Long addGetMoney;

    private Long statusSTK;
    private String statusSTKName;
    private Long statusAcountBalance;
    private String statusAcountBalanceName;
    private Long statusAnyPay;
    private String statusAnyPayName;
    private Long statusOwner;
    private String statusOwnerName;

    private Long agentType;
    private Long statusStaff;

    private Boolean reportSimple;//bao cao su dung template don gian, khong phan cap
    //trung dh3
    private Long subChannelTypeId;


    public ReportRevenueForm() {
        resetForm();
    }

    public Long getSubChannelTypeId() {
        return subChannelTypeId;
    }

    public void setSubChannelTypeId(Long subChannelTypeId) {
        this.subChannelTypeId = subChannelTypeId;
    }
    
    public void resetForm() {
        shopId = 0L;
        shopCode = "";
        staffId = 0L;
        staffCode = "";
        stockTypeId = 0L;
        stockModelId = 0L;
        try {
            fromDate = DateTimeUtils.convertDateToString(new Date());
            toDate = fromDate;
        } catch (Exception ex) {
            fromDate = "";
            toDate = "";
        }
        saleTransType = "";
        channelTypeId = 0L;
        reasonId = 0L;
        telecomServiceId = 0L;
        billedSaleTrans = true;
        notBilledSaleTrans = false;
        cancelSaleTrans = false;
        groupType = 1L;
        hasMoney = true;
        noMoney = false;
        groupBySaleTransType = 1L;
        reportStockModelInSaleService=false;
        objectType = 0L;
        payMethod = "";
        //
        shopName = "";
        staffName = "";
        goodsCode = "";
        goodsName = "";
        //
        reportIncludeShop = true;
        reportIncludeCollaborator = false;
        reportIncludePointOfSale = false;
        reportIncludeAgent = false;

        agentType = null;
        statusStaff = null;
        
        reportSimple = false;
    }

    public Boolean getReportSimple() {
        return reportSimple;
    }

    public void setReportSimple(Boolean reportSimple) {
        this.reportSimple = reportSimple;
    }

    
    public Boolean getExported() {
        return exported;
    }

    public void setExported(Boolean exported) {
        this.exported = exported;
    }

    public Boolean getNotConfirm() {
        return notConfirm;
    }

    public void setNotConfirm(Boolean notConfirm) {
        this.notConfirm = notConfirm;
    }


    public Long getAgentType() {
        return agentType;
    }

    public void setAgentType(Long agentType) {
        this.agentType = agentType;
    }

    public Long getStatusStaff() {
        return statusStaff;
    }

    public void setStatusStaff(Long statusStaff) {
        this.statusStaff = statusStaff;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public Long getGroupType() {
        return groupType;
    }

    public void setGroupType(Long groupType) {
        this.groupType = groupType;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public String getSaleTransType() {
        return saleTransType;
    }

    public void setSaleTransType(String saleTransType) {
        this.saleTransType = saleTransType;
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

    public Boolean getReportStockModelInSaleService() {
        return reportStockModelInSaleService;
    }

    public void setReportStockModelInSaleService(Boolean reportStockModelInSaleService) {
        this.reportStockModelInSaleService = reportStockModelInSaleService;
    }

   
    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Boolean getBilledSaleTrans() {
        return billedSaleTrans;
    }

    public void setBilledSaleTrans(Boolean billedSaleTrans) {
        this.billedSaleTrans = billedSaleTrans;
    }

    public Boolean getCancelSaleTrans() {
        return cancelSaleTrans;
    }

    public void setCancelSaleTrans(Boolean cancelSaleTrans) {
        this.cancelSaleTrans = cancelSaleTrans;
    }

    public Long getGroupBySaleTransType() {
        return groupBySaleTransType;
    }

    public void setGroupBySaleTransType(Long groupBySaleTransType) {
        this.groupBySaleTransType = groupBySaleTransType;
    }

    public Boolean getHasMoney() {
        return hasMoney;
    }

    public void setHasMoney(Boolean hasMoney) {
        this.hasMoney = hasMoney;
    }

    public Boolean getNoMoney() {
        return noMoney;
    }

    public void setNoMoney(Boolean noMoney) {
        this.noMoney = noMoney;
    }

    public Boolean getNotBilledSaleTrans() {
        return notBilledSaleTrans;
    }

    public void setNotBilledSaleTrans(Boolean notBilledSaleTrans) {
        this.notBilledSaleTrans = notBilledSaleTrans;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getReportType() {
        return reportType;
    }

    public void setReportType(Long reportType) {
        this.reportType = reportType;
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

    public Long getRequestTypeId() {
        return requestTypeId;
    }

    public void setRequestTypeId(Long requestTypeId) {
        this.requestTypeId = requestTypeId;
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

    public String getIsdnType() {
        return isdnType;
    }

    public void setIsdnType(String isdnType) {
        this.isdnType = isdnType;
    }

    public Long getMethode() {
        return methode;
    }

    public void setMethode(Long methode) {
        this.methode = methode;
    }

    public Long getAddGetMoney() {
        return addGetMoney;
    }

    public void setAddGetMoney(Long addGetMoney) {
        this.addGetMoney = addGetMoney;
    }

    public Long getStatusAcountBalance() {
        return statusAcountBalance;
    }

    public void setStatusAcountBalance(Long statusAcountBalance) {
        this.statusAcountBalance = statusAcountBalance;
    }

    public String getStatusAcountBalanceName() {
        return statusAcountBalanceName;
    }

    public void setStatusAcountBalanceName(String statusAcountBalanceName) {
        this.statusAcountBalanceName = statusAcountBalanceName;
    }
    
    public Long getStatusAnyPay() {
        return statusAnyPay;
    }

    public void setStatusAnyPay(Long statusAnyPay) {
        this.statusAnyPay = statusAnyPay;
    }

    public String getStatusAnyPayName() {
        return statusAnyPayName;
    }

    public void setStatusAnyPayName(String statusAnyPayName) {
        this.statusAnyPayName = statusAnyPayName;
    }

    public Long getStatusOwner() {
        return statusOwner;
    }

    public void setStatusOwner(Long statusOwner) {
        this.statusOwner = statusOwner;
    }

    public String getStatusOwnerName() {
        return statusOwnerName;
    }

    public void setStatusOwnerName(String statusOwnerName) {
        this.statusOwnerName = statusOwnerName;
    }

    public Long getStatusSTK() {
        return statusSTK;
    }

    public void setStatusSTK(Long statusSTK) {
        this.statusSTK = statusSTK;
    }

    public String getStatusSTKName() {
        return statusSTKName;
    }

    public void setStatusSTKName(String statusSTKName) {
        this.statusSTKName = statusSTKName;
    }

    public Long getObjectType() {
        return objectType;
    }

    public void setObjectType(Long objectType) {
        this.objectType = objectType;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Boolean getReportIncludeAgent() {
        return reportIncludeAgent;
    }

    public void setReportIncludeAgent(Boolean reportIncludeAgent) {
        this.reportIncludeAgent = reportIncludeAgent;
    }

    public Boolean getReportIncludeCollaborator() {
        return reportIncludeCollaborator;
    }

    public void setReportIncludeCollaborator(Boolean reportIncludeCollaborator) {
        this.reportIncludeCollaborator = reportIncludeCollaborator;
    }

    public Boolean getReportIncludePointOfSale() {
        return reportIncludePointOfSale;
    }

    public void setReportIncludePointOfSale(Boolean reportIncludePointOfSale) {
        this.reportIncludePointOfSale = reportIncludePointOfSale;
    }

    public Boolean getReportIncludeShop() {
        return reportIncludeShop;
    }

    public void setReportIncludeShop(Boolean reportIncludeShop) {
        this.reportIncludeShop = reportIncludeShop;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPath() {
        return shopPath;
    }

    public void setShopPath(String shopPath) {
        this.shopPath = shopPath;
    }
//TruongNQ5 20140904 R6505
    private Long month;

    public Long getMonth() {
        return month;
    }

    public void setMonth(Long month) {
        this.month = month;
    }
    


}
