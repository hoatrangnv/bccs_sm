package com.viettel.im.client.form;

import com.viettel.im.database.BO.StockTypeProfile;
import java.util.ArrayList;
import java.util.List;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tamdt1
 * form chua cac thong tin profile hang hoa
 *
 */
public class StockTypeProfileForm extends ActionForm {

    private Long profileId;
    private Long stockTypeId;
    private String profileName;
    private String description;
    private Long startLine;
    private String separatedChar;
    private String[] arrAvailbleField;
    private String[] arrSelectedField;
    private String stockTypeName;
    private String linePattern;
    private String[] arrStockModel;
    private List listStockModel = new ArrayList();
    private Long[] arrStockModelL;
    private String[] selectedItems;
    //
    private String stockModelName;
    private String stockModelCode;

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }

    public StockTypeProfileForm() {
        profileId = 0L;
        stockTypeId = 0L;
        profileName = "";
        description = "";
        startLine = 1L;
        separatedChar = ",";
        stockTypeName = "";
        linePattern = "";
    }

    public void copyDataFromBO(StockTypeProfile stockTypeProfile) {
        this.setProfileId(stockTypeProfile.getProfileId());
        this.setStockTypeId(stockTypeProfile.getStockTypeId());
        this.setProfileName(stockTypeProfile.getProfileName());
        this.setDescription(stockTypeProfile.getDescription());
        this.setStartLine(stockTypeProfile.getStartLine());
        this.setSeparatedChar(stockTypeProfile.getSeparatedChar());
    }

    public void copyDataToBO(StockTypeProfile stockTypeProfile) {
        stockTypeProfile.setProfileId(this.getProfileId());
        stockTypeProfile.setStockTypeId(this.getStockTypeId());
        stockTypeProfile.setProfileName(this.getProfileName());
        stockTypeProfile.setDescription(this.getDescription());
        stockTypeProfile.setStartLine(this.getStartLine());
        stockTypeProfile.setSeparatedChar(this.getSeparatedChar());
    }

    public void resetForm() {
        profileId = 0L;
        stockTypeId = 0L;
        profileName = "";
        description = "";
        startLine = 1L;
        separatedChar = ",";
        stockTypeName = "";
        linePattern = "";
    }

    public List getListStockModel() {
        return listStockModel;
    }

    public void setListStockModel(List listStockModel) {
        this.listStockModel = listStockModel;
    }

    public Long[] getArrStockModelL() {
        return arrStockModelL;
    }

    public void setArrStockModelL(Long[] arrStockModelL) {
        this.arrStockModelL = arrStockModelL;
    }

    public String[] getArrStockModel() {
        return arrStockModel;
    }

    public void setArrStockModel(String[] arrStockModel) {
        this.arrStockModel = arrStockModel;
    }

    public String[] getArrAvailbleField() {
        return arrAvailbleField;
    }

    public void setArrAvailbleField(String[] arrAvailbleField) {
        this.arrAvailbleField = arrAvailbleField;
    }

    public String[] getArrSelectedField() {
        return arrSelectedField;
    }

    public void setArrSelectedField(String[] arrSelectedField) {
        this.arrSelectedField = arrSelectedField;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinePattern() {
        return linePattern;
    }

    public void setLinePattern(String linePattern) {
        this.linePattern = linePattern;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getSeparatedChar() {
        return separatedChar;
    }

    public void setSeparatedChar(String separatedChar) {
        this.separatedChar = separatedChar;
    }

    public Long getStartLine() {
        return startLine;
    }

    public void setStartLine(Long startLine) {
        this.startLine = startLine;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getStockTypeName() {
        return stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }

    public String[] getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(String[] selectedItems) {
        this.selectedItems = selectedItems;
    }

}
