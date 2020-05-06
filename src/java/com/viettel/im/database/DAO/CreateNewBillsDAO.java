package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.InvoiceListBean;
import com.viettel.im.client.bean.InvoiceListManagerViewHelper;
import com.viettel.im.client.form.CreateNewBillsForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.BookType;
import com.viettel.im.database.BO.InvoiceList;
import com.viettel.im.database.BO.InvoiceTransferLog;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * author   :
 * date     :
 * desc     : cac nghiep vu lien quan den tao dai hoa don
 * modified : tamdt1, 21/10/2010
 * 
 */

public class CreateNewBillsDAO extends BaseDAOAction {
    private static final Logger log = Logger.getLogger(CreateNewBillsDAO.class);

    //cac hang so forward
    private String pageForward;
    private final String PREPARE_PAGE = "preparePage";
    private final String CREATE_NEW_BILLS = "createNewBills";
    private final String SEARCH_BILLS = "searchBills";
    private final String PAGE_NAVIGATOR = "pageNavigator";


    //cac bien request, session
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String SESSION_INVOICE_LIST = "invoiceList";

    //cac bien form
    private CreateNewBillsForm createNewBillsForm = new CreateNewBillsForm();

    public CreateNewBillsForm getCreateNewBillsForm() {
        return createNewBillsForm;
    }

    public void setCreateNewBillsForm(CreateNewBillsForm createNewBillsForm) {
        this.createNewBillsForm = createNewBillsForm;
    }

