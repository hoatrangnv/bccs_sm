package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import org.apache.log4j.Logger;
import com.viettel.im.client.form.StockTypeProfileForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.ProfileDetail;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.StockTypeProfile;
import com.viettel.im.database.BO.TableColumnFull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author tamdt1
 * xu ly cac tac vu lien quan den profile cua loai mat hang
 *
 */
public class GoodsProfileDAO extends BaseDAOAction {

    private static final Logger log = Logger.getLogger(GoodsProfileDAO.class);
    private String pageForward;

    //dinh nghia cac hang so pageForward
    private static final String GOODS_PROFILE_OVERVIEW = "goodsProfileOverview";
    private static final String SEARCH_PROFILE = "searchProfile";
    private static final String GOODS_PROFILE = "goodsProfile";
    private static final String LIST_PROFILES = "listProfiles";

    //dinh nghia cac ten bien session, request
    private final String SESSION_CURR_PROFILE_ID = "currentProfileId";
    private final String REQUEST_LIST_PROFILES = "listProfiles";
    private final String REQUEST_LIST_STOCK_TYPES = "listStockTypes";
    private final String REQUEST_LIST_STOCK_MODELS = "listStockModels";
    private final String REQUEST_LIST_PROFILE_SEPERATORS = "listProfileSeperators";
    private final String REQUEST_LIST_SELECTED_FIELDS = "listSelectedFields";
    private final String REQUEST_LIST_AVAILABLE_FIELDS = "listAvailableFields";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_IS_ADD_MODE = "isAddMode"; //flag thiet lap che do them thong tin moi
    private final String REQUEST_IS_EDIT_MODE = "isEditMode"; //flag thiet lap che do sua thong tin da co
    private final String REQUEST_IS_VIEW_MODE = "isViewMode"; //flag thiet lap che do xem thong tin

