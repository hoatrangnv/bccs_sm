/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.common.util.StringUtils;
import com.viettel.im.client.bean.DebitRequestBean;
import com.viettel.im.client.bean.DebitRequestDetailBean;
import com.viettel.im.client.form.SearchDebitRequestForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.UserToken;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author ThinhPH2
 */
public class SearchDateOfPaymentDAO extends BaseDAO {

    SearchDebitRequestForm searchDebitRequestForm = new SearchDebitRequestForm();
    String PREPARE_PAGE = "preparePage";
    String SEARCH_DEBIT_REQUEST = "listDateOfPaymentRequest";
    String SEARCH_DEBIT_REQUEST_DETAIL = "listDateOfPaymentRequestDetail";
    private String filename;
    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public SearchDebitRequestForm getSearchDebitRequestForm() {
        return searchDebitRequestForm;
    }

    public void setSearchDebitRequestForm(SearchDebitRequestForm searchDebitRequestForm) {
        this.searchDebitRequestForm = searchDebitRequestForm;
    }

    public String preparePage() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String preparePage = PREPARE_PAGE;
        searchDebitRequestForm.resetForm();
        req.getSession().setAttribute("listDateOfPaymentSearch", this.getListDateOfPayment(getSession(), userToken.getShopId()));
        this.resetListDetail(req);
        return preparePage;
    }

    public List<DebitRequestBean> getListDateOfPayment(Session session, Long shopId) {
        List<DebitRequestBean> lstDebitRequestBean = new ArrayList<DebitRequestBean>();
        String sql = "select dr.request_id requestId,"
                + " dr.request_code requestCode,"
                + " dr.create_date createDate,"
                + " dr.create_user createUser,"
                + " dr.file_name clientFileName,"
                + " dr.file_content fileContent,"
                + " dr.descripttion descripttion,"
                + " dr.status status,"
                + " dr.last_update_date lastUpdateDate,"
                + " dr.last_update_user lastUpdateUser "
                + " from debit_request dr "
                + " WHERE   (SELECT   shop_id FROM   staff WHERE   LOWER (staff_code) = LOWER (dr.create_user)) IN "
                + " (SELECT   shop_id FROM   tbl_shop_tree  WHERE   root_id = ?)  "
                + " order by request_id desc";
        try {
            Query query = session.createSQLQuery(sql).addScalar("requestId", Hibernate.LONG).addScalar("requestCode", Hibernate.STRING).addScalar("createDate", Hibernate.DATE).addScalar("createUser", Hibernate.STRING).addScalar("clientFileName", Hibernate.STRING).addScalar("fileContent", Hibernate.BINARY).addScalar("descripttion", Hibernate.STRING).addScalar("status", Hibernate.LONG).addScalar("lastUpdateDate", Hibernate.DATE).addScalar("lastUpdateUser", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DebitRequestBean.class));
            query.setParameter(0, shopId == null ? "" : shopId);
            lstDebitRequestBean = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstDebitRequestBean;
    }

    public List<DebitRequestBean> search(Session session, SearchDebitRequestForm inputForm, Long shopId) {
        List<DebitRequestBean> listDateOfPaymentSearch = new ArrayList<DebitRequestBean>();
        List param = new ArrayList();
        try {
            String sql = "select dr.request_id requestId,"
                    + "dr.request_code requestCode,"
                    + "dr.create_date createDate,"
                    + "dr.create_user createUser,"
                    + "dr.file_name clientFileName,"
                    + "dr.file_content fileContent,"
                    + "dr.descripttion descripttion,"
                    + "dr.status status,"
                    + "dr.last_update_date lastUpdateDate,"
                    + "dr.last_update_user lastUpdateUser "
                    + "from debit_request dr"
                    + " WHERE   (SELECT   shop_id FROM   staff WHERE   LOWER (staff_code) = LOWER (dr.create_user)) IN "
                    + " (SELECT   shop_id FROM   tbl_shop_tree  WHERE   root_id = ?)  ";
            param.add(shopId);
            if (inputForm.getDebitRequestId() != null) {//lay 1 ban ghi theo id
                sql += " and dr.request_id = ? ";
                param.add(inputForm.getDebitRequestId());
            } else {
                if (StringUtils.validString(inputForm.getUserCreate())) {
                    sql += " and upper(dr.create_user) like upper(?) ";
                    param.add("%" + inputForm.getUserCreate().trim() + "%");
                }
                if (StringUtils.validString(inputForm.getRequestCode())) {
                    sql += " and upper(dr.request_code) like upper(?) ";
                    param.add("%" + inputForm.getRequestCode().trim() + "%");
                }
                if (inputForm.getFromDate() != null) {
                    sql += " and dr.create_date >= to_date(?,'dd/MM/yyyy')";
                    param.add(DateTimeUtils.convertDateTimeToString(inputForm.getFromDate(), "dd/MM/yyyy"));
                }
                if (inputForm.getToDate() != null) {
                    sql += " and dr.create_date < to_date(?,'dd/MM/yyyy') + 1 ";
                    param.add(DateTimeUtils.convertDateTimeToString(inputForm.getToDate(), "dd/MM/yyyy"));
                }
                if (StringUtils.validString(inputForm.getStatus())) {
                    sql += " and dr.status = ? ";
                    param.add(inputForm.getStatus().trim());
                }
                if (StringUtils.validString(inputForm.getRequestObjectType())) {
                    if (inputForm.getRequestObjectType().equals("1")) {
                        sql += " and dr.request_object_type is not null ";
                    }
                    if (inputForm.getRequestObjectType().equals("2")) {
                        sql += " and dr.request_object_type is null ";
                    }
                }
                sql += " order by dr.request_id desc ";
            }
            Query query = session.createSQLQuery(sql).addScalar("requestId", Hibernate.LONG).addScalar("requestCode", Hibernate.STRING).addScalar("createDate", Hibernate.DATE).addScalar("createUser", Hibernate.STRING).addScalar("clientFileName", Hibernate.STRING).addScalar("fileContent", Hibernate.BINARY).addScalar("descripttion", Hibernate.STRING).addScalar("status", Hibernate.LONG).addScalar("lastUpdateDate", Hibernate.DATE).addScalar("lastUpdateUser", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DebitRequestBean.class));
            for (int i = 0; i < param.size(); i++) {
                query.setParameter(i, param.get(i));
            }
            listDateOfPaymentSearch = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDateOfPaymentSearch;
    }

    public String searchDebitRequest() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (checkValidate()) {
            List<DebitRequestBean> listDebitRequestBean = new ArrayList<DebitRequestBean>();
            listDebitRequestBean = this.search(getSession(), searchDebitRequestForm, userToken.getShopId());
            this.resetListDetail(req);
            req.getSession().setAttribute("listDateOfPaymentSearch", listDebitRequestBean);
            req.getSession().setAttribute("returnMsg", "Tìm thấy " + listDebitRequestBean.size() + " bản ghi");
        }
        return PREPARE_PAGE;
    }

    public void resetListDetail(HttpServletRequest req) {
        List<DebitRequestDetailBean> listDebitRequestBeanDetail = new ArrayList<DebitRequestDetailBean>();
        req.getSession().setAttribute("listDateOfPaymentDetailSearch", listDebitRequestBeanDetail);
    }

    public String downloadFile() {
        UserToken userToken = (UserToken) getRequest().getSession().getAttribute("userToken");
        try {
            List<DebitRequestBean> listItems = search(getSession(), searchDebitRequestForm, userToken.getShopId());
            if (!listItems.isEmpty()) {
                DebitRequestBean item = listItems.get(0);
                filename = item.getClientFileName();
                inputStream = new ByteArrayInputStream(item.getFileContent());
            } else {
                return "fileNotExists";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "fileNotExists";
        }
        return "download";
    }

    public String delRequetstDebit() {
        HttpServletRequest req = getRequest();
        Session session = getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String requestId = req.getParameter("requestId");
        try {
            String sql = "update debit_request set status = 0,last_update_date = ?, last_update_user = ?   where request_id = ?";
            String sqlUpdateDetail = " update debit_request_detail set status = 0 where request_id = ? and status = 1 ";
            Query query = session.createSQLQuery(sql);
            query.setParameter(0, getSysdate());
            query.setParameter(1, userToken.getLoginName());
            query.setParameter(2, requestId);
            int xoa = query.executeUpdate();
            if (xoa > 0) {
                Query query1 = getSession().createSQLQuery(sqlUpdateDetail);
                query1.setParameter(0, requestId);
                query1.executeUpdate();

                req.setAttribute("returnMsg", "msg.693.11");
                req.getSession().setAttribute("listDateOfPaymentSearch", this.getListDateOfPayment(getSession(), userToken.getShopId()));
                List<DebitRequestDetailBean> listDebitRequestBeanDetail = new ArrayList<DebitRequestDetailBean>();
                req.getSession().setAttribute("listDateOfPaymentDetailSearch", listDebitRequestBeanDetail);
            } else {
                req.setAttribute("returnMsg", "msg.693.12");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SEARCH_DEBIT_REQUEST;
    }

    private List<DebitRequestDetailBean> getListRequestDetail(Session session, Long requestId) {
        List<DebitRequestDetailBean> listRequestDetail = new ArrayList<DebitRequestDetailBean>();
        StringBuilder sql = new StringBuilder();
        sql.append("   SELECT   (SELECT   name ");
        sql.append("               FROM   app_params ");
        sql.append("              WHERE   code = a.debit_type AND TYPE = 'DEBIT_TYPE' AND status = 1) ");
        sql.append("                debitType, ");
        sql.append("            TO_CHAR(NVL ( ");
        sql.append("                        (SELECT   dt.max_debit ");
        sql.append("                           FROM   debit_type dt ");
        sql.append("                          WHERE       dt.debit_type = a.debit_type ");
        sql.append("                                  AND dt.debit_day_type = a.debit_day_type ");
        sql.append("                                  AND dt.status = 1), ");
        sql.append("                        0)) ");
        sql.append("                debitTypeOld, ");
        sql.append("            (SELECT   staff_code ");
        sql.append("               FROM   staff ");
        sql.append("              WHERE   staff_id = a.owner_id) ");
        sql.append("                staffCode, ");
        sql.append("            (SELECT   shop_code ");
        sql.append("               FROM   shop ");
        sql.append("              WHERE   shop_id = (SELECT   shop_id ");
        sql.append("                                   FROM   staff ");
        sql.append("                                  WHERE   staff_id = a.owner_id)) ");
        sql.append("                shopCode, ");
        sql.append("            NVL ( ");
        sql.append("                (SELECT   d.name ");
        sql.append("                   FROM   stock_debit f, app_params d ");
        sql.append("                  WHERE       f.owner_id = a.owner_id ");
        sql.append("                          AND d.status = 1 ");
        sql.append("                          AND d.TYPE = 'DEBIT_DAY_TYPE' ");
        sql.append("                          AND d.code = f.debit_day_type ");
        sql.append("                          AND f.debit_day_type = a.debit_day_type ");
        sql.append("                          AND ( (    f.owner_type = 1 ");
        sql.append("                                 AND f.stock_type_id = a.stock_type_id ");
        sql.append("                                 AND f.request_debit_type = a.request_debit_type) ");
        sql.append("                               OR f.owner_type = 2)), ");
        sql.append("                '-') ");
        sql.append("                debitDayTypeOld, ");
        sql.append("            (SELECT   name ");
        sql.append("               FROM   app_params ");
        sql.append("              WHERE       code = a.debit_day_type ");
        sql.append("                      AND TYPE = 'DEBIT_DAY_TYPE' ");
        sql.append("                      AND status = 1) ");
        sql.append("                debitDayType, ");
        sql.append("            a.status, ");
        sql.append("            a.note, ");
        sql.append("            a.owner_type ownerType, ");
        sql.append("            a.debit_type debitTypeShopNew, ");
        sql.append("            NVL ( ");
        sql.append("                (SELECT   c.debit_type ");
        sql.append("                   FROM   stock_debit c ");
        sql.append("                  WHERE       c.owner_id = a.owner_id ");
        sql.append("                          AND c.debit_day_type = a.debit_day_type ");
        sql.append("                          AND c.stock_type_id = a.stock_type_id ");
        sql.append("                          AND c.request_debit_type = a.request_debit_type), ");
        sql.append("                '--') ");
        sql.append("                debitTypeShopOld, ");
        sql.append("            NVL ( ");
        sql.append("                (SELECT   c.request_debit_type ");
        sql.append("                   FROM   stock_debit c ");
        sql.append("                  WHERE       c.owner_id = a.owner_id ");
        sql.append("                          AND c.debit_day_type = a.debit_day_type ");
        sql.append("                          AND c.stock_type_id = a.stock_type_id ");
        sql.append("                          AND c.request_debit_type = a.request_debit_type), ");
        sql.append("                3) ");
        sql.append("                requestDebitTypeOld, ");
        sql.append("            (SELECT   shop_code ");
        sql.append("               FROM   shop ");
        sql.append("              WHERE   shop_id = a.owner_id AND ROWNUM < 2) ");
        sql.append("                shopCodeDebit, ");
        sql.append("            (SELECT   name ");
        sql.append("               FROM   stock_type ");
        sql.append("              WHERE   stock_type_id = a.stock_type_id) ");
        sql.append("                stockTypeName, ");
        sql.append("            a.request_debit_type requestDebitType ");
        sql.append("     FROM   debit_request_detail a, debit_request k ");
        sql.append("    WHERE   k.request_id = ? AND a.request_id = k.request_id ");
        sql.append(" ORDER BY   k.request_id DESC ");



        try {
            Query query = session.createSQLQuery(sql.toString())
                    .addScalar("shopCode", Hibernate.STRING)
                    .addScalar("staffCode", Hibernate.STRING)
                    .addScalar("debitTypeOld", Hibernate.STRING)
                    .addScalar("debitType", Hibernate.STRING)
                    .addScalar("requestDebitTypeOld", Hibernate.LONG)
                    .addScalar("requestDebitType", Hibernate.LONG)
                    .addScalar("debitDayTypeOld", Hibernate.STRING)
                    .addScalar("debitDayType", Hibernate.STRING)
                    .addScalar("status", Hibernate.LONG)
                    .addScalar("note", Hibernate.STRING)
                    .addScalar("ownerType", Hibernate.LONG)
                    .addScalar("debitTypeShopNew", Hibernate.STRING)
                    .addScalar("debitTypeShopOld", Hibernate.STRING)
                    .addScalar("shopCodeDebit", Hibernate.STRING)
                    .addScalar("stockTypeName", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(DebitRequestDetailBean.class));
            query.setLong(0, requestId);
            listRequestDetail = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRequestDetail;
    }

    public String viewDetail() {
        HttpServletRequest req = getRequest();
        String requestId = (String) req.getParameter("requestId");
        try {
            req.getSession().setAttribute("listDateOfPaymentDetailSearch", this.getListRequestDetail(getSession(), Long.parseLong(requestId)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SEARCH_DEBIT_REQUEST_DETAIL;
    }

    public String pageNavigator() {
        return PREPARE_PAGE;
    }

    public String pageNavigatorDetail() {
        return PREPARE_PAGE;
    }

    public boolean checkValidate() {
        HttpServletRequest req = getRequest();
        Date fromDate = searchDebitRequestForm.getFromDate();
        Date toDate = searchDebitRequestForm.getToDate();
        if (DateTimeUtils.distanceBeetwen2Date(fromDate, toDate) > 30) {
            req.setAttribute("returnMsg", "err.tu_ngay_den_ngay_vuot_qua_30day");
            return false;
        }
        return true;
    }
}
