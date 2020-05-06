/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.anypay.database.AnypaySession;
import com.viettel.anypay.database.BO.AnypayMsg;
import com.viettel.anypay.logic.AnypayLogic;
import com.viettel.anypay.util.FindTransUtil;
import com.viettel.anypay.util.TransData;
import com.viettel.common.ViettelService;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.bean.AnyPayTransBean;
import com.viettel.im.client.bean.AnyPayTransFPTBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.SaleTransDetailV2Bean;
import com.viettel.im.client.form.AnyPayTransManagementForm;
import com.viettel.im.client.form.SaleToAccountAnyPayForm;
import com.viettel.im.client.form.SaleTransForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.NumberUtils;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.viettel.im.database.BO.UserToken;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.Shop;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.hibernate.Query;
import javax.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.StockOwnerTmp;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.sms.SmsClient;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author User
 */
public class SaleAnyPayToAgentOrStaffDAO extends BaseDAOAction {

    private static final String SALE_TO_ANYPAY = "saleToAnyPay";
    private static final String UPDATE_CHECK_VAT = "updateCheckVAT";
    private static final String SALE_ERROR_ANYPAY = "saleErrorAnyPay";
    private static final String SALE_ANYPAY_TRANS_MANAGEMENT = "saleAnyPayTransManagement";
    private static final String LIST_ANYPAY_TRANS = "lstAnyPayTrans";
    private String LIST_REASON_DESTROY = "lstReasonDestroy";
    private Long MAX_RESULT = 1000L;
    private static final Logger log = Logger.getLogger(SaleAnyPayToAgentOrStaffDAO.class);
    private SaleToAccountAnyPayForm saleToAnyPayForm;
    private AnyPayTransManagementForm anyPayTransForm;
    private final boolean IS_STOCK_STAFF_OWNER = false;

    public SaleToAccountAnyPayForm getSaleToAnyPayForm() {
        return saleToAnyPayForm;
    }

    public void setSaleToAnyPayForm(SaleToAccountAnyPayForm saleToAnyPayForm) {
        this.saleToAnyPayForm = saleToAnyPayForm;
    }

    public AnyPayTransManagementForm getAnyPayTransForm() {
        return anyPayTransForm;
    }

    public void setAnyPayTransForm(AnyPayTransManagementForm anyPayTransForm) {
        this.anyPayTransForm = anyPayTransForm;
    }

    public String preparePage() throws Exception {
        log.debug("# Begin method prepareBeforeSaleToAccountAnyPay");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = SALE_TO_ANYPAY;
        saleToAnyPayForm = new SaleToAccountAnyPayForm();
        //Lay thong tin hang hoa the cao dien tu                
        SaleToAccountAnyPayForm f = getStockModelInfo(userToken.getUserID(), Constant.STOCK_MODEL_CODE_TCDT);
        saleToAnyPayForm.setSaleTransDate(DateTimeUtils.getSysdate());
        //tutm1 07/03/2012 fix truong hop f = null => man hinh loi~ do
        if (f != null) {
            saleToAnyPayForm.setItemPriceId(f.getItemPriceId());
            saleToAnyPayForm.setItemPrice(f.getItemPrice());
            saleToAnyPayForm.setItemPriceMoney(f.getItemPriceMoney());

            saleToAnyPayForm.setStockModelId(f.getStockModelId());
            saleToAnyPayForm.setStockModelCode(f.getStockModelCode());
            saleToAnyPayForm.setStockModelName(f.getStockModelName());

            saleToAnyPayForm.setTelecomServiceId(f.getTelecomServiceId());
            saleToAnyPayForm.setTelecomServiceName(f.getTelecomServiceName());
        }

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> lstReason = reasonDAO.findAllReasonByType(Constant.SALE_TO_ANYPAY);
        req.getSession().setAttribute("listReason", lstReason);

        //String sql = "From ChannelType where isVtUnit = ?";
        //ThanhNC sua khong ban anypay cho dai ly
        String sql = "From ChannelType where isVtUnit = ? and objectType= ? ";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, Constant.IS_NOT_VT_UNIT);
        query.setParameter(1, Constant.OBJECT_TYPE_STAFF);
        List<ChannelType> listChannelType = query.list();
        req.getSession().setAttribute("channelTypeList", listChannelType);

        log.debug("# End method prepareBeforeSaleToAccountAnyPay");

