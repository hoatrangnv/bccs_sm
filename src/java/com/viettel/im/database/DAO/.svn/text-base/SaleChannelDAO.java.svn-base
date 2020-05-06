package com.viettel.im.database.DAO;

import com.viettel.database.BO.ActionResultBO;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.UserToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import com.viettel.database.DAO.BaseDAOAction;
import org.hibernate.Query;
import java.util.List;


/**
 *
 * @author tamdt1
 * date 18/02/2009
 *
 */
public class SaleChannelDAO extends BaseDAOAction{
    private Log log = LogFactory.getLog(SaleChannelDAO.class);

    private String pageForward;

    //dinh nghia cac hang so pageForward
    public static final String ADD_NEW_SALE_CHANNEL = "addNewSaleChannel";
    public static final String SEARCH_SALE_CHANNEL = "searchSaleChannel";


    /**
     *
     * author tamdt1
     * date: 18/02/2009
     * chuan bi form them kenh ban hang moi
     *
     */
    public ActionResultBO prepareAddNewSaleChannel(ActionForm form, HttpServletRequest req) throws Exception {

        log.info("Begin method prepareAddNewSaleChannel of SaleChannelDAO ...");

        ActionResultBO actionResult = new ActionResultBO();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
//                SaleChannelTypeForm saleChannelTypeForm = (SaleChannelTypeForm) form;

                pageForward = ADD_NEW_SALE_CHANNEL;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method prepareAddNewSaleChannel of SaleChannelDAO");
        actionResult.setPageForward(pageForward);
        return actionResult;
    }

    /**
     *
     * author tamdt1
     * date: 18/02/2009
     * chuan bi form tim kiem thong tin ve kenh ban hang
     *
     */
    public ActionResultBO prepareSearchSaleChannel(ActionForm form, HttpServletRequest req) throws Exception {
        log.info("Begin method prepareAddNewSaleChannel of SaleChannelDAO ...");
        ActionResultBO actionResult = new ActionResultBO();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                pageForward = SEARCH_SALE_CHANNEL;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method prepareAddNewSaleChannel of SaleChannelDAO");
        actionResult.setPageForward(pageForward);
        return actionResult;
    }

}
