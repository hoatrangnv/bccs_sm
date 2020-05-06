package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.UserToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.SaleChannelTypeForm;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.ChannelType;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;

/**
 *
 * @author NamNX
 * 13/06/2009
 *
 *
 */
public class SaleChannelTypeDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(SaleChannelTypeDAO.class);
    private String pageForward;
    private SaleChannelTypeForm saleChannelTypeForm = new SaleChannelTypeForm();

    public SaleChannelTypeForm getSaleChannelTypeForm() {
        return saleChannelTypeForm;
    }

    public void setSaleChannelTypeForm(SaleChannelTypeForm saleChannelTypeForm) {
        this.saleChannelTypeForm = saleChannelTypeForm;
    }
    public static final String ADD_NEW_SALE_CHANNEL_TYPE = "addNewSaleChannelType";
    public static final int SEARCH_RESULT_LIMIT = 100;

    public void save(ChannelType transientInstance) {
        log.debug("saving ChannelType  instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ChannelType persistentInstance) {
        log.debug("deleting ChannelType  instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ChannelType findById(java.lang.Long id) {
        log.debug("getting ChannelType instance with channelTypeId: " + id);
        try {
            ChannelType instance = (ChannelType) getSession().get("com.viettel.im.database.BO.ChannelType", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all ChannelType  instances");
        try {
//            String queryString = "from ChannelType as model where not status=" + Constant.STATUS_DELETE;
            String queryString = "from ChannelType ";
            queryString += "order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ChannelTypeDAO ...");

        HttpServletRequest req = getRequest();
        SaleChannelTypeForm f = this.saleChannelTypeForm;

        f.resetForm();
        List listChannelType = new ArrayList();
        listChannelType = findAll();
        req.getSession().removeAttribute("listChannelType");
        req.getSession().setAttribute("listChannelType", listChannelType);
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());
        List<AppParams> listPricePolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_PRICE_POLICY);
        req.setAttribute("listPricePolicy", listPricePolicy);
        List<AppParams> listDiscountPolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_DISCOUNT_POLICY);
        req.setAttribute("listDiscountPolicy", listDiscountPolicy);
        req.setAttribute("toEditChannelType", 0);

        pageForward = ADD_NEW_SALE_CHANNEL_TYPE;

        log.info("End method preparePage of ChannelTypeDAO");

        return pageForward;
    }

    /*Author: NamNX
     * Date created: 20/03/2009
     * Purpose: addNewChannelType tao moi ChannelType
     */
    public String addNewChannelType() throws Exception {
        log.info("Begin method preparePage of ChannelTypeDAO ...");

        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        HttpServletRequest req = getRequest();
        SaleChannelTypeForm f = this.saleChannelTypeForm;
        req.setAttribute("toEditChannelType", 0);
        if (checkValidateToAdd()) {
            ChannelType bo = new ChannelType();
            f.copyDataToBO(bo);
            Long channelTypeId = getSequence("CHANNEL_TYPE_ID_SEQ");
            bo.setChannelTypeId(channelTypeId);
            bo.setCode(f.getCode());
            bo.setPrefixObjectCode(f.getCode());
            bo.setIsPrivate(0L);
            save(bo);
            // thuc hien ghi Log :
            lstLogObj.add(new ActionLogsObj(null, bo));
            saveLog(Constant.ACTION_ADD_TYPE_CHANNEL, "Thực hiên thêm mới loại kênh", lstLogObj, bo.getChannelTypeId(),Constant.CATALOGUE_TYPE_OF_CHANNEL,Constant.ADD);


            f.resetForm();
            //f.setMessage("Đã thêm đối tác mới !");
            req.setAttribute("returnMsg", "channelType.add.success");
            List listChannelType = new ArrayList();
            listChannelType = findAll();
            req.getSession().removeAttribute("listChannelType");
            req.getSession().setAttribute("listChannelType", listChannelType);
           
        }
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());
        List<AppParams> listPricePolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_PRICE_POLICY);
        req.setAttribute("listPricePolicy", listPricePolicy);
        List<AppParams> listDiscountPolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_DISCOUNT_POLICY);
        req.setAttribute("listDiscountPolicy", listDiscountPolicy);
        pageForward = ADD_NEW_SALE_CHANNEL_TYPE;

        log.info("End method preparePage of ChannelTypeDAO");

        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of ChannelTypeDAO ...");
        pageForward = "pageNavigator";
        log.info("End method preparePage of ChannelTypeDAO");

        return pageForward;
    }
    /*Author: NamNX
     * Date created: 20/03/2009
     * Purpose: prepagePage of editChannelType
     */

    public String prepareEditChannelType() throws Exception {
        log.info("Begin method preparePage of ChannelTypeDAO ...");

        HttpServletRequest req = getRequest();
        SaleChannelTypeForm f = this.saleChannelTypeForm;
        String ajax = QueryCryptUtils.getParameter(req, "ajax");
        if ("1".equals(ajax)) {
            f.resetForm();
            req.setAttribute("toEditChannelType", 0);
        } else {
            String strChannelTypeId = (String) QueryCryptUtils.getParameter(req, "channelTypeId");
            Long channelTypeId;
            try {
                channelTypeId = Long.parseLong(strChannelTypeId);
            } catch (Exception ex) {
                channelTypeId = -1L;
            }

            ChannelType bo = findById(channelTypeId);
            f.copyDataFromBO(bo);
            f.setCode(bo.getCode());
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            List<AppParams> listPricePolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_PRICE_POLICY);
            req.setAttribute("listPricePolicy", listPricePolicy);
            List<AppParams> listDiscountPolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_DISCOUNT_POLICY);
            req.setAttribute("listDiscountPolicy", listDiscountPolicy);

            req.setAttribute("toEditChannelType", 1);
        }

        pageForward = ADD_NEW_SALE_CHANNEL_TYPE;

        log.info("End method preparePage of ChannelTypeDAO");

        return pageForward;
    }

    /*Author: NamNX
     * Date created: 20/03/2009
     * Purpose: editChannelType luu lai thay doi
     */
    public String editChannelType() throws Exception {
        log.info("Begin method preparePage of ChannelTypeDAO ...");

        HttpServletRequest req = getRequest();
        SaleChannelTypeForm f = this.saleChannelTypeForm;
        String ajax = QueryCryptUtils.getParameter(req, "ajax");
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();

        if ("1".equals(ajax)) {
            f.resetForm();
            req.setAttribute("toEditChannelType", 0);
        } else {
            // lay thong tin shop cu de ghi Log :
            ChannelType channelType = new ChannelTypeDAO().findById(f.getChannelTypeId());
            ChannelType oldChannelType = new ChannelType();
            BeanUtils.copyProperties(oldChannelType, channelType);
            oldChannelType.setStockType(null);
            if (checkValidateToEdit()) {
                channelType.setCheckComm(f.getCheckComm());
                channelType.setIsVtUnit(f.getIsVtUnit());
                channelType.setName(f.getName());
                channelType.setObjectType(f.getObjectType());
                channelType.setStatus(f.getStatus());
                channelType.setStockReportTemplate(f.getStockReportTemplate());
                channelType.setStockType(f.getStockType());
                channelType.setPricePolicyDefault(f.getPricePolicyDefault());
                channelType.setDiscountPolicyDefault(f.getDiscountPolicyDefault());
                channelType.setCode(f.getCode());
//                f.copyDataToBO(bo);
                getSession().saveOrUpdate(channelType);
                //ANHTT: thuc hien ghi Log :

                lstLogObj.add(new ActionLogsObj(oldChannelType, channelType));
                saveLog(Constant.ACTION_EDIT_TYPE_CHANNEL, "Cập nhật thông tin loại kênh bán hàng", lstLogObj, channelType.getChannelTypeId(),Constant.CATALOGUE_TYPE_OF_CHANNEL,Constant.EDIT);

                f.resetForm();
                //f.setMessage("Đã sửa thông tin đối tác !");
                req.setAttribute("returnMsg", "channelType.edit.success");
                List listChannelType = new ArrayList();
                listChannelType = findAll();
                req.getSession().removeAttribute("listChannelType");
                req.getSession().setAttribute("listChannelType", listChannelType);
                AppParamsDAO appParamsDAO = new AppParamsDAO();
                appParamsDAO.setSession(this.getSession());
                List<AppParams> listPricePolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_PRICE_POLICY);
                req.setAttribute("listPricePolicy", listPricePolicy);
                List<AppParams> listDiscountPolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_DISCOUNT_POLICY);
                req.setAttribute("listDiscountPolicy", listDiscountPolicy);
            }


        }
        req.setAttribute("toEditChannelType", 0);
        pageForward = ADD_NEW_SALE_CHANNEL_TYPE;
        log.info("End method preparePage of ChannelTypeDAO");
        return pageForward;
    }

    /*Author: NamNX
     * Date created: 20/03/2009
     * Purpose: deleteChannelType xoa doi tac
     */
    public String deleteChannelType() throws Exception {
        log.info("Begin method preparePage of ChannelTypeDAO ...");
        HttpServletRequest req = getRequest();
        SaleChannelTypeForm f = this.saleChannelTypeForm;
        pageForward = ADD_NEW_SALE_CHANNEL_TYPE;
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        try {
            String strChannelTypeId = (String) QueryCryptUtils.getParameter(req, "channelTypeId");
            Long channelTypeId;
            try {
                channelTypeId = Long.parseLong(strChannelTypeId);
            } catch (Exception ex) {
                channelTypeId = -1L;
            }
            ChannelType bo = findById(channelTypeId);
            String strQuery = "select count(*) from Shop sh where sh.channelTypeId = ? and sh.status = 1";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, channelTypeId);
            Long count = (Long) q.list().get(0);
            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("returnMsg", "ERR.CHL.124");
                return pageForward;
            }
            strQuery = "select count(*) from Staff sh where sh.channelTypeId = ? and sh.status = 1";
            q = getSession().createQuery(strQuery);
            q.setParameter(0, channelTypeId);
            count = (Long) q.list().get(0);
            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("returnMsg", "ERR.CHL.124");
                return pageForward;
            }
            if (bo == null) {
                req.setAttribute("returnMsg", "channelType.delete.error");
                log.info("End method preparePage of ChannelTypeDAO");
                return pageForward;
            }
            getSession().delete(bo);
            // thuc hien ghi Log :
            lstLogObj.add(new ActionLogsObj(bo, null));
            saveLog(Constant.ACTION_DEL_TYPE_CHANNEL, "Thực hiên xóa loại kênh", lstLogObj, bo.getChannelTypeId(),Constant.CATALOGUE_TYPE_OF_CHANNEL,Constant.DELETE);

            f.resetForm();
            req.setAttribute("returnMsg", "channelType.delete.success");
            req.setAttribute("toEditChannelType", 0);
            List listChannelType = new ArrayList();
            listChannelType = findAll();
            req.getSession().removeAttribute("listChannelType");
            req.getSession().setAttribute("listChannelType", listChannelType);
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            List<AppParams> listPricePolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_PRICE_POLICY);
            req.setAttribute("listPricePolicy", listPricePolicy);
            List<AppParams> listDiscountPolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_DISCOUNT_POLICY);
            req.setAttribute("listDiscountPolicy", listDiscountPolicy);
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("returnMsg", "channelType.delete.error");
            log.info("End method preparePage of ChannelTypeDAO");
            return pageForward;
        }
        log.info("End method preparePage of ChannelTypeDAO");
        return pageForward;
    }
