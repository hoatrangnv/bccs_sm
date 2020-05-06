package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.common.ReportConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.ReportRevenueForm;
import java.util.List;
import java.util.ArrayList;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.SaleServices;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VSaleTransDetail;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.SaleInvoiceType;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author tamdt1
 * bao cao doanh thu
 *
 */
public class ReportRevenueDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(LookupIsdnDAO.class);
    //dinh nghia cac hang so pageForward
    private String pageForward;

    private static final String SALE_INVOICE_TYPE_LIST = "saleInvoiceTypeList";

    private final String REPORT_REVENUE = "reportRevenue";
    private final String CHANGE_STOCK_TYPE = "changeStockType";
    //dinh nghia ten cac bien session hoac bien request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_REPORT_REVENUE_PATH = "reportRevenuePath";
    private final String REQUEST_REPORT_DESTROY_PATH = "reportDestroyPath";
    private final String REQUEST_REPORT_REVENUE_MESSAGE = "reportRevenueMessage";
    private final String REQUEST_LIST_STOCK_TYPE = "listStockType";
    private final String REQUEST_LIST_STOCK_MODEL = "listStockModel";
    private final String REQUEST_LIST_REASON = "listReason";
    private final String REQUEST_LIST_PAY_METHOD = "listPayMethod";
    private final String REQUEST_LIST_TELECOM_SERVICE = "listTelecomService";
    private final String REPORT_DESTROY = "reportDestroy";
    //
    private final Long RR_DETAIL_BY_SHOP = 2L;
    private final Long RR_DETAIL_BY_STAFF = 3L;
    private final Long RR_NOT_GROUP_BY_TRANS_TYPE = 2L;
    //khai bao bien form
    private ReportRevenueForm reportRevenueForm = new ReportRevenueForm();

    public ReportRevenueForm getReportRevenueForm() {
        return reportRevenueForm;
    }

    public void setReportRevenueForm(ReportRevenueForm reportRevenueForm) {
        this.reportRevenueForm = reportRevenueForm;
    }

    /**
     *
     * author tamdt1
     * date: 22/06/2009
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ReportRevenueDAO ...");

        HttpServletRequest req = getRequest();
        //reset form
        this.reportRevenueForm.resetForm();
        //thiet lap cac truong mac dinh cho shop code va staff code
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        this.reportRevenueForm.setShopCode(userToken.getShopCode());
        this.reportRevenueForm.setShopName(userToken.getShopName());
        this.reportRevenueForm.setStaffCode(userToken.getLoginName());
        this.reportRevenueForm.setStaffName(userToken.getStaffName());
        this.reportRevenueForm.setReportType(2L);
        
        this.reportRevenueForm.setHasMoney(true);
        this.reportRevenueForm.setNoMoney(false);

        /*2012-12-19 : trungdh3 ==> danh sach loai KPP */
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        List<ChannelType> listChannelType = channelTypeDAO.findByIsVtUnitAndIsPrivate(
                Constant.IS_NOT_VT_UNIT,
                Constant.CHANNEL_TYPE_IS_NOT_PRIVATE,
                Constant.OBJECT_TYPE_STAFF);
        req.getSession().setAttribute("listChannelType", listChannelType);

        /* danh sach loai giao dich */
        SaleInvoiceTypeDAO saleInvoiceTypeDAO = new SaleInvoiceTypeDAO();
        saleInvoiceTypeDAO.setSession(getSession());
        List<SaleInvoiceType> saleInvoiceTypeList = saleInvoiceTypeDAO.getListSaleTransType();
        req.getSession().setAttribute(SALE_INVOICE_TYPE_LIST, saleInvoiceTypeList);

        //lay du lieu cho cac combobox
        getDataForCombobox();

        pageForward = REPORT_REVENUE;
        log.info("End method preparePage of ReportRevenueDAO");
        return pageForward;
    }

    public String prepareDestroyTransPage() throws Exception {
        log.info("Begin method preparePage of ReportRevenueDAO ...");

        HttpServletRequest req = getRequest();
        //reset form
        this.reportRevenueForm.resetForm();
        //thiet lap cac truong mac dinh cho shop code va staff code
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        this.reportRevenueForm.setShopCode(userToken.getShopCode());
        this.reportRevenueForm.setShopName(userToken.getShopName());
        this.reportRevenueForm.setStaffCode(userToken.getLoginName());
        this.reportRevenueForm.setStaffName(userToken.getStaffName());
        this.reportRevenueForm.setReportType(1L);
        
        
        
        //lay du lieu cho cac combobox
        getDataForCombobox();

        pageForward = REPORT_DESTROY;
        log.info("End method preparePage of ReportRevenueDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 24/06/2009
     * bao cao doanh thu, xuat ra file excel
     *
     */
    public String reportRevenue() throws Exception {
        log.info("Begin method reportRevenue of ReportRevenueDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (!checkValidReportRevenue()) {
            //
            //lay du lieu cho cac combobox
            getDataForCombobox();

            pageForward = REPORT_REVENUE;
            log.info("End method reportRevenue of ReportRevenueDAO");
            return pageForward;
        }

        Long stockTypeId = this.reportRevenueForm.getStockTypeId();
        Long stockModelId = this.reportRevenueForm.getStockModelId();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        String saleTransType = this.reportRevenueForm.getSaleTransType();
        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
        Long reasonId = this.reportRevenueForm.getReasonId();
        Long telecomServiceId = this.reportRevenueForm.getTelecomServiceId();
        Boolean billedSaleTrans = this.reportRevenueForm.getBilledSaleTrans();
        Boolean notBilledSaleTrans = this.reportRevenueForm.getNotBilledSaleTrans();
        Boolean cancelSaleTrans = this.reportRevenueForm.getCancelSaleTrans();
        Long groupType = this.reportRevenueForm.getGroupType();
        Boolean hasMoney = this.reportRevenueForm.getHasMoney();
        Boolean noMoney = this.reportRevenueForm.getNoMoney();
        Long groupBySaleTransType = this.reportRevenueForm.getGroupBySaleTransType();
        String payMethod = this.reportRevenueForm.getPayMethod();

        Boolean reportIncludeShop = this.reportRevenueForm.getReportIncludeShop();
        Boolean reportIncludeCollaborator = this.reportRevenueForm.getReportIncludeCollaborator();
        //trung dh3
        Long subChannelTypeId=this.reportRevenueForm.getSubChannelTypeId();

        Boolean reportIncludePointOfSale = this.reportRevenueForm.getReportIncludePointOfSale();
        Boolean reportIncludeAgent = this.reportRevenueForm.getReportIncludeAgent();

        //truyen du lieu sang server bao cao
        ViettelMsg request = new OriginalViettelMsg();

        request.set("FROM_DATE", strFromDate);
        request.set("TO_DATE", strToDate);
        request.set("USER_NAME", userToken.getLoginName());

        request.set("REPORT_INCLUDE_SHOP", reportIncludeShop);
        request.set("REPORT_INCLUDE_COLLABORATOR", reportIncludeCollaborator);

        //trung dh3
        request.set("SUB_CHANNEL_TYPE_ID", subChannelTypeId);

        request.set("REPORT_INCLUDE_AGENT", reportIncludeAgent);

        if (reportRevenueForm.getOwnerId() != null && reportRevenueForm.getOwnerId().compareTo(0L) > 0) {
            //truong hop lay bao cao theo CTV/ DB
            request.set("STAFF_ID", reportRevenueForm.getOwnerId());
            request.set("STAFF_CODE", reportRevenueForm.getOwnerCode());
            request.set("STAFF_NAME", reportRevenueForm.getOwnerName());
        } else {
            request.set("STAFF_ID", reportRevenueForm.getStaffId());
            request.set("STAFF_CODE", reportRevenueForm.getStaffCode());
            request.set("STAFF_NAME", reportRevenueForm.getStaffName());
        }

        request.set("SHOP_ID", reportRevenueForm.getShopId());
        request.set("SHOP_CODE", reportRevenueForm.getShopCode());
        request.set("SHOP_NAME", reportRevenueForm.getShopName());
        request.set("SHOP_PATH", reportRevenueForm.getShopPath());
        request.set("SHOP_ADDRESS", reportRevenueForm.getShopAddress());


        // tutm1 : 9/9/2011 : lay thong tin channel_type_id cua shop
        String shopCode = reportRevenueForm.getShopCode();
        Shop shop = null;
        if (shopCode != null) {
            shop = new ShopDAO().findShopsAvailableByShopCode(shopCode);
            request.set("SHOP_CHANEL_TYPE_ID", shop.getChannelTypeId());
        }



        request.set("GROUP_TYPE", groupType);
        request.set("SALE_TRANS_TYPE", saleTransType);
        if (stockTypeId != null && stockTypeId > 0) {
            request.set("STOCK_TYPE_ID", stockTypeId);
        }
        if (stockModelId != null && stockModelId > 0) {
            request.set("STOCK_MODEL_ID", stockModelId);
        }
        if (channelTypeId != null && channelTypeId > 0) {
            request.set("CHANEL_TYPE_ID", channelTypeId);
        }
        if (reasonId != null && reasonId > 0) {
            request.set("REASON_ID", reasonId);
        }

        if (billedSaleTrans) {
            request.set("BILLED_SALE_TRANS", "true");
        } else {
            request.set("BILLED_SALE_TRANS", "false");
        }

        if (notBilledSaleTrans) {
            request.set("NOT_BILLED_SALE_TRANS", "true");
        } else {
            request.set("NOT_BILLED_SALE_TRANS", "false");
        }

        if (cancelSaleTrans) {
            request.set("CANCEL_SALE_TRANS", "true");
        } else {
            request.set("CANCEL_SALE_TRANS", "false");
        }

        if (hasMoney) {
            request.set("HAS_MONEY", "true");
        } else {
            request.set("HAS_MONEY", "false");
        }

        if (noMoney) {
            request.set("NOT_MONEY", "true");
        } else {
            request.set("NOT_MONEY", "false");
        }

        request.set("TELECOM_SERVICE_ID", telecomServiceId);
        request.set("PAY_METHOD", payMethod);
        request.set("GROUP_BY_SALE_TRANS_TYPE", groupBySaleTransType);
        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_REVENUE);

        //truyen du lieu sang server bao cao
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());

        AppParams appParams = appParamsDAO.findAppParams("GET_DATABASE_ONLINE", String.valueOf(ReportConstant.IM_REPORT_REVENUE));
        String getDatabase = appParams == null ? "1" : appParams.getValue();
        if (getDatabase == null) {
            request.set("DATABASE_ONLINE", "1");
        } else {
            request.set("DATABASE_ONLINE", getDatabase);
        }

        ViettelMsg response = ReportRequestSender.sendRequest(request);




        if (response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
            DownloadDAO downloadDAO = new DownloadDAO();
            req.setAttribute(REQUEST_REPORT_REVENUE_PATH, downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
            //req.setAttribute(REQUEST_REPORT_REVENUE_PATH, response.get(ReportConstant.RESULT_FILE).toString());
            req.setAttribute(REQUEST_REPORT_REVENUE_MESSAGE, "reportRevenue.reportRevenueMessage");
        } else {
            req.setAttribute(REQUEST_MESSAGE, "report.warning.noResult");
        }

        //lay du lieu cho cac combobox
        getDataForCombobox();
        pageForward = REPORT_REVENUE;
        log.info("End method reportRevenue of ReportRevenueDAO");
        return pageForward;

    }
    /*
     * Bao cao huy giao dich
     * Author NamLT
     * Date 27/7/2010
     */

    public String reportDestroyTrans() throws Exception {
        log.info("Begin method reportRevenue of ReportRevenueDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (!checkValidReportRevenue()) {
            //
            //lay du lieu cho cac combobox
            getDataForCombobox();

            pageForward = REPORT_DESTROY;
            log.info("End method reportRevenue of ReportRevenueDAO");
            return pageForward;
        }

        Long staffId = this.reportRevenueForm.getStaffId();
        Long stockTypeId = this.reportRevenueForm.getStockTypeId();
        String stockModelCode = this.reportRevenueForm.getGoodsCode();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        String saleTransType = this.reportRevenueForm.getSaleTransType();
        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
        Long reasonId = this.reportRevenueForm.getReasonId();
        Long telecomServiceId = this.reportRevenueForm.getTelecomServiceId();
//        Boolean billedSaleTrans = this.reportRevenueForm.getBilledSaleTrans();
//        Boolean notBilledSaleTrans = this.reportRevenueForm.getNotBilledSaleTrans();
//        Boolean cancelSaleTrans = this.reportRevenueForm.getCancelSaleTrans();
        Long groupType = this.reportRevenueForm.getGroupType();
        Boolean hasMoney = this.reportRevenueForm.getHasMoney();
        Boolean noMoney = this.reportRevenueForm.getNoMoney();
        Long groupBySaleTransType = this.reportRevenueForm.getGroupBySaleTransType();
        // Long reportType = this.reportRevenueForm.getReportType();

        String shopCode = this.reportRevenueForm.getShopCode().trim();

        //ShopDAO shopDAO = new ShopDAO();
        //shopDAO.setSession(this.getSession());
        //List<Shop> listShop = shopDAO.findByPropertyWithStatus(ShopDAO.SHOP_CODE, shopCode, Constant.STATUS_USE);
        String strShopQuery = " from Shop a where lower(a.shopCode) = ? and status = ? ";
        Query shopQuery = getSession().createQuery(strShopQuery);
        shopQuery.setParameter(0, shopCode.trim().toLowerCase());
        shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
        List<Shop> listShop = shopQuery.list();
        if (listShop == null || listShop.size() == 0) {
            //
            //lay du lieu cho cac combobox
            getDataForCombobox();

            //
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.shopNotExist");

            pageForward = REPORT_REVENUE;
            log.info("End method reportRevenue of ReportRevenueDAO");
            return pageForward;
        }

        String shopName = listShop.get(0).getName();
        String shopPath = listShop.get(0).getShopPath();
        String shopAddress = listShop.get(0).getAddress();
        this.reportRevenueForm.setShopId(listShop.get(0).getShopId());

        String staffCode = this.reportRevenueForm.getStaffCode();
        if (staffCode != null && !staffCode.trim().equals("")) {
            //StaffDAO staffDAO = new StaffDAO();
            //staffDAO.setSession(this.getSession());
            //List<Staff> listStaff = staffDAO.findByPropertyAndStatus(StaffDAO.STAFF_CODE,
            //staffCode.trim(), Constant.STATUS_USE);
            String queryString = " from Staff a where lower(a.staffCode) = ? and a.shopId = ? and status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, staffCode.trim().toLowerCase());
            queryObject.setParameter(1, listShop.get(0).getShopId());
            queryObject.setParameter(2, Constant.STATUS_ACTIVE);
            List<Staff> listStaff = queryObject.list();

            if (listStaff == null || listStaff.size() == 0) {
                //
                //lay du lieu cho cac combobox
                getDataForCombobox();

                //
                req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.staffNotExist");

                pageForward = REPORT_DESTROY;
                log.info("End method reportRevenue of ReportRevenueDAO");
                return pageForward;
            }

            this.reportRevenueForm.setStaffId(listStaff.get(0).getStaffId());
            this.reportRevenueForm.setStaffName(listStaff.get(0).getName());
        }
        ViettelMsg request = new OriginalViettelMsg();

        request.set("FROM_DATE", strFromDate);
        request.set("TO_DATE", strToDate);
        request.set("USER_NAME", userToken.getLoginName());
        //  request.set("REPORT_TYPE", reportType);
//        if (reportType.equals(1L)) {
//            request.set("STAFF_ID", reportRevenueForm.getStaffId());
//
//        } else {
//            if (reportType.equals(2L)) {
//                String ownerCode = this.reportRevenueForm.getOwnerCode();
//                request.set("STAFF_ID", getStaffId(ownerCode));
//            } else {
//                if (reportType.equals(3L)) {
//                    String ownerCode = this.reportRevenueForm.getOwnerCode();
//                    request.set("STAFF_ID", getShopId(ownerCode));
//                }
//            }
//        }
        //truyen them nhan vien quan ly
        request.set("STAFF_MANAGEMENT_ID", getStaffId(reportRevenueForm.getStaffCode()));
        request.set("STAFF_ID", staffId);
        request.set("STAFF_CODE", reportRevenueForm.getStaffCode());
        request.set("STAFF_NAME", reportRevenueForm.getStaffName());

        request.set("SHOP_ID", reportRevenueForm.getShopId());
        request.set("SHOP_NAME", shopName);
        request.set("SHOP_PATH", shopPath);
        request.set("SHOP_ADDRESS", shopAddress);

        request.set("GROUP_TYPE", groupType);
        request.set("SALE_TRANS_TYPE", saleTransType);
        if (stockTypeId != null && stockTypeId > 0) {
            request.set("STOCK_TYPE_ID", stockTypeId);
        }
        if (stockModelCode != null && !"".equals(stockModelCode)) {
            request.set("STOCK_MODEL_CODE", stockModelCode);
        }
        if (channelTypeId != null && channelTypeId > 0) {
            request.set("CHANEL_TYPE_ID", channelTypeId);
        }
        if (reasonId != null && reasonId > 0) {
            request.set("REASON_ID", reasonId);
        }

//        if (billedSaleTrans) {
//            request.set("BILLED_SALE_TRANS", "true");
//        } else {
//            request.set("BILLED_SALE_TRANS", "false");
//        }
//
//        if (notBilledSaleTrans) {
//            request.set("NOT_BILLED_SALE_TRANS", "true");
//        } else {
//            request.set("NOT_BILLED_SALE_TRANS", "false");
//        }

//        if (cancelSaleTrans) {
//            request.set("CANCEL_SALE_TRANS", "true");
//        } else {
//            request.set("CANCEL_SALE_TRANS", "false");
//        }

        if (hasMoney) {
            request.set("HAS_MONEY", "true");
        } else {
            request.set("HAS_MONEY", "false");
        }

        if (noMoney) {
            request.set("NOT_MONEY", "true");
        } else {
            request.set("NOT_MONEY", "false");
        }

        request.set("TELECOM_SERVICE_ID", telecomServiceId);
        request.set("GROUP_BY_SALE_TRANS_TYPE", groupBySaleTransType);
        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_DESTROY_TRANS);

        ViettelMsg response = ReportRequestSender.sendRequest(request);
        if (response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
            DownloadDAO downloadDAO = new DownloadDAO();
            req.setAttribute(REQUEST_REPORT_REVENUE_PATH, downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
            //req.setAttribute(REQUEST_REPORT_DESTROY_PATH, response.get(ReportConstant.RESULT_FILE).toString());
            req.setAttribute(REQUEST_REPORT_REVENUE_MESSAGE, "reportRevenue.reportRevenueMessage");
        } else {
            req.setAttribute(REQUEST_MESSAGE, "report.warning.noResult");
        }

        //lay du lieu cho cac combobox
        getDataForCombobox();
        pageForward = REPORT_DESTROY;
        log.info("End method reportRevenue of ReportRevenueDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 27/06/2009
     * lay du lieu bao cao tong hop
     *
     */
    private List<VSaleTransDetail> getListGeneral() throws Exception {
        List<VSaleTransDetail> listVSaleTransDetail = new ArrayList<VSaleTransDetail>();

        Long shopId = this.reportRevenueForm.getShopId();
        Long staffId = this.reportRevenueForm.getStaffId();
        Long stockTypeId = this.reportRevenueForm.getStockTypeId();
        Long stockModelId = this.reportRevenueForm.getStockModelId();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        String saleTransType = this.reportRevenueForm.getSaleTransType();
        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
        Long reasonId = this.reportRevenueForm.getReasonId();
        Long telecomServiceId = this.reportRevenueForm.getTelecomServiceId();
        Boolean billedSaleTrans = this.reportRevenueForm.getBilledSaleTrans();
        Boolean notBilledSaleTrans = this.reportRevenueForm.getNotBilledSaleTrans();
        Boolean cancelSaleTrans = this.reportRevenueForm.getCancelSaleTrans();
        Long groupType = this.reportRevenueForm.getGroupType();
        Boolean hasMoney = this.reportRevenueForm.getHasMoney();
        Boolean noMoney = this.reportRevenueForm.getNoMoney();
        Long groupBySaleTransType = this.reportRevenueForm.getGroupBySaleTransType();
        Boolean reportStockModelInSaleService = this.reportRevenueForm.getReportStockModelInSaleService();

        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Shop shop = shopDAO.findById(shopId);

        StringBuffer strQuery = new StringBuffer(
                "select new VSaleTransDetail(a.saleTransTypeName, a.stockTypeName, a.stockModelCode, a.stockModelName, " + "sum(a.quantity), a.price, sum(a.amount), sum(a.discountAmount), sum(a.vatAmount)) " + "from VSaleTransDetail a where 1 = 1 ");

        List listParameter = new ArrayList();

        //chon tat ca cac shop cap duoi
        strQuery.append("and a.shopId in (select shopId from Shop where shopPath like ?) ");
        listParameter.add(shop.getShopPath().trim() + "%");

        if (staffId != null && staffId.compareTo(0L) > 0) {
            strQuery.append("and a.staffId = ? ");
            listParameter.add(staffId);
        }

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.stockTypeId = ? ");
            listParameter.add(stockTypeId);
        }
        /**
         * AnDV 12/2/10
         * Them bao cao mat hang nam trong cac dich vu ban hang
         * tuy theo trang thai checkBox reportStockModelInSaleService
         */
        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
            if (reportStockModelInSaleService != null && reportStockModelInSaleService.equals(true)) {
                StringBuffer stringBuffer = new StringBuffer("");
                List<BigDecimal> lstSaleServiceId = new ArrayList<BigDecimal>();
                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
                stringBuffer.append(stockModelId.toString());
                if (lstSaleServiceId != null && lstSaleServiceId.size() > 0) {
                    for (int i = 0; i < lstSaleServiceId.size(); i++) {
                        stringBuffer.append(",");
                        stringBuffer.append(lstSaleServiceId.get(i).toString());

                    }
                }
                strQuery.append("and a.stockModelId in (" + stringBuffer.toString() + ") ");
//                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
            } else {
                strQuery.append("and a.stockModelId = ? ");
                listParameter.add(stockModelId);
            }
        }
        //End:Andv
        if (reportStockModelInSaleService != null && reportStockModelInSaleService.equals(true)) {
        }
        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
            strQuery.append("and a.stockModelId = ? ");
            listParameter.add(stockModelId);
        }

        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
            strQuery.append("and a.saleTransDate >= ? ");
            listParameter.add(fromDate);
        }

        Date toDate = new Date();
        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
            strQuery.append("and a.saleTransDate < ? ");
            listParameter.add(afterToDateOneDay);
        }

        if (saleTransType != null && !saleTransType.trim().equals("")) {
            strQuery.append("and a.saleTransType = ? ");
            listParameter.add(saleTransType);
        }

        //doi tuong (con thieu)

        if (reasonId != null && reasonId.compareTo(0L) > 0) {
            strQuery.append("and a.reasonId = ? ");
            listParameter.add(reasonId);
        }

        if (telecomServiceId != null && telecomServiceId.compareTo(0L) > 0) {
            strQuery.append("and a.telecomServiceId = ? ");
            listParameter.add(telecomServiceId);
        }

        strQuery.append("and (a.saleTransStatus = ? "); //them de su dung toan tu or
        listParameter.add("-1");

        if (billedSaleTrans != null && billedSaleTrans) {
            strQuery.append("or a.saleTransStatus = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_BILLED);
        }

        if (notBilledSaleTrans != null && notBilledSaleTrans) {
            strQuery.append("or a.saleTransStatus = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        }

        if (cancelSaleTrans != null && cancelSaleTrans) {
            strQuery.append("or a.saleTransStatus = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_CANCEL);
        }

        strQuery.append(") "); //

        strQuery.append("and (a.saleTransType = ? "); //them de su dung toan tu or
        listParameter.add("-1");

        if (hasMoney != null && hasMoney.equals(true)) {
            //cac giao dich co thu tien (bao gom ban le, ban dai ly, ban CTV, DB, lam giao dich CM)
            strQuery.append("or a.saleTransType = ? or a.saleTransType = ? or a.saleTransType = ? or a.saleTransType = ? ");
            listParameter.add(Constant.SALE_TRANS_TYPE_RETAIL);
            listParameter.add(Constant.SALE_TRANS_TYPE_AGENT);
            listParameter.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
            listParameter.add(Constant.SALE_TRANS_TYPE_SERVICE);
        }
        if (noMoney != null && noMoney.equals(true)) {
            //cac giao dich khong thu tien (bao gom ban khuyen mai va ban hang noi bo)
            strQuery.append("or a.saleTransType = ? or a.saleTransType = ? ");
            listParameter.add(Constant.SALE_TRANS_TYPE_PROMOTION);
            listParameter.add(Constant.SALE_TRANS_TYPE_INTERNAL);
        }

        strQuery.append(") "); //

        strQuery.append("group by a.saleTransTypeName, a.stockTypeName, a.stockModelCode, a.stockModelName, a.price ");
        strQuery.append("order by a.stockModelCode");

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        //lay danh sach cac giao dich
        listVSaleTransDetail = query.list();

        return listVSaleTransDetail;
    }

    /**
     *
     * author tamdt1
     * date: 29/12/2009
     * lay du lieu bao cao chi tiet cap duoi
     * thay the ham cmt ben duoi, turning hieu nang
     *
     */
    private List<VSaleTransDetail> getListDetailByShop() throws Exception {

        List<VSaleTransDetail> listVSaleTransDetail = new ArrayList<VSaleTransDetail>();

        Long shopId = this.reportRevenueForm.getShopId();
        Long staffId = this.reportRevenueForm.getStaffId();
        Long stockTypeId = this.reportRevenueForm.getStockTypeId();
        Long stockModelId = this.reportRevenueForm.getStockModelId();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        String saleTransType = this.reportRevenueForm.getSaleTransType();
        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
        Long reasonId = this.reportRevenueForm.getReasonId();
        Long telecomServiceId = this.reportRevenueForm.getTelecomServiceId();
        Boolean billedSaleTrans = this.reportRevenueForm.getBilledSaleTrans();
        Boolean notBilledSaleTrans = this.reportRevenueForm.getNotBilledSaleTrans();
        Boolean cancelSaleTrans = this.reportRevenueForm.getCancelSaleTrans();
        Long groupType = this.reportRevenueForm.getGroupType();
        Boolean hasMoney = this.reportRevenueForm.getHasMoney();
        Boolean noMoney = this.reportRevenueForm.getNoMoney();
        Long groupBySaleTransType = this.reportRevenueForm.getGroupBySaleTransType();

        List listParameter = new ArrayList();

        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }

        /*
        lay thong tin chi tiet cua 1 giao dich, phuc vu viec xuat bao cao doanh thu
        1 giao dich co the bao gom 3 loai:
        1> cac giao dich binh thuong: 1 giao dich bao gom cac mat hang, moi mat hang co' 1 gia'
        2> cac giao dich thuoc loai lam dich vu: 1 giao dich bao gom gia cua mat hang + gia cua dich vu
        3> cac giao dich ban go'i hang: 1 giao dich chi bao gom gia cua goi hang, ko quan tam den gia cua dich vu
         */

        //-- cac giao dich binh thuong
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        strNormalSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, d.stock_type_id, d.name AS stock_type_name, b.stock_model_id, b.stock_model_code, b.NAME AS stock_model_name, " + "       a.quantity, c.price, a.amount, (nvl(a.discount_amount,0) + nvl(a.discount_amount,0) * e.vat / 100) AS discount_amount, a.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strNormalSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       stock_model b, " + "       price c, " + "       stock_type d, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.staff_id, c.staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, DECODE (a.sale_trans_type, " + "                                                                  1, 'Bán lẻ', " + "                                                                  2, 'Bán đại lý', " + "                                                                  3, 'Bán cộng tác viên', " + "                                                                   4, 'Làm dịch vụ', " + "                                                                  5, 'Bán hàng khuyến mại', " + "                                                                  6, 'Xuất bán nội bộ' " + "                                                                ) AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, a.vat AS vat " + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ?) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   staff c " + "           WHERE   1 = 1 " + "                   AND b.shop_id = a.shop_id " + "                   AND c.staff_id = a.staff_id " + "                   AND a.sale_trans_type <> 4 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strNormalSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND b.stock_model_id = a.stock_model_id " + "       AND c.price_id = a.price_id " + "       AND b.stock_type_id = d.stock_type_id " + "       AND e.sale_trans_id = a.sale_trans_id " + "       AND a.sale_services_id IS NULL " + //--chi lay cac mat hang khong thuoc goi hang, co dieu kien nay la do trong bang sale_trans_detail luu ca chi tiet thong tin ve goi hang, vi du goi hang bao gom 2 mat hang -> luu thanh 3 ban ghi: 1 ban ghi ve goi hang, 2 ban ghi ve cac mat hang thuoc goi hang
                "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich binh htuong
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //-- lay cac giao dich thuoc loai lam dich vu
        StringBuffer strServicesSaleTransQuery = new StringBuffer("");
        strServicesSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, -1 AS stock_type_id, 'Làm dịch vụ' AS stock_type_name, b.sale_services_id AS stock_model_id, b.code AS stock_model_code, b.name AS stock_model_name, " + "       1 AS quantity, e.amount AS price, e.amount, e.discount_amount, e.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strServicesSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       sale_services b, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.staff_id, c.staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, 'Làm dịch vụ' AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, " + "                   a.amount_tax AS amount, " + "                   (nvl(a.discount,0) + nvl(a.discount,0) * a.vat / 100) AS discount_amount, " + "                   a.tax AS vat_amount" + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ?) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   staff c " + "           WHERE   1 = 1 " + "                   AND a.shop_id = b.shop_id " + "                   AND a.staff_id = c.staff_id " + "                   AND a.sale_trans_type = 4 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strServicesSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND a.sale_trans_id = e.sale_trans_id " + "       AND b.sale_services_id = a.sale_services_id " + "       AND b.sale_type = 1 " + "       AND a.stock_model_id IS NULL " + "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich lam dich vu
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //-- lay cac giao dich ban goi hang
        StringBuffer strPackageSaleTransQuery = new StringBuffer("");
        strPackageSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, -2 AS stock_type_id, 'Bán gói hàng' AS stock_type_name, b.sale_services_id AS stock_model_id, b.code AS stock_model_code, b.NAME AS stock_model_name, " + "       a.quantity, c.price, a.amount, (a.discount_amount + a.discount_amount * e.vat / 100) AS discount_amount, a.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strPackageSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       sale_services b, " + "       sale_services_price c, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.staff_id, c.staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, DECODE (a.sale_trans_type, " + "                                                               1, 'Bán lẻ', " + "                                                               2, 'Bán đại lý', " + "                                                               3, 'Bán cộng tác viên', " + "                                                               4, 'Làm dịch vụ', " + "                                                               5, 'Bán hàng khuyến mại', " + "                                                               6, 'Xuất bán nội bộ' " + "                                                           ) AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, a.vat " + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ?) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   staff c " + "           WHERE   1 = 1 " + "                   AND a.shop_id = b.shop_id " + "                   AND a.staff_id = c.staff_id " + "                   AND a.sale_trans_type = 1 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strPackageSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND a.sale_services_id = b.sale_services_id " + "       AND a.sale_services_price_id = c.sale_services_price_id " + "       AND a.sale_trans_id = e.sale_trans_id " + "       AND a.stock_model_id IS NULL " + //--co dieu kien nay la do trong bang sale_trans_detail luu ca chi tiet thong tin ve goi hang, vi du goi hang bao gom 2 mat hang -> luu thanh 3 ban ghi: 1 ban ghi ve goi hang, 2 ban ghi ve cac mat hang thuoc goi hang
                "       AND b.sale_type = 2 " + "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich lam dich vu
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //VSaleTransDetail = union all cua 3 loai tren
        StringBuffer strVSaleTransDetailQuery = new StringBuffer("");
        strVSaleTransDetailQuery.append(strNormalSaleTransQuery);
        strVSaleTransDetailQuery.append(" UNION ALL ");
        strVSaleTransDetailQuery.append(strServicesSaleTransQuery);
        strVSaleTransDetailQuery.append(" UNION ALL ");
        strVSaleTransDetailQuery.append(strPackageSaleTransQuery);

        StringBuffer strQuery = new StringBuffer("");
        strQuery.append("" + "SELECT a.shop_name shopName, a.sale_trans_type_name saleTransTypeName, a.stock_type_name stockTypeName, a.stock_model_code stockModelCode, a.stock_model_name stockModelName, " + "       sum(a.quantity) quantity, a.price price, sum(a.amount) amount, sum(a.discount_amount) discountAmount, sum(a.vat_amount) vatAmount ");
        strQuery.append("" + "FROM ").append("( ").append(strVSaleTransDetailQuery).append(" ) a ");
        strQuery.append("" + "WHERE 1 = 1 ");

        if (staffId != null && staffId.compareTo(0L) > 0) {
            strQuery.append("and a.staff_id = ? ");
            listParameter.add(staffId);
        }

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.stock_type_id = ? ");
            listParameter.add(stockTypeId);
        }

//        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
//            strQuery.append("and a.stock_model_id = ? ");
//            listParameter.add(stockModelId);
//        }
        /**
         * AnDV 12/2/10
         * Them bao cao mat hang nam trong cac dich vu ban hang
         * tuy theo trang thai checkBox reportStockModelInSaleService
         */
        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
            Boolean reportStockModelInSaleService = this.reportRevenueForm.getReportStockModelInSaleService();
            if (reportStockModelInSaleService != null && reportStockModelInSaleService.equals(true)) {
                StringBuffer stringBuffer = new StringBuffer("");
                List<BigDecimal> lstSaleServiceId = new ArrayList<BigDecimal>();
                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
                stringBuffer.append(stockModelId.toString());
                if (lstSaleServiceId != null && lstSaleServiceId.size() > 0) {
                    for (int i = 0; i < lstSaleServiceId.size(); i++) {
                        stringBuffer.append(",");
                        stringBuffer.append(lstSaleServiceId.get(i).toString());

                    }
                }
                strQuery.append("and a.stock_model_id in (" + stringBuffer.toString() + ") ");
//                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
            } else {
                strQuery.append("and a.stock_model_id = ? ");
                listParameter.add(stockModelId);
            }
        }
        //Andv: end

        if (saleTransType != null && !saleTransType.trim().equals("")) {
            strQuery.append("and a.sale_trans_type = ? ");
            listParameter.add(saleTransType);
        }

        if (channelTypeId != null && channelTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.channel_type_id = ? ");
            listParameter.add(channelTypeId);
        }

        if (reasonId != null && reasonId.compareTo(0L) > 0) {
            strQuery.append("and a.reason_id = ? ");
            listParameter.add(reasonId);
        }

        if (telecomServiceId != null && telecomServiceId.compareTo(0L) > 0) {
            strQuery.append("and a.telecom_service_id = ? ");
            listParameter.add(telecomServiceId);
        }

        strQuery.append("and (a.sale_trans_status = ? "); //them de su dung toan tu or
        listParameter.add("-1");

        if (billedSaleTrans != null && billedSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_BILLED);
        }

        if (notBilledSaleTrans != null && notBilledSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        }

        if (cancelSaleTrans != null && cancelSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_CANCEL);
        }

        strQuery.append(") "); //

        strQuery.append("and (a.sale_trans_type = ? "); //them de su dung toan tu or
        listParameter.add("-1");

        if (hasMoney != null && hasMoney.equals(true)) {
            //cac giao dich co thu tien (bao gom ban le, ban dai ly, ban CTV, DB, lam giao dich CM)
            strQuery.append("or a.sale_trans_type = ? or a.sale_trans_type = ? or a.sale_trans_type = ? or a.sale_trans_type = ? ");
            listParameter.add(Constant.SALE_TRANS_TYPE_RETAIL);
            listParameter.add(Constant.SALE_TRANS_TYPE_AGENT);
            listParameter.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
            listParameter.add(Constant.SALE_TRANS_TYPE_SERVICE);
        }
        if (noMoney != null && noMoney.equals(true)) {
            //cac giao dich khong thu tien (bao gom ban khuyen mai va ban hang noi bo)
            strQuery.append("or a.sale_trans_type = ? or a.sale_trans_type = ? ");
            listParameter.add(Constant.SALE_TRANS_TYPE_PROMOTION);
            listParameter.add(Constant.SALE_TRANS_TYPE_INTERNAL);
        }

        strQuery.append(") "); //

        strQuery.append("group by a.shop_name, a.sale_trans_type_name, a.stock_type_name, a.stock_model_code, a.stock_model_name, a.price ");
        strQuery.append("order by a.stock_model_code");

        System.out.println("===TUANPV: Cau lenh SQL: " + strQuery.toString());

        Query query = getSession().createSQLQuery(strQuery.toString()).addScalar("shopName", Hibernate.STRING).addScalar("saleTransTypeName", Hibernate.STRING).addScalar("stockTypeName", Hibernate.STRING).addScalar("stockModelCode", Hibernate.STRING).addScalar("stockModelName", Hibernate.STRING).addScalar("quantity", Hibernate.LONG).addScalar("price", Hibernate.LONG).addScalar("amount", Hibernate.DOUBLE).addScalar("discountAmount", Hibernate.LONG).addScalar("vatAmount", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(VSaleTransDetail.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        listVSaleTransDetail = query.list();
        return listVSaleTransDetail;

    }

//    /**
//     *
//     * author tamdt1
//     * date: 27/06/2009
//     * lay du lieu bao cao chi tiet cap duoi
//     *
//     */
//    private List<VSaleTransDetail> getListDetailByShop() throws Exception {
//
//        List<VSaleTransDetail> listVSaleTransDetail = new ArrayList<VSaleTransDetail>();
//
//        Long shopId = this.reportRevenueForm.getShopId();
//        Long staffId = this.reportRevenueForm.getStaffId();
//        Long stockTypeId = this.reportRevenueForm.getStockTypeId();
//        Long stockModelId = this.reportRevenueForm.getStockModelId();
//        String strFromDate = this.reportRevenueForm.getFromDate();
//        String strToDate = this.reportRevenueForm.getToDate();
//        String saleTransType = this.reportRevenueForm.getSaleTransType();
//        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
//        Long reasonId = this.reportRevenueForm.getReasonId();
//        Long telecomServiceId = this.reportRevenueForm.getTelecomServiceId();
//        Boolean billedSaleTrans = this.reportRevenueForm.getBilledSaleTrans();
//        Boolean notBilledSaleTrans = this.reportRevenueForm.getNotBilledSaleTrans();
//        Boolean cancelSaleTrans = this.reportRevenueForm.getCancelSaleTrans();
//        Long groupType = this.reportRevenueForm.getGroupType();
//        Boolean hasMoney = this.reportRevenueForm.getHasMoney();
//        Boolean noMoney = this.reportRevenueForm.getNoMoney();
//        Long groupBySaleTransType = this.reportRevenueForm.getGroupBySaleTransType();
//
//        ShopDAO shopDAO = new ShopDAO();
//        shopDAO.setSession(this.getSession());
//        Shop shop = shopDAO.findById(shopId);
//        List<Shop> listChildShop = shopDAO.findByPropertyWithStatus(
//                ShopDAO.PARENT_SHOP_ID, shopId, Constant.STATUS_USE);
//
//        StringBuffer strQuery = new StringBuffer(
//                "select new VSaleTransDetail('_CURRENT_SHOP_NAME_', a.saleTransTypeName, a.stockTypeName, a.stockModelCode, a.stockModelName, " +
//                "sum(a.quantity), a.price, sum(a.amount), sum(a.discountAmount), sum(a.vatAmount)) " +
//                "from VSaleTransDetail a where 1 = 1 ");
//
//        //chuoi _CURRENT_SHOP_NAME_ trong cau truy van muc dich de gom nhom tat ca cac shop con cua 1 shop vao shop cha
//        //vi du CN1 co 2 shop con la CH1 va CH2, CH1 co shop con la CH11
//        //cau truy van nay se lay tat ca cac giao dich cua CN1, CH1, CH2, CH11
//        //va gan cho cac giao dich nay co shopName deu = CN1 (nghia la tat ca cac giao dich cua cac shop con cua CN1 se duoc gan shopName = CN1)
//
//        List listParameter = new ArrayList();
//
//        strQuery.append("and a.shopId in (select shopId from Shop where shopPath like ?) ");
//        listParameter.add("INIT_SHOP_PATH"); //du lieu gia (se thiet lap cu the trong vong lap o duoi)
//
//        if (staffId != null && staffId.compareTo(0L) > 0) {
//            strQuery.append("and a.staffId = ? ");
//            listParameter.add(staffId);
//        }
//
//        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
//            strQuery.append("and a.stockTypeId = ? ");
//            listParameter.add(stockTypeId);
//        }
//
//        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
//            strQuery.append("and a.stockModelId = ? ");
//            listParameter.add(stockModelId);
//        }
//
//        Date fromDate = new Date();
//        if (strFromDate != null && !strFromDate.trim().equals("")) {
//            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
//            strQuery.append("and a.saleTransDate >= ? ");
//            listParameter.add(fromDate);
//        }
//
//        Date toDate = new Date();
//        if (strToDate != null && !strToDate.equals("")) {
//            toDate = DateTimeUtils.convertStringToDate(strToDate);
//            Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
//            strQuery.append("and a.saleTransDate < ? ");
//            listParameter.add(afterToDateOneDay);
//        }
//
//        if (saleTransType != null && !saleTransType.trim().equals("")) {
//            strQuery.append("and a.saleTransType = ? ");
//            listParameter.add(saleTransType);
//        }
//
//        //doi tuong (con thieu)
//
//        if (reasonId != null && reasonId.compareTo(0L) > 0) {
//            strQuery.append("and a.reasonId = ? ");
//            listParameter.add(reasonId);
//        }
//
//        if (telecomServiceId != null && telecomServiceId.compareTo(0L) > 0) {
//            strQuery.append("and a.telecomServiceId = ? ");
//            listParameter.add(telecomServiceId);
//        }
//
//        strQuery.append("and (a.saleTransStatus = ? "); //them de su dung toan tu or
//        listParameter.add("-1");
//
//        if (billedSaleTrans != null && billedSaleTrans) {
//            strQuery.append("or a.saleTransStatus = ? ");
//            listParameter.add(Constant.SALE_TRANS_STATUS_BILLED);
//        }
//
//        if (notBilledSaleTrans != null && notBilledSaleTrans) {
//            strQuery.append("or a.saleTransStatus = ? ");
//            listParameter.add(Constant.SALE_TRANS_STATUS_NOT_BILLED);
//        }
//
//        if (cancelSaleTrans != null && cancelSaleTrans) {
//            strQuery.append("or a.saleTransStatus = ? ");
//            listParameter.add(Constant.SALE_TRANS_STATUS_CANCEL);
//        }
//
//        strQuery.append(") "); //
//
//        strQuery.append("group by a.shopName, a.saleTransTypeName, a.stockTypeName, a.stockModelCode, a.stockModelName, a.price ");
//        strQuery.append("order by a.stockModelCode");
//
//        //tong hop cac giao dich cua shop
//        String stringQuery = strQuery.toString().replace("_CURRENT_SHOP_NAME_", shop.getName());
//        Query query = getSession().createQuery(stringQuery);
//        for (int i = 0; i < listParameter.size(); i++) {
//            query.setParameter(i, listParameter.get(i));
//        }
//
//        query.setParameter(0, shop.getShopPath());
//        List<VSaleTransDetail> tmpListVSaleTransDetail = query.list();
//        if (tmpListVSaleTransDetail != null && tmpListVSaleTransDetail.size() > 0) {
//            listVSaleTransDetail.addAll(tmpListVSaleTransDetail);
//        }
//
//        //tong hop giao dich cua cac shop con (con cap 1)
//        if (listChildShop != null && listChildShop.size() > 0) {
//            for (Shop childShop : listChildShop) {
//                stringQuery = strQuery.toString().replace("_CURRENT_SHOP_NAME_", childShop.getName().replaceAll("'", "''"));
//                query = getSession().createQuery(stringQuery);
//                for (int i = 0; i < listParameter.size(); i++) {
//                    query.setParameter(i, listParameter.get(i));
//                }
//
//                query.setParameter(0, childShop.getShopPath() + "%");
//                tmpListVSaleTransDetail = query.list();
//                if (tmpListVSaleTransDetail != null && tmpListVSaleTransDetail.size() > 0) {
//                    listVSaleTransDetail.addAll(tmpListVSaleTransDetail);
//                }
//            }
//
//        }
//
//        return listVSaleTransDetail;
//    }
    /**
     *
     * author tamdt1
     * date: 27/06/2009
     * lay du lieu bao cao chi tiet cap duoi
     *
     */
    private List<VSaleTransDetail> getListDetailByStaff() throws Exception {

        List<VSaleTransDetail> listVSaleTransDetail = new ArrayList<VSaleTransDetail>();

        Long shopId = this.reportRevenueForm.getShopId();
        Long staffId = this.reportRevenueForm.getStaffId();
        Long stockTypeId = this.reportRevenueForm.getStockTypeId();
        Long stockModelId = this.reportRevenueForm.getStockModelId();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        String saleTransType = this.reportRevenueForm.getSaleTransType();
        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
        Long reasonId = this.reportRevenueForm.getReasonId();
        Long telecomServiceId = this.reportRevenueForm.getTelecomServiceId();
        Boolean billedSaleTrans = this.reportRevenueForm.getBilledSaleTrans();
        Boolean notBilledSaleTrans = this.reportRevenueForm.getNotBilledSaleTrans();
        Boolean cancelSaleTrans = this.reportRevenueForm.getCancelSaleTrans();
        Long groupType = this.reportRevenueForm.getGroupType();
        Boolean hasMoney = this.reportRevenueForm.getHasMoney();
        Boolean noMoney = this.reportRevenueForm.getNoMoney();
        Long groupBySaleTransType = this.reportRevenueForm.getGroupBySaleTransType();

        StringBuffer strQuery = new StringBuffer(
                "select new VSaleTransDetail(a.staffName, a.shopName, a.saleTransTypeName, a.stockTypeName, a.stockModelCode, a.stockModelName, " + "sum(a.quantity), a.price, sum(a.amount), sum(a.discountAmount), sum(a.vatAmount)) " + "from VSaleTransDetail a where 1 = 1 ");

        List listParameter = new ArrayList();

        strQuery.append("and a.shopId = ? ");
        listParameter.add(shopId); //du lieu gia (se thiet lap cu the trong vong lap o duoi)

        if (staffId != null && staffId.compareTo(0L) > 0) {
            strQuery.append("and a.staffId = ? ");
            listParameter.add(staffId);
        }

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.stockTypeId = ? ");
            listParameter.add(stockTypeId);
        }

//        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
//            strQuery.append("and a.stockModelId = ? ");
//            listParameter.add(stockModelId);
//        }
        /**
         * AnDV 12/2/10
         * Them bao cao mat hang nam trong cac dich vu ban hang
         * tuy theo trang thai checkBox reportStockModelInSaleService
         */
        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
            Boolean reportStockModelInSaleService = this.reportRevenueForm.getReportStockModelInSaleService();
            if (reportStockModelInSaleService != null && reportStockModelInSaleService.equals(true)) {
                StringBuffer stringBuffer = new StringBuffer("");
                List<BigDecimal> lstSaleServiceId = new ArrayList<BigDecimal>();
                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
                stringBuffer.append(stockModelId.toString());
                if (lstSaleServiceId != null && lstSaleServiceId.size() > 0) {
                    for (int i = 0; i < lstSaleServiceId.size(); i++) {
                        stringBuffer.append(",");
                        stringBuffer.append(lstSaleServiceId.get(i).toString());

                    }
                }
                strQuery.append("and a.stockModelId in (" + stringBuffer.toString() + ") ");
//                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
            } else {
                strQuery.append("and a.stockModelId = ? ");
                listParameter.add(stockModelId);
            }
        }
        //end: AnDV

        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
            strQuery.append("and a.saleTransDate >= ? ");
            listParameter.add(fromDate);
        }

        Date toDate = new Date();
        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
            strQuery.append("and a.saleTransDate < ? ");
            listParameter.add(afterToDateOneDay);
        }

        if (saleTransType != null && !saleTransType.trim().equals("")) {
            strQuery.append("and a.saleTransType = ? ");
            listParameter.add(saleTransType);
        }

        //doi tuong (con thieu)

        if (reasonId != null && reasonId.compareTo(0L) > 0) {
            strQuery.append("and a.reasonId = ? ");
            listParameter.add(reasonId);
        }

        if (telecomServiceId != null && telecomServiceId.compareTo(0L) > 0) {
            strQuery.append("and a.telecomServiceId = ? ");
            listParameter.add(telecomServiceId);
        }

        strQuery.append("and (a.saleTransStatus = ? "); //them de su dung toan tu or
        listParameter.add("-1");

        if (billedSaleTrans != null && billedSaleTrans) {
            strQuery.append("or a.saleTransStatus = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_BILLED);
        }

        if (notBilledSaleTrans != null && notBilledSaleTrans) {
            strQuery.append("or a.saleTransStatus = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        }

        if (cancelSaleTrans != null && cancelSaleTrans) {
            strQuery.append("or a.saleTransStatus = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_CANCEL);
        }

        strQuery.append(") "); //

        strQuery.append("and (a.saleTransType = ? "); //them de su dung toan tu or
        listParameter.add("-1");

        if (hasMoney != null && hasMoney.equals(true)) {
            //cac giao dich co thu tien (bao gom ban le, ban dai ly, ban CTV, DB, lam giao dich CM)
            strQuery.append("or a.saleTransType = ? or a.saleTransType = ? or a.saleTransType = ? or a.saleTransType = ? ");
            listParameter.add(Constant.SALE_TRANS_TYPE_RETAIL);
            listParameter.add(Constant.SALE_TRANS_TYPE_AGENT);
            listParameter.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
            listParameter.add(Constant.SALE_TRANS_TYPE_SERVICE);
        }
        if (noMoney != null && noMoney.equals(true)) {
            //cac giao dich khong thu tien (bao gom ban khuyen mai va ban hang noi bo)
            strQuery.append("or a.saleTransType = ? or a.saleTransType = ? ");
            listParameter.add(Constant.SALE_TRANS_TYPE_PROMOTION);
            listParameter.add(Constant.SALE_TRANS_TYPE_INTERNAL);
        }

        strQuery.append(") "); //

        strQuery.append("group by a.staffName, a.shopName, a.saleTransTypeName, a.stockTypeName, a.stockModelCode, a.stockModelName, a.price ");
        strQuery.append("order by a.stockModelCode");

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        List<VSaleTransDetail> tmpListVSaleTransDetail = query.list();
        if (tmpListVSaleTransDetail != null && tmpListVSaleTransDetail.size() > 0) {
            listVSaleTransDetail.addAll(tmpListVSaleTransDetail);
        }

        return listVSaleTransDetail;
    }

    /**
     *
     * tamdt1, 24/06/2009
     * kiem tra cac dieu kien hop le truoc khi xuat bao cao
     *
     */
    private boolean checkValidReportRevenue() {
        HttpServletRequest req = getRequest();

        String shopCode = this.reportRevenueForm.getShopCode();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();

        //kiem tra cac truong bat buoc
        if (shopCode == null || shopCode.trim().equals("") || strFromDate == null || strFromDate.trim().equals("") || strToDate == null || strToDate.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.requiredFieldsEmpty");
            return false;
        }


        Date fromDate = new Date();
        Date toDate = new Date();
        try {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
            toDate = DateTimeUtils.convertStringToDate(strToDate);
        } catch (Exception ex) {
            //bao loi
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.invalidDateFormat");
            return false;
        }

        Calendar fromCalendar = Calendar.getInstance();
        Calendar toCalendar = Calendar.getInstance();
        fromCalendar.setTime(fromDate);
        toCalendar.setTime(toDate);
        if (fromCalendar.get(Calendar.YEAR) != toCalendar.get(Calendar.YEAR)) {
            //khong cung thang
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.yearOfFromDateAndToDateDifferent");
            return false;
        }
        if (fromCalendar.compareTo(toCalendar) > 0) {
            //ngay bat dau lon hon ngay ket thuc
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.startDateLargerEndDate");
            return false;
        }

        //kiem tra tinh hop le cua shop va staff, chi cho phep lay bao cao doanh thu tu cap nay tro xuong
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List listParameterShop = new ArrayList();
        StringBuffer strQueryShop = new StringBuffer("");
        strQueryShop.append("from Shop a ");
        strQueryShop.append("where 1 = 1 ");

        strQueryShop.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameterShop.add("%_" + userToken.getShopId() + "_%");
        listParameterShop.add(userToken.getShopId());

        strQueryShop.append("and lower(a.shopCode) = ? ");
        listParameterShop.add(shopCode.trim().toLowerCase());

        strQueryShop.append("and rownum <= ? ");
        listParameterShop.add(1L);

        Query queryShop = getSession().createQuery(strQueryShop.toString());
        for (int i = 0; i < listParameterShop.size(); i++) {
            queryShop.setParameter(i, listParameterShop.get(i));
        }

        List<Shop> listShop = queryShop.list();
        if (listShop == null || listShop.size() == 0) {
            //khogn tim thay shop
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.shopNotExist");
            return false;
        } else {
            this.reportRevenueForm.setShopId(listShop.get(0).getShopId());
            this.reportRevenueForm.setShopName(listShop.get(0).getName());
            this.reportRevenueForm.setShopPath(listShop.get(0).getShopPath());
            this.reportRevenueForm.setShopAddress(listShop.get(0).getAddress());

            //kiem tra staff
            String strStaffCode = this.reportRevenueForm.getStaffCode();
            if (strStaffCode != null && !strStaffCode.trim().equals("")) {
                List listParameterStaff = new ArrayList();
                StringBuffer strQueryStaff = new StringBuffer("");
                strQueryStaff.append("from Staff a ");
                strQueryStaff.append("where 1 = 1 ");

                strQueryStaff.append("and a.shopId = ? ");
                listParameterStaff.add(listShop.get(0).getShopId());

//                strQueryStaff.append("and a.channelTypeId = ? ");
//                listParameterStaff.add(Constant.CHANNEL_TYPE_STAFF);

                //Modified by : TrongLV
                //Modified date : 2011-08-16
                strQueryStaff.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
                listParameterStaff.add(Constant.OBJECT_TYPE_STAFF);
                listParameterStaff.add(Constant.IS_VT_UNIT);
                listParameterStaff.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

                strQueryStaff.append("and lower(a.staffCode) = ? ");
                listParameterStaff.add(strStaffCode.trim().toLowerCase());

                strQueryStaff.append("and rownum <= ? ");
                listParameterStaff.add(1L);

                Query queryStaff = getSession().createQuery(strQueryStaff.toString());
                for (int i = 0; i < listParameterStaff.size(); i++) {
                    queryStaff.setParameter(i, listParameterStaff.get(i));
                }

                List<Staff> listStaff = queryStaff.list();
                if (listStaff == null || listStaff.size() == 0) {
                    //khogn tim thay staff
                    req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.staffNotExist");
                    return false;
                } else {
                    this.reportRevenueForm.setStaffId(listStaff.get(0).getStaffId());

                    //kiem tra tinh hop le cua CTV/ DB
                    String strOwnerCode = this.reportRevenueForm.getOwnerCode();
                    if (strOwnerCode != null && !strOwnerCode.trim().equals("")) {
                        List listParameterOwner = new ArrayList();
                        StringBuffer strQueryOwner = new StringBuffer("");
                        strQueryOwner.append("from Staff a ");
                        strQueryOwner.append("where 1 = 1 ");
                        strQueryOwner.append("and status = 1 ");

                        strQueryOwner.append("and a.shopId = ? ");
                        listParameterOwner.add(listShop.get(0).getShopId());

                        strQueryOwner.append("and a.staffOwnerId = ? ");
                        listParameterOwner.add(listStaff.get(0).getStaffId());

                        strQueryOwner.append("and (a.channelTypeId = ? or a.channelTypeId = ?) ");
                        listParameterOwner.add(Constant.CHANNEL_TYPE_CTV);
                        listParameterOwner.add(Constant.CHANNEL_TYPE_DB);

                        strQueryOwner.append("and lower(a.staffCode) = ? ");
                        listParameterOwner.add(strOwnerCode.trim().toLowerCase());

                        strQueryOwner.append("and rownum <= ? ");
                        listParameterOwner.add(1L);

                        Query queryOwner = getSession().createQuery(strQueryOwner.toString());
                        for (int i = 0; i < listParameterOwner.size(); i++) {
                            queryOwner.setParameter(i, listParameterOwner.get(i));
                        }

                        List<Staff> listOwner = queryOwner.list();
                        if (listOwner == null || listOwner.size() == 0) {
                            //khogn tim thay staff
                            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.staffNotExist");
                            return false;
                        } else {
                            this.reportRevenueForm.setOwnerId(listOwner.get(0).getStaffId());
                        }
                    }
                }
            }
        }


        //kiem tra tinh hop le cua goodCode: phai ton tai stockModel hoac saleServices
        String strGoodsCode = this.reportRevenueForm.getGoodsCode();
        if (strGoodsCode != null && !strGoodsCode.trim().equals("")) {
            List listParameterGoods = new ArrayList();
            StringBuffer strQueryGoods = new StringBuffer("from StockModel a ");
            strQueryGoods.append("where 1 = 1 ");

            strQueryGoods.append("and lower(a.stockModelCode) = ? ");
            listParameterGoods.add(strGoodsCode.trim().toLowerCase());

            strQueryGoods.append("and status = ? ");
            listParameterGoods.add(Constant.STATUS_ACTIVE);

            strQueryGoods.append("and rownum <= ? ");
            listParameterGoods.add(1L);

            Query queryGoods = getSession().createQuery(strQueryGoods.toString());
            for (int i = 0; i < listParameterGoods.size(); i++) {
                queryGoods.setParameter(i, listParameterGoods.get(i));
            }

            List<StockModel> listStockModel = queryGoods.list();
            if (listStockModel != null && listStockModel.size() > 0) {
                this.reportRevenueForm.setStockModelId(listStockModel.get(0).getStockModelId());
            } else {
                List listParameterSaleServices = new ArrayList();
                StringBuffer strQuerySaleServices = new StringBuffer("from SaleServices a ");
                strQuerySaleServices.append("where 1 = 1 ");

                strQuerySaleServices.append("and lower(a.code) = ? ");
                listParameterSaleServices.add(strGoodsCode.trim().toLowerCase());

                strQuerySaleServices.append("and status = ? ");
                listParameterSaleServices.add(Constant.STATUS_ACTIVE);

                strQuerySaleServices.append("and rownum <= ? ");
                listParameterSaleServices.add(1L);

                Query querySaleServices = getSession().createQuery(strQuerySaleServices.toString());
                for (int i = 0; i < listParameterSaleServices.size(); i++) {
                    querySaleServices.setParameter(i, listParameterSaleServices.get(i));
                }

                List<SaleServices> listSaleServices = querySaleServices.list();
                if (listSaleServices != null && listSaleServices.size() > 0) {
                    this.reportRevenueForm.setStockModelId(listSaleServices.get(0).getSaleServicesId());
                } else {
                    req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.goodsNotExist");
                    return false;
                }
            }
        }

        return true;
    }
    private Map listItem = new HashMap();

    public Map getListItem() {
        return listItem;
    }

    public void setListItem(Map listItem) {
        this.listItem = listItem;
    }

//    /**
//     *
//     * author tamdt1
//     * date: 21/06/2009
//     * lay du lieu cho autocompleter
//     *
//     */
//    public String getShopCode() throws Exception {
//        try {
//            HttpServletRequest req = getRequest();
//            String shopCode = req.getParameter("reportRevenueForm.shopCode");
//
//            //
//            req.getSession().setAttribute(SESSION_CURRENT_SHOP_ID, null);
//
//            if (shopCode != null && shopCode.trim().length() > 0) {
//                String queryString = "from Shop where lower(shopCode) like ? and status = ? ";
//                Query queryObject = getSession().createQuery(queryString);
//                queryObject.setParameter(0, "%" + shopCode.trim().toLowerCase() + "%");
//                queryObject.setParameter(1, Constant.STATUS_USE);
//                queryObject.setMaxResults(8);
//                List<Shop> listShop = queryObject.list();
//                if (listShop != null && listShop.size() > 0) {
//                    for (int i = 0; i < listShop.size(); i++) {
//                        this.listItem.put(listShop.get(i).getShopId(), listShop.get(i).getShopCode());
//                    }
//                }
//            }
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//        return GET_SHOP_CODE;
//    }
    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");

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
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getListStaff(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");

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
        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);


        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

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

//    /**
//     *
//     * author tamdt1
//     * date: 21/06/2009
//     * lay ten shopCode cap nhat vao textbox
//     *
//     */
//    public String getShopName() throws Exception {
//        try {
//            HttpServletRequest req = getRequest();
//            String strShopId = req.getParameter("shopId");
//            String target = req.getParameter("target");
//
//            if (strShopId != null && strShopId.trim().length() > 0) {
//                Long shopId = Long.valueOf(strShopId);
//                String queryString = "from Shop where shopId = ? ";
//                Query queryObject = getSession().createQuery(queryString);
//                queryObject.setParameter(0, shopId);
//                queryObject.setMaxResults(8);
//                List<Shop> listShop = queryObject.list();
//                if (listShop != null && listShop.size() > 0) {
//                    this.listItem.put(target, listShop.get(0).getName());
//
//                    //
//                    req.getSession().setAttribute(SESSION_CURRENT_SHOP_ID, shopId);
//                }
//            }
//        } catch (Exception ex) {
//            throw ex;
//        }
//
//        return GET_SHOP_NAME;
//    }
//    /**
//     *
//     * author tamdt1
//     * date: 24/06/2009
//     * lay du lieu cho autocompleter
//     *
//     */
//    public String getStaffCode() throws Exception {
//        try {
//            HttpServletRequest req = getRequest();
//            String staffCode = req.getParameter("reportRevenueForm.staffCode");
//            Long shopId = (Long) req.getSession().getAttribute(SESSION_CURRENT_SHOP_ID);
//
//            if (shopId != null && staffCode != null && staffCode.trim().length() > 0) {
//                String queryString = "from Staff where lower(staffCode) like ? and shopId = ? and status = ? ";
//                Query queryObject = getSession().createQuery(queryString);
//                queryObject.setParameter(0, "%" + staffCode.trim().toLowerCase() + "%");
//                queryObject.setParameter(1, shopId);
//                queryObject.setParameter(2, Constant.STATUS_USE);
//                queryObject.setMaxResults(8);
//                List<Staff> listStaff = queryObject.list();
//                if (listStaff != null && listStaff.size() > 0) {
//                    for (int i = 0; i < listStaff.size(); i++) {
//                        this.listItem.put(listStaff.get(i).getStaffId(), listStaff.get(i).getStaffCode());
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            throw ex;
//        }
//        return GET_SHOP_CODE;
//    }
//    /**
//     *
//     * author tamdt1
//     * date: 24/06/2009
//     * lay ten staffCode cap nhat vao textbox
//     *
//     */
//    public String getStaffName() throws Exception {
//        try {
//            HttpServletRequest req = getRequest();
//            String strShopId = req.getParameter("staffId");
//            String target = req.getParameter("target");
//
//            if (strShopId != null && strShopId.trim().length() > 0) {
//                String queryString = "from Staff where staffId = ? ";
//                Query queryObject = getSession().createQuery(queryString);
//                queryObject.setParameter(0, Long.valueOf(strShopId));
//                queryObject.setMaxResults(8);
//                List<Staff> listStaff = queryObject.list();
//                if (listStaff != null && listStaff.size() > 0) {
//                    this.listItem.put(target, listStaff.get(0).getName());
//                }
//            }
//        } catch (Exception ex) {
//            throw ex;
//        }
//
//        return GET_SHOP_NAME;
//    }
    /**
     *
     * author tamdt1
     * date: 24/06/2009
     * xu ly su kien loai mat hang thay doi
     *
     */
    public String changeStockType() throws Exception {
        log.info("Begin method changeStockType of ReportRevenueDAO ...");

        try {
            HttpServletRequest req = getRequest();
            String strStockTypeId = req.getParameter("stockTypeId");
            String strTarget = req.getParameter("target");

            if (strStockTypeId != null && !strStockTypeId.trim().equals("")) {
                Long stockTypeId = Long.valueOf(strStockTypeId);
                //lay danh sach cac mat hang
                String strQueryStockModel = "select stockModelId, name from StockModel where stockTypeId = ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Query queryStockModel = getSession().createQuery(strQueryStockModel);
                queryStockModel.setParameter(0, stockTypeId);
                queryStockModel.setParameter(1, Constant.STATUS_USE);
                List listStockModel = queryStockModel.list();

                String[] headerStockModel = {"", "--Chọn mặt hàng--"};
                List tmpListStockModel = new ArrayList();
                tmpListStockModel.add(headerStockModel);
                tmpListStockModel.addAll(listStockModel);

                //them vao ket qua tra ve, cap nhat danh sach mat hang
                this.listItem.put(strTarget, tmpListStockModel);

            } else {
                //reset lai cac vung tuong ung

                String[] headerStockModel = {"", "--Chọn mặt hàng--"};
                List tmpListStockModel = new ArrayList();
                tmpListStockModel.add(headerStockModel);

                //them vao ket qua tra ve, cap nhat danh sach mat hang
                this.listItem.put(strTarget, tmpListStockModel);

            }

        } catch (Exception ex) {
            throw ex;
        }

        pageForward = CHANGE_STOCK_TYPE;

        log.info("Begin method changeStockType of ReportRevenueDAO ...");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 24/06/2009
     * lay du lieu cho cac combobox
     *
     */
    private void getDataForCombobox() throws Exception {
        HttpServletRequest req = getRequest();

//        //lay danh sach cac stockType
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List<StockType> listStockType = stockTypeDAO.findByProperty(
                StockTypeDAO.STATUS, Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_STOCK_TYPE, listStockType);
//
//        //lay danh sach cac mat hang (neu co loai mat hang)
//        Long stockTypeId = this.reportRevenueForm.getStockTypeId();
//        if (stockTypeId != null) {
//
//            StockModelDAO stockModelDAO = new StockModelDAO();
//            stockModelDAO.setSession(this.getSession());
//            List<StockModel> listStockModel = stockModelDAO.findByPropertyWithStatus(
//                    StockModelDAO.STOCK_TYPE_ID, stockTypeId, Constant.STATUS_USE);
//            req.setAttribute(REQUEST_LIST_STOCK_MODEL, listStockModel);
//        }

        //lay danh sach cac ly do
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> listReason = reasonDAO.findByPropertyWithStatus(
                ReasonDAO.REASON_TYPE, Constant.SALE_REASON_GROUP_CODE, Constant.STATUS_USE.toString());
        req.setAttribute(REQUEST_LIST_REASON, listReason);

        //lay danh sach hinh thuc thanh toan
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());
        List<AppParams> listPricePolicies = appParamsDAO.findAppParamsByType("PAY_METHOD");
        req.setAttribute(REQUEST_LIST_PAY_METHOD, listPricePolicies);

        //lay danh sach cac telecomservices
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List<TelecomService> listTelecomService = telecomServiceDAO.findTelecomServicesByStatus(
                Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_TELECOM_SERVICE, listTelecomService);

        //phan quyen chon
        if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("reportRevenueShop"), req)) {
            req.getSession().setAttribute("Edit", "true");
        }
        if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("reportRevenueStaff"), req)) {
            req.getSession().setAttribute("EditStaff", "true");
        }

    }

    //vunt them bao cao theo CTV/DB/DL
    private List<VSaleTransDetail> getListDetailByShopCTVDB() throws Exception {

        List<VSaleTransDetail> listVSaleTransDetail = new ArrayList<VSaleTransDetail>();

        Long shopId = this.reportRevenueForm.getShopId();
        Long stockTypeId = this.reportRevenueForm.getStockTypeId();
        Long stockModelId = this.reportRevenueForm.getStockModelId();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        String saleTransType = this.reportRevenueForm.getSaleTransType();
        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
        Long reasonId = this.reportRevenueForm.getReasonId();
        Long telecomServiceId = this.reportRevenueForm.getTelecomServiceId();
        Boolean billedSaleTrans = this.reportRevenueForm.getBilledSaleTrans();
        Boolean notBilledSaleTrans = this.reportRevenueForm.getNotBilledSaleTrans();
        Boolean cancelSaleTrans = this.reportRevenueForm.getCancelSaleTrans();
        Long groupType = this.reportRevenueForm.getGroupType();
        Boolean hasMoney = this.reportRevenueForm.getHasMoney();
        Boolean noMoney = this.reportRevenueForm.getNoMoney();
        Long groupBySaleTransType = this.reportRevenueForm.getGroupBySaleTransType();
        String ownerCode = this.reportRevenueForm.getOwnerCode();
        Long staffId = getStaffId(ownerCode);
        String ownerManagementCode = this.reportRevenueForm.getStaffCode();
        Long staffIdManamgement = getStaffId(ownerManagementCode);

        List listParameter = new ArrayList();

        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }

        /*
        lay thong tin chi tiet cua 1 giao dich, phuc vu viec xuat bao cao doanh thu
        1 giao dich co the bao gom 3 loai:
        1> cac giao dich binh thuong: 1 giao dich bao gom cac mat hang, moi mat hang co' 1 gia'
        2> cac giao dich thuoc loai lam dich vu: 1 giao dich bao gom gia cua mat hang + gia cua dich vu
        3> cac giao dich ban go'i hang: 1 giao dich chi bao gom gia cua goi hang, ko quan tam den gia cua dich vu
         */

