<%-- 
    Document   : AddStaffCodeAP
    Created on : Jul 30, 2010, 9:21:47 AM
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>


<tags:imPanel title="MES.CHL.014">

    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi tieu chi bao cao -->
    <s:form action="addStaffCodeCTVDBAction" theme="simple" method="post" id="addStaffCodeCTVDBForm">
<s:token/>

        <%--<s:hidden name="reportRevenueForm.reportType" id ="reportCheckType" disabled="true"></s:hidden>--%>
        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <%--<td style="width:10%" >Mã cửa hàng<font color="red">*</font></td>--%>
                    <td class="label" style="width:10%" ><tags:label key="MES.CHL.015" required="true" /></td>
                    <td style="width:40%">
                        <tags:imSearch codeVariable="addStaffCodeCTVDBForm.shopCode" nameVariable="addStaffCodeCTVDBForm.shopName"
                                       codeLabel="MES.CHL.015" nameLabel="MES.CHL.016"
                                       searchClassName="com.viettel.im.database.DAO.addStaffCodeCVTDBDAO"
                                       searchMethodName="getListShop"
                                       roleList="rolef9ShopAddStaffAP"/>
                    </td>
                    <%--<td style="width:10%">Loại đối tượng<font color="red">*</font></td>--%>
                    <td class="label" style="width:10%" ><tags:label key="MES.CHL.017" required="true" /></td>
                    <td style="width:25%">
                        <%--  <s:select name="addStaffCodeCTVDBForm.channelTypeId"
                                    id="channelTypeId"
                                    cssClass="txtInputFull"
                                    theme="simple"
                                    headerKey="" headerValue="--Loại đối tượng--"
                                    list="#{'1':'CTV_AP','2':'CTVDM_AP'}"
                                    />--%>
                        <tags:imSelect name="addStaffCodeCTVDBForm.channelTypeId"
                                       id="channelTypeId"
                                       cssClass="txtInputFull"
                                       theme="simple"
                                       headerKey="" headerValue="COM.CHL.001"
                                       list="1:MES.CHL.018,2:MES.CHL.019"
                                       />
                    </td>
                    <%--<td style="width:7%">Số lượng</td>--%>
                    <td class="label" style="width:7%" ><tags:label key="MES.CHL.020"/></td>
                    <td >
                        <s:textfield name="addStaffCodeCTVDBForm.total" id="total" maxlength="3" cssClass="txtInputFull" cssStyle="text-align:right;"/>
                    </td>
                </tr>
                <tr>
                </tr>
            </table>
        </div>

    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="addStaffCodeCTVDBForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MES.CHL.007"
                     confirm="true" confirmText="MES.CHL.021"
                     validateFunction="checkValidFields()"
                     preAction="addStaffCodeCTVDBAction!addStaffCodeAP.do"/>
    </div>
</tags:imPanel>

<script language="javascript">

    checkValidFields = function() {
        var shopCode = document.getElementById("addStaffCodeCTVDBForm.shopCode");
        var channelTypeId = document.getElementById("channelTypeId");
        var total = document.getElementById("total");
        total.value=trim(total.value);
        if (shopCode.value == null || shopCode.value ==""){
    <%--$( 'message' ).innerHTML='Bạn phải chọn mã cửa hàng';--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.002')"/>';
                shopCode.focus();
                return false;
            }
            if(channelTypeId.value == null || channelTypeId.value == "" ){
    <%--$('message').innerHTML="Bạn phải chọn loại đối tượng"--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.003')"/>';
                channelTypeId.focus();
                return false;
            }
            if(total.value == null || total.value == '0'){
    <%--$('message').innerHTML="Bạn phải nhập trường số lượng"--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.004')"/>';
                total.focus();
                return false;
            }
            if(total.value != null && !isInteger(trim(total.value))){
    <%--$('message').innerHTML="Số lượng phải là số"--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.005')"/>';
                total.focus();
                return false;
            }
            return true;
        }
</script>
