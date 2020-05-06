package com.viettel.im.database.BO;

import com.viettel.database.BO.BasicBO;
import java.util.Date;

/**
 * Users entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Users extends BasicBO{

	// Fields

	private long userId;
	private String loginName;
	private String password;
	private String fullName;
	private String jobTitle;
	private String phone;
	private String email;
	private Date dateOfBirth;
	private String nativePlace;
	private Date companyJoinDate;
	private String cardId;
	private String mobile;
	private long isEnable;
	private String provinceCode;
	private String identificationNum;
	private String note;
        private String shopCode;

	// Constructors

	/** default constructor */
	public Users() {
	}

	/** minimal constructor */
	public Users(long userId) {
		this.userId = userId;
	}

	/** full constructor */
	public Users(long userId, String loginName, String password,
			String fullName, String jobTitle, String phone, String email,
			Date dateOfBirth, String nativePlace, Date companyJoinDate,
			String cardId, String mobile, long isEnable, String provinceCode,
			String identificationNum, String note, String shopCode) {
		this.userId = userId;
		this.loginName = loginName;
		this.password = password;
		this.fullName = fullName;
		this.jobTitle = jobTitle;
		this.phone = phone;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.nativePlace = nativePlace;
		this.companyJoinDate = companyJoinDate;
		this.cardId = cardId;
		this.mobile = mobile;
		this.isEnable = isEnable;
		this.provinceCode = provinceCode;
		this.identificationNum = identificationNum;
		this.note = note;
                this.shopCode = shopCode;
	}

	// Property accessors

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getNativePlace() {
		return this.nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public Date getCompanyJoinDate() {
		return this.companyJoinDate;
	}

	public void setCompanyJoinDate(Date companyJoinDate) {
		this.companyJoinDate = companyJoinDate;
	}

	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public long getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(long isEnable) {
		this.isEnable = isEnable;
	}

	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getIdentificationNum() {
		return this.identificationNum;
	}

	public void setIdentificationNum(String identificationNum) {
		this.identificationNum = identificationNum;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

        public String getShopCode() {
            return shopCode;
        }

        public void setShopCode(String shopCode) {
            this.shopCode = shopCode;
        }
}