//        //-- cac giao dich binh thuong
//        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
//        strNormalSaleTransQuery.append(""
//                + "SELECT a.sale_trans_detail_id, d.stock_type_id, d.name AS stock_type_name, b.stock_model_id, b.stock_model_code, b.NAME AS stock_model_name, "
//                + "       a.quantity, c.price, a.amount, (nvl(a.discount_amount,0) + nvl(a.discount_amount,0) * e.vat / 100) AS discount_amount, a.vat_amount, "
//                + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, "
//                + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
//        strNormalSaleTransQuery.append(""
//                + "FROM   sale_trans_detail a, "
//                + "       stock_model b, "
//                + "       price c, "
//                + "       stock_type d, "
//                + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.staff_id, c.staff_code, c.name AS staff_name, "
//                + "                   a.sale_trans_date, a.sale_trans_type, DECODE (a.sale_trans_type, "
//                + "                                                                  1, 'Bán lẻ', "
//                + "                                                                  2, 'Bán đại lý', "
//                + "                                                                  3, 'Bán cộng tác viên', "
//                + "                                                                   4, 'Làm dịch vụ', "
//                + "                                                                  5, 'Bán hàng khuyến mại', "
//                + "                                                                  6, 'Xuất bán nội bộ', "
//                + "                                                                  7, 'Đấu nối/làm DV' "
//                + "                                                                ) AS sale_trans_type_name, "
//                + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, a.vat AS vat "
//                + "           FROM    sale_trans a, "
//                + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id "
//                + "                       FROM v_shop_tree "
//                + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ?) "
//                + "                       UNION ALL "
//                + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id "
//                + "                       FROM shop"
//                + "                       WHERE shop_id = ? "
//                + "                   ) b, "
//                + "                   staff c "
//                + "           WHERE   1 = 1 "
//                + "                   AND b.shop_id = a.shop_id "
//                + "                   AND c.staff_id = a.staff_id "
//                + "                   AND a.sale_trans_type = 7 "
//                + "                   AND a.sale_trans_date >= trunc(?) "
//                + "                   AND a.sale_trans_date < trunc(?) "
//                + "       ) e ");
//        strNormalSaleTransQuery.append(""
//                + "WHERE  1 = 1 "
//                + "       AND b.stock_model_id = a.stock_model_id "
//                + "       AND c.price_id = a.price_id "
//                + "       AND b.stock_type_id = d.stock_type_id "
//                + "       AND e.sale_trans_id = a.sale_trans_id "
//                + "       AND a.sale_services_id IS NULL " + //--chi lay cac mat hang khong thuoc goi hang, co dieu kien nay la do trong bang sale_trans_detail luu ca chi tiet thong tin ve goi hang, vi du goi hang bao gom 2 mat hang -> luu thanh 3 ban ghi: 1 ban ghi ve goi hang, 2 ban ghi ve cac mat hang thuoc goi hang
//                "       AND a.sale_trans_date >= trunc(?) "
//                + "       AND a.sale_trans_date < trunc(?) ");
//
//        //co 6 tham so trong cau truy van doi voi cac giao dich binh htuong
//        listParameter.add(shopId);
//        listParameter.add(shopId);
//        listParameter.add(fromDate);
//        listParameter.add(afterToDateOneDay);
//        listParameter.add(fromDate);
//        listParameter.add(afterToDateOneDay);

        //-- lay cac giao dich thuoc loai lam dich vu
        StringBuffer strServicesSaleTransQuery = new StringBuffer("");
        strServicesSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, -1 AS stock_type_id, 'Làm dịch vụ' AS stock_type_name, b.sale_services_id AS stock_model_id, b.code AS stock_model_code, b.name AS stock_model_name, " + "       1 AS quantity, e.amount AS price, e.amount, e.discount_amount, e.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strServicesSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       sale_services b, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.staff_id, c.staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, 'Đấu nối/làm DV' AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, " + "                   a.amount_tax AS amount, " + "                   (nvl(a.discount,0) + nvl(a.discount,0) * a.vat / 100) AS discount_amount, " + "                   a.tax AS vat_amount" + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ?) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   staff c " + "           WHERE   1 = 1 " + "                   AND a.shop_id = b.shop_id " + "                   AND a.staff_id = c.staff_id " + "                   AND a.sale_trans_type = 7 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strServicesSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND a.sale_trans_id = e.sale_trans_id " + "       AND b.sale_services_id = a.sale_services_id " + "       AND b.sale_type = 1 " + "       AND a.stock_model_id IS NULL " + "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich lam dich vu
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //-- lay cac giao dich ban goi hang
        StringBuffer strPackageSaleTransQuery = new StringBuffer("");
        strPackageSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, -2 AS stock_type_id, 'Bán gói hàng' AS stock_type_name, b.sale_services_id AS stock_model_id, b.code AS stock_model_code, b.NAME AS stock_model_name, " + "       a.quantity, c.price, a.amount, (a.discount_amount + a.discount_amount * e.vat / 100) AS discount_amount, a.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strPackageSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       sale_services b, " + "       sale_services_price c, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.staff_id, c.staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, DECODE (a.sale_trans_type, " + "                                                               1, 'Bán lẻ', " + "                                                               2, 'Bán đại lý', " + "                                                               3, 'Bán cộng tác viên', " + "                                                               4, 'Làm dịch vụ', " + "                                                               5, 'Bán hàng khuyến mại', " + "                                                               6, 'Xuất bán nội bộ', " + "                                                                  7, 'Đấu nối/làm DV' " + "                                                           ) AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, a.vat " + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ?) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   staff c " + "           WHERE   1 = 1 " + "                   AND a.shop_id = b.shop_id " + "                   AND a.staff_id = c.staff_id " + "                   AND a.sale_trans_type = 7 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strPackageSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND a.sale_services_id = b.sale_services_id " + "       AND a.sale_services_price_id = c.sale_services_price_id " + "       AND a.sale_trans_id = e.sale_trans_id " + "       AND a.stock_model_id IS NULL " //--co dieu kien nay la do trong bang sale_trans_detail luu ca chi tiet thong tin ve goi hang, vi du goi hang bao gom 2 mat hang -> luu thanh 3 ban ghi: 1 ban ghi ve goi hang, 2 ban ghi ve cac mat hang thuoc goi hang
                + "       AND b.sale_type = 2 " + "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich lam dich vu
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //VSaleTransDetail = union all cua 3 loai tren
        StringBuffer strVSaleTransDetailQuery = new StringBuffer("");
