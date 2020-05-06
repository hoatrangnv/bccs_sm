/*
 * Copyright YYYY Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.StockOwnerTmpBean;
import com.viettel.im.client.form.StockOwnerTmpForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.ErrorChangeChannelBean;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockOwnerTmp;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockOwnerTmp entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.StockOwnerTmp
 * @author MyEclipse Persistence Tools
 */
public class StockOwnerTmpDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(StockOwnerTmpDAO.class);
    private StockOwnerTmpForm stockOwnerTmpForm = new StockOwnerTmpForm();
    // property constants
    private String pageForward;
    public static final String ADD_SINGLE_STOCK_OWNER_TMP = "addSingleStockOwnerTmp";
    public static final String LIST_STOCK_OWNER_TMP = "listStockOwnerTmp";
    public static final String ADD_STOCK_OWNER_TMP_BY_FILE = "addStockOwnerTmpByFile";
    public static final String OWNER_ID = "ownerId";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String OWNER_TYPE = "ownerType";
    public static final String CHANNEL_TYPE_ID = "channelTypeId";

    public StockOwnerTmpForm getStockOwnerTmpForm() {
        return stockOwnerTmpForm;
    }

    public void setStockOwnerTmpForm(StockOwnerTmpForm stockOwnerTmpForm) {
        this.stockOwnerTmpForm = stockOwnerTmpForm;
    }

    public void save(StockOwnerTmp transientInstance) {
        log.debug("saving StockOwnerTmp instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(StockOwnerTmp persistentInstance) {
        log.debug("deleting StockOwnerTmp instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public StockOwnerTmp findById(java.lang.Long id) {
        log.debug("getting StockOwnerTmp instance with id: " + id);
        try {
            StockOwnerTmp instance = (StockOwnerTmp) getSession().get(
                    "com.viettel.im.database.BO.StockOwnerTmp", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(StockOwnerTmp instance) {
        log.debug("finding StockOwnerTmp instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.StockOwnerTmp").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding StockOwnerTmp instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from StockOwnerTmp as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByOwnerId(Object ownerId) {
        return findByProperty(OWNER_ID, ownerId);
    }

    public List findByCode(Object code) {
        return findByProperty(CODE, code);
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByOwnerType(Object ownerType) {
        return findByProperty(OWNER_TYPE, ownerType);
    }

    public List findByChannelTypeId(Object channelTypeId) {
        return findByProperty(CHANNEL_TYPE_ID, channelTypeId);
    }

    public List findAll() {
        log.debug("finding all StockOwnerTmp instances");
        try {
            String queryString = "from StockOwnerTmp";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public StockOwnerTmp merge(StockOwnerTmp detachedInstance) {
        log.debug("merging StockOwnerTmp instance");
        try {
            StockOwnerTmp result = (StockOwnerTmp) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(StockOwnerTmp instance) {
        log.debug("attaching dirty StockOwnerTmp instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(StockOwnerTmp instance) {
        log.debug("attaching clean StockOwnerTmp instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public Shop getShop(String shopCode) throws Exception {
        if (shopCode == null || shopCode.trim().equals("")) {
            return null;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCodeNotStatus(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop;
        }
        return null;
    }

    public Staff getStaff(String staffCode) throws Exception {
        if (staffCode == null || staffCode.trim().equals("")) {
            return null;
        }
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCodeNotStatus(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff;
        }
        return null;
    }

    public boolean checkShopUnder(Long shopIdLogin, Long shopIdSelect) {
        if (shopIdLogin.equals(shopIdSelect)) {
            return true;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shopLogin = shopDAO.findById(shopIdLogin);
        Shop shopSelect = shopDAO.findById(shopIdSelect);
        if (shopSelect.getShopPath().indexOf(shopLogin.getShopPath() + "_") < 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean existStockOwnerTmp(Long ownerId, String ownerType) throws Exception {
        String sql = "From StockOwnerTmp where ownerId = ? and ownerType = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        List<StockOwnerTmp> list = query.list();
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public StockOwnerTmp findByOwnerIdAndOwnerType(Long ownerId, String ownerType) throws Exception {
        String sql = "From StockOwnerTmp where ownerId = ? and ownerType = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        List list = query.list();
        if (list != null && list.size() > 0) {
            return (StockOwnerTmp) list.get(0);
        }
        return null;
    }

    public StockOwnerTmpBean validStockOwnerTmp(String shopCode, String staffCode, Long channelTypeId, Long currentShopId) throws Exception {
        List lstParam = new ArrayList();
        String sql = "SELECT new com.viettel.im.client.bean.StockOwnerTmpBean(st.staffId, st.staffCode, st.name, st.channelTypeId) "
                + " FROM Staff st, Shop sh WHERE st.shopId = sh.shopId AND st.status = ? "
                + " AND st.staffCode = ? AND st.channelTypeId = ? "
                + " AND sh.shopCode = ? AND LOWER(sh.shopPath || '_') LIKE LOWER(?)";
        lstParam.add(Constant.STATUS_USE);
        lstParam.add(staffCode);
        lstParam.add(channelTypeId);
        lstParam.add(shopCode);
        lstParam.add("_" + currentShopId.toString() + "_");

        sql += " AND sh.channelTypeId IN (SELECT channelTypeId FROM ChannelType WHERE status = ? AND isPrivate = ? AND isVtUnit = ? AND objectType = ?) ";
        lstParam.add(Constant.STATUS_USE);
        lstParam.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        lstParam.add(Constant.IS_VT_UNIT);
        lstParam.add(Constant.OBJECT_TYPE_SHOP);

        Query query = getSession().createQuery(sql.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        List lst = query.list();
        if (lst != null && !lst.isEmpty()) {
            return (StockOwnerTmpBean) lst.get(0);
        }
        return null;
    }

    public Long getShopId(String shopCode) throws Exception {
        if (shopCode == null) {
            return 0L;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop.getShopId();
        }
        return 0L;
    }

    public Long getStaffId(String staffCode) throws Exception {
        if (staffCode == null) {
            return 0L;
        }
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff.getStaffId();
        }
        return 0L;
    }

    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String shopCode = imSearchBean.getCode() == null ? "" : imSearchBean.getCode().trim();//req.getParameter("code");
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List lstParam = new ArrayList();
        StringBuilder sql = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");

        sql.append(" FROM Shop a WHERE LOWER(a.shopPath || '_') LIKE LOWER(?)");
        lstParam.add("%_" + userToken.getShopId().toString() + "_%");

        if (shopCode != null && !"".equals(shopCode)) {
            sql.append(" AND LOWER(a.shopCode) LIKE ? ");
            lstParam.add("%" + shopCode.toLowerCase() + "%");
        }

        sql.append(" and a.status =? ");
        lstParam.add(Constant.STATUS_USE);
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            sql.append(" AND LOWER(a.name) LIKE ? ");
            lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        sql.append(" AND a.channelTypeId IN (SELECT channelTypeId FROM ChannelType WHERE status = ? AND isPrivate = ? AND isVtUnit = ? AND objectType = ?) ");
        lstParam.add(Constant.STATUS_USE);
        lstParam.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        lstParam.add(Constant.IS_VT_UNIT);
        lstParam.add(Constant.OBJECT_TYPE_SHOP);

        sql.append(" ORDER BY a.shopCode");
        Query query = getSession().createQuery(sql.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        listImSearchBean = query.list();

        return listImSearchBean;
    }

    public List<ImSearchBean> getShopName(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String shopCode = imSearchBean.getCode() == null ? "" : imSearchBean.getCode().trim();//req.getParameter("code");
        List lstParam = new ArrayList();
        StringBuilder sql = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");

        sql.append(" FROM Shop a WHERE LOWER(a.shopCode) = ? AND LOWER(a.shopPath || '_') LIKE LOWER(?)");
        lstParam.add(shopCode.trim().toLowerCase());
        lstParam.add("%_" + userToken.getShopId().toString() + "_%");

        sql.append(" AND a.status = ? ");
        lstParam.add(Constant.STATUS_USE);

        sql.append(" AND a.channelTypeId IN (SELECT channelTypeId FROM ChannelType WHERE status = ? AND isPrivate = ? AND isVtUnit = ? AND objectType = ?) ");
        lstParam.add(Constant.STATUS_USE);
        lstParam.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        lstParam.add(Constant.IS_VT_UNIT);
        lstParam.add(Constant.OBJECT_TYPE_SHOP);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        Query query = getSession().createQuery(sql.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        listImSearchBean = query.list();
        return listImSearchBean;
    }

    public List<ImSearchBean> getListStaff(ImSearchBean imSearchBean) throws Exception {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List lstParam = new ArrayList();
        String sql = "select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) "
                + "from Staff a WHERE a.status = ? ";
        lstParam.add(Constant.STATUS_USE);
        if (imSearchBean.getOtherParamValue() != null
                && !imSearchBean.getOtherParamValue().trim().equals("")
                && imSearchBean.getOtherParamValue().indexOf(";") > 0) {

            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");

            sql += " AND a.shopId = ? ";
            String shopCode = otherParam.substring(0, index).toLowerCase();
            lstParam.add(getShopId(shopCode));

            if (index == otherParam.length() - 1) {
                //truong hop chua chon channelType
                sql += " AND a.channelTypeId IN (SELECT channelTypeId FROM ChannelType WHERE status = ? AND isPrivate = ? AND isVtUnit = ? AND objectType = ?) ";
                lstParam.add(Constant.STATUS_USE);
                lstParam.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
                lstParam.add(Constant.IS_VT_UNIT);
                lstParam.add(Constant.OBJECT_TYPE_STAFF);
            } else {
                String channelTypeId = otherParam.substring(index + 1, otherParam.length()).toLowerCase();
                sql += " AND a.channelTypeId = ? ";
                lstParam.add(Long.parseLong(channelTypeId));
            }
        } else {
            //truong hop chua co shop -> tra ve list rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            sql += " and lower(a.staffCode) like ? ";
            lstParam.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            sql += " and lower(a.name) like ? ";
            lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        sql += " and rownum < ? order by a.staffCode";
        lstParam.add(300L);

        Query query = getSession().createQuery(sql);
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }

        List<ImSearchBean> tmpList = query.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }

        return listImSearchBean;
    }

    public List<ImSearchBean> getStaffName(ImSearchBean imSearchBean) throws Exception {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List lstParam = new ArrayList();
        String sql = "select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) "
                + "from Staff a WHERE a.status = ? ";
        lstParam.add(Constant.STATUS_USE);
        if (imSearchBean.getOtherParamValue() != null
                && !imSearchBean.getOtherParamValue().trim().equals("")
                && imSearchBean.getOtherParamValue().indexOf(";") > 0) {

            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");

            sql += " AND a.shopId = ? ";
            String shopCode = otherParam.substring(0, index).toLowerCase();
            lstParam.add(getShopId(shopCode));

            if (index == otherParam.length() - 1) {
                //truong hop chua chon channelType
                sql += " AND a.channelTypeId IN (SELECT channelTypeId FROM ChannelType WHERE status = ? AND isPrivate = ? AND isVtUnit = ? AND objectType = ?) ";
                lstParam.add(Constant.STATUS_USE);
                lstParam.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
                lstParam.add(Constant.IS_VT_UNIT);
                lstParam.add(Constant.OBJECT_TYPE_STAFF);
            } else {
                String channelTypeId = otherParam.substring(index + 1, otherParam.length()).toLowerCase();
                sql += " AND a.channelTypeId = ? ";
                lstParam.add(Long.parseLong(channelTypeId));
            }
        } else {
            //truong hop chua co shop -> tra ve list rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            sql += " and lower(a.staffCode) = ? ";
            lstParam.add(imSearchBean.getCode().trim().toLowerCase());
        }

        Query query = getSession().createQuery(sql);
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }

        List<ImSearchBean> tmpList = query.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }

        return listImSearchBean;
    }

    public List<StockOwnerTmpBean> findDefault() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        String sql = "SELECT new com.viettel.im.client.bean.StockOwnerTmpBean(so.stockId, sh.shopCode, sh.name,"
                + " st.staffCode, st.name, ct.name, so.currentDebit, so.maxDebit) "
                + " FROM StockOwnerTmp so, Staff st, Shop sh, ChannelType ct WHERE so.ownerId = st.staffId "
                + " AND st.channelTypeId = ct.channelTypeId AND st.shopId = sh.shopId AND st.status = ? AND so.ownerType = ? ";
        List lstParam = new ArrayList();
        lstParam.add(Constant.STATUS_USE);
        lstParam.add(Constant.OWNER_TYPE_STAFF.toString());

        sql += " AND LOWER(sh.shopPath || '_') LIKE LOWER(?)";
        lstParam.add("%_" + userToken.getShopId().toString() + "_%");

        sql += " AND st.channelTypeId IN (SELECT channelTypeId FROM ChannelType WHERE status = ? AND isPrivate = ? AND isVtUnit = ? AND objectType = ?) ";
        lstParam.add(Constant.STATUS_USE);
        lstParam.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        lstParam.add(Constant.IS_VT_UNIT);
        lstParam.add(Constant.OBJECT_TYPE_STAFF);
        sql += " AND rownum < ? ORDER BY st.staffCode";
        lstParam.add(300L);

        Query query = getSession().createQuery(sql);
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        List<StockOwnerTmpBean> lstSO = query.list();
        if (lstSO != null && !lstSO.isEmpty()) {
            return lstSO;
        }
        return new ArrayList();
    }

    public List<StockOwnerTmpBean> findStockOwnerTmp(String shopCode, String staffCode, Long channelTypeId, String strMaxDebit) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Double maxDebit = 0D;
        if (strMaxDebit != null && !"".equals(strMaxDebit)) {
            maxDebit = Double.parseDouble(strMaxDebit);
        }

        String sql = "SELECT new com.viettel.im.client.bean.StockOwnerTmpBean(so.stockId, sh.shopCode, sh.name,"
                + " st.staffCode, st.name, ct.name, so.currentDebit, so.maxDebit) "
                + " FROM StockOwnerTmp so, Staff st, Shop sh, ChannelType ct WHERE so.ownerId = st.staffId "
                + " AND st.channelTypeId = ct.channelTypeId AND st.shopId = sh.shopId AND st.status = ? AND so.ownerType = ? ";
        List lstParam = new ArrayList();
        lstParam.add(Constant.STATUS_USE);
        lstParam.add(Constant.OWNER_TYPE_STAFF.toString());

        sql += " AND LOWER(sh.shopPath || '_') LIKE LOWER(?)";
        lstParam.add("%_" + userToken.getShopId().toString() + "_%");

        if (shopCode != null && !"".equals(shopCode)) {
            sql += " AND LOWER(sh.shopCode) = ? ";
            lstParam.add(shopCode.toLowerCase().trim());
        }

        if (channelTypeId != null) {
            sql += " AND st.channelTypeId = ? ";
            lstParam.add(channelTypeId);
        } else {
            sql += " AND st.channelTypeId IN (SELECT channelTypeId FROM ChannelType WHERE status = ? AND isPrivate = ? AND isVtUnit = ? AND objectType = ?) ";
            lstParam.add(Constant.STATUS_USE);
            lstParam.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
            lstParam.add(Constant.IS_VT_UNIT);
            lstParam.add(Constant.OBJECT_TYPE_STAFF);
        }
        if (staffCode != null && !"".equals(staffCode)) {
            sql += " AND LOWER(st.staffCode) = ? ";
            lstParam.add(staffCode.toLowerCase().trim());
        }
        if (maxDebit != null && maxDebit > 0) {
            sql += " AND so.maxDebit = ? ";
            lstParam.add(maxDebit);
        }
        
        sql += " AND rownum < ? ORDER BY st.staffCode";
        lstParam.add(300L);
        Query query = getSession().createQuery(sql);
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        List<StockOwnerTmpBean> lstSO = query.list();
        if (lstSO != null && !lstSO.isEmpty()) {
            //Lay lai gia tri kho hien tai cua tung nhan vien
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            for (int i = 0; i < lstSO.size() ; i ++) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(lstSO.get(i).getStaffCode());
                Shop shop = shopDAO.findShopsAvailableByShopCodeNotStatus(lstSO.get(i).getShopCode());
                if (staff != null && shop != null && shop.getPricePolicy() != null) {
                    Double currentDebit = getCurrentDebit(2L, staff.getStaffId(), shop.getPricePolicy());
                    if (currentDebit != null) {
                        lstSO.get(i).setCurrentDebit(currentDebit);
                        lstSO.get(i).setStrCurrentDebit(BigDecimal.valueOf(currentDebit).toString());
                    }
                }
            }
            return lstSO;
        }
        return new ArrayList();
    }

    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = ADD_SINGLE_STOCK_OWNER_TMP;
        this.stockOwnerTmpForm.setShopCode(userToken.getShopCode());
        this.stockOwnerTmpForm.setShopName(userToken.getShopName());
        this.stockOwnerTmpForm.setStaffCode("");
        this.stockOwnerTmpForm.setStaffName("");
        this.stockOwnerTmpForm.setStockId(null);
        this.stockOwnerTmpForm.setMaxDebit("");
        this.stockOwnerTmpForm.setChannelTypeId(null);

        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findByObjectTypeAndIsVtUnitAndIsPrivate(Constant.OBJECT_TYPE_STAFF,
                Constant.IS_VT_UNIT, Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        req.getSession().setAttribute("listChannelType", listChannelType);
        List<StockOwnerTmpBean> lstSO = findStockOwnerTmp(this.stockOwnerTmpForm.getShopCode(),
                this.stockOwnerTmpForm.getStaffCode(), this.stockOwnerTmpForm.getChannelTypeId(),
                this.stockOwnerTmpForm.getMaxDebit());
        req.getSession().setAttribute("listStockOwnerTmpBean", lstSO);
        return pageForward;
    }

    public String searchStockOwnerTmp() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = ADD_SINGLE_STOCK_OWNER_TMP;
        this.stockOwnerTmpForm.setPrevShopCode(this.stockOwnerTmpForm.getShopCode());
        this.stockOwnerTmpForm.setPrevStaffCode(this.stockOwnerTmpForm.getStaffCode());
        this.stockOwnerTmpForm.setPrevChannelTypeId(this.stockOwnerTmpForm.getChannelTypeId());
        this.stockOwnerTmpForm.setPrevMaxDebit(this.stockOwnerTmpForm.getMaxDebit());
        List<StockOwnerTmpBean> lstSO = findStockOwnerTmp(this.stockOwnerTmpForm.getPrevShopCode(),
                this.stockOwnerTmpForm.getPrevStaffCode(), this.stockOwnerTmpForm.getPrevChannelTypeId(),
                this.stockOwnerTmpForm.getPrevMaxDebit());
        req.getSession().setAttribute("listStockOwnerTmpBean", lstSO);
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        pageForward = LIST_STOCK_OWNER_TMP;
        return pageForward;
    }

    public String editStockOwnerTmp() throws Exception {
        pageForward = ADD_SINGLE_STOCK_OWNER_TMP;
        HttpServletRequest req = getRequest();
        Long stockId = Long.parseLong(req.getParameter("stockId"));
        StockOwnerTmp so = findById(stockId);
        if (so != null) {
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            Staff staff = staffDAO.findById(so.getOwnerId());
            if (staff != null) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());
                Shop sh = shopDAO.findById(staff.getShopId());
                if (sh != null) {
                    this.stockOwnerTmpForm.setShopCode(sh.getShopCode());
                    this.stockOwnerTmpForm.setShopName(sh.getName());
                }
                this.stockOwnerTmpForm.setStaffCode(staff.getStaffCode());
                this.stockOwnerTmpForm.setStaffName(staff.getName());
            }
            this.stockOwnerTmpForm.setStockId(stockId);
            this.stockOwnerTmpForm.setChannelTypeId(so.getChannelTypeId());
            if (so.getMaxDebit() != null) {
                this.stockOwnerTmpForm.setMaxDebit(BigDecimal.valueOf(so.getMaxDebit()).toString());
            } else {
                this.stockOwnerTmpForm.setMaxDebit("0");
            }
            req.setAttribute("isUpdating", "true");
        }
        return pageForward;
    }

    public String addStockOwnerTmp() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = ADD_SINGLE_STOCK_OWNER_TMP;

        StockOwnerTmpBean so = validStockOwnerTmp(this.stockOwnerTmpForm.getShopCode().trim(),
                this.stockOwnerTmpForm.getStaffCode().trim(),
                this.stockOwnerTmpForm.getChannelTypeId(),
                userToken.getShopId());
        if (so != null) {
            if (existStockOwnerTmp(so.getOwnerId(), Constant.OWNER_TYPE_STAFF.toString())) {
                /*Đã tồn tại bản ghi hạn mức*/
                req.setAttribute("returnMsg", getText("ERR.LIMIT.0006"));
            } else {
                StockOwnerTmp sto = new StockOwnerTmp();
                sto.setStockId(getSequence("STOCK_OWNER_TMP_SEQ"));
                sto.setOwnerId(so.getOwnerId());
                sto.setOwnerType(Constant.OWNER_TYPE_STAFF.toString());
                sto.setCode(so.getStaffCode());
                sto.setName(so.getStaffName());
                sto.setChannelTypeId(so.getChannelTypeId());
                sto.setMaxDebit(Double.parseDouble(this.stockOwnerTmpForm.getMaxDebit()));
                getSession().save(sto);
                /*Thêm mới thành công*/
                req.setAttribute("returnMsg", getText("ERR.CHL.099"));

                this.stockOwnerTmpForm.setStockId(null);
                this.stockOwnerTmpForm.setStaffCode("");
                this.stockOwnerTmpForm.setStaffName("");
                this.stockOwnerTmpForm.setMaxDebit(null);
            }
        } else {
            /*Dữ liệu nhập không hợp lệ*/
            req.setAttribute("returnMsg", getText("ERR.LIMIT.0007"));
        }

        List<StockOwnerTmpBean> lstSO = findStockOwnerTmp(this.stockOwnerTmpForm.getPrevShopCode(),
                this.stockOwnerTmpForm.getPrevStaffCode(), this.stockOwnerTmpForm.getPrevChannelTypeId(),
                this.stockOwnerTmpForm.getMaxDebit());
        req.setAttribute("listStockOwnerTmpBean", lstSO);
        return pageForward;
    }

    public String updateStockOwnerTmp() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = ADD_SINGLE_STOCK_OWNER_TMP;
        StockOwnerTmp so = findById(this.stockOwnerTmpForm.getStockId());
        this.stockOwnerTmpForm.setChannelTypeId(so.getChannelTypeId());
        if (so.getCurrentDebit() != null) {
            if (so.getCurrentDebit() <= Double.parseDouble(this.stockOwnerTmpForm.getMaxDebit())) {
                if ((so.getMaxDebit() == null || this.stockOwnerTmpForm.getMaxDebit() == null)
                        || (so.getMaxDebit() != null && this.stockOwnerTmpForm.getMaxDebit() != null && !so.getMaxDebit().equals(Double.parseDouble(this.stockOwnerTmpForm.getMaxDebit())))) {
                    if (this.stockOwnerTmpForm.getMaxDebit() != null) {
                        so.setMaxDebit(Double.parseDouble(this.stockOwnerTmpForm.getMaxDebit()));
                    } else {
                        so.setMaxDebit(null);
                    }

                    getSession().save(so);
                    List<StockOwnerTmpBean> lstSO = findStockOwnerTmp(this.stockOwnerTmpForm.getPrevShopCode(),
                            this.stockOwnerTmpForm.getPrevStaffCode(), this.stockOwnerTmpForm.getPrevChannelTypeId(),
                            this.stockOwnerTmpForm.getPrevMaxDebit());
                    req.setAttribute("listStockOwnerTmpBean", lstSO);
                    req.setAttribute("returnMsg", getText("ERR.CHL.060"));
                    // cap nhat thanh cong 
                    stockOwnerTmpForm.resetForm();
                    req.setAttribute("isUpdating", null);
                } else {
                    req.setAttribute("returnMsg", getText("ERR.LIMIT.0016"));
                }
            } else {
                /*Tổng GTHH hiện tại phải nhỏ hơn hạn mức*/
                req.setAttribute("returnMsg", getText("ERR.LIMIT.0008"));
            }
        } else {
            if ((this.stockOwnerTmpForm.getMaxDebit() == null || so.getMaxDebit() == null)
                    || (so.getMaxDebit() != null && this.stockOwnerTmpForm.getMaxDebit() != null && !so.getMaxDebit().equals(Double.parseDouble(this.stockOwnerTmpForm.getMaxDebit())))) {
                if (this.stockOwnerTmpForm.getMaxDebit() != null) {
                    so.setMaxDebit(Double.parseDouble(this.stockOwnerTmpForm.getMaxDebit()));
                } else {
                    so.setMaxDebit(null);
                }
                this.stockOwnerTmpForm.setMaxDebit(null);
                getSession().save(so);
                List<StockOwnerTmpBean> lstSO = findStockOwnerTmp(this.stockOwnerTmpForm.getPrevShopCode(),
                        this.stockOwnerTmpForm.getPrevStaffCode(), this.stockOwnerTmpForm.getPrevChannelTypeId(),
                        this.stockOwnerTmpForm.getPrevMaxDebit());
                req.setAttribute("listStockOwnerTmpBean", lstSO);
                req.setAttribute("returnMsg", getText("ERR.CHL.060"));
            } else {
                req.setAttribute("returnMsg", getText("ERR.LIMIT.0016"));
            }
        }

        

        return pageForward;
    }

    /*
     * Gán hạn mức cho nhân viên theo file
     */
    public String preparePageByFile() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = ADD_STOCK_OWNER_TMP_BY_FILE;
        this.stockOwnerTmpForm.setShopCode(userToken.getShopCode());
        this.stockOwnerTmpForm.setShopName(userToken.getShopName());
        return pageForward;
    }

    public String viewFile() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = ADD_STOCK_OWNER_TMP_BY_FILE;

        String serverFileName = CommonDAO.getSafeFileName(stockOwnerTmpForm.getServerFileName());
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 10);
        if (list == null) {
            //Kiểm tra lại file Import đã giống template chưa, phải đặt tên sheet của file Import là Sheet1
            req.setAttribute("returnMsg", "ERR.CHL.059");
            return pageForward;
        }
        List<StockOwnerTmpBean> lstSO = new ArrayList<StockOwnerTmpBean>();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            String shopCode = object[0].toString();
            String staffCode = object[1].toString();
            String strMaxDebit = object[2].toString();
            Double maxDebit = null;
            try {
                maxDebit = Double.parseDouble(strMaxDebit);
            } catch (Exception ex) {
            }
            StockOwnerTmpBean so = new StockOwnerTmpBean(null, shopCode, ".........", staffCode, ".........", ".........", null, maxDebit);
            so.setStrMaxDebit(strMaxDebit);
            lstSO.add(so);
        }
        req.setAttribute("listStockOwnerTmpBeanInFile", lstSO);
        return pageForward;
    }

    public String updateStockOwnerTmpByFile() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = ADD_STOCK_OWNER_TMP_BY_FILE;
        String serverFileName = CommonDAO.getSafeFileName(stockOwnerTmpForm.getServerFileName());
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 10);
        if (list == null) {
            //Kiểm tra lại file Import đã giống template chưa, phải đặt tên sheet của file Import là Sheet1
            req.setAttribute("returnMsg", "ERR.CHL.059");
            return pageForward;
        }
        String error = "";
        List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
        int countSuccess = 0;
        int total = list.size();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            String shopCode = object[0].toString();
            String staffCode = object[1].toString();
            String strMaxDebit = object[2].toString();
            Double maxDebit = null;
            error = "";
            if (shopCode == null || "".equals(shopCode)) {
                //error = "Chưa nhập Mã cửa hàng";
                error += ";" + getText("ERR.RET.033");
            }
            if (staffCode == null || "".equals(staffCode)) {
                //error = "Chưa nhập mã nhân viên";
                error += ";" + getText("ERR.LIMIT.0003");
            }
            if (shopCode != null && !"".equals(shopCode) && staffCode != null && !"".equals(staffCode)) {
                Shop shop = getShop(shopCode);
                if (shop == null) {
                    //error = "Mã cửa hàng không chính xác";
                    error += ";" + getText("ERR.DET.066");
                }

                if (shop != null && shop.getStatus() != null && shop.getStatus().equals(0L)) {
                    //error = "Mã cửa hàng đang ở trạng thái ngưng hoạt động";
                    error += ";" + getText("ERR.CHL.046");
                }

                if (shop != null && !checkShopUnder(userToken.getShopId(), shop.getShopId())) {
                    //error = "Mã cửa hàng không phải mã con của cửa hàng user đăng nhập";
                    error += ";" + getText("ERR.CHL.056");
                }
                Staff staff = getStaff(staffCode);
                if (staff == null) {
                    //error = "Mã nhân viên không chính xác";
                    error += ";" + getText("ERR.CHL.141");
                }
                if (staff != null && staff.getStatus().equals(0L)) {
                    //error = "Mã nhân viên đang ở trạng thái ngưng hoạt động";
                    error += ";" + getText("ERR.LIMIT.0009");
                }
                if (shop != null && staff != null && !staff.getShopId().equals(shop.getShopId())) {
                    //Nhân viên không thuộc cửa hàng đã nhập
                    error += ";" + getText("ERR.CHL.144");
                }
                try {
                    if (strMaxDebit == null || "".equals(strMaxDebit)) {
                        error += ";" + getText("ERR.LIMIT.0004");
                    } else {
                        strMaxDebit = strMaxDebit.replace(",", ".");
                        maxDebit = Double.parseDouble(strMaxDebit);
                        if (maxDebit < 0) {
                            //han muc ko duoc nhap so am
                            error += ";" + getText("ERR.LIMIT.0010");
                        }
                    }
                } catch (Exception ex) {
                    //han muc nhap khong hop le
                    error += ";" + getText("ERR.LIMIT.0005");
                }

                if ("".equals(error)) {
                    StockOwnerTmp sot = findByOwnerIdAndOwnerType(staff.getStaffId(), Constant.OWNER_TYPE_STAFF.toString());
                    if (sot != null) {
                        if (sot.getCurrentDebit() != null && maxDebit < sot.getCurrentDebit()) {
                            //han muc nho hon gia tri hang hoa hien tai
                            error += ";" + getText("ERR.LIMIT.0011");
                        } else {

                            countSuccess++;
                            sot.setMaxDebit(maxDebit);
                            getSession().update(sot);

                        }
                    } else {
                        countSuccess++;
                        sot = new StockOwnerTmp();
                        sot.setStockId(getSequence("STOCK_OWNER_TMP_SEQ"));
                        sot.setOwnerId(staff.getStaffId());
                        sot.setOwnerType(Constant.OWNER_TYPE_STAFF.toString());
                        sot.setCode(staff.getStaffCode());
                        sot.setName(staff.getName());
                        sot.setChannelTypeId(staff.getChannelTypeId());
                        sot.setMaxDebit(maxDebit);
                        getSession().save(sot);
                    }
                }
            }
            if (!"".equals(error)) {
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                errorBean.setOwnerCode(shopCode + ":" + staffCode + ":" + strMaxDebit);
                errorBean.setError(error);
                listError.add(errorBean);
            }
        }
        if (countSuccess > 0) {
            req.setAttribute("returnMsg", "ERR.LIMIT.0015");
            List listParam = new ArrayList();
            listParam.add(String.valueOf(countSuccess) + "/" + String.valueOf(total));
            req.setAttribute("paramMsg", listParam);
        } else {
            req.setAttribute("returnMsg", "ERR.LIMIT.0017");
        }
        if (countSuccess < total) {
            downloadFile("errorUpdateStockOwnerTmpByFile", listError);
        }
        return pageForward;
    }
    //download danh sach file loi ve

    public void downloadFile(String templatePathResource, List listReport) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
        filePath = filePath != null ? filePath : "/";
        filePath += templatePathResource + "_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String templatePath = ResourceBundleUtils.getResource(templatePathResource);
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        Map beans = new HashMap();
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        req.setAttribute("exportPath", filePath);
        //"Nếu hệ thống không tự download. Click vào đây để tải File lưu thông tin không cập nhật được");
        req.setAttribute("exportMessage", "ERR.CHL.102");
    }
}