/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

/**
 *
 * @author AnDV
 */
import org.apache.struts.action.ActionForm;

public class LookupStockSimForm extends ActionForm{
    private String hlrId;
    private String hlrStatus;
    private String aucStatus;
    private String stockModelId;
    private String startImsi;
    private String endImsi;
    private String startSerial;
    private String endSerial;

    public String getAucStatus() {
        return aucStatus;
    }

    public void setAucStatus(String aucStatus) {
        this.aucStatus = aucStatus;
    }

    public String getEndImsi() {
        return endImsi;
    }

    public void setEndImsi(String endImsi) {
        this.endImsi = endImsi;
    }

    public String getEndSerial() {
        return endSerial;
    }

    public void setEndSerial(String endSerial) {
        this.endSerial = endSerial;
    }

    public String getHlrId() {
        return hlrId;
    }

    public void setHlrId(String hlrId) {
        this.hlrId = hlrId;
    }

    public String getHlrStatus() {
        return hlrStatus;
    }

    public void setHlrStatus(String hlrStatus) {
        this.hlrStatus = hlrStatus;
    }

    public String getStartImsi() {
        return startImsi;
    }

    public void setStartImsi(String startImsi) {
        this.startImsi = startImsi;
    }

    public String getStartSerial() {
        return startSerial;
    }

    public void setStartSerial(String startSerial) {
        this.startSerial = startSerial;
    }

    public String getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(String stockModelId) {
        this.stockModelId = stockModelId;
    }

    public LookupStockSimForm() {
    }
    

}
