<%-- 
    Document   : addSubAnyPay
    Created on : Oct 18, 2012, 3:20:28 PM
    Author     : os_levt1
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
<%@page import="com.viettel.im.common.util.ResourceBundleUtils" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="addSubAnyPayAction" theme="simple" method="POST" id="addSubAnyPayForm">
<s:token/>

    <tags:imPanel title="MSG.info.general">
        <!-- hien thi message -->
        <div class="divHasBorder">
            <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
        </div>
        
        <div class="divHasBorder">
            <table class="inputTb16Col">
                <tr>
                    <td width="30%"><tags:label key="L.200051" /></td>
                    <td>
                        <tags:imRadio name="addSubAnyPayForm.impType"
                                      id="addSubAnyPayForm.impType"
                                      list="1:L.200053,2:L.200052"
                                      onchange="radioClick(this.value)"/>
                    </td>
                </tr>
            </table>
            <table class="inputTbl7Col" id="impSingle">
                <tr>
                    <td><tags:label key="MSG.chanel.code" required="true"/></td>
                    <td>
                        <tags:imSearch codeVariable="addSubAnyPayForm.collaboratorCode" nameVariable="addSubAnyPayForm.collaboratorName"
                                       codeLabel="MSG.chanel.code" nameLabel="MSG.chanel.name"
                                       searchClassName="com.viettel.im.database.DAO.AddSubAnyPayDAO"
                                       searchMethodName="getListChannel"
                                       getNameMethod="getListChannel"
                                       otherParam=""
                                       elementNeedClearContent=""
                                       roleList=""/>
                    </td>
                    <td><tags:label key="MSG.amount" required="true"/></td>
                    <td>
                        <s:textfield name="addSubAnyPayForm.amount" id="addSubAnyPayForm.amount" maxlength="50" cssStyle ="text-align:right" cssClass="txtInputFull"/>
                    </td>
                    <td><tags:label key="MSG.GOD.240" required="true"/></td>
                    <td>
                        <s:textfield name="addSubAnyPayForm.reason" id="addSubAnyPayForm.reason" maxlength="200" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
                    
            <table class="inputTbl7Col" id="impByFile">
                <tr>
                    <td colspan="3" align="center">
                        <a href="${contextPath}/share/pattern/AddSubAnyPay.xls">${fn:escapeXml(imDef:imGetText('MSG.download.file.here'))}</a>
                    </td>
                </tr>
                <tr>
                    <td class="label" ><tags:label key="MSG.generates.file_data" required="true" /></td>
                    <td  class="text" >
                        <tags:imFileUpload name="addSubAnyPayForm.clientFileName" id="addSubAnyPayForm.clientFileName" serverFileName="AddSubAnyPayForm.serverFileName" extension="xls;xlsx"/>
                    </td>
                    <td>
                        <tags:submit formId="addSubAnyPayForm"
                                         showLoadingText="true"
                                         cssStyle="width:80px;"
                                         id="btnFilePreview"
                                         targets="bodyContent"
                                         value="File Preview"
                                         validateFunction="checkValidateBeforePreview()"
                                         preAction="addSubAnyPayAction!filePreview.do"/>
                    </td>
                </tr>
            </table>        
                          
            <table class="inputTbl7Col">
                <tr>
                    <td align="center">
                        <tags:submit formId="addSubAnyPayForm"
                                         confirm="true"
                                         confirmText="C.200006"
                                         showLoadingText="true"
                                         cssStyle="width:80px;"
                                         id="btnUpdateByFile"
                                         targets="bodyContent"
                                         value="MSG.update"
                                         validateFunction="checkValidateBeforeAssign()"
                                         preAction="addSubAnyPayAction!updateAnyPay.do"/>
                    </td>     
                </tr>
            </table>
        </div>
        
        <div>
        <s:if test="#request.resultFilePath != null">
            <a href="${contextPath}${fn:escapeXml(resultFilePath)}">
                <tags:displayResult id="resultMessage" property="resultMessage" type="key"/>
            </a>
        </s:if>
       </div>

        <div>
            <jsp:include page="addSubAnyPayList.jsp"/>
        </div>

    </tags:imPanel>
