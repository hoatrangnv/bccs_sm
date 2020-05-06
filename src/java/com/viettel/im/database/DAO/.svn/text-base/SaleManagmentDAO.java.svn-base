package com.viettel.im.database.DAO;

import com.viettel.anypay.database.AnypaySession;
import com.viettel.anypay.database.BO.AnypayMsg;
import com.viettel.anypay.logic.AnypayLogic;
import com.viettel.bccs.cm.api.InterfaceCMToIM;
import com.viettel.bccs.cm.api.InterfaceCm;
import com.viettel.common.ViettelService;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.checkpost.checkReferenceNo;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.SaleManagmentForm;
import com.viettel.im.common.ConfigParam;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.SaleInvoiceType;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.SaleTransDetailFull;
import com.viettel.im.database.BO.SaleTransFull;
import com.viettel.im.database.BO.SaleTransOrder;
import com.viettel.im.database.BO.SaleTransPost;
import com.viettel.im.database.BO.SaleTransSerial;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockKit;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VSaleTransRole;
import com.viettel.im.sms.SmsClient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import com.viettel.payment.API.InitPaymentDatabaseServer;
import com.viettel.payment.API.PaymentPosService;
import com.viettel.security.util.DbProcessor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import net.sf.jxls.transformer.XLSTransformer;
import oracle.jdbc.OracleTypes;
import org.hibernate.LockMode;
import org.hibernate.Session;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockKit entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see com.viettel.im.database.BO.StockKit
 * @author MyEclipse Persistence Tools
 */
public class SaleManagmentDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(SaleManagmentDAO.class);
    private static final String SALE_INVOICE_TYPE_LIST = "saleInvoiceTypeList";
    public static Long SALE_TRANS_TYPE_STK_MANAGER_SALE_CHANNEL = 21L; //NVQL ban dut cho kenh
    public String pageForward;
    private String CANCEL_TRANS_NV = "cancelTransNV";
    private String CANCEL_TRANS_CHT = "cancelTransCHT";
    private String CANCEL_TRANS_VT = "cancelTransVT";
    private String CANCEL_TRANS_CN = "cancelTransCN";
    private String LIST_PAY_METHOD = "lstPayMethod";
    private String CANCEL_TRANS_CARD = "cancelTransCard";   //DucTM_R2265
    private SaleManagmentForm saleManagmentForm = new SaleManagmentForm();
    private static Connection connection = null;

    public SaleManagmentForm getSaleManagmentForm() {
        return saleManagmentForm;
    }

    public void setSaleManagmentForm(SaleManagmentForm saleManagmentForm) {
        this.saleManagmentForm = saleManagmentForm;
    }

    public String preparePage() throws Exception {
        /**
         * Action common object
         */
        log.debug("# Begin method SaleManagmentDAO.preparePage");
        HttpServletRequest req = getRequest();
        pageForward = "saleManagment";
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
        req.setAttribute("lstTelecomService", lstTelecomService);
        req.getSession().removeAttribute("lstSaleTrans");

        //PayMethod
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        List lstPayMethod = appParamsDAO.findAppParamsList("PAY_METHOD", Constant.STATUS_USE.toString());
        req.getSession().setAttribute(LIST_PAY_METHOD, lstPayMethod);

        /* danh sach loai giao dich */
        SaleInvoiceTypeDAO saleInvoiceTypeDAO = new SaleInvoiceTypeDAO();
        saleInvoiceTypeDAO.setSession(getSession());
        List<SaleInvoiceType> saleInvoiceTypeList = saleInvoiceTypeDAO.getListSaleTransType();
        req.getSession().setAttribute(SALE_INVOICE_TYPE_LIST, saleInvoiceTypeList);

        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());

        List lstStaff = staffDAO.findAllStaffOfShop(userToken.getShopId());

        req.setAttribute("lstStaff", lstStaff);

        req.setAttribute("lstTelecomService", lstTelecomService);
        req.getSession().removeAttribute("lstSaleTrans");

        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        saleManagmentForm.setSTransToDate(sdf.format(cal.getTime()));
        //cal.roll(Calendar.MONTH, false); // roll down, substract 1 month
        //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
        saleManagmentForm.setSTransFromDate(sdf.format(cal.getTime()));
        saleManagmentForm.setShopCode(userToken.getShopCode());
        saleManagmentForm.setShopName(userToken.getShopName());
        saleManagmentForm.setStaffCode(userToken.getLoginName());
        saleManagmentForm.setStaffName(userToken.getStaffName());
        if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("saleTransManagementf9Shop"), req)) {
            req.getSession().setAttribute("Edit", "true");
        }
        if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("saleTransManagementf9Staff"), req)) {
            req.getSession().setAttribute("EditStaff", "true");
        }

        //searchSaleTrans();
        pageForward = "saleManagment";
        req.setAttribute("resultUpdateSale", "");

        log.debug("# End method SaleManagmentDAO.preparePage");
        return pageForward;
    }

    public String searchSaleTrans() throws Exception {
        log.debug("# Begin method SaleManagmentDAO.searchSaleTrans");
        pageForward = "saleManagmentResult";

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }
        try {
            req.getSession().removeAttribute("lstSaleTrans");
            String SQL_SELECT_TRANS = " from SaleTransFull where 1= 1 ";
            List lstParam = new ArrayList();
            if (saleManagmentForm.getSTelecomServiceId() != null) {
                SQL_SELECT_TRANS += " and telecomServiceId = ? ";
                lstParam.add(saleManagmentForm.getSTelecomServiceId());
            }
            if (saleManagmentForm.getSSaleTransCode() != null && !saleManagmentForm.getSSaleTransCode().trim().equals("")) {
                SQL_SELECT_TRANS += " and lower(saleTransCode) like ? ";
                lstParam.add("%" + saleManagmentForm.getSSaleTransCode().trim().toLowerCase() + "%");
            }
            /*
             * if (saleManagmentForm.getSShopId() != null) { SQL_SELECT_TRANS +=
             * " and shopId = ? "; lstParam.add(saleManagmentForm.getSShopId());
             * }
             */

            Shop shopS = null;
            Staff staffS = null;
            if (saleManagmentForm.getShopCode() != null && !saleManagmentForm.getShopCode().trim().equals("")) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());
                List<Shop> listShop = shopDAO.findByPropertyWithStatus("shopCode", saleManagmentForm.getShopCode().trim(), 1L);
                if (listShop == null || listShop.isEmpty()) {
                    req.setAttribute("resultUpdateSale", "Mã cửa hàng không chính xác");
                    return pageForward;
                }
                shopS = listShop.get(0);
            }
            if (saleManagmentForm.getStaffCode() != null && !saleManagmentForm.getStaffCode().trim().equals("")) {
                StaffDAO shopDAO = new StaffDAO();
                shopDAO.setSession(getSession());
                List<Staff> listShop = shopDAO.findByPropertyAndStatus("staffCode", saleManagmentForm.getStaffCode().trim(), 1L);
                if (listShop == null || listShop.isEmpty()) {
                    req.setAttribute("resultUpdateSale", "Mã nhân viên không chính xác");
                    return pageForward;
                }
                staffS = listShop.get(0);
            }

            if (shopS != null && shopS.getShopId() != null) {
                SQL_SELECT_TRANS += " and shopId = ? ";
                lstParam.add(shopS.getShopId());
            } //Trong truong hop khong nhap ma cua hang --> chi tim kiem cac giao dich tu kho cap duoi cua user dang nhap
            else {
                SQL_SELECT_TRANS += " and shopId in (select shopId from Shop where status = ? and (shopId = ? or shopPath like ? escape '$') ) ";
                lstParam.add(Constant.STATUS_USE);
                lstParam.add(userToken.getShopId());
                lstParam.add("%$_" + userToken.getShopId() + "$_%");

            }
            if (staffS != null && staffS.getShopId() != null) {
                SQL_SELECT_TRANS += " and (staffId = ? or staffOwnerId = ?) ";
                lstParam.add(staffS.getStaffId());
                lstParam.add(staffS.getStaffId());

            }

//            String shopCode = saleManagmentForm.getShopCode();
//            ShopDAO shopDAO = new ShopDAO();
//            shopDAO.setSession(getSession());
//            List<Shop> listShop = shopDAO.findByPropertyWithStatus("shopCode", shopCode.trim(), 1L);
//            if (listShop != null && listShop.size() > 0) {
//                SQL_SELECT_TRANS += " and shopId = ? ";
//                lstParam.add(listShop.get(0).getShopId());
//            } else {
//                if (!shopCode.equals("")) {
//                    req.setAttribute("resultUpdateSale", "Mã cửa hàng không chính xác");
//                    return pageForward;
//                }
//            }

            /*
             * if (saleManagmentForm.getSStaffId() != null) { SQL_SELECT_TRANS
             * += " and staffId = ? ";
             * lstParam.add(saleManagmentForm.getSStaffId()); }
             */
