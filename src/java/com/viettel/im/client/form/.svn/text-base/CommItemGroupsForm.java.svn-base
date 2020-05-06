package com.viettel.im.client.form;

import com.viettel.im.database.BO.CommItemGroups;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author TheTM
 *
 */
public class CommItemGroupsForm extends ActionForm {

    private Long itemGroupId;
    private Long parentItemGroupId = null;
    private String groupName;
    private String reportType;
    private String status;

    public void resetForm() {
        this.itemGroupId = 0L;
        this.parentItemGroupId = 0L;
        this.groupName = "";
        this.reportType = "";
        this.status = "";
    }

    public void copyDataFromBO(CommItemGroups commItemGroups) {
        this.setItemGroupId(commItemGroups.getItemGroupId());
        this.setParentItemGroupId(commItemGroups.getParentItemGroupId());
        this.setGroupName(commItemGroups.getGroupName());
        this.setReportType(commItemGroups.getReportType());
        this.setStatus(commItemGroups.getStatus());
    }

    public void copyDataToBO(CommItemGroups commItemGroups) {
        commItemGroups.setItemGroupId(this.getItemGroupId());
//        commItemGroups.setParentItemGroupId(this.getParentItemGroupId());
        commItemGroups.setGroupName(this.getGroupName());
        commItemGroups.setReportType(this.getReportType());
        commItemGroups.setStatus(this.getStatus());
    }

    public CommItemGroupsForm() {
        resetForm();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getItemGroupId() {
        return itemGroupId;
    }

    public void setItemGroupId(long itemGroupId) {
        this.itemGroupId = itemGroupId;
    }

    public Long getParentItemGroupId() {
        return parentItemGroupId;
    }

    public void setParentItemGroupId(Long parentItemGroupId) {
        this.parentItemGroupId = parentItemGroupId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
