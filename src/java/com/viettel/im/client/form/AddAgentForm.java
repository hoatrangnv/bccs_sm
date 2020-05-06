/**
 * Copyright YYYY Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.im.client.form;

import java.util.Date;
import org.apache.struts.action.ActionForm;
/**
 *
 * @author haint
 */
public class AddAgentForm extends ActionForm{
    
    private Long changeType;
    private String clientFileName;
    private String serverFileName;
    
    private String parShopCode;
    private Long shopId;
    private String shopCode;
    private String shopName;
    private String tradeName;
    private String tin;
    private String account;
    private String bank;
    private String contactName;
    private String contactTitle;
    private String mail;
    private String idNo;
    private String province;
    private String district;
    private String precinct;
    private String address;
    private String profileState;
    private String registryDate;
    private String usefulWidth;
    private String surfaceArea;
    private String boardState;
    private String status;
    private String checkVAT;
    private String agentType;
    private String telNumber;
    //haint41 11/02/2012 : them thong tin to,duong,so nha
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
    
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getChangeType() {
        return changeType;
    }

    public void setChangeType(Long changeType) {
        this.changeType = changeType;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getBoardState() {
        return boardState;
    }

    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }

    public String getCheckVAT() {
        return checkVAT;
    }

    public void setCheckVAT(String checkVAT) {
        this.checkVAT = checkVAT;
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

    public String getParShopCode() {
        return parShopCode;
    }

    public void setParShopCode(String parShopCode) {
        this.parShopCode = parShopCode;
    }

    public String getPrecinct() {
        return precinct;
    }

    public void setPrecinct(String precinct) {
        this.precinct = precinct;
    }

    public String getProfileState() {
        return profileState;
    }

    public void setProfileState(String profileState) {
        this.profileState = profileState;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(String registryDate) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(String surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getUsefulWidth() {
        return usefulWidth;
    }

    public void setUsefulWidth(String usefulWidth) {
        this.usefulWidth = usefulWidth;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contacTitle) {
        this.contactTitle = contacTitle;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }
    
}
