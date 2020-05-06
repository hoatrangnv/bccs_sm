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

<%@attribute name="type"%>
<%@attribute name="form" required="true"%>
<%@attribute name="target" required="true"%>
<%@attribute name="action" required="true"%>
<%@taglib prefix="imDef" uri="imDef" %>


<%request.setAttribute("contextPath", request.getContextPath());%>
<%request.setAttribute("fromDate", form + ".fromDate");%>
 <%request.setAttribute("toDate", form + ".toDate");%>
<!-- Tìm kiếm giao dịch-->
<tags:imPanel title="MSG.SAE.107">
    <table class="inputTbl" style="width:100%">
        <tr>
            <td>
                <tags:label key="MSG.note.cmd.code"/>
            </td>
            <td>
                <s:textfield name="%{#attr.form+'.actionCode'}" id="actionCode" cssClass="txtInputFull"/>
                <script>$('actionCode').focus();</script>
            </td>
            <td >
                <tags:label key="MSG.generates.status"/>
            </td>
            <td>
                <tags:imSelect name="${form}.transStatus"
                          id="transStatus"
                          cssClass="txtInputFull"
                          headerKey="" headerValue="MSG.GOD.189"
                          list="1:MSG.GOD.190,2:MSG.GOD.191,3:MSG.ISN.016,4:MSG.GOD.195,5:MSG.GOD.196,6:MSG.ISN.017"/>
            </td>
            <td>
                <tags:label key="MSG.GOD.117"/>
<!--                Từ ngày-->
            </td>
            <td>
                
                <tags:dateChooser property="${fromDate}" id="fromDate" styleClass="txtInputFull"/>
            </td>
            <td>
                <tags:label key="MSG.SAE.127"/>
<!--                Đến ngày-->
            </td>
            <td>
               
                <tags:dateChooser property="${toDate}" id="toDate" styleClass="txtInputFull"/>
            </td>
        </tr>
        <tr>
            <td>
                <tags:label key="MSG.SAE.142"/>
<!--                Loại giao dịch-->
            </td>
            <td>
                <tags:imSelect name="%{#attr.form+'.transType'}"
                          id="transType"
                          cssClass="txtInputFull"
                          headerKey="" headerValue="MSG.ISN.018"
                          list="1:MSG.ISN.019,2:MSG.ISN.020"/>
            </td>
            <td>
                <tags:label key="MSG.noteType"/>
<!--                Loại lệnh/phiếu-->
            </td>
            <td>
                <tags:imSelect name="${form}.actionType"
                          id="actionType"
                          cssClass="txtInputFull"
                          headerKey="" headerValue="MSG.ISN.021"
                          list="1:MSG.ISN.022,2:MSG.ISN.023"/>
            </td>
            <td>
                <tags:label key="MSG.storeType"/>
<!--                Loại kho giao dịch-->
            </td>
            <td>
                <tags:imSelect name="${form}.fromOwnerType"
                          id="fromOwnerType"
                          cssClass="txtInputFull"
                          headerKey="" headerValue="MSG.GOD.252"
                          list="1:MSG.STK.006,2:MSG.ISN.024"
                          onchange="updateCombo('fromOwnerType','fromOwnerId','${request.contextPath}/StockTransManagmentAction!selectOwnerType.do')"/>
            </td>

            <td>
                <tags:label key="MSG.stockTransStore"/>
<!--                Kho giao dịch-->
            </td>
            <td>
                <tags:imSelect name="${form}.fromOwnerId"
                          id="fromOwnerId"
                          cssClass="txtInputFull"
                          headerKey="" headerValue="MSG.ISN.025"
                          list="lstOwnerExp"
                          listKey="id" listValue="name"
                          />

            </td>

            <%-- <sx:autocompleter name="%{#attr.form+'.fromOwnerCode'}"  keyName="%{#attr.form+'.fromOwnerId'}"
                                  id="fromOwnerId" loadOnTextChange="true" loadMinimumCount="1"

                                  listenTopics="select1"
                                  cssClass="txtInputFull"
                                  />
                <td colspan="2">
                <s:textfield name="%{#attr.form+'.fromOwnerName}" readonly="true" cssClass="txtInputFull" cssStyle="color:red"/>
            </td>
            --%>



        </tr>
        <tr>
            <td colspan="8" align="center" >
                <%--sx:submit parseContent="true" executeScripts="true" id="searchButton" formId="%{#attr.form}"
                           loadingText="Đang xử lý..." showLoadingText="true" targets="%{#attr.target}" value="Tìm kiếm"
                           beforeNotifyTopics="StockTrans/search"  errorNotifyTopics="errorAction"/--%>
                <%--tags:submit id="searchButton" confirm="false" formId="${form}" showLoadingText="true" targets="${target}" value="MSG.GOD.009" preAction="${action}"/--%>
            </td>
        </tr>
        <tr>
            <td colspan="8" align="center">
                <tags:displayResult id="searchMsgClient" property="searchMsg"/>
            </td>
        </tr>
    </table>

</tags:imPanel>
<script>    
    /*
    dojo.event.topic.subscribe("StockTrans/search", function(event, widget){
        widget.href = '<s:property value="#attr.action"/>';
    });

    dojo.event.topic.subscribe("StockTransManagmentAction/selectOwnerCode", function(value, key, text, widget){
       if(key!=null){
        var ownerType =$('fromOwnerType').value;
        alert(ownerType);
        if(ownerType==null || ownerType==''){
            $('searchMsgClient').innerHTML="Chưa chọn loại kho giao dịch";
            $('fromOwnerCode').value="";
            $('fromOwnerType').focus();

            event.cancel=true;
        }
        getInputText("StockTransManagmentAction!getShopNameText.do?ownerCode="+key +"&target=exportStockForm.fromOwnerName&ownerType="+ownerType)
       }else{
          $('exportStockForm.fromOwnerName').value="";
       }
    });
     */
</script>

