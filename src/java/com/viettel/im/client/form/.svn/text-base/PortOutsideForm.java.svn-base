/**
 * Copyright YYYY Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.im.client.form;

import org.apache.struts.action.ActionForm;
import com.viettel.im.database.BO.PortOutside;

/**
 *
 * @author haint
 */
public class PortOutsideForm extends ActionForm{
    
    private Long portPosition;
    private String status;
    private String cableBoxCode;
    private Long cableBoxId;
    private Long portOutsideId;

    public PortOutsideForm() {
    }

    public Long getPortOutsideId() {
        return portOutsideId;
    }

    public void setPortOutsideId(Long portOutsideId) {
        this.portOutsideId = portOutsideId;
    }

    public Long getCableBoxId() {
        return cableBoxId;
    }

    public void setCableBoxId(Long cableBoxId) {
        this.cableBoxId = cableBoxId;
    }
    
    public String getCableBoxCode() {
        return cableBoxCode;
    }

    public void setCableBoxCode(String cableBoxCode) {
        this.cableBoxCode = cableBoxCode;
    }

    public Long getPortPosition() {
        return portPosition;
    }

    public void setPortPosition(Long portPosition) {
        this.portPosition = portPosition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PortOutsideForm(Long portPosition, String status) {
        this.portPosition = portPosition;
        this.status = status;
    }

    public PortOutsideForm(Long portPosition, String status, Long portOutsideId) {
        this.portPosition = portPosition;
        this.status = status;
        this.portOutsideId = portOutsideId;
    }
    
    public void resetForm() {
        this.portPosition = null;
        this.status = null;
        this.cableBoxId = this.getCableBoxId();
        this.portOutsideId = null;        
    }
    
    public void copyToBO(PortOutside portOutside) {
        portOutside.setPortOutsideId(this.getPortOutsideId());
        portOutside.setPortPosition(this.portPosition);
        portOutside.setStatus(Long.valueOf(this.status));
        portOutside.setCableBoxId(this.cableBoxId);
    }
       
}
