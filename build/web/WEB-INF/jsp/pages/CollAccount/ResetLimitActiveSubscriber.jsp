<%-- 
    Document   : ResetLimitActiveSubscriber
    Created on : Jun 23, 2010, 4:26:28 PM
    Author     : User
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form method="POST" id="collAccountManagmentForm" action="CollAccountManagmentAction" theme="simple">
<s:token/>

    <sx:div id="searchAreaColl" theme="simple">
        <jsp:include page="searchResetLimit.jsp"/>
    </sx:div>
    <br>
    <tags:imPanel title="MSG.SIK.112">
        <sx:div id="searchArea" theme="simple">
            <jsp:include page="ListSearchResetLimit.jsp"/>
        </sx:div>
    </tags:imPanel>
    <%--
        <sx:div id="showView" theme="simple">
            <jsp:include page="ShowViewStaffAndAccount.jsp"/>
        </sx:div>
    --%>


</s:form>

<script language="javascript">

    checkValidFields = function() {
        var typeId = document.getElementById("channelTypeId");
        var shopcode = document.getElementById("collAccountManagmentForm.shopcode");
        if(shopcode.value == null || shopcode.value == ""){
    <%--$( 'displayResultMsg' ).innerHTML='Bạn phải chọn đơn vị';--%>
                $('displayResultMsg').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.032')"/>';
                $('collAccountManagmentForm.shopcode').focus();
                return false;
            }
            if(typeId.value == null || typeId.value == ""){
    <%--$( 'displayResultMsg' ).innerHTML='Bạn phải chọn loại tài khoản';--%>
                $('displayResultMsg').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.033')"/>';
                $('channelTypeId').focus();
                return false;
            }
            if(typeId.value == 3){
                $( 'displayResultMsg' ).innerHTML='';
                var staffManage = document.getElementById("collAccountManagmentForm.staffManageCode");
                var collCode = document.getElementById("collAccountManagmentForm.collCode");
            }
            else{

            }
            return true;
        }
</script>

