/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.MdfForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.Mdf;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

/**
 *
 * @author MrSun
 */
public class MdfDAO extends BaseDAOAction {

    private MdfForm mdfForm = new MdfForm();
    private static final Log log = LogFactory.getLog(MdfDAO.class);
    private final int SEARCH_RESULT_LIMIT = 1000;
    private String pageForward;
    private static final String ADD_MDF = "addMdf";

    public MdfForm getMdfForm() {
        return mdfForm;
    }

    public void setMdfForm(MdfForm mdfForm) {
        this.mdfForm = mdfForm;
    }

    public Mdf findById(java.lang.Long id) {
        log.debug("getting Mdf instance with id: " + id);
        try {
            Mdf instance = (Mdf) getSession().get(
                    "com.viettel.im.database.BO.Mdf", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public String preparePage() throws Exception {
        log.info("----->Begin method preparePage of MdfDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                mdfForm = new MdfForm();
                searchMdf();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("----->End method preparePage of MdfDAO ...");

        pageForward = ADD_MDF;

        return pageForward;
    }

    public String searchMdf() throws Exception {
        log.info("----->Begin method searchMdf of MdfDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                List listMdf = getListMdf(mdfForm);
                req.getSession().setAttribute("listMdf", listMdf);
                if (listMdf.size() >= SEARCH_RESULT_LIMIT) {
                    req.setAttribute("returnMsg", "search.result");
                } else {
                    req.setAttribute("returnMsg", "search.result");
                    List listMessageParam = new ArrayList();
                    listMessageParam.add(listMdf.size());
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

        pageForward = ADD_MDF;

        return pageForward;
    }

    private List getListMdf(MdfForm mdfForm) {

        if (mdfForm == null) {
            mdfForm = new MdfForm();
        }
        String queryString = " from Mdf a where 1=1 ";
        List parameterList = new ArrayList();

        if (mdfForm.getCode() != null && !mdfForm.getCode().trim().equals("")) {
            queryString += "and upper(a.code) = ? ";
            parameterList.add(mdfForm.getCode().trim().toUpperCase());
        }
        if (mdfForm.getName() != null && !mdfForm.getName().trim().equals("")) {
            queryString += "and upper(a.name) like ? ";
            parameterList.add("%" + mdfForm.getName().trim().toUpperCase() + "%");
        }

        if (mdfForm.getStatus() != null) {
            queryString += "and a.status = ? ";
            parameterList.add(mdfForm.getStatus());
        }
        if (mdfForm.getProvince() != null && !mdfForm.getProvince().trim().equals("")) {
            queryString += "and a.province = ? ";
            parameterList.add(mdfForm.getProvince().trim());
        }

        queryString += " order by province ";

        Query queryObject = getSession().createQuery(queryString);
        queryObject.setMaxResults(SEARCH_RESULT_LIMIT);
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of MdfDAO ...");
        pageForward = "listMdf";
        log.info("End method pageNavigator of MdfDAO");
        return pageForward;
    }

    public String saveMdf() throws Exception {
        log.info("----->Begin method saveMdf of MdfDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                Mdf mdf = new Mdf();
                if (mdfForm.getMdfId() == null || mdfForm.getMdfId().equals(0L)) {//add
                    if (!checkValidateToAdd(mdfForm, req)) {
                        pageForward = ADD_MDF;
                        log.info("----->End method saveMdf of MdfDAO ...");
                        return pageForward;
                    }
                    mdfForm.copyToBO(mdf);
                    mdf.setMdfId(getSequence("MDF_SEQ"));
                    this.save(mdf);
                    req.setAttribute(Constant.RETURN_MESSAGE, "succuss.mdf.add");
                } else {//edit
                    if (!checkValidateToEdit(mdfForm, req)) {
                        pageForward = ADD_MDF;
                        log.info("----->End method saveMdf of MdfDAO ...");
                        return pageForward;
                    }
                    mdfForm.copyToBO(mdf);
                    this.update(mdf);
                    req.setAttribute(Constant.RETURN_MESSAGE, "succuss.mdf.edit");
                }
                mdfForm.clearForm();
                List<Mdf> listMdf = getListMdf(mdfForm);
                listMdf.add(mdf);
                req.getSession().setAttribute("listMdf", listMdf);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("----->End method saveMdf of MdfDAO ...");

        pageForward = ADD_MDF;

        return pageForward;
    }

    private boolean checkValidateToAdd(MdfForm mdfForm, HttpServletRequest req) {
        if (checkValidateForm(mdfForm, req)) {
            String strQuery = "select count(*) from Mdf as model where upper(model.code)=?";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, mdfForm.getCode().trim().toUpperCase());
            Long count = (Long) q.list().get(0);
            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute(Constant.RETURN_MESSAGE, "error.mdf.code.exist");
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean checkValidateToEdit(MdfForm mdfForm, HttpServletRequest req) {
        if (checkValidateForm(mdfForm, req)) {
            String strQuery = "select count(*) from Mdf as model where upper(model.code)=? and not model.mdfId = ?";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, mdfForm.getCode().trim().toUpperCase());
            q.setParameter(1, mdfForm.getMdfId());
            Long count = (Long) q.list().get(0);
            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute(Constant.RETURN_MESSAGE, "error.mdf.code.exist");
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean checkValidateForm(MdfForm mdfForm, HttpServletRequest req) {
        if (mdfForm == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "system.error");
            return false;
        }
        if (mdfForm.getCode() == null || mdfForm.getCode().trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "error.mdf.code.null");
            return false;
        }
        if (mdfForm.getName() == null || mdfForm.getName().trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "error.mdf.name.null");
            return false;
        }
        if (mdfForm.getProvince() == null || mdfForm.getProvince().trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "error.mdf.province.null");
            return false;
        }
        if (mdfForm.getStatus() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "error.mdf.status.null");
            return false;
        }

        Area area = getArea(mdfForm.getProvince());
        if (area == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "error.mdf.province.wrong");
            return false;
        }
        if (area.getParentCode() != null && !area.getParentCode().trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "error.mdf.province.wrong");
            return false;
        }

        return true;
    }

    public String prepareEditMdf() throws Exception {
        log.info("----->Begin method prepareEditMdf of MdfDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                String mdfId = (String) req.getParameter("mdfId");
                if (mdfId == null || mdfId.trim().equals("")) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "error.mdf.mdfid.null");

                    pageForward = ADD_MDF;
                    log.info("----->End method prepareEditMdf of MdfDAO ...");
                    return pageForward;
                }

                Mdf mdf = this.findById(Long.valueOf(mdfId.trim()));
                this.mdfForm.clearForm();
                this.mdfForm.copyToForm(mdf);

                pageForward = ADD_MDF;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("----->End method prepareEditMdf of MdfDAO ...");

        pageForward = ADD_MDF;

        return pageForward;
    }

    public String cancelEditMdf() throws Exception {
        log.info("----->Begin method cancelEditMdf of MdfDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                this.mdfForm.clearForm();
                List<Mdf> listMdf = getListMdf(mdfForm);
                req.getSession().setAttribute("listMdf", listMdf);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("----->End method cancelEditMdf of MdfDAO ...");

        pageForward = ADD_MDF;

        return pageForward;
    }

    public String deleteMdf() throws Exception {
        log.info("----->Begin method deleteMdf of MdfDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                String mdfId = (String) req.getParameter("mdfId");
                if (mdfId == null || mdfId.trim().equals("")) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "error.mdf.mdfid.null");

                    pageForward = ADD_MDF;
                    log.info("----->End method prepareEditMdf of MdfDAO ...");
                    return pageForward;
                }

                Mdf mdf = this.findById(Long.valueOf(mdfId.trim()));
                if (mdf == null || mdf.getMdfId() == null) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "error.mdf.notfound");
                    pageForward = ADD_MDF;
                    log.info("----->End method deleteMdf of MdfDAO ...");
                    return pageForward;
                }
                if (!checkValidateToDelete(mdf.getMdfId(), req)) {
                    pageForward = ADD_MDF;
                    log.info("----->End method deleteMdf of MdfDAO ...");
                    return pageForward;
                } else {
                    this.delete(mdf);
                    this.mdfForm.clearForm();
                    List<Mdf> listMdf = getListMdf(mdfForm);
                    req.getSession().setAttribute("listMdf", listMdf);
                    req.setAttribute(Constant.RETURN_MESSAGE, "succuss.mdf.delete");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        pageForward = ADD_MDF;
        log.info("----->End method deleteMdf of MdfDAO ...");
        return pageForward;
    }

    private boolean checkValidateToDelete(Long mdfId, HttpServletRequest req) {
        String strQuery = "select count(*) from Dslam as model where upper(model.mdfId)=?";
        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, mdfId);
        Long count = (Long) q.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute(Constant.RETURN_MESSAGE, "error.mdf.using");
            return false;
        }

        return true;
    }

    public Area getArea(String areaCode) {
        if (areaCode == null || areaCode.trim().equals("")) {
            return null;
        }
        AreaDAO areaDAO = new AreaDAO();
        areaDAO.setSession(getSession());
        return areaDAO.findByAreaCode(areaCode.trim().toUpperCase());
    }
}
