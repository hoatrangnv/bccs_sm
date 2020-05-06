package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.im.client.bean.ComboListBean;
import com.viettel.im.client.bean.InvoiceListManagerViewHelper;
import com.viettel.im.client.bean.RetrieveBillBean;
import com.viettel.im.client.form.SearchBillForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.BookType;
import com.viettel.im.database.BO.InvoiceDestroyed;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 * @author TungTV
 * @funtion BillManagement
 * @Date 18/02/2009
 */
public class ConfirmDestroyBillsDAO extends BillBaseDAO {

    private SearchBillForm form;
    Pattern htmlTag = Pattern.compile("<[^>]*>");

    public SearchBillForm getForm() {
        return form;
    }

    public void setForm(SearchBillForm form) {
        this.form = form;
    }
    private static final Logger log = Logger.getLogger(AuthenticateDAO.class);

    public String preparePage() throws Exception {
        log.info("Begin.");
        getReqSession();
        String pageForward = "confirmDestroyBills";
        /* Get ID of user */
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userID = userToken.getUserID();
        if (userID == null) {
            return ERROR_PAGE;
        }
        /** Get Shop ID of User */
        getListShopChild();
        List listBookTypes = findAllBookType();
        reqSession.setAttribute("listBookTypes", listBookTypes);
        reqSession.setAttribute("invoiceList", null);
        log.info("End.");
        return pageForward;
    }

    /**
     * @funtion prepare data when go to the interface
     * @Date 18/02/2009
     */
    //public ActionResultBO searchBills(ActionForm form, HttpServletRequest req) throws Exception {
    public String searchBills() throws Exception {
        log.info("Begin.");

        getReqSession();
        String pageForward = "confirmDestroyBillsSearchResult";

        /* Get ID of user */
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userID = userToken.getUserID();

        if (userID == null) {
            log.info("WEB. Session time out");
            return ERROR_PAGE;
        }
        SearchBillForm searchBillForm = new SearchBillForm();
        searchBillForm = (SearchBillForm) form;
        
        Long bookType = null;

        String serialNo = searchBillForm.getBillSerial();

        if (searchBillForm.getBillSerialKey() != null && !searchBillForm.getBillSerialKey().trim().equals("")) {
            bookType = Long.parseLong(searchBillForm.getBillSerialKey());
        }
        if (serialNo != null && !serialNo.trim().equals("")) {
            serialNo = serialNo.trim();
            Matcher m = htmlTag.matcher(serialNo);
            if (m.find()) {
                req.setAttribute("returnMsg", "confirmDestroyBills.error.htmlTagBillSerial");
                form.setBillSerial("");
                return pageForward;
            }
            String queryString = " from BookType where serialCode = ? and status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, serialNo);
            queryObject.setParameter(1, Constant.STATUS_USE);
            List listBookType = new ArrayList();
            listBookType = queryObject.list();
            if (listBookType.size() == 0) {
                req.setAttribute("returnMsg", "confirmDestroyBills.error.invalidBillSerial");
                return pageForward;
            } else {
                BookType bBookType = (BookType) listBookType.get(0);
                bookType = bBookType.getBookTypeId();
            }
        } else {
            req.setAttribute("returnMsg", "confirmDestroyBills.error.requiredBillSerial");
            return pageForward;
        }

        Long shopId = null;

        if(form.getSubDepartmentName() != null && !form.getSubDepartmentName().trim().equals("")){
            CommonDAO commonDAO = new CommonDAO();
            List<ComboListBean> list = commonDAO.getShopAndStaffList(userToken.getShopId(), null, form.getSubDepartmentName().trim(), Constant.OBJECT_TYPE_SHOP, 0,false,false);
            if(list.size() == 0){
                req.setAttribute("returnMsg", "confirmDestroyBills.error.invalidShopChildCode");
                return pageForward;
            } else {
                shopId = list.get(0).getObjId();
            }
        }

        /** Reserve search condition */
        InvoiceListManagerViewHelper invoiceListManagerViewHelper =
                (InvoiceListManagerViewHelper) reqSession.getAttribute(billManagerViewHelper);
        if (invoiceListManagerViewHelper == null) {
            invoiceListManagerViewHelper = new InvoiceListManagerViewHelper();
            reqSession.setAttribute(billManagerViewHelper, invoiceListManagerViewHelper);
        }
        invoiceListManagerViewHelper.setSerialNo(serialNo);
        invoiceListManagerViewHelper.setBookType(bookType);

//        if(shopId == null){
//            shopId = parentShopId;
//        }
        List<RetrieveBillBean> invoiceListDisplay = null;
        invoiceListDisplay = searchInvoiceListForConfirmDestroy(null, bookType, Constant.INVOICE_DESTROYED_STATUS_DESTROYED_UNAPPROVED, shopId);
        reqSession.setAttribute("invoiceList", invoiceListDisplay);
        //listShopChild();

        log.info("End.");
        if (invoiceListDisplay.size() > 0) {
            req.setAttribute("returnMsg", "Tìm thấy (" + invoiceListDisplay.size() + ") bản ghi");
            //req.setAttribute("returnMsg", "confirmDestroyBills.success.searchBills");
            //List listParamValue = new ArrayList();
            //listParamValue.add(invoiceListDisplay.size());
            //req.setAttribute("returnMsgValue", listParamValue);
            return pageForward;
        } else {
            req.setAttribute("returnMsg", "confirmDestroyBills.unsuccess.searchBills");
            return pageForward;
        }
    }

