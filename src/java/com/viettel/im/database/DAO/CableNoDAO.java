/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.CableNoForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

/**
 *
 * @author MrSun
 */
public class CableNoDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(CableNoDAO.class);
    CableNoForm cableNoForm = new CableNoForm();
    private final int SEARCH_RESULT_LIMIT = 1000;
    private String pageForward;
    private static final String PREPARE_PAGE = "preparePage";
    private static final String LIST_CABLE_NO_PAGE = "listCableNoPage";

    public CableNoForm getCableNoForm() {
        return cableNoForm;
    }

    public void setCableNoForm(CableNoForm cableNoForm) {
        this.cableNoForm = cableNoForm;
    }

    public String preparePage() throws Exception {
        log.info("----->Begin method preparePage of MdfDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                cableNoForm = new CableNoForm();
                req.getSession().setAttribute("listCableNo", null);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("----->End method preparePage of MdfDAO ...");

        pageForward = PREPARE_PAGE;

        return pageForward;
    }

    public String searchCableNo() throws Exception {
        log.info("----->Begin method searchMdf of MdfDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                List listCableNo = getListCableNo(cableNoForm);
                req.getSession().setAttribute("listCableNo", listCableNo);
                if (listCableNo.size() >= SEARCH_RESULT_LIMIT) {
                    req.setAttribute("returnMsg", "search.result");
                } else {
                    req.setAttribute("returnMsg", "search.result");
                    List listMessageParam = new ArrayList();
                    listMessageParam.add(listCableNo.size());
                    req.setAttribute("returnMsgParam", listMessageParam);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("----->End method searchMdf of MdfDAO ...");

        pageForward = PREPARE_PAGE;

        return pageForward;
    }

    public List getListCableNo(CableNoForm cableNoForm) {

        if (cableNoForm == null) {
            cableNoForm = new CableNoForm();
        }
        String queryString = "SELECT PORT_POSITION as portPosition,"
                + "STATUS as status,"
                + "CABLE_BOX_CODE as cableBoxCode,"
                + "CABLE_BOX_NAME as cableBoxName,"
                + "BOARD_CODE as boardCode,"
                + "BOARD_NAME as boardName,"
                + "MDF_CODE as mdfCode,"
                + "MDF_NAME as mdfName,"
                + "PROVINCE_CODE as provinceCode, "
                + "PROVINCE_NAME as provinceName "
                + "FROM V_CABLE_NO_FULL a where 1=1 ";

        List parameterList = new ArrayList();
        if (cableNoForm.getProvinceCode() != null && !cableNoForm.getProvinceCode().trim().equals("")) {
            queryString += " AND upper(PROVINCE_CODE) = ? ";
            parameterList.add(cableNoForm.getProvinceCode().trim().toUpperCase());
        }
        if (cableNoForm.getMdfId() != null && !cableNoForm.getMdfId().equals(0L)) {
            queryString += " AND MDF_ID = ? ";
            parameterList.add(cableNoForm.getMdfId());
        }
        if (cableNoForm.getMdfCode() != null && !cableNoForm.getMdfCode().trim().equals("")) {
            queryString += " AND upper(MDF_CODE) = ? ";
            parameterList.add(cableNoForm.getMdfCode().trim().toUpperCase());
        }        
        if (cableNoForm.getBoardId() != null && !cableNoForm.getBoardId().equals(0L)) {
            queryString += " AND BOARD_ID = ? ";
            parameterList.add(cableNoForm.getBoardId());
        }
        if (cableNoForm.getBoardCode() != null && !cableNoForm.getBoardCode().trim().equals("")) {
            queryString += " AND upper(BOARD_CODE) = ? ";
            parameterList.add(cableNoForm.getBoardCode().trim().toUpperCase());
        }
        if (cableNoForm.getCableBoxId() != null && !cableNoForm.getCableBoxId().equals(0L)) {
            queryString += " AND CABLE_BOX_ID = ? ";
            parameterList.add(cableNoForm.getCableBoxId());
        }
        if (cableNoForm.getCableBoxCode() != null && !cableNoForm.getCableBoxCode().trim().equals("")) {
            queryString += " AND upper(CABLE_BOX_CODE) = ? ";
            parameterList.add(cableNoForm.getCableBoxCode().trim().toUpperCase());
        }

        if (cableNoForm.getFromPort() != null && !cableNoForm.getFromPort().trim().equals("")) {
            queryString += " AND PORT_POSITION >= ? ";
            parameterList.add(Long.valueOf(cableNoForm.getFromPort().trim()));
        }
        if (cableNoForm.getToPort() != null && !cableNoForm.getToPort().trim().equals("")) {
            queryString += " AND PORT_POSITION <= ? ";
            parameterList.add(Long.valueOf(cableNoForm.getToPort().trim()));
        }
        
        if (cableNoForm.getStatus() != null) {
            queryString += " AND STATUS = ? ";
            parameterList.add(cableNoForm.getStatus());
        }

        queryString += " ORDER BY provinceCode, mdfCode, boardCode, cableBoxCode, portPosition ";

        Query queryObject = getSession().createSQLQuery(queryString).
                addScalar("portPosition", Hibernate.LONG).
                addScalar("status", Hibernate.LONG).
                addScalar("cableBoxCode", Hibernate.STRING).
                addScalar("cableBoxName", Hibernate.STRING).
                addScalar("boardCode", Hibernate.STRING).
                addScalar("boardName", Hibernate.STRING).
                addScalar("mdfCode", Hibernate.STRING).
                addScalar("mdfName", Hibernate.STRING).
                addScalar("provinceCode", Hibernate.STRING).
                addScalar("provinceName", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(CableNoForm.class));
        queryObject.setMaxResults(SEARCH_RESULT_LIMIT);
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of MdfDAO ...");
        pageForward = LIST_CABLE_NO_PAGE;
        log.info("End method pageNavigator of MdfDAO");
        return pageForward;
    }

    public Long getListBoardSize(ImSearchBean imSearchBean) {

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) from Boards a ");
        strQuery1.append("where 1 = 1 ");

//        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
//            strQuery1.append("and lower(a.province) = ? ");
//            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
//        } else {
//            return 0L;
//        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.code) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_USE);

        strQuery1.append("order by nlssort(a.code, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
    }

    public List<ImSearchBean> getBoardName(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
        strQuery1.append("from Boards a ");
        strQuery1.append("where 1 = 1 ");

//        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
//            strQuery1.append("and lower(a.province) = ? ");
//            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
//        } else {
//            return listImSearchBean;
//        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.code) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_USE);

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.code, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    public List<ImSearchBean> getListBoard(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
        strQuery1.append("from Boards a ");
        strQuery1.append("where 1 = 1 ");

//        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
//            strQuery1.append("and lower(a.province) = ? ");
//            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
//        } else {
//            return listImSearchBean;
//        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.code) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_USE);

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.code, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    public Long getListCableBoxSize(ImSearchBean imSearchBean) {

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) from CableBox a ");
        strQuery1.append("where 1 = 1 ");

//        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
//            strQuery1.append("and lower(a.province) = ? ");
//            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
//        } else {
//            return 0L;
//        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.code) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_USE);

        strQuery1.append("order by nlssort(a.code, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
    }

    public List<ImSearchBean> getCableBoxName(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
        strQuery1.append("from CableBox a ");
        strQuery1.append("where 1 = 1 ");

//        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
//            strQuery1.append("and lower(a.province) = ? ");
//            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
//        } else {
//            return listImSearchBean;
//        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.code) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_USE);

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.code, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    public List<ImSearchBean> getListCableBox(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
        strQuery1.append("from CableBox a ");
        strQuery1.append("where 1 = 1 ");

//        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
//            strQuery1.append("and lower(a.province) = ? ");
//            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
//        } else {
//            return listImSearchBean;
//        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.code) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_USE);

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.code, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }
}
