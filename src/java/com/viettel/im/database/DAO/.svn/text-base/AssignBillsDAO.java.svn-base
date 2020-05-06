package com.viettel.im.database.DAO;

import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.InvoiceListManagerViewHelper;
import com.viettel.im.client.form.BillsDepartmentForm;
import com.viettel.im.client.form.SearchBillForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.BookType;
import com.viettel.im.database.BO.InvoiceList;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * author   :
 * date     :
 * desc     : cac nghiep vu lien quan den giao hoa don
 * modified : tamdt1, 21/10/2010
 * 
 */
public class AssignBillsDAO extends BillBaseDAO {

    private static final Logger log = Logger.getLogger(AuthenticateDAO.class);
    private static Long ASSIGN_SHOP = 1L;
    private static Long ASSIGN_STAFF = 2L;
    private static final Long ASSIGN_BILL_INTERFACE = 1L;
    private SearchBillForm form;
    private BillsDepartmentForm formBill;
    private Map<String, String> fromInvoiceMap;
    private Map<String, String> toInvoiceMap;

    public Map<String, String> getFromInvoiceMap() {
        return fromInvoiceMap;
    }

    public void setFromInvoiceMap(Map<String, String> fromInvoiceMap) {
        this.fromInvoiceMap = fromInvoiceMap;
    }

    public Map<String, String> getToInvoiceMap() {
        return toInvoiceMap;
    }

    public void setToInvoiceMap(Map<String, String> toInvoiceMap) {
        this.toInvoiceMap = toInvoiceMap;
    }

    public SearchBillForm getForm() {
        return form;
    }

    public void setForm(SearchBillForm form) {
        this.form = form;
    }

    public BillsDepartmentForm getFormBill() {
        return formBill;
    }

    public void setFormBill(BillsDepartmentForm formBill) {
        this.formBill = formBill;
    }

    /**
     * @funtion prepare data when go to the interface
     * @Date 18/02/2009
     */
    public String preparePage() throws Exception {
        //session.setAttribute(billManagerViewHelper, null);
        HttpServletRequest reqa = getRequest();
        String pageForward = "assignBills";
        String pageForwardAjax = "searchBillsAjax";
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
        reqa.setAttribute("selectedValue", "1");
        InvoiceListManagerViewHelper invoiceListManagerViewHelper =
                new InvoiceListManagerViewHelper();
        invoiceListManagerViewHelper.setInterfaceType(ASSIGN_BILL_INTERFACE);
        reqSession.setAttribute(billManagerViewHelper, invoiceListManagerViewHelper);
        deleteTemp(userToken);
        return preparePageBase(req, pageForward, pageForwardAjax, Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP, null);

    }

    /**
     * @funtion prepare data when go to the interface
     * @Date 18/02/2009
     */
    public String searchBills() throws Exception {
        HttpServletRequest reqa = getRequest();

        //tamdt1, bo sung thay the bang F9
        //kiem tra su ton tai cua BookType
        String serialCode = this.form.getSerialCode();
        this.form.setBillSerial(serialCode);
        //tamdt1, bo sung thay the bang F9


        System.out.println("Search bill form " + form.getBillSerial());
        return super.searchBills(form, reqa, Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP, Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED, true);
    }

    /**
     * @funtion when user click to split bill
     * @Date 18/02/2009
     */
    public String assignBill() throws Exception {
        log.info("Begin.");
        /** Common Action Object */
        getReqSession();
        String pageForward = "assignBills";

        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);

        BillsDepartmentForm billsDepartmentForm = (BillsDepartmentForm) formBill;

        Long departmentType = null;
        Long departmentStaffId = null;
        String tempDepartmentCode = billsDepartmentForm.getCode();
        String tempDepartmentType = billsDepartmentForm.getBillDepartmentKind();

