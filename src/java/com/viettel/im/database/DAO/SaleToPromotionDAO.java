/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.AppParamsBean;
import com.viettel.im.client.bean.SaleTransDetailBean;
import com.viettel.im.client.bean.ViewPackageCheck;
import com.viettel.im.client.form.SaleToCollaboratorForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.PackageGoodsDetail;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.Promotion;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.SaleTransSerial;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.StockTotalFull;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.DAO.StockTotalAuditDAO.SourceType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.ResourceBundle;

/**
 *
 * @author User
 */
public class SaleToPromotionDAO extends BaseDAOAction{

    private static final Log log = LogFactory.getLog(SaleToPromotionDAO.class);
    private static final String UPDATE_LIST_MODEL_PRICE = "updateListModelPrice";
    private static final String SALE_TO_PROMOTION = "SaleToPromotion";
    private SaleToCollaboratorForm saleToCollaboratorForm = new SaleToCollaboratorForm();
    private static final Long[] STAFF_AGENT_TYPE = new Long[]{3L, 10L};
    private static final String LIST_GOOD_SESSION_OBJ = "lstGoods";
    private static final String NEW_TYPE = "new";
    private static final String UPDATE_TYPE = "update";
    //private static final String PRICE_TYPE_SHOP_PROMOTION = "9";
    private static final String SALE_TRANS_PROMOTION_TYPE = "5";
    private static final String SALE_TRANS_CREATED_NOTINVOICE_STATUS = "2";
    private static final String SALE_TRANS_CHECK_STOCK = "0";
    private static final Long PROMOTION_RATE_TYPE = 1L;
    private static final Long PROMOTION_AMOUNT_TYPE = 2L;
    private static final String SALE_TO_PROMOTION_DECLARE = "saleToPromotionDeclare";
    private static final String SALE_TO_PROMOTION_INPUT_UPDATE = "saleToPromotionInputUpdateGoods";
    private static final String STOCK_MODEL_UNIT = "STOCK_MODEL_UNIT";
    private static final String SALE_TO_PROMOTION_GOOD_LIST = "saleToPromotionGoodList";
    private static final String REQUEST_MESSAGE = "returnMsg";

    public SaleToCollaboratorForm getSaleToCollaboratorForm() {
        return saleToCollaboratorForm;
    }

    public void setSaleToCollaboratorForm(SaleToCollaboratorForm saleToCollaboratorForm) {
        this.saleToCollaboratorForm = saleToCollaboratorForm;
    }

    private void removeSession() {
        reqSession.setAttribute("amountNotTax", null);
        reqSession.setAttribute("tax", null);
        reqSession.setAttribute("promotion", null);
        reqSession.setAttribute("amount", null);
        reqSession.setAttribute("salerId", null);
        reqSession.setAttribute("promotionAmount", null);
        reqSession.setAttribute("discout", null);
    }

    public String prepareSaleToPromotion() throws Exception {
        log.info("Begin method prepareSaleToRetail");
        getReqSession();

        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        String pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                /**
                 * Get shop ID
                 */
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());
                Long shopId = userToken.getShopId();
                if (shopId == null) {
                    log.debug("WEB:. User has no shop");
                    pageForward = Constant.ERROR_PAGE;
                    return pageForward;
                }
//                session.setAttribute("shopId", shopId);
//                StaffDAO staffDAO = new StaffDAO();
//                staffDAO.setSession(getSession());
//                staffDAO.setChannelTypeIdFilter(STAFF_AGENT_TYPE);
//                List<Staff> staffList = staffDAO.getSaleCollaboratorAndAgent(shopId);
//                session.setAttribute("staffList", staffList);
                /**
                 * List of pay method
                 */
//                AppParamsDAO appParamsDAO = new AppParamsDAO();
//                appParamsDAO.setSession(getSession());
//                List payMethodList = appParamsDAO.findAppParamsList("PAY_METHOD", "1");
//                session.setAttribute("listPayMethod", payMethodList);
                /**
                 * List of reason
                 */
                ReasonDAO reasonDAO = new ReasonDAO();
                reasonDAO.setSession(getSession());
                Long[] saleTransType = new Long[1];
                saleTransType[0] = Long.parseLong(Constant.SALE_TRANS_TYPE_PROMOTION);
                List reasonList = reasonDAO.getReasonBySaleTransType(saleTransType);
//                ReasonDAO reasonDAO = new ReasonDAO();
//                reasonDAO.setSession(getSession());
//                List reasonList = reasonDAO.findByPropertyWithStatus("REASON_TYPE", "BHKM", ACTIVE_STATUS);
                reqSession.setAttribute("reasonList", reasonList);
                /**
                 * List of telecom service
                 */
//                TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
//                telecomServiceDAO.setSession(getSession());
//                List<TelecomService> telecomList = telecomServiceDAO.findByProperty(TelecomServiceDAO.STATUS, 1L);
//                session.setAttribute("telecomList", telecomList);
                /**
                 * Reset list
                 */
                //session.setAttribute(LIST_GOOD_SESSION_OBJ, null);
                removeTabSession(LIST_GOOD_SESSION_OBJ);
                reqSession.setAttribute("actionType", NEW_TYPE);
                saleToCollaboratorForm.setDateSale(DateTimeUtils.getSysdateForWeb());
//                removeSession();
                pageForward = SALE_TO_PROMOTION;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        reqSession.setAttribute("priceList", null);

