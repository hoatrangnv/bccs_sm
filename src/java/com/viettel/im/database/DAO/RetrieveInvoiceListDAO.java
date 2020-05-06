/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.AssignInvoiceListForm;

import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.InvoiceListBean;
import com.viettel.im.client.bean.InvoiceListManagerViewHelper;
import com.viettel.im.client.bean.RetrieveBillBean;
import com.viettel.im.client.form.BillsDepartmentForm;
import com.viettel.im.client.form.SearchBillForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.BookType;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.InvoiceList;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author tronglv
 */
public class RetrieveInvoiceListDAO extends BaseDAOAction {

    private static final Logger log = Logger.getLogger(RetrieveInvoiceListDAO.class);
    private AssignInvoiceListForm form;
    private Map<String, String> fromInvoiceMap;
    private Map<String, String> toInvoiceMap;
    Pattern htmlTag = Pattern.compile("<[^>]*>");

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

    public String preparePage() throws Exception {
        String pageForward = "retrieveBills";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        this.form = new AssignInvoiceListForm();
        this.form.setInvoiceType(Constant.INVOICE_TYPE_SALE);

        searchBills();

        return pageForward;
    }

    /**
     * @funtion prepare data when go to the interface
     * @Date 18/02/2009
     */
    public String searchBills() throws Exception {
        String pageForward = "searchBills";

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Long invoiceType = this.form.getInvoiceType();
        String serialCode = this.form.getSerialCode();
        Long fromInvoice = this.form.getFromInvoice();
        Long toInvoice = this.form.getToInvoice();
        String status = this.form.getStatus();


        if (fromInvoice != null && toInvoice != null && fromInvoice.compareTo(toInvoice) > 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Từ số hoá đơn không được lớn hơn đến số HĐ!");
            return pageForward;
        }

        BookTypeDAO bookTypeDAO = new BookTypeDAO();
        bookTypeDAO.setSession(this.getSession());
        AssignInvoiceListDAO assignInvoiceListDAO = new AssignInvoiceListDAO();
        assignInvoiceListDAO.setSession(this.getSession());

        //kiem tra owner_id
        Long ownerId = null;
        if (this.form.getOwnerType() != null
                && this.form.getOwnerCode() != null
                && !this.form.getOwnerCode().trim().equals("")) {

            //check ky tu dac biet
            Matcher m = Constant.htmlTag.matcher(form.getOwnerCode().trim());
            if (m.find()) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Mã đơn vị không được chứa các ký tự đặc biệt!");
                this.form.setOwnerCode("");
                this.form.setOwnerName("");
                return pageForward;
            }

            ownerId = assignInvoiceListDAO.checkOwnerCodeForAssignInvoice(userToken.getShopId(), form.getOwnerType(), form.getOwnerCode());
            if (ownerId.compareTo(0L) <= 0) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Mã đối tượng không đúng!!!");
                return pageForward;
            }
            form.setOwnerId(ownerId);
        }

        //kiem tra book_type_id
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
        String queryString = "select "
                + "a.invoice_List_Id as invoiceListId, a.serial_No as serialNo, b.block_Name as blockName, a.block_No as blockNo, "
                + "a.from_Invoice as fromInvoice, a.to_Invoice as toInvoice, a.curr_Invoice_No as currInvoiceNo, a.status, case when a.staff_Id is null then (c.shop_Code || ' - ' || c.name) else (d.staff_Code || ' - ' || d.name) end as ownerName "
                + "from Invoice_List a, "
                + "Book_Type b, Shop c, Staff d ";
        queryString += "where b.book_Type_Id = a.book_Type_Id ";
        queryString += "and c.shop_Id = a.shop_Id ";
        queryString += "and a.staff_Id = d.staff_Id (+) ";
        queryString += "and a.curr_Invoice_No >= a.from_Invoice and a.curr_Invoice_No <= a.to_Invoice ";

        //owner_id
        if (ownerId != null) {
            if (form.getOwnerType() != null && form.getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                queryString += "and a.shop_id = ? and a.staff_id is null ";
                parameterList.add(ownerId);
            } else if (form.getOwnerType() != null && form.getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                queryString += "and a.shop_id = ? and a.staff_id = ? ";
                parameterList.add(userToken.getShopId());
                parameterList.add(ownerId);
            } else if (form.getOwnerType() != null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Loại đối tượng không đúng!!!");
                return pageForward;
            }
        } else {
            if (form.getOwnerType() != null && form.getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                queryString += "and a.staff_id is null ";
            } else if (form.getOwnerType() != null && form.getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                queryString += "and a.staff_id is not null ";
            } else if (form.getOwnerType() != null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Loại đối tượng không đúng!!!");
                return pageForward;
            }
        }


        //parent_owner_id
        queryString += "and ("
                + "(c.parent_Shop_Id = ? and a.staff_Id is null) "
                + " or ( c.shop_Id = ? and a.staff_Id is not null) "
                + ") ";
        parameterList.add(userToken.getShopId());
        parameterList.add(userToken.getShopId());

        if (status != null && status.trim().equals(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP.toString())) {
            queryString += "and  (a.status  = ? or a.status  = ? ) ";
            parameterList.add(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
            parameterList.add(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF);
        } else if (status != null && status.trim().equals(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED.toString())) {
            queryString += "and  a.status  = ? ";
            parameterList.add(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED);
        } else if (status != null && !status.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Trạng thái hoá đơn không đúng!!!");
            return pageForward;
        }
        //
        if (bookType != null) {
            queryString += "and a.book_Type_Id = ? ";
            parameterList.add(bookType.getBookTypeId());
        } else if (invoiceType != null) {
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

        if (lstInvoiceList.size() != 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "retrieveBills.success.search");
            List listParamValue = new ArrayList();
            listParamValue.add(lstInvoiceList.size());
            req.setAttribute(Constant.RETURN_MESSAGE_VALUE, listParamValue);
        } else {
            req.setAttribute(Constant.RETURN_MESSAGE, "retrieveBills.unsuccess.search");
        }

        return pageForward;
    }

    public String retrieveBillComplete() throws Exception {
        String pageForward = "retrieveBillComplete";

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {//Time out session
            pageForward = Constant.ERROR_PAGE;
            return pageForward;
        }
        searchBills();//Tim kiem lai danh sach hoa don

        if (this.form.getLstSelectedInvoice() == null || this.form.getLstSelectedInvoice().length == 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Bạn chưa chọn dải hoá đơn cần thu hồi!!!");
            return pageForward;
        }
        InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
        invoiceListDAO.setSession(this.getSession());
        BillBaseDAO billBaseDAO = new BillBaseDAO();
        billBaseDAO.setSession(this.getSession());
        boolean isOK = false;

        String errorMsg = "";

        for (int i = 0; i < this.form.getLstSelectedInvoice().length; i++) {
            isOK = false;
            String strInvoiceListId = this.form.getLstSelectedInvoice()[i];
            if (strInvoiceListId == null || strInvoiceListId.trim().equals("")) {
                break;
            }
            InvoiceList invoiceList = invoiceListDAO.findById(Long.valueOf(strInvoiceListId.trim()));
            if (invoiceList == null
                    || invoiceList.getInvoiceListId() == null) {
                break;
            }
            Long fromInvoice = null;
            String strFromInvoice = this.fromInvoiceMap.get(strInvoiceListId.trim());
            if (strFromInvoice == null || strFromInvoice.trim().equals("")) {
                errorMsg = "Từ số hoá đơn rỗng";
                break;
            }
            try {
                fromInvoice = Long.valueOf(strFromInvoice.trim());
            } catch (Exception ex) {
                errorMsg = "Từ số HĐ không đúng";
                break;
            }
            Long toInvoice = null;
            String strToInvoice = this.toInvoiceMap.get(strInvoiceListId.trim());
            if (strToInvoice == null || strToInvoice.trim().equals("")) {
                errorMsg = "Đến số hoá đơn rỗng";
                break;
            }
            try {
                toInvoice = Long.valueOf(strToInvoice.trim());
            } catch (Exception ex) {
                errorMsg = "Đến số HĐ không đúng";
                break;
            }

            //Khong cho phep nhap tu so hoa don < so hd hien tai cua dai
//            if (invoiceList.getCurrInvoiceNo().compareTo(toInvoice) < 0) {
//                errorMsg = "Từ số HĐ (" + strFromInvoice.trim() + ") không hợp lệ";
//                break;//tu so hoa don nho hon can thu hoi < so hoa don hien tai
//            }

            if (fromInvoice.compareTo(toInvoice) > 0) {
                errorMsg = "Từ số HĐ (" + strFromInvoice.trim() + ") > đến số hoá đơn (" + strToInvoice.trim() + ")";
                break;//tu so hoa don nho hon can thu hoi < so hoa don hien tai
            }

            if (!invoiceList.getToInvoice().equals(toInvoice)) {
                break;//den so hoa don thu hoi <> so hoa don cuoi dai
            }
            if (invoiceList.getCurrInvoiceNo().compareTo(fromInvoice) > 0) {
                errorMsg = "Từ số HĐ (" + strFromInvoice.trim() + ") không hợp lệ";
                break;//tu so hoa don nho hon can thu hoi < so hoa don hien tai
            }

            if (invoiceList.getStaffId() != null) {
                if (!invoiceList.getShopId().equals(userToken.getShopId())) {
                    break;
                }
            } else {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                Shop shop = shopDAO.findById(invoiceList.getShopId());
                if (shop == null
                        || shop.getShopId() == null
                        || shop.getParentShopId() == null
                        || !shop.getParentShopId().equals(userToken.getShopId())) {
                    break;
                }
            }

            //Thuc hien thu hoi hoa don
            this.getSession().refresh(invoiceList, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

            if (invoiceList.getFromInvoice().equals(invoiceList.getCurrInvoiceNo())
                    && invoiceList.getCurrInvoiceNo().equals(fromInvoice)) {
                //Truong hop dai con nguyen & thu hoi ca dai
                //Chi update owner & status
                
                Long oldShopId = invoiceList.getShopId();
                Long oldStaffId = invoiceList.getStaffId();
                
                invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
                invoiceList.setShopId(userToken.getShopId());
                invoiceList.setStaffId(null);

                invoiceList.setLastUpdateUserId(userToken.getUserID());
                invoiceList.setLastUpdateUserName(userToken.getStaffName());
                invoiceList.setLastUpdateTime(this.getSysdate());
                this.getSession().update(invoiceList);
                
                //120418:Tronglv bo sung log thu hoi hoa don ca dai
                //Ghi log cho ca dai hoa don duoc giao                
                if (oldStaffId != null) {
                    billBaseDAO.saveInvoiceTransferLog(invoiceList, userToken, Constant.INVOICE_LOG_STATUS_RETRIEVED, userToken.getShopId(), null, oldStaffId);
                } else if (oldShopId != null) {
                    billBaseDAO.saveInvoiceTransferLog(invoiceList, userToken, Constant.INVOICE_LOG_STATUS_RETRIEVED, userToken.getShopId(), oldShopId, null);
                } else {
                    break;//kiem tra thay dai hoa don khong thuoc don vi nao
                }                
                
            } else {
                //Tao dai thu hoi moi
                InvoiceList newInvoiceList = new InvoiceList();
                newInvoiceList.setInvoiceListId(this.getSequence("INVOICE_LIST_SEQ"));

                newInvoiceList.setBookTypeId(invoiceList.getBookTypeId());
                newInvoiceList.setBlockNo(invoiceList.getBlockNo());
                newInvoiceList.setSerialNo(invoiceList.getSerialNo());

                newInvoiceList.setFromInvoice(fromInvoice);
                newInvoiceList.setToInvoice(invoiceList.getToInvoice());
                newInvoiceList.setCurrInvoiceNo(fromInvoice);
                newInvoiceList.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);

                newInvoiceList.setShopId(userToken.getShopId());
                newInvoiceList.setStaffId(null);

                newInvoiceList.setUserAssign(userToken.getStaffName());
                newInvoiceList.setDateAssign(this.getSysdate());

                newInvoiceList.setUserCreated(userToken.getStaffName());
                newInvoiceList.setDateCreated(this.getSysdate());

                newInvoiceList.setLastUpdateUserId(userToken.getUserID());
                newInvoiceList.setLastUpdateUserName(userToken.getStaffName());
                newInvoiceList.setLastUpdateTime(this.getSysdate());

                this.getSession().save(newInvoiceList);

                //Ghi log cho ca dai hoa don duoc giao
                if (invoiceList.getStaffId() != null) {
                    billBaseDAO.saveInvoiceTransferLog(newInvoiceList, userToken, Constant.INVOICE_LOG_STATUS_RETRIEVED, userToken.getShopId(), null, invoiceList.getStaffId());
                } else if (invoiceList.getShopId() != null) {
                    billBaseDAO.saveInvoiceTransferLog(newInvoiceList, userToken, Constant.INVOICE_LOG_STATUS_RETRIEVED, userToken.getShopId(), invoiceList.getShopId(), null);
                } else {
                    break;//kiem tra thay dai hoa don khong thuoc don vi nao
                }

                invoiceList.setToInvoice(Long.valueOf(strFromInvoice.trim()) - 1);

                if (invoiceList.getCurrInvoiceNo().equals(Long.valueOf(strFromInvoice.trim()))) {
                    //Truong hop thu hoi ca dai con lai
                    //neu to_invoice = curr_invoice_no => update curr_invoice_no & status
                    invoiceList.setCurrInvoiceNo(0L);
                    invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
                }

                if (invoiceList.getCurrInvoiceNo().compareTo(invoiceList.getToInvoice()) > 0) {
                    invoiceList.setCurrInvoiceNo(invoiceList.getToInvoice());
                }

                invoiceList.setLastUpdateUserId(userToken.getUserID());
                invoiceList.setLastUpdateUserName(userToken.getStaffName());
                invoiceList.setLastUpdateTime(this.getSysdate());

                this.getSession().update(invoiceList);
            }
            isOK = true;//dai hoa don da duoc thu hoi
        }

        if (isOK) {//Neu khong co truong hop nao bi loi
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();
            searchBills();
            req.setAttribute(Constant.RETURN_MESSAGE, "retrieveBills.success.retrieveBills");
        } else {
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi. Thu hồi hoá đơn thất bại! - (" + errorMsg + ")");
        }

        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin.");
        searchBills();
        String forward = "pageNavigator";
        return forward;
    }

    public List<ImSearchBean> getListObject(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        try {
            HttpServletRequest req = imSearchBean.getHttpServletRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                String objType = imSearchBean.getOtherParamValue().trim();

                String strQuery = "";
                Query query;
                List lstParam = new ArrayList();
                if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {
                    strQuery = " select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) from Shop a ";
                    strQuery += " where 1=1 and a.status=? and a.parentShopId=? ";
                    lstParam.add(Constant.STATUS_USE);
                    lstParam.add(userToken.getShopId());

                    strQuery+=" and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ";
                    lstParam.add(Constant.OBJECT_TYPE_SHOP);
                    lstParam.add(Constant.IS_VT_UNIT);

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += "and lower(a.shopCode) like ? ";
                        lstParam.add(imSearchBean.getCode().trim().toLowerCase() + "%");
                    }

                    query = getSession().createQuery(strQuery);
                } else if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                    strQuery = " select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) from Staff a ";
                    strQuery += " WHERE 1=1 and a.status=? and a.shopId=? ";
                    lstParam.add(Constant.STATUS_USE);
                    lstParam.add(userToken.getShopId());

                    //Modified by : TrongLV
                    //Modified date : 2011-08-16
                    strQuery+=" and (a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ";
                    lstParam.add(Constant.OBJECT_TYPE_STAFF);
                    lstParam.add(Constant.IS_VT_UNIT);
                    
                    //haint41 28/9/2011 : bo sung them CTV
                    strQuery += " or  a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?)) ";
                    lstParam.add(Constant.OBJECT_TYPE_STAFF);
                    lstParam.add(Constant.IS_NOT_VT_UNIT);

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += "and lower(a.staffCode) like ? ";
                        lstParam.add(imSearchBean.getCode().trim().toLowerCase() + "%");
                    }

                    query = getSession().createQuery(strQuery);
