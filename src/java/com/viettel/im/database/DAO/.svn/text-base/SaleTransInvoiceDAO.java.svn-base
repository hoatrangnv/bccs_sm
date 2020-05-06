/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.SaleTransInvoiceBean;
import com.viettel.im.client.bean.SaleTransDetailBean;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.InvoiceList;
import com.viettel.im.database.BO.InvoiceUsed;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.viettel.im.client.bean.ComboListBean;
import com.viettel.im.client.bean.CurrentInvoiceListBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.database.BO.BookType;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import com.viettel.im.database.BO.SaleInvoiceType;
import org.hibernate.Query;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.viettel.im.client.form.SaleTransInvoiceForm;
import com.viettel.im.common.util.DataUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.InvoiceCoupon;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.sms.SmsClient;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 *
 * @author MrSun
 */
public class SaleTransInvoiceDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(SaleTransInvoiceDAO.class);
    private String SALE_TRANS_INVOICE_CAN_SELECT = "1";
    private String SALE_TRANS_INVOICE_NOT_SELECT = "0";
    private String CREATE_INVOICE_FOR_SHOP = "createInvoiceShop";
    private String CREATE_INVOICE_FOR_STAFF = "createInvoiceStaff";
    private String LIST_TELECOM_SERVICE = "lstTelecom";
    private String LIST_PAY_METHOD = "lstPayMethod";
    private String LIST_REASON = "lstReason";
    private String LIST_REASON_INVOICE = "lstReasonInvoice";
    private String LIST_SALETRANS_STATUS = "lstSaleTransStatus";
    private String LIST_SERIAL_NO = "lstSerialNo";
    private String LIST_SALE_TRANS = "lstSaleTrans";
    private String LIST_SALE_TRANS_DETAIL = "lstSaleTransDetail";
    private String SALE_TRANS_INVOICE_FORM = "SaleTransInvoiceForm";
    private String SALE_GROUP = "saleGroup";
    private String INVOICE_USED_DETAIL = "invoiceUsedDetail"; // => createInvoice.page
    private String INVOICE_SALE_TRANS = "invoiceSaleTrans"; // => invoiceSaleTrans.page
    private String INVOICE_SALE_TRANS_LIST = "invoiceSaleTransList"; // => saleTransList.page
    private String INVOICE_SALE_TRANS_DETAIL_LIST = "invoiceSaleTransDetailList"; // => saleTransDetail.page
    private String SEARCH_SALE_TRANS = "searchSaleTrans"; // => searchSaleTrans.page
    private String CREATE_INVOICE = "createInvoice"; // => createInvoice.page
    private String CREATE_INVOICE_SERIAL_NO = "createInvoiceSerialNo"; // => createInvoiceSerialNo.page    
    private static final String SALE_INVOICE_TYPE_LIST = "saleInvoiceTypeList";
    private static final String LIST_CHILD_SHOP = "childShopList";
    private static final String LIST_COLL_STAFF = "collStaffList";
    private static final String LIST_CHANNEL_TYPE = "channelTypeList";
    private static final String SUCCESS_LIST_OBJECT = "success_listObject";
    private static final String CHAR_SPLIT = ";";
    private List listObject = new ArrayList();
    private Map listModelCombo = new HashMap();
    private Map priceListMap = new HashMap();
    //    TrongLV 11-11-07
    //fix khong quan ly hang hoa theo kenh
    private final boolean IS_STOCK_STAFF_OWNER = true;

    public Map getListModelCombo() {
        return listModelCombo;
    }

    public void setListModelCombo(Map listModelCombo) {
        this.listModelCombo = listModelCombo;
    }

    public Map getPriceListMap() {
        return priceListMap;
    }

    public void setPriceListMap(Map priceListMap) {
        this.priceListMap = priceListMap;
    }
    private SaleTransInvoiceForm form = new SaleTransInvoiceForm();

    public SaleTransInvoiceForm getForm() {
        return form;
    }

    public void setForm(SaleTransInvoiceForm form) {
        this.form = form;
    }

    private void ClearSession() {
        try {
            getReqSession();
            reqSession.setAttribute(LIST_CHANNEL_TYPE, null);
            reqSession.setAttribute(LIST_TELECOM_SERVICE, null);
            reqSession.setAttribute(LIST_PAY_METHOD, null);
            reqSession.setAttribute(LIST_REASON, null);
            reqSession.setAttribute(LIST_REASON_INVOICE, null);
            reqSession.setAttribute(LIST_SALETRANS_STATUS, null);
            reqSession.setAttribute(SALE_INVOICE_TYPE_LIST, null);
            reqSession.setAttribute(SALE_TRANS_INVOICE_FORM, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkAuthorityCreateInvoice() {
        try {
            getReqSession();
            HttpServletRequest req = getRequest();
            reqSession.setAttribute(CREATE_INVOICE_FOR_SHOP, false);
            reqSession.setAttribute(CREATE_INVOICE_FOR_STAFF, false);

            if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CREATE_INVOICE_FOR_SHOP), req)) {
                reqSession.setAttribute(CREATE_INVOICE_FOR_SHOP, true);
                System.out.println(CREATE_INVOICE_FOR_SHOP);
            } else {
                System.out.println("Không có quyền -> " + CREATE_INVOICE_FOR_SHOP);
            }
            if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CREATE_INVOICE_FOR_STAFF), req)) {
                reqSession.setAttribute(CREATE_INVOICE_FOR_STAFF, true);
                System.out.println(CREATE_INVOICE_FOR_STAFF);
            } else {
                System.out.println("Không có quyền -> " + CREATE_INVOICE_FOR_STAFF);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String preparePage() throws Exception {
        String pageForward;
        getReqSession();
        try {
            form = new SaleTransInvoiceForm();
            UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
            ClearSession();

            //TelecomService
            TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
            telecomServiceDAO.setSession(getSession());
            List<TelecomService> lstTelecom = telecomServiceDAO.findByProperty("STATUS", Constant.STATUS_USE);
            reqSession.setAttribute(LIST_TELECOM_SERVICE, lstTelecom);

            //PayMethod
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(getSession());
            List lstPayMethod = appParamsDAO.findAppParamsList("PAY_METHOD", Constant.STATUS_USE.toString());
            reqSession.setAttribute(LIST_PAY_METHOD, lstPayMethod);

            //SaleTransStatus
            List lstSaleTransStatus = appParamsDAO.findAppParamsList("SALE_TRANS.STATUS", Constant.STATUS_USE.toString());
            reqSession.setAttribute(LIST_SALETRANS_STATUS, lstSaleTransStatus);

            //SaleGroup
            String saleGroup = (String) req.getParameter("saleGroup");
            if (saleGroup == null || "".equals(saleGroup.trim())) {
                saleGroup = Constant.SALE_GROUP_RETAIL;
            }
            saleGroup = saleGroup.trim();
            form.setSaleGroup(Long.valueOf(saleGroup));

            //Lap hoa don thay
            //Phan quyen lap hoa don cho DL hay cho CTV/DB
            if (saleGroup.equals(Constant.SALE_GROUP_COL_RETAIL)) {
                System.out.println("Kiểm tra quyền lập hoá đơn thay!");
                checkAuthorityCreateInvoice();

                Boolean forShop = (Boolean) reqSession.getAttribute(CREATE_INVOICE_FOR_SHOP);
                Boolean forStaff = (Boolean) reqSession.getAttribute(CREATE_INVOICE_FOR_STAFF);
                if (forShop == null) {
                    forShop = false;
                }
                if (forStaff == null) {
                    forStaff = false;
                }

                //ChannelType List
                if (forShop && forStaff) {
                    reqSession.setAttribute(LIST_CHANNEL_TYPE, AccountBalanceDAO.getChannelTypeList(this.getSession()));
                } else if (forShop && !forStaff) {
                    reqSession.setAttribute(LIST_CHANNEL_TYPE, AccountBalanceDAO.getChannelTypeList(this.getSession(), Constant.OBJECT_TYPE_SHOP, Constant.NOT_IS_VT_UNIT));
                } else if (!forShop && forStaff) {
                    reqSession.setAttribute(LIST_CHANNEL_TYPE, AccountBalanceDAO.getChannelTypeList(this.getSession(), Constant.OBJECT_TYPE_STAFF, Constant.NOT_IS_VT_UNIT));
                }
            }

            SaleInvoiceTypeDAO saleInvoiceTypeDAO = new SaleInvoiceTypeDAO();
            saleInvoiceTypeDAO.setSession(getSession());
            List<SaleInvoiceType> saleInvoiceTypeList = saleInvoiceTypeDAO.findByProperty(SALE_GROUP, form.getSaleGroup());
            reqSession.setAttribute(SALE_INVOICE_TYPE_LIST, saleInvoiceTypeList);
            if (saleInvoiceTypeList.size() == 1) {
                form.setSaleTransTypeSearch(String.valueOf(saleInvoiceTypeList.get(0).getSaleTransType()));
                //Neu size = 1 -> disable loai giao dich

                //AgentList
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());
                shopDAO.setObjectTypeFilter(Constant.OBJECT_TYPE_SHOP);
                shopDAO.setIsVTUnitFilter(Constant.NOT_IS_VT_UNIT);
                List<Shop> childShopList = shopDAO.findAgentShop(userToken.getShopId(), Constant.STATUS_USE);
                reqSession.setAttribute(LIST_CHILD_SHOP, childShopList);
            } else {
                //StaffList
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                List collStaffList = staffDAO.findAllCollaboratorOfStaff(userToken.getUserID());
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                channelTypeDAO.setSession(getSession());
//                List<ChannelType> channelTypeList = channelTypeDAO.findIsVTUnitActive(Constant.NOT_IS_VT_UNIT);
                List<ChannelType> channelTypeList = channelTypeDAO.findIsVTUnitActiveAndIsNotPrivate(Constant.NOT_IS_VT_UNIT);//2011-05-24
                reqSession.setAttribute(LIST_CHANNEL_TYPE, channelTypeList);
                reqSession.setAttribute(LIST_COLL_STAFF, collStaffList);
            }

            //Reason
            CommonDAO commonDAO = new CommonDAO();
            List lstReason = commonDAO.getReasonSaleList(form.getSaleGroup(), null);
            reqSession.setAttribute(LIST_REASON, lstReason);

            //fromDate - toDate
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            form.setToDateSearch(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, Integer.valueOf(Constant.DATE_DIS_INVOICE_DAY.toString()));
            form.setFromDateSearch(sdf.format(cal.getTime()));

            //shopId - staffId
            form.setShopId(userToken.getShopId().toString());
            form.setStaffId(userToken.getUserID().toString());

            //status
            form.setSaleTransStatusSearch("2");


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        pageForward = INVOICE_SALE_TRANS;
        return pageForward;
    }

    public String pageNavigator() {
        String pageForward = SEARCH_SALE_TRANS;
        try {
            searchSaleTrans();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pageForward;
    }

    /**
     * Search sale transactions to create invoice
     *
     * @return
     * @throws Exception
     */
    public String searchSaleTrans() throws Exception {
        String pageForward = INVOICE_SALE_TRANS_LIST;
        getReqSession();
        try {
            if (!validateSearchSaleTrans(form)) {
                return pageForward;
            }

            //Goi ham tim kiem
            List<SaleTransInvoiceBean> saleTransList = getSaleTransList(form);
            form.setCanSelect("1");
            //session.setAttribute(LIST_SALE_TRANS, saleTransList);
            req.setAttribute(LIST_SALE_TRANS, saleTransList);//Khong luu xuong sesstion -> luu xuong request

            //Thong bao ket qua tim kiem
            if (saleTransList != null && saleTransList.size() > 0) {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleInvoice.success.searchSaleTrans");
                List listParamValue = new ArrayList();
                listParamValue.add(saleTransList.size());
                req.setAttribute("returnMsgValue", listParamValue);
            } else {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleInvoice.ụnsuccess.searchSaleTrans");
            }
        } catch (Exception e) {
            System.out.print("Error: ");
            System.out.print(e.getMessage());
            req.setAttribute(Constant.RETURN_MESSAGE, e.getMessage());
            return pageForward;
        }

        //Luu thong tin form        
        reqSession.setAttribute(SALE_TRANS_INVOICE_FORM, null);
        reqSession.setAttribute(SALE_TRANS_INVOICE_FORM, form);

        return pageForward;
    }

    /**
     * validate input infomation before searching sale transactions
     *
     * @param saleTransInvoiceForm
     * @return
     */
    public boolean validateSearchSaleTrans(SaleTransInvoiceForm f) {
        boolean result = false;

        f.setObjTypeSearch("");
        f.setObjIdSearch("");


        if (form.getSaleGroup() != null && form.getSaleGroup().toString().equals(Constant.SALE_GROUP_COL_RETAIL)) {
            Boolean forShop = (Boolean) reqSession.getAttribute(CREATE_INVOICE_FOR_SHOP);
            Boolean forStaff = (Boolean) reqSession.getAttribute(CREATE_INVOICE_FOR_STAFF);
            if (forShop == null) {
                forShop = false;
            }
            if (forStaff == null) {
                forStaff = false;
            }
            if (!forShop && !forStaff) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Bạn không có quyền lập hoá đơn thay!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.101");
                return result;
            }

            //Mac dinh neu chi co quyen lap hoa don thay cho dai ly -> chi tim doi tuong shop; neu chi co quyen lap hoa don thay cho CTv -> chi tim doi tuong thuoc staff
            if (forShop && !forStaff) {
                f.setObjTypeSearch(Constant.OBJECT_TYPE_SHOP);
            } else if (!forShop && forStaff) {
                f.setObjTypeSearch(Constant.OBJECT_TYPE_STAFF);
            }
        }

        String sFromDate = form.getFromDateSearch();
        String sToDate = form.getToDateSearch();
        if (sFromDate == null || "".equals(sFromDate.trim())) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "Chưa chọn từ ngày");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.122");
            return result;
        }
        if (sToDate == null || "".equals(sToDate.trim())) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "Chưa chọn đến ngày");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.123");
            return result;
        }

        Date fromDate;
        Date toDate;
        Date currentDate = DateTimeUtils.getSysDate();
        try {
            fromDate = DateTimeUtils.convertStringToDate(sFromDate);
        } catch (Exception ex) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.024");
