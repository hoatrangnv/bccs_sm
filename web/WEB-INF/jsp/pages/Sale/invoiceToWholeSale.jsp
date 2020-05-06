<%-- 
    Document   : invoiceToWholeSale
    Created on : Apr 7, 2009, 10:33:01 AM
    Author     : Doan Thanh 8
--%>

<%--
    Note: lap hoa don cho dai ly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>


<%
        request.setAttribute("contextPath", request.getContextPath());
%>
<script type="text/javascript" language="javascript">
    /*changTransType = function() {
        var transType = document.getElementById("saleTransType").value;
        if (transType == 3) {
            var staff = document.getElementById("staffId");
            var staffLabel = document.getElementById("staffLabel");
            staff.style.display = "block";
            staffLabel.style.display = "block";
        } else if (transType != 3){
            var staff = document.getElementById("staffId");
            var staffLabel = document.getElementById("staffLabel");
            staff.style.display = "none";
            staffLabel.style.display = "none";
        }
        serviceDisplay();
    }
    serviceDisplay = function() {
        var saleTransType = document.getElementById("saleTransType").value;
        if (saleTransType == 4) {
            var telecomServiceLabel = document.getElementById("telecomServiceLabel");
            var telecomServiceId = document.getElementById("telecomServiceId");
            telecomServiceLabel.style.display = "block";
            telecomServiceId.style.display = "block";
        } else if (saleTransType != 4){
            var telecomServiceLabel = document.getElementById("telecomServiceLabel");
            var telecomServiceId = document.getElementById("telecomServiceId");
            telecomServiceLabel.style.display = "none";
            telecomServiceId.style.display = "none";
        }
    }*/

    changTransType = function() {
        var transType = document.getElementById("saleTransType").value;
        if (transType == 3) {
            var staff = document.getElementById("staffId");
            var staffLabel = document.getElementById("staffLabel");
            staff.disabled = false;
            staffLabel.disabled = false;
        } else if (transType != 3){
            var staff = document.getElementById("staffId");
            var staffLabel = document.getElementById("staffLabel");
            staff.value = "";
            staff.disabled = true;
            staffLabel.value = "";
            staffLabel.disabled = true;
        }
        serviceDisplay();


        //MrSun
        //Refresh ReasonList
        updateData('${contextPath}/changeObjectType!changeSaleTransType.do?saleTransType='+ transType);

    }
    serviceDisplay = function() {
        return true;
        var saleTransType = document.getElementById("saleTransType").value;
        if (saleTransType == 4) {
            var telecomServiceLabel = document.getElementById("telecomServiceLabel");
            var telecomServiceId = document.getElementById("telecomServiceId");
            telecomServiceLabel.disabled = false;
            telecomServiceId.disabled = false;
        } else if (saleTransType != 4){
            var telecomServiceLabel = document.getElementById("telecomServiceLabel");
            var telecomServiceId = document.getElementById("telecomServiceId");
            telecomServiceLabel.value = "";
            telecomServiceLabel.disabled = true;
            telecomServiceId.value = "";
            telecomServiceId.disabled = true;

        }
    }

    changeObjectType = function() {
        var channelTypeId = $('channelTypeId').value;
        updateData('${contextPath}/changeObjectType!changeObjectType.do?channelTypeId='+ channelTypeId);
    }
</script>

