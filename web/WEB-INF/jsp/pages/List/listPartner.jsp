<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listPartner
    Created on : May 14, 2009, 11:37:31 AM
    Author     : ThanhNC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<sx:div id="displayTagFrame">
    <%
                if (request.getAttribute("listPartner") == null) {
                    request.setAttribute("listPartner", request.getSession().getAttribute("listPartner"));
                }
                request.setAttribute("contextPath", request.getContextPath());
    %>
    <fieldset class="imFieldset">
        <legend><s:property escapeJavaScript="true"  value="getError('MSG.LST.006')"/></legend>
        <s:if test="#session.listPartner != null && #session.listPartner.size() > 0">
            <display:table id="tblListPartner" name="listPartner"
                           pagesize="10" targets="displayTagFrame"
                           requestURI="partnerAction!pageNavigator.do"
                           class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" style="text-align:center" headerClass="tct" sortable="false">
                    ${fn:escapeXml(tblListPartner_rowNum)}
                </display:column>
                <display:column headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.partner.code'))}" >
                    <s:property escapeJavaScript="true"  value="#attr.tblListPartner.partnerCode" />
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.partner.name'))}" headerClass="tct" sortable="false">
                    <s:property escapeJavaScript="true"  value="#attr.tblListPartner.partnerName" />
                </display:column>
                <%--<display:column title="Tên liên hệ" headerClass="tct">
                    <s:property escapeJavaScript="true"  value="#attr.tblListPartner.contactName" />
                </display:column>--%>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.partner.type'))}" headerClass="tct" sortable="false">
                    <s:if test="#attr.tblListPartner.partnerType == 1">
                        <s:property escapeJavaScript="true"  value="getError('Bán hàng')"/>
                        <!--                        Bán hàng-->
                    </s:if>
                    <s:elseif test="#attr.tblListPartner.partnerType == 2">
                        <s:property escapeJavaScript="true"  value="getError('Vendor')"/>
                        <!--                        Vendor-->
                    </s:elseif>
                    <s:else>
                        <s:property escapeJavaScript="true"  value="getError('Vận tải')"/>
                        <!--                        Vận tải-->
                    </s:else>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.address'))}"  headerClass="tct">
                    <s:property escapeJavaScript="true"   value="#attr.tblListPartner.address"/>
                </display:column>
                <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.phone.number'))}" property="phone" headerClass="tct"/>
                <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.fax'))}" property="fax" headerClass="tct"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" headerClass="tct" sortable="false" style="width:90px; ">
                    <s:if test="#attr.tblListPartner.status == 1">
                        <s:property escapeJavaScript="true"  value="getError('MSG.active')"/>
                        <!--                        Hiệu lực-->
                    </s:if>
                    <s:elseif test="#attr.tblListPartner.status == 0">
                        <s:property escapeJavaScript="true"  value="getError('MSG.inactive')"/>
                        <!--                        Không hiệu lực-->
                    </s:elseif>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.date.start'))}" format="{0,date,dd/MM/yyyy}" property="staDate" headerClass="tct" style="width:100px; text-align:center; "/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.finish.date'))}" format="{0,date,dd/MM/yyyy}" property="endDate" headerClass="tct" style="width:100px; text-align:center; "/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px;">
                    <sx:a onclick="copyPartner('%{#attr.tblListPartner.partnerId}')">
                        <img src="${contextPath}/share/img/clone.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.copy')"/>" alt="getText('MSG.copy"/>
                    </sx:a>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                    <s:url action="partnerAction!prepareEditPartner" id="URL1">
                        <s:token/>
                        <s:param name="partnerId" value="#attr.tblListPartner.partnerId"/>
                        <s:param name="struts.token.name" value="'struts.token'"/>
                        <s:param name="struts.token" value="struts.token"/>
                    </s:url>
                    <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')"/>" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')"/>"/>
                    </sx:a>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.delete'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                    <s:url action="partnerAction!deletePartner" id="URL2">
                        <s:token/>
                        <s:param name="partnerId" value="#attr.tblListPartner.partnerId"/>
                        <s:param name="struts.token.name" value="'struts.token'"/>
                        <s:param name="struts.token" value="struts.token"/>


                    </s:url>
                    <sx:a onclick="delPartner('%{#attr.tblListPartner.partnerId}')" >
                        <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.delete')"/>" alt="<s:property escapeJavaScript="true"  value="getError('MSG.delete')"/>"/>
                    </sx:a>
                </display:column>
            </display:table>

        </s:if>
        <s:else>
            <font color='red'>
                <s:property escapeJavaScript="true"  value="getError('Danh mục rỗng')"/>
            </font>
        </s:else>
    </fieldset>
</sx:div>