//            req.setAttribute(Constant.RETURN_MESSAGE, "Từ ngày chưa chính xác");
            return result;
        }
        if (fromDate.after(currentDate)) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "Từ ngày không được lớn hơn ngày hiện tại");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.042");
            return result;
        }
        try {
            toDate = DateTimeUtils.convertStringToDate(sToDate);
            //toDate = DateTimeUtils.addDate(toDate, 1);
        } catch (Exception ex) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "Đến ngày không chính xác");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.025");
            return result;
        }
        if (toDate.after(currentDate)) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "Đến ngày không được lớn hơn ngày hiện tại");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.102");
            return result;
        }
        if (fromDate.after(toDate)) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "Từ ngày không được lớn hơn đến ngày");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.026");
            return result;
        }


        //Kiem tra loai doi tuong
        if (f.getAgentTypeIdSearch() != null && !f.getAgentTypeIdSearch().trim().equals("")) {
            CommonDAO commonDAO = new CommonDAO();
            commonDAO.setSession(this.getSession());
            String objType = commonDAO.getObjTypeByChannelTypeId(Long.valueOf(f.getAgentTypeIdSearch()));
            if (objType == null || objType.trim().equals("")) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Loại đối tượng không đúng!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.103");
                return result;
            }
            f.setObjTypeSearch(objType);

            //Kiem tra ma doi tuong
            if (f.getAgentCodeSearch() != null && !f.getAgentCodeSearch().trim().equals("")) {
                if (objType.trim().equals(Constant.OBJECT_TYPE_SHOP)) {
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());
                    List<Shop> objList = shopDAO.findByShopCode(f.getAgentCodeSearch().trim());
                    if (objList == null || objList.size() == 0) {
//                        req.setAttribute(Constant.RETURN_MESSAGE, "Mã đối tượng không đúng!");
                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.104");
                        return result;
                    }
                    f.setObjIdSearch(String.valueOf(objList.get(0).getShopId()));
                }
                if (objType.trim().equals(Constant.OBJECT_TYPE_STAFF)) {
                    StaffDAO shopDAO = new StaffDAO();
                    shopDAO.setSession(this.getSession());
                    List<Staff> objList = shopDAO.findByStaffCode(f.getAgentCodeSearch().trim());
                    if (objList == null || objList.size() == 0) {
//                        req.setAttribute(Constant.RETURN_MESSAGE, "Mã đối tượng không đúng!");
                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.104");
                        return result;
                    }
                    f.setObjIdSearch(String.valueOf(objList.get(0).getStaffId()));
                }
            }
        }

        result = !result;
        return result;
    }

    //Tim kiem danh sach giao dich
    //Su dung chung trong chuc nang: quan ly hoa don ban hang, khi loc danh sach theo so hoa don (hien thi danh sach GD thuoc hoa don)
    public List<SaleTransInvoiceBean> getSaleTransList(SaleTransInvoiceForm f) {
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);

        List lstParam = new ArrayList();
        StringBuffer queryString = new StringBuffer();
        queryString.append(" SELECT st.sale_trans_id AS saleTransId ");
        queryString.append("         ,st.sale_trans_type AS saleTransType ");
        queryString.append("         ,sit.reason_sale_group_name AS saleTransTypeName ");
        queryString.append("         ,st.sale_trans_date AS saleTransDate ");
        queryString.append("         ,st.sale_trans_date AS saleTransDate1 ");
        queryString.append("         ,st.sale_trans_code AS saleTransCode ");
        queryString.append("         ,st.receiver_id AS receiverId ");
        queryString.append("         ,st.receiver_type AS receiverType ");
        queryString.append("         ,st.shop_id AS shopId ");
        queryString.append("         ,sp.shop_Code || ' - '  || sp.name AS shopName ");
        queryString.append("         ,st.staff_id AS staffId ");
        queryString.append("         ,sf.staff_Code || ' - ' || sf.name AS staffName ");
        queryString.append("         ,st.pay_method payMethod ");
        queryString.append("         ,(select name from app_params where code = st.pay_method AND type = '" + Constant.PARAM_TYPE_PAY_METHOD + "') AS payMethodName ");

        queryString.append("         ,st.reason_id reasonId ");
        queryString.append("         ,r.reason_name AS reasonName ");
        queryString.append("         ,st.note ");
        queryString.append("         ,st.status ");
        queryString.append("         ,(select name from app_params where code = st.status AND type = '" + Constant.PARAM_TYPE_PAY_SALE_TRANS_STATUS + "') AS statusName ");

        queryString.append("         ,st.cust_name AS custName ");
        queryString.append("         ,st.sub_id AS subId ");
        queryString.append("         ,st.isdn ");
        queryString.append("         ,st.company ");
        queryString.append("         ,st.contract_no AS contractNo ");
        queryString.append("         ,st.tel_number AS telNumber ");
        queryString.append("         ,st.tin ");
        queryString.append("         ,'' as ACCOUNT ");
        queryString.append("         ,st.address ");

        queryString.append("         ,st.amount_service AS amountService ");
        queryString.append("         ,st.amount_model AS amountModel ");
        queryString.append("         ,st.amount_tax AS amountTax ");
        queryString.append("         ,st.amount_not_tax AS amountNotTax ");
        queryString.append("         ,st.vat ");
        queryString.append("         ,st.currency ");
        queryString.append("         ,st.tax ");
        queryString.append("         ,st.promotion ");
        queryString.append("         ,st.discount ");

        queryString.append("         ,st.create_staff_id AS createStaffId ");
        queryString.append("         ,(select name from staff where staff_id = st.create_staff_id) AS createStaffName ");
        queryString.append("         ,st.invoice_used_id AS invoiceUsedId ");
        queryString.append("         , st.telecom_service_id AS telecomServiceId ");
        queryString.append("         ,ts.tel_service_name AS telecomServiceName ");
        queryString.append("         ,0 AS isFineTrans ");
        queryString.append("         , st.stock_trans_id AS stockTransId ");
        queryString.append(" FROM sale_trans st ");
        queryString.append(" ");
        queryString.append("     JOIN shop sp ON st.shop_id = sp.shop_id ");
        queryString.append("     LEFT JOIN staff sf ON st.staff_id = sf.staff_id ");

        queryString.append("     LEFT JOIN telecom_service ts ON st.telecom_service_id = ts.telecom_service_id ");
        queryString.append("     LEFT JOIN reason r ON st.reason_id = r.reason_id ");
        queryString.append("     JOIN sale_invoice_type sit ON st.sale_trans_type = sit.sale_trans_type ");

        queryString.append(" ");
        queryString.append(" WHERE 1=1 ");
        if (!Constant.IS_VER_HAITI) {
            queryString.append(" and st.amount_Tax > 0 ");
        }

        if (f.getSaleTransList() != null && !f.getSaleTransList().equals("")) {
            StringBuffer sqlString = new StringBuffer();
            String[] tmpList = f.getSaleTransList().split(CHAR_SPLIT);
            for (int i = 0; i < tmpList.length; i++) {
                if (tmpList[i] != null && !tmpList[i].trim().equals("")) {
                    if (!sqlString.toString().equals("")) {
                        sqlString.append(" or ");
                    }
                    sqlString.append(" st.sale_trans_id = ? ");
                    lstParam.add(Long.valueOf(tmpList[i].trim()));
                }
            }
            if (!sqlString.toString().equals("")) {
                queryString.append("     AND ( ").append(sqlString).append(" ) ");
            }
        } else {
            //invoiceUsedId
            if (null != f.getInvoiceUsedIdSearch() && !"".equals(f.getInvoiceUsedIdSearch().trim())) {
                queryString.append("     AND st.invoice_used_id = ? ");
                lstParam.add(Long.valueOf(f.getInvoiceUsedIdSearch().trim()));
            }

            //Giao dich cua CTV/DB/DL lam dich vu
            if ((f.getSaleGroup() != null && String.valueOf(f.getSaleGroup()).equals(Constant.SALE_GROUP_COL_RETAIL))
                    || (f.getSaleTransTypeSearch() != null
                    && (f.getSaleTransTypeSearch().trim().equals(Constant.SALE_TRANS_TYPE_COL_RETAIL)
                    || f.getSaleTransTypeSearch().trim().equals(Constant.SALE_TRANS_TYPE_RETAIL_FOR_COL)))) {
                //Tim theo CTV
                if (f.getObjTypeSearch() != null && f.getObjTypeSearch().trim().equals(Constant.OBJECT_TYPE_STAFF)) {
                    //Tim theo nhan vien quan ly CTV
                    queryString.append("     AND sf.staff_owner_id = ? ");
                    lstParam.add(userToken.getUserID());

                    //Tim theo kenh
                    if (!Constant.CHECK_POINT_OF_SALE) {
                        queryString.append("     AND sf.channel_type_id = ? ");
                        if (f.getAgentTypeIdSearch() != null && !f.getAgentTypeIdSearch().trim().equals("")) {
                            lstParam.add(Long.valueOf(f.getAgentTypeIdSearch().trim()));
                        }
                    } else {
                        queryString.append("     AND (sf.channel_type_id = ? or sf.channel_type_id = ? ) and sf.point_Of_Sale = ? ");
                        lstParam.add(Constant.CHANNEL_TYPE_CTV);
                        lstParam.add(80043L);
                        if (f.getAgentTypeIdSearch() != null && f.getAgentTypeIdSearch().trim().equals("80043")) {
                            lstParam.add("1");
                        } else {
                            lstParam.add("2");
                        }
                    }

                    //Tim theo ma CTV
                    if (f.getObjIdSearch() != null && !f.getObjIdSearch().trim().equals("")) {
                        queryString.append("     AND st.staff_id = ? ");
                        lstParam.add(Long.valueOf(f.getObjIdSearch().trim()));
                    }
                } //Tim theo Dai ly
                else if (f.getObjTypeSearch() != null && f.getObjTypeSearch().trim().equals(Constant.OBJECT_TYPE_SHOP)) {
                    //Tim theo don vi quan ly Dai ly
                    queryString.append("     AND sp.parent_shop_id = ? ");
                    lstParam.add(userToken.getShopId());

                    //Tim theo kenh
                    queryString.append("     AND sp.channel_type_id = ? ");
                    if (f.getAgentTypeIdSearch() != null && !f.getAgentTypeIdSearch().trim().equals("")) {
                        lstParam.add(Long.valueOf(f.getAgentTypeIdSearch().trim()));
                    } else {
                        lstParam.add(4L);
                    }
                    //Tim theo ma Dai ly
                    if (f.getObjIdSearch() != null && !f.getObjIdSearch().trim().equals("")) {
                        queryString.append("     AND st.shop_id = ? ");
                        lstParam.add(Long.valueOf(f.getObjIdSearch().trim()));
                    }
                } else {
                    Boolean forShop = (Boolean) reqSession.getAttribute(CREATE_INVOICE_FOR_SHOP);
                    Boolean forStaff = (Boolean) reqSession.getAttribute(CREATE_INVOICE_FOR_STAFF);
                    if (forShop == null) {
                        forShop = false;
                    }
                    if (forStaff == null) {
                        forStaff = false;
                    }
                    if (forShop && forStaff) {
                        queryString.append("     AND (sf.staff_owner_id = ? OR (sp.parent_shop_id = ? and sp.channel_type_id = ?)) ");
                        lstParam.add(userToken.getUserID());
                        lstParam.add(userToken.getShopId());
                        lstParam.add(4L);
                    } else if (forShop && !forStaff) {
                        queryString.append("     AND (sp.parent_shop_id = ? and sp.channel_type_id = ?) ");
                        lstParam.add(userToken.getShopId());
                        lstParam.add(4L);
                    } else if (!forShop && forStaff) {
                        queryString.append("     AND (sf.staff_owner_id = ?) ");
                        lstParam.add(userToken.getUserID());
                    } else {
                        return null;
                    }
                }
            } else {
                //shopId
                if (null != f.getShopId() && !"".equals(f.getShopId().trim())) {
                    queryString.append("     AND st.shop_Id = ? ");
                    lstParam.add(Long.valueOf(f.getShopId().trim()));
                }

                //Giao dich cua nhan vien
                if (null != f.getStaffId() && !"".equals(f.getStaffId().trim())) {
                    queryString.append("     AND st.staff_Id = ? ");
                    lstParam.add(Long.valueOf(f.getStaffId().trim()));
                }
                // truong hop ban cho dai ly' lo'n cho phep chon dai ly
                if (null != f.getAgentIdSearch() && !"".equals(f.getAgentIdSearch().trim())) {
                    queryString.append("     AND st.receiver_id = ? ");
                    lstParam.add(Long.valueOf(f.getAgentIdSearch().trim()));
                }

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
                queryString.append("     AND st.sale_trans_date >= ? ");
                lstParam.add(fromDate);
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
                queryString.append("     AND st.sale_trans_date <= ? ");
                lstParam.add(toDate);
            }
            //saleTransType
            if (null != f.getSaleTransTypeSearch() && !"".equals(f.getSaleTransTypeSearch().trim())) {
                queryString.append("     AND st.sale_trans_type = ? ");
                lstParam.add(f.getSaleTransTypeSearch());
            } //saleGroup
            else if (null != f.getSaleGroup()) {
                queryString.append("     AND st.sale_trans_type IN (SELECT sitlist.sale_trans_type FROM sale_invoice_type sitlist WHERE sitlist.sale_group = ? ) ");
                lstParam.add(f.getSaleGroup());
            }
            //saleTransStatus
            if (null != f.getSaleTransStatusSearch() && !"".equals(f.getSaleTransStatusSearch().trim())) {
                queryString.append("     AND st.status = ? ");
                lstParam.add(Long.valueOf(f.getSaleTransStatusSearch()));
            }
            //vatType
            if (null != f.getVatSearch() && !"".equals(f.getVatSearch().trim())) {
                Long tmp = Long.parseLong(f.getVatSearch().trim());
                if (tmp.compareTo(0L) >= 0) {
                    queryString.append("     AND st.vat = ? ");
                    lstParam.add(tmp);
                } else {
                    queryString.append("     AND st.vat IS NULL ");
                }
            }
            //telecomServiceId
            if (null != f.getTelecomServiceIdSearch() && !"".equals(f.getTelecomServiceIdSearch().trim())) {
                queryString.append("     AND st.telecom_service_id = ? ");
                lstParam.add(f.getTelecomServiceIdSearch());
            }
            //payMethod
            if (null != f.getPayMethodIdSearch() && !"".equals(f.getPayMethodIdSearch().trim())) {
                queryString.append("     AND st.pay_method = ? ");
                lstParam.add(f.getPayMethodIdSearch());
            }
            //reasonId
            if (null != f.getReasonIdSearch() && !"".equals(f.getReasonIdSearch().trim())) {
                queryString.append("     AND st.reason_Id = ? ");
                lstParam.add(f.getReasonIdSearch());
            }
            //custName
            if (null != f.getCustNameSearch() && !"".equals(f.getCustNameSearch().trim())) {
                queryString.append("     AND lower(st.cust_name) LIKE ? ");
                lstParam.add("%" + f.getCustNameSearch().trim().toLowerCase() + "%");
            }
        }

        queryString.append(" ORDER BY st.sale_trans_date desc ");

        Query queryObject = getSession().createSQLQuery(queryString.toString()).
                addScalar("saleTransId", Hibernate.LONG).
                addScalar("saleTransType", Hibernate.STRING).
                addScalar("saleTransTypeName", Hibernate.STRING).
                addScalar("saleTransDate", Hibernate.DATE).
                addScalar("saleTransDate1", Hibernate.TIMESTAMP).
                addScalar("saleTransCode", Hibernate.STRING).
                addScalar("receiverId", Hibernate.LONG).
                addScalar("receiverType", Hibernate.LONG).
                addScalar("shopId", Hibernate.LONG).
                addScalar("shopName", Hibernate.STRING).
                addScalar("staffId", Hibernate.LONG).
                addScalar("staffName", Hibernate.STRING).
                addScalar("payMethod", Hibernate.STRING).
                addScalar("payMethodName", Hibernate.STRING).
                addScalar("reasonId", Hibernate.LONG).
                addScalar("reasonName", Hibernate.STRING).
                addScalar("note", Hibernate.STRING).
                addScalar("status", Hibernate.STRING).
                addScalar("statusName", Hibernate.STRING).
                addScalar("custName", Hibernate.STRING).
                addScalar("subId", Hibernate.LONG).
                addScalar("isdn", Hibernate.STRING).
                addScalar("company", Hibernate.STRING).
                addScalar("contractNo", Hibernate.STRING).
                addScalar("telNumber", Hibernate.STRING).
                addScalar("tin", Hibernate.STRING).
                addScalar("account", Hibernate.STRING).
                addScalar("address", Hibernate.STRING).
                addScalar("amountService", Hibernate.DOUBLE).
                addScalar("amountModel", Hibernate.DOUBLE).
                addScalar("amountTax", Hibernate.DOUBLE).
                addScalar("amountNotTax", Hibernate.DOUBLE).
                addScalar("vat", Hibernate.DOUBLE).
                addScalar("currency", Hibernate.STRING).
                addScalar("tax", Hibernate.DOUBLE).
                addScalar("promotion", Hibernate.DOUBLE).
                addScalar("discount", Hibernate.DOUBLE).
                addScalar("createStaffId", Hibernate.LONG).
                addScalar("createStaffName", Hibernate.STRING).
                addScalar("invoiceUsedId", Hibernate.LONG).
                addScalar("telecomServiceId", Hibernate.STRING).
                addScalar("telecomServiceName", Hibernate.STRING).
                addScalar("isFineTrans", Hibernate.LONG).
                addScalar("stockTransId", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(SaleTransInvoiceBean.class));
        for (int idx = 0; idx < lstParam.size(); idx++) {
            queryObject.setParameter(idx, lstParam.get(idx));
        }
        return queryObject.list();
    }

    public String searchSaleTransDetailList() throws Exception {
        String pageForward = INVOICE_SALE_TRANS_DETAIL_LIST;
        getReqSession();
        try {
            reqSession.setAttribute(LIST_SALE_TRANS_DETAIL, null);
            SaleTransDetailDAO saleTransDetailDAO = new SaleTransDetailDAO();
            saleTransDetailDAO.setSession(getSession());

            String saleTransIdTemp = req.getParameter("saleTransId");
            if (saleTransIdTemp == null || saleTransIdTemp.trim().equals("")) {
                return pageForward;
            }
            Long saleTransId = new Long(saleTransIdTemp);

            Long isFineTrans = null;
            String isFineTransTemp = req.getParameter("isFineTrans");
            if (isFineTransTemp != null && !isFineTransTemp.trim().equals("")) {
                isFineTrans = new Long(isFineTransTemp);
            }

            String SQL_SELECT_TRANS_DETAIL = " from SaleTransDetailFull where saleTransId = ? ";
            if (null != isFineTrans) {
                SQL_SELECT_TRANS_DETAIL += " and isFineTrans = ? ";
            }
            Query q = getSession().createQuery(SQL_SELECT_TRANS_DETAIL);
            q.setParameter(0, saleTransId);
            if (null != isFineTrans) {
                q.setParameter(1, isFineTrans);
            }

            List lstSaleTransDetail = q.list();
            if (lstSaleTransDetail.size() > 0) {
                req.setAttribute("lstSaleTransDetail", lstSaleTransDetail);
            } else {
                req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.noSaleDetail");
            }

//            session.setAttribute(LIST_SALE_TRANS_DETAIL, lstSaleTransDetail);
            req.setAttribute(LIST_SALE_TRANS_DETAIL, lstSaleTransDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageForward;
    }

    public String gotoCreateInvoice() throws Exception {
        String pageForward = INVOICE_SALE_TRANS;
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        form.setPayMethodId(Constant.PAY_METHOD_MONNEY);
        form.setSaleTransList("");

        try {

            if (userToken == null) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Time out session! Bạn phải đăng nhập lại...");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.105");
                return pageForward;
            }

            //Kiem tra co giao dich duoc chon khong?
            String[] saleTransArr = form.getSaleTransIdList();
            if ((null == saleTransArr) || (saleTransArr.length == 0)) {
                searchSaleTrans();
//                req.setAttribute(Constant.RETURN_MESSAGE, "Bạn chưa chọn giao dịch để lập hóa đơn!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.106");
                return pageForward;
            }

            //Lay danh sach ma giao dich
            StringBuffer saleTransBuf = new StringBuffer();
            saleTransBuf.append("");
            for (int i = 0; i < saleTransArr.length; i++) {
                String temp = saleTransArr[i];
                if (temp != null && !temp.trim().equals("") && !temp.trim().equals("false")) {
                    if (!saleTransBuf.toString().equals("")) {
                        saleTransBuf.append(CHAR_SPLIT);
                    }
                    saleTransBuf.append(temp);
                }
            }
            if (saleTransBuf.toString().equals("")) {
                searchSaleTrans();
//                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi, chưa chọn giao dịch để lập hóa đơn!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.107");
                return pageForward;
            }

            form.setSaleTransList(saleTransBuf.toString());

            //Kiem tra dieu kien lap hoa don
            if (!validateCreateInvoice(req, saleTransArr, String.valueOf(form.getSaleGroup()))) {
                return pageForward;
            }
            //kiem tra neu khach hang co tin thi khong cho gop giao dich
            if (saleTransArr.length > 1) {
                if (checkTinInvoice()) {
                    form.setSaleTransList("");
                    searchSaleTrans();
                    req.setAttribute(Constant.RETURN_MESSAGE, "Không cho phép gộp giao dịch nếu khách hàng có mã số thuế!");
//                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.158");
                    return pageForward;
                }

            }

            //Kiem tra co cung loai giao dich hay khong?
            if (checkNotSameInvoiceType()) {
                form.setSaleTransList("");
                searchSaleTrans();
//                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi, các giao dịch không cùng nhóm hoá đơn!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.108");
                return pageForward;
            }

            //Kiem tra thong tin Cung ngay lap giao dich, cung loai dich vu, cung HTTT, cung loai VAT
            if (checkNotSameOtherFields(saleTransArr)) {
                form.setSaleTransList("");
                searchSaleTrans();
//                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi, các giao dịch không cùng thông tin lập hoá đơn!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.109");
                return pageForward;
            }

//            //Kiem tra voi nhung giao dich tru kho ngay da nhap Serial chua?
//            if (checkNotUpdateSerial()) {
//                form.setSaleTransList("");
//                searchSaleTrans();
//                req.setAttribute(Constant.RETURN_MESSAGE, "Chưa cập nhật số Serial của mặt hàng yêu cầu trừ kho ngay!");
//                return pageForward;
//            }

            //List of invoice serial -- lay hoa don cua cong ty
            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
            invoiceListDAO.setSession(getSession());
            List<InvoiceList> lstSerialNo = invoiceListDAO.findAllSerialInvoiceList(Constant.MOV_SHOP_ID, userToken.getUserID());
            //Check InvoiceList
            if (lstSerialNo == null || lstSerialNo.isEmpty()) {
                form.setSaleTransList("");
                searchSaleTrans();
//                req.setAttribute(Constant.RETURN_MESSAGE, "Hoá đơn rỗng! Không còn hoá đơn trong kho nhân viên.");
                //sonbc2: sua cau thong bao, hoa dong tu kho cong ty
//                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.110");
                req.setAttribute(Constant.RETURN_MESSAGE, "SaleToRetailDAO.013");
                return pageForward;
            }

            reqSession.setAttribute(LIST_SERIAL_NO, lstSerialNo);
            form.setSerialNo(lstSerialNo.get(0).getSerialNo());
            getInvoiceNo();

            List<SaleTransInvoiceBean> lstSaleTrans = getSaleTransList(form);
            //kiem tra neu khach hang co tin thi khong cho gop giao dich
//            if (saleTransArr.length > 1) {
//                if (checkTinStaffOrShop(lstSaleTrans)) {
//                    form.setSaleTransList("");
//                    searchSaleTrans();
////                req.setAttribute(Constant.RETURN_MESSAGE, "Không cho phép gộp giao dịch nếu khách hàng có mã số thuế!");
//                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.158");
//                    return pageForward;
//                }
//
//            }
            req.setAttribute(LIST_SALE_TRANS, lstSaleTrans);

            //Khoi tao form - thong tin lap hoa don
            form.CopyBeanInfo(lstSaleTrans.get(0));
            form.setShopName(userToken.getShopName());
            form.setStaffName(userToken.getStaffName());
            form.setCreateDate(DateTimeUtils.getSysdateForWeb());

            //Tinh tong tien hoa don            
            this.sum(form, lstSaleTrans);

            //Reason_2
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
            try {
                if (lstReason.size() == 1) {
                    ComboListBean comboListBean = (ComboListBean) lstReason.get(0);
                    form.setReasonId(comboListBean.getObjId().toString());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (Exception e) {
            throw e;
        }

        form.setCanSelect(SALE_TRANS_INVOICE_NOT_SELECT);//0
        pageForward = CREATE_INVOICE;
        return pageForward;
    }

    public String getInvoiceNo() throws Exception {
        String pageForward = CREATE_INVOICE_SERIAL_NO;
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        try {
            form.setFromInvoice("");
            form.setToInvoice("");
            form.setCurInvoice("");
            form.setInvoiceNo("");
            form.setInvoiceListId("");
            form.setInvoiceNoEdit("");

            form.setShopName(userToken.getShopName());
            form.setStaffName(userToken.getStaffName());
            form.setCreateDate(DateTimeUtils.getSysdate());

            String serialNo = (String) req.getParameter("serialNo");

            if ((null == serialNo) || ("".equals(serialNo.trim()))) {
                //Kiem tra truong hop lay mac dinh dai hoa don khi mo form
                if (form.getSerialNo() == null || form.getSerialNo().trim().equals("")) {
                    return pageForward;
                }
                serialNo = form.getSerialNo();
            }

            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
            invoiceListDAO.setSession(getSession());
            //List<CurrentInvoiceListBean> currentInvoiceListBean = invoiceListDAO.getCurrentInvoiceList(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF, userToken.getUserID(), userToken.getShopId(), null, serialNo.trim(), null);            
            List<CurrentInvoiceListBean> currentInvoiceListBean = invoiceListDAO.getCurrentInvoiceList(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP, null, userToken.getShopId(), null, serialNo.trim(), null);
            if ((null != currentInvoiceListBean) && (1 <= currentInvoiceListBean.size())) {
                form.setFromInvoice(currentInvoiceListBean.get(0).getFromInvoice());
                form.setToInvoice(currentInvoiceListBean.get(0).getToInvoice());
                form.setCurInvoice(currentInvoiceListBean.get(0).getCurrInvoiceNo());
                //modify by sonbc2: 29042016 : bo hien thi truong invoiceNo
//                form.setInvoiceNo(currentInvoiceListBean.get(0).getInvoiceNumber());
                //end modify by sonbc2
                form.setInvoiceListId(currentInvoiceListBean.get(0).getInvoiceListId().toString());

                //form.setInvoiceNoEdit(form.getInvoiceNo());
                form.setInvoiceNoEdit(form.getCurInvoice());
            }

            form.setSerialNo(serialNo);

        } catch (Exception e) {
            throw e;
        }

        return pageForward;
    }

    //Kiem tra dieu kien lap hoa don
    private boolean validateCreateInvoice(HttpServletRequest req, String[] saleTransIdList, String saleGroup) {
        boolean result = false;
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            String queryString = "";
            List parameterList = new ArrayList();
            //Kiem tra cac giao dich duoc chon
            for (int i = 0; i < saleTransIdList.length; i++) {
                String temp = saleTransIdList[i];
                if (temp != null && !temp.trim().equals("") && !temp.trim().equals("false")) {
                    if (!"".equals(queryString.trim())) {
                        queryString += " or ";
                    }
                    queryString += " st.sale_trans_id = ? ";
                    parameterList.add(new Long(temp));
                }
            }
            //Khong co giao dich nao duoc chon
            if ("".equals(queryString.trim())) {
                form.setSaleTransList("");
                searchSaleTrans();
//                req.setAttribute(Constant.RETURN_MESSAGE, "Bạn chưa chọn giao dịch nào!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.111");
                return result;
            }
            //kiem tra so luong ban ghi trong detail co vuot qua quy dinh ko
            ResourceBundle resource = ResourceBundle.getBundle("config");
            queryString = "  SELECT   distinct(std.stock_model_id)  FROM   sale_trans st, sale_trans_detail std WHERE 1 = 1 "
                    + "AND st.sale_trans_id = std.sale_trans_id AND  (" + queryString + " ) ";
            Query query = getSession().createSQLQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                query.setParameter(i, parameterList.get(i));
            }
            List lstStockmodel = query.list();
            if (lstStockmodel != null && lstStockmodel.size() > 0 && lstStockmodel.size() > Integer.parseInt(resource.getString("MAX_OF_GOODS"))) {
                searchSaleTrans();
                req.setAttribute(Constant.RETURN_MESSAGE, "sale.warn.Exceeds");
                List listMessageParam = new ArrayList();
                listMessageParam.add(resource.getString("MAX_OF_GOODS"));
                req.setAttribute("returnMsgValue", listMessageParam);
                return result;

            }
            //gan lai bien queryString
            queryString = "";
            //Kiem tra cac giao dich duoc chon
            for (int i = 0; i < saleTransIdList.length; i++) {
                String temp = saleTransIdList[i];
                if (temp != null && !temp.trim().equals("") && !temp.trim().equals("false")) {
                    if (!"".equals(queryString.trim())) {
                        queryString += " or ";
                    }
                    queryString += " st.sale_trans_id = ? ";
                    //parameterList.add(new Long(temp));
                }
            }
            if (saleGroup.equals(Constant.SALE_GROUP_COL_RETAIL)) {     //Lap hoa don thay CTV
                //Kiem tra cung CTV
                queryString = " ( select distinct st.shop_id, st.staff_id from sale_trans st where " + queryString + " ) ";
                Query queryObject = getSession().createSQLQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                //Khong cung CTV
                List lstTemp = queryObject.list();
                if (lstTemp == null || lstTemp.size() == 0) {
                    form.setSaleTransList("");
                    searchSaleTrans();
//                    req.setAttribute(Constant.RETURN_MESSAGE, "Bạn chưa chọn giao dịch nào!");
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.111");
                    return result;
                }

                /**
                 *
                 * CHECK LAP HOA DON CHO CUNG 1 DOI TUONG TREN GIAO DIEN DA
                 * KHONG CHO CHON NHIEU LOAI DOI TUONG ROI Ngay 20/05/2010: CODE
                 * KHONG CAN CHECK NUA 27/05/2010: su dung lai
                 */
//                if (lstTemp.size()>1){
//                    form.setSaleTransList("");
//                    searchSaleTrans();
//                    req.setAttribute(Constant.RETURN_MESSAGE, "Bạn phải chọn các giao dịch của cùng CTV ĐDV!");
//                    return result;
//                }
                Boolean forShop = (Boolean) reqSession.getAttribute(CREATE_INVOICE_FOR_SHOP);
                Boolean forStaff = (Boolean) reqSession.getAttribute(CREATE_INVOICE_FOR_STAFF);
                if (forShop == null) {
                    forShop = false;
                }
                if (forStaff == null) {
                    forStaff = false;
                }

                Object[] collInfo = (Object[]) lstTemp.get(0);
                if (collInfo != null && collInfo.length == 2) {
                    Object collId = collInfo[0];
                    String collType = Constant.OBJECT_TYPE_SHOP;
                    if (collInfo[1] != null && !collInfo[1].toString().trim().equals("") && !collInfo[1].toString().trim().equals("0")) {
                        collId = collInfo[1];
                        collType = Constant.OBJECT_TYPE_STAFF;
                    }

                    if (collId != null && !collId.toString().trim().equals("")) {
                        if (collType.equals(Constant.OBJECT_TYPE_STAFF)) {
                            if (!forStaff) {
//                                req.setAttribute(Constant.RETURN_MESSAGE, "Bạn không có quyền lập hoá đơn cho CTV/ĐB!");
                                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.112");
                                return result;
                            }

                            StaffDAO staffDAO = new StaffDAO();
                            staffDAO.setSession(this.getSession());
                            Staff staff = staffDAO.findById(Long.valueOf(collId.toString().trim()));
                            if (staff != null && !staff.getStaffOwnerId().toString().equals(userToken.getUserID().toString())) {
                                form.setSaleTransList("");
                                searchSaleTrans();
//                                req.setAttribute(Constant.RETURN_MESSAGE, "CTV không thuộc nhân viên quản lý!");
                                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.113");
                                return result;
                            }
                        } else {
                            if (!forShop) {
//                                req.setAttribute(Constant.RETURN_MESSAGE, "Bạn không có quyền lập hoá đơn cho Đại lý!");
                                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.114");
                                return result;
                            }

                            ShopDAO staffDAO = new ShopDAO();
                            staffDAO.setSession(this.getSession());
                            Shop staff = staffDAO.findById(Long.valueOf(collId.toString().trim()));
                            if (staff != null && !staff.getParentShopId().toString().equals(userToken.getShopId().toString())) {
                                form.setSaleTransList("");
                                searchSaleTrans();
//                                req.setAttribute(Constant.RETURN_MESSAGE, "Đại lý không thuộc đơn vị quản lý!");
                                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.115");
                                return result;
                            }
                        }
                    }
                }
            } else if (saleGroup.equals(Constant.SALE_GROUP_WHOS)) {    //Lap hoa don cho dai ly
                //Kiem tra cung Dai ly
                queryString = "SELECT distinct to_owner_id, to_owner_type "
                        + "  FROM stock_trans "
                        + " WHERE stock_trans_id IN "
                        + " ( select stock_trans_id from sale_trans st where " + queryString + " ) ";
                Query queryObject = getSession().createSQLQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                //Khong cung Dai ly
                List lstTemp = queryObject.list();
                if (lstTemp == null || lstTemp.size() == 0) {
                    form.setSaleTransList("");
                    searchSaleTrans();
//                    req.setAttribute(Constant.RETURN_MESSAGE, "Bạn chưa chọn giao dịch nào!");
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.111");
                    return result;
                }
                if (lstTemp.size() > 1) {
                    form.setSaleTransList("");
                    searchSaleTrans();
//                    req.setAttribute(Constant.RETURN_MESSAGE, "Bạn phải chọn các giao dịch của cùng Đại lý!");
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.116");
                    return result;
                }
            }

            result = !result;
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return result;
        }
    }

    private boolean checkNotSameOtherFields(String[] saleTransIdTemp) {
        boolean result = true; // true = not same

        String queryString = "";
        List parameterList = new ArrayList();
        for (int i = 0; i < saleTransIdTemp.length; i++) {
            String temp = saleTransIdTemp[i];
            if (temp != null && !temp.trim().equals("") && !temp.trim().equals("false")) {
                if (!"".equals(queryString.trim())) {
                    queryString += " or ";
                }
                queryString += " sale_trans_id = ? ";
                parameterList.add(new Long(temp));
            }
        }
        //Khong co giao dich nao duoc chon
        if ("".equals(queryString.trim())) {
            return result;
        }


        //Dung de su dung lai
        String saleTransList = queryString;

        queryString = "SELECT DISTINCT trunc(sale_trans_date), telecom_service_id, pay_method, vat, currency FROM sale_trans WHERE " + queryString;
        queryString += " GROUP BY trunc(sale_trans_date), telecom_service_id, pay_method, vat, currency ";
        Query queryObject = getSession().createSQLQuery(queryString);
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }

        //Cung cac thong tin lap hoa don -> chi co 1 ban ghi
        List lstTemp = queryObject.list();
        if (lstTemp == null || lstTemp.size() <= 1) {
            result = false;
        }

        //Lap hoa don thay
        //Kiem tra so ngay cac giao dich duoc lap chung hoa don
//        if (result && (form.getSaleGroup().compareTo(Long.valueOf(Constant.SALE_GROUP_COL_RETAIL)) == 0) ){
//            queryString = " SELECT (a.dis - b.VALUE) ";
//            queryString  += "   FROM (SELECT   MAX (TRUNC (sale_trans_date)) ";
//            queryString  += "                - MIN (TRUNC (sale_trans_date)) AS dis ";
//            queryString  += "           FROM sale_trans ";
//            queryString  += "          WHERE 1 = 1 ";
//
//            queryString += " and " + saleTransList;
//
//            queryString  += " ) a, ";
//            queryString  += " app_params b ";
//            queryString  += "  WHERE b.TYPE = 'INVOICE_USED_DATE_DIS' and b.code = 10 ";
//
//            //Neu chenh lech ngay trong gioi han cho phep lap chung hoa don
//            lstTemp = queryObject.list();
//            if (lstTemp != null && lstTemp.size()>=1){
//                Integer num = Integer.valueOf(lstTemp.get(0).toString());
//                if (num != null && num < 0){
//                    result = false;
//                }
//            }
//        }


        return result;
    }

    private boolean checkNotSameInvoiceType() {
        boolean result = true;

        //Lay danh sach ma giao dich
        String[] saleTransIdTemp = form.getSaleTransIdList();

        //Khong co giao dich nao duoc chon
        if (!(saleTransIdTemp != null && saleTransIdTemp.length != 0)) {
            return result;
        }

        String queryString = "";
        List parameterList = new ArrayList();
        for (int i = 0; i < saleTransIdTemp.length; i++) {
            String temp = saleTransIdTemp[i];
            if (temp != null && !temp.trim().equals("") && !temp.trim().equals("false")) {
                if (!"".equals(queryString.trim())) {
                    queryString += " or ";
                }
                queryString += " st.sale_trans_id = ? ";
                parameterList.add(new Long(temp));
            }
        }
        //Khong co giao dich nao duoc chon
        if ("".equals(queryString.trim())) {
            return result;
        }

        queryString = " ( select st.sale_trans_type from sale_trans st where " + queryString + " ) ";
        queryString = " select distinct INVOICE_GROUP From sale_invoice_type where SALE_TRANS_TYPE in  " + queryString;
        Query queryObject = getSession().createSQLQuery(queryString);
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }

        //Khong cung loai hoa don
        List lstTemp = queryObject.list();
        if (lstTemp == null || lstTemp.size() <= 1) {
            result = false;
        }
        return result;
    }

    /**
     * save invoice used for sale transactions selected
     *
     * @return
     * @throws Exception
     */
    public String createInvoice() throws Exception {
        String pageForward = CREATE_INVOICE;

        getReqSession();
        Session mySession = this.getSession();

        try {

            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            //Danh sach rong
            List<SaleTransInvoiceBean> lstSaleTrans = getSaleTransList(form);
            req.setAttribute(LIST_SALE_TRANS, lstSaleTrans);

            //Kiem tra thong tin hoa don
            String returnMsg = validateCreateInvoice(form);
            if (!"".equals(returnMsg.trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, returnMsg);
                return pageForward;
            }

            if (lstSaleTrans == null || lstSaleTrans.isEmpty()) {
                return pageForward;
            }

            //modify by sonbc2: cho phep kiem tra so luong dong
            if (checkLimitInvoiceDetail(lstSaleTrans)) {
                return pageForward;
            }
            //end modify by sonbc2
            //Kiem tra co giao dich nao khong phai o trang thai chua lap hoa don hay khong?
            if (!checkSaleTransStatusNotBill(lstSaleTrans, userToken)) {
                return pageForward;
            }

            //Kiem tra so luong mat hang lon hon so luong cho phep
            if (checkLimitInvoiceDetail(lstSaleTrans)) {
                return pageForward;
            }

            //Tinh lai tong tien
            this.sum(form, lstSaleTrans);

            //Thuc hien insert hoa don
            Long invoiceUsedId = saveInvoiceUsed(form, lstSaleTrans, mySession);
            if (null == invoiceUsedId) {
                mySession.clear();
                mySession.getTransaction().rollback();
                mySession.beginTransaction();
                return pageForward;
            }

            //Lap hoa don thay CTV
            //Cong lai tien khi lap hoa don
            //TrongLV_111214_KHONG CONG LAI TIEN NUA 
            String saleTransType = "";
            if (form.getSaleGroup().compareTo(Long.valueOf(Constant.SALE_GROUP_COL_RETAIL)) == 0) { //Kiem tra loai giao dich String saleTransType = ""; 

                /* 120807 : TrongLV : check chi co giao dich ban phat thi khong cong lai tien vao tktt */
                /* vi vay khong lap chung giao dich ban phat vao cac giao dich khac */
                for (SaleTransInvoiceBean bean : lstSaleTrans) {
                    if (!saleTransType.equals("") && !DataUtils.safeEqual(bean.getSaleTransType(), saleTransType)) {
                        if (DataUtils.safeEqual(bean.getSaleTransType(), Constant.SALE_TRANS_TYPE_PUNISH) || DataUtils.safeEqual(saleTransType, Constant.SALE_TRANS_TYPE_PUNISH)) {
                            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Không được lập hoá đơn chung loại giao dịch bán phạt với các loại giao dịch khác");
                            mySession.clear();
                            mySession.getTransaction().rollback();
                            mySession.beginTransaction();
                            return pageForward;
                        }
                    }
                    saleTransType = bean.getSaleTransType();
                }

                try {
                    if (!saleTransType.equals(Constant.SALE_TRANS_TYPE_PUNISH)) {
                        returnMsg = "";
                        String sendMess = "";
                        SaleTransInvoiceBean st = (SaleTransInvoiceBean) lstSaleTrans.get(0);
                        Double amount = form.getAmountTax();
                        sendMess = MessageFormat.format(getText("sms.2004"), NumberUtils.rounđAndFormatAmount(amount));
                        //"Lap hoa don thanh cong. Tai khoan thanh toan cua ban duoc cong lai " + WsCommonDAO.formatNumber(amount) + " dong";
                        System.out.println("sendSMS : " + sendMess);

                        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
                        returnMsg = accountAgentDAO.addBalance(mySession,
                                st.getStaffId(), Constant.OWNER_TYPE_STAFF,
                                Constant.DEPOSIT_TRANS_TYPE_INVOICE, amount, getSysdate(),
                                invoiceUsedId, userToken.getLoginName());

                        if (!returnMsg.equals("")) {
                            mySession.clear();
                            mySession.getTransaction().rollback();
                            mySession.beginTransaction();
                            req.setAttribute(Constant.RETURN_MESSAGE, returnMsg);
                            return pageForward;
                        }
//                        SmsClient.sendSMS155("982289145", sendMess);
                    } //Neu la giao dich CTV lam dich vu //Khong xu ly
                } catch (Exception ex) {
                    mySession.clear();
                    mySession.getTransaction().rollback();
                    mySession.beginTransaction();
                    ex.printStackTrace();
                    req.setAttribute(Constant.RETURN_MESSAGE, ex.getMessage());
                    return pageForward;
                }
            }

            //Neu tao hoa don thanh cong => lay lai danh sach giao dich
            lstSaleTrans = getSaleTransList(form);
            req.setAttribute(LIST_SALE_TRANS, lstSaleTrans);

            //KET THUC SESSION            
            form.setCurInvoice("");
            mySession.flush();
            //add sonbc2:28042016
            req.setAttribute("print", 1);
            //end sonbc2

            form.setInvoiceUsedId(invoiceUsedId.toString());

//            req.setAttribute(Constant.RETURN_MESSAGE, "Lập HĐ thành công!");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.117");
        } catch (Exception ex) {
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();

            form.setFromInvoice("");
            form.setToInvoice("");
            form.setCurInvoice("");
            form.setInvoiceNo("");
            form.setInvoiceListId("");
            form.setInvoiceNoEdit("");
            form.setSerialNo("");

//            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi lập hoá đơn bán hàng!");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.118");
        }
        return pageForward;
    }

    private String validateCreateInvoice(SaleTransInvoiceForm f) {
        try {
            String returnMsg = "";
            form.setCreateDate(DateTimeUtils.getSysdate());

            //invoiceNumber
//            if (null == f.getCurInvoice() || "".equals(f.getCurInvoice().trim())) {
//                form.setFromInvoice("");
//                form.setToInvoice("");
//                form.setCurInvoice("");
//                form.setInvoiceNo("");
//                form.setInvoiceListId("");
//                form.setInvoiceNoEdit("");
////                returnMsg = "Chưa chọn hoá đơn!";
//                returnMsg = "ERR.SAE.119";
//                return returnMsg;
//            }
            if (null == f.getSerialNo() || "".equals(f.getSerialNo().trim())) {
                form.setFromInvoice("");
                form.setToInvoice("");
                form.setCurInvoice("");
                form.setInvoiceNo("");
                form.setInvoiceListId("");
                form.setInvoiceNoEdit("");
//                returnMsg = "Chưa chọn hoá đơn!";
                returnMsg = "ERR.SAE.119";
                return returnMsg;
            }
            if (null == f.getInvoiceListId() || "".equals(f.getInvoiceListId().trim())) {
                form.setFromInvoice("");
                form.setToInvoice("");
                form.setCurInvoice("");
                form.setInvoiceNo("");
                form.setInvoiceListId("");
                form.setInvoiceNoEdit("");
//                returnMsg = "Chưa chọn hoá đơn!";
                returnMsg = "ERR.SAE.119";
                return returnMsg;
            }

            //objName
            if (null == f.getObjName() || "".equals(f.getObjName().trim())) {
//                returnMsg = "Chưa có tên khách hàng!";
                returnMsg = "ERR.SAE.120";
                return returnMsg;
            }
            //reasonID
            if (null == f.getReasonId() || "".equals(f.getReasonId().trim())) {
//                returnMsg = "Chưa chọn lý do lập hoá đơn!";
                returnMsg = "ERR.SAE.121";
                return returnMsg;
            }
            //payMethodId
            if (null == f.getPayMethodId() || "".equals(f.getPayMethodId().trim())) {
//                returnMsg = "Chưa chọn hình thức thanh toán!";
                returnMsg = "ERR.SAE.122";
                return returnMsg;
            }

            return returnMsg;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private boolean checkLimitInvoiceDetail(List<SaleTransInvoiceBean> lstSaleTrans) {
        boolean result = true;

        String serialNo = form.getSerialNo();
        if (null == serialNo || "".equals(serialNo.trim())) {
            return result;
        }
        BookTypeDAO bookTypeDAO = new BookTypeDAO();
        int rowCount = 0;
        SaleTransDetailDAO saleTransDetailDAO = new SaleTransDetailDAO();
        saleTransDetailDAO.setSession(getSession());
        List<BookType> listBookType = bookTypeDAO.findByProperty("serialCode", serialNo);
        if (null == listBookType || 0 == listBookType.size()) {
            req.setAttribute("returnMsg", "!!! Ký hiệu hoá đơn không tồn tại");
            return result;
        }
        if (listBookType.get(0).getMaxRow() == null) {
            req.setAttribute("returnMsg", "!!! Chi tiết hoá đơn vượt quá số dòng tối đa của hoá đơn");
            return result;
        }

        Long maxRow = listBookType.get(0).getMaxRow();

        Long[] lstSaleTransId = new Long[lstSaleTrans.size()];
        Long isFineTrans = 0L;
        int index = 0;
        boolean isExtraRow = false;
        for (SaleTransInvoiceBean tmp : lstSaleTrans) {
            isFineTrans = tmp.getIsFineTrans();
            lstSaleTransId[index++] = tmp.getSaleTransId();
            if (!isExtraRow) {
                if (tmp.getDiscount() != null && tmp.getDiscount().intValue() > 0) {
                    isExtraRow = true;
                } else if (tmp.getPromotion() != null && tmp.getPromotion().intValue() > 0) {
                    isExtraRow = true;
                }
            }
        }

        List<SaleTransDetailBean> listSaleTransDetailBean = saleTransDetailDAO.getSaleTransDetailList(lstSaleTransId, isFineTrans, lstSaleTrans.get(0).getSaleTransDate());
        rowCount = listSaleTransDetailBean.size();

        if (isExtraRow) {
            rowCount++;
        }

        if (rowCount > maxRow.intValue()) {
            req.setAttribute("returnMsg", "saleInvoice.error.invalidInvoice");
            List listParamValue = new ArrayList();
            listParamValue.add(rowCount);
            listParamValue.add(serialNo);
            listParamValue.add(maxRow);
            req.setAttribute("returnMsgValue", listParamValue);
            return result;
        }

        result = false;
        return result;
    }

    private boolean checkSaleTransStatusNotBill(List<SaleTransInvoiceBean> lstSaleTrans, UserToken userToken) {
        boolean result = false;
        try {
            List listParamValue = new ArrayList();
            StringBuffer queryString = new StringBuffer();
            queryString.append("SELECT * FROM SALE_TRANS WHERE shop_id = ? and staff_id = ? and status = ? and sale_trans_id in ");
            StringBuffer sqlSaleTransId = new StringBuffer();
            sqlSaleTransId.append("");
            if (form.getSaleGroup() != null && form.getSaleGroup().toString().equals(Constant.SALE_GROUP_COL_RETAIL)) {

//                if (lstSaleTrans.get(0).getStaffId() != null){
//                    queryString = new StringBuffer();
//                    queryString.append("SELECT * FROM SALE_TRANS WHERE shop_id = ? and staff_id = ? and status = ? and sale_trans_id in ");
//                    listParamValue.add(lstSaleTrans.get(0).getShopId());
//                    listParamValue.add(lstSaleTrans.get(0).getStaffId());
//                }
//                else{
//                    queryString = new StringBuffer();
//                    queryString.append("SELECT * FROM SALE_TRANS WHERE shop_id = ? and staff_id is null and status = ? and sale_trans_id in ");
//                    listParamValue.add(lstSaleTrans.get(0).getShopId());
//                }

                queryString = new StringBuffer();
                queryString.append("SELECT * FROM SALE_TRANS WHERE 1=1 and status = ? and sale_trans_id in ");

            } else {
                listParamValue.add(userToken.getShopId());
                listParamValue.add(userToken.getUserID());
            }

            listParamValue.add(Constant.SALE_TRANS_STATUS_NOT_BILLED);
            for (SaleTransInvoiceBean tmp : lstSaleTrans) {
                if (sqlSaleTransId.length() > 0) {
                    sqlSaleTransId.append(",");
                }
                sqlSaleTransId.append(Long.valueOf(tmp.getSaleTransId()));
            }
            queryString.append("(").append(sqlSaleTransId).append(")");

            Query queryObject = getSession().createSQLQuery(queryString.toString());
            for (int i = 0; i < listParamValue.size(); i++) {
                queryObject.setParameter(i, listParamValue.get(i));
            }

            List lstTemp = queryObject.list();
            if (lstTemp != null && lstTemp.size() == lstSaleTrans.size()) {
                result = true;
            }
            if (!result) {
                req.setAttribute("returnMsg", "Có giao dịch đã được lập hoá đơn. Đề nghị kiểm tra lại danh sách giao dịch!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("returnMsg", "Có lỗi xảy ra khi kiểm tra trạng thái chưa lập hoá đơn của giao dịch!");
        }
        return result;
    }

    //CREATE INVOICE_USED
    private Long saveInvoiceUsed(SaleTransInvoiceForm f, List<SaleTransInvoiceBean> l, Session mySession) {
        try {

            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            //Check invoice_number of staff
            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
            invoiceListDAO.setSession(mySession);
            //List<CurrentInvoiceListBean> invoiceList = invoiceListDAO.getCurrentInvoiceList(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF, Long.valueOf(f.getStaffId()), Long.valueOf(f.getShopId()), Long.valueOf(f.getInvoiceListId()), f.getSerialNo(), Long.valueOf(f.getCurInvoice()));
            List<CurrentInvoiceListBean> invoiceList = invoiceListDAO.getCurrentInvoiceList(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP, null, Long.valueOf(f.getShopId()), Long.valueOf(f.getInvoiceListId()), f.getSerialNo(), null);
            if (invoiceList == null
                    || invoiceList.isEmpty()
                    //|| !invoiceList.get(0).getCurrInvoiceNo().trim().equals(f.getCurInvoice().trim())
                    || !checkCurInvoiceNo(invoiceList.get(0))) {
                form.setFromInvoice("");
                form.setToInvoice("");
                form.setCurInvoice("");
                form.setInvoiceNo("");
                form.setInvoiceListId("");
                form.setInvoiceNoEdit("");
                form.setSerialNo("");
                req.setAttribute(Constant.RETURN_MESSAGE, "Error. Current invoice no already used for other sale transaction!");
                return null;
            }
            //lock record
            InvoiceList ilTemp = invoiceListDAO.findById(invoiceList.get(0).getInvoiceListId());
            mySession.refresh(ilTemp, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

            //Insert bang invoice_used
            InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
            invoiceUsedDAO.setSession(getSession());
            InvoiceUsed invoiceUsed = new InvoiceUsed();
            Long invoiceUsedId = getSequence("INVOICE_USED_SEQ");
            invoiceUsed.setInvoiceUsedId(invoiceUsedId);
            invoiceUsed.setInvoiceListId(Long.valueOf(f.getInvoiceListId()));
//            invoiceUsed.setType(1L);
            invoiceUsed.setType(f.getSaleGroup());
            invoiceUsed.setShopId(Long.valueOf(f.getShopId()));
            invoiceUsed.setStaffId(Long.valueOf(f.getStaffId()));

            invoiceUsed.setSerialNo(invoiceList.get(0).getSerialNo());
            invoiceUsed.setBlockNo(invoiceList.get(0).getBlockNo());
            invoiceUsed.setInvoiceId(Long.valueOf(invoiceList.get(0).getCurrInvoiceNo()));
            invoiceUsed.setInvoiceNo(invoiceList.get(0).getInvoiceNumber());

            form.setInvoiceNo(invoiceUsed.getInvoiceNo());
            form.setInvoiceNoEdit(invoiceList.get(0).getCurrInvoiceNo());

            form.setInvoiceListId(invoiceList.get(0).getInvoiceListId().toString());

            //custommer
            invoiceUsed.setCustName(f.getObjName().trim());
            invoiceUsed.setAddress(f.getObjAddress().trim());
            invoiceUsed.setCompany(f.getObjCompany().trim());
            //sonbc2
            String tin = f.getObjTin().trim();
            if (tin == null || tin.length() == 0) {
                if (l != null && l.size() > 0) {
                    if (l.get(0).getReceiverId() != null && l.get(0).getReceiverType() != null
                            && !l.get(0).getReceiverId().equals("") && !l.get(0).getReceiverType().equals("")) {
                        String queryString = "";
                        if (l.get(0).getReceiverType().compareTo(Constant.RECEIVER_TYPE_SHOP) == 0) {
                            queryString = "SELECT tin FROM shop WHERE tin IS NOT NULL AND shop_id=?";

                        } else {
                            queryString = "SELECT tin FROM staff WHERE tin IS NOT NULL AND staff_id=?";
                        }
                        Query queryObject = getSession().createSQLQuery(queryString);
                        queryObject.setParameter(0, l.get(0).getReceiverId());
                        List lstTemp = queryObject.list();
                        if (lstTemp != null && lstTemp.size() > 0) {
                            tin = lstTemp.get(0).toString();
                        }
                    }
                }
            }
            invoiceUsed.setTin(tin);
            invoiceUsed.setPayMethod(f.getPayMethodId());
            invoiceUsed.setNote(f.getNote().trim());
            if (f.getReasonId() != null || !f.getReasonId().trim().equals("")) {
                invoiceUsed.setReasonId(Long.valueOf(f.getReasonId().trim()));
            }

            invoiceUsed.setAmount(f.getAmountNotTax());
            invoiceUsed.setTax(f.getTax());
            invoiceUsed.setAmountTax(f.getAmountTax());
            invoiceUsed.setDiscount(f.getDiscount());
            invoiceUsed.setPromotion(f.getPromotion());

            //VAT & Currency
            if (l != null && l.size() > 0) {
                if (l.get(0).getVat() != null) {
                    invoiceUsed.setVat(l.get(0).getVat());
                } else {
                    req.setAttribute(Constant.RETURN_MESSAGE, "Error. VAT rate must be not null!");
                    return null;
                }
                invoiceUsed.setInvoiceDate(l.get(0).getSaleTransDate());
                if (l.get(0).getTelecomServiceId() != null && !l.get(0).getTelecomServiceId().trim().equals("")) {
                    invoiceUsed.setTelecomServiceId(Long.valueOf(l.get(0).getTelecomServiceId()));
                } else {
                    req.setAttribute(Constant.RETURN_MESSAGE, "Error. Telecom service must be not null!");
                    return null;
                }

                if (l.get(0).getCurrency() != null && !l.get(0).getCurrency().trim().equals("")) {
                    invoiceUsed.setCurrency(l.get(0).getCurrency());
                } else {
                    req.setAttribute(Constant.RETURN_MESSAGE, "Error. Currency must be not null!");
                    return null;
                }

            } else {
                req.setAttribute(Constant.RETURN_MESSAGE, "Error. List of sale transaction must be not empty!");
                return null;
            }

            invoiceUsed.setStatus(Constant.STATUS_USE.toString());
            invoiceUsed.setCreateDate(getSysdate());

            invoiceUsed.setAccount(f.getObjAccount());
            mySession.save(invoiceUsed);

            //update invoiceUsed of saleTrans
            if (!updateSaleTrans(invoiceUsedId, l, mySession)) {
                mySession.clear();
                mySession.getTransaction().rollback();
                mySession.beginTransaction();

                req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not update status of sale transactions to status created invoice!");
                return null;
            }
            //Neu la lap hoa don cho Dai Ly thi tao luon phieu xuat
            if (form.getSaleGroup() != null && form.getSaleGroup().compareTo(Long.valueOf(Constant.SALE_GROUP_WHOS)) == 0) {
                if (!createNoteExportStock(l, mySession, userToken)) {
                    mySession.clear();
                    mySession.getTransaction().rollback();
                    mySession.beginTransaction();

                    req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not update status of sale transactions Because create Note export stock fail!");
                    return null;
                }
            }

            //update status of invoiceList
//            if (!updateInvoiceList(invoiceList.get(0).getInvoiceListId(), f.getStaffId(), mySession)) {
//                mySession.clear();
//                mySession.getTransaction().rollback();
//                mySession.beginTransaction();
//
//                req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not update current invoice no for invoice range!");
//                return null;
//            }

            if (!updateInvoiceList(ilTemp, mySession)) {
                mySession.clear();
                mySession.getTransaction().rollback();
                mySession.beginTransaction();

                req.setAttribute(Constant.RETURN_MESSAGE, "SaleToCreateInvoiceDAO.error.010");
                return null;
            }
//end sonbc2

            //Insert Invoice_Coupon
            if (Constant.IS_VER_HAITI) {
                try {
                    InvoiceCoupon invoiceCoupon = new InvoiceCoupon();
                    invoiceCoupon.setInvoiceCouponId(getSequence("INVOICE_COUPON_SEQ"));
                    invoiceCoupon.setInvoiceDate(invoiceUsed.getInvoiceDate());
                    invoiceCoupon.setInvoiceNumber(invoiceUsed.getInvoiceId());
                    invoiceCoupon.setInvoiceUsedId(invoiceUsed.getInvoiceUsedId());
                    invoiceCoupon.setSerialNo(invoiceUsed.getSerialNo());
                    invoiceCoupon.setLastUpdate(getSysdate());
                    invoiceCoupon.setOwnerId(invoiceUsed.getStaffId());
                    invoiceCoupon.setOwnerType(Constant.OWNER_TYPE_STAFF);
                    invoiceCoupon.setStatus(Constant.INVOICE_COUPON_STATUS_USE);
                    invoiceCoupon.setUserName(userToken.getLoginName());
                    invoiceCoupon.setLastUpdateKey(2L);//Thuc hien tu web
                    mySession.save(invoiceCoupon);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    req.setAttribute(Constant.RETURN_MESSAGE, ex.getMessage());
                    mySession.clear();
                    mySession.getTransaction().rollback();
                    mySession.beginTransaction();
                    return null;
                }
            }

            return invoiceUsedId;
        } catch (Exception ex) {
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();

            req.setAttribute(Constant.RETURN_MESSAGE, "System error!");
            return null;
        }
    }

    //Kiem tra so hoa don hien tai tren he thong da duoc lap hoa don hay chua?
    //invoiceId co o trong bang invoice_used hay khong?
    private boolean checkCurInvoiceNo(CurrentInvoiceListBean invoiceList) {
        boolean result = false;
        try {
            String queryString = "from InvoiceUsed where invoiceListId = ?"
                    + " and invoiceId= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, invoiceList.getInvoiceListId());
            queryObject.setParameter(1, Long.valueOf(invoiceList.getCurrInvoiceNo()));
            List lst = queryObject.list();
            if (lst == null || lst.isEmpty()) {
                result = !result;
                return result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private void sum(SaleTransInvoiceForm f, List<SaleTransInvoiceBean> l) {


        f.setAmountTax(0.0);
        f.setTax(0.0);
        f.setAmountNotTax(0.0);
        f.setDiscount(0.0);
        f.setPromotion(0.0);

        f.setAmountTaxMoney("");
        f.setTaxMoney("");
        f.setAmountNotTaxMoney("");
        f.setDiscountMoney("");
        f.setPromotionMoney("");


        Double amountTax = 0.0;
        Double tax = 0.0;
        Double amountNotTax = 0.0;
        Double discount = 0.0;
        Double promotion = 0.0;

        if (Constant.PRICE_AFTER_VAT) {//Neu la gia sau thue
            for (SaleTransInvoiceBean tmp : l) {
                if (null != tmp.getAmountTax()) {
                    amountTax += tmp.getAmountTax();
                }
                if (null != tmp.getAmountNotTax()) {
                    amountNotTax += tmp.getAmountNotTax();
                }
                if (null != tmp.getDiscount()) {
                    discount += tmp.getDiscount();
                }
                if (null != tmp.getPromotion()) {
                    promotion += tmp.getPromotion();
                }
            }
            tax = amountTax - amountNotTax;
        } else {//Neu la gia truoc thue
            for (SaleTransInvoiceBean tmp : l) {
                if (null != tmp.getAmountTax()) {
                    tax += tmp.getTax();
                }
                if (null != tmp.getAmountNotTax()) {
                    amountNotTax += tmp.getAmountNotTax();
                }
                if (null != tmp.getDiscount()) {
                    discount += tmp.getDiscount();
                }
                if (null != tmp.getPromotion()) {
                    promotion += tmp.getPromotion();
                }
            }
            amountTax = amountNotTax + tax;
        }

        f.setAmountTax(amountTax);
        f.setTax(tax);
        f.setAmountNotTax(amountNotTax);
        f.setDiscount(discount);
        f.setPromotion(promotion);

        f.setAmountTaxMoney(NumberUtils.rounđAndFormatAmount(amountTax));
        f.setTaxMoney(NumberUtils.rounđAndFormatAmount(tax));
        f.setAmountNotTaxMoney(NumberUtils.rounđAndFormatAmount(amountNotTax));
        f.setDiscountMoney(NumberUtils.rounđAndFormatAmount(discount));
        f.setPromotionMoney(NumberUtils.rounđAndFormatAmount(promotion));
    }

    //UPDATE INVOICE_USED_ID FOR TBL_SALE_TRANS
    private boolean updateSaleTrans(Long invoiceUsedId, List<SaleTransInvoiceBean> l, Session mySession) {
        boolean result = false;
        try {
            List lstParam = new ArrayList();
            lstParam.add(invoiceUsedId);
            lstParam.add(Constant.SALE_TRANS_STATUS_BILLED);

            StringBuffer buf1 = new StringBuffer("");
            ;
            for (SaleTransInvoiceBean tmp : l) {
                if (!buf1.toString().equals("")) {
                    buf1.append(" or ");
                }
                buf1.append(" sale_trans_id = ? ");
                lstParam.add(tmp.getSaleTransId());
            }
            if (buf1.toString().equals("")) {
                return result;
            }
            StringBuffer buf2 = new StringBuffer("update sale_trans set invoice_used_id = ? , invoice_create_date = sysdate, status = ? where 1=1 ");
            buf2.append(" and ( ");
            buf2.append(buf1);
            buf2.append(" ) ");
            buf2.append(" and status = ? ");
            lstParam.add(Constant.SALE_TRANS_STATUS_NOT_BILLED);

            Query query = mySession.createSQLQuery(buf2.toString());

            for (int idx = 0; idx < lstParam.size(); idx++) {
                query.setParameter(idx, lstParam.get(idx));
            }

            int numSaleTrans = query.executeUpdate();
            if (numSaleTrans > 0 && numSaleTrans == l.size()) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();
        }

        return result;
    }
    
    //Tao phieu xuat cho giao dich ban hang cho dai ly
    private boolean createNoteExportStock(List<SaleTransInvoiceBean> l, Session mySession, UserToken userToken) {
        boolean result = false;
        int count = 0;
        try {
            for (int i = 0; i < l.size(); i++) {
                SaleTransInvoiceBean saleTransInvoice = l.get(i);
                if (saleTransInvoice.getStockTransId() == null) {
                    return false;
                } else {
                    StockTransDAO stockTransDAO = new StockTransDAO();
                    StockTrans stockTran =  stockTransDAO.findById(saleTransInvoice.getStockTransId());
                    if (stockTran.getStockTransStatus() != null 
                            && stockTran.getStockTransStatus().equals(Constant.TRANS_NON_NOTE)) {
                        stockTran.setStockTransStatus(Constant.TRANS_NOTED); //giao dich da lap phieu xuat
                        mySession.update(stockTran);
                        
                        //Tao phieu xuat tu phieu nhap
                        StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
                        StockTransAction stockTransAction =  (StockTransAction)stockTransActionDAO.findByStockTransId(stockTran.getStockTransId()).get(0);
                        Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
                        String noteExportCode ="PX_" + userToken.getLoginName().toUpperCase() + "_" + stockTran.getStockTransId();
                        
                        StockTransAction transAction = new StockTransAction();
                        transAction.setActionId(actionId);
                        transAction.setStockTransId(stockTran.getStockTransId());
                        transAction.setActionCode(noteExportCode.trim());
                        transAction.setActionType(Constant.ACTION_TYPE_NOTE); //Loai giao dich xuat kho
                        transAction.setNote(stockTran.getNote());
                        transAction.setUsername(userToken.getLoginName());
                        transAction.setCreateDatetime(new Date());
                        transAction.setActionStaffId(userToken.getUserID());
                        transAction.setFromActionCode(stockTransAction.getActionCode()); //Phieu nhap duoc lap tu lenh xuat
                        mySession.save(transAction);
                        count = count + 1;
                        
                    } else {
                        return false;
                    }
                }
            }
            if (count > 0 && count == l.size()) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();
        }

        return result;
    }

    //UPDATE CUR_INVOICE_NO FOR TBL_INVOICE_LIST
    private boolean updateInvoiceList(Long invoiceListId, String staffId, Session mySession) {
        boolean result = false;
        try {
            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
            invoiceListDAO.setSession(mySession);
            InvoiceList invoiceList = invoiceListDAO.findById(invoiceListId);

            mySession.refresh(invoiceList, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

            if (null == invoiceList) {
                return result;
            }
            if (!invoiceList.getStaffId().toString().equals(staffId)) {
                return result;
            }
            //if (invoiceList.getStatus().intValue() != Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF.intValue()) {            
            if (invoiceList.getStatus().intValue() != Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP.intValue()) {
                return result;
            }

            if (invoiceList.getCurrInvoiceNo() < invoiceList.getFromInvoice() || invoiceList.getCurrInvoiceNo() >= invoiceList.getToInvoice()) {
                invoiceList.setCurrInvoiceNo(0L);
                invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
            } else {
                Long currInvoiceNo = invoiceList.getCurrInvoiceNo();
                currInvoiceNo++;
                invoiceList.setCurrInvoiceNo(currInvoiceNo);
            }

            mySession.update(invoiceList);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();
        }

        return result;
    }

    public String printInvoice() throws Exception {
        String pageForward = CREATE_INVOICE;

        //pageForward = "returnMsg";

        getReqSession();
        try {
//            tannh20180419 :lay thong tin user login.
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String pageForwardTemp = (String) form.getPageForward();
            if (pageForwardTemp != null && !"".equals(pageForwardTemp)) {
                pageForward = pageForwardTemp;
            }

            Long invoiceUsedId = Long.valueOf(form.getInvoiceUsedId());
            //add by sonbc2: 27042016
            if (invoiceUsedId == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleManagment.printInvoice.error.noInvoiceId");
                return pageForward;
            }
            InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
            invoiceUsedDAO.setSession(getSession());
            InvoiceUsed invoiceUsed = invoiceUsedDAO.findById(invoiceUsedId);
            if (invoiceUsed == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleManagment.printInvoice.error.noInvoiceId");
                return pageForward;
            }
            //end sonbc2

            InvoicePrinterV2DAO invoicePrinterDAO = new InvoicePrinterV2DAO();
            String returnValue = invoicePrinterDAO.printSaleTransInvoice(invoiceUsedId, 1L, 1L);

            if (returnValue.equals("0")) {
                req.setAttribute("returnMsg", "Error. Goods list is empty! Print invoice is fail!");
                return pageForward;
            }

            if (returnValue.equals("2")) {
                req.setAttribute("returnMsg", "saleInvoice.error.invalidPrinterParam");
                return pageForward;
            }
            if (returnValue.equals("3")) {
                req.setAttribute("returnMsg", "saleInvoice.error.requiredPageSize");
                return pageForward;
            }
            // tannh20180423 start:theo yeu cau cua TraTV phong TC chi can in hoa don khong can xuat ra file  
            String path = returnValue;
            
            String pathOneNew = "";
            int lengtDot = path.lastIndexOf(".");
            if (lengtDot != -1) {
                String beforeDot = path.substring(0, lengtDot);
                beforeDot = beforeDot + "new";
                pathOneNew = beforeDot + ".pdf";
            }
            manipulatePdf(path, pathOneNew, userToken);

            String pathTow = invoicePrinterDAO.printSaleTransInvoiceT2(invoiceUsedId, 1L, 1L);
            String pathOneTow = "";
            int lengtDotTow = pathTow.lastIndexOf(".");
            if (lengtDotTow != -1) {
                String beforeDot = pathTow.substring(0, lengtDotTow);
                beforeDot = beforeDot + "new";
                pathOneTow = beforeDot + ".pdf";
            }
            manipulatePdf(pathTow, pathOneTow, userToken);

            String pathThree = invoicePrinterDAO.printSaleTransInvoiceT3(invoiceUsedId, 1L, 1L);
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
            num = invoiceUsed.getPrintType1();
            num = num == null ? 0L : num;
            num += 1L;
            invoiceUsed.setPrintType1(num);
            getSession().update(invoiceUsed);
            getSession().getTransaction().commit();
            getSession().beginTransaction();
            req.setAttribute("returnMsg", "saleInvoice.success.printeSaleTransInvoice");

            //Gan lai so hoa don len label
            form.setInvoiceNo(form.getInvoiceNoEdit());

              // tannh20180423 start:theo yeu cau cua TraTV phong TC chi can in hoa don khong can xuat ra file  
            
            form.setExportUrl(pathOneNew);
            form.setExportUrl1(pathOneTow);
            form.setExportUrl2(pathOneThree);
            
//            req.setAttribute("reportStockTransPath",returnValue);
//            form.setExprotUrlRac(returnValue.replace(".pdf", "_rac.pdf"));
           // tannh20180423 end:theo yeu cau cua TraTV phong TC chi can in hoa don khong can xuat ra file   
            form.setInvoiceUsedId(invoiceUsedId.toString());
            form.setInvoiceNo(invoiceUsed.getInvoiceNo());

            req.setAttribute("print", 0);
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
        } finally {
            return pageForward;
        }

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

    public String goBack() {
        String pageForward = INVOICE_SALE_TRANS;
        getReqSession();
        try {
            if (form.getIsPopup() != null && form.getIsPopup().trim().equals("1")) {
                return pageForward;
            }

            form = (SaleTransInvoiceForm) reqSession.getAttribute(SALE_TRANS_INVOICE_FORM);
            if (null == form) {
                preparePage();
            }
            searchSaleTrans();
        } catch (Exception e) {
        }
        return pageForward;
    }

    //Combo Danh sach doi tuong
    public List getListObject() {
        return listObject;
    }

    public void setListObject(List listObject) {
        this.listObject = listObject;
    }

    public String getObjectByChannelType() throws Exception {
        try {
            //Chon hang hoa tu loai hang hoa
            String channelTypeIdString = this.getRequest().getParameter("agentTypeIdSearch");
            String[] header = {"", "---Chọn đối tượng---"};
            //listObject.add(header);
            if (channelTypeIdString != null && !"".equals(channelTypeIdString.trim())) {
                UserToken userToken = (UserToken) this.getRequest().getSession().getAttribute(Constant.USER_TOKEN);

                Long channelTypeId = Long.valueOf(channelTypeIdString.trim());
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                ChannelType channelType = channelTypeDAO.findById(channelTypeId);
                String strQuery = "";
                Query query;
                List lstParam = new ArrayList();
                lstParam.add(Constant.STATUS_USE);
                lstParam.add(channelType.getChannelTypeId());
                if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                    strQuery = " SELECT a.shop_id AS objId, a.shop_code AS objCode, a.NAME AS objName, 1 AS objType FROM shop a ";
                    strQuery = " SELECT a.shop_id AS objId, a.NAME AS objName FROM shop a ";
                    strQuery += " WHERE 1=1 and a.status=? and a.channel_type_id=? and a.parent_shop_id=? ";
                    query =
                            getSession().createSQLQuery(strQuery).
                            addScalar("objId", Hibernate.LONG).
                            //addScalar("objCode", Hibernate.STRING).
                            addScalar("objName", Hibernate.STRING).
                            //addScalar("objType", Hibernate.LONG).
                            setResultTransformer(Transformers.aliasToBean(ComboListBean.class));
                    lstParam.add(userToken.getShopId());
                } else if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
                    //strQuery = " SELECT a.staff_id AS objId, a.staff_code AS objCode, a.NAME AS objName, 2 AS objType FROM staff a ";
                    strQuery = " SELECT a.staff_id AS objId, a.NAME AS objName FROM staff a ";
                    strQuery += " WHERE 1=1 and a.status=? and a.channel_type_id=? and a.staff_owner_id=? ";
                    query =
                            getSession().createSQLQuery(strQuery).
                            addScalar("objId", Hibernate.LONG).
                            //addScalar("objCode", Hibernate.STRING).
                            addScalar("objName", Hibernate.STRING).
                            //addScalar("objType", Hibernate.LONG).
                            setResultTransformer(Transformers.aliasToBean(ComboListBean.class));
                    lstParam.add(userToken.getUserID());
                } else {
                    throw new Exception("ChannelType khong dung");
                }
                for (int idx = 0; idx < lstParam.size(); idx++) {
                    query.setParameter(idx, lstParam.get(idx));
                }

                listObject.addAll(query.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS_LIST_OBJECT;
    }

    public List<ImSearchBean> getListObjectByParentIdOrOwenerId(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        try {
            req = imSearchBean.getHttpServletRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                Long channelTypeId = Long.valueOf(imSearchBean.getOtherParamValue().trim());
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                ChannelType channelType = channelTypeDAO.findById(channelTypeId);

                String strQuery = "";
                Query query;
                List lstParam = new ArrayList();

                if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                    strQuery = " select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) from Shop a ";
                    strQuery += "where 1=1 and a.status=? and a.parentShopId=? and a.channelTypeId=? ";
                    lstParam.add(Constant.STATUS_USE);
                    lstParam.add(userToken.getShopId());
                    lstParam.add(channelType.getChannelTypeId());

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += "and lower(a.shopCode) like ? ";
                        lstParam.add(imSearchBean.getCode().trim().toLowerCase() + "%");
                    }

                    if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                        strQuery += "and lower(a.name) like ? ";
                        lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                    }

                    strQuery += " and rownum <= ? ";
                    lstParam.add(100L);

                    strQuery += " order by a.shopCode ";

                    query = getSession().createQuery(strQuery);

                } else if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {

                    strQuery = " select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) from Staff a ";
                    strQuery += " WHERE 1=1 and a.status=? and a.shopId = ? ";//cung don vi shopId
                    lstParam.add(Constant.STATUS_USE);
                    lstParam.add(userToken.getShopId());

                    if (this.IS_STOCK_STAFF_OWNER) {//chi cho xuat kho cho CTV do NV quan ly
                        strQuery += " and a.staffOwnerId = ? ";
                        lstParam.add(userToken.getUserID());
                    }

                    strQuery += " and a.channelTypeId = ? ";
                    lstParam.add(channelType.getChannelTypeId());

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += " and lower(a.staffCode) like ? ";
                        lstParam.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
                    }

                    if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                        strQuery += " and lower(a.name) like ? ";
                        lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                    }

                    strQuery += " and rownum <= ? ";
                    lstParam.add(100L);

                    strQuery += " order by a.staffCode ";

                    query = getSession().createQuery(strQuery);
                } else {
                    throw new Exception("ChannelType khong dung");
                }
                for (int idx = 0; idx < lstParam.size(); idx++) {
                    query.setParameter(idx, lstParam.get(idx));
                }

                List<ImSearchBean> tmpList1 = query.list();
                if (tmpList1 != null && tmpList1.size() > 0) {
                    listImSearchBean.addAll(tmpList1);
                }
            }
            return listImSearchBean;
        } catch (Exception ex) {
            ex.printStackTrace();
            return listImSearchBean;
        }
    }

    public List<ImSearchBean> getObjectName(ImSearchBean imSearchBean) {
        try {
            getReqSession();
            CommonDAO commonDAO = new CommonDAO();
            commonDAO.setSession(this.getSession());
            imSearchBean.setHttpServletRequest(this.getRequest());
            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                Long channelTypeId = Long.valueOf(imSearchBean.getOtherParamValue().trim());
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                ChannelType channelType = channelTypeDAO.findById(channelTypeId);

                if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                    return commonDAO.getShopName(imSearchBean);
                } else if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
                    return commonDAO.getStaffName(imSearchBean);
                } else {
                    throw new Exception("ChannelType khong dung");
                }
            } else {
                throw new Exception("ChannelType khong dung");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<ImSearchBean>();
        }
    }

    public Long getListObjectByParentIdOrOwenerIdSize(ImSearchBean imSearchBean) {
        try {
            req = imSearchBean.getHttpServletRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                Long channelTypeId = Long.valueOf(imSearchBean.getOtherParamValue().trim());
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                ChannelType channelType = channelTypeDAO.findById(channelTypeId);

                String strQuery = "";
                Query query;
                List lstParam = new ArrayList();

                if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                    strQuery = " select count(*) from Shop a ";
                    strQuery += "where 1=1 and a.status=? and a.parentShopId=? and a.channelTypeId=? ";
                    lstParam.add(Constant.STATUS_USE);
                    lstParam.add(userToken.getShopId());
                    lstParam.add(channelType.getChannelTypeId());

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += "and lower(a.shopCode) like ? ";
                        lstParam.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
                    }

                    if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                        strQuery += "and lower(a.name) like ? ";
                        lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                    }

//                    strQuery += " order by a.shopCode ";

                    query = getSession().createQuery(strQuery);

                } else if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {

                    strQuery = " select count(*) from Staff a ";
                    strQuery += " WHERE 1=1 and a.status=? and a.shopId = ? ";//cung don vi shopId
                    lstParam.add(Constant.STATUS_USE);
                    lstParam.add(userToken.getShopId());

                    if (this.IS_STOCK_STAFF_OWNER) {//chi cho xuat kho cho CTV do NV quan ly
                        strQuery += " and a.staffOwnerId = ? ";
                        lstParam.add(userToken.getUserID());
                    }

                    strQuery += " and a.channelTypeId = ? ";
                    lstParam.add(channelType.getChannelTypeId());

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += " and lower(a.staffCode) like ? ";
                        lstParam.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
                    }

                    if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                        strQuery += " and lower(a.name) like ? ";
                        lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                    }

//                    strQuery += " order by a.staffCode ";

                    query = getSession().createQuery(strQuery);
                } else {
                    throw new Exception("ChannelType khong dung");
                }
                for (int idx = 0; idx < lstParam.size(); idx++) {
                    query.setParameter(idx, lstParam.get(idx));
                }

                List<Long> tmpList = query.list();
                if (tmpList != null && tmpList.size() > 0) {
                    return tmpList.get(0);
                } else {
                    return 0L;
                }
            }
            return 0L;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }

    /**
     * Sua - huy - khoi phuc hoa don ban hang
     *
     * @return
     * @throws java.lang.Exception
     */
    public String gotoRecoverInvoice() throws Exception {
        getReqSession();
        String pageForward = INVOICE_USED_DETAIL;
        if (form.getInvoiceUsedId() == null || form.getInvoiceUsedId().trim().equals("")) {
//            req.setAttribute("returnMsg", "Lỗi số hoá đơn rỗng!");
            req.setAttribute("returnMsg", "ERR.SAE.123");
            return pageForward;
        }
        return pageForward;
    }

    public String gotoEditInvoice() throws Exception {
        getReqSession();
        String pageForward = INVOICE_USED_DETAIL;
        if (form.getInvoiceUsedId() == null || form.getInvoiceUsedId().trim().equals("")) {
//            req.setAttribute("returnMsg", "Lỗi số hoá đơn rỗng!");
            req.setAttribute("returnMsg", "ERR.SAE.123");
            return pageForward;
        }
        backInvoice();
        form.setProcessingInvoice(1L);
        return pageForward;
    }

    public String gotoCancelInvoice() throws Exception {
        getReqSession();
        String pageForward = INVOICE_USED_DETAIL;
        if (form.getInvoiceUsedId() == null || form.getInvoiceUsedId().trim().equals("")) {
//            req.setAttribute("returnMsg", "Lỗi số hoá đơn rỗng!");
            req.setAttribute("returnMsg", "ERR.SAE.123");
            return pageForward;
        }
        backInvoice();
        form.setProcessingInvoice(2L);
        return pageForward;
    }

    public String gotoBackInvoice() throws Exception {
        getReqSession();
        String pageForward = INVOICE_USED_DETAIL;
        backInvoice();
        form.setProcessingInvoice(0L);
        return pageForward;
    }

    private void backInvoice() {
        try {
            getReqSession();
            InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
            invoiceUsedDAO.setSession(getSession());
            Long tempInvoiceUsedId = new Long(form.getInvoiceUsedId());
            InvoiceUsed invoiceUsed = invoiceUsedDAO.findById(tempInvoiceUsedId);
            form.setObjName(invoiceUsed.getCustName());
            if (invoiceUsed.getReasonId() != null) {
                form.setReasonId(invoiceUsed.getReasonId().toString());
            }
            form.setPayMethodId(invoiceUsed.getPayMethod());
            form.setInvoiceNo(invoiceUsed.getInvoiceNo());

            //
            form.setObjCompany(invoiceUsed.getCompany());
            form.setObjAccount(invoiceUsed.getAccount());
            form.setObjAddress(invoiceUsed.getAddress());

        } catch (Exception ex) {
            getSession().clear();
            ex.printStackTrace();
        }
    }

    /*
     * Khoi phuc hoa don ban hang
     */
    public String recoverInvoice() throws Exception {
        getReqSession();
        Session mySession = this.getSession();
        String pageForward = INVOICE_USED_DETAIL;
        if (form.getInvoiceUsedId() == null || form.getInvoiceUsedId().trim().equals("")) {
//            req.setAttribute("returnMsg", "Lỗi số hoá đơn rỗng!");
            req.setAttribute("returnMsg", "ERR.SAE.123");
            return pageForward;
        }
        if (!processingRecoverInvoice(mySession)) {
            return pageForward;
        }
//        req.setAttribute(Constant.RETURN_MESSAGE, "Khôi phục hoá đơn thành công!");
        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.124");
        req.setAttribute("recoverInvoice", true);
        return pageForward;
    }

    private boolean processingRecoverInvoice(Session mySession) {
        boolean result = false;
        try {
            UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
            InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
            invoiceUsedDAO.setSession(mySession);
            InvoiceUsed invoiceUsed = invoiceUsedDAO.findById(Long.valueOf(form.getInvoiceUsedId()));
            if (invoiceUsed == null) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi không tìm thấy thông tin hoá đơn!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.125");
                return result;
            }
            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
            invoiceListDAO.setSession(mySession);
            InvoiceList invoiceList = invoiceListDAO.findById(invoiceUsed.getInvoiceListId());
            if (invoiceList == null) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi không tìm thấy thông tin hoá đơn!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.125");
                return result;
            }

            BillBaseDAO billBaseDAO = new BillBaseDAO();
            billBaseDAO.setSession(mySession);

            //Neu phai tach dai
            if (!(invoiceUsed.getInvoiceId().compareTo(invoiceList.getCurrInvoiceNo() - 1) == 0 || invoiceUsed.getInvoiceId().compareTo(invoiceList.getToInvoice()) == 0)) {
                InvoiceList newInvoiceList = new InvoiceList();
                newInvoiceList.setInvoiceListId(getSequence("INVOICE_LIST_SEQ"));
                newInvoiceList.setBlockNo(invoiceList.getBlockNo());
                newInvoiceList.setBookType(invoiceList.getBookType());
                newInvoiceList.setBookTypeId(invoiceList.getBookTypeId());

                //newInvoiceList.setCurrInvoiceNo(invoiceUsed.getInvoiceId()+1);sai
                newInvoiceList.setCurrInvoiceNo(invoiceList.getCurrInvoiceNo());

                newInvoiceList.setDateAssign(invoiceList.getDateAssign());
                newInvoiceList.setDateCreated(new Date());

                //newInvoiceList.setFromInvoice(invoiceList.getFromInvoice());sai
                newInvoiceList.setFromInvoice(invoiceUsed.getInvoiceId() + 1);

                newInvoiceList.setSerialNo(invoiceList.getSerialNo());
                newInvoiceList.setShopId(invoiceList.getShopId());
                newInvoiceList.setStaffId(invoiceList.getStaffId());
                newInvoiceList.setStatus(invoiceList.getStatus());
                if (newInvoiceList.getStatus().compareTo(4L) == 0) {
                    newInvoiceList.setStatus(3L);
                }
                newInvoiceList.setStrBlockNo(invoiceList.getStrBlockNo());
                newInvoiceList.setStrCurrInvoiceNo(invoiceList.getStrCurrInvoiceNo());
                newInvoiceList.setStrSerialNo(invoiceList.getStrSerialNo());
                newInvoiceList.setStrToInvoice(invoiceList.getStrToInvoice());
                newInvoiceList.setToInvoice(invoiceList.getToInvoice());
                newInvoiceList.setUserAssign(invoiceList.getUserAssign());
                newInvoiceList.setUserCreated(invoiceList.getUserCreated());
                getSession().save(newInvoiceList);

                invoiceList.setToInvoice(invoiceUsed.getInvoiceId());



                //Log dai hoa don duoc tao moi khi khoi phuc
                billBaseDAO.saveInvoiceTransferLog(newInvoiceList, userToken, Constant.INVOICE_LOG_STATUS_RECOVERED, null, null, userToken.getUserID());

                //Update cac hoa don da su dung dai hoa don vua khoi phuc
                //String sql = " update invoice_used set invoice_list_id = " + newInvoiceList.getInvoiceListId()
                //        + " where invoice_list_id = " + invoiceList.getInvoiceListId() + " and invoice_id >= " + newInvoiceList.getFromInvoice();

                String sql = " update invoice_used set invoice_list_id = ? where invoice_list_id = ? and invoice_id >= ?";

                Query qUpdateInvoiceUsed = getSession().createSQLQuery(sql);

                qUpdateInvoiceUsed.setParameter(0, newInvoiceList.getInvoiceListId());
                qUpdateInvoiceUsed.setParameter(1, invoiceList.getInvoiceListId());
                qUpdateInvoiceUsed.setParameter(2, newInvoiceList.getFromInvoice());

                qUpdateInvoiceUsed.executeUpdate();
            }

            //Gan lai currentInvoice = hoa don khoi phuc
            invoiceList.setCurrInvoiceNo(invoiceUsed.getInvoiceId());
            //Neu dai hoa don co trang thai la da su dung het thi chuyen sang trang thai dang trong kho
            if (invoiceList.getStatus().compareTo(4L) == 0) {
                invoiceList.setStatus(3L);
            }

            //Log dai hoa don chua hoa don khoi phuc
            billBaseDAO.saveInvoiceTransferLog(invoiceList, userToken, Constant.INVOICE_LOG_STATUS_RECOVERED, null, null, userToken.getUserID());

            mySession.update(invoiceList);
            mySession.delete(invoiceUsed);


            //Invoice_Coupon
            if (Constant.IS_VER_HAITI) {
                try {
                    InvoiceCouponDAO invoiceCouponDAO = new InvoiceCouponDAO();
                    invoiceCouponDAO.setSession(mySession);
                    List<InvoiceCoupon> invoiceCouponList = invoiceCouponDAO.findByInvoiceUsedId(invoiceUsed.getInvoiceUsedId());
                    if (invoiceCouponList == null || invoiceCouponList.size() == 0) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Không tìm thấy thông tin cuốn hoá đơn");
//                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.");
                        return result;
                    }
                    InvoiceCoupon invoiceCoupon = invoiceCouponList.get(0);
                    if (!invoiceCoupon.getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Bạn không có quyền khôi phục hoá đơn");
//                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.");
                        return result;
                    }

                    //De luu log nguoi thuc hien 
                    invoiceCoupon.setUserName(userToken.getLoginName());
                    invoiceCoupon.setLastUpdateKey(2L);//Thuc hien tu web
                    mySession.update(invoiceCoupon);

                    mySession.delete(invoiceCoupon);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Bạn không có quyền khôi phục hoá đơn");
//                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.");
                    mySession.clear();
                    mySession.getTransaction().rollback();
                    mySession.beginTransaction();
                    return result;
                }
            }

            result = !result;
            return result;
        } catch (Exception ex) {
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();

            ex.printStackTrace();
//            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi khôi phục hoá đơn!");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.126");
            return result;
        }
    }


    /*
     * Huy hoa don ban hang
     */
    public String cancelInvoice() throws Exception {
        log.info("# Begin method cancelInvoice");
        getReqSession();
        Session mySession = this.getSession();
        String pageForward = INVOICE_USED_DETAIL;
        if (form.getInvoiceUsedId() == null || form.getInvoiceUsedId().trim().equals("")) {
            log.info("# End method cancelInvoice");
//            req.setAttribute("returnMsg", "Lỗi số hoá đơn rỗng!");
            req.setAttribute("returnMsg", "ERR.SAE.123");
            return pageForward;
        }
        if (!processingCancelInvoice(mySession)) {
            log.info("# End method cancelInvoice");
            return pageForward;
        }

        form.setProcessingInvoice(0L);
//        req.setAttribute(Constant.RETURN_MESSAGE, "Huỷ hoá đơn thành công!");
        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.127");
        req.setAttribute("recoverInvoice", true);
        log.info("# End method cancelInvoice");
        return pageForward;
    }

    private boolean processingCancelInvoice(Session mySession) {
        boolean result = false;
        try {
            getReqSession();
            UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);

            InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
            invoiceUsedDAO.setSession(mySession);
            Long tempInvoiceUsedId = new Long(form.getInvoiceUsedId());

            InvoiceUsed invoiceUsed = invoiceUsedDAO.findById(tempInvoiceUsedId);

            //Kiem tra quyen huy hoa don truoc khi thuc hien huy
            InvoiceManagementDAO invoiceManagementDAO = new InvoiceManagementDAO();
            invoiceManagementDAO.setSession(this.getSession());
            invoiceManagementDAO.checkAuthorityInvoiceUsed(tempInvoiceUsedId);
            if (!invoiceManagementDAO.getForm().isCancelInvoice()) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Bạn không có quyền huỷ hoá đơn");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.");
                return result;
            }

            //Huy hoa don lap cho giao dich ban cho dai ly
