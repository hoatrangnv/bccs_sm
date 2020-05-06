/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import java.util.Date;

/**
 *
 * @author User
 */
public class ViewStaffImportBean implements java.io.Serializable {

    private String shopCode;
    private String shopName;
    private String staffCode;    
    private String staffName;
    private String tradeName;
    private String contactName;
    private String staffOwnerCode;
    private String staffOwnerName;
    private Date birthDate;
    private String idNo;
    private Date idIssueDate;
    private String idIssuePlace;

    private String province;
    private String district;
    private String precinct;
    private String address;

    private Long profileState;
    private Date registryDate;
    private String usefulWidth;
    private String surfaceArea;
    private Long boardState;
    private Long status;
    private Long channelTypeId;
    private String channelTypeName;

    private String note;
    private String tel;
    private String type;
    
    //haint41 11/02/2012 : them thong tin To,Duong,So nha
    private String streetBlockName;
    private String streetName;
    private String home;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getStreetBlockName() {
        return streetBlockName;
    }

    public void setStreetBlockName(String streetBlockName) {
        this.streetBlockName = streetBlockName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
        
    public ViewStaffImportBean() {
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Long getBoardState() {
        return boardState;
    }

    public void setBoardState(Long boardState) {
        this.boardState = boardState;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Date getIdIssueDate() {
        return idIssueDate;
    }

    public void setIdIssueDate(Date idIssueDate) {
        this.idIssueDate = idIssueDate;
    }

    public String getIdIssuePlace() {
        return idIssuePlace;
    }

    public void setIdIssuePlace(String idIssuePlace) {
        this.idIssuePlace = idIssuePlace;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPrecinct() {
        return precinct;
    }

    public void setPrecinct(String precinct) {
        this.precinct = precinct;
    }

    public Long getProfileState() {
        return profileState;
    }

    public void setProfileState(Long profileState) {
        this.profileState = profileState;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Date getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(Date registryDate) {
        this.registryDate = registryDate;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffOwnerCode() {
        return staffOwnerCode;
    }

    public void setStaffOwnerCode(String staffOwnerCode) {
        this.staffOwnerCode = staffOwnerCode;
    }

    public String getStaffOwnerName() {
        return staffOwnerName;
    }

    public void setStaffOwnerName(String staffOwnerName) {
        this.staffOwnerName = staffOwnerName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(String surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public String getUsefulWidth() {
        return usefulWidth;
    }

    public void setUsefulWidth(String usefulWidth) {
        this.usefulWidth = usefulWidth;
    }

    

}