//Modified by TheTM <--End-->

    /*Author: NamNX
     * Date created: 30/03/2009
     * Purpose: tim kiem ChannelType
     */
    public String searchChannelType() throws Exception {
        log.info("Begin method searchChannelType of ChannelTypeDAO ...");

        HttpServletRequest req = getRequest();
        SaleChannelTypeForm f = this.saleChannelTypeForm;
        String ajax = QueryCryptUtils.getParameter(req, "ajax");
        if ("1".equals(ajax)) {
            f.resetForm();
            req.setAttribute("toEditChannelType", 0);
        } else {
            List listParameter = new ArrayList();

//            String strQuery = "from ChannelType as model where not status=" + Constant.STATUS_DELETE;
            String strQuery = "from ChannelType as model where 1=1";
            if (f.getStatus() != null) {
                listParameter.add(f.getStatus());
                strQuery += " and model.status = ? ";
            }

            if (f.getName() != null && !f.getName().trim().equals("")) {
                listParameter.add("%" + f.getName().trim().toLowerCase() + "%");
                strQuery += " and lower(model.name) like ? ";
            }
            if (f.getCheckComm() != null && !f.getCheckComm().trim().equals("")) {
                listParameter.add(f.getCheckComm().trim());
                strQuery += " and model.checkComm = ? ";
            }
            if (f.getIsVtUnit() != null && !f.getIsVtUnit().trim().equals("")) {
                listParameter.add(f.getIsVtUnit().trim());
                strQuery += " and model.isVtUnit = ? ";
            }
            if (f.getObjectType() != null && !f.getObjectType().trim().equals("")) {
                listParameter.add(f.getObjectType().trim());
                strQuery += " and model.objectType = ? ";
            }
            if (f.getStockType() != null) {
                listParameter.add(f.getStockType());
                strQuery += " and model.stockType = ? ";
            }
            if (f.getStockReportTemplate() != null && !f.getStockReportTemplate().trim().equals("")) {
                listParameter.add(f.getStockReportTemplate().trim());
                strQuery += " and model.stockReportTemplate = ? ";
            }
            strQuery += "order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query q = getSession().createQuery(strQuery);
            for (int i = 0; i < listParameter.size(); i++) {
                q.setParameter(i, listParameter.get(i));
            }

            List listChannelType = new ArrayList();
            listChannelType = q.list();

            req.setAttribute("toEditChannelType", 0);

//            if (listChannelType != null) {
//                f.setMessage("Tìm thấy " + listChannelType.size() + " kết quả thỏa mãn điều kiện tìm kiếm");
//            } else {
//                f.setMessage("");
//            }

            req.getSession().removeAttribute("listChannelType");
            req.getSession().setAttribute("listChannelType", listChannelType);
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            List<AppParams> listPricePolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_PRICE_POLICY);
            req.setAttribute("listPricePolicy", listPricePolicy);
            List<AppParams> listDiscountPolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_DISCOUNT_POLICY);
            req.setAttribute("listDiscountPolicy", listDiscountPolicy);

            req.setAttribute("returnMsg", "channelType.search");
            List paramMsg = new ArrayList();
            paramMsg.add(listChannelType.size());
            req.setAttribute("paramMsg", paramMsg);

        }

        pageForward = ADD_NEW_SALE_CHANNEL_TYPE;

        log.info("End method searchChannelType of ChannelTypeDAO");

        return pageForward;
    }

    /*Author: NamNX
     * Date created: 10/06/2009
     * Purpose: kiem tra du leu nhap channelType truoc khi them moi hoac sua
     */
    private boolean checkValidateToAdd() {

        Long count;
        HttpServletRequest req = getRequest();

        String name = this.saleChannelTypeForm.getName().trim();

        Long status = this.saleChannelTypeForm.getStatus();


        if ((name == null) || name.equals("")) {
            req.setAttribute("returnMsg", "channelType.error.invalidName");
            return false;
        }
        if (status == null) {
            req.setAttribute("returnMsg", "channelType.error.invalidStatus");
            return false;
        }

        String strQuery = "select count(*) from ChannelType as model where model.name=?";
        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, name);
        count = (Long) q.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("returnMsg", "channelType.add.duplicateName");
            return false;
        }
        return true;

    }

    private boolean checkValidateToEdit() {

        Long count;
        HttpServletRequest req = getRequest();
        Long id = this.saleChannelTypeForm.getChannelTypeId();
        String name = this.saleChannelTypeForm.getName().trim();
        String checkComm = this.saleChannelTypeForm.getCheckComm().trim();
        String objectType = this.saleChannelTypeForm.getObjectType().trim();
        Long status = this.saleChannelTypeForm.getStatus();
        Long stockType = this.saleChannelTypeForm.getStockType();
        String isVtUnit = this.saleChannelTypeForm.getIsVtUnit().trim();
        String stockReportTemplate = this.saleChannelTypeForm.getStockReportTemplate().trim();
        if ((name == null) || name.equals("")) {
            req.setAttribute("returnMsg", "channelType.error.invalidName");
            return false;
        }
        if (status == null) {
            req.setAttribute("returnMsg", "channelType.error.invalidStatus");
            return false;
        }

        String strQuery = "select count(*) from ChannelType as model where model.name=? and not model.channelTypeId = ?";

        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, name);
        q.setParameter(1, id);
        count = (Long) q.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("returnMsg", "channelType.edit.duplicateName");
            return false;
        }
        return true;

    }
    /* author NamNX
     * Ham chuan bi copy 1 ChannelType */

    public String prepareCopyChannelType() throws Exception {
        log.info("Begin method prepareCopyChannelType of ChannelTypeDAO ...");
        try {
            HttpServletRequest req = getRequest();
            SaleChannelTypeForm f = this.saleChannelTypeForm;
            String strChannelTypeId = (String) QueryCryptUtils.getParameter(req, "channelTypeId");
            Long channelTypeId;
            try {
                channelTypeId = Long.parseLong(strChannelTypeId);
            } catch (Exception ex) {
                channelTypeId = -1L;
            }
            ChannelType bo = findById(channelTypeId);
            if (bo == null) {
                req.setAttribute("returnMsg", "channelType.copy.error");
                pageForward = ADD_NEW_SALE_CHANNEL_TYPE;
                log.info("End method prepareCopyChannelType of SaleChannelTypeDAO");
                return pageForward;
            }
            f.copyDataFromBO(bo);
            req.setAttribute("toEditChannelType", 2);
            List listChannelType = new ArrayList();
            listChannelType = findAll();
            req.getSession().removeAttribute("listChannelType");
            req.getSession().setAttribute("listChannelType", listChannelType);
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            List<AppParams> listPricePolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_PRICE_POLICY);
            req.setAttribute("listPricePolicy", listPricePolicy);
            List<AppParams> listDiscountPolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_DISCOUNT_POLICY);
            req.setAttribute("listDiscountPolicy", listDiscountPolicy);
        } catch (Exception e) {
            e.printStackTrace();
            pageForward = ADD_NEW_SALE_CHANNEL_TYPE;
            log.info("End method prepareCopyChannelType of SaleChannelTypeDAO");
            return pageForward;
        }

        pageForward = ADD_NEW_SALE_CHANNEL_TYPE;
        log.info("End method prepareCopyChannelType of SaleChannelTypeDAO");
        return pageForward;
    }
    /* author NamNX
     * Ham copy 1 ChannelType */

    public String copyChannelType() throws Exception {
        log.info("Begin method copyPartner of ChannelTypeDAO ...");
        try {
            HttpServletRequest req = getRequest();
            SaleChannelTypeForm f = this.saleChannelTypeForm;
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();

            // lay thong tin shop cu de ghi Log :
            ChannelType oldChannelType = new ChannelTypeDAO().findById(f.getChannelTypeId());
            //  ChannelType oldChannelType = new ChannelType() ;
            //  BeanUtils.copyProperties(oldChannelType, channelType);

            req.setAttribute("toEditChannelType", 0);
            if (checkValidateToAdd()) {
                ChannelType bo = new ChannelType();

                f.copyDataToBO(bo);
                bo.setChannelTypeId(getSequence("CHANNEL_TYPE_ID_SEQ"));
                bo.setCode(f.getCode());
                bo.setPrefixObjectCode(f.getCode());
                bo.setIsPrivate(0L);
                getSession().save(bo);
                // ANHTT thuc hien ghi Log :
                lstLogObj.add(new ActionLogsObj(oldChannelType, bo));
                saveLog(Constant.ACTION_COPY_TYPE_CHANNEL, "Thực hiên sao chép loại kênh", lstLogObj, oldChannelType.getChannelTypeId());

                f.resetForm();
                req.setAttribute("returnMsg", "channelType.copy.success");
                List listChannelType = new ArrayList();
                listChannelType = findAll();
                req.getSession().removeAttribute("listChannelType");
                req.getSession().setAttribute("listChannelType", listChannelType);
            }
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            List<AppParams> listPricePolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_PRICE_POLICY);
            req.setAttribute("listPricePolicy", listPricePolicy);
            List<AppParams> listDiscountPolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_DISCOUNT_POLICY);
            req.setAttribute("listDiscountPolicy", listDiscountPolicy);
        } catch (Exception e) {
            e.printStackTrace();
            pageForward = ADD_NEW_SALE_CHANNEL_TYPE;

            log.info("End method copyChannelType of ChannelTypeDAO");
            return pageForward;
        }

        pageForward = ADD_NEW_SALE_CHANNEL_TYPE;

        log.info("End method copyPartner of ChannelTypeDAO");
        return pageForward;
    }
