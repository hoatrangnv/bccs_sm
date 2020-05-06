/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import java.math.BigDecimal;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;

/**
 *
 * @author kdvt_phuoctv
 */
public class LookupMaxDebitBean {

    private Long channelId;
    private String channelCode;
    private String channelName;
    private Double currentDebit;
    private String strCurrentDebit;
    private Double maxDebit;
    private String strMaxDebit;
    private String ownerType;

    public LookupMaxDebitBean(Long channelId, String channelCode, String channelName) {
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.channelName = channelName;
    }

    public LookupMaxDebitBean(Long channelId, String channelCode, String channelName, String ownerType) {
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.channelName = channelName;
        this.ownerType = ownerType;
    }

    public LookupMaxDebitBean(Long channelId, String channelCode, String channelName, Double currentDebit, Double maxDebit, String ownerType) {
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.channelName = channelName;
        this.currentDebit = currentDebit;
        this.maxDebit = maxDebit;
        this.ownerType = ownerType;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Double getCurrentDebit() {
        try {
            currentDebit = new BaseDAOAction().getCurrentDebit(channelId, Long.valueOf(ownerType));// shop
            return currentDebit;
        } catch (Exception e) {
            return null;
        }
    }

    public void setCurrentDebit(Double currentDebit) {
        this.currentDebit = currentDebit;
    }

    public Double getMaxDebit() {
        try {
            maxDebit = new BaseDAOAction().getMaxDebit(channelId, Long.valueOf(ownerType));// shop
            return maxDebit;
        } catch (Exception e) {
            return null;
        }
    }

    public void setMaxDebit(Double maxDebit) {
        this.maxDebit = maxDebit;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getStrCurrentDebit() {
        return strCurrentDebit;
    }

    public void setStrCurrentDebit(String strCurrentDebit) {
        this.strCurrentDebit = strCurrentDebit;
    }

    public String getStrMaxDebit() {
        return strMaxDebit;
    }

    public void setStrMaxDebit(String strMaxDebit) {
        this.strMaxDebit = strMaxDebit;
    }
}
