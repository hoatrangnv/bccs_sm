package com.viettel.im.client.bean;

import com.viettel.im.database.BO.BlockDetail;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * StockTransDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class BlockBean implements Comparable<BlockBean>  {

    // Fields
    private Long blockId;
    private String blockType;
    private String name;
    private String value;
    private Long quantity;    
    private Long status = 0L;
    
    /** Total block with same type */
    private Long total;
    /** Code of block type in the app params */
    private String code;
    private Date createDate;
    public int compareTo(BlockBean o) {
        return this.blockType.compareTo(o.blockType);
    }

    //private List<BlockDetail> lstBlockDetail = new ArrayList<BlockDetail>();
    public BlockBean() {
    }

    public BlockBean(Long blockId, String name, String value, Long quantity, String blockType) {
        this.blockId = blockId;
        this.name = name;
        this.value = value;
        this.quantity = quantity;
        this.blockType = blockType;
    }

    public BlockBean(String name, String value, Long quantity) {
        this.name = name;
        this.value = value;
        this.quantity = quantity;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

//    public List<BlockDetail> getLstBlockDetail() {
//        return lstBlockDetail;
//    }
//
//    public void setLstBlockDetail(List<BlockDetail> lstBlockDetail) {
//        this.lstBlockDetail = lstBlockDetail;
//    }
}