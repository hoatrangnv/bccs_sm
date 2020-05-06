/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.StockTotalFull;
import com.viettel.im.database.BO.StockTotalId;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockOwnerTmp;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.DAO.ChannelTypeDAO;
import com.viettel.im.common.Constant;
import com.viettel.im.client.form.DebitForm;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 *
 * @author NamLT
 * Date 07092010
 */
public class DebitDAO extends BaseDAOAction {

//    private Session session;
    private Map hashMapObjCode = new HashMap();
    private Map hashMapObjName = new HashMap();
    private final Long MAX_RESULT = 1000L; //so ban ghi toi da trong 1 lan truy van
    private String TO_ASSIGN_DEBIT = "toAssignDebit";

    public Map getHashMapObjCode() {
        return hashMapObjCode;
    }

    public void setHashMapObjCode(Map hashMapObjCode) {
        this.hashMapObjCode = hashMapObjCode;
    }

//    @Override
//    public void setSession(Session session) {
//        this.session = session;
//    }
//
//    @Override
//    public Session getSession() {
//        if (session == null) {
//            return BaseHibernateDAO.getSession();
//        }
//        return this.session;
//    }
    private DebitForm debitForm = new DebitForm();

    public DebitForm getDebitForm() {
        return debitForm;
    }

    public void setDebitForm(DebitForm debitForm) {
        this.debitForm = debitForm;
    }
    private static final Logger log = Logger.getLogger(DebitDAO.class);

    public String prepareAssignDebit() {
        String pageForward = "viewAssignDebit";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
//Lay ten ShopName và ShopCode
        Long shopId = userToken.getShopId();
        String strShopQuery = " from Shop a where a.shopId = ? and status = ? ";
        Query shopQuery = getSession().createQuery(strShopQuery);
        shopQuery.setParameter(0, shopId);
        shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
        List<Shop> listShop = shopQuery.list();
        debitForm.setParentName(listShop.get(0).getName());
        debitForm.setParentCode(listShop.get(0).getShopCode());
        setTabSession("viewAllShopDebit", ResourceBundleUtils.getResource("viewAllShopDebit"));
        return pageForward;

    }

    public String pageNavigatorForList() throws Exception {
        log.info("Begin method pageNavigatorForList of StockIsdnAssignPstnToDluDAO ...");

        String pageForward = "pageNavigatorForList";

        //getRequest().getSession().getAttribute(SESSION_LIST_STOCK_ISDN_PSTN);

        listDebit();


        log.info("End method pageNavigatorForList of StockIsdnAssignPstnToDluDAO");

        return pageForward;
    }

    //Ham tim kiem
    public List<ImSearchBean> getListChannel(ImSearchBean imSearchBean) {

        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.name, a.name) ");
        queryString.append("from ChannelType a where 1=1 ");
        queryString.append("and a.status = ? ");
        listParameter.add(Constant.STATUS_USE);

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");


        }


        queryString.append("and rownum <= ? ");
        listParameter.add(AssignShopDslamDAO.MAX_SEARCH_RESULT);
        queryString.append("order by a.name ");
        Query queryObject = getSession().createQuery(queryString.toString());


        for (int i = 0; i
                < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));


        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<ImSearchBean> tmpList = queryObject.list();


        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);


        }
        return listImSearchBean;


    }

    public Long getListChannelSize(ImSearchBean imSearchBean) {

        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select count(*) ");
        queryString.append("from ChannelType a where 1=1 ");
        queryString.append("and a.status = ? ");
        listParameter.add(Constant.STATUS_USE);

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");


        }


        queryString.append("and rownum <= ? ");
        listParameter.add(AssignShopDslamDAO.MAX_SEARCH_RESULT);
        queryString.append("order by a.name ");
        Query queryObject = getSession().createQuery(queryString.toString());


        for (int i = 0; i
                < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));


        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<Long> tmpList = queryObject.list();


        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);


        } else {
            return null;


        }


    }

    public List<ImSearchBean> getListChannelName(ImSearchBean imSearchBean) {

        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.name, a.name) ");
        queryString.append("from ChannelType a where 1=1 ");
        queryString.append("and a.status = ? ");
        listParameter.add(Constant.STATUS_USE);
        //   queryString.append("and a.productId = ? ");
        //   listParameter.add(Constant.PRODUCT_ID_DLU);


//        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
//            queryString.append("and upper(a.code) like ? ");
//            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
//
//
//        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");


        }
