package com.viettel.im.client.bean;

import com.viettel.im.database.BO.Block;
import com.viettel.im.database.BO.BlockDetail;
import java.util.ArrayList;
import java.util.List;

/**
 * StockTransDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class StockCmdBean implements java.io.Serializable {

    // Fields
    private Long stt;
    private Long shopId;
    private String shopCode;
    private String name;
    private String actionCode;
    private Long quantityRes;
    private Long stockTransId;
    private Long stockModelId;
    //Danh sach block boc tham dc
    private List<BlockBean> lstBlock =new ArrayList<BlockBean>();
    private List<BlockBean> lstBlockOverview =new ArrayList<BlockBean>();
    



    public StockCmdBean(Long shopId,String shopCode, String name, String actionCode, Long quantityRes, Long stockTransId,Long stockModelId) {
        this.shopId = shopId;
        this.shopCode=shopCode;
        this.name = name;
        this.actionCode = actionCode;
        this.quantityRes = quantityRes;
        this.stockTransId = stockTransId;
        this.stockModelId=stockModelId;
    }


    public List<BlockBean> getLstBlock() {
        return lstBlock;
    }

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public void setLstBlock(List<BlockBean> lstBlock) {
        this.lstBlock = lstBlock;
    }

    public List<BlockBean> getLstBlockOverview() {
        return lstBlockOverview;
    }

    public void setLstBlockOverview(List<BlockBean> lstBlockOverview) {
        this.lstBlockOverview = lstBlockOverview;
    }

    
    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    

    
    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantityRes() {
        return quantityRes;
    }

    public void setQuantityRes(Long quantityRes) {
        this.quantityRes = quantityRes;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }
    
}