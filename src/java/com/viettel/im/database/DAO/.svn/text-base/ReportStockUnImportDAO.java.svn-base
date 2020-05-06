/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.DAO;

/**
 *
 * @author tronglv
 */


import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.im.client.form.ReportStockUnImportForm;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
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

public class ReportStockUnImportDAO extends BaseDAOAction {
    private static final Log log = LogFactory.getLog(ReportDepositDAO.class);

    private ReportStockUnImportForm reportStockUnImportForm = new ReportStockUnImportForm();

    private static final String PAGE_FORWARD_REPORT = "pageReport";
    
    private String pageForward;

    public ReportStockUnImportForm getReportStockUnImportForm() {
        return reportStockUnImportForm;
    }

    public void setReportStockUnImportForm(ReportStockUnImportForm reportStockUnImportForm) {
        this.reportStockUnImportForm = reportStockUnImportForm;
    }



    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        pageForward = PAGE_FORWARD_REPORT;
        
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        reportStockUnImportForm.setToDate(dateFomat.format(cal.getTime()));
        //cal.add(Calendar.DAY_OF_MONTH, -10); // substract 1 month
        reportStockUnImportForm.setFromDate(dateFomat.format(cal.getTime()));

        reportStockUnImportForm.setShopCode(userToken.getShopCode());
        reportStockUnImportForm.setShopName(userToken.getShopName());

        reportStockUnImportForm.setReportType("1");

        return pageForward;
        
    }




    public String exportReport() throws Exception
    {
        HttpServletRequest req = getRequest();
        pageForward = PAGE_FORWARD_REPORT;
        try
        {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            Shop shop = new Shop();
            Staff staff = new Staff();

            String shopCode = reportStockUnImportForm.getShopCode();
            if (shopCode == null || "".equals(shopCode.trim())) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Chưa chọn mã đơn vị!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.052");
                return pageForward;
            }

            List lstParam = new ArrayList();
            StringBuffer queryString = new StringBuffer();
            queryString.append(" from Shop where lower(shopCode) = ? and status = ?  ");
            lstParam.add(shopCode.trim().toLowerCase());
            lstParam.add(Constant.STATUS_USE);
            queryString.append("    AND (shopPath LIKE (?) escape '$' OR shopId = ?) ");
            lstParam.add("%$_" + userToken.getShopId().toString() + "$_%");
            lstParam.add(userToken.getShopId());

            Query query = getSession().createQuery(queryString.toString());
            for (int i = 0; i < lstParam.size(); i++) {
                query.setParameter(i, lstParam.get(i));
            }
            List<Shop> listShop = query.list();
            if (listShop == null || listShop.size() == 0) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Mã đơn vị không chính xác!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.053");
                return pageForward;
            }
            shop = listShop.get(0);

            String staffCode = reportStockUnImportForm.getStaffCode();
            if (staffCode != null && !staffCode.trim().equals("")){
                lstParam = new ArrayList();
                queryString = new StringBuffer();
                queryString.append(" from Staff where lower(staffCode) = ? and status = ? and shopId = ? ");
                lstParam.add(staffCode.trim().toLowerCase());
                lstParam.add(Constant.STATUS_USE);
                lstParam.add(shop.getShopId());

                query = getSession().createQuery(queryString.toString());
                for (int i = 0; i < lstParam.size(); i++) {
                    query.setParameter(i, lstParam.get(i));
                }
                List<Staff> listStaff = query.list();
                if (listStaff == null || listStaff.size() == 0) {
//                    req.setAttribute(Constant.RETURN_MESSAGE, "Mã nhân viên không chính xác!");
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.054");
                    return pageForward;
                }
                staff = listStaff.get(0);

            }

            String sFromDate = reportStockUnImportForm.getFromDate();
            String sToDate = reportStockUnImportForm.getToDate();
            if(sFromDate == null || "".equals(sFromDate.trim()))
            {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Chưa chọn từ ngày!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.055");
                return pageForward;
            }
            if(sToDate == null || "".equals(sToDate.trim()))
            {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Chưa chọn đến ngày!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.056");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try
            {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            }catch(Exception ex)
            {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Từ ngày chưa chính xác");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.024");
                return pageForward;
            }
            try
            {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            }catch(Exception ex)
            {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Ðến ngày không chính xác!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.057");
                return pageForward;
            }
            if(fromDate.after(toDate))
            {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Từ ngày không được lớn hơn đến ngày!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.058");
                return pageForward;
            }

            if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear() != fromDate.getYear()) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "stock.report.impExp.error.fromDateToDateNotSame");
                    return pageForward;
                }
            
            ViettelMsg request = new OriginalViettelMsg();
            request.set("FROM_DATE", sFromDate);
            request.set("TO_DATE", sToDate);
            request.set("USER_CODE", userToken.getLoginName());
            request.set("REPORT_TYPE", reportStockUnImportForm.getReportType());
            request.set("SHOP_ID", shop.getShopId());
            request.set("SHOP_NAME", shop.getName());
            if (staff != null && staff.getStaffId() != null){
                request.set("STAFF_ID", staff.getStaffId());
                request.set("STAFF_NAME", staff.getName());
            }

            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_STOCK_UNIMPORT);

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if(response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString()))
            {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportStockImportPendingMessage", "reportRevenue.reportRevenueMessage");
            }else
            {
                req.setAttribute(Constant.RETURN_MESSAGE, "report.warning.noResult");
            }
//
        }catch(Exception ex)
        {
            req.setAttribute(Constant.RETURN_MESSAGE, "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        return pageForward;
    }
}

