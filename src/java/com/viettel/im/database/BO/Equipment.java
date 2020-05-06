/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

/**
 *
 * @author NamNX
 */
public class Equipment implements java.io.Serializable {

    private Long equipmentId;
    private String equipmentType;
    private String code;
    private String name;
    private String description;
    private Long equipmentVendorId;
    private Long status;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Long getEquipmentVendorId() {
        return equipmentVendorId;
    }

    public void setEquipmentVendorId(Long equipmentVendorId) {
        this.equipmentVendorId = equipmentVendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    
    
}
