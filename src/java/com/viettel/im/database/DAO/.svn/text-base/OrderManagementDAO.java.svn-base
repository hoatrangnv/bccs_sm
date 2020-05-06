/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.viettel.database.DAO.BaseDAOAction;
import static com.viettel.database.DAO.BaseDAOAction.logEndCall;
import static com.viettel.database.DAO.BaseDAOAction.logStartCallBegin;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.SaleManagmentForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.FTPUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.XmlConfig;
import com.viettel.im.common.util.XmlUtil;
import com.viettel.im.database.BO.AgentTransOrderHis;
import com.viettel.im.database.BO.BankTranferInfo;
import com.viettel.im.database.BO.BankTranferInfoTemp;
import com.viettel.im.database.BO.Deposit;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.RoleTrueChief1;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.SaleTransDetailOrder;
import com.viettel.im.database.BO.SaleTransOrder;
import com.viettel.im.database.BO.SaleTransSerial;
import com.viettel.im.database.BO.SaleTransSerialOrder;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import com.viettel.security.util.DbProcessor;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.jdom.Element;

/**
 *
 * @author itbl_linh
 */
public class OrderManagementDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(OrderManagementDAO.class);
    private static final String SEARCH_ODER = "searchOder";
    public String pageForward;
    private SaleManagmentForm saleManagmentForm = new SaleManagmentForm();
//tannh20180111 upload file va download file
    private InputStream fileInputStream;
    public static String fileDowd;
    private String srcFilePDF;
    private String fileDownloadForm02;
    private String fileName;
    private static final String LIST_GOOD = "lstGoods";         //Danh sach hang hoa

    public InputStream getFileInputStream() {

        return fileInputStream;

    }

    public String getFileDownloadForm02() {
        return fileDownloadForm02;
    }

    public void setFileDownloadForm02(String fileDownloadForm02) {
        this.fileDownloadForm02 = fileDownloadForm02;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSrcFilePDF() {
        return srcFilePDF;
    }

    public void setSrcFilePDF(String srcFilePDF) {
        this.srcFilePDF = srcFilePDF;
    }

    public OrderManagementDAO() {
        String log4JPath = ResourceBundle.getBundle("config").getString("logfilepath");
        PropertyConfigurator.configure(log4JPath);
    }

    public SaleManagmentForm getSaleManagmentForm() {
        return saleManagmentForm;
    }

    public void setSaleManagmentForm(SaleManagmentForm saleManagmentForm) {
        this.saleManagmentForm = saleManagmentForm;
    }

    public String preparePage() throws Exception {

        String pageForward = Constant.ERROR_PAGE;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        DbProcessor db = new DbProcessor();
        if (db.checkRole("SM_CLEAR_DEBIT_BY_HAND", userToken.getLoginName())) {
            req.setAttribute("clearDebitByHand", "TRUE");
        } else {
            List<String> lsRole = getRoleBankOrder(getSession(), userToken.getLoginName());
            if (lsRole != null || lsRole.size() > 0) {
                for (int i = 0; i < lsRole.size(); i++) {
                    if ("BR_ORDER_BANK".equalsIgnoreCase(lsRole.get(i).toString())) {
                        req.setAttribute("roleBrOrderBank", lsRole.get(i).toString());
                    }
                }
            }
        }
        pageForward = SEARCH_ODER;
        return pageForward;

    }

//    tannh20190523 management document bank
    public String preparePageManagementDocument() throws Exception {

        String pageForward = Constant.ERROR_PAGE;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<String> lsRole = getRoleBankOrder(getSession(), userToken.getLoginName());
        if (lsRole != null || lsRole.size() > 0) {
            for (int i = 0; i < lsRole.size(); i++) {
                if ("BR_ORDER_BANK".equalsIgnoreCase(lsRole.get(i).toString())) {
                    req.setAttribute("roleBrOrderBank", lsRole.get(i).toString());
                }
            }
        }

        pageForward = "searchBank";
        return pageForward;

    }

    public String preparePagePopupDocument() throws Exception {

        String pageForward = Constant.ERROR_PAGE;
        HttpServletRequest req = getRequest();
        pageForward = "popupDetailBank";
        String tranferId = req.getParameter("tranferId");
        String tranferCode = req.getParameter("tranferCode");
        req.setAttribute("tranferId", tranferId);
        req.setAttribute("tranferCode", tranferCode);
        try {
            req.getSession().removeAttribute("lstSaleTrans");
            String SQL_SELECT_TRANS = " from BankTranferInfoTemp where 1= 1 ";
            List lstParam = new ArrayList();
            if (tranferCode != null && !tranferCode.trim().equals("")) {
                SQL_SELECT_TRANS += " and lower(tranferCodeFather) = ? ";
                lstParam.add(tranferCode.trim().toLowerCase());
            }

            SQL_SELECT_TRANS += " and status  = 1 and STAFF_APPROVE_DIVIDE is null ";
            SQL_SELECT_TRANS += " order by createTime desc ";
            Query q = getSession().createQuery(SQL_SELECT_TRANS);
            for (int idx = 0; idx < lstParam.size(); idx++) {
                q.setParameter(idx, lstParam.get(idx));
            }
            List lstSaleTrans = q.list();
            req.setAttribute("lstSaleTrans", lstSaleTrans);

        } catch (Exception ex) {
            req.setAttribute("resultUpdateSale", "saleManagment.search.error.undefine");
            return pageForward;
        }
        return pageForward;
    }

    public List getAllBankChildDocumentByBankFather(String tranferCode) throws Exception {
        List lstSaleTrans = new ArrayList();
        try {
            String SQL_SELECT_TRANS = " from BankTranferInfoTemp where 1= 1 ";
            List lstParam = new ArrayList();
            if (tranferCode != null && !tranferCode.trim().equals("")) {
                SQL_SELECT_TRANS += " and lower(tranferCode) like ? ";
                lstParam.add("%" + tranferCode.trim().toLowerCase() + "-%");
            }
            SQL_SELECT_TRANS += " and status  = 1 and STAFF_APPROVE_DIVIDE is null  ";
            SQL_SELECT_TRANS += " order by createTime desc ";
            Query q = getSession().createQuery(SQL_SELECT_TRANS);
            for (int idx = 0; idx < lstParam.size(); idx++) {
                q.setParameter(idx, lstParam.get(idx));
            }
            lstSaleTrans = q.list();

        } catch (Exception ex) {
            log.error("  method getAllBankChildDocumentByBankFather ; Exception : " + ex);
            return lstSaleTrans;
        }
        return lstSaleTrans;
    }

    public String preparePagePopupDetailDocument() throws Exception {

        String pageForward = Constant.ERROR_PAGE;
        HttpServletRequest req = getRequest();
        String tranferId = req.getParameter("tranferId");
        String tranferCode = req.getParameter("tranferCode");
        req.setAttribute("tranferId", tranferId);
        req.setAttribute("tranferCode", tranferCode);
        pageForward = "popupBank";
        return pageForward;
    }

    public List<String> getRoleBankOrder(Session session, String staffCode) {
        List<String> ls = null;
        try {
            String strQuery = " SELECT   r.role_code  FROM   vsa_v3.role_user@cus a, vsa_v3.roles@cus r, vsa_v3.users@cus u where a.role_id = r.role_id \n"
                    + "                and a.user_id = u.user_id and u.status =1 and r.status =1 and a.is_active =1  and LOWER(u.user_name)  = ?";
            Query query = session.createSQLQuery(strQuery);
            query.setParameter(0, staffCode.toLowerCase());
            ls = query.list();
        } catch (RuntimeException e) {
            throw e;
        }

        return ls;
    }

    public String searchSaleTrans() throws Exception {
        log.debug("# Begin method OrderManagementDAO.searchSaleTrans");
        pageForward = "saleManagmentResultOrder";

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }

        if (saleManagmentForm.getSSaleTransCode() == null || "".equalsIgnoreCase(saleManagmentForm.getSSaleTransCode())) {

            //LinhNBV start modified on May 21 2018: Validate from date & to date
            if (saleManagmentForm.getSTransFromDate().isEmpty() || saleManagmentForm.getSTransToDate().isEmpty()) {
                req.setAttribute("resultUpdateSale", "saleManagment.search.error.date.empty");
                return pageForward;
            }
            Date sTransFromDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransFromDate(), "yyyy-MM-dd");
            Date sTransToDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransToDate(), "yyyy-MM-dd");
            int distanceBeetwen2Date = DateTimeUtils.distanceBeetwen2Date(sTransFromDate, sTransToDate);
            if (distanceBeetwen2Date > 30) {
                req.setAttribute("resultUpdateSale", "saleManagment.search.error.over.distance");
                return pageForward;
            }
            //LinhNBV end.

        }
        try {
            req.getSession().removeAttribute("lstSaleTrans");
            String SQL_SELECT_TRANS = "select sale_trans_code as saleTransCode, sale_trans_date as saleTransDate, status as status,"
                    + " amount_tax as amountTax, cust_name as custName, isdn as isdn, contract_no as contractNo, "
                    + "sale_trans_id as saleTransId, sale_trans_order_id as saleTransOrderId, order_code as orderCode,"
                    + "is_check as isCheck, scan_path as scanPath, order_from as orderFrom from Sale_Trans_Order where 1 = 1 ";//  and rowid not in (select rowid from Sale_Trans_Order a1 where  a1.order_From = 'mBCCS' and a1.status not in (5,6,4)  and  a1.sale_Trans_Id <= 0  ) ";
            List lstParam = new ArrayList();
            if (saleManagmentForm.getSSaleTransCode() != null && !saleManagmentForm.getSSaleTransCode().trim().equals("")) {
                SQL_SELECT_TRANS += " and lower(order_Code) like ? ";
                lstParam.add("%" + saleManagmentForm.getSSaleTransCode().trim().toLowerCase() + "%");
            }

            SQL_SELECT_TRANS += " and (exists (select 1 from shop where shop_id = (select shop_id from staff where staff_id = Sale_Trans_Order.receiver_id) and shop_path like ?) "
                    + "or exists (select 1 from shop where shop_id = (select shop_id from staff where staff_id = Sale_Trans_Order.staff_id) and shop_path like ?))";
            lstParam.add("%" + userToken.getShopId() + "%");
            lstParam.add("%" + userToken.getShopId() + "%");


            if (saleManagmentForm.getSTransFromDate() != null && !saleManagmentForm.getSTransFromDate().trim().equals("")) {
                Date fromDate;
                try {
                    fromDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransFromDate().trim());

                } catch (Exception ex) {
                    req.setAttribute("resultUpdateSale", "saleManagment.search.error.fromDate");
                    return pageForward;
                }
                SQL_SELECT_TRANS += " and sale_Trans_Date  >= ?";
                lstParam.add(fromDate);
            }
            if (saleManagmentForm.getSTransToDate() != null && !saleManagmentForm.getSTransToDate().trim().equals("")) {

                Date toDate;
                try {
                    String stoDate = saleManagmentForm.getSTransToDate().trim().substring(0, 10) + " 23:59:59";
                    toDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");

                } catch (Exception ex) {
                    return pageForward;
                }
                SQL_SELECT_TRANS += " and sale_Trans_Date  <= ?";
                lstParam.add(toDate);
            }
            if (saleManagmentForm.getSSaleTransType() == null) {
                saleManagmentForm.setSSaleTransType(3L);
            }
            if (saleManagmentForm.getSSaleTransType() == 2L) {
                SQL_SELECT_TRANS += " and order_from  ='mBCCS' ";
            } else if (saleManagmentForm.getSSaleTransType() == 1L) {
                SQL_SELECT_TRANS += " and order_from  is null ";
            }
            if (saleManagmentForm.getTransStatus() != null && saleManagmentForm.getTransStatus().length() > 0) {
                String[] arrTransStatus = saleManagmentForm.getTransStatus().split("\\|");
                String orderFrom = arrTransStatus[0];
                String status = arrTransStatus[1];

                if (!"4".equals(status)) {
                    if (saleManagmentForm.getSSaleTransType() == 3L) {
                        if ("2".equals(orderFrom)) {
                            SQL_SELECT_TRANS += " and order_from  ='mBCCS' ";
                        } else if ("1".equals(orderFrom)) {
                            SQL_SELECT_TRANS += " and order_from  is null ";
                        }
                    } else {
                        if (saleManagmentForm.getSSaleTransType() != 3L && saleManagmentForm.getSSaleTransType() != Long.valueOf(orderFrom)) {
                            List<SaleTransOrder> lstSaleTrans = new ArrayList<SaleTransOrder>();
                            req.setAttribute("lstSaleTrans", lstSaleTrans);
                            return pageForward;
                        }
                    }
                    if ("2".equals(orderFrom)) {
                        SQL_SELECT_TRANS += " and status = " + status;
                    } else {
                        //Order from BCCS
                        if ("5".equals(status)) {
                            SQL_SELECT_TRANS += " and status = 5 and is_Check = 1";//Approved
                        } else if ("55".equals(status)) {
                            SQL_SELECT_TRANS += " and status = 5 and is_Check = 3";//Sold
                        } else if ("2".equals(status)) {
                            SQL_SELECT_TRANS += " and status = 2 and is_Check = 0";//Processing
                        }

                    }
                } else {
                    SQL_SELECT_TRANS += " and status = 4";
                }
                //list="1|2:MSG.SAE.301, 1|5:MSG.SAE.302 ,1|4:MSG.SAE.311 ,1|55:MSG.SAE.312, 2|2:MSG.SAE.317, 2|6:MSG.SAE.318, 2|5:MSG.SAE.319"/>
            }

            SQL_SELECT_TRANS += " union select sale_trans_code as saleTransCode, sale_trans_date as saleTransDate, status as status,"
                    + " amount_tax as amountTax, cust_name as custName, isdn as isdn, contract_no as contractNo, "
                    + "sale_trans_id as saleTransId, sale_trans_order_id as saleTransOrderId, order_code as orderCode,"
                    + "is_check as isCheck, scan_path as scanPath, order_from as orderFrom from agent_trans_order_his where 1= 1 ";//and rowid not in (select rowid from agent_trans_order_his a1 where  a1.order_From = 'mBCCS' and a1.status !=5  and  a1.sale_Trans_Id <= 0  )   ";
            if (saleManagmentForm.getSSaleTransCode() != null && !saleManagmentForm.getSSaleTransCode().trim().equals("")) {
                SQL_SELECT_TRANS += " and lower(order_Code) like ? ";
                lstParam.add("%" + saleManagmentForm.getSSaleTransCode().trim().toLowerCase() + "%");
            }

            SQL_SELECT_TRANS += " and (exists (select 1 from shop where shop_id = (select shop_id from staff where staff_id = agent_trans_order_his.receiver_id) and shop_path like ? )  "
                    + "or exists (select 1 from shop where shop_id = (select shop_id from staff where staff_id = agent_trans_order_his.staff_id) and shop_path like ? ))";
            lstParam.add("%" + userToken.getShopId() + "%");
            lstParam.add("%" + userToken.getShopId() + "%");

            if (saleManagmentForm.getSTransFromDate() != null && !saleManagmentForm.getSTransFromDate().trim().equals("")) {
                Date fromDate;
                try {
                    fromDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransFromDate().trim());

                } catch (Exception ex) {
                    req.setAttribute("resultUpdateSale", "saleManagment.search.error.fromDate");
                    return pageForward;
                }
                SQL_SELECT_TRANS += " and sale_Trans_Date  >= ?";
                lstParam.add(fromDate);
            }
            if (saleManagmentForm.getSTransToDate() != null && !saleManagmentForm.getSTransToDate().trim().equals("")) {

                Date toDate;
                try {
                    String stoDate = saleManagmentForm.getSTransToDate().trim().substring(0, 10) + " 23:59:59";
                    toDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");

                } catch (Exception ex) {
                    return pageForward;
                }
                SQL_SELECT_TRANS += " and sale_Trans_Date  <= ?";
                lstParam.add(toDate);
            }
            if (saleManagmentForm.getSSaleTransType() == 2L) {
                SQL_SELECT_TRANS += " and order_from  ='mBCCS' ";
            } else if (saleManagmentForm.getSSaleTransType() == 1L) {
                SQL_SELECT_TRANS += " and order_from  is null ";
            }
            if (saleManagmentForm.getTransStatus() != null && saleManagmentForm.getTransStatus().length() > 0) {
                String[] arrTransStatus = saleManagmentForm.getTransStatus().split("\\|");
                String orderFrom = arrTransStatus[0];
                String status = arrTransStatus[1];

                if (!"4".equals(status)) {
                    if (saleManagmentForm.getSSaleTransType() == 3L) {
                        if ("2".equals(orderFrom)) {
                            SQL_SELECT_TRANS += " and order_from  ='mBCCS' ";
                        } else if ("1".equals(orderFrom)) {
                            SQL_SELECT_TRANS += " and order_from  is null ";
                        }
                    } else {
                        if (saleManagmentForm.getSSaleTransType() != 3L && saleManagmentForm.getSSaleTransType() != Long.valueOf(orderFrom)) {
                            List<SaleTransOrder> lstSaleTrans = new ArrayList<SaleTransOrder>();
                            req.setAttribute("lstSaleTrans", lstSaleTrans);
                            return pageForward;
                        }
                    }
                    if ("2".equals(orderFrom)) {
                        SQL_SELECT_TRANS += " and status = " + status;
                    } else {
                        //Order from BCCS
                        if ("5".equals(status)) {
                            SQL_SELECT_TRANS += " and status = 5 and is_Check = 1";//Approved
                        } else if ("55".equals(status)) {
                            SQL_SELECT_TRANS += " and status = 5 and is_Check = 3";//Sold
                        } else if ("2".equals(status)) {
                            SQL_SELECT_TRANS += " and status = 2 and is_Check = 0";//Processing
                        }

                    }
                } else {
                    SQL_SELECT_TRANS += " and status = 4";
                }
                //list="1|2:MSG.SAE.301, 1|5:MSG.SAE.302 ,1|4:MSG.SAE.311 ,1|55:MSG.SAE.312, 2|2:MSG.SAE.317, 2|6:MSG.SAE.318, 2|5:MSG.SAE.319"/>
            }

            Query q = getSession().createSQLQuery(SQL_SELECT_TRANS)
                    .addScalar("saleTransCode", Hibernate.STRING)
                    .addScalar("saleTransDate", Hibernate.TIMESTAMP)
                    .addScalar("status", Hibernate.STRING)
                    .addScalar("amountTax", Hibernate.DOUBLE)
                    .addScalar("custName", Hibernate.STRING)
                    .addScalar("isdn", Hibernate.STRING)
                    .addScalar("contractNo", Hibernate.STRING)
                    .addScalar("saleTransId", Hibernate.LONG)
                    .addScalar("saleTransOrderId", Hibernate.LONG)
                    .addScalar("orderCode", Hibernate.STRING)
                    .addScalar("orderFrom", Hibernate.STRING)
                    .addScalar("isCheck", Hibernate.LONG)
                    .addScalar("scanPath", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(SaleTransOrder.class));;
            for (int idx = 0; idx < lstParam.size(); idx++) {
                q.setParameter(idx, lstParam.get(idx));
            }
            List<SaleTransOrder> lstSaleTrans = q.list();
            req.setAttribute("lstSaleTrans", lstSaleTrans);

        } catch (Exception ex) {
            req.setAttribute("resultUpdateSale", "saleManagment.search.error.undefine");
        }

        return pageForward;
    }

    //tannh20182508 search bank_transfer_info
    public String searchBankTransferInfo() throws Exception {
        log.debug("# Begin method OrderManagementDAO.searchSaleTrans");
        pageForward = "saleManagmentResultOrder";

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }
        DbProcessor db = new DbProcessor();
        if (db.checkRole("SM_CLEAR_DEBIT_BY_HAND", userToken.getLoginName())) {
            req.setAttribute("clearDebitByHand", "TRUE");
        } else {
            List<String> lsRole = getRoleBankOrder(getSession(), userToken.getLoginName());
            if (lsRole != null || lsRole.size() > 0) {
                for (int i = 0; i < lsRole.size(); i++) {
                    if ("BR_ORDER_BANK".equalsIgnoreCase(lsRole.get(i).toString())) {
                        req.setAttribute("roleBrOrderBank", lsRole.get(i).toString());
                    }
                }
            }
        }
        if (saleManagmentForm.getSSaleTransCode() == null || "".equalsIgnoreCase(saleManagmentForm.getSSaleTransCode())) {

            //LinhNBV start modified on May 21 2018: Validate from date & to date
            if (saleManagmentForm.getSTransFromDate().isEmpty() || saleManagmentForm.getSTransToDate().isEmpty()) {
                req.setAttribute("resultUpdateSale", "saleManagment.search.error.date.empty");
                return pageForward;
            }
            Date sTransFromDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransFromDate(), "yyyy-MM-dd");
            Date sTransToDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransToDate(), "yyyy-MM-dd");
            int distanceBeetwen2Date = DateTimeUtils.distanceBeetwen2Date(sTransFromDate, sTransToDate);
            if (distanceBeetwen2Date > 30) {
                req.setAttribute("resultUpdateSale", "saleManagment.search.error.over.distance");
                return pageForward;
            }
            //LinhNBV end.

        }
        try {
            req.getSession().removeAttribute("lstSaleTrans");
            String SQL_SELECT_TRANS = " from BankTranferInfo where 1= 1 ";
            List lstParam = new ArrayList();
            if (saleManagmentForm.getSSaleTransCode() != null && !saleManagmentForm.getSSaleTransCode().trim().equals("")) {
                SQL_SELECT_TRANS += " and lower(tranferCode) like ? ";
                lstParam.add("%" + saleManagmentForm.getSSaleTransCode().trim().toLowerCase() + "%");
            }

            if (saleManagmentForm.getSTransFromDate() != null && !saleManagmentForm.getSTransFromDate().trim().equals("")) {
                Date fromDate;
                try {
                    fromDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransFromDate().trim());

                } catch (Exception ex) {
                    req.setAttribute("resultUpdateSale", "saleManagment.search.error.fromDate");
                    return pageForward;
                }
                SQL_SELECT_TRANS += " and createTime  >= ?";
                lstParam.add(fromDate);
            }
            if (saleManagmentForm.getSTransToDate() != null && !saleManagmentForm.getSTransToDate().trim().equals("")) {

                Date toDate;
                try {
                    String stoDate = saleManagmentForm.getSTransToDate().trim().substring(0, 10) + " 23:59:59";
                    toDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");

                } catch (Exception ex) {
                    return pageForward;
                }
                SQL_SELECT_TRANS += " and createTime  <= ?";
                lstParam.add(toDate);
            }
            if (saleManagmentForm.getSTransStatus() != null) {
                SQL_SELECT_TRANS += " and status  = " + saleManagmentForm.getSTransStatus();
            } else {
                SQL_SELECT_TRANS += " and status  = 1 ";
            }

            SQL_SELECT_TRANS += " order by createTime desc ";
            Query q = getSession().createQuery(SQL_SELECT_TRANS);
            for (int idx = 0; idx < lstParam.size(); idx++) {
                q.setParameter(idx, lstParam.get(idx));
            }
            List lstSaleTrans = q.list();
            req.setAttribute("lstSaleTrans", lstSaleTrans);

        } catch (Exception ex) {
            req.setAttribute("resultUpdateSale", "saleManagment.search.error.undefine");
        }

        return pageForward;
    }

    //tannh20182508 search bank_transfer_info
    public String searchBankDocumentTransferInfo() throws Exception {
        log.debug("# Begin method OrderManagementDAO.searchBankDocumentTransferInfo");
        pageForward = "saleBankManagmentResultOrder";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }
