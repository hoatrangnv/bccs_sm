package com.viettel.im.database.BO;

import java.util.Date;

/**
 * Discount entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Discount implements java.io.Serializable {

	// Fields
	private Long discountId;
	private String areaCode;
	private Long channelTypeId;
	private Long fromAmount;
	private Long toAmount;
//	private Double discountRate;
	private Double discountRateNumerator;
	private Double discountRateDenominator;
	private Long discountAmount;
	private String type;
	private Date startDatetime;
	private Date endDatetime;
	private Long status;
    private Long discountGroupId;
    
    
    //bo sung them phan dia ban + kenh
    private String[] arrAreas;
    private Long[] arrChannels;

	// Constructors

	/** default constructor */
	public Discount() {
	}

	/** minimal constructor */
	public Discount(Long discountId, Long fromAmount, Long toAmount,
			Date startDatetime) {
		this.discountId = discountId;
		this.fromAmount = fromAmount;
		this.toAmount = toAmount;
		this.startDatetime = startDatetime;
	}

//	/** full constructor */
//	public Discount(Long discountId, Long discountGroupId, String areaCode,
//			Long channelTypeId, Long fromAmount, Long toAmount,
//			Double discountRate, Long discountAmount, String type,
//			Date startDatetime, Date endDatetime, Long status) {
//		this.discountId = discountId;
//		this.discountGroupId = discountGroupId;
//		this.areaCode = areaCode;
//		this.channelTypeId = channelTypeId;
//		this.fromAmount = fromAmount;
//		this.toAmount = toAmount;
//		this.discountRate = discountRate;
//		this.discountAmount = discountAmount;
//		this.type = type;
//		this.startDatetime = startDatetime;
//		this.endDatetime = endDatetime;
//		this.status = status;
//	}

	/** full constructor */
	public Discount(Long discountId, Long discountGroupId, String areaCode,
			Long channelTypeId, Long fromAmount, Long toAmount,
			Double discountRateNumerator, Double discountRateDenominator, Long discountAmount, String type,
			Date startDatetime, Date endDatetime, Long status) {
		this.discountId = discountId;
		this.discountGroupId = discountGroupId;
		this.areaCode = areaCode;
		this.channelTypeId = channelTypeId;
		this.fromAmount = fromAmount;
		this.toAmount = toAmount;
		this.discountRateNumerator = discountRateNumerator;
		this.discountRateDenominator = discountRateDenominator;
		this.discountAmount = discountAmount;
		this.type = type;
		this.startDatetime = startDatetime;
		this.endDatetime = endDatetime;
		this.status = status;
	}

    

	// Property accessors

    
    public String[] getArrAreas() {
        return arrAreas;
    }

    public void setArrAreas(String[] arrAreas) {
        this.arrAreas = arrAreas;
    }

    public Long[] getArrChannels() {
        return arrChannels;
    }

    public void setArrChannels(Long[] arrChannels) {
        this.arrChannels = arrChannels;
    }
    
    
	public Long getDiscountId() {
		return this.discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

    public Long getDiscountGroupId() {
        return discountGroupId;
    }

    public void setDiscountGroupId(Long discountGroupId) {
        this.discountGroupId = discountGroupId;
    }

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Long getChannelTypeId() {
		return this.channelTypeId;
	}

	public void setChannelTypeId(Long channelTypeId) {
		this.channelTypeId = channelTypeId;
	}

	public Long getFromAmount() {
		return this.fromAmount;
	}

	public void setFromAmount(Long fromAmount) {
		this.fromAmount = fromAmount;
	}

	public Long getToAmount() {
		return this.toAmount;
	}

	public void setToAmount(Long toAmount) {
		this.toAmount = toAmount;
	}

    public Double getDiscountRateDenominator() {
        return discountRateDenominator;
    }

    public void setDiscountRateDenominator(Double discountRateDenominator) {
        this.discountRateDenominator = discountRateDenominator;
    }

    public Double getDiscountRateNumerator() {
        return discountRateNumerator;
    }

    public void setDiscountRateNumerator(Double discountRateNumerator) {
        this.discountRateNumerator = discountRateNumerator;
    }

//    public Double getDiscountRate() {
//        return discountRate;
//    }
//
//    public void setDiscountRate(Double discountRate) {
//        this.discountRate = discountRate;
//    }

	public Long getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStartDatetime() {
		return this.startDatetime;
	}

	public void setStartDatetime(Date startDatetime) {
		this.startDatetime = startDatetime;
	}

	public Date getEndDatetime() {
		return this.endDatetime;
	}

	public void setEndDatetime(Date endDatetime) {
		this.endDatetime = endDatetime;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}