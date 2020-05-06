/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author test
 */
public class SwitchesBean {
    private Long switchId;
    private Long equipmentId;
    private String name;
    private String code;
    private String ip;
    private Long status;
    private Long brasId;
    private String equipmentName;
    private String brasName;
    private String vendorName;
    private Long nmsVlan;

    public SwitchesBean(Long switchId, Long equipmentId, String name, String code, String ip, Long status, Long brasId, String equipmentName, String brasName, String vendorName, Long nmsVlan) {
        this.switchId = switchId;
        this.equipmentId = equipmentId;
        this.name = name;
        this.code = code;
        this.ip = ip;
        this.status = status;
        this.brasId = brasId;
        this.equipmentName = equipmentName;
        this.brasName = brasName;
        this.vendorName = vendorName;
        this.nmsVlan = nmsVlan;
    }

    public SwitchesBean() {
    }

    public Long getNmsVlan() {
        return nmsVlan;
    }

    public void setNmsVlan(Long nmsVlan) {
        this.nmsVlan = nmsVlan;
    }



    public Long getBrasId() {
        return brasId;
    }

    public void setBrasId(Long brasId) {
        this.brasId = brasId;
    }

    public String getBrasName() {
        return brasName;
    }

    public void setBrasName(String brasName) {
        this.brasName = brasName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
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

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

}
