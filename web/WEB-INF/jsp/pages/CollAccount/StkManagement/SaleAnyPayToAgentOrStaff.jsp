<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : SaleAnyPayToAgentOrStaff
    Created on : Dec 25, 2009, 9:06:38 AM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@page  import="com.viettel.im.database.BO.UserToken" %>

<%
            UserToken userToken = (UserToken) request.getSession().getAttribute("userToken");
            String shopCode = userToken.getShopCode();
            String shopName = userToken.getShopName();
            String staffCode = userToken.getLoginName();
            String staffName = userToken.getStaffName();
            request.setAttribute("contextPath", request.getContextPath());

            request.setAttribute("shopName", shopCode + " - " + shopName);
            request.setAttribute("staffName", staffCode + " - " + staffName);
            request.setAttribute("listReason", request.getSession().getAttribute("listReason"));
%>

<s:form action="SaleAnyPayToAgentOrStaffAction" theme="simple" method="post" id="saleToAnyPayForm">
<s:token/>

    <tags:imPanel title="MSG.SIK.165">
        <table class="inputTbl4Col">
            <tr>
                <td colspan="2">
                    <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
                    <s:hidden name="saleToAnyPayForm.acountAnyPayId" id="acountAnyPayId"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Loại đối tượng(<font color="red">*</font>)
                </td>
                <td class="text">
                    <s:select name="saleToAnyPayForm.channelTypeId"
                              id="channelTypeId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="--Chọn loại đối tượng--"
                              list="%{#session.channelTypeList != null?#session.channelTypeList:#{}}"
                              listKey="channelTypeId" listValue="name"
                              onchange=""/>                    
                </td>--%>
                <%--<td>Người mua (<font color="red">*</font>)</td>--%>
                <td ><tags:label key="MSG.SIK.166" required="true" /></td>
                <td colspan="3">
                    <tags:imSearch codeVariable="saleToAnyPayForm.receiverCode" nameVariable="saleToAnyPayForm.receiverName"
                                   codeLabel="MSG.SIK.167" nameLabel="MSG.SIK.168"
                                   searchClassName="com.viettel.im.database.DAO.SaleAnyPayToAgentOrStaffDAO"
                                   searchMethodName="getListShopOrStaff"
                                   otherParam=""
                                   getNameMethod="getNameShopOrStaff"/>
                </td>                

            </tr>            
            <tr>
                <%--<td class="label">Lý do bán (<font color="red">*</font>)</td>--%>
                <td ><tags:label key="MSG.SIK.169" required="true" /></td>
                <td class="text">
                    <%--<s:select name="saleToAnyPayForm.reasonId"
                              id="reasonId"
                              cssClass="txtInputFull"                              
                              list="%{#session.listReason != null?#session.listReason:#{}}"
                              listKey="reasonId" listValue="reasonName"
                              onchange=""/>--%>
                    <tags:imSelect name="saleToAnyPayForm.reasonId"
                                   id="reasonId" headerKey="" headerValue=""
                                   cssClass="txtInputFull"                                   
                                   list="listReason"
                                   listKey="reasonId" listValue="reasonName"/>

                </td>
                <%--<td class="label">Ngày bán</td>--%>
                <td ><tags:label key="MSG.SIK.170"/></td>
                <td class="text">
                    <tags:dateChooser property="saleToAnyPayForm.saleTransDate"  id="saleTransDate"  styleClass="txtInput"  insertFrame="false"
                                      readOnly="true"/>
                </td>                
            </tr>

            <tr>
                <%--<td class="label">Đơn vị bán</td>--%>
                <td ><tags:label key="MSG.SIK.171"/></td>
                <td class="text">
                    <input type="text" value="${fn:escapeXml(shopName)}" class="txtInputFull" readonly="true"/>
                </td>
                <%--<td class="label">Nhân viên bán</td>--%>
                <td ><tags:label key="MSG.SIK.172"/></td>
                <td class="text">
                    <input type="text" value="${fn:escapeXml(staffName)}" class="txtInputFull" readonly="true"/>
                </td>
            </tr>            
            <tr>
