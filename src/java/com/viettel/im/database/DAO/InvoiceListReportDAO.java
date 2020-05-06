package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.common.util.DateTimeUtils;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ComboListBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.InvoiceListManagerViewHelper;
import com.viettel.im.client.bean.InvoiceListReportBean;
import com.viettel.im.client.bean.InvoiceTransferLogBean;
import com.viettel.im.client.bean.VLookupInvoiceListBean;
import com.viettel.im.client.form.SearchBillForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.BookType;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 * 
 * author   :
 * date     : 18/04/2009
 * desc     : nghiep vu tra cuu hoa don
 * 
 */
public class InvoiceListReportDAO extends BaseDAOAction {

    private static final Logger log = Logger.getLogger(InvoiceListReportDAO.class);
    //dinh nghia cac hang so forward
    private String pageForward;
    private final String PREPARE_PAGE = "preparePage";
    private final String PAGE_NAVIGATOR = "pageNavigator";
    //dinh nghia cac bien session, request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String SESSION_INVOICE_LIST = "invoiceList";
    private String GET_SHOP_CODE = "getShopCode";
    private String GET_SHOP_NAME = "getShopName";
    Pattern htmlTag = Pattern.compile("<[^>]*>");
    //dinh nghia cac bien form
    private SearchBillForm form = new SearchBillForm();

    public SearchBillForm getForm() {
        return form;
    }

    public void setForm(SearchBillForm form) {
        this.form = form;
    }

