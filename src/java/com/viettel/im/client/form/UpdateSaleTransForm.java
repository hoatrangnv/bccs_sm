/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.InvoiceUsed;
import com.viettel.im.database.BO.UpdateSaleTransBO;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.DAO.InvoiceUsedDAO;
import com.viettel.im.database.DAO.TelecomServiceDAO;
import org.hibernate.Session;

/**
 *
 * @author ThanhNC
 * @Purpose: 
 */
public class UpdateSaleTransForm {
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
    private Long price;
    private String transDate;
    private String note;
    private Long amountNotTax;
    private Long amountTax;
    private Long amountPromotion;
    private Long amountDiscount;
    private Long total;
    private String transStatus;

    private Long saleTransId;

    public Long getSaleTransId() {
        return saleTransId;
    }

    public void setSaleTransId(Long saleTransId) {
        this.saleTransId = saleTransId;
    }

    public UpdateSaleTransForm() {
    }

    public UpdateSaleTransForm(String custName, String isdn, String contractNo, Long invoiceId, String saleTransCode, Long teleconServiceId, Long price, String transDate) {
        this.custName = custName;
        this.isdn = isdn;
        this.contractNo = contractNo;
        this.invoiceId = invoiceId;
        this.saleTransCode = saleTransCode;
        this.teleconServiceId = teleconServiceId;
        this.price = price;
        this.transDate = transDate;
    }

    public void copyFromSaleTrans(UpdateSaleTransBO saleTrans) throws Exception {
        this.custName = saleTrans.getCustName();
        this.custName = saleTrans.getCustName();
        this.telNumber=saleTrans.getTelNumber();
        this.company=saleTrans.getCompany();
        this.address=saleTrans.getAddress();
        this.tin=saleTrans.getTin();
        this.contractNo=saleTrans.getContractNo();
        this.isdn=saleTrans.getIsdn();
        this.note=saleTrans.getNote();
        this.amountNotTax=saleTrans.getAmountNotTax();
        this.amountTax=saleTrans.getTax();
        this.amountDiscount=saleTrans.getDiscount();
        this.amountPromotion=saleTrans.getPromotion();
        this.total=saleTrans.getAmountTax();
        
        this.transDate = DateTimeUtils.convertDateTimeToString(saleTrans.getSaleTransDate());

        this.saleTransId = saleTrans.getSaleTransId();
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
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

    public Long getAmountDiscount() {
        return amountDiscount;
    }

    public void setAmountDiscount(Long amountDiscount) {
        this.amountDiscount = amountDiscount;
    }

    public Long getAmountNotTax() {
        return amountNotTax;
    }

    public void setAmountNotTax(Long amountNotTax) {
        this.amountNotTax = amountNotTax;
    }

    public Long getAmountPromotion() {
        return amountPromotion;
    }

    public void setAmountPromotion(Long amountPromotion) {
        this.amountPromotion = amountPromotion;
    }

    public Long getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Long amountTax) {
        this.amountTax = amountTax;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }
    
}
