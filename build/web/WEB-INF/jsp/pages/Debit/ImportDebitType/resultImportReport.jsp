<%-- 
    Document   : resultImportReport
    Created on : May 6, 2013, 11:12:32 AM
    Author     : thinhph2_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<s:if test="importCommonForm.messageType=='fail'">
    <span style="color: red;text-align: center">
        <s:property value="importCommonForm.returnMessage"/>
    </span>
    <s:if test="importCommonForm.resultSheetLink!=null && importCommonForm.resultSheetLink!=''">
        <br/>
        <a onclick="downloadResult('<s:property value="importCommonForm.resultSheetLink"/>')">
            <s:property escapeJavaScript="true"  value="getText('msg.dowload.file.result')"/>
        </a>
    </s:if>
</s:if>
<s:if test="importCommonForm.messageType=='success'">
    <span style="color: blue;text-align: center">
        <s:property value="importCommonForm.returnMessage"/>
    </span>
</s:if>
