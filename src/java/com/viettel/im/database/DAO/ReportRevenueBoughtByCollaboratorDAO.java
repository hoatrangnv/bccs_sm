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
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.SaleServices;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.Query;

/**
 *
 * @author Doan Thanh 8 desc bao cao mua hang CTV/ DB
 *
 */
public class ReportRevenueBoughtByCollaboratorDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReportRevenueBoughtByCollaboratorDAO.class);
    //dinh nghia cac hang so pageForward
    private String pageForward;
    private final String REPORT_REVENUE_BOUGHT_BY_COLLABORATOR = "reportRevenueBoughtByCollaborator";
    //dinh nghia ten cac bien session hoac bien request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_REPORT_REVENUE_PATH = "reportRevenuePath";
    private final String REQUEST_REPORT_REVENUE_MESSAGE = "reportRevenueMessage";
    private final String REQUEST_LIST_TELECOM_SERVICE = "listTelecomService";
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
     * author tamdt1 date: 22/06/2009
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
        this.reportRevenueForm.setReportType(1L);

        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        req.setAttribute("lstChannelTypeCol", lstChannelTypeCol);

        pageForward = REPORT_REVENUE_BOUGHT_BY_COLLABORATOR;
        log.info("End method preparePage of ReportRevenueDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 24/06/2009 bao cao doanh thu, xuat ra file excel
     *
     */
    public String reportRevenueBoughtByCollaborator() throws Exception {
        log.info("Begin method reportRevenueBoughtByCollaborator of ReportRevenueBoughtByCollaboratorDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        String shopCode = this.reportRevenueForm.getShopCode();
        String staffManageCode = this.reportRevenueForm.getStaffManageCode();
        String staffManageName = this.reportRevenueForm.getStaffManageName();
        Long staffManageId = null;
        String ownerCode = this.reportRevenueForm.getOwnerCode();
        String ownerName = this.reportRevenueForm.getOwnerName();
        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();

        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        req.setAttribute("lstChannelTypeCol", lstChannelTypeCol);

        pageForward = REPORT_REVENUE_BOUGHT_BY_COLLABORATOR;

        //kiem tra cac truong bat buoc
        if (shopCode == null || shopCode.trim().equals("") || strFromDate == null || strFromDate.trim().equals("") || strToDate == null || strToDate.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.requiredFieldsEmpty");
            log.info("End method reportRevenueBoughtByCollaborator of ReportRevenueBoughtByCollaboratorDAO");
            return pageForward;
        }

        // kiem tra shop co ton tai hay ko
        // tao doi tuong tim kiem
        ImSearchBean imSearchBean = new ImSearchBean();
        imSearchBean.setHttpServletRequest(req);
        imSearchBean.setCode(shopCode);
        List<ImSearchBean> listShop = getExtractListShop(imSearchBean);

        if (listShop == null || listShop.isEmpty()) {
            //
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.shopNotExist");
            log.info("End method reportRevenueBoughtByCollaborator of ReportRevenueBoughtByCollaboratorDAO");
            return pageForward;
        }

        // tim kenh theo shop
        shopCode = listShop.get(0).getCode();
        String shopName = listShop.get(0).getName();
        Shop shop = new ShopDAO().findShopsAvailableByShopCode(shopCode);
        this.reportRevenueForm.setShopId(shop.getShopId());
        Long channelTypeShopId = shop.getChannelTypeId();
        ChannelType channelTypeShop = new ChannelTypeDAO().findById(channelTypeShopId);


        // kiem tra nhan vien quan ly co ton tai hay ko
        if (staffManageCode != null && !staffManageCode.trim().equals("")) {
            imSearchBean.setCode(staffManageCode);
            imSearchBean.setOtherParamValue(shopCode);
            List<ImSearchBean> listStaff = getExtractListStaff(imSearchBean);
            if (listStaff == null || listStaff.isEmpty()) {
                //
                req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.staffNotExist");
                log.info("End method reportRevenueBoughtByCollaborator of ReportRevenueBoughtByCollaboratorDAO");
                return pageForward;
            }
            staffManageCode = listStaff.get(0).getCode();
            staffManageName = listStaff.get(0).getName();

            // tim staff_id theo shop
            Staff staff = new StaffDAO().findAvailableByStaffCode(staffManageCode);
            staffManageId = staff.getStaffId();
        }



        // kiem tra ma dai ly co ton tai hay ko 
        if (ownerCode != null && !ownerCode.trim().equals("")) {
            // chua nhap ma nguoi quan ly
            if (staffManageCode == null || staffManageCode.trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.015");
                log.info("End method reportRevenueBoughtByCollaborator of ReportRevenueBoughtByCollaboratorDAO");
                return pageForward;
            }
            imSearchBean.setCode(ownerCode);
            imSearchBean.setOtherParamValue(shopCode + ";" + staffManageCode + ";");
            List<ImSearchBean> listStaff = getExtractListOwner(imSearchBean);
            if (listStaff == null || listStaff.isEmpty()) {
                req.setAttribute(REQUEST_MESSAGE, "payDeposit.error.invalidShopCode");
                pageForward = REPORT_REVENUE_BOUGHT_BY_COLLABORATOR;
                log.info("End method reportRevenueBoughtByCollaborator of ReportRevenueBoughtByCollaboratorDAO");
                return pageForward;
            }

            ownerCode = listStaff.get(0).getCode();
            ownerName = listStaff.get(0).getName();

        }


        // kiem tra ngay hop le
        Date fromDate;
        Date toDate;
        pageForward = REPORT_REVENUE_BOUGHT_BY_COLLABORATOR;
        try {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        } catch (Exception ex) {
//            req.setAttribute("displayResultMsgClient", "Từ ngày chưa chính xác");
            req.setAttribute("message", "ERR.RET.024");
            return pageForward;
        }
        try {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            //toDate = DateTimeUtils.addDate(toDate, 1);
        } catch (Exception ex) {
//            req.setAttribute("displayResultMsgClient", "Đến ngày không chính xác");
            req.setAttribute("message", "ERR.RET.025");
            return pageForward;
        }
        if (fromDate.after(toDate)) {
//            req.setAttribute("displayResultMsgClient", "Từ ngày không được lớn hơn đến ngày");
            req.setAttribute("message", "ERR.RET.026");
            return pageForward;
        }
        if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear() != fromDate.getYear()) {
            req.setAttribute("message", "stock.report.impExp.error.fromDateToDateNotSame");
            return pageForward;
        }


        ViettelMsg request = new OriginalViettelMsg();



        request.set("FROM_DATE", strFromDate);
        request.set("TO_DATE", strToDate);
        request.set("USER_NAME", userToken.getLoginName());
        request.set("USER_FULL_NAME", userToken.getFullName());

        request.set("SHOP_ID", reportRevenueForm.getShopId());
        request.set("SHOP_CODE", shopCode);
        request.set("SHOP_NAME", shopName);
        request.set("OWNER_CODE", ownerCode);
        request.set("OWNER_NAME", ownerName);
        request.set("OWNER_NAME", ownerName);
        request.set("STAFF_ID", staffManageId);
        request.set("STAFF_CODE", staffManageCode);
        request.set("STAFF_NAME", staffManageName);
        request.set("PREFIX_OBJECT_CODE", channelTypeShop.getPrefixObjectCode());
        request.set("REPORT_TYPE", reportRevenueForm.getReportType());
        request.set("CHANNEL_TYPE_ID", channelTypeId);

        if (reportRevenueForm.getReportSimple() != null && reportRevenueForm.getReportSimple()) {
            request.set("REPORT_SIMPLE", "TRUE");
        }

        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_REVENUE_BOUGHT_BY_COLLABORATOR);

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
        pageForward = REPORT_REVENUE_BOUGHT_BY_COLLABORATOR;
        log.info("End method reportRevenueBoughtByCollaborator of ReportRevenueDAO");
        return pageForward;

    }

    /**
     *
     * tamdt1, 24/06/2009 kiem tra cac dieu kien hop le truoc khi xuat bao cao
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
        this.reportRevenueForm.setShopId(tmpList1.get(0).getShopId());

        //kiem tra tinh hop le cua goodCode: phai ton tai stockModel hoac saleServices
        String strGoodsCode = this.reportRevenueForm.getGoodsCode();
        if (strGoodsCode != null && !strGoodsCode.trim().equals("")) {
            List listParameter3 = new ArrayList();
            StringBuffer strQuery3 = new StringBuffer("from StockModel a ");
            strQuery3.append("where 1 = 1 ");

            strQuery3.append("and lower(a.stockModelCode) = ? ");
            listParameter3.add(strGoodsCode.trim().toLowerCase());

            strQuery1.append("and status = ? ");
            listParameter1.add(Constant.STATUS_ACTIVE);

            strQuery1.append("and rownum < ? ");
            listParameter1.add(50L);

            Query query3 = getSession().createQuery(strQuery3.toString());
            for (int i = 0; i < listParameter3.size(); i++) {
                query3.setParameter(i, listParameter3.get(i));
            }

            List<StockModel> tmpList3 = query3.list();
            if (tmpList3 != null && tmpList3.size() > 0) {
                this.reportRevenueForm.setStockModelId(tmpList3.get(0).getStockModelId());
            } else {
                List listParameter4 = new ArrayList();
                StringBuffer strQuery4 = new StringBuffer("from SaleServices a ");
                strQuery4.append("where 1 = 1 ");


                strQuery4.append("and lower(a.code) = ? ");
                listParameter4.add(strGoodsCode.trim().toLowerCase());


                strQuery4.append("and status = ? ");
                listParameter4.add(Constant.STATUS_ACTIVE);

                strQuery4.append("and rownum < ? ");
                listParameter4.add(50L);

                Query query4 = getSession().createQuery(strQuery4.toString());
                for (int i = 0; i < listParameter4.size(); i++) {
                    query4.setParameter(i, listParameter4.get(i));
                }

                List<SaleServices> tmpList4 = query4.list();
                if (tmpList4 != null && tmpList4.size() > 0) {
                    this.reportRevenueForm.setStockModelId(tmpList4.get(0).getSaleServicesId());
                } else {
                    req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.goodsNotExist");
                    return false;
                }
            }
        }

        return true;
    }

    /**
     *
     * author tamdt1 date: 24/06/2009 lay du lieu cho cac combobox
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
     * author tamdt1 date: 26/06/2010 kiem tra 1 shop co p la nut la khong
     *
     */
    private boolean isShopWithoutChild(Long shopId) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("");
        strQuery1.append("select count(*) from Shop a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and a.parentShopId = ? ");
        listParameter1.add(shopId);

        strQuery1.append("and a.channelTypeId = ? ");
        listParameter1.add(Constant.CHANNEL_TYPE_SHOP);

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && !tmpList1.isEmpty()) {
            Long tmpCount = (Long) tmpList1.get(0);
            if (tmpCount != null && tmpCount.compareTo(0L) > 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public Long getStaffId(String staffCode) throws Exception {
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff.getStaffId();
        }
        return 0L;

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
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_SHOP);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

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
    public List<ImSearchBean> getShopName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_SHOP);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);


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
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.status = 1 and b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);



        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
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

        return listImSearchBean;
    }

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose : lay danh sach cac nhan vien
     * thuoc mot don vi
     *
     */
    public List<ImSearchBean> getStaffName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        } else {
            return listImSearchBean;
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

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose : lay danh sach cac nhan vien
     * thuoc mot don vi
     *
     */
    public List<ImSearchBean> getListOwner(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");
        strQuery1.append("and a.staffOwnerId is not null ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_NOT_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);


        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String[] arrOtherParamValue = imSearchBean.getOtherParamValue().trim().split(";");

            /* 120503 - TrongLV - NEU KHONG CHON MA NVQL THI TIM THEO SHOP_CODE */
            if (arrOtherParamValue != null && arrOtherParamValue.length == 2
                    && arrOtherParamValue[0] != null && !arrOtherParamValue[0].trim().equals("") //                    && arrOtherParamValue[1] != null && !arrOtherParamValue[1].trim().equals("")
                    ) {
                String shopCode = arrOtherParamValue[0].toLowerCase();
                String staffManageCode = arrOtherParamValue[1].toLowerCase();
                strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
                listParameter1.add(shopCode.trim().toLowerCase());
                try {
                    if (staffManageCode != null && !staffManageCode.equals("")) {
                        Long staffId = getStaffId(staffManageCode);
                        strQuery1.append("and a.staffOwnerId = ? ");
                        listParameter1.add(staffId);
                    }
                } catch (Exception ex) {
                }


            } else {
                return listImSearchBean;
            }

        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
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

        return listImSearchBean;
    }

    public List<ImSearchBean> getOwnerName(ImSearchBean imSearchBean) {

        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");
        strQuery1.append("and a.staffOwnerId is not null ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_NOT_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);


        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String[] arrOtherParamValue = imSearchBean.getOtherParamValue().trim().split(";");

            /* 120503 - TrongLV - NEU KHONG CHON MA NVQL THI TIM THEO SHOP_CODE */
            if (arrOtherParamValue != null && arrOtherParamValue.length == 2
                    && arrOtherParamValue[0] != null && !arrOtherParamValue[0].trim().equals("") //                    && arrOtherParamValue[1] != null && !arrOtherParamValue[1].trim().equals("")
                    ) {
                String shopCode = arrOtherParamValue[0].toLowerCase();
                String staffManageCode = arrOtherParamValue[1].toLowerCase();
                strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
                listParameter1.add(shopCode.trim().toLowerCase());
                try {
                    if (staffManageCode != null && !staffManageCode.equals("")) {
                        Long staffId = getStaffId(staffManageCode);
                        strQuery1.append("and a.staffOwnerId = ? ");
                        listParameter1.add(staffId);
                    }
                } catch (Exception ex) {
                }

            } else {
                return listImSearchBean;
            }

        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }


//        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
//            String otherParam = imSearchBean.getOtherParamValue().trim();
//            int index = otherParam.indexOf(";");
//            int index1 = otherParam.indexOf(";", index + 1);
//            if (index == 0 || index1 == 0) {
//                return listImSearchBean;
//            } else {
//                String shopCode = otherParam.substring(0, index).toLowerCase();
//                String staffManageCode = otherParam.substring(index + 1, index1).toLowerCase();
//                strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
//                listParameter1.add(shopCode.trim().toLowerCase());
//                try {
//                    if (staffManageCode != null && !staffManageCode.equals("")) {
//                        Long staffId = getStaffId(staffManageCode);
//                        strQuery1.append("and a.staffOwnerId = ? ");
//                        listParameter1.add(staffId);
//                    }
//                } catch (Exception ex) {
//                }
//            }
//        } else {
//            //truong hop chua co shop -> tra ve chuoi rong
//            return listImSearchBean;
//        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "");
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

    /**
     *
     * author : tutm1 date : 01/08/2011 purpose : lay danh sach cac kho (chinh
     * xac)
     *
     */
    public List<ImSearchBean> getExtractListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_SHOP);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
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
     * author : tamdt1 date : 18/11/2009 purpose : lay chinh xac danh sach cac
     * nhan vien thuoc mot don vi
     *
     */
    public List<ImSearchBean> getExtractListStaff(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);



        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
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

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose : lay chinh xac danh sach cac
     * dai ly theo nhan vien quan ly va cua hang
     *
     */
    public List<ImSearchBean> getExtractListOwner(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");
        strQuery1.append("and a.staffOwnerId is not null ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_NOT_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);


        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");
            int index1 = otherParam.indexOf(";", index + 1);
            if (index == 0 || index1 == 0) {
                return listImSearchBean;
            } else {
                String shopCode = otherParam.substring(0, index).toLowerCase();
                String staffManageCode = otherParam.substring(index + 1, index1).toLowerCase();
                strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
                listParameter1.add(shopCode.trim().toLowerCase());
                try {
                    if (staffManageCode != null && !staffManageCode.equals("")) {
                        Long staffId = getStaffId(staffManageCode);
                        strQuery1.append("and a.staffOwnerId = ? ");
                        listParameter1.add(staffId);
                    }
                } catch (Exception ex) {
                }
            }
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
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
