/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

/**
 *
 * @author Vunt
 */
import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.ReportGeneralInvoiceForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ReportGeneralInvoice;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ReportGeneralInvoiceDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportBankReceiptDAO.class);
    static private String REPORT_GENERAL_INVOICE = "reportGeneralInvoice";
    public static final String CHANEL_TYPE_OBJECT_TYPE = "1";
    public static final String CHANEL_TYPE_IS_VT_UNIT = "1";
    private static Long SHOP_VT_ID = 7282L;
    private static String DESTROY_INVOICE = "4";
    private static String VAT_INVOICE = "1";
    public String pageForward;
    private ReportGeneralInvoiceForm form = new ReportGeneralInvoiceForm();

    public ReportGeneralInvoiceForm getForm() {
        return form;
    }

    public void setForm(ReportGeneralInvoiceForm form) {
        this.form = form;
    }

    public String preparePage() throws Exception {
        log.debug("# Begin method preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        pageForward = REPORT_GENERAL_INVOICE;
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        form.setToDate(dateFomat.format(cal.getTime()));
        //cal.add(Calendar.MONTH, -1);
        form.setFromDate(dateFomat.format(cal.getTime()));
        form.setReportDetail(1L);
        form.setReportType(1L);
        form.setReportDateType(1L);
        form.setShopCode(userToken.getShopCode());
        form.setShopName(userToken.getShopName());
        log.debug("# End method preparePage");
        return pageForward;
    }

    public String reportGeneralInvoice() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportReportChangeGoodDAO.exportReportChangeGoodReport");
        HttpServletRequest req = getRequest();
        pageForward = REPORT_GENERAL_INVOICE;
        try {
            String shopCode = form.getShopCode();
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);

            if (shop == null) {
//                req.setAttribute("displayResultMsgClient", "Mã cửa hàng không chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.039");
                return pageForward;
            }
            String sFromDate = form.getFromDate();
            String sToDate = form.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
//                req.setAttribute("displayResultMsgClient", "Chưa chọn từ ngày");
                req.setAttribute("displayResultMsgClient", "ERR.RET.022");
                return pageForward;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
//                req.setAttribute("displayResultMsgClient", "Chưa chọn đến ngày");
                req.setAttribute("displayResultMsgClient", "ERR.RET.023");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
//                req.setAttribute("displayResultMsgClient", "Từ ngày chưa chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.024");
                return pageForward;
            }
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
//                req.setAttribute("displayResultMsgClient", "Đến ngày không chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.025");
                return pageForward;
            }
            if (fromDate.after(toDate)) {
//                req.setAttribute("displayResultMsgClient", "Từ ngày không được lớn hơn đến ngày");
                req.setAttribute("displayResultMsgClient", "ERR.RET.026");
                return pageForward;
            }

            if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear()!= fromDate.getYear()) {
                req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.fromDateToDateNotSame");
                return pageForward;
            }  
            Date toDateS = DateTimeUtils.addDate(toDate, 1);
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            try {
                /*Goi toi service va lay ra du lieu bao cao*/
                ViettelMsg request = new OriginalViettelMsg();                     
                request.set("FROM_DATE", sFromDate);
                request.set("TO_DATE", sToDate);
                request.set("SHOP_ID", shop.getShopId());
                request.set("SHOP_NAME", shop.getName());
                request.set("STAFF_NAME", userToken.getLoginName());
                request.set("USER_NAME", userToken.getLoginName());
                request.set("REPORT_TYPE", form.getReportType());
                request.set("REPORT_DETAIL", form.getReportDetail());
                request.set("REPORT_DATE_TYPE",form.getReportDateType());

                /*Thiet lap tham so loai bao cao*/
                request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_DESTROY_INVOICE_VAT );
               
                ViettelMsg response = ReportRequestSender.sendRequest(request);
                if (response != null && response.get(ReportConstant.RESULT_FILE) != null && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                    //Thong bao len jsp
                 //   req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thành công!");
                      //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                 //   req.setAttribute(REQUEST_REPORT_INVOICE_MESSAGE, "reportInvoice.reportInvoiceMessage");
                       DownloadDAO downloadDAO = new DownloadDAO();
                       req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                       req.setAttribute("reportAccountMessage", "reportRevenue.reportRevenueMessage");
                } else {
                 //   req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thất bại!");
                   req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.undefine");                 
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        log.debug("# End method ReportReportChangeGoodDAO.exportReportChangeGoodReport");
        return pageForward;
    }


        
    //Tao bao cao cũ 
    public String reportGeneralInvoice1() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportReportChangeGoodDAO.exportReportChangeGoodReport");
        HttpServletRequest req = getRequest();
        pageForward = REPORT_GENERAL_INVOICE;
        try {
            String shopCode = form.getShopCode();
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);

            if (shop == null) {
                req.setAttribute("displayResultMsgClient", "Mã cửa hàng không chính xác");
                return pageForward;
            }
            String sFromDate = form.getFromDate();
            String sToDate = form.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                req.setAttribute("displayResultMsgClient", "Chưa chọn từ ngày");
                return pageForward;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                req.setAttribute("displayResultMsgClient", "Chưa chọn đến ngày");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                req.setAttribute("displayResultMsgClient", "Từ ngày chưa chính xác");
                return pageForward;
            }
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                req.setAttribute("displayResultMsgClient", "Đến ngày không chính xác");
                return pageForward;
            }
            if (fromDate.after(toDate)) {
                req.setAttribute("displayResultMsgClient", "Từ ngày không được lớn hơn đến ngày");
                return pageForward;
            }

            Date toDateS = DateTimeUtils.addDate(toDate, 1);
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            String prefixPath = "Report_General";
            List<ReportGeneralInvoice> listReportGeneralInvoice = new ArrayList<ReportGeneralInvoice>();
            String sql = "From ReportGeneralInvoice where status = ?";
            if (form.getReportType().equals(1L)) {
                sql += " and shopId = ? ";
            } else {
                sql += " and shopId in (SELECT shopId FROM Shop sh WHERE sh.shopPath LIKE ? ESCAPE '$' OR sh.shopId = ? )";
            }
            if (form.getReportDateType().equals(1L)) {
                sql += " and createDate >= ? and createDate < ? ";
            } else {
                sql += " and destroyDate >= ? and destroyDate < ? ";
            }

            Query query = getSession().createQuery(sql);
            if (form.getReportDetail().equals(1L)) {
                query.setParameter(0, DESTROY_INVOICE);
            } else {
                query.setParameter(0, VAT_INVOICE);
            }
            if (form.getReportType().equals(1L)) {
                query.setParameter(1, shop.getShopId());
                query.setParameter(2, fromDate);
                query.setParameter(3, toDateS);
            } else {
                query.setParameter(1, "%$_" + shop.getShopId().toString() + "$_%");
                query.setParameter(2, shop.getShopId());
                query.setParameter(3, fromDate);
                query.setParameter(4, toDateS);
            }
            listReportGeneralInvoice = query.list();

            //Giao dich xuat
            if (form.getReportDetail().equals(1L)) {
                templatePath = tmp + prefixPath + "_Destroy.xls";
                filePath += prefixPath + "_Destroy_" + date + ".xls";
            }
            if (form.getReportDetail().equals(2L)) {
                templatePath = tmp + prefixPath + "_VAT.xls";
                filePath += prefixPath + "_VAT_" + date + ".xls";
            }

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            
            //TrongLV - Begin
                java.io.InputStream is = new BufferedInputStream(new FileInputStream(templateRealPath));
                HSSFWorkbook resultWorkbook = new HSSFWorkbook();
                XLSTransformer transformer = new XLSTransformer();

                List sheetNames = new ArrayList();
                List tempNames = new ArrayList();
                List maps = new ArrayList();
                String sheetName;
                String tempName;
                Long totalSheet = 20000L;
                int sheetNum = (int) Math.ceil(listReportGeneralInvoice.size() / (float) totalSheet);
                if (listReportGeneralInvoice.size() == 0) {
                    tempName = "BCHT";
                    sheetName = "BCHT0";
                    tempNames.add(tempName);
                    sheetNames.add(sheetName);
                        Map beans = new HashMap();
                        //set ngay tao
                        //beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        beans.put("fromDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(sFromDate)));
                        beans.put("toDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(sToDate)));
                        beans.put("shopName", shop.getName());
                        beans.put("userName", userToken.getStaffName());
                        beans.put("listInvoice", listReportGeneralInvoice);
                    maps.add(beans);

                } else {
                    List<ReportGeneralInvoice> displaySheet = new ArrayList();
                    Long begin;
                    Long end;
                    Long size = listReportGeneralInvoice.size() * 1L;
                    for (int i = 0; i < sheetNum; i++) {
                        displaySheet = new ArrayList();
                        begin = totalSheet * i;
                        end = totalSheet * (i + 1);
                        if (begin.compareTo(size) > 0) {
                            break;
                        }
                        if (end.compareTo(size) > 0) {
                            end = size;
                        }
                        displaySheet = listReportGeneralInvoice.subList(begin.intValue(), end.intValue());
                        sheetName = "BCHT" + i;
                        tempName = "BCHT";
                        tempNames.add(tempName);
                        sheetNames.add(sheetName);
                            Map beans = new HashMap();
                            //set ngay tao
                            //beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            beans.put("fromDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(sFromDate)));
                            beans.put("toDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(sToDate)));
                            beans.put("shopName", shop.getName());
                            beans.put("userName", userToken.getStaffName());                            
                            beans.put("listInvoice", displaySheet);
                        maps.add(beans);
                    }

                }
                resultWorkbook = transformer.transformXLS(is, tempNames, sheetNames, maps);
                /**********************************************************************************/
                java.io.OutputStream os = new BufferedOutputStream(new FileOutputStream(realPath));
                resultWorkbook.write(os);
                os.close();
            //TrongLV - End

//            Map beans = new HashMap();
            //set ngay tao
            //beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            beans.put("fromDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(sFromDate)));
//            beans.put("toDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(sToDate)));
//            beans.put("shopName", shop.getName());
//            beans.put("userName", userToken.getStaffName());
            
            
            //beans.put("shopAddress", shop.getAddress());
//            if (form.getReportDetail().equals(1L)) {
//                beans.put("listInvoice", listReportGeneralInvoice);
//                XLSTransformer transformer = new XLSTransformer();
//                transformer.transformXLS(templateRealPath, beans, realPath);
//                req.setAttribute("filePath", filePath);
//
//            } else {
//                if (form.getReportDetail().equals(2L)) {
//                    beans.put("listInvoice", listReportGeneralInvoice);
//                    XLSTransformer transformer = new XLSTransformer();
//                    transformer.transformXLS(templateRealPath, beans, realPath);
//                    req.setAttribute("filePath", filePath);
//                }
//            }
                
        //XLSTransformer transformer = new XLSTransformer();
        //transformer.transformXLS(templateRealPath, beans, realPath);
        req.setAttribute("filePath", filePath);

        } catch (Exception ex) {
            req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        log.debug("# End method ReportReportChangeGoodDAO.exportReportChangeGoodReport");
        return pageForward;
    }


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
        strQuery1.append(" from Shop a, ");
        strQuery1.append(" ChannelType chty ");
        strQuery1.append(" where 1 = 1 ");
        strQuery1.append(" AND (a.status = ? )");        
        listParameter1.add(Constant.STATUS_ACTIVE);        
        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");        
        listParameter1.add(userToken.getShopId());
        strQuery1.append(" and chty.channelTypeId = a.channelTypeId ");
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append(" and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append(" and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append(" AND ");
        strQuery1.append(" chty.objectType = ? ");
        listParameter1.add(CHANEL_TYPE_OBJECT_TYPE);
        strQuery1.append(" AND ");
        strQuery1.append(" chty.isVtUnit = ? ");
        listParameter1.add(CHANEL_TYPE_IS_VT_UNIT);

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
     * purpose  : lay ten shop
     *
     */
    public List<ImSearchBean> getNameShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append(" from Shop a, ");
        strQuery1.append(" ChannelType chty ");
        strQuery1.append(" where 1 = 1 ");
        strQuery1.append(" AND (a.status = ? )");        
        listParameter1.add(Constant.STATUS_ACTIVE);        
        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");        
        listParameter1.add(userToken.getShopId());
        strQuery1.append(" and chty.channelTypeId = a.channelTypeId ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append(" and lower(a.shopCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        }
        else{
            return listImSearchBean;
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append(" and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append(" AND ");
        strQuery1.append(" chty.objectType = ? ");
        listParameter1.add(CHANEL_TYPE_OBJECT_TYPE);
        strQuery1.append(" AND ");
        strQuery1.append(" chty.isVtUnit = ? ");
        listParameter1.add(CHANEL_TYPE_IS_VT_UNIT);

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
}
