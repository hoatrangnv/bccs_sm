package com.viettel.im.database.BO;

/**
 * ShopDslam entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ShopDslam implements java.io.Serializable {

	// Fields

	private ShopDslamId id;

        private Long shopDslamId;
        private Long shopId;
	private Long dslamId;
	private Long status;

	// Constructors

	/** default constructor */
	public ShopDslam() {
	}

	/** full constructor */
	public ShopDslam(ShopDslamId id) {
		this.id = id;
	}

	// Property accessors

	public ShopDslamId getId() {
		return this.id;
	}

	public void setId(ShopDslamId id) {
		this.id = id;
	}

    public Long getDslamId() {
        return dslamId;
    }

    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopDslamId() {
        return shopDslamId;
    }

    public void setShopDslamId(Long shopDslamId) {
        this.shopDslamId = shopDslamId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

        

}