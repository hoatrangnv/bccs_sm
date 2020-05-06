/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import com.viettel.im.common.util.StringUtils;
import java.util.Date;

/**
 *<P>InvoiceListBean for display in interface</P>
 * @author TungTV
 */
public class RetrieveBillBean implements java.io.Serializable {

    private Long invoiceDestroyedId;
    private Long invoiceListId;
    private String serialNo;
    private String blockName;
    private String blockNo;
    private Long fromInvoice;
    private String strFromInvoice;
    private Long toInvoice;
    private String strToInvoice;
    private Long currInvoiceNo;
    private String fromToInvoice;
    private String currToInvoice;
    private Long splitToInvoice;
    private Long splitFromInvoice;
    private String splitFromToInvoice;
    private String userCreated;
    private Long status;
    private Date dateCreated;
    private Long numberOfInvoice;
    private String invoiceListOrInvoice;
    private String destroyInvoiceReason;
    private String userAssign;
    private Date dateAssign;
    /** This was bill owner name */
    private String billOwnerName;
    /** Shop bill owner name */
    private String shopName;
    private String destroyer;
    /** Staff bill owner name */
    private String staffCode;
    private String departmentCode;
    private String staffName;

    public RetrieveBillBean() {
    }

    /* Construct method */
    public RetrieveBillBean(Long invoiceListId, String serialNo, String blockName,
            String blockNo, Long fromInvoice, Long toInvoice, String userCreated,
            Long status, Date dateCreated, String billOwnerName, String userAssign,
            Date dateAssign) {
        this.invoiceListId = invoiceListId;
        this.serialNo = serialNo;
        this.blockName = blockName;
        this.blockNo = blockNo;
        this.fromInvoice = fromInvoice;
        this.toInvoice = toInvoice;
        this.userCreated = userCreated;
        this.status = status;
        this.dateCreated = dateCreated;
        this.fromToInvoice = StringUtils.standardInvoiceString(this.fromInvoice) + " - " + StringUtils.standardInvoiceString(this.toInvoice);
        this.currToInvoice = StringUtils.standardInvoiceString(this.currInvoiceNo) + " - " + StringUtils.standardInvoiceString(this.toInvoice);
        this.numberOfInvoice = this.toInvoice - this.currInvoiceNo + 1;
        this.billOwnerName = billOwnerName;
        this.userAssign = userAssign;
        this.dateAssign = dateAssign;
        if (this.fromInvoice != null) {
            this.strFromInvoice = StringUtils.standardInvoiceString(this.fromInvoice);
        }
        if (this.toInvoice != null) {
            this.strToInvoice = StringUtils.standardInvoiceString(this.toInvoice);
        }
    }

    public String getCurrToInvoice() {
        return currToInvoice;
    }

    public void setCurrToInvoice(String currToInvoice) {
        this.currToInvoice = currToInvoice;
    }

    public String getDestroyer() {
        return destroyer;
    }

    public void setDestroyer(String destroyer) {
        this.destroyer = destroyer;
    }

    public Date getDateAssign() {
        return dateAssign;
    }

    public void setDateAssign(Date dateAssign) {
        this.dateAssign = dateAssign;
    }

    public String getUserAssign() {
        return userAssign;
    }

    public void setUserAssign(String userAssign) {
        this.userAssign = userAssign;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getFromInvoice() {
        return fromInvoice;
    }

    public void setFromInvoice(Long fromInvoice) {
        this.fromInvoice = fromInvoice;
        if (this.fromInvoice != null) {
            this.strFromInvoice = StringUtils.standardInvoiceString(this.fromInvoice);
        }
    }

    public String getFromToInvoice() {
        return fromToInvoice;
    }

    public void setFromToInvoice(String fromToInvoice) {
        this.fromToInvoice = fromToInvoice;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getToInvoice() {
        return toInvoice;
    }

    public void setToInvoice(Long toInvoice) {
        this.toInvoice = toInvoice;
        this.fromToInvoice = StringUtils.standardInvoiceString(this.fromInvoice) + " - " + StringUtils.standardInvoiceString(this.toInvoice);
        this.numberOfInvoice = this.toInvoice - this.fromInvoice + 1;
        if (this.toInvoice.equals(this.fromInvoice)) {
            this.invoiceListOrInvoice = StringUtils.standardInvoiceString(this.toInvoice);
        } else {
            this.invoiceListOrInvoice = this.fromToInvoice;
        }
        if (this.toInvoice != null) {
            this.strToInvoice = StringUtils.standardInvoiceString(this.toInvoice);
        }
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    public Long getNumberOfInvoice() {
        return numberOfInvoice;
    }

    public void setNumberOfInvoice(Long numberOfInvoice) {
        this.numberOfInvoice = numberOfInvoice;
    }

    public Long getInvoiceListId() {
        return invoiceListId;
    }

    public void setInvoiceListId(Long invoiceListId) {
        this.invoiceListId = invoiceListId;
    }

    public String getBillOwnerName() {
        return billOwnerName;
    }

    public void setBillOwnerName(String billOwnerName) {
        this.billOwnerName = billOwnerName;
    }

    public Long getCurrInvoiceNo() {
        return currInvoiceNo;
    }

    public void setCurrInvoiceNo(Long currInvoiceNo) {
        this.currInvoiceNo = currInvoiceNo;
        this.currToInvoice = StringUtils.standardInvoiceString(this.currInvoiceNo) + " - " + StringUtils.standardInvoiceString(this.toInvoice);
    }

    public String getInvoiceListOrInvoice() {
        return invoiceListOrInvoice;
    }

    public void setInvoiceListOrInvoice(String invoiceListOrInvoice) {
        this.invoiceListOrInvoice = invoiceListOrInvoice;
    }

    public Long getInvoiceDestroyedId() {
        return invoiceDestroyedId;
    }

    public void setInvoiceDestroyedId(Long invoiceDestroyedId) {
        this.invoiceDestroyedId = invoiceDestroyedId;
    }

    public String getDestroyInvoiceReason() {
        return destroyInvoiceReason;
    }

    public void setDestroyInvoiceReason(String destroyInvoiceReason) {
        this.destroyInvoiceReason = destroyInvoiceReason;
    }

    public Long getSplitFromInvoice() {
        return splitFromInvoice;
    }

    public void setSplitFromInvoice(Long splitFromInvoice) {
        this.splitFromInvoice = splitFromInvoice;
    }

    public String getSplitFromToInvoice() {
        return splitFromToInvoice;
    }

    public void setSplitFromToInvoice(String splitFromToInvoice) {
        this.splitFromToInvoice = splitFromToInvoice;
    }

    public Long getSplitToInvoice() {
        return splitToInvoice;
    }

    public void setSplitToInvoice(Long splitToInvoice) {
        this.splitToInvoice = splitToInvoice;
        if (this.splitFromInvoice != null && this.splitToInvoice != null) {
            this.splitFromToInvoice = StringUtils.standardInvoiceString(this.splitFromInvoice) + " - " + StringUtils.standardInvoiceString(this.splitToInvoice);
        } else {
            this.splitFromToInvoice = "";
        }
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
        if (shopName != null) {
            billOwnerName = shopName;
        }
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
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

    public String getStrFromInvoice() {
        return strFromInvoice;
    }

    public void setStrFromInvoice(String strFromInvoice) {
        this.strFromInvoice = strFromInvoice;
    }

    public String getStrToInvoice() {
        return strToInvoice;
    }

    public void setStrToInvoice(String strToInvoice) {
        this.strToInvoice = strToInvoice;
    }
}
