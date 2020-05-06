/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.client.bean.SaleTransInvoiceBean;

/**
 *
 * @author MrSun
 */
public class SaleTransInvoiceForm {

    private String shopCodeSearch;
    private String staffCodeSearch;
    private String shopNameSearch;
    private String staffNameSearch;
    private String serialNoSearch;          //- Ký hiệu hoá đơn
    private String invoiceNoSearch;          //- Số hoá đơn
    private String invoiceStatusSearch;     //- Trạng thái hoá đơn
    private String invoiceNoteSearch;       //- Ghi chú
    private String invoiceStaffNameSearch;  //- Người tạo hoá đơn
    private String invoiceUsedIdSearch;     //- Hoa don su dung
    private String[] invoiceUsedIdList;     //- Danh sach hoa don su dung duoc chon
    private String pageForward;     //- pageForward
    //Nhom giao dich
    private Long saleGroup;
    //Thong tin tim kiem giao dich lap hoa don
    private String custNameSearch;
    private String fromDateSearch;
    private String toDateSearch;
    private String saleTransTypeSearch;
    private String telecomServiceIdSearch;
    private String payMethodIdSearch;
    private String reasonIdSearch;
    private String agentIdSearch;
    private String collaboratorIdSearch;
    private String saleTransStatusSearch;
    private String vatSearch;
    private String saleTransList;//Danh sach giao dich duoc chon de lap hoa don
    private String agentTypeIdSearch;
    private String agentCodeSearch;
    private String agentNameSearch;
    private String objTypeSearch;
    private String objIdSearch;
    //Cho hien thi cot 'Lap HD'
    //Neu bang '1' thi moi cho hien thi nut "Lap hoa don"
    private String canSelect = "0";
    //La popup hay body
    //Neu la popup thi khong cho hien thi nut "Quay lai"
    private String isPopup = "0";
    //Thong tin hoa don
    private String invoiceUsedId;
    private String invoiceType;
    private String[] saleTransIdList;
    //Thong tin nguoi lap hoa don
    private String shopId;
    private String shopCode;
    private String shopName;
    private String staffId;
    private String staffCode;
    private String staffName;
    //Ngay tao
    private String createDate;
    //Hinh thuc thanh toan
    private String payMethodId;
    //Ly do lap hoa don
    private String reasonId;
    //Ghi chu
    private String note;
    private String cancelReasonId;
    //Thong tin so hoa don
    private String invoiceListId;
    private String serialNo;
    private String fromInvoice;
    private String toInvoice;
    private String curInvoice;
    private String invoiceNo;
    private String invoiceNoEdit;
    private String invoiceStatus;
    //Thong tin khach hang
    private String objId;
    private String objCode;
    private String objName;
    private String objCompany;
    private String objTin;
    private String objAccount;
    private String objAddress;
    //String
    //Tong tien truoc thue
    private String amountNotTaxMoney;
    //Tu suat thue
    private String vatMoney;
    //Tien thue
    private String taxMoney;
    //Tien chiet khau
    private String discountMoney;
    //Tien khuyen mai
    private String promotionMoney;
    //Tong tien thanh toan
    private String amountTaxMoney;
    //Double
    //Tong tien truoc thue
    private Double amountNotTax;
    //Tu suat thue
    private Double vat;
    //Tien thue
    private Double tax;
    //Tien chiet khau
    private Double discount;
    //Tien khuyen mai
    private Double promotion;
    //Tong tien thanh toan
    private Double amountTax;
    private String exportUrl;
//    tannh20180501  : in file hoa don moi theo yeu cua cua TraTV phong TC
    private String exportUrl1;
    private String exportUrl2;
    private String exprotUrlRac;
    //Phan quyen sua, huy hoa don
    private boolean cancelInvoice;
    private boolean editInvoice;
    private boolean recoverInvoice;
    private long processingInvoice; //1: edit; 2: cancel; 3: recover
    private String currency;
    private String approveStatusSearch;     //-Trạng thái duyệt hóa đơn
    private String urlReport;

    public String getUrlReport() {
        return urlReport;
    }

    public void setUrlReport(String urlReport) {
        this.urlReport = urlReport;
    }

    public String getApproveStatusSearch() {
        return approveStatusSearch;
    }

