/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.SaleRetailAnypayForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import com.viettel.lib.provisioning.database.client.bean.SubInfo;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.CustumerAccount;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockOwnerTmp;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.StockTotalId;
import com.viettel.lib.anypay.database.BO.TransReload;
import com.viettel.lib.cmpre.database.client.bean.SubMbInfo;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import com.viettel.lib.database.DAO.WebServiceDAO;
import com.viettel.lib.database.DAO.WebServiceCmDAO;
import com.viettel.lib.database.DAO.WebServiceProvisioningDAO;
import com.viettel.lib.provisioning.database.client.bean.BalanceInfo;
import com.viettel.lib.provisioning.database.client.bean.ResultRechargeAnypay;
import com.viettel.lib.sm.database.client.bean.Result;
import com.viettel.security.util.DbProcessor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Session;

/**
 *
 * @author kdvt_thaiph1
 */
public class SaleRetailAnypayDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ChannelDAO.class);
    //khai bao cac hang so forward
    private String pageForward = "";
    private final String PREPARE_PAGE = "saleAnypayToRetail";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "displayResultMsgClient";
    private final String SHOW_ISDN_DETAIL_INFO = "showIsdnDetailInfo";
    private SaleRetailAnypayForm saleRetailAnypayForm = new SaleRetailAnypayForm();

    public SaleRetailAnypayForm getSaleRetailAnypayForm() {
        return saleRetailAnypayForm;
    }

    public void setSaleRetailAnypayForm(SaleRetailAnypayForm saleRetailAnypayForm) {
        this.saleRetailAnypayForm = saleRetailAnypayForm;
    }

    /**
     * Modified by : thaiph Modify date : 28-06-2012 Purpose :
     *
     * @return
     * @throws Exception
     */
    public String preparePage() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }
        //LinhNBV start modified on May 24 2018: Check over limit.
        DbProcessor db = new DbProcessor();
//        if (isStaffMaputo(userToken.getLoginName())) {
            boolean isOverLimit = db.checkOverLimitBeforeSale(userToken.getLoginName());
            Staff s = getStaffByStaffCode(userToken.getLoginName().toUpperCase());
            //true meaning limit valid.
            if (isOverLimit) {
                req.getSession().setAttribute("isOverLimit", 1);
            } else {
                //false limit invalid.
                req.getSession().setAttribute("isOverLimit", 0);
            }
            if (s == null || s.getLimitApproveUser() == null || "".equalsIgnoreCase(s.getLimitApproveUser())) {
                //false limit invalid.
                req.getSession().setAttribute("isOverLimit", 0);
            }
