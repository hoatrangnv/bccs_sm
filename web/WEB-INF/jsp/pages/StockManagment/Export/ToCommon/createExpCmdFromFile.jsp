<%-- 
    Document   : createExpCmdFromFile
    Created on : Sep 1, 2010, 7:49:46 PM
    Author     : Doan Thanh 8
    Desc       : lap lenh xuat tu file
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
            request.setAttribute("contextPath", request.getContextPath());

%>

<tags:imPanel title="title.createExpCmdFromFile.page">
    <!-- phan hien thi thong tin message -->
    <div style="width:100%">
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi link download log loi -->
    <div align="center" class="txtError" id="divErrorFilePath">
        <s:if test="#request.errorFilePath != null">
            ${fn:escapeXml(imDef:imGetText('MSG.create.file.error.isdn'))}
            <a class="cursorHand" onclick="downloadErrorFile('${contextPath}/${fn:escapeXml(errorFilePath)}')">
                ${fn:escapeXml(imDef:imGetText('MSG.here'))}
            </a>
        </s:if>
    </div>

    <!-- phan hien thi thong tin mat hang can nhap kho -->
    <s:form action="createExpCmdFromFileAction!createExpCmdFromFile" theme="simple" method="post" id="createExpCmdFromFileForm">
<s:token/>

        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td><tags:label key="MSG.generates.file_data" /></td>
                    <td style="width: 550px;">
                        <tags:imFileUpload name="createExpCmdFromFileForm.clientFileName"
                                           id="createExpCmdFromFileForm.clientFileName"
                                           serverFileName="createExpCmdFromFileForm.serverFileName"
                                           cssStyle="width:500px;" extension="xls;txt"/>
                    </td>
                    <td>(<a onclick=downloadPatternFile()>download template</a>)</td>
                    <td>
                        <tags:submit formId="createExpCmdFromFileForm"
                                     showLoadingText="true"
                                     cssStyle="width:100px;"
                                     targets="bodyContent"
                                     value="File Preview"
                                     preAction="createExpCmdFromFileAction!filePreview.do"/>

                        <tags:submit formId="createExpCmdFromFileForm"
                                     showLoadingText="true"
                                     cssStyle="width:100px;"
                                     confirm="true"
                                     confirmText="MSG.confirm.create.cmd.exp.store"
                                     targets="bodyContent"
                                     value="MSG.createExportCmd"
                                     preAction="createExpCmdFromFileAction!createExpCmdFromFile.do"/>
                    </td>
                </tr>
            </table>
        </div>
    </s:form>

    <!-- phan xem truoc noi dung file -->
    <fieldset class="imFieldset">
        <legend>File preview</legend>
            <div style="width:100%; height:450px; overflow:auto;">
                <display:table name="listRowInFile" id="tblListRowInFile" class="dataTable" cellpadding="1" cellspacing="1">
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                        ${fn:escapeXml(tblListRowInFile_rowNum)}
                    </display:column>
                    <display:column escapeXml="true" property="actionCode" title="${fn:escapeXml(imDef:imGetText('MSG.STK.023'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true"  property="ownerCode" title="${fn:escapeXml(imDef:imGetText('MSG.STK.024'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true"  property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MSG.STK.026'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true"  property="stateName" title="${fn:escapeXml(imDef:imGetText('MSG.STK.028'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true"  property="quantityToString" title="${fn:escapeXml(imDef:imGetText('MSG.STK.029'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true" property="actionNote" title="${fn:escapeXml(imDef:imGetText('MSG.STK.030'))}" sortable="false" headerClass="tct"/>
                </display:table>
            </div>
    </fieldset>

</tags:imPanel>


<script type="text/javascript">
    
    downloadPatternFile = function() {
        window.open('${contextPath}/share/pattern/createExpCmdFromFilePattern.xls','','toolbar=yes,scrollbars=yes');
    }

    downloadErrorFile = function(errorFileUrl) {
        window.open(errorFileUrl, '', 'toolbar=yes,scrollbars=yes');
    }
</script>