    /**
     *
     * author   :
     * date     :
     * desc     : chuan bị form nhap dai hoa don moi
     * modified : tamdt1, 21/10/2010
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of CreateNewBillsDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //reset form
            this.createNewBillsForm.resetForm();

            //lay danh sach chinh sach CK
            searchBills();

            //
            req.setAttribute(REQUEST_MESSAGE, "");

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method preparePage of CreateNewBillsDAO");
        pageForward = PREPARE_PAGE;
        return pageForward;
    }

    /**
     *
     * author   :
     * date     :
     * desc     : tao dai hoa don moi
     * modified : tamdt1, 21/10/2010
     *
     */
    public String createNewBill() throws Exception {
        log.info("Begin method createNewBill of CreateNewBillsDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            if (!checkValidInvoiceListToCreate()) {
                //
                log.info("End method createNewBill of CreateNewBillsDAO");
                pageForward = CREATE_NEW_BILLS;
                return pageForward;
            }

            
            BookTypeDAO bookTypeDAO = new BookTypeDAO();
            bookTypeDAO.setSession(this.getSession());
            BookType bookType = bookTypeDAO.getBookTypeBySerialCode(this.createNewBillsForm.getSerialCode());
            if (bookType == null) {
                //
                req.setAttribute(REQUEST_MESSAGE, "createNewBills.error.invalidBillSerial");
                
                //
                log.info("End method createNewBill of CreateNewBillsDAO");
                pageForward = CREATE_NEW_BILLS;
                return pageForward;
                
            }
            
            Long startNumberBill = Long.valueOf(this.createNewBillsForm.getBillStartNumber());
            Long endNumberBill = Long.valueOf(this.createNewBillsForm.getBillEndNumber());
            
            //so luong dai invoiceList duoc tao
            Long numInvoice = bookType.getNumInvoice();
            Long numberBill = endNumberBill - startNumberBill + 1;

            Long numberOfInvoiceList = 0L;
            if ((numberBill % numInvoice) != 0) {
                numberOfInvoiceList = (numberBill / numInvoice) + 1;
            } else {
                numberOfInvoiceList = numberBill / numInvoice;
            }

            //insert du lieu vao bang invoiceList va ghi log
            Long startNumberBillTemp = startNumberBill;
            Long curreBlockNo;
            for (int i = 0; i < numberOfInvoiceList; i++) {
                //insert du lieu vao bang invoiceList
                InvoiceList invoiceList = new InvoiceList();
                Long invoiceListId = getSequence("INVOICE_LIST_SEQ");
                invoiceList.setInvoiceListId(invoiceListId);
                invoiceList.setBookTypeId(bookType.getBookTypeId());
                invoiceList.setSerialNo(bookType.getSerialCode());
                invoiceList.setFromInvoice(startNumberBillTemp);

                if (endNumberBill > startNumberBillTemp + numInvoice - 1) {
                    invoiceList.setToInvoice(startNumberBillTemp + numInvoice - 1);
                } else {
                    invoiceList.setToInvoice(endNumberBill);
                }

                //lay so thung/ quyen
                curreBlockNo = 0L;
                curreBlockNo = startNumberBillTemp / numInvoice;

                curreBlockNo++;
                invoiceList.setBlockNo(curreBlockNo.toString());

                invoiceList.setShopId(userToken.getShopId());
                invoiceList.setUserCreated(userToken.getFullName()); //set user created

                Date now = DateTimeUtils.getSysDate();

                invoiceList.setDateCreated(now);
                invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
                invoiceList.setCurrInvoiceNo(startNumberBillTemp);
                invoiceList.setUserAssign(userToken.getFullName());
                invoiceList.setDateAssign(now);

                //
                session.save(invoiceList);

                //ghi log
                InvoiceTransferLog invoiceTransferLog = new InvoiceTransferLog();
                invoiceTransferLog.setId(getSequence("INVOICE_TRANSFER_LOG_SEQ"));
                invoiceTransferLog.setSerialNo(bookType.getSerialCode());
                invoiceTransferLog.setInvoiceListId(invoiceList.getInvoiceListId());
                invoiceTransferLog.setFromInvoice(startNumberBillTemp);
                if (endNumberBill > startNumberBillTemp + numInvoice - 1) {
                    invoiceTransferLog.setToInvoice(startNumberBillTemp + numInvoice - 1);
                } else {
                    invoiceTransferLog.setToInvoice(endNumberBill);
                }
                invoiceTransferLog.setParentShopId(userToken.getShopId());
                invoiceTransferLog.setUserCreated(userToken.getLoginName());
                invoiceTransferLog.setDateCreated(new Date());
                invoiceTransferLog.setBlockNo(curreBlockNo);
                invoiceTransferLog.setTransferType(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);

                session.save(invoiceTransferLog);

                startNumberBillTemp = startNumberBillTemp + numInvoice;
            }

            //commit du lieu
            session.flush();

            //reset form
            this.createNewBillsForm.resetForm();

            //tim kiem lai list
            searchBills();

            //
            req.setAttribute(REQUEST_MESSAGE, "createNewBills.success.createNewBillSuccess");

        } catch (Exception ex) {
            //
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method createNewBill of CreateNewBillsDAO");
        pageForward = CREATE_NEW_BILLS;
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 21/10/2010
     * purpose  : kiem tra tinh hop le cua dai so hoa don truoc khi nhap hoa don moi
     *
     */
    private boolean checkValidInvoiceListToCreate() {
        HttpServletRequest req = getRequest();

        String serialCode = this.createNewBillsForm.getSerialCode();
        String blockName = this.createNewBillsForm.getBlockName();
        String strStartNumber = this.createNewBillsForm.getBillStartNumber();
        String strEndNumber = this.createNewBillsForm.getBillEndNumber();

        //kiem tra cac truong ko duoc de trong
        if(serialCode == null || serialCode.trim().equals("") ||
                strStartNumber == null || strStartNumber.trim().equals("") ||
                strEndNumber == null || strEndNumber.trim().equals("")) {
            //
            req.setAttribute(REQUEST_MESSAGE, "createNewBills.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra su ton tai cua BookType
        BookTypeDAO bookTypeDAO = new BookTypeDAO();
        bookTypeDAO.setSession(this.getSession());
        BookType bookType = bookTypeDAO.getBookTypeBySerialCode(serialCode);
        if(bookType == null) {
            //
            req.setAttribute(REQUEST_MESSAGE, "createNewBills.error.invalidBillSerial");
            return false;
        } else {
            this.createNewBillsForm.setBookTypeId(bookType.getBookTypeId());
            this.createNewBillsForm.setSerialCode(bookType.getSerialCode());
            this.createNewBillsForm.setBlockName(bookType.getBlockName());
        }

        //trim cac truong can thiet
        serialCode = this.createNewBillsForm.getSerialCode();
        strStartNumber = strStartNumber.trim();
        strEndNumber = strEndNumber.trim();
        this.createNewBillsForm.setBillStartNumber(strStartNumber);
        this.createNewBillsForm.setBillEndNumber(strEndNumber);

        Long startNumber;
        Long endNumber;

        try {
            startNumber = Long.valueOf(strStartNumber);
            endNumber = Long.valueOf(strEndNumber);
        } catch (Exception ex) {
            //
            req.setAttribute(REQUEST_MESSAGE, "createNewBills.error.numberNegative");
            return false;
        }

        //kiem tra so duong cua so dau va so ket thuc
        if (startNumber.compareTo(0L) <= 0 || endNumber.compareTo(0L) <= 0) {
            //
            req.setAttribute(REQUEST_MESSAGE, "createNewBills.error.numberNegative");
            return false;
        }

        //kiem tra so cuoi dai > so dau dai
        if (startNumber.compareTo(endNumber) > 0) {
            //
            req.setAttribute(REQUEST_MESSAGE, "createNewBills.error.invalidNumber");
            return false;
        }

        //kiem tra su ton tai cua dai hoa don
        try {
            StringBuilder strQuery = new StringBuilder("");
            strQuery.append("select     count(*) ");
            strQuery.append("from       InvoiceList ");
            strQuery.append("where      bookTypeId = ? ");
            strQuery.append("           and (       (? >= fromInvoice and ? <= toInvoice) ");
            strQuery.append("                   or  (? >= fromInvoice and ? <= toInvoice) ");
            strQuery.append("                   or  (? <= fromInvoice and ? >= toInvoice) ");
            strQuery.append("               ) ");
            Session session = getSession();
            Query query = session.createQuery(strQuery.toString());
            query.setParameter(0, this.createNewBillsForm.getBookTypeId());
            query.setParameter(1, startNumber);
            query.setParameter(2, startNumber);
            query.setParameter(3, endNumber);
            query.setParameter(4, endNumber);
            query.setParameter(5, startNumber);
            query.setParameter(6, endNumber);

            Long count = 0L;
            List list = query.list();
            if (list != null && list.size() > 0) {
                count = (Long) list.get(0);
            }

            if (count.compareTo(0L) > 0) {
                //
                req.setAttribute(REQUEST_MESSAGE, "createNewBills.error.duplicateSerialNum");
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return false;
        }
        
        return true;
    }

    /**
     * 
     * author   :
     * date     :
     * desc     : tim kiem dai hoa don da tao
     * modified : tamdt1, 21/10/2010
     * 
     */
    public String searchBills() throws Exception {
        log.info("Begin method searchBills of CreateNewBillsDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            //kiem tra cac dieu kien rang buoc
            String serialCode = this.createNewBillsForm.getSerialCode();
            String strStartNumber = this.createNewBillsForm.getBillStartNumber();
            String strEndNumber = this.createNewBillsForm.getBillEndNumber();

            if (serialCode != null && !serialCode.trim().equals("")) {
                //kiem tra su ton tai cua BookType
                BookTypeDAO bookTypeDAO = new BookTypeDAO();
                bookTypeDAO.setSession(this.getSession());
                BookType bookType = bookTypeDAO.getBookTypeBySerialCode(serialCode);
                if (bookType == null) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "createNewBills.error.invalidBillSerial");

                    //
                    log.info("End method searchBills of CreateNewBillsDAO");
                    pageForward = SEARCH_BILLS;
                    return pageForward;

                } else {
                    this.createNewBillsForm.setBookTypeId(bookType.getBookTypeId());
                    this.createNewBillsForm.setSerialCode(bookType.getSerialCode());
                    this.createNewBillsForm.setBlockName(bookType.getBlockName());
                }

                //trim cac truong can thiet
                serialCode = this.createNewBillsForm.getSerialCode();
            }

            Long startNumber = null;
            Long endNumber = null;

            if (strStartNumber != null && !strStartNumber.trim().equals("")) {
                strStartNumber = strStartNumber.trim();
                this.createNewBillsForm.setBillStartNumber(strStartNumber);

                try {
                    startNumber = Long.valueOf(strStartNumber);
                } catch (Exception ex) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "createNewBills.error.numberNegative");

                    //
                    log.info("End method searchBills of CreateNewBillsDAO");
                    pageForward = SEARCH_BILLS;
                    return pageForward;
                }
            }

            if (strEndNumber != null && !strEndNumber.trim().equals("")) {
                strEndNumber = strEndNumber.trim();
                this.createNewBillsForm.setBillEndNumber(strEndNumber);

                try {
                    endNumber = Long.valueOf(strEndNumber);
                } catch (Exception ex) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "createNewBills.error.numberNegative");

                    //
                    log.info("End method searchBills of CreateNewBillsDAO");
                    pageForward = SEARCH_BILLS;
                    return pageForward;
                }
            }