//        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
//            queryString.append("and upper(a.province) = ? ");
//            listParameter.add(imSearchBean.getOtherParamValue().trim().toUpperCase());
//
//
//        }

        queryString.append("and rownum <= ? ");
        listParameter.add(AssignShopDslamDAO.MAX_SEARCH_RESULT);
        queryString.append("order by a.name ");
        Query queryObject = getSession().createQuery(queryString.toString());


        for (int i = 0; i
                < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));


        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<ImSearchBean> tmpList = queryObject.list();


        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);


        }
        return listImSearchBean;


    }

    public String getDataForObjectCodeAutocompleter() throws Exception {
        try {
            //go ma cua hang -> hien thi danh sach cac cua hang tuong ung
            HttpServletRequest httpServletRequest = getRequest();
            String debitCode = httpServletRequest.getParameter("debitForm.code");

            if (debitCode != null && !debitCode.trim().equals("")) {
                String strQuery = "from StockOwnerTmp where lower(code) like ? ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, "%" + debitCode.trim().toLowerCase() + "%");
                // query.setParameter(1, Constant.STATUS_USE);
                query.setMaxResults(8);
                List<StockOwnerTmp> listDebitCode = query.list();
                for (int i = 0; i < listDebitCode.size(); i++) {
                    hashMapObjCode.put(listDebitCode.get(i).getName(), listDebitCode.get(i).getCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return "getDataForObjAutocompleter";
    }

    public String updateObjName() throws Exception {
        HttpServletRequest httpServletRequest = getRequest();

        String strTarget = httpServletRequest.getParameter("target");
        String strCode = httpServletRequest.getParameter("code");
        if (strCode != null && !strCode.trim().equals("")) {
            StockOwnerTmpDAO stockOwnerTmpDAO = new StockOwnerTmpDAO();
            stockOwnerTmpDAO.setSession(this.getSession());
            List<StockOwnerTmp> lst = stockOwnerTmpDAO.findByCode(strCode.trim());
            String strName = lst != null ? lst.get(0).getName() : "";
            this.hashMapObjName.put(strTarget, strName);
            // }
        }
        return "updateObjName";
    }

    public String getListDebitSize() throws Exception {
        HttpServletRequest httpServletRequest = getRequest();
        //String pageForward = "pageNavigatorForList";
        UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute("userToken");
        Shop shop = null;


        List listParameter = new ArrayList();
        //getReqSession();

        //  List<StockOwnerTmp> listStockOwnerTmpReal = new ArrayList<StockOwnerTmp>();
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(getSession());


        // DebitForm debitForm = (DebitForm) form;

        String strQuery = "Select count(*)";
        strQuery += " from StockOwnerTmp a where 1=1";



        if (debitForm.getChannelParent() != null && !"".equals(debitForm.getChannelParent())) {
            if (debitForm.getShopCode() != null && !"".equals(debitForm.getShopCode())) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                shop = shopDAO.findShopsAvailableByShopCode(debitForm.getShopCode());
                if (shop == null) {
                    // pageForward="viewAssignDebit";
                    httpServletRequest.setAttribute("messageList", "ERR.DET.066");
                    return null;
                }
            } else {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                shop = shopDAO.findShopsAvailableByShopCode(debitForm.getParentCode());
                if (shop == null) {
                    // pageForward="viewAssignDebit";
                    httpServletRequest.setAttribute("messageList", "ERR.LST.115");
                    return null;
                }
            }


            if ("1".equals(debitForm.getChannelParent())) {
                strQuery += " and a.ownerId in (select shopId from Shop where (shopPath like ? or shopId = ?) and status=1 ) and a.ownerType=1 ";
                listParameter.add("%_" + shop.getShopId() + "_%");
                listParameter.add(shop.getShopId());
            } // Lay duoi 1 cap
            else if ("2".equals(debitForm.getChannelParent())) {
                strQuery += " and a.ownerId in (select shopId from Shop where parentShopId=? and status=1) and a.ownerType=1 ";
                listParameter.add(shop.getShopId());
            } //Tat ca nhan vien
            else if ("3".equals(debitForm.getChannelParent())) {
                //   strQuery += " and ((a.ownerId in (select shopId from Shop where shopPath like ? or shopId = ? )) ";
                strQuery += "  and (a.ownerId in (select staffId from Staff where shopId in (select shopId from Shop where shopPath like ? or shopId=? ) and status=1)) and a.ownerType=2";
                // listParameter.add(userToken.getShopId());
                listParameter.add("%_" + shop.getShopId() + "_%");
                listParameter.add(shop.getShopId());
//                    listParameter.add("%_" + shop.getShopId() + "_%");
//                    listParameter.add(shop.getShopId());
            } //NV thuoc cua hang
            else if ("4".equals(debitForm.getChannelParent())) {
                //  strQuery += " and ((a.ownerId in (select shopId from Shop where parentShopId=? )) ";
                strQuery += "  and (a.ownerId in (select staffId from Staff where shopId =? and a.ownerType=2 and status=1))";
                listParameter.add(shop.getShopId());

            }
        }

        //Neu chon ma cua hang-->Tim kiem theo Ma cua hang
        if (debitForm.getShopCode() != null && !"".equals(debitForm.getShopCode())) {

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            shop = shopDAO.findShopsAvailableByShopCode(debitForm.getShopCode());
            if (shop == null) {
                //pageForward="viewAssignDebit";
                httpServletRequest.setAttribute("messageList", "ERR.DET.066");
                return null;
            }
            strQuery += " and ((a.ownerId in (select shopId from Shop where (shopPath like ? or shopId = ?) and status=1 ) and a.ownerType=1) ";
            strQuery += " or (a.ownerId in (select staffId from Staff where shopId in (select shopId from Shop where shopPath like ? or shopId=? ) and status=1) and a.ownerType=2)) ";
            listParameter.add("%_" + shop.getShopId() + "_%");
            listParameter.add(shop.getShopId());
            listParameter.add("%_" + shop.getShopId() + "_%");
            listParameter.add(shop.getShopId());
        } //neu khong tim theo ma cha
        else {
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            shop = shopDAO.findShopsAvailableByShopCode(debitForm.getParentCode());
            if (shop == null) {
                //  pageForward="viewAssignDebit";
                httpServletRequest.setAttribute("messageList", "ERR.LST.115");
                return null;
            }
            strQuery += " and ((a.ownerId in (select shopId from Shop where (shopPath like ? or shopId = ? ) and status=1) and a.ownerType=1 ) ";
            strQuery += "  or (a.ownerId in (select staffId from Staff where shopId in (select shopId from Shop where shopPath like ? or shopId=? ) and status=1) and a.ownerType=2 )) ";
            listParameter.add("%_" + shop.getShopId() + "_%");
            listParameter.add(shop.getShopId());
            listParameter.add("%_" + shop.getShopId() + "_%");
            listParameter.add(shop.getShopId());

        }

        if (debitForm.getCode().trim() != null && !"".equals(debitForm.getCode().trim())) {
//                if (httpServletRequest.getAttribute(TO_ASSIGN_DEBIT) != null && !"".equals(httpServletRequest.getAttribute(TO_ASSIGN_DEBIT))) {
//                    strQuery += " and lower(a.code) like ?";
//                    listParameter.add("%" + debitForm.getCode().trim().toLowerCase() + "%");
//
//                } else {
            strQuery += " and lower(a.code) like ?";
            listParameter.add("%" + debitForm.getCode().trim().toLowerCase() + "%");
            //}
        }

        if (debitForm.getMaxDebit().trim() != null && !"".equals(debitForm.getMaxDebit().trim())) {
            strQuery += " and a.maxDebit = ? ";
            listParameter.add(Long.valueOf(debitForm.getMaxDebit().trim()));
        }

        if (debitForm.getDateReset().trim() != null && !"".equals(debitForm.getDateReset().trim())) {
            strQuery += " and a.dateReset = ? ";
            listParameter.add(Long.valueOf(debitForm.getDateReset().trim()));
        }

        if (debitForm.getStatus() != null && !"".equals(debitForm.getStatus())) {
            if ("1".equals(debitForm.getStatus())) {
                strQuery += " and a.maxDebit is not null ";
            } else if ("2".equals(debitForm.getStatus())) {
                strQuery += " and a.maxDebit is null ";
            }
        }

        if (debitForm.getChannelType() != null && !"".equals(debitForm.getChannelType())) {
            List<ChannelType> lstChannelType = (List<ChannelType>) channelTypeDAO.findByName(debitForm.getChannelType().trim());
            if (lstChannelType == null || lstChannelType.size() == 0) {
                httpServletRequest.setAttribute("messageList", "ERR.LST.114");
                return "0";
            } else {
                Long channelTypeId = lstChannelType.get(0) != null ? lstChannelType.get(0).getChannelTypeId() : -1L;
                strQuery += " and a.channelTypeId = ? ";
                listParameter.add(channelTypeId);
            }
        }




//            if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("viewAllShopDebit"), httpServletRequest)) {
//
////                    ShopDAO shopDAO = new ShopDAO();
////                    shopDAO.setSession(this.getSession());
////                    Shop shop = shopDAO.findById(userToken.getShopId());
//                strQuery += " and a.ownerId in (select b.shopId from Shop b where 1=1 and b.status=1 and (b.shopPath like ? or b.shopId = ?))  ";
//                listParameter.add("%_" + userToken.getShopId() + "_%");
//                listParameter.add(userToken.getShopId());
//
//
//            }





//            strQuery += " and rownum < ? ";
//            listParameter.add(MAX_RESULT);

        strQuery += " order by a.code";

        Query query = getSession().createQuery(strQuery);
        for (int i = 0;
                i < listParameter.size();
                i++) {
            query.setParameter(i, listParameter.get(i));
        }

        List listStockOwnerTmp = query.list();

        //  listStockOwnerTmp = (List<StockOwnerTmp>) query.setMaxResults(Constant.MAX_RESULT_PSTN).list();

        //int realSize = query.list().size();
        if (null == listStockOwnerTmp || 0 == listStockOwnerTmp.size()) {
            return "0";


        }
        String size = listStockOwnerTmp.get(0).toString();








        return size;


    }

    public String listDebit() throws Exception {
        HttpServletRequest httpServletRequest = getRequest();
        String pageForward = "pageNavigatorForList";
        UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute("userToken");
        Shop shop = null;
        try {

            List listParameter = new ArrayList();
            //getReqSession();
            List<StockOwnerTmp> listStockOwnerTmp = new ArrayList<StockOwnerTmp>();
            //   List<StockOwnerTmp> listStockOwnerTmpReal = new ArrayList<StockOwnerTmp>();
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            channelTypeDAO.setSession(getSession());


            // DebitForm debitForm = (DebitForm) form;

            String strQuery = "Select new com.viettel.im.database.BO.StockOwnerTmp(stockId, ownerId, code, name,ownerType,(select name from ChannelType b where a.channelTypeId=b.channelTypeId),maxDebit,currentDebit,dateReset)";
            strQuery += " from StockOwnerTmp a where 1=1";


            if (debitForm.getChannelParent() != null && !"".equals(debitForm.getChannelParent())) {
                if (debitForm.getShopCode() != null && !"".equals(debitForm.getShopCode())) {
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());
                    shop = shopDAO.findShopsAvailableByShopCode(debitForm.getShopCode());
                    if (shop == null) {
                        // pageForward="viewAssignDebit";
                        httpServletRequest.setAttribute("messageList", "ERR.DET.066");
                        return pageForward;
                    }
                } else {
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());
                    shop = shopDAO.findShopsAvailableByShopCode(debitForm.getParentCode());
                    if (shop == null) {
                        // pageForward="viewAssignDebit";
                        httpServletRequest.setAttribute("messageList", "ERR.LST.115");
                        return pageForward;
                    }
                }


                if ("1".equals(debitForm.getChannelParent())) {
                    strQuery += " and a.ownerId in (select shopId from Shop where (shopPath like ? or shopId = ?) and status=1 ) and a.ownerType=1 ";
                    listParameter.add("%_" + shop.getShopId() + "_%");
                    listParameter.add(shop.getShopId());
                } // Lay duoi 1 cap
                else if ("2".equals(debitForm.getChannelParent())) {
                    strQuery += " and a.ownerId in (select shopId from Shop where parentShopId=? and status=1) and a.ownerType=1 ";
                    listParameter.add(shop.getShopId());
                } //Tat ca nhan vien
                else if ("3".equals(debitForm.getChannelParent())) {
                    //   strQuery += " and ((a.ownerId in (select shopId from Shop where shopPath like ? or shopId = ? )) ";
                    strQuery += "  and (a.ownerId in (select staffId from Staff where shopId in (select shopId from Shop where shopPath like ? or shopId=? ) and status=1)) and a.ownerType=2";
                    // listParameter.add(userToken.getShopId());
                    listParameter.add("%_" + shop.getShopId() + "_%");
                    listParameter.add(shop.getShopId());
//                    listParameter.add("%_" + shop.getShopId() + "_%");
//                    listParameter.add(shop.getShopId());
                } //NV thuoc cua hang
                else if ("4".equals(debitForm.getChannelParent())) {
                    //  strQuery += " and ((a.ownerId in (select shopId from Shop where parentShopId=? )) ";
                    strQuery += "  and (a.ownerId in (select staffId from Staff where shopId =? and a.ownerType=2 and status=1))";
                    listParameter.add(shop.getShopId());

                }
            }

            //Neu chon ma cua hang-->Tim kiem theo Ma cua hang
            if (debitForm.getShopCode() != null && !"".equals(debitForm.getShopCode())) {

                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                shop = shopDAO.findShopsAvailableByShopCode(debitForm.getShopCode());
                if (shop == null) {
                    //pageForward="viewAssignDebit";
                    httpServletRequest.setAttribute("messageList", "ERR.DET.066");
                    return pageForward;
                }
                strQuery += " and ((a.ownerId in (select shopId from Shop where (shopPath like ? or shopId = ?) and status=1 ) and a.ownerType=1) ";
                strQuery += " or (a.ownerId in (select staffId from Staff where shopId in (select shopId from Shop where shopPath like ? or shopId=? ) and status=1) and a.ownerType=2)) ";
                listParameter.add("%_" + shop.getShopId() + "_%");
                listParameter.add(shop.getShopId());
                listParameter.add("%_" + shop.getShopId() + "_%");
                listParameter.add(shop.getShopId());
            } //neu khong tim theo ma cha
            else {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                shop = shopDAO.findShopsAvailableByShopCode(debitForm.getParentCode());
                if (shop == null) {
                    //  pageForward="viewAssignDebit";
                    httpServletRequest.setAttribute("messageList", "ERR.LST.115");
                    return pageForward;
                }
                strQuery += " and ((a.ownerId in (select shopId from Shop where (shopPath like ? or shopId = ? ) and status=1) and a.ownerType=1 ) ";
                strQuery += "  or (a.ownerId in (select staffId from Staff where shopId in (select shopId from Shop where shopPath like ? or shopId=? ) and status=1) and a.ownerType=2 )) ";
                listParameter.add("%_" + shop.getShopId() + "_%");
                listParameter.add(shop.getShopId());
                listParameter.add("%_" + shop.getShopId() + "_%");
                listParameter.add(shop.getShopId());

            }

            if (debitForm.getCode().trim() != null && !"".equals(debitForm.getCode().trim())) {
//                if (httpServletRequest.getAttribute(TO_ASSIGN_DEBIT) != null && !"".equals(httpServletRequest.getAttribute(TO_ASSIGN_DEBIT))) {
//                    strQuery += " and lower(a.code) like ?";
//                    listParameter.add("%" + debitForm.getCode().trim().toLowerCase() + "%");
//
//                } else {
                strQuery += " and lower(a.code) like ?";
                listParameter.add("%" + debitForm.getCode().trim().toLowerCase() + "%");
                //}
            }

            if (debitForm.getMaxDebit().trim() != null && !"".equals(debitForm.getMaxDebit().trim())) {
                strQuery += " and a.maxDebit = ? ";
                listParameter.add(Double.valueOf(debitForm.getMaxDebit().trim()));
            }

            if (debitForm.getDateReset().trim() != null && !"".equals(debitForm.getDateReset().trim())) {
                strQuery += " and a.dateReset = ? ";
                listParameter.add(Long.valueOf(debitForm.getDateReset().trim()));
            }

            if (debitForm.getStatus() != null && !"".equals(debitForm.getStatus())) {
                if ("1".equals(debitForm.getStatus())) {
                    strQuery += " and a.maxDebit is not null ";
                } else if ("2".equals(debitForm.getStatus())) {
                    strQuery += " and a.maxDebit is null ";
                }
            }

            if (debitForm.getChannelType() != null && !"".equals(debitForm.getChannelType())) {
                List<ChannelType> lstChannelType = (List<ChannelType>) channelTypeDAO.findByName(debitForm.getChannelType().trim());
                if (lstChannelType == null || lstChannelType.size() == 0) {
                    httpServletRequest.setAttribute("messageList", "ERR.LST.114");
                    return pageForward;
                } else {
                    Long channelTypeId = lstChannelType.get(0) != null ? lstChannelType.get(0).getChannelTypeId() : -1L;
                    strQuery += " and a.channelTypeId = ? ";
                    listParameter.add(channelTypeId);
                }
            }




//            if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("viewAllShopDebit"), httpServletRequest)) {
//
////                    ShopDAO shopDAO = new ShopDAO();
////                    shopDAO.setSession(this.getSession());
////                    Shop shop = shopDAO.findById(userToken.getShopId());
//                strQuery += " and a.ownerId in (select b.shopId from Shop b where 1=1 and b.status=1 and (b.shopPath like ? or b.shopId = ?))  ";
//                listParameter.add("%_" + userToken.getShopId() + "_%");
//                listParameter.add(userToken.getShopId());
//
//
//            }





            strQuery += " and rownum < ? ";
            listParameter.add(MAX_RESULT);

            strQuery += " order by a.code";

            Query query = getSession().createQuery(strQuery);
            for (int i = 0;
                    i < listParameter.size();
                    i++) {
                query.setParameter(i, listParameter.get(i));
            }

            //  listStockOwnerTmpReal = (List<StockOwnerTmp>) query.list();
            listStockOwnerTmp = (List<StockOwnerTmp>) query.setMaxResults(Constant.MAX_RESULT_PSTN).list();

            //int realSize = query.list().size();
            if (null == listStockOwnerTmp || 0 == listStockOwnerTmp.size()) {
                throw new Exception("Không tìm thấy kết quả thỏa mãn");


            }

            if (listStockOwnerTmp != null && listStockOwnerTmp.size() != 0) {
                httpServletRequest.setAttribute("messageList", getText("MSG.ISN.052") + " " + String.valueOf(listStockOwnerTmp.size()) + "/" + this.getListDebitSize() + " " + getText("MSG.result"));
            } else {
                httpServletRequest.setAttribute("messageList", "MSG.doNotFindData");
            }

            httpServletRequest.setAttribute("listStockOwnerTmp", listStockOwnerTmp);
        } catch (Exception e) {
            e.printStackTrace();
        }






        return pageForward;



    }
    /*
     * Author NamLT
     * Ham gan han muc tren giao dien
     * date 07/09/2010
     */

    public String assignDebit() throws Exception {
        String[] arrSelectedItem = debitForm.getSelectedItems();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "viewAssignDebit";
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();


        try {
//            if (arrSelectedItem == null || arrSelectedItem.length <= 0
//                    || (arrSelectedItem.length == 1 && arrSelectedItem[0].equals("false"))) {
//                getRequest().setAttribute("message", "Chưa chọn bản ghi nào để gán");
//                log.info("End contractCOMM of ContractFeesDAO");
//
//
//            } else {
            for (int i = 0; i
                    < arrSelectedItem.length; i++) {
                String strId = arrSelectedItem[i];
                StockOwnerTmpDAO stockOwnerTmpDAO = new StockOwnerTmpDAO();
                stockOwnerTmpDAO.setSession(this.getSession());
                StockOwnerTmp stockOwnerTmp = stockOwnerTmpDAO.findById(Long.parseLong(strId));



                if (stockOwnerTmp != null) {
                    stockOwnerTmp.setMaxDebit(Double.valueOf(debitForm.getMaxDebit().trim()));
                    stockOwnerTmp.setDateReset(Long.valueOf(debitForm.getDateReset().trim()));
                    getSession().update(stockOwnerTmp);
                    lstLogObj.add(new ActionLogsObj(null, debitForm.getMaxDebit().trim()));
                    lstLogObj.add(new ActionLogsObj(null, debitForm.getDateReset().trim()));
                    saveLog(Constant.ACTION_ASSIGN_DEBIT_BY_PAGE, "Gán Debit trên giao diện", lstLogObj, stockOwnerTmp.getOwnerId());



                }


                req.setAttribute("message", "MSG.LST.044");





            }

            req.setAttribute("toAssignDebit", 2);
            this.listDebit();
            req.setAttribute("messageList", "");




        } catch (Exception ex2) {
            req.setAttribute("message", ex2.getMessage());
            ex2.printStackTrace();


        }


        return pageForward;


    }

    public String prepareAssignDebitPage() throws Exception {
        HttpServletRequest req = getRequest();
        String strStockOwnerTmpId = (String) QueryCryptUtils.getParameter(req, "stockId");
        StockOwnerTmpDAO stockOwnerTmpDAO = new StockOwnerTmpDAO();
        stockOwnerTmpDAO.setSession(this.getSession());
        String forwardPage = "viewAssignDebit";
        String shopCode = "";




        try {
            if (strStockOwnerTmpId != null) {
                StockOwnerTmp stockOwnerTmp = stockOwnerTmpDAO.findById(Long.parseLong(strStockOwnerTmpId));






                if (stockOwnerTmp != null) {
                    if ("1".equals(stockOwnerTmp.getOwnerType())) {

                        String strShopQuery = " from Shop a where a.shopCode= ? and a.status = ? ";
                        Query shopQuery = getSession().createQuery(strShopQuery);
                        shopQuery.setParameter(0, stockOwnerTmp.getCode());
                        shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
                        List<Shop> listShop = shopQuery.list();
                        shopCode = listShop.get(0).getShopCode();
                    } else if ("2".equals(stockOwnerTmp.getOwnerType())) {
                        StaffDAO staffDAO = new StaffDAO();
                        staffDAO.setSession(this.getSession());
                        Staff staff = staffDAO.findStaffAvailableByStaffCode(stockOwnerTmp.getCode());
                        String strShopQuery = " from Shop a where a.shopId= ? and a.status = ? ";
                        Query shopQuery = getSession().createQuery(strShopQuery);
                        shopQuery.setParameter(0, staff.getShopId());
                        shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
                        List<Shop> listShop = shopQuery.list();
                        shopCode = listShop.get(0).getShopCode();
                        //shopCode=staff!=null?staff.getStaffCode():"";

                    }

                    debitForm.setCode(stockOwnerTmp.getCode());
                    debitForm.setCurrentDebit(stockOwnerTmp.getCurrentDebit() != null ? stockOwnerTmp.getCurrentDebit().toString() : "");
                    debitForm.setOwnerType(stockOwnerTmp.getOwnerType());
                    debitForm.setShopCode(shopCode);
                    debitForm.setShopName(stockOwnerTmp.getName());
                    debitForm.setMaxDebit(stockOwnerTmp.getMaxDebit() != null ? stockOwnerTmp.getMaxDebit().toString() : "");
                    debitForm.setDateReset(stockOwnerTmp.getDateReset() != null ? stockOwnerTmp.getDateReset().toString() : "");
                    ChannelTypeDAO channelDAO = new ChannelTypeDAO();
                    channelDAO.setSession(this.getSession());
                    ChannelType channelType = channelDAO.findById(stockOwnerTmp.getChannelTypeId());
                    debitForm.setChannelType(channelType != null ? channelType.getName() : "");





                }
            }

            req.setAttribute("toAssignDebit", 1);

            req.removeAttribute("stockId");
            req.setAttribute("stockId", strStockOwnerTmpId);
            this.listDebit();





        } catch (Exception ex) {
            ex.printStackTrace();




        }

        return forwardPage;




    }
    /*
     * Author NamLT
     * Ham gan han muc bang file Excel
     * Date 7/9/2010
     */
    private static HashMap<String, List<String>> listProgressMessage = new HashMap<String, List<String>>();

    public String assginDebitByExcel() throws Exception {
        String forwardPage = "viewAssignDebit";
        HttpServletRequest req = getRequest();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();



        int numberRecord = 0;
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");





        List<String> listMessage = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.listProgressMessage.put(req.getSession().getId(), listMessage);
        String startTime = DateTimeUtils.getSysDateTime();
        String message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + getText("ERR.LST.120");
        listMessage.add(message);
        int NUMBER_CMD_IN_BATCH = 100; //so luong ban ghi se commit 1 lan
        Connection conn = getSession().connection();
        StringBuffer strUpdateQuery = new StringBuffer();
        strUpdateQuery.append("update STOCK_OWNER_TMP set ");
        strUpdateQuery.append("MAX_DEBIT = ?, DATE_RESET = ?");
        strUpdateQuery.append(" where CODE = ? AND OWNER_TYPE=? ");
        PreparedStatement stmUpdate = conn.prepareStatement(strUpdateQuery.toString());





        try {
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + CommonDAO.getSafeFileName(debitForm.getServerFileName()));
            File clientFile = new File(serverFilePath);





            if (debitForm.getServerFileName() == null) {
                req.setAttribute("resultAssignIsdnPrice", "ERR.ISN.138");




                return forwardPage;




            }


            List lstStockOwnerTmp = new CommonDAO().readExcelFile(clientFile, "Sheet1", 1, 0, 3);




            if (lstStockOwnerTmp == null || lstStockOwnerTmp.size() == 0) {
                req.setAttribute("resultAssignIsdnPrice", "ERR.ISN.132");
                // lstIsdn = new CommonDAO().readExcelFile(clientFile, "Sheet1", 0, 1, 9);




                return forwardPage;




            }

            List<DebitForm> listError = new ArrayList<DebitForm>();
            Object[] obj = null;
            String code = "";
            String maxDebit = "";
            String dateReset = "";
            String channelType = "";
            ChannelType channelTypeBO = null;




            int countInBatch = 0;
            //Loc ra danh sach cac shop duoc quyen gan debit
            String strQueryShop = "select shopId from Shop where (shopPath like ? or shopId = ?) and status=1 ";
            Query queryShop = getSession().createQuery(strQueryShop);
            queryShop.setParameter(0, "%_" + userToken.getShopId() + "_%");
            queryShop.setParameter(1, userToken.getShopId());
            List<Long> listShop = (List<Long>) queryShop.list();
            //Loc ra danh sach cac staff duoc quyen gan debit
            String strQueryStaff = "select a.staffId from Staff a where not exists (select 1 from Shop b where (b.shopPath like ? or b.shopId=?) and a.shopId=b.shopId and b.status=1)";
            strQueryStaff += " and a.status=1";
            Query queryStaff = getSession().createQuery(strQueryStaff);
            queryStaff.setParameter(0, "%_" + userToken.getShopId() + "_%");
            queryStaff.setParameter(1, userToken.getShopId());
            List<Long> listStaff = (List<Long>) queryStaff.list();




            for (int idx = 0; idx
                    < lstStockOwnerTmp.size(); idx++) {
                //Khoi tao lai bien
                code = "";
                maxDebit = "";
                dateReset = "";
                obj = (Object[]) lstStockOwnerTmp.get(idx);




                if (obj != null && obj.length >= 4) {
                    code = obj[1] != null ? obj[1].toString().trim() : "";
                    maxDebit = obj[2] != null ? obj[2].toString().trim() : "";
                    dateReset = obj[3] != null ? obj[3].toString().trim() : "";
                    channelType = obj[0] != null ? obj[0].toString().trim() : "";




                }
                if (!code.equals("") && !maxDebit.equals("") && !("".equals(code.trim())) && isNumber(maxDebit) && isNumber(dateReset)) {

                    DebitForm importDebit = new DebitForm();




                    boolean Check = true;
                    String strQueryStock = "select code from StockOwnerTmp WHERE lower(code) = ? and ownerType=? ";
                    Query queryStock = getSession().createQuery(strQueryStock);
                    queryStock.setParameter(0, code.toString().toLowerCase());
                    queryStock.setParameter(1, channelType.toString());
                    List listIsdn = queryStock.list();




                    if (listIsdn == null || listIsdn.size() == 0) {
                        importDebit.setError(("ERR.LST.108"));
                        Check = false;

                    }
                    //  Check quyen gan Debit cho cua hang / nhan vien


                    if ("1".equals(channelType)) {
                        ShopDAO shopDAO = new ShopDAO();
                        shopDAO.setSession(this.getSession());
                        Shop shop = shopDAO.findShopsAvailableByShopCode(code);
                        if (shop == null) {
                            importDebit.setError(("ERR.DET.066"));
                            Check = false;

                        } else {

                            if (!listShop.contains(shop.getShopId())) {
                                importDebit.setError(("ERR.LST.116"));
                                Check = false;
                            }
                        }


                    } else if ("2".equals(channelType)) {
                        StaffDAO staffDAO = new StaffDAO();
                        staffDAO.setSession(this.getSession());
                        Staff staff = staffDAO.findStaffAvailableByStaffCode(code);
                        if (staff == null) {
                            importDebit.setError(("ERR.BIL.028"));
                            Check = false;

                        } else {

                            if (listStaff.contains(staff.getStaffId())) {
                                importDebit.setError(("ERR.LST.116"));
                                Check = false;
                            }


                        }
                    }


                    if (!"1".equals(channelType) && !"2".equals(channelType)) {
                        importDebit.setError(("ERR.LST.114"));
                        Check = false;

                    }

                    if (maxDebit.length() > 20) {
                        importDebit.setError(("ERR.LST.118"));
                        Check = false;
                    }


                    if (dateReset.length() > 20) {
                        importDebit.setError(("ERR.LST.119"));
                        Check = false;
                    }



//                    if (channelType != null && !"".equals(channelType)) {
//                        channelTypeBO = this.getChannelTypeByCode(channelType);
//                        if (channelTypeBO == null) {
//                            importDebit.setError(("ERR.LST.114"));
//                            Check = false;
//                        }
//                    }

                    importDebit.setCode(code);
                    importDebit.setMaxDebit(maxDebit);
                    importDebit.setDateReset(dateReset);
                    importDebit.setOwnerType(channelType);





                    if (Check) {
                        stmUpdate.setLong(1, Long.valueOf(importDebit.getMaxDebit()));
                        stmUpdate.setLong(2, Long.valueOf(importDebit.getDateReset().trim()));
                        stmUpdate.setString(3, importDebit.getCode().trim());
                        stmUpdate.setLong(4, Long.parseLong(importDebit.getOwnerType().trim()));
                        stmUpdate.addBatch();
                        countInBatch += 1;




                        if (countInBatch % NUMBER_CMD_IN_BATCH == 0) {
                            //thuc hien batch, import du lieu vao DB, thuc hien insert 100 ban ghi 1 lan
                            int[] updateCount = stmUpdate.executeBatch();
                            conn.commit();
                            message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + getText("ERR.ISN.140") + " " + countInBatch + " bản ghi";
                            listMessage.add(message);
                            StockOwnerTmpDAO stockOwnerTmpDAO = new StockOwnerTmpDAO();
                            stockOwnerTmpDAO.setSession(this.getSession());
                            List<StockOwnerTmp> stockOwnerTmp = (List<StockOwnerTmp>) stockOwnerTmpDAO.findByCode(code);
                            lstLogObj.add(new ActionLogsObj(null, importDebit.getMaxDebit()));
                            lstLogObj.add(new ActionLogsObj(null, importDebit.getDateReset()));
                            saveLog(Constant.ACTION_ASSIGN_DEBIT_BY_FILE, "Gán Debit từ File Excel", lstLogObj, stockOwnerTmp.get(0).getOwnerId());





                        } else { //insert so ban ghi con lai
                            int[] updateCount = stmUpdate.executeBatch();
                            conn.commit();

                            System.out.println("end insert to DB - " + new java.util.Date());
                        }
                    } else {
                        listError.add(importDebit);




                    }

                } else {
                    DebitForm importDebit = new DebitForm();




                    if (code.equals("")) {
                        importDebit.setError(("ERR.LST.109"));




                    }
                    if (maxDebit.equals("") || !isNumber(maxDebit)) {
                        importDebit.setError(("ERR.LST.110"));




                    }
                    if (dateReset.equals("") || !isNumber(dateReset)) {
                        importDebit.setError(("ERR.LST.111"));




                    }


                    importDebit.setCode(code);
                    importDebit.setMaxDebit(maxDebit);
                    importDebit.setDateReset(dateReset);
                    importDebit.setOwnerType(channelType);
                    importDebit = setError(importDebit);
                    listError.add(importDebit);





                }



            }
            message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + getText("ERR.ISN.144");
            listMessage.add(message);

            if (listError != null && listError.size() != 0) {
                message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + getText("ERR.ISN.145");
                listMessage.add(message);
                String DATE_FORMAT = "yyyyMMddhh24mmss";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Calendar cal = Calendar.getInstance();
                String date = sdf.format(cal.getTime());
                String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
                String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

                String templatePath = "";
                //Giao dich xuat

                templatePath = tmp + "Log_Import_Debit_.xls";
                filePath += "Log_Import_Debit_" + userToken.getLoginName() + "_" + date + ".xls";
                debitForm.setPathLogFile(filePath);
                req.setAttribute("pathLogFile", filePath);
                String realPath = req.getSession().getServletContext().getRealPath(filePath);
                String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

                Map beans = new HashMap();
                beans.put("listLogs", listError);
                XLSTransformer transformer = new XLSTransformer();
                transformer.transformXLS(templateRealPath, beans, realPath);
                message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + getText("ERR.ISN.146");
                listMessage.add(message);
                req.setAttribute("resultAssignIsdnPrice", "ERR.ISN.146");







            } else {
                req.setAttribute("resultAssignIsdnPrice", getText("ERR.ISN.140") + " " + lstStockOwnerTmp.size() + " bản ghi");




                return forwardPage;




            }
        } catch (Exception ex) {

            ex.printStackTrace();
            req.setAttribute("resultAssignIsdnPrice", "ERR.ISN.147");
            debitForm.setPathLogFile("");
            getSession().clear();




            return forwardPage;




        }

        return forwardPage;




    }

    private boolean isNumber(String str) {
        try {
            Long value = Long.valueOf(str);




            if (value.compareTo(0L) >= 0) {
                return true;




            } else {
                return false;




            }
        } catch (Exception ex) {
            return false;




        }
    }

    private ChannelType getChannelTypeByCode(String channelName) throws Exception {

        ChannelType type = null;




        if (channelName == null) {
            return type;






        }

        try {
            String strQuery = "from ChannelType where name = ? and status = ?";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, channelName);
            query.setParameter(1, Constant.STATUS_USE);

            List<ChannelType> listChannelType = (List<ChannelType>) query.list();






            if (listChannelType != null && listChannelType.size() > 0) {
                type = listChannelType.get(0);






            }

        } catch (Exception e) {
            e.printStackTrace();






            throw e;






        }

        return type;






    }

    private DebitForm setError(DebitForm debitAdd) {
        if (!isNumber(debitAdd.getMaxDebit())) {
            debitAdd.setError(debitAdd.getError() + getText("ERR.LST.110"));




        }
        if (!isNumber(debitAdd.getDateReset())) {
            debitAdd.setError(debitAdd.getError() + getText("ERR.LST.111"));




        }

        return debitAdd;



    }

    /*
     * Author NamLT
     * Ham xoa han muc
     * Date 7/9/2010
     */
    public String actionDeleteDebit() throws Exception {
        String[] arrSelectedItem = debitForm.getSelectedItems();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "viewAssignDebit";
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();


        try {
            if (arrSelectedItem == null || arrSelectedItem.length <= 0
                    || (arrSelectedItem.length == 1 && arrSelectedItem[0].equals("false"))) {
                getRequest().setAttribute("message", "Chưa chọn bản ghi nào để xóa");
                log.info("End contractCOMM of ContractFeesDAO");


            } else {
                for (int i = 0; i
                        < arrSelectedItem.length; i++) {
                    String strId = arrSelectedItem[i];
                    StockOwnerTmpDAO stockOwnerTmpDAO = new StockOwnerTmpDAO();
                    stockOwnerTmpDAO.setSession(this.getSession());
                    StockOwnerTmp stockOwnerTmp = stockOwnerTmpDAO.findById(Long.parseLong(strId));



                    if (stockOwnerTmp != null) {
                        stockOwnerTmp.setMaxDebit(null);
                        stockOwnerTmp.setCurrentDebit(null);
                        stockOwnerTmp.setDateReset(null);
                        getSession().update(stockOwnerTmp);
                        lstLogObj.add(new ActionLogsObj(null, debitForm.getMaxDebit()));
                        lstLogObj.add(new ActionLogsObj(null, debitForm.getDateReset()));
                        saveLog(Constant.ACTION_DELETE_DEBIT, "Xóa Debit", lstLogObj, stockOwnerTmp.getOwnerId());



                    }

                }
                req.setAttribute("message", "MSG.LST.045");





            }
            this.listDebit();


        } catch (Exception ex2) {
            ex2.printStackTrace();


        }

        return pageForward;


    }
    /*
     * AUthor NamLT
     * Action xuat file bao cao
     * Date 8/9/2010
     */

    public String actionExport2Excel() throws Exception {

        HttpServletRequest httpServletRequest = getRequest();
        String pageForward = "viewAssignDebit";
        UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute("userToken");
        Shop shop = null;


        try {

            List listParameter = new ArrayList();
            //getReqSession();
            List<StockOwnerTmp> listStockOwnerTmp = new ArrayList<StockOwnerTmp>();
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            channelTypeDAO.setSession(getSession());


            // DebitForm debitForm = (DebitForm) form;

            String strQuery = "Select new com.viettel.im.database.BO.StockOwnerTmp(stockId, ownerId, code, name,ownerType,(select name from ChannelType b where a.channelTypeId=b.channelTypeId),maxDebit,currentDebit,dateReset)";
            strQuery += " from StockOwnerTmp a where 1=1";



            if (debitForm.getChannelParent() != null && !"".equals(debitForm.getChannelParent())) {
                if (debitForm.getShopCode() != null && !"".equals(debitForm.getShopCode())) {
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());
                    shop = shopDAO.findShopsAvailableByShopCode(debitForm.getShopCode());
                    if (shop == null) {
                        // pageForward="viewAssignDebit";
                        httpServletRequest.setAttribute("messageList", "ERR.DET.066");
                        return pageForward;
                    }
                } else {
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());
                    shop = shopDAO.findShopsAvailableByShopCode(debitForm.getParentCode());
                    if (shop == null) {
                        // pageForward="viewAssignDebit";
                        httpServletRequest.setAttribute("messageList", "ERR.LST.115");
                        return pageForward;
                    }
                }


                if ("1".equals(debitForm.getChannelParent())) {
                    strQuery += " and a.ownerId in (select shopId from Shop where (shopPath like ? or shopId = ?) and status=1 ) and a.ownerType=1 ";
                    listParameter.add("%_" + shop.getShopId() + "_%");
                    listParameter.add(shop.getShopId());
                } // Lay duoi 1 cap
                else if ("2".equals(debitForm.getChannelParent())) {
                    strQuery += " and a.ownerId in (select shopId from Shop where parentShopId=? and status=1) and a.ownerType=1 ";
                    listParameter.add(shop.getShopId());
                } //Tat ca nhan vien
                else if ("3".equals(debitForm.getChannelParent())) {
                    //   strQuery += " and ((a.ownerId in (select shopId from Shop where shopPath like ? or shopId = ? )) ";
                    strQuery += "  and (a.ownerId in (select staffId from Staff where shopId in (select shopId from Shop where shopPath like ? or shopId=? ) and status=1)) and a.ownerType=2";
                    // listParameter.add(userToken.getShopId());
                    listParameter.add("%_" + shop.getShopId() + "_%");
                    listParameter.add(shop.getShopId());
//                    listParameter.add("%_" + shop.getShopId() + "_%");
//                    listParameter.add(shop.getShopId());
                } //NV thuoc cua hang
                else if ("4".equals(debitForm.getChannelParent())) {
                    //  strQuery += " and ((a.ownerId in (select shopId from Shop where parentShopId=? )) ";
                    strQuery += "  and (a.ownerId in (select staffId from Staff where shopId =? and a.ownerType=2 and status=1))";
                    listParameter.add(shop.getShopId());

                }
            }

            //Neu chon ma cua hang-->Tim kiem theo Ma cua hang
            if (debitForm.getShopCode() != null && !"".equals(debitForm.getShopCode())) {

                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                shop = shopDAO.findShopsAvailableByShopCode(debitForm.getShopCode());
                if (shop == null) {
                    //pageForward="viewAssignDebit";
                    httpServletRequest.setAttribute("messageList", "ERR.DET.066");
                    return pageForward;
                }
                strQuery += " and ((a.ownerId in (select shopId from Shop where (shopPath like ? or shopId = ?) and status=1 ) and a.ownerType=1) ";
                strQuery += " or (a.ownerId in (select staffId from Staff where shopId in (select shopId from Shop where shopPath like ? or shopId=? ) and status=1) and a.ownerType=2)) ";
                listParameter.add("%_" + shop.getShopId() + "_%");
                listParameter.add(shop.getShopId());
                listParameter.add("%_" + shop.getShopId() + "_%");
                listParameter.add(shop.getShopId());
            } //neu khong tim theo ma cha
            else {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                shop = shopDAO.findShopsAvailableByShopCode(debitForm.getParentCode());
                if (shop == null) {
                    //  pageForward="viewAssignDebit";
                    httpServletRequest.setAttribute("messageList", "ERR.LST.115");
                    return pageForward;
                }
                strQuery += " and ((a.ownerId in (select shopId from Shop where (shopPath like ? or shopId = ? ) and status=1) and a.ownerType=1 ) ";
                strQuery += "  or (a.ownerId in (select staffId from Staff where shopId in (select shopId from Shop where shopPath like ? or shopId=? ) and status=1) and a.ownerType=2 )) ";
                listParameter.add("%_" + shop.getShopId() + "_%");
                listParameter.add(shop.getShopId());
                listParameter.add("%_" + shop.getShopId() + "_%");
                listParameter.add(shop.getShopId());

            }

            if (debitForm.getCode().trim() != null && !"".equals(debitForm.getCode().trim())) {
                strQuery += " and lower(a.code) like ?";
                listParameter.add("%" + debitForm.getCode().trim().toLowerCase() + "%");
            }

            if (debitForm.getMaxDebit().trim() != null && !"".equals(debitForm.getMaxDebit().trim())) {
                strQuery += " and a.maxDebit = ? ";
                listParameter.add(Long.valueOf(debitForm.getMaxDebit().trim()));
            }

            if (debitForm.getDateReset().trim() != null && !"".equals(debitForm.getDateReset().trim())) {
                strQuery += " and a.dateReset = ? ";
                listParameter.add(Long.valueOf(debitForm.getDateReset().trim()));
            }

            if (debitForm.getStatus() != null && !"".equals(debitForm.getStatus())) {
                if ("1".equals(debitForm.getStatus())) {
                    strQuery += " and a.maxDebit is not null ";
                } else if ("2".equals(debitForm.getStatus())) {
                    strQuery += " and a.maxDebit is null ";
                }
            }

            if (debitForm.getChannelType() != null && !"".equals(debitForm.getChannelType())) {
                List<ChannelType> lstChannelType = (List<ChannelType>) channelTypeDAO.findByName(debitForm.getChannelType().trim());
                if (lstChannelType == null || lstChannelType.size() == 0) {
                    httpServletRequest.setAttribute("messageList", "ERR.LST.114");
                    return pageForward;
                } else {
                    Long channelTypeId = lstChannelType.get(0) != null ? lstChannelType.get(0).getChannelTypeId() : -1L;
                    strQuery += " and a.channelTypeId = ? ";
                    listParameter.add(channelTypeId);
                }
            }




