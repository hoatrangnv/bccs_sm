/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author TungTV
 */
public class StockGoodDrawForm extends ActionForm {

    private String startDrawNumber;
    private String endDrawNumber;
//    private String totalBlock1;
//    private String totalBlock2;
//    private String totalBlock3;
//    private String totalBlock4;
    private String[] deleteBlockId;
//    private boolean deletePrevious;

    public String getEndDrawNumber() {
        return endDrawNumber;
    }

    public void setEndDrawNumber(String endDrawNumber) {
        this.endDrawNumber = endDrawNumber;
    }

    public String getStartDrawNumber() {
        return startDrawNumber;
    }

    public void setStartDrawNumber(String startDrawNumber) {
        this.startDrawNumber = startDrawNumber;
    }

//    public String getTotalBlock1() {
//        return totalBlock1;
//    }
//
//    public void setTotalBlock1(String totalBlock1) {
//        this.totalBlock1 = totalBlock1;
//    }
//
//    public String getTotalBlock2() {
//        return totalBlock2;
//    }
//
//    public void setTotalBlock2(String totalBlock2) {
//        this.totalBlock2 = totalBlock2;
//    }
//
//    public String getTotalBlock3() {
//        return totalBlock3;
//    }
//
//    public void setTotalBlock3(String totalBlock3) {
//        this.totalBlock3 = totalBlock3;
//    }
//
//    public boolean isDeletePrevious() {
//        return deletePrevious;
//    }
//
//    public void setDeletePrevious(boolean deletePrevious) {
//        this.deletePrevious = deletePrevious;
//    }
//
//    public String getTotalBlock4() {
//        return totalBlock4;
//    }
//
//    public void setTotalBlock4(String totalBlock4) {
//        this.totalBlock4 = totalBlock4;
//    }
//
    public String[] getDeleteBlockId() {
        return deleteBlockId;
    }

    public void setDeleteBlockId(String[] deleteBlockId) {
        this.deleteBlockId = deleteBlockId;
    }
}
