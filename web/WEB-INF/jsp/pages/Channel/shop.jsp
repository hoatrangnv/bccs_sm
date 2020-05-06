<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : shop
    Created on : Apr 19, 2009, 3:49:43 PM
    Author     : tamdt1
--%>

<%--
    Note: hien thi thong tin ve shop
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

<s:if test="#request.shopMode == 'prepareAddOrEdit'">
    <s:set var="readonly" value="false" scope="request"/>
</s:if>
<s:else>
    <s:set var="readonly" value="true" scope="request"/>
</s:else>


<tags:imPanel title="MES.CHL.118">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" type="key"/>
    </div>

    <!-- phan hien thi thong tin ve shop -->
    <s:form action="channelAction" theme="simple" method="post" id="shopForm">
        <s:token/>

        <s:hidden name="shopForm.shopId" id="shopID"/>
        <table class="inputTbl6Col">
            <tr>
                <%--<td class="label">Mã kênh <s:if test="#request.readonly == false"> (<font color="red">*</font>)</s:if></td>--%>
                <td class="label"><tags:label key="MES.CHL.119" required="${fn:escapeXml(!readonly)}"/></td>
                <td class="text">
                    <s:textfield name="shopForm.shopCode" id="shopForm.shopCode"  maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
                <%--<td class="label">Tên kênh <s:if test="#request.readonly == false"> (<font color="red">*</font>)</s:if></td>--%>
                <td class="label"><tags:label key="MES.CHL.120" required="${fn:escapeXml(!readonly)}"/></td>
                <td class="text">
                    <s:textfield name="shopForm.name" id="shopForm.name" maxlength="1000"  cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
                <%--<td class="label">Kênh cha <s:if test="#request.readonly == false"> (<font color="red">*</font>)</s:if></td>--%>
                <td class="label"><tags:label key="MES.CHL.121" required="${fn:escapeXml(!readonly)}"/></td>
                <td class="text">
                    <%--<s:select name="shopForm.parentShopId"
                              id="shopForm.parentShopId"
                              cssClass="txtInputFull"
                              disabled="#request.readonly"
                              headerKey=""
                              headerValue="--Chọn kênh cha--"
                              list="%{#request.listParentShop != null ? #request.listParentShop :#{}}"
                              listKey="shopId" listValue="name"/>--%>
                    <tags:imSelect name="shopForm.parentShopId"
                                   id="shopForm.parentShopId"
                                   cssClass="txtInputFull"
                                   disabled="${fn:escapeXml(requestScope.readonly)}"
                                   headerKey=""
                                   headerValue="COM.CHL.008"
                                   list="listParentShop"
                                   listKey="shopId" listValue="name"/>
                </td>

            </tr>
            <tr>
                <%--<td class="label">Loại kênh <s:if test="#request.readonly == false"> (<font color="red">*</font>)</s:if></td>--%>
                <td class="label"><tags:label key="MES.CHL.122" required="${fn:escapeXml(!readonly)}"/></td>
                <td class="text">
                    <%--<s:select name="shopForm.channelTypeId"
                              id="shopForm.channelTypeId"
                              cssClass="txtInputFull"
                              disabled="#request.readonly"
                              headerKey=""
                              headerValue="--Chọn loại kênh--"
                              list="%{#request.listChannelType != null ? #request.listChannelType :#{}}"
                              listKey="channelTypeId" listValue="name"/>--%>
                    <tags:imSelect name="shopForm.channelTypeId"
                                   id="shopForm.channelTypeId"
                                   cssClass="txtInputFull"
                                   disabled="${fn:escapeXml(requestScope.readonly)}"
                                   headerKey=""
                                   headerValue="COM.CHL.007"
                                   list="listChannelType"
                                   listKey="channelTypeId" listValue="name"
                                   onchange="changeChannelType(this)"/>
                </td>
                <%--<td class="label">Chính sách giá <s:if test="#request.readonly == false"> (<font color="red">*</font>)</s:if></td>--%>
                <td class="label"><tags:label key="MES.CHL.093" required="${fn:escapeXml(!readonly)}"/></td>
                <td class="text">
                    <%--<s:select name="shopForm.pricePolicy"
                              id="shopForm.pricePolicy"
                              cssClass="txtInputFull"
                              disabled="#request.readonly"
                              headerKey=""
                              headerValue="--Chọn chính sách giá--"
                              list="%{#request.listPricePolicy != null ? #request.listPricePolicy : #{}}"
                              listKey="id.code" listValue="name"/>--%>
                    <tags:imSelect name="shopForm.pricePolicy"
                                   id="shopForm.pricePolicy"
                                   cssClass="txtInputFull"
                                   disabled="${fn:escapeXml(requestScope.readonly)}"
                                   headerKey=""
                                   headerValue="COM.CHL.005"
                                   list="listPricePolicy"
                                   listKey="code" listValue="name"/>
                </td>
                <%--<td class="label">Chính sách CK <s:if test="#request.readonly == false"> (<font color="red">*</font>)</s:if></td>--%>
                <td class="label"><tags:label key="MES.CHL.094" required="${fn:escapeXml(!readonly)}"/></td>
                <td class="text">
                    <%--<s:select name="shopForm.discountPolicy"
                              id="shopForm.discountPolicy"
                              cssClass="txtInputFull"
                              disabled="#request.readonly"
                              headerKey=""
                              headerValue="--Chọn chính sách chiết khấu--"
                              list="%{#request.listDiscountPolicy != null ? #request.listDiscountPolicy : #{}}"
                              listKey="id.code" listValue="name"/>--%>
                    <tags:imSelect name="shopForm.discountPolicy"
                                   id="shopForm.discountPolicy"
                                   cssClass="txtInputFull"
                                   disabled="${fn:escapeXml(requestScope.readonly)}"
                                   headerKey=""
                                   headerValue="COM.CHL.006"
                                   list="listDiscountPolicy"
                                   listKey="code" listValue="name"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Mã số thuế</td>--%>
                <td><tags:label key="MES.CHL.123"/></td>
                <td class="text">
                    <s:textfield name="shopForm.tin" id="shopForm.tin" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
                <%--<td class="label">Tài khoản</td>--%>
                <td><tags:label key="MES.CHL.124"/></td>
                <td class="text">
                    <s:textfield name="shopForm.account" id="shopForm.account" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
                <%--<td class="label">Ngân hàng</td>--%>
                <td><tags:label key="MES.CHL.125"/></td>
                <td class="text">
                    <s:textfield name="shopForm.bankName" id="shopForm.bankName" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
            </tr>

            <tr>
                <%--<td class="label">Người đại diện</td>--%>
                <td><tags:label key="MES.CHL.126"/></td>
                <td class="text">
                    <s:textfield name="shopForm.contactName" id="shopForm.contactName" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
                <%--<td class="label">Chức vụ</td>--%>
                <td><tags:label key="MES.CHL.127"/></td>
                <td class="text">
                    <s:textfield name="shopForm.contactTitle" id="shopForm.contactTitle" maxlength="30" cssClass="txtInputFull" readonly="#request.readonly"/>
                    <%--s:select name="shopForm.contactTitle"
                              id="shopForm.contactTitle"
                              cssClass="txtInputFull"
                              disabled="#request.readonly"
                              headerKey="" headerValue="--Chọn chức vụ--"
                              list="%{#request.listStaffType != null ? #request.listStaffType : #{}}"
                              listKey="id.code" listValue="name"/--%>
                </td>
                <%--<td class="label">Email</td>--%>
                <td><tags:label key="MES.CHL.128"/></td>
                <td class="text">
                    <s:textfield name="shopForm.email" id="shopForm.email" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Điện thoại</td>--%>
                <td><tags:label key="MES.CHL.095"/></td>
                <td class="text">
                    <s:textfield name="shopForm.tel" id="shopForm.tel" maxlength="15" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
                <%--<td class="label">Fax</td>--%>
                <td><tags:label key="MES.CHL.096"/></td>
                <td class="text">
                    <s:textfield name="shopForm.fax" id="shopForm.fax" maxlength="15" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
                <%--<td class="label">Trạng thái <s:if test="#request.readonly == false"> (<font color="red">*</font>)</s:if></td>--%>
                <td class="label"><tags:label key="MES.CHL.060" required="${fn:escapeXml(!readonly)}"/></td>
                <td class="text">
                    <%--<s:select name="shopForm.status" id="shopForm.status"
                              cssClass="txtInputFull"
                              disabled="#request.readonly"
                              list="#{'1':'Có hiệu lực','0':'Không hiệu lực'}"
                              headerKey="" headerValue="--Chọn trạng thái--"/>--%>
                    <tags:imSelect name="shopForm.status" id="shopForm.status"
                                   cssClass="txtInputFull"
                                   disabled="${fn:escapeXml(requestScope.readonly)}"
                                   headerKey="" headerValue="COM.CHL.012"
                                   list="1:MES.CHL.129,0:MES.CHL.130"
                                   />
                </td>
            </tr>
            <tr>
                <%--<td class="label">Tỉnh</td>--%>
                <td><tags:label key="MES.CHL.131" required="true"/></td>
                <td class="text">
                    <%--<s:select name="shopForm.province"
                              id="shopForm.province"
                              cssClass="txtInputFull"
                              disabled="#request.readonly"
                              headerKey="" headerValue="--Chọn tỉnh--"
                              list="%{#request.listProvince != null ? #request.listProvince : #{}}"
                              listKey="areaCode" listValue="name"/>--%>
                    <tags:imSelect name="shopForm.province"
                                   id="shopForm.province"
                                   cssClass="txtInputFull"
                                   disabled="${fn:escapeXml(requestScope.readonly)}"
                                   headerKey="" headerValue="COM.CHL.009"
                                   list="listProvince"
                                   listKey="areaCode" listValue="name"/>
                </td>
                <%--<td class="label">Địa chỉ</td>--%>
                <td><tags:label key="MES.CHL.070"/></td>
                <td class="text" colspan="3" >
                    <s:textfield name="shopForm.address" id="shopForm.address"  maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
            </tr>
            <!--    loint commend        -->
            <tr>
                <%--<td class="label">Điện thoại</td>--%>
                <td class="label"><tags:label key="L.200054"/></td>
                <td class="text">
                    <%--s:textfield name="shopForm.TotalMaxDebit" id="shopForm.TotalMaxDebit" maxlength="15" cssClass="txtInputFull" readonly="#request.readonly" cssStyle="text-align:right;"  /--%>
                    <s:textfield name="shopForm.TotalMaxDebitDisplay" id="shopForm.TotalMaxDebitDisplay" maxlength="15" cssClass="txtInputFull" readonly="#request.readonly" cssStyle="text-align:right;"  />
                </td>
                <%--<td class="label">Fax</td>--%>
                <td class="label"><tags:label key="L.200055"/></td>
                <td class="text">
                    <%--s:textfield name="shopForm.TotalCurrentDebit" id="shopForm.TotalCurrentDebit" maxlength="15" cssClass="txtInputFull" readonly="#request.readonly" cssStyle="text-align:right" /--%>
                    <s:textfield name="shopForm.TotalCurrentDebitDisplay" id="shopForm.TotalCurrentDebitDisplay" maxlength="15" cssClass="txtInputFull" readonly="#request.readonly" cssStyle="text-align:right" />
                </td>
                <td class="label"><tags:label key="L.100024"/></td>
                <td>
                    <input type="button" onclick="prepareDetailsDebit();"  value="${fn:escapeXml(imDef:imGetText('...'))}"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Mô tả</td>--%>
                <td><tags:label key="MES.CHL.132"/></td>
                <td colspan="5">
                    <s:textarea name="shopForm.description" id="shopForm.description" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
            </tr>
            <%--<tr>
                <td class="label">Mã CTV/ĐB</td>
                <td class="text">
                    <s:textfield  name="shopForm.staffCodeSearch" id="staffCodeSearch" maxlength="15" cssClass="txtInputFull"/>

                </td>
                <td>
                    <tags:submit id="searchButton" confirm="false" formId="listSearchForm"
                                 showLoadingText="true" targets="divListStaff" value="Tìm kiếm" preAction="channelAction!searchListStaff.do"/>
                </td>
            </tr>--%>


        </table>



        <!-- phan nut tac vu -->
        <s:if test="#request.readonly == false">
            <div align="center" style="vertical-align:top; padding-top:10px;">
                <sx:submit parseContent="true" executeScripts="true"
                           formId="shopForm" loadingText="Processing..."
                           cssStyle="width:80px;" 
                           showLoadingText="true" targets="divDisplayInfo"
                           key="MES.CHL.097"  beforeNotifyTopics="channelAction/addOrEditShop"/>

                <sx:submit parseContent="true" executeScripts="true"
                           formId="shopForm" loadingText="Processing..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="divDisplayInfo"
                           key="MES.CHL.073"  beforeNotifyTopics="channelAction/displayShop"/>

            </div>

            <script language="javascript">
                textFieldNF($('quantity'));
                //lang nghe, xu ly su kien khi click nut "Dong y"
                dojo.event.topic.subscribe("channelAction/addOrEditShop", function(event, widget){
                    
                    
                    var shopId = document.getElementById('shopID').value;                    
                <%--if (!confirm("Bạn có chắc chắn muốn thực hiện không?")){--%>
                        var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MES.CHL.133')"/>');
                        if (!confirm(strConfirm)){
                            event.cancel = true ;
                        }
                        widget.href = "${contextPath}/channelAction!addOrEditShop.do?shopID="+shopId+"&"+ token.getTokenParamString();
                    });
            
                
                    //lang nghe, xu ly su kien khi click nut "Bo qua"
                    dojo.event.topic.subscribe("channelAction/displayShop", function(event, widget){
                        var shopId = document.getElementById('shopID').value;
                        widget.href = "${contextPath}/channelAction!displayShop.do?shopID="+shopId+"&"+ token.getTokenParamString();
                <%--setAction(widget,'divDisplayInfo','channelAction!displayShop.do');--%>
                    });
                

                    //ham kiem tra cac truong bat buoc
                    checkRequiredFields = function() {
                        //truong ma mat hang
                        if(trim($('stockModelForm.stockModelCode').value) == "") {
                <%--$('message').innerHTML = "Trường mã mặt hàng không được để trống";--%>
                            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.035')"/>';
                            $('stockModelForm.stockModelCode').select();
                            $('stockModelForm.stockModelCode').focus();
                            return false;
                        }
                        //truong ten mat hang
                        if(trim($('stockModelForm.name').value) == "") {
                <%--$('message').innerHTML = "Trường tên mặt hàng không được để trống";--%>
                            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.036')"/>';
                            $('stockModelForm.name').select();
                            $('stockModelForm.name').focus();
                            return false;
                        }
                        //truong dich vu vien thong
                        if(trim($('stockModelForm.telecomServiceId').value) == "") {
                <%--$('message').innerHTML = "Chọn dịch vụ viễn thông";--%>
                            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.037')"/>';
                            $('stockModelForm.telecomServiceId').focus();
                            return false;
                        }
                        //truong don vi
                        if(trim($('stockModelForm.unit').value) == "") {
                <%--$('message').innerHTML = "Chọn đơn vị tính";--%>
                            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.038')"/>';
                            $('stockModelForm.unit').focus();
                            return false;
                        }

                        return true;
                    }

                    //kiem tra ma khong duoc chua cac ky tu dac biet
                    checkValidFields = function() {
                        if(!isValidInput($('stockModelForm.stockModelCode').value, false, false)) {
                <%--$('message').innerHTML = "Trường mã mặt hàng chỉ được chứa các ký tự A-Z, a-z, 0-9, _";--%>
                            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.039')"/>';
                            $('stockModelForm.stockModelCode').select();
                            $('stockModelForm.stockModelCode').focus();
                            return false;
                        }
                        return true;
                    }

            </script>
        </s:if>
        <s:else>
            <div align="center" style="vertical-align:top; padding-top:10px;">
                <div align="center" style="padding-bottom:5px; padding-top:10px;">
                    <%--<input type="button" value="Thêm" style="width:80px;" onclick="prepareAddShop()">--%>
                    <input type="button" style="width:80px;" onclick="prepareAddShop();"  value="${fn:escapeXml(imDef:imGetText('MES.CHL.098'))}"
                           ${fn:escapeXml(sessionScope.addNewShop)} 
                           />
                    <%--<input type="button" value="Sửa" style="width:80px;" onclick="prepareEditShop()">--%>
                    <input ${fn:escapeXml(sessionScope.modifyShop)} type="button" style="width:80px;" onclick="prepareEditShop();"  value="${fn:escapeXml(imDef:imGetText('MES.CHL.062'))}"/>

                    <%--<sx:submit parseContent="true" executeScripts="true"
                               formId="shopForm" loadingText="Đang thực hiện..."
                               cssStyle="width:80px;"
                               showLoadingText="true" targets="divDisplayInfo"
                               value="Xóa"  beforeNotifyTopics="channelAction/deleteShop"/>--%>
                    <%--<input type="button" value="Thêm NV" style="width:80px;" onclick="prepareAddStaff()">--%>
                    <input ${fn:escapeXml(sessionScope.addNewStaff)} type="button" style="width:80px;" onclick="prepareAddStaff();"  value="${fn:escapeXml(imDef:imGetText('MES.CHL.134'))}"/>
                </div>
            </div>

            <script language="javascript">
            
                dojo.event.topic.subscribe("channelAction/deleteShop", function(event, widget){
                    var shopId = document.getElementById('shopID').value;
                <%--if (!confirm("Bạn có chắc chắn muốn xóa kênh này không?")){--%>
                        var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MES.CHL.135')"/>');
                        if (!confirm(strConfirm)){
                            event.cancel = true ;
                        }
                        widget.href = "${contextPath}/channelAction!deleteShop.do?shopID="+shopId+"&"+ token.getTokenParamString();
                    });

            </script>
        </s:else>

        <!-- danh sach tat ca cac nhan vien thuoc shop -->
        <div>
            <sx:div id="divListStaff">
                <jsp:include page="listStaffs.jsp"/>
            </sx:div>
        </div>
    </s:form>    

