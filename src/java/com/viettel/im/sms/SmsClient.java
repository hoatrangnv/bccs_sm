/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.sms;

//import com.viettel.database.config.BaseHibernateDAO;
//import com.viettel.database.DAO.BaseDAOAction;
//import com.viettel.sms.BinaryMessage;
//import com.viettel.vci.common.util.EditorUtils;
//import com.viettel.vci.database.DAO.ProcessCustomerDAO;
import com.viettel.im.common.util.ResourceBundleUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import sendmt.MtStub;
//import sun.misc.BASE64Encoder;

/**
 *
 * @author Tran Van Dat
 */
//--LongH copied
public class SmsClient {

    private static MtStub stub = null;

    public static int sendSMS155(String isdn, String content) {

        if (isdn == null || isdn.trim().equals("")) {
            return 0;
        }
        System.out.println("so vua gui " + isdn);
        // ToanPX su dung thu vien moi
        try {
            String strUrl = ResourceBundleUtils.getResource("SMS_URL");
            String strXmlns = ResourceBundleUtils.getResource("SMS_XMLNS");
            String strUser = ResourceBundleUtils.getResource("SMS_USER");
            String strPass = ResourceBundleUtils.getResource("SMS_PASS");
            String strServiceId = ResourceBundleUtils.getResource("SERVICE_ID");
            String strSessionId = ResourceBundleUtils.getResource("SESSION_ID");
            String strSender = ResourceBundleUtils.getResource("SENDER");
            System.out.println("Prepare sendSMS155:");
            if (stub == null) {
                stub = new MtStub(strUrl, strXmlns, strUser, strPass);
            }

            if (isdn != null) {
                isdn = isdn.trim();
                if (isdn.startsWith("0")) {
                    isdn = "258" + isdn.substring(1);
                } else if (!isdn.startsWith("258")) {
                    isdn = "258" + isdn;
                }
            }

            return stub.send(strSessionId, strServiceId, strSender, isdn, "1", content.getBytes("UnicodeBigUnmarked"), "1"); //Xoa

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("gui loi  " + isdn);
        }
        return 0;

    }

    public static void main(String[] args) {
        try {
            System.out.println("bat dau");




            //SmsClient.sendSMS155("986300518", "xmkh:that la vo van");
//            Date sysDate = new Date();
//            Date outDate1 = DateTimeUtils.addDayToDate(sysDate, 5);
//            Date outDate2 = DateTimeUtils.addDayToDate(sysDate, 5 + 3);
//            System.out.println("SysDate:" + DateTimeUtils.convertDateTimeToString(sysDate, "dd/MM/yyyy hh:mm:ss") +
//                               ",OutDate1:" + DateTimeUtils.convertDateTimeToString(outDate1, "dd/MM/yyyy hh:mm:ss") +
//                               ",OutDate2:" + DateTimeUtils.convertDateTimeToString(outDate2, "dd/MM/yyyy hh:mm:ss"));
        } catch (Exception ex) {
            Logger.getLogger(SmsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    
}
