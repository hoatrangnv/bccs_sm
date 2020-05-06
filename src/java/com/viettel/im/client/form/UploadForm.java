package com.viettel.im.client.form;

import java.io.File;
import org.apache.struts.action.ActionForm;

public class UploadForm extends ActionForm {

    private File fileUpload;
    private String clientFileNameId;
    private String serverFileNameId;
    private String dispType;
    private String stockTypeId;
    private String stockModelId;
    private String fileUploadFileName;
    private String stockModelIdKey;
    private String fileUploadContentType;
    
    private boolean impBankReceipt = false;

    public File getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(File fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String getClientFileNameId() {
        return clientFileNameId;
    }

    public void setClientFileNameId(String clientFileNameId) {
        this.clientFileNameId = clientFileNameId;
    }

    public String getServerFileNameId() {
        return serverFileNameId;
    }

    public void setServerFileNameId(String serverFileNameId) {
        this.serverFileNameId = serverFileNameId;
    }

    public String getDispType() {
        return dispType;
    }

    public void setDispType(String dispType) {
        this.dispType = dispType;
    }

    public String getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(String stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(String stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getFileUploadFileName() {
        return fileUploadFileName;
    }

    public void setFileUploadFileName(String fileUploadFileName) {
        this.fileUploadFileName = fileUploadFileName;
    }

    public String getStockModelIdKey() {
        return stockModelIdKey;
    }

    public void setStockModelIdKey(String stockModelIdKey) {
        this.stockModelIdKey = stockModelIdKey;
    }

    public String getFileUploadContentType() {
        return fileUploadContentType;
    }

    public void setFileUploadContentType(String fileUploadContentType) {
        this.fileUploadContentType = fileUploadContentType;
    }

    public boolean isImpBankReceipt() {
        return impBankReceipt;
    }

    public void setImpBankReceipt(boolean impBankReceipt) {
        this.impBankReceipt = impBankReceipt;
    }
    
}
