package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.LookupSerialHistoryBean;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.LookupSerialHistoryForm;
import java.util.List;
import com.viettel.im.database.BO.StockType;
import java.util.ArrayList;
import org.hibernate.Session;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.UserToken;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author tamdt1
 * xu ly cac tac vu lien quan den viec tim kiem lich su serial
 *
 */
public class LookupSerialHistoryDAO extends BaseDAOAction {
    private Log log = LogFactory.getLog(LookupSerialHistoryDAO.class);

    //dinh nghia cac hang so pageForward
    private String pageForward;
    private final String LOOKUP_SERIAL_HISTORY = "lookupSerialHistory";
    private final String LIST_LOOKUP_SERIAL_HISTORY = "listLookupSerialHistory";
    private final String CHANGE_STOCK_TYPE = "changeStockType";

    //dinh nghia ten cac bien session hoac bien request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_STOCK_TYPE = "listStockType";
    private final String REQUEST_LIST_STOCK_MODEL = "listStockModel";
    private final String REQUEST_LIST_LOOKUP_SERIAL_HISTORY_BEAN = "listLookupSerialHistoryBean";

    //
    private final Long MAX_RESULT = 1000L;

    //khai bao bien form
    LookupSerialHistoryForm lookupSerialHistoryForm = new LookupSerialHistoryForm();

    public LookupSerialHistoryForm getLookupSerialHistoryForm() {
        return lookupSerialHistoryForm;
    }

    public void setLookupSerialHistoryForm(LookupSerialHistoryForm lookupSerialHistoryForm) {
        this.lookupSerialHistoryForm = lookupSerialHistoryForm;
    }

    /**
     *
     * author tamdt1
     * date: 20/01/2010
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of LookupSerialHistoryDAO...");

        this.lookupSerialHistoryForm.resetForm();

        //lay du lieu cho combobox
        getDataForCombobox();

        pageForward = LOOKUP_SERIAL_HISTORY;
        log.info("End method preparePage of LookupSerialHistoryDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 20/01/2010
     * tim kiem serial dua tren cac dieu kien tim kiem
     *
     */
    public String lookupSerialHistory() throws Exception {
        log.info("Begin method lookupSerialHistory of LookupSerialHistoryDAO...");

        HttpServletRequest req = getRequest();
        
        Long stockTypeId = this.lookupSerialHistoryForm.getStockTypeId();
        Long stockModelId = this.lookupSerialHistoryForm.getStockModelId();
        String serial = this.lookupSerialHistoryForm.getSerial();
        Date fromDate = this.lookupSerialHistoryForm.getFromDate();
        Date toDate = this.lookupSerialHistoryForm.getToDate();

        Date afterToDateOneDay = new Date(toDate.getTime() + 24*60*60);
        List listParameter = new ArrayList();

        /*
        lay lich su serial bao gom cac phan, co the bao gom 2 loai
            1> giao dich ban hang. Bao gom 2 loai:
                1.1> giao dich ban hang cua nhan vien
                1.2> giao dich ban hang cua cua hang
            2> giao dich kho
         */

        //cac giao dich ban hang cua nhan vien
        StringBuffer strSaleTransByStaff = new StringBuffer("");
        strSaleTransByStaff.append(""
                + "SELECT a.sale_trans_id stock_trans_id, 8 stock_trans_type, a.sale_trans_date stock_trans_date, "
                + "       TO_NUMBER (a.status) stock_trans_status, a.staff_id from_owner_id, "
                + "       2 from_owner_type, b.staff_code from_owner_code, b.NAME from_owner_name, "
                + "       NULL to_owner_id, NULL to_owner_type, NULL to_owner_code, "
                + "       NULL to_owner_name, d.reason_code, d.reason_name ");
        strSaleTransByStaff.append(""
                + "FROM  sale_trans a, staff b, reason d ");
        strSaleTransByStaff.append(""
                + "WHERE 1 = 1 "
                + "       AND a.staff_id = b.staff_id "
                + "       AND a.reason_id = d.reason_id "
                + "       AND a.sale_trans_id IN ("
                + "             SELECT sale_trans_id "
                + "             FROM sale_trans_detail "
                + "             WHERE   1 = 1 " +
                "                       AND sale_trans_detail_id IN ("
                + "                         SELECT sale_trans_detail_id "
                + "                         FROM sale_trans_serial "
                + "                         WHERE   from_serial <= :serial "
                + "                             AND to_serial >= :serial "
                + "                             AND stock_model_id = :stockModelId "
                + "                             AND sale_trans_date >= :fromDate "
                + "                             AND sale_trans_date < :afterToDateOneDay "
                + "                     ) "
                + "             AND sale_trans_date >= :fromDate "
                + "             AND sale_trans_date < :afterToDateOneDay "
                + "       ) "
                + "       AND a.sale_trans_date >= :fromDate "
                + "       AND a.sale_trans_date < :afterToDateOneDay "
                + "       AND a.staff_id IS NOT NULL");


        //
        StringBuffer strQuery = new StringBuffer("");
        strQuery.append(strSaleTransByStaff);

        Query query = getSession().createSQLQuery(strQuery.toString()).addScalar("stock_trans_id", Hibernate.LONG).addScalar("saleTransTypeName", Hibernate.STRING).addScalar("stockTypeName", Hibernate.STRING).addScalar("stockModelCode", Hibernate.STRING).addScalar("stockModelName", Hibernate.STRING).addScalar("quantity", Hibernate.LONG).addScalar("price", Hibernate.LONG).addScalar("amount", Hibernate.DOUBLE).addScalar("discountAmount", Hibernate.LONG).addScalar("vatAmount", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(LookupSerialHistoryBean.class));

//        for (int i = 0; i < listParameter.size(); i++) {
//            query.setParameter(i, listParameter.get(i));
//        }

        query.setParameter("serial", serial);
        query.setParameter("stockModelId", stockModelId);
        query.setParameter("fromDate", fromDate);
        query.setParameter("afterToDateOneDay", afterToDateOneDay);

        List<LookupSerialHistoryBean> listLookupSerialHistoryBean = query.list();

        //lay du lieu cho cac combobox
        getDataForCombobox();

        pageForward = LOOKUP_SERIAL_HISTORY;
        log.info("End method lookupSerialHistory of LookupSerialHistoryDAO");
        return pageForward;


    }

