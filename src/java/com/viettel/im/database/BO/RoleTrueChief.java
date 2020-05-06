package com.viettel.im.database.BO;

/**
 * AccountActionLogs entity.
 *
 * @author MyEclipse Persistence Tools
 */
public class RoleTrueChief implements java.io.Serializable {

    // Fields
    private long id;
    private String userName;
    private String isdn;
    private String showroom;
    private String name;
    private String note;
    private String general;
    private String plus;
    private String description;

    public RoleTrueChief() {
    }

    public RoleTrueChief(long id, String userName, String isdn, String showroom, String name, String note, String general, String plus, String description) {
        this.id = id;
        this.userName = userName;
        this.isdn = isdn;
        this.showroom = showroom;
        this.name = name;
        this.note = note;
        this.general = general;
        this.plus = plus;
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getShowroom() {
        return showroom;
    }

    public void setShowroom(String showroom) {
        this.showroom = showroom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public String getPlus() {
        return plus;
    }

    public void setPlus(String plus) {
        this.plus = plus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}