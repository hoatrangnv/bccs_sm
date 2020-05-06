/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.database.BO.InvoiceCoupon;
import com.viettel.im.client.form.RetrieveInvoiceCouponForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

/**
 *
 * @author tronglv
 */
public class LookupInvoiceCouponDAO extends BaseDAOAction {

    private static final String REQUEST_MESSAGE = "returnMsg";      //Message tra ve Client
    private static final Log log = LogFactory.getLog(LookupInvoiceCouponDAO.class);
    private RetrieveInvoiceCouponForm form;
    private String PREPARE_PAGE = "preparePage";
    private String PAGE_NAVIGATOR = "pageNavigator";
    private String REQUEST_LIST_IC = "lstInvoiceCoupon";

    public RetrieveInvoiceCouponForm getForm() {
        return form;
    }

    public void setForm(RetrieveInvoiceCouponForm form) {
        this.form = form;
    }

    /**
     * @Author          :   TrongLV
     * @CreateDate      :   01/08/2010
     * @Purpose         :   Khoi tao trang web
     * @return
     * @throws Exception
     */
    public String preparePage() throws Exception {

        form = new RetrieveInvoiceCouponForm();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        form.setShopCode(userToken.getShopCode());
        form.setShopName(userToken.getShopName());
        form.setStaffCode(userToken.getLoginName());
        form.setStaffName(userToken.getStaffName());


        return this.PREPARE_PAGE;

    }

