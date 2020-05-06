<%-- 
    Copyright 2010 Viettel Telecom. All rights reserved.
    VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 

    Document   : changeInformationAgent
    Created on : Nov 22, 2011, 3:52:57 PM
    Author     : haint
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>


<tags:imPanel title="MSG.change.info">

    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <s:form action="changeInformationAgentAction" theme="simple" method="post" id="changeInfoAgentForm">
<s:token/>

        <fieldset class="imFieldset">
            <legend>
                <s:text name="MSG.search.info"/>
            </legend>
            <sx:div id="searchAgent" theme="simple">
                <jsp:include page="searchAgent.jsp"/>
            </sx:div>
        </fieldset>

        <fieldset class="imFieldset">
            <legend>
                <s:text name="MSG.search.result"/>
            </legend>
            <sx:div id="searchArea" theme="simple">
                <jsp:include page="listChangeInfoAgent.jsp"/>
            </sx:div>
        </fieldset>
    </s:form>

</tags:imPanel>

<script language="javascript">

    checkValidFields = function() {
        var shopCode = document.getElementById("changeInfoAgentForm.parShopCodeSearch");        
        if (shopCode.value ==""){
    <%--$('message' ).innerHTML='Bạn phải chọn mã cửa hàng';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.CHL.002"/>';
                shopCode.focus();
                return false;
            }
            return true;
        }
        
        changeArea = function() {
            var provinceCode  = $('changeInfoAgentForm.provinceCode').value;
            var districtCode  = $('changeInfoAgentForm.districtCode').value;
            var precinctCode  = $('changeInfoAgentForm.precinctCode').value;
            var streetBlockName  = document.getElementById("streetBlockName").value;
            var streetName  = document.getElementById("streetName").value;
            var home  = document.getElementById("home").value;
            getInputText('${contextPath}/changeInformationStaffAction!updateAddress.do?target=address&provinceCode='+ provinceCode + '&districtCode='+districtCode+'&precinctCode='+precinctCode+'&'+token.getTokenParamString()
                +'&streetBlockName='+streetBlockName+'&streetName='+streetName+'&home='+home);
        }
        
</script>