//        strVSaleTransDetailQuery.append(strNormalSaleTransQuery);
//        strVSaleTransDetailQuery.append(" UNION ALL ");
        strVSaleTransDetailQuery.append(strServicesSaleTransQuery);
        strVSaleTransDetailQuery.append(" UNION ALL ");
        strVSaleTransDetailQuery.append(strPackageSaleTransQuery);

        StringBuffer strQuery = new StringBuffer("");
        strQuery.append("" + "SELECT a.shop_name shopName, a.sale_trans_type_name saleTransTypeName, a.stock_type_name stockTypeName, a.stock_model_code stockModelCode, a.stock_model_name stockModelName, " + "       sum(a.quantity) quantity, a.price price, sum(a.amount) amount, sum(a.discount_amount) discountAmount, sum(a.vat_amount) vatAmount ");
        strQuery.append("" + "FROM ").append("( ").append(strVSaleTransDetailQuery).append(" ) a, staff b ");
        strQuery.append("" + "WHERE 1 = 1 ");
        strQuery.append("and a.staff_id = b.staff_id ");
        if (staffIdManamgement != null && staffIdManamgement.compareTo(0L) > 0) {
            strQuery.append("and b.staff_Owner_Id = ? ");
            listParameter.add(staffIdManamgement);
        }

        if (staffId != null && staffId.compareTo(0L) > 0) {
            strQuery.append("and a.staff_id = ? ");
            listParameter.add(staffId);
        }

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.stock_type_id = ? ");
            listParameter.add(stockTypeId);
        }

