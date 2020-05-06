package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.CommItems;

/**
 *
 * @author tamdt1
 * 
 */
public class CommItemsForm {

    private Long itemId;
    private Long itemGroupId;
    private Long counterId;
    private String itemName;
    private String unit;
    private String checkedDoc;
    private String inputType;
    private String reportType;
    private String itemOrder;
    private String startDate;
    private String endDate;
    private String status;
    private String description;

    private String[] arrCounterParam;
    private String commItemGroupName;

    public CommItemsForm() {
        resetForm();
    }

    public void resetForm() {
        itemId = 0L;
        itemGroupId = 0L;
        counterId = 0L;
        itemName = "";
        unit = "";
        checkedDoc = "";
        inputType = "";
        reportType = "";
        itemOrder = "";
        startDate = "";
        endDate = "";
        status = "";
        description = "";
    }

    public void copyDataFromBO(CommItems commItems) {
        this.setItemId(commItems.getItemId());
        this.setItemGroupId(commItems.getItemGroupId());
        this.setCounterId(commItems.getCounterId());
        this.setItemName(commItems.getItemName());
        this.setUnit(commItems.getUnit());
        this.setCheckedDoc(commItems.getCheckedDoc());
        this.setInputType(commItems.getInputType());
        this.setReportType(commItems.getReportType());
        this.setItemOrder(commItems.getItemOrder().toString());
        try {
            this.setStartDate(DateTimeUtils.convertDateToString(commItems.getStartDate()));
        } catch (Exception ex) {
            this.setStartDate(null);
        }
        try {
            this.setEndDate(DateTimeUtils.convertDateToString(commItems.getEndDate()));
        } catch (Exception ex) {
            this.setEndDate(null);
        }
        this.setStatus(commItems.getStatus());
        this.setDescription(commItems.getDescription());
    }

    public void copyDataToBO(CommItems commItems) {
        commItems.setItemId(this.getItemId());
        commItems.setItemGroupId(this.getItemGroupId());
        commItems.setCounterId(this.getCounterId());
        commItems.setItemName(this.getItemName());
        commItems.setUnit(this.getUnit());
        commItems.setCheckedDoc(this.getCheckedDoc());
        commItems.setInputType(this.getInputType());
        commItems.setReportType(this.getReportType());
        commItems.setItemOrder(Long.parseLong(this.getItemOrder()));
        try {
            commItems.setStartDate(DateTimeUtils.convertStringToDate(this.getStartDate()));
        } catch (Exception ex) {
            commItems.setStartDate(null);
        }
        try {
            commItems.setEndDate(DateTimeUtils.convertStringToDate(this.getEndDate()));
        } catch (Exception ex) {
            commItems.setEndDate(null);
        }
        commItems.setStatus(this.getStatus());
        commItems.setDescription(this.getDescription());
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public Long getItemGroupId() {
        return itemGroupId;
    }

    public void setItemGroupId(Long itemGroupId) {
        this.itemGroupId = itemGroupId;
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

    public String getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(String itemOrder) {
        this.itemOrder = itemOrder;
    }



    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String[] getArrCounterParam() {
        return arrCounterParam;
    }

    public void setArrCounterParam(String[] arrCounterParam) {
        this.arrCounterParam = arrCounterParam;
    }

    public String getCommItemGroupName() {
        return commItemGroupName;
    }

    public void setCommItemGroupName(String commItemGroupName) {
        this.commItemGroupName = commItemGroupName;
    }

}
