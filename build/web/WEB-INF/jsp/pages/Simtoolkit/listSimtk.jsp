<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listSimtk
    Created on : Jan 18, 2012, 10:23:03 AM
    Author     : TrongLV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@page  import="com.viettel.im.database.BO.UserToken" %>
<%@taglib prefix="imDef" uri="imDef" %>

<tags:imPanel title="L.200008">    
    <div style="width:100%; overflow:auto;">
        <display:table id="lstSimtk" name="lstSimtk" class="dataTable" pagesize="10"
                       targets="searchResultArea" requestURI="simtkManagmentAction!selectPage.do"
                       cellpadding="0" cellspacing="0">
            <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.SIK.147'))}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
                <s:property escapeJavaScript="true"  value="%{#attr.lstSimtk_rowNum}"/>
            </display:column>            
            <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.shop.code'))}" property="shopCode" style="width:75px;text-align:left"/>
            <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.shop.name'))}" property="shopName" style="width:200px;text-align:left"/>
            <s:if test="form.objectTypeSearch == 2 ">
                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MES.CHL.064'))}" property="staffCode" style="width:100px;text-align:left"/>
                <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MES.CHL.065'))}" property="staffName" style="width:150px;text-align:left"/>
            </s:if>
            <s:if test="form.isVtUnitSearch == 2 ">
                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.chanel.code'))}" property="ownerCode" style="width:100px;text-align:left"/>
                <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.channel.name'))}" property="ownerName" style="width:150px;text-align:left"/>
            </s:if>
            <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MES.CHL.057'))}" property="idNo" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
            <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.address'))}" property="address" sortable="false" headerClass="tct" />
            <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.isdn'))}" property="msisdn" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>

            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.149'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <div align="center">
                    <s:url action="simtkManagmentAction!viewSimtkDetail" id="URL" encode="true" escapeAmp="false">                        
                        <s:param name="staffId" value="#attr.lstSimtk.staffId"/>
                        <s:param name="shopId" value="#attr.lstSimtk.shopId"/>
                        <s:param name="channelTypeId" value="#attr.lstSimtk.channelTypeId"/>
                        <s:param name="objectType" value="#attr.lstSimtk.objectType"/>
                        <s:param name="isVtUnit" value="#attr.lstSimtk.isVtUnit"/>
                        <s:param name="ownerId" value="#attr.lstSimtk.ownerId"/>
                        <s:param name="ownerType" value="#attr.lstSimtk.ownerType"/>
                        <s:param name="accountId" value="#attr.lstSimtk.accountId"/>
                    </s:url>
                    <sx:a targets="simtkArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                        <s:if test="#attr.lstSimtk.status <> 0 ">
                            <tags:label key="MSG.SIK.149"/>
                        </s:if>
                        <s:else>
                            <tags:label key="MSG.SIK.149"/>
                        </s:else>
                    </sx:a>
                </div>
            </display:column>

        </display:table>
    </div>
</tags:imPanel>

<sx:div id="simtkArea" theme="simple">
    <s:if test="form.vfSimtk != null && form.vfSimtk.shopId != null ">
        <jsp:include page="simtk.jsp"/>
    </s:if>
</sx:div>
