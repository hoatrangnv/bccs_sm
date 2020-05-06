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
public class ViewAccountSubscriberBean implements java.io.Serializable{
    private String serviceCode;
    private String seviceName;
    private String telecomServiceName;
    private String ownerCode;
    private String ownerName;
    private String ownerManagementCode;
    private String ownerManagementName;
    private String shopCode;
    private String shopName;
    private String isdnTypeName;
    private String invoiceName;
    private Date saleTransDate;
    private String isdn;
    private String agentisdn;
    private Long amountIsdnTT;
    private Long amountAgentIsdnTT;
    private Long amountIsdnTS;
    private Long amountAgentIsdnTS;
    private String channelTypeName;

    private Date birthDate;
    private String idNo;
    private Date makeDate;
    private String makeAddress;
    private String contact;
    private String address;
    private String statusSTKName;
    private String statusAcountBalanceName;
    private String statusAnyPayName;
    private String statusOwnerName;
    private Long STATUS0;
    private Long STATUS1;
    private Long STATUS2;
    private Date dateCreated;
    private Date modifierDate;
    private Long amount;

    public String getIsdnTypeName() {
        return isdnTypeName;
    }

    public void setIsdnTypeName(String isdnTypeName) {
        this.isdnTypeName = isdnTypeName;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOwnerManagementCode() {
        return ownerManagementCode;
    }

    public void setOwnerManagementCode(String ownerManagementCode) {
        this.ownerManagementCode = ownerManagementCode;
    }

    public String getOwnerManagementName() {
        return ownerManagementName;
    }

    public void setOwnerManagementName(String ownerManagementName) {
        this.ownerManagementName = ownerManagementName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Date getSaleTransDate() {
        return saleTransDate;
    }

    public void setSaleTransDate(Date saleTransDate) {
        this.saleTransDate = saleTransDate;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getSeviceName() {
        return seviceName;
    }

    public void setSeviceName(String seviceName) {
        this.seviceName = seviceName;
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

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getTelecomServiceName() {
        return telecomServiceName;
    }

    public void setTelecomServiceName(String telecomServiceName) {
        this.telecomServiceName = telecomServiceName;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }
    
    public String getAgentisdn() {
        return agentisdn;
    }

    public void setAgentisdn(String agentisdn) {
        this.agentisdn = agentisdn;
    }

    public Long getAmountAgentIsdnTS() {
        return amountAgentIsdnTS;
    }

    public void setAmountAgentIsdnTS(Long amountAgentIsdnTS) {
        this.amountAgentIsdnTS = amountAgentIsdnTS;
    }

    public Long getAmountAgentIsdnTT() {
        return amountAgentIsdnTT;
    }

    public void setAmountAgentIsdnTT(Long amountAgentIsdnTT) {
        this.amountAgentIsdnTT = amountAgentIsdnTT;
    }

    public Long getAmountIsdnTS() {
        return amountIsdnTS;
    }

    public void setAmountIsdnTS(Long amountIsdnTS) {
        this.amountIsdnTS = amountIsdnTS;
    }

    public Long getAmountIsdnTT() {
        return amountIsdnTT;
    }

    public void setAmountIsdnTT(Long amountIsdnTT) {
        this.amountIsdnTT = amountIsdnTT;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMakeAddress() {
        return makeAddress;
    }

    public void setMakeAddress(String makeAddress) {
        this.makeAddress = makeAddress;
    }

    public Date getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(Date makeDate) {
        this.makeDate = makeDate;
    }

    public String getStatusAcountBalanceName() {
        return statusAcountBalanceName;
    }

    public void setStatusAcountBalanceName(String statusAcountBalanceName) {
        this.statusAcountBalanceName = statusAcountBalanceName;
    }

    public String getStatusAnyPayName() {
        return statusAnyPayName;
    }

    public void setStatusAnyPayName(String statusAnyPayName) {
        this.statusAnyPayName = statusAnyPayName;
    }

    public String getStatusOwnerName() {
        return statusOwnerName;
    }

    public void setStatusOwnerName(String statusOwnerName) {
        this.statusOwnerName = statusOwnerName;
    }

    public String getStatusSTKName() {
        return statusSTKName;
    }

    public void setStatusSTKName(String statusSTKName) {
        this.statusSTKName = statusSTKName;
    }

    public Long getSTATUS0() {
        return STATUS0;
    }

    public void setSTATUS0(Long STATUS0) {
        this.STATUS0 = STATUS0;
    }

    public Long getSTATUS1() {
        return STATUS1;
    }

    public void setSTATUS1(Long STATUS1) {
        this.STATUS1 = STATUS1;
    }

    public Long getSTATUS2() {
        return STATUS2;
    }

    public void setSTATUS2(Long STATUS2) {
        this.STATUS2 = STATUS2;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getModifierDate() {
        return modifierDate;
    }

    public void setModifierDate(Date modifierDate) {
        this.modifierDate = modifierDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }
    
    
}
