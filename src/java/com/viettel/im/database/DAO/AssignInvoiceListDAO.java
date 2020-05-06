package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.AssignInvoiceListForm;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.InvoiceListBean;
import com.viettel.im.client.form.BillsDepartmentForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.BookType;
import com.viettel.im.database.BO.InvoiceList;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import java.util.regex.Matcher;

/**
 *
 * author   :
 * date     :
 * desc     : cac nghiep vu lien quan den giao hoa don
 * modified : tamdt1, 21/10/2010
 * 
 */
public class AssignInvoiceListDAO extends BaseDAOAction {

    private static final Logger log = Logger.getLogger(AssignInvoiceListDAO.class);
    private AssignInvoiceListForm form;
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

    public AssignInvoiceListForm getForm() {
        return form;
    }

    public void setForm(AssignInvoiceListForm form) {
        this.form = form;
    }

    /**
     * @funtion prepare data when go to the interface
     * @Date 18/02/2009
     */
    public String preparePage() throws Exception {
        String pageForward = "assignBills";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        this.form = new AssignInvoiceListForm();
        this.form.setInvoiceType(Constant.INVOICE_TYPE_SALE);

        searchBills();

        return pageForward;
    }

    public String searchBills() throws Exception {
        String pageForward = "assignBills";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Long invoiceType = this.form.getInvoiceType();
        String serialCode = this.form.getSerialCode();
        Long fromInvoice = this.form.getFromInvoice();
        Long toInvoice = this.form.getToInvoice();
        String status = this.form.getStatus();

        BookTypeDAO bookTypeDAO = new BookTypeDAO();
        bookTypeDAO.setSession(this.getSession());
        BookType bookType = null;
        if (serialCode != null && !serialCode.trim().equals("")) {

            //check ky tu dac biet
            Matcher m = Constant.htmlTag.matcher(serialCode.trim());
            if (m.find()) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Ký hiệu hoá đơn không được chứa các ký tự đặc biệt!");
                this.form.setSerialCode("");
                this.form.setBlockName("");
                return pageForward;
            }            

            List<BookType> lstBookType = bookTypeDAO.getBySerialCode(serialCode, Constant.STATUS_USE);
            if (lstBookType == null || lstBookType.isEmpty()) {
                req.setAttribute(Constant.RETURN_MESSAGE, "assignBills.error.invalidBillSerial");
                return pageForward;
            }
            bookType = lstBookType.get(0);
            if (invoiceType != null) {
                if (bookType.getInvoiceType() != null && !bookType.getInvoiceType().equals(invoiceType)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "assignBills.error.invalidBillSerial");
                    return pageForward;
                }
            }
        }

        List parameterList = new ArrayList();