    /**
     * @Author          :   TrongLV
     * @CreateDate      :   01/08/2010
     * @Purpose         :   Kiem tra dieu kien tim kiem cuong hoa don
     * @return
     */
    private boolean valiateSearchInvoiceCoupon() {
        boolean result = false;

        HttpServletRequest req = this.getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //Neu chon SHOP
            if (!form.getShopCode().equals("")) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                Shop shop = shopDAO.findShopsAvailableByShopCode(form.getShopCode().trim());
                if (shop == null
                        || shop.getShopId() == null) {
                    req.setAttribute(REQUEST_MESSAGE, "ERR.BIL.027");
                    form.setShopCode("");
                    form.setShopName("");
                    form.setStaffCode("");
                    form.setStaffName("");
                    return result;
                }
                form.setOwnerId(shop.getShopId());
                form.setOwnerType(Constant.OWNER_TYPE_SHOP);
            }
            if (!form.getStaffCode().equals("")) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                Staff staff = staffDAO.findStaffAvailableByStaffCode(form.getStaffCode().trim());
                if (staff == null
                        || staff.getStaffId() == null
                        || staff.getShopId() == null
                        || staff.getShopId().compareTo(form.getOwnerId()) != 0) {
                    req.setAttribute(REQUEST_MESSAGE, "ERR.BIL.028");
                    form.setStaffCode("");
                    form.setStaffName("");
                    return result;
                }
                form.setOwnerId(staff.getStaffId());
                form.setOwnerType(Constant.OWNER_TYPE_STAFF);
            }
            if (form.getOwnerId() == null || form.getOwnerType() == null) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.BIL.027");
                form.setShopCode("");
                form.setShopName("");
                form.setStaffCode("");
                form.setStaffName("");
                return result;
            }

            if (form.getFromInvoiceSearch() != null && !form.getFromInvoiceSearch().trim().equals("")) {
                try {
                    form.setFromInvoice(new Long(form.getFromInvoiceSearch().trim()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    form.setFromInvoice(null);
                    req.setAttribute(REQUEST_MESSAGE, "ERR.BIL.030");
                    return result;
                }
            }
            if (form.getToInvoiceSearch() != null && !form.getToInvoiceSearch().trim().equals("")) {
                try {
                    form.setToInvoice(new Long(form.getToInvoiceSearch().trim()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    form.setToInvoice(null);
                    req.setAttribute(REQUEST_MESSAGE, "ERR.BIL.031");
                    return result;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, ex.getMessage());
            return result;
        }

        return !result;
    }

    /**
     * @Author          :   TrongLV
     * @CreateDate      :   01/08/2010
     * @Purpose         :   Tim kiem cuong hoa don
     * @return
     * @throws Exception
     */
    public String searchInvoiceCoupon() throws Exception {

        HttpServletRequest req = this.getRequest();


        if (!this.valiateSearchInvoiceCoupon()) {
            return this.PREPARE_PAGE;
        }

        List<RetrieveInvoiceCouponForm> listIC = this.getInvoiceCouponList(this.getForm());

        if (listIC == null || listIC.isEmpty()) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.BIL.032");
            return this.PREPARE_PAGE;
        }
        req.setAttribute(this.REQUEST_LIST_IC, listIC);

        return this.PREPARE_PAGE;
    }

    /**
     * @Author          :   TrongLV
     * @CreateDate      :   01/08/2010
     * @Purpose         :   Tim kiem cuong hoa don
     * @param pForm
     * @return
     */
    private List<RetrieveInvoiceCouponForm> getInvoiceCouponList(RetrieveInvoiceCouponForm pForm) {
        try {
            List listParameter = new ArrayList();
            String SQL_SELECT = "  SELECT serial_no as serialNo, status,   MIN (invoice_number) AS fromInvoice, "
                    + "         MAX (invoice_number) AS toInvoice, "
                    + "         MAX (invoice_number) - MIN (invoice_number) + 1 AS quantity "
                    + "    FROM (SELECT serial_no, status, invoice_number, "
                    + "                 invoice_number - ROW_NUMBER () OVER (ORDER BY serial_no, status, invoice_number) rn "
                    + "            FROM v_invoice_coupon "
                    + "           WHERE 1= 1          ";
            if (pForm.getOwnerType() != null) {
                SQL_SELECT += "           and owner_type = ? ";
                listParameter.add(pForm.getOwnerType());
            }
            if (pForm.getOwnerId() != null) {
                SQL_SELECT += "           and owner_id = ? ";
                listParameter.add(pForm.getOwnerId());
            }
            if (pForm.getSerialNo() != null && !pForm.getSerialNo().trim().equals("")) {
                SQL_SELECT += "           and serial_no = ? ";
                listParameter.add(pForm.getSerialNo().trim());
            }
            if (pForm.getStatus() != null) {
                SQL_SELECT += "           and status = ? ";
                listParameter.add(pForm.getStatus());
            }
            if (pForm.getFromInvoice() != null) {
                SQL_SELECT += "           and invoice_number >= ? ";
                listParameter.add(pForm.getFromInvoice());
            }
            if (pForm.getToInvoice() != null) {
                SQL_SELECT += "           and invoice_number <= ? ";
                listParameter.add(pForm.getToInvoice());
            }

            SQL_SELECT += "           )"
                    + "GROUP BY serial_no, status,  rn "
                    + "ORDER BY serialNo, fromInvoice ";

            Query q = getSession().createSQLQuery(SQL_SELECT).
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("status", Hibernate.LONG).
                    addScalar("fromInvoice", Hibernate.LONG).
                    addScalar("toInvoice", Hibernate.LONG).
                    addScalar("quantity", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(RetrieveInvoiceCouponForm.class));

            for (int i = 0; i < listParameter.size(); i++) {
                q.setParameter(i, listParameter.get(i));
            }

            return q.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @Author          :   TrongLV
     * @CreateDate      :   01/08/2010
     * @Purpose         :   Lay thong tin ky hieu hoa don
     * @param imSearchBean
     * @return
     */
    public List<ImSearchBean> getListSerialNo(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        try {
            HttpServletRequest req = imSearchBean.getHttpServletRequest();
            String strQuery = "";
            Query query;
            List lstParam = new ArrayList();
            strQuery = " select new com.viettel.im.client.bean.ImSearchBean(a.serialCode, a.blockName) from BookType a ";
            strQuery += "where 1=1 and a.status=? ";
            lstParam.add(Constant.STATUS_USE);
            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery += "and lower(a.serialCode) like ? ";
                lstParam.add(imSearchBean.getCode().trim().toLowerCase() + "%");
            }
            strQuery += " order by serialCode";

            query = getSession().createQuery(strQuery);
            for (int idx = 0; idx < lstParam.size(); idx++) {
                query.setParameter(idx, lstParam.get(idx));
            }

            List<ImSearchBean> tmpList1 = query.list();
            if (tmpList1 != null && tmpList1.size() > 0) {
                listImSearchBean.addAll(tmpList1);
            }

            return listImSearchBean;
        } catch (Exception ex) {
            ex.printStackTrace();
            return listImSearchBean;
        }
    }

    /**
     * @Author          :   TrongLV
     * @CreateDate      :   01/08/2010
     * @Purpose         :   Lay thong tin ten ky hieu hoa don
     * @param imSearchBean
     * @return
     */
    public List<ImSearchBean> getSerialName(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        try {
            HttpServletRequest req = imSearchBean.getHttpServletRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            String strQuery = "";
            Query query;
            List lstParam = new ArrayList();
            strQuery = " select new com.viettel.im.client.bean.ImSearchBean(a.serialCode, a.blockName) from BookType a ";
            strQuery += "where 1=1 and a.status=? ";
            lstParam.add(Constant.STATUS_USE);

            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery += "and lower(a.serialCode) = ? ";
                lstParam.add(imSearchBean.getCode().trim().toLowerCase());
            }

            strQuery += "order by serialCode ";

            query = getSession().createQuery(strQuery);

            for (int idx = 0; idx < lstParam.size(); idx++) {
                query.setParameter(idx, lstParam.get(idx));
            }

            List<ImSearchBean> tmpList1 = query.list();
            if (tmpList1 != null && tmpList1.size() > 0) {
                listImSearchBean.addAll(tmpList1);
            }

            return listImSearchBean;
        } catch (Exception ex) {
            ex.printStackTrace();
            return listImSearchBean;
        }
    }

    /**
     * @Author          :   TrongLV
     * @CreateDate      :   01/08/2010
     * @Purpose         :   Lay danh sach doi tuong
     * @param imSearchBean
     * @return
     */
    public List<ImSearchBean> getListObject(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        try {
            HttpServletRequest req = imSearchBean.getHttpServletRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                String objType = imSearchBean.getOtherParamValue().trim();

                String strQuery = "";
                Query query;
                List lstParam = new ArrayList();
                if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {
                    strQuery = " select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) from Shop a ";
                    strQuery += "where 1=1 and a.status=? and a.parentShopId=? ";
                    lstParam.add(Constant.STATUS_USE);
                    lstParam.add(userToken.getShopId());

                    strQuery+=" and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ";
                    lstParam.add(Constant.OBJECT_TYPE_SHOP);
                    lstParam.add(Constant.IS_VT_UNIT);
                    //strQuery += " and a.channelTypeId = ? ";
                    //lstParam.add(Constant.CHANNEL_TYPE_SHOP);

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += "and lower(a.shopCode) like ? ";
                        lstParam.add(imSearchBean.getCode().trim().toLowerCase() + "%");
                    }
                    strQuery += "order by shopCode ";
                    query = getSession().createQuery(strQuery);
                } else if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                    strQuery = " select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) from Staff a ";
                    strQuery += " where 1=1 and a.status=? and and a.shopId=? ";
                    lstParam.add(Constant.STATUS_USE);
                    lstParam.add(userToken.getShopId());

                    //Modified by : TrongLV
                    //Modified date : 2011-08-16
                    strQuery += "and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ";
                    lstParam.add(Constant.OBJECT_TYPE_STAFF);
                    lstParam.add(Constant.IS_VT_UNIT);

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += "and lower(a.staffCode) like ? ";
                        lstParam.add(imSearchBean.getCode().trim().toLowerCase() + "%");
                    }

                    strQuery += "order by staffCode ";

                    query = getSession().createQuery(strQuery);
                } else {
                    throw new Exception("ERR.BIL.029");
                }
                for (int idx = 0; idx < lstParam.size(); idx++) {
                    query.setParameter(idx, lstParam.get(idx));
                }

                List<ImSearchBean> tmpList1 = query.list();
                if (tmpList1 != null && tmpList1.size() > 0) {
                    listImSearchBean.addAll(tmpList1);
                }
            } else {
                throw new Exception("ERR.BIL.025");
            }
            return listImSearchBean;
        } catch (Exception ex) {
            ex.printStackTrace();
            return listImSearchBean;
        }
    }

    /**
     * @Author          :   TrongLV
     * @CreateDate      :   01/08/2010
     * @Purpose         :   Hien thi ten doi tuong
     * @param imSearchBean
     * @return
     */
    public List<ImSearchBean> getObjectName(ImSearchBean imSearchBean) {
        try {

            imSearchBean.setHttpServletRequest(this.getRequest());
            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                String objType = imSearchBean.getOtherParamValue().trim();

                if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {
                    return getShopName(imSearchBean);
                }
                if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                    return getStaffName(imSearchBean);
                } else {
                    throw new Exception("ERR.BIL.029");
                }
            } else {
                throw new Exception("ERR.BIL.025");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<ImSearchBean>();
        }
    }

    /**
     * @Author          :   TrongLV
     * @CreateDate      :   01/08/2010
     * @Purpose         :   Hien thi ten cua hang
     * @param imSearchBean
     * @return
     */
    private List<ImSearchBean> getShopName(ImSearchBean imSearchBean) {
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

        strQuery1.append("and a.parentShopId = ?) ");
        listParameter1.add(userToken.getShopId());

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
     * @Author          :   TrongLV
     * @CreateDate      :   01/08/2010
     * @Purpose         :   Hien thi ten nhan vien
     * @param imSearchBean
     * @return
     */
    private List<ImSearchBean> getStaffName(ImSearchBean imSearchBean) {
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
        strQuery1.append("and a.shopId = ? ");
        listParameter1.add(userToken.getShopId());

//        strQuery1.append("and a.channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);

        //Modified by : TrongLV
        //Modified date : 2011-08-16
        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);

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
}
