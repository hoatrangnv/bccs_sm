<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : viewAccountBook
    Created on : Jan 20, 2012, 4:34:10 PM
    Author     : TrongLV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%
            if (request.getAttribute("listAccountBook") == null) {
                request.setAttribute("listAccountBook", request.getSession().getAttribute("listAccountBook"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="simtkManagmentAction" theme="simple" enctype="multipart/form-data"  method="post" id="listAccountBookForm">
<s:token/>

    <s:hidden name="listAccountBookForm.accountId" id="listAccountBookForm.accountId"/>
    <div>
        <tags:displayResult id="displayResultMsgClient" property="displayResultMsgClient" type="key" />
    </div>
    <table class="inputTbl6Col">
        <sx:div id="displayParamDetail">
            <tr>
                <%--<td  class="label">Từ ngày (<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.SIK.113" required="true"/></td>
                <td  class="text">
                    <tags:dateChooser property="listAccountBookForm.fromDate" id="fromDate"/>
                </td>
                <%--<td  class="label">Đến ngày (<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.SIK.114" required="true"/></td>
                <td  class="text">
                    <tags:dateChooser property="listAccountBookForm.toDate" id="toDate"/>
                </td>
                <%--<td colspan="4">&nbsp</td>--%>
                <td>
                    <%--<input type="button" onclick="searchAccountBook();"  value="Tìm kiếm"/>--%>
                    <input type="button" onclick="searchAccountBook();"  value="${fn:escapeXml(imDef:imGetText('MSG.SIK.115'))}"/>
                </td>
                <td>
                    <%--<input type="button" onclick="reportAccountBook();"  value="Xuất ra Excel"/>--%>
                    <input type="button" onclick="reportAccountBook();"  value="${fn:escapeXml(imDef:imGetText('MSG.SIK.116'))}"/>
                </td>
            </tr>
        </sx:div>
    </table>

    <tags:imPanel title="MSG.SIK.117">
        <div>
            <tags:displayResult id="messageShow" property="messageShow" propertyValue="paramMsg" type="key" />
        </div>
        <tr>
            <td colspan="8" align="center">
                <s:if test="#request.filePath !=null && #request.filePath!=''">
                    <script>
                        window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                    </script>
                    <%--<div><a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a></div>--%>
                    <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' ><tags:label key="MSG.clickhere.to.download" /></a></div>
                </s:if>
            </td>
        </tr>
        <sx:div id="displayParamBookType">
            <display:table pagesize="10" id="listAccountBook"
                           targets="popupBody" name="listAccountBook"
                           class="dataTable"
                           requestURI="CollAccountManagmentAction!pageNextView.do"
                           cellpadding="1" cellspacing="1" >
                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.147'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center" >
                    ${fn:escapeXml(listAccountBook_rowNum)}
                </display:column>
                <%--<display:column title="Loại gd" headerClass="tct" sortable="false" style="width:60px; text-align:center">--%>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SIK.118'))}" headerClass="tct" sortable="false" style="width:60px; text-align:center">
                    <%--<s:if test="#attr.listAccountBook.requestType == 0">Khởi tạo tài khoản</s:if>--%>
                    <s:if test="#attr.listAccountBook.requestType == 0"><tags:label key="MSG.SIK.119"/></s:if>                    
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 1">Nạp tiền/Rút tiền</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 1"><tags:label key="MSG.SIK.120"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 2">Đặt cọc/Thu hồi</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 2"><tags:label key="MSG.SIK.121"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 3">Hoàn tiền đặt cọc đấu nối</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 3"><tags:label key="MSG.SIK.122"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 4">Phí đấu nối/làm DV qua SĐN/Web</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 4"><tags:label key="MSG.SIK.123"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 5">Tiền hoa hồng đấu nối</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 5"><tags:label key="MSG.SIK.124"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 6">Tiền thưởng kích hoạt</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 6"><tags:label key="MSG.SIK.125"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 7">Thanh toán tín dụng</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 7"><tags:label key="MSG.SIK.126"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 8">Hoàn tiền khi NVQL lập hóa đơn</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 8"><tags:label key="MSG.RET.008"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 9">Hủy phiếu thu chi</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 9"><tags:label key="MSG.SIK.128"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 11">VAS dùng</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 11"><tags:label key="MSG.SIK.129"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 12">Sửa sai hình thức hòa mạng bên CM</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 12"><tags:label key="MSG.SIK.130"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 13">Cho vay tín dụng</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 13"><tags:label key="MSG.SIK.131"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 17">Tiền thưởng kích hoạt tự động</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 17"><tags:label key="MSG.SIK.132"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 22">Cong tien dat coc ban phat</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 22"><tags:label key="L.200036"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 23">Tru tien dat coc ban phat</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 23"><tags:label key="L.200037"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 50">Cong tien dat coc ban thay</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 50"><tags:label key="L.200038"/></s:if>
                    </s:else>
                    <s:else>
                        <%--<s:if test="#attr.listAccountBook.requestType == 51">Tru tien dat coc ban thay</s:if>--%>
                        <s:if test="#attr.listAccountBook.requestType == 51"><tags:label key="L.200039"/></s:if>
                    </s:else>
                </display:column>

                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.133'))}" property="openingBalance" style="text-align:right" headerClass="tct" format=" {0} "/>
                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.134'))}" property="amountRequest" style="text-align:right" headerClass="tct" format=" {0} "/>
                <%--<display:column title="Tiền tín dụng" property="debitRequest" style="text-align:right" headerClass="tct" format=" {0} "/>--%>
                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.135'))}" property="closingBalance" style="text-align:right" headerClass="tct" format=" {0} "/>

                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SIK.133'))}" headerClass="tct" sortable="false" style="width:60px; text-align:center">
                    <s:if test="#attr.listAccountBook.status == 1"><tags:label key="MSG.SIK.137"/></s:if>
                    <s:else>
                        <s:if test="#attr.listAccountBook.status == 2"><tags:label key="MSG.SIK.138"/></s:if>
                    </s:else>
                    <s:else>
                        <s:if test="#attr.listAccountBook.status == 3"><tags:label key="MSG.SIK.139"/></s:if>
                    </s:else>
                    <s:else>
                        <s:if test="#attr.listAccountBook.status == 4"><tags:label key="MSG.SIK.140"/></s:if>
                    </s:else>
                </display:column>

                <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.141'))}" property="requestId" style="text-align:left" headerClass="tct" />
                <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.142'))}" property="userRequest" style="text-align:left" headerClass="tct" />

                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.143'))}" property="createDate" style="text-align:center" headerClass="tct" format="{0,date,dd/MM/yyyy hh:mm:ss}"/>

                <!-- comment theo yeu cau cua VTG -->
                <%--display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.144'))}" property="returnDate" style="text-align:center" headerClass="tct" format="{0,date,dd/MM/yyyy hh:mm:ss}"/--%>


                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.145'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                    <s:if test="#attr.listAccountBook.requestType == 1 || #attr.listAccountBook.requestType == 10">
                        <s:url action="CollAccountManagmentAction!printAccountBook" id="URL1">
                            <s:param name="requestId" value="#attr.listAccountBook.requestId"/>
                        </s:url>
                        <sx:a targets="popupBody" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/accept_1.jpg" title="<s:property escapeJavaScript="true"  value="getText(MSG.SIK.145)"/>"  alt="<s:property escapeJavaScript="true"  value="getText(MSG.SIK.146)"/>"/>
                        </sx:a>
                        <%--
                        <s:url action="GetBookTypeAction!deleteParamBookType" id="URL2">
                            <s:param name= "requestId" value="#attr.listAccountBook.requestId"/>
                        </s:url>
                        <sx:a onclick="printAccountBook('%{#attr.listAccountBook.requestType}')">
                            <img src="${contextPath}/share/img/accept_1.png" title="In hóa đơn" alt="Xóa"/>
                        </sx:a>
                        --%>
                    </s:if>
                </display:column>

            </display:table>
        </sx:div>
    </tags:imPanel>
</s:form>

<script>    
    printAccountBook = function(requestId) {              
    }
    searchAccountBook = function() {
        $( 'displayResultMsgClient' ).innerHTML='';
        if (!validate()) {
            event.cancel = true;
        }
        document.getElementById("listAccountBookForm").action="CollAccountManagmentAction!searchAccountBook.do?"+ token.getTokenParamString();
        document.getElementById("listAccountBookForm").submit();
    }
    reportAccountBook = function() {
        $( 'displayResultMsgClient' ).innerHTML='';
        if (!validate()) {
            event.cancel = true;
        }
        document.getElementById("listAccountBookForm").action="CollAccountManagmentAction!reportAccountBook.do?"+ token.getTokenParamString();
        document.getElementById("listAccountBookForm").submit();
    }
    validate = function(){
        var dateExported= dojo.widget.byId("fromDate");
        if(dateExported.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập từ ngày';--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.035')"/>';
                $('fromDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Từ ngày không hợp lệ';--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.036')"/>';
                $('fromDate').focus();
                return false;
            }

            var dateExported1 = dojo.widget.byId("toDate");
            if(dateExported1.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập đến ngày';--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.037')"/>';
                $('toDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported1.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Đến ngày chưa hợp lệ';--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.038')"/>';
                $('toDate').focus();
                return false;
            }
            if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'displayResultMsgClient' ).innerHTML='Từ ngày phải nhỏ hơn đến ngày';--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.039')"/>';
                $('fromDate').focus();
                return false;
            }
            return true;
        }
    
</script>




                