//            if (!invoiceManagementDAO.checkAuthorityInvoiceUsedForAgent(invoiceUsed.getInvoiceUsedId())){
//                return result;
//            }

            //Thuc hien huy hoa don
            invoiceUsed.setStatus(Constant.INVOICE_USED_STATUS_DELETE);
//            invoiceUsed.setReasonId(new Long(form.getCancelReasonId()));
            invoiceUsed.setDestroyReasonId(new Long(form.getCancelReasonId()));
            invoiceUsed.setDestroyDate(getSysdate());
            invoiceUsed.setDestroyer(userToken.getLoginName());
            mySession.update(invoiceUsed);
            SaleTransDAO saleTransDAO = new SaleTransDAO();
            saleTransDAO.setSession(mySession);

            Long ownerId = null;
            Long ownerType = null;
            Double amount = 0.0;

            //Cap nhat trang thai giao dich -> chua lap hoa don            
            List<SaleTrans> saleTransList = saleTransDAO.getSaleTrans(tempInvoiceUsedId, Constant.SALE_TRANS_STATUS_BILLED);
            for (SaleTrans saleTrans : saleTransList) {
                saleTrans.setStatus(Constant.SALE_TRANS_STATUS_NOT_BILLED);
                saleTrans.setInvoiceUsedId(null);
                saleTrans.setInvoiceCreateDate(null);
                //getSession().update(saleTrans);
                mySession.update(saleTrans);

                //Neu khong phai la ctv lam dv hoac ban thay ctv thi khong xu ly 
                if (!(saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COL_RETAIL)
                        || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_RETAIL_FOR_COL))) {
                    continue;
                }