//        }else{
//            req.getSession().setAttribute("isOverLimit", 1);
//        }
        //end.

        removeTabSession("listStaffImportFile");
        List listButton = null;
        req.setAttribute("listButton", listButton);
        saleRetailAnypayForm.setShopCode(userToken.getShopCode());
        saleRetailAnypayForm.setShopName(userToken.getShopName());
        saleRetailAnypayForm.setStaffCode(userToken.getLoginName());
        saleRetailAnypayForm.setStaffName(userToken.getStaffName());

        pageForward = PREPARE_PAGE;
        return pageForward;

    }

    public String getDetail() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String isdn = saleRetailAnypayForm.getIsdn().trim();
        WebServiceCmDAO webServiceCmDAO = new WebServiceCmDAO();
        SubMbInfo subMbInfo = webServiceCmDAO.getSubMbInfoByIsdn(getSession("cm_pre"), isdn);
        if (subMbInfo == null) {
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "E.200047");
            pageForward = PREPARE_PAGE;
            return pageForward;
        }
        saleRetailAnypayForm.setCusName(subMbInfo.getSubName());
        saleRetailAnypayForm.setSubId(subMbInfo.getSubId());

        getListInfo(isdn, req);


        pageForward = PREPARE_PAGE;
        return pageForward;
    }

    private void getListInfo(String isdn, HttpServletRequest req) throws Exception {
        WebServiceProvisioningDAO webServiceProvisioningDAO = new WebServiceProvisioningDAO();
        SubInfo subInfomation = webServiceProvisioningDAO.getBalanceInfo(null, isdn);

        List<BalanceInfo> lstCustumerAccount = new ArrayList();

//        for (Map.Entry<String, Long> mapEntry : listAccount.entrySet()) {
//            custumerAccount = new CustumerAccount();
//            custumerAccount.setAccountName(mapEntry.getKey());
//            custumerAccount.setMoneyTotal(mapEntry.getValue());
//            lstCustumerAccount.add(custumerAccount);
//        }
        lstCustumerAccount = subInfomation.getLstBalance();

        req.setAttribute("listAccount", lstCustumerAccount);
        saleRetailAnypayForm.setExpiryDate(subInfomation.getExpriryDae());
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        List<AppParams> listApParam = appParamsDAO.findAppParamsByType("ANYPAY_TO_RETAIL");
        req.setAttribute("listAmount", listApParam);
    }

    private long getSequence(Session session, String sequenceName) throws
            Exception {
        String strQuery = "SELECT " + sequenceName
                + " .NextVal FROM Dual";
        Query queryObject =
                session.createSQLQuery(strQuery);
        BigDecimal bigDecimal = (BigDecimal) queryObject.uniqueResult();
        return bigDecimal.longValue();
    }

    public String createTrans() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }
        Long shopId = userToken.getShopId();
        Long staffId = userToken.getUserID();

        boolean chk = saleRetailAnypayForm.getChk();

        String isdn = saleRetailAnypayForm.getIsdn().trim();
        Long amount = null;
        try {
            if (chk) {
                amount = saleRetailAnypayForm.getAmountManual();
            } else {
                amount = saleRetailAnypayForm.getAmountList();
            }
        } catch (Exception ex) {
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "E.200047");
            pageForward = PREPARE_PAGE;
            return pageForward;
        }

        //            tannh20180509 : kiem tra han muc cong no
        //LinhNBV 20180524: fixed check debit of staff.
        DbProcessor db = new DbProcessor();
//        if (isStaffMaputo(userToken.getLoginName())) {
//        //LinhNBV start modified on May 24 2018: Check amountSaleTrans + deposit + payment + amountTax > limitMoney
            double sumSaleTrans = db.sumSaleTransDebitByStaffCode(userToken.getLoginName());
            double sumDeposit = db.sumDepositDebitByStaffCode(userToken.getLoginName());
            double sumPayment = db.sumPaymentDebitByStaffCode(userToken.getLoginName());
            double limitMoney = db.getLimitMoneyByStaffCode(userToken.getLoginName());
            if ((sumSaleTrans + sumDeposit + sumPayment + amount.doubleValue()) > limitMoney) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.159");
                pageForward = PREPARE_PAGE;
                return pageForward;
            }
//        }
        //end.

        //validate du lieu:
        WebServiceCmDAO webServiceCmDAO = new WebServiceCmDAO();
        SubMbInfo subMbInfo = webServiceCmDAO.getSubMbInfoByIsdn(getSession("cm_pre"), isdn);
        if (subMbInfo == null) {
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "E.200047");
            pageForward = PREPARE_PAGE;
            return pageForward;
        }
        if (!subMbInfo.getSubId().equals(saleRetailAnypayForm.getSubId())) {
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "E.200048");
            pageForward = PREPARE_PAGE;
            return pageForward;
        }
//Huynq13 20170331 start add to fix error auto active for sub when topup, consequence is that sub can change sim, after that get bonus illegal
        if ("03".equals(subMbInfo.getActStatus())) {
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "E.200047");
            pageForward = PREPARE_PAGE;
            return pageForward;
        }
