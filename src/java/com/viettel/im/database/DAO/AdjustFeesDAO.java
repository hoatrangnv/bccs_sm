/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.util.DateTimeUtils;
import com.viettel.database.DAO.BaseDAOAction;

import com.viettel.im.client.bean.CalculateFeeBean;
import com.viettel.im.client.form.AdjustFeesForm;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
/**
 * @author tuan
 * @updated anhlt 05/05/2009
 */
public class AdjustFeesDAO extends BaseDAOAction {

    private String errorPage = "error";
    private String login = "loginError";
    private AdjustFeesForm adjustFeesForm;
    private static final Log log = LogFactory.getLog(UpdFeesDAO.class);
    private Map listShopID = new HashMap();
    private Map shopName = new HashMap();
    protected static final String USER_TOKEN_ATTRIBUTE = "userToken";
    public String pageForward;
    public static final String ADJ_FEE = "adjustFeesPrepare";

    public AdjustFeesForm getAdjustFeesForm() {
        return adjustFeesForm;
    }

    public void setAdjustFeesForm(AdjustFeesForm adjustFeesForm) {
        this.adjustFeesForm = adjustFeesForm;
    }
    

    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        adjustFeesForm=new AdjustFeesForm();
        pageForward = ADJ_FEE;
        String checkCOMM = "1";
        try {
            session.setAttribute("lstChannelType", getChanelType(checkCOMM));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return pageForward;
    }

    /**
     * @return the listShopID
     */
    public Map getListShopID() {
        return listShopID;
    }

    /**
     * @param listShopID the listShopID to set
     */
    public void setListShopID(Map listShopID) {
        this.listShopID = listShopID;
    }

    /**
     * @return the shopName
     */
    public Map getShopName() {
        return shopName;
    }

    /**
     * @param shopName the shopName to set
     */
    public void setShopName(Map shopName) {
        this.shopName = shopName;
    }

