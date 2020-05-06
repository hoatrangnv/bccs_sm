/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.io.File;

/**
 *
 * @author ANHLT
 */
public class AssignIsdnPriceForm {

    //Loai dich vu
    private Long stockTypeId;
    //File danh sach so dep
    private File impFile;
    //Duong dan file log loi
    private String pathLogFile;

    public File getImpFile() {
        return impFile;
    }

    public void setImpFile(File impFile) {
        this.impFile = impFile;
    }

    public String getPathLogFile() {
        return pathLogFile;
    }

    public void setPathLogFile(String pathLogFile) {
        this.pathLogFile = pathLogFile;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    
    
}
