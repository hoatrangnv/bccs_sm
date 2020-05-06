/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ReportInvoiceDailyRemainBean;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.ReportInvoiceDailyRemainForm;
import java.util.List;
import java.util.ArrayList;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.BookType;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author tronglv
 */
public class ReportInvoiceDailyRemainDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReportInvoiceDAO.class);
    //dinh nghia cac hang so pageForward
    private String pageForward = "error";
    private final String PAGE_FORWARD_REPORT_INVOICE_DAILY_REMAIN = "reportInvoiceDailyRemain";
    private final String SESSION_BOOK_TYPE_LIST = "bookTypeList";
    private final String SESSION_REPORT_RESULT_LIST = "reportResultList";

    private ReportInvoiceDailyRemainForm reportInvoiceDailyRemainForm = new ReportInvoiceDailyRemainForm();

    public ReportInvoiceDailyRemainForm getReportInvoiceDailyRemainForm() {
        return reportInvoiceDailyRemainForm;
    }

    public void setReportInvoiceDailyRemainForm(ReportInvoiceDailyRemainForm reportInvoiceDailyRemainForm) {
        this.reportInvoiceDailyRemainForm = reportInvoiceDailyRemainForm;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ReportInvoiceDailyRemainDAO ...");

        HttpServletRequest req = getRequest();
        
        this.reportInvoiceDailyRemainForm = new ReportInvoiceDailyRemainForm();

        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //Chốt đến ngày
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        this.reportInvoiceDailyRemainForm.setRemainDate(sdf.format(cal.getTime()));

        BookTypeDAO bookTypeDAO = new BookTypeDAO();
        bookTypeDAO.setSession(getSession());
        List<BookType> bookTypeList = bookTypeDAO.findByStatus();
        req.getSession().setAttribute(SESSION_BOOK_TYPE_LIST, bookTypeList);

        this.reportInvoiceDailyRemainForm.setShopCode(userToken.getShopCode());
        this.reportInvoiceDailyRemainForm.setShopName(userToken.getShopName());
        this.reportInvoiceDailyRemainForm.setStaffCode(userToken.getLoginName());
        this.reportInvoiceDailyRemainForm.setStaffName(userToken.getStaffName());

        this.reportInvoiceDailyRemainForm.setReportType("1");
                
        pageForward = PAGE_FORWARD_REPORT_INVOICE_DAILY_REMAIN;

        log.info("End method preparePage of ReportInvoiceDailyRemainDAO");

        return pageForward;
    }

    public String searchInvoiceDailyRemain() throws Exception {
        try{

            pageForward = PAGE_FORWARD_REPORT_INVOICE_DAILY_REMAIN;

            HttpServletRequest req = getRequest();

            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            Long shopId = -1L;
            Long staffId = -1L;
            String shopCode = this.reportInvoiceDailyRemainForm.getShopCode();
            String staffCode = this.reportInvoiceDailyRemainForm.getStaffCode();

            //Kiem tra Shop_Code
            if (shopCode == null || shopCode.trim().equals("")){
                return pageForward;
            }
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            List lstParam = new ArrayList();
            StringBuffer queryString = new StringBuffer();
            queryString.append(" from Shop where lower(shopCode) = ? and status = ?  ");
            lstParam.add(shopCode.trim().toLowerCase());
            lstParam.add(Constant.STATUS_USE);
            queryString.append("    AND shopPath LIKE (?) escape '$'  ");
            lstParam.add("%$_" + userToken.getShopId().toString() + "%");
            Query query = getSession().createQuery(queryString.toString());
            for(int i = 0; i < lstParam.size(); i++)
            {
                query.setParameter(i, lstParam.get(i));
            }
            List<Shop> tmpList = query.list();
            if(tmpList != null && tmpList.size() > 0)
            {
                shopId = tmpList.get(0).getShopId();
            }
            else{
//                req.setAttribute("displayResultMsgClient", "Mã chi nhánh không chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.040");
                return pageForward;
            }
            
            //Kiem tra Staff_Code
            if (staffCode != null && !staffCode.trim().equals("")){
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                Staff staff = staffDAO.findStaffAvailableByStaffCode(staffCode.trim());
                if (staff != null && staff.getShopId().intValue() == shopId.intValue()){
                    staffId = staff.getStaffId();
                }
                else{
//                    req.setAttribute("displayResultMsgClient", "Mã nhân viên không chính xác");
                    req.setAttribute("displayResultMsgClient", "ERR.RET.041");
                    return pageForward;
                }
            }

            //Kiem tra chot den ngay
            String sRemainDate =  this.reportInvoiceDailyRemainForm.getRemainDate();
            if (sRemainDate == null || "".equals(sRemainDate.trim())) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Chưa chọn từ ngày");
                req.setAttribute("displayResultMsgClient", "ERR.RET.022");
                return pageForward;
            }
            Date remainDate = getSysdate();
            Date currentDate = getSysdate();
            try {
                remainDate = DateTimeUtils.convertStringToDate(sRemainDate);
            } catch (Exception ex) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Từ ngày chưa chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.024");
                return pageForward;
            }
            if (remainDate.after(currentDate)) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Từ ngày không được lớn hơn ngày hiện tại");
                req.setAttribute("displayResultMsgClient", "ERR.RET.042");
                return pageForward;
            }

            //Loai hoa don
            String serialCode = this.reportInvoiceDailyRemainForm.getSerialCode();

            String reportType = this.reportInvoiceDailyRemainForm.getReportType();
            if (reportType == null || reportType.trim().equals("")){
                reportType = "1";
            }

            //Danh sach ket qua
            lstParam = new ArrayList();
            queryString = new StringBuffer();
            queryString.append("SELECT shop_name AS shopName ");
            queryString.append("    ,obj_name AS objName ");
            queryString.append("    ,block_no AS blockNo ");
            queryString.append("    ,serial_no AS serialNo ");
            queryString.append("    ,from_invoice AS fromInvoice ");
            queryString.append("    ,to_invoice AS toInvoice ");
            queryString.append("    ,quantity AS quantity ");
            queryString.append("FROM V_invoice_daily_remain WHERE 1=1 ");
            if (remainDate != null){
                queryString.append("    and remain_date = ? ");
                lstParam.add(remainDate);
            }
            if (shopId != null && shopId.intValue() >=0){
                if (reportType.trim().equals("1")){
                    queryString.append("    and shop_id = ? ");
                    lstParam.add(shopId);
                }
                else if (reportType.trim().equals("2")){
                    queryString.append("    and shop_path LIKE (?) ESCAPE '$'  ");
                    lstParam.add("%$_" + shopId.toString() + "%");
                }
                if (reportType.trim().equals("3")){
                    queryString.append("    (and shop_id = ? or parent_shop_id = ?) ");
                    lstParam.add(shopId);
                    lstParam.add(shopId);
                }
            }
            if (staffId != null && staffId.intValue() >=0){
                queryString.append("    and staff_id = ? ");
                lstParam.add(staffId);
            }

            if (serialCode != null && !serialCode.trim().equals("")){
                queryString.append("    and serial_no = ? ");
                lstParam.add(serialCode);
            }            
            queryString.append("ORDER BY shopName, objName ");
            Query queryObject = getSession().createSQLQuery(queryString.toString()).
                                addScalar("shopName", Hibernate.STRING).
                                addScalar("objName", Hibernate.STRING).
                                addScalar("blockNo", Hibernate.STRING).
                                addScalar("serialNo", Hibernate.STRING).
                                addScalar("fromInvoice", Hibernate.LONG).
                                addScalar("toInvoice", Hibernate.LONG).
                                addScalar("quantity", Hibernate.LONG).
                                setResultTransformer(Transformers.aliasToBean(ReportInvoiceDailyRemainBean.class));
            for (int idx = 0; idx < lstParam.size(); idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }
            List reportResultList = queryObject.list();

            //Thong bao ket qua tim kiem
            req.setAttribute(SESSION_REPORT_RESULT_LIST, reportResultList);

        }catch(Exception ex){
            pageForward = "error";
        }
        return pageForward;
    }


    public String exportInvoiceDailyRemain() throws Exception {
        try{

            pageForward = PAGE_FORWARD_REPORT_INVOICE_DAILY_REMAIN;

            HttpServletRequest req = getRequest();

            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            Shop shop = new Shop();
            Staff staff = new Staff();
            String shopCode = this.reportInvoiceDailyRemainForm.getShopCode();
            String staffCode = this.reportInvoiceDailyRemainForm.getStaffCode();

            //Kiem tra Shop_Code
            if (shopCode == null || shopCode.trim().equals("")){
                return pageForward;
            }
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            List lstParam = new ArrayList();
            StringBuffer queryString = new StringBuffer();
            queryString.append(" from Shop where lower(shopCode) = ? and status = ?  ");
            lstParam.add(shopCode.trim().toLowerCase());
            lstParam.add(Constant.STATUS_USE);
            queryString.append("    AND (shopPath LIKE (?) escape '$' OR shopId = ?) ");
            lstParam.add("%$_" + userToken.getShopId().toString() + "$_%");
            lstParam.add(userToken.getShopId());
            Query query = getSession().createQuery(queryString.toString());
            for(int i = 0; i < lstParam.size(); i++)
            {
                query.setParameter(i, lstParam.get(i));
            }
            List<Shop> tmpList = query.list();
            if(tmpList != null && tmpList.size() > 0)
            {
                shop = tmpList.get(0);
            }
            else{
//                req.setAttribute("displayResultMsgClient", "Mã chi nhánh không chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.040");
                return pageForward;
            }

            //Kiem tra Staff_Code
            if (staffCode != null && !staffCode.trim().equals("")){
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                Staff tmpStaff = staffDAO.findStaffAvailableByStaffCode(staffCode.trim());
                if (tmpStaff != null && shop != null && tmpStaff.getShopId().intValue() == shop.getShopId().intValue()){
                    staff = tmpStaff;
                }
                else{
//                    req.setAttribute("displayResultMsgClient", "Mã nhân viên không chính xác");
                    req.setAttribute("displayResultMsgClient", "ERR.RET.041");
                    return pageForward;
                }
            }

            //Kiem tra chot den ngay
            String sRemainDate =  this.reportInvoiceDailyRemainForm.getRemainDate();
            if (sRemainDate == null || "".equals(sRemainDate.trim())) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Chưa chọn từ ngày");
                req.setAttribute("displayResultMsgClient", "ERR.RET.022");
                return pageForward;
            }
            Date remainDate = getSysdate();
            Date currentDate = getSysdate();
            try {
                remainDate = DateTimeUtils.convertStringToDate(sRemainDate);
            } catch (Exception ex) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Từ ngày chưa chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.024");
                return pageForward;
            }
            if (remainDate.after(currentDate)) {
//                req.setAttribute("displayResultMsgClient", "Từ ngày không được lớn hơn ngày hiện tại");
                req.setAttribute("displayResultMsgClient", "ERR.RET.042");
                return pageForward;
            }

            //Loai hoa don
            String serialCode = this.reportInvoiceDailyRemainForm.getSerialCode();

            String reportType = this.reportInvoiceDailyRemainForm.getReportType();
            if (reportType == null || reportType.trim().equals("")){
                reportType = "1";
            }
            
            //Danh sach ket qua
            /*            
             */
            
            ViettelMsg request = new OriginalViettelMsg();
            request.set("USER_CODE", userToken.getLoginName());
            request.set("USER_NAME", userToken.getStaffName());

            if (shop != null && shop.getShopId() != null){
                request.set("SHOP_ID", shop.getShopId());
                request.set("SHOP_NAME", shop.getName());
            }

            if (staff != null && staff.getStaffId() != null){
                request.set("STAFF_ID", staff.getStaffId());
                request.set("STAFF_NAME", shop.getName());
            }

            request.set("SERIAL_CODE", serialCode);
            request.set("REMAIN_DATE", remainDate);
            request.set("REPORT_TYPE", reportType);

            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_INVOICE_DAILY_REMAIN);

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if(response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString()))
            {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportAccountMessage", "reportRevenue.reportRevenueMessage");
            }else
            {
                req.setAttribute("displayResultMsgClient", "report.warning.noResult");
            }

            //Thong bao ket qua tim kiem
            /*
            req.setAttribute(SESSION_REPORT_RESULT_LIST, reportResultList);
             */

        }catch(Exception ex){
            pageForward = "error";
        }
        return pageForward;
    }

}