        if (tempDepartmentType != null && !tempDepartmentType.equals("")) {
            departmentType = new Long(tempDepartmentType);
            if (tempDepartmentCode != null && !tempDepartmentCode.trim().equals("")) {
                tempDepartmentCode = tempDepartmentCode.trim();
                List parameterList = new ArrayList();
                String queryString = "from " + ((tempDepartmentType.equals(Constant.SHOP_TYPE)) ? " Shop " : " Staff ");
                queryString += " where status = ? ";
                parameterList.add(Constant.STATUS_USE);

                if (tempDepartmentType.equals(Constant.SHOP_TYPE)) {
                    queryString += " and (parentShopId = ?) ";
                    parameterList.add(userToken.getShopId());                    
                    queryString += " and lower(shopCode) = lower(?) ";
                } else {
                    queryString += " and shopId = ? ";
                    parameterList.add(userToken.getShopId());
                    queryString += " and lower(staffCode) = lower(?) ";
                }
                parameterList.add(tempDepartmentCode);

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                List listShop = new ArrayList();
                listShop = queryObject.list();
                if (listShop.size() == 0) {
                    req.setAttribute("returnMsg", "retrieveBills.error.invalidDepartmentCode");
                    return pageForward;
                } else {
                    if (tempDepartmentType.equals(Constant.SHOP_TYPE)) {
                        Shop shop = (Shop) listShop.get(0);
                        departmentStaffId = shop.getShopId();
                    } else {
                        Staff staff = (Staff) listShop.get(0);
                        departmentStaffId = staff.getStaffId();
                    }
                }
            } else {
                req.setAttribute("returnMsg", "assignBills.error.invalidBillCode");
                return pageForward;
            }
        } else {
            req.setAttribute("returnMsg", "assignBills.error.invalidBillKind");
            return pageForward;
        }

        String[] assignBillIds = form.getReceivedBill();
        if (assignBillIds == null || assignBillIds.length == 0) {
            req.setAttribute("returnMsg", "assignBills.error.invalidBillIds");
            return pageForward;
        }

        Map<String, String> fromInvoiceMap = getFromInvoiceMap();
        Map<String, String> toInvoiceMap = getToInvoiceMap();
        //Long tempDepartmentType = new Long(departmentType);
        //Long shopStaffAssignedId = new Long(departmentStaffId);

        InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
        invoiceListDAO.setSession(getSession());
        for (int i = 0; i < assignBillIds.length; i++) {
            Long invoiceListId;
            Long toInvoice;
            Long fromInvoice;
            try {
                invoiceListId = new Long(assignBillIds[i].trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "Lỗi. Bạn chưa chọn dải hoá đơn cần giao!!!");
                pageForward = "assignBills";
                return pageForward;
            }
            
            try {
                toInvoice = new Long(toInvoiceMap.get(String.valueOf(invoiceListId)).trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "splitBill.error.invalidBillNum");
                pageForward = "assignBills";
                return pageForward;
            }
            
            try {
                fromInvoice = Long.parseLong(fromInvoiceMap.get(String.valueOf(invoiceListId)).trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "splitBill.error.invalidBillNum");
                pageForward = "assignBills";
                return pageForward;
            }

            
            if (toInvoice < fromInvoice) {
                req.setAttribute("returnMsg", "splitBill.error.invalidBillNum");
                pageForward = "assignBills";
                return pageForward;
            }

            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append("from InvoiceList where 1=1 ");
            sBuffer.append("    and invoiceListId = ? ");
            sBuffer.append("    and shopId = ? and staffId is null ");
            sBuffer.append("    and status = ? ");
            sBuffer.append("    and currInvoiceNo >= fromInvoice ");
            sBuffer.append("    and currInvoiceNo <= toInvoice ");
            sBuffer.append("    and fromInvoice <= ? ");
            sBuffer.append("    and toInvoice >= ? ");
            sBuffer.append("    and currInvoiceNo >= ? ");
            sBuffer.append("    and currInvoiceNo <= ? ");
            
            Query query = getSession().createQuery(sBuffer.toString());
            query.setParameter(0, invoiceListId);
            query.setParameter(1, userToken.getShopId());
            query.setParameter(2, Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
            query.setParameter(3, fromInvoice);
            query.setParameter(4, toInvoice);
            query.setParameter(5, fromInvoice);
            query.setParameter(6, toInvoice);
            List<InvoiceList> list =  query.list();
            if (list == null || list.size()==0){
                req.setAttribute("returnMsg", "Lỗi. Dải hoá đơn được chọn không có trong kho!!!");
                pageForward = "assignBills";
                return pageForward;
            }

            InvoiceList temp = list.get(0);
            Long toInvoiceOld = temp.getToInvoice();
            if (toInvoiceOld < toInvoice) {
                req.setAttribute("returnMsg", "splitBill.error.invalidBillNum");
                pageForward = "assignBills";
                return pageForward;
            }
            
            if (temp != null) {
                Long tempParentShopId = temp.getShopId();
                assignInvoiceList(temp, fromInvoice, toInvoice, userToken, departmentType,
                        tempParentShopId, departmentStaffId);
            }            
        }
        
        /** Reset interface */
        searchBills();
        req.setAttribute("returnMsg", "assignBills.success.assignInvoiceList");
        //session.setAttribute(billManagerViewHelper, null);
        log.info("End.");
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        return "assignBillsSearchResult";
    }

