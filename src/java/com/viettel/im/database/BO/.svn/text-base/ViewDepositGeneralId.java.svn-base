package com.viettel.im.database.BO;

import java.util.Date;

/**
 * ViewDepositGeneralId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class ViewDepositGeneralId implements java.io.Serializable {

    // Fields
    private Long shopId;
    private Date createdate;
    private String shopname;
    private String branchId;    
    private String shopPath;

    // Constructors
    /** default constructor */
    public ViewDepositGeneralId() {
    }

    /** minimal constructor */
    public ViewDepositGeneralId(Long shopId, String shopname) {
        this.shopId = shopId;
        this.shopname = shopname;
    }

    public ViewDepositGeneralId(Long shopId, Date createdate, String shopname, String branchId, String shopPath) {
        this.shopId = shopId;
        this.createdate = createdate;
        this.shopname = shopname;
        this.branchId = branchId;
        this.shopPath = shopPath;
    }    

    // Property accessors
    public Long getShopId() {
        return this.shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Date getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getShopname() {
        return this.shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getBranchId() {
        return this.branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }    

    public String getShopPath() {
        return this.shopPath;
    }

    public void setShopPath(String shopPath) {
        this.shopPath = shopPath;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof ViewDepositGeneralId)) {
            return false;
        }
        ViewDepositGeneralId castOther = (ViewDepositGeneralId) other;

        return ((this.getShopId() == castOther.getShopId()) || (this.getShopId() != null && castOther.getShopId() != null 
                && this.getShopId().equals(castOther.getShopId()))) && ((this.getCreatedate() == castOther.getCreatedate()) || (this.getCreatedate() != null 
                && castOther.getCreatedate() != null && this.getCreatedate().equals(castOther.getCreatedate()))) 
                && ((this.getShopname() == castOther.getShopname()) || (this.getShopname() != null
                && castOther.getShopname() != null && this.getShopname().equals(castOther.getShopname()))) 
                && ((this.getBranchId() == castOther.getBranchId()) || (this.getBranchId() != null && castOther.getBranchId() != null 
                && this.getBranchId().equals(castOther.getBranchId())))                 
                && ((this.getShopPath() == castOther.getShopPath()) || (this.getShopPath() != null 
                && castOther.getShopPath() != null 
                && this.getShopPath().equals(castOther.getShopPath())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getShopId() == null ? 0 : this.getShopId().hashCode());
        result = 37 * result + (getCreatedate() == null ? 0 : this.getCreatedate().hashCode());
        result = 37 * result + (getShopname() == null ? 0 : this.getShopname().hashCode());
        result = 37 * result + (getBranchId() == null ? 0 : this.getBranchId().hashCode());
        result = 37 * result + (getShopPath() == null ? 0 : this.getShopPath().hashCode());
        return result;
    }
}
