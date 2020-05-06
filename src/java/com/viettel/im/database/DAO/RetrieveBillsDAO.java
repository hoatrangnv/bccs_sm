package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.im.client.bean.ImSearchBean;
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 * @author TungTV
 * @funtion BillManagement
 * @Date 18/02/2009
 */
public class RetrieveBillsDAO extends BillBaseDAO {

    private static final Logger log = Logger.getLogger(AuthenticateDAO.class);
    private static final Long SHOP_SEARCH_TYPE = 1L;
    private static final Long STAFF__SEARCH_TYPE = 2L;
    private SearchBillForm form = new SearchBillForm();
    private BillsDepartmentForm formBill;
    private static final Long DESTROY_BILL_INTERFACE_ID = 2L;
    Pattern htmlTag = Pattern.compile("<[^>]*>");
    private Map<String, String> toInvoiceMap;
    private Map<String, String> currInvoiceNoMap;

    public Map<String, String> getToInvoiceMap() {
        return toInvoiceMap;
    }

    public void setToInvoiceMap(Map<String, String> toInvoiceMap) {
        this.toInvoiceMap = toInvoiceMap;
    }

    public Map<String, String> getCurrInvoiceNoMap() {
        return currInvoiceNoMap;
    }

    public void setCurrInvoiceNoMap(Map<String, String> currInvoiceNoMap) {
        this.currInvoiceNoMap = currInvoiceNoMap;
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

    public String preparePage() throws Exception {
        log.info("User logout action...");
        log.debug("# Begin method user logout");
        getReqSession();
        String pageForward = "retrieveBills";

        /* Get ID of user */
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userID = userToken.getUserID();
        if (userID == null) {
            return "errorPage";
        }
        String usid = userID + reqSession.getId();
        /** Get Shop ID of User */
        Long shopId = getShopIDByStaffID(userID);
        reqSession.setAttribute("shopId", shopId);
        if (shopId == null) {
            return "errorPage";
        }
        reqSession.setAttribute("isEnableRetrieved", "0");

//        List<RetrieveBillBean> invoiceListDisplay = searchInvoiceListForRetrieve(null, null, null, null, ASSIGN_ABILITY,
//                ASSIGNED_STAFF_CONFIRMED, shopId, usid, null, null);
//        session.setAttribute("invoiceList", invoiceListDisplay);

        InvoiceListManagerViewHelper viewHelper = new InvoiceListManagerViewHelper();
        viewHelper.setInterfaceType(DESTROY_BILL_INTERFACE_ID);
        reqSession.setAttribute(billManagerViewHelper, viewHelper);

        reqSession.setAttribute("invoiceList", null);

        deleteTemp(userToken);
        log.debug("# End method user logout action");
        log.info("User logout has been done!");

        return pageForward;
    }

    /**
     * @funtion prepare data when go to the interface
     * @Date 18/02/2009
     */
    public String searchBills() throws Exception {

        log.info("User logout action...");
        log.debug("# Begin method user logout");
        getReqSession();
        String pageForward = "searchBills";

        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userID = userToken.getUserID();
        if (userID == null) {
            return ERROR_PAGE;
        }
        String usid = userID + reqSession.getId();
        Long shopId = (Long) reqSession.getAttribute("shopId");
        if (shopId == null) {
            return ERROR_PAGE;
        }
        SearchBillForm searchBillForm = new SearchBillForm();
        searchBillForm = (SearchBillForm) form;

        BillsDepartmentForm billsDepartmentForm = new BillsDepartmentForm();
        billsDepartmentForm = (BillsDepartmentForm) formBill;
        Long bookType = null;
        Long status = null;
        String strStartNum = null;
        String strEndNum = null;
        Long startNum = null;
        Long endNum = null;
        String serialNo = searchBillForm.getBillSerial();

        if (serialNo != null) {
            serialNo = serialNo.trim();
        }

        if (searchBillForm.getBillSerialKey() != null && !searchBillForm.getBillSerialKey().trim().equals("")) {
            bookType = Long.parseLong(searchBillForm.getBillSerialKey());
        }
        if (serialNo != null && !serialNo.trim().equals("")) {
//            serialNo = serialNo.trim();
            Matcher m = htmlTag.matcher(serialNo);
            if (m.find()) {
                req.setAttribute("returnMsg", "retrieveBills.error.htmlTagBillSerial");
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
                req.setAttribute("returnMsg", "retrieveBills.error.invalidBillSerial");
                return pageForward;
            } else {
                BookType bBookType = (BookType) listBookType.get(0);
                bookType = bBookType.getBookTypeId();
            }
        }
        //MrSun: Begin
        if (searchBillForm.getBillStartNumber() != null && !searchBillForm.getBillStartNumber().trim().equals("")) {
            //TRIM()
            strStartNum = searchBillForm.getBillStartNumber().trim();
            try {
                startNum = Long.parseLong(strStartNum);
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "assignBills.error.invalidBillStartNum");
                return pageForward;
            }
        }
        if (searchBillForm.getBillEndNumber() != null && !searchBillForm.getBillEndNumber().trim().equals("")) {
            //TRIM()
            strEndNum = searchBillForm.getBillEndNumber().trim();
            try {
                endNum = Long.parseLong(strEndNum);
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "assignBills.error.invalidBillEndNum");
                return pageForward;
            }
        }

