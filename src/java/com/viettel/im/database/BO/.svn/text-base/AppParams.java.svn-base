package com.viettel.im.database.BO;

/**
 * AppParams entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class AppParams implements java.io.Serializable {

    // Fields
    private AppParamsId id;
    private Long telecomServiceId;
    private String name;
    private String status;
    private String value;
    private String type;
    private String code;
    //ThanhNC add Su dung cho phan chia dai boc tham
    private Long numBlock;

    // Constructors
    /** default constructor */
    public AppParams() {
    }

    public AppParams(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /** minimal constructor */
    public AppParams(AppParamsId id) {
        this.id = id;
    }

    /** full constructor */
    public AppParams(AppParamsId id, Long telecomServiceId,
            String name, String status, String value) {
        this.id = id;
        this.telecomServiceId = telecomServiceId;
        this.name = name;
        this.status = status;
        this.value = value;
    }

    // Property accessors
    public AppParamsId getId() {
        return this.id;
    }

    public void setId(AppParamsId id) {
        this.id = id;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getNumBlock() {
        return numBlock;
    }

    public void setNumBlock(Long numBlock) {
        this.numBlock = numBlock;
    }
}
