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
    <table class="filelist">
        <tr>
            <th>List of uploaded files</th>
        </tr>
        <s:iterator value="#session.uploaded_list" status="rowStatus">
            <tr
                class="<s:if test="#rowStatus.odd == true ">odd</s:if><s:else>even</s:else>">
                <td><s:property escapeJavaScript="true"  /></td>
            </tr>
        </s:iterator>
    </table>
</s:if>