    /**
     * @anhlt Get list all chanel type with COMM_FEE
     * @return
     */
    public List getChanelType(String checkComm) throws Exception {
        log.debug("get getChannelType by CHECK_COMM");
        try {
            Session session = getSession();
            String queryString = "from ChannelType where status=1 AND checkComm = ?";
            Query queryObject = session.createQuery(queryString);
            if (checkComm != "0") {
                queryObject.setParameter(0, checkComm);
            }
            log.debug("get successful");
            return queryObject.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public String getShopCodeADJ() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            HttpSession session = httpServletRequest.getSession();
            String shopCode = httpServletRequest.getParameter("adjustFeesForm.shopCode");
            List<Shop> listShop = new ArrayList();
            if (shopCode.length() > 0) {
                if ("".equals(shopCode)) {
                    return "success";
                }
                //Long shopID = Long.parseLong(shopCode);
                UserToken userToken = (UserToken) session.getAttribute(USER_TOKEN_ATTRIBUTE);
                Long userID = userToken.getUserID();
                if (userID == null) {
                    return "errorPage";
                }
                Long shopId = getShopIDByStaffID(userID);
                List parameterList = new ArrayList();
                String queryString = "from Shop ";
                queryString += " where parentShopId = ? ";
                parameterList.add(shopId);
                queryString += " and shopCode Like ? ";
                parameterList.add("%" + shopCode + "%");

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                if (!queryObject.list().isEmpty()) {
                    listShop = queryObject.list();

                    for (int i = 0; i < listShop.size(); i++) {
                        listShopID.put(listShop.get(i).getShopCode(), listShop.get(i).getShopId());
                    }
                    return "success";
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return "success";
    }

    public String getShopNameTextFeeADJ() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            HttpSession session = httpServletRequest.getSession();
            String shopCode = httpServletRequest.getParameter("shopCode");
            List<Shop> listShop = new ArrayList();
            if (shopCode.length() > 0) {
                if ("".equals(shopCode)) {
                    return "success";
                }
                List parameterList = new ArrayList();
                String queryString = "from Shop ";
                queryString += " where shopId = ? ";
                parameterList.add(Long.parseLong(shopCode));

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                if (!queryObject.list().isEmpty()) {
                    listShop = queryObject.list();
                    shopName.put("shopName", listShop.get(0).getName());
                    return "success";
                }
            }

        } catch (Exception ex) {
            throw ex;
        }
        return "success";
    }

    /**
     * GET SHOP_ID BY STAFF LOGIN
     * @param staffID
     * @return SHOPID
     */
    protected Long getShopIDByStaffID(Long staffID) {
        log.debug("get shop ID by staff ID");
        try {
            String queryString = "from Staff where staffId = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, staffID);
            if (queryObject.list() != null && queryObject.list().size() > 0) {
                Staff staff = (Staff) queryObject.list().get(0);
                return staff.getShopId();
            }
            log.debug("get successful");
            return null;
        } catch (RuntimeException re) {
            log.error("get fail", re);
            throw re;
        }
    }

    protected Long getShopIDByShopCode(String shopCode) {
        log.debug("get shop ID by staff ID");
        try {
            String queryString = "from Shop where shopCode = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, shopCode);
            if (queryObject.list() != null && queryObject.list().size() > 0) {
                Shop shp = (Shop) queryObject.list().get(0);
                return shp.getShopId();
            }
            log.debug("get successful");
            return null;
        } catch (RuntimeException re) {
            log.error("get fail", re);
            throw re;
        }
    }

     /**
     * @author: anhlt - Calculate FEE
     * @param channelType
     * @param shopID
     * @param billCycleDate
     * @param stDate
     * @param endDate
     * @return
     */
    public List<CalculateFeeBean> calculateFEE(Long channelType, Long shopID, Date billCycleDate, Date stDate, Date endDate) {
        log.info("get list COMM_TRANSACTION");
        try {
            Session session = getSession();
            List parameterList = new ArrayList();
            String strQuery = "SELECT " +
                    "item.item_id AS itemid," +
                    "item.item_name AS itemname," +
                    "fee.fee AS fee," +
                    "fee.vat AS vat," +
                    "comm.comm_trans_id AS commtransid," +
                    "comm.invoice_used_id AS invoiceid," +
                    "comm.bill_cycle AS billcycle," +
                    "to_date(comm.create_date,'dd/mm/yyyy') AS createDate," +
                    "comm.quantity AS quantity," +
                    "comm.status as status," +
                    "comm.pay_status as paystatus," +
                    "(comm.quantity * fee.fee) AS totalmoney," +
                    "comm.tax_money as taxMoney" +
                    " FROM comm_transaction comm, comm_item_fee_channel fee, comm_items item " +
                    "WHERE comm.fee_id = fee.item_fee_channel_id " +
                    "AND item.item_id = comm.item_id " +
                    "AND item.item_id = fee.item_id " +
                    "AND comm.channel_type_id=? " +
                    "AND comm.shop_id=? " +
                    "AND comm.bill_cycle< ? " +
                    "AND fee.sta_date < ? " +
                    "AND fee.end_date > ? " +
                    "AND item.status = 1 " +
                    "AND comm.pay_status = 4 " +
                    "OR comm.pay_status = 3";

            parameterList.add(channelType);
            parameterList.add(shopID);
            parameterList.add(billCycleDate);
            parameterList.add(stDate);
            parameterList.add(endDate);
            Query queryObject = session.createSQLQuery(strQuery).addScalar("itemid", Hibernate.LONG).addScalar("itemname", Hibernate.STRING).addScalar("fee", Hibernate.LONG).addScalar("vat", Hibernate.LONG).addScalar("commtransid", Hibernate.LONG).addScalar("invoiceid", Hibernate.LONG).addScalar("billcycle", Hibernate.DATE).addScalar("createDate", Hibernate.DATE).addScalar("quantity", Hibernate.LONG).addScalar("totalmoney", Hibernate.LONG).addScalar("taxMoney", Hibernate.LONG).addScalar("status", Hibernate.STRING).addScalar("paystatus", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CalculateFeeBean.class));
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public String searchCOMM() throws Exception {
        log.info("Begin search CALCULATE FEE");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        try {
            Long shopID = 0L;
            Long channelType = 0L;
            AdjustFeesForm adForm = getAdjustFeesForm();
            String test = adForm.getShopCode();
            if (adForm.getShopCode().length() > 0) {

                shopID = getShopIDByShopCode(adForm.getShopCode());
            }
            channelType = adForm.getPayTypeCode();
            Date billcycle = DateTimeUtils.convertStringToDate(this.parseDate(Integer.parseInt(adForm.getMonth().toString())));
            List<CalculateFeeBean> lstView = this.calculateFEE(channelType, shopID, billcycle, billcycle, billcycle);
            req.setAttribute("listfees", null);
            req.setAttribute("listfees", lstView);
        /*List arTemp = new ArrayList();
        for (int idx = 0; idx < lstView.size(); idx++) {
        arTemp.add(lstView.get(idx).getCommtransid().toString());
        }
        if (arTemp.size() > 0) {
        upForm.setArrCOMMTRANSID(arTemp);
        }*/
        } catch (Exception ex) {
            pageForward = ADJ_FEE;
            ex.printStackTrace();
            throw ex;
        }
        pageForward = ADJ_FEE;
        return pageForward;
    }

    /**
     * @author anhlt on 28/04/2009
     * Parse Date data
     * @param dateInput
     * @return
     */
    public String parseDate(int dateInput) {
        String dateReturn = "01/01/";
        Calendar cal = Calendar.getInstance();
        switch (dateInput) {
            case 1:
                dateReturn = "01/01/";
                break;
            case 2:
                dateReturn = "01/02/";
                break;
            case 3:
                dateReturn = "01/03/";
                break;
            case 4:
                dateReturn = "01/04/";
                break;
            case 5:
                dateReturn = "01/05/";
                break;
            case 6:
                dateReturn = "01/06/";
                break;
            case 7:
                dateReturn = "01/07/";
                break;
            case 8:
                dateReturn = "01/08/";
                break;
            case 9:
                dateReturn = "01/09/";
                break;
            case 10:
                dateReturn = "01/10/";
                break;
            case 11:
                dateReturn = "01/11/";
                break;
            case 12:
                dateReturn = "01/12/";
                break;
        }
        return dateReturn + cal.get(Calendar.YEAR);
    }
     public String pageNavigator() throws Exception {
        String forward = "pageNavigator";
        return forward;
    }
}
