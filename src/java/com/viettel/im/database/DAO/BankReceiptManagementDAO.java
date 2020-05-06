/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;
//
import com.viettel.im.client.form.ImportBankReceiptForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.BankReceiptExternal;
import com.viettel.im.database.BO.BankReceiptTransError;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VBankReceiptTrans;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tronglv
 */
public class BankReceiptManagementDAO extends com.viettel.database.DAO.BaseDAOAction {

    private static final Logger log = Logger.getLogger(BankReceiptManagementDAO.class);
    private ImportBankReceiptForm form;
    private String pageForward;
    private final String PREPARE_PAGE_IBRM = "preparePageIBRM";
    private final String LIST_IBRM = "listIBRM";
    private final String VIEW_IBR_Detail = "viewIBRDetail";
    private final String PAGE_NAVIGATOR = "pageNavigator";

    public ImportBankReceiptForm getForm() {
        return form;
    }

    public void setForm(ImportBankReceiptForm form) {
        this.form = form;
    }

    /**
     * @Author          :   TrongLV
     * @CreateDate      :   01/08/2010
     * @Purpose         :   Quan ly giao dich import giay nop tien ngan hang
     * @return
     * @throws Exception
     */
    public String preparePageIBRM() throws Exception {
        log.info("# Begin method preparePageIBRM of BankReceiptManagementDAO...");
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                throw new Exception("Time out session");
            }

            form = (ImportBankReceiptForm) this.getTabSession("form");
            if (form == null) {
                form = new ImportBankReceiptForm();
                form.setFromDateSearch(DateTimeUtils.getSysdateForWeb());
                form.setToDateSearch(DateTimeUtils.getSysdateForWeb());
            }

            this.searchImportBankReceipt();

            req.setAttribute(Constant.RETURN_MESSAGE, "");
            pageForward = PREPARE_PAGE_IBRM;

        } catch (Exception ex) {
            ex.printStackTrace();
            pageForward = Constant.ERROR_PAGE;
        }
        log.info("# End method preparePageIBRM of BankReceiptManagementDAO");
        return pageForward;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public String searchImportBankReceipt() throws Exception {
        log.info("# Begin method searchImportBankReceipt of BankReceiptManagementDAO...");
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                throw new Exception("Time out session");
            }
            if (form == null) {
                form = new ImportBankReceiptForm();
                form.setFromDateSearch(DateTimeUtils.getSysdateForWeb());
                form.setToDateSearch(DateTimeUtils.getSysdateForWeb());
            }

            Date fromDate = null;
            Date toDate = null;
            if (form.getFromDateSearch() != null && !form.getFromDateSearch().trim().equals("")) {
                fromDate = DateTimeUtils.convertStringToDateForWeb(form.getFromDateSearch().trim());
                if (fromDate == null) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "MSG.DET.137");
                    pageForward = PREPARE_PAGE_IBRM;
                    return pageForward;
                }
            }
            if (form.getToDateSearch() != null && !form.getToDateSearch().trim().equals("")) {
                toDate = DateTimeUtils.convertStringToDateForWeb(form.getToDateSearch().trim());
                if (toDate == null) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "MSG.DET.138");
                    pageForward = PREPARE_PAGE_IBRM;
                    return pageForward;
                }
            }
            if (fromDate != null && toDate != null && fromDate.after(toDate)) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.DET.139");
                pageForward = PREPARE_PAGE_IBRM;
                return pageForward;
            }

            List lstParam = new ArrayList();
            StringBuffer sSql = new StringBuffer(" from VBankReceiptTrans a where 1= 1 ");
            if (fromDate != null) {
                sSql.append(" and transDate >= (?) ");
                lstParam.add(fromDate);
            }
            if (toDate != null) {
                sSql.append(" and transDate-1 < (?) ");
                lstParam.add(toDate);
            }

            sSql.append(" order by transDate desc ");
            Query q = getSession().createQuery(sSql.toString());
            for (int idx = 0; idx < lstParam.size(); idx++) {
                q.setParameter(idx, lstParam.get(idx));
            }

            List<VBankReceiptTrans> lstBankReceiptTrans = q.list();
            if (lstBankReceiptTrans != null && !lstBankReceiptTrans.isEmpty()) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Found (" + lstBankReceiptTrans.size() + ") result");
                req.setAttribute("lstBankReceiptTrans", lstBankReceiptTrans);
            } else {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.doNotFindData");
            }

            pageForward = PREPARE_PAGE_IBRM;

        } catch (Exception ex) {
            ex.printStackTrace();
            pageForward = Constant.ERROR_PAGE;
        }
        log.info("# End method searchImportBankReceipt of BankReceiptManagementDAO");
        return pageForward;
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public String viewIBRDetail() throws Exception {
        log.info("# Begin method viewIBRDetail of BankReceiptManagementDAO...");
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                throw new Exception("Time out session");
            }
            String strTransId = req.getParameter("transId");
            String isError = req.getParameter("isError");
            if (strTransId == null || strTransId.trim().equals("")) {
                req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.noTransId");
                pageForward = VIEW_IBR_Detail;
                return pageForward;
            }
            if (isError == null || isError.trim().equals("")) {
                isError = "0";
            }
            Long transId = Long.parseLong(strTransId.trim());

            StringBuffer sSql = new StringBuffer(" ");
            if (!isError.equals("0")) {
                sSql.append(" from BankReceiptTransError a where 1= 1 and transId = ? order by content ");
            } else {
                sSql.append("select new com.viettel.im.database.BO.BankReceiptExternal( ");
                sSql.append("       a.bankReceiptDate, ");
                sSql.append("       a.bankReceiptCode, ");
                sSql.append("       a.otherCode, ");
                sSql.append("       a.amount, ");
                sSql.append("       a.content, ");

                sSql.append("       d.bankCode, ");
                sSql.append("       d.bankName, ");
                sSql.append("       e.code, ");
                sSql.append("       e.name, ");
                sSql.append("       c.accountNo, ");

                sSql.append("       b.shopCode, ");
                sSql.append("       b.name ");

                sSql.append("       )");
                sSql.append("from   BankReceiptExternal a, ");
                sSql.append("       Shop b, ");
                sSql.append("       BankAccount c, ");
                sSql.append("       Bank d, ");
                sSql.append("       BankAccountGroup e ");
                sSql.append("where  1 = 1 ");
                sSql.append("       and a.shopId = b.shopId ");
                sSql.append("       and a.bankAccountId = c.bankAccountId ");
                sSql.append("       and c.bankId = d.bankId ");
                sSql.append("       and c.bankAccountGroupId = e.bankAccountGroupId ");
                sSql.append("       and a.transId = ? ");

            }
            Query q = getSession().createQuery(sSql.toString());
            q.setParameter(0, transId);

            if (!isError.equals("0")) {
                List<BankReceiptTransError> lstBankReceiptTransError = q.list();
                req.setAttribute("lstBankReceiptTransError", lstBankReceiptTransError);
            } else {
                List<BankReceiptExternal> lstBankReceiptTransSussess = q.list();
                req.setAttribute("lstBankReceiptTransSussess", lstBankReceiptTransSussess);
            }

            pageForward = VIEW_IBR_Detail;

        } catch (Exception ex) {
            ex.printStackTrace();
            pageForward = Constant.ERROR_PAGE;
        }
        log.info("# End method viewIBRDetail of BankReceiptManagementDAO");
        return pageForward;
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public String pageNavigator() throws Exception {
        String pageForward = LIST_IBRM;
        try {
            searchImportBankReceipt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pageForward;
    }

}
