
<%-- 
    Document   : commAdjustFeesAction
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv1
--%>

<%--
    Note: Dieu chinh hoa hong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>



<%
        
        if (request.getAttribute("lstChannelType") == null) {
            request.setAttribute("lstChannelType", request.getSession().getAttribute("lstChannelType"));
        }
        request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="commAdjustFeesAction" id="adjustFeesForm" method="post" theme="simple">
<s:token/>

    <fieldset style="width:95%; padding:10px 10px 10px 10px">
        <legend class="transparent">Tìm kiếm Tổng hợp phí hoa hồng</legend>
         <table class="inputTbl">
            <tr>
                <td>Đối tượng</td>
                <td>
                    <s:select name="adjustFeesForm.payTypeCode"  id="adjustFeesForm.payTypeCode"
                              cssClass="txtInput"
                              list="%{#session.lstChannelType != null?#session.lstChannelType:#{}}"
                              listKey="channelTypeId" listValue="name" headerKey="" headerValue="-- Chọn đối tượng --"/>
                </td>
                <td>Chu kỳ tính<font color="red">*</font></td>
                <td>
                    <s:select name="adjustFeesForm.month"  id="adjustFeesForm.month"
                              cssClass="txtInput"
                              list="#{'1':'Tháng 1','2':'Tháng 2','3':'Tháng 3','4':'Tháng 4'
                              ,'5':'Tháng 5','6':'Tháng 6','7':'Tháng 7','8':'Tháng 8','9':'Tháng 9','10':'Tháng 10','11':'Tháng 11','12':'Tháng 12'}"
                              headerKey="" headerValue="--Chu kỳ tính--"/>
                </td>
            </tr>
            <tr>
                <td>Mã cửa hàng/đại lý/CTV<font color="red">*</font></td>
                <td>
                   <%-- <s:textfield name="updFeesForm.shopCode" id="updFeesForm.shopCode" maxlength="20" cssClass="txtInput"/>--%>
                    <sx:autocompleter name="adjustFeesForm.shopCode" id = "shopCode" cssClass="txtInput" href="getShopCodeADJ.do" loadOnTextChange="true" loadMinimumCount="1" valueNotifyTopics="commAdjustFeesAction/displayShopName"/>
                </td>
                <td>Tên cửa hàng/đại lý/CTV<font color="red">*</font></td>
                <td>
                    <s:textfield   name="adjustFeesForm.shopName" id="shopName" maxlength="80"  cssClass="txtInput"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <sx:bind id="updateContentIsdn" indicator="overlay" events="onclick" listenTopics="updateContent" targets="bodyContent" separateScripts="true" executeScripts="true"/>
                    <br />
                    <sx:submit  parseContent="true" executeScripts="true"
                                formId="adjustFeesForm" loadingText="Đang thực hiện..."
                                showLoadingText="true" targets="bodyContent"
                                value="Tìm kiếm"  beforeNotifyTopics="commAdjustFeesAction/searchCOMM"/>
                    &nbsp;&nbsp;&nbsp;
                    <sx:submit  parseContent="true" executeScripts="true"
                                formId="adjustFeesForm" loadingText="Đang thực hiện..."
                                showLoadingText="true" targets="bodyContent"
                                value="Khởi tạo"  onclick="formReset()"/>
                    &nbsp;&nbsp;&nbsp;
                </td>
            </tr>
        </table>
    </fieldset>
    <br>
        <div id="listfees">
            <jsp:include page="AdjustFeesResult.jsp"/>
        </div>
    </br>
    <fieldset style="width:95%; padding:10px 10px 10px 10px">
        <legend class="transparent">Điều chỉnh phí hoa hồng</legend>
        <table class="inputTbl">
            <tr>
                <td>Tổng tiền<font color="red">*</font></td>
                <td>
                    <s:textfield name="adjustFeesForm.totalMoney" id="totalMoney" maxlength="20"  value="300" cssClass="txtInput"/>
                </td>
                <td>Số tiền điều chỉnh<font color="red">*</font></td>
                <td>
                    <s:textfield name="adjustFeesForm.adjustMoney" id="adjustMoney" maxlength="20"  cssClass="txtInput"/>
                </td>
            </tr>
            <tr>
                <td>Tổng tiền sau điều chỉnh<font color="red">*</font></td>
                <td>
                    <s:textfield name="adjustFeesForm.adjustTotalMoney" id="adjustTotalMoney" maxlength="20"  cssClass="txtInput"/>
                </td>
                <td>Lý do điều chỉnh<font color="red">*</font></td>
                <td>
                    <s:select name="adjustFeesForm.reasonCode" id="reasonCode"
                              cssClass="txtInput"
                              list="#{'1':'Yêu cầu cửa hàng/đại lý/chi nhánh','2':'Khuyến khích cửa hàng/đại lý/chi nhánh'}"
                              headerKey="" headerValue="--Lý do điều chỉnh--"/>
                                  
                </td>
            </tr>
                
            <tr>
                <td colspan="4" align="center">
                    <br />
                    <sx:submit  parseContent="true" executeScripts="true"
                                formId="adjustFeesForm" loadingText="Đang thực hiện..."
                                showLoadingText="true" targets="bodyContent"
                                value="Điều chỉnh hoa hồng"  beforeNotifyTopics="commAdjustFeesAction/editFees"/>
                    &nbsp;&nbsp;&nbsp;
                    <sx:submit  parseContent="true" executeScripts="true"
                                formId="adjustFeesForm" loadingText="Đang thực hiện..."
                                showLoadingText="true" targets="bodyContent"
                                value="Reset forms"  beforeNotifyTopics="commAdjustFeesAction/resetform"/>
                </td>
            </tr>
        </table>
    </fieldset>