    private void assignInvoiceList(InvoiceList invoiceList, Long fromInvoice, Long toInvoice, UserToken userToken,
            Long tempDepartmentType, Long parentShopId, Long shopStaffAssignedId) throws Exception {
        log.info("Begin");

        //Luu tru tam thoi cac gia tri cua HD goc
        InvoiceList oldInvoiceList = new InvoiceList();
        oldInvoiceList.setUserCreated(invoiceList.getUserCreated());
        oldInvoiceList.setDateCreated(invoiceList.getDateCreated());
        oldInvoiceList.setUserAssign(invoiceList.getUserAssign());
        oldInvoiceList.setDateAssign(invoiceList.getDateAssign());

        //invoiceListTemp
        InvoiceListTempDAO invoiceListTempDAO = new InvoiceListTempDAO();
        invoiceListTempDAO.setSession(getSession());
        
        Long fromInvoiceTemp = invoiceList.getFromInvoice();
        Long toInvoiceTemp = invoiceList.getToInvoice();
        invoiceList.setUserAssign(userToken.getShopCode() + " - " + userToken.getShopName());
        invoiceList.setDateAssign(getSysdate());
        
        /** Old invoice: If currInvoice != fromInvoice -> Old was not assigned */
        if (!fromInvoiceTemp.equals(fromInvoice)) {
            invoiceList.setToInvoice(fromInvoice - 1);
            invoiceList.setCurrInvoiceNo(0L);//All invoice list was used
            invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);//All invoice list was used
            save(invoiceList);
        } else {
            /** If currInvoice == fromInvoice -> Assign current invoice */
            invoiceList.setToInvoice(toInvoice);
            if (tempDepartmentType.equals(ASSIGN_SHOP)) {
                invoiceList.setShopId(shopStaffAssignedId);
            } else if (tempDepartmentType.equals(ASSIGN_STAFF)) {
                invoiceList.setStaffId(shopStaffAssignedId);
            }
            invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED);
            save(invoiceList);
        }

        /** New invoice to assign */
        if (!fromInvoiceTemp.equals(fromInvoice)) {
            /** Create new invoice to assign */
            InvoiceList tem = new InvoiceList();
            tem.setSerialNo(invoiceList.getSerialNo());
            tem.setBookTypeId(invoiceList.getBookTypeId());
            tem.setBlockNo(invoiceList.getBlockNo());            
            tem.setUserCreated(oldInvoiceList.getUserCreated());
            tem.setDateCreated(oldInvoiceList.getDateCreated());

            tem.setInvoiceListId(getSequence("INVOICE_LIST_SEQ"));
            tem.setFromInvoice(fromInvoice);
            tem.setCurrInvoiceNo(fromInvoice);
            tem.setToInvoice(toInvoice);
            
            if (tempDepartmentType.equals(ASSIGN_SHOP)) {
                tem.setShopId(shopStaffAssignedId);
            } else if (tempDepartmentType.equals(ASSIGN_STAFF)) {
                tem.setStaffId(shopStaffAssignedId);
            }
            tem.setStatus(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED);

            tem.setUserAssign(oldInvoiceList.getUserAssign());
            tem.setDateAssign(oldInvoiceList.getDateAssign());
            save(tem);
        }

        /** New invoice not assign */
        if (!toInvoiceTemp.equals(toInvoice)) {
            InvoiceList tem = new InvoiceList();
            tem.setInvoiceListId(getSequence("INVOICE_LIST_SEQ"));
            tem.setSerialNo(invoiceList.getSerialNo());
            tem.setBookTypeId(invoiceList.getBookTypeId());
            tem.setBlockNo(invoiceList.getBlockNo());            
            tem.setUserCreated(oldInvoiceList.getUserCreated());
            tem.setDateCreated(oldInvoiceList.getDateCreated());

            tem.setFromInvoice(toInvoice + 1);
            tem.setCurrInvoiceNo(toInvoice + 1);
            tem.setToInvoice(toInvoiceTemp);
            tem.setShopId(parentShopId);
            tem.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);

            tem.setUserAssign(oldInvoiceList.getUserAssign());
            tem.setDateAssign(oldInvoiceList.getDateAssign());
            save(tem);
        }
        getSession().flush();
        deleteTemp(userToken);
        //Refresh interface
        getSession().flush();
        super.searchBills(form, req, Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP, Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED, true);

        if (tempDepartmentType.equals(ASSIGN_SHOP)) {
            saveInvoiceTransferLog(invoiceList, userToken, Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED, parentShopId, shopStaffAssignedId, null);
        } else if (tempDepartmentType.equals(ASSIGN_STAFF)) {
            saveInvoiceTransferLog(invoiceList, userToken, Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED, null, null, shopStaffAssignedId);
        }
        getSession().flush();
        log.info("End");
    }

    /**
     * @return List of Shop for display in interface
     */
    public List<Shop> findShopChildByShopId(Long shopId) {
        log.debug("finding all InvoiceList instances");
        try {
            String queryString = "select new Shop(a.shopId, a.name) " +
                    "from Shop a " +
                    "where a.parentShopId = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, shopId);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * @return List of Shop for display in interface
     */
    public List<Staff> findAllStaffByShopId(Long shopId) {
        log.debug("finding all InvoiceList instances");
        try {
            String queryString = "select new Staff(a.staffId, a.name) " +
                    "from Staff a " +
                    "where a.shopId = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, shopId);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
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
                queryString += " and a.shopId = ? and a.channelTypeId in (14) ";
                listParameter1.add(userToken.getShopId());
                if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                    queryString += " and lower(a.name) like ? ";
                    listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                }
            } else {
                queryString += " and (b.parentShopId = ?  and b.channelTypeId in (1) or b.shopId = ?) ";
                listParameter1.add(userToken.getShopId());
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
        if ("".equals(ShopCode)){
            return listImSearchBean;
        }
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
                queryString += " and (b.parentShopId = ?  and b.channelTypeId in (1) or b.shopId = ?) ";
                listParameter1.add(userToken.getShopId());
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

    /**
     *
     * @param form
     * @param req
     * @param status1
     * @param status2
     * @param shopAndStaff
     * @return
     * @throws Exception
     */
    public String searchBills(ActionForm form, HttpServletRequest req,
            Long status1, Long status2, boolean shopAndStaff) throws Exception {
        log.info("Begin.");
        /** Action common Object */
        getReqSession();
        String pageForward = "searchBills";
        Long shopId = (Long) reqSession.getAttribute("shopId");
        if (shopId == null) {
            return ERROR_PAGE;
        }
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userID = userToken.getUserID();
        if (userID == null) {
            log.info("Web.Session time out. UserID is missing");
            return ERROR_PAGE;
        }
        InvoiceListManagerViewHelper viewHelper = (InvoiceListManagerViewHelper) reqSession.getAttribute(billManagerViewHelper);
        if (viewHelper == null) {
            viewHelper = new InvoiceListManagerViewHelper();
            reqSession.setAttribute(billManagerViewHelper, viewHelper);
        }

        String usid = userToken.getUserID() + reqSession.getId();

        SearchBillForm searchBillForm = new SearchBillForm();
        searchBillForm = (SearchBillForm) form;

        Long bookType = null;
        String serialBill = null;
        Long status = null;
        String strStartNum = null;
        String strEndNum = null;
        Long startNum = null;
        Long endNum = null;

        String serialNo = searchBillForm.getBillSerial();

        if (searchBillForm.getBillSerialKey() != null && !searchBillForm.getBillSerialKey().trim().equals("")) {
            bookType = Long.parseLong(searchBillForm.getBillSerialKey());
            viewHelper.setBookType(bookType);
        }
        if (serialNo != null && !serialNo.trim().equals("")) {
            serialNo = serialNo.trim();
            Matcher m = htmlTag.matcher(serialNo);
            if (m.find()) {
                req.setAttribute("returnMsg", "assignBills.error.htmlTagBillSerial");
                searchBillForm.setBillSerial("");
                return pageForward;
            }
            viewHelper.setSerialNo(serialBill);
            String queryString = " from BookType where serialCode = ? and status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, serialNo);
            queryObject.setParameter(1, Constant.STATUS_USE);
            List listBookType = new ArrayList();
            listBookType = queryObject.list();
            if (listBookType.size() == 0) {
                req.setAttribute("returnMsg", "assignBills.error.invalidBillSerial");
                return pageForward;
            } else {
                BookType bBookType = (BookType) listBookType.get(0);
                bookType = bBookType.getBookTypeId();
            }
        }
        if (searchBillForm.getBillStartNumber() != null && !searchBillForm.getBillStartNumber().trim().equals("")) {
            strStartNum = searchBillForm.getBillStartNumber();
            try {
                startNum = Long.parseLong(strStartNum);
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "assignBills.error.invalidBillStartNum");
                return pageForward;
            }
        }
        if (searchBillForm.getBillEndNumber() != null && !searchBillForm.getBillEndNumber().trim().equals("")) {
            strEndNum = searchBillForm.getBillEndNumber();
            try {
                endNum = Long.parseLong(strEndNum);
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "assignBills.error.invalidBillEndNum");
                return pageForward;
            }
        }

        if (searchBillForm.getBillSituation() != null && !searchBillForm.getBillSituation().equals("")) {
            status = new Long(searchBillForm.getBillSituation());
            viewHelper.setStatus(status);
        }
        List invoiceListDisplay = new ArrayList();
        if (status != null) {
            if (!shopAndStaff) {
                invoiceListDisplay = searchInvoiceList(serialBill,
                        bookType, startNum, endNum, status, null, shopId);
            } else {
                if (status == 1) {
                    invoiceListDisplay = searchInvoiceListByShopAndStaff(serialBill,
                            bookType, status, null, shopId, usid, startNum, endNum);
                } else if (status == 2) {
                    invoiceListDisplay = searchInvoiceListByShopAndStaff(serialBill,
                            bookType, null, status, shopId, usid, startNum, endNum);
                }
            }
        } else {
            if (!shopAndStaff) {
                invoiceListDisplay = searchInvoiceList(serialBill,
                        bookType, startNum, endNum, status1, status2, shopId);
            } else {
                invoiceListDisplay = searchInvoiceListByShopAndStaff(serialBill,
                        bookType, status1, status2, shopId, usid, startNum, endNum);
            }
        }
        reqSession.setAttribute("invoiceList", invoiceListDisplay);
        if (invoiceListDisplay.size() != 0) {
            req.setAttribute("returnMsg", "assignBills.success.search");
            List listParamValue = new ArrayList();
            listParamValue.add(invoiceListDisplay.size());
            req.setAttribute("returnMsgValue", listParamValue);
        } else {
            req.setAttribute("returnMsg", "assignBills.unsuccess.search");
        }



        String assignBillsSuccess = (String) req.getParameter("assignBillsSuccess");
        if (assignBillsSuccess != null) {
            req.setAttribute("returnMsg", "assignBills.success.assignBills");
        }

        log.info("End.");
        return pageForward;
    }
}

