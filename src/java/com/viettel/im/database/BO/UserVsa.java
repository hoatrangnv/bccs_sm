/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

/**
 *
 * @author toandv12
 */
public class UserVsa {
    
    private String userName;
    private String fullName;
    private String status;
    private String email;
    private String telephone;
    private String cellphone;
    private String staffCode;

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }
    

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public UserVsa() {
    }

    public UserVsa(String userName, String fullName, String status, String email, String telephone) {
        this.userName = userName;
        this.fullName = fullName;
        this.status = status;
        this.email = email;
        this.telephone = telephone;
    }

    public UserVsa(String userName, String fullName, String status, String email, String telephone, String cellphone) {
        this.userName = userName;
        this.fullName = fullName;
        this.status = status;
        this.email = email;
        this.telephone = telephone;
        this.cellphone = cellphone;
    }
    public UserVsa(String userName, String fullName, String status, String email, String telephone, String cellphone,String staffCode) {
        this.userName = userName;
        this.fullName = fullName;
        this.status = status;
        this.email = email;
        this.telephone = telephone;
        this.cellphone = cellphone;
        this.staffCode= staffCode;
    }
    
    
}
