/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

/**
 *
 * @author AnDV
 */
public class LogDistributeIsdnBean implements java.io.Serializable {

    private Long Id;
    private String serviceType;
    private String isdnType;
    private String Idsn;
    private String shopCode;
    private String fromShopCode;
    private String newIsdnType;
    private String logType = "";
     private String description;
    public static String ERROR = "ERROR";
    public static String WARINING = "WARNING";

    public LogDistributeIsdnBean() {
    }

    public String getIdsn() {
        return Idsn;
    }

    public void setIdsn(String Idsn) {
        this.Idsn = Idsn;
    }

    public String getFromShopCode() {
        return fromShopCode;
    }

    public void setFromShopCode(String fromShopCode) {
        this.fromShopCode = fromShopCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   

    public String getIsdnType() {
        return isdnType;
    }

    public void setIsdnType(String isdnType) {
        this.isdnType = isdnType;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getNewIsdnType() {
        return newIsdnType;
    }

    public void setNewIsdnType(String newIsdnType) {
        this.newIsdnType = newIsdnType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

   

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }


}
