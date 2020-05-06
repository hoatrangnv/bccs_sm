<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listProvince
    Created on : Jun 19, 2009, 10:47:03 AM
    Author     : NamNX
--%>

<%--
    Note   : hien thi danh sach cac tinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>


    <tags:imPanel title="MSG.province.list">
    <div style="width:100%; height:580px; overflow:auto;">
        <s:if test="#request.listProvince != null && #request.listProvince.size() > 0">
            <display:table id="tbllistProvince" name="listProvince"
                           class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tbllistProvince_rowNum)}</display:column>
                <display:column escapeXml="true" property="province" title="${fn:escapeXml(imDef:imGetText('MSG.province.code'))}" sortable="false" headerClass="tct" style="width:100px; text-align:left" />
                <display:column escapeXml="true" property="name" title="${fn:escapeXml(imDef:imGetText('MSG.province.name'))}" sortable="false" headerClass="tct"/>

                <%--DSLAM
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.dslam.list'))}" sortable="false" style="width:100px; text-align:center" headerClass="tct">
                    <s:url action="chassicAction!dslamResult" id="URL1">
                        <s:param name="selectedProvince" value="#attr.tbllistProvince.province"/>
                    </s:url>
                    <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/accept.png" title="<s:text name="MSG.dslam.list" />" alt="<s:text name="MSG.dslam.list" />"/>
                    </sx:a>
                </display:column>
                        --%>

                        <%--CHASSIC--%>
                         <display:column title="Chassic" sortable="false" style="width:100px; text-align:center" headerClass="tct">
                    <s:url action="chassicAction!displayChassic" id="URL1">
                        <s:param name="selectedProvince" value="#attr.tbllistProvince.province"/>
                    </s:url>
                    <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/accept.png" title="<s:text name="Dslam" />" alt="<s:text name="Dslam" />"/>
                    </sx:a>
                </display:column>


            </display:table>
        </s:if>
        <s:else>
           <font color='red'>
               <tags:label key="MSG.blank.item" />
            </font>
        </s:else>
    </div>
</tags:imPanel>
