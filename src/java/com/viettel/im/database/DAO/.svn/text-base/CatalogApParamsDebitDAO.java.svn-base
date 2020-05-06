
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.CatalogApParamsDebitForm;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.AppParams;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author KienLT10
 */
public class CatalogApParamsDebitDAO extends BaseDAOAction {

    /**
     * PROPERTIES AND CONSTANT
     */
    private CatalogApParamsDebitForm inputForm;
    private List<AppParams> lstParamType;

    /**
     * GETTER AND SETTER
     */
    public CatalogApParamsDebitForm getInputForm() {
        return inputForm;
    }

    public void setInputForm(CatalogApParamsDebitForm inputForm) {
        this.inputForm = inputForm;
    }

    public List<AppParams> getLstParamType() {
        return lstParamType;
    }

    public void setLstParamType(List<AppParams> lstParamType) {
        this.lstParamType = lstParamType;
    }

    /**
     * CONTROLLER METHOD (PUBLIC)
     */
    public String prepare() {
        initListParams();
        return "prepare";
    }

    public String search() {
        List listResult = search(getSession(), inputForm);
        getRequest().getSession().setAttribute("listResult", listResult);
        return "listResult";
    }

    public String insert() {
        Session session = getSession();
        if (isExists(session, inputForm)) {
            getRequest().setAttribute("returnMessage", "msg.693.16");
            String paramType = inputForm.getType();
            inputForm = new CatalogApParamsDebitForm();
            inputForm.setType(paramType);
        } else {
            int result = insert(session, inputForm);
            if (result > 0) {
                session.getTransaction().commit();
                getRequest().setAttribute("returnMessage", "msg.693.7");
                getRequest().setAttribute("isSuccess", true);
            } else {
                getRequest().setAttribute("returnMessage", "msg.693.8");
                String paramType = inputForm.getType();
                inputForm = new CatalogApParamsDebitForm();
                inputForm.setType(paramType);
            }
        }
        search();
        return "listResult";
    }

    public String preUpdate() {
        List<CatalogApParamsDebitForm> lstResult = search(getSession(), inputForm);
        if (!lstResult.isEmpty()) {
            inputForm = lstResult.get(0);
            getRequest().setAttribute("isEdit", true);
        }
        initListParams();
        return "query";
    }

    public String update() {
        Session session = getSession();
        if (checkDayDebitActive(session, inputForm.getCode().trim())) {
            getRequest().setAttribute("returnMessage", "err.ngay_cau_hinh_con_hieu_luc");
            return "listResult";
        }
        if (isExists(session, inputForm)) {
            getRequest().setAttribute("returnMessage", "msg.693.16");
            String paramType = inputForm.getType();
            inputForm = new CatalogApParamsDebitForm();
            inputForm.setType(paramType);
        } else {
            int result = update(session, inputForm);
            if (result > 0) {
                getSession().getTransaction().commit();
                getRequest().setAttribute("returnMessage", "msg.693.9");
                getRequest().setAttribute("isSuccess", true);
            } else {
                String paramType = inputForm.getType();
                inputForm = new CatalogApParamsDebitForm();
                inputForm.setType(paramType);
                getRequest().setAttribute("returnMessage", "msg.693.10");
            }
        }
        search();
        return "listResult";
    }

    public String delete() {
        Session session = getSession();
        if(checkDayDebitActive(session, inputForm.getCode().trim())){
            getRequest().setAttribute("returnMessage", "msg.693.12");
            return "listResult";
        }
        int result = delete(session, inputForm);
        if (result > 0) {
            session.getTransaction().commit();
            getRequest().setAttribute("returnMessage", "msg.693.11");
            getRequest().setAttribute("isSuccess", true);
        } else {
            getRequest().setAttribute("returnMessage", "msg.693.12");
        }
        String paramType = inputForm.getType();
        inputForm = new CatalogApParamsDebitForm();
        inputForm.setType(paramType);
        search();
        return "listResult";
    }