//            if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("viewAllShopDebit"), httpServletRequest)) {
//
////                    ShopDAO shopDAO = new ShopDAO();
////                    shopDAO.setSession(this.getSession());
////                    Shop shop = shopDAO.findById(userToken.getShopId());
//                strQuery += " and a.ownerId in (select b.shopId from Shop b where 1=1 and b.status=1 and (b.shopPath like ? or b.shopId = ?))  ";
//                listParameter.add("%_" + userToken.getShopId() + "_%");
//                listParameter.add(userToken.getShopId());
//
//
//            }





//            strQuery += " and rownum < ? ";
//            listParameter.add(MAX_RESULT);

            strQuery += " order by a.code";

            Query query = getSession().createQuery(strQuery);
            for (int i = 0;
                    i < listParameter.size();
                    i++) {
                query.setParameter(i, listParameter.get(i));
            }
            listStockOwnerTmp = (List<StockOwnerTmp>) query.list();


            if (null == listStockOwnerTmp || 0 == listStockOwnerTmp.size()) {
                throw new Exception("Không tìm thấy kết quả thỏa mãn");


            }

            for (int i = 0; i
                    < listStockOwnerTmp.size(); i++) {
                StockOwnerTmp lst = (StockOwnerTmp) listStockOwnerTmp.get(i);
                lst.setStrOwnerType(this.getOwnerType(Long.parseLong(lst.getOwnerType())));
                //lst.setStrIsdnType(this.getIsdnType(lst.getIsdnType()));



            }

            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String templatePath = "";

            templatePath = tmp + "Debit_List.xls";
            filePath += "Debit_List_" + userToken.getLoginName() + "_" + date + ".xls";



            this.debitForm.setPathExpFile(filePath);

            String realPath = httpServletRequest.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = httpServletRequest.getSession().getServletContext().getRealPath(templatePath);
            /*Xử lý xuất file với nhiều sheet khi dữ liệu báo cáo lớn hơn 65000 dòng*/
            Map beans = new HashMap();
            List sheetNames = new ArrayList();
            List tempNames = new ArrayList();
            List maps = new ArrayList();
            String sheetName;
            String tempName;
            Long totalSheet = 20000L;
            int sheetNum = (int) Math.ceil(listStockOwnerTmp.size() / (float) totalSheet);

            if (listStockOwnerTmp.size() == 0) {
                tempName = "Sheet";
                sheetName = "Sheet 0";
                tempNames.add(tempName);
                sheetNames.add(sheetName);

                //Map beans = new HashMap();
                beans.put("lstIsdn", listStockOwnerTmp);
                maps.add(beans);
                //  beans.put("dateCreate", date);
//                        beans.put("fromDate", DateTimeUtils.convertDateTimeToString(DateTimeUtils.convertStringToDate(request.get("FROM_DATE").toString())));
//                        beans.put("toDate", DateTimeUtils.convertDateTimeToString(DateTimeUtils.convertStringToDate(request.get("TO_DATE").toString())));
//                        beans.put("shopName", request.get("SHOP_NAME"));
//                        beans.put("listVSaleTransDetail", reportDestroyTrans);
                //maps.add(beans);

            } else {
                List<StockOwnerTmp> displaySheet = new ArrayList<StockOwnerTmp>();
                Long begin;
                Long end;
                Long size = listStockOwnerTmp.size() * 1L;
                for (int i = 0; i < sheetNum; i++) {
                    displaySheet = new ArrayList();
                    begin = totalSheet * i;
                    end = totalSheet * (i + 1);
                    if (begin.compareTo(size) > 0) {
                        break;
                    }
                    if (end.compareTo(size) > 0) {
                        end = size;
                    }
                    displaySheet = listStockOwnerTmp.subList(begin.intValue(), end.intValue());
                    sheetName = "Sheet " + i;
                    tempName = "Sheet";
                    tempNames.add(tempName);
                    sheetNames.add(sheetName);



                    //  beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
                    // beans.put("userCreate", userToken.getFullName());
                    beans.put("lstIsdn", displaySheet);
                    maps.add(beans);
                }
            }

            HSSFWorkbook resultWorkbook = new HSSFWorkbook();
            XLSTransformer transformer = new XLSTransformer();
            java.io.InputStream is = new BufferedInputStream(new FileInputStream(templateRealPath));
            resultWorkbook = transformer.transformXLS(is, tempNames, sheetNames, maps);
            OutputStream os = new BufferedOutputStream(new FileOutputStream(realPath));
            resultWorkbook.write(os);
            os.close();
            httpServletRequest.setAttribute("pathExpFile", realPath);
            httpServletRequest.setAttribute("message", "MSG.FAC.Excel.Success");
            this.listDebit();



        } catch (Exception e) {
            httpServletRequest.setAttribute("message", e.getMessage());
            e.printStackTrace();


        }

        return pageForward;



    }

    public String getOwnerType(Long ownerType) {
        if (ownerType == 1L) {
            return ("MSG.channel.shop");


        } else if (ownerType == 2L) {
            return ("MES.CHL.103");


        } else {
            return ("MSG.SAE.097");


        }
    }

    public List<ImSearchBean> getListShopDebit(ImSearchBean imSearchBean) {


        String param = imSearchBean.getOtherParamValue();
        CommonDAO common = new CommonDAO();
        common.setSession(this.getSession());
        String[] arrParam = param.split(";");


        try {
            if (arrParam != null && arrParam.length > 0) {
                Long ownerType = Long.valueOf(arrParam[0]);


                if (ownerType == 1L) {
                    return this.getListShop(imSearchBean);


                } else if (ownerType == 2L) {
                    return this.getListStaff(imSearchBean);


                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();


        }



        return null;


    }

    public List<ImSearchBean> getShopNameDebit(ImSearchBean imSearchBean) {

        String param = imSearchBean.getOtherParamValue();
        CommonDAO common = new CommonDAO();
        common.setSession(this.getSession());
        String[] arrParam = param.split(";");


        try {
            if (arrParam != null && arrParam.length > 0) {
                Long ownerType = Long.valueOf(arrParam[0]);


                if (ownerType == 1L) {
                    return this.getShopName(imSearchBean);


                } else if (ownerType == 2L) {
                    return this.getStaffName(imSearchBean);


                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();


        }



        return null;



    }

    public Long getListShopSizeDebit(ImSearchBean imSearchBean) {


        String param = imSearchBean.getOtherParamValue();
        CommonDAO common = new CommonDAO();
        common.setSession(this.getSession());
        String[] arrParam = param.split(";");


        try {
            if (arrParam != null && arrParam.length > 0) {
                Long ownerType = Long.valueOf(arrParam[0]);


                if (ownerType == 1L) {
                    return this.getListShopSize(imSearchBean);


                } else if (ownerType == 2L) {
                    return this.getListStaffSize(imSearchBean);


                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();


        }



        return null;


    }

    public List<ImSearchBean> getListStaff(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

//        if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("viewAllShopDebit"), req)) {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());
        strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
        listParameter1.add(shop.getShopCode().trim().toLowerCase());
        //  }

        //strQuery1.append("and a.channelTypeId = ? ");
        //listParameter1.add(Constant.CHANNEL_TYPE_STAFF);

        //Modified by : TrongLV
        //Modified date : 2011-08-16
        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);


//        //tuannv hieu chinh ngay 02/04/2010: cho phep tim kiem ca kho CTV/DB/DL
//        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=?) ");
//        listParameter1.add(Constant.OWNER_TYPE_STAFF.toString());



        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");


        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");


        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(100L);

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());


        for (int i = 0; i
                < listParameter1.size(); i++) {
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
     * purpose  : lay so luong cac nhan vien thuoc kho
     *
     */
    public Long getListStaffSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

//        if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("viewAllShopDebit"), req)) {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());
        strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
        listParameter1.add(shop.getShopCode().trim().toLowerCase());
        // }

//        strQuery1.append("and a.channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);

        //Modified by : TrongLV
        //Modified date : 2011-08-16
        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);


        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");


        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");


        }

        Query query1 = getSession().createQuery(strQuery1.toString());


        for (int i = 0; i
                < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));


        }

        List<Long> tmpList1 = query1.list();


        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);


        } else {
            return null;


        }

    }

    public List<ImSearchBean> getStaffName(ImSearchBean imSearchBean) {
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
        strQuery1.append("and status = 1 ");

//        if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("viewAllShopDebit"), req)) {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());
        strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
        listParameter1.add(shop.getShopCode().trim().toLowerCase());
        //   }

//        strQuery1.append("and a.channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);

        //Modified by : TrongLV
        //Modified date : 2011-08-16
        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);

        strQuery1.append("and lower(a.staffCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(100L);

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());


        for (int i = 0; i
                < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));


        }

        List<ImSearchBean> tmpList1 = query1.list();


        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);


        }

        return listImSearchBean;


    }

    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        String param = imSearchBean.getOtherParamValue();


        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");


        if (param != null) {
            String parentCode = String.valueOf(param);
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(parentCode);
            strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
            listParameter1.add("%_" + shop.getShopId() + "_%");
            listParameter1.add(shop.getShopId());


        }



