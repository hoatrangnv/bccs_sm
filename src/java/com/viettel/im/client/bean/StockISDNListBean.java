/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;
import com.viettel.im.common.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author ANHLT
 */
public class StockISDNListBean implements java.io.Serializable{

    private Long id;
    private String isdn;
    private String isdnType;
    private long status;
    private Date createDate;
    private String fromNumber;
    private String toNumber;
    private String count;
    private String[] strID;
    private List lstStringID=new ArrayList();
    private String ownerID;
    private Long serviceTypeId;
    private Long stockModelId; //tamdt1, 16/06/2009. Id cua mat hang so can phan phoi (nghiep vu bo sung)

    public StockISDNListBean(){

    }

    public StockISDNListBean(Long id,String isdn,String isdnType,Long status,Date createDate,String fNumber,String tNumber,String count,String[] sID,String ownerID)
    {
        this.id = id;
        this.isdn = isdn;
        this.isdnType = isdnType;
        this.status = status;
        this.createDate = createDate;
        this.fromNumber = fNumber;
        this.toNumber= tNumber;
        this.count=count;
        this.strID = sID;
        this.ownerID = ownerID;
    }

    public StockISDNListBean(Long id, String isdn, String isdnType, Long status,
            Date createDate, String fNumber, String tNumber, String count, String[] sID,
            String ownerID, Long stockModelId) {
        this.id = id;
        this.isdn = isdn;
        this.isdnType = isdnType;
        this.status = status;
        this.createDate = createDate;
        this.fromNumber = fNumber;
        this.toNumber= tNumber;
        this.count=count;
        this.strID = sID;
        this.ownerID = ownerID;
        this.stockModelId = stockModelId;
    }
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the isdn
     */
    public String getIsdn() {
        return isdn;
    }

    /**
     * @param isdn the isdn to set
     */
    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    /**
     * @return the isdnType
     */
    public String getIsdnType() {
        return isdnType;
    }

    /**
     * @param isdnType the isdnType to set
     */
    public void setIsdnType(String isdnType) {
        this.isdnType = isdnType;
    }

    /**
     * @return the status
     */
    public long getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(long status) {
        this.status = status;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the fromNumber
     */
    public String getFromNumber() {
        return fromNumber;
    }

    /**
     * @param fromNumber the fromNumber to set
     */
    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    /**
     * @return the toNumber
     */
    public String getToNumber() {
        return toNumber;
    }

    /**
     * @param toNumber the toNumber to set
     */
    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    /**
     * @return the count
     */
    public String getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(String count) {
        this.count = count;
    }

    /**
     * @return the strID
     */
    public String[] getStrID() {
        return strID;
    }

    /**
     * @param strID the strID to set
     */
    public void setStrID(String[] strID) {
        this.strID = strID;
    }

    /**
     * @return the lstStringID
     */
    public List getLstStringID() {
        return lstStringID;
    }

    /**
     * @param lstStringID the lstStringID to set
     */
    public void setLstStringID(List lstStringID) {
        this.lstStringID = lstStringID;
    }

    /**
     * @return the ownerID
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * @param ownerID the ownerID to set
     */
    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public Long getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }



}
