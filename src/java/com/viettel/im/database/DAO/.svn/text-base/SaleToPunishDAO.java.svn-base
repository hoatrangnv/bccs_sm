
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.AppParamsBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.SaleTransDetailBean;
import com.viettel.im.client.bean.SaleTransDetailV2Bean;
import com.viettel.im.client.bean.ViewPackageCheck;
import com.viettel.im.client.form.SaleTransDetailForm;
import com.viettel.im.client.form.SaleTransForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DataUtils;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.AccountBalance;
import com.viettel.im.database.BO.AccountBook;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.SaleTransPost;
import com.viettel.im.database.BO.SaleTransSerial;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTotalFull;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.sms.SmsClient;
import com.viettel.lib.sm.database.client.bean.StockSerialBean;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.hibernate.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;

/**
 *
 * @author NamLT
 */
public class SaleToPunishDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(SaleToPunishDAO.class);
    private static final String SALE_TO_PUNISH = "saleToPunish";
    private static final String SALE_TO_PUNISH_DETAIL = "saleToPunishDetail";
    private static final String SALE_TO_PUNISH_GOOD_LIST = "saleToPunishGoodList";
    private static final String SALE_TO_PUNISH_ADD_GOOD = "saleToPunishAddGood";
    private static final String SALE_TO_PUNISH_INPUT_GOOD = "saleToPunishInputGood";
    private static final String CHANGE_OBJECT_TYPE = "changeObjectType";
    private SaleTransForm form;
    private String pageForward;
    private static final String REQUEST_MESSAGE = "returnMsg";
    private static final String LIST_GOOD = "lstGoods";
    private Map priceListMap = new HashMap();

    public Map getPriceListMap() {
        return priceListMap;
    }

    public void setPriceListMap(Map priceListMap) {
        this.priceListMap = priceListMap;
    }

    public SaleTransForm getForm() {
        return form;
    }

    public void setForm(SaleTransForm form) {
        this.form = form;
    }

    public String prepareSaleToPunish() throws Exception {
        log.info("Begin method prepareSaleToPunish");
        req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        form = new SaleTransForm();

        if (userToken != null) {
            try {
                setTabSession(LIST_GOOD, null);
                resetSaleTransDetailForm();
                form.setSaleTransDate(getSysdate());

                getDataForComboxBox();
                pageForward = SALE_TO_PUNISH;
            } catch (Exception e) {
                String str = CommonDAO.readStackTrace(e);
                log.info(str);
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method prepareSaleToPunish");
        return pageForward;
    }

    public void getDataForComboxBox() throws Exception {
        req = getRequest();
        String sql = " from ChannelType where 1=1 and isVtUnit = ? and status=? and isPrivate = ? ";
        Query qry = getSession().createQuery(sql);
        qry.setParameter(0, Constant.NOT_IS_VT_UNIT);
        qry.setParameter(1, Constant.STATUS_USE);
        qry.setParameter(2, Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        List<ChannelType> lstChannelType = qry.list();
        req.setAttribute("lstChannelType", lstChannelType);

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(getSession());
        Long[] saleTransType = new Long[1];
        saleTransType[0] = Long.parseLong(Constant.SALE_TRANS_TYPE_PUNISH);
        List<Reason> lstReason = reasonDAO.getReasonBySaleTransType(saleTransType);
        req.setAttribute("lstReason", lstReason);
        if (lstReason != null && !lstReason.isEmpty() && form != null && form.getReasonId() == null) {
            form.setReasonId(lstReason.get(0).getReasonId());
        }
    }

    public String addGoods() throws Exception {
        log.debug("# Begin method addGoods");
        pageForward = SALE_TO_PUNISH_DETAIL;

        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List lstGoods = (List) getTabSession(LIST_GOOD);
        if (lstGoods == null) {
            lstGoods = new ArrayList();
            setTabSession(LIST_GOOD, lstGoods);
        }

        Long channelTypeId = form.getChannelTypeId();
        Long receiverId = null;
        Long receiverType = null;
        String pricePolicy = null;
        String receiverCode = form.getReceiverCode();
        String stockModelCode = form.getSaleTransDetailForm().getStockModelCode();
        Long priceId = form.getSaleTransDetailForm().getPriceId();
        Long quantity = form.getSaleTransDetailForm().getQuantity();

        if (channelTypeId == null || receiverCode == null || receiverCode.trim().equals("") || stockModelCode == null || stockModelCode.trim().equals("") || priceId == null || quantity == null || quantity.equals(0L)) {

            if (channelTypeId == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STAFF.0009");
            } else if (receiverCode == null || receiverCode.trim().equals("")) {
                req.setAttribute(Constant.RETURN_MESSAGE, "E.200054");
            } else if (stockModelCode == null || stockModelCode.trim().equals("")) {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleColl.warn.stock");
            } else if (priceId == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleColl.warn.price");
            } else if (quantity == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.quantity");
            } else if (quantity.compareTo(0L) <= 0) {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleColl.warn.quantity"); //!!!Lỗi. Số lượng phải là số nguyên dương
            } else {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống            
            }
            return pageForward;
        }

        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        ChannelType channelType = channelTypeDAO.findById(channelTypeId);
        if (channelType == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STAFF.0037");
            return pageForward;
        }

        String strQuery = "";
        Query query;
        List lstParam = new ArrayList();
        Shop receiverShop = null;
        Staff receiverStaff = null;
        if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            strQuery = " from Shop a ";
            strQuery += "where 1=1 and a.status=? and a.parentShopId=? and a.channelTypeId=? ";
            lstParam.add(Constant.STAFF_STATUS_ACTIVE);
            lstParam.add(userToken.getShopId());
            lstParam.add(channelType.getChannelTypeId());
            strQuery += "and lower(a.shopCode) = ? ";
            lstParam.add(receiverCode.trim().toLowerCase());
            query = getSession().createQuery(strQuery);
        } else if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
            strQuery = " from Staff a ";
            strQuery += " WHERE 1=1 and a.status=? and a.staffOwnerId = ? ";
            lstParam.add(Constant.STAFF_STATUS_ACTIVE);
            lstParam.add(userToken.getUserID());
            strQuery += "and lower(a.staffCode) = ? ";
            lstParam.add(receiverCode.trim().toLowerCase());
            query = getSession().createQuery(strQuery);
        } else {
            req.setAttribute(REQUEST_MESSAGE, "E.200055");
            return pageForward;
        }

        for (int idx = 0; idx < lstParam.size(); idx++) {
            query.setParameter(idx, lstParam.get(idx));
        }
        List lstReceiver = query.list();
        if (lstReceiver != null && !lstReceiver.isEmpty()) {
            if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                receiverShop = (Shop) lstReceiver.get(0);
                receiverId = receiverShop.getShopId();
                receiverType = Constant.OWNER_TYPE_SHOP;
                pricePolicy = receiverShop.getPricePolicy();
                receiverCode = receiverShop.getShopCode();
            } else if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
                receiverStaff = (Staff) lstReceiver.get(0);
                receiverId = receiverStaff.getStaffId();
                receiverType = Constant.OWNER_TYPE_STAFF;
                pricePolicy = receiverStaff.getPricePolicy();
                receiverCode = receiverStaff.getStaffCode();
            }
        }
        if (receiverShop == null && receiverStaff == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STAFF.0037");
            return pageForward;
        }


        StockModelDAO stockModelDAO = new StockModelDAO();
        StockModel stockModel = stockModelDAO.getStockModelByCode(stockModelCode.trim());
        if (stockModel == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return pageForward;
        }

        boolean checkValidatePrice = false;
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(getSession());
        Price price = null;
        List<Price> priceList = priceDAO.findByPolicyTypeAndStockModel(pricePolicy, Constant.PRICE_TYPE_PUNISH, stockModel.getStockModelId());
        if (priceList != null && !priceList.isEmpty()) {
            for (int i = 0; i < priceList.size(); i++) {
                if (priceList.get(i).getPriceId().equals(priceId)) {
                    price = priceList.get(i);
                    checkValidatePrice = true;
                    break;
                }
            }
        }
        if (!checkValidatePrice) {
            setTabSession("lstPrice", getPriceList(pricePolicy, Constant.PRICE_TYPE_PUNISH, stockModel.getStockModelId()));
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.062");
            return pageForward;
        }

        List<Price> depositPriceList = priceDAO.findByPolicyTypeAndStockModel(pricePolicy, Constant.PRICE_TYPE_DEPOSIT, stockModel.getStockModelId());
        if (depositPriceList == null || depositPriceList.isEmpty()) {
            setTabSession("lstPrice", getPriceList(pricePolicy, Constant.PRICE_TYPE_PUNISH, stockModel.getStockModelId()));
            req.setAttribute(REQUEST_MESSAGE, "E.200056");
            return pageForward;
        }

        if (!lstGoods.isEmpty()) {
            SaleTransDetailV2Bean oldBean = (SaleTransDetailV2Bean) lstGoods.get(0);

            /* Kiem tra co cung ma KPP hay khong */
            if (!DataUtils.safeEqual(oldBean.getReceiverId(), receiverId) || !DataUtils.safeEqual(oldBean.getReceiverType(), receiverType)) {
                req.setAttribute(REQUEST_MESSAGE, "E.200053");//Khong cung ma KPP
                setTabSession("lstPrice", getPriceList(pricePolicy, Constant.PRICE_TYPE_PUNISH, stockModel.getStockModelId()));
                return pageForward;
            }

            if (!DataUtils.safeEqual(oldBean.getTelecomServiceId(), stockModel.getTelecomServiceId())) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.091");// Bạn phải chọn cùng loại dịch vụ viễn thông!
                setTabSession("lstPrice", getPriceList(pricePolicy, Constant.PRICE_TYPE_PUNISH, stockModel.getStockModelId()));
                return pageForward;
            }

            /* Kiem tra co cung thue suat VAT hay khong */
            if (!DataUtils.safeEqual(oldBean.getVat(), price.getVat())) {
                req.setAttribute(REQUEST_MESSAGE, "E.200051");//Khong cung thue suat VAT
                setTabSession("lstPrice", getPriceList(pricePolicy, Constant.PRICE_TYPE_PUNISH, stockModel.getStockModelId()));
                return pageForward;
            }
            if (!DataUtils.safeEqual(oldBean.getCurrency(), price.getCurrency())) {
                req.setAttribute(REQUEST_MESSAGE, "E.200052");//Khong cung loai tien te
                setTabSession("lstPrice", getPriceList(pricePolicy, Constant.PRICE_TYPE_PUNISH, stockModel.getStockModelId()));
                return pageForward;
            }

            /* Kiem tra hang hoa da ton tai hay chua */
            for (int i = 0; i < lstGoods.size(); i++) {
                if (DataUtils.safeEqual(((SaleTransDetailV2Bean) lstGoods.get(i)).getStockModelId(), stockModel.getStockModelId())) {
                    req.setAttribute(REQUEST_MESSAGE, "saleRetail.warn.SameStock");//Hang hoa da ton tai
                    setTabSession("lstPrice", getPriceList(pricePolicy, Constant.PRICE_TYPE_PUNISH, stockModel.getStockModelId()));
                    return pageForward;
                }
            }
        }

        boolean check = StockCommonDAO.checkStockGoods(receiverId, receiverType, stockModel.getStockModelId(), Constant.STATE_NEW, quantity, Constant.UN_CHECK_DIAL, this.getSession());
        if (check == false) {
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.saleQuantity");
            setTabSession("lstPrice", getPriceList(pricePolicy, Constant.PRICE_TYPE_PUNISH, stockModel.getStockModelId()));
            return pageForward;
        }

        SaleTransDetailV2Bean bean = new SaleTransDetailV2Bean();
        bean.setStockModelId(stockModel.getStockModelId());
        bean.setStockModelCode(stockModel.getStockModelCode());
        bean.setStockModelName(stockModel.getName());
        bean.setTelecomServiceId(stockModel.getTelecomServiceId());
        bean.setCheckSerial(stockModel.getCheckSerial());
        bean.setQuantity(quantity);
        bean.setPriceId(price.getPriceId());
        bean.setPrice(price.getPrice());
        bean.setVat(price.getVat());
        bean.setCurrency(price.getCurrency());
        bean.setPriceDisplay(NumberUtils.rounđAndFormatAmount(price.getPrice()));
        bean.setAmount(price.getPrice() * quantity);
        bean.setAmountDisplay(NumberUtils.rounđAndFormatAmount(price.getPrice() * quantity));

        bean.setPricePolicy(pricePolicy);
        bean.setReceiverId(receiverId);
        bean.setReceiverCode(receiverCode);
        bean.setReceiverType(receiverType);

        if (Constant.PRICE_AFTER_VAT) {//Neu la gia sau thue (VIETNAM)
            bean.setAmountAfterTax(
                    quantity * price.getPrice());//For update to sale_trans_detail
            bean.setAmountBeforeTax(bean.getAmountAfterTax() / (1.0 + price.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT));
            bean.setAmountTax(bean.getAmountAfterTax() - bean.getAmountBeforeTax());
        } else {//Neu la gia truoc thue (HAITI)
            bean.setAmountBeforeTax(
                    quantity * price.getPrice());//For update to sale_trans_detail            
            bean.setAmountTax(bean.getAmountBeforeTax() * price.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT);
            bean.setAmountAfterTax(bean.getAmountTax() + bean.getAmountBeforeTax());
        }
        bean.setAmountAfterTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountAfterTax()));//For display to interface
        bean.setAmountTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountTax()));//For display to interface
        bean.setAmountBeforeTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountBeforeTax()));//For display to interface

        lstGoods.add(bean);

        resetSaleTransDetailForm();

        this.sumSaleTransAmount(lstGoods);
        setTabSession(LIST_GOOD, lstGoods);

        req.setAttribute(Constant.RETURN_MESSAGE, "MSG.STK.020");
        return pageForward;
    }

    private void sumSaleTransAmount(List<SaleTransDetailV2Bean> lstGoods) throws Exception {
        form.setAmountBeforeTax(0.0);
        form.setAmountTax(0.0);
        form.setAmountAfterTax(0.0);

        form.setAmountBeforeTaxDisplay("0");
        form.setAmountTaxDisplay("0");
        form.setAmountAfterTaxDisplay("0");

        if (lstGoods == null || lstGoods.isEmpty()) {
            return;
        }

        Double amountAfterTax = 0.0;
        Double amountBeforeTax = 0.0;
        Double amountTax = 0.0;


        for (int i = 0; i < lstGoods.size(); i++) {
            SaleTransDetailV2Bean bean = lstGoods.get(i);
            if (bean == null) {
                continue;
            }
            amountBeforeTax += bean.getAmountBeforeTax();
            amountTax += bean.getAmountTax();
            amountAfterTax += bean.getAmountAfterTax();
        }
        form.setAmountBeforeTax(amountBeforeTax);
        form.setAmountTax(amountTax);
        form.setAmountAfterTax(amountAfterTax);

        form.setAmountBeforeTaxDisplay(NumberUtils.rounđAndFormatAmount(amountBeforeTax));
        form.setAmountTaxDisplay(NumberUtils.rounđAndFormatAmount(amountTax));
        form.setAmountAfterTaxDisplay(NumberUtils.rounđAndFormatAmount(amountAfterTax));
    }

    public List<ImSearchBean> getListChannel(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        try {
            req = imSearchBean.getHttpServletRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                Long channelTypeId = Long.valueOf(imSearchBean.getOtherParamValue().trim());
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                ChannelType channelType = channelTypeDAO.findById(channelTypeId);

                String strQuery = "";
                Query query;
                List lstParam = new ArrayList();

                if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                    strQuery = " select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) from Shop a ";
                    strQuery += "where 1=1 and a.status=? and a.parentShopId=? and a.channelTypeId=? ";
                    lstParam.add(Constant.STAFF_STATUS_ACTIVE);
                    lstParam.add(userToken.getShopId());
                    lstParam.add(channelType.getChannelTypeId());

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += "and lower(a.shopCode) like ? ";
                        lstParam.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
                    }
                    if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                        strQuery += "and lower(a.name) like ? ";
                        lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                    }
                    strQuery += " and rownum <= ? ";
                    lstParam.add(100L);
                    query = getSession().createQuery(strQuery);

                } else if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {

                    strQuery = " select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) from Staff a ";
                    strQuery += " WHERE 1=1 and a.status=? and a.staffOwnerId = ? and a.channelTypeId=? ";
                    lstParam.add(Constant.STAFF_STATUS_ACTIVE);
                    lstParam.add(userToken.getUserID());
                    lstParam.add(channelType.getChannelTypeId());
                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += " and lower(a.staffCode) like ? ";
                        lstParam.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
                    }

                    if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                        strQuery += " and lower(a.name) like ? ";
                        lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                    }
                    strQuery += " and rownum <= ? ";
                    lstParam.add(100L);
                    query = getSession().createQuery(strQuery);
                } else {
                    throw new Exception("ChannelType not exists");
                }

                for (int idx = 0; idx < lstParam.size(); idx++) {
                    query.setParameter(idx, lstParam.get(idx));
                }

                List<ImSearchBean> tmpList1 = query.list();
                if (tmpList1 != null && tmpList1.size() > 0) {
                    listImSearchBean.addAll(tmpList1);
                }
            }
            return listImSearchBean;
        } catch (Exception ex) {
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
            return listImSearchBean;
        }
    }

    public List<ImSearchBean> getListChannelName(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        try {
            req = imSearchBean.getHttpServletRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                Long channelTypeId = Long.valueOf(imSearchBean.getOtherParamValue().trim());
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                ChannelType channelType = channelTypeDAO.findById(channelTypeId);

                String strQuery = "";
                Query query;
                List lstParam = new ArrayList();

                if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                    strQuery = " select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) from Shop a ";
                    strQuery += "where 1=1 and a.status=? and a.parentShopId=? and a.channelTypeId=? ";
                    lstParam.add(Constant.STAFF_STATUS_ACTIVE);
                    lstParam.add(userToken.getShopId());
                    lstParam.add(channelType.getChannelTypeId());

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += "and lower(a.shopCode) = ? ";
                        lstParam.add(imSearchBean.getCode().trim().toLowerCase());
                    }
                    if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                        strQuery += "and lower(a.name) = ? ";
                        lstParam.add(imSearchBean.getName().trim().toLowerCase());
                    }
                    query = getSession().createQuery(strQuery);

                } else if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {

                    strQuery = " select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) from Staff a ";
                    strQuery += " WHERE 1=1 and a.status=? and a.staffOwnerId = ? ";
                    lstParam.add(Constant.STAFF_STATUS_ACTIVE);
                    lstParam.add(userToken.getUserID());
                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += " and lower(a.staffCode) = ? ";
                        lstParam.add(imSearchBean.getCode().trim().toLowerCase());
                    }

                    if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                        strQuery += " and lower(a.name) = ? ";
                        lstParam.add(imSearchBean.getName().trim().toLowerCase());
                    }

                    query = getSession().createQuery(strQuery);
                } else {
                    throw new Exception("ChannelType not exists");
                }

                for (int idx = 0; idx < lstParam.size(); idx++) {
                    query.setParameter(idx, lstParam.get(idx));
                }

                List<ImSearchBean> tmpList1 = query.list();
                if (tmpList1 != null && tmpList1.size() > 0) {
                    listImSearchBean.addAll(tmpList1);
                }
            }
            return listImSearchBean;
        } catch (Exception ex) {
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
            return listImSearchBean;
        }
    }

    public Long getListChannelSize(ImSearchBean imSearchBean) {
        try {
            req = imSearchBean.getHttpServletRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                Long channelTypeId = Long.valueOf(imSearchBean.getOtherParamValue().trim());
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                ChannelType channelType = channelTypeDAO.findById(channelTypeId);

                String strQuery = "";
                Query query;
                List lstParam = new ArrayList();

                if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                    strQuery = " select count(*) from Shop a ";
                    strQuery += "where 1=1 and a.status=? and a.parentShopId=? and a.channelTypeId=? ";
                    lstParam.add(Constant.STATUS_USE);
                    lstParam.add(userToken.getShopId());
                    lstParam.add(channelType.getChannelTypeId());

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += "and lower(a.shopCode) like ? ";
                        lstParam.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
                    }
                    if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                        strQuery += "and lower(a.name) like ? ";
                        lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                    }
                    query = getSession().createQuery(strQuery);

                } else if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
                    strQuery = " select  count(*) from Staff a ";
                    strQuery += " WHERE 1=1 and a.status=? and a.staffOwnerId = ? ";
                    lstParam.add(Constant.STATUS_USE);
                    lstParam.add(userToken.getUserID());
                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += " and lower(a.staffCode) like ? ";
                        lstParam.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
                    }

                    if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                        strQuery += " and lower(a.name) like ? ";
                        lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                    }
                    query = getSession().createQuery(strQuery);
                } else {
                    throw new Exception("ChannelType not exists");
                }

                for (int idx = 0; idx < lstParam.size(); idx++) {
                    query.setParameter(idx, lstParam.get(idx));
                }

                List<Long> tmpList1 = query.list();
                if (tmpList1 != null && tmpList1.size() > 0) {
                    return tmpList1.get(0);
                }
            }
            return 0L;
        } catch (Exception ex) {
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
            return 0L;
        }
    }

    public String updateListGoodsPrice() throws Exception {
        log.info("Begin method updateListGoodsPrice");
        try {
            String[] header = {"", getText("MSG.SAE.024")};
            List tmpList = new ArrayList();

            req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            String channelTypeId = req.getParameter("channelTypeId");
            if (channelTypeId == null || channelTypeId.trim().equals("")) {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);
                return "success";
            }

            String receiverCode = req.getParameter("receiverCode");
            if (receiverCode == null || receiverCode.trim().equals("")) {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);
                return "updateListModelPrice";
            }

            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            ChannelType channelType = channelTypeDAO.findById(Long.valueOf(channelTypeId));

            String strQuery = "";
            Query query;
            List lstParam = new ArrayList();

            if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                strQuery = " select pricePolicy from Shop a ";
                strQuery += "where 1=1 and a.status=? and a.parentShopId=? and a.channelTypeId=? ";
                lstParam.add(Constant.STAFF_STATUS_ACTIVE);
                lstParam.add(userToken.getShopId());
                lstParam.add(channelType.getChannelTypeId());
                strQuery += "and lower(a.shopCode) = ? ";
                lstParam.add(receiverCode.trim().toLowerCase());
                query = getSession().createQuery(strQuery);
            } else if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
                strQuery = " select pricePolicy from Staff a ";
                strQuery += " WHERE 1=1 and a.status=? and a.staffOwnerId = ? ";
                lstParam.add(Constant.STAFF_STATUS_ACTIVE);
                lstParam.add(userToken.getUserID());
                strQuery += "and lower(a.staffCode) = ? ";
                lstParam.add(receiverCode.trim().toLowerCase());
                query = getSession().createQuery(strQuery);
            } else {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);
                return "updateListModelPrice";
            }
            for (int idx = 0; idx < lstParam.size(); idx++) {
                query.setParameter(idx, lstParam.get(idx));
            }
            List tmpPricePolicy = query.list();
            String pricePolicy = "";
            if (tmpPricePolicy != null && !tmpPricePolicy.isEmpty()) {
                pricePolicy = tmpPricePolicy.get(0).toString();
            } else {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);
                return "updateListModelPrice";
            }

            String stockModelCode = req.getParameter("stockModelCode");
            if (stockModelCode == null || stockModelCode.trim().equals("")) {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);
                return "updateListModelPrice";
            }
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.getStockModelByCode(stockModelCode.trim());
            if (stockModel == null) {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);
                return "updateListModelPrice";
            }
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
            List<Price> priceList = priceDAO.findByPolicyTypeAndStockModel(pricePolicy, Constant.PRICE_TYPE_PUNISH, stockModel.getStockModelId());
            if (priceList != null && !priceList.isEmpty()) {
                for (int i = 0; i < priceList.size(); i++) {
                    priceList.get(i).setPriceName(NumberUtils.formatNumber(priceList.get(i).getPrice()));
                    String[] content = {priceList.get(i).getPriceId().toString(), NumberUtils.formatNumber(priceList.get(i).getPrice())};
                    tmpList.add(content);
                }
            }
