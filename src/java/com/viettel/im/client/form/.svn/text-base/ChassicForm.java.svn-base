/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Chassic;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author Administrator
 */
public class ChassicForm extends ActionForm {

    private Long chassicId;
    private Long dslamId;
    private Long equipmentId;
    private String code;
    private String hostName;
    private String ip;
    private String vlanStart;
    private String vlanStop;
    private Long nmsVlan;
    private String chassicCode;
    private String switchCode;
    private Long switchId;
    private Long switchIdKey;
    private String channelNumber;
    private String totalSlot;
    private String totalPort;
    private String usedPort;
    private String invalidPort;
    private String setupDate;
    private String startupDate;
    private Long status;
    private String chassicType;
    private String subSlot;
    private String portBras;
    private String message;
    private String macAddress;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public ChassicForm() {
    }

    
    
    public String getChassicCode() {
        return chassicCode;
    }

    public void setChassicCode(String chassicCode) {
        this.chassicCode = chassicCode;
    }

    public String getSwitchCode() {
        return switchCode;
    }

    public void setSwitchCode(String switchCode) {
        this.switchCode = switchCode;
    }

    public Long getChassicId() {
        return chassicId;
    }

    public void setChassicId(Long chassicId) {
        this.chassicId = chassicId;
    }

    public String getChassicType() {
        return chassicType;
    }

    public void setChassicType(String chassicType) {
        this.chassicType = chassicType;
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber;
    }

    public String getInvalidPort() {
        return invalidPort;
    }

