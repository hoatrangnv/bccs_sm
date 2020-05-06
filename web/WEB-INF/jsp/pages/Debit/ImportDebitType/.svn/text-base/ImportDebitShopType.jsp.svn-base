<%-- 
    Document   : GroupArea
    Created on : May 4, 2013, 9:30:25 AM
    Author     : thinhph2_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tags:imPanel title="title.import.shop">
    <s:form id="importCommonForm" method="POST" theme ="simple">
        <table style="width: 100%" cellpadding="10px" cellspacing="10px" align="center" class="inputTbl6Col">
            <tr>
                <td class="label"><tags:label key="lbl.chose.file" required="true" /></td>
                <td class="text">
                    <s:file type="file" id="reportFile" name="importCommonForm.reportFile" size="50"/>
                </td>
                <td class="label"><tags:label key="MSG.attachmentFile" required="true" /></td>
                <td class="text">
                    <tags:imFileUpload id="fileUpload" name="importCommonForm.clientFileName" serverFileName="importCommonForm.serverFileName" extension="doc;docx;pdf;xls;xlsx;png;jpg;jpeg;bmp"/>
                </td>
            </tr>
            <tr>
                <td>(<a onclick=downloadFile('Import_shop_debit.xls')>Download Template</a>)</td>
            </tr>
        </table>
        <center>
            <input type="button" value="Import" onclick="importFile()"/>
        </center>
    </s:form>

    <div id="resultMsg" style="text-align: center"></div>
    <iframe id="uploadIframe" name="uploadIframe" style="display: none"></iframe>
</tags:imPanel>
<div id="resultMsg" style="text-align: center"></div>
<tags:displayResult id="displayResultMsg" property="returnMsg" type="key" propertyValue="returnMsgParam"/>
<script type="text/javascript">

    importFile=function(){
        
        if(jQuery('#reportFile').val()==''){
            jQuery("#displayResultMsg").html('<s:property value="getText('err.loi.chua.attack.file')"/>');
            jQuery("#reportFile").focus();
            return;
        }
        if(jQuery("#fileUpload").val() == ""){
            jQuery("#displayResultMsg").html('<s:property value="getText('err.loi_chua_chon_file_dinh_kem')"/>');
            jQuery("#fileUpload").focus();
            return;
        }
        if(!confirm('Bạn có chắc chắn muốn import file này?')){
            return;
        }
        var actionURL = "importDebitShopTypeAction!importFile.do";
        var form = jQuery("#importCommonForm");
        // Set properties of form...
        form.attr("target", "uploadIframe");
        form.attr("action", actionURL);
        form.attr("method", "post");
        form.attr("enctype", "multipart/form-data");
        form.attr("encoding", "multipart/form-data");
        // Submit the form...
        jQuery("#resultMsg").html("Uploading...");

        form.submit();
        jQuery("#uploadIframe").load(function () {
            iframeContents = jQuery("#uploadIframe")[0].contentWindow.document.body.innerHTML;
            jQuery("#resultMsg").html(iframeContents);
            jQuery("#displayResultMsg").text("");
        })
        jQuery('#reportFile').text('');
        jQuery('#fileUpload').val('');
    }
    
    downloadFile=function(fileName){
        window.open("${contextPath}" + "/download!downloadTemplate.do?filePath="+fileName,'','toolbar=no,scrollbars=no');
    }
    
    downloadResult = function(fileName){         
        window.open("${contextPath}" + "/download.do?"+fileName,'','toolbar=no,scrollbars=no');
    }

</script>
