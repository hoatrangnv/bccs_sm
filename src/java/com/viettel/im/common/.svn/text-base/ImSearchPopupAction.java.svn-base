package com.viettel.im.common;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.ImSearchForm;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Doan Thanh 8
 * bat popup tra cuu thong tin
 *
 */
public class ImSearchPopupAction extends BaseDAOAction {
    private static final Log log = LogFactory.getLog(ImSearchPopupAction.class);
    private String pageForward;

    //dinh nghia cac hang so pageForward
    private final String IM_SEARCH_POPUP = "imSearchPopup";
    private final String GET_NAME = "getName";

    //ten cac bien request, session
    private final String REQUEST_LIST_IM_SEARCH_BEAN = "listImSearchBean";
    private final String REQUEST_LIST_IM_SEARCH_SIZE = "listImSearchSize";

    //
    private Map listItem = new HashMap();

    public Map getListItem() {
        return listItem;
    }

    public void setListItem(Map listItem) {
        this.listItem = listItem;
    }

    //dinh nghia cac bien form
    private ImSearchForm imSearchForm = new ImSearchForm();

    public ImSearchForm getImSearchForm() {
        return imSearchForm;
    }

    public void setImSearchForm(ImSearchForm imSearchForm) {
        this.imSearchForm = imSearchForm;
    }

