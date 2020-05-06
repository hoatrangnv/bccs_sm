/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import java.io.Serializable;

/**
 *
 * @author kdvt_huynq36
 */
public class PortInside implements Serializable {

    private String provinceCode;
    private String provinceName;
    private String dslamCode;
    private String dslamName;
    private String chassicCode;
    private String chassicName;
    private String ip; //chassic
    private String hostName; //chassic
    private Long slotPosition;
    private Long portID;
    private Long portPosition;
    private Long portStatus;
    private String status; //haint41 12/12/2011 : them truong trang thai port

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

    public PortInside(String provinceCode,
            String provinceName,
            String dslamCode,
            String dslamName,
            String chassicCode,
            String chassicName,
            String ip,
            String hostName,
            Long slotPosition,
            Long portID,
            Long portPosition,
         //   Long portStatus,
            String status) {

        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
        this.dslamCode = dslamCode;
        this.dslamName = dslamName;
        this.chassicCode = chassicCode;
        this.chassicName = chassicName;
        this.ip = ip;
        this.hostName = hostName;
        this.slotPosition = slotPosition;
        this.portID = portID;
        this.portPosition = portPosition;
        //this.portStatus = portStatus;
        this.status = status;
    }

    public Long getPortPosition() {
        return portPosition;
    }

    public void setPortPosition(Long portPosition) {
        this.portPosition = portPosition;
    }

    public String getChassicCode() {
        return chassicCode;
    }

    public void setChassicCode(String chassicCode) {
        this.chassicCode = chassicCode;
    }

    public String getChassicName() {
        return chassicName;
    }

    public void setChassicName(String chassicName) {
        this.chassicName = chassicName;
    }

    public String getDslamCode() {
        return dslamCode;
    }

    public void setDslamCode(String dslamCode) {
        this.dslamCode = dslamCode;
    }

    public String getDslamName() {
        return dslamName;
    }

    public void setDslamName(String dslamName) {
        this.dslamName = dslamName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getPortID() {
        return portID;
    }

    public void setPortID(Long portID) {
        this.portID = portID;
    }

    public Long getPortStatus() {
        return portStatus;
    }

    public void setPortStatus(Long portStatus) {
        this.portStatus = portStatus;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getSlotPosition() {
        return slotPosition;
    }

    public void setSlotPosition(Long slotPosition) {
        this.slotPosition = slotPosition;
    }
}