//            String staffCode = saleManagmentForm.getStaffCode();
//            StaffDAO staffDAO = new StaffDAO();
//            staffDAO.setSession(getSession());
//            List<Staff> listStaff = staffDAO.findByPropertyAndStatus("staffCode", staffCode.trim(), 1L);
//            if (listStaff != null && listStaff.size() > 0 ) {
//                SQL_SELECT_TRANS += " and staffId = ? ";
//                lstParam.add(listStaff.get(0).getStaffId());
//            }
//            else{
//                if (!staffCode.trim().equals("")){
//                    req.setAttribute("resultUpdateSale", "Mã nhân viên không chính xác");
//                    return pageForward;
//                }
//            }
            if (saleManagmentForm.getSSaleTransType() != null) {
                SQL_SELECT_TRANS += " and saleTransType = ? ";
                lstParam.add(saleManagmentForm.getSSaleTransType().toString());
            }
            if (saleManagmentForm.getSCustName() != null && !saleManagmentForm.getSCustName().trim().equals("")) {
                SQL_SELECT_TRANS += " and lower(custName) like ? ";
                lstParam.add("%" + saleManagmentForm.getSCustName().trim().toLowerCase() + "%");
            }
            if (saleManagmentForm.getSIsdn() != null && !saleManagmentForm.getSIsdn().equals("")) {
                SQL_SELECT_TRANS += " and isdn = ? ";
                lstParam.add(saleManagmentForm.getSIsdn().trim());
            }
            if (saleManagmentForm.getSContractNo() != null && !saleManagmentForm.getSContractNo().trim().equals("")) {
                SQL_SELECT_TRANS += " and lower(contractNo)  like ?";
                lstParam.add("%" + saleManagmentForm.getSContractNo().trim().toLowerCase() + "%");
            }
            if (saleManagmentForm.getSTransFromDate() != null && !saleManagmentForm.getSTransFromDate().trim().equals("")) {
                Date fromDate;
                try {
                    fromDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransFromDate().trim());

                } catch (Exception ex) {
                    req.setAttribute("resultUpdateSale", "saleManagment.search.error.fromDate");
                    return pageForward;
                }
                SQL_SELECT_TRANS += " and saleTransDate  >= ?";
                lstParam.add(fromDate);
            }
            if (saleManagmentForm.getSTransToDate() != null && !saleManagmentForm.getSTransToDate().trim().equals("")) {

                Date toDate;
                try {
                    String stoDate = saleManagmentForm.getSTransToDate().trim().substring(0, 10) + " 23:59:59";
                    toDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");

                } catch (Exception ex) {
                    return pageForward;
                }
                SQL_SELECT_TRANS += " and saleTransDate  <= ?";
                lstParam.add(toDate);
            }
            if (saleManagmentForm.getSTransStatus() != null) {
                SQL_SELECT_TRANS += " and status  = ?";
                lstParam.add(saleManagmentForm.getSTransStatus().toString());
            }

            if (saleManagmentForm.getSPayMethod() != null && !saleManagmentForm.getSPayMethod().trim().equals("")) {
                SQL_SELECT_TRANS += " and payMethod  = ?";
                lstParam.add(saleManagmentForm.getSPayMethod().trim());
            }

            if (saleManagmentForm.getSDeliverStatus() != null) {
                if (saleManagmentForm.getSDeliverStatus().intValue() == 0) {
                    SQL_SELECT_TRANS += " and (transferGoods  = ? or transferGoods is null) ";
                } else {
                    SQL_SELECT_TRANS += " and transferGoods  = ?";
                }
                lstParam.add(saleManagmentForm.getSDeliverStatus().toString());
            }
            SQL_SELECT_TRANS += " order by saleTransDate desc ";
            Query q = getSession().createQuery(SQL_SELECT_TRANS);
            for (int idx = 0; idx < lstParam.size(); idx++) {
                q.setParameter(idx, lstParam.get(idx));
            }
            List lstSaleTrans = q.list();
            req.setAttribute("lstSaleTrans", lstSaleTrans);

        } catch (Exception ex) {
            req.setAttribute("resultUpdateSale", "saleManagment.search.error.undefine");
        }

        return pageForward;
    }

    public String expSaleTransToExcel() throws Exception {
        log.debug("# Begin method expSaleTransToExcel");
        HttpServletRequest req = this.getRequest();
        saleManagmentForm.setExportUrl("");
        pageForward = searchSaleTrans();
        List<SaleTransFull> lstSaleTrans = (List<SaleTransFull>) req.getAttribute("lstSaleTrans");
        if (lstSaleTrans != null) {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            SimpleDateFormat hoursFormat = new SimpleDateFormat("yyyyMMddhh24mmss");
            String dateTime = hoursFormat.format(new Date());

            String templatePath = ResourceBundleUtils.getResource("TEMPLATE_PATH") + "list_sale_trans.xls";
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE") + "list_sale_trans_" + userToken.getLoginName().toLowerCase() + "_" + dateTime + ".xls";

            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            String realPath = req.getSession().getServletContext().getRealPath(filePath);

            Map beans = new HashMap();
            beans.put("list", lstSaleTrans);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
            saleManagmentForm.setExportUrl(filePath);
        }

        log.debug("# End method expSaleTransToExcel");
        return pageForward;
    }


    /*
     * Author: ThanhNC Date created: 18/06/20009 Description: Xem chi tiet giao
     * dich
     */
    public String viewTransDetail() throws Exception {
        log.debug("# Begin method SaleManagmentDAO.viewTransDetail");
        pageForward = "saleManagmentDetail";
        HttpServletRequest req = getRequest();
        try {
            String trans = req.getParameter("saleTransId");
            if (trans == null || trans.trim().equals("")) {
                req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.noTransId");
                return pageForward;
            }
            Long saleTransId = Long.parseLong(trans);

            SaleTransDAO saleTransDAO = new SaleTransDAO();
            saleTransDAO.setSession(this.getSession());
            SaleTrans saleTrans = saleTransDAO.findById(saleTransId);
            if (saleTrans == null) {
                req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.noTransId");
                return pageForward;
            }
            String SQL_SELECT_SALE_TRANS = " from SaleTransFull where saleTransId = ? ";
            Query qS = getSession().createQuery(SQL_SELECT_SALE_TRANS);
            qS.setParameter(0, saleTransId);
            List lst = qS.list();
            if (lst.isEmpty()) {
                req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.noSaleDetail");
                return pageForward;
            }
            SaleTransFull saleTransFull = (SaleTransFull) lst.get(0);
            saleManagmentForm.copyFromSaleTrans(saleTransFull);

            //TRONGLV
            //BO SUNG HTTT & MA THAM CHIEU
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            AppParams appParams = appParamsDAO.findAppParams("PAY_METHOD", saleTrans.getPayMethod());
            if (appParams != null) {
                saleManagmentForm.setPayMethod(appParams.getName());

                if (appParams.getCode().trim().toUpperCase().equals(Constant.PAY_METHOD_POS)) {
                    SaleTransPostDAO saleTransPostDAO = new SaleTransPostDAO();
                    saleTransPostDAO.setSession(this.getSession());
                    List<SaleTransPost> listSaleTransPost = saleTransPostDAO.findBySaleTransId(saleTransId);
                    if (listSaleTransPost != null && listSaleTransPost.size() > 0) {
                        saleManagmentForm.setReferenceNo(listSaleTransPost.get(0).getReferenceNo());
                    }
                }
            }
            //XONG BO SUNG HTTT & MA THAM CHIEU

            //Tim danh sach mat hang trong GD
            String SQL_SELECT_TRANS_DETAIL = " from SaleTransDetailFull where saleTransId = ? ";
            Query q = getSession().createQuery(SQL_SELECT_TRANS_DETAIL);
            q.setParameter(0, saleTransId);
            List lstSaleTransDetail = q.list();
            if (lstSaleTransDetail.size() > 0) {
                //Format hien thi so luong, don gia, thanh tien
                for (int i = 0; i < lstSaleTransDetail.size(); i++) {
                    SaleTransDetailFull std = (SaleTransDetailFull) lstSaleTransDetail.get(i);
                    if (std == null) {
                        continue;
                    }
                    std.setQuantityMoney(NumberUtils.formatNumber(std.getQuantity()));
                    std.setPriceMoney(NumberUtils.rounđAndFormatAmount(std.getPrice()));
                    std.setAmountMoney(NumberUtils.rounđAndFormatAmount(std.getPrice() * std.getQuantity()));

                }
                req.setAttribute("lstSaleTransDetail", lstSaleTransDetail);
            } else {
                req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.noSaleDetail");
            }

            //check quyen huy giao dich anyPay
            //Trong sua them ngay 13/05/2010: chi check voi giao dich ban cho CTV
            boolean check = true;
            if (saleTrans.getSaleTransType().trim().equals(Constant.SALE_TRANS_TYPE_COLLABORATOR)) {
                boolean checkTCDT = false;
                for (int i = 0; i < lstSaleTransDetail.size(); i++) {
                    SaleTransDetailFull saleTransDetailFull = (SaleTransDetailFull) lstSaleTransDetail.get(i);
                    if (saleTransDetailFull.getStockModelCode().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
                        checkTCDT = true;
                        break;
                    }
                }
                if (checkTCDT) {
                    check = AuthenticateDAO.checkAuthority("checkDestroySaleTransAnyPay", req);
                    req.setAttribute("checkDestroySaleTransAnyPay", check);
                } else {
                    req.setAttribute("checkDestroySaleTransAnyPay", check);
                }
            }
            //check quyen huy gd qua post
//            if (saleTrans.getPayMethod().equals(Constant.PAY_METHOD_POS)) {
//                check = AuthenticateDAO.checkAuthority("checkdestroypos", req);
//                req.setAttribute("checkDestroySaleTransAnyPay", check);
//            }
            req.setAttribute("checkDestroySaleTransAnyPay", check);

            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_SERVICE) || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COL_RETAIL)
                    || (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION) && saleTrans.getSaleServiceId() != null)) {
                saleManagmentForm.setCancelTrans(AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("cancelSaleTransCM"), req));
            } else {
                checkAuthoritySaleTrans(saleTransId);
            }

            //DAI LY
            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT)) {
                StockTransDAO stockTransDAO = new StockTransDAO();
                stockTransDAO.setSession(this.getSession());
                StockTrans stockTrans = stockTransDAO.findById(saleTrans.getStockTransId());
                if (stockTrans == null || stockTrans.getStockTransId() == null) {
                    saleManagmentForm.setCancelTrans(false);
                }
                if (stockTrans.getStockTransStatus().intValue() == 4) {
                    saleManagmentForm.setCancelTrans(false);
                }
            }
            //Ban le Anypay
            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_RETAIL_ANYPAY.toString())) {
                saleManagmentForm.setCancelTrans(false);
            }
        } catch (Exception ex) {
            req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.undefine");
        }
        return pageForward;
    }

    /*
     * Author: TrongLV Date created: 30/12/20009 Description: Kiem tra quyen huy
     * giao dich
     */
    public void checkAuthoritySaleTrans(Long saleTransId) {
        saleManagmentForm.setCancelTrans(false);
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        String cancelInvoice = CANCEL_TRANS_NV; //Mac dinh la q uyen cua nhan vien

        try {
            //Huy hoa don
            if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_TRANS_VT), req)) {
                cancelInvoice = CANCEL_TRANS_VT;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_TRANS_CN), req)) {
                cancelInvoice = CANCEL_TRANS_CN;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_TRANS_CHT), req)) {
                cancelInvoice = CANCEL_TRANS_CHT;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_TRANS_NV), req)) {
                cancelInvoice = CANCEL_TRANS_NV;
            }
        } catch (Exception ex) {
        }

        try {
            cancelInvoice = cancelInvoice.trim().toLowerCase();

            List parameterList = new ArrayList();
            String queryString = " from VSaleTransRole where id.saleTransId = ? and id.roleValue = ? ";
            parameterList.add(saleTransId);
            parameterList.add("1");
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            List<VSaleTransRole> lstTemp = queryObject.list();
            if (lstTemp == null || lstTemp.isEmpty()) {
                return;
            }
            for (int i = 0; i < lstTemp.size(); i++) {
                VSaleTransRole object = lstTemp.get(i);
                //Cancel Invoice
                if (object.getId().getRoleName().trim().toLowerCase().equals(cancelInvoice)) {
                    if (cancelInvoice.endsWith("NV")) {
                        if (object.getId().getStaffId().compareTo(userToken.getUserID()) != 0) {
                            continue;
                        }
                    } else if (cancelInvoice.endsWith("CHT")) {
                        if (object.getId().getShopId().compareTo(userToken.getShopId()) != 0) {
                            continue;
                        }
                    }
                    saleManagmentForm.setCancelTrans(true);
                }
            }

        } catch (Exception ex) {
        }
    }

    /*
     * Author: ThanhNC Date created: 18/06/20009 Description: Xem chi tiet
     * serial trong giao dich
     */
    public String viewTransDetailSerial() throws Exception {
        log.debug("# Begin method SaleManagmentDAO.viewTransDetailSerial");
        pageForward = "saleManagmentDetailSerial";
        HttpServletRequest req = getRequest();
        try {
            String trans = req.getParameter("saleTransDetailId");
            if (trans == null || trans.trim().equals("")) {
                req.setAttribute("resultViewSaleDetailSerial", "saleManagment.viewDetailSerial.error.noTransDetailId");
                return pageForward;
            }
            Long saleTransDetailId = Long.parseLong(trans);
            String SQL_SELECT_TRANS_DETAIL = " from SaleTransSerial where saleTransDetailId = ? ";
            Query q = getSession().createQuery(SQL_SELECT_TRANS_DETAIL);
            q.setParameter(0, saleTransDetailId);
            List lstSaleTransDetailSerial = q.list();
            if (lstSaleTransDetailSerial.size() > 0) {
                req.setAttribute("lstSaleTransDetailSerial", lstSaleTransDetailSerial);
            } else {
                req.setAttribute("resultViewSaleDetailSerial", "saleManagment.viewDetailSerial.error.noSaleDetail");
            }

        } catch (Exception ex) {
            req.setAttribute("resultViewSaleDetailSerial", "saleManagment.viewDetail.error.undefine");
        }
        return pageForward;
    }
    /*
     * Author: ThanhNC Date created: 18/06/20009 Description: In hoa don ban
     * hang
     */
    public String printInvoice() throws Exception {
        log.debug("# Begin method SaleManagmentDAO.printInvoice");
        pageForward = "saleManagmentPrintInvoice";
        HttpServletRequest req = getRequest();
        try {
            Long invoiceUsedId = saleManagmentForm.getInvoiceId();
            if (invoiceUsedId == null) {
                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.noInvoiceId");
                return pageForward;
            }
            InvoicePrinterV2DAO invoicePrinterDAO = new InvoicePrinterV2DAO();
            invoicePrinterDAO.setSession(this.getSession());
            String path = invoicePrinterDAO.printSaleTransInvoice(invoiceUsedId, null, 1L);
            if (path.equals("0")) {
                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.exception");
                return pageForward;
            }
            if (path.equals("2")) {
                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.noConfig");
                return pageForward;
            }
            if (path.equals("3")) {
                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.noPageSize");
                return pageForward;
            }
            req.setAttribute("invoicePrintPath", path);

        } catch (Exception ex) {
            req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.undefine");
        }
        return pageForward;
    }
    /*
     * Author: ThanhNC Date created: 18/06/20009 Description: Huy giao dich ban
     * hang
     */
    public String cancelTrans() throws Exception {
        log.debug("# Begin method SaleManagmentDAO.cancelTrans");
        pageForward = "saleManagmentPrintInvoice";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        /**
         * THANHNC_20110215_START log.
         */
        String function = "com.viettel.im.database.DAO.SaleManagmentDAO.cancelTrans";
        Long callId = getApCallId();
        Date startDate = new Date();
        Date startUpdate3 = new Date();
        logStartUser(startDate, function, callId, userToken.getLoginName());
        /**
         * End log
         */
        //tamdt1, merge code, 17/02/2011, start
        ViettelService request = new ViettelService();
        AnypaySession anypaySession = null;//new AnypaySession();
//        anypaySession.beginAnypayTransaction();
        AnypayLogic anyPayLogic = null;//new AnypayLogic(anypaySession);
        AnypayMsg anyPayMsg = null;
        //tamdt1, merge code, 17/02/2011, end

        Session session = getSession();

        Session cmPreSession = null;

        try {
            String saleTransCode = saleManagmentForm.getSaleTransCode();
            if (saleTransCode == null || saleTransCode.equals("")) {
                req.setAttribute("resultPrintInvoice", "saleManagment.cancel.error.noSaleTransCode");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "saleManagment.cancel.error.noSaleTransCode");
                return pageForward;
            }
            SaleTransDAO saleTransDAO = new SaleTransDAO();
            saleTransDAO.setSession(this.getSession());
            SaleTrans saleTrans = saleTransDAO.findBySaleTransCode(saleTransCode);
            Long saleTransId = saleTrans.getSaleTransId();
            if (saleTrans == null) {
                req.setAttribute("resultPrintInvoice", "saleManagment.cancel.error.saleTransNotExits");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "saleManagment.cancel.error.saleTransNotExits");
                return pageForward;
            }

            //Check quyen huy giao dich voi truong hop giao dich tu CM day sang
            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_SERVICE)
                    || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COL_RETAIL)
                    || (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION) && saleTrans.getSaleServiceId() != null)) {
                if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("cancelSaleTransCM"), req)) {
                    req.setAttribute("resultPrintInvoice", "saleManagment.cancel.error.accessDeny");
                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "saleManagment.cancel.error.accessDeny");
                    return pageForward;
                }
            }
            // tannh08082018 : kiem tra neu gap các ly do sau tu system CM thi khong cho huy giao dich
            //ReasonId() == 302261 tuc la : Reason pre paid for fix broad band subs
            //ReasonId() == 201050 tuc la : Extend used-time for ftth prepaid sub
            //ReasonId() == 302201 tuc la : Change FTTH from Pos-paid to Pre-Paid
            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_SERVICE)
                    && (saleTrans.getReasonId() == null || saleTrans.getReasonId() == 302261 || saleTrans.getReasonId() == 201050
                    || saleTrans.getReasonId() == 302201)) {
                req.setAttribute("resultPrintInvoice", "saleManagment.cancel.error.accessDeny");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "saleManagment.cancel.error.accessDeny");
                return pageForward;
            }

            if (saleTrans.getStatus().equals(Constant.SALE_TRANS_STATUS_CANCEL)) {
                req.setAttribute("resultPrintInvoice", "saleManagment.cancel.error.saleTransReadyCanceled");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "saleManagment.cancel.error.saleTransReadyCanceled");
                return pageForward;
            }
            if (saleTrans.getStatus().equals(Constant.SALE_TRANS_STATUS_BILLED)) {
                req.setAttribute("resultPrintInvoice", "saleManagment.cancel.error.saleTransBilled");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "saleManagment.cancel.error.saleTransBilled");
                return pageForward;
            }

            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT)) {
                StockTransDAO stockTransDAO = new StockTransDAO();
                stockTransDAO.setSession(this.getSession());
                StockTrans stockTrans = stockTransDAO.findById(saleTrans.getStockTransId());
                if (stockTrans == null || stockTrans.getStockTransId() == null) {
                    req.setAttribute("resultPrintInvoice", "Lỗi. Không tìm thấy thông tin phiếu xuất kho!");
                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Khong tim thay phieu xuat kho");
                    return pageForward;
                }
                if (stockTrans.getStockTransStatus().intValue() == 4) {
                    req.setAttribute("resultPrintInvoice", "Giao dịch đã xuất kho, không huỷ được giao dịch!");
                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Giao dich da xuat kho khong duoc huy giao dich");
                    return pageForward;
                }
            }

            //TrongLV
            //Check quyen huy giao dich
            checkAuthoritySaleTrans(saleTrans.getSaleTransId());
            if (!saleManagmentForm.getCancelTrans()) {
                req.setAttribute("resultPrintInvoice", "E.200046");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Ban khong co quyen huy giao dich");
                return pageForward;
            }

            //R2265_NEW_DUCTM6_20120515_START
            //Check điều kiện hủy giao dịch bán thẻ cào
            if (ConfigParam.CHECK_CANCEL_SALE_TRANS_CARD) {/* TrongLV bo sung tham so check trien khai R2265 */
                Long checkCard = checkCancelCard(this.getSession(), saleTrans.getSaleTransId());
                if (checkCard.equals(1L)) {
                    //Kiểm tra user có quyền hủy
                    boolean checkAuthor
                            = AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_TRANS_CARD), req);
                    //Nếu không có quyền báo lỗi
                    if (checkAuthor == false) {
                        //Bạn không có quyền hủy giao dịch bán thẻ cào số lượng/giá trị lớn. Xin vui lòng liên hệ với Movitel để được hỗ trợ
                        req.setAttribute("resultPrintInvoice", "E.200045");
                        logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Ban khong co quyen huy giao dich the cao");
                        return pageForward;
                    }
                } else {
//                req.setAttribute("resultPrintInvoice", "saleManagment.cancel.error.saleTransCard");
//                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Ban khong co quyen huy giao dich the cao");
//                return pageForward;
                }
            }
            //R2265_NEW_DUCTM6_20120515_END

            //Giao dich ban cho CTV
            cmPreSession = null;//getSession("cm_pre");