//                } else if (objType.equals(Constant.OBJECT_TYPE_CTV)) {
//                    strQuery = " select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) from Staff a ";
//                    strQuery += " WHERE 1=1 and a.status=? and a.shopId=? ";
//                    lstParam.add(Constant.STATUS_USE);
//                    lstParam.add(userToken.getShopId());
//
//                    //Modified by : TrongLV
//                    //Modified date : 2011-08-16
//                    strQuery+=" and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ";        
//                    lstParam.add(Constant.OBJECT_TYPE_STAFF);
//                    lstParam.add(Constant.IS_NOT_VT_UNIT);
//
//                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
//                        strQuery += "and lower(a.staffCode) like ? ";
//                        lstParam.add(imSearchBean.getCode().trim().toLowerCase() + "%");
//                    }
//
//                    query = getSession().createQuery(strQuery);
//                } 
                } else {
                    throw new Exception("Loại đối tượng không đúng");
                }
                for (int idx = 0; idx < lstParam.size(); idx++) {
                    query.setParameter(idx, lstParam.get(idx));
                }

                List<ImSearchBean> tmpList1 = query.list();
                if (tmpList1 != null && tmpList1.size() > 0) {
                    listImSearchBean.addAll(tmpList1);
                }
            } else {
                throw new Exception("Bạn phải chọn loại đối tượng");
            }
            return listImSearchBean;
        } catch (Exception ex) {
            ex.printStackTrace();
            return listImSearchBean;
        }
    }

    public List<ImSearchBean> getObjectName(ImSearchBean imSearchBean) {
        try {
            CommonDAO commonDAO = new CommonDAO();
            commonDAO.setSession(this.getSession());
            imSearchBean.setHttpServletRequest(this.getRequest());
            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                String objType = imSearchBean.getOtherParamValue().trim();

                if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {
                    return getShopName(imSearchBean);
                }
                if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                    return getStaffName(imSearchBean);
                } else {
                    throw new Exception("Loại đối tượng không đúng");
                }
            } else {
                throw new Exception("Bạn phải chọn loại đối tượng");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<ImSearchBean>();
        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho, mac dinh lay tat ca cac kho tu cap hien tai do xuong
     *
     */
    private List<ImSearchBean> getShopName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }

        //lay ten cua shop dua tren code
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and a.parentShopId = ?) ");
        listParameter1.add(userToken.getShopId());

        strQuery1.append("and lower(a.shopCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum < ? ");
        listParameter1.add(100L);

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
     * purpose  : lay danh sach cac nhan vien thuoc kho
     *
     */
    private List<ImSearchBean> getStaffName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and a.shopId = ? ");
        listParameter1.add(userToken.getShopId());

