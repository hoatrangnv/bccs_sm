<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : portFormatList
    Created on : May 31, 2010, 11:24:02 AM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

    <sx:div id="displayTagFrame">
        <display:table pagesize="10" id="tblPortFormatList" name="listPortFormat" class="dataTable" targets="displayTagFrame" requestURI="portFormatAction!pageNavigator.do" cellpadding="1" cellspacing="1" >
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
            ${fn:escapeXml(tblPortFormatList_rowNum)}
            </display:column>

            <%----%><display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.device.type'))}" property="eqTypeName" sortable="false" headerClass="tct" style="width: 100px; text-align:left"/><%----%>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.device.code'))}" property="eqCode" sortable="false" headerClass="tct" style="width:100px; text-align:left"/>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.device.name'))}" property="eqName" sortable="false" headerClass="tct" style="width:200px; text-align:left"/>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.identifier'))}" property="portFormat" sortable="false" headerClass="tct" style="text-align:left"/>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.area.type'))}" property="typeName" sortable="false" headerClass="tct" style="width:100px; text-align:left"/>
            
            
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" headerClass="tct" sortable="false" style="width:100px; text-align:left">
                <s:if test="#attr.tblPortFormatList.status == 0"><tags:label key="MSG.inactive" /></s:if>
                <s:elseif test="#attr.tblPortFormatList.status == 1"><tags:label key="MSG.active" /></s:elseif>
                <s:else></s:else>
            </display:column>            
            
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                <s:bean name="com.viettel.im.common.util.QueryCryptSessionBean" var="vEditPortId" >                        
                    <s:param name="queryName">id</s:param>
                    <s:param name="httpRequestWeb" value="request" />
                    <s:param name="parameterId" value="#attr.tblPortFormatList.id" />
                </s:bean>  
                <a id="editPortId_<s:property escapeJavaScript="true"  value="#attr.tblPortFormatList.id"/>" onclick="prepareEditPortFormat('<s:property escapeJavaScript="true"  value="#vEditPortId.printParamCrypt()" />')">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit" />" alt="<s:text name="MSG.generates.edit" />"/>
                </a>                                  
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                <s:bean name="com.viettel.im.common.util.QueryCryptSessionBean" var="vDelPortId" >                        
                    <s:param name="queryName">id</s:param>
                    <s:param name="httpRequestWeb" value="request" />
                    <s:param name="parameterId" value="#attr.tblPortFormatList.id" />
                </s:bean>  
                <a id="delePortId_<s:property escapeJavaScript="true"  value="#attr.tblPortFormatList.id"/>" onclick="deletePortFormat('<s:property escapeJavaScript="true"  value="#vDelPortId.printParamCrypt()" />')">
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.generates.delete" />" alt="<s:text name="MSG.generates.delete" />"/>
                </a>                                   
            </display:column>
        </display:table>
    </sx:div>