    /**
     *
     * author   :
     * date     :
     * desc     : chuan bi form tim kiem thong tin dai hoa don
     * modified : tamdt1, 23/10/2010
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of InvoiceListReportDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //loai bo bien session
            req.getSession().setAttribute(SESSION_INVOICE_LIST, new ArrayList());

            //reset form
            this.form.resetForm();

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method preparePage of InvoiceListReportDAO");
        pageForward = PREPARE_PAGE;
        return pageForward;
    }

    /**
     *
     * author   :
     * date     :
     * desc     : tim kiem dai hoa don thoa man dieu kien
     * modified : tamdt1, 23/10/2010
     *
     */
    public String searchBills() throws Exception {
        log.info("Begin method searchBills of InvoiceListReportDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            req.getSession().setAttribute(SESSION_INVOICE_LIST, new ArrayList<VLookupInvoiceListBean>());

            //kiem tra tinh hop le cua cac truong nhap vao
            Long bookTypeId = null;
            Long fromInvoice = null;
            Long toInvoice = null;
            Long ownerId = null;
            String billDepartmentKind = this.form.getBillDepartmentKind();
            String billDepartmentName = this.form.getBillDepartmentName();
            Long invoiceType = this.form.getInvoiceType();
            String status = this.form.getStatus();

            //kiem tra tinh hop le cua kho
            //lay thong tin ma kho (kho don vi hoac kho nhan vien)
            String strQuery;
            Query query = null;
            if (billDepartmentName != null && !"".equals(billDepartmentName.trim())) {
                if (billDepartmentKind == null || billDepartmentKind.trim().equals("")) {
                    //truong hop co ma kho ma khong co loai kho -> loi
                    //
                    req.setAttribute("returnMsg", "reportBills.error.ownerTypeNotExists");

                    //return
                    log.info("End method searchBills of InvoiceListReportDAO");
                    pageForward = PREPARE_PAGE;
                    return pageForward;

                } else if (billDepartmentKind.trim().equals(Constant.OBJECT_TYPE_SHOP)) {
                    strQuery = "SELECT a.SHOP_ID FROM SHOP a WHERE a.SHOP_CODE = ? AND a.STATUS = ? AND (a.SHOP_PATH LIKE ? ESCAPE '$' OR a.SHOP_ID = ? ) ";
                    query = session.createSQLQuery(strQuery);
                    query.setParameter(0, billDepartmentName.trim());
                    query.setParameter(1, Constant.STATUS_USE);
                    query.setParameter(2, "%$_" + userToken.getShopId().toString() + "$_%");
                    query.setParameter(3, userToken.getShopId());

                } else {
                    strQuery = "SELECT b.STAFF_ID FROM STAFF b, SHOP a WHERE b.SHOP_ID = a.SHOP_ID AND b.STAFF_CODE = ? AND a.STATUS = ? AND (a.SHOP_PATH LIKE ? ESCAPE '$' OR a.SHOP_ID = ? ) AND b.STATUS = ? ";
                    query = session.createSQLQuery(strQuery);
                    query.setParameter(0, billDepartmentName.trim());
                    query.setParameter(1, Constant.STATUS_USE);
                    query.setParameter(2, "%$_" + userToken.getShopId().toString() + "$_%");
                    query.setParameter(3, userToken.getShopId());
                    query.setParameter(4, Constant.STATUS_USE);
                }


                List listObject = query.list();
                if (listObject == null || listObject.size() <= 0) {
                    //truong hop khong tim thay doi tuong tuong ung voi ma
                    //
                    req.setAttribute(REQUEST_MESSAGE, "reportBills.error.ownerNotExists");

                    //return
                    log.info("End method searchBills of InvoiceListReportDAO");
                    pageForward = PREPARE_PAGE;
                    return pageForward;

                } else {
                    ownerId = Long.valueOf(listObject.get(0).toString());
                }
            }

            //kiem tra tinh hop le cua dai hoa don
            String serialNo = this.form.getBillSerial();
            if (serialNo != null && !serialNo.trim().equals("")) {
                strQuery = " from BookType where lower(serialCode) = ? and status = ? ";
                query = session.createQuery(strQuery);
                query.setParameter(0, serialNo.trim().toLowerCase());
                query.setParameter(1, Constant.STATUS_USE);
                List<BookType> listBookType = query.list();
                if (listBookType == null || listBookType.size() <= 0) {
                    //khong tim thay dai hoa don
                    //
                    req.setAttribute(REQUEST_MESSAGE, "reportBills.error.invalidBillSerial");

                    //return
                    log.info("End method searchBills of InvoiceListReportDAO");
                    pageForward = PREPARE_PAGE;
                    return pageForward;

                } else {
                    bookTypeId = listBookType.get(0).getBookTypeId();
                }
            }

            //kiem tra tinh hop le cua tu so, den so
            String strStartNum = this.form.getBillStartNumber();
            if (strStartNum != null && !strStartNum.trim().equals("")) {
                try {
                    fromInvoice = Long.parseLong(strStartNum.trim());
                } catch (Exception ex) {
                    //so hoa don dau dai ko hop le
                    req.setAttribute(REQUEST_MESSAGE, "reportBills.error.invalidBillStartNum");

                    //return
                    log.info("End method searchBills of InvoiceListReportDAO");
                    pageForward = PREPARE_PAGE;
                    return pageForward;
                }
            }

            if (fromInvoice != null && fromInvoice.compareTo(0L) < 0) {
                //so hoa don dau dai ko hop le
                req.setAttribute(REQUEST_MESSAGE, "reportBills.error.invalidBillStartNum");

                //return
                log.info("End method searchBills of InvoiceListReportDAO");
                pageForward = PREPARE_PAGE;
                return pageForward;
            }

            String strEndNum = this.form.getBillEndNumber();
            if (strEndNum != null && !strEndNum.trim().equals("")) {
                try {
                    toInvoice = Long.parseLong(strEndNum.trim());
                } catch (Exception ex) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "reportBills.error.invalidBillEndNum");

                    //return
                    log.info("End method searchBills of InvoiceListReportDAO");
                    pageForward = PREPARE_PAGE;
                    return pageForward;
                }
            }

            if (fromInvoice != null && fromInvoice.compareTo(0L) < 0) {
                //so hoa don dau dai ko hop le
                req.setAttribute(REQUEST_MESSAGE, "reportBills.error.invalidBillEndNum");

                //return
                log.info("End method searchBills of InvoiceListReportDAO");
                pageForward = PREPARE_PAGE;
                return pageForward;
            }

            if (fromInvoice != null && toInvoice != null && fromInvoice.compareTo(toInvoice) > 0) {
                //
                req.setAttribute(REQUEST_MESSAGE, "reportBills.error.invalidNumberRange");

                //return
                log.info("End method searchBills of InvoiceListReportDAO");
                pageForward = PREPARE_PAGE;
                return pageForward;
            }

