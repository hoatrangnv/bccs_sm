/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.common.Constant;
import java.io.File;
import java.util.Date;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author dattv
 */
public class ImportStockForm extends ActionForm {

     //Dieu kien tim kiem
    private Long transType;
    private Long actionType;
    private String actionCode;
    private Long actionId;
    private Long transStatus;
    private Long fromOwnerType;
    private Long fromOwnerId;
    private String fromOwnerCode;
    private String fromOwnerName;
    private Long toOwnerType;
    private Long toOwnerId;
    private String toOwnerCode;
    private String toOwnerName;
    private String fromDate;
    private String toDate;
    private Long canPrint=0L;
    private String distanceStep; //Bước nhảy trong trường hợp nhập thẻ cào
    private Long paymethodeid;
    private Long shopImpType;
    //trung dh3
    private Long rejectUserId;
    private String rejectDate;
    private Long rejectReasonId;
    //Thong tin lenh nhap
    private String cmdImportCode; //Ma lenh nhap

    private String noteImpCode; //Ma phieu nhap
    private Long partnerId;
    //Trang thai no
    private String debitStatus;
    private String reasonId;
    private Long shopImportId;
    private Long shopImportType;    
    private String shopImportCode;
    private String shopImportName;
    private String shopExportCode;
    private String shopExportName;
    private Long shopExportId;
    private Long shopExportType;

    private String receiver;
    private Long receiverId;
    private String dateImported;
    private String note;
    private String inputExpNoteCode; // Ma phieu xuat (nguoi dung nhap vao de lay danh sach hang hoa)
    private Long status; //Trang thai phieu, lenh xuat
    private String errorUrl;//Duong dan den file log loi neu co loi xay ra
    private String returnMsg;
    private Long inStockModelId;
    private String exportUrl;


    //----------------------------------------------------------------
    //tamdt1 - start, 17/03/2009
    //cac property cho giao dien moi
    private Long stockTypeId;
    private Long stockModelId;
    private String receiverStock;
//    private Long partnerId; //da duoc thanhnc dinh nghia
    private String impDate;
    private Long impType; //kieu nhap hang (theo file, theo dai so, theo dánh sach)
    private File impFile; //file du lieu (trong truong hop nhap hang theo file)
    private String serverFileName; //ten file tren server trong truogn hop upload
    private String clientFileName; //ten file tren client trong truogn hop upload
    private Long recordQuantity; //so luong ban ghi trong file du lieu (trong truong hop nhap hang theo file)
    private String fromSerial; //tu serial (trong truong hop nhap theo dai so)
    private String toSerial; //den serial (trong truong hop nhap theo dai so)
    private Long serialQuantity; //so luong serial (trong truong hop nhap theo dai so)
    private Long quantity; //so luong (trong truong hop nhap theo dai so hoac theo list)
    private String profilePattern; //mau profile
    private Long amountTax;
    private String amountTaxDisplay;
    private Long stateId; //trang thai hang
    private String expSimActionCode;
    private Long expSimActionId;

    //ThanhNC add chi dung cho viec nhap KIT phai chon loai sim
    private Long stockModelSimId;
    private String kind;
    private String a3a8;
    private Long senderId;
    private String sender;
    private String reasonName;
    //LeVT1 - R499
    private boolean chkExport;
    private Date importDate;
    private String stockModelCode;
    private String stockModelName;
    private String stockTypeName;
    private Long noteQuantity;
    private Long stockTransId;
    private String contractCode;
    private String batchCode;

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Long getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    public Long getNoteQuantity() {
        return noteQuantity;
    }

    public void setNoteQuantity(Long noteQuantity) {
        this.noteQuantity = noteQuantity;
    }


    public String getStockModelCode() {
        return stockModelCode;
    }

