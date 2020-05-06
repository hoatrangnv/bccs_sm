<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listPrinterUser
    Created on : Jan 8, 2010, 11:15:44 AM
    Author     : TheTM
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
            if (request.getAttribute("listPinterUser") == null) {
                request.setAttribute("listPinterUser", request.getSession().getAttribute("listPinterUser"));
            }
            request.setAttribute("contextPath", request.getContextPath());
//request.setAttribute("listFineItemPrices", request.getSession().getAttribute("listFineItemPrices"));
%>


<s:if test="#request.listPinterUser != null && #request.listPinterUser.size() > 0">
    <display:table id="tbLlistPrinterUser" name="listPinterUser"
                   class="dataTable" cellpadding="1" cellspacing="1" >
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tbLlistPrinterUser_rowNum)}</display:column>
        <display:column escapeXml="true"  property="userName" title="${fn:escapeXml(imDef:imGetText('MSG.RET.028'))}" style="text-align:left" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  property="ipAddress" title="${fn:escapeXml(imDef:imGetText('meessages.generates.ip_address'))}" style="text-align:right" sortable="false" headerClass="tct"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.X_amplitude'))}" sortable="false" style="text-align:right" headerClass="tct">
            <s:property escapeJavaScript="true"  value="#attr.tbLlistPrinterUser.XAmplitude"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.Y_amplitude'))}" sortable="false" style="text-align:right" headerClass="tct">
            <s:property escapeJavaScript="true"  value="#attr.tbLlistPrinterUser.YAmplitude"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.invoice_type'))}" headerClass="tct" sortable="false">
            <s:if test="#attr.tbLlistPrinterUser.invoiceType == 1">Windows</s:if>
            <s:else>
                <s:if test="#attr.tbLlistPrinterUser.invoiceType == 2">Linux</s:if>
            </s:else>
        </display:column>
        <display:column escapeXml="true"  property="serialCode" title="${fn:escapeXml(imDef:imGetText('MSG.generates.serial_code'))}" style="text-align:right" sortable="false" headerClass="tct"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
            <s:url action="GetBookTypeAction!prepareEditPrinterUser" id="URL1">
                <s:token/>
                <s:param name="selectedPrinterUserId" value="#attr.tbLlistPrinterUser.id"/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>
            </s:url>
            <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')" />"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
            <s:url action="GetBookTypeAction!deletePrinterUser" id="URL2">
                <s:token/>
                <s:param name="selectedPrinterUserId" value="#attr.tbLlistPrinterUser.id"/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>


            </s:url>
            <sx:a onclick="delPrinterUser('%{#attr.tbLlistPrinterUser.id}')">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.generates.delete')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.delete')" />"/>
            </sx:a>
        </display:column>

    </display:table>

    <script language="javascript">
       
        //xoa gia hien co
        
        delPrinterUser = function(id) {
            if(confirm("<s:property escapeJavaScript="true"  value="msg.tool.confirm01" />")){
                gotoAction('bodyContent','GetBookTypeAction!deletePrinterUser.do?selectedPrinterUserId=' + id+"&"+ token.getTokenParamString());
            }
        }
    </script>
</s:if>
<s:else>
    <table class="dataTable">
        <tr>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.RET.028'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('meessages.generates.ip_address'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.generates.X_amplitude'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.generates.Y_amplitude'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.generates.invoice_type'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.generates.serial_code'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}</th>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </table>
</s:else>
