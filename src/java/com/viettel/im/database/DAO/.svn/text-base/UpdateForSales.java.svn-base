package com.viettel.im.database.DAO;

import com.viettel.common.ViettelService;
import com.viettel.im.client.bean.DbInfo;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author BinhTD
 * @date: 16-Jan-2010
 * @purpose:
 *
 */
public class UpdateForSales {

    Connection connection = null;
    PreparedStatement sStatement = null;
    String urlCTV = "";
    String userName = "";
    String passWord = "";
    String urlCTVEv = "";
    String userNameEv = "";
    String passWordEv = "";
    String urlCTVCus = "";
    String userNameCus = "";
    String passWordCus = "";
    ViettelService request = new ViettelService();


    public String ActiveAccount(ViettelService request) {

        ResultSet rs = null;
        try {
            connection = getConnection();
            String sInsertQuery = "INSERT INTO staff_stk VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            sStatement = connection.prepareStatement(sInsertQuery);
            connection.setAutoCommit(false);
            if (request.get("STAFF_STK_ID") != null && request.get("STAFF_STK_ID") != "") {
                sStatement.setLong(1, Long.parseLong(request.get("STAFF_STK_ID").toString()));
            } else {
                sStatement.setString(1, null);
            }
            if (request.get("MSISDN") != null && request.get("MSISDN") != "") {
                sStatement.setString(2, request.get("MSISDN").toString());
            } else {
                sStatement.setString(2, null);
            }
            if (request.get("ICCID") != null && request.get("ICCID") != "") {
                sStatement.setString(3, request.get("ICCID").toString());
            } else {
                sStatement.setString(3, null);
            }
            if (request.get("TRADE_NAME") != null && request.get("TRADE_NAME") != "") {
                sStatement.setString(4, request.get("TRADE_NAME").toString());
            } else {
                sStatement.setString(4, null);
            }
            if (request.get("OWNER_NAME") != null && request.get("OWNER_NAME") != "") {
                sStatement.setString(5, request.get("OWNER_NAME").toString());
            } else {
                sStatement.setString(5, null);
            }

            if (request.get("BIRTH_DATE") != null && request.get("BIRTH_DATE") != "") {
                Date x = DateTimeUtils.convertStringToDate(request.get("BIRTH_DATE").toString());
                sStatement.setDate(6, new java.sql.Date(x.getTime()));
                //new java.sql.Date(DateTimeUtils.convertStringToDate(request.get("BIRTH_DATE").toString())));
            } else {
                sStatement.setString(6, null);
            }
            if (request.get("CONTACT_NO") != null && request.get("CONTACT_NO") != "") {
                sStatement.setString(7, request.get("CONTACT_NO").toString());
            } else {
                sStatement.setString(7, null);
            }
            if (request.get("OUTLET_ADDRESS") != null && request.get("OUTLET_ADDRESS") != "") {
                sStatement.setString(8, request.get("OUTLET_ADDRESS").toString());
            } else {
                sStatement.setString(8, null);
            }
            if (request.get("EMAIL") != null && request.get("EMAIL") != "") {
                sStatement.setString(9, request.get("EMAIL").toString());
            } else {
                sStatement.setString(9, null);
            }
            if (request.get("SECURE_QUESTION") != null && request.get("SECURE_QUESTION") != "") {
                sStatement.setString(10, request.get("SECURE_QUESTION").toString());
            } else {
                sStatement.setString(10, null);
            }
            if (request.get("MPIN") != null && request.get("MPIN") != "") {
                sStatement.setString(11, request.get("MPIN").toString());
            } else {
                sStatement.setString(11, null);
            }
            if (request.get("SAP_CODE") != null && request.get("SAP_CODE") != "") {
                sStatement.setString(12, request.get("SAP_CODE").toString());
            } else {
                sStatement.setString(12, null);
            }
            Date date = new Date();
            java.sql.Timestamp nowDate = new java.sql.Timestamp(date.getTime());

//            if(request.get("CREATE_DATE")!=null || request.get("CREATE_DATE")!="" ){
//                Date create_Date = DateTimeUtils.convertStringToDate(request.get("CREATE_DATE").toString());
//                sStatement.setDate(13, new java.sql.Date(create_Date.getTime()));
//            } else {
//                sStatement.setString(13, null);
//            }
            sStatement.setTimestamp(13, nowDate);
            sStatement.setTimestamp(14, nowDate);
//            if(request.get("LAST_MODIFIED")!=null || request.get("LAST_MODIFIED")!="" ){
//                sStatement.setString(14, request.get("BIRTH_DATE").toString());
//            } else {
//                sStatement.setString(14, null);
//            }
            if (request.get("LOGIN_FAILURE_COUNT") != null && request.get("LOGIN_FAILURE_COUNT") != "") {
                sStatement.setLong(15, Long.parseLong(request.get("LOGIN_FAILURE_COUNT").toString()));
            } else {
                sStatement.setString(15, null);
            }
            if (request.get("STATUS") != null && request.get("STATUS") != "") {
                sStatement.setLong(16, Long.parseLong(request.get("STATUS").toString()));
            } else {
                sStatement.setString(16, null);
            }
            if (request.get("ACCOUNT_TYPE") != null && request.get("ACCOUNT_TYPE") != "") {
                sStatement.setString(17, request.get("ACCOUNT_TYPE").toString());
            } else {
                sStatement.setString(17, null);
            }
            if (request.get("PARENT_ID") != null && request.get("PARENT_ID") != "") {
                sStatement.setLong(18, Long.parseLong(request.get("PARENT_ID").toString()));
            } else {
                sStatement.setString(18, null);
            }
            if (request.get("TIN") != null && request.get("TIN") != "") {
                sStatement.setString(19, request.get("TIN").toString());
            } else {
                sStatement.setString(19, null);
            }
            if (request.get("MPIN_EXPIRE_DATE") != null && request.get("MPIN_EXPIRE_DATE") != "") {
                Date expire_Date = DateTimeUtils.convertStringToDate(request.get("MPIN_EXPIRE_DATE").toString());
                sStatement.setDate(20, new java.sql.Date(expire_Date.getTime()));
            } else {
                sStatement.setString(20, null);
            }
//            if(request.get("MPIN_EXPIRE_DATE")!=null && request.get("MPIN_EXPIRE_DATE")!="" ){
//                sStatement.setTimestamp(20, nowDate);
////                sStatement.setString(20, request.get("MPIN_EXPIRE_DATE").toString());
//            } else {
//                sStatement.setString(20, null);
//            }
            if (request.get("CENTRE_ID") != null && request.get("CENTRE_ID") != "") {
                sStatement.setLong(21, Long.parseLong(request.get("CENTRE_ID").toString()));
            } else {
                sStatement.setString(21, null);
            }
            if (request.get("ID_NO") != null && request.get("ID_NO") != "") {
                sStatement.setString(22, request.get("ID_NO").toString());
            } else {
                sStatement.setString(22, null);
            }
            if (request.get("MPIN_STATUS") != null && request.get("MPIN_STATUS") != "") {
                sStatement.setString(23, request.get("MPIN_STATUS").toString());
            } else {
                sStatement.setString(23, null);
            }
            if (request.get("SEX") != null && request.get("SEX") != "") {
                sStatement.setString(24, request.get("SEX").toString());
            } else {
                sStatement.setString(24, null);
            }
            if (request.get("ISSUE_DATE") != null && request.get("ISSUE_DATE") != "") {
                Date issue_Date = DateTimeUtils.convertStringToDate(request.get("ISSUE_DATE").toString());
                sStatement.setDate(25, new java.sql.Date(issue_Date.getTime()));
            } else {
                sStatement.setString(25, null);
            }
//            if(request.get("ISSUE_DATE")!=null && request.get("ISSUE_DATE")!="" ){
//                sStatement.setTimestamp(25, nowDate);
////                sStatement.setString(25, request.get("ISSUE_DATE").toString());
//            } else {
//                sStatement.setString(25, null);
//            }
            if (request.get("STAFF_CODE") != null && request.get("STAFF_CODE") != "") {
                sStatement.setString(26, request.get("STAFF_CODE").toString());
            } else {
                sStatement.setString(26, null);
            }
            if (request.get("PROVINCE") != null && request.get("PROVINCE") != "") {
                sStatement.setString(27, request.get("PROVINCE").toString());
            } else {
                sStatement.setString(27, null);
            }
            if (request.get("ISSUE_PLACE") != null && request.get("ISSUE_PLACE") != "") {
                sStatement.setString(28, request.get("ISSUE_PLACE").toString());
            } else {
                sStatement.setString(28, null);
            }
            if (request.get("FAX") != null && request.get("FAX") != "") {
                sStatement.setString(29, request.get("FAX").toString());
            } else {
                sStatement.setString(29, null);
            }
            if (request.get("DISTRICT") != null && request.get("DISTRICT") != "") {
                sStatement.setString(30, request.get("DISTRICT").toString());
            } else {
                sStatement.setString(30, null);
            }
            if (request.get("PRECINCT") != null && request.get("PRECINCT") != "") {
                sStatement.setString(31, request.get("PRECINCT").toString());
            } else {
                sStatement.setString(31, null);
            }
            if (request.get("NUM_ADD_MONEY") != null && request.get("NUM_ADD_MONEY") != "") {
                sStatement.setLong(32, Long.parseLong(request.get("NUM_ADD_MONEY").toString()));
            } else {
                sStatement.setString(32, null);
            }
            if (request.get("ANYPAY_STATUS") != null && request.get("ANYPAY_STATUS") != "") {
                sStatement.setLong(33, Long.parseLong(request.get("ANYPAY_STATUS").toString()));
            } else {
                sStatement.setString(33, null);
            }
            sStatement.execute();
            SqlCommon.closeStatement(sStatement);
            SqlCommon.commitOnly(connection);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception: " + e.getMessage());
            SqlCommon.rollback(connection);
            return "0.Co loi say ra trong qua trinh cap nhat trang thai";
        } finally {
            SqlCommon.closeResultSet(rs);
            SqlCommon.closeStatement(sStatement);
        }
        return "1.Thanh cong";
    }

