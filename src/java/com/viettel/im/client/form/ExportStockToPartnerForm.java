/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.util.List;

/**
 *
 * @author NamNX
 */
public class ExportStockToPartnerForm extends ActionSupport {

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
    //Trang thai no
    private String debitStatus;
    //Trang thai dat coc
    private Long depositStatus = null;
    //Trang thai thanh toan
    private Long payStatus = null;
    //Trang thai boc tham
    private Long drawStatus = null;
    private Long amount;
    private Long amountNotTax;
    private Long amountTax;
    private Long tax;
    private String stockTransType;
    private String shopCode;
    private String shopName;
    private Long stockTypeId;
    private Long stockModelId;
    private String receiverStock;
    private Long partnerId; //da duoc thanhnc dinh nghia
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
    private String distanceStep; //Bước nhảy trong trường hợp nhập thẻ cào




    private List fromSerialList;
    private List toSerialList;

    private String expDate;
    //private GoodsForm goodsForm=new GoodsForm();

    private String staffCode;
    private String actionCodestr;
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
        this.shopImportedCode = "";
        this.shopImportedName = "";
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
        this.stockTransType = "";
        returnMsg = "done";
        this.shopCode = "";
        this.shopName = "";


        this.impType = 1L;
        this.actionCode = "";
        this.partnerId = null;
        this.stockTypeId = null;
        this.stockModelId = null;
        this.fromSerial = "";
        this.toSerial = "";
        this.quantity = null;
        this.serialQuantity = null;

       this.clientFileName="";
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

    }

    public String getActionCodestr() {
        return actionCodestr;
    }
    public void setActionCodestr(String actionCodestr) {
        this.actionCodestr = actionCodestr;
    }
    public String getStaffCode() {
        return staffCode;
    }
    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }
    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Long getActionType() {
        return actionType;
    }

    public void setActionType(Long actionType) {
        this.actionType = actionType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getAmountNotTax() {
        return amountNotTax;
    }

    public void setAmountNotTax(Long amountNotTax) {
        this.amountNotTax = amountNotTax;
    }

    public Long getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Long amountTax) {
        this.amountTax = amountTax;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public Long getCanPrint() {
        return canPrint;
    }

    public void setCanPrint(Long canPrint) {
        this.canPrint = canPrint;
    }

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
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

    public String getDistanceStep() {
        return distanceStep;
    }

    public void setDistanceStep(String distanceStep) {
        this.distanceStep = distanceStep;
    }

    public Long getDrawStatus() {
        return drawStatus;
    }

    public void setDrawStatus(Long drawStatus) {
        this.drawStatus = drawStatus;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getExportUrl() {
        return exportUrl;
    }

    public void setExportUrl(String exportUrl) {
        this.exportUrl = exportUrl;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromOwnerCode() {
        return fromOwnerCode;
    }

    public void setFromOwnerCode(String fromOwnerCode) {
        this.fromOwnerCode = fromOwnerCode;
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

    public String getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public String getImpDate() {
        return impDate;
    }

    public void setImpDate(String impDate) {
        this.impDate = impDate;
    }

    public File getImpFile() {
        return impFile;
    }

    public void setImpFile(File impFile) {
        this.impFile = impFile;
    }

    public Long getImpType() {
        return impType;
    }

    public void setImpType(Long impType) {
        this.impType = impType;
    }

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

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Long payStatus) {
        this.payStatus = payStatus;
    }

    public String getProfilePattern() {
        return profilePattern;
    }

    public void setProfilePattern(String profilePattern) {
        this.profilePattern = profilePattern;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverStock() {
        return receiverStock;
    }

    public void setReceiverStock(String receiverStock) {
        this.receiverStock = receiverStock;
    }

    public Long getRecordQuantity() {
        return recordQuantity;
    }

    public void setRecordQuantity(Long recordQuantity) {
        this.recordQuantity = recordQuantity;
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

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public Long getShopExpType() {
        return shopExpType;
    }

    public void setShopExpType(Long shopExpType) {
        this.shopExpType = shopExpType;
    }

    public String getShopExportCode() {
        return shopExportCode;
    }

    public void setShopExportCode(String shopExportCode) {
        this.shopExportCode = shopExportCode;
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

    public String getShopImportedCode() {
        return shopImportedCode;
    }

    public void setShopImportedCode(String shopImportedCode) {
        this.shopImportedCode = shopImportedCode;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getStockTransType() {
        return stockTransType;
    }

    public void setStockTransType(String stockTransType) {
        this.stockTransType = stockTransType;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public Long getTax() {
        return tax;
    }

    public void setTax(Long tax) {
        this.tax = tax;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getToOwnerCode() {
        return toOwnerCode;
    }

    public void setToOwnerCode(String toOwnerCode) {
        this.toOwnerCode = toOwnerCode;
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

    public String getToSerial() {
        return toSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public Long getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(Long transStatus) {
        this.transStatus = transStatus;
    }

    public Long getTransType() {
        return transType;
    }

    public void setTransType(Long transType) {
        this.transType = transType;
    }

    public List getFromSerialList() {
        return fromSerialList;
    }

    public void setFromSerialList(List fromSerialList) {
        this.fromSerialList = fromSerialList;
    }

    public List getToSerialList() {
        return toSerialList;
    }

    public void setToSerialList(List toSerialList) {
        this.toSerialList = toSerialList;
    }





}
