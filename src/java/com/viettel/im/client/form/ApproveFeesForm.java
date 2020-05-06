/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author tuan
 */
public class ApproveFeesForm extends ActionForm {

    private String objectCode;
    private String objectName;
    private String shopCode;
    private String shopName;
    private String billcycle;
    private int criterion;
    private String actionCode;
    private String actionName;
    private Long state;
    private Long payTypeCode;
    private Long channelTypeId;
    private String[] aReceiveID;
    private String message;

    public void ResetFormSearch() {
        setObjectCode(null);
        setObjectName(null);
        setShopCode(null);
        setShopName(null);
//        setBillcycle(null);
        setActionCode(null);
        setActionName(null);
        setChannelTypeId(null);
        setPayTypeCode(null);
        setState(null);
        setMessage(null);
        setAReceiveID(null);
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    /**
     * @return the objectCode
     */
    public String getObjectCode() {
        return objectCode;
    }

    /**
     * @param objectCode the objectCode to set
     */
    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    /**
     * @return the objectName
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * @param objectName the objectName to set
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
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

    public String getBillcycle() {
        return billcycle;
    }

    public void setBillcycle(String billcycle) {
        this.billcycle = billcycle;
    }

    /**
     * @return the criterion
     */
    public int getCriterion() {
        return criterion;
    }

    /**
     * @param criterion1 the criterion1 to set
     */
    public void setCriterion(int criterion) {
        this.criterion = criterion;
    }

    /**
     * @return the actionCode
     */
    public String getActionCode() {
        return actionCode;
    }

    /**
     * @param actionCode the actionCode to set
     */
    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    /**
     * @return the actionName
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * @param actionName the actionName to set
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    /**
     * @return the aReceiveID
     */
    public String[] getAReceiveID() {
        return aReceiveID;
    }

    /**
     * @param aReceiveID the aReceiveID to set
     */
    public void setAReceiveID(String[] aReceiveID) {
        this.aReceiveID = aReceiveID;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the payTypeCode
     */
    public Long getPayTypeCode() {
        return payTypeCode;
    }

    /**
     * @param payTypeCode the payTypeCode to set
     */
    public void setPayTypeCode(Long payTypeCode) {
        this.payTypeCode = payTypeCode;
    }
}
