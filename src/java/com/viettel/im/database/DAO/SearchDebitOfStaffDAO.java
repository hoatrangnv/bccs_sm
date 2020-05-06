/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.client.bean.SearchDebitOfStaffBean;
import com.viettel.im.client.form.SearchDebitRequestForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import java.util.ArrayList;
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
public class SearchDebitOfStaffDAO extends BaseDAO {

    private String SEARCHRESULT = "listDebitOfStaff";
    private String PREPAREPAGE = "preparePage";
    SearchDebitRequestForm inputForm = new SearchDebitRequestForm();

    public SearchDebitRequestForm getInputForm() {
        return inputForm;
    }

    public void setInputForm(SearchDebitRequestForm inputForm) {
        this.inputForm = inputForm;
    }

    public String preparePage() {

        return PREPAREPAGE;
    }

    public String searchDebitStaff() {
        HttpServletRequest req = getRequest();
        List<SearchDebitOfStaffBean> lstDebit = new ArrayList<SearchDebitOfStaffBean>();
        List<Staff> listStaff = null;
        List<Shop> listShop = null;
        List<Long> listStaffId = new ArrayList();
        List<Long> listShopId = new ArrayList();
        try {
            if (!("1").equals(inputForm.getRequestObjectType().trim()) || !("2").equals(inputForm.getRequestObjectType().trim())) {
                if (inputForm.getRequestObjectType().equals("2")) {
                    listStaff = new StaffDAO().findStaffsByStaffCode(inputForm.getStaffCode().trim());
                    if (listStaff == null || listStaff.isEmpty()) {
                        req.setAttribute("returnMsg", "Không tìm thấy nhân viên này");
                        return SEARCHRESULT;
                    }
                } else {
                    listShop = new ShopDAO().findShopsByShopCode(inputForm.getStaffCode().trim());
                    if (listShop == null || listShop.isEmpty()) {
                        req.setAttribute("returnMsg", "err.khong_tim_thay_cua_hang_nay");
                        return SEARCHRESULT;
                    }
                }
            } else {
                req.setAttribute("returnMsg", "Không tồn tại lại đối tượng này");
                return SEARCHRESULT;
            }
            if (listStaff != null && !listStaff.isEmpty()) {
                for (Staff staff : listStaff) {
                    listStaffId.add(staff.getStaffId());
                }
            }
            if (listShop != null && !listShop.isEmpty()) {
                for (Shop shop : listShop) {
                    listShopId.add(shop.getShopId());
                }
            }
            if (("2").equals(inputForm.getRequestObjectType().trim())) {
                lstDebit = this.getListDebitStaff(getSession(), listStaffId);
//                req.setAttribute("lstStaffIdSearch", listStaffId);
                setTabSession("lstStaffIdSearch", listStaffId);
            }
            if (("1").equals(inputForm.getRequestObjectType().trim())) {
                lstDebit = this.getListDebitShop(getSession(), listShopId);
//                req.setAttribute("lstStaffIdSearch", listShopId);
                setTabSession("lstStaffIdSearch", listShopId);
            }
            req.setAttribute("lstDebitStaff", lstDebit);
            req.setAttribute("requestObjectType", inputForm.getRequestObjectType().trim());
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("returnMsg", "Không tồn tại nhân viên này");
        }

        return SEARCHRESULT;
    }

