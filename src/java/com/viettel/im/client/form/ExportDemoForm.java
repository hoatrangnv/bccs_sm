/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;
import java.util.Date;
import org.apache.struts.action.ActionForm;
/**
 *
 * @author dattv
 */
public class ExportDemoForm{

    //Thong tin lenh xuat
    private String cmdExportCode;
    private String inputCmdExpCode;
    private String inputExpNoteCode;
    //Trang thai no
    private String debitStatus;
    //Ma don vi van chuyen
    private Long transporterId;

    //Ma phieu xuat
    private String noteExpCode;

    private String sender;
    private String reasonId;
    private Long shopImportedId;
    //Loai kho xuat
    private Long shopImportedType;
    private Long shopExportId;
    private String shopExportName;
    private String receiver;
    private String approver;
    private String dateExported;
    private String note;

    private String returnMsg;

    private Long inStockModelId;

    private String pageForward;
    



    private ResourceForm resForm= new ResourceForm();
    private GoodsForm goodsForm=new GoodsForm();

    public ResourceForm getResForm() {
        return resForm;
    }

    public void setResForm(ResourceForm resForm) {
        this.resForm = resForm;
    }

    public Long getShopImportedType() {
        return shopImportedType;
    }

    public void setShopImportedType(Long shopImportedType) {
        this.shopImportedType = shopImportedType;
    }


    
    public String getInputExpNoteCode() {
        return inputExpNoteCode;
    }

    public void setInputExpNoteCode(String inputExpNoteCode) {
        this.inputExpNoteCode = inputExpNoteCode;
    }


    
    public String getInputCmdExpCode() {
        return inputCmdExpCode;
    }

    public String getNoteExpCode() {
        return noteExpCode;
    }

    public void setNoteExpCode(String noteExpCode) {
        this.noteExpCode = noteExpCode;
    }

    public void setInputCmdExpCode(String inputCmdExpCode) {
        this.inputCmdExpCode = inputCmdExpCode;
    }

    
    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }


    
    public GoodsForm getGoodsForm() {
        return goodsForm;
    }

    public void setGoodsForm(GoodsForm goodsForm) {
        this.goodsForm = goodsForm;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
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

    public Long getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(Long transporterId) {
        this.transporterId = transporterId;
    }

    public Long getInStockModelId() {
        return inStockModelId;
    }

    public void setInStockModelId(Long inStockModelId) {
        this.inStockModelId = inStockModelId;
    }

    public String getPageForward() {
        return pageForward;
    }

    public void setPageForward(String pageForward) {
        this.pageForward = pageForward;
    }

 

}
