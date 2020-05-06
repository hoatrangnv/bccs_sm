/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.SaleTransEmolaForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.SaleTransEmola;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class SaleTransPayMethodEMolaDAO extends BaseDAOAction{
    private static final Logger log = Logger.getLogger(SaleTransPayMethodEMolaDAO.class);
    private String pageForward;
    private final String SEARCH_SALE_TRANS_PAYMETHOD_EMOLA = "saleTransPayMethodEmola";
    private final String PAGE_NAVIGATOR = "pageNavigator";
    private final String REQUEST_MESSAGE = "message";
    private final String SESSION_SALE_TRANS_PAYMETHOD_EMOLA_LIST = "listSaleTransPayMethodEMola";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final Long MAX_SEARCH_RESULT = 150L;
    SaleTransEmolaForm saleTransEmolaForm = new SaleTransEmolaForm();

    public SaleTransEmolaForm getSaleTransEmolaForm() {
        return saleTransEmolaForm;
    }

    public void setSaleTransEmolaForm(SaleTransEmolaForm saleTransEmolaForm) {
        this.saleTransEmolaForm = saleTransEmolaForm;
    }
    
     public String preparePage() throws Exception {
        log.info("Begin method preparePage SaleTransPayMethodEMolaDAO");
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                pageForward = Constant.ERROR_PAGE;
                return pageForward;
            }
           
            pageForward = SEARCH_SALE_TRANS_PAYMETHOD_EMOLA;
        } catch (Exception e) {
            log.debug("load failed", e);
            return pageForward;
        }
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of SaleTransPayMethodEMolaDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method pageNavigator of SaleTransPayMethodEMolaDAO");
        pageForward = PAGE_NAVIGATOR;
        return pageForward;
    }
    
    public String searchSaleTransEmola() {

        log.info("Begin method searchStockCardLost of SaleTransPayMethodEMolaDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();
        req.getSession().setAttribute(SESSION_SALE_TRANS_PAYMETHOD_EMOLA_LIST, new ArrayList<SaleTransEmola>());
        String isdnEmola = saleTransEmolaForm.getIsdnEmola();
        String saleTransCode = saleTransEmolaForm.getSaleTransCode();
        String custName = saleTransEmolaForm.getCustName();
        Date fromDate = saleTransEmolaForm.getFromDate();
        Date toDate = saleTransEmolaForm.getToDate();

        List<SaleTransEmola> listSaleTransEmola = getListSaleTransEmola(session, isdnEmola, saleTransCode, custName, fromDate, toDate);
        req.getSession().setAttribute(SESSION_SALE_TRANS_PAYMETHOD_EMOLA_LIST, listSaleTransEmola);

        if (listSaleTransEmola != null && listSaleTransEmola.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "search.success.list.request");
            List listParamValue = new ArrayList();
            listParamValue.add(listSaleTransEmola.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParamValue);

        } else {
            req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request");
        }

        pageForward = SEARCH_SALE_TRANS_PAYMETHOD_EMOLA;

        return pageForward;
    }
    
    public List<SaleTransEmola> getListSaleTransEmola(Session session, String isdnEmola, String saleTransCode, String custName, Date fromDate, Date toDate) {

        List<SaleTransEmola> listSaleTransEmola = new ArrayList<SaleTransEmola>();
        StringBuilder strQueryRequestList = new StringBuilder("");
        
        strQueryRequestList.append(" SELECT sale_trans_id AS saleTransId , sale_trans_code AS saleTransCode, cust_name AS custName, ");
        strQueryRequestList.append(" isdn_emola AS isdnEmola, sale_trans_date AS saleTransDate, status AS status, note AS note ");
        strQueryRequestList.append(" FROM sale_trans_emola WHERE 1=1 ");

        List listParam = new ArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        if (isdnEmola != null && !("").equals(isdnEmola)) {
                strQueryRequestList.append(" AND isdn_emola = ? ");
            listParam.add(isdnEmola);
        }
        if (saleTransCode != null && !saleTransCode.equals("")) {
            strQueryRequestList.append(" AND sale_trans_code = ? ");
            listParam.add(saleTransCode);
        }
        if (custName != null && !custName.equals("")) {
            strQueryRequestList.append(" AND UPPER(cust_name) like ? ");
            listParam.add("%" +custName.toUpperCase().trim()+ "%");
        }
        if (fromDate != null) {
            strQueryRequestList.append(" AND sale_trans_date >= to_date(?,'dd/mm/yyyy') ");
            listParam.add(dateFormat.format(fromDate));
        }
        if (toDate != null) {
            strQueryRequestList.append(" AND sale_trans_date < to_date(?,'dd/mm/yyyy') + 1 ");
            listParam.add(dateFormat.format(toDate));
        }
        strQueryRequestList.append(" ORDER BY sale_trans_date DESC ");

        Query queryRequestList = session.createSQLQuery(strQueryRequestList.toString()).
                addScalar("saleTransId", Hibernate.LONG).
                addScalar("saleTransCode", Hibernate.STRING).
                addScalar("custName", Hibernate.STRING).
                addScalar("isdnEmola", Hibernate.STRING).
                addScalar("saleTransDate", Hibernate.DATE).
                addScalar("status", Hibernate.STRING).
                addScalar("note", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(SaleTransEmola.class));

        for (int i = 0; i < listParam.size(); i++) {
            queryRequestList.setParameter(i, listParam.get(i));
        }
        listSaleTransEmola = queryRequestList.list();

        return listSaleTransEmola;
    }
    
}
