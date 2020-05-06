/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

/**
 *
 * @author User
 */
public class ViewStaffOnOffBean implements java.io.Serializable {

    private String shopCode;
    private String staffCode;
    private Long channelType;
    private Long action;

    public ViewStaffOnOffBean() {
    }

    public Long getAction() {
        return action;
    }

    public void setAction(Long action) {
        this.action = action;
    }

    public Long getChannelType() {
        return channelType;
    }

    public void setChannelType(Long channelType) {
        this.channelType = channelType;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }
}
