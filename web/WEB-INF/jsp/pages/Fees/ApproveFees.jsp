<%-- 
    Document   : ApproveFees
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv1
--%>

<%--
    Note: Phe duyet khoan muc phi hoa hong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>


<s:form action="commApproveFeesAction" id="approveFeesForm" method="post" theme="simple">
<s:token/>

    <tags:imPanel title="Tìm kiếm khoản mục">
        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam"/>
        </div>

        <!-- phan thong tin khoan muc can phe duyet -->


        <table class="inputTbl6Col">
            <tr>
                <td class="label">Loại đối tượng (<font color="red">*</font>)</td>
                <td class="text">
                    <s:select name="approveFeesForm.payTypeCode" id="payTypeCode"
                              cssClass="txtInputFull"
                              list="%{#request.lstChannelType != null ? #request.lstChannelType : #{}}"
                              listKey="channelTypeId" listValue="name"
                              headerKey="" headerValue="--Chọn đối tượng--"/>
                </td>
                <td class="label">Mã đối tượng (<font color="red">*</font>)</td>
                <td class="text">
                    <sx:autocompleter name="approveFeesForm.shopCode" id ="shopCode"
                                      cssClass="txtInputFull"
                                      href="commApproveFeesAction!getShopCode.do"
                                      formId="approveFeesForm"
                                      loadOnTextChange="true" loadMinimumCount="2"
                                      valueNotifyTopics="commApproveFeesAction/displayShopName"/>
                </td>
                <td class="label">Tên đối tượng</td>
                <td class="text">
                    <s:textfield name="approveFeesForm.shopName" id="shopName" readonly="true" maxlength="80" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td class="label">Chu kỳ tính (<font color="red">*</font>)</td>
                <td class="text">
                    <tags:dateChooser property="approveFeesForm.billcycle" styleClass="txtInputFull"/>
                </td>
                <td class="label">Trạng thái (<font color="red">*</font>)</td>
                <td class="text">
                    <s:select name="approveFeesForm.state" id="state"
                              cssClass="txtInputFull"
                              list="#{'0':'Chưa duyệt','1':'Đã duyệt'}"
                              headerKey="" headerValue="--Chọn trạng thái--"/>
                </td>
            </tr>
        </table>
        <br />
        <!-- phan nut tac vu -->
        <div align="center" style="padding-bottom:5px; ">
            <sx:submit  parseContent="true" executeScripts="true"
                        formId="approveFeesForm" loadingText="Đang thực hiện..."
                        showLoadingText="true" targets="bodyContent"
                        cssStyle="width:80px; "
                        value="Tìm kiếm"  beforeNotifyTopics="commApproveFeesAction/searchCOMM"/>
        </div>
    </tags:imPanel>
    <tags:imPanel title="Danh sách khoản mục">
        <div id="lstCalulate">
            <jsp:include page="ApproveFeesResult.jsp"/>
        </div>
    </tags:imPanel>
</s:form>

<s:if test="#request.lstCalulate!=null && #request.lstCalulate.size()>0">
    <div align="center" style="padding-bottom:5px; padding-top:10px; ">
        <s:if test="approveFeesForm.state == 0">
            <sx:submit  parseContent="true" executeScripts="true"
                        formId="approveFeesForm" loadingText="Đang thực hiện..."
                        showLoadingText="true" targets="bodyContent"
                        cssStyle="width:80px; "
                        value="Phê duyệt"  beforeNotifyTopics="commApproveFeesAction/appCOMM"/>
        </s:if>
        <s:elseif test="approveFeesForm.state == 1">
            <sx:submit  parseContent="true" executeScripts="true"
                        formId="approveFeesForm" loadingText="Đang thực hiện..."
                        showLoadingText="true" targets="bodyContent"
                        cssStyle="width:80px; "
                        value="Hủy duyệt"  beforeNotifyTopics="commApproveFeesAction/unAppCOMM"/>
        </s:elseif>
    </div>
</s:if>

<script type="text/javascript">

    //xu ly su kien click nut tim kiem
    dojo.event.topic.subscribe("commApproveFeesAction/searchCOMM", function(event, widget){
        widget.href = "commApproveFeesAction!searchCOMM.do?"+ token.getTokenParamString();
    });

    //hien thi shopName
    dojo.event.topic.subscribe("commApproveFeesAction/displayShopName", function(value, key, text, widget){
        $('shopName').value = key;
    });

    //duyet khoan muc
    dojo.event.topic.subscribe("commApproveFeesAction/appCOMM", function(event, widget){
        if(confirm("Bạn có thực sự muốn phê duyệt?")){

            widget.href = "commApproveFeesAction!appCOMM.do?"+ token.getTokenParamString();
        } else {
            event.cancel = true;
        }


    });

    //huy duyet khoan muc
    dojo.event.topic.subscribe("commApproveFeesAction/unAppCOMM", function(event, widget){
        if(confirm("Bạn có thực sự muốn hủy duyệt?")){
            
            widget.href = "commApproveFeesAction!unAppCOMM.do?"+ token.getTokenParamString();
        } else {
            event.cancel = true;
        }

    });


</script>
