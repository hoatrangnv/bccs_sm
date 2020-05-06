package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ReportRevenueNiceNumberBean;
import com.viettel.im.common.ReportConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.ReportRevenueNiceNumberForm;
import java.util.List;
import java.util.ArrayList;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.SaleServices;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.Query;

/**
 *
 * @author tamdt1
 * bao cao doanh thu
 *
 */
public class ReportRevenueNiceNumberDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(LookupIsdnDAO.class);
    //dinh nghia cac hang so pageForward
    private String pageForward;
    private final String REPORT_REVENUE_NICE_NUMBER = "reportRevenueNiceNumber";
    //dinh nghia ten cac bien session hoac bien request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_REPORT_REVENUE_NICE_NUMBER_PATH = "reportRevenueNiceNumberPath";
    private final String REQUEST_REPORT_REVENUE_NICE_NUMBER_MESSAGE = "reportRevenueNiceNumberMessage";
    private final Long STATUS_SALE_SERVICES = 1L;
    private final Long TELECOM_SERVICE_ID = 1L;
    //
    //khai bao bien form
    private ReportRevenueNiceNumberForm reportRevenueNiceNumberForm = new ReportRevenueNiceNumberForm();

    public ReportRevenueNiceNumberForm getReportRevenueNiceNumberForm() {
        return reportRevenueNiceNumberForm;
    }

    public void setReportRevenueNiceNumberForm(ReportRevenueNiceNumberForm reportRevenueNiceNumberForm) {
        this.reportRevenueNiceNumberForm = reportRevenueNiceNumberForm;
    }

    /**
     *
     * author tamdt1
     * date: 22/06/2009
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ReportRevenueNiceNumberDAO ...");

        HttpServletRequest req = getRequest();
        //reset form
        this.reportRevenueNiceNumberForm.resetForm();
        //thiet lap cac truong mac dinh cho shop code va staff code
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        this.reportRevenueNiceNumberForm.setShopCode(userToken.getShopCode());
        this.reportRevenueNiceNumberForm.setShopName(userToken.getShopName());
        this.reportRevenueNiceNumberForm.setStaffCode(userToken.getLoginName());
        this.reportRevenueNiceNumberForm.setStaffName(userToken.getStaffName());
        //lay du lieu cho cac combobox

        pageForward = REPORT_REVENUE_NICE_NUMBER;
        log.info("End method preparePage of ReportRevenueNiceNumberDAO");
        return pageForward;
    }

    /**
     *
     * author   : NamNX
     * date     : 18/01/2010
     * purpose  : lay danh sach cac dich vu
     *
     */
    public List<ImSearchBean> getListSaleServices(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
        strQuery1.append("from SaleServices a ");
        strQuery1.append("where 1 = 1 ");


        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.code) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);
        strQuery1.append("and a.telecomServicesId = ? ");
        listParameter1.add(TELECOM_SERVICE_ID);
        strQuery1.append("and rownum < ? ");
        listParameter1.add(100L);

        strQuery1.append("order by nlssort(a.code, 'nls_sort=Vietnamese') asc ");

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
     * author   : NamNX
     * date     : 18/01/2010
     * bao cao doanh thu so dep, xuat ra file excel
     *
     */
    public String reportRevenueNiceNumber() throws Exception {
        log.info("Begin method reportRevenue of ReportRevenueNiceNumberDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Long staffId = this.reportRevenueNiceNumberForm.getStaffId();
        Long saleServicesId = this.reportRevenueNiceNumberForm.getSaleServicesId();
        String strFromDate = this.reportRevenueNiceNumberForm.getFromDate();
        String strToDate = this.reportRevenueNiceNumberForm.getToDate();
        Boolean hasMoney = this.reportRevenueNiceNumberForm.getHasMoney();

        if (!checkValidReportRevenue()) {
            pageForward = REPORT_REVENUE_NICE_NUMBER;
            log.info("End method reportRevenue of ReportRevenueNiceNumberDAO");
            return pageForward;
        }

        String shopCode = this.reportRevenueNiceNumberForm.getShopCode().trim();
        String strShopQuery = " from Shop a where lower(a.shopCode) = ? and status = ? ";
        Query shopQuery = getSession().createQuery(strShopQuery);
        shopQuery.setParameter(0, shopCode.trim().toLowerCase());
        shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
        List<Shop> listShop = shopQuery.list();
        if (listShop == null || listShop.size() == 0) {
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.shopNotExist");

            pageForward = REPORT_REVENUE_NICE_NUMBER;
            log.info("End method reportRevenue of ReportRevenueNiceNumberDAO");
            return pageForward;
        }

        String shopName = listShop.get(0).getName();
        this.reportRevenueNiceNumberForm.setShopId(listShop.get(0).getShopId());

        String staffCode = this.reportRevenueNiceNumberForm.getStaffCode();
        if (staffCode != null && !staffCode.trim().equals("")) {
            String queryString = " from Staff a where lower(a.staffCode) = ? and a.shopId = ? and status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, staffCode.trim().toLowerCase());
            queryObject.setParameter(1, listShop.get(0).getShopId());
            queryObject.setParameter(2, Constant.STATUS_ACTIVE);
            List<Staff> listStaff = queryObject.list();

            if (listStaff == null || listStaff.size() == 0) {
                req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.staffNotExist");

                pageForward = REPORT_REVENUE_NICE_NUMBER;
                log.info("End method reportRevenue of ReportRevenueNiceNumberDAO");
                return pageForward;
            }

            this.reportRevenueNiceNumberForm.setStaffId(listStaff.get(0).getStaffId());

        }

        //Lay saleServicesId
        String saleServiceCode = this.reportRevenueNiceNumberForm.getSaleServiceCode();
        if (saleServiceCode != null && !saleServiceCode.trim().equals("")) {
            String queryString = " from SaleServices a where lower(a.code) = ? and a.status = ? and a.telecomServicesId = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, saleServiceCode.trim().toLowerCase());
            queryObject.setParameter(1, Constant.STATUS_ACTIVE);
            queryObject.setParameter(2, TELECOM_SERVICE_ID);
            List<SaleServices> listSaleServices = queryObject.list();

            if (listSaleServices == null || listSaleServices.size() == 0) {
                req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.SaleServicesNotExist");

                pageForward = REPORT_REVENUE_NICE_NUMBER;
                log.info("End method reportRevenue of ReportRevenueNiceNumberDAO");
                return pageForward;
            }

            this.reportRevenueNiceNumberForm.setSaleServicesId(listSaleServices.get(0).getSaleServicesId());
        }

        /*TuanPV - 24/02/2010 - Comment de chuyen sang mo hinh ReportServer*/
        /*
        List<ReportRevenueNiceNumberBean> listRevenueNiceNumber = getListReportRevenueNiceNumber();
        if(null == listRevenueNiceNumber || 0 == listRevenueNiceNumber.size())
        {
        req.setAttribute(REQUEST_MESSAGE, "Danh sách doanh thu số đẹp rỗng");
        }else
        {
        //ket xuat ket qua ra file excel
        try
        {
        String DATE_FORMAT = "yyyyMMddhh24mmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        String date = sdf.format(cal.getTime());
        String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
        String templatePath = "";
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        templatePath = tmp + "Report_Revenue_Nice_Number.xls";
        filePath += "Report_Revenue_Nice_Number_" + userToken.getLoginName() + "_" + date + ".xls";
        
        reportRevenueNiceNumberForm.setPathExpFile(filePath);
        
        String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
        
        Map beans = new HashMap();
        beans.put("listRevenueNiceNumber", listRevenueNiceNumber);
        beans.put("shopName", shopName);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        beans.put("fromDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(strFromDate)));
        beans.put("toDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(strToDate)));
        
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(templateRealPath, beans, realPath);
        req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thành công!");
        req.setAttribute(REQUEST_REPORT_REVENUE_NICE_NUMBER_PATH, filePath);
        req.setAttribute(REQUEST_REPORT_REVENUE_NICE_NUMBER_MESSAGE, "reportRevenue.reportRevenueMessage");
        
        }catch(Exception ex)
        {
        ex.printStackTrace();
        
        req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
        return pageForward;
        }
        
        }
        End TuanPV Comment*/

        /*TuanPV - 24/02/2010 - Gửi lệnh xuất báo cáo sang app ReportServer*/
        ViettelMsg request = new OriginalViettelMsg();
        request.set("FROM_DATE", reportRevenueNiceNumberForm.getFromDate());
        request.set("TO_DATE", reportRevenueNiceNumberForm.getToDate());
        request.set("USER_NAME", userToken.getLoginName());

        request.set("SHOP_ID", reportRevenueNiceNumberForm.getShopId());
        request.set("SHOP_NAME", shopName);
        request.set("STAFF_ID", reportRevenueNiceNumberForm.getStaffId());
        request.set("SALE_SERVICE_ID", reportRevenueNiceNumberForm.getSaleServicesId());
        if (reportRevenueNiceNumberForm.getHasMoney()) {
            request.set("HAS_MONEY", "true");
        } else {
            request.set("HAS_MONEY", "false");
        }
        if (reportRevenueNiceNumberForm.getNoMoney()) {
            request.set("HAS_NO_MONEY", "true");
        } else {
            request.set("HAS_NO_MONEY", "false");
        }

        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_REVENUE_NICE_NUMBER);

        ViettelMsg response = ReportRequestSender.sendRequest(request);
        if (response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
            //Thong bao len jsp            
            req.setAttribute(REQUEST_MESSAGE, "MSG.RET.030");
            reportRevenueNiceNumberForm.setPathExpFile(response.get(ReportConstant.RESULT_FILE).toString());
            req.setAttribute(REQUEST_REPORT_REVENUE_NICE_NUMBER_PATH, response.get(ReportConstant.RESULT_FILE).toString());
            req.setAttribute(REQUEST_REPORT_REVENUE_NICE_NUMBER_MESSAGE, "reportRevenue.reportRevenueMessage");
        } else {
            req.setAttribute(REQUEST_MESSAGE, "report.warning.noResult");
        }

        pageForward = REPORT_REVENUE_NICE_NUMBER;

        log.info("End method reportRevenue of ReportRevenueNiceNumberDAO");

        return pageForward;
    }

    /**
     *
     * NamNX, 18/01/2010
     * kiem tra cac dieu kien hop le truoc khi xuat bao cao
     *
     */
    private boolean checkValidReportRevenue() {
        HttpServletRequest req = getRequest();

        String shopCode = this.reportRevenueNiceNumberForm.getShopCode();
        String strFromDate = this.reportRevenueNiceNumberForm.getFromDate();
        String strToDate = this.reportRevenueNiceNumberForm.getToDate();

        //kiem tra cac truong bat buoc
        if (shopCode == null || shopCode.trim().equals("")
                || strFromDate == null || strFromDate.trim().equals("")
                || strToDate == null || strToDate.trim().equals("")) {

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
        if (fromCalendar.get(Calendar.MONTH) != toCalendar.get(Calendar.MONTH)) {
            //khong cung thang
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.monthOfFromDateAndToDateDifferent");
            return false;
        }
        if (fromCalendar.compareTo(toCalendar) > 0) {
            //ngay bat dau lon hon ngay ket thuc
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.startDateLargerEndDate");
            return false;
        }

        //kiem tra tinh hop le cua shop va staff, chi cho phep lay bao cao doanh thu tu cap nay tro xuong
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        strQuery1.append("and lower(a.shopCode) = ? ");
        listParameter1.add(shopCode.trim().toLowerCase());

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Shop> tmpList1 = query1.list();
        if (tmpList1 == null || tmpList1.size() == 0) {
            //khogn tim thay shop
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.shopNotExist");
            return false;
        }
        this.reportRevenueNiceNumberForm.setShopId(tmpList1.get(0).getShopId());

        //kiem tra staff
        String strStaffCode = this.reportRevenueNiceNumberForm.getStaffCode();
        if (strStaffCode != null && !strStaffCode.trim().equals("")) {
            List listParameter2 = new ArrayList();
            StringBuffer strQuery2 = new StringBuffer("");
            strQuery2.append("from Staff a ");
            strQuery2.append("where 1 = 1 ");

            strQuery2.append("and a.shopId = ? ");
            listParameter2.add(tmpList1.get(0).getShopId());

//            strQuery2.append("and a.channelTypeId = ? ");
//            listParameter2.add(Constant.CHANNEL_TYPE_STAFF);

            //Modified by : TrongLV
            //Modified date : 2011-08-16
            strQuery2.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
            listParameter2.add(Constant.OBJECT_TYPE_STAFF);
            listParameter2.add(Constant.IS_VT_UNIT);





            strQuery2.append("and lower(a.staffCode) = ? ");
            listParameter2.add(strStaffCode.trim().toLowerCase());

            strQuery2.append("and rownum < ? ");
            listParameter2.add(300L);

            Query query2 = getSession().createQuery(strQuery2.toString());
            for (int i = 0; i < listParameter2.size(); i++) {
                query2.setParameter(i, listParameter2.get(i));
            }

            List<Staff> tmpList2 = query2.list();
            if (tmpList2 == null || tmpList2.size() == 0) {
                //khogn tim thay staff
                req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.staffNotExist");
                return false;
            } else {
                this.reportRevenueNiceNumberForm.setStaffId(tmpList2.get(0).getStaffId());
            }
        }

        return true;
    }

    /**
     *
     * author NamNX
     * date: 19/01/2010
     * lay du lieu bao cao doanh thu so dep
     *
     */
    private List<ReportRevenueNiceNumberBean> getListReportRevenueNiceNumber() throws Exception {

        List<ReportRevenueNiceNumberBean> listReportRevenueNiceNumberBean = new ArrayList<ReportRevenueNiceNumberBean>();

        Long shopId = this.reportRevenueNiceNumberForm.getShopId();
        Long staffId = this.reportRevenueNiceNumberForm.getStaffId();
        Long saleServiceId = this.reportRevenueNiceNumberForm.getSaleServicesId();
        String strFromDate = this.reportRevenueNiceNumberForm.getFromDate();
        String strToDate = this.reportRevenueNiceNumberForm.getToDate();
        Boolean hasMoney = this.reportRevenueNiceNumberForm.getHasMoney();
        Boolean noMoney = this.reportRevenueNiceNumberForm.getNoMoney();

        StringBuffer strQuery = new StringBuffer(
                "select new com.viettel.im.client.bean.ReportRevenueNiceNumberBean(a.isdn as isdn, b.price as sourcePrice, d.price as revenuePrice, t.code as saleServicesCode, t.name as saleServicesName, "
                + "f.staffCode || ' - ' || f.name as staffActivate, "
                + "g.shopCode || ' - ' || g.name as shopActivate, "
                + "h.shopCode || ' - ' || h.name as shopName, "
                + "a.saleTransDate as datetimeActivate) "
                + "from SaleTrans a, StockIsdnMobile b, SaleTransDetail c, Price d, IsdnFilterRules e, Staff f, Shop g, Shop h, SaleServices t "
                + "where a.status <> 4 and b.isdn=to_number(a.isdn) "
                + "and a.saleTransId =  c.saleTransId "
                + "and c.priceId = d.priceId "
                + "and e.rulesId = b.rulesId "
                + "and a.staffId = f.staffId "
                + "and a.shopId = g.shopId "
                + "and b.ownerId = h.shopId "
                + "and t.saleServicesId = a.saleServiceId "
                + "and c.stockModelId in (select stockModelId from StockModel where stockTypeId = 1 and status = 1) "
                + "and a.saleTransType = 4 "
                + "and b.stockModelId <> 1 ");
//        StringBuffer strQuery = new StringBuffer("SELECT new com.viettel.im.client.bean.ReportRevenueNiceNumberBean(a.isdn as isdn, b.price as sourcePrice, d.price as revenuePrice, t.code as saleServicesCode, t.name as saleServicesName, " +
//                "f.staff_code || ' - ' || f.NAME as staffActivate, " +
//                "g.shop_code || ' - ' || g.NAME AS shopActivate, " +
//                "h.shop_code || ' - ' || h.NAME AS shopName, a.sale_trans_date AS datetimeActivate) " +
//                "FROM sale_trans a, " +
//                "stock_isdn_mobile b, " +
//                "sale_trans_detail c, " +
//                "price d, " +
//                "isdn_filter_rules e, " +
//                "staff f, " +
//                "shop g, " +
//                "shop h, " +
//                "sale_services t " +
//                "WHERE a.status <> 4 " +
//                " AND a.isdn = b.isdn " +
//                "AND a.sale_trans_id = c.sale_trans_id " +
//                "AND c.price_id = d.price_id " +
//                "AND e.rules_id = b.rules_id " +
//                "AND a.staff_id = f.staff_id " +
//                "AND a.shop_id = g.shop_id " +
//                "AND b.owner_id = h.shop_id " +
//                "AND t.sale_services_id = a.sale_service_id " +
//                "AND c.stock_model_id IN (SELECT stock_model_id " +
//                "                           FROM stock_model " +
//                "                         WHERE stock_type_id = 1 AND status = 1) " +
//                "AND a.sale_trans_type = 4 " +
//                "AND b.stock_model_id <> 1 ");

        List listParameter = new ArrayList();

//        strQuery.append("and a.shopId = ? ");
//        listParameter.add(shopId);
//        strQuery.append("and a.shopId in (select shopId from Shop where shopPath like ? ESCAPE '$' or shopId =? ) ");
        strQuery.append(" and exists ( from Shop where shopId = a.shopId and (shopPath LIKE ? ESCAPE '$' OR shopId = ? )) ");
        listParameter.add("%$_" + shopId.toString() + "$_%");
        listParameter.add(shopId);
        if (staffId != null && staffId.compareTo(0L) > 0) {
            strQuery.append("and a.staffId = ? ");
            listParameter.add(staffId);
        }
        if (saleServiceId != null && saleServiceId.compareTo(0L) > 0) {
            strQuery.append("and a.saleServiceId = ? ");
            listParameter.add(saleServiceId);
        }
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
            strQuery.append("and a.saleTransDate >= ? ");
            listParameter.add(fromDate);
            strQuery.append("and c.saleTransDate >= ? ");
            listParameter.add(fromDate);
        }

        Date toDate = new Date();
        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
            strQuery.append("and a.saleTransDate < ? ");
            listParameter.add(afterToDateOneDay);
            strQuery.append("and c.saleTransDate < ? ");
            listParameter.add(afterToDateOneDay);
        }

        if (hasMoney && !noMoney) {
            strQuery.append("and d.price > 0");
        }

        if (noMoney && !hasMoney) {
            strQuery.append("and d.price = 0");
        }
//        if(hasMoney && noMoney){
//             strQuery.append("and d.price >= 0");
//        }




        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }
        listReportRevenueNiceNumberBean = query.list();
        List<ReportRevenueNiceNumberBean> tmpList = query.list();
        if (tmpList != null && tmpList.size() > 0) {
            listReportRevenueNiceNumberBean.addAll(tmpList);
        }

        return listReportRevenueNiceNumberBean;
    }
}
