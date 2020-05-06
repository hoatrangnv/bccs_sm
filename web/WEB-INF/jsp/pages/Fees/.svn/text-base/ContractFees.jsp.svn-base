<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : ContractFees.jsp
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv1
--%>

<%--
    Note: Lap ho so thanh toan phi hoa hong
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

<s:form action="commContractFeesAction" id="contractFeesForm" method="post" theme="simple">
<s:token/>

  <tags:imPanel title="Tìm kiếm khoản mục">
    

        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam"/>
        </div>

        <!-- tim kiem khoan muc can lap ho so thanh toan phi -->
        <table class="inputTbl6Col">
            <tr>
                <td class="label">Loại kênh(<font color="red">*</font>)</td>
                <td class="text">
                    <s:select name="contractFeesForm.channelTypeId"  id="channelTypeId"
                              cssClass="txtInputFull"
                              list="%{#request.lstChannelType != null ? #request.lstChannelType : #{}}"
                              listKey="channelTypeId" listValue="name" headerKey="" headerValue="--Chọn loại kênh--"/>
                </td>
                <td class="label">Mã đối tượng(<font color="red">*</font>)</td>
                <td class="text">
                    <sx:autocompleter name="contractFeesForm.objectCode" id = "objectCode"
                                      cssClass="txtInputFull"
                                      href="commContractFeesAction!getShopCode.do"
                                      formId="contractFeesForm"
                                      loadOnTextChange="true" loadMinimumCount="2"
                                      valueNotifyTopics="commContractFeesAction/displayShopName"/>
                </td>
                <td class="label">Tên đối tượng</td>
                <td class="text">
                    <s:textfield name="contractFeesForm.objectName" id="objectName" maxlength="80"  cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td class="label">Chu kỳ tính(<font color="red">*</font>)</td>
                <td class="text">
                    <tags:dateChooser property="contractFeesForm.billCycle" styleClass="txtInputFull"/>
                </td>
            </tr>
        </table>

        <!-- phan nut tac vu -->
        <div align="center" style="padding-bottom:5px; ">
            <sx:submit  parseContent="true" executeScripts="true"
                        formId="contractFeesForm" loadingText="Đang thực hiện..."
                        showLoadingText="true" targets="bodyContent"
                        cssStyle="width:80px; "
                        value="Tìm kiếm"  beforeNotifyTopics="commContractFeesAction/searchCOMM"/>
        </div>
         <div>
        <s:if test="#request.filePath != null">
            <script>
                window.open('${contextPath}/${fn:escapeXml(filePath)}','','toolbar=yes,scrollbars=yes');
            </script>
            <a href="${contextPath}/${fn:escapeXml(filePath)}">
                <tags:displayResult id="filePath" property="reportStockTransMessage" propertyValue="reportStockTransMessageValue" type="key"/>
            </a>
        </s:if>
        <s:else>
        
        </s:else>
    </div>

    </tags:imPanel>

    <br />

    <tags:imPanel title="Danh sách khoản mục lập hồ sơ TT">
      
        <div id="lstCalulate">
            <jsp:include page="ContractFeesResult.jsp"/>
        </div>
    </tags:imPanel>

    <div align="center" style="padding-bottom:5px; ">
        <s:if test="#request.lstCalulate!=null && #request.lstCalulate.size()>0">
            <table class="inputTbl">
                <tr>
                    <td colspan="4" align="center">
                        <sx:submit  parseContent="true" executeScripts="true"
                                    formId="contractFeesForm" loadingText="Đang thực hiện..."
                                    showLoadingText="true" targets="bodyContent"
                                    cssStyle="width:80px; "
                                    value="Lập hồ sơ"  beforeNotifyTopics="commContractFeesAction/contractCOMM"/>
                    </td>
                </tr>
            </table>
        </s:if>
    </div>

</s:form>

<script type="text/javascript">
    $('channelTypeId').focus();


    //xu ly su kien hien thi shopName sau khi chon shopCode
    dojo.event.topic.subscribe("commContractFeesAction/displayShopName", function(value, key, text, widget){
        $('objectName').value = key;
    });

    //lang nghe, xu ly su kien onclick tren nut tim kiem
    dojo.event.topic.subscribe("commContractFeesAction/searchCOMM", function(event, widget){
        widget.href = "commContractFeesAction!searchCOMM.do?"+ token.getTokenParamString();
    });

    //lang nghe, xu ly su kien onclik tren nut lap ho so
    dojo.event.topic.subscribe("commContractFeesAction/contractCOMM", function(event, widget){
        widget.href = "commContractFeesAction!contractCOMM.do?"+ token.getTokenParamString();
    });
    
</script>

