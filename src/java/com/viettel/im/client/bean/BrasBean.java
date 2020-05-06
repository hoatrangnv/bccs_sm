/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author AnDv
 */
public class BrasBean implements java.io.Serializable{
    private Long brasId;
	private Long equipmentId;
	private String code;
	private String name;
	private String ip;
	private String aaaIp;
	private Long status;
	private String description;
    private String slot;
    private String port;
    private String equipmentName;

    public BrasBean() {
    }
    

    public BrasBean(Long brasId, Long equipmentId, String code, String name, String ip, String aaaIp,
            Long status, String description, String slot, String port, String equipmentName) {
        this.brasId = brasId;
        this.equipmentId = equipmentId;
        this.code = code;
        this.name = name;
        this.ip = ip;
        this.aaaIp = aaaIp;
        this.status = status;
        this.description = description;
        this.slot = slot;
        this.port = port;
        this.equipmentName = equipmentName;
    }

    public String getAaaIp() {
        return aaaIp;
    }

    public void setAaaIp(String aaaIp) {
        this.aaaIp = aaaIp;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }
    
}