    public void setApproveStatusSearch(String approveStatusSearch) {
        this.approveStatusSearch = approveStatusSearch;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAgentCodeSearch() {
        return agentCodeSearch;
    }

    public void setAgentCodeSearch(String agentCodeSearch) {
        this.agentCodeSearch = agentCodeSearch;
    }

    public String getAgentIdSearch() {
        return agentIdSearch;
    }

    public void setAgentIdSearch(String agentIdSearch) {
        this.agentIdSearch = agentIdSearch;
    }

    public String getAgentNameSearch() {
        return agentNameSearch;
    }

    public void setAgentNameSearch(String agentNameSearch) {
        this.agentNameSearch = agentNameSearch;
    }

    public String getAgentTypeIdSearch() {
        return agentTypeIdSearch;
    }

    public void setAgentTypeIdSearch(String agentTypeIdSearch) {
        this.agentTypeIdSearch = agentTypeIdSearch;
    }

    public Double getAmountNotTax() {
        return amountNotTax;
    }

    public void setAmountNotTax(Double amountNotTax) {
        this.amountNotTax = amountNotTax;
    }

    public String getAmountNotTaxMoney() {
        return amountNotTaxMoney;
    }

    public void setAmountNotTaxMoney(String amountNotTaxMoney) {
        this.amountNotTaxMoney = amountNotTaxMoney;
    }

    public Double getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Double amountTax) {
        this.amountTax = amountTax;
    }

    public String getAmountTaxMoney() {
        return amountTaxMoney;
    }

    public void setAmountTaxMoney(String amountTaxMoney) {
        this.amountTaxMoney = amountTaxMoney;
    }

    public String getCanSelect() {
        return canSelect;
    }

    public void setCanSelect(String canSelect) {
        this.canSelect = canSelect;
    }

    public boolean isCancelInvoice() {
        return cancelInvoice;
    }

    public void setCancelInvoice(boolean cancelInvoice) {
        this.cancelInvoice = cancelInvoice;
    }

    public String getCancelReasonId() {
        return cancelReasonId;
    }

    public void setCancelReasonId(String cancelReasonId) {
        this.cancelReasonId = cancelReasonId;
    }

    public String getCollaboratorIdSearch() {
        return collaboratorIdSearch;
    }

