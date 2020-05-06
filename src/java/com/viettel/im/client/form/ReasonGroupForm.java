/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import org.apache.struts.action.ActionForm;
import com.viettel.im.database.BO.ReasonGroup;

/**
 *
 * @author NamNX
 */
public class ReasonGroupForm extends ActionForm {

    private long reasonGroupId;
    private String reasonGroupCode;
    private String reasonGroupName;
    private String status;
    private String description;
    private String message;

    public ReasonGroupForm() {
        reasonGroupId = 0L;
        reasonGroupCode = "";
        reasonGroupName = "";
        status = "";
        description = "";
        message = "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReasonGroupCode() {
        return reasonGroupCode;
    }

    public void setReasonGroupCode(String reasonGroupCode) {
        this.reasonGroupCode = reasonGroupCode;
    }

    public long getReasonGroupId() {
        return reasonGroupId;
    }

    public void setReasonGroupId(long reasonGroupId) {
        this.reasonGroupId = reasonGroupId;
    }

    public String getReasonGroupName() {
        return reasonGroupName;
    }

    public void setReasonGroupName(String reasonGroupName) {
        this.reasonGroupName = reasonGroupName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void copyDataFromBO(ReasonGroup reasonGroup) {
        reasonGroupId = reasonGroup.getReasonGroupId();
        reasonGroupCode = reasonGroup.getReasonGroupCode();
        reasonGroupName = reasonGroup.getReasonGroupName();
        status = String.valueOf(reasonGroup.getStatus());
        description = reasonGroup.getDescription();


    }

    public void copyDataToBO(ReasonGroup reasonGroup) {
        reasonGroup.setReasonGroupId(reasonGroupId);
        reasonGroup.setReasonGroupName(reasonGroupName.trim());
        reasonGroup.setReasonGroupCode(reasonGroupCode.trim());
        reasonGroup.setDescription(description.trim());
        reasonGroup.setStatus(Long.valueOf(status));

    }

    public void resetForm() {
        reasonGroupCode = "";
        reasonGroupName = "";
        status = "";
        description = "";
        message = "";
    }
}
