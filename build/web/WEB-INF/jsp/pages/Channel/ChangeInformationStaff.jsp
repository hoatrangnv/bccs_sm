<%-- 
    Document   : ChangeInformationStaff
    Created on : Jun 15, 2010, 4:46:03 PM
    Author     : Vunt
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


<tags:imPanel title="MSG.change.info">

    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <s:form action="changeInformationStaffAction" theme="simple" method="post" id="addStaffCodeCTVDBForm">
        <s:token/>

        <fieldset class="imFieldset">
            <legend>
                <s:text name="MSG.search.info"/>
                <!--                $_{imDef:imGetText('MSG.search.info')}-->
            </legend>
            <sx:div id="searchStaff" theme="simple">
                <jsp:include page="SearchStaff.jsp"/>
            </sx:div>
        </fieldset>
        <br>
        <fieldset class="imFieldset">
            <legend>
                <s:text name="MSG.search.result"/>
                <!--                $_{imDef:imGetText('MSG.search.result')}-->
            </legend>
            <sx:div id="searchArea" theme="simple">
                <jsp:include page="ListChangeInfoStaff.jsp"/>
            </sx:div>
        </fieldset>
    </s:form>
</tags:imPanel>

<script language="javascript">

    checkValidFields = function() {
        var shopCode = document.getElementById("addStaffCodeCTVDBForm.shopCodeSearch");        
        if (shopCode.value ==""){
    <%--$('message' ).innerHTML='Bạn phải chọn mã cửa hàng';--%>
                //                $_( 'message' ).innerHTML='<s_:property value="getError('ERR.CHL.002')"/>';
                $( 'message' ).innerHTML='<s:text name="ERR.CHL.002"/>';
                shopCode.focus();
                return false;
            }
            return true;
        }
        
        prepareAddStaff = function() {
            openDialog("${contextPath}/changeInformationStaffAction!prepareAddStaff.do?"+token.getTokenParamString(), 1100, 650,true);
        }

        addStaff = function(){
            gotoAction("bodyContent", "changeInformationStaffAction!preparePage.do");
        }

        changeArea = function() {
            var provinceCode  = $('AddStaffCodeCTVDBForm.provinceCode').value;
            var districtCode  = $('AddStaffCodeCTVDBForm.districtCode').value;
            var precinctCode  = $('AddStaffCodeCTVDBForm.precinctCode').value;
            var streetBlockName  = document.getElementById("streetBlockName").value;
            var streetName  = document.getElementById("streetName").value;
            var home  = document.getElementById("home").value;
            getInputText('${contextPath}/changeInformationStaffAction!updateAddress.do?target=address&provinceCode='+ provinceCode + '&districtCode='+districtCode+'&precinctCode='+precinctCode+'&'+token.getTokenParamString()
                +'&streetBlockName='+streetBlockName+'&streetName='+streetName+'&home='+home);
        }
        
</script>
