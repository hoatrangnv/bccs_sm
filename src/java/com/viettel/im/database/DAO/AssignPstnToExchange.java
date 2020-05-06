/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.PSTNExchangeForm;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.PstnIsdnExchange;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Exchange;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
/**
 *
 * @author tuan
 */
public class AssignPstnToExchange extends BaseDAOAction {

    private String errorPage = "error";
    private String login = "loginError";
    private PSTNExchangeForm pstnExchangeForm = new PSTNExchangeForm();
    private static final Log log = LogFactory.getLog(AssignPstnToExchange.class);
    private Map<String, String> listArea = new HashMap();
    protected static final String USER_TOKEN_ATTRIBUTE = "userToken";
    public String pageForward;
    public static final String EXCHANGE = "pstnExchangePrepare";
    public static final String EXCHANGE_LIST = "pstnExchangeList";
    private final Long MAX_SEARCH_RESULT = 100L; //gioi han so row tra ve doi voi tim kiem

    public PSTNExchangeForm getPstnExchangeForm() {
        return pstnExchangeForm;
    }

    public void setPstnExchangeForm(PSTNExchangeForm pstnExchangeForm) {
        this.pstnExchangeForm = pstnExchangeForm;
    }

    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        pstnExchangeForm = new PSTNExchangeForm();
        try {

//            getRequest().setAttribute(REQUEST_LIST_LOCATIONS, getListProvinces());

            req.setAttribute("lstExchangeType", getListExchangeType());
            List listPSTN = new ArrayList();
            listPSTN = findPstnExchange();
            req.setAttribute("lstPSTN", listPSTN);
            setTabSession("toEdit", "0");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        pageForward = EXCHANGE;
        return pageForward;
    }

