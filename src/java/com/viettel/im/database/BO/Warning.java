package com.viettel.im.database.BO;

/**
 * Warning entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Warning implements java.io.Serializable {

    private Long warningId;
    private String toStep;
    private String fromStep;
    private String nameWarning;
    private String codeWarning;
    private Long WLevel;
    private Long checkDate;
    private Long amountDate;
    private Long amountHour;
    private Long WType;
    private String linkStep;
    private Long status;
    private Long totalWarning;
    private Long totalDisable;
    private String sqlSelectWarning;
    private Long total;
    private String roleCode;
    private String allowUrlType;
    private Long lockAmountDay;
    private Long warnAmountDay;

    public Warning() {
    }

    public Warning(Long warningId, String toStep, String fromStep, String nameWarning, Long WLevel, Long checkDate, Long amountDate, Long amountHour, Long WType, String linkStep, Long status, Long totalWarning, Long totalDisable, String sqlSelectWarning) {
        this.warningId = warningId;
        this.toStep = toStep;
        this.fromStep = fromStep;
        this.nameWarning = nameWarning;
        this.WLevel = WLevel;
        this.checkDate = checkDate;
        this.amountDate = amountDate;
        this.amountHour = amountHour;
        this.WType = WType;
        this.linkStep = linkStep;
        this.status = status;
        this.totalWarning = totalWarning;
        this.totalDisable = totalDisable;
        this.sqlSelectWarning = sqlSelectWarning;
    }

    public Long getWLevel() {
        return WLevel;
    }

    public void setWLevel(Long WLevel) {
        this.WLevel = WLevel;
    }

    public Long getWType() {
        return WType;
    }

    public void setWType(Long WType) {
        this.WType = WType;
    }

    public Long getAmountDate() {
        return amountDate;
    }

    public void setAmountDate(Long amountDate) {
        this.amountDate = amountDate;
    }

    public Long getAmountHour() {
        return amountHour;
    }

    public void setAmountHour(Long amountHour) {
        this.amountHour = amountHour;
    }

    public Long getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Long checkDate) {
        this.checkDate = checkDate;
    }

    public String getFromStep() {
        return fromStep;
    }

    public void setFromStep(String fromStep) {
        this.fromStep = fromStep;
    }

    public String getLinkStep() {
        return linkStep;
    }

    public void setLinkStep(String linkStep) {
        this.linkStep = linkStep;
    }

    public String getNameWarning() {
        return nameWarning;
    }

    public void setNameWarning(String nameWarning) {
        this.nameWarning = nameWarning;
    }

    public String getCodeWarning() {
        return codeWarning;
    }

    public void setCodeWarning(String codeWarning) {
        this.codeWarning = codeWarning;
    }

    public String getSqlSelectWarning() {
        return sqlSelectWarning;
    }

    public void setSqlSelectWarning(String sqlSelectWarning) {
        this.sqlSelectWarning = sqlSelectWarning;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getToStep() {
        return toStep;
    }

    public void setToStep(String toStep) {
        this.toStep = toStep;
    }

    public Long getTotalDisable() {
        return totalDisable;
    }

    public void setTotalDisable(Long totalDisable) {
        this.totalDisable = totalDisable;
    }

    public Long getTotalWarning() {
        return totalWarning;
    }

    public void setTotalWarning(Long totalWarning) {
        this.totalWarning = totalWarning;
    }

    public Long getWarningId() {
        return warningId;
    }

    public void setWarningId(Long warningId) {
        this.warningId = warningId;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getAllowUrlType() {
        return allowUrlType;
    }

    public void setAllowUrlType(String allowUrlType) {
        this.allowUrlType = allowUrlType;
    }

    public Long getLockAmountDay() {
        return lockAmountDay;
    }

    public void setLockAmountDay(Long lockAmountDay) {
        this.lockAmountDay = lockAmountDay;
    }

    public Long getWarnAmountDay() {
        return warnAmountDay;
    }

    public void setWarnAmountDay(Long warnAmountDay) {
        this.warnAmountDay = warnAmountDay;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Warning clone() {
        Warning outPut = new Warning();
        outPut.setAmountDate(this.amountDate);
        outPut.setAmountHour(this.amountHour);
        outPut.setCheckDate(this.checkDate);
        outPut.setFromStep(this.fromStep);
        outPut.setLinkStep(this.linkStep);
        outPut.setNameWarning(this.nameWarning);
        outPut.setCodeWarning(this.codeWarning);
        outPut.setSqlSelectWarning(this.sqlSelectWarning);
        outPut.setStatus(this.status);
        outPut.setToStep(this.toStep);
        outPut.setTotal(this.total);
        outPut.setTotalDisable(this.totalDisable);
        outPut.setTotalWarning(this.totalWarning);
        outPut.setWLevel(this.WLevel);
        outPut.setWType(this.WType);
        outPut.setWarningId(this.warningId);
        outPut.setAllowUrlType(this.allowUrlType);
        outPut.setRoleCode(this.roleCode);
        outPut.setWarnAmountDay(this.warnAmountDay);
        outPut.setLockAmountDay(this.lockAmountDay);
        return outPut;
    }
}