            StringBuilder strQuery = new StringBuilder("");
            List parameterList = new ArrayList();

            strQuery.append("select new com.viettel.im.client.bean.InvoiceListBean( ");
            strQuery.append("       a.invoiceListId, ");
            strQuery.append("       a.serialNo, ");
            strQuery.append("       b.blockName, ");
            strQuery.append("       a.blockNo, ");
            strQuery.append("       a.fromInvoice, ");
            strQuery.append("       a.toInvoice, ");
            strQuery.append("       a.currInvoiceNo, ");
            strQuery.append("       a.userCreated, ");
            strQuery.append("       a.status, ");
            strQuery.append("       a.dateCreated, ");
            strQuery.append("       a.userAssign, ");
            strQuery.append("       a.dateAssign ");
            strQuery.append("       ) ");
            strQuery.append("from   InvoiceList a, BookType b ");
            strQuery.append("where  1 = 1 ");

            strQuery.append("       and a.bookTypeId = b.bookTypeId ");

            strQuery.append("       and a.shopId = ? ");
            parameterList.add(userToken.getShopId());

            strQuery.append("       and a.status = ? ");
            parameterList.add(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);

            strQuery.append("       and a.currInvoiceNo <> 0 ");

            Long bookTypeId = this.createNewBillsForm.getBookTypeId();
            if (bookTypeId != null && bookTypeId.compareTo(0L) > 0) {
                strQuery.append("   and a.bookTypeId = ? ");
                parameterList.add(bookTypeId);
            }

