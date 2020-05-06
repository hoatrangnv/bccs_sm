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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
    System.out.print("toandv12 " + request.getSession().getAttribute("lstInformation"));
    System.out.print("toandv12 1 " + request.getAttribute("lstInformation"));
    request.setAttribute("lstInformation", request.getAttribute("lstInformation"));
%>


<tags:imPanel title="title.informationUserVsa.page">

    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <s:form action="channelAction" theme="simple" method="post" id="informationUserVsaFrom">


        <fieldset class="imFieldset">
            <%--<legend>
                <s:text name="title.informationUserVsa.page"/>
                <!--                $_{imDef:imGetText('MSG.search.info')}-->
            </legend>--%>
            <table class="inputTbl6Col">


                <tr>
                    <%--<td >NV quản lý</td>--%>
                    
                    <td class="label"><tags:label key="MSG.VSA.UserName" /></td>
                    <td >
                        <s:textfield  name="informationUserVsaFrom.userName" id="informationUserVsaFrom.userName" readOnly="true" cssClass="txtInputFull"/>
                    </td>
                    <%--<td >Mã ĐB/NVĐB </td>--%>
                    <td class="label"><tags:label key="MSG.VSA.FullName" /></td>
                    <td >
                         <s:textfield name="informationUserVsaFrom.fullName" id="informationUserVsaFrom.fullName"  maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                        
                    </td>
                </tr>
                
                 <tr>
                    <%--<td >NV quản lý</td>--%>
                    
                    <td class="label"><tags:label key="MSG.email" /></td>
                    <td >
                        <s:textfield  name="informationUserVsaFrom.email" id="informationUserVsaFrom.email" readOnly="true" cssClass="txtInputFull"/>
                    </td>
                    <%--<td >Mã ĐB/NVĐB </td>--%>
                    <td class="label"><tags:label key="MSG.VSA.TELEPHONE" /></td>
                    <td >
                         <s:textfield name="informationUserVsaFrom.cellphone" id="informationUserVsaFrom.cellphone"  maxlength="20" cssClass="txtInputFull" readonly="true"/>
                        
                    </td>
                </tr>
                 <tr>
                    
                    <td class="label"><tags:label key="MSG.generates.GOD.status" /></td>
                    <td >
                        <s:textfield  name="informationUserVsaFrom.status" id="informationUserVsaFrom.status" readOnly="true" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.stock.staff.code" /></td>
                    <td >
                        <s:textfield  name="informationUserVsaFrom.staffCode" id="informationUserVsaFrom.staffCode" readOnly="true" cssClass="txtInputFull"/>
                    </td>
                    
                </tr>


            </table> 
             <sx:div id="searchArea" theme="simple">
                <jsp:include page="ListInformationUserVsa.jsp"/>
            </sx:div>



        </s:form>

        <%--<div id ="searchArea">
            <display:table id="coll" name="lstInformation" class="dataTable" pagesize="10"
                           targets="searchArea" requestURI="channelAction!selectPage.do"
                           cellpadding="0" cellspacing="0" >
                <display:column  title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
                    <s:property escapeJavaScript="true"  value="%{#attr.coll_rowNum}"/>
                </display:column>
                <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.055'))}" property="status" style="width:130px;text-align:left"/>
                <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.056'))}" property="roleId" style="width:240px;text-align:left"/>
                <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.015'))}" property="roleName" style="width:130px;text-align:center"/>
                <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.057'))}" property="description" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
                <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.058'))}" property="roleCode" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
                <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.059'))}" property="creatorId" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
            </display:table>
        </div>--%>
    </tags:imPanel>

    <script language="javascript">

        checkValidFields = function() {
            var shopCode = document.getElementById("addStaffCodeCTVDBForm.shopCodeSearch");
            if (shopCode.value == "") {
        <%--$('message' ).innerHTML='Bạn phải chọn mã cửa hàng';--%>
                    //                $_( 'message' ).innerHTML='<s_:property value="getError('ERR.CHL.002')"/>';
                    $('message').innerHTML = '<s:text name="ERR.CHL.002"/>';
                    shopCode.focus();
                    return false;
                }
                return true;
            }

            prepareAddStaff = function() {
                openDialog("${contextPath}/changeInformationStaffAction!prepareAddStaff.do?" + token.getTokenParamString(), 1100, 650, true);
            }

            addStaff = function() {
                gotoAction("bodyContent", "changeInformationStaffAction!preparePage.do");
            }

            changeArea = function() {
                var provinceCode = $('AddStaffCodeCTVDBForm.provinceCode').value;
                var districtCode = $('AddStaffCodeCTVDBForm.districtCode').value;
                var precinctCode = $('AddStaffCodeCTVDBForm.precinctCode').value;
                var streetBlockName = document.getElementById("streetBlockName").value;
                var streetName = document.getElementById("streetName").value;
                var home = document.getElementById("home").value;
                getInputText('${contextPath}/changeInformationStaffAction!updateAddress.do?target=address&provinceCode=' + provinceCode + '&districtCode=' + districtCode + '&precinctCode=' + precinctCode + '&' + token.getTokenParamString()
                        + '&streetBlockName=' + streetBlockName + '&streetName=' + streetName + '&home=' + home);
            }

    </script>