        return pageForward;
    }

    public String preparePageError() throws Exception {
        log.debug("# Begin method prepareBeforeSaleToAccountAnyPay");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = SALE_ERROR_ANYPAY;
        saleToAnyPayForm = new SaleToAccountAnyPayForm();
        //Lay thong tin hang hoa the cao dien tu
        SaleToAccountAnyPayForm f = getStockModelInfo(userToken.getUserID(), Constant.STOCK_MODEL_CODE_TCDT);
        saleToAnyPayForm.setSaleTransDate(DateTimeUtils.getSysdate());

        saleToAnyPayForm.setItemPriceId(f.getItemPriceId());
        saleToAnyPayForm.setItemPrice(f.getItemPrice());
        saleToAnyPayForm.setItemPriceMoney(f.getItemPriceMoney());

        saleToAnyPayForm.setStockModelId(f.getStockModelId());
        saleToAnyPayForm.setStockModelCode(f.getStockModelCode());
        saleToAnyPayForm.setStockModelName(f.getStockModelName());

        saleToAnyPayForm.setTelecomServiceId(f.getTelecomServiceId());
        saleToAnyPayForm.setTelecomServiceName(f.getTelecomServiceName());

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> lstReason = reasonDAO.findAllReasonByType(Constant.SALE_TO_ANYPAY);
        req.getSession().setAttribute("listReason", lstReason);

        //String sql = "From ChannelType where isVtUnit = ?";
        //ThanhNC sua khong ban anypay cho dai ly
        String sql = "From ChannelType where isVtUnit = ? and objectType= ? ";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, Constant.IS_NOT_VT_UNIT);
        query.setParameter(1, Constant.OBJECT_TYPE_STAFF);
        List<ChannelType> listChannelType = query.list();
        req.getSession().setAttribute("channelTypeList", listChannelType);

        log.debug("# End method prepareBeforeSaleToAccountAnyPay");

        return SALE_ERROR_ANYPAY;
    }

    public String preparePageUpdateCheckVAT() throws Exception {
        log.debug("# Begin method prepareBeforeSaleToAccountAnyPay");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = UPDATE_CHECK_VAT;
        log.debug("# End method prepareBeforeSaleToAccountAnyPay");
        return UPDATE_CHECK_VAT;
    }

    //query stock_model(TCDT) info
    private SaleToAccountAnyPayForm getStockModelInfo(Long staffId, String stockModelCode) {
        try {
            SaleToAccountAnyPayForm f = null;

            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            List<StockModel> lstStockModel = stockModelDAO.findByStockModelCode(stockModelCode);
            if (lstStockModel == null || lstStockModel.size() != 1) {
                return f;
            }
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = staffDAO.findById(staffId);
            if (staff == null) {
                return f;
            }
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
            priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_COLLABOR);
            String pricePolicy = staff.getPricePolicy();
            priceDAO.setPricePolicyFilter(pricePolicy);
            priceDAO.setStockModelIdFilter(lstStockModel.get(0).getStockModelId());
            List lstPrice = priceDAO.findPriceForSaleRetail();
            if (lstPrice == null || lstPrice.size() <= 0) {
                return f;
            }
            f = new SaleToAccountAnyPayForm();
            Object[] obj = (Object[]) lstPrice.get(0);
            if (obj != null && obj.length >= 2 && obj[0] != null && obj[1] != null) {
                f.setItemPriceId(Long.valueOf(obj[0].toString()));
                f.setItemPriceMoney(obj[1].toString());
            }
            f.setStockModelId(lstStockModel.get(0).getStockModelId());
            f.setStockModelCode(lstStockModel.get(0).getStockModelCode());
            f.setStockModelName(lstStockModel.get(0).getName());
            f.setTelecomServiceId(lstStockModel.get(0).getTelecomServiceId());
            f.setTelecomServiceName(lstStockModel.get(0).getTelecomServiceName());
            return f;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String sumAmount() throws Exception {
        log.debug("# Begin method sumAmount");
        HttpServletRequest req = getRequest();
        SaleToAccountAnyPayForm f = this.getSaleToAnyPayForm();
        String pageForward = SALE_TO_ANYPAY;

        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            req.setAttribute("returnMsg", "Error! Timeout session!");
            return pageForward;
        }

        //reset
        saleToAnyPayForm.setTax(0.0);
        saleToAnyPayForm.setAmountNotTax(0.0);
        saleToAnyPayForm.setAmountTax(0.0);
        saleToAnyPayForm.setDiscount(0.0);
        saleToAnyPayForm.setVat(null);
        saleToAnyPayForm.setItemAmount(0.0);
        saleToAnyPayForm.setItemDiscountId(null);
        f.setTaxMoney("");
        f.setAmountNotTaxMoney("");
        f.setAmountTaxMoney("");
        f.setDiscountMoney("");

        //Kiem tra so luong hang trong kho cua hang co du khong?
        String shopType = "2";//////////////////////////////////////////////////Tai sao chi co doi tuong CTV, ko co doi tuong dai ly
        f.setReceiverType(Long.parseLong(shopType));
        boolean check = false;
        if (f.getReceiverType() != null && String.valueOf(f.getReceiverType()).equals(Constant.OBJECT_TYPE_STAFF)) {
            check = StockCommonDAO.checkStockGoods(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, f.getStockModelId(), Constant.STATE_NEW, f.getItemQuantity(), Constant.UN_CHECK_DIAL, this.getSession());
        } else if (f.getReceiverType() != null && String.valueOf(f.getReceiverType()).equals(Constant.OBJECT_TYPE_SHOP)) {
            check = StockCommonDAO.checkStockGoods(userToken.getShopId(), Constant.OWNER_TYPE_SHOP, f.getStockModelId(), Constant.STATE_NEW, f.getItemQuantity(), Constant.UN_CHECK_DIAL, this.getSession());
        } else {
            req.setAttribute("returnMsg", "ERR.SIK.066");
            return pageForward;
        }
        if (check == false) {
            req.setAttribute("returnMsg", "saleColl.warn.saleQuantity");
            return pageForward;
        }

        //check exist
        String discountPolicy = "";
        if (String.valueOf(f.getReceiverType()).equals(Constant.OBJECT_TYPE_SHOP)) {
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(f.getReceiverCode().trim());
            if (shop == null) {
                req.setAttribute("returnMsg", "Error! Shop code is not exist!");
                return pageForward;
            } else {
                f.setReceiverId(shop.getShopId());
                f.setReceiverName(shop.getName());

                discountPolicy = shop.getDiscountPolicy();
            }
        } else {
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = staffDAO.findAvailableByStaffCode(f.getReceiverCode().trim());
            if (staff == null) {
                req.setAttribute("returnMsg", "Error! Staff code is not exist!");
                return pageForward;
            } else {
                f.setReceiverId(staff.getStaffId());
                f.setReceiverName(staff.getName());

                discountPolicy = staff.getDiscountPolicy();
            }
        }

        //discountGroupId
        SaleCommonDAO saleCommonDAO = new SaleCommonDAO();
        saleCommonDAO.setSession(this.getSession());
        Long discountGroupId = saleCommonDAO.getDiscountGroupId(f.getStockModelId(), discountPolicy);

        List<SaleTransDetailV2Bean> lstGoods = new ArrayList();
        SaleTransDetailV2Bean bean = new SaleTransDetailV2Bean();
        bean.setStockModelId(f.getStockModelId());
        bean.setQuantity(f.getItemQuantity());

        //Price
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        Price price = priceDAO.findById(f.getItemPriceId());
        if (price == null) {
            req.setAttribute("returnMsg", "Error! Price of eCard is not exist!");
            return pageForward;
        }

        bean.setPriceId(price.getPriceId());
        bean.setPrice(price.getPrice());
        bean.setVat(price.getVat());
        bean.setCurrency(price.getCurrency());
        bean.setPriceDisplay(NumberUtils.rounđAndFormatAmount(price.getPrice()));

        if (Constant.PRICE_AFTER_VAT) {//Neu la gia sau thue (VIETNAM)
            bean.setAmountAfterTax(
                    f.getItemQuantity() * price.getPrice());//For update to sale_trans_detail
            bean.setAmountBeforeTax(bean.getAmountAfterTax() / (1.0 + price.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT));
            bean.setAmountTax(bean.getAmountAfterTax() - bean.getAmountBeforeTax());
        } else {//Neu la gia truoc thue (HAITI)
            bean.setAmountBeforeTax(
                    f.getItemQuantity() * price.getPrice());//For update to sale_trans_detail
            bean.setAmountTax(bean.getAmountBeforeTax() * price.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT);
            bean.setAmountAfterTax(bean.getAmountTax() + bean.getAmountBeforeTax());
        }
        bean.setAmountAfterTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountAfterTax()));//For display to interface
        bean.setAmountTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountTax()));//For display to interface
        bean.setAmountBeforeTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountBeforeTax()));//For display to interface

        bean.setDiscountGroupId(discountGroupId);
        lstGoods.add(bean);
        SaleTransForm saleTransForm = new SaleTransForm();
        Map<String, Map<Long, Double>> map = saleCommonDAO.sumDiscount(req, lstGoods, saleTransForm);

        f.setAmountNotTaxMoney((saleTransForm.getAmountBeforeTaxDisplay()));
        f.setDiscountMoney((saleTransForm.getAmountDiscountDisplay()));
        f.setTaxMoney((saleTransForm.getAmountTaxDisplay()));
        f.setAmountTaxMoney((saleTransForm.getAmountAfterTaxDisplay()));

        saleToAnyPayForm.setTax(saleTransForm.getAmountTax());
        saleToAnyPayForm.setAmountNotTax(saleTransForm.getAmountBeforeTax());
        saleToAnyPayForm.setAmountTax(saleTransForm.getAmountAfterTax());
        saleToAnyPayForm.setDiscount(saleTransForm.getAmountDiscount());
        saleToAnyPayForm.setVat(price.getVat());
        saleToAnyPayForm.setItemAmount(saleTransForm.getAmountAfterTax());
        saleToAnyPayForm.setItemDiscountId(lstGoods.get(0).getDiscountId());

        log.debug("# End method sumAmount");
        pageForward = SALE_TO_ANYPAY;
        return pageForward;
    }

    public String saleToAccountAnyPay() throws Exception {
        log.debug("# Begin method saleToAccountAnyPay");
        HttpServletRequest req = getRequest();
        String pageForward = SALE_TO_ANYPAY;


        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //tamdt1, merge code, 17/02/2011, start
        ViettelService request = new ViettelService();
        AnypaySession anypaySession = new AnypaySession(getSession("anypay"));
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
        AnypayMsg anyPayMsg = null;
        //tamdt1, merge code, 17/02/2011, end

        /**
         * THANHNC_20110215_START log.
         */
        String function = "com.viettel.im.database.DAO.SaleAnyPayToAgentOrStaffDAO.saleToAccountAnyPay";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartUser(startDate, function, callId, userToken.getLoginName());
        /**
         * End log
         */
        try {

            if (userToken == null) {
                return pageForward;
            }
            if (saleToAnyPayForm.getReceiverCode() == null || saleToAnyPayForm.getReceiverCode().equals("")) {

                //rollback
                anypaySession.rollbackAnypayTransaction();

//                req.setAttribute("returnMsg", "Bạn chưa chọn mã người mua");
                req.setAttribute("returnMsg", "ERR.SIK.068");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "ERR.SIK.068");
                return pageForward;
            }

            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = staffDAO.findAvailableByStaffCode(saleToAnyPayForm.getReceiverCode().trim());
            if (staff == null) {
//                //rollback
                anypaySession.rollbackAnypayTransaction();

//                req.setAttribute("returnMsg", "Mã người mua không chính xác");
                req.setAttribute("returnMsg", "ERR.SIK.069");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "ERR.SIK.069");
                return pageForward;
            } else {
                if (!staff.getShopId().equals(userToken.getShopId())) {
//                   //rollback
                    anypaySession.rollbackAnypayTransaction();

//                    req.setAttribute("returnMsg", "Mã người mua không thuộc cửa hàng");
                    req.setAttribute("returnMsg", "ERR.SIK.070");
                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "ERR.SIK.070");
                    return pageForward;
                } else {
                    if (!staff.getStaffOwnerId().equals(userToken.getUserID())) {
//                      //rollback
                        anypaySession.rollbackAnypayTransaction();

//                        req.setAttribute("returnMsg", "Mã người mua không thuộc nhân viên quản lý đang đăng nhập");
                        req.setAttribute("returnMsg", "ERR.SIK.071");
                        logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "ERR.SIK.071");
                        return pageForward;
                    }
                }
            }
            sumAmount();

            if (saleToAnyPayForm.getAmountTax() == null || saleToAnyPayForm.getAmountTax().equals(0.0)) {
                req.setAttribute("returnMsg", "Error! Amount is must be greate than 0!");

                //rollback
                anypaySession.rollbackAnypayTransaction();

                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Tax is null");
                //

                return pageForward;
            }

            //Kiem tra so luong hang trong kho cua hang co du khong?
            boolean check = false;
            if (saleToAnyPayForm.getReceiverType() != null && String.valueOf(saleToAnyPayForm.getReceiverType()).equals(Constant.OBJECT_TYPE_STAFF)) {
                check = StockCommonDAO.checkStockGoods(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, saleToAnyPayForm.getStockModelId(), Constant.STATE_NEW, saleToAnyPayForm.getItemQuantity(), Constant.UN_CHECK_DIAL, this.getSession());
            } else if (saleToAnyPayForm.getReceiverType() != null && String.valueOf(saleToAnyPayForm.getReceiverType()).equals(Constant.OBJECT_TYPE_SHOP)) {
                check = StockCommonDAO.checkStockGoods(userToken.getShopId(), Constant.OWNER_TYPE_SHOP, saleToAnyPayForm.getStockModelId(), Constant.STATE_NEW, saleToAnyPayForm.getItemQuantity(), Constant.UN_CHECK_DIAL, this.getSession());
            }
            if (check == false) {

                //rollback
                anypaySession.rollbackAnypayTransaction();

                req.setAttribute("returnMsg", "saleColl.warn.saleQuantity");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "saleColl.warn.saleQuantity");

                return pageForward;
            }

            //Lay accountId ben FPT -- tam thoi bao channelType
//        String sql = "select STOCK_ID from stock_owner_tmp where 1 = 1 and owner_id = ? and owner_type = ? and channel_type_id = ?";
            String sql = "select STOCK_ID from stock_owner_tmp where 1 = 1 and owner_id = ? and owner_type = ?";
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, saleToAnyPayForm.getReceiverId());
            query.setParameter(1, saleToAnyPayForm.getReceiverType().toString());
            //query.setParameter(2, saleToAnyPayForm.getChannelTypeId());
            List list = query.list();
            Iterator iter = list.iterator();
            Long agentId = 0L;
            while (iter.hasNext()) {
                Object temp = (Object) iter.next();
                agentId = new Long(temp.toString());
            }