//    public String prepareAddNewSaleChannelType() throws Exception {
//        log.info("Begin method prepareAddNewSaleChannelType of SaleChannelTypeDAO ...");
//
//        HttpServletRequest req = getRequest();
//        HttpSession session = req.getSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = Constant.ERROR_PAGE;
//
//        if (userToken != null) {
//            try {
//                pageForward = ADD_NEW_SALE_CHANNEL_TYPE;
//                SearchSaleChannelTypeForm f = this.searchSaleChannelTypeForm;
//                List channelTypeList = this.getListSaleChannelType();
//                req.getSession().removeAttribute("channelTypeList");
//                req.getSession().setAttribute("channelTypeList", channelTypeList);
//                req.getSession().setAttribute("toEdit", 0);
//                req.getSession().setAttribute("toAdd", 0);
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//        log.info("End method prepareAddNewSaleChannelType of SaleChannelTypeDAO");
//        return pageForward;
//    }
//
//
//    public List getListSaleChannelType() throws Exception {
//        try {
//            List<ChannelType> channelTypeList = null;
//            String strHQL = "  from ChannelType as c " +
//                            " where c.status =? or c.status =?  order by c.name desc " ;
//
//            Query query = (Query) getSession().createQuery(strHQL);
//            query.setParameter(0,new Long(1));
//            query.setParameter(1,new Long(2));
//
//            int resultLimit = SEARCH_RESULT_LIMIT + 1;
//            query.setMaxResults(resultLimit);
//            channelTypeList =(ArrayList<ChannelType>)query.list();
//            return channelTypeList;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//    /**
//     *  CuongNT
//     * Method Search
//     */
//    public List listSearch(SearchSaleChannelTypeForm type) throws Exception {
//        try {
//            HttpServletRequest req = getRequest();
//            HttpSession session = req.getSession();
//            if (req.getSession().getAttribute("type") != null) {
//                type = (SearchSaleChannelTypeForm) req.getSession().getAttribute("type");
//            } else {
//                type = this.searchSaleChannelTypeForm;
//            }
//            String name = type.getSearchSaleChannelTypeName();
//            String groupChannel = type.getSearchSaleChannelTypeGroup();
//            Long status = type.getSearchSaleChannelTypeStatus();
//
//            List lst = new ArrayList();
//            List listParameter = new ArrayList();
//            String strHQL = "from ChannelType as model where 1=1";
//            if (name != null && !name.trim().equals("")) {
//                listParameter.add(name.trim().toLowerCase());
//                strHQL += " and lower(model.name) like lower(?) escape '!'";
//            }
//            if (groupChannel != null && !groupChannel.trim().equals("")) {
//                listParameter.add(groupChannel.trim().toLowerCase());
//                strHQL += " and lower(model.objectType) like lower(?) escape '!'";
//            }
//
//            // khi khech da xoa status
//            if (status!=null && ((status == 1L) || (status == 0L) || (status == 2L))) {
//                listParameter.add(status);
//                strHQL += " and model.status = ?";
//            }
//            //querry
//            Query query = getSession().createQuery(strHQL);
//            for (int i = 0; i < listParameter.size(); i++) {
//                query.setParameter(i, listParameter.get(i));
//            }
//            int resultLimit = SEARCH_RESULT_LIMIT + 1;
//            query.setMaxResults(resultLimit);
//            lst = query.list();
//            return lst;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//
//    public String pageNavigator() {
//     HttpServletRequest req = getRequest();
//     req.setAttribute("test", "test");
//
//     return "pageNavigatorSuccess" ;
//   }
//
//
//    public String Search() throws Exception {
//        log.info("Begin method search  ...");
//        HttpServletRequest req = getRequest();
//        String result = null;
//        HttpSession session = req.getSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = Constant.ERROR_PAGE;
//
//        if (userToken != null) {
//            try {
//                req.getSession().removeAttribute("type");
//                SearchSaleChannelTypeForm type = this.searchSaleChannelTypeForm;
//                List channelTypeList = this.listSearch(type);
//                req.getSession().setAttribute("type", type);
//                req.getSession().setAttribute("channelTypeList", channelTypeList);
//                req.getSession().setAttribute("toEdit", 0);
//                req.getSession().setAttribute("toAdd", 0);
//
//                pageForward = ADD_NEW_SALE_CHANNEL_TYPE;
//            } catch (Exception e) {
//                result = "error: " + e.getMessage();
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//        return pageForward;
//    }
//    //end Search
//     /*Author: CuongNT
//     * Purpose: prepagePage of Add
//     */
//
//    public String prepareAdd() throws Exception {
//        log.info("Begin method prepareAdd of SaleChannelTypeDAO ...");
//
//        HttpServletRequest req = getRequest();
//        SaleChannelTypeForm f = this.saleChannelTypeForm;
//        String ajax = QueryCryptUtils.getParameter(req, "ajax");
//        if ("1".equals(ajax)) {
//            f.resetForm();
//            req.getSession().setAttribute("toAdd", 0);
//        } else {
//            f.setMessage("");
//            req.getSession().setAttribute("toAdd", 1);
//// lay lai list
//            if (req.getSession().getAttribute("type") != null) {
//                SearchSaleChannelTypeForm sf = (SearchSaleChannelTypeForm) req.getSession().getAttribute("type");
//                List channelTypeList = this.listSearch(sf);
//                req.getSession().removeAttribute("channelTypeList");
//                req.getSession().setAttribute("channelTypeList", channelTypeList);
//            } else {
//                List channelTypeList = this.getListSaleChannelType();
//                req.getSession().removeAttribute("channelTypeList");
//                req.getSession().setAttribute("channelTypeList", channelTypeList);
//            }
//        }
//        pageForward = ADD_NEW_SALE_CHANNEL_TYPE;
//        log.info("End method preparePage of SaleChannelTypeDAO");
//        return pageForward;
//    }//end prepareAdd
//
//
//
//    public String prepareCancel() {
//        System.out.println("== thuc hien nut bo qua");
//        HttpServletRequest req = getRequest();
//        req.setAttribute("cancelSaleChannel", "cancelSaleChannel");
//        req.getSession().setAttribute("toEdit", 0);
//        req.getSession().setAttribute("toAdd", 0);
//        return "cancelSuccess" ;
//    }
//
//
//
//    /*Author: CuongNT
//     * Purpose: prepagePage of edit
//     */
//
//    public String prepareEdit() throws Exception {
//        log.info("Begin method preparePage of SaleChannelTypeDAO ...");
//
//        HttpServletRequest req = getRequest();
//        SaleChannelTypeForm f = this.saleChannelTypeForm;
//        String ajax = QueryCryptUtils.getParameter(req, "ajax");
//        if ("1".equals(ajax)) {
//            f.resetForm();
//            req.getSession().setAttribute("toEdit", 0);
//        } else {
//            String strChannelTypeId = (String) QueryCryptUtils.getParameter(req, "channelTypeId");
//            Long channelTypeId;
//            try {
//                channelTypeId = Long.parseLong(strChannelTypeId);
//            } catch (Exception ex) {
//                channelTypeId = -1L;
//            }
//
//            ChannelType bo = findById(channelTypeId);
//            f.copyDataFromBO(bo);
//            f.setMessage("");
//            req.getSession().setAttribute("toEdit", 1);
//// lay lai list
//            if(req.getSession().getAttribute("type") != null){
//                SearchSaleChannelTypeForm sf = (SearchSaleChannelTypeForm) req.getSession().getAttribute("type");
//                List channelTypeList = this.listSearch(sf);
//                req.getSession().removeAttribute("channelTypeList");
//                req.getSession().setAttribute("channelTypeList", channelTypeList);
//            }
//            else{
//                List channelTypeList = this.getListSaleChannelType();
//                req.getSession().removeAttribute("channelTypeList");
//                req.getSession().setAttribute("channelTypeList", channelTypeList);
//            }
//        }
//        pageForward = ADD_NEW_SALE_CHANNEL_TYPE;
//        log.info("End method preparePage of SaleChannelTypeDAO");
//        return pageForward;
//    }//end of prepare edit
//
//    // actionDeleteChannelType
//    public String actionDeleteChannelType() throws Exception {
//        log.info("Begin method actionDelete  ...");
//        HttpServletRequest req = getRequest();
//        String result = null;
//
//        HttpSession session = req.getSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = Constant.ERROR_PAGE;
//
//        if (userToken != null) {
//            try {
//                Long channelTypeId;
//                if (QueryCryptUtils.getParameter(req, "channelTypeId") != null) {
//                    channelTypeId = Long.parseLong(QueryCryptUtils.getParameter(req, "channelTypeId"));
//                    ChannelType type = this.findById(channelTypeId);
//                    //delete chuyen status ve ko hoat dong status = 0
//                    if (type != null) {
//                        type.setStatus(Constant.STATUS_DELETE);
//                        getSession().update(type);
//                    } else {
//                    }
//                }
//                // lay lai list
//                if (req.getSession().getAttribute("type") != null) {
//                    SearchSaleChannelTypeForm sf = (SearchSaleChannelTypeForm) req.getSession().getAttribute("type");
//                    List channelTypeList = this.listSearch(sf);
//                    req.getSession().removeAttribute("channelTypeList");
//                    req.getSession().setAttribute("channelTypeList", channelTypeList);
//                } else {
//                    List channelTypeList = this.getListSaleChannelType();
//                    req.getSession().removeAttribute("channelTypeList");
//                    req.getSession().setAttribute("channelTypeList", channelTypeList);
//                }
//                req.getSession().setAttribute("toEdit", 0);
//                req.getSession().setAttribute("toAdd", 0);
//
//                pageForward = ADD_NEW_SALE_CHANNEL_TYPE;
//            } catch (Exception e) {
//                result = "error: " + e.getMessage();
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//        return pageForward;
//    }//end action delete
//
//
//
//    /*Author: CuongNT
//     * Purpose:luu lai thay doi (add or edit)editChannelType
//     */
//    public String editChannelType() throws Exception {
//        log.info("Begin method preparePage of SaleChannelTypeDAO ...");
//
//        HttpServletRequest req = getRequest();
//        SaleChannelTypeForm f = this.saleChannelTypeForm;
//        String ajax = QueryCryptUtils.getParameter(req, "ajax");
//        if ("1".equals(ajax)) {
//            f.resetForm();
//            req.getSession().setAttribute("toEdit", 0);
//        } else {
//            String strToEdit = req.getSession().getAttribute("toEdit").toString();
//            Long toEdit = Long.parseLong(strToEdit);
//            if (toEdit == 1L) {
//                // Edit
//                ChannelType bo = new ChannelType();
//                f.copyDataToBO(bo);
//                Long channelTypeId = f.getSaleChannelTypeId();
//                Long stockType = Long.parseLong(f.getSaleChannelTypeGroup());
////                bo.setStockType(stockType);
//                bo.setChannelTypeId(channelTypeId);
//                getSession().update(bo);
//                f.resetForm();
//                f.setMessage("Đã sửa thông tin loại kênh !");
//                req.getSession().setAttribute("toEdit", 0);
//            } else {
//                //Add new
//                ChannelType bo = new ChannelType();
//                //f.setSaleChannelTypeStatus(1L);
//                f.copyDataToBO(bo);
//                Long stockType = Long.parseLong(f.getSaleChannelTypeGroup());
////                bo.setStockType(stockType);
//                bo.setChannelTypeId(getSequence("CHANNEL_TYPE_ID_SEQ"));
//                save(bo);
//                f.resetForm();
//                f.setMessage("Đã thêm loại loại kênh mới !");
//                req.getSession().setAttribute("toAdd", 0);
//            }
//        }
//// lay lai list
//            if(req.getSession().getAttribute("type") != null){
//                SearchSaleChannelTypeForm sf = (SearchSaleChannelTypeForm) req.getSession().getAttribute("type");
//                List channelTypeList = this.listSearch(sf);
//                req.getSession().removeAttribute("channelTypeList");
//                req.getSession().setAttribute("channelTypeList", channelTypeList);
//            }
//            else{
//                List channelTypeList = this.getListSaleChannelType();
//                req.getSession().removeAttribute("channelTypeList");
//                req.getSession().setAttribute("channelTypeList", channelTypeList);
//            }
//        pageForward = ADD_NEW_SALE_CHANNEL_TYPE;
//        log.info("End method preparePage of SaleChannelTypeDAO");
//        return pageForward;
//    }//end of editChannelType
//}
}
