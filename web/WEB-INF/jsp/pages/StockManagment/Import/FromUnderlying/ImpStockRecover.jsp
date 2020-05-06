<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ImpStockRecover
    Created on : Sep 17, 2010, 3:17:24 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<s:if test="importStockForm.cmdImportCode != null && importStockForm.cmdImportCode != '' ">

    <table style="width:100%">
        <tr>
            <td style="vertical-align:top;">
                <tags:ImportStock subForm="importStockForm" type="note" disabled="true" readOnly="true" viewOnly="true" height="210px"/>
            </td>
            <td style="vertical-align:top;">

                <jsp:include page="../../Common/ListGoodsReject.jsp"/>

            </td>
        </tr>
    </table>
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- Trang thai da xuat hang -->
    <s:if test="importStockForm.status == 2 ||importStockForm.canPrint==1 ">
        <s:if test="importStockForm.canPrint!=null && importStockForm.canPrint==1">
            <%--<input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.STK.046'))}" style="width:100px;" disabled>--%>
            <%--<div id="showMessageDestroy" style="color: red;font-size: 12pt" align="center" >
                ${fn:escapeXml(imDef:imGetText('MSG.STK.043'))}
            </div>--%>

        </s:if>
        <s:else>
            <tags:submit confirm="true" confirmText="MSG.STK.044"
                         formId="importStockForm"
                         preAction="ImportStockUnderlyingAction!rejectImpStockRecover.do"
                         showLoadingText="true"
                         value="MSG.STK.045" targets="searchArea"/>
        </s:else>

    </s:if>
</s:if>

