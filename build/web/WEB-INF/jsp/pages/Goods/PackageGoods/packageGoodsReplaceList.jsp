<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : packageGoodsReplaceList
    Created on : Nov 7, 2011, 9:23:34 AM
    Author     : haint
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<tags:displayResult id="returnMsgClient2" property="returnMsg2" propertyValue="returnMsgValue2" type="key"/>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('L.100036'))}</legend>
    <display:table id="tblPackageGoodsReplaceList" name="packageGoodsReplaceList" class="dataTable"
                   targets="divPackageGoodsDetailV2List" pagesize="100"
                   requestURI="packageGoodsV2Action!pageNavigator.do"
                   cellpadding="1" cellspacing="1">
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('L.100031'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblPackageGoodsReplaceList_rowNum)}</display:column>
        <display:column escapeXml="true" property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MES.package.goods.001'))}" sortable="false" headerClass="tct" style="width:70px; text-align:center" />
        <display:column escapeXml="true" property="stockModelName" title="${fn:escapeXml(imDef:imGetText('MES.package.goods.002'))}" sortable="false" headerClass="tct" style="text-align:left" />
        <display:column title="${fn:escapeXml(imDef:imGetText('MES.package.goods.013'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
            <s:a href="" onclick="deletePackageGoodsReplace('%{#attr.tblPackageGoodsReplaceList.packageGoodsReplaceId}')" cssClass="cursorHand">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="L.100029" />" alt="<s:text name="L.100029" />" />
            </s:a>
        </display:column>                
        <display:footer> <tr> <td colspan="14" style="color:green"><s:text name="L.100032" />: <s:property escapeJavaScript="true"  value="%{#request.packageGoodsDetailList.size()}" /></td> <tr> </display:footer>
   </display:table>
</fieldset>

