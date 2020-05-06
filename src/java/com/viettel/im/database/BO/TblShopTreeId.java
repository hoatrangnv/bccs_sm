package com.viettel.im.database.BO;

/**
 * VShopTreeId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TblShopTreeId implements java.io.Serializable {

    // Fields
    private Long shopId;
    private Long shopLevel;

    // Constructors
    /** default constructor */
    public TblShopTreeId() {
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(Long shopLevel) {
        this.shopLevel = shopLevel;
    }
}
