/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.checkpost;

import java.util.HashMap;
import com.viettel.im.database.DAO.*;
import com.viettel.im.database.BO.*;
import com.viettel.common.channel.impl.obj.ObjectClientChannel;
import com.viettel.common.message.ServiceRequest;
import com.viettel.common.message.ServiceResponse;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.database.DAO.BaseDAOAction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.common.Constant;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author User
 */
public class checkReferenceNo extends BaseDAOAction {

    /** Log Object */
    private static final Log log = LogFactory.getLog(checkReferenceNo.class);
    private static final String MsgType = "0600";
    private static final String Command = "590000";
    private static final String REF_NO = "REF_NO";
    private static checkReferenceNo checkReferenceNo;
    private static ObjectClientChannel client;
    private String errorString;

    public checkReferenceNo()
            throws Exception {
        /*client = new ObjectClientChannel("192.168.176.190", 8383,
                "bccs_pos", "bccs_pos1222", true);
        client.setReceiverTimeout(100000);//miliseconds
        client.open();*/
        client = new ObjectClientChannel("10.58.4.37", 8383,
        "bccs_pos", "bccs_pos1222", true);
        client.setReceiverTimeout(100000);//miliseconds
        client.open();
    }

    public static synchronized checkReferenceNo getInstance()
            throws Exception {
        if (checkReferenceNo == null) {
            checkReferenceNo = new checkReferenceNo();
            return checkReferenceNo;
        }
        return checkReferenceNo;
    }

    /**
     * @author TUNGTV
     * @date: 08/09/2009
     * Thêm 0 vào truoc
     * @param  
     * @return Đối tương có kiểu String sau khi thêm 0 vào đầu
     */
    public static String addZeroToString(String input, int strLength) {
        String result = input;
        for (int i = 1; i <= strLength - input.length(); i++) {
            result = "0" + result;
        }
        return result;
    }

