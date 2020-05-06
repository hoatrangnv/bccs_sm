/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.SaleTransDetailV2Bean;
import com.viettel.im.client.form.SaleToCollaboratorForm;
import com.viettel.im.client.form.SaleTransForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.SaleTransSerial;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockHandset;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.sms.SmsClient;
import java.math.BigInteger;
import java.util.Iterator;
import org.hibernate.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.ResourceBundle;

/**
 *
 * @author tuan
 */
public class SaleCollToRetailDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(SaleCollToRetailDAO.class);
    //
    private static final String GET_LIST_COLLABORATOR_CODE = "getListCollaboratorCode";
    private static final String GET_COLLABORATOR_NAME = "getCollaboratorName";
    private static final String LIST_GOOD = "lstGoods";         //Danh sach hang hoa
    private static final String LIST_PAYMETHOD = "listPayMethod";       //Danh sach hinh thuc thanh toan
    private static final String LIST_TELECOM_SERVICE = "listTelecomService";        //Danh sach dich vu vien thong
    private static final String LIST_REASON_SALE_EXP_COLLABORATOR = "listReaSonSaleExpCollaborator";        //Danh sach ly do lap giao dich
    private static final String LIST_CHANNEL_TYPE = "listChannelType";      //Danh sach loai doi tuong
    private static final String LIST_PRICE = "priceList";      //Hang hoa theo dich vu vien thong
    private static final String LIST_MODEL = "modelList";      //Gia theo hang hoa
//    private static final String NEW_TYPE = "new";
//    private static final String UPDATE_TYPE = "update";
    private static final String IS_EDIT = "isEdit";     //Trang thai sua hay them moi
    private static final String REQUEST_MESSAGE = "returnMsg";      //Message tra ve Client
    private String pageForward;
    private static final String PAGE_FORWARD_SALE_TO_COLLABORATOR = "saleCollToRetail";       //Refresh ca trang
    private static final String PAGE_FORWARD_SALE_TO_COLLABORATOR_ADD_GOOD = "saleCollToRetailInputGood";       //Refresh phan them hang hoa
    private static final String PAGE_FORWARD_SALE_TO_COLLABORATOR_GOOD_LIST = "saleCollToRetailGoodsList";       //Refresh phan danh sach hang hoa
    SaleToCollaboratorForm saleToCollaboratorForm = new SaleToCollaboratorForm();
    //    TrongLV 11-11-07
    //fix khong quan ly hang hoa theo kenh
    private final boolean IS_STOCK_STAFF_OWNER = true;

    public SaleToCollaboratorForm getSaleToCollaboratorForm() {
        return saleToCollaboratorForm;
    }

    public void setSaleToCollaboratorForm(SaleToCollaboratorForm saleToCollaboratorForm) {
        this.saleToCollaboratorForm = saleToCollaboratorForm;
    }

    private void clearTempList(HttpSession session) {
        session.setAttribute(LIST_MODEL, null);
        session.setAttribute(LIST_PRICE, null);
    }

    /**
     * @Author : TrongLV @CreateDate : 01/08/2010 @Purpose : Khoi tao trang web
     * @return
     * @throws Exception
     */
    public String prepareSaleCollToRetail() throws Exception {
        log.info("Begin method prepareSaleToCollaborator of SaleToCollaboratorDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                //LY DO BAN
                session.removeAttribute(LIST_REASON_SALE_EXP_COLLABORATOR);
                session.setAttribute(LIST_REASON_SALE_EXP_COLLABORATOR, getListReaSonSaleExpCollaborator());

                //DICH VU VIEN THONG
                session.removeAttribute(LIST_TELECOM_SERVICE);
                TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
                telecomServiceDAO.setSession(this.getSession());
                session.setAttribute(LIST_TELECOM_SERVICE, telecomServiceDAO.findAll());

                //HINH THUC THANH TOAN
                AppParamsDAO appParamsDAO = new AppParamsDAO();
                appParamsDAO.setSession(getSession());
                List payMethodList = appParamsDAO.findAppParamsList("PAY_METHOD", "1");
                session.setAttribute(LIST_PAYMETHOD, payMethodList);

                //LOAI DOI TUONG
                session.setAttribute(LIST_CHANNEL_TYPE, AccountBalanceDAO.getChannelTypeList(this.getSession(), Constant.OBJECT_TYPE_STAFF, Constant.NOT_IS_VT_UNIT));

                removeTabSession(LIST_GOOD);
                clearTempList(session);

//                session.setAttribute("stockModelId", null);
//                session.setAttribute("priceId", null);

                //Reset all
                saleToCollaboratorForm.resetForm(true);

                saleToCollaboratorForm.setSaleDate(DateTimeUtils.getSysDateTime("yyyy-MM-dd"));
                saleToCollaboratorForm.setPayMethodId(Constant.PAY_METHOD_MONNEY);

                // tutm1 - 23/10/11 : danh sach loai doi tuong (loai kenh dai ly/ DL door to door/lang xa, thu phi)
                ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
                List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
                req.getSession().setAttribute("lstChannelTypeCol", lstChannelTypeCol);

                pageForward = PAGE_FORWARD_SALE_TO_COLLABORATOR;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method prepareSaleToCollaborator of SaleDAO");
        return pageForward;
    }

    /**
     * @Author : TrongLV @CreateDate : 01/08/2010 @Purpose : Them hang hoa vao
     * danh sach
     * @return
     * @throws Exception
     */
    public String addGoods() throws Exception {
        log.debug("# Begin method addGoods");
        pageForward = PAGE_FORWARD_SALE_TO_COLLABORATOR;
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        SaleToCollaboratorForm goodsFrm = getSaleToCollaboratorForm();


        //----------------------------------------------------------------------
        //tamdt1, 16/09/2010, start bo sung chon mat hang bang F9
        //--------kiem tra ma mat hang co ton tai khong
        String stockModelCodeF9 = this.saleToCollaboratorForm.getStockModelCode();
        Long quantityF9 = this.saleToCollaboratorForm.getQuantity();
        if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("") || quantityF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống
            return pageForward;
        }

        //trim cac truong can thiet
        stockModelCodeF9 = stockModelCodeF9.trim();
        this.saleToCollaboratorForm.setStockModelCode(stockModelCodeF9);
        //
        StockModelDAO stockModelDAOF9 = new StockModelDAO();
        stockModelDAOF9.setSession(this.getSession());
        StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9);
        if (stockModelF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return pageForward;
        } else {
            this.saleToCollaboratorForm.setStockTypeId(stockModelF9.getStockTypeId());
            this.saleToCollaboratorForm.setStockModelId(stockModelF9.getStockModelId());
            this.saleToCollaboratorForm.setStockModelName(stockModelF9.getName());
        }
        //tamdt1, 16/09/2010, end
        //----------------------------------------------------------------------


        if (goodsFrm.getAgentTypeIdSearch() == null || goodsFrm.getAgentTypeIdSearch().trim().equals("")) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.085");
            return pageForward;
        }
        if (goodsFrm.getAgentCodeSearch() == null || goodsFrm.getAgentCodeSearch().trim().equals("")) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.086");
            return pageForward;
        }

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        Staff coll = staffDAO.findStaffAvailableByStaffCode(goodsFrm.getAgentCodeSearch().trim());
        if (coll == null) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.087");
            return pageForward;
        }
        if (coll.getChannelTypeId() == null) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.088");
            return pageForward;
        }


        //check channel type of channel must is_vt_unit = 2
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        ChannelType channelType = channelTypeDAO.findById(coll.getChannelTypeId());
        if (channelType == null || channelType.getIsVtUnit() == null || channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.089");
            return pageForward;
        }


        if ((goodsFrm.getStockModelId() == null) || (goodsFrm.getStockModelId().equals(0L))) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.stock");
            return pageForward;
        }
        if ((goodsFrm.getPriceId() == null) || (goodsFrm.getPriceId() <= 0)) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.price");
            return pageForward;
        }

        if ((goodsFrm.getQuantity() == null) || (goodsFrm.getQuantity() <= 0)) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.quantity");
            return pageForward;
        }

        List lstGoods = (List) getTabSession(LIST_GOOD);
        if (lstGoods == null) {
            lstGoods = new ArrayList();
            setTabSession(LIST_GOOD, lstGoods);
        }

        //Kiem tra hang hoa co ton tai trong kho
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(goodsFrm.getStockModelId());
        if (stockModel == null || stockModel.getStockModelId() == null) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.090");
            return pageForward;
        }

        goodsFrm.setStockModelCode(stockModel.getStockModelCode());

        //check gia xem co dung khong Vunt
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(getSession());
        priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_RETAIL);

        String pricePolicy = coll.getPricePolicy();
        priceDAO.setPricePolicyFilter(pricePolicy);
        priceDAO.setStockModelIdFilter(stockModel.getStockModelId());
        priceDAO.setPriceId(goodsFrm.getPriceId());
        List priceList = priceDAO.findPriceForSaleRetail();
        if (priceList == null || priceList.isEmpty()) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.062");
            return pageForward;
        }

        /*120501 : TRONGLV : check gia + mat hang co khop nhau khong */
        boolean checkValidatePrice = false;
        Iterator iter = priceList.iterator();
        while (iter.hasNext()) {
            Object[] temp = (Object[]) iter.next();
            Long priceId = new Long(temp[0].toString());
            if (priceId != null && priceId.equals(goodsFrm.getPriceId())) {
                checkValidatePrice = true;
                break;
            }
        }

        if (!checkValidatePrice) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.062");
            recoverTempList(req.getSession());
            return pageForward;
        }
        /*120501 : TRONGLV : check gia + mat hang co khop nhau khong */


        //Kiem tra vat hien tai va vat da co
        if (lstGoods.size() > 0) {

            SaleToCollaboratorForm frm = (SaleToCollaboratorForm) lstGoods.get(0);

            if (coll.getStaffId().compareTo(frm.getFromOwnerId()) != 0) {
                recoverTempList(req.getSession());
                req.setAttribute(REQUEST_MESSAGE, "Error. You must input channel code is the same with old value");
                return pageForward;
            }

//            if (goodsFrm.getTelecomServiceId().compareTo(frm.getTelecomServiceId()) != 0) {
            if (stockModel.getTelecomServiceId().compareTo(frm.getTelecomServiceId()) != 0) {
                recoverTempList(req.getSession());
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.091");
                return pageForward;
            }

            //tim vat cu
            Price price_old = new Price();
            price_old = this.findPrice(frm.getPriceId());

            //Tim vat moi
            Price price = this.findPrice(goodsFrm.getPriceId());
            Double newVat = price.getVat();//vat cu

            if ((price.getVat() == null) && (price_old.getVat() == null)) {
                recoverTempList(req.getSession());
                req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.NotSameVat");
                return pageForward;

            }
            if (price.getVat() != null && !newVat.equals(price_old.getVat())) {
                recoverTempList(req.getSession());
                req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.NotSameVat");
                return pageForward;
            }

        }





        //TEN HANG HOA
        goodsFrm.setStockModelId(stockModel.getStockModelId());
        goodsFrm.setStockModelName(stockModel.getName());
        goodsFrm.setCheckSerial(stockModel.getCheckSerial());
        Long checkDial = 0L;
        if (goodsFrm.getCheckDial() != null && goodsFrm.getCheckDial().toString().equals("true")) {
            checkDial = 1L;
        }
        goodsFrm.setCheckDial(checkDial);

        //TEN DON VI
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());
        AppParams appParams = (AppParams) appParamsDAO.findAppParams("STOCK_MODEL_UNIT", stockModel.getUnit());
        if (appParams != null) {
            goodsFrm.setUnit(appParams.getName());
        }

        goodsFrm.setFromOwnerType(Constant.OWNER_TYPE_STAFF);
        goodsFrm.setFromOwnerId(coll.getStaffId());
        goodsFrm.setStateId(Constant.STATE_NEW);

        boolean check = StockCommonDAO.checkStockGoods(goodsFrm.getFromOwnerId(), Constant.OWNER_TYPE_STAFF, goodsFrm.getStockModelId(), Constant.STATE_NEW, goodsFrm.getQuantity().longValue(), Constant.UN_CHECK_DIAL, this.getSession());
        if (check == false) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.saleQuantity");
            return pageForward;
        }

        Price price = priceDAO.findById(goodsFrm.getPriceId());
        if (price == null || price.getPriceId() == null) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.092");
            return pageForward;
        }

        //SO LUONG
        goodsFrm.setQuantityMoney(NumberUtils.formatNumber(goodsFrm.getQuantity()));

        //price
        goodsFrm.setPriceId(price.getPriceId());
        goodsFrm.setPrice(price.getPrice());