//Huynq13 20170331 start add to fix error auto active for sub when topup, consequence is that sub can change sim, after that get bonus illegal        
        String cusName = saleRetailAnypayForm.getCusName();

        //kiem tra so tien lon nhat cho phep nap
        String maxMoney = ResourceBundleUtils.getResource("Max_Anypay_To_Retail");
        if (maxMoney != null) {
            Long maxMoneyToSale = Long.valueOf(maxMoney);
            if (maxMoneyToSale.compareTo(amount) < 0) {
                getListInfo(isdn, req);
                req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "E.200049");
                List listParamValue = new ArrayList();
                listParamValue.add(maxMoney);
                req.setAttribute("returnMsgValue", listParamValue);
                pageForward = PREPARE_PAGE;
                return pageForward;
            }
        }

        String minMoney = ResourceBundleUtils.getResource("Min_Anypay_To_Retail");
        if (minMoney != null) {
            Long minMoneyToSale = Long.valueOf(minMoney);
            if (minMoneyToSale.compareTo(amount) > 0) {
                getListInfo(isdn, req);
                req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "E.200050");
                List listParamValue = new ArrayList();
                listParamValue.add(minMoneyToSale);
                req.setAttribute("returnMsgValue", listParamValue);
                pageForward = PREPARE_PAGE;
                return pageForward;
            }
        }


        //check kho:
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.getStockModelByCode(Constant.STOCK_MODEL_CODE_TCDT);
        boolean check = StockCommonDAO.checkStockGoods(
                userToken.getUserID(),
                Constant.OWNER_TYPE_STAFF,
                stockModel.getStockModelId(),
                Constant.STATE_NEW,
                amount,
                Constant.UN_CHECK_DIAL,
                this.getSession());
        if (!check) {
            getListInfo(isdn, req);
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "saleColl.warn.saleQuantity");
            pageForward = PREPARE_PAGE;
            return pageForward;
        }

        StockTotalId stockTotalId = new StockTotalId();
        stockTotalId.setOwnerId(userToken.getUserID());
        stockTotalId.setOwnerType(Constant.OWNER_TYPE_STAFF);
        stockTotalId.setStockModelId(stockModel.getStockModelId());
        stockTotalId.setStateId(Constant.STATE_NEW);
        StockTotalDAO stockTotalDAO = new StockTotalDAO();
        stockTotalDAO.setSession(getSession());
        StockTotal stockTotal = stockTotalDAO.findById(stockTotalId);
        if (stockTotal == null) {
            getListInfo(isdn, req);
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "saleColl.warn.saleQuantity");
            pageForward = PREPARE_PAGE;
            return pageForward;
        }
        Long quantityIssue = stockTotal.getQuantityIssue();

        WebServiceDAO ws = new WebServiceDAO();
        Session anypaySession = getSession("anypay");
        Session smlibSession = getSession("sm_lib");
        anypaySession.beginTransaction();
        smlibSession.beginTransaction();

        Long saleTransId = getSequence(smlibSession, "SALE_TRANS_SEQ");
        Long anypayTransId = getSequence(anypaySession, "trans_seq");

        //Luu giao dich ban hang:
        com.viettel.lib.sm.database.BO.SaleTrans saleTrans = new com.viettel.lib.sm.database.BO.SaleTrans();
        saleTrans.setSaleTransId(saleTransId);
        saleTrans.setShopId(shopId);
        saleTrans.setStaffId(staffId);
        saleTrans.setInTransId(anypayTransId.toString());
        saleTrans.setIsdn(isdn);
        saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_RETAIL_ANYPAY.toString());
        saleTrans.setCustName(cusName);

        List<com.viettel.lib.sm.database.client.bean.StockModelBean> lstStockModelBean = new ArrayList();
        com.viettel.lib.sm.database.client.bean.StockModelBean stockModelBean = new com.viettel.lib.sm.database.client.bean.StockModelBean();
        stockModelBean.setStockModelCode(Constant.STOCK_MODEL_CODE_TCDT);
        stockModelBean.setQuantity(amount);
        lstStockModelBean.add(stockModelBean);

        saleTrans.setLstStockModelBean(lstStockModelBean);
        Result result = ws.saveSaleTrans(smlibSession, saleTrans);

        if (result.getCode() != null && !result.getCode().equals("")) {
            anypaySession.getTransaction().rollback();
            smlibSession.getTransaction().rollback();
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, result.getCode());
            addMethodCallLog(getSession(), "createTrans", getIpAddress() + ";" + userToken.getShopCode() + ";" + userToken.getLoginName() + ";" + isdn + ";" + amount.toString(), result.getCode());

            pageForward = PREPARE_PAGE;
            return pageForward;
        }

        WebServiceProvisioningDAO webServiceProvisioningDAO = new WebServiceProvisioningDAO();
        SubInfo subInfomation = webServiceProvisioningDAO.getBalanceInfo(null, isdn);

        //Nap tien cho thue bao tra truoc:
        com.viettel.lib.anypay.database.BO.TransReload tr = new TransReload();
        tr.setTransId(anypayTransId);
        tr.setSourceAccountId(userToken.getUserID());
        tr.setSourceCurrBalance(quantityIssue.doubleValue());
        tr.setTargetMsisdn(isdn);
        tr.setTargetAccountId(subMbInfo.getSubId());
        tr.setTargetCurrBalance(subInfomation.getBalance());
        tr.setStockSmId(userToken.getUserID());
        tr.setSmTransId(saleTransId);

        tr.setTransAmount(amount.doubleValue());
