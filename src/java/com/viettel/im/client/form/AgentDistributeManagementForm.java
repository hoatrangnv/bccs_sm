/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

/**
 *
 * @author kdvt_hieptd
 */
public class AgentDistributeManagementForm {

    private String clientFileName;
    private String serverFileName;
    private Long totalMoney;
    private Long totalSubject;
    private Long stockTypeId;
    private String isdnType; //loai so (tra truoc, tra sau, dac biet)
    private Long status; //trang thai so (dang su dung, ngung su dung, so moi, so ghep lo)
    private String groupFilterRuleCode; //tap luat (phuc vu viec tim kiem so dep)
    private Long filterTypeId; //nhom luat (phuc vu viec tim kiem so dep)
    private Long ruleId; //luat (phuc vu viec tim kiem so dep)
    private Long fromPrice; //tu gia
    private Long totalMoneyAgent;
    private Long currentMoneyAgent;
     private Long toPrice; //den gia
    private Long stockModelId; //mat hang
    private Long shopId; //ownerId
    private String shopCode;
    private String shopName; //ownerId
    private String fromIsdn; //
    private String toIsdn; //
    private String count;
    private String staDate;
    private String endDate;
    private String shopCodeAgent;
    private String pathExpFile;
    private String pathExpFile2;
    private Long fileType;

    public Long getFileType() {
        return fileType;
    }

    public void setFileType(Long fileType) {
        this.fileType = fileType;
    }

    public String getPathExpFile2() {
        return pathExpFile2;
    }

    public void setPathExpFile2(String pathExpFile2) {
        this.pathExpFile2 = pathExpFile2;
    }
    private boolean isSearchAdvance; //Tim mo rong
     private String isdnAdvance;

    public String getIsdnAdvance() {
        return isdnAdvance;
    }

    public void setIsdnAdvance(String isdnAdvance) {
        this.isdnAdvance = isdnAdvance;
    }

    public boolean getIsSearchAdvance() {
        return isSearchAdvance;
    }

    public void setIsSearchAdvance(boolean isSearchAdvance) {
        this.isSearchAdvance = isSearchAdvance;
    }

    public String getPathExpFile() {
        return pathExpFile;
    }

    public void setPathExpFile(String pathExpFile) {
        this.pathExpFile = pathExpFile;
    }

    public Long getCurrentMoneyAgent() {
        return currentMoneyAgent;
    }

    public void setCurrentMoneyAgent(Long currentMoneyAgent) {
        this.currentMoneyAgent = currentMoneyAgent;
    }

    public Long getTotalMoneyAgent() {
        return totalMoneyAgent;
    }

    public void setTotalMoneyAgent(Long totalMoneyAgent) {
        this.totalMoneyAgent = totalMoneyAgent;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getStaDate() {
        return staDate;
    }

    public void setStaDate(String staDate) {
        this.staDate = staDate;
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


    public String getShopCodeAgent() {
        return shopCodeAgent;
    }

    public void setShopCodeAgent(String shopCodeAgent) {
        this.shopCodeAgent = shopCodeAgent;
    }

    public String getShopNameAgent() {
        return shopNameAgent;
    }

    public void setShopNameAgent(String shopNameAgent) {
        this.shopNameAgent = shopNameAgent;
    }
    private String shopNameAgent;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getTotalSubject() {
        return totalSubject;
    }

    public void setTotalSubject(Long totalSubject) {
        this.totalSubject = totalSubject;
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

    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }
}