package com.viettel.im.database.DAO;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.InvoiceSaleListBean;
import com.viettel.im.client.bean.SaleTransInvoiceBean;
import com.viettel.im.client.form.SaleTransInvoiceForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.DataUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.InvoiceUsed;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.database.BO.SaleTransDetailFull;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.VInvoiceUsedRole;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Session;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import java.io.IOException;

/**
 * End
 */
public class InvoiceManagementDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(InvoiceManagementDAO.class);
    private String LIST_PAY_METHOD = "lstPayMethod";
    private String LIST_REASON_INVOICE = "lstReasonInvoice";
    private String INVOICE_MANAGEMENT = "invoiceManagement"; // => invoiceManagement.page
    private String INVOICE_MANAGEMENT_LIST = "invoiceManagementList"; // => invoiceManagementList.page
    private String SEARCH_SALE_TRANS = "searchSaleTrans"; // => searchSaleTrans.page
    private String INVOICE_USED_DETAIL = "invoiceUsedDetail"; // => createInvoice.page
    private String LIST_INVOICE = "lstInvoice";
    private String LIST_SALE_TRANS = "lstSaleTrans";
    private String LIST_SALE_TRANS_DETAIL = "lstSaleTransDetail";
    private String LIST_REASON_DESTROY = "lstReasonDestroy";
    public static final String SALE_INVOICEUSED_CREATED_STATUS = "1";
    public static final String SALE_INVOICEUSED_DELETED_STATUS = "4";
    public static final String SALE_TRANS_INVOICE_CREATED_STATUS = "3";
    public static final String SALE_TRANS_INVOICE_NOT_CREATED = "2";
    public static final String REASON_TYPE = "REASON_TYPE";
    private String EDIT_INVOICE_NV = "editInvoiceNV";
    private String EDIT_INVOICE_CHT = "editInvoiceCHT";
    private String EDIT_INVOICE_VT = "editInvoiceVT";
    private String CANCEL_INVOICE_NV = "cancelInvoiceNV";
    private String CANCEL_INVOICE_CHT = "cancelInvoiceCHT";
    private String CANCEL_INVOICE_VT = "cancelInvoiceVT";
    private String RECOVER_INVOICE_NV = "recoverInvoiceNV";
    private String RECOVER_INVOICE_CHT = "recoverInvoiceCHT";
    private String RECOVER_INVOICE_VT = "recoverInvoiceVT";
    private String EDIT_INVOICE_CN = "editInvoiceCN";
    private String CANCEL_INVOICE_CN = "cancelInvoiceCN";
    private String RECOVER_INVOICE_CN = "recoverInvoiceCN";
    private SaleTransInvoiceForm form = new SaleTransInvoiceForm();

    public SaleTransInvoiceForm getForm() {
        return form;
    }

    public void setform(SaleTransInvoiceForm form) {
        this.form = form;
    }
    public static final String SALE_TRANS_LIST = "saleTransList";
    public static final String SALE_TRANS_DETAIL_LIST = "saleTransDetailList";

    //Xoa danh sach luu trong session
    private void ClearSession() {
        try {
            getReqSession();

            //Khi bam vao nut tim kiem
            reqSession.setAttribute(LIST_INVOICE, null);
            //Khi bam vao nut xem chi tiet hoa don
            reqSession.setAttribute(LIST_SALE_TRANS, null);
            //Khi bam vao nut xem chi tiet giao dich
            reqSession.setAttribute(LIST_SALE_TRANS_DETAIL, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Khoi tao trang quan ly hoa don ban hang
    public String preparePage() throws Exception {
        getReqSession();
        String pageForward = INVOICE_MANAGEMENT;
        try {
            reqSession.setAttribute(LIST_REASON_DESTROY, null);
            ClearSession();

            UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);

            //Thong tin nguoi tao
            form.setStaffId(userToken.getUserID().toString());

            //Tao hoa don tu ngay - den ngay
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            form.setToDateSearch(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(Constant.DATE_DIS_INVOICE_DAY.toString()));
            form.setFromDateSearch(sdf.format(cal.getTime()));
            form.setInvoiceStatusSearch(Constant.STATUS_USE.toString());

            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(getSession());
            List reasonList = reasonDAO.findByPropertyWithStatus(REASON_TYPE, Constant.REASON_DESTROY_INVOICE_USED, Constant.STATUS_USE.toString());
            reqSession.setAttribute(LIST_REASON_DESTROY, reasonList);

            form.setShopCodeSearch(userToken.getShopCode());
            form.setShopNameSearch(userToken.getShopName());
            form.setStaffCodeSearch(userToken.getLoginName());
            form.setStaffNameSearch(userToken.getStaffName());


            //PayMethod
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(getSession());
            List lstPayMethod = appParamsDAO.findAppParamsList("PAY_METHOD", Constant.STATUS_USE.toString());
            reqSession.setAttribute(LIST_PAY_METHOD, lstPayMethod);

            searchInvoice();
            req.setAttribute(Constant.RETURN_MESSAGE, "");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return pageForward;
    }

    //Tim kiem hoa don ban hang
    public String searchInvoice() {
        String pageForward = INVOICE_MANAGEMENT;
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        try {
            //Xoa het danh sach luu tren session
            ClearSession();

            //Kiem tra tinh hop le thong tin tim kiem
            if (!validateSearchInvoice(form, userToken)) {
                return pageForward;
            }

            //Goi ham tim kiem
            List<InvoiceSaleListBean> lstInvoice = getInvoiceList(form);
            form.setCanSelect("1");
//            session.setAttribute(LIST_INVOICE, lstInvoice);
            req.setAttribute(LIST_INVOICE, lstInvoice);

            //Thong bao ket qua tim kiem
            if (lstInvoice != null && lstInvoice.size() > 0) {
                boolean checkApproveRole = AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("roleApprovePrint"), req);
                for (InvoiceSaleListBean bean : lstInvoice) {
                    if (bean.getPrintType1() == null || bean.getPrintType1() == 0L) {
                        bean.setCheckPrint(1L);
                        bean.setCheckRequestPrint(0L);
                    } else {
                        bean.setCheckPrint(0L);
                        if (bean.getRequestPrint() != null && bean.getRequestPrint() == 1L) {
                            bean.setCheckRequestPrint(2L);
                            if (checkApproveRole) {
                                bean.setCheckApprovePrint(1L);
                            } else {
                                bean.setCheckApprovePrint(0L);
                            }
                        } else {
                            if (bean.getStaffCode() != null && userToken.getLoginName() != null
                                    && bean.getStaffCode().trim().toLowerCase().equalsIgnoreCase(userToken.getLoginName().trim().toLowerCase())) {
                                bean.setCheckRequestPrint(1L);
                            } else {
                                bean.setCheckRequestPrint(0L);
                            }
                            bean.setCheckApprovePrint(0L);
                        }
                    }
                    //tannh20180425 start: chi duoc in 3 lan theo YC TraTV phong TC
                    long checkPrint2 = 0L;
                     if (bean.getPrintType2() == null || bean.getPrintType2() == 0L) {
                         bean.setCheckPrint2(checkPrint2);
                     }else{
                         checkPrint2 = 1L;
                         bean.setCheckPrint2(checkPrint2);
                     }
                      //tannh20180425 end: chi duoc in 3 lan theo YC TraTV phong TC
                }
                req.setAttribute(Constant.RETURN_MESSAGE, "saleInvoice.success.searchSaleTrans");
                List listParamValue = new ArrayList();
                listParamValue.add(lstInvoice.size());
                req.setAttribute("returnMsgValue", listParamValue);
            } else {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleInvoice.ụnsuccess.searchSaleTrans");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getStackTrace());
        }

        return pageForward;
    }

    //Kiem tra tieu chi tim kiem hoa don ban hang
    private boolean validateSearchInvoice(SaleTransInvoiceForm f, UserToken userToken) {
        boolean result = false;
        try {
            f.setShopId(null);
            f.setStaffId(null);

            String shopCode = form.getShopCodeSearch();
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = null;
            String staffCode = form.getStaffCodeSearch();
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = null;

            if (shopCode != null && !"".equals(shopCode.trim())) {
                shop = shopDAO.findShopsAvailableByShopCode(shopCode);
                if (shop == null) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.134");
                    return result;
                }
                f.setShopId(shop.getShopId().toString());
                if (staffCode != null && !"".equals(staffCode.trim())) {
                    staff = staffDAO.findStaffAvailableByStaffCode(staffCode);
                    if (staff == null || staff.getShopId().compareTo(shop.getShopId()) != 0) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.134");
                        return result;
                    }
                    f.setStaffId(staff.getStaffId().toString());
                }
            } else {
                if (form.getInvoiceNoSearch() == null || form.getInvoiceNoSearch().trim().equals("")) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.134");
                    return result;
                }
            }


            String sFromDate = form.getFromDateSearch();
            String sToDate = form.getToDateSearch();
            Date fromDate = null;
            Date toDate = null;
            Date currentDate = getSysdate();
            if (sFromDate != null && !sFromDate.trim().equals("")) {
                try {
                    fromDate = DateTimeUtils.convertStringToDate(sFromDate);
                } catch (Exception ex) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.135");
                    return result;
                }
                if (fromDate.after(currentDate)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.136");
                    return result;
                }
            }
            if (sToDate != null && !sToDate.trim().equals("")) {
                try {
                    toDate = DateTimeUtils.convertStringToDate(sToDate);
                } catch (Exception ex) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.137");
                    return result;
                }
                if (toDate.after(currentDate)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.102");
                    return result;
                }
            }
            if (fromDate != null && toDate != null) {
                if (fromDate.after(toDate)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.138");
                    return result;
                }
            }

            result = !result;
            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.134");
            return result;
        }
    }

    //Ham Tim kiem hoa don ban hang
    private List<InvoiceSaleListBean> getInvoiceList(SaleTransInvoiceForm f) {
        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" invud.INVOICE_USED_ID as invoiceUsedId, ");
        sqlBuffer.append(" invud.amount as amountNotTax, ");
        sqlBuffer.append(" invud.vat as vat, ");
        sqlBuffer.append(" invud.tax as tax, ");
        sqlBuffer.append(" invud.amount_tax as amountTax, ");
        sqlBuffer.append(" invud.discount as discount, ");
        sqlBuffer.append(" invud.promotion as promotion, ");
        sqlBuffer.append(" invud.status as invoiceStatus, ");
        sqlBuffer.append(" invud.SERIAL_NO as serialNo, ");
        sqlBuffer.append(" invud.INVOICE_ID as invoiceId, ");
        sqlBuffer.append(" invud.INVOICE_NO as invoiceNo, ");
        sqlBuffer.append(" invud.original_invoice as originalInvoice,");
        sqlBuffer.append(" invud.CREATE_DATE as createdate, ");
        sqlBuffer.append(" invud.INVOICE_DATE as invoiceDate, ");
        sqlBuffer.append(" invud.CUST_NAME as custName, ");
        sqlBuffer.append(" invud.ADDRESS as address, ");
        sqlBuffer.append(" invud.COMPANY as company, ");
        sqlBuffer.append(" invud.BLOCK_NO as blockNo, ");
        sqlBuffer.append(" invud.PRINT_TYPE_1 as printType1, ");
        sqlBuffer.append(" invud.PRINT_TYPE_2 as printType2, ");
        sqlBuffer.append(" invud.REQUEST_PRINT as requestPrint, ");
        sqlBuffer.append(" stf.STAFF_CODE as staffCode, ");
        //them
        sqlBuffer.append(" sp.shop_code as shopCode ");
        sqlBuffer.append(" ,sp.NAME as shopName ");
        sqlBuffer.append(" ,stf.staff_code as staffCode ");
        sqlBuffer.append(" ,stf.NAME as staffName ");
        sqlBuffer.append(" ,(select name from app_params where type = 'INVOICE_USED_STATUS' and code = invud.status) as invoiceStatusName ");

        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" INVOICE_USED invud ,STAFF stf, SHOP sp ");

        sqlBuffer.append(" WHERE 1=1 and rownum <= 1000 ");
        sqlBuffer.append(" AND invud.STAFF_ID =  stf.STAFF_ID ");
        sqlBuffer.append(" AND invud.shop_id=  sp.shop_id ");

        sqlBuffer.append(" AND invud.INVOICE_LIST_ID in (select INVOICE_LIST_ID from invoice_list where BOOK_TYPE_ID in (select BOOK_TYPE_ID from BOOK_TYPE where INVOICE_TYPE = ? or INVOICE_TYPE = ?) ) ");
        parameterList.add(Constant.INVOICE_TYPE_SALE);
        parameterList.add(Constant.INVOICE_TYPE_CREDIT);

        //invoiceUsedId
        if (null != f.getInvoiceUsedIdSearch() && 0 < f.getInvoiceUsedIdSearch().trim().length()) {
            sqlBuffer.append(" AND invud.INVOICE_USED_ID = ? ");
            parameterList.add(f.getInvoiceUsedIdSearch().trim());
        } else {
            //shopId
            if (null != f.getShopId() && 0 < f.getShopId().trim().length()) {
                sqlBuffer.append(" AND invud.SHOP_ID = ? ");
                parameterList.add(f.getShopId().trim());
            }
            //staffId
            if (null != f.getStaffId() && 0 < f.getStaffId().trim().length()) {
                sqlBuffer.append(" AND invud.STAFF_ID = ? ");
                parameterList.add(f.getStaffId().trim());
            }

            //status
            if (null != f.getInvoiceStatusSearch() && 0 < f.getInvoiceStatusSearch().trim().length()) {
                sqlBuffer.append(" AND invud.STATUS = ? ");
                parameterList.add(f.getInvoiceStatusSearch().trim());
            }

            //custName
            if (null != f.getCustNameSearch() && 0 < f.getCustNameSearch().trim().length()) {
                sqlBuffer.append(" AND lower(invud.CUST_NAME) LIKE ? ");
                parameterList.add("%" + f.getCustNameSearch().trim().toLowerCase() + "%");
            }

            //serialNo
            if (null != f.getSerialNoSearch() && 0 < f.getSerialNoSearch().trim().length()) {
                sqlBuffer.append(" AND invud.SERIAL_NO = ? ");
                parameterList.add(f.getSerialNoSearch().trim());
            }
            //tannh tim kiem tuong doi sua theo phieu yeu cua nang cap hoa don cua tratv  phong tc
            //invoiceNo
            if (null != f.getInvoiceNoSearch() && 0 < f.getInvoiceNoSearch().trim().length()) {
                sqlBuffer.append(" AND invud.INVOICE_NO LIKE ? ");
                parameterList.add("%" + f.getInvoiceNoSearch().trim() + "%");
            }

            //fromDate
            if (null != f.getFromDateSearch() && !"".equals(f.getFromDateSearch().trim())) {
                Date fromDate;
                try {
                    fromDate = DateTimeUtils.convertStringToTime(f.getFromDateSearch().trim().substring(0, 10) + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return null;
                }
                sqlBuffer.append("     AND invud.INVOICE_DATE >= ? ");
                parameterList.add(fromDate);
            }
            //toDate
            if (null != f.getToDateSearch() && !"".equals(f.getToDateSearch().trim())) {
                Date toDate;
                try {
                    toDate = DateTimeUtils.convertStringToTime(f.getToDateSearch().trim().substring(0, 10) + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return null;
                }
                sqlBuffer.append("     AND invud.INVOICE_DATE <= ? ");
                parameterList.add(toDate);
            }

            //staffName
            if (null != f.getInvoiceStaffNameSearch() && 0 < f.getInvoiceStaffNameSearch().trim().length()) {
                sqlBuffer.append(" AND lower(invud.NAME) LIKE ? ");
                parameterList.add("%" + f.getInvoiceStaffNameSearch().trim().toLowerCase() + "%");
            }

            sqlBuffer.append(" ORDER BY invud.INVOICE_DATE DESC,invud.SERIAL_NO desc, invud.INVOICE_ID desc ");
        }

        Query queryObject = getSession().createSQLQuery(sqlBuffer.toString()).
                addScalar("invoiceUsedId", Hibernate.LONG).
                addScalar("amountNotTax", Hibernate.DOUBLE).
                addScalar("vat", Hibernate.DOUBLE).
                addScalar("tax", Hibernate.DOUBLE).
                addScalar("amountTax", Hibernate.DOUBLE).
                addScalar("discount", Hibernate.DOUBLE).
                addScalar("promotion", Hibernate.DOUBLE).
                addScalar("invoiceStatus", Hibernate.LONG).
                addScalar("serialNo", Hibernate.STRING).
                addScalar("invoiceId", Hibernate.LONG).
                addScalar("invoiceNo", Hibernate.STRING).
                addScalar("originalInvoice", Hibernate.STRING).
                addScalar("createdate", Hibernate.DATE).
                addScalar("invoiceDate", Hibernate.DATE).
                addScalar("custName", Hibernate.STRING).
                addScalar("address", Hibernate.STRING).
                addScalar("company", Hibernate.STRING).
                addScalar("blockNo", Hibernate.STRING).
                addScalar("shopCode", Hibernate.STRING).
                addScalar("shopName", Hibernate.STRING).
                addScalar("staffCode", Hibernate.STRING).
                addScalar("staffName", Hibernate.STRING).
                addScalar("invoiceStatusName", Hibernate.STRING).
                addScalar("staffCode", Hibernate.STRING).
                addScalar("printType1", Hibernate.LONG).
                addScalar("printType2", Hibernate.LONG).
                addScalar("requestPrint", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(InvoiceSaleListBean.class));

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();
    }

    //Ham xuat ra excel
    public String expInvoiceToExcel() throws Exception {
        log.debug("# Begin method expInvoiceToExcel");
        HttpServletRequest req = this.getRequest();
        form.setExportUrl("");
        String pageForward = searchInvoice();
        List<InvoiceSaleListBean> lstInvoice = (List<InvoiceSaleListBean>) req.getAttribute(LIST_INVOICE);
        if (lstInvoice != null) {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            SimpleDateFormat hoursFormat = new SimpleDateFormat("yyyyMMddhh24mmss");
            String dateTime = hoursFormat.format(new Date());

            String templatePath = ResourceBundleUtils.getResource("TEMPLATE_PATH") + "list_invoice.xls";
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE") + "list_invoice_" + userToken.getLoginName().toLowerCase() + "_" + dateTime + ".xls";

            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            String realPath = req.getSession().getServletContext().getRealPath(filePath);

            Map beans = new HashMap();
            beans.put("list", lstInvoice);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
            form.setExportUrl(filePath);
        }

        log.debug("# End method expInvoiceToExcel");
        return pageForward;
    }

    //Danh sach giao dich ban hang trong 1 hoa don
    public String searchSaleTransList() {

        String pageForward = SEARCH_SALE_TRANS;
        getReqSession();
        try {
            //Xoa danh sach giao dich & chi tiet giao dich
            reqSession.setAttribute(LIST_SALE_TRANS, null);
            reqSession.setAttribute(LIST_SALE_TRANS_DETAIL, null);

            //Lay so hoa don su dung tu Request
            String invoiceUsedId = req.getParameter("invoiceUsedId");

            //Goi ham tim kiem giao dich trong DAO: SaleTransInvoiceDAO
            //Chi tim kiem theo tieu chi so hoa don su dung: invoiceUsedId
            SaleTransInvoiceDAO saleTransInvoiceDAO = new SaleTransInvoiceDAO();
            saleTransInvoiceDAO.setSession(getSession());
            SaleTransInvoiceForm f = new SaleTransInvoiceForm();
            f.setSaleGroup(null);
            f.setInvoiceUsedIdSearch(invoiceUsedId);
            List<SaleTransInvoiceBean> lstSaleTrans = saleTransInvoiceDAO.getSaleTransList(f);
//            session.setAttribute(LIST_SALE_TRANS, lstSaleTrans);
            req.setAttribute(LIST_SALE_TRANS, lstSaleTrans);

            //Danh sach giao dich trong hoa don
            //Khong cho hien thi cot 'checked' & nut bam 'Lap hoa don'
            form.setCanSelect("0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageForward;
    }

    //Phan trang
    public String pageNavigator() {
        searchInvoice();
        String pageForward = INVOICE_MANAGEMENT_LIST;
        return pageForward;
    }

    //tannh20180406: them in chim trong file pdf
    public void manipulatePdf(String src, String dest, UserToken userToken) throws IOException, DocumentException, Exception {
        src = req.getSession().getServletContext().getRealPath(src);
        dest = req.getSession().getServletContext().getRealPath(dest);
        PdfReader reader = new PdfReader(src);
        int n = reader.getNumberOfPages();
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.setRotateContents(false);
        // text watermark
        Font f = new Font(Font.FontFamily.HELVETICA, 15);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String strDate = dateFormat.format(date);
        String strPrintingBy = userToken.getLoginName() + ", Factura criada a : " + strDate.substring(11, 16) + " " + strDate.substring(0, 10) + " em: " + userToken.getShopName();
        String strPrintingBy1 = "Imprimida por: " + userToken.getLoginName() + ", Factura criada a : " + strDate.substring(11, 16) + " " + strDate.substring(0, 10) + " em: " + userToken.getShopName();
        Phrase p = new Phrase(strPrintingBy, f);
        // image watermark
        // transparency
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.5f);
        // properties
        PdfContentByte over;
        Rectangle pagesize;
        float x, y;
        // loop over every page

        for (int i = 1; i <= n; i++) {
            pagesize = reader.getPageSize(i);
            x = (pagesize.getLeft() + pagesize.getRight()) / 2;
            y = (pagesize.getTop() + pagesize.getBottom()) / 2;
            over = stamper.getOverContent(i);
            over.saveState();
            over.setGState(gs1);
            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 45);
            // lan 2

            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, new Phrase(strPrintingBy1), x + 34, y, 45);

            over.restoreState();
        }
        stamper.close();
        reader.close();
    }

    //In hoa don ban hang
    public String printInvoice() throws Exception {
        String pageForward = "saleManagmentPrintInvoice";
		log.info("-->start printInvoice");
		log.debug("-->start printInvoice");
        req = getRequest();
        try {
//            tannh20180419 :lay thong tin user login.
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            Long invoiceUsedId = Long.parseLong(getRequest().getParameter("invoiceUsedId"));

            Long invoiceType = Long.parseLong(getRequest().getParameter("invoiceType"));
            if (invoiceType == null) {
                invoiceType = 1L;
            }

            Long printType = Long.parseLong(getRequest().getParameter("printType"));
            if (printType == null) {
                printType = 1L;
            }
            if (invoiceUsedId == null) {
                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.noInvoiceId");
                return pageForward;
            }
            InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
            invoiceUsedDAO.setSession(getSession());
            InvoiceUsed invoiceUsed = invoiceUsedDAO.findById(invoiceUsedId);
            InvoicePrinterV2DAO invoicePrinterDAO = new InvoicePrinterV2DAO();
            invoicePrinterDAO.setSession(this.getSession());
            String pathOne = invoicePrinterDAO.printSaleTransInvoice(invoiceUsedId, invoiceType, printType);
			log.debug("-->start printInvoice pathOne:"+pathOne);
            if (pathOne.equals("0")) {
                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.exception");
                return pageForward;
            }
            if (pathOne.equals("2")) {
                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.noConfig");
                return pageForward;
            }
            if (pathOne.equals("3")) {
                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.noPageSize");
                return pageForward;
            }
            // tannh20180423 start:theo yeu cau cua TraTV phong TC chi can in hoa don khong can xuat ra file  
            String pathOneNew = "";
            int lengtDot = pathOne.lastIndexOf(".");
            if (lengtDot != -1) {
                String beforeDot = pathOne.substring(0, lengtDot);
                beforeDot = beforeDot + "new";
                pathOneNew = beforeDot + ".pdf";
            }
            manipulatePdf(pathOne, pathOneNew, userToken);

            String pathTow = invoicePrinterDAO.printSaleTransInvoiceT2(invoiceUsedId, invoiceType, printType);
            String pathOneTow = "";
            int lengtDotTow = pathTow.lastIndexOf(".");
            if (lengtDotTow != -1) {
                String beforeDot = pathTow.substring(0, lengtDotTow);
                beforeDot = beforeDot + "new";
                pathOneTow = beforeDot + ".pdf";
            }
            manipulatePdf(pathTow, pathOneTow, userToken);

            String pathThree = invoicePrinterDAO.printSaleTransInvoiceT3(invoiceUsedId, invoiceType, printType);
            String pathOneThree = "";
            int lengtDotThree = pathThree.lastIndexOf(".");
            if (lengtDotThree != -1) {
                String beforeDot = pathThree.substring(0, lengtDotThree);
                beforeDot = beforeDot + "new";
                pathOneThree = beforeDot + ".pdf";
            }
            manipulatePdf(pathThree, pathOneThree, userToken);

           // tannh20180423 end:theo yeu cau cua TraTV phong TC chi can in hoa don khong can xuat ra file  
            Long num = 0L;
            if (invoiceType == 1L) {
                num = invoiceUsed.getPrintType1();
                num = num == null ? 0L : num;
                num += 1L;
                invoiceUsed.setPrintType1(num);
            } else {
                num = invoiceUsed.getPrintType2();
                num = num == null ? 0L : num;
                num += 1L;
                invoiceUsed.setPrintType2(num);
            }
            getSession().update(invoiceUsed);
            getSession().getTransaction().commit();
            getSession().beginTransaction();

            // tannh20180423 start:theo yeu cau cua TraTV phong TC chi can in hoa don khong can xuat ra file  

            req.setAttribute("invoicePrintPath", pathOneNew);
            req.setAttribute("invoicePrintPath1", pathOneTow);
            req.setAttribute("invoicePrintPath2", pathOneThree);
             // tannh20180423 end:theo yeu cau cua TraTV phong TC chi can in hoa don khong can xuat ra file  
            searchInvoice();

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.undefine");
        }
        return pageForward;
    }

    //Xem chi tiet hoa don ban hang
    public String getInvoiceInfo() throws Exception {
        String pageForward = INVOICE_USED_DETAIL;
        getReqSession();
        try {
            //Xoa danh sach hoa don
            reqSession.setAttribute(LIST_INVOICE, null);

            //Lay invoiceUsedId
            String invoiceUsedId = req.getParameter("invoiceUsedId");
            if (invoiceUsedId == null || invoiceUsedId.trim().equals("")) {
                return pageForward;
            }

            //Thong tin hoa don su dung
            InvoiceSaleListBean beanTemp = getInvoiceUsedDetail(Long.valueOf(invoiceUsedId));
            if (null == beanTemp) {
                return pageForward;
            }
            form = copyInvoiceUsedToForm(beanTemp);

            //
            //Kiem tra phan quyen sua hoa don, huy hoa don
            checkAuthorityInvoiceUsed(Long.valueOf(invoiceUsedId));
            //sonbc2: kiem tra quyen huy hoa don
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (beanTemp.getStaffCode() == null || !beanTemp.getStaffCode().trim().toLowerCase().equalsIgnoreCase(userToken.getLoginName().trim().toLowerCase())
                    || !compareDate(beanTemp.getCreatedate())) {
//                form.setEditInvoice(false);
                form.setCancelInvoice(false);
//                form.setRecoverInvoice(false);
            }
            //add sonbc2: disable 3 nut doi voi hoa don dieu chinh
            if (beanTemp.getIsCreditInv() != null && !beanTemp.getIsCreditInv().trim().equals("")) {
                form.setEditInvoice(false);
                form.setCancelInvoice(false);
                form.setRecoverInvoice(false);
            }


            //
//            if (form.isCancelInvoice()) {
//                boolean result = checkAuthorityInvoiceUsedForAgent(beanTemp.getInvoiceUsedId());
//                if (!result){
//                    form.setCancelInvoice(false);
//                }
//            }

            //Lay danh sach giao dich thuoc hoa don
            searchSaleTransList();

            //Reason
//            List<SaleTransInvoiceBean> lstSaleTrans = (List<SaleTransInvoiceBean>) session.getAttribute(LIST_SALE_TRANS);
            List<SaleTransInvoiceBean> lstSaleTrans = (List<SaleTransInvoiceBean>) req.getAttribute(LIST_SALE_TRANS);
            CommonDAO commonDAO = new CommonDAO();
            Long type = null;
            String typeList = "";
            if ((null != form.getSaleTransTypeSearch()) && (!"".equals(form.getSaleTransTypeSearch().trim()))) {
                type = Long.valueOf(form.getSaleTransTypeSearch().trim());
            }
            if (null == type) {
                for (SaleTransInvoiceBean bean : lstSaleTrans) {
                    if (null == bean.getSaleTransType() || "".equals(bean.getSaleTransType().trim())) {
                        continue;
                    }
                    if (!"".equals(typeList)) {
                        typeList += ",";
                    }
                    typeList += bean.getSaleTransType().trim();
                }
            }
            List lstReason = commonDAO.getReasonInvoiceList(form.getSaleGroup(), type, typeList);
            reqSession.setAttribute(LIST_REASON_INVOICE, lstReason);


        } catch (Exception e) {
            e.printStackTrace();
        }

//        form.setProcessingInvoice(12345L);
        return pageForward;
    }

    public boolean checkAuthorityInvoiceUsedForAgent(Long invoiceUsedId) {
        try {
            //Kiem tra quyen huy hoa don cho dai ly
            String sql = " SELECT * "
                    + "  FROM sale_trans a "
                    + " WHERE 1 = 1 "
                    + "   AND a.invoice_used_id = ? "
                    + "   AND EXISTS ( "
                    + "          SELECT * "
                    + "            FROM stock_trans b"
                    + "           WHERE b.stock_trans_id = a.stock_trans_id "
                    + "             AND b.stock_trans_status = 4) "
                    + " ";
            Query qry = getSession().createSQLQuery(sql);
            qry.setParameter(0, invoiceUsedId);
            List tmpList = qry.list();
            if (tmpList == null || tmpList.size() == 0) {
                return true;//Co quyen huy hoa don
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void checkAuthorityInvoiceUsed(Long invoiceUsedId) {
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        form.setCancelInvoice(false);
        form.setEditInvoice(false);
        form.setRecoverInvoice(false);

        String cancelInvoice = CANCEL_INVOICE_NV;
        String editInvoice = EDIT_INVOICE_NV;
        String recoverInvoice = RECOVER_INVOICE_NV;



        try {
            //Huy hoa don
            if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_INVOICE_VT), req)) {
                cancelInvoice = CANCEL_INVOICE_VT;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_INVOICE_CN), req)) {
                cancelInvoice = CANCEL_INVOICE_CN;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_INVOICE_CHT), req)) {
                cancelInvoice = CANCEL_INVOICE_CHT;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_INVOICE_NV), req)) {
                cancelInvoice = CANCEL_INVOICE_NV;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            //Sua hoa don
            if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(EDIT_INVOICE_VT), req)) {
                editInvoice = EDIT_INVOICE_VT;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(EDIT_INVOICE_CN), req)) {
                editInvoice = EDIT_INVOICE_CN;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(EDIT_INVOICE_CHT), req)) {
                editInvoice = EDIT_INVOICE_CHT;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(EDIT_INVOICE_NV), req)) {
                editInvoice = EDIT_INVOICE_NV;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            //Khoi phuc hoa don
            if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(RECOVER_INVOICE_VT), req)) {
                recoverInvoice = RECOVER_INVOICE_VT;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(RECOVER_INVOICE_CN), req)) {
                recoverInvoice = RECOVER_INVOICE_CN;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(RECOVER_INVOICE_NV), req)) {
                recoverInvoice = RECOVER_INVOICE_NV;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(RECOVER_INVOICE_CHT), req)) {
                recoverInvoice = RECOVER_INVOICE_CHT;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            cancelInvoice = cancelInvoice.trim().toLowerCase();
            editInvoice = editInvoice.trim().toLowerCase();
            recoverInvoice = recoverInvoice.trim().toLowerCase();

            List parameterList = new ArrayList();
            String queryString = " from VInvoiceUsedRole where id.invoiceUsedId = ? and roleValue = ? ";
            parameterList.add(invoiceUsedId);
            parameterList.add("1");
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            List<VInvoiceUsedRole> lstTemp = queryObject.list();
            if (lstTemp == null || lstTemp.size() == 0) {
                return;
            }
            for (int i = 0; i < lstTemp.size(); i++) {
                VInvoiceUsedRole object = lstTemp.get(i);
                //Edit Invoice
                if (object.getId().getRoleName().trim().toLowerCase().equals(editInvoice)) {
                    if (editInvoice.endsWith("NV")) {
                        if (object.getStaffId().compareTo(userToken.getUserID()) != 0) {
                            continue;
                        }
                    } else if (editInvoice.endsWith("CHT")) {
                        if (object.getShopId().compareTo(userToken.getShopId()) != 0) {
                            continue;
                        }
                    }
                    form.setEditInvoice(true);
                }

                //Cancel Invoice
                if (object.getId().getRoleName().trim().toLowerCase().equals(cancelInvoice)) {
                    if (cancelInvoice.endsWith("NV")) {
                        if (object.getStaffId().compareTo(userToken.getUserID()) != 0) {
                            continue;
                        }
                    } else if (cancelInvoice.endsWith("CHT")) {
                        if (object.getShopId().compareTo(userToken.getShopId()) != 0) {
                            continue;
                        }
                    }
                    form.setCancelInvoice(true);
                }
                //Recover Invoice
                if (object.getId().getRoleName().trim().toLowerCase().equals(recoverInvoice)) {
                    if (recoverInvoice.endsWith("NV")) {
                        if (object.getStaffId().compareTo(userToken.getUserID()) != 0) {
                            continue;
                        }
                    } else if (recoverInvoice.endsWith("CHT")) {
                        if (object.getShopId().compareTo(userToken.getShopId()) != 0) {
                            continue;
                        }
                    }
                    form.setRecoverInvoice(true);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Ham lay thong tin chi tiet hoa don ban hang
    private InvoiceSaleListBean getInvoiceUsedDetail(Long invoiceUsedId) {
        log.debug("finding all InvoiceList instances");
        try {
            StringBuffer sqlBuffer = new StringBuffer();
            List parameterList = new ArrayList();

            sqlBuffer.append(" SELECT DISTINCT ");
            sqlBuffer.append(" invud.INVOICE_USED_ID as invoiceUsedId, ");
            sqlBuffer.append(" invud.SERIAL_NO as serialNo, ");
            sqlBuffer.append(" invud.INVOICE_ID as invoiceId, ");
            sqlBuffer.append(" invud.CREATE_DATE as createdate, ");
            sqlBuffer.append(" invud.CUST_NAME as custName, ");
            sqlBuffer.append(" invud.ACCOUNT as account, ");
            sqlBuffer.append(" invud.ADDRESS as address, ");
            sqlBuffer.append(" invud.COMPANY as company, ");
            sqlBuffer.append(" invud.AMOUNT_TAX as amountTax, ");
            sqlBuffer.append(" invud.AMOUNT as amountNotTax, ");
            sqlBuffer.append(" invud.TIN as tin, ");
            sqlBuffer.append(" invud.NOTE as note, ");
            sqlBuffer.append(" invud.DISCOUNT as discount, ");
            sqlBuffer.append(" invud.PROMOTION as promotion, ");
            sqlBuffer.append(" invud.TAX as tax, ");
            sqlBuffer.append(" invud.INVOICE_NO as invoiceNo, ");
            sqlBuffer.append(" invud.IS_CREDIT_INV as isCreditInv, ");
            sqlBuffer.append(" appara.NAME as payMethodName, ");
            sqlBuffer.append(" rea.REASON_NAME as reasonName, ");
            sqlBuffer.append(" stf.STAFF_CODE as staffCode, ");
            sqlBuffer.append(" stf.NAME as staffName ");

            sqlBuffer.append(" ,sp.NAME AS shopName ");
            sqlBuffer.append(" ,il.from_invoice AS fromInvoice ");
            sqlBuffer.append(" ,il.to_invoice AS toInvoice ");
            sqlBuffer.append(" ,il.curr_invoice_no AS currInvoice ");
            sqlBuffer.append(" ,invud.status as invoiceStatus ");

            sqlBuffer.append(" ,invud.pay_Method as payMethodId ");
            sqlBuffer.append(" ,to_char(invud.reason_id) as reasonId ");

            sqlBuffer.append(" FROM ");
            sqlBuffer.append(" APP_PARAMS appara, ");
            sqlBuffer.append(" INVOICE_USED invud ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" STAFF stf ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" invud.STAFF_ID =  stf.STAFF_ID ");
            sqlBuffer.append(" LEFT JOIN ");
            sqlBuffer.append(" REASON rea ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" rea.REASON_ID =  invud.REASON_ID ");


            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" SHOP sp  ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" invud.shop_id =  sp.shop_id ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" invoice_list il ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" invud.invoice_list_id = il.invoice_list_id ");

            sqlBuffer.append(" WHERE ");
            sqlBuffer.append(" 1 = 1 ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" invud.INVOICE_USED_ID = ? ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" appara.TYPE = ? ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" appara.CODE = invud.PAY_METHOD ");

            parameterList.add(invoiceUsedId);
            parameterList.add("PAY_METHOD");

            Query queryObject = getSession().createSQLQuery(sqlBuffer.toString()).
                    addScalar("invoiceUsedId", Hibernate.LONG).
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("invoiceId", Hibernate.LONG).
                    addScalar("createdate", Hibernate.DATE).
                    addScalar("custName", Hibernate.STRING).
                    addScalar("account", Hibernate.STRING).
                    addScalar("address", Hibernate.STRING).
                    addScalar("company", Hibernate.STRING).
                    addScalar("amountTax", Hibernate.DOUBLE).
                    addScalar("amountNotTax", Hibernate.DOUBLE).
                    addScalar("tin", Hibernate.STRING).
                    addScalar("note", Hibernate.STRING).
                    addScalar("discount", Hibernate.DOUBLE).
                    addScalar("promotion", Hibernate.DOUBLE).
                    addScalar("tax", Hibernate.DOUBLE).
                    addScalar("invoiceNo", Hibernate.STRING).
                    addScalar("isCreditInv", Hibernate.STRING).
                    addScalar("payMethodName", Hibernate.STRING).
                    addScalar("reasonName", Hibernate.STRING).
                    addScalar("staffCode", Hibernate.STRING).
                    addScalar("staffName", Hibernate.STRING).
                    addScalar("shopName", Hibernate.STRING).
                    addScalar("fromInvoice", Hibernate.STRING).
                    addScalar("toInvoice", Hibernate.STRING).
                    addScalar("currInvoice", Hibernate.STRING).
                    addScalar("invoiceStatus", Hibernate.LONG).
                    addScalar("payMethodId", Hibernate.STRING).
                    addScalar("reasonId", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(InvoiceSaleListBean.class));

            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            List result = queryObject.list();
            if (result != null && result.size() != 0) {
                return (InvoiceSaleListBean) queryObject.list().get(0);
            }
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            re.printStackTrace();
        }
        return null;
    }

    //Copy thong tin hoa don ban hang tu BEAN -> FORM
    private SaleTransInvoiceForm copyInvoiceUsedToForm(InvoiceSaleListBean invoiceSaleListBean) {
        if (invoiceSaleListBean == null) {
            return null;
        }
        try {
            SaleTransInvoiceForm f = new SaleTransInvoiceForm();

            f.setCanSelect("0");//An nut 'Lap hoa don' & cot 'Checked'
            f.setIsPopup("1"); //An nut 'Quay lai'
            f.setSaleGroup(0L); //Hien thi truong: Ten khach hang (khong phai: Ten dai ly)
            f.setInvoiceUsedId(invoiceSaleListBean.getInvoiceUsedId().toString());

            f.setShopName(invoiceSaleListBean.getShopName());
            f.setFromInvoice(invoiceSaleListBean.getFromInvoice());
            f.setToInvoice(invoiceSaleListBean.getToInvoice());
            f.setCurInvoice(invoiceSaleListBean.getCurrInvoice());

            f.setSerialNo(invoiceSaleListBean.getSerialNo());

            f.setInvoiceNo(invoiceSaleListBean.getInvoiceNo());

            f.setStaffName(invoiceSaleListBean.getStaffName());
            f.setCreateDate(DateTimeUtils.convertDateTimeToString(invoiceSaleListBean.getCreatedate()));
            f.setObjName(invoiceSaleListBean.getCustName());
            f.setObjCompany(invoiceSaleListBean.getCompany());
            f.setObjAddress(invoiceSaleListBean.getAddress());
            f.setObjTin(invoiceSaleListBean.getTin());
            f.setObjAccount(invoiceSaleListBean.getAccount());
            //Thay the bang Name
//            f.setPayMethodId(invoiceSaleListBean.getPayMethodName());
//            f.setReasonId(invoiceSaleListBean.getReasonName());

            f.setPayMethodId(invoiceSaleListBean.getPayMethodId());
            f.setReasonId(invoiceSaleListBean.getReasonId());

            //

            if (invoiceSaleListBean.getAmountNotTax() != null) {
                f.setAmountNotTax(invoiceSaleListBean.getAmountNotTax());

                f.setAmountNotTaxMoney(NumberUtils.formatNumber(invoiceSaleListBean.getAmountNotTax()));
            }
            if (invoiceSaleListBean.getAmountTax() != null) {
                f.setAmountTax(invoiceSaleListBean.getAmountTax());

                f.setAmountTaxMoney(NumberUtils.formatNumber(invoiceSaleListBean.getAmountTax()));
            }
            if (invoiceSaleListBean.getDiscount() != null) {
                f.setDiscount(invoiceSaleListBean.getDiscount());

                f.setDiscountMoney(NumberUtils.formatNumber(invoiceSaleListBean.getDiscount()));
            }
            if (invoiceSaleListBean.getPromotion() != null) {
                f.setPromotion(invoiceSaleListBean.getPromotion());

                f.setPromotionMoney(NumberUtils.formatNumber(invoiceSaleListBean.getPromotion()));
            }
            if (invoiceSaleListBean.getTax() != null) {
                f.setTax(invoiceSaleListBean.getTax());

                f.setTaxMoney(NumberUtils.formatNumber(invoiceSaleListBean.getTax()));
            }
            f.setNote(invoiceSaleListBean.getNote());

            if (invoiceSaleListBean.getInvoiceStatus() != null) {
                f.setInvoiceStatus(String.valueOf(invoiceSaleListBean.getInvoiceStatus()));
            }

            return f;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getInvoiceManagementDetailSaleTransDetail() throws Exception {
        log.info("getInvoiceManagementDetailSaleTransDetail action...");
        log.debug("# Begin method getInvoiceManagementDetailSaleTransDetail");

        /*
         * Action Common Object
         */
        String pageForward = "invoiceManagementDetailSaleTransDetail";
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                //Xoa danh sach
                reqSession.setAttribute(SALE_TRANS_DETAIL_LIST, null);

                //Lay saleTransId
                String saleTransIdTemp = req.getParameter("saleTransId");
                if (saleTransIdTemp == null || saleTransIdTemp.trim().equals("")) {
                    log.debug("Selected invoice used id is missing");
                    return Constant.ERROR_PAGE;
                }
                Long saleTransId = new Long(saleTransIdTemp);

                //Lay danh sach cac mat hang trong giao dich, luu xuong session
                String queryString = " FROM SaleTransDetailFull WHERE saleTransId = ? ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, saleTransId);
                List<SaleTransDetailFull> saleTransDetailFull = queryObject.list();
                reqSession.setAttribute(SALE_TRANS_DETAIL_LIST, saleTransDetailFull);
            } catch (Exception e) {
                log.debug("WEB. " + e.toString());
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.debug("# End getInvoiceManagementDetailSaleTransDetail action");
        log.info("getInvoiceManagementDetailSaleTransDetail action has been done!");
        return pageForward;
    }

    public String destroyInvoice() {
        String pageForward = INVOICE_MANAGEMENT;
        getReqSession();
        Session mySession = this.getSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        try {

            if (!validateDestroyInvoice()) {
                return pageForward;
            }
            String[] selectedInvoiceUsed = form.getInvoiceUsedIdList();

//                if (selectedInvoiceUsed == null || selectedInvoiceUsed.length == 0) {
//                    return pageForward;
//                }
//                if (form.getReasonId() == null || form.getReasonId().equals("")) {
//                    return pageForward;
//                }

            InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
            invoiceUsedDAO.setSession(mySession);

            for (int i = 0; i < selectedInvoiceUsed.length; i++) {
                String tempId = selectedInvoiceUsed[i];
                if (tempId != null && !tempId.equals("")) {
                    Long tempInvoiceUsedId = new Long(tempId);
                    InvoiceUsed invoiceUsed = invoiceUsedDAO.findById(tempInvoiceUsedId);
                    invoiceUsed.setStatus(SALE_INVOICEUSED_DELETED_STATUS);
                    invoiceUsed.setReasonId(new Long(form.getReasonId()));

                    /*
                     * TrongLV Bo sung thong tin ngay huy va nguoi huy
                     */
                    invoiceUsed.setDestroyDate(DateTimeUtils.getSysDate());
                    invoiceUsed.setDestroyer(userToken.getLoginName());
                    //End

                    invoiceUsedDAO.save(invoiceUsed);

                    SaleTransDAO saleTransDAO = new SaleTransDAO();
                    saleTransDAO.setSession(mySession);
                    Long ownerId = null;
                    Long ownerType = null;
                    Double amount = 0.0;

                    List<SaleTrans> saleTransList = saleTransDAO.getSaleTrans(tempInvoiceUsedId, SALE_TRANS_INVOICE_CREATED_STATUS);
                    for (SaleTrans saleTrans : saleTransList) {
                        saleTrans.setStatus(SALE_TRANS_INVOICE_NOT_CREATED);
                        saleTrans.setInvoiceUsedId(null);
                        saleTrans.setInvoiceCreateDate(null);
                        saleTransDAO.save(saleTrans);
                    }
                    System.out.println("-----------------------------------------------------------------------------------------------");
                    System.out.println("-----------------------------------------------------------------------------------------------");
                    System.out.println("-----------------------------------------------------------------------------------------------");
                    System.out.println("-----------------------------------------------------------------------------------------------");
                    System.out.println("-----------------------------------------------------------------------------------------------");
                    System.out.println("invoiceUsed.type|SaleTransType");
                    System.out.println(invoiceUsed.getType());
                    System.out.println(saleTransList.get(0).getSaleTransType());
                    if (saleTransList != null && !saleTransList.isEmpty()) {
                        if (DataUtils.safeEqual(invoiceUsed.getType(), Long.valueOf(Constant.SALE_GROUP_COL_RETAIL))
                                && !DataUtils.safeEqual(saleTransList.get(0).getSaleTransType(), Constant.SALE_TRANS_TYPE_PUNISH)) {
                            if (saleTransList.get(0).getStaffId() == null) {
                                ownerId = saleTransList.get(0).getShopId();
                                ownerType = Constant.OWNER_TYPE_SHOP;
                            } else {
                                ownerId = saleTransList.get(0).getStaffId();
                                ownerType = Constant.OWNER_TYPE_STAFF;
                            }
                            amount = invoiceUsed.getAmountTax();

                            System.out.println("ownerId|ownerType|amount");
                            System.out.println(ownerId);
                            System.out.println(ownerType);
                            System.out.println(amount);
                        }
                    }
                    if (ownerId != null) {
                        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
                        String returnMsg = accountAgentDAO.addBalance(mySession, ownerId, ownerType,
                                Constant.DEPOSIT_TRANS_TYPE_INVOICE,
                                amount,
                                getSysdate(), invoiceUsed.getInvoiceUsedId(), userToken.getLoginName());
                        if (!returnMsg.equals("")) {
                            mySession.clear();
                            mySession.getTransaction().rollback();
                            mySession.beginTransaction();
                            req.setAttribute("returnMsg", returnMsg);
                            log.debug("End method destroyInvoice");
                            return pageForward;
                        }
                    }
                }
            }
            mySession.flush();

        } catch (Exception e) {
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();
            e.printStackTrace();
//                req.setAttribute("returnMsg", "Lỗi huỷ hoá đơn!");
            req.setAttribute("returnMsg", "ERR.SAE.128");
            return pageForward;
        }

        searchInvoice();
//        req.setAttribute("returnMsg", "Huỷ hoá đơn thành công!");
        req.setAttribute("returnMsg", "ERR.SAE.127");

        return pageForward;
    }

    private boolean validateDestroyInvoice() {
        boolean result = false;

        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);

        String[] selectedInvoiceUsed = form.getInvoiceUsedIdList();

        if (selectedInvoiceUsed == null || selectedInvoiceUsed.length == 0) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi, bạn chưa chọn hoá đơn cần huỷ!");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.139");
            return result;
        }
        if (form.getReasonId() == null || form.getReasonId().equals("")) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi, bạn chưa chọn lý do huỷ hoá đơn!");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.140");
            return result;
        }

        String queryString = "";
        List parameterList = new ArrayList();
        for (int i = 0; i < selectedInvoiceUsed.length; i++) {
            String temp = selectedInvoiceUsed[i];
            if (temp != null && !temp.trim().equals("") && !temp.trim().equals("false")) {
                if (!"".equals(queryString.trim())) {
                    queryString += " or ";
                }
                queryString += " invoice_used_id = ? ";
                parameterList.add(new Long(temp));
            }
        }
        if ("".equals(queryString.trim())) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi, bạn chưa chọn hoá đơn cần huỷ!");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.139");
            return result;
        }

        String roleName = CANCEL_INVOICE_NV;
        String strAddition = "";
//        Query queryObjectTMP = getSession().createSQLQuery("select name from app_params a where a.type = 'INVOICE_USED_DATE_DIS' and a.code = 'DEFAULT' ");
//        List lstTMP = queryObjectTMP.list();
//        if (lstTMP != null && lstTMP.size()>0){
//            roleName = lstTMP.get(0).toString();
//        }

        try {

            if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_INVOICE_VT), req)) {
                roleName = CANCEL_INVOICE_VT;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_INVOICE_CHT), req)) {
                roleName = CANCEL_INVOICE_CHT;
                strAddition = " shop_id != ? ";
                parameterList.add(userToken.getShopId());
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_INVOICE_NV), req)) {
                roleName = CANCEL_INVOICE_NV;
                strAddition = " staff_id != ? ";
                parameterList.add(userToken.getUserID());
            }
            //        else{
            //            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi, bạn không có quyền huỷ hoá đơn!");
            //            return result;
            //        }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        roleName = roleName.toLowerCase();
        if (strAddition.trim().equals("")) {
            queryString = " select * from v_invoice_used_role where ( " + queryString + " ) and " + roleName + " = 0 " + strAddition;
        } else {
            queryString = " select * from v_invoice_used_role where ( " + queryString + " ) and ((" + roleName + " = 0) or (" + roleName + " = 1 and " + strAddition + ")) ";
        }

        Query queryObject = getSession().createSQLQuery(queryString);
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List lstTemp = queryObject.list();
        if (lstTemp != null && lstTemp.size() > 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.SAE.141") + " (" + lstTemp.size() + " " + getText("ERR.SAE.142") + ")!");
            return result;
        }

        result = !result;
        return result;
    }

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
     * author : tamdt1 date : 18/11/2009 purpose : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getListStaff(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
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

    private boolean compareDate(Date createDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(sdf.format(createDate));
            Date today = sdf.parse(sdf.format(new Date()));
            if (date.compareTo(today) >= 0) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            log.error("ERROR: ", ex);
        }
        return false;
    }
    /*
     * Yeu cau in lai hoa don
     */

    public String requestPrint() throws Exception {
        String pageForward = "saleManagmentPrintInvoice";
        getReqSession();
        UserToken userToken = (UserToken) (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        try {
            String strInvoiceUsedId = getRequest().getParameter("invoiceUsedId");
            if (strInvoiceUsedId == null) {
                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.noInvoiceId");
                return pageForward;
            }
            Long invoiceUsedId = Long.parseLong(strInvoiceUsedId);
            InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
            invoiceUsedDAO.setSession(getSession());
            InvoiceUsed invoiceUsed = invoiceUsedDAO.findById(invoiceUsedId);
            if (invoiceUsed == null) {
                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.noInvoiceId");
                return pageForward;
            }
            invoiceUsed.setRequestPrint(1L);
            invoiceUsed.setRequestPrintDate(getSysdate());
            invoiceUsed.setRequestPrintUser(userToken.getLoginName());

            getSession().update(invoiceUsed);
            getSession().getTransaction().commit();
            getSession().beginTransaction();
            req.setAttribute("resultPrintInvoice", "MSG.request.print.suc");
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultPrintInvoice", ex.getMessage());
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
        }
        return pageForward;
    }
    /*
     * Duyet in lai
     */

    public String approvePrint() throws Exception {
        String pageForward = "saleManagmentPrintInvoice";
        getReqSession();
        UserToken userToken = (UserToken) (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        try {
            String strInvoiceUsedId = getRequest().getParameter("invoiceUsedId");
            if (strInvoiceUsedId == null) {
                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.noInvoiceId");
                return pageForward;
            }
            Long invoiceUsedId = Long.parseLong(strInvoiceUsedId);
            InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
            invoiceUsedDAO.setSession(getSession());
            InvoiceUsed invoiceUsed = invoiceUsedDAO.findById(invoiceUsedId);
            if (invoiceUsed == null) {
                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.noInvoiceId");
                return pageForward;
            }
            invoiceUsed.setPrintType1(0L);
            invoiceUsed.setRequestPrint(0L);
            invoiceUsed.setApprovePrintDate(getSysdate());
            invoiceUsed.setApprovePrintUser(userToken.getLoginName());

            getSession().update(invoiceUsed);
            getSession().getTransaction().commit();
            getSession().beginTransaction();

            req.setAttribute("resultPrintInvoice", "msg.category.staff.request.approveSuccess");

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultPrintInvoice", "Exception!!!");
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
        }
        return pageForward;
    }
}