//        ResultRechargeAnypay resultRechargeAnypay = ws.addMoneyForPrepaid(anypaySession, null, null, isdn, amount.doubleValue());
        ResultRechargeAnypay resultRechargeAnypay = ws.addMoneyForPrepaid(anypaySession, null, tr);
        if (resultRechargeAnypay.getErrorCode() != null && !resultRechargeAnypay.getErrorCode().equals("")) {
            anypaySession.getTransaction().rollback();
            smlibSession.getTransaction().rollback();
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, resultRechargeAnypay.getErrorCode());
            addMethodCallLog(getSession(), "createTrans", getIpAddress() + ";" + userToken.getShopCode() + ";" + userToken.getLoginName() + ";" + isdn + ";" + amount.toString(), resultRechargeAnypay.getErrorCode());
            pageForward = PREPARE_PAGE;
            return pageForward;
        }

        anypaySession.getTransaction().commit();
        smlibSession.getTransaction().commit();

        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "M.200005");
        CustumerAccount custumerAccount = new CustumerAccount();
        custumerAccount.setAccountName("Tai khoan chinh");
        custumerAccount.setMoneyTotal(100L);
        req.setAttribute("listButton", custumerAccount);

        addMethodCallLog(getSession(), "createTrans", getIpAddress() + ";" + userToken.getShopCode() + ";" + userToken.getLoginName() + ";" + isdn + ";" + amount.toString(), "0");

        pageForward = PREPARE_PAGE;
        return pageForward;
    }

    public Shop getShop(Long shopId) throws Exception {
        if (shopId == null) {
            return null;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(shopId);
        if (shop != null) {
            return shop;
        }
        return null;
    }

    public Shop getShop(String shopCode) throws Exception {
        if (shopCode == null || shopCode.trim().equals("")) {
            return null;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCodeNotStatus(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop;
        }
        return null;
    }

    public boolean checkShopUnder(Long shopIdLogin, Long shopIdSelect) {
        if (shopIdLogin.equals(shopIdSelect)) {
            return true;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shopLogin = shopDAO.findById(shopIdLogin);
        Shop shopSelect = shopDAO.findById(shopIdSelect);
        if (shopSelect.getShopPath().indexOf(shopLogin.getShopPath() + "_") < 0) {
            return false;
        } else {
            return true;
        }

    }

    ///////////////////////////////BO SUNG//////////////////////////////////////
    public Area getArea(String areaCode) {
        if (areaCode == null || areaCode.trim().equals("")) {
            return null;
        }
        AreaDAO areaDAO = new AreaDAO();
        areaDAO.setSession(getSession());
        return areaDAO.findByAreaCode(areaCode.toUpperCase());
    }

    /**
     * @author tutm1
     * @purpose them ban ghi moi co han muc mac dinh maxDebit
     * @param ownerId
     * @param ownerType
     * @param staffCode
     * @param staffName
     * @param channelTypeId
     * @param maxDebit
     * @return
     * @throws Exception
     */
    public boolean insertStockOwnerTmp(Long ownerId, String ownerType, String staffCode, String staffName,
            Long channelTypeId, Double maxDebit) throws Exception {
        //insert vao bang stock_owner_tmp
        String sql = "From StockOwnerTmp where ownerId = ? and ownerType = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        List<StockOwnerTmp> list = query.list();
        if (list == null || list.size() == 0) {
            StockOwnerTmp stockOwnerTmp = new StockOwnerTmp();
            stockOwnerTmp.setStockId(getSequence("STOCK_OWNER_TMP_SEQ"));
            stockOwnerTmp.setChannelTypeId(channelTypeId);
            stockOwnerTmp.setCode(staffCode);
            stockOwnerTmp.setName(staffName);
            stockOwnerTmp.setOwnerId(ownerId);
            stockOwnerTmp.setOwnerType(ownerType);
            stockOwnerTmp.setMaxDebit(maxDebit);
            getSession().save(stockOwnerTmp);
        } else {
            StockOwnerTmp stockOwnerTmp = list.get(0);
            stockOwnerTmp.setName(staffName);
            stockOwnerTmp.setChannelTypeId(channelTypeId);
            getSession().update(stockOwnerTmp);
        }
        return true;
    }

    public boolean insertStockOwnerTmp(Long ownerId, String ownerType, String staffCode, String staffName, Long channelTypeId) throws Exception {
        //insert vao bang stock_owner_tmp
        String sql = "From StockOwnerTmp where ownerId = ? and ownerType = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        List<StockOwnerTmp> list = query.list();
        if (list == null || list.size() == 0) {
            StockOwnerTmp stockOwnerTmp = new StockOwnerTmp();
            stockOwnerTmp.setStockId(getSequence("STOCK_OWNER_TMP_SEQ"));
            stockOwnerTmp.setChannelTypeId(channelTypeId);
            stockOwnerTmp.setCode(staffCode);
            stockOwnerTmp.setName(staffName);
            stockOwnerTmp.setOwnerId(ownerId);
            stockOwnerTmp.setOwnerType(ownerType);
            getSession().save(stockOwnerTmp);
        } else {
            StockOwnerTmp stockOwnerTmp = list.get(0);
            stockOwnerTmp.setName(staffName);
            stockOwnerTmp.setChannelTypeId(channelTypeId);
            getSession().update(stockOwnerTmp);
        }
        return true;
    }

    public Staff getStaff(String staffCode) throws Exception {
        if (staffCode == null || staffCode.trim().equals("")) {
            return null;
        }
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCodeNotStatus(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff;
        }
        return null;
    }

    //download danh sach file loi ve
    public void downloadFile(String templatePathResource, List listReport) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
        filePath = filePath != null ? filePath : "/";
        filePath += templatePathResource + "_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String templatePath = ResourceBundleUtils.getResource(templatePathResource);
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        Map beans = new HashMap();
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, filePath);
//        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "Nếu hệ thống không tự download. Click vào đây để tải File lưu thông tin không cập nhật được");
        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "ERR.CHL.102");

    }

    private Staff getStaffByStaffCode(String staffCode) {
        Staff staff = null;
        if (staffCode == null) {
            return staff;
        }

        String strQuery = "from Staff where staffCode = ?  and status =1";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, staffCode);
        List<Staff> listStaff = query.list();
        if (listStaff != null && listStaff.size() > 0) {
            staff = listStaff.get(0);
        }
        return staff;
    }