//            cmPreSession.beginTransaction();
            if (!checkCancelKitCTV(saleTrans, userToken, cmPreSession, req)) {
                if (cmPreSession != null) {
                    cmPreSession.getTransaction().rollback();
                }

//                req.setAttribute("resultPrintInvoice", "Error. KIT_MFS is activated 900!");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Giao dich co KIT CTV da kich hoat");
                return pageForward;
            }

            //Cap nhat trang thai giao dich --> da huy
            Date startUpdate1 = new Date();
            logStartCall(startUpdate1, "cancelTrans.UpdateTransStatus", callId);

            saleTrans.setStatus(Constant.SALE_TRANS_STATUS_CANCEL);
            //TrongLV
            //Destroy Date
            saleTrans.setDestroyDate(getSysdate());
            saleTrans.setDestroyUser(userToken.getLoginName());
            String mvarNote = saleTrans.getNote();
            if (mvarNote == null) {
                mvarNote = "";
            }
            if (!(saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_SERVICE) && !saleManagmentForm.getBackGood())) {
                mvarNote += " (Recover SIM)";
            } else {
                mvarNote += " (Recover SIM)";
            }

            //Cap nhat lai gia tri hang hoa
            Long ownerId = saleTrans.getStaffId() == null ? saleTrans.getShopId() : saleTrans.getStaffId();
            Long ownerType = saleTrans.getStaffId() == null ? Constant.OWNER_TYPE_SHOP : Constant.OWNER_TYPE_STAFF;
            boolean isInVT = isInVT(ownerId, ownerType);
            if (isInVT) {
//                    Double saleTransAmount = sumAmountBySaleTransId(saleTrans.getSaleTransId());
                Double saleTransAmount = sumAmountBySaleTransIdToCancel(saleTrans.getSaleTransId());
                if (saleTransAmount != null && saleTransAmount >= 0) {
                    if (checkCurrentDebitWhenImplementTrans(ownerId, ownerType, saleTransAmount)) {
                        if (!addCurrentDebit(ownerId, ownerType, saleTransAmount)) {
                            req.setAttribute("resultPrintInvoice", getText("ERR.LIMIT.0001"));
                            this.getSession().clear();
                            this.getSession().getTransaction().rollback();
                            this.getSession().getTransaction().begin();

                            if (cmPreSession != null) {
                                cmPreSession.getTransaction().rollback();
                            }

                            return pageForward;
                        }
                    } else {
                        req.setAttribute("resultPrintInvoice", getText("ERR.LIMIT.0002"));
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();

                        if (cmPreSession != null) {
                            cmPreSession.getTransaction().rollback();
                        }

                        return pageForward;
                    }
                } else {
                    req.setAttribute("resultPrintInvoice", getText("ERR.LIMIT.0014"));
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().getTransaction().begin();

                    if (cmPreSession != null) {
                        cmPreSession.getTransaction().rollback();
                    }

                    return pageForward;
                }
            }

            /* CAP NHAT STATUS = 4 */
            session.save(saleTrans);

            //Truong hop ban hang cho dai ly cap nhat lai trang thai thanh toan cua giao dich xuat kho
            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT)) {
                String SQL_UPDATE_STOCK_TRANS = " update STOCK_TRANS set PAY_STATUS = ? where STOCK_TRANS_ID = ? ";
                Query qUpdate = session.createSQLQuery(SQL_UPDATE_STOCK_TRANS);
                qUpdate.setParameter(0, Constant.NOT_PAY);
                qUpdate.setParameter(1, saleTrans.getStockTransId());
                int resultUpdateStockTrans = qUpdate.executeUpdate();
            }

            //Khoi tao danh sach chi tiet giao dich
            List<SaleTransDetail> listSaleTransDetail = null;

            //Cap nhat trang thai serial cho cac mat hang da ban khoi kho
            String SQL_SELECT_SALE_TRANS_DETAIL = " from SaleTransDetail where saleTransId = ? ";
            Query q = session.createQuery(SQL_SELECT_SALE_TRANS_DETAIL);
            q.setParameter(0, saleTrans.getSaleTransId());

            //TrongLV
            //23/11/2009
            //Huy giao dich ban TCDT -> lay tong tien giao dich
            Long amountRecharge = -1L;

            //17/12/2009
            //Huy GD lam DV, neu khong hoi kho -> khong xu ly
            if (!(saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_SERVICE) && !saleManagmentForm.getBackGood())) {
                listSaleTransDetail = q.list();
            }

            //Duyet danh sach chi tiet giao dich
            if (listSaleTransDetail != null && listSaleTransDetail.size() > 0) {
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                baseStockDAO.setSession(this.getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());

                //thinhph2 -- lay tong tien giao dich
                Shop shopImp = null;
                if (saleTrans.getShopId() != null) {
                    shopImp = shopDAO.findById(saleTrans.getShopId());
                }
                if (shopImp == null) {
                    req.setAttribute("resultPrintInvoice", "reportInvoice.error.shopNotExist");
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().getTransaction().begin();

                    if (cmPreSession != null) {
                        cmPreSession.getTransaction().rollback();
                    }

                    return pageForward;
                }
                if (!saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT) && !saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_INTERNAL)) {//Ko check han muc voi saleTransType = 2, 6
                    Double amountDebitPre = 0D;
                    for (SaleTransDetail saleTransDetail : listSaleTransDetail) {
                        Long stockModelIdPre = saleTransDetail.getStockModelId() != null ? saleTransDetail.getStockModelId() : -1L;
                        StockModelDAO stockModelDAO = new StockModelDAO();
                        stockModelDAO.setSession(session);
                        StockModel stockModelPre = stockModelDAO.findById(stockModelIdPre);
                        if (stockModelPre == null) {
                            //bo qua cac truong hop saleTransDetail la saleServices -> stockModel ko duoc luu
                            continue;
                        }
                        //Long stockTypeId = stockModelPre.getStockTypeId();
                        Double price = new PriceDAO().findSaleToRetailPrice(stockModelPre.getStockModelId(), shopImp.getPricePolicy());

                        //cong han muc so luong - nguoi ban
                        if (price == null) {
                            price = 0D;
                        }
                        amountDebitPre += price * saleTransDetail.getQuantity();
                    }

                    String[] arrMess = new String[3];
                    if (amountDebitPre != null && amountDebitPre.compareTo(0D) > 0) {
                        //Begin 25042013 : R693 thinhph2 bo sung check han muc cho nhan vien
                        Double currentDebit = getCurrentDebit(Constant.OWNER_TYPE_STAFF, saleTrans.getStaffId(), shopImp.getPricePolicy());
                        /*neu cap nhat stock_total truoc thi amount_debit phai dat = 0 */
                        arrMess = checkDebitStaffLimit(saleTrans.getStaffId(), Constant.OWNER_TYPE_STAFF, currentDebit, amountDebitPre, shopImp.getShopId(), saleTrans.getStockTransId());
                        if (!arrMess[0].equals("")) {
                            req.setAttribute("resultPrintInvoice", arrMess[0]);
                            this.getSession().clear();
                            this.getSession().getTransaction().rollback();
                            this.getSession().getTransaction().begin();

                            if (cmPreSession != null) {
                                cmPreSession.getTransaction().rollback();
                            }

                            return pageForward;
                        }
                    }
                }
                for (int i = 0; i < listSaleTransDetail.size(); i++) {
                    SaleTransDetail saleTransDetail = listSaleTransDetail.get(i);

                    /* NEU LA DICH VU BAN HANG */
                    if (saleTransDetail.getStockModelId() == null) {
                        continue;
                    }

                    //TrongLV
                    //20091123
                    if (amountRecharge.compareTo(-1L) == 0) {
                        amountRecharge = saleTransDetail.getQuantity();
                    }

                    //lay danh sach cac serial tuong ung voi saleTransDetail nay, 2 buoc
                    //      - cap nhat lai serial ve trang thai binh thuong
                    //      - cong so luong da update vao bang stockTotal
                    //cap nhat lai serial ve trang thai binh thuong
                    Long saleTransDetailId = saleTransDetail.getSaleTransDetailId();
                    Long stockModelId = saleTransDetail.getStockModelId() != null ? saleTransDetail.getStockModelId() : -1L;
                    StockModelDAO stockModelDAO = new StockModelDAO();
                    stockModelDAO.setSession(this.getSession());
                    StockModel stockModel = stockModelDAO.findById(stockModelId);
                    if (stockModel == null) {
                        //bo qua cac truong hop saleTransDetail la saleServices -> stockModel ko duoc luu
                        continue;
                    }
                    Long stockTypeId = stockModel.getStockTypeId();

                    //TrongLV - 18/12/2009 - Neu la GD lam DV & mat hang la SIM thi bo qua
                    if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_SERVICE)
                            && (stockTypeId != null)
                            && (stockTypeId.compareTo(Constant.STOCK_SIM_POST_PAID) == 0
                            || stockTypeId.compareTo(Constant.STOCK_SIM_PRE_PAID) == 0)) {
                        continue;
                    }

                    /* DANH INDEX THEO SALE_TRANS_DETAIL_ID */
                    String tableName = baseStockDAO.getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
