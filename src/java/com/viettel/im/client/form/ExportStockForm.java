/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Date;

/**
 *
 * @author dattv
 */
public class ExportStockForm extends ActionSupport {

    //Dieu kien tim kiem
    private Long actionType;
    private String actionCode;
    private Long actionId;
    private Long transStatus;
    private Long transType;
    private Long fromOwnerType;
    private String fromOwnerCode;
    private Long fromOwnerId;
    private String fromOwnerName;
    private Long toOwnerType;
    private Long toOwnerId;
    private String toOwnerCode;
    private String toOwnerName;
    private String fromDate;
    private String toDate;
    private String cmdExportCode;
    private String inputCmdExpCode;
    private String inputExpNoteCode;
    private Long canPrint = 0L;
    
    private Long channelTypeId;

    //Ma phieu xuat
    private String noteExpCode;
    private Long senderId;
    private String sender;
    private String reasonId;
    private String reasonName;
    private Long shopImportedId;
    private String shopImportedCode;
    private String shopImportedName;
    //Loai kho xuat
    private Long shopImportedType;
    private Long shopImportedChannelTypeId;
    private Long shopExpType;
    private Long shopExportId;
    private String shopExportCode;
    private String shopExportName;
    private String receiver;
    private String approver;
    private String dateExported;
    private String note;
    private String returnMsg = "done";
    private String exportUrl = "";
    private Long status;
    private Long payMethodeid;
    private Long rejectUserId;
    private String rejectDate;
    private Long rejectReasonId;
    //Trang thai no
    private String debitStatus;
    //Trang thai dat coc
    private Long depositStatus = null;
    //Trang thai thanh toan
    private Long payStatus = null;
    //Trang thai boc tham
    private Long drawStatus = null;
    private Double amount;
    private Double amountNotTax;
    private Double amountTax;
    private Double tax;
    private String stockTransType;
    
    private String amountTaxDisplay;

    private String shopCode;//Mã đơn vị xuất/nhập kho
    private String shopName;//Tên đơn vị xuất/nhập kho
    private String staffCode;//Mã nhân viên xuất/nhập kho
    private String staffName;//Tên nhân viên xuất/nhập kho
    private String userCode;//Mã nhân viên xuất/nhập kho
    private String userName;//Tên nhân viên xuất/nhập kho

    //bo sung cho chuc nang tao lenh xuat tu file
    private String serverFileName; //ten file tren server trong truogn hop upload
    private String clientFileName; //ten file tren client trong truogn hop upload
    private boolean chkExport;
    private boolean chkOtherCmd;
    //trung dh3 R499 xuát hàng cho đại lý
    private Long expBigAngent;
    
    //Them kieu Date cho from_date, to_date
    private  Date fromDateSearch;
    private  Date toDateSearch;
    
    private Long stockTransId;
    
    //lamnt 06082017lam cho kho giam tru
    private String shopId;
    private Long reason;
    private String fileUpload;

    public Long getReason() {
        return reason;
    }

    public void setReason(Long reason) {
        this.reason = reason;
    }