        if (searchBillForm.getBillSituation() != null && !searchBillForm.getBillSituation().trim().equals("")) {
            //TRIM()
            status = new Long(searchBillForm.getBillSituation().trim());
        }

        Long departmentType = null;
        Long departmentStaffId = null;
        String tempDepartmentCode = billsDepartmentForm.getCode();
        String tempDepartmentType = billsDepartmentForm.getBillDepartmentKind();

        if (tempDepartmentCode != null && !tempDepartmentCode.trim().equals("")) {
            Matcher m = htmlTag.matcher(tempDepartmentCode.trim());
            if (m.find()) {
                req.setAttribute("returnMsg", "retrieveBills.error.htmlTagDeparmentCode");
                billsDepartmentForm.setBillDepartmentName("");
                return pageForward;
            }
        }
        if (tempDepartmentType != null && !tempDepartmentType.trim().equals("")) {
            //TRIM()
            departmentType = new Long(tempDepartmentType.trim());
            if (tempDepartmentCode != null && !tempDepartmentCode.trim().equals("")) {

                tempDepartmentCode = tempDepartmentCode.trim().toLowerCase();

                List parameterList = new ArrayList();
                String queryString = "from " + ((tempDepartmentType.equals(Constant.SHOP_TYPE)) ? " Shop " : " Staff ");
                queryString += " where status = ? ";
                parameterList.add(Constant.STATUS_USE);

                if (tempDepartmentType.equals(Constant.SHOP_TYPE)) {
                    queryString += " and parentShopId = ? ";
                    parameterList.add(userToken.getShopId());
                    queryString += " and lower(shopCode) = ? ";

                } else {
                    queryString += " and shopId = ? ";
                    parameterList.add(userToken.getShopId());
                    queryString += " and lower(staffCode) = ? ";
                }
                parameterList.add(tempDepartmentCode);

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                List listShop = new ArrayList();
                listShop = queryObject.list();
                if (listShop.size() == 0) {
                    reqSession.setAttribute("isEnableRetrieved", "0");
                    req.setAttribute("returnMsg", "retrieveBills.error.invalidDepartmentCode");
                    return pageForward;
                } else {
                    reqSession.setAttribute("isEnableRetrieved", "1");
                    if (tempDepartmentType.equals(Constant.SHOP_TYPE)) {
                        Shop shop = (Shop) listShop.get(0);
                        departmentStaffId = shop.getShopId();
                    } else {
                        Staff staff = (Staff) listShop.get(0);
                        departmentStaffId = staff.getStaffId();
                    }
                }
            } else {
                reqSession.setAttribute("isEnableRetrieved", "0");
            }
        } else {
            reqSession.setAttribute("isEnableRetrieved", "0");
            if (tempDepartmentCode != null && !tempDepartmentCode.trim().equals("")) {
                req.setAttribute("returnMsg", "retrieveBills.error.invalidDepartmentType");
                return pageForward;
            }
        }
        //MrSun: End

