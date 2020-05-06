<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : collaborator
    Created on : Jun 13, 2009, 2:44:43 PM
    Author     : tamdt1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MES.CHL.091">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" type="key"/>
    </div>

    <!-- phan thong tin ve CTV -->
    <s:form action="channelAction!addOrEditCollaborator" theme="simple" enctype="multipart/form-data"  method="post" id="staffForm">
<s:token/>

        <s:hidden name="staffForm.staffId" id="staffForm.staffId"/>
        <s:hidden name="staffForm.shopId" id="staffForm.shopId"/>
        <s:hidden name="staffForm.staffOwnerId" id="staffForm.staffOwnerId"/>

        <table class="inputTbl4Col">
            <tr>
                <%--<td class="label">Mã NVQL (<font color="red">*</font>)</td>--%>
                <td class="label" ><tags:label key="MES.CHL.064" required="true" /></td>
                <td class="text">
                    <s:textfield name="staffForm.staffManagementName" id="staffManagementName" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                </td>
                <%--<td class="label">Tên NVQL (<font color="red">*</font>)</td>--%>
                <td class="label" ><tags:label key="MES.CHL.065" required="true" /></td>
                <td class="text">
                    <%--<s:select name="staffForm.staffOwnerId"
                              id="staffOwnerId"
                              cssClass="txtInputFull"
                              disabled="true"
                              headerKey="" headerValue="--Chọn nhân viên quản lý--"
                              list="%{#request.listStaffOwner != null ? #request.listStaffOwner :#{}}"
                              listKey="staffId" listValue="name"/>--%>
                    <tags:imSelect name="staffForm.staffOwnerId"
                                   id="staffOwnerId"
                                   cssClass="txtInputFull"
                                   disabled="true"
                                   headerKey="" headerValue="COM.CHL.003"
                                   list="listStaffOwner"
                                   listKey="staffId" listValue="name"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Loại Điểm bán/NVĐB (<font color="red">*</font>)</td>--%>
                <td class="label" ><tags:label key="MES.CHL.092" required="true" /></td>
                <td class="text">
                    <%--<s:select name="staffForm.channelTypeId"
                              id="staffForm.channelTypeId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="--Chọn loại Điểm bán/NVĐB--"
                              list="%{#request.listChannelType != null ? #request.listChannelType :#{}}"
                              listKey="channelTypeId" listValue="name"/>--%>
                    <tags:imSelect name="staffForm.channelTypeId"
                                   id="staffForm.channelTypeId"
                                   cssClass="txtInputFull"                                   
                                   headerKey="" headerValue="COM.CHL.004"
                                   list="listChannelType"
                                   listKey="channelTypeId" listValue="name"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Mã Điểm bán/NVĐB (<font color="red">*</font>)</td>--%>
                <td class="label" ><tags:label key="MES.CHL.055" required="true" /></td>
                <td class="text">
                    <s:textfield name="staffForm.staffCode" id="staffForm.staffCode" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                </td>
                <%--<td class="label">Tên Điểm bán/NVĐB (<font color="red">*</font>)</td>--%>
                <td class="label" ><tags:label key="MES.CHL.056" required="true" /></td>
                <td class="text">
                    <s:textfield name="staffForm.name" id="staffForm.name" maxlength="1000" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Chính sách giá (<font color="red">*</font>)</td>--%>
                <td class="label" ><tags:label key="MES.CHL.093" required="true" /></td>
                <td class="text">
                    <%--<s:select name="staffForm.pricePolicy"
                              id="staffForm.pricePolicy"
                              cssClass="txtInputFull"
                              disabled="true"
                              headerKey="" headerValue="--Chọn chính sách giá--"
                              list="%{#request.listPricePolicy != null ? #request.listPricePolicy :#{}}"
                              listKey="id.code" listValue="name"/>--%>
                    <tags:imSelect name="staffForm.pricePolicy"
                                   id="staffForm.pricePolicy"
                                   cssClass="txtInputFull"
                                   disabled="true"
                                   headerKey="" headerValue="COM.CHL.005"
                                   list="listPricePolicy"
                                   listKey="id.code" listValue="name"/>
                    <s:hidden name="staffForm.pricePolicy"/>
                </td>
                <%--<td class="label">Chính sách CK (<font color="red">*</font>)</td>--%>
                <td class="label" ><tags:label key="MES.CHL.094" required="true" /></td>
                <td class="text">
                    <%--<s:select name="staffForm.discountPolicy"
                              id="staffForm.discountPolicy"
                              cssClass="txtInputFull"
                              disabled="true"
                              headerKey="" headerValue="--Chọn chính sách CK--"
                              list="%{#request.listDiscountPolicy != null ? #request.listDiscountPolicy :#{}}"
                              listKey="id.code" listValue="name"/>--%>
                    <tags:imSelect name="staffForm.discountPolicy"
                                   id="staffForm.discountPolicy"
                                   cssClass="txtInputFull"
                                   disabled="true"
                                   headerKey="" headerValue="COM.CHL.006"
                                   list="listDiscountPolicy"
                                   listKey="id.code" listValue="name"/>
                    <s:hidden name="staffForm.discountPolicy"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Điện thoại</td>--%>
                <td class="label" ><tags:label key="MES.CHL.095"/></td>
                <td class="text">
                    <s:textfield name="staffForm.tel" id="staffForm.tel" maxlength="15" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
                <%--<td class="label">Fax</td>--%>
                <td class="label" ><tags:label key="MES.CHL.096"/></td>
                <td>
                    <s:textfield name="shopForm.fax" id="shopForm.fax" maxlength="15" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Số CMT (<font color="red">*</font>) </td>--%>
                <td class="label" ><tags:label key="MES.CHL.057" required="true"/></td>
                <td class="text">
                    <s:textfield name="staffForm.idNo" id="staffForm.idNo" maxlength="20" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
                <%--<td style="width:10%; ">Ngày cấp (<font color="red">*</font>) </td>--%>
                <td class="label" ><tags:label key="MES.CHL.067" required="true"/></td>
                <td style="width:20%; " class="oddColumn">
                    <tags:dateChooser property="staffForm.idIssueDate" id ="staffForm.idIssueDate"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Nơi cấp (<font color="red">*</font>) </td>--%>
                <td class="label" ><tags:label key="MES.CHL.066" required="true"/></td>
                <td class="text">
                    <s:textfield name="staffForm.idIssuePlace" id="staffForm.idIssuePlace" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
                <%--<td class="label">Địa chỉ (<font color="red">*</font>)</td>--%>
                <td class="label" ><tags:label key="MES.CHL.070" required="true"/></td>
                <td class="text" >
                    <s:textfield name="staffForm.address" id="staffForm.address" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
            </tr>
        </table>
    </s:form>

    <!-- phan nut tac vu, dong popup -->
    <s:if test="#request.collaboratorMode == 'prepareEdit'">
        <div align="center" style="padding-top:20px; padding-bottom:5px;">
            <%--<button style="width:80px;" onclick="addStaff()">Đồng ý</button>--%>
            <input type="button" style="width:80px;" onclick="addStaff();"  value="${fn:escapeXml(imDef:imGetText('MES.CHL.097'))}"/>
            <%--<button style="width:80px;" onclick="cancelEditStaff()">Bỏ qua</button>--%>
            <input type="button" style="width:80px;" onclick="cancelEditStaff();"  value="${fn:escapeXml(imDef:imGetText('MES.CHL.073'))}"/>
        </div>
    </s:if>
    <s:else>
        <div align="center" style="padding-top:20px; padding-bottom:5px;">
            <%--<button style="width:80px;" onclick="addStaff()">Thêm</button>--%>
            <input type="button" style="width:80px;" onclick="addStaff();"  value="${fn:escapeXml(imDef:imGetText('MES.CHL.098'))}"/>
            <%--<button style="width:80px;" onclick="findStaff()">Tìm kiếm</button>--%>
            <input type="button" style="width:80px;" onclick="findStaff();"  value="${fn:escapeXml(imDef:imGetText('MES.CHL.086'))}"/>
            <%--<button style="width:80px;" onclick="cancelAddStaff()">Bỏ qua</button>--%>
        </div>
    </s:else>
    <!-- phan hien thi danh sach CTV -->
    <sx:div id="divListCollaborator">
        <jsp:include page="listCollaborators.jsp"/>
    </sx:div>