//        LinhNBV 20190627: Fix objectCode on VSA
        long objIdDivide = 19554069;//"divideBankDocument";
        long objIdApproveReject = 19649069;//"approveRejectBankDocument";
        DbProcessor db = new DbProcessor();
        if (db.checkRoleBankDocumentManagement(userToken.getLoginName(), objIdDivide)) {
            req.setAttribute("roleDivideBank", "TRUE");
            log.info("user: " + userToken.getLoginName() + ", have role diveBank");
        }
        if (db.checkRoleBankDocumentManagement(userToken.getLoginName(), objIdApproveReject)) {
            req.setAttribute("roleApproveRejectBank", "TRUE");
            log.info("user: " + userToken.getLoginName() + ", have role approve or reject bank...");
        }
//        List<String> lsRole = getRoleBankOrder(getSession(), userToken.getLoginName());
//        if (lsRole != null || lsRole.size() > 0) {
//            for (int i = 0; i < lsRole.size(); i++) {
//                if ("BR_DIRECTOR".equalsIgnoreCase(lsRole.get(i).toString())
//                        || "MV_FINANCE_MANAGER_IM".equalsIgnoreCase(lsRole.get(i).toString())) {
//                    req.setAttribute("roleBrOrderBank", "BR_DIVIDE_BANK_APPROVE");
//                    break;
//                }
//            }
//        }

        if (saleManagmentForm.getSSaleTransCode() == null || "".equalsIgnoreCase(saleManagmentForm.getSSaleTransCode())) {
            req.setAttribute("resultUpdateSale", "saleManagment.search.error.bankDocument.empty");
            return pageForward;
        }
        //LinhNBV start modified on May 21 2018: Validate from date & to date
        if (!saleManagmentForm.getSTransFromDate().isEmpty() && !saleManagmentForm.getSTransToDate().isEmpty()) {
            Date sTransFromDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransFromDate(), "yyyy-MM-dd");
            Date sTransToDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransToDate(), "yyyy-MM-dd");
            int distanceBeetwen2Date = DateTimeUtils.distanceBeetwen2Date(sTransFromDate, sTransToDate);
            if (distanceBeetwen2Date > 30) {
                req.setAttribute("resultUpdateSale", "saleManagment.search.error.over.distance");
                return pageForward;
            }
        }
        //LinhNBV end.
        try {
            req.getSession().removeAttribute("lstSaleTrans");
            String SQL_SELECT_TRANS = " from BankTranferInfo where 1= 1 ";
            List lstParam = new ArrayList();
            if (saleManagmentForm.getSSaleTransCode() != null && !saleManagmentForm.getSSaleTransCode().trim().equals("")) {
                SQL_SELECT_TRANS += " and lower(tranferCode) = ? ";
                lstParam.add(saleManagmentForm.getSSaleTransCode().trim().toLowerCase());
            }

            if (saleManagmentForm.getSTransFromDate() != null && !saleManagmentForm.getSTransFromDate().trim().equals("")) {
                Date fromDate;
                try {
                    fromDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransFromDate().trim());

                } catch (Exception ex) {
                    req.setAttribute("resultUpdateSale", "saleManagment.search.error.fromDate");
                    return pageForward;
                }
                SQL_SELECT_TRANS += " and createTime  >= ?";
                lstParam.add(fromDate);
            }
            if (saleManagmentForm.getSTransToDate() != null && !saleManagmentForm.getSTransToDate().trim().equals("")) {

                Date toDate;
                try {
                    String stoDate = saleManagmentForm.getSTransToDate().trim().substring(0, 10) + " 23:59:59";
                    toDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");

                } catch (Exception ex) {
                    return pageForward;
                }
                SQL_SELECT_TRANS += " and createTime  <= ?";
                lstParam.add(toDate);
            }
            SQL_SELECT_TRANS += " and status  in (1,4) and (check_time is null or ( check_time is not null and (sysdate-check_time>1) )or rollback_time>check_time)  ";
            SQL_SELECT_TRANS += " order by createTime desc ";
            Query q = getSession().createQuery(SQL_SELECT_TRANS);
            for (int idx = 0; idx < lstParam.size(); idx++) {
                q.setParameter(idx, lstParam.get(idx));
            }
            List lstSaleTrans = q.list();

            req.setAttribute("lstSaleTrans", lstSaleTrans);

        } catch (Exception ex) {
            req.setAttribute("resultUpdateSale", "saleManagment.search.error.undefine");
        }

        return pageForward;
    }

    //tannh20182508 search bank_transfer_info
    public String divideBankDocument() throws Exception {
        log.debug("# Begin method OrderManagementDAO.divideBankDocument");
        pageForward = "viewMess";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session smSession = getSession();
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }
        try {
            String s = saleManagmentForm.getBankChild();
            s = s.replaceAll(" ", "");
            String[] str = s.split(",");

            if (saleManagmentForm.getBankFather() == null || "".equalsIgnoreCase(saleManagmentForm.getBankFather())) {
                req.setAttribute("resultUpdateSale", "Bank father is empty!");
                return pageForward;
            }
            BankTranferInfo bf = findAvailableByTranferCode(Long.parseLong(saleManagmentForm.getBankFather()));
            if (bf == null) {
                req.setAttribute("resultUpdateSale", "Bank father is empty!");
                return pageForward;
            }

            BigDecimal totalAmount = new BigDecimal(0);
            for (int i = 0; i < str.length; i++) {
                if (Double.parseDouble(str[i]) <= 0 || Double.parseDouble(str[i]) >= Double.parseDouble(bf.getAmount())) {
                    req.setAttribute("resultUpdateSale", "Money bank's child must between 1 and " + bf.getAmount());
                    return pageForward;
                }
                totalAmount = totalAmount.add(new BigDecimal(str[i]));
            }

            if (new BigDecimal(bf.getAmount()).doubleValue() != totalAmount.doubleValue()) {
                req.setAttribute("resultUpdateSale", "Total all money bank'schild not equal money bank's father");
                return pageForward;
            }

            //update trang thai giay nop tien cha dang cho duyet thi khong duoc su dung theo yeu cau anh tuan anh tai chinh 
            bf.setStatus("4");

            for (int i = 0; i < str.length; i++) {
                String childTranferCode = bf.getTranferCode() + "-" + (i + 1);
                BankTranferInfoTemp b = new BankTranferInfoTemp(getSequence("seq_bank_tranfer_info"), bf.getCreateTime(), bf.getBankName(), childTranferCode.toUpperCase(), str[i], bf.getDescription(),
                        bf.getCreateUser(), "1", null, null, null, null, bf.getBankCreate(), bf.getAccountBank());
                b.setStaffRequestDivide(userToken.getLoginName());
                b.setRequestTimeDivide(new Date());
                b.setTranferCodeFather(bf.getTranferCode());
                smSession.save(b);
            }
            DbProcessor db = new DbProcessor();
            List<String> lstIsdn = getIsdnFinanceBank(userToken.getShopCode());
            for (String isdn : lstIsdn) {
                db.sendSms("258" + isdn, "User " + userToken.getLoginName() + " request divide bankslip :" + bf.getTranferCode(), "86904");
            }
            req.setAttribute("resultUpdateSale", "Divide Bank Document success!!!");
        } catch (Exception ex) {
            req.setAttribute("resultUpdateSale", "saleManagment.search.error.undefine" + ex.getMessage());
        }

        return pageForward;
    }

    public String acceptDetailBankDocument() throws Exception {
        log.debug("# Begin method OrderManagementDAO.acceptDetailBankDocument");
        pageForward = "saleBankManagmentResultOrder";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session smSession = getSession();
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }
        try {
            String tranferId = req.getParameter("tranferId");
            String tranferCode = req.getParameter("tranferCode");
            req.setAttribute("tranferId", tranferId);
            req.setAttribute("tranferCode", tranferCode);

            if (tranferCode == null || "".equalsIgnoreCase(tranferCode)) {
                req.setAttribute("resultUpdateSale", "Bank father is empty!");
                return pageForward;
            }
            BankTranferInfo bf = findWaitingByTranferCode(Long.parseLong(tranferId));
            if (bf == null) {
                req.setAttribute("resultUpdateSale", "Bank father is empty!");
                return pageForward;
            }
            // huy bank cha
            bf.setStatus("2");
            smSession.update(bf);
            //tao bank con
            List<BankTranferInfoTemp> str = getAllBankChildDocumentByBankFather(tranferCode);
            DbProcessor db = new DbProcessor();
            for (BankTranferInfoTemp t : str) {
                BankTranferInfo b = new BankTranferInfo(t.getBankTranferInfoId(), t.getCreateTime(), t.getBankName(), t.getTranferCode(),
                        t.getAmount(), t.getDescription(), t.getCreateUser(), t.getStatus(), t.getUpdateTime(), t.getUpdateUser(),
                        t.getCheckTime(), t.getCheckUser(), t.getBankCreate(), t.getAccountBank());
                smSession.save(b);
                // cap nhat lai bank con
                t.setStaffApproveDivide(userToken.getLoginName());
                t.setApproveTimeDivide(new Date());
                smSession.save(t);
                List<String> lstIsdn = getIsdnStaff(t.getStaffRequestDivide());
                for (String isdn : lstIsdn) {
                    db.sendSms("258" + isdn, "User " + userToken.getLoginName() + " approved request divide bank slip :" + t.getTranferCode(), "86904");
                }
            }

            req.setAttribute("resultUpdateSale", "Accept divide Bank Document success!!!");
        } catch (Exception ex) {
            req.setAttribute("resultUpdateSale", "saleManagment.search.error.undefine" + ex.getMessage());
        }

        return pageForward;
    }

    public String rejectDetailBankDocument() throws Exception {
        log.debug("# Begin method OrderManagementDAO.rejectDetailBankDocument");
        pageForward = "saleBankManagmentResultOrder";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session smSession = getSession();
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }
        try {
            String tranferId = req.getParameter("tranferId");
            String tranferCode = req.getParameter("tranferCode");
            req.setAttribute("tranferId", tranferId);
            req.setAttribute("tranferCode", tranferCode);

            if (tranferCode == null || "".equalsIgnoreCase(tranferCode)) {
                req.setAttribute("resultUpdateSale", "Bank father is empty!");
                return pageForward;
            }
            BankTranferInfo bf = findWaitingByTranferCode(Long.parseLong(tranferId));
            if (bf == null) {
                req.setAttribute("resultUpdateSale", "Bank father is empty!");
                return pageForward;
            }
            // cap nhat lai bank cha
            bf.setStatus("1");
            smSession.update(bf);
            //tao bank con
            List<BankTranferInfoTemp> str = getAllBankChildDocumentByBankFather(tranferCode);
            DbProcessor db = new DbProcessor();
            for (BankTranferInfoTemp t : str) {
                // reject 
                // cap nhat lai bank con
                t.setStatus("7");
                t.setStaffApproveDivide(userToken.getLoginName());
                t.setApproveTimeDivide(new Date());
                smSession.save(t);
                List<String> lstIsdn = getIsdnStaff(t.getStaffRequestDivide());
                for (String isdn : lstIsdn) {
                    db.sendSms("258" + isdn, "User " + userToken.getLoginName() + " reject request divide bank slip :" + t.getTranferCode(), "86904");
                }
            }
            req.setAttribute("resultUpdateSale", "Reject divide Bank Document success!!!");
        } catch (Exception ex) {
            req.setAttribute("resultUpdateSale", "saleManagment.search.error.undefine" + ex.getMessage());
        }

        return pageForward;
    }

    //tannh20182508 load bank_transfer_info
    public String loadBankTransferInfo() throws Exception {
        log.debug("# Begin method OrderManagementDAO.searchSaleTrans");
        pageForward = "saleManagmentResultOrder";

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }

        if (saleManagmentForm.getSSaleTransCode() == null || "".equalsIgnoreCase(saleManagmentForm.getSSaleTransCode())) {

            //LinhNBV start modified on May 21 2018: Validate from date & to date
            if (saleManagmentForm.getSTransFromDate().isEmpty() || saleManagmentForm.getSTransToDate().isEmpty()) {
                req.setAttribute("resultUpdateSale", "saleManagment.search.error.date.empty");
                return pageForward;
            }
            Date sTransFromDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransFromDate(), "yyyy-MM-dd");
            Date sTransToDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransToDate(), "yyyy-MM-dd");
            int distanceBeetwen2Date = DateTimeUtils.distanceBeetwen2Date(sTransFromDate, sTransToDate);
            if (distanceBeetwen2Date > 30) {
                req.setAttribute("resultUpdateSale", "saleManagment.search.error.over.distance");
                return pageForward;
            }
            //LinhNBV end.

        }
        try {
            req.getSession().removeAttribute("lstSaleTrans");
            String SQL_SELECT_TRANS = " from BankTranferInfo where 1= 1 ";
            List lstParam = new ArrayList();

            if (saleManagmentForm.getSTransFromDate() != null && !saleManagmentForm.getSTransFromDate().trim().equals("")) {
                Date fromDate;
                try {
                    fromDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransFromDate().trim());

                } catch (Exception ex) {
                    req.setAttribute("resultUpdateSale", "saleManagment.search.error.fromDate");
                    return pageForward;
                }
                SQL_SELECT_TRANS += " and createTime  >= ?";
                lstParam.add(fromDate);
            }
            if (saleManagmentForm.getSTransToDate() != null && !saleManagmentForm.getSTransToDate().trim().equals("")) {

                Date toDate;
                try {
                    String stoDate = saleManagmentForm.getSTransToDate().trim().substring(0, 10) + " 23:59:59";
                    toDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");

                } catch (Exception ex) {
                    return pageForward;
                }
                SQL_SELECT_TRANS += " and createTime  <= ?";
                lstParam.add(toDate);
            }
            SQL_SELECT_TRANS += " and status  = 1";
            SQL_SELECT_TRANS += " order by createTime desc ";
            Query q = getSession().createQuery(SQL_SELECT_TRANS);
            for (int idx = 0; idx < lstParam.size(); idx++) {
                q.setParameter(idx, lstParam.get(idx));
            }
            List lstSaleTrans = q.list();
            req.setAttribute("lstSaleTrans", lstSaleTrans);

        } catch (Exception ex) {
            req.setAttribute("resultUpdateSale", "saleManagment.search.error.undefine");
        }

        return pageForward;
    }

    //tannh20182508 search bank_transfer_info
    public String deleteBankTranfer() throws Exception {
        log.debug("# Begin method OrderManagementDAO.deleteBankTranfer");
        pageForward = "saleManagmentDetailOrder";

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }

        try {
            req.getSession().removeAttribute("lstSaleTrans");
            String tranferId = req.getParameter("tranferId");
            BankTranferInfo bf = findAvailableByTranferCode(Long.parseLong(tranferId));

            String SQL_SELECT_TRANS = " DELETE FROM BankTranferInfo where  ";
            List lstParam = new ArrayList();
            SQL_SELECT_TRANS += "  bankTranferInfoId = ? ";
            lstParam.add(Long.parseLong(tranferId));

            SQL_SELECT_TRANS += " and status  = 1";
            Query q = getSession().createQuery(SQL_SELECT_TRANS);
            for (int idx = 0; idx < lstParam.size(); idx++) {
                q.setParameter(idx, lstParam.get(idx));
            }
            int rs = q.executeUpdate();
            if (rs <= 0) {
                req.setAttribute("resultViewSaleDetail", "delete Bank Document No Unsuccess!!! Because funtion deleteBankTranfer error");
                return pageForward;
            }

            // thuc hien ghi Log  :
            lstLogObj.add(new ActionLogsObj(null, bf));
            saveLog("DELETE_BANKTRANFER_INFO", "Delete a BankTranferCode", lstLogObj, bf.getBankTranferInfoId(), Constant.CATALOGUE_OF_CHANNEL, Constant.DELETE);

            req.setAttribute("resultViewSaleDetail", "Delete success!!!");
        } catch (Exception ex) {
            req.setAttribute("resultViewSaleDetail", "saleManagment.search.error.undefine");
        }

        return pageForward;
    }

    //tannh20182508 cancel rch bank_transfer_info
    public String cancelBankTranfer() throws Exception {
        log.debug("# Begin method OrderManagementDAO.deleteBankTranfer");
        pageForward = "saleManagmentDetailOrder";

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }

        try {
            req.getSession().removeAttribute("lstSaleTrans");
            String tranferId = req.getParameter("tranferId");
            BankTranferInfo bf = findAvailableByTranferCode(Long.parseLong(tranferId));

            String SQL_SELECT_TRANS = " UPDATE BankTranferInfo SET status = 2, purpose= ? WHERE  status = 1 and bankTranferInfoId = ?     ";
            Query q = getSession().createQuery(SQL_SELECT_TRANS);
            String mucdich = "5";
            q.setParameter(0, mucdich);
            q.setParameter(1, Long.parseLong(tranferId));
            int rs = q.executeUpdate();
            if (rs <= 0) {
                req.setAttribute("resultViewSaleDetail", "Cancel Bank Document No Unsuccess!!! Because funtion cancelBankTranfer error");
                return pageForward;
            }

            // thuc hien ghi Log  :
            lstLogObj.add(new ActionLogsObj(null, bf));
            saveLog("CALCEL_BANKTRANFER_INFO", "Calcel a BankTranferCode", lstLogObj, bf.getBankTranferInfoId(), Constant.CATALOGUE_OF_CHANNEL, Constant.EDIT);

            req.setAttribute("resultViewSaleDetail", "Cancel Bank Document No success!!!");

        } catch (Exception ex) {
            req.setAttribute("resultViewSaleDetail", "saleManagment.search.error.undefine");
        }

        return pageForward;
    }

    public String revertPendingBankTranfer() throws Exception {
        log.debug("# Begin method OrderManagementDAO.revertPendingBankTranfer");
        pageForward = "saleManagmentDetailOrder";

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }
        try {
            req.getSession().removeAttribute("lstSaleTrans");
            String tranferId = req.getParameter("tranferId");


            DbProcessor db = new DbProcessor();
            int rs = db.revertBankDocumentPending(userToken.getLoginName().toUpperCase(), Long.parseLong(tranferId));
            if (rs <= 0) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.revertPending.error.01");
                return pageForward;
            }
            // thuc hien ghi Log  :
            BankTranferInfo bf = findAvailableByTranferCode(Long.parseLong(tranferId));
            lstLogObj.add(new ActionLogsObj(null, bf));
            saveLog("REVERT_BANKTRANFER_INFO", "Revert a BankTranferCode", lstLogObj, bf.getBankTranferInfoId(), Constant.CATALOGUE_OF_CHANNEL, Constant.EDIT);

            req.setAttribute("resultViewSaleDetail", "orderManagement.revertPending.error.02");

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultViewSaleDetail", "saleManagment.search.error.undefine");
        }

        return pageForward;
    }

    //tannh20182508 accept rch bank_transfer_info
    public String acceptBankTranfer() throws Exception {
        log.debug("# Begin method OrderManagementDAO.acceptBankTranfer");
        pageForward = "saleManagmentDetailOrder";

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }

        try {
            req.getSession().removeAttribute("lstSaleTrans");
            String tranferId = req.getParameter("tranferId");
            String rowNum = req.getParameter("rowNum");
            BankTranferInfo bf = findAvailableByTranferCode(Long.parseLong(tranferId));
            if (bf == null) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.05");
                return pageForward;
            }
            String saleTransId = req.getParameter("saleTransId" + rowNum).trim();
            if (saleTransId == null || saleTransId.isEmpty()) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.01");
                return pageForward;
            }
            String paymentId = req.getParameter("paymentId" + rowNum).trim();
            if (paymentId != null && paymentId.length() > 0) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.07");
                return pageForward;
            }
            SaleTransDAO std = new SaleTransDAO();
            SaleTrans st = std.findById(Long.parseLong(saleTransId));

            if (!checkExitsSaleTrans(req, userToken)) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.02");
                return pageForward;
            }
            Staff requestStaff = findAvailableByStaffId(st.getStaffId());
            int rs = updateAcceptBankTranferInfo(Long.parseLong(tranferId), userToken, requestStaff, Long.parseLong(saleTransId), "1");
            if (rs <= 0) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.03");
                return pageForward;
            }

            int rs1 = updateSaleTransForCleanByHand(req, userToken);
            if (rs1 <= 0) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.06");
                return pageForward;
            }
            // thuc hien ghi Log  :
            lstLogObj.add(new ActionLogsObj(null, bf));
            saveLog("CLEAR_DEBIT_BY_HAND", "CLEAR_DEBIT_BY_HAND", lstLogObj, bf.getBankTranferInfoId(), Constant.CATALOGUE_OF_CHANNEL, Constant.EDIT);

            req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.04");

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultViewSaleDetail", "saleManagment.search.error.undefine");
        }

        return pageForward;
    }

    public String clearPaymentTransaction() throws Exception {
        log.debug("# Begin method OrderManagementDAO.clearPaymentTransaction");
        pageForward = "saleManagmentDetailOrder";

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }

        try {
            req.getSession().removeAttribute("lstSaleTrans");
            String tranferId = req.getParameter("tranferId");
            String rowNum = req.getParameter("rowNum");
            BankTranferInfo bf = findAvailableByTranferCode(Long.parseLong(tranferId));
            if (bf == null) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.05");
                return pageForward;
            }
            String saleTransId = req.getParameter("saleTransId" + rowNum).trim();
            if (saleTransId != null && saleTransId.length() > 0) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.08");
                return pageForward;
            }
            String paymentId = req.getParameter("paymentId" + rowNum).trim();
            if (paymentId == null || paymentId.isEmpty()) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.09");
                return pageForward;
            }
            DbProcessor db = new DbProcessor();
            long collectionStaffId = db.getCollectionStaffId(Long.valueOf(paymentId));
            if (collectionStaffId <= 0) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.02");
                return pageForward;
            }
            Staff requestStaff = findAvailableByStaffId(collectionStaffId);
            int rs = updateAcceptBankTranferInfo(Long.parseLong(tranferId), userToken, requestStaff, Long.valueOf(paymentId), "2");
            if (rs <= 0) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.03");
                return pageForward;
            }
            int rs1 = db.updatePaymentForCleanByHand(Long.valueOf(paymentId));
            if (rs1 <= 0) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.06");
                return pageForward;
            }

            lstLogObj.add(new ActionLogsObj(null, bf));
            saveLog("CLEAR_PAYMENT_TRANS", "CLEAR_PAYMENT_TRANS", lstLogObj, bf.getBankTranferInfoId(), Constant.CATALOGUE_OF_CHANNEL, Constant.EDIT);

            req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.04");

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultViewSaleDetail", "saleManagment.search.error.undefine");
        }

        return pageForward;
    }

    public String clearDepositTransaction() throws Exception {
        log.debug("# Begin method OrderManagementDAO.clearPaymentTransaction");
        pageForward = "saleManagmentDetailOrder";

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }

        try {
            req.getSession().removeAttribute("lstSaleTrans");
            String tranferId = req.getParameter("tranferId");
            String rowNum = req.getParameter("rowNum");
            BankTranferInfo bf = findAvailableByTranferCode(Long.parseLong(tranferId));
            if (bf == null) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.05");
                return pageForward;
            }
            String depositId = req.getParameter("depositId" + rowNum).trim();
            if (depositId == null || depositId.isEmpty()) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.10");
                return pageForward;
            }
            //validate saleTransId, paymentId, saleFloatId ???
            DepositDAO depositDAO = new DepositDAO();
            Deposit deposit = depositDAO.findById(Long.valueOf(depositId));
            if (deposit == null) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.11");
                return pageForward;
            }
            DbProcessor db = new DbProcessor();
            Staff requestStaff = findAvailableByStaffId(deposit.getStaffId());
            int rs = updateAcceptBankTranferInfo(Long.parseLong(tranferId), userToken, requestStaff, Long.valueOf(depositId), "7");
            if (rs <= 0) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.03");
                return pageForward;
            }
            int rs1 = db.updateDepositForCleanByHand(Long.valueOf(depositId), tranferId);
            if (rs1 <= 0) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.06");
                return pageForward;
            }

            lstLogObj.add(new ActionLogsObj(null, bf));
            saveLog("CLEAR_DEPOSIT_TRANS", "CLEAR_DEPOSIT_TRANS", lstLogObj, bf.getBankTranferInfoId(), Constant.CATALOGUE_OF_CHANNEL, Constant.EDIT);

            req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.04");

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultViewSaleDetail", "saleManagment.search.error.undefine");
        }

        return pageForward;
    }

    public String clearSaleFloatTransaction() throws Exception {
        log.debug("# Begin method OrderManagementDAO.clearPaymentTransaction");
        pageForward = "saleManagmentDetailOrder";

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
            return pageForward;
        }
        try {
            req.getSession().removeAttribute("lstSaleTrans");
            String tranferId = req.getParameter("tranferId");
            String rowNum = req.getParameter("rowNum");
            BankTranferInfo bf = findAvailableByTranferCode(Long.parseLong(tranferId));
            if (bf == null) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.05");
                return pageForward;
            }
            String saleFloatId = req.getParameter("saleFloatId" + rowNum).trim();
            if (saleFloatId == null || saleFloatId.isEmpty()) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.12");
                return pageForward;
            }
            //validate another transactionId... ???
            DbProcessor db = new DbProcessor();
            long staffId = db.getSaleFloatStaffId(Long.valueOf(saleFloatId));
            if (staffId <= 0) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.02");
                return pageForward;
            }
            Staff requestStaff = findAvailableByStaffId(staffId);
            int rs = updateAcceptBankTranferInfo(Long.parseLong(tranferId), userToken, requestStaff, Long.valueOf(saleFloatId), "3");
            if (rs <= 0) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.03");
                return pageForward;
            }
            int rs1 = db.updateSaleFloatForCleanByHand(Long.valueOf(saleFloatId), tranferId);
            if (rs1 <= 0) {
                req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.06");
                return pageForward;
            }

            lstLogObj.add(new ActionLogsObj(null, bf));
            saveLog("CLEAR_SALE_FLOAT_TRANS", "CLEAR_SALE_FLOAT_TRANS", lstLogObj, bf.getBankTranferInfoId(), Constant.CATALOGUE_OF_CHANNEL, Constant.EDIT);

            req.setAttribute("resultViewSaleDetail", "orderManagement.clearDebit.error.04");

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultViewSaleDetail", "saleManagment.search.error.undefine");
        }

        return pageForward;
    }

    public int updateAcceptBankTranferInfo(Long tranferId, UserToken userToken, Staff staff, Long transId, String purpose) {
        int rs = 0;
        try {
            String SQL_SELECT_TRANS = "UPDATE Bank_Tranfer_Info SET status = 0, purpose= ? ,update_Time = sysdate ,update_User= ?,check_Time= sysdate ,check_User= ?,staff_Request=?,staff_Approve= ?,sale_trans_id =? WHERE  status = 1 and bank_Tranfer_Info_Id = ?   ";
            Query q = getSession().createSQLQuery(SQL_SELECT_TRANS);
            q.setParameter(0, purpose);
            q.setParameter(1, userToken.getLoginName());
            q.setParameter(2, userToken.getLoginName());
            if (staff != null) {
                q.setParameter(3, staff.getStaffCode());
            } else {
                q.setParameter(3, userToken.getLoginName());
            }

            q.setParameter(4, userToken.getLoginName());
            q.setParameter(5, transId);
            q.setParameter(6, tranferId);
            rs = q.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
        return rs;
    }

    public int updateSaleTransForCleanByHand(HttpServletRequest req, UserToken userToken) {
        int rs = 0;
        try {
            String SQL_SELECT_TRANS = " update sale_trans set clear_debit_status=1, clear_debit_time=sysdate, clear_debit_user= ?, clear_debit_request_id=0 \n"
                    + " where sale_trans_date > to_date('17-07-2018 00:00:01','DD-MM-YYYY HH24:MI:SS')\n"
                    + " and sale_trans_id = ? and clear_debit_status is null  ";
            Query q = getSession().createSQLQuery(SQL_SELECT_TRANS);
            String rowNum = req.getParameter("rowNum");
            String saleTransId = req.getParameter("saleTransId" + rowNum);
            q.setParameter(0, userToken.getLoginName());
            q.setParameter(1, saleTransId);
            rs = q.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
        return rs;
    }

//    public int updatePaymentForCleanByHand(HttpServletRequest req, UserToken userToken) {
//        int rs = 0;
//        try {
//            String SQL_SELECT_TRANS = " update payment_contract a set a.clear_debit_status=1, a.clear_debit_time=sysdate, a.clear_debit_user='IT', a.clear_debit_request_id=0 \n"
//                    + "where a.create_date > to_date('17-07-2018 11:07:02','DD-MM-YYYY HH24:MI:SS')\n"
//                    + "and a.collection_staff_id = (select collection_staff_id from collection_staff where collection_staff_code = ?) and a.payment_id =? \n"
//                    + "and a.clear_debit_status is null\n"
//                    + "and not exists (select * from payment.payment_bank_slip where payment_id = a.payment_id and status = 3)  ";
//            Query q = getSession().createSQLQuery(SQL_SELECT_TRANS);
//            String rowNum = req.getParameter("rowNum");
//            String saleTransId = req.getParameter("saleTransId" + rowNum);
//            q.setParameter(0, userToken.getLoginName());
//            q.setParameter(1, saleTransId);
//            rs = q.executeUpdate();
//        } catch (HibernateException ex) {
//            System.out.println(ex.getMessage());
//            return 0;
//        }
//        return rs;
//    }
    public boolean checkExitsSaleTrans(HttpServletRequest req, UserToken userToken) {
        String rowNum = req.getParameter("rowNum");
        String saleTransId = req.getParameter("saleTransId" + rowNum).trim();
        if (saleTransId == null || "".equalsIgnoreCase(saleTransId)) {
            return false;
        }

        try {
            String SQL_SELECT_TRANS = "select * from sale_trans where sale_trans_date > to_date('17-07-2018 00:00:01','DD-MM-YYYY HH24:MI:SS')  and sale_trans_id = ? ";
            Query q = getSession().createSQLQuery(SQL_SELECT_TRANS);
            q.setParameter(0, Long.parseLong(saleTransId));
            List lst = q.list();
            if (lst != null && lst.size() > 0) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public String createOrderBankByFile() throws Exception {
        log.info("begin method createOrderBankByFile of OrderManagementDao ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session smSession = getSession();
        Workbook workbook = null;
        String pageForward = "saleManagmentResultOrder";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String[] output = saleManagmentForm.getFileUrl().split(",");
            String fileName = output[0];

            workbook = Workbook.getWorkbook(new File(fileName));

            Sheet sheet = workbook.getSheet(0);

            int totalNoOfRows = sheet.getRows();
            List<BankTranferInfo> ls = new ArrayList<BankTranferInfo>();
            List<String> lsString = new ArrayList<String>();
            List<String> transCodeArray = new ArrayList<String>();
            DbProcessor db = new DbProcessor();
            for (int i = 1; i < totalNoOfRows; i++) {
                Cell cell1 = sheet.getCell(0, i);
                Cell cell2 = sheet.getCell(1, i);
                if (transCodeArray.contains(cell2.getContents().toString())) {
                    continue;
                }

                transCodeArray.add(cell2.getContents().toString());
                Cell cell3 = sheet.getCell(2, i);
                String amount;
                try {
                    amount = cell3.getContents().toString().replaceAll(",", "");
                } catch (Exception x) {
                    smSession.getTransaction().rollback();
                    smSession.clear();
                    req.setAttribute("resultUpdateSale", "Amuont isn't a number!!! ");
                    smSession.getTransaction().rollback();
                    smSession.clear();
                    return pageForward;
                }
                Cell cell4 = sheet.getCell(3, i);
                Cell cell5 = sheet.getCell(4, i);
                String bankCreate = cell5.getContents().toString();
                Cell cell6 = sheet.getCell(5, i);
                String accountBank = cell6.getContents().toString();
                Date bankCreateDate = formatter.parse(bankCreate);
                if (!db.checkBankAccountImport(cell1.getContents().trim(), accountBank.trim())) {
                    smSession.getTransaction().rollback();
                    smSession.clear();
                    req.setAttribute("resultUpdateSale", "Bank and account is not map.");
                    return pageForward;
                }

                if ("BIM".equalsIgnoreCase(cell1.getContents().toUpperCase()) && (cell2.getContents() == null || "".equalsIgnoreCase(cell2.getContents().toString()))) {
                    if (cell4.getContents().toString() == null || "".equalsIgnoreCase(cell4.getContents().toString())
                            || cell4.getContents().toString().length() < 15) {
                        req.setAttribute("resultUpdateSale", "Bank Name BIM have infomation incorrect! ");
                        smSession.getTransaction().rollback();
                        smSession.clear();
                        return pageForward;
                    }
                    String transCode = cell4.getContents().toString().substring(cell4.getContents().toString().length() - 15, cell4.getContents().toString().length());;
//                    if (!transCode.startsWith("180")) {
//                        transCode = null;
//                    };
                    if (transCode != null && checkAlreadlyHaveBankTrans(cell1.getContents().toUpperCase(), transCode)) {
                        lsString.add(transCode);
                    } else {
                        BankTranferInfo b = new BankTranferInfo(getSequence("seq_bank_tranfer_info"), new Date(), cell1.getContents().trim(), transCode.trim(), amount.trim(), cell4.getContents().trim(),
                                userToken.getLoginName(), "1", null, null, null, null, bankCreateDate, accountBank.trim());
                        smSession.save(b);
                    }
                } else {
                    if (checkAlreadlyHaveBankTrans(cell1.getContents().toUpperCase(), cell2.getContents().toUpperCase())) {
                        lsString.add(cell2.getContents().toUpperCase());
                    } else {
                        BankTranferInfo b = new BankTranferInfo(getSequence("seq_bank_tranfer_info"), new Date(), cell1.getContents().trim(), cell2.getContents().trim().toUpperCase(), amount.trim(), cell4.getContents().trim(),
                                userToken.getLoginName(), "1", null, null, null, null, bankCreateDate, accountBank.trim());
                        smSession.save(b);
                    }
                }

            }

            smSession.getTransaction().commit();
            smSession.flush();
            smSession.beginTransaction();

            req.setAttribute("resultUpdateSale", "Create by file successful!!! ");
            if (lsString != null && lsString.size() > 0) {
                String s = "";
                for (int k = 0; k < lsString.size(); k++) {
                    s = s + " ; " + lsString.get(k).toString();
                }
                req.setAttribute("resultUpdateSale", "BankName map Trans code:" + s + "  is exist !!! ");
                smSession.getTransaction().rollback();
                smSession.clear();
                return pageForward;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            smSession.getTransaction().rollback();
            smSession.clear();
            req.setAttribute("resultUpdateSale", "Have error (Exception) ");
            return pageForward;
        } finally {

            if (workbook != null) {
                workbook.close();
            }

        }
        return pageForward;
    }

    public String viewTransDetail() throws Exception {
        log.debug("# Begin method SaleManagmentDAO.viewTransDetail");
        pageForward = "saleManagmentDetailOrder";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        try {
            String trans = req.getParameter("saleTransOrderId");
            if (trans == null || trans.trim().equals("")) {
                req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.noTransId");
                return pageForward;
            }
            Long saleTransOrderId = Long.parseLong(trans);

            SaleTransOrderDAO saleTransOrderDAO = new SaleTransOrderDAO();
            saleTransOrderDAO.setSession(this.getSession());
            SaleTransOrder saleTrans = saleTransOrderDAO.findBySaleTransOrderId(saleTransOrderId);
            Long saleTransId = 0L;
            String custName = "";
            long createStaffId = 0;
            long receiverId = 0;
            String agentReceiverId = "";
            String orderFrom = "";
            if (saleTrans == null) {
                AgentTransOrderHis agentTransOrderHis = saleTransOrderDAO.findAgentOrderBySaleTransOrderId(saleTransOrderId);
                if (agentTransOrderHis == null) {
                    req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.noTransId");
                    return pageForward;
                } else {
                    agentReceiverId = agentTransOrderHis.getInTransId();
                    saleTransId = agentTransOrderHis.getSaleTransId();
                    custName = agentTransOrderHis.getCustName();
                    createStaffId = agentTransOrderHis.getStaffId();
                    if (agentTransOrderHis.getReceiverId() != null) {
                        receiverId = agentTransOrderHis.getReceiverId();
                    }
                    orderFrom = agentTransOrderHis.getOrderFrom();
                    saleManagmentForm.copyFromAgentTransOrderHis(agentTransOrderHis);
                }

            } else {
                agentReceiverId = saleTrans.getInTransId();
                saleTransId = saleTrans.getSaleTransId();
                custName = saleTrans.getCustName();
                createStaffId = saleTrans.getStaffId();
                if (saleTrans.getReceiverId() != null) {
                    receiverId = saleTrans.getReceiverId();
                }
                orderFrom = saleTrans.getOrderFrom();
                saleManagmentForm.copyFromSaleTransOrder(saleTrans);
            }



            //Tim danh sach mat hang trong GD
            String SQL_SELECT_TRANS_DETAIL = " from SaleTransDetailOrder where saleTransOrderId = ? ";
            Query q = getSession().createQuery(SQL_SELECT_TRANS_DETAIL);
            q.setParameter(0, saleTransOrderId);
            List lstSaleTransDetail = q.list();
            if (lstSaleTransDetail.size() > 0) {
                //Format hien thi so luong, don gia, thanh tien
                for (int i = 0; i < lstSaleTransDetail.size(); i++) {
                    SaleTransDetailOrder std = (SaleTransDetailOrder) lstSaleTransDetail.get(i);
                    if (std == null) {
                        continue;
                    }
                    std.setQuantityMoney(NumberUtils.formatNumber(std.getQuantity()));
                    std.setPriceMoney(NumberUtils.rounđAndFormatAmount(std.getPrice()));
                    std.setAmountMoney(NumberUtils.rounđAndFormatAmount(std.getPrice() * std.getQuantity()));

                }
                req.setAttribute("lstSaleTransDetail", lstSaleTransDetail);
            } else {
                req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.noSaleDetail");
            }

            if (saleTransId != null && saleTransId > 0 && orderFrom != null && !orderFrom.isEmpty() && "mBCCS".equals(orderFrom)) {

                // Lay ra danh sach de in ra file excel
                String SQL_SELECT_TRANS_DETAIL_REAL = " from SaleTransDetail where saleTransId = ? ";
                Query qReal = getSession().createQuery(SQL_SELECT_TRANS_DETAIL_REAL);
                qReal.setParameter(0, saleTransId);
                List lstSaleTransDetailReal = qReal.list();
                List<SaleTransDetail> lsFull = lstSaleTransDetailReal;
                if (lstSaleTransDetailReal.size() > 0) {
                    //Format hien thi so luong, don gia, thanh tien
                    for (int i = 0; i < lstSaleTransDetailReal.size(); i++) {
                        SaleTransDetail std = (SaleTransDetail) lstSaleTransDetailReal.get(i);
                        if (std == null) {
                            continue;
                        }
                        Long saleTransDetailId = std.getSaleTransDetailId();
                        String SQL_SELECT_TRANS_SERIAL_REAL = " from SaleTransSerial where saleTransDetailId = ? ";
                        Query qSerialReal = getSession().createQuery(SQL_SELECT_TRANS_SERIAL_REAL);
                        qSerialReal.setParameter(0, saleTransDetailId);
                        List lstSaleTransSerialReal = qSerialReal.list();
                        List<SaleTransSerial> lsFullSerial = lstSaleTransSerialReal;
                        lsFull.get(i).setLsSaleTransSerial(lsFullSerial);
                    }
                    req.setAttribute("lstSaleTransSerial", lsFull);
                }


                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                Staff staff = staffDAO.findByStaffId(createStaffId);
                if (staff == null) {
                    req.setAttribute("resultViewSaleDetail", "ERR.SAE.087");
                    return pageForward;
                }
                if (receiverId > 0) {
                    Staff receiver = staffDAO.findByStaffId(receiverId);
                    if (receiver == null) {
                        req.setAttribute("resultViewSaleDetail", "ERR.SAE.087");
                        return pageForward;
                    }
                    String staffCode = staff.getStaffCode();
                    // Lay ra danh sach de in ra file excel
                    String SQL_SELECT_AGENT_ORDER_INFO = "  from RoleTrueChief1 where  agentCode =? and agentOwnerCode = ?  ";
                    Query qagent = getSession().createQuery(SQL_SELECT_AGENT_ORDER_INFO);
                    qagent.setParameter(0, receiver.getStaffCode());
                    qagent.setParameter(1, staffCode);
                    List lstagent = qagent.list();
                    // Get receiver name...
                    DbProcessor db = new DbProcessor();
                    String receiverName = db.getReceiverName(agentReceiverId);
                    String[] arrReceiverInfo = receiverName.split("\\|");
                    RoleTrueChief1 lsAgent = new RoleTrueChief1();
                    if (lstagent != null && !lstagent.isEmpty()) {
                        lsAgent = (RoleTrueChief1) lstagent.get(0);
                        if (arrReceiverInfo.length == 3) {
                            lsAgent.setReceiverNameLv2(arrReceiverInfo[0]);
                            lsAgent.setBiStockAgent(arrReceiverInfo[1]);
                            lsAgent.setPhoneStockoutAgent(arrReceiverInfo[2]);
                        }

                    }
                    // tao file bao cao excel
                    String imageUrlForm02 = expOrderToExcel(lsFull, lsAgent);

                    saleManagmentForm.setImageUrlForm02(imageUrlForm02);
                }

            }
            //boolean check = AuthenticateDAO.checkAuthorityRoles("finance", req);
            DbProcessor db = new DbProcessor();

            boolean check = db.isStaffFinance(userToken.getLoginName());
            if (check) {
                //Role for Finance... --> approve and cancel transaction
                saleManagmentForm.setRole(1L);
            } else {
                //Role for Sale... --> create transaction
                saleManagmentForm.setRole(0L);
            }

        } catch (Exception ex) {
            req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.noSaleDetail");
        }
        return pageForward;
    }

    //Ham xuat ra excel
    public String expOrderToExcel(List<SaleTransDetail> lstGoods, RoleTrueChief1 agent) throws Exception {
        log.debug("# Begin method expOrderToExcel");
        HttpServletRequest req = this.getRequest();
        String pathFilePDF = null;
        if (lstGoods != null) {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            SimpleDateFormat hoursFormat = new SimpleDateFormat("yyyyMMddhh24mmss");
            String dateTime = hoursFormat.format(new Date());

            String templatePath = ResourceBundleUtils.getResource("TEMPLATE_PATH") + "Form_02.xls";
            String fileName = "Form_02_" + userToken.getLoginName().toLowerCase() + "_" + dateTime;
            String fileType = ".xls";
            String fullFileName = fileName + fileType;
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE") + fullFileName;

            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            String realPath = req.getSession().getServletContext().getRealPath(filePath);

            Map beans = new HashMap();
            beans.put("list", lstGoods);
            log.info("List good will be print in file excel file: " + lstGoods.size());
            beans.put("userToken", userToken);
            beans.put("agent", agent);
            Date date = new Date();
            beans.put("sysDate", new SimpleDateFormat("dd-MM-yyyy").format(date));
            double sum = 0;
            for (int i = 0; i < lstGoods.size(); i++) {
                sum += lstGoods.get(i).getQuantity();
            }
            beans.put("sum", sum);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
//            with file excel
            pathFilePDF = realPath;

        }
        log.debug("# End method expOrderToExcel");
        return pathFilePDF;
    }

    public String viewTransDetailSerial() throws Exception {
        log.debug("# Begin method SaleManagmentDAO.viewTransDetailSerial");
        pageForward = "saleManagmentDetailSerial";
        HttpServletRequest req = getRequest();
        try {
            String trans = req.getParameter("saleTransDetailId");
            String stockModelId = req.getParameter("stockModelId");

            if (trans == null || trans.trim().equals("")) {
                req.setAttribute("resultViewSaleDetailSerial", "saleManagment.viewDetailSerial.error.noTransDetailId");
                return pageForward;
            }
            Long saleTransDetailId = Long.parseLong(trans);
            String SQL_SELECT_TRANS_mBCCS = " select order_From from sale_Trans_Order where sale_Trans_Order_Id = ( select sale_Trans_Order_Id from sale_Trans_Detail_Order  where sale_Trans_Detail_Id = ?) and order_From =  'mBCCS' ";
            Query q1 = getSession().createSQLQuery(SQL_SELECT_TRANS_mBCCS);
            q1.setParameter(0, saleTransDetailId);
            List lstSaleTransDetailSerial1 = q1.list();
            if (lstSaleTransDetailSerial1.size() > 0) {
                String SQL_SELECT_TRANS_DETAIL = "  select from_Serial as fromSerial,to_Serial as toSerial, quantity as quantity from sale_Trans_Serial where sale_Trans_Detail_Id = (select sale_Trans_Detail_Id from sale_Trans_Detail where sale_Trans_Id = (select sale_Trans_Id from  sale_Trans_Order where sale_Trans_Order_Id = ( select sale_Trans_Order_Id from sale_Trans_Detail_Order  where sale_Trans_Detail_Id = ?))  and stock_Model_Id =? ) ";
                Query q = getSession().createSQLQuery(SQL_SELECT_TRANS_DETAIL).addScalar("fromSerial", Hibernate.STRING).addScalar("toSerial", Hibernate.STRING).addScalar("quantity", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(SaleTransSerial.class));;
                q.setParameter(0, saleTransDetailId);
                q.setParameter(1, stockModelId);
                List lstSaleTransDetailSerial = q.list();
                if (lstSaleTransDetailSerial.size() > 0) {
                    req.setAttribute("lstSaleTransDetailSerial", lstSaleTransDetailSerial);
                } else {
                    req.setAttribute("resultViewSaleDetailSerial", "saleManagment.viewDetailSerial.error.noSaleDetail");
                }
            } else {
                String SQL_SELECT_TRANS_DETAIL = " from SaleTransSerialOrder where saleTransDetailId = ? ";
                Query q = getSession().createQuery(SQL_SELECT_TRANS_DETAIL);
                q.setParameter(0, saleTransDetailId);
                List lstSaleTransDetailSerial = q.list();
                if (lstSaleTransDetailSerial.size() > 0) {
                    req.setAttribute("lstSaleTransDetailSerial", lstSaleTransDetailSerial);
                } else {
                    req.setAttribute("resultViewSaleDetailSerial", "saleManagment.viewDetailSerial.error.noSaleDetail");
                }

            }

        } catch (Exception ex) {
            req.setAttribute("resultViewSaleDetailSerial", "saleManagment.viewDetail.error.undefine");
        }
        return pageForward;
    }

    public String createTrans() throws Exception {

        pageForward = "saleManagmentDetailOrder";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String saleTransCode = saleManagmentForm.getSaleTransCode();
            if (saleTransCode == null || saleTransCode.equals("")) {
                req.setAttribute("resultViewSaleDetail", "saleManagment.cancel.error.noSaleTransCode");
                return pageForward;
            }

            SaleTransOrderDAO saleTransOrderDAO = new SaleTransOrderDAO();
            saleTransOrderDAO.setSession(this.getSession());
            SaleTransOrder saleTransOrder = saleTransOrderDAO.findBySaleTransCode(saleTransCode);
            if (saleTransOrder == null) {
                req.setAttribute("resultViewSaleDetail", "saleManagment.cancel.error.saleTransNotExits");
                return pageForward;
            }

            /*check da nhap serial hay chua*/
            if (!checkAlreadlyHaveSerial(saleTransCode)) {
                req.setAttribute("resultViewSaleDetail", "ERR.SAE.098");
                return pageForward;
            }

            //Tim danh sach mat hang trong GD
            //LinhNBV start modified on May 22 2018: change saleTransId -> saleTransOrderId
            String SQL_SELECT_TRANS_DETAIL = " from SaleTransDetailOrder where saleTransOrderId = ? ";
            Query q = getSession().createQuery(SQL_SELECT_TRANS_DETAIL);
            q.setParameter(0, saleTransOrder.getSaleTransOrderId());
            List<SaleTransDetailOrder> lstSaleTransDetail = q.list();

            if (Constant.SALE_TRANS_TYPE_RETAIL.equals(saleTransOrder.getSaleTransType())) {
                //Nghiep vu ban le
                String result = this.checkSalePackageGoods(lstSaleTransDetail);
                if (!result.equals("")) {
                    String[] arr = result.split("@");
                    if (arr.length < 2) {
                        req.setAttribute("resultViewSaleDetail", "E.100028");
                    } else if (arr.length == 2) {
                        req.setAttribute("resultViewSaleDetail", "E.100036");
                    } else {
                        req.setAttribute("resultViewSaleDetail", "E.100035");
                        List listParamValue = new ArrayList();
                        listParamValue.add(arr[0]);
                        listParamValue.add(arr[1]);
                        listParamValue.add(arr[2]);
                        req.setAttribute("resultViewSaleDetail", listParamValue);
                    }
                    return pageForward;
                }
                /*ket thuc check ban bo hang hoa theo cach cu cua mzb*/

                /*luu giao dich ban hang*/
                SaleTrans saleTrans = this.saveSaleTransRetail(saleTransOrder, lstSaleTransDetail);
                log.info("SaleTransToRetail: " + "CustName: " + saleTransOrder.getCustName() + "saveSaleTrans");
                if (saleTrans == null || saleTrans.getSaleTransId() == null) {
                    log.info("saveSaleTrans is fail.");
                    req.setAttribute("resultViewSaleDetail", "Error. Can not save sale transaction!");
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().getTransaction().begin();
                    return pageForward;
                }
                //LinhNBV start modified on May 22 2018: update SaleTransId to SaleTransOrderId
                saleTransOrder.setSaleTransId(saleTrans.getSaleTransId());
                //end.

                for (SaleTransDetailOrder saleTransDetailOrder : lstSaleTransDetail) {
                    log.info("SaleTransToRetail: saveSaleTransDetail ");
                    SaleTransDetail saleTransDetail = this.saveSaleTransDetailRetail(saleTrans, saleTransDetailOrder);
                    if (saleTransDetail == null || saleTransDetail.getSaleTransId() == null) {
                        log.info("SaleTransToRetail: saveSaleTransDetail fail. saletransId" + saleTrans.getSaleTransId());
                        req.setAttribute("resultViewSaleDetail", "Error. Can not save sale transaction detail!");
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();
                        return pageForward;
                    }

                    String SQL_SELECT_TRANS_SERIAL = " from SaleTransSerialOrder where saleTransDetailId = ? ";
                    Query query = getSession().createQuery(SQL_SELECT_TRANS_SERIAL);
                    query.setParameter(0, saleTransDetailOrder.getSaleTransDetailId());
                    List<SaleTransSerialOrder> lstSaleTransSerialOrder = query.list();

                    List<StockTransSerial> lstSerial = new ArrayList<StockTransSerial>();

                    if (lstSaleTransSerialOrder != null && !lstSaleTransSerialOrder.isEmpty()) {
                        for (int i = 0; i < lstSaleTransSerialOrder.size(); i++) {
                            StockTransSerial stockTransSerial = new StockTransSerial();
                            stockTransSerial.setStockTransSerialId(1L);
                            stockTransSerial.setFromSerial(lstSaleTransSerialOrder.get(i).getFromSerial());
                            stockTransSerial.setToSerial(lstSaleTransSerialOrder.get(i).getToSerial());
                            BigInteger startSerialTemp = new BigInteger(lstSaleTransSerialOrder.get(i).getFromSerial());
                            BigInteger endSerialTemp = new BigInteger(lstSaleTransSerialOrder.get(i).getToSerial());
                            Long quantity = endSerialTemp.subtract(startSerialTemp).longValue() + 1;
                            stockTransSerial.setQuantity(quantity);
                            lstSerial.add(stockTransSerial);
                        }
                    }
                    boolean saveSaleTransSerialInRetail = this.saveSaleTransSerialRetail(saleTransDetail, lstSerial);
                    if (!saveSaleTransSerialInRetail) {
                        log.info("SaleTransToRetail: saveSaleTransSerialOrder fail. saletransId" + saleTrans.getSaleTransId());
                        req.setAttribute("resultViewSaleDetail", "Error. Can not save serial list of sale transaction!");
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();
                        return pageForward;
                    }

                }
                //tinh tong gia tri hang hoa trong giao dich ban hang
                Double saleTransAmount = sumAmountByGoodsListRetail(lstSaleTransDetail);
                if (saleTransAmount != null && saleTransAmount >= 0) {
                    //Cap nhat lai gia tri hang hoa cua nhan vien
                    if (!addCurrentDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, -1 * saleTransAmount)) {
                        req.setAttribute("resultViewSaleDetail", getText("ERR.LIMIT.0001"));
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();
                        return pageForward;
                    }
                } else {
                    req.setAttribute("resultViewSaleDetail", getText("ERR.LIMIT.0014"));
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().getTransaction().begin();
                    return pageForward;
                }

            } else if (Constant.SALE_TRANS_TYPE_COLLABORATOR.equals(saleTransOrder.getSaleTransType())) {
                //Nghiep vu ban cho kenh
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                Staff staff = staffDAO.findByStaffId(saleTransOrder.getReceiverId());
                if (staff == null) {
                    req.setAttribute("resultViewSaleDetail", "ERR.SAE.087");
                    return pageForward;
                }

                //save giao dich ban hang
                SaleTrans saleTrans = expSaleTrans(userToken, staff, saleTransOrder);
                log.info("SaleToCollaborator: " + "CustName: " + saleTransOrder.getCustName() + "saveSaleTrans");
                if (saleTrans == null) {
                    log.info("expSaleTrans is fail.");
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    //save giao dich ban hang that bai
                    req.setAttribute("resultViewSaleDetail", "ERR.SAE.097");
                    log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                    return pageForward;
                }
                //LinhNBV start modified on May 22 2018: update SaleTransId to SaleTransOrderId
                saleTransOrder.setSaleTransId(saleTrans.getSaleTransId());
                //end.
                //save thong tin chi tiet ve giao dich, saleTransDetail + saleTransSerial
                log.info("SaleToCollaborator: saveSaleTransDetail ");
                for (SaleTransDetailOrder saleTransDetailOrder : lstSaleTransDetail) {
                    SaleTransDetail saleTransDetail = expSaleTransDetail(userToken, saleTrans, saleTransDetailOrder);
                    if (saleTransDetail == null) {
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        req.setAttribute("resultViewSaleDetail", "ERR.SAE.099");
                        log.info("SaleToCollaborator: saveSaleTransDetail fail. saletransId" + saleTrans.getSaleTransId());
                        log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                        return pageForward;
                    }

                    String SQL_SELECT_TRANS_SERIAL = " from SaleTransSerialOrder where saleTransDetailId = ? ";
                    Query query = getSession().createQuery(SQL_SELECT_TRANS_SERIAL);
                    query.setParameter(0, saleTransDetailOrder.getSaleTransDetailId());
                    List<SaleTransSerialOrder> lstSaleTransSerialOrder = query.list();
                    List<StockTransSerial> lstSerial = new ArrayList<StockTransSerial>();

                    if (lstSaleTransSerialOrder != null && !lstSaleTransSerialOrder.isEmpty()) {
                        for (int i = 0; i < lstSaleTransSerialOrder.size(); i++) {
                            StockTransSerial stockTransSerial = new StockTransSerial();
                            stockTransSerial.setStockTransSerialId(1L);
                            stockTransSerial.setFromSerial(lstSaleTransSerialOrder.get(i).getFromSerial());
                            stockTransSerial.setToSerial(lstSaleTransSerialOrder.get(i).getToSerial());
                            BigInteger startSerialTemp = new BigInteger(lstSaleTransSerialOrder.get(i).getFromSerial());
                            BigInteger endSerialTemp = new BigInteger(lstSaleTransSerialOrder.get(i).getToSerial());
                            Long quantity = endSerialTemp.subtract(startSerialTemp).longValue() + 1;
                            stockTransSerial.setQuantity(quantity);
                            lstSerial.add(stockTransSerial);
                        }

                    }

                    boolean saveSaleTransSerialInCollaborator = this.saveSaleTransSerial(userToken, staff, saleTransDetail, lstSerial);
                    if (!saveSaleTransSerialInCollaborator) {
                        log.info("SaleTransToRetail: saveSaleTransSerialOrder fail. saletransId" + saleTrans.getSaleTransId());
                        req.setAttribute("resultViewSaleDetail", "Error. Can not save serial list of sale transaction!");
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();
                        return pageForward;
                    }

                }

                log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");

            }
            //Cap nhat lai trang thai trong bang sale_trans_order --> da tao giao dich -- 3
            // DbProcessor db = new DbProcessor();
            // db.updateStateSaleTransOrder(saleTransCode, userToken.getUserID());
//            saleTransOrderDAO.setSession(this.getSession());
//            SaleTransOrder newSaleTransOrder = saleTransOrderDAO.findBySaleTransCode(saleTransCode);
            saleTransOrder.setIsCheck(3L);
            this.getSession().save(saleTransOrder);
            req.setAttribute("resultViewSaleDetail", "saleManagment.create.order.success");

            this.getSession().getTransaction().commit();
            this.getSession().flush();
            this.getSession().beginTransaction();

        } catch (Exception ex) {
            log.debug("Has exception: " + ex.getMessage());
            req.setAttribute("resultViewSaleDetail", "saleManagment.create.trans.error");
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().getTransaction().begin();
            return pageForward;
        }
        return pageForward;
    }

    public String cancelTrans() throws Exception {

        pageForward = "saleManagmentDetailOrder";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String saleTransCode = saleManagmentForm.getSaleTransCode();
            if (saleTransCode == null || saleTransCode.equals("")) {
                req.setAttribute("resultViewSaleDetail", "saleManagment.cancel.error.noSaleTransCode");
                return pageForward;
            }

            SaleTransOrderDAO saleTransOrderDAO = new SaleTransOrderDAO();
            saleTransOrderDAO.setSession(this.getSession());
            SaleTransOrder saleTrans = saleTransOrderDAO.findBySaleTransCode(saleTransCode);
            if (saleTrans == null) {
                req.setAttribute("resultViewSaleDetail", "saleManagment.cancel.error.saleTransNotExits");
                return pageForward;
            }

            saleTrans.setStatus(Constant.SALE_TRANS_STATUS_CANCEL);
            //TrongLV
            //Destroy Date
            saleTrans.setDestroyDate(getSysdate());
            saleTrans.setDestroyUser(userToken.getLoginName());
            saleTrans.setIsCheck(2L);
            /* CAP NHAT STATUS = 4 */
            this.getSession().save(saleTrans);

            // tra lai trang thai chua su dung cho giay nop tienn ( table bankTranInfo)
            if (saleTrans.getOrderCode() != null && !saleTrans.getOrderCode().trim().isEmpty()) {
                rollbackBankTranferInfo(userToken, saleTrans.getOrderCode(), saleTrans.getAmount(), saleTrans.getBankName());

            }
            if (saleTrans.getOrderCode2() != null && !saleTrans.getOrderCode2().trim().isEmpty()) {
                rollbackBankTranferInfo(userToken, saleTrans.getOrderCode2(), saleTrans.getAmount2(), saleTrans.getBankName2());

            }
            if (saleTrans.getOrderCode3() != null && !saleTrans.getOrderCode3().trim().isEmpty()) {
                rollbackBankTranferInfo(userToken, saleTrans.getOrderCode3(), saleTrans.getAmount3(), saleTrans.getBankName3());

            }

            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            req.setAttribute("resultViewSaleDetail", "saleManagment.cancel.success");

            DbProcessor db = new DbProcessor();
            String message = ResourceBundle.getBundle("config").getString("msgCancelTrans");
            String userName = userToken.getLoginName();
            StaffDAO staffDAO = new StaffDAO();
            Staff staff = staffDAO.findByStaffId(saleTrans.getStaffId());
            String isdn = "258" + db.getIsdnStaff(staff.getStaffCode());
            String orderCode = "";
            db.sendSms(isdn, message.replace("ZZZ", orderCode).replace("XXX", userName), "86904");

            return pageForward;

        } catch (Exception ex) {
            log.debug("", ex);
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.undefine");
        }
        return pageForward;
    }

    /**
     * Tannh, 31/08/2018 rollbackBankTranferInfobang TranferCode
     *
     * @param TranferId
     * @return
     * @throws java.lang.Exception
     */
    public void rollbackBankTranferInfo(UserToken userToken, String tranferCode, String amount, String bankName) throws Exception {
        log.debug("funtion rollbackBankTranferInfo");
        try {
            BankTranferInfo bf = findAvailableByTranferCodeAmountName(tranferCode, amount, bankName);
            bf.setStatus("1");
            bf.setUpdateTime(null);
            bf.setUpdateUser(null);
            bf.setCheckTime(null);
            bf.setCheckUser(null);
            bf.setStaffRequest(null);
            bf.setStaffApprove(null);
            bf.setPurpose(null);
            bf.setRollbackReason("systemSM_rollback_cancelOrder");
            bf.setRollbackUser(userToken.getLoginName());
            bf.setRollbackTime(new Date());
            bf.setSaleTransId(null);

            this.getSession().save(bf);

        } catch (Exception re) {
            log.error("funtion rollbackBankTranferInfo errors", re);
            throw re;
        }
    }

    /**
     * Tannh, 31/08/2018 Lay BankTranInfo available bang TranferCode
     *
     * @param tranferCode
     * @param amount
     * @param bankName
     * @return
     * @throws java.lang.Exception
     */
    public BankTranferInfo findAvailableByTranferCodeAmountName(String tranferCode, String amount, String bankName) throws Exception {
        log.debug("finding all findAvailableByTranferCodeAmountName");
        try {
            String queryString = "from BankTranferInfo  where tranferCode = ? and amount = ? and bankName = ? and sale_trans_id is null ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, tranferCode);
            queryObject.setParameter(1, amount);
            queryObject.setParameter(2, bankName.substring(0, Math.min(bankName.length(), 3)));
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (BankTranferInfo) lst.get(0);
            }
            return null;
        } catch (Exception re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    //tannh20180123 update order 
//    viewTransDetail
    public String updateOrder() throws Exception {
        log.debug("# Begin method SaleManagmentDAO.updateOrder");
        pageForward = "saleManagmentDetailOrder";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        try {
            // xu li cho 1 bank document 
            if (saleManagmentForm.getListBankDocuments() == null || saleManagmentForm.getListBankDocuments().trim().equalsIgnoreCase("")) {

                // check khong nhap orderCode
                if (saleManagmentForm.getOrderCode() == null || saleManagmentForm.getOrderCode().trim().equalsIgnoreCase("")) {
                    req.setAttribute("resultViewSaleDetail", "ERR.SAE.153");
                    return pageForward;
                } else {
                    if (checkAlreadlyHaveOrderCode(saleManagmentForm.getOrderCode())) {
                        req.setAttribute("resultViewSaleDetail", "ERR.SAE.152");
                        return pageForward;
                    }
                    if (checkAlreadlyHaveOrderCodePM(saleManagmentForm.getOrderCode())) {
                        req.setAttribute("resultViewSaleDetail", "ERR.SAE.152");
                        return pageForward;
                    }
                }

                //LinhNBV start modified on May 21 2018: Check exist bank_document_no
                DbProcessor db = new DbProcessor();
                boolean isExistInventorySystem = db.checkExistDocumentNoInInventorySystem(saleManagmentForm.getOrderCode());
                if (isExistInventorySystem) {
                    req.setAttribute("resultViewSaleDetail", "ERR.SAE.152");
                    return pageForward;
                }
                boolean isExistPmSystem = db.checkExistDocumentNoInPaymentSystem(saleManagmentForm.getOrderCode());
                if (isExistPmSystem) {
                    req.setAttribute("resultViewSaleDetail", "ERR.SAE.152");
                    return pageForward;
                }

                Float amount = Float.parseFloat(saleManagmentForm.getAmount());
                Float amount2 = 0F;
                if (!"".equalsIgnoreCase(saleManagmentForm.getAmount2())) {
                    amount2 = Float.parseFloat(saleManagmentForm.getAmount2());
                }
                Float amount3 = 0F;
                if (!"".equalsIgnoreCase(saleManagmentForm.getAmount3())) {
                    amount3 = Float.parseFloat(saleManagmentForm.getAmount3());
                }
                Float total = amount + amount2 + amount3;

                if (total < Float.parseFloat(saleManagmentForm.getTotalMoney().replaceAll(",", ""))) {
                    req.setAttribute("resultViewSaleDetail", "You are not enough money to create transaction! ");
                    return pageForward;
                }
                String checkTrans = callWSSOAPcheckTrans(saleManagmentForm.getOrderCode().toUpperCase(), saleManagmentForm.getBankName().toUpperCase(), saleManagmentForm.getAmount(), userToken);
                if ("01".equalsIgnoreCase(checkTrans)) {
                    req.setAttribute("resultViewSaleDetail", "TransCode not exist");
                    return pageForward;
                } else if ("02".equalsIgnoreCase(checkTrans)) {
                    req.setAttribute("resultViewSaleDetail", "TransCode currently checking by other user");
                    return pageForward;
                } else if ("03".equalsIgnoreCase(checkTrans)) {
                    req.setAttribute("resultViewSaleDetail", "TransCode already used");
                    return pageForward;
                } else if ("04".equalsIgnoreCase(checkTrans)) {
                    req.setAttribute("resultViewSaleDetail", "Invalid BankName");
                    return pageForward;
                } else if ("05".equalsIgnoreCase(checkTrans)) {
                    req.setAttribute("resultViewSaleDetail", "TransCode and Amount and Bank not map");
                    return pageForward;
                } else if ("99".equalsIgnoreCase(checkTrans)) {
                    req.setAttribute("resultViewSaleDetail", "System error");
                    return pageForward;
                } else if ("00".equalsIgnoreCase(checkTrans)) {
                } else {
                    req.setAttribute("resultViewSaleDetail", "System error ! callWSSOAPcheckTrans error!");
                    return pageForward;
                }

                // Start : check khi them nhieu bank ducument
                if (saleManagmentForm.getOrderCode2() != null && !saleManagmentForm.getOrderCode2().trim().equalsIgnoreCase("")) {
                    // check khong nhap orderCode
                    if (saleManagmentForm.getOrderCode2() == null || saleManagmentForm.getOrderCode2().trim().equalsIgnoreCase("")) {
                        req.setAttribute("resultViewSaleDetail", "ERR.SAE.153");
                        return pageForward;
                    } else {
                        if (checkAlreadlyHaveOrderCode(saleManagmentForm.getOrderCode2())) {
                            req.setAttribute("resultViewSaleDetail", "ERR.SAE.152");
                            return pageForward;
                        }
                        if (checkAlreadlyHaveOrderCodePM(saleManagmentForm.getOrderCode2())) {
                            req.setAttribute("resultViewSaleDetail", "ERR.SAE.152");
                            return pageForward;
                        }
                    }

                    //LinhNBV start modified on May 21 2018: Check exist bank_document_no
                    boolean isExistInventorySystem2 = db.checkExistDocumentNoInInventorySystem(saleManagmentForm.getOrderCode2());
                    if (isExistInventorySystem2) {
                        req.setAttribute("resultViewSaleDetail", "ERR.SAE.152");
                        return pageForward;
                    }
                    boolean isExistPmSystem2 = db.checkExistDocumentNoInPaymentSystem(saleManagmentForm.getOrderCode2());
                    if (isExistPmSystem2) {
                        req.setAttribute("resultViewSaleDetail", "ERR.SAE.152");
                        return pageForward;
                    }

                    String checkTrans2 = callWSSOAPcheckTrans(saleManagmentForm.getOrderCode2().toUpperCase(), saleManagmentForm.getBankName2().toUpperCase(), saleManagmentForm.getAmount2(), userToken);
                    if ("01".equalsIgnoreCase(checkTrans2)) {
                        req.setAttribute("resultViewSaleDetail", "TransCode not exist");
                        return pageForward;
                    } else if ("02".equalsIgnoreCase(checkTrans2)) {
                        req.setAttribute("resultViewSaleDetail", "TransCode currently checking by other user");
                        return pageForward;
                    } else if ("03".equalsIgnoreCase(checkTrans2)) {
                        req.setAttribute("resultViewSaleDetail", "TransCode already used");
                        return pageForward;
                    } else if ("04".equalsIgnoreCase(checkTrans2)) {
                        req.setAttribute("resultViewSaleDetail", "Invalid BankName");
                        return pageForward;
                    } else if ("05".equalsIgnoreCase(checkTrans2)) {
                        req.setAttribute("resultViewSaleDetail", "TransCode and Amount and Bank not map");
                        return pageForward;
                    } else if ("99".equalsIgnoreCase(checkTrans2)) {
                        req.setAttribute("resultViewSaleDetail", "System error");
                        return pageForward;
                    } else if ("00".equalsIgnoreCase(checkTrans2)) {
                    } else {
                        req.setAttribute("resultViewSaleDetail", "System error ! callWSSOAPcheckTrans error!");
                        return pageForward;
                    }
                }

                if (saleManagmentForm.getOrderCode3() != null && !saleManagmentForm.getOrderCode3().trim().equalsIgnoreCase("")) {
                    // check khong nhap orderCode
                    if (saleManagmentForm.getOrderCode3() == null || saleManagmentForm.getOrderCode3().trim().equalsIgnoreCase("")) {
                        req.setAttribute("resultViewSaleDetail", "ERR.SAE.153");
                        return pageForward;
                    } else {
                        if (checkAlreadlyHaveOrderCode(saleManagmentForm.getOrderCode3())) {
                            req.setAttribute("resultViewSaleDetail", "ERR.SAE.152");
                            return pageForward;
                        }
                        if (checkAlreadlyHaveOrderCodePM(saleManagmentForm.getOrderCode3())) {
                            req.setAttribute("resultViewSaleDetail", "ERR.SAE.152");
                            return pageForward;
                        }
                    }

                    //LinhNBV start modified on May 21 2018: Check exist bank_document_no
                    boolean isExistInventorySystem3 = db.checkExistDocumentNoInInventorySystem(saleManagmentForm.getOrderCode3());
                    if (isExistInventorySystem3) {
                        req.setAttribute("resultViewSaleDetail", "ERR.SAE.152");
                        return pageForward;
                    }
                    boolean isExistPmSystem3 = db.checkExistDocumentNoInPaymentSystem(saleManagmentForm.getOrderCode3());
                    if (isExistPmSystem3) {
                        req.setAttribute("resultViewSaleDetail", "ERR.SAE.152");
                        return pageForward;
                    }

                    String checkTrans3 = callWSSOAPcheckTrans(saleManagmentForm.getOrderCode3().toUpperCase(), saleManagmentForm.getBankName3().toUpperCase(), saleManagmentForm.getAmount3(), userToken);
                    if ("01".equalsIgnoreCase(checkTrans3)) {
                        req.setAttribute("resultViewSaleDetail", "TransCode not exist");
                        return pageForward;
                    } else if ("02".equalsIgnoreCase(checkTrans3)) {
                        req.setAttribute("resultViewSaleDetail", "TransCode currently checking by other user");
                        return pageForward;
                    } else if ("03".equalsIgnoreCase(checkTrans3)) {
                        req.setAttribute("resultViewSaleDetail", "TransCode already used");
                        return pageForward;
                    } else if ("04".equalsIgnoreCase(checkTrans3)) {
                        req.setAttribute("resultViewSaleDetail", "Invalid BankName");
                        return pageForward;
                    } else if ("05".equalsIgnoreCase(checkTrans3)) {
                        req.setAttribute("resultViewSaleDetail", "TransCode and Amount and Bank not map");
                        return pageForward;
                    } else if ("99".equalsIgnoreCase(checkTrans3)) {
                        req.setAttribute("resultViewSaleDetail", "System error");
                        return pageForward;
                    } else if ("00".equalsIgnoreCase(checkTrans3)) {
                    } else {
                        req.setAttribute("resultViewSaleDetail", "System error ! callWSSOAPcheckTrans error!");
                        return pageForward;
                    }
                }

                // End : check khi them nhieu bank ducument
                //LinhNBV end.
                String lStr = null;
                if (!saleManagmentForm.getImageUrl().equals("") && !saleManagmentForm.getImageUrl().trim().equals(",")) {
                    String serverFilePath = this.saleManagmentForm.getImageUrl();
                    String[] fileParth = serverFilePath.split(",");
                    // tannh 11/23/2017 ; upload file to server 
                    String host = ResourceBundleUtils.getResource("server_get_file_to_server_host");
                    String userName = ResourceBundleUtils.getResource("server_get_file_to_server_username");
                    String password = ResourceBundleUtils.getResource("server_get_file_to_server_password");
                    String tempDir = ResourceBundleUtils.getResource("server_get_file_to_server_tempdir_upto_file_accounttrans");
                    if (fileParth[0].length() == 0) {
                        saleManagmentForm.setImageUrl("");
                    } else {
                        if (fileParth[0].endsWith(".rar")) {

                            if (fileParth[0].contains("\\")) {
                                lStr = fileParth[0].substring(fileParth[0].lastIndexOf("\\"));
                            } else {
                                lStr = fileParth[0].substring(fileParth[0].lastIndexOf("/"));
                            }
                            lStr = tempDir + lStr.substring(1);
                            saleManagmentForm.setImageUrl(lStr);
                            List<String> lst = new ArrayList<String>();
                            //LinhNBV start modified on May 21 2018: Edit file upload as: date_user_name
                            lst.add(fileParth[0]);
                            boolean uploadFile = uploadListFileFromFTPServer(host, userName, password, lst, tempDir, tempDir, userToken.getLoginName().toUpperCase());
                            if (!uploadFile) {
                                req.setAttribute("resultViewSaleDetail", "ERR.SAE.157");
                                return pageForward;
                            }
                        } else {
                            req.setAttribute("resultViewSaleDetail", "ER.RAR");
                            return pageForward;
                        }
                    }
                } else {
                    req.setAttribute("resultViewSaleDetail", "ERR.SAE.155");
                    return pageForward;
                }
                // update sale_trans_order
                updateSaleTransOrder(saleManagmentForm, userToken.getLoginName().toUpperCase());
            }
            req.setAttribute("resultViewSaleDetail", "saleManagment.updateOrder.success");
        } catch (Exception ex) {
            req.setAttribute("resultViewSaleDetail", ex.getMessage());
        }
        return pageForward;
    }

    public String approveTrans() throws Exception {

        pageForward = "saleManagmentDetailOrder";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {

            String saleTransCode = saleManagmentForm.getSaleTransCode();
            if (saleTransCode == null || saleTransCode.equals("")) {
                req.setAttribute("resultViewSaleDetail", "saleManagment.cancel.error.noSaleTransCode");
                return pageForward;
            }

            SaleTransOrderDAO saleTransOrderDAO = new SaleTransOrderDAO();
            saleTransOrderDAO.setSession(this.getSession());
            SaleTransOrder saleTrans = saleTransOrderDAO.findBySaleTransCode(saleTransCode);
            if (saleTrans == null) {
                req.setAttribute("resultViewSaleDetail", "saleManagment.cancel.error.saleTransNotExits");
                return pageForward;
            }

            String imageUrl = saleManagmentForm.getImageUrl();
            if (imageUrl == null || imageUrl.equals("") || imageUrl.equals(",")) {
                req.setAttribute("resultViewSaleDetail", "ERR.SAE.155");
                return pageForward;
            }

            // check khong nhap orderCode
            if (saleManagmentForm.getOrderCode() == null || saleManagmentForm.getOrderCode().trim().equalsIgnoreCase("")) {
                req.setAttribute("resultViewSaleDetail", "ERR.SAE.153");
                return pageForward;
            }

            String checkTrans = callWSSOAPupdateTrans(saleManagmentForm.getOrderCode().toUpperCase(), saleManagmentForm.getBankName().toUpperCase(), saleManagmentForm.getAmount(), userToken,
                    findAvailableByStaffId(saleTrans.getStaffId()).getStaffCode());
            if ("01".equalsIgnoreCase(checkTrans)) {
                req.setAttribute("resultViewSaleDetail", "TransCode not exist");
                return pageForward;
            } else if ("02".equalsIgnoreCase(checkTrans)) {
                req.setAttribute("resultViewSaleDetail", "TransCode currently checking by other user");
                return pageForward;
            } else if ("03".equalsIgnoreCase(checkTrans)) {
                req.setAttribute("resultViewSaleDetail", "TransCode already used");
                return pageForward;
            } else if ("04".equalsIgnoreCase(checkTrans)) {
                req.setAttribute("resultViewSaleDetail", "Invalid BankName");
                return pageForward;
            } else if ("05".equalsIgnoreCase(checkTrans)) {
                req.setAttribute("resultViewSaleDetail", "TransCode and Amount and Bank not map");
                return pageForward;
            } else if ("99".equalsIgnoreCase(checkTrans)) {
                req.setAttribute("resultViewSaleDetail", "System error");
                return pageForward;
            } else if ("00".equalsIgnoreCase(checkTrans)) {
            } else {
                req.setAttribute("resultViewSaleDetail", "System error ! callWSSOAPcheckTrans error!");
                return pageForward;
            }
            //Start : doi voi truong hop duyet nhieu bank document
            if (saleManagmentForm.getOrderCode2() != null && !saleManagmentForm.getOrderCode2().trim().equalsIgnoreCase("")) {

                String checkTrans2 = callWSSOAPupdateTrans(saleManagmentForm.getOrderCode2().toUpperCase(), saleManagmentForm.getBankName2().toUpperCase(), saleManagmentForm.getAmount2(), userToken,
                        findAvailableByStaffId(saleTrans.getStaffId()).getStaffCode());
                if ("01".equalsIgnoreCase(checkTrans2)) {
                    req.setAttribute("resultViewSaleDetail", "TransCode not exist");
                    return pageForward;
                } else if ("02".equalsIgnoreCase(checkTrans2)) {
                    req.setAttribute("resultViewSaleDetail", "TransCode currently checking by other user");
                    return pageForward;
                } else if ("03".equalsIgnoreCase(checkTrans2)) {
                    req.setAttribute("resultViewSaleDetail", "TransCode already used");
                    return pageForward;
                } else if ("04".equalsIgnoreCase(checkTrans2)) {
                    req.setAttribute("resultViewSaleDetail", "Invalid BankName");
                    return pageForward;
                } else if ("05".equalsIgnoreCase(checkTrans2)) {
                    req.setAttribute("resultViewSaleDetail", "TransCode and Amount and Bank not map");
                    return pageForward;
                } else if ("99".equalsIgnoreCase(checkTrans2)) {
                    req.setAttribute("resultViewSaleDetail", "System error");
                    return pageForward;
                } else if ("00".equalsIgnoreCase(checkTrans2)) {
                } else {
                    req.setAttribute("resultViewSaleDetail", "System error ! callWSSOAPcheckTrans error!");
                    return pageForward;
                }
            }
            if (saleManagmentForm.getOrderCode3() != null && !saleManagmentForm.getOrderCode3().trim().equalsIgnoreCase("")) {

                String checkTrans3 = callWSSOAPupdateTrans(saleManagmentForm.getOrderCode3().toUpperCase(), saleManagmentForm.getBankName3().toUpperCase(), saleManagmentForm.getAmount3(), userToken,
                        findAvailableByStaffId(saleTrans.getStaffId()).getStaffCode());
                if ("01".equalsIgnoreCase(checkTrans3)) {
                    req.setAttribute("resultViewSaleDetail", "TransCode not exist");
                    return pageForward;
                } else if ("02".equalsIgnoreCase(checkTrans3)) {
                    req.setAttribute("resultViewSaleDetail", "TransCode currently checking by other user");
                    return pageForward;
                } else if ("03".equalsIgnoreCase(checkTrans3)) {
                    req.setAttribute("resultViewSaleDetail", "TransCode already used");
                    return pageForward;
                } else if ("04".equalsIgnoreCase(checkTrans3)) {
                    req.setAttribute("resultViewSaleDetail", "Invalid BankName");
                    return pageForward;
                } else if ("05".equalsIgnoreCase(checkTrans3)) {
                    req.setAttribute("resultViewSaleDetail", "TransCode and Amount and Bank not map");
                    return pageForward;
                } else if ("99".equalsIgnoreCase(checkTrans3)) {
                    req.setAttribute("resultViewSaleDetail", "System error");
                    return pageForward;
                } else if ("00".equalsIgnoreCase(checkTrans3)) {
                } else {
                    req.setAttribute("resultViewSaleDetail", "System error ! callWSSOAPcheckTrans error!");
                    return pageForward;
                }
            }
            //End : doi voi truong hop duyet nhieu bank document

            saleTrans.setStatus("5");//5 is meaning approve
            //TrongLV
            //Destroy Date
            saleTrans.setDestroyDate(getSysdate());
            saleTrans.setDestroyUser(userToken.getLoginName());
            saleTrans.setIsCheck(1L);
            /* CAP NHAT STATUS = 4 */
            this.getSession().save(saleTrans);

            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();
            req.setAttribute("resultViewSaleDetail", "saleManagment.approve.success");

            DbProcessor db = new DbProcessor();
            String message = ResourceBundle.getBundle("config").getString("msgToSaleStaff");
            String userName = userToken.getLoginName();
            StaffDAO staffDAO = new StaffDAO();
            Staff staff = staffDAO.findByStaffId(saleTrans.getStaffId());
            String isdn = "258" + db.getIsdnStaff(staff.getStaffCode());
            String orderCode = saleTrans.getOrderCode();
            db.sendSms(isdn, message.replace("ZZZ", orderCode).replace("XXX", userName), "86904");
//            test
//            db.sendSms("258870093239", message.replace("ZZZ", orderCode).replace("XXX", userName), "86904");

            return pageForward;

        } catch (Exception ex) {
            log.debug("", ex);
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.undefine");
        }

        return pageForward;
    }

    /**
     *
     * author : TuanNV date : 25/03/2009 purpose : cap nhat giao dich ban hang
     * cho cong tac vien modified : tamdt1, 28/10/2009
     *
     */
    private SaleTrans expSaleTrans(UserToken userToken, Staff staff, SaleTransOrder saleTransOrder) {
        try {
            Long saleTransId = getSequence("SALE_TRANS_SEQ");
            SaleTrans saleTrans = new SaleTrans();
            saleTrans.setSaleTransId(saleTransId);
            saleTrans.setSaleTransDate(getSysdate());

            saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_COLLABORATOR); //loai giao dich: ban cho CTV/ diem ban
            saleTrans.setStatus(String.valueOf(Constant.SALE_PAY_NOT_BILL)); //da thanh toan nhung chua lap hoa don
            saleTrans.setShopId(userToken.getShopId());
            saleTrans.setStaffId(userToken.getUserID());

            saleTrans.setPayMethod(saleTransOrder.getPayMethod()); //mac dinh giao dich do CM day sang la tien mat
            saleTrans.setDiscount(saleTransOrder.getDiscount()); //tien chiet khau
            saleTrans.setAmountTax(saleTransOrder.getAmountTax()); //tong tien phai tra cua KH, = chua thue + thue - KM - C/Khau
            saleTrans.setAmountNotTax(saleTransOrder.getAmountNotTax()); //tien chua thue

            saleTrans.setVat(17D); //tien vat

            saleTrans.setTax(saleTransOrder.getTax());
            saleTrans.setCurrency("MT");

            saleTrans.setCustName(staff.getStaffCode() + " - " + staff.getName()); //doi voi ban hang cho CTV, luu thong tin truong nay la ten CTV
            saleTrans.setReceiverId(staff.getStaffId());
            saleTrans.setReceiverType(Constant.OWNER_TYPE_STAFF);

            saleTrans.setAddress(saleTrans.getAddress()); //CM day sang
            saleTrans.setReasonId(saleTransOrder.getReasonId());
            saleTrans.setTelecomServiceId(saleTransOrder.getTelecomServiceId());
            saleTrans.setSaleTransCode(CommonDAO.formatTransCode(saleTransId));
            saleTrans.setCreateStaffId(userToken.getUserID());

            this.getSession().save(saleTrans);
            return saleTrans;
        } catch (Exception ex) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return null;
        }

    }

    /**
     * author : TuanNV date : 25/03/2009 purpose : cap nhat giao dich ban hang
     * cho cong tac vien modified : tamdt1, 28/10/2009
     *
     */
    private SaleTransDetail expSaleTransDetail(UserToken userToken, SaleTrans saleTrans, SaleTransDetailOrder saleTransForm) {
        try {
            /**
             * DUCTM_20110215_START log.
             */
            String function = "com.viettel.im.database.DAO.SaleToCollaboratorDAO.expSaleTransDetail";
            Long callId = getApCallId();
            Date startTime = new Date();
            logStartCallBegin(startTime, function, callId);

            SaleTransDetail saleTransDetail = new SaleTransDetail();

            //tamdt1, start, 15/07/2010
            //cap nhat them cac truong can thiet phuc vu bao cao doanh thu
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(saleTransForm.getStockModelId());
            if (stockModel != null) {
                saleTransDetail.setStockModelCode(stockModel.getStockModelCode());
                saleTransDetail.setStockModelName(stockModel.getName());

                saleTransDetail.setAccountingModelCode(stockModel.getAccountingModelCode());
                saleTransDetail.setAccountingModelName(stockModel.getAccountingModelName());

            } else {
                //khong tim thay gia
                logEndCall(startTime, new Date(), function, callId);
                return null;
            }

            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(stockModel.getStockTypeId());
            if (stockType != null) {
                saleTransDetail.setStockTypeId(stockType.getStockTypeId());
                saleTransDetail.setStockTypeName(stockType.getName());
            } else {
                logEndCall(startTime, new Date(), function, callId);
                return null;
            }
            //tamdt1, end

            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(this.getSession());
            Price price = priceDAO.findById(saleTransForm.getPriceId());
            if (price == null || price.getPriceId() == null) {
                logEndCall(startTime, new Date(), function, callId);
                return null;
            }

            saleTransDetail.setSaleTransDetailId(getSequence("SALE_TRANS_DETAIL_SEQ"));
            saleTransDetail.setSaleTransId(saleTrans.getSaleTransId());
            saleTransDetail.setSaleTransDate(saleTrans.getSaleTransDate());

            saleTransDetail.setStockModelId(saleTransForm.getStockModelId());

            saleTransDetail.setPriceId(price.getPriceId());

            saleTransDetail.setCurrency(price.getCurrency());
            saleTransDetail.setPriceVat(price.getVat());//thue suat
            saleTransDetail.setPrice(price.getPrice());
            saleTransDetail.setAmount(price.getPrice() * saleTransForm.getQuantity());
            saleTransDetail.setQuantity(saleTransForm.getQuantity());//so luong
            saleTransDetail.setDiscountAmount(0.0);//chiet khau
            saleTransDetail.setDiscountId(saleTransForm.getDiscountId());

            Double amountAfterTax = 0.0;
            Double amountTax = 0.0;
            Double amountBeforeTax = 0.0;

            if (!Constant.PRICE_AFTER_VAT) {
                amountBeforeTax = price.getPrice() * saleTransForm.getQuantity();
                amountTax = amountBeforeTax * price.getVat() / 100.0;
                amountAfterTax = amountBeforeTax + amountTax;
            } else {
                amountAfterTax = price.getPrice() * saleTransForm.getQuantity();
                amountBeforeTax = amountAfterTax * 100.0 / (price.getVat() + 100.0);
                amountTax = amountAfterTax - amountBeforeTax;
            }

            saleTransDetail.setAmountBeforeTax(amountBeforeTax);
            saleTransDetail.setAmountTax(amountTax);
            saleTransDetail.setVatAmount(amountTax);
            saleTransDetail.setAmountAfterTax(amountAfterTax);

//            if (mapStockModelDiscount != null && !mapStockModelDiscount.isEmpty()) {
//                Double discountAmount = mapStockModelDiscount.get(saleTransForm.getStockModelId());
//                if (discountAmount != null) {//chiet khau
            saleTransDetail.setDiscountAmount(saleTransForm.getDiscountAmount());
//                }
//            }

            saleTransDetail.setStateId(Constant.STATE_NEW);
            saleTransDetail.setNote(saleTransForm.getNote());
            this.getSession().save(saleTransDetail);

            //Tru kho nhan vien quan ly CTV/DB
//            StockCommonDAO stockCommonDAO = new StockCommonDAO();
//            stockCommonDAO.setSession(this.getSession());
//            if (!stockCommonDAO.expStockTotal(Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATE_NEW,
//                    saleTransForm.getStockModelId(), saleTransForm.getQuantity(), true)) {
//
//                logEndCall(startTime, new Date(), function, callId);
//                return null;
//            }
            GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), userToken.getUserID(), Constant.OWNER_TYPE_STAFF, saleTransForm.getStockModelId(),
                    Constant.STATE_NEW, -saleTransForm.getQuantity(), -saleTransForm.getQuantity(), null,
                    userToken.getUserID(), 0L, 0L, "", Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.SALE_TRANS);
            boolean noError = genResult.isSuccess();

            //R499 trung dh3 add end
            if (noError == false) {
                return null;
            }

            logEndCall(startTime, new Date(), function, callId);
            return saleTransDetail;
        } catch (Exception ex) {
            log.error("Has exception: " + ex.getMessage());
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();

            return null;
        }

    }

    /**
     * @Author: Tuannv
     * @Date created: 25/03/2009
     * @Purpose: save sale trans serial
     */
    private boolean saveSaleTransSerial(UserToken userToken, Staff staff, SaleTransDetail saleTransDetail, List<StockTransSerial> lstSerial)
            throws Exception {

        Long stockModelId = 0L;
        boolean noError = true;

        stockModelId = saleTransDetail.getStockModelId();

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(getSession());
        StockModel stockModel = stockModelDAO.findById(stockModelId);
        if (stockModel == null) {
            return false;
        }
        int total = 0;
        BaseStockDAO baseStockDAO = new BaseStockDAO();
        baseStockDAO.setSession(this.getSession());
        String tableName = baseStockDAO.getTableNameByStockType(stockModel.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);
        String SQLUPDATE = "update " + tableName + " set status = ?, owner_type = ?, owner_id = ? where stock_model_id = ? "
                + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? ";

        if (stockModel.getStockTypeId() != null
                && (stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)
                || stockModel.getStockTypeId().equals(Constant.STOCK_KIT)
                || stockModel.getStockTypeId().equals(Constant.STOCK_SIM_PRE_PAID)
                || stockModel.getStockTypeId().equals(Constant.STOCK_SIM_POST_PAID))) {

            SQLUPDATE = "update " + tableName + " set status = ?, owner_type = ?, owner_id = ?, CHANNEL_STOCK_STATUS = ? where stock_model_id = ? "
                    + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? ";
        }
        if (lstSerial != null && lstSerial.size() > 0) {
            StockTransSerial stockSerial = null;
            for (int idx = 0; idx < lstSerial.size(); idx++) {
                stockSerial = (StockTransSerial) lstSerial.get(idx);
                BigInteger startSerialTemp = new BigInteger(stockSerial.getFromSerial());
                BigInteger endSerialTemp = new BigInteger(stockSerial.getToSerial());
                Long totals = endSerialTemp.subtract(startSerialTemp).longValue() + 1;

                Long saleTransDetailId = getSequence("SALE_TRANS_SERIAL_SEQ");
                SaleTransSerial saleTransSerial = new SaleTransSerial();
                saleTransSerial.setSaleTransSerialId(saleTransDetailId);
                saleTransSerial.setSaleTransDetailId(saleTransDetail.getSaleTransDetailId());
                saleTransSerial.setStockModelId(stockModelId);
                saleTransSerial.setSaleTransDate(saleTransDetail.getSaleTransDate());
                saleTransSerial.setFromSerial(stockSerial.getFromSerial());
                saleTransSerial.setToSerial(stockSerial.getToSerial());
                saleTransSerial.setQuantity(totals);
                this.getSession().save(saleTransSerial);

                //Cap nhat ve kho CTV/DB & trang thai da ban
                Query query = getSession().createSQLQuery(SQLUPDATE);
                if (stockModel.getStockTypeId() != null
                        && (stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)
                        || stockModel.getStockTypeId().equals(Constant.STOCK_KIT)
                        || stockModel.getStockTypeId().equals(Constant.STOCK_SIM_PRE_PAID)
                        || stockModel.getStockTypeId().equals(Constant.STOCK_SIM_POST_PAID))) {

                    query.setParameter(0, Constant.STATUS_SALED);
                    query.setParameter(1, Constant.OWNER_TYPE_STAFF);
                    query.setParameter(2, staff.getStaffId());
                    query.setParameter(3, 1L);
                    query.setParameter(4, stockModelId);
                    query.setParameter(5, Constant.OWNER_TYPE_STAFF);
                    query.setParameter(6, userToken.getUserID());
                    query.setParameter(7, stockSerial.getFromSerial());
                    query.setParameter(8, stockSerial.getToSerial());
                    query.setParameter(9, Constant.STATUS_USE);
                } else {
                    query.setParameter(0, Constant.STATUS_SALED);
                    query.setParameter(1, Constant.OWNER_TYPE_STAFF);
                    query.setParameter(2, staff.getStaffId());
                    query.setParameter(3, stockModelId);
                    query.setParameter(4, Constant.OWNER_TYPE_STAFF);
                    query.setParameter(5, userToken.getUserID());
                    query.setParameter(6, stockSerial.getFromSerial());
                    query.setParameter(7, stockSerial.getToSerial());
                    query.setParameter(8, Constant.STATUS_USE);
                }

                int count = query.executeUpdate();
                if (count != totals.intValue()) {
                    return false;
                }
                total += count;
                //luu thong tin vao bang VcRequets                                
                if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)) {
                    VcRequestDAO vcRequestDAO = new VcRequestDAO();
                    vcRequestDAO.setSession(getSession());
                    vcRequestDAO.saveVcRequest(DateTimeUtils.getSysDate(), stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.REQUEST_TYPE_SALE_CTV, saleTransDetail.getSaleTransId());
                }

                if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_KIT)) {
                    ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                    reqActiveKitDAO.setSession(getSession());
                    reqActiveKitDAO.saveReqActiveKit(stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.SALE_TYPE, saleTransDetail.getSaleTransId(),
                            Long.parseLong(Constant.SALE_TRANS_TYPE_COLLABORATOR), getSysdate());

                    /* Kiem tra neu la mat hang KIT da nang thi lay gia tri ISDN gan voi KIT */
//                    if (checkKITCTV(stockModel.getStockModelCode())) {
//                        if (stockSerial.getFromSerial().equals(stockSerial.getToSerial())) {
//                            String sql = "From StockKit where to_number(serial) = ?";
//                            Query query2 = getSession().createQuery(sql);
//                            query2.setParameter(0, stockSerial.getFromSerial());
//                            List<StockKit> list = query2.list();
//                            if (list != null && list.size() > 0) {
//                                saleTransDetailBean.setKitMfsIsdn(list.get(0).getIsdn());
//                            }
//                        }
//                    }
                }
            }
        }
        //Check so luong serial update duoc khong bang tong so luong hang xuat di
        if (total != saleTransDetail.getQuantity().intValue()) {
            return false;
        }

        return noError;
    }

    public boolean checkKITCTV(String stockModelCode) {
        String tmpKitCodeList = Constant.STOCK_MODEL_CODE_KITCTV;
        while (tmpKitCodeList.indexOf("(") >= 0) {
            tmpKitCodeList = tmpKitCodeList.replace("(", "");
        }
        while (tmpKitCodeList.indexOf(")") >= 0) {
            tmpKitCodeList = tmpKitCodeList.replace(")", "");
        }
        String[] kitCodeList = tmpKitCodeList.split(",");
        for (int i = 0; i < kitCodeList.length; i++) {
            String kitCode = kitCodeList[i].trim();
            if (stockModelCode.equals(kitCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * author tannh date create : 12/11/2017
     *
     * @return Ham luu file lay tu tren server ve local
     *
     */
    public String myDownload() throws Exception {
        log.info("begin method preaddvoucher");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        String pageForward;
        pageForward = Constant.ERROR_PAGE;
        String scanPath = req.getParameter("scanPath");
        if (scanPath == null || scanPath.equals("")) {
            req.setAttribute("resultViewSaleDetail", "ERR.SAE.156");
            return "saleManagmentDetailOrder";
        }
        setFileName(scanPath.substring(scanPath.length() - 6));
        getFileLocationToServer(scanPath);
        String[] filesrc = scanPath.split("\\/", 5);
        String tempDir = ResourceBundleUtils.getResource("server_get_file_to_server_tempdir");
        fileInputStream = new FileInputStream(new File(tempDir + filesrc[3] + "/" + filesrc[4]));
        return SUCCESS;
    }

    public String form02Download() {
        try {
            HttpServletRequest req = getRequest();
            String fileDownloadForm = req.getParameter("fileDownloadForm02");
            if (fileDownloadForm != null) {
                fileDownloadForm02 = fileDownloadForm;
                fileInputStream = new FileInputStream(new File(fileDownloadForm));  // new ByteArrayInputStream(new byte[]{0});
            } else {
                return "saleManagmentDetailOrder";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "saleManagmentDetailOrder";
        }
        return SUCCESS;
    }

    /**
     * author tannh date create : 12/11/2017
     *
     * @return Ham luu file lay tu tren server ve local
     *
     */
    public String getFileLocationToServer(String srcFilePDF) throws Exception {
        log.info("");
        String fileNameDir = "";
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            String pageForward;
            UserToken userToken = (UserToken) session.getAttribute(
                    Constant.USER_TOKEN);
            if (userToken == null) {
                return "sessionTimeout";
            }
            String host = ResourceBundleUtils.getResource("server_get_file_to_server_host");
            String userName = ResourceBundleUtils.getResource("server_get_file_to_server_username");
            String password = ResourceBundleUtils.getResource("server_get_file_to_server_password");
            String tempDir = ResourceBundleUtils.getResource("server_get_file_to_server_tempdir");

            List<String> lst = new ArrayList<String>();
            lst.add(srcFilePDF);
            String imgPathDB = srcFilePDF;
            fileNameDir = FTPUtils.retrieveListFileFromFTPServer(host, userName, password, lst, imgPathDB, tempDir);

        } catch (Exception ex) {
//            String message = StringEscapeUtils.escapeHtml(languageMap.getMessage("action.search.error"));
//            log.error(message, ex);
        } finally {
        }
        return fileNameDir;
    }

    private SaleTrans saveSaleTransRetail(SaleTransOrder saleTransOrder, List<SaleTransDetailOrder> lstSaleTransDetail) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        SaleTrans saleTrans = new SaleTrans();
        saleTrans.setSaleTransId(getSequence("SALE_TRANS_SEQ"));
        saleTrans.setSaleTransCode(CommonDAO.formatTransCode(saleTrans.getSaleTransId()));

        saleTrans.setSaleTransDate(getSysdate());
        saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_RETAIL);
        saleTrans.setStatus(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        saleTrans.setCheckStock("0");

        saleTrans.setCustName(saleTransOrder.getCustName());
        saleTrans.setCompany(saleTransOrder.getCompany());
        saleTrans.setTin(saleTransOrder.getTin());

        saleTrans.setShopId(userToken.getShopId());
        saleTrans.setStaffId(userToken.getUserID());
        saleTrans.setReasonId(saleTransOrder.getReasonId());
        saleTrans.setPayMethod(saleTransOrder.getPayMethod());
        // tinh tong so tien , tong tien truoc ,sau thue
        Double amountBeforeTax = 0.0;
        Double amountAfterTax = 0.0;
        Double amountTax = 0.0;

        for (int i = 0; i < lstSaleTransDetail.size(); i++) {
            amountBeforeTax = amountBeforeTax + lstSaleTransDetail.get(i).getAmountBeforeTax();
            amountAfterTax = amountAfterTax + lstSaleTransDetail.get(i).getAmountAfterTax();
            amountTax = amountTax + lstSaleTransDetail.get(i).getAmountTax();
        }

        saleTrans.setAmountNotTax(amountBeforeTax);
        saleTrans.setAmountTax(amountAfterTax);
        saleTrans.setTax(amountTax);

        //LinhNBV: Ban le thi khong co chiet khau
        saleTrans.setDiscount(0.0);

        saleTrans.setTelecomServiceId(saleTransOrder.getTelecomServiceId());
        saleTrans.setVat(saleTransOrder.getVat());
        saleTrans.setCurrency(saleTransOrder.getCurrency());

        this.getSession().save(saleTrans);

        return saleTrans;
    }

    private SaleTransDetail saveSaleTransDetailRetail(SaleTrans saleTrans, SaleTransDetailOrder saleTransDetailBean) throws Exception {

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        SaleTransDetail saleTransDetail = new SaleTransDetail();
        saleTransDetail.setSaleTransDetailId(getSequence("SALE_TRANS_DETAIL_SEQ"));
        saleTransDetail.setSaleTransId(saleTrans.getSaleTransId());
        saleTransDetail.setSaleTransDate(saleTrans.getSaleTransDate());

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(saleTransDetailBean.getStockModelId());
        if (stockModel == null || stockModel.getStockModelId() == null) {
            return null;
        }
        saleTransDetail.setStockModelId(stockModel.getStockModelId());

        saleTransDetail.setStockModelCode(stockModel.getStockModelCode());
        saleTransDetail.setStockModelName(stockModel.getName());

        saleTransDetail.setAccountingModelCode(stockModel.getAccountingModelCode());
        saleTransDetail.setAccountingModelName(stockModel.getAccountingModelName());

        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        StockType stockType = stockTypeDAO.findById(stockModel.getStockTypeId());
        if (stockType == null || stockType.getStockTypeId() == null) {
            return null;
        }
        saleTransDetail.setStockTypeId(stockType.getStockTypeId());
        saleTransDetail.setStockTypeCode(stockType.getTableName());
        saleTransDetail.setStockTypeName(stockType.getName());

        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        Price price = priceDAO.findById(saleTransDetailBean.getPriceId());
        if (price == null || price.getPriceId() == null) {
            return null;
        }
        saleTransDetail.setPriceId(price.getPriceId());
        saleTransDetail.setCurrency(price.getCurrency());
        saleTransDetail.setPriceVat(price.getVat());//thue suat
        saleTransDetail.setPrice(price.getPrice());
        saleTransDetail.setAmount(price.getPrice() * saleTransDetailBean.getQuantity());
        saleTransDetail.setQuantity(saleTransDetailBean.getQuantity());//so luong
        saleTransDetail.setDiscountAmount(0.0);//chiet khau

        Double amountAfterTax = 0.0;
        Double amountTax = 0.0;
        Double amountBeforeTax = 0.0;

        if (!Constant.PRICE_AFTER_VAT) {
            amountBeforeTax = price.getPrice() * saleTransDetailBean.getQuantity();
            amountTax = amountBeforeTax * price.getVat() / 100.0;
            amountAfterTax = amountBeforeTax + amountTax;
        } else {
            amountAfterTax = price.getPrice() * saleTransDetailBean.getQuantity();
            amountBeforeTax = amountAfterTax * 100.0 / (price.getVat() + 100.0);
            amountTax = amountAfterTax - amountBeforeTax;
        }

        saleTransDetail.setAmountBeforeTax(amountBeforeTax);
        saleTransDetail.setAmountTax(amountTax);
        saleTransDetail.setVatAmount(amountTax);
        saleTransDetail.setAmountAfterTax(amountAfterTax);

        saleTransDetail.setStateId(Constant.STATE_NEW);
        saleTransDetail.setNote(saleTransDetailBean.getNote());

        /*neu khach hang la vip set discountGroupId and discountAmount*/
//        if (isSaleToVipCustomer()) {
//            saleTransDetail.setDiscountId(saleTransDetailBean.getDiscountId());
//            Map<String, Map<Long, Double>> map = (Map<String, Map<Long, Double>>) getTabSession("MapStockModelDiscountAmount");
//            Map<Long, Double> mapStockModelDiscount = map.get("mapStockModelDiscount");
//
//            if (mapStockModelDiscount != null && !mapStockModelDiscount.isEmpty()) {
//                Double discountAmount = mapStockModelDiscount.get(saleTransDetailBean.getStockModelId());
//                if (discountAmount != null) {//chiet khau
//                    saleTransDetail.setDiscountAmount(discountAmount);
//                }
//            }
//        }
        this.getSession().save(saleTransDetail);

        //Tru kho GDV
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        //trung dh3
        //stockCommonDAO.expStockTotal(Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATE_NEW, saleTransDetail.getStockModelId(), saleTransDetail.getQuantity(), true);
        GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), userToken.getUserID(), Constant.OWNER_TYPE_STAFF,
                saleTransDetail.getStockModelId(), Constant.STATUS_USE, -saleTransDetail.getQuantity(), -saleTransDetail.getQuantity(), 0L,
                userToken.getUserID(), saleTrans.getReasonId(), saleTrans.getSaleTransId(),
                saleTrans.getSaleTransCode(), saleTrans.getSaleTransType(), StockTotalAuditDAO.SourceType.SALE_TRANS);
        boolean noError = genResult.isSuccess();
        if (!noError) {
            return null;
        }

        return saleTransDetail;
    }

    public boolean saveSaleTransSerialRetail(SaleTransDetail saleTransDetail, List<StockTransSerial> lstSerial) throws Exception {
        if (lstSerial == null || lstSerial.isEmpty()) {
            return false;
        }
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(getSession());
        StockModel stockModel = stockModelDAO.findById(saleTransDetail.getStockModelId());
        if (stockModel == null || stockModel.getStockModelId() == null) {
            return false;
        }
        Long total = 0L;

        for (StockTransSerial stockSerial : lstSerial) {
            BigInteger startSerialTemp = new BigInteger(stockSerial.getFromSerial());
            BigInteger endSerialTemp = new BigInteger(stockSerial.getToSerial());
            Long quantity = endSerialTemp.subtract(startSerialTemp).longValue() + 1;
            total += quantity;
            SaleTransSerial saleTransSerial = new SaleTransSerial();
            saleTransSerial.setSaleTransSerialId(getSequence("SALE_TRANS_SERIAL_SEQ"));
            saleTransSerial.setSaleTransDetailId(saleTransDetail.getSaleTransDetailId());
            saleTransSerial.setSaleTransDate(saleTransDetail.getSaleTransDate());
            saleTransSerial.setStockModelId(saleTransDetail.getStockModelId());
            saleTransSerial.setFromSerial(stockSerial.getFromSerial());
            saleTransSerial.setToSerial(stockSerial.getToSerial());
            saleTransSerial.setQuantity(quantity);
            this.getSession().save(saleTransSerial);

            if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)) {
                VcRequestDAO vcRequestDAO = new VcRequestDAO();
                vcRequestDAO.setSession(getSession());
                vcRequestDAO.saveVcRequest(saleTransDetail.getSaleTransDate(), stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.REQUEST_TYPE_SALE_RETAIL, saleTransDetail.getSaleTransId());
            }

            if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_KIT)) {
                ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                reqActiveKitDAO.setSession(getSession());
                reqActiveKitDAO.saveReqActiveKit(stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.SALE_TYPE, saleTransDetail.getSaleTransId(),
                        Long.parseLong(Constant.SALE_TRANS_TYPE_RETAIL), getSysdate());
            }
        }

        if (!saleTransDetail.getQuantity().equals(total)) {
            return false;
        }

        //update stock_good_serial
        BaseStockDAO baseStockDAO = new BaseStockDAO();
        baseStockDAO.setSession(this.getSession());
        return baseStockDAO.updateSeialExp(lstSerial, saleTransDetail.getStockModelId(),
                Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATUS_USE, Constant.STATUS_SALED, saleTransDetail.getQuantity(), null);

    }

    /*
     * Ham tinh tong gia tri hang hoa (dung trong 1 giao dich)
     * lstGoods: List<com.viettel.im.client.bean.SaleTransDetailV2Bean>
     *           List<com.viettel.im.client.bean.SaleTransDetailBean>
     *           List<com.viettel.im.client.form.SaleToCollaboratorForm>
     *           List<com.viettel.im.database.BO.StockTransFull>
     *           List<com.viettel.im.client.form.GoodsForm>
     */
    public Double sumAmountByGoodsListRetail(List<SaleTransDetailOrder> lstGoods) throws Exception {
        try {
            Double amount = 0D;
            Long stockModelId = null;
            Long quantity = null;

            if (lstGoods != null && lstGoods.size() > 0) {
                for (SaleTransDetailOrder obj : lstGoods) {

                    stockModelId = obj.getStockModelId();
                    quantity = obj.getQuantity();

                    if (stockModelId != null) {
                        //lay gia goc
                        String sql = "FROM StockModel WHERE stockModelId = ?";
                        Query query = getSession().createQuery(sql);
                        query.setParameter(0, stockModelId);
                        List<StockModel> lst = query.list();
                        if (lst != null && lst.size() > 0) {
                            if (lst.get(0).getSourcePrice() != null && quantity != null) {
                                amount += lst.get(0).getSourcePrice() * quantity;
                            }
                        }
                    }
                }
            }
            return amount;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1D;
        }
    }

    public String pageNavigator() throws Exception {
        log.debug("# Begin method SaleManagmentDAO.pageNavigator");
        pageForward = "saleManagmentResultOrder";
        return pageForward;
    }

    public boolean checkAlreadlyHaveOrderCode(String orderCode) throws Exception {
        log.debug("# Begin method checkAlreadlyHaveOrderCode");
        HttpServletRequest req = getRequest();
        List<String> list = new ArrayList();
        boolean alreadlyOrderCode = false;
        try {
            String queryString = "from SaleTransOrder where isCheck !=2 and ( orderCode = ? or orderCode2 = ? or orderCode3 = ? ) ";
            Query queryObject = getSession().createQuery(queryString);

            queryObject.setParameter(0, orderCode);
            queryObject.setParameter(1, orderCode);
            queryObject.setParameter(2, orderCode);
            list = queryObject.list();
            if (list == null || list.isEmpty() || list.size() <= 0) {
                alreadlyOrderCode = false;
            } else {
                alreadlyOrderCode = true;
            }
        } catch (Exception ex) {
            log.error(" Begin method checkAlreadlyHaveOrderCode ; Exception : " + ex);
        }
        return alreadlyOrderCode;
    }

    public boolean checkAlreadlyHaveOrderCodePM(String orderCode) throws Exception {
        log.debug("# Begin method checkAlreadlyHaveOrderCodePM");
        HttpServletRequest req = getRequest();
        List<String> list = new ArrayList();
        boolean alreadlyOrderCode = false;
        try {
            String queryString = " select bank_document_no From payment_bank_slip where  status !=2 and bank_document_no  = ?  ";
            Query queryObject = getSession("Payment").createSQLQuery(queryString);

            queryObject.setParameter(0, orderCode);
            list = queryObject.list();
            if (list == null || list.isEmpty() || list.size() <= 0) {
                alreadlyOrderCode = false;
            } else {
                alreadlyOrderCode = true;
            }
        } catch (Exception ex) {
            log.error(" Begin method checkAlreadlyHaveOrderCodePM ; Exception : " + ex);
        }
        return alreadlyOrderCode;
    }

    public boolean checkAlreadlyHaveBankTrans(String bankName, String tranferCode) throws Exception {
        log.debug("# Begin method checkAlreadlyHaveBankTrans");
        HttpServletRequest req = getRequest();
        List<String> list = new ArrayList();
        boolean alreadlyOrderCode = false;
        try {
            String queryString = " select * from bank_tranfer_info where UPPER(bank_name) = UPPER(?) and UPPER(tranfer_code) = UPPER(?)   ";
            Query queryObject = getSession().createSQLQuery(queryString);
            queryObject.setParameter(0, bankName);
            queryObject.setParameter(1, tranferCode);
            list = queryObject.list();
            if (list == null || list.isEmpty() || list.size() <= 0) {
                alreadlyOrderCode = false;
            } else {
                alreadlyOrderCode = true;
            }
        } catch (Exception ex) {
            log.error(" Begin method checkAlreadlyHaveBankTrans ; Exception : " + ex);
        }
        return alreadlyOrderCode;
    }

    //tannh start modified on Jan 14 2018: Get phone of finance staff
    public List<String> getIsdnFinanceBank(String shopCode) {
        List<String> lstIsdn = new ArrayList<String>();
        log.debug("# Begin method getIsdnFinanceBank");
        String sql = "select u.cellphone from vsa_v3.users u where \n"
                + "u.status = 1 \n"
                + "and u.user_id in \n"
                + "(\n"
                + "    select user_id from vsa_v3.role_user where is_active = 1 and role_id in (select role_id from vsa_v3.roles where status = 1 and role_code in ('BR_DIRECTOR','MV_FINANCE_MANAGER_IM'))\n"
                + ")\n"
                + "and u.user_name in \n"
                + "(\n"
                + "    select lower(staff_code) from staff where status = 1 and shop_id in (select shop_id from shop where status = 1 and lower(shop_code) = lower(?))\n"
                + ")";
        try {
            log.info("Start getIsdnOf Finance branch shopCode " + shopCode);
            Query queryObject = getSession().createSQLQuery(sql);
            queryObject.setParameter(0, shopCode);
            lstIsdn = queryObject.list();

        } catch (Exception ex) {
            log.error("Had error getIsdnFinance shopCode " + shopCode + ex.getMessage());
        }
        return lstIsdn;
    }

    //LinhNBV start modified on Jan 14 2018: Get phone of finance staff
    public List<String> getIsdnStaff(String staffCode) {
        List<String> lstIsdn = new ArrayList<String>();
        String sql = "select u.cellphone from vsa_v3.users u where \n"
                + "u.status = 1 and lower(u.user_name) = lower(?)";
        try {
            log.info("Start getIsdnOf staff ----- staff Code " + staffCode);
            Query queryObject = getSession().createSQLQuery(sql);
            queryObject.setParameter(0, staffCode);
            lstIsdn = queryObject.list();
        } catch (Exception ex) {
            log.error("Had error get isdn staff ---- staff code " + staffCode + ex.getMessage());
        }
        return lstIsdn;
    }

    public boolean checkAlreadlyHaveSerial(String saleTransCode) throws Exception {
        log.debug("# Begin method checkAlreadlyHaveSerial");
        HttpServletRequest req = getRequest();
        List<SaleTransDetailOrder> list = new ArrayList();
        List<String> list1 = new ArrayList();
        boolean alreadlySerial = false;
        try {
//              String queryString = "from SaleTransSerialOrder where saleTransDetailId in "
//                      + " ( select saleTransDetailId from SaleTransDetailOrder where saleTransId in  "
//                      + " ( select saleTransId from SaleTransOrder where saleTransCode = ? ) ) ";

            //LinhNBV start modified on May 23 2018: change saleTransId to saleTransOrderId
            String queryString = "from SaleTransDetailOrder where saleTransOrderId in   ( select saleTransOrderId from SaleTransOrder where saleTransCode = ? )";
            //end.
            Query queryObject = null;

            queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, saleTransCode);
            list = queryObject.list();

            if (list == null || list.isEmpty() || list.size() <= 0) {
                alreadlySerial = false;
            } else {
                alreadlySerial = true;
            }

            String queryString1 = "from SaleTransSerialOrder where saleTransDetailId = ? ";
            for (int i = 0; i <= list.size(); i++) {
                queryObject = getSession().createQuery(queryString1);
                queryObject.setParameter(0, list.get(i).getSaleTransDetailId());
                list1 = queryObject.list();
                if (list1 == null || list1.isEmpty() || list1.size() <= 0) {
                    alreadlySerial = false;
                    break;
                } else {
                    alreadlySerial = true;
                }
            }

        } catch (Exception ex) {
            log.error(" Begin method checkAlreadlyHaveSerial ; Exception : " + ex);
        }
        return alreadlySerial;
    }

    public void updateBookStatus(Long stockTransId, Long status) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        try {
            strBuilder = new StringBuilder();
            strBuilder.append("UPDATE account_book ");
            strBuilder.append("   SET status = ?, ");
            strBuilder.append("       return_date = sysdate ");
            strBuilder.append(" WHERE stock_trans_id = ? ");

            stmt = getSession().connection().prepareStatement(strBuilder.toString());
            stmt.setLong(1, status);
            stmt.setLong(2, stockTransId);

            stmt.executeUpdate();

        } catch (Exception re) {
            throw re;
        } finally {
            stmt.close();
        }
    }

    public void updateSaleTransOrder(SaleManagmentForm saleManagmentForm, String userName) throws Exception {
        log.debug("# Begin method updateSaleTransOrder");
        PreparedStatement stmt = null;
        StringBuilder strBuilder = null;
        try {

            if (saleManagmentForm.getOrderCode() == null || saleManagmentForm.getImageUrl() == null || saleManagmentForm.getSaleTransCode() == null) {
                return;
            }

            strBuilder = new StringBuilder();
            strBuilder.append("UPDATE sale_trans_order ");
            strBuilder.append("   SET order_code = ?, ");
            strBuilder.append("       scan_path = ?, ");
            strBuilder.append("       bank_name = ?, ");
            strBuilder.append("       amount = ?, ");
            strBuilder.append("       list_bank_documents = ?, ");
            strBuilder.append("       order_code2 = ?, ");
            strBuilder.append("       bank_name2 = ?, ");
            strBuilder.append("       amount2 = ?, ");
            strBuilder.append("       order_code3 = ?, ");
            strBuilder.append("       bank_name3 = ?, ");
            strBuilder.append("       amount3 = ? ");
            strBuilder.append(" WHERE sale_trans_code = ? ");

            stmt = getSession().connection().prepareStatement(strBuilder.toString());
            stmt.setString(1, saleManagmentForm.getOrderCode());
            stmt.setString(2, saleManagmentForm.getImageUrl().replace(saleManagmentForm.getImageUrl().substring(saleManagmentForm.getImageUrl().lastIndexOf("_") + 1, saleManagmentForm.getImageUrl().lastIndexOf(".")), userName));
            stmt.setString(3, saleManagmentForm.getBankName());
            stmt.setString(4, saleManagmentForm.getAmount());
            stmt.setString(5, saleManagmentForm.getListBankDocuments());
            stmt.setString(6, saleManagmentForm.getOrderCode2());
            stmt.setString(7, saleManagmentForm.getBankName2());
            stmt.setString(8, saleManagmentForm.getAmount2());
            stmt.setString(9, saleManagmentForm.getOrderCode3());
            stmt.setString(10, saleManagmentForm.getBankName3());
            stmt.setString(11, saleManagmentForm.getAmount3());
            stmt.setString(12, saleManagmentForm.getSaleTransCode());

            stmt.executeUpdate();

        } catch (Exception re) {
            log.error(" Begin method updateSaleTransOrder ; Exception : " + re);
            throw re;
        } finally {
            stmt.close();
        }
    }

    /**
     * author tannh date create : 12/11/2017
     *
     * @return Ham upload file
     *
     */
    public static boolean uploadListFileFromFTPServer(String host, String username, String password, List<String> fileName, String destinationDir, String tempDir, String userName) throws Exception {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        String fileNameDir = "";
        try {

            // connect, login, set timeout
            client.connect(host);
            client.login(username, password);

            // set transfer type
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
            // set timeout
            client.setSoTimeout(60000); // default 1 minute

            // check connection
            int reply = client.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                // switch to working folder
                client.changeWorkingDirectory("ACCOUNT_TRANS");
//                client.changeWorkingDirectory(destinationDir);
                //new forder By DateTime

                // create temp file on temp directory
                for (int i = 0; i < fileName.size(); i++) {
                    String lStr;
                    if (fileName.get(i).contains("\\")) {
                        lStr = fileName.get(i).substring(fileName.get(i).lastIndexOf("\\"));
                    } else {
                        lStr = fileName.get(i).substring(fileName.get(i).lastIndexOf("/"));
                    }

                    lStr = lStr.substring(1);
                    long fileSize = new File(fileName.get(i)).length();
                    if (fileSize <= 0 || fileSize > (5 * 1024 * 1024)) {
                        return false;
                    }
                    InputStream inputStream = new FileInputStream(new File(fileName.get(i)));
                    client.storeFile(lStr, inputStream);
                    String fixedPath = lStr.substring(0, lStr.lastIndexOf("/") + 1 + 14);// 14 mean: yyyymmddhhmmss
                    System.out.println("After rename: " + lStr.replace(lStr.substring(fixedPath.length() + 1, lStr.lastIndexOf(".")), userName));
                    client.rename(lStr, lStr.replace(lStr.substring(fixedPath.length() + 1, lStr.lastIndexOf(".")), userName));
                }
                // close connection
                client.logout();
            } else {
                client.disconnect();
                System.err.println("FTP server refused connection !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            client.disconnect();
        }
        return true;
    }

    public String callWSSOAPcheckTrans(String transCode, String bankName, String amount, UserToken userToken) {
        bankName = StringUtils.substring(bankName, 0, 3);
        String wsdlIM = ResourceBundleUtils.getResource("urlBankManager");
        org.apache.commons.httpclient.HttpClient httpTransport = null;
        MultiThreadedHttpConnectionManager conMgr = null;
        //innit connection
        if (conMgr == null) {
            conMgr = new MultiThreadedHttpConnectionManager();
            conMgr.setMaxConnectionsPerHost(20000);
            conMgr.setMaxTotalConnections(20000);
        }
        if (httpTransport == null) {
            httpTransport = new org.apache.commons.httpclient.HttpClient(conMgr);
            HttpConnectionManager conMgr1 = httpTransport.getHttpConnectionManager();
            HttpConnectionManagerParams conPars = conMgr1.getParams();
            conPars.setMaxTotalConnections(2000);
            conPars.setConnectionTimeout(30000); //timeout ket noi : ms
            conPars.setSoTimeout(60000); //timeout doc ket qua : ms
        }

        PostMethod post = new PostMethod(wsdlIM);
        String soapResponse = "";
        String result = "";
        long start = System.currentTimeMillis();
        String userName = "00797ce8fadf77a6e0f9019ba58022f5";
        String pass = "00797ce8fadf77a6f13baddb16669dfc6a6c7d6a93c496cb";
        try {
            String request = " <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.wsfw.vas.viettel.com/\">\n"
                    + "   <soapenv:Header/>\n"
                    + "   <soapenv:Body>\n"
                    + "      <ser:checkTrans>\n"
                    + "         <!--Optional:-->\n"
                    + "         <transCode>" + transCode + "</transCode>\n"
                    + "         <!--Optional:-->\n"
                    + "         <bankName>" + bankName + "</bankName>\n"
                    + "         <!--Optional:-->\n"
                    + "         <amount>" + amount + "</amount>\n"
                    + "         <!--Optional:-->\n"
                    + "         <userName>" + userName + "</userName>\n"
                    + "         <!--Optional:-->\n"
                    + "         <passWord>" + pass + "</passWord>\n"
                    + "      </ser:checkTrans>\n"
                    + "   </soapenv:Body>\n"
                    + "</soapenv:Envelope> ";
            System.out.println("------------------------------------------------");
            System.out.println("------------------------------------------------");
            RequestEntity entity = new StringRequestEntity(request, "text/xml", "UTF-8");
            post.setRequestEntity(entity);
            httpTransport.executeMethod(post);
            soapResponse = post.getResponseBodyAsString(609600000);
            //Parse reponse
            XmlConfig soapMsg = new XmlConfig();
            soapMsg.load(soapResponse, null);
            Element root = soapMsg.getDocument().getRootElement();
            Element ele = XmlUtil.findElement(root, "errorCode");
            Element ele2 = XmlUtil.findElement(root, "description");
            result += ele.getText();
//            logTime("Time to callMoneyForsale  "  + " result: " + result, start);
            return result;
        } catch (Exception ex) {
//            logTime("Exception to callMoneyForsale StaffCode:  " + userCode, start);
//            logger.error(AppManager.logException(start, ex));
            return result;
        } finally {
            post.releaseConnection();
        }
    }

    public String callWSSOAPupdateTrans(String transCode, String bankName, String amount, UserToken userToken, String staffCodeCreate) {
        bankName = StringUtils.substring(bankName, 0, 3);
        String wsdlIM = ResourceBundleUtils.getResource("urlBankManager");
        org.apache.commons.httpclient.HttpClient httpTransport = null;
        MultiThreadedHttpConnectionManager conMgr = null;
        //innit connection
        if (conMgr == null) {
            conMgr = new MultiThreadedHttpConnectionManager();
            conMgr.setMaxConnectionsPerHost(20000);
            conMgr.setMaxTotalConnections(20000);
        }
        if (httpTransport == null) {
            httpTransport = new org.apache.commons.httpclient.HttpClient(conMgr);
            HttpConnectionManager conMgr1 = httpTransport.getHttpConnectionManager();
            HttpConnectionManagerParams conPars = conMgr1.getParams();
            conPars.setMaxTotalConnections(2000);
            conPars.setConnectionTimeout(30000); //timeout ket noi : ms
            conPars.setSoTimeout(60000); //timeout doc ket qua : ms
        }

        PostMethod post = new PostMethod(wsdlIM);
        String soapResponse = "";
        String result = "";
        long start = System.currentTimeMillis();
        String userName = "00797ce8fadf77a6e0f9019ba58022f5";
        String pass = "00797ce8fadf77a6f13baddb16669dfc6a6c7d6a93c496cb";
        try {
            String request = " <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.wsfw.vas.viettel.com/\">\n"
                    + "   <soapenv:Header/>\n"
                    + "   <soapenv:Body>\n"
                    + "      <ser:updateTrans>\n"
                    + "         <!--Optional:-->\n"
                    + "         <transCode>" + transCode + "</transCode>\n"
                    + "         <!--Optional:-->\n"
                    + "         <bankName>" + bankName + "</bankName>\n"
                    + "         <!--Optional:-->\n"
                    + "         <amount>" + amount + "</amount>\n"
                    + "         <!--Optional:-->\n"
                    + "         <userName>" + userName + "</userName>\n"
                    + "         <!--Optional:-->\n"
                    + "         <passWord>" + pass + "</passWord>\n"
                    + "         <staffCreate>" + staffCodeCreate + "</staffCreate>\n"
                    + "         <staffApprove>" + userToken.getLoginName() + "</staffApprove>\n"
                    + "         <purpose>1</purpose> "
                    + "      </ser:updateTrans>\n"
                    + "   </soapenv:Body>\n"
                    + "</soapenv:Envelope> ";

            RequestEntity entity = new StringRequestEntity(request, "text/xml", "UTF-8");
            post.setRequestEntity(entity);
            httpTransport.executeMethod(post);
            soapResponse = post.getResponseBodyAsString(609600000);
            //Parse reponse
            XmlConfig soapMsg = new XmlConfig();
            soapMsg.load(soapResponse, null);
            Element root = soapMsg.getDocument().getRootElement();
            Element ele = XmlUtil.findElement(root, "errorCode");
            Element ele2 = XmlUtil.findElement(root, "description");
            result += ele.getText();
//            logTime("Time to callMoneyForsale  "  + " result: " + result, start);
            return result;
        } catch (Exception ex) {
//            logTime("Exception to callMoneyForsale StaffCode:  " + userCode, start);
//            logger.error(AppManager.logException(start, ex));
            return result;
        } finally {
            post.releaseConnection();
        }
    }

    /**
     * ThanhNC, 20/06/2009 Lay staff available bang staff_code
     *
     * @param staffId
     * @return
     * @throws java.lang.Exception
     */
    public Staff findAvailableByStaffId(Long staffId) throws Exception {
        log.debug("finding all findAvailableByStaffCode");
        try {
            if (staffId == null) {
                return null;
            }
            String queryString = "from Staff a where a.status = ? and a.staffId= ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, staffId);
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (Staff) lst.get(0);
            }
            return null;
        } catch (HibernateException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * Tannh, 31/08/2018 Lay BankTranInfo available bang TranferCode
     *
     * @param TranferId
     * @return
     * @throws java.lang.Exception
     */
    public BankTranferInfo findAvailableByTranferCode(Long TranferId) throws Exception {
        log.debug("finding all findAvailableByTranferCode");
        try {
            if (TranferId == null || TranferId == 0L) {
                return null;
            }
            String queryString = "from BankTranferInfo  where status = 1 and bankTranferInfoId = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, TranferId);
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (BankTranferInfo) lst.get(0);
            }
            return null;
        } catch (Exception re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public BankTranferInfo findWaitingByTranferCode(Long TranferId) throws Exception {
        log.debug("finding all findAvailableByTranferCode");
        try {
            if (TranferId == null || TranferId == 0L) {
                return null;
            }
            String queryString = "from BankTranferInfo  where status = 4 and bankTranferInfoId = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, TranferId);
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (BankTranferInfo) lst.get(0);
            }
            return null;
        } catch (Exception re) {
            log.error("find all failed", re);
            throw re;
        }
    }
//   public String callWSSOAPcheckTrans(String transCode, String bankName, String amount, UserToken userToken) {
//        bankName = StringUtils.substring(bankName, 0, 3);
//        String wsdlIM = "http://10.229.40.206:10322/BankTranfer?wsdl";
//        org.apache.commons.httpclient.HttpClient httpTransport = null;
//        MultiThreadedHttpConnectionManager conMgr = null;
//        //innit connection
//        if (conMgr == null) {
//            conMgr = new MultiThreadedHttpConnectionManager();
//            conMgr.setMaxConnectionsPerHost(20000);
//            conMgr.setMaxTotalConnections(20000);
//        }
//        if (httpTransport == null) {
//            httpTransport = new org.apache.commons.httpclient.HttpClient(conMgr);
//            HttpConnectionManager conMgr1 = httpTransport.getHttpConnectionManager();
//            HttpConnectionManagerParams conPars = conMgr1.getParams();
//            conPars.setMaxTotalConnections(2000);
//            conPars.setConnectionTimeout(30000); //timeout ket noi : ms
//            conPars.setSoTimeout(60000); //timeout doc ket qua : ms
//        }
//
//        PostMethod post = new PostMethod(wsdlIM);
//        String soapResponse = "";
//        String result = "";
//        long start = System.currentTimeMillis();
//        String userName = "00797ce8fadf77a6e0f9019ba58022f5";
//        String pass = "00797ce8fadf77a6f13baddb16669dfc6a6c7d6a93c496cb";
//        try {
//            String request =
//                    " <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.wsfw.vas.viettel.com/\">\n"
//                    + "   <soapenv:Header/>\n"
//                    + "   <soapenv:Body>\n"
//                    + "      <ser:checkTrans>\n"
//                    + "         <!--Optional:-->\n"
//                    + "         <transCode>" + transCode + "</transCode>\n"
//                    + "         <!--Optional:-->\n"
//                    + "         <bankName>" + bankName + "</bankName>\n"
//                    + "         <!--Optional:-->\n"
//                    + "         <amount>" + amount + "</amount>\n"
//                    + "         <!--Optional:-->\n"
//                    + "         <userName>" + userName + "</userName>\n"
//                    + "         <!--Optional:-->\n"
//                    + "         <passWord>" + pass + "</passWord>\n"
//                    + "      </ser:checkTrans>\n"
//                    + "   </soapenv:Body>\n"
//                    + "</soapenv:Envelope> ";
//            System.out.println("------------------------------------------------");
//            System.out.println("------------------------------------------------");
//            RequestEntity entity = new StringRequestEntity(request, "text/xml", "UTF-8");
//            post.setRequestEntity(entity);
//            httpTransport.executeMethod(post);
//            soapResponse = post.getResponseBodyAsString(609600000);
//            //Parse reponse
//            XmlConfig soapMsg = new XmlConfig();
//            soapMsg.load(soapResponse, null);
//            Element root = soapMsg.getDocument().getRootElement();
//            Element ele = XmlUtil.findElement(root, "errorCode");
//            Element ele2 = XmlUtil.findElement(root, "description");
//            result += ele.getText();
////            logTime("Time to callMoneyForsale  "  + " result: " + result, start);
//            return result;
//        } catch (Exception ex) {
////            logTime("Exception to callMoneyForsale StaffCode:  " + userCode, start);
////            logger.error(AppManager.logException(start, ex));
//            return result;
//        } finally {
//            post.releaseConnection();
//        }
//    }
}
