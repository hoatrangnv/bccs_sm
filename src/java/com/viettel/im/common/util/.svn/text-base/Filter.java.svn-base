/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.common.util;

import com.viettel.database.config.BaseHibernateDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hibernate.Session;
import com.viettel.im.database.BO.StockIsdnMobile;
import com.viettel.im.database.BO.StockIsdnHomephone;
import com.viettel.im.database.BO.StockIsdnPstn;
import com.viettel.im.database.BO.IsdnRangeRules;
import com.viettel.im.database.BO.Staff;
import java.math.BigDecimal;
import org.hibernate.Query;

/**
 *
 * @author Vu Ngoc Ha
 */
public class Filter implements Runnable {

    private Long isdnRangeId;
    private String prefixNumber;
    private Long fromNumber = null;
    private Long toNumber = null;
    private String maskMapping = "";
    private Long ruleId = null;
    private Long shopId = null;
    private Long staffId = null;
    private Session session;

    public Filter(Session _session, Long _isdnRangeId, String _prefixNumber, Long _fromNumber, Long _toNumber, Long _ruleId, String _maskMapping, Long _shopId, Long _staffId) {
        session = _session;
        isdnRangeId = _isdnRangeId;
        prefixNumber = _prefixNumber;
        fromNumber = _fromNumber;
        toNumber = _toNumber;
        ruleId = _ruleId;
        maskMapping = _maskMapping;
        shopId = _shopId;
        staffId = _staffId;
    }

    public static long getSequence(String sequenceName, Session session) throws Exception {
        String strQuery = "SELECT " + sequenceName + " .NextVal FROM Dual";

        Query queryObject = session.createSQLQuery(strQuery);
        BigDecimal bigDecimal = (BigDecimal) queryObject.uniqueResult();
        return bigDecimal.longValue();
    }

    /**
     * Thuc hien loc dai so
     * 
     * @param 
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    private void filterRange() throws FileNotFoundException, IOException, Exception {
        try {
            for (Long isdn = fromNumber; isdn <= toNumber; isdn++) {
                filterISDN("0" + isdn.toString());
            }
            //Save rule to Range
            session.beginTransaction();
            IsdnRangeRules isdnRangeRules = new IsdnRangeRules();

            isdnRangeRules.setId(getSequence("ISDN_RANGE_RULES_SEQ", session));
            isdnRangeRules.setIsdnRangeId(isdnRangeId);
            isdnRangeRules.setRulesId(ruleId);
            isdnRangeRules.setStatus(Long.parseLong("1"));

            session.save(isdnRangeRules);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Thuc hien loc  so
     *
     * @param 
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public boolean filterISDN(String ISDN) {

        boolean reSult = Pattern.matches(maskMapping, ISDN);
        //if reSult=true Then save ISDN into StockIsdnMobile va cap nhat model cho so
        if (reSult == true) {
            reSult = saveISDNModel(prefixNumber, ISDN, ruleId);
        }
        return reSult;
    }

    /**
     * Thuc hien save ISDN into StockIsdnMobile va cap nhat model cho so
     *
     * @param 
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public boolean saveISDNModel(String prefixNumber, String ISDN, Long ruleId) {
        boolean reSult = false;
        try {
            if (prefixNumber.equals("098")) {

                session.beginTransaction();
                StockIsdnMobile stockIsdnMobile = new StockIsdnMobile();
                stockIsdnMobile.setId(getSequence("STOCK_ISDN_MOBILE_SEQ", session));
                stockIsdnMobile.setStockModelId(ruleId);
                stockIsdnMobile.setIsdn(ISDN);

                session.save(stockIsdnMobile);
                session.getTransaction().commit();

//                Staff staff = new Staff();
//                staff.setStaffId(staffId);
//                staff.setShopId(shopId);
//                staff.setIsdn(ISDN);
//                getSession().save(staff);

                reSult = true;
            }
        } catch (Exception ex) {
            reSult = false;
            ex.printStackTrace();
        }

        return reSult;
    }

    public void run() {
        try {

            filterRange();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

