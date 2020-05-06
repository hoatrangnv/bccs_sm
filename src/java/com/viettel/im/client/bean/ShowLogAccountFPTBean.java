/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author User
 */
public class ShowLogAccountFPTBean {
    private Long iD;
    private Long agentId;
    private String fieid;
    private String currentValue;
    private String currentValueName;
    private String newValue;
    private String newValueName;
    private String datetime;
    private String user_name;
    private String host;
    private String reasonName;

    public ShowLogAccountFPTBean() {
    }

    public ShowLogAccountFPTBean(Long iD, Long agentId, String fieid, String currentValue, String currentValueName, String newValue, String newValueName, String datetime, String user_name, String host, String reasonName) {
        this.iD = iD;
        this.agentId = agentId;
        this.fieid = fieid;
        this.currentValue = currentValue;
        this.currentValueName = currentValueName;
        this.newValue = newValue;
        this.newValueName = newValueName;
        this.datetime = datetime;
        this.user_name = user_name;
        this.host = host;
        this.reasonName = reasonName;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getCurrentValueName() {
        return currentValueName;
    }

    public void setCurrentValueName(String currentValueName) {
        this.currentValueName = currentValueName;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getFieid() {
        return fieid;
    }

    public void setFieid(String fieid) {
        this.fieid = fieid;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getID() {
        return iD;
    }

    public void setID(Long iD) {
        this.iD = iD;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getNewValueName() {
        return newValueName;
    }

    public void setNewValueName(String newValueName) {
        this.newValueName = newValueName;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    
}
