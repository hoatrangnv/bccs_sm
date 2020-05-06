<%-- 
    Document   : addMdf
    Created on : Jun 3, 2011, 11:12:59 PM
    Author     : MrSun
--%>
<%--
    Note: danh mục Mdf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
    if (request.getAttribute("listMdf") == null) {
        request.setAttribute("listMdf", request.getSession().getAttribute("listMdf"));
    }
    request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title = "menu.infrastructure.mdf.management">
    <div>
        <tags:displayResult id="message" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
    </div>

    <s:form action="mdfAction" id="mdfForm"  theme="simple" method="post" >
<s:token/>

        <s:hidden name="mdfForm.mdfId" id="mdfForm.mdfId"/>

        <table class="inputTbl4Col">
            <tr>
                <td class="label">
                    <tags:label key="mdf.code" required="true"/>
                </td>
                <td class="text">
                    <s:textfield name="mdfForm.code" id="mdfForm.code" maxLength="50" cssClass="txtInputFull"/>
                </td>
                <td class="label">
                    <tags:label key="mdf.name" required="true"/>
                </td>
                <td class="text">
                    <s:textfield name="mdfForm.name" id="mdfForm.name" maxLength="50" cssClass="txtInputFull"/>
                </td>
            </tr>

            <tr>
                <td class="label"><tags:label key="mdf.status" required="true"/> </td>
                <td class="text">
                    <tags:imSelect name="mdfForm.status" id="mdfForm.status"
                                   cssClass="txtInputFull"
                                   list="1:MSG.active,
                                   0:MSG.inactive"
                                   headerKey="" headerValue="mdf.status"/>
                </td>
                <td style="width:10%; " class="label"><tags:label key="mdf.province" required="true"/> </td>
                <td style="width:30%; " class="text" colspan="1">
                    <tags:imSearch codeVariable="mdfForm.province" nameVariable="mdfForm.provinceName"
                                   codeLabel="mdf.province" nameLabel="mdf.province"
                                   searchClassName="com.viettel.im.database.DAO.AssignDslamAreaDAO"
                                   searchMethodName="getListProvince"
                                   getNameMethod="getListProvinceName"
                                   roleList=""/>
                </td>
            <tr>

            <tr>
                <td class="label"><tags:label key="mdf.address" required="false"/> </td>
                <td class="text" colspan="3">
                    <s:textarea name="mdfForm.address" id="mdfForm.address" cols="3" cssClass="txtInputFull" rows="2" wrap="true"/>
                </td>
            </tr>    
        </table>
    </s:form>

    <div align="center" style="padding-bottom:5px;">
        <s:if test="#attr.mdfForm.mdfId == null || #attr.mdfForm.mdfId <=0 ">            
            <tags:submit value="mdf.add"
                         formId="mdfForm" 
                         showLoadingText="true"
                         confirm="true" 
                         confirmText="Do you want to add new MDF?" 
                         targets="bodyContent"
                         cssStyle="width:80px;" 
                         validateFunction="checkValidate()"
                         preAction="mdfAction!saveMdf.do"/>

            <tags:submit value="mdf.search"
                         formId="mdfForm"         
                         showLoadingText="true"
                         targets="bodyContent"
                         cssStyle="width:80px;" 
                         preAction="mdfAction!searchMdf.do"/>
        </s:if>
        <s:else>
            <tags:submit value="mdf.edit"
                         formId="mdfForm" 
                         showLoadingText="true"
                         confirm="true" 
                         confirmText="Do you want to edit curent MDF?" 
                         targets="bodyContent"
                         cssStyle="width:80px;" 
                         validateFunction="checkValidate()"
                         preAction="mdfAction!saveMdf.do"/>           
            <tags:submit value="mdf.cancel"
                         formId="mdfForm"                          
                         showLoadingText="true"
                         targets="bodyContent"
                         cssStyle="width:80px;" 
                         preAction="mdfAction!cancelEditMdf.do"/>            
        </s:else>
    </div>
</tags:imPanel>

<br />

<div id="listMdf">
    <tags:imPanel title="mdf.list">
        <jsp:include page="listMdf.jsp"/>
    </tags:imPanel>
</div>


<script type="text/javascript">
    $('mdfForm.code').select();
    $('mdfForm.code').focus();
    
    checkValidate = function() {
        
        if(trim($('mdfForm.code').value) == "") {
            $('message').innerHTML = '<s:text name="waring.mdf.code.null"/>';
            $('mdfForm.code').select();
            $('mdfForm.code').focus();
            return false;
        }
        if(trim($('mdfForm.name').value) == "") {
            $('message').innerHTML = '<s:text name="waring.mdf.name.null"/>';
            $('mdfForm.name').select();
            $('mdfForm.name').focus();
            return false;
        }
        if(trim($('mdfForm.status').value) == "") {
            $('message').innerHTML = '<s:text name="waring.mdf.status.null"/>';
            $('mdfForm.status').select();
            $('mdfForm.status').focus();
            return false;
        }
        if(trim($('mdfForm.province').value) == "") {
            $('message').innerHTML = '<s:text name="waring.mdf.province.null"/>';
            $('mdfForm.province').select();
            $('mdfForm.province').focus();
            return false;
        }
        return true;
    }

    confirmDelete = function (mdfId){
        if (mdfId == ""){
            alert("error.mdf.mdfid.null");
            return;
        }
        
        if(confirm('Do you want to delete this mdf?')){
            gotoAction('bodyContent', 'mdfAction!deleteMdf.do?mdfId='+mdfId+'&'+token.getTokenParamString());
        }
    }
    

</script>


