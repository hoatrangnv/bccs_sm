package com.viettel.im.database.BO;

/**
 * VShopTree entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TblShopTree implements java.io.Serializable {

    // Fields
    private TblShopTreeId id;
    private Long rootId;
    private String rootCode;
    private String rootName;
    private String shopCode;
    private Long parentShopId;
    private String shopName;
    private String shopNameTree;
    private String shopPathTree;
    private String shopType;
    private Long channelTypeId;
    private Long shopOrder;
    private Long shopStatus;

    // Constructors
    /** default constructor */
    public TblShopTree() {
    }

    /** full constructor */
    public TblShopTree(TblShopTreeId id) {
        this.id = id;
    }

    // Property accessors
    public TblShopTreeId getId() {
        return this.id;
    }

    public void setId(TblShopTreeId id) {
        this.id = id;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public Long getParentShopId() {
        return parentShopId;
    }

    public void setParentShopId(Long parentShopId) {
        this.parentShopId = parentShopId;
    }

    public String getRootCode() {
        return rootCode;
    }

    public void setRootCode(String rootCode) {
        this.rootCode = rootCode;
    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopNameTree() {
        return shopNameTree;
    }

    public void setShopNameTree(String shopNameTree) {
        this.shopNameTree = shopNameTree;
    }

    public Long getShopOrder() {
        return shopOrder;
    }

    public void setShopOrder(Long shopOrder) {
        this.shopOrder = shopOrder;
    }

    public String getShopPathTree() {
        return shopPathTree;
    }

    public void setShopPathTree(String shopPathTree) {
        this.shopPathTree = shopPathTree;
    }

    public Long getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(Long shopStatus) {
        this.shopStatus = shopStatus;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }
}
