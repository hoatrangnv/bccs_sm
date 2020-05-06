/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

/**
 *
 * @author Vunt
 */
import com.viettel.bccs.cm.api.InterfaceCm;
import com.viettel.common.ViettelService;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.AnyPayTransFPTBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.AccountAnyPayFPTForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DataUtils;
import com.viettel.im.database.BO.UserToken;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import oracle.jdbc.*;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockIsdnMobile;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Locale;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.bean.ShowLogAccountFPTBean;
import com.viettel.im.database.BO.MethodCallLog;
import com.viettel.im.client.bean.DbInfo;

public class AccountAnyPayFPTManagementDAO extends BaseDAOAction {

    CallableStatement Execute;
    CallableStatement ExecuteCus;
    String schemaEvoucher = "IM_EVOUCHER";
    String schemaCus = "COM_OWNER";
    //String strIPEvoucher = "192.168.176.12";
    //String strSIDEvoucher = "AnyPay01";
    //String portNumberEvoucher = "1521";
    String strUserNameEvoucher = "anypay_bccs";
    String strPassWordEvoucher = "ace";
    String connectUrlEv = "jdbc:oracle:thin:@192.168.176.12:1521:AnyPay01";
    //String strIPCus = "192.168.176.12";
    //String strSIDCus = "AnyPay01";
    //String portNumberCus = "1521";
    String strUserNameCus = "anypay_bccs";
    String strPassWordCus = "ace";
    String connectUrlCus = "jdbc:oracle:thin:@10.58.3.15:1522:gsmbill";
    String className = "com.viettel.im.database.DAO.AccountAnyPayFPTManagementDAO";
    String userCreateCus = "IM_CUS";
    String userCreateEvoucher = "IM_EVOUCHER";
    String userCreateCTVDDV = "IM_CTVDDV";
    private static final Log log = LogFactory.getLog(AccountAnyPayFPTManagementDAO.class);
    private AccountAnyPayFPTForm accountAnyPayFPTForm = new AccountAnyPayFPTForm();

    public AccountAnyPayFPTForm getAccountAnyPayFPTForm() {
        return accountAnyPayFPTForm;
    }

    public void setAccountAnyPayFPTForm(AccountAnyPayFPTForm accountAnyPayFPTForm) {
        this.accountAnyPayFPTForm = accountAnyPayFPTForm;
    }

