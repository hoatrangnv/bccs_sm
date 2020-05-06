<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listRetrieveInvoiceCoupon
    Created on : Sep 9, 2010, 8:26:05 AM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MSG.BIL.008'))}</legend>
        <display:table name="lstInvoiceCoupon" id="invoice"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" headerClass="tct" style="width:40px; text-align:center">
                ${fn:escapeXml(invoice_rowNum)}
            </display:column>
            <display:column property="serialNo" title="${fn:escapeXml(imDef:imGetText('MSG.bill.sign'))}" sortable="false" headerClass="sortable" style=" text-align:center" escapeXml="true"/>
            <display:column property="fromInvoice" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.253'))}" sortable="false" headerClass="sortable" style="width:150px; text-align:right" escapeXml="true"/>
            <display:column property="toInvoice" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.254'))}" sortable="false" headerClass="sortable" style="width:150px; text-align:right" escapeXml="true"/>
            <display:column escapeXml="true"  property="quantity" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.019'))}" sortable="false" headerClass="sortable" style="width:100px; text-align:right"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.094'))}" sortable="false" headerClass="sortable" style="width:200px; text-align:center">
                <s:if test="#attr.invoice.status == 1">
                    <s:text name="MSG.DET.040"/>
                </s:if>
                <s:elseif test="#attr.invoice.status == 0">
                    <s:text name="MSG.GOD.196"/>
                </s:elseif>
                <s:else>-</s:else>
            </display:column>

            <display:column title="<input id = 'checkAllID' type='checkbox' onclick=\"selectCbAll()\">"
                            sortable="false" headerClass="tct" style="width: 50px; text-align:center">
                <div align="center" style="vertical-align:middle; width:50px">
                    <s:checkbox name="form.invoiceCouponSelected" id="checkBoxID%{#attr.invoice.serialNo + ','+ #attr.invoice.fromInvoice + ',' + #attr.invoice.toInvoice}"
                                onclick="checkSelectCbAll();"
                                fieldValue="%{#attr.invoice.serialNo + ','+ #attr.invoice.fromInvoice + ',' + #attr.invoice.toInvoice}" theme="simple"/>
                </div>
            </display:column>

        </display:table>
</fieldset>

<div class="divHasBorder" style="padding:3px; margin-top:5px; text-align:center;">
    <s:if test="#request.lstInvoiceCoupon != null">
        <tags:submit targets="bodyContent" formId="form"
                     value="MSG.recover" cssStyle="width:80px;"
                     preAction="retrieveInvoiceCouponAction!retrieveInvoiceCoupon.do"
                     showLoadingText="true" validateFunction="validateRetrieve()"
                     confirm="true" confirmText="ERR.BIL.024"
                     />
    </s:if>
    <s:else>
        <tags:submit targets="bodyContent" formId="form" disabled="true"
                     value="MSG.recover" cssStyle="width:80px;"
                     preAction="retrieveInvoiceCouponAction!retrieveInvoiceCoupon.do"
                     />
    </s:else>
</div>

<script type="text/javascript">
    selectCbAll = function(){        
        var checkAll = document.getElementById("checkAllID");
        var rowId = document.getElementsByName("form.invoiceCouponSelected");        
        var checkBoxID = "checkBoxID";        
        var i = 0;        
        if(checkAll.checked){
            for(i = 0; i < rowId.length; i++){
                document.getElementById(checkBoxID+rowId[i].value).checked=true;
            }
        }else{
            for(i = 0; i < rowId.length; i++){
                document.getElementById(checkBoxID+rowId[i].value).checked=false;
            }
        }

    }

    checkSelectCbAll = function(){
        checkSelectAll("checkAllID", "form.invoiceCouponSelected", "checkBoxID");
    }

    selectAll = function(checkAllID, cbName, cbId) {
        var checkAll = document.getElementById(checkAllID);
        var rowId = document.getElementsByName(cbName);
        var checkBoxID = cbId;
        var i = 0;

        if(checkAll.checked){
            for(i = 0; i < rowId.length; i++){
                document.getElementById(checkBoxID+rowId[i].value).checked=true;
            }
        }else{
            for(i = 0; i < rowId.length; i++){
                document.getElementById(checkBoxID+rowId[i].value).checked=false;
            }
        }
    }

    checkSelectAll = function(checkAllID, cbName, cbId) {
        var checkAll = document.getElementById(checkAllID);
        var rowId = document.getElementsByName(cbName);
        var checkBoxID = cbId;
        var checkedAll = true;
        for(var i = 0; i < rowId.length; i++){
            if(document.getElementById(checkBoxID+rowId[i].value).checked != true){
                checkedAll = false;
            }
        }
        if(checkedAll == true){
            checkAll.checked = true;
        }else{
            checkAll.checked = false;
        }
    }


</script>
