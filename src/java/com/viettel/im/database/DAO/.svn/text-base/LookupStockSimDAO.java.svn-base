/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.database.BO.StockSimPrePaid;
import org.hibernate.Query;
import javax.servlet.http.HttpServletRequest;

import com.viettel.im.client.form.LookupStockSimForm;
import java.math.BigInteger;
import org.hibernate.Query;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AnDV
 */
public class LookupStockSimDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(LookupStockSimDAO.class);
    private String pageForward;
    private final String LOOKUP_STOCK_SIM = "lookupStockSim";
    private final String REQUEST_MESSAGE = "message";
    public static final int SEARCH_RESULT_LIMIT = 1000;
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String SESSION_LIST_STOCK_SIM = "listStockSim";
    LookupStockSimForm lookUpStockSimForm = new LookupStockSimForm();

    public LookupStockSimForm getLookUpStockSimForm() {
        return lookUpStockSimForm;
    }

    public void setLookUpStockSimForm(LookupStockSimForm lookUpStockSimForm) {
        this.lookUpStockSimForm = lookUpStockSimForm;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of LookupIsdnDAO ...");
        pageForward = LOOKUP_STOCK_SIM;
        return pageForward;
    }

    List<StockSimPrePaid> getListStockSim() throws Exception {
        LookupStockSimForm f = this.lookUpStockSimForm;
        List parameterList = new ArrayList();
        
        /*START ANHDK: R5705: Backup cac bang du lieu lon -> query du lieu trong view*/
        String queryString = "from ViewStockSim as model where 1=1 ";
        /*END ANHDK*/
        
        if (f.getHlrId() != null && !f.getHlrId().trim().equals("")) {
            queryString += " and model.hlrId = ? ";
            parameterList.add(f.getHlrId().trim());
        }
        if (f.getHlrStatus() != null && !f.getHlrStatus().trim().equals("")) {
            queryString += " and model.hlrStatus = ? ";
            parameterList.add(Long.parseLong(f.getHlrStatus().trim()));
        }
         if (f.getStockModelId() != null && !f.getStockModelId().trim().equals("")) {
            queryString += " and model.stockModelId = ? ";
            parameterList.add(Long.parseLong(f.getStockModelId().trim()));
        }
        if (f.getAucStatus() != null && !f.getAucStatus().trim().equals("")) {
            queryString += " and model.aucStatus = ? ";
            parameterList.add(f.getAucStatus().trim());
        }
        if (f.getStartImsi() != null && !f.getStartImsi().trim().equals("")) {
            BigInteger startImsi = new BigInteger(f.getStartImsi().trim());
            queryString += " and to_number(model.imsi)>= ? ";
            parameterList.add(startImsi);
        }
        if (f.getEndImsi() != null && !f.getEndImsi().trim().equals("")) {
            BigInteger endImsi = new BigInteger(f.getEndImsi());
            queryString += " and to_number(model.imsi)<= ? ";
            parameterList.add(endImsi);
        }
        if (f.getStartSerial() != null && !f.getStartSerial().trim().equals("")) {
            BigInteger startSerial = new BigInteger(f.getStartSerial());
            queryString += " and to_number(model.serial)>= ? ";
            parameterList.add(startSerial);
        }
        if (f.getEndSerial() != null && !f.getEndSerial().trim().equals("")) {
            BigInteger endSerial = new BigInteger(f.getEndSerial());
            queryString += " and to_number(model.serial)<= ? ";
            parameterList.add(endSerial);
        }
        Query query = getSession().createQuery(queryString);
        query.setMaxResults(SEARCH_RESULT_LIMIT);
        for (int i = 0; i < parameterList.size(); i++) {
            query.setParameter(i, parameterList.get(i));
        }
        List lstStockSim = new ArrayList();
        lstStockSim = query.list();
        return lstStockSim;

    }

    public String searchStockSim() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            List listStockSim = this.getListStockSim();
            req.removeAttribute(SESSION_LIST_STOCK_SIM);
            req.setAttribute(SESSION_LIST_STOCK_SIM, listStockSim);
            if (listStockSim.size() > 999) {
                req.setAttribute(REQUEST_MESSAGE, "MSG.FAC.Search.List1000");
            } else {
                req.setAttribute(REQUEST_MESSAGE, "search.result");
                List listMessageParam = new ArrayList();
                listMessageParam.add(listStockSim.size());
                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);
            }
            pageForward = LOOKUP_STOCK_SIM;
        } catch (Exception e) {
            getSession().clear();
            e.printStackTrace();
            throw e;
        }
        return pageForward;
    }
}
