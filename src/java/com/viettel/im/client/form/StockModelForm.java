package com.viettel.im.client.form;

import com.sun.star.text.ControlCharacter;
import com.viettel.im.database.BO.StockModel;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tamdt1
 * date 15/03/2008
 *
 */
public class StockModelForm extends ActionForm {

    private Long stockModelId;
    private String stockModelCode;
    private Long stockTypeId;
    private String name;
    private Boolean checkSerial;
    private Boolean checkDeposit;
    private Boolean checkDial;
    private String unit;
    private String notes;
    private Long telecomServiceId;
    private Boolean checkView;
    private String accountingModelCode;
    private String accountingModelName;
    /*
     * @Author : hieptd
     * @Description : add check 
     */
    ///@Use : use for search like select box
    private Integer checkStockChannel;
    ///@Use : use for add, view, edit like checkbox
    private Boolean checkStockChannelInfor;

    //
    private String stockTypeName;
    private String stockModelName;
    private Long sourcePrice;
    //TruongNQ5 20140725 R6237
    private Long rvnServiceId;
    private Long rvnServiceQualityId;
    private Long serviceIndex;
    private Long serviceQualityIndex;
    //9969
    private Long stockTypeGroup;
    private Boolean checkItem;
    private String sourcePriceStr;

    public Long getStockTypeGroup() {
        return stockTypeGroup;
    }

    public void setStockTypeGroup(Long stockTypeGroup) {
        this.stockTypeGroup = stockTypeGroup;
    }

    public Boolean getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(Boolean checkItem) {
        this.checkItem = checkItem;
    }

    public String getSourcePriceStr() {
        return sourcePriceStr;
    }

    public void setSourcePriceStr(String sourcePriceStr) {
        this.sourcePriceStr = sourcePriceStr;
    }

    public Long getServiceIndex() {
        return serviceIndex;
    }

    public void setServiceIndex(Long serviceIndex) {
        this.serviceIndex = serviceIndex;
    }

    public Long getServiceQualityIndex() {
        return serviceQualityIndex;
    }

    public void setServiceQualityIndex(Long serviceQualityIndex) {
        this.serviceQualityIndex = serviceQualityIndex;
    }

    public Long getRvnServiceId() {
        return rvnServiceId;
    }

    public void setRvnServiceId(Long rvnServiceId) {
        this.rvnServiceId = rvnServiceId;
    }

    public Long getRvnServiceQualityId() {
        return rvnServiceQualityId;
    }

    public void setRvnServiceQualityId(Long rvnServiceQualityId) {
        this.rvnServiceQualityId = rvnServiceQualityId;
    }

    //End TruongNQ5
    public StockModelForm() {
        //resetForm();
    }

    public void resetForm() {
        stockModelId = 0L;
        stockModelCode = "";
        stockTypeId = 0L;
        name = "";
        telecomServiceId = 0L;
        checkSerial = false;
        checkDeposit = false;
        checkDial = false;
        checkStockChannelInfor = false;
        unit = "";
        notes = "";
        checkView = false;
        accountingModelCode = "";
        accountingModelName = "";
    }

    public void copyDataFromBO(StockModel stockModel) {
        this.setStockModelId(stockModel.getStockModelId());
        this.setStockModelCode(stockModel.getStockModelCode());
        this.setStockTypeId(stockModel.getStockTypeId());
        this.setName(stockModel.getName());
        if (stockModel.getCheckSerial() != null && stockModel.getCheckSerial().equals(1L)) {
            this.setCheckSerial(true);
        } else {
            this.setCheckSerial(false);
        }
        if (stockModel.getCheckDeposit() != null && stockModel.getCheckDeposit().equals(1L)) {
            this.setCheckDeposit(true);
        } else {
            this.setCheckDeposit(false);
        }
        if (stockModel.getCheckDial() != null && stockModel.getCheckDial().equals(1L)) {
            this.setCheckDial(true);
        } else {
            this.setCheckDial(false);
        }
        /***
         * @Author : Hieptd
         * @Description : Modify
         */
        if(stockModel.getCheckStockChannel() != null && stockModel.getCheckStockChannel().equals(1)){
            setCheckStockChannelInfor(true);
        }else{
            setCheckStockChannelInfor(false);
        }
        this.setUnit(stockModel.getUnit());
        this.setNotes(stockModel.getNotes());
        this.setTelecomServiceId(stockModel.getTelecomServiceId());
        this.setAccountingModelCode(stockModel.getAccountingModelCode());
        this.setAccountingModelName(stockModel.getAccountingModelName());
        //9969
        this.setStockTypeGroup(stockModel.getStockTypeGroup());
        if (stockModel.getStockModelType() != null && stockModel.getStockModelType().equals(2L)) {
            this.setCheckItem(true);
        } else {
            this.setCheckItem(false);
        }
        if (stockModel.getSourcePrice() != null) {
            this.setSourcePriceStr(String.valueOf(stockModel.getSourcePrice()));
        }
    }

