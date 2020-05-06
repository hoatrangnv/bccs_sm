/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

import java.util.Date;

/**
 *
 * @author MrSun
 */
public class AnyPayTransManagementForm {

    private String pShopCodeS;    //Ma cua hang quan ly
    private String pShopNameS;    //Ten cua hang quan ly
    private String pStaffCodeS;    //Ma nhan vien quan ly
    private String pStaffNameS;    //Ten nhan vien quan ly
    
    private String pTransIdS;    //Ma giao dich
    private String pTransTypeS;  //Loai giao dich (chuyen tien den tk AnyPay(1), nap tien cho khach hang(2)
    private String pFromIsdnS;       //So Isdn cua tai khoan AnyPay gui
    private String pToIsdnS;       //So Isdn cua tai khoan AnyPay nhan hoac cua khach hang
    private String pFromDateS;   //Giao dich tu ngay
    private String pToDateS;     //Giao dich den ngay
    private String pStatusS;     //Trang thai da huy(0), chua huy(1)

    private String[] pTransIdList;      //Danh sach ma giao dich duoc chon
    private  Long pReasonId;

    public AnyPayTransManagementForm() {
    }

    public Long getPReasonId() {
        return pReasonId;
    }

    public void setPReasonId(Long pReasonId) {
        this.pReasonId = pReasonId;
    }

    public String getPFromDateS() {
        return pFromDateS;
    }

    public void setPFromDateS(String pFromDateS) {
        this.pFromDateS = pFromDateS;
    }

    public String getPFromIsdnS() {
        return pFromIsdnS;
    }

    public void setPFromIsdnS(String pFromIsdnS) {
        this.pFromIsdnS = pFromIsdnS;
    }

    public String getPShopCodeS() {
        return pShopCodeS;
    }

    public void setPShopCodeS(String pShopCodeS) {
        this.pShopCodeS = pShopCodeS;
    }

    public String getPShopNameS() {
        return pShopNameS;
    }

    public void setPShopNameS(String pShopNameS) {
        this.pShopNameS = pShopNameS;
    }

    public String getPStaffCodeS() {
        return pStaffCodeS;
    }

    public void setPStaffCodeS(String pStaffCodeS) {
        this.pStaffCodeS = pStaffCodeS;
    }

    public String getPStaffNameS() {
        return pStaffNameS;
    }

    public void setPStaffNameS(String pStaffNameS) {
        this.pStaffNameS = pStaffNameS;
    }

    public String getPStatusS() {
        return pStatusS;
    }

    public void setPStatusS(String pStatusS) {
        this.pStatusS = pStatusS;
    }

    public String getPToDateS() {
        return pToDateS;
    }

    public void setPToDateS(String pToDateS) {
        this.pToDateS = pToDateS;
    }

    public String getPToIsdnS() {
        return pToIsdnS;
    }

    public void setPToIsdnS(String pToIsdnS) {
        this.pToIsdnS = pToIsdnS;
    }

    public String[] getPTransIdList() {
        return pTransIdList;
    }

    public void setPTransIdList(String[] pTransIdList) {
        this.pTransIdList = pTransIdList;
    }

    public String getPTransIdS() {
        return pTransIdS;
    }

    public void setPTransIdS(String pTransIdS) {
        this.pTransIdS = pTransIdS;
    }

    public String getPTransTypeS() {
        return pTransTypeS;
    }

    public void setPTransTypeS(String pTransTypeS) {
        this.pTransTypeS = pTransTypeS;
    }

    
}
