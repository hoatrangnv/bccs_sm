/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

import java.io.File;

/**
 *
 * @author tronglv
 */
public class ImportBankReceiptForm {

    //search ImportBankReceipt
    private String fromDateSearch;
    private String toDateSearch;
    private String bankCodeSearch;
    private String bankNameSearch;
    private String bankAccountNoSearch;
    

    private String impString;
    private File impFile;
    private String serverFileName; //ten file tren server trong truong hop upload
    private String clientFileName; //ten file tren client trong truong hop upload

    public ImportBankReceiptForm() {
    }

    public String getBankAccountNoSearch() {
        return bankAccountNoSearch;
    }

    public void setBankAccountNoSearch(String bankAccountNoSearch) {
        this.bankAccountNoSearch = bankAccountNoSearch;
    }

    public String getBankCodeSearch() {
        return bankCodeSearch;
    }

    public void setBankCodeSearch(String bankCodeSearch) {
        this.bankCodeSearch = bankCodeSearch;
    }

    public String getBankNameSearch() {
        return bankNameSearch;
    }

    public void setBankNameSearch(String bankNameSearch) {
        this.bankNameSearch = bankNameSearch;
    }

    public String getFromDateSearch() {
        return fromDateSearch;
    }

    public void setFromDateSearch(String fromDateSearch) {
        this.fromDateSearch = fromDateSearch;
    }

    public String getToDateSearch() {
        return toDateSearch;
    }

    public void setToDateSearch(String toDateSearch) {
        this.toDateSearch = toDateSearch;
    }

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    

    
}