//        goodsFrm.setPriceMoney(StringUtils.formatMoney(price.getPrice()));
        goodsFrm.setPriceMoney(NumberUtils.rounđAndFormatAmount(goodsFrm.getPrice()));

        //VAT
        goodsFrm.setItemVat(price.getVat().toString());

        //TONG TIEN
        goodsFrm.setAmount(goodsFrm.getQuantity() * goodsFrm.getPrice());
//        goodsFrm.setAmountMoney(StringUtils.formatMoney(goodsFrm.getQuantity() * goodsFrm.getPrice()));
        goodsFrm.setAmountMoney(NumberUtils.rounđAndFormatAmount(goodsFrm.getAmount()));

        SaleCommonDAO saleCommonDAO = new SaleCommonDAO();
        saleCommonDAO.setSession(this.getSession());
        goodsFrm.setDiscountGroupId(saleCommonDAO.getDiscountGroupId(goodsFrm.getStockModelId(), coll.getDiscountPolicy()));

        //DICH VU VIEN THONG
        TelecomServiceDAO telDAO = new TelecomServiceDAO();
        telDAO.setSession(this.getSession());
//        TelecomService tel = telDAO.findById(goodsFrm.getTelecomServiceId());
        TelecomService tel = telDAO.findById(stockModel.getTelecomServiceId());
        if (tel == null || tel.getTelecomServiceId() == null) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.093");
            this.sumSaleTransAmount(lstGoods);
            return pageForward;
        }
        goodsFrm.setTelecomServiceId(tel.getTelecomServiceId());
        goodsFrm.setTelecomServiceName(tel.getTelServiceName());

        //Kiem tra hang hoa da duoc them tu truoc chua
        String exist = checkGoodsExist(lstGoods, goodsFrm.getStockModelId().toString());

        if (!exist.equals("")) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, exist);
            this.sumSaleTransAmount(lstGoods);
            return pageForward;
        }

        SaleToCollaboratorForm tmp = new SaleToCollaboratorForm();
        BeanUtils.copyProperties(tmp, goodsFrm);

        tmp.setChannelTypeId(coll.getChannelTypeId());
        lstGoods.add(tmp);

        this.sumSaleTransAmount(lstGoods);
        setTabSession(LIST_GOOD, lstGoods);

        goodsFrm.resetForm();
        clearTempList(session);

        req.setAttribute(REQUEST_MESSAGE, "MSG.SAE.076");
        log.debug("# End method SALE_TO_COLLABORATOR");
        return pageForward;
    }

    /**
     * Author: Tuannv Date created: 26/02/2009 Purpose: Nhap serial cho mat hang
     * can xuat kho
     */
    public String refreshListGoods() throws Exception {
        HttpServletRequest req = getRequest();
        String pageFoward = PAGE_FORWARD_SALE_TO_COLLABORATOR_GOOD_LIST;
        try {
//Tien chua thue
            String amountNotTax = (String) QueryCryptUtils.getParameter(req, "amountNotTax");
            saleToCollaboratorForm.setAmountNotTaxMoney(amountNotTax);
            //Tien thue
            String tax = (String) QueryCryptUtils.getParameter(req, "tax");
            saleToCollaboratorForm.setTaxMoney(tax);
            //Tien chiet khau
            String discout = (String) QueryCryptUtils.getParameter(req, "discout");
            saleToCollaboratorForm.setDiscoutMoney(discout);
            //Tien phai tra
            String amountTax = (String) QueryCryptUtils.getParameter(req, "amountTax");
            saleToCollaboratorForm.setAmountTaxMoney(amountTax);
            req.setAttribute("editable", "true");
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return "";
        }

        return pageFoward;
    }

    /**
     * Author: TuanNV Date created: 26/02/2009 Purpose: prepare edit goods in
     * goods list
     */
    public String prepareEditGoods() throws Exception {
        log.debug("# Begin method prepareEditGoods");
        HttpServletRequest req = getRequest();
        pageForward = PAGE_FORWARD_SALE_TO_COLLABORATOR;
        HttpSession session = req.getSession();

//        String stockModelId = (String) QueryCryptUtils.getParameter(req, "stockModelId");
        String strStockModelId = req.getParameter("stockModelId");
        if (strStockModelId == null || strStockModelId.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.094");
            return pageForward;
        }

        //List lstGoods = (List) req.getSession().getAttribute("lstGoods");
        List lstGoods = (List) getTabSession("lstGoods");
        Long stockModelId = Long.parseLong(strStockModelId.trim());

        SaleToCollaboratorForm goodsFrm;
        for (int i = 0; i < lstGoods.size(); i++) {
            goodsFrm = (SaleToCollaboratorForm) lstGoods.get(i);
            if (goodsFrm.getStockModelId().equals(stockModelId)) {
                saleToCollaboratorForm.setTelecomServiceId(goodsFrm.getTelecomServiceId());
                saleToCollaboratorForm.setStockModelId(stockModelId);
                saleToCollaboratorForm.setStockModelCode(goodsFrm.getStockModelCode());
                saleToCollaboratorForm.setStockModelName(goodsFrm.getStockModelName());
                saleToCollaboratorForm.setPriceId(goodsFrm.getPriceId());
                saleToCollaboratorForm.setQuantity(goodsFrm.getQuantity());
                saleToCollaboratorForm.setNote(goodsFrm.getNote());

                StockModelDAO modelDAO = new StockModelDAO();
                modelDAO.setSession(this.getSession());
                modelDAO.setTelecomServiceIdFilter(goodsFrm.getTelecomServiceId());

                UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
                Long shopId = userToken.getShopId();

                StockModel stockModel = modelDAO.findById(stockModelId);
                List<StockModel> modelList = new ArrayList();
                modelList.add(stockModel);
                session.setAttribute(LIST_MODEL, modelList);
                modelDAO.setShopIdFilter(shopId);

                PriceDAO priceDAO = new PriceDAO();
                priceDAO.setSession(getSession());
                Price price = priceDAO.findById(goodsFrm.getPriceId());
                List<Price> priceList = new ArrayList();
                priceList.add(price);
                session.setAttribute(LIST_PRICE, priceList);
                break;
            }
        }

        req.setAttribute(IS_EDIT, true);

        log.debug("# End method prepareEditGoods");
        return pageForward;
    }

    /**
     * Author: Tuannv Date created: 26/02/2009 Purpose: edit goods in goods list
     */
    public String editGoods() throws Exception {
        log.debug("# Begin method editGoods");
        pageForward = PAGE_FORWARD_SALE_TO_COLLABORATOR;
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        SaleToCollaboratorForm goodsFrm = getSaleToCollaboratorForm();

        //----------------------------------------------------------------------
        //tamdt1, 16/09/2010, start bo sung chon mat hang bang F9
        //--------kiem tra ma mat hang co ton tai khong
        String stockModelCodeF9 = this.saleToCollaboratorForm.getStockModelCode();
        Long quantityF9 = this.saleToCollaboratorForm.getQuantity();
        if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("") || quantityF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống
            return pageForward;
        }

        //trim cac truong can thiet
        stockModelCodeF9 = stockModelCodeF9.trim();
        this.saleToCollaboratorForm.setStockModelCode(stockModelCodeF9);
        //
        StockModelDAO stockModelDAOF9 = new StockModelDAO();
        stockModelDAOF9.setSession(this.getSession());
        StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9);
        if (stockModelF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return pageForward;
        } else {
            this.saleToCollaboratorForm.setStockTypeId(stockModelF9.getStockTypeId());
            this.saleToCollaboratorForm.setStockModelId(stockModelF9.getStockModelId());
            this.saleToCollaboratorForm.setStockModelName(stockModelF9.getName());
        }
        //tamdt1, 16/09/2010, end
        //----------------------------------------------------------------------

        //TRANG THAI SUA SO LUONG HANG HOA
        req.setAttribute(IS_EDIT, true);

        //Check ma cong tac vien/diem ban
        if (goodsFrm.getAgentTypeIdSearch() == null || goodsFrm.getAgentTypeIdSearch().trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.085");
            return pageForward;
        }
        if (goodsFrm.getAgentCodeSearch() == null || goodsFrm.getAgentCodeSearch().trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.086");
            return pageForward;
        }

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        Staff coll = staffDAO.findStaffAvailableByStaffCode(goodsFrm.getAgentCodeSearch().trim());
        if (coll == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.087");
            return pageForward;
        }
        if (coll.getChannelTypeId() == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.088");
            return pageForward;
        }


        //check channel type of channel must is_vt_unit = 2
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        ChannelType channelType = channelTypeDAO.findById(coll.getChannelTypeId());
        if (channelType == null || channelType.getIsVtUnit() == null || channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.089");
            return pageForward;
        }


        if ((goodsFrm.getStockModelId() == null) || (goodsFrm.getStockModelId().equals(0L))) {
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.stock");
            return pageForward;
        }
        if ((goodsFrm.getPriceId() == null) || (goodsFrm.getPriceId() <= 0)) {
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.price");
            return pageForward;
        }

        if ((goodsFrm.getQuantity() == null) || (goodsFrm.getQuantity() <= 0)) {
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.quantity");
            return pageForward;
        }


        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        boolean check = stockCommonDAO.checkStockGoods(coll.getStaffId(), Constant.OWNER_TYPE_STAFF, goodsFrm.getStockModelId(), Constant.STATE_NEW, saleToCollaboratorForm.getQuantity().longValue(), Constant.UN_CHECK_DIAL, this.getSession());

        if (check == false) {
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.saleQuantity");
            return pageForward;
        }

        List lstGoods = (List) getTabSession(LIST_GOOD);
        if (lstGoods == null || lstGoods.isEmpty()) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.095");
            return pageForward;
        }

        Long stockModelId = goodsFrm.getStockModelId();

        for (int i = 0; i < lstGoods.size(); i++) {
            SaleToCollaboratorForm frm = (SaleToCollaboratorForm) lstGoods.get(i);
            if (frm.getStockModelId().equals(stockModelId)) {
                frm.setNote(goodsFrm.getNote());

                //SO LUONG
                frm.setQuantity(goodsFrm.getQuantity());
                frm.setQuantityMoney(NumberUtils.formatNumber(frm.getQuantity()));

                //TONG TIEN
                frm.setAmount(frm.getQuantity() * frm.getPrice());
                frm.setAmountMoney(NumberUtils.rounđAndFormatAmount(frm.getAmount()));


                break;
            }
        }

        this.sumSaleTransAmount(lstGoods);
        setTabSession(LIST_GOOD, lstGoods);

        goodsFrm.resetForm();
        clearTempList(session);

        //TRANG THAI KHONG THEM MOI HANG HOA
        req.setAttribute(IS_EDIT, false);

        req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.saveSuccess");
        return pageForward;
    }

    /**
     * Author: Tuannv Date created: 26/02/2009 Purpose: cacel sua hang hoa
     */
    public String cancelEditGoods() throws Exception {
        log.debug("# Begin method cancelEditGoods");
        pageForward = PAGE_FORWARD_SALE_TO_COLLABORATOR_ADD_GOOD;
        HttpServletRequest req = getRequest();
        saleToCollaboratorForm.resetForm();
        clearTempList(req.getSession());
        log.debug("# End method prepareEditGoods");
        return pageForward;
    }

    /**
     * Author: Tuannv Date created: 20/04/2009 Purpose: Kiem tra hang hoa da
     * duoc them truoc do
     */
    public String checkGoodsExist(List lstGoods, String stockModelId) throws Exception {
        if (lstGoods != null) {

            if (stockModelId != null && !"".equals(stockModelId.trim())) {
                Long lStockModelId = Long.parseLong(stockModelId.trim());
                for (int i = 0; i < lstGoods.size(); i++) {
                    if (lstGoods.get(i) instanceof SaleToCollaboratorForm) {
                        SaleToCollaboratorForm saleToCollaboratorForm = (SaleToCollaboratorForm) lstGoods.get(i);
                        if (saleToCollaboratorForm.getStockModelId().equals(lStockModelId)) {
                            return "saleColl.warn.Existed";
                        }
                    }
                }

            } else {

                return "";
            }
        }
        return "";
    }

    /**
     * Author: Tuannv Date created: 20/04/2009 Purpose: Thuc hien xoa hang hoa
     */
    public String delGoods() throws Exception {
        log.debug("# Begin method delGoods");
        pageForward = PAGE_FORWARD_SALE_TO_COLLABORATOR_GOOD_LIST;
        HttpServletRequest req = getRequest();
        List<SaleToCollaboratorForm> lstGoods = (List<SaleToCollaboratorForm>) getTabSession(LIST_GOOD);
        if (lstGoods == null) {
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.Empty");
            return pageForward;
        }

        String stockModelId = (String) req.getParameter("stockModelId");
        if (stockModelId == null || stockModelId.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.NotSelect");
            return pageForward;
        }
        Long lStockModelId = Long.parseLong(stockModelId.trim());
        for (int i = 0; i < lstGoods.size(); i++) {
            SaleToCollaboratorForm frm = lstGoods.get(i);
            if (frm.getStockModelId().equals(lStockModelId)) {
                lstGoods.remove(i);
                break;
            }
        }

        this.sumSaleTransAmount(lstGoods);
        setTabSession(LIST_GOOD, lstGoods);
        saleToCollaboratorForm.resetForm();
        clearTempList(req.getSession());
        req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.delSuccess");
        log.debug("# End method addResource");
        return pageForward;
    }

    /**
     *
     * author : Tuannv date : 20/04/2009 purpose : thuc hien ban hang cho cong
     * tac vien modified : tamdt1, 28/10/2009
     *
     */
    public String saveSaleCollToRetail() throws Exception {
        log.debug("Begin method saveSaleToCollaborator of SaleToCollaboratorDAO...");
        getReqSession();

        org.hibernate.Session mySession = this.getSession();

        try {
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(mySession);
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(mySession);

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(mySession);

            req.setAttribute(IS_EDIT, false);
            saleToCollaboratorForm.resetForm();

            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Shop shop = shopDAO.findById(userToken.getShopId());

            if (saleToCollaboratorForm.getAgentTypeIdSearch() == null || saleToCollaboratorForm.getAgentTypeIdSearch().trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.085");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }
            if (saleToCollaboratorForm.getAgentCodeSearch() == null || saleToCollaboratorForm.getAgentCodeSearch().trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.086");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            Staff staff = staffDAO.findStaffAvailableByStaffCode(saleToCollaboratorForm.getAgentCodeSearch().trim());
            if (staff == null) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.087");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            if (this.IS_STOCK_STAFF_OWNER) {
                if (staff == null
                        || staff.getStaffId() == null
                        || staff.getStaffOwnerId() == null
                        || staff.getShopId() == null
                        || !staff.getStaffOwnerId().equals(userToken.getUserID())
                        || !staff.getShopId().equals(userToken.getShopId())) {
                    req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.096");
                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                }
            } else {
                if (staff == null
                        || staff.getStaffId() == null
                        || staff.getStaffOwnerId() == null
                        || staff.getShopId() == null
                        || !staff.getShopId().equals(userToken.getShopId())) {
                    req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.096");
                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                }
            }
            if (staff.getChannelTypeId() == null) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.088");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            //check channel type of channel must is_vt_unit = 2
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
            ChannelType channelType = channelTypeDAO.findById(staff.getChannelTypeId());
            if (channelType == null || channelType.getIsVtUnit() == null || channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.089");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            if ((saleToCollaboratorForm.getReasonId() == null) || (saleToCollaboratorForm.getReasonId().equals(0L))) {
                req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.reason");
                log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            if ((saleToCollaboratorForm.getPayMethodId() == null) || (saleToCollaboratorForm.getPayMethodId().equals(""))) {
                req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.payMethodNotEmpty");
                log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            List<SaleToCollaboratorForm> lstGoods = (List<SaleToCollaboratorForm>) getTabSession(LIST_GOOD);
            if (lstGoods == null || lstGoods.isEmpty()) {
                req.setAttribute("returnMsg", "saleRetail.warn.NotStock");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            /*
             *add by sonbc2
             *date: 11/04/2016
             */
            ResourceBundle resource = ResourceBundle.getBundle("config");
            if (lstGoods.size() > Integer.parseInt(resource.getString("MAX_OF_GOODS"))) {
                req.setAttribute(REQUEST_MESSAGE, "sale.warn.Exceeds");
                List listMessageParam = new ArrayList();
                listMessageParam.add(resource.getString("MAX_OF_GOODS"));
                req.setAttribute("returnMsgValue", listMessageParam);
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }
            //end sonbc2

            /**
             * Modified by : haint41 Modified date : 16/11/2011 Purpose : check
             * ban bo hang hoa
             */
            String result = this.checkSalePackageGoods(lstGoods);

            if (!result.equals("")) {
                String[] arr = result.split("@");
                if (arr.length < 2) {
                    req.setAttribute("returnMsg", "E.100028");
                } else if (arr.length == 2) {
                    req.setAttribute("returnMsg", "E.100034");
                    List listParamValue = new ArrayList();
                    listParamValue.add(arr[0]);
                    listParamValue.add(arr[1]);
                    req.setAttribute(Constant.RETURN_MESSAGE_VALUE, listParamValue);
                } else {
                    req.setAttribute("returnMsg", "E.100035");
                    List listParamValue = new ArrayList();
                    listParamValue.add(arr[0]);
                    listParamValue.add(arr[1]);
                    listParamValue.add(arr[2]);
                    req.setAttribute(Constant.RETURN_MESSAGE_VALUE, listParamValue);
                }
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }
            /*
             * end haint41
             */

            this.sumSaleTransAmount(lstGoods);



            SaleToCollaboratorForm tmpForm = lstGoods.get(0);
            Long telecomServiceId = tmpForm.getTelecomServiceId();
            Long priceId = tmpForm.getPriceId();
            if (telecomServiceId == null) {
//                req.setAttribute("returnMsg", "Lỗi. Không có loại dịch vụ!");
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.095");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }
            if (priceId == null) {
//                req.setAttribute("returnMsg", "Lỗi mặt hàng chưa được khai báo giá!");
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.095");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }
            Price priceBO = priceDAO.findById(priceId);
            if (priceBO == null) {
//                req.setAttribute("returnMsg", "Lỗi mặt hàng chưa được khai báo giá!");
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.095");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            saleToCollaboratorForm.setTelecomServiceId(telecomServiceId);//Loai dich vu
            saleToCollaboratorForm.setSaleTransVat(priceBO.getVat().toString());//Ti gia VAT
            saleToCollaboratorForm.setCurrency(priceBO.getCurrency());//Ti gia VAT

            //save giao dich ban hang
            SaleTrans saleTrans = this.expSaleTrans(mySession, userToken, staff);
            if (saleTrans == null) {
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                //save giao dich ban hang that bai
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.097");
                log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            Double amountDeposit = 0.0;
            String sendMess = getText("sms.2001");
            sendMess = String.format(sendMess, DateTimeUtils.convertDateToString_tuannv(getSysdate()));
            for (int i = 0; i < lstGoods.size(); i++) {
                SaleToCollaboratorForm SaleTransForm = (SaleToCollaboratorForm) lstGoods.get(i);
                if (SaleTransForm.getLstSerial() == null || SaleTransForm.getLstSerial().isEmpty()) {
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    //save giao dich ban hang that bai
                    req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.098");
                    log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                }

                //save thong tin chi tiet ve giao dich, saleTransDetail + saleTransSerial
                SaleTransDetail saleTransDetail = expSaleTransDetail(mySession, userToken, staff, saleTrans, SaleTransForm);
                if (saleTransDetail == null) {
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.099");
                    log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                }

                if (!saveSaleTransSerial(mySession, userToken, staff, SaleTransForm, saleTransDetail)) {
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.100");
                    log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                }
                if (i != 0) {
                    sendMess += ", " + saleTransDetail.getQuantity() + " " + saleTransDetail.getStockModelName();
                } else {
                    sendMess += " " + saleTransDetail.getQuantity() + " " + saleTransDetail.getStockModelName();
                }

                //Tinh lai tien dat coc
                Double price = getPriceDeposit(mySession, staff, SaleTransForm);
                if (price < 0.0) {
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.NotStock");
                    log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                }
//                priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_DEPOSIT);
//                priceDAO.setPricePolicyFilter(staff.getPricePolicy());//chinh sach gia cua cua hang
//                priceDAO.setStockModelIdFilter(saleTransDetail.getStockModelId());
//                List priceList = priceDAO.findPriceForSaleRetail();
//                Double price = 0.0;
//                if (priceList == null || priceList.isEmpty()) {
//                    mySession.clear();
//                    mySession.getTransaction().rollback();
//                    mySession.beginTransaction();
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.STK.078");
//                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
//                }
//                Object[] temp = (Object[]) priceList.get(0);
//                price = Double.valueOf(temp[1].toString());//gia la sau thue hoac truoc thue
                amountDeposit += price;//Cong tien dat coc

            }
            saleTrans.setAmountService(amountDeposit);//Luu tien dat coc => khi huy giao dich se tru xien dat coc
            getSession().update(saleTrans);//Luu them tong tien dat coc
            sendMess += " (+ " + NumberUtils.formatNumber(amountDeposit) + ") ";
            sendMess += getText("sms.2002");

            //Khi lap hoa don se cong tien giao dich
            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            //Cong tien dat coc vao TKTT & Tru tien giao dich vao TKTT
            String returnMsg = accountAgentDAO.addBalance(getSession(), staff.getStaffId(), Constant.OWNER_TYPE_STAFF,
                    Constant.DEPOSIT_TRANS_TYPE_ADD_COLL_RETAIL,
                    amountDeposit,
                    Constant.DEPOSIT_TRANS_TYPE_MINUS_COLL_RETAIL,
                    -1 * saleTrans.getAmountTax(),
                    getSysdate(), saleTrans.getSaleTransId(), userToken.getLoginName());
            if (!returnMsg.equals("")) {
                mySession.clear();
                mySession.getTransaction().rollback();
                mySession.beginTransaction();
                req.setAttribute(REQUEST_MESSAGE, returnMsg);
                log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            AccountAgent accountAgent = null;
            accountAgent = accountAgentDAO.findByOwnerIdAndOwnerType(mySession, staff.getStaffId(), Constant.OWNER_TYPE_STAFF);
            if (accountAgent != null
                    && accountAgent.getCheckIsdn() != null
                    && accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)
                    && accountAgent.getMsisdn() != null) {
                SmsClient.sendSMS155(accountAgent.getMsisdn(), sendMess);
            }

            removeTabSession(LIST_GOOD);
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.makeSaleService");
            saleToCollaboratorForm = new SaleToCollaboratorForm();
            saleToCollaboratorForm.setPayMethodId(Constant.PAY_METHOD_MONNEY);
        } catch (Exception ex) {
            log.debug("", ex);
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.097");
        }

        saleToCollaboratorForm.setSaleDate(DateTimeUtils.getSysDateTime("yyyy-MM-dd"));
        log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
        return PAGE_FORWARD_SALE_TO_COLLABORATOR;
    }

    public Double getPriceDeposit(org.hibernate.Session session, Staff staff, SaleToCollaboratorForm saleToCollaboratorForm) throws Exception {
        Long stockModelId = 0L;
        List lstSerial = null;
        Double amountDeposit = 0.0;

        stockModelId = saleToCollaboratorForm.getStockModelId();

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(session);
        StockModel stockModel = stockModelDAO.findById(stockModelId);
        if (stockModel == null) {
            return 0.0;
        }
        BaseStockDAO baseStockDAO = new BaseStockDAO();
        Long Id = 815711L;
        StockHandsetDAO stockHandsetDAO = new StockHandsetDAO();
        StockHandset stockHandset = stockHandsetDAO.findById(Id);
        String tableName = baseStockDAO.getTableNameByStockType(stockModel.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);
        String querySql = " Select sum(nvl(deposit_price,0)) from " + tableName + " where 1=1 "
                + " and stock_model_id = ? "
                //                                + " and owner_type = ? "
                //                                + " and owner_id = ? "
                + " and  to_number(serial) >= ? and to_number(serial) <= ? ";
//                                + " and status = 1 "
//                                + "";
        lstSerial = saleToCollaboratorForm.getLstSerial();
        if (lstSerial != null && lstSerial.size() > 0) {
            StockTransSerial stockSerial = null;
            //PreparedStatement stmt = session.connection().prepareStatement(querySql);
            try {
                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    int i = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    Query query = session.createSQLQuery(querySql);
                    query.setParameter(i++, stockModelId);
//                    query.setParameter(i++, Constant.OWNER_TYPE_STAFF);
//                    query.setParameter(i++, staff.getStaffId());
                    query.setParameter(i++, stockSerial.getFromSerial());
                    query.setParameter(i++, stockSerial.getToSerial());
//                    query.setParameter(i++, Constant.STATUS_USE);
                    Object obj = query.uniqueResult();
                    System.out.println("obj=" + obj);
                    if (obj == null) {
                        return -1.0;
                    }
                    amountDeposit += Double.parseDouble(obj.toString());


//                    ResultSet rs = null;
//                    try {
////                Query query = session.createSQLQuery(querySql).addScalar("depositPrice", Hibernate.DOUBLE);
////                        stmt.setLong(1, stockModelId);
////                        stmt.setLong(2, Constant.OWNER_TYPE_STAFF);
////                        stmt.setLong(3, staff.getStaffId());
////                        stmt.setString(4, stockSerial.getFromSerial());
////                        stmt.setString(5, stockSerial.getToSerial());
////                        stmt.setLong(6, Constant.STATUS_USE);
////                        rs = stmt.executeQuery();
////                        if (rs.next()) {
////                            Object deposit1 = rs.getLong("depositPrice");
//////                            System.out.println("deposit: " + deposit);
//////                            amountDeposit += deposit;
////                        }
////                         System.out.println("amountDeposit: " + amountDeposit);
//                        //Start thanhlq6
////                        Query queryDepositPrice = session.createSQLQuery(querySql).addScalar("depositPrice", Hibernate.DOUBLE).setResultTransformer(Transformers.aliasToBean(StockHandset.class));
////
////                        queryDepositPrice.setParameter(0, stockModelId);
////                        queryDepositPrice.setParameter(1, Constant.OWNER_TYPE_STAFF);
////                        queryDepositPrice.setParameter(2, staff.getStaffId());
////                        queryDepositPrice.setParameter(3, stockSerial.getFromSerial());
////                        queryDepositPrice.setParameter(4, stockSerial.getToSerial());
////                        queryDepositPrice.setParameter(5, Constant.STATUS_USE);
////                        List<StockHandset> fuck = queryDepositPrice.list();
//                    } catch (Exception ex) {
//                    } finally {
////                        if (rs != null) {
////                            rs.close();
////                        }
//                    }
                }
            } catch (Exception ex) {
            } finally {
//                if (stmt != null) {
//                    stmt.close();
//                }
            }
            return amountDeposit;
        }
        return 0.0;
    }

    /**
     * @Author: Tuannv @Date created: 25/03/2009 @Purpose: save sale trans
     * serial
     */
    private boolean saveSaleTransSerial(org.hibernate.Session mySession, UserToken userToken, Staff staff, SaleToCollaboratorForm saleTransDetailBean, SaleTransDetail saleTransDetail)
            throws Exception {

        Long stockModelId = 0L;
        List lstSerial = null;
        boolean noError = true;

        stockModelId = saleTransDetailBean.getStockModelId();

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(mySession);
        StockModel stockModel = stockModelDAO.findById(stockModelId);
        if (stockModel == null) {
            return false;
        }
        int total = 0;
        BaseStockDAO baseStockDAO = new BaseStockDAO();
        baseStockDAO.setSession(this.getSession());
        String tableName = baseStockDAO.getTableNameByStockType(stockModel.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);
        String SQLUPDATE = "update " + tableName + " set status = ?, owner_type = ?, owner_id = ? where stock_model_id = ? "
                + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? ";
        lstSerial = saleTransDetailBean.getLstSerial();
        if (lstSerial != null && lstSerial.size() > 0) {
            StockTransSerial stockSerial = null;
            for (int idx = 0; idx < lstSerial.size(); idx++) {
                stockSerial = (StockTransSerial) lstSerial.get(idx);
                BigInteger startSerialTemp = new BigInteger(stockSerial.getFromSerial());
                BigInteger endSerialTemp = new BigInteger(stockSerial.getToSerial());
                Long totals = endSerialTemp.subtract(startSerialTemp).longValue() + 1;
                Long saleTransDetailId = getSequence("SALE_TRANS_SERIAL_SEQ");
                SaleTransSerial saleTransSerial = new SaleTransSerial();
                saleTransSerial.setSaleTransSerialId(saleTransDetailId);
                saleTransSerial.setSaleTransDetailId(saleTransDetail.getSaleTransDetailId());
                saleTransSerial.setStockModelId(stockModelId);
                saleTransSerial.setSaleTransDate(saleTransDetail.getSaleTransDate());
                saleTransSerial.setFromSerial(stockSerial.getFromSerial());
                saleTransSerial.setToSerial(stockSerial.getToSerial());
                saleTransSerial.setQuantity(totals);
                mySession.save(saleTransSerial);

                //Cap nhat ve kho CTV/DB & trang thai da ban
                Query query = mySession.createSQLQuery(SQLUPDATE);
                query.setParameter(0, Constant.STATUS_SALED);
                query.setParameter(1, Constant.OWNER_TYPE_STAFF);
                query.setParameter(2, staff.getStaffId());
                query.setParameter(3, stockModelId);
                query.setParameter(4, Constant.OWNER_TYPE_STAFF);
                query.setParameter(5, staff.getStaffId());
                query.setParameter(6, stockSerial.getFromSerial());
                query.setParameter(7, stockSerial.getToSerial());
                query.setParameter(8, Constant.STATUS_USE);
                int count = query.executeUpdate();
                if (count != totals.intValue()) {
                    return false;
                }
                total += count;
                //luu thong tin vao bang VcRequets
                if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)) {
                    VcRequestDAO vcRequestDAO = new VcRequestDAO();
                    vcRequestDAO.setSession(getSession());
                    vcRequestDAO.saveVcRequest(DateTimeUtils.getSysDate(), stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.REQUEST_TYPE_SALE_CTV_REPLACE, saleTransDetail.getSaleTransId());
                }

                if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_KIT)) {
                    ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                    reqActiveKitDAO.setSession(mySession);
                    reqActiveKitDAO.saveReqActiveKit(stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.SALE_TYPE, saleTransDetail.getSaleTransId(),
                            Long.parseLong(Constant.SALE_TRANS_TYPE_RETAIL_FOR_COL), getSysdate());
                }
            }
        }
        //Check so luong serial update duoc khong bang tong so luong hang xuat di
        if (total != saleTransDetail.getQuantity().intValue()) {
            return false;
        }
        return true;

    }

    /**
     *
     * author : TuanNV date : 25/03/2009 purpose : cap nhat giao dich ban hang
     * cho cong tac vien modified : tamdt1, 28/10/2009
     *
     */
    private SaleTrans expSaleTrans(org.hibernate.Session mySession, UserToken userToken, Staff staff) {
        try {
            Long saleTransId = getSequence("SALE_TRANS_SEQ");
            SaleTrans saleTrans = new SaleTrans();
            saleTrans.setSaleTransId(saleTransId);
            saleTrans.setSaleTransDate(getSysdate());
            saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_RETAIL_FOR_COL); //loai giao dich: ban thay CTV
            saleTrans.setStatus(String.valueOf(Constant.SALE_PAY_NOT_BILL)); //da thanh toan nhung chua lap hoa don
            saleTrans.setShopId(userToken.getShopId());

            saleTrans.setStaffId(staff.getStaffId()); //tru kho CTV  => CTV tao giao dich

            saleTrans.setDiscount(null); //tien chiet khau
            saleTrans.setAmountTax(this.saleToCollaboratorForm.getAmountTax()); //tong tien phai tra cua KH, = chua thue + thue - KM - C/Khau
            saleTrans.setAmountNotTax(this.saleToCollaboratorForm.getAmountNotTax()); //tien chua thue
            if (this.saleToCollaboratorForm.getSaleTransVat() != null && !this.saleToCollaboratorForm.getSaleTransVat().trim().equals("")) {
                saleTrans.setVat(Double.valueOf(this.saleToCollaboratorForm.getSaleTransVat().replaceAll(",", ""))); //tien vat
            }
            saleTrans.setTax(this.saleToCollaboratorForm.getTax());
            saleTrans.setCurrency(this.saleToCollaboratorForm.getCurrency());

            saleTrans.setReceiverId(staff.getStaffId());
            saleTrans.setReceiverType(Constant.OWNER_TYPE_STAFF);

            saleTrans.setCustName(this.saleToCollaboratorForm.getCustName());
            saleTrans.setCompany(this.saleToCollaboratorForm.getCompany());
            saleTrans.setAddress(saleTrans.getAddress());

            saleTrans.setReasonId(this.saleToCollaboratorForm.getReasonId());
            saleTrans.setPayMethod(this.saleToCollaboratorForm.getPayMethodId());
            saleTrans.setTelecomServiceId(this.saleToCollaboratorForm.getTelecomServiceId());
            saleTrans.setSaleTransCode(CommonDAO.formatTransCode(saleTransId));
            saleTrans.setCreateStaffId(userToken.getUserID());
            saleTrans.setCreateStaffId(staff.getStaffId());
            mySession.save(saleTrans);
            return saleTrans;
        } catch (Exception ex) {
            ex.printStackTrace();
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();
            return null;
        }
    }

    /**
     * author : TuanNV date : 25/03/2009 purpose : cap nhat giao dich ban hang
     * cho cong tac vien modified : tamdt1, 28/10/2009
     *
     */
    private SaleTransDetail expSaleTransDetail(org.hibernate.Session mySession, UserToken userToken, Staff staff, SaleTrans saleTrans, SaleToCollaboratorForm saleTransForm) {
        try {

            SaleTransDetail saleTransDetail = new SaleTransDetail();

            //tamdt1, start, 15/07/2010
            //cap nhat them cac truong can thiet phuc vu bao cao doanh thu
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(saleTransForm.getStockModelId());
            if (stockModel != null) {
                saleTransDetail.setStockModelCode(stockModel.getStockModelCode());
                saleTransDetail.setStockModelName(stockModel.getName());

                saleTransDetail.setAccountingModelCode(stockModel.getAccountingModelCode());
                saleTransDetail.setAccountingModelName(stockModel.getAccountingModelName());

            } else {
                //khong tim thay mat hang
                throw new Exception("stockModel is null");
            }

            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(stockModel.getStockTypeId());
            if (stockType != null) {
                saleTransDetail.setStockTypeId(stockType.getStockTypeId());
                saleTransDetail.setStockTypeName(stockType.getName());
            } else {
                //khong tim thay gia
                throw new Exception("stockType is null");
            }
            //tamdt1, end

            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(this.getSession());
            Price price = priceDAO.findById(saleTransForm.getPriceId());
            if (price == null || price.getPriceId() == null) {
                throw new Exception("price is null");
            }

            saleTransDetail.setSaleTransDetailId(getSequence("SALE_TRANS_DETAIL_SEQ"));
            saleTransDetail.setSaleTransId(saleTrans.getSaleTransId());
            saleTransDetail.setSaleTransDate(saleTrans.getSaleTransDate());

            saleTransDetail.setStockModelId(saleTransForm.getStockModelId());


            saleTransDetail.setSaleTransDetailId(getSequence("SALE_TRANS_DETAIL_SEQ"));
            saleTransDetail.setSaleTransId(saleTrans.getSaleTransId());
            saleTransDetail.setSaleTransDate(saleTrans.getSaleTransDate());

            saleTransDetail.setStockModelId(saleTransForm.getStockModelId());

            saleTransDetail.setPriceId(price.getPriceId());
            saleTransDetail.setCurrency(price.getCurrency());
            saleTransDetail.setPriceVat(price.getVat());//thue suat
            saleTransDetail.setPrice(price.getPrice());
            saleTransDetail.setAmount(price.getPrice() * saleTransForm.getQuantity());
            saleTransDetail.setQuantity(saleTransForm.getQuantity());//so luong
            saleTransDetail.setDiscountAmount(0.0);//chiet khau
            saleTransDetail.setDiscountId(null);


            Double amountAfterTax = 0.0;
            Double amountTax = 0.0;
            Double amountBeforeTax = 0.0;

            if (!Constant.PRICE_AFTER_VAT) {
                amountBeforeTax = price.getPrice() * saleTransForm.getQuantity();
                amountTax = amountBeforeTax * price.getVat() / 100.0;
                amountAfterTax = amountBeforeTax + amountTax;
            } else {
                amountAfterTax = price.getPrice() * saleTransForm.getQuantity();
                amountBeforeTax = amountAfterTax * 100.0 / (price.getVat() + 100.0);
                amountTax = amountAfterTax - amountBeforeTax;
            }

            saleTransDetail.setAmountBeforeTax(amountBeforeTax);
            saleTransDetail.setAmountTax(amountTax);
            saleTransDetail.setVatAmount(amountTax);
            saleTransDetail.setAmountAfterTax(amountAfterTax);

            saleTransDetail.setStateId(Constant.STATE_NEW);
            saleTransDetail.setNote(saleTransForm.getNote());
            this.getSession().save(saleTransDetail);


            //tru kho stocktotal
//            StockCommonDAO stockCommonDAO = new StockCommonDAO();
//            stockCommonDAO.setSession(mySession);
//            if (!stockCommonDAO.expStockTotal(Constant.OWNER_TYPE_STAFF, staff.getStaffId(), Constant.STATE_NEW, saleTransDetail.getStockModelId(), saleTransDetail.getQuantity(), true)) {
//                return null;
//            }

            //trung dh3 R499
            GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(),  staff.getStaffId(), Constant.OWNER_TYPE_STAFF, saleTransDetail.getStockModelId(),
                    Constant.STATE_NEW, -saleTransDetail.getQuantity(), -saleTransDetail.getQuantity(), null,
                    userToken.getUserID(), 0L, 0L, "", Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.SALE_TRANS);
            boolean noError = genResult.isSuccess();

            //R499 trung dh3 add end
            if (noError == false) {
                return null;
            }
            return saleTransDetail;
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return null;
        }
    }

    /**
     *
     * author tuannv date: 28/04/2009 Tinh toan cac gia tri tong
     *
     */
    public void sumSaleTransAmount(List lstGoods) throws Exception {

        //Khoi tao gia tri tong
        SaleToCollaboratorForm f = this.getSaleToCollaboratorForm();
        getReqSession();

        List<SaleToCollaboratorForm> tmpList = new ArrayList(lstGoods);
        List<SaleTransDetailV2Bean> saleTransDetailBeanList = new ArrayList();

        for (int idx = 0; idx < tmpList.size(); idx++) {
            SaleToCollaboratorForm oldBean = tmpList.get(idx);
            SaleTransDetailV2Bean newBean = new SaleTransDetailV2Bean();

            newBean.setStockModelId(oldBean.getStockModelId());
            newBean.setQuantity(oldBean.getQuantity());
            newBean.setPriceId(oldBean.getPriceId());
            newBean.setPrice(oldBean.getPrice());
            newBean.setVat(Double.parseDouble(oldBean.getItemVat()));
            newBean.setPriceDisplay(NumberUtils.rounđAndFormatAmount(oldBean.getPrice()));

            if (Constant.PRICE_AFTER_VAT) {//Neu la gia sau thue (VIETNAM)
                newBean.setAmountAfterTax(
                        newBean.getQuantity() * newBean.getPrice());//For update to sale_trans_detail
                newBean.setAmountBeforeTax(newBean.getAmountAfterTax() / (1.0 + newBean.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT));
                newBean.setAmountTax(newBean.getAmountAfterTax() - newBean.getAmountBeforeTax());
            } else {//Neu la gia truoc thue (HAITI)
                newBean.setAmountBeforeTax(
                        newBean.getQuantity() * newBean.getPrice());//For update to sale_trans_detail
                newBean.setAmountTax(newBean.getAmountBeforeTax() * newBean.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT);
                newBean.setAmountAfterTax(newBean.getAmountTax() + newBean.getAmountBeforeTax());
            }
            newBean.setAmountAfterTaxDisplay(
                    NumberUtils.rounđAndFormatAmount(newBean.getAmountAfterTax()));//For display to interface
            newBean.setAmountTaxDisplay(
                    NumberUtils.rounđAndFormatAmount(newBean.getAmountTax()));//For display to interface
            newBean.setAmountBeforeTaxDisplay(
                    NumberUtils.rounđAndFormatAmount(newBean.getAmountBeforeTax()));//For display to interface

            newBean.setDiscountGroupId(oldBean.getDiscountGroupId());
            newBean.setDiscountId(oldBean.getDiscountId());

            saleTransDetailBeanList.add(newBean);
        }

        SaleTransForm saleTransForm = new SaleTransForm();

        saleTransForm.setAmountBeforeTax(0.0);
        saleTransForm.setAmountTax(0.0);
        saleTransForm.setAmountAfterTax(0.0);

        saleTransForm.setAmountBeforeTaxDisplay("0");
        saleTransForm.setAmountTaxDisplay("0");
        saleTransForm.setAmountAfterTaxDisplay("0");

        if (lstGoods == null || lstGoods.isEmpty()) {
            return;
        }

        Double amountAfterTax = 0.0;
        Double amountBeforeTax = 0.0;
        Double amountTax = 0.0;


        for (int i = 0; i < saleTransDetailBeanList.size(); i++) {
            SaleTransDetailV2Bean bean = saleTransDetailBeanList.get(i);
            if (bean == null) {
                continue;
            }
            amountBeforeTax += bean.getAmountBeforeTax();
            amountTax += bean.getAmountTax();
            amountAfterTax += bean.getAmountAfterTax();
        }

        saleTransForm.setAmountBeforeTax(amountBeforeTax);
        saleTransForm.setAmountTax(amountTax);
        saleTransForm.setAmountAfterTax(amountAfterTax);

        saleTransForm.setAmountBeforeTaxDisplay(NumberUtils.rounđAndFormatAmount(amountBeforeTax));
        saleTransForm.setAmountTaxDisplay(NumberUtils.rounđAndFormatAmount(amountTax));
        saleTransForm.setAmountAfterTaxDisplay(NumberUtils.rounđAndFormatAmount(amountAfterTax));


        f.setAmountNotTaxMoney((saleTransForm.getAmountBeforeTaxDisplay()));
        f.setDiscountMoney((saleTransForm.getAmountDiscountDisplay()));
        f.setDiscoutMoney((saleTransForm.getAmountDiscountDisplay()));
        f.setTaxMoney((saleTransForm.getAmountTaxDisplay()));
        f.setAmountTaxMoney((saleTransForm.getAmountAfterTaxDisplay()));

        f.setTax(saleTransForm.getAmountTax());
        f.setAmountNotTax(saleTransForm.getAmountBeforeTax());
        f.setAmountTax(saleTransForm.getAmountAfterTax());
        f.setDiscount(saleTransForm.getAmountDiscount());
        f.setDiscout(saleTransForm.getAmountDiscount());

    }

    /**
     *
     * author tuannv1 date: 16/04/2009 Tim kiem phieu xuat hang cho cong tac
     * vien
     *
     */
    private List getListReaSonSaleExpCollaborator() {
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(getSession());
        Long[] saleTransType = new Long[1];
        saleTransType[0] = Long.parseLong(Constant.SALE_TRANS_TYPE_RETAIL_FOR_COL);
        List reasonList = reasonDAO.getReasonBySaleTransType(saleTransType);
        return reasonList;

//        List listReaSonSaleExpCollaborator = new ArrayList();
//        String strQuery = "from Reason where reasonType = ? and reasonStatus = ?";
//        Query query = getSession().createQuery(strQuery);
//        query.setParameter(0, "SALE_EXP");
//        query.setParameter(1, Constant.STATUS_ACTIVE.toString());
//        listReaSonSaleExpCollaborator = query.list();
//
//        return listReaSonSaleExpCollaborator;
    }

    /**
     * @Author : TrongLV @CreateDate : 01/08/2010 @Purpose : Lay danh sach CTV
     * @return
     * @throws Exception
     */
    public String getListCollaboratorCode() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String staffCode = httpServletRequest.getParameter("saleToCollaboratorForm.staffCode");
            if (staffCode != null && !staffCode.trim().equals("")) {
                //lay danh sach cac staff_code
                UserToken userToken = (UserToken) getRequest().getSession().getAttribute(Constant.USER_TOKEN);
                Long staffId = userToken.getUserID();
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                List<Staff> listStaff = staffDAO.findByPreStaffCode("staffCode", staffCode + "%", staffId);
                if (listStaff != null && listStaff.size() > 0) {
                    for (int i = 0; i < listStaff.size(); i++) {
                        Staff tmpStaff = listStaff.get(i);
                        hashMapResult.put(tmpStaff.getStaffId(), tmpStaff.getStaffCode());

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return GET_LIST_COLLABORATOR_CODE;
    }

    /**
     *
     * author : tamdt1 date : 28/10/2009 purpose : lay staffName, phuc vu viec
     * chon autocompleter
     *
     */
    public String getCollaboratorName() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String staffCode = httpServletRequest.getParameter("staffCode");
            String target = httpServletRequest.getParameter("target");
            if (staffCode != null && staffCode.trim().length() > 0) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                List<Staff> listStaff = staffDAO.findByPropertyAndStatus(StaffDAO.STAFF_CODE, staffCode, Constant.STATUS_ACTIVE);
                if (listStaff != null && listStaff.size() > 0) {
                    hashMapResult.put(target, listStaff.get(0).getName());
                } else {
                    hashMapResult.put(target, "");
                }
            } else {
                hashMapResult.put(target, "");
            }

        } catch (Exception ex) {
            throw ex;
        }

        return GET_COLLABORATOR_NAME;
    }
    /**
     *
     * author tuannv1 date: 09/03/2009 ham lay danh sach cac GoodsDef co' tai
     * nguyen can boc tham
     */
    private Map listModelCombo = new HashMap();
    private Map priceListMap = new HashMap();
    Map hashMapResult = new HashMap();

    /**
     * @Author : TrongLV @CreateDate : 01/08/2010 @Purpose : Lay danh sach hang
     * hoa theo dich vu ban hang
     * @return
     * @throws Exception
     */
    public String getCollaboratorModel() throws Exception {
        log.info("Begin.");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
//            String telecomServiceIdTemp = (String) QueryCryptUtils.getParameter(req, "telecomServiceId");
//            BaseDAOAction baseDAOAction = new BaseDAOAction();

            List tmpList = new ArrayList();
            String[] header = {"", getText("MSG.SAE.023")};
            tmpList.add(header);

            String strTelecomServiceId = req.getParameter("telecomServiceId");
            if (strTelecomServiceId == null || strTelecomServiceId.trim().equals("")) {
                listModelCombo.put("stockModelId", tmpList);
                return "successGood";
            }
            Long telecomServiceId = Long.valueOf(strTelecomServiceId.trim());

            String agentCodeSearch = req.getParameter("agentCodeSearch");
            if (agentCodeSearch == null || agentCodeSearch.trim().equals("")) {
                listModelCombo.put("stockModelId", tmpList);
                return "successGood";
            }
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = staffDAO.findAvailableByStaffCode(agentCodeSearch.trim());
            if (this.IS_STOCK_STAFF_OWNER) {
                if (staff == null
                        || staff.getStaffId() == null
                        || staff.getStaffOwnerId() == null
                        || staff.getShopId() == null
                        || !staff.getStaffOwnerId().equals(userToken.getUserID())
                        || !staff.getShopId().equals(userToken.getShopId())) {
                    listModelCombo.put("stockModelId", tmpList);
                    return "successGood";
                }
            } else {
                if (staff == null
                        || staff.getStaffId() == null
                        || staff.getStaffOwnerId() == null
                        || staff.getShopId() == null
                        || !staff.getShopId().equals(userToken.getShopId())) {
                    listModelCombo.put("stockModelId", tmpList);
                    return "successGood";
                }
            }

            StockModelDAO modelDAO = new StockModelDAO();
            modelDAO.setSession(this.getSession());
            modelDAO.setTelecomServiceIdFilter(telecomServiceId);
            modelDAO.setStaffSalerIdFilter(staff.getStaffId());
//            List<StockModel> model = modelDAO.findStockModelForSaleCollaborater();
            List<StockModel> model = modelDAO.findStockModelForSaleCollToRetail();
            if (model != null && model.size() > 0) {
                tmpList.addAll(model);
            }
            listModelCombo.put("stockModelId", tmpList);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "successGood";
    }

    /**
     *
     * author tuannv1 date: 09/03/2009 ham lay danh sach cac price co' tai
     * nguyen can boc tham
     */
    public String updateStockModelPrice() throws Exception {
        log.info("Begin.");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();

        try {
//            BaseDAOAction baseDAOAction = new BaseDAOAction();

            String[] header = {"", getText("MSG.SAE.024")};
            List tmpList = new ArrayList();

            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

            String agentCodeSearch = req.getParameter("agentCodeSearch");
            if (agentCodeSearch == null || agentCodeSearch.trim().equals("")) {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);
                return "successPrice";
            }

            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = staffDAO.findAvailableByStaffCode(agentCodeSearch.trim());
            if (this.IS_STOCK_STAFF_OWNER) {
                if (staff == null
                        || staff.getStaffId() == null
                        || staff.getStaffOwnerId() == null
                        || staff.getShopId() == null
                        || !staff.getStaffOwnerId().equals(userToken.getUserID())
                        || !staff.getShopId().equals(userToken.getShopId())) {
                    tmpList.add(header);
                    priceListMap.put("priceId", tmpList);
                    return "successPrice";
                }
            } else {
                if (staff == null
                        || staff.getStaffId() == null
                        || staff.getStaffOwnerId() == null
                        || staff.getShopId() == null
                        || !staff.getShopId().equals(userToken.getShopId())) {
                    tmpList.add(header);
                    priceListMap.put("priceId", tmpList);
                    return "successPrice";
                }
            }

            String strStockModelId = req.getParameter("stockModelId");
//            String stockModelIdTemp = (String) QueryCryptUtils.getParameter(req, "stockModelId");

            if (strStockModelId == null || strStockModelId.trim().equals("")) {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);
                return "successPrice";
            }

            Long stockModelId = new Long(strStockModelId);

            /**
             * Get price list
             */
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
            priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_RETAIL);

            String pricePolicy = staff.getPricePolicy();
            priceDAO.setPricePolicyFilter(pricePolicy);
            priceDAO.setStockModelIdFilter(stockModelId);
            List priceList = priceDAO.findPriceForSaleRetail();
            if (priceList == null || priceList.isEmpty()) {
                tmpList.add(header);
            } else {
                tmpList.addAll(priceList);
            }
            priceListMap.put("priceId", tmpList);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "successPrice";
    }

    /**
     *
     * author : tamdt1 date : 17/09/2010 desc : thay the ham
     * updateStockModelPrice do chuyen sang chon mat hang bang F9
     *
     */
    public String updateListModelPrice() throws Exception {
        log.info("Begin method updateListModelPrice of SaleToRetailDAO...");

        HttpServletRequest req = getRequest();

        try {
//            BaseDAOAction baseDAOAction = new BaseDAOAction();

            String[] header = {"", getText("MSG.SAE.024")};
            List tmpList = new ArrayList();

            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            String agentCodeSearch = req.getParameter("agentCodeSearch");
            if (agentCodeSearch == null || agentCodeSearch.trim().equals("")) {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);
                return "successPrice";
            }

            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = staffDAO.findAvailableByStaffCode(agentCodeSearch.trim());
            if (this.IS_STOCK_STAFF_OWNER) {
                if (staff == null
                        || staff.getStaffId() == null
                        || staff.getStaffOwnerId() == null
                        || staff.getShopId() == null
                        || !staff.getStaffOwnerId().equals(userToken.getUserID())
                        || !staff.getShopId().equals(userToken.getShopId())) {
                    tmpList.add(header);
                    priceListMap.put("priceId", tmpList);
                    return "successPrice";
                }
            } else {

                if (staff == null
                        || staff.getStaffId() == null
                        || staff.getStaffOwnerId() == null
                        || staff.getShopId() == null
                        || !staff.getShopId().equals(userToken.getShopId())) {
                    tmpList.add(header);
                    priceListMap.put("priceId", tmpList);
                    return "successPrice";
                }

            }

            String stockModelCodeF9 = (String) req.getParameter("stockModelCode");
            if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("")) {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);
                return "successPrice";
            }

            StockModelDAO stockModelDAOF9 = new StockModelDAO();
            stockModelDAOF9.setSession(this.getSession());
            StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9.trim());

            if (stockModelF9 == null) {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);
                return "successPrice";
            }

            Long stockModelId = stockModelF9.getStockModelId();

            /**
             * Get price list
             */
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
            priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_RETAIL);

            String pricePolicy = staff.getPricePolicy();
            priceDAO.setPricePolicyFilter(pricePolicy);
            priceDAO.setStockModelIdFilter(stockModelId);
            List priceList = priceDAO.findPriceForSaleRetail();
            if (priceList == null || priceList.isEmpty()) {
                tmpList.add(header);
            } else {
                tmpList.addAll(priceList);
            }
            priceListMap.put("priceId", tmpList);

        } catch (Exception e) {
            e.printStackTrace();

            //
//            BaseDAOAction baseDAOAction = new BaseDAOAction();
            List tmpList = new ArrayList();
            String[] header = {"", getText("MSG.SAE.024")};
            tmpList.add(header);
            priceListMap.put("priceId", tmpList);
        }

        log.info("End method updateListModelPrice of SaleToRetailDAO");
        return "successPrice";
    }

    public List findAll() {
        log.debug("finding all TelecomService instances");
        try {
            String queryString = "from TelecomService";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * @Author : TrongLV @CreateDate : 01/08/2010 @Purpose : Lay thong tin gia
     * hang hoa theo id
     * @param id
     * @return
     */
    public Price findPrice(java.lang.Long id) {
        log.debug("getting Price instance with id: " + id);
        try {
            Price instance = (Price) getSession().get("com.viettel.im.database.BO.Price", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public Map getHashMapResult() {
        return hashMapResult;
    }

    public void setHashMapResult(Map hashMapResult) {
        this.hashMapResult = hashMapResult;
    }

    public Map getListModelCombo() {
        return listModelCombo;
    }

    public void setListModelCombo(Map listModelCombo) {
        this.listModelCombo = listModelCombo;
    }

    public Map getPriceListMap() {
        return priceListMap;
    }

    public void setPriceListMap(Map priceListMap) {
        this.priceListMap = priceListMap;
    }

    /**
     * @Author : TrongLV @CreateDate : 01/08/2010 @Purpose : Khoi tao lai thong
     * tin hang hoa & gia hang hoa trong combobox
     * @param session
     */
    private void recoverTempList(HttpSession session) {
        try {
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

            //Thong tin CTV/DB
            String collCode = saleToCollaboratorForm.getAgentCodeSearch();
            if (collCode == null || collCode.trim().equals("")) {
                return;
            }
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff coll = staffDAO.findAvailableByStaffCode(collCode.trim());
            if (this.IS_STOCK_STAFF_OWNER) {
                if (coll == null
                        || coll.getStaffId() == null
                        || coll.getStaffOwnerId() == null
                        || coll.getShopId() == null
                        || !coll.getStaffOwnerId().equals(userToken.getUserID())
                        || !coll.getShopId().equals(userToken.getShopId())) {
                    return;
                }
            } else {
                if (coll == null
                        || coll.getStaffId() == null
                        || coll.getStaffOwnerId() == null
                        || coll.getShopId() == null
                        || !coll.getShopId().equals(userToken.getShopId())) {
                    return;
                }
            }

            //Danh sach hang hoa cua nhan vien quan ly CTV/DB
            StockModelDAO modelDAO = new StockModelDAO();
            modelDAO.setSession(this.getSession());
            modelDAO.setTelecomServiceIdFilter(saleToCollaboratorForm.getTelecomServiceId());
            modelDAO.setStaffSalerIdFilter(coll.getStaffId());
            List model = modelDAO.findStockModelForSaleCollToRetail();
            List<StockModel> modelList = new ArrayList<StockModel>();
            for (int i = 0; i < model.size(); i++) {
                StockModel stockModel = new StockModel();
                Object[] obj = (Object[]) model.get(i);
                stockModel.setStockModelId(Long.valueOf(obj[0].toString()));
                stockModel.setName(obj[1].toString());
                modelList.add(stockModel);
            }
            session.setAttribute(LIST_MODEL, modelList);

            //Chinh sach gia cua CTV/DB
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
            priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_RETAIL);
            priceDAO.setPricePolicyFilter(coll.getPricePolicy());
            priceDAO.setStockModelIdFilter(saleToCollaboratorForm.getStockModelId());
            List price = priceDAO.findPriceForSaleRetail();
            List<Price> priceList = new ArrayList<Price>();
            for (int i = 0; i < price.size(); i++) {
                Object[] obj = (Object[]) price.get(i);
                Price p = new Price();
                p.setPriceId(Long.valueOf(obj[0].toString()));
                p.setPrice(Double.valueOf(obj[1].toString()));
                priceList.add(p);
            }
            session.setAttribute(LIST_PRICE, priceList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @Author : TrongLV @CreateDate : 01/08/2010 @Purpose : Kiem tra kieu so
     * @param sNumber
     * @return
     */
    public boolean chkNumber(String sNumber) {
        int i = 0;
        for (i = 0; i < sNumber.length(); i++) {
            // Check that current character is number.
            if (!Character.isDigit(sNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
