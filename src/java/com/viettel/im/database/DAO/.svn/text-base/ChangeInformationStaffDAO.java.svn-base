package com.viettel.im.database.DAO;

import com.google.gson.Gson;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.viettel.anypay.util.DataUtil;
import com.viettel.bccs.cm.api.InterfaceCMToIM;
import com.viettel.bccs.cm.api.InterfaceCm;
import com.viettel.bccs.cm.database.BO.STKSub;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.bean.AppParamsBean;
import com.viettel.im.client.bean.ChannelWalletBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ViewStaffBean;
import com.viettel.im.client.form.AddStaffCodeCTVDBForm;
import com.viettel.im.common.ConfigParam;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ChangeChannelTypeWallet;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.MobileWallet;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.ResponseWallet;
import com.viettel.im.common.util.ResultViewInforWallet;
import com.viettel.im.common.util.TextSecurity;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.LogCallWsWallet;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockIsdnMobile;
import com.viettel.im.database.BO.StockOwnerTmp;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.UserVsa;
import com.viettel.im.database.BO.VsaUser;
import com.viettel.security.util.DbProcessor;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class ChangeInformationStaffDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ChangeInformationStaffDAO.class);
    private String pageForward;
    private static final String UPDATE_ADDRESS = "updateAddress";
    private final String CHANGE_INFO = "changeInformationStaff";
    private final String CHANGE_INFO_AP = "changeInformationStaffAP";
    private final String STAFF_NAME_DEFAUT = "EMPTY NAME";
    private final String LIST_STAFF = "listChangeInfoStaff";
    private final String LIST_STAFF_AP = "listChangeInfoStaffAP";
    private final String REQUEST_LIST_PRICE_POLICY = "listPricePolicy";
    private final String REQUEST_LIST_DISCOUNT_POLICY = "listDiscountPolicy";
    private final Long CHANNEL_TYPE_ID = Long.valueOf(10L);
    private final Long CHANNEL_TYPE_AP_ID = Long.valueOf(13L);
    private final String REQUEST_MESSAGE = "messageUpdate";
    private final String REQUEST_MESSAGE_ADD = "messageAdd";
    private final String REQUEST_MESSAGE_PARAM = "paramMsgAdd";
    private final String REQUEST_LIST_PROFILE_STATE = "listProfileState";
    private final String REQUEST_LIST_BOARD_STATE = "listBoardState";
    private final String REQUEST_LIST_STAFF_STATUS = "listStaffStatus";
    private final String REQUEST_LIST_CHANNEL_TYPE = "listChannelType";
    private final String REQUEST_LIST_CHECK_VAT = "listCheckVatStatus";
    private final String REQUEST_LIST_AGENT_TYPE = "listAgentType";
    private final String REQUEST_LIST_CHANNEL_STATUS_SEARCH = "listChannelStatusSearch";
    private static boolean afterUpdateSuccess = false;
    private AddStaffCodeCTVDBForm addStaffCodeCTVDBForm = new AddStaffCodeCTVDBForm();
    Map hashMapResult = new HashMap();
    String ROLE_CREATE_CHANNEL = "ROLE_CREATE_CHANNEL";
    String ROLE_UPDATE_CHANNEL = "ROLE_UPDATE_CHANNEL";
    String ROLE_CANCEL_CHANNEL = "ROLE_CANCEL_CHANNEL";
    String ROLE_F9_SHOP = "saleTransManagementf9Shop";
    String ROLE_F9_STAFF = "saleTransManagementf9Staff";
    String ROLE_AGREE_CHANNEL = "ROLE_AGREE_CHANNEL";

    public Map getHashMapResult() {
        return this.hashMapResult;
    }

    public void setHashMapResult(Map hashMapResult) {
        this.hashMapResult = hashMapResult;
    }

    public AddStaffCodeCTVDBForm getAddStaffCodeCTVDBForm() {
        return this.addStaffCodeCTVDBForm;
    }

    public void setAddStaffCodeCTVDBForm(AddStaffCodeCTVDBForm addStaffCodeCTVDBForm) {
        this.addStaffCodeCTVDBForm = addStaffCodeCTVDBForm;
    }
    private InputStream fileInputStream;
    public static String fileDowd;

    public InputStream getFileInputStream() {

        return fileInputStream;

    }

    public String preparePage() throws Exception {
        this.log.info("Begin method preparePage of addStaffCodeCVTDBDAO ...");
        HttpServletRequest req = getRequest();

        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        this.addStaffCodeCTVDBForm.setShopCodeSearch(userToken.getShopCode());
        this.addStaffCodeCTVDBForm.setShopNameSearch(userToken.getShopName());
        this.addStaffCodeCTVDBForm.setStaffMagCodeSearch(userToken.getLoginName());
        this.addStaffCodeCTVDBForm.setStaffMagNameSearch(userToken.getStaffName());

        setTabSession("changeStatus", "true");
        removeTabSession("listStaff");
        setTabSession("changeInfo", "true");
        removeTabSession("listAgentType");

        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());

        setTabSession("listProfileState", appParamsDAO.findAppParamsList("PROFILE_STATE", Constant.STATUS_USE.toString()));
        setTabSession("listBoardState", appParamsDAO.findAppParamsList("BOARD_STATE", Constant.STATUS_USE.toString()));
        setTabSession("listStaffStatus", appParamsDAO.findAppParamsList("CHANNEL_STATUS", Constant.STATUS_USE.toString()));
        setTabSession("listCheckVatStatus", appParamsDAO.findAppParamsList("STAFF_CHECK_VAT", Constant.STATUS_USE.toString()));
        setTabSession("listAgentType", appParamsDAO.findAppParamsList("STAFF_AGENT_TYPE", Constant.STATUS_USE.toString()));
        //vi dien tu
        setTabSession("lstChannelTypeWallet", appParamsDAO.findAppParamsList("CHANNEL_TYPE_WALLET", Constant.STATUS_USE.toString()));
        setTabSession("listChannelTypeAgree", appParamsDAO.findAppParamsList("IS_CHECK_AGREE", Constant.STATUS_USE.toString()));

        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(getSession());
        List listChannelType = channelTypeDAO.findByIsVtUnitAndIsPrivate("2", Constant.CHANNEL_TYPE_IS_NOT_PRIVATE, Constant.OWNER_TYPE_STAFF.toString());

        setTabSession("listChannelType", listChannelType);

        List listChannelStatusSearch = appParamsDAO.findAppParamsByType_1("CHANNEL_STATUS");
        setTabSession("listChannelStatusSearch", listChannelStatusSearch);

        if (!AuthenticateDAO.checkAuthority("convertChannelType", req)) {
            req.getSession().setAttribute("convertChannelType", "true");
        } else {
            req.getSession().setAttribute("convertChannelType", "false");
        }

        this.pageForward = "changeInformationStaff";
        this.log.info("End method preparePage of addStaffCodeCVTDBDAO");
        return this.pageForward;
    }

    public String clickStaff() throws Exception {
        clearRoleChannel();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Object object = req.getParameter("staffId");
        Long staffId = Long.valueOf(0L);
        if (object != null) {
            resetForm();
            List lstAgentType = new ArrayList();
            staffId = Long.valueOf(Long.parseLong(object.toString()));
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            Staff staff = staffDAO.findById(staffId);
            if (staff != null) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());
                Shop shop = shopDAO.findById(staff.getShopId());
                if (shop != null) {
                    this.addStaffCodeCTVDBForm.setOldShopId(shop.getShopId());
                    this.addStaffCodeCTVDBForm.setShopCode(shop.getShopCode());
                    this.addStaffCodeCTVDBForm.setShopName(shop.getName());

                    if (shop.getParentShopId() != null) {
                        this.addStaffCodeCTVDBForm.setParentShopId(shop.getParentShopId());
                    }

                }

                this.addStaffCodeCTVDBForm.setOldStaffId(staff.getStaffId());
                this.addStaffCodeCTVDBForm.setStaffCode(staff.getStaffCode());
                this.addStaffCodeCTVDBForm.setStaffName(staff.getName());
                this.addStaffCodeCTVDBForm.setIdNo(staff.getIdNo());
                this.addStaffCodeCTVDBForm.setMakeAddress(staff.getIdIssuePlace());
                this.addStaffCodeCTVDBForm.setAddress(staff.getAddress());

                this.addStaffCodeCTVDBForm.setStatus(staff.getStatus());
                this.addStaffCodeCTVDBForm.setIsdn(staff.getIsdn());
                if ((staff.getStaffOwnerId() != null) && (!staff.getStaffOwnerId().equals(Long.valueOf(0L)))) {
                    Staff staffManagement = staffDAO.findById(staff.getStaffOwnerId());
                    if (staffManagement != null) {
                        this.addStaffCodeCTVDBForm.setOldStaffOwnerId(staffManagement.getStaffId());
                        this.addStaffCodeCTVDBForm.setStaffManagementCode(staffManagement.getStaffCode());
                        this.addStaffCodeCTVDBForm.setStaffManagementName(staffManagement.getName());
                    }

                }

                if (staff.getIdIssueDate() != null) {
                    this.addStaffCodeCTVDBForm.setMakeDate(DateTimeUtils.convertDateToString(staff.getIdIssueDate()));
                }

                checkRoleUser(userToken, req);

                this.addStaffCodeCTVDBForm.setProvinceCode(staff.getProvince());
                this.addStaffCodeCTVDBForm.setDistrictCode(staff.getDistrict());
                this.addStaffCodeCTVDBForm.setPrecinctCode(staff.getPrecinct());

                this.addStaffCodeCTVDBForm.setProfileState(staff.getProfileState());
                this.addStaffCodeCTVDBForm.setBoardState(staff.getBoardState());
                this.addStaffCodeCTVDBForm.setBirthday(staff.getBirthday());

                Area provinceArea = getArea(staff.getProvince());
                if ((provinceArea != null) && (provinceArea.getName() != null)) {
                    this.addStaffCodeCTVDBForm.setProvinceName(provinceArea.getName());
                    Area districtArea = getArea(staff.getProvince() + staff.getDistrict());
                    if ((districtArea != null) && (districtArea.getName() != null)) {
                        this.addStaffCodeCTVDBForm.setDistrictName(districtArea.getName());
                        Area precinctArea = getArea(staff.getProvince() + staff.getDistrict() + staff.getPrecinct());
                        if ((precinctArea != null) && (precinctArea.getName() != null)) {
                            this.addStaffCodeCTVDBForm.setPrecinctName(precinctArea.getName());
                        }
                    }
                }
            }
            this.addStaffCodeCTVDBForm.setStaffId(staffId);

            this.addStaffCodeCTVDBForm.setUsedfulWidth(staff.getUsedfulWidth());
            this.addStaffCodeCTVDBForm.setSurfaceArea(staff.getSurfaceArea());
            this.addStaffCodeCTVDBForm.setTradeName(staff.getTradeName());
            this.addStaffCodeCTVDBForm.setContactName(staff.getContactName());
            this.addStaffCodeCTVDBForm.setTel(staff.getTel());
            this.addStaffCodeCTVDBForm.setRegistryDate(staff.getRegistryDate());
            this.addStaffCodeCTVDBForm.setCheckVat(staff.getCheckVat());
            this.addStaffCodeCTVDBForm.setIsChecked(staff.getIsChecked());

            if ((staff.getAgentType() != null)
                    && (lstAgentType != null) && (!lstAgentType.isEmpty())) {
                for (int k = 0; k < lstAgentType.size(); k++) {
                    if (((AppParamsBean) lstAgentType.get(k)).getCode().equals(staff.getAgentType().toString())) {
                        this.addStaffCodeCTVDBForm.setAgentType(staff.getAgentType());
                        break;
                    }
                }

            }

            this.addStaffCodeCTVDBForm.setNote(staff.getNote());

            this.addStaffCodeCTVDBForm.setStreetBlockName(staff.getStreetBlockName());
            this.addStaffCodeCTVDBForm.setStreetName(staff.getStreetName());
            this.addStaffCodeCTVDBForm.setHome(staff.getHome());
            this.addStaffCodeCTVDBForm.setLastUpdateKey(staff.getLastUpdateKey());
            this.addStaffCodeCTVDBForm.setRegistrationPoint(staff.getRegistrationPoint());
            this.addStaffCodeCTVDBForm.setBtsCode(staff.getBtsCode());
            this.addStaffCodeCTVDBForm.setFullAddress("");
            //vi dien tu
            this.addStaffCodeCTVDBForm.setChannelWallet(staff.getChannelWallet());
            this.addStaffCodeCTVDBForm.setIsdnWallet(staff.getIsdnWallet());
            this.addStaffCodeCTVDBForm.setParentCodeWallet("");
            this.addStaffCodeCTVDBForm.setParentNameWallet("");
            this.addStaffCodeCTVDBForm.setImeiSmartphone(staff.getImeiSmartphone());
            this.addStaffCodeCTVDBForm.setImageUrl(staff.getImageUrl());
            this.addStaffCodeCTVDBForm.setConverChanel(staff.getChannelTypeId());
            fileDowd = addStaffCodeCTVDBForm.getImageUrl();
            if (staff.getParentIdWallet() != null) {
                Staff parentWallet = staffDAO.findById(staff.getParentIdWallet());
                if (parentWallet != null) {
                    this.addStaffCodeCTVDBForm.setParentCodeWallet(parentWallet.getStaffCode());
                    this.addStaffCodeCTVDBForm.setParentNameWallet(parentWallet.getName());
                }
            }
            //DINHDC ADD 20160524
            //Neu la kenh vi thi lay thong tin so du ben vi
            if (staff.getChannelWallet() != null && staff.getIsdnWallet() != null) {
                req.getSession().setAttribute("isCheckChannelWallet", "true");
                if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("ChangeISDNChannelWallet"), req)) {
                    req.getSession().setAttribute("ChangeISDNChannelWallet", 1);
                } else {
                    req.getSession().setAttribute("ChangeISDNChannelWallet", 0);
                }
                Double balance = getBalanceFromWallet(getSession(), staff.getIsdnWallet(), staff.getStaffId().toString());
                if (balance.compareTo(0d) > 0) {
                    req.getSession().setAttribute("checkBalanceWallet", 1);
                } else {
                    req.getSession().setAttribute("checkBalanceWallet", 0);
                }
            } else {
                setTabSession("isCheckChannelWallet", "false");
                req.getSession().setAttribute("checkBalanceWallet", 0);
                setTabSession("ChangeISDNChannelWallet", 0);
            }
            //END DINHDC
            if (staff.getStatus().longValue() == 0L) {
                setTabSession("changeStatus", "true");
            } else {
                setTabSession("changeStatus", "false");
            }

            if (staff.getImageUrl() == null) {
                req.getSession().setAttribute("KenhDacBiet", 0);
            } else {
                req.getSession().setAttribute("KenhDacBiet", 1);
            }

            ChannelType channelType = new ChannelTypeDAO().findById(staff.getChannelTypeId());
            checkRoleChannel(channelType, req, userToken.getLoginName());
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(getSession());
            //vi dien tu
            if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("ChangeISDNChannelWallet"), req)) {
                setTabSession("lstChannelTypeWallet", appParamsDAO.findAppParamsList("CHANNEL_TYPE_WALLET", Constant.STATUS_USE.toString()));
            } else {
                if (channelType.getChannelTypeId().equals(Constant.CHANNEL_TYPE_ID_SA)) {
                    setTabSession("lstChannelTypeWallet", appParamsDAO.findAppParamsByTypeCodeStatus("CHANNEL_TYPE_WALLET", "SA", Constant.STATUS_USE.toString()));
                } else if (channelType.getChannelTypeId().equals(Constant.CHANNEL_TYPE_ID_MA)) {
                    setTabSession("lstChannelTypeWallet", appParamsDAO.findAppParamsByTypeCodeStatus("CHANNEL_TYPE_WALLET", "MA", Constant.STATUS_USE.toString()));
                } else {
                    setTabSession("lstChannelTypeWallet", appParamsDAO.findAppParamsByTypeCodeStatus("CHANNEL_TYPE_WALLET", "AG", Constant.STATUS_USE.toString()));
                }
            }
        }

        return "listChangeInfoStaff";
    }

    private void clearRoleChannel() {
        setTabSession(this.ROLE_CREATE_CHANNEL, Boolean.valueOf(false));
        setTabSession(this.ROLE_UPDATE_CHANNEL, Boolean.valueOf(false));
        setTabSession(this.ROLE_CANCEL_CHANNEL, Boolean.valueOf(false));
        setTabSession(this.ROLE_AGREE_CHANNEL, Boolean.valueOf(false));
    }

    private void checkRoleChannel(ChannelType channelType, HttpServletRequest req, String loginName) {
        if (channelType == null) {
            return;
        }

        setTabSession(this.ROLE_CREATE_CHANNEL, Boolean.valueOf(true));

        if ((channelType.getRoleCreateChannel() != null) && (!channelType.getRoleCreateChannel().trim().equals(""))
                && (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req))) {
            setTabSession(this.ROLE_CREATE_CHANNEL, Boolean.valueOf(false));
        }

        setTabSession(this.ROLE_UPDATE_CHANNEL, Boolean.valueOf(true));

        if ((channelType.getRoleCreateChannel() != null) && (!channelType.getRoleCreateChannel().trim().equals(""))
                && (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req))) {
            setTabSession(this.ROLE_UPDATE_CHANNEL, Boolean.valueOf(false));
        }

        setTabSession(this.ROLE_CANCEL_CHANNEL, Boolean.valueOf(true));

        if ((channelType.getRoleCreateChannel() != null) && (!channelType.getRoleCreateChannel().trim().equals(""))
                && (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req))) {
            setTabSession(this.ROLE_CANCEL_CHANNEL, Boolean.valueOf(false));
        }

        // Tannh ;date:23/10; check rule agree show or hide
        if (isCheckLimitSM(loginName.toUpperCase())) {
            setTabSession(this.ROLE_AGREE_CHANNEL, Boolean.valueOf(true));
        }

    }

    /**
     * author tannh date create : 12/11/2017
     *
     * @return Ham luu file lay tu tren server ve local
     *
     */
    public String myDownload() throws Exception {
        if (fileDowd.contains("AUTO_GEN_CONTRACT_POS")) {
            byte[] buffer = new byte[1024];
            String tempDir = ResourceBundleUtils.getResource("server_get_file_to_server_tempdir");
            String[] ary = fileDowd.split("\\|");
            for (int i = 0; i < ary.length; i++) {
                getFileLocationToServerAutoGen(ary[i]);
            }
            Date today = new Date();
            String zipFile = today.getTime() + ".zip";
            FileOutputStream out = new FileOutputStream(tempDir + zipFile);
            ZipOutputStream zout = new ZipOutputStream(out);

            for (int i = 0; i < ary.length; i++) {
                String[] filesrc = ary[i].split("\\/", 6);
                FileInputStream fin = new FileInputStream(tempDir + filesrc[3] + "\\" + filesrc[4] + "\\" + filesrc[5]);
                ZipEntry zip = new ZipEntry(filesrc[5]);
                zout.putNextEntry(zip);
                int length;

                while ((length = fin.read(buffer)) > 0) {
                    zout.write(buffer, 0, length);
                }
                zout.closeEntry();
            }

            zout.close();

            fileInputStream = new FileInputStream(new File(tempDir + zipFile));
            return SUCCESS;
        } else if (!fileDowd.endsWith("rar")) {
            byte[] buffer = new byte[1024];
            String tempDir = ResourceBundleUtils.getResource("server_get_file_to_server_tempdir");
            String[] ary = fileDowd.split("\\|");
            for (int i = 0; i < ary.length; i++) {
                getFileLocationToServer(ary[i]);
            }
            Date today = new Date();
            String zipFile = today.getTime() + ".zip";
            FileOutputStream out = new FileOutputStream(tempDir + zipFile);
            ZipOutputStream zout = new ZipOutputStream(out);

            for (int i = 0; i < ary.length; i++) {
                String[] filesrc = ary[i].split("\\/", 5);
                FileInputStream fin = new FileInputStream(tempDir + filesrc[3] + "\\" + filesrc[4]);
                ZipEntry zip = new ZipEntry(filesrc[4]);
                zout.putNextEntry(zip);
                int length;

                while ((length = fin.read(buffer)) > 0) {
                    zout.write(buffer, 0, length);
                }
                zout.closeEntry();
            }

            zout.close();

            fileInputStream = new FileInputStream(new File(tempDir + zipFile));
            return SUCCESS;
        } else {
            getFileLocationToServer(fileDowd);
            String[] filesrc = fileDowd.split("\\/", 5);
            String tempDir = ResourceBundleUtils.getResource("server_get_file_to_server_tempdir");
            fileInputStream = new FileInputStream(new File(tempDir + filesrc[3] + "/" + filesrc[4]));
            return SUCCESS;
        }
    }

    /**
     * author tannh date create : 12/11/2017
     *
     * @return Ham luu file lay tu tren server ve local
     *
     */
    public String getFileLocationToServer(String srcFilePDF) throws Exception {
        log.info("");
        String fileNameDir = "";
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            String pageForward;
            UserToken userToken = (UserToken) session.getAttribute(
                    Constant.USER_TOKEN);
            if (userToken == null) {
                return "sessionTimeout";
            }
            String host = ResourceBundleUtils.getResource("server_get_file_to_server_host");
            String userName = ResourceBundleUtils.getResource("server_get_file_to_server_username");
            String password = ResourceBundleUtils.getResource("server_get_file_to_server_password");
            String tempDir = ResourceBundleUtils.getResource("server_get_file_to_server_tempdir");

            List<String> lst = new ArrayList<String>();
            lst.add(srcFilePDF);
            String imgPathDB = srcFilePDF;
            fileNameDir = retrieveListFileFromFTPServer(host, userName, password, lst, imgPathDB, tempDir);

        } catch (Exception ex) {
//            String message = StringEscapeUtils.escapeHtml(languageMap.getMessage("action.search.error"));
//            log.error(message, ex);
        } finally {
        }
        return fileNameDir;
    }

    /**
     * author tannh date create : 12/11/2017
     *
     * @return Ham luu file lay tu tren server ve local
     *
     */
    public String getFileLocationToServerAutoGen(String srcFilePDF) throws Exception {
        log.info("");
        String fileNameDir = "";
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            String pageForward;
            UserToken userToken = (UserToken) session.getAttribute(
                    Constant.USER_TOKEN);
            if (userToken == null) {
                return "sessionTimeout";
            }
            String host = ResourceBundleUtils.getResource("server_get_file_to_server_host");
            String userName = ResourceBundleUtils.getResource("server_get_file_to_server_username");
            String password = ResourceBundleUtils.getResource("server_get_file_to_server_password");
            String tempDir = ResourceBundleUtils.getResource("server_get_file_to_server_tempdir");

            List<String> lst = new ArrayList<String>();
            lst.add(srcFilePDF);
            String imgPathDB = srcFilePDF;
            fileNameDir = retrieveListFileFromFTPServerAutoGen(host, userName, password, lst, imgPathDB, tempDir);

        } catch (Exception ex) {
//            String message = StringEscapeUtils.escapeHtml(languageMap.getMessage("action.search.error"));
//            log.error(message, ex);
        } finally {
        }
        return fileNameDir;
    }

    /**
     * author tannh date create : 12/11/2017
     *
     * @return Ham upload file
     *
     */
    public static boolean uploadListFileFromFTPServer(String host, String username, String password, List<String> fileName, String destinationDir, String tempDir, String channelCode) throws Exception {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        String fileNameDir = "";
        try {

            // connect, login, set timeout
            client.connect(host);
            client.login(username, password);

            // set transfer type
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
            // set timeout
            client.setSoTimeout(60000); // default 1 minute

            // check connection
            int reply = client.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                // switch to working folder
                client.changeWorkingDirectory("CONTRACT_CHANNEL");
//                client.changeWorkingDirectory(destinationDir);
                // create temp file on temp directory
                //LinhNBV start modified on May 25 2018: Rename file upload and validate Maxfile: 5MB
                for (int i = 0; i < fileName.size(); i++) {
                    String lStr;
                    if (fileName.get(i).contains("\\")) {
                        lStr = fileName.get(i).substring(fileName.get(i).lastIndexOf("\\"));
                    } else {
                        lStr = fileName.get(i).substring(fileName.get(i).lastIndexOf("/"));
                    }

                    lStr = lStr.substring(1);
                    long fileSize = new File(fileName.get(i)).length();
                    if (fileSize <= 0 || fileSize > (5 * 1024 * 1024)) {
                        return false;
                    }
                    InputStream inputStream = new FileInputStream(new File(fileName.get(i)));
                    client.storeFile(lStr, inputStream);
                    String fixedPath = lStr.substring(0, lStr.lastIndexOf("/") + 1 + 14);// 14 mean: yyyymmddhhmmss
                    System.out.println("After rename: " + lStr.replace(lStr.substring(fixedPath.length() + 1, lStr.lastIndexOf(".")), channelCode));
                    client.rename(lStr, lStr.replace(lStr.substring(fixedPath.length() + 1, lStr.lastIndexOf(".")), channelCode));
                }
                //end.
                // close connection
                client.logout();
            } else {
                client.disconnect();
                System.err.println("FTP server refused connection !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            client.disconnect();
        }
        return true;
    }

    /**
     * author tannh date create : 12/11/2017
     *
     * @return Ham lay file tu tren server ve local may
     *
     */
    public static String retrieveListFileFromFTPServer(String host, String username, String password, List<String> fileName, String destinationDir, String tempDir) throws Exception {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        String fileNameDir = "";
        try {

            // connect, login, set timeout
            client.connect(host);
            client.login(username, password);

            // set transfer type
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
            // set timeout
            client.setSoTimeout(60000); // default 1 minute

            // check connection
            int reply = client.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                // switch to working folder
                client.changeWorkingDirectory("CONTRACT_CHANNEL");
//                client.changeWorkingDirectory(destinationDir);
                // create temp file on temp directory
                for (int i = 0; i < fileName.size(); i++) {
                    // retrieve file from server and logout
                    String[] filesrc = fileName.get(i).split("\\/", 5);
                    ///u01/scan_doc/CONTRACT_CHANNEL/20171122104349_tony luis.rar.zip
                    File tempFile = new File(tempDir + filesrc[3] + "/" + filesrc[4]);
                    boolean success = (new File(tempFile.getParent())).mkdirs();
                    if (!success) {
                        File file = new File(tempDir + filesrc[3] + "/" + filesrc[4]);
                        file.createNewFile();
                    }
                    OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));

                    if (client.retrieveFile(filesrc[4], outputStream)) {
                        outputStream.close();
                    }
                }
                // close connection
                client.logout();
            } else {
                client.disconnect();
                System.err.println("FTP server refused connection !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            client.disconnect();
        }
        return fileNameDir;
    }

    /**
     * author tannh date create : 12/11/2017
     *
     * @return Ham lay file tu tren server ve local may
     *
     */
    public static String retrieveListFileFromFTPServerAutoGen(String host, String username, String password, List<String> fileName, String destinationDir, String tempDir) throws Exception {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        String fileNameDir = "";
        String[] ary = destinationDir.split("/");
        try {

            // connect, login, set timeout
            client.connect(host);
            client.login(username, password);

            // set transfer type
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
            // set timeout
            client.setSoTimeout(60000); // default 1 minute

            // check connection
            int reply = client.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                // switch to working folder
                client.changeWorkingDirectory(ary[3]);
                client.changeWorkingDirectory(ary[4]);
                // create temp file on temp directory
                for (int i = 0; i < fileName.size(); i++) {
                    // retrieve file from server and logout
                    String[] filesrc = fileName.get(i).split("\\/", 6);
                    ///u01/scan_doc/CONTRACT_CHANNEL/20171122104349_tony luis.rar.zip
                    File tempFile = new File(tempDir + filesrc[3] + "\\" + filesrc[4] + "\\" + filesrc[5]);
                    boolean success = (new File(tempFile.getParent())).mkdirs();
                    if (!success) {
                        File file = new File(tempDir + filesrc[3] + "\\" + filesrc[4] + "\\" + filesrc[5]);
                        file.createNewFile();
                    }
                    OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));

                    if (client.retrieveFile(filesrc[5], outputStream)) {
                        outputStream.close();
                    }
                }
                // close connection
                client.logout();
            } else {
                client.disconnect();
                System.err.println("FTP server refused connection !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            client.disconnect();
        }
        return fileNameDir;
    }

    public String findStaff()
            throws Exception {
        HttpServletRequest req = getRequest();
        String shopCode = this.addStaffCodeCTVDBForm.getShopCodeSearch();
        String pointOfSale = this.addStaffCodeCTVDBForm.getSearchPointOfSale();
        String staffCode = this.addStaffCodeCTVDBForm.getOwnercode();
        String staffManagementCode = this.addStaffCodeCTVDBForm.getStaffMagCodeSearch();
        Long objectType = this.addStaffCodeCTVDBForm.getObjectType();
        Long staffStatus = this.addStaffCodeCTVDBForm.getStatusSearch();
        Long staffOwnerId = Long.valueOf(0L);
        staffOwnerId = getStaffId(staffManagementCode);
        if ((staffManagementCode != null) && (!staffManagementCode.equals("")) && (staffOwnerId.equals(Long.valueOf(0L)))) {
            req.setAttribute("messageUpdate", "ERR.CHL.088");
            return "listChangeInfoStaff";
        }

        Long shopId = getShopId(shopCode);
        if (shopId.equals(Long.valueOf(0L))) {
            req.setAttribute("messageUpdate", "ERR.CHL.083");
            return "listChangeInfoStaff";
        }
        setTabSession("changeStatus", "true");

        String sql = "";
        List listParameter = new ArrayList();
        sql = "SELECT a.staff_id as staffId  ,a.staff_code as staffCode, a.NAME as name, a.id_no as idNo, (select name from channel_type where channel_type.channel_type_id = a.channel_type_id) as nameChannelType, stm.staff_code as nameManagement,  decode(a.status,0,'Cancel',1,'Active', 2, 'Inactive') as statusName, sh.shop_code as shopCode FROM staff a, staff stm, shop sh  WHERE 1 = 1 AND a.shop_id = sh.shop_id AND a.staff_owner_id = stm.staff_id(+) ";

        sql = sql + " and a.shop_id in (select shop_id from v_shop_tree where root_id = ? ) and a.channel_type_id in (select channel_type_id from channel_type where channel_type.status = ? and channel_type.is_Private = ? and channel_type.is_Vt_Unit = ?) and  lower(a.staff_Code) like ? ";

        listParameter.add(shopId);

        listParameter.add(Constant.STATUS_USE);
        listParameter.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        listParameter.add("2");
        listParameter.add(staffCode.toLowerCase().trim() + "%");
        if (!staffOwnerId.equals(Long.valueOf(0L))) {
            sql = sql + " and a.staff_owner_id = ? ";
            listParameter.add(staffOwnerId);
        }

        if ((pointOfSale != null) && (!pointOfSale.equals(""))) {
            sql = sql + " and a.channel_type_id = ? ";
            listParameter.add(Long.valueOf(Long.parseLong(pointOfSale)));
        }

        if (objectType != null) {
            if (objectType.equals(Long.valueOf(1L))) {
                sql = sql + " and a.staff_owner_id is null ";
            } else {
                sql = sql + " and a.staff_owner_id is not null ";
            }

        }

        if (staffStatus != null) {
            if (staffStatus.equals(Constant.STAFF_STATUS_ACTIVE)) {
                sql = sql + " and a.status = ? ";
                listParameter.add(Constant.STAFF_STATUS_ACTIVE);
            } else if (staffStatus.equals(Constant.STAFF_STATUS_INACTIVE)) {
                sql = sql + " and (a.status = ?) ";
                listParameter.add(Constant.STAFF_STATUS_INACTIVE);
            } else {
                sql = sql + " and (a.status is null or a.status = ?) ";
                listParameter.add(Constant.STAFF_STATUS_DELETE);
            }

        }

        sql = sql + " and rownum <=1000 ";
        sql = sql + " order by a.staff_code asc ";
        List list = new ArrayList();
        Query queryObject = getSession().createSQLQuery(sql).addScalar("staffId", Hibernate.LONG).addScalar("staffCode", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("idNo", Hibernate.STRING).addScalar("nameChannelType", Hibernate.STRING).addScalar("nameManagement", Hibernate.STRING).addScalar("statusName", Hibernate.STRING).addScalar("shopCode", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewStaffBean.class));
        for (int i = 0; i < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));
        }
        list = queryObject.list();
        setTabSession("listStaff", list);

        if (!afterUpdateSuccess) {
            resetForm();
        }
        afterUpdateSuccess = false;

        return "listChangeInfoStaff";
    }

    public String cancel() {
        resetForm();
        setTabSession("changeStatus", "true");
        setTabSession("changeInfo", "true");
        return "listChangeInfoStaff";
    }

    private String validateUpdate(Long staffId, Long staffOwnerId)
            throws Exception {
        if (!checkStockDeposit(staffId)) {
            return "err.staff.haveStockDeposit";
        }

        if (!checkAccountBalance(staffId)) {
            return "err.staff.haveAccountBalance";
        }

        if (!checkTransNotInvoice(staffId)) {
            return "err.staff.haveTransNotInvoice";
        }
        if (!checkTransNotInvoiceRetail(staffOwnerId, staffId)) {
            return "err.staff.haveTransNotInvoiceRetail";
        }
        return "";
    }

    private boolean checkStockDeposit(Long staffId)
            throws Exception {
        try {
            String query = " from StockTotal where status = 1      and id.ownerId = ?      and id.ownerType = ?      and quantityIssue > 0 ";

            Query q = getSession().createQuery(query);

            q.setParameter(0, staffId);
            q.setParameter(1, Constant.OWNER_TYPE_STAFF);
            List lst = q.list();

            return (lst == null) || (lst.size() <= 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkAccountBalance(Long staffId) {
        try {
            String query = " from AccountBalance where status = 1  and real_balance >0   and accountId =  (     select accountId from AccountAgent where status <> ?      and ownerId = ?      and ownerType = ?  )";

            Query q = getSession().createQuery(query);
            q.setParameter(0, Constant.ACCOUNT_AGENT_STATUS_DESTROY);
            q.setParameter(1, staffId);
            q.setParameter(2, Constant.OWNER_TYPE_STAFF);
            List lst = q.list();

            return (lst == null) || (lst.size() <= 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkTransNotInvoiceRetail(Long staffOwnerId, Long staffId)
            throws Exception {
        try {
            String query = " from SaleTrans where  saleTransType = ?  and staffId = ?  and receiverId = ?  and status = ? ";

            Query q = getSession().createQuery(query);
            q.setParameter(0, "7");
            q.setParameter(1, staffOwnerId);
            q.setParameter(2, staffId);
            q.setParameter(3, "2");
            List lst = q.list();

            return (lst == null) || (lst.size() <= 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkTransNotInvoice(Long staffId)
            throws Exception {
        try {
            String query = " from SaleTrans where  saleTransType = ?  and staffId = ?  and  status = ? ";

            Query q = getSession().createQuery(query);
            q.setParameter(0, "9");
            q.setParameter(1, staffId);
            q.setParameter(2, "2");
            List lst = q.list();

            return (lst == null) || (lst.size() <= 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String updateInfo() throws Exception {
        Session smSession = getSession();
        Session cmPreSession = getSession("cm_pre");
        ResponseWallet responseWallet = new ResponseWallet();
        ResponseWallet responseWalletActive = new ResponseWallet();
        try {
            getReqSession();

            UserToken userToken = (UserToken) this.reqSession.getAttribute("userToken");
            List lstLogObj = new ArrayList();
            Long staffId = this.addStaffCodeCTVDBForm.getStaffId();
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(smSession);

            Staff staff = staffDAO.findById(staffId);
            if (staff == null) {
                this.req.setAttribute("messageUpdate", "ERR.CHL.107");
                return "listChangeInfoStaff";
            }

            boolean checkUpdate = true;

            ChannelType channelTypeChild = new ChannelTypeDAO().findById(staff.getChannelTypeId());
            if ((channelTypeChild != null) && (channelTypeChild.getRoleCreateChannel() != null) && (!channelTypeChild.getRoleCreateChannel().trim().equals(""))) {
                checkUpdate = AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(channelTypeChild.getRoleCreateChannel().trim()), this.req);
            }

            if (!checkUpdate) {
                this.req.setAttribute("messageUpdate", "E.200043");
                return "listChangeInfoStaff";
            }

            String idNo = this.addStaffCodeCTVDBForm.getIdNo();
            Long channelTypeId = staff.getChannelTypeId();
            if ((idNo == null) || (idNo.trim().equals(""))) {
                this.req.setAttribute("messageUpdate", "ERR.STAFF.0025");
                return "listChangeInfoStaff";
            }

            if (!CommonDAO.checkValidIdNo(idNo.trim())) {
                this.req.setAttribute("messageUpdate", "E.100005");
            }

            if (new ChannelDAO().checkExistIdNoWithStaff(idNo.trim(), channelTypeId, staff.getStaffId())) {
                Staff existsIdNoStaff = new ChannelDAO().getExistIdNoWithStaff(idNo.trim(), channelTypeId, staff.getStaffId());
                if ((existsIdNoStaff != null) && (existsIdNoStaff.getStaffCode() != null)) {
                    this.req.setAttribute("messageUpdate", getText("ERR.STAFF.0040") + " (staff_code = " + existsIdNoStaff.getStaffCode() + ")");
                    return "listChangeInfoStaff";
                }

                this.req.setAttribute("messageUpdate", "ERR.STAFF.0040");
                return "listChangeInfoStaff";
            }
            //Check quyen Active kenh
            if (this.addStaffCodeCTVDBForm.getStatus() != null
                    && this.addStaffCodeCTVDBForm.getStatus().equals(1L)
                    && staff.getStatus() != null && staff.getStatus().equals(2L)) {
                if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("ChangeISDNChannelWallet"), req)) {
                    this.req.setAttribute("messageUpdate", "ERR.Account.Have.Not.Permit");
                    return "listChangeInfoStaff";
                }
            }
            //Check trung so dien thoai
            String tel = this.addStaffCodeCTVDBForm.getTel();
            tel = tel == null ? "" : tel.trim();
            if (!tel.equals("")) {
                if (new ChannelDAO().checkExistTelWithStaff(tel, staff.getStaffId())) {
                    Staff existsTelStaff = new ChannelDAO().getExistTelWithStaff(tel, staff.getStaffId());
                    if ((existsTelStaff != null) && (existsTelStaff.getStaffCode() != null)) {
                        this.req.setAttribute("messageUpdate", getText("ERR.STAFF.0048") + " (staff_code = " + existsTelStaff.getStaffCode() + ")");
                        return "listChangeInfoStaff";
                    }
                }
            }
            Long staffOwnerId = getStaffId(this.addStaffCodeCTVDBForm.getStaffManagementCode());
            if (staffOwnerId.equals(Long.valueOf(0L))) {
                this.req.setAttribute("messageUpdate", "ERR.CHL.088");
                return "listChangeInfoStaff";
            }
            Staff staffManagement = staffDAO.findById(staffOwnerId);

            if (staffManagement.getChannelTypeId() == null) {
                this.req.setAttribute("messageUpdate", "ERR.CHL.090");
                return "listChangeInfoStaff";
            }
            ChannelType channelType = new ChannelTypeDAO().findById(staffManagement.getChannelTypeId());
            if ((channelType == null) || (channelType.getChannelTypeId() == null) || (!channelType.getIsVtUnit().equals("1")) || (!channelType.getIsPrivate().equals(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE)) || (!channelType.getObjectType().equals("2"))) {
                this.req.setAttribute("messageUpdate", "ERR.CHL.090");
                return "listChangeInfoStaff";
            }

            if (this.addStaffCodeCTVDBForm.getStaffName().toUpperCase().trim().equals("EMPTY NAME")) {
                this.req.setAttribute("messageUpdate", "ERR.CHL.112");
                return "listChangeInfoStaff";
            }

            if ((this.addStaffCodeCTVDBForm.getProvinceCode() == null) || (this.addStaffCodeCTVDBForm.getProvinceCode().trim().equals(""))) {
                this.req.setAttribute("messageUpdate", "ERR.SIK.006");
                return "listChangeInfoStaff";
            }
            if ((this.addStaffCodeCTVDBForm.getDistrictCode() == null) || (this.addStaffCodeCTVDBForm.getDistrictCode().trim().equals(""))) {
                this.req.setAttribute("messageUpdate", "ERR.SIK.007");
                return "listChangeInfoStaff";
            }
            if ((this.addStaffCodeCTVDBForm.getPrecinctCode() == null) || (this.addStaffCodeCTVDBForm.getPrecinctCode().trim().equals(""))) {
                this.req.setAttribute("messageUpdate", "ERR.SIK.008");
                return "listChangeInfoStaff";
            }
            Area provinceArea = getArea(this.addStaffCodeCTVDBForm.getProvinceCode().trim());
            if (provinceArea == null) {
                this.req.setAttribute("messageUpdate", "ERR.SIK.152");
                return "listChangeInfoStaff";
            }
            Area districtArea = getArea(this.addStaffCodeCTVDBForm.getProvinceCode().trim() + this.addStaffCodeCTVDBForm.getDistrictCode().trim());
            if (districtArea == null) {
                this.req.setAttribute("messageUpdate", "ERR.SIK.153");
                return "listChangeInfoStaff";
            }
            Area precinctArea = getArea(this.addStaffCodeCTVDBForm.getProvinceCode().trim() + this.addStaffCodeCTVDBForm.getDistrictCode().trim() + this.addStaffCodeCTVDBForm.getPrecinctCode().trim());
            if (precinctArea == null) {
                this.req.setAttribute("messageUpdate", "ERR.SIK.154");
                return "listChangeInfoStaff";
            }
            if ((staffManagement != null) && (staffManagement.getProvince() != null) && (provinceArea != null) && (!staffManagement.getProvince().trim().equals(provinceArea.getProvince()))) {
                this.req.setAttribute("messageUpdate", "ERR.STAFF.0030");
                return "listChangeInfoStaff";
            }

            String discountPolicy = "";
            String pricePolicy = "";

            if (channelTypeChild != null) {
                if ((staff.getDiscountPolicy() == null) || (staff.getDiscountPolicy().trim().equals(""))) {
                    discountPolicy = channelTypeChild.getDiscountPolicyDefault();
                } else {
                    discountPolicy = staff.getDiscountPolicy().trim();
                }
                if ((staff.getPricePolicy() == null) || (staff.getPricePolicy().trim().equals(""))) {
                    pricePolicy = channelTypeChild.getPricePolicyDefault();
                } else {
                    pricePolicy = staff.getPricePolicy().trim();
                }

            }

            Shop shop = getShop(this.addStaffCodeCTVDBForm.getShopCode());
            if (shop == null) {
                this.req.setAttribute("messageUpdate", "ERR.SHOP.shopNotNull");
                return "listChangeInfoStaff";
            }
            if (!staffManagement.getShopId().equals(shop.getShopId())) {
                this.req.setAttribute("messageUpdate", "ERR.StaffManager.staffNotInShop");
                return "listChangeInfoStaff";
            }

            Long oldShopId = this.addStaffCodeCTVDBForm.getOldShopId();
            Long oldStaffOwnerId = this.addStaffCodeCTVDBForm.getOldStaffOwnerId();
            System.out.println("oldShopId=" + oldShopId);
            System.out.println("oldStaffOwnerId=" + oldStaffOwnerId);
            System.out.println("shop.getShopId=" + shop.getShopId());
            if ((oldShopId == null) || (oldStaffOwnerId == null) || (shop.getShopId() == null)) {
                this.req.setAttribute("messageUpdate", "ERR.StaffManager.staffNotInShop");
                return "listChangeInfoStaff";
            }
            if ((!oldShopId.equals(shop.getShopId())) || (!oldStaffOwnerId.equals(staffOwnerId))) {
                String errValidateUpdate = validateUpdate(staffId, oldStaffOwnerId);
                if ((errValidateUpdate != null) && (!"".equals(errValidateUpdate))) {
                    this.req.setAttribute("messageUpdate", errValidateUpdate);
                    return "listChangeInfoStaff";
                }
            }

            //vi dien tu
            boolean isLock = false;
            String channelWallet = this.addStaffCodeCTVDBForm.getChannelWallet();
            String parentCodeWallet = this.addStaffCodeCTVDBForm.getParentCodeWallet();
            String isdnWallet = this.addStaffCodeCTVDBForm.getIsdnWallet();
            channelWallet = channelWallet == null ? "" : channelWallet.trim();
            parentCodeWallet = parentCodeWallet == null ? "" : parentCodeWallet.trim();
            isdnWallet = isdnWallet == null ? "0" : isdnWallet.trim();
            if (staff.getChannelWallet() != null) {
                if (this.addStaffCodeCTVDBForm.getStatus() != null
                        && this.addStaffCodeCTVDBForm.getStatus().equals(2L)) {
                    //LinhNBV start modified on May 24 2018: Check isdnWallet: If exist --> will call WS eMola.
                    if (!isdnWallet.equals("")) {
                        //DINHDC ADD 20160524
                        //Thuc hien huy thue bao vi
                        String dob = "";
                        String idIssueDate = "";
                        String parentIdWallet = "";
                        String strShopId = "";
                        if (staff.getStatus() != null && staff.getStatus().equals(2L)) {
                            this.req.setAttribute("messageUpdate", "Channel.Wallet.Canceled");
                            return "listChangeInfoStaff";
                        }
                        if (this.addStaffCodeCTVDBForm.getBirthday() != null) {
                            dob = DateTimeUtils.convertDateTimeToString(this.addStaffCodeCTVDBForm.getBirthday(), "ddMMyyyy");
                            //dob = this.addStaffCodeCTVDBForm.getBirthday().toString();
                        }
                        if (this.addStaffCodeCTVDBForm.getMakeDate() != null) {
                            idIssueDate = DateTimeUtils.convertDateTimeToString(DateTimeUtils.convertStringToDate(this.addStaffCodeCTVDBForm.getMakeDate()), "ddMMyyyy");
                            //idIssueDate = DateTimeUtils.convertStringToDate(this.addStaffCodeCTVDBForm.getMakeDate()).toString();
                        }
                        if (staff.getParentIdWallet() != null) {
                            parentIdWallet = staff.getParentIdWallet().toString();
                        }
                        if (staff.getShopId() != null) {
                            strShopId = staff.getShopId().toString();
                        }
                        isLock = true;
                        String response = functionChannelWallet(smSession, isdnWallet, this.addStaffCodeCTVDBForm.getStaffName(), "1",
                                dob, "1", idNo, addStaffCodeCTVDBForm.getAddress(), "3", addStaffCodeCTVDBForm.getChannelWallet(), staff.getStaffId().toString(), parentIdWallet,
                                this.addStaffCodeCTVDBForm.getMakeAddress(), idIssueDate, staff.getStaffId().toString(), strShopId, addStaffCodeCTVDBForm.getVoucherCode());
                        System.out.println("response deleteSubPre:" + response);
                        if ("ERROR".equals(response)) {
                            this.req.setAttribute("messageUpdate", "Error.Call.WS.Wallet");
                            return "listChangeInfoStaff";
                        } else {
                            Gson gson = new Gson();
                            responseWallet = gson.fromJson(response, ResponseWallet.class);
                            if (responseWallet == null || responseWallet.getResponseCode() == null) {
                                insertLogCallWsWallet(smSession, isdnWallet, staff.getStaffId(), "1", 0L, 0L, getSysdate(), "Response Error:" + response,
                                        staff.getName(), staff.getBirthday(), idNo, channelWallet, this.addStaffCodeCTVDBForm.getParentIdWallet(), staff.getIdIssuePlace(), staff.getIdIssueDate());
                                this.req.setAttribute("messageUpdate", "Error.Call.WS.Wallet");
                                return "listChangeInfoStaff";
                            }
                        }
                    }
                } else {
                    //Thay doi thong tin va ma kenh vi cha
                    //LinhNBV comment code.
//                    if (isdnWallet.equals("")) {
//                        this.req.setAttribute("messageUpdate", "err.input.isdnWallet");
//                        return "listChangeInfoStaff";
//                    }
                    //LinhNBV start modified on May 24 2018: Check isdnWallet: If exist --> will call WS eMola.
                    if (!isdnWallet.equals("")) {
                        //Thuc hien thay doi thong tin kenh bao vi
                        String dob = "";
                        String idIssueDate = "";
                        String parentIdWallet = "";
                        String strShopId = "";
                        if (this.addStaffCodeCTVDBForm.getBirthday() != null) {
                            dob = DateTimeUtils.convertDateTimeToString(this.addStaffCodeCTVDBForm.getBirthday(), "ddMMyyyy");
                            //dob = this.addStaffCodeCTVDBForm.getBirthday().toString();
                        }
                        if (this.addStaffCodeCTVDBForm.getMakeDate() != null) {
                            idIssueDate = DateTimeUtils.convertDateTimeToString(DateTimeUtils.convertStringToDate(this.addStaffCodeCTVDBForm.getMakeDate()), "ddMMyyyy");
                            //idIssueDate = DateTimeUtils.convertStringToDate(this.addStaffCodeCTVDBForm.getMakeDate()).toString();
                        }
                        if (staff.getShopId() != null) {
                            strShopId = staff.getShopId().toString();
                        }
                        if (!channelWallet.equals("SA")) {
                            Staff parentWallet = staffDAO.findParentWalletByStaffCode(parentCodeWallet, channelWallet);
                            if (parentWallet != null && parentWallet.getStaffId() != null) {
                                this.addStaffCodeCTVDBForm.setParentIdWallet(parentWallet.getStaffId());
                                parentIdWallet = parentWallet.getStaffId().toString();
                            }
                        }
                        String response = functionChannelWallet(smSession, staff.getIsdnWallet(), this.addStaffCodeCTVDBForm.getStaffName(), "1",
                                dob, "1", idNo, addStaffCodeCTVDBForm.getAddress(), "2", staff.getChannelWallet(), staff.getStaffId().toString(), parentIdWallet,
                                this.addStaffCodeCTVDBForm.getMakeAddress(), idIssueDate, staff.getStaffId().toString(), strShopId, addStaffCodeCTVDBForm.getVoucherCode());
                        System.out.println("response update info:" + response);
                        if ("ERROR".equals(response)) {
                            this.req.setAttribute("messageUpdate", "Error.Call.WS.Wallet");
                            return "listChangeInfoStaff";
                        } else {
                            Gson gson = new Gson();
                            responseWallet = gson.fromJson(response, ResponseWallet.class);
                            if (responseWallet == null || responseWallet.getResponseCode() == null) {
                                insertLogCallWsWallet(smSession, isdnWallet, staff.getStaffId(), "1", 0L, 0L, getSysdate(), "Response Error:" + response,
                                        staff.getName(), staff.getBirthday(), idNo, channelWallet, this.addStaffCodeCTVDBForm.getParentIdWallet(), staff.getIdIssuePlace(), staff.getIdIssueDate());
                                this.req.setAttribute("messageUpdate", "Error.Call.WS.Wallet");
                                return "listChangeInfoStaff";
                            }
                        }
                    }

                }
            } else {
                if (!channelWallet.equals("")) {
                    //LinhNBV comment code.
//                    if (isdnWallet.equals("")) {
//                        this.req.setAttribute("messageUpdate", "err.input.isdnWallet");
//                        return "listChangeInfoStaff";
//                    }
                    //LinhNBV start modified on May 24 2018: Check isdnWallet: If exist --> will call WS eMola.
                    if (!isdnWallet.equals("")) {
                        //Phan quyen chi cap cong ty va chi nhanh moi tao kenh vi SA, MA
                        Shop shopUser = getShop(userToken.getShopCode());
                        if ("SA".equals(channelWallet) || "MA".equals(channelWallet)) {
                            if (!shopUser.getShopId().equals(Constant.VT_SHOP_ID) && !shopUser.getParentShopId().equals(Constant.VT_SHOP_ID)) {
                                this.req.setAttribute("messageUpdate", "err.right.create.channel");
                                return "listChangeInfoStaff";
                            }
                        }
                        if (this.addStaffCodeCTVDBForm.getStatus() != null
                                && this.addStaffCodeCTVDBForm.getStatus().equals(2L)) {
                            this.req.setAttribute("messageUpdate", "Channel.Wallet.Canceled");
                            return "listChangeInfoStaff";
                        }
                        if (!channelWallet.equals("SA")) {
                            Staff parentWallet = staffDAO.findParentWalletByStaffCode(parentCodeWallet, channelWallet);
//                    if (parentWallet == null) {
//                        r.setAttribute("messageAdd", "err.channel.wallet.parent");
//                        return this.pageForward;
//                    }
//                    this.addStaffCodeCTVDBForm.setParentIdWallet(parentWallet.getStaffId());
                            if (parentWallet != null) {
                                this.addStaffCodeCTVDBForm.setParentIdWallet(parentWallet.getStaffId());
                            }
                        }
                        String sql = "from Staff where isdnWallet = ? and status != ? and staffCode != ?";
                        Query query = getSession().createQuery(sql);
                        query.setParameter(0, isdnWallet);
                        query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
                        // tannh 20180809 fix loi trung voi chinh staff code
                        query.setParameter(2, this.addStaffCodeCTVDBForm.getStaffCode());
                        List<Staff> listStaff = query.list();
                        if (listStaff != null && listStaff.size() > 0) {
                            this.req.setAttribute("messageUpdate", getText("err.isdn.wallet.duplicate") + " (staff_code = " + listStaff.get(0).getStaffCode() + ")");
                            return "listChangeInfoStaff";
                        }

                        Byte subType = 1;
                        sql = "From StockIsdnMobile where to_number(isdn) = ?";
                        BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(isdnWallet));
                        query = getSession().createQuery(sql);
                        query.setParameter(0, isdnSearch);
                        List<StockIsdnMobile> list = query.list();
                        if (list != null && list.size() > 0) {
                            if (list.get(0).getIsdnType() != null) {
                                subType = Byte.valueOf(list.get(0).getIsdnType());
                            }
                        } else {
                            this.req.setAttribute("messageUpdate", "err.isdn.wallet");
                            return "listChangeInfoStaff";
                        }

                        InterfaceCm inter = new InterfaceCm();
                        Object subInfo;
                        subInfo = inter.getSubscriberInfoV2(isdnSearch.toString(), "M", subType);
                        if (subInfo == null) {
                            this.req.setAttribute("messageUpdate", "err.isdn.wallet");
                            return "listChangeInfoStaff";
                        }
                        if (subType == 1) {
                            com.viettel.bccs.cm.database.BO.pre.SubMb subMb = (com.viettel.bccs.cm.database.BO.pre.SubMb) subInfo;
                            if (subMb.getActStatus() != null && (subMb.getActStatus().equals("01")
                                    || subMb.getActStatus().equals("10") || subMb.getActStatus().equals("02")
                                    || subMb.getActStatus().equals("20"))) {
                                this.req.setAttribute("messageUpdate", "err.isdn.wallet.blocked");
                                return "listChangeInfoStaff";
                            } else if (subMb.getActStatus() != null && subMb.getActStatus().equals("03")) {
                                this.req.setAttribute("messageUpdate", "err.isdn.wallet.inActive");
                                return "listChangeInfoStaff";
                            } else if (subMb.getActStatus() == null) {
                                this.req.setAttribute("messageUpdate", "err.isdn.wallet");
                                return "listChangeInfoStaff";
                            }
                        }
                        //Thuc hien Active kenh vi
                        String dob = "";
                        String idIssueDate = "";
                        String parentIdWallet = "";
                        String strShopId = "";
                        if (this.addStaffCodeCTVDBForm.getBirthday() != null) {
                            dob = DateTimeUtils.convertDateTimeToString(this.addStaffCodeCTVDBForm.getBirthday(), "ddMMyyyy");
                            //dob = this.addStaffCodeCTVDBForm.getBirthday().toString();
                        }
                        if (this.addStaffCodeCTVDBForm.getMakeDate() != null) {
                            idIssueDate = DateTimeUtils.convertDateTimeToString(DateTimeUtils.convertStringToDate(this.addStaffCodeCTVDBForm.getMakeDate()), "ddMMyyyy");
                            //idIssueDate = DateTimeUtils.convertStringToDate(this.addStaffCodeCTVDBForm.getMakeDate()).toString();
                        }
                        if (staff.getParentIdWallet() != null) {
                            parentIdWallet = staff.getParentIdWallet().toString();
                        }
                        if (staff.getShopId() != null) {
                            strShopId = staff.getShopId().toString();
                        }
                        String response = functionChannelWallet(smSession, isdnWallet, this.addStaffCodeCTVDBForm.getStaffName(), "1",
                                dob, "1", idNo, addStaffCodeCTVDBForm.getAddress(), "1", addStaffCodeCTVDBForm.getChannelWallet(), staff.getStaffId().toString(), parentIdWallet,
                                this.addStaffCodeCTVDBForm.getMakeAddress(), idIssueDate, staff.getStaffId().toString(), strShopId, addStaffCodeCTVDBForm.getVoucherCode());
                        System.out.println("response deleteSubPre:" + response);
                        if ("ERROR".equals(response)) {
                            this.req.setAttribute("messageUpdate", "Error.Call.WS.Wallet");
                            return "listChangeInfoStaff";
                        } else {
                            Gson gson = new Gson();
                            responseWalletActive = gson.fromJson(response, ResponseWallet.class);
                            if (responseWalletActive == null || responseWalletActive.getResponseCode() == null) {
                                insertLogCallWsWallet(smSession, isdnWallet, staff.getStaffId(), "1", 0L, 0L, getSysdate(), "Response Error:" + response,
                                        staff.getName(), staff.getBirthday(), idNo, channelWallet, this.addStaffCodeCTVDBForm.getParentIdWallet(), staff.getIdIssuePlace(), staff.getIdIssueDate());
                                this.req.setAttribute("messageUpdate", "Error.Call.WS.Wallet");
                                return "listChangeInfoStaff";
                            }
                        }
                    }
                }

            }
            Staff oldStaff = new Staff();
            BeanUtils.copyProperties(oldStaff, staff);
            String actionType = "";
            String message = "";
            if ((staff.getStaffOwnerId() != null) && (!staff.getStaffOwnerId().equals(Long.valueOf(0L)))) {
                actionType = "ACTION_CHANGE_INFO_STAFF";
                message = "Update information of Channel";
            } else {
                actionType = "ACTION_CHANGE_INFO_STAFF";
                message = "Update information of Empty Channel";
            }
            //Hien thi ket qua thuc hien huy vi
            if (responseWallet != null && responseWallet.getResponseCode() != null) {
                if (responseWallet.getResponseCode().equals("01")) {
                    this.req.setAttribute("messageUpdate", "ERR.CHL.116");
                    //huy vi dien tu
                    if (isLock) {
                        staff.setChannelWallet(null);
                        staff.setParentIdWallet(null);
                        staff.setIsdnWallet(null);
                    } else {
                        staff.setParentIdWallet(this.addStaffCodeCTVDBForm.getParentIdWallet());
                    }
                } else {
                    if (isLock) {
                        if (responseWallet.getResponseMessage() != null) {
                            this.req.setAttribute("messageUpdate", responseWallet.getResponseMessage());
                        } else {
                            this.req.setAttribute("messageUpdate", "ERR.DELETE.CHANNEL.WALLET.UNSUCCESS");
                        }
                        insertLogCallWsWallet(smSession, staff.getIsdnWallet(), staff.getStaffId(), "3", 0L, 0L, getSysdate(), responseWallet.getResponseMessage(),
                                staff.getName(), staff.getBirthday(), idNo, staff.getChannelWallet(), staff.getParentIdWallet(), staff.getIdIssuePlace(), staff.getIdIssueDate());
                        return "listChangeInfoStaff";
                    } else {
                        if (responseWallet.getResponseMessage() != null) {
                            this.req.setAttribute("messageUpdate", responseWallet.getResponseMessage());
                        } else {
                            this.req.setAttribute("messageUpdate", "ERR.UPDATE.INFO.WALLET.UNSUCCESS");
                        }
                        insertLogCallWsWallet(smSession, staff.getIsdnWallet(), staff.getStaffId(), "2", 0L, 0L, getSysdate(), responseWallet.getResponseMessage(),
                                this.addStaffCodeCTVDBForm.getStaffName(), staff.getBirthday(), idNo, staff.getChannelWallet(), this.addStaffCodeCTVDBForm.getParentIdWallet(), staff.getIdIssuePlace(), staff.getIdIssueDate());
                        return "listChangeInfoStaff";
                    }
                }
            } else if (responseWalletActive != null && responseWalletActive.getResponseCode() != null) {
                if (responseWalletActive.getResponseCode().equals("01")) {
                    this.req.setAttribute("messageUpdate", "ERR.CHL.116");
                    staff.setChannelWallet(channelWallet);
                    staff.setParentIdWallet(this.addStaffCodeCTVDBForm.getParentIdWallet());
                    staff.setIsdnWallet(isdnWallet);
                } else {
                    if (responseWalletActive.getResponseMessage() != null) {
                        this.req.setAttribute("messageUpdate", responseWalletActive.getResponseMessage());
                    } else {
                        this.req.setAttribute("messageUpdate", "ERR.ACTIVE.CHANNEL.WALLET.UNSUCCESS");
                    }
                    insertLogCallWsWallet(smSession, isdnWallet, staff.getStaffId(), "1", 0L, 0L, getSysdate(), responseWalletActive.getResponseMessage(),
                            staff.getName(), staff.getBirthday(), idNo, channelWallet, this.addStaffCodeCTVDBForm.getParentIdWallet(), staff.getIdIssuePlace(), staff.getIdIssueDate());
                    return "listChangeInfoStaff";
                }
            }
            //END
            if (shop.getShopId() != null) {
                staff.setShopId(shop.getShopId());
            }
//            lamnt file upload   
//&& ( addStaffCodeCTVDBForm.getIsChecked() == null || addStaffCodeCTVDBForm.getIsChecked() != 1)
            if (!addStaffCodeCTVDBForm.getImageUrl().equals("") && !addStaffCodeCTVDBForm.getImageUrl().startsWith("/u01/scan_doc/")) {
                String serverFilePath = this.addStaffCodeCTVDBForm.getImageUrl();
                String[] fileParth = serverFilePath.split(",");
                // tannh 11/23/2017 ; upload file to server 
                String host = ResourceBundleUtils.getResource("server_get_file_to_server_host");
                String userName = ResourceBundleUtils.getResource("server_get_file_to_server_username");
                String password = ResourceBundleUtils.getResource("server_get_file_to_server_password");
                String tempDir = ResourceBundleUtils.getResource("server_get_file_to_server_tempdir_upto_file");
                if (fileParth[0].length() == 0) {
                    addStaffCodeCTVDBForm.setImageUrl("");
                } else {
                    String lStr;
                    if (fileParth[0].contains("\\")) {
                        lStr = fileParth[0].substring(fileParth[0].lastIndexOf("\\"));
                    } else {
                        lStr = fileParth[0].substring(fileParth[0].lastIndexOf("/"));
                    }
                    lStr = tempDir + lStr.substring(1);
                    addStaffCodeCTVDBForm.setImageUrl(lStr);
                    List<String> lst = new ArrayList<String>();
                    lst.add(fileParth[0]);
                    boolean isUpload = uploadListFileFromFTPServer(host, userName, password, lst, tempDir, tempDir, addStaffCodeCTVDBForm.getStaffCode());
                    if (!isUpload) {
                        req.setAttribute("messageUpdate", "ERR.SAE.157");
                        return "listChangeInfoStaff";
                    }
                }
                //LinhNBV start modified on May 25 2018: Change ImageUrl correct.
                String fullPath = addStaffCodeCTVDBForm.getImageUrl();
                if (!"".equalsIgnoreCase(fullPath)) {
                    String fixedPath = fullPath.substring(0, fullPath.lastIndexOf("/") + 1 + 14);//14 mean: yyyymmddhhmmss
                    System.out.println("After rename: " + fullPath.replace(fullPath.substring(fixedPath.length() + 1, fullPath.lastIndexOf(".")), addStaffCodeCTVDBForm.getStaffCode()));
                    String imgUrl = fullPath.replace(fullPath.substring(fixedPath.length() + 1, fullPath.lastIndexOf(".")), addStaffCodeCTVDBForm.getStaffCode());
                    staff.setImageUrl(imgUrl);
                }
                //LinhNBV end.
            }
//            lamnt end

            staff.setStaffOwnerId(staffOwnerId);

            staff.setName(this.addStaffCodeCTVDBForm.getStaffName().trim());
            staff.setIdNo(this.addStaffCodeCTVDBForm.getIdNo().trim());
            staff.setIdIssueDate(DateTimeUtils.convertStringToDate(this.addStaffCodeCTVDBForm.getMakeDate()));
            staff.setIdIssuePlace(this.addStaffCodeCTVDBForm.getMakeAddress().trim());
            staff.setAddress(this.addStaffCodeCTVDBForm.getAddress().trim());

            staff.setPricePolicy(pricePolicy);
            staff.setDiscountPolicy(discountPolicy);

            staff.setStatus(this.addStaffCodeCTVDBForm.getStatus());

            staff.setProfileState(this.addStaffCodeCTVDBForm.getProfileState());
            staff.setBoardState(this.addStaffCodeCTVDBForm.getBoardState());
            staff.setBirthday(this.addStaffCodeCTVDBForm.getBirthday());

            staff.setProvince(provinceArea.getProvince());
            staff.setDistrict(districtArea.getDistrict());
            staff.setPrecinct(precinctArea.getPrecinct());
            staff.setStreetBlockName(this.addStaffCodeCTVDBForm.getStreetBlockName());
            staff.setStreetName(this.addStaffCodeCTVDBForm.getStreetName());
            staff.setHome(this.addStaffCodeCTVDBForm.getHome());

            staff.setAddress(this.addStaffCodeCTVDBForm.getAddress());

            staff.setUsedfulWidth(this.addStaffCodeCTVDBForm.getUsedfulWidth());
            staff.setSurfaceArea(this.addStaffCodeCTVDBForm.getSurfaceArea());
            //staff.setRegistryDate(this.addStaffCodeCTVDBForm.getRegistryDate());//khong cho sua truong nay            
            staff.setTradeName(this.addStaffCodeCTVDBForm.getTradeName());
            staff.setContactName(this.addStaffCodeCTVDBForm.getContactName());
            staff.setTel(this.addStaffCodeCTVDBForm.getTel());
            staff.setCheckVat(this.addStaffCodeCTVDBForm.getCheckVat());
            staff.setIsChecked(this.addStaffCodeCTVDBForm.getIsChecked());
            staff.setAgentType(this.addStaffCodeCTVDBForm.getAgentType());

            staff.setLastUpdateUser(userToken.getLoginName());
            staff.setLastUpdateTime(getSysdate());
            staff.setLastUpdateIpAddress(this.req.getRemoteAddr());
            staff.setNote(this.addStaffCodeCTVDBForm.getNote().trim());
            staff.setBtsCode(addStaffCodeCTVDBForm.getBtsCode());
            //Lay truong nay lam kenh Key de quan ly
            if (addStaffCodeCTVDBForm.getLastUpdateKey() != null && !addStaffCodeCTVDBForm.getLastUpdateKey().equals("")) {
                staff.setLastUpdateKey(addStaffCodeCTVDBForm.getLastUpdateKey());
            } else {
                staff.setLastUpdateKey("Normal");
            }
            //Them truong diem ban DKTT
            if (addStaffCodeCTVDBForm.getRegistrationPoint() != null && !addStaffCodeCTVDBForm.getRegistrationPoint().equals("")) {
                staff.setRegistrationPoint(addStaffCodeCTVDBForm.getRegistrationPoint());
            } else {
                staff.setRegistrationPoint(null);
            }
            smSession.update(staff);

            AccountAgent bo = checkMsisdn(staffId);
            if ((bo != null) && (bo.getStatus() != null) && (!bo.getStatus().equals(Constant.ACCOUNT_STATUS_ACTIVE)) && (!bo.getStatus().equals(Long.valueOf(4L)))) {
                cmPreSession.beginTransaction();
                STKSub stkSub = InterfaceCMToIM.getSTKSubscriberInformation(oldStaff.getIdNo(), cmPreSession);

                if ((stkSub != null) && (stkSub.getIsdn() != null)) {
                    if (!new SaleToCollaboratorDAO().regCust(2, userToken, staff, stkSub.getIsdn(), cmPreSession, this.req, "messageUpdate")) {
                        smSession.getTransaction().rollback();
                        smSession.clear();

                        cmPreSession.getTransaction().rollback();
                        cmPreSession.clear();

                        return "listChangeInfoStaff";
                    }
                } else if (!new SaleToCollaboratorDAO().regCust(1, userToken, staff, bo.getMsisdn(), cmPreSession, this.req, "messageUpdate")) {
                    smSession.getTransaction().rollback();
                    smSession.clear();

                    cmPreSession.getTransaction().rollback();
                    cmPreSession.clear();

                    return "listChangeInfoStaff";
                }

            }

            lstLogObj.add(new ActionLogsObj(oldStaff, staff));
            saveLog(actionType, message, lstLogObj, staff.getStaffId());

            insertStockOwnerTmp(staff.getStaffId(), Constant.OWNER_TYPE_STAFF.toString(), staff.getStaffCode(), staff.getName(), staff.getChannelTypeId());

            setTabSession("changeStatus", "true");
            setTabSession("changeInfo", "true");

            this.req.setAttribute("messageUpdate", "ERR.CHL.116");
            smSession.getTransaction().commit();
            smSession.flush();
            smSession.clear();

            cmPreSession.getTransaction().commit();
            cmPreSession.flush();
            cmPreSession.clear();

            afterUpdateSuccess = true;
            findStaff();

            return "listChangeInfoStaff";
        } catch (Exception ex) {
            this.log.debug("", ex);

            if ((smSession != null) && (smSession.isConnected())) {
                smSession.getTransaction().rollback();
                smSession.clear();
            }

            if ((cmPreSession != null) && (cmPreSession.isConnected())) {
                cmPreSession.getTransaction().rollback();
                cmPreSession.clear();
            }
        }

        return "listChangeInfoStaff";
    }

    public String convertChannel() throws Exception {
        Session smSession = getSession();
        Session cmPreSession = getSession("cm_pre");
        try {
            getReqSession();
            UserToken userToken = (UserToken) this.reqSession.getAttribute("userToken");
            List lstLogObj = new ArrayList();
            Long staffId = this.addStaffCodeCTVDBForm.getStaffId();
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(smSession);

            Staff staff = staffDAO.findById(staffId);
            if (staff == null || staff.getStatus() == 0) {
                this.req.setAttribute("messageUpdate", "ERR.CHL.107");
                return "listChangeInfoStaff";
            }
            Shop shop = getShop(this.addStaffCodeCTVDBForm.getShopCode());
            String province = shop.getProvince();
            Area provinceArea = null;
            ChannelDAO channelDAO = new ChannelDAO();
            provinceArea = channelDAO.getArea(province);
            Long converChanel = this.addStaffCodeCTVDBForm.getConverChanel();
            if (converChanel == null || converChanel <= 0) {
                this.req.setAttribute("messageUpdate", "You must choose convert channel!!!");
                return "listChangeInfoStaff";
            };

            if (1000485L == converChanel) {
                if (!staff.getChannelTypeId().equals(1000522L)) {
                    this.req.setAttribute("messageUpdate", staff.getStaffCode() + " isn't belong to Super Agent!! ");
                    return "listChangeInfoStaff";
                }
            }

            if (staff.getChannelTypeId().equals(converChanel)) {
                this.req.setAttribute("messageUpdate", "Can't convert same channel!!!");
                return "listChangeInfoStaff";
            };
            DbProcessor db = new DbProcessor();
//            String message = ResourceBundle.getBundle("config").getString("smsConverChannelsuccess");
            String message = "XXXX foi modificado para YYYY pelo usuario ZZZZ. Por favor use YYYY para iniciar a sessao no mBCCS. Obrigado!";
            String isdn = "258" + db.getIsdnStaff(staff.getStaffCode());
            Long staffIdNew = getSequence("STAFF_SEQ");
            Staff newStaff = new Staff();
            BeanUtils.copyProperties(newStaff, staff);
            //b1 off staff lay nguyen hanm off staff tu ben chuc nang offstaff sang 
            String rs = offStaff(staff.getStaffCode(), this.addStaffCodeCTVDBForm.getShopCode());
            if (!"listChangeInfoStaff".equalsIgnoreCase(rs)) {
                this.req.setAttribute("messageUpdate", rs);
                return "listChangeInfoStaff";
            }
            //b2 : copy ra staff moi chi thay doi id,type,code
            newStaff.setStaffId(staffIdNew);
            newStaff.setChannelTypeId(converChanel);
            ChannelType channelType = new ChannelTypeDAO().findById(newStaff.getChannelTypeId());
            newStaff.setPricePolicy(channelType.getPricePolicyDefault());
            newStaff.setDiscountPolicy(channelType.getDiscountPolicyDefault());

            Map staffHashMap = new HashMap();
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            ChannelType objectType = channelTypeDAO.findById(Long.valueOf(this.addStaffCodeCTVDBForm.getConverChanel()));
            String staffCode = "";
            staffCode = channelDAO.getStaffCodeSeqIsNotVt(provinceArea.getProvinceReference() + objectType.getPrefixObjectCode(), staffHashMap);
            System.out.println("Staff code " + staffCode);
            newStaff.setStaffCode(staffCode);
            getSession().save(newStaff);
            //B3: cap nhat tren VSA 
            int rsud = updateUserNameVSA(staff.getStaffCode(), newStaff.getStaffCode());
//          B4 save log tao nhan vien moi tren BCCS , log Off da luu o ham OffStaffs
            lstLogObj.add(new ActionLogsObj(staff, newStaff));
            saveLog("ACTION_CONVERT_CHANNEL", "Update information staff of convert channel", lstLogObj, newStaff.getStaffId());
//          B5 nhan tin cho channel
//            XXXX was changed to YYYY by ZZZZ. Please use YYYY to login mBCCS. Thank you!
            sendSms(isdn, message.replace("XXXX", staff.getStaffCode()).replace("YYYY", newStaff.getStaffCode())
                    .replace("ZZZZ", userToken.getLoginName()), "86904");
//            B6: save enventlogs in vsav3
            db.saveEventLogsVSA(userToken.getLoginName(), staff.getStaffCode(), newStaff.getStaffCode());
            setTabSession("changeStatus", "true");
            setTabSession("changeInfo", "true");
            this.req.setAttribute("messageUpdate", "ERR.CHL.116");
            smSession.getTransaction().commit();
            smSession.flush();
            smSession.clear();

            cmPreSession.getTransaction().commit();
            cmPreSession.flush();
            cmPreSession.clear();

            afterUpdateSuccess = true;
            findStaff();

            return "listChangeInfoStaff";
        } catch (Exception ex) {
            this.log.debug("", ex);

            if ((smSession != null) && (smSession.isConnected())) {
                smSession.getTransaction().rollback();
                smSession.clear();
            }

            if ((cmPreSession != null) && (cmPreSession.isConnected())) {
                cmPreSession.getTransaction().rollback();
                cmPreSession.clear();
            }
        }

        return "listChangeInfoStaff";
    }

    public int sendSms(String isdn, String message, String channel) {
        int result = 0;
        long startTime = System.currentTimeMillis();
        String sql = "INSERT INTO mt VALUES(MT_SEQ.NEXTVAL,0,?,?,0,?,SYSDATE)";
        try {
            log.info("Start insert mt to send sms isdn " + isdn);
            System.out.println("isdn  :" + isdn);
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, isdn);
            query.setParameter(1, message);
            query.setParameter(2, channel);
            result = query.executeUpdate();
            System.out.println("channel  :" + channel);
            log.info("End insert mt isdn " + isdn
                    + " Duration " + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            System.out.println("Exception  :");
            log.error("Had error insert mt isdn" + isdn);
            throw ex;
        } finally {
            System.out.println("result  :" + result);
            return result;
        }
    }

    private int updateUserNameVSA(String userName, String userNameNew) {
        Session session = null;
        int rs = 0;
        String sql = "update users set user_name = ? where lower(user_name) = lower(?)";
        try {
            session = getSession("vsa");
            Query q = session.createSQLQuery(sql).addScalar("userName", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(UserVsa.class));;
            q.setParameter(0, userNameNew.toLowerCase());
            q.setParameter(1, userName);
            rs = q.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error getStaff ", e);
            LOG.error("Error getStaff ", e);
        }
        return rs;
    }

    public String offStaff(String staffCode, String shopCode) throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = "listChangeInfoStaff";
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<Staff> list = null;
        // lay ma nhan vien 
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Long shopId = 0L;
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
        if (shop == null) {
            return "ERR.CHL.083";
        }
        shopId = shop.getShopId();
        Staff staff = null;
        Long staffId = 0L;
        if (staffCode == null || "".equals(staffCode.toString().trim())) {
            return "ERR.CHL.129";
        }
        if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
            return "ERR.CHL.140";
        }
        StaffDAO staffDao = new StaffDAO();
        staffDao.setSession(this.getSession());
        staff = staffDao.findAvailableByStaffCodeNotStatus(staffCode);
        if (staff == null) {
            return "ERR.CHL.130";
        }
        if (staff.getStatus().equals(Constant.STATUS_DELETE)) {
            return "ERR.CHL.137";
        }
        if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
            return "ERR.CHL.140";
        }
        if (!staff.getShopId().equals(shop.getShopId())) {
            return "ERR.CHL.144";
        }
        staffId = staff.getStaffId();
        Staff oldStaff = new Staff();
        BeanUtils.copyProperties(oldStaff, staff);

        // tutm1 : check han muc cua cua hang cap tren ma nhan vien nay truc thuoc
        // check han muc : currentDebitOfShop <= maxDebitOfShop - maxDebitOfStaff.

        //TrongLV
        boolean result = checkMaxDebitWhenOffStaff(shopId, staffId, null);

        if (!result) { // khong thoa man => ko off ma nhan vien
            return "ERR.LIMIT.0025";
        }

        // goi den thu tuc trong Oracle :
        String errorCoe = this.checkStaff(staffId);
        if ("1".equals(errorCoe)) {
            return "ERR.CHL.131";
        }
//        if ("2".equals(errorCoe)) {
//            return "ERR.CHL.132";
//        }
        if ("3".equals(errorCoe)) {
            return "ERR.CHL.133";
        }
        if ("4".equals(errorCoe)) {
            return "ERR.CHL.134";
        }
        if ("5".equals(errorCoe)) {
            return "ERR.CHL.135";
        }
        String sql = "From Staff where status = 1 and shopId = ? and  staffOwnerId = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, staff.getShopId());
        query.setParameter(1, staff.getStaffId());
        list = query.list();
        if (list != null && list.size() > 0) {
            return "ERR.CHL.136";
        }
        // thuc hien Off mã nv
        staff.setStatus(Constant.STATUS_DELETE);
        staff.setSyncStatus(Constant.STATUS_NOT_SYNC);
        getSession().update(staff);
        lstLogObj.add(new ActionLogsObj("STAFF", "STATUS", "1", "0"));

        saveLog(Constant.ACTION_OFF_STAFF, "OFF mã nhân viên", lstLogObj, staff.getStaffId(), Constant.OFF_STAFF_CODE, Constant.EDIT);

        // ghi Log cho chuyen doi nhan vien :
//        req.setAttribute("messageParam", "MES.CHL.160");
        return pageForward;
    }

    public boolean checkShopUnder(Long shopIdLogin, Long shopIdSelect) {
        if (shopIdLogin.equals(shopIdSelect)) {
            return true;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shopLogin = shopDAO.findById(shopIdLogin);
        Shop shopSelect = shopDAO.findById(shopIdSelect);
        if (shopSelect.getShopPath().indexOf(shopLogin.getShopPath() + "_") < 0) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * @Author:ANHTT
     * @param staffId
     * @return Ham check Nhan vien thoa man dieu kien de chuyen cua hang
     * @throws Exception
     */
    public String checkStaff(Long staffId) throws Exception {
        String query = " begin PKG_CHECK_STAFF.CHECK_STAFF(?,?); "
                + "end;";
        try {
            CallableStatement stmt =
                    getSession().connection().prepareCall(query);
            stmt.setLong(1, staffId);
            stmt.registerOutParameter(2, Types.VARCHAR);
            stmt.executeUpdate();
            String errorCode = stmt.getString(2);
            return errorCode;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    //DINHDC ADD 20160525 Update ISDN Channel Wallet
    public String updateISDNChannelWallet() throws Exception {
        Session smSession = getSession();
        Session cmPreSession = getSession("cm_pre");
        ResponseWallet responseWallet = new ResponseWallet();
        try {
            getReqSession();

            UserToken userToken = (UserToken) this.reqSession.getAttribute("userToken");
            List lstLogObj = new ArrayList();
            Long staffId = this.addStaffCodeCTVDBForm.getStaffId();
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(smSession);

            Staff staff = staffDAO.findById(staffId);
            if (staff == null) {
                this.req.setAttribute("messageUpdate", "ERR.CHL.107");
                return "listChangeInfoStaff";
            }
            //Kiem tra thong tin ISDN vi
            String channelWallet = this.addStaffCodeCTVDBForm.getChannelWallet();
            String isdnWallet = this.addStaffCodeCTVDBForm.getIsdnWallet();
            if (channelWallet == null || "".equals(channelWallet)) {
                this.req.setAttribute("messageUpdate", "ERR.IS.NOT.CHANNEL.WALLET");
                return "listChangeInfoStaff";
            }
            if (isdnWallet != null && (isdnWallet.trim()).equals(staff.getIsdnWallet())) {
                this.req.setAttribute("messageUpdate", "ERR.INPUT.NEW.ISDN.WALLET");
                return "listChangeInfoStaff";
            }
            String sql = "from Staff where isdnWallet = ? and status != ? ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, isdnWallet);
            query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
            List<Staff> listStaff = query.list();
            if (listStaff != null && listStaff.size() > 0) {
                this.req.setAttribute("messageUpdate", getText("err.isdn.wallet.duplicate") + " (staff_code = " + listStaff.get(0).getStaffCode() + ")");
                return "listChangeInfoStaff";
            }

            Byte subType = 1;
            sql = "From StockIsdnMobile where to_number(isdn) = ?";
            BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(isdnWallet));
            query = getSession().createQuery(sql);
            query.setParameter(0, isdnSearch);
            List<StockIsdnMobile> list = query.list();
            if (list != null && list.size() > 0) {
                if (list.get(0).getIsdnType() != null) {
                    subType = Byte.valueOf(list.get(0).getIsdnType());
                }
            } else {
                this.req.setAttribute("messageUpdate", "err.isdn.wallet");
                return "listChangeInfoStaff";
            }

            InterfaceCm inter = new InterfaceCm();
            Object subInfo;
            subInfo = inter.getSubscriberInfoV2(isdnSearch.toString(), "M", subType);
            if (subInfo == null) {
                this.req.setAttribute("messageUpdate", "err.isdn.wallet");
                return "listChangeInfoStaff";
            }

            if (subType == 1) {
                com.viettel.bccs.cm.database.BO.pre.SubMb subMb = (com.viettel.bccs.cm.database.BO.pre.SubMb) subInfo;
                if (subMb.getActStatus() != null && (subMb.getActStatus().equals("01")
                        || subMb.getActStatus().equals("10") || subMb.getActStatus().equals("02")
                        || subMb.getActStatus().equals("20"))) {
                    this.req.setAttribute("messageUpdate", "err.isdn.wallet.blocked");
                    return "listChangeInfoStaff";
                } else if (subMb.getActStatus() != null && subMb.getActStatus().equals("03")) {
                    this.req.setAttribute("messageUpdate", "err.isdn.wallet.inActive");
                    return "listChangeInfoStaff";
                } else if (subMb.getActStatus() == null) {
                    this.req.setAttribute("messageUpdate", "err.isdn.wallet");
                    return "listChangeInfoStaff";
                }
            }

            //DINHDC ADD 20160524
            //Thuc hien Update isdn kenh vi
            String dob = "";
            String idIssueDate = "";
            String parentIdWallet = "";
            String idNo = "";
            String strShopId = "";
            if (this.addStaffCodeCTVDBForm.getBirthday() != null) {
                dob = DateTimeUtils.convertDateTimeToString(this.addStaffCodeCTVDBForm.getBirthday(), "ddMMyyyy");
                //dob = this.addStaffCodeCTVDBForm.getBirthday().toString();
            }
            if (this.addStaffCodeCTVDBForm.getMakeDate() != null) {
                idIssueDate = DateTimeUtils.convertDateTimeToString(DateTimeUtils.convertStringToDate(this.addStaffCodeCTVDBForm.getMakeDate()), "ddMMyyyy");
                //idIssueDate = DateTimeUtils.convertStringToDate(this.addStaffCodeCTVDBForm.getMakeDate()).toString();
            }
            if (staff.getParentIdWallet() != null) {
                parentIdWallet = staff.getParentIdWallet().toString();
            }
            if (this.addStaffCodeCTVDBForm.getIdNo() != null) {
                idNo = this.addStaffCodeCTVDBForm.getIdNo().toString();
            }
            if (staff.getShopId() != null) {
                strShopId = staff.getShopId().toString();
            }

            if (staff.getStatus() != null
                    && staff.getStatus().equals(1L)) {

                String response = functionChannelWallet(smSession, isdnWallet, this.addStaffCodeCTVDBForm.getStaffName(), "1",
                        dob, "1", idNo, addStaffCodeCTVDBForm.getAddress(), "4", addStaffCodeCTVDBForm.getChannelWallet(), staff.getStaffId().toString(), parentIdWallet,
                        this.addStaffCodeCTVDBForm.getMakeAddress(), idIssueDate, staff.getStaffId().toString(), strShopId, addStaffCodeCTVDBForm.getVoucherCode());
                System.out.println("response Update ISDN Channel Wallet:" + response);
                if ("ERROR".equals(response)) {
                    this.req.setAttribute("messageUpdate", "Error.Call.WS.Wallet");
                    return "listChangeInfoStaff";
                } else {
                    Gson gson = new Gson();
                    responseWallet = gson.fromJson(response, ResponseWallet.class);
                }
            }
            Staff oldStaff = new Staff();
            BeanUtils.copyProperties(oldStaff, staff);
            String actionType = "ACTION_CHANGE_INFO_STAFF";
            String message = "Update information isdn of Channel wallet";
            //Hien thi ket qua thuc hien thay doi SDT vi
            if (responseWallet != null && responseWallet.getResponseCode() != null) {
                if (responseWallet.getResponseCode().equals("01")) {
                    //vi dien tu
                    staff.setIsdnWallet(isdnWallet);
                    smSession.update(staff);
                } else {
                    this.req.setAttribute("messageUpdate", "MSG.UPDATE.ISDN.UNSECCESS");
                    insertLogCallWsWallet(smSession, staff.getIsdnWallet(), staff.getStaffId(), "4", 0L, 0L, getSysdate(), responseWallet.getResponseMessage(),
                            staff.getName(), staff.getBirthday(), idNo, staff.getChannelWallet(), staff.getParentIdWallet(), staff.getIdIssuePlace(), staff.getIdIssueDate());
                    return "listChangeInfoStaff";
                }
            } else {
                this.req.setAttribute("messageUpdate", "MSG.UPDATE.ISDN.UNSECCESS");
                return "listChangeInfoStaff";
            }

            lstLogObj.add(new ActionLogsObj(oldStaff, staff));
            saveLog(actionType, message, lstLogObj, staff.getStaffId());

            smSession.getTransaction().commit();
            smSession.flush();

            afterUpdateSuccess = true;
            findStaff();

            this.req.setAttribute("messageUpdate", "ERR.CHL.116");

            return "listChangeInfoStaff";
        } catch (Exception ex) {
            this.log.debug("", ex);

            if ((smSession != null) && (smSession.isConnected())) {
                smSession.getTransaction().rollback();
                smSession.clear();
            }
        }

        return "listChangeInfoStaff";
    }
    //END 

    //DINHDC ADD 20160525 Change Channel  Type Wallet
    public String changeChannelWallet() throws Exception {
        Session smSession = getSession();
        Session cmPreSession = getSession("cm_pre");
        ResponseWallet responseWallet = new ResponseWallet();
        try {
            getReqSession();

            UserToken userToken = (UserToken) this.reqSession.getAttribute("userToken");
            List lstLogObj = new ArrayList();
            Long staffId = this.addStaffCodeCTVDBForm.getStaffId();
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(smSession);

            Staff staff = staffDAO.findById(staffId);
            if (staff == null) {
                this.req.setAttribute("messageUpdate", "ERR.CHL.107");
                return "listChangeInfoStaff";
            }
            //Kiem tra thong tin ISDN vi
            String channelWallet = this.addStaffCodeCTVDBForm.getChannelWallet();
            String isdnWallet = this.addStaffCodeCTVDBForm.getIsdnWallet();
            String parentCodeWallet = this.addStaffCodeCTVDBForm.getParentCodeWallet();
            parentCodeWallet = parentCodeWallet == null ? "" : parentCodeWallet.trim();
            if (channelWallet == null || "".equals(channelWallet)) {
                this.req.setAttribute("messageUpdate", "ERR.IS.NOT.CHANNEL.WALLET");
                return "listChangeInfoStaff";
            }
            if (channelWallet.equals(staff.getChannelWallet())) {
                this.req.setAttribute("messageUpdate", "ERR.INPUT.NEW.CHANNEL.WALLET");
                return "listChangeInfoStaff";
            }
            if (!channelWallet.equals("SA")) {
                Staff parentWallet = staffDAO.findParentWalletByStaffCode(parentCodeWallet, channelWallet);
                if (parentWallet != null) {
                    this.addStaffCodeCTVDBForm.setParentIdWallet(parentWallet.getStaffId());
                }
            }

            //DINHDC ADD 20160524
            //Thuc hien Update isdn kenh vi
            String dob = "";
            String idIssueDate = "";
            String idNo = "";
            String parentIdWallet = "";
            String strShopId = "";
            if (this.addStaffCodeCTVDBForm.getBirthday() != null) {
                dob = DateTimeUtils.convertDateTimeToString(this.addStaffCodeCTVDBForm.getBirthday(), "ddMMyyyy");
                //dob = this.addStaffCodeCTVDBForm.getBirthday().toString();
            }
            if (this.addStaffCodeCTVDBForm.getMakeDate() != null) {
                idIssueDate = DateTimeUtils.convertDateTimeToString(DateTimeUtils.convertStringToDate(this.addStaffCodeCTVDBForm.getMakeDate()), "ddMMyyyy");
                //idIssueDate = DateTimeUtils.convertStringToDate(this.addStaffCodeCTVDBForm.getMakeDate()).toString();
            }
            if (this.addStaffCodeCTVDBForm.getParentIdWallet() != null && this.addStaffCodeCTVDBForm.getParentIdWallet() > 0) {
                parentIdWallet = this.addStaffCodeCTVDBForm.getParentIdWallet().toString();
            }
            if (this.addStaffCodeCTVDBForm.getIdNo() != null) {
                idNo = this.addStaffCodeCTVDBForm.getIdNo().toString();
            }
            if (staff.getShopId() != null) {
                strShopId = staff.getShopId().toString();
            }

            if (staff.getStatus() != null
                    && staff.getStatus().equals(1L)) {

                String response = functionChannelWallet(smSession, isdnWallet, staff.getName(), "1",
                        dob, "1", idNo, this.addStaffCodeCTVDBForm.getAddress(), "5", this.addStaffCodeCTVDBForm.getChannelWallet(), staff.getStaffId().toString(), parentIdWallet,
                        this.addStaffCodeCTVDBForm.getMakeAddress(), idIssueDate, staff.getStaffId().toString(), strShopId, addStaffCodeCTVDBForm.getVoucherCode());
                System.out.println("response change Channel Wallet:" + response);
                if ("ERROR".equals(response)) {
                    this.req.setAttribute("messageUpdate", "Error.Call.WS.Wallet");
                    return "listChangeInfoStaff";
                } else {
                    Gson gson = new Gson();
                    responseWallet = gson.fromJson(response, ResponseWallet.class);
                }
            }
            Staff oldStaff = new Staff();
            BeanUtils.copyProperties(oldStaff, staff);
            String actionType = "ACTION_CHANGE_INFO_STAFF";
            String message = "Update information Channel wallet";
            //Hien thi ket qua thuc hien thay doi kenh vi
            if (responseWallet != null && responseWallet.getResponseCode() != null) {
                if (responseWallet.getResponseCode().equals("01")) {
                    //vi dien tu
                    List<Long> listSubStaff = getListSubChannelWallet(staffId);
                    if (listSubStaff != null && listSubStaff.size() > 0) {
                        for (int j = 0; j < listSubStaff.size(); j++) {
                            Staff subStaff = staffDAO.findById(listSubStaff.get(j));
                            if (subStaff != null) {
                                subStaff.setParentIdWallet(null);
                                smSession.update(subStaff);
                            }
                        }
                    }
                    staff.setChannelWallet(channelWallet);
                    staff.setParentIdWallet(this.addStaffCodeCTVDBForm.getParentIdWallet());
                    smSession.update(staff);
                } else {
                    this.req.setAttribute("messageUpdate", "MSG.UPDATE.ISDN.UNSECCESS");
                    insertLogCallWsWallet(smSession, staff.getIsdnWallet(), staff.getStaffId(), "5", 0L, 0L, getSysdate(), responseWallet.getResponseMessage(),
                            staff.getName(), staff.getBirthday(), idNo, this.addStaffCodeCTVDBForm.getChannelWallet(), this.addStaffCodeCTVDBForm.getParentIdWallet(), staff.getIdIssuePlace(), staff.getIdIssueDate());
                    return "listChangeInfoStaff";
                }
            } else {
                this.req.setAttribute("messageUpdate", "MSG.UPDATE.ISDN.UNSECCESS");
                return "listChangeInfoStaff";
            }

            lstLogObj.add(new ActionLogsObj(oldStaff, staff));
            saveLog(actionType, message, lstLogObj, staff.getStaffId());

            smSession.getTransaction().commit();
            smSession.flush();

            afterUpdateSuccess = true;
            findStaff();

            this.req.setAttribute("messageUpdate", "ERR.CHL.116");

            return "listChangeInfoStaff";
        } catch (Exception ex) {
            this.log.debug("", ex);

            if ((smSession != null) && (smSession.isConnected())) {
                smSession.getTransaction().rollback();
                smSession.clear();
            }
        }

        return "listChangeInfoStaff";
    }
    //END 

    private AccountAgent checkMsisdn(Long staffId)
            throws Exception {
        try {
            String query = " from AccountAgent where ownerId = ? and ownerType = ? and msisdn is not null";
            Query q = getSession().createQuery(query);
            q.setParameter(0, staffId);
            q.setParameter(1, Constant.OWNER_TYPE_STAFF);
            List lst = q.list();
            if ((lst != null) && (lst.size() > 0)) {
                return (AccountAgent) lst.get(0);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateStaff(Staff staff) {
    }

    public String selectPage() throws Exception {
        this.log.debug("# Begin method selectPage");
        this.log.debug("# End method selectPage");
        return "listChangeInfoStaff";
    }

    public String preparePageChangeInfoAP() throws Exception {
        this.log.info("Begin method preparePage of addStaffCodeCVTDBDAO ...");
        HttpServletRequest req = getRequest();

        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        this.addStaffCodeCTVDBForm.setShopCodeSearch(userToken.getShopCode());
        this.addStaffCodeCTVDBForm.setShopNameSearch(userToken.getShopName());
        this.addStaffCodeCTVDBForm.setStaffMagCodeSearch(userToken.getLoginName());
        this.addStaffCodeCTVDBForm.setStaffMagNameSearch(userToken.getStaffName());
        setTabSession("changeStatus", "true");
        setTabSession("changeInfo", "true");
        removeTabSession("listStaff");
        this.pageForward = "changeInformationStaffAP";
        this.log.info("End method preparePage of addStaffCodeCVTDBDAO");
        return this.pageForward;
    }

    public String clickStaffAP() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Object object = req.getParameter("staffId");
        Long staffId = Long.valueOf(0L);
        if (object != null) {
            resetForm();
            staffId = Long.valueOf(Long.parseLong(object.toString()));
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            Staff staff = staffDAO.findById(staffId);
            if (staff != null) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());
                Shop shop = shopDAO.findById(staff.getShopId());
                if (shop != null) {
                    this.addStaffCodeCTVDBForm.setShopCode(shop.getShopCode());
                    this.addStaffCodeCTVDBForm.setShopName(shop.getName());
                }
                this.addStaffCodeCTVDBForm.setStaffCode(staff.getStaffCode());
                this.addStaffCodeCTVDBForm.setStaffName(staff.getName());
                this.addStaffCodeCTVDBForm.setIdNo(staff.getIdNo());
                this.addStaffCodeCTVDBForm.setMakeAddress(staff.getIdIssuePlace());
                this.addStaffCodeCTVDBForm.setAddress(staff.getAddress());
                this.addStaffCodeCTVDBForm.setStatus(staff.getStatus());
                this.addStaffCodeCTVDBForm.setIsdn(staff.getIsdn());
                if ((staff.getStaffOwnerId() != null) && (!staff.getStaffOwnerId().equals(Long.valueOf(0L)))) {
                    setTabSession("changeInfo", "true");
                    Staff staffManagement = staffDAO.findById(staff.getStaffOwnerId());
                    if (staffManagement != null) {
                        this.addStaffCodeCTVDBForm.setStaffManagementCode(staffManagement.getStaffCode());
                        this.addStaffCodeCTVDBForm.setStaffManagementName(staffManagement.getName());
                    }
                } else {
                    setTabSession("changeInfo", "false");
                }
                if (staff.getIdIssueDate() != null) {
                    this.addStaffCodeCTVDBForm.setMakeDate(DateTimeUtils.convertDateToString(staff.getIdIssueDate()));
                }
            }
            this.addStaffCodeCTVDBForm.setStaffId(staffId);

            this.addStaffCodeCTVDBForm.setUsedfulWidth(staff.getUsedfulWidth());
            this.addStaffCodeCTVDBForm.setSurfaceArea(staff.getSurfaceArea());
            this.addStaffCodeCTVDBForm.setTradeName(staff.getTradeName());
            this.addStaffCodeCTVDBForm.setContactName(staff.getContactName());
            this.addStaffCodeCTVDBForm.setTel(staff.getTel());
            this.addStaffCodeCTVDBForm.setRegistryDate(staff.getRegistryDate());
            this.addStaffCodeCTVDBForm.setCheckVat(staff.getCheckVat());
            this.addStaffCodeCTVDBForm.setAgentType(staff.getAgentType());
        }
        setTabSession("changeStatus", "false");
        return "listChangeInfoStaffAP";
    }

    public String findStaffAP() throws Exception {
        HttpServletRequest req = getRequest();
        String shopCode = this.addStaffCodeCTVDBForm.getShopCodeSearch();
        String pointOfSale = this.addStaffCodeCTVDBForm.getSearchPointOfSale();
        String staffCode = this.addStaffCodeCTVDBForm.getOwnercode();
        String staffManagementCode = this.addStaffCodeCTVDBForm.getStaffMagCodeSearch();
        Long staffOwnerId = Long.valueOf(0L);
        staffOwnerId = getStaffId(staffManagementCode);
        if ((staffManagementCode != null) && (!staffManagementCode.equals("")) && (staffOwnerId.equals(Long.valueOf(0L)))) {
            req.setAttribute("messageUpdate", "ERR.CHL.088");
            return "listChangeInfoStaffAP";
        }

        Long shopId = getShopId(shopCode);
        if (shopId.equals(Long.valueOf(0L))) {
            req.setAttribute("messageUpdate", "ERR.CHL.083");
            return "listChangeInfoStaffAP";
        }
        setTabSession("changeStatus", "true");

        String sql = "";
        List listParameter = new ArrayList();
        sql = "SELECT a.staff_id as staffId  ,a.staff_code as staffCode, a.NAME as name, a.id_no as idNo, 'CTV_AP' as nameChannelType, stm.staff_code as nameManagement,  decode(a.status,0,'Tạm ngưng',1,'Hoạt động') as statusName, sh.shop_code as shopCode FROM staff a, staff stm, shop sh  WHERE 1 = 1 AND a.shop_id = sh.shop_id AND a.staff_owner_id = stm.staff_id(+) ";

        sql = sql + " and a.shop_id in (select shop_id from v_shop_tree where root_id = ?) and a.channel_type_id = ? and  lower(a.staff_Code) like ? ";
        listParameter.add(shopId);
        listParameter.add(this.CHANNEL_TYPE_AP_ID);
        listParameter.add(staffCode.toLowerCase().trim() + "%");
        if ((pointOfSale != null) && (pointOfSale.equals("1"))) {
            sql = sql + " and a.staff_code like ?  ";
            listParameter.add("%_CTV_AP");
        } else if ((pointOfSale != null) && (pointOfSale.equals("2"))) {
            sql = sql + " and a.staff_code like ?  ";
            listParameter.add("%_CTVDM_AP");
        }

        if (!staffOwnerId.equals(Long.valueOf(0L))) {
            sql = sql + " and a.staff_owner_id = ? ";
            listParameter.add(staffOwnerId);
        }
        sql = sql + " and rownum <=100 ";
        sql = sql + " order by a.staff_code asc ";
        List list = new ArrayList();
        Query queryObject = getSession().createSQLQuery(sql).addScalar("staffId", Hibernate.LONG).addScalar("staffCode", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("idNo", Hibernate.STRING).addScalar("nameChannelType", Hibernate.STRING).addScalar("nameManagement", Hibernate.STRING).addScalar("statusName", Hibernate.STRING).addScalar("shopCode", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewStaffBean.class));
        for (int i = 0; i < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));
        }
        list = queryObject.list();
        setTabSession("listStaff", list);
        resetForm();
        return "listChangeInfoStaffAP";
    }

    public String cancelAP() {
        resetForm();
        setTabSession("changeStatus", "true");
        setTabSession("changeInfo", "true");
        return "listChangeInfoStaffAP";
    }

    public String updateInfoAP() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List lstLogObj = new ArrayList();
        Long staffId = this.addStaffCodeCTVDBForm.getStaffId();
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findById(staffId);
        if (staff == null) {
            req.setAttribute("messageUpdate", "ERR.CHL.117");
            return "listChangeInfoStaffAP";
        }

        Long staffOwnerId = getStaffId(this.addStaffCodeCTVDBForm.getStaffManagementCode());
        if (staffOwnerId.equals(Long.valueOf(0L))) {
            req.setAttribute("messageUpdate", "ERR.CHL.088");
            return "listChangeInfoStaffAP";
        }

        Staff staffManagement = staffDAO.findById(staffOwnerId);
        if (staffManagement.getChannelTypeId() == null) {
            req.setAttribute("messageUpdate", "ERR.CHL.090");
            return "listChangeInfoStaff";
        }
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        ChannelType channelType = channelTypeDAO.findById(staffManagement.getChannelTypeId());
        if ((channelType == null) || (channelType.getChannelTypeId() == null) || (!channelType.getIsVtUnit().equals("1")) || (!channelType.getIsPrivate().equals(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE)) || (!channelType.getObjectType().equals("2"))) {
            req.setAttribute("messageUpdate", "ERR.CHL.090");
            return "listChangeInfoStaff";
        }

        if (!staff.getShopId().equals(staffManagement.getShopId())) {
            req.setAttribute("messageUpdate", "ERR.CHL.118");
            return "listChangeInfoStaffAP";
        }
        if (this.addStaffCodeCTVDBForm.getStaffName().toUpperCase().trim().equals("EMPTY NAME")) {
            req.setAttribute("messageUpdate", "ERR.CHL.119");
            return "listChangeInfoStaffAP";
        }

        Long status = this.addStaffCodeCTVDBForm.getStatus();
        if ((status != null) && (status.equals(Long.valueOf(0L))) && (staff.getStatus().equals(Long.valueOf(1L)))) {
            ChannelDAO channelDAO = new ChannelDAO();
            channelDAO.setSession(getSession());

            if (!channelDAO.checkStockTotal(staff.getStaffId(), Constant.OWNER_TYPE_STAFF, Constant.STATE_NEW)) {
                req.setAttribute("messageUpdate", "ERR.CHL.146");
                return "listChangeInfoStaffAP";
            }

            if (!channelDAO.checkInvoiceUsed(staff.getShopId(), staff.getStaffId(), 45)) {
                req.setAttribute("messageUpdate", "ERR.CHL.147");
                return "listChangeInfoStaffAP";
            }

        }

        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        List listPricePolicy = null;
        List listDiscountPolicy = null;
        String pricePolicy = "";
        String discountPolicy = "";
        if (staff != null) {
            pricePolicy = staff.getPricePolicy();
            discountPolicy = staff.getDiscountPolicy();
            listPricePolicy = appParamsDAO.findAppParamsByType("PRICE_POLICY_DEFAUT_CTV");

            listDiscountPolicy = appParamsDAO.findAppParamsByType("DISCOUNT_POLICY_DEFAUT_CTV");

            if ((listPricePolicy != null) && (listPricePolicy.size() > 0)) {
                pricePolicy = ((AppParams) listPricePolicy.get(0)).getCode();
            }
            if ((listDiscountPolicy != null) && (listDiscountPolicy.size() > 0)) {
                discountPolicy = ((AppParams) listDiscountPolicy.get(0)).getCode();
            }
        }
        Staff oldStaff = new Staff();
        BeanUtils.copyProperties(oldStaff, staff);
        String actionType = "";
        String message = "";
        actionType = "ACTION_CHANGE_INFO_STAFF_AP";
        message = "Cáº­p nháº­t thÃ´ng tin CTV_AP";

        staff.setStaffOwnerId(staffOwnerId);
        staff.setName(this.addStaffCodeCTVDBForm.getStaffName().trim());
        staff.setIdNo(this.addStaffCodeCTVDBForm.getIdNo().trim());
        staff.setIdIssueDate(DateTimeUtils.convertStringToDate(this.addStaffCodeCTVDBForm.getMakeDate()));
        staff.setIdIssuePlace(this.addStaffCodeCTVDBForm.getMakeAddress().trim());
        staff.setAddress(this.addStaffCodeCTVDBForm.getAddress().trim());
        staff.setPricePolicy(pricePolicy);
        staff.setDiscountPolicy(discountPolicy);
        staff.setStatus(this.addStaffCodeCTVDBForm.getStatus());
        staff.setIsdn(this.addStaffCodeCTVDBForm.getIsdn());

        staff.setLastUpdateUser(userToken.getLoginName());
        staff.setLastUpdateTime(getSysdate());
        staff.setLastUpdateIpAddress(req.getRemoteAddr());
        staff.setLastUpdateKey("88888881");

        staff.setUsedfulWidth(this.addStaffCodeCTVDBForm.getUsedfulWidth());
        staff.setSurfaceArea(this.addStaffCodeCTVDBForm.getSurfaceArea());
        staff.setRegistryDate(this.addStaffCodeCTVDBForm.getRegistryDate());
        staff.setTradeName(this.addStaffCodeCTVDBForm.getTradeName());
        staff.setContactName(this.addStaffCodeCTVDBForm.getContactName());
        staff.setTel(this.addStaffCodeCTVDBForm.getTel());
        staff.setCheckVat(this.addStaffCodeCTVDBForm.getCheckVat());
        staff.setAgentType(this.addStaffCodeCTVDBForm.getAgentType());

        getSession().update(staff);

        lstLogObj.add(new ActionLogsObj(oldStaff, staff));
        saveLog(actionType, message, lstLogObj, staff.getStaffId());

        insertStockOwnerTmp(staff.getStaffId(), "2", staff.getStaffCode(), staff.getName(), staff.getChannelTypeId());

        setTabSession("changeStatus", "true");
        setTabSession("changeInfo", "true");
        getSession().flush();
        getSession().getTransaction().commit();
        getSession().beginTransaction();
        findStaffAP();

        req.setAttribute("messageUpdate", "ERR.CHL.116");
        return "listChangeInfoStaffAP";
    }

    private boolean updateAccountAgent(Staff staff) {
        try {
            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            AccountAgent accountAgent = accountAgentDAO.findByOwnerIdAndOwnerTypeAndStatus(getSession(), staff.getStaffId(), Constant.OWNER_TYPE_STAFF, null);
            if ((accountAgent != null) && (accountAgent.getAccountId() != null)) {
                accountAgent.setBirthDate(staff.getBirthday());
                accountAgent.setOwnerCode(staff.getStaffCode());
                accountAgent.setOwnerName(staff.getName());

                accountAgent.setProvince(staff.getProvince());
                accountAgent.setDistrict(staff.getDistrict());
                accountAgent.setPrecinct(staff.getPrecinct());
                accountAgent.setOutletAddress(staff.getAddress());

                accountAgent.setTradeName(staff.getTradeName());
                accountAgent.setContactNo(staff.getContactName());

                accountAgent.setIdNo(staff.getIdNo());
                accountAgent.setIssueDate(staff.getIdIssueDate());
                accountAgent.setIssuePlace(staff.getIdIssuePlace());

                accountAgent.setIdNo(staff.getIdNo());
                accountAgent.setIssueDate(staff.getIdIssueDate());
                accountAgent.setIssuePlace(staff.getIdIssuePlace());

                accountAgent.setLastUpdateUser(staff.getLastUpdateUser());
                accountAgent.setLastUpdateTime(staff.getLastUpdateTime());
                accountAgent.setLastUpdateIpAddress(staff.getLastUpdateIpAddress());
                accountAgent.setLastUpdateKey(staff.getLastUpdateKey());

                accountAgent.setCheckVat(staff.getCheckVat());

                getSession().update(accountAgent);
            }

            return true;
        } catch (Exception ex) {
            this.log.info(ex);
        }
        return false;
    }

    public String selectPageAP() throws Exception {
        this.log.debug("# Begin method selectPage");
        this.log.debug("# End method selectPage");
        return "listChangeInfoStaffAP";
    }

    private void resetForm() {
        this.addStaffCodeCTVDBForm.setShopCode(null);
        this.addStaffCodeCTVDBForm.setShopName(null);
        this.addStaffCodeCTVDBForm.setStaffCode(null);
        this.addStaffCodeCTVDBForm.setStaffName(null);
        this.addStaffCodeCTVDBForm.setStaffManagementCode(null);
        this.addStaffCodeCTVDBForm.setStaffManagementName(null);
        this.addStaffCodeCTVDBForm.setBirthday(null);
        this.addStaffCodeCTVDBForm.setStatus(null);
        this.addStaffCodeCTVDBForm.setIdNo(null);
        this.addStaffCodeCTVDBForm.setMakeAddress(null);
        this.addStaffCodeCTVDBForm.setMakeDate(null);
        this.addStaffCodeCTVDBForm.setAddress(null);
        this.addStaffCodeCTVDBForm.setPricePolicy(null);
        this.addStaffCodeCTVDBForm.setDiscountPolicy(null);
        this.addStaffCodeCTVDBForm.setIsdn(null);

        this.addStaffCodeCTVDBForm.setProvinceCode(null);
        this.addStaffCodeCTVDBForm.setProvinceName(null);
        this.addStaffCodeCTVDBForm.setDistrictCode(null);
        this.addStaffCodeCTVDBForm.setDistrictName(null);
        this.addStaffCodeCTVDBForm.setPrecinctCode(null);
        this.addStaffCodeCTVDBForm.setPrecinctName(null);
        this.addStaffCodeCTVDBForm.setStreetBlockName(null);
        this.addStaffCodeCTVDBForm.setStreetName(null);
        this.addStaffCodeCTVDBForm.setHome(null);

        this.addStaffCodeCTVDBForm.setProfileState(null);
        this.addStaffCodeCTVDBForm.setBoardState(null);
        this.addStaffCodeCTVDBForm.setUsedfulWidth(null);
        this.addStaffCodeCTVDBForm.setSurfaceArea(null);
        this.addStaffCodeCTVDBForm.setTradeName(null);
        this.addStaffCodeCTVDBForm.setContactName(null);
        this.addStaffCodeCTVDBForm.setTel(null);
        this.addStaffCodeCTVDBForm.setRegistryDate(null);
        this.addStaffCodeCTVDBForm.setCheckVat(null);
        this.addStaffCodeCTVDBForm.setIsChecked(null);
        this.addStaffCodeCTVDBForm.setAgentType(null);
        this.addStaffCodeCTVDBForm.setNote(null);
        this.addStaffCodeCTVDBForm.setImageUrl(null);
    }

    public Long getShopId(String shopCode) throws Exception {
        if (shopCode == null) {
            return Long.valueOf(0L);
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop.getShopId();
        }
        return Long.valueOf(0L);
    }

    public Long getStaffId(String staffCode) throws Exception {
        if (staffCode == null) {
            return Long.valueOf(0L);
        }
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff.getStaffId();
        }
        return Long.valueOf(0L);
    }

    public List<ImSearchBean> getListShopSameLevel(ImSearchBean imSearchBean) {
        HttpServletRequest r = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) r.getSession().getAttribute("userToken");

        List listImSearchBean = new ArrayList();
        Long parentShopId = this.addStaffCodeCTVDBForm.getParentShopId();

        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_USE);
        strQuery1.append("and channelTypeId <> 4 ");
        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (parentShopId != null) {
            strQuery1.append("and parentShopId = ? ");
            listParameter1.add(parentShopId);
        }
        if ((imSearchBean.getCode() != null) && (!imSearchBean.getCode().trim().equals(""))) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if ((imSearchBean.getName() != null) && (!imSearchBean.getName().trim().equals(""))) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(Long.valueOf(100L));

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List tmpList1 = query1.list();
        if ((tmpList1 != null) && (tmpList1.size() > 0)) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    public Long getListShopSameLevelSize(ImSearchBean imSearchBean) {
        HttpServletRequest r = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) r.getSession().getAttribute("userToken");

        Long parentShopId = this.addStaffCodeCTVDBForm.getParentShopId();

        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select count(*) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_USE);
        strQuery1.append("and channelTypeId <> 4 ");
        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (parentShopId != null) {
            strQuery1.append("and parentShopId = ? ");
            listParameter1.add(parentShopId);
        }
        if ((imSearchBean.getCode() != null) && (!imSearchBean.getCode().trim().equals(""))) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if ((imSearchBean.getName() != null) && (!imSearchBean.getName().trim().equals(""))) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List tmpList1 = query1.list();
        if ((tmpList1 != null) && (tmpList1.size() > 0)) {
            return (Long) tmpList1.get(0);
        }

        return null;
    }

    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        List listImSearchBean = new ArrayList();

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_USE);
        strQuery1.append("and channelTypeId <> 4 ");
        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if ((imSearchBean.getCode() != null) && (!imSearchBean.getCode().trim().equals(""))) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if ((imSearchBean.getName() != null) && (!imSearchBean.getName().trim().equals(""))) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(Long.valueOf(100L));

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List tmpList1 = query1.list();
        if ((tmpList1 != null) && (tmpList1.size() > 0)) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    public List<ImSearchBean> getListStaffManage(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        List listImSearchBean = new ArrayList();

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a WHERE 1 = 1 ");

        strQuery1.append(" AND a.shopId=? and a.status = 1 and a.channelTypeId in (select channelTypeId from ChannelType where isVtUnit= 1 and objectType = 2 and isPrivate = 0 and status = 1)  ");
        if ((imSearchBean.getOtherParamValue() != null) && (!imSearchBean.getOtherParamValue().trim().equals(""))) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            listParameter1.add(getShopId(otherParam));
        } else {
            return listImSearchBean;
        }

        if ((imSearchBean.getCode() != null) && (!imSearchBean.getCode().trim().equals(""))) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if ((imSearchBean.getName() != null) && (!imSearchBean.getName().trim().equals(""))) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(Long.valueOf(300L));

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List tmpList1 = query1.list();
        if ((tmpList1 != null) && (tmpList1.size() > 0)) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    public Long getListStaffManageSize(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest r = imSearchBean.getHttpServletRequest();

        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select count(*) ");
        strQuery1.append("from Staff a WHERE 1 = 1 ");

        strQuery1.append(" AND a.shopId=? and a.status = 1 and a.channelTypeId in (select channelTypeId from ChannelType where isVtUnit= 1 and objectType = 2 and isPrivate = 0 and status = 1)  ");
        if ((imSearchBean.getOtherParamValue() != null) && (!imSearchBean.getOtherParamValue().trim().equals(""))) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            listParameter1.add(getShopId(otherParam));
        } else {
            return null;
        }
        if ((imSearchBean.getCode() != null) && (!imSearchBean.getCode().trim().equals(""))) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if ((imSearchBean.getName() != null) && (!imSearchBean.getName().trim().equals(""))) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List tmpList1 = query1.list();
        if ((tmpList1 != null) && (tmpList1.size() > 0)) {
            return (Long) tmpList1.get(0);
        }
        return null;
    }

    public boolean insertStockOwnerTmp(Long ownerId, String ownerType, String staffCode, String staffName, Long channelTypeId) throws Exception {
        String sql = "From StockOwnerTmp where ownerId = ? and ownerType = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        List list = query.list();
        if ((list == null) || (list.isEmpty())) {
            StockOwnerTmp stockOwnerTmp = new StockOwnerTmp();
            stockOwnerTmp.setStockId(Long.valueOf(getSequence("STOCK_OWNER_TMP_SEQ")));
            stockOwnerTmp.setChannelTypeId(channelTypeId);
            stockOwnerTmp.setCode(staffCode);
            stockOwnerTmp.setName(staffName);
            stockOwnerTmp.setOwnerId(ownerId);
            stockOwnerTmp.setOwnerType(ownerType);
            getSession().save(stockOwnerTmp);
        } else {
            StockOwnerTmp stockOwnerTmp = (StockOwnerTmp) list.get(0);
            stockOwnerTmp.setName(staffName);
            getSession().update(stockOwnerTmp);
        }
        return true;
    }

    public String showLogAccountAgent() throws Exception {
        this.log.debug("# Begin method ReportChangeGood.preparePage");
        HttpServletRequest r = getRequest();
        Long accountId = Long.valueOf(Long.parseLong(r.getParameter("accountId").toString()));
        String sqlApp = "From AppParams where id.type = ?";
        Query queryApp = getSession().createQuery(sqlApp);
        queryApp.setParameter(0, "ACTION_LOG_STAFF");
        List listApp = queryApp.list();
        String actionType = "";
        List listActionType = new ArrayList();
        for (int i = 0; i < listApp.size(); i++) {
            listActionType.add(((AppParams) listApp.get(i)).getValue());
        }
        String sql = "From ActionLog where objectId =? AND actionType in (:a)  order by actionDate desc ";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, accountId);
        query.setParameterList("a", listActionType);
        List listLog = query.list();
        setTabSession("listLog", listLog);
        removeTabSession("listLogDetail");
        this.pageForward = "showLogAccount";
        this.log.debug("# End method ReportChangeGood.preparePage");
        return this.pageForward;
    }

    public String getLogDetail() throws Exception {
        this.log.debug("# Begin method ReportChangeGood.preparePage");
        HttpServletRequest req = getRequest();
        Long actionId = Long.valueOf(Long.parseLong(req.getParameter("actionId").toString()));
        String sql = "From ActionLogDetail where actionId =?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, actionId);
        List listLogDetail = query.list();
        setTabSession("listLogDetail", listLogDetail);
        this.pageForward = "showLogDetail";
        this.log.debug("# End method ReportChangeGood.preparePage");
        return this.pageForward;
    }

    public Area getArea(String areaCode) {
        if ((areaCode == null) || (areaCode.trim().equals(""))) {
            return null;
        }
        AreaDAO areaDAO = new AreaDAO();
        areaDAO.setSession(getSession());
        return areaDAO.findByAreaCode(areaCode.toUpperCase());
    }

    public String updateAddress() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String provinceCode = httpServletRequest.getParameter("provinceCode");
            String districtCode = httpServletRequest.getParameter("districtCode");
            String precinctCode = httpServletRequest.getParameter("precinctCode");

            String streetBlockName = httpServletRequest.getParameter("streetBlockName");
            String streetName = httpServletRequest.getParameter("streetName");
            String home = httpServletRequest.getParameter("home");
            String target = httpServletRequest.getParameter("target");

            String address = "";
            if ((provinceCode != null) && (!provinceCode.trim().equals(""))) {
                Area provinceArea = getArea(provinceCode.trim());
                if ((provinceArea != null) && (districtCode != null) && (!districtCode.trim().equals(""))) {
                    Area districtArea = getArea(provinceArea.getAreaCode() + districtCode.trim());
                    if ((districtArea != null) && (precinctCode != null) && (!precinctCode.trim().equals(""))) {
                        Area precinctArea = getArea(districtArea.getAreaCode() + precinctCode.trim());
                        if (precinctArea != null) {
                            address = precinctArea.getFullName();
                            if ((streetBlockName != null) && (!streetBlockName.trim().equals(""))) {
                                address = streetBlockName.trim() + " - " + address;
                                if ((streetName != null) && (!streetName.trim().equals(""))) {
                                    address = streetName.trim() + " - " + address;
                                    if ((home != null) && (!home.trim().equals(""))) {
                                        address = home.trim() + " - " + address;
                                    }
                                }
                            }
                            this.hashMapResult.put(target, address);
                        } else {
                            this.hashMapResult.put(target, districtArea.getFullName());
                        }
                    } else {
                        this.hashMapResult.put(target, districtArea.getFullName());
                    }
                } else {
                    this.hashMapResult.put(target, provinceArea.getFullName());
                }
            } else {
                this.hashMapResult.put(target, "");
            }
        } catch (Exception ex) {
            throw ex;
        }

        return "updateAddress";
    }

    public String destroy() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        try {
            Long staffId = this.addStaffCodeCTVDBForm.getStaffId();
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            Staff staff = staffDAO.findById(staffId);

            String sql = "From AccountAgent where ownerId = ? and ownerType = ? and status <> " + Constant.ACCOUNT_STATUS_INACTIVE.toString();
            Query query = getSession().createQuery(sql);
            query.setParameter(0, staff.getStaffId());
            query.setParameter(1, Constant.OWNER_TYPE_STAFF);
            List list = query.list();
            if ((list != null) && (list.size() > 0)) {
                req.setAttribute("messageUpdate", "ERR.CHL.115");
                return "listChangeInfoStaff";
            }
            ChannelDAO channelDAO = new ChannelDAO();
            channelDAO.setSession(getSession());

            if (!channelDAO.checkStockTotal(staff.getStaffId(), Constant.OWNER_TYPE_STAFF, Constant.STATE_NEW)) {
                req.setAttribute("messageUpdate", "ERR.CHL.146");
                return "listChangeInfoStaff";
            }

            if (!channelDAO.checkInvoiceUsed(staff.getShopId(), staff.getStaffId(), 45)) {
                req.setAttribute("messageUpdate", "ERR.CHL.147");
                return "listChangeInfoStaff";
            }

            staff.setStatus(Constant.STAFF_STATUS_DELETE);
            getSession().update(staff);

            Staff oldStaff = new Staff();
            BeanUtils.copyProperties(oldStaff, staff);
            String actionType = "";
            String messageLog = "";
            if ((staff.getStaffOwnerId() != null) && (!staff.getStaffOwnerId().equals(Long.valueOf(0L)))) {
                actionType = "ACTION_CHANGE_INFO_STAFF";
                messageLog = "Destroy Channel";
            }
            List lstLogObj = new ArrayList();
            lstLogObj.add(new ActionLogsObj(oldStaff, staff));
            saveLog(actionType, messageLog, lstLogObj, staff.getStaffId());

            resetForm();
            setTabSession("changeStatus", "true");
            setTabSession("changeInfo", "true");
            getSession().flush();
            getSession().getTransaction().commit();
            getSession().beginTransaction();
        } catch (Exception e) {
        }
        req.setAttribute("messageUpdate", "ERR.CHL.116");
        return "listChangeInfoStaff";
    }

    public String prepareAddStaff()
            throws Exception {
        try {
            this.pageForward = "prepareAddNewStaff";
            HttpServletRequest r = getRequest();
            UserToken userToken = (UserToken) r.getSession().getAttribute("userToken");
            if (userToken == null) {
                this.pageForward = "";
                return this.pageForward;
            }

            ShopDAO shopDAO = new ShopDAO();
            Shop sh = shopDAO.findById(userToken.getShopId());
            if (sh != null) {
                this.addStaffCodeCTVDBForm.setShopCode(sh.getShopCode());
                this.addStaffCodeCTVDBForm.setShopName(sh.getName());
            }

            StaffDAO stfDAO = new StaffDAO();
            Staff stf = stfDAO.findStaffAvailableByStaffCode(userToken.getLoginName());
            if (stf != null) {
                this.addStaffCodeCTVDBForm.setStaffManagementCode(stf.getStaffCode());
                this.addStaffCodeCTVDBForm.setStaffManagementName(stf.getName());
            }

            ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
            List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", Long.valueOf(0L));
            r.getSession().setAttribute("lstChannelTypeCol", lstChannelTypeCol);

            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(getSession());

            r.getSession().setAttribute("listProfileState", appParamsDAO.findAppParamsList("PROFILE_STATE", Constant.STATUS_USE.toString()));
            r.getSession().setAttribute("listBoardState", appParamsDAO.findAppParamsList("BOARD_STATE", Constant.STATUS_USE.toString()));
            r.getSession().setAttribute("listStaffStatus", appParamsDAO.findAppParamsList("CHANNEL_STATUS", Constant.STATUS_USE.toString()));
            //vi dien tu
            r.getSession().setAttribute("lstChannelTypeWallet", appParamsDAO.findAppParamsList("CHANNEL_TYPE_WALLET", Constant.STATUS_USE.toString()));
            //TANNH :IS CHECK AGREE
            r.getSession().setAttribute("listChannelTypeAgree", appParamsDAO.findAppParamsList("IS_CHECK_AGREE", Constant.STATUS_USE.toString()));

            this.addStaffCodeCTVDBForm.setSysDate(getSysdate());

            if ((sh != null) && (sh.getProvince() != null) && (!"".equals(sh.getProvince().trim()))) {
                String province = sh.getProvince().trim();
                Area provinceArea = getArea(province);
                if (provinceArea != null) {
                    this.addStaffCodeCTVDBForm.setProvinceCode(provinceArea.getAreaCode());
                    this.addStaffCodeCTVDBForm.setProvinceName(provinceArea.getName());
                }

            }

            checkRoleUser(userToken, r);
            return this.pageForward;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.pageForward;
    }

    private String validateAdd(Session smSession, String staffCode)
            throws Exception {
        String shopCode = this.addStaffCodeCTVDBForm.getShopCode();
        if ((shopCode == null) || ("".equals(shopCode))) {
            return "ERR.Shopcode.null";
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(smSession);

        Shop sh = shopDAO.findShopsAvailableByShopCode(shopCode);
        if (sh == null) {
            return "ERR.shop.notExist";
        }
        this.addStaffCodeCTVDBForm.setShopId(sh.getShopId());
        String idNo = this.addStaffCodeCTVDBForm.getIdNo();
        if ((idNo == null) || (idNo.trim().equals(""))) {
            return "ERR.STAFF.0025";
        }
        if (!CommonDAO.checkValidIdNo(idNo.trim())) {
            return "E.100005";
        }
        if (!new ChannelDAO().checkExistIdNoWithStaffCode(idNo, staffCode)) {
            return "ERR.STAFF.0040";
        }
        Long staffOwnerId = getStaffId(this.addStaffCodeCTVDBForm.getStaffManagementCode());
        if (staffOwnerId.equals(Long.valueOf(0L))) {
            return "ERR.CHL.088";
        }

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(smSession);

        Staff staffManagement = staffDAO.findById(staffOwnerId);
        if (staffManagement == null) {
            return "ERR.CHL.090";
        }
        if (staffManagement.getChannelTypeId() == null) {
            return "ERR.CHL.090";
        }
        ChannelType channelType = new ChannelTypeDAO().findById(staffManagement.getChannelTypeId());
        if ((channelType == null) || (channelType.getChannelTypeId() == null) || (!channelType.getIsVtUnit().equals("1")) || (!channelType.getIsPrivate().equals(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE)) || (!channelType.getObjectType().equals("2"))) {
            return "ERR.CHL.090";
        }
        String channel = this.addStaffCodeCTVDBForm.getChannelType();
        if ((channel == null) || ("".equals(channel))) {
            return "ERR.CHL.channelNull";
        }
        ChannelType channelTypeOfStaff = new ChannelTypeDAO().findById(Long.valueOf(channel));
        if ((channelTypeOfStaff == null) || (channelTypeOfStaff.getChannelTypeId() == null)) {
            return "ERR.CHL.channelNull";
        }

        if ((channelTypeOfStaff.getRoleCreateChannel() != null) && (!channelTypeOfStaff.getRoleCreateChannel().trim().equals(""))) {
            System.out.println("role = " + channelTypeOfStaff.getRoleCreateChannel().trim());
            if (!AuthenticateDAO.checkAuthority(channelTypeOfStaff.getRoleCreateChannel().trim(), this.req)) {
                return "E.200044";
            }

        }
//        LinhNBV20190715: Check permission create VP channel (VIP_CHANNEL_PERMISSION) & HandsetChannel (1000741) and Master agent channel (1000485)
        DbProcessor db = new DbProcessor();
        if ("1000641".equals(addStaffCodeCTVDBForm.getChannelType())) {
            log.info("Channel type is Vip Channel, start check permission create channel...staffCode: " + staffCode);
            if (!db.checkPermissionCreateVipChannel(staffCode)) {
                return "ERR.SIK.160";
            }
        } else if ("1000741".equals(addStaffCodeCTVDBForm.getChannelType()) || "1000485".equals(addStaffCodeCTVDBForm.getChannelType())) {
            log.info("Channel type in HS, MA, start check permission create channel...staffCode: " + staffCode);
            if (!db.checkPermissionCreateChannel(staffCode, Long.valueOf(addStaffCodeCTVDBForm.getChannelType()))) {
                return "ERR.SIK.160";
            }
        } else {
            log.info("Channel type not in VP, MA, HS, no need to check permission create channel...staffCode: " + staffCode);
        }

        if ((this.addStaffCodeCTVDBForm.getProvinceCode() == null) || (this.addStaffCodeCTVDBForm.getProvinceCode().trim().equals(""))) {
            return "ERR.SIK.006";
        }
        if ((this.addStaffCodeCTVDBForm.getDistrictCode() == null) || (this.addStaffCodeCTVDBForm.getDistrictCode().trim().equals(""))) {
            return "ERR.SIK.007";
        }
        if ((this.addStaffCodeCTVDBForm.getPrecinctCode() == null) || (this.addStaffCodeCTVDBForm.getPrecinctCode().trim().equals(""))) {
            return "ERR.SIK.008";
        }
        Area provinceArea = getArea(this.addStaffCodeCTVDBForm.getProvinceCode().trim());
        if (provinceArea == null) {
            return "ERR.SIK.152";
        }
        Area districtArea = getArea(this.addStaffCodeCTVDBForm.getProvinceCode().trim() + this.addStaffCodeCTVDBForm.getDistrictCode().trim());

        if (districtArea == null) {
            return "ERR.SIK.153";
        }
        Area precinctArea = getArea(this.addStaffCodeCTVDBForm.getProvinceCode().trim() + this.addStaffCodeCTVDBForm.getDistrictCode().trim() + this.addStaffCodeCTVDBForm.getPrecinctCode().trim());
        if (precinctArea == null) {
            return "ERR.SIK.154";
        }
        if ((staffManagement != null) && (staffManagement.getProvince() != null) && (provinceArea != null) && (!staffManagement.getProvince().trim().equals(provinceArea.getProvince()))) {
            return "ERR.STAFF.0030";
        }
        //Check trung so dien thoai
//        String tel = this.addStaffCodeCTVDBForm.getTel();
//        tel = tel == null ? "" : tel.trim();
//        if (!tel.equals("")) {
//            if (!new ChannelDAO().checkExistTelWithStaffCode(tel, staffCode)) {
//                return "ERR.STAFF.0048";
//            }
//        }
        this.addStaffCodeCTVDBForm.setStaffOwnerId(staffManagement.getStaffId());

        return "";
    }

    private Shop getShop(String shopCode) throws Exception {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop;
        }
        return null;
    }

    public String addStaff()
            throws Exception {

        ResponseWallet responseWallet = new ResponseWallet();
        try {
            Session smSession = getSession();
            this.pageForward = "prepareAddNewStaff";
            HttpServletRequest r = getRequest();
            UserToken userToken = (UserToken) r.getSession().getAttribute("userToken");
            List lstLogObj = new ArrayList();

            String errValidate = validateAdd(smSession, userToken.getLoginName());
            if ((errValidate != null) && (!"".equals(errValidate))) {
                r.setAttribute("messageAdd", errValidate);
                return this.pageForward;
            }
            //Phan quyen chi cap cong ty va chi nhanh moi tao kenh SA, MA
            Shop shopUser = getShop(userToken.getShopCode());
            String channelType = this.addStaffCodeCTVDBForm.getChannelType();
            Long channelTypeId = Long.parseLong(channelType);
            if (channelTypeId.equals(Constant.CHANNEL_TYPE_ID_SA) || channelTypeId.equals(Constant.CHANNEL_TYPE_ID_MA)) {
                if (!shopUser.getShopId().equals(Constant.VT_SHOP_ID) && !shopUser.getParentShopId().equals(Constant.VT_SHOP_ID)) {
                    r.setAttribute("messageAdd", "err.right.create.channel");
                    return this.pageForward;
                }
            }

            Map staffHashMap = new HashMap();
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            ChannelType objectType = channelTypeDAO.findById(Long.valueOf(Long.parseLong(this.addStaffCodeCTVDBForm.getChannelType())));

            Long staffOwnerId = getStaffId(this.addStaffCodeCTVDBForm.getStaffManagementCode());
            if (staffOwnerId.equals(Long.valueOf(0L))) {
                r.setAttribute("messageAdd", "ERR.CHL.088");
                return this.pageForward;
            }

            Long defaultRole = null;
            if (ConfigParam.CHECK_ROLE_STK) {
                AppParamsDAO appParamDAO = new AppParamsDAO();
                appParamDAO.setSession(getSession());
                AppParams appParam = appParamDAO.findAppParams("CHANNEL_DEFAULT_ROLE", DataUtil.safeToString(objectType.getChannelTypeId()));
                try {
                    defaultRole = Long.valueOf(Long.parseLong(appParam.getValue()));
                } catch (Exception ex) {
                    defaultRole = null;
                }

                if ((appParam == null) || (defaultRole == null)) {
                    r.setAttribute("messageAdd", getText("ERR.STAFF.0047"));
                    return this.pageForward;
                }

            }

            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(smSession);
            Staff staffManagement = staffDAO.findById(staffOwnerId);
            if (staffManagement == null) {
                r.setAttribute("messageAdd", "ERR.CHL.052");
                return this.pageForward;
            }
            Shop shop = getShop(this.addStaffCodeCTVDBForm.getShopCode());

            if (shop == null) {
                r.setAttribute("messageAdd", "ERR.CHL.045");
                return this.pageForward;
            }
            if (!staffManagement.getShopId().equals(shop.getShopId())) {
                r.setAttribute("messageAdd", "ERR.StaffManager.staffNotInShop");
                return this.pageForward;
            }
            if (shop.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AGENT)) {
                r.setAttribute("messageAdd", "ERR.CHL.103");
                return this.pageForward;
            }

            //Check dieu kien vi
            if (this.addStaffCodeCTVDBForm.isChkWallet()) {
                String channelWallet = this.addStaffCodeCTVDBForm.getChannelWalletHide();
                this.addStaffCodeCTVDBForm.setChannelWallet(channelWallet);
                String parentCodeWallet = this.addStaffCodeCTVDBForm.getParentCodeWallet();
                parentCodeWallet = parentCodeWallet == null ? "" : parentCodeWallet.trim();
                if (!channelWallet.equals("SA")) {
                    Staff parentWallet = staffDAO.findParentWalletByStaffCode(parentCodeWallet, channelWallet);
//                    if (parentWallet == null) {
//                        r.setAttribute("messageAdd", "err.channel.wallet.parent");
//                        return this.pageForward;
//                    }
//                    this.addStaffCodeCTVDBForm.setParentIdWallet(parentWallet.getStaffId());
                    if (parentWallet != null) {
                        this.addStaffCodeCTVDBForm.setParentIdWallet(parentWallet.getStaffId());
                    }
                }
                String isdnWallet = this.addStaffCodeCTVDBForm.getIsdnWallet();
                isdnWallet = isdnWallet == null ? "0" : isdnWallet.trim();

                String sql = "from Staff where isdnWallet = ? and status != ? ";
                Query query = getSession().createQuery(sql);
                query.setParameter(0, isdnWallet);
                query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
                List<Staff> listStaff = query.list();
                if (listStaff != null && listStaff.size() > 0) {
                    r.setAttribute("messageAdd", getText("err.isdn.wallet.duplicate") + " (staff_code = " + listStaff.get(0).getStaffCode() + ")");
                    return this.pageForward;
                }

                Byte subType = 1;
                sql = "From StockIsdnMobile where to_number(isdn) = ?";
                BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(isdnWallet));
                query = getSession().createQuery(sql);
                query.setParameter(0, isdnSearch);
                List<StockIsdnMobile> list = query.list();
                if (list != null && list.size() > 0) {
                    if (list.get(0).getIsdnType() != null) {
                        subType = Byte.valueOf(list.get(0).getIsdnType());
                    }
                } else {
                    r.setAttribute("messageAdd", "err.isdn.wallet");
                    return this.pageForward;
                }

                InterfaceCm inter = new InterfaceCm();
                Object subInfo;
                subInfo = inter.getSubscriberInfoV2(isdnSearch.toString(), "M", subType);
                if (subInfo == null) {
                    r.setAttribute("messageAdd", "err.isdn.wallet");
                    return this.pageForward;
                }

                if (subType == 1) {
                    com.viettel.bccs.cm.database.BO.pre.SubMb subMb = (com.viettel.bccs.cm.database.BO.pre.SubMb) subInfo;
                    if (subMb.getActStatus() != null && (subMb.getActStatus().equals("01")
                            || subMb.getActStatus().equals("10") || subMb.getActStatus().equals("02")
                            || subMb.getActStatus().equals("20"))) {
                        r.setAttribute("messageAdd", "err.isdn.wallet.blocked");
                        return this.pageForward;
                    } else if (subMb.getActStatus() != null && subMb.getActStatus().equals("03")) {
                        r.setAttribute("messageAdd", "err.isdn.wallet.inActive");
                        return this.pageForward;
                    } else if (subMb.getActStatus() == null) {
                        r.setAttribute("messageAdd", "err.isdn.wallet");
                        return this.pageForward;
                    }
                }
            }
            String province = shop.getProvince();
            Area provinceArea = null;
            ChannelDAO channelDAO = new ChannelDAO();
            provinceArea = channelDAO.getArea(province);
            String staffCode = "";

            staffCode = channelDAO.getStaffCodeSeqIsNotVt(provinceArea.getProvinceReference() + objectType.getPrefixObjectCode(), staffHashMap);
            System.out.println("Staff code " + staffCode);

            Staff stf = insertStaff(smSession, this.addStaffCodeCTVDBForm, userToken, staffCode, objectType);
            //DINHDC ADD goi API them moi kenh vi
            if (stf == null) {
                r.setAttribute("messageAdd", "create.staff.fail");
                return this.pageForward;
            }
            if (this.addStaffCodeCTVDBForm.isChkWallet()) {
                if (stf != null && stf.getStaffId() != null && stf.getStaffId() > 0 && stf.getIsdnWallet() != null) {
                    String dob = "";
                    String idIssueDate = "";
                    String parentIdWallet = "";
                    String strShopId = "";
                    if (this.addStaffCodeCTVDBForm.getBirthday() != null) {
                        dob = DateTimeUtils.convertDateTimeToString(this.addStaffCodeCTVDBForm.getBirthday(), "ddMMyyyy");
                        //dob = this.addStaffCodeCTVDBForm.getBirthday().toString();
                    }
                    if (this.addStaffCodeCTVDBForm.getMakeDate() != null) {
                        idIssueDate = DateTimeUtils.convertDateTimeToString(DateTimeUtils.convertStringToDate(this.addStaffCodeCTVDBForm.getMakeDate()), "ddMMyyyy");
                        //idIssueDate = DateTimeUtils.convertStringToDate(this.addStaffCodeCTVDBForm.getMakeDate()).toString();
                    }
                    if (stf.getParentIdWallet() != null) {
                        parentIdWallet = stf.getParentIdWallet().toString();
                    }
                    if (stf.getShopId() != null) {
                        strShopId = stf.getShopId().toString();
                    }

                    String response = functionChannelWallet(smSession, stf.getIsdnWallet(), this.addStaffCodeCTVDBForm.getStaffName(), "1",
                            dob, "1", stf.getIdNo(), addStaffCodeCTVDBForm.getAddress(), "1", addStaffCodeCTVDBForm.getChannelWallet(), stf.getStaffId().toString(), parentIdWallet,
                            this.addStaffCodeCTVDBForm.getMakeAddress(), idIssueDate, stf.getStaffId().toString(), strShopId, addStaffCodeCTVDBForm.getVoucherCode());
                    System.out.println("response add wallet:" + response);
                    if ("ERROR".equals(response)) {
                        r.setAttribute("messageAdd", "Error.Call.WS.Wallet");
                        stf.setChannelWallet(null);
                        stf.setParentIdWallet(null);
                        stf.setIsdnWallet(null);
                        getSession().update(stf);
                        return "prepareAddNewStaff";
                    } else {
                        Gson gson = new Gson();
                        responseWallet = gson.fromJson(response, ResponseWallet.class);
                        if (responseWallet == null || responseWallet.getResponseCode() == null) {
                            insertLogCallWsWallet(smSession, stf.getIsdnWallet(), stf.getStaffId(), "1", 0L, 0L, getSysdate(), "Response Error AddStaff : " + response,
                                    stf.getName(), stf.getBirthday(), stf.getIdNo(), stf.getChannelWallet(), stf.getParentIdWallet(), stf.getIdIssuePlace(), stf.getIdIssueDate());

                            stf.setChannelWallet(null);
                            stf.setParentIdWallet(null);
                            stf.setIsdnWallet(null);
                            getSession().update(stf);
                            r.setAttribute("messageAdd", "Error.Call.WS.Wallet");
                            return "prepareAddNewStaff";
                        }
                    }
                }
            }
            if (ConfigParam.CHECK_ROLE_STK) {
                VsaUser vsaUser = new VsaUser();
                vsaUser.setUserName(stf.getStaffCode());
                vsaUser.setFullName(stf.getName());
                boolean result = VsaDAO.insertUser(getSession(), vsaUser);
                result = VsaDAO.assignRole(getSession(), vsaUser.getUserId(), defaultRole);
            }

            insertStockOwnerTmp(stf.getStaffId(), Constant.OWNER_TYPE_STAFF.toString(), staffCode, stf.getName(), stf.getChannelTypeId());
            getSession().getTransaction().commit();
            getSession().beginTransaction();

            Staff oldStaff = new Staff();

            String actionType = "";
            String message = "";
            if ((stf.getStaffOwnerId() != null) && (!stf.getStaffOwnerId().equals(Long.valueOf(0L)))) {
                actionType = "ACTION_ADD_COLLABORATOR";
                message = "Insert new information of Channel";
            } else {
                actionType = "ACTION_ADD_COLLABORATOR";
                message = "Insert new information of Empty Channel";
            }

            lstLogObj.add(new ActionLogsObj(oldStaff, stf));
            saveLog(actionType, message, lstLogObj, stf.getStaffId());
            List lstParam = new ArrayList();
            lstParam.add(staffCode);
            r.setAttribute("paramMsgAdd", lstParam);
            r.setAttribute("messageAdd", "msg.addStaff.successfully");
            //Hien thi ket qua thuc hien Add vi
            if (this.addStaffCodeCTVDBForm.isChkWallet()) {
                if (responseWallet != null && responseWallet.getResponseCode() != null) {
                    if (responseWallet.getResponseCode().equals("01")) {
                        r.setAttribute("messageAdd", "msg.addStaff.successfully");
                    } else {
                        r.setAttribute("messageAdd", "ERR.ADD.CHANNEL.WALLET.UNSUCCESS");
                        stf.setChannelWallet(null);
                        stf.setParentIdWallet(null);
                        stf.setIsdnWallet(null);
                        getSession().update(stf);
                        insertLogCallWsWallet(smSession, stf.getIsdnWallet(), stf.getStaffId(), "1", 0L, 0L, getSysdate(), responseWallet.getResponseMessage(),
                                stf.getName(), stf.getBirthday(), stf.getIdNo(), stf.getChannelWallet(), stf.getParentIdWallet(), stf.getIdIssuePlace(), stf.getIdIssueDate());
                        return "prepareAddNewStaff";
                    }
                } else {
                    r.setAttribute("messageAdd", "ERR.ADD.CHANNEL.WALLET.UNSUCCESS");
                    stf.setChannelWallet(null);
                    stf.setParentIdWallet(null);
                    stf.setIsdnWallet(null);
                    getSession().update(stf);
                    insertLogCallWsWallet(smSession, stf.getIsdnWallet(), stf.getStaffId(), "1", 0L, 0L, getSysdate(), "Error call WS Wallet",
                            stf.getName(), stf.getBirthday(), stf.getIdNo(), stf.getChannelWallet(), stf.getParentIdWallet(), stf.getIdIssuePlace(), stf.getIdIssueDate());
                    return "prepareAddNewStaff";
                }
            }

            return this.pageForward;
        } catch (Exception e) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            e.printStackTrace();
        }
        return this.pageForward;
    }

    private Staff insertStaff(Session s, AddStaffCodeCTVDBForm addStaffCodeCTVDBForm, UserToken u, String staffCode, ChannelType channelType)
            throws Exception {
        HttpServletRequest r = getRequest();
        Staff staff = new Staff();
        Long staffId = Long.valueOf(getSequence("STAFF_SEQ"));
        staff.setStaffId(staffId);
        if (addStaffCodeCTVDBForm.getShopId() != null) {
            staff.setShopId(addStaffCodeCTVDBForm.getShopId());
        }
        if ((addStaffCodeCTVDBForm.getChannelType() != null) && (!"".equals(addStaffCodeCTVDBForm.getChannelType()))) {
            Long channelTypeId = Long.valueOf(Long.parseLong(addStaffCodeCTVDBForm.getChannelType()));
            staff.setChannelTypeId(channelTypeId);
        }
        if (addStaffCodeCTVDBForm.getBirthday() != null) {
            staff.setBirthday(addStaffCodeCTVDBForm.getBirthday());
        }
        if (addStaffCodeCTVDBForm.getStaffOwnerId() != null) {
            staff.setStaffOwnerId(addStaffCodeCTVDBForm.getStaffOwnerId());
        }
        if ((addStaffCodeCTVDBForm.getStaffName() != null) && (!"".equals(addStaffCodeCTVDBForm.getStaffName()))) {
            staff.setName(addStaffCodeCTVDBForm.getStaffName().trim());
        }
        if ((staffCode != null) && (!"".equals(staffCode))) {
            staff.setStaffCode(staffCode);
        }

        if (channelType != null) {
            staff.setDiscountPolicy(channelType.getDiscountPolicyDefault());
            staff.setPricePolicy(channelType.getPricePolicyDefault());
        }
        if (staff.getDiscountPolicy() == null) {
            staff.setDiscountPolicy("1");
        }
        if (staff.getPricePolicy() == null) {
            staff.setPricePolicy("0");
        }

        if ((addStaffCodeCTVDBForm.getIdNo() != null) && (!"".equals(addStaffCodeCTVDBForm.getIdNo()))) {
            staff.setIdNo(addStaffCodeCTVDBForm.getIdNo().trim());
        }

        if (addStaffCodeCTVDBForm.getMakeDate() != null) {
            staff.setIdIssueDate(DateTimeUtils.convertStringToDate(addStaffCodeCTVDBForm.getMakeDate()));
        }
        if ((addStaffCodeCTVDBForm.getMakeAddress() != null) && (!"".equals(addStaffCodeCTVDBForm.getMakeAddress()))) {
            staff.setIdIssuePlace(addStaffCodeCTVDBForm.getMakeAddress().trim());
        }
        if ((addStaffCodeCTVDBForm.getAddress() != null) && (!"".equals(addStaffCodeCTVDBForm.getAddress()))) {
            staff.setAddress(addStaffCodeCTVDBForm.getAddress().trim());
        }

        if (addStaffCodeCTVDBForm.getStatus() != null) {
            staff.setStatus(addStaffCodeCTVDBForm.getStatus());
        }
        if ((addStaffCodeCTVDBForm.getIsdn() != null) && (!"".equals(addStaffCodeCTVDBForm.getIsdn()))) {
            staff.setIsdn(addStaffCodeCTVDBForm.getIsdn());
        }
        if (u != null) {
            staff.setLastUpdateUser(u.getLoginName());
        }

        staff.setLastUpdateTime(getSysdate());
        staff.setLastUpdateIpAddress(r.getRemoteAddr());
        //Lay truong nay lam kenh Key de quan ly
        if (addStaffCodeCTVDBForm.getLastUpdateKey() != null && !addStaffCodeCTVDBForm.getLastUpdateKey().equals("")) {
            staff.setLastUpdateKey(addStaffCodeCTVDBForm.getLastUpdateKey());
        } else {
            staff.setLastUpdateKey("Normal");
        }
        //Them truong de phan biet la diem DKTT
        if (addStaffCodeCTVDBForm.getRegistrationPoint() != null && !addStaffCodeCTVDBForm.getRegistrationPoint().equals("")) {
            staff.setRegistrationPoint(addStaffCodeCTVDBForm.getRegistrationPoint());
        }
        if ((addStaffCodeCTVDBForm.getUsedfulWidth() != null) && (!"".equals(addStaffCodeCTVDBForm.getUsedfulWidth()))) {
            staff.setUsedfulWidth(addStaffCodeCTVDBForm.getUsedfulWidth().trim());
        }
        if ((addStaffCodeCTVDBForm.getSurfaceArea() != null) && (!"".equals(addStaffCodeCTVDBForm.getSurfaceArea()))) {
            staff.setSurfaceArea(addStaffCodeCTVDBForm.getSurfaceArea().trim());
        }
//        if (addStaffCodeCTVDBForm.getRegistryDate() != null) {
//            staff.setRegistryDate(addStaffCodeCTVDBForm.getRegistryDate());
//        }
        staff.setRegistryDate(getSysdate());//mac dinh la ngay insert

        if ((addStaffCodeCTVDBForm.getTradeName() != null) && (!"".equals(addStaffCodeCTVDBForm.getTradeName()))) {
            staff.setTradeName(addStaffCodeCTVDBForm.getTradeName());
        }

        if ((addStaffCodeCTVDBForm.getContactName() != null) && (!"".equals(addStaffCodeCTVDBForm.getContactName()))) {
            staff.setContactName(addStaffCodeCTVDBForm.getContactName().trim());
        }
        if ((addStaffCodeCTVDBForm.getTel() != null) && (!"".equals(addStaffCodeCTVDBForm.getTel()))) {
            staff.setTel(addStaffCodeCTVDBForm.getTel().trim());
        }
        if (addStaffCodeCTVDBForm.getCheckVat() != null) {
            staff.setCheckVat(addStaffCodeCTVDBForm.getCheckVat());
        }
        if (addStaffCodeCTVDBForm.getAgentType() != null) {
            staff.setAgentType(addStaffCodeCTVDBForm.getAgentType());
        }

        if ((addStaffCodeCTVDBForm.getStreetBlockName() != null) && (!"".equals(addStaffCodeCTVDBForm.getStreetBlockName()))) {
            staff.setStreetBlockName(addStaffCodeCTVDBForm.getStreetBlockName().trim());
        }
        if ((addStaffCodeCTVDBForm.getStreetName() != null) && (!"".equals(addStaffCodeCTVDBForm.getStreetName()))) {
            staff.setStreetName(addStaffCodeCTVDBForm.getStreetName().trim());
        }
        if ((addStaffCodeCTVDBForm.getHome() != null) && (!"".equals(addStaffCodeCTVDBForm.getHome()))) {
            staff.setHome(addStaffCodeCTVDBForm.getHome().trim());
        }

        staff.setProfileState(addStaffCodeCTVDBForm.getProfileState());
        staff.setBoardState(addStaffCodeCTVDBForm.getBoardState());
        staff.setProvince(addStaffCodeCTVDBForm.getProvinceCode());
        staff.setDistrict(addStaffCodeCTVDBForm.getDistrictCode());
        staff.setPrecinct(addStaffCodeCTVDBForm.getPrecinctCode());
        if ((addStaffCodeCTVDBForm.getNote() != null) && (!"".equals(addStaffCodeCTVDBForm.getNote()))) {
            staff.setNote(addStaffCodeCTVDBForm.getNote().trim());
        }
        staff.setBtsCode(addStaffCodeCTVDBForm.getBtsCode());
        //vi dien tu
        if (addStaffCodeCTVDBForm.isChkWallet()) {
            staff.setChannelWallet(addStaffCodeCTVDBForm.getChannelWallet().trim());
            staff.setParentIdWallet(addStaffCodeCTVDBForm.getParentIdWallet());
            staff.setIsdnWallet(addStaffCodeCTVDBForm.getIsdnWallet().trim());
        }

        s.save(staff);
        return staff;
    }

    public String cancelStaff() throws Exception {
        this.pageForward = "addStaffSuccess";
        try {
            return this.pageForward;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.pageForward;
    }

    private void checkRoleUser(UserToken u, HttpServletRequest req) {
        req.getSession().setAttribute("isEditShop", Boolean.valueOf(false));
        if (!AuthenticateDAO.checkAuthority(this.ROLE_F9_SHOP, req)) {
            req.getSession().setAttribute("isEditShop", Boolean.valueOf(true));
        }
        req.getSession().setAttribute("isEditStaff", Boolean.valueOf(false));
        if (!AuthenticateDAO.checkAuthority(this.ROLE_F9_STAFF, req)) {
            req.getSession().setAttribute("isEditStaff", Boolean.valueOf(true));
        }
    }

    public List<ImSearchBean> getListStaffManageWallet(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listImSearchBean = new ArrayList();
        List listParameter1 = new ArrayList();

        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a WHERE 1 = 1 ");

        strQuery1.append(" AND a.shopId=? and a.status = 1 and a.channelWallet = ?  ");
        if ((imSearchBean.getOtherParamValue() != null) && (!imSearchBean.getOtherParamValue().trim().equals(""))) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");
            if (index == 0 || index == otherParam.length() - 1) {
                return listImSearchBean;
            } else {
                String shopCode = otherParam.substring(0, index).toLowerCase();
                listParameter1.add(getShopId(shopCode));
                String channelWallet = otherParam.substring(index + 1, otherParam.length()).toLowerCase();
                listParameter1.add(getParentChannelWallet(channelWallet));
            }
        } else {
            return listImSearchBean;
        }

        if ((imSearchBean.getCode() != null) && (!imSearchBean.getCode().trim().equals(""))) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if ((imSearchBean.getName() != null) && (!imSearchBean.getName().trim().equals(""))) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(Long.valueOf(300L));

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List tmpList1 = query1.list();
        if ((tmpList1 != null) && (tmpList1.size() > 0)) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    public Long getListStaffManageWalletSize(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest r = imSearchBean.getHttpServletRequest();

        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select count(*) ");
        strQuery1.append("from Staff a WHERE 1 = 1 ");

        strQuery1.append(" AND a.shopId=? and a.status = 1 and a.channelWallet = ?  ");
        if ((imSearchBean.getOtherParamValue() != null) && (!imSearchBean.getOtherParamValue().trim().equals(""))) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");
            if (index == 0 || index == otherParam.length() - 1) {
                return 0L;
            } else {
                String shopCode = otherParam.substring(0, index).toLowerCase();
                listParameter1.add(getShopId(shopCode));
                String channelWallet = otherParam.substring(index + 1, otherParam.length()).toLowerCase();
                listParameter1.add(getParentChannelWallet(channelWallet));
            }
        } else {
            return null;
        }
        if ((imSearchBean.getCode() != null) && (!imSearchBean.getCode().trim().equals(""))) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if ((imSearchBean.getName() != null) && (!imSearchBean.getName().trim().equals(""))) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List tmpList1 = query1.list();
        if ((tmpList1 != null) && (tmpList1.size() > 0)) {
            return (Long) tmpList1.get(0);
        }
        return null;
    }

    public String getParentChannelWallet(String channelWallet) throws Exception {
        if (channelWallet == null) {
            return "";
        }
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        String level = appParamsDAO.findValueAppParams("CHANNEL_TYPE_WALLET", channelWallet.trim().toUpperCase());
        if (level.equals("")) {
            return level;
        }
        try {
            String sql = "select code from app_params where type = 'CHANNEL_TYPE_WALLET' and to_number(?) -  to_number(value) = 1";
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, level);
            List list = query.list();
            if (list != null && !list.isEmpty()) {
                return (String) list.get(0);
            }
        } catch (Exception e) {
        }
        return "";
    }
    //Lay danh sach kenh vi con

    public List<Long> getListSubChannelWallet(Long parentWalletId) {
        List<Long> listStaffId = new ArrayList<Long>();
        try {
            String sql = "SELECT STAFF_ID FROM Staff WHERE PARENT_ID_WALLET = ? ";
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, parentWalletId);
            listStaffId = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listStaffId;
    }

    //goi WS thuc hien cac ham ben vi theo progressType (1:tao moi kenh 2:thay doi thong tin 3:huy kenh 4:thay doi isdn kenh vi)
    public String functionChannelWallet(Session imSession, String mobile, String customerName, String gender, String doB, String idType,
            String idNo, String address, String progressType, String channelType, String ewalletId, String parentId, String idIssuePlace,
            String idIssueDate, String id, String shopId, String voucherCode) throws Exception {

        String request = "";
        Long ewallet_Id = 0L;
        Date birthday = getSysdate();
        Long parent_id = 0L;
        Date issueDate = getSysdate();
        try {
            String content = null;
            if (ewalletId != null && !ewalletId.equals("")) {
                ewallet_Id = Long.valueOf(ewalletId);
            }
            if (parentId != null && !parentId.equals("")) {
                parent_id = Long.valueOf(parentId);
            }
            if (doB != null && !doB.equals("")) {
                birthday = DateTimeUtils.convertStringToDate(doB);
            }
            if (idIssueDate != null && !idIssueDate.equals("")) {
                issueDate = DateTimeUtils.convertStringToDate(idIssueDate);
            }
            String BASE_URL = ResourceBundleUtils.getResource("CreateWalletForSubscriber_wsdlUrl");
            String API = ResourceBundleUtils.getResource("API");
            String userNameString = ResourceBundleUtils.getResource("UserName");
            String pasString = ResourceBundleUtils.getResource("PassWord");
            String funString = "SyncChannel";
            ChannelWalletBean channelWallet = new ChannelWalletBean();
            channelWallet.setMobile(mobile);
            channelWallet.setCustomerName(customerName);
            channelWallet.setGender(gender);
            channelWallet.setDoB(doB);
            channelWallet.setIdType(idType);
            channelWallet.setIdNo(idNo);
            channelWallet.setAddress(address);
            channelWallet.setProgressType(progressType);
            channelWallet.setChannelType(channelType);
            channelWallet.setEwalletId(ewalletId);
            channelWallet.setParentId(parentId);
            channelWallet.setIdIssuePlace(idIssuePlace);
            channelWallet.setIdIssueDate(idIssueDate);
            channelWallet.setShopId(shopId);
            channelWallet.setVoucherCode(voucherCode);

            Gson gson = new Gson();
            request = gson.toJson(channelWallet, ChannelWalletBean.class);

            // set the connection timeout value to 60 seconds (60000 milliseconds)
            final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 60000);
            HttpConnectionParams.setSoTimeout(httpParams, 60000);
            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost(BASE_URL + API);
            List nameValuePairs = new ArrayList();
            TextSecurity sec = TextSecurity.getInstance();
            String str = pasString + "|" + id;
            System.out.println("ID:" + id);
            String passEncrypt = sec.Encrypt(str);
            nameValuePairs.add(new BasicNameValuePair("Username", userNameString));
            nameValuePairs.add(new BasicNameValuePair("Password", passEncrypt));
            nameValuePairs.add(new BasicNameValuePair("FunctionName", funString));
            nameValuePairs.add(new BasicNameValuePair("RequestId", id));
            nameValuePairs.add(new BasicNameValuePair("FunctionParams", request));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder sb = new StringBuilder();

            String output;
            while ((output = rd.readLine()) != null) {
                sb.append(output);
            }
            content = sb.toString();

            return content;
        } catch (Exception ex) {
            insertLogCallWsWallet(imSession, mobile, ewallet_Id, progressType, 0L, 0L, getSysdate(), request + "---Exception---" + ex, customerName, birthday, idNo, channelType, parent_id, idIssuePlace, issueDate);
            if ((imSession != null) && (imSession.isConnected())) {
                imSession.getTransaction().commit();
                imSession.beginTransaction();
            }
            return "ERROR";
        }
    }
    //Lay thong tin kenh vi tra ve 1 chuoi String dang Json

    public String viewInfoChannelWallet(Session imSession, String mobile, String id) throws Exception {

        String request = "";
        try {
            String content = null;
            String BASE_URL = ResourceBundleUtils.getResource("CreateWalletForSubscriber_wsdlUrl");
            String API = ResourceBundleUtils.getResource("API");
            String userNameString = ResourceBundleUtils.getResource("UserName");
            String pasString = ResourceBundleUtils.getResource("PassWord");
            String funString = "CheckInfo";
            MobileWallet mobileWallet = new MobileWallet();
            mobileWallet.setMobile(mobile);

            Gson gson = new Gson();
            request = gson.toJson(mobileWallet, MobileWallet.class);

            // set the connection timeout value to 60 seconds (60000 milliseconds)
            final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 60000);
            HttpConnectionParams.setSoTimeout(httpParams, 60000);
            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost(BASE_URL + API);
            List nameValuePairs = new ArrayList();
            TextSecurity sec = TextSecurity.getInstance();
            String str = pasString + "|" + id;
            System.out.println("ID:" + id);
            String passEncrypt = sec.Encrypt(str);
            nameValuePairs.add(new BasicNameValuePair("Username", userNameString));
            nameValuePairs.add(new BasicNameValuePair("Password", passEncrypt));
            nameValuePairs.add(new BasicNameValuePair("FunctionName", funString));
            nameValuePairs.add(new BasicNameValuePair("RequestId", id));
            nameValuePairs.add(new BasicNameValuePair("FunctionParams", request));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder sb = new StringBuilder();

            String output;
            while ((output = rd.readLine()) != null) {
                sb.append(output);
            }
            content = sb.toString();

            return content;
        } catch (Exception ex) {
            return "ERROR";
        }
    }

    //DINHDC ADD 20160519 lay thong tin tai khoan vi
    public Double getBalanceFromWallet(Session imSession, String mobile, String staffId) throws Exception {
        Double balance = 0d;
        try {
            ResultViewInforWallet resultViewInforWallet = new ResultViewInforWallet();

            String response = viewInfoChannelWallet(imSession, mobile, staffId);
            Gson gson = new Gson();
            resultViewInforWallet = gson.fromJson(response, ResultViewInforWallet.class);
            if (resultViewInforWallet != null) {
                if (resultViewInforWallet.getResponseCode() != null && resultViewInforWallet.getResponseCode().equals("01")) {
                    balance = Double.parseDouble(resultViewInforWallet.getBalance());
                } else {
                    balance = 0d;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return balance;
    }

    //DINHDC ADD 20160527 goi WS thuc hien thay doi loai kenh vi
    public String funcChangeChannelTypeWallet(Session imSession, String mobile, String progressType, String newChannelType, String ewalletId, String newParentId, String id) throws Exception {

        String request = "";
        Long ewallet_Id = 0L;
        Long parent_id = 0L;
        String custName = "";
        String idNo = "";
        String issuePlace = "";
        Date dob = getSysdate();
        Date issueDate = getSysdate();
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(imSession);
        Staff staff = new Staff();
        try {
            String content = null;
            if (ewalletId != null && !ewalletId.equals("")) {
                ewallet_Id = Long.valueOf(ewalletId);
                staff = staffDAO.findById(ewallet_Id);
            }
            if (newParentId != null && !newParentId.equals("")) {
                parent_id = Long.valueOf(newParentId);
            }
            if (staff != null && staff.getStaffId() > 0) {
                custName = staff.getName();
                idNo = staff.getIdNo();
                issuePlace = staff.getIdIssuePlace();
                dob = staff.getBirthday();
                issueDate = staff.getIdIssueDate();
            }
            String BASE_URL = ResourceBundleUtils.getResource("CreateWalletForSubscriber_wsdlUrl");
            String API = ResourceBundleUtils.getResource("API");
            String userNameString = ResourceBundleUtils.getResource("UserName");
            String pasString = ResourceBundleUtils.getResource("PassWord");
            String funString = "ChangeChannelType";
            ChangeChannelTypeWallet channelTypeWallet = new ChangeChannelTypeWallet();
            channelTypeWallet.setMobile(mobile);
            channelTypeWallet.setNewChannelType(newChannelType);
            channelTypeWallet.setEwalletId(ewalletId);
            channelTypeWallet.setNewParentId(newParentId);

            Gson gson = new Gson();
            request = gson.toJson(channelTypeWallet, ChangeChannelTypeWallet.class);
            // set the connection timeout value to 60 seconds (60000 milliseconds)
            final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 60000);
            HttpConnectionParams.setSoTimeout(httpParams, 60000);
            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost(BASE_URL + API);
            List nameValuePairs = new ArrayList();
            TextSecurity sec = TextSecurity.getInstance();
            String str = pasString + "|" + id;
            System.out.println("ID:" + id);
            String passEncrypt = sec.Encrypt(str);
            nameValuePairs.add(new BasicNameValuePair("Username", userNameString));
            nameValuePairs.add(new BasicNameValuePair("Password", passEncrypt));
            nameValuePairs.add(new BasicNameValuePair("FunctionName", funString));
            nameValuePairs.add(new BasicNameValuePair("RequestId", id));
            nameValuePairs.add(new BasicNameValuePair("FunctionParams", request));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder sb = new StringBuilder();

            String output;
            while ((output = rd.readLine()) != null) {
                sb.append(output);
            }
            content = sb.toString();

            return content;
        } catch (Exception ex) {
            insertLogCallWsWallet(imSession, mobile, ewallet_Id, progressType, 0L, 0L, getSysdate(), request, custName, dob, idNo, newChannelType, parent_id, issuePlace, issueDate);
            if ((imSession != null) && (imSession.isConnected())) {
                imSession.getTransaction().commit();
                imSession.beginTransaction();
            }
            return "ERROR";
        }
    }

    public void insertLogCallWsWallet(Session imSession, String isdn, Long ewalletId, String actionType, Long statusProcess,
            Long numberProcess, Date insertDate, String description, String customerName, Date doB, String idNo,
            String channelType, Long parentId, String idIssuePlace, Date idIssueDate) {
        try {
            LogCallWsWallet logCallWsWallet = new LogCallWsWallet();
            Long id = getSequence("LOG_CALL_WS_WALLET_SEQ");
            logCallWsWallet.setId(id);
            logCallWsWallet.setIsdn(isdn);
            logCallWsWallet.setEwalletId(ewalletId);
            logCallWsWallet.setActionType(actionType);
            logCallWsWallet.setStatusProcess(statusProcess);
            logCallWsWallet.setNumberProcess(numberProcess);
            logCallWsWallet.setInsertDate(insertDate);
            logCallWsWallet.setDescription(description);
            logCallWsWallet.setCustomerName(customerName);
            logCallWsWallet.setDoB(doB);
            logCallWsWallet.setIdNo(idNo);
            logCallWsWallet.setChannelType(channelType);
            logCallWsWallet.setParentId(parentId);
            logCallWsWallet.setIdIssuePlace(idIssuePlace);
            logCallWsWallet.setIdIssueDate(idIssueDate);
            imSession.save(logCallWsWallet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //lay danh sach tram BTS theo tinh va huyen

    public List<ImSearchBean> getListBTS(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.btsCode, a.fullAddress) ");
        queryString.append("from BtsDKTT a where 1=1 ");
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.btsCode) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.fullAddress) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String tmp = imSearchBean.getOtherParamValue().trim();
            String province = "";
            String district = "";
            if (tmp.split(";").length == 2) {
                province = tmp.split(";")[0];
                district = tmp.split(";")[1];
            }
            queryString.append("and upper(a.areaCode) = ? ");
            listParameter.add(province.trim().toUpperCase() + district.trim().toUpperCase());
        } else {
            queryString.append("and upper(a.areaCode) is null ");
        }

        queryString.append("and rownum <= ? ");
        listParameter.add(150L);
        queryString.append("order by a.btsCode ");
        Query queryObject = getSession().createQuery(queryString.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));
        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<ImSearchBean> tmpList = queryObject.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }
        return listImSearchBean;
    }

    public Long getListBTSSize(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter1 = new ArrayList();
        StringBuffer queryString = new StringBuffer("select count(*) ");
        queryString.append("from BtsDKTT a where 1=1 ");
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.btsCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.fullAddress) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String tmp = imSearchBean.getOtherParamValue().trim();
            String province = "";
            String district = "";
            if (tmp.split(";").length == 2) {
                province = tmp.split(";")[0];
                district = tmp.split(";")[1];
            }
            queryString.append("and upper(a.areaCode) = ? ");
            listParameter1.add(province.trim().toUpperCase() + district.trim().toUpperCase());
        } else {
            queryString.append("and upper(a.areaCode) is null ");
        }

        queryString.append("and rownum <= ? ");
        listParameter1.add(150L);
        queryString.append("order by a.btsCode ");
        Query query1 = getSession().createQuery(queryString.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List tmpList1 = query1.list();
        if ((tmpList1 != null) && (tmpList1.size() > 0)) {
            return (Long) tmpList1.get(0);
        }
        return null;
    }

    public boolean isCheckLimitSM(String staffCode) {
        try {
            String sql = "select * from staff_sm_role where staff_code = ? ";
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, staffCode);
            List list = query.list();
            if (list != null && !list.isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}