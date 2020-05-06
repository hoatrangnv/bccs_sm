/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.client.bean.DebitRequestBean;
import com.viettel.im.client.bean.DebitRequestDetailBean;
import com.viettel.im.client.form.SearchDebitRequestForm;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.AjustLimitBO;
import com.viettel.im.database.BO.UserToken;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
public class ApprovalDateOfPaymentDAO extends BaseDAO {

    SearchDebitRequestForm searchDebitRequestForm = new SearchDebitRequestForm();
    String PREPARE_PAGE = "preparePage";
    String SEARCH_DEBIT_REQUEST = "listAppDOPRequest";
    String SEARCH_DEBIT_REQUEST_DETAIL = "listAppDOPRequestDetail";
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
        req.getSession().setAttribute("listDebitRequest", this.getListDebitRequest(getSession(), userToken.getShopId()));
        this.resetListDetail(req);
        return preparePage;
    }

    public List<DebitRequestBean> getListDebitRequest(Session session, Long shopId) {
        List<DebitRequestBean> lstDebitRequestBean = new ArrayList<DebitRequestBean>();
        if (shopId == null) {
            return lstDebitRequestBean;
        }
        String sql = "select "
                + " a.request_id requestId,"
                + " a.request_code requestCode, "
                + " a.create_date createDate,"
                + " a.create_user createUser,"
                + " a.file_name clientFileName, "
                //+ " a.file_content fileContent,"
                + " a.descripttion descripttion, "
                + " a.status, "
                + " a.last_update_date lastUpdateDate, "
                + " a.last_update_user lastUpdateUser  "
                + " from debit_request a where nvl(request_object_type,0) <> 1 "
                + " and (SELECT   shop_id FROM   staff WHERE   LOWER (staff_code) = LOWER (a.create_user)) IN "
                + " (SELECT   shop_id FROM   tbl_shop_tree  WHERE   root_id = ?)  "
                + " order by request_id desc";
        try {
            Query query = session.createSQLQuery(sql).addScalar("requestId", Hibernate.LONG).addScalar("requestCode", Hibernate.STRING).addScalar("createDate", Hibernate.DATE).addScalar("createUser", Hibernate.STRING).addScalar("clientFileName", Hibernate.STRING).addScalar("descripttion", Hibernate.STRING).addScalar("status", Hibernate.LONG).addScalar("lastUpdateDate", Hibernate.DATE).addScalar("lastUpdateUser", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DebitRequestBean.class));
            query.setParameter(0, shopId);
            lstDebitRequestBean = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstDebitRequestBean;
    }

    public List<DebitRequestBean> search(Session session, SearchDebitRequestForm inputForm, Long shopId) {
        List<DebitRequestBean> listDebitRequest = new ArrayList<DebitRequestBean>();
        List param = new ArrayList();
        try {
            String sql = "select a.request_id requestId,"
                    + " a.request_code requestCode,"
                    + " a.create_date createDate,"
                    + " a.create_user createUser,"
                    + " a.file_name clientFileName,"
                    + " a.file_content fileContent,"
                    + " a.descripttion descripttion,"
                    + " a.status, "
                    + " a.last_update_date lastUpdateDate,"
                    + " a.last_update_user lastUpdateUser "
                    + " from debit_request a "
                    + " where nvl(request_object_type,0) <> 1  "
                    + " and (SELECT   shop_id FROM   staff WHERE   LOWER (staff_code) = LOWER (a.create_user)) IN "
                    + " (SELECT   shop_id FROM   tbl_shop_tree  WHERE   root_id = ?)  ";
            param.add(shopId);
            if (inputForm.getDebitRequestId() != null) {//lay 1 ban ghi theo id
                sql += " and a.request_id = ? ";
                param.add(inputForm.getDebitRequestId());
            } else {
                if (StringUtils.validString(inputForm.getUserCreate())) {
                    sql += " and upper(a.create_user) like upper(?) ";
                    param.add("%" + inputForm.getUserCreate().trim() + "%");
                }
                if (StringUtils.validString(inputForm.getRequestCode())) {
                    sql += " and upper(a.request_code) like upper(?) ";
                    param.add("%" + inputForm.getRequestCode().trim() + "%");
                }
                if (inputForm.getFromDate() != null) {
                    sql += " and a.create_date >= to_date(?,'dd/MM/yyyy') ";
                    param.add(DateTimeUtils.convertDateTimeToString(inputForm.getFromDate(), "dd/MM/yyyy"));
                }
                if (inputForm.getToDate() != null) {
                    sql += " and a.create_date < to_date(?,'dd/MM/yyyy') + 1 ";
                    param.add(DateTimeUtils.convertDateTimeToString(inputForm.getToDate(), "dd/MM/yyyy"));
                }
                if (StringUtils.validString(inputForm.getStatus())) {
                    sql += " and a.status = ? ";
                    param.add(inputForm.getStatus());
                }
                sql += " order by a.request_id desc ";
            }
            Query query = session.createSQLQuery(sql).addScalar("requestId", Hibernate.LONG).addScalar("requestCode", Hibernate.STRING).addScalar("createDate", Hibernate.DATE).addScalar("createUser", Hibernate.STRING).addScalar("clientFileName", Hibernate.STRING).addScalar("fileContent", Hibernate.BINARY).addScalar("descripttion", Hibernate.STRING).addScalar("status", Hibernate.LONG).addScalar("lastUpdateDate", Hibernate.DATE).addScalar("lastUpdateUser", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DebitRequestBean.class));
            for (int i = 0; i < param.size(); i++) {
                query.setParameter(i, param.get(i));
            }
            listDebitRequest = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDebitRequest;
    }

    public String searchDebitRequest() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (checkValidate()) {
            List<DebitRequestBean> listDebitRequestBean = new ArrayList<DebitRequestBean>();
            listDebitRequestBean = this.search(getSession(), searchDebitRequestForm, userToken.getShopId());
            req.getSession().setAttribute("listDebitRequest", listDebitRequestBean);
            req.getSession().setAttribute("returnMsg", "Tìm thấy " + listDebitRequestBean.size() + " bản ghi");
            this.resetListDetail(req);
        }
        return PREPARE_PAGE;
    }

    public void resetListDetail(HttpServletRequest req) {
        List<DebitRequestDetailBean> listDebitRequestBeanDetail = new ArrayList<DebitRequestDetailBean>();
        req.getSession().setAttribute("listDebitRequestDetail", listDebitRequestBeanDetail);
    }

    public String downloadFile() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
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

    private List<DebitRequestDetailBean> getListRequestDetail(Session session, Long requestId) {
        List<DebitRequestDetailBean> listDebitRequestDetail = new ArrayList<DebitRequestDetailBean>();
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT   (SELECT   shop_code ");
        sql.append("               FROM   shop ");
        sql.append("              WHERE   shop_id = (SELECT   shop_id ");
        sql.append("                                   FROM   staff ");
        sql.append("                                  WHERE   staff_id = a.owner_id)) ");
        sql.append("                shopCode, ");
        sql.append("            (SELECT   staff_code ");
        sql.append("               FROM   staff ");
        sql.append("              WHERE   staff_id = a.owner_id) ");
        sql.append("                staffCode, ");
        sql.append("            TO_CHAR(NVL ( ");
        sql.append("                        (SELECT   dt.max_debit ");
        sql.append("                           FROM   debit_type dt ");
        sql.append("                          WHERE       dt.debit_type = a.debit_type ");
        sql.append("                                  AND dt.debit_day_type = a.debit_day_type ");
        sql.append("                                  AND dt.status = 1), ");
        sql.append("                        0)) ");
        sql.append("                debitTypeOld, ");
        
        sql.append("            TO_CHAR(NVL ( ");
        sql.append("                        (SELECT   dt.max_debit ");
        sql.append("                           FROM   stock_debit c, debit_type dt ");
        sql.append("                          WHERE       c.debit_type = dt.debit_type ");
        sql.append("                                  AND c.debit_day_type = dt.debit_day_type ");
        sql.append("                                  AND dt.status = 1 ");
        sql.append("                                  AND c.owner_id = a.owner_id ");
        sql.append("                                  AND c.debit_day_type = a.debit_day_type), ");
        sql.append("                        0)) ");
        sql.append("                debitTypeOld1, ");
        
        sql.append("            NVL ( ");
        sql.append("                (SELECT   d.name ");
        sql.append("                   FROM   stock_debit f, app_params d ");
        sql.append("                  WHERE       f.owner_id = a.owner_id ");
        sql.append("                          AND d.status = 1 ");
        sql.append("                          AND d.TYPE = 'DEBIT_DAY_TYPE' ");
        sql.append("                          AND d.code = f.debit_day_type ");
        sql.append("                          AND f.debit_day_type = a.debit_day_type), ");
        sql.append("                '-') ");
        sql.append("                debitDayTypeOld, ");
        sql.append("            (SELECT   name ");
        sql.append("               FROM   app_params ");
        sql.append("              WHERE   code = a.debit_type AND TYPE = 'DEBIT_TYPE' AND status = 1) ");
        sql.append("                debitType, ");
        sql.append("            (SELECT   name ");
        sql.append("               FROM   app_params ");
        sql.append("              WHERE       code = a.debit_day_type ");
        sql.append("                      AND TYPE = 'DEBIT_DAY_TYPE' ");
        sql.append("                      AND status = 1) ");
        sql.append("                debitDayType, ");
        sql.append("            a.status status, ");
        sql.append("            a.note note, ");
        sql.append("            a.request_detail_id requestDetailId, ");
        sql.append("            a.request_id requestId ");
        sql.append("     FROM   debit_request_detail a, debit_request k ");
        sql.append("    WHERE       NVL (k.request_object_type, 0) <> 1 ");
        sql.append("            AND k.request_id = ? ");
        sql.append("            AND a.request_id = k.request_id ");
        sql.append(" ORDER BY   k.request_id DESC ");

        try {
            Query query = session.createSQLQuery(sql.toString())
                    .addScalar("shopCode", Hibernate.STRING)
                    .addScalar("staffCode", Hibernate.STRING)
                    .addScalar("debitTypeOld", Hibernate.STRING)
                    .addScalar("debitTypeOld1", Hibernate.STRING)
                    .addScalar("debitType", Hibernate.STRING).addScalar("debitDayTypeOld", Hibernate.STRING).addScalar("debitDayType", Hibernate.STRING).addScalar("status", Hibernate.LONG).addScalar("note", Hibernate.STRING).addScalar("requestDetailId", Hibernate.LONG).addScalar("requestId", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(DebitRequestDetailBean.class));
            query.setParameter(0, requestId);
            listDebitRequestDetail = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDebitRequestDetail;
    }

    private DebitRequestDetailBean getRequestDetail(Session session, Long requestDetailId) {
        DebitRequestDetailBean debitRequest = new DebitRequestDetailBean();
        StringBuilder sql = new StringBuilder();
        sql.append(" select (select shop_code from shop where shop_id = (select shop_id from staff where staff_id = a.owner_id)) shopCode,  ");
        sql.append(" (select staff_code from staff where staff_id = a.owner_id) staffCode,    ");
        sql.append(" nvl((select b.name from stock_debit c, app_params b where c.owner_id = a.owner_id and b.status = 1 and b.TYPE = 'DEBIT_TYPE' AND b.code = c.debit_type and c.debit_day_type = a.debit_day_type),'-') debitTypeOld,    ");
        sql.append(" nvl((select d.name from stock_debit f, app_params d where f.owner_id = a.owner_id and d.status = 1 and d.TYPE = 'DEBIT_DAY_TYPE' AND d.code = f.debit_day_type and f.debit_day_type = a.debit_day_type),'-') debitDayTypeOld,    ");
        sql.append(" nvl((select d.code from stock_debit f, app_params d where f.owner_id = a.owner_id and d.status = 1 and d.TYPE = 'DEBIT_DAY_TYPE' AND d.code = f.debit_day_type and f.debit_day_type = a.debit_day_type),'-') debitDayTypeOldCode,    ");
        sql.append(" (select code from app_params where code = a.debit_type and TYPE = 'DEBIT_TYPE' and status = 1 ) debitType,     ");
        sql.append(" (select code from app_params where code = a.debit_day_type and TYPE = 'DEBIT_DAY_TYPE' and status = 1) debitDayType  ,    ");
        sql.append(" a.status status, ");
        sql.append(" a.note note, ");
        sql.append(" a.owner_id  ownerId, ");
        sql.append(" a.owner_type ownerType, ");
        sql.append(" (select max_debit from debit_type dt where dt.debit_type = a.debit_type and dt.debit_day_type = a.debit_day_type and status = 1)  valueDebitNew, ");
        sql.append(" (select max_debit from debit_type dt where dt.debit_type = (select debit_type from  ");
        sql.append(" stock_debit where owner_id = a.owner_id and owner_type=a.owner_type and debit_day_type = a.debit_day_type)  ");
        sql.append(" and dt.debit_day_type = a.debit_day_type and status = 1)  valueDebitOld ");
        sql.append(" from debit_request_detail a where  a.owner_type = 2 and a.request_detail_id = ? ");

        try {
            Query query = session.createSQLQuery(sql.toString())
                    .addScalar("shopCode", Hibernate.STRING)
                    .addScalar("staffCode", Hibernate.STRING)
                    .addScalar("debitTypeOld", Hibernate.STRING)
                    .addScalar("debitType", Hibernate.STRING)
                    .addScalar("debitDayTypeOld", Hibernate.STRING)
                    .addScalar("debitDayTypeOldCode", Hibernate.STRING)
                    .addScalar("debitDayType", Hibernate.STRING)
                    .addScalar("status", Hibernate.LONG)
                    .addScalar("note", Hibernate.STRING)
                    .addScalar("ownerId", Hibernate.LONG)
                    .addScalar("ownerType", Hibernate.LONG)
                    .addScalar("valueDebitOld", Hibernate.STRING)
                    .addScalar("valueDebitNew", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(DebitRequestDetailBean.class));
            query.setLong(0, requestDetailId);
            List<DebitRequestDetailBean> listDebitRequestDetailBean = query.list();
            if (!listDebitRequestDetailBean.isEmpty()) {
                debitRequest = listDebitRequestDetailBean.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return debitRequest;
    }

    public String viewDetail() {
        HttpServletRequest req = getRequest();
        String requestId = (String) req.getParameter("requestId");
        setTabSession("requestDebitId", requestId);
        try {
            List<DebitRequestDetailBean> lstDebitRequestDetailBean = this.getListRequestDetail(getSession(), Long.parseLong(requestId));
            req.getSession().setAttribute("listDebitRequestDetail", lstDebitRequestDetailBean);
            if (checkExistsUnApproval(getSession(), Long.parseLong(requestId))) {
                req.setAttribute("enableApproval", 1);//enable checkbox and buton phe duyet
            } else {
                req.setAttribute("enableApproval", 2);//disenable checkbox and buton phe duyet
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SEARCH_DEBIT_REQUEST_DETAIL;
    }

    public String pageNavigator() {
        return PREPARE_PAGE;
    }

    public String pageNavigatorDetail() {
        HttpServletRequest req = getRequest();
        String requestId = (String) getTabSession("requestDebitId");
        if (checkExistsUnApproval(getSession(), Long.parseLong(requestId))) {
            req.setAttribute("enableApproval", 1);//enable checkbox and buton phe duyet
        } else {
            req.setAttribute("enableApproval", 2);//disenable checkbox and buton phe duyet
        }
        return PREPARE_PAGE;
    }

    public String approvalRequest() {
        getReqSession();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Long[] listRequestId = searchDebitRequestForm.getListRequestId();
        Long[] listRequestDetailId = searchDebitRequestForm.getListRequestDetailId();
        String[] listApprovalReject = searchDebitRequestForm.getListApprovalReject();
        String[] listDescription = searchDebitRequestForm.getListDescription();
        String[] status = searchDebitRequestForm.getStatusList();
        Session sessionHiber = getSession();
        String sqlUpdateDebit = "update stock_debit set "
                + " debit_type = ?, "
                + " debit_day_type = ?, "
                + " last_update_date = ?, "
                + " last_update_user = ? "
                + " where owner_id = ? "
                + " and debit_day_type = ? "
                + " and owner_type = ?";
        String sqlInsertDebit = " insert into "
                + "stock_debit("
                + "owner_id,"
                + "owner_type,"
                + "debit_type,"
                + "debit_day_type,"
                + "create_date,"
                + "create_user) "
                + " values (?,?,?,?,?,?) ";
        String sqlUpdateDebitDetail = "update debit_request_detail "
                + "set note = ?, status = ? "
                + "where request_detail_id = ?";
        String sqlUpdateStatusDebitReq = "update debit_request "
                + "set status = ?, last_update_date = ?, last_update_user =? "
                + "where request_id =? and nvl(request_object_type,0) <> 1";
        try {
            for (int i = 0; i < listRequestDetailId.length; i++) {
                if (status[i].equals("1")) {
                    DebitRequestDetailBean debitRequestBean = this.getRequestDetail(getSession(), listRequestDetailId[i]);
                    if (debitRequestBean == null) {
                        req.setAttribute("returnMsg", "Duyệt yêu cầu thất bại");
                        if (checkExistsUnApproval(getSession(), listRequestId[0])) {
                            req.setAttribute("enableApproval", 1);//enable checkbox and buton phe duyet
                        } else {
                            req.setAttribute("enableApproval", 2);//disenable checkbox and buton phe duyet
                        }
                        return PREPARE_PAGE;
                    }
                    if (listApprovalReject[i].equals("2")) {
                        Query query = sessionHiber.createSQLQuery(sqlUpdateDebit);
                        query.setParameter(0, debitRequestBean.getDebitType());
                        query.setParameter(1, debitRequestBean.getDebitDayType());
                        query.setParameter(2, getSysdate());
                        query.setParameter(3, userToken.getLoginName());
                        query.setParameter(4, debitRequestBean.getOwnerId());
                        query.setParameter(5, debitRequestBean.getDebitDayTypeOldCode());
                        query.setParameter(6, debitRequestBean.getOwnerType());
                        int numUpdateD = query.executeUpdate();
                        if (numUpdateD == 0) {
                            Query query1 = sessionHiber.createSQLQuery(sqlInsertDebit);
                            query1.setParameter(0, debitRequestBean.getOwnerId());
                            query1.setParameter(1, 2L);
                            query1.setParameter(2, debitRequestBean.getDebitType());
                            query1.setParameter(3, debitRequestBean.getDebitDayType());
                            query1.setParameter(4, getSysdate());
                            query1.setParameter(5, userToken.getLoginName());
                            query1.executeUpdate();
                        } else {
                            //ghi log dung cho bao cao thay doi han muc
                            AjustLimitBO limit = new AjustLimitBO();
                            limit.setLastUpdateUser(userToken.getLoginName());
                            limit.setNewDebitType(debitRequestBean.getValueDebitNew());
                            limit.setNote(listDescription[i]);
                            limit.setOldDebitType(debitRequestBean.getValueDebitOld());
                            limit.setOwnerId(debitRequestBean.getOwnerId());
                            limit.setOwnerType(2L);
                            int insertLog = insertLogReport(sessionHiber, limit);
                        }
                    }
                    Query query2 = sessionHiber.createSQLQuery(sqlUpdateDebitDetail);
                    query2.setString(0, listDescription[i]);
                    query2.setParameter(1, listApprovalReject[i]);
                    query2.setLong(2, listRequestDetailId[i]);
                    query2.executeUpdate();
                    //set status for Debit_request by status detail                    
                }
            }
            Query query3 = sessionHiber.createSQLQuery(sqlUpdateStatusDebitReq);
            query3.setParameter(0, this.getStatusDebitRequest(sessionHiber, listRequestId[0]));
            query3.setParameter(1, getSysdate());
            query3.setParameter(2, userToken.getLoginName());
            query3.setParameter(3, listRequestId[0]);
            query3.executeUpdate();

            req.setAttribute("returnHMMsg", "msg.duyet_yeu_cau_thanh_cong");
            req.getSession().setAttribute("listDebitRequestDetail", this.getListRequestDetail(getSession(), listRequestId[0]));
            if (checkExistsUnApproval(getSession(), listRequestId[0])) {
                req.setAttribute("enableApproval", 1);//enable checkbox and buton phe duyet
            } else {
                req.setAttribute("enableApproval", 2);//disenable checkbox and buton phe duyet
            }
            req.getSession().setAttribute("listDebitRequest", this.getListDebitRequest(getSession(), userToken.getShopId()));
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("returnHMMsg", "Duyệt yêu cầu thất bại");
            if (checkExistsUnApproval(getSession(), listRequestId[0])) {
                req.setAttribute("enableApproval", 1);//enable checkbox and buton phe duyet
            } else {
                req.setAttribute("enableApproval", 2);//disenable checkbox and buton phe duyet
            }
        }

        return PREPARE_PAGE;
    }

    private String getStatusDebitRequest(Session session, Long requestId) {
        String sql = " select decode((select 'x' from dual where EXISTS(select 'x' from debit_request_detail "
                + " where request_id = ? and status != 1)),'x',    "
                + " (decode((select 'x' from dual where EXISTS(select 'x' from debit_request_detail    "
                + " where request_id = ? and status != 2)),'x','3','2')),'1') from dual  ";
        String status = "1";
        try {
            Query query = session.createSQLQuery(sql);
            query.setParameter(0, requestId);
            query.setParameter(1, requestId);
            List lst = query.list();
            if (!lst.isEmpty()) {
                status = (String) lst.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean checkValidate() {
        HttpServletRequest req = getRequest();
        Date fromDate = searchDebitRequestForm.getFromDate();
        Date toDate = searchDebitRequestForm.getToDate();
        if (DateTimeUtils.distanceBeetwen2Date(fromDate, toDate) > 30) {
            req.setAttribute("returnMsg", "err.tu_ngay_den_ngay_ko_qua_30_day");
            return false;
        }
        return true;
    }


    /*
     * kiem tra xem trong YC da duyet (stt = 3) con ton tai yc chi tiet nao co stt = 1 khong
     * co - true
     * no - false
     */
    public boolean checkExistsUnApproval(Session session, Long requestId) {
        String sql = "select 'x' from dual where EXISTS (select 'x' from debit_request_detail where request_id = ? and status = 1)";
        try {
            Query query = session.createSQLQuery(sql);
            query.setParameter(0, requestId);
            if (!query.list().isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int insertLogReport(Session session, AjustLimitBO input) {
        int insert = 0;
        StringBuilder sql = new StringBuilder();
        sql.append(" insert into  adjust_limit(owner_id,owner_type,old_debit_type,");
        sql.append(" new_debit_type,last_update_date,last_update_user,note) values(  ");
        sql.append(" :owner_id,:owner_type,:old_debit_type, ");
        sql.append(" :new_debit_type,sysdate,:last_update_user,:note) ");

        if (input.getOwnerId() == null || input.getOwnerType() == null
                || !StringUtils.validString(input.getOldDebitType())
                || !StringUtils.validString(input.getNewDebitType())
                || !StringUtils.validString(input.getLastUpdateUser())) {
            return insert;
        }
        try {
            Query query = session.createSQLQuery(sql.toString());
            query.setParameter("owner_id", input.getOwnerId());
            query.setParameter("owner_type", input.getOwnerType());
            query.setParameter("old_debit_type", input.getOldDebitType());
            query.setParameter("new_debit_type", input.getNewDebitType());
            query.setParameter("last_update_user", input.getLastUpdateUser());
            query.setParameter("note", input.getNote());

            insert = query.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return insert;
    }
}
