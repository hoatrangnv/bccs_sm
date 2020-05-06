/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class BtsDKTT implements java.io.Serializable {
    
    private Long btsId;
    private String btsCode;
    private String areaCode;
    private String fullAddress;
    private String isDelete;
    private String type;
    private String btsType;
    private String areaType;
    private String status;

    public BtsDKTT() {
    }

    public Long getBtsId() {
        return btsId;
    }

    public void setBtsId(Long btsId) {
        this.btsId = btsId;
    }

    public String getBtsCode() {
        return btsCode;
    }

    public void setBtsCode(String btsCode) {
        this.btsCode = btsCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBtsType() {
        return btsType;
    }

    public void setBtsType(String btsType) {
        this.btsType = btsType;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
