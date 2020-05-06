package com.viettel.im.client.form;

/**
 *
 * @author Administrator
 */
public class SearchSaleChannelTypeForm {

    private String searchSaleChannelTypeName ;
    private String searchSaleChannelTypeGroup ;
    private Long searchSaleChannelTypeStatus ;
    private String message;

    public String getSearchSaleChannelTypeName() {
        return searchSaleChannelTypeName;
    }

    public void setSearchSaleChannelTypeName(String searchSaleChannelTypeName) {
        this.searchSaleChannelTypeName = searchSaleChannelTypeName;
    }

    public String getSearchSaleChannelTypeGroup() {
        return searchSaleChannelTypeGroup;
    }

    public void setSearchSaleChannelTypeGroup(String searchSaleChannelTypeGroup) {
        this.searchSaleChannelTypeGroup = searchSaleChannelTypeGroup;
    }

    public Long getSearchSaleChannelTypeStatus() {
        return searchSaleChannelTypeStatus;
    }

    public void setSearchSaleChannelTypeStatus(Long searchSaleChannelTypeStatus) {
        this.searchSaleChannelTypeStatus = searchSaleChannelTypeStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