<s:form action="searchSaleTransAction" theme="simple" method="post" id="saleForm">
<s:token/>

    <!-- phan tim kiem thong tin ve giao dich de lap hoa don -->
    <tags:imPanel title="Tìm kiếm giao dịch để lập hóa đơn">

        <s:actionerror/>
        <s:actionmessage/>
        <table class="inputTbl6Col" style="width:100%" cellpadding="0" cellspacing="4" border="0">
            <tr>
                <td class="label">Tên khách hàng</td>
                <td class="text">
                    <s:textfield name="saleForm.custName" id="custName" maxlength="1000" cssClass="txtInput" theme="simple"/>
                </td>
                <td class="label">Ngày giao dịch từ</td>
                <td class="text">
                    <tags:dateChooser property="saleForm.startDate" styleClass="txtInput" id="startDate"/>
                </td>
                <td class="label">Ngày giao dịch đến</td>
                <td class="text">
                    <tags:dateChooser property="saleForm.endDate" styleClass="txtInput" id="endDate"/>
                </td>
            </tr>
            <tr>
                <td class="label">Hình thức bán</td>
                <td class="text">
                    <s:if test="#session.saleTransInvoice.interfaceType == 3">
                        <s:select name="saleForm.saleTransType" id="saleTransType"
                                  cssClass="txtInput" theme="simple"
                                  list="%{#session.saleInvoiceTypeList != null?#session.saleInvoiceTypeList:#{}}"
                                  listKey="saleInvoiceTypeId" listValue="name"
                                  headerKey="" headerValue="-Chọn hình thức bán--"
                                  onchange="changTransType();"/>
                    </s:if>
                    <s:elseif test="#session.saleTransInvoice.interfaceType == 1">
                        <s:select name="saleForm.saleTransType" id="saleTransType" theme="simple"
                                  cssClass="txtInput" disabled="true"
                                  list="#{'1':'Bán lẻ cho khách hàng',
                                  '2':'Bán cho đại lý',
                                  '3':'Bán cho cộng tác viên, điểm bán'}"
                                  headerKey="2" headerValue="Bán cho đại lý"/>
                    </s:elseif>
                    <s:elseif test="#session.saleTransInvoice.interfaceType == 2">
                        <s:select name="saleForm.saleTransType" id="saleTransType"
                                  cssClass="txtInput" disabled="true" theme="simple"
                                  list="#{'1':'Bán lẻ cho khách hàng',
                                  '2':'Bán cho đại lý',
                                  '3':'Bán cho cộng tác viên, điểm bán'}"
                                  headerKey="1" headerValue="Bán lẻ cho khách hàng"/>
                    </s:elseif>
                </td>
                <td class="label"><div id='telecomServiceLabel'>Loại dịch vụ</div></td>
                <td class="text">
                    <s:select name="saleForm.telecomServiceId"
                              id="telecomServiceId" theme="simple"
                              cssClass="txtInput"
                              headerKey="" headerValue="--Chọn loại dịch vụ--"
                              list="%{#session.telecomList != null?#session.telecomList:#{}}"
                              listKey="telecomServiceId" listValue="telServiceName"
                              onchange=""/>

                </td>
                <script type="text/javascript" language="javascript">
                    serviceDisplay();
                </script>
            </tr>
            <tr>
                <td class="label">Hình thức thanh toán</td>
                <td class="text">
                    <s:select name="saleForm.payMethod"
                              id="payMethod" theme="simple"
                              cssClass="txtInput" theme="simple"
                              headerKey="" headerValue="--Chọn HTTT--"
                              list="%{#session.listPayMethod != null?#session.listPayMethod:#{}}"
                              listKey="code" listValue="name"/>
                </td>
                <td class="label">Lý do bán</td>
                <td class="text">
                    <s:select name="saleForm.reasonId"
                              id="reasonId" theme="simple"
                              cssClass="txtInput"
                              headerKey="" headerValue="--Chọn lý do--"
                              list="%{#session.lstReasonSale != null?#session.lstReasonSale:#{}}"
                              listKey="reasonId" listValue="reasonName"/>
                </td>
            </tr>
            <s:if test="#session.saleTransInvoice.interfaceType != null && #session.saleTransInvoice.interfaceType == 3">
                <script>
                    var title ="Lập hoá đơn bán lẻ và làm dịch vụ";
                    document.title=title.trim();
                    document.getElementById("myHeader").innerHTML=title;
                </script>
                <tr>
                    <td class="label">
                        <div id='staffLabel'>
                        Cộng tác viên/Điểm bán</div>
                    </td>
                    <td class="text">
                        <s:hidden name="saleForm.transStatus" id="transStatus"></s:hidden>

                        <s:select name="saleForm.staffId"
                                  id="staffId" theme="simple"
                                  cssClass="txtInput"
                                  headerKey="" headerValue="--Chọn cộng tác viên--"
                                  list="%{#session.collStaffList != null?#session.collStaffList:#{}}"
                                  listKey="staffId" listValue="name"
                                  onchange=""/>
                    </td>
                </tr>
                <script type="text/javascript" language="javascript">
                    changTransType();
                </script>
            </s:if>
            <s:if test="#session.saleTransInvoice.interfaceType != null && #session.saleTransInvoice.interfaceType == 2">
                <script>
                    var title ="Lập hoá đơn thay CTV/ĐB";
                    document.title=title.trim();
                    document.getElementById("myHeader").innerHTML=title;
                </script>
                <tr>
                    <td class="label">Chọn loại đối tượng (<font color="red">*</font>)</td>
                    <td class="text">
                        <s:hidden name="saleForm.transStatus" id="transStatus"></s:hidden>

                        <s:select name="saleForm.channelTypeId"
                                  id="channelTypeId" theme="simple"
                                  cssClass="txtInput"
                                  headerKey="" headerValue="--Chọn đối tượng--"
                                  list="%{#session.channelTypeList != null?#session.channelTypeList:#{}}"
                                  listKey="channelTypeId" listValue="name"
                                  onchange="changeObjectType();"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">Chọn đối tượng lập thay</td>
                    <td class="text">
                        <s:select name="saleForm.salerId"
                                  id="salerIdChan" theme="simple"
                                  cssClass="txtInput"
                                  headerKey="" headerValue="--Chọn đối tượng lập thay--"
                                  list="%{#session.collStaffList != null?#session.collStaffList:#{}}"
                                  listKey="staffId" listValue="name"
                                  onchange=""/>
                    </td>
                </tr>
            </s:if>
            <s:elseif test="#session.saleTransInvoice.interfaceType != null && #session.saleTransInvoice.interfaceType == 1">
                <script>
                    var title ="Lập hoá đơn bán hàng cho đại lý";
                    document.title=title.trim();
                    document.getElementById("myHeader").innerHTML=title;
                </script>
                <tr>
                    <td class="label">Đại lý</td>
                    <td class="text">
                        <s:select name="saleForm.agentId"
                                  id="agentId" theme="simple"
                                  cssClass="txtInput"
                                  headerKey="" headerValue="--Chọn đại lý--"
                                  list="%{#session.childShopList != null?#session.childShopList:#{}}"
                                  listKey="shopId" listValue="name"
                                  onchange=""/>
                    </td>
                    <td class="label">Trạng thái GD</td>
                    <td class="text">
                        <s:select name="saleForm.transStatus" id="transStatus"
                                  cssClass="txtInput" theme="simple"
                                  list="%{#session.statusList != null?#session.statusList:#{}}"
                                  listKey="code" listValue="name"
                                  headerKey="" headerValue="--Chọn trạng thái--"/>

                        <%--<s:select name="saleForm.transStatus" id="transStatus"
                                  cssClass="txtInput" theme="simple"
                                  list="#{'1':'Chưa thanh toán',
                                  '2':'Chưa lập hóa đơn',
                                  '3':'Đã lập hóa đơn',
                                  '4':'Đã hủy'}"
                        headerKey="" headerValue="--Chọn trạng thái--"/>--%>
                    </td>
                </tr>
            </s:elseif>
            <tr>
                <td colspan="6">
                    <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
                </td>
            </tr>
        </table>
           <br/>
            <div style="width:100%" align="center">
        <tags:submit targets="bodyContent" formId="saleForm"
                     cssStyle="width:80px;"
                     value="Tìm kiếm" preAction="searchSaleTransAction!searchSaleTrans.do"
                     showLoadingText="true"
                     />
    </div>
    </tags:imPanel>
    <br/>


    <jsp:include page="invoiceSaleTransList.jsp"/>
