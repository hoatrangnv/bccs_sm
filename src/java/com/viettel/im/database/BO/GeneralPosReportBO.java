/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.BO;

/**
 *
 * @author TungTV
 */
public class GeneralPosReportBO {

    /* Tong so giao dich */
    private String totalTrans;

    /* Tong so tien MB */
    private Long totalAmount;

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalTrans() {
        return totalTrans;
    }

    public void setTotalTrans(String totalTrans) {
        this.totalTrans = totalTrans;
    }

}
