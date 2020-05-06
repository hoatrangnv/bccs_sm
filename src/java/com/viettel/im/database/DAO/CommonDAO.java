package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;

import com.viettel.im.client.bean.CalculateFeeBean;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jxl.Sheet;
import jxl.Workbook;
import jxl.Cell;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.viettel.im.client.bean.ComboListBean;
import com.viettel.im.client.bean.DateTimeDB;
import com.viettel.im.client.bean.ImObject;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.BankAccount;
import com.viettel.im.database.BO.BankAccountGroup;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ThanhNC
 */
public class CommonDAO extends BaseDAOAction {

    protected static final String USER_TOKEN_ATTRIBUTE = "userToken";
    private final Long MAX_SEARCH_RESULT = 100L; //gioi han so row tra ve doi voi tim kiem

    /**
     *
     * author   : tamdt1
     * date     : 21/10/2010
     * purpose  : lay danh sach cac loai hoa don
     *
     */
    public List<ImSearchBean> getListBookType(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cac loai hoa don
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.serialCode, a.blockName) ");
        strQuery1.append("from BookType a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.invoiceType = ? ");
            listParameter1.add(Long.valueOf(imSearchBean.getOtherParamValue().trim()));
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.serialCode) like ? ");
            listParameter1.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.blockName) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.serialCode, 'nls_sort=Vietnamese') asc ");

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
     * date     : 21/10/2010
     * purpose  : so luong cac loai hoa don
     *
     */
    public Long getListBookTypeSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select count(*) ");
        strQuery1.append("from BookType a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.invoiceType = ? ");
            listParameter1.add(Long.valueOf(imSearchBean.getOtherParamValue().trim()));
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.serialCode) like ? ");
            listParameter1.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.blockName) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 21/10/2010
     * purpose  : lay ten cua loai hoa don
     *
     */
    public List<ImSearchBean> getBookTypeName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }

        //lay ten cua shop dua tren code
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.serialCode, a.blockName) ");
        strQuery1.append("from BookType a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and lower(a.serialCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.serialCode, 'nls_sort=Vietnamese') asc ");

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
     * purpose  : lay danh sach cac kho, mac dinh lay tat ca cac kho tu cap hien tai do xuong
     *
     */
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");
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

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

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
     * purpose  : so luong cac kho, mac dinh lay tat ca cac kho tu cap hien tai do xuong
     *
     */
    public Long getListShopSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

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

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho, mac dinh lay tat ca cac kho tu cap hien tai do xuong
     *
     */
    public List<ImSearchBean> getShopName(ImSearchBean imSearchBean) {
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
        strQuery1.append("and status = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        strQuery1.append("and lower(a.shopCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

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
     * Purpose: Trong giao dien Lap lenh xuat kho cho cap duoi
     *          Chi lay danh sach don vi cap duoi
     */
    public List<ImSearchBean> getListShopExportStock(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");
        strQuery1.append("and a.parentShopId = ? ");
//        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
//        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

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

    public Long getListShopExportStockSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        strQuery1.append("and a.parentShopId = ? ");
//        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
//        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
    }

    public List<ImSearchBean> getShopExportStockName(ImSearchBean imSearchBean) {
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
        strQuery1.append("and status = 1 ");

        strQuery1.append("and a.parentShopId = ? ");
//        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
//        listParameter1.add("%_" + userToken.getShopId() + "_%");

        listParameter1.add(userToken.getShopId());

        strQuery1.append("and lower(a.shopCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

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
     * Purpose: Trong giao dien Lap lenh xuat kho cho cap duoi
     *          Chi lay danh sach don vi cap duoi
     */
    public List<ImSearchBean> getListSpecialShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");
        strQuery1.append("and channelTypeId = ? ");
        listParameter1.add(Constant.SPECIAL_SHOP_ID);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

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

    public Long getListSpecialShopSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        strQuery1.append("and channelTypeId = ? ");
        listParameter1.add(Constant.SPECIAL_SHOP_ID);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay ten kho dac biet
     *
     */
    public List<ImSearchBean> getSpecialShopName(ImSearchBean imSearchBean) {
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
        strQuery1.append("and status = 1 ");

        strQuery1.append("and channelTypeId = ? ");
        listParameter1.add(Constant.SPECIAL_SHOP_ID);

        strQuery1.append("and lower(a.shopCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

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

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

//        strQuery1.append("and a.channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);

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
        listParameter1.add(MAX_SEARCH_RESULT);

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

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return null;
        }

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
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }

    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac nhan vien thuoc kho
     *
     */
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

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

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
        listParameter1.add(MAX_SEARCH_RESULT);

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

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach CTV/ DB
     *
     */
    public List<ImSearchBean> getListCollaboratorAndPointOfSale(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String[] arrOtherParamValue = imSearchBean.getOtherParamValue().trim().split(";");
            if (arrOtherParamValue != null && arrOtherParamValue.length == 2
                    && arrOtherParamValue[0] != null && !arrOtherParamValue[0].trim().equals("")
                    && arrOtherParamValue[1] != null && !arrOtherParamValue[1].trim().equals("")) {
                strQuery1.append("and a.shopId = (select shopId from Shop where lower(shopCode) = ? ) ");
                listParameter1.add(arrOtherParamValue[0].trim().toLowerCase());
                strQuery1.append("and a.staffOwnerId in (select staffId from Staff where lower(staffCode) = ? ) ");
                listParameter1.add(arrOtherParamValue[1].trim().toLowerCase());

            } else {
                return listImSearchBean;
            }

        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

        strQuery1.append("and (a.channelTypeId = ? or a.channelTypeId = ?) ");
        listParameter1.add(Constant.CHANNEL_TYPE_CTV);
        listParameter1.add(Constant.CHANNEL_TYPE_DB);
        strQuery1.append("and a.channelTypeId = ? ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

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

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay so luong cac CTV/ DB
     *
     */
    public Long getListCollaboratorAndPointOfSaleSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String[] arrOtherParamValue = imSearchBean.getOtherParamValue().trim().split(";");
            if (arrOtherParamValue != null && arrOtherParamValue.length == 2
                    && arrOtherParamValue[0] != null && !arrOtherParamValue[0].trim().equals("")
                    && arrOtherParamValue[1] != null && !arrOtherParamValue[1].trim().equals("")) {
                strQuery1.append("and a.shopId = (select shopId from Shop where lower(shopCode) = ? ) ");
                listParameter1.add(arrOtherParamValue[0].trim().toLowerCase());
                strQuery1.append("and a.staffOwnerId in (select staffId from Staff where lower(staffCode) = ? ) ");
                listParameter1.add(arrOtherParamValue[1].trim().toLowerCase());

            } else {
                return null;
            }

        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return null;
        }

        strQuery1.append("and (a.channelTypeId = ? or a.channelTypeId = ?) ");
        listParameter1.add(Constant.CHANNEL_TYPE_CTV);
        listParameter1.add(Constant.CHANNEL_TYPE_DB);
        strQuery1.append("and a.channelTypeId = ? ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }

    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac nhan vien thuoc kho
     *
     */
    public List<ImSearchBean> getCollaboratorAndPointOfSaleName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String[] arrOtherParamValue = imSearchBean.getOtherParamValue().trim().split(";");
            if (arrOtherParamValue != null && arrOtherParamValue.length == 2
                    && arrOtherParamValue[0] != null && !arrOtherParamValue[0].trim().equals("")
                    && arrOtherParamValue[1] != null && !arrOtherParamValue[1].trim().equals("")) {
                strQuery1.append("and a.shopId = (select shopId from Shop where lower(shopCode) = ? ) ");
                listParameter1.add(arrOtherParamValue[0].trim().toLowerCase());
                strQuery1.append("and a.staffOwnerId in (select staffId from Staff where lower(staffCode) = ? ) ");
                listParameter1.add(arrOtherParamValue[1].trim().toLowerCase());

            } else {
                return listImSearchBean;
            }

        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

        strQuery1.append("and (a.channelTypeId = ? or a.channelTypeId = ?) ");
        listParameter1.add(Constant.CHANNEL_TYPE_CTV);
        listParameter1.add(Constant.CHANNEL_TYPE_DB);
        strQuery1.append("and a.channelTypeId = ? ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

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

    /**
     *
     * author   : tuannv1
     * date     : 07/04/2010: tÃ¡ch thÃ nh hÃ m riÃªng
     * purpose  : lay danh sach cac cong tac vien/dai ly/diem ban
     *
     */
    public List<ImSearchBean> getListCTVDB(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }


        //tuannv hieu chinh ngay 02/04/2010: cho phep tim kiem ca kho CTV/DB/DL
        // tutm1 18/11/2011
        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_NOT_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by a.pointOfSale desc, nlssort( a.staffCode, 'nls_sort=Vietnamese') asc ");

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
     * author   : tuannv1
     * date     : 07/04/2010: tÃ¡ch thÃ nh hÃ m riÃªng
     * purpose  : lay danh sach cac cong tac vien/dai ly/diem ban
     *
     */
    public Long getListCTVDBSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return null;
        }


        // tutm1 18/11/2011
        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_NOT_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }

    }

    /**
     *
     * author   : tuannv1
     * date     : 07/04/2010: tÃ¡ch thÃ nh hÃ m riÃªng
     * purpose  : lay danh sach cac cong tac vien/dai ly/diem ban
     *
     */
    public List<ImSearchBean> getCTVDBName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }


        // tutm1 18/11/2011
        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_NOT_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by a.pointOfSale desc, nlssort( a.staffCode, 'nls_sort=Vietnamese') asc ");

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
     * date     : 21/01/2009
     * purpose  : lay danh sach mat hang + dich vu ban hang
     *
     */
    public List<ImSearchBean> getListGoods(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        Long stockTypeId = 0L;
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            stockTypeId = Long.valueOf(imSearchBean.getOtherParamValue().trim());
        }

        //lay danh sach cua mat hang + dich vu ban hang
        //1> lay danh sach mat hang
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery1.append("and stockTypeId = ? ");
            listParameter1.add(stockTypeId);
        }

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT / 2);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        //2> lay danh sach dich vu ban hang
        List listParameter2 = new ArrayList();
        StringBuffer strQuery2 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
        strQuery2.append("from SaleServices a ");
        strQuery2.append("where 1 = 1 ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery2.append("and lower(a.code) like ? ");
            listParameter2.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery2.append("and lower(a.name) like ? ");
            listParameter2.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery2.append("and status = ? ");
        listParameter2.add(Constant.STATUS_ACTIVE);

        strQuery2.append("and rownum <= ? ");
        listParameter2.add(MAX_SEARCH_RESULT / 2);

        strQuery2.append("order by nlssort(a.code, 'nls_sort=Vietnamese') asc ");

        Query query2 = getSession().createQuery(strQuery2.toString());
        for (int i = 0; i < listParameter2.size(); i++) {
            query2.setParameter(i, listParameter2.get(i));
        }

        List<ImSearchBean> tmpList2 = query2.list();
        if (tmpList2 != null && tmpList2.size() > 0) {
            listImSearchBean.addAll(tmpList2);
        }

        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 21/01/2009
     * purpose  : lay danh sach mat hang + dich vu ban hang
     *
     */
    public Long getListGoodsSize(ImSearchBean imSearchBean) {
        Long stockTypeId = 0L;
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            stockTypeId = Long.valueOf(imSearchBean.getOtherParamValue().trim());
        }

        Long result = 0L;
        //lay danh sach cua mat hang + dich vu ban hang
        //1> lay danh sach mat hang
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery1.append("and stockTypeId = ? ");
            listParameter1.add(stockTypeId);
        }

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            if (tmpList1.get(0) != null) {
                result += tmpList1.get(0);
            }
        }

        //2> lay danh sach dich vu ban hang
        List listParameter2 = new ArrayList();
        StringBuffer strQuery2 = new StringBuffer("select count(*) ");
        strQuery2.append("from SaleServices a ");
        strQuery2.append("where 1 = 1 ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery2.append("and lower(a.code) like ? ");
            listParameter2.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery2.append("and lower(a.name) like ? ");
            listParameter2.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery2.append("and status = ? ");
        listParameter2.add(Constant.STATUS_ACTIVE);

        Query query2 = getSession().createQuery(strQuery2.toString());
        for (int i = 0; i < listParameter2.size(); i++) {
            query2.setParameter(i, listParameter2.get(i));
        }

        List<Long> tmpList2 = query2.list();
        if (tmpList2 != null && tmpList2.size() > 0) {
            if (tmpList2.get(0) != null) {
                result += tmpList2.get(0);
            }
        }

        return result;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay ten mat hang hoac dich vu ban hang
     *
     */
    public List<ImSearchBean> getGoodsName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }

        //lay ten cua mat hang dua tren code
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and lower(a.stockModelCode) like ? ");
        listParameter1.add("%" + imSearchBean.getCode().trim().toLowerCase()+ "%");

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT / 2);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
            return listImSearchBean;

        } else {
            List listParameter2 = new ArrayList();
            StringBuffer strQuery2 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
            strQuery2.append("from SaleServices a ");
            strQuery2.append("where 1 = 1 ");

            strQuery2.append("and lower(a.code) like ? ");
            listParameter2.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");

            strQuery2.append("and rownum <= ? ");
            listParameter2.add(MAX_SEARCH_RESULT / 2);

            strQuery2.append("order by nlssort(a.code, 'nls_sort=Vietnamese') asc ");

            Query query2 = getSession().createQuery(strQuery2.toString());
            for (int i = 0; i < listParameter2.size(); i++) {
                query2.setParameter(i, listParameter2.get(i));
            }

            List<ImSearchBean> tmpList2 = query2.list();
            if (tmpList2 != null && tmpList2.size() > 0) {
                listImSearchBean.addAll(tmpList2);
            }
        }

        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 22/04/2010
     * purpose  : lay danh sach mat hang
     *
     */
    public List<ImSearchBean> getListStockModel(ImSearchBean imSearchBean) {
        Long stockTypeId = 0L;
        String staffExportType = "";//nv xuat tra cua hang hoac nv xuat dat coc cho ctv
        Long ownerId = null;
        Long ownerType = null;
        String objectCode = null;
        String objectType = null;

        //Phan biet neu la NV xuat tra hang cho cua hang
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("") && imSearchBean.getOtherParamValue().trim().equals("staff_export_shop")) {
            staffExportType = "staff_export_shop";
        } else if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("") && imSearchBean.getOtherParamValue().trim().equals("staff_export_channel")) {
            staffExportType = "staff_export_channel";
        } else if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("") && imSearchBean.getOtherParamValue().trim().contains("staff_import_channel")) {
            staffExportType = "staff_import_channel";
            String[] array = imSearchBean.getOtherParamValue().trim().split(";");//cach nhau bo dau spacebar

            if (array.length == 3) {
                try {
                    objectCode = array[1];
                    objectType = array[2];
                    if (objectCode != null && !objectCode.trim().equals("")) {
                        if (objectType != null && objectType.trim().equals(Constant.OBJECT_TYPE_STAFF)) {
                            StaffDAO staffDAO = new StaffDAO();
                            staffDAO.setSession(this.getSession());
                            Staff staff = staffDAO.findAvailableByStaffCode(objectCode.trim());
                            if (staff != null) {
                                ownerId = staff.getStaffId();
                                ownerType = Constant.OWNER_TYPE_STAFF;
                            }
                        } else if (objectType != null && objectType.trim().equals(Constant.OBJECT_TYPE_SHOP)) {
                            ShopDAO shopDAO = new ShopDAO();
                            shopDAO.setSession(this.getSession());
                            Shop shop = shopDAO.findShopsAvailableByShopCode(objectCode.trim());
                            if (shop != null) {
                                ownerId = shop.getShopId();
                                ownerType = Constant.OWNER_TYPE_SHOP;
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } else if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("") && imSearchBean.getOtherParamValue().trim().contains("channel_sale")) {
            /* truong hop tru kho trong kho cua kpp : khi ban thay hoac ban phat */
            staffExportType = "channel_sale";
            String[] array = imSearchBean.getOtherParamValue().trim().split(";");//cach nhau bo dau ;

            if (array.length == 3) {
                try {
                    objectCode = array[1];
                    ChannelType channelType = null;
                    if (array[2] != null && !array[2].trim().equals("")) {
                        Long channelTypeId = Long.valueOf(array[2].trim());
                        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                        channelType = channelTypeDAO.findById(channelTypeId);
                    }

                    if (objectCode != null && !objectCode.trim().equals("")) {
                        if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
                            StaffDAO staffDAO = new StaffDAO();
                            staffDAO.setSession(this.getSession());
                            Staff staff = staffDAO.findAvailableByStaffCode(objectCode.trim());
                            if (staff != null) {
                                ownerId = staff.getStaffId();
                                ownerType = Constant.OWNER_TYPE_STAFF;
                            }
                        } else if (objectType != null && objectType.trim().equals(Constant.OBJECT_TYPE_SHOP)) {
                            ShopDAO shopDAO = new ShopDAO();
                            shopDAO.setSession(this.getSession());
                            Shop shop = shopDAO.findShopsAvailableByShopCode(objectCode.trim());
                            if (shop != null) {
                                ownerId = shop.getShopId();
                                ownerType = Constant.OWNER_TYPE_SHOP;
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            stockTypeId = Long.valueOf(imSearchBean.getOtherParamValue().trim());
        }

        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");


        //Tam thoi khong gioi han hang hoa co trong kho - doi voi thu hoi tu ctv
        if (stockTypeId == null || stockTypeId.equals(0L)) {
            strQuery1.append("and exists (select 'x' from StockTotal b where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 and b.id.ownerId = ? and b.id.ownerType = ? ) ");

            if (staffExportType == null || staffExportType.equals("")) {
                listParameter1.add(userToken.getShopId());
                listParameter1.add(Constant.OWNER_TYPE_SHOP);
            }else if (staffExportType.equals("channel_sale")) {//Neu la tru kho trong kho cua kpp khi ban thay hoac ban phat
                listParameter1.add(ownerId);
                listParameter1.add(ownerType);
            } else if (staffExportType.equals("staff_import_channel")) {//Neu la thu hoi hang tu CTV ==> chi lay trong kho CTV
                listParameter1.add(ownerId);
                listParameter1.add(ownerType);
            } else if (!staffExportType.equals("staff_import_channel")) {//Tam thoi neu ko phai la thu hoi tu ctv se ko gioi han hang co trong kho
                listParameter1.add(userToken.getUserID());
                listParameter1.add(Constant.OWNER_TYPE_STAFF);
            } 

            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery1.append("and lower(a.stockModelCode) like ? ");
                listParameter1.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
            }
            if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                strQuery1.append("and lower(a.name) like ? ");
                listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
            }
        } else {
            strQuery1.append("and a.stockTypeId = ? ");
            listParameter1.add(stockTypeId);
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> listImSearchBean = query1.list();

        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 21/01/2009
     * purpose  : lay danh sach mat hang
     *
     */
    public Long getListStockModelSize(ImSearchBean imSearchBean) {

        if (true) {
            return 0L; //truong hop danh sach mat hang it : khong can hien thi tong so luong mat hang thuc te trong kho
        }
        Long stockTypeId = 0L;

        String staffExportType = "";//nv xuat tra cua hang hoac nv xuat dat coc cho ctv

        //Phan biet neu la NV xuat tra hang cho cua hang
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("") && imSearchBean.getOtherParamValue().trim().equals("staff_export_shop")) {
            staffExportType = "staff_export_shop";
        } else if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("") && imSearchBean.getOtherParamValue().trim().equals("staff_export_channel")) {
            staffExportType = "staff_export_channel";
        } else if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            stockTypeId = Long.valueOf(imSearchBean.getOtherParamValue().trim());
        }

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);



        Long result = 0L;
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and exists (select 'x' from StockTotal b where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 and b.id.ownerId = ? and b.id.ownerType = ? ) ");

        if (staffExportType == null || staffExportType.equals("")) {
            listParameter1.add(userToken.getShopId());
            listParameter1.add(Constant.OWNER_TYPE_SHOP);
        } else {
            listParameter1.add(userToken.getUserID());
            listParameter1.add(Constant.OWNER_TYPE_STAFF);
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery1.append("and a.stockTypeId = ? ");
            listParameter1.add(stockTypeId);
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            if (tmpList1.get(0) != null) {
                result = tmpList1.get(0);
            }
        }

        return result;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay ten mat hang
     *
     */
    public List<ImSearchBean> getStockModelName(ImSearchBean imSearchBean) {
        
        Long stockTypeId = 0L;
        String staffExportType = "";//nv xuat tra cua hang hoac nv xuat dat coc cho ctv
        Long ownerId = null;
        Long ownerType = null;
        String objectCode = null;
        String objectType = null;

        //Phan biet neu la NV xuat tra hang cho cua hang
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("") && imSearchBean.getOtherParamValue().trim().equals("staff_export_shop")) {
            staffExportType = "staff_export_shop";
        } else if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("") && imSearchBean.getOtherParamValue().trim().equals("staff_export_channel")) {
            staffExportType = "staff_export_channel";
        } else if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("") && imSearchBean.getOtherParamValue().trim().contains("staff_import_channel")) {
            staffExportType = "staff_import_channel";
            String[] array = imSearchBean.getOtherParamValue().trim().split(";");//cach nhau bo dau spacebar

            if (array.length == 3) {
                try {
                    objectCode = array[1];
                    objectType = array[2];
                    if (objectCode != null && !objectCode.trim().equals("")) {
                        if (objectType != null && objectType.trim().equals(Constant.OBJECT_TYPE_STAFF)) {
                            StaffDAO staffDAO = new StaffDAO();
                            staffDAO.setSession(this.getSession());
                            Staff staff = staffDAO.findAvailableByStaffCode(objectCode.trim());
                            if (staff != null) {
                                ownerId = staff.getStaffId();
                                ownerType = Constant.OWNER_TYPE_STAFF;
                            }
                        } else if (objectType != null && objectType.trim().equals(Constant.OBJECT_TYPE_SHOP)) {
                            ShopDAO shopDAO = new ShopDAO();
                            shopDAO.setSession(this.getSession());
                            Shop shop = shopDAO.findShopsAvailableByShopCode(objectCode.trim());
                            if (shop != null) {
                                ownerId = shop.getShopId();
                                ownerType = Constant.OWNER_TYPE_SHOP;
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } else if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("") && imSearchBean.getOtherParamValue().trim().contains("channel_sale")) {
            /* truong hop tru kho trong kho cua kpp : khi ban thay hoac ban phat */
            staffExportType = "channel_sale";
            String[] array = imSearchBean.getOtherParamValue().trim().split(";");//cach nhau bo dau ;

            if (array.length == 3) {
                try {
                    objectCode = array[1];
                    ChannelType channelType = null;
                    if (array[2] != null && !array[2].trim().equals("")) {
                        Long channelTypeId = Long.valueOf(array[2].trim());
                        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                        channelType = channelTypeDAO.findById(channelTypeId);
                    }

                    if (objectCode != null && !objectCode.trim().equals("")) {
                        if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
                            StaffDAO staffDAO = new StaffDAO();
                            staffDAO.setSession(this.getSession());
                            Staff staff = staffDAO.findAvailableByStaffCode(objectCode.trim());
                            if (staff != null) {
                                ownerId = staff.getStaffId();
                                ownerType = Constant.OWNER_TYPE_STAFF;
                            }
                        } else if (objectType != null && objectType.trim().equals(Constant.OBJECT_TYPE_SHOP)) {
                            ShopDAO shopDAO = new ShopDAO();
                            shopDAO.setSession(this.getSession());
                            Shop shop = shopDAO.findShopsAvailableByShopCode(objectCode.trim());
                            if (shop != null) {
                                ownerId = shop.getShopId();
                                ownerType = Constant.OWNER_TYPE_SHOP;
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            stockTypeId = Long.valueOf(imSearchBean.getOtherParamValue().trim());
        }

        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");


        //Tam thoi khong gioi han hang hoa co trong kho - doi voi thu hoi tu ctv
        if (stockTypeId == null || stockTypeId.equals(0L)) {
            strQuery1.append("and exists (select 'x' from StockTotal b where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 and b.id.ownerId = ? and b.id.ownerType = ? ) ");

            if (staffExportType == null || staffExportType.equals("")) {
                listParameter1.add(userToken.getShopId());
                listParameter1.add(Constant.OWNER_TYPE_SHOP);
            } else if (!staffExportType.equals("staff_import_channel")) {//Tam thoi neu ko phai la thu hoi tu ctv se ko gioi han hang co trong kho
                listParameter1.add(userToken.getUserID());
                listParameter1.add(Constant.OWNER_TYPE_STAFF);
            } else if (staffExportType.equals("staff_import_channel")) {//Neu la thu hoi hang tu CTV ==> chi lay trong kho CTV
                listParameter1.add(ownerId);
                listParameter1.add(ownerType);
            } else if (staffExportType.equals("channel_sale")) {//Neu la tru kho trong kho cua kpp khi ban thay hoac ban phat
                listParameter1.add(ownerId);
                listParameter1.add(ownerType);
            }

            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery1.append("and lower(a.stockModelCode) = ? ");
                listParameter1.add(imSearchBean.getCode().trim().toLowerCase() );
            }
            if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                strQuery1.append("and lower(a.name) = ? ");
                listParameter1.add(imSearchBean.getName().trim().toLowerCase());
            }
        } else {
            strQuery1.append("and a.stockTypeId = ? ");
            listParameter1.add(stockTypeId);
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> listImSearchBean = query1.list();

        return listImSearchBean;
        
        
/*
        Long stockTypeId = 0L;
        String staffExportType = "";//nv xuat tra cua hang hoac nv xuat dat coc cho ctv

        //Phan biet neu la NV xuat tra hang cho cua hang
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("") && imSearchBean.getOtherParamValue().trim().equals("staff_export_shop")) {
            staffExportType = "staff_export_shop";
        } else if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("") && imSearchBean.getOtherParamValue().trim().equals("staff_export_channel")) {
            staffExportType = "staff_export_channel";
        } else if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            stockTypeId = Long.valueOf(imSearchBean.getOtherParamValue().trim());
        }

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }

        //lay ten cua mat hang dua tren code
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and exists (select 'x' from StockTotal b where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 and b.id.ownerId = ? and b.id.ownerType = ? ) ");

        if (staffExportType == null || staffExportType.equals("")) {
            listParameter1.add(userToken.getShopId());
            listParameter1.add(Constant.OWNER_TYPE_SHOP);
        } else {
            listParameter1.add(userToken.getUserID());
            listParameter1.add(Constant.OWNER_TYPE_STAFF);
        }

        strQuery1.append("and lower(a.stockModelCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery1.append("and a.stockTypeId = ? ");
            listParameter1.add(stockTypeId);
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        listImSearchBean = query1.list();

        return listImSearchBean;*/
    }

    /**
     *
     * author   : tamdt1
     * date     : 22/04/2010
     * purpose  : lay danh sach mat hang so (MB, HP hoac PSTN)
     *
     */
    public List<ImSearchBean> getListIsdnStockModel(ImSearchBean imSearchBean) {
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and (stockTypeId = ? or stockTypeId = ? or stockTypeId = ?) ");
        listParameter1.add(Constant.STOCK_ISDN_MOBILE);
        listParameter1.add(Constant.STOCK_ISDN_HOMEPHONE);
        listParameter1.add(Constant.STOCK_ISDN_PSTN);

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> listImSearchBean = query1.list();

        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 21/01/2009
     * purpose  : lay danh sach mat hang so (MB, HP hoac PSTN)
     *
     */
    public Long getListIsdnStockModelSize(ImSearchBean imSearchBean) {
        Long result = 0L;
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and (stockTypeId = ? or stockTypeId = ? or stockTypeId = ?) ");
        listParameter1.add(Constant.STOCK_ISDN_MOBILE);
        listParameter1.add(Constant.STOCK_ISDN_HOMEPHONE);
        listParameter1.add(Constant.STOCK_ISDN_PSTN);

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            if (tmpList1.get(0) != null) {
                result = tmpList1.get(0);
            }
        }

        return result;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay ten mat hang so (MB, HP hoac PSTN)
     *
     */
    public List<ImSearchBean> getIsdnStockModelName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }

        //lay ten cua mat hang dua tren code
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and lower(a.stockModelCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and (stockTypeId = ? or stockTypeId = ? or stockTypeId = ?) ");
        listParameter1.add(Constant.STOCK_ISDN_MOBILE);
        listParameter1.add(Constant.STOCK_ISDN_HOMEPHONE);
        listParameter1.add(Constant.STOCK_ISDN_PSTN);

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        listImSearchBean = query1.list();

        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho, mac dinh lay tat ca cac kho tu cap hien tai do xuong
     *
     */
    public List<ImSearchBean> getShopNameByChannelType(ImSearchBean imSearchBean) {
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

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.channelTypeId = ? ");
            listParameter1.add(Long.valueOf(imSearchBean.getOtherParamValue().trim().toLowerCase()));
        }

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
    public List<ImSearchBean> getStaffNameChannelType(ImSearchBean imSearchBean) {
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

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.channelTypeId = ? ");
            listParameter1.add(Long.valueOf(imSearchBean.getOtherParamValue().trim().toLowerCase()));
        }

        strQuery1.append("and a.shopId = ? ");
        listParameter1.add(userToken.getShopId());

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

    /**
     *
     * author tamdt1
     * date: 19/03/2009
     * muc dich: luu doi tuong vao CSDL
     * dau vao:
     *              - kieu cua doi tuong (vi du com.viettel.im.database.BO.StockHandset)
     *              - danh sach cac ham thiet lap gia tri thuoc tinh can update
     *              - danh sach cac gia tri can thiet lap cho cac thuoc tinh
     * dau ra:
     *              - true neu moi viec thuc hien thanh cong
     *              - false neu xay ra loi
     */
    public static boolean saveObjectToDb(String updatedClassName,
            List<String> listSetFieldMethodName,
            List<Object> listFieldValue,
            Session session) {
        try {
            //lay doi tuong tro den lop can update
            Class updatedClass = Class.forName(updatedClassName);

            //lay cac phuong thuc get cua cac truong
            List<Method> listSetFieldMethod = new ArrayList<Method>();
            for (int i = 0; i < listSetFieldMethodName.size(); i++) {
                Class partypes[] = new Class[1];
                partypes[0] = listFieldValue.get(i).getClass();
                Method setFieldMethod = updatedClass.getMethod(listSetFieldMethodName.get(i), partypes);
                listSetFieldMethod.add(setFieldMethod);
            }

            //tao the hien
            Constructor updatedClassConstructor = updatedClass.getConstructor(new Class[0]);
            Object updatedClassInstance = updatedClassConstructor.newInstance(new Object[0]);

            //goi cac ham set field de thiet lap thuoc tinh cho doi tuong
            for (int i = 0; i < listSetFieldMethod.size(); i++) {
                Object arglist[] = new Object[1];
                arglist[0] = listFieldValue.get(i);
                Object retobj = listSetFieldMethod.get(i).invoke(updatedClassInstance, arglist);
            }

            //luu vao CSDL
            session.save(updatedClassInstance);

        } catch (Throwable e) {
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 19/03/2009
     * muc dich: Set cac value vao object
     * dau vao:
     *              - Object can set value
     *              - HashMap chua ham set cua doi tuong va gia tri can set vao
     * dau ra:
     *              - Doi tuong sau khi set cac gia tri tuong ung
     */
    public Object setFieldsForObject(Object updatedObject,
            HashMap<String, Object> hashMap) {
        try {
            //lay doi tuong tro den lop can update
            Class updatedClass = updatedObject.getClass();


            Set set = hashMap.entrySet();
            Iterator i = set.iterator();
            while (i.hasNext()) {
                Map.Entry me = (Map.Entry) i.next();
                Class partypes[] = new Class[1];
                partypes[0] = me.getValue().getClass();
                Method setFieldMethod = updatedClass.getMethod((String) me.getKey(), partypes);

                Object arglist[] = new Object[1];
                arglist[0] = me.getValue();
                Object retobj = setFieldMethod.invoke(updatedObject, arglist);
            }


        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }

        return updatedObject;
    }

    /**
     *
     * author ThanhNC
     * date: 04/05/2009
     * muc dich: get cac value tu object
     * dau vao:
     *              - Object can get value
     *              - Ten phuong thuc get
     * dau ra:
     *              - Gia tri can lay cua ham get
     */
    public static Object getObjectField(Object updatedObject,
            String getMethodName) {
        try {
            //lay doi tuong tro den lop can update
            Class updatedClass = updatedObject.getClass();
            Class partypes[] = new Class[0];

            Method getFieldMethod = updatedClass.getMethod(getMethodName, partypes);
            Object arglist[] = new Object[0];
            Object retobj = getFieldMethod.invoke(updatedObject, arglist);
            return retobj;


        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * tuannv,23-05-2009
     * @return danh sach cac doi tuong tinh hoa hong
     */
    public List getChanelType() throws Exception {
        try {
//            String queryString = "from ChannelType where status=1 and checkComm = ? and isVtUnit=?";
            String queryString = "from ChannelType where status=1 and checkComm = ?";
            Query queryObject = getSession().createQuery(queryString);

            queryObject.setParameter(0, Constant.CHECK_COM);
//            queryObject.setParameter(1, Constant.IS_NOT_VT_UNIT);

            return queryObject.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     *
     * author tuannv
     * date: 05/06/2009
     * lay loai doi tuong: shop or staff dua tren channelTypeId
     *
     */
    public String getObjTypeByChannelTypeId(Long channelTypeId) {
        List<ChannelType> listShops = new ArrayList<ChannelType>();
        try {

            String strQuery = "from ChannelType where channelTypeId = ?";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, channelTypeId);
            listShops = query.list();
            return listShops.get(0).getObjectType();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";

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
    public List<CalculateFeeBean> calculateFEE(Long channelType, Long ownerId, String monthyear, Long approved, Long paystatus, String InputType) {
        try {
            String objType = getObjTypeByChannelTypeId(channelType);
            Session session = getSession();
            List parameterList = new ArrayList();
            String strQuery = "SELECT "
                    + "item.item_id AS itemid,"
                    + "item.item_name AS itemname,"
                    + "comm.comm_trans_id AS commtransid,"
                    + "comm.invoice_used_id AS invoiceid,"
                    + "comm.bill_cycle AS billcycle,"
                    + "comm.create_date AS createDate,"
                    + "comm.quantity AS quantity,"
                    + "comm.status as status,"
                    + "comm.pay_status as paystatus,"
                    + "comm.total_money AS totalmoney,"
                    + "comm.tax_money AS taxMoney," + //quantityGet * (price * (vat / 100));
                    "comm.approved as approved, "
                    + "item.input_Type as inputType "
                    + " FROM comm_transaction comm, comm_items item "
                    + "WHERE item.item_id = comm.item_id "
                    + "AND comm.channel_type_id=? "
                    + "AND comm.pay_status = ? "
                    + "AND to_char(comm.bill_cycle,'mm/yyyy')= ? "
                    + "AND item.status = ? ";

            if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {
                strQuery += "AND comm.shop_id=? "; //thuoc loai cua hang
            }
            if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                strQuery += "AND comm.staff_id=? "; //thuoc loai nhan vien
            }
            parameterList.add(channelType);
            parameterList.add(paystatus);
            parameterList.add(monthyear);
            parameterList.add(Constant.STATUS_USE);
            parameterList.add(ownerId);
            if (!InputType.equals("")) {
                strQuery += "AND item.input_type!=? ";
                parameterList.add(Constant.INPUT_TYPE_AUTO);
            }

            if (approved != 2) {//Toan bo ko phan biet phe duyet
                strQuery += "AND comm.approved = ? ";
                parameterList.add(approved);
            }


            strQuery += "order by item.item_name,comm.create_date "; //thuoc loai cua hang

            Query queryObject = session.createSQLQuery(strQuery).addScalar("itemid", Hibernate.LONG).addScalar("itemname", Hibernate.STRING).addScalar("commtransid", Hibernate.LONG).addScalar("invoiceid", Hibernate.LONG).addScalar("billcycle", Hibernate.DATE).addScalar("createDate", Hibernate.DATE).addScalar("quantity", Hibernate.LONG).addScalar("totalmoney", Hibernate.LONG).addScalar("inputType", Hibernate.STRING).addScalar("status", Hibernate.STRING).addScalar("approved", Hibernate.LONG).addScalar("paystatus", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(CalculateFeeBean.class));
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
     * @author: tuannv - Calculate FEE
     * @param channelType
     * @param shopID
     * @param billCycleDate
     * @param stDate
     * @param endDate
     * @return ma doi tuong
     */
    private Map listShopID = new HashMap();
    private Map shopName = new HashMap();

    public String getShopCode() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            HttpSession session = httpServletRequest.getSession();

//            String shopType = httpServletRequest.getParameter("updFeesForm.shopType");

//            if ((shopType != null) && (!shopType.equals(""))) {
            String autoName = httpServletRequest.getParameter("autoName");
            String shopCode = httpServletRequest.getParameter(autoName);
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

                //Tim shop chua doi tuong
                Long shopId = getShopIDByStaffID(userID);
                //Tim danh sach doi tuong cua shop
                List parameterList = new ArrayList();
                String queryString = "from Shop ";
                queryString += " where parentShopId = ? ";
//                queryString += " and channelTypeId = ? ";
                queryString += " and lower(shopCode) Like ? ";

                parameterList.add(shopId);
//                parameterList.add(Long.parseLong(shopType));
                parameterList.add("%" + shopCode.trim().toLowerCase() + "%");


                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                if (!queryObject.list().isEmpty()) {
                    listShop = queryObject.list();

                    for (int i = 0; i < listShop.size(); i++) {
                        getListShopID().put(listShop.get(i).getShopId(), listShop.get(i).getShopCode());
                    }
                    return "success";
                }
            }

//            }
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
        try {
            String queryString = "from Staff where staffId = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, staffID);
            if (queryObject.list() != null && queryObject.list().size() > 0) {
                Staff staff = (Staff) queryObject.list().get(0);
                return staff.getShopId();
            }
            return null;
        } catch (RuntimeException re) {
            throw re;
        }
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

    /*
     * Author:ThanhNC
     * Date created: 09/06/2009
     * Purpose: Doc file excel return  1 list
     */
    public List readExcelFile(File file,
            String sheetName,
            int beginRow,
            int fromCol,
            int toCol) throws Exception {
        if (file == null) {
            return null;
        }

        try {
            Workbook workbook = Workbook.getWorkbook(file);
            //Sheet sheet = workbook.getSheet(sheetName);
            Sheet sheet = null;
            if (sheet == null) {
                sheet = workbook.getSheet(0);
            }

            List lst = new ArrayList();
            //boolean isBreak=false;
            for (int i = beginRow; i < sheet.getRows(); i++) {
                Object[] obj = new Object[toCol - fromCol + 1];
                int icount = 0;
                int icountNull = 0;
                for (int j = fromCol; j <= toCol; j++) {

                    //if (checkColumnNotBlank(sheet.getColumn(j))) {
                    try {
                        obj[icount] = sheet.getCell(j, i).getContents();
                    } catch (Exception ex) {
                        obj[icount] = "";
                        continue;
                    }
                    if (obj[icount] == null || obj[icount].equals("")) {
                        icountNull += 1;
                    }
                    icount += 1;
                }
                //   }
                if (icount == icountNull) {
                    break;
                }
                lst.add(obj);
            }

            return lst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List readCompleteExcelFile(File file,
            String sheetName,
            int beginRow,
            int fromCol,
            int toCol) throws Exception {
        if (file == null) {
            return null;
        }

        try {
            Workbook workbook = Workbook.getWorkbook(file);
            //Sheet sheet = workbook.getSheet(sheetName);
            Sheet sheet = null;
            if (sheet == null) {
                sheet = workbook.getSheet(0);
            }

            List lst = new ArrayList();
            //boolean isBreak=false;
            for (int i = beginRow; i < sheet.getRows(); i++) {
                Object[] obj = new Object[toCol - fromCol + 1];
                int icount = 0;
                int icountNull = 0;
                for (int j = fromCol; j <= toCol; j++) {

                    //if (checkColumnNotBlank(sheet.getColumn(j))) {
                    try {
                        obj[icount] = sheet.getCell(j, i).getContents();
                    } catch (Exception ex) {
                        obj[icount] = "";
                        continue;
                    }
                    if (obj[icount] == null || obj[icount].equals("")) {
                        icountNull += 1;
                    }
                    icount += 1;
                }
                //   }
                if (icount == icountNull) {
                    break;
                }
                lst.add(obj);
            }

            return lst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static boolean checkColumnNotBlank(Cell[] cell) {
        for (int i = 0; i < cell.length; i++) {
            if (cell[i].getContents().trim().length() > 0) {
                return true;
            }
        }
        return false;
    }

    /*
     * Author:DucDD
     * Date created: 25/06/2009
     * Purpose: chuyá»ƒn tiá»�n tá»« dáº¡ng sá»‘ thÃ nh dáº¡ng chá»¯
     */
    public String speakMoney(Long money) {
        String moneyString = expressMoney(money);
        String mot = "má»™t";
        String hai = "hai";
        String ba = "ba";
        String bon = "bá»‘n";
        String nam = "nÄƒm";
        String sau = "sÃ¡u";
        String bay = "báº©y";
        String tam = "tÃ¡m";
        String chin = "chÃ­n";

        moneyString = moneyString.replaceAll("1", mot);
        moneyString = moneyString.replaceAll("2", hai);
        moneyString = moneyString.replaceAll("3", ba);
        moneyString = moneyString.replaceAll("4", bon);
        moneyString = moneyString.replaceAll("5", nam);
        moneyString = moneyString.replaceAll("6", sau);
        moneyString = moneyString.replaceAll("7", bay);
        moneyString = moneyString.replaceAll("8", tam);
        moneyString = moneyString.replaceAll("9", chin);
        moneyString += " Ä‘á»“ng ";

        moneyString = toUpperCharacters(moneyString, 0, 1);
        moneyString = moneyString.replaceAll("  ", " ");

        return moneyString;
    }

    /*
     * Author:DucDD
     * Date created: 25/06/2009
     * Purpose: há»— trá»£ cho hÃ m readMoney, thÃªm ty, trieu, nghin vao gia tri tien
     */
    private String expressMoney(Long money) {
        String moneyString = "";
        Long billion = 1000000000L;
        Long million = 1000000L;
        Long thousand = 1000L;

        Long billionNo = money / billion;
        money = money - billionNo * billion;
        Long millionNo = money / million;
        money = money - millionNo * million;
        Long thousandNo = money / thousand;
        money = money - thousandNo * thousand;

        if (billionNo != null && billionNo != 0L) {
            moneyString += expressMoney(billionNo) + " tá»· ";
        }
        if (millionNo != null && millionNo != 0L) {
            moneyString += expressDetailMoney(millionNo) + " triá»‡u ";
        }
        if (thousandNo != null && thousandNo != 0L) {
            moneyString += expressDetailMoney(thousandNo) + " nghÃ¬n ";
        }
        if (money != 0L) {
            moneyString += expressDetailMoney(money);
        }
        return moneyString;
    }


    /*
     * Author:DucDD
     * Date created: 25/06/2009
     * Purpose: há»— trá»£ cho hÃ m expressMoney, chuyen so tien dinh dang 3 chu so
     * ve tram, muoi, linh
     */
    private String expressDetailMoney(Long money) {
        String moneyString = "";
        Long hundred = 100L;
        Long ten = 10L;
        Long hundredNo = money / hundred;
        money = money - hundredNo * hundred;
        Long tenNo = money / ten;
        money = money - tenNo * ten;
        if (hundredNo != null && hundredNo != 0L) {
            moneyString += hundredNo.toString() + " trÄƒm ";
        }
        if (tenNo != null && tenNo != 0L) {
            if (tenNo == 1L) {
                moneyString += "mÆ°á»�i ";
            } else {
                moneyString += tenNo.toString() + " mÆ°Æ¡i ";
            }
        } else if (tenNo == 0L && hundredNo != 0 && money != 0L) {
            moneyString += " linh ";
        }
        if (money != 0L) {
            moneyString += money.toString();
        }
        return moneyString;
    }

    /*
     * Author:DucDD
     * Date created: 25/06/2009
     * Purpose: viet hoa cho 1 so ki tu trong 1 String
     */
    public static String toUpperCharacters(String inputString, int startIndex, int numberOfCharacter) {
        if (startIndex < 0) {
            startIndex = 0;
        }
        int endIndex = startIndex + numberOfCharacter;
        if (endIndex > inputString.length()) {
            endIndex = inputString.length() - 1;
        }
        String returnString = "";
        String toUpperString = inputString.substring(startIndex, endIndex);
        toUpperString = toUpperString.toUpperCase();
        returnString = inputString.substring(0, startIndex) + toUpperString + inputString.substring(endIndex);
        return returnString;
    }

//==========================================================================================================================================//
    /**
     * Author: TrongLV
     * Created on: 21/08/2009
     * Purpose: Tim tat ca cac kenh ban hang thoa man dieu kien:
     *     Ton tai it nhat 1 cua hang OR nhan vien cua cua hang do
     *         OR cua hang truc thuoc OR nhan vien cua cua hang truc thuoc nam trong kenh do
     *
     * Neu shopID == null => return null
     * Neu shopId != null => Tim het ChannelType thoa man dieu kien: co Shop || Staff cua Shop thuoc ChannelType do
     * @param shopId
     * @return
     */
    public List getChannelTypeListByShopId(Long shopId, boolean isCheckedId) {
        try {
            if (shopId == null) {
                if (isCheckedId) {
                    return null;
                }
            }

            List lstParam = new ArrayList();
            String queryString = "";
            queryString += " SELECT CT.channel_type_id AS objId ";
            queryString += "    ,CT.name AS objName ";
            queryString += " FROM CHANNEL_TYPE CT ";

            if (shopId != null) {
                queryString += " WHERE 1=1 ";
                queryString += " AND ( EXISTS ( ";
                queryString += "     SELECT SP1.* ";
                queryString += "     FROM SHOP SP1 ";
                queryString += "     WHERE  SP1.channel_type_id = CT.channel_type_id ";
                queryString += "         AND SP1.status = ? ";
                lstParam.add(Constant.STATUS_USE);
                queryString += "         AND SP1.shop_path LIKE ( ";
                queryString += "             SELECT SP2.shop_path || '%' ";
                queryString += "             FROM SHOP SP2  ";
                queryString += "             WHERE SP2.shop_id = ? ";
                lstParam.add(shopId);
                queryString += "                 AND SP2.status = ? ";
                lstParam.add(Constant.STATUS_USE);
                queryString += "         ) ";
                queryString += "     )         ";
                queryString += "     OR EXISTS ( ";
                queryString += "     SELECT SF1.*  ";
                queryString += "     FROM STAFF SF1 ";
                queryString += "     WHERE  SF1.channel_type_id = CT.channel_type_id ";
                queryString += "         AND EXISTS ( ";
                queryString += "             SELECT *  ";
                queryString += "                 FROM SHOP SP3  ";
                queryString += "                 WHERE SP3.shop_id = SF1.shop_id ";
                queryString += "                     AND SP3.status = ? ";
                lstParam.add(Constant.STATUS_USE);
                queryString += "                     AND SP3.shop_path LIKE ( ";
                queryString += "                     SELECT SP4.shop_path || '%' ";
                queryString += "                     FROM SHOP SP4 ";
                queryString += "                     WHERE SP4.shop_id = ? ";
                lstParam.add(shopId);
                queryString += "                         AND SP4.status = ? ";
                lstParam.add(Constant.STATUS_USE);
                queryString += "                 ) ";
                queryString += "         ) ";
                queryString += "     ) ";
                queryString += " ) ";
                queryString += " AND CT.STATUS = ? ";
                lstParam.add(Constant.STATUS_USE);
            }
            queryString += " ORDER BY CT.channel_type_id ";

            Query queryObject = getSession().createSQLQuery(queryString).
                    addScalar("objId", Hibernate.LONG).
                    addScalar("objName", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(ComboListBean.class));

            for (int idx = 0; idx < lstParam.size(); idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }

            List list = queryObject.list();
            return list;
        } catch (RuntimeException re) {
            throw re;
        }
    }
    /*
     * Lay toan bo cac channel_type cua shop_id dang nhap
     * Vunt
     *
     */

    public List getAllChannelTypeListByShopId(Long shopId, boolean isCheckedId) {
        try {
            if (shopId == null) {
                if (isCheckedId) {
                    return null;
                }
            }

            List lstParam = new ArrayList();
            String queryString = "";
            queryString += " SELECT DISTINCT ch.channel_type_id AS objid, ch.NAME AS objname";
            queryString += " FROM shop sh, channel_type ch";
            if (shopId != null) {
                queryString += " WHERE sh.channel_type_id = ch.channel_type_id";
                queryString += " AND sh.status = 1";
                queryString += " AND sh.shop_path LIKE ? ESCAPE '$'";
                lstParam.add("%$_" + shopId.toString() + "$_%");
                queryString += " UNION";
                queryString += " SELECT ch.channel_type_id AS objid, ch.NAME AS objname";
                queryString += " FROM shop sh, channel_type ch";
                queryString += " WHERE sh.channel_type_id = ch.channel_type_id";
                queryString += " AND sh.status = 1";
                queryString += " AND sh.shop_id = ?";
                lstParam.add(shopId);
                queryString += " UNION";
                queryString += " SELECT DISTINCT ch.channel_type_id AS objid, ch.NAME AS objname";
                queryString += " FROM staff st, channel_type ch";
                queryString += " WHERE st.channel_type_id = ch.channel_type_id";
                queryString += " AND st.status = 1";
                queryString += " AND st.shop_id IN (";
                queryString += " SELECT DISTINCT sh.shop_id";
                queryString += " FROM shop sh, channel_type ch";
                queryString += " WHERE sh.channel_type_id = ch.channel_type_id";
                queryString += " AND sh.status = 1";
                queryString += " AND sh.shop_path LIKE ? ESCAPE '$')";
                lstParam.add("%$_" + shopId.toString() + "$_%");
                //queryString += " ORDER BY CT.channel_type_id";

                Query queryObject = getSession().createSQLQuery(queryString).
                        addScalar("objId", Hibernate.LONG).
                        addScalar("objName", Hibernate.STRING).
                        setResultTransformer(Transformers.aliasToBean(ComboListBean.class));

                for (int idx = 0; idx < lstParam.size(); idx++) {
                    queryObject.setParameter(idx, lstParam.get(idx));
                }

                List list = queryObject.list();
                return list;
            }
        } catch (RuntimeException re) {
            throw re;
        }
        return null;
    }

    /**
     * Author: TrongLV
     * Created on: 21/08/2009
     * Purpose: Tim tat ca cac cua hang OR nhan vien cua cua hang OR cua hang truc thuoc OR nhan vien cua cua hang truc thuoc
     *
     * Neu ChannelTypeId == null =>
     *      Neu objType == null => Tim het Shop JOIN Staff thoa man objCode
     *      Neu objType != null => Tim het Shop (OR Staff) thoa man objCode
     * Neu ChannelTypeId != null =>
     *      objType = ChannelType.objType
     *      Tim het Shop (OR Staff) thoa man objCode & channelTypeId
     *
     * @param channelTypeId
     * @param objType
     * @param objCode
     * @return
     */
    public List getShopAndStaffList(Long rootId, Long objId, String objCode, String objType, int maxRowsResult, boolean isAllChild, boolean isExact) {
        if (rootId == null && objId == null && (objCode == null || objCode.trim().equals(""))) {
            return null;
        }
        if (maxRowsResult <= 0 || maxRowsResult > Constant.maxRowsResultCombo) {
            maxRowsResult = Constant.maxRowsResultCombo;
        }
        List lstParam = new ArrayList();
        String queryString = "";
        String queryStringShop = "";
        String queryStringStaff = "";
        Query queryObject = null;
        List list = null;

        if (objType == null || "".equals(objType) || objType.equals(Constant.OBJECT_TYPE_SHOP)) {
            queryStringShop += " SELECT a.shop_id AS objId, a.shop_code AS objCode, a.NAME AS objName, 1 AS objType FROM shop a ";
            queryStringShop += " WHERE 1=1 and a.status=? and rownum<=? ";
            lstParam.add(Constant.STATUS_USE);
            lstParam.add(maxRowsResult);
            if (rootId != null) {
                if (!isAllChild) {
                    queryStringShop += " and (a.parent_shop_id = ? or shop_id =?)";
                    lstParam.add(rootId);
                    lstParam.add(rootId);
                } else {
                    queryStringShop += " and (a.shop_path LIKE ? ESCAPE '$' or a.shop_id = ?)";
                    lstParam.add("%$_" + String.valueOf(rootId) + "$_%");
                    lstParam.add(rootId);
                }
            }
            if (objId != null) {
                queryStringShop += " AND a.shop_id=? ";
                lstParam.add(objId);
            }
            if (objCode != null && !objCode.trim().equals("")) {
                if (isExact) {
                    queryStringShop += " AND lower(a.shop_code) = ? ";
                    lstParam.add(objCode.toLowerCase().trim());
                } else {
                    queryStringShop += " AND lower(a.shop_code) like ? ";
                    lstParam.add(objCode.toLowerCase().trim() + "%");
                }
            }
            queryStringShop += " ORDER BY a.shop_code ";
        }

        if (objType == null || "".equals(objType) || objType.equals(Constant.OBJECT_TYPE_STAFF)) {
            queryStringStaff += " SELECT a.staff_id AS objId, a.staff_code AS objCode, a.NAME AS objName, 2 AS objType FROM staff a, shop b ";
            queryStringStaff += " WHERE 1=1 AND a.status=? and rownum<=? and a.shop_id = b.shop_id ";
            lstParam.add(Constant.STATUS_USE);
            lstParam.add(maxRowsResult);
            if (rootId != null) {
                if (!isAllChild) {
                    queryStringStaff += " and (a.shop_id = ?)";
                    lstParam.add(rootId);
                } else {
                    queryStringStaff += " and (b.shop_path LIKE ? ESCAPE '$' or a.shop_id = ?)";
                    lstParam.add("%$_" + String.valueOf(rootId) + "$_%");
                    lstParam.add(rootId);
                }
            }
            if (objId != null) {
                queryStringStaff += " AND a.staff_id=? ";
                lstParam.add(objId);
            }
            if (objCode != null && !objCode.trim().equals("")) {
                if (isExact) {
                    queryStringStaff += " AND lower(a.staff_code) = ? ";
                    lstParam.add(objCode.toLowerCase().trim());
                } else {
                    queryStringStaff += " AND lower(a.staff_code) like ? ";
                    lstParam.add(objCode.toLowerCase().trim() + "%");
                }
            }
            queryStringStaff += " ORDER BY a.staff_code ";
        }

        if ((!"".equals(queryStringShop)) && (!"".equals(queryStringStaff))) {
            queryString = queryStringShop + " UNION " + queryStringStaff;
        } else {
            queryString = queryStringShop + " " + queryStringStaff;
        }
        queryObject =
                getSession().createSQLQuery(queryString).
                addScalar("objId", Hibernate.LONG).
                addScalar("objCode", Hibernate.STRING).
                addScalar("objName", Hibernate.STRING).
                addScalar("objType", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(ComboListBean.class));
        for (int idx = 0;
                idx < lstParam.size();
                idx++) {
            queryObject.setParameter(idx, lstParam.get(idx));
        }
        list = queryObject.list();
        return list;
    }

    public List getShopAndSfaffList_OLD(
            Long shopId, Long channelTypeId, String objType, String objCode) {
        try {
            if (shopId == null) {
                return null;
            }

            List lstParam = new ArrayList();
            String queryString = "";
            String queryStringShop = "";
            String queryStringStaff = "";
            Query queryObject = null;
            List list = null;

            if (objType == null) {
                objType = "";
            }

            if (objCode == null) {
                objCode = "";
            }

            if (channelTypeId != null) {
                queryString = " SELECT objectType "
                        + " FROM ChannelType "
                        + " WHERE channelTypeId = ? ";
                queryObject =
                        getSession().createQuery(queryString);
                queryObject.setParameter(0, channelTypeId);
                list =
                        queryObject.list();
                if (list.size() > 0) {
                    objType = list.get(0).toString();
                }

            }

            if ("".equals(objType) || objType.equals(Constant.SHOP_TYPE)) {
                queryStringShop = " SELECT SP1.shop_id AS objId ,SP1.shop_code AS objCode "
                        + " ,SP1.name AS objName, 1 AS objType "
                        + " FROM SHOP SP1 "
                        + " WHERE  1=1 AND ROWNUM<=10 AND SP1.status = ? ";
                lstParam.add(Constant.STATUS_USE);
                if (!"".equals(objCode)) {
                    queryStringShop += "     AND lower(SP1.shop_code) LIKE ? ";
                    lstParam.add("" + objCode.toLowerCase().trim() + "%");
                }
                if (channelTypeId != null) {
                    queryStringShop += "     AND SP1.channel_type_id = ? ";
                    lstParam.add(channelTypeId);
                }
                queryStringShop += "     AND SP1.shop_path LIKE (  ";
                queryStringShop +=
                        "         SELECT SP2.shop_path || '%'  ";
                queryStringShop +=
                        "         FROM SHOP SP2   ";
                queryStringShop +=
                        "         WHERE 1=1 ";
                queryStringShop +=
                        "                 AND SP2.shop_id = ? ";
                lstParam.add(shopId);
                queryStringShop +=
                        "             AND SP2.status = ? ";
                lstParam.add(Constant.STATUS_USE);
                queryStringShop +=
                        "     ) ";
            }

            if ("".equals(objType) || objType.equals(Constant.STAFF_TYPE)) {
                queryStringStaff = "";
                queryStringStaff +=
                        " SELECT SF1.staff_id AS objId  ,SF1.staff_code AS objCode "
                        + " ,SF1.name AS objName, 2 as objType "
                        + " FROM STAFF SF1 "
                        + " WHERE  1=1 AND ROWNUM<=10  AND SF1.STATUS = ? ";
                lstParam.add(Constant.STATUS_USE);
                if (!"".equals(objCode)) {
                    queryStringStaff += "     AND lower(SF1.staff_code) LIKE ? ";
                    lstParam.add("" + objCode.toLowerCase().trim() + "%");
                }

                if (channelTypeId != null) {
                    queryStringStaff += "     AND SF1.channel_type_id = ? ";
                    lstParam.add(channelTypeId);
                }

                queryStringStaff += "     AND EXISTS (  ";
                queryStringStaff +=
                        "         SELECT *   ";
                queryStringStaff +=
                        "         FROM SHOP SP3   ";
                queryStringStaff +=
                        "         WHERE SP3.shop_id = SF1.shop_id  ";
                queryStringStaff +=
                        "             AND SP3.status = ?  ";
                lstParam.add(Constant.STATUS_USE);
                queryStringStaff +=
                        "             AND SP3.shop_path LIKE (  ";
                queryStringStaff +=
                        "                 SELECT SP4.shop_path || '%'  ";
                queryStringStaff +=
                        "                 FROM SHOP SP4  ";
                queryStringStaff +=
                        "                 WHERE SP4.shop_id = ? ";
                lstParam.add(shopId);
                queryStringStaff +=
                        "                     AND SP4.status = ? ";
                lstParam.add(Constant.STATUS_USE);
                queryStringStaff +=
                        "             )  ";
                queryStringStaff +=
                        "     ) ";
            }

            if ((!"".equals(queryStringShop)) && (!"".equals(queryStringStaff))) {
                queryString = queryStringShop + " UNION " + queryStringStaff;
            } else {
                queryString = queryStringShop + " " + queryStringStaff;
            }

            queryObject = null;
            queryObject =
                    getSession().createSQLQuery(queryString).
                    addScalar("objId", Hibernate.LONG).
                    addScalar("objCode", Hibernate.STRING).
                    addScalar("objName", Hibernate.STRING).
                    addScalar("objType", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(ComboListBean.class));



            for (int idx = 0;
                    idx < lstParam.size();
                    idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }

            list = null;
            list = queryObject.list();
            return list;
        } catch (RuntimeException re) {
            throw re;
        }

    }

    /**
     * Author: TrongLV
     * Created on: 21/08/2009
     * Purpose: Tim tat ca cac cua hang OR nhan vien co ID = objId
     *
     * @param channelTypeId
     * @param objType
     * @param objId
     * @param isCheckId
     * @return
     */
    public List getShopAndSfaffList_OLD(
            Long channelTypeId, String objType, String objId, boolean isCheckedId) {
        try {
            if (objId == null) {
                if (isCheckedId) {
                    return null;
                }

            }

            List lstParam = new ArrayList();
            String queryString = "";
            String queryStringShop = "";
            String queryStringStaff = "";
            Query queryObject = null;
            List list = null;

            if (objType == null) {
                objType = "";
            }

            if (channelTypeId != null) {
                queryString = "";
                queryString +=
                        " SELECT objectType ";
                queryString +=
                        " FROM ChannelType ";
                queryString +=
                        " WHERE channelTypeId = ? ";
                queryObject =
                        getSession().createQuery(queryString);
                queryObject.setParameter(0, channelTypeId);
                list =
                        queryObject.list();
                if (list.size() > 0) {
                    objType = list.get(0).toString();
                }

            }

            if ("".equals(objType) || objType.equals(Constant.SHOP_TYPE)) {
                queryStringShop = "";
                queryStringShop +=
                        " SELECT SP1.shop_id AS objId ";
                queryStringShop +=
                        "   ,SP1.shop_code AS objCode ";
                queryStringShop +=
                        "   ,SP1.name AS objName ";
                queryStringShop +=
                        " FROM SHOP SP1 ";
                queryStringShop +=
                        " WHERE 1=1 ";
                if (objId != null) {
                    queryStringShop += "     AND SP1.shop_id = ? ";
                    lstParam.add(objId);
                }

            }

            if ("".equals(objType) || objType.equals(Constant.STAFF_TYPE)) {
                queryStringStaff = "";
                queryStringStaff +=
                        " SELECT SF1.staff_id AS objId ";
                queryStringStaff +=
                        "   ,SF1.staff_code AS objCode ";
                queryStringStaff +=
                        "   ,SF1.name AS objName ";
                queryStringStaff +=
                        " FROM STAFF SF1 ";
                queryStringStaff +=
                        " WHERE  1=1 ";
                if (objId != null) {
                    queryStringStaff += "     AND SF1.staff_id = ? ";
                    lstParam.add(objId);
                }

            }

            if ((!"".equals(queryStringShop)) && (!"".equals(queryStringStaff))) {
                queryString = queryStringShop + " UNION " + queryStringStaff;
            } else {
                queryString = queryStringShop + " " + queryStringStaff;
            }

            queryObject = null;
            queryObject =
                    getSession().createSQLQuery(queryString).
                    addScalar("objId", Hibernate.LONG).
                    addScalar("objCode", Hibernate.STRING).
                    addScalar("objName", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(ComboListBean.class));



            for (int idx = 0;
                    idx < lstParam.size();
                    idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }

            list = null;
            list = queryObject.list();
            return list;
        } catch (RuntimeException re) {
            throw re;
        }

    }

    public List getInvoiceStatusList(
            Long statusType) {
        try {
            List lstParam = new ArrayList();
            String queryString = "";
            String queryStringList = "";
            String queryStringDestroyed = "";

            if (statusType == null || statusType.compareTo(1L) != 0) {
                queryStringList += " SELECT 1 AS objId ";
                queryStringList +=
                        "     ,'1_' || aps.code AS objCode ";
                queryStringList +=
                        "     ,aps.NAME AS objName ";
                queryStringList +=
                        " FROM app_params aps ";
                queryStringList +=
                        " WHERE aps.TYPE = 'INVOICE_LIST.STATUS' ";
                queryStringList +=
                        "     AND aps.status = 1 ";
            }

            if (statusType == null || statusType.compareTo(2L) != 0) {
                queryStringDestroyed += " SELECT 2 AS objId ";
                queryStringDestroyed +=
                        "     ,'2_' || aps.code AS objCode ";
                queryStringDestroyed +=
                        "     ,aps.NAME AS objName  ";
                queryStringDestroyed +=
                        " FROM app_params aps  ";
                queryStringDestroyed +=
                        " WHERE aps.TYPE = 'INVOICE_DESTROYED.STATUS' ";
                queryStringDestroyed +=
                        "     AND aps.status = 1 ";
            }

            if ((!"".equals(queryStringList)) && (!"".equals(queryStringDestroyed))) {
                queryString = " UNION  ";
            }

            queryString = queryStringList + queryString + queryStringDestroyed;
            if ("".equals(queryString)) {
                return null;
            }

            queryString += " ORDER BY objId, objCode ";

            Query queryObject = getSession().createSQLQuery(queryString).
                    addScalar("objId", Hibernate.LONG).
                    addScalar("objCode", Hibernate.STRING).
                    addScalar("objName", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(ComboListBean.class));











            return queryObject.list();
        } catch (RuntimeException re) {
            throw re;
        }

    }

    public List getReasonSaleList(
            Long saleGroup, Long saleTransType) {
        try {
            List lstParam = new ArrayList();
            String queryString = "";
            if (saleGroup == null) {
                saleGroup = 1L;
            }

            queryString += " SELECT r.reason_id AS objId ";
            queryString +=
                    "     ,r.reason_code AS objCode ";
            queryString +=
                    "     ,r.reason_name AS objName ";
            queryString +=
                    " FROM reason r ";
            queryString +=
                    " WHERE r.reason_type IN ";
            queryString +=
                    "     (SELECT sit.reason_sale_group_code FROM sale_invoice_type sit WHERE 1=1 ";
            if (null != saleTransType) {
                queryString += "        and sit.sale_trans_type = ? ";
                lstParam.add(saleTransType);
            } else if (null != saleGroup) {
                queryString += "        and sit.sale_group = ? ";
                lstParam.add(saleGroup);
            }

            queryString += "     ) ";

            queryString +=
                    " ORDER BY objCode ";

            Query queryObject = getSession().createSQLQuery(queryString).
                    addScalar("objId", Hibernate.LONG).
                    addScalar("objCode", Hibernate.STRING).
                    addScalar("objName", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(ComboListBean.class));

            for (int idx = 0;
                    idx < lstParam.size();
                    idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            throw re;
        }

    }

    public List getReasonInvoiceList(
            Long saleGroup, Long saleTransType, String saleTransTypeList) {
        try {
            List lstParam = new ArrayList();
            String queryString = "";
            if (saleGroup == null) {
                saleGroup = 1L;
            }

            queryString += " SELECT r.reason_id AS objId ";
            queryString +=
                    "     ,r.reason_code AS objCode ";
            queryString +=
                    "     ,r.reason_name AS objName ";
            queryString +=
                    " FROM reason r ";
            queryString +=
                    " WHERE r.reason_type IN ";
            queryString +=
                    "     (SELECT sit.reason_invoice_group_code FROM sale_invoice_type sit WHERE 1=1 ";
            if (null != saleTransType) {
                queryString += "        and sit.sale_trans_type = ? ";
                lstParam.add(saleTransType);
            } else if (null != saleTransTypeList && !"".equals(saleTransTypeList.trim())) {
                String tmp = "";
                for (int index = 0; index
                        < saleTransTypeList.split(",").length; index++) {
                    if (!"".equals(tmp.trim())) {
                        tmp += " or ";
                    }

                    tmp += "        sit.sale_trans_type = ? ";
                    lstParam.add(Long.valueOf(saleTransTypeList.split(",")[index].trim()));
                }

                if (!"".equals(tmp.trim())) {
                    queryString += " and ( " + tmp + " ) ";
                }

            } else if (null != saleGroup) {
                queryString += "        and sit.sale_group = ? ";
                lstParam.add(saleGroup);
            }

            queryString += "     ) ";

            queryString +=
                    " ORDER BY objCode ";

            Query queryObject = getSession().createSQLQuery(queryString).
                    addScalar("objId", Hibernate.LONG).
                    addScalar("objCode", Hibernate.STRING).
                    addScalar("objName", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(ComboListBean.class));



            for (int idx = 0;
                    idx < lstParam.size();
                    idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            throw re;
        }

    }

    public DateTimeDB getDateTimeDB() {
        try {
            String queryString = " SELECT a.date_time_string as dateTimeString, a.date_string as dateString, a.time_string as timeString FROM get_dateTime a  ";
            Query queryObject = getSession().createSQLQuery(queryString).
                    addScalar("dateTimeString", Hibernate.STRING).
                    addScalar("dateString", Hibernate.STRING).
                    addScalar("timeString", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(DateTimeDB.class));

            List<DateTimeDB> list = queryObject.list();




            return list.get(
                    0);
        } catch (RuntimeException re) {
            return null;
        }
    }

    //get Sale_Trans_Code from Sale_Trans_Id
    public static String formatTransCode(Long transId) {
        return Constant.TRANS_CODE_PREFIX + String.format("%0" + Constant.TRANS_ID_LENGTH + "d", transId);
    }

    public static List findByProperty(Session mySession, String tableName, HashMap<String, Object> hashMap) throws Exception {
        try {
            List lstResult = new ArrayList();

            if (mySession == null) {
                return lstResult;
            }
            if (tableName == null || tableName.trim().equals("")) {
                return lstResult;
            }
            if (hashMap == null) {
                return lstResult;
            }

            List lstParam = new ArrayList();
            StringBuffer queryString = new StringBuffer("from " + tableName + " as model where 1=1 ");

            if (hashMap.size() > 0) {
                Set<String> key = hashMap.keySet();
                Iterator<String> iteratorKey = key.iterator();
                while (iteratorKey.hasNext()) {
                    String propertyName = iteratorKey.next();
                    if (propertyName.startsWith("ORDER_BY")) {
                        continue;
                    }
                    if (propertyName.startsWith("IS_NULL.")) {
                        queryString.append("and model.").append(com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName.replaceAll("IS_NULL.", ""))).append(" is null ");
                    } else if (propertyName.startsWith("IS_NULL")) {
                        queryString.append("and model.").append(com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName.replaceAll("IS_NULL", ""))).append(" is null ");
                    } else if (propertyName.startsWith("NOT_NULL.")) {
                        queryString.append("and model.").append(com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName.replaceAll("NOT_NULL.", ""))).append(" is not null ");
                    } else if (propertyName.startsWith("NOT_NULL")) {
                        queryString.append("and model.").append(com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName.replaceAll("NOT_NULL", ""))).append(" is not null ");
                    } else if (propertyName.startsWith("NOT_EQUAL...")) {
                        queryString.append("and not model.").append(com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName.replaceAll("NOT_EQUAL...", ""))).append("=? ");
                        lstParam.add(hashMap.get(propertyName));
                    } else if (propertyName.startsWith("NOT_EQUAL..")) {
                        queryString.append("and not model.").append(com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName.replaceAll("NOT_EQUAL..", ""))).append("=? ");
                        lstParam.add(hashMap.get(propertyName));
                    } else if (propertyName.startsWith("NOT_EQUAL.")) {
                        queryString.append("and not model.").append(com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName.replaceAll("NOT_EQUAL.", ""))).append("=? ");
                        lstParam.add(hashMap.get(propertyName));
                    } else if (propertyName.startsWith("NOT_EQUAL")) {
                        queryString.append("and not model.").append(com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName.replaceAll("NOT_EQUAL", ""))).append("=? ");
                        lstParam.add(hashMap.get(propertyName));
                    } else {
                        queryString.append("and model.").append(com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName)).append("=? ");
                        lstParam.add(hashMap.get(propertyName));
                    }
                }
            }
            if (hashMap.get("ORDER_BY") != null) {
                if (hashMap.get("ORDER_BY").toString().trim().toLowerCase().startsWith("order by")) {
                    queryString.append(hashMap.get("ORDER_BY"));
                } else {
                    queryString.append(" order by ").append(hashMap.get("ORDER_BY"));
                }

            }

            Query queryObject = mySession.createQuery(queryString.toString());
            for (int idx = 0; idx < lstParam.size(); idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }
            lstResult = queryObject.list();

            return lstResult;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public static int deleteObject(Session mySession, String tableName, HashMap<String, Object> hashMap) throws Exception {
        try {
            int result = -1;

            if (mySession == null) {
                return result;
            }
            if (tableName == null || tableName.trim().equals("")) {
                return result;
            }
            if (hashMap == null) {
                return result;
            }

            List lstParam = new ArrayList();
            StringBuffer queryString = new StringBuffer("delete from " + tableName + " as model where 1=1 ");

            if (hashMap.size() > 0) {
                Set<String> key = hashMap.keySet();
                Iterator<String> iteratorKey = key.iterator();
                while (iteratorKey.hasNext()) {
                    String propertyName = iteratorKey.next();
                    queryString.append("and model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "=? ");
                    lstParam.add(hashMap.get(propertyName));
                }
            }

            Query queryObject = mySession.createQuery(queryString.toString());
            for (int idx = 0; idx < lstParam.size(); idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }
            result = queryObject.executeUpdate();

            return result;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    /**
     *
     * author   : TamDT1
     * date     : 05/08/2010
     * muc dich : lay gia tri cua 1 thuoc tinh cua object
     * dau vao:
     *              - Object
     *              - Ten thuoc tinh can lay du lieu
     * dau ra:
     *              - Gia tri can lay cua ham get
     */
    public static Object getObjectPropertyValue(Object updatedObject, String propertyName) {
        try {
            //lay doi tuong tro den lop can update
            Class updatedClass = updatedObject.getClass();
            Class partypes[] = new Class[0];

            Method getFieldMethod = updatedClass.getMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), partypes);
            Object arglist[] = new Object[0];
            Object retobj = getFieldMethod.invoke(updatedObject, arglist);
            return retobj;


        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * author   : TamDT1
     * date     : 05/08/2010
     * muc dich : lay value dua tren key (multilanguage)
     *
     */
    public static String imGetText(String _key) {
        try {

            BaseDAOAction baseDAOAction = new BaseDAOAction();

            String result = baseDAOAction.getText(_key);

            result = com.viettel.security.util.StringEscapeUtils.escapeHtml(result);
            return result;
        } catch (Exception ex) {

            System.out.println("CommonDAO.imGetText(String _key)");

            System.out.println(_key);
//            ex.printStackTrace();
            String result = com.viettel.security.util.StringEscapeUtils.escapeHtml(_key);
            return result;
        }
    }

    /**
     *
     * author   : TamDT1
     * date     : 05/08/2010
     * muc dich : lay bien tren request
     *
     */
    public static List imGetList(HttpServletRequest req, String variableName) {
        try {
            return (List) req.getAttribute(variableName);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static List<com.viettel.im.common.imObject> imGetList(HttpServletRequest req, String variableName, String listKey, String listValue) {
        try {

            List list = (List) req.getAttribute(variableName);
            List<com.viettel.im.common.imObject> listImObject = new ArrayList<com.viettel.im.common.imObject>();

//            if (list != null && list.size() > 0) {
//                for (int i = 0; i < list.size(); i++) {
//                    listImObject.add(new com.viettel.im.common.imObject("",""));
//                }
//            }

            return listImObject;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * purpose  : lay danh sach doi tac
     *
     */
    public List<ImSearchBean> getListPartner(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.partnerCode, a.partnerName) ");
        strQuery1.append("from Partner a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.partnerCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.partnerName) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.partnerCode, 'nls_sort=Vietnamese') asc ");

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
     * date     : 02/09/2010
     * purpose  : so luong doi tac
     *
     */
    public Long getListPartnerSize(ImSearchBean imSearchBean) {
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from Partner a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.partnerCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.partnerName) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * purpose  : lay ten doi tac
     *
     */
    public List<ImSearchBean> getPartnerName(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }

        //lay ten cua doi tac dua tren code
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.partnerCode, a.partnerName) ");
        strQuery1.append("from Partner a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        strQuery1.append("and lower(a.partnerCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.partnerCode, 'nls_sort=Vietnamese') asc ");

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

    public Long getShopIdByProvinceCodeAndShopCode(Session mySession, String provinceCode, String shopCode) {
        if (provinceCode == null || provinceCode.trim().equals("") || shopCode == null || shopCode.trim().equals("")) {
            return null;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(mySession);
        return shopDAO.getShopIdByProvinceAndNo(provinceCode.trim(), shopCode.trim(), Constant.STATUS_USE, Constant.IS_VT_UNIT);
    }

    /**
     * @Author: TrongLV
     * @Purpose: check <= trun(sysdate) & >= trunc(trunc(sysdate,'MM')-1,'MM')
     * @param bankReceiptDate
     * @return
     */
    public static String validateBankReceiptDate(Date bankReceiptDate) {

        try {
            if (bankReceiptDate == null) {
                return "Error. Bank receipt date is null!";
            }
            //Khong duoc lon hon ngay hien tai
            if (bankReceiptDate.after(new Date())) {
                return "Error. Bank receipt date is must not greater than current date";
            }
            //Khong duoc nam ngoai thang truoc
            Date lowDate = DateTimeUtils.getDateWithDayFirstMonth(DateTimeUtils.addMonth(new Date(), -1));
            if (lowDate.after(DateTimeUtils.getEndOfDay(bankReceiptDate))) {
                return "Error. Bank receipt date is must not less than first day of last month!";
            }
            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    public int BANK_RECEIPT_LEN_DATE = 4;
    public int BANK_RECEIPT_LEN_SERVICE = 2;
    public String BANK_RECEIPT_DATE_FORMAT = "dd/MM/yyyy";
    public String BANK_RECEIPT_CODE_DATE_FORMAT = "yyMM";

    public String validateBankReceiptCode(Session mySession, Long shopId, Long bankAccountId, String bankReceiptCode, Date date) {

//        if (true)return "";

        String tmp = bankReceiptCode;
        try {
            if (shopId == null) {
                return "Error. Shop is null!";
            }
            if (bankAccountId == null) {
                return "Error. Account number is null!!!";
            }
            if (bankReceiptCode == null || bankReceiptCode.trim().equals("")) {
                return "Error. Bank receipt code is null!";
            }

            if (bankReceiptCode == null || bankReceiptCode.trim().equals("")) {
                return getText("E.200004");
            } else if (bankReceiptCode.trim().length() <= BANK_RECEIPT_LEN_DATE + BANK_RECEIPT_LEN_SERVICE) {
                return getText("E.200005");
            }

            String bankDate = "";
            String serviceCode = "";
            String shopCode = "";

            bankDate = bankReceiptCode.trim().substring(bankReceiptCode.trim().length() - BANK_RECEIPT_LEN_DATE);
            serviceCode = bankReceiptCode.trim().substring(bankReceiptCode.trim().length() - BANK_RECEIPT_LEN_DATE - BANK_RECEIPT_LEN_SERVICE, bankReceiptCode.trim().length() - BANK_RECEIPT_LEN_DATE);
            shopCode = bankReceiptCode.trim().substring(0, bankReceiptCode.trim().length() - BANK_RECEIPT_LEN_DATE - BANK_RECEIPT_LEN_SERVICE);

//            if (bankReceiptCode.length() >= Constant.BANK_RECEIPT_LEN_DATE) {
//                bankDate = bankReceiptCode.substring(bankReceiptCode.length() - Constant.BANK_RECEIPT_LEN_DATE);
//                bankReceiptCode = bankReceiptCode.substring(0, bankReceiptCode.length()
//                        - Constant.BANK_RECEIPT_LEN_DATE);
//            } else {
//                return "Error. Bank receipt code is invalid";
//            }
//            if (bankReceiptCode.length() >= Constant.BANK_RECEIPT_LEN_SERVICE) {
//                serviceCode = bankReceiptCode.substring(bankReceiptCode.length() - Constant.BANK_RECEIPT_LEN_SERVICE);
//                bankReceiptCode = bankReceiptCode.substring(0, bankReceiptCode.length()
//                        - Constant.BANK_RECEIPT_LEN_SERVICE);
//            } else {
//                return "Error. Bank receipt code is invalid! (" + tmp + " )";
//            }
//            shopCode = bankReceiptCode;

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopVTByCodeAndStatusAndUnit(shopCode, Constant.STATUS_USE, Constant.IS_VT_UNIT);
            if (shop == null) {
                return "Error. Shop code is not exists! (" + shopCode + " )";
            }
            Long shopIdBRC = shop.getShopId();
            if (shopIdBRC == null) {
                return "Error. Shop code is not exists! (" + shopCode + " )";
            }
            if (!shopIdBRC.equals(shopId)) {
                return "Error. Shop code in bankreceiptcode must be same with current shop!";
            }

            BankAccountDAO bankAccountDAO = new BankAccountDAO();
            bankAccountDAO.setSession(mySession);
            BankAccount bankAccount = bankAccountDAO.findById(bankAccountId);
            if (bankAccount == null || bankAccount.getBankAccountId() == null) {
                return "Error. Account number is invalid!";
            }
            BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
            bankAccountGroupDAO.setSession(mySession);
            BankAccountGroup bankAccountGroup = bankAccountGroupDAO.findById(bankAccount.getBankAccountGroupId());
            if (bankAccountGroup == null || bankAccountGroup.getBankAccountGroupId() == null) {
                return "Error. Account number is invalid!";
            }
            if (bankAccountGroup.getCode() == null
                    || !bankAccountGroup.getCode().trim().toUpperCase().equals(serviceCode)) {
                return "Error. Service code in bankreceiptcode must be same with current service code!";
            }

//            Date date1 = DateTimeUtils.convertStringToDate(bankDate, Constant.BANK_RECEIPT_CODE_DATE_FORMAT);
//
//            if (date1 == null || date == null || !date1.equals(date)) {
//                return "Error. Date in BankReceiptCode must be same with BankDate!";
//            }

            return "";

//            String month = DateTimeUtils.convertDateTimeToString(date1, "MM");
//            String year = DateTimeUtils.convertDateTimeToString(date1, "yyyy");
//
//
//            String currMonth = DateTimeUtils.convertDateTimeToString(date, "MM");
//            if (currMonth == null || !currMonth.equals(month)) {
//                return "Error. Month in bankreceiptcode must be same with current month!";
//            }
//            String currYear = DateTimeUtils.convertDateTimeToString(date, "yyyy");
//            if (currYear == null || !currYear.equals(year)) {
//                return "Error. Year in bankreceiptcode must be same with current year!";
//            }
//
//            return "";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Purpose: check Role of curr
     * @param mySession
     * @param shopId
     * @param currentLevel
     * @return
     */
    public static boolean checkRoleAssignChannelToGoods(Session mySession, Long shopId, boolean currentLevel) {
        if (mySession == null || shopId == null) {
            return false;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(mySession);
        Shop shop = shopDAO.findById(shopId);
        if (shop == null || shop.getShopId() == null || shop.getShopPath() == null || shop.getShopPath().trim().equals("")) {
            return false;
        }
        int level = 0;
        String shopPath = shop.getShopPath().trim();
        for (int i = 0; i < shopPath.length(); i++) {
            if (shopPath.charAt(i) == '_') {
                level++;
            }
        }
        if (!currentLevel) {
            level++;
        }

        int param_level = 1;
        try {
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(mySession);
            AppParams appParams = appParamsDAO.findAppParams(Constant.APP_PARAMS_TYPE_LEVEL_ASSIGN_CHANNEL_TO_GOODS, Constant.APP_PARAMS_TYPE_LEVEL_ASSIGN_CHANNEL_TO_GOODS);
            if (appParams != null && appParams.getValue() != null && !appParams.getValue().trim().equals("")) {
                param_level = Integer.parseInt(appParams.getValue().trim());
            }
        } catch (Exception ex) {
        }

        if (level > param_level) {
            return false;
        }

        return true;


    }

    public List getChannelTypeByStaffOwnerId(Session mySession, Long staffId) {
        try {

            List listParameter = new ArrayList();
            String strQuery = "from ChannelType where 1=1 and status = ? and isVtUnit = ? and channelTypeId in (select channelTypeId from Staff where 1=1 and status = ? and staffOwnerId = ?) ";
            listParameter.add(Constant.STATUS_USE);
            listParameter.add(Constant.IS_NOT_VT_UNIT);
            listParameter.add(Constant.STATUS_USE);
            listParameter.add(staffId);
            Query query = getSession().createQuery(strQuery);
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List<ChannelType> tmpList = query.list();
            if (tmpList != null && tmpList.size() > 0) {
                return tmpList;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public List getChannelTypeByShopId(Session mySession, Long shopId) {
        try {

            List listParameter = new ArrayList();
            String strQuery = "from ChannelType where 1=1 and status = ? and isVtUnit = ? and channelTypeId in (select channelTypeId from Staff where 1=1 and status = ? and shopId = ?) ";
            listParameter.add(Constant.STATUS_USE);
            listParameter.add(Constant.IS_NOT_VT_UNIT);
            listParameter.add(Constant.STATUS_USE);
            listParameter.add(shopId);
            Query query = getSession().createQuery(strQuery);
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List<ChannelType> tmpList = query.list();
            if (tmpList != null && tmpList.size() > 0) {
                return tmpList;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    //So luong shop thuoc viettel co parentshopid = cap hien tai
    public static int countShopVtByParentShopId(Session mySession, Long parentShopId) {
        try {

            List listParameter = new ArrayList();
            String strQuery = "select count(*) from shop a where parent_shop_id = ? and status = ? and exists (select 'x' from channel_type b where b.channel_type_id = a.channel_type_id and is_vt_unit = ? and status = ?) ";
            listParameter.add(parentShopId);
            listParameter.add(Constant.STATUS_USE);
            listParameter.add(Constant.IS_VT_UNIT);
            listParameter.add(Constant.STATUS_USE);

            Query query = mySession.createSQLQuery(strQuery);
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            BigDecimal count = (BigDecimal) query.uniqueResult();
            if (count == null) {
                return 0;
            }
            return count.intValue();

        } catch (Exception ex) {
            return 0;
        }
    }

    public static boolean checkValidateObjectCode(String objectCode) {
        //kiem tra ma mat hang chi gom cac chu cai a-z, A-Z, 0-9, _
        objectCode = (objectCode != null) ? objectCode.trim().toUpperCase() : "";
        if (objectCode.length() == 0) {
            return false;
        }
        char[] arrTmp = objectCode.toCharArray();
        for (int i = 0; i < arrTmp.length; i++) {
            if (arrTmp[i] >= 'a' && arrTmp[i] <= 'z') {
                continue;
            } else if (arrTmp[i] >= 'A' && arrTmp[i] <= 'Z') {
                continue;
            } else if (arrTmp[i] >= '0' && arrTmp[i] <= '9') {
                continue;
            } else if (arrTmp[i] == '_') {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean checkValidIdNo(String idNo) {
        idNo = (idNo != null) ? idNo.trim().toUpperCase() : "";
        if (idNo.length() < Constant.IDNO_MIN_LEN || idNo.length() > Constant.IDNO_MAX_LEN) {
            return false;
        }
        char[] arrTmp = idNo.toCharArray();
        for (int i = 0; i < arrTmp.length; i++) {
            if (arrTmp[i] >= 'A' && arrTmp[i] <= 'Z') {
                continue;
            } else if (arrTmp[i] >= '0' && arrTmp[i] <= '9') {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * author   : tutm1
     * date     : 01/08/2011
     * purpose  : lay danh sach cac kho thuoc VT 
     *
     */
    public List<ImSearchBean> getListShopVT(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_SHOP);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

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
     * author   : tutm1
     * date     : 01/08/2011
     * purpose  : lay danh sach chinh xac cac kho thuoc VT theo shopcode
     *
     */
    public List<ImSearchBean> getExtractListShopVT(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_SHOP);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
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
     * author   : tutm1
     * date     : 21/07/2011
     * purpose  : lay danh sach NV quan ly DB
     *
     */
    public List<ImSearchBean> getAgentManagers(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);



        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

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

    /**
     *
     * author   : tutm1
     * date     : 01/09/2011
     * purpose  : so luong cac kho duoc tim thay
     *
     */
    public Long getAgentManagersSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);



        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        }

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }


        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
    }

    /**
     *
     * author   : tutm1
     * date     : 21/07/2011
     * purpose  : lay danh sach NV quan ly DB
     *
     */
    public List<ImSearchBean> getExtractAgentManagers(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        //Modified by : TrongLV
        //Modified date : 2011-08-16
        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);



        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

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

    /**
     * @added tutm1 03/03/2012
     * @param input
     * @reason check ATTT
     * @return Remove cac ky tu dac biet khi hien thi ten file
     */
    public static String getSafeFileName(String input) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {

            char c = input.charAt(i);

            if (c != '/' && c != '\\' && c != 0) {

                sb.append(c);

            }
        }
        return sb.toString();
    }

    /**
     *
     * author   : tuannv1
     * date     : 07/04/2010: tÃ¡ch thÃ nh hÃ m riÃªng
     * purpose  : lay danh sach cac cong tac vien/dai ly/diem ban
     *
     */
    public List<ImSearchBean> getListChannel(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");

        String shopCode = "";
        String staffOwnerCode = "";

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String[] array = imSearchBean.getOtherParamValue().trim().split(";");
            if (array != null && array.length >= 1) {
                shopCode = array[0];
                if (array.length >= 2) {
                    staffOwnerCode = array[1];
                }
            }
        }

        if (!shopCode.trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(shopCode.trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }
        if (!staffOwnerCode.trim().equals("")) {
            strQuery1.append("and a.staffOwnerId in (select staffId from Staff where lower(staffCode) = ? ) ");
            listParameter1.add(staffOwnerCode.trim().toLowerCase());
        } else {
            /* 120510 - TRONGLV - khong check theo ma nvql */
            //truong hop chua co shop -> tra ve chuoi rong
//            return listImSearchBean;
        }


        //tuannv hieu chinh ngay 02/04/2010: cho phep tim kiem ca kho CTV/DB/DL
        // tutm1 18/11/2011
        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_NOT_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by a.pointOfSale desc, nlssort( a.staffCode, 'nls_sort=Vietnamese') asc ");

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
     * author   : tuannv1
     * date     : 07/04/2010: tÃ¡ch thÃ nh hÃ m riÃªng
     * purpose  : lay danh sach cac cong tac vien/dai ly/diem ban
     *
     */
    public Long getListChannelSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select count(*) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");

        String shopCode = "";
        String staffOwnerCode = "";

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String[] array = imSearchBean.getOtherParamValue().trim().split(";");
            if (array != null && array.length >= 1) {
                shopCode = array[0];
                if (array.length >= 2) {
                    staffOwnerCode = array[1];
                }
            }
        }

        if (!shopCode.trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(shopCode.trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return null;
        }
        if (!staffOwnerCode.trim().equals("")) {
            strQuery1.append("and a.staffOwnerId in (select staffId from Staff where lower(staffCode) = ? ) ");
            listParameter1.add(staffOwnerCode.trim().toLowerCase());
        } else {
            /* 120510 - TRONGLV - khong check theo ma nvql */
            //truong hop chua co shop -> tra ve chuoi rong
//            return null;
        }


        // tutm1 18/11/2011
        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_NOT_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }

    }

    /**
     *
     * author   : tuannv1
     * date     : 07/04/2010: tÃ¡ch thÃ nh hÃ m riÃªng
     * purpose  : lay danh sach cac cong tac vien/dai ly/diem ban
     *
     */
    public List<ImSearchBean> getChannelName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");

        String shopCode = "";
        String staffOwnerCode = "";

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String[] array = imSearchBean.getOtherParamValue().trim().split(";");
            if (array != null && array.length >= 1) {
                shopCode = array[0];
                if (array.length >= 2) {
                    staffOwnerCode = array[1];
                }
            }
        }

        if (!shopCode.trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(shopCode.trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }
        if (!staffOwnerCode.trim().equals("")) {
            strQuery1.append("and a.staffOwnerId in (select staffId from Staff where lower(staffCode) = ? ) ");
            listParameter1.add(staffOwnerCode.trim().toLowerCase());
        } else {
            /* 120510 - TRONGLV - khong check theo ma nvql */
            //truong hop chua co shop -> tra ve chuoi rong
//            return listImSearchBean;
        }


        // tutm1 18/11/2011
        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_NOT_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by a.pointOfSale desc, nlssort( a.staffCode, 'nls_sort=Vietnamese') asc ");

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
    public List<ImSearchBean> getListStaffAndChannel(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

//        strQuery1.append("and a.channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);

//Modified by : TrongLV
        //Modified date : 2011-08-16
//        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
//        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
//        listParameter1.add(Constant.IS_VT_UNIT);


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
        listParameter1.add(MAX_SEARCH_RESULT);

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

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay so luong cac nhan vien thuoc kho
     *
     */
    public Long getListStaffAndChannelSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return null;
        }

//        strQuery1.append("and a.channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);

        //Modified by : TrongLV
        //Modified date : 2011-08-16
//        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
//        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
//        listParameter1.add(Constant.IS_VT_UNIT);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }

    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac nhan vien thuoc kho
     *
     */
    public List<ImSearchBean> getStaffAndChannelName(ImSearchBean imSearchBean) {
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

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

//        strQuery1.append("and a.channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);

        //Modified by : TrongLV
        //Modified date : 2011-08-16
//        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
//        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
//        listParameter1.add(Constant.IS_VT_UNIT);

        strQuery1.append("and lower(a.staffCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

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

    //Ham doc loi tu Ex sang log info
    /* 120802 : TrongLV : Copy tu VTT sang */
    public static String readStackTrace(Exception e) {
        String strUserName = "";
        String strNow = "";
        try {
            HttpServletRequest req = new CommonDAO().getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            strUserName = userToken.getLoginName();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            strNow = dateFormat.format(new Date());
        } catch (Exception ex) {
            //
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        StringBuilder result = new StringBuilder("");
        result.append(strUserName).append(" ").append(strNow).append(" start\n").append(sw.toString()).append("\n").append(strUserName).append(" ").append(strNow).append(" end\n");
        return result.toString();
    }
    /**
     *
     * author   : SonNH27
     * date     : 24/02/2014
     * purpose  : lay danh sach cac kho thuoc VT
     *
     */
    public List<ImSearchBean> getListShopIsVTUnit(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ?  ) ");
        listParameter1.add(Constant.OBJECT_TYPE_SHOP);
        listParameter1.add(Constant.IS_VT_UNIT);

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
     * author   : truongnq5
     * date     : 18/9/2014
     * purpose  : so luong cac kho, mac dinh lay tat ca cac kho tu cap hien tai do xuong thuoc VIETTEL
     *
     */
    public List<ImSearchBean> getListShopIsVt(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");
        strQuery1.append("and channelTypeId in (select channelTypeId from ChannelType where status = 1 and isVtUnit = 1 and objectType = 1) ");//TruongNQ5
        strQuery1.append("and (a.shopPath like ? escape '$' or a.shopId = ?) ");
        listParameter1.add("%$_" + userToken.getShopId() + "$_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

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
     * author   : truongnq5
     * date     : 18/9/2014
     * purpose  : so luong cac kho, mac dinh lay tat ca cac kho tu cap hien tai do xuong thuoc VIETTEL
     *
     */
    public Long getListShopIsVtSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");
        strQuery1.append("and channelTypeId in (select channelTypeId from ChannelType where status = 1 and isVtUnit = 1 and objectType = 1) ");//TruongNQ5
        strQuery1.append("and (a.shopPath like ? escape '$' or a.shopId = ?) ");
        listParameter1.add("%$_" + userToken.getShopId() + "$_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
    }
//    ThinDM R6762
    public static Long getShopLevel(Session session, Long shopId) {
        // tutm1 14/12/2012 toi uu hoa bang cach lay shop_level tu bang TblShopTree
        Long shopLevel = null;
        String queryString = " select shop_level from Tbl_Shop_Tree where shop_id = ? and root_id = ? ";
        Query queryObject = session.createSQLQuery(queryString).addScalar("shop_level", Hibernate.LONG);
        queryObject.setParameter(0, shopId);
        queryObject.setParameter(1, Constant.VT_SHOP);
        List list = queryObject.list();
        if (list != null && list.size() > 0) {
            shopLevel = Long.parseLong(list.get(0).toString());
        }
        // truong hop bang Tbl_Shop_Tree chua dong bo thi tim theo V_shop_tree
        if (shopLevel == null) {
            queryString = " select shop_level from v_shop_tree where shop_id = ? and root_id = ? ";
            queryObject = session.createSQLQuery(queryString).addScalar("shop_level", Hibernate.LONG);
            queryObject.setParameter(0, shopId);
            queryObject.setParameter(1, Constant.VT_SHOP);
            list = queryObject.list();
            if (list != null && list.size() > 0) {
                shopLevel = Long.parseLong(list.get(0).toString());
            }
        }
        return shopLevel;
    }
//    end ThinDM
}
