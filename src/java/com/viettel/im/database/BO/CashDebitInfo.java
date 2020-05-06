/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

import java.util.List;

/**
 *
 * @author dev_linh
 */
public class CashDebitInfo {

    private String branch;
    private long debitBranch;
    private List<DebitUser> listDebitUser;
    private List<DebitTrans> listDebitTrans;
    private List<RequestBorrowCash> listRequest;

    public List<RequestBorrowCash> getListRequest() {
        return listRequest;
    }

    public void setListRequest(List<RequestBorrowCash> listRequest) {
        this.listRequest = listRequest;
    }

    public long getDebitBranch() {
        return debitBranch;
    }

    public void setDebitBranch(long debitBranch) {
        this.debitBranch = debitBranch;
    }

    public List<DebitTrans> getListDebitTrans() {
        return listDebitTrans;
    }

    public void setListDebitTrans(List<DebitTrans> listDebitTrans) {
        this.listDebitTrans = listDebitTrans;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<DebitUser> getListDebitUser() {
        return listDebitUser;
    }

    public void setListDebitUser(List<DebitUser> listDebitUser) {
        this.listDebitUser = listDebitUser;
    }
}
