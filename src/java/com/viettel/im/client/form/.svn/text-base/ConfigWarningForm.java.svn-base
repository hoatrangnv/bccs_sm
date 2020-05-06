/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

/**
 *
 * @author kdvt_tronglv
 */
public class ConfigWarningForm {

    Long warningId;
    String toStep;
    String fromStep;
    String linkStep;
    String sqlSelectWarning;
    String roleCode;
    String allowUrlType;
    Long lockAmountDay;
    Long warnAmountDay;
    String nameWarning;
    String codeWarning;
    private Long status;

    public ConfigWarningForm() {
    }

    public String getAllowUrlType() {
        return allowUrlType;
    }

    public void setAllowUrlType(String allowUrlType) {
        this.allowUrlType = allowUrlType;
    }

    public String getCodeWarning() {
        return codeWarning;
    }

    public void setCodeWarning(String codeWarning) {
        this.codeWarning = codeWarning;
    }

    public String getLinkStep() {
        return linkStep;
    }

    public void setLinkStep(String linkStep) {
        this.linkStep = linkStep;
    }

    public Long getLockAmountDay() {
        return lockAmountDay;
    }

    public void setLockAmountDay(Long lockAmountDay) {
        this.lockAmountDay = lockAmountDay;
    }

    public String getNameWarning() {
        return nameWarning;
    }

    public void setNameWarning(String nameWarning) {
        this.nameWarning = nameWarning;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getSqlSelectWarning() {
        return sqlSelectWarning;
    }

    public void setSqlSelectWarning(String sqlSelectWarning) {
        this.sqlSelectWarning = sqlSelectWarning;
    }

    public String getToStep() {
        return toStep;
    }

    public void setToStep(String toStep) {
        this.toStep = toStep;
    }

    public String getFromStep() {
        return fromStep;
    }

    public void setFromStep(String fromStep) {
        this.fromStep = fromStep;
    }

    public Long getWarnAmountDay() {
        return warnAmountDay;
    }

    public void setWarnAmountDay(Long warnAmountDay) {
        this.warnAmountDay = warnAmountDay;
    }

    public Long getWarningId() {
        return warningId;
    }

    public void setWarningId(Long warningId) {
        this.warningId = warningId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public void copyBoToForm(com.viettel.im.database.BO.Warning warning) {
        if (warning != null) {
            this.warningId = warning.getWarningId();
            this.toStep = warning.getToStep();
            this.fromStep = warning.getFromStep();
            this.linkStep = warning.getLinkStep();
            this.sqlSelectWarning = warning.getSqlSelectWarning();
            this.roleCode = warning.getRoleCode();
            this.allowUrlType = warning.getAllowUrlType();
            this.lockAmountDay = warning.getLockAmountDay();
            this.warnAmountDay = warning.getWarnAmountDay();
            this.nameWarning = warning.getNameWarning();
            this.codeWarning = warning.getCodeWarning();
            this.status = warning.getStatus();
        }
    }
}
