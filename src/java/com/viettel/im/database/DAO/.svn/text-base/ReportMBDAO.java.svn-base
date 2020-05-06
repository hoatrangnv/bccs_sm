/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.GeneralPosReportBean;
import com.viettel.im.client.bean.ReportMBBean;
import com.viettel.im.client.form.ReportMBForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.CardTypePos;
import com.viettel.im.database.BO.GeneralPosReportBO;
import com.viettel.im.database.BO.MapShopTid;
import com.viettel.im.database.BO.UserToken;
import com.viettel.payment.API.InitDatabaseServer;
import com.viettel.payment.API.ReportDestroyPosService;
import com.viettel.payment.client.bean.ReportPaymentPosBean;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

/**
 *
 * @author tuanv
 */
public class ReportMBDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportMBDAO.class);
    static private String PAGE_REPORT_MB01 = "reportMB01";
    static private String PAGE_REPORT_MB02 = "reportMB02";
    static private String PAGE_REPORT_MB03 = "reportMB03";
    static private String PAGE_REPORT_MB04 = "reportMB04";
    static private String PAGE_REPORT_MB05 = "reportMB05";
    static private String PAGE_REPORT_MB07 = "reportMB07";
    static private String PAGE_REPORT_MB08 = "reportMB08";
    static private String PAGE_REPORT_MB09 = "reportMB09";
    static private String LIST_ITEMS = "lstCards";
    public String pageForward;
    private ReportMBForm form = new ReportMBForm();
    private static Connection connection = null;//connection toi payment

    public ReportMBForm getForm() {
        return form;
    }

    public void setForm(ReportMBForm form) {
        this.form = form;
    }

    public String preparePage07() throws Exception {
        log.debug("# Begin method preparePage");
        HttpServletRequest req = getRequest();
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        form.setToDate(dateFomat.format(cal.getTime()));
        cal.add(Calendar.MONTH, -1);
        form.setFromDate(dateFomat.format(cal.getTime()));
        log.debug("# End method preparePage");
        return PAGE_REPORT_MB07;
    }

    /**
     * @author: TuanNV
     * Yêu cầu hủy giao dịch Viettel
     * @param startDate
     * @param endDate
     * @param cardType
     * @return List<SaleTransPost>
     * @throws java.lang.Exception
     */
    public String reportMB07() throws Exception {
        HttpServletRequest req = getRequest();
        try {
//            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            String sFromDate = form.getFromDate();
            String sToDate = form.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                req.setAttribute("displayResultMsgClient", "Chưa chọn ngày hủy giao dịch");
                return PAGE_REPORT_MB07;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                req.setAttribute("displayResultMsgClient", "Chưa chọn đến ngày");
                return PAGE_REPORT_MB07;
            }
            Date startDate;
            Date endDate;
            Date sysDate = DateTimeUtils.convertStringToDate(DateTimeUtils.convertDateToString(DateTimeUtils.getSysDate()));

            try {
                startDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                req.setAttribute("displayResultMsgClient", "Từ ngày chưa chính xác");
                return PAGE_REPORT_MB07;
            }
            try {
                endDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                req.setAttribute("displayResultMsgClient", "Đến ngày không chính xác");
                return PAGE_REPORT_MB07;
            }
            if (startDate.after(endDate)) {
                req.setAttribute("displayResultMsgClient", "Từ ngày không được lớn hơn đến ngày");
                return PAGE_REPORT_MB07;
            }

            if (startDate.after(sysDate)) {
                req.setAttribute("displayResultMsgClient", "Từ ngày không được lớn hơn ngày hiện tại");
                return PAGE_REPORT_MB07;
            }

            String reportType = form.getReportType();
            if (reportType == null || "".equals(reportType.trim())) {
                req.setAttribute("displayResultMsgClient", "Chưa chọn loại báo cáo");
                return PAGE_REPORT_MB07;
            }

            ReportDestroyPosService reportDestroyPosService = new ReportDestroyPosService();
            if (connection == null) {
                connection = InitDatabaseServer.getConnection();
            }

            List<ReportPaymentPosBean> listReport = new ArrayList<ReportPaymentPosBean>();
            //lay danh sach may cua cua hang
            String[] tidList = searchMapShopTid();
            //goi ham bao cao ben payment
            listReport = reportDestroyPosService.getListDestroyPaymentPos(connection, startDate, endDate, null, reportType, tidList);


            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String prefixPath = "BCDS_09";
            String templatePath = tmp + prefixPath + ".xls";
            filePath += prefixPath + date + ".xls";

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            //set ngay tao
            beans.put("dateCreate", DateTimeUtils.convertDateToString_tuannv(DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate())));
            beans.put("fromDate", DateTimeUtils.convertDateToString_tuannv(startDate));
            req.setAttribute("filePath", filePath);

            beans.put(LIST_ITEMS, listReport);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);

            //Thong bao len jsp
            req.setAttribute("filePath", filePath);
            req.setAttribute("displayResultMsgClient", "report.stocktrans.error.successMessage");


        } catch (Exception ex) {
            req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        return PAGE_REPORT_MB07;

    }

    public String preparePage08() throws Exception {
        log.debug("# Begin method preparePage");
        HttpServletRequest req = getRequest();
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        form.setToDate(dateFomat.format(cal.getTime()));
        cal.add(Calendar.MONTH, -1);
        form.setFromDate(dateFomat.format(cal.getTime()));
        log.debug("# End method preparePage");
        return PAGE_REPORT_MB08;
    }

    /**
     * @author: TuanNV
     * Yêu cầu hủy giao dịch Viettel
     * @param startDate
     * @param endDate
     * @param cardType
     * @return List<SaleTransPost>
     * @throws java.lang.Exception
     */
    public String reportMB08() throws Exception {
        HttpServletRequest req = getRequest();
        try {
//            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            String sFromDate = form.getFromDate();
            String sToDate = form.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                req.setAttribute("displayResultMsgClient", "Chưa chọn ngày hủy giao dịch");
                return PAGE_REPORT_MB08;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                req.setAttribute("displayResultMsgClient", "Chưa chọn đến ngày");
                return PAGE_REPORT_MB08;
            }
            Date startDate;
            Date endDate;
            Date sysDate = DateTimeUtils.convertStringToDate(DateTimeUtils.convertDateToString(DateTimeUtils.getSysDate()));

            try {
                startDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                req.setAttribute("displayResultMsgClient", "Từ ngày chưa chính xác");
                return PAGE_REPORT_MB08;
            }
            try {
                endDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                req.setAttribute("displayResultMsgClient", "Đến ngày không chính xác");
                return PAGE_REPORT_MB08;
            }
            if (startDate.after(endDate)) {
                req.setAttribute("displayResultMsgClient", "Từ ngày không được lớn hơn đến ngày");
                return PAGE_REPORT_MB08;
            }

            if (startDate.after(sysDate)) {
                req.setAttribute("displayResultMsgClient", "Từ ngày không được lớn hơn ngày hiện tại");
                return PAGE_REPORT_MB08;
            }

            String reportType = form.getReportType();
            if (reportType == null || "".equals(reportType.trim())) {
                req.setAttribute("displayResultMsgClient", "Chưa chọn loại báo cáo");
                return PAGE_REPORT_MB08;
            }

            ReportDestroyPosService reportDestroyPosService = new ReportDestroyPosService();
            if (connection == null) {
                connection = InitDatabaseServer.getConnection();
            }

            List<ReportPaymentPosBean> listReport = new ArrayList<ReportPaymentPosBean>();
            //lay danh sach may cua cua hang
            String[] tidList = searchMapShopTid();
            //goi ham bao cao ben payment
            listReport = reportDestroyPosService.getListDestroyPaymentPos(connection, startDate, endDate, null, reportType, tidList);


            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String prefixPath = "BCDS_09";
            String templatePath = tmp + prefixPath + ".xls";
            filePath += prefixPath + date + ".xls";

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            //set ngay tao
            beans.put("dateCreate", DateTimeUtils.convertDateToString_tuannv(DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate())));
            beans.put("fromDate", DateTimeUtils.convertDateToString_tuannv(startDate));
            req.setAttribute("filePath", filePath);

            beans.put(LIST_ITEMS, listReport);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);

            //Thong bao len jsp
            req.setAttribute("filePath", filePath);
            req.setAttribute("displayResultMsgClient", "report.stocktrans.error.successMessage");


        } catch (Exception ex) {
            req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        return PAGE_REPORT_MB08;
    }

    public String preparePage09() throws Exception {
        log.debug("# Begin method preparePage");
        HttpServletRequest req = getRequest();
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        form.setToDate(dateFomat.format(cal.getTime()));
        cal.add(Calendar.MONTH, -1);
        form.setFromDate(dateFomat.format(cal.getTime()));
        log.debug("# End method preparePage");
        return PAGE_REPORT_MB09;
    }

    /**
     * @author: TuanNV
     * Yêu cầu hủy giao dịch Viettel
     * @param startDate
     * @param endDate
     * @param cardType
     * @return List<SaleTransPost>
     * @throws java.lang.Exception
     */
    public String reportMB09() throws Exception {
        HttpServletRequest req = getRequest();
        try {
//            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            String sFromDate = form.getFromDate();
            String sToDate = form.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                req.setAttribute("displayResultMsgClient", "Chưa chọn ngày hủy giao dịch");
                return PAGE_REPORT_MB09;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                req.setAttribute("displayResultMsgClient", "Chưa chọn đến ngày");
                return PAGE_REPORT_MB09;
            }
            Date startDate;
            Date endDate;
            Date sysDate = DateTimeUtils.convertStringToDate(DateTimeUtils.convertDateToString(DateTimeUtils.getSysDate()));

            try {
                startDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                req.setAttribute("displayResultMsgClient", "Từ ngày chưa chính xác");
                return PAGE_REPORT_MB09;
            }
            try {
                endDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                req.setAttribute("displayResultMsgClient", "Đến ngày không chính xác");
                return PAGE_REPORT_MB09;
            }
            if (startDate.after(endDate)) {
                req.setAttribute("displayResultMsgClient", "Từ ngày không được lớn hơn đến ngày");
                return PAGE_REPORT_MB09;
            }

            if (startDate.after(sysDate)) {
                req.setAttribute("displayResultMsgClient", "Từ ngày không được lớn hơn ngày hiện tại");
                return PAGE_REPORT_MB09;
            }

            String reportType = form.getReportType();
            if (reportType == null || "".equals(reportType.trim())) {
                req.setAttribute("displayResultMsgClient", "Chưa chọn loại báo cáo");
                return PAGE_REPORT_MB07;
            }

            ReportDestroyPosService reportDestroyPosService = new ReportDestroyPosService();
            if (connection == null) {
                connection = InitDatabaseServer.getConnection();
            }

            List<ReportPaymentPosBean> listReport = new ArrayList<ReportPaymentPosBean>();
            //lay danh sach may cua cua hang
            String[] tidList = searchMapShopTid();
            //goi ham bao cao ben payment
            listReport = reportDestroyPosService.getListDestroyPaymentPos(connection, startDate, endDate, null, reportType, tidList);


            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String prefixPath = "BCDS_09";
            String templatePath = tmp + prefixPath + ".xls";
            filePath += prefixPath + date + ".xls";

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            //set ngay tao
            beans.put("dateCreate", DateTimeUtils.convertDateToString_tuannv(DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate())));
            beans.put("fromDate", DateTimeUtils.convertDateToString_tuannv(startDate));
            req.setAttribute("filePath", filePath);

            beans.put(LIST_ITEMS, listReport);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);

            //Thong bao len jsp
            req.setAttribute("filePath", filePath);
            req.setAttribute("displayResultMsgClient", "report.stocktrans.error.successMessage");


        } catch (Exception ex) {
            req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        return PAGE_REPORT_MB09;
    }

    public String[] searchMapShopTid() {
        log.debug("finding PaymentPos instance with reference, tid");
        try {

            String queryString = "from MapShopTid";
            List arrParam = new ArrayList();
            Query queryObject = getSession().createQuery(queryString);
            System.out.println(" queryString: " + queryString);

            List<MapShopTid> shopTidList = queryObject.list();

            if (shopTidList != null && shopTidList.size() != 0) {
                System.out.println(" FFFFFFFFFFFFFF " + shopTidList.size());
                String[] tid = new String[shopTidList.size()];
                for (int i = 0; i < shopTidList.size(); i++) {
                    tid[i] = shopTidList.get(i).getId().getTid().toString();
                }
                return tid;
            }
            System.out.println(" FFFCCCCCCCCCCCCCCCCCCCCCCCCC ");
            return null;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findCardTypePos() {
        log.debug("finding all Staff instances");
        try {
            String queryString = "from CardTypePos where status=?";
            Query queryObject = (Query) getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_ACTIVE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }
}