            if (startNumber != null && startNumber.compareTo(0L) > 0) {
                strQuery.append("   and (a.fromInvoice >= ? or (a.fromInvoice <= ? and ? <= a.toInvoice)) ");
                parameterList.add(startNumber);
                parameterList.add(startNumber);
                parameterList.add(startNumber);
            }
            if (endNumber != null) {
                strQuery.append("   and (a.toInvoice <= ? or (a.fromInvoice <= ? and ? <= a.toInvoice)) ");
                parameterList.add(endNumber);
                parameterList.add(endNumber);
                parameterList.add(endNumber);
            }

            strQuery.append("order by a.serialNo, a.fromInvoice ");

            Query query = getSession().createQuery(strQuery.toString());
            for (int i = 0; i < parameterList.size(); i++) {
                query.setParameter(i, parameterList.get(i));
            }

            List<InvoiceListBean> listInvoiceListBean = query.list();
            req.getSession().setAttribute(SESSION_INVOICE_LIST, listInvoiceListBean);

            if (listInvoiceListBean != null) {
                req.setAttribute(REQUEST_MESSAGE, "createNewBills.success.search");
                List listParamValue = new ArrayList();
                listParamValue.add(listInvoiceListBean.size());
                req.setAttribute(REQUEST_MESSAGE_PARAM, listParamValue);

            } else {
                req.setAttribute(REQUEST_MESSAGE, "createNewBills.unsuccess.search");
            }


        } catch (Exception ex) {
            //
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method searchBills of CreateNewBillsDAO");
        pageForward = SEARCH_BILLS;
        return pageForward;
    }

    /**
     *
     * author   :
     * date     :
     * desc     : ham phuc vu muc dich phan trang
     *
     */
    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of CreateNewBillsDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method pageNavigator of CreateNewBillsDAO");
        pageForward = PAGE_NAVIGATOR;
        return pageForward;

    }

    /**
     *
     * author   :
     * date     :
     * desc     : xoa dai hoa don moi tao
     *
     */
    public String deleteBills() throws Exception {
        log.info("Begin method deleteBills of CreateNewBillsDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            String[] deleteBillsId = this.createNewBillsForm.getDeleteBill();
            if (deleteBillsId == null || deleteBillsId.length == 0) {
                //loi chua chon dai hoa don de xoa
                req.setAttribute(REQUEST_MESSAGE, "createNewBills.error.checkBoxUnchecked");

                //
                log.info("End method deleteBills of CreateNewBillsDAO");
                pageForward = SEARCH_BILLS;
                return pageForward;
            }

            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
            invoiceListDAO.setSession(session);
            int counter = 0;
            for (int i = 0; i < deleteBillsId.length; i++) {
                String tempId = deleteBillsId[i];
                if (tempId != null && !tempId.equals("")) {
                    InvoiceList invoiceList = invoiceListDAO.findById(new Long(tempId));
                    if (invoiceList == null) {
                        continue;
                    }

                    //cap nhat db
                    session.delete(invoiceList);

                    //ghi log
                    InvoiceTransferLog invoiceTransferLog = new InvoiceTransferLog();
                    invoiceTransferLog.setId(getSequence("INVOICE_TRANSFER_LOG_SEQ"));
                    invoiceTransferLog.setSerialNo(invoiceList.getSerialNo());
                    invoiceTransferLog.setFromInvoice(invoiceList.getFromInvoice());
                    invoiceTransferLog.setToInvoice(invoiceList.getToInvoice());
                    invoiceTransferLog.setParentShopId(invoiceList.getShopId());
                    invoiceTransferLog.setUserCreated(userToken.getLoginName());
                    invoiceTransferLog.setDateCreated(DateTimeUtils.getSysDate());
                    invoiceTransferLog.setTransferType(Constant.INVOICE_LOG_STATUS_DESTROYED_BY_VT);
                    if (invoiceList.getBlockNo() != null) {
                        if (!"".equals(invoiceList.getBlockNo().trim())) {
                            invoiceTransferLog.setBlockNo(Long.parseLong(invoiceList.getBlockNo()));
                        }
                    }

                    session.save(invoiceTransferLog);
                    counter++;
                }
            }
            if (counter > 0) {
                //commit
                session.flush();

                //lay lai danh sach hoa don
                searchBills();

                //hien thi message
                req.setAttribute(REQUEST_MESSAGE, "createNewBills.success.deleted");

            } else {
                req.setAttribute(REQUEST_MESSAGE, "!!!Err. Delete 0 invoice range");
            }

        } catch (Exception ex) {
            //
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();
            
            //
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception. " + ex.toString());
        }

        log.info("End method deleteBills of CreateNewBillsDAO");
        pageForward = SEARCH_BILLS;
        return pageForward;

    }

}
