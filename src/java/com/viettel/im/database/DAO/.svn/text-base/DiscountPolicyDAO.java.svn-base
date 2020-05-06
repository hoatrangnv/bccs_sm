package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.DiscountPolicyForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.PriceForm;
import com.viettel.im.client.form.StockDepositForm;
import com.viettel.im.client.form.StockModelForm;
import com.viettel.im.common.util.QueryCryptUtils;
import java.util.List;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.StockModel;
import java.util.ArrayList;
import java.util.Iterator;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.AppParamsId;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.StockDeposit;
import com.viettel.im.database.BO.TelecomService;
import java.util.Date;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * author   : Doan Thanh 8
 * date     : 29/09/2010
 * desc     : thong tin ve chinh sach chiet khau
 *
 */
public class DiscountPolicyDAO extends BaseDAOAction {
    private Log log = LogFactory.getLog(DiscountPolicyDAO.class);

    //dinh nghia cac hang so pageForward
    private String pageForward;
    private final String DISCOUNT_POLICY = "discountPolicy";
    
    //bien form
    private DiscountPolicyForm discountPolicyForm = new DiscountPolicyForm();

    public DiscountPolicyForm getDiscountPolicyForm() {
        return discountPolicyForm;
    }

    public void setDiscountPolicyForm(DiscountPolicyForm discountPolicyForm) {
        this.discountPolicyForm = discountPolicyForm;
    }

    //danh sach cac bien request, session
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_DEFAULT_DISCOUNT_POLICY = "listDefaultDiscountPolicy";
    private final String REQUEST_LIST_DISCOUNT_POLICY = "listDiscountPolicy";
    private final String REQUEST_IS_ADD_MODE = "isAddMode"; //flag thiet lap che do them thong tin moi
    private final String REQUEST_IS_EDIT_MODE = "isEditMode"; //flag thiet lap che do sua thong tin da co
    private final String REQUEST_IS_VIEW_MODE = "isViewMode"; //flag thiet lap che do xem thong tin

