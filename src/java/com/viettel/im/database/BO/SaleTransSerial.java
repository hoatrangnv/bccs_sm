package com.viettel.im.database.BO;

import java.util.Date;

/**
 * SaleTransSerial entity.
 *
 * @author MyEclipse Persistence Tools
 */
public class SaleTransSerial implements java.io.Serializable {

    // Fields
    private Long saleTransSerialId;
    private Long saleTransDetailId;
    private Long stockModelId;
    //private String serial;
    private Date saleTransDate;
    private String fromSerial;
    private String toSerial;
    private Long quantity;
    private Long userDeliver;
    private Date dateDeliver;
    private Long userUpdate;
    //private SaleTransDetail saleTransDetail;

    // Constructors
    /**
     * default constructor
     */
    public SaleTransSerial() {
    }

    /**
     * minimal constructor
     */
    public SaleTransSerial(Long saleTransSerialId,
            Long saleTransDetailId, Date saleTransDate) {
        this.saleTransSerialId = saleTransSerialId;
        this.saleTransDetailId = saleTransDetailId;
        this.saleTransDate = saleTransDate;
    }

    /**
     * full constructor
     */
    public SaleTransSerial(Long saleTransSerialId,
            Long saleTransDetailId, Long stockModelId,
            Date saleTransDate) {
        this.saleTransSerialId = saleTransSerialId;
        this.saleTransDetailId = saleTransDetailId;
        this.stockModelId = stockModelId;
        //this.serial = serial;
        this.saleTransDate = saleTransDate;
    }

    // Property accessors
    public Long getSaleTransSerialId() {
        return this.saleTransSerialId;
    }

    public void setSaleTransSerialId(Long saleTransSerialId) {
        this.saleTransSerialId = saleTransSerialId;
    }

    public Long getSaleTransDetailId() {
        return saleTransDetailId;
    }

    public void setSaleTransDetailId(Long saleTransDetailId) {
        this.saleTransDetailId = saleTransDetailId;
    }

    public Long getStockModelId() {
        return this.stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

//	public String getSerial() {
//		return this.serial;
//	}
//
//	public void setSerial(String serial) {
//		this.serial = serial;
//	}
    public Date getSaleTransDate() {
        return this.saleTransDate;
    }

    public void setSaleTransDate(Date saleTransDate) {
        this.saleTransDate = saleTransDate;
    }

    public String getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getToSerial() {
        return toSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public Date getDateDeliver() {
        return dateDeliver;
    }

    public void setDateDeliver(Date dateDeliver) {
        this.dateDeliver = dateDeliver;
    }

    public Long getUserDeliver() {
        return userDeliver;
    }

    public void setUserDeliver(Long userDeliver) {
        this.userDeliver = userDeliver;
    }

    public Long getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(Long userUpdate) {
        this.userUpdate = userUpdate;
    }
//    public SaleTransDetail getSaleTransDetail() {
//        return saleTransDetail;
//    }
//
//    public void setSaleTransDetail(SaleTransDetail saleTransDetail) {
//        this.saleTransDetail = saleTransDetail;
//    }
}