</tags:imPanel>


<script language="javascript">       

    textFieldNF($('shopForm.TotalMaxDebit'));
    textFieldNF($('shopForm.TotalCurrentDebit'));
    //
    prepareAddStaff = function() {
        openDialog("${contextPath}/channelAction!prepareAddStaff.do?"+ token.getTokenParamString(), 750, 550,true);
    }
    prepareDetailsDebit = function() {
        openDialog("${contextPath}/channelAction!prepareDetailsDebit.do?"+ token.getTokenParamString(), 750, 550,true);
    }
  
    //
    prepareAddStaff = function() {
        openDialog("${contextPath}/channelAction!prepareAddStaff.do?"+ token.getTokenParamString(), 1100, 650,true);
    }
    
    //
    refreshListStaff = function() {
        gotoAction("divListStaff", '${contextPath}/channelAction!refreshListStaff.do?'+ token.getTokenParamString());
    }


    prepareAddShop = function() {
        gotoAction("divDisplayInfo", "${contextPath}/channelAction!prepareAddShop.do?"+ token.getTokenParamString());
    }

    prepareEditShop = function() {
        var shopId = document.getElementById('shopID').value;

        gotoAction("divDisplayInfo", "${contextPath}/channelAction!prepareEditShop.do?shopID="+shopId+"&"+ token.getTokenParamString());
    }
    
    prepareDelShop = function() {
        var shopId = document.getElementById('shopID').value;
    <%--var isOk = confirm("Bạn có chắc chắn muốn xóa kênh này không?");--%>
    <%--if(!isOk){--%>
            if (!confirm('<s:property escapeJavaScript="true"  value="getError('MES.CHL.135')"/>')){
        
                return false;
            }
            gotoAction("divDisplayInfo", "${contextPath}/channelAction!deleteShop.do?shopID="+shopId+"&"+ token.getTokenParamString());
        }
        
        
        
        changeChannelType = function(channelTypeId){
            if (channelTypeId.value == 8){
                $('shopForm.shopCode').disabled = false;
            }else{
                $('shopForm.shopCode').disabled = true;
            }
        }
</script>


