package com.viettel.im.client.form;

import com.viettel.im.database.BO.Reason;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author namnx
 * date 07/03/2009
 *
 */
public class ReasonForm extends ActionForm {

    private Long reasonId;
    private String reasonCode;
    private String reasonName;
    private String reasonType;
    private String reasonDescription;
    private String reasonStatus;


    private String reasonMessage;

    public ReasonForm() {
        reasonId = 0L;
        reasonCode = "";
        reasonName = "";
        reasonType = "";
        reasonDescription = "";
        reasonStatus = "";
        reasonMessage = "";


    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    

    public String getReasonDescription() {
        return reasonDescription;
    }

    public void setReasonDescription(String reasonDescription) {
        this.reasonDescription = reasonDescription;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getReasonStatus() {
        return reasonStatus;
    }

    public void setReasonStatus(String reasonStatus) {
        this.reasonStatus = reasonStatus;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    public String getReasonMessage() {
        return reasonMessage;
    }

    public void setReasonMessage(String reasonMessage) {
        this.reasonMessage = reasonMessage;
    }


    public void copyDataFromBO(Reason reason) {
        this.setReasonId(reason.getReasonId());
        this.setReasonCode(reason.getReasonCode());
        this.setReasonType(reason.getReasonType());
        this.setReasonStatus(reason.getReasonStatus());
        this.setReasonName(reason.getReasonName());
        this.setReasonDescription(reason.getReasonDescription());

    }

    public void copyDataToBO(Reason reason) {
        reason.setReasonId(this.getReasonId());
        reason.setReasonCode(this.getReasonCode().trim());
        reason.setReasonType(this.getReasonType());
        reason.setReasonStatus(this.getReasonStatus());
        reason.setReasonName(this.getReasonName().trim());
        reason.setReasonDescription(this.getReasonDescription().trim());
    }
    public void resetForm(){
        reasonId = 0L;
        reasonCode = "";
        reasonName = "";
        reasonType = "";
        reasonDescription = "";
        reasonStatus = "";
        reasonMessage = "";

    }
}