            //lay danh sach hoa don
            StringBuilder strQueryInvoiceList = new StringBuilder("");
            List listParam = new ArrayList();

            strQueryInvoiceList.append("SELECT  a.invoice_list_id AS invoiceListId, ");
            strQueryInvoiceList.append("        a.serial_code AS serialCode, ");
            strQueryInvoiceList.append("        a.block_name AS blockName, ");
            strQueryInvoiceList.append("        a.invoice_type AS invoiceType, ");
            strQueryInvoiceList.append("        a.block_no AS blockNo, ");
            strQueryInvoiceList.append("        a.from_invoice AS fromInvoice, ");
            strQueryInvoiceList.append("        a.to_invoice AS toInvoice, ");
            strQueryInvoiceList.append("        a.shop_code AS shopCode, ");
            strQueryInvoiceList.append("        a.shop_name AS shopName, ");
            strQueryInvoiceList.append("        a.staff_code AS staffCode, ");
            strQueryInvoiceList.append("        a.staff_name AS staffName, ");
            strQueryInvoiceList.append("        a.user_assign AS userAssign, ");
            strQueryInvoiceList.append("        a.date_assign AS dateAssign, ");
            strQueryInvoiceList.append("        a.invoice_list_status AS invoiceListStatus ");
            strQueryInvoiceList.append("FROM    v_lookup_invoice_list a ");
            strQueryInvoiceList.append("WHERE   1 = 1 ");

            //neu co tieu chi tim kiem theo kho
            if (ownerId != null && ownerId.compareTo(0L) > 0) {
                if (Constant.OWNER_TYPE_SHOP.toString().equals(billDepartmentKind)) {
                    //loai kho la shop
                    strQueryInvoiceList.append("AND   a.shop_id = ? ");
                    strQueryInvoiceList.append("AND   a.staff_id is null ");
                    listParam.add(ownerId);

                } else if (Constant.OWNER_TYPE_STAFF.toString().equals(billDepartmentKind)) {
                    //loai kho la staff
                    strQueryInvoiceList.append("AND   a.staff_id = ? ");
                    listParam.add(ownerId);

                } else {
                    //khong thuoc 2 loai tren -> bao loi
                    req.setAttribute(REQUEST_MESSAGE, "reportBills.error.ownerTypeNotSelect");

                    //return
                    log.info("End method searchBills of InvoiceListReportDAO");
                    pageForward = PREPARE_PAGE;
                    return pageForward;
                }
            } else {
                //truong hop tim kiem theo loai kho, shop hoac staff nhung ko co ma kho cu the
                if (Constant.OWNER_TYPE_SHOP.toString().equals(billDepartmentKind)) {
                    //loai kho la shop
                    strQueryInvoiceList.append("AND   a.shop_id is not null ");
                    strQueryInvoiceList.append("AND   a.staff_id is null ");

                } else if (Constant.OWNER_TYPE_STAFF.toString().equals(billDepartmentKind)) {
                    //loai kho la staff
                    strQueryInvoiceList.append("AND   a.shop_id is not null ");
                    strQueryInvoiceList.append("AND   a.staff_id is not null ");
                }
            }

            //neu co tieu chi tim kiem theo loai hoa don
            if (bookTypeId != null && bookTypeId.compareTo(0L) > 0) {
                if (invoiceType != null && invoiceType.compareTo(0L) > 0) {
                    //
                    strQueryInvoiceList.append("AND   a.book_type_id = ? ");
                    listParam.add(bookTypeId);

                } else {
                    //khong co invoiceType -> bao loi
                    req.setAttribute(REQUEST_MESSAGE, "reportBills.error.invoiceTypeNotSelect");

                    //return
                    log.info("End method searchBills of InvoiceListReportDAO");
                    pageForward = PREPARE_PAGE;
                    return pageForward;
                }
            } else {
                //truong hop tim kiem theo loai hoa don, HD ban hang HD gach no, nhung ko co ma hoa don cu the
                if (invoiceType != null && invoiceType.compareTo(0L) > 0) {
                    strQueryInvoiceList.append("AND   a.invoice_type = ? ");
                    listParam.add(invoiceType);
                }
            }

