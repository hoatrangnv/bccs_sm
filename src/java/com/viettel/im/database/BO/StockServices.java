package com.viettel.im.database.BO;

import java.util.Date;


/**
 * StockServices entity. @author MyEclipse Persistence Tools
 */

public class StockServices  implements java.io.Serializable {


    // Fields    

     private Long id;
     private Long goodsDefId;
     private Long goodsInstanceId;
     private Long resourceTypeId;
     private String serviceName;
     private Long shopId;
     private Long staffId;
     private Long status;
     private Date createDate;
     private Long fromPrice;
     private Long toPrice;


    // Constructors

    /** default constructor */
    public StockServices() {
    }

	/** minimal constructor */
    public StockServices(Long id) {
        this.id = id;
    }
    
    /** full constructor */
    public StockServices(Long id, Long goodsDefId, Long goodsInstanceId, Long resourceTypeId, String serviceName, Long shopId, Long staffId, Long status, Date createDate, Long fromPrice, Long toPrice) {
        this.id = id;
        this.goodsDefId = goodsDefId;
        this.goodsInstanceId = goodsInstanceId;
        this.resourceTypeId = resourceTypeId;
        this.serviceName = serviceName;
        this.shopId = shopId;
        this.staffId = staffId;
        this.status = status;
        this.createDate = createDate;
        this.fromPrice = fromPrice;
        this.toPrice = toPrice;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsDefId() {
        return this.goodsDefId;
    }
    
    public void setGoodsDefId(Long goodsDefId) {
        this.goodsDefId = goodsDefId;
    }

    public Long getGoodsInstanceId() {
        return this.goodsInstanceId;
    }
    
    public void setGoodsInstanceId(Long goodsInstanceId) {
        this.goodsInstanceId = goodsInstanceId;
    }

    public Long getResourceTypeId() {
        return this.resourceTypeId;
    }
    
    public void setResourceTypeId(Long resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }

    public String getServiceName() {
        return this.serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getShopId() {
        return this.shopId;
    }
    
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getStaffId() {
        return this.staffId;
    }
    
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getFromPrice() {
        return this.fromPrice;
    }
    
    public void setFromPrice(Long fromPrice) {
        this.fromPrice = fromPrice;
    }

    public Long getToPrice() {
        return this.toPrice;
    }
    
    public void setToPrice(Long toPrice) {
        this.toPrice = toPrice;
    }
   








}