        log.info("End method prepareSaleToRetail");
        return pageForward;
    }

    public String saveSaleTransRetail() throws Exception {
        log.info("# Begin saveSaleTransRetail");
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        try {
            List<SaleTransDetailBean> lstGoods = (List) getTabSession(LIST_GOOD_SESSION_OBJ);
            if (lstGoods == null || lstGoods.isEmpty()) {
                req.setAttribute("returnMsg", "saleRetail.warn.NotStock");
                return SALE_TO_PROMOTION;
            }
            /*
             *add by sonbc2
             *date: 11/04/2016
             */
            ResourceBundle resource = ResourceBundle.getBundle("config");
            if (lstGoods.size() > Integer.parseInt(resource.getString("MAX_OF_GOODS"))) {
                req.setAttribute("returnMsg", "sale.warn.Exceeds");
                List listMessageParam = new ArrayList();
                listMessageParam.add(resource.getString("MAX_OF_GOODS"));
                req.setAttribute("returnMsgParam", listMessageParam);
                return SALE_TO_PROMOTION;
            }
            //end sonbc2
            this.sum(lstGoods);//tinh lai tong tien giao dich

            if ((saleToCollaboratorForm.getCustName() == null) || (saleToCollaboratorForm.getCustName().equals(""))) {
                req.setAttribute("returnMsg", "saleRetail.warn.cust");
                return SALE_TO_PROMOTION;
            }
            if ((saleToCollaboratorForm.getDateSale() == null) || (saleToCollaboratorForm.getDateSale().equals(""))) {
                req.setAttribute("returnMsg", "saleRetail.warn.saleDate");
                return SALE_TO_PROMOTION;
            }
            if ((saleToCollaboratorForm.getReasonId() == null) || (saleToCollaboratorForm.getReasonId().equals(0L))) {
                req.setAttribute("returnMsg", "saleRetail.warn.reason");
                return SALE_TO_PROMOTION;
            }
//            if ((saleToCollaboratorForm.getPayMethodId() == null) || (saleToCollaboratorForm.getPayMethodId().equals(""))) {
//                req.setAttribute("returnMsg", "saleRetail.warn.pay");//
//                return SALE_TO_PROMOTION;
//            }
//            List<SaleTransDetailBean> lstGoods = (List) req.getSession().getAttribute(LIST_GOOD_SESSION_OBJ);


            boolean selectedSerial = true;
            for (SaleTransDetailBean saleTransDetailBean : lstGoods) {
                if (saleTransDetailBean.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                    if (saleTransDetailBean.getLstSerial() == null || saleTransDetailBean.getLstSerial().size() == 0) {
                        selectedSerial = false;
                        break;
                    }
                }
            }

            if (!selectedSerial) {

                req.setAttribute("returnMsg", "saleRetail.warn.serial");

                return SALE_TO_PROMOTION;
            }

            //Check dieu kien neu ban goi hang thi da du chua
            List<ViewPackageCheck> listView = new ArrayList<ViewPackageCheck>();
            for (int i = 0; i < lstGoods.size(); i++) {
                SaleTransDetailBean saleTransDetailBean = (SaleTransDetailBean) lstGoods.get(i);
                ViewPackageCheck viewPackageCheck = new ViewPackageCheck();
                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(this.getSession());
                StockModel stockModel = stockModelDAO.findById(saleTransDetailBean.getStockModelId());
                viewPackageCheck.setStockModelId(stockModel.getStockModelId());
                viewPackageCheck.setStockModelCode(stockModel.getStockModelCode());
                viewPackageCheck.setQuantity(saleTransDetailBean.getQuantity());
                listView.add(viewPackageCheck);
            }
            String outPut = checkPackageGoodsToSaleTrans(listView);
            if (!"".equals(outPut)) {
                for (int i = 0; i < lstGoods.size(); i++) {
                    SaleTransDetailBean saleTransDetailBean = (SaleTransDetailBean) lstGoods.get(i);
                    PackageGoodsDetailDAO packageGoodsDetailDAO = new PackageGoodsDetailDAO();
                    List<PackageGoodsDetail> listPackageGoodsDetail = packageGoodsDetailDAO.findByStockModelId(saleTransDetailBean.getStockModelId());
                    if (listPackageGoodsDetail.size() != 0) {
                        req.setAttribute("returnMsg", outPut);
                        return SALE_TO_PROMOTION;
                    }
                }
//                req.setAttribute("lstGoods", lstGoods);
//                logEndUser(startTime, new Date(), function, callId, userToken.getLoginName(), outPut);
//                return SALE_TO_PROMOTION;
            }
            //Ghi file log loi
            boolean noError = true;

            if (!validateSaleTrans()) {
                req.setAttribute("global", true);
                log.info("End. Validate false");
                return SALE_TO_PROMOTION;
            }
            //Khoi tao tong tien han muc
//            Long amountDebit = 0L;
//            String[] arrMess = new String[3];

            //save giao dich ban hang
            SaleTrans saleTrans = expSaleTrans();

            if (saleTrans == null) {
                return SALE_TO_PROMOTION;
            }

            for (int i = 0; i < lstGoods.size(); i++) {
                SaleTransDetailBean saleTransDetailBean = (SaleTransDetailBean) lstGoods.get(i);

                //save hang hoa ban
                SaleTransDetail saleTransDetail = expSaleTransDetail(saleTrans, saleTransDetailBean);

                if (saleTransDetail == null) {
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    log.debug("# End method addSerial");
                    return SALE_TO_PROMOTION;
                }

//                ShopDAO shopDAO = new ShopDAO();
//                shopDAO.setSession(getSession());
//                Shop shop = shopDAO.findById(userToken.getShopId());
//                String pricePolicy = shop.getPricePolicy();

//                PriceDAO priceDAO = new PriceDAO();
//                priceDAO.setSession(getSession());
//                Long price = priceDAO.findBasicPrice(saleTransDetail.getStockModelId(), pricePolicy);
//                if (price == null && checkStockOwnerTmpDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF)) {
//                    noError = false;
//                    req.setAttribute("lstGoods", lstGoods);
//                    req.setAttribute("returnMsg", "ERR.SAE.143");
//                    getSession().clear();
//                    getSession().getTransaction().rollback();
//                    getSession().beginTransaction();
//                    log.debug("# End method addSerial");
//                    return SALE_TO_PROMOTION;
//                } else
                {
                    //tru han muc so luong
//                    if (price == null) {
//                        price = 0L;
//                    }
//                    amountDebit += price * saleTransDetail.getQuantity().longValue();
//                    arrMess = new String[3];
//                    arrMess = reduceDebitTotal(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, saleTransDetail.getStockModelId(),
//                            Constant.STATE_NEW, Constant.STATUS_DEBIT_DEPOSIT, saleTransDetail.getQuantity().longValue(), false, null);
//                    if (!arrMess[0].equals("")) {
//                        req.setAttribute("returnMsg", getText(arrMess[0]));
//                        getSession().clear();
//                        getSession().getTransaction().rollback();
//                        getSession().beginTransaction();
//                        log.debug("# End method addSerial");
//                        return SALE_TO_PROMOTION;
//                    }

                    if (userToken == null) {
                        log.info("WEB. Session time out. UserToken is missing");
                        return Constant.ERROR_PAGE;
                    }

                    Long staffId = userToken.getUserID();
                    if (staffId != null) {
                        /**
                         * Thuc hien tru kho
                         */
                        //R499 Trung dh3  comment and add
//                    noError = stockTotal(Constant.OWNER_TYPE_STAFF, staffId, Constant.STATUS_USE,
//                            saleTransDetailBean.getStockModelId(), saleTransDetailBean.getQuantity());
                    GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), staffId, Constant.OWNER_TYPE_STAFF,
                            saleTransDetailBean.getStockModelId(), Constant.STATUS_USE, -saleTransDetailBean.getQuantity(), -saleTransDetailBean.getQuantity(), 0L,
                            userToken.getUserID(), saleTrans.getReasonId(), saleTrans.getSaleTransId(),
                            saleTrans.getSaleTransCode(), saleTrans.getSaleTransType(), SourceType.SALE_TRANS);
                    noError = genResult.isSuccess();
                      //R499 Trung dh3  comment and add end
                    }
                    if (!noError) {
                        req.setAttribute("returnMsg", "ERR.SAE.074");
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        log.debug("# End method addSerial");
                        return SALE_TO_PROMOTION;
                    }
                    /**
                     * Thuc hien luu serial
                     */
                    if (saleTransDetailBean.getCheckSerial() != null && saleTransDetailBean.getCheckSerial().equals(1L)) {
                        noError = saveStockTransSerial(saleTransDetailBean, saleTrans, userToken.getUserID(), saleTransDetail);

                        if (!noError) {
                            req.setAttribute("returnMsg", "ERR.SAE.078");
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            log.debug("# End method addSerial");
                            return SALE_TO_PROMOTION;
                        }
                    }

                }
            }


            //tru han muc tong tien
