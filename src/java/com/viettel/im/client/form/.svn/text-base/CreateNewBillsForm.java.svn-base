/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

import com.viettel.im.common.util.StringUtils;
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

public class CreateNewBillsForm extends ValidatorForm {
    private Long bookTypeId;
    private String serialCode;
    private String blockName;

    /* Serial hoa don **/
    private String billSerial;

    private String billSerialKey;
    /* Loai T/Q **/
    private String billCategory;

    /* So hoa don dau dai **/
    private String billStartNumber;
    
    /* So hoa don cuoi dai **/
    private String billEndNumber;

    /* Do dai lon nhat cua truong */
    private static int maxLength = 10;

    /** Chua id cua hoa don se bi xoa */
    private String[] deleteBill;

    public void resetForm() {
        this.bookTypeId = 0L;
        this.serialCode = "";
        this.blockName = "";
        this.billSerial = "";
        this.billCategory = "";
        this.billStartNumber = "";
        this.billEndNumber = "";
        this.deleteBill = null;
        this.billSerialKey = null;
    }

    public Long getBookTypeId() {
        return bookTypeId;
    }

    public void setBookTypeId(Long bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public String getBillSerialKey() {
        return billSerialKey;
    }

    public void setBillSerialKey(String billSerialKey) {
        this.billSerialKey = billSerialKey;
    }

    public String getBillCategory() {
        return billCategory;
    }

    public void setBillCategory(String billCategory) {
        this.billCategory = billCategory;
    }

    public String getBillEndNumber() {
        return billEndNumber;
    }

    public void setBillEndNumber(String billEndNumber) {
        this.billEndNumber = billEndNumber;
    }

    public String getBillSerial() {
        return billSerial;
    }

    public void setBillSerial(String billSerial) {
        this.billSerial = billSerial;
    }

    public String getBillStartNumber() {
        return billStartNumber;
    }

    public void setBillStartNumber(String billStartNumber) {
        this.billStartNumber = billStartNumber;
    }

    public String[] getDeleteBill() {
        return deleteBill;
    }

    public void setDeleteBill(String[] deleteBill) {
        this.deleteBill = deleteBill;
    }


    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

            ActionErrors errors = new ActionErrors();
            if (this.billSerial == null || this.billSerial.equals("")) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập trường Serial của hóa đơn", false));
            } else {
                if (!ValidateUtils.isMaxLength(billSerial, maxLength)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Serial của hóa đơn không dài quá 10 ký tự", false));
                } else if (!StringUtils.checkAlphabeUpCaseNumber(billSerial)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Serial của hóa đơn không đúng định dạng", false));
                }
            }
            if (this.billCategory == null || this.billCategory.equals("")) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa chọn loại T/Q", false));
            }
            if (this.billStartNumber == null || this.billStartNumber.equals("")) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập số hóa đơn bắt đầu", false));
            } else {
                if (!ValidateUtils.isMaxLength(billStartNumber, maxLength)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường số hóa đơn bắt đầu không dài quá 10 ký tự", false));
                } else if (!ValidateUtils.isInteger(billStartNumber)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường số hóa đơn bắt đầu phải là số", false));
                }
            }
            if (this.billEndNumber == null || this.billEndNumber.equals("")) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập số hóa đơn kết thúc", false));
            } else {
                if (!ValidateUtils.isMaxLength(billEndNumber, maxLength)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường số hóa đơn kết thúc không dài quá 10 ký tự", false));
                } else if (!ValidateUtils.isInteger(billEndNumber)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường số hóa đơn kết thúc phải là số", false));
                }
            }
            return errors;

    }
}