    private void loadParam() {
        try {
            DbInfo info = ResourceBundleUtils.getDbInfoEncrypt("connectUrlEv");
            strUserNameEvoucher = info.getUserName();
            strPassWordEvoucher = info.getPassWord();
            connectUrlEv = info.getConnStr();
            schemaEvoucher = ResourceBundleUtils.getResource("schemaEvoucher");

            DbInfo infoCus = ResourceBundleUtils.getDbInfoEncrypt("connectUrlCMPre");
            strUserNameCus = infoCus.getUserName();
            strPassWordCus = infoCus.getPassWord();
            connectUrlCus = infoCus.getConnStr();

            schemaCus = ResourceBundleUtils.getResource("schemaCus");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public AccountAnyPayFPTManagementDAO() {
        loadParam();
    }

    public String preparePage() throws Exception {
        String pageForward;
        log.debug("# Begin method ReportChangeGood.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        loadParam();
        pageForward = "activeAccountAnyPayFPT";
        //String DATE_FORMAT = "yyyy-MM-dd";
        //SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        //Calendar cal = Calendar.getInstance();
        //accountAnyPayFPTForm.setToDate(dateFomat.format(cal.getTime()));
        //cal.add(Calendar.MONTH, -1); // substract 1 month
        req.getSession().setAttribute("showInfoActive", "true");
        req.getSession().removeAttribute("showButton");
        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    public String preparePageEditAccountAnyPay() throws Exception {
        String pageForward;
        log.debug("# Begin method ReportChangeGood.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        loadParam();
        pageForward = "editAccountAnyPayFPT";
        //String DATE_FORMAT = "yyyy-MM-dd";
        //SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        //Calendar cal = Calendar.getInstance();
        //accountAnyPayFPTForm.setToDate(dateFomat.format(cal.getTime()));
        //cal.add(Calendar.MONTH, -1); // substract 1 month
        req.getSession().setAttribute("showEdit", -1);
        req.getSession().setAttribute("changePassword", "true");
        req.getSession().setAttribute("changeStatus", "true");
        req.getSession().setAttribute("changeInfo", "true");
        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    public String findAccountAnyPay() throws Exception {
        String pageForward;
        log.debug("# Begin method ReportChangeGood.preparePage");
        pageForward = "activeAccountAnyPayFPT";
        HttpServletRequest req = getRequest();
        ViettelService request = new ViettelService();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String isdn = accountAnyPayFPTForm.getIsdnSearch();
        String sapCode = accountAnyPayFPTForm.getAccountCodeSearch();
        //Lay thong tin cua CTV tu CM
        //Lay subType 1 - tra truoc 2 - tra sau
        Byte subType = 1;
        String sql = "From StockIsdnMobile where to_number(isdn) = ?";
        BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(isdn));
        Query query = getSession().createQuery(sql);
        query.setParameter(0, isdnSearch);
        List<StockIsdnMobile> list = query.list();
        if (list != null && list.size() > 0) {
            subType = Byte.valueOf(list.get(0).getIsdnType());
        }
        InterfaceCm inter = new InterfaceCm();
        Object subInfo = inter.getSubscriberInfoV2(isdn, "M", subType);
        if (subInfo == null) {
            req.setAttribute("returnMsg", "Không tìm thấy thông tin của thuê bao");
            return pageForward;
        }
        if (subType == 1) {
            com.viettel.bccs.cm.database.BO.pre.SubMb subMb = (com.viettel.bccs.cm.database.BO.pre.SubMb) subInfo;
            accountAnyPayFPTForm.setIccid(subMb.getSerial());
            accountAnyPayFPTForm.setSerial(subMb.getSerial());
        } else {
            com.viettel.bccs.cm.database.BO.SubMb subMb = (com.viettel.bccs.cm.database.BO.SubMb) subInfo;
            accountAnyPayFPTForm.setIccid(subMb.getSerial());
            accountAnyPayFPTForm.setSerial(subMb.getSerial());
        }

        UpdateForSales updateForSales = new UpdateForSales();
        request = updateForSales.FindAccountAnypay("0" + isdn, "");
        if (request != null) {
            Object iCCID = request.get("ICCID");
            if (iCCID != null) {
                req.setAttribute("returnMsg", "Đã tồn tại tài khoản nên không thể kích hoạt mới");
                return pageForward;
            }
        }
        String stockModelCode = "";
        if (accountAnyPayFPTForm.getAccountTypeSearch().equals(1L)) {
            stockModelCode = getStockKit(accountAnyPayFPTForm.getSerial(), getShopId(accountAnyPayFPTForm.getShopCodeSearch()), Constant.OWNER_TYPE_SHOP);
        } else {
            stockModelCode = getStockKit(accountAnyPayFPTForm.getSerial(), getStaffId(accountAnyPayFPTForm.getStaffCodeSearch()), Constant.OWNER_TYPE_STAFF);
        }

        if (stockModelCode.equals("")) {
            req.setAttribute("returnMsg", "Số này chưa đấu kit");
            return pageForward;
        } else {
            if (!stockModelCode.equals("KITCTV")) {
                req.setAttribute("returnMsg", "Số này không phải đấu kit CTV");
                return pageForward;
            }
        }


        //setInfoToForm(request);
        //day thong tin len form
        accountAnyPayFPTForm.setShopCode(accountAnyPayFPTForm.getShopCodeSearch());
        accountAnyPayFPTForm.setShopName(getShopName(accountAnyPayFPTForm.getShopCode()));
        accountAnyPayFPTForm.setStaffCode(accountAnyPayFPTForm.getStaffCodeSearch());
        accountAnyPayFPTForm.setStaffName(accountAnyPayFPTForm.getStaffCode());
        accountAnyPayFPTForm.setAccountType(accountAnyPayFPTForm.getAccountTypeSearch());
        accountAnyPayFPTForm.setAccountName(getNameAccountType(accountAnyPayFPTForm.getAccountType()));
        accountAnyPayFPTForm.setIsdn(accountAnyPayFPTForm.getIsdnSearch());
        if (accountAnyPayFPTForm.getAccountType().equals(1L)) {
            accountAnyPayFPTForm.setAgent_id(getAgentId(getShopId(accountAnyPayFPTForm.getShopCode()), Constant.OWNER_TYPE_SHOP, 0L));
        } else {
            accountAnyPayFPTForm.setAgent_id(getAgentId(getStaffId(accountAnyPayFPTForm.getStaffCode()), Constant.OWNER_TYPE_STAFF, 0L));
        }
        accountAnyPayFPTForm.setAccountType(accountAnyPayFPTForm.getAccountTypeSearch());
        accountAnyPayFPTForm.setAccountName(getNameAccountType(accountAnyPayFPTForm.getAccountType()));
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        accountAnyPayFPTForm.setDateCreate(dateFomat.format(cal.getTime()));
        accountAnyPayFPTForm.setBirthDate(dateFomat.format(cal.getTime()));
        accountAnyPayFPTForm.setMakeDate(dateFomat.format(cal.getTime()));
        accountAnyPayFPTForm.setDatePassword(dateFomat.format(cal.getTime()));
        req.getSession().setAttribute("showInfoActive", "false");
        req.getSession().setAttribute("showButton", 1);
        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    //Tim kiem thong tin tai khoan de cap nhat
    public String findAccountAnyPayEdit() throws Exception {
        String pageForward;
        log.debug("# Begin method ReportChangeGood.preparePage");
        pageForward = "editAccountAnyPayFPT";
        HttpServletRequest req = getRequest();
        ViettelService request = new ViettelService();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String isdn;
        if (accountAnyPayFPTForm.getIsdnSearch().charAt(0) == '0') {
            isdn = accountAnyPayFPTForm.getIsdnSearch().trim();
        } else {
            isdn = "0" + accountAnyPayFPTForm.getIsdnSearch().trim();
        }
        String sapCode = accountAnyPayFPTForm.getAccountCodeSearch();
        UpdateForSales updateForSales = new UpdateForSales();
        request = updateForSales.FindAccountAnypay(isdn, sapCode);
        if (request != null) {
            Object iCCID = request.get("ICCID");
            if (iCCID == null) {
                req.setAttribute("returnMsg", "Không tìm được tài khoản thỏa mãn điều kiện");
                return pageForward;
            }
        } else {
            req.setAttribute("returnMsg", "Không kết nối được với dữ liệu của CTVDDV");
            return pageForward;
        }
        //day du lieu len form
        setInfoToForm(request);
        req.getSession().setAttribute("showEdit", 0);
        req.getSession().setAttribute("showInfoActive", "false");
        req.getSession().setAttribute("showButton", 1);
        req.getSession().setAttribute("changePassword", "true");
        req.getSession().setAttribute("changeStatus", "true");
        req.getSession().setAttribute("changeInfo", "true");
        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    public String activceAnyPay() throws Exception {
        String pageForward;
        pageForward = "activeAccountAnyPayFPT";
        log.debug("# Begin method ReportChangeGood.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Date dateCreate = DateTimeUtils.getSysDate();
        Date birthDate = DateTimeUtils.convertStringToDate(accountAnyPayFPTForm.getBirthDate());
        Date datePassword = DateTimeUtils.convertStringToDate(accountAnyPayFPTForm.getDatePassword());
        Date makeDate = DateTimeUtils.convertStringToDate(accountAnyPayFPTForm.getMakeDate());
        //Goi pakage sang evoucher neu loai tai khoan là quản lý CTVDVV thi bo qua -- AGENT_PKG.create_agent
        Connection connection = null;
        Connection connectionCus = null;
        String strErrorCode = null;
        try {
            if (!accountAnyPayFPTForm.getAccountType().equals(4L)) {
//                String url = "jdbc:oracle:thin:@" + strIPEvoucher + ":" + portNumberEvoucher + ":" + strSIDEvoucher;
                String url = connectUrlEv;
                connection = DriverManager.getConnection(url, strUserNameEvoucher, strPassWordEvoucher);
                String strSQL = "{call " + schemaEvoucher + ".AGENT_PKG.create_agent(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                Execute = connection.prepareCall(strSQL);
                Execute.setLong(1, accountAnyPayFPTForm.getAgent_id());
                if (accountAnyPayFPTForm.getIsdn().charAt(0) == '0') {
                    Execute.setString(2, accountAnyPayFPTForm.getIsdn());
                } else {
                    Execute.setString(2, "0" + accountAnyPayFPTForm.getIsdn());
                }
                Execute.setString(3, accountAnyPayFPTForm.getSerial());
                Execute.setString(4, accountAnyPayFPTForm.getNamerepresentative());
                Execute.setString(5, accountAnyPayFPTForm.getNameAccount());
                Execute.setDate(6, new java.sql.Date(birthDate.getTime()));
                Execute.setString(7, "");
                Execute.setString(8, accountAnyPayFPTForm.getAddress());
                Execute.setString(9, accountAnyPayFPTForm.getEmail());
                Execute.setString(10, accountAnyPayFPTForm.getSecretQuestion());
                Execute.setString(11, accountAnyPayFPTForm.getShopCode());
                Execute.setDate(12, new java.sql.Date(dateCreate.getTime()));
                Execute.setDate(13, new java.sql.Date(dateCreate.getTime()));
                Execute.setLong(14, 3);
                Execute.setDate(15, new java.sql.Date(datePassword.getTime()));
                Execute.setLong(16, 0L);//parent_id
                Execute.setLong(17, 1L);//satus
                Execute.setString(18, accountAnyPayFPTForm.getTin());
                Execute.setLong(19, 0L);//centrenrId
                Execute.setString(20, encryptionCode(accountAnyPayFPTForm.getPassword()));
                Execute.setString(21, accountAnyPayFPTForm.getIdNo());
                Execute.setString(22, accountAnyPayFPTForm.getStaffCode());
                Execute.setDate(23, new java.sql.Date(makeDate.getTime()));
                Execute.setString(24, accountAnyPayFPTForm.getMakeAddress());
                Execute.setString(25, accountAnyPayFPTForm.getFax());
                Execute.setString(26, accountAnyPayFPTForm.getAccountType().toString());
                Execute.setString(27, accountAnyPayFPTForm.getProvinceCode());
                Execute.setString(28, accountAnyPayFPTForm.getDistrictCode());
                Execute.setString(29, accountAnyPayFPTForm.getWardCode());
                Execute.setString(30, userToken.getLoginName());
                Execute.setString(31, accountAnyPayFPTForm.getPhoneNumber());
                Execute.registerOutParameter(32, OracleTypes.CHAR);
                Execute.registerOutParameter(33, OracleTypes.CHAR);
                Execute.execute();
                strErrorCode = Execute.getString(32);
                String strError = Execute.getString(33);
                saveMethodCallLog(className, "create_agent", "", userCreateEvoucher, strErrorCode);
                if (strErrorCode != null) {
                    connection.rollback();
                }
            }

            String error = null;
            if (strErrorCode == null) {
                //Goi pakage sang cus -- acc_mng.create_agent
//                String urlCus = "jdbc:oracle:thin:@" + strIPCus + ":" + portNumberCus + ":" + strSIDCus;
                String urlCus = connectUrlCus;
                connectionCus = DriverManager.getConnection(urlCus, strUserNameCus, strPassWordCus);
                String strSQLCus = "{call " + schemaCus + ".acc_mng.create_agent(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                ExecuteCus = connectionCus.prepareCall(strSQLCus);
                ExecuteCus.setLong(1, getStaffId(accountAnyPayFPTForm.getStaffCode()));
                ExecuteCus.setLong(2, accountAnyPayFPTForm.getAgent_id());
                if (accountAnyPayFPTForm.getIsdn().charAt(0) == '0') {
                    ExecuteCus.setString(3, accountAnyPayFPTForm.getIsdn());
                } else {
                    ExecuteCus.setString(3, "0" + accountAnyPayFPTForm.getIsdn());
                }
                ExecuteCus.setString(4, accountAnyPayFPTForm.getSerial());
                ExecuteCus.setString(5, accountAnyPayFPTForm.getNameAccount());
                ExecuteCus.setDate(6, new java.sql.Date(birthDate.getTime()));
                ExecuteCus.setString(7, accountAnyPayFPTForm.getPhoneNumber());
                ExecuteCus.setString(8, accountAnyPayFPTForm.getAddress());
                ExecuteCus.setString(9, encryptionCode(accountAnyPayFPTForm.getPassword()));
                ExecuteCus.setDate(10, new java.sql.Date(dateCreate.getTime()));
                ExecuteCus.setDate(11, new java.sql.Date(dateCreate.getTime()));
                ExecuteCus.setDate(12, new java.sql.Date(datePassword.getTime()));
                ExecuteCus.setLong(13, 1L);//status
                ExecuteCus.setString(14, accountAnyPayFPTForm.getAccountType().toString());
                ExecuteCus.setString(15, accountAnyPayFPTForm.getTin());
                ExecuteCus.setString(16, accountAnyPayFPTForm.getIdNo());
                ExecuteCus.setDate(17, new java.sql.Date(makeDate.getTime()));
                ExecuteCus.setString(18, accountAnyPayFPTForm.getStaffCode());
                ExecuteCus.setString(19, accountAnyPayFPTForm.getProvinceCode());
                ExecuteCus.setString(20, accountAnyPayFPTForm.getDistrictCode());
                ExecuteCus.setString(21, accountAnyPayFPTForm.getWardCode());
                ExecuteCus.registerOutParameter(22, OracleTypes.CHAR);
                ExecuteCus.execute();
                error = ExecuteCus.getString(22);
                saveMethodCallLog(className, "create_agent", "", userCreateCus, error);
                if (error != null) {
                    connectionCus.rollback();
                }
            }

            if (error == null && strErrorCode == null) {
                //Goi pakage sang CTVDDV -- ben DDV cung cap ham
                ViettelService request = new ViettelService();
                request = getInfo();
                UpdateForSales updateForSales = new UpdateForSales();
                String errorDDV = updateForSales.ActiveAccount(request);
                saveMethodCallLog(className, "create_agent", "", userCreateCTVDDV, errorDDV);
                if (errorDDV.charAt(0) == '0') {
                    connectionCus.rollback();
                    connection.rollback();
                    connectionCus.close();
                    connection.close();
                    req.setAttribute("returnMsg", "Kích hoạt tài khoản không thành công");
                    return pageForward;
                } else {
                    connectionCus.commit();
                    connection.commit();
                    req.setAttribute("returnMsg", "Kích hoạt tài khoản thành công");
                }
            } else {
                req.setAttribute("returnMsg", "Không kích hoạt được tài khoản bên FPT");
                if (connectionCus != null) {
                    connectionCus.rollback();
                    connectionCus.close();
                }
                if (connection != null) {
                    connection.rollback();
                    connection.close();
                }

                return pageForward;

            }

        } catch (Exception ex) {
            if (connectionCus != null) {
                connectionCus.rollback();
            }
            if (connection != null) {
                connection.rollback();
            }
            ex.printStackTrace();
            throw ex;

        } finally {
            if (Execute != null) {
                Execute.close();
            }
            if (ExecuteCus != null) {
                ExecuteCus.close();
            }
            if (connectionCus != null) {
                connectionCus.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        req.getSession().setAttribute("showInfoActive", "true");
        req.getSession().removeAttribute("showButton");
        resetForm();
        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    public String changeStatus() throws Exception {
        String pageForward;
        log.debug("# Begin method ReportChangeGood.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "editAccountAnyPayFPT";
        accountAnyPayFPTForm.setStatus(accountAnyPayFPTForm.getStatusAcc());
        req.getSession().setAttribute("showEdit", 1);
        req.getSession().setAttribute("changeStatus", "false");
        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    public String changePassword() throws Exception {
        String pageForward;
        log.debug("# Begin method ReportChangeGood.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "editAccountAnyPayFPT";
        req.getSession().setAttribute("showEdit", 2);
        req.getSession().setAttribute("changePassword", "false");
        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    public String changInfomation() throws Exception {
        String pageForward;
        log.debug("# Begin method ReportChangeGood.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "editAccountAnyPayFPT";
        req.getSession().setAttribute("showEdit", 3);
        req.getSession().setAttribute("changeInfo", "false");
        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    public String repairSim() throws Exception {
        String pageForward;
        log.debug("# Begin method ReportChangeGood.preparePage");
        pageForward = "editAccountAnyPayFPT";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        accountAnyPayFPTForm.setSerial(accountAnyPayFPTForm.getSaveSerialOld());
        //Lay thong tin cua CTV tu CM
        //Lay subType 1 - tra truoc 2 - tra sau
        Byte subType = 1;
        String sql = "From StockIsdnMobile where to_number(isdn) = ?";
        BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(accountAnyPayFPTForm.getIsdn()));
        Query query = getSession().createQuery(sql);
        query.setParameter(0, isdnSearch);
        List<StockIsdnMobile> list = query.list();
        if (list != null && list.size() > 0) {
            subType = Byte.valueOf(list.get(0).getIsdnType());
        }
        InterfaceCm inter = new InterfaceCm();
        Object subInfo = inter.getSubscriberInfoV2(isdnSearch.toString(), "M", subType);
        if (subInfo == null) {
            req.setAttribute("returnMsg", "Không tìm thấy thông tin của thuê bao");
            return pageForward;
        }
        String serialNew = "";
        if (subType == 1) {
            com.viettel.bccs.cm.database.BO.pre.SubMb subMb = (com.viettel.bccs.cm.database.BO.pre.SubMb) subInfo;
            serialNew = subMb.getSerial();
            if (accountAnyPayFPTForm.getSerial().equals(subMb.getSerial())) {
                req.setAttribute("returnMsg", "Chưa thực hiện đổi sim nên không gắp sim được");
                return pageForward;
            }
        } else {
            com.viettel.bccs.cm.database.BO.SubMb subMb = (com.viettel.bccs.cm.database.BO.SubMb) subInfo;
            serialNew = subMb.getSerial();
            if (accountAnyPayFPTForm.getSerial().equals(subMb.getSerial())) {
                req.setAttribute("returnMsg", "Chưa thực hiện đổi sim nên không gắp sim được");
                return pageForward;
            }
        }
        accountAnyPayFPTForm.setSerialNew(serialNew);
        req.getSession().setAttribute("showEdit", 4);
        //req.getSession().setAttribute("changeInfo", "false");

        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    public String cancel() throws Exception {
        String pageForward;
        log.debug("# Begin method ReportChangeGood.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "editAccountAnyPayFPT";
        req.getSession().setAttribute("changePassword", "true");
        req.getSession().setAttribute("changeStatus", "true");
        req.getSession().setAttribute("changeInfo", "true");
        req.getSession().setAttribute("showEdit", 0);
        log.debug("# End method ReportChangeGood.preparePage");
        accountAnyPayFPTForm.setPassword(accountAnyPayFPTForm.getPassAcc());
        accountAnyPayFPTForm.setRePassword(accountAnyPayFPTForm.getPassAcc());
        accountAnyPayFPTForm.setStatus(accountAnyPayFPTForm.getStatusAcc());
        String showEdit = req.getSession().getAttribute("showEdit").toString();
        //if (showEdit.equals("3")) {
        //}
        UpdateForSales updateForSales = new UpdateForSales();
        ViettelService request = updateForSales.FindAccountAnypay(accountAnyPayFPTForm.getIsdn(), null);
        setInfoToForm(request);
        return pageForward;
    }

    public String save() throws Exception {
        String pageForward;
        log.debug("# Begin method ReportChangeGood.preparePage");
        HttpServletRequest req = getRequest();
        pageForward = "editAccountAnyPayFPT";
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Date sysDate = DateTimeUtils.getSysDate();
        Date dateCreate = DateTimeUtils.convertStringToDate(accountAnyPayFPTForm.getDateCreate());
        Date birthDate = DateTimeUtils.convertStringToDate(accountAnyPayFPTForm.getBirthDate());
        Date datePassword = DateTimeUtils.convertStringToDate(accountAnyPayFPTForm.getDatePassword());
        Date makeDate = DateTimeUtils.convertStringToDate(accountAnyPayFPTForm.getMakeDate());
        //Goi pakage sang evoucher neu loai tai khoan là quản lý CTVDVV thi bo qua -- AGENT_PKG.create_agent
        //Goi pakage sang cus -- acc_mng.create_agent
        //Goi pakage sang CTVDDV -- ben DDV cung cap ham
        String editType = req.getSession().getAttribute("showEdit").toString();
        //connect evoucher        
//        String url = "jdbc:oracle:thin:@" + strIPEvoucher + ":" + portNumberEvoucher + ":" + strSIDEvoucher;
        String url = connectUrlEv;
        Connection connection = null;
        connection = DriverManager.getConnection(url, strUserNameEvoucher, strPassWordEvoucher);
        //connect cus        
//        String urlCus = "jdbc:oracle:thin:@" + strIPCus + ":" + portNumberCus + ":" + strSIDCus;
        String urlCus = connectUrlCus;
        Connection connectionCus = null;
        connectionCus = DriverManager.getConnection(urlCus, strUserNameCus, strPassWordCus);
        String strErrorCode = "";
        if (editType.equals("1")) {
            //change status
            //evoucher
            if (!accountAnyPayFPTForm.getAccountType().equals(4L)) {
                String strSQL = "{call " + schemaEvoucher + ".AGENT_PKG.update_status_agent(?,?,?,?,?,?)}";
                Execute = connection.prepareCall(strSQL);
                Execute.setLong(1, accountAnyPayFPTForm.getAgent_id());
                Execute.setLong(2, accountAnyPayFPTForm.getStatus());//state  = status
                Execute.setLong(3, accountAnyPayFPTForm.getReasonId());
                Execute.setString(4, userToken.getLoginName());
                Execute.setString(5, req.getLocalAddr());//host
                Execute.registerOutParameter(6, OracleTypes.CHAR);
                Execute.execute();
                strErrorCode = Execute.getString(6);
                saveMethodCallLog(className, "update_status_agent", "", userCreateEvoucher, strErrorCode);
                if (strErrorCode != null) {
                    connection.rollback();
                }
            }
            //cho de them phan update sang cus

            //update sang DDV
            if (strErrorCode == null) {
                UpdateForSales updateForSales = new UpdateForSales();
                String errorDDV = updateForSales.UpdateAccountStatus(accountAnyPayFPTForm.getIsdn(), accountAnyPayFPTForm.getStatus(), accountAnyPayFPTForm.getReasonId().toString());
                saveMethodCallLog(className, "update_status_agent", "", userCreateCTVDDV, errorDDV);
                if (errorDDV.charAt(0) == '0') {
                    req.setAttribute("returnMsg", errorDDV.substring(2));
                    connection.rollback();
                    return pageForward;
                } else {
                    req.setAttribute("returnMsg", errorDDV.substring(2));
                    req.getSession().setAttribute("changePassword", "true");
                    req.getSession().setAttribute("changeStatus", "true");
                    req.getSession().setAttribute("changeInfo", "true");
                    req.getSession().setAttribute("showEdit", 0);
                    accountAnyPayFPTForm.setStatusAcc(accountAnyPayFPTForm.getStatus());
                    connection.commit();
                    connection.close();
                }
            } else {
                req.setAttribute("returnMsg", "Không cập nhật được thông tin bên Evoucher");
                connection.rollback();
                connection.close();
                return pageForward;
            }
        } else {
            if (editType.equals("2")) {
                //change pass chua thay package
                //evoucher
                String strSQL = "{call " + schemaEvoucher + ".AGENT_PKG.reset_password(?,?,?,?,?,?)}";
                Execute = connection.prepareCall(strSQL);
                Execute.setString(1, userToken.getLoginName());
                Execute.setString(2, req.getLocalAddr());//host
                Execute.setString(3, accountAnyPayFPTForm.getIsdn());
                Execute.setString(4, accountAnyPayFPTForm.getPassword());
                Execute.setString(5, encryptionCode(accountAnyPayFPTForm.getRePassword()));
                Execute.registerOutParameter(6, OracleTypes.CHAR);
                Execute.execute();
                strErrorCode = Execute.getString(6);
                if (strErrorCode.equals(Constant.ERROR_UPDATE_PASS)) {
                    strErrorCode = null;
                }
                saveMethodCallLog(className, "reset_password", "", userCreateEvoucher, strErrorCode);
                if (strErrorCode != null) {
                    connection.rollback();
                }
                //goi update mat khau sang cus

                if (strErrorCode == null) {
                    //update sang DDV
                    UpdateForSales updateForSales = new UpdateForSales();
                    String errorDDV = updateForSales.UpdateAccountPassword(accountAnyPayFPTForm.getIsdn(), encryptionCode(accountAnyPayFPTForm.getPassword()));
                    saveMethodCallLog(className, "reset_password", "", userCreateCTVDDV, errorDDV);
                    if (errorDDV.charAt(0) == '0') {
                        req.setAttribute("returnMsg", errorDDV.substring(2));
                        connection.rollback();
                        return pageForward;
                    } else {
                        req.setAttribute("returnMsg", errorDDV.substring(2));
                        req.getSession().setAttribute("changePassword", "true");
                        req.getSession().setAttribute("changeStatus", "true");
                        req.getSession().setAttribute("changeInfo", "true");
                        req.getSession().setAttribute("showEdit", 0);
                        accountAnyPayFPTForm.setPassAcc(encryptionCode(accountAnyPayFPTForm.getPassword()));
                        connection.commit();
                        connection.close();
                    }
                } else {
                    req.setAttribute("returnMsg", "Không cập nhật được thông tin bên Evoucher");
                    connection.rollback();
                    connection.close();
                    return pageForward;
                }

            } else {
                if (editType.equals("3")) {
                    //chang info
                    //evoucher                    
                    if (!accountAnyPayFPTForm.getAccountType().equals(4L)) {
                        String strSQL = "{call " + schemaEvoucher + ".AGENT_PKG.update_agent(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                        Execute = connection.prepareCall(strSQL);
                        Execute.setString(1, accountAnyPayFPTForm.getNameAccount());
                        Execute.setLong(2, accountAnyPayFPTForm.getStatusAcc());
                        Execute.setString(3, "");//delegate khong su dung
                        Execute.setString(4, accountAnyPayFPTForm.getAddress());
                        Execute.setDate(5, new java.sql.Date(sysDate.getTime()));//p_sn_tl_date
                        Execute.setString(6, "");//id_permit khong can
                        Execute.setDate(7, new java.sql.Date(birthDate.getTime()));
                        Execute.setString(8, accountAnyPayFPTForm.getMakeAddress());
                        Execute.setString(9, accountAnyPayFPTForm.getPhoneNumber());
                        if (accountAnyPayFPTForm.getFax() != null && !accountAnyPayFPTForm.getFax().equals("")) {
                            Execute.setLong(10, Long.parseLong(accountAnyPayFPTForm.getFax()));
                        } else {
                            Execute.setString(10, null);
                        }
                        Execute.setString(11, accountAnyPayFPTForm.getEmail());
                        Execute.setString(12, accountAnyPayFPTForm.getPassAcc());
                        Execute.setString(13, accountAnyPayFPTForm.getPassAcc());
                        Execute.setDate(14, new java.sql.Date(datePassword.getTime()));
                        Execute.setString(15, accountAnyPayFPTForm.getSecretQuestion());
                        Execute.setString(16, accountAnyPayFPTForm.getProvinceCode());
                        Execute.setString(17, accountAnyPayFPTForm.getDistrictCode());
                        Execute.setString(18, accountAnyPayFPTForm.getWardCode());
                        Execute.setLong(19, accountAnyPayFPTForm.getAgent_id());
                        Execute.setLong(20, 0L);//new_agent
                        Execute.setString(21, userToken.getLoginName());
                        Execute.setString(22, req.getLocalAddr());//host
                        Execute.registerOutParameter(23, OracleTypes.CHAR);
                        Execute.execute();
                        strErrorCode = Execute.getString(23);
                        saveMethodCallLog(className, "update_agent", "", userCreateEvoucher, strErrorCode);
                        if (strErrorCode != null) {
                            connection.rollback();
                        }
                    }

                    //cus
                    String strSQLCus = "{call " + schemaCus + ".acc_mng.update_agent(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                    ExecuteCus = connectionCus.prepareCall(strSQLCus);
                    ExecuteCus.setString(1, accountAnyPayFPTForm.getAccountName());
                    ExecuteCus.setLong(2, accountAnyPayFPTForm.getStatusAcc());
                    ExecuteCus.setString(3, "");//delegate khong su dung
                    ExecuteCus.setString(4, accountAnyPayFPTForm.getAddress());
                    ExecuteCus.setDate(5, new java.sql.Date(sysDate.getTime()));//p_sn_tl_date
                    ExecuteCus.setString(6, "");//id_permit khong can
                    ExecuteCus.setDate(7, new java.sql.Date(birthDate.getTime()));
                    ExecuteCus.setString(8, accountAnyPayFPTForm.getMakeAddress());
                    ExecuteCus.setString(9, accountAnyPayFPTForm.getPhoneNumber());
                    if (accountAnyPayFPTForm.getFax() != null && !accountAnyPayFPTForm.getFax().equals("")) {
                        ExecuteCus.setLong(10, Long.parseLong(accountAnyPayFPTForm.getFax()));
                    } else {
                        ExecuteCus.setString(10, null);
                    }
                    ExecuteCus.setString(11, accountAnyPayFPTForm.getEmail());
                    ExecuteCus.setString(12, accountAnyPayFPTForm.getPassAcc());
                    ExecuteCus.setString(13, accountAnyPayFPTForm.getPassAcc());
                    ExecuteCus.setDate(14, new java.sql.Date(datePassword.getTime()));
                    ExecuteCus.setString(15, accountAnyPayFPTForm.getSecretQuestion());
                    ExecuteCus.setString(16, accountAnyPayFPTForm.getProvinceCode());
                    ExecuteCus.setString(17, accountAnyPayFPTForm.getDistrictCode());
                    ExecuteCus.setString(18, accountAnyPayFPTForm.getWardCode());
                    ExecuteCus.setLong(19, accountAnyPayFPTForm.getAgent_id());
                    ExecuteCus.registerOutParameter(20, OracleTypes.CHAR);
                    ExecuteCus.execute();
                    String error = ExecuteCus.getString(20);
                    saveMethodCallLog(className, "update_agent", "", userCreateCus, error);
                    if (error != null) {
                        connectionCus.rollback();
                    }
                    if (error == null && strErrorCode == null) {
                        //Update sang DDV
                        ViettelService request = new ViettelService();
                        request = getInfo();
                        UpdateForSales updateForSales = new UpdateForSales();
                        String errorDDV = updateForSales.UpdateAccountInfo(request);
                        saveMethodCallLog(className, "update_agent", "", userCreateCTVDDV, errorDDV);
                        if (errorDDV.charAt(0) == '0' || error != null || strErrorCode != null) {
                            if (connectionCus != null) {
                                connectionCus.rollback();
                                connectionCus.close();
                            }
                            if (connection != null) {
                                connection.rollback();
                                connection.close();
                            }
                            req.setAttribute("returnMsg", "Cập nhật tài khoản không thành công");
                            return pageForward;
                        } else {
                            if (connectionCus != null) {
                                connectionCus.commit();
                                connectionCus.close();
                            }
                            if (connection != null) {
                                connection.commit();
                                connection.close();
                            }
                            req.setAttribute("returnMsg", "Cập nhật tài khoản thành công");
                        }
                    } else {
                        if (connectionCus != null) {
                            connectionCus.rollback();
                            connectionCus.close();
                        }
                        if (connection != null) {
                            connection.rollback();
                            connection.close();
                        }
                        req.setAttribute("returnMsg", "Cập nhật tài khoản không thành công");
                        return pageForward;
                    }
                } else {
                    if (editType.equals("4")) {
                        //gap sim
                        //cap nhat serial ben evoucher
                        String strSQL = "{call " + schemaEvoucher + ".AGENT_PKG.update_iccid_agent(?,?,?,?,?)}";
                        Execute = connection.prepareCall(strSQL);
                        Execute.setLong(1, accountAnyPayFPTForm.getAgent_id());
                        Execute.setString(2, accountAnyPayFPTForm.getSerialNew());
                        Execute.setString(3, userToken.getLoginName());
                        Execute.setString(4, req.getLocalAddr());
                        Execute.registerOutParameter(5, OracleTypes.CHAR);
                        Execute.execute();
                        strErrorCode = Execute.getString(6);
                        if (strErrorCode != null) {
                            connection.rollback();
                        }
                        //cap nhat ben cus
                        if (strErrorCode == null) {
                            //cap nhat ben DDV
                            UpdateForSales updateForSales = new UpdateForSales();
                            String errorDDV = updateForSales.UpdateIccid(accountAnyPayFPTForm.getSerialNew(), accountAnyPayFPTForm.getAgent_id());
                            if (errorDDV.charAt(0) == '0' || strErrorCode != null) {
                                connection.rollback();
                                connection.close();
                                req.setAttribute("returnMsg", "Gắp sim thất bại");
                                return pageForward;
                            } else {
                                connection.commit();
                                connection.close();
                                accountAnyPayFPTForm.setSaveSerialOld(accountAnyPayFPTForm.getSerialNew());
                                req.setAttribute("returnMsg", "Gắp sim thành công");
                            }
                        } else {
                            connection.rollback();
                            connection.close();
                            req.setAttribute("returnMsg", "Không gắp được sim bên Evoucher");
                            return pageForward;
                        }
                    }
                }
            }
        }

        req.getSession().setAttribute("showEdit", 0);
        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    public String showLogAccountAgent() throws Exception {
        String pageForward;
        log.debug("# Begin method ReportChangeGood.preparePage");
        HttpServletRequest req = getRequest();
        Long agentId = Long.parseLong(req.getParameter("agentId").toString());
        String sql = "";
        sql += " SELECT ID as iD, agent_id as agentId, fieid as fieid,current_value as currentValue,";
        sql += " (SELECT NAME FROM app_params WHERE TYPE = 'SIM_STATUS' AND status = '1'";
        sql += " AND code = current_value) as currentValueName,  new_value as newValue,";
        sql += " (SELECT NAME FROM app_params WHERE TYPE = 'SIM_STATUS' AND status = '1'";
        sql += " AND code = new_value) as newValueName,";
        sql += " TO_CHAR(date_time,'dd/mm/yyyy HH24:MI:SS') as datetime,user_name as user_name,HOST as host, reason.reason_name as reasonName";
        sql += " FROM evoucher_owner.agent_acction_audit@db_evoucher a,reason";
        sql += " WHERE reason.reason_id = a.reason";
        sql += " and agent_id = ?";
        sql += " order by datetime desc";
        Query query = getSession().createSQLQuery(sql).
                addScalar("iD", Hibernate.LONG).
                addScalar("agentId", Hibernate.LONG).
                addScalar("currentValue", Hibernate.STRING).
                addScalar("currentValueName", Hibernate.STRING).
                addScalar("newValue", Hibernate.STRING).
                addScalar("newValueName", Hibernate.STRING).
                addScalar("datetime", Hibernate.STRING).
                addScalar("user_name", Hibernate.STRING).
                addScalar("host", Hibernate.STRING).
                addScalar("reasonName", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(ShowLogAccountFPTBean.class));
        query.setParameter(0, agentId);
        List<ShowLogAccountFPTBean> listLog = query.list();
        req.setAttribute("listLog", listLog);

        pageForward = "showLogAccount";
        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        String shopType = imSearchBean.getOtherParamValue().trim();
        if (shopType == null || shopType.equals("")) {
            return listImSearchBean;
        }
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        if (shopType.equals("1")) {
            strQuery1.append("and a.channelTypeId = 4 ");
        } else {
            strQuery1.append("and a.channelTypeId <> 4 ");
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getNameShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        String shopType = imSearchBean.getOtherParamValue().trim();
        if (shopType == null || shopType.equals("")) {
            return listImSearchBean;
        }
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        } else {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add("");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        if (shopType.equals("1")) {
            strQuery1.append("and a.channelTypeId = 4 ");
        } else {
            strQuery1.append("and a.channelTypeId <> 4 ");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac nhan vien thuoc mot don vi
     *
     */
    public List<ImSearchBean> getListStaff(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac nhan vien thuoc mot don vi
     *
     */
    public List<ImSearchBean> getNameStaff(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        } else {
            return listImSearchBean;
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }


        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    //F9 cho tinh, phuong, xa
    //F9 cho province
    public List<ImSearchBean> getProvinceList(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.areaCode, a.name) ");
        strQuery1.append("from Area a ");
        strQuery1.append("where a.district is null ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.areaCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.areaCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }
        return listImSearchBean;
    }

    public Long getProvinceListSize(ImSearchBean imSearchBean) {
        HttpServletRequest r = imSearchBean.getHttpServletRequest();
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select count(*) ");
        strQuery1.append("from Area a ");
        strQuery1.append("where a.district is null ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.areaCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("order by nlssort(a.areaCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            return tmpList1.get(0);
        }
        return null;
    }

    public List<ImSearchBean> getProvinceName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.areaCode, a.name) ");
        strQuery1.append("from Area a ");
        strQuery1.append("where a.district is null ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.areaCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        } else {
            return listImSearchBean;
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);
        strQuery1.append("order by nlssort(a.areaCode, 'nls_sort=Vietnamese') asc ");
        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }
        return listImSearchBean;
    }

    //F9 cho district
    public List<ImSearchBean> getDistrictList(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List listParameter1 = new ArrayList();

        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.district, a.name) ");
        strQuery1.append("from Area a ");
        strQuery1.append("where a.district is not null and a.precinct is null ");
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and lower(a.province) = ?  ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.district) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.district, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }
        return listImSearchBean;
    }

    /**
     * @author: ductm6@viettel.com.vn
     * @desc  :     LẤY SỐ LƯỢNG BẢN GHI
     */
    public Long getDistrictListSize(ImSearchBean imSearchBean) {
        HttpServletRequest r = imSearchBean.getHttpServletRequest();
        List listParameter1 = new ArrayList();

        StringBuilder strQuery1 = new StringBuilder("select count(*) ");
        strQuery1.append("from Area a ");
        strQuery1.append("where a.district is not null and a.precinct is null ");
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and lower(a.province) = ?  ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return null;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.district) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("order by nlssort(a.district, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            return tmpList1.get(0);
        }
        return null;
    }

    public List<ImSearchBean> getDistrictName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.district, a.name) ");
        strQuery1.append("from Area a ");
        strQuery1.append("where a.district is not null and a.precinct is null ");
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and lower(a.province) = ? ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.district) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        } else {
            return listImSearchBean;
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.district, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }
        return listImSearchBean;
    }

    //F9 cho Ward
    public List<ImSearchBean> getWardList(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.precinct, a.name) ");
        strQuery1.append("from Area a ");
        strQuery1.append("where a.district is not null and a.precinct is not null and a.precinct is not null ");
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");
            if (index == 0 || index == otherParam.length() - 1) {
                return listImSearchBean;
            } else {
                String districtCode = otherParam.substring(0, index).toLowerCase();
                String provinceCode = otherParam.substring(index + 1, otherParam.length()).toLowerCase();
                strQuery1.append("and lower(a.province) = ? ");
                listParameter1.add(provinceCode);
                strQuery1.append("and lower(a.district) = ? ");
                listParameter1.add(districtCode);
            }

        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.precinct) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.precinct, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }
        return listImSearchBean;
    }

    public List<ImSearchBean> getWardName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.precinct, a.name) ");
        strQuery1.append("from Area a ");
        strQuery1.append("where a.district is not null and a.precinct is not null and a.precinct is not null ");
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");
            if (index == 0 || index == otherParam.length() - 1) {
                return listImSearchBean;
            } else {
                String districtCode = otherParam.substring(0, index).toLowerCase();
                String provinceCode = otherParam.substring(index + 1, otherParam.length()).toLowerCase();
                strQuery1.append("and lower(a.province) = ? ");
                listParameter1.add(provinceCode);
                strQuery1.append("and lower(a.district) = ? ");
                listParameter1.add(districtCode);
            }

        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.precinct) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        } else {
            return listImSearchBean;
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.precinct, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }
        return listImSearchBean;
    }

    /**
     * @author haint41
     * @date 30/11/2011
     * @desc 
     * @param imSearchBean
     * @return 
     */
    public Long getWardListSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from Area a ");
        strQuery1.append("where a.district is not null and a.precinct is not null and a.precinct is not null ");
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");
            if (index == 0 || index == otherParam.length() - 1) {
                return null;
            } else {
                String districtCode = otherParam.substring(0, index).toLowerCase();
                String provinceCode = otherParam.substring(index + 1, otherParam.length()).toLowerCase();
                strQuery1.append("and lower(a.province) = ? ");
                listParameter1.add(provinceCode);
                strQuery1.append("and lower(a.district) = ? ");
                listParameter1.add(districtCode);
            }

        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return null;
        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.precinct) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.precinct, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<Long> tmpList = query1.list();
        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);
        } else {
            return null;
        }
    }

    public String encryptionCode(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String output;
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        sha.reset();
        sha.update(input.getBytes("UTF-8"));
        output = Base64.encodeBase64String(sha.digest());
        output = output.substring(0, output.length() - 2);
        return output;

    }

    public Long getAgentId(Long ownerId, Long ownerType, Long channelType) {
        //Lay accountId ben FPT
        String sql = "select STOCK_ID from stock_owner_tmp where 1 = 1 and owner_id = ? and owner_type = ?";
        Query query = getSession().createSQLQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        //query.setParameter(2, channelType);
        List list = query.list();
        Iterator iter = list.iterator();
        Long agentId = 0L;
        while (iter.hasNext()) {
            Object temp = (Object) iter.next();
            agentId = new Long(temp.toString());
        }
        String[] arrMess;
        if (agentId.equals(0L)) {
            return 0L;
        }
        return agentId;
    }

    public void resetForm() {
        accountAnyPayFPTForm.setAccountType(null);
        accountAnyPayFPTForm.setShopCode(null);
        accountAnyPayFPTForm.setShopName(null);
        accountAnyPayFPTForm.setStaffCode(null);
        accountAnyPayFPTForm.setStaffName(null);
        accountAnyPayFPTForm.setProvinceCode(null);
        accountAnyPayFPTForm.setProvinceName(null);
        accountAnyPayFPTForm.setDistrictCode(null);
        accountAnyPayFPTForm.setDistrictName(null);
        accountAnyPayFPTForm.setWardCode(null);
        accountAnyPayFPTForm.setWardName(null);
        accountAnyPayFPTForm.setNameAccount(null);
        accountAnyPayFPTForm.setBirthDate(null);
        accountAnyPayFPTForm.setIdNo(null);
        accountAnyPayFPTForm.setMakeDate(null);
        accountAnyPayFPTForm.setMakeAddress(null);
        accountAnyPayFPTForm.setPhoneNumber(null);
        accountAnyPayFPTForm.setFax(null);
        accountAnyPayFPTForm.setEmail(null);
        accountAnyPayFPTForm.setPassword(null);
        accountAnyPayFPTForm.setRePassword(null);
        accountAnyPayFPTForm.setDatePassword(null);
        accountAnyPayFPTForm.setSecretQuestion(null);
        accountAnyPayFPTForm.setStatus(null);
        accountAnyPayFPTForm.setReasonId(null);
        accountAnyPayFPTForm.setAmount(null);
        accountAnyPayFPTForm.setAddress(null);
        accountAnyPayFPTForm.setAgent_id(null);
        accountAnyPayFPTForm.setIsdn(null);
        accountAnyPayFPTForm.setIccid(null);
        accountAnyPayFPTForm.setParent_agent(null);
        accountAnyPayFPTForm.setTin(null);
        accountAnyPayFPTForm.setCentrerId(null);
        accountAnyPayFPTForm.setStaffId(null);
        accountAnyPayFPTForm.setDateCreate(null);
        accountAnyPayFPTForm.setAccountName(null);

    }

    public ViettelService getInfo() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        ViettelService request = new ViettelService();
        request.set("STAFF_STK_ID", accountAnyPayFPTForm.getAgent_id());
        if (accountAnyPayFPTForm.getIsdn().charAt(0) == '0') {
            request.set("MSISDN", accountAnyPayFPTForm.getIsdn());
        } else {
            request.set("MSISDN", "0" + accountAnyPayFPTForm.getIsdn());
        }

        request.set("ICCID", accountAnyPayFPTForm.getSaveSerialOld());
        request.set("TRADE_NAME", accountAnyPayFPTForm.getNamerepresentative());
        request.set("OWNER_NAME", accountAnyPayFPTForm.getNameAccount());
        if (!accountAnyPayFPTForm.getBirthDate().equals("")) {
            request.set("BIRTH_DATE", accountAnyPayFPTForm.getBirthDate().substring(0, 10));
        }
        request.set("CONTACT_NO", accountAnyPayFPTForm.getPhoneNumber());
        request.set("OUTLET_ADDRESS", accountAnyPayFPTForm.getAddress());
        request.set("EMAIL", accountAnyPayFPTForm.getEmail());
        request.set("SECURE_QUESTION", accountAnyPayFPTForm.getSecretQuestion());
        request.set("MPIN", encryptionCode(accountAnyPayFPTForm.getPassword()));
        request.set("SAP_CODE", accountAnyPayFPTForm.getShopCode());
        request.set("LOGIN_FAILURE_COUNT", 0L);
        if (accountAnyPayFPTForm.getStatus() != null && accountAnyPayFPTForm.getStatus().equals(0L)) {
            request.set("STATUS", accountAnyPayFPTForm.getStatus());
        } else {
            request.set("STATUS", 1L);
        }
        request.set("ACCOUNT_TYPE", accountAnyPayFPTForm.getAccountType());
        request.set("PARENT_ID", 0L);
        request.set("TIN", accountAnyPayFPTForm.getTin());
        if (!accountAnyPayFPTForm.getDatePassword().equals("")) {
            request.set("MPIN_EXPIRE_DATE", accountAnyPayFPTForm.getDatePassword().substring(0, 10));
        }
        request.set("CENTRE_ID", 0L);
        request.set("MPIN_STATUS", 0L);
        request.set("SEX", 0L);
        if (!accountAnyPayFPTForm.getDateCreate().equals("")) {
            request.set("ISSUE_DATE", accountAnyPayFPTForm.getMakeDate().substring(0, 10));
        }
        request.set("STAFF_CODE", accountAnyPayFPTForm.getStaffCode());
        request.set("PROVINCE", accountAnyPayFPTForm.getProvinceCode());
        request.set("ISSUE_PLACE", accountAnyPayFPTForm.getMakeAddress());
        request.set("FAX", accountAnyPayFPTForm.getFax());
        request.set("DISTRICT", accountAnyPayFPTForm.getDistrictCode());
        request.set("PRECINCT", accountAnyPayFPTForm.getWardCode());
        request.set("NUM_ADD_MONEY", accountAnyPayFPTForm.getAmount());
        request.set("ID_NO", accountAnyPayFPTForm.getIdNo());
        if (!accountAnyPayFPTForm.getMakeDate().equals("")) {
            request.set("ISSUE_DATE", accountAnyPayFPTForm.getMakeDate().substring(0, 10));
        }
        return request;
    }

    private void setInfoToForm(ViettelService request) throws NoSuchAlgorithmException, UnsupportedEncodingException, Exception {
        Object object;
        String provinceCode = "";
        String districtCode = "";
        String warCode = "";
        String shopCode = "";
        String staffCode = "";
        object = request.get("STAFF_STK_ID");
        if (object != null) {
            accountAnyPayFPTForm.setAgent_id(Long.parseLong(request.get("STAFF_STK_ID").toString()));
        }
        object = request.get("MSISDN");
        if (object != null) {
            accountAnyPayFPTForm.setIsdn(request.get("MSISDN").toString());
        }
        object = request.get("ICCID");
        if (object != null) {
            accountAnyPayFPTForm.setSerial(request.get("ICCID").toString());
            accountAnyPayFPTForm.setSaveSerialOld(request.get("ICCID").toString());
        }
        object = request.get("TRADE_NAME");
        if (object != null) {
            accountAnyPayFPTForm.setNamerepresentative(request.get("TRADE_NAME").toString());
        }
        object = request.get("OWNER_NAME");
        if (object != null) {
            accountAnyPayFPTForm.setNameAccount(request.get("OWNER_NAME").toString());
        }
        object = request.get("BIRTH_DATE");
        if (object != null) {
            accountAnyPayFPTForm.setBirthDate(request.get("BIRTH_DATE").toString());
        }
        object = request.get("CREATE_DATE");
        if (object != null) {
            accountAnyPayFPTForm.setDateCreate(request.get("CREATE_DATE").toString());
        }
        object = request.get("CONTACT_NO");
        if (object != null) {
            accountAnyPayFPTForm.setPhoneNumber(request.get("CONTACT_NO").toString());
        }
        object = request.get("OUTLET_ADDRESS");
        if (object != null) {
            accountAnyPayFPTForm.setAddress(request.get("OUTLET_ADDRESS").toString());
        }
        object = request.get("EMAIL");
        if (object != null) {
            accountAnyPayFPTForm.setEmail(request.get("EMAIL").toString());
        }
        object = request.get("SECURE_QUESTION");
        if (object != null) {
            accountAnyPayFPTForm.setSecretQuestion(request.get("SECURE_QUESTION").toString());
        }
        object = request.get("MPIN");
        if (object != null) {
            accountAnyPayFPTForm.setPassword(request.get("MPIN").toString());
            accountAnyPayFPTForm.setPassAcc(request.get("MPIN").toString());
        }
        object = request.get("MPIN");
        if (object != null) {
            accountAnyPayFPTForm.setRePassword(request.get("MPIN").toString());
        }
        object = request.get("SAP_CODE");
        if (object != null) {
            accountAnyPayFPTForm.setShopCode(request.get("SAP_CODE").toString());
            shopCode = request.get("SAP_CODE").toString();
        }
        object = request.get("STATUS");
        if (object != null) {
            accountAnyPayFPTForm.setStatus(Long.parseLong(request.get("STATUS").toString()));
            accountAnyPayFPTForm.setStatusAcc(Long.parseLong(request.get("STATUS").toString()));
        }
        object = request.get("ACCOUNT_TYPE");
        if (object != null) {
            accountAnyPayFPTForm.setAccountType(Long.parseLong(request.get("ACCOUNT_TYPE").toString()));
        }
        object = request.get("TIN");
        if (object != null) {
            accountAnyPayFPTForm.setTin(request.get("TIN").toString());
        }
        object = request.get("MPIN_EXPIRE_DATE");
        if (object != null) {
            accountAnyPayFPTForm.setDatePassword(request.get("MPIN_EXPIRE_DATE").toString());
        }
        object = request.get("ISSUE_DATE");
        if (object != null) {
            accountAnyPayFPTForm.setMakeDate(request.get("ISSUE_DATE").toString());
        }
        object = request.get("STAFF_CODE");
        if (object != null) {
            accountAnyPayFPTForm.setStaffCode(request.get("STAFF_CODE").toString());
            staffCode = request.get("STAFF_CODE").toString();
        }
        object = request.get("PROVINCE");
        if (object != null) {
            accountAnyPayFPTForm.setProvinceCode(request.get("PROVINCE").toString());
            provinceCode += request.get("PROVINCE").toString();
            districtCode += provinceCode;
            warCode += districtCode;
        }
        object = request.get("ISSUE_PLACE");
        if (object != null) {
            accountAnyPayFPTForm.setMakeAddress(request.get("ISSUE_PLACE").toString());
        }
        object = request.get("FAX");
        if (object != null) {
            accountAnyPayFPTForm.setFax(request.get("FAX").toString());
        }
        object = request.get("DISTRICT");
        if (object != null) {
            accountAnyPayFPTForm.setDistrictCode(request.get("DISTRICT").toString());
            districtCode += request.get("DISTRICT").toString();
            warCode = districtCode;
        }
        object = request.get("PRECINCT");
        if (object != null) {
            accountAnyPayFPTForm.setWardCode(request.get("PRECINCT").toString());
            warCode += request.get("PRECINCT").toString();
        }
        object = request.get("NUM_ADD_MONEY");
        if (object != null) {
            accountAnyPayFPTForm.setAmount(Long.parseLong(request.get("NUM_ADD_MONEY").toString()));
        }
        object = request.get("ID_NO");
        if (object != null) {
            accountAnyPayFPTForm.setIdNo(request.get("ID_NO").toString());
        }

        accountAnyPayFPTForm.setProvinceName(getNameArea(provinceCode));
        accountAnyPayFPTForm.setDistrictName(getNameArea(districtCode));
        accountAnyPayFPTForm.setWardName(getNameArea(warCode));
        accountAnyPayFPTForm.setShopName(getShopName(shopCode));
        accountAnyPayFPTForm.setStaffName(getStaffName(staffCode));
        if (accountAnyPayFPTForm.getAccountType() != null) {
            accountAnyPayFPTForm.setAccountName(getNameAccountType(accountAnyPayFPTForm.getAccountType()));
        }
    }

    public String getNameArea(String AreaCode) {
        String sql = "From Area where lower(areaCode) = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, AreaCode.trim().toLowerCase());
        List<Area> list = query.list();
        if (list != null && list.size() > 0) {
            return list.get(0).getName();
        }
        return "";
    }

    public String getStaffName(String staffCode) throws Exception {
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff.getName();
        }
        return "";

    }