//        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
//            strQuery.append("and a.stock_model_id = ? ");
//            listParameter.add(stockModelId);
//        }
        /**
         * AnDV 12/2/10
         * Them bao cao mat hang nam trong cac dich vu ban hang
         * tuy theo trang thai checkBox reportStockModelInSaleService
         */
        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
            Boolean reportStockModelInSaleService = this.reportRevenueForm.getReportStockModelInSaleService();
            if (reportStockModelInSaleService != null && reportStockModelInSaleService.equals(true)) {
                StringBuffer stringBuffer = new StringBuffer("");
                List<BigDecimal> lstSaleServiceId = new ArrayList<BigDecimal>();
                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
                stringBuffer.append(stockModelId.toString());
                if (lstSaleServiceId != null && lstSaleServiceId.size() > 0) {
                    for (int i = 0; i < lstSaleServiceId.size(); i++) {
                        stringBuffer.append(",");
                        stringBuffer.append(lstSaleServiceId.get(i).toString());

                    }
                }
                strQuery.append("and a.stock_model_id in (" + stringBuffer.toString() + ") ");
//                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
            } else {
                strQuery.append("and a.stock_model_id = ? ");
                listParameter.add(stockModelId);
            }
        }
        //end:andv

        if (saleTransType != null && !saleTransType.trim().equals("")) {
            strQuery.append("and a.sale_trans_type = ? ");
            listParameter.add(saleTransType);
        }

        if (channelTypeId != null && channelTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.channel_type_id = ? ");
            listParameter.add(channelTypeId);
        }

        if (reasonId != null && reasonId.compareTo(0L) > 0) {
            strQuery.append("and a.reason_id = ? ");
            listParameter.add(reasonId);
        }

        if (telecomServiceId != null && telecomServiceId.compareTo(0L) > 0) {
            strQuery.append("and a.telecom_service_id = ? ");
            listParameter.add(telecomServiceId);
        }

        strQuery.append("and (a.sale_trans_status = ? "); //them de su dung toan tu or
        listParameter.add("-1");

        if (billedSaleTrans != null && billedSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_BILLED);
        }

        if (notBilledSaleTrans != null && notBilledSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        }

        if (cancelSaleTrans != null && cancelSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_CANCEL);
        }

        strQuery.append(") "); //

