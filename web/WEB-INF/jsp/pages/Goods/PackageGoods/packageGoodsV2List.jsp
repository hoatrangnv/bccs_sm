<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : packageGoodsV2List
    Created on : Aug 8, 2011, 4:44:53 PM
    Author     : kdvt_tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('L.100030'))}</legend>
    <display:table id="tblPackageGoodsList" name="packageGoodsList" class="dataTable"
                   targets="divPackageGoodsList" pagesize="100"
                   requestURI="packageGoodsV2Action!pageNavigator.do"
                   cellpadding="1" cellspacing="1">
        <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('L.100031'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblPackageGoodsList_rowNum)}</display:column>
        <display:column escapeXml="true" property="code" title=" ${fn:escapeXml(imDef:imGetText('L.100017'))}" sortable="false" headerClass="tct"  style="width:50px; text-align:center"/>
        <display:column escapeXml="true" property="name" title=" ${fn:escapeXml(imDef:imGetText('L.100018'))}" sortable="false" headerClass="tct"  style="width:200px; text-align:left"/>
        <display:column title=" ${fn:escapeXml(imDef:imGetText('L.100019'))}" sortable="false" headerClass="tct"  style="width:75px; text-align:left">
            <s:if test = "#attr.tblPackageGoodsList.status*1 == 1">
                <!--                Hieu luc-->
                <s:text name="L.100021"/>
            </s:if>
            <s:else>
                <!--                    Khong hieu luc-->
                <s:text name="L.100020"/>
            </s:else>
        </display:column>
        <display:column escapeXml="true" property="note" title=" ${fn:escapeXml(imDef:imGetText('L.100026'))}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" property="createdBy" title=" ${fn:escapeXml(imDef:imGetText('Created_by'))}" sortable="false" headerClass="tct" style="width:75px; text-align:left"/>
        <display:column property="createdDate" title=" ${fn:escapeXml(imDef:imGetText('Created_date'))}" sortable="false" headerClass="tct" style="width:75px; text-align:left" format="{0,date,dd/MM/yyyy}" />
        <display:column escapeXml="true" property="lastUpdatedBy" title=" ${fn:escapeXml(imDef:imGetText('Last_updated_by'))}" sortable="false" headerClass="tct" style="width:75px; text-align:left"/>
        <display:column property="lastUpdatedDate" title=" ${fn:escapeXml(imDef:imGetText('Last_updated_date'))}" sortable="false" headerClass="tct" style="width:75px; text-align:left" format="{0,date,dd/MM/yyyy}" />
        <display:column title="${fn:escapeXml(imDef:imGetText('L.100027'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
            <s:a href="" onclick="prepareEditPackageGoods('%{#attr.tblPackageGoodsList.packageGoodsId}')" cssClass="cursorHand">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="L.100027" />" alt="<s:text name="L.100027" />" />
            </s:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('L.100029'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
            <s:a href="" onclick="deletePackageGoods('%{#attr.tblPackageGoodsList.packageGoodsId}')" cssClass="cursorHand">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="L.100029" />" alt="<s:text name="L.100029" />" />
            </s:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('L.100030'))}" sortable="false" headerClass="tct" style="width:75px;text-align:center">
            <s:a href="" onclick="searchPackageGoodsDetail('%{#attr.tblPackageGoodsList.packageGoodsId}')" cssClass="cursorHand">
                <img src="${contextPath}/share/img/accept.png" title="<s:text name="L.100036" />" alt="<s:text name="L.100030" />" />
            </s:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('L.100039'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
            <s:a href="" onclick="prepareAddNewPackageGoodsDetail('%{#attr.tblPackageGoodsList.packageGoodsId}')" cssClass="cursorHand">
                <img src="${contextPath}/share/img/add_icon.png" title="<s:text name="L.100039" />" alt="<s:text name="L.100039" />" />
            </s:a>
        </display:column>
        <display:footer> <tr> <td colspan="13" style="color:green"><s:text name="L.100032" />: <s:property escapeJavaScript="true"  value="%{#request.packageGoodsList.size()}" /></td> <tr> </display:footer>
        </display:table>
</fieldset>

<div style="width:100%;" id="divPackageGoodsDetailV2List">    
    <jsp:include page="packageGoodsDetailV2List.jsp"/>
</div>

<script language="javascript">
            
    prepareEditPackageGoods = function (packageGoodsId){
        $('returnMsgClient').innerHTML= '';
        openDialog("${contextPath}/packageGoodsV2Action!prepareEditPackageGoods.do?packageGoodsId="+packageGoodsId + "&" + token.getTokenParamString(), 800, 600,true);
    }
        
        
    prepareAddNewPackageGoodsDetail = function (packageGoodsId){
        $('returnMsgClient').innerHTML= '';
        openDialog("${contextPath}/packageGoodsV2Action!prepareAddNewPackageGoodsDetail.do?packageGoodsId="+packageGoodsId + "&" + token.getTokenParamString(), 800, 600,true);
    }
    
    deletePackageGoods = function (packageGoodsId){
        $('returnMsgClient').innerHTML= '';
        var strConfirm = strConfirm = getUnicodeMsg('<s:text name="C.100003"/>');
        if (!confirm(strConfirm)) {
            return;
        }        
        gotoAction("bodyContent","${contextPath}/packageGoodsV2Action!deletePackageGoods.do?packageGoodsId="+packageGoodsId + "&" + token.getTokenParamString());
    }
    
    searchPackageGoodsDetail = function (packageGoodsId){
        $('returnMsgClient').innerHTML= '';
        gotoAction("divPackageGoodsDetailV2List","${contextPath}/packageGoodsV2Action!searchPackageGoodsDetail.do?packageGoodsId="+packageGoodsId + "&" + token.getTokenParamString());
    }
    
    refreshPackageGoodsList = function() {
        //alert("abc");
        //gotoAction("divPackageGoodsList",'${contextPath}/packageGoodsV2Action!refreshPackageGoodsList.do');
    }
    
</script>

