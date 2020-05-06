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
public class InvoiceListBean implements java.io.Serializable {

    private Long invoiceListId;
    private String serialNo;
    private String blockName;
    private String blockNo;
    private Long fromInvoice;
    private Long splitFromInvoice;
    private Long toInvoice;
    private Long splitToInvoice;
    private String fromToInvoice;
    private String splitFromToInvoice;
    private String userCreated;
    private Long status;
    private Date dateCreated;
    private Long numberOfInvoice;
    private Long currInvoiceNo;
    private String userAssign;
    private Date dateAssign;
    private String ownerId;
    private String ownerCode;
    private String ownerName;

    public InvoiceListBean() {
    }


    /* Construct method */
    public InvoiceListBean(Long invoiceListId, String serialNo, String blockName,
            String blockNo, Long fromInvoice, Long toInvoice, Long currInvoiceNo,
            String userCreated, Long status, Date dateCreated,
            String userAssign, Date dateAssign) {

        this.invoiceListId = invoiceListId;
        this.serialNo = serialNo;
        this.blockName = blockName;
        this.blockNo = blockNo;
        this.fromInvoice = fromInvoice;
        this.toInvoice = toInvoice;
        this.currInvoiceNo = currInvoiceNo;
        this.userCreated = userCreated;
        this.status = status;
        this.dateCreated = dateCreated;
        this.fromToInvoice = StringUtils.standardInvoiceString(this.fromInvoice) + " - "
                + StringUtils.standardInvoiceString(this.toInvoice);
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
        this.fromToInvoice = StringUtils.standardInvoiceString(this.fromInvoice) + " - "
                + StringUtils.standardInvoiceString(this.toInvoice);
        this.numberOfInvoice = this.toInvoice - this.fromInvoice + 1;
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

    public Long getCurrInvoiceNo() {
        return currInvoiceNo;
    }

    public void setCurrInvoiceNo(Long currInvoiceNo) {
        this.currInvoiceNo = currInvoiceNo;
    }

    public Long getSplitFromInvoice() {
        return splitFromInvoice;
    }

    public void setSplitFromInvoice(Long splitFromInvoice) {
        this.splitFromInvoice = splitFromInvoice;
    }

    public Long getSplitToInvoice() {
        return splitToInvoice;
    }

    public void setSplitToInvoice(Long splitToInvoice) {
        this.splitToInvoice = splitToInvoice;
        if (this.splitFromInvoice != null && this.splitToInvoice != null) {
            this.splitFromToInvoice = StringUtils.standardInvoiceString(this.splitFromInvoice) + " - "
                    + StringUtils.standardInvoiceString(this.splitToInvoice);
        } else {
            this.splitFromToInvoice = "";
        }
    }

    public String getSplitFromToInvoice() {
        return splitFromToInvoice;
    }

    public void setSplitFromToInvoice(String splitFromToInvoice) {
        this.splitFromToInvoice = splitFromToInvoice;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     *
     * @param invoiceListId
     * @param serialNo
     * @param blockName
     * @param blockNo
     * @param fromInvoice
     * @param toInvoice
     * @param currInvoiceNo
     * @param status
     * @param ownerId
     * @param ownerCode
     * @param ownerName
     */
    public InvoiceListBean(
            Long invoiceListId, String serialNo, String blockName, String blockNo,
            Long fromInvoice, Long toInvoice, Long currInvoiceNo, Long status, String ownerName) {
        this.invoiceListId = invoiceListId;
        this.serialNo = serialNo;
        this.blockName = blockName;
        this.blockNo = blockNo;
        this.fromInvoice = fromInvoice;
        this.toInvoice = toInvoice;
        this.currInvoiceNo = currInvoiceNo;
        this.status = status;
        this.ownerName = ownerName;
    }
}