</s:form>
<script type="text/javascript" language="javascript">

    dojo.event.topic.subscribe("saleForm/search", function(event, widget){
        widget.href = "searchSaleTransAction!searchSaleTrans.do";
    });
</script>
<script type="text/javascript" language="javascript">
    dojo.event.topic.subscribe("saleForm/gotoCreateInvoice", function(event, widget){
        widget.href = "searchSaleTransAction!gotoCreateSaleTransInvoice.do";
    });

    dojo.event.topic.subscribe("saleForm/reset", function(event, widget){
        if($( 'custName' ) != null){
            $( 'custName' ).value = "";
        }
        if($( 'saleTransType' ) != null)
            $( 'saleTransType' ).value = "";
        dojo.widget.byId("startDate").domNode.childNodes[1].value = "";
        if($( 'telecomServiceId' ) != null)
            $( 'telecomServiceId' ).value = "";
        if($( 'payMethod' ) != null)
            $( 'payMethod' ).value = "";
        if($( 'reasonId' ) != null)
            $( 'reasonId' ).value = "";
        if($( 'staffId' ) != null)
            $( 'staffId' ).value = "";
        if($( 'agentId' ) != null)
            $( 'agentId' ).value = "";
        if($( 'transStatus' ) != null)
            $( 'transStatus' ).value = "";
        event.cancel = true;
    });

</script>
