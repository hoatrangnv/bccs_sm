/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import java.io.IOException;
import com.viettel.im.client.bean.InvoiceSaleListBean;
import com.viettel.im.client.bean.SaleTransBean;
import com.viettel.im.client.bean.SaleTransDetailBean;
import com.viettel.im.client.bean.SaleTransInvoiceViewHelper;
import com.viettel.im.client.form.SaleForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.InvoiceList;
import com.viettel.im.database.BO.InvoiceUsed;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.PrinterParam;
import com.viettel.im.database.DAO.PrinterParamDAO;
import com.viettel.im.database.DAO.CommonDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.lowagie.text.Anchor;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.pdf.PdfGraphics2D;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfTemplate;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.CurrentInvoiceListBean;
import com.viettel.im.database.BO.BookType;
import com.viettel.im.database.BO.SaleTransDetail;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import com.viettel.im.client.bean.InvoiceNoSaleItem;
import com.viettel.im.database.BO.ReasonGroup;
import com.viettel.im.database.BO.SaleInvoiceType;
import org.hibernate.EntityMode;
import org.hibernate.Query;


/**
 *
 * @author TUNGTV
 */
public class SaleInvoiceDAO extends BaseDAOAction{
//
//    float widthPage = 210;
//    float heightPage = 297 / 2;
//    /** Log Object */
//    private static final Logger log = Logger.getLogger(SaleInvoiceDAO.class);
//    /** Form Object */
//    private SaleForm saleForm = new SaleForm();
//    /** Search return */
//    public static final String SEARCH_SALE_TRANS = "searchSaleTrans";
//    /** Detail of sale trans return */
//    public static final String SALE_TRANS_DETAIL = "saleTransDetail";
//    /** List of sale trans */
//    public static final String SALE_TRANS_LIST = "saleTransList";
//    /** Create sale trans invoice return */
//    public static final String TO_CREATE_SALE_TRANS_INVOICE = "createSaleTransInvoice";
//
//    /** Go to interface for whole sale */
//    public static final String INVOICE_TO_WHOLE_SALE = "invoiceToWholeSale";
//    /** Status for invoice for agent */
//    private static final String SALE_TRANS_CREATED_STATUS = "2";
//    /** Self create invoice for sale trans */
//    private static final Long INVOICE_USED_MYSELF_CREATED = 1L;
//    /** Invoice create for agent, collaboration */
//    private static final Long INVOICE_USED_FOR_COLLABORATOR = 2L;
//    private static final String INVOICE_USED_STATUS = "1";
//    /** Status of sale trans when invoice was created */
//    private static final String SALE_TRANS_INVOICE_CREATED_STATUS = "3";
//    /** Current invoice no when all invoice was used */
//    private static final Long USED_ALL_CURR_INVOICE = 0L;
//    /** List of trans selected for creating invoice */
//    private static String[] saleTransId;
//    /** Invoice for sale trans Collaboration interface type */
//    private static final String SALE_TRANS_INVOICE_RETAIL_INTERFACE = "3";//HD ban le
//    /** Invoice for sale trans Agent interface type */
//    private static final String SALE_TRANS_INVOICE_AGENT_INTERFACE = "1";//HD cho dai ly
//    /** Invoice for sale trans retail interface type */
//    private static final String SALE_TRANS_INVOICE_COLLABORATOR_INTERFACE = "2";//HD thay
//    /** Invoice for sale trans retail interface type */
//    private static final String SALE_TRANS_DIRECT_CREATE_INVOICE_INTERFACE = "4";//HD khong tu GD
//    /** Stock trans agent owner type */
//    private static final Long STOCK_TRANS_AGENT_OWNER_TYPE = 1L;
//    /** Stock trans staff owner type */
//    private static final Long STOCK_TRANS_STAFF_OWNER_TYPE = 2L;
//    /** Retail sale trans type */
//    private static final String SALE_TRANS_TYPE_RETAIL = "1";
//    /** Agent sale trans type */
//    private static final String SALE_TRANS_TYPE_AGENT = "2";
//    /** Sale trans type for collaborator */
//    private static final String SALE_TRANS_TYPE_COLLABORATOR = "3";
//    private static final String SALE_TRANS_TYPE_SERVICE = "4";
//    private static final String AGENT_OBJECT_TYPE = "1";
//    private static final String AGENT_IS_VT_UNIT = "2";
//    private static final String SALE_TRANS_TYPE_PROMOTION = "5";
//    /** Status of invoice list was used all */
//    private static final Long NOT_USEABLE = 4L;
//    /** Resource bundle for COLLABOR */
//    private static final String COLLABOR = "COLLABOR";
//    /** Object to create sale invoice for */
//    private Map listObject = new HashMap();
//    private CommonDAO commonDAO = new CommonDAO();
//
//
//
//
//
//    //MrSun
//    private static final String PAY_METHOD = "PAY_METHOD";
//    private static final String REASON_TYPE = "INVOICE_NO_SALE";
//    private static final String LST_ITEMS = "lstItems";
//    private static final String  SELECTED_INVOICE_LIST_ID = "selectedInvoiceListId";
//    private static final String SALE_INVOICE_ID = "invoiceUsedId";
//    private static final Long SALE_INVOICE_TYPE = 3L;   //HD tu khong GD
//    private static final String CREATE_INVOICE_NO_SALE = "createInvoiceNoSale";
//    private static final String SALE_INVOICE_TYPE_GROUP = "saleGroup";
//    private static final String SALE_INVOICE_TYPE_LIST = "saleInvoiceTypeList";
//
//    //lap hoa don chung
//    private static final String REASON_INVOICE_WHOS = "INVOICE_WHOS";
//
//    private static final String REASON_INVOICE_RETAIL = "INVOICE_RETAIL";
//    private static final String REASON_INVOICE_COL = "INVOICE_COL";
//    private static final String REASON_INVOICE_PROMO = "INVOICE_PROMO";
//    private static final String REASON_INVOICE_INTERNAL = "INVOICE_INTERNAL";
//
//    private static final String REASON_INVOICE_NO_SALE = "INVOICE_NO_SALE";
//
//    private static final String REASON_SALE_WHOS = "SALE_WHOS";
//    private static final String REASON_SALE_RETAIL  = "SALE_RETAIL";
//    private static final String REASON_SALE_COL = "SALE_COL";
//    private static final String REASON_SALE_PROMO = "SALE_PROMO";
//    private static final String REASON_SALE_INTERNAL = "SALE_INTERNAL";
//
//    private static final String REASON_SALE_LIST = "lstReasonSale";
//    private static final String REASON_INVOICE_LIST = "lstReasonInvoice";
//
//    private Map lstReasonSale = new HashMap();
//
//    public Map getLstReasonSale() {
//        return lstReasonSale;
//    }
//
//    public void setLstReasonSale(Map lstReasonSale) {
//        this.lstReasonSale = lstReasonSale;
//    }
//    
//
//    //MrSun
//
//
//
//
//
//
//
//    public SaleForm getSaleForm() {
//        return saleForm;
//    }
//
//    public void setSaleForm(SaleForm saleForm) {
//        this.saleForm = saleForm;
//    }
//
//    /**
//     *
//     * author tamdt1
//     * date: 07/04/2009
//     * chuan bi form lap hoa don cho dai ly
//     *
//     */
//    public String preparePage() throws Exception {
//
//        log.info("Begin method preparePage of SaleDAO ...");
//        String pageForward;
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = Constant.ERROR_PAGE;
//        setSearchConditionSession(true);
//        if (userToken != null) {
//            try {
//                pageForward = INVOICE_TO_WHOLE_SALE;
//                SaleForm saleForm = getSaleForm();
//
//                saleForm.setTransStatus(SALE_TRANS_CREATED_STATUS);
//
//                /** Get shop ID */
//                ShopDAO shopDAO = new ShopDAO();
//                shopDAO.setSession(getSession());
//                Long shopId = shopDAO.getShopIDByStaffID(userToken.getUserID());
//                if (shopId == null) {
//                    log.debug("WEB:. User has no shop");
//                    pageForward = Constant.ERROR_PAGE;
//                    return pageForward;
//                }
//
//                Calendar currentDate = Calendar.getInstance();
//                Calendar tenDayBefore = Calendar.getInstance();
//                tenDayBefore.add(Calendar.DATE, -10);
//
//                saleForm.setStartDate(DateTimeUtils.convertDateToString(tenDayBefore.getTime()));
//                saleForm.setEndDate(DateTimeUtils.convertDateToString(currentDate.getTime()));
//
//                session.setAttribute("shopId", shopId);
//
//                /** List of telecom service */
//                TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
//                telecomServiceDAO.setSession(getSession());
//                List<TelecomService> telecomList = telecomServiceDAO.findByProperty(TelecomServiceDAO.STATUS, 1L);
//                session.setAttribute("telecomList", telecomList);
//
//                /** List of pay method */
//                AppParamsDAO appParamsDAO = new AppParamsDAO();
//                appParamsDAO.setSession(getSession());
//                List payMethodList = appParamsDAO.findAppParamsList("PAY_METHOD", Constant.STATUS_USE.toString());
//                session.setAttribute("listPayMethod", payMethodList);
//
//                //Status List
//                List statusList = appParamsDAO.findAppParamsList("SALE_TRANS.STATUS", Constant.STATUS_USE.toString());
//                session.setAttribute("statusList", statusList);
//
//                /** Reset list to null */
//                session.setAttribute("saleTransList", null);
//                session.setAttribute("saleTransDetailList", null);
//                session.setAttribute("invoiceUsedId", null);
//
//                SaleTransInvoiceViewHelper viewHelper = new SaleTransInvoiceViewHelper();
//
//
//                /** Go to interface to create invoice for collaboration people */
//                String interfaceType = (String) req.getParameter("interfaceType");
//                if (interfaceType != null && !interfaceType.trim().equals("")) {
//                    viewHelper.setInterfaceType(new Long(interfaceType));
//                }
//                if (interfaceType != null && !interfaceType.trim().equals("") &&
//                        (interfaceType.equals(SALE_TRANS_INVOICE_COLLABORATOR_INTERFACE) || interfaceType.equals(SALE_TRANS_INVOICE_RETAIL_INTERFACE))) {
//                    log.debug("WEB. Go to interface create invoice for sale trans collaboration");
//                    StaffDAO staffDAO = new StaffDAO();
//                    staffDAO.setSession(getSession());
//                    List collStaffList = staffDAO.findAllCollaboratorOfShop(shopId);
//
//                    ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
//                    channelTypeDAO.setSession(getSession());
//
//                    List<ChannelType> channelTypeList = channelTypeDAO.findIsVTUnitActive(Constant.NOT_IS_VT_UNIT);
//
//                    session.setAttribute("channelTypeList", channelTypeList);
//                    session.setAttribute("collStaffList", collStaffList);
//                } else if (interfaceType != null && !interfaceType.trim().equals("") &&
//                        interfaceType.equals(SALE_TRANS_INVOICE_AGENT_INTERFACE)) {
//                    log.debug("WEB. Go to interface create invoice for agent");
//                    /** List of agent shop */
//                    shopDAO.setObjectTypeFilter(AGENT_OBJECT_TYPE);
//                    shopDAO.setIsVTUnitFilter(AGENT_IS_VT_UNIT);
//                    List<Shop> childShopList = shopDAO.findAgentShop(shopId, new Long(ACTIVE_STATUS));
//                    session.setAttribute("childShopList", childShopList);
//                }
//                if (interfaceType.equals(SALE_TRANS_INVOICE_COLLABORATOR_INTERFACE)) {
//                    //Lập hóa đơn thay
//                    pageForward = "InvoiceCollaborator";
//                }
//                if (interfaceType.equals(SALE_TRANS_INVOICE_RETAIL_INTERFACE)) {
//                    //Lập hóa đơn bán lẻ và làm dịch vụ
//                    pageForward = "InvoiceRetail";
//                }
//                if (interfaceType.equals(SALE_TRANS_INVOICE_AGENT_INTERFACE)) {
//                    //Lập hóa đơn cho đại lý
//                    pageForward = "InvoiceAgent";
//                }
//                session.setAttribute(SaleTransInvoiceViewHelper.SALE_TRANS_INVOICE, viewHelper);
//
//
//                 //MrSun
//
//                /** List of reason sale */
//                ReasonDAO reasonDAO = new ReasonDAO();
//                reasonDAO.setSession(getSession());
//                String[] propertyValue;
//                String[] fieldName = new String[0];
//
//                List reasonList;
//                if (viewHelper.getInterfaceType() != null && viewHelper.getInterfaceType() == 1){
//                    /** List of reason sale */
//                    propertyValue = new String[1];
//                    propertyValue[0] = REASON_SALE_WHOS;
//                    reasonList = reasonDAO.findByPropertyWithStatus(fieldName, "REASON_TYPE", propertyValue, Constant.STATUS_ACTIVE);
//                    session.setAttribute(REASON_SALE_LIST, reasonList);
//
//                    /** List of reason invoice */
//                    propertyValue = new String[1];
//                    propertyValue[0] = REASON_INVOICE_WHOS;
//                    reasonList = reasonDAO.findByPropertyWithStatus(fieldName, "REASON_TYPE", propertyValue, Constant.STATUS_ACTIVE);
//                    session.setAttribute(REASON_INVOICE_LIST, reasonList);
//                }
//                else{
//                    /** List of reason invoice */
//                    propertyValue = new String[4];
//                    propertyValue[0] = REASON_SALE_RETAIL;
//                    propertyValue[1] = REASON_SALE_COL;
//                    propertyValue[2] = REASON_SALE_INTERNAL;
//                    propertyValue[3] = REASON_SALE_PROMO;
//                    //reasonList = reasonDAO.findByPropertyWithStatus("REASON_TYPE", propertyValue, ACTIVE_STATUS);
//                    //session.setAttribute(REASON_SALE_LIST, reasonList);
//
//                    /** List of reason sale */
//                    propertyValue = new String[4];
//                    propertyValue[0] = REASON_INVOICE_RETAIL;
//                    propertyValue[1] = REASON_INVOICE_COL;
//                    propertyValue[2] = REASON_INVOICE_INTERNAL;
//                    propertyValue[3] = REASON_INVOICE_PROMO;
//
//                    //reasonList = reasonDAO.findByPropertyWithStatus("REASON_TYPE", propertyValue, ACTIVE_STATUS);
//                    //session.setAttribute(REASON_INVOICE_LIST, reasonList);
//                }
//
//                //Danh sach loai giao dich
//                SaleInvoiceTypeDAO saleInvoiceTypeDAO = new SaleInvoiceTypeDAO();
//                saleInvoiceTypeDAO.setSession(getSession());
//                List saleInvoiceTypeList = saleInvoiceTypeDAO.findByProperty(SALE_INVOICE_TYPE_GROUP,Constant.SALE_INVOICE_GROUP_RETAIL);
//
//                session.setAttribute(SALE_INVOICE_TYPE_LIST, saleInvoiceTypeList);
//                //MrSun
//
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//
//        log.info("End method prepareInvoiceToWholeSale of SaleDAO");
//
//        return pageForward;
//    }
//
//    /**
//     * Search for trans to create invoice
//     * List of trans will be displayed
//     * @return forward page
//     * @throws java.lang.Exception
//     */
//    public String searchSaleTrans() throws Exception {
//        log.info("Begin");
//        String pageForward = INVOICE_TO_WHOLE_SALE;
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//
//        if (userToken == null) {
//            log.info("WEB:. Session time out");
//            pageForward = Constant.SESSION_TIME_OUT;
//            return pageForward;
//        }
//        try {
//
//            String transStatus = "";
//            if (saleForm.getTransStatus() != null)
//                transStatus = saleForm.getTransStatus();
//
//            /** Get shop ID-Incase of not search for select sale trans */
//            Long shopId = (Long) session.getAttribute("shopId");
//
//            if (shopId == null) {
//                ShopDAO shopDAO = new ShopDAO();
//                shopDAO.setSession(getSession());
//                shopId = shopDAO.getShopIDByStaffID(userToken.getUserID());
//                if (shopId == null) {
//                    log.debug("WEB:. User has no shop");
//                    pageForward = Constant.ERROR_PAGE;
//                    return pageForward;
//                }
//                session.setAttribute("shopId", shopId);
//            }
//
//            SaleTransDAO saleTransDAO = new SaleTransDAO();
//            saleTransDAO.setSession(getSession());
//
//            setSearchSaleTransCondition(saleForm, saleTransDAO);
//
//            SaleTransInvoiceViewHelper viewHelper = getViewHelper();
//
//            if (viewHelper.getInterfaceType() != null &&
//                    viewHelper.getInterfaceType().equals(new Long(SALE_TRANS_INVOICE_AGENT_INTERFACE))) {
//
//                /** Lap hoa don ban cho Dai ly */
//                saleTransDAO.setStockTransAgentOwnerTypeFilter(STOCK_TRANS_AGENT_OWNER_TYPE);
//                String[] saleTransType = new String[1];
//                saleTransType[0] = SALE_TRANS_TYPE_AGENT;
//                saleTransDAO.setSaleTransTypeFilter(saleTransType);
//
//            } else if (viewHelper.getInterfaceType() != null &&
//                    viewHelper.getInterfaceType().equals(new Long(SALE_TRANS_INVOICE_RETAIL_INTERFACE))) {
//
//                /** Lap hoa don ban cho Cong tac vien, diem ban, ban le */
//                if (saleTransDAO.getSaleTransTypeFilter() == null) {
//                    SaleInvoiceTypeDAO saleInvoiceTypeDAO = new SaleInvoiceTypeDAO();
//                    List<SaleInvoiceType> saleInvoiceType = saleInvoiceTypeDAO.findByProperty(SALE_INVOICE_TYPE_GROUP, new Long(SALE_TRANS_INVOICE_RETAIL_INTERFACE));
//                    if (saleInvoiceType != null && saleInvoiceType.size()>0){
//                        String[] tmp = new String[saleInvoiceType.size()];
//                        for(int i = 0; i < saleInvoiceType.size(); i++)
//                            tmp[i] = saleInvoiceType.get(i).getSaleInvoiceTypeId().toString();
//                        saleTransDAO.setSaleTransTypeFilter(tmp);
//                    }
//                }
//            } else if (viewHelper.getInterfaceType() != null &&
//                    viewHelper.getInterfaceType().equals(new Long(SALE_TRANS_INVOICE_COLLABORATOR_INTERFACE))) {
//
//                /** Lap hoa don thay Cong tac vien */
//                //saleTransDAO.setStockTransStaffOwnerTypeFilter(STOCK_TRANS_STAFF_OWNER_TYPE);
//                if (StringUtils.validString(saleForm.getChannelTypeId())) {
//                    Long channelTypeId = new Long(saleForm.getChannelTypeId());
//                    ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
//                    channelTypeDAO.setSession(getSession());
//
//                    ChannelType channelType = channelTypeDAO.findById(channelTypeId);
//
//                    if (channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
//                        log.info("In process for staff object type");
//
//                        String[] saleTransType = new String[1];
//                        saleTransType[0] = SALE_TRANS_TYPE_RETAIL;
//                        saleTransDAO.setSaleTransTypeFilter(saleTransType);
//                        saleTransDAO.setSaleTransForCollaborator(Boolean.TRUE);
//                        saleTransDAO.setChannelTypeIdFilter(channelTypeId);
//                    } else if (channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
//                        log.info("In process for shop object type");
//                        saleTransDAO.setSaleTransForSubShop(Boolean.TRUE);
//                        viewHelper.setSaleTransForSubShop(Boolean.TRUE);//Reserve for research selected sale trans
//                        Long salerId = saleForm.getSalerId();
//
//                        if (salerId == null) {
//                            ShopDAO shopDAO = new ShopDAO();
//                            shopDAO.setSession(getSession());
//
//                            shopDAO.setObjectTypeFilter(AGENT_OBJECT_TYPE);
//                            shopDAO.setIsVTUnitFilter(AGENT_IS_VT_UNIT);
//                            List<Shop> childShopList = shopDAO.findAgentShop(shopId, new Long(ACTIVE_STATUS));
//                            if (childShopList != null && childShopList.size() != 0) {
//                                Long[] subShopId = new Long[childShopList.size()];
//                                for (int i = 0; i < childShopList.size(); i++) {
//                                    Shop shopTemp = (Shop) childShopList.get(i);
//                                    subShopId[i] = shopTemp.getShopId();
//                                }
//                                saleTransDAO.setSubShopIdFilter(subShopId);
//                            }
//                        }
//                    }
//                } else {
//                    addActionError("Bạn chưa chọn loại đối tượng để lập thay hóa đơn");
//                    return pageForward;
//                }
//            }
//
////            List<SaleTransBean> saleTransList = saleTransDAO.searchSaleTrans(shopId, SALE_TRANS_CREATED_STATUS);
//            System.out.print("Tim theo TT: "+transStatus);
//            List<SaleTransBean> saleTransList = saleTransDAO.searchSaleTrans(shopId, transStatus);
//
//            session.setAttribute("saleTransList", saleTransList);
//            //Clear danh sách hàng hóa trong giao dịch
//            session.setAttribute("saleTransDetailList", null);
//            if (saleTransList != null && saleTransList.size() > 0) {
//                req.setAttribute("returnMsg", "saleInvoice.success.searchSaleTrans");
//                List listParamValue = new ArrayList();
//                listParamValue.add(saleTransList.size());
//                req.setAttribute("returnMsgValue", listParamValue);
//            } else {
//                req.setAttribute("returnMsg", "saleInvoice.ụnsuccess.searchSaleTrans");
//            }
//
//
//        } catch (Exception e) {
//            log.error("WEB: " + e.toString());
//            throw e;
//        }
//
//        log.info("End");
//        return pageForward;
//    }
//
//    /**
//     * Display trans detail infor
//     * List of good in selected trans will be displayed
//     * @return forward page
//     * @throws java.lang.Exception
//     */
//    public String gotoSaleTransDetail() throws Exception {
//        log.info("Begin");
//        String pageForward = SALE_TRANS_DETAIL;
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//
//        if (userToken == null) {
//            log.info("WEB:. Session time out");
//            pageForward = Constant.SESSION_TIME_OUT;
//            return pageForward;
//        }
//        try {
//
//            SaleTransDetailDAO saleTransDetailDAO = new SaleTransDetailDAO();
//            saleTransDetailDAO.setSession(getSession());
//
//            String saleTransDetailIdTemp = req.getParameter("saleTransDetailId");
//            if (saleTransDetailIdTemp == null || saleTransDetailIdTemp.trim().equals("")) {
//                log.debug("WEB:. saleTransDetailId was missing ");
//                pageForward = Constant.ERROR_PAGE;
//                return pageForward;
//            }
//
//            Long saleTransDetailId = new Long(saleTransDetailIdTemp);
//
//            List<SaleTransDetailBean> saleTransDetailList = saleTransDetailDAO.selectSaleTransDetail(saleTransDetailId);
//
//            session.setAttribute("saleTransDetailList", saleTransDetailList);
//
//
//
//
//        }catch(Exception e){
//           log.error("WEB: " + e.toString());
//           throw e;
//        }
//
//        log.info("End");
//        return pageForward;
//    }
//
//    //MrSun
//
//    /**
//     * Go to interface to create invoice
//     * Prepare infor to display in the interface include:
//     *      - Staff name, Staff's shop name, Date create
//     *      - List of agent to be choosen
//     *      - List of pay method to be choosen
//     *      - List of reason to be choosen
//     *      - List of serial invoice to be choosen
//     * @return forward page
//     * @throws java.lang.Exception
//     */
//    public String gotoCreateSaleTransInvoice() throws Exception {
//        log.info("Begin");
//        String pageForward = TO_CREATE_SALE_TRANS_INVOICE;
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        session.setAttribute("saleTransDetailList", null);
//
//        if (userToken == null) {
//            log.info("WEB:. Session time out");
//            pageForward = Constant.SESSION_TIME_OUT;
//            return pageForward;
//        }
//        try {
//            //MrSun
//            //Lay Reason va payMethod
//            Long tmpseasonId = 0L;
//            String tmppayMethod = "";
//            String tmpTIN = "";
//            Long tmpsaleTransType = 0L;
//            if (saleForm.getSaleTransType() != null)
//                tmpsaleTransType = saleForm.getSaleTransType();
//            //MrSun
//
//
//
//            //Kiem tra HD co cung loai hay khong?
//            //Neu la giao dich ban le + ban DL + ban CTV + Lam DV : HD thu tien: 1
//            //Neu la giao dich noi bo: 2
//            //Neu la  giao dich ban hang khuyen mai: 3
//
//
//
//
//
//
//            /** Get shop ID-Incase of not search for select sale trans */
//            Long shopId = (Long) session.getAttribute("shopId");
//
//            if (shopId == null) {
//                ShopDAO shopDAO = new ShopDAO();
//                shopDAO.setSession(getSession());
//                shopId = shopDAO.getShopIDByStaffID(userToken.getUserID());
//                if (shopId == null) {
//                    log.debug("WEB:. User has no shop");
//                    pageForward = Constant.ERROR_PAGE;
//                    return pageForward;
//                }
//                session.setAttribute("shopId", shopId);
//            }
//
//            //Kiem tra co cung loai giao dich hay khong?
//            if (!checkSameInvoiceType()){
//                pageForward = INVOICE_TO_WHOLE_SALE;
//                log.info("End");
//                req.setAttribute("returnMsg", "createInvoice.notSameInvoiceType");
//                return pageForward;
//            }
//
//             //Kiem tra voi nhung giao dich tru kho ngay da nhap Serial chua?
//            if (!checkNotUpdateSerial()){
//                pageForward = INVOICE_TO_WHOLE_SALE;
//                log.info("End");
//                req.setAttribute("returnMsg", "createInvoice.notUpdateSerial");
//                return pageForward;
//            }
//
//            SaleTransInvoiceViewHelper viewHelper = getViewHelper();
//            /** Go to interface to create invoice directly */
//            //MrSun
//            String interfaceType = (String) req.getParameter("interfaceType");
//            //MrSun
//
//            if (interfaceType != null && !interfaceType.trim().equals("")) {
//                viewHelper.setInterfaceType(new Long(interfaceType));
//
//                //MrSun
//                //Nhay den trang lap hoa don khong tu giao dich
//                pageForward = CREATE_INVOICE_NO_SALE;
//                //MrSun
//            }
//            if (interfaceType != null && !interfaceType.trim().equals("") &&
//                    interfaceType.equals(SALE_TRANS_DIRECT_CREATE_INVOICE_INTERFACE)) {
//                log.info("Go to create invoice directly");
//                prepareCreateSaleInvoiceInfo(shopId, userToken.getUserID());
//            } else {
//                log.info("Go to select sale trans for create invoice");
//                boolean selectedSaleTrans = checkSelectedSaleTrans();
//                if (!selectedSaleTrans) {
//                    req.setAttribute("returnMsg", "saleInvoice.error.requiredSaleTransSelected");
//                    pageForward = INVOICE_TO_WHOLE_SALE;
//                    log.info("End");
//                    return pageForward;
//                }
//                String[] saleTransIdTemp = saleForm.getSaleTransId();
//                SaleTransDAO saleTransDAO = new SaleTransDAO();
//                saleTransDAO.setSession(getSession());
//                Boolean isPromotion = false;
//                 for(int i = 0; i < saleTransIdTemp.length; i ++){
//                    Long saleTransId = Long.parseLong(saleTransIdTemp[i]);
//                    SaleTrans saleTrans = saleTransDAO.findById(saleTransId);
//
//                    //MrSun
//                    if (tmpseasonId == null || tmpseasonId==0L){
//                        tmpseasonId = saleTrans.getReasonId();
//                        tmppayMethod = saleTrans.getPayMethod();
//                        tmpTIN = saleTrans.getTin();
//                    }
//                    else
//                    {
//                        tmpseasonId = -1L;
//                    }
//                    //MrSun
//
//                    if(saleTrans.getSaleTransType().equals(SALE_TRANS_TYPE_PROMOTION)){
//                        isPromotion = true;
//                        break;
//                    }
//                }
////                if (isPromotion) {
////                    for (int i = 0; i < saleTransIdTemp.length; i++) {
////                        Long saleTransId = Long.parseLong(saleTransIdTemp[i]);
////                        SaleTrans saleTrans = saleTransDAO.findById(saleTransId);
////                        if (!saleTrans.getSaleTransType().equals(SALE_TRANS_TYPE_PROMOTION)) {
////                            req.setAttribute("returnMsg", "saleInvoice.error.invalidPromotion");
////                            pageForward = INVOICE_TO_WHOLE_SALE;
////                            log.info("End");
////                            return pageForward;
////                        }
////                    }
////                }
//
//                if (viewHelper.getInterfaceType() != null && viewHelper.getInterfaceType().toString().equals(SALE_TRANS_INVOICE_AGENT_INTERFACE)) {
//                    boolean validSelectedSaleTrans = checkSameAgentOwnerSaleTrans();
//                    if (!validSelectedSaleTrans) {
//                        pageForward = INVOICE_TO_WHOLE_SALE;
//                        log.info("End");
//                        return pageForward;
//                    }
//                }
//                prepareAmountAndTaxInfo(shopId);
//                prepareCreateSaleInvoiceInfo(shopId, userToken.getUserID());
//            }
//            setSearchConditionSession(false);
//            /** Reset infor from previous action */
//            //viewHelper.resetViewHelper();
//            if (viewHelper.getInterfaceType() != null && !(viewHelper.getInterfaceType().toString().equals(SALE_TRANS_DIRECT_CREATE_INVOICE_INTERFACE))) {
//                saleForm.resetSaleForm();
//                setSessionToTextField(viewHelper);
//            }
//
//            if (viewHelper.getInterfaceType() != null && (viewHelper.getInterfaceType().toString().equals(SALE_TRANS_DIRECT_CREATE_INVOICE_INTERFACE))) {
//                viewHelper.resetAmountInfo();
//                saleForm = new SaleForm();
//                saleForm.setShopName(viewHelper.getShopName());
//                saleForm.setStaffName(viewHelper.getStaffName());
//                saleForm.setStartDate(viewHelper.getCreateDate());
//
//
//
//                session.setAttribute("invoiceUsedId", null);
//            }
//
//
//
//        //MrSun
//        if (tmpseasonId != null){
//            if (tmpseasonId>0L){
//                saleForm.setPayMethod(tmppayMethod);
//                saleForm.setTin(tmpTIN);
//            }
//        }
//
//        //MrSun - Begin
//
//
//
//
//        ReasonDAO reasonDAO = new ReasonDAO();
//        reasonDAO.setSession(getSession());
//        String[] propertyValue = new String[1];
//        List reasonList;
//        String[] fieldName = new String[0];
//
//        if (viewHelper.getInterfaceType() != null && viewHelper.getInterfaceType().compareTo(1L)==0){
//            propertyValue = new String[1];
//            propertyValue[0] = REASON_INVOICE_WHOS;
//            reasonList = reasonDAO.findByPropertyWithStatus(fieldName, "REASON_TYPE", propertyValue, Constant.STATUS_USE);
//            session.setAttribute(REASON_INVOICE_LIST, reasonList);
//        }
//        else{
//            //Neu chon 1 loai giao dich
//            if (tmpsaleTransType>0L){
//                //Danh sach nhom ly do
//                SaleInvoiceTypeDAO saleInvoiceTypeDAO = new SaleInvoiceTypeDAO();
//                List<SaleInvoiceType> lstTemp = saleInvoiceTypeDAO.findByProperty("saleTransType",tmpsaleTransType);
//                if (!(lstTemp == null || lstTemp.size() <=0)){
//                    //Ten nhom ly do
//                    SaleInvoiceType saleInvoiceType = lstTemp.get(0);
//
//                    if (saleInvoiceType.getInvoiceGroup() != null){
//                        lstTemp = saleInvoiceTypeDAO.findByProperty("invoiceGroup",saleInvoiceType.getInvoiceGroup());
//                        //Lay nhung ly do lap hoa don co cung type
//                        if (!(lstTemp == null || lstTemp.size() <=0)){
//                            propertyValue = new String[lstTemp.size()];
//                            for(int i = 0; i < lstTemp.size(); i++){
//                                saleInvoiceType = lstTemp.get(i);
//                                if (saleInvoiceType.getCode() != null)
//                                    propertyValue[i] = saleInvoiceType.getReasonInvoiceGroupCode();
//                                else
//                                    propertyValue[i] = "";
//                            }
//                        }
//                    }
//                }
//            }
//            //Khong chon loai giao dich
//            else{
//                propertyValue = new String[4];
//                propertyValue[0] = REASON_INVOICE_RETAIL;
//                propertyValue[1] = REASON_INVOICE_COL;
//                propertyValue[2] = REASON_INVOICE_INTERNAL;
//                propertyValue[3] = REASON_INVOICE_PROMO;
//            }
//
//            reasonList = reasonDAO.findByPropertyWithStatus(fieldName,"REASON_TYPE", propertyValue, Constant.STATUS_USE);
//            session.setAttribute(REASON_INVOICE_LIST, reasonList);
//        }
//        //MrSun - Finish
//
//        } catch (Exception e) {
//            log.error("WEB: " + e.toString());
//            throw e;
//        }
//        log.info("End");
//        return pageForward;
//    }
//
//    private void setSessionToTextField(SaleTransInvoiceViewHelper viewHelper) {
//        saleForm.setShopName(viewHelper.getShopName());
//        saleForm.setStaffName(viewHelper.getStaffName());
//        saleForm.setStartDate(viewHelper.getCreateDate());
//        saleForm.setAgentName(viewHelper.getAgentName());
//        saleForm.setAgentAddress(viewHelper.getAgentAddress());
//        saleForm.setAgentTin(viewHelper.getAgentTin());
//        saleForm.setAgentAccount(viewHelper.getAgentAccount());
//        saleForm.setAgentId(viewHelper.getAgentId());
//        saleForm.setCustName(viewHelper.getCustName());
//
//        saleForm.setCompany(viewHelper.getCompany());
//        saleForm.setAddress(viewHelper.getAddress());
//        saleForm.setTin(viewHelper.getTin());
//        saleForm.setAccount(viewHelper.getAccount());
//        saleForm.setPayMethod(viewHelper.getPayMethodCode());
//        if (viewHelper.getReasonId() != null) {
//            saleForm.setReasonId(String.valueOf(viewHelper.getReasonId()));
//        }
//        if (viewHelper.getAmountNotTax() != null) {
//            saleForm.setAmount(Long.parseLong(viewHelper.getAmountNotTax()));
//        }
//        if (viewHelper.getTax() != null) {
//            saleForm.setTax(Long.parseLong(viewHelper.getTax()));
//        }
//        saleForm.setDiscount(viewHelper.getDiscount());
//        saleForm.setPromotion(viewHelper.getPromotion());
//        if (viewHelper.getFinalAmount() != null) {
//            saleForm.setFinalAmount(Long.parseLong(viewHelper.getFinalAmount()));
//        }
//
//        saleForm.setNote(viewHelper.getNote());
//    }
//
//    private void setSearchConditionSession(Boolean isClear) {
//        getReqSession();
//        if (isClear) {
//            session.removeAttribute("custName");
//            session.removeAttribute("startDate");
//            session.removeAttribute("endDate");
//            session.removeAttribute("saleTransType");
//            session.removeAttribute("payMethod");
//            session.removeAttribute("telecomServiceId");
//            session.removeAttribute("reasonId");
//            session.removeAttribute("staffId");
//            session.removeAttribute("channelTypeId");
//            session.removeAttribute("salerId");
//            session.removeAttribute("agentId");
//            session.removeAttribute("transStatus");
//        } else {
//            if (getSaleForm() != null) {
//
//
//                session.setAttribute("custName", saleForm.getCustName());
//                session.setAttribute("startDate", saleForm.getStartDate());
//                session.setAttribute("endDate", saleForm.getEndDate());
//                session.setAttribute("saleTransType", saleForm.getSaleTransType());
//                session.setAttribute("payMethod", saleForm.getPayMethod());
//                session.setAttribute("telecomServiceId", saleForm.getTelecomServiceId());
//                session.setAttribute("reasonId", saleForm.getReasonId());
//                session.setAttribute("staffId", saleForm.getStaffId());
//                session.setAttribute("channelTypeId", saleForm.getChannelTypeId());
//                session.setAttribute("salerId", saleForm.getSalerId());
//                session.setAttribute("agentId", saleForm.getAgentId());
//                session.setAttribute("transStatus", saleForm.getTransStatus());
//            }
//        }
//    }
//
//    /**
//     * Create invoice for trans
//     * @return forward page
//     * @throws java.lang.Exception
//     */
//    public String createSaleTransInvoice() throws Exception {
//        log.info("Begin");
//        String pageForward = TO_CREATE_SALE_TRANS_INVOICE;
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        SaleTransInvoiceViewHelper viewHelper = getViewHelper();
//        if (viewHelper.getInterfaceType() != null && viewHelper.getInterfaceType().toString().equals(SALE_TRANS_DIRECT_CREATE_INVOICE_INTERFACE)) {
//
//            if (viewHelper.getInterfaceType().toString().equals(SALE_TRANS_INVOICE_RETAIL_INTERFACE)) {
//                if (saleForm.getCustName() == null || saleForm.getCustName().equals("")) {
//                    req.setAttribute("returnMsg", "saleInvoice.error.requiredCustName");
//                    pageForward = "createSaleTransInvoice";
//                    return pageForward;
//                }
//                if (saleForm.getCompany() == null || saleForm.getCompany().equals("")) {
//                    req.setAttribute("returnMsg", "saleInvoice.error.requiredCompanyName");
//                    pageForward = "createSaleTransInvoice";
//                    return pageForward;
//                }
//                if (saleForm.getAddress() == null || saleForm.getAddress().equals("")) {
//                    req.setAttribute("returnMsg", "saleInvoice.error.requiredAddress");
//                    pageForward = "createSaleTransInvoice";
//                    return pageForward;
//                }
//                if (saleForm.getTin() == null || saleForm.getTin().equals("")) {
//                    req.setAttribute("returnMsg", "saleInvoice.error.requiredTin");
//                    pageForward = "createSaleTransInvoice";
//                    return pageForward;
//                }
//            }
//            if (saleForm.getAmount() == null) {
//                req.setAttribute("returnMsg", "saleInvoice.error.requiredAmount");
//                pageForward = "createSaleTransInvoice";
//                return pageForward;
//            }
//
//            if (saleForm.getTax() == null) {
//                req.setAttribute("returnMsg", "saleInvoice.error.requiredTax");
//                pageForward = "createSaleTransInvoice";
//                return pageForward;
//            }
//            if (viewHelper.getInterfaceType() != null &&
//                    !(viewHelper.getInterfaceType().toString().equals(SALE_TRANS_DIRECT_CREATE_INVOICE_INTERFACE)) && saleForm.getFinalAmount() == null) {
//                req.setAttribute("returnMsg", "saleInvoice.error.requiredFinalAmount");
//                pageForward = "createSaleTransInvoice";
//                return pageForward;
//            }
//        }
//
//        if (saleForm.getBillSerial() == null || saleForm.getBillSerial().equals("")) {
//            req.setAttribute("returnMsg", "saleInvoice.error.requiredBillSerial");
//            pageForward = "createSaleTransInvoice";
//            return pageForward;
//        }
//
//        String serialCode = saleForm.getBillSerial();
//
//         BookTypeDAO bookTypeDAO = new BookTypeDAO();
//        //bookTypeDAO.setSession(this.getSession());
//        Long rowCount = 0L;
//        SaleTransDetailDAO saleTransDetailDAO = new SaleTransDetailDAO();
//        saleTransDetailDAO.setSession(getSession());
//        List<BookType> listBookType = bookTypeDAO.findByProperty("serialCode", serialCode);
//        if (listBookType.get(0).getMaxRow() != null) {
//            Long maxRow = listBookType.get(0).getMaxRow();
//            List<SaleTransBean> saleTransList = (List<SaleTransBean>) session.getAttribute("saleTransSelectedList");
//            for(SaleTransBean saleTransDetail : saleTransList){
//                List<SaleTransDetailBean> saleTransDetailList = saleTransDetailDAO.selectSaleTransDetail(saleTransDetail.getSaleTransId());
//                rowCount += saleTransDetailList.size();
//            }
//            if(rowCount > maxRow){
//                req.setAttribute("returnMsg", "saleInvoice.error.invalidInvoice");
//                List listParamValue = new ArrayList();
//                listParamValue.add(rowCount);
//                listParamValue.add(serialCode);
//                listParamValue.add(maxRow);
//                req.setAttribute("returnMsgValue", listParamValue);
//                pageForward = "createSaleTransInvoice";
//                return pageForward;
//            }
//        }
//
//        if (saleForm.getPayMethod() == null || saleForm.getPayMethod().equals("")) {
//            req.setAttribute("returnMsg", "saleInvoice.error.requiredPayMethod");
//            pageForward = "createSaleTransInvoice";
//            return pageForward;
//        }
//        if (saleForm.getReasonId() == null || saleForm.getReasonId().equals("")) {
//            req.setAttribute("returnMsg", "saleInvoice.error.requiredReason");
//            pageForward = "createSaleTransInvoice";
//            return pageForward;
//        }
//
//
//        if (userToken == null) {
//            log.info("WEB:. Session time out");
//            pageForward = Constant.SESSION_TIME_OUT;
//            return pageForward;
//        }
//        try {
//            /** Get shop ID */
//            Long shopId = (Long) session.getAttribute("shopId");
//            if (shopId == null) {
//                log.info("WEB:. Session time out");
//                pageForward = Constant.SESSION_TIME_OUT;
//                return pageForward;
//            }
//
//
//            if (viewHelper.isSelectedSaleTrans()) {
//                log.debug("In selected sale trans");
//                updateSaleTrans(shopId);
//            } else {
//                log.debug("Not selected sale trans");
//                /** Not selected invoiceSaleTrans */
//                InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
//                invoiceListDAO.setSession(getSession());
//                Long selectedInvoiceListId = (Long) session.getAttribute("selectedInvoiceListId");
//
//                if (selectedInvoiceListId == null) {
//                    log.info("WEB. Session time out. selectedInvoiceListId is missing");
//                    return Constant.ERROR_PAGE;
//                }
//                InvoiceList invoiceList = invoiceListDAO.findById(selectedInvoiceListId);
//                Long invoiceUsedId = insertInvoiceUsed(shopId, invoiceList);
//                //session.setAttribute("shopId", shopId);
//                session.setAttribute("invoiceUsedId", invoiceUsedId);
//            }
//            /** Reset infor */
//            saleTransId = null;
//            /** Return page */
//            if (viewHelper.getInterfaceType() != null && !(viewHelper.getInterfaceType().toString().equals(SALE_TRANS_DIRECT_CREATE_INVOICE_INTERFACE))) {
//
//                req.setAttribute("returnMsg", "saleInvoice.success.createSaleTransInvoice");
//                pageForward = "createSaleTransInvoice";
//            }
//            viewHelper.resetViewHelper();
//            if (viewHelper.getInterfaceType() != null && (viewHelper.getInterfaceType().toString().equals(SALE_TRANS_DIRECT_CREATE_INVOICE_INTERFACE))) {
//                viewHelper.resetAmountInfo();
//                req.setAttribute("returnMsg", "saleInvoice.success.createSaleTransInvoice");
//                pageForward = "createSaleTransInvoice";
//            }
//            /** Update invoice list */
//            getSession().flush();//Update invoice list to database
//        } catch (Exception e) {
//            log.error("WEB: " + e.toString());
//            throw e;
//        }
//        log.info("End");
//        return pageForward;
//    }
//
//    public String printSaleTransInvoice() throws Exception {
//        String pageForward = "createSaleTransInvoice";
//        getReqSession();
//        try {
//            Long shopId = (Long) session.getAttribute("shopId");
//            Long invoiceUsedId = (Long) session.getAttribute("invoiceUsedId");
//
//            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//            InvoicePrinterDAO invoicePrinterDAO = new InvoicePrinterDAO();
//            String returnValue = invoicePrinterDAO.printSaleTransInvoice(invoiceUsedId,null);
//
//            if (returnValue.equals("2")) {
//                req.setAttribute("returnMsg", "saleInvoice.error.invalidPrinterParam");
//                pageForward = "createSaleTransInvoice";
//                return pageForward;
//            }
//            if (returnValue.equals("3")) {
//                req.setAttribute("returnMsg", "saleInvoice.error.requiredPageSize");
//                pageForward = "createSaleTransInvoice";
//                return pageForward;
//            }
//
//            saleForm.resetSaleForm();
//            saleForm.setExportUrl(returnValue);
//            saleForm.setExprotUrlRac(returnValue.replace(".pdf", "_rac.pdf"));
//            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
//            invoiceListDAO.setSession(getSession());
//
//            List<InvoiceList> invoiceSerialList = invoiceListDAO.findAllSerialInvoiceList(shopId, userToken.getUserID());
//
//            session.setAttribute("invoiceSerialList", invoiceSerialList);
//            req.setAttribute("returnMsg", "saleInvoice.success.printeSaleTransInvoice");
//            pageForward = "createSaleTransInvoice";
//        /** Reset list to null */
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            getSession().clear();
//        } finally {
//            session.setAttribute("saleTransList", null);
//            session.setAttribute("saleTransDetailList", null);
//
//            return pageForward;
//        }
//
//    }
//
//    /**
//     * When user click to chang invoice type
//     * @return forward page
//     * @throws java.lang.Exception
//     */
//    public String changeInvoiceType() throws Exception {
//        log.info("Begin");
//        String pageForward = TO_CREATE_SALE_TRANS_INVOICE;
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//
//        if (userToken == null) {
//            log.info("WEB:. Session time out");
//            pageForward = Constant.SESSION_TIME_OUT;
//            return pageForward;
//        }
//
//        try {
//            /** Get agent ID */
//            String invoiceTypeTemp = (String) req.getParameter("invoiceType");
//            SaleTransInvoiceViewHelper viewHelper = getViewHelper();
//            if (invoiceTypeTemp == null || invoiceTypeTemp.trim().equals("")) {
//                viewHelper.setInvoiceType(null);
//                return pageForward;
//            }
//
//            session.setAttribute("invoiceType", invoiceTypeTemp);
//            viewHelper.setInvoiceType(new Long(invoiceTypeTemp));
//
//            /** Reset Agent ID */
//            viewHelper.setAgentId(null);
//        } catch (Exception e) {
//            log.error("WEB: " + e.toString());
//            throw e;
//        }
//        log.info("End");
//        return pageForward;
//    }
//
//    /**
//     * When user click to chang agent, then display information about agent
//     * Agent infor was displayed include: Address, Tin, Account
//     * @return forward page
//     * @throws java.lang.Exception
//     */
//    public String changeAgent() throws Exception {
//        log.info("Begin");
//        String pageForward = TO_CREATE_SALE_TRANS_INVOICE;
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//
//        if (userToken == null) {
//            log.info("WEB:. Session time out");
//            pageForward = Constant.SESSION_TIME_OUT;
//            return pageForward;
//        }
//        try {
//            /** Get agent ID */
//            String agentIdTemp = (String) req.getParameter("agentId");
//
//            SaleTransInvoiceViewHelper viewHelper = getViewHelper();
//
//            if (agentIdTemp == null || agentIdTemp.trim().equals("")) {
//                viewHelper.setAgentId(null);
//                return pageForward;
//            }
//
//            Long agentId = new Long(agentIdTemp);
//            saleForm.setAgentId(agentId);
//
//            ShopDAO shopDAO = new ShopDAO();
//            shopDAO.setSession(getSession());
//            Shop agent = shopDAO.findById(agentId);
//
//            saleForm.setAgentAddress(agent.getAddress());
//            saleForm.setAgentTin(agent.getTin());
//            saleForm.setAgentAccount(agent.getAccount());
//
//        /** Save selected infor */
//        //saveViewHelper();
//        } catch (Exception e) {
//            log.error("WEB: " + e.toString());
//            throw e;
//        }
//
//        log.info("End");
//        return pageForward;
//    }
//
//    /**
//     * When user click to chose invoice serial, then display information invoice to be used
//     * Automatic chose invoice from invoice list of current shop
//     * @return forward page
//     * @throws java.lang.Exception
//     */
//    public String choseInvoice() throws Exception {
//        log.info("Begin");
//        String pageForward = TO_CREATE_SALE_TRANS_INVOICE;
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//
//        if (userToken == null) {
//            log.info("WEB:. Session time out");
//            pageForward = Constant.SESSION_TIME_OUT;
//            return pageForward;
//        }
//        try {
//            /** Get agent ID */
//            String invoiceSerial = (String) req.getParameter("invoiceSerial");
//
//            if (invoiceSerial == null || invoiceSerial.trim().equals("")) {
//                saleForm.setFromInvoice(null);
//                saleForm.setToInvoice(null);
//                saleForm.setCurrInvoiceNo(null);
//                return pageForward;
//            }
//
//            saleForm.setBillSerial(invoiceSerial);//Save selected infor
//
//            Long shopId = (Long) session.getAttribute("shopId");
//
//            if (shopId == null) {
//                log.info("WEB:. Session time out");
//                pageForward = Constant.SESSION_TIME_OUT;
//                return pageForward;
//            }
//
//            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
//            invoiceListDAO.setSession(getSession());
//            invoiceListDAO.setSerialNoFilter(invoiceSerial);
//
//            SaleTransInvoiceViewHelper viewHelper = getViewHelper();
//
//            List<InvoiceList> invoiceLists = invoiceListDAO.findInvoiceListBySerialNo(userToken.getShopId(), userToken.getUserID());
//            if (invoiceLists != null && invoiceLists.size() != 0) {
//                InvoiceList currUsedInvoiceList = null;
//                for (InvoiceList invoiceList : invoiceLists) {
//                    Long minFromInvoiceNo = 0L;
//                    if (invoiceList.getFromInvoice() != invoiceList.getCurrInvoiceNo()) {
//                        /** Tiep tuc su dung dai hoa don dang duoc su dung */
//                        currUsedInvoiceList = invoiceList;
//                        log.info("Continue using invoice list which was in used");
//                        break;
//                    } else {
//
//                        /** Truong hop dau tien */
//                        if (minFromInvoiceNo == 0) {
//                            currUsedInvoiceList = invoiceList;
//                            minFromInvoiceNo = invoiceList.getFromInvoice();
//                        } else {
//
//                            /** Khong chon ra dai hoa don co so hoa don bat dau nho nhat */
//                            if (invoiceList.getFromInvoice() < minFromInvoiceNo) {
//                                currUsedInvoiceList = invoiceList;
//                                minFromInvoiceNo = invoiceList.getFromInvoice();
//                            }
//                        }
//                    }
//                }
//
//
//                /** Hoa don hien tai la hoa don dang su dung, hoac co so hoa don bat dau nho nhat */
//                if (currUsedInvoiceList != null) {
//                    String InvoiceNo = currUsedInvoiceList.getSerialNo()
//                            + currUsedInvoiceList.getStrBlockNo()
//                            + currUsedInvoiceList.getStrCurrInvoiceNo();
//                    saleForm.setStrFromInvoice(currUsedInvoiceList.getStrFromInvoice());
//                    saleForm.setStrCurrInvoice(currUsedInvoiceList.getStrCurrInvoiceNo());
//                    saleForm.setStrToInvoice(currUsedInvoiceList.getStrToInvoice());
//                    saleForm.setInvoiceNo(InvoiceNo);
//                    session.setAttribute("selectedInvoiceListId", currUsedInvoiceList.getInvoiceListId());
//                }
//            }
//
//        /** Save selected infor */
//        //saveViewHelper();
//        } catch (Exception e) {
//            log.error("WEB: " + e.toString());
//            throw e;
//        }
//
//        log.info("End");
//        return pageForward;
//    }
//
//    public String back() {
//        log.info("Begin");
//        String pageForward = INVOICE_TO_WHOLE_SALE;
//        getReqSession();
//        session.setAttribute("invoiceUsedId", null);
//        try {
//            if (session.getAttribute("custName") != null) {
//                saleForm.setCustName((String) session.getAttribute("custName"));
//            }
//            if (session.getAttribute("startDate") != null) {
//                saleForm.setStartDate((String) session.getAttribute("startDate"));
//            }
//            if (session.getAttribute("endDate") != null) {
//                saleForm.setEndDate((String) session.getAttribute("endDate"));
//            }
//            if (session.getAttribute("saleTransType") != null) {
//                saleForm.setSaleTransType((Long) (session.getAttribute("saleTransType")));
//            }
//            if (session.getAttribute("payMethod") != null) {
//                saleForm.setPayMethod((String) session.getAttribute("payMethod"));
//            }
//            if (session.getAttribute("telecomServiceId") != null) {
//                saleForm.setTelecomServiceId((Long) session.getAttribute("telecomServiceId"));
//            }
//            if (session.getAttribute("reasonId") != null) {
//                saleForm.setReasonId((String) session.getAttribute("reasonId"));
//            }
//            if (session.getAttribute("staffId") != null) {
//                saleForm.setReasonId((String) session.getAttribute("staffId"));
//            }
//            if (session.getAttribute("channelTypeId") != null) {
//                saleForm.setChannelTypeId((String) session.getAttribute("channelTypeId"));
//            }
//            if (session.getAttribute("salerId") != null) {
//                saleForm.setSalerId((Long) session.getAttribute("salerId"));
//            }
//            if (session.getAttribute("agentId") != null) {
//                saleForm.setAgentId((Long) session.getAttribute("agentId"));
//            }
//            if (session.getAttribute("transStatus") != null) {
//                saleForm.setTransStatus((String) session.getAttribute("transStatus"));
//            }
//            //saleForm.resetSaleForm();
//            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
//            invoiceListDAO.setSession(getSession());
//            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//            Long shopId = (Long) session.getAttribute("shopId");
//            List<InvoiceList> invoiceSerialList = invoiceListDAO.findAllSerialInvoiceList(shopId, userToken.getUserID());
//            session.setAttribute("invoiceSerialList", invoiceSerialList);
//
//        //searchSaleTrans();
//        } catch (Exception ex) {
//            log.info(ex.toString());
//        }
//        log.info("End");
//        return pageForward;
//    }
//
//    public String pageNavigator() {
//        log.info("Begin");
//        String pageForward = SEARCH_SALE_TRANS;
//        log.info("End");
//        return pageForward;
//    }
//
//    /**
//     * Chang object type to display
//     * @return void
//     * @throws java.lang.Exception
//     */
//    public String changeObjectType() throws Exception {
//        log.info("Begin");
//        getReqSession();
//        String pageForward = INVOICE_TO_WHOLE_SALE;
//
//        String channelTypeIdTemp = req.getParameter("channelTypeId");
//        if (!StringUtils.validString(channelTypeIdTemp)) {
//            log.info("WEB. Channel type id is missing");
//            String[] header = {"", "--Chọn đối tượng--"};
//            List tmpList = new ArrayList();
//            tmpList.add(header);
//            listObject.put("salerIdChan", tmpList);
//            return "success";
//        }
//
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//
//        if (userToken == null) {
//            log.info("WEB:. Session time out");
//            pageForward = Constant.SESSION_TIME_OUT;
//            return pageForward;
//        }
//        try {
//            Long channelTypeId = new Long(channelTypeIdTemp);
//            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
//            channelTypeDAO.setSession(getSession());
//
//            ChannelType channelType = channelTypeDAO.findById(channelTypeId);
//
//            if (channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
//                log.info("In process for shop object type");
//                /** List of agent shop */
//                ShopDAO shopDAO = new ShopDAO();
//                shopDAO.setSession(getSession());
//                shopDAO.setObjectTypeFilter(AGENT_OBJECT_TYPE);
//                shopDAO.setIsVTUnitFilter(AGENT_IS_VT_UNIT);
//                shopDAO.setChannelTypeIdFilter(channelTypeId);
//                List childShopList = shopDAO.findNativeAgentShop(userToken.getShopId(), new Long(ACTIVE_STATUS));
//
//                String[] header = {"", "--Chọn đơn vị cấp dưới--"};
//                List tmpList = new ArrayList();
//                tmpList.add(header);
//                tmpList.addAll(childShopList);
//
//                listObject.put("salerIdChan", tmpList);
//
//            } else if (channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
//                log.info("In process for staff object type");
//                StaffDAO staffDAO = new StaffDAO();
//                staffDAO.setSession(getSession());
//                Long[] channelTypeFilter = new Long[1];
//                channelTypeFilter[0] = channelType.getChannelTypeId();
//                staffDAO.setChannelTypeIdFilter(channelTypeFilter);
//
//                List collStaffList = staffDAO.getNativeSaleCollaboratorAndAgent(userToken.getShopId());
//
//                String[] header = {"", "--Chọn cộng tác viên--"};
//                List tmpList = new ArrayList();
//                tmpList.add(header);
//                tmpList.addAll(collStaffList);
//
//                listObject.put("salerIdChan", tmpList);
//
//            }
//        } catch (Exception e) {
//            log.error("WEB: " + e.toString());
//            throw e;
//        }
//
//        log.info("End");
//        return "success";
//    }
//
//    /**
//     * Set search condition for trans
//     * @return void
//     * @throws java.lang.Exception
//     */
//    private void setSearchSaleTransCondition(SaleForm saleForm, SaleTransDAO dao)
//            throws Exception {
//        log.info("Begin");
//        if (saleForm.getCustName() != null &&
//                !saleForm.getCustName().trim().equals("")) {
//            dao.setCustNameFilter(saleForm.getCustName().trim());
//        }
//        if (saleForm.getStartDate() != null &&
//                !saleForm.getStartDate().trim().equals("")) {
//            Date date = DateTimeUtils.convertStringToDate(saleForm.getStartDate());
//            dao.setStartDate(date);
//        }
//        if (saleForm.getEndDate() != null &&
//                !saleForm.getEndDate().trim().equals("")) {
//            Date date = DateTimeUtils.convertStringToDate(saleForm.getEndDate());
//            dao.setEndDate(date);
//        }
//        if (saleForm.getTelecomServiceId() != null) {
//            dao.setTelecomServiceId(saleForm.getTelecomServiceId());
//        }
//        if (saleForm.getSaleTransType() != null) {
//            String[] saleTransType = new String[1];
//            saleTransType[0] = saleForm.getSaleTransType().toString();
//            dao.setSaleTransTypeFilter(saleTransType);
//        }
//        if (saleForm.getPayMethod() != null &&
//                !saleForm.getPayMethod().trim().equals("")) {
//            dao.setPayMethodFilter(saleForm.getPayMethod());
//        }
//        if (saleForm.getReasonId() != null &&
//                !saleForm.getReasonId().trim().equals("")) {
//            dao.setReasonIdFilter(saleForm.getReasonId());
//        }
//        if (saleForm.getTransStatus() != null && !saleForm.getTransStatus().trim().equals("")) {
//            dao.setTransStatusFilter(Long.parseLong(saleForm.getTransStatus()));
//        }
//        if (saleForm.getStaffId() != null) {
//            dao.setStockTransStaffOwnerTypeFilter(STOCK_TRANS_STAFF_OWNER_TYPE);
//            dao.setStaffIdFilter(saleForm.getStaffId());
//        }
//        if (saleForm.getAgentId() != null) {
//            dao.setStockTransAgentOwnerTypeFilter(STOCK_TRANS_AGENT_OWNER_TYPE);
//            dao.setAgentIdFilter(saleForm.getAgentId());
//        }
//        if (StringUtils.validString(saleForm.getChannelTypeId())) {
//            Long channelTypeId = new Long(saleForm.getChannelTypeId());
//            if (channelTypeId != null) {
//                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
//                channelTypeDAO.setSession(getSession());
//
//                ChannelType channelType = channelTypeDAO.findById(channelTypeId);
//
//                if (channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
//                    if (saleForm.getSalerId() != null) {
//                        dao.setSaleTransForStaffIdFilter(saleForm.getSalerId());
//                    }
//                } else if (channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
//                    if (saleForm.getSalerId() != null) {
//                        Long[] subShopIdFilter = new Long[1];
//                        subShopIdFilter[0] = saleForm.getSalerId();
//                        dao.setSubShopIdFilter(subShopIdFilter);
//                    }
//                }
//
//            }
//        }
//        log.info("End");
//    }
//
//    private boolean checkSelectedSaleTrans() {
//        log.info("Begin.");
//        String[] saleTransIdTemp = saleForm.getSaleTransId();
//        SaleTransDAO saleTransDAO = new SaleTransDAO();
//        saleTransDAO.setSession(getSession());
//        boolean result = false;
//        if (saleTransIdTemp != null && saleTransIdTemp.length != 0) {
//            String[] stardSaleTransId = new String[saleTransIdTemp.length];
//            for (int i = 0; i < saleTransIdTemp.length; i++) {
//                String temp = saleTransIdTemp[i];
//                if (temp != null && !temp.trim().equals("") && !temp.trim().equals("false")) {
//                    result = true;
//                    return result;
//                }
//            }
//        } else {
//            log.debug("Not select sale trans");
//            log.info("End.");
//            return false;
//        }
//        log.info("End.");
//        return result;
//    }
//
//    private boolean checkSameAgentOwnerSaleTrans() {
//        log.info("Begin.");
//        String[] saleTransIdTemp = saleForm.getSaleTransId();
//        SaleTransDAO saleTransDAO = new SaleTransDAO();
//        saleTransDAO.setSession(getSession());
//        SaleTransInvoiceViewHelper viewHelper = getViewHelper();
//
//        boolean result = false;
//        if (saleTransIdTemp != null && saleTransIdTemp.length != 0) {
//            String[] stardSaleTransId = new String[saleTransIdTemp.length];
//            Long agentId = null;
//            for (int i = 0; i < saleTransIdTemp.length; i++) {
//                String temp = saleTransIdTemp[i];
//                if (temp != null && !temp.trim().equals("") && !temp.trim().equals("false")) {
//                    result = true;
//                    stardSaleTransId[i] = temp;
//                    Long tempSaleTransId = new Long(temp);
//                    saleTransDAO.setStockTransAgentOwnerTypeFilter(STOCK_TRANS_AGENT_OWNER_TYPE);
//                    Long tempAgentId = saleTransDAO.selectAgentIdBySaleTransId(tempSaleTransId);
//                    if (agentId == null) {
//                        agentId = tempAgentId;
//                    } else if (!agentId.equals(tempAgentId)) {
//                        result = false;
//                        addActionError("Giao dịch chọn phải thuộc cùng một đại lý");
//                        log.info("Selected sale trans not from the same agent");
//                        log.info("End.");
//                        return result;
//                    }
//                }
//            }
//
//            /** Save selected agent id */
//            viewHelper.setAgentId(agentId);
//        } else {
//            log.info("Not select sale trans");
//            log.info("End.");
//            return false;
//        }
//        log.info("End.");
//        return result;
//    }
//
//    private void prepareAmountAndTaxInfo(Long shopId) {
//        log.info("Begin");
//        SaleTransDAO saleTransDAO = new SaleTransDAO();
//        saleTransDAO.setSession(getSession());
//
//        /** Amount and tax */
//        String[] saleTransIdTemp = saleForm.getSaleTransId();
//
//        Long amountTax = 0L;
//        Long amountNotTax = 0L;
//        Long tax = 0L;
//        Long discount = 0L;
//        Long promotion = 0L;
//        Long finalAmount = 0L;
//        boolean selectedSaleTrans = false;
//        if (saleTransIdTemp != null && saleTransIdTemp.length != 0) {
//
//            /** We standardlization selected sale trans */
//            String[] stardSaleTransId = new String[saleTransIdTemp.length];
//            for (int i = 0; i < saleTransIdTemp.length; i++) {
//                String temp = saleTransIdTemp[i];
//                if (temp != null && !temp.trim().equals("") && !temp.trim().equals("false")) {
//                    stardSaleTransId[i] = temp;
//                    selectedSaleTrans = true;
//                    Long tempSaleTransId = new Long(temp);
//                    SaleTrans saleTrans = saleTransDAO.findById(tempSaleTransId);
//                    if (saleTrans.getAmountTax() != null) {
//                        amountTax += saleTrans.getAmountTax();
//                    }
//                    if (saleTrans.getAmountNotTax() != null) {
//                        amountNotTax += saleTrans.getAmountNotTax();
//                    }
//                    //vat += saleTrans.getVat();
//                    if (saleTrans.getTax() != null) {
//                        tax += saleTrans.getTax();
//                    }
//                    if (saleTrans.getDiscount() != null) {
//                        discount += saleTrans.getDiscount();
//                    }
//                    if (saleTrans.getPromotion() != null) {
//                        promotion += saleTrans.getPromotion();
//                    }
//                    if(saleTrans.getAmountTax() != null){
//                        finalAmount += saleTrans.getAmountTax();
//                    }
//                }
//            }
//
//
//            setSaleTransId(stardSaleTransId);
//        }
//
//        if (!selectedSaleTrans) {
//            log.debug("Not selected sale trans");
//            log.info("End.");
//            return;
//        }
//
//        SaleTransInvoiceViewHelper viewHelper = getViewHelper();
//        viewHelper.resetViewHelper();
//        viewHelper.setAmountNotTax(amountNotTax.toString());
//        viewHelper.setAmountTax(amountTax.toString());
//        viewHelper.setTax(tax.toString());
//        viewHelper.setDiscount(discount.toString());
//        viewHelper.setPromotion(promotion.toString());
//        viewHelper.setFinalAmount(finalAmount.toString());
//
//
//        viewHelper.setSelectedSaleTrans(selectedSaleTrans);
//
//        /** Display list of selected sale trans */
//        if (selectedSaleTrans) {
//            saleTransDAO.setSaleTransIdFilter(getSaleTransId());
//            if (viewHelper.isSaleTransForSubShop()) {
//                saleTransDAO.setSaleTransForSubShop(Boolean.TRUE);
//            }
//            List<SaleTransBean> saleTransList = saleTransDAO.searchSaleTrans(shopId, SALE_TRANS_CREATED_STATUS);
//            session.setAttribute("saleTransSelectedList", saleTransList);
//        } else {
//            session.setAttribute("saleTransSelectedList", null);
//        }
//        // Clear Danh sách hàng hóa trong giao dịch
//        session.setAttribute("saleTransDetailList", null);
//        log.info("End");
//    }
//
//    /**
//     * Set search condition for trans
//     * @return void
//     * @throws java.lang.Exception
//     */
//    private void prepareCreateSaleInvoiceInfo(Long shopId, Long staffId)
//            throws Exception {
//        log.info("Begin");
//        //saleForm = getSaleForm();
//        SaleTransDAO saleTransDAO = new SaleTransDAO();
//        saleTransDAO.setSession(getSession());
//
//        SaleTransInvoiceViewHelper viewHelper = getViewHelper();
//        StaffDAO staffDAO = new StaffDAO();
//        staffDAO.setSession(getSession());
//
//        /** Save name of staff */
//        Staff staff = staffDAO.findById(staffId);
//        viewHelper.setStaffName(staff.getName());
//
//        ShopDAO shopDAO = new ShopDAO();
//        shopDAO.setSession(getSession());
//        Shop shop = shopDAO.findById(shopId);
//        viewHelper.setShopName(shop.getName());
//
//        Date currDate = new Date();
//        String date = DateTimeUtils.convertDateTimeToString(currDate);
//        viewHelper.setCreateDate(date);
//
//        /** List of agent shop */
//        shopDAO.setObjectTypeFilter(AGENT_OBJECT_TYPE);
//        shopDAO.setIsVTUnitFilter(AGENT_IS_VT_UNIT);
//        List<Shop> childShopList = shopDAO.findAgentShop(shopId, new Long(ACTIVE_STATUS));
//        session.setAttribute("childShopList", childShopList);
//
//        /** List of pay method */
//        AppParamsDAO appParamsDAO = new AppParamsDAO();
//        appParamsDAO.setSession(getSession());
//        List payMethodList = appParamsDAO.findAppParamsList("PAY_METHOD", "1");
//
//        session.setAttribute("listPayMethod", payMethodList);
//
//        /** List of reason */
//        ReasonDAO reasonDAO = new ReasonDAO();
//        reasonDAO.setSession(getSession());
//        List reasonList = reasonDAO.findByPropertyWithStatus("REASON_TYPE", "SALE_REASON", ACTIVE_STATUS);
//
//        session.setAttribute("reasonList", reasonList);
//
//        /** List of invoice serial */
//        InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
//        invoiceListDAO.setSession(getSession());
//
//        List<InvoiceList> invoiceSerialList = invoiceListDAO.findAllSerialInvoiceList(shopId, staffId);
//
//        session.setAttribute("invoiceSerialList", invoiceSerialList);
//
//        /* Prepare Agent infor */
//        if (viewHelper.getInterfaceType() != null &&
//                viewHelper.getInterfaceType().equals(new Long(SALE_TRANS_INVOICE_AGENT_INTERFACE))) {
//
//            //MrSun
//            //Bo: phai lay thong tin da duoc nhap tu bang saleTrans
//
//            Long agentId = viewHelper.getAgentId();
////
//            Shop agent = shopDAO.findById(agentId);
////            viewHelper.setAgentAddress(agent.getAddress());
////            viewHelper.setAgentTin(agent.getTin());
//            viewHelper.setAgentAccount(agent.getAccount());
////            viewHelper.setAgentName(agent.getName());
//
//            //Lay thong tin ten khach hang, dia chi
//            String[] saleTransIdTemp1 = saleForm.getSaleTransId();
//            int i1 = 0;
//            while ((viewHelper.getAddress() == null || viewHelper.getTin() == null || viewHelper.getCompany() == null || viewHelper.getCustName() == null) && (i1 < saleTransIdTemp1.length)) {
//                String temp1 = saleTransIdTemp1[i1];
//                Long tempSaleTransId = new Long(temp1);
//                SaleTrans saleTrans = saleTransDAO.findById(tempSaleTransId);
//
//                if (viewHelper.getAddress() == null) {
//                    viewHelper.setAddress(saleTrans.getAddress());
//                }
//                if (viewHelper.getTin() == null) {
//                    viewHelper.setTin(saleTrans.getTin());
//                }
//                if (viewHelper.getCustName() == null) {
//                    viewHelper.setAgentName(saleTrans.getCustName());
//                }
//                i1++;
//            }
//        }
//
//        if (viewHelper.getInterfaceType() != null &&
//                viewHelper.getInterfaceType().equals(new Long(SALE_TRANS_INVOICE_RETAIL_INTERFACE))) {
//            String[] saleTransIdTemp = saleForm.getSaleTransId();
//            int i = 0;
//            while ((viewHelper.getAddress() == null || viewHelper.getTin() == null || viewHelper.getCompany() == null || viewHelper.getCustName() == null) && (i < saleTransIdTemp.length)) {
//                String temp = saleTransIdTemp[i];
//                Long tempSaleTransId = new Long(temp);
//                SaleTrans saleTrans = saleTransDAO.findById(tempSaleTransId);
//                if (viewHelper.getAddress() == null) {
//                    viewHelper.setAddress(saleTrans.getAddress());
//                }
//                if (viewHelper.getTin() == null) {
//                    viewHelper.setTin(saleTrans.getTin());
//                }
//                if (viewHelper.getCompany() == null) {
//                    viewHelper.setCompany(saleTrans.getCompany());
//                }
//                if (viewHelper.getCustName() == null) {
//                    viewHelper.setCustName(saleTrans.getCustName());
//                }
//                i++;
//            }
//
//        }
//
//        log.info("End");
//    }
//
//    /**
//     * Update sale trans infor with invoice infor
//     * @return void
//     * @throws java.lang.Exception
//     */
//    private void updateSaleTrans(Long shopId) throws Exception {
//        try {
//            log.info("Begin");
//            getReqSession();
//            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
//            invoiceListDAO.setSession(getSession());
//            Long selectedInvoiceListId = (Long) session.getAttribute("selectedInvoiceListId");
//            if (selectedInvoiceListId == null) {
//                log.info("WEB. Session time out. selectedInvoiceListId is missing");
//                return;
//            }
//            InvoiceList invoiceList = invoiceListDAO.findById(selectedInvoiceListId);
//            Long invoiceUsedId = insertInvoiceUsed(shopId, invoiceList);
//            /** Update list of saleTrans */
//            for (int i = 0; i < saleTransId.length; i++) {
//                Long saleTransIdTemp = new Long(saleTransId[i]);
//                SaleTransDAO saleTransDAO = new SaleTransDAO();
//                saleTransDAO.setSession(getSession());
//                SaleTrans saleTrans = saleTransDAO.findById(saleTransIdTemp);
//                /** Update invoice list infor */
//                saleTrans.setInvoiceUsedId(invoiceUsedId);
//                saleTrans.setStatus(SALE_TRANS_INVOICE_CREATED_STATUS);
//                saleTrans.setInvoiceCreateDate(new Date());
//                saleTransDAO.save(saleTrans);
//            }
//            session.setAttribute("invoiceUsedId", invoiceUsedId);
//            log.info("End");
//        } catch (Exception ex) {
//            log.error("WEB: " + ex.toString());
//            throw ex;
//        }
//    }
//
//    /**
//     * Insert new invoice used
//     * @return id of invoiceused which was insert to database
//     * @throws java.lang.Exception
//     */
//    private Long insertInvoiceUsed(Long shopId, InvoiceList invoiceList) throws Exception {
//        log.info("Begin");
//        InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
//        invoiceUsedDAO.setSession(getSession());
//        SaleTransInvoiceViewHelper viewHelper = getViewHelper();
//
//        InvoiceUsed invoiceUsed = new InvoiceUsed();
//
//        Long invoiceUsedId = getSequence("INVOICE_USED_SEQ");
//
//        invoiceUsed.setInvoiceUsedId(invoiceUsedId);
//
//        Long selectedInvoiceListId = (Long) session.getAttribute("selectedInvoiceListId");
//
//        if (selectedInvoiceListId == null) {
//            log.info("WEB. Session time out. selectedInvoiceListId is missing");
//            return null;
//        }
//
//        invoiceUsed.setInvoiceListId(selectedInvoiceListId);
//        invoiceUsed.setInvoiceDate(new Date());
//
//        /** Invoice used type */
//        if (viewHelper.getInterfaceType() != null && (viewHelper.getInterfaceType().toString().equals(SALE_TRANS_INVOICE_COLLABORATOR_INTERFACE))) {
//            invoiceUsed.setType(INVOICE_USED_FOR_COLLABORATOR);
//        } else if (viewHelper.getInterfaceType() != null) {
//            invoiceUsed.setType(INVOICE_USED_MYSELF_CREATED);
//        }
//
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//
//        invoiceUsed.setShopId(shopId);
//        invoiceUsed.setStaffId(userToken.getUserID());
//        if (viewHelper.getInterfaceType() != null && (viewHelper.getInterfaceType().toString().equals(SALE_TRANS_INVOICE_AGENT_INTERFACE) || viewHelper.getInterfaceType().toString().equals(SALE_TRANS_DIRECT_CREATE_INVOICE_INTERFACE))) {
//            Long agentId = viewHelper.getAgentId();
//            if (agentId != null) {
//                ShopDAO shopDAO = new ShopDAO();
//                shopDAO.setSession(getSession());
//                List<Shop> shopList = shopDAO.findByPropertyWithStatus(ShopDAO.SHOP_ID, agentId, new Long(ACTIVE_STATUS));
//                if (shopList != null && shopList.size() != 0) {
//                    Shop shop = shopList.get(0);
//                    invoiceUsed.setCustName(shop.getName());
//                    invoiceUsed.setCompany(shop.getCompany());
//                    invoiceUsed.setAddress(shop.getAddress());
//                    invoiceUsed.setTin(shop.getTin());
//                    invoiceUsed.setAccount(shop.getAccount());
//                }
//            } else if (viewHelper.getInterfaceType().toString().equals(SALE_TRANS_DIRECT_CREATE_INVOICE_INTERFACE)) {
//                /** Set customer infor */
//                invoiceUsed.setCustName(saleForm.getCustName());
//                invoiceUsed.setCompany(saleForm.getCompany());
//                invoiceUsed.setAddress(saleForm.getAddress());
//                invoiceUsed.setTin(saleForm.getTin());
//                invoiceUsed.setAccount(saleForm.getAccount());
//            }
//        } else {
//            /** Set customer infor */
//            invoiceUsed.setCustName(saleForm.getCustName());
//            invoiceUsed.setCompany(saleForm.getCompany());
//            invoiceUsed.setAddress(saleForm.getAddress());
//            invoiceUsed.setTin(saleForm.getTin());
//            invoiceUsed.setAccount(saleForm.getAccount());
//        }
//
//        invoiceUsed.setPayMethod(saleForm.getPayMethod());
//
//        SaleTransDAO saleTransDAO = new SaleTransDAO();
//        saleTransDAO.setSession(getSession());
//
//        /** Amount, tax not direct input from interface */
//        if (viewHelper.getInterfaceType() != null && !viewHelper.getInterfaceType().toString().equals(SALE_TRANS_DIRECT_CREATE_INVOICE_INTERFACE)) {
//            String[] saleTransIdTemp = getSaleTransId();
//
//            Long amountTax = 0L;
//            Long amountNotTax = 0L;
//            Long tax = 0L;
//            Long discount = 0L;
//            Long promotion = 0L;
//            if (saleTransIdTemp != null && saleTransIdTemp.length != 0) {
//                for (int i = 0; i < saleTransIdTemp.length; i++) {
//                    Long saleTransId = new Long(saleTransIdTemp[i]);
//                    SaleTrans saleTrans = saleTransDAO.findById(saleTransId);
//                    if (saleTrans.getAmountTax() != null) {
//                        amountTax += saleTrans.getAmountTax();
//                    }
//                    if (saleTrans.getAmountNotTax() != null) {
//                        amountNotTax += saleTrans.getAmountNotTax();
//                    }
//                    if (saleTrans.getTax() != null) {
//                        tax += saleTrans.getTax();
//                    }
//                    if (saleTrans.getDiscount() != null) {
//                        discount += saleTrans.getDiscount();
//                    }
//                    if (saleTrans.getPromotion() != null) {
//                        promotion += saleTrans.getPromotion();
//                    }
//                }
//            }
//
//            invoiceUsed.setAmount(amountNotTax);
//            invoiceUsed.setAmountTax(amountTax);
//            invoiceUsed.setTax(tax);
//            invoiceUsed.setPromotion(promotion);
//            invoiceUsed.setDiscount(discount);
//        } else {
//
//            /** Input direct from interface */
//            Long amountTax = 0L;
//            Long amountNotTax = 0L;
//            Long tax = 0L;
//            Long discount = 0L;
//            Long promotion = 0L;
//            if (saleForm.getAmount() != null) {
//                amountNotTax = saleForm.getAmount();
//            }
//            if (saleForm.getTax() != null) {
//                tax = saleForm.getTax();
//            }
//            if (saleForm.getPromotion() != null && !saleForm.getPromotion().trim().equals("")) {
//                promotion = new Long(saleForm.getPromotion().replace(",", ""));
//            }
//            if (saleForm.getDiscount() != null && !saleForm.getDiscount().trim().equals("")) {
//                discount = new Long(saleForm.getDiscount().replace(",", ""));
//            }
//            amountTax = amountNotTax + tax - discount - promotion;
//            invoiceUsed.setAmount(amountNotTax);
//            invoiceUsed.setAmountTax(amountTax);
//            invoiceUsed.setTax(tax);
//            invoiceUsed.setPromotion(promotion);
//            invoiceUsed.setDiscount(discount);
//        }
//
//        /** Invoice infor */
//        invoiceUsed.setSerialNo(invoiceList.getSerialNo());
//        invoiceUsed.setBlockNo(invoiceList.getBlockNo());
//        invoiceUsed.setInvoiceId(invoiceList.getCurrInvoiceNo());
//
//        StringBuffer invoiceNo = new StringBuffer();
//
//        //Chua xu ly do dai hoa don
//        InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
//        invoiceListDAO.setSession(getSession());
//        List<CurrentInvoiceListBean> currentInvoiceListBean = invoiceListDAO.getCurrentInvoiceList(null, null, null, invoiceList.getInvoiceListId(), null);
//        if (currentInvoiceListBean != null && currentInvoiceListBean.size()==1){
//            invoiceNo.append(currentInvoiceListBean.get(0).getSerialNo());
//            invoiceNo.append(currentInvoiceListBean.get(0).getBlockNo());
//            invoiceNo.append(currentInvoiceListBean.get(0).getCurrInvoiceNo());
//        }
//        else{
//            invoiceNo.append(invoiceList.getSerialNo());
//            invoiceNo.append(invoiceList.getBlockNo());
//            invoiceNo.append(invoiceList.getCurrInvoiceNo().toString());
//        }
//        invoiceUsed.setInvoiceNo(invoiceNo.toString());
//
//        invoiceUsed.setStatus(INVOICE_USED_STATUS);
//        invoiceUsed.setCreateDate(new Date());
//
//        invoiceUsed.setNote(saleForm.getNote());
//
//        if (saleForm.getReasonId() != null && !saleForm.getReasonId().trim().equals("")) {
//            invoiceUsed.setReasonId(new Long(saleForm.getReasonId()));
//        }
//
//        invoiceUsedDAO.save(invoiceUsed);
//
//        /** Now update invoice list */
//        invoiceListDAO = new InvoiceListDAO();
//        invoiceListDAO.setSession(getSession());
//        if (invoiceList.getCurrInvoiceNo().equals(invoiceList.getToInvoice())) {
//            invoiceList.setCurrInvoiceNo(USED_ALL_CURR_INVOICE);
//            invoiceList.setStatus(NOT_USEABLE);//Used all
//        } else {
//            Long currInvoiceNo = invoiceList.getCurrInvoiceNo();
//            currInvoiceNo++;
//            invoiceList.setCurrInvoiceNo(currInvoiceNo);
//        }
//
//        invoiceListDAO.save(invoiceList);
//
//        log.info("End");
//        return invoiceUsedId;
//    }
//
//    /**
//     * Save viewHelper for reserve selected condition
//     * @return void
//     */
//    private SaleTransInvoiceViewHelper getViewHelper() {
//        SaleTransInvoiceViewHelper viewHelper =
//                (SaleTransInvoiceViewHelper) session.getAttribute(SaleTransInvoiceViewHelper.SALE_TRANS_INVOICE);
//        if (viewHelper == null) {
//            viewHelper = new SaleTransInvoiceViewHelper();
//            session.setAttribute(SaleTransInvoiceViewHelper.SALE_TRANS_INVOICE, viewHelper);
//        }
//        return viewHelper;
//    }
//
//    public static String[] getSaleTransId() {
//        return saleTransId;
//    }
//
//    public static void setSaleTransId(String[] saleTransId) {
//        SaleInvoiceDAO.saleTransId = saleTransId;
//    }
//
//    public Map getListObject() {
//        return listObject;
//    }
//
//    public void setListObject(Map listObject) {
//        this.listObject = listObject;
//    }
//
//
//
//    //MrSun
//
//    public String goCreateInvoiceNoSale(){
//        log.info("Begin");
//        String pageForward = Constant.ERROR_PAGE;
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        if (userToken == null) {
//            log.info("WEB:. Session time out");
//            return pageForward;
//        }
//        try {
//            session.setAttribute(LST_ITEMS,null);
//            session.setAttribute(SALE_INVOICE_ID,null);
//            session.setAttribute(SELECTED_INVOICE_LIST_ID,null);
//            session.setAttribute("createInvoiceNoSaleActionActionType",null);
//
//            Long shopId = (Long) session.getAttribute("shopId");
//            if (shopId == null) {
//                ShopDAO shopDAO = new ShopDAO();
//                shopDAO.setSession(getSession());
//                shopId = shopDAO.getShopIDByStaffID(userToken.getUserID());
//                if (shopId == null) {
//                    log.debug("WEB:. User has no shop");
//                    return pageForward;
//                }
//                session.setAttribute("shopId", shopId);
//            }
//
//            //Khoi tao form
//            saleForm = new SaleForm();
//
//            //Ten nhan vien
//            saleForm.setStaffName(userToken.getStaffName());
//
//            //Ten cua hang
//            ShopDAO shopDAO = new ShopDAO();
//            shopDAO.setSession(getSession());
//            Shop shop = shopDAO.findById(shopId);
//            saleForm.setShopName(shop.getName());
//
//            //Ngay tao hoa don
//            Date currDate = new Date();
//            String date = DateTimeUtils.convertDateTimeToString(currDate);
//            saleForm.setStartDate(date);
//
//            //Danh sach HTTT
//            AppParamsDAO appParamsDAO = new AppParamsDAO();
//            appParamsDAO.setSession(getSession());
//            List payMethodList = appParamsDAO.findAppParamsList("PAY_METHOD", ACTIVE_STATUS);
//            session.setAttribute("listPayMethod", payMethodList);
//
//            //Danh sach ly do
//            ReasonDAO reasonDAO = new ReasonDAO();
//            reasonDAO.setSession(getSession());
//            List reasonList = reasonDAO.findByPropertyWithStatus("REASON_TYPE", REASON_TYPE, ACTIVE_STATUS);
//            session.setAttribute("reasonList", reasonList);
//
//            /** List of invoice serial */
//            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
//            invoiceListDAO.setSession(getSession());
//            List<InvoiceList> invoiceSerialList = invoiceListDAO.findAllSerialInvoiceList(shopId, userToken.getUserID());
//            session.setAttribute("invoiceSerialList", invoiceSerialList);
//
//        } catch (Exception e) {
//            log.error("WEB: " + e.toString());
//        }
//        log.info("End");
//        pageForward = CREATE_INVOICE_NO_SALE;
//        return pageForward;
//    }
//
//    public String addItem(){
//        log.info("Begin");
//        String pageForward = "createInvoiceNoSaleDetail";
//        getReqSession();
//        try{
//            List lstItems = (List) session.getAttribute("lstItems");
//            if (lstItems == null) {
//                lstItems = new ArrayList();
//                session.setAttribute("lstItems", lstItems);
//            }
//            if (lstItems.size()>0){
//                for (int i  = 0; i< lstItems.size(); i++){
//                    InvoiceNoSaleItem tmp = (InvoiceNoSaleItem)lstItems.get(i);
//                    if (tmp.getItemId().equals(saleForm.getItemId())){
//                        //Xu ly lua chon cho phep sua tai row do
//                        lstItems.remove(i);
//                        break;
//                    }
//                }
//            }
//            saleForm.calcItemAmount();
//            InvoiceNoSaleItem invoiceNoSaleItem = new InvoiceNoSaleItem();
//
//            if ("".equals(saleForm.getItemId().trim())){
//                saleForm.setItemId(Integer.toString(lstItems.size()+1));
//            }
//
//            invoiceNoSaleItem = saleForm.copyItemInfoB2F();
//            lstItems.add(invoiceNoSaleItem);
//            saleForm.clearItemInfo();
//            session.setAttribute("lstItems",lstItems);
//            calcInvoiceTotal(lstItems);
//
//            req.setAttribute("returnMsg", "createInvoice.success.addItem");
//            log.info("End.");
//        }catch(Exception ex){
//            req.setAttribute("returnMsg", "createInvoice.success.error");
//            log.info("Error.");
//            return pageForward;
//        }
//        session.setAttribute("createInvoiceNoSaleActionActionType",null);
//        pageForward = "createInvoiceNoSaleDetail";
//        return pageForward;
//    }
//
//    public String editItem(){
//        log.info("Begin");
//        String pageForward = "createInvoiceNoSaleDetailInputItem";
//        getReqSession();
//        try{
//            HttpServletRequest req = getRequest();
//            if (req.getParameter("itemId") == null)
//                return pageForward;
//            String itemId = req.getParameter("itemId").toString();
//            if (itemId == null || "".equals(itemId.trim()))
//                return pageForward;
//
//            List lstItems = (List) session.getAttribute("lstItems");
//            if (lstItems == null) {
//                lstItems = new ArrayList();
//                session.setAttribute("lstItems", lstItems);
//            }
//            if (lstItems.size()>0){
//                for (int i  = 0; i< lstItems.size(); i++){
//                    InvoiceNoSaleItem tmp = (InvoiceNoSaleItem)lstItems.get(i);
//                    if (tmp.getItemId().equals(itemId)){
//                        saleForm.copyItemInfoF2B(tmp);
//                        break;
//                    }
//                }
//            }
//            log.info("End.");
//        }catch(Exception ex){
//            log.info("Error.");
//            return pageForward;
//        }
//        session.setAttribute("createInvoiceNoSaleActionActionType","EDIT");
//        pageForward = "createInvoiceNoSaleDetailInputItem";
//        return pageForward;
//    }
//
//    public String delItem(){
//        log.info("Begin");
//        String pageForward = "createInvoiceNoSaleDetailItemList";
//        getReqSession();
//        try{
//            HttpServletRequest req = getRequest();
//            if (req.getParameter("itemId") == null)
//                return pageForward;
//            String itemId = req.getParameter("itemId").toString();
//            if (itemId == null || "".equals(itemId.trim()))
//                return pageForward;
//
//            List lstItems = (List) session.getAttribute("lstItems");
//            if (lstItems == null) {
//                lstItems = new ArrayList();
//                session.setAttribute("lstItems", lstItems);
//            }
//            if (lstItems.size()>0){
//                for (int i  = 0; i< lstItems.size(); i++){
//                    InvoiceNoSaleItem tmp = (InvoiceNoSaleItem)lstItems.get(i);
//                    if (tmp.getItemId().equals(itemId)){
//                        lstItems.remove(i);
//                        break;
//                    }
//                }
//            }
//            session.setAttribute("lstItems", lstItems);
//            calcInvoiceTotal(lstItems);
//            req.setAttribute("returnMsg", "createInvoice.success.delItem");
//            log.info("End.");
//        }catch(Exception ex){
//            log.info("Error.");
//            return pageForward;
//        }
//        pageForward = "createInvoiceNoSaleDetailItemList";
//        return pageForward;
//    }
//
//    public String cancelItem(){
//        log.info("Begin");
//        String pageForward = "createInvoiceNoSaleDetailInputItem";
//        getReqSession();
//        try{
//            session.setAttribute("createInvoiceNoSaleActionActionType",null);
//            saleForm.clearItemInfo();
//            log.info("End.");
//        }catch(Exception ex){
//            log.info("Error.");
//            return pageForward;
//        }
//        return pageForward;
//    }
//
//    private void calcInvoiceTotal(List lstItems){
//        try{
//            Long amount = 0L;
//            Long discount = 0L;
//            Long promotion = 0L;
//            if (lstItems != null && lstItems.size()>0){
//                for (int i  = 0; i< lstItems.size(); i++){
//                    InvoiceNoSaleItem tmp = (InvoiceNoSaleItem)lstItems.get(i);
//                    amount += tmp.getItemAmount();
//                    discount += tmp.getItemDiscount();
//                    promotion += tmp.getItemPromotion();
//                }
//            }
//            saleForm.setInvoiceAmount(amount);
//            saleForm.setInvoiceDiscount(discount);
//            saleForm.setInvoicePromotion(promotion);
//            saleForm.setInvoiceTotal(amount+discount+promotion);
//        }catch(Exception ex){
//        }
//    }
//
//
//    public String getInvoiceNumber() throws Exception {
//        log.info("Begin");
//        String pageForward = Constant.ERROR_PAGE;
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//
//        if (userToken == null) {
//            log.info("WEB:. Session time out");
//            pageForward = Constant.SESSION_TIME_OUT;
//            return pageForward;
//        }
//        try {
//            String invoiceSerial = (String) req.getParameter("invoiceSerial");
//            if (invoiceSerial == null || "".equals(invoiceSerial.trim())) {
//                saleForm.setFromInvoice(5L);
//                saleForm.setToInvoice(5L);
//                saleForm.setCurrInvoiceNo(5L);
//                return pageForward;
//            }
//            saleForm.setBillSerial(invoiceSerial);
//
//            //Gan nguoc tro lai cac thong tin len webpage
//            String tmp = (String) req.getParameter("shopName");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setShopName(tmp);
//            }
//            tmp = (String) req.getParameter("staffName");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setStaffName(tmp);
//            }
//            tmp = (String) req.getParameter("startDate");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setStartDate(tmp);
//            }
//            tmp = (String) req.getParameter("custName");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setCustName(tmp);
//            }
//            tmp = (String) req.getParameter("company");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setCompany(tmp);
//            }
//            tmp = (String) req.getParameter("address");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setAddress(tmp);
//            }
//            tmp = (String) req.getParameter("tin");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setTin(tmp);
//            }
//            tmp = (String) req.getParameter("account");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setAccount(tmp);
//            }
//            tmp = (String) req.getParameter("payMethod");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setPayMethod(tmp);
//            }
//            tmp = (String) req.getParameter("reasonId");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setReasonId(tmp);
//            }
//            tmp = (String) req.getParameter("note");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setNote(tmp);
//            }
//            tmp = (String) req.getParameter("itemId");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setItemId(tmp);
//            }
//            tmp = (String) req.getParameter("itemCode");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setItemCode(tmp);
//            }
//            tmp = (String) req.getParameter("itemName");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setItemName(tmp);
//            }
//            tmp = (String) req.getParameter("itemUnit");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setItemUnit(tmp);
//            }
//            tmp = (String) req.getParameter("itemPriceString");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setItemPriceString(tmp);
//            }
//            tmp = (String) req.getParameter("itemQtyString");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setItemQtyString(tmp);
//            }
//            tmp = (String) req.getParameter("itemNote");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                saleForm.setItemNote(tmp);
//            }
//            tmp = (String) req.getParameter("invoiceTotal");
//            if (!(tmp == null || "".equals(tmp.trim()))) {
//                tmp = tmp.replace(",", "");
//                tmp = tmp.replace(".", "");
//                saleForm.setInvoiceTotal(Long.parseLong(tmp.trim()));
//            }
//
//            Long shopId = (Long) session.getAttribute("shopId");
//            if (shopId == null) {
//                log.info("WEB:. Session time out");
//                pageForward = Constant.SESSION_TIME_OUT;
//                return pageForward;
//            }
//
//            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
//            invoiceListDAO.setSession(getSession());
//            invoiceListDAO.setSerialNoFilter(invoiceSerial);
//
//            List<InvoiceList> invoiceLists = invoiceListDAO.findInvoiceListBySerialNo(userToken.getShopId(), userToken.getUserID());
//            if (invoiceLists != null && invoiceLists.size() != 0) {
//                InvoiceList currUsedInvoiceList = null;
//                for (InvoiceList invoiceList : invoiceLists) {
//                    Long minFromInvoiceNo = 0L;
//                    if (invoiceList.getFromInvoice() != invoiceList.getCurrInvoiceNo()) {
//                        /** Tiep tuc su dung dai hoa don dang duoc su dung */
//                        currUsedInvoiceList = invoiceList;
//                        log.info("Continue using invoice list which was in used");
//                        break;
//                    } else {
//
//                        /** Truong hop dau tien */
//                        if (minFromInvoiceNo == 0) {
//                            currUsedInvoiceList = invoiceList;
//                            minFromInvoiceNo = invoiceList.getFromInvoice();
//                        } else {
//
//                            /** Khong chon ra dai hoa don co so hoa don bat dau nho nhat */
//                            if (invoiceList.getFromInvoice() < minFromInvoiceNo) {
//                                currUsedInvoiceList = invoiceList;
//                                minFromInvoiceNo = invoiceList.getFromInvoice();
//                            }
//                        }
//                    }
//                }
//
//                /** Hoa don hien tai la hoa don dang su dung, hoac co so hoa don bat dau nho nhat */
//                if (currUsedInvoiceList != null) {
//                    String InvoiceNo = currUsedInvoiceList.getSerialNo()
//                            + currUsedInvoiceList.getStrBlockNo()
//                            + currUsedInvoiceList.getStrCurrInvoiceNo();
//                    saleForm.setStrFromInvoice(currUsedInvoiceList.getStrFromInvoice());
//                    saleForm.setStrCurrInvoice(currUsedInvoiceList.getStrCurrInvoiceNo());
//                    saleForm.setStrToInvoice(currUsedInvoiceList.getStrToInvoice());
//                    saleForm.setInvoiceNo(InvoiceNo);
//                    //Luu so hoa don se su dung
//                    session.setAttribute(SELECTED_INVOICE_LIST_ID, currUsedInvoiceList.getInvoiceListId());
//                }
//            }
//        } catch (Exception e) {
//            log.error("WEB: " + e.toString());
//            return pageForward;
//        }
//
//        log.info("End");
//        pageForward = CREATE_INVOICE_NO_SALE;
//        return pageForward;
//    }
//
//
//    public String createInvoiceNoSale() throws Exception {
//        log.info("Begin");
//        String pageForward = CREATE_INVOICE_NO_SALE;
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//
//        if (saleForm.getBillSerial() == null || "".equals(saleForm.getBillSerial())) {
//            req.setAttribute("returnMsg", "saleInvoice.error.requiredBillSerial");
//            return pageForward;
//        }
//
//        if (saleForm.getPayMethod() == null || saleForm.getPayMethod().equals("")) {
//            req.setAttribute("returnMsg", "saleInvoice.error.requiredPayMethod");
//            return pageForward;
//        }
//        if (saleForm.getReasonId() == null || saleForm.getReasonId().equals("")) {
//            req.setAttribute("returnMsg", "saleInvoice.error.requiredReason");
//            return pageForward;
//        }
//
//        if (userToken == null) {
//            log.info("WEB:. Session time out");
//            pageForward = Constant.SESSION_TIME_OUT;
//            return pageForward;
//        }
//        try {
//            Long shopId = (Long) session.getAttribute("shopId");
//            if (shopId == null) {
//                log.info("WEB:. Session time out");
//                pageForward = Constant.SESSION_TIME_OUT;
//                return pageForward;
//            }
//
//            List<InvoiceNoSaleItem> lstItems = (List<InvoiceNoSaleItem>) session.getAttribute("lstItems");
//            if (lstItems == null || lstItems.size()<=0) {
//                req.setAttribute("returnMsg", "!!!Danh sách mặt hàng rỗng");
//                return pageForward;
//            }
//
//
//            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
//            invoiceListDAO.setSession(getSession());
//            Long selectedInvoiceListId = (Long) session.getAttribute(SELECTED_INVOICE_LIST_ID);
//            InvoiceList invoiceList = invoiceListDAO.findById(selectedInvoiceListId);
//
//            //24/08/2009
//            //Kiem tra so luong mat hang toi da duoc nhap
//            Long maxRow = -1L;
//            if (invoiceList.getBookTypeId() != null){
//                BookTypeDAO bookTypeDAO = new BookTypeDAO();
//                BookType bookType = bookTypeDAO.findById(invoiceList.getBookTypeId());
//                if (bookType != null)
//                    if (bookType.getMaxRow() != null)
//                        maxRow = bookType.getMaxRow();
//            }
//            if (maxRow > 0 && maxRow < lstItems.size()){
//                req.setAttribute("returnMsg", "!!!Số lượng mặt hàng ("+ lstItems.size() +") > sô lượng cho phép ("+ maxRow.toString() +")");
//                return pageForward;
//            }
//
//            Long invoiceUsedId = insertInvoiceNoSale(shopId, userToken.getUserID(), invoiceList);
//            if (invoiceUsedId == null){
//                req.setAttribute("returnMsg", "saleInvoice.error.requiredReason");
//                return pageForward;
//            }
//
//            session.setAttribute(SALE_INVOICE_ID, invoiceUsedId);
//
//            /** Update invoice list */
//            getSession().flush();//Update invoice list to database
//        } catch (Exception e) {
//            log.error("WEB: " + e.toString());
//            req.setAttribute("returnMsg", "createInvoice.success.error");
//            return pageForward;
//        }
//        log.info("End");
//        req.setAttribute("returnMsg", "createInvoice.success.createSaleTransInvoice");
//        pageForward = CREATE_INVOICE_NO_SALE;
//        return pageForward;
//    }
//
//
//    private Long insertInvoiceNoSale(Long shopId, Long staffId, InvoiceList invoiceList) throws Exception {
//        try{
//            log.info("Begin");
//            InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
//            invoiceUsedDAO.setSession(getSession());
//
//            InvoiceUsed invoiceUsed = new InvoiceUsed();
//            Long invoiceUsedId = getSequence("INVOICE_USED_SEQ");
//            invoiceUsed.setInvoiceUsedId(invoiceUsedId);
//            invoiceUsed.setShopId(shopId);
//            invoiceUsed.setStaffId(staffId);
//            invoiceUsed.setInvoiceDate(new Date());
//            invoiceUsed.setPayMethod(saleForm.getPayMethod());
//            if (saleForm.getReasonId() != null && !saleForm.getReasonId().trim().equals("")) {
//                invoiceUsed.setReasonId(new Long(saleForm.getReasonId()));
//            }
//            invoiceUsed.setCreateDate(new Date());
//            invoiceUsed.setStatus(INVOICE_USED_STATUS);
//            invoiceUsed.setNote(saleForm.getNote());
//
//            invoiceUsed.setCustName(saleForm.getCustName());
//            invoiceUsed.setCompany(saleForm.getCompany());
//            invoiceUsed.setAddress(saleForm.getAddress());
//            invoiceUsed.setTin(saleForm.getTin());
//            invoiceUsed.setAccount(saleForm.getAccount());
//
//            List lstItems = (List) session.getAttribute("lstItems");
//                if (lstItems == null) {
//                    lstItems = new ArrayList();
//                    session.setAttribute("lstItems", lstItems);
//                }
//            calcInvoiceTotal(lstItems);
//            invoiceUsed.setAmount(saleForm.getInvoiceAmount());
//            invoiceUsed.setTax(saleForm.getInvoiceVAT() * saleForm.getInvoiceAmount());
//            invoiceUsed.setDiscount(saleForm.getInvoiceDiscount());
//            invoiceUsed.setPromotion(saleForm.getInvoicePromotion());
//            invoiceUsed.setAmountTax(saleForm.getInvoiceTotal());
//
//            invoiceUsed.setSerialNo(invoiceList.getSerialNo());
//            invoiceUsed.setBlockNo(invoiceList.getBlockNo());
//            invoiceUsed.setInvoiceId(invoiceList.getCurrInvoiceNo());
//
//            StringBuffer invoiceNo = new StringBuffer();
//            //Xu ly them do dai hoa don
//            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
//            invoiceListDAO.setSession(getSession());
//            List<CurrentInvoiceListBean> currentInvoiceListBean = invoiceListDAO.getCurrentInvoiceList(null, null, null, invoiceList.getInvoiceListId(), null);
//            if (currentInvoiceListBean != null && currentInvoiceListBean.size()==1){
//                invoiceNo.append(currentInvoiceListBean.get(0).getSerialNo());
//                invoiceNo.append(currentInvoiceListBean.get(0).getBlockNo());
//                invoiceNo.append(currentInvoiceListBean.get(0).getCurrInvoiceNo());
//            }
//            else{
//                invoiceNo.append(invoiceList.getSerialNo());
//                invoiceNo.append(invoiceList.getBlockNo());
//                invoiceNo.append(invoiceList.getCurrInvoiceNo().toString());
//            }
//            invoiceUsed.setInvoiceNo(invoiceNo.toString());
//
//            //HD tu khong GD
//            invoiceUsed.setType(SALE_INVOICE_TYPE);
//
//            //Ma HD duoc su dung
//            invoiceUsed.setInvoiceListId(invoiceList.getInvoiceListId());
//
//            invoiceUsedDAO.save(invoiceUsed);
//
//            /** Now update invoice list */
//            invoiceListDAO = new InvoiceListDAO();
//            invoiceListDAO.setSession(getSession());
//            if (invoiceList.getCurrInvoiceNo().equals(invoiceList.getToInvoice())) {
//                invoiceList.setCurrInvoiceNo(USED_ALL_CURR_INVOICE);
//                invoiceList.setStatus(NOT_USEABLE);//Used all
//            } else {
//                Long currInvoiceNo = invoiceList.getCurrInvoiceNo();
//                currInvoiceNo++;
//                invoiceList.setCurrInvoiceNo(currInvoiceNo);
//            }
//            invoiceListDAO.save(invoiceList);
//            log.info("End");
//            return invoiceUsedId;
//        } catch (Exception e) {
//            log.info("Error");
//            return null;
//        }
//    }
//
//    public String printInvoiceNoSale() throws Exception {
//        String pageForward = CREATE_INVOICE_NO_SALE;
//        getReqSession();
//        try {
//            Long shopId = (Long) session.getAttribute("shopId");
//            Long invoiceUsedId = (Long) session.getAttribute(SALE_INVOICE_ID);
//
//            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//            InvoicePrinterDAO invoicePrinterDAO = new InvoicePrinterDAO();
//
//            List<InvoiceNoSaleItem> lstItems = (List<InvoiceNoSaleItem>) session.getAttribute("lstItems");
//            if (lstItems == null) {
//                req.setAttribute("returnMsg", "saleInvoice.error.requiredPageSize");
//                return pageForward;
//            }
//
//            String returnValue = invoicePrinterDAO.printInvoiceNoSale(invoiceUsedId, lstItems);
//
//            if (returnValue.equals("2")) {
//                req.setAttribute("returnMsg", "saleInvoice.error.invalidPrinterParam");
//                return pageForward;
//            }
//            if (returnValue.equals("3")) {
//                req.setAttribute("returnMsg", "saleInvoice.error.requiredPageSize");
//                return pageForward;
//            }
//
//            saleForm.resetSaleForm();
//            saleForm.setExportUrl(returnValue);
//            saleForm.setExprotUrlRac(returnValue.replace(".pdf", "_rac.pdf"));
//            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
//            invoiceListDAO.setSession(getSession());
//
//            List<InvoiceList> invoiceSerialList = invoiceListDAO.findAllSerialInvoiceList(shopId, userToken.getUserID());
//
//            session.setAttribute("invoiceSerialList", invoiceSerialList);
//            req.setAttribute("returnMsg", "createInvoice.success.printeSaleTransInvoice");
//        /** Reset list to null */
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            getSession().clear();
//            req.setAttribute("returnMsg", "createInvoice.success.error");
//        }
//        return pageForward;
//    }
//
//
//
//    public String changeSaleTransType() throws Exception {
//        log.info("Begin");
//        getReqSession();
//        String pageForward = INVOICE_TO_WHOLE_SALE;
//        String[] header = {"", "--Chọn lý do--"};
//        List tmpList = new ArrayList();
//        tmpList.add(header);
//        listObject.put("reasonId", tmpList);
//
//        String saleTransTypeTemp = req.getParameter("saleTransType");
//        if (saleTransTypeTemp == null) {
//            log.info("WEB. Channel type id is missing");
//            return "success";
//        }
//
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//
//        if (userToken == null) {
//            log.info("WEB:. Session time out");
//            pageForward = Constant.SESSION_TIME_OUT;
//            return pageForward;
//        }
//        try {
//            //Ma loai giao dich
//            Long saleTransType = 0L;
//            if (!"".equals(saleTransTypeTemp.trim()))
//                saleTransType = new Long(saleTransTypeTemp.trim());
//
//            ReasonDAO reasonDAO = new ReasonDAO();
//            reasonDAO.setSession(getSession());
//            String[] fieldName;
//            String[] propertyValue;
//            List reasonList;
//
//            fieldName = new String[2];
//            fieldName[0] = "reasonId";
//            fieldName[1] = "reasonName";
//
//            //Neu chon 1 loai giao dich
//            if (saleTransType>0){
//                //Danh sach nhom ly do
//                SaleInvoiceTypeDAO saleInvoiceTypeDAO = new SaleInvoiceTypeDAO();
//                saleInvoiceTypeDAO.setSession(getSession());
//                List<SaleInvoiceType> lstTemp = saleInvoiceTypeDAO.findByProperty("saleInvoiceTypeId",saleTransType);
//                if (lstTemp == null || lstTemp.size() <=0)
//                    return "successReasonSaleList";
//                //Ten nhom ly do
//                SaleInvoiceType saleInvoiceType = lstTemp.get(0);
//                if (saleInvoiceType.getCode() == null)
//                    return "successReasonSaleList";
//
//                //Danh sach ly do
//                propertyValue = new String[1];
//                propertyValue[0] = saleInvoiceType.getCode();
//                reasonList = reasonDAO.findByPropertyWithStatus(fieldName,"REASON_TYPE", propertyValue, Constant.STATUS_ACTIVE);
//            }
//            else{
//                //Khong chon loai giao dich
//                propertyValue = new String[4];
//                propertyValue[0] = REASON_SALE_RETAIL;
//                propertyValue[1] = REASON_SALE_COL;
//                propertyValue[2] = REASON_SALE_INTERNAL;
//                propertyValue[3] = REASON_SALE_PROMO;
//                reasonList = reasonDAO.findByPropertyWithStatus(fieldName,"REASON_TYPE", propertyValue, Constant.STATUS_ACTIVE);
//            }
//            //Day vao combo
//            tmpList.addAll(reasonList);
//            lstReasonSale.put("reasonId", tmpList);
//
//        }catch (Exception e) {
//            log.error("WEB: " + e.toString());
//            throw e;
//        }
//        log.info("End");
//        return "successReasonSaleList";
//    }
//
//    private boolean checkSameInvoiceType(){
//        //Bat dau
//        log.info("Begin.");
//        boolean result = true;
//
//        //Lay danh sach ma giao dich
//        String[] saleTransIdTemp = saleForm.getSaleTransId();
//
//        //Khong co giao dich nao duoc chon
//        if (!(saleTransIdTemp != null && saleTransIdTemp.length != 0)) {
//            return result;
//        }
//
//        String queryString = "";
//        List parameterList = new ArrayList();
//        for (int i = 0; i < saleTransIdTemp.length; i++) {
//            String temp = saleTransIdTemp[i];
//            if (temp != null && !temp.trim().equals("") && !temp.trim().equals("false")) {
//                if (!"".equals(queryString.trim()))
//                    queryString += " or ";
//                queryString += " st.sale_trans_id = ? ";
//                parameterList.add(new Long(temp));
//            }
//        }
//        //Khong co giao dich nao duoc chon
//        if ("".equals(queryString.trim()))
//            return result;
//
//        queryString = " ( select st.sale_trans_type from sale_trans st where " + queryString + " ) ";
//        queryString = " select distinct INVOICE_GROUP From sale_invoice_type where SALE_TRANS_TYPE in  " + queryString;
//        Query queryObject = getSession().createSQLQuery(queryString);
//        for (int i = 0; i < parameterList.size(); i++){
//            queryObject.setParameter(i, parameterList.get(i));
//        }
//
//        //Khong cung loai hoa don
//        List lstTemp = queryObject.list();
//        if (lstTemp != null && lstTemp.size()>=2){
//            result = false;
//        }
//
//        //Ket thuc
//        log.info("End.");
//        return result;
//    }
//
//
//    private boolean checkNotUpdateSerial(){
//        //Bat dau
//        log.info("Begin.");
//        boolean result = true;
//
//        //Lay danh sach ma giao dich
//        String[] saleTransIdTemp = saleForm.getSaleTransId();
//
//        //Khong co giao dich nao duoc chon
//        if (!(saleTransIdTemp != null && saleTransIdTemp.length != 0)) {
//            log.info("End.");
//            return result;
//        }
//
//        for (int i = 0; i < saleTransIdTemp.length; i++) {
//            String queryString = "";
//            String temp = saleTransIdTemp[i];
//            if (temp != null && !temp.trim().equals("") && !temp.trim().equals("false")) {
//                queryString = " SELECT * from sale_services_model ssm where SSM.status = 1 ";
//                queryString += " AND SSM.update_stock = 0 ";
//                queryString += " AND ssm.sale_services_id IN (SELECT ST.sale_service_id FROM SALE_TRANS ST WHERE ST.sale_trans_id = ? ) ";
//                queryString += " AND SSM.stock_type_id NOT IN  ";
//                queryString += " ( ";
//                queryString += " SELECT SM.stock_type_id FROM STOCK_MODEL SM WHERE SM.stock_model_id IN (SELECT STD.stock_model_id FROM SALE_TRANS_DETAIL STD WHERE STD.sale_trans_id = ? ) ";
//                queryString += " ) ";
//
//                Query queryObject = getSession().createSQLQuery(queryString);
//                queryObject.setParameter(0, new Long(temp));
//                queryObject.setParameter(1, new Long(temp));
//                List lstTemp = queryObject.list();
//                if (lstTemp != null && lstTemp.size()>=1){
//                    log.info("End.");
//                    result = false;
//                }
//
//                queryString = "";
//                queryString = " SELECT * from sale_services_model ssm where SSM.status = 1 ";
//                queryString += " AND SSM.update_stock = 1 ";
//                queryString += " AND ssm.sale_services_id IN (SELECT ST.sale_service_id FROM SALE_TRANS ST WHERE ST.sale_trans_id = ? ) ";
//                queryString += " AND SSM.stock_type_id NOT IN  ";
//                queryString += " ( ";
//                queryString += " SELECT SM.stock_type_id FROM STOCK_MODEL SM WHERE SM.stock_model_id IN (SELECT STD.stock_model_id FROM SALE_TRANS_DETAIL STD WHERE STD.sale_trans_id = ? AND exists (select * from SALE_TRANS_SERIAL stserial where stserial.sale_trans_detail_id = STD.sale_trans_detail_id AND stserial.stock_model_id = STD.stock_model_id  ) ) ";
//                queryString += " ) ";
//
//                queryObject = null;
//                queryObject = getSession().createSQLQuery(queryString);
//                queryObject.setParameter(0, new Long(temp));
//                queryObject.setParameter(1, new Long(temp));
//                lstTemp = null;
//                lstTemp = queryObject.list();
//                if (lstTemp != null && lstTemp.size()>=1){
//                    log.info("End.");
//                    result = false;
//                }
//            }
//        }
//        //Ket thuc
//        log.info("End.");
//        return result;
//    }
//
//    //MrSun
//

}