//        strQuery.append("and (a.sale_trans_type = ? "); //them de su dung toan tu or
//        listParameter.add("-1");
//
//        if (hasMoney != null && hasMoney.equals(true)) {
//            //cac giao dich co thu tien (bao gom ban le, ban dai ly, ban CTV, DB, lam giao dich CM)
//            strQuery.append("or a.sale_trans_type = ? or a.sale_trans_type = ? or a.sale_trans_type = ? or a.sale_trans_type = ? ");
//            listParameter.add(Constant.SALE_TRANS_TYPE_RETAIL);
//            listParameter.add(Constant.SALE_TRANS_TYPE_AGENT);
//            listParameter.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
//            listParameter.add(Constant.SALE_TRANS_TYPE_SERVICE);
//        }
//        if (noMoney != null && noMoney.equals(true)) {
//            //cac giao dich khong thu tien (bao gom ban khuyen mai va ban hang noi bo)
//            strQuery.append("or a.sale_trans_type = ? or a.sale_trans_type = ? ");
//            listParameter.add(Constant.SALE_TRANS_TYPE_PROMOTION);
//            listParameter.add(Constant.SALE_TRANS_TYPE_INTERNAL);
//        }
//
//        strQuery.append(") "); //

        strQuery.append("group by a.shop_name, a.sale_trans_type_name, a.stock_type_name, a.stock_model_code, a.stock_model_name, a.price ");
        strQuery.append("order by a.stock_model_code");

        System.out.println("===TUANPV: Cau lenh SQL: " + strQuery.toString());

        Query query = getSession().createSQLQuery(strQuery.toString()).addScalar("shopName", Hibernate.STRING).addScalar("saleTransTypeName", Hibernate.STRING).addScalar("stockTypeName", Hibernate.STRING).addScalar("stockModelCode", Hibernate.STRING).addScalar("stockModelName", Hibernate.STRING).addScalar("quantity", Hibernate.LONG).addScalar("price", Hibernate.LONG).addScalar("amount", Hibernate.DOUBLE).addScalar("discountAmount", Hibernate.LONG).addScalar("vatAmount", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(VSaleTransDetail.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        listVSaleTransDetail = query.list();
        return listVSaleTransDetail;

    }

    private List<VSaleTransDetail> getListDetailByShopAgent() throws Exception {

        List<VSaleTransDetail> listVSaleTransDetail = new ArrayList<VSaleTransDetail>();

        Long shopId = this.reportRevenueForm.getShopId();
        Long stockTypeId = this.reportRevenueForm.getStockTypeId();
        Long stockModelId = this.reportRevenueForm.getStockModelId();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        String saleTransType = this.reportRevenueForm.getSaleTransType();
        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
        Long reasonId = this.reportRevenueForm.getReasonId();
        Long telecomServiceId = this.reportRevenueForm.getTelecomServiceId();
        Boolean billedSaleTrans = this.reportRevenueForm.getBilledSaleTrans();
        Boolean notBilledSaleTrans = this.reportRevenueForm.getNotBilledSaleTrans();
        Boolean cancelSaleTrans = this.reportRevenueForm.getCancelSaleTrans();
        Long groupType = this.reportRevenueForm.getGroupType();
        Boolean hasMoney = this.reportRevenueForm.getHasMoney();
        Boolean noMoney = this.reportRevenueForm.getNoMoney();
        Long groupBySaleTransType = this.reportRevenueForm.getGroupBySaleTransType();
        String ownerCode = this.reportRevenueForm.getOwnerCode();
        Long staffId = getShopId(ownerCode);

        List listParameter = new ArrayList();

        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }

        /*
        lay thong tin chi tiet cua 1 giao dich, phuc vu viec xuat bao cao doanh thu
        1 giao dich co the bao gom 3 loai:
        1> cac giao dich binh thuong: 1 giao dich bao gom cac mat hang, moi mat hang co' 1 gia'
        2> cac giao dich thuoc loai lam dich vu: 1 giao dich bao gom gia cua mat hang + gia cua dich vu
        3> cac giao dich ban go'i hang: 1 giao dich chi bao gom gia cua goi hang, ko quan tam den gia cua dich vu
         */

//        //-- cac giao dich binh thuong
//        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
//        strNormalSaleTransQuery.append(""
//                + "SELECT a.sale_trans_detail_id, d.stock_type_id, d.name AS stock_type_name, b.stock_model_id, b.stock_model_code, b.NAME AS stock_model_name, "
//                + "       a.quantity, c.price, a.amount, (nvl(a.discount_amount,0) + nvl(a.discount_amount,0) * e.vat / 100) AS discount_amount, a.vat_amount, "
//                + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, "
//                + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
//        strNormalSaleTransQuery.append(""
//                + "FROM   sale_trans_detail a, "
//                + "       stock_model b, "
//                + "       price c, "
//                + "       stock_type d, "
//                + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.shop_id as staff_id, c.shop_code AS staff_code, c.name AS staff_name, "
//                + "                   a.sale_trans_date, a.sale_trans_type, DECODE (a.sale_trans_type, "
//                + "                                                                  1, 'Bán lẻ', "
//                + "                                                                  2, 'Bán đại lý', "
//                + "                                                                  3, 'Bán cộng tác viên', "
//                + "                                                                   4, 'Làm dịch vụ', "
//                + "                                                                  5, 'Bán hàng khuyến mại', "
//                + "                                                                  6, 'Xuất bán nội bộ', "
//                + "                                                                  7, 'Đấu nối/làm DV' "
//                + "                                                                ) AS sale_trans_type_name, "
//                + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, a.vat AS vat "
//                + "           FROM    sale_trans a, "
//                + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id "
//                + "                       FROM v_shop_tree "
//                + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ? AND channel_type_id <> 4) "
//                + "                       UNION ALL "
//                + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id "
//                + "                       FROM shop"
//                + "                       WHERE shop_id = ? "
//                + "                   ) b, "
//                + "                   shop c "
//                + "           WHERE   1 = 1 "
//                + "                   AND a.shop_id = c.shop_id"
//                + "                   AND c.parent_shop_id = b.shop_id"
//                + "                   AND a.sale_trans_type = 7 "
//                + "                   AND a.sale_trans_date >= trunc(?) "
//                + "                   AND a.sale_trans_date < trunc(?) "
//                + "       ) e ");
//        strNormalSaleTransQuery.append(""
//                + "WHERE  1 = 1 "
//                + "       AND b.stock_model_id = a.stock_model_id "
//                + "       AND c.price_id = a.price_id "
//                + "       AND b.stock_type_id = d.stock_type_id "
//                + "       AND e.sale_trans_id = a.sale_trans_id "
//                + "       AND a.sale_services_id IS NULL " + //--chi lay cac mat hang khong thuoc goi hang, co dieu kien nay la do trong bang sale_trans_detail luu ca chi tiet thong tin ve goi hang, vi du goi hang bao gom 2 mat hang -> luu thanh 3 ban ghi: 1 ban ghi ve goi hang, 2 ban ghi ve cac mat hang thuoc goi hang
//                "       AND a.sale_trans_date >= trunc(?) "
//                + "       AND a.sale_trans_date < trunc(?) ");
//
//        //co 6 tham so trong cau truy van doi voi cac giao dich binh htuong
//        listParameter.add(shopId);
//        listParameter.add(shopId);
//        listParameter.add(fromDate);
//        listParameter.add(afterToDateOneDay);
//        listParameter.add(fromDate);
//        listParameter.add(afterToDateOneDay);

        //-- lay cac giao dich thuoc loai lam dich vu
        StringBuffer strServicesSaleTransQuery = new StringBuffer("");
        strServicesSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, -1 AS stock_type_id, 'Làm dịch vụ' AS stock_type_name, b.sale_services_id AS stock_model_id, b.code AS stock_model_code, b.name AS stock_model_name, " + "       1 AS quantity, e.amount AS price, e.amount, e.discount_amount, e.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strServicesSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       sale_services b, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.shop_id as staff_id, c.shop_code AS staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, 'Đấu nối/làm DV' AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, " + "                   a.amount_tax AS amount, " + "                   (nvl(a.discount,0) + nvl(a.discount,0) * a.vat / 100) AS discount_amount, " + "                   a.tax AS vat_amount" + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ? AND channel_type_id <> 4) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   shop c " + "           WHERE   1 = 1 " + "                   AND a.shop_id = c.shop_id" + "                   AND c.parent_shop_id = b.shop_id" + "                   AND a.sale_trans_type = 7 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strServicesSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND a.sale_trans_id = e.sale_trans_id " + "       AND b.sale_services_id = a.sale_services_id " + "       AND b.sale_type = 1 " + "       AND a.stock_model_id IS NULL " + "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich lam dich vu
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //-- lay cac giao dich ban goi hang
        StringBuffer strPackageSaleTransQuery = new StringBuffer("");
        strPackageSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, -2 AS stock_type_id, 'Bán gói hàng' AS stock_type_name, b.sale_services_id AS stock_model_id, b.code AS stock_model_code, b.NAME AS stock_model_name, " + "       a.quantity, c.price, a.amount, (a.discount_amount + a.discount_amount * e.vat / 100) AS discount_amount, a.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strPackageSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       sale_services b, " + "       sale_services_price c, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.shop_id as staff_id, c.shop_code AS staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, DECODE (a.sale_trans_type, " + "                                                               1, 'Bán lẻ', " + "                                                               2, 'Bán đại lý', " + "                                                               3, 'Bán cộng tác viên', " + "                                                               4, 'Làm dịch vụ', " + "                                                               5, 'Bán hàng khuyến mại', " + "                                                               6, 'Xuất bán nội bộ', " + "                                                                  7, 'Đấu nối/làm DV' " + "                                                           ) AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, a.vat " + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ? AND channel_type_id <> 4) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   shop c " + "           WHERE   1 = 1 " + "                   AND a.shop_id = c.shop_id" + "                   AND c.parent_shop_id = b.shop_id" + "                   AND a.sale_trans_type = 7 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strPackageSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND a.sale_services_id = b.sale_services_id " + "       AND a.sale_services_price_id = c.sale_services_price_id " + "       AND a.sale_trans_id = e.sale_trans_id " + "       AND a.stock_model_id IS NULL " //--co dieu kien nay la do trong bang sale_trans_detail luu ca chi tiet thong tin ve goi hang, vi du goi hang bao gom 2 mat hang -> luu thanh 3 ban ghi: 1 ban ghi ve goi hang, 2 ban ghi ve cac mat hang thuoc goi hang
                + "       AND b.sale_type = 2 " + "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich lam dich vu
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //VSaleTransDetail = union all cua 3 loai tren
        StringBuffer strVSaleTransDetailQuery = new StringBuffer("");
//        strVSaleTransDetailQuery.append(strNormalSaleTransQuery);
//        strVSaleTransDetailQuery.append(" UNION ALL ");
        strVSaleTransDetailQuery.append(strServicesSaleTransQuery);
        strVSaleTransDetailQuery.append(" UNION ALL ");
        strVSaleTransDetailQuery.append(strPackageSaleTransQuery);

        StringBuffer strQuery = new StringBuffer("");
        strQuery.append("" + "SELECT a.shop_name shopName, a.sale_trans_type_name saleTransTypeName, a.stock_type_name stockTypeName, a.stock_model_code stockModelCode, a.stock_model_name stockModelName, " + "       sum(a.quantity) quantity, a.price price, sum(a.amount) amount, sum(a.discount_amount) discountAmount, sum(a.vat_amount) vatAmount ");
        strQuery.append("" + "FROM ").append("( ").append(strVSaleTransDetailQuery).append(" ) a ");
        strQuery.append("" + "WHERE 1 = 1 ");

        if (staffId != null && staffId.compareTo(0L) > 0) {
            strQuery.append("and a.staff_id = ? ");
            listParameter.add(staffId);
        }

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.stock_type_id = ? ");
            listParameter.add(stockTypeId);
        }

//        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
//            strQuery.append("and a.stock_model_id = ? ");
//            listParameter.add(stockModelId);
//        }
        /**
         * AnDV 12/2/10
         * Them bao cao mat hang nam trong cac dich vu ban hang
         * tuy theo trang thai checkBox reportStockModelInSaleService
         */
        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
            Boolean reportStockModelInSaleService = this.reportRevenueForm.getReportStockModelInSaleService();
            if (reportStockModelInSaleService != null && reportStockModelInSaleService.equals(true)) {
                StringBuffer stringBuffer = new StringBuffer("");
                List<BigDecimal> lstSaleServiceId = new ArrayList<BigDecimal>();
                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
                stringBuffer.append(stockModelId.toString());
                if (lstSaleServiceId != null && lstSaleServiceId.size() > 0) {
                    for (int i = 0; i < lstSaleServiceId.size(); i++) {
                        stringBuffer.append(",");
                        stringBuffer.append(lstSaleServiceId.get(i).toString());

                    }
                }
                strQuery.append("and a.stock_model_id in (" + stringBuffer.toString() + ") ");
//                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
            } else {
                strQuery.append("and a.stock_model_id = ? ");
                listParameter.add(stockModelId);
            }
        }
        //Andv: end

        if (saleTransType != null && !saleTransType.trim().equals("")) {
            strQuery.append("and a.sale_trans_type = ? ");
            listParameter.add(saleTransType);
        }

        if (channelTypeId != null && channelTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.channel_type_id = ? ");
            listParameter.add(channelTypeId);
        }

        if (reasonId != null && reasonId.compareTo(0L) > 0) {
            strQuery.append("and a.reason_id = ? ");
            listParameter.add(reasonId);
        }

        if (telecomServiceId != null && telecomServiceId.compareTo(0L) > 0) {
            strQuery.append("and a.telecom_service_id = ? ");
            listParameter.add(telecomServiceId);
        }

        strQuery.append("and (a.sale_trans_status = ? "); //them de su dung toan tu or
        listParameter.add("-1");

        if (billedSaleTrans != null && billedSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_BILLED);
        }

        if (notBilledSaleTrans != null && notBilledSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        }

        if (cancelSaleTrans != null && cancelSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_CANCEL);
        }

        strQuery.append(") "); //

