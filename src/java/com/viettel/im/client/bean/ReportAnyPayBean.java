/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author MrSun
 */
public class ReportAnyPayBean {

    private Long objType;
    private String objCode;
    private String objName;
    private String objIsdn;
    private String transType;
    private Long countIsdn;
    private Long sumTrans;
    private Long sumTarIsdn;
    private Long sumAmount;

    public ReportAnyPayBean() {
    }

    public Long getCountIsdn() {
        return countIsdn;
    }

    public void setCountIsdn(Long countIsdn) {
        this.countIsdn = countIsdn;
    }


    public Long getObjType() {
        return objType;
    }

    public void setObjType(Long objType) {
        this.objType = objType;
    }

    
    public String getObjIsdn() {
        return objIsdn;
    }

    public void setObjIsdn(String objIsdn) {
        this.objIsdn = objIsdn;
    }

    public String getObjCode() {
        return objCode;
    }

    public void setObjCode(String objCode) {
        this.objCode = objCode;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public Long getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(Long sumAmount) {
        this.sumAmount = sumAmount;
    }

    public Long getSumTrans() {
        return sumTrans;
    }

    public void setSumTrans(Long sumTrans) {
        this.sumTrans = sumTrans;
    }

    public Long getSumTarIsdn() {
        return sumTarIsdn;
    }

    public void setSumTarIsdn(Long sumTarIsdn) {
        this.sumTarIsdn = sumTarIsdn;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    
}
