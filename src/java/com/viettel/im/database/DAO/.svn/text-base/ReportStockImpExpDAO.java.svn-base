package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ReportStockImpExpForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockKit;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

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
public class ReportStockImpExpDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportStockImpExpDAO.class);
    public String pageForward;
    private ReportStockImpExpForm reportStockImpExpForm = new ReportStockImpExpForm();
    private List listStockModel = new ArrayList();

    public ReportStockImpExpForm getReportStockImpExpForm() {
        return reportStockImpExpForm;
    }

    public void setReportStockImpExpForm(ReportStockImpExpForm reportStockImpExpForm) {
        this.reportStockImpExpForm = reportStockImpExpForm;
    }

    public List getListStockModel() {
        return listStockModel;
    }

    public void setListStockModel(List listStockModel) {
        this.listStockModel = listStockModel;
    }

    /*
     * @Author: ThanhNC
     * @Date created: 03/08/2009
     * @Description: Khoi tao trang bao cao xuat nhap ton
     */
    public String preparePage() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportStockImpExpDAO.preparePage");
        initData();
        pageForward = "reportStockExpImp";

        log.debug("# End method ReportStockImpExpDAO.preparePage");
        return pageForward;
    }

    private void initData() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
        req.setAttribute("lstTelecomService", lstTelecomService);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        reportStockImpExpForm.setToDate(sdf.format(cal.getTime()));
        //cal.add(Calendar.MONTH, -1); // substract 1 month
        reportStockImpExpForm.setFromDate(sdf.format(cal.getTime()));
        reportStockImpExpForm.setShopCode(userToken.getShopCode());
        reportStockImpExpForm.setShopName(userToken.getShopName());
    }

    /*
     * @Author: ThanhNC
     * @Date created: 03/08/2009
     * @Description: autocomplete select shop
     */