</s:form>

<script type="text/javascript">
    dojo.event.topic.subscribe("commAdjustFeesAction/displayShopName", function(value, key, text, widget){

        getInputText("getShopNameTextFeeADJ.do?shopCode="+key);
        //event: set event.cancel = true, to cancel request
    });
     pageNavigator = function (ajaxTagId, pageNavigator, pageNumber){
        dojo.widget.byId("updateContent").href = 'commAdjustFeesAction!pageNavigator.do?' + pageNavigator + "=" + pageNumber;
        dojo.event.topic.publish('updateContent');
    }
    var code=document.getElementById('cableBoxForm.code');
    var name=document.getElementById('cableBoxForm.name');
    var address=document.getElementById('cableBoxForm.address');
    var maxPorts=document.getElementById('cableBoxForm.maxPorts');
    var usedPorts=document.getElementById('cableBoxForm.usedPorts');
    var status=document.getElementById('cableBoxForm.status');

     checkValidate=function(){


                if (document.getElementById('cableBoxForm.name').value==""){
                    alert("Bạn phải điền tên Cáp");
                    document.getElementById('cableBoxForm.name').focus();
                    return false;
                }
                 if (document.getElementById('cableBoxForm.code').value==""){
                    alert("Bạn phải điền mã Cáp");
                    document.getElementById('cableBoxForm.code').focus();
                    return false;
                }

                if (document.getElementById('cableBoxForm.maxPorts').value==""){
                    alert("Bạn phải điền số cônge tối đa cho Cáp");
                    document.getElementById('cableBoxForm.maxPorts').focus();
                    return false;
                }
                if (document.getElementById('cableBoxForm.usedPorts').value==""){
                    alert("Bạn phải điền số cổng sử dụng cho Cáp");
                    document.getElementById('cableBoxForm.usedPorts').focus();
                    return false;
                }
                if (document.getElementById('cableBoxForm.status').value==""){
                    alert("Bạn phải điền trạng thái của Cáp");
                    document.getElementById('cableBoxForm.status').focus();
                    return false;
                }
                if (document.getElementById('cableBoxForm.address').value==""){
                    alert("Bạn phải điền địa chỉ Cáp");
                    document.getElementById('cableBoxForm.address').focus();
                    return false;
                }
         return true;
     }

    dojo.event.topic.subscribe("commAdjustFeesAction/searchFees", function(event, widget){
         if (!checkValidate()) {
          event.cancel = true;
         }
        widget.href = "commAdjustFeesAction!searchFees.do";

        //event: set event.cancel = true, to cancel request
    });
    dojo.event.topic.subscribe("commAdjustFeesAction/resetform", function(event, widget){
        if (!checkValidate()) {
          event.cancel = true;
         }
        widget.href = "commAdjustFeesAction!editCableBox.do?"+token.getTokenParamString();

    });
    dojo.event.topic.subscribe("commAdjustFeesAction/editFees", function(event, widget){
         if (!checkValidate()) {
          event.cancel = true;
         }
        widget.href = "commAdjustFeesAction!addNewCableBox.do?"+token.getTokenParamString();

    });
    dojo.event.topic.subscribe("commAdjustFeesAction/searchCOMM", function(event, widget){
        widget.href = "commAdjustFeesAction!searchCOMM.do";
        //event: set event.cancel = true, to cancel request
    });
</script>