    public void setCollaboratorIdSearch(String collaboratorIdSearch) {
        this.collaboratorIdSearch = collaboratorIdSearch;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCurInvoice() {
        return curInvoice;
    }

    public void setCurInvoice(String curInvoice) {
        this.curInvoice = curInvoice;
    }

    public String getCustNameSearch() {
        return custNameSearch;
    }

    public void setCustNameSearch(String custNameSearch) {
        this.custNameSearch = custNameSearch;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(String discountMoney) {
        this.discountMoney = discountMoney;
    }

    public boolean isEditInvoice() {
        return editInvoice;
    }

    public void setEditInvoice(boolean editInvoice) {
        this.editInvoice = editInvoice;
    }

    public String getExportUrl() {
        return exportUrl;
    }

    public void setExportUrl(String exportUrl) {
        this.exportUrl = exportUrl;
    }

    public String getExprotUrlRac() {
        return exprotUrlRac;
    }

    public void setExprotUrlRac(String exprotUrlRac) {
        this.exprotUrlRac = exprotUrlRac;
    }

    public String getFromDateSearch() {
        return fromDateSearch;
    }

    public void setFromDateSearch(String fromDateSearch) {
        this.fromDateSearch = fromDateSearch;
    }

    public String getFromInvoice() {
        return fromInvoice;
    }

    public void setFromInvoice(String fromInvoice) {
        this.fromInvoice = fromInvoice;
    }

    public String getInvoiceListId() {
        return invoiceListId;
    }

    public void setInvoiceListId(String invoiceListId) {
        this.invoiceListId = invoiceListId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceNoEdit() {
        return invoiceNoEdit;
    }

    public void setInvoiceNoEdit(String invoiceNoEdit) {
        this.invoiceNoEdit = invoiceNoEdit;
    }

    public String getInvoiceNoSearch() {
        return invoiceNoSearch;
    }

    public void setInvoiceNoSearch(String invoiceNoSearch) {
        this.invoiceNoSearch = invoiceNoSearch;
    }

    public String getInvoiceNoteSearch() {
        return invoiceNoteSearch;
    }

    public void setInvoiceNoteSearch(String invoiceNoteSearch) {
        this.invoiceNoteSearch = invoiceNoteSearch;
    }

    public String getInvoiceStaffNameSearch() {
        return invoiceStaffNameSearch;
    }

    public void setInvoiceStaffNameSearch(String invoiceStaffNameSearch) {
        this.invoiceStaffNameSearch = invoiceStaffNameSearch;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceStatusSearch() {
        return invoiceStatusSearch;
    }

    public void setInvoiceStatusSearch(String invoiceStatusSearch) {
        this.invoiceStatusSearch = invoiceStatusSearch;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceUsedId() {
        return invoiceUsedId;
    }

    public void setInvoiceUsedId(String invoiceUsedId) {
        this.invoiceUsedId = invoiceUsedId;
    }

    public String[] getInvoiceUsedIdList() {
        return invoiceUsedIdList;
    }

    public void setInvoiceUsedIdList(String[] invoiceUsedIdList) {
        this.invoiceUsedIdList = invoiceUsedIdList;
    }

    public String getInvoiceUsedIdSearch() {
        return invoiceUsedIdSearch;
    }

    public void setInvoiceUsedIdSearch(String invoiceUsedIdSearch) {
        this.invoiceUsedIdSearch = invoiceUsedIdSearch;
    }

    public String getIsPopup() {
        return isPopup;
    }

    public void setIsPopup(String isPopup) {
        this.isPopup = isPopup;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getObjAccount() {
        return objAccount;
    }

    public void setObjAccount(String objAccount) {
        this.objAccount = objAccount;
    }

    public String getObjAddress() {
        return objAddress;
    }

    public void setObjAddress(String objAddress) {
        this.objAddress = objAddress;
    }

    public String getObjCode() {
        return objCode;
    }

    public void setObjCode(String objCode) {
        this.objCode = objCode;
    }

    public String getObjCompany() {
        return objCompany;
    }

    public void setObjCompany(String objCompany) {
        this.objCompany = objCompany;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getObjIdSearch() {
        return objIdSearch;
    }

    public void setObjIdSearch(String objIdSearch) {
        this.objIdSearch = objIdSearch;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public String getObjTin() {
        return objTin;
    }

    public void setObjTin(String objTin) {
        this.objTin = objTin;
    }

    public String getObjTypeSearch() {
        return objTypeSearch;
    }

    public void setObjTypeSearch(String objTypeSearch) {
        this.objTypeSearch = objTypeSearch;
    }

    public String getPageForward() {
        return pageForward;
    }

    public void setPageForward(String pageForward) {
        this.pageForward = pageForward;
    }

    public String getPayMethodId() {
        return payMethodId;
    }

    public void setPayMethodId(String payMethodId) {
        this.payMethodId = payMethodId;
    }

    public String getPayMethodIdSearch() {
        return payMethodIdSearch;
    }

    public void setPayMethodIdSearch(String payMethodIdSearch) {
        this.payMethodIdSearch = payMethodIdSearch;
    }

    public long getProcessingInvoice() {
        return processingInvoice;
    }

    public void setProcessingInvoice(long processingInvoice) {
        this.processingInvoice = processingInvoice;
    }

    public Double getPromotion() {
        return promotion;
    }

    public void setPromotion(Double promotion) {
        this.promotion = promotion;
    }

    public String getPromotionMoney() {
        return promotionMoney;
    }

    public void setPromotionMoney(String promotionMoney) {
        this.promotionMoney = promotionMoney;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonIdSearch() {
        return reasonIdSearch;
    }

    public void setReasonIdSearch(String reasonIdSearch) {
        this.reasonIdSearch = reasonIdSearch;
    }

    public boolean isRecoverInvoice() {
        return recoverInvoice;
    }

    public void setRecoverInvoice(boolean recoverInvoice) {
        this.recoverInvoice = recoverInvoice;
    }

    public Long getSaleGroup() {
        return saleGroup;
    }

    public void setSaleGroup(Long saleGroup) {
        this.saleGroup = saleGroup;
    }

    public String[] getSaleTransIdList() {
        return saleTransIdList;
    }

    public void setSaleTransIdList(String[] saleTransIdList) {
        this.saleTransIdList = saleTransIdList;
    }

    public String getSaleTransList() {
        return saleTransList;
    }

    public void setSaleTransList(String saleTransList) {
        this.saleTransList = saleTransList;
    }

    public String getSaleTransStatusSearch() {
        return saleTransStatusSearch;
    }

    public void setSaleTransStatusSearch(String saleTransStatusSearch) {
        this.saleTransStatusSearch = saleTransStatusSearch;
    }

    public String getSaleTransTypeSearch() {
        return saleTransTypeSearch;
    }

    public void setSaleTransTypeSearch(String saleTransTypeSearch) {
        this.saleTransTypeSearch = saleTransTypeSearch;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSerialNoSearch() {
        return serialNoSearch;
    }

    public void setSerialNoSearch(String serialNoSearch) {
        this.serialNoSearch = serialNoSearch;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopCodeSearch() {
        return shopCodeSearch;
    }

    public void setShopCodeSearch(String shopCodeSearch) {
        this.shopCodeSearch = shopCodeSearch;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopNameSearch() {
        return shopNameSearch;
    }

    public void setShopNameSearch(String shopNameSearch) {
        this.shopNameSearch = shopNameSearch;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffCodeSearch() {
        return staffCodeSearch;
    }

    public void setStaffCodeSearch(String staffCodeSearch) {
        this.staffCodeSearch = staffCodeSearch;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffNameSearch() {
        return staffNameSearch;
    }

    public void setStaffNameSearch(String staffNameSearch) {
        this.staffNameSearch = staffNameSearch;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(String taxMoney) {
        this.taxMoney = taxMoney;
    }

    public String getTelecomServiceIdSearch() {
        return telecomServiceIdSearch;
    }

    public void setTelecomServiceIdSearch(String telecomServiceIdSearch) {
        this.telecomServiceIdSearch = telecomServiceIdSearch;
    }

    public String getToDateSearch() {
        return toDateSearch;
    }

    public void setToDateSearch(String toDateSearch) {
        this.toDateSearch = toDateSearch;
    }

    public String getToInvoice() {
        return toInvoice;
    }

    public void setToInvoice(String toInvoice) {
        this.toInvoice = toInvoice;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public String getVatMoney() {
        return vatMoney;
    }

    public void setVatMoney(String vatMoney) {
        this.vatMoney = vatMoney;
    }

    public String getVatSearch() {
        return vatSearch;
    }

    public void setVatSearch(String vatSearch) {
        this.vatSearch = vatSearch;
    }

    public String getExportUrl1() {
        return exportUrl1;
    }

    public void setExportUrl1(String exportUrl1) {
        this.exportUrl1 = exportUrl1;
    }

    public String getExportUrl2() {
        return exportUrl2;
    }

    public void setExportUrl2(String exportUrl2) {
        this.exportUrl2 = exportUrl2;
    }

//
//
//    ///Tao giao dich ao khi lap hoa don tu khong giao dich
//
//    private boolean itemIsEdit = false;
//
//    private Long itemTelecomServiceId;
//    private Long itemEditTelecomServiceId;
//    private String itemTelecomServiceName;
//
//    private Long itemId;
//    private Long itemEditId;
//    private String itemCode;
//    private String itemName;
//    private String itemNote;
//
//    private Long itemPriceId;
//    private Long itemEditPriceId;
//    private Long itemPrice;
//    private String itemPriceDisplay;
//    private Long itemVAT;
//
//    private Long itemQuantity;
//    private Long itemAmount;
//    private String itemAmountDisplay;
//
//    private Long itemAmountTax;
//    private Long itemAmountNotTax;
//    private Long itemTax;
//
//    private String itemAmountTaxDisplay;
//    private String itemAmountNotTaxDisplay;
//    private String itemTaxDisplay;
//
//    private Long itemEditVAT;
    public void ClearSearchInfo() {
        //Thong tin tim kiem giao dich lap hoa don
        this.custNameSearch = "";
        this.fromDateSearch = "";
        this.toDateSearch = "";
        this.saleTransTypeSearch = "";
        this.telecomServiceIdSearch = "";
        this.payMethodIdSearch = "";
        this.reasonIdSearch = "";
        this.agentIdSearch = "";
        this.collaboratorIdSearch = "";
        this.saleTransStatusSearch = "";
        this.vatSearch = "";
    }

    public void CopySearchInfo(SaleTransInvoiceForm f) {
        //Thong tin tim kiem giao dich lap hoa don
        this.custNameSearch = f.getCustNameSearch();
        this.fromDateSearch = f.getFromDateSearch();
        this.toDateSearch = f.getToDateSearch();
        this.saleTransTypeSearch = f.getSaleTransTypeSearch();
        this.telecomServiceIdSearch = f.telecomServiceIdSearch;
        this.payMethodIdSearch = f.getPayMethodIdSearch();
        this.reasonIdSearch = f.getReasonIdSearch();
        this.agentIdSearch = f.getAgentIdSearch();
        this.collaboratorIdSearch = f.getCollaboratorIdSearch();
        this.saleTransStatusSearch = f.getSaleTransStatusSearch();
        this.vatSearch = f.getVatSearch();

        this.isPopup = f.getIsPopup();
        this.saleGroup = f.getSaleGroup();
        this.canSelect = f.getCanSelect();
    }

    public void CopyBeanInfo(SaleTransInvoiceBean b) {
        //Thong tin khach hang
        this.objName = b.getCustName();
        this.objCompany = b.getCompany();
        this.objTin = b.getTin();
        this.objAccount = b.getAccount();
        this.objAddress = b.getAddress();

        //Kiem tra neu la dai ly/cong tac vien
        if (null != b.getToOwnerId() && null != b.getToOwnerType() && 0 == b.getToOwnerType().compareTo(1L)) {
            this.objCompany = b.getToOwnerName();
        }

        //Thong tin hoa don
        this.payMethodId = b.getPayMethod();
        this.note = b.getNote();

        this.setCurrency(b.getCurrency());

    }
//    public void ClearItemInfo(){
//        this.itemTelecomServiceId = null;
//        this.itemId = null;
//        this.itemPriceId = null;
//        this.itemQuantity = null;
//        this.itemNote = "";
//    }
}
