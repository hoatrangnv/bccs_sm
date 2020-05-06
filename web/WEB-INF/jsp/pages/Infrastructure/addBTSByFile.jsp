<%-- 
    Document   : addBTSByFile
    Created on : Jun 22, 2016, 2:17:22 PM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<script type="text/javascript">
    onchangeTemplate= function(){
        var fileType = $('chooseType').value;
        $('agentDistributeManagementForm.clientFileName').value = "";
        displayButton(fileType);
        displayFileTempalte(fileType);
        <%-- gotoAction("notChooseFileType", "manageBTSAction!changeTypeDownload.do", "agentDistributeManagementForm");--%>
    }
    downloadTemFile = function(fileName) {
        window.open('${contextPath}/download.do?down=1&'+fileName,'','toolbar=yes,scrollbars=yes');
    }
</script>

<div>
    <s:form action="manageBTSAction" theme="simple" method="post" id="agentDistributeManagementForm">
<s:token/>

        <table class="inputTbl6Col" align="right">
            <tr>
                <td class="label" ><tags:label key="BTS.ChooseFileType" required="true" /></td>
                <td class="text">
                    <tags:imSelect name="agentDistributeManagementForm.fileType" id="chooseType"
                                   cssClass="txtInputFull"
                                   list="1:BTS.fileAddNewBTS,
                                   3:BTS.fileUpdateChannel"
                                   onchange="onchangeTemplate()"
                                   headerKey="2" headerValue="BTS.chooseFile"/>
                </td>
                <%--
                <td id="notChooseFileType" align="right" width="150px" onclick="displayMessage($('chooseType').value)">
                    <a><tags:label key="MSG.download.fileaddNew.BTS"/></a>
                </td>
                --%>
                <td id="notChooseFileTypeAdd" align="right" width="150px"  style="display:none" onclick="displayMessage($('chooseType').value)">
                    <a href="${contextPath}/share/pattern/AddBTS.xls">${fn:escapeXml(imDef:imGetText('MSG.download.fileaddNew.BTS'))}</a>
                </td>
                <td id="notChooseFileTypeUpdate" align="right" width="150px"  style="display:none" onclick="displayMessage($('chooseType').value)">
                    <a href="${contextPath}/share/pattern/updateChannelBTS.xls">${fn:escapeXml(imDef:imGetText('MSG.download.fileaddNew.BTS'))}</a>
                </td>
                <%--
                <td id="fileAddNewBTS" align="right" width="150px" style="display:none">
                    <a onclick=downloadAddNewBTSFile()><s:property escapeJavaScript="true"  value="getText('MSG.download.fileaddNew.BTS')"/></a>
                </td>--%>
                <td class="label" ><tags:label key="BTS.ChooseFileUp" required="true" /></td>
                <td  class="text" >
                    <tags:imFileUpload name="agentDistributeManagementForm.clientFileName" id="agentDistributeManagementForm.clientFileName" serverFileName="agentDistributeManagementForm.serverFileName"
                                       extension="xls;xlsx"/>
                </td>

                <td id="mustChooseType">
                    <tags:submit formId="agentDistributeManagementForm"
                                 showLoadingText="true"
                                 targets="bodyContent"
                                 value="BTS.addByFile"
                                 cssStyle="width:95px;"
                                 preAction=""
                                 validateFunction="checkValidFile()"/>
                </td>
                <td style="display:none" id="addByFile">
                    <tags:submit formId="agentDistributeManagementForm"
                                 showLoadingText="true" confirm="true"
                                 targets="bodyContent" confirmText="BTS.Confirmtext.AddNewBTSByFile"
                                 value="BTS.addByFile"
                                 cssStyle="width:95px;"
                                 preAction="manageBTSAction!addByFile.do"
                                 validateFunction="checkValidFile()"/>
                </td>
                <td style="display:none" id="assignAreaByFile">
                    <tags:submit formId="agentDistributeManagementForm"
                                 showLoadingText="true" confirm="true"
                                 targets="bodyContent" confirmText="BTS.Confirmtext.AssignAreaBTSByFile"
                                 value="BTS.assignAreaBTSByFile"
                                 cssStyle="width:165px;"
                                 preAction="manageBTSAction!assignAreaByFile.do"
                                 validateFunction="checkValidFile()"/>
                </td>
                <td style="display:none" id="updateChannelByFile">
                    <tags:submit formId="agentDistributeManagementForm"
                                 showLoadingText="true" confirm="true"
                                 targets="bodyContent"
                                 value="BTS.updateChannelByFile"
                                 cssStyle="width:200px;"  confirmText="BTS.Confirmtext.UpdateChannelByFile"
                                 preAction="manageBTSAction!updateChannelByFile.do"
                                 validateFunction="checkValidFile()"/>
                </td>
            </tr>
        </table>
    </s:form>
    <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    <div>
        <s:if test="#request.reportAccountPath != null">
            <script>
                window.open('${contextPath}/download.do?<s:property escapeJavaScript="true" value="#request.reportAccountPath"/>','','toolbar=yes,scrollbars=yes');
                <%-- window.open('${contextPath}/download.do?down=1&<s:property escapeJavaScript="true" value="fileTemplateUpdateChannel"/>','','toolbar=yes,scrollbars=yes'); --%>
            </script>
            <a href="${contextPath}/download.do?<s:property escapeJavaScript="true" value="#request.reportAccountPath"/>">
                <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
            </a>
        </s:if>
    </div>