        List<RetrieveBillBean> invoiceListDisplay = new ArrayList<RetrieveBillBean>();

        if (status != null) {
            invoiceListDisplay =
                    searchInvoiceListForRetrieve(serialNo, bookType, departmentType, departmentStaffId,
                    status, null, shopId, usid, startNum, endNum);
        } else {
            invoiceListDisplay =
                    searchInvoiceListForRetrieve(serialNo, bookType, departmentType, departmentStaffId,
                    Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP, Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF, shopId, usid, startNum, endNum);
        }

        InvoiceListManagerViewHelper viewHelper = getViewHelper();
        saveViewHelper(form, viewHelper, bookType);

        reqSession.setAttribute("invoiceList", invoiceListDisplay);
        if (invoiceListDisplay.size() != 0) {
            req.setAttribute("returnMsg", "retrieveBills.success.search");
            List listParamValue = new ArrayList();
            listParamValue.add(invoiceListDisplay.size());
            req.setAttribute("returnMsgValue", listParamValue);
        } else {
            req.setAttribute("returnMsg", "retrieveBills.unsuccess.search");
        }
        log.debug("# End method user logout action");
        log.info("User logout has been done!");

        return pageForward;

    }

    public String pageNavigator() throws Exception {
        log.info("Begin.");
        String forward = "pageNavigator";
        return forward;
    }

    /**
     * @funtion when user click to split bill
     * @Date 18/02/2009
     */
    public String retrieveBillComplete() throws Exception {
        log.info("User logout action...");
        log.debug("# Begin method user logout");
        String pageForward = "retrieveBillComplete";
        getReqSession();

        Long shopId = (Long) reqSession.getAttribute("shopId");
        if (shopId == null) {
            return ERROR_PAGE;
        }
        /* Get ID of user */
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);

        SearchBillForm searchBillForm = (SearchBillForm) form;

        String[] receivedBillsId = searchBillForm.getReceivedBill();

        if (receivedBillsId == null || receivedBillsId.length == 0) {
            req.setAttribute("returnMsg", "retrieveBills.error.invalidReceivedBills");
            return pageForward;
        }

        Map<String, String> toInvoiceMap = getToInvoiceMap();
        Map<String, String> currInvoiceNoMap = getCurrInvoiceNoMap();

        /** Retrieve for Shop */
        int counter = 0;
        for (int i = 0; i < receivedBillsId.length; i++) {
            Long invoiceListId;
            Long toInvoice;
            Long currInvoice;
            try {
                invoiceListId = new Long(receivedBillsId[i].trim());
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
                currInvoice = Long.parseLong(currInvoiceNoMap.get(String.valueOf(invoiceListId)).trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "splitBill.error.invalidBillNum");
                pageForward = "assignBills";
                return pageForward;
            }


            if (toInvoice < currInvoice) {
                req.setAttribute("returnMsg", "splitBill.error.invalidBillNum");
                pageForward = "assignBills";
                return pageForward;
            }


            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append("from InvoiceList where 1=1 ");
            sBuffer.append("    and invoiceListId = ? ");
            sBuffer.append("    and currInvoiceNo >= fromInvoice ");
            sBuffer.append("    and currInvoiceNo <= toInvoice ");

            sBuffer.append("    and currInvoiceNo <= ? ");//Tranh truong hop currInvoiceNo da duoc ++
            sBuffer.append("    and toInvoice = ? ");//Chi thu hoi tu cuoi dai

            Query query = getSession().createQuery(sBuffer.toString());
            query.setParameter(0, invoiceListId);
            query.setParameter(1, currInvoice);
            query.setParameter(2, toInvoice);

            List<InvoiceList> list = query.list();
            if (list == null || list.size() == 0) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute("returnMsg", "Lỗi. Dải hoá đơn không thoả mãn điều kiện thu hồi!");
                return pageForward;
            }

            InvoiceList invoiceList = list.get(0);

            //Thuc hien thu hoi
            retriveBill(invoiceList, currInvoice, toInvoice, shopId, userToken);
            counter++;
        }
        if (counter > 0) {
            deleteTemp(userToken);
            this.getSession().flush();
            searchBills();
            req.setAttribute("returnMsg", "retrieveBills.success.retrieveBills");
        } else {
            req.setAttribute("returnMsg", "Lỗi. Không có dải hoá đơn nào được thu hồi!");
        }
        log.debug("# End method user logout action");
        log.info("User logout has been done!");
        return pageForward;
    }

    private void retriveBill(InvoiceList invoiceList, Long fromInvoice, Long toInvoice, Long shopId,
            UserToken userToken) throws Exception {

        InvoiceListTempDAO invoiceListTempDAO = new InvoiceListTempDAO();
        invoiceListTempDAO.setSession(getSession());

        Long tempChildShopId = invoiceList.getShopId();
        Long tempStaffId = invoiceList.getStaffId();

        Long fromInvoiceTemp = invoiceList.getFromInvoice();
        Long toInvoiceTemp = invoiceList.getToInvoice();
        Long currInvoiceNo = invoiceList.getCurrInvoiceNo();

        InvoiceList invoiceListLog = null;
        //?
        if (fromInvoiceTemp.compareTo(currInvoiceNo) == 0) {
            //?
            //Thu hoi ca dai -> cap nhat shopId
            if (currInvoiceNo.compareTo(fromInvoice) == 0) {
                invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
                invoiceList.setShopId(shopId);
                invoiceList.setStaffId(null);
                //CHECK HOA DON DA SU DUNG HET TRUOC KHI SAVE
                if (invoiceList.getCurrInvoiceNo().intValue() == 0) {
                    invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
                }

                save(invoiceList);
                invoiceListLog = invoiceList;
            } //?
            else {
                //Tao moi dai hoa don thu hoi trong kho quan ly
                InvoiceList tem = new InvoiceList();
                tem.setSerialNo(invoiceList.getSerialNo());
                tem.setBookTypeId(invoiceList.getBookTypeId());
                tem.setBlockNo(invoiceList.getBlockNo());
                tem.setInvoiceListId(getSequence("INVOICE_LIST_SEQ"));
                tem.setFromInvoice(fromInvoice);
                tem.setCurrInvoiceNo(fromInvoice);
                tem.setToInvoice(toInvoice);
                tem.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
                tem.setShopId(shopId);
                tem.setUserCreated(userToken.getFullName());
                tem.setDateCreated(new Date());
                tem.setDateAssign(new Date());
                //CHECK HOA DON DA SU DUNG HET TRUOC KHI SAVE
                if (tem.getCurrInvoiceNo().intValue() == 0) {
                    tem.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
                }

                save(tem);

                invoiceListLog = tem;

                //Cap nhat dai hoa don con lai trong kho don vi -> da su dung het
                invoiceList.setToInvoice(fromInvoice - 1);
                invoiceList.setUserCreated(userToken.getFullName());
                //CHECK HOA DON DA SU DUNG HET TRUOC KHI SAVE
                if (invoiceList.getCurrInvoiceNo().intValue() == 0) {
                    invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
                }

                save(invoiceList);
            }
            if (tempStaffId != null) {
                saveInvoiceTransferLog(invoiceListLog, userToken, Constant.INVOICE_LOG_STATUS_RETRIEVED, shopId, null, tempStaffId);
            } else {
                saveInvoiceTransferLog(invoiceListLog, userToken, Constant.INVOICE_LOG_STATUS_RETRIEVED, shopId, tempChildShopId, null);
            }
        } else {
            if (currInvoiceNo.longValue() == fromInvoice.longValue()) {
                //Tao moi dai hoa don thu hoi trong kho quan ly
                InvoiceList tem = new InvoiceList();
                tem.setInvoiceListId(getSequence("INVOICE_LIST_SEQ"));
                tem.setSerialNo(invoiceList.getSerialNo());
                tem.setBookTypeId(invoiceList.getBookTypeId());
                tem.setBlockNo(invoiceList.getBlockNo());
                tem.setFromInvoice(currInvoiceNo);
                tem.setCurrInvoiceNo(currInvoiceNo);
                tem.setToInvoice(toInvoiceTemp);
                tem.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
                tem.setShopId(shopId);
                tem.setUserCreated(userToken.getFullName());
                tem.setDateCreated(new Date());
                tem.setDateAssign(new Date());
                //CHECK HOA DON DA SU DUNG HET TRUOC KHI SAVE
                if (tem.getCurrInvoiceNo().intValue() == 0) {
                    tem.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
                }

                save(tem);

                invoiceListLog = tem;

                //Cap nhat dai hoa don con lai trong kho don vi -> da su dung het
                invoiceList.setToInvoice(currInvoiceNo - 1);
                invoiceList.setCurrInvoiceNo(0L);
                invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
                save(invoiceList);

            } else {
                //Tao moi dai hoa don thu hoi trong kho quan ly
                InvoiceList tem = new InvoiceList();
                tem.setInvoiceListId(getSequence("INVOICE_LIST_SEQ"));
                tem.setSerialNo(invoiceList.getSerialNo());
                tem.setBookTypeId(invoiceList.getBookTypeId());
                tem.setBlockNo(invoiceList.getBlockNo());
                tem.setFromInvoice(fromInvoice);
                tem.setCurrInvoiceNo(fromInvoice);
                tem.setToInvoice(toInvoice);
                tem.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
                tem.setShopId(shopId);
                tem.setUserCreated(userToken.getFullName());
                tem.setDateCreated(new Date());
                tem.setDateAssign(new Date());
                //CHECK HOA DON DA SU DUNG HET TRUOC KHI SAVE
                if (tem.getCurrInvoiceNo().intValue() == 0) {
                    tem.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
                }

                save(tem);

                invoiceListLog = tem;

                //Cap nhat dai hoa don con lai trong kho don vi -> dang su dung
                invoiceList.setToInvoice(fromInvoice - 1);
                //CHECK HOA DON DA SU DUNG HET TRUOC KHI SAVE
                if (invoiceList.getCurrInvoiceNo().intValue() == 0) {
                    invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
                }

                save(invoiceList);
            }
            if (tempStaffId != null) {
                saveInvoiceTransferLog(invoiceListLog, userToken, Constant.INVOICE_LOG_STATUS_RETRIEVED, shopId, null, tempStaffId);
            } else {
                saveInvoiceTransferLog(invoiceListLog, userToken, Constant.INVOICE_LOG_STATUS_RETRIEVED, shopId, tempChildShopId, null);
            }
        }
    }

    private void saveViewHelper(SearchBillForm formBill,
            InvoiceListManagerViewHelper viewHelper, Long bookType) {
        viewHelper.setSerialNo(formBill.getBillSerial());
        if (bookType != null) {
            viewHelper.setBookType(bookType);
        }
        if (formBill.getBillDepartmentKind() != null && !formBill.getBillDepartmentKind().equals("")) {
            viewHelper.setDepartmentType(new Long(formBill.getBillDepartmentKind()));
        }
        if (formBill.getBillDepartmentNameKey() != null && !formBill.getBillDepartmentNameKey().equals("")) {
            viewHelper.setDepartmentId(new Long(formBill.getBillDepartmentNameKey()));
        }
    }

    /**
     * @return List of Invoice List for display in interface
     */
    protected List<RetrieveBillBean> searchInvoiceListForRetrieve(String serialNo, Long bookTypeId,
            Long billDepartmentType, Long billDepartmentId,
            Long status1, Long status2, Long shopId, String usid,
            Long startNum, Long endNum) {
        log.debug("finding all InvoiceList instances");
        try {
            String queryString = "";
            List parameterList = new ArrayList();
            if ((billDepartmentType != null && billDepartmentType.equals(SHOP_SEARCH_TYPE)) || billDepartmentType == null) {
                queryString = "select "
                        + "a.invoice_list_id as invoiceListId, "
                        + "a.serial_no as serialNo, "
                        + "b.block_name as blockName, "
                        + "a.block_no as blockNo, "
                        + "a.from_invoice as fromInvoice, "
                        + "a.to_invoice as toInvoice, "
                        + "a.curr_invoice_no as currInvoiceNo, "
                        + "a.user_created as userCreated, "
                        + "a.status as status, "
                        + "a.date_created as dateCreated, "
                        + "d.from_invoice as splitFromInvoice, "
                        + "d.to_invoice as splitToInvoice, "
                        + "c.name as billOwnerName "
                        + "from invoice_list a "
                        + "join book_type b "
                        + "on a.book_type_id = b.book_type_id "
                        + "join shop c "
                        + "on c.shop_id = a.shop_id "
                        + "left join invoice_list_temp d "
                        + "on "
                        + "a.invoice_list_id = d.invoice_list_id "
                        + "and d.usid = ? "
                        + "where c.parent_shop_id = ? "
                        + "and a.curr_invoice_no != 0 ";
                parameterList.add(usid);
                parameterList.add(shopId);
                if (status1 != null) {
                    /* Status of Bill */
                    queryString += "and (a.status = ? or a.status = ? ) ";
                    parameterList.add(status1);
                    //Cho phep thu hoi hoa don chua xac nhan
                    parameterList.add(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED);
                }
                if (serialNo != null && !serialNo.equals("")) {
                    queryString += "and a.serial_no like ? ";
                    parameterList.add(serialNo);
                }
                if (bookTypeId != null) {
                    queryString += "and a.book_type_id = ? ";
                    parameterList.add(bookTypeId);
                }
                //MrSun ???
                if (startNum != null) {
                    queryString += " AND ( a.from_Invoice >= ? OR ( a.from_Invoice <= ? AND ? <= a.to_Invoice ))";
                    parameterList.add(startNum);
                    parameterList.add(startNum);
                    parameterList.add(startNum);
                }
                if (endNum != null) {
                    queryString += " AND ( a.to_Invoice <= ? OR ( a.from_Invoice <= ? AND ? <= a.to_Invoice ))";
                    parameterList.add(endNum);
                    parameterList.add(endNum);
                    parameterList.add(endNum);
                }
                //MrSun ???
                if (billDepartmentType != null && billDepartmentId != null) {
                    if (billDepartmentType.equals(SHOP_SEARCH_TYPE)) {
                        queryString += "and a.shop_id = ?  and a.staff_id is null ";
                        parameterList.add(billDepartmentId);
                    }
                }
            }
            if (billDepartmentType == null) {
                queryString += " union all ";
            }
            if ((billDepartmentType != null && billDepartmentType.equals(STAFF__SEARCH_TYPE)) || billDepartmentType == null) {
                queryString += "select "
                        + "a.invoice_list_id as invoiceListId, "
                        + "a.serial_no as serialNo, "
                        + "b.block_name as blockName, "
                        + "a.block_no as blockNo, "
                        + "a.from_invoice as fromInvoice, "
                        + "a.to_invoice as toInvoice, "
                        + "a.curr_invoice_no as currInvoiceNo, "
                        + "a.user_created as userCreated, "
                        + "a.status as status, "
                        + "a.date_created as dateCreated, "
                        + "d.from_invoice as splitFromInvoice, "
                        + "d.to_invoice as splitToInvoice, "
                        + "c.name as billOwnerName "
                        + "from invoice_list a "
                        + "join book_type b "
                        + "on a.book_type_id = b.book_type_id "
                        + "join staff c "
                        + "on c.staff_id = a.staff_id "
                        + "left join invoice_list_temp d "
                        + "on "
                        + "a.invoice_list_id = d.invoice_list_id "
                        + "and d.usid = ? "
                        + "where c.shop_id = ? "
                        + "and a.curr_invoice_no != 0 ";
                parameterList.add(usid);
                parameterList.add(shopId);
                if (status1 != null && status2 != null) {
                    queryString += "and (a.status = ? or a.status = ? or a.status = ?) ";
                    parameterList.add(status1);
                    parameterList.add(status2);
                    //Cho phep thu hoi hoa don chua xac nhan
                    parameterList.add(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED);
                } else if (status1 != null) {
                    /* Status of Bill */
                    queryString += "and a.status = ? ";
                    parameterList.add(status1);
                }
                if (serialNo != null && !serialNo.equals("")) {
                    queryString += "and a.serial_no like ? ";
                    parameterList.add(serialNo);
                }
                if (bookTypeId != null) {
                    queryString += "and a.book_type_id = ? ";
                    parameterList.add(bookTypeId);
                }
                if (startNum != null) {
                    queryString += " AND ( a.from_Invoice >= ? OR ( a.from_Invoice <= ? AND ? <= a.to_Invoice ))";
                    parameterList.add(startNum);
                    parameterList.add(startNum);
                    parameterList.add(startNum);
                }
                if (endNum != null) {
                    queryString += " AND ( a.to_Invoice <= ? OR ( a.from_Invoice <= ? AND ? <= a.to_Invoice ))";
                    parameterList.add(endNum);
                    parameterList.add(endNum);
                    parameterList.add(endNum);
                }
                if (billDepartmentType != null && billDepartmentId != null) {
                    if (billDepartmentType.equals(STAFF__SEARCH_TYPE)) {
                        queryString += "and a.staff_id = ? ";
                        parameterList.add(billDepartmentId);
                    }
                }
            }
            queryString += " order by serialNo, fromInvoice";
            Session session = getSession();
            Query queryObject = session.createSQLQuery(queryString).addScalar("invoiceListId", Hibernate.LONG).addScalar("serialNo", Hibernate.STRING).addScalar("blockName", Hibernate.STRING).addScalar("blockNo", Hibernate.STRING).addScalar("fromInvoice", Hibernate.LONG).addScalar("toInvoice", Hibernate.LONG).addScalar("currInvoiceNo", Hibernate.LONG).addScalar("userCreated", Hibernate.STRING).addScalar("userCreated", Hibernate.STRING).addScalar("status", Hibernate.LONG).addScalar("dateCreated", Hibernate.DATE).addScalar("billOwnerName", Hibernate.STRING).addScalar("splitFromInvoice", Hibernate.LONG).addScalar("splitToInvoice", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(RetrieveBillBean.class));
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
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
                    strQuery += "where 1=1 and a.status=? and a.parentShopId=? ";
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
                    strQuery += "and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ";
                    lstParam.add(Constant.OBJECT_TYPE_STAFF);
                    lstParam.add(Constant.IS_VT_UNIT);

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += "and lower(a.staffCode) like ? ";
                        lstParam.add(imSearchBean.getCode().trim().toLowerCase() + "%");
                    }

                    query = getSession().createQuery(strQuery);
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
            getReqSession();
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
}
