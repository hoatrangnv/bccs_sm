/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.common.Constant;
import java.util.ArrayList;
import java.util.List;
import org.apache.struts.action.ActionForm;

/**
 * RevokeISDNForm class
 * @author ANHLT
 * @date 23/04/2009
 */
public class RevokeISDNForm extends ActionForm {

    private Long shopId;
    private String shopCode;
    private String shopName;
    private String serviceType;
    private String fromIsdn;
    private String toIsdn;
    private String isdnType;
    private List strRevokeID = new ArrayList();
    private String[] aReceiveID;
    private List sListID;
    private Long realQuantity;
    private Long status;
    private Long revokeIsdnFormId; //tamdt1, 19/06/2009, them vao phuc vu viec phan biet giua cac form tren list hien thi
    private Long[] arrSelectedFormId;
    private Long stockTypeId; //NamNX

    public RevokeISDNForm() {
        resetForm();
    }

    public RevokeISDNForm(String shopCode, String shopName, List strList, String fNumber, String tNumber, String isdnType, String[] aReceiveID, List sListID, Long status) {
        this.shopCode = shopCode;
        this.shopName = shopName;
        this.strRevokeID = strList;
        this.fromIsdn = fNumber;
        this.toIsdn = tNumber;
        this.isdnType = isdnType;
        this.aReceiveID = aReceiveID;
        this.sListID = sListID;
        this.status = status;
    }

    public RevokeISDNForm(String shopCode, String shopName, String fromIsdn, String toIsdn, String isdnType, Long realQuantity, Long status, Long[] arrSelectedFormId, Long stockTypeId, Long shopId) {
        this.shopCode = shopCode;
        this.shopName = shopName;
        this.fromIsdn = fromIsdn;
        this.toIsdn = toIsdn;
        this.isdnType = isdnType;
        this.realQuantity = realQuantity;
        this.status = status;
        this.arrSelectedFormId = arrSelectedFormId;
        this.stockTypeId = stockTypeId;
        this.shopId=shopId;
    }

    /**
     * @return the shopCode
     */
    public String getShopCode() {
        return shopCode;
    }

    /**
     * @param shopCode the shopCode to set
     */
    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    /**
     * @return the shopName
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * @param shopName the shopName to set
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * @return the serviceType
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType the serviceType to set
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * @return the strRevokeID
     */
    public List getStrRevokeID() {
        return strRevokeID;
    }

    /**
     * @param strRevokeID the strRevokeID to set
     */
    public void setStrRevokeID(List strRevokeID) {
        this.strRevokeID = strRevokeID;
    }

    public String getFromIsdn() {
        return fromIsdn;
    }

    public void setFromIsdn(String fromIsdn) {
        this.fromIsdn = fromIsdn;
    }

    public String getToIsdn() {
        return toIsdn;
    }

    public void setToIsdn(String toIsdn) {
        this.toIsdn = toIsdn;
    }

    public String getIsdnType() {
        return isdnType;
    }

    public void setIsdnType(String isdnType) {
        this.isdnType = isdnType;
    }

    /**
     * @return the aReceiveID
     */
    public String[] getAReceiveID() {
        return aReceiveID;
    }

    /**
     * @param aReceiveID the aReceiveID to set
     */
    public void setAReceiveID(String[] aReceiveID) {
        this.aReceiveID = aReceiveID;
    }

    /**
     * @return the sListID
     */
    public List getSListID() {
        return sListID;
    }

    /**
     * @param sListID the sListID to set
     */
    public void setSListID(List sListID) {
        this.sListID = sListID;
    }

    public Long getRealQuantity() {
        return realQuantity;
    }

    public void setRealQuantity(Long realQuantity) {
        this.realQuantity = realQuantity;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getRevokeIsdnFormId() {
        return revokeIsdnFormId;
    }

    public void setRevokeIsdnFormId(Long revokeIsdnFormId) {
        this.revokeIsdnFormId = revokeIsdnFormId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public Long[] getArrSelectedFormId() {
        return arrSelectedFormId;
    }

    public void setArrSelectedFormId(Long[] arrSelectedFormId) {
        this.arrSelectedFormId = arrSelectedFormId;
    }
     public void resetForm() {
        shopCode = "";
        shopName = "";
        stockTypeId = Constant.STOCK_ISDN_MOBILE;
        isdnType = "";
        fromIsdn = "";
        toIsdn = "";
        status =Constant.STATUS_ISDN_SUSPEND;
        realQuantity = 0L;
        revokeIsdnFormId = 0L;
        shopId = 0L;
    }




}
