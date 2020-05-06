package com.viettel.im.client.form;

import com.viettel.im.common.Constant;


/**
 *
 * author   :
 * date     :
 * desc     :
 * modified : tamdt1, 21/10/2010
 *
 */

public class SearchBillForm extends ConfirmReceiveBillForm {
    private Long invoiceType;
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

    /* So T/Q dau **/
    private String billCategoryStartNumber;

    /* So T/Q cuoi **/
    private String billCategoryEndNumber;

    /* Loai don vi cua hoa don **/
    private String billDepartmentKind;

    /* Ten don vi cua hoa don **/
    private String billDepartmentName;

    private String billDepartmentNameKey;

    private String billDepartmentNameB;

    private String billDepartmentType;

    /* Trang thai cua hoa don **/
    private String billSituation;

    /* Invoice List ID **/
    private String invoiceListId;

    private String subDepartmentName;

    private String status;

    private String includeStaff = "1";

    private String pathFile = "";

    private String resultMsg = "";

    public void resetForm() {
        invoiceType = Constant.INVOICE_TYPE_SALE;
        bookTypeId = 0L;
        serialCode = "";
        blockName = "";
        billSerial = "";
        billSerialKey = "";
        billCategory = "";
        billStartNumber = "";
        billEndNumber = "";
        billCategoryStartNumber = "";
        billCategoryEndNumber = "";
        billDepartmentKind = "";
        billDepartmentName = "";
        billDepartmentNameKey = "";
        billDepartmentNameB = "";
        billDepartmentType = "";
        billSituation = "";
        invoiceListId = "";
        subDepartmentName = "";
        status = "";
        includeStaff = "";
        pathFile = "";
        resultMsg = "";
    }


    public Long getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Long invoiceType) {
        this.invoiceType = invoiceType;
    }

    

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public Long getBookTypeId() {
        return bookTypeId;
    }

    public void setBookTypeId(Long bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

    public String getSubDepartmentName() {
        return subDepartmentName;
    }

    public void setSubDepartmentName(String subDepartmentName) {
        this.subDepartmentName = subDepartmentName;
    }

    public String getBillSerialKey() {
        return billSerialKey;
    }

    public void setBillSerialKey(String billSerialKey) {
        this.billSerialKey = billSerialKey;
    }

    public String getBillDepartmentType() {
        return billDepartmentType;
    }

    public void setBillDepartmentType(String billDepartmentType) {
        this.billDepartmentType = billDepartmentType;
    }

    public String getBillDepartmentNameB() {
        return billDepartmentNameB;
    }

    public void setBillDepartmentNameB(String billDepartmentNameB) {
        this.billDepartmentNameB = billDepartmentNameB;
    }

    public String getBillDepartmentNameKey() {
        return billDepartmentNameKey;
    }

    public void setBillDepartmentNameKey(String billDepartmentNameKey) {
        this.billDepartmentNameKey = billDepartmentNameKey;
    }
    
    public String getBillCategory() {
        return billCategory;
    }

    public void setBillCategory(String billCategory) {
        this.billCategory = billCategory;
    }

    public String getBillCategoryEndNumber() {
        return billCategoryEndNumber;
    }

    public void setBillCategoryEndNumber(String billCategoryEndNumber) {
        this.billCategoryEndNumber = billCategoryEndNumber;
    }

    public String getBillCategoryStartNumber() {
        return billCategoryStartNumber;
    }

    public void setBillCategoryStartNumber(String billCategoryStartNumber) {
        this.billCategoryStartNumber = billCategoryStartNumber;
    }

    public String getBillDepartmentKind() {
        return billDepartmentKind;
    }

    public void setBillDepartmentKind(String billDepartmentKind) {
        this.billDepartmentKind = billDepartmentKind;
    }

    public String getBillDepartmentName() {
        return billDepartmentName;
    }

    public void setBillDepartmentName(String billDepartmentName) {
        this.billDepartmentName = billDepartmentName;
    }

    public String getBillEndNumber() {
        return billEndNumber.trim();
    }

    public void setBillEndNumber(String billEndNumber) {
        this.billEndNumber = billEndNumber.trim();
    }

    public String getBillSerial() {
        return billSerial;
    }

    public void setBillSerial(String billSerial) {
        this.billSerial = billSerial;
        if (this.billSerial != null)
            this.billSerial = this.billSerial.trim();
    }

    public String getBillSituation() {
        return billSituation;
    }

    public void setBillSituation(String billSituation) {
        this.billSituation = billSituation;
    }

    public String getBillStartNumber() {
        return billStartNumber.trim();
    }

    public void setBillStartNumber(String billStartNumber) {
        this.billStartNumber = billStartNumber.trim();
    }

    public String getInvoiceListId() {
        return invoiceListId;
    }

    public void setInvoiceListId(String invoiceListId) {
        this.invoiceListId = invoiceListId;
    }

    public String getIncludeStaff() {
        return includeStaff;
    }

    public void setIncludeStaff(String includeStaff) {
        this.includeStaff = includeStaff;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }



}
