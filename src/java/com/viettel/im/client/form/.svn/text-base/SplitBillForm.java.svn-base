/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;
import com.viettel.im.common.util.ValidateUtils;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author TungTV
 * @funtion BillManagement
 * @Date 18/02/2009
 */

public class SplitBillForm extends ValidatorForm {

    /* Split from **/
    private String billSplitStartNumber;

    /* Split to **/
    private String billSplitEndNumber;

    /* Number per Split **/
    private String billSplitPerPartNumber;

    
    /* Do dai lon nhat cua truong */
    private static int maxLength = 10;


    public String getBillSplitEndNumber() {
        return billSplitEndNumber;
    }

    public void setBillSplitEndNumber(String billSplitEndNumber) {
        this.billSplitEndNumber = billSplitEndNumber;
    }

    public String getBillSplitStartNumber() {
        return billSplitStartNumber;
    }

    public void setBillSplitStartNumber(String billSplitStartNumber) {
        this.billSplitStartNumber = billSplitStartNumber;
    }

    public String getBillSplitPerPartNumber() {
        return billSplitPerPartNumber;
    }

    public void setBillSplitPerPartNumber(String billSplitPerPartNumber) {
        this.billSplitPerPartNumber = billSplitPerPartNumber;
    }

    public static int getMaxLength() {
        return maxLength;
    }

    public static void setMaxLength(int maxLength) {
        SplitBillForm.maxLength = maxLength;
    }

    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        
        ActionErrors errors = new ActionErrors();

        boolean validInput = true;

        if (this.billSplitStartNumber == null || this.billSplitStartNumber.equals(""))
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập trường Từ số hóa đơn", false));
            validInput = false;
        }
        else
        {
            if (!ValidateUtils.isMaxLength(billSplitStartNumber, maxLength)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Từ số hóa đơn không dài quá 10 ký tự", false));
                validInput = false;
            } else if (!ValidateUtils.isInteger(billSplitStartNumber)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Từ số hóa đơn không phải là số", false));
                validInput = false;
            }
        }


        if (this.billSplitEndNumber == null || this.billSplitEndNumber.equals(""))
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập trường Đến số hóa đơn", false));
            validInput = false;
        }
        else
        {
            if (!ValidateUtils.isMaxLength(billSplitEndNumber, maxLength)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Đến số hóa đơn không dài quá 10 ký tự", false));
                validInput = false;
            } else if (!ValidateUtils.isInteger(billSplitEndNumber)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Đến số hóa đơn không phải là số", false));
                validInput = false;
            }
        }

        Long temp;

        if (validInput) {

            /** Now we check logic of input */
            temp = new Long(billSplitEndNumber) - new Long(billSplitStartNumber);
            if (temp < 0) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Từ số hóa đơn phải nhỏ hơn Đến số hóa đơn", false));
                validInput = false;
            } 
        }


        if (this.billSplitPerPartNumber == null || this.billSplitPerPartNumber.equals(""))
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập trường Số hóa đơn/dải", false));
            validInput = false;
        }
        else
        {
            if (!ValidateUtils.isMaxLength(billSplitPerPartNumber, maxLength)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số hóa đơn/dải không dài quá 10 ký tự", false));
                validInput = false;
            } else if (!ValidateUtils.isInteger(billSplitPerPartNumber)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số hóa đơn/dải không phải là số", false));
                validInput = false;
            }
        }

        if (validInput) {

            /** Now we check logic of input */
            temp = new Long(billSplitEndNumber) - new Long(billSplitStartNumber) + 1;
            
            if (temp < new Long(billSplitPerPartNumber)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Số hóa đơn/dải lơn hơn dải muốn tách", false));
            }
            
        }
        return errors;
    }
    
    
    
    
    
}