</div>
<script type="text/javascript">

    if($('chooseType').value == 1){
        // Cac button submit
        $('mustChooseType').style.display='none';
        $('addByFile').style.display='';
        $('assignAreaByFile').style.display='none';
        $('updateChannelByFile').style.display='none';
    }else if($('chooseType').value == 0){
        // Cac button submit
        $('mustChooseType').style.display='none';
        $('addByFile').style.display='none';
        $('assignAreaByFile').style.display='';
        $('updateChannelByFile').style.display='none';
    }else if($('chooseType').value == 2){
        // Cac button submit
        $('mustChooseType').style.display='';
        $('addByFile').style.display='none';
        $('assignAreaByFile').style.display='none';
        $('updateChannelByFile').style.display='none';
    }else if($('chooseType').value == 3){
        // Cac button submit
        $('mustChooseType').style.display='none';
        $('addByFile').style.display='none';
        $('assignAreaByFile').style.display='none';
        $('updateChannelByFile').style.display='';
    }

    report = function(){
        gotoAction("", "manageBTSAction!reportChannel.do", "")
    }
    displayFileTempalte = function(value) {
        if (value == 1) {
            $('notChooseFileTypeAdd').style.display='';
            $('notChooseFileTypeUpdate').style.display='none';
        } else if (value == 2) {
            $('notChooseFileTypeAdd').style.display='none';
            $('notChooseFileTypeUpdate').style.display='none';
        } else if (value == 3) {
            $('notChooseFileTypeAdd').style.display='none';
            $('notChooseFileTypeUpdate').style.display='';
        }
    } 
    displayButton = function(value){
        if(value == 1){
            // Cac button submit
            $('mustChooseType').style.display='none';
            $('addByFile').style.display='';
            $('assignAreaByFile').style.display='none';
            $('updateChannelByFile').style.display='none';
        }else if(value == 0){
            // Cac button submit
            $('mustChooseType').style.display='none';
            $('addByFile').style.display='none';
            $('assignAreaByFile').style.display='';
            $('updateChannelByFile').style.display='none';
        }else if(value == 2){
            // Cac button submit
            $('mustChooseType').style.display='';
            $('addByFile').style.display='none';
            $('assignAreaByFile').style.display='none';
            $('updateChannelByFile').style.display='none';
        }else if(value == 3){
            // Cac button submit
            $('mustChooseType').style.display='none';
            $('addByFile').style.display='none';
            $('assignAreaByFile').style.display='none';
            $('updateChannelByFile').style.display='';
        }
    }
    displayMessage = function(value){
        if(value.toString() == "2"){
            $('message').innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000035')"/>';
        }
    }
    checkValidFile = function(){
        var clientFileName = document.getElementById("agentDistributeManagementForm.clientFileName");
        if(trim($('chooseType').value) == 2){
            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000035')"/>';
            $('chooseType').focus();
            return false;
        }
        if (trim(clientFileName.value).length ==0){
    <%--"Bạn chưa chọn file cần tạo"--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000036')"/>';
                clientFileName.focus();
                return false;
            }
            return true;
        }

        downloadAddNewBTSFile = function() {
            window.open('${contextPath}/download.do?down=1&<s:property escapeJavaScript="true" value="fileTemplateAddNew"/>','','toolbar=yes,scrollbars=yes');
        }

        downloadAssignAreaForBTSFile = function(){
            window.open('${contextPath}/download.do?down=1&<s:property escapeJavaScript="true" value="fileTemplateAssign"/>','','toolbar=yes,scrollbars=yes');
        }

        downloadUpdateChannelBTSFile = function(){
            window.open('${contextPath}/download.do?down=1&<s:property escapeJavaScript="true" value="fileTemplateUpdateChannel"/>','','toolbar=yes,scrollbars=yes');
        }

</script>