    /**
     * author BinhTD
     * date: 09:17 18/01/2010
     * Cap nhat trang thai thong tin cong tac vien
     * @param: sISDN - So thue bao
     *         Status - Trang thai
     *         Reason - Ly do cap nhat
     * @return : 1 - Thanh cong
     *           0 - That bai
     */
    public String UpdateAccountStatus(String ISDN, Long Status, String Reason) {
        ResultSet rs = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            if (!Status.equals(0L)) {
                String SQLUpdate = "update staff_stk set status = ? where msisdn=? ";
                sStatement = connection.prepareStatement(SQLUpdate);
                sStatement.setLong(1, Status);
                sStatement.setString(2, ISDN);
            } else {
                String SQLUpdate = "update staff_stk set status = ?,anypay_status =?  where msisdn=? ";
                sStatement = connection.prepareStatement(SQLUpdate);
                sStatement.setLong(1, Status);
                sStatement.setLong(2, Status);
                sStatement.setString(3, ISDN);
            }
            int rowCount = sStatement.executeUpdate();
            boolean isUpdate = rowCount > 0 ? true : false;
            if (isUpdate) {
                SqlCommon.commitOnly(connection);
                return "1.Cap nhat trang thai thanh cong.";
            } else {
                return "0.Cap nhat trang thai that bai.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception: " + e.getMessage());
            SqlCommon.rollback(connection);
            return "0.Co loi say ra trong qua trinh cap nhat trang thai";
        } finally {
            SqlCommon.closeResultSet(rs);
            SqlCommon.closeStatement(sStatement);
        }
    }

    public String UpdateAccountStatusAndAnyPay(String ISDN, Long Status, Long StatusAnyPay, String Reason, String strErrorCode) {
        ResultSet rs = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            String SQLUpdate = "update staff_stk set status = ?,anypay_status =?  where msisdn=? ";
            sStatement = connection.prepareStatement(SQLUpdate);
            sStatement.setLong(1, Status);
            if (strErrorCode == null) {
                sStatement.setLong(2, StatusAnyPay);
            } else {
                sStatement.setLong(2, 9L);
            }
            sStatement.setString(3, ISDN);
            int rowCount = sStatement.executeUpdate();
            boolean isUpdate = rowCount > 0 ? true : false;
            if (isUpdate) {
                SqlCommon.commitOnly(connection);
                return "1.Cap nhat trang thai thanh cong.";
            } else {
                return "0.Cap nhat trang thai that bai.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception: " + e.getMessage());
            SqlCommon.rollback(connection);
            return "0.Co loi say ra trong qua trinh cap nhat trang thai";
        } finally {
            SqlCommon.closeResultSet(rs);
            SqlCommon.closeStatement(sStatement);
        }
    }

    public String UpdateAccountStatusAnyPay(String ISDN, Long Status, String Reason) {
        ResultSet rs = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            String SQLUpdate = "update staff_stk set anypay_status = ?  where msisdn=? ";
            sStatement = connection.prepareStatement(SQLUpdate);
            sStatement.setLong(1, Status);
            sStatement.setString(2, ISDN);
            int rowCount = sStatement.executeUpdate();
            boolean isUpdate = rowCount > 0 ? true : false;
            if (isUpdate) {
                SqlCommon.commitOnly(connection);
                return "1.Cap nhat trang thai thanh cong.";
            } else {
                return "0.Cap nhat trang thai that bai.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception: " + e.getMessage());
            SqlCommon.rollback(connection);
            return "0.Co loi say ra trong qua trinh cap nhat trang thai";
        } finally {
            SqlCommon.closeResultSet(rs);
            SqlCommon.closeStatement(sStatement);
        }
    }

    public String UnlockAccount(String ISDN, Long Status, String Reason, String strErrorCode) {
        ResultSet rs = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            String SQLUpdate = "update staff_stk set status = ?, anypay_status = ? , login_failure_count = 0  where msisdn=? ";
            sStatement = connection.prepareStatement(SQLUpdate);
            sStatement.setLong(1, Status);
            if (strErrorCode == null) {
                sStatement.setLong(2, Status);
            } else {
                sStatement.setLong(2, 9L);
            }
            sStatement.setString(3, ISDN);
            int rowCount = sStatement.executeUpdate();
            boolean isUpdate = rowCount > 0 ? true : false;
            if (isUpdate) {
                SqlCommon.commitOnly(connection);
                return "1.Cap nhat trang thai thanh cong.";
            } else {
                return "0.Cap nhat trang thai that bai.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception: " + e.getMessage());
            SqlCommon.rollback(connection);
            return "0.Co loi say ra trong qua trinh cap nhat trang thai";
        } finally {
            SqlCommon.closeResultSet(rs);
            SqlCommon.closeStatement(sStatement);
        }
    }

    /**
     * author BinhTD
     * date: 09:17 18/01/2010
     * Cap nhat password thong tin cong tac vien
     * @param: sISDN - So thue bao
     *         Status - Trang thai
     *         Reason - Ly do cap nhat
     * @return : 1 - Thanh cong
     *           0 - That bai
     */
    public String UpdateAccountPassword(String ISDN, String PASSWORD) {
        ResultSet rs = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            String SQLUpdate = "update staff_stk set mpin = ?  where msisdn=? ";
            sStatement = connection.prepareStatement(SQLUpdate);
            sStatement.setString(1, PASSWORD);
            sStatement.setString(2, ISDN);
            int rowCount = sStatement.executeUpdate();
            boolean isUpdate = rowCount > 0 ? true : false;
            if (isUpdate) {
                SqlCommon.commitOnly(connection);
                return "1.Cap nhat password thanh cong.";
            } else {
                return "0.Cap nhat password that bai.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception: " + e.getMessage());
            SqlCommon.rollback(connection);
            return "0.Co loi say ra trong qua trinh cap nhat password";
        } finally {
            SqlCommon.closeResultSet(rs);
            SqlCommon.closeStatement(sStatement);
        }
    }

    /**
     * author BinhTD
     * date: 09:17 18/01/2010
     * Cap nhat thong tin cong tac vien
     * @param: sISDN - So thue bao
     *         Status - Trang thai
     *         Reason - Ly do cap nhat
     * @return : 1 - Thanh cong
     *           0 - That bai
     */
    public String UpdateAccountInfo(ViettelService request) {
        ResultSet rs = null;
        try {
            connection = getConnection();
            String sUpdateQuery = "update staff_stk set STAFF_STK_ID=?, "
                    + " ICCID = ?,"
                    + " TRADE_NAME =?,"
                    + " OWNER_NAME=?,"
                    + " BIRTH_DATE=?,"
                    + " CONTACT_NO=?,"
                    + " OUTLET_ADDRESS=?,"
                    + " EMAIL =?,"
                    + " SECURE_QUESTION=?,"
                    //                    + " MPIN=?,"
                    + " SAP_CODE =?,"
                    + " CREATE_DATE=?,"
                    + " LAST_MODIFIED=?,"
                    + " LOGIN_FAILURE_COUNT=?,"
                    //                    + " STATUS=?,"
                    + " ACCOUNT_TYPE=?,"
                    + " PARENT_ID=?,"
                    + " TIN=?,"
                    + " MPIN_EXPIRE_DATE=?,"
                    + " CENTRE_ID=?,"
                    + " ID_NO=?,"
                    + " MPIN_STATUS=?,"
                    + " SEX=?,"
                    + " ISSUE_DATE=?,"
                    + " STAFF_CODE=?,"
                    + " PROVINCE=?,"
                    + " ISSUE_PLACE=?,"
                    + " FAX=?,"
                    + " DISTRICT=?,"
                    + " PRECINCT=?,"
                    + " NUM_ADD_MONEY=?"
                    + " where MSISDN = ?";
            sStatement = connection.prepareStatement(sUpdateQuery);
            connection.setAutoCommit(false);
            if (request.get("STAFF_STK_ID") != null && request.get("STAFF_STK_ID") != "") {
                sStatement.setLong(1, Long.parseLong(request.get("STAFF_STK_ID").toString()));
            } else {
                sStatement.setString(1, null);
            }
            if (request.get("ICCID") != null && request.get("ICCID") != "") {
                sStatement.setString(2, request.get("ICCID").toString());
            } else {
                sStatement.setString(2, null);
            }
            if (request.get("TRADE_NAME") != null && request.get("TRADE_NAME") != "") {
                sStatement.setString(3, request.get("TRADE_NAME").toString());
            } else {
                sStatement.setString(3, null);
            }
            if (request.get("OWNER_NAME") != null && request.get("OWNER_NAME") != "") {
                sStatement.setString(4, request.get("OWNER_NAME").toString());
            } else {
                sStatement.setString(4, null);
            }

            if (request.get("BIRTH_DATE") != null && request.get("BIRTH_DATE") != "") {
                Date x = DateTimeUtils.convertStringToDate(request.get("BIRTH_DATE").toString());
                sStatement.setDate(5, new java.sql.Date(x.getTime()));
                //new java.sql.Date(DateTimeUtils.convertStringToDate(request.get("BIRTH_DATE").toString())));
            } else {
                sStatement.setString(5, null);
            }
            if (request.get("CONTACT_NO") != null && request.get("CONTACT_NO") != "") {
                sStatement.setString(6, request.get("CONTACT_NO").toString());
            } else {
                sStatement.setString(6, null);
            }
            if (request.get("OUTLET_ADDRESS") != null && request.get("OUTLET_ADDRESS") != "") {
                sStatement.setString(7, request.get("OUTLET_ADDRESS").toString());
            } else {
                sStatement.setString(7, null);
            }
            if (request.get("EMAIL") != null && request.get("EMAIL") != "") {
                sStatement.setString(8, request.get("EMAIL").toString());
            } else {
                sStatement.setString(8, null);
            }
            if (request.get("SECURE_QUESTION") != null && request.get("SECURE_QUESTION") != "") {
                sStatement.setString(9, request.get("SECURE_QUESTION").toString());
            } else {
                sStatement.setString(9, null);
            }
//            if (request.get("MPIN") != null && request.get("MPIN") != "") {
//                sStatement.setString(10, request.get("MPIN").toString());
//            } else {
//                sStatement.setString(10, null);
//            }
            if (request.get("SAP_CODE") != null && request.get("SAP_CODE") != "") {
                sStatement.setString(10, request.get("SAP_CODE").toString());
            } else {
                sStatement.setString(10, null);
            }
            Date date = new Date();
            java.sql.Timestamp nowDate = new java.sql.Timestamp(date.getTime());

            if (request.get("CREATE_DATE") != null && request.get("CREATE_DATE") != "") {
                Date create_Date = DateTimeUtils.convertStringToDate(request.get("CREATE_DATE").toString());
                sStatement.setDate(11, new java.sql.Date(create_Date.getTime()));
            } else {
                sStatement.setString(11, null);
            }
//            sStatement.setTimestamp(12, nowDate);
            sStatement.setTimestamp(12, nowDate);
//            if(request.get("LAST_MODIFIED")!=null || request.get("LAST_MODIFIED")!="" ){
//                sStatement.setString(14, request.get("BIRTH_DATE").toString());
//            } else {
//                sStatement.setString(14, null);
//            }
            if (request.get("LOGIN_FAILURE_COUNT") != null && request.get("LOGIN_FAILURE_COUNT") != "") {
                sStatement.setLong(13, Long.parseLong(request.get("LOGIN_FAILURE_COUNT").toString()));
            } else {
                sStatement.setString(13, null);
            }
//            if (request.get("STATUS") != null && request.get("STATUS") != "") {
//                sStatement.setLong(14, Long.parseLong(request.get("STATUS").toString()));
//            } else {
//                sStatement.setString(14, null);
//            }
            if (request.get("ACCOUNT_TYPE") != null && request.get("ACCOUNT_TYPE") != "") {
                sStatement.setString(14, request.get("ACCOUNT_TYPE").toString());
            } else {
                sStatement.setString(14, null);
            }
            if (request.get("PARENT_ID") != null && request.get("PARENT_ID") != "") {
                sStatement.setLong(15, Long.parseLong(request.get("PARENT_ID").toString()));
            } else {
                sStatement.setString(15, null);
            }
            if (request.get("TIN") != null && request.get("TIN") != "") {
                sStatement.setString(16, request.get("TIN").toString());
            } else {
                sStatement.setString(16, null);
            }
            if (request.get("MPIN_EXPIRE_DATE") != null && request.get("MPIN_EXPIRE_DATE") != "") {
                Date expire_Date = DateTimeUtils.convertStringToDate(request.get("MPIN_EXPIRE_DATE").toString());
                sStatement.setDate(17, new java.sql.Date(expire_Date.getTime()));
            } else {
                sStatement.setString(17, null);
            }
//            if(request.get("MPIN_EXPIRE_DATE")!=null && request.get("MPIN_EXPIRE_DATE")!="" ){
//                sStatement.setTimestamp(20, nowDate);
////                sStatement.setString(20, request.get("MPIN_EXPIRE_DATE").toString());
//            } else {
//                sStatement.setString(20, null);
//            }
            if (request.get("CENTRE_ID") != null && request.get("CENTRE_ID") != "") {
                sStatement.setLong(18, Long.parseLong(request.get("CENTRE_ID").toString()));
            } else {
                sStatement.setString(18, null);
            }
            if (request.get("ID_NO") != null && request.get("ID_NO") != "") {
                sStatement.setString(19, request.get("ID_NO").toString());
            } else {
                sStatement.setString(19, null);
            }
            if (request.get("MPIN_STATUS") != null && request.get("MPIN_STATUS") != "") {
                sStatement.setString(20, request.get("MPIN_STATUS").toString());
            } else {
                sStatement.setString(20, null);
            }
            if (request.get("SEX") != null && request.get("SEX") != "") {
                sStatement.setString(21, request.get("SEX").toString());
            } else {
                sStatement.setString(21, null);
            }
            if (request.get("ISSUE_DATE") != null && request.get("ISSUE_DATE") != "") {
                Date issue_Date = DateTimeUtils.convertStringToDate(request.get("ISSUE_DATE").toString());
                sStatement.setDate(22, new java.sql.Date(issue_Date.getTime()));
            } else {
                sStatement.setString(22, null);
            }
//            if(request.get("ISSUE_DATE")!=null && request.get("ISSUE_DATE")!="" ){
//                sStatement.setTimestamp(25, nowDate);
////                sStatement.setString(25, request.get("ISSUE_DATE").toString());
//            } else {
//                sStatement.setString(25, null);
//            }
            if (request.get("STAFF_CODE") != null && request.get("STAFF_CODE") != "") {
                sStatement.setString(23, request.get("STAFF_CODE").toString());
            } else {
                sStatement.setString(23, null);
            }
            if (request.get("PROVINCE") != null && request.get("PROVINCE") != "") {
                sStatement.setString(24, request.get("PROVINCE").toString());
            } else {
                sStatement.setString(24, null);
            }
            if (request.get("ISSUE_PLACE") != null && request.get("ISSUE_PLACE") != "") {
                sStatement.setString(25, request.get("ISSUE_PLACE").toString());
            } else {
                sStatement.setString(25, null);
            }
            if (request.get("FAX") != null && request.get("FAX") != "") {
                sStatement.setString(26, request.get("FAX").toString());
            } else {
                sStatement.setString(26, null);
            }
            if (request.get("DISTRICT") != null && request.get("DISTRICT") != "") {
                sStatement.setString(27, request.get("DISTRICT").toString());
            } else {
                sStatement.setString(27, null);
            }
            if (request.get("PRECINCT") != null && request.get("PRECINCT") != "") {
                sStatement.setString(28, request.get("PRECINCT").toString());
            } else {
                sStatement.setString(28, null);
            }
            if (request.get("NUM_ADD_MONEY") != null && request.get("NUM_ADD_MONEY") != "") {
                sStatement.setLong(29, Long.parseLong(request.get("NUM_ADD_MONEY").toString()));
            } else {
                sStatement.setString(29, null);
            }
            if (request.get("MSISDN") != null && request.get("MSISDN") != "") {
                sStatement.setString(30, request.get("MSISDN").toString());
            } else {
                sStatement.setString(30, null);
            }
            int rowCount = sStatement.executeUpdate();
            boolean isUpdate = rowCount > 0 ? true : false;
            if (isUpdate) {
                SqlCommon.commitOnly(connection);
                return "1.Thanh cong.";
            } else {
                return "1.Cap nhat thong tin CTV that bai.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception: " + e.getMessage());
            SqlCommon.rollback(connection);
            return "0.Co loi say ra trong qua trinh cap nhat thong tin";
        } finally {
            SqlCommon.closeResultSet(rs);
            SqlCommon.closeStatement(sStatement);
        }
    }

    public String reActiveAccount(ViettelService request) {
        ResultSet rs = null;
        try {
            connection = getConnection();
            String sUpdateQuery = "update staff_stk set STAFF_STK_ID=?, "
                    + " ICCID = ?,"
                    + " TRADE_NAME =?,"
                    + " OWNER_NAME=?,"
                    + " BIRTH_DATE=?,"
                    + " CONTACT_NO=?,"
                    + " OUTLET_ADDRESS=?,"
                    + " EMAIL =?,"
                    + " SECURE_QUESTION=?,"
                    + " MPIN=?,"
                    + " SAP_CODE =?,"
                    + " CREATE_DATE=?,"
                    + " LAST_MODIFIED=?,"
                    + " LOGIN_FAILURE_COUNT=?,"
                    + " STATUS=?,"
                    + " ACCOUNT_TYPE=?,"
                    + " PARENT_ID=?,"
                    + " TIN=?,"
                    + " MPIN_EXPIRE_DATE=?,"
                    + " CENTRE_ID=?,"
                    + " ID_NO=?,"
                    + " MPIN_STATUS=?,"
                    + " SEX=?,"
                    + " ISSUE_DATE=?,"
                    + " STAFF_CODE=?,"
                    + " PROVINCE=?,"
                    + " ISSUE_PLACE=?,"
                    + " FAX=?,"
                    + " DISTRICT=?,"
                    + " PRECINCT=?,"
                    + " NUM_ADD_MONEY=?,"
                    + " MSISDN = ?"
                    + " where STAFF_STK_ID = ? and STATUS <> 1";
            sStatement = connection.prepareStatement(sUpdateQuery);
            connection.setAutoCommit(false);
            if (request.get("STAFF_STK_ID") != null && request.get("STAFF_STK_ID") != "") {
                sStatement.setLong(1, Long.parseLong(request.get("STAFF_STK_ID").toString()));
            } else {
                sStatement.setString(1, null);
            }
            if (request.get("ICCID") != null && request.get("ICCID") != "") {
                sStatement.setString(2, request.get("ICCID").toString());
            } else {
                sStatement.setString(2, null);
            }
            if (request.get("TRADE_NAME") != null && request.get("TRADE_NAME") != "") {
                sStatement.setString(3, request.get("TRADE_NAME").toString());
            } else {
                sStatement.setString(3, null);
            }
            if (request.get("OWNER_NAME") != null && request.get("OWNER_NAME") != "") {
                sStatement.setString(4, request.get("OWNER_NAME").toString());
            } else {
                sStatement.setString(4, null);
            }

            if (request.get("BIRTH_DATE") != null && request.get("BIRTH_DATE") != "") {
                Date x = DateTimeUtils.convertStringToDate(request.get("BIRTH_DATE").toString());
                sStatement.setDate(5, new java.sql.Date(x.getTime()));
                //new java.sql.Date(DateTimeUtils.convertStringToDate(request.get("BIRTH_DATE").toString())));
            } else {
                sStatement.setString(5, null);
            }
            if (request.get("CONTACT_NO") != null && request.get("CONTACT_NO") != "") {
                sStatement.setString(6, request.get("CONTACT_NO").toString());
            } else {
                sStatement.setString(6, null);
            }
            if (request.get("OUTLET_ADDRESS") != null && request.get("OUTLET_ADDRESS") != "") {
                sStatement.setString(7, request.get("OUTLET_ADDRESS").toString());
            } else {
                sStatement.setString(7, null);
            }
            if (request.get("EMAIL") != null && request.get("EMAIL") != "") {
                sStatement.setString(8, request.get("EMAIL").toString());
            } else {
                sStatement.setString(8, null);
            }
            if (request.get("SECURE_QUESTION") != null && request.get("SECURE_QUESTION") != "") {
                sStatement.setString(9, request.get("SECURE_QUESTION").toString());
            } else {
                sStatement.setString(9, null);
            }
            if (request.get("MPIN") != null && request.get("MPIN") != "") {
                sStatement.setString(10, request.get("MPIN").toString());
            } else {
                sStatement.setString(10, null);
            }
            if (request.get("SAP_CODE") != null && request.get("SAP_CODE") != "") {
                sStatement.setString(11, request.get("SAP_CODE").toString());
            } else {
                sStatement.setString(11, null);
            }
            Date date = new Date();
            java.sql.Timestamp nowDate = new java.sql.Timestamp(date.getTime());

            if (request.get("CREATE_DATE") != null && request.get("CREATE_DATE") != "") {
                Date create_Date = DateTimeUtils.convertStringToDate(request.get("CREATE_DATE").toString());
                sStatement.setDate(12, new java.sql.Date(create_Date.getTime()));
            } else {
                sStatement.setString(12, null);
            }
//            sStatement.setTimestamp(12, nowDate);
            sStatement.setTimestamp(13, nowDate);
//            if(request.get("LAST_MODIFIED")!=null || request.get("LAST_MODIFIED")!="" ){
//                sStatement.setString(14, request.get("BIRTH_DATE").toString());
//            } else {
//                sStatement.setString(14, null);
//            }
            if (request.get("LOGIN_FAILURE_COUNT") != null && request.get("LOGIN_FAILURE_COUNT") != "") {
                sStatement.setLong(14, Long.parseLong(request.get("LOGIN_FAILURE_COUNT").toString()));
            } else {
                sStatement.setString(14, null);
            }
            if (request.get("STATUS") != null && request.get("STATUS") != "") {
                sStatement.setLong(15, Long.parseLong(request.get("STATUS").toString()));
            } else {
                sStatement.setString(15, null);
            }
            if (request.get("ACCOUNT_TYPE") != null && request.get("ACCOUNT_TYPE") != "") {
                sStatement.setString(16, request.get("ACCOUNT_TYPE").toString());
            } else {
                sStatement.setString(16, null);
            }
            if (request.get("PARENT_ID") != null && request.get("PARENT_ID") != "") {
                sStatement.setLong(17, Long.parseLong(request.get("PARENT_ID").toString()));
            } else {
                sStatement.setString(17, null);
            }
            if (request.get("TIN") != null && request.get("TIN") != "") {
                sStatement.setString(18, request.get("TIN").toString());
            } else {
                sStatement.setString(18, null);
            }
            if (request.get("MPIN_EXPIRE_DATE") != null && request.get("MPIN_EXPIRE_DATE") != "") {
                Date expire_Date = DateTimeUtils.convertStringToDate(request.get("MPIN_EXPIRE_DATE").toString());
                sStatement.setDate(19, new java.sql.Date(expire_Date.getTime()));
            } else {
                sStatement.setString(19, null);
            }
//            if(request.get("MPIN_EXPIRE_DATE")!=null && request.get("MPIN_EXPIRE_DATE")!="" ){
//                sStatement.setTimestamp(20, nowDate);
////                sStatement.setString(20, request.get("MPIN_EXPIRE_DATE").toString());
//            } else {
//                sStatement.setString(20, null);
//            }
            if (request.get("CENTRE_ID") != null && request.get("CENTRE_ID") != "") {
                sStatement.setLong(20, Long.parseLong(request.get("CENTRE_ID").toString()));
            } else {
                sStatement.setString(20, null);
            }
            if (request.get("ID_NO") != null && request.get("ID_NO") != "") {
                sStatement.setString(21, request.get("ID_NO").toString());
            } else {
                sStatement.setString(21, null);
            }
            if (request.get("MPIN_STATUS") != null && request.get("MPIN_STATUS") != "") {
                sStatement.setString(22, request.get("MPIN_STATUS").toString());
            } else {
                sStatement.setString(22, null);
            }
            if (request.get("SEX") != null && request.get("SEX") != "") {
                sStatement.setString(23, request.get("SEX").toString());
            } else {
                sStatement.setString(23, null);
            }
            if (request.get("ISSUE_DATE") != null && request.get("ISSUE_DATE") != "") {
                Date issue_Date = DateTimeUtils.convertStringToDate(request.get("ISSUE_DATE").toString());
                sStatement.setDate(24, new java.sql.Date(issue_Date.getTime()));
            } else {
                sStatement.setString(24, null);
            }
//            if(request.get("ISSUE_DATE")!=null && request.get("ISSUE_DATE")!="" ){
//                sStatement.setTimestamp(25, nowDate);
////                sStatement.setString(25, request.get("ISSUE_DATE").toString());
//            } else {
//                sStatement.setString(25, null);
//            }
            if (request.get("STAFF_CODE") != null && request.get("STAFF_CODE") != "") {
                sStatement.setString(25, request.get("STAFF_CODE").toString());
            } else {
                sStatement.setString(25, null);
            }
            if (request.get("PROVINCE") != null && request.get("PROVINCE") != "") {
                sStatement.setString(26, request.get("PROVINCE").toString());
            } else {
                sStatement.setString(26, null);
            }
            if (request.get("ISSUE_PLACE") != null && request.get("ISSUE_PLACE") != "") {
                sStatement.setString(27, request.get("ISSUE_PLACE").toString());
            } else {
                sStatement.setString(27, null);
            }
            if (request.get("FAX") != null && request.get("FAX") != "") {
                sStatement.setString(28, request.get("FAX").toString());
            } else {
                sStatement.setString(28, null);
            }
            if (request.get("DISTRICT") != null && request.get("DISTRICT") != "") {
                sStatement.setString(29, request.get("DISTRICT").toString());
            } else {
                sStatement.setString(29, null);
            }
            if (request.get("PRECINCT") != null && request.get("PRECINCT") != "") {
                sStatement.setString(30, request.get("PRECINCT").toString());
            } else {
                sStatement.setString(30, null);
            }
            if (request.get("NUM_ADD_MONEY") != null && request.get("NUM_ADD_MONEY") != "") {
                sStatement.setLong(31, Long.parseLong(request.get("NUM_ADD_MONEY").toString()));
            } else {
                sStatement.setString(31, null);
            }
            if (request.get("MSISDN") != null && request.get("MSISDN") != "") {
                sStatement.setString(32, request.get("MSISDN").toString());
            } else {
                sStatement.setString(32, null);
            }
            if (request.get("STAFF_STK_ID") != null && request.get("STAFF_STK_ID") != "") {
                sStatement.setLong(33, Long.parseLong(request.get("STAFF_STK_ID").toString()));
            } else {
                sStatement.setString(33, null);
            }
            int rowCount = sStatement.executeUpdate();
            boolean isUpdate = rowCount > 0 ? true : false;
            if (isUpdate) {
                SqlCommon.commitOnly(connection);
                return "1.Thanh cong.";
            } else {
                return "0.Cap nhat thong tin CTV that bai.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception: " + e.getMessage());
            SqlCommon.rollback(connection);
            return "0.Co loi say ra trong qua trinh cap nhat thong tin";
        } finally {
            SqlCommon.closeResultSet(rs);
            SqlCommon.closeStatement(sStatement);
        }
    }

    /**
     * author BinhTD
     * date: 09:17 18/01/2010
     * Lay thont tin CTV
     * @param: sISDN - So thue bao
     *          sSapCode - Ma CTV
     * @return : ViettelService
     *  - null: Exception
     */
    public ViettelService FindAccountAnypay(String sISDN, String sSapCode) {
        ResultSet rs = null;

        try {
            connection = getConnectionEvoucher();
            connection.setAutoCommit(false);
            String schemaEvoucher = ResourceBundleUtils.getResource("schemaEvoucher");
            String SQLSearch = "select * from " + schemaEvoucher + ".Agent where 1=1";
            if (sISDN != null && !sISDN.equals("")) {
                SQLSearch = SQLSearch + " and msisdn = ?";
            }
            if (sSapCode != null && !sSapCode.equals("")) {
                SQLSearch = SQLSearch + " and sap_code=?";
            }
            sStatement = connection.prepareStatement(SQLSearch);
            if (sISDN != null && !sISDN.equals("")) {
                sStatement.setString(1, sISDN);
            }
            if (sISDN != null && !sISDN.equals("") && sSapCode != null && !sSapCode.equals("")) {
                sStatement.setString(2, sSapCode);
            } else {
                if (sSapCode != null && !sSapCode.equals("")) {
                    sStatement.setString(1, sSapCode);
                }
            }
            rs = sStatement.executeQuery();
            while (rs.next()) {
                if (rs.getString("AGENT_ID") != null) {
                    request.set("STAFF_STK_ID", rs.getLong("AGENT_ID"));
                }
                if (rs.getString("MSISDN") != null) {
                    request.set("MSISDN", rs.getString("MSISDN"));
                }
                if (rs.getString("ICCID") != null) {
                    request.set("ICCID", rs.getString("ICCID"));
                }
                if (rs.getString("TRADE_NAME") != null) {
                    request.set("TRADE_NAME", rs.getString("TRADE_NAME"));
                }
                if (rs.getString("OWNER_NAME") != null) {
                    request.set("OWNER_NAME", rs.getString("OWNER_NAME"));
                }
                if (rs.getString("BIRTH_DATE") != null) {
                    request.set("BIRTH_DATE", rs.getDate("BIRTH_DATE"));
                }
                if (rs.getString("CONTACT_NO") != null) {
                    request.set("CONTACT_NO", rs.getString("CONTACT_NO"));
                }
                if (rs.getString("OUTLET_ADDRESS") != null) {
                    request.set("OUTLET_ADDRESS", rs.getString("OUTLET_ADDRESS"));
                }
                if (rs.getString("EMAIL") != null) {
                    request.set("EMAIL", rs.getString("EMAIL"));
                }
                if (rs.getString("SECURE_QUESTION") != null) {
                    request.set("SECURE_QUESTION", rs.getString("SECURE_QUESTION"));
                }
                if (rs.getString("MPIN") != null) {
                    request.set("MPIN", rs.getString("MPIN"));
                }
                if (rs.getString("SAP_CODE") != null) {
                    request.set("SAP_CODE", rs.getString("SAP_CODE"));
                }
                if (rs.getString("CREATE_DATE") != null) {
                    request.set("CREATE_DATE", rs.getDate("CREATE_DATE"));
                }
                if (rs.getString("LAST_MODIFIED") != null) {
                    request.set("LAST_MODIFIED", rs.getDate("LAST_MODIFIED"));
                }
                if (rs.getString("LOGIN_FAILURE_COUNT") != null) {
                    request.set("LOGIN_FAILURE_COUNT", rs.getLong("LOGIN_FAILURE_COUNT"));
                }
                if (rs.getString("STATUS") != null) {
                    request.set("STATUS", rs.getLong("STATUS"));
                }
                if (rs.getString("ACCOUNT_TYPE") != null) {
                    request.set("ACCOUNT_TYPE", rs.getString("ACCOUNT_TYPE"));
                }
                if (rs.getString("PARENT_ID") != null) {
                    request.set("PARENT_ID", rs.getLong("PARENT_ID"));
                }
                if (rs.getString("TIN") != null) {
                    request.set("TIN", rs.getString("TIN"));
                }
                if (rs.getString("MPIN_EXPIRE_DATE") != null) {
                    request.set("MPIN_EXPIRE_DATE", rs.getDate("MPIN_EXPIRE_DATE"));
                }
                if (rs.getString("CENTRE_ID") != null) {
                    request.set("CENTRE_ID", rs.getLong("CENTRE_ID"));
                }
                if (rs.getString("ID_NO") != null) {
                    request.set("ID_NO", rs.getString("ID_NO"));
                }
                if (rs.getString("MPIN_STATUS") != null) {
                    request.set("MPIN_STATUS", rs.getString("MPIN_STATUS"));
                }
                if (rs.getString("SEX") != null) {
                    request.set("SEX", rs.getString("SEX"));
                }
                if (rs.getString("ISSUE_DATE") != null) {
                    request.set("ISSUE_DATE", rs.getDate("ISSUE_DATE"));
                }
                if (rs.getString("STAFF_CODE") != null) {
                    request.set("STAFF_CODE", rs.getString("STAFF_CODE"));
                }

                if (rs.getString("PROVINCE") != null) {
                    request.set("PROVINCE", rs.getString("PROVINCE"));
                }
                if (rs.getString("ISSUE_PLACE") != null) {
                    request.set("ISSUE_PLACE", rs.getString("ISSUE_PLACE"));
                }
                if (rs.getString("FAX") != null) {
                    request.set("FAX", rs.getString("FAX"));
                }
                if (rs.getString("DISTRICT") != null) {
                    request.set("DISTRICT", rs.getString("DISTRICT"));
                }
                if (rs.getString("PRECINCT") != null) {
                    request.set("PRECINCT", rs.getString("PRECINCT"));
                }
                if (rs.getString("NUM_ADD_MONEY") != null) {
                    request.set("NUM_ADD_MONEY", rs.getLong("NUM_ADD_MONEY"));
                }
            }
            SqlCommon.closeResultSet(rs);
            SqlCommon.closeStatement(sStatement);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public ViettelService FindAccountAnypayByAgentId(Long agentID) {

 
        ResultSet rs = null;

        try {
            connection = getConnectionEvoucher();
            connection.setAutoCommit(false);
            String schemaEvoucher = ResourceBundleUtils.getResource("schemaEvoucher");
            String SQLSearch = "select * from " + schemaEvoucher + ".Agent where agent_id=?";
            sStatement = connection.prepareStatement(SQLSearch);
            sStatement.setLong(1, agentID);
            rs = sStatement.executeQuery();
            while (rs.next()) {
                if (rs.getString("AGENT_ID") != null) {
                    request.set("STAFF_STK_ID", rs.getLong("AGENT_ID"));
                }
                if (rs.getString("MSISDN") != null) {
                    request.set("MSISDN", rs.getString("MSISDN"));
                }
                if (rs.getString("ICCID") != null) {
                    request.set("ICCID", rs.getString("ICCID"));
                }
                if (rs.getString("TRADE_NAME") != null) {
                    request.set("TRADE_NAME", rs.getString("TRADE_NAME"));
                }
                if (rs.getString("OWNER_NAME") != null) {
                    request.set("OWNER_NAME", rs.getString("OWNER_NAME"));
                }
                if (rs.getString("BIRTH_DATE") != null) {
                    request.set("BIRTH_DATE", rs.getDate("BIRTH_DATE"));
                }
                if (rs.getString("CONTACT_NO") != null) {
                    request.set("CONTACT_NO", rs.getString("CONTACT_NO"));
                }
                if (rs.getString("OUTLET_ADDRESS") != null) {
                    request.set("OUTLET_ADDRESS", rs.getString("OUTLET_ADDRESS"));
                }
                if (rs.getString("EMAIL") != null) {
                    request.set("EMAIL", rs.getString("EMAIL"));
                }
                if (rs.getString("SECURE_QUESTION") != null) {
                    request.set("SECURE_QUESTION", rs.getString("SECURE_QUESTION"));
                }
                if (rs.getString("MPIN") != null) {
                    request.set("MPIN", rs.getString("MPIN"));
                }
                if (rs.getString("SAP_CODE") != null) {
                    request.set("SAP_CODE", rs.getString("SAP_CODE"));
                }
                if (rs.getString("CREATE_DATE") != null) {
                    request.set("CREATE_DATE", rs.getDate("CREATE_DATE"));
                }
                if (rs.getString("LAST_MODIFIED") != null) {
                    request.set("LAST_MODIFIED", rs.getDate("LAST_MODIFIED"));
                }
                if (rs.getString("LOGIN_FAILURE_COUNT") != null) {
                    request.set("LOGIN_FAILURE_COUNT", rs.getLong("LOGIN_FAILURE_COUNT"));
                }
                if (rs.getString("STATUS") != null) {
                    request.set("STATUS", rs.getLong("STATUS"));
                }
                if (rs.getString("ACCOUNT_TYPE") != null) {
                    request.set("ACCOUNT_TYPE", rs.getString("ACCOUNT_TYPE"));
                }
                if (rs.getString("PARENT_ID") != null) {
                    request.set("PARENT_ID", rs.getLong("PARENT_ID"));
                }
                if (rs.getString("TIN") != null) {
                    request.set("TIN", rs.getString("TIN"));
                }
                if (rs.getString("MPIN_EXPIRE_DATE") != null) {
                    request.set("MPIN_EXPIRE_DATE", rs.getDate("MPIN_EXPIRE_DATE"));
                }
                if (rs.getString("CENTRE_ID") != null) {
                    request.set("CENTRE_ID", rs.getLong("CENTRE_ID"));
                }
                if (rs.getString("ID_NO") != null) {
                    request.set("ID_NO", rs.getString("ID_NO"));
                }
                if (rs.getString("MPIN_STATUS") != null) {
                    request.set("MPIN_STATUS", rs.getString("MPIN_STATUS"));
                }
                if (rs.getString("SEX") != null) {
                    request.set("SEX", rs.getString("SEX"));
                }
                if (rs.getString("ISSUE_DATE") != null) {
                    request.set("ISSUE_DATE", rs.getDate("ISSUE_DATE"));
                }
                if (rs.getString("STAFF_CODE") != null) {
                    request.set("STAFF_CODE", rs.getString("STAFF_CODE"));
                }

                if (rs.getString("PROVINCE") != null) {
                    request.set("PROVINCE", rs.getString("PROVINCE"));
                }
                if (rs.getString("ISSUE_PLACE") != null) {
                    request.set("ISSUE_PLACE", rs.getString("ISSUE_PLACE"));
                }
                if (rs.getString("FAX") != null) {
                    request.set("FAX", rs.getString("FAX"));
                }
                if (rs.getString("DISTRICT") != null) {
                    request.set("DISTRICT", rs.getString("DISTRICT"));
                }
                if (rs.getString("PRECINCT") != null) {
                    request.set("PRECINCT", rs.getString("PRECINCT"));
                }
                if (rs.getString("NUM_ADD_MONEY") != null) {
                    request.set("NUM_ADD_MONEY", rs.getLong("NUM_ADD_MONEY"));
                }
            }
            SqlCommon.closeResultSet(rs);
            SqlCommon.closeStatement(sStatement);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public ViettelService FindAccountCus(Long AgentId) {
        ResultSet rs = null;

        try {
            connection = getConnectionCus();
            connection.setAutoCommit(false);
            String SQLSearch = "select * from CUS_OWNER.Agent where agent_id=?";
            sStatement = connection.prepareStatement(SQLSearch);
            sStatement.setLong(1, AgentId);
            rs = sStatement.executeQuery();
            while (rs.next()) {
                if (rs.getString("AGENT_ID") != null) {
                    request.set("STAFF_STK_ID", rs.getLong("AGENT_ID"));
                }
                if (rs.getString("MSISDN") != null) {
                    request.set("MSISDN", rs.getString("MSISDN"));
                }
                if (rs.getString("ICCID") != null) {
                    request.set("ICCID", rs.getString("ICCID"));
                }
                if (rs.getString("TRADE_NAME") != null) {
                    request.set("TRADE_NAME", rs.getString("TRADE_NAME"));
                }
                if (rs.getString("OWNER_NAME") != null) {
                    request.set("OWNER_NAME", rs.getString("OWNER_NAME"));
                }
                if (rs.getString("BIRTH_DATE") != null) {
                    request.set("BIRTH_DATE", rs.getDate("BIRTH_DATE"));
                }
                if (rs.getString("CONTACT_NO") != null) {
                    request.set("CONTACT_NO", rs.getString("CONTACT_NO"));
                }
                if (rs.getString("OUTLET_ADDRESS") != null) {
                    request.set("OUTLET_ADDRESS", rs.getString("OUTLET_ADDRESS"));
                }
                if (rs.getString("EMAIL") != null) {
                    request.set("EMAIL", rs.getString("EMAIL"));
                }
                if (rs.getString("SECURE_QUESTION") != null) {
                    request.set("SECURE_QUESTION", rs.getString("SECURE_QUESTION"));
                }
                if (rs.getString("MPIN") != null) {
                    request.set("MPIN", rs.getString("MPIN"));
                }
                if (rs.getString("SAP_CODE") != null) {
                    request.set("SAP_CODE", rs.getString("SAP_CODE"));
                }
                if (rs.getString("CREATE_DATE") != null) {
                    request.set("CREATE_DATE", rs.getDate("CREATE_DATE"));
                }
                if (rs.getString("LAST_MODIFIED") != null) {
                    request.set("LAST_MODIFIED", rs.getDate("LAST_MODIFIED"));
                }
                if (rs.getString("LOGIN_FAILURE_COUNT") != null) {
                    request.set("LOGIN_FAILURE_COUNT", rs.getLong("LOGIN_FAILURE_COUNT"));
                }
                if (rs.getString("STATUS") != null) {
                    request.set("STATUS", rs.getLong("STATUS"));
                }
                if (rs.getString("ACCOUNT_TYPE") != null) {
                    request.set("ACCOUNT_TYPE", rs.getString("ACCOUNT_TYPE"));
                }
                if (rs.getString("PARENT_ID") != null) {
                    request.set("PARENT_ID", rs.getLong("PARENT_ID"));
                }
                if (rs.getString("TIN") != null) {
                    request.set("TIN", rs.getString("TIN"));
                }
                if (rs.getString("MPIN_EXPIRE_DATE") != null) {
                    request.set("MPIN_EXPIRE_DATE", rs.getDate("MPIN_EXPIRE_DATE"));
                }
                if (rs.getString("CENTRE_ID") != null) {
                    request.set("CENTRE_ID", rs.getLong("CENTRE_ID"));
                }
                if (rs.getString("ID_NO") != null) {
                    request.set("ID_NO", rs.getString("ID_NO"));
                }
                if (rs.getString("MPIN_STATUS") != null) {
                    request.set("MPIN_STATUS", rs.getString("MPIN_STATUS"));
                }
                if (rs.getString("SEX") != null) {
                    request.set("SEX", rs.getString("SEX"));
                }
                if (rs.getString("ISSUE_DATE") != null) {
                    request.set("ISSUE_DATE", rs.getDate("ISSUE_DATE"));
                }
                if (rs.getString("STAFF_CODE") != null) {
                    request.set("STAFF_CODE", rs.getString("STAFF_CODE"));
                }

                if (rs.getString("PROVINCE") != null) {
                    request.set("PROVINCE", rs.getString("PROVINCE"));
                }
                if (rs.getString("ISSUE_PLACE") != null) {
                    request.set("ISSUE_PLACE", rs.getString("ISSUE_PLACE"));
                }
                if (rs.getString("FAX") != null) {
                    request.set("FAX", rs.getString("FAX"));
                }
                if (rs.getString("DISTRICT") != null) {
                    request.set("DISTRICT", rs.getString("DISTRICT"));
                }
                if (rs.getString("PRECINCT") != null) {
                    request.set("PRECINCT", rs.getString("PRECINCT"));
                }
                if (rs.getString("NUM_ADD_MONEY") != null) {
                    request.set("NUM_ADD_MONEY", rs.getLong("NUM_ADD_MONEY"));
                }
            }
            SqlCommon.closeResultSet(rs);
            SqlCommon.closeStatement(sStatement);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String FindAccountAnypayFPT(Long AgentID) {
        ResultSet rs = null;
        String output = "";
        try {
            connection = getConnectionEvoucher();
            connection.setAutoCommit(false);
            String schemaEvoucher = ResourceBundleUtils.getResource("schemaEvoucher");
            String SQLSearch = "select " + schemaEvoucher + ".agent.status as status," + schemaEvoucher + ".agent_account.curr_balance as curr_balance"
                    + " from " + schemaEvoucher + ".agent_account," + schemaEvoucher + ".agent where ";
            SQLSearch += schemaEvoucher + ".agent_account.agent_id = " + schemaEvoucher + ".agent.agent_id ";
            SQLSearch += "and " + schemaEvoucher + ".agent_account.agent_id=?";
            sStatement = connection.prepareStatement(SQLSearch);
            sStatement.setLong(1, AgentID);
            rs = sStatement.executeQuery();
            while (rs.next()) {
                if (rs.getString("curr_balance") != null) {
                    Long curr_balance = rs.getLong("curr_balance");
                    if (curr_balance != null) {
                        output = curr_balance.toString() + ".";
                    } else {
                        output = "0.";
                    }

                }
                if (rs.getString("status") != null) {
                    Long status = rs.getLong("status");
                    if (status != null) {
                        output += status.toString();
                    } else {
                        output += "-1";
                    }
                }
            }
            SqlCommon.closeResultSet(rs);
            SqlCommon.closeStatement(sStatement);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * author BinhTD
     * date: 09:17 18/01/2010
     * Doc file Config
     * @param:
     * @return : lay cac tham so
     *  - null: Exception
     */
    public void getSomeProperty() throws IOException {
        DbInfo info = ResourceBundleUtils.getDbInfoEncrypt("connectUrlDDV");
        urlCTV = info.getConnStr();
        userName = info.getUserName();
        passWord = info.getPassWord();

        DbInfo infoEv = ResourceBundleUtils.getDbInfoEncrypt("connectUrlEv");
        urlCTVEv = infoEv.getConnStr();
        userNameEv = infoEv.getUserName();
        passWordEv = infoEv.getPassWord();

        DbInfo infoCus = ResourceBundleUtils.getDbInfoEncrypt("connectUrlCMPre");
        urlCTVCus = infoCus.getConnStr();
        userNameCus = infoCus.getUserName();
        passWordCus = infoCus.getPassWord();        
    }

    /**
     * author BinhTD
     * date: 09:17 18/01/2010
     * tao connection
     * @param:
     * @return : Connection
     *  - null: Exception
     */
    public Connection getConnection() {
        try {
            getSomeProperty();
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connect = DriverManager.getConnection(urlCTV, userName, passWord);
            System.out.println("connected");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Connection getConnectionEvoucher() {
        try {
            getSomeProperty();
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connect = DriverManager.getConnection(urlCTVEv, userNameEv, passWordEv);
            System.out.println("connected");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Connection getConnectionCus() {
        try {
            getSomeProperty();
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connect = DriverManager.getConnection(urlCTVCus, userNameCus, passWordCus);
            System.out.println("connected");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *Ham cap nhat thong so ICCID.<br>
     * <pre>
     *  Chi update cho nhung SDN dang o trang thai huy.<br>
     * </pre>
     *
     * @param iccid: Thong so iccid
     * @param agentId: Ma Cong tac vien ban hang.
     * @since 23/01/2010
     * @author: TTPM - BSS - Duong Kim Anh.
     * @return 1. Thanh cong
     *         0. That bai
     */
    public String UpdateIccid(String iccid, Long agentId) {
        ResultSet rsAgent = null;
        ResultSet rsIccid = null;
        String UPDATE_SUCCESS = "1.Cap nhat iccid thanh cong.";
        String UPDATE_NOT_SUCCESS = "0.Cap nhat iccid that bai.";
        String UPDATE_ERROR = "0.Co loi say ra trong qua trinh cap nhat iccid";

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            //Tim kiem xem co Agent nao ung voi agentId truyen vao hay khong.
            String sqlSearchAgent = "SELECT * FROM staff_stk WHERE staff_stk_id = ?";
            sStatement = connection.prepareStatement(sqlSearchAgent);

            if (agentId != null) {
                sStatement.setLong(1, agentId);
            } else {
                sStatement.setNull(1, java.sql.Types.NUMERIC);
            }

            rsAgent = sStatement.executeQuery();

            //Neu khong co Agent nao ung voi agentId dua vao thi ra ve FALSE
            if (!rsAgent.next()) {
                return UPDATE_NOT_SUCCESS;

                //Neu tim thay Agent ung voi agentId dua vao.
            } else {

                //Tim xem ICCID nay co dang duoc su dung hay khong.
                String sqlSearchIccid = "SELECT * FROM staff_stk WHERE iccid = ? AND status = 1";

                sStatement = connection.prepareStatement(sqlSearchIccid);

                if (iccid != null) {
                    sStatement.setString(1, iccid);
                } else {
                    sStatement.setNull(1, java.sql.Types.VARCHAR);
                }

                rsIccid = sStatement.executeQuery();

                //Neu ICCID nay da co nguoi su dung (con dang trong trang thai hoat dong)
                //Thi dua ra thong bao: FALSE
                if (rsIccid.next()) {
                    return UPDATE_NOT_SUCCESS;

                    //Neu hien tai chua co nguoi su dung hoac dang o trang thai huy thi tien hanh update.
                } else {

                    //UPDATE lai iccid cho Agent neu thoa cac dieu kien.
                    String sqlUpdateIccid = "UPDATE staff_stk SET iccid = ? WHERE staff_stk_id = ?";
                    sStatement = connection.prepareStatement(sqlUpdateIccid);

                    if (iccid != null) {
                        sStatement.setString(1, iccid);
                    } else {
                        sStatement.setNull(1, java.sql.Types.VARCHAR);
                    }

                    if (agentId != null) {
                        sStatement.setLong(2, agentId);
                    } else {
                        sStatement.setNull(2, java.sql.Types.NUMERIC);
                    }

                    int rowCount = sStatement.executeUpdate();
                    boolean isUpdate = rowCount > 0 ? true : false;
                    if (isUpdate) {
                        SqlCommon.commitOnly(connection);
                        return UPDATE_SUCCESS;
                    } else {
                        return UPDATE_NOT_SUCCESS;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception: " + e.getMessage());
            SqlCommon.rollback(connection);
            return UPDATE_ERROR;
        } finally {
            SqlCommon.closeResultSet(rsAgent);
            SqlCommon.closeResultSet(rsIccid);
            SqlCommon.closeStatement(sStatement);
        }
    }
}
