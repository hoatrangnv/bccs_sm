<%--
    Document   : SearchTrans.tag
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>
<%@tag description="Hien thi cac thong tin ve CommCountersForm" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ tag import="java.util.List" %>
<%@ tag import="java.util.ArrayList" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="type"%>
<%@attribute name="form" required="true"%>
<%@attribute name="target" required="true"%>
<%@attribute name="action" required="true"%>

<%
            request.setAttribute("lstSerial", request.getSession().getAttribute("lstSerial"));
            request.setAttribute("fromDate", form + ".fromDate");
            request.setAttribute("toDate", form + ".toDate");
%>

<tags:imPanel title="MSG.search.trans">
    <!--Loai action can tim kiem la phieu hay lenh-->
    <s:hidden name="%{#attr.form+'.actionType'}" />
    <s:hidden name="%{#attr.form+'.transType'}" />
    <s:hidden name="%{#attr.form+'.fromOwnerType'}" id="fromOwnerType"/>
    <s:hidden name="%{#attr.form+'.toOwnerType'}" id="toOwnerType"/>
    <table class="inputTbl4Col" >
        <tr>
            <td class="label">
                <tags:label key="MSG.GOD.183"/>
                <!--                Mã giao dịch-->
            </td>
            <td class="text">
                <s:textfield name="%{#attr.form+'.actionCode'}" id="actionCode" cssClass="txtInput"/>
                <script>$('actionCode').focus();</script>
            </td>
            <td  class="label">
                <tags:label key="MSG.GOD.013"/>
                <!--                Trạng thái-->
            </td>
            <td  class="text">

                <s:if test="#attr.type=='exp_to_senior' ">
                    <tags:imSelect name="${pageScope.form}.transStatus"
                                   id="transStatus"
                                   cssClass="txtInput"   headerKey="2" headerValue="MSG.GOD.191"
                                   list="3:MSG.GOD.194,4:MSG.GOD.195,5:MSG.GOD.196"/>
                </s:if>
                <s:if test="#attr.type=='exp' ">
                    <tags:imSelect name="${pageScope.form}.transStatus"
                                   id="transStatus"
                                   cssClass="txtInput"
                                   headerKey="" headerValue="MSG.GOD.189"
                                   list="1:MSG.GOD.190, 2:MSG.GOD.191,3:MSG.GOD.194,4:MSG.GOD.195,5:MSG.GOD.196"/>
                </s:if>
                <s:if test="#attr.type=='imp_from_exp' ||#attr.type=='imp_from_staff' || #attr.type=='staff_imp_from_shop'">

                    <tags:imSelect name="${pageScope.form}.transStatus"
                                   id="transStatus"
                                   cssClass="txtInput"
                                   headerKey="" headerValue="MSG.GOD.189"
                                   list="3:MSG.GOD.193,4:MSG.GOD.192,6:MSG.GOD.197"/>
                </s:if>
                <s:if test="#attr.type=='imp_from_senior'">
                    <tags:imSelect name="${pageScope.form}.transStatus"
                                   id="transStatus"
                                   cssClass="txtInput"                                   
                                   list="3:MSG.GOD.193,4:MSG.GOD.192"/>
                </s:if>
                <s:if test="#attr.type=='imp'">
                    <tags:imSelect name="${pageScope.form}.transStatus"
                                   id="transStatus"
                                   cssClass="txtInput"
                                   headerKey="" headerValue="MSG.GOD.189"
                                   list="1:MSG.GOD.190,2:MSG.GOD.191,3:MSG.GOD.192"/>
                </s:if>
                <s:if test="#attr.type=='realImp_from_senior'">                    

                    <tags:imSelect name="${pageScope.form}.transStatus"
                                   id="transStatus"
                                   cssClass="txtInput"
                                   headerKey="" headerValue="MSG.GOD.189"
                                   list="1:MSG.GOD.190,2:MSG.GOD.191,3:MSG.GOD.192"/>
                </s:if>
            </td>
            <!--/s:else-->
        </tr>
        <tr>
            <td  class="label">
                <tags:label key="MSG.GOD.184"/>
                <!--                Kho xuất-->
            </td>
            <td  class="text">

                <s:if test="#attr.type=='imp_from_staff'" >
                    <tags:imSelect name="${pageScope.form}.fromOwnerId"
                                   id="fromOwnerId"
                                   cssClass="txtInput"
                                   headerKey="" headerValue="MSG.GOD.188"
                                   list="lstStaff"
                                   listKey="staffId" listValue="name"/>
                </s:if>
                <s:else>

                    <s:if test="#attr.type=='exp'|| #attr.type=='exp_to_senior' ||
                          #attr.type=='imp_from_senior' || #attr.type=='staff_imp_from_shop' ||  #attr.type=='realImp_from_senior'" >
                        <s:textfield name="%{#attr.form+'.fromOwnerName'}" id="fromOwnerName" readonly="true" cssClass="txtInput"/>
                        <s:hidden name="%{#attr.form+'.fromOwnerId'}" id="fromOwnerId"/>

                    </s:if>

                    <s:else>
                        <tags:imSelect name="${pageScope.form}.fromOwnerId"
                                       id="fromOwnerId"
                                       cssClass="txtInput"
                                       headerKey="" headerValue="MSG.GOD.187"
                                       list="lstShopExport"
                                       listKey="shopId" listValue="name"/>

                    </s:else>
                </s:else>
            </td>
            <td  class="label">
                <tags:label key="MSG.GOD.185"/>
                <!--                Kho nhận-->
            </td>
            <td  class="text">
                <s:if test="#attr.type=='exp'">
                    <tags:imSelect name="${pageScope.form}.toOwnerId"
                                   id="toOwnerId"
                                   cssClass="txtInput"
                                   headerKey="" headerValue="MSG.GOD.186"
                                   list="lstShopImport"
                                   listKey="shopId" listValue="name"/>
                </s:if>
                <s:else>
                    <s:textfield name="%{#attr.form+'.toOwnerName'}"  id ="toOwnerName" readonly="true" cssClass="txtInput"/>
                    <s:hidden name="%{#attr.form+'.toOwnerId'}" id="toOwnerId"/>
                </s:else>

            </td>
        </tr>
        <tr>
            <td  class="label">
                <tags:label key="MSG.GOD.117"/>
                <!--                Từ ngày-->
            </td>
            <td  class="text">
                <tags:dateChooser property="${fromDate}" id="fromDate" styleClass="txtInput"/>
            </td>
            <td  class="label">
                <tags:label key="MSG.GOD.118"/>
                <!--                Đến ngày-->
            </td>
            <td  class="text">
                <tags:dateChooser property="${toDate}" id="toDate" styleClass="txtInput"/>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="center" >
                <%--sx:submit parseContent="true" executeScripts="true" id="searchButton" formId="%{#attr.form}"
                afterNotifyTopics="StockTrans/conplete"
                           loadingText="Đang xử lý..." showLoadingText="false" targets="%{#attr.target}" value="Tìm kiếm"
                           beforeNotifyTopics="StockTrans/search"  errorNotifyTopics="errorAction"/--%>
                <tags:submit formId="${form}" confirm="false" showLoadingText="true" targets="${target}" value="MSG.GOD.009" preAction="${action}"/>
            </td>
        </tr>
    </table>


</tags:imPanel>
<%--
<script>
dojo.event.topic.subscribe("StockTrans/conplete", function(event, widget){
resetProgress();
});
dojo.event.topic.subscribe("StockTrans/search", function(event, widget){
initProgress();
widget.href = '<s:property value="#attr.action"/>';
//event: set event.cancel = true, to cancel request
});
</script>
--%>
