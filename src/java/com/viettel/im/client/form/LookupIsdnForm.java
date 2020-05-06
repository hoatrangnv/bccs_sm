package com.viettel.im.client.form;

/**
 *
 * @author tamdt1
 * form phuc vu action tra cuu isdn
 *
 */
import com.viettel.im.common.Constant;
import java.io.File;
import java.util.List;

public class LookupIsdnForm {

    private Long stockTypeId;
    private String isdnType; //loai so (tra truoc, tra sau, dac biet)
    private Long status; //trang thai so (dang su dung, ngung su dung, so moi, so ghep lo)
    private String groupFilterRuleCode; //tap luat (phuc vu viec tim kiem so dep)
    private Long filterTypeId; //nhom luat (phuc vu viec tim kiem so dep)
    private Long ruleId; //luat (phuc vu viec tim kiem so dep)
    private Long fromPrice; //tu gia
    private Long toPrice; //den gia
    private Long stockModelId; //mat hang
    private Long shopId; //ownerId
    private String shopCode; //ownerId
    private String fromIsdn; //
    private String toIsdn; //
    private String count;
    //
    private String shopName; //
    private String searchType = "1"; //
    private boolean isSearchAdvance; //Tim mo rong
    private String isdnAdvance; //So ISDN tim kiem mo rong
    private boolean isSearchInFile; //Tim theo file
    private File isdnFile; //Ten file tim kiem
    private String clientIsdnFile; //Ten file tim kiem
    private String serverIsdnFile; //Ten file tim kiem
    private List isdnList; //Danh sach ISDN phu hop doc tu file Excel
    private List isdnErrorList; //Danh sach ISDN khong phu hop doc tu file Excel
    private String resultMsg; //Message tra ve khi doc file Excel
    private String pathExpFile; //
    private String stockTypeIdNormal;
    private String stockTypeIdAdvance;
    private String stockTypeIdInFile;
    private String staDate;
    private String endDate;
    private Long reportFileType; //kieu xuat bao cao

    public LookupIsdnForm() {
        resetForm();
    }

    public void resetForm() {
        stockTypeId = 0L;
        isdnType = "";
        status = 0L;
        groupFilterRuleCode = "";
        filterTypeId = 0L;
        ruleId = 0L;
        fromPrice = null;
        toPrice = null;
        stockModelId = 0L;
        shopId = 0L;
        shopCode = "";
        fromIsdn = "";
        toIsdn = "";

        //
        shopName = "";

        searchType = "1";

        isSearchAdvance = false;
        isdnAdvance = "";

        isSearchInFile = false;
        isdnFile = null;
        clientIsdnFile = "";
        serverIsdnFile = "";
        isdnList = null;
        isdnErrorList = null;
        resultMsg = "";
        pathExpFile = "";

        stockTypeIdNormal = "";
        stockTypeIdAdvance = "";
        stockTypeIdInFile = "";
        count = "1000";
        staDate = null;
        endDate = null;
        reportFileType = Constant.REPORT_FILE_TYPE_EXCEL;

    }

    public String getStockTypeIdAdvance() {
        return stockTypeIdAdvance;
    }

    public void setStockTypeIdAdvance(String stockTypeIdAdvance) {
        this.stockTypeIdAdvance = stockTypeIdAdvance;
    }

    public String getStockTypeIdInFile() {
        return stockTypeIdInFile;
    }

    public void setStockTypeIdInFile(String stockTypeIdInFile) {
        this.stockTypeIdInFile = stockTypeIdInFile;
    }

    public String getStockTypeIdNormal() {
        return stockTypeIdNormal;
    }

    public void setStockTypeIdNormal(String stockTypeIdNormal) {
        this.stockTypeIdNormal = stockTypeIdNormal;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
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

    public boolean getIsSearchAdvance() {
        return isSearchAdvance;
    }

    public void setIsSearchAdvance(boolean isSearchAdvance) {
        this.isSearchAdvance = isSearchAdvance;
    }

    public String getIsdnAdvance() {
        return isdnAdvance;
    }

    public void setIsdnAdvance(String isdnAdvance) {
        this.isdnAdvance = isdnAdvance;
    }

    public boolean getIsSearchInFile() {
        return isSearchInFile;
    }

    public void setIsSearchInFile(boolean isSearchInFile) {
        this.isSearchInFile = isSearchInFile;
    }

    public File getIsdnFile() {
        return isdnFile;
    }

    public void setIsdnFile(File isdnFile) {
        this.isdnFile = isdnFile;
    }

    public List getIsdnErrorList() {
        return isdnErrorList;
    }

    public void setIsdnErrorList(List isdnErrorList) {
        this.isdnErrorList = isdnErrorList;
    }

    public List getIsdnList() {
        return isdnList;
    }

    public void setIsdnList(List isdnList) {
        this.isdnList = isdnList;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getClientIsdnFile() {
        return clientIsdnFile;
    }

    public void setClientIsdnFile(String clientIsdnFile) {
        this.clientIsdnFile = clientIsdnFile;
    }

    public String getServerIsdnFile() {
        return serverIsdnFile;
    }

    public void setServerIsdnFile(String serverIsdnFile) {
        this.serverIsdnFile = serverIsdnFile;
    }

    public void setPathExpFile(String pathExpFile) {
        this.pathExpFile = pathExpFile;
    }

    public boolean isIsSearchAdvance() {
        return isSearchAdvance;
    }

    public boolean isIsSearchInFile() {
        return isSearchInFile;
    }

    public String getPathExpFile() {
        return pathExpFile;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStaDate() {
        return staDate;
    }

    public void setStaDate(String staDate) {
        this.staDate = staDate;
    }

    public Long getReportFileType() {
        return reportFileType;
    }

    public void setReportFileType(Long reportFileType) {
        this.reportFileType = reportFileType;
    }

}