//    private boolean isStaffMaputo(String staffCode) {
//        if (staffCode == null) {
//            return false;
//        }
//
//        String strQuery = "select * from sm.staff where shop_id in (select shop_id from shop where shop_path like '%1000911%') and status =1 and staff_code = ? ";
//        Query query = getSession().createSQLQuery(strQuery);
//        query.setParameter(0, staffCode.toUpperCase());
//        List<Staff> listStaff = query.list();
//        if (listStaff != null && listStaff.size() > 0) {
//            return true;
//        }
//        return false;
//    }

    public void addMethodCallLog(Session session, String methodName, String parameter, String methodCallResult) {
        java.sql.PreparedStatement stmt = null;
        try {
            StringBuilder strBuff = new StringBuilder();
            strBuff.append("INSERT INTO method_call_log ");
            strBuff.append("            (method_call_log_id, class_name, ");
            strBuff.append("             method_name, ");
            strBuff.append("             parameter, ");
            strBuff.append("             create_user, create_date, last_update_time, method_call_result) ");
            strBuff.append("     VALUES (METHOD_CALL_LOG_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?) ");

            stmt = session.connection().prepareStatement(strBuff.toString());
            stmt.setString(1, "com.viettel.im.database.DAO.SaleRetailAnypayDAO");
            stmt.setString(2, methodName);
            stmt.setString(3, parameter);
            stmt.setString(4, "sm_app");
            stmt.setTimestamp(5, new java.sql.Timestamp((getSysdate()).getTime()));
            stmt.setTimestamp(6, new java.sql.Timestamp((getSysdate()).getTime()));
            stmt.setString(7, methodCallResult);

            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
