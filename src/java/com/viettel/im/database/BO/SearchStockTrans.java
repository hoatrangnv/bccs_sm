package com.viettel.im.database.BO;

import java.util.Date;

/**
 * SearchStockTrans entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SearchStockTrans implements java.io.Serializable {

    // Fields
    private Long stt;
    private SearchStockTransId id;
    private Long stockTransType;
    private Long actionType;
    private Date createDatetime;
    private String userCreate;
    private String userCode;
    private Long fromOwnerId;
    private Long fromOwnerType;
    private String fromOwnerName;
    private Long toOwnerId;
    private Long toOwnerType;
    private String toOwnerName;
    private String note;
    private Long reasonId;
    private String reasonName;
    private Long stockTransStatus;
    private String statusName;
    private Long depositStatus;

    private String hisAction;
    
    //DINHDC PYC 22670 xuat hang cho dai ly
    private String fileAcceptNote;
    private String fileAcceptConfirm;
    private Long fileAcceptStatus;

    // Constructors
    /** default constructor */
    public SearchStockTrans() {
    }

    public SearchStockTrans(SearchStockTransId id) {
        this.id = id;
    }
    
    public SearchStockTrans(SearchStockTransId id, Long stockTransType, Date createDatetime, String userCreate, Long fromOwnerId, Long fromOwnerType, String fromOwnerName, Long toOwnerId, Long toOwnerType, String toOwnerName, String note, Long reasonId, String reasonName, Long stockTransStatus, String statusName, Long actionType,Long depositStatus) {
        this.id = id;
        this.stockTransType = stockTransType;
        this.createDatetime = createDatetime;
        this.userCreate = userCreate;
        this.fromOwnerId = fromOwnerId;
        this.fromOwnerType = fromOwnerType;
        this.fromOwnerName = fromOwnerName;
        this.toOwnerId = toOwnerId;
        this.toOwnerType = toOwnerType;
        this.toOwnerName = toOwnerName;
        this.note = note;
        this.reasonId = reasonId;
        this.reasonName = reasonName;
        this.stockTransStatus = stockTransStatus;
        this.statusName = statusName;
        this.actionType=actionType;
        this.depositStatus = depositStatus;
    }
    
    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    
    public SearchStockTransId getId() {
        return id;
    }

    public void setId(SearchStockTransId id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    

    public Long getStockTransType() {
        return this.stockTransType;
    }

    public void setStockTransType(Long stockTransType) {
        this.stockTransType = stockTransType;
    }

    public Date getCreateDatetime() {
        return this.createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public Long getFromOwnerId() {
        return this.fromOwnerId;
    }

    public void setFromOwnerId(Long fromOwnerId) {
        this.fromOwnerId = fromOwnerId;
    }

    public Long getFromOwnerType() {
        return this.fromOwnerType;
    }

    public void setFromOwnerType(Long fromOwnerType) {
        this.fromOwnerType = fromOwnerType;
    }

    public String getFromOwnerName() {
        return this.fromOwnerName;
    }

    public void setFromOwnerName(String fromOwnerName) {
        this.fromOwnerName = fromOwnerName;
    }

    public Long getToOwnerId() {
        return this.toOwnerId;
    }

    public void setToOwnerId(Long toOwnerId) {
        this.toOwnerId = toOwnerId;
    }

    public Long getToOwnerType() {
        return this.toOwnerType;
    }

    public void setToOwnerType(Long toOwnerType) {
        this.toOwnerType = toOwnerType;
    }

    public String getToOwnerName() {
        return this.toOwnerName;
    }

    public void setToOwnerName(String toOwnerName) {
        this.toOwnerName = toOwnerName;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getReasonId() {
        return this.reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonName() {
        return this.reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public Long getStockTransStatus() {
        return this.stockTransStatus;
    }

    public void setStockTransStatus(Long stockTransStatus) {
        this.stockTransStatus = stockTransStatus;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getActionType() {
        return actionType;
    }

    public void setActionType(Long actionType) {
        this.actionType = actionType;
    }

    public String getHisAction() {
        return hisAction;
    }

    public void setHisAction(String hisAction) {
        this.hisAction = hisAction;
    }

    public Long getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(Long depositStatus) {
        this.depositStatus = depositStatus;
    }

    public String getFileAcceptNote() {
        return fileAcceptNote;
    }

    public void setFileAcceptNote(String fileAcceptNote) {
        this.fileAcceptNote = fileAcceptNote;
    }

    public String getFileAcceptConfirm() {
        return fileAcceptConfirm;
    }

    public void setFileAcceptConfirm(String fileAcceptConfirm) {
        this.fileAcceptConfirm = fileAcceptConfirm;
    }

    public Long getFileAcceptStatus() {
        return fileAcceptStatus;
    }

    public void setFileAcceptStatus(Long fileAcceptStatus) {
        this.fileAcceptStatus = fileAcceptStatus;
    }

}
