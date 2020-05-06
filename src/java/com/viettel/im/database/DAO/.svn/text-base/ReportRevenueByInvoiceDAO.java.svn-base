package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.ReportRevenueByInvoiceForm;
import java.util.List;
import java.util.ArrayList;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VReportRevenueByInvoice;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Query;

/**
 *
 * @author tamdt1, 03/08/2009 bao cao doanh thu theo hoa don
 *
 */
public class ReportRevenueByInvoiceDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReportRevenueByInvoiceDAO.class);
    //dinh nghia cac hang so pageForward
    private String pageForward;
    private final String REPORT_REVENUE_BY_INVOICE = "reportRevenueByInvoice";
    private final String GET_SHOP_CODE = "getShopCode";
    private final String GET_SHOP_NAME = "getShopName";
    private final String GET_STAFF_CODE = "getStaffCode";
    private final String GET_STAFF_NAME = "getStaffName";
    private final String CHANGE_STOCK_TYPE = "changeStockType";
    //dinh nghia ten cac bien session hoac bien request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_REPORT_REVENUE_PATH = "reportRevenuePath";
    private final String REQUEST_REPORT_REVENUE_MESSAGE = "reportRevenueMessage";
    private final String REQUEST_LIST_STOCK_TYPE = "listStockType";
    private final String REQUEST_LIST_STOCK_MODEL = "listStockModel";
    private final String REQUEST_LIST_REASON = "listReason";
    private final String REQUEST_LIST_TELECOM_SERVICE = "listTelecomService";
    private final String SESSION_CURRENT_SHOP_ID = "currentShopId";
    //
    private final Long SALE_TRANS_TYPE_HAS_PAYMENT = 1L; //loai giao dich co thu tien
    private final Long SALE_TRANS_TYPE_NOT_PAYMENT = 0L; //loai giao dich khong thu tien
    //khai bao bien form
    private ReportRevenueByInvoiceForm reportRevenueByInvoiceForm = new ReportRevenueByInvoiceForm();

    public ReportRevenueByInvoiceForm getReportRevenueByInvoiceForm() {
        return reportRevenueByInvoiceForm;
    }

    public void setReportRevenueByInvoiceForm(ReportRevenueByInvoiceForm reportRevenueByInvoiceForm) {
        this.reportRevenueByInvoiceForm = reportRevenueByInvoiceForm;
    }

    /**
     *
     * author tamdt1 date: 03/08/2009 man hinh bao cao
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ReportRevenueByInvoiceDAO ...");

        HttpServletRequest req = getRequest();

        //reset form
        this.reportRevenueByInvoiceForm.resetForm();

        //thiet lap cac gia tri mac dinh cho user name
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        this.reportRevenueByInvoiceForm.setShopId(userToken.getShopId());
        this.reportRevenueByInvoiceForm.setShopCode(userToken.getShopCode());
        this.reportRevenueByInvoiceForm.setShopName(userToken.getShopName());
        this.reportRevenueByInvoiceForm.setStaffCode(userToken.getLoginName());
        this.reportRevenueByInvoiceForm.setStaffName(userToken.getStaffName());
        //xoa bien session
        req.getSession().setAttribute(SESSION_CURRENT_SHOP_ID, null);
        //phan quyen chon
        if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("reportRevenueByInvoicef9Shop"), req)) {
            req.getSession().setAttribute("Edit", "true");
        }
        if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("reportRevenueByInvoicef9Staff"), req)) {
            req.getSession().setAttribute("EditStaff", "true");
        }

        //lay du lieu cho cac combobox
        getDataForCombobox();

        pageForward = REPORT_REVENUE_BY_INVOICE;

        log.info("End method preparePage of ReportRevenueByInvoiceDAO");

        return pageForward;
    }

    /**
     * @author : ANHTT
     * @return : Thưc hien xuat báo cáo theo cách mới
     * @throws Exception
     */
    public String reportRevenueByInvoice() throws Exception {
        log.info("Begin method reportRevenueByInvoice of ReportRevenueByInvoiceDAO...");
        HttpServletRequest req = getRequest();

        String strFromDate = this.reportRevenueByInvoiceForm.getFromDate();
        String strToDate = this.reportRevenueByInvoiceForm.getToDate();
        Long telecomServiceId = this.reportRevenueByInvoiceForm.getTelecomServiceId();
        Long payMoney = this.reportRevenueByInvoiceForm.getPayMoney();
        Boolean usedInvoice = this.reportRevenueByInvoiceForm.getUsedInvoice();
        Boolean destroyedInvoice = this.reportRevenueByInvoiceForm.getDestroyedInvoice();
        if (!checkValidReportRevenue()) {
            //lay du lieu cho cac combobox

            getDataForCombobox();
            pageForward = REPORT_REVENUE_BY_INVOICE;
            log.info("End method reportRevenueByInvoice of ReportRevenueByInvoiceDAO");
            return pageForward;
        }
        String shopCode = this.reportRevenueByInvoiceForm.getShopCode().trim();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List<Shop> listShop = shopDAO.findByPropertyWithStatus(ShopDAO.SHOP_CODE, shopCode, Constant.STATUS_USE);
        if (listShop == null || listShop.size() == 0) {
            //lay du lieu cho cac combobox
            getDataForCombobox();
            //
            req.setAttribute(REQUEST_MESSAGE, "reportRevenueByInvoice.error.shopNotExist");
            pageForward = REPORT_REVENUE_BY_INVOICE;
            log.info("End method reportRevenueByInvoice of ReportRevenueByInvoiceDAO");
            return pageForward;
        }
        String shopName = listShop.get(0).getName();
        this.reportRevenueByInvoiceForm.setShopId(listShop.get(0).getShopId());
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        String staffCode = this.reportRevenueByInvoiceForm.getStaffCode();
        if (staffCode != null && !staffCode.trim().equals("")) {
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            List<Staff> listStaff = staffDAO.findByPropertyAndStatus(StaffDAO.STAFF_CODE, staffCode.trim(), Constant.STATUS_USE);
            if (listStaff == null || listStaff.size() == 0) {
                //lay du lieu cho cac combobox
                getDataForCombobox();
                //
                req.setAttribute(REQUEST_MESSAGE, "reportRevenueByInvoice.error.staffNotExist");
                pageForward = REPORT_REVENUE_BY_INVOICE;
                log.info("End method reportRevenueByInvoice of ReportRevenueByInvoiceDAO");
                return pageForward;
            }

            this.reportRevenueByInvoiceForm.setStaffId(listStaff.get(0).getStaffId());

        }
        try {
            /*Goi toi service va lay ra du lieu bao cao*/
            ViettelMsg request = new OriginalViettelMsg();
            request.set("SHOP_ID", this.reportRevenueByInvoiceForm.getShopId());
            request.set("FROM_DATE", this.reportRevenueByInvoiceForm.getFromDate());
            request.set("TO_DATE", this.reportRevenueByInvoiceForm.getToDate());
            request.set("STAFF_ID", this.reportRevenueByInvoiceForm.getStaffId());
            request.set("SHOP_ID", this.reportRevenueByInvoiceForm.getShopId());
            request.set("SHOP_NAME", shopName);

            request.set("CHANNEL_TYPE_ID", listShop.get(0).getChannelTypeId());

            request.set("TEL_SERVICE_ID", this.reportRevenueByInvoiceForm.getTelecomServiceId());
            request.set("PAY_MONEY", this.reportRevenueByInvoiceForm.getPayMoney());

            request.set("STAFF_NAME", userToken.getStaffName());
            request.set("USER_NAME", userToken.getLoginName());

            if (reportRevenueByInvoiceForm.getReportSimple() != null && reportRevenueByInvoiceForm.getReportSimple()) {
                request.set("REPORT_SIMPLE", "TRUE");
            }

            /*Thiet lap tham so loai bao cao*/
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_REVENUE_INVOICE);

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null && response.get(ReportConstant.RESULT_FILE) != null && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //Thong bao len jsp
                req.setAttribute(REQUEST_MESSAGE, "MSG.RET.182");
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute(REQUEST_REPORT_REVENUE_PATH, downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                //req.setAttribute(REQUEST_REPORT_REVENUE_PATH, response.get(ReportConstant.RESULT_FILE).toString());
                req.setAttribute(REQUEST_REPORT_REVENUE_MESSAGE, "reportRevenueByInvoice.reportRevenueMessage");
            } else {
                req.setAttribute(REQUEST_MESSAGE, "MSG.RET.183");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //lay du lieu cho cac combobox
            getDataForCombobox();
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.062" + e.toString());
            pageForward = REPORT_REVENUE_BY_INVOICE;
            return pageForward;
        }
        //lay du lieu cho cac combobox
        getDataForCombobox();
        pageForward = REPORT_REVENUE_BY_INVOICE;
        log.info("End method reportRevenueByInvoice of ReportRevenueByInvoiceDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 03/08/2009 bao cao doanh thu theo hoa don, xuat ra
     * file excel
     *
     */
    public String reportRevenueByInvoice1() throws Exception {
        log.info("Begin method reportRevenueByInvoice of ReportRevenueByInvoiceDAO...");

        HttpServletRequest req = getRequest();

        String strFromDate = this.reportRevenueByInvoiceForm.getFromDate();
        String strToDate = this.reportRevenueByInvoiceForm.getToDate();
        Long telecomServiceId = this.reportRevenueByInvoiceForm.getTelecomServiceId();
        Long payMoney = this.reportRevenueByInvoiceForm.getPayMoney();
        Boolean usedInvoice = this.reportRevenueByInvoiceForm.getUsedInvoice();
        Boolean destroyedInvoice = this.reportRevenueByInvoiceForm.getDestroyedInvoice();


        if (!checkValidReportRevenue()) {
            //
            //lay du lieu cho cac combobox
            getDataForCombobox();

            pageForward = REPORT_REVENUE_BY_INVOICE;
            log.info("End method reportRevenueByInvoice of ReportRevenueByInvoiceDAO");
            return pageForward;
        }

        String shopCode = this.reportRevenueByInvoiceForm.getShopCode().trim();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List<Shop> listShop = shopDAO.findByPropertyWithStatus(ShopDAO.SHOP_CODE, shopCode, Constant.STATUS_USE);
        if (listShop == null || listShop.size() == 0) {
            //
            //lay du lieu cho cac combobox
            getDataForCombobox();

            //
            req.setAttribute(REQUEST_MESSAGE, "reportRevenueByInvoice.error.shopNotExist");

            pageForward = REPORT_REVENUE_BY_INVOICE;
            log.info("End method reportRevenueByInvoice of ReportRevenueByInvoiceDAO");
            return pageForward;
        }

        String shopName = listShop.get(0).getName();
        this.reportRevenueByInvoiceForm.setShopId(listShop.get(0).getShopId());

        String staffCode = this.reportRevenueByInvoiceForm.getStaffCode();
        if (staffCode != null && !staffCode.trim().equals("")) {
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            List<Staff> listStaff = staffDAO.findByPropertyAndStatus(StaffDAO.STAFF_CODE,
                    staffCode.trim(), Constant.STATUS_USE);
            if (listStaff == null || listStaff.size() == 0) {
                //
                //lay du lieu cho cac combobox
                getDataForCombobox();

                //
                req.setAttribute(REQUEST_MESSAGE, "reportRevenueByInvoice.error.staffNotExist");

                pageForward = REPORT_REVENUE_BY_INVOICE;
                log.info("End method reportRevenueByInvoice of ReportRevenueByInvoiceDAO");
                return pageForward;
            }

            this.reportRevenueByInvoiceForm.setStaffId(listStaff.get(0).getStaffId());

        }

        List<VReportRevenueByInvoice> listVReportRevenueByInvoice = new ArrayList<VReportRevenueByInvoice>();
        String templatePathResource = "RR_BY_INVOICE_TMP_PATH_GENERAL_1";

        //lay du lieu bao cao
        listVReportRevenueByInvoice = getListGeneral();



        //ket xuat ket qua ra file excel
        try {
            String DATE_FORMAT = "yyyyMMddHHmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            filePath = filePath != null ? filePath : "/";
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            filePath += "ReportRevenueByInvoice_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
            String realPath = req.getSession().getServletContext().getRealPath(filePath);

            String templatePath = ResourceBundleUtils.getResource(templatePathResource);
            if (templatePath == null || templatePath.trim().equals("")) {
                //lay du lieu cho cac combobox
                getDataForCombobox();

                //khong tim thay duong dan den file template de xuat ket qua
                req.setAttribute(REQUEST_MESSAGE, "reportRevenueByInvoice.error.templateNotExist");

                pageForward = REPORT_REVENUE_BY_INVOICE;
                return pageForward;
            }

            String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
            File fTemplateFile = new File(realTemplatePath);
            if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                //lay du lieu cho cac combobox
                getDataForCombobox();

                //khong tim thay file template de xuat ket qua
                req.setAttribute(REQUEST_MESSAGE, "reportRevenueByInvoice.error.templateNotExist");

                pageForward = REPORT_REVENUE_BY_INVOICE;
                return pageForward;
            }

            Map beans = new HashMap();
            beans.put("listVReportRevenueByInvoice", listVReportRevenueByInvoice);
            beans.put("listVReportRevenueByInvoice_1", listVReportRevenueByInvoice); //phuc vu viec in ra tong so
            beans.put("shopName", shopName);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            beans.put("fromDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(strFromDate)));
            beans.put("toDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(strToDate)));
            beans.put("reportDate", simpleDateFormat.format(new Date()));

            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(realTemplatePath, beans, realPath);

            req.setAttribute(REQUEST_REPORT_REVENUE_PATH, filePath);
            req.setAttribute(REQUEST_REPORT_REVENUE_MESSAGE, "reportRevenueByInvoice.reportRevenueMessage");

        } catch (Exception ex) {
            ex.printStackTrace();

            //lay du lieu cho cac combobox
            getDataForCombobox();

            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return pageForward;
        }

        //lay du lieu cho cac combobox
        getDataForCombobox();

        pageForward = REPORT_REVENUE_BY_INVOICE;

        log.info("End method reportRevenueByInvoice of ReportRevenueByInvoiceDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 03/08/2009 lay du lieu bao cao doanh thu theo hoa don
     *
     */
    private List<VReportRevenueByInvoice> getListGeneral() throws Exception {
        List<VReportRevenueByInvoice> listVReportRevenueByInvoice = new ArrayList<VReportRevenueByInvoice>();

        Long shopId = this.reportRevenueByInvoiceForm.getShopId();
        Long staffId = this.reportRevenueByInvoiceForm.getStaffId();
        String strFromDate = this.reportRevenueByInvoiceForm.getFromDate();
        String strToDate = this.reportRevenueByInvoiceForm.getToDate();
        Long telecomServiceId = this.reportRevenueByInvoiceForm.getTelecomServiceId();
        Long payMoney = this.reportRevenueByInvoiceForm.getPayMoney();

        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Shop shop = shopDAO.findById(shopId);

        StringBuffer strQuery = new StringBuffer(
                "from VReportRevenueByInvoice a where 1 = 1 ");

        List listParameter = new ArrayList();

        //
        strQuery.append("and a.shopId = ? ");
        listParameter.add(shopId);

        if (staffId != null && staffId.compareTo(0L) > 0) {
            strQuery.append("and a.staffId = ? ");
            listParameter.add(staffId);
        }

        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
//            strQuery.append("and a.invoiceDate >= ? "); //thay nghiep vu, ngay lap hoa don -> ngay tao giao dich
            strQuery.append("and a.saleTransDate >= ? ");
            listParameter.add(fromDate);
        }

        Date toDate = new Date();
        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
//            strQuery.append("and a.invoiceDate < ? "); ////thay nghiep vu, ngay lap hoa don -> ngay tao giao dich
            strQuery.append("and a.saleTransDate < ? ");
            listParameter.add(afterToDateOneDay);
        }

        if (telecomServiceId != null && telecomServiceId.compareTo(0L) > 0) {
            strQuery.append("and a.telecomServiceId = ? ");
            listParameter.add(telecomServiceId);
        }

        if (payMoney != null && payMoney.equals(SALE_TRANS_TYPE_HAS_PAYMENT)) {
            //cac giao dich co thu tien (bao gom ban le, ban dai ly, ban CTV, DB, lam giao dich CM)
            strQuery.append("and (a.saleTransType = ? or a.saleTransType =? or a.saleTransType =? or a.saleTransType =?) ");
            listParameter.add(Constant.SALE_TRANS_TYPE_RETAIL);
            listParameter.add(Constant.SALE_TRANS_TYPE_AGENT);
            listParameter.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
            listParameter.add(Constant.SALE_TRANS_TYPE_SERVICE);
        } else if (payMoney != null && payMoney.equals(SALE_TRANS_TYPE_NOT_PAYMENT)) {
            //cac giao dich khong thu tien (bao gom ban khuyen mai va ban hang noi bo)
            strQuery.append("and (a.saleTransType = ? or a.saleTransType =?) ");
            listParameter.add(Constant.SALE_TRANS_TYPE_PROMOTION);
            listParameter.add(Constant.SALE_TRANS_TYPE_INTERNAL);
        }

        //strQuery.append("order by a.saleTransId");
        //ThanhNC sua order by so hoa don trong bao cao doanh thu theo hoa don
        strQuery.append("order by a.invoiceNo");

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        //lay danh sach cac giao dich
        listVReportRevenueByInvoice = query.list();

        return listVReportRevenueByInvoice;
    }
    private Map listItem = new HashMap();

    public Map getListItem() {
        return listItem;
    }

    public void setListItem(Map listItem) {
        this.listItem = listItem;
    }

    /**
     *
     * author tamdt1 date: 21/06/2009 lay du lieu cho autocompleter
     *
     */
    public String getShopCode() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String shopCode = req.getParameter("reportRevenueByInvoiceForm.shopCode");

            //
            req.getSession().setAttribute(SESSION_CURRENT_SHOP_ID, null);

            if (shopCode != null && shopCode.trim().length() > 0) {
                String queryString = "from Shop where lower(shopCode) like ? and status = ? ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, "%" + shopCode.trim().toLowerCase() + "%");
                queryObject.setParameter(1, Constant.STATUS_USE);
                queryObject.setMaxResults(8);
                List<Shop> listShop = queryObject.list();
                if (listShop != null && listShop.size() > 0) {
                    for (int i = 0; i < listShop.size(); i++) {
                        this.listItem.put(listShop.get(i).getShopId(), listShop.get(i).getShopCode());
                    }
                }
            }

        } catch (Exception ex) {
            throw ex;
        }
        return GET_SHOP_CODE;
    }

    /**
     *
     * author tamdt1 date: 21/06/2009 lay ten shopCode cap nhat vao textbox
     *
     */
    public String getShopName() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String strShopId = req.getParameter("shopId");
            String target = req.getParameter("target");

            if (strShopId != null && strShopId.trim().length() > 0) {
                Long shopId = Long.valueOf(strShopId);
                String queryString = "from Shop where shopId = ? ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, shopId);
                queryObject.setMaxResults(8);
                List<Shop> listShop = queryObject.list();
                if (listShop != null && listShop.size() > 0) {
                    this.listItem.put(target, listShop.get(0).getName());

                    //
                    req.getSession().setAttribute(SESSION_CURRENT_SHOP_ID, shopId);
                }
            }
        } catch (Exception ex) {
            throw ex;
        }

        return GET_SHOP_NAME;
    }

    /**
     *
     * author tamdt1 date: 24/06/2009 lay du lieu cho autocompleter
     *
     */
    public String getStaffCode() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String staffCode = req.getParameter("reportRevenueByInvoiceForm.staffCode");
            Long shopId = (Long) req.getSession().getAttribute(SESSION_CURRENT_SHOP_ID);

            if (shopId != null && staffCode != null && staffCode.trim().length() > 0) {
                String queryString = "from Staff where lower(staffCode) like ? and shopId = ? and status = ? ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, "%" + staffCode.trim().toLowerCase() + "%");
                queryObject.setParameter(1, shopId);
                queryObject.setParameter(2, Constant.STATUS_USE);
                queryObject.setMaxResults(8);
                List<Staff> listStaff = queryObject.list();
                if (listStaff != null && listStaff.size() > 0) {
                    for (int i = 0; i < listStaff.size(); i++) {
                        this.listItem.put(listStaff.get(i).getStaffId(), listStaff.get(i).getStaffCode());
                    }
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return GET_SHOP_CODE;
    }

    /**
     *
     * author tamdt1 date: 24/06/2009 lay ten staffCode cap nhat vao textbox
     *
     */
    public String getStaffName() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String strShopId = req.getParameter("staffId");
            String target = req.getParameter("target");

            if (strShopId != null && strShopId.trim().length() > 0) {
                String queryString = "from Staff where staffId = ? ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, Long.valueOf(strShopId));
                queryObject.setMaxResults(8);
                List<Staff> listStaff = queryObject.list();
                if (listStaff != null && listStaff.size() > 0) {
                    this.listItem.put(target, listStaff.get(0).getName());
                }
            }
        } catch (Exception ex) {
            throw ex;
        }

        return GET_SHOP_NAME;
    }

    /**
     *
     * tamdt1, 03/08/2009 kiem tra cac dieu kien hop le truoc khi xuat bao cao
     *
     */
    private boolean checkValidReportRevenue() {
        HttpServletRequest req = getRequest();

        String shopCode = this.reportRevenueByInvoiceForm.getShopCode();
        String strFromDate = this.reportRevenueByInvoiceForm.getFromDate();
        String strToDate = this.reportRevenueByInvoiceForm.getToDate();

        //kiem tra cac truong bat buoc
        if (shopCode == null || shopCode.trim().equals("")
                || strFromDate == null || strFromDate.trim().equals("")
                || strToDate == null || strToDate.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGE, "reportRevenueByInvoice.error.requiredFieldsEmpty");
            return false;
        }

        Date fromDate = new Date();
        Date toDate = new Date();
        try {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
            toDate = DateTimeUtils.convertStringToDate(strToDate);
        } catch (Exception ex) {
            //bao loi
            req.setAttribute(REQUEST_MESSAGE, "reportRevenueByInvoice.error.invalidDateFormat");
            return false;
        }

        Calendar fromCalendar = Calendar.getInstance();
        Calendar toCalendar = Calendar.getInstance();
        fromCalendar.setTime(fromDate);
        toCalendar.setTime(toDate);