//            tmpList.addAll(priceList);
            priceListMap.put("priceId", tmpList);

        } catch (Exception e) {
            String str = CommonDAO.readStackTrace(e);
            log.info(str);
            throw e;
        }
        log.info("End method updateListGoodsPrice");
        return "updateListModelPrice";
    }

    public String prepareEditGoods() throws Exception {
        req = getRequest();
        pageForward = SALE_TO_PUNISH_DETAIL;

        if (form == null) {
            form = new SaleTransForm();
        }

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(LIST_GOOD);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            return pageForward;
        }
        this.sumSaleTransAmount(lstGoods);
        String stockModelIdTemp = (String) req.getParameter("stockModelId");
        if (stockModelIdTemp == null || stockModelIdTemp.trim().equals("")) {
            return pageForward;
        }
        Long stockModelId = Long.valueOf(stockModelIdTemp.trim());
        for (SaleTransDetailV2Bean bean : lstGoods) {
            if (bean.getStockModelId().equals(stockModelId)) {
                SaleTransDetailForm goodsForm = new SaleTransDetailForm();
                goodsForm.setStockModelId(bean.getStockModelId());
                goodsForm.setStockModelCode(bean.getStockModelCode());
                goodsForm.setStockModelName(bean.getStockModelName());
                goodsForm.setPriceId(bean.getPriceId());
                goodsForm.setQuantity(bean.getQuantity());
                goodsForm.setNote(bean.getNote());
                form.setSaleTransDetailForm(goodsForm);
                setTabSession("lstPrice", getPriceList(bean.getPricePolicy(), Constant.PRICE_TYPE_PUNISH, bean.getStockModelId()));
                break;
            }
        }
        return pageForward;
    }

    public String deleteGoods() throws Exception {
        req = getRequest();
        pageForward = SALE_TO_PUNISH_DETAIL;

        if (form == null) {
            form = new SaleTransForm();
        }

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(LIST_GOOD);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            return pageForward;
        }

        String stockModelIdTemp = (String) req.getParameter("stockModelId");
        if (stockModelIdTemp == null || stockModelIdTemp.trim().equals("")) {
            return pageForward;
        }

        Long stockModelId = Long.valueOf(stockModelIdTemp.trim());
        for (SaleTransDetailV2Bean bean : lstGoods) {
            if (bean.getStockModelId().equals(stockModelId)) {
                lstGoods.remove(bean);
                break;
            }
        }
        resetSaleTransDetailForm();

        this.sumSaleTransAmount(lstGoods);
        setTabSession(LIST_GOOD, lstGoods);

        req.setAttribute(Constant.RETURN_MESSAGE, "MSG.STK.021");
        return pageForward;
    }

    public String editGoods() throws Exception {
        req = getRequest();
        pageForward = SALE_TO_PUNISH_DETAIL;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Long channelTypeId = form.getChannelTypeId();

        String receiverCode = form.getReceiverCode();
        Long stockModelId = form.getSaleTransDetailForm().getStockModelId();
        Long priceId = form.getSaleTransDetailForm().getPriceId();
        Long quantity = form.getSaleTransDetailForm().getQuantity();

        if (channelTypeId == null || receiverCode == null || receiverCode.trim().equals("") || stockModelId == null || priceId == null || quantity == null || quantity.equals(0L)) {
            if (channelTypeId == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STAFF.0009");
            } else if (receiverCode == null || receiverCode.trim().equals("")) {
                req.setAttribute(Constant.RETURN_MESSAGE, "E.200054");
            } else if (stockModelId == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleColl.warn.stock");
            } else if (priceId == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleColl.warn.price");
            } else if (quantity == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.quantity");
            } else if (quantity.compareTo(0L) <= 0) {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleColl.warn.quantity"); //!!!Lỗi. Số lượng phải là số nguyên dương
            } else {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống            
            }
            return pageForward;
        }

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(LIST_GOOD);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            resetSaleTransDetailForm();
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.088 "); // Danh sách hàng hoá rỗng!            
            return pageForward;
        }

        for (SaleTransDetailV2Bean bean : lstGoods) {
            if (bean.getStockModelId().equals(stockModelId)) {
                /* check thong tin ma KPP co trung hay khong */

                boolean check = StockCommonDAO.checkStockGoods(bean.getReceiverId(), bean.getReceiverType(), bean.getStockModelId(), Constant.STATE_NEW, quantity, Constant.UN_CHECK_DIAL, this.getSession());
                if (check == false) {
                    req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.saleQuantity");
                    setTabSession("lstPrice", getPriceList(bean.getPricePolicy(), Constant.PRICE_TYPE_PUNISH, bean.getStockModelId()));
                    return pageForward;
                }

                bean.setQuantity(quantity);
                bean.setAmount(quantity * bean.getPrice());
                bean.setAmountDisplay(NumberUtils.rounđAndFormatAmount(quantity * bean.getPrice()));

                if (Constant.PRICE_AFTER_VAT) {//Neu la gia sau thue (VIETNAM)
                    bean.setAmountAfterTax(
                            quantity * bean.getPrice());
                    bean.setAmountBeforeTax(bean.getAmountAfterTax() / (1.0 + bean.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT));
                    bean.setAmountTax(bean.getAmountAfterTax() - bean.getAmountBeforeTax());
                } else {//Neu la gia truoc thue (HAITI)
                    bean.setAmountBeforeTax(
                            quantity * bean.getPrice());//For update to sale_trans_detail
                    bean.setAmountTax(bean.getAmountBeforeTax() * bean.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT);
                    bean.setAmountAfterTax(bean.getAmountTax() + bean.getAmountBeforeTax());
                }
                bean.setAmountAfterTaxDisplay(
                        NumberUtils.rounđAndFormatAmount(bean.getAmountAfterTax()));//For display to interface
                bean.setAmountTaxDisplay(
                        NumberUtils.rounđAndFormatAmount(bean.getAmountTax()));//For display to interface
                bean.setAmountBeforeTaxDisplay(
                        NumberUtils.rounđAndFormatAmount(bean.getAmountBeforeTax()));//For display to interface
                break;
            }
        }
        resetSaleTransDetailForm();

        this.sumSaleTransAmount(lstGoods);
        setTabSession(LIST_GOOD, lstGoods);

        req.setAttribute(Constant.RETURN_MESSAGE, "M.200003");

        return pageForward;
    }

    public String cancelEditGoods() throws Exception {
        req = getRequest();
        pageForward = SALE_TO_PUNISH_DETAIL;

        if (form == null) {
            form = new SaleTransForm();
        }

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(LIST_GOOD);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            return pageForward;
        }
        this.sumSaleTransAmount(lstGoods);
        resetSaleTransDetailForm();
        return pageForward;
    }
    
    public String refreshGoodsList() throws Exception {
        req = getRequest();
        pageForward = SALE_TO_PUNISH_DETAIL;
        
        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(LIST_GOOD);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            return pageForward;
        }
        form = new SaleTransForm();
        this.sumSaleTransAmount(lstGoods);
        return pageForward;        
    }

    private void resetSaleTransDetailForm() {
        form.setSaleTransDetailForm(new SaleTransDetailForm());
        setTabSession("lstPrice", null);
    }

    private List<Price> getPriceList(String pricePolicy, String priceType, Long stockModelId) {
        try {
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
            List<Price> priceList = priceDAO.findByPolicyTypeAndStockModel(pricePolicy, priceType, stockModelId);
            if (priceList != null && !priceList.isEmpty()) {
                for (int i = 0; i < priceList.size(); i++) {
                    priceList.get(i).setPriceName(NumberUtils.formatNumber(priceList.get(i).getPrice()));
                }
            }
            return priceList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String save() throws Exception {
        req = getRequest();
        pageForward = SALE_TO_PUNISH;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        getDataForComboxBox();
        Long channelTypeId = form.getChannelTypeId();
        Long receiverId = null;
        Long receiverType = null;
        String pricePolicy = null;
        String receiverCode = form.getReceiverCode();

        if (form.getChannelTypeId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STAFF.0009");
            return pageForward;
        }
        if (receiverCode == null || receiverCode.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.200054");
            return pageForward;
        }
        if (form.getReasonId() == null) {
            req.setAttribute(REQUEST_MESSAGE, "MSG.SAE.060"); //!!!Lỗi. Các trường bắt buộc không được để trống
            return pageForward;
        }
        if (form.getPayMethod() == null) {
            req.setAttribute(REQUEST_MESSAGE, "MSG.SAE.061"); //!!!Lỗi. Các trường bắt buộc không được để trống
            return pageForward;
        }

        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        ChannelType channelType = channelTypeDAO.findById(form.getChannelTypeId());
        if (channelType == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STAFF.0037");
            return pageForward;
        }

        String strQuery = "";
        Query query;
        List lstParam = new ArrayList();
        Shop receiverShop = null;
        Staff receiverStaff = null;
        if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            strQuery = " from Shop a ";
            strQuery += "where 1=1 and a.status=? and a.parentShopId=? and a.channelTypeId=? ";
            lstParam.add(Constant.STAFF_STATUS_ACTIVE);
            lstParam.add(userToken.getShopId());
            lstParam.add(channelType.getChannelTypeId());
            strQuery += "and lower(a.shopCode) = ? ";
            lstParam.add(receiverCode.trim().toLowerCase());
            query = getSession().createQuery(strQuery);
        } else if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
            strQuery = " from Staff a ";
            strQuery += " WHERE 1=1 and a.status=? and a.staffOwnerId = ? ";
            lstParam.add(Constant.STAFF_STATUS_ACTIVE);
            lstParam.add(userToken.getUserID());
            strQuery += "and lower(a.staffCode) = ? ";
            lstParam.add(receiverCode.trim().toLowerCase());
            query = getSession().createQuery(strQuery);
        } else {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STAFF.0037");
            return pageForward;
        }

        for (int idx = 0; idx < lstParam.size(); idx++) {
            query.setParameter(idx, lstParam.get(idx));
        }
        List lstReceiver = query.list();
        if (lstReceiver != null && !lstReceiver.isEmpty()) {
            if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                receiverShop = (Shop) lstReceiver.get(0);
                receiverId = receiverShop.getShopId();
                receiverType = Constant.OWNER_TYPE_SHOP;
                pricePolicy = receiverShop.getPricePolicy();
                receiverCode = receiverShop.getShopCode();
            } else if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
                receiverStaff = (Staff) lstReceiver.get(0);
                receiverId = receiverStaff.getStaffId();
                receiverType = Constant.OWNER_TYPE_STAFF;
                pricePolicy = receiverStaff.getPricePolicy();
                receiverCode = receiverStaff.getStaffCode();
            }
        }
        if (receiverShop == null && receiverStaff == null) {
            req.setAttribute(REQUEST_MESSAGE, "E.200055");
            return pageForward;
        }

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(LIST_GOOD);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            resetSaleTransDetailForm();
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.088"); // Danh sách hàng hoá rỗng!
            return pageForward;
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
            return pageForward;
        }
        //end sonbc2
        /* Kiem tra co cung ma KPP hay khong */
        if (!DataUtils.safeEqual(((SaleTransDetailV2Bean) lstGoods.get(0)).getReceiverId(), receiverId) || !DataUtils.safeEqual(((SaleTransDetailV2Bean) lstGoods.get(0)).getReceiverType(), receiverType)) {
            req.setAttribute(REQUEST_MESSAGE, "E.200053");//Khong cung ma KPP
            return pageForward;
        }

        for (SaleTransDetailV2Bean bean : lstGoods) {
            if (DataUtils.safeEqual(bean.getCheckSerial(), 1L)) {
                if (bean.getLstSerial() == null || bean.getLstSerial().isEmpty()) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.068");
                    return pageForward;
                }
            }
        }

        /* GOI LIB LUU THONG TIN GIAO DICH BAN HANG */
        com.viettel.lib.database.DAO.WebServiceDAO ws = new com.viettel.lib.database.DAO.WebServiceDAO();
        com.viettel.lib.sm.database.BO.SaleTrans saleTrans = new com.viettel.lib.sm.database.BO.SaleTrans();
        if (DataUtils.safeEqual(receiverType, Constant.OWNER_TYPE_STAFF)) {
            saleTrans.setShopId(userToken.getShopId());
            saleTrans.setStaffId(receiverId);
        } else {
            saleTrans.setShopId(receiverId);
            saleTrans.setStaffId(null);
        }
        saleTrans.setCreateStaffId(userToken.getUserID());
        saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_PUNISH);//ban phat
        saleTrans.setIsdn(null);
        saleTrans.setCustName(receiverStaff != null ? receiverStaff.getStaffCode() + " - " + receiverStaff.getName() : receiverShop.getShopCode() + " - " + receiverShop.getName());

        saleTrans.setReasonId(form.getReasonId());
        saleTrans.setPayMethod(form.getPayMethod());
        saleTrans.setChannel(1L);

        List<com.viettel.lib.sm.database.client.bean.StockModelBean> lstStockModelBean = new ArrayList();
        for (SaleTransDetailV2Bean bean : lstGoods) {
            com.viettel.lib.sm.database.client.bean.StockModelBean stockModelBean = new com.viettel.lib.sm.database.client.bean.StockModelBean();
            stockModelBean.setStockModelCode(bean.getStockModelCode());
            stockModelBean.setQuantity(bean.getQuantity());
            stockModelBean.setPriceId(bean.getPriceId());

            List<StockTransSerial> lstSerial = bean.getLstSerial();
            List<com.viettel.lib.sm.database.client.bean.StockSerialBean> lstStockSerialBean = new ArrayList();
            if (lstSerial != null) {
                for (StockTransSerial beanSerial : lstSerial) {
                    com.viettel.lib.sm.database.client.bean.StockSerialBean stockSerialBean = new StockSerialBean();
                    stockSerialBean.setFromSerial(beanSerial.getFromSerial());
                    stockSerialBean.setToSerial(beanSerial.getToSerial());
                    stockSerialBean.setQuantity(beanSerial.getQuantity());
                    lstStockSerialBean.add(stockSerialBean);
                }
                stockModelBean.setLstStockSerialBean(lstStockSerialBean);
            }
            lstStockModelBean.add(stockModelBean);
        }
        saleTrans.setLstStockModelBean(lstStockModelBean);

        /* Goi lib xu ly */
        com.viettel.lib.sm.database.client.bean.Result result = ws.saveSaleTrans(getSession("sm_lib"), saleTrans);

        /* Thong bao ket qua luu thong tin giao dich */
        if (result.getCode() != null && !result.getCode().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, result.getCode());
            getSession("sm_lib").getTransaction().rollback();
            System.out.println(result.getDescription());
        } else {
            req.setAttribute(Constant.RETURN_MESSAGE, "M.200006");
            getSession("sm_lib").getTransaction().commit();
            form.setSaleTransId(saleTrans.getSaleTransId());

            try {
                AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
                AccountAgent accountAgent = null;
                accountAgent = accountAgentDAO.findByOwnerIdAndOwnerType(getSession(), receiverId, receiverType);
                if (accountAgent != null
                        && accountAgent.getCheckIsdn() != null
                        && accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)
                        && accountAgent.getMsisdn() != null) {
                    String sendMess = "";//String.format(getText("sms.2003"), NumberUtils.formatNumber(amountDeposit), NumberUtils.formatNumber(amountTax));
                    SmsClient.sendSMS155(accountAgent.getMsisdn(), sendMess);
                }
            } catch (Exception ex) {
            }
        }

        return pageForward;
    }

}