            //truong hop tim kiem theo trang thai
            if (status != null && !status.trim().equals("")) {
                strQueryInvoiceList.append("AND   a.invoice_list_status = ? ");
                listParam.add(Long.valueOf(status.trim()));
            }

            if (fromInvoice != null && toInvoice == null) {
                strQueryInvoiceList.append("AND   a.from_invoice <= ? ");//sua tieu chi theo mzb
                strQueryInvoiceList.append("AND   a.to_invoice >= ? ");//sua tieu chi theo mzb
                listParam.add(fromInvoice);
                listParam.add(fromInvoice);
            } else if (fromInvoice == null && toInvoice != null) {
                strQueryInvoiceList.append("AND   a.from_invoice <= ? ");//sua tieu chi theo mzb
                strQueryInvoiceList.append("AND   a.to_invoice >= ? ");//sua tieu chi theo mzb
                listParam.add(toInvoice);
                listParam.add(toInvoice);
            } else if (fromInvoice != null && toInvoice != null) {
                strQueryInvoiceList.append(" AND ( ");
                strQueryInvoiceList.append(" (a.from_invoice <= ? AND a.to_invoice >= ?) ");//sua tieu chi theo mzb
                listParam.add(fromInvoice);
                listParam.add(fromInvoice);
                
                strQueryInvoiceList.append(" OR ");
                strQueryInvoiceList.append(" (a.from_invoice <= ? AND a.to_invoice >= ?) ");//sua tieu chi theo mzb
                listParam.add(toInvoice);
                listParam.add(toInvoice);
                
                strQueryInvoiceList.append(" OR ");
                strQueryInvoiceList.append(" (a.from_invoice >= ? AND a.to_invoice <= ?) ");//sua tieu chi theo mzb
                listParam.add(fromInvoice);
                listParam.add(toInvoice);
                
                
                strQueryInvoiceList.append(" ) ");
                               
                
            }

            strQueryInvoiceList.append("ORDER BY a.invoice_type, a.serial_code, a.block_no, a.from_invoice ");

            Query queryInvoiceList = session.createSQLQuery(strQueryInvoiceList.toString()).
                    addScalar("invoiceListId", Hibernate.LONG).
                    addScalar("serialCode", Hibernate.STRING).
                    addScalar("blockName", Hibernate.STRING).
                    addScalar("invoiceType", Hibernate.LONG).
                    addScalar("blockNo", Hibernate.STRING).
                    addScalar("fromInvoice", Hibernate.LONG).
                    addScalar("toInvoice", Hibernate.LONG).
                    addScalar("shopCode", Hibernate.STRING).
                    addScalar("shopName", Hibernate.STRING).
                    addScalar("staffCode", Hibernate.STRING).
                    addScalar("staffName", Hibernate.STRING).
                    addScalar("userAssign", Hibernate.STRING).
                    addScalar("dateAssign", Hibernate.DATE).
                    addScalar("invoiceListStatus", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(VLookupInvoiceListBean.class));

            for (int i = 0; i < listParam.size(); i++) {
                queryInvoiceList.setParameter(i, listParam.get(i));
            }

            List<VLookupInvoiceListBean> listVLookupInvoiceListBean = queryInvoiceList.list();
            req.getSession().setAttribute(SESSION_INVOICE_LIST, listVLookupInvoiceListBean);

            if (listVLookupInvoiceListBean != null && listVLookupInvoiceListBean.size() > 0) {
                //
                req.setAttribute(REQUEST_MESSAGE, "reportBills.success.search");
                List listParamValue = new ArrayList();
                listParamValue.add(listVLookupInvoiceListBean.size());
                req.setAttribute(REQUEST_MESSAGE_PARAM, listParamValue);

            } else {
                //
                req.setAttribute(REQUEST_MESSAGE, "reportBills.unsuccess.search");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method searchBills of InvoiceListReportDAO");
        pageForward = PREPARE_PAGE;
        return pageForward;
    }