    private List<SearchDebitOfStaffBean> getListDebitStaff(Session session, List listStaff) {
        List<SearchDebitOfStaffBean> lstDebit = new ArrayList<SearchDebitOfStaffBean>();
        if (listStaff == null || listStaff.isEmpty()) {
            return lstDebit;
        }
        StringBuilder sql = new StringBuilder();
        sql.append(" select  nvl(staff_code,'-') staffCode,  ");
        sql.append(" nvl(debit_type_name,'-') debitTypeName,  ");
        sql.append(" nvl(debit_day_name,'-') debitDayName,  ");
        sql.append(" ownerType, currentAmount, debitType from(    ");
        sql.append(" select  sd.owner_id staff_id,    ");
        sql.append(" (select sf.staff_code from staff sf where sf.staff_id = sd.owner_id) staff_code,    ");
        sql.append(" (select name from app_params where code = sd.debit_type and status = 1 and type = 'DEBIT_TYPE') debit_type_name,    ");
        sql.append(" (select name from app_params where code = sd.debit_day_type and status = 1 and type = 'DEBIT_DAY_TYPE') debit_day_name,      ");
        sql.append(" sd.owner_type ownerType ,  ");
        sql.append(" (SELECT nvl(SUM(st.quantity * p.price),0) FROM stock_total st, price p   ");
        sql.append(" WHERE st.stock_model_id = p.stock_model_id    ");
        sql.append(" AND p.status = 1 AND p.TYPE = 1    ");
        sql.append(" and p.price_policy = (SELECT price_policy FROM shop WHERE shop_id = (select shop_id from staff where staff_id = sd.owner_id))   ");
        sql.append(" and st.owner_type= 2 and st.owner_id = sd.owner_id  ");
        sql.append(" and p.sta_date <= sysdate and (p.end_date is null or p.end_date >= trunc(sysdate))   ");
        sql.append(" and st.state_id= 1 and p.currency= :currency) currentAmount, ");
        sql.append(" (select max_debit from debit_type where debit_type = sd.debit_type and debit_day_type = sd.debit_day_type and status = 1) debitType ");
        sql.append(" from stock_debit sd  ");
        sql.append(" where sd.owner_type = 2) a  where staff_id in (:listId) ");

        try {
            Query query = session.createSQLQuery(sql.toString())
                    .addScalar("staffCode", Hibernate.STRING)
                    .addScalar("debitTypeName", Hibernate.STRING)
                    .addScalar("debitDayName", Hibernate.STRING)
                    .addScalar("ownerType", Hibernate.STRING)
                    .addScalar("currentAmount", Hibernate.DOUBLE)
                    .addScalar("debitType", Hibernate.DOUBLE)
                    .setResultTransformer(Transformers.aliasToBean(SearchDebitOfStaffBean.class));
            query.setParameter("currency", Constant.PRICE_UNIT_DEBIT);
            query.setParameterList("listId", listStaff);
            lstDebit = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstDebit;
    }

    public String pageNavigator() {
        HttpServletRequest req = getRequest();
        List listId = (List) getTabSession("lstStaffIdSearch");

        if (("2").equals(inputForm.getRequestObjectType())) {
            req.setAttribute("lstDebitStaff", this.getListDebitStaff(getSession(), listId));
        }
        if (("1").equals(inputForm.getRequestObjectType())) {
            req.setAttribute("lstDebitStaff", this.getListDebitShop(getSession(), listId));
        }
        return SEARCHRESULT;
    }

    private List<SearchDebitOfStaffBean> getListDebitShop(Session session, List listShop) {
        List<SearchDebitOfStaffBean> lstDebit = new ArrayList<SearchDebitOfStaffBean>();
        if (listShop == null || listShop.isEmpty()) {
            return lstDebit;
        }
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT   (SELECT   shop_code ");
        sql.append("             FROM   shop ");
        sql.append("            WHERE   shop_id = sd.owner_id AND ROWNUM < 2) ");
        sql.append("              staffCode, ");
        sql.append("          (SELECT   name ");
        sql.append("             FROM   app_params ");
        sql.append("            WHERE       code = sd.debit_day_type ");
        sql.append("                    AND status = 1 ");
        sql.append("                    AND TYPE = 'DEBIT_DAY_TYPE') ");
        sql.append("              debitDayName, ");
        sql.append("          '--' financeName, ");
        sql.append("          sd.owner_type ownerType, ");
        sql.append("          (SELECT   name ");
        sql.append("             FROM   stock_type ");
        sql.append("            WHERE   stock_type_id = sd.stock_type_id) ");
        sql.append("              stockTypeName, ");
        sql.append("          DECODE ( ");
        sql.append("              sd.request_debit_type, ");
        sql.append("              1, ");
        sql.append(" (SELECT   SUM (st.quantity) ");
        sql.append("                 FROM   stock_total st ");
        sql.append("                WHERE   st.owner_id = sd.owner_id AND st.owner_type = 1 ");
        sql.append("                        AND st.stock_model_id IN ");
        sql.append("                                   (SELECT   stock_model_id ");
        sql.append("                                      FROM   stock_model ");
        sql.append("                                     WHERE   stock_type_id = sd.stock_type_id) ");
        sql.append("                        AND st.state_id = 1 ");
        sql.append("                        AND st.status = 1), ");
        sql.append("              (SELECT   SUM (st.quantity * p.price) ");
        sql.append("                 FROM   stock_total st, price p, stock_model sm ");
        sql.append("                WHERE       st.stock_model_id = p.stock_model_id ");
        sql.append("                        AND st.stock_model_id = sm.stock_model_id ");
        sql.append("                        AND p.status = 1 ");
        sql.append("                        AND p.TYPE = 1 ");
        sql.append("                        AND p.price_policy = ");
        sql.append("                               (SELECT   price_policy ");
        sql.append("                                  FROM   shop ");
        sql.append("                                 WHERE   shop_id = sd.owner_id AND status = 1) ");
        sql.append("                        AND st.owner_type = 1 ");
        sql.append("                        AND st.owner_id = sd.owner_id ");
        sql.append("                        AND p.sta_date <= SYSDATE ");
        sql.append("                        AND (p.end_date IS NULL ");
        sql.append("                             OR p.end_date >= TRUNC (SYSDATE)) ");
        sql.append("                        AND st.state_id = 1 AND sm.stock_type_id = sd.stock_type_id)) ");
        sql.append("               currentAmount, ");
        sql.append("          sd.request_debit_type requestDebitType, ");
        sql.append("          sd.debit_type debitType ");
        sql.append("   FROM   stock_debit sd ");
        sql.append("  WHERE   sd.owner_id IN (:listShopId) ");
        sql.append("          AND sd.owner_type = 1 ");
        sql.append("          order by staffCode, stockTypeName ");

        try {
            Query query = session.createSQLQuery(sql.toString()).addScalar("staffCode", Hibernate.STRING)
                    .addScalar("debitType", Hibernate.DOUBLE)
                    .addScalar("currentAmount", Hibernate.DOUBLE)
                    .addScalar("debitDayName", Hibernate.STRING).addScalar("financeName", Hibernate.STRING).addScalar("ownerType", Hibernate.STRING)
                    .addScalar("requestDebitType", Hibernate.LONG)
                    .addScalar("stockTypeName", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(SearchDebitOfStaffBean.class));
            query.setParameterList("listShopId", listShop);
            lstDebit = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = 0;
        for (Object b : listShop) {
            System.out.println("----tham so " + i + "---" + b.toString());
            i++;
        }
        for (SearchDebitOfStaffBean a : lstDebit) {
            System.out.println("------------" + a.getCurrentAmount());
        }
        return lstDebit;
    }
}