//        String queryString = "select new com.viettel.im.client.bean.InvoiceListBean"
        String queryString = "select "
                //                + "(a.invoiceListId, a.serialNo, b.blockName, a.blockNo, "
                + "a.invoice_List_Id as invoiceListId, a.serial_No as serialNo, b.block_Name as blockName, a.block_No as blockNo, "
                //                + "a.fromInvoice, a.toInvoice, a.currInvoiceNo, a.status, case when a.staffId is null then (c.shopCode || ' - ' || c.name) else (d.staffCode || ' - ' || d.name) end as ownerName) "
                + "a.from_Invoice as fromInvoice, a.to_Invoice as toInvoice, a.curr_Invoice_No as currInvoiceNo, a.status, case when a.staff_Id is null then (c.shop_Code || ' - ' || c.name) else (d.staff_Code || ' - ' || d.name) end as ownerName "
                + "from Invoice_List a, "
                + "Book_Type b, Shop c, Staff d ";
        queryString += "where b.book_Type_Id = a.book_Type_Id ";
        queryString += "and c.shop_Id = a.shop_Id ";
        queryString += "and a.staff_Id = d.staff_Id (+) ";
        queryString += "and a.curr_Invoice_No >= a.from_Invoice and a.curr_Invoice_No <= a.to_Invoice ";

        if (status == null || status.trim().equals("")) {
            queryString += "and ("
                    + "(a.status  = ? and c.shop_Id = ?) "
                    + "or "
                    + "(a.status  = ? and ((c.parent_Shop_Id = ? and a.staff_Id is null) "
                    + " or ( c.shop_Id = ? and a.staff_Id is not null))) "
                    + ") ";
            //trong kho don vi
            parameterList.add(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
            parameterList.add(userToken.getShopId());
            //da giao - chua xac nhan
            parameterList.add(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED);
            //giao cho cap duoi - chua xac nhan
            parameterList.add(userToken.getShopId());
            //giao cho nhan vien - chua xac nhan
            parameterList.add(userToken.getShopId());
        } else if (status.trim().equals(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP.toString())) {
            queryString += "and  a.status  = ? and c.shop_Id = ? ";
            parameterList.add(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
            parameterList.add(userToken.getShopId());
        } else if (status.trim().equals(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED.toString())) {

            //queryString += "and a.status  = ? and ((c.parent_Shop_Id = ? and a.staff_Id is null) or (and c.shop_Id = ? and a.staff_Id is not null))) ";

            queryString += "and (a.status  = ? and ((c.parent_Shop_Id = ? and a.staff_Id is null) "
                    + " or ( c.shop_Id = ? and a.staff_Id is not null))) ";

            parameterList.add(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED);
            parameterList.add(userToken.getShopId());
            parameterList.add(userToken.getShopId());
        } else {
//            queryString += "and a.status = -1 ";
            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Trạng thái hoá đơn không hợp lệ!");
            return pageForward;
        }
        //
        if (bookType != null) {
            queryString += "and a.book_Type_Id = ? ";
            parameterList.add(bookType.getBookTypeId());
        }else if (invoiceType != null){
            queryString += "and b.invoice_type = ? ";
            parameterList.add(invoiceType);
        }
        if (fromInvoice != null) {
            queryString += " and ( a.from_Invoice >= ? or ( a.from_Invoice <= ? and ? <= a.to_Invoice ))";
            parameterList.add(fromInvoice);
            parameterList.add(fromInvoice);
            parameterList.add(fromInvoice);
        }
        if (toInvoice != null) {
            queryString += " and ( a.to_Invoice <= ? or ( a.from_Invoice <= ? and ? <= a.to_Invoice ))";
            parameterList.add(toInvoice);
            parameterList.add(toInvoice);
            parameterList.add(toInvoice);
        }
        queryString += "order by a.serial_No, a.from_Invoice ";
        Query queryObject = getSession().createSQLQuery(queryString).
                addScalar("invoiceListId", Hibernate.LONG).
                addScalar("serialNo", Hibernate.STRING).
                addScalar("blockName", Hibernate.STRING).
                addScalar("blockNo", Hibernate.STRING).
                addScalar("fromInvoice", Hibernate.LONG).
                addScalar("toInvoice", Hibernate.LONG).
                addScalar("currInvoiceNo", Hibernate.LONG).
                addScalar("status", Hibernate.LONG).
                addScalar("ownerName", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(InvoiceListBean.class));

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List<InvoiceListBean> lstInvoiceList = queryObject.list();
        req.setAttribute("invoiceList", lstInvoiceList);

        if (!lstInvoiceList.isEmpty()) {
            req.setAttribute(Constant.RETURN_MESSAGE, "assignBills.success.search");
            List listParamValue = new ArrayList();
            listParamValue.add(lstInvoiceList.size());
            req.setAttribute(Constant.RETURN_MESSAGE_VALUE, listParamValue);
        } else {
            req.setAttribute(Constant.RETURN_MESSAGE, "assignBills.unsuccess.search");
        }

        return pageForward;
    }

    public String assignBill() throws Exception {
        String pageForward = "assignBills";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {//Time out session
            pageForward = Constant.ERROR_PAGE;
            return pageForward;
        }
        searchBills();//Tim kiem lai danh sach hoa don

        if (form.getOwnerType() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Bạn chưa chọn loại đối tượng nhận hoá đơn!!!");
            return pageForward;
        }
        if (form.getOwnerCode() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Bạn chưa chọn mã đối tượng nhận hoá đơn!!!");
            return pageForward;
        }

        //check ky tu dac biet
        Matcher m = Constant.htmlTag.matcher(form.getOwnerCode().trim());
        if (m.find()) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Mã đơn vị không được chứa các ký tự đặc biệt!");
            this.form.setOwnerCode("");
            this.form.setOwnerName("");
            return pageForward;
        }

        Long ownerId = this.checkOwnerCodeForAssignInvoice(userToken.getShopId(), form.getOwnerType(), form.getOwnerCode());
        if (ownerId.compareTo(0L) <= 0) {

            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Mã đối tượng không đúng!!!");
            return pageForward;
        }

        if (form.getLstSelectedInvoice() == null || form.getLstSelectedInvoice().length == 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Bạn chưa chọn dải hoá đơn cần giao!!!");
            return pageForward;
        }

        InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
        invoiceListDAO.setSession(this.getSession());
        BillBaseDAO billBaseDAO = new BillBaseDAO();
        billBaseDAO.setSession(this.getSession());
        boolean isOK = false;
        String errorMsg = "";

        if (form.getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
            for (int i = 0; i < form.getLstSelectedInvoice().length; i++) {
                boolean checkIsVTUnit = checkIsVTUnit(ownerId);
                if (checkIsVTUnit == false) {
                    String strInvoiceListId = form.getLstSelectedInvoice()[i];
                    if (strInvoiceListId == null || strInvoiceListId.trim().equals("")) {
                        break;
                    }
                    InvoiceList invoiceList = invoiceListDAO.findById(Long.valueOf(strInvoiceListId.trim()));
                    if (invoiceList == null
                            || invoiceList.getInvoiceListId() == null
                            || !invoiceList.getStatus().equals(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP)) {
                        break;
                    }
                    Long bookTypeId = invoiceList.getBookTypeId();
                    BookTypeDAO bookTypeDAO = new BookTypeDAO();
                    BookType bookType = bookTypeDAO.findById(bookTypeId);
                    Long invoiceType = bookType.getInvoiceType();
                    if (!invoiceType.equals(Constant.INVOICE_FOR_PAYMENT) && !invoiceType.equals(Constant.VOUCHER_FOR_PAYMENT)) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "assignBills.error.invalidInvoiceType");
                        return pageForward;
                    }
                }
            }
        }        
        
        for (int i = 0; i < form.getLstSelectedInvoice().length; i++) {
            isOK = false;
            String strInvoiceListId = form.getLstSelectedInvoice()[i];
            if (strInvoiceListId == null || strInvoiceListId.trim().equals("")) {
                break;
            }
            InvoiceList invoiceList = invoiceListDAO.findById(Long.valueOf(strInvoiceListId.trim()));
            if (invoiceList == null
                    || invoiceList.getInvoiceListId() == null
                    || !invoiceList.getStatus().equals(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP)) {
                break;
            }
            String strFromInvoice = fromInvoiceMap.get(strInvoiceListId.trim());
            Long fromInvoice = null;
            if (strFromInvoice == null || strFromInvoice.trim().equals("")) {
                errorMsg = "Từ số hoá đơn rỗng";
                break;
            }
            try{
                fromInvoice = Long.valueOf(strFromInvoice.trim());
            }catch(Exception ex){
                errorMsg = "Từ số HĐ không đúng";
                break;
            }

            Long toInvoice = null;
            String strToInvoice = toInvoiceMap.get(strInvoiceListId.trim());
            if (strToInvoice == null || strToInvoice.trim().equals("")) {
                errorMsg = "Đến số hoá đơn rỗng";
                break;
            }            
            try{
                toInvoice = Long.valueOf(strToInvoice.trim());
            }catch(Exception ex){
                errorMsg = "Đến số HĐ không đúng";
                break;
            }

            if (fromInvoice.compareTo(toInvoice) > 0) {
                errorMsg = "Từ số HĐ ("+strFromInvoice.trim()+") > đến số hoá đơn ("+ strToInvoice.trim() + ")";
                break;//tu so hoa don nho hon can thu hoi < so hoa don hien tai
            }
            
            if (!invoiceList.getCurrInvoiceNo().equals(fromInvoice)) {
                break;
            }
            if (invoiceList.getToInvoice().compareTo(toInvoice) < 0) {
                errorMsg = "Đến số HĐ ("+strToInvoice.trim()+") không hợp lệ";
                break;
            }

            //Thuc hien giao hoa don
            this.getSession().refresh(invoiceList, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

            if (invoiceList.getToInvoice().equals(toInvoice)) {
                //Truong hop giao ca dai
                //Chi update owner
                if (form.getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                    boolean checkIsVTUnit = checkIsVTUnit(ownerId);
                    if (checkIsVTUnit == false) {
                        invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF);
                    } else {
                        invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED);
                    }
                } else {
                    invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED);
                }                
                                
                invoiceList.setUserAssign(userToken.getStaffName());
                invoiceList.setDateAssign(this.getSysdate());
                
                if (form.getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
//                    invoiceList.setShopId(invoiceList.getShopId());
                    invoiceList.setStaffId(ownerId);
                } else if (form.getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                    invoiceList.setShopId(ownerId);
                    invoiceList.setStaffId(null);
                } else {
                    break;
                }

                invoiceList.setLastUpdateUserId(userToken.getUserID());
                invoiceList.setLastUpdateUserName(userToken.getStaffName());
                invoiceList.setLastUpdateTime(this.getSysdate());
                this.getSession().update(invoiceList);

                //Ghi log cho ca dai hoa don duoc giao
                if (form.getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                    billBaseDAO.saveInvoiceTransferLog(invoiceList, userToken, Constant.INVOICE_LOG_STATUS_ASSIGNED_UNCONFIRMED, userToken.getShopId(), ownerId, null);
                } else if (form.getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                    billBaseDAO.saveInvoiceTransferLog(invoiceList, userToken, Constant.INVOICE_LOG_STATUS_ASSIGNED_UNCONFIRMED, userToken.getShopId(), null, ownerId);
                } else {
                    break;
                }
            } else {
                //Truong hop giao 1 phan
                //Dai hoa don giao
                InvoiceList newInvoiceList = new InvoiceList();
                newInvoiceList.setInvoiceListId(this.getSequence("INVOICE_LIST_SEQ"));

                newInvoiceList.setBookTypeId(invoiceList.getBookTypeId());
                newInvoiceList.setBlockNo(invoiceList.getBlockNo());
                newInvoiceList.setSerialNo(invoiceList.getSerialNo());

                newInvoiceList.setFromInvoice(invoiceList.getCurrInvoiceNo());
                newInvoiceList.setToInvoice(toInvoice);
                newInvoiceList.setCurrInvoiceNo(invoiceList.getCurrInvoiceNo());
                newInvoiceList.setStatus(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED);

                if (form.getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                    newInvoiceList.setShopId(invoiceList.getShopId());
                    newInvoiceList.setStaffId(ownerId);
                } else if (form.getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                    newInvoiceList.setShopId(ownerId);
                    newInvoiceList.setStaffId(null);
                } else {
                    break;
                }

                newInvoiceList.setUserAssign(userToken.getStaffName());
                newInvoiceList.setDateAssign(this.getSysdate());

                newInvoiceList.setUserCreated(userToken.getStaffName());
                newInvoiceList.setDateCreated(this.getSysdate());

                newInvoiceList.setLastUpdateUserId(userToken.getUserID());
                newInvoiceList.setLastUpdateUserName(userToken.getStaffName());
                newInvoiceList.setLastUpdateTime(this.getSysdate());

                this.getSession().save(newInvoiceList);

                //Dai hoa don con lai
                invoiceList.setFromInvoice(toInvoice + 1);
                invoiceList.setCurrInvoiceNo(toInvoice + 1);
                invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);

                invoiceList.setLastUpdateUserId(userToken.getUserID());
                invoiceList.setLastUpdateUserName(userToken.getStaffName());
                invoiceList.setLastUpdateTime(this.getSysdate());
                this.getSession().update(invoiceList);

                //Ghi Log cho phan dau cua dai hoa don giao
                if (form.getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                    billBaseDAO.saveInvoiceTransferLog(newInvoiceList, userToken, Constant.INVOICE_LOG_STATUS_ASSIGNED_UNCONFIRMED, userToken.getShopId(), ownerId, null);
                } else if (form.getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                    billBaseDAO.saveInvoiceTransferLog(newInvoiceList, userToken, Constant.INVOICE_LOG_STATUS_ASSIGNED_UNCONFIRMED, userToken.getShopId(), null, ownerId);
                } else {
                    break;
                }
            }
            isOK = true;//dai hoa don da duoc giao
        }
        if (isOK) {//Neu khong co truong hop nao bi loi
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();
            searchBills();
            form.setOwnerType(null);
            form.setOwnerCode("");
            form.setOwnerName("");
            req.setAttribute(Constant.RETURN_MESSAGE, "assignBills.success.assignInvoiceList");
        } else {
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Giao hoá đơn thất bại! - (" + errorMsg + ")");
        }
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        searchBills();
        return "assignBillsSearchResult";





    }

    /**
     *
     * @param shopId
     * @param ownerType
     * @param ownerCode
     * @return
     */
    public Long checkOwnerCodeForAssignInvoice(Long shopId, Long ownerType, String ownerCode) {
        try {
            if (ownerType != null && ownerType.equals(Constant.OWNER_TYPE_SHOP)) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                Shop shop = shopDAO.findShopVTByCodeAndStatusAndUnit(ownerCode, Constant.STATUS_USE, Constant.IS_VT_UNIT);
                if (shop != null && shop.getShopId() != null && shop.getParentShopId().equals(shopId)) {
                    return shop.getShopId();
                } else {
                    return -1L;
                }
            } else if (ownerType != null && ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(ownerCode);
                if (staff != null && staff.getStaffId() != null && staff.getShopId() != null && staff.getShopId().equals(shopId)) {
                    return staff.getStaffId();
                } else {
                    return -2L;
                }
            } else {
                return -3L;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -4L;
//            return ex.getMessage();
        }
    }
    
    /**
     * @author : haint41
     * @since : 28/9/2011
     * @description : kiem tra nhan vien hay CTV
     * @param staffId
     * @return 
     */
    public boolean checkIsVTUnit(Long staffId) {
        String strQuery = "";
        try {
            strQuery = "select c.is_vt_unit "
                    + " from channel_type c,staff s "
                    + " where s.staff_id = ? "
                    + " and c.channel_type_id = s.channel_type_id";
            Query queryObject = getSession().createSQLQuery(strQuery);
            queryObject.setParameter(0, staffId);
            List lst = queryObject.list();
            if (lst.get(0).equals(Constant.IS_VT_UNIT)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            log.error("Loi khi check NV co thuoc VT hay ko : " + ex);
            return false;
        }
    }
}