    public String getStockTypeName() {
        return stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public boolean isChkExport() {
        return chkExport;
    }

    public void setChkExport(boolean chkExport) {
        this.chkExport = chkExport;
    }


    public String getA3a8() {
        return a3a8;
    }

    public void setA3a8(String a3a8) {
        this.a3a8 = a3a8;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }


    public Long getStockModelSimId() {
        return stockModelSimId;
    }

    public void setStockModelSimId(Long stockModelSimId) {
        this.stockModelSimId = stockModelSimId;
    }

    
    public Long getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Long amountTax) {
        this.amountTax = amountTax;
    }    
    
    public ImportStockForm() {
        //
        this.stockTypeId = 0L;
        this.stockModelId = 0L;
        this.receiverStock = "";
        this.impDate = "";
        this.partnerId = null;
        this.impType = 1L;
        this.recordQuantity = null;
        this.fromSerial = "";
        this.toSerial = "";
        this.serialQuantity = null;
        this.quantity = null;
        this.profilePattern = "";
        
        this.amountTaxDisplay = "0.0";
    }

    public void resetForm() {
        this.actionCode = "";
        this.cmdImportCode = "";

        this.debitStatus = "";
        this.reasonId = null;
        this.shopImportId = 0L;
        this.shopImportName = "";
        this.shopExportId = 0L;
        this.receiver = "";
        this.receiverId = null;
        this.dateImported = null;
        this.note = "";
        this.inputExpNoteCode = "";
        this.status = null;
        this.errorUrl = "";
        this.returnMsg = "";
        this.noteImpCode="";
        this.inStockModelId=0L;
        this.canPrint=0L;

        //
        this.stockTypeId = 0L;
        this.stockModelId = 0L;
        this.receiverStock = "";
        this.impDate = "";
        this.partnerId = 0L;
        this.impType = 1L;
        this.serverFileName = "";
        this.clientFileName = "";
        this.recordQuantity = null;
        this.fromSerial = "";
        this.toSerial = "";
        this.serialQuantity = null;
        this.quantity = null;
        this.profilePattern = "";
        this.stateId = Constant.STATE_NEW;
        this.expSimActionCode = "";
        this.expSimActionId = 0L;
        
        this.amountTax = null;
        this.amountTaxDisplay = "0.0";
    }

    public String getExportUrl() {
        return exportUrl;
    }

    public void setExportUrl(String exportUrl) {
        this.exportUrl = exportUrl;
    }

    
    public String getProfilePattern() {
        return profilePattern;
    }

    public void setProfilePattern(String profilePattern) {
        this.profilePattern = profilePattern;
    }

    public Long getTransType() {
        return transType;
    }

