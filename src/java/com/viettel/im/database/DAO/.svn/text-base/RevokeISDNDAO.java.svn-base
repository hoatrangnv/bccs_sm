/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.AutoCompleteSearchBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.StockISDNListBean;
import com.viettel.im.client.form.RevokeISDNForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 * RevokeISDNDAO class
 * @date 23/04/2009
 * @author ANHLT
 */
public class RevokeISDNDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(RevokeISDNDAO.class);
    private final int MAX_RESULT = 1000000;
    private final String REQUEST_MESSAGE = "message";
    private final String SESSION_LIST_ISDN_RANGE = "listrkISDN";
    private final BigInteger NUMBER_ROW_PER_SHEET = new BigInteger("50000"); //so luong row/ sheet
    private final String REQUEST_DETAIL_ISDN_RANGE_PATH = "detailIsdnRangePath";
    private final String REQUEST_DETAIL_ISDN_RANGE_MESSAGE = "detailIsdnRangeMessage";
    private final String REQUEST_DETAIL_ISDN_RANGE_MESSAGE_PARAM = "detailIsdnRangeMessageParam";
    public String pageForward;
    public final String RVK_ISDN = "revokeISDN";
    public final String RVK_ISDN_LIST = "listRevokeISDN";
    private RevokeISDNForm rkform = new RevokeISDNForm();
    private Map listShopID = new HashMap();
    private Map shopName = new HashMap();
    HashMap hashMapShopName = new HashMap();

    public HashMap getHashMapShopName() {
        return hashMapShopName;
    }

    public void setHashMapShopName(HashMap hashMapShopName) {
        this.hashMapShopName = hashMapShopName;
    }
    private Map hashMapShopCode = new HashMap();

    public Map getHashMapShopCode() {
        return hashMapShopCode;
    }

    public void setHashMapShopCode(Map hashMapShopCode) {
        this.hashMapShopCode = hashMapShopCode;
    }

    public RevokeISDNForm getRkform() {
        return rkform;
    }

    public void setRkform(RevokeISDNForm rkform) {
        this.rkform = rkform;
    }

    public String preparePage() throws Exception {
        log.debug("begin method preparePage of RevokeISDNDAO");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

//        //xoa bien session
//        req.getSession().removeAttribute(SESSION_LIST_ISDN_RANGE);
//        //tao form moi
//        this.rkform = new RevokeISDNForm();
//
//        log.debug("end method preparePage of RevokeISDNDAO");
        try {
            //xoa bien session
            req.getSession().setAttribute(SESSION_LIST_ISDN_RANGE, null);

            //kho mac dinh la kho cua user dang nhap
            this.rkform.setShopCode(userToken.getShopCode());
            this.rkform.setShopName(userToken.getShopName());


        } catch (Exception ex) {
            throw ex;
        }
        pageForward = RVK_ISDN;
        return pageForward;
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

    /**
     * GET LIST SHOP BY SHOP_ID
     * @param shopid
     * @return
     */
    public List getListShop(Long shopid) {
        List lstShop = new ArrayList();
        try {
            ShopDAO spDAO = new ShopDAO();
            spDAO.setSession(this.getSession());
            lstShop = spDAO.findChildShop(shopid);

        } catch (Exception ex) {
            log.error("Loi khi lay danh sach SHOP: " + ex.toString());
        }
        return lstShop;
    }

    /**
     *
     * thu hoi so
     *
     */
    public String revokeNumber() throws Exception {
        log.debug("begin method revokeNumber of RevokeISDNDAO");

        HttpServletRequest req = getRequest();

        //@anhlt: Fix shop revoke number from config.properties
        String ownerID = ResourceBundleUtils.getResource("REVOKE_SHOP_ID");
        if (ownerID == null || ownerID.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.listIsdnRangeEmpty");

            pageForward = RVK_ISDN;
            return pageForward;
        }
        //@anhlt: Reset status to new number


        List<RevokeISDNForm> listRevokeISDNForm = (List<RevokeISDNForm>) req.getSession().getAttribute(SESSION_LIST_ISDN_RANGE);
        if (listRevokeISDNForm == null || listRevokeISDNForm.size() == 0) {

            req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.listIsdnRangeEmpty");

            pageForward = RVK_ISDN;
            return pageForward;
        }
        Long[] arrSelectedFormId = this.rkform.getArrSelectedFormId();
        if (arrSelectedFormId == null || arrSelectedFormId.length == 0) {
            req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.listIsdnRangeEmpty");

            pageForward = RVK_ISDN;
            log.debug("end method assignIsdnType of AssignIsdnTypeDAO");
            return pageForward;
        }


        for (int i = 0; i < arrSelectedFormId.length; i++) {
            RevokeISDNForm revokeISDNForm = listRevokeISDNForm.get(arrSelectedFormId[i].intValue() - 1);////-1 do chi so mang bat dau tu 0, rownum bat dau tu 1
            Long stockTypeId = revokeISDNForm.getStockTypeId();
            String tableName = "StockIsdnMobile";
            if (Constant.STOCK_ISDN_MOBILE.equals(stockTypeId)) {
                tableName = "StockIsdnMobile";
            } else if (Constant.STOCK_ISDN_HOMEPHONE.equals(stockTypeId)) {
                tableName = "StockIsdnHomephone";
            } else if (Constant.STOCK_ISDN_PSTN.equals(stockTypeId)) {
                tableName = "StockIsdnPstn";
            }

            boolean hasIsdnType = false;
            if (revokeISDNForm.getIsdnType() != null && !revokeISDNForm.getIsdnType().equals("")) {
                hasIsdnType = true;
            }

            BigInteger startIsdn = new BigInteger(revokeISDNForm.getFromIsdn());
            BigInteger endIsdn = new BigInteger(revokeISDNForm.getToIsdn());

            String strQuery = "update " + tableName + " set ownerId = ?, status = ? " +
                    "where to_number(isdn) >= ? and to_number(isdn) <= ? " +
                    "and status = ? and ownerId = ? ";
            if (hasIsdnType) {
                strQuery += "and isdnType = ? ";
            }
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, Long.valueOf(ownerID));
            query.setParameter(1, Constant.STATUS_ISDN_NEW);
            query.setParameter(2, startIsdn);
            query.setParameter(3, endIsdn);
            query.setParameter(4, Constant.STATUS_ISDN_SUSPEND);
            query.setParameter(5, revokeISDNForm.getShopId());
            if (hasIsdnType) {
                query.setParameter(6, revokeISDNForm.getIsdnType());
            }

            int result = query.executeUpdate();
        }

        //xoa bien session
        req.getSession().removeAttribute(SESSION_LIST_ISDN_RANGE);
        //tao form moi
        this.rkform.resetForm();

        //kho mac dinh la kho cua user dang nhap
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        this.rkform.setShopCode(userToken.getShopCode());
        this.rkform.setShopName(userToken.getShopName());
        //
        req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.success");




//
//        Long statusNew = 1L;
//        String[] lst = rform.getAReceiveID();
//        try {
//            if (lst.length > 0) {
//                if (serviceType != null && !serviceType.equals("")) {
//                    if (new Long(serviceType).equals(MOBILE_STOCK_TYPE_ID)) {
//                        for (int i = 0; i <= lst.length - 1; i++) {
//                            String test = lst[i].toString();
//                            test = removeCharAt(test, 0);
//                            test = removeCharAt(test, test.length() - 1);
//                            String[] arrSTR = test.split(", ");
//                            for (int j = 0; j <= arrSTR.length - 1; j++) {
//                                String getID = arrSTR[j].toString();
//                                //@anhlt: Update data here
//                                StockIsdnMobile mBO = new StockIsdnMobile();
//                                StockIsdnMobileDAO mDAO = new StockIsdnMobileDAO();
//                                mDAO.setSession(getSession());
//                                mBO = mDAO.findByIds(Long.parseLong(getID));
//                                mBO.setOwnerId(Long.parseLong(ownerID));
//                                mBO.setStatus(Constant.STATUS_ISDN_NEW);
//                                mBO.setCreateDate(new Date());
//                                getSession().update(mBO);
//                                getSession().flush();
//                            //@anhlt: After update data
//                            }
//                        }
//                    //pageForward = serviceChange();
//
//                    } else if (new Long(serviceType).equals(HOMEPHONE_STOCK_TYPE_ID)) {
//                        for (int i = 0; i <= lst.length - 1; i++) {
//                            String test = lst[i].toString();
//                            test = removeCharAt(test, 0);
//                            test = removeCharAt(test, test.length() - 1);
//                            String[] arrSTR = test.split(", ");
//                            for (int j = 0; j <= arrSTR.length - 1; j++) {
//                                String getID = arrSTR[j].toString();
//                                //@anhlt: Update data here
//                                StockIsdnHomephone mBO = new StockIsdnHomephone();
//                                StockIsdnHomephoneDAO mDAO = new StockIsdnHomephoneDAO();
//                                mDAO.setSession(getSession());
//                                mBO = mDAO.findById(Long.parseLong(getID));
//                                mBO.setOwnerId(Long.parseLong(ownerID));
//                                mBO.setStatus(Constant.STATUS_ISDN_NEW);
//                                mBO.setCreateDate(new Date());
//                                getSession().update(mBO);
//                                getSession().flush();
//                            //@anhlt: After update data
//                            }
//                        }
//
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            log.debug("Loi khi thuc hien: " + ex.toString());
//        }
//        //pageForward = RVK_ISDN;
//        //@anhlt Reload page

        log.debug("end method revokeNumber of RevokeISDNDAO");

        pageForward = RVK_ISDN;
        return pageForward;
    }

    /**
     * Search ISDN
     * @return
     * @throws java.lang.Exception
     */
