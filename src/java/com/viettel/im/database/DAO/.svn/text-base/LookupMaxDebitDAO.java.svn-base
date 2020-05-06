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
import com.viettel.im.database.BO.ChannelType;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;

/**
 *
 * @author Tutm1
 * @date 15/09/2011
 * @purpose tra cuu han muc 
 *
 */
public class LookupMaxDebitDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(LookupMaxDebitDAO.class);
    private String pageForward;
    private SaleChannelTypeForm saleChannelTypeForm = new SaleChannelTypeForm();

    public SaleChannelTypeForm getSaleChannelTypeForm() {
        return saleChannelTypeForm;
    }

    public void setSaleChannelTypeForm(SaleChannelTypeForm saleChannelTypeForm) {
        this.saleChannelTypeForm = saleChannelTypeForm;
    }
    public static final String SEARCH_MAX_DEBIT = "lookupMaxDebit";
    public static final String SEARCH_MAX_DEBIT_RESULT = "lookupMaxDebitResult";
    public static final int SEARCH_RESULT_LIMIT = 100;


    public String preparePage() throws Exception {
        log.info("Begin method preparePage of LookupMaxDebitDAO ...");

        HttpServletRequest req = getRequest();
        SaleChannelTypeForm f = this.saleChannelTypeForm;
        f.resetForm();
        pageForward = SEARCH_MAX_DEBIT;
        log.info("End method preparePage of LookupMaxDebitDAO");
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of LookMaxDebitDAO ...");
        pageForward = SEARCH_MAX_DEBIT_RESULT;
        log.info("End method preparePage of LookMaxDebitDAO");
        return pageForward;
    }

    /*Author: TuTM1
     * Purpose: tim kiem Channel
     */
    public String searchChannel() throws Exception {
        log.info("Begin method searchChannelType of ChannelTypeDAO ...");

        HttpServletRequest req = getRequest();
        SaleChannelTypeForm f = this.saleChannelTypeForm;
        List listParameter = new ArrayList();
        String strQuery = "";

        // tim kiem theo staff
        if (Constant.OWNER_TYPE_STAFF.equals(f.getStockType())) {
            strQuery = "select new com.viettel.im.client.bean.LookupMaxDebitBean(staffId, staffCode, name, '2') from Staff as model where 1=1";
            strQuery += " and model.status = 1 ";

            if (f.getCode() != null && !f.getCode().trim().equals("")) {
                listParameter.add("%" + f.getCode().trim().toLowerCase() + "%");
                strQuery += " and lower(model.shopCode) like ? ";
            }

            if (f.getName() != null && !f.getName().trim().equals("")) {
                listParameter.add("%" + f.getName().trim().toLowerCase() + "%");
                strQuery += " and lower(model.name) like ? ";
            }
        } else {// tim kiem theo shop
            strQuery = "select new com.viettel.im.client.bean.LookupMaxDebitBean(shopId, shopCode, name, '1') from Shop as model where 1=1";
            strQuery += " and model.status = 1 ";

            if (f.getCode() != null && !f.getCode().trim().equals("")) {
                listParameter.add("%" + f.getCode().trim().toLowerCase() + "%");
                strQuery += " and lower(model.shopCode) like ? ";
            }

            if (f.getName() != null && !f.getName().trim().equals("")) {
                listParameter.add("%" + f.getName().trim().toLowerCase() + "%");
                strQuery += " and lower(model.name) like ? ";
            }
        }
        

//        strQuery += "order by nlssort(name,'nls_sort=Vietnamese') asc";
        Query q = getSession().createQuery(strQuery);
        for (int i = 0; i < listParameter.size(); i++) {
            q.setParameter(i, listParameter.get(i));
        }

        List listChannel = new ArrayList();
        listChannel = q.list();

        req.getSession().removeAttribute("listChannel");
        req.getSession().setAttribute("listChannel", listChannel);

        req.setAttribute("returnMsg", "channelType.search");
        List paramMsg = new ArrayList();
        paramMsg.add(listChannel.size());
        req.setAttribute("paramMsg", paramMsg);

        pageForward = SEARCH_MAX_DEBIT;

        log.info("End method searchChannelType of ChannelTypeDAO");

        return pageForward;
    }

}