//        strQuery1.append("and a.channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);
//        
        //Modified by : TrongLV
        //Modified date : 2011-08-16
        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);

        strQuery1.append("and lower(a.staffCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum < ? ");
        listParameter1.add(100L);

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
    
    /**
     * @author haint41
     * @since 29/9/2011
     * @description Lay tong so ban ghi
     * @param imSearchBean
     * @return 
     */
    public Long getListObjectSize(ImSearchBean imSearchBean) {

        try {
            HttpServletRequest req = imSearchBean.getHttpServletRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                String objType = imSearchBean.getOtherParamValue().trim();

                String strQuery = "";
                Query query;
                List lstParam = new ArrayList();

                if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {
                    strQuery = ""
                            + "select count(*) "
                            + "from Shop a "
                            + "where    1 = 1 "
                            + "         and a.status = ? "
                            + "         and a.parentShopId = ? "
                            + "         and a.channelTypeId = ? ";

                    lstParam.add(Constant.STATUS_ACTIVE);
                    lstParam.add(userToken.getShopId());
                    lstParam.add(Constant.CHANNEL_TYPE_SHOP);

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += "   and lower(a.shopCode) like ? ";
                        lstParam.add(imSearchBean.getCode().trim().toLowerCase() + "%");
                    }

                } else if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                    strQuery = ""
                            + "select count(*) "
                            + "from Staff a "
                            + "where    1 = 1 "
                            + "         and a.status = ? "
                            + "         and a.shopId = ? ";

                    lstParam.add(Constant.STATUS_ACTIVE);
                    lstParam.add(userToken.getShopId());

                    //Modified by : TrongLV
                    //Modified date : 2011-08-16
                    strQuery += "and (a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ";
                    lstParam.add(Constant.OBJECT_TYPE_STAFF);
                    lstParam.add(Constant.IS_VT_UNIT);

                    //haint41 28/9/2011 : bo sung them CTV
                    strQuery += " or  a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?)) ";
                    lstParam.add(Constant.OBJECT_TYPE_STAFF);
                    lstParam.add(Constant.IS_NOT_VT_UNIT);

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += "   and lower(a.staffCode) like ? ";
                        lstParam.add(imSearchBean.getCode().trim().toLowerCase() + "%");
                    }

                } else {
                    throw new Exception("ERR.BIL.029");
                }

                query = getSession().createQuery(strQuery);
                for (int idx = 0; idx < lstParam.size(); idx++) {
                    query.setParameter(idx, lstParam.get(idx));
                }

                List<Long> tmpList1 = query.list();
                if (tmpList1 != null && tmpList1.size() == 1) {
                    return tmpList1.get(0);
                } else {
                    return null;
                }

            } else {
                throw new Exception("ERR.BIL.025");
            }

        } catch (Exception ex) {
            log.error("Loi khi lay tong so ban ghi : " + ex);
            return 0L;
        }
    }
}
