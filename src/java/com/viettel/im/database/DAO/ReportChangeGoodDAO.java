/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

/**
 *
 * @author User
 */
import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ChangeGoodBeanDetail;
import com.viettel.im.client.bean.ChangeGoodBeanGenaral;
import com.viettel.im.client.bean.ChangeGoodSerialBean;
import com.viettel.im.client.form.ReportChangeGoodForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

public class ReportChangeGoodDAO extends BaseDAOAction {

    private Map listShop = new HashMap();
    private static final Log log = LogFactory.getLog(ReportChangeGoodDAO.class);
    public String pageForward;
    private ReportChangeGoodForm reportChangeGoodForm = new ReportChangeGoodForm();
    private List listStockModel = new ArrayList();

    private final String REQUEST_MESSAGE = "message";

    public List getListStockModel() {
        return listStockModel;
    }

    public void setListStockModel(List listStockModel) {
        this.listStockModel = listStockModel;
    }

    public ReportChangeGoodForm getReportChangeGoodForm() {
        return reportChangeGoodForm;
    }

    public void setReportChangeGoodForm(ReportChangeGoodForm reportChangeGoodForm) {
        this.reportChangeGoodForm = reportChangeGoodForm;
    }

    public Map getListShop() {
        return listShop;
    }

    public void setListShop(Map listShop) {
        this.listShop = listShop;
    }

    public String preparePage() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportChangeGood.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        pageForward = "ReportChangeGood";
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
        req.setAttribute("lstTelecomService", lstTelecomService);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        reportChangeGoodForm.setToDate(dateFomat.format(cal.getTime()));
        //cal.add(Calendar.MONTH, -1); // substract 1 month
        reportChangeGoodForm.setFromDate(dateFomat.format(cal.getTime()));
        reportChangeGoodForm.setShopCode(userToken.getShopCode());
        reportChangeGoodForm.setShopName(userToken.getShopName());
        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    //Lay mat hang tu telecomservice
    public String selectTelecomService() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportChangeGoodDAO.selectTelecomService");
        HttpServletRequest req = getRequest();
        pageForward = "selectTelecomService";
        String telecomService = req.getParameter("ReportChangeGoodForm.telecomServiceId");
        if (telecomService == null || "".equals(telecomService.trim())) {
            String[] header = {"", "--Chọn mặt hàng--"};
            listStockModel.add(header);
            return pageForward;
        }
        Long telecomServiceId = Long.valueOf(telecomService);
        String SQL_SELECT = "select stockModelId, name from StockModel where telecomServiceId = ? and status = ? and stockTypeId = ? ";
        Query q = getSession().createQuery(SQL_SELECT);
        q.setParameter(0, telecomServiceId);
        q.setParameter(1, Constant.STATUS_USE);
        q.setParameter(2, Constant.STOCK_TYPE_CARD);
        