    /**
     *@purpose Tra ve thong tin giao dich qua MB Bank
     *@param reference: ma giao dich qua POS
     */
    public SaleTransPost getMBBankTransInfo(String reference, String collectionGroupCode, String collectionStaffCode, String userName, String messageType, String command, String Amount)
            throws Exception {
        log.info("Service. Begin");
        SaleTransPost saleTransPost = new SaleTransPost();
        ServiceRequest serviceRequest = new ServiceRequest();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDate = DateTimeUtils.getSysDateTime();
        String dateTime = dateFormat.format(calendar.getTime());
        Long salePostLogId = getSequence("SALE_POS_LOG_SEQ");

        serviceRequest.setMsgType("0600");
        serviceRequest.set("MTI", "0600");
        serviceRequest.setCommand(command);
        serviceRequest.set("TRANSMISSION_DATE", dateTime);
        serviceRequest.set("REF_NO", reference);
        serviceRequest.set("SHOP_CODE", collectionGroupCode);
        serviceRequest.set("STAFF_CODE", collectionStaffCode);
        serviceRequest.set("TID NUMBER", "60510001");
        serviceRequest.set("BCCS_POS_ID", addZeroToString(salePostLogId.toString(), 12));
        serviceRequest.set("MTI", "0600");
        serviceRequest.set("PROCESS_CODE", command);
        if (command.equals("580000")) {
            serviceRequest.set("SERVICE_CODE", "99");
        } else {
            serviceRequest.set("SERVICE_CODE", "00");
        }

        serviceRequest.set("CARD_NUMBER", "9704223047820418");
        if (Amount != null) {
            serviceRequest.set("TRANSACTION_AMOUNT", Amount);
        }
//        else {
//            serviceRequest.set("TRANSACTION_AMOUNT", "000008000000");
//        }
//        serviceRequest.set("SHOP_CODE", "01010006");
//        serviceRequest.set("STAFF_CODE", "TT_TESTER");
        serviceRequest.set("SYSTEM_TRACE", "001009");

        ServiceResponse svResponse = ((ServiceResponse) client.sendRequest(serviceRequest));
        if (svResponse != null) {
            PaymentPosLog paymentPostLog = new PaymentPosLog();
            String respCode = (String) svResponse.get("RESPONSE_CODE");
            if (!respCode.equals("00")) {
                errorString = getErrorString(respCode);
                if (!errorString.equals("")) {
                    saleTransPost.setError(errorString);
                }
                if (respCode.equals("28")) {
                    saleTransPost.setStatus(0L);
                }
            } else {
                saleTransPost.setError("");
                saleTransPost.setStatus(1L);
            }

            String refNo = (String) svResponse.get("REF_NO");
            if (refNo == null || "".equals(refNo)) {
                //return "Không trả về được mã giao dịch từ MB";
                saleTransPost.setError("Không trả về được mã giao dịch từ MB");
                saleTransPost.setStatus(0L);
            }
            String amount = (String) svResponse.get("TRANSACTION_AMOUNT");
            String mti = (String) svResponse.get("MTI");
            String processCode = (String) svResponse.get("PROCESS_CODE");
            String processingCode = (String) svResponse.get("PROCESSING_CODE");
            String tid = (String) svResponse.get("TID NUMBER");
            String cardNum = (String) svResponse.get("CARD_NUMBER");
            String traceNo = (String) svResponse.get("SYSTEM_TRACE");
            String transactionFee = (String) svResponse.get("TRANSACTION_FEE");
            //tuannv bo sung    
            String bin = "";
            if (cardNum != null) {
                if (cardNum.length() >= 6) {
                    bin = cardNum.substring(0, 6);
                } else {
                    bin = cardNum;
                }
            }

            String transmissionDate = (String) svResponse.get("TRANSMISSION_DATE");
            ///////////////////Luu log
            paymentPostLog.setPaymentPosId(salePostLogId);
            paymentPostLog.setReference(reference);
            paymentPostLog.setCardNumber(cardNum);

            paymentPostLog.setCreateDate(DateTimeUtils.convertStringToDateTime(currentDate));
            paymentPostLog.setMti(MsgType);
            paymentPostLog.setProcessingCode(processCode);
            paymentPostLog.setResponseCode(respCode);
            paymentPostLog.setStatus("1");
            paymentPostLog.setTidNo(tid);
            //tuannv bo sung
            paymentPostLog.setTransmissionDate(DateTimeUtils.convertStringToDateTimePOS(transmissionDate));
            /////////////
            if (traceNo != null && !"".equals(traceNo)) {
                paymentPostLog.setTraceNo(Long.parseLong(traceNo));
            }
            if (amount != null && !"".equals(amount)) {
                paymentPostLog.setTransactionAmount(Long.parseLong(amount) / 100);
            }
            if (transactionFee != null && !"".equals(transactionFee)) {
                paymentPostLog.setTransactionFee(Long.parseLong(transactionFee));
            }
            paymentPostLog.setUserName(userName);
            paymentPostLog.setViettelShopCode(collectionGroupCode);
            paymentPostLog.setViettelStaffCode(collectionStaffCode);

            getSession().save(paymentPostLog);

//            if (respCode == null || "".equals(respCode) || !respCode.equals("00") || refNo == null || "".equals(refNo)) {
////                 errorString = getErrorString(respCode);
////                if (!errorString.equals("")) {
////                    return errorString;
////                }
////                return respCode;
//            } else {
            //Tao saleTransPost
            if (amount != null && !"".equals(amount)) {
                saleTransPost.setAmount(Long.parseLong(amount) / 100);
            }
            if (refNo != null && !"".equals(refNo)) {
                saleTransPost.setReferenceNo(refNo);
            }
            if (processingCode != null && !"".equals(processingCode)) {
                saleTransPost.setProcessingCode(processingCode);
            } else {
                saleTransPost.setProcessingCode(command);
            }
            if (mti != null && !"".equals(mti)) {
                saleTransPost.setMti(mti);
            } else {
                saleTransPost.setMti(messageType);
            }
            if (tid != null && !"".equals(tid)) {
                saleTransPost.setTid(Long.parseLong(tid));
            }
            //tuannv bo sung
            if (bin != null && !"".equals(bin)) {
                saleTransPost.setBin(bin);
            }
            if (transmissionDate != null && !"".equals(transmissionDate)) {
                saleTransPost.setTransmissionDate(DateTimeUtils.convertPosDate(transmissionDate));
            }
            if (traceNo != null && !"".equals(traceNo)) {
                saleTransPost.setTraceNo(traceNo);
            }
            if (transactionFee != null && !"".equals(transactionFee)) {
                saleTransPost.setTransactionFee(Long.parseLong(transactionFee));
            }
            if (cardNum != null && !"".equals(cardNum)) {
                saleTransPost.setCardNumber(cardNum);
            }
            if (processCode != null && !"".equals(processCode)) {
                Date nowDate = DateTimeUtils.getSysDate();
                if (saleTransPost.getTid() != null) {
                    saleTransPost.setProcessCode(DateTimeUtils.getPosProcessCodeDate(nowDate) + saleTransPost.getTid().toString() + saleTransPost.getReferenceNo().toString());
                } else {
                    saleTransPost.setProcessCode(DateTimeUtils.getPosProcessCodeDate(nowDate) + saleTransPost.getReferenceNo().toString());
                }
            } else {
                saleTransPost.setProcessCode(command);
            }
            return saleTransPost;
//            }

        }
        log.info("Service. End");
        return null;
    }

    public String getErrorString(String responseCode) {
        errorString = "";
        if (responseCode != null) {
            if (responseCode.equals("01")) {
                errorString = "Có lỗi xảy ra trong hệ thống";
            } else if (responseCode.equals("03") || responseCode.equals("04")
                    || responseCode.equals("05") || responseCode.equals("06")
                    || responseCode.equals("09")
                    || responseCode.equals("10") || responseCode.equals("11")
                    || responseCode.equals("12")) {
                errorString = "Tham số không hợp lệ";
            } else if (responseCode.equals("07")) {
                errorString = "Lỗi kết nối";
            } else if (responseCode.equals("08")) {
                errorString = "Phiên kết nối đã kết thúc";
            } else if (responseCode.equals("09")) {
                errorString = "Lỗi kết nối cơ sở dữ liệu";
            } else if (responseCode.equals("103")) {
                errorString = "Không kết nối được với MB Bank";
            } else if (responseCode.equals("28")) {
                errorString = "Giao dịch đã bị hủy trên Ngân Hàng";
            } else if (responseCode.equals("73")) {
                errorString = "Mã giao dịch không tồn tại";
            } else if (responseCode.equals("32")) {
                errorString = "Connect time out";
            } else {
                errorString = "Có lỗi xảy ra";
            }
        }
        return errorString;
    }
}
