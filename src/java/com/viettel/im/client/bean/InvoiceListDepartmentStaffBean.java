/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;


/**
 *<P>InvoiceListDepartmentStaffBean for display in interface</P>
 * @author TungTV
 */
public class InvoiceListDepartmentStaffBean implements java.io.Serializable {

    private Long departmentStaffBeanId;

    private String name;
    
    public InvoiceListDepartmentStaffBean(){
        
    }


    /* Construct method */
    public InvoiceListDepartmentStaffBean(Long departmentStaffBeanId, String name) {
            this.departmentStaffBeanId = departmentStaffBeanId;
            this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDepartmentStaffBeanId() {
        return departmentStaffBeanId;
    }

    public void setDepartmentStaffBeanId(Long departmentStaffBeanId) {
        this.departmentStaffBeanId = departmentStaffBeanId;
    }

    
    
}
