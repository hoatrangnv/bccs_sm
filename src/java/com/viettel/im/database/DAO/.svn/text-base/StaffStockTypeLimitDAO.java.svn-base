/*
 * Copyright Expression 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @author tutm1
 * 13/08/2011
 * chức năng cập nhật giá trị hạn mức cho từng loại kho nhân viên
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.common.Constant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.SaleChannelTypeForm;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.ChannelType;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import com.viettel.im.common.util.NumberUtils;

public class StaffStockTypeLimitDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(StaffStockTypeLimitDAO.class);
    private String pageForward;
    private SaleChannelTypeForm saleChannelTypeForm = new SaleChannelTypeForm();

    public SaleChannelTypeForm getSaleChannelTypeForm() {
        return saleChannelTypeForm;
    }

    public void setSaleChannelTypeForm(SaleChannelTypeForm saleChannelTypeForm) {
        this.saleChannelTypeForm = saleChannelTypeForm;
    }
    public static final String PRE_STAFF_STOCK_TYPE_LIMIT = "preStaffStockTypeLimit";
    public static final String SEARCH_STAFF_STOCK_TYPE_LIMIT = "searchStaffStockTypeLimit";
    public static final int SEARCH_RESULT_LIMIT = 100;

    public void save(ChannelType transientInstance) {
        log.debug("saving ChannelType  instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ChannelType persistentInstance) {
        log.debug("deleting ChannelType  instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ChannelType findById(java.lang.Long id) {
        log.debug("getting ChannelType instance with channelTypeId: " + id);
        try {
            ChannelType instance = (ChannelType) getSession().get("com.viettel.im.database.BO.ChannelType", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all ChannelType  instances");
        try {
//            String queryString = "from ChannelType as model where not status=" + Constant.STATUS_DELETE;
            String queryString = "from ChannelType where isPrivate = ? and isVtUnit = ? and objectType = ? ";
            queryString += " and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
            queryObject.setParameter(1, Constant.VT_UNIT);
            queryObject.setParameter(2, Constant.OBJECT_TYPE_STAFF);
            queryObject.setParameter(3, Constant.STATUS_ACTIVE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of StaffStockTypeLimitDAO ...");

        HttpServletRequest req = getRequest();
        SaleChannelTypeForm f = this.saleChannelTypeForm;

        f.resetForm();
        List listChannelType = new ArrayList();
        listChannelType = findAll();
        req.getSession().removeAttribute("listChannelType");
        req.getSession().setAttribute("listChannelType", listChannelType);
        req.getSession().setAttribute("toEditChannelType", 0);

        pageForward = PRE_STAFF_STOCK_TYPE_LIMIT;

        log.info("End method preparePage of StaffStockTypeLimitDAO");

        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of StaffStockTypeLimitDAO ...");
        pageForward = "pageNavigator";
        log.info("End method preparePage of StaffStockTypeLimitDAO");

        return pageForward;
    }
    /*
     * Author: NamNX Date created: 20/03/2009 Purpose: prepagePage of
     * editChannelType
     */

    public String prepareEditChannelType() throws Exception {
        log.info("Begin method preparePage of StaffStockTypeLimitDAO ...");

        HttpServletRequest req = getRequest();
        SaleChannelTypeForm f = this.saleChannelTypeForm;
        String strChannelTypeId = (String) QueryCryptUtils.getParameter(req, "channelTypeId");
        Long channelTypeId;
        try {
            channelTypeId = Long.parseLong(strChannelTypeId);
        } catch (Exception ex) {
            channelTypeId = -1L;
        }

        ChannelType bo = findById(channelTypeId);
        f.copyDataFromBO(bo);


        req.getSession().setAttribute("toEditChannelType", 1);

        pageForward = PRE_STAFF_STOCK_TYPE_LIMIT;

        log.info("End method preparePage of StaffStockTypeLimitDAO");

        return pageForward;
    }

    /*
     * Author: tutm1 Date created: 15/08/2011 Purpose: editChannelType luu lai
     * thay doi
     */
    public String editChannelType() throws Exception {
        log.info("Begin method preparePage of StaffStockTypeLimitDAO ...");

        HttpServletRequest req = getRequest();
        SaleChannelTypeForm f = this.saleChannelTypeForm;
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();

        // lay thong tin shop cu de ghi Log :
        ChannelType channelType = new StaffStockTypeLimitDAO().findById(f.getChannelTypeId());
        ChannelType oldChannelType = new ChannelType();
        BeanUtils.copyProperties(oldChannelType, channelType);
        oldChannelType.setStockType(null);

        if (f.getDebitDefaultStr() != null) {
            channelType.setDebitDefault(NumberUtils.roundNumber(Double.parseDouble(f.getDebitDefaultStr()), 4));
        }
        if (f.getRateDebitStr() != null) {
            channelType.setRateDebit(NumberUtils.roundNumber(Double.parseDouble(f.getRateDebitStr()), 2));
        }

        getSession().saveOrUpdate(channelType);

        // lay thong tin shop cu de ghi Log :
        lstLogObj.add(new ActionLogsObj(oldChannelType, channelType));
        saveLog(Constant.ACTION_EDIT_TYPE_CHANNEL, "Update max & rate debit of channel type (staff)", lstLogObj, channelType.getChannelTypeId());

        f.resetForm();
        req.setAttribute("returnMsg", "channelType.edit.success");
        req.getSession().setAttribute("toEditChannelType", 0);

        List listChannelType = new ArrayList();
        listChannelType = findAll();
        req.getSession().removeAttribute("listChannelType");
        req.getSession().setAttribute("listChannelType", listChannelType);


        pageForward = PRE_STAFF_STOCK_TYPE_LIMIT;
        log.info("End method preparePage of StaffStockTypeLimitDAO");
        return pageForward;
    }


    /*
     * Author: NamNX Date created: 30/03/2009 Purpose: tim kiem ChannelType
     */
    public String searchChannelType() throws Exception {
        log.info("Begin method searchChannelType of StaffStockTypeLimitDAO ...");

        HttpServletRequest req = getRequest();
        SaleChannelTypeForm f = this.saleChannelTypeForm;
        List listParameter = new ArrayList();

        String strQuery = "from ChannelType as model where 1=1 and isPrivate = ? and isVtUnit = ? and objectType = ? and status = ? ";

        listParameter.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        listParameter.add(Constant.VT_UNIT);
        listParameter.add(Constant.OBJECT_TYPE_STAFF);
        listParameter.add(Constant.STATUS_ACTIVE);

        if (f.getName() != null && !f.getName().trim().equals("")) {
            listParameter.add("%" + f.getName().trim().toLowerCase() + "%");
            strQuery += " and lower(model.name) like ? ";
        }
        if (f.getDebitDefaultStr() != null && !f.getDebitDefaultStr().trim().equals("")) {
            listParameter.add(Double.valueOf(f.getDebitDefaultStr()));
            strQuery += " and model.debitDefault = ? ";
        }
        if (f.getRateDebitStr() != null && !f.getRateDebitStr().equals("")) {
            listParameter.add(Double.valueOf(f.getRateDebitStr()));
            strQuery += " and model.rateDebit = ? ";
        }


        strQuery += "order by nlssort(name,'nls_sort=Vietnamese') asc";
        Query q = getSession().createQuery(strQuery);
        for (int i = 0; i < listParameter.size(); i++) {
            q.setParameter(i, listParameter.get(i));
        }

        List listChannelType = new ArrayList();
        listChannelType = q.list();

        req.getSession().setAttribute("toEditChannelType", 0);

        req.getSession().removeAttribute("listChannelType");
        req.getSession().setAttribute("listChannelType", listChannelType);

        req.setAttribute("returnMsg", "channelType.search");
        List paramMsg = new ArrayList();
        paramMsg.add(listChannelType.size());
        req.setAttribute("paramMsg", paramMsg);
        pageForward = PRE_STAFF_STOCK_TYPE_LIMIT;
        log.info("End method searchChannelType of StaffStockTypeLimitDAO");
        return pageForward;
    }
}
