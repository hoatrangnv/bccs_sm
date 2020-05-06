<%-- 
    Document   : mngChannelGroupMain
    Created on : Dec 13, 2013, 1:52:14 PM
    Author     : thinhph2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<tags:imPanel title="title.mapping.debit.day.type">
    <div id="mngChannelGroupDIV">
        <jsp:include page="MapDebitDayType.jsp"/> 
    </div>
    <div id="listChannelGroupDIV">
        <jsp:include page="listMapDebitDayResult.jsp"/> 
    </div>
</tags:imPanel>
<script type="text/javascript">

    checkAllShop = function() {
        if (jQuery("#checkAll").attr("checked")) {
            jQuery("input[id='inputForm.shopCode']").val("");
            jQuery("input[id='inputForm.shopName']").val("");
            jQuery("input[id='inputForm.shopCode']").attr("disabled", true);
            jQuery("input[id='inputForm.shopName']").attr("disabled", true);
        } else {
            jQuery("input[id='inputForm.shopCode']").attr("disabled", false);
            jQuery("input[id='inputForm.shopName']").attr("disabled", false);
        }
    }

    validate = function() {
        if (!jQuery("#checkAll").attr('checked')) {
            if (jQuery("input[id='inputForm\\.shopCode']").val() == "") {
                jQuery("#displayResultMsg").html('<s:property value="getText('err.not_enter_unit_code')"/>');
                jQuery("input[id='inputForm\\.shopCode']").focus();
                return false;
            }
            if (jQuery("input[id='inputForm\\.shopName']").val() == "") {
                jQuery("#displayResultMsg").html('<s:property value="getText('err.chua_chon_ten_shop')"/>');
                jQuery("input[id='inputForm\\.shopName']").focus();
                return false;
            }
        }

        if (jQuery("#debitDayTypeId").val() == "") {
            jQuery("#displayResultMsg").html('<s:property value="getText('err.chua_chon_dot_km')"/>');
            jQuery("#debitDayTypeId").focus();
            return false;
        }

        if (jQuery("#status").val() == "") {
            jQuery("#displayResultMsg").html('<s:property value="getText('ERR.INF.105')"/>');
            jQuery("#status").focus();
            return false;
        }

        return true;
    }

    preUpdate = function(debitDayTypeShopId) {
        gotoAction('mngChannelGroupDIV', 'mappingDebitDayWithShopAction!preUpdate.do?debitDayTypeShopId=' + debitDayTypeShopId);
    }

    cancel = function() {

    }

</script>