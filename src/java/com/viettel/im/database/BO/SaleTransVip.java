package com.viettel.im.database.BO;
// Generated Mar 23, 2015 9:36:16 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * SaleTransVip generated by hbm2java
 */
public class SaleTransVip  implements java.io.Serializable {


     private Long saleTransVipId;
     private Long saleTransId;
     private String msisdn;
     private String custName;
     private String idNo;
     private Date birthday;
     private String precinctCode;
     private String districtCode;
     private String provinceCode;
     private Long staffId;
     private Long shopId;
     private Date saleTransDate;
     private String saleTransType;

    public String getSaleTransType() {
        return saleTransType;
    }

    public void setSaleTransType(String saleTransType) {
        this.saleTransType = saleTransType;
    }

    public SaleTransVip() {
    }

	
    public SaleTransVip(Long saleTransVipId, Long saleTransId, Date saleTransDate) {
        this.saleTransVipId = saleTransVipId;
        this.saleTransId = saleTransId;
        this.saleTransDate = saleTransDate;
    }
    public SaleTransVip(Long saleTransVipId, Long saleTransId, String msisdn, String custName, String idNo, Date birthday, String precinctCode, String districtCode, String provinceCode, Long staffId, Long shopId, Date saleTransDate) {
       this.saleTransVipId = saleTransVipId;
       this.saleTransId = saleTransId;
       this.msisdn = msisdn;
       this.custName = custName;
       this.idNo = idNo;
       this.birthday = birthday;
       this.precinctCode = precinctCode;
       this.districtCode = districtCode;
       this.provinceCode = provinceCode;
       this.staffId = staffId;
       this.shopId = shopId;
       this.saleTransDate = saleTransDate;
    }
   
    public Long getSaleTransVipId() {
        return this.saleTransVipId;
    }
    
    public void setSaleTransVipId(Long saleTransVipId) {
        this.saleTransVipId = saleTransVipId;
    }
    public Long getSaleTransId() {
        return this.saleTransId;
    }
    
    public void setSaleTransId(Long saleTransId) {
        this.saleTransId = saleTransId;
    }
    public String getMsisdn() {
        return this.msisdn;
    }
    
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
    public String getCustName() {
        return this.custName;
    }
    
    public void setCustName(String custName) {
        this.custName = custName;
    }
    public String getIdNo() {
        return this.idNo;
    }
    
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    public Date getBirthday() {
        return this.birthday;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getPrecinctCode() {
        return this.precinctCode;
    }
    
    public void setPrecinctCode(String precinctCode) {
        this.precinctCode = precinctCode;
    }
    public String getDistrictCode() {
        return this.districtCode;
    }
    
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
    public String getProvinceCode() {
        return this.provinceCode;
    }
    
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
    public Long getStaffId() {
        return this.staffId;
    }
    
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }
    public Long getShopId() {
        return this.shopId;
    }
    
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
    public Date getSaleTransDate() {
        return this.saleTransDate;
    }
    
    public void setSaleTransDate(Date saleTransDate) {
        this.saleTransDate = saleTransDate;
    }
}


