<%-- 
    Document   : OnStaff
    Created on : Sep 21, 2010, 3:34:39 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@page  import="com.viettel.im.database.BO.UserToken" %>


<%
            UserToken userToken = (UserToken) request.getSession().getAttribute("userToken");
            String shopName = userToken.getShopName();
            Long shopId = userToken.getShopId();
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("shopName", shopName);

%>
<s:form action="channelChangeStaffAction!prapareOnStaff" theme="simple" method="post" id="channelForm">
<s:token/>


    <tags:imPanel title="MES.CHL.157">
        <table class="inputTbl8Col">
            <tr>
                <td class="label"><tags:label key="MSG.shop.code" required="true" /> </td>
                <td colspan="3">
                    <tags:imSearch codeVariable="channelForm.shopCode" nameVariable="channelForm.shopName"
                                   codeLabel="MES.CHL.025" nameLabel="MES.CHL.026"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListShop"
                                   elementNeedClearContent="channelForm.staffCode;channelForm.staffName"
                                   getNameMethod="getNameShop"
                                   roleList="onStafff9Shop"/>
                </td>
                <td class="label"><tags:label key="MSG.staff.code" required="true" /> </td>
                <td colspan="3">
                    <tags:imSearch codeVariable="channelForm.staffCode" nameVariable="channelForm.staffName"
                                   codeLabel="MES.CHL.105" nameLabel="MES.CHL.106"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListStaff"
                                   otherParam="channelForm.shopCode"
                                   getNameMethod="getNameStaff"
                                   roleList=""/>
                </td>
            </tr>
        </table>
        <%--<div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <s:radio name="channelForm.changeType" id ="changeType"
                     list="#{'1':'Chỉ chuyển nhân viên', '2':'Chuyển cả ĐB/NVĐB'}"/>
            <tags:imRadio name="channelForm.changeType" id="changeType"
                          list="1:MES.CHL.114,2:MES.CHL.115"/>
        </div>--%>
        <div align="center" style="vertical-align:top; padding-top:10px;">
            <tags:submit formId="channelForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MES.CHL.158"
                         confirm="true"
                         confirmText="MES.CHL.159"
                         validateFunction="checkStaff()"
                         preAction="channelChangeStaffAction!onStaff.do"/>
        </div>
    </tags:imPanel>
    <div>
        <tags:displayResult id="displayResultMsgClient" property="messageParam" propertyValue="paramMsg" type="key" />
    </div>    
</s:form>
<script type="text/javascript" language="javascript">
    checkStaff = function(){
        $( 'displayResultMsgClient' ).innerHTML ='';
        var staffCode = document.getElementById("channelForm.staffCode");
        if (trim(staffCode.value).length ==0){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.138')"/>';
            staffCode.focus();
            return false;
        }
        return true;
    }
</script>
