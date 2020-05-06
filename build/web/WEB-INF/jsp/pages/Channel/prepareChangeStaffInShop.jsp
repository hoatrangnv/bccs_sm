<%--
    Document   : saleTransManagment.jsp
    Quan ly giao dich ban hang
    Created on : 10/06/2009
    Author     : ThanhNC
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
<s:form action="channelChangeStaffAction!changeStaffInShop" theme="simple" method="post" id="channelForm">
<s:token/>


    <tags:imPanel title="MSG.move.staff.to.shop">
        <table class="inputTbl8Col">
            <tr>
                <td class="label"><tags:label key="MSG.shop.old.code" required="true" /> </td>
                <td colspan="3">
                    <tags:imSearch codeVariable="channelForm.shopCode" nameVariable="channelForm.shopName"
                                   codeLabel="MES.CHL.025" nameLabel="MES.CHL.026"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListShop"
                                   elementNeedClearContent="channelForm.staffCode;channelForm.staffName"
                                   getNameMethod="getNameShop"
                                   roleList="saleTransManagementf9Shop"/>
<!--                                   roleList="changeStaffInShopf9Shop"/>-->
                </td>
                <td class="label"><tags:label key="MSG.staff.code" required="true" /> </td>
                <td colspan="3">
                    <tags:imSearch codeVariable="channelForm.staffCode" nameVariable="channelForm.staffName"
                                   codeLabel="MES.CHL.105" nameLabel="MES.CHL.106"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListStaff"
                                   otherParam="channelForm.shopCode"
                                   getNameMethod="getNameStaff"
                                   roleList="saleTransManagementf9Staff"/>
<!--                                   roleList="changeStaffInShopf9Staff"/>-->
                </td>
            </tr>

            <tr>
                <td class="label"><tags:label key="MSG.shop.moved.code" required="true" /> </td>
                <td colspan="3">
                    <tags:imSearch codeVariable="channelForm.newShopCode" nameVariable="channelForm.newShopName"
                                   codeLabel="MES.CHL.112" nameLabel="MES.CHL.113"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListShop"
                                   elementNeedClearContent=""
                                   getNameMethod="getNameShop"
                                   roleList=""/>
                </td>
            </tr>

        </table>
        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <%--<s:radio name="channelForm.changeType" id ="changeType"
                     list="#{'1':'Chỉ chuyển nhân viên', '2':'Chuyển cả ĐB/NVĐB'}"/>--%>
            <tags:imRadio name="channelForm.changeType" id="changeType"
                          list="1:MES.CHL.114,2:MES.CHL.115"/>
        </div>                    
        <div align="center" style="vertical-align:top; padding-top:10px;">
            <td colspan="8" align="center">
                <sx:submit parseContent="true" executeScripts="true"
                           formId="channelForm" loadingText="Processing..."
                           showLoadingText="true" targets="bodyContent"
                           cssStyle="width:90px"
                           key="MES.CHL.116" beforeNotifyTopics="channelChangeStaffAction/changeStaffInShop"/>
            </td>
        </div>
    </tags:imPanel>
    <tags:displayResult id="resultViewChangeStaffInShopClient" property="resultViewChangeStaffInShop" type="key"/>
</s:form>
<script type="text/javascript" language="javascript">
    dojo.event.topic.subscribe("channelChangeStaffAction/changeStaffInShop", function(event, widget){
    <%--if (!confirm("Bạn có chắc chắn muốn chuyển nhân viên đến cửa hàng mới không ?")){--%>

            if (!confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MES.CHL.117')"/>'))){
                event.cancel = true ;
            }        
            widget.href = 'channelChangeStaffAction!changeStaffInShop.do?'+  token.getTokenParamString();
        });

</script>
