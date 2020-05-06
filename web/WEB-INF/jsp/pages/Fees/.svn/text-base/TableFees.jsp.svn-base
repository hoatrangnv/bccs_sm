<%-- 
    Document   : TableFees
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv1
--%>

<%--
    Note: Bảng tính phí bán hàng
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

<s:form action="commTableFeesAction" id="tableFeesForm" method="post" theme="simple">
<s:token/>

    <tags:imPanel title="Thông tin đối tượng tính phí">
    
    
        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam"/>
        </div>
        
        <!-- thong tin doi tuong tinh phi -->
        <table class="inputTbl6Col">
            <tr>
                <td class="label">Loại đối tượng (<font color="red">*</font>)</td>
                <td class="text">
                    <s:select name="tableFeesForm.objectCode"  id="objectCode"
                              cssClass="txtInputFull"
                              list="%{#request.lstChannelType != null ? #request.lstChannelType:#{}}"
                              listKey="channelTypeId" listValue="name" headerKey="" headerValue="--Chọn loại đối tượng--"/>
                </td>
                <td class="label">Mã đối tượng (<font color="red">*</font>)</td>
                <td class="text">
                    <sx:autocompleter name="tableFeesForm.shopCode" id = "shopCode"
                                      cssClass="txtInputFull" 
                                      href="commTableFeesAction!getShopCode.do"
                                      loadOnTextChange="true" loadMinimumCount="2"
                                      formId="tableFeesForm"
                                      valueNotifyTopics="commTableFeesAction/displayShopName"/>
                </td>
                <td class="label">Tên đối tượng</td>
                <td class="text">
                    <s:textfield name="tableFeesForm.shopName" id="shopName" readonly="true" maxlength="80"  cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td class="label">Chu kỳ tính (<font color="red">*</font>)</td>
                <td class="text">
                    <tags:dateChooser property="tableFeesForm.billcycle" styleClass="txtInputFull"/>
                </td>
                <td class="label">Kiểu báo cáo (<font color="red">*</font>)</td>
                <td class="text">
                    <s:select name="tableFeesForm.reportType" id="tableFeesForm.reportType"
                              cssClass="txtInputFull"
                              disabled="#request.readonly"
                              list="#{'1':'Báo cáo hoa hồng','2':'Báo cáo chế tài'}"
                              headerKey="" headerValue="--Chọn tất cả--"/>
                </td>
            </tr>
        </table>
        
        <!-- phan nut tac vu -->
        <div align="center" style="padding-bottom:5px; ">
            <sx:submit  parseContent="true" executeScripts="true"
                        formId="tableFeesForm" loadingText="Đang thực hiện..."
                        showLoadingText="true" targets="bodyContent"
                        cssStyle="width:90px; "
                        value="Xuất báo cáo"  beforeNotifyTopics="commTableFeesAction/export"/>
        </div>
        
        <!-- phan link download bao cao -->
        <div align="center">
            <s:if test="tableFeesForm.exportUrl !=null && tableFeesForm.exportUrl!=''">
                <script>
                    window.open('${contextPath}<s:property escapeJavaScript="true"  value="tableFeesForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                </script>
                <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="tableFeesForm.exportUrl"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về</a></div>
            </s:if>
        </div>
        
    </tags:imPanel>
    
</s:form>    

<script type="text/javascript">
    
    //
    dojo.event.topic.subscribe("commTableFeesAction/displayShopName", function(value, key, text, widget){
        $('shopName').value = key;
    });
    
    //
    dojo.event.topic.subscribe("commTableFeesAction/export", function(event, widget){
        widget.href = "commTableFeesAction!export.do?"+ token.getTokenParamString();
    });
    
    </script>


