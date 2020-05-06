package com.viettel.im.database.DAO;

import com.viettel.common.ObjectClientChannel;
import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.InvoiceDailyRptV2Bean;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.ReportInvoiceForm;
import java.util.List;
import java.util.ArrayList;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.InvoiceDailyRpt;
import com.viettel.im.database.BO.InvoiceDailyRptV2;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Doan Thanh 8
 */
public class ReportInvoiceDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReportInvoiceDAO.class);
    //dinh nghia cac hang so pageForward
    private String pageForward;
    private final String REPORT_INVOICE = "reportInvoice";
    private final String REPORT_INVOICE_V2 = "reportInvoiceV2";
    private final String GET_SHOP_CODE = "getShopCode";
    private final String GET_SHOP_NAME = "getShopName";
    private final String GET_STAFF_CODE = "getStaffCode";
    private final String GET_STAFF_NAME = "getStaffName";
    //dinh nghia ten cac bien session hoac bien request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_REPORT_INVOICE_PATH = "reportInvoicePath";
    private final String REQUEST_REPORT_INVOICE_MESSAGE = "reportInvoiceMessage";
    private final String SESSION_CURRENT_SHOP_ID = "currentShopId";
    //
    private final int MAX_RESULT = 1000;
    //
    private ObjectClientChannel client = null;
    //khai bao bien form
    private ReportInvoiceForm reportInvoiceForm = new ReportInvoiceForm();

    public ReportInvoiceForm getReportInvoiceForm() {
        return reportInvoiceForm;
    }

    public void setReportInvoiceForm(ReportInvoiceForm reportInvoiceForm) {
        this.reportInvoiceForm = reportInvoiceForm;
    }

    /**
     *
     * author tamdt1
     * date: 09/07/2009
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ReportInvoiceDAO ...");

        HttpServletRequest req = getRequest();

        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        //reset form
        this.reportInvoiceForm.resetForm();

        //xoa bien session
        req.getSession().setAttribute(SESSION_CURRENT_SHOP_ID, null);

        this.reportInvoiceForm.setShopCode(userToken.getShopCode());
        this.reportInvoiceForm.setShopName(userToken.getShopName());
        this.reportInvoiceForm.setStaffCode(userToken.getLoginName());
        this.reportInvoiceForm.setStaffName(userToken.getStaffName());

        pageForward = REPORT_INVOICE;

        log.info("End method preparePage of ReportInvoiceDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 09/03/2010
     *
     */
    public String preparePageV2() throws Exception {
        log.info("Begin method preparePageV2 of ReportInvoiceDAO ...");

        HttpServletRequest req = getRequest();

        //reset form
        this.reportInvoiceForm.resetForm();

        pageForward = REPORT_INVOICE_V2;

        log.info("End method preparePageV2 of ReportInvoiceDAO");

        return pageForward;
    }

    /**
     *
     *
     * @return
     * @throws Exception
     */
    public String reportInvoice() throws Exception {
        log.info("Begin method reportInvoice of ReportInvoiceDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        String strFromDate = this.reportInvoiceForm.getFromDate();
        String strToDate = this.reportInvoiceForm.getToDate();
        Long notIncludeChild = this.reportInvoiceForm.getNotIncludeChild();
        Long groupType = this.reportInvoiceForm.getGroupType();

        if (!checkValidReportInvoice()) {
            pageForward = REPORT_INVOICE;
            log.info("End method reportInvoice of ReportInvoiceDAO");
            return pageForward;
        }
        String shopCode = this.reportInvoiceForm.getShopCode().trim();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List<Shop> listShop = shopDAO.findByPropertyWithStatus(ShopDAO.SHOP_CODE, shopCode, Constant.STATUS_USE);
        if (listShop == null || listShop.size() == 0) {

            //
            req.setAttribute(REQUEST_MESSAGE, "reportInvoice.error.shopNotExist");

            pageForward = REPORT_INVOICE;
            log.info("End method reportInvoice of ReportInvoiceDAO");
            return pageForward;
        }

        String shopName = listShop.get(0).getName();
        String shopAddress = listShop.get(0).getAddress();
        String shopTin = listShop.get(0).getTin();
        String shopTel = listShop.get(0).getTel();
        this.reportInvoiceForm.setShopId(listShop.get(0).getShopId());
        String staffCode = this.reportInvoiceForm.getStaffCode();

        if (staffCode != null && !staffCode.trim().equals("")) {
            String strStaffQuery = "from Staff where staffCode = ? and status = ? and shopId = ? ";
            Query staffQuery = getSession().createQuery(strStaffQuery);
            staffQuery.setParameter(0, staffCode.trim());
            staffQuery.setParameter(1, Constant.STATUS_USE);
            staffQuery.setParameter(2, this.reportInvoiceForm.getShopId());
            List<Staff> listStaff = staffQuery.list();
            if (listStaff == null || listStaff.size() == 0) {

                //
                req.setAttribute(REQUEST_MESSAGE, "reportInvoice.error.staffNotExist");

                pageForward = REPORT_INVOICE;
                log.info("End method reportInvoice of ReportInvoiceDAO");
                return pageForward;
            }

            this.reportInvoiceForm.setStaffId(listStaff.get(0).getStaffId());
        }
        Long shopId = this.reportInvoiceForm.getShopId();
        Long staffId = this.reportInvoiceForm.getStaffId();

        Date fromDate;
        Date toDate;
        try {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        } catch (Exception ex) {
//            req.setAttribute("displayResultMsgClient", "Từ ngày chưa chính xác");
            req.setAttribute("displayResultMsgClient", "ERR.RET.024");
            return pageForward;
        }
        try {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            //toDate = DateTimeUtils.addDate(toDate, 1);
        } catch (Exception ex) {
//            req.setAttribute("displayResultMsgClient", "Đến ngày không chính xác");
            req.setAttribute("displayResultMsgClient", "ERR.RET.025");
            return pageForward;
        }
        if (fromDate.after(toDate)) {
//            req.setAttribute("displayResultMsgClient", "Từ ngày không được lớn hơn đến ngày");
            req.setAttribute("displayResultMsgClient", "ERR.RET.026");
            return pageForward;
        }
        if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear() != fromDate.getYear()) {
            req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.fromDateToDateNotSame");
            return pageForward;
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Date fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        //Date toDate = DateTimeUtils.convertStringToDate(strToDate);
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(fromDate);
        try {
            /*Goi toi service va lay ra du lieu bao cao*/
            ViettelMsg request = new OriginalViettelMsg();
            request.set("SHOP_ID", shopId);
            request.set("STAFF_ID", staffId);
            request.set("FROM_DATE", strFromDate);
            request.set("TO_DATE", strToDate);
            request.set("USER_NAME", userToken.getLoginName());
            request.set("STAFF_NAME", userToken.getStaffName());

            request.set("SHOP_NAME", shopName);
            request.set("SHOP_ADDRESS", shopAddress);
            request.set("SHOP_TIN", shopTin);
            request.set("SHOP_TEL", shopTel);
            request.set("CURRENT_MONTH", fromCalendar.get(Calendar.MONTH) + 1);
            request.set("CURRENT_YEAR", fromCalendar.get(Calendar.YEAR));

            /*Truyen tham so duong dan file dau ra*/
            /*Thiet lap tham so loai bao cao*/
            //  request.set(ReportConstant.REPORT_KIND, String.valueOf(ReportConstant.POS_CT_CHANGE_PRODUCT_CT));
            // IM_REPORT_INVOICE = 9
            request.set("reportKind", "9");

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            System.out.print("RESULT_FILE: " + response.get(ReportConstant.RESULT_FILE));
            if (response != null && response.get(ReportConstant.RESULT_FILE) != null && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //Thong bao len jsp
//                req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thành công!");
                req.setAttribute(REQUEST_MESSAGE, "MSG.RET.030");
                //req.setAttribute(REQUEST_REPORT_INVOICE_PATH, response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute(REQUEST_REPORT_INVOICE_PATH, downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute(REQUEST_REPORT_INVOICE_MESSAGE, "reportInvoice.reportInvoiceMessage");
            } else {
//                req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thất bại!");
                req.setAttribute(REQUEST_MESSAGE, "MSG.RET.031");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        pageForward = REPORT_INVOICE;
        log.info("End method reportInvoice of ReportInvoiceDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 09/07/2009
     * bao cao tinh hinh su dung hoa don, xuat ra file excel
     *
     */
    public String reportInvoice1() throws Exception {
        log.info("Begin method reportInvoice of ReportInvoiceDAO...");

        HttpServletRequest req = getRequest();

        String strFromDate = this.reportInvoiceForm.getFromDate();
        String strToDate = this.reportInvoiceForm.getToDate();
        Long notIncludeChild = this.reportInvoiceForm.getNotIncludeChild();
        Long groupType = this.reportInvoiceForm.getGroupType();



        if (!checkValidReportInvoice()) {

            pageForward = REPORT_INVOICE;
            log.info("End method reportInvoice of ReportInvoiceDAO");
            return pageForward;
        }

        String shopCode = this.reportInvoiceForm.getShopCode().trim();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List<Shop> listShop = shopDAO.findByPropertyWithStatus(ShopDAO.SHOP_CODE, shopCode, Constant.STATUS_USE);
        if (listShop == null || listShop.size() == 0) {

            //
            req.setAttribute(REQUEST_MESSAGE, "reportInvoice.error.shopNotExist");

            pageForward = REPORT_INVOICE;
            log.info("End method reportInvoice of ReportInvoiceDAO");
            return pageForward;
        }

        String shopName = listShop.get(0).getName();
        String shopAddress = listShop.get(0).getAddress();
        String shopTin = listShop.get(0).getTin();
        String shopTel = listShop.get(0).getTel();
        this.reportInvoiceForm.setShopId(listShop.get(0).getShopId());

        String staffCode = this.reportInvoiceForm.getStaffCode();
        if (staffCode != null && !staffCode.trim().equals("")) {
            String strStaffQuery = "from Staff where staffCode = ? and status = ? and shopId = ? ";
            Query staffQuery = getSession().createQuery(strStaffQuery);
            staffQuery.setParameter(0, staffCode.trim());
            staffQuery.setParameter(1, Constant.STATUS_USE);
            staffQuery.setParameter(2, this.reportInvoiceForm.getShopId());
            List<Staff> listStaff = staffQuery.list();
            if (listStaff == null || listStaff.size() == 0) {

                //
                req.setAttribute(REQUEST_MESSAGE, "reportInvoice.error.staffNotExist");

                pageForward = REPORT_INVOICE;
                log.info("End method reportInvoice of ReportInvoiceDAO");
                return pageForward;
            }

            this.reportInvoiceForm.setStaffId(listStaff.get(0).getStaffId());
        }

        List<InvoiceDailyRpt> listInvoiceDailyRpt = new ArrayList<InvoiceDailyRpt>();
        //List<InvoiceDailyRpt> listInvoiceDailyRptGroupBySerial = new ArrayList<InvoiceDailyRpt>();
        String templatePathResource = "";

        //bao cao tong hop
        listInvoiceDailyRpt = getListGeneral();

        //copy ra 1 ban de fill du lieu vao sheet2
        List<InvoiceDailyRpt> listInvoiceDailyRpt1 = new ArrayList<InvoiceDailyRpt>();
        if (listInvoiceDailyRpt != null) {
            for (int i = 0; i < listInvoiceDailyRpt.size(); i++) {
                listInvoiceDailyRpt1.add(listInvoiceDailyRpt.get(i));
            }
        }


        //listInvoiceDailyRptGroupBySerial = getListGeneralGroupBySerial();

        //
        templatePathResource = "RI_TMP_PATH_GENERAL_1";


        //lay danh sach
//        if(groupType.equals(RR_DETAIL_BY_SHOP)) {
//            //bao cao theo chi tiet cap duoi
//            listVSaleTransDetail = getListDetailByShop();
//            if(groupBySaleTransType.equals(RR_NOT_GROUP_BY_TRANS_TYPE)) {
//                templatePathResource = "RR_TMP_PATH_DETAIL_BY_SHOP_2";
//            } else {
//                templatePathResource = "RR_TMP_PATH_DETAIL_BY_SHOP_1";
//            }
//        } else {
//            //bao cao tong hop
//            listVSaleTransDetail = getListGeneral();
//            if(groupBySaleTransType.equals(RR_NOT_GROUP_BY_TRANS_TYPE)) {
//                templatePathResource = "RR_TMP_PATH_GENERAL_2";
//            } else {
//                templatePathResource = "RR_TMP_PATH_GENERAL_1";
//            }
//        }


        //ket xuat ket qua ra file excel
        try {
            String DATE_FORMAT = "yyyyMMddHHmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            filePath = filePath != null ? filePath : "/";
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            filePath += "ReportInvoice_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
            String realPath = req.getSession().getServletContext().getRealPath(filePath);

            String templatePath = ResourceBundleUtils.getResource(templatePathResource);
            if (templatePath == null || templatePath.trim().equals("")) {

                //khong tim thay duong dan den file template de xuat ket qua
                req.setAttribute(REQUEST_MESSAGE, "reportInvoice.error.templateNotExist");

                pageForward = REPORT_INVOICE;
                return pageForward;
            }

            String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
            File fTemplateFile = new File(realTemplatePath);
            if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {

                //khong tim thay file template de xuat ket qua
                req.setAttribute(REQUEST_MESSAGE, "reportInvoice.error.templateNotExist");

                pageForward = REPORT_INVOICE;
                return pageForward;
            }

            Map beans = new HashMap();
            beans.put("listInvoiceDailyRpt", listInvoiceDailyRpt);
            beans.put("listInvoiceDailyRpt1", listInvoiceDailyRpt1);

            beans.put("shopName", shopName);
            beans.put("shopAddress", shopAddress);
            beans.put("shopTin", shopTin);
            beans.put("shopTel", shopTel);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fromDate = DateTimeUtils.convertStringToDate(strFromDate);
            Date toDate = DateTimeUtils.convertStringToDate(strToDate);
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(fromDate);
            beans.put("fromDate", simpleDateFormat.format(fromDate));
            beans.put("toDate", simpleDateFormat.format(toDate));
            beans.put("reportMonth", fromCalendar.get(Calendar.MONTH) + 1);
            beans.put("reportYear", fromCalendar.get(Calendar.YEAR));


            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(realTemplatePath, beans, realPath);

            req.setAttribute(REQUEST_REPORT_INVOICE_PATH, filePath);
            req.setAttribute(REQUEST_REPORT_INVOICE_MESSAGE, "reportInvoice.reportInvoiceMessage");

        } catch (Exception ex) {
            ex.printStackTrace();

            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.062" + ex.toString());
            return pageForward;
        }

        pageForward = REPORT_INVOICE;

        log.info("End method reportInvoice of ReportInvoiceDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 09/07/2009
     * lay du lieu bao cao tinh hinh su dung hoa don, dang tong hop
     *
     */
    private List<InvoiceDailyRpt> getListGeneral() throws Exception {

        Long shopId = this.reportInvoiceForm.getShopId();
        Long staffId = this.reportInvoiceForm.getStaffId();
        String strFromDate = this.reportInvoiceForm.getFromDate();
        String strToDate = this.reportInvoiceForm.getToDate();

        Date fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        Date toDate = DateTimeUtils.convertStringToDate(strToDate);
        toDate = DateTimeUtils.addDate(toDate, 1);

        //
        List<InvoiceDailyRpt> listBeginCyle = new ArrayList<InvoiceDailyRpt>();
        List<InvoiceDailyRpt> listInCyle = new ArrayList<InvoiceDailyRpt>();


        if (staffId != null && staffId.compareTo(0L) > 0) {
            //truong hop thong ke hoa don theo staff
            //lay du lieu ton dau ky
            List listBeginCycleParameter = new ArrayList();
            StringBuffer strBeginCycleQuery = new StringBuffer(
                    "SELECT new com.viettel.im.database.BO.InvoiceDailyRpt(a.docNo, a.serialNo, a.volumeNo, " + "(SUM(a.createdInvoiceQuantity) + SUM(a.assignedInvoiceQuantity)) " + "- (SUM(a.destroyedInvoiceQuantity) + SUM(a.deletedInvoiceQuantity) + SUM(a.retrievedInvoiceQuantity))) " + "FROM InvoiceDailyRpt a " + "WHERE a.staffId = ? AND a.createdDate < ? " + "GROUP BY a.docNo, a.serialNo, a.volumeNo " + "ORDER BY a.docNo, a.serialNo, a.volumeNo  ");
            listBeginCycleParameter.add(staffId);
            listBeginCycleParameter.add(fromDate);
            Query beginCycleQuery = getSession().createQuery(strBeginCycleQuery.toString());
            for (int i = 0; i < listBeginCycleParameter.size(); i++) {
                beginCycleQuery.setParameter(i, listBeginCycleParameter.get(i));
            }
            listBeginCyle = beginCycleQuery.list();

            //lay du lieu trong ky
            List listInCycleParameter = new ArrayList();
            StringBuffer strInCycleQuery = new StringBuffer(
                    "SELECT new com.viettel.im.database.BO.InvoiceDailyRpt( " + "a.docNo, a.serialNo, a.volumeNo " + "SUM(a.usedInvoiceQuantity), " + "SUM(a.createdInvoiceQuantity), " + "SUM(a.assignedInvoiceQuantity), " + "SUM(a.destroyedInvoiceQuantity), " + "SUM(a.deletedInvoiceQuantity), " + "SUM(a.retrievedInvoiceQuantity)) " + "FROM InvoiceDailyRpt a " + "WHERE a.staffId = ? AND a.createdDate >= ? AND a.createdDate < ? " + "GROUP BY a.docNo, a.serialNo, a.volumeNo " + "ORDER BY a.docNo, a.serialNo, a.volumeNo ");
            listInCycleParameter.add(staffId);
            listInCycleParameter.add(fromDate);
            listInCycleParameter.add(toDate);
            Query inCycleQuery = getSession().createQuery(strInCycleQuery.toString());
            for (int i = 0; i < listInCycleParameter.size(); i++) {
                inCycleQuery.setParameter(i, listInCycleParameter.get(i));
            }
            listInCyle = inCycleQuery.list();
        } else {
            //truong hop tong hop tai muc shop
            //lay du lieu ton dau ky, du lieu ton dau ky gom 2 phan:
            //  1> du lieu doi voi cac dang hoa don: tao moi, duoc gan, xoa bo, bi thu hoi
            //  2> du lieu doi voi cac dang hoa don: su dung, bi huy

            //1> voi cac dang hoa don: tao moi, duoc gan, xoa bo, bi thu hoi -> chi lay cac ban ghi ap dung voi shop hien tai
            List listBeginCycleParameter1 = new ArrayList();
            StringBuffer strBeginCycleQuery1 = new StringBuffer(
                    "SELECT new com.viettel.im.database.BO.InvoiceDailyRpt( " + "       a.docNo, a.serialNo, a.volumeNo, " + "       SUM(0), " + // as usedInvoiceQuantity
                    "       SUM(a.createdInvoiceQuantity), " + "       SUM(a.assignedInvoiceQuantity), " + "       SUM(0), " + // as destroyedInvoiceQuantity
                    "       SUM(a.deletedInvoiceQuantity), " + "       SUM(a.retrievedInvoiceQuantity)) " + "FROM InvoiceDailyRpt a " + "WHERE  1 = 1" + "       AND a.shopId = ? " + "       AND a.staffId is null " + "       AND a.createdDate < ? " + "GROUP BY a.docNo, a.serialNo, a.volumeNo " + "HAVING (SUM(a.createdInvoiceQuantity) > 0 " + "       OR SUM(a.assignedInvoiceQuantity) > 0 " + "       OR SUM(a.deletedInvoiceQuantity) > 0 " + "       OR SUM(a.retrievedInvoiceQuantity) > 0 ) " + "ORDER BY a.docNo, a.serialNo, a.volumeNo ");
            listBeginCycleParameter1.add(shopId);
            listBeginCycleParameter1.add(fromDate);
            Query beginCycleQuery1 = getSession().createQuery(strBeginCycleQuery1.toString());
            for (int i = 0; i < listBeginCycleParameter1.size(); i++) {
                beginCycleQuery1.setParameter(i, listBeginCycleParameter1.get(i));
            }
            List<InvoiceDailyRpt> listBeginCyle1 = beginCycleQuery1.list();

            //2> doi voi cac danh hoa don su dung, bi huy -> tong hop tu cap duoi len
            List listBeginCycleParameter2 = new ArrayList();
            StringBuffer strBeginCycleQuery2 = new StringBuffer(
                    "SELECT new com.viettel.im.database.BO.InvoiceDailyRpt( " + "       a.docNo, a.serialNo, a.volumeNo, " + "       SUM(a.usedInvoiceQuantity), " + "       SUM(0), " + // as createdInvoiceQuantity
                    "       SUM(0), " + // as assignedInvoiceQuantity
                    "       SUM(a.destroyedInvoiceQuantity), " + "       SUM(0), " + // as deletedInvoiceQuantity
                    "       SUM(0)) " + // as a.retrievedInvoiceQuantity
                    "FROM InvoiceDailyRpt a " + "WHERE  1= 1" + "       AND a.shopId in (select id.shopId from VShopTree where rootId = ?) " + "       AND a.staffId is not null " + "       AND a.createdDate < ? " + "GROUP BY a.docNo, a.serialNo, a.volumeNo " + "HAVING (SUM(a.usedInvoiceQuantity) > 0 " + "       OR SUM(a.destroyedInvoiceQuantity) > 0) " + "ORDER BY a.docNo, a.serialNo, a.volumeNo ");
            listBeginCycleParameter2.add(shopId);
            listBeginCycleParameter2.add(fromDate);
            Query beginCycleQuery2 = getSession().createQuery(strBeginCycleQuery2.toString());
            for (int i = 0; i < listBeginCycleParameter2.size(); i++) {
                beginCycleQuery2.setParameter(i, listBeginCycleParameter2.get(i));
            }
            List<InvoiceDailyRpt> listBeginCyle2 = beginCycleQuery2.list();

            //tong hop du lieu de ra du lieu ton dau ky
            if (listBeginCyle1 != null && listBeginCyle1.size() > 0) {
                for (int i = 0; i < listBeginCyle1.size(); i++) {
                    InvoiceDailyRpt tmpInvoiceDailyRpt1 = listBeginCyle1.get(i);
                    //du lieu ton dau ky = (createdInvoiceQuantity + assignedInvoiceQuantity) - (usedInvoiceQuantity + destroyedInvoiceQuantity + deletedInvoiceQuantity + retrievedInvoiceQuantity)
                    Long createdInvoiceQuantity = tmpInvoiceDailyRpt1.getCreatedInvoiceQuantity();
                    Long assignedInvoiceQuantity = tmpInvoiceDailyRpt1.getAssignedInvoiceQuantity();
                    Long deletedInvoiceQuantity = tmpInvoiceDailyRpt1.getDeletedInvoiceQuantity();
                    Long retrievedInvoiceQuantity = tmpInvoiceDailyRpt1.getRetrievedInvoiceQuantity();

                    Long usedInvoiceQuantity = tmpInvoiceDailyRpt1.getUsedInvoiceQuantity();
                    Long destroyedInvoiceQuantity = tmpInvoiceDailyRpt1.getDestroyedInvoiceQuantity();

                    Long remainQuantity = (createdInvoiceQuantity + assignedInvoiceQuantity) - (usedInvoiceQuantity + destroyedInvoiceQuantity + deletedInvoiceQuantity + retrievedInvoiceQuantity);

                    tmpInvoiceDailyRpt1.setRemainQuantity(remainQuantity);
                    listBeginCyle.add(tmpInvoiceDailyRpt1);
                }
            }
            if (listBeginCyle2 != null && listBeginCyle2.size() > 0) {
                for (int i = 0; i < listBeginCyle2.size(); i++) {
                    //
                    InvoiceDailyRpt tmpInvoiceDailyRpt2 = listBeginCyle2.get(i);
                    boolean flag = false;
                    for (int j = 0; j < listBeginCyle.size(); j++) {
                        InvoiceDailyRpt tmpInvoiceDailyRpt = listBeginCyle.get(j);
                        if (tmpInvoiceDailyRpt2.getSerialNo().equals(tmpInvoiceDailyRpt.getSerialNo()) && tmpInvoiceDailyRpt2.getVolumeNo().equals(tmpInvoiceDailyRpt.getVolumeNo())) {
                            //neu trong list da ton tai serial va block, chi gop phan du lieu ton dau ky
                            //du lieu ton dau ky = (createdInvoiceQuantity + assignedInvoiceQuantity) - (usedInvoiceQuantity + destroyedInvoiceQuantity + deletedInvoiceQuantity + retrievedInvoiceQuantity)
                            Long createdInvoiceQuantity = tmpInvoiceDailyRpt.getCreatedInvoiceQuantity();
                            Long assignedInvoiceQuantity = tmpInvoiceDailyRpt.getAssignedInvoiceQuantity();
                            Long deletedInvoiceQuantity = tmpInvoiceDailyRpt.getDeletedInvoiceQuantity();
                            Long retrievedInvoiceQuantity = tmpInvoiceDailyRpt.getRetrievedInvoiceQuantity();

                            Long usedInvoiceQuantity = tmpInvoiceDailyRpt2.getUsedInvoiceQuantity();
                            Long destroyedInvoiceQuantity = tmpInvoiceDailyRpt2.getDestroyedInvoiceQuantity();

                            Long remainQuantity = (createdInvoiceQuantity + assignedInvoiceQuantity) - (usedInvoiceQuantity + destroyedInvoiceQuantity + deletedInvoiceQuantity + retrievedInvoiceQuantity);

                            tmpInvoiceDailyRpt.setRemainQuantity(remainQuantity);
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        //truong hop chua ton tai trong list -> them moi
                        //du lieu ton dau ky = (createdInvoiceQuantity + assignedInvoiceQuantity) - (usedInvoiceQuantity + destroyedInvoiceQuantity + deletedInvoiceQuantity + retrievedInvoiceQuantity)
                        Long createdInvoiceQuantity = tmpInvoiceDailyRpt2.getCreatedInvoiceQuantity();
                        Long assignedInvoiceQuantity = tmpInvoiceDailyRpt2.getAssignedInvoiceQuantity();
                        Long deletedInvoiceQuantity = tmpInvoiceDailyRpt2.getDeletedInvoiceQuantity();
                        Long retrievedInvoiceQuantity = tmpInvoiceDailyRpt2.getRetrievedInvoiceQuantity();

                        Long usedInvoiceQuantity = tmpInvoiceDailyRpt2.getUsedInvoiceQuantity();
                        Long destroyedInvoiceQuantity = tmpInvoiceDailyRpt2.getDestroyedInvoiceQuantity();

                        Long remainQuantity = (createdInvoiceQuantity + assignedInvoiceQuantity) - (usedInvoiceQuantity + destroyedInvoiceQuantity + deletedInvoiceQuantity + retrievedInvoiceQuantity);

                        tmpInvoiceDailyRpt2.setRemainQuantity(remainQuantity);
                        listBeginCyle.add(tmpInvoiceDailyRpt2);
                    }

                }
            }

            //lay du lieu trong ky, du lieu trong ky bao gom 2 phan:
            //  1> du lieu doi voi cac dang hoa don: tao moi, duoc gan, xoa bo, bi thu hoi
            //  2> du lieu doi voi cac dang hoa don: su dung, bi huy

            //1> voi cac dang hoa don: tao moi, duoc gan, xoa bo, bi thu hoi -> chi lay cac ban ghi ap dung voi shop hien tai
            List listInCycleParameter1 = new ArrayList();
            StringBuffer strInCycleQuery1 = new StringBuffer(
                    "SELECT new com.viettel.im.database.BO.InvoiceDailyRpt( " + "       a.docNo, a.serialNo, a.volumeNo, " + "       SUM(0), " + // as usedInvoiceQuantity
                    "       SUM(a.createdInvoiceQuantity), " + "       SUM(a.assignedInvoiceQuantity), " + "       SUM(0), " + // as destroyedInvoiceQuantity
                    "       SUM(a.deletedInvoiceQuantity), " + "       SUM(a.retrievedInvoiceQuantity)) " + "FROM InvoiceDailyRpt a " + "WHERE  1 = 1" + "       AND a.shopId = ? " + "       AND a.staffId is null " + "       AND a.createdDate >= ? " + "       AND a.createdDate < ? " + "GROUP BY a.docNo, a.serialNo, a.volumeNo " + "HAVING (SUM(a.createdInvoiceQuantity) > 0 " + "       OR SUM(a.assignedInvoiceQuantity) > 0 " + "       OR SUM(a.deletedInvoiceQuantity) > 0 " + "       OR SUM(a.retrievedInvoiceQuantity) > 0 ) " + "ORDER BY a.docNo, a.serialNo, a.volumeNo ");
            listInCycleParameter1.add(shopId);
            listInCycleParameter1.add(fromDate);
            listInCycleParameter1.add(toDate);
            Query inCycleQuery1 = getSession().createQuery(strInCycleQuery1.toString());
            for (int i = 0; i < listInCycleParameter1.size(); i++) {
                inCycleQuery1.setParameter(i, listInCycleParameter1.get(i));
            }
            List<InvoiceDailyRpt> listInCyle1 = inCycleQuery1.list();

            //2> doi voi cac danh hoa don su dung, bi huy -> tong hop tu cap duoi len
            List listInCycleParameter2 = new ArrayList();
            StringBuffer strInCycleQuery2 = new StringBuffer(
                    "SELECT new com.viettel.im.database.BO.InvoiceDailyRpt( " + "       a.docNo, a.serialNo, a.volumeNo, " + "       SUM(a.usedInvoiceQuantity), " + "       SUM(0), " + // as createdInvoiceQuantity
                    "       SUM(0), " + // as assignedInvoiceQuantity
                    "       SUM(a.destroyedInvoiceQuantity), " + "       SUM(0), " + // as deletedInvoiceQuantity
                    "       SUM(0)) " + // as a.retrievedInvoiceQuantity
                    "FROM InvoiceDailyRpt a " + "WHERE  1= 1" + "       AND a.shopId in (select id.shopId from VShopTree where rootId = ?) " + "       AND a.staffId is not null " + "       AND a.createdDate >= ? " + "       AND a.createdDate < ? " + "GROUP BY a.docNo, a.serialNo, a.volumeNo " + "HAVING (SUM(a.usedInvoiceQuantity) > 0 " + "       OR SUM(a.destroyedInvoiceQuantity) > 0) " + "ORDER BY a.docNo, a.serialNo, a.volumeNo ");
            listInCycleParameter2.add(shopId);
            listInCycleParameter2.add(fromDate);
            listInCycleParameter2.add(toDate);
            Query inCycleQuery2 = getSession().createQuery(strInCycleQuery2.toString());
            for (int i = 0; i < listInCycleParameter2.size(); i++) {
                inCycleQuery2.setParameter(i, listInCycleParameter2.get(i));
            }
            List<InvoiceDailyRpt> listInCyle2 = inCycleQuery2.list();

            //tong hop du lieu trong ky
            if (listInCyle1 != null && listInCyle1.size() > 0) {
                for (int i = 0; i < listInCyle1.size(); i++) {
                    listInCyle.add(listInCyle1.get(i));
                }
            }
            if (listInCyle2 != null && listInCyle2.size() > 0) {
                for (int i = 0; i < listInCyle2.size(); i++) {
                    //
                    InvoiceDailyRpt tmpInvoiceDailyRpt = listInCyle2.get(i);
                    boolean flag = false;
                    for (int j = 0; j < listInCyle.size(); j++) {
                        if (tmpInvoiceDailyRpt.getSerialNo().equals(listInCyle.get(j).getSerialNo()) && tmpInvoiceDailyRpt.getVolumeNo().equals(listInCyle.get(j).getVolumeNo())) {
                            //neu trong list da ton tai serial va block, chi gop phan du lieu hoa don su dung va hoa don bi huy
                            listInCyle.get(j).setUsedInvoiceQuantity(tmpInvoiceDailyRpt.getUsedInvoiceQuantity());
                            listInCyle.get(j).setDestroyedInvoiceQuantity(tmpInvoiceDailyRpt.getDestroyedInvoiceQuantity());
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        //truong hop chua ton tai trong list -> them moi
                        listInCyle.add(tmpInvoiceDailyRpt);
                    }
                }
            }
        }

        //tong hop = du lieu ton dau ky + du lieu trong ky
        List<InvoiceDailyRpt> listInvoiceDailyRpt = new ArrayList<InvoiceDailyRpt>();
        if (listInCyle != null && listInCyle.size() > 0) {
            for (int i = 0; i < listInCyle.size(); i++) {
                InvoiceDailyRpt tmpInvoiceDailyRpt = listInCyle.get(i);
                tmpInvoiceDailyRpt.setRemainQuantity(0L);
                listInvoiceDailyRpt.add(tmpInvoiceDailyRpt);
            }
        }
        if (listBeginCyle != null && listBeginCyle.size() > 0) {
            for (int i = 0; i < listBeginCyle.size(); i++) {
                //
                InvoiceDailyRpt tmpInvoiceDailyRpt = listBeginCyle.get(i);
                boolean flag = false;
                for (int j = 0; j < listInvoiceDailyRpt.size(); j++) {
                    if (tmpInvoiceDailyRpt.getSerialNo().equals(listInvoiceDailyRpt.get(j).getSerialNo()) && tmpInvoiceDailyRpt.getVolumeNo().equals(listInvoiceDailyRpt.get(j).getVolumeNo())) {
                        //neu trong list da ton tai serial va block, chi gop phan du lieu ton dau ky
                        listInvoiceDailyRpt.get(j).setRemainQuantity(tmpInvoiceDailyRpt.getRemainQuantity());
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    //truong hop chua ton tai trong list -> them moi
                    listInvoiceDailyRpt.add(tmpInvoiceDailyRpt);
                }
            }
        }

        return listInvoiceDailyRpt;

    }

    /**
     *
     * author       : tamdt1
     * date         : 09/07/2009
     * desc         : bao cao tinh hinh su dung hoa don, xuat ra file excel, co chi tiet fromInvoice, toInvoice
     *
     */
    public String reportInvoiceV2() throws Exception {
        log.info("Begin method reportInvoiceV2 of ReportInvoiceDAO...");

        HttpServletRequest req = getRequest();

        String strFromDate = this.reportInvoiceForm.getFromDate();
        String strToDate = this.reportInvoiceForm.getToDate();
        Long notIncludeChild = this.reportInvoiceForm.getNotIncludeChild();
        Long groupType = this.reportInvoiceForm.getGroupType();



        if (!checkValidReportInvoice()) {

            pageForward = REPORT_INVOICE_V2;
            log.info("End method reportInvoice of ReportInvoiceDAO");
            return pageForward;
        }

        String shopCode = this.reportInvoiceForm.getShopCode().trim();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List<Shop> listShop = shopDAO.findByPropertyWithStatus(ShopDAO.SHOP_CODE, shopCode, Constant.STATUS_USE);
        if (listShop == null || listShop.size() == 0) {

            //
            req.setAttribute(REQUEST_MESSAGE, "reportInvoice.error.shopNotExist");

            pageForward = REPORT_INVOICE_V2;
            log.info("End method reportInvoice of ReportInvoiceDAO");
            return pageForward;
        }

        String shopName = listShop.get(0).getName();
        String shopAddress = listShop.get(0).getAddress();
        String shopTin = listShop.get(0).getTin();
        String shopTel = listShop.get(0).getTel();
        this.reportInvoiceForm.setShopId(listShop.get(0).getShopId());

        String staffCode = this.reportInvoiceForm.getStaffCode();
        if (staffCode != null && !staffCode.trim().equals("")) {
            String strStaffQuery = "from Staff where staffCode = ? and status = ? and shopId = ? ";
            Query staffQuery = getSession().createQuery(strStaffQuery);
            staffQuery.setParameter(0, staffCode.trim());
            staffQuery.setParameter(1, Constant.STATUS_USE);
            staffQuery.setParameter(2, this.reportInvoiceForm.getShopId());
            List<Staff> listStaff = staffQuery.list();
            if (listStaff == null || listStaff.size() == 0) {

                //
                req.setAttribute(REQUEST_MESSAGE, "reportInvoice.error.staffNotExist");

                pageForward = REPORT_INVOICE;
                log.info("End method reportInvoice of ReportInvoiceDAO");
                return pageForward;
            }

            this.reportInvoiceForm.setStaffId(listStaff.get(0).getStaffId());
        }

        List<InvoiceDailyRptV2Bean> listInvoiceDailyRpt = new ArrayList<InvoiceDailyRptV2Bean>();
        //List<InvoiceDailyRpt> listInvoiceDailyRptGroupBySerial = new ArrayList<InvoiceDailyRpt>();
        String templatePathResource = "";

        //bao cao tong hop
        listInvoiceDailyRpt = getListGeneralV2();


        //listInvoiceDailyRptGroupBySerial = getListGeneralGroupBySerial();

        //
        templatePathResource = "RI_TMP_PATH_GENERAL_V2";


        //lay danh sach
//        if(groupType.equals(RR_DETAIL_BY_SHOP)) {
//            //bao cao theo chi tiet cap duoi
//            listVSaleTransDetail = getListDetailByShop();
//            if(groupBySaleTransType.equals(RR_NOT_GROUP_BY_TRANS_TYPE)) {
//                templatePathResource = "RR_TMP_PATH_DETAIL_BY_SHOP_2";
//            } else {
//                templatePathResource = "RR_TMP_PATH_DETAIL_BY_SHOP_1";
//            }
//        } else {
//            //bao cao tong hop
//            listVSaleTransDetail = getListGeneral();
//            if(groupBySaleTransType.equals(RR_NOT_GROUP_BY_TRANS_TYPE)) {
//                templatePathResource = "RR_TMP_PATH_GENERAL_2";
//            } else {
//                templatePathResource = "RR_TMP_PATH_GENERAL_1";
//            }
//        }


        //ket xuat ket qua ra file excel
        try {
            String DATE_FORMAT = "yyyyMMddHHmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE_2");
            filePath = filePath != null ? filePath : "/";
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            filePath += "ReportInvoice_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
            //String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String realPath = filePath;
            String templatePath = ResourceBundleUtils.getResource(templatePathResource);
            if (templatePath == null || templatePath.trim().equals("")) {

                //khong tim thay duong dan den file template de xuat ket qua
                req.setAttribute(REQUEST_MESSAGE, "reportInvoice.error.templateNotExist");

                pageForward = REPORT_INVOICE;
                return pageForward;
            }

            String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
            File fTemplateFile = new File(realTemplatePath);
            if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {

                //khong tim thay file template de xuat ket qua
                req.setAttribute(REQUEST_MESSAGE, "reportInvoice.error.templateNotExist");

                pageForward = REPORT_INVOICE;
                return pageForward;
            }

            Map beans = new HashMap();
            beans.put("listInvoiceDailyRpt", listInvoiceDailyRpt);
            //beans.put("listInvoiceDailyRpt1", listInvoiceDailyRpt1);

            beans.put("shopName", shopName);
            beans.put("shopAddress", shopAddress);
            beans.put("shopTin", shopTin);
            beans.put("shopTel", shopTel);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fromDate = DateTimeUtils.convertStringToDate(strFromDate);
            Date toDate = DateTimeUtils.convertStringToDate(strToDate);
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(fromDate);
            beans.put("fromDate", simpleDateFormat.format(fromDate));
            beans.put("toDate", simpleDateFormat.format(toDate));
            beans.put("reportMonth", fromCalendar.get(Calendar.MONTH) + 1);
            beans.put("reportYear", fromCalendar.get(Calendar.YEAR));


            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(realTemplatePath, beans, realPath);

            //req.setAttribute(REQUEST_REPORT_INVOICE_PATH, filePath);
            DownloadDAO downloadDAO = new DownloadDAO();
            req.setAttribute("REQUEST_REPORT_INVOICE_PATH", downloadDAO.getFileNameEncNew(filePath, req.getSession()));
            req.setAttribute(REQUEST_REPORT_INVOICE_MESSAGE, "reportInvoice.reportInvoiceMessage");
            

        } catch (Exception ex) {
            ex.printStackTrace();

            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.062 " + ex.toString());
            return pageForward;
        }

        pageForward = REPORT_INVOICE_V2;

        log.info("End method reportInvoiceV2 of ReportInvoiceDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 09/03/2010
     * lay du lieu bao cao tinh hinh su dung hoa don
     *
     */
    private List<InvoiceDailyRptV2Bean> getListGeneralV2() throws Exception {

        Long shopId = this.reportInvoiceForm.getShopId();
        Long staffId = this.reportInvoiceForm.getStaffId();
        String strFromDate = this.reportInvoiceForm.getFromDate();
        String strToDate = this.reportInvoiceForm.getToDate();

        Date fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        Date toDate = DateTimeUtils.convertStringToDate(strToDate);
        toDate = DateTimeUtils.addDate(toDate, 1);

        //
        List<InvoiceDailyRptV2Bean> listInCyle = new ArrayList<InvoiceDailyRptV2Bean>();


        if (staffId != null && staffId.compareTo(0L) > 0) {
//            //truong hop thong ke hoa don theo staff
//            //lay du lieu ton dau ky
//            List listBeginCycleParameter = new ArrayList();
//            StringBuffer strBeginCycleQuery = new StringBuffer(
//                    "SELECT new com.viettel.im.database.BO.InvoiceDailyRpt(a.docNo, a.serialNo, a.volumeNo, " +
//                    "(SUM(a.createdInvoiceQuantity) + SUM(a.assignedInvoiceQuantity)) " +
//                    "- (SUM(a.destroyedInvoiceQuantity) + SUM(a.deletedInvoiceQuantity) + SUM(a.retrievedInvoiceQuantity))) " +
//                    "FROM InvoiceDailyRpt a " +
//                    "WHERE a.staffId = ? AND a.createdDate < ? " +
//                    "GROUP BY a.docNo, a.serialNo, a.volumeNo " +
//                    "ORDER BY a.docNo, a.serialNo, a.volumeNo  ");
//            listBeginCycleParameter.add(staffId);
//            listBeginCycleParameter.add(fromDate);
//            Query beginCycleQuery = getSession().createQuery(strBeginCycleQuery.toString());
//            for (int i = 0; i < listBeginCycleParameter.size(); i++) {
//                beginCycleQuery.setParameter(i, listBeginCycleParameter.get(i));
//            }
//            listBeginCyle = beginCycleQuery.list();
//
//            //lay du lieu trong ky
//            List listInCycleParameter = new ArrayList();
//            StringBuffer strInCycleQuery = new StringBuffer(
//                    "SELECT new com.viettel.im.database.BO.InvoiceDailyRpt( " +
//                    "a.docNo, a.serialNo, a.volumeNo " +
//                    "SUM(a.usedInvoiceQuantity), " +
//                    "SUM(a.createdInvoiceQuantity), " +
//                    "SUM(a.assignedInvoiceQuantity), " +
//                    "SUM(a.destroyedInvoiceQuantity), " +
//                    "SUM(a.deletedInvoiceQuantity), " +
//                    "SUM(a.retrievedInvoiceQuantity)) " +
//                    "FROM InvoiceDailyRpt a " +
//                    "WHERE a.staffId = ? AND a.createdDate >= ? AND a.createdDate < ? " +
//                    "GROUP BY a.docNo, a.serialNo, a.volumeNo " +
//                    "ORDER BY a.docNo, a.serialNo, a.volumeNo ");
//            listInCycleParameter.add(staffId);
//            listInCycleParameter.add(fromDate);
//            listInCycleParameter.add(toDate);
//            Query inCycleQuery = getSession().createQuery(strInCycleQuery.toString());
//            for (int i = 0; i < listInCycleParameter.size(); i++) {
//                inCycleQuery.setParameter(i, listInCycleParameter.get(i));
//            }
//            listInCyle = inCycleQuery.list();
        } else {
            //truong hop tong hop tai muc shop
            //lay du lieu ton dau ky:
            List<InvoiceDailyRptV2Bean> listBeginCyle = getListInvoiceBeginCycle();
            //gop vao du lieu trong ky
            if (listBeginCyle != null && listBeginCyle.size() > 0) {
                for (int i = 0; i < listBeginCyle.size(); i++) {
                    listInCyle.add(listBeginCyle.get(i));
                }
            }

            //lay du lieu trong ky, du lieu trong ky bao gom cac phan:
            //  1> so luong hoa don tao moi, duoc gan
            //  2> so luong hoa don su dung
            //  3> so luong hoa don huy
            //  4> so luong hoa don mat
            //  5> so luong hoa don tra lai

            //1> voi cac dang hoa don tao moi, duoc gan -> chi lay cac ban ghi ap dung voi shop hien tai
            List listReceivedParameter = new ArrayList();
            StringBuffer strReceivedQuery = new StringBuffer(
                    "SELECT new com.viettel.im.client.bean.InvoiceDailyRptV2Bean( " + "       a.shopId, a.staffId, " + "       a.invoiceListId, a.docNo, a.serialNo, a.volumeNo, " + //                    "       0, 0, 0, " + //so luong hoa don ton dau ky
                    "       a.fromInvoice, a.toInvoice, (a.toInvoice - a.fromInvoice + 1), " + //so luong hoa don nhan trong ky
                    "       a.fromInvoice, a.toInvoice, (a.toInvoice - a.fromInvoice + 1)) " + //so luong hoa don ton cuoi thang
                    "FROM InvoiceDailyRptV2 a " + "WHERE  1 = 1" + "       AND a.shopId = ? " + "       AND a.staffId is null " + "       AND a.actionType = ? " + "       AND a.status = ? " + "       AND a.invoiceDate >= ? " + "       AND a.invoiceDate < ? " + "ORDER BY a.docNo, a.serialNo, a.volumeNo, a.fromInvoice ");
            listReceivedParameter.add(shopId);
            listReceivedParameter.add(Constant.INV_RPT_ACTION_TYPE_RECEIVED);
            listReceivedParameter.add(Constant.STATUS_ACTIVE);
            listReceivedParameter.add(fromDate);
            listReceivedParameter.add(toDate);
            Query receivedQuery = getSession().createQuery(strReceivedQuery.toString());
            for (int i = 0; i < listReceivedParameter.size(); i++) {
                receivedQuery.setParameter(i, listReceivedParameter.get(i));
            }
            List<InvoiceDailyRptV2Bean> listReceived = receivedQuery.list();

            //gop vao du lieu trong ky
            if (listReceived != null && listReceived.size() > 0) {
                for (int i = 0; i < listReceived.size(); i++) {
                    listInCyle.add(listReceived.get(i));
                }
            }

            //2> doi voi cac hoa don su dung -> tong hop tu cap duoi len
            List listUsedParameter = new ArrayList();
            StringBuffer strUsedQuery = new StringBuffer(
                    "SELECT shop_id shopId, staff_id staffId, " + "       invoice_list_id invoiceListId, doc_no docNo, serial_no serialNo, volume_no volumeNo, " + "       MIN (from_invoice) fromInvoice, MAX (from_invoice) toInvoice " + "FROM ( SELECT  shop_id, staff_id, " + "               invoice_list_id, doc_no, serial_no, volume_no, " + "               from_invoice, " + "               from_invoice - ROW_NUMBER () OVER (ORDER BY from_invoice) rn " + "       FROM invoice_daily_rpt_v2 " + "       WHERE   1 = 1 " + "               AND shop_id in (select shop_id from v_shop_tree where root_id = ?) " + "               AND action_type = ? " + "               AND status = ? " + "               AND invoice_date >= ? " + "               AND invoice_date < ?) " + "GROUP BY rn, shop_id, staff_id, invoice_list_id, doc_no, serial_no, volume_no " + "ORDER BY fromInvoice ");

            Query usedQuery = getSession().createSQLQuery(strUsedQuery.toString()).
                    addScalar("invoiceListId", Hibernate.LONG).
                    addScalar("docNo", Hibernate.STRING).
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("volumeNo", Hibernate.LONG).
                    addScalar("fromInvoice", Hibernate.LONG).
                    addScalar("toInvoice", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(InvoiceDailyRptV2.class));

            listUsedParameter.add(shopId);
            listUsedParameter.add(Constant.INV_RPT_ACTION_TYPE_USED);
            listUsedParameter.add(Constant.STATUS_ACTIVE);
            listUsedParameter.add(fromDate);
            listUsedParameter.add(toDate);

            for (int i = 0; i < listUsedParameter.size(); i++) {
                usedQuery.setParameter(i, listUsedParameter.get(i));
            }
            List<InvoiceDailyRptV2> listUsed = usedQuery.list();

            //
            if (listUsed != null && listUsed.size() > 0) {
                for (int i = 0; i < listUsed.size(); i++) {
                    InvoiceDailyRptV2 tmpUsed = listUsed.get(i);
                    for (int j = 0; j < listInCyle.size(); j++) {
                        InvoiceDailyRptV2Bean tmpInCycle = listInCyle.get(j);
                        if (tmpUsed.getInvoiceListId().equals(tmpInCycle.getInvoiceListId()) && tmpUsed.getDocNo().equals(tmpInCycle.getDocNo()) && tmpUsed.getSerialNo().equals(tmpInCycle.getSerialNo()) && tmpUsed.getVolumeNo().equals(tmpInCycle.getVolumeNo())) {

                            //gop phan du lieu hoa don su dung
                            Long tmpUsedQuantity = 0L;
                            if (tmpInCycle.getUsedInvoice() == null || tmpInCycle.getUsedInvoice().equals("")) {
                                tmpInCycle.setUsedInvoice(tmpUsed.getFromInvoice() + "->" + tmpUsed.getToInvoice());
                                tmpUsedQuantity = tmpUsed.getToInvoice() - tmpUsed.getFromInvoice() + 1;
                                tmpInCycle.setUsedQuantity(tmpUsedQuantity);
                            } else {
                                tmpInCycle.setUsedInvoice(tmpInCycle.getUsedInvoice() + ", " + tmpUsed.getFromInvoice() + " -> " + tmpUsed.getToInvoice());
                                tmpUsedQuantity = tmpUsed.getToInvoice() - tmpUsed.getFromInvoice() + 1;
                                tmpInCycle.setUsedQuantity(tmpInCycle.getUsedQuantity() + tmpUsedQuantity);
                            }

                            //tru phan hoa don ton cuoi ky
                            if (tmpInCycle.getRemainedEndCycleToInvoice().equals(tmpUsed.getToInvoice())) {
                                tmpInCycle.setRemainedEndCycleFromInvoice(0L);
                                tmpInCycle.setRemainedEndCycleToInvoice(0L);
                                tmpInCycle.setRemainedEndCycleQuantity(0L);
                            } else {
                                tmpInCycle.setRemainedEndCycleFromInvoice(tmpUsed.getToInvoice() + 1);
                                tmpInCycle.setRemainedEndCycleQuantity(tmpInCycle.getRemainedEndCycleQuantity() - tmpUsedQuantity);
                            }

                            break;
                        }
                    }
                }
            }

            //3> doi voi cac danh hoa don so luong hoa don huy -> tong hop tu cap duoi len
            List listDestroyedParameter = new ArrayList();
            StringBuffer strDestroyedQuery = new StringBuffer(
                    "SELECT shop_id shopId, staff_id staffId, " + "       invoice_list_id invoiceListId, doc_no docNo, serial_no serialNo, volume_no volumeNo, " + "       MIN (from_invoice) fromInvoice, MAX (from_invoice) toInvoice " + "FROM ( SELECT  shop_id, staff_id, " + "               invoice_list_id, doc_no, serial_no, volume_no, " + "               from_invoice, " + "               from_invoice - ROW_NUMBER () OVER (ORDER BY from_invoice) rn " + "       FROM invoice_daily_rpt_v2 " + "       WHERE   1 = 1 " + "               AND shop_id in (select shop_id from v_shop_tree where root_id = ?) " + "               AND action_type = ? " + "               AND status = ? " + "               AND invoice_date >= ? " + "               AND invoice_date < ?) " + "GROUP BY rn, shop_id, staff_id, invoice_list_id, doc_no, serial_no, volume_no " + "ORDER BY fromInvoice ");

            Query destroyedQuery = getSession().createSQLQuery(strDestroyedQuery.toString()).
                    addScalar("invoiceListId", Hibernate.LONG).
                    addScalar("docNo", Hibernate.STRING).
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("volumeNo", Hibernate.LONG).
                    addScalar("fromInvoice", Hibernate.LONG).
                    addScalar("toInvoice", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(InvoiceDailyRptV2.class));

            listDestroyedParameter.add(shopId);
            listDestroyedParameter.add(Constant.INV_RPT_ACTION_TYPE_DESTROYED);
            listDestroyedParameter.add(Constant.STATUS_ACTIVE);
            listDestroyedParameter.add(fromDate);
            listDestroyedParameter.add(toDate);

            for (int i = 0; i < listDestroyedParameter.size(); i++) {
                destroyedQuery.setParameter(i, listDestroyedParameter.get(i));
            }
            List<InvoiceDailyRptV2> listDestroyed = destroyedQuery.list();

            if (listDestroyed != null && listDestroyed.size() > 0) {
                for (int i = 0; i < listDestroyed.size(); i++) {
                    InvoiceDailyRptV2 tmpDestroyed = listDestroyed.get(i);
                    for (int j = 0; j < listInCyle.size(); j++) {
                        InvoiceDailyRptV2Bean tmpInCycle = listInCyle.get(j);
                        if (tmpDestroyed.getInvoiceListId().equals(tmpInCycle.getInvoiceListId()) && tmpDestroyed.getDocNo().equals(tmpInCycle.getDocNo()) && tmpDestroyed.getSerialNo().equals(tmpInCycle.getSerialNo()) && tmpDestroyed.getVolumeNo().equals(tmpInCycle.getVolumeNo())) {

                            //gop phan du lieu hoa don bi huy
                            Long tmpDestroyedQuantity = 0L;
                            if (tmpInCycle.getDestroyedInvoice() == null || tmpInCycle.getDestroyedInvoice().equals("")) {
                                tmpInCycle.setDestroyedInvoice(tmpDestroyed.getFromInvoice() + "->" + tmpDestroyed.getToInvoice());
                                tmpDestroyedQuantity = tmpDestroyed.getToInvoice() - tmpDestroyed.getFromInvoice() + 1;
                                tmpInCycle.setDestroyedQuantity(tmpDestroyedQuantity);
                            } else {
                                tmpInCycle.setDestroyedInvoice(tmpInCycle.getDestroyedInvoice() + ", " + tmpDestroyed.getFromInvoice() + " -> " + tmpDestroyed.getToInvoice());
                                tmpDestroyedQuantity = tmpDestroyed.getToInvoice() - tmpDestroyed.getFromInvoice() + 1;
                                tmpInCycle.setDestroyedQuantity(tmpInCycle.getDestroyedQuantity() + tmpDestroyedQuantity);
                            }

                            //tru phan hoa don ton cuoi ky
                            if (tmpInCycle.getRemainedEndCycleToInvoice().equals(tmpDestroyed.getToInvoice())) {
                                tmpInCycle.setRemainedEndCycleFromInvoice(0L);
                                tmpInCycle.setRemainedEndCycleToInvoice(0L);
                                tmpInCycle.setRemainedEndCycleQuantity(0L);
                            } else if (tmpInCycle.getRemainedEndCycleFromInvoice().compareTo(tmpDestroyed.getToInvoice()) <= 0) {
                                //co dieu kien nay, vi co the xay ra truong hop, hoa don bi huy co so thu tu < so thu tu cua hoa don su dung da thiet lap o buoc 2
                                tmpInCycle.setRemainedEndCycleFromInvoice(tmpDestroyed.getToInvoice() + 1);
                                tmpInCycle.setRemainedEndCycleQuantity(tmpInCycle.getRemainedEndCycleQuantity() - tmpDestroyedQuantity);
                            } else {
                                tmpInCycle.setRemainedEndCycleQuantity(tmpInCycle.getRemainedEndCycleQuantity() - tmpDestroyedQuantity);
                            }

                            break;
                        }
                    }
                }
            }

            //4> doi voi so luong hoa don mat -> tong hop tu cap duoi len
            List listLosedParameter = new ArrayList();
            StringBuffer strLosedQuery = new StringBuffer(
                    "SELECT shop_id shopId, staff_id staffId, " + "       invoice_list_id invoiceListId, doc_no docNo, serial_no serialNo, volume_no volumeNo, " + "       from_invoice fromInvoice, to_invoice toInvoice " + "FROM   invoice_daily_rpt_v2 " + "WHERE  1 = 1 " + "       AND shop_id in (select shop_id from v_shop_tree where root_id = ?) " + "       AND action_type = ? " + "       AND status = ? " + "       AND invoice_date >= ? " + "       AND invoice_date < ? " + "ORDER BY fromInvoice ");

            Query losedQuery = getSession().createSQLQuery(strLosedQuery.toString()).
                    addScalar("invoiceListId", Hibernate.LONG).
                    addScalar("docNo", Hibernate.STRING).
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("volumeNo", Hibernate.LONG).
                    addScalar("fromInvoice", Hibernate.LONG).
                    addScalar("toInvoice", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(InvoiceDailyRptV2.class));

            listLosedParameter.add(shopId);
            listLosedParameter.add(Constant.INV_RPT_ACTION_TYPE_LOSED);
            listLosedParameter.add(Constant.STATUS_ACTIVE);
            listLosedParameter.add(fromDate);
            listLosedParameter.add(toDate);

            for (int i = 0; i < listLosedParameter.size(); i++) {
                losedQuery.setParameter(i, listLosedParameter.get(i));
            }
            List<InvoiceDailyRptV2> listLosed = losedQuery.list();

            if (listLosed != null && listLosed.size() > 0) {
                for (int i = 0; i < listLosed.size(); i++) {
                    InvoiceDailyRptV2 tmpLosed = listLosed.get(i);
                    for (int j = 0; j < listInCyle.size(); j++) {
                        InvoiceDailyRptV2Bean tmpInCycle = listInCyle.get(j);
                        if (tmpLosed.getInvoiceListId().equals(tmpInCycle.getInvoiceListId()) && tmpLosed.getDocNo().equals(tmpInCycle.getDocNo()) && tmpLosed.getSerialNo().equals(tmpInCycle.getSerialNo()) && tmpLosed.getVolumeNo().equals(tmpInCycle.getVolumeNo())) {

                            //gop phan du lieu hoa don bi mat
                            Long tmpLosedQuantity = 0L;
                            if (tmpInCycle.getLosedInvoice() == null || tmpInCycle.getLosedInvoice().equals("")) {
                                tmpInCycle.setLosedInvoice(tmpLosed.getFromInvoice() + "->" + tmpLosed.getToInvoice());
                                tmpLosedQuantity = tmpLosed.getToInvoice() - tmpLosed.getFromInvoice() + 1;
                                tmpInCycle.setLosedQuantity(tmpLosedQuantity);
                            } else {
                                tmpInCycle.setLosedInvoice(tmpInCycle.getLosedInvoice() + ", " + tmpLosed.getFromInvoice() + " -> " + tmpLosed.getToInvoice());
                                tmpLosedQuantity = tmpLosed.getToInvoice() - tmpLosed.getFromInvoice() + 1;
                                tmpInCycle.setLosedQuantity(tmpInCycle.getLosedQuantity() + tmpLosedQuantity);
                            }

                            //tru phan hoa don ton cuoi ky
                            if (tmpInCycle.getRemainedEndCycleToInvoice().equals(tmpLosed.getToInvoice())) {
                                tmpInCycle.setRemainedEndCycleFromInvoice(0L);
                                tmpInCycle.setRemainedEndCycleToInvoice(0L);
                                tmpInCycle.setRemainedEndCycleQuantity(0L);
                            } else if (tmpInCycle.getRemainedEndCycleFromInvoice().compareTo(tmpLosed.getToInvoice()) <= 0) {
                                tmpInCycle.setRemainedEndCycleFromInvoice(tmpLosed.getToInvoice() + 1);
                                tmpInCycle.setRemainedEndCycleQuantity(tmpInCycle.getRemainedEndCycleQuantity() - tmpLosedQuantity);
                            } else {
                                tmpInCycle.setRemainedEndCycleQuantity(tmpInCycle.getRemainedEndCycleQuantity() - tmpLosedQuantity);
                            }

                            break;
                        }
                    }
                }
            }

            //5> doi voi so luong hoa tra lai -> chi lay shop hien tai
            List listReturnedParameter = new ArrayList();
            StringBuffer strReturnedQuery = new StringBuffer(
                    "SELECT shop_id shopId, staff_id staffId, " + "       invoice_list_id invoiceListId, doc_no docNo, serial_no serialNo, volume_no volumeNo, " + "       from_invoice fromInvoice, to_invoice toInvoice " + "FROM   invoice_daily_rpt_v2 " + "WHERE  1 = 1 " + "       AND shop_id = ? " + "       AND staff_id is null " + "       AND action_type = ? " + "       AND status = ? " + "       AND invoice_date >= ? " + "       AND invoice_date < ? " + "ORDER BY fromInvoice ");

            Query returnedQuery = getSession().createSQLQuery(strReturnedQuery.toString()).
                    addScalar("invoiceListId", Hibernate.LONG).
                    addScalar("docNo", Hibernate.STRING).
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("volumeNo", Hibernate.LONG).
                    addScalar("fromInvoice", Hibernate.LONG).
                    addScalar("toInvoice", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(InvoiceDailyRptV2.class));

            listReturnedParameter.add(shopId);
            listReturnedParameter.add(Constant.INV_RPT_ACTION_TYPE_RETURNED);
            listReturnedParameter.add(Constant.STATUS_ACTIVE);
            listReturnedParameter.add(fromDate);
            listReturnedParameter.add(toDate);

            for (int i = 0; i < listReturnedParameter.size(); i++) {
                returnedQuery.setParameter(i, listReturnedParameter.get(i));
            }
            List<InvoiceDailyRptV2> listReturned = returnedQuery.list();

            if (listReturned != null && listReturned.size() > 0) {
                for (int i = 0; i < listReturned.size(); i++) {
                    InvoiceDailyRptV2 tmpReturned = listReturned.get(i);
                    for (int j = 0; j < listInCyle.size(); j++) {
                        InvoiceDailyRptV2Bean tmpInCycle = listInCyle.get(j);
//                        if (tmpReturned.getInvoiceListId().equals(tmpInCycle.getInvoiceListId()) &&
//                                tmpReturned.getDocNo().equals(tmpInCycle.getDocNo()) &&
//                                tmpReturned.getSerialNo().equals(tmpInCycle.getSerialNo()) &&
//                                tmpReturned.getVolumeNo().equals(tmpInCycle.getVolumeNo())) {
                        if (tmpReturned.getDocNo().equals(tmpInCycle.getDocNo()) && tmpReturned.getSerialNo().equals(tmpInCycle.getSerialNo()) && tmpReturned.getVolumeNo().equals(tmpInCycle.getVolumeNo()) && (tmpReturned.getToInvoice().equals(tmpInCycle.getReceivedToInvoice()) || tmpReturned.getToInvoice().equals(tmpInCycle.getRemainedBeginCycleToInvoice()))) {

                            //gop phan du lieu hoa don tra lai
                            Long tmpReturnedQuantity = 0L;
                            if (tmpInCycle.getReturnedInvoice() == null || tmpInCycle.getReturnedInvoice().equals("")) {
                                tmpInCycle.setReturnedInvoice(tmpReturned.getFromInvoice() + "->" + tmpReturned.getToInvoice());
                                tmpReturnedQuantity = tmpReturned.getToInvoice() - tmpReturned.getFromInvoice() + 1;
                                tmpInCycle.setReturnedQuantity(tmpReturnedQuantity);
                            } else {
                                tmpInCycle.setReturnedInvoice(tmpInCycle.getReturnedInvoice() + ", " + tmpReturned.getFromInvoice() + " -> " + tmpReturned.getToInvoice());
                                tmpReturnedQuantity = tmpReturned.getToInvoice() - tmpReturned.getFromInvoice() + 1;
                                tmpInCycle.setReturnedQuantity(tmpInCycle.getReturnedQuantity() + tmpReturnedQuantity);
                            }

                            //tru phan hoa don ton cuoi ky
                            if (tmpInCycle.getRemainedEndCycleToInvoice().equals(tmpReturned.getToInvoice())) {
                                tmpInCycle.setRemainedEndCycleFromInvoice(0L);
                                tmpInCycle.setRemainedEndCycleToInvoice(0L);
                                tmpInCycle.setRemainedEndCycleQuantity(0L);
                            } else {
                                tmpInCycle.setRemainedEndCycleToInvoice(tmpReturned.getFromInvoice() - 1);
                                tmpInCycle.setRemainedEndCycleQuantity(tmpInCycle.getRemainedEndCycleQuantity() - tmpReturnedQuantity);
                            }

                            break;
                        }
                    }
                }
            }

        }

        return listInCyle;

    }

    /**
     *
     * author tamdt1
     * date: 14/03/2010
     * lay du lieu bao cao tinh hinh su dung hoa don (dau ky)
     *
     */
    private List<InvoiceDailyRptV2Bean> getListInvoiceBeginCycle() throws Exception {

        Long shopId = this.reportInvoiceForm.getShopId();
        Long staffId = this.reportInvoiceForm.getStaffId();
        String strFromDate = this.reportInvoiceForm.getFromDate();
        String strToDate = this.reportInvoiceForm.getToDate();

        Date fromDate = DateTimeUtils.convertStringToDate(strFromDate);

        //
        List<InvoiceDailyRptV2Bean> listInCycle = new ArrayList<InvoiceDailyRptV2Bean>();


        if (staffId != null && staffId.compareTo(0L) > 0) {
//            //truong hop thong ke hoa don theo staff
//            //lay du lieu ton dau ky
//            List listBeginCycleParameter = new ArrayList();
//            StringBuffer strBeginCycleQuery = new StringBuffer(
//                    "SELECT new com.viettel.im.database.BO.InvoiceDailyRpt(a.docNo, a.serialNo, a.volumeNo, " +
//                    "(SUM(a.createdInvoiceQuantity) + SUM(a.assignedInvoiceQuantity)) " +
//                    "- (SUM(a.destroyedInvoiceQuantity) + SUM(a.deletedInvoiceQuantity) + SUM(a.retrievedInvoiceQuantity))) " +
//                    "FROM InvoiceDailyRpt a " +
//                    "WHERE a.staffId = ? AND a.createdDate < ? " +
//                    "GROUP BY a.docNo, a.serialNo, a.volumeNo " +
//                    "ORDER BY a.docNo, a.serialNo, a.volumeNo  ");
//            listBeginCycleParameter.add(staffId);
//            listBeginCycleParameter.add(fromDate);
//            Query beginCycleQuery = getSession().createQuery(strBeginCycleQuery.toString());
//            for (int i = 0; i < listBeginCycleParameter.size(); i++) {
//                beginCycleQuery.setParameter(i, listBeginCycleParameter.get(i));
//            }
//            listBeginCyle = beginCycleQuery.list();
//
//            //lay du lieu trong ky
//            List listInCycleParameter = new ArrayList();
//            StringBuffer strInCycleQuery = new StringBuffer(
//                    "SELECT new com.viettel.im.database.BO.InvoiceDailyRpt( " +
//                    "a.docNo, a.serialNo, a.volumeNo " +
//                    "SUM(a.usedInvoiceQuantity), " +
//                    "SUM(a.createdInvoiceQuantity), " +
//                    "SUM(a.assignedInvoiceQuantity), " +
//                    "SUM(a.destroyedInvoiceQuantity), " +
//                    "SUM(a.deletedInvoiceQuantity), " +
//                    "SUM(a.retrievedInvoiceQuantity)) " +
//                    "FROM InvoiceDailyRpt a " +
//                    "WHERE a.staffId = ? AND a.createdDate >= ? AND a.createdDate < ? " +
//                    "GROUP BY a.docNo, a.serialNo, a.volumeNo " +
//                    "ORDER BY a.docNo, a.serialNo, a.volumeNo ");
//            listInCycleParameter.add(staffId);
//            listInCycleParameter.add(fromDate);
//            listInCycleParameter.add(toDate);
//            Query inCycleQuery = getSession().createQuery(strInCycleQuery.toString());
//            for (int i = 0; i < listInCycleParameter.size(); i++) {
//                inCycleQuery.setParameter(i, listInCycleParameter.get(i));
//            }
//            listInCyle = inCycleQuery.list();
        } else {
            //lay du lieu trong ky, du lieu trong ky bao gom cac phan:
            //  1> so luong hoa don tao moi, duoc gan
            //  2> so luong hoa don su dung
            //  3> so luong hoa don huy
            //  4> so luong hoa don mat
            //  5> so luong hoa don tra lai

            //1> voi cac dang hoa don tao moi, duoc gan -> chi lay cac ban ghi ap dung voi shop hien tai
            List listReceivedParameter = new ArrayList();
            StringBuffer strReceivedQuery = new StringBuffer(
                    "SELECT new com.viettel.im.client.bean.InvoiceDailyRptV2Bean( " + "       a.shopId, a.staffId, " + "       a.invoiceListId, a.docNo, a.serialNo, a.volumeNo, " + //                    "       '0', '0', '0', " + //so luong hoa don ton dau ky
                    "       a.fromInvoice, a.toInvoice, (a.toInvoice - a.fromInvoice + 1), " + //so luong hoa don nhan trong ky
                    "       a.fromInvoice, a.toInvoice, (a.toInvoice - a.fromInvoice + 1)) " + //so luong hoa don ton cuoi thang
                    "FROM InvoiceDailyRptV2 a " + "WHERE  1 = 1" + "       AND a.shopId = ? " + "       AND a.staffId is null " + "       AND a.actionType = ? " + "       AND a.status = ? " + "       AND a.invoiceDate < ? " + "ORDER BY a.docNo, a.serialNo, a.volumeNo, a.fromInvoice ");
            listReceivedParameter.add(shopId);
            listReceivedParameter.add(Constant.INV_RPT_ACTION_TYPE_RECEIVED);
            listReceivedParameter.add(Constant.STATUS_ACTIVE);
            listReceivedParameter.add(fromDate);
            Query receivedQuery = getSession().createQuery(strReceivedQuery.toString());
            for (int i = 0; i < listReceivedParameter.size(); i++) {
                receivedQuery.setParameter(i, listReceivedParameter.get(i));
            }
            List<InvoiceDailyRptV2Bean> listReceived = receivedQuery.list();

            //gop vao du lieu trong ky
            if (listReceived != null && listReceived.size() > 0) {
                for (int i = 0; i < listReceived.size(); i++) {
                    listInCycle.add(listReceived.get(i));
                }
            }

            //2> doi voi cac hoa don su dung -> tong hop tu cap duoi len
            List listUsedParameter = new ArrayList();
            StringBuffer strUsedQuery = new StringBuffer(
                    "SELECT shop_id shopId, staff_id staffId, " 
                    + "       invoice_list_id invoiceListId, doc_no docNo, serial_no serialNo, volume_no volumeNo, " 
                    + "       MIN (from_invoice) fromInvoice, MAX (from_invoice) toInvoice " 
                    + "FROM ( SELECT  shop_id, staff_id, " 
                    + "               invoice_list_id, doc_no, serial_no, volume_no, " 
                    + "               from_invoice, " 
                    + "               from_invoice - ROW_NUMBER () OVER (ORDER BY from_invoice) rn " 
                    + "       FROM invoice_daily_rpt_v2 " + "       WHERE   1 = 1 " 
                    + "               AND shop_id in (select shop_id from v_shop_tree where root_id = ?) " 
                    + "               AND action_type = ? " + "               AND status = ? " 
                    + "               AND invoice_date < ?) " + "GROUP BY rn, shop_id, staff_id, invoice_list_id, doc_no, serial_no, volume_no " 
                    + "ORDER BY fromInvoice ");

            Query usedQuery = getSession().createSQLQuery(strUsedQuery.toString()).
                    addScalar("invoiceListId", Hibernate.LONG).
                    addScalar("docNo", Hibernate.STRING).
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("volumeNo", Hibernate.LONG).
                    addScalar("fromInvoice", Hibernate.LONG).
                    addScalar("toInvoice", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(InvoiceDailyRptV2.class));

            listUsedParameter.add(shopId);
            listUsedParameter.add(Constant.INV_RPT_ACTION_TYPE_USED);
            listUsedParameter.add(Constant.STATUS_ACTIVE);
            listUsedParameter.add(fromDate);

            for (int i = 0; i < listUsedParameter.size(); i++) {
                usedQuery.setParameter(i, listUsedParameter.get(i));
            }
            List<InvoiceDailyRptV2> listUsed = usedQuery.list();

            //
            if (listUsed != null && listUsed.size() > 0) {
                for (int i = 0; i < listUsed.size(); i++) {
                    InvoiceDailyRptV2 tmpUsed = listUsed.get(i);
                    for (int j = 0; j < listInCycle.size(); j++) {
                        InvoiceDailyRptV2Bean tmpInCycle = listInCycle.get(j);
                        if (tmpUsed.getInvoiceListId().equals(tmpInCycle.getInvoiceListId()) && tmpUsed.getDocNo().equals(tmpInCycle.getDocNo()) && tmpUsed.getSerialNo().equals(tmpInCycle.getSerialNo()) && tmpUsed.getVolumeNo().equals(tmpInCycle.getVolumeNo())) {

                            //tru phan hoa don ton cuoi ky
                            Long tmpUsedQuantity = tmpUsed.getToInvoice() - tmpUsed.getFromInvoice() + 1;
                            if (tmpInCycle.getRemainedEndCycleToInvoice().equals(tmpUsed.getToInvoice())) {
                                tmpInCycle.setRemainedEndCycleFromInvoice(0L);
                                tmpInCycle.setRemainedEndCycleToInvoice(0L);
                                tmpInCycle.setRemainedEndCycleQuantity(0L);
                            } else {
                                tmpInCycle.setRemainedEndCycleFromInvoice(tmpUsed.getToInvoice() + 1);
                                tmpInCycle.setRemainedEndCycleQuantity(tmpInCycle.getRemainedEndCycleQuantity() - tmpUsedQuantity);
                            }

                            break;
                        }
                    }
                }
            }

            //3> doi voi cac danh hoa don so luong hoa don huy -> tong hop tu cap duoi len
            List listDestroyedParameter = new ArrayList();
            StringBuffer strDestroyedQuery = new StringBuffer(
                    "SELECT shop_id shopId, staff_id staffId, " + "       invoice_list_id invoiceListId, doc_no docNo, serial_no serialNo, volume_no volumeNo, " + "       MIN (from_invoice) fromInvoice, MAX (from_invoice) toInvoice " + "FROM ( SELECT  shop_id, staff_id, " + "               invoice_list_id, doc_no, serial_no, volume_no, " + "               from_invoice, " + "               from_invoice - ROW_NUMBER () OVER (ORDER BY from_invoice) rn " + "       FROM invoice_daily_rpt_v2 " + "       WHERE   1 = 1 " + "               AND shop_id in (select shop_id from v_shop_tree where root_id = ?) " + "               AND action_type = ? " + "               AND status = ? " + "               AND invoice_date < ?) " + "GROUP BY rn, shop_id, staff_id, invoice_list_id, doc_no, serial_no, volume_no " + "ORDER BY fromInvoice ");

            Query destroyedQuery = getSession().createSQLQuery(strDestroyedQuery.toString()).
                    addScalar("invoiceListId", Hibernate.LONG).
                    addScalar("docNo", Hibernate.STRING).
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("volumeNo", Hibernate.LONG).
                    addScalar("fromInvoice", Hibernate.LONG).
                    addScalar("toInvoice", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(InvoiceDailyRptV2.class));

            listDestroyedParameter.add(shopId);
            listDestroyedParameter.add(Constant.INV_RPT_ACTION_TYPE_DESTROYED);
            listDestroyedParameter.add(Constant.STATUS_ACTIVE);
            listDestroyedParameter.add(fromDate);

            for (int i = 0; i < listDestroyedParameter.size(); i++) {
                destroyedQuery.setParameter(i, listDestroyedParameter.get(i));
            }
            List<InvoiceDailyRptV2> listDestroyed = destroyedQuery.list();

            if (listDestroyed != null && listDestroyed.size() > 0) {
                for (int i = 0; i < listDestroyed.size(); i++) {
                    InvoiceDailyRptV2 tmpDestroyed = listDestroyed.get(i);
                    for (int j = 0; j < listInCycle.size(); j++) {
                        InvoiceDailyRptV2Bean tmpInCycle = listInCycle.get(j);
                        if (tmpDestroyed.getInvoiceListId().equals(tmpInCycle.getInvoiceListId()) && tmpDestroyed.getDocNo().equals(tmpInCycle.getDocNo()) && tmpDestroyed.getSerialNo().equals(tmpInCycle.getSerialNo()) && tmpDestroyed.getVolumeNo().equals(tmpInCycle.getVolumeNo())) {

                            //tru phan hoa don ton cuoi ky
                            Long tmpDestroyedQuantity = tmpDestroyed.getToInvoice() - tmpDestroyed.getFromInvoice() + 1;
                            if (tmpInCycle.getRemainedEndCycleToInvoice().equals(tmpDestroyed.getToInvoice())) {
                                tmpInCycle.setRemainedEndCycleFromInvoice(0L);
                                tmpInCycle.setRemainedEndCycleToInvoice(0L);
                                tmpInCycle.setRemainedEndCycleQuantity(0L);
                            } else if (tmpInCycle.getRemainedEndCycleFromInvoice().compareTo(tmpDestroyed.getToInvoice()) < 0) {
                                //co dieu kien nay, vi co the xay ra truong hop, hoa don bi huy co so thu tu < so thu tu cua hoa don su dung da thiet lap o buoc 2
                                tmpInCycle.setRemainedEndCycleFromInvoice(tmpDestroyed.getToInvoice() + 1);
                                tmpInCycle.setRemainedEndCycleQuantity(tmpInCycle.getRemainedEndCycleQuantity() - tmpDestroyedQuantity);
                            } else {
                                tmpInCycle.setRemainedEndCycleQuantity(tmpInCycle.getRemainedEndCycleQuantity() - tmpDestroyedQuantity);
                            }

                            break;
                        }
                    }
                }
            }

            //4> doi voi so luong hoa don mat -> tong hop tu cap duoi len
            List listLosedParameter = new ArrayList();
            StringBuffer strLosedQuery = new StringBuffer(
                    "SELECT shop_id shopId, staff_id staffId, " + "       invoice_list_id invoiceListId, doc_no docNo, serial_no serialNo, volume_no volumeNo, " + "       from_invoice fromInvoice, to_invoice toInvoice " + "FROM   invoice_daily_rpt_v2 " + "WHERE  1 = 1 " + "       AND shop_id in (select shop_id from v_shop_tree where root_id = ?) " + "       AND action_type = ? " + "       AND status = ? " + "       AND invoice_date < ? " + "ORDER BY fromInvoice ");

            Query losedQuery = getSession().createSQLQuery(strLosedQuery.toString()).
                    addScalar("invoiceListId", Hibernate.LONG).
                    addScalar("docNo", Hibernate.STRING).
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("volumeNo", Hibernate.LONG).
                    addScalar("fromInvoice", Hibernate.LONG).
                    addScalar("toInvoice", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(InvoiceDailyRptV2.class));

            listLosedParameter.add(shopId);
            listLosedParameter.add(Constant.INV_RPT_ACTION_TYPE_LOSED);
            listLosedParameter.add(Constant.STATUS_ACTIVE);
            listLosedParameter.add(fromDate);

            for (int i = 0; i < listLosedParameter.size(); i++) {
                losedQuery.setParameter(i, listLosedParameter.get(i));
            }
            List<InvoiceDailyRptV2> listLosed = losedQuery.list();

            if (listLosed != null && listLosed.size() > 0) {
                for (int i = 0; i < listLosed.size(); i++) {
                    InvoiceDailyRptV2 tmpLosed = listLosed.get(i);
                    for (int j = 0; j < listInCycle.size(); j++) {
                        InvoiceDailyRptV2Bean tmpInCycle = listInCycle.get(j);
                        if (tmpLosed.getInvoiceListId().equals(tmpInCycle.getInvoiceListId()) && tmpLosed.getDocNo().equals(tmpInCycle.getDocNo()) && tmpLosed.getSerialNo().equals(tmpInCycle.getSerialNo()) && tmpLosed.getVolumeNo().equals(tmpInCycle.getVolumeNo())) {

                            //tru phan hoa don ton cuoi ky
                            Long tmpLosedQuantity = tmpLosed.getToInvoice() - tmpLosed.getFromInvoice() + 1;
                            if (tmpInCycle.getRemainedEndCycleToInvoice().equals(tmpLosed.getToInvoice())) {
                                tmpInCycle.setRemainedEndCycleFromInvoice(0L);
                                tmpInCycle.setRemainedEndCycleToInvoice(0L);
                                tmpInCycle.setRemainedEndCycleQuantity(0L);
                            } else if (tmpInCycle.getRemainedEndCycleFromInvoice().compareTo(tmpLosed.getToInvoice()) < 0) {
                                tmpInCycle.setRemainedEndCycleFromInvoice(tmpLosed.getToInvoice() + 1);
                                tmpInCycle.setRemainedEndCycleQuantity(tmpInCycle.getRemainedEndCycleQuantity() - tmpLosedQuantity);
                            } else {
                                tmpInCycle.setRemainedEndCycleQuantity(tmpInCycle.getRemainedEndCycleQuantity() - tmpLosedQuantity);
                            }

                            break;
                        }
                    }
                }
            }

            //5> doi voi so luong hoa tra lai -> chi lay shop hien tai
            List listReturnedParameter = new ArrayList();
            StringBuffer strReturnedQuery = new StringBuffer(
                    "SELECT shop_id shopId, staff_id staffId, " + "       invoice_list_id invoiceListId, doc_no docNo, serial_no serialNo, volume_no volumeNo, " + "       from_invoice fromInvoice, to_invoice toInvoice " + "FROM   invoice_daily_rpt_v2 " + "WHERE  1 = 1 " + "       AND shop_id = ? " + "       AND staff_id is null " + "       AND action_type = ? " + "       AND status = ? " + "       AND invoice_date < ? " + "ORDER BY fromInvoice ");

            Query returnedQuery = getSession().createSQLQuery(strReturnedQuery.toString()).
                    addScalar("invoiceListId", Hibernate.LONG).
                    addScalar("docNo", Hibernate.STRING).
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("volumeNo", Hibernate.LONG).
                    addScalar("fromInvoice", Hibernate.LONG).
                    addScalar("toInvoice", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(InvoiceDailyRptV2.class));

            listReturnedParameter.add(shopId);
            listReturnedParameter.add(Constant.INV_RPT_ACTION_TYPE_RETURNED);
            listReturnedParameter.add(Constant.STATUS_ACTIVE);
            listReturnedParameter.add(fromDate);

            for (int i = 0; i < listReturnedParameter.size(); i++) {
                returnedQuery.setParameter(i, listReturnedParameter.get(i));
            }
            List<InvoiceDailyRptV2> listReturned = returnedQuery.list();

            if (listReturned != null && listReturned.size() > 0) {
                for (int i = 0; i < listReturned.size(); i++) {
                    InvoiceDailyRptV2 tmpReturned = listReturned.get(i);
                    for (int j = 0; j < listInCycle.size(); j++) {
                        InvoiceDailyRptV2Bean tmpInCycle = listInCycle.get(j);
//                        if (tmpReturned.getInvoiceListId().equals(tmpInCycle.getInvoiceListId()) &&
//                                tmpReturned.getDocNo().equals(tmpInCycle.getDocNo()) &&
//                                tmpReturned.getSerialNo().equals(tmpInCycle.getSerialNo()) &&
//                                tmpReturned.getVolumeNo().equals(tmpInCycle.getVolumeNo())) {
                        if (tmpReturned.getDocNo().equals(tmpInCycle.getDocNo()) && tmpReturned.getSerialNo().equals(tmpInCycle.getSerialNo()) && tmpReturned.getVolumeNo().equals(tmpInCycle.getVolumeNo()) && (tmpReturned.getToInvoice().equals(tmpInCycle.getReceivedToInvoice()) || tmpReturned.getToInvoice().equals(tmpInCycle.getRemainedBeginCycleToInvoice()))) {


                            //tru phan hoa don ton cuoi ky
                            Long tmpReturnedQuantity = tmpReturned.getToInvoice() - tmpReturned.getFromInvoice() + 1;
                            if (tmpInCycle.getRemainedEndCycleToInvoice().equals(tmpReturned.getToInvoice())) {
                                tmpInCycle.setRemainedEndCycleFromInvoice(0L);
                                tmpInCycle.setRemainedEndCycleToInvoice(0L);
                                tmpInCycle.setRemainedEndCycleQuantity(0L);
                            } else {
                                tmpInCycle.setRemainedEndCycleToInvoice(tmpReturned.getFromInvoice() - 1);
                                tmpInCycle.setRemainedEndCycleQuantity(tmpInCycle.getRemainedEndCycleQuantity() - tmpReturnedQuantity);
                            }

                            break;
                        }
                    }
                }
            }

        }

        //buoc lam min, bo qua cac dai hoa don da het ton dau ky, chuyen du lieu vao phan ton dau ky
        List<InvoiceDailyRptV2Bean> result = new ArrayList<InvoiceDailyRptV2Bean>();
        if (listInCycle != null && listInCycle.size() > 0) {
            for (int i = 0; i < listInCycle.size(); i++) {
                InvoiceDailyRptV2Bean tmpInvoiceDailyRptV2Bean = listInCycle.get(i);
                if (tmpInvoiceDailyRptV2Bean.getRemainedEndCycleQuantity() != null && tmpInvoiceDailyRptV2Bean.getRemainedEndCycleQuantity().compareTo(0L) > 0) {
                    tmpInvoiceDailyRptV2Bean.setRemainedBeginCycleFromInvoice(tmpInvoiceDailyRptV2Bean.getRemainedEndCycleFromInvoice());
                    tmpInvoiceDailyRptV2Bean.setRemainedBeginCycleToInvoice(tmpInvoiceDailyRptV2Bean.getRemainedEndCycleToInvoice());
                    tmpInvoiceDailyRptV2Bean.setRemainedBeginCycleQuantity(tmpInvoiceDailyRptV2Bean.getRemainedEndCycleQuantity());
                    tmpInvoiceDailyRptV2Bean.setReceivedFromInvoice(0L);
                    tmpInvoiceDailyRptV2Bean.setReceivedToInvoice(0L);
                    tmpInvoiceDailyRptV2Bean.setReceivedQuantity(0L);
                    result.add(tmpInvoiceDailyRptV2Bean);
                }
            }
        }

        return result;

    }

    /**
     *
     * author tamdt1
     * date: 09/07/2009
     * lay danh sach cac dai hoa don, phan biet nhau bang serial (khog phan biet bang block)
     *
     */
    private List<InvoiceDailyRpt> getListGeneralGroupBySerial() throws Exception {

        Long shopId = this.reportInvoiceForm.getShopId();
        Long staffId = this.reportInvoiceForm.getStaffId();
        String strFromDate = this.reportInvoiceForm.getFromDate();
        String strToDate = this.reportInvoiceForm.getToDate();

        Date fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        Date toDate = DateTimeUtils.convertStringToDate(strToDate);

        //
        List<InvoiceDailyRpt> listBeginCyle = new ArrayList<InvoiceDailyRpt>();
        List<InvoiceDailyRpt> listInCyle = new ArrayList<InvoiceDailyRpt>();


        if (staffId != null && staffId.compareTo(0L) > 0) {
            //truong hop thong ke hoa don theo nhan vien
            //lay du lieu ton dau ky
            List listBeginCycleParameter = new ArrayList();
            StringBuffer strBeginCycleQuery = new StringBuffer(
                    "SELECT new com.viettel.im.database.BO.InvoiceDailyRpt(a.serial, " + "(SUM(a.createdInvoiceQuantity) + SUM(a.assignedInvoiceQuantity)) " + "- (SUM(a.destroyedInvoiceQuantity) + SUM(a.deletedInvoiceQuantity) + SUM(a.retrievedInvoiceQuantity))) " + "FROM InvoiceDailyRpt a " + "WHERE a.id.ownerId = ? AND a.id.ownerType = ? AND a.createdDate < ? " + "GROUP BY a.serial ");
            listBeginCycleParameter.add(staffId);
            listBeginCycleParameter.add(Constant.OWNER_TYPE_STAFF);
            listBeginCycleParameter.add(fromDate);
            Query beginCycleQuery = getSession().createQuery(strBeginCycleQuery.toString());
            for (int i = 0; i < listBeginCycleParameter.size(); i++) {
                beginCycleQuery.setParameter(i, listBeginCycleParameter.get(i));
            }
            listBeginCyle = beginCycleQuery.list();

            //lay du lieu trong ky
            List listInCycleParameter = new ArrayList();
            StringBuffer strInCycleQuery = new StringBuffer(
                    "SELECT new com.viettel.im.database.BO.InvoiceDailyRpt( " + "a.serial, " + "SUM(a.usedInvoiceQuantity), " + "SUM(a.createdInvoiceQuantity), " + "SUM(a.assignedInvoiceQuantity), " + "SUM(a.destroyedInvoiceQuantity), " + "SUM(a.deletedInvoiceQuantity), " + "SUM(a.retrievedInvoiceQuantity)) " + "FROM InvoiceDailyRpt a " + "WHERE a.id.ownerId = ? AND a.id.ownerType = ? AND a.createdDate >= ? AND a.createdDate < ? " + "GROUP BY a.serial ");
            listInCycleParameter.add(staffId);
            listInCycleParameter.add(Constant.OWNER_TYPE_STAFF);
            listInCycleParameter.add(fromDate);
            listInCycleParameter.add(toDate);
            Query inCycleQuery = getSession().createQuery(strInCycleQuery.toString());
            for (int i = 0; i < listInCycleParameter.size(); i++) {
                inCycleQuery.setParameter(i, listInCycleParameter.get(i));
            }
            listInCyle = inCycleQuery.list();
        } else {
            //truong hop tong hop tai muc cua hang -> phai lay chi tiet ca nhan vien thuoc cua hang do

            //lay du lieu ton dau ky
            List listBeginCycleParameter = new ArrayList();
            StringBuffer strBeginCycleQuery = new StringBuffer(
                    "SELECT new com.viettel.im.database.BO.InvoiceDailyRpt(a.serial, " + "(SUM(a.createdInvoiceQuantity) + SUM(a.assignedInvoiceQuantity)) " + "- (SUM(a.destroyedInvoiceQuantity) + SUM(a.deletedInvoiceQuantity) + SUM(a.retrievedInvoiceQuantity))) " + "FROM InvoiceDailyRpt a " + "WHERE ((a.id.ownerId = ? AND a.id.ownerType = ?) OR " + "               (a.id.ownerId IN (SELECT staffId from Staff where shopId = ?) AND a.id.ownerType = ?)) " + "      AND a.createdDate < ? " + "GROUP BY a.serial ");
            listBeginCycleParameter.add(shopId);
            listBeginCycleParameter.add(Constant.OWNER_TYPE_SHOP);
            listBeginCycleParameter.add(shopId);
            listBeginCycleParameter.add(Constant.OWNER_TYPE_STAFF);
            listBeginCycleParameter.add(fromDate);
            Query beginCycleQuery = getSession().createQuery(strBeginCycleQuery.toString());
            for (int i = 0; i < listBeginCycleParameter.size(); i++) {
                beginCycleQuery.setParameter(i, listBeginCycleParameter.get(i));
            }
            listBeginCyle = beginCycleQuery.list();

            //lay du lieu trong ky
            List listInCycleParameter = new ArrayList();
            StringBuffer strInCycleQuery = new StringBuffer(
                    "SELECT new com.viettel.im.database.BO.InvoiceDailyRpt( " + "a.serial, " + "SUM(a.usedInvoiceQuantity), " + "SUM(a.createdInvoiceQuantity), " + "SUM(a.assignedInvoiceQuantity), " + "SUM(a.destroyedInvoiceQuantity), " + "SUM(a.deletedInvoiceQuantity), " + "SUM(a.retrievedInvoiceQuantity)) " + "FROM InvoiceDailyRpt a " + "WHERE ((a.id.ownerId = ? AND a.id.ownerType = ?) OR " + "               (a.id.ownerId IN (SELECT staffId from Staff where shopId = ?) AND a.id.ownerType = ?)) " + "       AND a.createdDate >= ? " + "       AND a.createdDate < ? " + "GROUP BY a.serial ");
            listInCycleParameter.add(shopId);
            listInCycleParameter.add(Constant.OWNER_TYPE_SHOP);
            listInCycleParameter.add(shopId);
            listInCycleParameter.add(Constant.OWNER_TYPE_STAFF);
            listInCycleParameter.add(fromDate);
            listInCycleParameter.add(toDate);
            Query inCycleQuery = getSession().createQuery(strInCycleQuery.toString());
            for (int i = 0; i < listInCycleParameter.size(); i++) {
                inCycleQuery.setParameter(i, listInCycleParameter.get(i));
            }
            listInCyle = inCycleQuery.list();
        }

        List<InvoiceDailyRpt> listInvoiceDailyRpt = new ArrayList<InvoiceDailyRpt>();
        if (listInCyle != null && listInCyle.size() > 0) {
            for (int i = 0; i < listInCyle.size(); i++) {
                listInvoiceDailyRpt.add(listInCyle.get(i));
            }
        }
        if (listBeginCyle != null && listBeginCyle.size() > 0) {
            for (int i = 0; i < listBeginCyle.size(); i++) {
                //
                InvoiceDailyRpt tmpInvoiceDailyRpt = listBeginCyle.get(i);
                boolean flag = false;
                for (int j = 0; j < listInvoiceDailyRpt.size(); j++) {
                    if (tmpInvoiceDailyRpt.getSerialNo().equals(listInvoiceDailyRpt.get(j).getSerialNo())) {
                        //neu trong list da ton tai serial chi gop phan du lieu ton dau ky
                        listInvoiceDailyRpt.get(j).setRemainQuantity(tmpInvoiceDailyRpt.getRemainQuantity());
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    //truong hop chua ton tai trong list -> them moi
                    listInvoiceDailyRpt.add(tmpInvoiceDailyRpt);
                }

            }
        }

        return listInvoiceDailyRpt;

    }

//    /**
//     *
//     * author tamdt1
//     * date: 09/07/2009
//     * lay du lieu bao cao tinh hinh su dung hoa don, dang tong hop
//     *
//     */
//    private List<VInvoiceDetail> getListGeneral() throws Exception {
//
//        String tmpStringQuery = "from InvoiceDailyRpt";
//        Query tmpQuery = getSession().createQuery(tmpStringQuery);
//        List tmpList = tmpQuery.list();
//
//
//
//        List<VInvoiceDetail> listVInvoiceDetail = new ArrayList<VInvoiceDetail>();
//
//        Long shopId = this.reportInvoiceForm.getShopId();
//        Long staffId = this.reportInvoiceForm.getStaffId();
//        String strFromDate = this.reportInvoiceForm.getFromDate();
//        String strToDate = this.reportInvoiceForm.getToDate();
//        Long notIncludeChild = this.reportInvoiceForm.getNotIncludeChild();
//        Long groupType = this.reportInvoiceForm.getGroupType();
//
//        //tao cau lenh chay thu tuc de lay du lieu cua khoang thoi gian hien tai
//        Connection conn = getSession().connection();
//        String query = "begin ? := PK_REPORT.sp_get_invoice_report(?, ?, ?); end;";
//        CallableStatement stmt = conn.prepareCall(query);
//
//        //dang ky tham so dau ra - an Oracle specific type
//        stmt.registerOutParameter(1, OracleTypes.CURSOR);
//
//        //thiet lap cac tham so
//        Date fromDate = DateTimeUtils.convertStringToDate(strFromDate);
//        Date toDate = DateTimeUtils.convertStringToDate(strToDate);
//        stmt.setLong(2, shopId);
//        stmt.setDate(3, new java.sql.Date(fromDate.getTime()));
//        stmt.setDate(4, new java.sql.Date(toDate.getTime()));
//
//        //lay du lieu
//        stmt.execute();
//        ResultSet rs = (ResultSet) stmt.getObject(1);
//
//        //
//        while (rs.next()) {
//            VInvoiceDetail vInvoiceDetail = new VInvoiceDetail();
//            //chu y: neu lay theo index thi cot bat dau co chi so = 1
//            vInvoiceDetail.setSerialNo(rs.getString("SERIAL_NO"));
//            vInvoiceDetail.setBlockNo(rs.getString("BLOCK_NO"));
//            vInvoiceDetail.setUsedInvoiceQuantity(rs.getLong("USED_INVOICE_QUANTITY"));
//            vInvoiceDetail.setCreatedInvoiceQuantity(rs.getLong("CREATED_INVOICE_QUANTITY"));
//            vInvoiceDetail.setAssignedInvoiceQuantity(rs.getLong("ASSIGNED_INVOICE_QUANTITY"));
//            vInvoiceDetail.setDestroyedInvoiceQuantity(rs.getLong("DESTROYED_INVOICE_QUANTITY"));
//            vInvoiceDetail.setDeletedInvoiceQuantity(rs.getLong("DELETED_INVOICE_QUANTITY"));
//            vInvoiceDetail.setRetrievedInvoiceQuantity(rs.getLong("RETRIEVED_INVOICE_QUANTITY"));
//            vInvoiceDetail.setOtherInvoiceQuantity(rs.getLong("OTHER_INVOICE_QUANTITY"));
//
//            listVInvoiceDetail.add(vInvoiceDetail);
//
//        }
//
//        //tao cau lenh de lay du lieu ton dau ky (du lieu tinh tu truoc ngay fromDate 1 ngay)
//        query = "begin ? := PK_REPORT.sp_get_invoice_report(?, ?); end;";
//        stmt = conn.prepareCall(query);
//
//        //dang ky tham so dau ra - an Oracle specific type
//        stmt.registerOutParameter(1, OracleTypes.CURSOR);
//
//        //thiet lap cac tham so
//        stmt.setLong(2, shopId);
//        stmt.setDate(3, new java.sql.Date(fromDate.getTime() - 1*24*60*60*1000)); //lui 1 ngay
//
//        //lay du lieu
//        stmt.execute();
//        rs = (ResultSet) stmt.getObject(1);
//
//        //
//        List<VInvoiceDetail> listConservedInvoice = new ArrayList<VInvoiceDetail>();
//        while (rs.next()) {
//            VInvoiceDetail vInvoiceDetail = new VInvoiceDetail();
//            //chu y: neu lay theo index thi cot bat dau co chi so = 1
//            vInvoiceDetail.setSerialNo(rs.getString("SERIAL_NO"));
//            vInvoiceDetail.setBlockNo(rs.getString("BLOCK_NO"));
//            vInvoiceDetail.setUsedInvoiceQuantity(rs.getLong("USED_INVOICE_QUANTITY"));
//            vInvoiceDetail.setCreatedInvoiceQuantity(rs.getLong("CREATED_INVOICE_QUANTITY"));
//            vInvoiceDetail.setAssignedInvoiceQuantity(rs.getLong("ASSIGNED_INVOICE_QUANTITY"));
//            vInvoiceDetail.setDestroyedInvoiceQuantity(rs.getLong("DESTROYED_INVOICE_QUANTITY"));
//            vInvoiceDetail.setDeletedInvoiceQuantity(rs.getLong("DELETED_INVOICE_QUANTITY"));
//            vInvoiceDetail.setRetrievedInvoiceQuantity(rs.getLong("RETRIEVED_INVOICE_QUANTITY"));
//            vInvoiceDetail.setOtherInvoiceQuantity(rs.getLong("OTHER_INVOICE_QUANTITY"));
//
//            listConservedInvoice.add(vInvoiceDetail);
//
//        }
//
//        //tong hop du lieu ton dau ky
//        for (VInvoiceDetail conservedInvoice : listConservedInvoice) {
//            boolean bHasConservedInvoice = false;
//            for (VInvoiceDetail vInvoiceDetail : listVInvoiceDetail) {
//                //dai hoa don con ton dau ky, trung voi dai hoa don nhan duoc trong thang
//                if(conservedInvoice.getSerialNo() != null &&
//                        conservedInvoice.getBlockNo() != null &&
//                        conservedInvoice.getSerialNo().equals(vInvoiceDetail.getSerialNo()) &&
//                        conservedInvoice.getBlockNo().equals(vInvoiceDetail.getBlockNo())) {
//                    //so hoa don ton dong = (so hoa don tao ra + so hoa don duoc gan)
//                    //                      - (so hoa don su dung + so hoa don bi huy + so hoa don bi xoa + so hoa don bi thu hoi)
//                    Long conservedQuantity = (conservedInvoice.getCreatedInvoiceQuantity() +
//                            conservedInvoice.getAssignedInvoiceQuantity()) - (conservedInvoice.getUsedInvoiceQuantity() +
//                            conservedInvoice.getDestroyedInvoiceQuantity() + conservedInvoice.getDeletedInvoiceQuantity() +
//                            conservedInvoice.getRetrievedInvoiceQuantity());
//                    vInvoiceDetail.setConservedInvoiceQuantity(conservedQuantity);
//
//                    bHasConservedInvoice = true;
//                    break;
//                }
//            }
//
//            if (!bHasConservedInvoice) {
//                //truong hop dai hoa don ton dau ky khong co trong thang hien tai
//                //tao ra doi tuong moi, luu vao list
//                VInvoiceDetail vInvoiceDetail = new VInvoiceDetail();
//                vInvoiceDetail.setSerialNo(conservedInvoice.getSerialNo());
//                vInvoiceDetail.setBlockNo(conservedInvoice.getBlockNo());
//
//                Long usedInvoiceQuantity = getUsedInvoiceQuantity(shopId, staffId,
//                        conservedInvoice.getSerialNo(), conservedInvoice.getBlockNo(),
//                        fromDate, toDate);
//
//                vInvoiceDetail.setUsedInvoiceQuantity(usedInvoiceQuantity);
//
//                listVInvoiceDetail.add(vInvoiceDetail);
//            }
//        }
//
//        return listVInvoiceDetail;
//    }
    /**
     *
     * author tamdt1, 10/07/2009
     * lay so luong hoa don da su dung
     * dau vao:
     *          - shopId, staffId, serialNo, blockNo
     * dau ra:
     *          - so luong hoa don da su dung cua staffId, shopId
     *
     */
    private Long getUsedInvoiceQuantity(Long shopId, Long staffId, String serialNo, String blockNo,
            Date fromDate, Date toDate) {

        Long usedInvoiceQuantity = 0L;
        if (shopId == null || serialNo == null) {
            return usedInvoiceQuantity;
        }

        List listParameter = new ArrayList();
        StringBuffer stringCountUsedInvoiceQuery = new StringBuffer(
                "select count(*) from InvoiceUsed where shopId = ? and serialNo = ? ");
        listParameter.add(shopId);
        listParameter.add(serialNo);

        if (staffId != null) {
            stringCountUsedInvoiceQuery.append(" and staffId = ? ");
            listParameter.add(staffId);
        }

        if (blockNo != null) {
            stringCountUsedInvoiceQuery.append(" and blockNo = ? ");
            listParameter.add(blockNo);
        }

        if (fromDate != null) {
            stringCountUsedInvoiceQuery.append(" and trunc(createDate) >= ? ");
            listParameter.add(fromDate);
        }

        if (toDate != null) {
            stringCountUsedInvoiceQuery.append(" and trunc(createDate) < ? ");
            listParameter.add(toDate);
        }

        Query countUsedInvoiceQuery = getSession().createQuery(stringCountUsedInvoiceQuery.toString());

        for (int i = 0; i < listParameter.size(); i++) {
            countUsedInvoiceQuery.setParameter(i, listParameter.get(i));
        }

        usedInvoiceQuantity = (Long) countUsedInvoiceQuery.list().get(0);

        return usedInvoiceQuantity;
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
//        query.setMaxResults(MAX_RESULT);
//
//        query.setParameter(0, shop.getShopPath());
//        List<VSaleTransDetail> tmpListVSaleTransDetail = query.list();
//        if (tmpListVSaleTransDetail != null && tmpListVSaleTransDetail.size() > 0) {
//            listVSaleTransDetail.addAll(tmpListVSaleTransDetail);
//        }
//
//        //tong hop giao dich cua cac shop con (con cap 1)
//        if (listChildShop != null && listChildShop.size() > 0) {
//            for(Shop childShop : listChildShop){
//                stringQuery = strQuery.toString().replace("_CURRENT_SHOP_NAME_", childShop.getName());
//                query = getSession().createQuery(stringQuery);
//                for (int i = 0; i < listParameter.size(); i++) {
//                    query.setParameter(i, listParameter.get(i));
//                }
//
//                query.setMaxResults(MAX_RESULT);
//
//                query.setParameter(0, childShop.getShopPath() + "%");
//                tmpListVSaleTransDetail = query.list();
//                if(tmpListVSaleTransDetail != null && tmpListVSaleTransDetail.size() > 0) {
//                    listVSaleTransDetail.addAll(tmpListVSaleTransDetail);
//                }
//            }
//
//        }
//
//        return listVSaleTransDetail;
//    }
//
//    /**
//     *
//     * author tamdt1
//     * date: 27/06/2009
//     * lay du lieu bao cao chi tiet cap duoi
//     *
//     */
//    private List<VSaleTransDetail> getListDetailByStaff() throws Exception {
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
//        StringBuffer strQuery = new StringBuffer(
//                "select new VSaleTransDetail(a.staffName, a.shopName, a.saleTransTypeName, a.stockTypeName, a.stockModelCode, a.stockModelName, " +
//                "sum(a.quantity), a.price, sum(a.amount), sum(a.discountAmount), sum(a.vatAmount)) " +
//                "from VSaleTransDetail a where 1 = 1 ");
//
//        List listParameter = new ArrayList();
//
//        strQuery.append("and a.shopId = ? ");
//        listParameter.add(shopId); //du lieu gia (se thiet lap cu the trong vong lap o duoi)
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
//        strQuery.append("group by a.staffName, a.shopName, a.saleTransTypeName, a.stockTypeName, a.stockModelCode, a.stockModelName, a.price ");
//        strQuery.append("order by a.stockModelCode");
//
//        Query query = getSession().createQuery(strQuery.toString());
//        for (int i = 0; i < listParameter.size(); i++) {
//            query.setParameter(i, listParameter.get(i));
//        }
//
//        query.setMaxResults(MAX_RESULT);
//
//        List<VSaleTransDetail> tmpListVSaleTransDetail = query.list();
//        if (tmpListVSaleTransDetail != null && tmpListVSaleTransDetail.size() > 0) {
//            listVSaleTransDetail.addAll(tmpListVSaleTransDetail);
//        }
//
//        return listVSaleTransDetail;
//    }
    /**
     *
     * tamdt1, 09/07/2009
     * kiem tra cac dieu kien hop le truoc khi xuat bao cao
     *
     */
    private boolean checkValidReportInvoice() {
        HttpServletRequest req = getRequest();

        String shopCode = this.reportInvoiceForm.getShopCode();
        String strFromDate = this.reportInvoiceForm.getFromDate();
        String strToDate = this.reportInvoiceForm.getToDate();

        //kiem tra cac truong bat buoc
        if (shopCode == null || shopCode.trim().equals("") || strFromDate == null || strFromDate.trim().equals("") || strToDate == null || strToDate.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGE, "reportInvoice.error.requiredFieldsEmpty");
            return false;
        }


        Date fromDate = new Date();
        Date toDate = new Date();
        try {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
            toDate = DateTimeUtils.convertStringToDate(strToDate);
        } catch (Exception ex) {
            //bao loi
            req.setAttribute(REQUEST_MESSAGE, "reportInvoice.error.invalidDateFormat");
            return false;
        }

        Calendar fromCalendar = Calendar.getInstance();
        Calendar toCalendar = Calendar.getInstance();
        fromCalendar.setTime(fromDate);
        toCalendar.setTime(toDate);
        if (fromCalendar.get(Calendar.MONTH) != toCalendar.get(Calendar.MONTH)) {
            //khong cung thang
            req.setAttribute(REQUEST_MESSAGE, "reportInvoice.error.monthOfFromDateAndToDateDifferent");
            return false;
        }
        if (fromCalendar.compareTo(toCalendar) > 0) {
            //ngay bat dau lon hon ngay ket thuc
            req.setAttribute(REQUEST_MESSAGE, "reportInvoice.error.startDateLargerEndDate");
            return false;
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

    /**
     *
     * author tamdt1
     * date: 21/06/2009
     * lay du lieu cho autocompleter
     *
     */
    public String getShopCode() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String shopCode = req.getParameter("reportInvoiceForm.shopCode");

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
     * author tamdt1
     * date: 21/06/2009
     * lay ten shopCode cap nhat vao textbox
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
     * author tamdt1
     * date: 24/06/2009
     * lay du lieu cho autocompleter
     *
     */
    public String getStaffCode() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String staffCode = req.getParameter("reportInvoiceForm.staffCode");
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
     * author tamdt1
     * date: 24/06/2009
     * lay ten staffCode cap nhat vao textbox
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
}