    /**
     *
     * author   :
     * date     :
     * desc     : phan trang
     *
     */
    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of InvoiceListReportDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method pageNavigator of InvoiceListReportDAO");
        pageForward = PAGE_NAVIGATOR;
        return pageForward;
    }

    //
    public String lookUpInvoiceHistory() throws Exception {
        log.info("Begin.");
        String pageForward = "lookUpInvoiceHistory";
        String serialNo = (String) getRequest().getParameter("serialNo");
        Long fromInvoice = Long.parseLong(getRequest().getParameter("fromInvoice"));
        Long toInvoice = Long.parseLong(getRequest().getParameter("toInvoice"));
        Long blockNo = Long.parseLong(getRequest().getParameter("blockNo"));

        InvoiceListDAO dao = new InvoiceListDAO();
        dao.setSession(getSession());

        List<InvoiceTransferLogBean> invoiceHistoryList = dao.lookUpInvoiceHistory(serialNo, blockNo, fromInvoice, toInvoice);

        getRequest().getSession().setAttribute("invoiceListHistory", invoiceHistoryList);


        return pageForward;
    }

    public String expList2Excel() {
        String pageForward = "reportBillExcel";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        try {
            List<InvoiceListReportBean> reportList = (List<InvoiceListReportBean>) req.getSession().getAttribute("invoiceList");
            //Danh sach rong
            if (null == reportList || 0 == reportList.size()) {
                throw new Exception("Danh sách rỗng");
            }

            String DATE_FORMAT_1 = "dd/MM/yyyy";
            SimpleDateFormat sdf_1 = new SimpleDateFormat(DATE_FORMAT_1);
            for (InvoiceListReportBean b : reportList) {
                if (null == b.getStaffCode() || "".equals(b.getStaffCode().trim())) {
                    b.setStoreString(b.getDepartmentCode());
                } else {
                    b.setStoreString(b.getStaffCode());
                }
                if (null != b.getDateAssign()) {
                    b.setDateString(sdf_1.format(b.getDateAssign()));
                }
            }


            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String templatePath = "";

            templatePath = tmp + "Invoice_List.xls";
            filePath += "Invoice_List_" + userToken.getLoginName() + "_" + date + ".xls";

            form.setPathFile(filePath);

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            beans.put("userCreate", userToken.getFullName());
            beans.put("reportList", reportList);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
            form.setResultMsg("Đã xuất ra file Excel thành công!");
        } catch (Exception e) {
            form.setResultMsg(e.getMessage());
            e.printStackTrace();
        }

        return pageForward;
    }
    //MrSun
    Map lstItems = new HashMap();

    public Map getLstItems() {
        return lstItems;
    }

    public void setLstItems(Map lstItems) {
        this.lstItems = lstItems;
    }

    public String getShopCode() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

            String ShopType = form.getBillDepartmentKind();
            String ShopCode = form.getBillDepartmentName();

            if (ShopCode != null && ShopCode.length() > 0) {
                ShopCode = ShopCode.split(",")[0].trim();
            }

            lstItems.clear();
            CommonDAO commonDAO = new CommonDAO();
            List<ComboListBean> list = commonDAO.getShopAndStaffList(userToken.getShopId(), null, ShopCode, ShopType, 0, false, false);

            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    lstItems.put(list.get(i).getObjId(), list.get(i).getObjCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GET_SHOP_CODE;
    }

    public String getShopName() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String objType = req.getParameter("billDepartmentKind");
            String objId = req.getParameter("billDepartmentId");

            if (objId != null && objId.trim().length() > 0) {

                CommonDAO commonDAO = new CommonDAO();
                List<ComboListBean> listShop = commonDAO.getShopAndStaffList(null, Long.valueOf(objId), "", objType, 0, false, false);

//                Long shopId = Long.valueOf(strShopId);
//                String queryString = "from Shop where shopId = ? ";
//                Query queryObject = getSession().createQuery(queryString);
//                queryObject.setParameter(0, shopId);
//                queryObject.setMaxResults(8);
//                List<Shop> listShop = queryObject.list();

                if (listShop != null && listShop.size() > 0) {
                    this.lstItems.put("billDepartmentNameB", listShop.get(0).getObjName());
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return GET_SHOP_NAME;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : 
     *
     */
    public List<ImSearchBean> getListShopOrStaff(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        String ShopType = imSearchBean.getOtherParamValue().trim();
        String ShopCode = imSearchBean.getCode().trim();
        if (ShopCode != null && ShopType != null) {
            if ("".equals(ShopType)) {
                return listImSearchBean;
            }
            List listParameter1 = new ArrayList();
            String queryString = ((ShopType.equals("2")) ? "select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) " : "select new com.viettel.im.client.bean.ImSearchBean(b.shopCode, b.name)");
            queryString += " from " + ((ShopType.equals("2")) ? " Staff a where a.status = ? " : " Shop b where b.status = ? ");
            //queryString +=
            //        " where status = ? ";
            listParameter1.add(Constant.STATUS_USE);
            queryString += " and " + ((ShopType.equals("2")) ? " lower(a.staffCode) " : " lower(b.shopCode) ") + " like ? ";
            listParameter1.add("%" + ShopCode.toLowerCase() + "%");
            if (!ShopType.equals("1")) {
                queryString += " and a.shopId = ? and (a.channelTypeId in (select channelTypeId from ChannelType where isVtUnit = '" + Constant.IS_VT_UNIT + "' and objectType = '" + Constant.OBJECT_TYPE_STAFF + "') ";
                //haint41 28/9/2011 : bo sung them CTV 
                queryString += " or  a.channelTypeId in (select channelTypeId from ChannelType where isVtUnit = '" + Constant.IS_NOT_VT_UNIT + "' and objectType = '" + Constant.OBJECT_TYPE_STAFF + "')) ";
                listParameter1.add(userToken.getShopId());
                if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                    queryString += " and lower(a.name) like ? ";
                    listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                }
            } else {
                queryString += " and (b.shopPath like ? and b.channelTypeId in (select channelTypeId from ChannelType where isVtUnit = '" + Constant.IS_VT_UNIT + "' and objectType = '" + Constant.OBJECT_TYPE_SHOP + "') or b.shopId = ?) ";
                listParameter1.add("%_" + userToken.getShopId().toString() + "_%");
                listParameter1.add(userToken.getShopId());
                if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                    queryString += " and lower(b.name) like ? ";
                    listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                }
            }

            queryString += " and rownum < ? ";
            listParameter1.add(300L);
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < listParameter1.size(); i++) {
                queryObject.setParameter(i, listParameter1.get(i));
            }
            listImSearchBean = queryObject.list();
            return listImSearchBean;
        }
        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : 
     *
     */
    public List<ImSearchBean> getNameShopOrStaff(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        String ShopType = imSearchBean.getOtherParamValue().trim();
        String ShopCode = imSearchBean.getCode().trim();
        if (ShopCode != null && ShopType != null) {
            if ("".equals(ShopType)) {
                return listImSearchBean;
            }
            List listParameter1 = new ArrayList();
            String queryString = ((ShopType.equals("2")) ? "select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) " : "select new com.viettel.im.client.bean.ImSearchBean(b.shopCode, b.name)");
            queryString += " from " + ((ShopType.equals("2")) ? " Staff a where a.status = ? " : " Shop b where b.status = ? ");
            //queryString +=
            //        " where status = ? ";
            listParameter1.add(Constant.STATUS_USE);
            queryString += " and " + ((ShopType.equals("2")) ? " lower(a.staffCode) " : " lower(b.shopCode) ") + " = ? ";
            listParameter1.add(ShopCode.toLowerCase());
            if (!ShopType.equals("1")) {
                queryString += " and a.shopId = ? and a.channelTypeId in (14) ";
                listParameter1.add(userToken.getShopId());
            } else {
                queryString += " and (b.shopPath like ? and b.channelTypeId in (1) or b.shopId = ?) ";
                listParameter1.add("%_" + userToken.getShopId().toString() + "_%");
                listParameter1.add(userToken.getShopId());
            }
            queryString += " and rownum < ? ";
            listParameter1.add(300L);
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < listParameter1.size(); i++) {
                queryObject.setParameter(i, listParameter1.get(i));
            }
            listImSearchBean = queryObject.list();
            return listImSearchBean;
        }
        return listImSearchBean;
    }
}
