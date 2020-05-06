/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

import com.viettel.im.database.BO.Bras;

/**
 *
 * @author Administrator
 */
public class BrasForm {

    private Long brasId;
	private String equipmentId;
	private String code;
	private String name;
	private String ip;
	private String aaaIp;
	private String status;
	private String description;
    private String slot;
    private String port;

    private  String message;

    public BrasForm() {
    }

    public BrasForm(Long brasId, String equipmentId, String code, String name, String ip, String aaaIp, String status, String description, String message) {
        this.brasId = brasId;
        this.equipmentId = equipmentId;
        this.code = code;
        this.name = name;
        this.ip = ip;
        this.aaaIp = aaaIp;
        this.status = status;
        this.description = description;
        this.message = message;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAaaIp() {
        return aaaIp;
    }

    public void setAaaIp(String aaaIp) {
        this.aaaIp = aaaIp;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public void resetForm(){
        this.brasId = 0L;
        this.equipmentId = "";
        this.code = "";
        this.name = "";
        this.ip = "";
        this.aaaIp = "";
        this.status = "";
        this.description = "";
        this.slot = "";
        this.port = "";
        this.message = "";
    }
    public void copyDataToBO(Bras bras){
        bras.setBrasId(this.getBrasId());
        bras.setEquipmentId(Long.parseLong(this.getEquipmentId()));
        bras.setCode(this.getCode().trim());
        bras.setName(this.getName().trim());
        bras.setIp(this.getIp().trim());
        bras.setAaaIp(this.getAaaIp().trim());
        bras.setStatus(Long.parseLong(this.getStatus()));
        bras.setDescription(this.getDescription().trim());
        bras.setSlot(this.getSlot().trim());
        bras.setPort(this.getPort().trim());
    }
    public void copyDataFromBO(Bras bras){
        this.brasId = bras.getBrasId();
        this.equipmentId = String.valueOf(bras.getEquipmentId());
        this.code = bras.getCode();
        this.name = bras.getName();
        this.ip = bras.getIp();
        this.aaaIp = bras.getAaaIp();
        this.status = String.valueOf(bras.getStatus());
        this.description = bras.getDescription();
        this.slot = bras.getSlot();
        this.port = bras.getPort();
    }

}
