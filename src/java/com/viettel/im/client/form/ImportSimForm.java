/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author dattv
 */
public class ImportSimForm extends ActionForm {

    private String fullPath;
    private String fileName;
    private String firstSerial;
    private String endSerial;
    private String countSim;
    private FormFile fileAttachment = null;


    private String serviceType;
    private String serial;
    private String imsi;
    private String pin1;
    private String pin2;
    private String puk1;
    private String puk2;
    private String adm1;
    private String eki;

    private String message;
    
    /**
     * @return the firstSerial
     */
    public String getFirstSerial() {
        return firstSerial;
    }

    /**
     * @param firstSerial the firstSerial to set
     */
    public void setFirstSerial(String firstSerial) {
        this.firstSerial = firstSerial;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    /**
     * @return the endSerial
     */
    public String getEndSerial() {
        return endSerial;
    }

    /**
     * @param endSerial the endSerial to set
     */
    public void setEndSerial(String endSerial) {
        this.endSerial = endSerial;
    }

    /**
     * @return the countSim
     */
    public String getCountSim() {
        return countSim;
    }

    /**
     * @param countSim the countSim to set
     */
    public void setCountSim(String countSim) {
        this.countSim = countSim;
    }

    /**
     * @return the fileAttachment
     */
    public FormFile getFileAttachment() {
        return fileAttachment;
    }

    /**
     * @param fileAttachment the fileAttachment to set
     */
    public void setFileAttachment(FormFile fileAttachment) {
        this.fileAttachment = fileAttachment;
    }

    /**
     * @return the serial
     */
    public String getSerial() {
        return serial;
    }

    /**
     * @param serial the serial to set
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * @return the imsi
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * @param imsi the imsi to set
     */
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    /**
     * @return the pin1
     */
    public String getPin1() {
        return pin1;
    }

    /**
     * @param pin1 the pin1 to set
     */
    public void setPin1(String pin1) {
        this.pin1 = pin1;
    }

    /**
     * @return the pin2
     */
    public String getPin2() {
        return pin2;
    }

    /**
     * @param pin2 the pin2 to set
     */
    public void setPin2(String pin2) {
        this.pin2 = pin2;
    }

    /**
     * @return the puk1
     */
    public String getPuk1() {
        return puk1;
    }

    /**
     * @param puk1 the puk1 to set
     */
    public void setPuk1(String puk1) {
        this.puk1 = puk1;
    }

    /**
     * @return the puk2
     */
    public String getPuk2() {
        return puk2;
    }

    /**
     * @param puk2 the puk2 to set
     */
    public void setPuk2(String puk2) {
        this.puk2 = puk2;
    }

    /**
     * @return the adm1
     */
    public String getAdm1() {
        return adm1;
    }

    /**
     * @param adm1 the adm1 to set
     */
    public void setAdm1(String adm1) {
        this.adm1 = adm1;
    }

    /**
     * @return the eki
     */
    public String getEki() {
        return eki;
    }

    /**
     * @param eki the eki to set
     */
    public void setEki(String eki) {
        this.eki = eki;
    }

    /**
     * @return the serviceType
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType the serviceType to set
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
