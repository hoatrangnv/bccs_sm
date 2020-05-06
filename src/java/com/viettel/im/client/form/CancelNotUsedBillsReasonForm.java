/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author TungTV
 * @funtion BillManagement
 * @Date 18/02/2009
 */

public class CancelNotUsedBillsReasonForm extends ActionForm {

    /* Not used bill reason **/
    private String cancelBillReason;

    private String billCancelStartNumber;

    private String billCancelEndNumber;

    public String getCancelBillReason() {
        return cancelBillReason;
    }

    public void setCancelBillReason(String cancelBillReason) {
        this.cancelBillReason = cancelBillReason;
    }

    public String getBillCancelStartNumber() {
        return billCancelStartNumber;
    }

    public void setBillCancelStartNumber(String billCancelStartNumber) {
        this.billCancelStartNumber = billCancelStartNumber;
    }

    public String getBillCancelEndNumber() {
        return billCancelEndNumber;
    }

    public void setBillCancelEndNumber(String billCancelEndNumber) {
        this.billCancelEndNumber = billCancelEndNumber;
    }

    
}
