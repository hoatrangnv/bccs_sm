<%-- 
    Document   : cableNoManagement
    Created on : Jun 9, 2011, 4:49:29 AM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
    if (request.getAttribute("listCableNo") == null) {
        request.setAttribute("listCableNo", request.getSession().getAttribute("listCableNo"));
    }
    request.setAttribute("contextPath", request.getContextPath());
%>


<tags:imPanel title = "menu.infrastructure.cable.no.management">
    <div>
        <tags:displayResult id="message" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
    </div>

    <s:form action="cableNoAction" id="cableNoForm"  theme="simple" method="post" >
<s:token/>

        <table class="inputTbl4Col">
            <tr>
                <td style="width:10%; " class="label"><tags:label key="Province" required="true"/> </td>
                <td style="width:30%; " class="text" colspan="1">
                    <tags:imSearch codeVariable="cableNoForm.provinceCode" nameVariable="cableNoForm.provinceName"
                                   codeLabel="Province code" nameLabel="Province name"
                                   searchClassName="com.viettel.im.database.DAO.AssignDslamAreaDAO"
                                   searchMethodName="getListProvince"
                                   getNameMethod="getListProvinceName"
                                   elementNeedClearContent="cableNoForm.mdfCode;cableNoForm.mdfName;cableNoForm.boardCode;cableNoForm.boardName;cableNoForm.cableBoxCode;cableNoForm.cableBoxName"
                                   roleList=""/>
                </td>
                <td style="width:10%; " class="label"><tags:label key="Site" required="true"/> </td>
                <td style="width:30%; " class="text" colspan="1">
                    <tags:imSearch codeVariable="cableNoForm.mdfCode" nameVariable="cableNoForm.mdfName"
                                   codeLabel="MDF Code" nameLabel="MDF Name"
                                   otherParam="cableNoForm.provinceCode"
                                   searchClassName="com.viettel.im.database.DAO.DslamDAO"
                                   searchMethodName="getListMdf"
                                   getNameMethod="getMdfName" 
                                   elementNeedClearContent="cableNoForm.boardCode;cableNoForm.boardName;cableNoForm.cableBoxCode;cableNoForm.cableBoxName"
                                   roleList=""/>
                </td>
            </tr>
            <tr>
                <td style="width:10%; " class="label"><tags:label key="Main box" required="false"/> </td>
                <td style="width:30%; " class="text" colspan="1">
                    <tags:imSearch codeVariable="cableNoForm.boardCode" nameVariable="cableNoForm.boardName"
                                   codeLabel="Main box code" nameLabel="Main box name"
                                   searchClassName="com.viettel.im.database.DAO.CableNoDAO"
                                   searchMethodName="getListBoard"
                                   getNameMethod="getBoardName"
                                   elementNeedClearContent="cableNoForm.cableBoxCode;cableNoForm.cableBoxName"
                                   roleList=""/>
                </td>
                <td style="width:10%; " class="label"><tags:label key="Par end box" required="false"/> </td>
                <td style="width:30%; " class="text" colspan="1">
                    <tags:imSearch codeVariable="cableNoForm.cableBoxCode" nameVariable="cableNoForm.cableBoxName"
                                   codeLabel="Par end box code" nameLabel="Par end box name"
                                   searchClassName="com.viettel.im.database.DAO.CableNoDAO"
                                   searchMethodName="getListCableBox"
                                   getNameMethod="getCableBoxName"
                                   roleList=""/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="From port" required="false"/> </td>
                <td class="text"><s:textfield id="cableNoForm.fromPort" name="cableNoForm.fromPort" cssClass="txtInputFull"/> </td>
                <td class="label"><tags:label key="To port" required="false"/> </td>
                <td class="text"><s:textfield id="cableNoForm.toPort" name="cableNoForm.toPort" cssClass="txtInputFull"/> </td>
            <tr>
            <tr>                
                <td class="label"><tags:label key="Status" required="false"/> </td>
                <td class="text">
                    <tags:imSelect name="cableNoForm.status" id="cableNoForm.status"
                                   cssClass="txtInputFull"
                                   list="1:free,
                                   2:used,
                                   3:locked,
                                   0:damaged"
                                   headerKey="" headerValue="--Select port status--"/>
                </td>
            <tr>
        </table>
    </s:form>

    <div align="center" style="padding-bottom:5px;">
        <tags:submit value="search"
                     formId="cableNoForm" 
                     showLoadingText="true"
                     targets="bodyContent"
                     cssStyle="width:80px;"
                     validateFunction="validateSearch();"
                     preAction="cableNoAction!searchCableNo.do"/>
    </div>
</tags:imPanel>

<br />

<div id="listCableNo">
    <tags:imPanel title="List of cable no">
        <jsp:include page="listCableNoManagement.jsp"/>
    </tags:imPanel>
</div>


<script type="text/javascript">
    validateSearch = function(){
        if(trim($('cableNoForm.provinceCode').value) == "") {
            $('message').innerHTML = '<s:text name="Warning! You must input province!"/>';
            $('cableNoForm.provinceCode').select();
            $('cableNoForm.provinceCode').focus();
            return false;
        }
        
        if(trim($('cableNoForm.mdfCode').value) == "") {
            $('message').innerHTML = '<s:text name="Warning! You must input site!"/>';
            $('cableNoForm.mdfCode').select();
            $('cableNoForm.mdfCode').focus();
            return false;
        }
        
        
        if(trim($('cableNoForm.fromPort').value).length>0) {
            if(!isInteger((trim($('cableNoForm.fromPort').value)))){
                $('message').innerHTML = '<s:text name="Warning! From port is invalid!"/>';
                $('cableNoForm.fromPort').select();
                $('cableNoForm.fromPort').focus();
                return false;
            }
        }
        if(trim($('cableNoForm.toPort').value).length>0) {
            if(!isInteger((trim($('cableNoForm.toPort').value)))){
                $('message').innerHTML = '<s:text name="Warning! To port is invalid!"/>';
                $('cableNoForm.toPort').select();
                $('cableNoForm.toPort').focus();
                return false;
            }
        }
        
        if(trim($('cableNoForm.fromPort').value).length>0 && trim($('cableNoForm.toPort').value).length>0) {
            if (trim($('cableNoForm.fromPort').value) * 1 > trim($('cableNoForm.toPort').value) * 1){
                $('message').innerHTML = '<s:text name="Warning! From port must less then to port!"/>';
                $('cableNoForm.fromPort').select();
                $('cableNoForm.fromPort').focus();
                return false;
            }
        }
        
        return true;
    }
    
</script>

