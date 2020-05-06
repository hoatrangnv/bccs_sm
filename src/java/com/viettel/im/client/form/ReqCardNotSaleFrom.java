/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.io.File;
import java.util.Date;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class ReqCardNotSaleFrom extends ActionForm {
    
    private Long reqId;
    private String reqCode;
    private Long createStaffId;
    private Long shopId;
    private Date createReqDate;
    private Date approveDate;
    private Long approveStaffId;
    private Long total;
    private Long totalValid;
    private Long status;
    private String filePath;
    private String userCodeCreate;
    private String serverFileName; //ten file tren server trong truogn hop upload
    private String clientFileName; //ten file tren client trong truogn hop upload
    private String uploadFileFileName;
    private File uploadFile;
    private String serial;
    private Date lostDate;
    private Long reasonId;
    private Date fromDate;
    private Date toDate;
    private String staffCode;
    private String staffName;
    private String serverFileNameApp; //ten file tren server trong truogn hop upload
    private String clientFileNameApp; //ten file tren client trong truogn hop upload
    private String shopCode;
    private String shopName;
    private String toSerial;
    private String[] selectedStockCardLostIds;
    private Long statusSold;
    private Long impType;
    
    public void resetForm() {
        staffCode = "";
        staffName = "";
        reqCode = "";
        fromDate = null;
        toDate = null;
        status = null;
        createReqDate = null;
    }
    
    public Long getReqId() {
        return this.reqId;
    }

    public void setReqId(Long reqId) {
        this.reqId = reqId;
    }

    public String getReqCode() {
        return this.reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public Long getCreateStaffId() {
        return this.createStaffId;
    }

    public void setCreateStaffId(Long createStaffId) {
        this.createStaffId = createStaffId;
    }

    public Long getShopId() {
        return this.shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Date getCreateReqDate() {
        return this.createReqDate;
    }

    public void setCreateReqDate(Date createReqDate) {
        this.createReqDate = createReqDate;
    }

    public Date getApproveDate() {
        return this.approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Long getApproveStaffId() {
        return this.approveStaffId;
    }

    public void setApproveStaffId(Long approveStaffId) {
        this.approveStaffId = approveStaffId;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalValid() {
        return this.totalValid;
    }

    public void setTotalValid(Long totalValid) {
        this.totalValid = totalValid;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getUserCodeCreate() {
        return this.userCodeCreate;
    }

    public void setUserCodeCreate(String userCodeCreate) {
        this.userCodeCreate = userCodeCreate;
    }
    
    public String getServerFileName() {
        return this.serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public String getClientFileName() {
        return this.clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }
    
    public String getUploadFileFileName() {
        return uploadFileFileName;
    }

    public void setUploadFileFileName(String uploadFileFileName) {
        this.uploadFileFileName = uploadFileFileName;
    }

    public File getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }
    
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
    
    public Date getLostDate() {
        return this.lostDate;
    }

    public void setLostDate(Date lostDate) {
        this.lostDate = lostDate;
    }
    
    public Long getReasonId() {
        return this.reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }
    
    public Date getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }
    
    public Date getToDate() {
        return this.toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
    
    public String getStaffCode() {
        return this.staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }
    
     public String getStaffName() {
        return this.staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
    
     public String getServerFileNameApp() {
        return this.serverFileNameApp;
    }

    public void setServerFileNameApp(String serverFileNameApp) {
        this.serverFileNameApp = serverFileNameApp;
    }

    public String getClientFileNameApp() {
        return this.clientFileNameApp;
    }

    public void setClientFileNameApp(String clientFileNameApp) {
        this.clientFileNameApp = clientFileNameApp;
    }
    
    public String getShopCode() {
        return this.shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }
    
    public String getShopName() {
        return this.shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    
    public String getToSerial() {
        return toSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }
    
    public String[] getSelectedStockCardLostIds() {
        return selectedStockCardLostIds;
    }

    public void setSelectedStockCardLostIds(String[] selectedStockCardLostIds) {
        this.selectedStockCardLostIds = selectedStockCardLostIds;
    }
    
    public Long getStatusSold() {
        return this.statusSold;
    }

    public void setStatusSold(Long statusSold) {
        this.statusSold = statusSold;
    }
    
    public Long getImpType() {
        return impType;
    }

    public void setImpType(Long impType) {
        this.impType = impType;
    }
}
