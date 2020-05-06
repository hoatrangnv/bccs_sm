package com.viettel.im.database.BO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ApParamCm entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class ApParamCm implements java.io.Serializable {

    // Fields
    private Long paramId;
    private String paramType;
    private String paramCode;
    private String paramName;
    private String paramValue;
    private Long status;


    // Constructors
    /** default constructor */
    public ApParamCm() {
    }

    /** minimal constructor */
    public ApParamCm(Long paramId, String paramType, String paramCode,
            String paramName) {
        this.paramId = paramId;
        this.paramType = paramType;
        this.paramCode = paramCode;
        this.paramName = paramName;
    }

    /** full constructor */
    public ApParamCm(Long paramId, String paramType, String paramCode,
            String paramName, String paramValue, String description,
            Long status, Long actionAuditId, Date issueDatetime,
            Set commItemParamReasons) {
        this.paramId = paramId;
        this.paramType = paramType;
        this.paramCode = paramCode;
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.status = status;
    }

    // Property accessors
    public Long getParamId() {
        return this.paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public String getParamType() {
        return this.paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamCode() {
        return this.paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamName() {
        return this.paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return this.paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}