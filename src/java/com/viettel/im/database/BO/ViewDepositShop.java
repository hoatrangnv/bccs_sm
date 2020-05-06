package com.viettel.im.database.BO;

import java.util.Date;

/**
 * ViewDepositShop entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class ViewDepositShop implements java.io.Serializable {

    private Long depositId;
    private String isdn;
    private String address;
    private String idNo;
    private String branchId;
    private String namecust;
    private Long shopId;
    private Long receiptId;
    private Date createdate;
    private Long staffId;
    private String namebranch;
    private String nameshop;
    private String receiptNo;
    private String staffname;
    private Long amount;
    private String type;

    public ViewDepositShop() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Long getDepositId() {
        return depositId;
    }

    public void setDepositId(Long depositId) {
        this.depositId = depositId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getNamebranch() {
        return namebranch;
    }

    public void setNamebranch(String namebranch) {
        this.namebranch = namebranch;
    }

    public String getNamecust() {
        return namecust;
    }

    public void setNamecust(String namecust) {
        this.namecust = namecust;
    }

    public String getNameshop() {
        return nameshop;
    }

    public void setNameshop(String nameshop) {
        this.nameshop = nameshop;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
