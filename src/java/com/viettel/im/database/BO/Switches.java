/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

/**
 *
 * @author NamNX
 */
public class Switches implements java.io.Serializable {

    private Long switchId;

    private Long equipmentId;
    private String name;
    private String code;
    private Long nmsVlan;
    private String ip;
    private Long status;
    private Long brasId;

    public Long getBrasId() {
        return brasId;
    }

    public void setBrasId(Long brasId) {
        this.brasId = brasId;
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

    public Long getNmsVlan() {
        return nmsVlan;
    }

    public void setNmsVlan(Long nmsVlan) {
        this.nmsVlan = nmsVlan;
    }



   


}
