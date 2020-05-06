<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listLookupSerialHistory
    Created on : Jan 20, 2010, 8:52:30 AM
    Author     : Doan Thanh 8
    Desc       : danh sach lich su tao serial
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

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MSG.search.result'))}</legend>

    <div style="width:100%; height:350px; overflow:auto;">
        <display:table name="listLookupSerialHistoryBean" id="tblListLookupSerialHistoryBean"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListLookupSerialHistoryBean_rowNum)}</display:column>
            <display:column escapeXml="true" property="serial" title="${fn:escapeXml(imDef:imGetText('MSG.serial.number'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MSG.stockModelId'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="stockModelName" title="${fn:escapeXml(imDef:imGetText('MSG.stockModelName'))}" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.GOD.state'))}" sortable="false" headerClass="tct">
                <s:if test="#attr.tblListLookupSerialBean.stateId == 1">
                    <s:property escapeJavaScript="true"  value="getError('MSG.GOD.new')"/>
                </s:if>
                <s:elseif test="#attr.tblListLookupSerialBean.stateId == 2">
                    <s:property escapeJavaScript="true"  value="getError('MSG.GOD.old')"/>
                </s:elseif>
                <s:elseif test="#attr.tblListLookupSerialBean.stateId == 3">
                    <s:property escapeJavaScript="true"  value="getError('MSG.GOD.error')"/>
                </s:elseif>
            </display:column>
            <display:column title="Loại kho" sortable="false" headerClass="tct">
                <s:if test="#attr.tblListLookupSerialBean.ownerType == 1">
                    <s:property escapeJavaScript="true"  value="getError('MSG.FAC.DepartShop')"/>
                </s:if>
                <s:elseif test="#attr.tblListLookupSerialBean.ownerType == 2">
                    <s:property escapeJavaScript="true"  value="getError('MSG.FAC.StaffShop')"/>
                </s:elseif>
            </display:column>
            <display:column escapeXml="true" property="ownerCode" title="${fn:escapeXml(imDef:imGetText('MSG.store.code'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="ownerName" title="${fn:escapeXml(imDef:imGetText('MSG.storeName'))}" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" sortable="false" headerClass="tct">
                <s:if test="#attr.tblListLookupSerialBean.status == 0">
                    <s:property escapeJavaScript="true"  value="getError('MSG.paid')"/>
                </s:if>
                <s:elseif test="#attr.tblListLookupSerialBean.status == 1">
                    <s:property escapeJavaScript="true"  value="getError('MSG.instock')"/>
                </s:elseif>
                <s:elseif test="#attr.tblListLookupSerialBean.status == 2">
                    <s:property escapeJavaScript="true"  value="getError('MSG.used')"/>

                </s:elseif>
                <s:elseif test="#attr.tblListLookupSerialBean.status == 3">
                    <s:property escapeJavaScript="true"  value="getError('MSG.FAC.Pending')"/>

                </s:elseif>
                <s:elseif test="#attr.tblListLookupSerialBean.status == 4">
                    <s:property escapeJavaScript="true"  value="getError('MSG.registed.KIT')"/>

                </s:elseif>
                <s:elseif test="#attr.tblListLookupSerialBean.status == 5">
                    <s:property escapeJavaScript="true"  value="getError('MSG.removed')"/>

                </s:elseif>
                <s:elseif test="#attr.tblListLookupSerialBean.status == 6">
                    <s:property escapeJavaScript="true"  value="getError('MSG.lock')"/>

                </s:elseif>
            </display:column>
        </display:table>
    </div>
</fieldset>
