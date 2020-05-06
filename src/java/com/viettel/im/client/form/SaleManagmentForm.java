/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.database.BO.AgentTransOrderHis;
import com.viettel.im.database.BO.SaleTransFull;
import com.viettel.im.database.BO.SaleTransOrder;

/**
 *
 * @author ThanhNC
 * @Purpose:
 */
public class SaleManagmentForm {
    //Dieu kien tim kiem    

    private Long sTelecomServiceId;
    private String sSaleTransCode;
    private Long sShopId;
    private Long sStaffId;
    private Long sSaleTransType;
    private String sCustName;
    private String sIsdn;
    private String sTransFromDate;
    private String sTransToDate;
    private String sContractNo;
    private Long sTransStatus;
    private Long sDeliverStatus;
    private Long sHaveMoney;
    //Thong tin chi tiet giao dich
    private String custName;
    private String telNumber;
    private String company;
    private String address;
    private String tin;
    private String isdn;
    private String contractNo;
    private String invoiceNo;
    private Long invoiceId;
    private String saleTransCode;
    private String telecomServiceName;
    private Long teleconServiceId;
    private String transDate;
    private String note;
    private Double price;
    private Double amountNotTax;
    private Double amountTax;
    private Double amountPromotion;
    private Double amountDiscount;
    private Double total;
    private String priceMoney;
    private String amountNotTaxMoney;
    private String amountTaxMoney;
    private String amountPromotionMoney;
    private String amountDiscountMoney;
    private String totalMoney;
    private String transStatus;
    private String saleTransType;
    private String returnMsg;
    private String shopCode;
    private String shopName;
    private String staffCode;
    private String staffName;
    //TrongLV
    private String sPayMethod;
    private String payMethod;
    private String referenceNo;
    private String exportUrl = "";
    private Long role;
    private Long status;
//    tannh20180123  them cac truong cho chung nang order manager
    private String imageUrl;
    private String orderCode;
    private String bankName;
    private String amount;
    private String fileUrl;
    private String listBankDocuments;
    private String orderCode2;
    private String bankName2;
    private String amount2;
    private String orderCode3;
    private String bankName3;
    private String amount3;
    private String imageUrlForm02;
//    tannh20190506 
    private String bankFather;
    private String bankChild;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public String getExportUrl() {
        return exportUrl;
    }

    public void setExportUrl(String exportUrl) {
        this.exportUrl = exportUrl;
    }
    //TrongLV
    //17/12/2009
    //Huy GD co hoi hang ve kho hay khong? Mac dinh la khong (false)
    private Boolean backGood = false;
    private Boolean cancelTrans = false;

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getSPayMethod() {
        return sPayMethod;
    }