//    public String searchISDN() throws Exception {
//        HttpServletRequest req = getRequest();
//
//        try {
//            if (!checkValidIsdnRange()) {
//                pageForward = RVK_ISDN;
//                return pageForward;
//            }
//
//            List<RevokeISDNForm> listRevokeISDNForm = (List<RevokeISDNForm>) req.getSession().getAttribute(SESSION_LIST_ISDN_RANGE);
//            if(listRevokeISDNForm == null) {
//                listRevokeISDNForm = new ArrayList<RevokeISDNForm>();
//                req.getSession().setAttribute(SESSION_LIST_ISDN_RANGE, listRevokeISDNForm);
//            }
//
//            //tim maxId, tao id gia de phuc vu viec sua, xoa trong list
//            Long maxId = 0L;
//            for(int i = 0; i < listRevokeISDNForm.size(); i++) {
//                if(listRevokeISDNForm.get(i).getRevokeIsdnFormId().compareTo(maxId) > 0) {
//                    maxId = listRevokeISDNForm.get(i).getRevokeIsdnFormId();
//                }
//            }
//
//            //
//            RevokeISDNForm revokeIsdnForm = new RevokeISDNForm();
//            BeanUtils.copyProperties(revokeIsdnForm, this.rkform);
//            revokeIsdnForm.setRevokeIsdnFormId(maxId + 1);
//            listRevokeISDNForm.add(revokeIsdnForm);
//
//            this.rkform = new RevokeISDNForm();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw ex;
//        }