<!--                <td class="label">Số lượng <font color="red">(*)</font> </td>-->
                <td ><tags:label key="MSG.SIK.173" required="true"/></td>
                <td width="text">
                    <s:textfield name="saleToAnyPayForm.itemQuantity" id="itemQuantity" theme="simple" maxlength="15" cssClass="txtInputFull"   readonly="false" cssStyle="width:80%;" onkeyup="textFieldNF(this)" />                    
                    <tags:submit targets="bodyContent" formId="saleToAnyPayForm"
                                 value="MSG.SIK.174"
                                 cssStyle="width:80px;"
                                 preAction="SaleAnyPayToAgentOrStaffAction!sumAmount.do"
                                 showLoadingText="true" validateFunction="validateSaleToAccountAnyPay();"/>                                 
                </td>
            </tr>
            <tr>
                <%--<td class="label">Tổng tiền chưa thuế</td>--%>
                <td ><tags:label key="MSG.SIK.175"/></td>
                <td class="text">
                    <s:textfield name="saleToAnyPayForm.amountNotTaxMoney" id="amountNotTaxMoney" theme="simple" maxlength="1000" cssClass="txtInputFull"   readonly="true"/>
                </td>
                <%--<td class="label">Tiền thuế</td>--%>
                <td ><tags:label key="MSG.SIK.176"/></td>
                <td class="text">
                    <s:textfield name="saleToAnyPayForm.taxMoney" id="taxMoney" theme="simple" maxlength="1000" cssClass="txtInputFull"   readonly="true"/>
                </td>
            </tr>            
            <tr>
                <%--<td class="label">Tổng tiền chiết khấu</td>--%>
                <td ><tags:label key="MSG.SIK.177"/></td>
                <td class="text">
                    <s:textfield name="saleToAnyPayForm.discountMoney" id="discountMoney" theme="simple" maxlength="1000" cssClass="txtInputFull"   readonly="true"/>
                </td>
                <%--<td class="label">Tổng tiền phải trả</td>--%>
                <td ><tags:label key="MSG.SIK.178"/></td>
                <td class="text">
                    <s:textfield name="saleToAnyPayForm.amountTaxMoney" id="amountTaxMoney" theme="simple" maxlength="1000" cssClass="txtInputFull"   readonly="true"/>
                </td>
            </tr>            
            <s:hidden name="saleToAnyPayForm.receiverId" id="receiverId"/>
            <s:hidden name="saleToAnyPayForm.receiverType" id="receiverType"/>

            <s:hidden name="saleToAnyPayForm.stockModelId" id="stockModelId"/>
            <s:hidden name="saleToAnyPayForm.itemPriceId" id="itemPriceId"/>
        </table>
        <br/>
        <div style="width:100%" align="center">
            <tags:submit targets="bodyContent" formId="saleToAnyPayForm"
                         value="MSG.SIK.179"
                         cssStyle="width:80px;"
                         confirm="MSG.SIK.180"
                         preAction="SaleAnyPayToAgentOrStaffAction!saleToAccountAnyPay.do"
                         showLoadingText="true" validateFunction="validateSaleToAccountAnyPay();"/>
        </div>
    </tags:imPanel>
</s:form>

<script type="text/javascript" language="javascript">    
    <%--$('channelTypeId').focus();--%>
        $('saleToAnyPayForm.receiverCode').focus();
        textFieldNF($('itemQuantity'));
    
        sumAmount =function(){
            if (validateSaleToAccountAnyPay()){
                document.getElementById("saleToAnyPayForm").action="SaleToAnyPayAction!sumAmount.do?"+ token.getTokenParamString();
                document.getElementById("saleToAnyPayForm").submit();
            }
        }
    
        submitForm =function(){
            if (validateSaleToAccountAnyPay()){
                if (!confirm("Bạn có chắc chắn muốn tạo giao dịch?")){
                    return;
                }
                document.getElementById("saleToAnyPayForm").action="SaleToAnyPayAction!saleToAccountAnyPay.do?"+ token.getTokenParamString();
                document.getElementById("saleToAnyPayForm").submit();
            }
        }
    
        validateSaleToAccountAnyPay = function(){
    <%--var channelType = $("channelTypeId");
    if(channelType.value==null || channelType.value ==''){
        $('returnMsgClient').innerHTML="!!! Chưa chọn loại đối tượng"
        $('channelTypeId').focus();
        return false;
    }        --%>
        
            var receiverCode = $("saleToAnyPayForm.receiverCode");
            if(receiverCode.value==null || receiverCode.value ==''){
    <%--$('returnMsgClient').innerHTML="!!! Chưa chọn người mua"--%>
                $('returnMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.041')"/>';
                $('saleToAnyPayForm.receiverCode').focus();
                return false;
            }
            var reasonId = $("reasonId");
            if(reasonId.value==null || reasonId.value ==''){
    <%--$('returnMsgClient').innerHTML="!!! Chưa chọn lý do"--%>
                $('returnMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.042')"/>';
                $('reasonId').focus();
                return false;
            }
        
            var temp = $('itemQuantity').value.replace(/\,/g,""); //loai bo dau , trong chuoi
            if(temp==null || trim(temp) ==''){
    <%--$('returnMsgClient').innerHTML="!!! Chưa nhập số lượng"--%>
                $('returnMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.043')"/>';
                $('itemQuantity').focus();
                return false;
            }
        
            if (!isNumberFormat(trim(temp))){
    <%--$('returnMsgClient').innerHTML="!!! Số lượng không đúng định dạng"--%>
                $('returnMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.044')"/>';
                $('itemQuantity').focus();
                return false;
            }
            $('itemQuantity').value = trim(temp);

            
            return true;
        }
    
</script>
