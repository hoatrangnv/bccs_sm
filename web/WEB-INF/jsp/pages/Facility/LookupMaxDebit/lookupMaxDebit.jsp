<%--
    Document   : addNewSaleChannelType
    Created on : Jun 13, 2009, 8:39:06 AM
    Author     : NamNX
--%>
<%--
    Note: danh muc loai kenh ban hang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@ taglib uri="VTdisplaytaglib" prefix="display"%>

<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>


<s:form action="saleChannelTypeAction" theme="simple" enctype="multipart/form-data"  method="post" id="saleChannelTypeForm">
<s:token/>

    <tags:imPanel title="MSG.channel.info">
        <s:hidden name="saleChannelTypeForm.channelTypeId" id="saleChannelTypeForm.channelTypeId"/>
        <table class="inputTbl6Col" style="width:100%" >
            <tr>
                <td class="text" colspan="6" align="center">
                    <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="paramMsg" type="key" />
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.chanel.type.name" required="true" /> </td>
                <td class="text">
                    <s:textfield name="saleChannelTypeForm.code" id="saleChannelTypeForm.code" maxlength="80" cssClass="txtInputFull" maxLength="50"/>
                </td>
                <td class="label"><tags:label key="MSG.chanel.type.name" required="true" /> </td>
                <td class="text">
                    <s:textfield name="saleChannelTypeForm.name" id="saleChannelTypeForm.name" maxlength="80"  cssClass="txtInputFull" maxLength="50"/>
                </td>
            </tr>
            <tr>
                <td class="label">
                    <tags:label key="MSG.chanel.group" required="false" />
                </td>
                <td class="text">
                    <%--<s:select name="%{#attr.property1 + 'objectType'}" id="%{#attr.property1 + 'objectType'}"
                              cssClass="txtInputFull" disabled="%{!#attr.toInputData}"
                              list="#{'1':'Shop',
                                      '2':'Staff'}"
                              headerKey="" headerValue="--Chọn nhóm kênh--"/>--%>
                    <tags:imSelect name="saleChannelTypeForm.objectType" id="saleChannelTypeForm.objectType"
                                   cssClass="txtInputFull" 
                                   list="1:Shop,2:Staff"
                                   headerKey="" headerValue="COM.CHL.013"/>
                </td>                
                <td class="label"></td>
                <td class="text">
                </td>
            </tr>            
        </table>

        <sx:bind id="updateContent" indicator="overlay" events="onclick" listenTopics="updateContent" targets="bodyContent" separateScripts="true" executeScripts="true"/>
        <br />
        <tags:submit formId="saleChannelTypeForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MSG.find"
                     preAction="lookupMaxDebitAction!searchChannel.do"/>
        <script type="text/javascript">
            //ham phuc vu viec phan trang
            pageNavigator = function (ajaxTagId, pageNavigator, pageNumber){
                dojo.widget.byId("updateContent").href = 'saleChannelTypeAction!pageNavigator.do?' + pageNavigator + "=" + pageNumber;
                dojo.event.topic.publish('updateContent');
            }

            validateForm=function(){
                $('displayResultMsgClient' ).innerHTML = "";
                if($('saleChannelTypeForm.name').value ==null || $('saleChannelTypeForm.name').value.trim() == ""){
            <%--$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Bạn chưa nhập tên loại kênh";--%>
                        $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.CHL.120"/>';
                        $('saleChannelTypeForm.name').focus();

                        return false;
                    }
                    if (isHtmlTagFormat(trim($('saleChannelTypeForm.name').value))){
            <%--$('displayResultMsg').innerHTML="Tên loại kênh không được nhập định dạng thẻ HTML"--%>
                        $('displayResultMsgClient').innerHTML= '<s:text name="ERR.CHL.122"/>';
                        $('saleChannelTypeForm.name').focus();
                        return false;
                    }
                    if($('saleChannelTypeForm.status').value ==null || $('saleChannelTypeForm.status').value.trim() == ""){
            <%--$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Bạn chưa chọn trạng thái";--%>
                        $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.CHL.121"/>';
                        $('saleChannelTypeForm.status').focus();

                        return false;
                    }
                    if (isHtmlTagFormat(trim($('saleChannelTypeForm.stockReportTemplate').value))){
            <%--$('displayResultMsg').innerHTML="Mẫu phiếu xuất kho không được nhập định dạng thẻ HTML"--%>
                        $('displayResultMsgClient').innerHTML= '<s:text name="ERR.CHL.123"/>';
                        $('saleChannelTypeForm.stockReportTemplate').focus();
                        return false;
                    }
                    return true;

                }
                //lang nghe, xu ly su kien khi click nut "Them doi tac moi"
                dojo.event.topic.subscribe("saleChannelTypeAction/addNewChannelType", function(event, widget){
                    if (!validateForm()) {
                        event.cancel = true;
                    }
                    widget.href = "saleChannelTypeAction!addNewChannelType.do";
                    //event: set event.cancel = true, to cancel request
                });

                //lang nghe, xu ly su kien khi click nut "Tim kiem"
                dojo.event.topic.subscribe("saleChannelTypeAction/searchChannelType", function(event, widget){
                    widget.href = "saleChannelTypeAction!searchChannelType.do";
                    //event: set event.cancel = true, to cancel request
                });

                //lang nghe, xu ly su kien khi click nut "Reset"
                dojo.event.topic.subscribe("saleChannelTypeAction/resetChannelType", function(event, widget){
                    document.getElementById('saleChannelTypeForm.partnerName').value="";
                    document.getElementById('saleChannelTypeForm.partnerType').value="";
                    document.getElementById('saleChannelTypeForm.status').value="";
                    document.getElementById('saleChannelTypeForm.address').value="";
                    document.getElementById('saleChannelTypeForm.phone').value="";
                    document.getElementById('saleChannelTypeForm.fax').value="";
                    document.getElementById('saleChannelTypeForm.status').value="";
                    document.getElementById('saleChannelTypeForm.contactName').value="";
                    document.getElementById('saleChannelTypeForm.staDate').value="";
                    document.getElementById('saleChannelTypeForm.endDate').value="";

                    event.cancel = true;
                    //event: set event.cancel = true, to cancel request
                });
                //lang nghe, xu ly su kien khi click nut "Đong y"
                dojo.event.topic.subscribe("saleChannelTypeAction/editChannelType", function(event, widget){
                    if (!validateForm()) {
                        event.cancel = true;
                    }
                    widget.href = "saleChannelTypeAction!editChannelType.do";
                    //event: set event.cancel = true, to cancel request
                });
                //xử lý sự kiện onclick của nut copy
                dojo.event.topic.subscribe("saleChannelTypeAction/copyChannelType", function(event, widget){
                    if(!validateForm()) {
                        event.cancel = true;
                    }
                    widget.href = "saleChannelTypeAction!copyChannelType.do";

                });
                //lang nghe, xu ly su kien khi click nut "bo qua"
                dojo.event.topic.subscribe("saleChannelTypeAction/cancel", function(event, widget){
                    widget.href = "saleChannelTypeAction!preparePage.do";
                    //event: set event.cancel = true, to cancel request
                });

                delChannelType = function(channelTypeId) {
            <%--if(confirm("Bạn có thực sự muốn xóa loại kênh này?")){--%>
                    var strConfirm = getUnicodeMsg('<s:text name="MES.CHL.151')"/>');
                    if (confirm(strConfirm)){
                        gotoAction('bodyContent','saleChannelTypeAction!deleteChannelType.do?channelTypeId=' + channelTypeId);
                    }
                }
                copyChannelType = function(channelTypeId) {
                    gotoAction('bodyContent', "saleChannelTypeAction!prepareCopyChannelType.do?channelTypeId=" + channelTypeId);
                }
        </script>
    </tags:imPanel>
</s:form>
<br/>
<div id="listChannelType">
    <jsp:include page="lookupMaxDebitResult.jsp"/>
</div>



