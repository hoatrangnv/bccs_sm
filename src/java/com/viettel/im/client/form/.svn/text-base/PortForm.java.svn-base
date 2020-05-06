/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Port;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts.action.ActionForm;


/**
 * @author ANHLT
 */
public class PortForm extends ActionForm {

    private Long portId;
    private String vlanId;
    private Long slotId;
    private String portPosition;
    private Long status;
    private String message;
    private String slotKey;
    private String slotIdKey;
    private String createDate;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

   

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    public String getPortPosition() {
        return portPosition;
    }

    public void setPortPosition(String portPosition) {
        this.portPosition = portPosition;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public String getSlotIdKey() {
        return slotIdKey;
    }

    public void setSlotIdKey(String slotIdKey) {
        this.slotIdKey = slotIdKey;
    }

    public String getSlotKey() {
        return slotKey;
    }

    public void setSlotKey(String slotKey) {
        this.slotKey = slotKey;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getVlanId() {
        return vlanId;
    }

    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

    public void resetForm() {
        this.setStatus(null);
        this.setMessage("");
        this.slotId = 0L;
        this.status = null;
        this.portId = 0L;
        this.vlanId = "";
        this.portPosition = "";
        this.createDate = "";
        
    }

    public void copyDataToBO(Port port) {
        port.setPortId(this.getPortId());

        if (this.getVlanId() != null && !this.getVlanId().trim().equals("")) {
            port.setVlanId(Long.parseLong(this.getVlanId()));
        }

        if (this.getPortPosition() != null && !this.getPortPosition().trim().equals("")) {
            port.setPortPosition(Long.parseLong(this.getPortPosition()));
        }
        port.setSlotId(this.getSlotId());
        port.setStatus(this.getStatus());
        try {
            port.setCreateDate(DateTimeUtils.convertStringToDate(this.getCreateDate()));

        } catch (Exception ex) {

            Logger.getLogger(PortForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void copyDataFromBO(Port p) {
        this.setPortId(p.getPortId());
        this.setVlanId((p.getVlanId() == null) ? "" : String.valueOf(p.getVlanId()));
        this.setSlotId(p.getSlotId());
        this.setPortPosition((p.getPortPosition() == null) ? "" : String.valueOf(p.getPortPosition()));
        this.setStatus(p.getStatus());
        try {
            this.createDate = DateTimeUtils.convertDateToString(p.getCreateDate());
        } catch (Exception ex) {
            Logger.getLogger(PortForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
   