//                        String strSaleTransSerial = " from SaleTransSerial where saleTransDetailId = ? and saleTransDate>= trunc(?)  and saleTransDate< ? ";
                    String strSaleTransSerial = " from SaleTransSerial where saleTransDetailId = ? ";
                    Query querySaleTransSerial = getSession().createQuery(strSaleTransSerial);
                    querySaleTransSerial.setParameter(0, saleTransDetailId);
//                        querySaleTransSerial.setParameter(1, DateTimeUtils.addDate(saleTransDetail.getSaleTransDate(), -1));
//                        querySaleTransSerial.setParameter(2, DateTimeUtils.addDate(saleTransDetail.getSaleTransDate(), 1));
                    List<SaleTransSerial> listSerial = querySaleTransSerial.list();
//                        NOT_DONE
                    if (listSerial != null) {
                        for (int j = 0; j < listSerial.size(); j++) {
                            SaleTransSerial stockSerial = listSerial.get(j);
                            if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_KIT)) {
                                ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                                reqActiveKitDAO.setSession(getSession());
                                reqActiveKitDAO.saveReqActiveKit(stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.SALE_TYPE_DESTROY, saleTransDetail.getSaleTransId(),
                                        Long.parseLong(saleTrans.getSaleTransType()), saleTrans.getSaleTransDate());
                            }

                            //DucTM_20120516_Với stock_card thực hiện insert vào VC_REQUEST
                            if (ConfigParam.CHECK_CANCEL_SALE_TRANS_CARD) {/* TrongLV bo sung tham so check trien khai R2265 */
                                if (Constant.STOCK_CARD.equals(stockTypeId)) {
                                    //DucTM_Insert vào VC_REQUEST dải thẻ cào hủy
                                    VcRequestDAO vcRequestDAO = new VcRequestDAO();
                                    vcRequestDAO.insertVcRequest(session, stockSerial.getFromSerial(),
                                            stockSerial.getToSerial(), saleTrans.getSaleTransType(),
                                            saleTrans.getSaleTransId(), Constant.ACTIVE_TYPE_CARD_DESTROY);
                                }
                            }
                        }
                    }

                    logEndCall(startUpdate1, new Date(), "cancelTrans.UpdateTransStatus", callId);

                    Date startUpdate2 = new Date();
                    logStartCall(startUpdate2, "cancelTrans.UpdateDetailSerial", callId);

                    //ThanhNC modify on 28/10/2009
                    //Des: Doi voi truong hop huy giao dich ban hang cho dai ly --> khong cap nhat lai trang thai serial trong kho
                    if (listSerial != null && listSerial.size() > 0
                            && !saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT)) {
                        for (int j = 0; j < listSerial.size(); j++) {
                            SaleTransSerial saleTransSerial = listSerial.get(j);
                            //cap nhap trang thai cua cac serial tu trang thai da ban -> trang thai serial trong kho
                            //rieng doi voi truong hop so mobile, homephone, pstn -> cap nhat trang thai isdn
                            if (stockTypeId.equals(Constant.STOCK_ISDN_MOBILE)
                                    || stockTypeId.equals(Constant.STOCK_ISDN_HOMEPHONE)
                                    || stockTypeId.equals(Constant.STOCK_ISDN_PSTN)) {
                                //truong hop sim so -> update isdn thanh so moi
                                String strUpdateSerialQuery = " update " + tableName + " "
                                        + "set status = ? "
                                        + "where to_number(isdn) >= ? and to_number(isdn) <= ? ";
                                Query queryUpdateSerial = session.createSQLQuery(strUpdateSerialQuery);
                                queryUpdateSerial.setParameter(0, Constant.STATUS_ISDN_NEW);
                                queryUpdateSerial.setParameter(1, saleTransSerial.getFromSerial());
                                queryUpdateSerial.setParameter(2, saleTransSerial.getToSerial());
                                queryUpdateSerial.executeUpdate();
                            } else {
                                /* GIAO DICH BAN CHO CTV QUA WEB (=3) HAY QUA SIM (=21) */
 /* UPDATE SERIAL VE KHO NVQL */
                                if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COLLABORATOR)
                                        || saleTrans.getSaleTransType().equals(SALE_TRANS_TYPE_STK_MANAGER_SALE_CHANNEL.toString())) {
                                    String strUpdateSerialQuery = "update " + tableName + " "
                                            + "set status = ?, owner_id = ?, owner_type = ? ";
                                    //DucTM_20120516_Với stock_card thực hiện update active_status = 2
                                    if (ConfigParam.CHECK_CANCEL_SALE_TRANS_CARD) {/* TrongLV bo sung tham so check trien khai R2265 */
                                        if (Constant.STOCK_CARD.equals(stockTypeId)) {
                                            strUpdateSerialQuery += " ,active_status = " + Constant.ACTIVE_STATUS_CARD_DEACTIVING;
                                            // queryUpdateSerial.setParameter(7, Constant.STATUS_SIM_IN_STOCK);
                                        }
                                    }
                                    //End_DucTM
                                    strUpdateSerialQuery += "where    1 = 1 "
                                            + "         and to_number(serial) >= ?" //+ saleTransSerial.getFromSerial()
                                            + "         and to_number(serial) <= ?" //+ saleTransSerial.getToSerial()
                                            + "         and stock_model_id = ? "
                                            + "         and status = ? ";

                                    Query queryUpdateSerial = session.createSQLQuery(strUpdateSerialQuery);
                                    queryUpdateSerial.setParameter(0, Constant.GOOD_IN_STOCK_STATUS);
                                    queryUpdateSerial.setParameter(1, saleTrans.getStaffId());
                                    queryUpdateSerial.setParameter(2, Constant.OWNER_TYPE_STAFF);
                                    //DucTM_Fix_ATTT_20120517
                                    queryUpdateSerial.setParameter(3, saleTransSerial.getFromSerial());
                                    queryUpdateSerial.setParameter(4, saleTransSerial.getToSerial());
                                    queryUpdateSerial.setParameter(5, stockModelId);
                                    queryUpdateSerial.setParameter(6, Constant.GOOD_SALE_STATUS); //ĐÃ HỦY

                                    int numberRowUpdated = queryUpdateSerial.executeUpdate();

                                    if (numberRowUpdated != saleTransSerial.getQuantity().intValue()) {
                                        //so luong cap nhat khong du
                                        req.setAttribute("resultPrintInvoice", "!!!Error Can not update goods code " + stockModel.getStockModelCode() + " serial range from " + saleTransSerial.getFromSerial() + " to " + saleTransSerial.getToSerial());
                                        session.clear();
                                        session.getTransaction().rollback();
                                        session.beginTransaction();

                                        if (cmPreSession != null) {
                                            cmPreSession.getTransaction().rollback();
                                        }
                                        return pageForward;
                                    }
                                } else {
                                    //cac truong hop khac -> update serial thanh trong kho
                                    String strUpdateSerialQuery = "update " + tableName + " "
                                            + "set status = ? ";
                                    //DucTM_20120516_Với stock_card thực hiện update active_status = 2
                                    if (ConfigParam.CHECK_CANCEL_SALE_TRANS_CARD) {/* TrongLV bo sung tham so check trien khai R2265 */
                                        if (Constant.STOCK_CARD.equals(stockTypeId)) {
                                            strUpdateSerialQuery += " ,active_status = " + Constant.ACTIVE_STATUS_CARD_DEACTIVING;
                                        }
                                    }
                                    //End_DucTM
                                    strUpdateSerialQuery += "where    1 = 1 "
                                            + "         and to_number(serial) >= " + saleTransSerial.getFromSerial()
                                            + "         and to_number(serial) <= " + saleTransSerial.getToSerial()
                                            + "         and stock_model_id = ? "
                                            + "         and status = ? ";
                                    Query queryUpdateSerial = session.createSQLQuery(strUpdateSerialQuery);
                                    queryUpdateSerial.setParameter(0, Constant.GOOD_IN_STOCK_STATUS);
//                                        queryUpdateSerial.setParameter(1, saleTransSerial.getFromSerial());
//                                        queryUpdateSerial.setParameter(2, saleTransSerial.getToSerial());
                                    queryUpdateSerial.setParameter(1, stockModelId);
                                    queryUpdateSerial.setParameter(2, Constant.GOOD_SALE_STATUS);
                                    int numberRowUpdated = queryUpdateSerial.executeUpdate();

                                    if (numberRowUpdated != saleTransSerial.getQuantity().intValue()) {
                                        //so luong cap nhat khong du
                                        req.setAttribute("resultPrintInvoice", "!!!Error Can not update goods code " + stockModel.getStockModelCode() + " serial range from " + saleTransSerial.getFromSerial() + " to " + saleTransSerial.getToSerial());
                                        session.clear();
                                        session.getTransaction().rollback();
                                        session.beginTransaction();

                                        if (cmPreSession != null) {
                                            cmPreSession.getTransaction().rollback();
                                        }

                                        logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Error update range serial");
                                        return pageForward;
                                    }
                                }
                                //tamdt1, end
                            }
                        }
                    }

                    logEndCall(startUpdate2, new Date(), "cancelTrans.UpdateDetailSerial", callId);
                    startUpdate3 = new Date();
                    logStartCall(startUpdate3, "cancelTrans.UpdateStockTotalAndDebit", callId);
                    //cong so luong da update vao bang stockTotal
                    //Truong hop ban hang cho dai ly --> khong cap nhat lai stock_total
                    if (!saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT)) {
                        //R499 trung dh3 commnet

                        //R499 trung dh3 comment end
                        //R499 trung dh3 add
//                            List listUpdateStockTotalParam = new ArrayList();
//                            String strUpdateStockTotalQuery = "update stock_total "
//                                    + "set quantity = quantity + ? "
//                                    + ", quantity_issue = quantity_issue + ? "
//                                    + ", modified_date = sysdate "
//                                    + "where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? ";
//                            Query qUpdateStockTotal = getSession().createSQLQuery(strUpdateStockTotalQuery);
                        Long ownerId2 = 0L;
                        Long ownerType2 = 0L;
                        Long stateId2 = 0L;
//                            listUpdateStockTotalParam.add(saleTransDetail.getQuantity());
//                            listUpdateStockTotalParam.add(saleTransDetail.getQuantity());

                        //Neu la giao dich ban hang noi bo --> cong hang vao kho cua hang cac truong hop ban hang khac cong hang vao kho nhan vien
//                            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_INTERNAL)) {
//                                ownerId2 = saleTrans.getShopId();
//                                ownerType2 = Constant.OWNER_TYPE_SHOP;
////                                listUpdateStockTotalParam.add(saleTrans.getShopId());
////                                listUpdateStockTotalParam.add(Constant.OWNER_TYPE_SHOP);
//                            } else {
//                                if (saleTrans.getStaffId() == null || saleTrans.getStaffId().equals(0L)) {
////                                    listUpdateStockTotalParam.add(saleTrans.getShopId());
////                                    listUpdateStockTotalParam.add(Constant.OWNER_TYPE_SHOP);
//                                    ownerId2 = saleTrans.getShopId();
//                                    ownerType2 = Constant.OWNER_TYPE_SHOP;
//                                } else {
////                                    listUpdateStockTotalParam.add(saleTrans.getStaffId());
////                                    listUpdateStockTotalParam.add(Constant.OWNER_TYPE_STAFF);
//                                    ownerId2 = saleTrans.getStaffId();
//                                    ownerType2 = Constant.OWNER_TYPE_STAFF;
//
//                                }
//                            }
                        if (saleTrans.getStaffId() == null || saleTrans.getStaffId().equals(0L)) {
//                                    listUpdateStockTotalParam.add(saleTrans.getShopId());
//                                    listUpdateStockTotalParam.add(Constant.OWNER_TYPE_SHOP);
                            ownerId2 = saleTrans.getShopId();
                            ownerType2 = Constant.OWNER_TYPE_SHOP;
                        } else {
//                                    listUpdateStockTotalParam.add(saleTrans.getStaffId());
//                                    listUpdateStockTotalParam.add(Constant.OWNER_TYPE_STAFF);
                            ownerId2 = saleTrans.getStaffId();
                            ownerType2 = Constant.OWNER_TYPE_STAFF;

                        }

//                            listUpdateStockTotalParam.add(stockModelId);
//                            listUpdateStockTotalParam.add(Constant.STATE_NEW);
//                            for (int indexOfListUpdateStockTotalParam = 0; indexOfListUpdateStockTotalParam < listUpdateStockTotalParam.size(); indexOfListUpdateStockTotalParam++) {
//                                qUpdateStockTotal.setParameter(indexOfListUpdateStockTotalParam, listUpdateStockTotalParam.get(indexOfListUpdateStockTotalParam));
//                            }
                        // qUpdateStockTotal.executeUpdate();
                        StockTotalAuditDAO.changeStockTotal(getSession(), ownerId2, ownerType2, stockModelId,
                                Constant.STATE_NEW, saleTransDetail.getQuantity(), saleTransDetail.getQuantity(), null,
                                userToken.getUserID(), saleTrans.getReasonId(), saleTrans.getSaleTransId(), saleTrans.getSaleTransCode(), saleTrans.getSaleTransType().toString(), StockTotalAuditDAO.SourceType.SALE_TRANS);
                    }

                    //TrongLV
                    //20091123
                    //cancel recharge of AnyPayAgent (saletransType = 8)
                    //Chi check voi giao dich ban TCDT
                    /* BAN THE CAO DIEN TU QUA WEB HOAC QUA SIM DA NANG */
                    if ( //                                saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COLLABORATOR) 
                            //                                && 
                            stockModel.getStockModelCode().equals(Constant.STOCK_MODEL_CODE_TCDT)
                            && saleTrans.getReceiverId() != null
                            && saleTrans.getReceiverType() != null
                            && amountRecharge.compareTo(-1L) != 0) {
                        ReasonDAO reasonDAO = new ReasonDAO();
                        reasonDAO.setSession(this.getSession());
                        Reason reason = new Reason();
                        reason.setReasonType("");
                        if (saleTrans.getReasonId() != null) {
                            reason = reasonDAO.findById(saleTrans.getReasonId());
                        }
                        //Lay accountId ben FPT
                        String sql = "select STOCK_ID from stock_owner_tmp where 1 = 1 and owner_id = ? and owner_type = ?";
                        Query query = getSession().createSQLQuery(sql);
                        query.setParameter(0, saleTrans.getReceiverId());
                        query.setParameter(1, saleTrans.getReceiverType().toString());
                        List list = query.list();
                        Iterator iter = list.iterator();
                        Long agentId = 0L;
                        while (iter.hasNext()) {
                            Object temp = (Object) iter.next();
                            agentId = new Long(temp.toString());
                        }
                        if (agentId.equals(0L)) {
                            req.setAttribute("resultPrintInvoice", "!!! Không tồn tại account bên FPT");
                            this.getSession().getTransaction().rollback();
                            this.getSession().beginTransaction();
                            this.getSession().clear();

                            if (cmPreSession != null) {
                                cmPreSession.getTransaction().rollback();

                            }

                            logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Account not exist (FPT)");
                            return pageForward;
                        } else {//Huy giao dich ban ANYPAY
                            try {
                                if (saleTrans.getInTransId() != null && !saleTrans.getInTransId().equals("")) {
                                    /* KHOI TAO */
                                    anypaySession = new AnypaySession(getSession("anypay"));
                                    anyPayLogic = new AnypayLogic(anypaySession);

                                    String error;
                                    Date startRec = new Date();
                                    logStartCall(startRec, "anyPayLogic.recoverSaleAnypay", callId, "agentId", agentId, "TransId", saleTrans.getInTransId());
                                    anyPayMsg = anyPayLogic.recoverSaleAnypay(getLanguage(), agentId, Long.parseLong(saleTrans.getInTransId()), userToken.getLoginName(), req.getRemoteAddr());
                                    logEndCall(startRec, new Date(), function, callId);
                                    error = anyPayMsg.getErrCode();
                                    if (error != null && !error.equals("")) {
                                        //rollback
                                        if (anypaySession != null) {
                                            anypaySession.rollbackAnypayTransaction();
                                        }

                                        req.setAttribute("resultPrintInvoice", error);
                                        this.getSession().getTransaction().rollback();
                                        this.getSession().beginTransaction();
                                        this.getSession().clear();

                                        if (cmPreSession != null) {
                                            cmPreSession.getTransaction().rollback();
                                        }

                                        logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), error);
                                        return pageForward;
                                    } else {
                                        req.setAttribute("resultPrintInvoice", "!!! Thực hiện hủy giao dịch thành công");
                                    }
                                } else {
                                    req.setAttribute("resultPrintInvoice", "!!! Không có thông tin giao dịch bên FPT");
                                    this.getSession().getTransaction().rollback();
                                    this.getSession().beginTransaction();
                                    this.getSession().clear();

                                    if (cmPreSession != null) {
                                        cmPreSession.getTransaction().rollback();
                                    }
                                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Trans not exist in FPT");
                                    return pageForward;
                                }
                            } catch (Exception ex) {

                                //rollback
                                if (anypaySession != null) {
                                    anypaySession.rollbackAnypayTransaction();
                                }

                                req.setAttribute("resultPrintInvoice", "!!! Lỗi kết nối với hệ thống FPT");
                                this.getSession().getTransaction().rollback();
                                this.getSession().beginTransaction();
                                this.getSession().clear();

                                if (cmPreSession != null) {
                                    cmPreSession.getTransaction().rollback();
                                }

                                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Exception2");
                                return pageForward;
                            }
                        }
                    }
                }

                /*
                 * Modified by: TrongLV Modified date: 16/09/2010 Purpose:
                 * cancel sale_transaction for sale_coll_to_retail change
                 * balance of account_balance: -amountDeposit & +amountTax
                 */
 /* NEU THUC HIEN TREN STK - CTV ban le ==> loai giao dich giong tren web */
                com.viettel.lib.database.DAO.BaseDAO baseDAO = new com.viettel.lib.database.DAO.BaseDAO();