//            arrMess = new String[3];
//            arrMess = reduceDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, amountDebit, false, null);
//            if (!arrMess[0].equals("")) {
//            req.setAttribute("returnMsg", getText(arrMess[0]));
//            getSession().clear();
//            getSession().getTransaction().rollback();
//            getSession().beginTransaction();
//            log.debug("# End method addSerial");
//            return SALE_TO_PROMOTION;
//            }

            //tinh tong gia tri hang hoa trong giao dich ban hang
            /**
             * Modified by TrongLV Modify date 2011-09-22 Purpose : reduce
             * current value of goods
             */
            Double saleTransAmount = sumAmountByGoodsList(lstGoods);
            if (saleTransAmount != null && saleTransAmount >= 0) {
                //Cap nhat lai gia tri hang hoa cua nhan vien
                if (!addCurrentDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, -1 * saleTransAmount)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.LIMIT.0001"));
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().getTransaction().begin();
                    return SALE_TO_PROMOTION;
                }
            } else {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.LIMIT.0014"));
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().getTransaction().begin();
                return SALE_TO_PROMOTION;
            }

            //session.removeAttribute("lstGoods");
            removeTabSession("lstGoods");

            removeSession();

            /**
             * Reset form
             */
            saleToCollaboratorForm = new SaleToCollaboratorForm();
            reqSession.setAttribute("actionType", NEW_TYPE);

            /**
             * Set current date
             */
            String currentDate = DateTimeUtils.getSysdate();

            saleToCollaboratorForm.setDateSale(currentDate);


            req.setAttribute("returnMsg", "saleRetail.warn.makeSaleService");

            req.setAttribute("global", true);

            log.info("# End saveSaleTransRetail");
            return SALE_TO_PROMOTION;


        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            return SALE_TO_PROMOTION;
        }
    }

    public boolean saveStockTransSerial(SaleTransDetailBean saleTransDetailBean, SaleTrans saleTrans,
            Long userId, SaleTransDetail saleTransDetail)
            throws Exception {
        Long stockModelId = 0L;
        //Long goodsStatus = 0L;
        List lstSerial = null;
        BaseStockDAO baseStockDAO = new BaseStockDAO();
        baseStockDAO.setSession(this.getSession());
        boolean noError = true;
        stockModelId = saleTransDetailBean.getStockModelId();
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(getSession());
        StockModel stockModel = stockModelDAO.findById(stockModelId);
        if (stockModel == null || stockModel.getStockModelId() == null) {
            return false;
        }
        //goodsStatus = saleTransDetailBean.getStateId();
        lstSerial = saleTransDetailBean.getLstSerial();
        //Luu chi tiet serial
        if (lstSerial != null && lstSerial.size() > 0) {
            StockTransSerial stockSerial = null;
            //SerialGoods serialGoods = null;
            for (int idx = 0; idx
                    < lstSerial.size(); idx++) {
                stockSerial = (StockTransSerial) lstSerial.get(idx);
                BigInteger startSerialTemp = new BigInteger(stockSerial.getFromSerial());
                BigInteger endSerialTemp = new BigInteger(stockSerial.getToSerial());
                Long totals = endSerialTemp.subtract(startSerialTemp).longValue() + 1;
                SaleTransSerial saleTransSerial = new SaleTransSerial();
                saleTransSerial.setSaleTransSerialId(getSequence("SALE_TRANS_SERIAL_SEQ"));
                //saleTransSerial.setSaleTransDetail(saleTransDetail);
                //ThanhNC modify
                saleTransSerial.setSaleTransDetailId(saleTransDetail.getSaleTransDetailId());
                saleTransSerial.setStockModelId(stockModelId);
                saleTransSerial.setFromSerial(stockSerial.getFromSerial());
                saleTransSerial.setToSerial(stockSerial.getToSerial());
                saleTransSerial.setQuantity(totals);
                Date createDate = new Date();
                saleTransSerial.setSaleTransDate(createDate);
                this.getSession().save(saleTransSerial);

                if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)) {
                    VcRequestDAO vcRequestDAO = new VcRequestDAO();
                    vcRequestDAO.setSession(getSession());
                    vcRequestDAO.saveVcRequest(saleTransDetail.getSaleTransDate(), stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.REQUEST_TYPE_SALE_PROMOTION, saleTransDetail.getSaleTransId());
                }

                if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_KIT)) {
                    ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                    reqActiveKitDAO.setSession(getSession());
                    reqActiveKitDAO.saveReqActiveKit(stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.SALE_TYPE, saleTransDetail.getSaleTransId(),
                            Long.parseLong(Constant.SALE_TRANS_TYPE_PROMOTION), getSysdate());
                }
            }
        }
        /**
         * Staff or Shop
         */
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            log.info("WEB. Session time out. UserToken is missing");
            return false;
        }
        Long staffId = userToken.getUserID();
        if (staffId != null) {
            noError = baseStockDAO.updateSeialExp(lstSerial, saleTransDetailBean.getStockModelId(),
                    Constant.OWNER_TYPE_STAFF, staffId, Constant.STATUS_USE, Constant.STATUS_SALED, saleTransDetail.getQuantity().longValue(), null);
        }
        return noError;
    }

    public boolean stockTotal(Long ownerType, Long ownerId, Long stateId, Long modelId, Long quantity) {
        log.info("Begin.");
        try {
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(getSession());
            stockCommonDAO.expStockStaffTotal(ownerType, ownerId, stateId, modelId, quantity);
            log.info("End.");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            return false;
        }
    }

    public SaleTransDetail expSaleTransDetail(SaleTrans saleTrans, SaleTransDetailBean saleTransDetailBean) {

        try {
            SaleTransDetail saleTransDetail = new SaleTransDetail();
            Long saleTransDetailId = getSequence("SALE_TRANS_DETAIL_SEQ");
            saleTransDetail.setSaleTransDetailId(saleTransDetailId);
            //saleTransDetail.setSaleTrans(saleTrans);
            //ThanhNC modify
            saleTransDetail.setSaleTransId(saleTrans.getSaleTransId());
            saleTransDetail.setSaleTransDate(getSysdate());
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(saleTransDetailBean.getStockModelId());
            saleTransDetail.setStockModelId(stockModel.getStockModelId());
            saleTransDetail.setStateId(Constant.STATE_NEW);
            saleTransDetail.setPriceId(saleTransDetailBean.getPriceId());
            saleTransDetail.setQuantity(saleTransDetailBean.getQuantity());
            saleTransDetail.setPromotionId(saleTransDetailBean.getPromotionId());

            //Tien khuyen mai bang tong tien phai tra
            saleTransDetail.setPromotionAmount(saleTransDetailBean.getAmount());
            saleTransDetail.setAmount(saleTransDetailBean.getAmount());

            saleTransDetail.setTransferGood("1");
            saleTransDetail.setNote(saleTransDetailBean.getNote());
            //tamdt1, start, 15/07/2010
            //cap nhat them cac truong can thiet phuc vu bao cao doanh thu
            if (stockModel != null) {
                saleTransDetail.setStockModelCode(stockModel.getStockModelCode());
                saleTransDetail.setStockModelName(stockModel.getName());
            } else {
                //khong tim thay mat hang
                return null;
            }
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(this.getSession());
            Price price = priceDAO.findById(saleTransDetailBean.getPriceId());
            if (price != null) {
                if (!Constant.PRICE_AFTER_VAT) {//luu gia truoc thue
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

            saleTransDetail.setDiscountAmount(0.0);//chiet khau

            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(stockModel.getStockTypeId());
            if (stockType != null) {
                saleTransDetail.setStockTypeId(stockType.getStockTypeId());
                saleTransDetail.setStockTypeName(stockType.getName());
            } else {
                //khong tim thay gia
                return null;
            } //tamdt1, end

            saleTransDetail.setDiscountAmount(0.0);//chiet khau
            saleTransDetail.setAmountBeforeTax(saleTransDetail.getPrice() * saleTransDetailBean.getQuantity());
            saleTransDetail.setAmount(saleTransDetail.getPrice() * saleTransDetailBean.getQuantity());
            saleTransDetail.setAmountTax(saleTransDetail.getAmountBeforeTax() * (price.getVat() / 100));//tien thue
            saleTransDetail.setVatAmount(saleTransDetail.getAmountBeforeTax() * (price.getVat() / 100));//tien thue
            saleTransDetail.setAmountAfterTax(saleTransDetail.getAmountBeforeTax() + saleTransDetail.getAmountTax());//tien thue                       
            saleTransDetail.setNote(saleTransDetailBean.getNote());
            this.getSession().save(saleTransDetail);
            return saleTransDetail;
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            return null;
        }
    }

    public SaleTrans expSaleTrans() {
        try {
            SaleTrans saleTrans = new SaleTrans();
            //Thong tin chung
            saleTrans.setSaleTransId(getSequence("SALE_TRANS_SEQ"));
            saleTrans.setSaleTransDate(getSysdate());

            saleTrans.setSaleTransType(SALE_TRANS_PROMOTION_TYPE);
            saleTrans.setStatus(SALE_TRANS_CREATED_NOTINVOICE_STATUS);
            saleTrans.setCheckStock(SALE_TRANS_CHECK_STOCK);

            saleTrans.setCustName(saleToCollaboratorForm.getCustName());
            saleTrans.setCompany(saleToCollaboratorForm.getCompany());
            saleTrans.setTin(saleToCollaboratorForm.getTin());
            saleTrans.setAddress(saleToCollaboratorForm.getAddress());
            saleTrans.setTelNumber(saleToCollaboratorForm.getTeleNumber());
            saleTrans.setNote(saleToCollaboratorForm.getNoteSaleTrans());
            saleTrans.setSaleTransCode(CommonDAO.formatTransCode(saleTrans.getSaleTransId()));


            UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
            saleTrans.setShopId(userToken.getShopId());
            saleTrans.setStaffId(userToken.getUserID());

            saleTrans.setReasonId(saleToCollaboratorForm.getReasonId());
            saleTrans.setPayMethod(saleToCollaboratorForm.getPayMethodId());

            saleTrans.setAmountNotTax(saleToCollaboratorForm.getAmountTax());
            saleTrans.setAmountTax(saleToCollaboratorForm.getAmountTax());
            saleTrans.setPromotion(saleToCollaboratorForm.getAmountTax());
            saleTrans.setTax(0.0);
            saleTrans.setVat(0.0);
            saleTrans.setDiscount(0.0);

            //telecom_service_id
            List<SaleTransDetailBean> lstGoods = (List) getTabSession(LIST_GOOD_SESSION_OBJ);
            if (lstGoods != null && !lstGoods.isEmpty() && lstGoods.get(0).getTelecomServiceID() != null) {
                saleTrans.setTelecomServiceId(lstGoods.get(0).getTelecomServiceID());

                PriceDAO priceDAO = new PriceDAO();
                priceDAO.setSession(this.getSession());
                Price price = priceDAO.findById(lstGoods.get(0).getPriceId());
                if (price != null) {
                    saleTrans.setCurrency(price.getCurrency());
                    saleTrans.setVat(price.getVat());
                } else {
                    //khong tim thay gia
                    return null;
                }
            } else {
                return null;
            }

            this.getSession().save(saleTrans);
            return saleTrans;
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            return null;
        }
    }

    private boolean validateSaleTrans() {
        log.info("Begin.");
        boolean result = true;
        Long salerId = (Long) reqSession.getAttribute("salerId");
        if (saleToCollaboratorForm.isOther()) {
            if (salerId == null) {
                req.setAttribute("returnMsg", "saleRetail.warn.NotStaff");
                result = false;
            }
        }
        if (!StringUtils.validString(saleToCollaboratorForm.getCustName())) {
            req.setAttribute("returnMsg", "saleRetail.warn.cust");
            result = false;
        }
        if (!StringUtils.validString(saleToCollaboratorForm.getDateSale())) {
            req.setAttribute("returnMsg", "saleRetail.warn.saleDate");
            result = false;
        }
//        if (!StringUtils.validString(saleToCollaboratorForm.getPayMethodId())) {
//            req.setAttribute("returnMsg", "saleRetail.warn.pay");
//
//
//            result = false;
//        }
        if (saleToCollaboratorForm.getReasonId() == null) {
            req.setAttribute("returnMsg", "saleRetail.warn.reason");
            result = false;
        }
        if (salerId != null) {
            saleToCollaboratorForm.setSalerId(salerId);
        }
        log.info("End.");
        return result;
    }
    private Map priceListMap = new HashMap();

    public String updateStockPromotionPrice_BK() throws Exception {
        log.info("Begin.");
        try {
            getReqSession();
            /**
             * Get shop ID
             */
            Long shopId = (Long) reqSession.getAttribute("shopId");
            if (shopId == null) {
                log.info("WEB:. Session time out");
                return "success";
            }
            String stockModelIdTemp = (String) req.getParameter("stockModelId");
            if (stockModelIdTemp == null || stockModelIdTemp.equals("")) {
                log.info("WEB:. Stock model id was missing");
                return "success";
            }
            Long stockModelId = new Long(stockModelIdTemp);
            /**
             * Get price list
             */
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
            priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_PROMOTION);

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(shopId);

            String pricePolicy = shop.getPricePolicy();
            priceDAO.setPricePolicyFilter(pricePolicy);

            priceDAO.setStockModelIdFilter(stockModelId);

            //List priceList = priceDAO.findPriceForSaleRetail();
            priceDAO.setPriceManyFilter(Constant.SELECT_PRICE_TYPE_SHOP_PROMOTION);
            List priceList = priceDAO.findManyPriceForSalePromotion();
            List tmpList = new ArrayList();
            tmpList.addAll(priceList);
            priceListMap.put("priceId", tmpList);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("END.");
        return "success";
    }

    /**
     *
     * author : tamdt1 date : 17/09/2010 desc : thay the ham
     * updateStockPromotionPrice do chuyen sang chon mat hang bang F9
     *
     */
    public String updateListModelPrice() throws Exception {
        log.info("Begin method updateListModelPrice of SaleToPromotionDAO...");

        try {
            getReqSession();
            UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
            Long shopId = userToken.getShopId();
            if (shopId == null) {
                List tmpList = new ArrayList();
                String[] header = {"", getText("MSG.SAE.024")};
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);

                log.info("End method updateListModelPrice of SaleToPromotionDAO");
                return UPDATE_LIST_MODEL_PRICE;
            }

            String stockModelCodeF9 = (String) req.getParameter("stockModelCode");
            if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("")) {
                List tmpList = new ArrayList();
                String[] header = {"", getText("MSG.SAE.024")};
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);

                log.info("End method updateListModelPrice of SaleToRetailDAO");
                return UPDATE_LIST_MODEL_PRICE;
            }

            StockModelDAO stockModelDAOF9 = new StockModelDAO();
            stockModelDAOF9.setSession(this.getSession());
            StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9.trim());
            if (stockModelF9 == null) {
                List tmpList = new ArrayList();
                String[] header = {"", getText("MSG.SAE.024")};
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);

                log.info("End method updateListModelPrice of SaleToRetailDAO");
                return UPDATE_LIST_MODEL_PRICE;
            }

            //lay danh sach gia cua mat hang
            Long stockModelId = stockModelF9.getStockModelId();
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
            priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_PROMOTION);

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(shopId);

            String pricePolicy = shop.getPricePolicy();
            priceDAO.setPricePolicyFilter(pricePolicy);

            priceDAO.setStockModelIdFilter(stockModelId);

            //List priceList = priceDAO.findPriceForSaleRetail();
            priceDAO.setPriceManyFilter(Constant.SELECT_PRICE_TYPE_SHOP_PROMOTION);
            List priceList = priceDAO.findManyPriceForSalePromotion();
            List tmpList = new ArrayList();
            tmpList.addAll(priceList);
            priceListMap.put("priceId", tmpList);

        } catch (Exception e) {
            e.printStackTrace();
            List tmpList = new ArrayList();
            String[] header = {"", getText("MSG.SAE.024")};
            tmpList.add(header);
            priceListMap.put("priceId", tmpList);
        }

        log.info("End method updateListModelPrice of SaleToPromotionDAO");
        return UPDATE_LIST_MODEL_PRICE;
    }

    public Map getPriceListMap() {
        return priceListMap;
    }

    public void setPriceListMap(Map priceListMap) {
        this.priceListMap = priceListMap;
    }

    public String changActionType() throws Exception {
        log.info("Begin.");
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        String pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                /**
                 * Reset form
                 */
                saleToCollaboratorForm = new SaleToCollaboratorForm();
                reqSession.setAttribute("actionType", NEW_TYPE);
                pageForward = SALE_TO_PROMOTION_DECLARE;
            } catch (Exception e) {
                log.debug("WEB. " + e.toString());
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End.");
        return pageForward;
    }

    public String updateGoods() throws Exception {
        log.info("# Begin method delGoods");
        String pageForward = SALE_TO_PROMOTION_INPUT_UPDATE;
        getReqSession();
//        List lstGoods = (List) session.getAttribute(LIST_GOOD_SESSION_OBJ);
        List lstGoods = (List) getTabSession(LIST_GOOD_SESSION_OBJ);
        if (lstGoods == null) {
            req.setAttribute("returnMsg", "saleRetail.warn.Empty");
            return pageForward;
        }
        Long stockModelId = saleToCollaboratorForm.getStockModelId();
        if (stockModelId == null) {
            log.info("WEB. Session time out. stockModelId is missing");
            return Constant.ERROR_PAGE;
        }
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            log.info("WEB. Session time out. UserToken is missing");
            return Constant.ERROR_PAGE;
        }

        StockTotalFull stockTotalFull = null;
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(getSession());
        List<StockTotalFull> stockTotalList = stockCommonDAO.findStockTotalFullByStockModel(
                Constant.OWNER_TYPE_STAFF, userToken.getUserID(), stockModelId);
        if (stockTotalList != null && stockTotalList.size() > 0) {
            stockTotalFull = stockTotalList.get(0);
        }

        if ((saleToCollaboratorForm.getQuantity() == null) || (saleToCollaboratorForm.getQuantity() <= 0)) {
            req.setAttribute("returnMsg", "saleRetail.warn.quantity");
            return pageForward;
        }

        if (stockTotalFull != null) {
            if (saleToCollaboratorForm.getQuantity() > stockTotalFull.getQuantityIssue()) {
                log.info("Nhap qua so luong co trong kho");
                req.setAttribute("returnMsg", "saleRetail.warn.saleQuantity");
                reserveCondition(stockModelId);
                return pageForward;
            }
        } else {
            log.info("Khong tim thay hang trong kho");
            req.setAttribute("returnMsg", "saleRetail.warn.saleQuantity");
            reserveCondition(stockModelId);
            return pageForward;
        }
        for (int i = 0; i
                < lstGoods.size(); i++) {
            if (lstGoods.get(i) instanceof SaleTransDetailBean) {
                SaleTransDetailBean saleTransDetailBean = (SaleTransDetailBean) lstGoods.get(i);
                if (saleTransDetailBean.getStockModelId().equals(stockModelId)) {
                    saleTransDetailBean.setQuantity(saleToCollaboratorForm.getQuantity());
                    saleTransDetailBean.setNote(saleToCollaboratorForm.getNote());
                    saleTransDetailBean.setAmount(
                            saleToCollaboratorForm.getQuantity() * saleTransDetailBean.getPrice());//For update to sale_trans_detail
                    saleTransDetailBean.setAmountTaxDisplay(
                            NumberUtils.rounđAndFormatAmount(saleToCollaboratorForm.getQuantity() * saleTransDetailBean.getPrice()));//For display to interface

                    break;
                }
            }
        }

        setTabSession(LIST_GOOD_SESSION_OBJ, lstGoods);
        /**
         * Set action type
         */
        reqSession.setAttribute("actionType", NEW_TYPE);
        /**
         * Reset form
         */
        saleToCollaboratorForm.resetForm();
        reqSession.setAttribute("priceList", null);
        sum(lstGoods);
        req.setAttribute("returnMsg", "saleRetail.warn.saveSuccess");
        log.info("# End method delGoods");
        return pageForward;
    }

    private void reserveCondition(Long stockModelId) throws Exception {
        StockModelDAO modelDAO = new StockModelDAO();
        modelDAO.setSession(this.getSession());
        modelDAO.setTelecomServiceIdFilter(saleToCollaboratorForm.getTelecomServiceId());
        /**
         * Get shop ID
         */
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            log.info("WEB. Session time out. UserToken is missing");
            return;
        }
        Long staffSalerId = null;
        staffSalerId = (Long) reqSession.getAttribute("salerId");
        if (staffSalerId != null) {
            modelDAO.setStaffSalerIdFilter(staffSalerId);
        } else {
            modelDAO.setStaffSalerIdFilter(userToken.getUserID());
        }
        List model = modelDAO.findStockModelForSaleRetail();
        List<StockModel> modelList = new ArrayList();
        Iterator iter = model.iterator();
        while (iter.hasNext()) {
            Object[] temp = (Object[]) iter.next();
            StockModel stockModel = new StockModel();
            stockModel.setStockModelId(new Long(temp[0].toString()));
            stockModel.setName(temp[1].toString());
            modelList.add(stockModel);
        }
        reqSession.setAttribute("modelList", modelList);
        /**
         * Get price list
         */
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(getSession());
        priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_PROMOTION);

        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());

        String pricePolicy = shop.getPricePolicy();
        priceDAO.setPricePolicyFilter(pricePolicy);

        priceDAO.setStockModelIdFilter(stockModelId);

        List price = priceDAO.findPriceForSaleRetail();

        List<Price> priceList = new ArrayList();

        iter = price.iterator();
        while (iter.hasNext()) {
            Object[] temp = (Object[]) iter.next();
            Price priceTemp = new Price();
            priceTemp.setPriceId(new Long(temp[0].toString()));
            priceTemp.setPrice(new Double(temp[1].toString()));
            priceList.add(priceTemp);
        }
        reqSession.setAttribute("priceList", priceList);
    }

    private void sum(List<SaleTransDetailBean> lstGoods) throws Exception {
        log.info("Begin");

        //Tinh tong tien co thue
        if (lstGoods != null) {
            Double amount = 0.0;
            for (int i = 0; i < lstGoods.size(); i++) {
                SaleTransDetailBean saleTransDetailBean = (SaleTransDetailBean) lstGoods.get(i);
                Long priceId = saleTransDetailBean.getPriceId();
                //find price
                PriceDAO priceDAO = new PriceDAO();
                priceDAO.setSession(this.getSession());
                Price price = priceDAO.findById(priceId);
                //sum notTax
                if (price != null) {
                    amount = amount + price.getPrice() * saleTransDetailBean.getQuantity();
                } else {
                    throw new Exception("Error. Promotion price is not exists!");
                }
            }

            saleToCollaboratorForm.setAmountNotTax(amount);
            saleToCollaboratorForm.setAmountNotTaxMoney(NumberUtils.rounđAndFormatAmount(amount));
            saleToCollaboratorForm.setTax(0.0);
            saleToCollaboratorForm.setTaxMoney("0");

            saleToCollaboratorForm.setDiscout(0.0);
            saleToCollaboratorForm.setDiscoutMoney("0");

            saleToCollaboratorForm.setPromotionAmount(amount);
            saleToCollaboratorForm.setPromotionAmountMoney(NumberUtils.rounđAndFormatAmount(amount));

            saleToCollaboratorForm.setAmountTax(amount);
            saleToCollaboratorForm.setAmountTaxMoney(NumberUtils.rounđAndFormatAmount(amount));
        }

        log.info("End");
    }

    public String addGoods() throws Exception {
        log.info("Begin");
        String pageForward = SALE_TO_PROMOTION_INPUT_UPDATE;
        getReqSession();
        if (reqSession == null) {
            log.info("WEB. Session time out. UserToken is missing");
            return Constant.ERROR_PAGE;
        }
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            log.info("WEB. Session time out. UserToken is missing");
            return Constant.ERROR_PAGE;
        }

        List<SaleTransDetailBean> lstGoods = (List) getTabSession(LIST_GOOD_SESSION_OBJ);
        if (lstGoods == null) {
            lstGoods = new ArrayList();
        } else {
            this.sum(lstGoods);
        }

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
            //bo sung them doan nay cua anh Trong de phan biet goi hang
            this.saleToCollaboratorForm.setItemId("0:" + stockModelF9.getStockModelId());
        }

        //--------kiem tra luong hang trong kho co du de thuc hien xuat theo so luong o tren khong
        //kiem tra truong so luong phai ko am
        if (quantityF9.compareTo(0L) <= 0) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.118"); //!!!Lỗi. Số lượng phải là số nguyên dương

            //lay danh sach gia cua mat hang
            getDataForCombobox();

            return pageForward;
        }
        StockTotalDAO stockTotalDAOF9 = new StockTotalDAO();
        stockTotalDAOF9.setSession(this.getSession());
        Long ownerTypeF9 = Constant.OWNER_TYPE_STAFF;
        Long ownerIdF9 = userToken.getUserID();

        StockTotal stockTotalF9 = stockTotalDAOF9.getStockTotal(ownerTypeF9, ownerIdF9, this.saleToCollaboratorForm.getStockModelId(), Constant.STATE_NEW);
        if (stockTotalF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ

            //lay danh sach gia cua mat hang
            getDataForCombobox();

            return pageForward;
        }

        if (quantityF9.compareTo(stockTotalF9.getQuantityIssue()) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ

            //lay danh sach gia cua mat hang
            getDataForCombobox();

            return pageForward;
        }
        //tamdt1, 16/09/2010, end
        //----------------------------------------------------------------------

        Long TelecomIDAdd = stockModelF9.getTelecomServiceId();
        boolean CheckTelecomServiceID = true;
        for (SaleTransDetailBean saleTransDetailBean : lstGoods) {
            Long TelecomID = (Long) saleTransDetailBean.getTelecomServiceID();
            if (TelecomID.compareTo(TelecomIDAdd) != 0) {
                CheckTelecomServiceID = false;
                break;
            }
        }
        if (!CheckTelecomServiceID) {
            req.setAttribute("returnMsg", "saleRetail.warn.SameTel");
            saleToCollaboratorForm.resetForm();
            return pageForward;
        }

        /**
         * Check valid good
         */
        if (!checkValidGoods(lstGoods)) {
            log.info("Ma hang hoa da bi nhap trung");
            req.setAttribute("returnMsg", "saleRetail.warn.SameStock");
            sum(lstGoods);
            saleToCollaboratorForm.resetForm();
            return pageForward;
        }

        if ((saleToCollaboratorForm.getPriceId() == null) || (saleToCollaboratorForm.getPriceId() <= 0)) {
            saleToCollaboratorForm.resetForm();
            req.setAttribute("returnMsg", "saleRetail.warn.NotPrice");
            return pageForward;
        }

        if ((saleToCollaboratorForm.getQuantity() == null) || (saleToCollaboratorForm.getQuantity() <= 0)) {
            saleToCollaboratorForm.resetForm();
            req.setAttribute("returnMsg", "saleRetail.warn.quantity");
            return pageForward;
        }

        //        Long staffSalerId = null;