</s:form>


<script>
    downloadErrorFile = function(errorFileUrl) {
        window.open(errorFileUrl, '', 'toolbar=yes,scrollbars=yes');
    }
    checkValidateBeforeAssign = function () {
        var i = 1;
        for(i = 1; i < 3; i=i+1) {
            var radioId = "addSubAnyPayForm.impType" + i;
            if($(radioId).checked == true) {
                break;
            }

        }
        if(i == 1) {
            //neu lay du lieu tu file
            if($('addSubAnyPayForm.clientFileName').value == "") {
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('E.200062')"/>';
                $('addSubAnyPayForm.clientFileName').select();
                $('addSubAnyPayForm.clientFileName').focus();
                return false; 
            }
        } else { 
            //neu nhap du lieu theo dai
            //1> kiem tra cac truong can thiet
            if(trim($('addSubAnyPayForm.collaboratorCode').value) == "") {
                $('message').innerHTML = "";
                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('E.200058')"/>'; 
                $('addSubAnyPayForm.collaboratorCode').focus();
                return false;
            }
            if(trim($('addSubAnyPayForm.amount').value) == "") {
                $('message').innerHTML = "";
                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('E.200059')"/>'; 
                $('addSubAnyPayForm.amount').select();
                $('addSubAnyPayForm.amount').focus();
                return false;
            }
           if(trim($('addSubAnyPayForm.reason').value) == "") {
                $('message').innerHTML = "";
                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('E.200061')"/>'; 
                $('addSubAnyPayForm.reason').select();
                $('addSubAnyPayForm.reason').focus();
                return false;
            }
            //2> kiem tra tinh hop le cua cac truong
            var amount = $('addSubAnyPayForm.amount').value;
            if(!isNum(trim(amount)) || amount == 0) {
                $('message').innerHTML = "";
                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('E.200060')"/>'; 
                $('addSubAnyPayForm.amount').select();
                $('addSubAnyPayForm.amount').focus();
                return false;
            }
        }

        return true;
    }

    checkValidateBeforePreview = function () {
        var i = 1;
        for(i = 1; i < 3; i=i+1) {
            var radioId = "addSubAnyPayForm.impType" + i;
            if($(radioId).checked == true) {
                break;
            }

        }
        if(i == 1) {
            //neu lay du lieu tu file
            if($('addSubAnyPayForm.clientFileName').value == "") {
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('E.200062')"/>';
                $('addSubAnyPayForm.clientFileName').select();
                $('addSubAnyPayForm.clientFileName').focus();
                return false;
            }
        }

        return true;
    }

    //xu ly su kien khi thay doi kieu nhap du lieu
    radioClick = function(value){
        if(value == 1) {
            //disable cac component phuc vu viec nhap so theo dai
            $('impSingle').style.display = 'none';
            //enable component phuc vu viec nhap so theo file
            disableImFileUpload('addSubAnyPayForm.clientFileName', false);
            $('impByFile').style.display = '';
        } else {
            //enable cac component phuc vu viec nhap so theo dai
            $('impSingle').style.display = '';
            //disable component phuc vu viec nhap so theo file
            disableImFileUpload('addSubAnyPayForm.clientFileName', true);
            $('impByFile').style.display = 'none';
        }
    }

    downloadPatternFile = function() {
        window.open('${contextPath}/share/pattern/AddSubAnyPay.xls','','toolbar=yes,scrollbars=yes');
    }

    //update viec an hien cac vung, tuy thuoc vao kieu nhap du lieu
    var i = 1;
    for(i = 1; i < 3; i=i+1) {
        var radioId = "addSubAnyPayForm.impType" + i;
        if($(radioId).checked == true) {
            radioClick(i);
            break;
        }

    }
    
</script>
