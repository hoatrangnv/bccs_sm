/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.database.BO.EquipmentVendor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author ducdd
 *
 */
public class EquipmentVendorForm extends ActionForm {

    private Long equipmentVendorId;
    private String vendorCode;
    private String vendorName;
    private String description;
    private Long status;
    private String equipmentVendorMessage;

    // Constructors
    /** default constructor */
    public EquipmentVendorForm() {
        resetForm();
    }

    public void resetForm() {
        this.equipmentVendorId = 0L;
        this.vendorCode = "";
        this.vendorName = "";
        this.description = "";
        this.status = null;
        this.equipmentVendorMessage = "";
    }

    public void copyDataToBO(EquipmentVendor equipmentVendor) {
        try {
            equipmentVendor.setEquipmentVendorId(this.getEquipmentVendorId());
            equipmentVendor.setDescription(this.getDescription());
            equipmentVendor.setVendorCode(this.getVendorCode());
            equipmentVendor.setVendorName(this.getVendorName());
            equipmentVendor.setStatus(this.status);
        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void copyDataFromBO(EquipmentVendor equipmentVendor) {

        try {
            this.equipmentVendorId = equipmentVendor.getEquipmentVendorId();
            this.description = equipmentVendor.getDescription();
            this.vendorCode = equipmentVendor.getVendorCode();
            this.vendorName = equipmentVendor.getVendorName();
            this.status=equipmentVendor.getStatus();
        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    // Property accessors

    

    public String getEquipmentVendorMessage() {
        return equipmentVendorMessage;
    }

    public void setEquipmentVendorMessage(String equipmentVendorMessage) {
        this.equipmentVendorMessage = equipmentVendorMessage;
    }

    public Long getEquipmentVendorId() {
        return this.equipmentVendorId;
    }

    public void setEquipmentVendorId(Long equipmentVendorId) {
        this.equipmentVendorId = equipmentVendorId;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
