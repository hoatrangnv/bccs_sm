<%--
    Document   : ImpStock.jsp
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
    Description: Lap phieu nhap kho tu cap tren
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<tags:displayResult id="resultCreateExp" property="resultCreateExp" propertyValue="resultCreateExpParams" type="key"/>
<tags:displayResultList property="lstError" type="key"/>

<s:if test="importStockForm.cmdImportCode != null && importStockForm.cmdImportCode != '' ">
    <table style="width:100%">
        <tr>
            <td style="vertical-align:top;">
                <tags:ImportStock subForm="importStockForm" type="note" disabled="true" readOnly="true" viewOnly="true"  height="210px"/>
            </td>
            <td style="vertical-align:top;">
                <jsp:include page="../../Common/ListGoods.jsp"/>
            </td>
        </tr>
    </table>
    <!-- Trang thai da lap phieu nhap -->
    <s:hidden name="importStockForm.status"/>
    <s:if test = "importStockForm.status == 2">
        <tags:submit confirm="true" confirmText="MSG.confirm.import.store" formId="importStockForm" showLoadingText="true" targets="bodyContent"
                     value="MSG.importToStore" preAction="ImportStockFromSenior!impStock.do"/>
    </s:if>
   
</s:if>


