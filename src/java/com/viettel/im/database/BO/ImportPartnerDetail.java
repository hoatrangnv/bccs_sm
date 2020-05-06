package com.viettel.im.database.BO;

/**
 * ImportPartnerDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ImportPartnerDetail implements java.io.Serializable {

	// Fields

	private Long id;
	private Long impPartnerId;
	private Long stockModelId;
	private String impType;
	private Long fromSerial;
	private Long toSerial;
	private Long quantity;
	private String impFileName;
	private Long status;
	private String note;

	// Constructors

	/** default constructor */
	public ImportPartnerDetail() {
	}

	/** minimal constructor */
	public ImportPartnerDetail(Long id) {
		this.id = id;
	}

	/** full constructor */
	public ImportPartnerDetail(Long id, Long impPartnerId, Long stockModelId,
			String impType, Long fromSerial, Long toSerial, Long quantity,
			String impFileName, Long status, String note) {
		this.id = id;
		this.impPartnerId = impPartnerId;
		this.stockModelId = stockModelId;
		this.impType = impType;
		this.fromSerial = fromSerial;
		this.toSerial = toSerial;
		this.quantity = quantity;
		this.impFileName = impFileName;
		this.status = status;
		this.note = note;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getImpPartnerId() {
		return this.impPartnerId;
	}

	public void setImpPartnerId(Long impPartnerId) {
		this.impPartnerId = impPartnerId;
	}

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

	
	public String getImpType() {
		return this.impType;
	}

	public void setImpType(String impType) {
		this.impType = impType;
	}

	public Long getFromSerial() {
		return this.fromSerial;
	}

	public void setFromSerial(Long fromSerial) {
		this.fromSerial = fromSerial;
	}

	public Long getToSerial() {
		return this.toSerial;
	}

	public void setToSerial(Long toSerial) {
		this.toSerial = toSerial;
	}

	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getImpFileName() {
		return this.impFileName;
	}

	public void setImpFileName(String impFileName) {
		this.impFileName = impFileName;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}