//            String[] arrMess = new String[3];

            if (agentId.equals(0L)) {
//                //rollback
                anypaySession.rollbackAnypayTransaction();

//                req.setAttribute("returnMsg", "!!! Không tồn tại account bên FPT");
                req.setAttribute("returnMsg", "ERR.SIK.072");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "agentId is null");
                return pageForward;
            } else {
                if (!checkStatusAccount(agentId)) {
//                   //rollback
                    anypaySession.rollbackAnypayTransaction();

//                    req.setAttribute("returnMsg", "!!! Account này không ở trạng thái hoạt động không thể bán TCĐT");
                    req.setAttribute("returnMsg", "ERR.SIK.073");
                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "ERR.SIK.073");
                    return pageForward;
                }
                try {
                    anyPayMsg = anyPayLogic.saleAnypay(getLanguage(), 123L, saleToAnyPayForm.getStockModelId(), agentId.toString(), saleToAnyPayForm.getItemQuantity(), getSysdate(), userToken.getLoginName(), req.getRemoteAddr());
                    if (anyPayMsg != null && (anyPayMsg.getTransAnypayId() == null || (anyPayMsg.getErrMsg() != null && !"".equals(anyPayMsg.getErrMsg()))
                            || (anyPayMsg.getErrCode() != null && !"".equals(anyPayMsg.getErrCode())))) {
                        //rollback
                        anypaySession.rollbackAnypayTransaction();

                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();

//                        req.setAttribute("returnMsg",anyPayMsg.getErrMsg()); //old
                        req.setAttribute("returnMsg", "ANYPAY : " + getText(anyPayMsg.getErrCode()));

                        logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "ERR.SIK.073");
                        return pageForward;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                    //rollback
                    anypaySession.rollbackAnypayTransaction();

                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();

//                    req.setAttribute("returnMsg", "!!! Lỗi kết nối với hệ thống FPT");
                    req.setAttribute("returnMsg", "ERR.SIK.074");
                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Exception");
                    return pageForward;
                }
            }

            //Thuc hien tru kho cua kho cua hang
            if (!updateStockTotal(saleToAnyPayForm)) {
//               //rollback
                anypaySession.rollbackAnypayTransaction();

                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();

//                req.setAttribute("returnMsg", "!!! Lỗi trừ kho");
                req.setAttribute("returnMsg", "ERR.SIK.075");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "ERR.SIK.075");
                return pageForward;
            }

            //Tao giao dich ban hang cho CTV
            SaleTrans saleTrans = expSaleTrans(anyPayMsg.getTransAnypayId().toString());
            if (saleTrans == null) {
                //rollback
                anypaySession.rollbackAnypayTransaction();

                //
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();

                //                req.setAttribute("returnMsg", "!!! Lỗi tạo giao dịch");
                req.setAttribute("returnMsg", "ERR.SIK.076");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "ERR.SIK.075");
                return pageForward;
            }
            SaleTransDetail saleTransDetail = expSaleTransDetail(saleTrans, saleToAnyPayForm);
            if (saleTransDetail == null) {
//rollback
                anypaySession.rollbackAnypayTransaction();

                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();

                //                req.setAttribute("returnMsg", "!!! Lỗi tạo chi tiết giao dịch");
                req.setAttribute("returnMsg", "ERR.SIK.077");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "ERR.SIK.077");
                return pageForward;
            }

            //tinh tong gia tri hang hoa
            /**
             * Modified by TrongLV Modify date 2011-09-22 Purpose : Chuyen check
             * han muc len vi tri truoc khi commit DB Sales
             */
            SaleTransDetailV2Bean saleTransDetailV2Bean = new SaleTransDetailV2Bean();
            saleTransDetailV2Bean.setStockModelId(saleTransDetail.getStockModelId());
            saleTransDetailV2Bean.setQuantity(saleTransDetail.getQuantity());
            List<SaleTransDetailV2Bean> listSaleTransDetailV2Bean = new ArrayList();
            listSaleTransDetailV2Bean.add(saleTransDetailV2Bean);

//            Double saleTransAmount = sumAmountBySaleTransId(saleTrans.getSaleTransId());
            Double saleTransAmount = sumAmountByGoodsList(listSaleTransDetailV2Bean);

            if (saleTransAmount != null && saleTransAmount >= 0) {
                Long ownerId = saleTrans.getStaffId() == null ? saleTrans.getShopId() : saleTrans.getStaffId();
                Long ownerType = saleTrans.getStaffId() == null ? Constant.OWNER_TYPE_SHOP : Constant.OWNER_TYPE_STAFF;
                //Cap nhat lai gia tri hang hoa
                if (!addCurrentDebit(ownerId, ownerType, saleTransAmount)) {
                    req.setAttribute("returnMsg", "ERR.LIMIT.0001");
                    anypaySession.rollbackAnypayTransaction();
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().getTransaction().begin();
                    return pageForward;
                }
            } else {
                req.setAttribute("returnMsg", "ERR.LIMIT.0014");
                anypaySession.rollbackAnypayTransaction();
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().getTransaction().begin();
                return pageForward;
            }


            saleToAnyPayForm.setSaleTransId(saleTrans.getSaleTransId());
            log.debug("# End method saleToAccountAnyPay");
//            req.setAttribute("returnMsg", "!!! Thực hiện giao dịch bán TCĐT thành công");
            req.setAttribute("returnMsg", "ERR.SIK.078");
            preparePage();

            getSession().flush();
            getSession().getTransaction().commit();
            getSession().beginTransaction();

            SaleTransDAO saleTransDAO = new SaleTransDAO();
            saleTransDAO.setSession(getSession());
            SaleTrans saleTransFind = saleTransDAO.findById(saleTrans.getSaleTransId());
            if (saleTransFind == null) {
//               rollback
                anypaySession.rollbackAnypayTransaction();

                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();

//                req.setAttribute("returnMsg", "!!! Lỗi lưu dữ liệu vào db");
                req.setAttribute("returnMsg", "ERR.SIK.079");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "ERR.SIK.079");
                return pageForward;
            }

            //commit
            anypaySession.commitAnypayTransaction();

            saleToAnyPayForm.setSaleTransId(saleTrans.getSaleTransId());

            req.setAttribute("returnMsg", "ERR.SIK.078");
            preparePage();
            pageForward = SALE_TO_ANYPAY;

        } catch (Exception ex) {
            ex.printStackTrace();

            //rollback
            anypaySession.rollbackAnypayTransaction();

            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();

            throw ex;
        }
        return pageForward;
    }

    public boolean updateStockTotal(SaleToAccountAnyPayForm form) throws Exception {
        log.debug("# Begin method expStock");
        boolean result = false;
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            if (form.getReceiverType() != null && String.valueOf(form.getReceiverType()).equals(Constant.OBJECT_TYPE_STAFF)) {
                ///trung dh 3 R499 thay doi
                // noError = stockCommonDAO.expStockTotal(this.getSession(), Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATE_NEW, form.getStockModelId(), form.getItemQuantity(), true);
                //R499 trung dh3 add
                GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), userToken.getUserID(), Constant.OWNER_TYPE_STAFF, form.getStockModelId(),
                        Constant.STATE_NEW, -form.getItemQuantity(), -form.getItemQuantity(), null,
                        userToken.getUserID(), form.getReasonId(), form.getSaleTransId(), "", Constant.TRANS_RECOVER.toString(), StockTotalAuditDAO.SourceType.SALE_TRANS);
                result = genResult.isSuccess();

                //R499 trung dh3 add end
                if (!result) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                }
            } else if (form.getReceiverType() != null && String.valueOf(form.getReceiverType()).equals(Constant.OBJECT_TYPE_SHOP)) {
                //noError = stockCommonDAO.expStockTotal(this.getSession(), Constant.OWNER_TYPE_SHOP, userToken.getShopId(), Constant.STATE_NEW, form.getStockModelId(), form.getItemQuantity(), true);
                GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), userToken.getShopId(), Constant.OWNER_TYPE_SHOP, form.getStockModelId(),
                        Constant.STATE_NEW, -form.getItemQuantity(), -form.getItemQuantity(), null,
                        userToken.getUserID(), form.getReasonId(), form.getSaleTransId(), CommonDAO.formatTransCode(form.getSaleTransId()), Constant.TRANS_RECOVER.toString(), StockTotalAuditDAO.SourceType.SALE_TRANS);
                result = genResult.isSuccess();
                //trung dh 3 R499 end
                if (!result) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                }
            } else {
                return result;
            }
        } catch (Exception ex) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            ex.printStackTrace();
            return result;
        }