    /**
     * ham chi save vao danh sach hoa don huy ma chua move hoa don trong list can thong nhat lai voi Tung
     * @return
     * @throws java.lang.Exception
     */
    public String confirmDestroyed() throws Exception {
        log.info("anhtt Begin. confirmDestroyed");
        getReqSession();
        String pageForward = "confirmDestroyBillsSearchResult";
        /* Get ID of user */
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
        String[] receivedBillsId = getForm().getReceivedBill();
        if (receivedBillsId == null) {
            req.setAttribute("returnMsg", "confirmDestroyBills.error.requiredBillsId");
            return pageForward;
        }
        /** Confirm for Shop */
        int counter = 0;
        for (int i = 0; i < receivedBillsId.length; i++) {
            String tempId = receivedBillsId[i];
            if (tempId != null && !tempId.equals("")) {
                Long tempInvoiceListId = new Long(tempId);
                InvoiceDestroyed invoiceDestroyed = getInvoiceDestroyedById(tempInvoiceListId);
                if (invoiceDestroyed.getStatus().compareTo(Constant.INVOICE_DESTROYED_STATUS_DESTROYED_UNAPPROVED)!=0){
                    continue;
                }
                invoiceDestroyed.setStatus(Constant.INVOICE_DESTROYED_STATUS_DESTROYED_APPROVED);
                save(invoiceDestroyed);
                /** Now we save log */
                saveInvoiceTransferLog(invoiceDestroyed, userToken, Constant.INVOICE_LOG_STATUS_DESTROYED_APPROVED);

                counter++;
            }
        }
        if (counter == 0){
            req.setAttribute("returnMsg", "Lỗi. Không có dải hoá đơn nào được duyệt huỷ!");
            log.info("End. confirmDestroyed");
            return pageForward;
        }

        getSession().flush();
        searchBills();
        /*SearchBillForm searchBillForm = new SearchBillForm();
        searchBillForm = (SearchBillForm) form;

        Long bookType = null;

        String serialNo = searchBillForm.getBillSerial();
        if (searchBillForm.getBillSerialKey() != null && !searchBillForm.getBillSerialKey().trim().equals("")) {
            bookType = Long.parseLong(searchBillForm.getBillSerialKey());
        }
        if (serialNo != null && !serialNo.trim().equals("")) {
            serialNo = serialNo.trim();
            String queryString = " from BookType where serialCode = ? and status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, serialNo);
            queryObject.setParameter(1, Constant.STATUS_USE);
            List listBookType = new ArrayList();
            listBookType = queryObject.list();
            if (listBookType.size() == 0) {
                req.setAttribute("returnMsg", "confirmDestroyBills.error.invalidBillSerial");
                return pageForward;
            } else {
                BookType bBookType = (BookType) listBookType.get(0);
                bookType = bBookType.getBookTypeId();
            }
        } else {
            req.setAttribute("returnMsg", "confirmDestroyBills.error.requiredBillSerial");
            return pageForward;
        }

        Long parentShopId = null;

        if(form.getBillDepartmentName() == null || form.getBillDepartmentName().equals("0")){
            req.setAttribute("returnMsg", "confirmDestroyBills.error.requiredBillDepartmentName");
            return pageForward;
        } else{
            parentShopId = Long.parseLong(form.getBillDepartmentName());
        }

        Long shopId = null;

        if(form.getSubDepartmentName() != null && !form.getSubDepartmentName().trim().equals("")){
            String shopChildCode = form.getSubDepartmentName();
            String queryString = " select new com.viettel.im.database.BO.Shop" +
                    " (a.shopId, a.shopCode, a.name, a.shopCode)" +
                    " from Shop a , ChannelType b " +
                    " where a.channelTypeId = b.channelTypeId " +
                    " and a.parentShopId = ? " +
                    " and a.status = ? " +
                    " and b.objectType = 1 " +
                    " and b.isVtUnit = 1 " +
                    " and a.shopCode = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, parentShopId);
            queryObject.setParameter(1, Constant.STATUS_USE);
            queryObject.setParameter(2, shopChildCode);
            List<Shop> listShop = new ArrayList();
            listShop = queryObject.list();
            if(listShop.size() == 0){
                req.setAttribute("returnMsg", "confirmDestroyBills.error.invalidShopChildCode");
                return pageForward;
            } else {
                shopId = listShop.get(0).getShopId();
            }
        }

//        Long departmentName = 0L;
//
//        if (getForm().getBillDepartmentName() != null && !getForm().getBillDepartmentName().equals("0")) {
//            departmentName = Long.parseLong(getForm().getBillDepartmentName());
//        }
//        if (getForm().getSubDepartmentName() != null && !getForm().getSubDepartmentName().equals("0")) {
//            departmentName = Long.parseLong(getForm().getSubDepartmentName());
//        }

        if(shopId == null){
            shopId = parentShopId;
        }
        List<RetrieveBillBean> invoiceListDisplay;
        invoiceListDisplay = searchInvoiceListForConfirmDestroy(serialNo,
                bookType, STAFF_DESTROYED_UNCONFIRMED, shopId);
        session.setAttribute("invoiceList", invoiceListDisplay);
        */
        req.setAttribute("returnMsg", "confirmDestroyBills.success.confirmDestroyBills");
        log.info("End. confirmDestroyed");
        return pageForward;
    }

