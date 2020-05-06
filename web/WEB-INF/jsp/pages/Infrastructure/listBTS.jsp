<%-- 
    Document   : listBTS
    Created on : Jun 22, 2016, 2:15:08 PM
    Author     : mov_itbl_dinhdc
--%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
            if (request.getAttribute("btsModelList") == null) {
                request.setAttribute("btsModelList", request.getSession().getAttribute("btsModelList"));
            }
            request.setAttribute("contextPath", request.getContextPath());
            com.viettel.rd.crypto.AESPKCSBASE64URIEncode aes = (com.viettel.rd.crypto.AESPKCSBASE64URIEncode) session.getAttribute("aes");
%>

<sx:div id="displayTagFrame">
    <display:table pagesize="10" id="tblBTSModelList"  name="btsModelList" class="dataTable" targets="displayTagFrame" requestURI="manageBTSAction!pageNavigator.do" cellpadding="1" cellspacing="1" >
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
            ${fn:escapeXml(tblBTSModelList_rowNum)}
        </display:column>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('BTS.Bts_Code'))}" property="btsCode" sortable="false" headerClass="tct" />
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('BTS.Bts_Name'))}" property="btsName" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('BTS.Shop_Name'))}" property="shopManager" sortable="false" headerClass="tct" />
        <display:column escapeXml="false" title="${fn:escapeXml(imDef:imGetText('BTS.Status'))}" headerClass="tct" sortable="false">
            <s:if test="#attr.tblBTSModelList.status == 1"><tags:label key="BTS.active" /></s:if>
            <s:else><tags:label key="BTS.inactive" /></s:else>
        </display:column>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('BTS.Update_Staff_Id'))}" property="lastUpdateUser" sortable="false" headerClass="tct" />
        <display:column escapeXml="false" title="${fn:escapeXml(imDef:imGetText('BTS.From_Date'))}" format="{0,date,dd/MM/yyyy}" property="fromDate" headerClass="tct" />
        <display:column escapeXml="false" title="${fn:escapeXml(imDef:imGetText('BTS.To_Date'))}" format="{0,date,dd/MM/yyyy}" property="toDate" headerClass="tct" />
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('BTS.Province'))}" property="provinceName" sortable="false" headerClass="tct" />
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('BTS.District'))}" property="districtName" sortable="false" headerClass="tct" />
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('BTS.Precinct'))}" property="precinctName" sortable="false" headerClass="tct" />
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MES.CHL.070'))}" property="address" sortable="false" headerClass="tct" />
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.comment'))}" property="note" sortable="false" headerClass="tct" />


        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
            <s:url action="manageBTSAction!prepareEditBTS" id="URL1">
                <s:param name="btsCode" value="#attr.tblBTSModelList.btsCode"/>
            </s:url>
            <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getText('MSG.generates.edit')"/>" alt="<s:property escapeJavaScript="true"  value="getText('MSG.generates.edit')"/>"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('BTS.AssignArea'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
            <s:url action="manageBTSAction!prepareAssignAreaBTS" id="URL1">
                <s:token/>
                <s:param name="btsCode" value="#attr.tblBTSModelList.btsCode"/>
            </s:url>
            <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/forward_icon.jpg" title="<s:property escapeJavaScript="true"  value="getText('BTS.AssignArea')"/>" alt="<s:property escapeJavaScript="true"  value="getText('BTS.AssignArea')"/>"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('BTS.ViewHistory'))}" sortable="false"
                        style="text-align:center; width:50px" headerClass="tct">
            <s:set var="id" scope="request" value="%{#attr.tblBTSModelList.btsId}" />
            <%
                        String id = aes.encrypt(String.valueOf(request.getAttribute("id")));
                        request.setAttribute("id", id.trim());
            %>
            <sx:a onclick="editRecords('%{#request.id}')">
                <img src="${contextPath}/share/img/manifier_icon.jpg"
                     title="<s:property escapeJavaScript="true"  value="getText(BTS.ViewHistory)"/>"
                     alt="<s:property escapeJavaScript="true"  value="getText(BTS.ViewHistory)"/>"/>
            </sx:a>
        </display:column>
        <s:token/>
    </display:table>
</sx:div>

<script language="javascript">
    //Sửa
    editRecords = function(id) {
        openPopup("manageBTSAction!showBTSLog.do?btsId="+id, 1000,700,true )
    }
</script>

