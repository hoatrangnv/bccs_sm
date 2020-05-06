/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

/**
 *
 * @author kdvt_tronglv
 */
public class PackageGoodsV2Form {
    
    private Long packageGoodsId;
    private String code;
    private String name;
    private String note;
    private Long status;
    
    private String codeSearch;
    private String nameSearch;
    private Long statusSearch;

    public PackageGoodsV2Form() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeSearch() {
        return codeSearch;
    }

    public void setCodeSearch(String codeSearch) {
        this.codeSearch = codeSearch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getPackageGoodsId() {
        return packageGoodsId;
    }

    public void setPackageGoodsId(Long packageGoodsId) {
        this.packageGoodsId = packageGoodsId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStatusSearch() {
        return statusSearch;
    }

    public void setStatusSearch(Long statusSearch) {
        this.statusSearch = statusSearch;
    }
    
    
    
}
