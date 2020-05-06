package com.viettel.im.client.bean;

import com.viettel.im.database.BO.*;
import com.viettel.database.BO.BasicBO;
import java.util.Date;

/**
 * CommItems entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CommItemsCounterBean extends BasicBO {

    private Long itemId;
    private String itemName;
    private String checkedDoc;
    private String reportType;
    private Long counterId;
    private String functionName;

    public String getCheckedDoc() {
        return checkedDoc;
    }

    public void setCheckedDoc(String checkedDoc) {
        this.checkedDoc = checkedDoc;
    }

    public Long getCounterId() {
        return counterId;
    }

    public void setCounterId(Long counterId) {
        this.counterId = counterId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}