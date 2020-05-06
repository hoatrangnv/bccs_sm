<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listDslam
    Created on : May 15, 2009, 3:00:40 PM
    Author     : User one
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>
<sx:div id="displayTagFrame">

    <s:if test="#request.listDslam != null">

        <display:table id="tblListDslam"  name="listDslam"
                       class="dataTable"  cellpadding="1" cellspacing="1" pagesize="20" requestURI="dslamAction!pageNavigatorDslamCable.do"
                       targets="displayTagFrame"
                       >

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
                ${fn:escapeXml(tblListDslam_rowNum)}
            </display:column>

            <%--display:column title="Mã DSLAM/DLU" sortable="false" headerClass="sortable">
                <s:property escapeJavaScript="true"  value="#attr.tblListDslam.code"/>
            </display:column--%>


            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.dslam_dlu'))}" sortable="false" headerClass="sortable">
                <s:property escapeJavaScript="true"  value="#attr.tblListDslam.code + ' - ' + #attr.tblListDslam.name"/>
            </display:column>
            
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.limited.sport'))}" property="maxPort"  style="width:60px; text-align:right" sortable="false" headerClass="tct" format="{0}" />
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.total.sport.use'))}" property="usedPort" style="width:60px; text-align:right" sortable="false" headerClass="tct" format="{0}"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.total.sport.reserve'))}" property="reservedPort" style="width:60px; text-align:right" sortable="false" headerClass="tct" format="{0}"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.coordinates.Y'))}" sortable="false" property="x" style="width:60px; text-align:right" headerClass="tct" format="{0}"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.coordinates.X'))}" sortable="false" property="y" style="width:60px; text-align:right" headerClass="tct" format="{0}"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.dslam_dlu.ip'))}" sortable="false" property="dslamIp" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.AP.Bras_ip'))}" sortable="false" property="brasIp" headerClass="tct" style="width:100px; text-align:left"/>
            <!--display:column title="Tỉnh" sortable="false" property="province" headerClass="tct" style="width:75px; text-align:left"/-->

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" headerClass="tct"sortable="false" style="width:75px; text-align:left">
                <s:if test="#attr.tblListDslam.status == 1"><tags:label key="MSG.active" /></s:if>
                <s:else><tags:label key="MSG.inactive" /></s:else>
            </display:column>

                
          <%--      <display:column title="Sửa" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                <s:url action="dslamAction!prepareEditDslam" id="URLEdit">
                    <s:param name="dslamId" value="#attr.tblListDslam.dslamId"/>
                </s:url>
                <sx:a targets="divDisplayInfo" href="%{#URLEdit}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa" alt="Sửa"/>
                </sx:a>
            </display:column>
            <display:column title="Xóa" sortable="false" style="width:50px; text-align:center"headerClass="sortable">
                <s:url action="dslamAction!actionDeleteDslam" id="URL2">
                    <s:param name="dslamId" value="#attr.tblListDslam.dslamId"/>
                </s:url>
                <sx:a onclick="confirmDelete('%{#attr.tblListDslam.dslamId}')" >
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa" alt="Xóa"/>
                </sx:a>
            </display:column>
            --%>
            
        </display:table>
    </s:if>
</sx:div>