    public List getListExchangeType() throws Exception {
        List<AppParams> lstExchangeType = new ArrayList();
        String exchangeType = Constant.APP_PARAMS_EXCHANGE_TYPE;
        try {
            AppParamsDAO appDAO = new AppParamsDAO();
            appDAO.setSession(getSession());
            lstExchangeType = appDAO.findAppParamsByType(exchangeType);
//            Collections.sort(List lstExchangeType);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        return lstExchangeType;
    }
    private List listItemsSelectBox = new ArrayList();

    public List getListItemsSelectBox() {
        return listItemsSelectBox;
    }

    public void setListItemsSelectBox(List listItemsSelectBox) {
        this.listItemsSelectBox = listItemsSelectBox;
    }

    /*Author: ThanhNC
     * Date created: 31/05/2010
     * Purpose: select exchangeType --> exchange list
     */
    public String getExchange() throws Exception {
        try {

            HttpServletRequest httpServletRequest = getRequest();

            //Chon hang hoa tu loai hang hoa
            String exchangeType = httpServletRequest.getParameter("exchangeType");
            
            String[] header = {"", "---"+getText("L_100001")+"--"};
            listItemsSelectBox.add(header);
            if (exchangeType != null && !"".equals(exchangeType.trim())) {
                Long id = Long.parseLong(exchangeType);
                String SQL_SELECT_EXCHANGE = "select exchangeId, name from Exchange where typeId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Query q = getSession().createQuery(SQL_SELECT_EXCHANGE);
                q.setParameter(0, id);
                q.setParameter(1, Constant.STATUS_USE);
                List lstExchange = q.list();
                listItemsSelectBox.addAll(lstExchange);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return "success";
    }

    public List getExchangeByExchangeType(Long exchangeType) throws Exception {
        try {
            if (exchangeType != null) {

                String SQL_SELECT_EXCHANGE = " from Exchange where typeId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Query q = getSession().createQuery(SQL_SELECT_EXCHANGE);
                q.setParameter(0, exchangeType);
                q.setParameter(1, Constant.STATUS_USE);
                return q.list();
            }
            return new ArrayList();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


    }

    public String getPSTNExchange() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String pCode = httpServletRequest.getParameter("pstnExchangeForm.provinceCode");
            List<Area> lArea = new ArrayList();
            if (pCode != null && pCode.length() > 0) {
                lArea = findArea(pCode);
                if (lArea != null && lArea.size() > 0) {
                    Iterator iterator = lArea.iterator();
                    while (iterator.hasNext()) {
                        Area a = (Area) iterator.next();
                        listArea.put(a.getProvince(), a.getFullName());
                    }
                }
            }

            pageForward = SUCCESS;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pageForward;
    }

    public List findArea(String provinceCode) throws Exception {
        log.debug("finding findArea available Area instances");
        try {

            if (provinceCode == null || provinceCode.trim().length() == 0) {
                return null;
            }
            String queryString = " from Area a where a.province like  ?  ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, '%' + provinceCode.trim() + '%');
            return queryObject.list();
        } catch (HibernateException re) {
            log.error("HibernateException at findAreaByPSTN : " + re.getMessage());
            re.printStackTrace();
            throw new Exception(re);
        }
    }

    /**
     * Check PREFIX in table
     * @return Boolean
     */
    public String searchPSTNExchange() throws Exception {
        HttpServletRequest req = getRequest();
        // HttpSession session = req.getSession();
        req.setAttribute("lstExchangeType", getListExchangeType());
        String exchangeType = pstnExchangeForm.getExchangeType();
        if (exchangeType != null && !exchangeType.trim().equals("")) {
            List lstExchange = getExchangeByExchangeType(Long.parseLong(exchangeType));
            req.setAttribute("lstExhange", lstExchange);
        }

        List listPSTN = findPstnExchange();
        req.setAttribute("lstPSTN", listPSTN);
        req.setAttribute("message", "assignPstnToExchange.search");
        List paramMsg = new ArrayList();
        paramMsg.add(listPSTN.size());
        req.setAttribute("paramMsg", paramMsg);
        pageForward = EXCHANGE_LIST;

        pageForward = EXCHANGE;
        return pageForward;
    }

    private List findPstnExchange() {
        List listPSTN = new ArrayList();
        try {
            PSTNExchangeForm pForm = getPstnExchangeForm();
            List listParameter = new ArrayList();
            StringBuffer SQL_SELECT = new StringBuffer();
            SQL_SELECT.append(" SELECT a.prefix,a.po_code AS poCode,a.exchange_id AS exchangeId, "
                    + " a.createdate as createdate,a.pstn_isdn_exchange_id AS pstnIsdnExchangeId,b.province AS provinceCode,"
                    + " c.name || ' - (' || d.name  || ')' as exchangeName "
                    + " FROM pstn_isdn_exchange a, area b, EXCHANGE c, app_params d "
                    + " WHERE a.po_code=b.pstn_code "
                    + " and b.area_code= b.province "
                    + " AND a.exchange_id=c.exchange_id "
                    + " and c.type_id = d.code and d.type = ? "
                    + " and c.status = ? ");
            
            listParameter.add(Constant.APP_PARAMS_EXCHANGE_TYPE);
            listParameter.add(Constant.STATUS_USE);

            if (pForm.getPstnHeadNumber() != null && !pForm.getPstnHeadNumber().trim().equals("")) {
                SQL_SELECT.append(" and a.prefix like ? ");
                listParameter.add(pForm.getPstnHeadNumber().trim() + "%");
            }

            if (pForm.getProvinceCode() != null && !pForm.getProvinceCode().trim().equals("")) {
                SQL_SELECT.append(" and upper(b.province) = ? ");
                listParameter.add(pForm.getProvinceCode().trim().toUpperCase());
            }

            if (pForm.getExchangeType() != null && !pForm.getExchangeType().trim().equals("")) {
                SQL_SELECT.append(" and c.type_id = ? ");
                listParameter.add(Long.parseLong(pForm.getExchangeType().trim()));

//                SQL_SELECT.append(" and a.exchange_Id in (select exchange_Id from Exchange where type_Id =  ? and status = ? ) ");
//                listParameter.add(Long.parseLong(pForm.getExchangeType().trim()));
//                listParameter.add(Constant.STATUS_USE);
                
            }
            if (pForm.getExchangeId() != null && !pForm.getExchangeId().equals(0L)) {
                SQL_SELECT.append(" and a.exchange_Id = ?  ");
                listParameter.add(pForm.getExchangeId());
            }

            Query queryObject = getSession().createSQLQuery(SQL_SELECT.toString()).addScalar("prefix", Hibernate.STRING).addScalar("poCode", Hibernate.STRING).addScalar("exchangeId", Hibernate.LONG).addScalar("createdate", Hibernate.DATE).addScalar("pstnIsdnExchangeId", Hibernate.LONG).addScalar("provinceCode", Hibernate.STRING).addScalar("exchangeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(PstnIsdnExchange.class));
            for (int i = 0; i < listParameter.size(); i++) {
                queryObject.setParameter(i, listParameter.get(i));
            }
            listPSTN = queryObject.list();
            return listPSTN;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listPSTN;
    }

    public String addPSTN() throws Exception {
        HttpServletRequest httpServletRequest = getRequest();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        pageForward = EXCHANGE;

        try {
            httpServletRequest.setAttribute("lstExchangeType", getListExchangeType());
            PSTNExchangeForm pform = getPstnExchangeForm();
            String prefix = pform.getPstnHeadNumber().trim();
            String exchangeType = pform.getExchangeType();
            if (exchangeType != null && !exchangeType.trim().equals("")) {
                List lstExchange = getExchangeByExchangeType(Long.parseLong(exchangeType));
                httpServletRequest.setAttribute("lstExhange", lstExchange);
            }
            if (checkValidateToAdd()) {
                PstnIsdnExchange pBO = new PstnIsdnExchange();
//                PstnIsdnExchangeDAO pDAO = new PstnIsdnExchangeDAO();
//                Date createdate = com.viettel.common.util.DateTimeUtils.getDate();
                pBO.setCreatedate(getSysdate());
                List temp = findpoCodebyProvinceCode(pform.getProvinceCode());
                Area tempobj = (Area) temp.get(0);
                String tempPstnCode = tempobj.getPstnCode();

                //CHECK DA TON TAI CHUA
                String strPstnHeadNumber = this.pstnExchangeForm.getPstnHeadNumber().trim();
                String strQuery = "select count(*) from PstnIsdnExchange as model where model.prefix=? and model.poCode = ? ";
                Query q = getSession().createQuery(strQuery);
                q.setParameter(0, strPstnHeadNumber);
                q.setParameter(1, tempPstnCode);
                List tmp = q.list();
                Long count = (Long) tmp.get(0);

                if ((count != null) && (count.compareTo(0L) > 0)) {
                    httpServletRequest.setAttribute("message", "assignPstnToExchange.add.duplicateName");
                    List paramMsg = new ArrayList();
                    paramMsg.add(strPstnHeadNumber);
                    httpServletRequest.setAttribute("paramMsg", paramMsg);
                    List listPSTN = new ArrayList();
                    listPSTN = findPstnExchange();
                    httpServletRequest.setAttribute("lstPSTN", listPSTN);
                    return pageForward;
                }
                //KET THUC


                pBO.setPoCode(tempPstnCode);
                pBO.setPrefix(prefix);
                pBO.setExchangeId(pform.getExchangeId());
                Long pstnIsdnExchangeId = getSequence("PSTN_ISDN_EXCHANGE_SEQ");
                pBO.setPstnIsdnExchangeId(pstnIsdnExchangeId);
                getSession().save(pBO);
                lstLogObj.add(new ActionLogsObj(null, pBO));
                saveLog(Constant.ACTION_ADD_PSTN_EXCHANGE, "Thêm mới thông tin đầu số PSTN với tổng đài", lstLogObj, pBO.getPstnIsdnExchangeId());
                getSession().flush();
                pform.resetForm();
                httpServletRequest.setAttribute("message", "assignPstnToExchange.add.success");

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString());
            httpServletRequest.setAttribute("message", "MSG.pstnExchange.Error");
        }
        List listPSTN = new ArrayList();
        listPSTN = findPstnExchange();
        httpServletRequest.setAttribute("lstPSTN", listPSTN);

        return pageForward;
    }

    public String preupdate() throws Exception {
        HttpServletRequest httpServletRequest = getRequest();
        PSTNExchangeForm pform = getPstnExchangeForm();
        try {
            httpServletRequest.setAttribute("lstExchangeType", getListExchangeType());
            String pstnIsdnExchangeId = (String) QueryCryptUtils.getParameter(httpServletRequest, "pstnIsdnExchangeId");

            setTabSession("pstnIsdnExchangeId", pstnIsdnExchangeId);
            List listPSTN = findPstnExchange();
            httpServletRequest.setAttribute("lstPSTN", listPSTN);
            if (pstnIsdnExchangeId == null || pstnIsdnExchangeId.trim().equals("")) {
                httpServletRequest.setAttribute("msgMessage", "MSG.pstnExchange.pstnIsdnNotfound");
                pageForward = EXCHANGE;
                setTabSession("toEdit", "0");
                return pageForward;
            }
            PstnIsdnExchange pBO = new PstnIsdnExchange();
            pBO = findById(Long.parseLong(pstnIsdnExchangeId));
            if (pBO == null) {
                httpServletRequest.setAttribute("msgMessage", "MSG.pstnExchange.pstnIsdnExchangeNotfound");
                pageForward = EXCHANGE;
                setTabSession("toEdit", "0");
                return pageForward;
            }
            Exchange exchange = findExchangeById(pBO.getExchangeId());
            List lstExchange = getExchangeByExchangeType(exchange.getTypeId());
            httpServletRequest.setAttribute("lstExhange", lstExchange);
            AreaDAO areaDAO = new AreaDAO();
            areaDAO.setSession(this.getSession());
            Area area = areaDAO.findProvinceByPstnCode(pBO.getPoCode());
            if (area != null) {
                pform.setProvinceCode(area.getProvince());
                pform.setProvinceName(area.getName());
            }
            pform.setExchangeType(exchange.getTypeId().toString());
            pform.setPstnHeadNumber(pBO.getPrefix());
            pform.setExchangeId(pBO.getExchangeId());
            pform.setPstnIsdnExchangeId(pBO.getPstnIsdnExchangeId());
            pform.setPstnHeadNumber(pBO.getPrefix());
            setTabSession("toEdit", "1");

        } catch (Exception ex) {
            log.error(ex.toString());
        }
        pageForward = EXCHANGE;
        return pageForward;
    }

    private Exchange findExchangeById(Long exchangeId) {
        String SQL_SELECT = " from Exchange where exchangeId = ? ";
        Query q = getSession().createQuery(SQL_SELECT);
        q.setParameter(0, exchangeId);
        List lst = q.list();
        if (lst.size() > 0) {
            return (Exchange) lst.get(0);
        }
        return null;
    }

    public String updatePSTN() throws Exception {
        HttpServletRequest httpServletRequest = getRequest();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        pageForward = EXCHANGE;
        try {
            httpServletRequest.setAttribute("lstExchangeType", getListExchangeType());
            String exchangeType = pstnExchangeForm.getExchangeType();
            if (exchangeType != null) {
                List lstExchange = getExchangeByExchangeType(Long.parseLong(exchangeType));
                httpServletRequest.setAttribute("lstExhange", lstExchange);
            }
            PSTNExchangeForm pform = getPstnExchangeForm();

            if (checkValidateToEdit()) {
                PstnIsdnExchange pBO = new PstnIsdnExchange();
                PstnIsdnExchangeDAO pDAO = new PstnIsdnExchangeDAO();
                pDAO.setSession(getSession());
                String pstnIsdnExchangeId = (String) getTabSession("pstnIsdnExchangeId");
                pBO = findById(Long.parseLong(pstnIsdnExchangeId));

                if (pBO == null) {
                    httpServletRequest.setAttribute("message", "assignPstnToExchange.edit.errorPstnIsdnExchangeNotExists");
                    return pageForward;
                }
                PstnIsdnExchange oldObject = new PstnIsdnExchange();
                BeanUtils.copyProperties(oldObject, pBO);
                //Date createdate = com.viettel.common.util.DateTimeUtils.getDate();
                //pBO.setCreatedate(createdate);
                pBO.setExchangeId(pform.getExchangeId());
                pBO.setPrefix(pform.getPstnHeadNumber().trim());
                List temp = findpoCodebyProvinceCode(pform.getProvinceCode());
                if (temp.size() < 0) {
                    httpServletRequest.setAttribute("message", "assignPstnToExchange.edit.errorPosCodeNotExists");
                    return pageForward;
                }
                Area tempobj = (Area) temp.get(0);
                String tempPstnCode = tempobj.getPstnCode();

                //CHECK DA TON TAI CHUA
                String strPstnHeadNumber = this.pstnExchangeForm.getPstnHeadNumber().trim();
                String strQuery = "select count(*) from PstnIsdnExchange as model where model.prefix=? and model.pstnIsdnExchangeId <> ? and model.poCode = ? ";
                Query q = getSession().createQuery(strQuery);
                q.setParameter(0, strPstnHeadNumber);
                q.setParameter(1, Long.parseLong(pstnIsdnExchangeId));
                q.setParameter(2, tempPstnCode);
                Long count = (Long) q.list().get(0);

                if ((count != null) && (count.compareTo(0L) > 0)) {
                    httpServletRequest.setAttribute("message", "assignPstnToExchange.edit.duplicateName");
                    List paramMsg = new ArrayList();
                    paramMsg.add(strPstnHeadNumber);
                    httpServletRequest.setAttribute("paramMsg", paramMsg);
                    return pageForward;
                }
                //KET THUC

                pBO.setPoCode(tempPstnCode);
                getSession().update(pBO);
                lstLogObj.add(new ActionLogsObj(oldObject, pBO));
                saveLog(Constant.ACTION_EDIT_PSTN_EXCHANGE, "Sửa thông tin đầu số PSTN với tổng đài", lstLogObj, pBO.getPstnIsdnExchangeId());
                getSession().flush();

                pform.resetForm();
                httpServletRequest.setAttribute("message", "assignPstnToExchange.edit.success");
                setTabSession("toEdit", "0");
            }
            List listPSTN = new ArrayList();
            listPSTN = findPstnExchange();
            httpServletRequest.setAttribute("lstPSTN", listPSTN);
        } catch (Exception ex) {
            ex.printStackTrace();
            httpServletRequest.setAttribute("message", "MSG.pstnExchange.EditError");
        }

        return pageForward;
    }

    public String deletePSTN() throws Exception {
        HttpServletRequest httpServletRequest = getRequest();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        PSTNExchangeForm pform = getPstnExchangeForm();
        try {
            httpServletRequest.setAttribute("lstExchangeType", getListExchangeType());
            String pstnIsdnExchangeId = (String) QueryCryptUtils.getParameter(httpServletRequest, "pstnIsdnExchangeId");
            if (pstnIsdnExchangeId != null && pstnIsdnExchangeId.length() > 0) {
                PstnIsdnExchange pBO = new PstnIsdnExchange();
                pBO = findById(Long.parseLong(pstnIsdnExchangeId));
                if (pBO == null) {
                    httpServletRequest.setAttribute("message", "assignPstnToExchange.delete.error");
                    log.info("End method deletePSTN of AssignPstnToExchange");
                    pageForward = EXCHANGE;
                    return pageForward;
                }
                pform.resetForm();
                getSession().delete(pBO);
                lstLogObj.add(new ActionLogsObj(pBO, null));
                saveLog(Constant.ACTION_DELETE_PSTN_EXCHANGE, "Xóa thông tin đầu số PSTN với tổng đài", lstLogObj, pBO.getPstnIsdnExchangeId());
                httpServletRequest.setAttribute("message", "assignPstnToExchange.delete.success");
                setTabSession("toEdit", "0");
                List listPSTN = new ArrayList();
                listPSTN = findPstnExchange();
                httpServletRequest.setAttribute("lstPSTN", listPSTN);
            } else {
                setTabSession("toEdit", "0");
            }
        } catch (Exception ex) {
            log.error(ex.toString());
            httpServletRequest.setAttribute("message", "MSG.pstnExchange.DeleteError");
        }
        pageForward = EXCHANGE;
        return pageForward;
    }

    /**
     * @return the listArea
     */
    public Map<String, String> getListArea() {
        return listArea;
    }

    /**
     * @param listArea the listArea to set
     */
    public void setListArea(Map<String, String> listArea) {
        this.listArea = listArea;
    }

    public String getListProvince_Pstn() throws Exception {
        try {

            HttpServletRequest httpServletRequest = getRequest();
            String provinceCode = httpServletRequest.getParameter("pstnExchangeForm.provinceCode");

            List<Area> ListProvinceNo = new ArrayList();
            if (provinceCode != null) {
                if ("".equals(provinceCode)) {
                    return "success";
                }
                List parameterList = new ArrayList();
                String queryString = "from Area where district IS NULL and precinct IS NULL ";
                queryString += " and lower(areaCode) like ? ";
                parameterList.add("%" + provinceCode.toLowerCase() + "%");

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                if (!queryObject.list().isEmpty()) {
                    ListProvinceNo = queryObject.list();
                    for (int i = 0; i < ListProvinceNo.size(); i++) {
                        ListProvinceNoCombo.put(ListProvinceNo.get(i).getFullName(), ListProvinceNo.get(i).getAreaCode());
                    }
                    return "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "success";
    }
    private Map ListProvinceNoCombo = new HashMap();

    public Map getListProvinceNoCombo() {
        return ListProvinceNoCombo;
    }

    public void setListProvinceNoCombo(Map ListProvinceNoCombo) {
        this.ListProvinceNoCombo = ListProvinceNoCombo;
    }

    public List findpoCodebyProvinceCode(String provinceCode) {
        log.debug("finding pocode ");
        try {
            String queryString = "from Area as model where model.areaCode like ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, provinceCode);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find pocode failed", re);
            throw re;
        }

    }

    public PstnIsdnExchange findById(java.lang.Long id) {
        log.debug("getting Boards instance with id: " + id);
        try {
            PstnIsdnExchange instance = (PstnIsdnExchange) getSession().get(
                    "com.viettel.im.database.BO.PstnIsdnExchange", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    private boolean checkValidateToAdd() {
        Long count;
        HttpServletRequest req = getRequest();
        String strPstnHeadNumber = this.pstnExchangeForm.getPstnHeadNumber().trim();
        String strProvinceCode = this.pstnExchangeForm.getProvinceCode().trim();
        String strExchangeCode = this.pstnExchangeForm.getExchangeType().trim();
        if ((strPstnHeadNumber == null || strPstnHeadNumber.equals(""))) {
            req.setAttribute("message", "assignPstnToExchange.error.invalidPstnHesdNumber");
            return false;
        }
        if ((strProvinceCode == null || strProvinceCode.equals(""))) {
            req.setAttribute("message", "assignPstnToExchange.error.invalidProvinceCode");
            return false;
        }
        if ((strExchangeCode == null || strExchangeCode.equals(""))) {
            req.setAttribute("message", "assignPstnToExchange.error.invalidExchangeCode");
            return false;
        }

        return true;

    }

    private boolean checkValidateToEdit() {
        Long count;
        HttpServletRequest req = getRequest();
        String strPstnHeadNumber = this.pstnExchangeForm.getPstnHeadNumber().trim();
        String strProvinceCode = this.pstnExchangeForm.getProvinceCode().trim();
        String strExchangeCode = this.pstnExchangeForm.getExchangeType().trim();
        Long exchangeId = pstnExchangeForm.getExchangeId();
        if ((strPstnHeadNumber == null || strPstnHeadNumber.equals(""))) {
            req.setAttribute("message", "assignPstnToExchange.error.invalidPstnHesdNumber");
            return false;
        }
        if ((strProvinceCode == null || strProvinceCode.equals(""))) {
            req.setAttribute("message", "assignPstnToExchange.error.invalidProvinceCode");
            return false;
        }
        if ((strExchangeCode == null || strExchangeCode.equals(""))) {
            req.setAttribute("message", "assignPstnToExchange.error.invalidExchangeCode");
            return false;
        }
        if (exchangeId == null) {
            req.setAttribute("message", "assignPstnToExchange.error.invalidExchangeId");
            return false;
        }

        return true;

    }

    public List<ImSearchBean> getListArea(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.areaCode, a.name) ");
        queryString.append("from Area a where 1=1  and parentCode is null ");
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.areaCode) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        queryString.append("order by a.areaCode ");
        Query queryObject = getSession().createQuery(queryString.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));
        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<ImSearchBean> tmpList = queryObject.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }
        return listImSearchBean;
    }

    public Long getListAreaSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select count(*) ");
        queryString.append("from Area a where 1=1 and parentCode is null ");
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.areaCode) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        Query queryObject = getSession().createQuery(queryString.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));
        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<Long> tmpList = queryObject.list();
        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);
        } else {
            return null;
        }
    }

    public List<ImSearchBean> getListAreaName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.areaCode, a.name) ");
        queryString.append("from Area a where 1=1  and parentCode is null ");
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.areaCode) = ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase());
        }
        Query queryObject = getSession().createQuery(queryString.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));
        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<ImSearchBean> tmpList = queryObject.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }

        return listImSearchBean;
    }
}
