package com.viettel.im.database.BO;

import java.util.Date;

/**
 * AccountBalance entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class AccountBalance implements java.io.Serializable {

    // Fields
    private Long balanceId;
    private Long accountId;
    private Long balanceType;
    private Long status;
    private Date startDate;
    private Date endDate;
    private String userCreated;
    private Date dateCreated;
    private Double realBalance;
    private Double realDebit;
    private Double realCredit;
    private Double currentDebit;
    private Double limitDebit;

    // Constructors
    /** default constructor */
    public AccountBalance() {
    }

    public Double getRealCredit() {
        if (realCredit != null) {
            return realCredit;
        } else {
            return 0.0;
        }
    }

    public void setRealCredit(Double realCredit) {
        if (realCredit != null) {
            this.realCredit = realCredit;
        } else {
            this.realCredit = 0.0;
        }
    }

    public Double getCurrentDebit() {
        if (currentDebit != null) {
            return currentDebit;
        } else {
            return 0.0;
        }
    }

    public void setCurrentDebit(Double currentDebit) {
        if (currentDebit != null) {
            this.currentDebit = currentDebit;
        } else {
            this.currentDebit = 0.0;
        }
    }

    public Double getLimitDebit() {
        if (limitDebit != null) {
            return limitDebit;
        } else {
            return 0.0;
        }
    }

    public void setLimitDebit(Double limitDebit) {
        if (limitDebit != null) {
            this.limitDebit = limitDebit;
        } else {
            this.limitDebit = 0.0;
        }

    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Long getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(Long balanceType) {
        this.balanceType = balanceType;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getRealBalance() {
        if (realDebit != null) {
            return realBalance;
        } else {
            return 0.0;
        }
    }

    public void setRealBalance(Double realBalance) {
        if (realBalance != null) {
            this.realBalance = realBalance;
        } else {
            this.realBalance = 0.0;
        }
    }

    public Double getRealDebit() {
        if (realDebit != null) {
            return realDebit;
        } else {
            return 0.0;
        }
    }

    public void setRealDebit(Double realDebit) {
        if (realDebit != null) {
            this.realDebit = realDebit;
        } else {
            this.realDebit = 0.0;
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }
}