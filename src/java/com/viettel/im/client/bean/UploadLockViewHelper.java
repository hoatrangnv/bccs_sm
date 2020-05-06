/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;


/**
 *
 * @author TungTV
 */
public class UploadLockViewHelper {


    private Long kind;//Kind of upload


    private String usid;//User ID and session ID


    private String fileName;


    private Long stockModelId;


    private Long interfaceDisp;


    private String filePath;


    public static final String UPLOAD_LOCK_VIEWHELPER = "UPLOAD_LOCK_VIEWHELPER";


    public Long getKind() {
        return kind;
    }


    public void setKind(Long kind) {
        this.kind = kind;
    }


    public String getUsid() {
        return usid;
    }


    public void setUsid(String usid) {
        this.usid = usid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getInterfaceDisp() {
        return interfaceDisp;
    }

    public void setInterfaceDisp(Long interfaceDisp) {
        this.interfaceDisp = interfaceDisp;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