//        result = !result;
        return result;
    }

    public SaleTrans expSaleTrans(String iTransId) {
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

            SaleTrans saleTrans = new SaleTrans();
            saleTrans.setSaleTransId(getSequence("SALE_TRANS_SEQ"));
            saleTrans.setSaleTransCode(CommonDAO.formatTransCode(saleTrans.getSaleTransId()));
            //saleTrans.setReasonId(saleToAnyPayForm.getReasonId());
            saleTrans.setSaleTransDate(getSysdate());
            saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_COLLABORATOR);
            saleTrans.setStatus(Constant.SALE_TRANS_STATUS_NOT_BILLED);
            saleTrans.setShopId(userToken.getShopId());
            saleTrans.setStaffId(userToken.getUserID());
            saleTrans.setTax(saleToAnyPayForm.getTax());//Thue VAT
            saleTrans.setAmountNotTax(saleToAnyPayForm.getAmountNotTax());//Tien chua thue
            saleTrans.setDiscount(saleToAnyPayForm.getDiscount());//Tien chiet khau
            saleTrans.setAmountTax(saleToAnyPayForm.getAmountTax());//Tong tien phai tra
            saleTrans.setPayMethod(Constant.PAY_METHOD_MONNEY);//Hinh thuc thanh toan
            saleTrans.setVat(saleToAnyPayForm.getVat());
            saleTrans.setTelecomServiceId(Constant.TELECOM_SERVICE_ANYPAY);

            saleTrans.setCurrency(Constant.CURRENCY_DEFAULT);//Currency

            //saleTrans.setTelecomServiceId(saleToAnyPayForm.getTelecomServiceId());

            saleTrans.setAmountModel(saleToAnyPayForm.getItemAmount());

            saleTrans.setReceiverId(saleToAnyPayForm.getReceiverId());
            saleTrans.setReceiverType(saleToAnyPayForm.getReceiverType());
            saleTrans.setCustName(saleToAnyPayForm.getReceiverCode().trim() + " - " + saleToAnyPayForm.getReceiverName().trim());
            saleTrans.setReasonId(saleToAnyPayForm.getReasonId());
            saleTrans.setInTransId(iTransId);

            this.getSession().save(saleTrans);
            return saleTrans;

        } catch (Exception ex) {
            this.getSession().clear();
            ex.printStackTrace();
            return null;
        }
    }

    public SaleTransDetail expSaleTransDetail(SaleTrans saleTrans, SaleToAccountAnyPayForm SaleTransForm) {
        try {
            SaleTransDetail saleTransDetail = new SaleTransDetail();

            saleTransDetail.setSaleTransDetailId(getSequence("SALE_TRANS_DETAIL_SEQ"));
            saleTransDetail.setSaleTransId(saleTrans.getSaleTransId());
            saleTransDetail.setSaleTransDate(saleTrans.getSaleTransDate());

            saleTransDetail.setStockModelId(SaleTransForm.getStockModelId());

            saleTransDetail.setDiscountAmount(0.0);

            saleTransDetail.setPriceId(SaleTransForm.getItemPriceId());
            saleTransDetail.setQuantity(SaleTransForm.getItemQuantity());
            if (saleTrans.getDiscount() != null) {
                saleTransDetail.setDiscountAmount(saleTrans.getDiscount());
            }
            saleTransDetail.setDiscountId(SaleTransForm.getItemDiscountId());//cai nay phai them vao

            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(SaleTransForm.getStockModelId());
            if (stockModel != null) {
                saleTransDetail.setStockModelCode(stockModel.getStockModelCode());
                saleTransDetail.setStockModelName(stockModel.getName());
            } else {
                //khong tim thay mat hang
                return null;
            }
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(this.getSession());
            Price price = priceDAO.findById(SaleTransForm.getItemPriceId());

            if (price != null) {
                if (!Constant.PRICE_AFTER_VAT) {
                    saleTransDetail.setPrice(price.getPrice());
                } else {
                    saleTransDetail.setPrice(price.getPrice() / (1 + price.getVat() / 100));
                }
                saleTransDetail.setPriceVat(price.getVat());
                saleTransDetail.setCurrency(price.getCurrency());
            } else {
                //khong tim thay gia
                return null;
            }
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(stockModel.getStockTypeId());
            if (stockType != null) {
                saleTransDetail.setStockTypeId(stockType.getStockTypeId());
                saleTransDetail.setStockTypeName(stockType.getName());
            } else {
                //khong tim thay loai mat hang
                return null;
            }

            saleTransDetail.setAmount(saleTransDetail.getPrice() * saleTransDetail.getQuantity());

            saleTransDetail.setAmountBeforeTax(saleTransDetail.getAmount() - saleTransDetail.getDiscountAmount());
            saleTransDetail.setAmountTax(saleTransDetail.getAmountBeforeTax() * (price.getVat() / 100));//tien thue
            saleTransDetail.setVatAmount(saleTransDetail.getAmountBeforeTax() * (price.getVat() / 100));//tien thue
            saleTransDetail.setAmountAfterTax(saleTransDetail.getAmountBeforeTax() + saleTransDetail.getAmountTax());

            saleTransDetail.setStateId(Constant.STATE_NEW);
            saleTransDetail.setNote("AnyPay");

            this.getSession().save(saleTransDetail);
            return saleTransDetail;
        } catch (Exception ex) {
            this.getSession().clear();
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose :
     *
     */
    public List<ImSearchBean> getListShopOrStaff(ImSearchBean imSearchBean) {
        req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(getSession());
        String ShopType = "2";
        String ShopCode = imSearchBean.getCode().trim();
        if (ShopCode != null && ShopType != null) {
            if ("".equals(ShopType)) {
                return listImSearchBean;
            }
            List listParameter1 = new ArrayList();
            String queryString = ((ShopType.equals("2")) ? "select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) " : "select new com.viettel.im.client.bean.ImSearchBean(b.shopCode, b.name)");
            queryString += " from " + ((ShopType.equals("2")) ? " Staff a where a.status = ? " : " Shop b where b.status = ? ");
            listParameter1.add(Constant.STATUS_USE);
            queryString += " and " + ((ShopType.equals("2")) ? " lower(a.staffCode) " : " lower(b.shopCode) ") + " like ? ";
            listParameter1.add(ShopCode.toLowerCase() + "%");
            if (!ShopType.equals("1")) {
                queryString += " and a.staffOwnerId = ?";
                listParameter1.add(userToken.getUserID());
                if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                    queryString += " and lower(a.name) like ? ";
                    listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                }
            } else {
                queryString += " and (b.parentShopId = ?) ";
                listParameter1.add(userToken.getShopId());
                if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                    queryString += " and lower(b.name) like ? ";
                    listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                }
            }
            queryString += " and rownum <= ? ";
            listParameter1.add(100L);
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < listParameter1.size(); i++) {
                queryObject.setParameter(i, listParameter1.get(i));
            }
            listImSearchBean = queryObject.list();
            return listImSearchBean;
        }
        return listImSearchBean;
    }

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose :
     *
     */
    public List<ImSearchBean> getNameShopOrStaff(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(getSession());
        String ShopType = "2";
        String ShopCode = imSearchBean.getCode().trim();
        if (ShopCode != null && ShopType != null) {
            if ("".equals(ShopType)) {
                return listImSearchBean;
            }
            List listParameter1 = new ArrayList();
            String queryString = ((ShopType.equals("2")) ? "select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) " : "select new com.viettel.im.client.bean.ImSearchBean(b.shopCode, b.name)");
            queryString += " from " + ((ShopType.equals("2")) ? " Staff a where a.status = ? " : " Shop b where b.status = ? ");
            listParameter1.add(Constant.STATUS_USE);
            queryString += " and " + ((ShopType.equals("2")) ? " lower(a.staffCode) " : " lower(b.shopCode) ") + " like ? ";
            listParameter1.add(ShopCode.toLowerCase());
            if (!ShopType.equals("1")) {
                queryString += " and a.staffOwnerId = ?";
                listParameter1.add(userToken.getUserID());
                if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                    queryString += " and lower(a.name) = ?  ";
                    listParameter1.add(imSearchBean.getName().trim().toLowerCase());
                }
            } else {
                queryString += " and (b.parentShopId = ?) ";
                listParameter1.add(userToken.getShopId());
                if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                    queryString += " and lower(b.name) = ? ";
                    listParameter1.add(imSearchBean.getName().trim().toLowerCase());
                }
            }
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < listParameter1.size(); i++) {
                queryObject.setParameter(i, listParameter1.get(i));
            }
            listImSearchBean = queryObject.list();
            return listImSearchBean;
        }
        return listImSearchBean;
    }

    public Long getListShopOrStaffSize(ImSearchBean imSearchBean) {
        req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(getSession());
        String ShopType = "2";
        String ShopCode = imSearchBean.getCode().trim();
        if (ShopCode != null && ShopType != null) {
            if ("".equals(ShopType)) {
                return 0L;
            }
            List listParameter1 = new ArrayList();
            String queryString = ((ShopType.equals("2")) ? "select count(*) " : "select count(*) ");
            queryString += " from " + ((ShopType.equals("2")) ? " Staff a where a.status = ? " : " Shop b where b.status = ? ");
            listParameter1.add(Constant.STATUS_USE);
            queryString += " and " + ((ShopType.equals("2")) ? " lower(a.staffCode) " : " lower(b.shopCode) ") + " like ? ";
            listParameter1.add(ShopCode.toLowerCase() + "%");
            if (!ShopType.equals("1")) {
                queryString += " and a.staffOwnerId = ?";
                listParameter1.add(userToken.getUserID());
                if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                    queryString += " and lower(a.name) like ? ";
                    listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                }
            } else {
                queryString += " and (b.parentShopId = ?) ";
                listParameter1.add(userToken.getShopId());
                if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                    queryString += " and lower(b.name) like ? ";
                    listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                }
            }
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < listParameter1.size(); i++) {
                queryObject.setParameter(i, listParameter1.get(i));
            }
            List<Long> tmpList = queryObject.list();
            if (tmpList != null && tmpList.size() > 0) {
                return tmpList.get(0);
            } else {
                return 0L;
            }
        }
        return 0L;
    }

    //Quan ly giao dich nap tien chuyen tien
    //Vunt
    public String prepareAnyPayTransManagement() throws Exception {

        log.debug("# Begin method prepareAnyPayTransManagement");
        HttpServletRequest req = getRequest();
        String pageForward = SALE_ANYPAY_TRANS_MANAGEMENT;

        anyPayTransForm = new AnyPayTransManagementForm();
        req.getSession().setAttribute(LIST_REASON_DESTROY, null);

        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            return pageForward;
        }

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(getSession());
        List reasonList = reasonDAO.findByPropertyWithStatus(reasonDAO.REASON_TYPE, Constant.REASON_DESTROY_ANYPAY_TRANS, Constant.STATUS_USE.toString());
        req.getSession().setAttribute(LIST_REASON_DESTROY, reasonList);

        anyPayTransForm.setPShopCodeS(userToken.getShopCode());
        anyPayTransForm.setPShopNameS(userToken.getShopName());
        anyPayTransForm.setPStaffCodeS(userToken.getLoginName());
        anyPayTransForm.setPStaffNameS(userToken.getFullName());
        anyPayTransForm.setPFromDateS(DateTimeUtils.getSysdate());
        anyPayTransForm.setPToDateS(DateTimeUtils.getSysdate());

        anyPayTransForm.setPTransTypeS(String.valueOf(Constant.ANYPAY_TRANS_TYPE_CUSTOMER));
        anyPayTransForm.setPStatusS(String.valueOf(Constant.STATUS_USE));

        log.debug("# End method prepareAnyPayTransManagement");
        pageForward = SALE_ANYPAY_TRANS_MANAGEMENT;

        return pageForward;
    }

    public String searchAnyPayTrans() throws Exception {
        log.debug("# Begin method searchAnyPayTrans");
        HttpServletRequest req = getRequest();
        String pageForward = SALE_ANYPAY_TRANS_MANAGEMENT;
        /**
         * THANHNC_20110215_START log.
         */
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String function = "com.viettel.im.database.DAO.SaleAnyPayToAgentOrStaffDAO.searchAnyPayTrans";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartUser(startDate, function, callId, userToken.getLoginName());
        /**
         * End log
         */
        //tamdt1, merge code, 17/02/2011, start
        ViettelService request = new ViettelService();
        AnypaySession anypaySession = new AnypaySession(getSession("anypay"));
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
        AnypayMsg anyPayMsg = null;
        //tamdt1, merge code, 17/02/2011, end

        if (!this.validateSearchAnyPayTransInfo(anyPayTransForm)) {
            //rollback
            anypaySession.rollbackAnypayTransaction();

            return pageForward;
        }

        String transId = anyPayTransForm.getPTransIdS();


        List<AnyPayTransFPTBean> lstAnyPayTrans = new ArrayList<AnyPayTransFPTBean>();
        TransData transData;
        String strFromDate = anyPayTransForm.getPFromDateS();
        String strToDate = anyPayTransForm.getPToDateS();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }
        Date toDate = new Date();
        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
        }
        Long statusAnyPay = null;
        String transType = null;
        if (anyPayTransForm.getPTransTypeS() != null && !"".equals(anyPayTransForm.getPTransTypeS())) {
            transType = anyPayTransForm.getPTransTypeS();
        }
        if (anyPayTransForm.getPStatusS() != null && !"".equals(anyPayTransForm.getPStatusS())) {
            statusAnyPay = Long.parseLong(anyPayTransForm.getPStatusS());
        }
        Date startFind = new Date();
        if (transId.equals("")) {
            logStartCall(startFind, "FindTransUtil.findByTransId", callId, "fromDate=", fromDate, "toDate=", toDate, "transType", transType, "statusAnyPay", statusAnyPay);
            transData = FindTransUtil.findByTransId(anypaySession, null, fromDate, toDate, transType, statusAnyPay);
            logEndCall(startFind, new Date(), "FindTransUtil.findByTransId", callId);
        } else {
            logStartCall(startFind, "FindTransUtil.findByTransId", callId, "fromDate=", fromDate, "toDate=", toDate, "transType", transType, "statusAnyPay", statusAnyPay);
            transData = FindTransUtil.findByTransId(anypaySession, Long.parseLong(transId.trim()), fromDate, toDate, transType, statusAnyPay);
            logEndCall(startFind, new Date(), "FindTransUtil.findByTransId", callId);
        }

        if (transData != null) {
            AnyPayTransFPTBean anyPayTransFPTBean = new AnyPayTransFPTBean();

            //Modified by : TrongLV
            //Modify date : 2011-09-19
            //Chia so tien cho 10,000 va 1.1
            if (transData.getTransAmount() != null) {
                anyPayTransFPTBean.setAmount(transData.getTransAmount().doubleValue());
            }

            anyPayTransFPTBean.setCreateDate(transData.getCreateDate());
            anyPayTransFPTBean.setLastModify(transData.getLastModified());
            anyPayTransFPTBean.setProcessStatus(String.valueOf(transData.getProcessStatus()));
            anyPayTransFPTBean.setSourceAgentId(transData.getSourceAgentId());
            anyPayTransFPTBean.setSourceIsdn(transData.getSourceMsisdn());
            anyPayTransFPTBean.setTargetIsdn(transData.getTargetMsisdn());
            anyPayTransFPTBean.setTransId(transData.getTransId());
            anyPayTransFPTBean.setTransType(transData.getTransType());
            anyPayTransFPTBean.setLoadDetail(transData.getLoadDetail());
            lstAnyPayTrans.add(anyPayTransFPTBean);
        }

        anypaySession.rollbackAnypayTransaction(); //ngu kinh khung (vi lib luon bat rollback hoac commit)

        //List lstAnyPayTrans = this.getAnyPayTransList(anyPayTransForm);
        req.setAttribute(LIST_ANYPAY_TRANS, lstAnyPayTrans);
        if (lstAnyPayTrans != null) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "TÃ¬m tháº¥y " + lstAnyPayTrans.size() + " báº£n ghi!");
            req.setAttribute(Constant.RETURN_MESSAGE, "M.100003");
            List listParamValue = new ArrayList();
            listParamValue.add(lstAnyPayTrans.size());
            req.setAttribute(Constant.RETURN_MESSAGE_VALUE, listParamValue);

        }

        log.debug("# End method searchAnyPayTrans");
        pageForward = SALE_ANYPAY_TRANS_MANAGEMENT;
        logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "OK");
        return pageForward;
    }

    public String searchAnyPayTrans_old() throws Exception {
        log.debug("# Begin method searchAnyPayTrans");
        HttpServletRequest req = getRequest();
        String pageForward = SALE_ANYPAY_TRANS_MANAGEMENT;

        if (anyPayTransForm == null) {
            return pageForward;
        }

        if (!this.validateSearchAnyPayTransInfo(anyPayTransForm)) {
            return pageForward;
        }
        SaleToAccountAnyPayFPTDAO saleToAccountAnyPayFPTDAO = new SaleToAccountAnyPayFPTDAO();
        String transId = anyPayTransForm.getPTransIdS();
        List<AnyPayTransFPTBean> lstAnyPayTrans;
        if (transId.equals("")) {
            lstAnyPayTrans = saleToAccountAnyPayFPTDAO.getTransfer(getSession(), null, anyPayTransForm.getPFromDateS().substring(0, 10), anyPayTransForm.getPToDateS().substring(0, 10), anyPayTransForm.getPTransTypeS(), anyPayTransForm.getPStatusS());
        } else {
            lstAnyPayTrans = saleToAccountAnyPayFPTDAO.getTransfer(getSession(), Long.parseLong(transId.trim()), anyPayTransForm.getPFromDateS().substring(0, 10), anyPayTransForm.getPToDateS().substring(0, 10), anyPayTransForm.getPTransTypeS(), anyPayTransForm.getPStatusS());
        }
        //List lstAnyPayTrans = this.getAnyPayTransList(anyPayTransForm);
        req.setAttribute(LIST_ANYPAY_TRANS, lstAnyPayTrans);
        if (lstAnyPayTrans != null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Found " + lstAnyPayTrans.size() + " record!");
        }

        log.debug("# End method searchAnyPayTrans");
        pageForward = SALE_ANYPAY_TRANS_MANAGEMENT;

        return pageForward;
    }

    private List getAnyPayTransList(AnyPayTransManagementForm f) {
        try {
            List lstParam = new ArrayList();
            String queryString = "";
            queryString += "SELECT apt.trans_id AS transId ";
            queryString += "    ,DECODE(apt.trans_type,1, 'RELOAD',2,'CASHTRANSFER',3,'RELOAD','') AS transTypeDes ";
            queryString += "    ,apt.owner_code AS ownerCode ";
            queryString += "    ,apt.isdn_sent AS isdnSent ";
            queryString += "    ,apt.isdn_receive AS isdnReceive ";
            queryString += "    ,apt.amount ";
            queryString += "    ,apt.trans_datetime AS transDateTime ";
            queryString += "    ,apt.status ";
            queryString += "    ,DECODE(apt.status,1,'Hiệu lực',0,'Đã huỷ','') AS statusDes ";
            queryString += "    ,apt.cdr_trans_id as cdrTransId ";
            queryString += "FROM anypay_trans apt ";
            queryString += "WHERE 1=1 and result = " + Constant.ACCOUNT_RESULT_SUCCESS;
            queryString += "    AND rownum <= ? ";
            lstParam.add(MAX_RESULT);

            if (f.getPTransIdS() != null && !f.getPTransIdS().trim().equals("")) {
                queryString += "    AND apt.trans_id = ? ";
                lstParam.add(Long.valueOf(f.getPTransIdS().trim()));
            } else {
                if (f.getPStatusS() != null && !f.getPStatusS().trim().equals("")) {
                    queryString += "    AND apt.status = ? ";
                    lstParam.add(Long.valueOf(f.getPStatusS().trim()));
                }

                if (f.getPTransTypeS() != null && !f.getPTransTypeS().trim().equals("")) {
                    queryString += "    AND apt.trans_type = ? ";
                    lstParam.add(Long.valueOf(f.getPTransTypeS().trim()));
                } else {
                    queryString += "    AND apt.trans_type != 1 ";
                }
                if (f.getPFromDateS() != null && !f.getPFromDateS().trim().equals("")) {
                    Date fromDate;
                    try {
                        fromDate = DateTimeUtils.convertStringToTime(f.getPFromDateS().trim().substring(0, 10) + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                        queryString += "    AND apt.trans_datetime >= ? ";
                        lstParam.add(fromDate);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return null;
                    }
                }
                if (f.getPToDateS() != null && !f.getPToDateS().trim().equals("")) {
                    Date toDate;
                    try {
                        toDate = DateTimeUtils.convertStringToTime(f.getPToDateS().trim().substring(0, 10) + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
                        queryString += "    AND apt.trans_datetime <= ? ";
                        lstParam.add(toDate);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return null;
                    }
                }
            }

            Query queryObject = getSession().createSQLQuery(queryString).
                    addScalar("transId", Hibernate.LONG).
                    addScalar("transTypeDes", Hibernate.STRING).
                    addScalar("ownerCode", Hibernate.STRING).
                    addScalar("isdnSent", Hibernate.STRING).
                    addScalar("isdnReceive", Hibernate.STRING).
                    addScalar("amount", Hibernate.LONG).
                    addScalar("transDateTime", Hibernate.TIMESTAMP).
                    addScalar("status", Hibernate.LONG).
                    addScalar("statusDes", Hibernate.STRING).
                    addScalar("cdrTransId", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(AnyPayTransBean.class));
            for (int idx = 0; idx < lstParam.size(); idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }

            return queryObject.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private boolean validateSearchAnyPayTransInfo(AnyPayTransManagementForm f) {
        HttpServletRequest req = getRequest();
        boolean result = false;
        try {
            try {
                if (f.getPTransIdS() != null && !f.getPTransIdS().trim().equals("")) {
                    Long tmp = Long.valueOf(f.getPTransIdS().trim());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
//                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi, mã giao dịch phải có giá trị kiểu số nguyên dương!");
                req.setAttribute("returnMsg", "ERR.SIK.081");
                return result;
            }

            try {
                Date fromDate = DateTimeUtils.convertStringToDate(f.getPFromDateS());
            } catch (Exception ex) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi, giá trị từ ngày không đúng định dạng!");
                req.setAttribute("returnMsg", "ERR.SIK.082");
                return result;
            }
            try {
                Date toDate = DateTimeUtils.convertStringToDate(f.getPToDateS());
            } catch (Exception ex) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi, giá trị đến ngày không đúng định dạng!");
                req.setAttribute("returnMsg", "ERR.SIK.082");
                return result;
            }

            //OK
            result = !result;
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return result;
        }
    }

    public String cancelAnyPayTrans() throws SQLException, Exception {
        String pageForward = SALE_ANYPAY_TRANS_MANAGEMENT;
        HttpServletRequest req = getRequest();
        String showMessage = "";

        /**
         * Modified by TrongLV Modify date: 2011/04/16
         */
        /**
         * THANHNC_20110215_START log.
         */
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String function = "com.viettel.im.database.DAO.SaleAnyPayToAgentOrStaffDAO.cancelAnyPayTrans";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartUser(startDate, function, callId, userToken.getLoginName());


        /**
         * End log
         */
        //tamdt1, merge code, 17/02/2011, start
        ViettelService request = new ViettelService();
        AnypaySession anypaySession = new AnypaySession(getSession("anypay"));
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
        AnypayMsg anyPayMsg = null;
        //tamdt1, merge code, 17/02/2011, end

        String strFromDate = anyPayTransForm.getPFromDateS();
        String strToDate = anyPayTransForm.getPToDateS();
        Date fromDate = new Date();
        Date toDate = new Date();

        try {
            if (strFromDate != null && !strFromDate.trim().equals("")) {
                fromDate = DateTimeUtils.convertStringToDate(strFromDate);
            }
            if (strToDate != null && !strToDate.equals("")) {
                toDate = DateTimeUtils.convertStringToDate(strToDate);
            }

            String[] selectedTransList = anyPayTransForm.getPTransIdList();

            if (selectedTransList == null || selectedTransList.length == 0) {
                //rollback
                anypaySession.rollbackAnypayTransaction();

//                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi, chưa chọn giao dịch nào!");
                req.setAttribute("returnMsg", "ERR.SIK.083");
                return pageForward;
            }

//            if (anyPayTransForm.getpReasonId() == null) {
//                //rollback
//                anypaySession.rollbackAnypayTransaction();
//                req.setAttribute("returnMsg", "Error. Pls select reason to cancel transaction!");
//                return pageForward;
//            }


            String cancel_user = userToken.getLoginName();
            Date cancel_date = DateTimeUtils.convertStringToDateTime(DateTimeUtils.getSysDateTime());

            for (int i = 0; i < selectedTransList.length; i++) {
                if (selectedTransList[i] == null) {
                    continue;
                }

                //TrongLV
                Date startFind = new Date();
                logStartCall(startFind, "FindTransUtil.findByTransId", callId, "fromDate=", fromDate, "toDate=", toDate, "selectedTransList", selectedTransList[i].toString());
                TransData transData = FindTransUtil.findByTransId(anypaySession, Long.parseLong(selectedTransList[i]), fromDate, toDate, null, null);
                logEndCall(startFind, new Date(), "FindTransUtil.findByTransId", callId);
                AnyPayTransFPTBean anyPayTransFPTBean = new AnyPayTransFPTBean();
                if (transData != null) {

                    //Modified by : TrongLV
                    //Modify date : 2011-09-19
                    //Chia so tien cho 10,000 va 1.1
                    if (transData.getTransAmount() != null) {
                        anyPayTransFPTBean.setAmount(transData.getTransAmount() / 11000.0);
                    }

                    anyPayTransFPTBean.setCreateDate(transData.getCreateDate());
                    anyPayTransFPTBean.setLastModify(transData.getLastModified());
                    anyPayTransFPTBean.setProcessStatus(String.valueOf(transData.getProcessStatus()));
                    anyPayTransFPTBean.setSourceAgentId(transData.getSourceAgentId());
                    anyPayTransFPTBean.setSourceIsdn(transData.getSourceMsisdn());
                    anyPayTransFPTBean.setTargetIsdn(transData.getTargetMsisdn());
                    anyPayTransFPTBean.setTransId(transData.getTransId());
                    anyPayTransFPTBean.setTransType(transData.getTransType());
                }
                String error = "";
                Long agentId = anyPayTransFPTBean.getSourceAgentId();

                Date startCall = new Date();
                if (anyPayTransFPTBean != null) {
                    if (anyPayTransFPTBean.getTransType().equals("LOAD")) {
                        String functionCall = "anyPayLogic.destroyReload";
                        logStartCall(startCall, functionCall, callId, "agentId=", agentId, "selectedTransList", selectedTransList[i].toString());
                        anyPayMsg = anyPayLogic.destroyReload(getLanguage(), agentId, Long.parseLong(selectedTransList[i]));
                        logEndCall(startCall, new Date(), functionCall, callId);
                        error = anyPayMsg.getErrCode();
                    } else if (anyPayTransFPTBean.getTransType().equals("TRANS")) {
                        String functionCall = "anyPayLogic.destroyTranfer";
                        logStartCall(startCall, functionCall, callId, "agentId=", agentId, "selectedTransList", selectedTransList[i].toString());
                        anyPayMsg = anyPayLogic.destroyTranfer(getLanguage(), agentId, selectedTransList[i].toString());
                        error = anyPayMsg.getErrCode();
                        logEndCall(startCall, new Date(), functionCall, callId);
                    } else {
                        //rollback
                        anypaySession.rollbackAnypayTransaction();

                        //Lá»—i. Giao dá»‹ch cáº§n há»§y khÃ´ng pháº£i loáº¡i TRANS hoáº·c LOAD
                        req.setAttribute("returnMsg", "ERR.SIK.158");
                        return pageForward;
                    }

                    if (error != null && !error.equals("")) {
                        if (!showMessage.equals("")) {
                            showMessage += ", " + error;
                        } else {
                            showMessage = error;
                        }
                        if (anyPayMsg.getErrMsg() != null) {
                            showMessage += "-" + anyPayMsg.getErrMsg();
                        }
                    } else {
                        //Luu log
                        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                        lstLogObj.add(new ActionLogsObj("TransFPT", "PROCESS_STATUS", "3", "5"));
                        saveLog(Constant.ACTION_CANCEL_ANYPAY_TRANS, "Há»§y giao dá»‹ch náº¡p, chuyá»ƒn tiá»�n", lstLogObj, Long.parseLong(selectedTransList[i]));
                    }
                }
            }

            anyPayTransForm.setPTransIdList(null);

            if (showMessage != null && !showMessage.equals("")) {
                //rollback
                anypaySession.rollbackAnypayTransaction();

                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();

                //refresh list
                searchAnyPayTrans();

                req.setAttribute(Constant.RETURN_MESSAGE, showMessage);
                return pageForward;

            } else {
                //commit;
                anypaySession.commitAnypayTransaction();

                getSession().flush();
                getSession().getTransaction().commit();
                getSession().beginTransaction();

                //refresh list
                searchAnyPayTrans();

//                req.setAttribute(Constant.RETURN_MESSAGE, "Huá»· GD thÃ nh cÃ´ng!");
                req.setAttribute("returnMsg", "ERR.SIK.084");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "OK");
                return pageForward;
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            //rollback
            anypaySession.rollbackAnypayTransaction();

            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            throw ex;
        }
    }

    public boolean checkStatusAccount(Long AgentId) {
        //TEST: tra ve true de pass qua TH nay--->
        return true;
        /*
         * UpdateForSales updateForSales = new UpdateForSales(); ViettelService
         * request = updateForSales.FindAccountAnypayByAgentId(AgentId); if
         * (request != null) { Object STAFF_STK_ID =
         * request.get("STAFF_STK_ID"); Object status = request.get("STATUS");
         * if (status != null && STAFF_STK_ID != null) { if
         * (Long.valueOf(status.toString()).equals(Constant.STATUS_ACTIVE)) {
         * return true; } else { return false; } } else { return false; } }
         * return false;
         */
//        String sql = "select status FROM evoucher_owner.agent@db_evoucher where agent_id = ?";
//        Query query = getSession().createSQLQuery(sql);
//        query.setParameter(0, AgentId);
//        List list = query.list();
//        Iterator iter = list.iterator();
//        Long status = 0L;
//        while (iter.hasNext()) {
//            Object temp = (Object) iter.next();
//            status = new Long(temp.toString());
//            if (status.equals(Constant.STATUS_ACTIVE)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        return false;
    }

    public String repairError() throws Exception {
        String serverFileName = saleToAnyPayForm.getServerFileName();
        // DungPT_Fix_ATTT
        serverFileName = getSafeFileName(serverFileName);
        HttpServletRequest req = getRequest();
        //set ID nguoi ban        
        String anyPayTrans = "0";
        Date createDate;

        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 3);
        Session session = getSession();

        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            String datetime = object[3].toString();
            String checktrukho = "";
            Long agentId = Long.parseLong(object[0].toString().trim());
            Long quantity = Long.parseLong(object[1].toString().trim());
            anyPayTrans = object[2].toString().trim();
            createDate = DateTimeUtils.convertStringToDateVunt(datetime);
//            checktrukho = object[4].toString().trim();

//            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
//            accountAgentDAO.setSession(getSession());
//            List<AccountAgent> listAgent = accountAgentDAO.findByProperty("agentId", agentId);
//            AccountAgent accountAgent = null;
//            if (listAgent != null && listAgent.size() > 0) {
//                accountAgent = listAgent.get(0);
//            }
            StockOwnerTmpDAO stockOwnerTmpDAO = new StockOwnerTmpDAO();
            stockOwnerTmpDAO.setSession(getSession());
            StockOwnerTmp stockOwnerTmp = stockOwnerTmpDAO.findById(agentId);
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            if (stockOwnerTmp != null) {
                saleToAnyPayForm.setReceiverCode(stockOwnerTmp.getCode());
                saleToAnyPayForm.setReceiverId(stockOwnerTmp.getOwnerId());
                saleToAnyPayForm.setReceiverType(Long.parseLong(stockOwnerTmp.getOwnerType()));
                saleToAnyPayForm.setItemQuantity(quantity);
                if (saleToAnyPayForm.getReceiverType().equals(Constant.OWNER_TYPE_SHOP)) {
                    Shop shop = shopDAO.findById(saleToAnyPayForm.getReceiverId());
                    if (shop != null) {
                        saleToAnyPayForm.setUserSaleAnyPayId(shop.getParentShopId());
                        saleToAnyPayForm.setUserSaleAnyPayType(Constant.OWNER_TYPE_SHOP);
                    }
                } else {
                    Staff staff = staffDAO.findById(saleToAnyPayForm.getReceiverId());
                    if (staff != null) {
                        saleToAnyPayForm.setUserSaleAnyPayId(staff.getStaffOwnerId());
                        saleToAnyPayForm.setUserSaleAnyPayType(Constant.OWNER_TYPE_STAFF);
                        saleToAnyPayForm.setShopUserSale(staff.getShopId());
                    }
                }
            } else {
                continue;
            }
            //tinh lai tien
            if (!sumAmountError()) {
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                continue;
            }

//            //Kiem tra so luong hang trong kho cua hang co du khong?
//            boolean check = false;
//            if (saleToAnyPayForm.getReceiverType() != null) {
//                check = StockCommonDAO.checkStockGoods(saleToAnyPayForm.getUserSaleAnyPayId(), saleToAnyPayForm.getUserSaleAnyPayType(), saleToAnyPayForm.getStockModelId(), Constant.STATE_NEW, saleToAnyPayForm.getItemQuantity(), Constant.UN_CHECK_DIAL, this.getSession());
//            }
//            if (!check) {
//                session.clear();
//                session.getTransaction().rollback();
//                session.beginTransaction();
//                continue;
//            }
            //Thuc hien tru kho cua kho cua hang
            //kiem tra de thuc hien tru kho
//            if (checktrukho.equals("1")) {
//            }
//            if (!updateStockTotal(saleToAnyPayForm)) {
//                session.clear();
//                session.getTransaction().rollback();
//                session.beginTransaction();
//                continue;
//            }


            //Tao giao dich ban hang cho CTV
            SaleTrans saleTrans = expSaleTransError(anyPayTrans, createDate);
            if (saleTrans == null) {
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                continue;
            }
            SaleTransDetail saleTransDetail = expSaleTransDetail(saleTrans, saleToAnyPayForm);
            if (saleTransDetail == null) {
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                continue;
            }
            //commit neu thanh cong
            session.getTransaction().commit();
            session.flush();
            session.beginTransaction();
        }

        return SALE_ERROR_ANYPAY;
    }

    public boolean sumAmountError() throws Exception {
        log.debug("# Begin method sumAmount");
        HttpServletRequest req = getRequest();
        SaleToAccountAnyPayForm f = this.getSaleToAnyPayForm();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //reset
        saleToAnyPayForm.setTax(0.0);
        saleToAnyPayForm.setAmountNotTax(0.0);
        saleToAnyPayForm.setAmountTax(0.0);
        saleToAnyPayForm.setDiscount(0.0);
        saleToAnyPayForm.setVat(null);
        saleToAnyPayForm.setItemAmount(0.0);
        saleToAnyPayForm.setItemDiscountId(null);
        f.setTaxMoney("");
        f.setAmountNotTaxMoney("");
        f.setAmountTaxMoney("");
        f.setDiscountMoney("");

        //Kiem tra so luong hang trong kho cua hang co du khong?
        String shopType = "2";//////////////////////////////////////////////////Tai sao chi co doi tuong CTV, ko co doi tuong dai ly
        f.setReceiverType(Long.parseLong(shopType));
        boolean check = false;
        if (f.getReceiverType() != null && String.valueOf(f.getReceiverType()).equals(Constant.OBJECT_TYPE_STAFF)) {
            check = StockCommonDAO.checkStockGoods(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, f.getStockModelId(), Constant.STATE_NEW, f.getItemQuantity(), Constant.UN_CHECK_DIAL, this.getSession());
        } else if (f.getReceiverType() != null && String.valueOf(f.getReceiverType()).equals(Constant.OBJECT_TYPE_SHOP)) {
            check = StockCommonDAO.checkStockGoods(userToken.getShopId(), Constant.OWNER_TYPE_SHOP, f.getStockModelId(), Constant.STATE_NEW, f.getItemQuantity(), Constant.UN_CHECK_DIAL, this.getSession());
        } else {
            req.setAttribute("returnMsg", "ERR.SIK.066");
            return false;
        }
        if (check == false) {
            req.setAttribute("returnMsg", "saleColl.warn.saleQuantity");
            return false;
        }

        //check exist
        String discountPolicy = "";
        if (String.valueOf(f.getReceiverType()).equals(Constant.OBJECT_TYPE_SHOP)) {
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            List<Shop> listShop = shopDAO.findByShopCode(f.getReceiverCode().trim());
            if (listShop == null || listShop.size() == 0) {
                req.setAttribute("returnMsg", "Error! Shop code is not exist!");
                return false;
            } else {
                f.setReceiverId(listShop.get(0).getShopId());
            }
        } else {
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = staffDAO.findAvailableByStaffCode(f.getReceiverCode().trim());
            if (staff == null) {
                req.setAttribute("returnMsg", "Error! Staff code is not exist!");
                return false;
            } else {
                f.setReceiverId(staff.getStaffId());
                f.setReceiverName(staff.getName());
            }
        }

        //discountGroupId
        SaleCommonDAO saleCommonDAO = new SaleCommonDAO();
        saleCommonDAO.setSession(this.getSession());
        Long discountGroupId = saleCommonDAO.getDiscountGroupId(f.getStockModelId(), discountPolicy);

        List<SaleTransDetailV2Bean> lstGoods = new ArrayList();
        SaleTransDetailV2Bean bean = new SaleTransDetailV2Bean();
        bean.setStockModelId(f.getStockModelId());
        bean.setQuantity(f.getItemQuantity());

        //Price
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        Price price = priceDAO.findById(f.getItemPriceId());
        if (price == null) {
            req.setAttribute("returnMsg", "Error! Price of eCard is not exist!");
            return false;
        }

        bean.setPriceId(price.getPriceId());
        bean.setPrice(price.getPrice());
        bean.setVat(price.getVat());
        bean.setCurrency(price.getCurrency());
        bean.setPriceDisplay(NumberUtils.rounđAndFormatAmount(price.getPrice()));

        if (Constant.PRICE_AFTER_VAT) {//Neu la gia sau thue (VIETNAM)
            bean.setAmountAfterTax(
                    f.getItemQuantity() * price.getPrice());//For update to sale_trans_detail
            bean.setAmountBeforeTax(bean.getAmountAfterTax() / (1.0 + price.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT));
            bean.setAmountTax(bean.getAmountAfterTax() - bean.getAmountBeforeTax());
        } else {//Neu la gia truoc thue (HAITI)
            bean.setAmountBeforeTax(
                    f.getItemQuantity() * price.getPrice());//For update to sale_trans_detail
            bean.setAmountTax(bean.getAmountBeforeTax() * price.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT);
            bean.setAmountAfterTax(bean.getAmountTax() + bean.getAmountBeforeTax());
        }
        bean.setAmountAfterTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountAfterTax()));//For display to interface
        bean.setAmountTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountTax()));//For display to interface
        bean.setAmountBeforeTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountBeforeTax()));//For display to interface

        bean.setDiscountGroupId(discountGroupId);
        lstGoods.add(bean);
        SaleTransForm saleTransForm = new SaleTransForm();
        Map<String, Map<Long, Double>> map = saleCommonDAO.sumDiscount(req, lstGoods, saleTransForm);

        f.setAmountNotTaxMoney((saleTransForm.getAmountTaxDisplay()));
        f.setDiscountMoney((saleTransForm.getAmountDiscountDisplay()));
        f.setTaxMoney((saleTransForm.getAmountTaxDisplay()));
        f.setAmountTaxMoney((saleTransForm.getAmountAfterTaxDisplay()));

        saleToAnyPayForm.setTax(saleTransForm.getAmountTax());
        saleToAnyPayForm.setAmountNotTax(saleTransForm.getAmountBeforeTax());
        saleToAnyPayForm.setAmountTax(saleTransForm.getAmountAfterTax());
        saleToAnyPayForm.setDiscount(saleTransForm.getAmountDiscount());
        saleToAnyPayForm.setVat(price.getVat());
        saleToAnyPayForm.setItemAmount(saleTransForm.getAmountAfterTax());
        saleToAnyPayForm.setItemDiscountId(lstGoods.get(0).getDiscountId());

        return true;
    }

    public SaleTrans expSaleTransError(String iTransId, Date createDate) {
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

            SaleTrans saleTrans = new SaleTrans();
            saleTrans.setSaleTransId(getSequence("SALE_TRANS_SEQ"));
            saleTrans.setSaleTransCode("GD0000" + saleTrans.getSaleTransId().toString());
            //saleTrans.setReasonId(saleToAnyPayForm.getReasonId());
            saleTrans.setSaleTransDate(createDate);
            saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_COLLABORATOR);
            saleTrans.setStatus(Constant.SALE_TRANS_STATUS_NOT_BILLED);
            if (saleToAnyPayForm.getReceiverType().equals(Constant.OWNER_TYPE_SHOP)) {
                saleTrans.setShopId(saleToAnyPayForm.getUserSaleAnyPayId());
                saleTrans.setStaffId(null);
            } else {
                saleTrans.setShopId(saleToAnyPayForm.getShopUserSale());
                saleTrans.setStaffId(saleToAnyPayForm.getUserSaleAnyPayId());
            }


            saleTrans.setTax(saleToAnyPayForm.getTax().doubleValue());//Thue VAT
            saleTrans.setAmountNotTax(saleToAnyPayForm.getAmountNotTax().doubleValue());//Tien chua thue
            saleTrans.setDiscount(saleToAnyPayForm.getDiscount().doubleValue());//Tien chiet khau
            saleTrans.setAmountTax(saleToAnyPayForm.getAmountTax().doubleValue());//Tong tien phai tra
            saleTrans.setPayMethod(Constant.PAY_METHOD_MONNEY);//Hinh thuc thanh toan
            saleTrans.setVat(saleToAnyPayForm.getVat().doubleValue());
            saleTrans.setTelecomServiceId(Constant.TELECOM_SERVICE_ANYPAY);

            //saleTrans.setTelecomServiceId(saleToAnyPayForm.getTelecomServiceId());

            saleTrans.setAmountModel(saleToAnyPayForm.getItemAmount().doubleValue());

            saleTrans.setReceiverId(saleToAnyPayForm.getReceiverId());
            saleTrans.setReceiverType(saleToAnyPayForm.getReceiverType());
            saleTrans.setCustName(saleToAnyPayForm.getReceiverCode().trim() + " - " + saleToAnyPayForm.getReceiverName().trim());
            saleTrans.setReasonId(saleToAnyPayForm.getReasonId());
            saleTrans.setInTransId(iTransId);

            this.getSession().save(saleTrans);
            return saleTrans;

        } catch (Exception ex) {
            this.getSession().clear();
            ex.printStackTrace();
            return null;
        }
    }

    public String repairErrorSaleTrans() throws Exception {
        String serverFileName = saleToAnyPayForm.getServerFileName();
        // DungPT_Fix_ATTT
        serverFileName = getSafeFileName(serverFileName);
        HttpServletRequest req = getRequest();
        //set ID nguoi ban
        String anyPayTrans = "0";
        Date createDate;
        String confirmSms = "";
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 1);
        for (int i = 0; i < list.size(); i++) {
            getSession().beginTransaction();
            Object[] object = (Object[]) list.get(i);
            String isdn = object[0].toString().trim();
            String isdnSendSMS = object[1].toString().trim();
            BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(isdnSendSMS));
            confirmSms = String.format("Ban bi tru 50000 dong vao Tai khoan thanh toan do dau noi thanh cong thue bao %s ngay 01/07/2010", isdn);
            int sentResult = SmsClient.sendSMS155(isdnSearch.toString(), confirmSms);