    public String getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(String fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    //lamnt end

    public boolean isChkExport() {
        return chkExport;
    }

    public void setChkExport(boolean chkExport) {
        this.chkExport = chkExport;
    }
    //trung dh3 R499 xuát hàng cho đại lý
    public boolean isChkOtherCmd() {
        return chkOtherCmd;
    }

    public void setChkOtherCmd(boolean chkOtherCmd) {
        this.chkOtherCmd = chkOtherCmd;
    }
    public void resetForm() {
        this.actionType = 0L;
        this.cmdExportCode = "";
        this.inputCmdExpCode = "";
        this.inputExpNoteCode = "";
        this.debitStatus = "";
        this.transStatus = null;
        this.noteExpCode = "";
        this.senderId = null;
        this.sender = "";
        this.reasonId = "0";
        this.shopImportedId = null;
        this.shopImportedType = null;
        this.shopImportedCode="";
        this.shopImportedName="";
        this.shopExportId = null;
        this.shopExportName = "";
        this.receiver = "";
        this.approver = "";
        this.dateExported = null;
        this.note = "";
        this.returnMsg = "";
        this.exportUrl = "";
        this.status = null;
        this.depositStatus = null;
        this.payStatus = null;
        this.drawStatus = null;
        this.actionId = null;
        this.canPrint = 0L;
        this.amount = null;
        this.amountNotTax = null;
        this.amountTax = null;
        this.tax = null;
        amountTaxDisplay = "0.0";
        this.stockTransType = "";
        returnMsg = "done";
        this.shopCode = "";
        this.shopName = "";
        this.staffCode = "";
        this.staffName = "";
        this.userCode = "";
        this.userName = "";
        this.payMethodeid = null;

        this.channelTypeId = null;
    }

    public void resetInputForm() {

        this.cmdExportCode = "";
        this.inputCmdExpCode = "";
        this.inputExpNoteCode = "";

        this.noteExpCode = "";
        this.senderId = null;
        this.sender = "";
        this.reasonId = "0";
        this.shopImportedId = null;
        this.shopImportedType = null;
        this.shopExportId = null;
        this.shopExportName = "";
        this.receiver = "";
        this.approver = "";
        this.dateExported = null;
        this.note = "";
        this.returnMsg = "";
        this.exportUrl = "";
        this.status = null;
        this.stockTransType = "";
        returnMsg = "done";
        this.shopCode = "";
        this.shopName = "";

        this.channelTypeId = null;

    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getRejectDate() {
        return rejectDate;
    }

    public void setRejectDate(String rejectDate) {
        this.rejectDate = rejectDate;
    }

    public Long getRejectReasonId() {
        return rejectReasonId;
    }

    public void setRejectReasonId(Long rejectReasonId) {
        this.rejectReasonId = rejectReasonId;
    }

    public Long getRejectUserId() {
        return rejectUserId;
    }

    public void setRejectUserId(Long rejectUserId) {
        this.rejectUserId = rejectUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public Long getActionType() {
        return actionType;
    }

    public void setActionType(Long actionType) {
        this.actionType = actionType;
    }

    public Long getTransType() {
        return transType;
    }

    public void setTransType(Long transType) {
        this.transType = transType;
    }

    public String getFromOwnerCode() {
        return fromOwnerCode;
    }

    public void setFromOwnerCode(String fromOwnerCode) {
        this.fromOwnerCode = fromOwnerCode;
    }

    public String getFromOwnerName() {
        return fromOwnerName;
    }

    public void setFromOwnerName(String fromOwnerName) {
        this.fromOwnerName = fromOwnerName;
    }

    public String getToOwnerName() {
        return toOwnerName;
    }

    public void setToOwnerName(String toOwnerName) {
        this.toOwnerName = toOwnerName;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getCmdExportCode() {
        return cmdExportCode;
    }

    public void setCmdExportCode(String cmdExportCode) {
        this.cmdExportCode = cmdExportCode;
    }

    public String getDateExported() {
        return dateExported;
    }

    public void setDateExported(String dateExported) {
        this.dateExported = dateExported;
    }

    public String getDebitStatus() {
        return debitStatus;
    }

    public void setDebitStatus(String debitStatus) {
        this.debitStatus = debitStatus;
    }

    public Long getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(Long depositStatus) {
        this.depositStatus = depositStatus;
    }

    public Long getDrawStatus() {
        return drawStatus;
    }

    public void setDrawStatus(Long drawStatus) {
        this.drawStatus = drawStatus;
    }

    public String getExportUrl() {
        return exportUrl;
    }

    public void setExportUrl(String exportUrl) {
        this.exportUrl = exportUrl;
    }

    public Long getCanPrint() {
        return canPrint;
    }

    public void setCanPrint(Long canPrint) {
        this.canPrint = canPrint;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public Long getFromOwnerId() {
        return fromOwnerId;
    }

    public void setFromOwnerId(Long fromOwnerId) {
        this.fromOwnerId = fromOwnerId;
    }

    public Long getFromOwnerType() {
        return fromOwnerType;
    }

    public void setFromOwnerType(Long fromOwnerType) {
        this.fromOwnerType = fromOwnerType;
    }

//    public GoodsForm getGoodsForm() {
//        return goodsForm;
//    }
//
//    public void setGoodsForm(GoodsForm goodsForm) {
//        this.goodsForm = goodsForm;
//    }
//
    public String getInputCmdExpCode() {
        return inputCmdExpCode;
    }

    public void setInputCmdExpCode(String inputCmdExpCode) {
        this.inputCmdExpCode = inputCmdExpCode;
    }

    public String getInputExpNoteCode() {
        return inputExpNoteCode;
    }

    public void setInputExpNoteCode(String inputExpNoteCode) {
        this.inputExpNoteCode = inputExpNoteCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteExpCode() {
        return noteExpCode;
    }

    public void setNoteExpCode(String noteExpCode) {
        this.noteExpCode = noteExpCode;
    }

    public Long getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Long payStatus) {
        this.payStatus = payStatus;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getShopExpType() {
        return shopExpType;
    }

    public void setShopExpType(Long shopExpType) {
        this.shopExpType = shopExpType;
    }

    public Long getShopExportId() {
        return shopExportId;
    }

    public void setShopExportId(Long shopExportId) {
        this.shopExportId = shopExportId;
    }

    public String getShopExportName() {
        return shopExportName;
    }

    public void setShopExportName(String shopExportName) {
        this.shopExportName = shopExportName;
    }

    public Long getShopImportedId() {
        return shopImportedId;
    }

    public void setShopImportedId(Long shopImportedId) {
        this.shopImportedId = shopImportedId;
    }

    public String getShopImportedName() {
        return shopImportedName;
    }

    public void setShopImportedName(String shopImportedName) {
        this.shopImportedName = shopImportedName;
    }

    public Long getShopImportedType() {
        return shopImportedType;
    }

    public void setShopImportedType(Long shopImportedType) {
        this.shopImportedType = shopImportedType;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Long getToOwnerId() {
        return toOwnerId;
    }

    public void setToOwnerId(Long toOwnerId) {
        this.toOwnerId = toOwnerId;
    }

    public Long getToOwnerType() {
        return toOwnerType;
    }

    public void setToOwnerType(Long toOwnerType) {
        this.toOwnerType = toOwnerType;
    }

    public Long getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(Long transStatus) {
        this.transStatus = transStatus;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmountNotTax() {
        return amountNotTax;
    }

    public void setAmountNotTax(Double amountNotTax) {
        this.amountNotTax = amountNotTax;
    }

    public Double getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Double amountTax) {
        this.amountTax = amountTax;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getStockTransType() {
        return stockTransType;
    }

    public void setStockTransType(String stockTransType) {
        this.stockTransType = stockTransType;
    }

    public String getShopImportedCode() {
        return shopImportedCode;
    }

    public void setShopImportedCode(String shopImportedCode) {
        this.shopImportedCode = shopImportedCode;
    }

    public String getShopExportCode() {
        return shopExportCode;
    }

    public void setShopExportCode(String shopExportCode) {
        this.shopExportCode = shopExportCode;
    }

    public String getToOwnerCode() {
        return toOwnerCode;
    }

    public void setToOwnerCode(String toOwnerCode) {
        this.toOwnerCode = toOwnerCode;
    }

    public Long getPayMethodeid() {
        return payMethodeid;
    }

    public void setPayMethodeid(Long payMethodeid) {
        this.payMethodeid = payMethodeid;
    }

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public Long getShopImportedChannelTypeId() {
        return shopImportedChannelTypeId;
    }

    public void setShopImportedChannelTypeId(Long shopImportedChannelTypeId) {
        this.shopImportedChannelTypeId = shopImportedChannelTypeId;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getAmountTaxDisplay() {
        return amountTaxDisplay;
    }

    public void setAmountTaxDisplay(String amountTaxDisplay) {
        this.amountTaxDisplay = amountTaxDisplay;
    }

    public Long getExpBigAngent() {
        return expBigAngent;
    }

    public void setExpBigAngent(Long expBigAngent) {
        this.expBigAngent = expBigAngent;
    }

    public Date getFromDateSearch() {
        return fromDateSearch;
    }

    public void setFromDateSearch(Date fromDateSearch) {
        this.fromDateSearch = fromDateSearch;
    }

    public Date getToDateSearch() {
        return toDateSearch;
    }

    public void setToDateSearch(Date toDateSearch) {
        this.toDateSearch = toDateSearch;
    }

    public Long getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }
    
}
