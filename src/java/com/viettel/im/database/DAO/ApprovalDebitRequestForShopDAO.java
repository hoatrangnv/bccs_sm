/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

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
public class ApprovalDebitRequestForShopDAO extends BaseDAO {

    SearchDebitRequestForm searchDebitRequestForm = new SearchDebitRequestForm();
    String PREPARE_PAGE = "preparePage";
    String SEARCH_DEBIT_REQUEST = "listDebitRequest";
    String SEARCH_DEBIT_REQUEST_DETAIL = "listDebitRequestDetail";
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
        String preparePage = PREPARE_PAGE;
        searchDebitRequestForm.resetForm();
        req.getSession().setAttribute("listDebitRequest", this.getListDebitRequest(getSession()));
        this.resetListDetail(req);
        return preparePage;
    }

    public List<DebitRequestBean> getListDebitRequest(Session session) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<DebitRequestBean> lstDebitRequestBean = new ArrayList<DebitRequestBean>();
        String sql = "select "
                + " a.request_id requestId,"
                + " a.request_code requestCode, "
                + " a.create_date createDate,"
                + " a.create_user createUser,"
                + " a.file_name clientFileName, "
                //+ " a.file_content fileContent, "
                + " a.descripttion descripttion, "
                + " a.status, "
                + " a.last_update_date lastUpdateDate, "
                + " a.last_update_user lastUpdateUser  "
                + " from debit_request a where nvl(request_object_type,0) = 1  "
                + " and ((select shop_id from staff where lower(staff_code) = lower(a.create_user) and status = 1) in (select shop_id from tbl_shop_tree where root_id = ?)) "
                + " order by request_id desc";
        try {
            Query query = session.createSQLQuery(sql)
                    .addScalar("requestId", Hibernate.LONG)
                    .addScalar("requestCode", Hibernate.STRING)
                    .addScalar("createDate", Hibernate.DATE)
                    .addScalar("createUser", Hibernate.STRING)
                    .addScalar("clientFileName", Hibernate.STRING)
                    //.addScalar("fileContent", Hibernate.BINARY)
                    .addScalar("descripttion", Hibernate.STRING)
                    .addScalar("status", Hibernate.LONG)
                    .addScalar("lastUpdateDate", Hibernate.DATE)
                    .addScalar("lastUpdateUser", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(DebitRequestBean.class));
            query.setParameter(0, userToken.getShopId() == null ? "" : userToken.getShopId());
            lstDebitRequestBean = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstDebitRequestBean;
    }

    public List<DebitRequestBean> search(Session session, SearchDebitRequestForm inputForm) {
        List<DebitRequestBean> listDebitRequest = new ArrayList<DebitRequestBean>();
        List param = new ArrayList();
        try {
            String sql = "select a.request_id requestId,"
                    + "a.request_code requestCode,"
                    + "a.create_date createDate,"
                    + "a.create_user createUser,"
                    + "a.file_name clientFileName,"
                    + "a.file_content fileContent,"
                    + "a.descripttion descripttion,"
                    + "a.status, "
                    + "a.last_update_date lastUpdateDate,"
                    + "a.last_update_user lastUpdateUser "
                    + "from debit_request a "
                    + "where nvl(request_object_type,0) = 1  ";
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
        if (checkValidate()) {
            List<DebitRequestBean> listDebitRequestBean = new ArrayList<DebitRequestBean>();
            listDebitRequestBean = this.search(getSession(), searchDebitRequestForm);
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
        try {
            List<DebitRequestBean> listItems = search(getSession(), searchDebitRequestForm);
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
        sql.append("select ");
        sql.append("(select shop_code from shop where shop_id =  a.owner_id) shopCode,    ");
        sql.append("(select name from shop where shop_id =  a.owner_id) shopName,    ");
        sql.append("nvl((select c.debit_type from stock_debit c where c.owner_id = a.owner_id  and c.debit_day_type = a.debit_day_type and c.owner_type = 1 and c.stock_type_id = a.stock_type_id and c.request_debit_type = a.request_debit_type ),'--') debitTypeOld,   ");
        sql.append("nvl((select c.request_debit_type from stock_debit c where c.owner_id = a.owner_id  and c.debit_day_type = a.debit_day_type and c.owner_type = 1 and c.stock_type_id = a.stock_type_id and c.request_debit_type = a.request_debit_type ),3) requestDebitTypeOld,   ");
        sql.append("a.debit_type debitType,    ");
        sql.append("(select name from app_params where type = 'DEBIT_DAY_TYPE' and status = 1 and code = a.debit_day_type) debitDayType  ,   ");
        sql.append("a.status status, a.note note , a.request_detail_id  requestDetailId, a.request_id  requestId,");
        sql.append("(SELECT name FROM stock_type WHERE stock_type_id = a.stock_type_id) stockTypeName, ");
        sql.append("a.request_debit_type requestDebitType ");
        sql.append("from debit_request_detail a, debit_request k   ");
        sql.append("where nvl(k.request_object_type,0) = 1 and k.request_id = ?  ");
        sql.append("and a.request_id = k.request_id   ");
        sql.append("order by k.request_id desc");

        try {
            Query query = session.createSQLQuery(sql.toString())
                    .addScalar("shopCode", Hibernate.STRING)
                    .addScalar("shopName", Hibernate.STRING)
                    .addScalar("debitTypeOld", Hibernate.STRING)
                    .addScalar("debitType", Hibernate.STRING)
                    .addScalar("debitDayType", Hibernate.STRING)
                    .addScalar("status", Hibernate.LONG)
                    .addScalar("note", Hibernate.STRING)
                    .addScalar("requestDetailId", Hibernate.LONG)
                    .addScalar("requestId", Hibernate.LONG)
                    .addScalar("stockTypeName", Hibernate.STRING)
                    .addScalar("requestDebitType", Hibernate.LONG)
                    .addScalar("requestDebitTypeOld", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(DebitRequestDetailBean.class));
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
        
        sql.append(" SELECT   (SELECT   shop_code ");
        sql.append("             FROM   shop ");
        sql.append("            WHERE   shop_id = a.owner_id) ");
        sql.append("              shopcode, ");
        sql.append("          (SELECT   name ");
        sql.append("             FROM   shop ");
        sql.append("            WHERE   shop_id = a.owner_id) ");
        sql.append("              shopname, ");
        sql.append("          NVL ( ");
        sql.append("              (SELECT   c.debit_type ");
        sql.append("                 FROM   stock_debit c ");
        sql.append("                WHERE       c.owner_id = a.owner_id ");
        sql.append("                        AND c.debit_day_type = a.debit_day_type ");
        sql.append("                        AND c.owner_type = 1 ");
        sql.append("                        AND c.request_debit_type = a.request_debit_type ");
        sql.append("                        AND c.stock_type_id = a.stock_type_id), ");
        sql.append("              '--') ");
        sql.append("              debittypeold, ");
        sql.append("          a.debit_type debittype, ");
        sql.append("          (SELECT   code ");
        sql.append("             FROM   app_params ");
        sql.append("            WHERE       code = a.debit_day_type ");
        sql.append("                    AND TYPE = 'DEBIT_DAY_TYPE' ");
        sql.append("                    AND status = 1) ");
        sql.append("              debitdaytype, ");
        sql.append("          a.status status, ");
        sql.append("          a.note note, ");
        sql.append("          a.owner_id ownerid, ");
        sql.append("          a.owner_type ownertype, ");
        sql.append("          a.stock_type_id stocktypeid, ");
        sql.append("          a.request_debit_type requestdebittype ");
        sql.append("   FROM   debit_request_detail a ");
        sql.append("  WHERE   a.owner_type = 1 AND a.request_detail_id = ? ");

        try {
            Query query = session.createSQLQuery(sql.toString())
                    .addScalar("shopCode", Hibernate.STRING)
                    .addScalar("shopName", Hibernate.STRING)
                    .addScalar("debitTypeOld", Hibernate.STRING)
                    .addScalar("debitType", Hibernate.STRING)
                    .addScalar("debitDayType", Hibernate.STRING)
                    .addScalar("status", Hibernate.LONG)
                    .addScalar("note", Hibernate.STRING)
                    .addScalar("ownerId", Hibernate.LONG)
                    .addScalar("ownerType", Hibernate.LONG)
                    .addScalar("requestDebitType", Hibernate.LONG)
                    .addScalar("stockTypeId", Hibernate.LONG)
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
            if (checkExistsUnApproval(getSession(), Long.parseLong(requestId))) {//enable button phe duyet neu nhu con yeu cau chua duyet
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

        StringBuilder sqlUpdateDebit = new StringBuilder();
        sqlUpdateDebit.append(" UPDATE   stock_debit ");
        sqlUpdateDebit.append("    SET   debit_type = ?, ");
        sqlUpdateDebit.append("          last_update_date = ?, ");
        sqlUpdateDebit.append("          last_update_user = ?, ");
        sqlUpdateDebit.append("          request_debit_type = ? ");
        sqlUpdateDebit.append("  WHERE       owner_id = ? ");
        sqlUpdateDebit.append("          AND debit_day_type = ? ");
        sqlUpdateDebit.append("          AND owner_type = ? ");
        sqlUpdateDebit.append("          AND stock_type_id = ? ");

        StringBuilder sqlInsertDebit = new StringBuilder();
        sqlInsertDebit.append(" INSERT INTO stock_debit (owner_id, ");
        sqlInsertDebit.append("                          owner_type, ");
        sqlInsertDebit.append("                          debit_type, ");
        sqlInsertDebit.append("                          debit_day_type, ");
        sqlInsertDebit.append("                          create_date, ");
        sqlInsertDebit.append("                          stock_type_id, ");
        sqlInsertDebit.append("                          request_debit_type, ");
        sqlInsertDebit.append("                          create_user) ");
        sqlInsertDebit.append("   VALUES   (?,?,?,?,?,?,?,?) ");

        String sqlUpdateDebitDetail = "update debit_request_detail "
                + "set note = ?, status = ? "
                + "where request_detail_id = ?";
        String sqlUpdateStatusDebitReq = "update debit_request "
                + "set status = ?, last_update_date = ?, last_update_user =? "
                + "where request_id =? and nvl(request_object_type,0) = 1";
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

                        Query query = sessionHiber.createSQLQuery(sqlUpdateDebit.toString());
                        query.setParameter(0, debitRequestBean.getDebitType());
                        query.setParameter(1, getSysdate());
                        query.setParameter(2, userToken.getLoginName());
                        query.setParameter(3, debitRequestBean.getRequestDebitType());
                        query.setParameter(4, debitRequestBean.getOwnerId());
                        query.setParameter(5, debitRequestBean.getDebitDayType());
                        query.setParameter(6, debitRequestBean.getOwnerType());
                        query.setParameter(7, debitRequestBean.getStockTypeId());

                        int numUpdateD = query.executeUpdate();
                        if (numUpdateD == 0) {
                            Query query1 = sessionHiber.createSQLQuery(sqlInsertDebit.toString());
                            query1.setParameter(0, debitRequestBean.getOwnerId());
                            query1.setParameter(1, 1L);
                            query1.setParameter(2, debitRequestBean.getDebitType());
                            query1.setParameter(3, debitRequestBean.getDebitDayType());
                            query1.setParameter(4, getSysdate());
                            query1.setParameter(5, debitRequestBean.getStockTypeId());
                            query1.setParameter(6, debitRequestBean.getRequestDebitType());
                            query1.setParameter(7, userToken.getLoginName());
                            query1.executeUpdate();
                        } else {
                            //ghi log neu update
                            AjustLimitBO limit = new AjustLimitBO();
                            limit.setLastUpdateUser(userToken.getLoginName());
                            limit.setNewDebitType(debitRequestBean.getDebitType());
                            limit.setNote(listDescription[i]);
                            limit.setOldDebitType(debitRequestBean.getDebitTypeOld());
                            limit.setOwnerId(debitRequestBean.getOwnerId());
                            limit.setOwnerType(1L);
                            limit.setRequestDebitType(debitRequestBean.getRequestDebitType());
                            limit.setStockTypeId(debitRequestBean.getStockTypeId());
                            insertLogReport(sessionHiber, limit);
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
            req.getSession().setAttribute("listDebitRequest", this.getListDebitRequest(getSession()));
        } catch (Exception e) {
            e.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            req.setAttribute("returnHMMsg", "Duyệt yêu cầu thất bại");
            if (checkExistsUnApproval(getSession(), listRequestId[0])) {
                req.setAttribute("enableApproval", 1);//enable checkbox and buton phe duyet
            } else {
                req.setAttribute("enableApproval", 2);//disable checkbox and buton phe duyet
            }
        }

        return PREPARE_PAGE;
    }

    private String getStatusDebitRequest(Session session, Long requestId) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT   DECODE ( ");
        sql.append("              (SELECT   'x' ");
        sql.append("                 FROM   DUAL ");
        sql.append("                WHERE   EXISTS (SELECT   'x' ");
        sql.append("                                  FROM   debit_request_detail ");
        sql.append("                                 WHERE   request_id = ? AND status != 1)), ");
        sql.append("              'x', ");
        sql.append("              (DECODE ( ");
        sql.append("                   (SELECT   'x' ");
        sql.append("                      FROM   DUAL ");
        sql.append("                     WHERE   EXISTS (SELECT   'x' ");
        sql.append("                                       FROM   debit_request_detail ");
        sql.append("                                      WHERE   request_id = ? AND status != 2)), ");
        sql.append("                   'x', ");
        sql.append("                   '3', ");
        sql.append("                   '2')), ");
        sql.append("              '1') ");
        sql.append("   FROM   DUAL ");

        String status = "1";
        try {
            Query query = session.createSQLQuery(sql.toString());
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
        sql.append(" insert into adjust_limit(owner_id,owner_type,stock_type_id,old_debit_type,");
        sql.append(" new_debit_type,request_debit_type,last_update_date,last_update_user,note) values(  ");
        sql.append(" :owner_id,:owner_type,:stock_type_id,:old_debit_type, ");
        sql.append(" :new_debit_type,:request_debit_type,sysdate,:last_update_user,:note) ");

        if (input.getOwnerId() == null || input.getOwnerType() == null
                || input.getStockTypeId() == null || !StringUtils.validString(input.getOldDebitType())
                || !StringUtils.validString(input.getNewDebitType()) || input.getRequestDebitType() == null
                || !StringUtils.validString(input.getLastUpdateUser())) {
            return insert;
        }
        try {
            Query query = session.createSQLQuery(sql.toString());
            query.setParameter("owner_id", input.getOwnerId());
            query.setParameter("owner_type", input.getOwnerType());
            query.setParameter("stock_type_id", input.getStockTypeId());
            query.setParameter("old_debit_type", input.getOldDebitType());
            query.setParameter("new_debit_type", input.getNewDebitType());
            query.setParameter("request_debit_type", input.getRequestDebitType());
            query.setParameter("last_update_user", input.getLastUpdateUser());
            query.setParameter("note", input.getNote());

            insert = query.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return insert;
    }
}
