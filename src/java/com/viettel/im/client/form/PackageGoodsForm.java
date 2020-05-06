/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

/**
 *
 * @author User
 */
public class PackageGoodsForm {

    private Long packageGoodsId;
    private String packageCode;
    private String packageName;
    private String fromDate;
    private String toDate;
    private Long status;
    private String decriptions;

    public PackageGoodsForm() {
    }

    public void reset() {
        packageGoodsId = null;
        packageCode = null;
        packageName = null;
        fromDate = null;
        toDate = null;
        status = null;
        decriptions = null;

    }

    public Long getPackageGoodsId() {
        return packageGoodsId;
    }

    public void setPackageGoodsId(Long packageGoodsId) {
        this.packageGoodsId = packageGoodsId;
    }

    public String getDecriptions() {
        return decriptions;
    }

    public void setDecriptions(String decriptions) {
        this.decriptions = decriptions;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
