/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author User
 */
public class LogAssignIsdnPriceBean implements java.io.Serializable {

    //STT
    private Long id;
    //ISDN
    private String isdn;
    //Gia
    private String price;
    //ShopCode
    private String shopCode;
    //Tap luat
    private String filterType;
    //Bo luat
    private String rule;
    //Mo ta loi
    private String description;
    //loai log
    //ERROR, WARNING
    private String logType="";
    public static String ERROR="ERROR";
    public static String WARINING="WARNING";


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    


    
}
