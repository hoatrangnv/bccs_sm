/**
 *  AddSingleAnyPayManagement
 *  @author: HaiNT41
 *  @version: 1.0
 *  @since: 1.0
 */

package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.AddSingleAnyPayDetailBean;
import com.viettel.im.client.form.AddSingleAnyPayManagementForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.database.BO.TransAnyPay;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;


/**
 *
 * @author haint
 */
public class AddSingleAnyPayManagementDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(AddSingleAnyPayManagementDAO.class);
    private final String ADD_SINGLE_ANYPAY_MANAGEMENT = "addSingleAnyPayManagement";
    private final String ADD_SINGLE_ANYPAY_MANAGEMENT_LIST = "addSingleAnyPayManagementList";
    private final String ADD_SINGLE_ANYPAY_MANAGEMENT_DETAIL = "addSingleAnyPayManagementDetail";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private String pageForward;
    private AddSingleAnyPayManagementForm addSingleAnyPayManagementForm = new AddSingleAnyPayManagementForm();

    public AddSingleAnyPayManagementForm getAddSingleAnyPayManagementForm() {
        return addSingleAnyPayManagementForm;
    }

    public void setAddSingleAnyPayManagementForm(AddSingleAnyPayManagementForm addSingleAnyPayManagementForm) {
        this.addSingleAnyPayManagementForm = addSingleAnyPayManagementForm;
    }

    /**
     * @desc : Ham load ra giao dien mac dinh cua chuong trinh
     */
    public String preparePage() throws Exception {
        log.info("begin method preparePage of AddSingleAnyPayManagementDAO");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            addSingleAnyPayManagementForm.setFromDate(getSysdate());
            addSingleAnyPayManagementForm.setToDate(getSysdate());
            removeTabSession(ADD_SINGLE_ANYPAY_MANAGEMENT_LIST);
//            this.addSingleAnyPayForm.resetForm();

//            this.addSingleAnyPayForm.setShopCode(userToken.getShopCode());
//            this.addSingleAnyPayForm.setShopName(userToken.getShopName());
            
//            StringBuilder strQuery = new StringBuilder();
//            strQuery.append(" select trans_id transId, (select name from staff where staff.staff_id = trans_anypay.staff_id) staffName, to_char(trans_date,'dd/mm/yyyy') transDate, trans_type transType, round(trans_amount,4) transAmount,note");
//            strQuery.append(" from trans_anypay where 1 = 1 ");
//            Query query = getSession().createSQLQuery(strQuery.toString()).
//                    addScalar("transId",Hibernate.LONG).
//                    addScalar("staffName",Hibernate.STRING).
//                    addScalar("transType",Hibernate.STRING).
//                    addScalar("transDate",Hibernate.STRING).
//                    addScalar("transAmount",Hibernate.DOUBLE).
//                    addScalar("note",Hibernate.STRING).
//                    setResultTransformer(Transformers.aliasToBean(AddSingleAnyPayManagementForm.class));
//            List<AddSingleAnyPayManagementForm> list = query.list();
//            setTabSession(ADD_SINGLE_ANYPAY_MANAGEMENT_LIST, list);

        } catch (Exception ex) {
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
            log.error("Loi ham preparePage : " + ex);
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception. " + ex.toString());
        }

        pageForward = ADD_SINGLE_ANYPAY_MANAGEMENT;
        log.info("end method preparePage of AddSingleAnyPayManagementDAO");
        return pageForward;
    }

    /**
     * @desc : ham tim kiem giao dich
     * @return 
     */
    public String searchAnyPayTrans() {
        pageForward = ADD_SINGLE_ANYPAY_MANAGEMENT_LIST;
        log.info("begin method searchAnyPayTrans of AddSingleAnyPayManagementDAO");
        HttpServletRequest req = getRequest();
        try {
            String transType = addSingleAnyPayManagementForm.getTransType();
            String fromDate = null;
            String toDate = null;
            if (addSingleAnyPayManagementForm.getFromDate() != null) {
                fromDate = DateTimeUtils.convertDateToString_tuannv(addSingleAnyPayManagementForm.getFromDate());
            }
            if (addSingleAnyPayManagementForm.getToDate() != null) {
                toDate = DateTimeUtils.convertDateToString_tuannv(addSingleAnyPayManagementForm.getToDate());
            }
            List listParameter = new ArrayList();
            StringBuilder strQuery = new StringBuilder();
            strQuery.append(" select trans_id transId, (select name from staff where staff.staff_id = trans_anypay.staff_id) staffName, to_char(trans_date,'dd/mm/yyyy') transDate, trans_type transType, round(trans_amount,4) transAmount,note");
            strQuery.append(" from trans_anypay where 1 = 1 ");
            if (fromDate != null && !"".equals(fromDate)) {
                strQuery.append(" and trans_date > to_date ( ? ,'dd/mm/yyyy')");
                listParameter.add(fromDate);
            }
            if (toDate != null && !"".equals(toDate)) {
                strQuery.append(" and trans_date <= to_date('").append(toDate).append(" 23:59:59' , 'dd/mm/yyyy hh24:mi:ss')");
            }
            if (transType != null && !"".equals(transType)) {
                strQuery.append(" and trans_type = ? ");
                listParameter.add(Constant.TRANS_TYPE_ANYPAY);
            }
            strQuery.append(" order by trans_date desc ");
            Query query = getSession().createSQLQuery(strQuery.toString()).
                    addScalar("transId",Hibernate.LONG).
                    addScalar("staffName",Hibernate.STRING).
                    addScalar("transType",Hibernate.STRING).
                    addScalar("transDate",Hibernate.STRING).
                    addScalar("transAmount",Hibernate.DOUBLE).
                    addScalar("note",Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(AddSingleAnyPayManagementForm.class));
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }
            List<AddSingleAnyPayManagementForm> list = query.list();
            setTabSession(ADD_SINGLE_ANYPAY_MANAGEMENT_LIST, list);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return pageForward;
    }
    
    /**
     * @desc Ham xem chi tiet giao dich
     * @return 
     */
    public String viewAnyPayDetail(){
        pageForward = ADD_SINGLE_ANYPAY_MANAGEMENT_DETAIL;
        log.info("begin method viewAnyPayDetail of AddSingleAnyPayManagementDAO");
        HttpServletRequest req = getRequest();
        try {
            Long transId = 0L;
            String transIdStr = (String) req.getParameter("transId");
            if (transIdStr != null && !"".equals(transIdStr)){
                transId = Long.valueOf(transIdStr);
            }
            StringBuilder strQuery = new StringBuilder();
            strQuery.append(" select to_char(a.trans_date,'dd/mm/yyyy') transDate,round(a.opening_balance,4) openingBalance,round(a.closing_balance,4) closingBalance,round(a.amount,4) amount,a.message,");
            strQuery.append(" b.owner_name ownerName,b.isdn isdn, b.owner_code collaboratorCode");
            strQuery.append(" from trans_anypay_detail a, account_agent b");
            strQuery.append(" where a.agent_id = b.agent_id ");
            strQuery.append(" and a.trans_id = ? ");
            strQuery.append(" order by trans_date desc ");
            Query query = getSession().createSQLQuery(strQuery.toString()).
                    addScalar("transDate",Hibernate.STRING).
                    addScalar("openingBalance",Hibernate.DOUBLE).
                    addScalar("closingBalance",Hibernate.DOUBLE).
                    addScalar("amount",Hibernate.DOUBLE).
                    addScalar("message",Hibernate.STRING).
                    addScalar("ownerName",Hibernate.STRING).
                    addScalar("isdn",Hibernate.STRING).
                    addScalar("collaboratorCode",Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(AddSingleAnyPayDetailBean.class));
            query.setParameter(0, transId);
            List<AddSingleAnyPayDetailBean> list = query.list();
            req.setAttribute(ADD_SINGLE_ANYPAY_MANAGEMENT_DETAIL,list);
            
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return pageForward;
    }
    
    
}
