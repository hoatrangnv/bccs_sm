/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author nguyentuan
 */
public class ActionInItemTempTableBean implements Serializable {

    private Long staffId;
    private String staffName;
    private Long shopId;
    private String shopName;
    List<ActionInItemTemp> listItems;

    public List<ActionInItemTemp> getListItems() {
        return listItems;
    }

    public void setListItems(List<ActionInItemTemp> listItems) {
        this.listItems = listItems;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}