//                if (!saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COL_RETAIL)) {
//                    continue;
//                }

//                if (collId == null && saleTrans.getStaffId() != null) {
//                    collId = saleTrans.getStaffId();
//                    collType = Constant.OWNER_TYPE_STAFF;
//                    amount = 0.0 - invoiceUsed.getAmountTax();
//                } else if (collId == null && saleTrans.getShopId() != null) {
//                    collId = saleTrans.getShopId();
//                    collType = Constant.OWNER_TYPE_SHOP;
//                    amount = 0.0 - invoiceUsed.getAmountTax();
//                }
            }
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
                        -1 * amount,
                        getSysdate(), invoiceUsed.getInvoiceUsedId(), userToken.getLoginName());
                if (!returnMsg.equals("")) {
                    mySession.clear();
                    mySession.getTransaction().rollback();
                    mySession.beginTransaction();
                    req.setAttribute(Constant.RETURN_MESSAGE, returnMsg);
                    return result;
                }
                String sendMess = MessageFormat.format(getText("sms.2005"), NumberUtils.rounđAndFormatAmount(amount));
                //"Huy hoa don thanh cong. Tai khoan thanh toan cua ban duoc cong lai " + WsCommonDAO.formatNumber(amount) + " dong";
                System.out.println("sendSMS : " + sendMess);

                AccountAgent accountAgent = null;
                accountAgent = accountAgentDAO.findByOwnerIdAndOwnerType(mySession, ownerId, ownerType);
                if (accountAgent != null
                        && accountAgent.getCheckIsdn() != null
                        && accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)
                        && accountAgent.getMsisdn() != null) {
                    SmsClient.sendSMS155(accountAgent.getMsisdn(), sendMess);
                }
            }


            //Lap hoa don thay CTV
            //Tru tien khi huy hoa don
            //TrongLV_111214_KHONG CONG LAI TIEN KHI HUY HD
            /*
             * if (collId != null && collType != null) { try { String returnMsg
             * = ""; String sendMess = "";
             *
             * //Giao dich ban hang ky gui thay CTV if
             * (saleTransList.get(0).getSaleTransType().equals(Constant.SALE_TRANS_TYPE_RETAIL_FOR_COL))
             * {
             *
             * sendMess = "Huy hoa don thanh cong. Tai khoan thanh toan cua ban
             * bi tru " + WsCommonDAO.formatNumber(amount) + " dong";
             *
             * AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
             * returnMsg = accountAgentDAO.addBalance(getSession(),
             * saleTransList.get(0).getStaffId(), Constant.OWNER_TYPE_STAFF,
             * Constant.DEPOSIT_TRANS_TYPE_INVOICE, amount, getSysdate(),
             * invoiceUsed.getInvoiceUsedId(), userToken.getLoginName());
             *
             * if (!returnMsg.equals("")) { getSession().clear();
             * getSession().getTransaction().rollback();
             * getSession().beginTransaction();
             * req.setAttribute(Constant.RETURN_MESSAGE, returnMsg); return
             * result; } // OK155_SmsClient.sendSMS155("982289145", sendMess);
             *
             * }
             * //Neu la giao dich CTV lam dich vu //Khong xu ly
             *
             * } catch (Exception ex) {
             *
             * mySession.clear(); mySession.getTransaction().rollback();
             * mySession.beginTransaction();
             *
             * ex.printStackTrace(); req.setAttribute(Constant.RETURN_MESSAGE,
             * ex.getMessage()); return result; }
            }
             */

            //Update Invoice_Coupon
            if (Constant.IS_VER_HAITI) {
                try {
                    InvoiceCouponDAO invoiceCouponDAO = new InvoiceCouponDAO();
                    invoiceCouponDAO.setSession(mySession);
                    List<InvoiceCoupon> invoiceCouponList = invoiceCouponDAO.findByInvoiceUsedId(invoiceUsed.getInvoiceUsedId());
                    if (invoiceCouponList == null || invoiceCouponList.size() == 0) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Không tìm thấy thông tin cuốn hoá đơn");
//                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.");
                        return result;
                    }
                    InvoiceCoupon invoiceCoupon = invoiceCouponList.get(0);
                    if (invoiceCoupon.getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                        if (!invoiceCoupon.getOwnerId().equals(userToken.getShopId())) {
                            ShopDAO shopDAO = new ShopDAO();
                            shopDAO.setSession(mySession);
                            Shop shop = shopDAO.findById(invoiceCoupon.getOwnerId());
                            if (shop == null || shop.getShopPath().indexOf("_" + userToken.getShopId().toString() + "_") < 0) {
                                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Bạn không có quyền huỷ hoá đơn");
//                                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.");
                                return result;
                            }
                        }
                    }
//                    invoiceCoupon.setInvoiceDate(getSysdate());//Sua ngay lap hoa don thanh ngay huy hoa don
                    invoiceCoupon.setLastUpdate(getSysdate());//
                    invoiceCoupon.setStatus(Constant.INVOICE_COUPON_STATUS_DELETE);

                    invoiceCoupon.setUserName(userToken.getLoginName());
                    invoiceCoupon.setLastUpdateKey(2L);//Thuc hien tu web

                    mySession.update(invoiceCoupon);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Bạn không có quyền huỷ hoá đơn");
//                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.");
                    mySession.clear();
                    mySession.getTransaction().rollback();
                    mySession.beginTransaction();
                    return result;
                }
            }



            mySession.flush();
            mySession.getTransaction().commit();
            mySession.beginTransaction();

            //Kiem tra lai cac quyen ve hoa don (Sau khi huy hoa don xong, khong duoc huy lai hoa don nua & cho phep khoi phuc hoa don da huy)
            invoiceManagementDAO = new InvoiceManagementDAO();
            invoiceManagementDAO.setSession(mySession);
            invoiceManagementDAO.checkAuthorityInvoiceUsed(invoiceUsed.getInvoiceUsedId());
            form.setRecoverInvoice(invoiceManagementDAO.getForm().isRecoverInvoice());
            form.setCancelInvoice(invoiceManagementDAO.getForm().isCancelInvoice());
            form.setEditInvoice(invoiceManagementDAO.getForm().isEditInvoice());

            result = !result;
            return result;
        } catch (Exception ex) {

            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();

            ex.printStackTrace();
//            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi huỷ hoá đơn!");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.128");
            return result;
        }
    }

    /*
     * Sua hoa don ban hang
     */
    public String editInvoice() throws Exception {
        getReqSession();
        Session mySession = this.getSession();
        String pageForward = INVOICE_USED_DETAIL;
        if (form.getInvoiceUsedId() == null || form.getInvoiceUsedId().trim().equals("")) {
//            req.setAttribute("returnMsg", "Lỗi số hoá đơn rỗng!");
            req.setAttribute("returnMsg", "ERR.SAE.123");
            return pageForward;
        }
        if (!processingEditInvoice(mySession)) {
            return pageForward;
        }

        form.setProcessingInvoice(0L);
//        req.setAttribute(Constant.RETURN_MESSAGE, "Sửa thông tin hoá đơn thành công!");
        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.129");
        req.setAttribute("recoverInvoice", true);
        return pageForward;
    }

    private boolean processingEditInvoice(Session mySession) {
        boolean result = false;
        try {
            getReqSession();
//            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
            //invoiceUsedDAO.setSession(getSession());
            invoiceUsedDAO.setSession(mySession);
            InvoiceUsed invoiceUsed = invoiceUsedDAO.findById(Long.valueOf(form.getInvoiceUsedId()));
            if (invoiceUsed == null) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi không tìm thấy thông tin hoá đơn!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.125");
                return result;
            }

            if (!validateEditInvoice(mySession)) {
                return result;
            }

            invoiceUsed.setCustName(form.getObjName().trim());

            if (form.getObjAccount() != null && !form.getObjAccount().trim().equals("")) {
                invoiceUsed.setAccount(form.getObjAccount().trim());
            }

            if (form.getObjAddress() != null && !form.getObjAddress().trim().equals("")) {
                invoiceUsed.setAddress(form.getObjAddress().trim());
            }

            if (form.getObjCompany() != null && !form.getObjCompany().trim().equals("")) {
                invoiceUsed.setCompany(form.getObjCompany().trim());
            }

            if (form.getObjTin() != null && !form.getObjTin().trim().equals("")) {
                invoiceUsed.setTin(form.getObjTin().trim());
            }

            invoiceUsed.setReasonId(Long.valueOf(form.getReasonId().trim()));
            invoiceUsed.setPayMethod(form.getPayMethodId().trim());
            //getSession().update(invoiceUsed);
            mySession.update(invoiceUsed);

            result = !result;
            return result;
        } catch (Exception ex) {
//            getSession().clear();



            ex.printStackTrace();
//            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi sửa thông tin hoá đơn!");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.130");
            return result;
        }
    }

    private boolean validateEditInvoice(Session mySession) {
        boolean result = false;
        try {
            if (form.getObjName() == null || form.getObjName().trim().equals("")) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi chưa nhập tên khách hàng!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.131");
                return result;
            }

            if (form.getReasonId() == null || form.getReasonId().trim().equals("")) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi chưa chọn lý do lập hoá đơn!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.132");
                return result;
            }

            if (form.getPayMethodId() == null || form.getPayMethodId().trim().equals("")) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi chưa chọn hình thức thanh toán!");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.133");
                return result;
            }

            result = !result;
            return result;
        } catch (Exception ex) {
//            getSession().clear();
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();

            ex.printStackTrace();
//            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi sửa thông tin hoá đơn!");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.130");
            return result;
        }
    }
    private boolean checkTinInvoice() {
        boolean result = true;

        //Lay danh sach ma giao dich
        String[] saleTransIdTemp = form.getSaleTransIdList();

        //Khong co giao dich nao duoc chon
        if (!(saleTransIdTemp != null && saleTransIdTemp.length != 0)) {
            return result;
        }

        String queryString = "";
        List parameterList = new ArrayList();
        for (int i = 0; i < saleTransIdTemp.length; i++) {
            String temp = saleTransIdTemp[i];
            if (temp != null && !temp.trim().equals("") && !temp.trim().equals("false")) {
                if (!"".equals(queryString.trim())) {
                    queryString += " or ";
                }
                queryString += " st.sale_trans_id = ? ";
                parameterList.add(new Long(temp));
            }
        }
        //Khong co giao dich nao duoc chon
        if ("".equals(queryString.trim())) {
            return result;
        }

        queryString = "  select st.tin from sale_trans st where tin is not null and (" + queryString + " ) ";
        Query queryObject = getSession().createSQLQuery(queryString);
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }

        //Khong cung loai hoa don
        List lstTemp = queryObject.list();
        if (lstTemp != null && lstTemp.size() > 0) {
            if (lstTemp.get(0) == null) {
                return false;
            }
            return true;
        }
        result = false;
        return result;
    }

    private boolean checkTinStaffOrShop(List<SaleTransInvoiceBean> lstSaleTrans) {
        boolean result = true;
        //Lay danh sach ma giao dich
        String[] saleTransIdTemp = form.getSaleTransIdList();

        //Khong co giao dich nao duoc chon
        if (!(saleTransIdTemp != null && saleTransIdTemp.length != 0)) {
            return result;
        }

        String queryString = "";
        List parameterList = new ArrayList();
        for (int i = 0; i < saleTransIdTemp.length; i++) {
            String temp = saleTransIdTemp[i];
            if (temp != null && !temp.trim().equals("") && !temp.trim().equals("false")) {
                if (!"".equals(queryString.trim())) {
                    queryString += " or ";
                }
                queryString += " st.sale_trans_id = ? ";
                parameterList.add(new Long(temp));
            }
        }
        //Khong co giao dich nao duoc chon
        if ("".equals(queryString.trim())) {
            return result;
        }
        Long receiverType = 0L;
        for (SaleTransInvoiceBean lstSaleTran : lstSaleTrans) {
            if (lstSaleTran.getReceiverType() != null && lstSaleTran.getReceiverId() != null) {
                receiverType = lstSaleTran.getReceiverType();
                break;
            }
        }
        if (receiverType == 0L) {
            return false;
        }
        if (lstSaleTrans.get(0).getReceiverType().compareTo(Constant.RECEIVER_TYPE_SHOP) == 0) {
            queryString = "SELECT tin FROM shop WHERE tin IS NOT NULL AND shop_id IN (SELECT st.receiver_id FROM sale_trans st WHERE " + queryString + ")";

        } else {
            queryString = "SELECT tin FROM staff WHERE tin IS NOT NULL AND staff_id IN (SELECT st.receiver_id FROM sale_trans st WHERE " + queryString + ")";
        }
        Query queryObject = getSession().createSQLQuery(queryString);
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List lstTemp = queryObject.list();
        if (lstTemp == null || lstTemp.isEmpty()) {
            result = false;
        }

        return result;
    }
    //UPDATE CUR_INVOICE_NO FOR TBL_INVOICE_LIST
    private boolean updateInvoiceList(InvoiceList invoiceList, Session mySession) {
        boolean result = false;
        try {
            /*
            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
            invoiceListDAO.setSession(mySession);
            InvoiceList invoiceList = invoiceListDAO.findById(invoiceListId);
            mySession.refresh(invoiceList, LockMode.UPGRADE);
             */

            if (null == invoiceList) {
                return result;
            }
            /*
            if (!invoiceList.getStaffId().toString().equals(staffId)) {
            return result;
            }*/
            if (invoiceList.getStatus().intValue() != Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP.intValue()) {
                return result;
            }

            if (invoiceList.getCurrInvoiceNo() < invoiceList.getFromInvoice() || invoiceList.getCurrInvoiceNo() >= invoiceList.getToInvoice()) {
                invoiceList.setCurrInvoiceNo(0L);
                invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
            } else {
                Long currInvoiceNo = invoiceList.getCurrInvoiceNo();
                currInvoiceNo++;
                invoiceList.setCurrInvoiceNo(currInvoiceNo);
            }

            mySession.update(invoiceList);
            result = true;
        } catch (Exception e) {
            log.info(e.getStackTrace());
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();
        }

        return result;
    }
}
