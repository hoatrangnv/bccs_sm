package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.CommCounterParams;
import com.viettel.im.database.BO.CommCounters;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author namnt
 * date 07/03/2009
 *
 */
public class CommCountersForm extends ActionForm {

    private long counterId;
    private String counterCode;
    private String counterName;
    private String functionName;
    private String createDate;
    private String status;
    private String detailfunctionName;
    private String reportTemplate;
    private String message;
    private String paramName;
    private String dataType;

    public long getCounterId() {
        return counterId;
    }

    public void setCounterId(long counterId) {
        this.counterId = counterId;
    }
  
    /**
     * @return the counterCode
     */
    public String getCounterCode() {
        return counterCode;
    }

    /**
     * @param counterCode the counterCode to set
     */
    public void setCounterCode(String counterCode) {
        this.counterCode = counterCode;
    }

    /**
     * @return the counterName
     */
    public String getCounterName() {
        return counterName;
    }

    /**
     * @param counterName the counterName to set
     */
    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    /**
     * @return the functionName
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * @param functionName the functionName to set
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the detailfunctionName
     */
    public String getDetailfunctionName() {
        return detailfunctionName;
    }

    /**
     * @param detailfunctionName the detailfunctionName to set
     */
    public void setDetailfunctionName(String detailfunctionName) {
        this.detailfunctionName = detailfunctionName;
    }

    /**
     * @return the reportTemplate
     */
    public String getReportTemplate() {
        return reportTemplate;
    }

    /**
     * @param reportTemplate the reportTemplate to set
     */
    public void setReportTemplate(String reportTemplate) {
        this.reportTemplate = reportTemplate;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public void resetForm() {
        this.setCounterId(0);
        this.counterCode = "";
        this.counterName = "";
        this.functionName = "";
        this.createDate = "";
        this.setStatus("");
        this.setMessage("");
        this.detailfunctionName = "";
        this.reportTemplate = "";


    }
    //   <!-- Break here -->

    public void copyDataToBO(CommCounters CommCounters) {
        CommCounters.setCounterCode(this.getCounterCode());
        CommCounters.setCounterName(this.getCounterName());
        CommCounters.setDetailFunctionName(this.getDetailfunctionName());
        CommCounters.setFunctionName(this.getFunctionName());
        CommCounters.setReportTemplate(this.getReportTemplate());
        CommCounters.setStatus(this.getStatus());
        try {
            CommCounters.setCreateDate(DateTimeUtils.convertStringToDate(this.getCreateDate()));
        } catch (Exception ex) {
            Logger.getLogger(CommCountersForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void copyDataFromBO(CommCounters CommCounters) {
        this.setCounterId(CommCounters.getCounterId());
        this.setCounterCode(CommCounters.getCounterCode());
        this.setCounterName(CommCounters.getCounterName());
        this.setDetailfunctionName(CommCounters.getDetailFunctionName());
        this.setFunctionName(CommCounters.getFunctionName());
        this.setReportTemplate(CommCounters.getReportTemplate());
        this.setStatus(CommCounters.getStatus());
        try {
            this.setCreateDate(DateTimeUtils.convertDateTimeToString(CommCounters.getCreateDate()));
        } catch (Exception ex) {
            Logger.getLogger(CommCountersForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @return the paramName
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * @param paramName the paramName to set
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

//CompleteD
}