//
//
//
////        String ownerID = getShopIDByShopCode(rform.getShopCode()).toString();
////
////        //@anhlt Gan gia tri mac dinh cho Trang thai ngung su dung
////        Long status = Constant.STATUS_ISDN_SUSPEND;
////
////        try {
////            if (serviceType != null && !serviceType.equals("")) {
////                if (fromNumber.length() > 0 && toNumber.length() > 0 && isdnType != null) {
////                    if (new Long(serviceType).equals(MOBILE_STOCK_TYPE_ID)) {
////                        List<StockISDNListBean> Temp = this.getlistISDNMobile(Long.parseLong(ownerID), isdnType, fromNumber, toNumber, status);
////                        if (Temp.size() > 0) {
////                            String sFrom = Temp.get(0).getIsdn();
////                            String sTo = Temp.get(Temp.size() - 1).getIsdn();
////                            String count = String.valueOf(Temp.size());
////                            Temp.get(0).setFromNumber(sFrom);
////                            Temp.get(0).setToNumber(sTo);
////                            Temp.get(0).setCount(count);
////                            //@anhlt: Add item ID
////                            ArrayList lstID = new ArrayList();
////                            for (int j = 1; j <= Temp.size(); j++) {
////                                lstID.add(Temp.get(j - 1).getId());
////                            }
////                            Temp.get(0).setLstStringID(lstID);
////
////                            //@anhlt 08/05/2009
////                            if (!checkNumber(lstNumber, Temp)) {
////                                lstNumber.add(Temp.get(0));
////                                Temp.clear();
////                            } else {
////                                Temp.clear();
////                            }
////                            req.setAttribute("listrkISDN", null);
////                            req.setAttribute("listrkISDN", lstNumber);
////                        }
////                    } else if (new Long(serviceType).equals(HOMEPHONE_STOCK_TYPE_ID)) {
////                        List<StockISDNListBean> Temp = this.getlistISDNHomephone(Long.parseLong(ownerID), isdnType, fromNumber, toNumber, status);
////                        if (Temp.size() > 0) {
////                            String sFrom = Temp.get(0).getIsdn();
////                            String sTo = Temp.get(Temp.size() - 1).getIsdn();
////                            String count = String.valueOf(Temp.size());
////                            Temp.get(0).setFromNumber(sFrom);
////                            Temp.get(0).setToNumber(sTo);
////                            Temp.get(0).setCount(count);
////                            //@anhlt: Add item ID
////                            ArrayList lstID = new ArrayList();
////                            for (int j = 1; j <= Temp.size(); j++) {
////                                lstID.add(Temp.get(j - 1).getId());
////                            }
////                            Temp.get(0).setLstStringID(lstID);
////
////                            //@anhlt 08/05/2009
////                            if (!checkNumber(lstNumber, Temp)) {
////                                lstNumber.add(Temp.get(0));
////                                Temp.clear();
////                            } else {
////                                Temp.clear();
////                            }
////                            req.setAttribute("listrkISDN", null);
////                            req.setAttribute("listrkISDN", lstNumber);
////                        }
////                    }
////                } else {
////                    req.setAttribute("listrkISDN", null);
////                    lstNumber.clear();
////                }
////            } else {
////                req.setAttribute("listrkISDN", null);
////                lstNumber.clear();
////            }
////        } catch (Exception ex) {
////            log.debug("Loi khi tim kiem: " + ex.toString());
////            throw ex;
////        }
//        pageForward = RVK_ISDN;
//        return pageForward;
//    }
    /**
     * @author anhlt
     * @date 08/05/2009
     * @Note Kiem tra xem dai so da ton tai trong danh sach hay chua
     * @param lstNumber
     * @param lstCheck
     * @return
     */
    private Boolean checkNumber(List<StockISDNListBean> lstNumber, List<StockISDNListBean> lstCheck) {
        Boolean chk = false;
        if (lstNumber.size() > 0) {
            Long fN = Long.parseLong(lstCheck.get(0).getFromNumber());
            Long tN = Long.parseLong(lstCheck.get(0).getToNumber());
            for (int k = 0; k < lstNumber.size(); k++) {
                Long kFrom = Long.parseLong(lstNumber.get(k).getFromNumber());
                Long kTo = Long.parseLong(lstNumber.get(k).getToNumber());
                //@anhlt: Neu so kiem tra nam trong dai da ton tai
                //thi tra ve true.
                if (fN <= kFrom && tN <= kTo) {
                    chk = true;
                    //Thoat khoi de tra ve gia tri
                    break;
                } else {
                    chk = false;
                }
            }
        } else {
            chk = false;
        }
        return chk;
    }

    /* Get list data in STOCK_ISDN_HOMEPHONE
     * author anhlt
     * @return STOCK_ISDN_HOMEPHONE List
     */
    public List<StockISDNListBean> getlistISDNHomephone(Long owner_id, Long isdnType, String fromNumber, String toNumber, Long status) {
        log.debug("get StockISDNHomephone by Range Number");
        try {
            Session session = getSession();
            List parameterList = new ArrayList();
            String queryString = "select " +
                    "a.id as id, " +
                    "a.isdn as isdn, " +
                    "a.isdn_type as isdnType, " +
                    "a.owner_id as ownerId, " +
                    "a.status as status, " +
                    "a.create_date as createDate " +
                    "from stock_isdn_homephone a " +
                    " where a.owner_id=? " +
                    " and a.isdn_type=? " +
                    " and a.isdn>= ? " +
                    " and a.isdn<=? " +
                    " and a.status=?";
            parameterList.add(owner_id);
            parameterList.add(isdnType);
            parameterList.add(fromNumber);
            parameterList.add(toNumber);
            parameterList.add(status);
            Query queryObject = session.createSQLQuery(queryString).addScalar("id", Hibernate.LONG).addScalar("isdn", Hibernate.STRING).addScalar("isdnType", Hibernate.STRING).addScalar("status", Hibernate.LONG).addScalar("createDate", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(StockISDNListBean.class));
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /**
     * Get list data in STOCK_ISDN_MOBILE
     * @param owner_id
     * @return
     */
    public List<StockISDNListBean> getlistISDNMobile(Long owner_id, Long isdnType, String fromNumber, String toNumber, Long status) {
        log.debug("get StockISDNMobile by Range Number");
        try {
            Session session = getSession();
            List parameterList = new ArrayList();
            String queryString = "select " +
                    "a.id as id, " +
                    "a.isdn as isdn, " +
                    "a.isdn_type as isdnType, " +
                    "a.owner_id as ownerId, " +
                    "a.status as status, " +
                    "a.create_date as createDate " +
                    "from stock_isdn_mobile a " +
                    " where a.owner_id=? " +
                    " and a.isdn_type=? " +
                    " and a.isdn>= ? " +
                    " and a.isdn<=? " +
                    " and a.status=?";
            parameterList.add(owner_id);
            parameterList.add(isdnType);
            parameterList.add(fromNumber);
            parameterList.add(toNumber);
            parameterList.add(status);
            Query queryObject = session.createSQLQuery(queryString).addScalar("id", Hibernate.LONG).addScalar("isdn", Hibernate.STRING).addScalar("isdnType", Hibernate.STRING).addScalar("status", Hibernate.LONG).addScalar("createDate", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(StockISDNListBean.class));
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /**
     * Remove special item in string
     * @param String
     * @param position need remove
     * @return
     */
    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    public String getShopID() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            HttpSession session = httpServletRequest.getSession();
            String shopCode = httpServletRequest.getParameter("rkform.shopCode");

            if (shopCode != null && shopCode.trim().length() > 0) {
                UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
                Long userID = userToken.getUserID();
                Long userShopId = getShopIDByStaffID(userID);

                List parameterList = new ArrayList();
                String queryString = "from Shop ";
                queryString += " where 1 = 1 ";
//                queryString += " where parentShopId = ? ";
//                parameterList.add(userShopId);
                queryString += " and lower(shopCode) like ? ";
                parameterList.add("%" + shopCode.trim().toLowerCase() + "%");
                Query queryObject = getSession().createQuery(queryString);

                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                queryObject.setMaxResults(8);

                List<Shop> listShop = queryObject.list();
                if (listShop != null && listShop.size() > 0) {
                    for (int i = 0; i < listShop.size(); i++) {
                        listShopID.put(listShop.get(i).getShopId(), listShop.get(i).getShopCode());
                    }
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return "success";
    }

    public String getShopNameText() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String strShopId = httpServletRequest.getParameter("shopId");
            String target = httpServletRequest.getParameter("target");

            if (strShopId != null && strShopId.trim().length() > 0) {
                List parameterList = new ArrayList();
                String queryString = "from Shop ";
                queryString += " where shopId = ? ";
                parameterList.add(Long.parseLong(strShopId));

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }

                List<Shop> listShop = queryObject.list();
                if (listShop != null && listShop.size() > 0) {
                    shopName.put(target, listShop.get(0).getName());
                }
            }

        } catch (Exception ex) {
            throw ex;
        }

        return "success";
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

//    /**
//     *
//     * author tamdt1
//     * date: 22/05/2009
//     * kiem tra dai so co hop le hay khong
//     *
//     */
//    private boolean checkValidIsdnRange() throws Exception {
//        HttpServletRequest req = getRequest();
//
//        String shopCode = this.rkform.getShopCode();
//        String serviceType = this.rkform.getServiceType();
//        String isdnType = this.rkform.getISDNType();
//        String strStartIsdn = this.rkform.getFromNumber();
//        String strEndIsdn = this.rkform.getToNumber();
//
//        if (shopCode == null || shopCode.trim().equals("") ||
//                serviceType == null || serviceType.trim().equals("") ||
//                strStartIsdn == null || strStartIsdn.trim().equals("") ||
//                strEndIsdn == null || strEndIsdn.trim().equals("")) {
//
//            req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.requiredFieldsEmpty");
//            return false;
//        }
//
//        boolean hasIsdnType = false;
//        if (isdnType != null && !isdnType.trim().equals("")) {
//            hasIsdnType = true;
//        }
//
//        shopCode = shopCode.trim();
//        strStartIsdn = strStartIsdn.trim();
//        strEndIsdn = strEndIsdn.trim();
//
//        this.rkform.setShopCode(shopCode);
//        this.rkform.setFromNumber(strStartIsdn);
//        this.rkform.setToNumber(strEndIsdn);
//
//        BigInteger startIsdn;
//        BigInteger endIsdn;
//        try {
//            startIsdn = new BigInteger(strStartIsdn);
//            endIsdn = new BigInteger(strEndIsdn);
//        } catch (Exception ex) {
//            req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.isdnNegative");
//            return false;
//        }
//
//        //so dau dai + cuoi dai phai la so khogn am
//        if (startIsdn.compareTo(BigInteger.ZERO) < 0 || endIsdn.compareTo(BigInteger.ZERO) < 0) {
//            req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.isdnNegative");
//            return false;
//        }
//
//        //so dau dai khong duoc lon hon so cuoi dai
//        if (startIsdn.compareTo(endIsdn) > 0) {
//            req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.invalidIsdn");
//            return false;
//        }
//
//        //kiem tra dai so da ton tai trong list chua
//        List<RevokeISDNForm> listRevokeISDNForm = (List<RevokeISDNForm>) req.getSession().getAttribute(SESSION_LIST_ISDN_RANGE);
//        if (listRevokeISDNForm != null && listRevokeISDNForm.size() > 0) {
//            for (int i = 0; i < listRevokeISDNForm.size(); i++) {
//                RevokeISDNForm tmp = listRevokeISDNForm.get(i);
//                if (tmp.getShopCode().equals(shopCode) && tmp.getServiceType().equals(serviceType)) {
//                    BigInteger tmpStartIsdn = new BigInteger(tmp.getFromNumber());
//                    BigInteger tmpEndIsdn = new BigInteger(tmp.getToNumber());
//                    if ((tmpStartIsdn.compareTo(startIsdn) >= 0 && tmpEndIsdn.compareTo(endIsdn) <= 0) ||
//                            (tmpStartIsdn.compareTo(startIsdn) >= 0 && tmpEndIsdn.compareTo(endIsdn) <= 0)) {
//
//                        req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.duplicateIsdnRange");
//                        return false;
//                    }
//                }
//            }
//        }
//
//        //kiem tra dai so co ton tai hay khong trong CSDL khong
//        ShopDAO shopDAO = new ShopDAO();
//        shopDAO.setSession(this.getSession());
//        List<Shop> listShop = shopDAO.findByPropertyWithStatus(ShopDAO.SHOP_CODE, shopCode, Constant.STATUS_USE);
//        if (listShop == null || listShop.size() == 0) {
//            req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.commonShopNotExist");
//            return false;
//        }
//
//        Shop shop = listShop.get(0);
//
//
//        try {
//            String tableName = new BaseStockDAO().getTableNameByStockType(Long.valueOf(serviceType), BaseStockDAO.NAME_TYPE_HIBERNATE);
//            String strQuery = "select count(*) " +
//                    "from " + tableName + " " +
//                    "where to_number(isdn) >= ? and to_number(isdn) <= ? " +
//                    "and status = ? and ownerId = ? ";
//            if (hasIsdnType) {
//                strQuery += "and isdnType = ? ";
//            }
//            Query query = getSession().createQuery(strQuery);
//            query.setParameter(0, startIsdn);
//            query.setParameter(1, endIsdn);
//            query.setParameter(2, Constant.STATUS_ISDN_SUSPEND);
//            query.setParameter(3, shop.getShopId());
//            if (hasIsdnType) {
//                query.setParameter(4, isdnType);
//            }
//
//            List<Long> list = query.list();
//            Long count = list.get(0);
//            if (count.compareTo(0L) <= 0) {
//                //dai so khong ton tai
//                req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.serialRangeNotExist");
//                return false;
//            } else {
//                this.rkform.setIsdnQuantity(count);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            req.setAttribute(REQUEST_MESSAGE, "Lỗi!!! " + ex.toString());
//            return false;
//        }
//
//        return true;
//    }
    /**
     *
     * author   :  NamNX
     * date     : 11/01/2010
     * purpose  : kiem tra tinh hop le cua dai so truoc khi gan
     *
     */
    private boolean checkValidIsdnRange() {
        HttpServletRequest req = getRequest();

        try {
            String shopCode = this.rkform.getShopCode();
            Long stockTypeId = this.rkform.getStockTypeId();
            String strFromIsdn = this.rkform.getFromIsdn();
            String strToIsdn = this.rkform.getToIsdn();
            Long status = this.rkform.getStatus();
            String isdnType = this.rkform.getIsdnType();

            //kiem tra cac truong bat buoc
            if (stockTypeId == null ||
                    strFromIsdn == null || strFromIsdn.trim().equals("") ||
                    strToIsdn == null || strToIsdn.trim().equals("")) {

                req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.requiredFieldsEmpty");
                return false;
            }

            //kiem tra su ton tai cua stock_type_id
            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            if (strTableName == null || strTableName.equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.stockTypeTableNotExist");
                return false;
            }

            //kiem tra shop co ton tai khong
            Long ownerId = 0L;
            if (shopCode != null && !shopCode.trim().equals("")) {
                String strShopQuery = "from Shop where lower(shopCode) = ? ";
                Query shopQuery = getSession().createQuery(strShopQuery);
                shopQuery.setParameter(0, shopCode.trim().toLowerCase());
                List<Shop> listShop = shopQuery.list();
                if (listShop == null || listShop.size() == 0) {
                    req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.shopNotExist");
                    return false;
                }
                ownerId = listShop.get(0).getShopId();
            } else {
                shopCode = "";
            }

            //kiem tra truong fromIsdn, toIsdn phai la so khong am
            Long fromIsdn = 0L;
            Long toIsdn = 0L;
            try {
                fromIsdn = Long.valueOf(strFromIsdn.trim());
                toIsdn = Long.valueOf(strToIsdn.trim());
            } catch (NumberFormatException ex) {
                req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.isdnNegative");
                return false;
            }

            //kiem tra truong fromNumber khong duoc lon hon truong toNumber
            if (fromIsdn.compareTo(toIsdn) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.invalidIsdn");
                return false;
            }

            //kiem tra so luong so 1 lan tao dai so ko duoc vuot qua so luong max (hien tai la 2trieu so/lan)
            int maxIsdnAssignType = -1;
            try {
                String strMaxIsdnAssignType = ResourceBundleUtils.getResource("MAX_ISDN_ASSIGN_TYPE");
                maxIsdnAssignType = Integer.parseInt(strMaxIsdnAssignType.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                maxIsdnAssignType = -1;
            }

            if ((toIsdn - fromIsdn + 1) > maxIsdnAssignType) {
                req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.quantityOverMaximum");
                List listParam = new ArrayList();
                listParam.add(maxIsdnAssignType);
//                req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
                return false;
            }

            //
            this.rkform.setShopId(ownerId);

            //trim cac truong can thiet
            this.rkform.setShopCode(shopCode.trim());
            this.rkform.setFromIsdn(strFromIsdn.trim());
            this.rkform.setToIsdn(strToIsdn.trim());
            this.rkform.setStatus(status);
            this.rkform.setIsdnType(isdnType.trim());


            return true;

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Error function checkValidIsdnRange(): " + e.toString());
            return false;

        }
    }

    /**
     *
     * author tamdt1
     * date: 20/05/2009
     * xem danh sach chi tiet cac isdn cua 1 dai so
     *
     */
    public String detailIsdnRange() throws Exception {
        log.debug("begin method detailIsdnRange of RevokeISDNDAO");
        HttpServletRequest httpServletRequest = getRequest();

        pageForward = RVK_ISDN;

        String strRevokeIsdnFormId = httpServletRequest.getParameter("revokeIsdnFormId");
        Long revokeIsdnFormId = Long.valueOf(strRevokeIsdnFormId);

        //tim thong tin ve dai so can hien thi chi tiet
        RevokeISDNForm selectedRevokeISDNForm = null;
        List<RevokeISDNForm> listRevokeISDNForm = (List<RevokeISDNForm>) httpServletRequest.getSession().getAttribute(SESSION_LIST_ISDN_RANGE);
        if (listRevokeISDNForm != null && listRevokeISDNForm.size() > 0) {
            for (int i = 0; i < listRevokeISDNForm.size(); i++) {
                RevokeISDNForm revokeISDNForm = listRevokeISDNForm.get(i);
                if (revokeISDNForm.getRevokeIsdnFormId().equals(revokeIsdnFormId)) {
                    selectedRevokeISDNForm = revokeISDNForm;
                    break;
                }
            }
        }

        if (selectedRevokeISDNForm == null) {
            return pageForward;
        }

        String strFromIsdn = selectedRevokeISDNForm.getFromIsdn();
        String strToIsdn = selectedRevokeISDNForm.getToIsdn();
        Long stockTypeId = selectedRevokeISDNForm.getStockTypeId();

        BigInteger fromIsdn = new BigInteger(strFromIsdn);
        BigInteger toIsdn = new BigInteger(strToIsdn);

        boolean hasIsdnType = false;
        if (selectedRevokeISDNForm.getIsdnType() != null && !selectedRevokeISDNForm.getIsdnType().equals("")) {
            hasIsdnType = true;
        }

        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List<Shop> listShop = shopDAO.findByPropertyWithStatus(ShopDAO.SHOP_CODE, selectedRevokeISDNForm.getShopCode(), Constant.STATUS_USE);
        if (listShop == null || listShop.size() == 0) {
            httpServletRequest.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.commonShopNotExist");
            return pageForward;
        }

        Shop commonShop = listShop.get(0);

        //ghi du lieu ra file
        try {
            String DATE_FORMAT = "yyyyMMddHHmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            filePath = filePath != null ? filePath : "/";
            UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute("userToken");
            filePath += "revokeIsdn_detailIsdn_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".txt";
            String realPath = httpServletRequest.getSession().getServletContext().getRealPath(filePath);

            File fDetailIsdn = new File(realPath);
            PrintWriter output = new PrintWriter(fDetailIsdn);

            //tim thong tin chi tiet ve dai so
            String tableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_HIBERNATE);
            String strQuery = "select isdn from " + tableName + " " +
                    "where to_number(isdn) >= ? and to_number(isdn) <= ? " +
                    "and status = ? and ownerId = ? ";
            if (hasIsdnType) {
                strQuery += "and isdnType = ? ";
            }

            BigInteger currentIsdn = fromIsdn;
            do {
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, currentIsdn);
                query.setParameter(1, toIsdn);
                query.setParameter(2, Constant.STATUS_ISDN_SUSPEND); //tim cac so ngung su dung
                query.setParameter(3, commonShop.getShopId());
                if (hasIsdnType) {
                    query.setParameter(4, selectedRevokeISDNForm.getIsdnType());
                }
                query.setMaxResults(MAX_RESULT);
                List listStockIsdn = query.list();
                for (Object stockIsdn : listStockIsdn) {
                    String line = stockIsdn.toString();
                    output.println(line);
                }

                currentIsdn = currentIsdn.add(new BigInteger(String.valueOf(MAX_RESULT)));

            } while (currentIsdn.compareTo(toIsdn) < 0);

            output.flush();
            output.close();

            httpServletRequest.setAttribute(REQUEST_DETAIL_ISDN_RANGE_PATH, filePath);
            httpServletRequest.setAttribute(REQUEST_DETAIL_ISDN_RANGE_MESSAGE, "revokeIsdn.detailIsdnRangeMessage");

            List listMessageParam = new ArrayList();
            listMessageParam.add(fromIsdn);
            listMessageParam.add(toIsdn);
            httpServletRequest.setAttribute(REQUEST_DETAIL_ISDN_RANGE_MESSAGE_PARAM, listMessageParam);


        } catch (Exception ex) {
            ex.printStackTrace();
            httpServletRequest.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return pageForward;
        }

        //cmt, thay the bang viec ghi ra file text
//        try {
//            String DATE_FORMAT = "yyyyMMddHHmmss";
//            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
//            filePath = filePath != null ? filePath : "/";
//            UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute("userToken");
//            filePath += "revokeIsdn_detailIsdn_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
//            String realPath = httpServletRequest.getSession().getServletContext().getRealPath(filePath);
//
//            String templatePath = ResourceBundleUtils.getResource("LIST_ISDN_MOBILE_TEMPLATE_PATH");
//            if (templatePath == null || templatePath.trim().equals("")) {
//                //khong tim thay file template de xuat ket qua
//                httpServletRequest.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.isdnTemplateNotExist");
//                return pageForward;
//            }
//
//            String realTemplatePath = httpServletRequest.getSession().getServletContext().getRealPath(templatePath);
//            File fTemplateFile = new File(realTemplatePath);
//            if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
//                //khong tim thay file template de xuat ket qua
//                httpServletRequest.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.isdnTemplateNotExist");
//                return pageForward;
//            }
//
//            InputStream is = new BufferedInputStream(new FileInputStream(realTemplatePath));
//            XLSTransformer transformer = new XLSTransformer();
//            List sheetNames = new ArrayList();
//            List listStockIsdn = new ArrayList();
//
//            BigInteger isdnQuantity = toIsdn.subtract(fromIsdn).add(BigInteger.ONE); //so luong serial
//            BigInteger numberSheet = isdnQuantity.divide(NUMBER_ROW_PER_SHEET);
//            numberSheet = numberSheet.add(BigInteger.ONE); //them 1 sheet de chua cac phan tu con lai (trong truong hop co' du*)
//            BigInteger index = new BigInteger("0");
//            int indexSheet = 0;
//
//            while (index.compareTo(numberSheet) < 0) {
//                BigInteger tmpFromIsdn = fromIsdn.add(index.multiply(NUMBER_ROW_PER_SHEET));
//                BigInteger tmpToIsdn = tmpFromIsdn.add(NUMBER_ROW_PER_SHEET).subtract(BigInteger.ONE);
//                if (tmpToIsdn.compareTo(toIsdn) > 0) {
//                    //truong hop lan lap cuoi cung (so ban ghi le)
//                    tmpToIsdn = toIsdn;
//                }
//
//                //tim thong tin chi tiet ve dai so
//                String tableName = new BaseStockDAO().getTableNameByStockType(Long.valueOf(serviceType), BaseStockDAO.NAME_TYPE_HIBERNATE);
//                String strQuery = "from " + tableName + " " +
//                        "where to_number(isdn) >= ? and to_number(isdn) <= ? " +
//                        "and status = ? and ownerId = ? ";
//                if(hasIsdnType) {
//                    strQuery += "and isdnType = ? ";
//                }
//                Query query = getSession().createQuery(strQuery);
//                query.setParameter(0, tmpFromIsdn);
//                query.setParameter(1, tmpToIsdn);
//                query.setParameter(2, Constant.STATUS_ISDN_SUSPEND); //tim cac so ngung su dung
//                query.setParameter(3, commonShop.getShopId());
//                if(hasIsdnType) {
//                    query.setParameter(4, Constant.ISDN_TYPE_PRE_PAID);
//                }
//
//                List tmpListStockIsdn = query.list();
//
//                if (tmpListStockIsdn != null && tmpListStockIsdn.size() > 0) {
//                    sheetNames.add("sheet_" + index.toString());
//                    listStockIsdn.add(tmpListStockIsdn);
//                }
//
//                index = index.add(BigInteger.ONE);
//                indexSheet++;
//
//            }
//
//            Map map = new HashMap();
//            map.put("description", "Thu hồi số ngưng sử dụng");
//            //ngay tao, nguoi tao
//            map.put("dateCreate", new Date());
//            map.put("userCreate", userToken.getFullName());
//            //thong tin ve serial
//            map.put("fromIsdn", fromIsdn);
//            map.put("toIsdn", toIsdn);
//            map.put("isdnQuantity", isdnQuantity);
//
//            HSSFWorkbook resultWorkbook = transformer.transformMultipleSheetsList(is, listStockIsdn, sheetNames, "listIsdnMobile", map, 0);
//            OutputStream os = new BufferedOutputStream(new FileOutputStream(realPath));
//            resultWorkbook.write(os);
//            os.close();
//
//            httpServletRequest.setAttribute(REQUEST_DETAIL_ISDN_RANGE_PATH, filePath);
//            httpServletRequest.setAttribute(REQUEST_DETAIL_ISDN_RANGE_MESSAGE, "Thông tin chi tiết về dải isdn từ " + strFromIsdn + " đến " + strToIsdn);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            httpServletRequest.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
//            return pageForward;
//        }

        log.debug("end method detailIsdnRange of RevokeISDNDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 21/05/2009
     * xoa dai isdn khoi list
     *
     */
    public String delIsdnRange() throws Exception {
        log.debug("begin method delIsdnRange of RevokeISDNDAO");

        HttpServletRequest httpServletRequest = getRequest();
        pageForward = RVK_ISDN;

        try {
            String strRevokeIsdnFormId = httpServletRequest.getParameter("revokeIsdnFormId");
            Long revokeIsdnFormId = Long.valueOf(strRevokeIsdnFormId);

            //xoa du lieu trong bien session
            List<RevokeISDNForm> listRevokeISDNForm = (List<RevokeISDNForm>) httpServletRequest.getSession().getAttribute(SESSION_LIST_ISDN_RANGE);
            if (listRevokeISDNForm != null && listRevokeISDNForm.size() > 0) {
                for (int i = 0; i < listRevokeISDNForm.size(); i++) {
                    RevokeISDNForm revokeISDNForm = listRevokeISDNForm.get(i);
                    if (revokeISDNForm.getRevokeIsdnFormId().equals(revokeIsdnFormId)) {
                        listRevokeISDNForm.remove(i);
                        break;
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            httpServletRequest.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return pageForward;
        }

        log.debug("end method delIsdnRange of RevokeISDNDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 21/05/2009
     * phuc vu muc dich phan trang
     *
     */
    public String pageNagivator() throws Exception {
        log.debug("begin method pageNagivator of RevokeISDNDAO");

        pageForward = RVK_ISDN;

        log.debug("end method pageNagivator of RevokeISDNDAO");

        return pageForward;
    }

    /**
     *
     * author NamNX
     * date: 08/01/2010
     * tag F9
     *
     */
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach tat ca cac kho
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

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
     * author NamNX
     * date: 08/01/2010
     * Tim kiem dai so
     *
     */
    public String searchIsdnRange() throws Exception {
        log.info("begin method searchIsdnRange of RevokeISDNDAO...");

        try {
            HttpServletRequest req = getRequest();

            if (!checkValidIsdnRange()) {
                pageForward = RVK_ISDN;

                log.info("end method searchIsdnRange of RevokeISDNDAO");
                return pageForward;
            }


//            String serviceType = this.rkform.getServiceType();
            Long stockTypeId = this.rkform.getStockTypeId();
            Long shopId = this.rkform.getShopId();
            String isdnType = this.rkform.getIsdnType();
            String strFromIsdn = this.rkform.getFromIsdn();
            String strToIsdn = this.rkform.getToIsdn();
            Long status = this.rkform.getStatus();
            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            List listParameter = new ArrayList();

            //
            //cau lenh select ra cac khoang isdn theo min, max, isdn_type, status, owner_id
            StringBuffer strIsdnRangeQuery = new StringBuffer("");
            strIsdnRangeQuery.append("SELECT MIN(isdn) lb, MAX (isdn) ub, isdn_type, status, owner_id ");
            strIsdnRangeQuery.append("FROM (SELECT isdn isdn, ");
            strIsdnRangeQuery.append("          isdn - ROW_NUMBER () OVER (ORDER BY isdn) rn, ");
            strIsdnRangeQuery.append("          isdn_type, status, owner_id ");
            strIsdnRangeQuery.append("      FROM " + strTableName + " ");
            strIsdnRangeQuery.append("      WHERE 1 = 1 ");
            strIsdnRangeQuery.append("          and to_number(isdn) >= ? ");
            listParameter.add(Long.valueOf(strFromIsdn.trim()));
            strIsdnRangeQuery.append("          and to_number(isdn) <= ? ");
            listParameter.add(Long.valueOf(strToIsdn.trim()));

            if (shopId != null && shopId.compareTo(0L) > 0) {
                strIsdnRangeQuery.append("      and owner_id = ? and owner_type = ? ");
                listParameter.add(shopId);
                listParameter.add(Constant.OWNER_TYPE_SHOP);
            }

            if (isdnType != null && !isdnType.trim().equals("")) {
                if (isdnType.equals("-1")) {
                    strIsdnRangeQuery.append("      and isdn_type is null ");
                } else {
                    strIsdnRangeQuery.append("      and isdn_type = ? ");
                    listParameter.add(isdnType.trim());
                }
            }
            if (status != null) {
                strIsdnRangeQuery.append("      and status = ? ");
                listParameter.add(status);
            }



            strIsdnRangeQuery.append("      ) ");
            strIsdnRangeQuery.append("GROUP BY rn, isdn_type, status, owner_id ");
            strIsdnRangeQuery.append("ORDER BY 1 ");

            //join bang shop voi cau lenh tren de tim ra danh sach cac khoang isdn + thong tin ve kho chua isdn
            //ham khoi tao: revokeIsdnFormId(Long revokeIsdnFormId, String serviceType, String fromIsdn, String toIsdn, Long realQuantity, String isdnType, Long status, Long shopId, String shopCode, String shopName) {
            StringBuffer strSearchQuery = new StringBuffer("");
            strSearchQuery.append("SELECT rownum revokeIsdnFormId, " + stockTypeId + " stockTypeId, a.lb fromIsdn, a.ub toIsdn, (a.ub - a.lb + 1) realQuantity, a.isdn_type isdnType, a.status status, a.owner_id shopId, b.shop_code shopCode, b.name shopName ");
            strSearchQuery.append("FROM (").append(strIsdnRangeQuery).append(") a, shop b ");
            strSearchQuery.append("WHERE a.owner_id = b.shop_id ");

            Query searchQuery = getSession().createSQLQuery(strSearchQuery.toString()).addScalar("revokeIsdnFormId", Hibernate.LONG).addScalar("stockTypeId", Hibernate.LONG).addScalar("fromIsdn", Hibernate.STRING).addScalar("toIsdn", Hibernate.STRING).addScalar("realQuantity", Hibernate.LONG).addScalar("isdnType", Hibernate.STRING).addScalar("status", Hibernate.LONG).addScalar("shopId", Hibernate.LONG).addScalar("shopCode", Hibernate.STRING).addScalar("shopName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(RevokeISDNForm.class));
            for (int i = 0; i < listParameter.size(); i++) {
                searchQuery.setParameter(i, listParameter.get(i));
            }

            List<RevokeISDNForm> listrkform = searchQuery.list();
            req.getSession().setAttribute(SESSION_LIST_ISDN_RANGE, listrkform);
            if (listrkform != null && listrkform.size() != 0) {
                req.setAttribute(REQUEST_MESSAGE, "Tìm kiếm được " + listrkform.size() + " dải số thỏa mãn điều kiện");
            } else {
                req.setAttribute(REQUEST_MESSAGE, "Không tìm kiếm được dải số nào thỏa mãn điều kiện");
            }

            pageForward = RVK_ISDN;
            log.info("end method searchIsdnRange of RevokeISDNDAO");
            return pageForward;

        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("end method searchIsdnRange of RevokeISDNDAO");
            throw ex;
        }
    }
}
