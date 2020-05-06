package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.DistributeIsdnForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.util.Date;
import com.viettel.im.common.util.DateTimeUtils;
import java.util.HashMap;
import org.hibernate.Session;
import com.viettel.im.database.BO.IsdnTrans;
import com.viettel.im.database.BO.IsdnTransDetail;
import com.viettel.im.database.BO.StockModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author tuannv
 * Lop xu ly cac tac vu lien quan den phan phoi so
 *
 */
public class DistributeISDNDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(DistributeISDNDAO.class);
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_ERROR_FILE_PATH = "errorFilePath";
    private final String SESSION_LIST_ISDN_RANGE = "listIsdnRange";
    //dinh nghia cac hang so forward
    public String pageForward;
    private final String DISTRIBUTE_ISDN = "distributeISDN";
    private int NUMBER_CMD_IN_BATCH = 10000; //so luong ban ghi se commit 1 lan
    //bien form
    private DistributeIsdnForm distributeIsdnForm = new DistributeIsdnForm();

    public DistributeIsdnForm getDistributeIsdnForm() {
        return distributeIsdnForm;
    }

    public void setDistributeIsdnForm(DistributeIsdnForm distributeIsdnForm) {
        this.distributeIsdnForm = distributeIsdnForm;
    }
    //
    private Long IMP_BY_ISDN_RANGE = 1L;
    private Long IMP_BY_FILE = 2L;

    /**
     *
     * author tamdt1, 25/07/2009
     * trang khoi tao phan phoi so
     *
     */
    public String preparePage() throws Exception {
        log.debug("Begin method preparePage of DistributeISDNDAO...");

        HttpServletRequest req = getRequest();

        //resetForm
        this.distributeIsdnForm.resetForm();

        //xoa bien session
        removeTabSession(SESSION_LIST_ISDN_RANGE);

        //set kho lay so mac dinh la user dang nhap
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            this.distributeIsdnForm.setFromShopId(userToken.getShopId());
            this.distributeIsdnForm.setFromShopCode(userToken.getShopCode());
            this.distributeIsdnForm.setFromShopName(userToken.getShopName());
        }

        //
        List<String> listMessage = new ArrayList<String>();
        String userSessionId = req.getSession().getId();
        DistributeISDNDAO.listProgressMessage.put(userSessionId, listMessage);

        pageForward = DISTRIBUTE_ISDN;
        log.debug("End method preparePage of DistributeISDNDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 23/11/2009
     * purpose  : tim kiem thong tin dai so de phan phoi so
     *
     */
    public String searchIsdnRange() throws Exception {
        log.info("begin method searchIsdnRange of DistributeISDNDAO...");
        HttpServletRequest req = getRequest();

        boolean hasNiceIsdnRole = false;
        boolean hasNormalIsdnRole = false;
        String strNiceIsdnRole = ResourceBundleUtils.getResource("niceIsdnRole");
        strNiceIsdnRole = strNiceIsdnRole != null ? strNiceIsdnRole : "";
        String strNormalIsdnRole = ResourceBundleUtils.getResource("normalIsdnRole");
        strNormalIsdnRole = strNormalIsdnRole != null ? strNormalIsdnRole : "";
        String strNormalIsdnStockModelId = com.viettel.security.util.StringEscapeUtils.getSafeFieldName(ResourceBundleUtils.getResource("normalIsdnStockModelId"));
        strNormalIsdnStockModelId = strNormalIsdnStockModelId != null ? strNormalIsdnStockModelId : "-1";

        if (AuthenticateDAO.checkAuthority(strNiceIsdnRole, req)) {
            //neu co quyen phan phoi so dep
            hasNiceIsdnRole = true;
        }
        if (AuthenticateDAO.checkAuthority(strNormalIsdnRole, req)) {
            //neu co quyen phan phoi so dep
            hasNormalIsdnRole = true;
        }

        try {
            Long impType = this.distributeIsdnForm.getImpType();

            if (impType.equals(IMP_BY_ISDN_RANGE)) {
                String resultCheckValidIsdnRange = checkValidIsdnRange();
                if (resultCheckValidIsdnRange == null || !resultCheckValidIsdnRange.equals("")) {
                    req.setAttribute(REQUEST_MESSAGE, resultCheckValidIsdnRange);

                    pageForward = DISTRIBUTE_ISDN;
                    log.info("end method searchIsdnRange of DistributeISDNDAO");
                    return pageForward;
                }

                Long stockTypeId = this.distributeIsdnForm.getServiceType();
                Long fromShopId = this.distributeIsdnForm.getFromShopId();
                Long status = this.distributeIsdnForm.getStatus();
                String isdnType = this.distributeIsdnForm.getIsdnType();
                Long stockModelId = this.distributeIsdnForm.getStockModelId();
                String strFromIsdn = this.distributeIsdnForm.getStartIsdn();
                String strToIsdn = this.distributeIsdnForm.getEndIsdn();

                String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
                List listParameter = new ArrayList();

                //
                //cau lenh select ra cac khoang isdn theo min, max, isdn_type, status, owner_id
                StringBuffer strIsdnRangeQuery = new StringBuffer("");
                strIsdnRangeQuery.append("SELECT MIN(isdn) lb, MAX (isdn) ub, isdn_type, status, owner_id, stock_model_id ");
                strIsdnRangeQuery.append("FROM (SELECT isdn isdn, ");
                strIsdnRangeQuery.append("          isdn - ROW_NUMBER () OVER (ORDER BY isdn_type,status, isdn) rn, ");
                strIsdnRangeQuery.append("          isdn_type, status, owner_id, stock_model_id ");
                strIsdnRangeQuery.append("      FROM " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName) + " ");
                strIsdnRangeQuery.append("      WHERE 1 = 1 ");
                strIsdnRangeQuery.append("          and to_number(isdn) >= ? ");
                listParameter.add(Long.valueOf(strFromIsdn.trim()));
                strIsdnRangeQuery.append("          and to_number(isdn) <= ? ");
                listParameter.add(Long.valueOf(strToIsdn.trim()));

                if (fromShopId != null && fromShopId.compareTo(0L) > 0) {
                    strIsdnRangeQuery.append("       and owner_type = ? and owner_id = ? ");
                    listParameter.add(Constant.OWNER_TYPE_SHOP);
                    listParameter.add(fromShopId);

                }

                //kiem tra quyen phan phoi
                if (hasNiceIsdnRole && hasNormalIsdnRole) {
                    //neu co ca 2 quyen -> khong gioi han gi ca, chi can stock_model_id is not null
                    strIsdnRangeQuery.append("      and stock_model_id is not null ");
                } else if (hasNiceIsdnRole && !hasNormalIsdnRole) {
                    //neu chi co quyen so dep, khong co quyen so thuong -> chi duoc phan phoi so dep, khogn duoc phan phoi so thuong
                    strIsdnRangeQuery.append("      and stock_model_id not in (" + strNormalIsdnStockModelId + ") ");
                } else if (!hasNiceIsdnRole && hasNormalIsdnRole) {
                    //neu chi co quyen so thuong, ko co quyen so dep -> chi duoc phan phoi so thuong, khong duoc phan phoi so dep
                    strIsdnRangeQuery.append("      and stock_model_id in (" + strNormalIsdnStockModelId + ") ");
                } else {
                    //neu khong co quyen so dep + so thuong -> khong duoc phan phoi
                    strIsdnRangeQuery.append("      and stock_model_id = ? ");
                    listParameter.add(-1L);
                }

                if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
                    strIsdnRangeQuery.append("      and stock_model_id = ? ");
                    listParameter.add(stockModelId);
                }

                if (isdnType != null && !isdnType.trim().equals("")) {
                    strIsdnRangeQuery.append("      and isdn_type = ? ");
                    listParameter.add(isdnType.trim());
                }

                if (status != null && status.compareTo(0L) > 0) {
                    strIsdnRangeQuery.append("      and status = ? ");
                    listParameter.add(status);
                } else {
                    //chi cho phep phan phoi so moi va so ngung su dung
                    strIsdnRangeQuery.append("      and (status = ? or status = ? ) ");
                    listParameter.add(Constant.STATUS_ISDN_NEW);
                    listParameter.add(Constant.STATUS_ISDN_SUSPEND);
                }

                strIsdnRangeQuery.append("      ) ");
                strIsdnRangeQuery.append("GROUP BY rn, isdn_type, status, owner_id, stock_model_id ");
                strIsdnRangeQuery.append("ORDER BY 1 ");

                //join bang shop voi cau lenh tren de tim ra danh sach cac khoang isdn + thong tin ve kho chua isdn
                //ham khoi tao: public DistributeIsdnForm(Long formId, Long serviceType, String isdnType, Long status, String startIsdn, String endIsdn, Long countIsdn, Long fromShopId, String fromShopCode, String fromShopName) {
                StringBuffer strSearchQuery = new StringBuffer("");
                strSearchQuery.append("SELECT rownum formId, " + stockTypeId + " serviceType, a.lb startIsdn, a.ub endIsdn, (a.ub - a.lb + 1) countIsdn, a.isdn_type isdnType, a.status status, a.owner_id fromShopId, b.shop_code fromShopCode, b.name fromShopName, a.stock_model_id stockModelId, c.stock_model_code stockModelCode, c.name stockModelName ");
                strSearchQuery.append("FROM (").append(strIsdnRangeQuery).append(") a, shop b, stock_model c ");
                strSearchQuery.append("WHERE a.owner_id = b.shop_id and a.stock_model_id = c.stock_model_id ");

                Query searchQuery = getSession().createSQLQuery(strSearchQuery.toString()).addScalar("formId", Hibernate.LONG).addScalar("serviceType", Hibernate.LONG).addScalar("startIsdn", Hibernate.STRING).addScalar("endIsdn", Hibernate.STRING).addScalar("countIsdn", Hibernate.LONG).addScalar("isdnType", Hibernate.STRING).addScalar("status", Hibernate.LONG).addScalar("fromShopId", Hibernate.LONG).addScalar("fromShopCode", Hibernate.STRING).addScalar("fromShopName", Hibernate.STRING).addScalar("stockModelId", Hibernate.LONG).addScalar("stockModelCode", Hibernate.STRING).addScalar("stockModelName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DistributeIsdnForm.class));
                for (int i = 0; i < listParameter.size(); i++) {
                    searchQuery.setParameter(i, listParameter.get(i));
                }

                List<DistributeIsdnForm> listDistributeIsdnForm = searchQuery.list();
                setTabSession(SESSION_LIST_ISDN_RANGE, listDistributeIsdnForm);

                if (listDistributeIsdnForm != null && listDistributeIsdnForm.size() != 0) {
                    //req.setAttribute(REQUEST_MESSAGE, "Tìm kiếm được" + listDistributeIsdnForm.size() + " dải số thỏa mãn điều kiện");

                    req.setAttribute(REQUEST_MESSAGE, "M.100003");
                    List listParamValue = new ArrayList();
                    listParamValue.add(listDistributeIsdnForm.size());
                    req.setAttribute(REQUEST_MESSAGE_PARAM, listParamValue);

                } else {
//                    req.setAttribute(REQUEST_MESSAGE, "Không tìm kiếm được dải số nào thỏa mãn điều kiện");
                    req.setAttribute(REQUEST_MESSAGE, "M.100005");
                }

                pageForward = DISTRIBUTE_ISDN;
                log.info("end method searchIsdnRange of DistributeISDNDAO");
                return pageForward;

//            } else if (impType.equals(IMP_BY_FILE)) {
//                StringBuffer strMessage = new StringBuffer("");
//                List<DistributeIsdnForm> listDistributeIsdnForm = searchIsdnRangeByFile(hasNiceIsdnRole, hasNormalIsdnRole, strNormalIsdnStockModelId, strMessage);
//                setTabSession(SESSION_LIST_ISDN_RANGE, listDistributeIsdnForm);
//
//                String message = "";
//                if (listDistributeIsdnForm != null && listDistributeIsdnForm.size() != 0) {
//                    message = "Tìm kiếm được " + listDistributeIsdnForm.size() + " dải số thỏa mãn điều kiện";
//                } else {
//                    message = "Không tìm kiếm được dải số nào thỏa mãn điều kiện";
//                }
//
//                if(strMessage != null && !strMessage.toString().equals("")) {
//                    req.setAttribute(REQUEST_ERROR_FILE_PATH, strMessage);
//                }
//
//                req.setAttribute(REQUEST_MESSAGE, message);
//
//                //reset lai progress
//                String userSessionId = req.getSession().getId();
//                DistributeISDNDAO.listProgressMessage.remove(userSessionId);
//                List<String> listMessage = new ArrayList<String>();
//                DistributeISDNDAO.listProgressMessage.put(userSessionId, listMessage);
//
//                pageForward = DISTRIBUTE_ISDN;
//                log.info("end method searchIsdnRange of DistributeISDNDAO");
//                return pageForward;

            } else {
                req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Kiểu nhập dữ liệu không đúng");

                //reset lai progress
                String userSessionId = req.getSession().getId();
                DistributeISDNDAO.listProgressMessage.remove(userSessionId);
                List<String> listMessage = new ArrayList<String>();
                DistributeISDNDAO.listProgressMessage.put(userSessionId, listMessage);

                pageForward = DISTRIBUTE_ISDN;
                log.info("end method searchIsdnRange of DistributeISDNDAO");
                return pageForward;
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("end method searchIsdnRange of DistributeISDNDAO");
            throw ex;
        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 16/06/2010
     * purpose  : doc du lieu tu file, hien thi len list
     *
     */
    private String distributeIsdnByFile() throws Exception {
        log.info("begin method readFileData of DistributeISDNDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        boolean hasNiceIsdnRole = false;
        boolean hasNormalIsdnRole = false;
        boolean rejectLogErrorDistributeIsdn = false;
        String strNiceIsdnRole = ResourceBundleUtils.getResource("niceIsdnRole");
        strNiceIsdnRole = strNiceIsdnRole != null ? strNiceIsdnRole : "";
        String strNormalIsdnRole = ResourceBundleUtils.getResource("normalIsdnRole");
        strNormalIsdnRole = strNormalIsdnRole != null ? strNormalIsdnRole : "";
        String strRejectLogErrorDistributeIsdn = ResourceBundleUtils.getResource("rejectLogErrorDistributeIsdn");
        strRejectLogErrorDistributeIsdn = strRejectLogErrorDistributeIsdn != null ? strRejectLogErrorDistributeIsdn : "";
        String strNormalIsdnStockModelId = ResourceBundleUtils.getResource("normalIsdnStockModelId");
        strNormalIsdnStockModelId = strNormalIsdnStockModelId != null ? strNormalIsdnStockModelId : "-1";

        if (AuthenticateDAO.checkAuthority(strNiceIsdnRole, req)) {
            //neu co quyen phan phoi so dep
            hasNiceIsdnRole = true;
        }
        if (AuthenticateDAO.checkAuthority(strNormalIsdnRole, req)) {
            //neu co quyen phan phoi so dep
            hasNormalIsdnRole = true;
        }
        if (AuthenticateDAO.checkAuthority(strRejectLogErrorDistributeIsdn, req)) {
            //bo qua viec ghi log khi phan phoi so
            rejectLogErrorDistributeIsdn = true;
        }

        try {
            Connection conn = null;
            PreparedStatement stmUpdate = null;

            conn = session.connection();


            Long stockTypeId = this.distributeIsdnForm.getServiceType();
            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            String userSessionId = req.getSession().getId();

            //tao cau lenh update
            StringBuffer strUpdateQuery = new StringBuffer();
            strUpdateQuery.append("update " + strTableName + " a ");
            strUpdateQuery.append("set  a.last_update_user = ?, "
                    + "                 a.last_update_ip_address = ?, "
                    + "                 a.last_update_time = sysdate, "
                    + "                 a.last_update_key = ?, "
                    + "                 a.owner_id = ?  ");
            //3 vi tri dau : 1,2,3
            //vi tri tiep theo : 4
            strUpdateQuery.append("where 1 = 1 ");
            strUpdateQuery.append("and to_number(a.isdn) = ? ");
            //vi tri tiep theo : 5

            //kiem tra quyen phan phoi
            if (hasNiceIsdnRole && hasNormalIsdnRole) {
                //neu co ca 2 quyen -> khong gioi han gi ca, chi can stock_model_id is not null
                strUpdateQuery.append("      and a.stock_model_id is not null ");
            } else if (hasNiceIsdnRole && !hasNormalIsdnRole) {
                //neu chi co quyen so dep, khong co quyen so thuong -> chi duoc phan phoi so dep, khogn duoc phan phoi so thuong
                strUpdateQuery.append("      and a.stock_model_id not in (" + strNormalIsdnStockModelId + ") ");
            } else if (!hasNiceIsdnRole && hasNormalIsdnRole) {
                //neu chi co quyen so thuong, ko co quyen so dep -> chi duoc phan phoi so thuong, khong duoc phan phoi so dep
                strUpdateQuery.append("      and a.stock_model_id in (" + strNormalIsdnStockModelId + ") ");
            } else {
                //neu khong co quyen so dep + so thuong -> khong duoc phan phoi
                strUpdateQuery.append("      and a.stock_model_id = -1 ");
            }
            //chi cho phep phan phoi so moi va so ngung su dung
            strUpdateQuery.append("and (a.status = " + Constant.STATUS_ISDN_NEW + " or a.status = " + Constant.STATUS_ISDN_SUSPEND + " ) ");

            strUpdateQuery.append("and exists (SELECT 'X' FROM SHOP WHERE shop_id = a.owner_id and (shop_id = ? or shop_path like ?) ) ");

            if (!rejectLogErrorDistributeIsdn) {//chiem 2 vi tri 7,8
                strUpdateQuery.append("and a.owner_type = ? and a.owner_id = ? ");
            }

            strUpdateQuery.append("log errors reject limit unlimited"); //chuyen cac ban insert loi ra bang log

            stmUpdate = conn.prepareStatement(strUpdateQuery.toString());

            System.out.println("     =====> " + strUpdateQuery.toString());
            
            stmUpdate.setString(1, userToken.getLoginName()); //last_update_user
            stmUpdate.setString(2, req.getRemoteAddr()); //last_update_ip_address
            stmUpdate.setString(3, Constant.LAST_UPDATE_KEY); //last_update_key

            //2 vi tri: 4,5: danh cho new_shop va isdn_number
            
            //2 vi tri tiep theo
            stmUpdate.setLong(6, userToken.getShopId()); //thiet lap truong oldOwnerId
            stmUpdate.setString(7, "%_" + userToken.getShopId() + "_%"); //thiet lap truong oldOwnerId
                
            //lay du lieu tu file
            List<DistributeIsdnForm> listError = new ArrayList<DistributeIsdnForm>();
            String serverFileName = CommonDAO.getSafeFileName(this.distributeIsdnForm.getServerFileName());
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);


            Workbook workbook = null;
            Sheet sheet = null;
            int numberRow = 0;
            try {
                File impFile = new File(serverFilePath);
                workbook = Workbook.getWorkbook(impFile);
                sheet = workbook.getSheet(0);
                numberRow = sheet.getRows();
            } catch (Exception ex) {
                ex.printStackTrace();
                pageForward = DISTRIBUTE_ISDN;
                req.setAttribute(REQUEST_MESSAGE, "Error. File must be MS Excel 2003 version!");
                return pageForward;
            }


            numberRow = numberRow - 1; //tru di dong dau tien

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            List<String> listMessage = DistributeISDNDAO.listProgressMessage.get(userSessionId);
            String message = "";

            if (numberRow > 0) {
                int toRow = NUMBER_CMD_IN_BATCH;
                toRow = toRow < numberRow ? toRow : numberRow;
                message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " processing from row 1 to " + toRow + "/ " + numberRow + " row in file";
                listMessage.add(message);
            }

            //
            HashMap<String, Long> hashMapShop = new HashMap<String, Long>();

            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                //doc tat ca cac dong trong sheet, cot dau tien la isdn, cot sau la ma shopCode can phan phoi
                String tmpStrIsdn = sheet.getCell(0, rowIndex).getContents();
                Long tmpIsdn = -1L;
                String tmpNewShopCode = sheet.getCell(1, rowIndex).getContents();
                if (tmpStrIsdn == null || tmpStrIsdn.trim().equals("") || tmpNewShopCode == null || tmpNewShopCode.trim().equals("")) {
                    DistributeIsdnForm errDistributeIsdnForm = new DistributeIsdnForm();
                    errDistributeIsdnForm.setStartIsdn(tmpStrIsdn);
                    errDistributeIsdnForm.setFromShopCode(tmpNewShopCode);
                    errDistributeIsdnForm.setErrorMessage(getText("E.100001"));
                    listError.add(errDistributeIsdnForm);
                    continue;
                } else {
                    tmpStrIsdn = tmpStrIsdn.trim();
                    tmpNewShopCode = tmpNewShopCode.trim();
                    try {
                        tmpIsdn = Long.parseLong(tmpStrIsdn);
                    } catch (NumberFormatException ex) {
                        //isdn khong dung dinh dang
                        DistributeIsdnForm errDistributeIsdnForm = new DistributeIsdnForm();
                        errDistributeIsdnForm.setStartIsdn(tmpStrIsdn);
                        errDistributeIsdnForm.setFromShopCode(tmpNewShopCode);
                        errDistributeIsdnForm.setErrorMessage(getText("E.100002"));
                        listError.add(errDistributeIsdnForm);
                        continue;
                    }
                }

                Long tmpOldShopId = -1L;
                if (!rejectLogErrorDistributeIsdn) {
                    tmpOldShopId = getOwnerId(strTableName, tmpIsdn);
                    if (tmpOldShopId == null) {
                        //so khong ton tai hoac so khong thuoc kho duoc phep phan phoi
                        DistributeIsdnForm errDistributeIsdnForm = new DistributeIsdnForm();
                        errDistributeIsdnForm.setStartIsdn(tmpStrIsdn);
                        errDistributeIsdnForm.setFromShopCode(tmpNewShopCode);
                        errDistributeIsdnForm.setErrorMessage("E.100003");
                        listError.add(errDistributeIsdnForm);
                        continue;
                    }
                }

                Long tmpNewShopId = hashMapShop.get(tmpNewShopCode);
                if (tmpNewShopId == null) {
                    tmpNewShopId = getShopId(tmpNewShopCode);
                    if (tmpNewShopId != null) {
                        //tim thay ma shopCode moi -> day vao map de su dung sau nay
                        hashMapShop.put(tmpNewShopCode, tmpNewShopId);
                    } else {
                        //khong tim thay -> day vao -1 de khong p tim lai
                        hashMapShop.put(tmpNewShopCode, -1L);
                    }
                }

                if (tmpNewShopId == null || tmpNewShopId.equals(-1L)) {
                    //ma kho khong ton tai hoac ma kho khong duoc phep phan phoi
                    DistributeIsdnForm errDistributeIsdnForm = new DistributeIsdnForm();
                    errDistributeIsdnForm.setStartIsdn(tmpStrIsdn);
                    errDistributeIsdnForm.setFromShopCode(tmpNewShopCode);
                    errDistributeIsdnForm.setErrorMessage(getText("E.100004"));
                    listError.add(errDistributeIsdnForm);
                    continue;

                } else {
                    try {
                        //bat dau tu 4, vi 3 chi so dau da duoc thiet lap o tren khi tao cau lenh
                        stmUpdate.setLong(4, tmpNewShopId); //thiet lap truong newOwnerId
                        stmUpdate.setLong(5, tmpIsdn); //thiet lap truong isdn


                        if (!rejectLogErrorDistributeIsdn) {
                            stmUpdate.setLong(8, Constant.OWNER_TYPE_SHOP); //
                            stmUpdate.setLong(9, tmpOldShopId); //
                        }

                        stmUpdate.addBatch();

                    } catch (Exception ex) {
                        //
                        DistributeIsdnForm errDistributeIsdnForm = new DistributeIsdnForm();
                        errDistributeIsdnForm.setStartIsdn(tmpStrIsdn);
                        errDistributeIsdnForm.setFromShopCode(tmpNewShopCode);
                        errDistributeIsdnForm.setErrorMessage("!!!Ex. " + ex.getMessage());
                        listError.add(errDistributeIsdnForm);
                        continue;
                    }
                }

                if (rowIndex % NUMBER_CMD_IN_BATCH == 0) {
                    //commit
                    conn.setAutoCommit(false);
                    //chay batch chen du lieu vao cac bang tuong ung
                    int[] updateCount = stmUpdate.executeBatch();
                    conn.commit();
//                    conn.setAutoCommit(true);tronglv comment 120604

                    int fromRow = rowIndex + 1;
                    int toRow = fromRow + NUMBER_CMD_IN_BATCH;
                    toRow = toRow < numberRow ? toRow : numberRow;
                    message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " processing from row " + (fromRow + 1) + " to " + toRow + "/ " + numberRow + " row in file";
                    listMessage.add(message);
                }
            }

            //cap nhat not cac ban ghi con lai
            //commit
            conn.setAutoCommit(false);
            //chay batch chen du lieu vao cac bang tuong ung
            int[] updateCount = stmUpdate.executeBatch();
            conn.commit();
//            conn.setAutoCommit(true);tronglv comment 120604

            //ket xuat ket qua ra file excel trong truong hop co loi
            if (listError != null && listError.size() > 0) {
                req.setAttribute(REQUEST_MESSAGE, "M.100002");
                List listParamValue = new ArrayList();
                listParamValue.add(numberRow - listError.size());
                listParamValue.add(numberRow);
                req.setAttribute(REQUEST_MESSAGE_PARAM, listParamValue);
                //req.setAttribute(REQUEST_MESSAGE, "Phân phối thành công " + (numberRow - listError.size()) + "/ " + numberRow);
                try {
                    String DATE_FORMAT = "yyyyMMddHHmmss";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
                    filePath = filePath != null ? filePath : "/";
                    filePath += com.viettel.security.util.StringEscapeUtils.getSafeFileName("distributeIsdnErr_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls");
                    String realPath = req.getSession().getServletContext().getRealPath(filePath);
                    String templatePath = ResourceBundleUtils.getResource("DI_TMP_PATH_ERR");
                    if (templatePath == null || templatePath.trim().equals("")) {
                        //khong tim thay duong dan den file template de xuat ket qua
                        req.setAttribute(REQUEST_ERROR_FILE_PATH, "distributeIsdn.error.errTemplateNotExist");
                    }
                    String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
                    File fTemplateFile = new File(realTemplatePath);
                    if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                        //file ko ton tai
                        req.setAttribute(REQUEST_ERROR_FILE_PATH, "distributeIsdn.error.errTemplateNotExist");
                    }
                    Map beans = new HashMap();
                    beans.put("listError", listError);
                    XLSTransformer transformer = new XLSTransformer();
                    transformer.transformXLS(realTemplatePath, beans, realPath);
                    req.setAttribute(REQUEST_ERROR_FILE_PATH, filePath);

                } catch (Exception ex) {
                    req.setAttribute(REQUEST_ERROR_FILE_PATH, "!!!Error. " + ex.toString());
                }

            } else {
                req.setAttribute(REQUEST_MESSAGE, "M.100002");
                List listParamValue = new ArrayList();
                listParamValue.add(numberRow);
                listParamValue.add(numberRow);
                req.setAttribute(REQUEST_MESSAGE_PARAM, listParamValue);

                //req.setAttribute(REQUEST_MESSAGE, "Phân phối thành công " + numberRow + "/ " + numberRow);
            }

            //resetForm
            this.distributeIsdnForm.resetForm();

            //set kho lay so mac dinh la user dang nhap
            if (userToken != null) {
                this.distributeIsdnForm.setFromShopCode(userToken.getShopCode());
                this.distributeIsdnForm.setFromShopName(userToken.getShopName());
            }

            //reset lai progress
            DistributeISDNDAO.listProgressMessage.put(userSessionId, new ArrayList<String>());

            pageForward = DISTRIBUTE_ISDN;
            log.info("end method searchIsdnRange of DistributeISDNDAO");
            return pageForward;

        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("end method readFileData of DistributeISDNDAO");
            throw ex;
        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 16/06/2010
     * desc     : kiem tra 1 so co hop le khong
     *
     */
    private Long getOwnerId(String strTableName, Long strIsdn) {
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Session session = getSession();

            boolean hasNiceIsdnRole = false;
            boolean hasNormalIsdnRole = false;
            String strNiceIsdnRole = ResourceBundleUtils.getResource("niceIsdnRole");
            strNiceIsdnRole = strNiceIsdnRole != null ? strNiceIsdnRole : "";
            String strNormalIsdnRole = ResourceBundleUtils.getResource("normalIsdnRole");
            strNormalIsdnRole = strNormalIsdnRole != null ? strNormalIsdnRole : "";
            String strNormalIsdnStockModelId = com.viettel.security.util.StringEscapeUtils.getSafeFieldName(ResourceBundleUtils.getResource("normalIsdnStockModelId"));
            strNormalIsdnStockModelId = strNormalIsdnStockModelId != null ? strNormalIsdnStockModelId : "-1";

            if (AuthenticateDAO.checkAuthority(strNiceIsdnRole, req)) {
                //neu co quyen phan phoi so dep
                hasNiceIsdnRole = true;
            }
            if (AuthenticateDAO.checkAuthority(strNormalIsdnRole, req)) {
                //neu co quyen phan phoi so dep
                hasNormalIsdnRole = true;
            }

            //cau lenh select ra cac khoang isdn theo min, max, isdn_type, status, owner_id
            List listParameter = new ArrayList();
            StringBuffer strQuery = new StringBuffer("");
            strQuery.append("SELECT owner_id ");
            strQuery.append("FROM " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName)+ " a ");
            strQuery.append("WHERE 1 = 1 ");
            strQuery.append("      and to_number(a.isdn) = ? ");
            listParameter.add(strIsdn);

            //kiem tra quyen phan phoi
            if (hasNiceIsdnRole && hasNormalIsdnRole) {
                //neu co ca 2 quyen -> khong gioi han gi ca, chi can stock_model_id is not null
                strQuery.append("      and a.stock_model_id is not null ");
            } else if (hasNiceIsdnRole && !hasNormalIsdnRole) {
                //neu chi co quyen so dep, khong co quyen so thuong -> chi duoc phan phoi so dep, khogn duoc phan phoi so thuong
                strQuery.append("      and a.stock_model_id not in (" + strNormalIsdnStockModelId + ") ");
            } else if (!hasNiceIsdnRole && hasNormalIsdnRole) {
                //neu chi co quyen so thuong, ko co quyen so dep -> chi duoc phan phoi so thuong, khong duoc phan phoi so dep
                strQuery.append("      and a.stock_model_id in (" + strNormalIsdnStockModelId + ") ");
            } else {
                //neu khong co quyen so dep + so thuong -> khong duoc phan phoi
                strQuery.append("      and a.stock_model_id = ? ");
                listParameter.add(-1L);
            }

            //chi cho phep phan phoi so moi va so ngung su dung
            strQuery.append("and (status = ? or status = ? ) ");
            listParameter.add(Constant.STATUS_ISDN_NEW);
            listParameter.add(Constant.STATUS_ISDN_SUSPEND);

            strQuery.append("and exists (SELECT 'X' FROM SHOP WHERE shop_id = a.owner_id and (shop_id = ? or shop_path like ?) ) ");
            listParameter.add(userToken.getShopId());
            listParameter.add("%_" + userToken.getShopId() + "_%");

            Query query = session.createSQLQuery(strQuery.toString());
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List list = query.list();
            if (list != null && list.size() == 1) {
                return Long.valueOf(list.get(0).toString());
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     *
     * author   : tamdt1
     * date     : 16/06/2010
     * desc     : kiem tra 1 so co hop le khong
     *
     */
    private Long getShopId(String shopCode) {
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Session session = getSession();

            List listParameter = new ArrayList();
            StringBuffer strQuery = new StringBuffer("");
            strQuery.append("SELECT shop_id a ");
            strQuery.append("FROM shop a ");
            strQuery.append("where 1 = 1 ");
            strQuery.append("and a.status = 1 ");
//            strQuery.append("and exists (SELECT 'X' FROM V_SHOP_TREE WHERE shop_id = a.shop_id and root_id = ?) ");
            strQuery.append("and exists (SELECT 'X' FROM SHOP WHERE shop_id = a.shop_id and (shop_id = ? or shop_path like ?) ) ");
            listParameter.add(userToken.getShopId());
            listParameter.add("%_" + userToken.getShopId() + "_%");

            strQuery.append("and lower(a.shop_code) = ? ");
            listParameter.add(shopCode.trim().toLowerCase());

            Query query = session.createSQLQuery(strQuery.toString());
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List list = query.list();
            if (list != null && list.size() == 1) {
                return Long.valueOf(list.get(0).toString());
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * author   :tamdt1
     * date     : 19/09/2009
     * purpose  : kiem tra tinh hop le cua dai so truoc khi phan phoi
     *
     */
    private String checkValidIsdnRange() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String fromShopCode = this.distributeIsdnForm.getFromShopCode();
            String stockModelCode = this.distributeIsdnForm.getStockModelCode();
            Long stockTypeId = this.distributeIsdnForm.getServiceType();
            String strFromIsdn = this.distributeIsdnForm.getStartIsdn();
            String strToIsdn = this.distributeIsdnForm.getEndIsdn();

            //kiem tra cac truong bat buoc
            if (stockTypeId == null
                    || fromShopCode == null || fromShopCode.trim().equals("")
                    || strFromIsdn == null || strFromIsdn.trim().equals("")
                    || strToIsdn == null || strToIsdn.trim().equals("")) {

                return "distributeIsdn.error.requiredFieldsEmpty";
            }

            //kiem tra su ton tai cua stock_type_id
            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            if (strTableName == null || strTableName.equals("")) {
                return "distributeIsdn.error.stockTypeTableNotExist";
            }

            //kiem tra shop co ton tai khong
            Long ownerId = 0L;
            if (fromShopCode != null && !fromShopCode.trim().equals("")) {
                String strShopQuery = "from Shop a "
                        + "where lower(a.shopCode) = ? and a.status = ? and (a.shopPath like ? or a.shopId = ?) ";
                Query shopQuery = getSession().createQuery(strShopQuery);
                shopQuery.setParameter(0, fromShopCode.trim().toLowerCase());
                shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
                shopQuery.setParameter(2, "%_" + userToken.getShopId() + "_%");
                shopQuery.setParameter(3, userToken.getShopId());
                List<Shop> listShop = shopQuery.list();
                if (listShop == null || listShop.size() == 0) {
                    return "distributeIsdn.error.fromShopCodeNotExist";
                }
                ownerId = listShop.get(0).getShopId();
            } else {
                return "distributeIsdn.error.fromShopCodeNotExist";
            }

            //kiem tra stock model co ton tai khong
            Long stockModelId = 0L;
            if (stockModelCode != null && !stockModelCode.trim().equals("")) {
                String strStockModelQuery = "from StockModel a "
                        + "where lower(a.stockModelCode) = ? and a.status = ? ";
                Query stockModelQuery = getSession().createQuery(strStockModelQuery);
                stockModelQuery.setParameter(0, stockModelCode.trim().toLowerCase());
                stockModelQuery.setParameter(1, Constant.STATUS_ACTIVE);
                List<StockModel> listStockModel = stockModelQuery.list();
                if (listStockModel == null || listStockModel.size() == 0) {
                    return "distributeIsdn.error.stockModelNotExist";
                }
                stockModelId = listStockModel.get(0).getStockModelId();
            }

            //kiem tra truong fromIsdn, toIsdn phai la so khong am
            Long fromIsdn = 0L;
            Long toIsdn = 0L;
            try {
                fromIsdn = Long.valueOf(strFromIsdn.trim());
                toIsdn = Long.valueOf(strToIsdn.trim());
            } catch (NumberFormatException ex) {
                return "distributeIsdn.error.isdnNegative";
            }

            //kiem tra truong fromNumber khong duoc lon hon truong toNumber
            if (fromIsdn.compareTo(toIsdn) > 0) {
                return "distributeIsdn.error.invalidIsdn";
            }

            //kiem tra so luong so 1 lan tao dai so ko duoc vuot qua so luong max (hien tai la 2trieu so/lan)
            int maxIsdnAssignType = -1;
            try {
                String strMaxIsdnAssignType = ResourceBundleUtils.getResource("MAX_ISDN_ASSIGN_TYPE");
                maxIsdnAssignType = Integer.parseInt(strMaxIsdnAssignType.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                maxIsdnAssignType = -1;
            }

            if ((toIsdn - fromIsdn + 1) > maxIsdnAssignType) {
                return "distributeIsdn.error.quantityOverMaximum";
            }

            //
            this.distributeIsdnForm.setFromShopId(ownerId);
            this.distributeIsdnForm.setStockModelId(stockModelId);

            //trim cac truong can thiet
            this.distributeIsdnForm.setFromShopCode(fromShopCode.trim());
            this.distributeIsdnForm.setStockModelCode(stockModelCode.trim());
            this.distributeIsdnForm.setStartIsdn(strFromIsdn.trim());
            this.distributeIsdnForm.setEndIsdn(strToIsdn.trim());

            return "";

        } catch (Exception e) {
            e.printStackTrace();
            return "!!!Error function checkValidIsdnRange(): " + e.toString();

        }
    }

    /**
     *
     * author tamdt1, 25/07/2009
     * phan phoi so vao cac kho
     *
     */
    public String distributeNumber() throws Exception {
        log.info("Begin method distributeNumber of distributeISDNDAO...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Long impType = this.distributeIsdnForm.getImpType();
        if (impType.equals(IMP_BY_FILE)) {
            //neu la im port boi file
            pageForward = distributeIsdnByFile();
            log.debug("End method addIsdnRange of DistributeIsdnDAO");

            return pageForward;
        }


        //kiem tra list co rong khong
        List<DistributeIsdnForm> listDistributeIsdnForm = (List<DistributeIsdnForm>) getTabSession(SESSION_LIST_ISDN_RANGE);
        if (listDistributeIsdnForm == null || listDistributeIsdnForm.size() == 0) {
            //
            req.setAttribute(REQUEST_MESSAGE, "distributeIsdn.error.listIsEmpty");

            pageForward = DISTRIBUTE_ISDN;
            log.info("End method distributeNumber of distributeISDNDAO");
            return pageForward;
        }

        Session session = getSession();
        try {
            boolean hasNiceIsdnRole = false;
            boolean hasNormalIsdnRole = false;
            String strNiceIsdnRole = ResourceBundleUtils.getResource("niceIsdnRole");
            strNiceIsdnRole = strNiceIsdnRole != null ? strNiceIsdnRole : "";
            String strNormalIsdnRole = ResourceBundleUtils.getResource("normalIsdnRole");
            strNormalIsdnRole = strNormalIsdnRole != null ? strNormalIsdnRole : "";
            String strNormalIsdnStockModelId = com.viettel.security.util.StringEscapeUtils.getSafeFieldName(ResourceBundleUtils.getResource("normalIsdnStockModelId"));
            strNormalIsdnStockModelId = strNormalIsdnStockModelId != null ? strNormalIsdnStockModelId : "-1";

            if (AuthenticateDAO.checkAuthority(strNiceIsdnRole, req)) {
                //neu co quyen phan phoi so dep
                hasNiceIsdnRole = true;
            }
            if (AuthenticateDAO.checkAuthority(strNormalIsdnRole, req)) {
                //neu co quyen phan phoi so dep
                hasNormalIsdnRole = true;
            }

            String shopCode = this.distributeIsdnForm.getShopCode();
            String newIsdnType = this.distributeIsdnForm.getNewIsdnType();

            //kiem tra cac truong bat buoc
            if (shopCode == null || shopCode.trim().equals("")) {

                req.setAttribute(REQUEST_MESSAGE, "distributeIsdn.error.requiredFieldsEmpty");

                pageForward = DISTRIBUTE_ISDN;
                log.info("End method distributeNumber of distributeISDNDAO");
                return pageForward;
            }

            //kiem tra shop co ton tai khong
            Long newOwnerId = 0L;
            if (shopCode != null && !shopCode.trim().equals("")) {
                String strShopQuery = "from Shop a "
                        + "where lower(a.shopCode) = ? and a.status = ? and (a.shopPath like ? or a.shopId = ?) ";
                Query shopQuery = getSession().createQuery(strShopQuery);
                shopQuery.setParameter(0, shopCode.trim().toLowerCase());
                shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
                shopQuery.setParameter(2, "%_" + userToken.getShopId() + "_%");
                shopQuery.setParameter(3, userToken.getShopId());
                List<Shop> listShop = shopQuery.list();
                if (listShop == null || listShop.size() == 0) {
                    req.setAttribute(REQUEST_MESSAGE, "distributeIsdn.error.toShopCodeNotExist");

                    pageForward = DISTRIBUTE_ISDN;
                    log.info("End method distributeNumber of distributeISDNDAO");
                    return pageForward;
                }
                newOwnerId = listShop.get(0).getShopId();

            } else {
                req.setAttribute(REQUEST_MESSAGE, "distributeIsdn.error.toShopCodeNotExist");

                pageForward = DISTRIBUTE_ISDN;
                log.info("End method distributeNumber of distributeISDNDAO");
                return pageForward;
            }

            String[] selectedFormId = this.distributeIsdnForm.getSelectedFormId();
            if (selectedFormId == null || selectedFormId.length <= 0 || selectedFormId[0].equals("false")) {
                req.setAttribute(REQUEST_MESSAGE, "distributeIsdn.error.noIsdnRangeHasSelected");

                pageForward = DISTRIBUTE_ISDN;
                log.info("End method distributeNumber of distributeISDNDAO");
                return pageForward;
            }

            for (int i = 0; i < selectedFormId.length; i++) {
                try {
                    DistributeIsdnForm tmpDistributeIsdnForm = listDistributeIsdnForm.get(Integer.parseInt(selectedFormId[i]) - 1); //-1 do chi so mang bat dau tu 0, rownum bat dau tu 1
                    Long stockTypeId = tmpDistributeIsdnForm.getServiceType();
                    String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
                    if (strTableName == null || strTableName.equals("")) {
                        req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.stockTypeTableNotExist");

                        pageForward = DISTRIBUTE_ISDN;
                        log.info("End method distributeNumber of distributeISDNDAO");
                        return pageForward;
                    }

                    List listParameter = new ArrayList();
                    StringBuffer strQuery = new StringBuffer(" ");
                    strQuery.append("update ").append(strTableName).append(" a ");
                    strQuery.append("set    a.owner_id = ?, ");
                    strQuery.append("       a.LAST_UPDATE_USER = ?, ");
                    strQuery.append("       a.LAST_UPDATE_IP_ADDRESS = ?, ");
                    strQuery.append("       a.LAST_UPDATE_TIME = ?, ");
                    strQuery.append("       a.LAST_UPDATE_KEY = ? ");
                    listParameter.add(newOwnerId);
                    listParameter.add(userToken.getLoginName()); //last_update_user
                    listParameter.add(req.getRemoteAddr()); //LAST_UPDATE_IP_ADDRESS
                    listParameter.add(new java.sql.Date(DateTimeUtils.getSysDate().getTime())); //LAST_UPDATE_TIME
                    listParameter.add(Constant.LAST_UPDATE_KEY); //LAST_UPDATE_KEY

                    if (newIsdnType != null && !newIsdnType.equals("")) {
                        strQuery.append(", isdn_type = ?  ");
                        listParameter.add(newIsdnType);
                    }

                    Long fromShopId = tmpDistributeIsdnForm.getFromShopId();
                    Long status = tmpDistributeIsdnForm.getStatus();
                    String isdnType = tmpDistributeIsdnForm.getIsdnType();
                    Long stockModelId = tmpDistributeIsdnForm.getStockModelId();
                    String strFromIsdn = tmpDistributeIsdnForm.getStartIsdn();
                    String strToIsdn = tmpDistributeIsdnForm.getEndIsdn();
                    Long fromIsdn = Long.valueOf(strFromIsdn.trim());
                    Long toIsdn = Long.valueOf(strToIsdn.trim());

                    strQuery.append("where 1 = 1 ");

                    strQuery.append("and to_number(isdn) >= ? ");
                    listParameter.add(fromIsdn);

                    strQuery.append("and to_number(isdn) <= ? ");
                    listParameter.add(toIsdn);

                    if (fromShopId != null && fromShopId.compareTo(0L) > 0) {
                        strQuery.append("and owner_type = ? and owner_id = ? ");
                        listParameter.add(Constant.OWNER_TYPE_SHOP);
                        listParameter.add(fromShopId);

                    }

                    //kiem tra quyen phan phoi
                    if (hasNiceIsdnRole && hasNormalIsdnRole) {
                        //neu co ca 2 quyen -> khong gioi han gi ca, chi can stock_model_id is not null
                        strQuery.append("      and stock_model_id is not null ");
                    } else if (hasNiceIsdnRole && !hasNormalIsdnRole) {
                        //neu chi co quyen so dep, khong co quyen so thuong -> chi duoc phan phoi so dep, khogn duoc phan phoi so thuong
                        strQuery.append("      and stock_model_id not in (" + strNormalIsdnStockModelId + ") ");
                    } else if (!hasNiceIsdnRole && hasNormalIsdnRole) {
                        //neu chi co quyen so thuong, ko co quyen so dep -> chi duoc phan phoi so thuong, khong duoc phan phoi so dep
                        strQuery.append("      and stock_model_id in (" + strNormalIsdnStockModelId + ") ");
                    } else {
                        //neu khong co quyen so dep + so thuong -> khong duoc phan phoi
                        strQuery.append("      and stock_model_id = ? ");
                        listParameter.add(-1L);
                    }

                    if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
                        strQuery.append("and stock_model_id = ? ");
                        listParameter.add(stockModelId);
                    }

                    if (isdnType != null && !isdnType.trim().equals("")) {
                        strQuery.append("and isdn_type = ? ");
                        listParameter.add(Long.valueOf(isdnType.trim()));
                    }

                    if (status != null && status.compareTo(0L) > 0) {
                        strQuery.append("and status = ? ");
                        listParameter.add(status);
                    } else {
                        //chi cho phep phan phoi so moi va so ngung su dung
                        strQuery.append("and (status = ? or status = ? ) ");
                        listParameter.add(Constant.STATUS_ISDN_NEW);
                        listParameter.add(Constant.STATUS_ISDN_SUSPEND);
                    }

                    Query query = session.createSQLQuery(strQuery.toString());
                    for (int indexParameter = 0; indexParameter < listParameter.size(); indexParameter++) {
                        query.setParameter(indexParameter, listParameter.get(indexParameter));
                    }

                    int numberRowUpdated = query.executeUpdate();
                    if (numberRowUpdated == (toIsdn - fromIsdn + 1)) {
                        //neu update thanh cong
                        //----------------------------------------------------------------
                        //              Andv
                        //luu thong tin dai so can tao isdnTrans va isdnTransDetail
                        Date now = DateTimeUtils.getSysDate();

                        Long isdnTransId = getSequence("ISDN_TRANS_SEQ");
                        IsdnTrans isdnTrans = new IsdnTrans();
                        isdnTrans.setIsdnTransId(isdnTransId);
                        isdnTrans.setStockTypeId(stockTypeId);
                        isdnTrans.setTransType(Constant.ISDN_TRANS_TYPE_DISTRIBUTE);
                        isdnTrans.setLastUpdateUser(userToken.getLoginName() + "-" + req.getRemoteHost());
                        isdnTrans.setLastUpdateIpAddress(req.getRemoteAddr());
                        isdnTrans.setLastUpdateTime(now);
                        session.save(isdnTrans);

                        Long isdnTransDetailId = getSequence("ISDN_TRANS_DETAIL_SEQ");
                        IsdnTransDetail isdnTransDetail = new IsdnTransDetail();
                        isdnTransDetail.setIsdnTransDetailId(isdnTransDetailId);
                        isdnTransDetail.setIsdnTransId(isdnTransId);
                        isdnTransDetail.setFromIsdn(strFromIsdn);
                        isdnTransDetail.setToIsdn(strToIsdn);
                        isdnTransDetail.setQuantity(toIsdn - fromIsdn + 1);
                        isdnTransDetail.setOldValue(fromShopId.toString());
                        isdnTransDetail.setNewValue(newOwnerId.toString());
                        isdnTransDetail.setLastUpdateTime(now);
                        session.save(isdnTransDetail);

                        if (newIsdnType != null && !newIsdnType.equals("")) {
                            Long isdnTransDetailId_1 = getSequence("ISDN_TRANS_DETAIL_SEQ");
                            IsdnTransDetail isdnTransDetail_1 = new IsdnTransDetail();
                            isdnTransDetail_1.setIsdnTransDetailId(isdnTransDetailId_1);
                            isdnTransDetail_1.setIsdnTransId(isdnTransId);
                            isdnTransDetail_1.setFromIsdn(strFromIsdn);
                            isdnTransDetail_1.setToIsdn(strToIsdn);
                            isdnTransDetail_1.setQuantity(toIsdn - fromIsdn + 1);
                            isdnTransDetail_1.setOldValue("");
                            isdnTransDetail_1.setNewValue(newIsdnType);
                            isdnTransDetail.setLastUpdateTime(now);
                            session.save(isdnTransDetail_1);
                        }

                        session.flush();
                        session.beginTransaction();
                        session.getTransaction().commit();
                        session.getTransaction().begin();
                        //              Andv
                        //----------------------------------------------------------------

                    } else {
                        //truong hop bi loi
                        session.clear();
                        session.beginTransaction();
                        session.getTransaction().rollback();
                        session.getTransaction().begin();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                    session.clear();
                    session.beginTransaction();
                    session.getTransaction().rollback();
                    session.getTransaction().begin();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            session.clear();
            session.beginTransaction();
            session.getTransaction().rollback();
            session.getTransaction().begin();

            req.setAttribute(REQUEST_MESSAGE, "distributeIsdn.error.updateNotSuccess");
        }

        //resetForm
        this.distributeIsdnForm.resetForm();

        //set kho lay so mac dinh la user dang nhap
        if (userToken != null) {
            this.distributeIsdnForm.setFromShopCode(userToken.getShopCode());
            this.distributeIsdnForm.setFromShopName(userToken.getShopName());
        }

        //xoa bien session
        removeTabSession(SESSION_LIST_ISDN_RANGE);

        //reset lai progress
        String userSessionId = req.getSession().getId();
        DistributeISDNDAO.listProgressMessage.remove(userSessionId);
        List<String> listMessage = new ArrayList<String>();
        DistributeISDNDAO.listProgressMessage.put(userSessionId, listMessage);

        //
        req.setAttribute(REQUEST_MESSAGE, "distributeIsdn.success");
        pageForward = DISTRIBUTE_ISDN;
        log.debug("End method addIsdnRange of DistributeIsdnDAO");

        return pageForward;

    }
    //bien phuc vu viec hien thi progress
    private static HashMap<String, List<String>> listProgressMessage = new HashMap<String, List<String>>(); //

    /**
     *
     * author   : tamdt1
     * date     : 14/11/2009
     * desc     : tra ve du lieu cap nhat cho divProgress
     *
     */
    public String updateProgressDiv(HttpServletRequest req) {
        log.info("Begin updateProgressDiv of StockIsdnDAO");

        try {
            String userSessionId = req.getSession().getId();
            List<String> listMessage = DistributeISDNDAO.listProgressMessage.get(userSessionId);
            if (listMessage != null && listMessage.size() > 0) {
                String message = listMessage.get(0);
                listMessage.remove(0);
                return message;
            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
