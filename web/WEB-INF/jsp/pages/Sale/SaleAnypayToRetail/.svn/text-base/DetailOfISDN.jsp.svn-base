<%-- 
    Document   : DetailOfISDN.jsp
    Created on : Jun 28, 2012, 3:07:01 PM
    Author     : thaiph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>


<table class="inputTbl4Col">
    <tr>
        <td><tags:label key="MSG.cusName" required="false" /> </td>
        <s:textfield maxlength="50" cssClass="txtInputFull" id="saleRetailAnypayForm.cusName" name="saleRetailAnypayForm.cusName" readonly="true"/></td>
        <td class="text">
        </td>
    </tr>
</table>
<table class="inputTbl2Col">
    <tr>
        <td>
            <div style="width:100%;">
                <display:table name="listAccount" id="tbllistAccount"
                               class="dataTable" cellpadding="1" cellspacing="1">
                    <display:column escapeXml="true"  property="accountName" title="MSG.account.type" style="text-align:center" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true"  property="moneyTotal" title="MSG.SIK.079" style="text-align:center" sortable="false" headerClass="tct"/>
                </display:table>
            </div>
        </td>
    </tr>
    <tr>
        <td colspan="4" align="center">
            <tags:displayResult id="displayResultMsgClient" property="displayResultMsgClient" propertyValue="returnMsgValue" type="key"/>
        </td>                
    </tr>
</table>
<table class="inputTbl2Col">
    <tr>
        <td  class="label"><tags:label key="MSG.SIK.134" required="true" /></td>

        <td class="text"><s:textfield maxlength="20" onkeypress='return isNumberKey(event)' cssClass="txtInputFull" 
                     id="saleRetailAnypayForm.amountManual" name="saleRetailAnypayForm.amountManual" /></td>
    </tr>

    <tr>
        <td colspan="4" align="center">
            <tags:submit formId="saleRetailAnypayForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         validateFunction = "checkValidFieldsToCreatTrans()"
                         value="MSG.createTrans"
                         preAction="saleRetailAnypayAction!createTrans.do"/>
        </td>
    </tr>
</table>


<script language="javascript">
    
    checkValidFieldsToCreatTrans = function() {
        alert('hi');
        var amountManual= document.getElementById("saleRetailAnypayForm.amountManual");
        if(trim(amountManual) == "" || !isInteger(trim(amountManual))) {
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError(' bankReceiptExternal.error.invalidAmount ')"/>';
            amountManual.focus();
            return false;
        }
        return true;
    }


        

</script>