//        staffSalerId = (Long) session.getAttribute("salerId");
        StockTotalFull stockTotalFull = null;
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(getSession());
        List<StockTotalFull> stockTotalList = stockCommonDAO.findStockTotalFullByStockModel(
                Constant.OWNER_TYPE_STAFF, userToken.getUserID(), saleToCollaboratorForm.getStockModelId());
        if (stockTotalList != null && stockTotalList.size() > 0) {
            stockTotalFull = stockTotalList.get(0);
        }
        if (stockTotalFull != null) {
            if (saleToCollaboratorForm.getQuantity() > stockTotalFull.getQuantityIssue()) {
                req.setAttribute("returnMsg", "saleRetail.warn.saleQuantity");
                reserveCondition(saleToCollaboratorForm.getStockModelId());
                saleToCollaboratorForm.resetForm();
                return pageForward;
            }
        } else {
            log.info("Khong tim thay hang trong kho");
            req.setAttribute("returnMsg", "saleRetail.warn.saleQuantity");
            reserveCondition(saleToCollaboratorForm.getStockModelId());
            saleToCollaboratorForm.resetForm();
            return pageForward;
        }

        //kiem tra gia lai lan nua
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(getSession());
        priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_PROMOTION);
//        Long shopId = (Long) session.getAttribute("shopId");

        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());

        String pricePolicy = shop.getPricePolicy();
        priceDAO.setPricePolicyFilter(pricePolicy);
        priceDAO.setStockModelIdFilter(saleToCollaboratorForm.getStockModelId());
        priceDAO.setPriceId(saleToCollaboratorForm.getPriceId());

        //List priceList = priceDAO.findPriceForSaleRetail();
        priceDAO.setPriceManyFilter(Constant.SALE_TRANS_TYPE_PROMOTION);
        List priceList = priceDAO.findManyPriceForSalePromotion();
        if (priceList == null || priceList.size() == 0) {
            req.setAttribute("returnMsg", "ERR.SAE.062");
            return pageForward;
        }
        
        /*120501 : TRONGLV : check gia + mat hang co khop nhau khong */
        boolean checkValidatePrice = false;
        Iterator iter = priceList.iterator();
        while (iter.hasNext()) {
            Object[] temp = (Object[]) iter.next();
            Long priceId = new Long(temp[0].toString());
            if (priceId != null && priceId.equals(saleToCollaboratorForm.getPriceId())){
                checkValidatePrice = true;
                break;
            }
        }
        
        if (!checkValidatePrice) {
            req.setAttribute("returnMsg", "ERR.SAE.062");
            reserveCondition(saleToCollaboratorForm.getStockModelId());
            return pageForward;
        }
        /*120501 : TRONGLV : check gia + mat hang co khop nhau khong */

        SaleTransDetailBean saleTransDetailBean = new SaleTransDetailBean();
        /**
         * Set price
         */
        Price price = priceDAO.findById(saleToCollaboratorForm.getPriceId());
        saleTransDetailBean.setPrice(price.getPrice());
        saleTransDetailBean.setVat(price.getVat().toString());
        saleTransDetailBean.setCurrency(price.getCurrency());

        saleTransDetailBean.setPriceDisplay(NumberUtils.rounđAndFormatAmount(price.getPrice()));
        saleTransDetailBean.setPriceId(saleToCollaboratorForm.getPriceId());

        saleTransDetailBean.setNote(saleToCollaboratorForm.getNote());

        /**
         * Set amountTax
         */
        if (saleToCollaboratorForm.getQuantity() > 0) {
            saleTransDetailBean.setAmount(saleToCollaboratorForm.getQuantity() * price.getPrice());//For update to sale_trans_detail
            saleTransDetailBean.setAmountTaxDisplay(NumberUtils.rounđAndFormatAmount(saleToCollaboratorForm.getQuantity() * price.getPrice()));//For display to interface
        }

        /**
         * set unit, name, Id
         */
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(saleToCollaboratorForm.getStockModelId());
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        AppParamsBean appParamsBean = appParamsDAO.findAppParamsByCode(STOCK_MODEL_UNIT, stockModel.getUnit());
        saleTransDetailBean.setUnit(appParamsBean.getName());
        saleTransDetailBean.setQuantity(saleToCollaboratorForm.getQuantity());
        saleTransDetailBean.setStockModelName(stockModel.getName());
        saleTransDetailBean.setStockModelCode(stockModel.getStockModelCode());
        saleTransDetailBean.setStockModelId(stockModel.getStockModelId());
        saleTransDetailBean.setCheckSerial(stockModel.getCheckSerial());

        //set dich vu vien thong
        TelecomServiceDAO telDAO = new TelecomServiceDAO();
        telDAO.setSession(this.getSession());
        TelecomService tel = telDAO.findById(stockModelF9.getTelecomServiceId());
        if (tel == null || tel.getTelecomServiceId() == null) {
            log.info("Telecom service khong hop le");
            req.setAttribute("returnMsg", "Lỗi. Mã dịch vụ viễn thông không hợp lệ");
            reserveCondition(saleToCollaboratorForm.getStockModelId());
            saleToCollaboratorForm.resetForm();
            return pageForward;
        }

        saleTransDetailBean.setTelecomServiceID(tel.getTelecomServiceId());