    //khai bao cac bien form
    private List listItems = new ArrayList();
    private StockTypeProfileForm profileForm = new StockTypeProfileForm();

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }

    public StockTypeProfileForm getProfileForm() {
        return profileForm;
    }

    public void setProfileForm(StockTypeProfileForm profileForm) {
        this.profileForm = profileForm;
    }

    /**
     *
     * date: 16/04/2009
     * man hinh dau tien, hien thi tree va danh sach cac stockType
     * modified tamdt1, 17/04/2009
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of GoodsProfileDAO ...");

        HttpServletRequest req = getRequest();

        //xoa tat ca cac bien session
        removeAllSessionAttribute();

        //lay danh sach cac profile
        List<StockTypeProfile> listStockTypeProfile = getListProfiles(null);
        req.setAttribute(REQUEST_LIST_PROFILES, listStockTypeProfile);

        log.info("End method preparePage of GoodsProfileDAO");
        pageForward = GOODS_PROFILE_OVERVIEW;
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 05/10/2009
     * xoa tat ca cac bien session
     *
     */
    private void removeAllSessionAttribute() {
        HttpServletRequest req = getRequest();

        //xoa tat ca cac bien session
        req.getSession().removeAttribute(SESSION_CURR_PROFILE_ID);
    }

    /**
     *
     * author   : tamdt1
     * date     : 23/11/2009
     * purpose  : hien thi danh sach tat cac cac profile
     *
     */
    public String displayAllProfile() throws Exception {
        log.info("Begin method displayAllProfile of GoodsProfileDAO...");

        HttpServletRequest req = getRequest();

        //lay danh sach cac profile chuyen len bien request
        List<StockTypeProfile> listStockTypeProfile = getListProfiles(null);
        req.setAttribute(REQUEST_LIST_PROFILES, listStockTypeProfile);

        //reset form
        this.profileForm.resetForm();

        log.info("End method displayAllProfile of GoodsProfileDAO");
        pageForward = SEARCH_PROFILE;
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 16/04/2009
     * hien thi danh sach cac profile cua 1 stockType
     *
     */
    public String displayAllProfileInStockType() throws Exception {
        log.info("Begin method displayAllProfileInStockType of GoodsProfileDAO ...");

        HttpServletRequest req = getRequest();

        //lay stockTypeId can hien thi danh sach profile
        String strSelectedStockTypeId = req.getParameter("selectedStockTypeId");
        Long stockTypeId = 0L;
        if (strSelectedStockTypeId != null) {
            try {
                stockTypeId = new Long(strSelectedStockTypeId);
            } catch (NumberFormatException ex) {
                stockTypeId = -1L;
            }
        }

        //lay danh sach cac profile chuyen len bien request
        List<StockTypeProfile> listStockTypeProfile = getListProfiles(stockTypeId);
        req.setAttribute(REQUEST_LIST_PROFILES, listStockTypeProfile);

        //reset form
        this.profileForm.resetForm();

        //thiet lap stock_type
        this.profileForm.setStockTypeId(stockTypeId);

        log.info("End method displayAllProfileInStockType of GoodsProfileDAO");
        pageForward = SEARCH_PROFILE;
        return pageForward;
    }
   
    /**
     *
     * author tamdt1
     * date: 02/04/2009
     * hien thi thòng tin chi tiet ve 1 profile
     *
     */
    public String displayProfile() throws Exception {
        log.info("Begin method displayProfile of GoodsProfileDAO ...");

        HttpServletRequest req = getRequest();

        //lay profileId
        Long profileId;
        String strSelectedProfileId = req.getParameter("selectedProfileId");
        if (strSelectedProfileId != null) {
            profileId = new Long(strSelectedProfileId);
        } else {
            profileId = (Long) req.getSession().getAttribute(SESSION_CURR_PROFILE_ID);
        }

        req.getSession().setAttribute(SESSION_CURR_PROFILE_ID, profileId);

        //chuyen du lieu len form
        StockTypeProfile stockTypeProfile = getStockTypeProfileById(profileId);
        if (stockTypeProfile != null) {
            profileForm.copyDataFromBO(stockTypeProfile);
            
            //lay du lieu cho cac combobox
            getDataForComboBox();

            //xac lap che do hien thi thong tin
            req.setAttribute(REQUEST_IS_VIEW_MODE, true);
        }

        pageForward = GOODS_PROFILE;
        log.info("End method displayProfile of GoodsProfileDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 14/12/2009
     * thiet lap du lieu can thiet cho cac combobox doi voi profile
     *
     */
    private void getDataForComboBox() throws Exception {
        try {
            //thiet lap cac bien can thiet cho combobox
            HttpServletRequest req = getRequest();

            //danh sach cac loai mat hang
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            List<StockType> listStockTypes = stockTypeDAO.findByProperty(StockTypeDAO.STATUS, Constant.STATUS_USE, StockTypeDAO.NAME);
            req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockTypes);

            //danh sach cac dau phan cach
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            List<AppParams> listProfileSeperators = appParamsDAO.findAppParamsByType("PROFILE_SEPARATOR");
            req.setAttribute(REQUEST_LIST_PROFILE_SEPERATORS, listProfileSeperators);

            //chi tiet profile
            List<ProfileDetail> listProfileDetails = new ArrayList<ProfileDetail>();
            Long profileId = this.profileForm.getProfileId();
            if (profileId != null && profileId.compareTo(0L) > 0) {
                //lay danh sach cac cot da co trong profile nay, dua len bien request
                listProfileDetails = getListProfileDetails(profileId);
                req.setAttribute(REQUEST_LIST_SELECTED_FIELDS, listProfileDetails);
            }

            //lay danh sach tat ca cac truong cua bang co stockType = stockTypeId, dua len bien request
            Long stockTypeId = this.profileForm.getStockTypeId();
            StockType stockType = getStockTypeById(stockTypeId);
            if (stockType != null) {
                String strQuery = "select new com.viettel.im.database.BO.TableColumnFull(id.columnName) "
                        + "from TableColumnFull where id.tableName = ? order by id.columnName";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, stockType.getTableName());
                List<TableColumnFull> listAvailableFields = query.list();
                
                //tim tat cac cac cot da co trong profile detail doi voi 1 stockType
                List<TableColumnFull> listExistColumnIndex = new ArrayList<TableColumnFull>();
                for (int i = 0; i < listAvailableFields.size(); i++) {
                    TableColumnFull tableColumnFull = listAvailableFields.get(i);
                    String availableColumnName = tableColumnFull.getColumnName();
                    for (int j = 0; j < listProfileDetails.size(); j++) {
                        ProfileDetail tmpProfileDetail = listProfileDetails.get(j);
                        if (availableColumnName.equals(tmpProfileDetail.getColumnName())) {
                            listExistColumnIndex.add(tableColumnFull);
                            break;
                        }
                    }
                }
                //loai bo tat ca cac cot da co khoi list available
                for (int i = 0; i < listExistColumnIndex.size(); i++) {
                    listAvailableFields.remove(listExistColumnIndex.get(i));
                }

                //
                req.setAttribute(REQUEST_LIST_AVAILABLE_FIELDS, listAvailableFields);
            }

            //lay danh sach tat ca cac stockModel co the ap dung profile nay
            List<StockModel> listStockModel = getListStockModels(stockTypeId, profileId);
            req.setAttribute(REQUEST_LIST_STOCK_MODELS, listStockModel);


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     *
     * author tamdt1
     * date: 06/04/2009
     * chuan bi form them profile moi
     *
     */
    public String prepareAddProfile() throws Exception {
        log.info("Begin method prepareAddProfile of GoodsProfileDAO ...");

        HttpServletRequest req = getRequest();

        //giu lai stock_type
        Long stockTypeId = this.profileForm.getStockTypeId();
        this.profileForm.resetForm();
        this.profileForm.setStockTypeId(stockTypeId);

        //lay du lieu cho cac combobox
        getDataForComboBox();

        //xac lap che do chuan bi them profile moi
        req.setAttribute(REQUEST_IS_ADD_MODE, true);
        //

        pageForward = GOODS_PROFILE;
        log.info("End method prepareAddProfile of GoodsProfileDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 06/04/2009
     * chuan bi form sua thong tin profile
     *
     */
    public String prepareEditProfile() throws Exception {
        log.info("Begin method prepareEditProfile of GoodsProfileDAO ...");

        HttpServletRequest req = getRequest();

        Long profileId = profileForm.getProfileId();
        StockTypeProfile stockTypeProfile = getStockTypeProfileById(profileId);
        if (stockTypeProfile != null) {
            this.profileForm.copyDataFromBO(stockTypeProfile);

            //lay du lieu cho cac combobox
            getDataForComboBox();

            //xac lap che do chuan bi sua thong tin profile
            req.setAttribute(REQUEST_IS_EDIT_MODE, true);
        }

        pageForward = GOODS_PROFILE;
        log.info("End method prepareEditProfile of GoodsProfileDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 06/04/2009
     * them moi hoac sua thogn tin profile
     *
     */
    public String addOrEditProfile() throws Exception {
        log.info("Begin method addOrEditProfile of GoodsProfileDAO ...");

        HttpServletRequest req = getRequest();
        Session session = this.getSession();

        Long profileId = profileForm.getProfileId();
        StockTypeProfile stockTypeProfile = getStockTypeProfileById(profileId);
        if (stockTypeProfile != null) {
            /* LamNV ADD START */
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            /* LamNV STOP START */
            //----------------------------------------------------------------
            //truong hop edit thong tin profile da co
            if (!checkValidProfileToEdit()) {
                //thiet lap danh sach du lieu cho cac combobox
                getDataForComboBox();
                //xac lap che do chuan bi sua thong tin profile moi
                req.setAttribute(REQUEST_IS_EDIT_MODE, true);
                //return
                pageForward = GOODS_PROFILE;
                log.info("End method addOrEditProfile of GoodsProfileDAO");
                return pageForward;
            }
            /* LamNV ADD START */
            StockTypeProfile stockTypeProfileOld = new StockTypeProfile();
            BeanUtils.copyProperties(stockTypeProfileOld, stockTypeProfile);
            /* LamNV ADD STOP */            

            //luu thong tin stockTypeProfile vao bang
            profileForm.copyDataToBO(stockTypeProfile);
            session.update(stockTypeProfile);
            
            /* LamNV ADD START */
            lstLogObj.add(new ActionLogsObj(stockTypeProfileOld, stockTypeProfile, stockTypeProfile.getProfileId()));
            /* LamNV ADD STOP */


            List<ProfileDetail> lstProfileDetail = findProfileDetailByProperty(session, "profileId", profileId);
            if (!lstProfileDetail.isEmpty()) {
                for (ProfileDetail profileDetail : lstProfileDetail) {
                    Long oldStatus = profileDetail.getStatus();
                    profileDetail.setStatus(Constant.STATUS_DELETE);
                    session.save(profileDetail);
                    lstLogObj.add(new ActionLogsObj("PROFILE_DETAIL", "STATUS", oldStatus, Constant.STATUS_DELETE, profileDetail.getProfileDetailId()));
                }
            }

            //luu danh sach cac truong tuong ung
            //dua tat ca cac ban ghi ve status = delete
//            String strQuery = "update ProfileDetail set status = ? where profileId = ?";
//            Query query = session.createQuery(strQuery);
//            query.setParameter(0, Constant.STATUS_DELETE);
//            query.setParameter(1, profileId);
//            query.executeUpdate();
//            /* LamNV ADD START */
//            lstLogObj.add(new ActionLogsObj("PROFILE_DETAIL", "STATUS", "", String.valueOf(Constant.STATUS_DELETE)));
//            /* LamNV ADD STOP */



            //cap nhat thong tin moi
            String[] arrSelectedField = profileForm.getArrSelectedField();
            if (arrSelectedField != null) {
                for (int i = 0; i < arrSelectedField.length; i++) {
                    ProfileDetail profileDetail = new ProfileDetail();
                    Long profileDetailId = getSequence("PROFILE_DETAIL_SEQ");
                    profileDetail.setProfileDetailId(profileDetailId);
                    profileDetail.setColumnName(arrSelectedField[i]);
                    profileDetail.setColumnOrder(i * 1L);
                    profileDetail.setProfileId(profileId);
                    profileDetail.setStatus(Constant.STATUS_USE);

                    session.save(profileDetail);
                    /* LamNV ADD START */
                    lstLogObj.add(new ActionLogsObj(null, profileDetail, profileDetail.getProfileDetailId()));
                    /* LamNV ADD STOP */
                }
            }



            //cap nhat cac thong tin moi
            String[] arrStockModel = profileForm.getArrStockModel();

            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(session);

            List<StockModel> lstStockModel = stockModelDAO.findByProperty("profileId", profileId);
            if (!lstStockModel.isEmpty()) {
                for (StockModel stockModel : lstStockModel) {
                    boolean checkExist = false;
                    if (arrStockModel != null) {
                        for (int i = 0; i < arrStockModel.length; i++) {
                            if ((Long.valueOf(arrStockModel[i])).compareTo(stockModel.getStockModelId()) == 0) {
                                continue;
                            }
                            checkExist = true;
                        }
                    } else {
                        checkExist = true;
                    }
                    if (checkExist) {
                        Long oldProfileId = stockModel.getProfileId();
                        stockModel.setProfileId(null);
                        session.save(stockModel);
                        lstLogObj.add(new ActionLogsObj("STOCK_MODEL", "PROFILE_ID", oldProfileId, null, stockModel.getStockModelId()));
                    }
                }
            }

            //luu thong tin profile vao bang stockmodel
            //xoa cac thong tin cu
//            strQuery = "update StockModel set profileId = null where profileId = ? and status = ?";
//            query = session.createQuery(strQuery);
//            query.setParameter(0, profileId);
//            query.setParameter(1, Constant.STATUS_USE);
//            query.executeUpdate();
//            /* LamNV ADD START */
//            lstLogObj.add(new ActionLogsObj("STOCK_MODEL", "PROFILE_ID", "", null));
//            /* LamNV ADD STOP */

            if (arrStockModel != null) {
                for (int i = 0; i < arrStockModel.length; i++) {
                    Long stockModelId = -1L;
                    try {
                        stockModelId = Long.parseLong(arrStockModel[i]);
                    } catch (NumberFormatException ex) {
                        stockModelId = -1L;
                    }
                    if (stockModelId > 0) {
                        StockModel stockModel = stockModelDAO.findById(stockModelId);
                        if (stockModel != null) {
                            Long oldProfileId = stockModel.getProfileId();
                            if (oldProfileId != null && profileId != null && oldProfileId.compareTo(profileId) == 0) {
                                continue;
                            }
                            stockModel.setProfileId(profileId);
                            session.save(stockModel);
                            lstLogObj.add(new ActionLogsObj("STOCK_MODEL", "PROFILE_ID", oldProfileId, profileId, stockModel.getStockModelId()));
                        }

//                        strQuery = "update StockModel set profileId = ? where stockModelId = ? and status = ?";
//                        query = session.createQuery(strQuery);
//                        query.setParameter(0, profileId);
//                        query.setParameter(1, Long.parseLong(arrStockModel[i]));
//                        query.setParameter(2, Constant.STATUS_USE);
//                        query.executeUpdate();
//                        /* LamNV ADD START */
//                        lstLogObj.add(new ActionLogsObj("STOCK_MODEL", "PROFILE_ID", "", String.valueOf(profileId)));
//                        /* LamNV ADD STOP */
                    }
                }
            }

            /* LamNV ADD START */
            saveLog(Constant.ACTION_UPDATE_GOOD_PROFILE, "Cập nhật Profile mặt hàng", lstLogObj, profileId, Constant.DEFINE_PROFILE_OF_GOODS, Constant.EDIT);
            /* LamNV ADD STOP */
            req.setAttribute(REQUEST_MESSAGE, "goodsprofile.editsuccessful");

        } else {
            /* LamNV ADD START */
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            /* LamNV STOP START */
            
            //----------------------------------------------------------------
            //truong hop them moi profile
            if (!checkValidProfileToAdd()) {
                //thiet lap danh sach du lieu cho cac combobox
                getDataForComboBox();
                //xac lap che do chuan bi them profile moi
                req.setAttribute(REQUEST_IS_ADD_MODE, true);
                //return
                pageForward = GOODS_PROFILE;
                log.info("End method addOrEditProfile of GoodsProfileDAO");
                return pageForward;
            }

            //luu thong tin stockTypeProfile vao bang
            stockTypeProfile = new StockTypeProfile();
            profileForm.copyDataToBO(stockTypeProfile);
            profileId = getSequence("PROFILE_SEQ");
            stockTypeProfile.setProfileId(profileId);
            stockTypeProfile.setStatus(Constant.STATUS_USE);
            session.save(stockTypeProfile);
            /* LamNV ADD START */
            lstLogObj.add(new ActionLogsObj(null, stockTypeProfile));
            /* LamNV ADD STOP */

            //luu danh sach cac truong tuong ung
            String[] arrSelectedField = profileForm.getArrSelectedField();
            if (arrSelectedField != null) {
                for (int i = 0; i < arrSelectedField.length; i++) {
                    ProfileDetail profileDetail = new ProfileDetail();
                    Long profileDetailId = getSequence("PROFILE_DETAIL_SEQ");
                    profileDetail.setProfileDetailId(profileDetailId);
                    profileDetail.setColumnName(arrSelectedField[i]);
                    profileDetail.setColumnOrder(i * 1L);
                    profileDetail.setProfileId(profileId);
                    profileDetail.setStatus(Constant.STATUS_USE);

                    session.save(profileDetail);
                    /* LamNV ADD START */
                    lstLogObj.add(new ActionLogsObj(null, profileDetail));
                    /* LamNV ADD STOP */
                }
            }

            //luu thong tin profile vao bang stockmodel
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(session);

            String[] arrStockModel = profileForm.getArrStockModel();
            if (arrStockModel != null) {
                for (int i = 0; i < arrStockModel.length; i++) {

                    StockModel stockModel = stockModelDAO.findById(Long.parseLong(arrStockModel[i]));
                    if (stockModel != null) {
                        Long oldProfileId = stockModel.getProfileId();
                        stockModel.setProfileId(profileId);
                        session.save(stockModel);
                        lstLogObj.add(new ActionLogsObj("STOCK_MODEL", "PROFILE_ID", oldProfileId, profileId));
                    }

//                    String strQuery = "update StockModel set profileId = ? where stockModelId = ? and status = ?";
//                    Query query = session.createQuery(strQuery);
//                    query.setParameter(0, profileId);
//                    query.setParameter(1, Long.parseLong(arrStockModel[i]));
//                    query.setParameter(2, Constant.STATUS_USE);
//                    query.executeUpdate();

                    /* LamNV ADD START */
//                    lstLogObj.add(new ActionLogsObj("STOCK_MODEL", "PROFILE_ID", "", String.valueOf(profileId)));                    
                    /* LamNV ADD STOP */

                }
            }

            req.setAttribute(REQUEST_MESSAGE, "goodsprofile.addsuccessful");
            /* LamNV ADD START */
            saveLog(Constant.ACTION_ADD_GOOD_PROFILE, "Thêm mới Profile mặt hàng", lstLogObj, profileId, Constant.DEFINE_PROFILE_OF_GOODS, Constant.ADD);
            /* LamNV ADD STOP */
            //
            this.profileForm.setProfileId(profileId);
            req.getSession().setAttribute(SESSION_CURR_PROFILE_ID, profileId);
        }

        //xac lap che do hien thi thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        //lay du lieu cho combobox
        getDataForComboBox();
        
        //return
        pageForward = GOODS_PROFILE;
        log.info("End method addOrEditProfile of GoodsProfileDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 07/04/2009
     * xoa thong tin ve profile
     *
     */
    public String delProfile() throws Exception {
        log.info("Begin method delProfile of GoodsDAO ...");
        /* LamNV ADD START */
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        /* LamNV ADD STOP */
        HttpServletRequest req = getRequest();
        Session session = this.getSession();

        Long profileId = profileForm.getProfileId();
        StockTypeProfile stockTypeProfile = getStockTypeProfileById(profileId);
        Long oldStockTypeProfileStatus = stockTypeProfile.getStatus();
        stockTypeProfile.setStatus(Constant.STATUS_DELETE);
        session.save(stockTypeProfile);
        lstLogObj.add(new ActionLogsObj("STOCK_TYPE_PROFILE", "STATUS", oldStockTypeProfileStatus, Constant.STATUS_DELETE, stockTypeProfile.getProfileId()));

        List<ProfileDetail> lstProfileDetail = findProfileDetailByProperty(session, "profileId", profileId);
        if (!lstProfileDetail.isEmpty()) {
            for (ProfileDetail profileDetail : lstProfileDetail) {
                Long oldStatus = profileDetail.getStatus();
                profileDetail.setStatus(Constant.STATUS_DELETE);
                session.save(profileDetail);
                lstLogObj.add(new ActionLogsObj("PROFILE_DETAIL", "STATUS", oldStatus, Constant.STATUS_DELETE, profileDetail.getProfileDetailId()));
            }
        }
       
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(session);        
        List<StockModel> lstStockModel = stockModelDAO.findByProperty("profileId", profileId);
        if (!lstStockModel.isEmpty()) {
            for (StockModel stockModel : lstStockModel) {
                Long oldProfileId = stockModel.getProfileId();
                stockModel.setProfileId(null);
                session.save(stockModel);
                lstLogObj.add(new ActionLogsObj("STOCK_MODEL", "PROFILE_ID", oldProfileId, null, stockModel.getStockModelId()));
            }
        }


//        String strQuery = "update StockTypeProfile set status = ? where profileId = ?";
//        Query query = getSession().createQuery(strQuery);
//        query.setParameter(0, Constant.STATUS_DELETE);
//        query.setParameter(1, profileId);
//        query.executeUpdate();
//        /* LamNV ADD START */
//        lstLogObj.add(new ActionLogsObj("STOCK_TYPE_PROFILE", "STATUS", "?", String.valueOf(Constant.STATUS_DELETE)));
//        /* LamNV ADD STOP */

        //

//        strQuery = "update ProfileDetail set status = ? where profileId = ?";
//        query = getSession().createQuery(strQuery);
//        query.setParameter(0, Constant.STATUS_DELETE);
//        query.setParameter(1, profileId);
//        query.executeUpdate();
//        /* LamNV ADD START */
//        lstLogObj.add(new ActionLogsObj("PROFILE_DETAIL", "STATUS", "?", String.valueOf(Constant.STATUS_DELETE)));
//        /* LamNV ADD STOP */


//        //xoa cac thong tin cu
//        strQuery = "update StockModel set profileId = null where profileId = ? and status = ?";
//        query = getSession().createQuery(strQuery);
//        query.setParameter(0, profileId);
//        query.setParameter(1, Constant.STATUS_USE);
//        query.executeUpdate();
//        /* LamNV ADD START */
//        lstLogObj.add(new ActionLogsObj("STOCK_MODEL", "PROFILE_ID", "?", ""));
//        lstLogObj.add(new ActionLogsObj("STOCK_MODEL", "STATUS", "?", String.valueOf(Constant.STATUS_DELETE)));
//        /* LamNV ADD STOP */

        req.getSession().removeAttribute(SESSION_CURR_PROFILE_ID);

        Long stockTypeId = this.profileForm.getStockTypeId();
        this.profileForm.resetForm();
        this.profileForm.setStockTypeId(stockTypeId);

        /* LamNV ADD START */
        saveLog(Constant.ACTION_DEL_GOOD_PROFILE, "Xóa Profile mặt hàng", lstLogObj, profileId, Constant.DEFINE_PROFILE_OF_GOODS, Constant.DELETE);
        /* LamNV ADD STOP */
        //
        req.setAttribute(REQUEST_MESSAGE, "goodsprofile.delsuccessful");

        pageForward = GOODS_PROFILE;
        log.info("End method delProfile of GoodsDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 02/04/2009
     * lay danh sach tat ca cac stockTypeProfile cua 1 stockType
     *
     */
    private List<StockTypeProfile> getListProfiles(Long stockTypeId) {
        List<StockTypeProfile> listStockTypeProfiles = new ArrayList<StockTypeProfile>();
        List listParam = new ArrayList();
        StringBuffer strQuery = new StringBuffer("");
        strQuery.append("from StockTypeProfile a ");
        strQuery.append("where 1 = 1 ");
        
        strQuery.append("and a.status = ? ");
        listParam.add(Constant.STATUS_USE);
        
        if(stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery.append("and a.stockTypeId = ? ");
            listParam.add(stockTypeId);
        }
        
        strQuery.append("order by nlssort(a.profileName,'nls_sort=Vietnamese') asc");

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParam.size(); i++) {
            query.setParameter(i, listParam.get(i));
        }

        listStockTypeProfiles = query.list();
        if (listStockTypeProfiles != null && listStockTypeProfiles.size() > 0) {
            for (int i = 0; i < listStockTypeProfiles.size(); i++) {
                StockTypeProfile tmpStockTypeProfile = listStockTypeProfiles.get(i);
                List<ProfileDetail> listProfileDetails = getListProfileDetails(tmpStockTypeProfile.getProfileId());
                StringBuffer tmpStringBuffer = new StringBuffer();
                for (int j = 0; j < listProfileDetails.size(); j++) {
                    tmpStringBuffer.append(tmpStockTypeProfile.getSeparatedChar());
                    tmpStringBuffer.append(" ");
                    tmpStringBuffer.append(listProfileDetails.get(j).getColumnName());
                }
                if (tmpStringBuffer.length() > 0) {
                    //loai bo ky tu phan cach dau tien
                    tmpStringBuffer.delete(0, tmpStockTypeProfile.getSeparatedChar().length());
                }
                tmpStockTypeProfile.setLinePattern(tmpStringBuffer.toString());
            }
        }

        return listStockTypeProfiles;
    }

    /**
     *
     * author tamdt1
     * date: 15/03/2009
     * lay StockType co id = stockTypeId
     *
     */
    private StockType getStockTypeById(Long stockTypeId) {
        StockType stockType = null;
        if (stockTypeId == null) {
            stockTypeId = -1L;
        }
        String strQuery = "from StockType where stockTypeId = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockTypeId);
        query.setParameter(1, Constant.STATUS_USE);
        List listStockTypes = query.list();
        if (listStockTypes != null && listStockTypes.size() > 0) {
            stockType = (StockType) listStockTypes.get(0);
        }

        return stockType;
    }

    /**
     *
     * author tamdt1
     * date: 02/04/2009
     * lay StockTypeProfile co id = profileId
     *
     */
    private StockTypeProfile getStockTypeProfileById(Long profileId) {
        StockTypeProfile stockTypeProfile = null;

        if (profileId == null) {
            profileId = -1L;
        }

        String strQuery = "from StockTypeProfile where profileId = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, profileId);
        query.setParameter(1, Constant.STATUS_USE);
        List<StockTypeProfile> listStockTypeProfiles = query.list();
        if (listStockTypeProfiles != null && listStockTypeProfiles.size() > 0) {
            stockTypeProfile = listStockTypeProfiles.get(0);
        }

        return stockTypeProfile;
    }

    /**
     *
     * author tamdt1
     * date: 03/04/2009
     * lay danh sach tat ca cac profileDetail cua 1 profile
     *
     */
    private List<ProfileDetail> getListProfileDetails(Long profileId) {
        List<ProfileDetail> listProfileDetails = new ArrayList<ProfileDetail>();
        if (profileId == null) {
            profileId = -1L;
        }
        String strQuery = "from ProfileDetail where status = ? and profileId = ? order by columnOrder";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        query.setParameter(1, profileId);

        listProfileDetails = query.list();

        return listProfileDetails;
    }

    /**
     *
     * author tamdt1
     * date: 06/04/2009
     * tra du lieu cho cay stockType
     *
     */
    public String getGoodsProfileTree() throws Exception {
        try {
            this.listItems = new ArrayList();
            Session hbnSession = getSession();

            HttpServletRequest httpServletRequest = getRequest();

            String node = QueryCryptUtils.getParameter(httpServletRequest, "nodeId");
            if (node.indexOf("0_") == 0) {
                //neu la lan lay cay du lieu muc 0, tra ve danh sach cac stockType
                List listStockTypes = hbnSession.createCriteria(StockType.class).
                        add(Restrictions.eq("status", 1L)).
                        addOrder(Order.asc("name")).
                        list();
                Iterator iterStockType = listStockTypes.iterator();
                while (iterStockType.hasNext()) {
                    StockType stockType = (StockType) iterStockType.next();
                    String nodeId = "1_" + stockType.getStockTypeId().toString(); //them prefix 1_ de xac dinh la node level
                    getListItems().add(new Node(stockType.getName(), "true", nodeId, httpServletRequest.getContextPath() + "/profileAction!displayAllProfileInStockType.do?selectedStockTypeId=" + stockType.getStockTypeId().toString()));
                }
            } else if (node.indexOf("1_") == 0) {
                //neu la lan lay cay du lieu muc 1, tra ve danh sach cac profile tuong ung voi stockType
                node = node.substring(2); //bo phan prefix danh dau level cua cay (1_)
                List listProfiles = hbnSession.createCriteria(StockTypeProfile.class).
                        add(Restrictions.eq("stockTypeId", Long.parseLong(node))).
                        add(Restrictions.eq("status", 1L)).
                        addOrder(Order.asc("profileName")).
                        list();

                Iterator iterProfile = listProfiles.iterator();
                while (iterProfile.hasNext()) {
                    StockTypeProfile stockTypeProfile = (StockTypeProfile) iterProfile.next();
                    //ung voi moi saleServicesModel, tim stockType tuong ung
                    String nodeId = "2_" + node + "_" + stockTypeProfile.getProfileId().toString(); //them prefix 2_ de xac dinh node level
                    getListItems().add(new Node(stockTypeProfile.getProfileName(),
                            "false",
                            nodeId,
                            httpServletRequest.getContextPath() + "/profileAction!displayProfile.do?selectedProfileId=" + stockTypeProfile.getProfileId().toString()));
                }
            }

            return "getGoodsProfileTree";

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     *
     * author tamdt1
     * date: 06/04/2009
     * lay danh sach tat ca cac stockType
     *
     */
    private List<StockType> getListStockType() {
        List<StockType> listStockTypes = new ArrayList<StockType>();
        String strQuery = "from StockType where status = ? order by name";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);

        listStockTypes = query.list();

        return listStockTypes;
    }

    /**
     *
     * author tamdt1
     * date: 13/04/2009
     * lay danh sach tat ca cac avaiable stockModel
     *
     */
    private List<StockModel> getListStockModels(Long stockTypeId) {
        if (stockTypeId == null) {
            stockTypeId = -1L;
        }
        List<StockModel> listStockModels = new ArrayList<StockModel>();
//        String strQuery = "from StockModel where stockTypeId = ? and profileId is null and status = ? order by name";
//        Query query = getSession().createQuery(strQuery);
//        query.setParameter(0, stockTypeId);
//        query.setParameter(1, Constant.STATUS_USE);

        String strQuery = "select new StockModel(a.stockModelId,a.stockModelCode,a.stockTypeId,a.name,a.telecomServiceId"
                + ",a.checkSerial,a.checkDeposit,a.checkDial,a.unit,a.status,a.notes,a.discountGroupId,b.name)"
                + " from StockModel a,AppParams b where a.status = ? and a.stockTypeId = ? and a.profileId is null "
                + " and a.unit = b.code and b.type = ? order by a.name";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        query.setParameter(1, stockTypeId);
        query.setParameter(2, "STOCK_MODEL_UNIT");

        listStockModels = query.list();

        return listStockModels;
    }

    /**
     *
     * author tamdt1
     * date: 13/04/2009
     * lay danh sach tat ca cac avaiable stockModel doi voi 1 profile
     *
     */
    private List<StockModel> getListStockModels(Long stockTypeId, Long profileId) {
        if (stockTypeId == null || profileId == null) {
            stockTypeId = -1L;
            profileId = -1L;
        }
        List<StockModel> listStockModels = new ArrayList<StockModel>();
        List listParam = new ArrayList();
        StringBuffer strQuery = new StringBuffer("");
        strQuery.append("select new com.viettel.im.database.BO.StockModel("
                + "a.stockModelId, a.stockModelCode, a.name, a.stockTypeId, b.name, "
                + "a.telecomServiceId, c.telServiceName, a.profileId) ");
        strQuery.append("from StockModel a, StockType b, TelecomService c ");
        strQuery.append("where 1 = 1 ");
        strQuery.append("and a.stockTypeId = b.stockTypeId ");
        strQuery.append("and a.telecomServiceId = c.telecomServiceId ");

        strQuery.append("and a.status = ? ");
        listParam.add(Constant.STATUS_USE);

        strQuery.append("and a.stockTypeId = ? ");
        listParam.add(stockTypeId);
        
        strQuery.append("and (a.profileId is null or a.profileId = ?) ");
        listParam.add(profileId);

        strQuery.append("order by a.stockModelCode, a.name ");

        Query query = getSession().createQuery(strQuery.toString());
        for(int i = 0; i < listParam.size(); i++) {
            query.setParameter(i, listParam.get(i));
        }
        listStockModels = query.list();

        return listStockModels;
    }

    /**
     *
     * author tamdt1
     * date: 26/04/2009
     * ham kiem tra thong tin profile truoc khi update
     *
     */
    private boolean checkValidProfileToAdd() {
        HttpServletRequest req = getRequest();

        if (this.profileForm.getProfileName() == null || this.profileForm.getProfileName().trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Tên profile không được để trống");
            return false;
        }
        if (this.profileForm.getStartLine() == null || this.profileForm.getStartLine() <= 0) {
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Dòng bắt đầu DL phải là số dương");
            return false;
        }
        if (this.profileForm.getArrSelectedField() == null || this.profileForm.getArrSelectedField().length <= 0) {
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Profile phải chứa ít nhất 1 trường DL");
            return false;
        }

        String strQuery = "from StockTypeProfile where profileName = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, this.profileForm.getProfileName());
        query.setParameter(1, Constant.STATUS_USE);
        List<StockTypeProfile> listStockTypeProfiles = query.list();
        if (listStockTypeProfiles != null && listStockTypeProfiles.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Tên profile bị trùng");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 26/04/2009
     * ham kiem tra thong tin profile truoc khi update
     *
     */
    private boolean checkValidProfileToEdit() {
        HttpServletRequest req = getRequest();

        if (this.profileForm.getProfileName() == null || this.profileForm.getProfileName().trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Tên profile không được để trống");
            return false;
        }
        if (this.profileForm.getStartLine() == null || this.profileForm.getStartLine() <= 0) {
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Dòng bắt đầu DL phải là số dương");
            return false;
        }
        if (this.profileForm.getArrSelectedField() == null || this.profileForm.getArrSelectedField().length <= 0) {
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Profile phải chứa ít nhất 1 trường DL");
            return false;
        }

        String strQuery = "from StockTypeProfile where profileName = ? and status = ? and profileId <> ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, this.profileForm.getProfileName());
        query.setParameter(1, Constant.STATUS_USE);
        query.setParameter(2, this.profileForm.getProfileId());
        List<StockTypeProfile> listStockTypeProfiles = query.list();
        if (listStockTypeProfiles != null && listStockTypeProfiles.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Tên profile bị trùng");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 02/05/2009
     * tim kiem danh sach cac profile thoa man cac dieu kien
     *
     */
    public String searchProfile() throws Exception {
        log.info("Begin method listProfiles of GoodsProfileDAO ...");

        HttpServletRequest req = getRequest();

        //lay danh sach cac mat hang dua vao dieu kien tim kiem
        List listParam = new ArrayList();
        StringBuffer strQuery = new StringBuffer("from StockModel where 1 = 1 ");

        Long stockTypeId = this.profileForm.getStockTypeId();
        String stockModelCode = this.profileForm.getStockModelCode();
        String stockModelName = this.profileForm.getStockModelName();

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery.append("and stockTypeId = ? ");
            listParam.add(stockTypeId);
        }

        if (stockModelCode != null && !stockModelCode.trim().equals("")) {
            strQuery.append("and lower(stockModelCode) like ? ");
            listParam.add("%" + stockModelCode.trim().toLowerCase() + "%");
        }

        if (stockModelName != null && !stockModelName.trim().equals("")) {
            strQuery.append("and lower(name) like ? ");
            listParam.add("%" + stockModelName.trim().toLowerCase() + "%");
        }

        strQuery.append("and status = ? ");
        listParam.add(Constant.STATUS_USE);

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParam.size(); i++) {
            query.setParameter(i, listParam.get(i));
        }

        //lay danh sach cac profile ma cac mat hang nay thuoc ve
        List<StockModel> listStockModels = query.list();
        if (listStockModels != null && listStockModels.size() > 0) {
            StringBuffer listProfileId = new StringBuffer("");
            for (int i = 0; i < listStockModels.size(); i++) {
                if (listStockModels.get(i).getProfileId() != null) {
                    listProfileId.append(", ");
                    listProfileId.append(listStockModels.get(i).getProfileId());

                }
            }
            if (listProfileId.length() > 0) {
                listProfileId.delete(0, 1);
                String strQueryProfile = "from StockTypeProfile where profileId in ("
                        + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(listProfileId.toString()) + ") and status = ? "
                        + "order by nlssort(profileName,'nls_sort=Vietnamese') asc";
                Query queryProfile = getSession().createQuery(strQueryProfile);
                queryProfile.setParameter(0, Constant.STATUS_USE);
                List<StockTypeProfile> listProfiles = queryProfile.list();
                if (listProfiles != null && listProfiles.size() > 0) {
                    for (int i = 0; i < listProfiles.size(); i++) {
                        StockTypeProfile tmpStockTypeProfile = listProfiles.get(i);
                        List<ProfileDetail> listProfileDetails = getListProfileDetails(tmpStockTypeProfile.getProfileId());
                        StringBuffer tmpStringBuffer = new StringBuffer();
                        for (int j = 0; j < listProfileDetails.size(); j++) {
                            tmpStringBuffer.append(tmpStockTypeProfile.getSeparatedChar());
                            tmpStringBuffer.append(" ");
                            tmpStringBuffer.append(listProfileDetails.get(j).getColumnName());
                        }
                        if (tmpStringBuffer.length() > 0) {
                            //loai bo ky tu phan cach dau tien
                            tmpStringBuffer.delete(0, tmpStockTypeProfile.getSeparatedChar().length());
                        }
                        tmpStockTypeProfile.setLinePattern(tmpStringBuffer.toString());
                    }
                }

                req.setAttribute(REQUEST_LIST_PROFILES, listProfiles);
            }
        }

        pageForward = LIST_PROFILES;
        log.info("End method listProfiles of GoodsProfileDAO");
        return pageForward;
    }

    public List findProfileDetailByProperty(Session session, String propertyName, Object value) {
        log.debug("finding ProfileDetail instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ProfileDetail as model where model." + propertyName + "= ? and  model.status != ?";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, value);
            queryObject.setParameter(1, Constant.STATUS_DELETE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
}
