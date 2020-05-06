/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.MapDebitDayTypeForm;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.Shop;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author thinhph2
 */
public class MapDebitDayWithShopDAO extends BaseDAOAction {

    private String QUERY_FORM = "mapDebitDayType";
    private String MAIN_FORM = "mapDebitDayTypeMain";
    private String LIST_RESULT = "listMapDebitDayResult";
    MapDebitDayTypeForm inputForm = new MapDebitDayTypeForm();

    public MapDebitDayTypeForm getInputForm() {
        return inputForm;
    }

    public void setInputForm(MapDebitDayTypeForm inputForm) {
        this.inputForm = inputForm;
    }

    public String preparePage() {
        HttpServletRequest req = getRequest();
        Session session = getSession();
//        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
//        Shop shop = new ShopDAO().findById(userToken.getShopId());
        loadCombobox(session, req);
        removeTabSession("listMappingResult");
        inputForm.resetForm();
//        inputForm.setShopCode(shop.getShopCode());
//        inputForm.setShopName(shop.getName());
        List listResult = search(session, inputForm, "");
        setTabSession("listMappingResult", listResult);
        req.setAttribute("preUpdate", false);
        return MAIN_FORM;
    }

    private void loadCombobox(Session session, HttpServletRequest req) {
        List listDebitDayType = new DebitDayTypeDAO().findAllActive(session);
        req.setAttribute("listDebitDayType", listDebitDayType);
    }

