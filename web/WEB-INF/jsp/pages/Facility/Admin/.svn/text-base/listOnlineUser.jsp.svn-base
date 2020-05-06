<%-- 
    Document   : listOnlineUser
    Created on : Dec 29, 2009, 9:57:11 AM
    Author     : Doan Thanh 8
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<fieldset class="imFieldset">

    <legend>>${fn:escapeXml(imDef:imGetText('MSG.FAC.user.online'))}</legend>
    <table style="inputTbl2Col">
        <tr>
            <td style="width:60%; vertical-align:top; ">
                <div style="width:100%; height:300px; overflow:auto;" class="divHasBorder">
                    <display:table name="listOnlineUser" id="tblListOnlineUser"
                                   class="dataTable" cellpadding="1" cellspacing="1">
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListOnlineUser_rowNum)}</display:column>
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.FAC.User.LoginName'))}" sortable="false" headerClass="tct">
                            <s:property escapeJavaScript="true"  value="#attr.tblListOnlineUser" />
                        </display:column>
                    </display:table>
                </div>
            </td>
            <td style="vertical-align:top; ">
                <!-- phan hien thi thong tin message -->
                <div class="divHasBorder">
                    <table style="border: red dotted">
                        <tr>
                            <td><tags:label key="MSG.FAC.user.login" /></td>
                            <td style="text-align:right;">${fn:escapeXml(numberOnlineUser)}</td>
                        </tr>
                        <tr>
                            <td><tags:label key="MSG.FAC.user.loginMax" /></td>
                            <td style="text-align:right;">${fn:escapeXml(maxOnlineUser)}</td>
                        </tr>
                        <tr>
                            <td><tags:label key="MSG.FAC.user.loginTotal" /></td>
                            <td style="text-align:right;">${fn:escapeXml(numOfLogin)}</td>
                        </tr>
                        <tr>
                            <td><tags:label key="MSG.FAC.user.Start" /></td>
                            <td style="text-align:right;">${fn:escapeXml(startDate)}</td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>
    </table>
</fieldset>
