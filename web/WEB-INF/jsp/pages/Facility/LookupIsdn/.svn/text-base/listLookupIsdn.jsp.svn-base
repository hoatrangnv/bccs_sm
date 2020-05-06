<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listLookupIsdn
    Created on : Jun 22, 2009, 1:48:50 PM
    Author     : Doan Thanh 8
    Modified : NamNX Bo sung chuc nang xem thong tin cam ket
    Desc       : danh sach cac isdn
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
        request.setAttribute("listLookupIsdn", request.getSession().getAttribute("listLookupIsdn"));
%>

<fieldset class="imFieldset">
    <tags:imPanel title="MSG.search.result">
    <div style="width:100%; height:300px; overflow:auto;">
        <display:table name="listLookupIsdn" id="tblListLookupIsdn"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListLookupIsdn_rowNum)}</display:column>
            <display:column escapeXml="true" property="id.isdn" title="${fn:escapeXml(imDef:imGetText('MSG.isdn'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="stockIsdnCode" title="${fn:escapeXml(imDef:imGetText('MSG.service.type'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="isdnTypeName" title="${fn:escapeXml(imDef:imGetText('MSG.isdn_type'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="ownerCode" title="${fn:escapeXml(imDef:imGetText('MSG.generates.unit_code'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="statusName" title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="stockModelName" title="${fn:escapeXml(imDef:imGetText('MSG.generates.goods'))}" sortable="false" headerClass="tct"/>
            <display:column property="price" title="${fn:escapeXml(imDef:imGetText('MSG.price'))}" format="{0,number,#,###}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="ruleName" title="${fn:escapeXml(imDef:imGetText('MSG.isdn.beauti.rule'))}" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.commitment.view.info'))}" sortable="false" headerClass="tct" style="width:70px; text-align:center; ">
                <s:a href="" onclick="aOnclick('%{#attr.tblListLookupIsdn.stockModelId}')">
                    <img src="${contextPath}/share/img/accept.png"
                         title="${fn:escapeXml(imDef:imGetText('MSG.generates.commitment.view.info'))}"
                         alt="${fn:escapeXml(imDef:imGetText('MSG.generates.commitment.view.info'))}" />
                </s:a>
            </display:column>

        </display:table>
    </div>
    </tags:imPanel>
</fieldset>

<script>
    aOnclick = function(stockModelId) {
        openPopup('lookupIsdnAction!viewPledgeInfo.do?stockModelId=' + stockModelId,1050,550);
    }
</script>