//                if (!isInVT && !saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PUNISH)) {
                if (!isInVT) {
                    //Tru tien dat coc vao TKTT & Cong tien giao dich vao TKTT
                    AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
                    Double amountDeposit = saleTrans.getAmountService();
                    Double amountTax = saleTrans.getAmountTax();
                    if (amountDeposit == null) {
                        amountDeposit = 0.0;
                    }
                    if (amountTax == null) {
                        amountTax = 0.0;
                    }
                    String returnMsg = "";
                    com.viettel.lib.database.DAO.WebServiceDAO wsDAO = new com.viettel.lib.database.DAO.WebServiceDAO();
                    returnMsg = accountAgentDAO.addBalance(session, ownerId, ownerType,
                            wsDAO.getRequestTypeBySaleTransType(saleTrans.getSaleTransType(), false),
                            -1 * amountDeposit,
                            wsDAO.getRequestTypeBySaleTransType(saleTrans.getSaleTransType(), true),
                            amountTax,
                            getSysdate(), saleTrans.getSaleTransId(), userToken.getLoginName());
                    if (!returnMsg.equals("")) {
                        if (anypaySession != null) {
                            anypaySession.rollbackAnypayTransaction();
                        }
                        session.clear();
                        session.getTransaction().rollback();
                        session.beginTransaction();

                        if (cmPreSession != null) {
                            cmPreSession.getTransaction().rollback();
                        }

                        req.setAttribute("resultPrintInvoice", returnMsg);
                        log.debug("End method cancelTrans of SaleManagementDAO");

                        logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), returnMsg);
                        return pageForward;
                    }

                    AccountAgent accountAgent = null;
                    accountAgent = accountAgentDAO.findByOwnerIdAndOwnerType(session, ownerId, ownerType);
                    if (accountAgent != null
                            && accountAgent.getCheckIsdn() != null
                            && accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)
                            && accountAgent.getMsisdn() != null) {
                        String sendMess = String.format(getText("sms.2003"), NumberUtils.formatNumber(amountDeposit), NumberUtils.formatNumber(amountTax));
                        SmsClient.sendSMS155(accountAgent.getMsisdn(), sendMess);
                    }
                }

                this.getSession().flush();
                this.getSession().getTransaction().commit();
                this.getSession().beginTransaction();

                if (cmPreSession != null) {
                    cmPreSession.getTransaction().commit();
                }
            }
            
            // Da test case chinh -> dung . Tuy nhien  neu can dung thi bo comment ra
            // tannh2018208 : Neu la giao dich dat hang thi huy giao dich dat hang :
