/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.common;

import com.viettel.common.ObjectClientChannel;
import com.viettel.common.ViettelMsg;
import com.viettel.common.ViettelService;
import com.viettel.im.common.util.ResourceBundleUtils;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class Provisioning {
    
    private static final Logger log = Logger.getLogger(Provisioning.class);
    public static ObjectClientChannel channelViewCard;
    private static long countRequest = 0l;

    public Provisioning() {
    }
    
     //Cac ham lay gia tri config cho ket noi Prov
    /**
     * Ham tra ve IP ket noi den he thong Prov tu file properties
     */
    public String getProvisioningIP() {
        return ResourceBundleUtils.getResource(Constant.PROV_FILE_CONFIG, Constant.PROV_IP);
    }

    /**
     * Ham tra ve cong ket noi toi he thong prov tu file properties
     */
    public String getProvisioningtPort() {
        return ResourceBundleUtils.getResource(Constant.PROV_FILE_CONFIG, Constant.PROV_PORT);
    }

    /**
     * Ham tra ve User ket noi toi Prov lay tu file properties
     */
    public String getProvisioningUser() {
        return ResourceBundleUtils.getResource(Constant.PROV_FILE_CONFIG , Constant.PROV_USER);
    }

    /**
     * Ham tra ve pass ket noi toi he thong Prov lay tu file properties
     */
    public String getProvisioningPass() {
        return ResourceBundleUtils.getResource(Constant.PROV_FILE_CONFIG , Constant.PROV_PASS);
    }

    /**
     * Ham tra ve gia tri Syn trong ket noi Pro(lay trong file properties)
     */
    public boolean getProvisioningSyn() {
        return Boolean.parseBoolean(ResourceBundleUtils.getResource(Constant.PROV_FILE_CONFIG , Constant.PROV_SYN));
    }

    /**
     * Ham tra ve gia tri messageType trong ket noi Prov (lay trong file properties)
     */
    public String getProvisioningMessageType() {
        return ResourceBundleUtils.getResource(Constant.PROV_FILE_CONFIG, Constant.PROV_MESSAGE_TYPE);
    }

    /**
     * Ham tra ve Client Id dung trong ket noi Prov (lay trong file properties)
     */
    public String getProvisioningClientID() {
        return ResourceBundleUtils.getResource(Constant.PROV_FILE_CONFIG, Constant.PROV_CLIENT_ID);
    }
    
     /**
     * @author tienmh
     * @since 4/6/2010
     * @description ham send request view thong tin the cao
     */
    public ViettelMsg sendRequestViewCard(String processCode, Map<String, String> params) {
        ViettelMsg viettetMsg = null;
        try {
            String provisioningIP = getProvisioningIP();
            int provisioningPort = Integer.valueOf(getProvisioningtPort());
            String provisioningUser = getProvisioningUser();
            String provisiongingPass = getProvisioningPass();
            boolean provisioningSyn = getProvisioningSyn();
            String messageType = getProvisioningMessageType();
            String clientID = getProvisioningClientID();
            Random random = new Random(Calendar.getInstance().getTimeInMillis());
            long systemTrace = random.nextLong() + countRequest++;
            String timeOutRequest = ResourceBundleUtils.getResource(Constant.PROV_FILE_CONFIG, Constant.TIME_OUT_REQUEST_VIEW_CARD);
            Long timeOutChannel = Long.parseLong(ResourceBundleUtils.getResource(Constant.PROV_FILE_CONFIG, Constant.TIME_OUT_CHANNEL_VIEW_CARD));
            if (channelViewCard == null) {
                channelViewCard = new ObjectClientChannel(provisioningIP, provisioningPort,
                        provisioningUser, provisiongingPass, provisioningSyn);
                Logger logger = Logger.getLogger("client");
                channelViewCard.setLogger(logger);
                channelViewCard.connect();
                channelViewCard.setReceiverTimeout(timeOutChannel);
            }
            ViettelService request = new ViettelService();
            request.setMessageType(messageType);
            request.setProcessCode(processCode);
            request.setTransactionTime(new java.util.Date());
            request.setClientId(clientID);
            request.setSystemTrace(systemTrace);
            Set setParams = params.entrySet();
            Iterator it = setParams.iterator();
            String isdn="";
            while (it.hasNext()) {
                Map.Entry param = (Map.Entry) it.next();
                request.set(String.valueOf(param.getKey()), String.valueOf(param.getValue()));
                //haint41 21/12/2011 : thay doi STARTNO --> SEQUENCE
                if(Constant.PROV_PARAM_SEQUENCE_NAME.equals(String.valueOf(param.getKey()))
//                        || Constant.STARTNO.equals(String.valueOf(param.getKey()))){
                        || Constant.SEQUENCE.equals(String.valueOf(param.getKey()))){
                    isdn=String.valueOf(param.getValue());
                }
            }
            request.set("ClientTimeout", timeOutRequest);
            //Time out cho channel
            viettetMsg = channelViewCard.send(request);
            //end
        } catch (Exception e) {
            log.error("Error at sendRequestViewCard:/ Loi khi gui tong dai Prov/ ", e);
        }
        return viettetMsg;
    }

}
