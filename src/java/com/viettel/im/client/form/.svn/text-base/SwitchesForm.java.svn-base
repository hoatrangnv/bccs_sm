/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.database.BO.Switches;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author NamNX
 */
public class SwitchesForm extends ActionForm {

    private Long switchId;
    private String equipmentId;
    private String name;
    private String code;
    private String ip;
    private Long status;
    private String brasId;
    private Long nmsVlan;

    public String getBrasId() {
        return brasId;
    }

    public void setBrasId(String brasId) {
        this.brasId = brasId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public Long getSwitchId() {
        return switchId;
    }

    public void setSwitchId(Long switchId) {
        this.switchId = switchId;
    }

    public void copyDataFromBO(Switches Switches) {
        this.brasId = String.valueOf(Switches.getBrasId());
        this.code = Switches.getCode();
        this.equipmentId = String.valueOf(Switches.getEquipmentId());
        this.ip = Switches.getIp();
        this.name = Switches.getName();
        this.status = Switches.getStatus();
        this.switchId = Switches.getSwitchId();
        this.nmsVlan = Switches.getNmsVlan();


    }

    public void copyDataToBO(Switches bo) {
        bo.setBrasId(Long.parseLong(this.getBrasId()));
        bo.setCode(this.getCode().trim());
        bo.setEquipmentId(Long.parseLong(this.getEquipmentId()));
        bo.setIp(this.getIp().trim());
        bo.setStatus(this.getStatus());
        bo.setSwitchId(this.getSwitchId());
        bo.setName(this.getName().trim());
        bo.setNmsVlan(this.getNmsVlan());



    }

    public void resetForm() {
        this.brasId = "";
        this.code = "";
        this.equipmentId = "";
        this.ip = "";
        this.name = "";
        this.status = null;
        this.switchId = 0L;
        this.nmsVlan = null;
    }

    public Long getNmsVlan() {
        return nmsVlan;
    }

    public void setNmsVlan(Long nmsVlan) {
        this.nmsVlan = nmsVlan;
    }
}