    public String pageNavigator() {
        return "pageNavigator";
    }

    /**
     * @return List of Invoice List for display in interface
     */
    public List<RetrieveBillBean> searchInvoiceListForConfirmDestroy(String serialNo, Long bookTypeId,
            Long status, Long shopId) {
        log.debug("finding all InvoiceList instances");
        try {
            getReqSession();
//            UserToken userToken = (UserToken) session.getAttribute(USER_TOKEN_ATTRIBUTE);
//            Long parentStaffId = userToken.getShopId();
            String queryString = "";
            List parameterList = new ArrayList();
//            if (departmentType == 2) {
                queryString = "select " +
                        "a.name as shopName, " +
                        "e.name as destroyer, " +
                        "b.invoice_destroyed_id as invoiceDestroyedId, " +
                        "b.invoice_list_id as invoiceListId, " +
                        "b.serial_no as serialNo, " +
                        "c.block_name as blockName, " +
                        "b.block_no as blockNo, " +
                        "b.from_invoice as fromInvoice, " +
                        "b.to_invoice as toInvoice, " +
                        "b.date_created as dateCreated, " +
                        "d.reason_name as destroyInvoiceReason " +
                        "from shop a " +
                        "join invoice_destroyed b " +
                        "on a.shop_id = b.shop_id " +
                        "join book_type c " +
                        "on c.book_type_id = b.book_type_id " +
                        "join reason d " +
                        "on d.reason_id = b.reason_id " +
                        "join staff e " +
                        "on b.destroyer = e.staff_id " +
                        "where 1=1 ";
                if (shopId != null){
                    queryString += " and a.shop_id = ? ";
                    parameterList.add(shopId);
                }
                
                if (status != null) {
                    /* Status of Bill */
                    queryString += "and b.status = ? ";
                    parameterList.add(status);
                }
                if (serialNo != null && !serialNo.equals("")) {
                    queryString += "and b.serial_no = ? ";
                    parameterList.add(serialNo);
                }
                if (bookTypeId != null) {
                    queryString += "and b.book_type_id = ? ";
                    parameterList.add(bookTypeId);
                }
                queryString += "union ";
                queryString += "select " +
                        "a.name as shopName, " +
                        "e.name as destroyer, " +
                        "b.invoice_destroyed_id as invoiceDestroyedId, " +
                        "b.invoice_list_id as invoiceListId, " +
                        "b.serial_no as serialNo, " +
                        "c.block_name as blockName, " +
                        "b.block_no as blockNo, " +
                        "b.from_invoice as fromInvoice, " +
                        "b.to_invoice as toInvoice, " +
                        "b.date_created as dateCreated, " +
                        "d.reason_name as destroyInvoiceReason " +
                        "from shop a " +
                        "join invoice_destroyed b " +
                        "on a.shop_id = b.shop_id " +
                        "join book_type c " +
                        "on c.book_type_id = b.book_type_id " +
                        "join reason d " +
                        "on d.reason_id = b.reason_id " +
                        "join staff e " +
                        "on b.destroyer = e.staff_id " +
                        "where 1=1 ";
                if (shopId != null){
                    queryString += " and a.parent_shop_id = ? ";
                    parameterList.add(shopId);
                }
                if (status != null) {
                    /* Status of Bill */
                    queryString += "and b.status = ? ";
                    parameterList.add(status);
                }
                if (serialNo != null && !serialNo.equals("")) {
                    queryString += "and b.serial_no = ? ";
                    parameterList.add(serialNo);
                }
                if (bookTypeId != null) {
                    queryString += "and b.book_type_id = ? ";
                    parameterList.add(bookTypeId);
                }
                queryString +=" ORDER BY shopname ASC ,serialno ASC ,blockno ASC ,frominvoice asc ";
                Session session = getSession();
                Query queryObject = session.createSQLQuery(queryString).
                        addScalar("shopName", Hibernate.STRING).
                        addScalar("destroyer", Hibernate.STRING).
                        addScalar("invoiceDestroyedId", Hibernate.LONG).
                        addScalar("invoiceListId", Hibernate.LONG).
                        addScalar("serialNo", Hibernate.STRING).
                        addScalar("blockName", Hibernate.STRING).
                        addScalar("blockNo", Hibernate.STRING).
                        addScalar("fromInvoice", Hibernate.LONG).
                        addScalar("toInvoice", Hibernate.LONG).
                        addScalar("dateCreated", Hibernate.DATE).
                        addScalar("destroyInvoiceReason", Hibernate.STRING).
                        setResultTransformer(Transformers.aliasToBean(RetrieveBillBean.class));
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                return queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }


    public void getListShopChild() {
        log.debug("finding all InvoiceList instances");
        try {
            getReqSession();
            UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
            Long userID = userToken.getUserID();
            Long shopId = getShopIDByStaffID(userID);
            List shopList = new ArrayList();

            Long shopType = Long.parseLong(ResourceBundleUtils.getResource("BRANCH"));
            String queryString = " from Shop a " +
                    "where a.parentShopId = ? and a.channelTypeId = ? and a.status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, shopId);
            queryObject.setParameter(1, shopType);
            queryObject.setParameter(2, Constant.STATUS_USE);

            shopList = queryObject.list();

            req.setAttribute("shopList", shopList);

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }
    /**
     * @return List of Invoice List for display in interface
     */
    private Map shopListCombo = new HashMap();

    private Map lstItems = new HashMap();

    public Map getLstItems() {
        return lstItems;
    }

    public void setLstItems(Map lstItems) {
        this.lstItems = lstItems;
    }
    

    public String getListShopChildCode(){
        try{
            UserToken userToken = (UserToken) getRequest().getSession().getAttribute(Constant.USER_TOKEN);
            String objCode = getRequest().getParameter("form.subDepartmentName");
            if (objCode != null) {
                if ("".equals(objCode)) {
                    return "success";
                }
                CommonDAO commonDAO = new CommonDAO();
                List<ComboListBean> list = commonDAO.getShopAndStaffList(userToken.getShopId(), null, objCode, Constant.OBJECT_TYPE_SHOP, 0, false, false);

                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                            shopListCombo.put(list.get(i).getObjName(), list.get(i).getObjCode());
                        }
                    return "success";
                }
            } else {
                shopListCombo.clear();
            }            
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return "success";
    }


    public String getShopName() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String ShopId = httpServletRequest.getParameter("shopId");
            if (ShopId == null || ShopId.trim().equals(""))
                return "successShopName";
            
            System.out.println("ShopId: " + ShopId);

            CommonDAO commonDAO = new CommonDAO();
            List<ComboListBean> list = commonDAO.getShopAndStaffList(null,Long.valueOf(ShopId),"",Constant.OBJECT_TYPE_SHOP,0,false,false);
            if (list!= null && list.size()>0){
                lstItems.put("shopName", list.get(0).getObjName());
                System.out.println("ShopName: " + list.get(0).getObjName());
            }
            else
                lstItems.put("shopName", "");
            return "successShopName";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "successShopName";
    }
    

    /**
     * @return the searchBillForm
     */
    public Map getShopListCombo() {
        return shopListCombo;
    }

    public void setShopListCombo(Map shopListCombo) {
        this.shopListCombo = shopListCombo;
    }
}