//        strQuery.append("and (a.sale_trans_type = ? "); //them de su dung toan tu or
//        listParameter.add("-1");
//
//        if (hasMoney != null && hasMoney.equals(true)) {
//            //cac giao dich co thu tien (bao gom ban le, ban dai ly, ban CTV, DB, lam giao dich CM)
//            strQuery.append("or a.sale_trans_type = ? or a.sale_trans_type = ? or a.sale_trans_type = ? or a.sale_trans_type = ? ");
//            listParameter.add(Constant.SALE_TRANS_TYPE_RETAIL);
//            listParameter.add(Constant.SALE_TRANS_TYPE_AGENT);
//            listParameter.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
//            listParameter.add(Constant.SALE_TRANS_TYPE_SERVICE);
//        }
//        if (noMoney != null && noMoney.equals(true)) {
//            //cac giao dich khong thu tien (bao gom ban khuyen mai va ban hang noi bo)
//            strQuery.append("or a.sale_trans_type = ? or a.sale_trans_type = ? ");
//            listParameter.add(Constant.SALE_TRANS_TYPE_PROMOTION);
//            listParameter.add(Constant.SALE_TRANS_TYPE_INTERNAL);
//        }
//
//        strQuery.append(") "); //

        strQuery.append("group by a.shop_name, a.sale_trans_type_name, a.stock_type_name, a.stock_model_code, a.stock_model_name, a.price ");
        strQuery.append("order by a.stock_model_code");

        System.out.println("===TUANPV: Cau lenh SQL: " + strQuery.toString());

        Query query = getSession().createSQLQuery(strQuery.toString()).addScalar("shopName", Hibernate.STRING).addScalar("saleTransTypeName", Hibernate.STRING).addScalar("stockTypeName", Hibernate.STRING).addScalar("stockModelCode", Hibernate.STRING).addScalar("stockModelName", Hibernate.STRING).addScalar("quantity", Hibernate.LONG).addScalar("price", Hibernate.LONG).addScalar("amount", Hibernate.DOUBLE).addScalar("discountAmount", Hibernate.LONG).addScalar("vatAmount", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(VSaleTransDetail.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        listVSaleTransDetail = query.list();
        return listVSaleTransDetail;

    }

    private List<VSaleTransDetail> getListDetailByStaffCTVDB() throws Exception {

        List<VSaleTransDetail> listVSaleTransDetail = new ArrayList<VSaleTransDetail>();

        Long shopId = this.reportRevenueForm.getShopId();
        Long stockTypeId = this.reportRevenueForm.getStockTypeId();
        Long stockModelId = this.reportRevenueForm.getStockModelId();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        String saleTransType = this.reportRevenueForm.getSaleTransType();
        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
        Long reasonId = this.reportRevenueForm.getReasonId();
        Long telecomServiceId = this.reportRevenueForm.getTelecomServiceId();
        Boolean billedSaleTrans = this.reportRevenueForm.getBilledSaleTrans();
        Boolean notBilledSaleTrans = this.reportRevenueForm.getNotBilledSaleTrans();
        Boolean cancelSaleTrans = this.reportRevenueForm.getCancelSaleTrans();
        Long groupType = this.reportRevenueForm.getGroupType();
        Boolean hasMoney = this.reportRevenueForm.getHasMoney();
        Boolean noMoney = this.reportRevenueForm.getNoMoney();
        Long groupBySaleTransType = this.reportRevenueForm.getGroupBySaleTransType();
        String ownerCode = this.reportRevenueForm.getOwnerCode();
        Long staffId = getStaffId(ownerCode);
        String ownerManagementCode = this.reportRevenueForm.getStaffCode();
        Long staffIdManamgement = getStaffId(ownerManagementCode);

        List listParameter = new ArrayList();

        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }

        /*
        lay thong tin chi tiet cua 1 giao dich, phuc vu viec xuat bao cao doanh thu
        1 giao dich co the bao gom 3 loai:
        1> cac giao dich binh thuong: 1 giao dich bao gom cac mat hang, moi mat hang co' 1 gia'
        2> cac giao dich thuoc loai lam dich vu: 1 giao dich bao gom gia cua mat hang + gia cua dich vu
        3> cac giao dich ban go'i hang: 1 giao dich chi bao gom gia cua goi hang, ko quan tam den gia cua dich vu
         */

        //-- cac giao dich binh thuong
//        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
//        strNormalSaleTransQuery.append(""
//                + "SELECT a.sale_trans_detail_id, d.stock_type_id, d.name AS stock_type_name, b.stock_model_id, b.stock_model_code, b.NAME AS stock_model_name, "
//                + "       a.quantity, c.price, a.amount, (nvl(a.discount_amount,0) + nvl(a.discount_amount,0) * e.vat / 100) AS discount_amount, a.vat_amount, "
//                + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, "
//                + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
//        strNormalSaleTransQuery.append(""
//                + "FROM   sale_trans_detail a, "
//                + "       stock_model b, "
//                + "       price c, "
//                + "       stock_type d, "
//                + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.staff_id, c.staff_code, c.name AS staff_name, "
//                + "                   a.sale_trans_date, a.sale_trans_type, DECODE (a.sale_trans_type, "
//                + "                                                                  1, 'Bán lẻ', "
//                + "                                                                  2, 'Bán đại lý', "
//                + "                                                                  3, 'Bán cộng tác viên', "
//                + "                                                                   4, 'Làm dịch vụ', "
//                + "                                                                  5, 'Bán hàng khuyến mại', "
//                + "                                                                  6, 'Xuất bán nội bộ', "
//                + "                                                                  7, 'Đấu nối/làm DV' "
//                + "                                                                ) AS sale_trans_type_name, "
//                + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, a.vat AS vat "
//                + "           FROM    sale_trans a, "
//                + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id "
//                + "                       FROM v_shop_tree "
//                + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ?) "
//                + "                       UNION ALL "
//                + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id "
//                + "                       FROM shop"
//                + "                       WHERE shop_id = ? "
//                + "                   ) b, "
//                + "                   staff c "
//                + "           WHERE   1 = 1 "
//                + "                   AND b.shop_id = a.shop_id "
//                + "                   AND c.staff_id = a.staff_id "
//                + "                   AND a.sale_trans_type = 7 "
//                + "                   AND a.sale_trans_date >= trunc(?) "
//                + "                   AND a.sale_trans_date < trunc(?) "
//                + "       ) e ");
//        strNormalSaleTransQuery.append(""
//                + "WHERE  1 = 1 "
//                + "       AND b.stock_model_id = a.stock_model_id "
//                + "       AND c.price_id = a.price_id "
//                + "       AND b.stock_type_id = d.stock_type_id "
//                + "       AND e.sale_trans_id = a.sale_trans_id "
//                + "       AND a.sale_services_id IS NULL " + //--chi lay cac mat hang khong thuoc goi hang, co dieu kien nay la do trong bang sale_trans_detail luu ca chi tiet thong tin ve goi hang, vi du goi hang bao gom 2 mat hang -> luu thanh 3 ban ghi: 1 ban ghi ve goi hang, 2 ban ghi ve cac mat hang thuoc goi hang
//                "       AND a.sale_trans_date >= trunc(?) "
//                + "       AND a.sale_trans_date < trunc(?) ");
//
//        //co 6 tham so trong cau truy van doi voi cac giao dich binh htuong
//        listParameter.add(shopId);
//        listParameter.add(shopId);
//        listParameter.add(fromDate);
//        listParameter.add(afterToDateOneDay);
//        listParameter.add(fromDate);
//        listParameter.add(afterToDateOneDay);

        //-- lay cac giao dich thuoc loai lam dich vu
        StringBuffer strServicesSaleTransQuery = new StringBuffer("");
        strServicesSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, -1 AS stock_type_id, 'Làm dịch vụ' AS stock_type_name, b.sale_services_id AS stock_model_id, b.code AS stock_model_code, b.name AS stock_model_name, " + "       1 AS quantity, e.amount AS price, e.amount, e.discount_amount, e.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strServicesSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       sale_services b, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.staff_id, c.staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, 'Đấu nối/làm DV' AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, " + "                   a.amount_tax AS amount, " + "                   (nvl(a.discount,0) + nvl(a.discount,0) * a.vat / 100) AS discount_amount, " + "                   a.tax AS vat_amount" + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ?) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   staff c " + "           WHERE   1 = 1 " + "                   AND a.shop_id = b.shop_id " + "                   AND a.staff_id = c.staff_id " + "                   AND a.sale_trans_type = 7 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strServicesSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND a.sale_trans_id = e.sale_trans_id " + "       AND b.sale_services_id = a.sale_services_id " + "       AND b.sale_type = 1 " + "       AND a.stock_model_id IS NULL " + "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich lam dich vu
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //-- lay cac giao dich ban goi hang
        StringBuffer strPackageSaleTransQuery = new StringBuffer("");
        strPackageSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, -2 AS stock_type_id, 'Bán gói hàng' AS stock_type_name, b.sale_services_id AS stock_model_id, b.code AS stock_model_code, b.NAME AS stock_model_name, " + "       a.quantity, c.price, a.amount, (a.discount_amount + a.discount_amount * e.vat / 100) AS discount_amount, a.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strPackageSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       sale_services b, " + "       sale_services_price c, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.staff_id, c.staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, DECODE (a.sale_trans_type, " + "                                                               1, 'Bán lẻ', " + "                                                               2, 'Bán đại lý', " + "                                                               3, 'Bán cộng tác viên', " + "                                                               4, 'Làm dịch vụ', " + "                                                               5, 'Bán hàng khuyến mại', " + "                                                               6, 'Xuất bán nội bộ', " + "                                                                  7, 'Đấu nối/làm DV' " + "                                                           ) AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, a.vat " + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ?) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   staff c " + "           WHERE   1 = 1 " + "                   AND a.shop_id = b.shop_id " + "                   AND a.staff_id = c.staff_id " + "                   AND a.sale_trans_type = 7 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strPackageSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND a.sale_services_id = b.sale_services_id " + "       AND a.sale_services_price_id = c.sale_services_price_id " + "       AND a.sale_trans_id = e.sale_trans_id " + "       AND a.stock_model_id IS NULL " //--co dieu kien nay la do trong bang sale_trans_detail luu ca chi tiet thong tin ve goi hang, vi du goi hang bao gom 2 mat hang -> luu thanh 3 ban ghi: 1 ban ghi ve goi hang, 2 ban ghi ve cac mat hang thuoc goi hang
                + "       AND b.sale_type = 2 " + "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich lam dich vu
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //VSaleTransDetail = union all cua 3 loai tren
        StringBuffer strVSaleTransDetailQuery = new StringBuffer("");
//        strVSaleTransDetailQuery.append(strNormalSaleTransQuery);
//        strVSaleTransDetailQuery.append(" UNION ALL ");
        strVSaleTransDetailQuery.append(strServicesSaleTransQuery);
        strVSaleTransDetailQuery.append(" UNION ALL ");
        strVSaleTransDetailQuery.append(strPackageSaleTransQuery);

        StringBuffer strQuery = new StringBuffer("");
        strQuery.append("" + "SELECT a.staff_Name staffName, a.shop_name shopName, a.sale_trans_type_name saleTransTypeName, a.stock_type_name stockTypeName, a.stock_model_code stockModelCode, a.stock_model_name stockModelName, " + "       sum(a.quantity) quantity, a.price price, sum(a.amount) amount, sum(a.discount_amount) discountAmount, sum(a.vat_amount) vatAmount ");
        strQuery.append("" + "FROM ").append("( ").append(strVSaleTransDetailQuery).append(" ) a, staff b ");
        strQuery.append("" + "WHERE 1 = 1 ");
        strQuery.append("and a.staff_id = b.staff_id ");
        if (staffIdManamgement != null && staffIdManamgement.compareTo(0L) > 0) {
            strQuery.append("and b.staff_Owner_Id = ? ");
            listParameter.add(staffIdManamgement);
        }

        if (staffId != null && staffId.compareTo(0L) > 0) {
            strQuery.append("and a.staff_id = ? ");
            listParameter.add(staffId);
        }

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.stock_type_id = ? ");
            listParameter.add(stockTypeId);
        }

//        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
//            strQuery.append("and a.stock_model_id = ? ");
//            listParameter.add(stockModelId);
//        }
        /**
         * AnDV 12/2/10
         * Them bao cao mat hang nam trong cac dich vu ban hang
         * tuy theo trang thai checkBox reportStockModelInSaleService
         */
        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
            Boolean reportStockModelInSaleService = this.reportRevenueForm.getReportStockModelInSaleService();
            if (reportStockModelInSaleService != null && reportStockModelInSaleService.equals(true)) {
                StringBuffer stringBuffer = new StringBuffer("");
                List<BigDecimal> lstSaleServiceId = new ArrayList<BigDecimal>();
                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
                stringBuffer.append(stockModelId.toString());
                if (lstSaleServiceId != null && lstSaleServiceId.size() > 0) {
                    for (int i = 0; i < lstSaleServiceId.size(); i++) {
                        stringBuffer.append(",");
                        stringBuffer.append(lstSaleServiceId.get(i).toString());

                    }
                }
                strQuery.append("and a.stock_model_id in (" + stringBuffer.toString() + ") ");
//                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
            } else {
                strQuery.append("and a.stock_model_id = ? ");
                listParameter.add(stockModelId);
            }
        }
        //End: AnDV

        if (saleTransType != null && !saleTransType.trim().equals("")) {
            strQuery.append("and a.sale_trans_type = ? ");
            listParameter.add(saleTransType);
        }

        if (channelTypeId != null && channelTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.channel_type_id = ? ");
            listParameter.add(channelTypeId);
        }

        if (reasonId != null && reasonId.compareTo(0L) > 0) {
            strQuery.append("and a.reason_id = ? ");
            listParameter.add(reasonId);
        }

        if (telecomServiceId != null && telecomServiceId.compareTo(0L) > 0) {
            strQuery.append("and a.telecom_service_id = ? ");
            listParameter.add(telecomServiceId);
        }

        strQuery.append("and (a.sale_trans_status = ? "); //them de su dung toan tu or
        listParameter.add("-1");

        if (billedSaleTrans != null && billedSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_BILLED);
        }

        if (notBilledSaleTrans != null && notBilledSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        }

        if (cancelSaleTrans != null && cancelSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_CANCEL);
        }

        strQuery.append(") "); //

//        strQuery.append("and (a.sale_trans_type = ? "); //them de su dung toan tu or
//        listParameter.add("-1");
//
//        if (hasMoney != null && hasMoney.equals(true)) {
//            //cac giao dich co thu tien (bao gom ban le, ban dai ly, ban CTV, DB, lam giao dich CM)
//            strQuery.append("or a.sale_trans_type = ? or a.sale_trans_type = ? or a.sale_trans_type = ? or a.sale_trans_type = ? ");
//            listParameter.add(Constant.SALE_TRANS_TYPE_RETAIL);
//            listParameter.add(Constant.SALE_TRANS_TYPE_AGENT);
//            listParameter.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
//            listParameter.add(Constant.SALE_TRANS_TYPE_SERVICE);
//        }
//        if (noMoney != null && noMoney.equals(true)) {
//            //cac giao dich khong thu tien (bao gom ban khuyen mai va ban hang noi bo)
//            strQuery.append("or a.sale_trans_type = ? or a.sale_trans_type = ? ");
//            listParameter.add(Constant.SALE_TRANS_TYPE_PROMOTION);
//            listParameter.add(Constant.SALE_TRANS_TYPE_INTERNAL);
//        }
//
//        strQuery.append(") "); //

        strQuery.append("group by  a.staff_Name, a.shop_name, a.sale_trans_type_name, a.stock_type_name, a.stock_model_code, a.stock_model_name, a.price ");
        strQuery.append("order by a.stock_model_code");

        System.out.println("===TUANPV: Cau lenh SQL: " + strQuery.toString());

        Query query = getSession().createSQLQuery(strQuery.toString()).addScalar("staffName", Hibernate.STRING).addScalar("shopName", Hibernate.STRING).addScalar("saleTransTypeName", Hibernate.STRING).addScalar("stockTypeName", Hibernate.STRING).addScalar("stockModelCode", Hibernate.STRING).addScalar("stockModelName", Hibernate.STRING).addScalar("quantity", Hibernate.LONG).addScalar("price", Hibernate.LONG).addScalar("amount", Hibernate.DOUBLE).addScalar("discountAmount", Hibernate.LONG).addScalar("vatAmount", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(VSaleTransDetail.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        listVSaleTransDetail = query.list();
        return listVSaleTransDetail;

    }

    private List<VSaleTransDetail> getListDetailByStaffAgent() throws Exception {

        List<VSaleTransDetail> listVSaleTransDetail = new ArrayList<VSaleTransDetail>();

        Long shopId = this.reportRevenueForm.getShopId();
        Long stockTypeId = this.reportRevenueForm.getStockTypeId();
        Long stockModelId = this.reportRevenueForm.getStockModelId();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        String saleTransType = this.reportRevenueForm.getSaleTransType();
        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
        Long reasonId = this.reportRevenueForm.getReasonId();
        Long telecomServiceId = this.reportRevenueForm.getTelecomServiceId();
        Boolean billedSaleTrans = this.reportRevenueForm.getBilledSaleTrans();
        Boolean notBilledSaleTrans = this.reportRevenueForm.getNotBilledSaleTrans();
        Boolean cancelSaleTrans = this.reportRevenueForm.getCancelSaleTrans();
        Long groupType = this.reportRevenueForm.getGroupType();
        Boolean hasMoney = this.reportRevenueForm.getHasMoney();
        Boolean noMoney = this.reportRevenueForm.getNoMoney();
        Long groupBySaleTransType = this.reportRevenueForm.getGroupBySaleTransType();
        String ownerCode = this.reportRevenueForm.getOwnerCode();
        Long staffId = getShopId(ownerCode);

        List listParameter = new ArrayList();

        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }

        /*
        lay thong tin chi tiet cua 1 giao dich, phuc vu viec xuat bao cao doanh thu
        1 giao dich co the bao gom 3 loai:
        1> cac giao dich binh thuong: 1 giao dich bao gom cac mat hang, moi mat hang co' 1 gia'
        2> cac giao dich thuoc loai lam dich vu: 1 giao dich bao gom gia cua mat hang + gia cua dich vu
        3> cac giao dich ban go'i hang: 1 giao dich chi bao gom gia cua goi hang, ko quan tam den gia cua dich vu
         */

//        //-- cac giao dich binh thuong
//        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
//        strNormalSaleTransQuery.append(""
//                + "SELECT a.sale_trans_detail_id, d.stock_type_id, d.name AS stock_type_name, b.stock_model_id, b.stock_model_code, b.NAME AS stock_model_name, "
//                + "       a.quantity, c.price, a.amount, (nvl(a.discount_amount,0) + nvl(a.discount_amount,0) * e.vat / 100) AS discount_amount, a.vat_amount, "
//                + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, "
//                + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
//        strNormalSaleTransQuery.append(""
//                + "FROM   sale_trans_detail a, "
//                + "       stock_model b, "
//                + "       price c, "
//                + "       stock_type d, "
//                + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.shop_id as staff_id, c.shop_code AS staff_code, c.name AS staff_name, "
//                + "                   a.sale_trans_date, a.sale_trans_type, DECODE (a.sale_trans_type, "
//                + "                                                                  1, 'Bán lẻ', "
//                + "                                                                  2, 'Bán đại lý', "
//                + "                                                                  3, 'Bán cộng tác viên', "
//                + "                                                                   4, 'Làm dịch vụ', "
//                + "                                                                  5, 'Bán hàng khuyến mại', "
//                + "                                                                  6, 'Xuất bán nội bộ', "
//                + "                                                                  7, 'Đấu nối/làm DV' "
//                + "                                                                ) AS sale_trans_type_name, "
//                + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, a.vat AS vat "
//                + "           FROM    sale_trans a, "
//                + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id "
//                + "                       FROM v_shop_tree "
//                + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ? AND channel_type_id <> 4) "
//                + "                       UNION ALL "
//                + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id "
//                + "                       FROM shop"
//                + "                       WHERE shop_id = ? "
//                + "                   ) b, "
//                + "                   shop c "
//                + "           WHERE   1 = 1 "
//                + "                   AND a.shop_id = c.shop_id"
//                + "                   AND c.parent_shop_id = b.shop_id"
//                + "                   AND a.sale_trans_type = 7 "
//                + "                   AND a.sale_trans_date >= trunc(?) "
//                + "                   AND a.sale_trans_date < trunc(?) "
//                + "       ) e ");
//        strNormalSaleTransQuery.append(""
//                + "WHERE  1 = 1 "
//                + "       AND b.stock_model_id = a.stock_model_id "
//                + "       AND c.price_id = a.price_id "
//                + "       AND b.stock_type_id = d.stock_type_id "
//                + "       AND e.sale_trans_id = a.sale_trans_id "
//                + "       AND a.sale_services_id IS NULL " + //--chi lay cac mat hang khong thuoc goi hang, co dieu kien nay la do trong bang sale_trans_detail luu ca chi tiet thong tin ve goi hang, vi du goi hang bao gom 2 mat hang -> luu thanh 3 ban ghi: 1 ban ghi ve goi hang, 2 ban ghi ve cac mat hang thuoc goi hang
//                "       AND a.sale_trans_date >= trunc(?) "
//                + "       AND a.sale_trans_date < trunc(?) ");
//
//        //co 6 tham so trong cau truy van doi voi cac giao dich binh htuong
//        listParameter.add(shopId);
//        listParameter.add(shopId);
//        listParameter.add(fromDate);
//        listParameter.add(afterToDateOneDay);
//        listParameter.add(fromDate);
//        listParameter.add(afterToDateOneDay);
//
        //-- lay cac giao dich thuoc loai lam dich vu
        StringBuffer strServicesSaleTransQuery = new StringBuffer("");
        strServicesSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, -1 AS stock_type_id, 'Làm dịch vụ' AS stock_type_name, b.sale_services_id AS stock_model_id, b.code AS stock_model_code, b.name AS stock_model_name, " + "       1 AS quantity, e.amount AS price, e.amount, e.discount_amount, e.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strServicesSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       sale_services b, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.shop_id as staff_id, c.shop_code AS staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, 'Đấu nối/làm DV' AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, " + "                   a.amount_tax AS amount, " + "                   (nvl(a.discount,0) + nvl(a.discount,0) * a.vat / 100) AS discount_amount, " + "                   a.tax AS vat_amount" + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ? AND channel_type_id <> 4) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   shop c " + "           WHERE   1 = 1 " + "                   AND a.shop_id = c.shop_id" + "                   AND c.parent_shop_id = b.shop_id" + "                   AND a.sale_trans_type = 7 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strServicesSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND a.sale_trans_id = e.sale_trans_id " + "       AND b.sale_services_id = a.sale_services_id " + "       AND b.sale_type = 1 " + "       AND a.stock_model_id IS NULL " + "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich lam dich vu
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //-- lay cac giao dich ban goi hang
        StringBuffer strPackageSaleTransQuery = new StringBuffer("");
        strPackageSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, -2 AS stock_type_id, 'Bán gói hàng' AS stock_type_name, b.sale_services_id AS stock_model_id, b.code AS stock_model_code, b.NAME AS stock_model_name, " + "       a.quantity, c.price, a.amount, (a.discount_amount + a.discount_amount * e.vat / 100) AS discount_amount, a.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strPackageSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       sale_services b, " + "       sale_services_price c, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.shop_id as staff_id, c.shop_code AS staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, DECODE (a.sale_trans_type, " + "                                                               1, 'Bán lẻ', " + "                                                               2, 'Bán đại lý', " + "                                                               3, 'Bán cộng tác viên', " + "                                                               4, 'Làm dịch vụ', " + "                                                               5, 'Bán hàng khuyến mại', " + "                                                               6, 'Xuất bán nội bộ', " + "                                                                  7, 'Đấu nối/làm DV' " + "                                                           ) AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, a.vat " + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ? AND channel_type_id <> 4) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   shop c " + "           WHERE   1 = 1 " + "                   AND a.shop_id = c.shop_id" + "                   AND c.parent_shop_id = b.shop_id" + "                   AND a.sale_trans_type = 7 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strPackageSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND a.sale_services_id = b.sale_services_id " + "       AND a.sale_services_price_id = c.sale_services_price_id " + "       AND a.sale_trans_id = e.sale_trans_id " + "       AND a.stock_model_id IS NULL " //--co dieu kien nay la do trong bang sale_trans_detail luu ca chi tiet thong tin ve goi hang, vi du goi hang bao gom 2 mat hang -> luu thanh 3 ban ghi: 1 ban ghi ve goi hang, 2 ban ghi ve cac mat hang thuoc goi hang
                + "       AND b.sale_type = 2 " + "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich lam dich vu
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //VSaleTransDetail = union all cua 3 loai tren
        StringBuffer strVSaleTransDetailQuery = new StringBuffer("");
//        strVSaleTransDetailQuery.append(strNormalSaleTransQuery);
//        strVSaleTransDetailQuery.append(" UNION ALL ");
        strVSaleTransDetailQuery.append(strServicesSaleTransQuery);
        strVSaleTransDetailQuery.append(" UNION ALL ");
        strVSaleTransDetailQuery.append(strPackageSaleTransQuery);

        StringBuffer strQuery = new StringBuffer("");
        strQuery.append("" + "SELECT a.staff_Name staffName, a.shop_name shopName, a.sale_trans_type_name saleTransTypeName, a.stock_type_name stockTypeName, a.stock_model_code stockModelCode, a.stock_model_name stockModelName, " + "       sum(a.quantity) quantity, a.price price, sum(a.amount) amount, sum(a.discount_amount) discountAmount, sum(a.vat_amount) vatAmount ");
        strQuery.append("" + "FROM ").append("( ").append(strVSaleTransDetailQuery).append(" ) a ");
        strQuery.append("" + "WHERE 1 = 1 ");

        if (staffId != null && staffId.compareTo(0L) > 0) {
            strQuery.append("and a.staff_id = ? ");
            listParameter.add(staffId);
        }

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.stock_type_id = ? ");
            listParameter.add(stockTypeId);
        }

