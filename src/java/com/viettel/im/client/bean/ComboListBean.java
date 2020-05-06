/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author MrSun
 */
public class ComboListBean {

    Long objId;
    String objCode;
    String objName;
    Long objType;
    
    Long priceId;
    Long price;

    public ComboListBean() {
    }


    public ComboListBean(Long priceId, Long price) {
        this.priceId = priceId;
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Long getObjId() {
        return objId;
    }

    public void setObjId(Long objId) {
        this.objId = objId;
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

    public Long getObjType() {
        return objType;
    }

    public void setObjType(Long objType) {
        this.objType = objType;
    }
    
    
    
}