    public void setSPayMethod(String sPayMethod) {
        this.sPayMethod = sPayMethod;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Boolean getBackGood() {
        return backGood;
    }

    public void setBackGood(Boolean backGood) {
        this.backGood = backGood;
    }

    public Boolean getCancelTrans() {
        return cancelTrans;
    }

    public void setCancelTrans(Boolean cancelTrans) {
        this.cancelTrans = cancelTrans;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public SaleManagmentForm() {
        backGood = false;
    }

    public SaleManagmentForm(String custName, String isdn, String contractNo, Long invoiceId, String saleTransCode, Long teleconServiceId, Double price, String transDate) {
        this.custName = custName;
        this.isdn = isdn;
        this.contractNo = contractNo;
        this.invoiceId = invoiceId;
        this.saleTransCode = saleTransCode;
        this.teleconServiceId = teleconServiceId;
        this.price = price;
        this.transDate = transDate;
    }

    public void copyFromSaleTrans(SaleTransFull saleTrans) throws Exception {
        this.custName = saleTrans.getCustName();
        this.telNumber = saleTrans.getTelNumber();
        this.company = saleTrans.getCompany();
        this.address = saleTrans.getAddress();
        this.tin = saleTrans.getTin();
        this.contractNo = saleTrans.getContractNo();
        this.isdn = saleTrans.getIsdn();
        this.note = saleTrans.getNote();

        this.amountNotTax = saleTrans.getAmountNotTax();
        this.amountTax = saleTrans.getTax();
        this.amountDiscount = saleTrans.getDiscount();
        this.amountPromotion = saleTrans.getPromotion();
        this.total = saleTrans.getAmountTax();

        this.saleTransCode = saleTrans.getSaleTransCode();
        this.teleconServiceId = saleTrans.getTelecomServiceId();
        this.telecomServiceName = saleTrans.getTelServiceName();

        this.transDate = DateTimeUtils.convertDateTimeToString(saleTrans.getSaleTransDate());
        this.invoiceId = saleTrans.getInvoiceUsedId();
        this.invoiceNo = saleTrans.getInvoiceNo();
        this.transStatus = saleTrans.getStatus();
        this.saleTransType = saleTrans.getSaleTransType();

        this.amountNotTaxMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getAmountNotTax());
        this.amountTaxMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getTax());
        this.amountDiscountMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getDiscount());
        this.amountPromotionMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getPromotion());
        this.totalMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getAmountTax());
        System.out.println("----------SaleManagement:begin");
        System.out.println("amountNotTaxMoney =" + amountNotTaxMoney);
        System.out.println("amountTaxMoney =" + amountTaxMoney);
        System.out.println("amountDiscountMoney =" + amountDiscountMoney);
        System.out.println("amountPromotionMoney =" + amountPromotionMoney);
        System.out.println("totalMoney =" + totalMoney);
        System.out.println("----------SaleManagement:finish");
    }

    public void copyFromSaleTransOrder(SaleTransOrder saleTrans) throws Exception {
        this.custName = saleTrans.getCustName();
        this.telNumber = saleTrans.getTelNumber();
        this.company = saleTrans.getCompany();
        this.address = saleTrans.getAddress();
        this.tin = saleTrans.getTin();
        this.contractNo = saleTrans.getContractNo();
        this.isdn = saleTrans.getIsdn();
        this.note = saleTrans.getNote();

        this.amountNotTax = saleTrans.getAmountNotTax();
        this.amountTax = saleTrans.getTax();
        this.amountDiscount = saleTrans.getDiscount();
        this.amountPromotion = saleTrans.getPromotion();
        this.total = saleTrans.getAmountTax();

        this.saleTransCode = saleTrans.getSaleTransCode();
        this.teleconServiceId = saleTrans.getTelecomServiceId();

        this.transDate = DateTimeUtils.convertDateTimeToString(saleTrans.getSaleTransDate());
        this.invoiceId = saleTrans.getInvoiceUsedId();
        this.transStatus = saleTrans.getStatus();
        this.saleTransType = saleTrans.getSaleTransType();

        this.amountNotTaxMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getAmountNotTax());
        this.amountTaxMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getTax());
        this.amountDiscountMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getDiscount());
        this.amountPromotionMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getPromotion());
        this.totalMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getAmountTax());

        this.status = saleTrans.getIsCheck();
        this.imageUrl = saleTrans.getScanPath();
        this.orderCode = saleTrans.getOrderCode();
        this.bankName = saleTrans.getBankName();
        this.amount = saleTrans.getAmount();
        this.listBankDocuments = saleTrans.getListBankDocuments();

        this.orderCode2 = saleTrans.getOrderCode2();
        this.bankName2 = saleTrans.getBankName2();
        this.amount2 = saleTrans.getAmount2();
        this.orderCode3 = saleTrans.getOrderCode3();
        this.bankName3 = saleTrans.getBankName3();
        this.amount3 = saleTrans.getAmount3();

        System.out.println("----------SaleManagement:begin");
        System.out.println("amountNotTaxMoney =" + amountNotTaxMoney);
        System.out.println("amountTaxMoney =" + amountTaxMoney);
        System.out.println("amountDiscountMoney =" + amountDiscountMoney);
        System.out.println("amountPromotionMoney =" + amountPromotionMoney);
        System.out.println("totalMoney =" + totalMoney);
        System.out.println("----------SaleManagement:finish");
    }

    public void copyFromAgentTransOrderHis(AgentTransOrderHis saleTrans) throws Exception {
        this.custName = saleTrans.getCustName();
        this.telNumber = saleTrans.getTelNumber();
        this.company = saleTrans.getCompany();
        this.address = saleTrans.getAddress();
        this.tin = saleTrans.getTin();
        this.contractNo = saleTrans.getContractNo();
        this.isdn = saleTrans.getIsdn();
        this.note = saleTrans.getNote();

        this.amountNotTax = saleTrans.getAmountNotTax();
        this.amountTax = saleTrans.getTax();
        this.amountDiscount = saleTrans.getDiscount();
        this.amountPromotion = saleTrans.getPromotion();
        this.total = saleTrans.getAmountTax();

        this.saleTransCode = saleTrans.getSaleTransCode();
        this.teleconServiceId = saleTrans.getTelecomServiceId();

        this.transDate = DateTimeUtils.convertDateTimeToString(saleTrans.getSaleTransDate());
        this.invoiceId = saleTrans.getInvoiceUsedId();
        this.transStatus = saleTrans.getStatus();
        this.saleTransType = saleTrans.getSaleTransType();

        this.amountNotTaxMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getAmountNotTax());
        this.amountTaxMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getTax());
        this.amountDiscountMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getDiscount());
        this.amountPromotionMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getPromotion());
        this.totalMoney = NumberUtils.rounđAndFormatAmount(saleTrans.getAmountTax());

        this.status = saleTrans.getIsCheck();
        this.imageUrl = saleTrans.getScanPath();
        this.orderCode = saleTrans.getOrderCode();
        this.bankName = saleTrans.getBankName();
        this.amount = saleTrans.getAmount();
        this.listBankDocuments = saleTrans.getListBankDocuments();

        this.orderCode2 = saleTrans.getOrderCode2();
        this.bankName2 = saleTrans.getBankName2();
        this.amount2 = saleTrans.getAmount2();
        this.orderCode3 = saleTrans.getOrderCode3();
        this.bankName3 = saleTrans.getBankName3();
        this.amount3 = saleTrans.getAmount3();

        System.out.println("----------SaleManagement:begin");
        System.out.println("amountNotTaxMoney =" + amountNotTaxMoney);
        System.out.println("amountTaxMoney =" + amountTaxMoney);
        System.out.println("amountDiscountMoney =" + amountDiscountMoney);
        System.out.println("amountPromotionMoney =" + amountPromotionMoney);
        System.out.println("totalMoney =" + totalMoney);
        System.out.println("----------SaleManagement:finish");
    }

    public Long getSHaveMoney() {
        return sHaveMoney;
    }

    public void setSHaveMoney(Long sHaveMoney) {
        this.sHaveMoney = sHaveMoney;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSContractNo() {
        return sContractNo;
    }

    public void setSContractNo(String sContractNo) {
        this.sContractNo = sContractNo;
    }

    public String getSCustName() {
        return sCustName;
    }

    public void setSCustName(String sCustName) {
        this.sCustName = sCustName;
    }

    public Long getSDeliverStatus() {
        return sDeliverStatus;
    }

    public void setSDeliverStatus(Long sDeliverStatus) {
        this.sDeliverStatus = sDeliverStatus;
    }

    public String getSIsdn() {
        return sIsdn;
    }

    public void setSIsdn(String sIsdn) {
        this.sIsdn = sIsdn;
    }

    public String getSSaleTransCode() {
        return sSaleTransCode;
    }

    public void setSSaleTransCode(String sSaleTransCode) {
        this.sSaleTransCode = sSaleTransCode;
    }

    public Long getSSaleTransType() {
        return sSaleTransType;
    }

    public void setSSaleTransType(Long sSaleTransType) {
        this.sSaleTransType = sSaleTransType;
    }

    public Long getSShopId() {
        return sShopId;
    }

    public void setSShopId(Long sShopId) {
        this.sShopId = sShopId;
    }

    public Long getSStaffId() {
        return sStaffId;
    }

    public void setSStaffId(Long sStaffId) {
        this.sStaffId = sStaffId;
    }

    public Long getSTelecomServiceId() {
        return sTelecomServiceId;
    }

    public void setSTelecomServiceId(Long sTelecomServiceId) {
        this.sTelecomServiceId = sTelecomServiceId;
    }

    public String getSTransFromDate() {
        return sTransFromDate;
    }

    public void setSTransFromDate(String sTransFromDate) {
        this.sTransFromDate = sTransFromDate;
    }

    public Long getSTransStatus() {
        return sTransStatus;
    }

    public void setSTransStatus(Long sTransStatus) {
        this.sTransStatus = sTransStatus;
    }

    public String getSTransToDate() {
        return sTransToDate;
    }

    public void setSTransToDate(String sTransToDate) {
        this.sTransToDate = sTransToDate;
    }

    public String getSaleTransCode() {
        return saleTransCode;
    }

    public void setSaleTransCode(String saleTransCode) {
        this.saleTransCode = saleTransCode;
    }

    public String getTelecomServiceName() {
        return telecomServiceName;
    }

    public void setTelecomServiceName(String telecomServiceName) {
        this.telecomServiceName = telecomServiceName;
    }

    public Long getTeleconServiceId() {
        return teleconServiceId;
    }

    public void setTeleconServiceId(Long teleconServiceId) {
        this.teleconServiceId = teleconServiceId;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getAmountDiscount() {
        return amountDiscount;
    }

    public void setAmountDiscount(Double amountDiscount) {
        this.amountDiscount = amountDiscount;
    }

    public Double getAmountNotTax() {
        return amountNotTax;
    }

    public void setAmountNotTax(Double amountNotTax) {
        this.amountNotTax = amountNotTax;
    }

    public Double getAmountPromotion() {
        return amountPromotion;
    }

    public void setAmountPromotion(Double amountPromotion) {
        this.amountPromotion = amountPromotion;
    }

    public Double getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Double amountTax) {
        this.amountTax = amountTax;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
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

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getAmountDiscountMoney() {
        return amountDiscountMoney;
    }

    public void setAmountDiscountMoney(String amountDiscountMoney) {
        this.amountDiscountMoney = amountDiscountMoney;
    }

    public String getAmountNotTaxMoney() {
        return amountNotTaxMoney;
    }

    public void setAmountNotTaxMoney(String amountNotTaxMoney) {
        this.amountNotTaxMoney = amountNotTaxMoney;
    }

    public String getAmountPromotionMoney() {
        return amountPromotionMoney;
    }

    public void setAmountPromotionMoney(String amountPromotionMoney) {
        this.amountPromotionMoney = amountPromotionMoney;
    }

    public String getAmountTaxMoney() {
        return amountTaxMoney;
    }

    public void setAmountTaxMoney(String amountTaxMoney) {
        this.amountTaxMoney = amountTaxMoney;
    }

    public String getPriceMoney() {
        return priceMoney;
    }

    public void setPriceMoney(String priceMoney) {
        this.priceMoney = priceMoney;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getListBankDocuments() {
        return listBankDocuments;
    }

    public void setListBankDocuments(String listBankDocuments) {
        this.listBankDocuments = listBankDocuments;
    }

    public String getOrderCode2() {
        return orderCode2;
    }

    public void setOrderCode2(String orderCode2) {
        this.orderCode2 = orderCode2;
    }

    public String getBankName2() {
        return bankName2;
    }

    public void setBankName2(String bankName2) {
        this.bankName2 = bankName2;
    }

    public String getAmount2() {
        return amount2;
    }

    public void setAmount2(String amount2) {
        this.amount2 = amount2;
    }

    public String getOrderCode3() {
        return orderCode3;
    }

    public void setOrderCode3(String orderCode3) {
        this.orderCode3 = orderCode3;
    }

    public String getBankName3() {
        return bankName3;
    }

    public void setBankName3(String bankName3) {
        this.bankName3 = bankName3;
    }

    public String getAmount3() {
        return amount3;
    }

    public void setAmount3(String amount3) {
        this.amount3 = amount3;
    }

    public String getImageUrlForm02() {
        return imageUrlForm02;
    }

    public void setImageUrlForm02(String imageUrlForm02) {
        this.imageUrlForm02 = imageUrlForm02;
    }

    public String getBankFather() {
        return bankFather;
    }

    public void setBankFather(String bankFather) {
        this.bankFather = bankFather;
    }

    public String getBankChild() {
        return bankChild;
    }

    public void setBankChild(String bankChild) {
        this.bankChild = bankChild;
    }
}
