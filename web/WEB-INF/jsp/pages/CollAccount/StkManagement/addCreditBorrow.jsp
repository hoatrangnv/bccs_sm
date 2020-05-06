<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : addBorrowCredit
    Created on : April 03, 2010, 9:58:00 AM
    Author     : Tuannv
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
            request.setAttribute("contextPath", request.getContextPath());
            String pageId = request.getParameter("pageId");
            request.setAttribute("typeId", request.getSession().getAttribute("typeId" + pageId));
%>
<s:form action="CollAccountManagmentAction" theme="simple" method="post" id="addCreditBorrowForm">
<s:token/>

    <input type="hidden"  name="pageId" id="pageId"/>
    <tags:imPanel title="Thông tin khởi tạo tín dụng">
        <s:hidden name="addCreditBorrowForm.accountId" id="addCreditBorrowForm.accountId"/>
        <s:hidden name="addCreditBorrowForm.collId" id="addCreditBorrowForm.collId"/>
        <s:hidden name="addCreditBorrowForm.realBalance" id="addCreditBorrowForm.realBalance"/>
        <s:hidden name="addCreditBorrowForm.realDebit" id="addCreditBorrowForm.realDebit"/>
        <s:hidden name="addCreditBorrowForm.maxDebit" id="addCreditBorrowForm.maxDebit"/>
        <s:hidden name="addCreditBorrowForm.minSum" id="addCreditBorrowForm.minSum"/>
        <s:hidden name="addCreditBorrowForm.canExc" id = "canExc"/>
        <div>
            <tags:displayResult id="messageShow" property="messageShow" propertyValue="paramMsg" type="key" />
        </div>
        <table class="inputTbl6Col" style="width:100%">
            <tr>
                <s:if test="#request.typeId ==2">
                    <%--<td class="label">Mã CTV</td>--%>
                    <td><tags:label key="MSG.SIK.092"/></td>
                </s:if>
                <s:else>
                    <%--<td class="label">Mã Đại lý</td>--%>
                    <td><tags:label key="MSG.SIK.093"/></td>
                </s:else>
                <td class="text">
                    <s:textfield name="addCreditBorrowForm.collCode" id="collCode" theme="simple" maxlength="250" cssClass="txtInputFull"
                                 readonly="true"/>
                </td>
                <s:if test="#request.typeId ==2">
                    <%--<td class="label">Tên CTV</td>--%>
                    <td><tags:label key="MSG.SIK.094"/></td>
                </s:if>
                <s:else>
                    <%--<td class="label">Tên Đại lý</td>--%>
                    <td><tags:label key="MSG.SIK.095"/></td>
                </s:else>
                <td class="text">
                    <s:textfield name="addCreditBorrowForm.namestaff" id="namestaff" theme="simple" maxlength="250" cssClass="txtInputFull"
                                 readonly="true"/>
                </td>                
            </tr>
            <tr>
                <%--<td class="label">Số tiền vay (<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.SIK.208" required="true"/></td>
                <td class="text">
                    <s:textfield name="addCreditBorrowForm.moneyBalance" id="moneyBalance" style="text-align:right" onkeyup="textFieldNF(this)" theme="simple" maxlength="20" cssClass="txtInputFull"/>
                </td>
                <%--<td class="label">Ngày cho vay</td>--%>
                <td><tags:label key="MSG.SIK.209"/></td>
                <td class="text">
                    <tags:dateChooser property="addCreditBorrowForm.createDate"  id="createDate"  styleClass="txtInput" readOnly="true"  insertFrame="false"/>
                </td>
                <%--<td class="label">Ngày hết hạn thanh toán</td>--%>
                <td><tags:label key="MSG.SIK.210"/></td>
                <td class="text">
                    <tags:dateChooser property="addCreditBorrowForm.endDate"  id="endDate"  styleClass="txtInput" readOnly="true"  insertFrame="false"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Mã cửa hàng</td>--%>
                <td><tags:label key="MSG.SIK.099"/></td>
                <td class="text">
                    <s:textfield name="addCreditBorrowForm.shopcode" id="shopcode" theme="simple" maxlength="100" cssClass="txtInputFull"
                                 readonly="true"/>
                </td>
                <%--<td class="label">Nhân viên thực hiện</td>--%>
                <td><tags:label key="MSG.SIK.100"/></td>
                <td class="text">
                    <s:textfield name="addCreditBorrowForm.nameStaffCreat" id="nameStaffCreat" theme="simple" maxlength="250" cssClass="txtInputFull"
                                 readonly="true"/>
                </td>
                <%--<td class="label">Lý do (<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.SIK.098" required="true"/></td>
                <td class="text">
                    <%--                    <s:select name="addCreditBorrowForm.reasonId"
                                                  id="reasonId"
                                                  cssClass="txtInputFull"
                                                  headerKey="" headerValue="--Chọn lý do--"
                                                  listKey="reasonId" listValue="reasonName"
                                                  list="%{#request.listReason != null ? #request.listReason : #{} }"/>--%>
                    <tags:imSelect name="addCreditBorrowForm.reasonId"
                                   id="reasonId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.SIK.253"
                                   list="listReason"
                                   listKey="reasonId" listValue="reasonName"/>
                </td>
            </tr>
        </table>
    </tags:imPanel>
    <div style="height:10px">
    </div>
    <tags:imPanel title="MSG.SIK.211">
        <table class="inputTbl6Col" style="width:100%">
            <tr>
                <%--<td class="label">Tên người bảo lãnh (<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.SIK.212" required="true"/></td>
                <td class="text">
                    <s:textfield name="addCreditBorrowForm.guarantorName" id="guarantorName" theme="simple" maxlength="50" cssClass="txtInputFull"/>
                </td>
                <%--<td class="label">Số CMT(<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.SIK.148" required="true"/></td>
                <td class="text">
                    <s:textfield name="addCreditBorrowForm.guarantorIdNo" id="guarantorIdNo" theme="simple" maxlength="20" cssClass="txtInputFull"/>
                </td>
                <%--<td class="label">Chức vụ người bảo lãnh (<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.SIK.213" required="true"/></td>
                <td class="text">
 
                    <tags:imSelect name="addCreditBorrowForm.guarantorTitleId"
                                   id="guarantorTitleId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.SIK.258"
                                   list="listPosition"
                                   listKey="code" listValue="name"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Số điện thoại</td>--%>
                <td><tags:label key="MSG.SIK.214"/></td>
                <td class="text">
                    <s:textfield name="addCreditBorrowForm.guarantorPhone" id="guarantorPhone" theme="simple" maxlength="15" cssClass="txtInputFull"/>
                </td>
                <%--<td class="label">Đơn vị bảo lãnh</td>--%>
                <td><tags:label key="MSG.SIK.215"/></td>
                <td class="text">
                    <s:textfield name="addCreditBorrowForm.guarantorDepartment" id="guarantorDepartment" theme="simple" maxlength="255" cssClass="txtInputFull"/>
                </td>
                <%--<td class="label">Số chứng từ bảo lãnh</td>--%>
                <td><tags:label key="MSG.SIK.216"/></td>
                <td class="text">
                    <s:textfield name="addCreditBorrowForm.referenceNo" id="referenceNo" theme="simple" maxlength="50" cssClass="txtInputFull"/>
                </td>
            </tr>
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
        </table>
    </tags:imPanel>
    <div style="height:10px">
    </div>

    <div style="height:10px">
    </div>

    <div align="center" style="padding-bottom:5px;">
        <s:if test="#session.print == 1">
            <%--<input type="button" onclick="addMoney();"  value="Khởi tạo tín dụng" disabled/>--%>
            <input type="button" onclick="addMoney();"  value="${fn:escapeXml(imDef:imGetText('MSG.SIK.087'))}" disabled/>
        </s:if>
        <s:else>
            <!--s:property value= "%{#request.AddCreditBorrowForm.canExc}"/-->
            <s:if test="%{#request.AddCreditBorrowForm.canExc == 1}">
                <%--<input type="button" onclick="addMoney();"  value="Khởi tạo tín dụng"/>--%>
                <input type="button" onclick="addMoney();"  value="${fn:escapeXml(imDef:imGetText('MSG.SIK.087'))}"/>
            </s:if>
            <s:else>                
                <%--<input type="button" onclick="addMoney();"  value="Khởi tạo tín dụng" disabled/>--%>
                <input type="button" onclick="addMoney();"  value="${fn:escapeXml(imDef:imGetText('MSG.SIK.087'))}" disabled/>
            </s:else>

        </s:else>
        <!--input type="button" onclick="resetForm();"  value="Reset"/-->
        <input type="button" onclick="refreshParent();"  value="Thoát"/>
        <input type="button" onclick="refreshParent();"  value="${fn:escapeXml(imDef:imGetText('MSG.SIK.103'))}"/>
    </div>

</s:form>

<script type="text/javascript" language="javascript">
    textFieldNF($('moneyBalance'));
    textFieldNF($('moneyDebit'));
    validate = function(){
        //var moneyBalance = document.getElementById("moneyBalance");
        //var moneyDebit = document.getElementById("moneyDebit");
        var moneyBalance = $('moneyBalance').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        //var moneyDebit = $('moneyDebit').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        var reasonId = document.getElementById("reasonId");
        var guarantorName= document.getElementById("guarantorName");
        var guarantorIdNo = document.getElementById("guarantorIdNo");;
        var guarantorTitleId = document.getElementById("guarantorTitleId");;
        var guarantorPhone = document.getElementById("guarantorPhone");;

        if(moneyBalance == null || trim(moneyBalance).length <= 0 ){
    <%--$('messageShow').innerHTML="Bạn chưa nhập số tiền vay"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.045')"/>';
                $('moneyBalance').focus();
                return false;
            }
            if(trim(moneyBalance) == "0" ){
    <%--$('messageShow').innerHTML="Số tiền vay thêm phải > 0"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('MSG.SIK.046')"/>';
                $('moneyBalance').focus();
                return false;
            }
            if(!isInteger( trim(moneyBalance) ) ){
    <%--$('messageShow').innerHTML="Số tiền vay thêm phải là số nguyên dương"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('MSG.SIK.047')"/>';
                $('moneyBalance').focus();
                return false;
            }
        
            if(guarantorName.value==null || guarantorName.value.trim() ==""){
    <%--$('messageShow').innerHTML="Chưa nhập tên người bảo lãnh"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('MSG.SIK.048')"/>';
                guarantorName.focus();
                return false;
            }
            if (isHtmlTagFormat(trim(guarantorName.value))){
    <%--$('messageShow').innerHTML="Tên người bảo lãnh không được nhập định dạng thẻ HTML"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('MSG.SIK.049')"/>';
                guarantorName.focus();
                return false;
            }

            if(guarantorIdNo.value==null || guarantorIdNo.value.trim() ==""){
    <%--$('messageShow').innerHTML="Chưa nhập số chứng minh thư"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('MSG.SIK.050')"/>';
                guarantorIdNo.focus();
                return false;
            }
            if (isHtmlTagFormat(trim(guarantorIdNo.value))){
    <%--$('messageShow').innerHTML="Số chứng minh thư không được nhập định dạng thẻ HTML"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('MSG.SIK.051')"/>';
                guarantorIdNo.focus();
                return false;
            }

            if(guarantorTitleId.value==null || guarantorTitleId.value ==""){
    <%--$('messageShow').innerHTML="Chưa nhập chức vụ"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('MSG.SIK.052')"/>';
                guarantorTitleId.focus();
                return false;
            }
            if (isHtmlTagFormat(trim(guarantorTitleId.value))){
    <%--$('messageShow').innerHTML="Chức vụ không được nhập định dạng thẻ HTML"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('MSG.SIK.053')"/>';
                guarantorTitleId.focus();
                return false;
            }

            if(guarantorPhone.value != null && guarantorPhone.value != '' && !isInteger( trim(guarantorPhone.value))){
    <%--$('messageShow').innerHTML="Số điện thoại phải là số"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('MSG.SIK.054')"/>';
                guarantorPhone.focus();
                return false;
            }

        
            if(reasonId.value==null || reasonId.value ==""){
    <%--$('messageShow').innerHTML="Chưa chọn lý do"--%>
                $('messageShow').innerHTML='<s:property escapeJavaScript="true"  value="getError('MSG.SIK.055')"/>';
                reasonId.focus();
                return false;
            }

            moneyBalance = trim(moneyBalance);
            $('moneyBalance').value = moneyBalance; //loai bo dau , trong chuoi
            //$('moneyDebit').value = $('moneyDebit').value.replace(/\,/g,""); //loai bo dau , trong chuoi
            return true;
        }
        addMoney = function() {
            if (!validate()) {
                event.cancel = true;
                return;
            }
            /*
        if (confirm("Are you sure you want to delete")) {
        document.location = delUrl;
        }
             */
    <%--if (!confirm("Bạn có chắc chắn muốn khởi tạo tín dụng không?")){--%>
            if(!confirm("'<s:property escapeJavaScript="true"  value="getError('MSG.SIK.217')"/>'")){
                event.cancel = true;
                return;
            }
            document.getElementById("addCreditBorrowForm").action="CollAccountManagmentAction!addBorrowCredit.do?"+ token.getTokenParamString();
            document.getElementById("addCreditBorrowForm").submit();
        }
        resetForm = function(){
            document.getElementById("addCreditBorrowForm").action="CollAccountManagmentAction!reset.do?"+ token.getTokenParamString();
            document.getElementById("addCreditBorrowForm").submit();
        }
        printReceipt = function(){
            document.getElementById("addCreditBorrowForm").action="CollAccountManagmentAction!printReceiptCredit.do?printType=1"+"&"+ token.getTokenParamString();
            document.getElementById("addCreditBorrowForm").submit();
        }
        refreshParent = function(){
            window.close();
            window.opener.updateParent();
        }
    
</script>
