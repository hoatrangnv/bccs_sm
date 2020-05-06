package com.viettel.im.database.BO;

/**
 * InvoiceList entity. @author MyEclipse Persistence Tools
 */
public class Reason implements java.io.Serializable {


    // Fields    
    private Long reasonId;
    private String reasonCode;
    private String reasonName;
    private String reasonStatus;
    private String reasonDescription;
    private String reasonType;
    private String service;
    private Long haveMoney = 0L;

    // Constructors
    /** default constructor */
    public Reason() {
    }

    /** minimal constructor */
    public Reason(Long reasonId, String reasonName, String reasonDescription) {
        this.reasonId = reasonId;
        this.reasonName = reasonName;
        this.reasonDescription = reasonDescription;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonDescription() {
        return reasonDescription;
    }

    public void setReasonDescription(String reasonDescription) {
        this.reasonDescription = reasonDescription;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Long getHaveMoney() {
        return haveMoney;
    }

    public void setHaveMoney(Long haveMoney) {
        this.haveMoney = haveMoney;
    }
    // Property accessors
}
