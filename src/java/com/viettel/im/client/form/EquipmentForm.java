/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.database.BO.Equipment;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author ducdd
 *
 */
public class EquipmentForm extends ActionForm {

    private Long equipmentId;
    private String code;
    private String name;
    private String description;
    private String status;
    private String equipmentType;
    private String equipmentVendorId;
    private String equipmentVendorName;
    private String equipmentMessage;
    private String equipmentTypeName;

    // Constructors
    /** default constructor */
    public EquipmentForm() {
        resetForm();
    }

    public void resetForm() {
        this.equipmentId = 0L;
        this.equipmentType = "";
        this.code = "";
        this.name = "";
        this.description = "";
        this.status = "";
        this.equipmentVendorId = "";
        this.equipmentVendorName = "";
        this.equipmentMessage = "";
        this.equipmentTypeName = "";
    }

    public void copyDataToBO(Equipment equipment) {
        try {
            equipment.setEquipmentId(this.getEquipmentId());
            equipment.setEquipmentType(this.getEquipmentType());
            equipment.setCode(this.getCode());
            equipment.setName(this.getName());
            equipment.setStatus(Long.parseLong(this.status));
            equipment.setDescription(this.getDescription());
            equipment.setEquipmentVendorId(Long.parseLong(this.getEquipmentVendorId()));
        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void copyDataFromBO(Equipment equipment) {

        try {
            this.equipmentId = equipment.getEquipmentId();
            this.equipmentType = equipment.getEquipmentType();
            this.code = equipment.getCode();
            this.name = equipment.getName();
            this.description = equipment.getDescription();
            this.status = String.valueOf(equipment.getStatus());
            this.equipmentVendorId = String.valueOf(equipment.getEquipmentVendorId());
        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
    }

    public String getEquipmentVendorName() {
        return equipmentVendorName;
    }

    public void setEquipmentVendorName(String equipmentVendorName) {
        this.equipmentVendorName = equipmentVendorName;
    }

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

    public String getEquipmentMessage() {
        return equipmentMessage;
    }

    public void setEquipmentMessage(String equipmentMessage) {
        this.equipmentMessage = equipmentMessage;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentVendorId() {
        return equipmentVendorId;
    }

    public void setEquipmentVendorId(String equipmentVendorId) {
        this.equipmentVendorId = equipmentVendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