    private Map listItem = new HashMap();

    public Map getListItem() {
        return listItem;
    }

    public void setListItem(Map listItem) {
        this.listItem = listItem;
    }
    
    /**
     *
     * author tamdt1
     * date: 21/06/2009
     * thay doi bien currentOwnerType khi ownerType thay doi
     *
     */
    public String changeStockType() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String strStockTypeId = req.getParameter("stockTypeId");
            String target = req.getParameter("target");

            if(strStockTypeId != null && !strStockTypeId.trim().equals("")) {
                Long stockTypeId = Long.valueOf(strStockTypeId);
                String queryString = "select stockModelId, name " +
                        "from StockModel as model " +
                        "where model.stockTypeId = ? and status = ? " +
                        "order by nlssort(name,'nls_sort=Vietnamese') asc";
                Session session = getSession();
                Query queryObject = session.createQuery(queryString);
                queryObject.setParameter(0, stockTypeId);
                queryObject.setParameter(1, Constant.STATUS_USE);
                List tmpList = queryObject.list();
                String[] tmpHeader = {"", getText("MSG.GOD.217")};
                List list = new ArrayList();
                list.add(tmpHeader);
                list.addAll(tmpList);
                this.listItem.put(target, list);

            } else {
                String[] tmpHeader = {"", getText("MSG.GOD.217")};
                List list = new ArrayList();
                list.add(tmpHeader);
                this.listItem.put(target, list);

            }

        } catch (Exception ex) {
            throw ex;
        }
        return CHANGE_STOCK_TYPE;
    }

    /**
     *
     * author tamdt1
     * date: 18/07/2009
     * lay du lieu cho cac combobox
     *
     */
    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();

        //lay danh sach cac stockType
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List<StockType> listStockType = stockTypeDAO.getListForLookupSerial();
        req.setAttribute(REQUEST_LIST_STOCK_TYPE, listStockType);

        //lay danh sach cac mat hang (mac dinh stockType la bo kit)
        Long stockTypeId = this.lookupSerialHistoryForm.getStockTypeId();
        if(stockTypeId == null) {
            //neu == nul lay mac dinh la stockKit
            stockTypeId = Constant.STOCK_KIT;
            this.lookupSerialHistoryForm.setStockTypeId(stockTypeId);
        }
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        List<StockModel> listStockModel = stockModelDAO.findByPropertyWithStatus(
                StockModelDAO.STOCK_TYPE_ID, Constant.STOCK_KIT, Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_STOCK_MODEL, listStockModel);
    }

     /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String strOwnerType = imSearchBean.getOtherParamValue();
        Long ownerType = -1L;
        try {
            ownerType = Long.valueOf(strOwnerType);
        } catch (NumberFormatException ex) {
            ownerType = -1L;
        }

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (ownerType.equals(Constant.OWNER_TYPE_SHOP)) {
            //neu ownerType la shop lay danh sach cua hang hien tai + cua hang cap duoi
            List listParameter1 = new ArrayList();
            StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
            strQuery1.append("from Shop a ");
            strQuery1.append("where 1 = 1 ");

            strQuery1.append("and a.status = ? ");
            listParameter1.add(Constant.STATUS_ACTIVE);

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
            listParameter1.add(100L);

            strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

            Query query1 = getSession().createQuery(strQuery1.toString());
            for (int i = 0; i < listParameter1.size(); i++) {
                query1.setParameter(i, listParameter1.get(i));
            }

            listImSearchBean = query1.list();

        } else if (ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
            //neu ownerType la staff lay danh sach nhan vien cua hang hien tai + cua hang cap duoi
            List listParameter1 = new ArrayList();
            StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
            strQuery1.append("from Staff a ");
            strQuery1.append("where 1 = 1 ");

            strQuery1.append("and a.status = ? ");
            listParameter1.add(Constant.STATUS_ACTIVE);

            strQuery1.append("and a.shopId in (select shopId from Shop where shopPath like ? or shopId = ?) ");
            listParameter1.add("%_" + userToken.getShopId() + "_%");
            listParameter1.add(userToken.getShopId());

            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery1.append("and lower(a.staffCode) like ? ");
                listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
            }

            if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                strQuery1.append("and lower(a.name) like ? ");
                listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
            }

            strQuery1.append("and rownum < ? ");
            listParameter1.add(100L);

            strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

            Query query1 = getSession().createQuery(strQuery1.toString());
            for (int i = 0; i < listParameter1.size(); i++) {
                query1.setParameter(i, listParameter1.get(i));
            }

            listImSearchBean = query1.list();
        }

        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getShopName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String strOwnerType = imSearchBean.getOtherParamValue();
        Long ownerType = -1L;
        try {
            ownerType = Long.valueOf(strOwnerType);
        } catch (NumberFormatException ex) {
            ownerType = -1L;
        }

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (ownerType.equals(Constant.OWNER_TYPE_SHOP)) {
            //lay ten cua cua hang
            List listParameter1 = new ArrayList();
            StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
            strQuery1.append("from Shop a ");
            strQuery1.append("where 1 = 1 ");

            strQuery1.append("and a.status = ? ");
            listParameter1.add(Constant.STATUS_ACTIVE);

            strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
            listParameter1.add("%_" + userToken.getShopId() + "_%");
            listParameter1.add(userToken.getShopId());

            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery1.append("and lower(a.shopCode) = ? ");
                listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
            } else {
                return listImSearchBean;
            }

            strQuery1.append("and rownum < ? ");
            listParameter1.add(2L);

            Query query1 = getSession().createQuery(strQuery1.toString());
            for (int i = 0; i < listParameter1.size(); i++) {
                query1.setParameter(i, listParameter1.get(i));
            }

            listImSearchBean = query1.list();

        } else if (ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
            //lay ten staff
            List listParameter1 = new ArrayList();
            StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
            strQuery1.append("from Staff a ");
            strQuery1.append("where 1 = 1 ");

            strQuery1.append("and a.status = ? ");
            listParameter1.add(Constant.STATUS_ACTIVE);

            strQuery1.append("and a.shopId in (select shopId from Shop where shopPath like ? or shopId = ?) ");
            listParameter1.add("%_" + userToken.getShopId() + "_%");
            listParameter1.add(userToken.getShopId());

            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery1.append("and lower(a.staffCode) = ? ");
                listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
            } else {
                return listImSearchBean;
            }

            strQuery1.append("and rownum < ? ");
            listParameter1.add(2L);

            Query query1 = getSession().createQuery(strQuery1.toString());
            for (int i = 0; i < listParameter1.size(); i++) {
                query1.setParameter(i, listParameter1.get(i));
            }

            listImSearchBean = query1.list();

        }

        return listImSearchBean;
    }

}