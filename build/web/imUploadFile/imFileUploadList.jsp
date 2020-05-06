<%-- 
    Document   : imFileUploadList
    Created on : Sep 12, 2009, 2:45:56 PM
    Author     : Doan Thanh 8
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="#session.uploaded_list != null">
    <table cellspacing="1" cellpadding="1" class="dataTable">
        <s:iterator value="#session.uploaded_list" status="rowStatus">
            <tr class="<s:if test="#rowStatus.odd == true ">odd</s:if><s:else>even</s:else>">
                <td style="width: 50px; text-align: center;" >
                    <s:property escapeJavaScript="true"  value="#attr.rowStatus.index + 1"/>
                </td>
                <td><s:property escapeJavaScript="true"  /></td>
            </tr>
        </s:iterator>
    </table>
</s:if>
<s:else>
    <table cellspacing="1" cellpadding="1" class="dataTable">
        <tr class="odd">
            <td>Không có dữ liệu</td>
        </tr>
        <tr class="even">
            <td>&nbsp;</td>
        </tr>
    </table>
</s:else>

<s:hidden id="tamdt1_clientFileName" name="#session.clientFileName"/>
<s:hidden id="tamdt1_serverFileName" name="#session.serverFileName"/>
<s:hidden id="tamdt1_hasImUploadFileError" name="#session.hasImUploadFileError"/>