//            SaleTransOrderDAO saleTransOrderDAO = new SaleTransOrderDAO();
//            saleTransOrderDAO.setSession(this.getSession());
//            SaleTransOrder saleTransOrder = saleTransOrderDAO.findSaleTransOrderBySaleTransId(saleTransId);
//
//            if (saleTransOrder != null) {
//                saleTransOrder.setStatus(Constant.SALE_TRANS_STATUS_CANCEL);
//                //TrongLV
//                //Destroy Date
//                saleTransOrder.setDestroyDate(getSysdate());
//                saleTransOrder.setDestroyUser(userToken.getLoginName());
//                saleTransOrder.setIsCheck(2L);
//                /* CAP NHAT STATUS = 4 */
//                this.getSession().save(saleTransOrder);
//
//                OrderManagementDAO orderDao = new OrderManagementDAO();
//
//                // tra lai trang thai chua su dung cho giay nop tienn ( table bankTranInfo)
//                if (saleTransOrder.getOrderCode() != null && !saleTransOrder.getOrderCode().trim().isEmpty()) {
//                    orderDao.rollbackBankTranferInfo(userToken, saleTransOrder.getOrderCode(), saleTransOrder.getAmount(), saleTransOrder.getBankName());
//
//                }
//                if (saleTransOrder.getOrderCode2() != null && !saleTransOrder.getOrderCode2().trim().isEmpty()) {
//                    orderDao.rollbackBankTranferInfo(userToken, saleTransOrder.getOrderCode2(), saleTransOrder.getAmount2(), saleTransOrder.getBankName2());
//
//                }
//                if (saleTransOrder.getOrderCode3() != null && !saleTransOrder.getOrderCode3().trim().isEmpty()) {
//                    orderDao.rollbackBankTranferInfo(userToken, saleTransOrder.getOrderCode3(), saleTransOrder.getAmount3(), saleTransOrder.getBankName3());
//                }
//                
//                DbProcessor db = new DbProcessor();
//                String message = ResourceBundle.getBundle("config").getString("msgCancelTrans");
//                String userName = userToken.getLoginName();
//                StaffDAO staffDAO = new StaffDAO();
//                Staff staff = staffDAO.findByStaffId(saleTrans.getStaffId());
//                String isdn = "258" + db.getIsdnStaff(staff.getStaffCode());
//                String orderCode = "";
//                db.sendSms(isdn, message.replace("ZZZ", orderCode).replace("XXX", userName), "86904");
//
//            }

            if (connection != null && !connection.isClosed()) {
                connection.commit();
            }

            //commit
            if (anypaySession != null) {
                anypaySession.commitAnypayTransaction();
            }
            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            if (cmPreSession != null) {
                cmPreSession.getTransaction().commit();
            }

            logEndCall(startUpdate3, new Date(), "cancelTrans.UpdateStockTotalAndDebit", callId);
            req.setAttribute("resultPrintInvoice", "saleManagment.cancel.success");
            logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "OK");
            return pageForward;

        } catch (Exception ex) {
            log.debug("", ex);

            if (connection != null && !connection.isClosed()) {
                connection.rollback();
                connection.close();
            }
            //rollback
            if (anypaySession != null) {
                anypaySession.rollbackAnypayTransaction();
            }
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            if (cmPreSession != null) {
                cmPreSession.getTransaction().rollback();
            }
            throw ex;
        }
    }

    /**
     * @author: ductm6@viettel.com.vn
     * @desc : KIỂM TRA ĐIỀU KIỆN HỦY GIAO DỊCH
     * @modified: 17/05/2012
     * @R2265
     */
    private Long checkCancelCard(Session s, Long saleTransId) throws Exception {
        Long result = 0L;
        try {
            String sqlString = "{? = call F_CHECK_CANCEL_TRANS(?)}";
            CallableStatement cstmt = s.connection().prepareCall(sqlString);
            cstmt.setFetchSize(100);
            cstmt.registerOutParameter(1, OracleTypes.NUMBER);
            if (saleTransId != null) {
                cstmt.setLong(2, saleTransId);
            } else {
                cstmt.setNull(2, java.sql.Types.NUMERIC);
            }
            cstmt.executeQuery();
            //ResultSet rs = (ResultSet) cstmt.getObject(1);
            result = cstmt.getLong(1);
        } catch (Exception e) {
            log.debug("SaleManagment.checkCancelCard", e);
            e.printStackTrace();
            throw e;
            //return false;
        }
        return result;
    }

    private boolean checkCancelKitCTV_old(SaleTrans saleTrans) {
        boolean result = false;
        try {
            Date fromDate = DateTimeUtils.addDate(saleTrans.getSaleTransDate(), -1);
            Date toDate = DateTimeUtils.addDate(saleTrans.getSaleTransDate(), 1);

            String tmpList = Constant.STOCK_MODEL_CODE_KITCTV;
            while (tmpList.indexOf("(") >= 0) {
                tmpList = tmpList.replace("(", "");
            }
            while (tmpList.indexOf(")") >= 0) {
                tmpList = tmpList.replace(")", "");
            }

            String sql = " SELECT * "
                    + "    FROM sale_trans_detail b, sale_trans_serial a"
                    + " WHERE 1 = 1"
                    + "   AND b.sale_trans_detail_id = a.sale_trans_detail_id"
                    + "   AND b.SALE_TRANS_DATE >= ?"
                    + "   AND b.SALE_TRANS_DATE <= ?"
                    + "   AND b.sale_trans_id = ?"
                    + "   AND a.SALE_TRANS_DATE >= ?"
                    + "   AND a.SALE_TRANS_DATE <= ?"
                    + "   AND a.stock_model_id IN (select stock_model_id from Stock_Model where stock_model_code in (:tmpList)) "
                    + "   AND EXISTS ("
                    + "             SELECT *"
                    + "               FROM account_agent"
                    + "              WHERE 1 = 1 AND status <> 2 AND serial >= a.from_serial"
                    + "                    AND serial <= a.to_serial)"
                    + "";

            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, fromDate);
            query.setParameter(1, toDate);
            query.setParameter(2, saleTrans.getSaleTransId());
            query.setParameter(3, fromDate);
            query.setParameter(4, toDate);
            query.setParameterList("tmpList", tmpList.split(","));

            List list = query.list();
            if (list == null || list.isEmpty()) {
                result = true;
            }

        } catch (Exception ex) {
        }
        return result;
    }

    /**
     *
     */
    private boolean checkCancelKitCTV(SaleTrans saleTrans, UserToken userToken, Session cmPreSession, HttpServletRequest req) {
        try {
            Date fromDate = DateTimeUtils.addDate(saleTrans.getSaleTransDate(), -1);
            Date toDate = DateTimeUtils.addDate(saleTrans.getSaleTransDate(), 1);

            /* Lay danh sach ma mat hang KIT CTV */
            String tmpList = Constant.STOCK_MODEL_CODE_KITCTV;
            while (tmpList.indexOf("(") >= 0) {
                tmpList = tmpList.replace("(", "");
            }
            while (tmpList.indexOf(")") >= 0) {
                tmpList = tmpList.replace(")", "");
            }

            /* Lay danh sach serial KIT duoc ban */
            String sql = " SELECT from_serial "
                    + "    FROM sale_trans_detail b, sale_trans_serial a"
                    + " WHERE 1 = 1"
                    + "   AND b.sale_trans_detail_id = a.sale_trans_detail_id"
                    + "   AND b.SALE_TRANS_DATE >= ?"
                    + "   AND b.SALE_TRANS_DATE <= ?"
                    + "   AND b.sale_trans_id = ?"
                    + "   AND a.SALE_TRANS_DATE >= ?"
                    + "   AND a.SALE_TRANS_DATE <= ?"
                    + "   AND a.stock_model_id IN (select stock_model_id from Stock_Model where stock_model_code in (:tmpList) )";

            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, fromDate);
            query.setParameter(1, toDate);
            query.setParameter(2, saleTrans.getSaleTransId());
            query.setParameter(3, fromDate);
            query.setParameter(4, toDate);
            query.setParameterList("tmpList", tmpList.split(","));
            List list = query.list();

            /* KIEM TRA CO PHAI LA KIT DA NANG HAY KHONG */
            if (list == null || list.isEmpty()) {
                return true;
            }

            cmPreSession = getSession("cm_pre");
            cmPreSession.beginTransaction();

            Byte subType = 1;
            InterfaceCm inter = new InterfaceCm();
            Object subInfo;

            /* Lay thong tin KIT de kiem tra trang thai kich hoat 900 */
            for (int i = 0; i < list.size(); i++) {
                String serial = list.get(i).toString();
                if (serial == null || serial.equals("")) {
                    continue;
                }

                /* LAY THONG TIN KIT DA NANG */
                String sql2 = "From StockKit where to_number(serial) = ?";
                Query query2 = getSession().createQuery(sql2);
                query2.setParameter(0, serial);
                List<StockKit> list2 = query2.list();
                if (list2 == null || list2.isEmpty()) {
                    continue;
                }

                /* Kiem tra KIT da kich hoat 900 hay chua? Neu roi thi khong cho huy */
                for (int j = 0; j < list2.size(); j++) {
                    StockKit stockKit = list2.get(j);

                    /* LAY THONG TIN THUE BAO BEN CM */
                    subInfo = inter.getSubscriberInfoV2(stockKit.getIsdn(), "M", subType);
                    if (subInfo == null) {
                        continue;
                    }

                    /* NEU THUE BAO DA KICH HOAT 900 : THOAT KHOI FUNCTION VA THONG BAO LOI */
                    com.viettel.bccs.cm.database.BO.pre.SubMb subMb = (com.viettel.bccs.cm.database.BO.pre.SubMb) subInfo;
                    if (subMb.getStatus() == null || !subMb.getStatus().equals(2L)) {
                        continue;
                    }

                    if (subMb.getActStatus().equals(SimtkManagementDAO.activeStatus900)) {
                        req.setAttribute("resultPrintInvoice", "Error. This subscriber has activated! ");
                        return false;
                    }

                    /* GOI LIB CM */
                    int result = InterfaceCMToIM.cancelCustomerRegisted(subMb.getIsdn(), userToken.getLoginName(), userToken.getShopCode(), cmPreSession);
                    /* NEU HUY THONG TIN THANH CONG : TIEP TUC XU LY */
                    if (result == 0) {
                        continue;
                    }
                    /* NEU BI BI LOI : THOAT KHOI FUNCTION VA THONG BAO LOI */
                    if (result == -1) {
//                        req.setAttribute("resultPrintInvoice", "-1: định dạng Id_no không đúng");
                        req.setAttribute("resultPrintInvoice", "E.200030");
                    } else if (result == -2) {
//                        req.setAttribute("resultPrintInvoice", "-2: hết hạn mức thuê bao");
                        req.setAttribute("resultPrintInvoice", "E.200031");
                    } else if (result == -3) {
//                        req.setAttribute("resultPrintInvoice", "-3: Exception");
                        req.setAttribute("resultPrintInvoice", "E.200037");
                    } else if (result == -4) {
//                        req.setAttribute("resultPrintInvoice", "-4: shop, staff không đúng");
                        req.setAttribute("resultPrintInvoice", "E.200032");
                    } else if (result == -5) {
//                        req.setAttribute("resultPrintInvoice", "-5: isdn truyền vào không đúng");
                        req.setAttribute("resultPrintInvoice", "E.200033");
                    } else if (result == -6) {
//                        req.setAttribute("resultPrintInvoice", "-6: số thuê bao không tồn tại trên hệ thống");
                        req.setAttribute("resultPrintInvoice", "E.200034");
                    } else if (result == -7) {
//                        req.setAttribute("resultPrintInvoice", "-7: thuê bao đã được đăng kí thông tin");
                        req.setAttribute("resultPrintInvoice", "E.200035");
                    } else if (result == -8) {
                        req.setAttribute("resultPrintInvoice", "E.200036");
                    } else {
//                        req.setAttribute("resultPrintInvoice", "error");
                        req.setAttribute("resultPrintInvoice", "E.200037");
                    }
                    return false;
                }
            }
        } catch (Exception ex) {
            log.debug("", ex);
            return false;
        }
        /* NEU KHONG CO LOI : RETURN TRUE */
        return true;
    }

    public String pageNavigator() throws Exception {
        log.debug("# Begin method SaleManagmentDAO.pageNavigator");
        pageForward = "saleManagmentResult";
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getNameShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        } else {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add("");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose : lay danh sach cac nhan vien
     * thuoc mot don vi
     *
     */
    public List<ImSearchBean> getListStaff(ImSearchBean imSearchBean) {
        req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

//        strQuery1.append("and a.channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);
        //Modified by : TrongLV
        //Modified date : 2011-08-16
        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(100L);

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose : lay danh sach cac nhan vien
     * thuoc mot don vi
     *
     */
    public List<ImSearchBean> getNameStaff(ImSearchBean imSearchBean) {
        req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

//        strQuery1.append("and a.channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);
        //Modified by : TrongLV
        //Modified date : 2011-08-16
        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        } else {
            return listImSearchBean;
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like = ");
            listParameter1.add(imSearchBean.getName().trim().toLowerCase());
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    public Long getListStaffSize(ImSearchBean imSearchBean) {
        req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select count(*) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return 0L;
        }

//        strQuery1.append("and a.channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);
        //Modified by : TrongLV
        //Modified date : 2011-08-16
        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            return tmpList1.get(0);
        }

        return 0L;
    }

    /*
     * Author: ThanhNC Date created: 18/06/20009 Description: Huy giao dich ban
     * hang
     */
    public String cancelTransPos(Long id) throws Exception {
        log.debug("# Begin method SaleManagmentDAO.cancelTrans");
        try {
            Long saleTransPostId = id;
            if (saleTransPostId == null) {
                return "Giao dịch không tồn tại";
            }
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            SaleTransDAO saleTransDAO = new SaleTransDAO();
            saleTransDAO.setSession(this.getSession());
            SaleTrans saleTrans = saleTransDAO.findById(saleTransPostId);
            getSession().refresh(saleTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
            if (saleTrans == null) {
                return "Giao dịch không tồn tại";
            }

            if (saleTrans.getStatus().equals(Constant.SALE_TRANS_STATUS_CANCEL)) {
                return "Giao dịch bán hàng được hủy từ trước nên không thực hiện hủy được";
            }

            if (saleTrans.getStatus().equals(Constant.SALE_TRANS_STATUS_BILLED)) {
                return "Giao dịch bán hàng đã được lập hóa đơn từ trước nên không hủy được";
            }

            if (saleTrans.getStatus().equals(Constant.SALE_TRANS_STATUS_BILLED)) {
                return "Giao dịch đã lập hóa đơn";
            }

            saleTrans.setStatus(Constant.SALE_TRANS_STATUS_CANCEL);
            getSession().save(saleTrans);
            //Truong hop ban hang cho dai ly cap nhat lai trang thai thanh toan cua giao dich xuat kho
            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT)) {
                String SQL_UPDATE_STOCK_TRANS = " update STOCK_TRANS set PAY_STATUS = ? where STOCK_TRANS_ID = ? ";
                Query qUpdate = getSession().createSQLQuery(SQL_UPDATE_STOCK_TRANS);
                qUpdate.setParameter(0, Constant.NOT_PAY);
                qUpdate.setParameter(1, saleTrans.getStockTransId());
                qUpdate.executeUpdate();
            }

            //Khoi tao danh sach chi tiet giao dich
            List<SaleTransDetail> listSaleTransDetail = null;

            //Cap nhat trang thai serial cho cac mat hang da ban khoi kho
            String SQL_SELECT_SALE_TRANS_DETAIL = " from SaleTransDetail where saleTransId = ? ";
            Query q = getSession().createQuery(SQL_SELECT_SALE_TRANS_DETAIL);
            q.setParameter(0, saleTrans.getSaleTransId());
            listSaleTransDetail
                    = q.list();

            //Duyet danh sach chi tiet giao dich
            if (listSaleTransDetail != null && listSaleTransDetail.size() > 0) {
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                baseStockDAO.setSession(this.getSession());

                for (int i = 0; i
                        < listSaleTransDetail.size(); i++) {
                    SaleTransDetail saleTransDetail = listSaleTransDetail.get(i);
                    //lay danh sach cac serial tuong ung voi saleTransDetail nay, 2 buoc
                    //      - cap nhat lai serial ve trang thai binh thuong
                    //      - cong so luong da update vao bang stockTotal

                    //cap nhat lai serial ve trang thai binh thuong
                    Long saleTransDetailId = saleTransDetail.getSaleTransDetailId();
                    Long stockModelId = saleTransDetail.getStockModelId() != null ? saleTransDetail.getStockModelId() : -1L;
                    StockModelDAO stockModelDAO = new StockModelDAO();
                    stockModelDAO.setSession(this.getSession());
                    StockModel stockModel = stockModelDAO.findById(stockModelId);
                    if (stockModel == null) {
                        //bo qua cac truong hop saleTransDetail la saleServices -> stockModel ko duoc luu
                        continue;
                    }

                    Long stockTypeId = stockModel.getStockTypeId();

                    String tableName = baseStockDAO.getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
                    String strSaleTransSerial = " from SaleTransSerial where saleTransDetailId = ? ";
                    Query querySaleTransSerial = getSession().createQuery(strSaleTransSerial);
                    querySaleTransSerial.setParameter(0, saleTransDetailId);
                    List<SaleTransSerial> listSerial = querySaleTransSerial.list();
                    //ThanhNC modify on 28/10/2009
                    //Des: Doi voi truong hop huy giao dich ban hang cho dai ly --> khong cap nhat lai trang thais serial trong kho
                    if (listSerial != null && listSerial.size() > 0 && !saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT)) {
                        for (int j = 0; j
                                < listSerial.size(); j++) {
                            SaleTransSerial saleTransSerial = listSerial.get(j);
                            //cap nhap trang thai cua cac serial tu trang thai da ban -> trang thai serial trong kho
                            //rieng doi voi truong hop so mobile, homephone, pstn -> cap nhat trang thai isdn
                            if (stockTypeId.equals(Constant.STOCK_ISDN_MOBILE)
                                    || stockTypeId.equals(Constant.STOCK_ISDN_HOMEPHONE)
                                    || stockTypeId.equals(Constant.STOCK_ISDN_HOMEPHONE)) {
                                //truong hop sim so -> update isdn thanh so moi
                                String strUpdateSerialQuery = " update " + tableName + " "
                                        + "set status = ? "
                                        + "where to_number(isdn) >= ? and to_number(isdn) <= ? ";
                                Query queryUpdateSerial = getSession().createSQLQuery(strUpdateSerialQuery);
                                queryUpdateSerial.setParameter(0, Constant.STATUS_ISDN_NEW);
                                queryUpdateSerial.setParameter(1, saleTransSerial.getFromSerial());
                                queryUpdateSerial.setParameter(2, saleTransSerial.getToSerial());
                                queryUpdateSerial.executeUpdate();
                            } else {
                                //cac truong hop khac -> update serial thanh trong kho
                                String strUpdateSerialQuery = " update " + tableName + " "
                                        + "set status = ? "
                                        + "where to_number(serial) >= ? and to_number(serial) <= ? ";
                                Query queryUpdateSerial = getSession().createSQLQuery(strUpdateSerialQuery);
                                queryUpdateSerial.setParameter(0, Constant.STATUS_SIM_IN_STOCK);
                                queryUpdateSerial.setParameter(1, saleTransSerial.getFromSerial());
                                queryUpdateSerial.setParameter(2, saleTransSerial.getToSerial());
                                queryUpdateSerial.executeUpdate();
                            }

                        }
                    }

                    //cong so luong da update vao bang stockTotal
                    //Truong hop ban hang cho dai ly --> khong cap nhat lai stock_total
                    if (!saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT)) {
                        //                        List listUpdateStockTotalParam = new ArrayList();
//                        String strUpdateStockTotalQuery = "update stock_total "
//                                + "set quantity = quantity + ? "
//                                + ", quantity_issue = quantity_issue + ? "
//                                + ", modified_date = sysdate "
//                                + "where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? ";
//                        listUpdateStockTotalParam.add(saleTransDetail.getQuantity());
//                        listUpdateStockTotalParam.add(saleTransDetail.getQuantity());

//                        Query qUpdateStockTotal = getSession().createSQLQuery(strUpdateStockTotalQuery);
                        //Neu la giao dich ban hang noi bo --> cong hang vao kho cua hang cac truong hop ban hang khac cong hang vao kho nhan vien
                        Long shopId2 = 0L;
                        Long shopType2 = 0L;
                        if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_INTERNAL)) {
                            shopId2 = saleTrans.getShopId();
                            shopType2 = Constant.OWNER_TYPE_SHOP;
//                            listUpdateStockTotalParam.add(saleTrans.getShopId());
//                            listUpdateStockTotalParam.add(Constant.OWNER_TYPE_SHOP);
                        } else {
                            shopId2 = saleTrans.getStaffId();
                            shopType2 = Constant.OWNER_TYPE_STAFF;
//                            listUpdateStockTotalParam.add(saleTrans.getStaffId());
//                            listUpdateStockTotalParam.add(Constant.OWNER_TYPE_STAFF);
                        }

//                        listUpdateStockTotalParam.add(stockModelId);
//                        listUpdateStockTotalParam.add(Constant.STATE_NEW);
//                        for (int indexOfList = 0; indexOfList < listUpdateStockTotalParam.size(); indexOfList++) {
//                            qUpdateStockTotal.setParameter(indexOfList, listUpdateStockTotalParam.get(indexOfList));
//                        }
                        GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), shopId2, shopType2, stockModelId,
                                Constant.STATE_NEW, saleTransDetail.getQuantity(), saleTransDetail.getQuantity(), null,
                                userToken.getUserID(), saleTrans.getReasonId(), saleTrans.getSaleTransId(), saleTrans.getSaleTransCode(), saleTrans.getSaleTransType().toString(), StockTotalAuditDAO.SourceType.SALE_TRANS);
