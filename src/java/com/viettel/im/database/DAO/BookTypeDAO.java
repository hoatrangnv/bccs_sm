package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.BookTypeForm;
import com.viettel.im.client.form.PrinterParamForm;
import com.viettel.im.client.form.PrinterUserForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.BookType;
import com.viettel.im.database.BO.PrinterParam;
import com.viettel.im.database.BO.PrinterUser;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for BookType entities.
 * Transaction control of the save(), update() and delete() operations
can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions.
Each of these methods provides additional information for how to configure it for the desired type of transaction control.
 * @see com.viettel.bccs.im.database.BO.BookType
 * @author MyEclipse Persistence Tools
 */
public class BookTypeDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(BookTypeDAO.class);
    //property constants
    public static final String NUM_INVOICE = "numInvoice";
    public static final String BLOCK_NAME = "blockName";
    public static final String LENGTH_NAME = "lengthName";
    public static final String LENGTH_INVOICE = "lengthInvoice";
    public static final String TYPE = "type";
    public static final String BOOK = "book";
    private static final String GetBookType = "GetBookTypePrepare";
    private static final String LisParamBookType = "LisParamBookType";
    private static final String PrinterUser = "PrinterUser";
    private static final String REQUEST_BOOK_TYPE_MODE = "bookTypeMode";
    private static final String REQUEST_LIST_BOOK_TYPE = "listBookType";
    private static final String REQUEST_LIST_PRINTER_USER = "listPinterUser";
    private BookTypeForm bookTypeForm = new BookTypeForm();
    private PrinterParamForm printerParamForm = new PrinterParamForm();
    private PrinterUserForm printerUserForm = new PrinterUserForm();

    public PrinterParamForm getPrinterParamForm() {
        return printerParamForm;
    }

    public void setPrinterParamForm(PrinterParamForm printerParamForm) {
        this.printerParamForm = printerParamForm;
    }

    public PrinterUserForm getPrinterUserForm() {
        return printerUserForm;
    }

    public void setPrinterUserForm(PrinterUserForm printerUserForm) {
        this.printerUserForm = printerUserForm;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage");
        String pageForward = GetBookType;
        HttpServletRequest req = getRequest();
        BookTypeForm f = this.getBookTypeForm();
        f.resetForm();

        List listBookType = new ArrayList();
        listBookType = findByStatus();
        req.getSession().removeAttribute("listBookType");
        req.getSession().setAttribute("listBookType", listBookType);
        req.getSession().setAttribute("toEditBookType", 0);
        log.info("End method preparePage");
        return pageForward;

    }

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of BoardsDAO ...");
        String pageForward = "pageNavigator";
        log.info("End method preparePage of BoardsDAO");
        return pageForward;
    }

    public String pageNavigatorParam() throws Exception {
        log.info("Begin method preparePage of BoardsDAO ...");
        String pageForward = LisParamBookType;
        log.info("End method preparePage of BoardsDAO");
        return pageForward;
    }

    //Hien thi thong tin Param cua mot serial_code
    public String prepareEditPrinterParamBookType() throws Exception {
        log.info("Begin method preparePage of BoardsDAO ...");
        String pageForward = LisParamBookType;
        HttpServletRequest req = getRequest();
        PrinterParamForm f = this.getPrinterParamForm();
        String serialCode = (String) QueryCryptUtils.getParameter(req, "serialCode");
        if (serialCode == null || serialCode.equals("")) {
            serialCode = f.getSerialCode();
        }
        req.getSession().setAttribute("serialCode", serialCode);
        f.setSerialCode(serialCode);
        f.resetForm();
        List listParamBookType = new ArrayList();
        listParamBookType = findByStatusandserialCode(serialCode);
        req.getSession().removeAttribute("listParamBookType");
        req.getSession().setAttribute("listParamBookType", listParamBookType);
        req.getSession().setAttribute("toEditParamBookType", 0);
        log.info("End method preparePage of BoardsDAO");
        return pageForward;
    }

    /**
     *
     * author       : VuNT
     * date         :
     * purpose      : tim kiem book_type
     * modified     : tamdt1. 29/10/2009
     *
     */
    public String searchBookType() throws Exception {
        log.info("Begin method searchBoards of BookTypeDAO...");

        HttpServletRequest req = getRequest();
        BookTypeForm f = this.getBookTypeForm();
        String pageForward = GetBookType;
        List listParameter = new ArrayList();
        String strQuery = "from BookType where 1 = 1 ";
        if (f.getNuminvoice() != null && !f.getNuminvoice().trim().equals("")) {
            try {
                if (Long.parseLong(f.getNuminvoice().trim()) < 0) {
                    req.setAttribute("message", "BookType.error.numInvoiceFormat");
                    return pageForward;
                } else {
                    listParameter.add(Long.parseLong(f.getNuminvoice().trim()));
                    strQuery += " and numInvoice = ? ";
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "BookType.error.numInvoiceFormat");
                return pageForward;
            }
        }
        if (f.getBlockname() != null && !f.getBlockname().trim().equals("")) {
            listParameter.add("%" + f.getBlockname().trim() + "%");
            strQuery += " and lower(blockName) like lower (?) ";
        }
        if (f.getLengthname() != null && !f.getLengthname().trim().equals("")) {
            try {
                if (Long.parseLong(f.getLengthname().trim()) < 0) {
                    req.setAttribute("message", "BookType.error.lengthNameFormat");
                    return pageForward;
                } else {
                    listParameter.add(Long.parseLong(f.getLengthname().trim()));
                    strQuery += " and lengthName = ? ";
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "BookType.error.lengthNameFormat");
                return pageForward;
            }
        }
        if (f.getLengthinvoice() != null && !f.getLengthinvoice().trim().equals("")) {
            try {
                if (Long.parseLong(f.getLengthinvoice().trim()) < 0) {
                    req.setAttribute("message", "BookType.error.lengthInvoiceFormat");
                    return pageForward;
                } else {
                    listParameter.add(Long.parseLong(f.getLengthinvoice().trim()));
                    strQuery += " and lengthInvoice = ? ";
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "BookType.error.lengthInvoiceFormat");
                return pageForward;
            }
        }
        if (f.getType() != null && !f.getType().trim().equals("")) {
            listParameter.add(f.getType().trim());
            strQuery += " and type like ? ";
        }
        if (f.getBook() != null && !f.getBook().trim().equals("")) {
            try {
                if (Long.parseLong(f.getBook().trim()) < 0) {
                    req.setAttribute("message", "BookType.error.BookNumberFormat");
                    return pageForward;
                } else {
                    listParameter.add(Long.parseLong(f.getBook().trim()));
                    strQuery += " and book like ? ";
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "BookType.error.BookNumberFormat");
                return pageForward;
            }
        }
        if (f.getSerialreal() != null && !f.getSerialreal().trim().equals("")) {
            listParameter.add("%" + f.getSerialreal().trim() + "%");
            strQuery += " and serialReal like ?  ";
        }
        if (f.getSerialcode() != null && !f.getSerialcode().trim().equals("")) {
            listParameter.add("%" + f.getSerialcode().trim() + "%");
            strQuery += " and serialCode like ? ";
        }
        if (f.getDecription() != null && !f.getDecription().trim().equals("")) {
            listParameter.add("%" + f.getDecription().trim() + "%");
            strQuery += " and lower(description) like lower(?) ";
        }
        if (f.getPagewith() != null && !f.getPagewith().trim().equals("")) {
            try {
                if (Long.parseLong(f.getPagewith().trim()) < 0) {
                    req.setAttribute("message", "BookType.error.pageWidthFormat");
                    return pageForward;
                } else {
                    listParameter.add(Long.parseLong(f.getPagewith().trim()));
                    strQuery += " and pageWidth = ? ";
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "BookType.error.pageWidthFormat");
                return pageForward;
            }
        }
        if (f.getPageheight() != null && !f.getPageheight().trim().equals("")) {
            try {
                if (Long.parseLong(f.getPageheight().trim()) < 0) {
                    req.setAttribute("message", "BookType.error.pageHeightFormat");
                    return pageForward;
                } else {
                    listParameter.add(Long.parseLong(f.getPageheight().trim()));
                    strQuery += " and pageHeight = ? ";
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "BookType.error.pageHeightFormat");
                return pageForward;
            }
        }
        if (f.getRowspacing() != null && !f.getRowspacing().trim().equals("")) {
            try {
                if (Long.parseLong(f.getRowspacing().trim()) < 0) {
                    req.setAttribute("message", "BookType.error.rowSpacingFormat");
                    return pageForward;
                } else {
                    listParameter.add(Long.parseLong(f.getRowspacing().trim()));
                    strQuery += " and rowSpacing = ? ";
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "BookType.error.rowSpacingFormat");
                return pageForward;
            }
        }
        if (f.getMaxrow() != null && !f.getMaxrow().trim().equals("")) {
            try {
                if (Long.parseLong(f.getMaxrow().trim()) < 0) {
                    req.setAttribute("message", "BookType.error.maxRowFormat");
                    return pageForward;
                } else {
                    listParameter.add(Long.parseLong(f.getMaxrow().trim()));
                    strQuery += " and maxRow = ? ";
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "BookType.error.maxRowFormat");
                return pageForward;
            }
        }
        if (f.getStatus() != null && !f.getStatus().equals("")) {
            strQuery += " and status = ? ";
            listParameter.add(Long.valueOf(f.getStatus()));
        }

        if (f.getInvoiceType() != null && f.getInvoiceType().compareTo(0L) > 0) {
            strQuery += " and invoiceType = ? ";
            listParameter.add(f.getInvoiceType());
        }

        strQuery += " order by nlssort( blockName ,'nls_sort=Vietnamese') asc ";

        Query q = getSession().createQuery(strQuery);
        for (int i = 0; i < listParameter.size(); i++) {
            q.setParameter(i, listParameter.get(i));
        }
        List listBoards = new ArrayList();
        listBoards = q.list();
        req.getSession().setAttribute("toEditBoards", 0);

        if (listBoards != null) {
            req.setAttribute("message", getText("MSG.INF.BrasIpool.Found") + String.valueOf(listBoards.size()) + getText("MSG.INF.BrasIpool.Avaiable"));
            List paramMsg = new ArrayList();
            paramMsg.add(listBoards.size());
            req.setAttribute("paramMsg", paramMsg);
        } else {
            req.setAttribute("message", "BookType.search.Notsucess");
        }
        req.getSession().removeAttribute("listBookType");
        req.getSession().setAttribute("listBookType", listBoards);
        log.info("End method searchPartner of BookTypeDAO ...");
        return pageForward;
    }

    /***
     * AnDv
     * 12/11/2009
     * @throws Exception
     */
    //Sao chep BookType
    public String copyBookType() throws Exception {
        log.info("Begin method preparePage of BookTypeDAO ...");
        HttpServletRequest req = getRequest();
        String pageForward = GetBookType;
        BookTypeForm f = this.getBookTypeForm();
        try {
            BookType bo = new BookType();
            bo = findById(Long.parseLong(f.getBooktypeid()));
            BookType copyBookType = new BookType();
            f.copyDataToBO(copyBookType);
            Long BookTypeID = getSequence("BOOK_TYPE_SEQ");
            copyBookType.setBookTypeId(BookTypeID);
            copyBookType.setStatus(Long.parseLong("1"));
            List<BookType> list = (List<BookType>) req.getSession().getAttribute("listBookType");
            //if (!checkBookName(list, f.getBlockname(), 0L)) {
            //  req.setAttribute("message", "BookType.add.DublicadeBookName");
            //return pageForward;
            //}
            //if (!checkSerialReal(list, f.getSerialreal(), 0L)) {
            //  req.setAttribute("message", "BookType.add.DublicadeSerialReal");
            //return pageForward;
            //}
            if (!checkSerialCode(list, f.getSerialcode(), 0L)) {
                req.setAttribute("message", "BookType.add.DublicadeSerialCode");
                req.setAttribute(REQUEST_BOOK_TYPE_MODE, "copy");

                return pageForward;
            }
            getSession().save(copyBookType);
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(bo, copyBookType));
            saveLog(Constant.ACTION_COPY_BOOKTYPE, "Sao loại hóa đơn", lstLogObj, bo.getBookTypeId());
            //sao chep du lieu trong bang printerParam
            String strBookTypeId = f.getBooktypeid();
            String serialCode = null;
            try {
                Long bookTypeId = Long.parseLong(strBookTypeId);
                serialCode = this.findById(bookTypeId).getSerialCode();

            } catch (Exception e) {
            }
            if (serialCode != null) {
                PrinterParamDAO printerParamDAO = new PrinterParamDAO();
                printerParamDAO.setSession(getSession());
                List<PrinterParam> bookParam = printerParamDAO.findBySerialCode(serialCode);
                Long PrinterParamID = -1L;
                PrinterParam pBo;
                if (bookParam.size() > 0) {
                    for (int i = 0; i < bookParam.size(); i++) {
                        pBo = new PrinterParam();
                        PrinterParamID = getSequence("Printer_Param_seq");
                        pBo.setFieldName(bookParam.get(i).getFieldName());
                        pBo.setFont(bookParam.get(i).getFont());
                        pBo.setFontSize(bookParam.get(i).getFontSize());
                        pBo.setFontStyle(bookParam.get(i).getFontStyle());
                        pBo.setHeight(bookParam.get(i).getHeight());
                        pBo.setIsDetailField(bookParam.get(i).getIsDetailField());
                        pBo.setPrinterParamId(PrinterParamID);
                        pBo.setSerialCode(f.getSerialcode());
                        pBo.setStatus(bookParam.get(i).getStatus());
                        pBo.setWidth(bookParam.get(i).getWidth());
                        pBo.setXCoordinates(bookParam.get(i).getXCoordinates());
                        pBo.setYCoordinates(bookParam.get(i).getYCoordinates());
//                        bookParam.get(i).setPrinterParamId(PrinterParamID);
//                        bookParam.get(i).setSerialCode(f.getSerialcode());
                        printerParamDAO.save(pBo);
                    }
                }
            }

            f.resetForm();
            req.setAttribute("message", "BookType.add.success");
            List listBookType = new ArrayList();
            listBookType = findByStatus();
            req.getSession().removeAttribute("listBookType");
            req.getSession().setAttribute("listBookType", listBookType);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("End method copyBookType of BookTypeDAO");
        return pageForward;

    }

    //Them moi BookType
    public String addBookType() throws Exception {
        log.info("Begin method preparePage of BookTypeDAO ...");
        HttpServletRequest req = getRequest();
        String pageForward = GetBookType;
        BookTypeForm f = this.getBookTypeForm();
        try {
            BookType bo = new BookType();
            f.copyDataToBO(bo);
            Long BookTypeID = getSequence("BOOK_TYPE_SEQ");
            bo.setBookTypeId(BookTypeID);
            bo.setStatus(Long.parseLong("1"));
            List<BookType> list = (List<BookType>) req.getSession().getAttribute("listBookType");
            //if (!checkBookName(list, f.getBlockname(), 0L)) {
            //  req.setAttribute("message", "BookType.add.DublicadeBookName");
            //return pageForward;
            //}
            //if (!checkSerialReal(list, f.getSerialreal(), 0L)) {
            //  req.setAttribute("message", "BookType.add.DublicadeSerialReal");
            //return pageForward;
            //}
            if (!checkSerialCode(list, f.getSerialcode(), 0L)) {
                req.setAttribute("message", "BookType.add.DublicadeSerialCode");
                return pageForward;
            }
            getSession().save(bo);
            f.resetForm();
            req.setAttribute("message", "BookType.add.success");
            List listBookType = new ArrayList();
            listBookType = findByStatus();
            req.getSession().removeAttribute("listBookType");
            req.getSession().setAttribute("listBookType", listBookType);
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(null, bo));
            saveLog(Constant.ACTION_ADD_BOOKTYPE, "Thêm mới loại hóa đơn", lstLogObj, bo.getBookTypeId());

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("End method preparePage of BookTypeDAO");
        return pageForward;
    }
    //Cap nhat BookType

    public String editBookType() throws Exception {
        log.info("Begin method preparePage of BoardsDAO ...");
        HttpServletRequest req = getRequest();
        String pageForward;
        pageForward = GetBookType;
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        BookTypeForm f = this.getBookTypeForm();
        if (userToken != null) {
            try {
                
                List<BookType> list = (List<BookType>) req.getSession().getAttribute("listBookType");
                //if (!checkBookName(list, f.getBlockname(), bo.getBookTypeId())) {
                //  req.setAttribute("message", "BookType.add.DublicadeBookName");
                //return pageForward;
                //}
                //if (!checkSerialReal(list, f.getSerialreal(), bo.getBookTypeId())) {
                //  req.setAttribute("message", "BookType.add.DublicadeSerialReal");
                //return pageForward;
                //}
                if (!checkSerialCode(list, f.getSerialcode(), Long.parseLong(f.getBooktypeid()))) {
                    req.setAttribute("message", "BookType.add.DublicadeSerialCode");
                    return pageForward;
                }

                BookType bo = new BookType();
                BookType oldBookType = new BookType();
                bo = findById(Long.parseLong(f.getBooktypeid()));
                BeanUtils.copyProperties(oldBookType, bo);
                f.copyDataToBO(bo);
                bo.setBookTypeId(Long.parseLong(f.getBooktypeid()));

//                bo.setStatus(1L);
                getSession().update(bo);
                f.resetForm();
                req.setAttribute("message", "BookType.edit.success");
                req.getSession().setAttribute("toEditBookType", 0);
                List listBoards = new ArrayList();
                listBoards = findByStatus();
                req.getSession().removeAttribute("listBookType");
                req.getSession().setAttribute("listBookType", listBoards);
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(oldBookType, bo));
                saveLog(Constant.ACTION_UPDATE_BOOKTYPE, "Cập nhật loại hóa đơn", lstLogObj, bo.getBookTypeId());
                pageForward = GetBookType;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method preparePage of BoardsDAO");
        return pageForward;
    }
    //Xoa BookType

    public String deleteBookType() throws Exception {
        log.info("Begin method preparePage of BookTypeDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = GetBookType;
        BookTypeForm f = this.getBookTypeForm();
        if (userToken != null) {
            try {
                String strbookTypeId = (String) QueryCryptUtils.getParameter(req, "bookTypeId");
                Long bookTypeId;
                try {
                    bookTypeId = Long.parseLong(strbookTypeId);
                } catch (Exception ex) {
                    bookTypeId = -1L;
                }
                BookType bo = new BookType();
                bo = findById(bookTypeId);
                String sqlString = "select book_type_id from invoice_list where book_type_id = ?"
                        + " union all"
                        + " select book_type_id from invoice_destroyed where book_type_id = ?";
                Query queryObject = getSession().createSQLQuery(sqlString.toString());
                queryObject.setParameter(0, bo.getBookTypeId());
                queryObject.setParameter(1, bo.getBookTypeId());
                List listBookType = queryObject.list();
                if (listBookType.size() != 0) {
                    req.setAttribute("message", "BookType.delete.error");
                    return pageForward;

                }
                try {
                    delete(bo);
                    f.resetForm();
                    req.setAttribute("message", "BookType.delete.success");
                    req.getSession().setAttribute("toEditBookType", 0);
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(bo, null));
                    saveLog(Constant.ACTION_DELETE_BOOKTYPE, "Xóa loại hóa đơn", lstLogObj, bo.getBookTypeId());

                } catch (Exception e) {
                    req.setAttribute("message", "BooType.delete.error");
                }
                List listBoards = new ArrayList();
                listBoards = findByStatus();
                req.getSession().removeAttribute("listBookType");
                req.getSession().setAttribute("listBookType", listBoards);
//                bo.setStatus(Constant.STATUS_DELETE);
//                updateStatus(bookTypeId);


                pageForward = GetBookType;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method preparePage of BookTypeDAO");
        return pageForward;
    }
    //Hien thong tin de cap nhat BookType

    public String prepareEditBookType() throws Exception {
        log.info("Begin method preparePage of BookTypeDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = GetBookType;
        BookTypeForm f = this.getBookTypeForm();
        if (userToken != null) {
            try {
                String strBookTypeID = (String) QueryCryptUtils.getParameter(req, "bookTypeId");
                String strIsCopy = req.getParameter("isCopy");
                Long BookTypeID;
                Long isCopy;
                try {
                    BookTypeID = Long.parseLong(strBookTypeID);
                    isCopy = Long.parseLong(strIsCopy);
                } catch (Exception ex) {
                    BookTypeID = -1L;
                    isCopy = -1L;
                }

                try {
                    BookType bo = new BookType();
                    bo = findById(BookTypeID);
                    f.copyDataFromBO(bo);
                    if (isCopy == 0) {
                        req.setAttribute(REQUEST_BOOK_TYPE_MODE, "prepareAddOrEdit");
                    } else {
                        req.setAttribute(REQUEST_BOOK_TYPE_MODE, "copy");
                    }
                    req.getSession().setAttribute("toEditBookType", 1);
                } catch (Exception ex) {
                }
                pageForward = GetBookType;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method preparePage of BookTypeDAO");
        return pageForward;
    }

    //Them moi ParamBookType
    public String addParamBookType() throws Exception {
        log.info("Begin method preparePage of ParamBookTypeDAO ...");
        HttpServletRequest req = getRequest();
        String pageForward = LisParamBookType;
        PrinterParamForm f = this.getPrinterParamForm();
        try {
            PrinterParam bo = new PrinterParam();
            f.copyDataToBO(bo);
            Long PrinterParamID = getSequence("Printer_Param_seq");
            bo.setPrinterParamId(PrinterParamID);
            //String serialCode = (String) req.getSession().getAttribute("serialCode");
//            bo.setStatus(1L);
            List<PrinterParam> list = (List<PrinterParam>) req.getSession().getAttribute("listParamBookType");
            if (!checkFieldName(list, f.getFieldName(), 0L)) {
                req.setAttribute("messageParam", "PrinterParam.add.DublicadeBookName");
                return pageForward;
            }
            PrinterParamDAO printerParamDAO = new PrinterParamDAO();
            printerParamDAO.setSession(getSession());
            printerParamDAO.save(bo);
            f.resetForm();
            req.setAttribute("messageParam", "ParamBookType.add.success");
            List listParamBookType = new ArrayList();
            listParamBookType = findByStatusandserialCode(f.getSerialCode());
            req.getSession().removeAttribute("listParamBookType");
            req.getSession().setAttribute("listParamBookType", listParamBookType);
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(null, bo));
            saveLog(Constant.ACTION_ADD_PRINTERPARAM, "Thêm mới thông số in", lstLogObj, bo.getPrinterParamId());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("End method preparePage of ParamBookTypeDAO");
        return pageForward;
    }
    //Cap nhat ParamBookType

    public String editParamBookType() throws Exception {
        log.info("Begin method preparePage of ParamBookTypeDAO ...");
        String pageForward;
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = LisParamBookType;
        PrinterParamForm f = this.getPrinterParamForm();
        if (userToken != null) {
            try {
                PrinterParam oldPrinterParam = new PrinterParam();
                PrinterParamDAO printerDAO = new PrinterParamDAO();
                printerDAO.setSession(getSession());
                PrinterParam bo = printerDAO.findById(Long.parseLong(f.getPrinterParamId()));
                BeanUtils.copyProperties(oldPrinterParam, bo);
                f.copyDataToBO(bo);
                String serialCode = (String) req.getSession().getAttribute("serialCode");
                bo.setPrinterParamId(Long.parseLong(f.getPrinterParamId()));

                bo.setSerialCode(serialCode);
//                bo.setStatus(1L);
//                bo.setStatus(1L);
                List<PrinterParam> list = (List<PrinterParam>) req.getSession().getAttribute("listParamBookType");
                if (!checkFieldName(list, f.getFieldName(), bo.getPrinterParamId())) {
                    req.setAttribute("messageParam", "PrinterParam.add.DublicadeBookName");
                    return pageForward;
                }
                getSession().update(bo);
                f.resetForm();
                req.setAttribute("messageParam", "ParamBookType.edit.success");
                req.getSession().setAttribute("toEditParamBookType", 0);
                List listParamBookType = new ArrayList();
                listParamBookType = findByStatusandserialCode(serialCode);
                req.getSession().removeAttribute("listParamBookType");
                req.getSession().setAttribute("listParamBookType", listParamBookType);
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(oldPrinterParam, bo));
                saveLog(Constant.ACTION_UPDATE_PRINTERPARAM, "Cập nhật thông số in", lstLogObj, bo.getPrinterParamId());
                pageForward = LisParamBookType;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method preparePage of ParamBookTypeDAO");
        return pageForward;
    }
    //Xoa ParamBookType

    public String deleteParamBookType() throws Exception {
        log.info("Begin method preparePage of ParamBookTypeDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = LisParamBookType;
        BookTypeForm f = this.getBookTypeForm();
        if (userToken != null) {
            try {
                String strprinterParamId = (String) QueryCryptUtils.getParameter(req, "printerParamId");
                Long printerParamId;
                try {
                    printerParamId = Long.parseLong(strprinterParamId);
                } catch (Exception ex) {
                    printerParamId = -1L;
                }
                try {
                    String strQuery = "delete from PrinterParam where printerParamId = ?";
                    Query q = getSession().createQuery(strQuery);
                    q.setParameter(0, printerParamId);
                    q.executeUpdate();
//                    PrinterParam bo = (PrinterParam) q.list().get(0);
//                    getSession().delete(bo);
                    f.resetForm();
                    req.setAttribute("messageParam", "ParamBookType.delete.success");
                } catch (Exception e) {
                    req.setAttribute("messageParam", "ParamBookType.delete.error");
                }
//                updateStatusParam(printerParamId);
//                f.resetForm();
//                req.setAttribute("messageParam", "ParamBookType.delete.success");                                
                req.getSession().setAttribute("toEditParamBookType", 0);
                String serialCode = (String) req.getSession().getAttribute("serialCode");
                List listParamBookType = new ArrayList();
                listParamBookType = findByStatusandserialCode(serialCode);
                req.getSession().removeAttribute("listParamBookType");
                req.getSession().setAttribute("listParamBookType", listParamBookType);
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj("PRINTER_PARAM", "STATUS", "1", "0"));
                lstLogObj.add(new ActionLogsObj("PRINTER_PARAM", "PARAMBOOKTYPEID", strprinterParamId, ""));
                saveLog(Constant.ACTION_DELETE_PRINTERPARAM, "Xóa thông số in", lstLogObj, printerParamId);
                pageForward = LisParamBookType;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method preparePage of ParamBookTypeDAO");
        return pageForward;
    }
    //Hien thong tin de cap nhat ParamBookType

    public String prepareEditParamBookType() throws Exception {
        log.info("Begin method preparePage of ParamBookTypeDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = LisParamBookType;
        // PrinterParamForm f = this.getPrinterParamForm();
        if (userToken != null) {
            try {
                String strprinterParamID = (String) QueryCryptUtils.getParameter(req, "printerParamId");
                Long printerParamId;
                try {
                    printerParamId = Long.parseLong(strprinterParamID);
                } catch (Exception ex) {
                    printerParamId = -1L;
                }
                try {
                    PrinterParam bo = new PrinterParam();
                    PrinterParamDAO printerParamDAO = new PrinterParamDAO();
                    printerParamDAO.setSession(getSession());
                    bo = printerParamDAO.findById(printerParamId);
                    printerParamForm.copyDataFromBO(bo);
                    req.getSession().setAttribute("toEditParamBookType", 1);
                } catch (Exception ex) {
                }
                pageForward = LisParamBookType;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method preparePage of ParamBookTypeDAO");
        return pageForward;
    }

    public BookTypeForm getBookTypeForm() {
        return bookTypeForm;
    }

    public void setBookTypeForm(BookTypeForm bookTypeForm) {
        this.bookTypeForm = bookTypeForm;
    }

    public void save(BookType transientInstance) {
        log.debug("saving BookType instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(BookType persistentInstance) {
        log.debug("deleting BookType instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public BookType findById(java.lang.Long id) {
        log.debug("getting BookType instance with id: " + id);
        try {
            BookType instance = (BookType) getSession().get("com.viettel.im.database.BO.BookType", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(BookType instance) {
        log.debug("finding BookType instance by example");
        try {
            List results = getSession().createCriteria("com.viettel.im.database.BO.BookType").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding BookType instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from BookType as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName)  + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByNumInvoice(Object numInvoice) {
        return findByProperty(NUM_INVOICE, numInvoice);
    }

    public List findByBlockName(Object blockName) {
        return findByProperty(BLOCK_NAME, blockName);
    }

    public List findByLengthName(Object lengthName) {
        return findByProperty(LENGTH_NAME, lengthName);
    }

    public List findByLengthInvoice(Object lengthInvoice) {
        return findByProperty(LENGTH_INVOICE, lengthInvoice);
    }

    public List findByType(Object type) {
        return findByProperty(TYPE, type);
    }

    public List findByBook(Object book) {
        return findByProperty(BOOK, book);
    }

    public List findAll() {
        log.debug("finding all BookType instances");
        try {
            String queryString = "from BookType";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public BookType merge(BookType detachedInstance) {
        log.debug("merging BookType instance");
        try {
            BookType result = (BookType) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BookType instance) {
        log.debug("attaching dirty BookType instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(BookType instance) {
        log.debug("attaching clean BookType instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void updateStatus(Long bookTypeId) {
        log.info("Begin");

        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" update ");
        sqlBuffer.append(" Book_Type ");
        sqlBuffer.append(" Set Status = 0 ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" Book_Type_ID = ? ");
        Query query = getSession().createSQLQuery(sqlBuffer.toString());
        query.setParameter(0, bookTypeId);
        query.executeUpdate();
        log.info("End");
    }

    public void updateStatusParam(Long printerParamId) {
        log.info("Begin");
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" update ");
        sqlBuffer.append(" Printer_Param ");
        sqlBuffer.append(" Set Status = 0 ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" Printer_Param_ID = ? ");
        Query query = getSession().createSQLQuery(sqlBuffer.toString());
        query.setParameter(0, printerParamId);
        query.executeUpdate();
        log.info("End");
    }

    public List findByStatus() {
        log.debug("finding all BookType instances");
        try {
            String queryString = "from BookType order by serialCode";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findByStatusandserialCode(String serialCode) {
        log.debug("finding all BookType instances");
        try {
            String queryString = "from PrinterParam "
                    + "where serialCode = ? ";
            Query queryObject = getSession().createQuery(queryString);
//            queryObject.setParameter(0, Constant.STATUS_DELETE);
            queryObject.setParameter(0, serialCode);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public BookType getBookTypeBySerialCode(String serialCode) {
        BookType bookType = null;
        if (serialCode != null || !serialCode.trim().equals("")) {

            String strQuery = "from BookType where lower(serialCode) = ? and status = ?  ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, serialCode.trim().toLowerCase());
            query.setParameter(1, Constant.STATUS_USE);
            List<BookType> listBookType = query.list();
            if (listBookType != null && listBookType.size() == 1) {
                bookType = listBookType.get(0);
            }
        }

        return bookType;
    }

    //Check trung tên bookName
    private boolean checkBookName(List<BookType> listBookType, String bookName, Long Index) {
        log.info("Begin.");
        boolean valid = true;
        for (BookType booktype : listBookType) {
            if (booktype.getBlockName().toUpperCase().equals(bookName.trim().toUpperCase()) && booktype.getBookTypeId().compareTo(Index) != 0) {
                valid = false;
                return valid;
            }
        }
        log.info("End.");
        return valid;
    }
    //Check trung serialcode

    private boolean checkSerialCode(List<BookType> listBookType, String serialcode, Long Index) {
        log.info("Begin.");
        boolean valid = true;
        for (BookType booktype : listBookType) {
            if ((booktype.getSerialCode() != null) && booktype.getSerialCode().toUpperCase().equals(serialcode.trim().toUpperCase()) && booktype.getBookTypeId().compareTo(Index) != 0) {
                valid = false;
                return valid;
            }
        }
        log.info("End.");
        return valid;
    }
    //Check trung serialReal

    private boolean checkSerialReal(List<BookType> listBookType, String serialReal, Long Index) {
        log.info("Begin.");
        boolean valid = true;
        for (BookType booktype : listBookType) {
            if ((booktype.getSerialReal() != null) && booktype.getSerialReal().toUpperCase().equals(serialReal.trim().toUpperCase()) && booktype.getBookTypeId().compareTo(Index) != 0) {
                valid = false;
                return valid;
            }
        }
        log.info("End.");
        return valid;
    }
    //Check trung fieldName

    private boolean checkFieldName(List<PrinterParam> listPrinterParam, String fieldName, Long Index) {
        log.info("Begin.");
        boolean valid = true;
        for (PrinterParam printerParam : listPrinterParam) {
            if (printerParam.getFieldName().toUpperCase().equals(fieldName.trim().toUpperCase()) && printerParam.getPrinterParamId().compareTo(Index) != 0) {
                valid = false;
                return valid;
            }
        }
        log.info("End.");
        return valid;
    }

    public String preparePagePrinterUser() throws Exception {
        log.info("Begin method preparePage");
        String pageForward = PrinterUser;
        HttpServletRequest req = getRequest();
        PrinterUserForm f = this.getPrinterUserForm();
        f.resetForm();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        getListSerialCode();
        getListPrinterUser(userToken.getShopCode());
        req.getSession().setAttribute("toEditPrinterUser", 0);
        printerUserForm.setUserName(userToken.getShopCode());
        printerUserForm.setIpAddress(req.getRemoteAddr());

        log.info("End method preparePage");
        return pageForward;

    }

    private void getListSerialCode() throws Exception {
        try {
            //thiet lap cac bien can thiet cho combobox
            HttpServletRequest req = getRequest();
            String queryString = "from BookType where status = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            List<BookType> listBookType = queryObject.list();
            req.setAttribute(REQUEST_LIST_BOOK_TYPE, listBookType);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    private void getListPrinterUser(String userName) throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String queryString = "from PrinterUser where userName = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, userName);
            List<PrinterUser> listPinterUser = queryObject.list();
            req.setAttribute(REQUEST_LIST_PRINTER_USER, listPinterUser);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    private PrinterUser getListPrinterUserById(Long printerUserId) {
        PrinterUser printerUser = null;
        if (printerUserId == null) {
            printerUserId = -1L;
        }
        String strQuery = "from PrinterUser where id = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, printerUserId);
        List<PrinterUser> listPrinterUser = query.list();
        if (listPrinterUser != null && listPrinterUser.size() > 0) {
            printerUser = listPrinterUser.get(0);
        }

        return printerUser;
    }

    public String addOrEditPrinterUser() throws Exception {
        try {
            log.info("Begin method preparePage");
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            String pageForward = PrinterUser;

            Long printerUserId = this.printerUserForm.getId();
            PrinterUser printerUser = getListPrinterUserById(printerUserId);

            printerUserForm.setUserName(userToken.getShopCode());
            printerUserForm.setIpAddress(req.getRemoteAddr());

            if (printerUser == null) {
                //truong hop them  moi
                if (!checkValidPrinterUserToAdd()) {
                    getListSerialCode();
                    getListPrinterUser(userToken.getShopCode());
                    log.info("End method addOrEditPrinterUser of BookTypeDAO");
                    return pageForward;
                }

                printerUser = new PrinterUser();
                this.printerUserForm.copyDataToBO(printerUser);
                printerUserId = getSequence("PRINTER_USER_SEQ");
                printerUser.setId(printerUserId);
                getSession().save(printerUser);

                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, printerUser));
                saveLog(Constant.ACTION_SAVE_PRINTER_USER, "ACTION_SAVE_PRINTER_USER", lstLogObj, printerUser.getId());

                req.setAttribute("message", "PrinterUser.add.success");
            } else {
                if (!checkValidPrinterUserToEdit()) {
                    getListSerialCode();
                    getListPrinterUser(userToken.getShopCode());
                    log.info("End method addOrEditPrinterUser of BookTypeDAO");
                    return pageForward;
                }

                //Log
                PrinterUser oldPrinterUser = new PrinterUser();
                BeanUtils.copyProperties(oldPrinterUser, printerUser);

                //truong hop cap nhat thong tin
                this.printerUserForm.copyDataToBO(printerUser);
                getSession().update(printerUser);

                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(oldPrinterUser, printerUser));
                saveLog(Constant.ACTION_UPDATE_PRINTER_USER, "ACTION_UPDATE_PRINTER_USER", lstLogObj, printerUser.getId());
                req.setAttribute("message", "PrinterUser.edit.success");
            }

            this.printerUserForm.resetForm();
            req.getSession().setAttribute("toEditPrinterUser", 0);
            getListPrinterUser(userToken.getShopCode());
            getListSerialCode();
            printerUserForm.setUserName(userToken.getShopCode());
            printerUserForm.setIpAddress(req.getRemoteAddr());

            log.info("End method addOrEditPrinterUser of BookTypeDAO");
            return pageForward;
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();

            log.info("End method addOrEditPrinterUser of BookTypeDAO");
            return PrinterUser;
        }
    }

    public String prepareEditPrinterUser() throws Exception {
        log.info("Begin method prepareEditPrinterUser of BookTypeDAO ...");
        HttpServletRequest req = getRequest();
        String strPinterUserId = req.getParameter("selectedPrinterUserId");
        Long printerUserId = -1L;
        if (strPinterUserId != null) {
            try {
                printerUserId = new Long(strPinterUserId);
            } catch (NumberFormatException ex) {
                printerUserId = -1L;
            }
        }

        PrinterUserDAO printerUserDAO = new PrinterUserDAO();
        printerUserDAO.setSession(this.getSession());
        PrinterUser printerUser = printerUserDAO.findById(printerUserId);
        if (printerUser != null) {
            this.printerUserForm.copyDataFromBO(printerUser);
        } else {
            this.printerUserForm.resetForm();
        }
        req.getSession().setAttribute("toEditPrinterUser", 1);
        getListSerialCode();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        getListPrinterUser(userToken.getShopCode());
        String pageForward = PrinterUser;
        return pageForward;
    }

    public String deletePrinterUser() throws Exception {
        log.info("Begin method deletePrinterUser of BookTypeDAO ...");
        HttpServletRequest req = getRequest();
        String pageForward = PrinterUser;
        try {
            String strPinterUserId = req.getParameter("selectedPrinterUserId");
            Long printerUserId = -1L;
            if (strPinterUserId != null) {
                try {
                    printerUserId = new Long(strPinterUserId);
                } catch (NumberFormatException ex) {
                    printerUserId = -1L;
                }
            }

            PrinterUserDAO printerUserDAO = new PrinterUserDAO();
            printerUserDAO.setSession(this.getSession());
            PrinterUser printerUser = printerUserDAO.findById(printerUserId);
            if (printerUser != null) {
                try {
                    //xoa printerUser trong DB
                    printerUserDAO.delete(printerUser);

                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(printerUser, null));
                    saveLog(Constant.ACTION_DELETE_PRINTER_USER, "ACTION_DELETE_PRINTER_USER", lstLogObj, printerUser.getId());

//                    getSession().delete(printerUser);
                    req.setAttribute("message", "PrinterUser.delete.success");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    req.setAttribute("message", "PrinterUser.delete.error");
                    log.info("End method deletePrinterUser of BookTypeDAO");
                    return pageForward;
                }

            }
            getListSerialCode();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            getListPrinterUser(userToken.getShopCode());
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "PrinterUser.delete.error");
            log.info("End method deletePrinterUser of BookTypeDAO");
            return pageForward;
        }
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        printerUserForm.setUserName(userToken.getShopCode());
        printerUserForm.setIpAddress(req.getRemoteAddr());

        log.info("End method deletePrinterUser of BookTypeDAO");
        return pageForward;
    }

    public String searchPrinterUser() throws Exception {
        log.info("Begin method deletePrinterUser of BookTypeDAO ...");
        HttpServletRequest req = getRequest();
        String pageForward = PrinterUser;
        PrinterUserForm printerUserForm = this.printerUserForm;
        List listParameter = new ArrayList();
        String strQuery = "from PrinterUser where 1 = 1 ";
        listParameter.add(printerUserForm.getUserName());
        strQuery += " and userName = ? ";
        if (printerUserForm.getXAmplitude() != null && !printerUserForm.getXAmplitude().trim().equals("")) {
            try {
                listParameter.add(Long.parseLong(printerUserForm.getXAmplitude().trim()));
                strQuery += " and XAmplitude = ? ";
            } catch (NumberFormatException e) {
                req.setAttribute("message", "PrinterUser.error.numInvoiceFormat");
                return pageForward;
            }
        }
        if (printerUserForm.getYAmplitude() != null && !printerUserForm.getYAmplitude().trim().equals("")) {
            try {
                listParameter.add(Long.parseLong(printerUserForm.getYAmplitude().trim()));
                strQuery += " and YAmplitude = ? ";
            } catch (NumberFormatException e) {
                req.setAttribute("message", "PrinterUser.error.numInvoiceFormat");
                return pageForward;
            }
        }
        if (printerUserForm.getInvoiceType() != null) {
            listParameter.add(printerUserForm.getInvoiceType());
            strQuery += " and invoiceType = ? ";
        }
        if (printerUserForm.getSerialCode() != null && !printerUserForm.getSerialCode().trim().equals("")) {
            listParameter.add(printerUserForm.getSerialCode());
            strQuery += " and serialCode = ? ";
        }
        strQuery += " order by nlssort( userName ,'nls_sort=Vietnamese') asc ";

        Query q = getSession().createQuery(strQuery);
        for (int i = 0; i < listParameter.size(); i++) {
            q.setParameter(i, listParameter.get(i));
        }
        List listPrinterUser = new ArrayList();
        listPrinterUser = q.list();
        req.getSession().setAttribute("toEditPrinterUser", 0);
        if (listPrinterUser != null) {
            req.setAttribute("message", "PrinterUser.search");
            List paramMsg = new ArrayList();
            paramMsg.add(listPrinterUser.size());
            req.setAttribute("paramMsg", paramMsg);
        } else {
            req.setAttribute("message", "PrinterUser.search.Notsucess");
        }
        getListSerialCode();
        req.removeAttribute(REQUEST_LIST_PRINTER_USER);
        req.setAttribute(REQUEST_LIST_PRINTER_USER, listPrinterUser);
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        printerUserForm.setUserName(userToken.getShopCode());
        log.info("End method deletePrinterUser of BookTypeDAO");
        return pageForward;
    }

    private boolean checkValidPrinterUserToAdd() {
        HttpServletRequest req = getRequest();
        String XAmplitude = this.printerUserForm.getXAmplitude();
        String YAmplitude = this.printerUserForm.getYAmplitude();
        String serialcode = this.printerUserForm.getSerialCode();
        Long invoiceType = this.printerUserForm.getInvoiceType();
        String userName = this.printerUserForm.getUserName();

        if (XAmplitude == null || YAmplitude == null || serialcode == null || invoiceType == null) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute("message", "PrinterUser.error.requiredFieldsEmpty");
            return false;
        }
        if (printerUserForm.getXAmplitude() != null && !printerUserForm.getXAmplitude().trim().equals("")) {
            try {
                Long.parseLong(printerUserForm.getXAmplitude().trim());
            } catch (NumberFormatException e) {
                req.setAttribute("message", "PrinterUser.error.numXAmplitudeFormat");
                return false;
            }
        }
        if (printerUserForm.getYAmplitude() != null && !printerUserForm.getYAmplitude().trim().equals("")) {
            try {
                Long.parseLong(printerUserForm.getYAmplitude().trim());
            } catch (NumberFormatException e) {
                req.setAttribute("message", "PrinterUser.error.numYAmplitudeFormat");
                return false;
            }
        }
        String strQuery = "select count(*) from PrinterUser where userName = ? "
                + "and invoiceType = ? and serialCode = ?";
        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, userName);
        q.setParameter(1, invoiceType);
        q.setParameter(2, serialcode);
        Long count = (Long) q.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("message", "PrinterUser.add.duplicateName");
            return false;
        }
        return true;
    }

    private boolean checkValidPrinterUserToEdit() {
        HttpServletRequest req = getRequest();
        String XAmplitude = this.printerUserForm.getXAmplitude();
        String YAmplitude = this.printerUserForm.getYAmplitude();
        String serialCode = this.printerUserForm.getSerialCode();
        Long invoiceType = this.printerUserForm.getInvoiceType();
        String userName = this.printerUserForm.getUserName();
        Long printerUserId = this.printerUserForm.getId();

        if (XAmplitude == null || YAmplitude == null || serialCode == null || invoiceType == null) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute("message", "PrinterUser.error.requiredFieldsEmpty");
            return false;
        }
        if (printerUserForm.getXAmplitude() != null && !printerUserForm.getXAmplitude().trim().equals("")) {
            try {
                Long.parseLong(printerUserForm.getXAmplitude().trim());
            } catch (NumberFormatException e) {
                req.setAttribute("message", "PrinterUser.error.numInvoiceFormat");
                return false;
            }
        }
        if (printerUserForm.getYAmplitude() != null && !printerUserForm.getYAmplitude().trim().equals("")) {
            try {
                Long.parseLong(printerUserForm.getYAmplitude().trim());
            } catch (NumberFormatException e) {
                req.setAttribute("message", "PrinterUser.error.numInvoiceFormat");
                return false;
            }
        }
        String strQuery = "select count(*) from PrinterUser as model where userName = ? "
                + " and invoiceType = ? and serialCode = ? and not id = ? ";
        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, userName);
        q.setParameter(1, invoiceType);
        q.setParameter(2, serialCode);
        q.setParameter(3, printerUserId);
        Long count = (Long) q.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("message", "PrinterUser.add.duplicateName");
            return false;
        }
        return true;
    }

    public List<BookType> getBySerialCode(String serialCode, Long status) {
        List listParameter = new ArrayList();
        String strQuery = "from BookType where 1=1 ";
        if (serialCode != null && !serialCode.trim().equals("")) {
            strQuery += " and lower(serialCode) = ? ";
            listParameter.add(serialCode.trim().toLowerCase());
        }
        if (status != null) {
            strQuery += " and status = ? ";
            listParameter.add(status);
        }
        Query query = getSession().createQuery(strQuery);
        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }
        return query.list();
    }
}