        List lst = q.list();
        String[] header = {"", "--Chọn mặt hàng--"};
        listStockModel.add(header);
        listStockModel.addAll(lst);
        log.debug("# End method ReportChangeGoodDAO.selectTelecomService");
        return pageForward;
    }

    //Lay ten Shop
    public String autoSelectShop() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportChangeGoodDAO.autoSelectShop");
        HttpServletRequest req = getRequest();
        pageForward = "autoSelectShop";
        String shopCode = req.getParameter("ReportChangeGoodForm.shopCode");
        if (shopCode == null || "".equals(shopCode.trim())) {
            return pageForward;
        }
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String SQL_SELECT = "from Shop where lower(shopCode) like  ? and status = ? " +
                " and (shopPath like ? or shopId= ?) ";
        Query q = getSession().createQuery(SQL_SELECT);
        q.setParameter(0, shopCode.toLowerCase() + "%");
        q.setParameter(1, Constant.STATUS_USE);
        q.setParameter(2, "%_" + userToken.getShopId() + "_%");
        q.setParameter(3, userToken.getShopId());
        List<Shop> lst = q.list();
        for (Shop shop : lst) {
            listShop.put(shop.getName(), shop.getShopCode());
        }
        log.debug("# End method ReportChangeGoodDAO.autoSelectShop");
        return pageForward;
    }

    /**
     * @Author : ANHTT
     * @return : Tạo báo cáo theo cách mới 
     * @throws : Exception
     */
    public String exportReportChangeGoodReport() throws Exception {
        log.debug("# Begin method ReportReportChangeGoodDAO.exportReportChangeGoodReport");
        HttpServletRequest req = getRequest();
        pageForward = "ReportChangeGood";
        try {
            TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
            telecomServiceDAO.setSession(this.getSession());
            List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
            req.setAttribute("lstTelecomService", lstTelecomService);
            Long telecomServiceID = reportChangeGoodForm.getTelecomServiceId();
            if (telecomServiceID != null && telecomServiceID.compareTo(0L) != 0) {
                String SQL_SELECT = "from StockModel where telecomServiceId = ? and status = ? and stockTypeId = ?";
                Query q = getSession().createQuery(SQL_SELECT);
                q.setParameter(0, telecomServiceID);
                q.setParameter(1, Constant.STATUS_USE);
                q.setParameter(2, Constant.STOCK_TYPE_CARD);
                List lst = q.list();
                listStockModel.addAll(lst);
            }
            req.setAttribute("lstStockModel", listStockModel);

            String shopCode = reportChangeGoodForm.getShopCode();
            if (shopCode == null || "".equals(shopCode.trim())) {
                req.setAttribute("displayResultMsgClient", "stock.report.ReportChangeGood.error.noShopCode");
                return pageForward;
            }
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
            if (shop == null) {
                req.setAttribute("displayResultMsgClient", "stock.report.ReportChangeGood.error.shopCodeNotValid");
                return pageForward;
            }
            String sFromDate = reportChangeGoodForm.getFromDate();
            String sToDate = reportChangeGoodForm.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                req.setAttribute("displayResultMsgClient", "stock.report.ReportChangeGood.error.noFromDate");
                return pageForward;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                req.setAttribute("displayResultMsgClient", "stock.report.ReportChangeGood.error.noToDate");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                req.setAttribute("displayResultMsgClient", "stock.report.ReportChangeGood.error.fromDateNotValid");
                return pageForward;
            }
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                req.setAttribute("displayResultMsgClient", "stock.report.ReportChangeGood.error.toDateNotValid");
                return pageForward;
            }
            if (fromDate.after(toDate)) {
                req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.fromDateToDateNotValid");
                return pageForward;
            }

            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            try {
                ViettelMsg request = new OriginalViettelMsg();

                request.set("REPORT_DETAIL", "1");
                request.set("USER_CREATE", userToken.getFullName());
                request.set("USER_NAME", userToken.getLoginName());

                request.set("FROM_DATE", sFromDate);
                request.set("TO_DATE", sToDate);
                request.set("SHOP_NAME", shop.getName());
                request.set("SHOP_CODE", shop.getShopCode());

                request.set("SHOP_ADDRESS", shop.getAddress());
                request.set("SHOP_ID", shop.getShopId());

                request.set("TELECOM_SERVICE_ID", reportChangeGoodForm.getTelecomServiceId());
                request.set("STOCK_MODEL_ID", reportChangeGoodForm.getStockModelId());

                // báo cáo chi tiết
                if (reportChangeGoodForm.getReportDetail().equals(Constant.REPORT_DETAIL)) {

                    request.set("REPORT_DETAIL", "1");

                    //Thiet lap tham so loai bao cao/
                    //  request.set(ReportConstant.REPORT_KIND, String.valueOf(ReportConstant.POS_CT_CHANGE_PRODUCT_CT));
                    request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_CHANGE_GOOD);

                    ViettelMsg response = ReportRequestSender.sendRequest(request);
                        if (response != null && response.get(ReportConstant.RESULT_FILE) != null && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                        //Thong bao len jsp
//                        req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thành công!");
                        req.setAttribute(REQUEST_MESSAGE, "MSG.RET.030");
                        //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                         DownloadDAO downloadDAO = new DownloadDAO();
                            req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                            req.setAttribute("reportAccountMessage", "reportRevenue.reportRevenueMessage");
                    } else {
//                        req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thất bại!");
                        req.setAttribute(REQUEST_MESSAGE, "MSG.RET.031");
                    }
                } else {
                    request.set("REPORT_DETAIL", "0");
                    //Thiet lap tham so loai bao cao
                    //  request.set(ReportConstant.REPORT_KIND, String.valueOf(ReportConstant.POS_CT_CHANGE_PRODUCT_CT));
                    request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_CHANGE_GOOD);
                    ViettelMsg response = ReportRequestSender.sendRequest(request);
                    if (response != null && response.get(ReportConstant.RESULT_FILE) != null && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                        //Thong bao len jsp
//                        req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thành công!");
                        req.setAttribute(REQUEST_MESSAGE, "MSG.RET.030");
                        //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                         DownloadDAO downloadDAO = new DownloadDAO();
                        req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                        req.setAttribute("reportAccountMessage", "reportRevenue.reportRevenueMessage");
                    } else {
//                        req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thất bại!");
                        req.setAttribute(REQUEST_MESSAGE, "MSG.RET.031");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        log.debug("# End method ReportReportChangeGoodDAO.exportReportChangeGoodReport");
        return pageForward;
    }
    
    

    //Tao bao cao
    public String exportReportChangeGoodReport1() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportReportChangeGoodDAO.exportReportChangeGoodReport");
        HttpServletRequest req = getRequest();
        pageForward = "ReportChangeGood";
        try {

            TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
            telecomServiceDAO.setSession(this.getSession());
            List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
            req.setAttribute("lstTelecomService", lstTelecomService);
            Long telecomServiceID = reportChangeGoodForm.getTelecomServiceId();
            if (telecomServiceID != null && telecomServiceID.compareTo(0L) != 0) {
                String SQL_SELECT = "from StockModel where telecomServiceId = ? and status = ? ";
                Query q = getSession().createQuery(SQL_SELECT);
                q.setParameter(0, telecomServiceID);
                q.setParameter(1, Constant.STATUS_USE);
                List lst = q.list();
                listStockModel.addAll(lst);
            }
            req.setAttribute("lstStockModel", listStockModel);

            String shopCode = reportChangeGoodForm.getShopCode();
            if (shopCode == null || "".equals(shopCode.trim())) {
                req.setAttribute("displayResultMsgClient", "stock.report.ReportChangeGood.error.noShopCode");
                return pageForward;
            }
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
            if (shop == null) {
                req.setAttribute("displayResultMsgClient", "stock.report.ReportChangeGood.error.shopCodeNotValid");
                return pageForward;
            }
            String sFromDate = reportChangeGoodForm.getFromDate();
            String sToDate = reportChangeGoodForm.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                req.setAttribute("displayResultMsgClient", "stock.report.ReportChangeGood.error.noFromDate");
                return pageForward;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                req.setAttribute("displayResultMsgClient", "stock.report.ReportChangeGood.error.noToDate");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                req.setAttribute("displayResultMsgClient", "stock.report.ReportChangeGood.error.fromDateNotValid");
                return pageForward;
            }
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                req.setAttribute("displayResultMsgClient", "stock.report.ReportChangeGood.error.toDateNotValid");
                return pageForward;
            }
            if (fromDate.after(toDate)) {
                req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.fromDateToDateNotValid");
                return pageForward;
            }            
            if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear()!= fromDate.getYear()) {
                req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.fromDateToDateNotSame");
                return pageForward;
            }  
            String queryStringgenaral = "";
            String queryStringgetName = "";
            Query queryDetailName;            
            Query queryGenaral;
            List<ChangeGoodBeanDetail> lstChangeGoodRptDetail = new ArrayList<ChangeGoodBeanDetail>();
            List<ChangeGoodBeanGenaral> lstChangeGoodRptGenaral = new ArrayList<ChangeGoodBeanGenaral>();
            if (reportChangeGoodForm.getReportDetail().equals(Constant.REPORT_DETAIL)) {
                queryStringgetName += "select b.staff_id, b.name ";
                queryStringgetName += " from Staff b";
                queryStringgetName += " Where (b.shop_id = ?) and (b.channel_type_id = ? or b.channel_type_id = ?)";
                queryDetailName = getSession().createSQLQuery(queryStringgetName.toString());
                queryDetailName.setParameter(0, shop.getShopId());
                queryDetailName.setParameter(1, ResourceBundleUtils.getResource("STAFF_SALE_DIRECT"));
                queryDetailName.setParameter(2, ResourceBundleUtils.getResource("STAFF_OF_SHOP"));
                List NameList = queryDetailName.list();
                ChangeGoodBeanDetail changegood = new ChangeGoodBeanDetail();
                changegood.setName(shop.getName());
                changegood.setID(shop.getShopId());
                changegood.setLstSerial(GetSerial(changegood.getID(), fromDate, toDate, 1L));
                lstChangeGoodRptDetail.add(changegood);
                Iterator iter = NameList.iterator();

                while (iter.hasNext()) {
                    Object[] temp = (Object[]) iter.next();
                    ChangeGoodBeanDetail changegoodstaff = new ChangeGoodBeanDetail();
                    changegoodstaff.setID(new Long(temp[0].toString()));
                    changegoodstaff.setName(temp[1].toString());
                    changegoodstaff.setLstSerial(GetSerial(changegoodstaff.getID(), fromDate, toDate, 2L));
                    lstChangeGoodRptDetail.add(changegoodstaff);
                }

            } else {
                queryStringgenaral += "select b.stock_model_id as stockModelId , c.name as stockModelName,d.name as typeName,Sum(b.quantity_res) as totalQuantity";
                queryStringgenaral += " from Stock_Trans a,stock_trans_detail b,stock_model c,APP_Params d";
                queryStringgenaral += "  ";
                queryStringgenaral += "where a.reason_id = (select reason_id from reason where reason_code = ? and reason_status  = ? )";
                queryStringgenaral += " and c.stock_model_id = b.stock_model_id";
                queryStringgenaral += " and a.stock_trans_id = b.stock_trans_id";
                queryStringgenaral += " and a.stock_trans_id = b.stock_trans_id";
                queryStringgenaral += " and c.unit = d.code";
                queryStringgenaral += " and d.type = ?"; //'STOCK_MODEL_UNIT'                
                queryStringgenaral += " and (a.create_datetime >= ? and a.create_datetime < add_months(?,1))";
                queryStringgenaral += " and ((a.from_owner_id = ? and a.from_owner_type = 1)";
                queryStringgenaral += " or (a.from_owner_id  in (select staff_id from staff where shop_id = ? ))) and a.from_owner_type = 2";
                if (reportChangeGoodForm.getTelecomServiceId() != null && reportChangeGoodForm.getTelecomServiceId().compareTo(0L) != 0) {
                    queryStringgenaral += " and c.telecom_service_id = ?";
                }
                if (reportChangeGoodForm.getStockModelId() != null && reportChangeGoodForm.getStockModelId().compareTo(0L) != 0) {
                    queryStringgenaral += " and c.stock_model_id = ?";
                }
                queryStringgenaral += "  ";
                queryStringgenaral += "group by b.stock_model_id,c.name,d.name";
                queryStringgenaral += "  ";
                queryStringgenaral += " ORDER BY  c.name ";

                queryGenaral = getSession().createSQLQuery(queryStringgenaral.toString()).
                        addScalar("stockModelId", Hibernate.LONG).
                        addScalar("stockModelName", Hibernate.STRING).
                        addScalar("typeName", Hibernate.STRING).
                        addScalar("totalQuantity", Hibernate.LONG).
                        setResultTransformer(Transformers.aliasToBean(ChangeGoodBeanGenaral.class));
                queryGenaral.setParameter(0, ResourceBundleUtils.getResource("REASON_EXP_CHANGE_DEMAGED_GOODS"));
                queryGenaral.setParameter(1, Constant.STATUS_USE.toString());
                queryGenaral.setParameter(2, Constant.APP_PARAMS_STOCK_MODEL_UNIT);
                queryGenaral.setParameter(3, fromDate);
                queryGenaral.setParameter(4, toDate);
                queryGenaral.setParameter(5, shop.getShopId());
                queryGenaral.setParameter(6, shop.getShopId());
                if (reportChangeGoodForm.getTelecomServiceId() != null && reportChangeGoodForm.getTelecomServiceId().compareTo(0L) != 0) {
                    queryGenaral.setParameter(7, reportChangeGoodForm.getTelecomServiceId());
                }
                if (reportChangeGoodForm.getStockModelId() != null && reportChangeGoodForm.getStockModelId().compareTo(0L) != 0) {
                    queryGenaral.setParameter(8, reportChangeGoodForm.getStockModelId());
                }
                lstChangeGoodRptGenaral = queryGenaral.list();
            }
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            String prefixPath = "ChangeGood_report";

            //Giao dich xuat
            if (reportChangeGoodForm.getReportDetail().equals(Constant.REPORT_GENERAL)) {
                templatePath = tmp + prefixPath + "_general.xls";
                filePath += prefixPath + "_general_" + date + ".xls";
            }
            if (reportChangeGoodForm.getReportDetail().equals(Constant.REPORT_DETAIL)) {
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
            beans.put("shopName", shop.getName());
            beans.put("shopAddress", shop.getAddress());
            if (reportChangeGoodForm.getReportDetail().equals(Constant.REPORT_DETAIL)) {
                beans.put("lstChangeGoodRptDetail", lstChangeGoodRptDetail);
                XLSTransformer transformer = new XLSTransformer();
                transformer.transformXLS(templateRealPath, beans, realPath);
                req.setAttribute("filePath", filePath);

            } else {
                beans.put("lstChangeGoodRptGenaral", lstChangeGoodRptGenaral);
                XLSTransformer transformer = new XLSTransformer();
                transformer.transformXLS(templateRealPath, beans, realPath);
                req.setAttribute("filePath", filePath);
            }



        //XLSTransformer transformer = new XLSTransformer();
        //transformer.transformXLS(templateRealPath, beans, realPath);
        //req.setAttribute("filePath", filePath);

        } catch (Exception ex) {
            req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        log.debug("# End method ReportReportChangeGoodDAO.exportReportChangeGoodReport");
        return pageForward;
    }

    public String example1() throws FileNotFoundException, IOException {
        HttpServletRequest req = getRequest();
        pageForward = "ReportChangeGood";
        try {
            // Khởi tạo workbook
            HSSFWorkbook resultWorkbook = new HSSFWorkbook();
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            String prefixPath = "Demo";
            String date = sdf.format(cal.getTime());
            templatePath = tmp + prefixPath + ".xls";
            filePath += prefixPath + date + ".xls";
            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            
            //***********************************************************************************
            // Đọc file template

            java.io.InputStream is = new BufferedInputStream(new FileInputStream(templateRealPath));

            // Khởi tạo 1 transformer để xuất excel từ 1 template
            XLSTransformer transformer = new XLSTransformer();

            List sheetNames = new ArrayList();
            List tempNames = new ArrayList();
            List maps = new ArrayList();
            String sheetName;
            String tempName;
            for (int i = 0; i < 2L; i++) {
                // beanName: map
                Map map = new HashMap();
                //Department department = (Department) departments.get( i );
                //map.put("department", department );
                sheetName = "Demo" + i;
                tempName = "Sheet" + i;
                tempNames.add(tempName);
                sheetNames.add(sheetName);
                map.put("name", "Number " + i);
                map.put("Test", tempName);
                maps.add(map);
            }

            // Tạo workbook để xuất ra excel
            // Chú ý: cách sử dụng HSSFWorkbook: Chú tìm trong project của chú chắc chắn có đấy, vì anh thấy trong lib của chú có khai báo gói POI mà
            resultWorkbook = transformer.transformXLS(is, tempNames, sheetNames, maps);
            /**********************************************************************************/
            java.io.OutputStream os = new BufferedOutputStream(new FileOutputStream(realPath));
            resultWorkbook.write(os);
            os.close();
            req.setAttribute("filePath", filePath);
            

        } catch (Exception ex) {
            req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        log.debug("# End method ReportReportChangeGoodDAO.exportReportChangeGoodReport");
        return pageForward;
    }

    public List<ChangeGoodSerialBean> GetSerial(Long ShopID, Date fromDate, Date toDate, Long from_owner_type) throws Exception {
        String queryStringDetail = "";
        queryStringDetail += " select c.name, b.from_serial,b.create_datetime,a.reason_id";
        queryStringDetail += " from Stock_Trans a,stock_trans_serial b,stock_model c,APP_Params d";
        queryStringDetail += " where (a.reason_id in (select reason_id from reason where (reason_code = ? or reason_code = ?) and reason_status= ? ) )";
        queryStringDetail += " and c.stock_model_id = b.stock_model_id";
        queryStringDetail += " and a.stock_trans_id = b.stock_trans_id";
        queryStringDetail += " and c.unit = d.code";
        queryStringDetail += " and d.type = ?";
        queryStringDetail += " and (a.create_datetime >= ? and a.create_datetime <=?)";
        queryStringDetail += " and ((a.from_owner_id = ? and a.from_owner_type = ?)";
        queryStringDetail += " or (a.to_owner_id = ? AND a.to_owner_type =? ))";
        if (reportChangeGoodForm.getTelecomServiceId() != null && reportChangeGoodForm.getTelecomServiceId().compareTo(0L) != 0) {
            queryStringDetail += " and c.telecom_service_id = ?";
        }
        if (reportChangeGoodForm.getStockModelId() != null && reportChangeGoodForm.getStockModelId().compareTo(0L) != 0) {
            queryStringDetail += " and c.stock_model_id = ?";
        }
        queryStringDetail += " ORDER BY  b.create_datetime,a.stock_trans_id,a.reason_id desc,c.name";
        Query queryDetail;
        queryDetail = getSession().createSQLQuery(queryStringDetail.toString());
        queryDetail.setParameter(0, ResourceBundleUtils.getResource("REASON_EXP_CHANGE_DEMAGED_GOODS"));
        queryDetail.setParameter(1, ResourceBundleUtils.getResource("REASON_IMP_CHANGE_DEMAGED_GOODS"));
        queryDetail.setParameter(2, Constant.STATUS_USE.toString());
        queryDetail.setParameter(3, Constant.APP_PARAMS_STOCK_MODEL_UNIT);
        queryDetail.setParameter(4, fromDate);
        queryDetail.setParameter(5, toDate);
        queryDetail.setParameter(6, ShopID);
        queryDetail.setParameter(7, from_owner_type);
        queryDetail.setParameter(8, ShopID);
        queryDetail.setParameter(9, from_owner_type);
        if (reportChangeGoodForm.getTelecomServiceId() != null && reportChangeGoodForm.getTelecomServiceId().compareTo(0L) != 0) {
            queryDetail.setParameter(10, reportChangeGoodForm.getTelecomServiceId());
        }
        if (reportChangeGoodForm.getStockModelId() != null && reportChangeGoodForm.getStockModelId().compareTo(0L) != 0) {
            queryDetail.setParameter(11, reportChangeGoodForm.getStockModelId());
        }
        List ListSerial = queryDetail.list();
        List<ChangeGoodSerialBean> listSerialReturn = new ArrayList<ChangeGoodSerialBean>();
        ChangeGoodSerialBean changeGoodserial = new ChangeGoodSerialBean();
        for (int idx = 0; idx < ListSerial.size() - 1; idx += 2) {
            Object[] stockSerialX = (Object[]) ListSerial.get(idx);
            Object[] stockSerialN = (Object[]) ListSerial.get(idx + 1);
            //Date DateX = DateTimeUtils.convertStringToTime(stockSerialX[2].toString(),"yyyy-MM-dd HH:mm:ss");
            //Date DateN = DateTimeUtils.convertStringToTime(stockSerialN[2].toString(),"yyyy-MM-dd HH:mm:ss");
            changeGoodserial = new ChangeGoodSerialBean();
            changeGoodserial.setStockModelName(stockSerialX[0].toString());
            changeGoodserial.setSerialX(stockSerialX[1].toString());
            changeGoodserial.setSerialN(stockSerialN[1].toString());
            changeGoodserial.setDateChange(DateTimeUtils.convertStringToDate(stockSerialX[2].toString()));
            listSerialReturn.add(changeGoodserial);
        }
        return listSerialReturn;
    }
}
