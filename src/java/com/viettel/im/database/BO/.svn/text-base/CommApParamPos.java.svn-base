package com.viettel.im.database.BO;

import java.util.HashSet;
import java.util.Set;

/**
 * CommApParam entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CommApParamPos implements java.io.Serializable {

    // Fields
    private Long paramId;
    private String paramType;
    private String paramCode;
    private String paramName;
    private String paramValue;
    private Long status;
    private String reasonType;
    private Set commItemParamReasons = new HashSet(0);

    // Constructors
    /** default constructor */
    public CommApParamPos() {
    }

    /** minimal constructor */
    public CommApParamPos(Long paramId, String paramType, String paramCode,
            String paramName) {
        this.paramId = paramId;
        this.paramType = paramType;
        this.paramCode = paramCode;
        this.paramName = paramName;
    }
    
    public CommApParamPos(String paramCode,String paramName) {
        this.paramCode = paramCode;
        this.paramName = paramName;
    }

    /** full constructor */
    public CommApParamPos(Long paramId, String paramType, String paramCode,
            String paramName, String paramValue, Long status,
            Set commItemParamReasons) {
        this.paramId = paramId;
        this.paramType = paramType;
        this.paramCode = paramCode;
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.status = status;
        this.commItemParamReasons = commItemParamReasons;
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

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    public Set getCommItemParamReasons() {
        return this.commItemParamReasons;
    }

    public void setCommItemParamReasons(Set commItemParamReasons) {
        this.commItemParamReasons = commItemParamReasons;
    }
}
