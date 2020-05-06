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
public class BillsDepartmentForm extends ActionForm {

    /* Category of bill **/
    private String billDepartmentKind;
    private String billDepartmentCode;
    /* Department of bill **/
    private String billDepartmentName;
    private String billDepartmentNameKey;
    private String billDepartmentNameB;
    private String code;
    private String name;

    public String getBillDepartmentCode() {
        return billDepartmentCode;
    }

    public void setBillDepartmentCode(String billDepartmentCode) {
        this.billDepartmentCode = billDepartmentCode;
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

    public String getBillDepartmentNameKey() {
        return billDepartmentNameKey;
    }

    public void setBillDepartmentNameKey(String billDepartmentNameKey) {
        this.billDepartmentNameKey = billDepartmentNameKey;
    }

    public String getBillDepartmentNameB() {
        return billDepartmentNameB;
    }

    public void setBillDepartmentNameB(String billDepartmentNameB) {
        this.billDepartmentNameB = billDepartmentNameB;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