//        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
//            strQuery.append("and a.stock_model_id = ? ");
//            listParameter.add(stockModelId);
//        }

        /**
         * AnDV 12/2/10
         * Them bao cao mat hang nam trong cac dich vu ban hang
         * tuy theo trang thai checkBox reportStockModelInSaleService
         */
        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
            Boolean reportStockModelInSaleService = this.reportRevenueForm.getReportStockModelInSaleService();
            if (reportStockModelInSaleService != null && reportStockModelInSaleService.equals(true)) {
                StringBuffer stringBuffer = new StringBuffer("");
                List<BigDecimal> lstSaleServiceId = new ArrayList<BigDecimal>();
                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
                stringBuffer.append(stockModelId.toString());
                if (lstSaleServiceId != null && lstSaleServiceId.size() > 0) {
                    for (int i = 0; i < lstSaleServiceId.size(); i++) {
                        stringBuffer.append(",");
                        stringBuffer.append(lstSaleServiceId.get(i).toString());

                    }
                }
                strQuery.append("and a.stock_model_id in (" + stringBuffer.toString() + ") ");
//                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
            } else {
                strQuery.append("and a.stock_model_id = ? ");
                listParameter.add(stockModelId);
            }
        }
        //End: AnDV
        if (saleTransType != null && !saleTransType.trim().equals("")) {
            strQuery.append("and a.sale_trans_type = ? ");
            listParameter.add(saleTransType);
        }

        if (channelTypeId != null && channelTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.channel_type_id = ? ");
            listParameter.add(channelTypeId);
        }

        if (reasonId != null && reasonId.compareTo(0L) > 0) {
            strQuery.append("and a.reason_id = ? ");
            listParameter.add(reasonId);
        }

        if (telecomServiceId != null && telecomServiceId.compareTo(0L) > 0) {
            strQuery.append("and a.telecom_service_id = ? ");
            listParameter.add(telecomServiceId);
        }

        strQuery.append("and (a.sale_trans_status = ? "); //them de su dung toan tu or
        listParameter.add("-1");

        if (billedSaleTrans != null && billedSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_BILLED);
        }

        if (notBilledSaleTrans != null && notBilledSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        }

        if (cancelSaleTrans != null && cancelSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_CANCEL);
        }

        strQuery.append(") "); //

//        strQuery.append("and (a.sale_trans_type = ? "); //them de su dung toan tu or
//        listParameter.add("-1");
//
//        if (hasMoney != null && hasMoney.equals(true)) {
//            //cac giao dich co thu tien (bao gom ban le, ban dai ly, ban CTV, DB, lam giao dich CM)
//            strQuery.append("or a.sale_trans_type = ? or a.sale_trans_type = ? or a.sale_trans_type = ? or a.sale_trans_type = ? ");
//            listParameter.add(Constant.SALE_TRANS_TYPE_RETAIL);
//            listParameter.add(Constant.SALE_TRANS_TYPE_AGENT);
//            listParameter.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
//            listParameter.add(Constant.SALE_TRANS_TYPE_SERVICE);
//        }
//        if (noMoney != null && noMoney.equals(true)) {
//            //cac giao dich khong thu tien (bao gom ban khuyen mai va ban hang noi bo)
//            strQuery.append("or a.sale_trans_type = ? or a.sale_trans_type = ? ");
//            listParameter.add(Constant.SALE_TRANS_TYPE_PROMOTION);
//            listParameter.add(Constant.SALE_TRANS_TYPE_INTERNAL);
//        }
//
//        strQuery.append(") "); //

        strQuery.append("group by a.staff_Name, a.shop_name, a.sale_trans_type_name, a.stock_type_name, a.stock_model_code, a.stock_model_name, a.price ");
        strQuery.append("order by a.stock_model_code");

        System.out.println("===TUANPV: Cau lenh SQL: " + strQuery.toString());

        Query query = getSession().createSQLQuery(strQuery.toString()).addScalar("staffName", Hibernate.STRING).addScalar("shopName", Hibernate.STRING).addScalar("saleTransTypeName", Hibernate.STRING).addScalar("stockTypeName", Hibernate.STRING).addScalar("stockModelCode", Hibernate.STRING).addScalar("stockModelName", Hibernate.STRING).addScalar("quantity", Hibernate.LONG).addScalar("price", Hibernate.LONG).addScalar("amount", Hibernate.DOUBLE).addScalar("discountAmount", Hibernate.LONG).addScalar("vatAmount", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(VSaleTransDetail.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        listVSaleTransDetail = query.list();
        return listVSaleTransDetail;

    }

    private List<VSaleTransDetail> getListGeneralCTVDB() throws Exception {

        List<VSaleTransDetail> listVSaleTransDetail = new ArrayList<VSaleTransDetail>();

        Long shopId = this.reportRevenueForm.getShopId();
        Long stockTypeId = this.reportRevenueForm.getStockTypeId();
        Long stockModelId = this.reportRevenueForm.getStockModelId();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        String saleTransType = this.reportRevenueForm.getSaleTransType();
        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
        Long reasonId = this.reportRevenueForm.getReasonId();
        Long telecomServiceId = this.reportRevenueForm.getTelecomServiceId();
        Boolean billedSaleTrans = this.reportRevenueForm.getBilledSaleTrans();
        Boolean notBilledSaleTrans = this.reportRevenueForm.getNotBilledSaleTrans();
        Boolean cancelSaleTrans = this.reportRevenueForm.getCancelSaleTrans();
        Long groupType = this.reportRevenueForm.getGroupType();
        Boolean hasMoney = this.reportRevenueForm.getHasMoney();
        Boolean noMoney = this.reportRevenueForm.getNoMoney();
        Long groupBySaleTransType = this.reportRevenueForm.getGroupBySaleTransType();
        String ownerCode = this.reportRevenueForm.getOwnerCode();
        Long staffId = getStaffId(ownerCode);
        String ownerManagementCode = this.reportRevenueForm.getStaffCode();
        Long staffIdManamgement = getStaffId(ownerManagementCode);

        List listParameter = new ArrayList();

        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }

        /*
        lay thong tin chi tiet cua 1 giao dich, phuc vu viec xuat bao cao doanh thu
        1 giao dich co the bao gom 3 loai:
        1> cac giao dich binh thuong: 1 giao dich bao gom cac mat hang, moi mat hang co' 1 gia'
        2> cac giao dich thuoc loai lam dich vu: 1 giao dich bao gom gia cua mat hang + gia cua dich vu
        3> cac giao dich ban go'i hang: 1 giao dich chi bao gom gia cua goi hang, ko quan tam den gia cua dich vu
         */

//        //-- cac giao dich binh thuong
//        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
//        strNormalSaleTransQuery.append(""
//                + "SELECT a.sale_trans_detail_id, d.stock_type_id, d.name AS stock_type_name, b.stock_model_id, b.stock_model_code, b.NAME AS stock_model_name, "
//                + "       a.quantity, c.price, a.amount, (nvl(a.discount_amount,0) + nvl(a.discount_amount,0) * e.vat / 100) AS discount_amount, a.vat_amount, "
//                + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, "
//                + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
//        strNormalSaleTransQuery.append(""
//                + "FROM   sale_trans_detail a, "
//                + "       stock_model b, "
//                + "       price c, "
//                + "       stock_type d, "
//                + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.staff_id, c.staff_code, c.name AS staff_name, "
//                + "                   a.sale_trans_date, a.sale_trans_type, DECODE (a.sale_trans_type, "
//                + "                                                                  1, 'Bán lẻ', "
//                + "                                                                  2, 'Bán đại lý', "
//                + "                                                                  3, 'Bán cộng tác viên', "
//                + "                                                                   4, 'Làm dịch vụ', "
//                + "                                                                  5, 'Bán hàng khuyến mại', "
//                + "                                                                  6, 'Xuất bán nội bộ', "
//                + "                                                                  7, 'Đấu nối/làm DV' "
//                + "                                                                ) AS sale_trans_type_name, "
//                + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, a.vat AS vat "
//                + "           FROM    sale_trans a, "
//                + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id "
//                + "                       FROM v_shop_tree "
//                + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ?) "
//                + "                       UNION ALL "
//                + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id "
//                + "                       FROM shop"
//                + "                       WHERE shop_id = ? "
//                + "                   ) b, "
//                + "                   staff c "
//                + "           WHERE   1 = 1 "
//                + "                   AND b.shop_id = a.shop_id "
//                + "                   AND c.staff_id = a.staff_id "
//                + "                   AND a.sale_trans_type = 7 "
//                + "                   AND a.sale_trans_date >= trunc(?) "
//                + "                   AND a.sale_trans_date < trunc(?) "
//                + "       ) e ");
//        strNormalSaleTransQuery.append(""
//                + "WHERE  1 = 1 "
//                + "       AND b.stock_model_id = a.stock_model_id "
//                + "       AND c.price_id = a.price_id "
//                + "       AND b.stock_type_id = d.stock_type_id "
//                + "       AND e.sale_trans_id = a.sale_trans_id "
//                + "       AND a.sale_services_id IS NULL " + //--chi lay cac mat hang khong thuoc goi hang, co dieu kien nay la do trong bang sale_trans_detail luu ca chi tiet thong tin ve goi hang, vi du goi hang bao gom 2 mat hang -> luu thanh 3 ban ghi: 1 ban ghi ve goi hang, 2 ban ghi ve cac mat hang thuoc goi hang
//                "       AND a.sale_trans_date >= trunc(?) "
//                + "       AND a.sale_trans_date < trunc(?) ");
//
//        //co 6 tham so trong cau truy van doi voi cac giao dich binh htuong
//        listParameter.add(shopId);
//        listParameter.add(shopId);
//        listParameter.add(fromDate);
//        listParameter.add(afterToDateOneDay);
//        listParameter.add(fromDate);
//        listParameter.add(afterToDateOneDay);

        //-- lay cac giao dich thuoc loai lam dich vu
        StringBuffer strServicesSaleTransQuery = new StringBuffer("");
        strServicesSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, -1 AS stock_type_id, 'Làm dịch vụ' AS stock_type_name, b.sale_services_id AS stock_model_id, b.code AS stock_model_code, b.name AS stock_model_name, " + "       1 AS quantity, e.amount AS price, e.amount, e.discount_amount, e.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strServicesSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       sale_services b, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.staff_id, c.staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, 'Đấu nối/làm DV' AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, " + "                   a.amount_tax AS amount, " + "                   (nvl(a.discount,0) + nvl(a.discount,0) * a.vat / 100) AS discount_amount, " + "                   a.tax AS vat_amount" + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ?) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   staff c " + "           WHERE   1 = 1 " + "                   AND a.shop_id = b.shop_id " + "                   AND a.staff_id = c.staff_id " + "                   AND a.sale_trans_type = 7 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strServicesSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND a.sale_trans_id = e.sale_trans_id " + "       AND b.sale_services_id = a.sale_services_id " + "       AND b.sale_type = 1 " + "       AND a.stock_model_id IS NULL " + "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich lam dich vu
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //-- lay cac giao dich ban goi hang
        StringBuffer strPackageSaleTransQuery = new StringBuffer("");
        strPackageSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, -2 AS stock_type_id, 'Bán gói hàng' AS stock_type_name, b.sale_services_id AS stock_model_id, b.code AS stock_model_code, b.NAME AS stock_model_name, " + "       a.quantity, c.price, a.amount, (a.discount_amount + a.discount_amount * e.vat / 100) AS discount_amount, a.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strPackageSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       sale_services b, " + "       sale_services_price c, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.staff_id, c.staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, DECODE (a.sale_trans_type, " + "                                                               1, 'Bán lẻ', " + "                                                               2, 'Bán đại lý', " + "                                                               3, 'Bán cộng tác viên', " + "                                                               4, 'Làm dịch vụ', " + "                                                               5, 'Bán hàng khuyến mại', " + "                                                               6, 'Xuất bán nội bộ', " + "                                                                  7, 'Đấu nối/làm DV' " + "                                                           ) AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, a.vat " + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ?) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   staff c " + "           WHERE   1 = 1 " + "                   AND a.shop_id = b.shop_id " + "                   AND a.staff_id = c.staff_id " + "                   AND a.sale_trans_type = 7 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strPackageSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND a.sale_services_id = b.sale_services_id " + "       AND a.sale_services_price_id = c.sale_services_price_id " + "       AND a.sale_trans_id = e.sale_trans_id " + "       AND a.stock_model_id IS NULL " //--co dieu kien nay la do trong bang sale_trans_detail luu ca chi tiet thong tin ve goi hang, vi du goi hang bao gom 2 mat hang -> luu thanh 3 ban ghi: 1 ban ghi ve goi hang, 2 ban ghi ve cac mat hang thuoc goi hang
                + "       AND b.sale_type = 2 " + "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich lam dich vu
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //VSaleTransDetail = union all cua 3 loai tren
        StringBuffer strVSaleTransDetailQuery = new StringBuffer("");
//        strVSaleTransDetailQuery.append(strNormalSaleTransQuery);
//        strVSaleTransDetailQuery.append(" UNION ALL ");
        strVSaleTransDetailQuery.append(strServicesSaleTransQuery);
        strVSaleTransDetailQuery.append(" UNION ALL ");
        strVSaleTransDetailQuery.append(strPackageSaleTransQuery);

        StringBuffer strQuery = new StringBuffer("");
        strQuery.append("" + "SELECT  a.sale_trans_type_name saleTransTypeName, a.stock_type_name stockTypeName, a.stock_model_code stockModelCode, a.stock_model_name stockModelName, " + "       sum(a.quantity) quantity, a.price price, sum(a.amount) amount, sum(a.discount_amount) discountAmount, sum(a.vat_amount) vatAmount ");
        strQuery.append("" + "FROM ").append("( ").append(strVSaleTransDetailQuery).append(" ) a, staff b ");
        strQuery.append("" + "WHERE 1 = 1 ");
        strQuery.append("and a.staff_id = b.staff_id ");
        if (staffIdManamgement != null && staffIdManamgement.compareTo(0L) > 0) {
            strQuery.append("and b.staff_Owner_Id = ? ");
            listParameter.add(staffIdManamgement);
        }

        if (staffId != null && staffId.compareTo(0L) > 0) {
            strQuery.append("and a.staff_id = ? ");
            listParameter.add(staffId);
        }

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.stock_type_id = ? ");
            listParameter.add(stockTypeId);
        }

//        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
//            strQuery.append("and a.stock_model_id = ? ");
//            listParameter.add(stockModelId);
//        }
        /**
         * AnDV 12/2/10
         * Them bao cao mat hang nam trong cac dich vu ban hang
         * tuy theo trang thai checkBox reportStockModelInSaleService
         */
        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
            Boolean reportStockModelInSaleService = this.reportRevenueForm.getReportStockModelInSaleService();

            if (reportStockModelInSaleService != null && reportStockModelInSaleService.equals(true)) {
                StringBuffer stringBuffer = new StringBuffer("");
                List<BigDecimal> lstSaleServiceId = new ArrayList<BigDecimal>();
                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
                stringBuffer.append(stockModelId.toString());
                if (lstSaleServiceId != null && lstSaleServiceId.size() > 0) {
                    for (int i = 0; i < lstSaleServiceId.size(); i++) {
                        stringBuffer.append(",");
                        stringBuffer.append(lstSaleServiceId.get(i).toString());

                    }
                }
                strQuery.append("and a.stock_model_id in (" + stringBuffer.toString() + ") ");
//                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
            } else {
                strQuery.append("and a.stock_model_id = ? ");
                listParameter.add(stockModelId);
            }
        }
        //End:Andv

        if (saleTransType != null && !saleTransType.trim().equals("")) {
            strQuery.append("and a.sale_trans_type = ? ");
            listParameter.add(saleTransType);
        }

        if (channelTypeId != null && channelTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.channel_type_id = ? ");
            listParameter.add(channelTypeId);
        }

        if (reasonId != null && reasonId.compareTo(0L) > 0) {
            strQuery.append("and a.reason_id = ? ");
            listParameter.add(reasonId);
        }

        if (telecomServiceId != null && telecomServiceId.compareTo(0L) > 0) {
            strQuery.append("and a.telecom_service_id = ? ");
            listParameter.add(telecomServiceId);
        }

        strQuery.append("and (a.sale_trans_status = ? "); //them de su dung toan tu or
        listParameter.add("-1");

        if (billedSaleTrans != null && billedSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_BILLED);
        }

        if (notBilledSaleTrans != null && notBilledSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        }

        if (cancelSaleTrans != null && cancelSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_CANCEL);
        }

        strQuery.append(") "); //

