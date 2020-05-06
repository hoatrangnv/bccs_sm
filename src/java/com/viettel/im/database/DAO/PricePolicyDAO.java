package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.PricePolicyForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
import java.util.ArrayList;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.AppParamsId;
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
public class PricePolicyDAO extends BaseDAOAction {
    private Log log = LogFactory.getLog(PricePolicyDAO.class);

    //dinh nghia cac hang so pageForward
    private String pageForward;
    private final String PRICE_POLICY = "pricePolicy";

    //bien form
    private PricePolicyForm pricePolicyForm = new PricePolicyForm();

    public PricePolicyForm getPricePolicyForm() {
        return pricePolicyForm;
    }

    public void setPricePolicyForm(PricePolicyForm pricePolicyForm) {
        this.pricePolicyForm = pricePolicyForm;
    }

    //danh sach cac bien request, session
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_DEFAULT_PRICE_POLICY = "listDefaultPricePolicy";
    private final String REQUEST_LIST_PRICE_POLICY = "listPricePolicy";
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
        log.info("Begin method preparePage of PricePolicyDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //reset form
            this.pricePolicyForm.resetForm();

            //lay danh sach chinh sach CK
            searchPricePolicy();

            //lay du lieu cho combobox
            getDataForCombobox();

            //
            req.setAttribute(REQUEST_MESSAGE, "");

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method preparePage of PricePolicyDAO");
        pageForward = PRICE_POLICY;
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 30/09/2010
     * desc     : chuan bi form them moi pricePolicy
     *
     */
    public String prepareAddPricePolicy() throws Exception {
        log.info("Begin method prepareAddPricePolicy of PricePolicyDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //
            this.pricePolicyForm.resetForm();

            //thiet lap danh sach du lieu cho cac combobox
            getDataForCombobox();

            //xac lap che do chuan bi them stockModel moi
            req.setAttribute(REQUEST_IS_ADD_MODE, true);

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        pageForward = PRICE_POLICY;
        log.info("End method prepareAddPricePolicy of PricePolicyDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 30/09/2010
     * desc     : chuan bi form sua thong tin pricePolicy
     *
     */
    public String prepareEditPricePolicy() throws Exception {
        log.info("Begin method prepareEditPricePolicy of GoodsDefDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String pricePolicyId = this.pricePolicyForm.getPricePolicyId();
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            AppParams appParams = appParamsDAO.findAppParams(Constant.APP_PARAMS_PRICE_POLICY, pricePolicyId);
            if (appParams != null) {
                //copy du lieu len form
                this.pricePolicyForm.copyDataFromBO(appParams);

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

        pageForward = PRICE_POLICY;
        log.info("End method prepareEditPricePolicy of PricePolicyDAO");
        return pageForward;

    }

    /**
     *
     * author   : tamdt1
     * date     : 29/09/2010
     * desc     : them pricePolicy moi hoac sua thong tin pricePolicy da co
     *
     */
    public String addOrEditPricePolicy() throws Exception {
        log.info("Begin method addOrEditPricePolicy of PricePolicyDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String pricePolicyId = this.pricePolicyForm.getPricePolicyId();
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            AppParams appParams = appParamsDAO.findAppParams(Constant.APP_PARAMS_PRICE_POLICY, pricePolicyId);
            if (appParams == null) {
                //----------------------------------------------------------------
                //them pricePolicy moi
                if (!checkValidPricePolicyToAdd()) {
                    //thiet lap danh sach du lieu cho cac combobox
                    getDataForCombobox();

                    //xac lap che do chuan bi them stockModel moi
                    req.setAttribute(REQUEST_IS_ADD_MODE, true);

                    //return
                    pageForward = PRICE_POLICY;
                    log.info("End method addOrEditPricePolicy of PricePolicyDAO");
                    return pageForward;
                }

                //luu du lieu vao db
                Long tmpPricePolicyId = getSequence("APP_PARAMS_SEQ");
                pricePolicyId = tmpPricePolicyId.toString();
                this.pricePolicyForm.setPricePolicyId(pricePolicyId);

                AppParamsId appParamsId = new AppParamsId();
                appParams = new AppParams();
                appParams.setId(appParamsId);
                this.pricePolicyForm.copyDataToBO(appParams);

                //
//                appParams.setStatus(Constant.STATUS_USE.toString());
                getSession().save(appParams);

                //luu log
                List<ActionLogsObj> listActionLogsObj = new ArrayList<ActionLogsObj>();
                listActionLogsObj.add(new ActionLogsObj(null, appParams));
                saveLog(Constant.ACTION_ADD_PRICE_POLICY, "Add new price policy", listActionLogsObj, tmpPricePolicyId);

                //


                //hien thi message
                req.setAttribute(REQUEST_MESSAGE, "pricePolicy.addSuccessful");

            } else {
                //----------------------------------------------------------------
                //sua thong tin pricePolicy da co
                if (!checkValidPricePolicyToEdit()) {
                    //thiet lap danh sach du lieu cho cac combobox
                    getDataForCombobox();

                    //xac lap che do chuan bi them stockModel moi
                    req.setAttribute(REQUEST_IS_EDIT_MODE, true);

                    //return
                    pageForward = PRICE_POLICY;
                    log.info("End method addOrEditPricePolicy of PricePolicyDAO");
                    return pageForward;
                }

                //luu log
                AppParams oldAppParams = new AppParams();
                BeanUtils.copyProperties(oldAppParams, appParams);

                this.pricePolicyForm.copyDataToBO(appParams);
                getSession().update(appParams);

                //luu log
                List<ActionLogsObj> listActionLogsObj = new ArrayList<ActionLogsObj>();
                listActionLogsObj.add(new ActionLogsObj(oldAppParams, appParams));
                saveLog(Constant.ACTION_UPDATE_PRICE_POLICY, "Update price policy", listActionLogsObj, Long.parseLong(oldAppParams.getId().getCode()));

                //hien thi message
                req.setAttribute(REQUEST_MESSAGE, "pricePolicy.editSuccessful");

            }

            //reset form
            this.pricePolicyForm.resetForm();

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
        pageForward = PRICE_POLICY;
        log.info("End method addOrEditPricePolicy of PricePolicyDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 30/09/2010
     * desc     : xoa pricePolicy
     *
     */
    public String delPricePolicy() throws Exception {
        log.info("Begin method delPricePolicy of PricePolicyDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            if (!checkValidPricePolicyToDel()) {
                //thiet lap danh sach du lieu cho cac combobox
                getDataForCombobox();

                //xac lap che do chuan bi them stockModel moi
                req.setAttribute(REQUEST_IS_ADD_MODE, true);

                //reset form
                this.pricePolicyForm.resetForm();

                //return
                pageForward = PRICE_POLICY;
                log.info("End method delPricePolicy of PricePolicyDAO");
                return pageForward;
            }

            String pricePolicyId = this.pricePolicyForm.getPricePolicyId();
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            AppParams appParams = appParamsDAO.findAppParams(Constant.APP_PARAMS_PRICE_POLICY, pricePolicyId);
            if (appParams != null) {
                //xoa du lieu trong db
                getSession().delete(appParams);
                getSession().flush();

                //ghi log
                List<ActionLogsObj> listActionLogsObj = new ArrayList<ActionLogsObj>();
                listActionLogsObj.add(new ActionLogsObj(appParams, null));
                saveLog(Constant.ACTION_DELETE_PRICE_POLICY, "Delete price policy", listActionLogsObj, Long.valueOf(appParams.getId().getCode()));

                //reset form
                this.pricePolicyForm.resetForm();

                //
                req.setAttribute(REQUEST_MESSAGE, "pricePolicy.delSuccessful");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method delPricePolicy of PricePolicyDAO");
        pageForward = PRICE_POLICY;
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 30/09/2010
     * purpose  : tim kiem danh sach cac pricePolicy thoa man dieu kien tim kiem
     *
     */
    public String searchPricePolicy() throws Exception {
        log.info("Begin method searchPricePolicy of PricePolicyDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            List listParameters = new ArrayList();

            String pricePolicyName = this.pricePolicyForm.getPricePolicyName();
            String defaultPricePolicyId = this.pricePolicyForm.getDefaultPricePolicyId();
            Boolean isDefaultPricePolicy = this.pricePolicyForm.getIsDefaultPricePolicy();
            Long pricePolicyStatus =  this.pricePolicyForm.getPricePolicyStatus();

            StringBuilder strQuery = new StringBuilder("");
            strQuery.append("select a.code as pricePolicyId, ");
            strQuery.append("       a.name as pricePolicyName, ");
            strQuery.append("       a.value as defaultPricePolicyId, ");
            strQuery.append("       (select name from app_params where type = ? and code = a.value) as defaultPricePolicyName, ");
            strQuery.append("       a.status as pricePolicyStatus ");
            strQuery.append("from   app_params a ");
            strQuery.append("where  a.type = ? ");

            listParameters.add(Constant.APP_PARAMS_PRICE_POLICY);
            listParameters.add(Constant.APP_PARAMS_PRICE_POLICY);

            if (pricePolicyName != null && !pricePolicyName.trim().equals("")) {
                strQuery.append("   and lower(a.name) like ? ");
                listParameters.add("%" + pricePolicyName.trim().toLowerCase() + "%");
            }

            if (isDefaultPricePolicy) {
                strQuery.append("   and a.value = ? ");
                listParameters.add(Constant.PRICE_POLICY_DEFAULT);
            } else {
                strQuery.append("   and a.value <> ? ");
                listParameters.add(Constant.PRICE_POLICY_DEFAULT);

                if (defaultPricePolicyId != null && !defaultPricePolicyId.trim().equals("")) {
                    strQuery.append("   and a.value = ? ");
                    listParameters.add(defaultPricePolicyId);
                }
            }

            if (pricePolicyStatus != null) {
                strQuery.append("   and a.status = ? ");
                listParameters.add(pricePolicyStatus.toString());
            }

            strQuery.append("order by nlssort(a.name,'nls_sort=Vietnamese') asc ");

            Query query = getSession().createSQLQuery(strQuery.toString())
                    .addScalar("pricePolicyId", Hibernate.STRING)
                    .addScalar("pricePolicyName", Hibernate.STRING)
                    .addScalar("defaultPricePolicyId", Hibernate.STRING)
                    .addScalar("defaultPricePolicyName", Hibernate.STRING)
                    .addScalar("pricePolicyStatus", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(PricePolicyForm.class));

            for (int i = 0; i < listParameters.size(); i++) {
                query.setParameter(i, listParameters.get(i));
            }

            List listPricePolicy = query.list();

            if (listPricePolicy != null) {
                req.setAttribute(REQUEST_MESSAGE, "pricePolicy.searchMessage");
                List listParam = new ArrayList();
                listParam.add(listPricePolicy.size());
                req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
            }

            //
            req.setAttribute(REQUEST_LIST_PRICE_POLICY, listPricePolicy);

            //lay du lieu cho combobox
            getDataForCombobox();

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method searchPricePolicy of PricePolicyDAO");
        pageForward = PRICE_POLICY;
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
        log.info("Begin method getDataForCombobox of PricePolicyDAO ...");

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
            query.setParameter(0, Constant.APP_PARAMS_PRICE_POLICY);
            query.setParameter(1, Constant.STATUS_USE.toString());
            query.setParameter(2, Constant.PRICE_POLICY_DEFAULT);
            listAppParams = query.list();
            req.setAttribute(REQUEST_LIST_DEFAULT_PRICE_POLICY, listAppParams);

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        log.info("End method getDataForCombobox of PricePolicyDAO");
    }

    /**
     *
     * author   : tamdt1
     * date     : 29/09/2010
     * desc     : kiem tra tinh hop le cua CSCK truoc khi them
     *
     */
    private boolean checkValidPricePolicyToAdd() throws Exception {
        log.info("Begin method checkValidPricePolicyToAdd of PricePolicyDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String pricePolicyName = this.pricePolicyForm.getPricePolicyName();
            String defaultPricePolicyId = this.pricePolicyForm.getDefaultPricePolicyId();
            Boolean isDefaultPricePolicy = this.pricePolicyForm.getIsDefaultPricePolicy();
            Long pricePolicyStatus =  this.pricePolicyForm.getPricePolicyStatus();

            //kiem tra cac truong can thiet
            if(pricePolicyName == null || pricePolicyName.trim().equals("") ||
                    pricePolicyStatus == null) {
                req.setAttribute(REQUEST_MESSAGE, "pricePolicy.err.requiredFieldsEmpty");
                return false;
            }

            if(!isDefaultPricePolicy && (defaultPricePolicyId == null || defaultPricePolicyId.trim().equals(""))) {
                req.setAttribute(REQUEST_MESSAGE, "pricePolicy.err.requiredFieldsEmpty");
                return false;
            }

            //trim cac truong can thiet
            pricePolicyName = pricePolicyName.trim();
            this.pricePolicyForm.setPricePolicyName(pricePolicyName);

            //kiem tra tinh trung lap cua ten nhom CK
            StringBuilder sqlBuffer = new StringBuilder("");
            sqlBuffer.append("select    count(*) ");
            sqlBuffer.append("from      app_params ");
            sqlBuffer.append("where     1= 1 ");
            sqlBuffer.append("          and type = ? ");
            sqlBuffer.append("          and lower(name) = ? ");

            Query query = getSession().createSQLQuery(sqlBuffer.toString());
            query.setParameter(0, Constant.APP_PARAMS_PRICE_POLICY);
            query.setParameter(1, pricePolicyName.toLowerCase());

            List list = query.list();
            Long count = Long.valueOf(list.get(0).toString());
            if(list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                //truong hop da ton tai ten CSCK
                req.setAttribute(REQUEST_MESSAGE, "pricePolicy.err.duplicateName");
                return false;
            }

        } catch (Exception ex) {
            //
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());

            return false;
        }

        log.info("End method checkValidPricePolicyToAdd of PricePolicyDAO");
        return true;
    }

    /**
     *
     * author   : tamdt1
     * date     : 29/09/2010
     * desc     : kiem tra tinh hop le cua CSCK truoc khi sua thong tin
     *
     */
    private boolean checkValidPricePolicyToEdit() throws Exception {
        log.info("Begin method checkValidPricePolicyToEdit of PricePolicyDAO ...");

        HttpServletRequest req = getRequest();

        try {
            String pricePolicyId = this.pricePolicyForm.getPricePolicyId();
            String pricePolicyName = this.pricePolicyForm.getPricePolicyName();
            String defaultPricePolicyId = this.pricePolicyForm.getDefaultPricePolicyId();
            Boolean isDefaultPricePolicy = this.pricePolicyForm.getIsDefaultPricePolicy();
            Long pricePolicyStatus =  this.pricePolicyForm.getPricePolicyStatus();

            //kiem tra cac truong can thiet
            if(pricePolicyName == null || pricePolicyName.trim().equals("") ||
                    pricePolicyStatus == null) {
                req.setAttribute(REQUEST_MESSAGE, "pricePolicy.err.requiredFieldsEmpty");
                return false;
            }

            if(!isDefaultPricePolicy && (defaultPricePolicyId == null || defaultPricePolicyId.trim().equals(""))) {
                req.setAttribute(REQUEST_MESSAGE, "pricePolicy.err.requiredFieldsEmpty");
                return false;
            }

            //trim cac truong can thiet
            pricePolicyName = pricePolicyName.trim();
            this.pricePolicyForm.setPricePolicyName(pricePolicyName);

            //kiem tra tinh trung lap cua ten nhom CK
            StringBuilder sqlBuffer = new StringBuilder("");
            sqlBuffer.append("select    count(*) ");
            sqlBuffer.append("from      app_params ");
            sqlBuffer.append("where     1= 1 ");
            sqlBuffer.append("          and type = ? ");
            sqlBuffer.append("          and lower(name) = ? ");
            sqlBuffer.append("          and code <> ? ");

            Query query = getSession().createSQLQuery(sqlBuffer.toString());
            query.setParameter(0, Constant.APP_PARAMS_PRICE_POLICY);
            query.setParameter(1, pricePolicyName.toLowerCase());
            query.setParameter(2, pricePolicyId);

            List list = query.list();
            Long count = Long.valueOf(list.get(0).toString());

            if(list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                //truong hop da ton tai ten CSCK
                req.setAttribute(REQUEST_MESSAGE, "pricePolicy.err.duplicateName");
                return false;
            }

            //truong hop chuyen tu enable ve disable hoac -> phai dam bao ko con nhom CK con nao duoc enable
            if (pricePolicyStatus.equals(Constant.STATUS_DELETE)) {
                sqlBuffer = new StringBuilder("");
                sqlBuffer.append("select    count(*) ");
                sqlBuffer.append("from      app_params ");
                sqlBuffer.append("where     1= 1 ");
                sqlBuffer.append("          and type = ? ");
                sqlBuffer.append("          and status = ? ");
                sqlBuffer.append("          and value = ? ");

                query = getSession().createSQLQuery(sqlBuffer.toString());
                query.setParameter(0, Constant.APP_PARAMS_PRICE_POLICY);
                query.setParameter(1, Constant.STATUS_ACTIVE);
                query.setParameter(2, pricePolicyId);

                list = query.list();
                count = Long.valueOf(list.get(0).toString());
                if (list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                    //truong hop con ton tai nhom CK con
                    req.setAttribute(REQUEST_MESSAGE, "pricePolicy.err.existChild");
                    return false;
                }
            }

            //truog hop chuyen tu mac dinh ve ko mac dinh -> phai dam bao ko con con
            if (!isDefaultPricePolicy) {
                sqlBuffer = new StringBuilder("");
                sqlBuffer.append("select    count(*) ");
                sqlBuffer.append("from      app_params ");
                sqlBuffer.append("where     1= 1 ");
                sqlBuffer.append("          and type = ? ");
                sqlBuffer.append("          and value = ? ");

                query = getSession().createSQLQuery(sqlBuffer.toString());
                query.setParameter(0, Constant.APP_PARAMS_PRICE_POLICY);
                query.setParameter(1, pricePolicyId);

                list = query.list();
                count = Long.valueOf(list.get(0).toString());
                if (list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                    //truong hop con ton tai nhom CK con
                    req.setAttribute(REQUEST_MESSAGE, "pricePolicy.err.existChild");
                    return false;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        log.info("End method checkValidPricePolicyToEdit of PricePolicyDAO");
        return true;
    }

    /**
     *
     * author   : tamdt1
     * date     : 29/09/2010
     * desc     : kiem tra tinh hop le cua CSCK truoc khi sua thong tin
     *
     */
    private boolean checkValidPricePolicyToDel() throws Exception {
        log.info("Begin method checkValidPricePolicyToDel of PricePolicyDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String pricePolicyId = this.pricePolicyForm.getPricePolicyId();
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            AppParams appParams = appParamsDAO.findAppParams(Constant.APP_PARAMS_PRICE_POLICY, pricePolicyId);
            if (appParams == null) {
                //truong hop khong tim thay CSCK de xoa
                req.setAttribute(REQUEST_MESSAGE, "pricePolicy.err.pricePolicyNotExist");
                return false;
            }

            StringBuilder sqlBuffer = new StringBuilder("");
            sqlBuffer.append("select    count(*) ");
            sqlBuffer.append("from      app_params ");
            sqlBuffer.append("where     1= 1 ");
            sqlBuffer.append("          and type = ? ");
            sqlBuffer.append("          and value = ? ");

            Query query = getSession().createSQLQuery(sqlBuffer.toString());
            query.setParameter(0, Constant.APP_PARAMS_PRICE_POLICY);
            query.setParameter(1, pricePolicyId);

            List list = query.list();
            Long count = Long.valueOf(list.get(0).toString());
            if (list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                //truong hop con ton tai nhom CK con
                req.setAttribute(REQUEST_MESSAGE, "pricePolicy.err.existChild");
                return false;
            }

            //Check rang buoc khi xoa price polilcy

            //shop
            sqlBuffer = new StringBuilder("");
            sqlBuffer.append("select    count(*) ");
            sqlBuffer.append("from      shop ");
            sqlBuffer.append("where     1= 1 ");
            sqlBuffer.append("          and price_policy = ? ");

            query = getSession().createSQLQuery(sqlBuffer.toString());
            query.setParameter(0, pricePolicyId);

            list = query.list();
            count = Long.valueOf(list.get(0).toString());

            if (list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                //truong hop con ton tai nhom CK con
                req.setAttribute(REQUEST_MESSAGE, "Error. Price policy is assigning to shop!");
                return false;
            }

            //staff
            sqlBuffer = new StringBuilder("");
            sqlBuffer.append("select    count(*) ");
            sqlBuffer.append("from      staff ");
            sqlBuffer.append("where     1= 1 ");
            sqlBuffer.append("          and price_policy = ? ");

            query = getSession().createSQLQuery(sqlBuffer.toString());
            query.setParameter(0, pricePolicyId);

            list = query.list();
            count = Long.valueOf(list.get(0).toString());

            if (list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                //truong hop con ton tai nhom CK con
                req.setAttribute(REQUEST_MESSAGE, "Error. Price policy is assigning to staff!");
                return false;
            }

            //channel_type
            sqlBuffer = new StringBuilder("");
            sqlBuffer.append("select    count(*) ");
            sqlBuffer.append("from      channel_type ");
            sqlBuffer.append("where     1= 1 ");
            sqlBuffer.append("          and PRICE_POLICY_DEFAULT = ? ");

            query = getSession().createSQLQuery(sqlBuffer.toString());
            query.setParameter(0, pricePolicyId);

            list = query.list();
            count = Long.valueOf(list.get(0).toString());

            if (list != null && !list.isEmpty() && count.compareTo(0L) > 0) {
                //truong hop con ton tai nhom CK con
                req.setAttribute(REQUEST_MESSAGE, "Error. Price policy is assigning to channel type!");
                return false;
            }


        } catch (Exception ex) {
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
            //
            return false;
        }

        log.info("End method checkValidPricePolicyToDel of PricePolicyDAO");
        return true;
    }

}