//                        if (genResult.isSuccess() != true) {
//                            return "SaleManagmentDAO.015";
//                        }
//
//                        //R499 trung dh3 add end
//                        qUpdateStockTotal.executeUpdate();
                    }

                }
                //this.getSession().getTransaction().commit();
                //this.getSession().beginTransaction();
            }

        } catch (Exception ex) {
            this.getSession().getTransaction().rollback();
            getSession().clear();
            this.getSession().beginTransaction();
        }

        return null;
    }

    private Long getRequestTypeBySaleTransType(String saleTransType, boolean isAdd) {
        if (saleTransType == null || saleTransType.trim().equals("")) {
            return null;
        }
        if (saleTransType.trim().equals(Constant.SALE_TRANS_TYPE_PUNISH)) {
            if (isAdd) {
                return Constant.DEPOSIT_TRANS_TYPE_ADD_PUNISH;
            } else {
                return Constant.DEPOSIT_TRANS_TYPE_MINUS_PUNISH;
            }
        }
        if (saleTransType.trim().equals(Constant.SALE_TRANS_TYPE_RETAIL_FOR_COL)) {
            if (isAdd) {
                return Constant.DEPOSIT_TRANS_TYPE_ADD_COLL_RETAIL;
            } else {
                return Constant.DEPOSIT_TRANS_TYPE_MINUS_COLL_RETAIL;
            }
        }

        return null;
    }
}
