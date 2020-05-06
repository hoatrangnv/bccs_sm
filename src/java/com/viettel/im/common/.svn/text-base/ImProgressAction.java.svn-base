package com.viettel.im.common;

import com.viettel.database.DAO.BaseDAOAction;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Doan Thanh 8
 * lay du lieu cho thanh progress
 *
 */
public class ImProgressAction extends BaseDAOAction {
    private static final Log log = LogFactory.getLog(ImSearchPopupAction.class);
    private String pageForward;

    //dinh nghia cac hang so pageForward
    private final String UPDATE_PROGRESS_DIV = "updateProgressDiv";

    //
    private Map listItem = new HashMap();

    public Map getListItem() {
        return listItem;
    }

    public void setListItem(Map listItem) {
        this.listItem = listItem;
    }

    /**
     *
     * author   : tamdt1
     * date     : 14/11/2009
     * desc     : tra ve du lieu cap nhat cho divProgress
     *
     */

    public String updateProgressDiv() throws Exception {
        log.info("Begin updateProgressDiv of ImProgressAction");

        HttpServletRequest req = getRequest();
        String showProgressClass = req.getParameter("showProgressClass");
        String showProgressMethod = req.getParameter("showProgressMethod");

        String strMessage = (String) getObject(showProgressClass, showProgressMethod, req);
        if (strMessage == null || strMessage.trim().equals("")) {
            strMessage = "";
        }

        //
        listItem.put("progressDivData", strMessage);

        //return
        pageForward = UPDATE_PROGRESS_DIV;
        log.info("End updateProgressDiv of ImProgressAction");
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
    private Object getObject(String strClassName, String strMethodName, HttpServletRequest request) {
        try {
            //lay lop + phuong thuc tim kiem thuc su
            Class searchClass = Class.forName(strClassName);
            Class paramTypes[] = new Class[1];
            paramTypes[0] = Class.forName("javax.servlet.http.HttpServletRequest");
            Method searchMethod = searchClass.getMethod(strMethodName, paramTypes);

            //tao the hien
            Constructor searchClassConstructor = searchClass.getConstructor(new Class[0]);
            BaseDAOAction searchClassInstance = (BaseDAOAction) searchClassConstructor.newInstance(new Object[0]);
            searchClassInstance.setSession(this.getSession());

            //goi ham tim du lieu
            Object result = searchMethod.invoke(searchClassInstance, request);

            //
            return result;

        } catch (Throwable e) {
            e.printStackTrace();

            //
            return null;
        }
    }

}