//            String sql = "From SaleTrans where saleTransDate >= TRUNC (SYSDATE - 1) and saleTransDate < TRUNC(SYSDATE) and isdn = ?";
//            Query query = getSession().createQuery(sql);
//            query.setParameter(0, isdn);
//            List<SaleTrans> listSaleTrans = query.list();
//            SaleTrans saleTrans = new SaleTrans();
//            Long staffId = 0L;
//            Long saleTransId = 0L;
//            if (list != null && list.size()>0){
//                saleTrans = listSaleTrans.get(0);
//                staffId = saleTrans.getStaffId();
//                saleTransId = saleTrans.getSaleTransId();
//                sql = "From AccountBook where stockTransId = ? and requestType = 4";
//                query = getSession().createQuery(sql);
//                query.setParameter(0, saleTransId);
//                List<AccountBook> listAccountBook = query.list();
//                AccountBook accountBook = new AccountBook();
//                if (listAccountBook != null && listAccountBook.size()>0){
//                    accountBook = listAccountBook.get(0);
//                }
//            }
//            else{
//                continue;
//            }
            //getSession().getTransaction().commit();
        }
        return SALE_ERROR_ANYPAY;
    }

    public String createAnyPayTrans() throws SQLException {
        HttpServletRequest req = getRequest();
        String pageForward = SALE_TO_ANYPAY;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        SaleToAccountAnyPayFPTDAO saleToAccountAnyPayFPTDAO = new SaleToAccountAnyPayFPTDAO();
        Connection fptConnection = saleToAccountAnyPayFPTDAO.getConnection();
        String[] arrMess = new String[3];
        try {

            arrMess = saleToAccountAnyPayFPTDAO.saleToAccountAnyPay(fptConnection, 1076L, 283994L, 10000000L, DateTimeUtils.convertStringToDateTime("14/06/2010 13:46:25"), "B056_TC_001",
                    "10.58.3.53");
            if (arrMess[2].equals("") || !arrMess[0].equals("") || !arrMess[1].equals("")) {
                req.setAttribute("returnMsg", "Lỗi tạo GD !!! " + arrMess[1]);
                return pageForward;
            }
            fptConnection.commit();

        } catch (Exception ex) {
            if (fptConnection != null && !fptConnection.isClosed()) {
                fptConnection.rollback();
            }
        } finally {
            if (fptConnection != null && !fptConnection.isClosed()) {
                fptConnection.close();
            }
        }

        return pageForward;
    }

    public String updateCheckVAT() throws SQLException, Exception {
        String serverFileName = saleToAnyPayForm.getServerFileName();
        // DungPT_Fix_ATTT
        serverFileName = getSafeFileName(serverFileName);
        HttpServletRequest req = getRequest();
        //set ID nguoi ban
        String anyPayTrans = "0";
        Date createDate;

        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 1);
        Session session = getSession();
        session.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            String staff_code = object[0].toString();
            String status = object[1].toString();
            String sql = "From AccountAgent where lower(ownerCode) = lower(?)";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, staff_code.trim());
            List<AccountAgent> listAccount = query.list();
            if (listAccount != null && listAccount.size() > 0 && status != null && !status.equals("") && chkNumber(status)) {
                AccountAgent accountAgent = listAccount.get(0);
                accountAgent.setCheckVat(Long.parseLong(status));
                session.getTransaction().commit();
                session.flush();
                session.beginTransaction();
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                if (accountAgent.getCheckVat() != null) {
                    lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "CHECK_VAT", accountAgent.getCheckVat().toString(), status));
                } else {
                    lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "CHECK_VAT", "", status));
                }

                saveLog("UPDATE_CHECK_VAT", "Cập nhật thông tin cam kết", lstLogObj, accountAgent.getAccountId());
            }

        }
        req.setAttribute("returnMsg", "Cập nhật thông tin cam kết thành công");
        return UPDATE_CHECK_VAT;
    }

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
