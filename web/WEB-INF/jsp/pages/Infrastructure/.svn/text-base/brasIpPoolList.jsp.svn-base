<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : brasIpPoolList
    Created on : May 19, 2010, 3:27:05 PM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

    <sx:div id="displayTagFrame">
        <display:table pagesize="25" id="tblBrasIppoolList" name="listBrasIppool" class="dataTable" targets="displayTagFrame" requestURI="brasIppoolAction!pageNavigator.do" cellpadding="1" cellspacing="1" >
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
            ${fn:escapeXml(tblBrasIppoolList_rowNum)}
            </display:column>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.ip'))}" property="ipPool" sortable="false" headerClass="tct" style="width:100px; text-align:center"/>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.pool.name'))}" property="poolname" sortable="false" headerClass="tct" style="width:100px; text-align:center"/>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.domain'))}" property="domain" sortable="false" headerClass="tct" style="width:100px; text-align:center"/>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.AP.Bras_ip'))}" property="brasIp" sortable="false" headerClass="tct" style="width:100px; text-align:center"/>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" property="statusName"  headerClass="tct" sortable="false" style="width:150px; text-align:left">
                <%--s:if test="#attr.tblBrasIppoolList.status == 0">IP Hong</s:if>
                <s:elseif test="#attr.tblBrasIppoolList.status == 1">IP free</s:elseif>
                <s:elseif test="#attr.tblBrasIppoolList.status == 2">IP dang su dung</s:elseif>
                <s:elseif test="#attr.tblBrasIppoolList.status == 3">IP tam khoa</s:elseif>
                <s:else></s:else--%>
            </display:column>
                
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.mask.ip'))}" property="ipMask" sortable="false" headerClass="tct" style="width:100px; text-align:center"/>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.pooluniq'))}" property="pooluniq" sortable="false" headerClass="tct" style="width:100px; text-align:center"/>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.update.date'))}" property="dateUpdate" sortable="false" headerClass="tct" style="width:150px; text-align:center" format="{0,date,dd/MM/yyyy}" />


            
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <s:bean name="com.viettel.im.common.util.QueryCryptSessionBean" var="vCopyBrasId" >                        
                    <s:param name="queryName">poolId</s:param>
                    <s:param name="httpRequestWeb" value="request" />
                    <s:param name="parameterId" value="#attr.tblBrasIppoolList.poolId" />
                </s:bean>  
                <a id="copyBrasId_<s:property escapeJavaScript="true"  value="#attr.tblBrasIppoolList.poolId"/>" onclick="prepareCopyBras('<s:property escapeJavaScript="true"  value="#vCopyBrasId.printParamCrypt()" />')">
                    <img src="${contextPath}/share/img/clone.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.copy')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.copy')" />"/>
                </a>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                <s:bean name="com.viettel.im.common.util.QueryCryptSessionBean" var="vEditBrasId" >                        
                    <s:param name="queryName">poolId</s:param>
                    <s:param name="httpRequestWeb" value="request" />
                    <s:param name="parameterId" value="#attr.tblBrasIppoolList.poolId" />
                </s:bean>  
                <a id="editBrasId_<s:property escapeJavaScript="true"  value="#attr.tblBrasIppoolList.poolId"/>" onclick="prepareEditBras('<s:property escapeJavaScript="true"  value="#vEditBrasId.printParamCrypt()" />')">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')" />"/>
                </a> 
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                <s:bean name="com.viettel.im.common.util.QueryCryptSessionBean" var="vDelBrasId" >                        
                    <s:param name="queryName">poolId</s:param>
                    <s:param name="httpRequestWeb" value="request" />
                    <s:param name="parameterId" value="#attr.tblBrasIppoolList.poolId" />
                </s:bean>  
                <a id="deleBrasId_<s:property escapeJavaScript="true"  value="#attr.tblBrasIppoolList.poolId"/>" onclick="deleteBras('<s:property escapeJavaScript="true"  value="#vDelBrasId.printParamCrypt()" />')">
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.generates.delete')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.delete')" />"/>
                </a>                   
            </display:column>
        </display:table>
    </sx:div>