</tags:imPanel>
<s:if test="#request.staffMode == 'closePopup'">
    <script language="javascript">
        window.opener.refreshListStaff();
        window.close();
    </script>
</s:if>
<script language="javascript">
    addStaff = function() {
        $('staffForm').submit();
        /*
        if(checkRequiredFields() && checkValidFields()) {
            $(priceForm).submit();
        }*/
    }

    cancelAddStaff = function() {
        window.close();
    }

    //xu ly nut "Bo qua" (bo qua viec sua doi thong tin collaborator)
    cancelEditStaff = function() {
        var staffId = $('staffForm.staffOwnerId').value;
        $('staffForm').action = "${contextPath}/channelAction!prepareAddCollaborator.do?selectedStaffId=" + staffId +'&'+ token.getTokenParamString();
        $('staffForm').submit();
    }

    //xu ly nut "tìm kiem" (bo qua viec sua doi thong tin collaborator)
    findStaff = function() {
        var staffId = $('staffForm.staffOwnerId').value;
        $('staffForm').action = "${contextPath}/channelAction!searchCollByStaffCode.do?selectedStaffId=" + staffId +'&' + token.getTokenParamString();
        $('staffForm').submit();
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        if(trim($('priceForm.price').value) == "") {
            <%--$('message').innerHTML = "Giá mặt hàng không được để trống";--%>
            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.028')"/>';
            $('priceForm.price').select();
            $('priceForm.price').focus();
            return false;
        }
        if(trim($('priceForm.vat').value) == "") {
            <%--$('message').innerHTML = "VAT không được để trống";--%>
            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.029')"/>';
            $('priceForm.vat').select();
            $('priceForm.vat').focus();
            return false;
        }
        /*
        if(trim($('saleServicesPriceForm.staDate').value) == "") {
            alert('Ngày bắt đầu không được để trống');
            $('saleServicesPriceForm.staDate').select();
            $('saleServicesPriceForm.staDate').focus();
            return false;
        }
         */
        if(trim($('priceForm.pricePolicy').value) == "") {
            <%--$('message').innerHTML = "Chọn Chính sách giá";--%>
            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.030')"/>';
            $('priceForm.pricePolicy').focus();
            return false;
        }
        if(trim($('priceForm.status').value) == "") {
            <%--$('message').innerHTML = "Chọn trạng thái";--%>
            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.031')"/>';
            $('priceForm.status').focus();
            return false;
        }
        return true;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        if(!isInteger(trim($('priceForm.price').value))) {
            <%--$('message').innerHTML = "Giá dịch vụ phải là số không âm";--%>
            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.032')"/>';
            $('priceForm.price').select();
            $('priceForm.price').focus();
            return false;
        }
        if(!isInteger(trim($('priceForm.vat').value))) {
            <%--$('message').innerHTML = "VAT phải là số không âm";--%>
            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.033')"/>';
            $('priceForm.vat').select();
            $('priceForm.vat').focus();
            return false;
        }
        if($('priceForm.vat').value < 0 || $('priceForm.vat').value > 100) {
            <%--$('message').innerHTML = "VAT phải là số không âm nằm trong khoảng 0 đến 100";--%>
            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.034')"/>';
            $('priceForm.vat').select();
            $('priceForm.vat').focus();
            return false;
        }
        return true;
    }
    priceChange = function(id) {
        //alert('ukie');
        var tmp = $(id).value;
        tmp = tmp.replace(/\,/g,"");
        var tmp1 = addSeparatorsNF(tmp, '.', '.', ',');
        $(id).value = tmp1;
    }
    textFieldNF($('priceForm.price'));
</script>