//        saleTransDetailBean.setTelecomServiceId(tel.getTelecomServiceId());

        saleTransDetailBean.setStateId(Constant.STATE_NEW);
//        sumSaleTransDetailAmount(saleTransDetailBean);
        lstGoods.add(saleTransDetailBean);
        sum(lstGoods);
        saleToCollaboratorForm.resetForm();
        reqSession.setAttribute("priceList", null);

        setTabSession(LIST_GOOD_SESSION_OBJ, lstGoods);
        log.info("End.");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 17/09/2010 desc : thay the ham private void
     * reserveCondition(Long stockModelId) khi chuyen sang lib moi
     *
     */
    private void getDataForCombobox() throws Exception {
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);

        //lay danh sach gia
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(getSession());
        priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_PROMOTION);
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());
        String pricePolicy = shop.getPricePolicy();
        priceDAO.setPricePolicyFilter(pricePolicy);
        priceDAO.setStockModelIdFilter(saleToCollaboratorForm.getStockModelId());
        List price = priceDAO.findPriceForSaleRetail();
        List<Price> priceList = new ArrayList();

        Iterator iter = price.iterator();
        while (iter.hasNext()) {
            Object[] temp = (Object[]) iter.next();
            Price priceTemp = new Price();
            priceTemp.setPriceId(new Long(temp[0].toString()));
            priceTemp.setPrice(new Double(temp[1].toString()));
            priceList.add(priceTemp);
        }

        reqSession.setAttribute("priceList", priceList);

    }

    private boolean checkValidGoods(List<SaleTransDetailBean> lstGoods) {
        log.info("Begin.");
        boolean valid = true;
        for (SaleTransDetailBean saleTransDetailBean : lstGoods) {
            if (saleTransDetailBean.getStockModelId().equals(saleToCollaboratorForm.getStockModelId())) {
                valid = false;
                return valid;
            }
        }
        log.info("End.");
        return valid;
    }

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

    private void sumSaleTransDetailAmount_not_used(SaleTransDetailBean saleTransDetailBean) {
        Double promotionTotal = 0.0;
        PromotionDAO promotionDAO = new PromotionDAO();
        promotionDAO.setSession(getSession());
        Long promotionId = saleTransDetailBean.getPromotionId();
        if (promotionId != null) {
            Promotion promotion = promotionDAO.findById(promotionId);
            Long type = promotion.getType();
            if (type.equals(PROMOTION_AMOUNT_TYPE)) {
                promotionTotal += saleTransDetailBean.getQuantity() * promotion.getAmount();
            } else if (type.equals(PROMOTION_RATE_TYPE)) {
                Double promotionTemp = saleTransDetailBean.getQuantity() * saleTransDetailBean.getPrice() * promotion.getRate();
                promotionTotal += Math.round(promotionTemp / 100);
            }
        }
        saleTransDetailBean.setPromotionAmount(promotionTotal.longValue());
        //Sum AmountTax
        Double AmountTax = 0.0;
        Long priceId = saleTransDetailBean.getPriceId();
        //find price
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        Price price = priceDAO.findById(priceId);
        //sum notTax
        if (price != null) {
            Double Tax = (price.getPrice().doubleValue() * saleTransDetailBean.getQuantity().doubleValue());
            if (price.getVat() != null) {
                Tax = (Tax / (1 + (price.getVat().doubleValue() / 100))) * (price.getVat().doubleValue());
            }
            AmountTax += Math.round(Tax);
        }
        saleTransDetailBean.setAmountTax(AmountTax);
    }

    /**
     * @Author: TungTV @Date created: 06/05/2009 @Purpose: prepare edit goods in
     * goods list
     */
    public String prepareEditGoods() throws Exception {
        log.info("# Begin method prepareEditGoods");
        getReqSession();
        String pageForward = SALE_TO_PROMOTION_DECLARE;

        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            log.info("WEB. Session time out. UserToken is missing");
            return Constant.ERROR_PAGE;
        }
        List lstGoods = (List) getTabSession(LIST_GOOD_SESSION_OBJ);
        if (lstGoods == null || lstGoods.isEmpty()) {
            log.info("WEB. Session time out. UserToken is missing");
            return Constant.ERROR_PAGE;
        }

        String stockModelIdTemp = (String) req.getParameter("stockModelId");
        if (stockModelIdTemp != null && !"".equals(stockModelIdTemp.trim())) {
            Long stockModelId = Long.parseLong(stockModelIdTemp.trim());
//            session.setAttribute("stockModelId", stockModelId);
            SaleTransDetailBean saleTransDetailBean;
            for (int i = 0; i < lstGoods.size(); i++) {
                saleTransDetailBean = (SaleTransDetailBean) lstGoods.get(i);
                if (saleTransDetailBean.getStockModelId().equals(stockModelId)) {

                    saleToCollaboratorForm.setTelecomServiceId(saleTransDetailBean.getTelecomServiceID());
                    saleToCollaboratorForm.setStockModelId(saleTransDetailBean.getStockModelId());
                    saleToCollaboratorForm.setStockModelCode(saleTransDetailBean.getStockModelCode());
                    saleToCollaboratorForm.setStockModelName(saleTransDetailBean.getStockModelName());
                    saleToCollaboratorForm.setPriceId(saleTransDetailBean.getPriceId());
                    saleToCollaboratorForm.setQuantity(saleTransDetailBean.getQuantity());
                    saleToCollaboratorForm.setNote(saleTransDetailBean.getNote());

//                    saleToCollaboratorForm.setPromotionId(saleTransDetailBean.getPromotionId());

//                    StockModelDAO modelDAO = new StockModelDAO();
//                    modelDAO.setSession(this.getSession());
//                    modelDAO.setTelecomServiceIdFilter(saleTransDetailBean.getTelecomServiceID());
                    /**
                     * Get shop ID
                     */
//                    Long staffSalerId = null;
//                    staffSalerId = (Long) session.getAttribute("salerId");
//                    if (staffSalerId != null) {
//                        modelDAO.setStaffSalerIdFilter(staffSalerId);
//                    } else {
//                        modelDAO.setStaffSalerIdFilter(userToken.getUserID());
//                    }
//                    List model = modelDAO.findStockModelForSaleRetail();
//                    List<StockModel> modelList = new ArrayList();
//                    Iterator iter = model.iterator();
//                    while (iter.hasNext()) {
//                        Object[] temp = (Object[]) iter.next();
//                        StockModel stockModel = new StockModel();
//                        stockModel.setStockModelId(new Long(temp[0].toString()));
//                        stockModel.setName(temp[1].toString());
//                        modelList.add(stockModel);
//                    }
//                    session.setAttribute("modelList", modelList);
                    /**
                     * Get price list
                     */
                    PriceDAO priceDAO = new PriceDAO();
                    priceDAO.setSession(getSession());
                    priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_PROMOTION);

                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(getSession());
                    Shop shop = shopDAO.findById(userToken.getShopId());

                    String pricePolicy = shop.getPricePolicy();
                    priceDAO.setPricePolicyFilter(pricePolicy);

                    priceDAO.setStockModelIdFilter(stockModelId);
                    List price = priceDAO.findPriceForSaleRetail();
                    List<Price> priceList = new ArrayList();
                    Iterator iter = price.iterator();
                    while (iter.hasNext()) {
                        Object[] temp = (Object[]) iter.next();
                        Price priceTemp = new Price();
                        priceTemp.setPriceId(new Long(temp[0].toString()));
                        priceTemp.setPrice(new Double(temp[1].toString()));
                        priceList.add(priceTemp);
                    }
                    reqSession.setAttribute("priceList", priceList);

//                    /** Get promotion for stock model */
//                    PromotionDAO promotionDAO = new PromotionDAO();
//                    promotionDAO.setSession(getSession());
//
//                    List promotion = promotionDAO.findCurrPromotion(new Date(), stockModelId);
//
//                    List<Promotion> promotionList = new ArrayList();
//
//                    iter = promotion.iterator();
//
//                    while(iter.hasNext()) {
//                        Object[] temp = (Object[])iter.next();
//                        Promotion promotionTemp = new Promotion();
//                        promotionTemp.setPromotionId(new Long(temp[0].toString()));
//                        promotionTemp.setPromotionName(temp[1].toString());
//                        promotionList.add(promotionTemp);
//                    }
//
//                    session.setAttribute("promotionList", promotionList);
                    req.setAttribute("edit", true);
                    break;
                }
            }
        }

        //req.setAttribute("edit", "true");
        sum(lstGoods);
        reqSession.setAttribute("actionType", UPDATE_TYPE);
        log.info("# End method prepareEditGoods");
        return pageForward;
    }

    /**
     * @Author: TungTV @Date created: 05/05/2009 @Purpose: xoa hang hoa khoi
     * danh sach hang hoa
     */
    public String delGoods() throws Exception {
        log.info("# Begin method delGoods");
        String pageForward = SALE_TO_PROMOTION_INPUT_UPDATE;
        getReqSession();
        reqSession.setAttribute("priceList", null);
        saleToCollaboratorForm.resetForm();
        List lstGoods = (List) getTabSession(LIST_GOOD_SESSION_OBJ);
        if (lstGoods == null || lstGoods.isEmpty()) {
            req.setAttribute("returnMsg", "saleRetail.warn.Empty");
            return pageForward;
        }
        String stockModelIdTemp = (String) req.getParameter("stockModelId");
        if (stockModelIdTemp != null && !"".equals(stockModelIdTemp.trim())) {
            Long stockModelId = Long.parseLong(stockModelIdTemp.trim());
            for (int i = 0; i
                    < lstGoods.size(); i++) {
                if (lstGoods.get(i) instanceof SaleTransDetailBean) {
                    SaleTransDetailBean saleTransDetailBean = (SaleTransDetailBean) lstGoods.get(i);
                    if (saleTransDetailBean.getStockModelId().equals(stockModelId)) {
                        lstGoods.remove(i);
                        break;
                    }
                }
            }
        }        
        
        setTabSession(LIST_GOOD_SESSION_OBJ, lstGoods);
        /**
         * Set action type
         */
        reqSession.setAttribute("actionType", NEW_TYPE);
        /**
         * Reset form
         */
        saleToCollaboratorForm.resetForm();
        reqSession.setAttribute("priceList", null);
        sum(lstGoods);
        req.setAttribute("returnMsg", "saleRetail.warn.saveSuccess");
        log.info("# End method delGoods");
        return pageForward;        
    }
    /**
     *
     * author VuNT date: 27/07/2009 ham lay danh sach cac GoodsDef co' tai
     * nguyen can boc tham
     */
    private Map listModelCombo = new HashMap();
    private Map stockModelMap = new HashMap();

    public String getRetailModelPromotion() throws Exception {
        log.info("Begin.");
        try {
            getReqSession();
            UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
            String pageForward = Constant.ERROR_PAGE;
            if (userToken == null) {
                return pageForward;
            }
            String telecomServiceIdTemp = (String) req.getParameter("telecomServiceId");
            Long telecomServiceId = null;
            if (telecomServiceIdTemp != null && !telecomServiceIdTemp.equals("")) {
                telecomServiceId = new Long(telecomServiceIdTemp);
            }
            StockModelDAO modelDAO = new StockModelDAO();
            modelDAO.setSession(this.getSession());
            if (telecomServiceId != null) {
                modelDAO.setTelecomServiceIdFilter(telecomServiceId);
            }
            /**
             * Get shop ID
             */
            Long shopId = (Long) reqSession.getAttribute("shopId");
            if (shopId == null) {
                log.info("WEB:. Session time out");
                return "success";
            }
            Long staffSalerId = (Long) reqSession.getAttribute("salerId");
            if (staffSalerId != null) {
                modelDAO.setStaffSalerIdFilter(staffSalerId);
            } else {
                /**
                 * Current login staff
                 */
                modelDAO.setStaffSalerIdFilter(userToken.getUserID());
            }
            reqSession.setAttribute("salerId", staffSalerId);
            List model = modelDAO.findStockModelForSaleRetail();
            if (telecomServiceId == null) {
                model = new ArrayList();
            }
//            BaseDAOAction baseDAOAction = new BaseDAOAction();
            String[] header = {"", getText("MSG.SAE.023")};
            List tmpList = new ArrayList();
            tmpList.add(header);
            tmpList.addAll(model);
            listModelCombo.put("stockModelId", tmpList);
//            listModelCombo.put("","--Chon mat hang--");
//            if (model != null) {
//
//                for (int i = 0; i < model.size(); i++) {
//
//                    listModelComboPromotion.put(model.get(i).getStockModelId(), model.get(i).getName());
//
//                }
//                return "success";
//            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "success";
    }

    public Map getlistModelCombo() {
        return listModelCombo;
    }

    public void setlistModelCombo(Map listModelCombo) {
        this.listModelCombo = listModelCombo;
    }

    /**
     * @Author: TungTV @Date created: 06/05/2009 @Purpose: update good list
     * after select serial
     */
    public String updateGoodList() throws Exception {
        log.info("In update good list.");
        checkInputSerial();
        String pageForward = SALE_TO_PROMOTION_GOOD_LIST;
        return pageForward;
    }

    public String saleTransInfo() throws Exception {
        log.info("Begin.");
        try {
            getReqSession();
            if (saleToCollaboratorForm.isOther()) {
                Long salerId = saleToCollaboratorForm.getSalerId();
                if (salerId == null) {
                    req.setAttribute("returnMsg", "saleRetail.warn.NotStaffSupport");
                } else {
                    //session.setAttribute(LIST_GOOD_SESSION_OBJ, null);
                    removeTabSession(LIST_GOOD_SESSION_OBJ);
                    removeSession();
                    reqSession.setAttribute("salerId", salerId);
                    saleToCollaboratorForm.resetForm();
                }

            } else {
                Long salerId = saleToCollaboratorForm.getSalerId();
                if (salerId != null) {
                    req.setAttribute("returnMsg", "saleRetail.warn.NotCheck");
                } else {
                    saleToCollaboratorForm.setOther(false);
                    reqSession.setAttribute("salerId", null);
                    log.info("Not create sale trans for other");
                }
            }
            String currentDate = DateTimeUtils.getSysdate();
            saleToCollaboratorForm.setDateSale(currentDate);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("END.");
        return SALE_TO_PROMOTION;
    }

    public String saleTransRefresh() throws Exception {
        log.info("Begin.");
        try {
            getReqSession();
            reqSession.setAttribute("salerId", null);
            //session.setAttribute(LIST_GOOD_SESSION_OBJ, null);
            removeTabSession(LIST_GOOD_SESSION_OBJ);
            removeSession();
            saleToCollaboratorForm.resetForm();
            String currentDate = DateTimeUtils.getSysdate();
            saleToCollaboratorForm.setDateSale(currentDate);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("END.");
        return SALE_TO_PROMOTION;
    }
    //null || 0: da nhap

    private void checkInputSerial() throws Exception {
        getReqSession();
        //List lstGoods = (List) req.getSession().getAttribute(LIST_GOOD_SESSION_OBJ);
        List lstGoods = (List) getTabSession(LIST_GOOD_SESSION_OBJ);
        if (lstGoods == null || lstGoods.size() == 0) {
            return;
        } //Tinh lai tong tien
        sum(lstGoods);
    }
}