//        if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("viewAllShopDebit"), req)) {

        //  }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");


        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");


        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(100L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());


        for (int i = 0; i
                < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));


        }

        List<ImSearchBean> tmpList1 = query1.list();


        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);


        }

        return listImSearchBean;


    }

    public Long getListShopSize(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String param = imSearchBean.getOtherParamValue();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");


        if (param != null) {
            String parentCode = String.valueOf(param);
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(parentCode);
            strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
            listParameter1.add("%_" + shop.getShopId() + "_%");
            listParameter1.add(shop.getShopId());


        }

//        if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("viewAllShopDebit"), req)) {

        //  }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");


        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");


        }

        Query query1 = getSession().createQuery(strQuery1.toString());


        for (int i = 0; i
                < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));


        }

        List<Long> tmpList1 = query1.list();


        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);


        } else {
            return null;


        }
    }

    public List<ImSearchBean> getShopName(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String param = imSearchBean.getOtherParamValue();

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();



        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;


        }

        //lay ten cua shop dua tren code
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");


        if (param != null) {
            String parentCode = String.valueOf(param);
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(parentCode);
            strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
            listParameter1.add("%_" + shop.getShopId() + "_%");
            listParameter1.add(shop.getShopId());


        }

//        if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("viewAllShopDebit"), req)) {

        //     }


        strQuery1.append("and lower(a.shopCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(100L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());


        for (int i = 0; i
                < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));


        }

        List<ImSearchBean> tmpList1 = query1.list();


        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);


        }

        return listImSearchBean;

    }

    public String resetForm() throws Exception {
        String forwardPage = "viewAssignDebit";
        UserToken userToken = (UserToken) getRequest().getSession().getAttribute("userToken");
//Lay ten ShopName và ShopCode
        Long shopId = userToken.getShopId();
        String strShopQuery = " from Shop a where a.shopId = ? and status = ? ";
        Query shopQuery = getSession().createQuery(strShopQuery);
        shopQuery.setParameter(0, shopId);
        shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
        List<Shop> listShop = shopQuery.list();
        debitForm.setParentName(listShop.get(0).getName());
        debitForm.setParentCode(listShop.get(0).getShopCode());
//        debitForm.setParentName("");
//        debitForm.setParentCode("");
        debitForm.setShopName("");
        debitForm.setShopCode("");
        debitForm.setStatus("");
        debitForm.setChannelParent("");
        debitForm.setCode("");
        debitForm.setMaxDebit("");
        debitForm.setDateReset("");
        debitForm.setChannelType("");
        debitForm.setOwnerType("");
        //this.listDebit();
        return forwardPage;






    }

    public String updateProgressDiv(HttpServletRequest req) {
        log.info("Begin updateProgressDiv of StockIsdnDAO");

        try {
            String userSessionId = req.getSession().getId();
            List<String> listMessage = this.listProgressMessage.get(userSessionId);
            if (listMessage != null && listMessage.size() > 0) {
                String message = listMessage.get(0);
                listMessage.remove(0);
                return message;
            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