//        strQuery.append("and (a.sale_trans_type = ? "); //them de su dung toan tu or
//        listParameter.add("-1");
//
//        if (hasMoney != null && hasMoney.equals(true)) {
//            //cac giao dich co thu tien (bao gom ban le, ban dai ly, ban CTV, DB, lam giao dich CM)
//            strQuery.append("or a.sale_trans_type = ? or a.sale_trans_type = ? or a.sale_trans_type = ? or a.sale_trans_type = ? ");
//            listParameter.add(Constant.SALE_TRANS_TYPE_RETAIL);
//            listParameter.add(Constant.SALE_TRANS_TYPE_AGENT);
//            listParameter.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
//            listParameter.add(Constant.SALE_TRANS_TYPE_SERVICE);
//        }
//        if (noMoney != null && noMoney.equals(true)) {
//            //cac giao dich khong thu tien (bao gom ban khuyen mai va ban hang noi bo)
//            strQuery.append("or a.sale_trans_type = ? or a.sale_trans_type = ? ");
//            listParameter.add(Constant.SALE_TRANS_TYPE_PROMOTION);
//            listParameter.add(Constant.SALE_TRANS_TYPE_INTERNAL);
//        }
//
//        strQuery.append(") "); //

        strQuery.append("group by a.sale_trans_type_name, a.stock_type_name, a.stock_model_code, a.stock_model_name, a.price ");
        strQuery.append("order by a.stock_model_code");

        System.out.println("===TUANPV: Cau lenh SQL: " + strQuery.toString());

        Query query = getSession().createSQLQuery(strQuery.toString()).addScalar("saleTransTypeName", Hibernate.STRING).addScalar("stockTypeName", Hibernate.STRING).addScalar("stockModelCode", Hibernate.STRING).addScalar("stockModelName", Hibernate.STRING).addScalar("quantity", Hibernate.LONG).addScalar("price", Hibernate.LONG).addScalar("amount", Hibernate.DOUBLE).addScalar("discountAmount", Hibernate.LONG).addScalar("vatAmount", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(VSaleTransDetail.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        listVSaleTransDetail = query.list();
        return listVSaleTransDetail;

    }

    private List<VSaleTransDetail> getListGeneralAgent() throws Exception {

        List<VSaleTransDetail> listVSaleTransDetail = new ArrayList<VSaleTransDetail>();

        Long shopId = this.reportRevenueForm.getShopId();
        Long stockTypeId = this.reportRevenueForm.getStockTypeId();
        Long stockModelId = this.reportRevenueForm.getStockModelId();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        String saleTransType = this.reportRevenueForm.getSaleTransType();
        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
        Long reasonId = this.reportRevenueForm.getReasonId();
        Long telecomServiceId = this.reportRevenueForm.getTelecomServiceId();
        Boolean billedSaleTrans = this.reportRevenueForm.getBilledSaleTrans();
        Boolean notBilledSaleTrans = this.reportRevenueForm.getNotBilledSaleTrans();
        Boolean cancelSaleTrans = this.reportRevenueForm.getCancelSaleTrans();
        Long groupType = this.reportRevenueForm.getGroupType();
        Boolean hasMoney = this.reportRevenueForm.getHasMoney();
        Boolean noMoney = this.reportRevenueForm.getNoMoney();
        Long groupBySaleTransType = this.reportRevenueForm.getGroupBySaleTransType();
        String ownerCode = this.reportRevenueForm.getOwnerCode();
        Long staffId = getShopId(ownerCode);

        List listParameter = new ArrayList();

        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }

        /*
        lay thong tin chi tiet cua 1 giao dich, phuc vu viec xuat bao cao doanh thu
        1 giao dich co the bao gom 3 loai:
        1> cac giao dich binh thuong: 1 giao dich bao gom cac mat hang, moi mat hang co' 1 gia'
        2> cac giao dich thuoc loai lam dich vu: 1 giao dich bao gom gia cua mat hang + gia cua dich vu
        3> cac giao dich ban go'i hang: 1 giao dich chi bao gom gia cua goi hang, ko quan tam den gia cua dich vu
         */

//        //-- cac giao dich binh thuong
//        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
//        strNormalSaleTransQuery.append(""
//                + "SELECT a.sale_trans_detail_id, d.stock_type_id, d.name AS stock_type_name, b.stock_model_id, b.stock_model_code, b.NAME AS stock_model_name, "
//                + "       a.quantity, c.price, a.amount, (nvl(a.discount_amount,0) + nvl(a.discount_amount,0) * e.vat / 100) AS discount_amount, a.vat_amount, "
//                + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, "
//                + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
//        strNormalSaleTransQuery.append(""
//                + "FROM   sale_trans_detail a, "
//                + "       stock_model b, "
//                + "       price c, "
//                + "       stock_type d, "
//                + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.shop_id as staff_id, c.shop_code AS staff_code, c.name AS staff_name, "
//                + "                   a.sale_trans_date, a.sale_trans_type, DECODE (a.sale_trans_type, "
//                + "                                                                  1, 'Bán lẻ', "
//                + "                                                                  2, 'Bán đại lý', "
//                + "                                                                  3, 'Bán cộng tác viên', "
//                + "                                                                   4, 'Làm dịch vụ', "
//                + "                                                                  5, 'Bán hàng khuyến mại', "
//                + "                                                                  6, 'Xuất bán nội bộ', "
//                + "                                                                  7, 'Đấu nối/làm DV' "
//                + "                                                                ) AS sale_trans_type_name, "
//                + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, a.vat AS vat "
//                + "           FROM    sale_trans a, "
//                + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id "
//                + "                       FROM v_shop_tree "
//                + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ? AND channel_type_id <> 4) "
//                + "                       UNION ALL "
//                + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id "
//                + "                       FROM shop"
//                + "                       WHERE shop_id = ? "
//                + "                   ) b, "
//                + "                   shop c "
//                + "           WHERE   1 = 1 "
//                + "                   AND a.shop_id = c.shop_id"
//                + "                   AND c.parent_shop_id = b.shop_id"
//                + "                   AND a.sale_trans_type = 7 "
//                + "                   AND a.sale_trans_date >= trunc(?) "
//                + "                   AND a.sale_trans_date < trunc(?) "
//                + "       ) e ");
//        strNormalSaleTransQuery.append(""
//                + "WHERE  1 = 1 "
//                + "       AND b.stock_model_id = a.stock_model_id "
//                + "       AND c.price_id = a.price_id "
//                + "       AND b.stock_type_id = d.stock_type_id "
//                + "       AND e.sale_trans_id = a.sale_trans_id "
//                + "       AND a.sale_services_id IS NULL " + //--chi lay cac mat hang khong thuoc goi hang, co dieu kien nay la do trong bang sale_trans_detail luu ca chi tiet thong tin ve goi hang, vi du goi hang bao gom 2 mat hang -> luu thanh 3 ban ghi: 1 ban ghi ve goi hang, 2 ban ghi ve cac mat hang thuoc goi hang
//                "       AND a.sale_trans_date >= trunc(?) "
//                + "       AND a.sale_trans_date < trunc(?) ");
//
//        //co 6 tham so trong cau truy van doi voi cac giao dich binh htuong
//        listParameter.add(shopId);
//        listParameter.add(shopId);
//        listParameter.add(fromDate);
//        listParameter.add(afterToDateOneDay);
//        listParameter.add(fromDate);
//        listParameter.add(afterToDateOneDay);

        //-- lay cac giao dich thuoc loai lam dich vu
        StringBuffer strServicesSaleTransQuery = new StringBuffer("");
        strServicesSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, -1 AS stock_type_id, 'Làm dịch vụ' AS stock_type_name, b.sale_services_id AS stock_model_id, b.code AS stock_model_code, b.name AS stock_model_name, " + "       1 AS quantity, e.amount AS price, e.amount, e.discount_amount, e.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strServicesSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       sale_services b, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.shop_id as staff_id, c.shop_code AS staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, 'Đấu nối/làm DV' AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, " + "                   a.amount_tax AS amount, " + "                   (nvl(a.discount,0) + nvl(a.discount,0) * a.vat / 100) AS discount_amount, " + "                   a.tax AS vat_amount" + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ? AND channel_type_id <> 4) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   shop c " + "           WHERE   1 = 1 " + "                   AND a.shop_id = c.shop_id" + "                   AND c.parent_shop_id = b.shop_id" + "                   AND a.sale_trans_type = 7 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strServicesSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND a.sale_trans_id = e.sale_trans_id " + "       AND b.sale_services_id = a.sale_services_id " + "       AND b.sale_type = 1 " + "       AND a.stock_model_id IS NULL " + "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich lam dich vu
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //-- lay cac giao dich ban goi hang
        StringBuffer strPackageSaleTransQuery = new StringBuffer("");
        strPackageSaleTransQuery.append("" + "SELECT a.sale_trans_detail_id, -2 AS stock_type_id, 'Bán gói hàng' AS stock_type_name, b.sale_services_id AS stock_model_id, b.code AS stock_model_code, b.NAME AS stock_model_name, " + "       a.quantity, c.price, a.amount, (a.discount_amount + a.discount_amount * e.vat / 100) AS discount_amount, a.vat_amount, " + "       e.sale_trans_id, e.shop_id, e.shop_code, e.shop_name, e.channel_type_id, e.staff_id, e.staff_code, e.staff_name, " + "       e.sale_trans_date, e.sale_trans_type, e.sale_trans_type_name, e.reason_id, e.telecom_service_id, e.sale_trans_status ");
        strPackageSaleTransQuery.append("" + "FROM   sale_trans_detail a, " + "       sale_services b, " + "       sale_services_price c, " + "       (SELECT     a.sale_trans_id, b.root_id AS shop_id, b.root_code AS shop_code, b.root_name AS shop_name, b.channel_type_id, a.shop_id as staff_id, c.shop_code AS staff_code, c.name AS staff_name, " + "                   a.sale_trans_date, a.sale_trans_type, DECODE (a.sale_trans_type, " + "                                                               1, 'Bán lẻ', " + "                                                               2, 'Bán đại lý', " + "                                                               3, 'Bán cộng tác viên', " + "                                                               4, 'Làm dịch vụ', " + "                                                               5, 'Bán hàng khuyến mại', " + "                                                               6, 'Xuất bán nội bộ', " + "                                                                  7, 'Đấu nối/làm DV' " + "                                                           ) AS sale_trans_type_name, " + "                   a.reason_id, a.telecom_service_id, a.status AS sale_trans_status, a.vat " + "           FROM    sale_trans a, " + "                   (   SELECT root_id, root_code, root_name, channel_type_id, shop_id " + "                       FROM v_shop_tree " + "                       WHERE root_id in (SELECT shop_id FROM shop WHERE parent_shop_id = ? AND channel_type_id <> 4) " + "                       UNION ALL " + "                       SELECT shop_id AS root_id, shop_code as root_code, name as root_name, channel_type_id, shop_id " + "                       FROM shop" + "                       WHERE shop_id = ? " + "                   ) b, " + "                   shop c " + "           WHERE   1 = 1 " + "                   AND a.shop_id = c.shop_id" + "                   AND c.parent_shop_id = b.shop_id" + "                   AND a.sale_trans_type = 7 " + "                   AND a.sale_trans_date >= trunc(?) " + "                   AND a.sale_trans_date < trunc(?) " + "       ) e ");
        strPackageSaleTransQuery.append("" + "WHERE  1 = 1 " + "       AND a.sale_services_id = b.sale_services_id " + "       AND a.sale_services_price_id = c.sale_services_price_id " + "       AND a.sale_trans_id = e.sale_trans_id " + "       AND a.stock_model_id IS NULL " //--co dieu kien nay la do trong bang sale_trans_detail luu ca chi tiet thong tin ve goi hang, vi du goi hang bao gom 2 mat hang -> luu thanh 3 ban ghi: 1 ban ghi ve goi hang, 2 ban ghi ve cac mat hang thuoc goi hang
                + "       AND b.sale_type = 2 " + "       AND a.sale_trans_date >= trunc(?) " + "       AND a.sale_trans_date < trunc(?) ");

        //co 6 tham so trong cau truy van doi voi cac giao dich lam dich vu
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        //VSaleTransDetail = union all cua 3 loai tren
        StringBuffer strVSaleTransDetailQuery = new StringBuffer("");
//        strVSaleTransDetailQuery.append(strNormalSaleTransQuery);
//        strVSaleTransDetailQuery.append(" UNION ALL ");
        strVSaleTransDetailQuery.append(strServicesSaleTransQuery);
        strVSaleTransDetailQuery.append(" UNION ALL ");
        strVSaleTransDetailQuery.append(strPackageSaleTransQuery);

        StringBuffer strQuery = new StringBuffer("");
        strQuery.append("" + "SELECT  a.sale_trans_type_name saleTransTypeName, a.stock_type_name stockTypeName, a.stock_model_code stockModelCode, a.stock_model_name stockModelName, " + "       sum(a.quantity) quantity, a.price price, sum(a.amount) amount, sum(a.discount_amount) discountAmount, sum(a.vat_amount) vatAmount ");
        strQuery.append("" + "FROM ").append("( ").append(strVSaleTransDetailQuery).append(" ) a ");
        strQuery.append("" + "WHERE 1 = 1 ");

        if (staffId != null && staffId.compareTo(0L) > 0) {
            strQuery.append("and a.staff_id = ? ");
            listParameter.add(staffId);
        }

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.stock_type_id = ? ");
            listParameter.add(stockTypeId);
        }

//        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
//            strQuery.append("and a.stock_model_id = ? ");
//            listParameter.add(stockModelId);
//        }
        /**
         * AnDV 12/2/10
         * Them bao cao mat hang nam trong cac dich vu ban hang
         * tuy theo trang thai checkBox reportStockModelInSaleService
         */
        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
            Boolean reportStockModelInSaleService = this.reportRevenueForm.getReportStockModelInSaleService();

            if (reportStockModelInSaleService != null && reportStockModelInSaleService.equals(true)) {
                StringBuffer stringBuffer = new StringBuffer("");
                List<BigDecimal> lstSaleServiceId = new ArrayList<BigDecimal>();
                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
                stringBuffer.append(stockModelId.toString());
                if (lstSaleServiceId != null && lstSaleServiceId.size() > 0) {
                    for (int i = 0; i < lstSaleServiceId.size(); i++) {
                        stringBuffer.append(",");
                        stringBuffer.append(lstSaleServiceId.get(i).toString());

                    }
                }
                strQuery.append("and a.stock_model_id in (" + stringBuffer.toString() + ") ");
//                lstSaleServiceId = getListSaleServicesIdByStockModelId(stockModelId);
            } else {
                strQuery.append("and a.stock_model_id = ? ");
                listParameter.add(stockModelId);
            }
        }
        //End:Andv

        if (saleTransType != null && !saleTransType.trim().equals("")) {
            strQuery.append("and a.sale_trans_type = ? ");
            listParameter.add(saleTransType);
        }

        if (channelTypeId != null && channelTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.channel_type_id = ? ");
            listParameter.add(channelTypeId);
        }

        if (reasonId != null && reasonId.compareTo(0L) > 0) {
            strQuery.append("and a.reason_id = ? ");
            listParameter.add(reasonId);
        }

        if (telecomServiceId != null && telecomServiceId.compareTo(0L) > 0) {
            strQuery.append("and a.telecom_service_id = ? ");
            listParameter.add(telecomServiceId);
        }

        strQuery.append("and (a.sale_trans_status = ? "); //them de su dung toan tu or
        listParameter.add("-1");

        if (billedSaleTrans != null && billedSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_BILLED);
        }

        if (notBilledSaleTrans != null && notBilledSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        }

        if (cancelSaleTrans != null && cancelSaleTrans) {
            strQuery.append("or a.sale_trans_status = ? ");
            listParameter.add(Constant.SALE_TRANS_STATUS_CANCEL);
        }

        strQuery.append(") "); //

//        strQuery.append("and (a.sale_trans_type = ? "); //them de su dung toan tu or
//        listParameter.add("-1");
//
//        if (hasMoney != null && hasMoney.equals(true)) {
//            //cac giao dich co thu tien (bao gom ban le, ban dai ly, ban CTV, DB, lam giao dich CM)
//            strQuery.append("or a.sale_trans_type = ? or a.sale_trans_type = ? or a.sale_trans_type = ? or a.sale_trans_type = ? ");
//            listParameter.add(Constant.SALE_TRANS_TYPE_RETAIL);
//            listParameter.add(Constant.SALE_TRANS_TYPE_AGENT);
//            listParameter.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
//            listParameter.add(Constant.SALE_TRANS_TYPE_SERVICE);
//        }
//        if (noMoney != null && noMoney.equals(true)) {
//            //cac giao dich khong thu tien (bao gom ban khuyen mai va ban hang noi bo)
//            strQuery.append("or a.sale_trans_type = ? or a.sale_trans_type = ? ");
//            listParameter.add(Constant.SALE_TRANS_TYPE_PROMOTION);
//            listParameter.add(Constant.SALE_TRANS_TYPE_INTERNAL);
//        }
//
//        strQuery.append(") "); //

        strQuery.append("group by a.shop_name, a.sale_trans_type_name, a.stock_type_name, a.stock_model_code, a.stock_model_name, a.price ");
        strQuery.append("order by a.stock_model_code");

        System.out.println("===TUANPV: Cau lenh SQL: " + strQuery.toString());

        Query query = getSession().createSQLQuery(strQuery.toString()).addScalar("saleTransTypeName", Hibernate.STRING).addScalar("stockTypeName", Hibernate.STRING).addScalar("stockModelCode", Hibernate.STRING).addScalar("stockModelName", Hibernate.STRING).addScalar("quantity", Hibernate.LONG).addScalar("price", Hibernate.LONG).addScalar("amount", Hibernate.DOUBLE).addScalar("discountAmount", Hibernate.LONG).addScalar("vatAmount", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(VSaleTransDetail.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        listVSaleTransDetail = query.list();
        return listVSaleTransDetail;

    }

    public Long getStaffId(String staffCode) throws Exception {
        if (staffCode == null || staffCode.equals("")) {
            return 0L;
        }
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff.getStaffId();
        }
        return 0L;

    }

    public Long getShopId(String shopCode) throws Exception {
        if (shopCode == null || shopCode.equals("")) {
            return 0L;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop.getShopId();
        }
        return 0L;
    }

    public List<ImSearchBean> getListCTVDB(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien hoac dai ly
        String shopCode;
        String staffManageCode;
        String reportType;

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");
            int index1 = otherParam.indexOf(";", index + 1);
            if (index == 0 || index1 == 0) {
                return listImSearchBean;
            } else {
                shopCode = otherParam.substring(0, index).toLowerCase();
                staffManageCode = otherParam.substring(index + 1, index1).toLowerCase();
                reportType = otherParam.substring(index1 + 1, otherParam.length()).toLowerCase();
            }
            if (reportType.equals("2")) {
                if (staffManageCode == null || staffManageCode.equals("")) {
                    return listImSearchBean;
                }
                List listParameter1 = new ArrayList();
                StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
                strQuery1.append("from Staff a ");
                strQuery1.append("where 1 = 1 and a.status = 1 ");
                strQuery1.append("and a.staffOwnerId is not null ");
                strQuery1.append("and a.shopId = ? ");
                listParameter1.add(getShopId(shopCode));
                if (staffManageCode != null && !staffManageCode.equals("")) {
                    strQuery1.append("and a.staffOwnerId = ? ");
                    listParameter1.add(getStaffId(staffManageCode));
                }
                if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                    strQuery1.append("and lower(a.staffCode) like ? ");
                    listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
                }

                if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                    strQuery1.append("and lower(a.name) like ? ");
                    listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                }

                strQuery1.append("and rownum < ? ");
                listParameter1.add(300L);

                strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

                Query query1 = getSession().createQuery(strQuery1.toString());
                for (int i = 0; i < listParameter1.size(); i++) {
                    query1.setParameter(i, listParameter1.get(i));
                }

                List<ImSearchBean> tmpList1 = query1.list();
                if (tmpList1 != null && tmpList1.size() > 0) {
                    listImSearchBean.addAll(tmpList1);
                }

            } else {
                if (reportType.equals("3")) {
                    List listParameter1 = new ArrayList();
                    StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
                    strQuery1.append("from Shop a ");
                    strQuery1.append("where 1 = 1 and a.status = 1 ");
                    strQuery1.append("and a.channelTypeId = ? ");
                    listParameter1.add(4L);
                    strQuery1.append("and a.parentShopId =? ");
                    listParameter1.add(getShopId(shopCode));
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
                } else {
                    return listImSearchBean;
                }
            }
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }



        return listImSearchBean;
    }

    /**
     * AnDV
     * 12/4/2010
     * Tim kiem cac dich vu co chua id mat hang cho truoc
     */
    public List<BigDecimal> getListSaleServicesIdByStockModelId(Long stockModelId) {
        StringBuffer strQuery = new StringBuffer("");
        strQuery.append("SELECT DISTINCT sale_services_id FROM sale_services_model ");
        strQuery.append("WHERE sale_services_model_id in ");
        strQuery.append("(SELECT DISTINCT sale_services_model_id FROM sale_services_detail WHERE stock_model_id = ? and status=? )");
        Query query = getSession().createSQLQuery(strQuery.toString());
        query.setParameter(0, stockModelId);
        query.setParameter(1, Constant.STATUS_ACTIVE);
        List<BigDecimal> lstSaleServicesId = query.list();
        return lstSaleServicesId;
    }
}
