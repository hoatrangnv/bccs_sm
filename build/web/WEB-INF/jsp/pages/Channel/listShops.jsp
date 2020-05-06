<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listShops
    Created on : Apr 19, 2009, 3:33:50 PM
    Author     : Doan Thanh 8
Modified by NamNX
--%>

<%--
    Notes   : tim kiem cac cac shop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listShops", request.getSession().getAttribute("listShops"));
            request.setAttribute("listChannelType", request.getSession().getAttribute("listChannelType"));
            request.setAttribute("listPricePolicy", request.getSession().getAttribute("listPricePolicy"));
            request.setAttribute("listDiscountPolicy", request.getSession().getAttribute("listDiscountPolicy"));
            request.setAttribute("listProvince", request.getSession().getAttribute("listProvince"));
%>


<tags:imPanel title="MSG.sale.channel.search">
    <!-- hien thi message -->
    <div>
        <tags:displayResult id="displayResultMsgClient" property="message" propertyValue="paramMsg" type="key" />
    </div>
    <s:form action="channelAction" theme="simple" enctype="multipart/form-data"  method="post" id="shopForm">
<s:token/>

        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <%--<s:radio name="shopForm.searchType" id ="searchType"
                     list="#{'1':'Kênh bán hàng', '2':'Kênh nhân viên'}"/>--%>
            <tags:imRadio name="shopForm.searchType" id="searchType"
                          list="1:MES.CHL.102,2:MES.CHL.103 "
                          onchange="radioClick(this.value)" />
        </div>        
        <table class="inputTbl6Col">
            <tr>
                <td class="label"><tags:label key="MSG.channel.code" /></td>
                <td class="text">
                    <s:textfield name="shopForm.shopCode" id="shopForm.shopCode"  maxlength="1000" cssClass="txtInputFull" readonly="false"/>
                </td>
                <td class="label"><tags:label key="MSG.channel.name" /></td>
                <td class="text">
                    <s:textfield name="shopForm.name" id="shopForm.name" maxlength="1000"  cssClass="txtInputFull" readonly="false"/>
                </td>
                <td class="label"><tags:label key="MSG.chanel.type" /></td>
                <td>
                    <tags:imSelect name="shopForm.channelTypeId"
                                   id="shopForm.channelTypeId"
                                   cssClass="txtInputFull"
                                   disabled="false"
                                   headerKey=""                                    
                                   headerValue="COM.CHL.007"                                   
                                   list="listChannelType"
                                   listKey="channelTypeId" listValue="name"
                                   />
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.policy.price" /></td>
                <td class="text">
                    <%--<s:select name="shopForm.pricePolicy"
                              id="shopForm.pricePolicy"
                              cssClass="txtInputFull"
                              disabled="false"
                              headerKey=""
                              headerValue="--Chọn chính sách giá--"
                              list="%{#session.listPricePolicy != null ? #session.listPricePolicy : #{}}"
                              listKey="id.code" listValue="name"/>--%>
                    <tags:imSelect name="shopForm.pricePolicy"
                                   id="shopForm.pricePolicy"
                                   cssClass="txtInputFull"
                                   disabled="false"
                                   headerKey=""
                                   headerValue="COM.CHL.005"
                                   list="listPricePolicy"
                                   listKey="code" listValue="name"/>
                </td>
                <td class="label"><tags:label key="MSG.policy.discount" /></td>
                <td class="text">
                    <%--<s:select name="shopForm.discountPolicy"
                              id="shopForm.discountPolicy"
                              cssClass="txtInputFull"
                              disabled="false"
                              headerKey=""
                              headerValue="--Chọn chính sách chiết khấu--"
                              list="%{#session.listDiscountPolicy != null ? #session.listDiscountPolicy : #{}}"
                              listKey="id.code" listValue="name"/>--%>
                     <tags:imSelect name="shopForm.discountPolicy"
                                   id="shopForm.discountPolicy"
                                   cssClass="txtInputFull"
                                   disabled="false"
                                   headerKey=""
                                   headerValue="COM.CHL.006"
                                   list="listDiscountPolicy"
                                   listKey="code" listValue="name"/>
                </td>
                <td class="label"><tags:label key="MSG.generates.province" required="false"/></td>
                <td>
                    <%--<s:select name="shopForm.province"
                              id="shopForm.province"
                              cssClass="txtInputFull"                              
                              disabled="#request.readonly}"
                              headerKey="" headerValue="--Chọn tỉnh--"
                              list="%{#session.listProvince != null ? #session.listProvince : #{}}"
                              listKey="areaCode" listValue="name"/>--%>
                    <tags:imSelect name="shopForm.province"
                                   id="shopForm.province"
                                   cssClass="txtInputFull"
                                   disabled="${fn:escapeXml(requestScope.readonly)}"
                                   headerKey="" headerValue="COM.CHL.009"
                                   list="listProvince"
                                   listKey="areaCode" listValue="name"/>
                </td>
            </tr>
            <tr id="trSearchVSA">
                <td class="label"><tags:label key="MSG.VSA.FullName" /></td>
                <td class="text">
                    <s:textfield name="shopForm.contactName" id="shopForm.contactName"  maxlength="100" cssClass="txtInputFull" readonly="false"/>
                </td>
                <td class="label"><tags:label key="MSG.email" /></td>
                <td class="text">
                    <s:textfield name="shopForm.email" id="shopForm.email"  maxlength="100" cssClass="txtInputFull" readonly="false"/>
                </td>
                <td class="label"><tags:label key="MES.CHL.095" /></td>
                <td class="text">
                    <s:textfield name="shopForm.telNumber" id="shopForm.telNumber" maxlength="20"  cssClass="txtInputFull" readonly="false"/>
                </td>
            </tr>
          <s:if test="#request.roleLimitCreater == 'BR_LIMIT_CREATER'">
            <tr id="trSearchDebit">
            </tr>
            <tr id="trSearchDebit">
                <td class="label">File Import</td>
                <td id="tagsImFileUpload" class="text" colspan="2"  >
                                <tags:imFileUpload name="shopForm.imageUrl" 
                                                   id="imageUrl" 
                                                   serverFileName="shopForm.imageUrl"
                                                   cssStyle="width:400px;"/>
                </td>
                <td class="text" colspan="2"  > 
                     <tags:submit formId="shopForm"
                         showLoadingText="true"
                         cssStyle="width:120px;"
                         targets="divDisplayInfo"
                         value="Create limit by file"
                         preAction="channelAction!createDebitByFile.do"/>
                      <a href="/SM/share/pattern/TeamplateDebitStaff.xls">Download template </a>
                </td>
            </tr>
          </s:if> 
        </table>
        <div align="center" style="vertical-align:top; padding-top:10px;">
            <tags:submit formId="shopForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="divDisplayInfo"
                         value="MSG.find"
                         preAction="channelAction!searchShop.do"/>

            <%--<sx:submit  parseContent="true" executeScripts="true"
                        formId="shopForm" loadingText="Đang thực hiện..."
                        showLoadingText="true" targets="divDisplayInfo"
                        cssStyle="width:80px;"
                        value="Tìm kiếm"  beforeNotifyTopics="channelAction/searchShop"/>--%>
        </div>
    </s:form>
</tags:imPanel>
<br/>
<sx:div id="resultArea">
    <jsp:include page="shopResult.jsp"></jsp:include>
</sx:div>
<script language="javascript">
    $('trSearchVSA').style.visibility = 'hidden';
    $('trSearchDebit').style.visibility  = 'hidden';
    dojo.event.topic.subscribe("channelAction/searchShop", function(event, widget) {
        widget.href = "channelAction!searchShop.do?" + token.getTokenParamString();
        //event: set event.cancel = true, to cancel request
    });
    radioClick = function(value) {

        if (value == 2) {
            $('trSearchVSA').style.visibility = 'visible';
            $('trSearchDebit').style.visibility = 'visible';
        } else {
            $('trSearchVSA').style.visibility = 'hidden';
            $('trSearchDebit').style.visibility = 'hidden';
        }
    }
    //bat popup sua thong tin nhan vien
                    prepareEditStaff = function(staffId) {
                        openDialog("${contextPath}/channelAction!prepareEditStaff.do?selectedStaffId=" + staffId + '&' + token.getTokenParamString(), 1100, 650, true);
                    }

</script>