    public void setInvalidPort(String invalidPort) {
        this.invalidPort = invalidPort;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getNmsVlan() {
        return nmsVlan;
    }

    public void setNmsVlan(Long nmsVlan) {
        this.nmsVlan = nmsVlan;
    }

    public String getPortBras() {
        return portBras;
    }

    public void setPortBras(String portBras) {
        this.portBras = portBras;
    }

    public String getSubSlot() {
        return subSlot;
    }

    public void setSubSlot(String subSlot) {
        this.subSlot = subSlot;
    }

    public String getTotalPort() {
        return totalPort;
    }

    public void setTotalPort(String totalPort) {
        this.totalPort = totalPort;
    }

    public String getTotalSlot() {
        return totalSlot;
    }

    public void setTotalSlot(String totalSlot) {
        this.totalSlot = totalSlot;
    }

    public String getUsedPort() {
        return usedPort;
    }

    public void setUsedPort(String usedPort) {
        this.usedPort = usedPort;
    }

    public String getVlanStart() {
        return vlanStart;
    }

    public void setVlanStart(String vlanStart) {
        this.vlanStart = vlanStart;
    }

    public String getVlanStop() {
        return vlanStop;
    }

    public void setVlanStop(String vlanStop) {
        this.vlanStop = vlanStop;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getDslamId() {
        return dslamId;
    }

    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
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

    public String getSetupDate() {
        return setupDate;
    }

    public void setSetupDate(String setupDate) {
        this.setupDate = setupDate;
    }

    public String getStartupDate() {
        return startupDate;
    }

    public void setStartupDate(String startupDate) {
        this.startupDate = startupDate;
    }

//    public Date getSetupDate() {
//        return setupDate;
//    }
//
//    public void setSetupDate(Date setupDate) {
//        this.setupDate = setupDate;
//    }
//
//    public Date getStartupDate() {
//        return startupDate;
//    }
//
//    public void setStartupDate(Date startupDate) {
//        this.startupDate = startupDate;
//    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void resetForm() {
        this.chassicId = 0L;
        this.chassicType = "";
        this.code = "";
        this.channelNumber = "";
        this.dslamId = 0L;
        this.equipmentId = 0L;
        this.hostName = "";
        this.invalidPort = "";
        this.ip = "";
        this.nmsVlan = null;
        this.portBras = "";
        this.setupDate = null;
        this.startupDate = null;
        this.status = null;
        this.subSlot = "";
        this.switchId = 0L;
        this.totalPort = "";
        this.totalSlot = "";
        this.usedPort = "";
        this.vlanStart = "";
        this.vlanStop = "";
        this.message = "";

        this.chassicCode = "";
        this.switchCode = "";


    }

    public void CopyDataToBO(Chassic chassic) {
        if (this.getChannelNumber() != null && !this.getChannelNumber().trim().equals("")) {
            chassic.setChannelNumber(Long.parseLong(this.channelNumber));
        }
        chassic.setChassicType(this.chassicType);
        chassic.setChassicId(this.chassicId);
        chassic.setCode(this.code);
        chassic.setDslamId(this.dslamId);
        chassic.setEquipmentId(this.equipmentId);
        chassic.setHostName(this.hostName);
        if (this.getInvalidPort() != null && !this.getInvalidPort().trim().equals("")) {
            chassic.setInvalidPort(Long.parseLong(this.invalidPort));
        }
        chassic.setIp(this.ip);
        if (this.getNmsVlan() != null) {
            chassic.setNmsVlan((this.nmsVlan));
        }
        if (this.getPortBras() != null && !this.getPortBras().trim().equals("")) {
            chassic.setPortBras(Long.parseLong(this.portBras));
        }
//        chassic.setSetupDate(this.setupDate);
//        chassic.setStartupDate(this.startupDate);
        try {
            chassic.setSetupDate(DateTimeUtils.convertStringToDate(this.setupDate));
            chassic.setStartupDate(DateTimeUtils.convertStringToDate(this.startupDate));
        } catch (Exception ex) {
            Logger.getLogger(ChassicForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        chassic.setSwitchId(this.switchId);


        chassic.setStatus(this.status);
        if (this.getSubSlot() != null && !this.getSubSlot().trim().equals("")) {
            chassic.setSubSlot(Long.parseLong(this.subSlot));
        }
        if (this.getTotalPort() != null && !this.getTotalPort().trim().equals("")) {
            chassic.setTotalPort(Long.parseLong(this.totalPort));
        }
        if (this.getTotalSlot() != null && !this.getTotalSlot().trim().equals("")) {
            chassic.setTotalSlot(Long.parseLong(this.totalSlot));
        }
        if (this.getUsedPort() != null && !this.getUsedPort().trim().equals("")) {
            chassic.setUsedPort(Long.parseLong(this.usedPort));
        }
        if (this.getVlanStart() != null && !this.getVlanStart().trim().equals("")) {
            chassic.setVlanStart(Long.parseLong(this.vlanStart));
        }
        if (this.getVlanStop() != null && !this.getVlanStop().trim().equals("")) {
            chassic.setVlanStop(Long.parseLong(this.vlanStop));
        }
        chassic.setMacAddress(this.getMacAddress());
    }

    public void CopyDataFromBO(Chassic chassic) {
        this.channelNumber = (chassic.getChannelNumber() == null) ? "" : String.valueOf(chassic.getChannelNumber());
        this.chassicType = chassic.getChassicType();
        this.chassicId = chassic.getChassicId();
        this.code = chassic.getCode();
        this.dslamId = chassic.getDslamId();
        this.equipmentId = chassic.getEquipmentId();
        this.hostName = chassic.getHostName();
        this.invalidPort = (chassic.getInvalidPort() == null) ? "" : String.valueOf(chassic.getInvalidPort());
        this.ip = chassic.getIp();
        this.nmsVlan = chassic.getNmsVlan() ;
        this.portBras = (chassic.getPortBras() == null) ? "" : String.valueOf(chassic.getPortBras());
//        this.setupDate = chassic.getSetupDate();
//        this.startupDate = chassic.getStartupDate();
        try {
            this.setupDate = DateTimeUtils.convertDateToString(chassic.getSetupDate());
            this.startupDate = DateTimeUtils.convertDateToString(chassic.getStartupDate());
        } catch (Exception ex) {
            Logger.getLogger(ChassicForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.status = chassic.getStatus();
        this.subSlot = (chassic.getSubSlot() == null) ? "" : String.valueOf(chassic.getSubSlot());
        this.switchId = chassic.getSwitchId();
        this.switchIdKey = chassic.getSwitchId();
        this.totalPort = (chassic.getTotalPort() == null) ? "" : String.valueOf(chassic.getTotalPort());
        this.totalSlot = (chassic.getTotalSlot() == null) ? "" : String.valueOf(chassic.getTotalSlot());
        this.vlanStart = (chassic.getVlanStart() == null) ? "" : String.valueOf(chassic.getVlanStart());
        this.usedPort = (chassic.getUsedPort() == null) ? "" : String.valueOf(chassic.getUsedPort());
        this.vlanStop = (chassic.getVlanStop() == null) ? "" : String.valueOf(chassic.getVlanStop());

        this.macAddress = chassic.getMacAddress();
    }

    /**
     * @return the switchIdKey
     */
    public Long getSwitchIdKey() {
        return switchIdKey;
    }

    /**
     * @param switchIdKey the switchIdKey to set
     */
    public void setSwitchIdKey(Long switchIdKey) {
        this.switchIdKey = switchIdKey;
    }
}
