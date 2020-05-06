
<%-- 
    Document   : CalculateFees
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv1
--%>

<%--
    Note: tinh phi hoa hong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags" %>



<%
        if (request.getAttribute("lstChannelType") == null) {
            request.setAttribute("lstChannelType", request.getSession().getAttribute("lstChannelType"));
        }

        request.setAttribute("contextPath", request.getContextPath());
%>
<s:form action="commCalculateAction" id="calculateFeesForm" method="post" theme="simple">
<s:token/>

    <fieldset style="width:95%; padding:10px 10px 10px 10px">
        <legend class="transparent">Tìm kiếm khoản mục tính hoa hồng</legend>
        <table class="inputTbl">
            <tr>
                <td>Đối tượng(<font color="red">*</font>)</td>
                <td>
                    <s:select name="calculateFeesForm.payTypeCode"  id="payTypeCode"
                              cssClass="txtInput"
                              list="%{#session.lstChannelType != null?#session.lstChannelType:#{}}"
                              listKey="channelTypeId" listValue="name" headerKey="" headerValue="-- Chọn đối tượng --"/>
                </td>
                <td>Chu kỳ tính(<font color="red">*</font>)</td>
                <td>
                    <tags:dateChooser property="calculateFeesForm.billcycle" styleClass="txtInput"/>

                </td>
            </tr>
            <tr>
                <td>Mã đối tượng(<font color="red">*</font>)</td>
                <td>
                    <sx:autocompleter name="calculateFeesForm.shopCode" id = "shopCode" cssClass="txtInput" href="getShopCode.do?autoName=calculateFeesForm.shopCode" loadOnTextChange="true" loadMinimumCount="2" valueNotifyTopics="commCalculateAction/displayShopName"/>
                </td>
                <td>Tên đối tượng</td>
                <td>
                    <s:textfield   name="calculateFeesForm.shopName" id="shopName" maxlength="80"  cssClass="txtInput"/>
                </td>
            </tr>

            <!--tr>
                <td align="left">Tiêu chí tổng hợp hoa hồng</td>
                <td align="left">

                    <!_-s:select name="calculateFeesForm.criterion" id="criterion"
                              cssClass="txtInput"
                              list="'#'{''1'':''Tổng hợp theo số lượng','2':'Tổng hợp theo tiền'}'"
                              headerKey="" headerValue="--Tiêu chí tổng hợp hoa hồng--"/>
                </td>
            </tr-->
            <s:if test="Approve">
                <tr>
                    <td>Trạng thái(<font color="red">*</font>)</td>
                    <td>
                        <s:select name="state" id="state"
                                  cssClass="txtInput"
                                  list="#{'1':'Chưa duyệt','2':'Đã duyệt'}"
                                  headerKey="" headerValue="--Trạng thái--"/>

                    </td>
                </tr>
            </s:if>
            <tr>
                <td colspan="4" align="center">
                    <sx:bind id="updateContentIsdn" indicator="overlay" events="onclick" listenTopics="updateContent" targets="bodyContent" separateScripts="true" executeScripts="true"/>
                    <br />
                    <!--sx:submit  parseContent="true" executeScripts="true"
                                formId="calculateFeesForm" loadingText="Äang thá»±c hiá»‡n..."
                                showLoadingText="true" targets="bodyContent"
                                value="Tá»•ng há»£p"  beforeNotifyTopics="commCalculateAction/searchCOMM"/>
                    &nbsp;&nbsp;&nbsp;
                    <!--sx:submit  parseContent="true" executeScripts="true"
                                formId="calculateFeesForm" loadingText="Äang thá»±c hiá»‡n..."
                                showLoadingText="true" targets="bodyContent"
                                value="Khá»Ÿi táº¡o"  beforeNotifyTopics="commCalculateAction/refesh"/>
                    &nbsp;&nbsp;&nbsp;-->

                    <sx:submit  parseContent="true" executeScripts="true"
                                formId="calculateFeesForm" loadingText="Đang thực hiện..."
                                showLoadingText="true" targets="bodyContent"
                                value="Tổng hợp"  beforeNotifyTopics="commCalculateAction/searchCOMM"/>
                    &nbsp;&nbsp;&nbsp;
                    <sx:submit  parseContent="true" executeScripts="true"
                                formId="calculateFeesForm" loadingText="Đang thực hiện..."
                                showLoadingText="true" targets="bodyContent"
                                value="Khởi tạo"  beforeNotifyTopics="commCalculateAction/resetForm"/>
                    &nbsp;&nbsp;&nbsp;
                </td>
            </tr>
        </table>
    </fieldset>
    <div style="color:red" align="center">
        <s:property escapeJavaScript="true"  value="#request.message" />
    </div>
    <div id="lstCalulateUp">
        <jsp:include page="CalculateFeesResult.jsp"/>
    </div>
    <br/>
    <s:if test="#request.lstCalulateUp!=null && #request.lstCalulateUp.size()>0">
        <fieldset style="width:95%; padding:10px 10px 10px 10px">
            <table style="width:100%" cellpadding="0" cellspacing="4" border="0">
                <tr>
                    <td colspan="4" align="center">
                        <s:hidden name="calculateFeesForm.arrCOMMTRANSID" id="calculateFeesForm.arrCOMMTRANSID"/>
                        <sx:submit  parseContent="true" executeScripts="true"
                                    formId="calculateFeesForm" loadingText="Đang thực hiện..."
                                    showLoadingText="true" targets="bodyContent"
                                    value="Cập nhật hoa hồng"  beforeNotifyTopics="commCalculateAction/updateCOMM"/>
                        &nbsp;&nbsp;&nbsp;
                    </td>
                </tr>

            </table>
        </fieldset>
    </s:if>
</s:form>
<script type="text/javascript">
    dojo.event.topic.subscribe("commCalculateAction/displayShopName", function(value, key, text, widget){
        if(key!=null){
            getInputText("getShopNameTextCal.do?shopCode="+key);
        }else{
            $('shopName').value="";
        }
        //event: set event.cancel = true, to cancel request
    });
    dojo.event.topic.subscribe("commCalculateAction/resetForm", function(event, widget){
        widget.href = "commCalculateAction!resetForm.do";
        //event: set event.cancel = true, to cancel request
    });
    dojo.event.topic.subscribe("commCalculateAction/searchCOMM", function(event, widget){
        widget.href = "commCalculateAction!searchCOMM.do";
        //event: set event.cancel = true, to cancel request
    });
    dojo.event.topic.subscribe("commCalculateAction/updateCOMM", function(event, widget){
        widget.href = "commCalculateAction!updateCOMM.do?"+token.getTokenParamString();
        //event: set event.cancel = true, to cancel request
    });
    dojo.event.topic.subscribe("commCalculateAction/refesh", function(event, widget){
        widget.href = "commCalculateAction!refesh.do";
        //event: set event.cancel = true, to cancel request
    });
</script>
