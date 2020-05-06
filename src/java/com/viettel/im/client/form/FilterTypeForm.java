package com.viettel.im.client.form;

/**
 *
 * @author Administrator
 */
import com.viettel.im.database.BO.FilterType;
import org.apache.struts.action.ActionForm;

public class FilterTypeForm extends ActionForm {

    private Long filterTypeId;
    private String groupFilterRuleCode;
    private String name;
    private String notes;

    public FilterTypeForm() {
        resetForm();
    }

    public Long getFilterTypeId() {
        return filterTypeId;
    }

    public void setFilterTypeId(Long filterTypeId) {
        this.filterTypeId = filterTypeId;
    }

    public String getGroupFilterRuleCode() {
        return groupFilterRuleCode;
    }

    public void setGroupFilterRuleCode(String groupFilterRuleCode) {
        this.groupFilterRuleCode = groupFilterRuleCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public void resetForm() {
        this.filterTypeId = 0L;
        this.groupFilterRuleCode = "";
        this.name = "";
        this.notes = "";
    }

    public void copyDataToBO(FilterType filterType) {
        filterType.setFilterTypeId(this.getFilterTypeId());
        filterType.setGroupFilterRuleCode(this.getGroupFilterRuleCode());
        filterType.setName(this.getName());
        filterType.setNotes(this.getNotes());
    }

   public void copyDataFromBO(FilterType filterType) {
        this.setFilterTypeId(filterType.getFilterTypeId());
        this.setGroupFilterRuleCode(filterType.getGroupFilterRuleCode());
        this.setName(filterType.getName());
        this.setNotes(filterType.getNotes());
    }
}
