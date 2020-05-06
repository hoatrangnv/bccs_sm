package com.viettel.im.client.form;

import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Discount;
import java.util.Date;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tamdt1
 * date 24/03/2009
 *
 */
public class DiscountForm extends ActionForm {

    private Long discountId;
    private Long fromAmount;
    private Long toAmount;
    private String type;
//    private Double discountRate;
//    private Double discountRateNumerator;
//    private Double discountRateDenominator;
    private String discountRateNumerator;
    private String discountRateDenominator;

    private Long discountAmount;

    public String getDiscountRateDenominator() {
        return discountRateDenominator;
    }

    public void setDiscountRateDenominator(String discountRateDenominator) {
        this.discountRateDenominator = discountRateDenominator;
    }

    public String getDiscountRateNumerator() {
        return discountRateNumerator;
    }

    public void setDiscountRateNumerator(String discountRateNumerator) {
        this.discountRateNumerator = discountRateNumerator;
    }
    private String startDatetime;
    private String endDatetime;
    private String areaCode;
    private Long channelTypeId;
    private Long discountGroupId;

    public DiscountForm() {
        resetForm();
    }

    public void copyDataFromBO(Discount discount) {
        this.setDiscountId(discount.getDiscountId());
        this.setFromAmount(discount.getFromAmount());
        this.setToAmount(discount.getToAmount());
        this.setType(discount.getType());
        this.setDiscountRateNumerator(String.valueOf(discount.getDiscountRateNumerator()));
        this.setDiscountRateDenominator(String.valueOf(discount.getDiscountRateDenominator()));
        this.setDiscountAmount(discount.getDiscountAmount());
        try {
            startDatetime = DateTimeUtils.convertDateToString(discount.getStartDatetime());
        } catch (Exception ex) {
            startDatetime = null;
        }
        try {
            endDatetime = DateTimeUtils.convertDateToString(discount.getEndDatetime());
        } catch (Exception ex) {
            endDatetime = null;
        }
        this.setAreaCode(discount.getAreaCode());
        this.setChannelTypeId(discount.getChannelTypeId());
        this.setDiscountGroupId(discount.getDiscountGroupId());
    }

    public void copyDataToBO(Discount discount) {
        discount.setDiscountId(this.getDiscountId());
        discount.setFromAmount(this.getFromAmount());
        discount.setToAmount(this.getToAmount());
        discount.setType(this.getType());
//        discount.setDiscountRate(this.getDiscountRate());
         Double discountRateNumerator = 0.0 ;

        Double discountRateDenominator = 0.0 ;
        
        if (this.getDiscountRateNumerator() != null){
        discount.setDiscountRateNumerator(Double.parseDouble(this.getDiscountRateNumerator()));
        } else {
        discount.setDiscountRateNumerator(0.0);
        }

        if (this.getDiscountRateDenominator() != null){
        discount.setDiscountRateDenominator(Double.parseDouble(this.getDiscountRateDenominator()));
        } else {
        discount.setDiscountRateDenominator(0.0);
        }
        
        discount.setDiscountAmount(this.getDiscountAmount());
        try {
            discount.setStartDatetime(DateTimeUtils.convertStringToDate(this.getStartDatetime()));
        } catch (Exception ex) {
            discount.setStartDatetime(null);
        }
        try {
            discount.setEndDatetime(DateTimeUtils.convertStringToDate(this.getEndDatetime()));
        } catch (Exception ex) {
            discount.setEndDatetime(null);
        }
        discount.setDiscountGroupId(this.getDiscountGroupId());
    }

    public void resetForm() {
        discountId = 0L;
        fromAmount = null;
        toAmount = null;
        type = String.valueOf(Constant.DISCOUNT_TYPE_RATE);
//        discountRate = null;
        discountRateNumerator = null;
        discountRateDenominator = null;
        discountAmount = null;
        try {
            startDatetime = DateTimeUtils.convertDateToString(new Date());
        } catch (Exception ex) {
            startDatetime = "";
        }
        endDatetime = "";
        areaCode = "";
        channelTypeId = 0L;
        discountGroupId = 0L;
    }

    public Long getDiscountGroupId() {
        return discountGroupId;
    }

    public void setDiscountGroupId(Long discountGroupId) {
        this.discountGroupId = discountGroupId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    /*
    public Double getDiscountRateDenominator() {
        return discountRateDenominator;
    }

    public void setDiscountRateDenominator(Double discountRateDenominator) {
        this.discountRateDenominator = discountRateDenominator;
    }

    public Double getDiscountRateNumerator() {
        return discountRateNumerator;
    }

    public void setDiscountRateNumerator(Double discountRateNumerator) {
        this.discountRateNumerator = discountRateNumerator;
    }
     */

//    public Double getDiscountRate() {
//        return discountRate;
//    }
//
//    public void setDiscountRate(Double discountRate) {
//        this.discountRate = discountRate;
//    }


    public String getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    public Long getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(Long fromAmount) {
        this.fromAmount = fromAmount;
    }

    public String getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Long getToAmount() {
        return toAmount;
    }

    public void setToAmount(Long toAmount) {
        this.toAmount = toAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
