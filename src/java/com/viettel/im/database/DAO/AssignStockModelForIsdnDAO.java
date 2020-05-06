package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.AssignStockModelForIsdnForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import java.util.HashMap;
import java.util.Map;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.FilterType;
import com.viettel.im.database.BO.GroupFilterRule;
import com.viettel.im.database.BO.IsdnFilterRules;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import org.hibernate.Session;
import com.viettel.im.database.BO.IsdnTrans;
import com.viettel.im.database.BO.IsdnTransDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author tamdt1
 * xu ly cac tac vu lien quan den viec gan mat hang cho so isdn
 *
 */
public class AssignStockModelForIsdnDAO extends BaseDAOAction {
    private static final Log log = LogFactory.getLog(AssignStockModelForIsdnDAO.class);
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_ERROR_FILE_PATH = "errorFilePath";
    private final String REQUEST_MESSAGE_LIST = "messageList";
    private final String REQUEST_MESSAGE_LIST_PARAM = "messageListParam";
    private final String REQUEST_DETAIL_ISDN_RANGE_PATH = "detailIsdnRangePath";
    private final String REQUEST_DETAIL_ISDN_RANGE_MESSAGE = "detailIsdnRangeMessage";
    private final String REQUEST_LIST_STOCK_TYPE = "listStockType";
    private final String REQUEST_LIST_STOCK_MODEL = "listStockModel";
    private final String REQUEST_LIST_GROUP_FILTER = "listGroupFilter";
    private final String REQUEST_LIST_FILTER_TYPE = "listFilterType";
    private final String REQUEST_LIST_RULE = "listRule";
    private final String SESSION_LIST_ISDN_RANGE = "listIsdnRange";

    //cac hang so forward
    private String pageForward;
    private final String ASSIGN_STOCK_MODEL_FOR_ISDN = "assignStockModelForIsdn";
    private final String UPDATE_LIST_HASH_MAP_RESULT = "updateListHashMapResult";

    //
    private Long IMP_BY_ISDN_RANGE = 1L;
    private Long IMP_BY_FILE = 2L;
    private int NUMBER_CMD_IN_BATCH = 10000; //so luong ban ghi se commit 1 lan

    //bien form
    private AssignStockModelForIsdnForm assignStockModelForIsdnForm = new AssignStockModelForIsdnForm();

    public AssignStockModelForIsdnForm getAssignStockModelForIsdnForm() {
        return assignStockModelForIsdnForm;
    }

    public void setAssignStockModelForIsdnForm(AssignStockModelForIsdnForm assignStockModelForIsdnForm) {
        this.assignStockModelForIsdnForm = assignStockModelForIsdnForm;
    }

    /**
     *
     * author tamdt1
     * date: 25/05/2009
     * chuan bi form de gan mat hang cho so isdn
     *
     */
    public String preparePage() throws Exception {
        log.debug("begin method preparePage of AssignStockModelForIsdnDAO");

        HttpServletRequest req = getRequest();
        Session session = getSession();

        try {
            //resetForm
            this.assignStockModelForIsdnForm.resetForm();

            //
            removeTabSession(SESSION_LIST_ISDN_RANGE);

//            //set kho lay so mac dinh la user dang nhap
//            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
//            if (userToken != null) {
//                this.assignStockModelForIsdnForm.setShopId(userToken.getShopId());
//                this.assignStockModelForIsdnForm.setShopCode(userToken.getShopCode());
//                this.assignStockModelForIsdnForm.setShopName(userToken.getShopName());
//            }

            //set loai dich vu mac dinh la mobile
            this.assignStockModelForIsdnForm.setStockTypeId(Constant.STOCK_ISDN_MOBILE);

            //lay du lieu cho combobox
            getDataForCombobox();

            //
            List<String> listMessage = new ArrayList<String>();
            String userSessionId = req.getSession().getId();
            AssignStockModelForIsdnDAO.listProgressMessage.put(userSessionId, listMessage);
            
        } catch (Exception ex) {
            //rollback
            session.clear();

            //ghi log
            ex.printStackTrace();

            //hien thi message
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Exception: " + ex.toString());
        }

        //return
        pageForward = ASSIGN_STOCK_MODEL_FOR_ISDN;
        log.debug("End method preparePage of AssignStockModelForIsdnDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 27/05/2009
     * tim kiem cac dai isdn thoa man dieu kien
     * Modified by NamNX - bo sung chuc nang nhap du lieu tu file 15/1/2010
     *
     */
    public String searchIsdnRange() throws Exception {
        log.info("begin method searchIsdnRange of AssignStockModelForIsdnDAO ...");

        HttpServletRequest req = getRequest();
        Session session = getSession();

        try {
            Long impType = this.assignStockModelForIsdnForm.getImpType();

            if (impType.equals(IMP_BY_ISDN_RANGE)) {
                String resultCheckValidIsdnRange = checkValidIsdnRange();
                if (resultCheckValidIsdnRange == null || !resultCheckValidIsdnRange.equals("")) {
                    req.setAttribute(REQUEST_MESSAGE, resultCheckValidIsdnRange);

                    pageForward = ASSIGN_STOCK_MODEL_FOR_ISDN;
                    log.info("end method searchIsdnRange of AssignStockModelForIsdnDAO");
                    return pageForward;
                }

                Long stockTypeId = this.assignStockModelForIsdnForm.getStockTypeId();
                Long shopId = this.assignStockModelForIsdnForm.getShopId();
                Long stockModelId = this.assignStockModelForIsdnForm.getStockModelId();
                String strFromIsdn = this.assignStockModelForIsdnForm.getFromIsdn();
                String strToIsdn = this.assignStockModelForIsdnForm.getToIsdn();
                String strFromPrice = this.assignStockModelForIsdnForm.getFromPrice();
                String strToPrice = this.assignStockModelForIsdnForm.getToPrice();
                String groupFilterRuleCode = this.assignStockModelForIsdnForm.getGroupFilterRuleCode();
                Long filterTypeId = this.assignStockModelForIsdnForm.getFilterTypeId();
                Long ruleId = this.assignStockModelForIsdnForm.getRulesId();

                String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
                List listParameter = new ArrayList();

                //
                //cau lenh select ra cac khoang isdn theo min, max, isdn_type, status, owner_id
                StringBuffer strIsdnRangeQuery = new StringBuffer("");
                strIsdnRangeQuery.append("SELECT MIN(isdn) lb, MAX (isdn) ub, price, owner_id, stock_model_id ");
                strIsdnRangeQuery.append("FROM (SELECT isdn isdn, ");
                strIsdnRangeQuery.append("          isdn - ROW_NUMBER () OVER (ORDER BY isdn) rn, ");
                strIsdnRangeQuery.append("          price, owner_id, stock_model_id ");
                strIsdnRangeQuery.append("      FROM " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName) + " a ");
                strIsdnRangeQuery.append("      WHERE 1 = 1 ");
                strIsdnRangeQuery.append("          and to_number(a.isdn) >= ? ");
                listParameter.add(Long.valueOf(strFromIsdn.trim()));
                strIsdnRangeQuery.append("          and to_number(a.isdn) <= ? ");
                listParameter.add(Long.valueOf(strToIsdn.trim()));

                if (shopId != null && shopId.compareTo(0L) > 0) {
                    strIsdnRangeQuery.append("       and a.owner_type = ? and a.owner_id = ? ");
                    listParameter.add(Constant.OWNER_TYPE_SHOP);
                    listParameter.add(shopId);
                }

                if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
                    strIsdnRangeQuery.append("      and a.stock_model_id = ? ");
                    listParameter.add(stockModelId);
                }

//                if (ruleId != null) {
//                    strIsdnRangeQuery.append("      and a.rules_id = ? ");
//                    listParameter.add(ruleId);
//                } else if (filterTypeId != null) {
//                    strIsdnRangeQuery.append("      and EXISTS (" +
//                            "SELECT 'X' FROM isdn_filter_rules " +
//                            "WHERE filter_type_id = ? and rules_id = a.rules_id) ");
//                    listParameter.add(filterTypeId);
//                } else if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("")) {
//                    strIsdnRangeQuery.append("      and EXISTS (" +
//                            "SELECT 'X' FROM isdn_filter_rules b " +
//                            "WHERE  b.rules_id = a.rules_id " +
//                            "       and EXISTS (" +
//                            "                   SELECT 'Y' FROM filter_type " +
//                            "                   WHERE group_filter_rule_code = ? and filter_type_id = b.filter_type_id " +
//                            "                   )) ");
//                    listParameter.add(groupFilterRuleCode.trim());
//                }
//                if (strFromPrice != null && !strFromPrice.equals("")) {
//                    strIsdnRangeQuery.append("      and a.price >= ? ");
//                    listParameter.add(strFromPrice);
//                }
//                if (strToPrice != null && !strToPrice.equals("")) {
//                    strIsdnRangeQuery.append("      and a.price <= ? ");
//                    listParameter.add(strToPrice);
//                }

                //chi cho phep phan phoi so moi va so ngung su dung
                strIsdnRangeQuery.append("      and (status = ? or status = ? ) ");
                listParameter.add(Constant.STATUS_ISDN_NEW);
                listParameter.add(Constant.STATUS_ISDN_SUSPEND);

                strIsdnRangeQuery.append("      ) ");
                strIsdnRangeQuery.append("GROUP BY rn, price, owner_id, stock_model_id ");
                strIsdnRangeQuery.append("ORDER BY 1 ");

                //join bang shop voi cau lenh tren de tim ra danh sach cac khoang isdn + thong tin ve kho chua isdn
                //ham khoi tao: public DistributeIsdnForm(Long formId, Long serviceType, String isdnType, Long status, String startIsdn, String endIsdn, Long countIsdn, Long fromShopId, String fromShopCode, String fromShopName) {
                StringBuffer strSearchQuery = new StringBuffer("");
                strSearchQuery.append("SELECT rownum formId, " + stockTypeId + " stockTypeId, " +
                        "a.lb fromIsdn, a.ub toIsdn, (a.ub - a.lb + 1) countIsdn, a.price fromPrice, a.price toPrice, " +
                        "a.owner_id shopId, b.shop_code shopCode, b.name shopName, " +
                        "a.stock_model_id stockModelId, " +
                        "(SELECT c.stock_model_code FROM stock_model c WHERE c.stock_model_id = a.stock_model_id) stockModelCode, " +
                        "(SELECT c.NAME FROM stock_model c WHERE c.stock_model_id = a.stock_model_id) stockModelName ");
                strSearchQuery.append("FROM (").append(strIsdnRangeQuery).append(") a, shop b ");
                strSearchQuery.append("WHERE a.owner_id = b.shop_id ");

                Query searchQuery = getSession().createSQLQuery(strSearchQuery.toString())
                        .addScalar("formId", Hibernate.LONG)
                        .addScalar("stockTypeId", Hibernate.LONG)
                        .addScalar("fromIsdn", Hibernate.STRING)
                        .addScalar("toIsdn", Hibernate.STRING)
                        .addScalar("countIsdn", Hibernate.LONG)
                        .addScalar("fromPrice", Hibernate.STRING)
                        .addScalar("toPrice", Hibernate.STRING)
                        .addScalar("shopId", Hibernate.LONG)
                        .addScalar("shopCode", Hibernate.STRING)
                        .addScalar("shopName", Hibernate.STRING)
                        .addScalar("stockModelId", Hibernate.LONG)
                        .addScalar("stockModelCode", Hibernate.STRING)
                        .addScalar("stockModelName", Hibernate.STRING)
                        .setResultTransformer(Transformers.aliasToBean(AssignStockModelForIsdnForm.class));
                
                for (int i = 0; i < listParameter.size(); i++) {
                    searchQuery.setParameter(i, listParameter.get(i));
                }

                List<AssignStockModelForIsdnForm> listAssignStockModelForIsdnForm = searchQuery.list();
                setTabSession(SESSION_LIST_ISDN_RANGE, listAssignStockModelForIsdnForm);

                if (listAssignStockModelForIsdnForm != null && listAssignStockModelForIsdnForm.size() != 0) {
                    req.setAttribute(REQUEST_MESSAGE, "Tìm kiếm được" + listAssignStockModelForIsdnForm.size() + " dải số thỏa mãn điều kiện");
                } else {
                    req.setAttribute(REQUEST_MESSAGE, "Không tìm kiếm được dải số nào thỏa mãn điều kiện");
                }
                
            } else if (impType.equals(IMP_BY_FILE)) {
                StringBuffer strMessage = new StringBuffer("");
                List<AssignStockModelForIsdnForm> listAssignStockModelForIsdnForm = searchIsdnRangeByFile(strMessage);
                setTabSession(SESSION_LIST_ISDN_RANGE, listAssignStockModelForIsdnForm);

                String message = "";
                if (listAssignStockModelForIsdnForm != null && listAssignStockModelForIsdnForm.size() != 0) {
                    message = "Tìm kiếm được " + listAssignStockModelForIsdnForm.size() + " dải số thỏa mãn điều kiện";
                } else {
                    message = "Không tìm kiếm được dải số nào thỏa mãn điều kiện";
                }

                if(strMessage != null && !strMessage.toString().equals("")) {
                    req.setAttribute(REQUEST_ERROR_FILE_PATH, strMessage);
                }

                req.setAttribute(REQUEST_MESSAGE, message);

            } else {
                req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Kiểu nhập dữ liệu không đúng");
                
            }
        } catch (Exception ex) {
            //rollback
            session.clear();

            //ghi log
            ex.printStackTrace();

            //hien thi message
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Exception: " + ex.toString());
        }

        //return
        pageForward = ASSIGN_STOCK_MODEL_FOR_ISDN;
        log.info("end method searchIsdnRange of AssignStockModelForIsdnDAO");
        return pageForward;

    }

