/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.database.DAO.BaseDAOAction;

import com.viettel.im.client.bean.CalculateFeeBean;
import com.viettel.im.client.form.CalculateFeesForm;

import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.CommItemFeeChannel;
import com.viettel.im.database.BO.CommTransaction;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * CalculateFeesDAO
 * @author tuan
 * @update by: anhlt
 */
public class CalculateFeesDAO extends BaseDAOAction {

    private String errorPage = "error";
    private String login = "loginError";
    public String pageForward;
    public static final String CAL_FEE = "calculatePrepare";
    private CalculateFeesForm calculateFeesForm;
    private CommonDAO commonDAO = new CommonDAO();
    private static final Log log = LogFactory.getLog(CalculateFeesDAO.class);
    private Map listShopID = new HashMap();
    private Map shopName = new HashMap();
    protected static final String USER_TOKEN_ATTRIBUTE = "userToken";

    public CalculateFeesForm getCalculateFeesForm() {
        return calculateFeesForm;
    }

    public void setCalculateFeesForm(CalculateFeesForm calculateFeesForm) {
        this.calculateFeesForm = calculateFeesForm;
    }

    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        calculateFeesForm = new CalculateFeesForm();
        try {
            req.getSession().removeAttribute("lstCalulateUp");
            req.getSession().removeAttribute("lstChannelType");
            req.getSession().removeAttribute("lstItem");
            session.setAttribute("lstChannelType", commonDAO.getChanelType());
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
        pageForward = CAL_FEE;
        return pageForward;
    }