    /**
     * BUSSINESS METHOD (PRIVATE)
     */
    private void initListParams() {
        lstParamType = new ArrayList();
        List<String> listParamTypeValid = getListParamsType();
        try {
            for (String paramType : listParamTypeValid) {
                AppParams appParams = new AppParams();
                appParams.setCode(paramType);
                appParams.setName(getMessage("lbl.param_type_" + paramType));
                lstParamType.add(appParams);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            lstParamType = new ArrayList();
        }
    }

    private List<CatalogApParamsDebitForm> search(Session session, CatalogApParamsDebitForm inputForm) {
        List<CatalogApParamsDebitForm> lstResult = new ArrayList();
        try {
            StringBuilder strQuery = new StringBuilder();
            strQuery.append("SELECT type, name, code, status FROM app_params WHERE lower(type) = lower(?)");

            List lstParams = new ArrayList();
            lstParams.add(inputForm.getType());
            if (StringUtils.validString(inputForm.getName())) {
                strQuery.append(" AND lower(name) = lower(?) ");
                lstParams.add(inputForm.getName().trim());
            }
            if (StringUtils.validString(inputForm.getCode())) {
                strQuery.append(" AND lower(code) = lower(?) ");
                lstParams.add(inputForm.getCode().trim());
            }
            if (inputForm.getStatus() != null) {
                strQuery.append(" AND status = ?");
                lstParams.add(inputForm.getStatus());
            }
            strQuery.append(" AND type in (:listParamType) ORDER BY code");
            Query query = session.createSQLQuery(strQuery.toString()).addScalar("type", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("code", Hibernate.STRING).addScalar("status", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(CatalogApParamsDebitForm.class));
            for (int i = 0; i < lstParams.size(); i++) {
                query.setParameter(i, lstParams.get(i));
            }
            List<String> lstParamsType = getListParamsType();
            query.setParameterList("listParamType", lstParamsType);
            lstResult = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstResult;
    }

    private int insert(Session session, CatalogApParamsDebitForm inputForm) {
        int result = 0;
        try {
            if (isDataValid(inputForm)) {
                StringBuilder strQuery = new StringBuilder();
                strQuery.append("INSERT INTO app_params(type, code, name, status)");
                strQuery.append(" VALUES (:type, :code, :name, 1)");
                Query query = session.createSQLQuery(strQuery.toString());
                query.setString("type", inputForm.getType().trim());
                query.setString("code", getNextCodeValue(session, inputForm.getType()));
                query.setString("name", inputForm.getName().trim());
                result = query.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private int update(Session session, CatalogApParamsDebitForm inputForm) {
        int result = 0;
        try {
            if (isDataValid(inputForm)) {
                StringBuilder strQuery = new StringBuilder();
                strQuery.append("UPDATE app_params ");
                strQuery.append(" SET name = :name, status = :status ");
                strQuery.append(" WHERE type = :type and code = :code");
                Query query = session.createSQLQuery(strQuery.toString());
                query.setString("type", inputForm.getType().trim());
                query.setString("code", inputForm.getCode().trim());
                query.setString("name", inputForm.getName().trim());
                query.setLong("status", inputForm.getStatus());
                result = query.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private int delete(Session session, CatalogApParamsDebitForm inputForm) {
        int result = 0;
        try {
            if (isDataValid(inputForm)) {
                StringBuilder strQuery = new StringBuilder();
                strQuery.append("UPDATE app_params ");
                strQuery.append(" SET status = :status ");
                strQuery.append(" WHERE type = :type and code = :code");
                Query query = session.createSQLQuery(strQuery.toString());
                query.setString("type", inputForm.getType().trim());
                query.setString("code", inputForm.getCode().trim());
                query.setLong("status", 0L);
                result = query.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private boolean isExists(Session session, CatalogApParamsDebitForm inputForm) {
        try {
            StringBuilder strQuery = new StringBuilder();
            strQuery.append("SELECT code FROM app_params WHERE type = ?");

            List lstParams = new ArrayList();
            lstParams.add(inputForm.getType());
            if (StringUtils.validString(inputForm.getCode())) {//Neu update thi kiem tra them code
                strQuery.append(" AND code <> ?");
                lstParams.add(inputForm.getCode().trim());
            }
            strQuery.append(" AND lower(name) = lower(?) ");
            lstParams.add(inputForm.getName().trim());
            strQuery.append(" AND type in (:listParamType)");
            Query query = session.createSQLQuery(strQuery.toString()).addScalar("code", Hibernate.STRING);
            for (int i = 0; i < lstParams.size(); i++) {
                query.setParameter(i, lstParams.get(i));
            }
            List<String> lstParamsType = getListParamsType();
            query.setParameterList("listParamType", lstParamsType);
            if (query.list().isEmpty()) {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private List<String> getListParamsType() {
        List<String> lstResult = new ArrayList();
        String strListParam = ResourceBundleUtils.getResource("listApParamTypeDebit");
        String[] arrListParams = strListParam.split(",");
        for (String paramType : arrListParams) {
            lstResult.add(paramType.trim());
        }
        return lstResult;
    }

    private boolean isDataValid(CatalogApParamsDebitForm inputForm) {
        boolean flag = false;
        List<String> lstParamValid = getListParamsType();
        for (String paramType : lstParamValid) {
            if (paramType.equals(inputForm.getType())) {
                flag = true;
                break;
            }
        }
        //Chuan hoa status
        if (inputForm.getStatus() != null) {
            if (inputForm.getStatus() != 0 && inputForm.getStatus() != 1) {
                inputForm.setStatus(0L);
            }
        }
        return flag;
    }

    private String getNextCodeValue(Session session, String paramType) {
        Long result = 1L;
        try {
            StringBuilder strQuery = new StringBuilder();
            strQuery.append("SELECT MAX(to_number(code) + 1) code FROM app_params WHERE type = ?");
            Query query = session.createSQLQuery(strQuery.toString()).addScalar("code", Hibernate.LONG);
            query.setParameter(0, paramType);
            result = (Long) query.list().get(0);
            if (result == null) {
                result = 1L;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result.toString();
    }

    //bo sung ham phan trang
    public String preparePage() {
        
        return "listResult";
    }
    //end thinhph2
    
    public boolean checkDayDebitActive(Session session, String debitDayCode) {
        String sql = "select 1 from debit_day_type where debit_day_type = ? and status = 1 and "
                + " ((trunc(sta_date) <= trunc(sysdate) and trunc(end_date) >= trunc(sysdate) ) or (trunc(sta_date) <= trunc(sysdate) and end_date is null) ) ";
        
        try{
            Query query = session.createSQLQuery(sql);
            query.setParameter(0, debitDayCode);
            List list = query.list();
            
            if(!list.isEmpty()){
                return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
