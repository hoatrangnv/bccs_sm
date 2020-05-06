package com.viettel.im.database.BO;

import com.viettel.database.BO.BasicBO;
import java.util.Date;

/**
 * CommItemFeeChannel entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CommItemFeeChannel extends BasicBO  {

	// Fields

	private Long itemFeeChannelId;
	private Long itemId;
	private Long channelTypeId;
	private Long fee;
	private Long vat;
	private Date staDate;
	private Date endDate;
	private String status;
	private String userCreate;
	private Date createDate;

    private String channelTypeName;
    private String feeString;
    private String vatString;
    private boolean checked = false;
    

    
	// Constructors

	/** default constructor */
	public CommItemFeeChannel() {
	}

	/** minimal constructor */
	public CommItemFeeChannel(Long itemFeeChannelId, Long itemId,
			Long channelTypeId) {
		this.itemFeeChannelId = itemFeeChannelId;
		this.itemId = itemId;
		this.channelTypeId = channelTypeId;
	}

	/** full constructor */
	public CommItemFeeChannel(Long itemFeeChannelId, Long itemId,
			Long channelTypeId, Long fee, Long vat, Date staDate, Date endDate,
			String status, String userCreate, Date createDate) {
		this.itemFeeChannelId = itemFeeChannelId;
		this.itemId = itemId;
		this.channelTypeId = channelTypeId;
		this.fee = fee;
		this.vat = vat;
		this.staDate = staDate;
		this.endDate = endDate;
		this.status = status;
		this.userCreate = userCreate;
		this.createDate = createDate;
	}


	public CommItemFeeChannel(Long itemFeeChannelId, Long itemId,
			Long channelTypeId, Long fee, Long vat, Date staDate, Date endDate,
			String status, String userCreate, Date createDate, String channelTypeName) {
		this.itemFeeChannelId = itemFeeChannelId;
		this.itemId = itemId;
		this.channelTypeId = channelTypeId;
		this.fee = fee;
		this.vat = vat;
		this.staDate = staDate;
		this.endDate = endDate;
		this.status = status;
		this.userCreate = userCreate;
		this.createDate = createDate;
		this.channelTypeName = channelTypeName;
	}

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getFeeString() {
        return feeString;
    }

    public void setFeeString(String feeString) {
        this.feeString = feeString;
    }

    public String getVatString() {
        return vatString;
    }

    public void setVatString(String vatString) {
        this.vatString = vatString;
    }

	// Property accessors

        
	public Long getItemFeeChannelId() {
		return this.itemFeeChannelId;
	}

	public void setItemFeeChannelId(Long itemFeeChannelId) {
		this.itemFeeChannelId = itemFeeChannelId;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getChannelTypeId() {
		return this.channelTypeId;
	}

	public void setChannelTypeId(Long channelTypeId) {
		this.channelTypeId = channelTypeId;
	}

	public Long getFee() {
		return this.fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public Long getVat() {
		return this.vat;
	}

	public void setVat(Long vat) {
		this.vat = vat;
	}

	public Date getStaDate() {
		return this.staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserCreate() {
		return this.userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }
}