package com.viettel.im.client.form;

import java.util.Date;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tuannv
 * date 18/02/2009
 *
 */
public class PayDepositForm extends ActionForm {

    private String shopCode;
    private String shopName;
    private String startDate;
    private String endDate;
    private String goodName;
    private String goodCount;
    private String unit;
    private String priceUnit;
    private String moneyTotal;
    
    private String payCode;
    private String payDate;
    private String resonCode;
    private String senderName;

    public PayDepositForm() {
    }

    /**
     * @return the shopCode
     */
    public String getShopCode() {
        return shopCode;
    }

    /**
     * @param shopCode the shopCode to set
     */
    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    /**
     * @return the shopName
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * @param shopName the shopName to set
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the goodName
     */
    public String getGoodName() {
        return goodName;
    }

    /**
     * @param goodName the goodName to set
     */
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    /**
     * @return the goodCount
     */
    public String getGoodCount() {
        return goodCount;
    }

    /**
     * @param goodCount the goodCount to set
     */
    public void setGoodCount(String goodCount) {
        this.goodCount = goodCount;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the priceUnit
     */
    public String getPriceUnit() {
        return priceUnit;
    }

    /**
     * @param priceUnit the priceUnit to set
     */
    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    /**
     * @return the moneyTotal
     */
    public String getMoneyTotal() {
        return moneyTotal;
    }

    /**
     * @param moneyTotal the moneyTotal to set
     */
    public void setMoneyTotal(String moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    /**
     * @return the payCode
     */
    public String getPayCode() {
        return payCode;
    }

    /**
     * @param payCode the payCode to set
     */
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    /**
     * @return the payDate
     */
    public String getPayDate() {
        return payDate;
    }

    /**
     * @param payDate the payDate to set
     */
    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    /**
     * @return the resonCode
     */
    public String getResonCode() {
        return resonCode;
    }

    /**
     * @param resonCode the resonCode to set
     */
    public void setResonCode(String resonCode) {
        this.resonCode = resonCode;
    }

    /**
     * @return the senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * @param senderName the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
