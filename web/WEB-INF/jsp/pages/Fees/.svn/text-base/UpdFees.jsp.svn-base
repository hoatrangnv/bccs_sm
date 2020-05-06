<%-- 
    Document   : UpdFees
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv1
--%>

<%--
    Note: Nhap tay khoan muc phi hoa hong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>


<tags:imPanel title="Thông tin nhập, điều chỉnh khoản mục">
    <!-- phan hien thi message -->
    <div align="center">
        <tags:displayResult id="message" property="message" propertyValue="messageParam"/>
    </div>

    <!-- phan hien thi thong tin ve dieu chinh khoan muc -->
    <s:form action="commUpdFeesAction" id="updFeesForm" method="post" theme="simple">
<s:token/>

        <s:hidden name="updFeesForm.commTransId" />

        <table class="inputTbl6Col">
            <tr>
                <td class="label">Loại đối tượng (<font color="red">*</font>)</td>
                <td class="text">
                    <s:select name="updFeesForm.payTypeCode" id="payTypeCode"
                              cssClass="txtInputFull"
                              list="%{#request.lstChannelType != null ? #request.lstChannelType : #{}}"
                              listKey="channelTypeId" listValue="name" headerKey="" headerValue="--Chọn loại đối tượng--"/>
                </td>
                <td class="label">Mã đối tượng (<font color="red">*</font>)</td>
                <td class="text">
                    <sx:autocompleter name="updFeesForm.shopCode" id="shopCode"
                                      cssClass="txtInputFull"
                                      href="commUpdFeesAction!getShopCode.do"
                                      formId="updFeesForm"
                                      loadOnTextChange="true" loadMinimumCount="2"
                                      valueNotifyTopics="commUpdFeesAction/displayShopName"/>
                </td>
                <td class="label">Tên đối tượng</td>
                <td class="text">
                    <s:textfield name="updFeesForm.shopName" id="shopName" readonly="true" maxlength="80"  cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td class="label">Kiểu dữ liệu nhập (<font color="red">*</font>)</td>
                <td class="text">
                    <s:select name="updFeesForm.inputType" id="updFeesForm.inputType"
                              cssClass="txtInputFull"
                              list="#{'2':'Nhập tay số lượng','3':'Nhập tay số tiền'}"
                              headerKey="" headerValue="--Chọn kiểu dữ liệu nhập--"
                              onchange="changeInputType(this.value)"/>
                </td>
                <td class="label">Khoản mục (<font color="red">*</font>)</td>
                <td class="text">
                    <s:select name="updFeesForm.itemId" id="itemId" theme="simple"
                              cssClass="txtInputFull"
                              list="%{#request.lstItem != null ? #request.lstItem : #{}}"
                              listKey="itemId" listValue="itemName"
                              headerKey="" headerValue="--Chọn khoản mục--"/>
                </td>
                <td class="label">Số lượng/Số tiền (<font color="red">*</font>)</td>
                <td class="text">
                    <s:textfield name="updFeesForm.count" id="count" onkeyup="textFieldNF(this)" maxlength="10" theme="simple" cssClass="txtInputFull" disabled="#request.valueQty"/>
                </td>
            </tr>
            <tr>
                <td class="label">Chu kỳ tính (<font color="red">*</font>)</td>
                <td class="text">
                    <tags:dateChooser property="updFeesForm.itemDate" styleClass="txtInputFull"/>
                </td>
            </tr>
        </table>
    </s:form>
    <br/>
    <!-- phan nut tac vu -->
    <div align="center" style="padding-bottom:5px; ">
        <s:if test="#request.upFeeMode == 'prepareEdit'">
            <sx:submit parseContent="true" executeScripts="true"
                       formId="updFeesForm" loadingText="Đang thực hiện..." showLoadingText="false"
                       targets="bodyContent" value="Đồng ý"  beforeNotifyTopics="commUpdFeesAction/updateCOMM"
                       errorNotifyTopics="errorAction" cssStyle="width:80px; "/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="updFeesForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px; "
                       value="Bỏ qua"  beforeNotifyTopics="commUpdFeesAction/cancelUpdateCOMM"/>
        </s:if>
        <s:else>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="updFeesForm" loadingText="Đang thực hiện..." showLoadingText="false"
                       targets="bodyContent"  value="Thêm mới"  beforeNotifyTopics="commUpdFeesAction/addCOMM"
                       errorNotifyTopics="errorAction" cssStyle="width:80px; "/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="updFeesForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px; "
                       value="Tìm kiếm"  beforeNotifyTopics="commUpdFeesAction/searchCOMM"/>
        </s:else>
    </div>
</tags:imPanel>
<tags:imPanel title="Danh sách nhập, điều chỉnh khoản mục">
    <div id="lstCalulate">
        <jsp:include page="UpdFeesResult.jsp"/>
    </div>
</tags:imPanel>

<sx:div id="ListItemFees">
    <jsp:include page="addItemFees.jsp" flush="true"/>
</sx:div>



<script type="text/javascript">
    //focus() vao control dau tien
    $('payTypeCode').focus();

    textFieldNF($('count'));

    //lang nghe, xu ly su kien nhap them khoan muc moi
    dojo.event.topic.subscribe("commUpdFeesAction/addCOMM", function(event, widget){
        if (confirm("Bạn có chắc chắn muốn thêm mới khoản mục nhập tay?")){
            widget.href = "commUpdFeesAction!addCOMM.do?"+ token.getTokenParamString();
        }
        else
            event.cancel = true;
            
    });

    //lang nghe xu ly su kien tim kiem khoan muc
    dojo.event.topic.subscribe("commUpdFeesAction/searchCOMM", function(event, widget){
        widget.href = "commUpdFeesAction!searchCOMM.do?"+ token.getTokenParamString();
    });

    //lang nghe, xu ly su kien dong y
    dojo.event.topic.subscribe("commUpdFeesAction/updateCOMM", function(event, widget){
        if (confirm("Bạn có chắc chắn muốn sửa khoản mục nhập tay?")){
            widget.href = "commUpdFeesAction!updateCOMM.do?"+ token.getTokenParamString();
        }
        else
            event.cancel = true;
    });

    //lang nghe, xu ly su kien bo qua
    dojo.event.topic.subscribe("commUpdFeesAction/cancelUpdateCOMM", function(event, widget){
        widget.href = "commUpdFeesAction!cancelUpdateCOMM.do?"+ token.getTokenParamString();
    });

    

    //xu ly su kien thay doi kieu nhap lieu
    changeInputType = function(inputType) {
        updateData("${contextPath}/commUpdFeesAction!updateListItem.do?target=itemId&inputType=" + inputType+"&"+ token.getTokenParamString());
    }

    //cap nhat shopName sau khi chon autotcompleter
    dojo.event.topic.subscribe("commUpdFeesAction/displayShopName", function(value, key, text, widget){
        $('shopName').value = key;
    });

</script>
