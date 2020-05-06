<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : Report Bill
    Created on : 17/04/2009, 10:51:45 AM
    Author     :
    desc       : tra cuu hoa don
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>


<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("lstStatus", request.getSession().getAttribute("lstStatus"));
%>

<tags:imPanel title="title.approveRequestLockUser.page">
    <s:form action="approveRequestLockUserAction" method="POST" id="reqCardNotSaleFrom" theme="simple">
        <s:token/>
        
        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
        </div>
        <table class="inputTbl6Col">
            <tr>
                <td class="label"><tags:label key="MES.CHL.105" required="true" /> </td>
                <td colspan="2">
                    <tags:imSearch codeVariable="reqCardNotSaleFrom.staffCode" nameVariable="reqCardNotSaleFrom.staffName"
                                   codeLabel="MES.CHL.105" nameLabel="MES.CHL.106"
                                   searchClassName="com.viettel.im.database.DAO.ApproveRequestLockUserDAO"
                                   searchMethodName="getListStaffCode"
                                   getNameMethod="getListStaffCode"
                                   roleList=""/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="lbl.ma_yeu_cau"/></td>
                <td colspan="3">
                    <s:textfield name="reqCardNotSaleFrom.reqCode" id="reqCardNotSaleFrom.reqCode" readonly ="false" styleClass="txtInputFull" theme="simple"  maxlength="100" />
                </td>
                <td><tags:label key="MSG.generates.status" /></td>
                <td>
                    <tags:imSelect name="reqCardNotSaleFrom.status" theme="simple"
                                   id="status"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.GOD.189"
                                   list="   0:MSG.Not.Approved.Request,
                                   1:MSG.Approved.Request"/>
                </td>
            </tr>
            <tr>
                <td  class="label"> <tags:label key="MSG.order.date.from"/></td>
                <td colspan="3" >
                    <tags:dateChooser property="reqCardNotSaleFrom.fromDate" id="fromDateB"  />
                </td>
                <td  class="label"> <tags:label key="MSG.order.date.to"/></td>
                <td>
                    <tags:dateChooser property="reqCardNotSaleFrom.toDate" styleClass="txtInputFull" id="toDateB" />
                </td>
            </tr>
            <tr>
                <td colspan="6">
                    <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
                </td>
            </tr>
        </table>

        <br />
        <div style="width:100%" align="center">
            <tags:submit targets="bodyContent" formId="reqCardNotSaleFrom"
                         value="MSG.find"
                         cssStyle="width:80px;"
                         preAction="approveRequestLockUserAction!searchRequestLock.do"
                         showLoadingText="true"
                         validateFunction="validateForm()"/>
        </div>
         
        <div>
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.RequestCardNotSale.list'))}</legend>
                <sx:div id="ResultSearch">
                    <jsp:include page="ListRequestCardNotSale.jsp"/>
                </sx:div>
            </fieldset>
        </div>
        
        <br>
    </s:form>
</tags:imPanel>
        
        
<script type="text/javascript" language="javascript">

    validateForm = function(){
        
        var staffCode = document.getElementById("reqCardNotSaleFrom.staffCode");
        var fromDate = document.getElementById("reqCardNotSaleFrom.fromDate");
        var toDate = document.getElementById("reqCardNotSaleFrom.toDate");
            if (trim(staffCode.value).length == 0){
                <%--"Bạn chưa mã nhân viên"--%>
                $('message').innerHTML = '<s:property value="getText('err.input.staff.code')"/>';
                staffCode.focus();
                return false;
            }
                
        return true;
    }


</script>


