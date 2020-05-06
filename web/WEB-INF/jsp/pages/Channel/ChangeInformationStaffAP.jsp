<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ChangeInformationStaffAP
    Created on : Jul 30, 2010, 9:42:43 AM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>


<tags:imPanel title="MES.CHL.037">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <s:form action="changeInformationStaffAction" theme="simple" method="post" id="addStaffCodeCTVDBForm">
<s:token/>

        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MES.CHL.038'))}</legend>
            <sx:div id="searchStaff" theme="simple">
                <jsp:include page="SearchStaffAP.jsp"/>
            </sx:div>
        </fieldset>
        <br>
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MES.CHL.039'))}</legend>
            <sx:div id="searchArea" theme="simple">
                <jsp:include page="ListChangeInfoStaffAP.jsp"/>
            </sx:div>
        </fieldset>
    </s:form>

</tags:imPanel>

<script language="javascript">

    checkValidFields = function() {
        var shopCode = document.getElementById("addStaffCodeCTVDBForm.shopCodeSearch");
        if (shopCode.value ==""){
    <%--$('message' ).innerHTML='Bạn phải chọn mã cửa hàng';--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.002')"/>';
                shopCode.focus();
                return false;
            }
            return true;
        }
</script>

