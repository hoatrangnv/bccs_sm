/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

/**
 *
 * @author BSS_CTV_THETM
 */
public class ReportInStockHomephoneForm {

    private Long stockTypeId;
    private String isdnType; //loai so (tra truoc, tra sau, dac biet)
    private Long status; //trang thai so (dang su dung, ngung su dung, so moi, so ghep lo)
    private String groupFilterRuleCode; //tap luat
    private Long filterTypeId; //nhom luat
    private Long ruleId; //luat
    private Long fromPrice; //tu gia
    private Long toPrice; //den gia
    private Long stockModelId; //mat hang
    private Long shopId; //ownerId
    private String shopCode; //ownerId
    private String shopName; //
    private String fromIsdn; //
    private String toIsdn; //
    private String pathExpFile; //
    private String province; //

    public ReportInStockHomephoneForm() {
        resetForm();
    }

    public void resetForm() {
        stockTypeId = 1L;
        isdnType = "";
        status = 0L;
        groupFilterRuleCode = "";
        filterTypeId = 0L;
        ruleId = 0L;
        fromPrice = null;
        toPrice = null;
        stockModelId = 0L;
        shopName = "";
        shopCode = "";
        fromIsdn = "";
        toIsdn = "";
        province ="";
    }

    public Long getFilterTypeId() {
        return filterTypeId;
    }

    public void setFilterTypeId(Long filterTypeId) {
        this.filterTypeId = filterTypeId;
    }

    public String getFromIsdn() {
        return fromIsdn;
    }

    public void setFromIsdn(String fromIsdn) {
        this.fromIsdn = fromIsdn;
    }

    public Long getFromPrice() {
        return fromPrice;
    }

    public void setFromPrice(Long fromPrice) {
        this.fromPrice = fromPrice;
    }

    public String getGroupFilterRuleCode() {
        return groupFilterRuleCode;
    }

    public void setGroupFilterRuleCode(String groupFilterRuleCode) {
        this.groupFilterRuleCode = groupFilterRuleCode;
    }

    public String getIsdnType() {
        return isdnType;
    }

    public void setIsdnType(String isdnType) {
        this.isdnType = isdnType;
    }

    public String getPathExpFile() {
        return pathExpFile;
    }

    public void setPathExpFile(String pathExpFile) {
        this.pathExpFile = pathExpFile;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getToIsdn() {
        return toIsdn;
    }

    public void setToIsdn(String toIsdn) {
        this.toIsdn = toIsdn;
    }

    public Long getToPrice() {
        return toPrice;
    }

    public void setToPrice(Long toPrice) {
        this.toPrice = toPrice;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