    public List<ImSearchBean> searchDefault(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 17/11/2009
     * purpose  : bat popup tra cuu thong tin
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ImSearchPopupAction...");

        HttpServletRequest req = getRequest();
        
        //lay cac tham so
        String searchClassName = req.getParameter("searchClassName");
        String searchMethodName = req.getParameter("searchMethodName");
        String codeVariable = req.getParameter("codeVariable");
        String nameVariable = req.getParameter("nameVariable");
        String codeLabel = req.getParameter("codeLabel");
        String nameLabel = req.getParameter("nameLabel");
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String otherParam = req.getParameter("otherParam");
        String otherParamValue = req.getParameter("otherParamValue");

        //thiet lap cac gia tri mac dinh
        searchClassName = (searchClassName != null && !searchClassName.trim().equals("")) ? searchClassName.trim() : "com.viettel.im.common.ImSearchPopupAction";
        searchMethodName = (searchMethodName != null && !searchMethodName.trim().equals("")) ? searchMethodName.trim() : "searchDefault";
        codeVariable = (codeVariable != null && !codeVariable.trim().equals("")) ? codeVariable.trim() : "code";
        nameVariable = (nameVariable != null && !nameVariable.trim().equals("")) ? nameVariable.trim() : "name";
        codeLabel = (codeLabel != null && !codeLabel.trim().equals("")) ? codeLabel.trim() : "Mã";
        nameLabel = (nameLabel != null && !nameLabel.trim().equals("")) ? nameLabel.trim() : "Tên";

        this.imSearchForm.setSearchClassName(searchClassName);
        this.imSearchForm.setSearchMethodName(searchMethodName);
        this.imSearchForm.setCodeVariable(codeVariable);
        this.imSearchForm.setNameVariable(nameVariable);
        this.imSearchForm.setCodeLabel(codeLabel);
        this.imSearchForm.setNameLabel(nameLabel);
        this.imSearchForm.setCode(code);
        this.imSearchForm.setName(name);
        this.imSearchForm.setOtherParam(otherParam);
        this.imSearchForm.setOtherParamValue(otherParamValue);

        search();

        pageForward = IM_SEARCH_POPUP;

        log.info("End method preparePage of ImSearchPopupAction");

        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 17/11/2009
     * purpose  : tra cuu thong tin
     *
     */
    public String search() throws Exception {
        log.info("Begin method search of ImSearchPopupAction...");

        HttpServletRequest req = getRequest();

        String searchClassName = this.imSearchForm.getSearchClassName();
        String searchMethodName = this.imSearchForm.getSearchMethodName();

        String code = this.imSearchForm.getCode();
        String name = this.imSearchForm.getName();
        String otherParamValue = this.imSearchForm.getOtherParamValue();

        ImSearchBean imSearchBean = new ImSearchBean(code, name);
        imSearchBean.setOtherParamValue(otherParamValue);
        imSearchBean.setHttpServletRequest(req);
        List<ImSearchBean> listImSearchBean = (List<ImSearchBean>) getObject(searchClassName, searchMethodName, imSearchBean);
        String strListImSearchSize = "0/ ";
        NumberFormat nf = NumberFormat.getInstance();
        if(listImSearchBean != null) {
            strListImSearchSize = nf.format(listImSearchBean.size()) + "/ ";
        }
        Long listImSearchSize = (Long) getObject(searchClassName, searchMethodName + "Size", imSearchBean);
        if (listImSearchSize != null) {
            strListImSearchSize += nf.format(listImSearchSize);
        }

        req.setAttribute(REQUEST_LIST_IM_SEARCH_BEAN, listImSearchBean);
        req.setAttribute(REQUEST_LIST_IM_SEARCH_SIZE, strListImSearchSize);

        pageForward = IM_SEARCH_POPUP;
        log.info("End method search of ImSearchPopupAction");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 17/11/2009
     * purpose  : lay thong tin ten tu code
     *
     */
    public String getName() throws Exception {
        log.info("Begin method getName of ImSearchPopupAction...");

        HttpServletRequest req = getRequest();

        String searchClassName = req.getParameter("searchClassName");
        String searchMethodName = req.getParameter("searchMethodName");
        String getNameMethod = req.getParameter("getNameMethod");
        String code = req.getParameter("code");
        String otherParamValue = req.getParameter("otherParamValue");

        ImSearchBean imSearchBean = new ImSearchBean(code, "");
        imSearchBean.setOtherParamValue(otherParamValue);
        imSearchBean.setHttpServletRequest(req);
        List<ImSearchBean> listImSearchBean = (List<ImSearchBean>) getObject(searchClassName, getNameMethod, imSearchBean);

        if (listImSearchBean != null && listImSearchBean.size() == 1) {
            this.listItem.put("codeValue", listImSearchBean.get(0).getCode());
            this.listItem.put("nameValue", listImSearchBean.get(0).getName());
        } else {
            this.listItem.put("codeValue", "");
        }

        pageForward = GET_NAME;
        log.info("End method getName of ImSearchPopupAction");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 17/11/2009
     * purpose  : goi ham tim kiem o lop khac
     * dau vao:
     *              - ten lop co chua ham tim kiem (vi du com.viettel.im.database.DAO.ShopDAO)
     *              - ten ham tim kiem (vi du searchShop): ham tim kiem nhan vao 1 doi tuong ImSearchBean va tra ve 1 danh sach cac ImSearchBean
     *              - doi tuong can search
     * dau ra:
     *              - danh sach cac doi tuong can tim
     */
    private Object getObject(String strClassName, String strMethodName, ImSearchBean imSearchBean) {
        try {
            //lay lop + phuong thuc tim kiem thuc su
            Class searchClass = Class.forName(strClassName);
            Class paramTypes[] = new Class[1];
            paramTypes[0] = imSearchBean.getClass();
            Method searchMethod = searchClass.getMethod(strMethodName, paramTypes);

            //tao the hien
            Constructor searchClassConstructor = searchClass.getConstructor(new Class[0]);
            BaseDAOAction searchClassInstance = (BaseDAOAction) searchClassConstructor.newInstance(new Object[0]);
            searchClassInstance.setSession(this.getSession());

            //goi ham tim du lieu
            Object result = searchMethod.invoke(searchClassInstance, imSearchBean);

            //
            return result;

        } catch (Throwable e) {
            e.printStackTrace();

            //
            return null;
        }
    }

}
