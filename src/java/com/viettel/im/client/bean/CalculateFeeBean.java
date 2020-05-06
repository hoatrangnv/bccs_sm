/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import java.util.Date;

/**
 *
 * @author ANHLT
 */
public class CalculateFeeBean implements java.io.Serializable {

    private Long itemid;
    private String itemname;
    private String inputType;
    private Long fee;
    private Long vat;
    private Long commtransid;
    private Long invoiceid;
    private Date billcycle;
    private Long quantity;
    private Long totalmoney;
    private Long taxMoney;
    private Date createDate;
    private String status;
    private Long paystatus;
    private Long approved;

    /**
     * @return the itemid
     */
    public Long getItemid() {
        return itemid;
    }

    /**
     * @param itemid the itemid to set
     */
    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    /**
     * @return the itemname
     */
    public String getItemname() {
        return itemname;
    }

    /**
     * @param itemname the itemname to set
     */
    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    /**
     * @return the fee
     */
    public Long getFee() {
        return fee;
    }

    /**
     * @param fee the fee to set
     */
    public void setFee(Long fee) {
        this.fee = fee;
    }

    /**
     * @return the vat
     */
    public Long getVat() {
        return vat;
    }

    /**
     * @param vat the vat to set
     */
    public void setVat(Long vat) {
        this.vat = vat;
    }

    /**
     * @return the commtransid
     */
    public Long getCommtransid() {
        return commtransid;
    }

    /**
     * @param commtransid the commtransid to set
     */
    public void setCommtransid(Long commtransid) {
        this.commtransid = commtransid;
    }

    /**
     * @return the invoiceid
     */
    public Long getInvoiceid() {
        return invoiceid;
    }

    /**
     * @param invoiceid the invoiceid to set
     */
    public void setInvoiceid(Long invoiceid) {
        this.invoiceid = invoiceid;
    }

    /**
     * @return the billcycle
     */
    public Date getBillcycle() {
        return billcycle;
    }

    /**
     * @param billcycle the billcycle to set
     */
    public void setBillcycle(Date billcycle) {
        this.billcycle = billcycle;
    }

    /**
     * @return the quantity
     */
    public Long getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the totalmoney
     */
    public Long getTotalmoney() {
        return totalmoney;
    }

    /**
     * @param totalmoney the totalmoney to set
     */
    public void setTotalmoney(Long totalmoney) {
        this.totalmoney = totalmoney;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(Long paystatus) {
        this.paystatus = paystatus;
    }

    /**
     * @return the taxMoney
     */
    public Long getTaxMoney() {
        return taxMoney;
    }

    /**
     * @param taxMoney the taxMoney to set
     */
    public void setTaxMoney(Long taxMoney) {
        this.taxMoney = taxMoney;
    }

    /**
     * @return the approved
     */
    public Long getApproved() {
        return approved;
    }

    /**
     * @param approved the approved to set
     */
    public void setApproved(Long approved) {
        this.approved = approved;
    }

    /**
     * @return the inputType
     */
    public String getInputType() {
        return inputType;
    }

    /**
     * @param inputType the inputType to set
     */
    public void setInputType(String inputType) {
        this.inputType = inputType;
    }
}