    public Long getStaffId(String staffCode) throws Exception {
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff.getStaffId();
        }
        return 0L;

    }

    public String getShopName(String shopCode) throws Exception {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop.getName();
        }
        return "";
    }

    public Long getShopId(String shopCode) throws Exception {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop.getShopId();
        }
        return 0L;
    }

    public String getNameAccountType(Long accountId) {
        if (accountId != null) {
            if (accountId.equals(1L)) {
                return "Đại lý";
            } else {
                if (accountId.equals(2L)) {
                    return "Điểm bán";
                } else {
                    if (accountId.equals(3L)) {
                        return "Cộng tác viên";
                    } else {
                        return "Nhân viên quản lý";
                    }
                }
            }
        }
        return "";
    }

    public String getStockKit(String serial, Long shopId, Long OWNER_TYPE_SHOP) {
        String sql = "";
        sql += "select sm.stockModelCode from StockKit sk, StockModel sm";
        sql += " where 1= 1";
        sql += " and sk.stockModelId = sm.stockModelId";
        //sql += " and sm.stock_model_code = 'KITCTV'";
        sql += " and serial = ?";
        sql += " and sk.ownerId = ?";
        sql += " and sk.ownerType = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, serial);
        query.setParameter(1, shopId);
        query.setParameter(2, OWNER_TYPE_SHOP);
        List list = query.list();
        if (list != null && list.size() > 0) {
            return list.get(0).toString();
        }

        return "";
    }

    public String pageNavigator() throws Exception {
        log.debug("# Begin method ReportStockImpExpDAO.pageNavigator");
        String pageForward = "showLogAccount";
        return pageForward;
    }

    public void saveMethodCallLog(String className, String methodName, String parameter, String userCreate, String result) throws Exception {
        MethodCallLog methodCallLog = new MethodCallLog();
        methodCallLog.setMethodCallLogId(getSequence("METHOD_CALL_LOG_SEQ"));
        methodCallLog.setClassName(className);
        methodCallLog.setCreateUser(userCreate);
        methodCallLog.setMethodCallResult(result);
        methodCallLog.setMethodName(methodName);
        methodCallLog.setParameter(parameter);
        methodCallLog.setCreateDate(DateTimeUtils.getSysDate());
        methodCallLog.setLastUpdateTime(DateTimeUtils.getSysDate());
        getSession().save(methodCallLog);
    }
}