    public void copyDataToBO(StockModel stockModel) {
        stockModel.setStockModelId(this.getStockModelId());
        stockModel.setStockModelCode(this.getStockModelCode());
        stockModel.setStockTypeId(this.getStockTypeId());
        stockModel.setName(this.getName());
        if (this.getCheckSerial()) {
            stockModel.setCheckSerial(1L);
        } else {
            stockModel.setCheckSerial(0L);
        }
        if (this.getCheckDeposit() != null && this.getCheckDeposit()) {
            stockModel.setCheckDeposit(1L);
        } else {
            stockModel.setCheckDeposit(0L);
        }
        if (this.getCheckDial()) {
            stockModel.setCheckDial(1L);
        } else {
            stockModel.setCheckDial(0L);
        }
        if(this.getCheckStockChannelInfor()){
            stockModel.setCheckStockChannel(1);
        }else{
            stockModel.setCheckStockChannel(0);
        }
        stockModel.setUnit(this.getUnit());
        stockModel.setNotes(this.getNotes());
        stockModel.setTelecomServiceId(this.getTelecomServiceId());
        stockModel.setAccountingModelCode(this.getAccountingModelCode());
        stockModel.setAccountingModelName(this.getAccountingModelName());
        //9969
        stockModel.setStockTypeGroup(this.getStockTypeGroup());
        if (this.getCheckItem()) {
            stockModel.setStockModelType(2L);
        } else {
            stockModel.setStockModelType(0L);
        }
        if (this.getSourcePriceStr() != null && !this.getSourcePriceStr().trim().equals("")) {
            try {
                stockModel.setSourcePrice(Double.parseDouble(this.getSourcePriceStr().trim()));
            } catch (Exception e) {
            }
        }
    }

    public String getStockTypeName() {
        return stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }

    public Boolean getCheckDeposit() {
        return checkDeposit;
    }

    public void setCheckDeposit(Boolean checkDeposit) {
        this.checkDeposit = checkDeposit;
    }

    public Boolean getCheckDial() {
        return checkDial;
    }

    public void setCheckDial(Boolean checkDial) {
        this.checkDial = checkDial;
    }

    public Boolean getCheckSerial() {
        return checkSerial;
    }

    public void setCheckSerial(Boolean checkSerial) {
        this.checkSerial = checkSerial;
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

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public Boolean getCheckView() {
        return checkView;
    }

    public void setCheckView(Boolean checkView) {
        this.checkView = checkView;
    }

    public String getAccountingModelCode() {
        return accountingModelCode;
    }

    public void setAccountingModelCode(String accountingModelCode) {
        this.accountingModelCode = accountingModelCode;
    }

    public String getAccountingModelName() {
        return accountingModelName;
    }

    public void setAccountingModelName(String accountingModelName) {
        this.accountingModelName = accountingModelName;
    }

    public Long getSourcePrice() {
        return sourcePrice;
    }

    public void setSourcePrice(Long sourcePrice) {
        this.sourcePrice = sourcePrice;
    }
    /*
     * Add by hieptd
     */
    public Integer getCheckStockChannel() {
        return checkStockChannel;
    }

    public void setCheckStockChannel(Integer checkStockChannel) {
        this.checkStockChannel = checkStockChannel;
    }
    
    public Boolean getCheckStockChannelInfor() {
        return checkStockChannelInfor;
    }

    public void setCheckStockChannelInfor(Boolean checkStockChannelInfor) {
        this.checkStockChannelInfor = checkStockChannelInfor;
    }
    
}