//        if(fromCalendar.get(Calendar.MONTH) != toCalendar.get(Calendar.MONTH)) {
//            //khong cung thang
//            req.setAttribute(REQUEST_MESSAGE, "reportRevenueByInvoice.error.monthOfFromDateAndToDateDifferent");
//            return false;
//        }
        if (fromCalendar.compareTo(toCalendar) > 0) {
            //ngay bat dau lon hon ngay ket thuc
            req.setAttribute(REQUEST_MESSAGE, "reportRevenueByInvoice.error.startDateLargerEndDate");
            return false;
        }
        if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear() != fromDate.getYear()) {
            req.setAttribute(REQUEST_MESSAGE, "stock.report.impExp.error.fromDateToDateNotSame");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1 date: 03/08/2009 lay du lieu cho cac combobox
     *
     */
    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();

        //lay danh sach cac telecomservices
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List<TelecomService> listTelecomService = telecomServiceDAO.findTelecomServicesByStatus(
                Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_TELECOM_SERVICE, listTelecomService);

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

        //lay danh sach cac kho dac biet
        List listParameter2 = new ArrayList();
        StringBuffer strQuery2 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery2.append("from Shop a ");
        strQuery2.append("where 1 = 1 ");

        strQuery2.append("and channelTypeId = ? ");
        String strSpecialChannelTypeId = ResourceBundleUtils.getResource("SHOP_SPECIAL");
        Long specialChannelTypeId = -1L;
        try {
            specialChannelTypeId = Long.valueOf(strSpecialChannelTypeId.trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        listParameter2.add(specialChannelTypeId);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery2.append("and lower(a.shopCode) like ? ");
            listParameter2.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery2.append("and lower(a.name) like ? ");
            listParameter2.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery2.append("and rownum < ? ");
        listParameter2.add(300L);

        strQuery2.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query2 = getSession().createQuery(strQuery2.toString());
        for (int i = 0; i < listParameter2.size(); i++) {
            query2.setParameter(i, listParameter2.get(i));
        }

        List<ImSearchBean> tmpList2 = query2.list();
        if (tmpList2 != null && tmpList2.size() > 0) {
            listImSearchBean.addAll(tmpList2);
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
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
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
//
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
}
