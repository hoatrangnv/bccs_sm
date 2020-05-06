/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.database.BO.VSimtkFull;

/**
 *
 * @author TrongLV
 */
public class SimtkManagementForm {

    String shopCodeSearch;
    String shopNameSearch;
    String staffCodeSearch;
    String staffNameSearch;
    Long channelTypeIdSearch;
    String ownerCodeSearch;
    String ownerNameSearch;
    String ownerTypeSearch;    
    String isdnSearch;
    
    String objectTypeSearch;
    String isVtUnitSearch;
    
    VSimtkFull vfSimtk;
    
    Long shopBranchId;
    Long shopCenterId;
    Long shopShowroomId;
    private String serverFileName; //ten file tren server trong truogn hop upload
    private String clientFileName; //ten file tren client trong truogn hop upload
    
    private String otp;
    
    private Long authenStatus;
    public String[] selectedShopAuthenIds;
    public String[] selectedShopRemoveIds;


    public SimtkManagementForm() {
    }

    public Long getChannelTypeIdSearch() {
        return channelTypeIdSearch;
    }

    public void setChannelTypeIdSearch(Long channelTypeIdSearch) {
        this.channelTypeIdSearch = channelTypeIdSearch;
    }

    public String getIsVtUnitSearch() {
        return isVtUnitSearch;
    }

    public void setIsVtUnitSearch(String isVtUnitSearch) {
        this.isVtUnitSearch = isVtUnitSearch;
    }

    public String getIsdnSearch() {
        return isdnSearch;
    }

    public void setIsdnSearch(String isdnSearch) {
        this.isdnSearch = isdnSearch;
    }

    public String getObjectTypeSearch() {
        return objectTypeSearch;
    }

    public void setObjectTypeSearch(String objectTypeSearch) {
        this.objectTypeSearch = objectTypeSearch;
    }

    public String getOwnerCodeSearch() {
        return ownerCodeSearch;
    }

    public void setOwnerCodeSearch(String ownerCodeSearch) {
        this.ownerCodeSearch = ownerCodeSearch;
    }

    public String getOwnerNameSearch() {
        return ownerNameSearch;
    }

    public void setOwnerNameSearch(String ownerNameSearch) {
        this.ownerNameSearch = ownerNameSearch;
    }

    public String getOwnerTypeSearch() {
        return ownerTypeSearch;
    }

    public void setOwnerTypeSearch(String ownerTypeSearch) {
        this.ownerTypeSearch = ownerTypeSearch;
    }

    public String getShopCodeSearch() {
        return shopCodeSearch;
    }

    public void setShopCodeSearch(String shopCodeSearch) {
        this.shopCodeSearch = shopCodeSearch;
    }

    public String getShopNameSearch() {
        return shopNameSearch;
    }

    public void setShopNameSearch(String shopNameSearch) {
        this.shopNameSearch = shopNameSearch;
    }

    public String getStaffCodeSearch() {
        return staffCodeSearch;
    }

    public void setStaffCodeSearch(String staffCodeSearch) {
        this.staffCodeSearch = staffCodeSearch;
    }

    public String getStaffNameSearch() {
        return staffNameSearch;
    }

    public void setStaffNameSearch(String staffNameSearch) {
        this.staffNameSearch = staffNameSearch;
    }

    public VSimtkFull getVfSimtk() {
        return vfSimtk;
    }

    public void setVfSimtk(VSimtkFull vSimtk) {
        this.vfSimtk = vSimtk;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Long getShopBranchId() {
        return shopBranchId;
    }

    public void setShopBranchId(Long shopBranchId) {
        this.shopBranchId = shopBranchId;
    }

    public Long getShopCenterId() {
        return shopCenterId;
    }

    public void setShopCenterId(Long shopCenterId) {
        this.shopCenterId = shopCenterId;
    }

    public Long getShopShowroomId() {
        return shopShowroomId;
    }

    public void setShopShowroomId(Long shopShowroomId) {
        this.shopShowroomId = shopShowroomId;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }
    
      public String[] getSelectedShopAuthenIds() {
        return selectedShopAuthenIds;
    }

    public void setSelectedShopAuthenIds(String[] selectedShopAuthenIds) {
        this.selectedShopAuthenIds = selectedShopAuthenIds;
    }

    public String[] getSelectedShopRemoveIds() {
        return selectedShopRemoveIds;
    }

    public void setSelectedShopRemoveIds(String[] selectedShopRemoveIds) {
        this.selectedShopRemoveIds = selectedShopRemoveIds;
    }

    public Long getAuthenStatus() {
        return authenStatus;
    }

    public void setAuthenStatus(Long authenStatus) {
        this.authenStatus = authenStatus;
    }
    
}
