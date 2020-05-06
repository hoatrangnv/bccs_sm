<%-- 
    Document   : addStaffCodeCTVDB
    Created on : Jun 15, 2010, 4:03:31 PM
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


<tags:imPanel title="MSG.blank.code.inset">

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
                    <td class="label" style="width:10%" ><tags:label key="MSG.shop.code" required="true" /></td>
                    <td style="width:40%">
                        <tags:imSearch codeVariable="addStaffCodeCTVDBForm.shopCode" nameVariable="addStaffCodeCTVDBForm.shopName"
                                       codeLabel="Mã cửa hàng" nameLabel="Tên cửa hàng"
                                       searchClassName="com.viettel.im.database.DAO.addStaffCodeCVTDBDAO"
                                       searchMethodName="getListShop"                                       
                                       roleList="rolef9ShopAddStaff"/>
                    </td>
                    <td class="label" style="width:10%"><tags:label key="MSG.object.type" required="true" /></td>
                    <td style="width:25%">
                        <%--<s:select name="addStaffCodeCTVDBForm.channelTypeId"
                                  id="channelTypeId"
                                  cssClass="txtInputFull"
                                  theme="simple"
                                  headerKey="" headerValue="--Loại đối tượng--"
                                  list="#{'2':'Nhân viên địa bàn','1':'Điểm bán'}"
                                  />--%>
<%--                        <tags:imSelect name="addStaffCodeCTVDBForm.channelTypeId"
                                       id="channelTypeId"
                                       cssClass="txtInputFull"
                                       theme="simple"
                                       headerKey="" headerValue="COM.CHL.001"
                                       list="2:MES.CHL.085,1:MES.CHL.084"
                                       />
--%>

                        <tags:imSelect name="addStaffCodeCTVDBForm.channelTypeId" id="channelTypeId" headerKey="" 
                                       headerValue="label.option" cssClass="txtInputFull"
                                       list="lstChannelTypeCol" listKey="channelTypeId" listValue="name"/>
                    </td>
                    <td class="label" style="width:7%"><tags:label key="MSG.quantity" required="false" /></td>
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
                     value="MSG.code.create"
                     confirm="true" confirmText="MSG.createcode.confirm"
                     validateFunction="checkValidFields()"
                     preAction="addStaffCodeCTVDBAction!addStaffCode.do"/>
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
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.CHL.002')"/>';
                shopCode.focus();
                return false;
            }
            if(channelTypeId.value == null || channelTypeId.value == "" ){
    <%--$('message').innerHTML="Bạn phải chọn loại đối tượng"--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.CHL.003')"/>';
                channelTypeId.focus();
                return false;
            }
            if(total.value == null || total.value == '0'){
    <%--$('message').innerHTML="Bạn phải nhập trường số lượng"--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.CHL.004')"/>';
                total.focus();
                return false;
            }
            if(total.value != null && !isInteger(trim(total.value))){
    <%--$('message').innerHTML="Số lượng phải là số"--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.CHL.005')"/>';
                total.focus();
                return false;
            }
            return true;
        }
</script>