    /**
     *
     * author   : tamdt1
     * date     : 19/09/2009
     * purpose  : tim kiem dai so can gan mat hang theo file
     *
     */
    private List<AssignStockModelForIsdnForm> searchIsdnRangeByFile(
            StringBuffer strMessage) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<AssignStockModelForIsdnForm> listResult = new ArrayList<AssignStockModelForIsdnForm>();
        List<AssignStockModelForIsdnForm> listError = new ArrayList<AssignStockModelForIsdnForm>();

        //lay du lieu tu file
        String serverFileName = CommonDAO.getSafeFileName(this.assignStockModelForIsdnForm.getServerFileName());
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List listIsdn = new ArrayList();
        try {
            listIsdn = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 6);
        } catch (Exception ex) {
            ex.printStackTrace();
            strMessage.append("!!!Lỗi. " + ex.toString());

            return listResult;
        }
        if (listIsdn == null || listIsdn.size() == 0) {
            //
            strMessage.append("!!!Lỗi. File không có dữ liệu");
            return listResult;
        }

        //mapping mo ta va ma
        //dich vu
        HashMap<String, Long> hashMapService = new HashMap<String, Long>();
        hashMapService.put(Constant.STOCK_ISDN_MOBILE_DESC, Constant.STOCK_ISDN_MOBILE);
        hashMapService.put(Constant.STOCK_ISDN_HOMEPHONE_DESC, Constant.STOCK_ISDN_HOMEPHONE);
        hashMapService.put(Constant.STOCK_ISDN_PSTN_DESC, Constant.STOCK_ISDN_PSTN);
        hashMapService.put("-1", -1L);

        for (int idx = 0; idx < listIsdn.size(); idx++) {
            Object[] obj = (Object[]) listIsdn.get(idx); //1 hang trong file excel
            if (obj != null && obj.length >= 7) {
                //so isdn
                String tmpFromIsdn = obj[0] != null ? obj[0].toString().trim() : "";
                //
                String tmpToIsdn = obj[1] != null ? obj[1].toString().trim() : "";
                //service Type
                String tmpStockTypeId = obj[2] != null ? obj[2].toString().trim() : "-1";
                //ma kho lay so
                String tmpShopCode = obj[3] != null ? obj[3].toString().trim() : "";
                //ma mat hang
                String tmpStockModelCode = obj[4] != null ? obj[4].toString().trim() : "";
                //price
                String tmpFromPrice = obj[5] != null ? obj[5].toString().trim() : "";
                //
                String tmpToPrice = obj[6] != null ? obj[6].toString().trim() : "";

                //
                boolean bInvalidFormatData = false;
                StringBuffer strInvalidFormatData = new StringBuffer("");
                Long tmpStockTypeId1 = hashMapService.get(tmpStockTypeId);
                if(tmpStockTypeId1 == null) {
                    strInvalidFormatData.append("@assignStockModelForIsdn.error.servicesInvalidFormatData");
                    bInvalidFormatData  = true;
                } else if (tmpStockTypeId1.equals(-1L)) {
                    tmpStockTypeId1 = null;
                }

                if (bInvalidFormatData) {
                    //gap loi xay ra
                    AssignStockModelForIsdnForm errAssignStockModelForIsdnForm = new AssignStockModelForIsdnForm();
                    errAssignStockModelForIsdnForm.setFromIsdn(tmpFromIsdn);
                    errAssignStockModelForIsdnForm.setToIsdn(tmpToIsdn);
                    errAssignStockModelForIsdnForm.setStockTypeId(tmpStockTypeId1);
                    errAssignStockModelForIsdnForm.setShopCode(tmpShopCode);
                    errAssignStockModelForIsdnForm.setStockModelCode(tmpStockModelCode);
                    errAssignStockModelForIsdnForm.setFromPrice(tmpFromPrice);
                    errAssignStockModelForIsdnForm.setToPrice(tmpToPrice);
                    errAssignStockModelForIsdnForm.setErrorMessage(strInvalidFormatData.toString());
                    listError.add(errAssignStockModelForIsdnForm);

                    continue;
                }

                //tim du lieu
                this.assignStockModelForIsdnForm.setFromIsdn(tmpFromIsdn);
                this.assignStockModelForIsdnForm.setToIsdn(tmpToIsdn);
                this.assignStockModelForIsdnForm.setStockTypeId(tmpStockTypeId1);
                this.assignStockModelForIsdnForm.setShopCode(tmpShopCode);
                this.assignStockModelForIsdnForm.setStockModelCode(tmpStockModelCode);
                this.assignStockModelForIsdnForm.setFromPrice(tmpFromPrice);
                this.assignStockModelForIsdnForm.setToPrice(tmpToPrice);


                String resultCheckValidIsdnRange = checkValidIsdnRange();
                if (resultCheckValidIsdnRange == null || !resultCheckValidIsdnRange.equals("")) {
                    //gap loi xay ra
                    AssignStockModelForIsdnForm errAssignStockModelForIsdnForm = new AssignStockModelForIsdnForm();
                    errAssignStockModelForIsdnForm.setFromIsdn(tmpFromIsdn);
                    errAssignStockModelForIsdnForm.setToIsdn(tmpToIsdn);
                    errAssignStockModelForIsdnForm.setStockTypeId(tmpStockTypeId1);
                    errAssignStockModelForIsdnForm.setShopCode(tmpShopCode);
                    errAssignStockModelForIsdnForm.setStockModelCode(tmpStockModelCode);
                    errAssignStockModelForIsdnForm.setFromPrice(tmpFromPrice);
                    errAssignStockModelForIsdnForm.setToPrice(tmpToPrice);
                    errAssignStockModelForIsdnForm.setErrorMessage(resultCheckValidIsdnRange);
                    listError.add(errAssignStockModelForIsdnForm);

                    continue;
                }

                Long stockTypeId = this.assignStockModelForIsdnForm.getStockTypeId();
                Long shopId = this.assignStockModelForIsdnForm.getShopId();
                Long stockModelId = this.assignStockModelForIsdnForm.getStockModelId();
                String strFromIsdn = this.assignStockModelForIsdnForm.getFromIsdn();
                String strToIsdn = this.assignStockModelForIsdnForm.getToIsdn();
                String strFromPrice = this.assignStockModelForIsdnForm.getFromPrice();
                String strToPrice = this.assignStockModelForIsdnForm.getToPrice();

                String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
                List listParameter = new ArrayList();

                //
                //cau lenh select ra cac khoang isdn theo min, max, isdn_type, status, owner_id
                StringBuffer strIsdnRangeQuery = new StringBuffer("");
                strIsdnRangeQuery.append("SELECT MIN(isdn) lb, MAX (isdn) ub, price, owner_id, stock_model_id ");
                strIsdnRangeQuery.append("FROM (SELECT isdn isdn, ");
                strIsdnRangeQuery.append("          isdn - ROW_NUMBER () OVER (ORDER BY isdn) rn, ");
                strIsdnRangeQuery.append("          price, owner_id, stock_model_id ");
                strIsdnRangeQuery.append("      FROM " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName) + " a ");
                strIsdnRangeQuery.append("      WHERE 1 = 1 ");
                strIsdnRangeQuery.append("          and to_number(a.isdn) >= ? ");
                listParameter.add(Long.valueOf(strFromIsdn.trim()));
                strIsdnRangeQuery.append("          and to_number(a.isdn) <= ? ");
                listParameter.add(Long.valueOf(strToIsdn.trim()));

                if (shopId != null && shopId.compareTo(0L) > 0) {
                    strIsdnRangeQuery.append("       and a.owner_type = ? and a.owner_id = ? ");
                    listParameter.add(Constant.OWNER_TYPE_SHOP);
                    listParameter.add(shopId);
                }

                if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
                    strIsdnRangeQuery.append("      and a.stock_model_id = ? ");
                    listParameter.add(stockModelId);
                }

                if (strFromPrice != null && !strFromPrice.equals("")) {
                    strIsdnRangeQuery.append("      and a.price >= ? ");
                    listParameter.add(strFromPrice);
                }
                if (strToPrice != null && !strToPrice.equals("")) {
                    strIsdnRangeQuery.append("      and a.price <= ? ");
                    listParameter.add(strToPrice);
                }

                //chi cho phep phan phoi so moi va so ngung su dung
                strIsdnRangeQuery.append("      and (a.status = ? or a.status = ? ) ");
                listParameter.add(Constant.STATUS_ISDN_NEW);
                listParameter.add(Constant.STATUS_ISDN_SUSPEND);

                strIsdnRangeQuery.append("      ) ");
                strIsdnRangeQuery.append("GROUP BY rn, price, owner_id, stock_model_id ");
                strIsdnRangeQuery.append("ORDER BY 1 ");

                //join bang shop voi cau lenh tren de tim ra danh sach cac khoang isdn + thong tin ve kho chua isdn
                //ham khoi tao: public DistributeIsdnForm(Long formId, Long serviceType, String isdnType, Long status, String startIsdn, String endIsdn, Long countIsdn, Long fromShopId, String fromShopCode, String fromShopName) {
                int beginIndex = listResult.size();
                StringBuffer strSearchQuery = new StringBuffer("");
                strSearchQuery.append("SELECT (rownum + " + beginIndex + ") formId, " + stockTypeId + " stockTypeId, " +
                        "a.lb fromIsdn, a.ub toIsdn, (a.ub - a.lb + 1) countIsdn, a.price fromPrice, a.price toPrice, " +
                        "a.owner_id shopId, b.shop_code shopCode, b.name shopName, " +
                        "a.stock_model_id stockModelId, " +
                        "(SELECT c.stock_model_code FROM stock_model c WHERE c.stock_model_id = a.stock_model_id) stockModelCode, " +
                        "(SELECT c.NAME FROM stock_model c WHERE c.stock_model_id = a.stock_model_id) stockModelName ");
                strSearchQuery.append("FROM (").append(strIsdnRangeQuery).append(") a, shop b ");
                strSearchQuery.append("WHERE a.owner_id = b.shop_id ");

                Query searchQuery = getSession().createSQLQuery(strSearchQuery.toString())
                        .addScalar("formId", Hibernate.LONG)
                        .addScalar("stockTypeId", Hibernate.LONG)
                        .addScalar("fromIsdn", Hibernate.STRING)
                        .addScalar("toIsdn", Hibernate.STRING)
                        .addScalar("countIsdn", Hibernate.LONG)
                        .addScalar("fromPrice", Hibernate.STRING)
                        .addScalar("toPrice", Hibernate.STRING)
                        .addScalar("shopId", Hibernate.LONG)
                        .addScalar("shopCode", Hibernate.STRING)
                        .addScalar("shopName", Hibernate.STRING)
                        .addScalar("stockModelId", Hibernate.LONG)
                        .addScalar("stockModelCode", Hibernate.STRING)
                        .addScalar("stockModelName", Hibernate.STRING)
                        .setResultTransformer(Transformers.aliasToBean(AssignStockModelForIsdnForm.class));

                for (int i = 0; i < listParameter.size(); i++) {
                    searchQuery.setParameter(i, listParameter.get(i));
                }

                List<AssignStockModelForIsdnForm> listDistributeIsdnForm = searchQuery.list();

                if (listDistributeIsdnForm != null && listDistributeIsdnForm.size() > 0) {
                    listResult.addAll(listDistributeIsdnForm);
                } else {
                    //khong tim thay so isdn thoa man dieu kien tim kiem
                    AssignStockModelForIsdnForm errAssignStockModelForIsdnForm = new AssignStockModelForIsdnForm();
                    errAssignStockModelForIsdnForm.setFromIsdn(tmpFromIsdn);
                    errAssignStockModelForIsdnForm.setToIsdn(tmpToIsdn);
                    errAssignStockModelForIsdnForm.setStockTypeId(tmpStockTypeId1);
                    errAssignStockModelForIsdnForm.setShopCode(tmpShopCode);
                    errAssignStockModelForIsdnForm.setStockModelCode(tmpStockModelCode);
                    errAssignStockModelForIsdnForm.setFromPrice(tmpFromPrice);
                    errAssignStockModelForIsdnForm.setToPrice(tmpToPrice);
                    errAssignStockModelForIsdnForm.setErrorMessage("assignStockModelForIsdn.error.isdnRangeNotFound");
                    listError.add(errAssignStockModelForIsdnForm);
                }
            }
        }

        //ket xuat ket qua ra file excel trong truong hop co loi
        if (listError != null && listError.size() > 0) {
            try {
                String DATE_FORMAT = "yyyyMMddHHmmss";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
                filePath = filePath != null ? filePath : "/";
                //UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
                filePath += com.viettel.security.util.StringEscapeUtils.getSafeFileName("assignStockModelForIsdnErr_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls");
                String realPath = req.getSession().getServletContext().getRealPath(filePath);

                String templatePath = ResourceBundleUtils.getResource("ASMFI_TMP_PATH_ERR");
                if (templatePath == null || templatePath.trim().equals("")) {
                    //khong tim thay duong dan den file template de xuat ket qua
                    strMessage.append("assignStockModelForIsdn.error.errTemplateNotExist");
                    return listResult;
                }

                String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
                File fTemplateFile = new File(realTemplatePath);
                if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                    //file ko ton tai
                    strMessage.append("assignStockModelForIsdn.error.errTemplateNotExist");
                    return listResult;
                }

                Map beans = new HashMap();
                beans.put("listError", listError);

                XLSTransformer transformer = new XLSTransformer();
                transformer.transformXLS(realTemplatePath, beans, realPath);

                strMessage.append(filePath);

            } catch (Exception ex) {
                ex.printStackTrace();
                strMessage.append("!!!Lỗi. " + ex.toString());
            }

        }


        return listResult;
    }

    /**
     *
     * author   :tamdt1
     * date     : 19/09/2009
     * purpose  : kiem tra tinh hop le cua dai so truoc khi gan mat hang
     *
     */
    private String checkValidIsdnRange() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String shopCode = this.assignStockModelForIsdnForm.getShopCode();
            String stockModelCode = this.assignStockModelForIsdnForm.getStockModelCode();
            Long stockTypeId = this.assignStockModelForIsdnForm.getStockTypeId();
            String strFromIsdn = this.assignStockModelForIsdnForm.getFromIsdn();
            String strToIsdn = this.assignStockModelForIsdnForm.getToIsdn();
            String strFromPrice = this.assignStockModelForIsdnForm.getFromPrice();
            String strToPrice = this.assignStockModelForIsdnForm.getToPrice();

            //kiem tra cac truong bat buoc
            if (stockTypeId == null
                    || strFromIsdn == null || strFromIsdn.trim().equals("")
                    || strToIsdn == null || strToIsdn.trim().equals("")) {

                return "assignStockModelForIsdn.error.requiredFieldsEmpty";
            }

            //kiem tra su ton tai cua stock_type_id
            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            if (strTableName == null || strTableName.equals("")) {
                return "assignStockModelForIsdn.error.stockTypeTableNotExist";
            }

            //kiem tra shop co ton tai khong
            Long ownerId = 0L;
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
                    return "assignStockModelForIsdn.error.fromShopCodeNotExist";
                }
                ownerId = listShop.get(0).getShopId();
            }

            //kiem tra stock model co ton tai khong
            Long stockModelId = 0L;
            if (stockModelCode != null && !stockModelCode.trim().equals("")) {
                String strStockModelQuery = "from StockModel a "
                        + "where lower(a.stockModelCode) = ? and a.status = ? and a.stockTypeId = ? ";
                Query stockModelQuery = getSession().createQuery(strStockModelQuery);
                stockModelQuery.setParameter(0, stockModelCode.trim().toLowerCase());
                stockModelQuery.setParameter(1, Constant.STATUS_ACTIVE);
                stockModelQuery.setParameter(2, stockTypeId);
                List<StockModel> listStockModel = stockModelQuery.list();
                if (listStockModel == null || listStockModel.size() == 0) {
                    return "assignStockModelForIsdn.error.stockModelNotExist";
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
                return "assignStockModelForIsdn.error.isdnIsNotInteger";
            }

            //kiem tra truong fromNumber khong duoc lon hon truong toNumber
            if (fromIsdn.compareTo(toIsdn) > 0) {
                return "assignStockModelForIsdn.error.invalidIsdn";
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
                return "assignStockModelForIsdn.error.quantityOverMaximum";
            }

//            //kiem tra truong gia phai la so khong am
//            Long fromPrice = 0L;
//            Long toPrice = 0L;
//            if (strFromPrice != null && !strFromPrice.trim().equals("")) {
//                try {
//                    fromPrice = Long.valueOf(strFromPrice.trim());
//                } catch (NumberFormatException ex) {
//                    return "assignStockModelForIsdn.error.priceIsNotInteger";
//                }
//            }
//            if (strToPrice != null && !strToPrice.trim().equals("")) {
//                try {
//                    toPrice = Long.valueOf(strToPrice.trim());
//                } catch (NumberFormatException ex) {
//                    return "assignStockModelForIsdn.error.priceIsNotInteger";
//                }
//            }
//
//            //kiem tra truong fromPricekhong duoc lon hon truong toPrice
//            if (fromPrice.compareTo(toPrice) > 0) {
//                return "assignStockModelForIsdn.error.invalidPrice";
//            }

            //
            this.assignStockModelForIsdnForm.setShopId(ownerId);
            this.assignStockModelForIsdnForm.setStockModelId(stockModelId);

            //trim cac truong can thiet
            this.assignStockModelForIsdnForm.setShopCode(shopCode.trim());
            this.assignStockModelForIsdnForm.setStockModelCode(stockModelCode.trim());
            this.assignStockModelForIsdnForm.setFromIsdn(strFromIsdn.trim());
            this.assignStockModelForIsdnForm.setToIsdn(strToIsdn.trim());
//            if(strFromPrice != null) {
//                this.assignStockModelForIsdnForm.setFromPrice(strFromPrice.trim());
//            }
//            if(strToPrice != null) {
//                this.assignStockModelForIsdnForm.setToPrice(strToPrice.trim());
//            }

            return "";

        } catch (Exception e) {
            e.printStackTrace();
            return "!!!Error function checkValidIsdnRange(): " + e.toString();

        }
    }

    /**
     *
     * author tamdt1
     * date: 25/05/2009
     * gan stockmodel moi cho cac so isdn
     *
     */
    public String assignStockModelForIsdn() throws Exception {
        log.debug("begin method assignStockModelForIsdn of AssignStockModelForIsdnDAO");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Long impType = this.assignStockModelForIsdnForm.getImpType();
        if(impType.equals(IMP_BY_FILE)) {
            //neu la im port boi file
            pageForward = assignStockModelForIsdnByFile();
            log.debug("end method assignStockModelForIsdn of AssignStockModelForIsdnDAO");

            return pageForward;
        }

        //kiem tra list co rong khong
        List<AssignStockModelForIsdnForm> listAssignStockModelForIsdnForm = (List<AssignStockModelForIsdnForm>) getTabSession(SESSION_LIST_ISDN_RANGE);
        if (listAssignStockModelForIsdnForm == null || listAssignStockModelForIsdnForm.size() == 0) {
            //
            req.setAttribute(REQUEST_MESSAGE, "assignStockModelForIsdn.error.listIsEmpty");

            pageForward = ASSIGN_STOCK_MODEL_FOR_ISDN;
            log.info("End method assignStockModelForIsdn of AssignStockModelForIsdnDAO");
            return pageForward;
        }

        Session session = getSession();
        List<AssignStockModelForIsdnForm> listError = new ArrayList<AssignStockModelForIsdnForm>();

        try {
            String newStockModelCode = this.assignStockModelForIsdnForm.getNewStockModelCode();
            //kiem tra cac truong bat buoc
            if (newStockModelCode == null || newStockModelCode.trim().equals("")) {

                req.setAttribute(REQUEST_MESSAGE, "assignStockModelForIsdn.error.requiredFieldsEmpty");

                pageForward = ASSIGN_STOCK_MODEL_FOR_ISDN;
                log.info("End method assignStockModelForIsdn of AssignStockModelForIsdnDAO");
                return pageForward;
            }

            //kiem tra mat hang moi co ton tai khong
            Long newStockModelId = 0L;
            Long newStockTypeId = 0L;
            Double newSourcePrice = 0D;
            if (newStockModelCode != null && !newStockModelCode.trim().equals("")) {
                String strStockModelQuery = "from StockModel a "
                        + "where lower(a.stockModelCode) = ? and a.status = ? " +
                        "        and (a.stockTypeId = ? or a.stockTypeId = ? or a.stockTypeId = ?) ";
                Query stockModelQuery = getSession().createQuery(strStockModelQuery);
                stockModelQuery.setParameter(0, newStockModelCode.trim().toLowerCase());
                stockModelQuery.setParameter(1, Constant.STATUS_ACTIVE);
                stockModelQuery.setParameter(2, Constant.STOCK_ISDN_MOBILE);
                stockModelQuery.setParameter(3, Constant.STOCK_ISDN_HOMEPHONE);
                stockModelQuery.setParameter(4, Constant.STOCK_ISDN_PSTN);
                List<StockModel> listStockModel = stockModelQuery.list();
                if (listStockModel == null || listStockModel.size() != 1) {
                    req.setAttribute(REQUEST_MESSAGE, "assignStockModelForIsdn.error.stockModelNotExist");

                    pageForward = ASSIGN_STOCK_MODEL_FOR_ISDN;
                    log.info("End method assignStockModelForIsdn of AssignStockModelForIsdnDAO");
                    return pageForward;
                }
                newStockModelId = listStockModel.get(0).getStockModelId();
                newStockTypeId = listStockModel.get(0).getStockTypeId();
                newSourcePrice = listStockModel.get(0).getSourcePrice();

            } else {
                req.setAttribute(REQUEST_MESSAGE, "assignStockModelForIsdn.error.stockModelNotExist");

                pageForward = ASSIGN_STOCK_MODEL_FOR_ISDN;
                log.info("End method assignStockModelForIsdn of AssignStockModelForIsdnDAO");
                return pageForward;
            }

            String[] selectedFormId = this.assignStockModelForIsdnForm.getSelectedFormId();
            if (selectedFormId == null || selectedFormId.length <= 0 || selectedFormId[0].equals("false")) {
                req.setAttribute(REQUEST_MESSAGE, "assignStockModelForIsdn.error.noIsdnRangeHasSelected");

                pageForward = ASSIGN_STOCK_MODEL_FOR_ISDN;
                log.info("End method assignStockModelForIsdn of AssignStockModelForIsdnDAO");
                return pageForward;
            }

            for (int i = 0; i < selectedFormId.length; i++) {
                AssignStockModelForIsdnForm tmpAssignStockModelForIsdnForm = listAssignStockModelForIsdnForm.get(Integer.parseInt(selectedFormId[i]) - 1); //-1 do chi so mang bat dau tu 0, rownum bat dau tu 1
                try {
                    Long stockTypeId = tmpAssignStockModelForIsdnForm.getStockTypeId();
                    String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
                    if (strTableName == null || strTableName.equals("")) {
                        AssignStockModelForIsdnForm errAssignStockModelForIsdnForm = new AssignStockModelForIsdnForm();
                        errAssignStockModelForIsdnForm.copyProperties(tmpAssignStockModelForIsdnForm);
                        errAssignStockModelForIsdnForm.setErrorMessage("assignStockModelForIsdn.error.stockTypeTableNotExist");
                        listError.add(errAssignStockModelForIsdnForm);

                        continue;
                    }
                    if(!stockTypeId.equals(newStockTypeId)) {
                        AssignStockModelForIsdnForm errAssignStockModelForIsdnForm = new AssignStockModelForIsdnForm();
                        errAssignStockModelForIsdnForm.copyProperties(tmpAssignStockModelForIsdnForm);
                        errAssignStockModelForIsdnForm.setErrorMessage("assignStockModelForIsdn.error.stockModelNotExist");
                        listError.add(errAssignStockModelForIsdnForm);

                        continue;
                    }

                    List listParameter = new ArrayList();
                    StringBuffer strQuery = new StringBuffer(" ");
                    strQuery.append("update ").append(strTableName).append(" a ");
                    strQuery.append("set    a.stock_model_id = ?, ");
                    strQuery.append("       a.price = ?, ");
                    strQuery.append("       a.LAST_UPDATE_USER = ?, ");
                    strQuery.append("       a.LAST_UPDATE_IP_ADDRESS = ?, ");
//                    strQuery.append("       a.LAST_UPDATE_TIME = ?, ");
                    strQuery.append("       a.LAST_UPDATE_TIME = sysdate, ");
                    strQuery.append("       a.LAST_UPDATE_KEY = ? ");
                    listParameter.add(newStockModelId);
                    listParameter.add(newSourcePrice);
                    listParameter.add(userToken.getLoginName()); //last_update_user
                    listParameter.add(req.getRemoteAddr()); //LAST_UPDATE_IP_ADDRESS
//                    listParameter.add(new java.sql.Date(DateTimeUtils.getSysDate().getTime())); //LAST_UPDATE_TIME
                    listParameter.add(Constant.LAST_UPDATE_KEY); //LAST_UPDATE_KEY

                    Long shopId = tmpAssignStockModelForIsdnForm.getShopId();
                    Long stockModelId = tmpAssignStockModelForIsdnForm.getStockModelId();
                    String strFromIsdn = tmpAssignStockModelForIsdnForm.getFromIsdn();
                    String strToIsdn = tmpAssignStockModelForIsdnForm.getToIsdn();
                    String strFromPrice = tmpAssignStockModelForIsdnForm.getFromPrice();
                    String strToPrice = tmpAssignStockModelForIsdnForm.getToPrice();
                    Long ruleId = tmpAssignStockModelForIsdnForm.getRulesId();
                    Long filterTypeId = tmpAssignStockModelForIsdnForm.getFilterTypeId();
                    String groupFilterRuleCode = tmpAssignStockModelForIsdnForm.getGroupFilterRuleCode();

                    Long fromIsdn = Long.valueOf(strFromIsdn.trim());
                    Long toIsdn = Long.valueOf(strToIsdn.trim());

                    strQuery.append("where 1 = 1 ");

                    strQuery.append("and to_number(a.isdn) >= ? ");
                    listParameter.add(fromIsdn);

                    strQuery.append("and to_number(a.isdn) <= ? ");
                    listParameter.add(toIsdn);

                    if (shopId != null && shopId.compareTo(0L) > 0) {
                        strQuery.append("       and a.owner_type = ? and a.owner_id = ? ");
                        listParameter.add(Constant.OWNER_TYPE_SHOP);
                        listParameter.add(shopId);
                    }

                    if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
                        strQuery.append("      and a.stock_model_id = ? ");
                        listParameter.add(stockModelId);
                    }

                    if (ruleId != null) {
                        strQuery.append("      and a.rules_id = ? ");
                        listParameter.add(ruleId);
                    } else if (filterTypeId != null) {
                        strQuery.append("      and EXISTS ("
                                + "SELECT 'X' FROM isdn_filter_rules "
                                + "WHERE filter_type_id = ? and rules_id = a.rules_id) ");
                        listParameter.add(filterTypeId);
                    } else if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("")) {
                        strQuery.append("      and EXISTS ("
                                + "SELECT 'X' FROM isdn_filter_rules b "
                                + "WHERE  b.rules_id = a.rules_id "
                                + "       and EXISTS ("
                                + "                   SELECT 'Y' FROM filter_type "
                                + "                   WHERE group_filter_rule_code = ? and filter_type_id = b.filter_type_id "
                                + "                   )) ");
                        listParameter.add(groupFilterRuleCode.trim());
                    }
                    if (strFromPrice != null && !strFromPrice.equals("")) {
                        strQuery.append("      and a.price >= ? ");
                        listParameter.add(strFromPrice);
                    }
                    if (strToPrice != null && !strToPrice.equals("")) {
                        strQuery.append("      and a.price <= ? ");
                        listParameter.add(strToPrice);
                    }

                    //chi cho phep phan phoi so moi va so ngung su dung
                    strQuery.append("      and (a.status = ? or a.status = ? ) ");
                    listParameter.add(Constant.STATUS_ISDN_NEW);
                    listParameter.add(Constant.STATUS_ISDN_SUSPEND);

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
                        isdnTrans.setTransType(Constant.ISDN_TRANS_TYPE_ASSIGN_MODEL);
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
                        isdnTransDetail.setOldValue("STOCK_MODEL_ID@");
                        isdnTransDetail.setNewValue("STOCK_MODEL_ID@" + newStockModelId);
                        isdnTransDetail.setLastUpdateTime(now);
                        session.save(isdnTransDetail);

                        Long isdnTransDetailId_1 = getSequence("ISDN_TRANS_DETAIL_SEQ");
                        IsdnTransDetail isdnTransDetail_1 = new IsdnTransDetail();
                        isdnTransDetail_1.setIsdnTransDetailId(isdnTransDetailId_1);
                        isdnTransDetail_1.setIsdnTransId(isdnTransId);
                        isdnTransDetail_1.setFromIsdn(strFromIsdn);
                        isdnTransDetail_1.setToIsdn(strToIsdn);
                        isdnTransDetail_1.setQuantity(toIsdn - fromIsdn + 1);
                        isdnTransDetail_1.setOldValue("PRICE@");
                        isdnTransDetail_1.setNewValue("PRICE@" + newSourcePrice);
                        isdnTransDetail.setLastUpdateTime(now);
                        session.save(isdnTransDetail_1);

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

                        AssignStockModelForIsdnForm errAssignStockModelForIsdnForm = new AssignStockModelForIsdnForm();
                        errAssignStockModelForIsdnForm.copyProperties(tmpAssignStockModelForIsdnForm);
                        errAssignStockModelForIsdnForm.setErrorMessage("assignStockModelForIsdn.error.updateNotSuccess");
                        listError.add(errAssignStockModelForIsdnForm);

                        continue;

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                    session.clear();
                    session.beginTransaction();
                    session.getTransaction().rollback();
                    session.getTransaction().begin();

                    AssignStockModelForIsdnForm errAssignStockModelForIsdnForm = new AssignStockModelForIsdnForm();
                    errAssignStockModelForIsdnForm.copyProperties(tmpAssignStockModelForIsdnForm);
                    errAssignStockModelForIsdnForm.setErrorMessage("assignStockModelForIsdn.error.exception");
                    listError.add(errAssignStockModelForIsdnForm);

                    continue;
                }
            }



        } catch (Exception ex) {
            ex.printStackTrace();

            session.clear();
            session.beginTransaction();
            session.getTransaction().rollback();
            session.getTransaction().begin();

            req.setAttribute(REQUEST_MESSAGE, "assignStockModelForIsdn.error.exception");
        }

        //ket xuat ket qua ra file excel trong truong hop co loi
        if (listError != null && listError.size() > 0) {
            StringBuffer strMessage = new StringBuffer("");

            try {
                String DATE_FORMAT = "yyyyMMddHHmmss";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
                filePath = filePath != null ? filePath : "/";
                //UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
                filePath += com.viettel.security.util.StringEscapeUtils.getSafeFileName("assignStockModelForIsdnErr_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls");
                String realPath = req.getSession().getServletContext().getRealPath(filePath);

                String templatePath = ResourceBundleUtils.getResource("ASMFI_TMP_PATH_ERR");
                if (templatePath == null || templatePath.trim().equals("")) {
                    //khong tim thay duong dan den file template de xuat ket qua
                    strMessage.append("assignStockModelForIsdn.error.errTemplateNotExist");
                } else {
                    String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
                    File fTemplateFile = new File(realTemplatePath);
                    if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                        //file ko ton tai
                        strMessage.append("assignStockModelForIsdn.error.errTemplateNotExist");
                    } else {
                        Map beans = new HashMap();
                        beans.put("listError", listError);

                        XLSTransformer transformer = new XLSTransformer();
                        transformer.transformXLS(realTemplatePath, beans, realPath);

                        strMessage.append(filePath);
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                strMessage.append("!!!Lỗi. " + ex.toString());
            }

            if (strMessage != null && !strMessage.toString().equals("")) {
                req.setAttribute(REQUEST_ERROR_FILE_PATH, strMessage);
            }

        }

        //resetForm
        this.assignStockModelForIsdnForm.resetForm();

//        //set kho lay so mac dinh la user dang nhap
//        if (userToken != null) {
//            this.assignStockModelForIsdnForm.setShopId(userToken.getShopId());
//            this.assignStockModelForIsdnForm.setShopCode(userToken.getShopCode());
//            this.assignStockModelForIsdnForm.setShopName(userToken.getShopName());
//        }

        //xoa bien session
        removeTabSession(SESSION_LIST_ISDN_RANGE);

        //
        req.setAttribute(REQUEST_MESSAGE, "assignStockModelForIsdn.success");
        pageForward = ASSIGN_STOCK_MODEL_FOR_ISDN;
        log.debug("End method assignStockModelForIsdn of AssignStockModelForIsdnDAO");

        return pageForward;
    }

    private Map hashMapResult = new HashMap();

    public Map getHashMapResult() {
        return hashMapResult;
    }

    public void setHashMapResult(Map hashMapResult) {
        this.hashMapResult = hashMapResult;
    }

    /**
     *
     * author tamdt1
     * date: 21/06/2009
     *
     */
    public String updateListGroupFilterRule() throws Exception {
        log.info("Begin method updateListGroupFilterRule of AssignStockModelForIsdnDAO ...");

        try {
            HttpServletRequest req = getRequest();
            String strStockTypeId = req.getParameter("stockTypeId");
            String strTarget = req.getParameter("target");
            String[] arrTarget = strTarget.split(",");

            if (strStockTypeId != null && !strStockTypeId.trim().equals("") && arrTarget.length == 3) {
                Long stockTypeId = Long.valueOf(strStockTypeId);

                //lay danh sach cac tap luat
                String strQueryGroupFilter = "select groupFilterRuleCode, name from GroupFilterRule where stockTypeId = ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Query queryGroupFilter = getSession().createQuery(strQueryGroupFilter);
                queryGroupFilter.setParameter(0, stockTypeId);
                queryGroupFilter.setParameter(1, Constant.STATUS_USE);
                List listGroupFilterRule = queryGroupFilter.list();

                String[] headerGroupFilter = {"", "--Chọn tập luật--"};
                List tmpListGroupFilter = new ArrayList();
                tmpListGroupFilter.add(headerGroupFilter);
                tmpListGroupFilter.addAll(listGroupFilterRule);

                //reset lai cac vung Nhom luat va luat
                String[] headerFilterType = {"", "--Chọn nhóm luật--"};
                List tmpFilterType = new ArrayList();
                tmpFilterType.add(headerFilterType);
                String[] headerRule = {"", "--Chọn luật--"};
                List tmpRule = new ArrayList();
                tmpRule.add(headerRule);

                //them vao ket qua tra ve, cap nhat 3 vung (danh sach tap luat, nhom luat, va luat)
                this.hashMapResult.put(arrTarget[0], tmpListGroupFilter);
                this.hashMapResult.put(arrTarget[1], tmpFilterType);
                this.hashMapResult.put(arrTarget[2], tmpRule);


            } else if (arrTarget.length == 3) {
                //reset lai cac vung tuong ung

                String[] headerGroupFilter = {"", "--Chọn tập luật--"};
                List tmpListGroupFilter = new ArrayList();
                tmpListGroupFilter.add(headerGroupFilter);

                String[] headerFilterType = {"", "--Chọn nhóm luật--"};
                List tmpFilterType = new ArrayList();
                tmpFilterType.add(headerFilterType);

                String[] headerRule = {"", "--Chọn luật--"};
                List tmpRule = new ArrayList();
                tmpRule.add(headerRule);

                //them vao ket qua tra ve, cap nhat 3 vung (danh sach tap luat, nhom luat, va luat)
                this.hashMapResult.put(arrTarget[0], tmpListGroupFilter);
                this.hashMapResult.put(arrTarget[1], tmpFilterType);
                this.hashMapResult.put(arrTarget[2], tmpRule);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        //return
        pageForward = UPDATE_LIST_HASH_MAP_RESULT;
        log.info("Begin method updateListGroupFilterRule of AssignStockModelForIsdnDAO ...");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 27/05/2009
     * cap nhat danh sach cac filterType khi filterGroup thay doi
     *
     */
    public String updateListFilterType() throws Exception {
        log.info("begin method updateListFilterType of AssignStockModelForIsdnDAO ...");

        try {
            HttpServletRequest httpServletRequest = getRequest();

            //lay danh sach cac nhom luat thuoc ve tap luat nay
            String strGroupFilterRuleCode = httpServletRequest.getParameter("groupFilterRuleCode");
            String strTarget = httpServletRequest.getParameter("target").trim();
            String[] arrTarget = strTarget.split(",");
            if (strGroupFilterRuleCode != null && !strGroupFilterRuleCode.trim().equals("")) {
                String strQuery = "select filterTypeId, name from FilterType where groupFilterRuleCode = ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, strGroupFilterRuleCode);
                query.setParameter(1, Constant.STATUS_USE);
                List listStockModel = query.list();

                String[] header = {"", "--Chọn nhóm luật--"};
                List tmpList = new ArrayList();
                tmpList.add(header);
                tmpList.addAll(listStockModel);

                //
                String[] headerRule = {"", "--Chọn luật--"};
                List tmpListRule = new ArrayList();
                tmpListRule.add(headerRule);

                //them vao ket qua tra ve, cap nhat 2 vung
                hashMapResult.put(arrTarget[0], tmpList);
                hashMapResult.put(arrTarget[1], tmpListRule);
            } else {
                String[] header = {"", "--Chọn nhóm luật--"};
                List tmpList = new ArrayList();
                tmpList.add(header);

                String[] headerRule = {"", "--Chọn luật--"};
                List tmpListRule = new ArrayList();
                tmpListRule.add(headerRule);

                //them vao ket qua tra ve, cap nhat 2 vung, danh sach cac dlu va dau ma tinh
                hashMapResult.put(arrTarget[0], tmpList);
                hashMapResult.put(arrTarget[1], tmpListRule);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        //return
        pageForward = UPDATE_LIST_HASH_MAP_RESULT;
        log.info("end method updateListFilterType of AssignStockModelForIsdnDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 27/05/2009
     * cap nhat danh sach cac rule khi filter thay doi
     *
     */
    public String updateListRule() throws Exception {
        log.info("begin method updateListRule of AssignStockModelForIsdnDAO ...");

        try {
            HttpServletRequest httpServletRequest = getRequest();

            //lay danh sach cac mat hang thuoc ve 1 stockType
            String strFilterId = httpServletRequest.getParameter("filterId");
            String strTarget = httpServletRequest.getParameter("target").trim();
            if (strFilterId != null && !strFilterId.trim().equals("")) {
                String strQuery = "select rulesId, name from IsdnFilterRules where filterTypeId = ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, Long.valueOf(strFilterId));
                query.setParameter(1, Constant.STATUS_USE);
                List listStockModel = query.list();

                String[] header = {"", "--Chọn luật--"};
                List tmpList = new ArrayList();
                tmpList.add(header);
                tmpList.addAll(listStockModel);

                //them vao ket qua tra ve, cap nhat 2 vung
                hashMapResult.put(strTarget, tmpList);
            } else {
                String[] header = {"", "--Chọn luật-"};
                List tmpList = new ArrayList();
                tmpList.add(header);

                //them vao ket qua tra ve, cap nhat 2 vung, danh sach cac dlu va dau ma tinh
                hashMapResult.put(strTarget, tmpList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        //return
        pageForward = UPDATE_LIST_HASH_MAP_RESULT;
        log.info("end method updateListRule of AssignStockModelForIsdnDAO");
        return pageForward;
    }

    private boolean isNumber(String str) {
        try {
            Long value = Long.parseLong(str);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    /**
     *
     * author tamdt1
     * date: 21/06/2009
     * lay du lieu cho cac combobox
     *
     */
    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();

        //lay danh sach cac stockType
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List<StockType> listStockType = stockTypeDAO.findByPropertyAndStatus(
                StockTypeDAO.CHECK_EXP, Constant.STOCK_NON_EXP, Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_STOCK_TYPE, listStockType);

//        Long stockTypeId = this.assignStockModelForIsdnForm.getStockTypeId();
//        if (stockTypeId != null) {
//            //lay danh sach cac mat hang
//            StockModelDAO stockModelDAO = new StockModelDAO();
//            stockModelDAO.setSession(this.getSession());
//            List<StockModel> listStockModel = stockModelDAO.findByPropertyWithStatus(
//                    StockModelDAO.STOCK_TYPE_ID, stockTypeId, Constant.STATUS_USE);
//            req.setAttribute(REQUEST_LIST_STOCK_MODEL, listStockModel);
//
//            //lay danh sach cac tap luat
//            GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
//            groupFilterRuleDAO.setSession(this.getSession());
//            List<GroupFilterRule> listGroupFilterRule = groupFilterRuleDAO.findByPropertyAndStatus(
//                    GroupFilterRuleDAO.STOCK_TYPE_ID, stockTypeId, Constant.STATUS_USE);
//            req.setAttribute(REQUEST_LIST_GROUP_FILTER, listGroupFilterRule);
//
//            String groupFilterRuleCode = this.assignStockModelForIsdnForm.getGroupFilterRuleCode();
//            if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("")) {
//                //lay danh sach cac nhom luat thuoc tap luat nay
//                FilterTypeDAO filterTypeDAO = new FilterTypeDAO();
//                filterTypeDAO.setSession(this.getSession());
//                List<FilterType> listFilterType = filterTypeDAO.findByPropertyWithStatus(
//                        FilterTypeDAO.GROUP_FILTER_RULE_CODE, groupFilterRuleCode, Constant.STATUS_USE);
//                req.setAttribute(REQUEST_LIST_FILTER_TYPE, listFilterType);
//
//                Long filterTypeId = this.assignStockModelForIsdnForm.getFilterTypeId();
//                if (filterTypeId != null) {
//                    IsdnFilterRulesDAO isdnFilterRulesDAO = new IsdnFilterRulesDAO();
//                    isdnFilterRulesDAO.setSession(this.getSession());
//                    List<IsdnFilterRules> listIsdnFilterRules = isdnFilterRulesDAO.findByPropertyAndStatus(
//                            IsdnFilterRulesDAO.FILTER_TYPE_ID, filterTypeId, Constant.STATUS_USE);
//                    req.setAttribute(REQUEST_LIST_RULE, listIsdnFilterRules);
//                }
//
//            }
//        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/08/2010
     * purpose  : cap nhat mat hang cho so bang file
     *
     */
    private String assignStockModelForIsdnByFile() throws Exception {
        log.info("begin method assignStockModelForIsdnByFile of AssignStockModelForIsdnDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        boolean rejectLogErrorAssignStockModel = false;
        String strRejectLogErrorAssignStockModel = ResourceBundleUtils.getResource("rejectLogErrorAssignStockModel");
        strRejectLogErrorAssignStockModel = strRejectLogErrorAssignStockModel != null ? strRejectLogErrorAssignStockModel : "";

        if (AuthenticateDAO.checkAuthority(strRejectLogErrorAssignStockModel, req)) {
            //bo qua viec ghi log khi phan phoi so
            rejectLogErrorAssignStockModel = true;
        }

        try {
            Connection conn = null;
            PreparedStatement stmUpdate = null;

            conn = session.connection();
            
            Long stockTypeId = this.assignStockModelForIsdnForm.getStockTypeId();
            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            String userSessionId = req.getSession().getId();

            //tao cau lenh update
            StringBuffer strUpdateQuery = new StringBuffer();
            strUpdateQuery.append("update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName)+ " a ");
            strUpdateQuery.append("set  a.last_update_user = ?, "
                    + "                 a.last_update_ip_address = ?, "
                    + "                 a.last_update_time = sysdate, "
                    + "                 a.last_update_key = ?, "
                    + "                 a.stock_model_id = ?  ");
            strUpdateQuery.append("where 1 = 1 ");
            strUpdateQuery.append("and to_number(a.isdn) = ? ");
            
            //chi cho phep gan mat hang cho so moi va so ngung su dung
            strUpdateQuery.append("and (a.status = " + Constant.STATUS_ISDN_NEW + " or a.status = " + Constant.STATUS_ISDN_SUSPEND + " ) ");
            if(!rejectLogErrorAssignStockModel) {
                strUpdateQuery.append("and (a.stock_model_id = ? or a.stock_model_id is null) ");
            }
            strUpdateQuery.append("log errors reject limit unlimited"); //chuyen cac ban insert loi ra bang log

            stmUpdate = conn.prepareStatement(strUpdateQuery.toString());

            stmUpdate.setString(1, userToken.getLoginName()); //last_update_user
            stmUpdate.setString(2, req.getRemoteAddr()); //last_update_ip_address
            stmUpdate.setString(3, Constant.LAST_UPDATE_KEY); //last_update_key
//            stmUpdate.setLong(4, tmpNewStockModelId); //thiet lap truong newStockModelId
//            stmUpdate.setLong(5, Long.parseLong(tmpIsdn)); //thiet lap truong isdn
//            stmUpdate.setLong(6, tmpOldStockModelId); //thiet lap truong oldStockModelId

            //lay du lieu tu file
            List<AssignStockModelForIsdnForm> listError = new ArrayList<AssignStockModelForIsdnForm>();
            String serverFileName = CommonDAO.getSafeFileName(this.assignStockModelForIsdnForm.getServerFileName());
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
                pageForward = ASSIGN_STOCK_MODEL_FOR_ISDN;
                req.setAttribute(REQUEST_MESSAGE, "Error. File must be MS Excel 2003 version!");
                return pageForward;
            }
            
            numberRow = numberRow  - 1; //bo dong dau tien chua title

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            List<String> listMessage = AssignStockModelForIsdnDAO.listProgressMessage.get(userSessionId);
            String message = "";

            //lay cac message thong bao loi
//            BaseDAOAction baseDAOAction = new BaseDAOAction();
            HashMap<String, String> hashMapError = new HashMap<String, String>();
            hashMapError.put("ERR.ISN.121", getText("ERR.ISN.121")); //!!!Err. Cột isdn và mã mặt hàng mới không được để trống
            hashMapError.put("ERR.ISN.122", getText("ERR.ISN.122")); //!!!Err. Cột isdn không đúng định dạng số
            hashMapError.put("ERR.ISN.128", getText("ERR.ISN.128")); //!!!Err. Số isdn không tồn tại hoặc số isdn thuộc kho không được phép phân phối
            hashMapError.put("ERR.ISN.124", getText("ERR.ISN.124")); //!!!Err. Mã mặt hàng mới không tồn tại

            HashMap<String, String> hashMapMessage = new HashMap<String, String>();
            hashMapMessage.put("MSG.ISN.048", getText("MSG.ISN.048")); //đang xử lý từ dòng
            hashMapMessage.put("MSG.ISN.049", getText("MSG.ISN.049")); //đến
            hashMapMessage.put("MSG.ISN.050", getText("MSG.ISN.050")); //dòng trong file
            hashMapMessage.put("MSG.ISN.051", getText("MSG.ISN.051")); //Gán mặt hàng thành công

            HashMap<String, Long> hashMapStockModel = new HashMap<String, Long>();


            if (numberRow > 0) {
                int toRow = NUMBER_CMD_IN_BATCH;
                toRow = toRow < numberRow ? toRow : numberRow;
                message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " " + hashMapMessage.get("MSG.ISN.048") + " 1 " + hashMapMessage.get("MSG.ISN.049") + " " + toRow + "/ " + numberRow + " " + hashMapMessage.get("MSG.ISN.050");
                listMessage.add(message);
            }

            
            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                //doc tat ca cac dong trong sheet, cot dau tien la isdn, cot sau la ma mat hang moi can cap nhat
                String tmpStrIsdn = sheet.getCell(0, rowIndex).getContents();
                Long tmpIsdn = -1L;
                String tmpNewStockModelCode = sheet.getCell(1, rowIndex).getContents();
                if (tmpStrIsdn == null || tmpStrIsdn.trim().equals("") || tmpNewStockModelCode == null || tmpNewStockModelCode.trim().equals("")) {
                    AssignStockModelForIsdnForm errAssignModelForm = new AssignStockModelForIsdnForm();
                    errAssignModelForm.setFromIsdn(tmpStrIsdn);
                    errAssignModelForm.setNewStockModelCode(tmpNewStockModelCode);
                    errAssignModelForm.setErrorMessage(hashMapError.get("ERR.ISN.121")); //!!!Err. Cột isdn và mã kho mới không được để trống
                    listError.add(errAssignModelForm);
                    continue;
                } else {
                    tmpStrIsdn = tmpStrIsdn.trim();
                    tmpNewStockModelCode = tmpNewStockModelCode.trim();
                    try {
                        tmpIsdn = Long.parseLong(tmpStrIsdn);
                    } catch (NumberFormatException ex) {
                        //isdn khong dung dinh dang
                        AssignStockModelForIsdnForm errAssignModelForm = new AssignStockModelForIsdnForm();
                        errAssignModelForm.setFromIsdn(tmpStrIsdn);
                        errAssignModelForm.setNewStockModelCode(tmpNewStockModelCode);
                        errAssignModelForm.setErrorMessage(hashMapError.get("ERR.ISN.122")); //!!!Err. Cột isdn không đúng định dạng số
                        listError.add(errAssignModelForm);
                        continue;
                    }
                }

                Long tmpOldStockModelId = -1L;
                if (!rejectLogErrorAssignStockModel) {
                    tmpOldStockModelId = getIsdnStockModelId(strTableName, tmpIsdn);
                    if (tmpOldStockModelId == null) {
                        //so khong ton tai hoac so khong thuoc kho duoc phep phan phoi
                        AssignStockModelForIsdnForm errAssignModelForm = new AssignStockModelForIsdnForm();
                        errAssignModelForm.setFromIsdn(tmpStrIsdn);
                        errAssignModelForm.setNewStockModelCode(tmpNewStockModelCode);
                        errAssignModelForm.setErrorMessage(hashMapError.get("ERR.ISN.128")); //!!!Err. Số isdn không tồn tại hoặc số isdn thuộc kho không được phép phân phối
                        listError.add(errAssignModelForm);
                        continue;
                    }
                }

                Long tmpNewStockModelId = hashMapStockModel.get(tmpNewStockModelCode);
                if (tmpNewStockModelId == null) {
                    tmpNewStockModelId = getStockModelId(tmpNewStockModelCode);
                    if (tmpNewStockModelId != null) {
                        //tim thay ma shopCode moi -> day vao map de su dung sau nay
                        hashMapStockModel.put(tmpNewStockModelCode, tmpNewStockModelId);
                    } else {
                        //khong tim thay -> day vao -1 de khong p tim lai
                        hashMapStockModel.put(tmpNewStockModelCode, -1L);
                    }
                }

                if (tmpNewStockModelId == null || tmpNewStockModelId.equals(-1L)) {
                    //ma mat hang khong ton tai
                    AssignStockModelForIsdnForm errAssignModelForm = new AssignStockModelForIsdnForm();
                    errAssignModelForm.setFromIsdn(tmpStrIsdn);
                    errAssignModelForm.setNewStockModelCode(tmpNewStockModelCode);
                    errAssignModelForm.setErrorMessage(hashMapError.get("ERR.ISN.124")); //!!!Err. Mã mặt hàng mới không tồn tại
                    listError.add(errAssignModelForm);
                    continue;

                } else {
                    try {
                        //bat dau tu 4, vi 3 chi so dau da duoc thiet lap o tren khi tao cau lenh
                        stmUpdate.setLong(4, tmpNewStockModelId); //thiet lap truong newStockModelId
                        stmUpdate.setLong(5, tmpIsdn); //thiet lap truong isdn
                        if (!rejectLogErrorAssignStockModel) {
                            stmUpdate.setLong(6, tmpOldStockModelId); //
                        }
                        stmUpdate.addBatch();
//                        int resultUpdate = stmUpdate.executeUpdate();
//                        if (resultUpdate == 1) {
//                            //update thanh cong
//                            conn.commit();
//                        } else {
//                            //co loi xay ra
//                            conn.rollback();
//
//                            //isdn khong ton tai hoac ko duoc phep phan phoi
//                            DistributeIsdnForm errDistributeIsdnForm = new DistributeIsdnForm();
//                            errDistributeIsdnForm.setStartIsdn(tmpStrIsdn);
//                            errDistributeIsdnForm.setFromShopCode(tmpNewShopCode);
//                            errDistributeIsdnForm.setErrorMessage("!!!Err. Số isdn không tồn tại hoặc số isdn không được phép phân phối");
//                            listError.add(errDistributeIsdnForm);
//
////                            getSession().clear();
////                            getSession().getTransaction().rollback();
////                            getSession().beginTransaction();
//                        }

                    } catch (Exception ex) {
                        //
                        AssignStockModelForIsdnForm errAssignModelForm = new AssignStockModelForIsdnForm();
                        errAssignModelForm.setFromIsdn(tmpStrIsdn);
                        errAssignModelForm.setNewStockModelCode(tmpNewStockModelCode);
                        errAssignModelForm.setErrorMessage("!!!Ex. " + ex.getMessage()); //
                        listError.add(errAssignModelForm);
                        continue;
                    }
                }

                if(rowIndex % NUMBER_CMD_IN_BATCH == 0) {
                    //commit
                    conn.setAutoCommit(false);
                    //chay batch chen du lieu vao cac bang tuong ung
                    int[] updateCount = stmUpdate.executeBatch();
                    conn.commit();
//                    conn.setAutoCommit(true);tronglv comment 120604

                    int fromRow = rowIndex + 1;
                    int toRow = fromRow + NUMBER_CMD_IN_BATCH;
                    toRow = toRow < numberRow ? toRow : numberRow;
//                    message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " đang xử lý từ dòng " + (fromRow + 1) + " đến " + toRow + "/ " + numberRow + " dòng trong file";
                    message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " " + hashMapMessage.get("MSG.ISN.048") + " " + (fromRow + 1) + " " + hashMapMessage.get("MSG.ISN.049") + " " + toRow + "/ " + numberRow + " " + hashMapMessage.get("MSG.ISN.050");
                    listMessage.add(message);
                }
            }

            //chen not cac ban ghi con lai
            //commit
            conn.setAutoCommit(false);
            //chay batch chen du lieu vao cac bang tuong ung
            int[] updateCount = stmUpdate.executeBatch();
            conn.commit();
//            conn.setAutoCommit(true);tronglv comment 120604

            //ket xuat ket qua ra file excel trong truong hop co loi
            if (listError != null && listError.size() > 0) {
                req.setAttribute(REQUEST_MESSAGE, hashMapMessage.get("MSG.ISN.051") + " " + (numberRow - listError.size()) + "/ " + numberRow);
                try {
                    String DATE_FORMAT = "yyyyMMddHHmmss";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
                    filePath = filePath != null ? filePath : "/";
                    filePath += com.viettel.security.util.StringEscapeUtils.getSafeFileName("assignIsdnModelErr_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls");
                    String realPath = req.getSession().getServletContext().getRealPath(filePath);
                    String templatePath = ResourceBundleUtils.getResource("ASMFI_TMP_PATH_ERR");
                    if (templatePath == null || templatePath.trim().equals("")) {
                        //khong tim thay duong dan den file template de xuat ket qua
                        req.setAttribute(REQUEST_ERROR_FILE_PATH, "assignStockModelForIsdn.error.errTemplateNotExist");
                    }
                    String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
                    File fTemplateFile = new File(realTemplatePath);
                    if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                        //file ko ton tai
                        req.setAttribute(REQUEST_ERROR_FILE_PATH, "assignStockModelForIsdn.error.errTemplateNotExist");
                    }
                    Map beans = new HashMap();
                    beans.put("listError", listError);
                    XLSTransformer transformer = new XLSTransformer();
                    transformer.transformXLS(realTemplatePath, beans, realPath);
                    req.setAttribute(REQUEST_ERROR_FILE_PATH, filePath);

                } catch (Exception ex) {
                    req.setAttribute(REQUEST_ERROR_FILE_PATH, "!!!Ex. " + ex.toString());
                }

            } else {
                req.setAttribute(REQUEST_MESSAGE, hashMapMessage.get("MSG.ISN.051") + " " + numberRow + "/ " + numberRow);
            }

            //resetForm
            this.assignStockModelForIsdnForm.resetForm();

            //reset lai progress
            AssignStockModelForIsdnDAO.listProgressMessage.put(userSessionId, new ArrayList<String>());

            pageForward = ASSIGN_STOCK_MODEL_FOR_ISDN;
            log.info("end method assignStockModelForIsdnByFile of AssignStockModelForIsdnDAO");
            return pageForward;

        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("end method assignStockModelForIsdnByFile of AssignStockModelForIsdnDAO");
            throw ex;
        }
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
            List<String> listMessage = AssignStockModelForIsdnDAO.listProgressMessage.get(userSessionId);
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

    /**
     *
     * author   : tamdt1
     * date     : 18/08/2010
     * desc     : kiem tra 1 so co hop le khong
     *
     */
    private Long getIsdnStockModelId(String strTableName, Long strIsdn) {
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Session session = getSession();

            //
            List listParameter = new ArrayList();
            StringBuffer strQuery = new StringBuffer("");
            strQuery.append("SELECT stock_model_id ");
            strQuery.append("FROM " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName) + " a ");
            strQuery.append("WHERE 1 = 1 ");
            strQuery.append("      and to_number(a.isdn) = ? ");
            listParameter.add(strIsdn);

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
                Object tmpItem = list.get(0);
                if(tmpItem != null && !tmpItem.toString().equals("")) {
                    return Long.valueOf(tmpItem.toString());
                } else {
                    return -1L;
                }
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
     * date     : 18/08/2010
     * desc     :
     *
     */
    private Long getStockModelId(String stockModelCode) {
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Session session = getSession();

            List listParameter = new ArrayList();
            StringBuffer strQuery = new StringBuffer("");
            strQuery.append("SELECT stock_model_id a ");
            strQuery.append("FROM stock_model a ");
            strQuery.append("where 1 = 1 ");
            strQuery.append("and a.status = 1 ");

            strQuery.append("and lower(a.stock_model_code) = ? ");
            listParameter.add(stockModelCode.trim().toLowerCase());

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

}
