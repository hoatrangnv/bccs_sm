package com.viettel.im.client.form;

import com.viettel.im.database.BO.GoodsDef;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tamdt1
 * date 24/02/2009
 *
 */
public class GoodsDefForm extends ActionForm {
    //tuong ung cac truong trong DB

    private Long goodsDefId;
    private String goodsCode;
    private Long goodsGroupId;
    private String goodsType;
    private String name;
    private String unit;
    private String notes;

    //
    private String message;
    private boolean toEdit;

    public GoodsDefForm() {
        goodsDefId = 0L;
        goodsCode = "";
        goodsGroupId = 0L;
        goodsType = "";
        name = "";
        unit = "";
        notes = "";
        message = "";
        toEdit = false;
    }

    public void copyDataFromBO(GoodsDef goodsDef){
        this.setGoodsDefId(goodsDef.getGoodsDefId());
        this.setGoodsCode(goodsDef.getGoodsCode());
        this.setGoodsGroupId(goodsDef.getGoodsGroupId());
        this.setGoodsType(goodsDef.getGoodsType());
        this.setName(goodsDef.getName());
        this.setUnit(goodsDef.getUnit());
        this.setNotes(goodsDef.getNotes());
    }

    public void copyDataToBO(GoodsDef goodsDef){
        goodsDef.setGoodsDefId(this.getGoodsDefId());
        goodsDef.setGoodsCode(this.getGoodsCode());
        goodsDef.setGoodsGroupId(this.getGoodsGroupId());
        goodsDef.setGoodsType(this.getGoodsType());
        goodsDef.setName(this.getName());
        goodsDef.setUnit(this.getUnit());
        goodsDef.setNotes(this.getNotes());
    }

    public void resetForm(){
        goodsDefId = 0L;
        goodsCode = "";
        goodsGroupId = 0L;
        goodsType = "";
        name = "";
        unit = "";
        notes = "";
        message = "";
        toEdit = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isToEdit() {
        return toEdit;
    }

    public void setToEdit(boolean toEdit) {
        this.toEdit = toEdit;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Long getGoodsDefId() {
        return goodsDefId;
    }

    public void setGoodsDefId(Long goodsDefId) {
        this.goodsDefId = goodsDefId;
    }

    public Long getGoodsGroupId() {
        return goodsGroupId;
    }

    public void setGoodsGroupId(Long goodsGroupId) {
        this.goodsGroupId = goodsGroupId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