    /**
     *
     * author   : tamdt1
     * date     : 29/09/2010
     * desc     : chuan bi form danh muc CSCK
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of DiscountPolicyDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //reset form
            this.discountPolicyForm.resetForm();

            //lay danh sach chinh sach CK
            searchDiscountPolicy();
            
            //lay du lieu cho combobox
            getDataForCombobox();

            //
            req.setAttribute(REQUEST_MESSAGE, "");

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method preparePage of DiscountPolicyDAO");
        pageForward = DISCOUNT_POLICY;
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 30/09/2010
     * desc     : chuan bi form them moi discountPolicy
     *
     */
    public String prepareAddDiscountPolicy() throws Exception {
        log.info("Begin method prepareAddDiscountPolicy of DiscountPolicyDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //
            this.discountPolicyForm.resetForm();

            //thiet lap danh sach du lieu cho cac combobox
            getDataForCombobox();

            //xac lap che do chuan bi them stockModel moi
            req.setAttribute(REQUEST_IS_ADD_MODE, true);

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        pageForward = DISCOUNT_POLICY;
        log.info("End method prepareAddDiscountPolicy of DiscountPolicyDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 30/09/2010
     * desc     : chuan bi form sua thong tin discountPolicy
     *
     */
    public String prepareEditDiscountPolicy() throws Exception {
        log.info("Begin method prepareEditDiscountPolicy of GoodsDefDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String discountPolicyId = this.discountPolicyForm.getDiscountPolicyId();
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            AppParams appParams = appParamsDAO.findAppParams(Constant.APP_PARAMS_DISCOUNT_POLICY, discountPolicyId);
            if (appParams != null) {
                //copy du lieu len form
                this.discountPolicyForm.copyDataFromBO(appParams);

                //xac lap che do chuan bi sua thong tin stockModel
                req.setAttribute(REQUEST_IS_EDIT_MODE, true);
            }

            //thiet lap danh sach du lieu cho cac combobox
            getDataForCombobox();

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        pageForward = DISCOUNT_POLICY;
        log.info("End method prepareEditDiscountPolicy of DiscountPolicyDAO");
        return pageForward;

    }

    /**
     *
     * author   : tamdt1
     * date     : 29/09/2010
     * desc     : them discountPolicy moi hoac sua thong tin discountPolicy da co
     *
     */
    public String addOrEditDiscountPolicy() throws Exception {
        log.info("Begin method addOrEditDiscountPolicy of DiscountPolicyDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String discountPolicyId = this.discountPolicyForm.getDiscountPolicyId();
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            AppParams appParams = appParamsDAO.findAppParams(Constant.APP_PARAMS_DISCOUNT_POLICY, discountPolicyId);
            if (appParams == null) {
                //----------------------------------------------------------------
                //them discountPolicy moi
                if (!checkValidDiscountPolicyToAdd()) {
                    //thiet lap danh sach du lieu cho cac combobox
                    getDataForCombobox();
                    
                    //xac lap che do chuan bi them stockModel moi
                    req.setAttribute(REQUEST_IS_ADD_MODE, true);
                    
                    //return
                    pageForward = DISCOUNT_POLICY;
                    log.info("End method addOrEditDiscountPolicy of DiscountPolicyDAO");
                    return pageForward;
                }

                //luu du lieu vao db
                Long tmpDiscountPolicyId = getSequence("APP_PARAMS_SEQ");
                discountPolicyId = tmpDiscountPolicyId.toString();
                this.discountPolicyForm.setDiscountPolicyId(discountPolicyId);

                AppParamsId appParamsId = new AppParamsId();
                appParams = new AppParams();
                appParams.setId(appParamsId);
                this.discountPolicyForm.copyDataToBO(appParams);
                
                //
//                appParams.setStatus(Constant.STATUS_USE.toString());
                getSession().save(appParams);

                //luu log
                List<ActionLogsObj> listActionLogsObj = new ArrayList<ActionLogsObj>();
                listActionLogsObj.add(new ActionLogsObj(null, appParams));
                saveLog(Constant.ACTION_ADD_DISCOUNT_POLICY, "Add new discount policy", listActionLogsObj, tmpDiscountPolicyId);

                //
                

                //hien thi message
                req.setAttribute(REQUEST_MESSAGE, "discountPolicy.addSuccessfull");

            } else {
                //----------------------------------------------------------------
                //sua thong tin discountPolicy da co
                if (!checkValidDiscountPolicyToEdit()) {
                    //thiet lap danh sach du lieu cho cac combobox
                    getDataForCombobox();

                    //xac lap che do chuan bi them stockModel moi
                    req.setAttribute(REQUEST_IS_EDIT_MODE, true);

                    //return
                    pageForward = DISCOUNT_POLICY;
                    log.info("End method addOrEditDiscountPolicy of DiscountPolicyDAO");
                    return pageForward;
                }

                //luu log
                AppParams oldAppParams = new AppParams();
                BeanUtils.copyProperties(oldAppParams, appParams);

                this.discountPolicyForm.copyDataToBO(appParams);
                getSession().update(appParams);

                //luu log
                List<ActionLogsObj> listActionLogsObj = new ArrayList<ActionLogsObj>();
                listActionLogsObj.add(new ActionLogsObj(oldAppParams, appParams));
                saveLog(Constant.ACTION_UPDATE_DISCOUNT_POLICY, "Update discount policy", listActionLogsObj, Long.parseLong(oldAppParams.getId().getCode()));

                //hien thi message
                req.setAttribute(REQUEST_MESSAGE, "discountPolicy.editSuccessfull");

            }

            //reset form
            this.discountPolicyForm.resetForm();

            //xac lap che do hien thi thong tin
            req.setAttribute(REQUEST_IS_VIEW_MODE, true);

            //lay du lieu cho combobox
            getDataForCombobox();

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        //retutn
        pageForward = DISCOUNT_POLICY;
        log.info("End method addOrEditDiscountPolicy of DiscountPolicyDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 30/09/2010
     * desc     : xoa discountPolicy
     *
     */
    public String delDiscountPolicy() throws Exception {
        log.info("Begin method delDiscountPolicy of DiscountPolicyDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            if (!checkValidDiscountPolicyToDel()) {
                //thiet lap danh sach du lieu cho cac combobox
                getDataForCombobox();

                //xac lap che do chuan bi them stockModel moi
                req.setAttribute(REQUEST_IS_ADD_MODE, true);

                //reset form
                this.discountPolicyForm.resetForm();

                //return
                pageForward = DISCOUNT_POLICY;
                log.info("End method delDiscountPolicy of DiscountPolicyDAO");
                return pageForward;
            }

            String discountPolicyId = this.discountPolicyForm.getDiscountPolicyId();
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            AppParams appParams = appParamsDAO.findAppParams(Constant.APP_PARAMS_DISCOUNT_POLICY, discountPolicyId);
            if (appParams != null) {
                //xoa du lieu trong db
                getSession().delete(appParams);
                getSession().flush();

                //ghi log
                List<ActionLogsObj> listActionLogsObj = new ArrayList<ActionLogsObj>();
                listActionLogsObj.add(new ActionLogsObj(appParams, null));
                saveLog(Constant.ACTION_DELETE_DISCOUNT_POLICY, "Delete discount policy", listActionLogsObj, Long.valueOf(appParams.getId().getCode()));

                //reset form
                this.discountPolicyForm.resetForm();

                //
                req.setAttribute(REQUEST_MESSAGE, "discountPolicy.delSuccessfull");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method delDiscountPolicy of DiscountPolicyDAO");
        pageForward = DISCOUNT_POLICY;
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 30/09/2010
     * purpose  : tim kiem danh sach cac discountPolicy thoa man dieu kien tim kiem
     *
     */
    public String searchDiscountPolicy() throws Exception {
        log.info("Begin method searchDiscountPolicy of DiscountPolicyDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            List listParameters = new ArrayList();

            String discountPolicyName = this.discountPolicyForm.getDiscountPolicyName();
            String defaultDiscountPolicyId = this.discountPolicyForm.getDefaultDiscountPolicyId();
            Boolean isDefaultDiscountPolicy = this.discountPolicyForm.getIsDefaultDiscountPolicy();
            Long discountPolicyStatus =  this.discountPolicyForm.getDiscountPolicyStatus();

            StringBuilder strQuery = new StringBuilder("");
            strQuery.append("select a.code as discountPolicyId, ");
            strQuery.append("       a.name as discountPolicyName, ");
            strQuery.append("       a.value as defaultDiscountPolicyId, ");
            strQuery.append("       (select name from app_params where type = ? and code = a.value) as defaultDiscountPolicyName, ");
            strQuery.append("       a.status as discountPolicyStatus ");
            strQuery.append("from   app_params a ");
            strQuery.append("where  a.type = ? ");

            listParameters.add(Constant.APP_PARAMS_DISCOUNT_POLICY);
            listParameters.add(Constant.APP_PARAMS_DISCOUNT_POLICY);

            if (discountPolicyName != null && !discountPolicyName.trim().equals("")) {
                strQuery.append("   and lower(a.name) like ? ");
                listParameters.add("%" + discountPolicyName.trim().toLowerCase() + "%");
            }

            if (isDefaultDiscountPolicy) {
                strQuery.append("   and a.value = ? ");
                listParameters.add(Constant.DISCOUNT_POLICY_DEFAULT);
            } else {
                strQuery.append("   and a.value <> ? ");
                listParameters.add(Constant.DISCOUNT_POLICY_DEFAULT);

                if (defaultDiscountPolicyId != null && !defaultDiscountPolicyId.trim().equals("")) {
                    strQuery.append("   and a.value = ? ");
                    listParameters.add(defaultDiscountPolicyId);
                }
            }

            if (discountPolicyStatus != null) {
                strQuery.append("   and a.status = ? ");
                listParameters.add(discountPolicyStatus.toString());
            }

            strQuery.append("order by nlssort(a.name,'nls_sort=Vietnamese') asc ");

            Query query = getSession().createSQLQuery(strQuery.toString())
                    .addScalar("discountPolicyId", Hibernate.STRING)
                    .addScalar("discountPolicyName", Hibernate.STRING)
                    .addScalar("defaultDiscountPolicyId", Hibernate.STRING)
                    .addScalar("defaultDiscountPolicyName", Hibernate.STRING)
                    .addScalar("discountPolicyStatus", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(DiscountPolicyForm.class));

            for (int i = 0; i < listParameters.size(); i++) {
                query.setParameter(i, listParameters.get(i));
            }

            List listDiscountPolicy = query.list();

            if (listDiscountPolicy != null) {
                req.setAttribute(REQUEST_MESSAGE, "discountPolicy.searchMessage");
                List listParam = new ArrayList();
                listParam.add(listDiscountPolicy.size());
                req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
            }

            //
            req.setAttribute(REQUEST_LIST_DISCOUNT_POLICY, listDiscountPolicy);

            //lay du lieu cho combobox
            getDataForCombobox();

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method searchDiscountPolicy of DiscountPolicyDAO");
        pageForward = DISCOUNT_POLICY;
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 29/09/2010
     * desc     : lay du lieu can thiet cho cac combobox
     *
     */
    private void getDataForCombobox() throws Exception {
        log.info("Begin method getDataForCombobox of DiscountPolicyDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //lay danh sach cac CSCK default
            List<AppParams> listAppParams = new ArrayList<AppParams>();
            String strQuery = ""
                    + "select   new com.viettel.im.database.BO.AppParams(name, id.code) "
                    + "from     AppParams "
                    + "where    type = ? and status = ? and value = ? "
                    + "order by nlssort(name,'nls_sort=Vietnamese') asc ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, Constant.APP_PARAMS_DISCOUNT_POLICY);
            query.setParameter(1, Constant.STATUS_USE.toString());
            query.setParameter(2, Constant.DISCOUNT_POLICY_DEFAULT);
            listAppParams = query.list();
            req.setAttribute(REQUEST_LIST_DEFAULT_DISCOUNT_POLICY, listAppParams);

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        log.info("End method getDataForCombobox of DiscountPolicyDAO");
    }

    /**
     *
     * author   : tamdt1
     * date     : 29/09/2010
     * desc     : kiem tra tinh hop le cua CSCK truoc khi them
     *
     */
    private boolean checkValidDiscountPolicyToAdd() throws Exception {
        log.info("Begin method checkValidDiscountPolicyToAdd of DiscountPolicyDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String discountPolicyName = this.discountPolicyForm.getDiscountPolicyName();
            String defaultDiscountPolicyId = this.discountPolicyForm.getDefaultDiscountPolicyId();
            Boolean isDefaultDiscountPolicy = this.discountPolicyForm.getIsDefaultDiscountPolicy();
            Long discountPolicyStatus =  this.discountPolicyForm.getDiscountPolicyStatus();

            //kiem tra cac truong can thiet
            if(discountPolicyName == null || discountPolicyName.trim().equals("") ||
                    discountPolicyStatus == null) {
                req.setAttribute(REQUEST_MESSAGE, "discountPolicy.err.requiredFieldsEmpty");
                return false;
            }

            if(!isDefaultDiscountPolicy && (defaultDiscountPolicyId == null || defaultDiscountPolicyId.trim().equals(""))) {
                req.setAttribute(REQUEST_MESSAGE, "discountPolicy.err.requiredFieldsEmpty");
                return false;
            }

            //trim cac truong can thiet
            discountPolicyName = discountPolicyName.trim();
            this.discountPolicyForm.setDiscountPolicyName(discountPolicyName);

            //kiem tra tinh trung lap cua ten nhom CK
            StringBuilder sqlBuffer = new StringBuilder("");
            sqlBuffer.append("select    count(*) ");
            sqlBuffer.append("from      app_params ");
            sqlBuffer.append("where     1= 1 ");
            sqlBuffer.append("          and type = ? ");
            sqlBuffer.append("          and lower(name) = ? ");

            Query query = getSession().createSQLQuery(sqlBuffer.toString());
            query.setParameter(0, Constant.APP_PARAMS_DISCOUNT_POLICY);
            query.setParameter(1, discountPolicyName.toLowerCase());

            List list = query.list();
            Long count = Long.valueOf(list.get(0).toString());
            if(list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                //truong hop da ton tai ten CSCK
                req.setAttribute(REQUEST_MESSAGE, "discountPolicy.err.duplicateName");
                return false;
            }

        } catch (Exception ex) {
            //
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());

            return false;
        }

        log.info("End method checkValidDiscountPolicyToAdd of DiscountPolicyDAO");
        return true;
    }

    /**
     *
     * author   : tamdt1
     * date     : 29/09/2010
     * desc     : kiem tra tinh hop le cua CSCK truoc khi sua thong tin
     *
     */
    private boolean checkValidDiscountPolicyToEdit() throws Exception {
        log.info("Begin method checkValidDiscountPolicyToEdit of DiscountPolicyDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String discountPolicyId = this.discountPolicyForm.getDiscountPolicyId();
            String discountPolicyName = this.discountPolicyForm.getDiscountPolicyName();
            String defaultDiscountPolicyId = this.discountPolicyForm.getDefaultDiscountPolicyId();
            Boolean isDefaultDiscountPolicy = this.discountPolicyForm.getIsDefaultDiscountPolicy();
            Long discountPolicyStatus =  this.discountPolicyForm.getDiscountPolicyStatus();

            //kiem tra cac truong can thiet
            if(discountPolicyName == null || discountPolicyName.trim().equals("") ||
                    discountPolicyStatus == null) {
                req.setAttribute(REQUEST_MESSAGE, "discountPolicy.err.requiredFieldsEmpty");
                return false;
            }

            if(!isDefaultDiscountPolicy && (defaultDiscountPolicyId == null || defaultDiscountPolicyId.trim().equals(""))) {
                req.setAttribute(REQUEST_MESSAGE, "discountPolicy.err.requiredFieldsEmpty");
                return false;
            }

            //trim cac truong can thiet
            discountPolicyName = discountPolicyName.trim();
            this.discountPolicyForm.setDiscountPolicyName(discountPolicyName);

            //kiem tra tinh trung lap cua ten nhom CK
            StringBuilder sqlBuffer = new StringBuilder("");
            sqlBuffer.append("select    count(*) ");
            sqlBuffer.append("from      app_params ");
            sqlBuffer.append("where     1= 1 ");
            sqlBuffer.append("          and type = ? ");
            sqlBuffer.append("          and lower(name) = ? ");
            sqlBuffer.append("          and code <> ? ");

            Query query = getSession().createSQLQuery(sqlBuffer.toString());
            query.setParameter(0, Constant.APP_PARAMS_DISCOUNT_POLICY);
            query.setParameter(1, discountPolicyName.toLowerCase());
            query.setParameter(2, discountPolicyId);

            List list = query.list();
            Long count = Long.valueOf(list.get(0).toString());

            if(list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                //truong hop da ton tai ten CSCK
                req.setAttribute(REQUEST_MESSAGE, "discountPolicy.err.duplicateName");
                return false;
            }

            //truong hop chuyen tu enable ve disable hoac -> phai dam bao ko con nhom CK con nao duoc enable
            if (discountPolicyStatus.equals(Constant.STATUS_DELETE)) {
                sqlBuffer = new StringBuilder("");
                sqlBuffer.append("select    count(*) ");
                sqlBuffer.append("from      app_params ");
                sqlBuffer.append("where     1= 1 ");
                sqlBuffer.append("          and type = ? ");
                sqlBuffer.append("          and status = ? ");
                sqlBuffer.append("          and value = ? ");

                query = getSession().createSQLQuery(sqlBuffer.toString());
                query.setParameter(0, Constant.APP_PARAMS_DISCOUNT_POLICY);
                query.setParameter(1, Constant.STATUS_ACTIVE);
                query.setParameter(2, discountPolicyId);

                list = query.list();
                count = Long.valueOf(list.get(0).toString());
                if (list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                    //truong hop con ton tai nhom CK con
                    req.setAttribute(REQUEST_MESSAGE, "discountPolicy.err.existChild");
                    return false;
                }
            }

            //truog hop chuyen tu mac dinh ve ko mac dinh -> phai dam bao ko con con
            if (!isDefaultDiscountPolicy) {
                sqlBuffer = new StringBuilder("");
                sqlBuffer.append("select    count(*) ");
                sqlBuffer.append("from      app_params ");
                sqlBuffer.append("where     1= 1 ");
                sqlBuffer.append("          and type = ? ");
                sqlBuffer.append("          and value = ? ");

                query = getSession().createSQLQuery(sqlBuffer.toString());
                query.setParameter(0, Constant.APP_PARAMS_DISCOUNT_POLICY);
                query.setParameter(1, discountPolicyId);

                list = query.list();
                count = Long.valueOf(list.get(0).toString());
                if (list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                    //truong hop con ton tai nhom CK con
                    req.setAttribute(REQUEST_MESSAGE, "discountPolicy.err.existChild");
                    return false;
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();

        }

        log.info("End method checkValidDiscountPolicyToEdit of DiscountPolicyDAO");
        return true;
    }

    /**
     *
     * author   : tamdt1
     * date     : 29/09/2010
     * desc     : kiem tra tinh hop le cua CSCK truoc khi sua thong tin
     *
     */
    private boolean checkValidDiscountPolicyToDel() throws Exception {
        log.info("Begin method checkValidDiscountPolicyToDel of DiscountPolicyDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String discountPolicyId = this.discountPolicyForm.getDiscountPolicyId();
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            AppParams appParams = appParamsDAO.findAppParams(Constant.APP_PARAMS_DISCOUNT_POLICY, discountPolicyId);
            if (appParams == null) {
                //truong hop khong tim thay CSCK de xoa
                req.setAttribute(REQUEST_MESSAGE, "discountPolicy.err.discountPolicyNotExist");
                return false;
            }

            StringBuilder sqlBuffer = new StringBuilder("");
            sqlBuffer.append("select    count(*) ");
            sqlBuffer.append("from      app_params ");
            sqlBuffer.append("where     1= 1 ");
            sqlBuffer.append("          and type = ? ");
            sqlBuffer.append("          and value = ? ");

            Query query = getSession().createSQLQuery(sqlBuffer.toString());
            query.setParameter(0, Constant.APP_PARAMS_DISCOUNT_POLICY);
            query.setParameter(1, discountPolicyId);

            List list = query.list();
            Long count = Long.valueOf(list.get(0).toString());
            if (list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                //truong hop con ton tai nhom CK con
                req.setAttribute(REQUEST_MESSAGE, "discountPolicy.err.existChild");
                return false;
            }
            
            
            
            
            
            //Check rang buoc khi xoa discount polilcy 
            
            //shop
            sqlBuffer = new StringBuilder("");
            sqlBuffer.append("select    count(*) ");
            sqlBuffer.append("from      shop ");
            sqlBuffer.append("where     1= 1 ");
            sqlBuffer.append("          and discount_policy = ? ");

            query = getSession().createSQLQuery(sqlBuffer.toString());
            query.setParameter(0, discountPolicyId);

            list = query.list();
            count = Long.valueOf(list.get(0).toString());
            
            if (list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                //truong hop con ton tai nhom CK con
                req.setAttribute(REQUEST_MESSAGE, "Error. Discount policy is assigning to shop!");
                return false;
            }
            
            //staff
            sqlBuffer = new StringBuilder("");
            sqlBuffer.append("select    count(*) ");
            sqlBuffer.append("from      staff ");
            sqlBuffer.append("where     1= 1 ");
            sqlBuffer.append("          and discount_policy = ? ");

            query = getSession().createSQLQuery(sqlBuffer.toString());
            query.setParameter(0, discountPolicyId);

            list = query.list();
            count = Long.valueOf(list.get(0).toString());
            
            if (list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                //truong hop con ton tai nhom CK con
                req.setAttribute(REQUEST_MESSAGE, "Error. Discount policy is assigning to staff!");
                return false;
            }
            
            //channel_type
            sqlBuffer = new StringBuilder("");
            sqlBuffer.append("select    count(*) ");
            sqlBuffer.append("from      channel_type ");
            sqlBuffer.append("where     1= 1 ");
            sqlBuffer.append("          and DISCOUNT_POLICY_DEFAULT = ? ");

            query = getSession().createSQLQuery(sqlBuffer.toString());
            query.setParameter(0, discountPolicyId);

            list = query.list();
            count = Long.valueOf(list.get(0).toString());
            
            if (list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                //truong hop con ton tai nhom CK con
                req.setAttribute(REQUEST_MESSAGE, "Error. Discount policy is assigning to channel type!");
                return false;
            }
            

        } catch (Exception ex) {
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
            //
            return false;
        }

        log.info("End method checkValidDiscountPolicyToDel of DiscountPolicyDAO");
        return true;
    }
    
}
