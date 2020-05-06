/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.DAO;
//
//import com.viettel.im.common.Constant;
//import java.math.*;
//import java.security.*;
//        
//import com.viettel.im.database.BO.WsAnyPayAgentBO;
//import com.viettel.im.database.BO.WsRechargeAgentBO;
//import com.viettel.im.database.BO.WsTransactionBO;
//import com.viettel.im.ws.*;
/**
 *
 * @author MrSun
 */
public class WsAnyPayDAO {
//
//    //Main Key
//    private static String MAIN_KEY = "iexsbccs";
//    private static int DIGEST_LENGTH = 32;
//    private static String CHAR_PLUS = "+";
//    private static String CHAR_O = "(";
//    private static String CHAR_C = ")";
//    
//    /**
//     * generating digest for update anyPayAgent
//     * @param bo
//     * @return
//     */
//    public static String genDigest(WsAnyPayAgentBO bo, String actionType, StringBuffer inputParams){
//        try{            
//            String inputString = ""; // Value to encrypt
//            
//            //info required
//            if (bo.getSerialID() == null || bo.getAgentCode() == null || bo.getMsisdn() == null)
//                return "";
//            inputString = bo.getSerialID() + bo.getAgentCode() + bo.getMsisdn();
//            inputParams.append("serialID");            
//            inputParams.append(CHAR_O);
//            inputParams.append(bo.getSerialID());
//            inputParams.append(CHAR_C);
//            
//            inputParams.append(CHAR_PLUS);
//            inputParams.append("agentCode");
//            inputParams.append(CHAR_O);
//            inputParams.append(bo.getAgentCode());
//            inputParams.append(CHAR_C);
//            
//            inputParams.append(CHAR_PLUS);
//            inputParams.append("msisdn");
//            inputParams.append(CHAR_O);
//            inputParams.append(bo.getMsisdn());
//            inputParams.append(CHAR_C);
//
//            //Create
//            if (actionType != null && actionType.equals(Constant.ACTION_TYPE_CREATE)){
//                if (bo.getIccid() == null || bo.getServiceType() == null || bo.getPassword() == null)
//                    return "";
//                inputString += bo.getIccid();
//                inputString += bo.getServiceType();
//                inputString += String.valueOf(bo.getBalance());
//                inputString += bo.getPassword();
//                
//                inputParams.append(CHAR_PLUS);
//                inputParams.append("iccid");
//                inputParams.append(CHAR_O);
//                inputParams.append(bo.getIccid());
//                inputParams.append(CHAR_C);
//
//                inputParams.append(CHAR_PLUS);
//                inputParams.append("serviceType");
//                inputParams.append(CHAR_O);
//                inputParams.append(bo.getServiceType());
//                inputParams.append(CHAR_C);
//                
//                inputParams.append(CHAR_PLUS);
//                inputParams.append("balance");
//                inputParams.append(CHAR_O);
//                inputParams.append(String.valueOf(bo.getBalance()));
//                inputParams.append(CHAR_C);
//                
//                inputParams.append(CHAR_PLUS);
//                inputParams.append("password");
//                inputParams.append(CHAR_O);
//                inputParams.append(bo.getPassword());
//                inputParams.append(CHAR_C);
//                
//                if (bo.getAgentType()!=null && !bo.getAgentType().equals("")){
//                    inputString += bo.getAgentType();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("agentType");
//                    inputParams.append(CHAR_O);
//                inputParams.append(bo.getAgentType());
//                inputParams.append(CHAR_C);
//                }
//                if (bo.getState()!=null && !bo.getState().equals("")){                    
//                    inputString += bo.getState();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("state");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getState());
//                    inputParams.append(CHAR_C);
//                }
//                if (bo.getAccessType()!=null && !bo.getAccessType().equals("")){                    
//                    inputString += bo.getAccessType();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("accessType");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getAccessType());
//                    inputParams.append(CHAR_C);
//                }
//                                
//            }
//            //Modify
//            else if (actionType != null && actionType.equals(Constant.ACTION_TYPE_MODIFY)){
//                if (bo.getNewMSISDN()!=null && !bo.getNewMSISDN().equals("")){
//                    inputString += bo.getNewMSISDN();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("newMSISDN");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getNewMSISDN());
//                    inputParams.append(CHAR_C);
//                }
//                 if (bo.getNewICCID()!=null && !bo.getNewICCID().equals("")){
//                    inputString += bo.getNewICCID();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("newICCID");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getNewICCID());
//                    inputParams.append(CHAR_C);
//                 }
//                 if (bo.getNewPassword()!=null && !bo.getNewPassword().equals("")){
//                    inputString += bo.getNewPassword();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("newPassword");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getNewPassword());
//                    inputParams.append(CHAR_C);
//                 }
//                 if (bo.getNewState()!=null && !bo.getNewState().equals("")){
//                    inputString += bo.getNewState();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("newState");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getNewState());
//                    inputParams.append(CHAR_C);
//                 }
//                 if (bo.getNewAccessType()!=null && !bo.getNewAccessType().equals("")){
//                    inputString += bo.getNewAccessType();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("newAccessType");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getNewAccessType());
//                    inputParams.append(CHAR_C);
//                 }
//                 if (bo.getNewValidDate()!=null && !bo.getNewValidDate().equals("")){
//                    inputString += bo.getNewValidDate();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("newValidDate");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getNewValidDate());
//                    inputParams.append(CHAR_C);
//                 }
//                 if (bo.getNewUserName()!=null && !bo.getNewUserName().equals("")){
//                    inputString += bo.getNewUserName();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("newUserName");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getNewUserName());
//                    inputParams.append(CHAR_C);
//                 }
//                 if (bo.getNewResidentID()!=null && !bo.getNewResidentID().equals("")){
//                    inputString += bo.getNewResidentID();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("newResidentID");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getNewResidentID());
//                    inputParams.append(CHAR_C);
//                 }
//                 if (bo.getNewAddress()!=null && !bo.getNewAddress().equals("")){
//                    inputString += bo.getNewAddress();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("newAddress");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getNewAddress());
//                    inputParams.append(CHAR_C);
//                 }
//                 if (bo.getNewPostcode()!=null && !bo.getNewPostcode().equals("")){
//                    inputString += bo.getNewPostcode();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("newPostcode");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getNewPostcode());
//                    inputParams.append(CHAR_C);
//                 }
//                 if (bo.getNewEmail()!=null && !bo.getNewEmail().equals("")){
//                    inputString += bo.getNewEmail();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("newEmail");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getNewEmail());
//                    inputParams.append(CHAR_C);
//                 }
//                 if (bo.getNewContactPh()!=null && !bo.getNewContactPh().equals("")){
//                    inputString += bo.getNewContactPh();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("newContactPh");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getNewContactPh());
//                    inputParams.append(CHAR_C);
//                 }
//                 if (bo.getNewServiceAddr()!=null && !bo.getNewServiceAddr().equals("")){
//                    inputString += bo.getNewServiceAddr();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("newServiceAddr");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getNewServiceAddr());
//                    inputParams.append(CHAR_C);
//                 }                
//                if (bo.getAgentType()!=null && !bo.getAgentType().equals("")){
//                    inputString += bo.getAgentType();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("agentType");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getAgentType());
//                    inputParams.append(CHAR_C);
//                }
//            }            
//            //cancel balance
//            else if (actionType != null && actionType.equals(Constant.ACTION_TYPE_CANCEL_BALANCE)){
//                if (bo.getServiceType()!=null && !bo.getServiceType().equals("")){
//                    inputString += bo.getServiceType();
//                    inputParams.append(CHAR_PLUS);
//                    inputParams.append("serviceType");
//                    inputParams.append(CHAR_O);
//                    inputParams.append(bo.getServiceType());
//                    inputParams.append(CHAR_C);
//                }
//            }
//            
//            inputParams.append(" -> ");
//            inputParams.append(inputString);
//            //Lan 1
//            inputString = WsCommonDAO.getMD5(inputString);
//            if (inputString.length() < DIGEST_LENGTH){
//                inputString  = "0" + inputString;
//            }
//            inputString += MAIN_KEY;
//            //Lan 2
//            inputString = WsCommonDAO.getMD5(inputString);
//            if (inputString.length() < DIGEST_LENGTH){
//                inputString  = "0" + inputString;
//            }
//            return inputString;
//        }catch(Exception ex){
//            ex.printStackTrace();
//            return "";
//        }
//    }
//    
//    public static String genDigest(WsAnyPayAgentBO bo, String actionType){
//        try{            
//            String inputString = ""; // Value to encrypt
//            StringBuffer inputParams = new StringBuffer("");
//            
//            //info required
//            if (bo.getSerialID() == null || bo.getAgentCode() == null || bo.getMsisdn() == null)
//                return "";
//            inputString = bo.getSerialID() + bo.getAgentCode() + bo.getMsisdn();
//            inputParams.append("serialID");
//            inputParams.append("agentCode");
//            inputParams.append("msisdn");
//
//            //Create
//            if (actionType != null && actionType.equals(Constant.ACTION_TYPE_CREATE)){
//                if (bo.getIccid() == null || bo.getServiceType() == null || bo.getPassword() == null)
//                    return "";
//                inputString += bo.getIccid();
//                inputString += bo.getServiceType();
//                inputString += String.valueOf(bo.getBalance());
//                inputString += bo.getPassword();
//                inputParams.append("iccid");
//                inputParams.append("serviceType");
//                inputParams.append("balance");
//                inputParams.append("password");
//                if (bo.getState()!=null && !bo.getState().equals("")){                    
//                    inputString += bo.getState();
//                    inputParams.append("state");
//                }
//                if (bo.getAgentType()!=null && !bo.getAgentType().equals("")){
//                    inputString += bo.getAgentType();
//                    inputParams.append("agentType");
//                }
//                
//            }
//            //Modify
//            else if (actionType != null && actionType.equals(Constant.ACTION_TYPE_MODIFY)){
//                if (bo.getNewMSISDN()!=null && !bo.getNewMSISDN().equals(""))
//                    inputString += bo.getNewMSISDN();
//                 if (bo.getNewICCID()!=null && !bo.getNewICCID().equals(""))
//                    inputString += bo.getNewICCID();
//                 if (bo.getNewPassword()!=null && !bo.getNewPassword().equals(""))
//                    inputString += bo.getNewPassword();
//                 if (bo.getNewState()!=null && !bo.getNewState().equals(""))
//                    inputString += bo.getNewState();
//                 if (bo.getNewAccessType()!=null && !bo.getNewAccessType().equals(""))
//                    inputString += bo.getNewAccessType();
//                 if (bo.getNewValidDate()!=null && !bo.getNewValidDate().equals(""))
//                    inputString += bo.getNewValidDate();
//                 if (bo.getNewUserName()!=null && !bo.getNewUserName().equals(""))
//                    inputString += bo.getNewUserName();
//                 if (bo.getNewResidentID()!=null && !bo.getNewResidentID().equals(""))
//                    inputString += bo.getNewResidentID();
//                 if (bo.getNewAddress()!=null && !bo.getNewAddress().equals(""))
//                    inputString += bo.getNewAddress();
//                 if (bo.getNewPostcode()!=null && !bo.getNewPostcode().equals(""))
//                    inputString += bo.getNewPostcode();
//                 if (bo.getNewEmail()!=null && !bo.getNewEmail().equals(""))
//                    inputString += bo.getNewEmail();
//                 if (bo.getContactPh()!=null && !bo.getContactPh().equals(""))
//                    inputString += bo.getContactPh();
//                 if (bo.getNewServiceAddr()!=null && !bo.getNewServiceAddr().equals(""))
//                    inputString += bo.getNewServiceAddr();
//                
//                if (bo.getAgentType()!=null && !bo.getAgentType().equals("")){
//                    inputString += bo.getAgentType();
//                }
//            }            
//            //cancel balance
//            else if (actionType != null && actionType.equals(Constant.ACTION_TYPE_CANCEL_BALANCE)){
//                if (bo.getServiceType()!=null && !bo.getServiceType().equals(""))
//                    inputString += bo.getServiceType();
//            }
//            
//            inputParams.append("-");
//            inputParams.append(inputString);
//            inputString = WsCommonDAO.getMD5(inputString);
//            inputString += MAIN_KEY;            
//            inputString = WsCommonDAO.getMD5(inputString);
//            if (inputString.length() < DIGEST_LENGTH){
//                inputString  = "0" + inputString;
//            }
//            return inputString;
//        }catch(Exception ex){
//            ex.printStackTrace();
//            return "";
//        }
//    }
//
//    /**
//     * generating digest for recharge anyPayAgent
//     * @param bo
//     * @param actionType
//     * @return
//     */
//    public static String genDigest(WsRechargeAgentBO bo, StringBuffer inputParams){
//        try{
//            String inputString = ""; // Value to encrypt
//
//            //info required
//            if (bo.getSerialID() == null || bo.getAgentCode() == null || bo.getMsisdn() == null || bo.getTransSerial() == null || bo.getServiceType() == null)
//                return "";
//            inputString = bo.getSerialID() + bo.getAgentCode() + bo.getMsisdn() + bo.getTransSerial() + bo.getServiceType();            
//            inputParams.append("serialID");            
//            inputParams.append(CHAR_O);
//            inputParams.append(bo.getSerialID());
//            inputParams.append(CHAR_C);
//            
//            inputParams.append(CHAR_PLUS);
//            inputParams.append("agentCode");
//            inputParams.append(CHAR_O);
//            inputParams.append(bo.getAgentCode());
//            inputParams.append(CHAR_C);
//            
//            inputParams.append(CHAR_PLUS);
//            inputParams.append("msisdn");
//            inputParams.append(CHAR_O);
//            inputParams.append(bo.getMsisdn());
//            inputParams.append(CHAR_C);
//            
//            inputParams.append(CHAR_PLUS);
//            inputParams.append("transSerial");
//            inputParams.append(CHAR_O);
//            inputParams.append(bo.getTransSerial());
//            inputParams.append(CHAR_C);
//            
//            inputParams.append(CHAR_PLUS);
//            inputParams.append("serviceType");
//            
//            inputString += String.valueOf(bo.getAmount());
//            inputParams.append(CHAR_PLUS);
//            inputParams.append("amount");
//            inputParams.append(CHAR_O);
//            inputParams.append(String.valueOf(bo.getAmount()));
//            inputParams.append(CHAR_C);
//            
//            inputParams.append(" -> ");
//            inputParams.append(inputString);
//            inputString = WsCommonDAO.getMD5(inputString);
//            inputString += MAIN_KEY;            
//            inputString = WsCommonDAO.getMD5(inputString);
//            if (inputString.length() < DIGEST_LENGTH){
//                inputString  = "0" + inputString;
//            }
//            return inputString;
//        }catch(Exception ex){
//            ex.printStackTrace();
//            return "";
//        }
//    }
//
//    public static String genDigest(WsRechargeAgentBO bo){
//        try{
//            String inputString = ""; // Value to encrypt
//            StringBuffer inputParams = new StringBuffer("");
//        
//            //info required
//            if (bo.getSerialID() == null || bo.getAgentCode() == null || bo.getMsisdn() == null || bo.getTransSerial() == null || bo.getServiceType() == null)
//                return "";
//            inputString = bo.getSerialID() + bo.getAgentCode() + bo.getMsisdn() + bo.getTransSerial() + bo.getServiceType();
//            inputParams.append("serialID");
//            inputParams.append("agentCode");
//            inputParams.append("msisdn");
//            inputParams.append("transSerial");
//            inputParams.append("serviceType");
//            
//            inputString += String.valueOf(bo.getAmount());
//            inputParams.append("amount");
//            
//            inputParams.append(" - ");
//            inputParams.append(inputString);
//            inputString = WsCommonDAO.getMD5(inputString);
//            inputString += MAIN_KEY;            
//            inputString = WsCommonDAO.getMD5(inputString);
//            if (inputString.length() < DIGEST_LENGTH){
//                inputString  = "0" + inputString;
//            }
//            return inputString;
//        }catch(Exception ex){
//            ex.printStackTrace();
//            return "";
//        }
//    }
//    
//    
//    /**
//     * generating digest for cancel transaction anyPayAgent
//     * @param bo
//     * @param actionType
//     * @return
//     */
//    public static String genDigest(WsTransactionBO bo, StringBuffer inputParams){
//        try{
//            String inputString = ""; // Value to encrypt
//
//            //info required
//            if (bo.getSerialID() == null || bo.getAgentCode() == null || bo.getMsisdn() == null || bo.getTransID() == null || bo.getServiceType() == null || bo.getOperType() == null)
//                return "";
//            
//            inputString = bo.getSerialID() + bo.getAgentCode() + bo.getMsisdn() + bo.getTransID() + bo.getServiceType() + bo.getOperType();            
//            inputParams.append("serialID");
//            inputParams.append(CHAR_O);
//            inputParams.append(bo.getSerialID());
//            inputParams.append(CHAR_C);
//            inputParams.append(CHAR_PLUS);
//            inputParams.append("agentCode");
//            inputParams.append(CHAR_O);
//            inputParams.append(bo.getAgentCode());
//            inputParams.append(CHAR_C);
//            inputParams.append(CHAR_PLUS);
//            inputParams.append("msisdn");
//            inputParams.append(CHAR_O);
//            inputParams.append(bo.getMsisdn());
//            inputParams.append(CHAR_C);
//            inputParams.append(CHAR_PLUS);
//            inputParams.append("transID");
//            inputParams.append(CHAR_O);
//            inputParams.append(bo.getTransID());
//            inputParams.append(CHAR_C);
//            inputParams.append(CHAR_PLUS);
//            inputParams.append("serviceType");
//            inputParams.append(CHAR_O);
//            inputParams.append(bo.getServiceType());
//            inputParams.append(CHAR_C);
//            inputParams.append(CHAR_PLUS);
//            inputParams.append("operType");
//            inputParams.append(CHAR_O);
//            inputParams.append(bo.getOperType());
//            inputParams.append(CHAR_C);
//            
//            inputString += String.valueOf(bo.getAmount());
//            inputParams.append(CHAR_PLUS);
//            inputParams.append("amount");
//            inputParams.append(CHAR_O);
//            inputParams.append(String.valueOf(bo.getAmount()));
//            inputParams.append(CHAR_C);
//            
//            inputParams.append(" -> ");
//            inputParams.append(inputString);            
//            inputString = WsCommonDAO.getMD5(inputString);
//            inputString += MAIN_KEY;
//            inputString = WsCommonDAO.getMD5(inputString);
//            if (inputString.length() < DIGEST_LENGTH){
//                inputString  = "0" + inputString;
//            }
//            return inputString;
//        }catch(Exception ex){
//            ex.printStackTrace();
//            return "";
//        }
//    }
//    
//    public static String genDigest(WsTransactionBO bo){
//        try{
//            String inputString = ""; // Value to encrypt
//
//            //info required
//            if (bo.getSerialID() == null || bo.getAgentCode() == null || bo.getMsisdn() == null || bo.getTransID() == null || bo.getServiceType() == null || bo.getOperType() == null)
//                return "";            
//            inputString = bo.getSerialID() + bo.getAgentCode() + bo.getMsisdn() + bo.getTransID() + bo.getServiceType() + bo.getOperType();            
//            inputString += String.valueOf(bo.getAmount());
//                        
//            inputString = WsCommonDAO.getMD5(inputString);
//            inputString += MAIN_KEY;
//            inputString = WsCommonDAO.getMD5(inputString);
//            if (inputString.length() < DIGEST_LENGTH){
//                inputString  = "0" + inputString;
//            }
//            return inputString;
//        }catch(Exception ex){
//            ex.printStackTrace();
//            return "";
//        }
//    }
//    
//        
//    /**
//     * Create new account AnyPay
//     * Input:
//     *      serialID (*)
//     *      agentCode (*)
//     *      msisdn (*)
//     *      iccid (*)
//     *      serviceType (*)
//     *      balance (*)
//     *      password
//     *      agentType
//     *      createdDate
//     *      state
//     *      accessType
//     *      validDate
//     *      userName
//     *      residentID
//     *      address
//     *      postcode
//     *      email
//     *      contactPh
//     *      serviceAddr
//     *      digest (*)
//     * Output:
//     *      serialID
//     *      agentCode
//     *      result
//     *      resInfo
//     *      digest
//     */
//    public WsAnyPayAgentBO vCreateAnyPayAgent(WsAnyPayAgentBO bo){
//        try { // Call Web Service Operation
//            IEXS service = new IEXS();
//            IEXSSoap port = service.getIEXSSoap();
//            // TODO initialize WS operation arguments here
//            VCreateAnyPayAgentReq req = new VCreateAnyPayAgentReq();
//            
//            //BCCS: copy values of AnyPayAgent from BO to IN
//            req = copyToCreateAnyPayAgentReq(bo);
//            
//            // TODO process result here
//            try{
//                VCreateAnyPayAgentRsp result = port.vCreateAnyPayAgent(req);
//                
//                //BCCS: copy values of Respond from IN to BO
//                WsAnyPayAgentBO rsq = copyFromCreateAnyPayAgentRsp(result);
//                return rsq;            
//            } catch (Exception ex) {
//                throw new Exception("Error from port.vCreateAnyPayAgent(req) - " + ex.getMessage());
//            }            
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//            ex.printStackTrace();
//            WsAnyPayAgentBO rsq = new WsAnyPayAgentBO();
//            rsq.setResult("-1");
//            rsq.setResInfo(ex.getMessage());
//            rsq.setResDigest("-1");
//            return rsq;
//        }
//    }
//   
//    /**
//     * View infomation of account AnyPay
//     */
//    public WsAnyPayAgentBO vQueryAnyPayInfo(WsAnyPayAgentBO bo){
//        try { // Call Web Service Operation
//            IEXS service = new IEXS();
//            IEXSSoap port = service.getIEXSSoap();
//            // TODO initialize WS operation arguments here
//            VQueryAnyPayInfoReq req = new VQueryAnyPayInfoReq();
//            
//            //BCCS
//            req = copyToQueryAnyPayInfoReq(bo);
//            
//            try{
//                // TODO process result here
//                VQueryAnyPayInfoRsp result = port.vQueryAnyPayInfo(req);
//                
//                //BCCS: copy values of Respond from IN to BO
//                WsAnyPayAgentBO rsq = copyFromQueryAnyPayInfoRsp(result);
//                if (rsq.getMsisdn() == null)
//                    rsq.setMsisdn(bo.getMsisdn());
//                return rsq;
//            } catch (Exception ex) {
//                throw new Exception("Loi goi ham: port.vQueryAnyPayInfo(req) - " + ex.getMessage());
//            }            
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//            ex.printStackTrace();            
//            WsAnyPayAgentBO rsq = new WsAnyPayAgentBO();
//            rsq.setResult("-1");
//            rsq.setResInfo(ex.getMessage());
//            rsq.setResDigest("-1");
//            return rsq;
//        }
//    }
//    
//    /**
//     * Modify infomation of account AnyPay
//     */
//    public WsAnyPayAgentBO vModifyAnyPayInfo(WsAnyPayAgentBO bo){
//        try { // Call Web Service Operation
//            IEXS service = new IEXS();
//            IEXSSoap port = service.getIEXSSoap();
//            // TODO initialize WS operation arguments here
//            VModiAnyPayInfoReq req = new VModiAnyPayInfoReq();
//            
//            //BCCS
//            req = copyToModifyAnyPayInfoReq(bo);
//                        
//             try{
//                // TODO process result here
//                VModiAnyPayInfoRsp result = port.vModiAnyPayInfo(req);
//
//                //BCCS
//                WsAnyPayAgentBO rsq = copyFromModifyAnyPayInfoRsp(result);
//                return rsq;
//            } catch (Exception ex) {
//                throw new Exception("Loi goi ham: port.vModiAnyPayInfo(req) - " + ex.getMessage());
//            }            
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//            ex.printStackTrace();
//            WsAnyPayAgentBO rsq = new WsAnyPayAgentBO();
//            rsq.setResult("-1");
//            rsq.setResInfo(ex.getMessage());
//            rsq.setResDigest("-1");
//            return rsq;
//        }
//    }
//    
//    /**
//     * Cancel exist account AnyPay on IN
//     */
//    public WsAnyPayAgentBO vCancelAnyPayAgent(WsAnyPayAgentBO bo){
//        try { // Call Web Service Operation
//            IEXS service = new IEXS();
//            IEXSSoap port = service.getIEXSSoap();
//            // TODO initialize WS operation arguments here
//            VCancelAnyPayAgentReq req = new VCancelAnyPayAgentReq();
//
//            //BCCS
//            req = copyToCancelAnyPayAgentReq(bo);
//
//            try{
//                // TODO process result here
//                VCancelAnyPayAgentRsp result = port.vCancelAnyPayAgent(req);
//
//                //BCCS
//                WsAnyPayAgentBO rsq = copyFromCancelAnyPayAgentRsp(result);            
//                return rsq;
//            } catch (Exception ex) {
//                throw new Exception("Loi goi ham: port.vCancelAnyPayAgent(req) - " + ex.getMessage());
//            }
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//            ex.printStackTrace();
//            WsAnyPayAgentBO rsq = new WsAnyPayAgentBO();
//            rsq.setResult("-1");
//            rsq.setResInfo(ex.getMessage());
//            rsq.setResDigest("-1");
//            return rsq; 
//        }
//    }
//    
//    /**
//     * Recharge for account AnyPay
//     */
//    public WsRechargeAgentBO vRechargeAgent(WsRechargeAgentBO bo){
//        try { // Call Web Service Operation
//            IEXS service = new IEXS();
//            IEXSSoap port = service.getIEXSSoap();
//            // TODO initialize WS operation arguments here
//            VRechargeAgentReq req = new VRechargeAgentReq();
//
//            //BCCS
//            req = copyToRechargeAgentReq(bo);
//            
//            try{
//                // TODO process result here
//                VRechargeAgentRsp result = port.vRechargeAgent(req);
//
//                //BCCS
//                WsRechargeAgentBO rsq = copyFromRechargeAgentRsp(result);
//                return rsq;
//            } catch (Exception ex) {
//                throw new Exception("Loi goi ham: port.vRechargeAgent(req) - " + ex.getMessage());
//            }            
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//            ex.printStackTrace();
//            WsRechargeAgentBO rsq = new WsRechargeAgentBO();
//            rsq.setResult("-1");
//            rsq.setResInfo(ex.getMessage());
//            rsq.setResDigest("-1");
//            return rsq;
//        }
//
//    }
//    
//    /**
//     * Cancel Recharge for account AnyPay
//     */
//    public WsAnyPayAgentBO vCancelAnyPayBlc(WsAnyPayAgentBO bo){
//        try { // Call Web Service Operation
//            IEXS service = new IEXS();
//            IEXSSoap port = service.getIEXSSoap();
//            // TODO initialize WS operation arguments here
//            VCancelAnyPayBlcReq req = new VCancelAnyPayBlcReq();
//            // TODO process result here
//            req = copyToCancelAnyPayBlcReq(bo);
//            
//            try{
//                // TODO process result here
//                VCancelAnyPayBlcRsp result = port.vCancelAnyPayBlc(req);
//
//                //BCCS
//                WsAnyPayAgentBO rsq = copyFromCancelAnyPayBlcRsp(result);
//                return rsq;
//            } catch (Exception ex) {
//                throw new Exception("Loi goi ham: port.vCancelAnyPayBlc(req) - " + ex.getMessage());
//            }            
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//            ex.printStackTrace();
//            WsAnyPayAgentBO rsq = new WsAnyPayAgentBO();
//            rsq.setResult("-1");
//            rsq.setResInfo(ex.getMessage());
//            rsq.setResDigest("-1");
//            return rsq;
//        }
//
//    }
//    
//    /**
//     * Cancel Transaction of account AnyPay to other account AnyPay or customer
//     */
//    public WsTransactionBO vCancelTransaction(WsTransactionBO bo){
//        try { // Call Web Service Operation
//            IEXS service = new IEXS();
//            IEXSSoap port = service.getIEXSSoap();
//            // TODO initialize WS operation arguments here
//            VCancelTransactionReq req = new VCancelTransactionReq();
//
//            //BCCS
//            req = copyToCancelTransactionReq(bo);
//            try{
//                // TODO process result here
//                VCancelTransactionRsp result = port.vCancelTransaction(req);
//
//                //BCCS
//                WsTransactionBO rsq = copyFromCancelTransactionRsp(result);
//                return rsq;
//            } catch (Exception ex) {
//                throw new Exception("Loi goi ham: port.vCancelTransaction(req) - " + ex.getMessage());
//            }
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//            ex.printStackTrace();
//            WsTransactionBO rsq = new WsTransactionBO();
//            rsq.setResult("-1");
//            rsq.setResInfo(ex.getMessage());
//            rsq.setResDigest("-1");
//            return rsq;
//        }
//    }
//    
//    
//    
//    
//    /**
//     * copy values of WsAnyPayAgentBO to VCreateAnyPayAgentReq
//     * @param bo
//     * @return
//     */
//    private VCreateAnyPayAgentReq copyToCreateAnyPayAgentReq(WsAnyPayAgentBO bo){
//        VCreateAnyPayAgentReq req = new VCreateAnyPayAgentReq();
//        req.setSerialID(bo.getSerialID());
//        req.setAgentCode(bo.getAgentCode());
//        req.setMSISDN(bo.getMsisdn());
//        req.setICCID(bo.getIccid());
//        req.setServiceType(bo.getServiceType());
//        req.setBalance(bo.getBalance());
//        req.setPassword(bo.getPassword());
//        req.setAgentType(bo.getAgentType());
//        req.setCreatedDate(bo.getCreatedDate());
//        req.setState(bo.getState());
//        req.setAccessType(bo.getAccessType());
//        req.setValidDate(bo.getValidDate());
//        req.setUserName(bo.getUserName());
//        req.setResidentID(bo.getResidentID());
//        req.setAddress(bo.getAddress());
//        req.setPostcode(bo.getPostcode());
//        req.setEmail(bo.getEmail());
//        req.setContactPh(bo.getContactPh());
//        req.setServiceAddr(bo.getServiceAddr());
//        req.setDigest(bo.getDigest());
//
//        return req;
//    }
//    
//    private VQueryAnyPayInfoReq copyToQueryAnyPayInfoReq(WsAnyPayAgentBO bo){
//        VQueryAnyPayInfoReq req = new VQueryAnyPayInfoReq();
//        req.setSerialID(bo.getSerialID());      //M
//        req.setAgentCode(bo.getAgentCode());    //M
//        req.setMSISDN(bo.getMsisdn());          //M
//        req.setDigest(bo.getDigest());          //M
//
//        return req;
//    }
//
//    private VModiAnyPayInfoReq copyToModifyAnyPayInfoReq(WsAnyPayAgentBO bo){
//        VModiAnyPayInfoReq req = new VModiAnyPayInfoReq();
//        req.setSerialID(bo.getSerialID());      //M
//        req.setAgentCode(bo.getAgentCode());    //M
//        req.setMSISDN(bo.getMsisdn());          //M        
//        req.setDigest(bo.getDigest());          //M
//
//        //Fields can be modified
//        if (bo.getNewMSISDN() != null)
//            req.setNewMSISDN(bo.getNewMSISDN());
//        if (bo.getNewICCID() != null)
//            req.setNewICCID(bo.getNewICCID());
//        if (bo.getNewPassword() != null)
//            req.setNewPassword(bo.getNewPassword());
//        if (bo.getNewState() != null)
//            req.setNewState(bo.getNewState());
//        if (bo.getNewAccessType() != null)
//            req.setNewAccessType(bo.getNewAccessType());
//        if (bo.getNewValidDate() != null)
//            req.setNewValidDate(bo.getNewValidDate());
//        if (bo.getNewUserName() != null)
//            req.setNewUserName(bo.getNewUserName());
//        if (bo.getNewResidentID() != null)
//            req.setNewResidentID(bo.getNewResidentID());
//        if (bo.getNewAddress() != null)
//            req.setNewAddress(bo.getNewAddress());
//        if (bo.getNewPostcode() != null)
//            req.setNewPostcode(bo.getNewPostcode());
//        if (bo.getNewEmail() != null)
//            req.setNewEmail(bo.getNewEmail());
//        if (bo.getNewContactPh() != null)
//            req.setNewContactPh(bo.getNewContactPh());
//        if (bo.getNewServiceAddr() != null)
//            req.setNewServiceAddr(bo.getNewServiceAddr());
//        if (bo.getAgentType() != null)
//            req.setAgentType(bo.getAgentType());
//        
//        return req;
//    }
//    
//    private VCancelAnyPayAgentReq copyToCancelAnyPayAgentReq(WsAnyPayAgentBO bo){
//        VCancelAnyPayAgentReq req = new VCancelAnyPayAgentReq();
//        req.setSerialID(bo.getSerialID());      //M
//        req.setAgentCode(bo.getAgentCode());    //M
//        req.setMSISDN(bo.getMsisdn());          //M
//        req.setDigest(bo.getDigest());          //M
//
//        return req;
//    }
//    
//    
//    /**
//     * copy values of VCreateAnyPayAgentRsp to WsAnyPayAgentBO
//     * @param req
//     * @return
//     */
//    private WsAnyPayAgentBO copyFromCreateAnyPayAgentRsp(VCreateAnyPayAgentRsp rsp){
//        WsAnyPayAgentBO bo = new WsAnyPayAgentBO();
//        bo.setSerialID(rsp.getSerialID());
//        bo.setAgentCode(rsp.getAgentCode());
//        bo.setResult(rsp.getResult());
//        bo.setResInfo(rsp.getResInfo());        
//        bo.setResDigest(rsp.getDigest());
//        return bo;
//    }
//
//    private WsAnyPayAgentBO copyFromQueryAnyPayInfoRsp(VQueryAnyPayInfoRsp rsp){
//        WsAnyPayAgentBO bo = new WsAnyPayAgentBO();
//        bo.setSerialID(rsp.getSerialID());
//        bo.setAgentCode(rsp.getAgentCode());
//        bo.setResult(rsp.getResult());
//        bo.setIccid(rsp.getICCID());
//        bo.setBalanceInfo(rsp.getBalanceInfo());
//        bo.setCreatedDate(rsp.getCreatedDate());
//        bo.setState(rsp.getState());
//        bo.setAccessType(rsp.getAccessType());
//        bo.setValidDate(rsp.getValidDate());
//        bo.setUserName(rsp.getUserName());
//        bo.setResidentID(rsp.getResidentID());
//        bo.setAddress(rsp.getAddress());
//        bo.setPostcode(rsp.getPostcode());
//        bo.setEmail(rsp.getEmail());
//        bo.setContactPh(rsp.getContactPh());
//        bo.setServiceAddr(rsp.getServiceAddr());
//        bo.setResInfo(rsp.getResInfo());
//        bo.setAgentType(rsp.getAgentType());
//        bo.setResDigest(rsp.getDigest());
//        
//        return bo;
//    }
//
//    private WsAnyPayAgentBO copyFromModifyAnyPayInfoRsp(VModiAnyPayInfoRsp rsp){
//        WsAnyPayAgentBO bo = new WsAnyPayAgentBO();
//        bo.setSerialID(rsp.getSerialID());
//        bo.setAgentCode(rsp.getAgentCode());
//        bo.setResult(rsp.getResult());
//        bo.setResInfo(rsp.getResInfo());
//        bo.setResDigest(rsp.getDigest());
//        
//        return bo;
//    }
//
//    private WsAnyPayAgentBO copyFromCancelAnyPayAgentRsp(VCancelAnyPayAgentRsp rsp){
//        WsAnyPayAgentBO bo = new WsAnyPayAgentBO();
//        bo.setSerialID(rsp.getSerialID());
//        bo.setAgentCode(rsp.getAgentCode());        
//        bo.setResult(rsp.getResult());
//        bo.setResInfo(rsp.getResInfo());
//        bo.setResDigest(rsp.getDigest());
//        
//        return bo;
//    }
//
//    
//    
//    /**
//     * 
//     * @param bo
//     * @return
//     */    
//    private VRechargeAgentReq copyToRechargeAgentReq(WsRechargeAgentBO bo){
//        VRechargeAgentReq req = new VRechargeAgentReq();
//        req.setSerialID(bo.getSerialID());
//        req.setAgentCode(bo.getAgentCode());
//        req.setMSISDN(bo.getMsisdn());
//        req.setTransSerial(bo.getTransSerial());
//        req.setServiceType(bo.getServiceType());
//        req.setAmount(bo.getAmount());
//        req.setDigest(bo.getDigest());
//        return req;
//    }
//    
//    /**
//     * 
//     * @param req
//     * @return
//     */
//    private WsRechargeAgentBO copyFromRechargeAgentRsp(VRechargeAgentRsp rsp){
//        WsRechargeAgentBO bo = new WsRechargeAgentBO();
//        bo.setSerialID(rsp.getSerialID());
//        bo.setAgentCode(rsp.getAgentCode());        
//        bo.setResult(rsp.getResult());
//        bo.setBlcAfter(rsp.getBlcAfter());//(O)
//        bo.setTransID(rsp.getTransID());
//        bo.setResInfo(rsp.getResInfo());
//        bo.setResDigest(rsp.getDigest());
//        
//        return bo;
//    }
//    
//    
//    
//    /**
//     * 
//     * @param bo
//     * @return
//     */
//    private VCancelTransactionReq copyToCancelTransactionReq(WsTransactionBO bo){
//        VCancelTransactionReq req = new VCancelTransactionReq();
//        req.setSerialID(bo.getSerialID());
//        req.setAgentCode(bo.getAgentCode());
//        req.setMSISDN(bo.getMsisdn());
//        req.setTransID(bo.getTransID());
//        req.setServiceType(bo.getServiceType());
//        req.setOperType(bo.getOperType());
//        req.setAmount(bo.getAmount());
//        req.setDigest(bo.getDigest());
//        return req;
//    }
//    
//    /**
//     * 
//     * @param bo
//     * @return
//     */
//    private WsTransactionBO copyFromCancelTransactionRsp(VCancelTransactionRsp rsp){
//        WsTransactionBO bo = new WsTransactionBO();
//        bo.setSerialID(rsp.getSerialID());
//        bo.setAgentCode(rsp.getAgentCode());
//        bo.setResult(rsp.getResult());
//        bo.setBlcAfter(rsp.getBlcAfter());//(O)
//        bo.setTransID(rsp.getTransID());
//        bo.setResInfo(rsp.getResInfo());
//        bo.setResDigest(rsp.getDigest());
//        return bo;
//    }
//    
//       
//    
//    private VCancelAnyPayBlcReq copyToCancelAnyPayBlcReq(WsAnyPayAgentBO bo){
//        VCancelAnyPayBlcReq req = new VCancelAnyPayBlcReq();
//        req.setSerialID(bo.getSerialID());
//        req.setAgentCode(bo.getAgentCode());
//        req.setMSISDN(bo.getMsisdn());
//        req.setServiceType(bo.getServiceType());
//        req.setDigest(bo.getDigest());        
//        return req;
//    }
//        
//    private WsAnyPayAgentBO copyFromCancelAnyPayBlcRsp(VCancelAnyPayBlcRsp rsp){
//        WsAnyPayAgentBO bo = new WsAnyPayAgentBO();
//        bo.setSerialID(rsp.getSerialID());
//        bo.setAgentCode(rsp.getAgentCode());
//        bo.setBalance(rsp.getBalance());
//        bo.setResDigest(rsp.getDigest());
//        bo.setResult(rsp.getResult());
//        bo.setResInfo(rsp.getResInfo());
//        return bo;
//    }
//    
    
    
}