    private List<MapDebitDayTypeForm> search(Session session, MapDebitDayTypeForm inputForm, String debitDayShopId) {
        List<MapDebitDayTypeForm> listResult = new ArrayList<MapDebitDayTypeForm>();
        List apparams = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append(" select  db.id, debit_day_type_id debitDayTypeId, shop_id shopId, status, ");
        sql.append(" nvl((select shop_code from shop where shop_id = db.shop_id and status = 1),'Select All') shopCode, ");
        sql.append(" nvl((select name from shop where shop_id = db.shop_id and status = 1),'Select All') shopName, ");
        sql.append(" (select ddt_name from debit_day_type where id = db.debit_day_type_id) debitDayTypeName, ");
        sql.append(" shop_id shopId ");
        sql.append("  from debit_day_type_shop db where 1=1 ");

        try {
            System.out.println("debitDayShopId|" + debitDayShopId);
            if (!StringUtils.validString(debitDayShopId)) {
                System.out.println("inputForm.getDebitDayTypeId()|" + inputForm.getDebitDayTypeId() + "|inputForm.getShopCode()|" + inputForm.getShopCode() + "|inputForm.getStatus()|" + inputForm.getStatus());
                if (inputForm.getDebitDayTypeId() != null) {
                    sql.append(" and db.debit_day_type_id = ? ");
                    apparams.add(inputForm.getDebitDayTypeId());
                }

                if (StringUtils.validString(inputForm.getShopCode())) {
                    sql.append(" and db.shop_id = (select shop_id from shop where lower(shop_code) = ? and status = 1) ");
                    apparams.add(inputForm.getShopCode().trim().toLowerCase());
                }

                if (inputForm.getStatus() != null) {
                    sql.append(" and db.status = ? ");
                    apparams.add(inputForm.getStatus());
                }
            } else {
                sql.append(" and db.id = ? ");
                apparams.add(debitDayShopId.trim());
            }
            sql.append("order by nlssort(shopCode, 'nls_sort=Vietnamese') desc ");
            Query query = session.createSQLQuery(sql.toString())
                    .addScalar("shopCode", Hibernate.STRING)
                    .addScalar("status", Hibernate.LONG)
                    .addScalar("shopName", Hibernate.STRING)
                    .addScalar("debitDayTypeId", Hibernate.LONG)
                    .addScalar("debitDayTypeName", Hibernate.STRING)
                    .addScalar("id", Hibernate.LONG)
                    .addScalar("shopId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(MapDebitDayTypeForm.class));

            for (int i = 0; i < apparams.size(); i++) {
                query.setParameter(i, apparams.get(i));
            }

            listResult = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listResult;
    }

    public String addChannelGroup() {
        HttpServletRequest req = getRequest();
        Session session = getSession();
        StringBuilder sql = new StringBuilder();
        StringBuilder insertForAll = new StringBuilder();
        StringBuilder updateForAll = new StringBuilder();
        if (inputForm.isCheckAll()) {
            sql.append(" update debit_day_type_shop set status = 0 where debit_day_type_id = ? ");
            insertForAll.append(" insert into debit_day_type_shop(id,debit_day_type_id,shop_id,status) ");
            insertForAll.append(" values(debit_day_type_shop_seq.nextval, ?,-1,1) ");
            updateForAll.append(" update debit_day_type_shop set status = 1 where shop_id = -1 and debit_day_type_id = ? ");
            try {
                String strValidate = validateForm(session, inputForm);
                if (StringUtils.validString(strValidate)) {
                    req.setAttribute("returnMsg", strValidate);
                    loadCombobox(session, req);
                    return MAIN_FORM;
                }
                if (checkExistsCurentDate(session, false)) {
                    req.setAttribute("returnMsg", "err.trung_khoang_cau_hinh_km");
                    loadCombobox(session, req);
                    return MAIN_FORM;
                }
                Query query = session.createSQLQuery(sql.toString());
                query.setParameter(0, inputForm.getDebitDayTypeId());
                int update = query.executeUpdate();

                Query queryUpdate = session.createSQLQuery(updateForAll.toString());
                queryUpdate.setParameter(0, inputForm.getDebitDayTypeId());
                int updateForAllResult = queryUpdate.executeUpdate();
                int insertForAllResult = 0;
                if (updateForAllResult <= 0) {
                    Query queryInsert = session.createSQLQuery(insertForAll.toString());
                    queryInsert.setParameter(0, inputForm.getDebitDayTypeId());
                    insertForAllResult = queryInsert.executeUpdate();
                }

                if (updateForAllResult > 0 || insertForAllResult > 0) {
                    req.setAttribute("returnMsg", "ERR.CHL.099");
                } else {
                    req.setAttribute("returnMsg", "msg.693.8");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            sql.append(" insert into debit_day_type_shop(id,debit_day_type_id,shop_id,status) ");
            sql.append(" values(debit_day_type_shop_seq.nextval, ?,(select shop_id from shop where lower(shop_code) = ? and status = 1),?) ");

            if (checkExistsCheckAll(session)) {
                req.setAttribute("returnMsg", "msg.promotion_day_appended_for_all_units");
                loadCombobox(session, req);
                return MAIN_FORM;
            }
            if (new DebitDayTypeShopDAO().checkConstraints(session, inputForm.getShopCode(), inputForm.getDebitDayTypeId())) {
                req.setAttribute("returnMsg", "err.don_vi_trung_khoang_km");
                loadCombobox(session, req);
                return MAIN_FORM;
            }
            if (checkExistsCurentDate(session, false)) {
                req.setAttribute("returnMsg", "err.trung_khoang_cau_hinh_km");
                loadCombobox(session, req);
                return MAIN_FORM;
            }
            try {
                String strValidate = validateForm(session, inputForm);
                if (StringUtils.validString(strValidate)) {
                    req.setAttribute("returnMsg", strValidate);
                    loadCombobox(session, req);
                    return MAIN_FORM;
                }

                Query query = getSession().createSQLQuery(sql.toString());
                query.setParameter(0, inputForm.getDebitDayTypeId());
                query.setParameter(1, inputForm.getShopCode().toLowerCase());
                query.setParameter(2, inputForm.getStatus());
                int insert = query.executeUpdate();

                if (insert > 0) {
                    req.setAttribute("returnMsg", "ERR.CHL.099");
                } else {
                    req.setAttribute("returnMsg", "msg.693.8");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        loadCombobox(session, req);
        removeTabSession("listMappingResult");

        List listResult = search(session, inputForm, "");
        setTabSession("listMappingResult", listResult);
        inputForm.resetForm();
        return MAIN_FORM;
    }

    public String preUpdate() {
        HttpServletRequest req = getRequest();
        Session session = getSession();
        ShopDAO shopDAO = new ShopDAO();
        try {
            String debitDayTypeShopId = req.getParameter("debitDayTypeShopId");
            if (StringUtils.validString(debitDayTypeShopId)) {
                try {
                    Long.parseLong(debitDayTypeShopId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    req.setAttribute("returnMsg", "Lỗi !!!. Có lỗi xảy ra trong khi thực hiện");
                }
            } else {
                req.setAttribute("returnMsg", "Lỗi !!!. Không lấy được ID");
            }
            List<MapDebitDayTypeForm> listResultByGroupId = search(session, inputForm, debitDayTypeShopId);
            inputForm.loadFormPreUpdate(listResultByGroupId.get(0));
            if (listResultByGroupId.get(0).getShopId() == -1L) {
                req.setAttribute("checkAllShop", 1);
            } else {
                Shop shop = shopDAO.findShopsAvailableByShopCode(listResultByGroupId.get(0).getShopCode());
                inputForm.setShopCode(shop.getShopCode());
                inputForm.setShopName(shop.getName());
            }
            inputForm.setId(Long.parseLong(debitDayTypeShopId));
            loadCombobox(getSession(), req);
            req.setAttribute("preUpdate", true);
            req.setAttribute("disableGroupType", true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return QUERY_FORM;
    }

    public String cancel() {
        HttpServletRequest req = getRequest();
        Session session = getSession();
        loadCombobox(session, req);
        req.setAttribute("preUpdate", false);
        inputForm.resetForm();
        return MAIN_FORM;
    }

    public String updateChannelGroup() {
        HttpServletRequest req = getRequest();
        Session session = getSession();
        String sql = "update debit_day_type_shop set debit_day_type_id = ?, shop_id = (select shop_id from shop where lower(shop_code) = ? and status = 1), status = ? where id = ?";

        if (inputForm.isCheckAll()) {
            String updateBeforForAll = "update debit_day_type_shop set status = 0 where debit_day_type_id = ? and status = 1";// cap nhat tat cac cac shop dang co cung dot KM duoc gan all ve khong hieu luc
            String sqlUpdateCheckAll = " update debit_day_type_shop set status = ?, debit_day_type_id = ? where  id = ? "; // gan dot KM A cho tat ca cac shop
            if (checkExistsCurentDate(session, true)) {
                req.setAttribute("returnMsg", "err.trung_khoang_cau_hinh_km");
                loadCombobox(session, req);
                return MAIN_FORM;
            }

            if (inputForm.getStatus() == 1) {
                Query queryUpdate = session.createSQLQuery(updateBeforForAll);
                queryUpdate.setParameter(0, inputForm.getDebitDayTypeId());
                int queryUpdateRe = queryUpdate.executeUpdate();
            }

            Query query = session.createSQLQuery(sqlUpdateCheckAll);
            query.setParameter(0, inputForm.getStatus());
            query.setParameter(1, inputForm.getDebitDayTypeId());
            query.setParameter(2, inputForm.getId());
            int update = query.executeUpdate();

            if (update > 0) {
                req.setAttribute("returnMsg", "MSG.SIK.280");
            } else {
                req.setAttribute("returnMsg", "msg.693.10");
            }
        } else {
            try {
                String strValidate = validateForm(session, inputForm);
                if (StringUtils.validString(strValidate)) {
                    req.setAttribute("returnMsg", strValidate);
                    loadCombobox(session, req);
                    req.setAttribute("preUpdate", true);
                    req.setAttribute("disableGroupType", true);
                    return MAIN_FORM;
                }
                if (inputForm.getStatus() == 1) {
                    if (checkExistsCurentDateUD(session)) {
                        req.setAttribute("returnMsg", "err.trung_khoang_cau_hinh_km");
                        loadCombobox(session, req);
                        return MAIN_FORM;
                    }
                }

                Query query = getSession().createSQLQuery(sql);
                query.setParameter(0, inputForm.getDebitDayTypeId());
                query.setParameter(1, inputForm.getShopCode().toLowerCase());
                query.setParameter(2, inputForm.getStatus());
                query.setParameter(3, inputForm.getId());
                System.out.println("inputForm.getDebitDayTypeId()|" + inputForm.getDebitDayTypeId() + "|shop-code|" + inputForm.getShopCode().toLowerCase() + "|inputForm.getStatus()|" + inputForm.getStatus()
                        + "|inputForm.getId()|" + inputForm.getId());
                int update = query.executeUpdate();

                if (update > 0) {
                    req.setAttribute("returnMsg", "MSG.SIK.280");
                } else {
                    req.setAttribute("returnMsg", "msg.693.10");
                }
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "msg.693.10");
                ex.printStackTrace();
            }
        }
        loadCombobox(session, req);
        removeTabSession("listMappingResult");
        inputForm.resetForm();
        List listResult = search(session, inputForm, "");
        setTabSession("listMappingResult", listResult);
        req.setAttribute("preUpdate", false);

        return MAIN_FORM;
    }

    public String searchChannel() {
        HttpServletRequest req = getRequest();
        Session session = getSession();
        loadCombobox(getSession(), req);
        removeTabSession("listMappingResult");
        List listResult = search(session, inputForm, "");
        setTabSession("listMappingResult", listResult);
        return LIST_RESULT;
    }

    public String validateForm(Session session, MapDebitDayTypeForm inputForm) {
        String strErr = "";
        if (!inputForm.isCheckAll()) {
            if (!StringUtils.validString(inputForm.getShopCode())) {
                strErr = "err.unit_not_select";
            }
        }
        if (inputForm.getDebitDayTypeId() == null) {
            strErr = "err.promotion_day_not_select";
        }
        if (inputForm.getStatus() == null) {
            strErr = "err.status_is_not_select";
        }

        return strErr;
    }

    public String pageNavigator() {
        return LIST_RESULT;
    }

    public boolean checkExistsCheckAll(Session session) {
        String sql = " select * from debit_day_type_shop where debit_day_type_id = ? and shop_id = -1 and status = 1 ";
        if (inputForm.getDebitDayTypeId() == null) {
            return true;
        }
        try {
            Query query = session.createSQLQuery(sql);
            query.setParameter(0, inputForm.getDebitDayTypeId());

            List list = query.list();
            if (!list.isEmpty()) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean checkExistsDate(Session session, String shopCode, Long debitDayTypeId, boolean checkAllShop, String id) {
        List params = new ArrayList();
        System.out.println("shopCode|" + shopCode + "|debitDayTypeId|" + debitDayTypeId + "|checkAllShop|" + checkAllShop + "|id|" + id);

        String sql = " select * from debit_day_type_shop s where exists (select * from debit_day_type where"
                + " debit_day_type_id  = ? and (sta_date <= sysdate and end_date >= sysdate)) ";

        if (debitDayTypeId == null) {
            return true;
        }

        params.add(debitDayTypeId);

        if (!checkAllShop) {
            if (!StringUtils.validString(shopCode)) {
                return true;
            }

            sql += " and s.shop_id = (select shop_id from shop where lower(shop_code) = ? and status = 1) ";
            params.add(shopCode.trim().toCharArray());
        }



        if (StringUtils.validString(id)) {
            sql += " and s.id != ? ";
            params.add(id);
        }

        try {
            Query query = session.createSQLQuery(sql);

            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
            List list = query.list();
            if (!list.isEmpty()) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean checkExistsCurentDate(Session session, boolean isUpdate) {
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        sql.append(" select * from debit_day_type_shop ds where ds.status = 1 ");
        if (StringUtils.validString(inputForm.getShopCode())) {
            sql.append(" and ((ds.shop_id = (select shop_id from shop where lower(shop_code) = ? and status = 1)) or ds.shop_id = -1 )");
            params.add(inputForm.getShopCode().trim().toLowerCase());
        } else {
            sql.append(" and ds.debit_day_type_id != ? ");
            params.add(inputForm.getDebitDayTypeId());
        }


        sql.append(" and ( ");
        if (isUpdate) {
            sql.append(" ((select sta_date from debit_day_type where id = ds.debit_day_type_id and status = 1 and id != ?) <= (select sta_date from debit_day_type where id = ? and status = 1) ");
            sql.append(" and (select end_date from debit_day_type where id = ds.debit_day_type_id and status = 1 and id != ?) >= (select sta_date from debit_day_type where id = ? and status = 1)) or  ");
            sql.append(" ((select sta_date from debit_day_type where id = ds.debit_day_type_id and status = 1 and id != ?) <= (select end_date from debit_day_type where id = ? and status = 1) ");
            sql.append(" and (select end_date from debit_day_type where id = ds.debit_day_type_id and status = 1 and id != ?) >= (select end_date from debit_day_type where id = ? and status = 1)) or ");
            sql.append(" ((select sta_date from debit_day_type where id = ds.debit_day_type_id and status = 1 and id != ?) >= (select sta_date from debit_day_type where id = ? and status = 1) ");
            sql.append(" and (((select end_date from debit_day_type where id = ds.debit_day_type_id and status = 1 and id != ?) <= (select end_date from debit_day_type where id = ? and status = 1)))  ");
            sql.append(" or (select end_date from debit_day_type where id = ? and status = 1) is null) ");

            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
        } else {
            sql.append(" ((select sta_date from debit_day_type where id = ds.debit_day_type_id and status = 1) <= (select sta_date from debit_day_type where id = ? and status = 1)                          ");
            sql.append(" and (select end_date from debit_day_type where id = ds.debit_day_type_id and status = 1) >= (select sta_date from debit_day_type where id = ? and status = 1)) or  ");
            sql.append(" ((select sta_date from debit_day_type where id = ds.debit_day_type_id and status = 1) <= (select end_date from debit_day_type where id = ? and status = 1)                          ");
            sql.append(" and (select end_date from debit_day_type where id = ds.debit_day_type_id and status = 1) >= (select end_date from debit_day_type where id = ? and status = 1)) or ");
            sql.append(" ((select sta_date from debit_day_type where id = ds.debit_day_type_id and status = 1) >= (select sta_date from debit_day_type where id = ? and status = 1)                          ");
            sql.append(" and (((select end_date from debit_day_type where id = ds.debit_day_type_id and status = 1) <= (select end_date from debit_day_type where id = ? and status = 1)))  ");
            sql.append(" or (select end_date from debit_day_type where id = ? and status = 1) is null) ");

            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
            params.add(inputForm.getDebitDayTypeId());
        }
        sql.append(" ) ");

        try {

            Query query = session.createSQLQuery(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
                System.out.println(i + "-" + params.get(i));
            }

            List list = query.list();
            if (!list.isEmpty()) {
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean checkExistsCurentDateUD(Session session) {
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        sql.append(" select * from debit_day_type_shop ds where ds.status = 1 ");
        if (StringUtils.validString(inputForm.getShopCode())) {
            sql.append(" and ((ds.shop_id = (select shop_id from shop where lower(shop_code) = ? and status = 1)) or ds.shop_id = -1 ) ");
            params.add(inputForm.getShopCode().trim().toLowerCase());
        } else {//check all
            sql.append(" and ds.debit_day_type_id != ? ");
            params.add(inputForm.getDebitDayTypeId());
        }


        sql.append(" and ( ");
        sql.append(" ((select sta_date from debit_day_type where id = ds.debit_day_type_id and status = 1) <= (select sta_date from debit_day_type where id = ? and status = 1)                          ");
        sql.append(" and (select end_date from debit_day_type where id = ds.debit_day_type_id and status = 1) >= (select sta_date from debit_day_type where id = ? and status = 1)) or  ");
        sql.append(" ((select sta_date from debit_day_type where id = ds.debit_day_type_id and status = 1) <= (select end_date from debit_day_type where id = ? and status = 1)                          ");
        sql.append(" and (select end_date from debit_day_type where id = ds.debit_day_type_id and status = 1) >= (select end_date from debit_day_type where id = ? and status = 1)) or ");
        sql.append(" ((select sta_date from debit_day_type where id = ds.debit_day_type_id and status = 1) >= (select sta_date from debit_day_type where id = ? and status = 1)                          ");
        sql.append(" and (((select end_date from debit_day_type where id = ds.debit_day_type_id and status = 1) <= (select end_date from debit_day_type where id = ? and status = 1)))  ");
        sql.append(" or (select end_date from debit_day_type where id = ? and status = 1) is null) ");

        params.add(inputForm.getDebitDayTypeId());
        params.add(inputForm.getDebitDayTypeId());
        params.add(inputForm.getDebitDayTypeId());
        params.add(inputForm.getDebitDayTypeId());
        params.add(inputForm.getDebitDayTypeId());
        params.add(inputForm.getDebitDayTypeId());
        params.add(inputForm.getDebitDayTypeId());
        sql.append(" ) ");

        try {

            Query query = session.createSQLQuery(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
                System.out.println(i + "-" + params.get(i));
            }

            List list = query.list();
            if (!list.isEmpty()) {
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
