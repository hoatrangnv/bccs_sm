/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.common.Constant;
import java.util.List;

/**
 *
 * @author Doan Thanh 8
 * Modified by TrongLV
 * Modified at 2010-01-17 *
 */
public class AssignIsdnTypeForm {

    private String shopCode;
    private String shopName;
    private Long stockTypeId;
    private String isdnType;
    private String fromIsdn;
    private String toIsdn;
    private Long status;
    private Long realQuantity; //tamdt1, so luong so isdn thuc su trong dai so
    private Long assignIsdnTypeFormId; //tamdt1, them id gia de phuc vu viec sua, xoa, xem chi tiet list
//    private String newIsdnType; //tamdt1, isdnType moi duoc gan
    private Long newStatus;//Vunt, status moi duoc gan
    private Long shopId;
    private Long[] arrSelectedFormId; //mang luu tru cac formId duoc chon
    private String newIsdnType; //Loai so moi duoc gan
    private String newIsdnStatus; //Trang thai moi duoc gan
    private String[] isdnIdList; //mang luu tru cac formId duoc chon
    private Long impType; //NamNX, kieu nhap (theo file, theo dai so)
    private String serverFileName; //ten file tren server trong truogn hop upload
    private String clientFileName; //ten file tren client trong truogn hop upload
    private String pathExpFile;
    private String errorMessage;


    public AssignIsdnTypeForm() {
        resetForm();
    }

    public AssignIsdnTypeForm(String shopCode, String shopName, Long stockTypeId, String isdnType, String fromIsdn, String toIsdn, Long status, Long realQuantity, Long assignIsdnTypeFormId, String newIsdnType, Long newStatus, Long shopId, Long[] arrSelectedFormId) {
        this.shopCode = shopCode;
        this.shopName = shopName;
        this.stockTypeId = stockTypeId;
        this.isdnType = isdnType;
        this.fromIsdn = fromIsdn;
        this.toIsdn = toIsdn;
        this.status = status;
        this.realQuantity = realQuantity;
        this.assignIsdnTypeFormId = assignIsdnTypeFormId;
        this.newIsdnType = newIsdnType;
        this.newStatus = newStatus;
        this.shopId = shopId;
        this.arrSelectedFormId = arrSelectedFormId;
    }

    public void resetForm() {
        shopCode = "";
        shopName = "";
        stockTypeId = Constant.STOCK_ISDN_MOBILE;
        isdnType = Constant.ISDN_TYPE_UNKOWN;
        fromIsdn = "";
        toIsdn = "";
        status = Constant.STATUS_ISDN_NEW;

        realQuantity = 0L;
        assignIsdnTypeFormId = 0L;
        newIsdnType = Constant.ISDN_TYPE_PRE;
        shopId = 0L;
        impType = 2L;
    }

    public Long getAssignIsdnTypeFormId() {
        return assignIsdnTypeFormId;
    }

    public void setAssignIsdnTypeFormId(Long assignIsdnTypeFormId) {
        this.assignIsdnTypeFormId = assignIsdnTypeFormId;
    }

    public String getFromIsdn() {
        return fromIsdn;
    }

    public void setFromIsdn(String fromIsdn) {
        this.fromIsdn = fromIsdn;
    }

    public String getIsdnType() {
        return isdnType;
    }

    public void setIsdnType(String isdnType) {
        this.isdnType = isdnType;
    }

    public String getNewIsdnType() {
        return newIsdnType;
    }

    public void setNewIsdnType(String newIsdnType) {
        this.newIsdnType = newIsdnType;
    }

    public Long getRealQuantity() {
        return realQuantity;
    }

    public void setRealQuantity(Long realQuantity) {
        this.realQuantity = realQuantity;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public String getToIsdn() {
        return toIsdn;
    }

    public void setToIsdn(String toIsdn) {
        this.toIsdn = toIsdn;
    }

    public Long[] getArrSelectedFormId() {
        return arrSelectedFormId;
    }

    public void setArrSelectedFormId(Long[] arrSelectedFormId) {
        this.arrSelectedFormId = arrSelectedFormId;
    }

    public Long getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Long newStatus) {
        this.newStatus = newStatus;
    }

    public String[] getIsdnIdList() {
        return isdnIdList;
    }

    public void setIsdnIdList(String[] isdnIdList) {
        this.isdnIdList = isdnIdList;
    }

    public String getNewIsdnStatus() {
        return newIsdnStatus;
    }

    public void setNewIsdnStatus(String newIsdnStatus) {
        this.newIsdnStatus = newIsdnStatus;
    }

    public Long getImpType() {
        return impType;
    }

    public void setImpType(Long impType) {
        this.impType = impType;
    }

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public String getPathExpFile() {
        return pathExpFile;
    }

    public void setPathExpFile(String pathExpFile) {
        this.pathExpFile = pathExpFile;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    


}