    /**
     * @author anhlt
     * @return
     * @throws java.lang.Exception
     */
    public String updateCOMM() throws Exception {
        log.info("Begin search CALCULATE FEE");
        HttpServletRequest req = getRequest();
        try {
            CalculateFeesForm calForm = getCalculateFeesForm();
            List lstTemp = new ArrayList();
            lstTemp = calForm.getArrCOMMTRANSID();
            if (lstTemp.size() > 0) {
                for (int i = 0; i < lstTemp.size(); i++) {
                    String strGet = lstTemp.get(i).toString();
                    strGet = this.removeCharAt(strGet, 0);
                    strGet = this.removeCharAt(strGet, strGet.length() - 1);
                    //@anhlt: Get CommtransactionID
                    String[] arrSTR = strGet.split(", ");
                    for (int j = 0; j <= arrSTR.length - 1; j++) {
                        String getID = arrSTR[j].toString();
                        CommTransaction coBO = new CommTransaction();
                        CommTransactionDAO coDAO = new CommTransactionDAO();
                        coDAO.setSession(getSession());
                        coBO = coDAO.findById(Long.parseLong(getID));
                        //@anhlt Get COMM_ITEM_FEE_CHANNEL 
                        CommItemFeeChannel feeBO = new CommItemFeeChannel();
                        CommItemFeeChannelDAO feeDAO = new CommItemFeeChannelDAO();
                        feeDAO.setSession(getSession());
                        feeBO = feeDAO.findById(coBO.getFeeId());
                        //-----------------------------------------------------------
                        Long price = feeBO.getFee();
                        Long vat = feeBO.getVat();
                        Long Totalmoney = 0L;
                        Long quantityGet = coBO.getQuantity();
//                        Double tax_money_value = Double.parseDouble(ResourceBundleUtils.getResource("Tax_Money"));
                        if (vat == 0L) {
                            Totalmoney = price * quantityGet;
                        } else {
                            Totalmoney = quantityGet * (price * (vat / 100));
                        }
                        coBO.setTotalMoney(Totalmoney);
//                        Double taxMoney = Double.parseDouble(Totalmoney.toString()) * tax_money_value;
//                        coBO.setTaxMoney(Double.parseDouble(taxMoney.toString()));
                        coBO.setStatus(Constant.STATUS_USE);
                        getSession().update(coBO);
                        getSession().flush();
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        pageForward = CAL_FEE;
        return pageForward;
    }

    /**
     * Caculate data
     * @return
     * @throws java.lang.Exception
     */
    public String searchCOMM() throws Exception {
        log.info("Begin search CALCULATE FEE");
        pageForward = CAL_FEE;
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        try {
            CalculateFeesForm calForm = getCalculateFeesForm();
            //Kiem tra du lieu nhap
            if ((calForm.getPayTypeCode() == null) || (calForm.getPayTypeCode().equals(0L))) {

                req.setAttribute("message", "Bạn chưa chọn đối tượng ");
                return pageForward;

            }
            if ((calForm.getBillcycle() == null) || (calForm.getBillcycle().equals(""))) {

                req.setAttribute("message", "Bạn chưa chọn chu kỳ tính");
                return pageForward;

            }
            if ((calForm.getShopCode() == null) || (calForm.getShopCode().equals(""))) {

                req.setAttribute("message", "Bạn chưa nhập đối tượng");
                return pageForward;

            }
            int criterion = calForm.getCriterion();
            Long shopID = 0L;
            shopID = getShopIDByShopCode(calForm.getShopCode());
            Long channelType = 0L;
            channelType = calForm.getPayTypeCode();
            if (criterion == 1) {
                //Calculate by Quantity
                session.setAttribute("vQuantity", 1);
                session.setAttribute("vPrice", 0);
            } else if (criterion == 2) {
                //Calculate by Price -> Total Money
                session.setAttribute("vPrice", 1);
                session.setAttribute("vQuantity", 0);
            } else {
                //Calculate by ALL (Quantity & Money)
                session.setAttribute("vQuantity", 0);
                session.setAttribute("vPrice", 0);
            }

            Date date = DateTimeUtils.convertStringToDate(calForm.getBillcycle());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH) + 1;

            int year = cal.get(Calendar.YEAR);

            String monthyear = "0" + String.valueOf(month) + '/' + String.valueOf(year);
            if (!monthyear.equals("")) {
                List<CalculateFeeBean> lstView = commonDAO.calculateFEE(channelType, shopID, monthyear,null,null,"");
                if (lstView == null) {
                    req.setAttribute("message", "Không tìm thấy khoản mục nào");
                    return pageForward;
                }

                //Tính thành tiền cho khoản mục
                lstView = calFees(lstView);
                ////////////
                req.getSession().removeAttribute("lstCalulateUp");
                req.getSession().setAttribute("lstCalulateUp", lstView);

                List arTemp = new ArrayList();
                for (int idx = 0; idx < lstView.size(); idx++) {
                    arTemp.add(lstView.get(idx).getCommtransid().toString());
                }
                if (arTemp.size() > 0) {
                    calForm.setArrCOMMTRANSID(arTemp);
                }
            }
        } catch (Exception ex) {
            pageForward = CAL_FEE;
            ex.printStackTrace();
            throw ex;
        }
        pageForward = CAL_FEE;
        return pageForward;
    }
    /*
     * Author: Tuannv
     * Date created: 26/02/2009
     * Purpose: khoi tao tim kiem
     */

    public String resetForm() throws Exception {
        HttpServletRequest req = getRequest();
        calculateFeesForm.ResetFormSearch();
        req.getSession().removeAttribute("lstCalulateUp");
        req.setAttribute("status", "add");
        pageForward = CAL_FEE;
        return pageForward;


    }

    /**
     * @author tuannc
     * @return
     * @throws java.lang.Exception
     */
    public List<CalculateFeeBean> calFees(List<CalculateFeeBean> lstView) throws Exception {
        log.info("Begin search CALCULATE FEE");
        try {
            if (lstView.size() > 0) {
                for (int i = 0; i < lstView.size(); i++) {
                    CalculateFeeBean calFee = new CalculateFeeBean();
                    calFee = lstView.get(i);
                    Long totalMoney = calFee.getTotalmoney();
                    if ((totalMoney == null) || (totalMoney.equals(0L))) {//Neu khong co gia tri thanh tien thi tinh
                        Long comTransId = calFee.getCommtransid();

                        CommTransaction coBO = new CommTransaction();
                        CommTransactionDAO coDAO = new CommTransactionDAO();
                        coDAO.setSession(getSession());
                        coBO = coDAO.findById(comTransId);

                        //@tuannv Get COMM_ITEM_FEE_CHANNEL
                        CommItemFeeChannel feeBO = new CommItemFeeChannel();
                        CommItemFeeChannelDAO feeDAO = new CommItemFeeChannelDAO();
                        feeDAO.setSession(getSession());
                        if (coBO.getFeeId() != null) {
                            feeBO = feeDAO.findById(coBO.getFeeId());
                            //-----------------------------------------------------------
                            Long price = feeBO.getFee();
                            calFee.setFee(price);
                            Long vat = feeBO.getVat();
                            Long totalmoney = 0L;
                            Long quantityGet = coBO.getQuantity();
                            if (quantityGet != null) {
                                if (vat == 0L) {
                                    totalmoney = price * quantityGet;
                                } else {
                                    totalmoney = Math.round(quantityGet.doubleValue() * (price.doubleValue() * (vat.doubleValue() / 100)));
                                }
                                calFee.setTotalmoney(totalmoney);
                                lstView.set(i, calFee);
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        pageForward = CAL_FEE;
        return lstView;
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
    public List<CalculateFeeBean> calculateFEE(Long channelType, Long shopID, String monthyear) {
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
                    "comm.create_date AS createDate," +
                    "comm.quantity AS quantity," +
                    "comm.status as status," +
                    "comm.pay_status as paystatus," +
                    "(comm.quantity * fee.fee) AS totalmoney, " +
                    "comm.approved as approved, " +
                    "item.input_Type as inputType " +
                    " FROM comm_transaction comm, comm_item_fee_channel fee, comm_items item " +
                    "WHERE comm.fee_id = fee.item_fee_channel_id " +
                    "AND item.item_id = comm.item_id " +
                    "AND comm.pay_status = 0 " + //Chua duoc thanh toan
                    "AND comm.approved = 0 " + //Chua duoc phe duyet
                    "AND item.item_id = fee.item_id " +// @anhlt comment for itemID
                    "AND comm.channel_type_id=? " +
                    "AND comm.shop_id=? " +
                    "AND to_char(comm.bill_cycle,'mm/yyyy')= ? " +
                    "AND item.status = ?";
            parameterList.add(channelType);
            parameterList.add(shopID);
            parameterList.add(monthyear);
            parameterList.add(Constant.STATUS_USE);
            Query queryObject = session.createSQLQuery(strQuery).addScalar("itemid", Hibernate.LONG).addScalar("itemname", Hibernate.STRING).addScalar("fee", Hibernate.LONG).addScalar("vat", Hibernate.LONG).addScalar("commtransid", Hibernate.LONG).addScalar("invoiceid", Hibernate.LONG).addScalar("billcycle", Hibernate.DATE).addScalar("createDate", Hibernate.DATE).addScalar("quantity", Hibernate.LONG).addScalar("totalmoney", Hibernate.LONG).addScalar("inputType", Hibernate.STRING).addScalar("status", Hibernate.STRING).addScalar("approved", Hibernate.LONG).addScalar("paystatus", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CalculateFeeBean.class));
//            Query queryObject = session.createSQLQuery(strQuery).addScalar("itemid", Hibernate.LONG).addScalar("itemname", Hibernate.STRING).addScalar("fee", Hibernate.LONG).addScalar("vat", Hibernate.LONG).addScalar("commtransid", Hibernate.LONG).addScalar("invoiceid", Hibernate.LONG).addScalar("billcycle", Hibernate.DATE).addScalar("createDate", Hibernate.DATE).addScalar("quantity", Hibernate.LONG).addScalar("totalmoney", Hibernate.LONG).addScalar("inputType", Hibernate.STRING).addScalar("status", Hibernate.STRING).addScalar("approved", Hibernate.LONG).addScalar("paystatus", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CalculateFeeBean.class));
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @author anhlt
     * Remove special item in string
     * @param String
     * @param position need remove
     * @return
     */
    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    /**
     * @param shopID
     * @return shopName
     */
    public String getShopNameText() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
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

    public String pageNavigator() throws Exception {
        String forward = "pageNavigator";
        return forward;
    }
}