//    public String autoSelectStaff() throws Exception {
//        log.debug("# Begin method ReportStockImpExpDAO.autoSelectStaff");
//        HttpServletRequest req = getRequest();
//        pageForward = "autoSelectStaff";
//        String shopCode = reportStockImpExpForm.getShopCode();
//        String staffCode = req.getParameter("reportStockImpExpForm.staffCode");
//        if (shopCode == null || "".equals(shopCode.trim()) || staffCode == null || "".equals(staffCode.trim())) {
//            return pageForward;
//        }
//
//        String SQL_SELECT = "from Staff where lower(staffCode) like  ? and status = ? " +
//                " and shopId in (select shopId from Shop where lower(shopCode) = ? and status =? )";
//        Query q = getSession().createQuery(SQL_SELECT);
//        q.setParameter(0, staffCode.toLowerCase() + "%");
//        q.setParameter(1, Constant.STATUS_USE);
//        q.setParameter(2, shopCode.toLowerCase());
//        q.setParameter(3, Constant.STATUS_USE);
//        List<Staff> lst = q.list();
//        for (Staff staff : lst) {
//            listStaff.put(staff.getName(), staff.getStaffCode());
//        }
//        log.debug("# End method ReportStockImpExpDAO.autoSelectStaff");
//        return pageForward;
//    }
    /*
     * @Author: ThanhNC
     * @Date created: 03/08/2009
     * @Description: List danh sach stockModel khi chon telecom service
     */
    public String selectTelecomService() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportStockImpExpDAO.selectTelecomService");
        HttpServletRequest req = getRequest();
        pageForward = "selectTelecomService";
        String telecomService = req.getParameter("reportStockImpExpForm.telecomServiceId");
        if (telecomService == null || "".equals(telecomService.trim())) {
            return pageForward;
        }
        Long telecomServiceId = Long.valueOf(telecomService);
        String SQL_SELECT = "select stockModelId, name from StockModel where telecomServiceId = ? and status = ? ";
        Query q = getSession().createQuery(SQL_SELECT);
        q.setParameter(0, telecomServiceId);
        q.setParameter(1, Constant.STATUS_USE);
        List lst = q.list();

        String[] header = {
            "", "--Chọn mặt hàng--"
        };
        listStockModel.add(header);
        listStockModel.addAll(lst);
        log.debug("# End method ReportStockImpExpDAO.selectTelecomService");
        return pageForward;
    }
    /*
     * @Author: ThanhNC
     * @Date created: 03/08/2009
     * @Description: Xuat bao cao xuat nhap ton
     */

    public String exportStockImpExpReport() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportStockImpExpDAO.exportStockImpExpReport");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        pageForward = "reportStockExpImp";
        try {
            TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
            telecomServiceDAO.setSession(this.getSession());
            List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
            req.setAttribute("lstTelecomService", lstTelecomService);

            Long telecomServiceId = reportStockImpExpForm.getTelecomServiceId();
            if (telecomServiceId != null && telecomServiceId > 0) {
                String SQL_SELECT = " from StockModel where telecomServiceId = ? and status = ? ";
                Query q = getSession().createQuery(SQL_SELECT);
                q.setParameter(0, telecomServiceId);
                q.setParameter(1, Constant.STATUS_USE);
                List lst = q.list();

                String[] header = {
                    "", "--Chọn mặt hàng--"
                };
                listStockModel.add(header);
                listStockModel.addAll(lst);
                req.setAttribute("lstStockModel", listStockModel);
            }
            String shopCode = reportStockImpExpForm.getShopCode();
            String staffCode = reportStockImpExpForm.getStaffCode();
            if (shopCode == null || "".equals(shopCode.trim())) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.noShopCode");
                return pageForward;
            }
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
            if (shop == null) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.shopCodeNotValid");
                return pageForward;
            }
            Staff staff = null;
            if (staffCode != null && !"".equals(staffCode.trim())) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                staff = staffDAO.findStaffAvailableByStaffCode(staffCode);
                if (staff == null) {
                    req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.staffCodeNotValid");
                    return pageForward;
                }
                if (!staff.getShopId().equals(shop.getShopId())) {
                    req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.staffNotInShop");
                    return pageForward;
                }
            }
            String sFromDate = reportStockImpExpForm.getFromDate();
            String sToDate = reportStockImpExpForm.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.noFromDate");
                return pageForward;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.noToDate");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.fromDateNotValid");
                return pageForward;
            }
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.toDateNotValid");
                return pageForward;
            }
            if (fromDate.after(toDate)) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.fromDateToDateNotValid");
                return pageForward;
            }
            if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear()!= fromDate.getYear()) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.fromDateToDateNotSame");
                return pageForward;
            }  

            /*TuanPV - 25/02/2010 - Comment để chuyển sang mô hình ReportServer*/
            /*
            StringBuffer SUB_SQL_SELECT_REMAIN_QUANTITY = new StringBuffer(
            " ( SELECT (SUM (imp_quantity)  - " +
            "        SUM (sale_quantity) - " +
            "        SUM (free_exp_quantity) - " +
            "        SUM (other_exp_quantity) ) AS remain_quantity " +
            " FROM stock_daily_rpt  WHERE created_date < ? ");
            String POS_SUB_SQL_SELECT_REMAIN_QUANTITY = " ) AS remainQuantity ";

            StringBuffer SQL_SELECT = new StringBuffer(
            " SELECT vSgrt.stock_type_id as stockTypeId,vSgrt.stock_type_name as stockTypeName," +
            " vSgrt.stock_model_id as stockModelId, vSgrt.stock_model_code as stockModelCode , " +
            " vSgrt.stock_model_name as stockModelName, " +
            " SUM(vSgrt.imp_quantity) AS impQuantity, sum(vSgrt.sale_quantity) AS saleQuantity, " +
            " SUM(vSgrt.free_exp_quantity) AS freeExpQuantity, SUM(vSgrt.other_exp_quantity) AS otherExpQuantity ,");
            StringBuffer POS_SQL_SELECT = new StringBuffer(
            " FROM V_STOCK_GENERAL_RPT vSgrt" +
            " WHERE vSgrt.created_date >= ? and vSgrt.created_date <= ? ");
            StringBuffer SQL_GROUP_BY = new StringBuffer(
            " GROUP BY vSgrt.stock_type_id,vSgrt.stock_type_name, vSgrt.stock_model_id," +
            " vSgrt.stock_model_code, vSgrt.stock_model_name ");
            String SQL_ORDER_BY =
            " ORDER BY nlssort( vSgrt.stock_type_name, 'nls_sort=Vietnamese'), " +
            " nlssort( vSgrt.stock_model_name, 'nls_sort=Vietnamese') ";

            List parameterSubQuery = new ArrayList();
            List parameterMainQuery = new ArrayList();

            parameterSubQuery.add(fromDate);

            parameterMainQuery.add(fromDate);
            parameterMainQuery.add(toDate);

            //Bao cao theo trang thai hang
            if(reportStockImpExpForm.getStateId() != null)
            {
            SUB_SQL_SELECT_REMAIN_QUANTITY.append(" and state_id = ? ");
            parameterSubQuery.add(reportStockImpExpForm.getStateId());
            POS_SQL_SELECT.append(" and vSgrt.state_id = ? ");
            parameterMainQuery.add(reportStockImpExpForm.getStateId());
            }

            //Bao cao bao gom ca nhan vien
            if(reportStockImpExpForm.getIncludeStaff().equals(Constant.REPORT_INCLUDE_STAFF))
            {
            //Neu bao cao chi tiet bao gom ca nhan vien
            if(reportStockImpExpForm.getReportDetail().equals(Constant.REPORT_DETAIL))
            {
            if(staff != null)
            {
            SUB_SQL_SELECT_REMAIN_QUANTITY.append(" and owner_type= ? and owner_id= ? ");
            parameterSubQuery.add(Constant.OWNER_TYPE_STAFF);
            parameterSubQuery.add(staff.getStaffId());
            }else
            {
            SUB_SQL_SELECT_REMAIN_QUANTITY.append(" and owner_type= vSgrt.owner_type and owner_id= vSgrt.owner_id ");
            }
            }else
            {
            if(staff != null)
            {
            SUB_SQL_SELECT_REMAIN_QUANTITY.append(" and owner_type= ? and owner_id= ? ");
            parameterSubQuery.add(Constant.OWNER_TYPE_STAFF);
            parameterSubQuery.add(staff.getStaffId());
            }else
            {
            SUB_SQL_SELECT_REMAIN_QUANTITY.append(" and ((owner_type= ? and owner_id= ?) or " +
            " ( owner_type = ? and owner_id in (select staff_id from staff where shop_id = ? and status = ? and staff_owner_id is null ) ) ) ");
            //Khong lay cong tac vien (staff_owner_id not null ==ctv)
            parameterSubQuery.add(Constant.OWNER_TYPE_SHOP);
            parameterSubQuery.add(shop.getShopId());
            parameterSubQuery.add(Constant.OWNER_TYPE_STAFF);
            parameterSubQuery.add(shop.getShopId());
            parameterSubQuery.add(Constant.STATUS_USE);
            }
            }
            /*
            SUB_SQL_SELECT_REMAIN_QUANTITY.append(" and owner_type= vSgrt.owner_type and owner_id= vSgrt.owner_id ");
            SQL_GROUP_BY.append(" ,vSgrt.owner_type, vSgrt.owner_id, " +
            " vSgrt.owner_code , vSgrt.owner_name ");
             */
            /*
            if(staff != null)
            {
            POS_SQL_SELECT.append(" and vSgrt.owner_type=? and vSgrt.owner_id= ? ");
            parameterMainQuery.add(Constant.OWNER_TYPE_STAFF);
            parameterMainQuery.add(staff.getStaffId());

            }else
            {
            POS_SQL_SELECT.append(" and ((vSgrt.owner_type=? and vSgrt.owner_id= ?) or " +
            "   (vSgrt.owner_type=? and vSgrt.owner_id in (select staff_id from staff where shop_id = ? and status = ? and staff_owner_id is null )) ) ");
            //Khong lay cong tac vien (staff_owner_id not null ==ctv)
            parameterMainQuery.add(Constant.OWNER_TYPE_SHOP);
            parameterMainQuery.add(shop.getShopId());
            parameterMainQuery.add(Constant.OWNER_TYPE_STAFF);
            parameterMainQuery.add(shop.getShopId());
            parameterMainQuery.add(Constant.STATUS_USE);
            }
            }else
            { //Bao cao khong bao gom nhan vien
            SUB_SQL_SELECT_REMAIN_QUANTITY.append(" and owner_type= ? and owner_id= ? ");
            parameterSubQuery.add(Constant.OWNER_TYPE_SHOP);
            parameterSubQuery.add(shop.getShopId());

            POS_SQL_SELECT.append(" and vSgrt.owner_type=? and vSgrt.owner_id= ? ");
            parameterMainQuery.add(Constant.OWNER_TYPE_SHOP);
            parameterMainQuery.add(shop.getShopId());
            }

            //Bao cao theo 1 loai hang
            if(reportStockImpExpForm.getStockModelId() != null)
            {

            SUB_SQL_SELECT_REMAIN_QUANTITY.append(" and stock_model_id = ? ");
            parameterSubQuery.add(reportStockImpExpForm.getStockModelId());

            POS_SQL_SELECT.append(" and vSgrt.stock_model_id = ? ");
            parameterMainQuery.add(reportStockImpExpForm.getStockModelId());
            }else
            {
            if(reportStockImpExpForm.getTelecomServiceId() != null)
            {
            SUB_SQL_SELECT_REMAIN_QUANTITY.append(" and stock_model_id = vSgrt.stock_model_id ");

            POS_SQL_SELECT.append(" and vSgrt.stock_model_id in (select stock_model_id from stock_model where telecom_service_id = ? and status = ? ) ");
            parameterMainQuery.add(reportStockImpExpForm.getTelecomServiceId());
            parameterMainQuery.add(Constant.STATUS_USE);
            }else
            {
            SUB_SQL_SELECT_REMAIN_QUANTITY.append(" and stock_model_id = vSgrt.stock_model_id ");
            }

            }
            //Loai bao cao
            //Bao cao chi tiet --> lay them thong tin owner
            if(reportStockImpExpForm.getReportDetail().equals(Constant.REPORT_DETAIL))
            {
            SQL_SELECT.append(" vSgrt.owner_type as ownerType, vSgrt.owner_id as ownerId," +
            " vSgrt.owner_code as ownerCode, vSgrt.owner_name as ownerName, ");
            SQL_GROUP_BY.append(" ,vSgrt.owner_type, vSgrt.owner_id, " +
            " vSgrt.owner_code , vSgrt.owner_name ");
            }


            SQL_SELECT.append(SUB_SQL_SELECT_REMAIN_QUANTITY);
            SQL_SELECT.append(POS_SUB_SQL_SELECT_REMAIN_QUANTITY);
            SQL_SELECT.append(POS_SQL_SELECT);
            SQL_SELECT.append(SQL_GROUP_BY);
            SQL_SELECT.append(SQL_ORDER_BY);
            Query query;
            if(reportStockImpExpForm.getReportDetail().equals(Constant.REPORT_DETAIL))
            {
            query = getSession().createSQLQuery(SQL_SELECT.toString()).
            addScalar("stockTypeId", Hibernate.LONG).
            addScalar("stockTypeName", Hibernate.STRING).
            addScalar("stockModelId", Hibernate.LONG).
            addScalar("stockModelCode", Hibernate.STRING).
            addScalar("stockModelName", Hibernate.STRING).
            addScalar("impQuantity", Hibernate.LONG).
            addScalar("saleQuantity", Hibernate.LONG).
            addScalar("freeExpQuantity", Hibernate.LONG).
            addScalar("otherExpQuantity", Hibernate.LONG).
            addScalar("ownerType", Hibernate.LONG).
            addScalar("ownerId", Hibernate.LONG).
            addScalar("ownerCode", Hibernate.STRING).
            addScalar("ownerName", Hibernate.STRING).
            addScalar("remainQuantity", Hibernate.LONG).
            setResultTransformer(Transformers.aliasToBean(StockReportBean.class));
            }else
            {
            query = getSession().createSQLQuery(SQL_SELECT.toString()).
            addScalar("stockTypeId", Hibernate.LONG).
            addScalar("stockTypeName", Hibernate.STRING).
            addScalar("stockModelId", Hibernate.LONG).
            addScalar("stockModelCode", Hibernate.STRING).
            addScalar("stockModelName", Hibernate.STRING).
            addScalar("impQuantity", Hibernate.LONG).
            addScalar("saleQuantity", Hibernate.LONG).
            addScalar("freeExpQuantity", Hibernate.LONG).
            addScalar("otherExpQuantity", Hibernate.LONG).
            addScalar("remainQuantity", Hibernate.LONG).
            setResultTransformer(Transformers.aliasToBean(StockReportBean.class));
            }
            int idx = 0;
            //Set parameter cho sub query
            for(int i = 0; i < parameterSubQuery.size(); i++)
            {
            query.setParameter(idx, parameterSubQuery.get(i));
            idx++;
            }
            //Set parameter cho main query
            for(int i = 0; i < parameterMainQuery.size(); i++)
            {
            query.setParameter(idx, parameterMainQuery.get(i));
            idx++;
            }
            List lstStockRpt = query.list();

            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            String prefixPath = "Stock_report";

            //Giao dich xuat
            if(reportStockImpExpForm.getReportDetail().equals(Constant.REPORT_GENERAL))
            {
            templatePath = tmp + prefixPath + "_general.xls";
            filePath += prefixPath + "_general_" + date + ".xls";
            }
            if(reportStockImpExpForm.getReportDetail().equals(Constant.REPORT_DETAIL))
            {
            templatePath = tmp + prefixPath + "_detail.xls";
            filePath += prefixPath + "_detail_" + date + ".xls";
            }


            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            //set ngay tao
            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            beans.put("userCreate", userToken.getFullName());
            beans.put("fromDate", fromDate);
            beans.put("toDate", toDate);
            if(staff == null)
            {
            beans.put("shopName", shop.getName());
            beans.put("shopAddress", shop.getAddress());
            }else
            {
            beans.put("shopName", staff.getName());
            beans.put("shopAddress", shop.getName() + " - " + shop.getAddress());
            }



            beans.put("lstStockRpt", lstStockRpt);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);

            End TuanPV Comment*/

            /*TuanPV - 25/02/2010 - Gửi lệnh xuất báo cáo sang app ReportServer*/
            ViettelMsg request = new OriginalViettelMsg();
            request.set("FROM_DATE", sFromDate);
            request.set("TO_DATE", sToDate);
            request.set("USER_NAME", userToken.getLoginName());
            request.set("REPORT_TYPE", reportStockImpExpForm.getReportType());
            request.set("REPORT_DETAIL", reportStockImpExpForm.getReportDetail());

            request.set("INCLUDE_STAFF", reportStockImpExpForm.getIncludeStaff());
            if (staff != null) {
                request.set("STAFF_ID", staff.getStaffId());
                request.set("STAFF_CODE", staff.getStaffCode());
                request.set("STAFF_NAME", staff.getName());
            }

            if (shop != null) {
                request.set("SHOP_ID", shop.getShopId());
                request.set("SHOP_NAME", shop.getName());
                request.set("SHOP_ADDRESS", shop.getAddress());
            }

            request.set("STATE_ID", reportStockImpExpForm.getStateId());
            request.set("STOCK_MODEL_ID", reportStockImpExpForm.getStockModelId());
            request.set("TELECOM_SERVICE_ID", reportStockImpExpForm.getTelecomServiceId());
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_IMPORT_EXPORT_STOCK);

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null && response.get(ReportConstant.RESULT_FILE) != null && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportStockImportPendingMessage", "reportRevenue.reportRevenueMessage");
            } else {
                req.setAttribute("resultStockImpExpRpt", "report.warning.noResult");
            }

        } catch (Exception ex) {
            req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        log.debug("# End method ReportStockImpExpDAO.exportStockImpExpReport");
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.debug("# Begin method ReportStockImpExpDAO.pageNavigator");
        pageForward = "saleManagmentResult";
        return pageForward;
    }
}
