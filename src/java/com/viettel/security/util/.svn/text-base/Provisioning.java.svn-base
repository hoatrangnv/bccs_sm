/*
 * Copyright 2012 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.security.util;

import com.viettel.common.ExchMsg;
import com.viettel.vas.util.obj.ExchangeChannel;
import java.io.IOException;
//import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;

/**
 *
 * @author kdvt_tungtt8
 * @version x.x
 * @since Dec 28, 2012
 */
public class Provisioning {

    private Logger logger;
    private String loggerLabel = Provisioning.class.getSimpleName() + ": ";
    private ExchangeChannel channel;
//    private DbProcessor dbProcessor;
    private StringBuffer br = new StringBuffer();
//    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    //
    public static final long REQUEST_TIME_OUT = 30000;
//    private static final String module = "PROVISIONING";

    public Provisioning(ExchangeChannel channel, Logger logger) throws IOException {
        this.logger = logger;
        this.channel = channel;
//        this.dbProcessor = dbProcessor;
        try {
            logger.info(loggerLabel + "Connect Exchange Client-" + channel.getId());
        } catch (Exception ex) {
            logger.error(loggerLabel + "ERROR connect Exchange Client-" + channel.getId(), ex);
        }
    }

    public void logTime(String strLog, long timeSt) {
        long timeEx = System.currentTimeMillis() - timeSt;
        if (timeEx >= 15000) {
            br.setLength(0);
            br.append(loggerLabel).
                    append("Slow call Pro: ").
                    append(strLog).
                    append(": ").
                    append(timeEx).
                    append(" ms");

            logger.warn(br);
        } else {
            br.setLength(0);
            br.append(loggerLabel).
                    append(strLog).
                    append(": ").
                    append(timeEx).
                    append(" ms");

            logger.info(br);
        }
    }

    public boolean viewCard(String serial) {
        ExchMsg request = new ExchMsg();
        ExchMsg response = null;
        long start = System.currentTimeMillis();
        boolean result = true;
        String flag = "";
        try {
            System.out.println("Start view status for card: " + serial);
            logger.info("Start view status for card: " + serial);
            request.setCommand("VCHW_QUERYCARD");
            request.set("SEQUENCE", serial);
            request.set("ClientTimeout", "25000");
            response = (ExchMsg) channel.sendAll(request, REQUEST_TIME_OUT, false);            
//            Neu goi Pro loi thi van tra ve true de uu tien xuat hang
            if ("0".equals(response.getError())) {
                flag = (String) response.get("HOTCARDFLAG");
                if (flag.length() > 0 && !"0".equals(flag.trim()) && !"5".equals(flag.trim())) {
                    result = false;
                }
            }
//            Check The card has not been loaded yet
			//20200220-Bacnx update new errorcode 1002
            if ("1002".equals(response.getError())) {
                result = false;
            }
            System.out.println("Finish view status for card: " + serial + " result " + result);
            logTime("Time to viewCard serial " + serial + " result " + result, start);
            return result;
        } catch (Exception e) {
            logTime("Exception to viewCard sub " + serial, start);
            logger.error(e.toString());
            System.out.println("Exception view status for card: " + serial + " detail " + e.toString());
            return result;
        }
    }
    
    public String addMoney(String isdn, String money, String balanceId) {
        long timeSt = System.currentTimeMillis();
        ExchMsg request = new ExchMsg();
        ExchMsg response = null;
        String err = "";
        try {
            logger.info("Start addMoney for sub " + isdn + " value " + money + " accountid " + balanceId);
            request.setCommand("OCSHW_ADJUSTACCOUNT");
            request.set("ISDN", "258" + isdn.trim());
            request.set("ACCOUNT_TYPE", balanceId.trim());
            request.set("AMOUNT", money.trim());
            request.set("VALIDITY_INCREMENT", "0");
            response = (ExchMsg) channel.sendAll(request, REQUEST_TIME_OUT, true);
            err = response.getError();
            logger.info("End addMoney isdn " + isdn + " amount " + money + " balanceid " + balanceId + " result " + err);
            return err;
        } catch (Exception ex) {
            logger.error("Had exception addMoney isdn " + isdn + " amount " + money);
            logger.error(ex.toString());
            return err;
        }
    }
}