    public void setTransType(Long transType) {
        this.transType = transType;
    }

    
    public String getShopExportName() {
        return shopExportName;
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
    public void setShopExportName(String shopExportName) {
        this.shopExportName = shopExportName;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getShopImportName() {
        return shopImportName;
    }

    public void setShopImportName(String shopImportName) {
        this.shopImportName = shopImportName;
    }

    public String getImpDate() {
        return impDate;
    }

    public void setImpDate(String impDate) {
        this.impDate = impDate;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public File getImpFile() {
        return impFile;
    }

    public void setImpFile(File impFile) {
        this.impFile = impFile;
    }

    public String getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public Long getImpType() {
        return impType;
    }

    public void setImpType(Long impType) {
        this.impType = impType;
    }

    public String getReceiverStock() {
        return receiverStock;
    }

    public void setReceiverStock(String receiverStock) {
        this.receiverStock = receiverStock;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getToSerial() {
        return toSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public String getCmdImportCode() {
        return cmdImportCode;
    }

    public void setCmdImportCode(String cmdImportCode) {
        this.cmdImportCode = cmdImportCode;
    }

    public String getDateImported() {
        return dateImported;
    }

    public void setDateImported(String dateImported) {
        this.dateImported = dateImported;
    }

    public String getDebitStatus() {
        return debitStatus;
    }

    public void setDebitStatus(String debitStatus) {
        this.debitStatus = debitStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
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

    public Long getShopExportId() {
        return shopExportId;
    }

    public void setShopExportId(Long shopExportId) {
        this.shopExportId = shopExportId;
    }

    public Long getShopImportId() {
        return shopImportId;
    }

    public void setShopImportId(Long shopImportId) {
        this.shopImportId = shopImportId;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getInputExpNoteCode() {
        return inputExpNoteCode;
    }

    public void setInputExpNoteCode(String inputExpNoteCode) {
        this.inputExpNoteCode = inputExpNoteCode;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }


    public String getNoteImpCode() {
        return noteImpCode;
    }

    public void setNoteImpCode(String noteImpCode) {
        this.noteImpCode = noteImpCode;
    }

    public Long getInStockModelId() {
        return inStockModelId;
    }

    public void setInStockModelId(Long inStockModelId) {
        this.inStockModelId = inStockModelId;
    }

    public Long getShopExportType() {
        return shopExportType;
    }

    public void setShopExportType(Long shopExportType) {
        this.shopExportType = shopExportType;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public Long getActionType() {
        return actionType;
    }

    public void setActionType(Long actionType) {
        this.actionType = actionType;
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

    public String getFromOwnerName() {
        return fromOwnerName;
    }

    public void setFromOwnerName(String fromOwnerName) {
        this.fromOwnerName = fromOwnerName;
    }

    public Long getFromOwnerType() {
        return fromOwnerType;
    }

    public void setFromOwnerType(Long fromOwnerType) {
        this.fromOwnerType = fromOwnerType;
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

    public String getToOwnerName() {
        return toOwnerName;
    }

    public void setToOwnerName(String toOwnerName) {
        this.toOwnerName = toOwnerName;
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

    public Long getShopImportType() {
        return shopImportType;
    }

    public void setShopImportType(Long shopImportType) {
        this.shopImportType = shopImportType;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Long getCanPrint() {
        return canPrint;
    }

    public void setCanPrint(Long canPrint) {
        this.canPrint = canPrint;
    }

    public Long getRecordQuantity() {
        return recordQuantity;
    }

    public void setRecordQuantity(Long recordQuantity) {
        this.recordQuantity = recordQuantity;
    }

    public Long getSerialQuantity() {
        return serialQuantity;
    }

    public void setSerialQuantity(Long serialQuantity) {
        this.serialQuantity = serialQuantity;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }

    public String getShopExportCode() {
        return shopExportCode;
    }

    public void setShopExportCode(String shopExportCode) {
        this.shopExportCode = shopExportCode;
    }

    public String getShopImportCode() {
        return shopImportCode;
    }

    public void setShopImportCode(String shopImportCode) {
        this.shopImportCode = shopImportCode;
    }

    public String getFromOwnerCode() {
        return fromOwnerCode;
    }

    public void setFromOwnerCode(String fromOwnerCode) {
        this.fromOwnerCode = fromOwnerCode;
    }

    public String getToOwnerCode() {
        return toOwnerCode;
    }

    public void setToOwnerCode(String toOwnerCode) {
        this.toOwnerCode = toOwnerCode;
    }

    public String getDistanceStep() {
        return distanceStep;
    }

    public void setDistanceStep(String distanceStep) {
        this.distanceStep = distanceStep;
    }

    public Long getPaymethodeid() {
        return paymethodeid;
    }

    public void setPaymethodeid(Long paymethodeid) {
        this.paymethodeid = paymethodeid;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getExpSimActionCode() {
        return expSimActionCode;
    }

    public void setExpSimActionCode(String expSimActionCode) {
        this.expSimActionCode = expSimActionCode;
    }

    public Long getExpSimActionId() {
        return expSimActionId;
    }

    public void setExpSimActionId(Long expSimActionId) {
        this.expSimActionId = expSimActionId;
    }

    public Long getShopImpType() {
        return shopImpType;
    }
    
    public void setShopImpType(Long shopImpType) {
        this.shopImpType = shopImpType;
    }


    //tamdt1 - end
    //----------------------------------------------------------------

    public String getAmountTaxDisplay() {
        return amountTaxDisplay;
    }

    public void setAmountTaxDisplay(String amountTaxDisplay) {
        this.amountTaxDisplay = amountTaxDisplay;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }
}
