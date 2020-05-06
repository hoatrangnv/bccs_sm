<%-- 
    Document   : reportRevenue
    Created on : Jun 23, 2009, 4:31:48 PM
    Author     : tamdt1
    Desc       : bao cao doanh thu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
            request.setAttribute("contextPath", request.getContextPath());

            if (request.getSession().getAttribute("listChannelType") != null) {
                request.setAttribute("listChannelType", request.getSession().getAttribute("listChannelType"));
            }
            if (request.getSession().getAttribute("saleInvoiceTypeList") != null) {
                request.setAttribute("saleInvoiceTypeList", request.getSession().getAttribute("saleInvoiceTypeList"));
            }
%>

<tags:imPanel title="MSG.criterion.report.create">

    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi tieu chi bao cao -->
    <s:form action="reportRevenueAction" theme="simple" method="post" id="reportRevenueForm">
<s:token/>

        <s:hidden name="reportRevenueForm.reportType" id ="reportCheckType" disabled="true"></s:hidden>
        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td style="width:10%; "><tags:label key="MSG.shop.code" required="true"/></td>
                    <td style="width:30%; " class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.shopCode"
                                       nameVariable="reportRevenueForm.shopName"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"
                                       elementNeedClearContent="reportRevenueForm.staffCode;reportRevenueForm.staffName;reportRevenueForm.ownerCode;reportRevenueForm.ownerName"
                                       roleList="reportRevenueShop"/>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.fromDate" required="true" /></td>
                    <td style="width:20%; " class="oddColumn">
                        <tags:dateChooser property="reportRevenueForm.fromDate"/>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.generates.to_date" required="true" /></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="reportRevenueForm.toDate"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.staff.code" /></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.staffCode" nameVariable="reportRevenueForm.staffName"
                                       codeLabel="MSG.RET.047" nameLabel="MSG.RET.048"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStaff"
                                       getNameMethod="getStaffName"
                                       otherParam="reportRevenueForm.shopCode"
                                       elementNeedClearContent="reportRevenueForm.ownerCode;reportRevenueForm.ownerName"
                                       roleList="reportRevenueStaff"/>  
                    </td>
                    <td><tags:label key="MSG.transaction.type" /></td>
                    <td class="oddColumn">
                        <%--<s:select name="reportRevenueForm.saleTransType"
                                  id="saleTransType"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn loại giao dịch--"
                                  list="#{'1':'Bán lẻ', '2':'Bán cho đại lý', '3':'Bán cho CTV', '4':'Làm dịch vụ'}"/>--%>
                        <%--tags:imSelect name="reportRevenueForm.saleTransType"
                                       id="saleTransType"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.170"
                                       list="1:MSG.RET.083,2:MSG.RET.084,3:MSG.RET.085,4:MSG.RET.086"/--%>
                        <tags:imSelect name="reportRevenueForm.saleTransType"
                                       id="saleTransType"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.170"
                                       list="saleInvoiceTypeList" listKey="saleTransType" listValue="name"/>

                    </td>
                    <td><tags:label key="MSG.generates.service" /></td>
                    <td>
                        <%--<s:select name="reportRevenueForm.telecomServiceId"
                                  id="telecomServiceId"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn dịch vụ--"
                                  list="%{#request.listTelecomService!=null ? #request.listTelecomService : #{}}"
                                  listKey="telecomServiceId" listValue="telServiceName"/>--%>
                        <tags:imSelect name="reportRevenueForm.telecomServiceId"
                                       id="telecomServiceId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.171"
                                       list="listTelecomService"
                                       listKey="telecomServiceId" listValue="telServiceName"/>
                    </td>
                    <%--<td>Đối tượng</td>
                    <td>
                        <s:select name="reportRevenueForm.channelTypeId"
                                  id="channelTypeId"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn đối tượng--"
                                  list="#{'1':'Cửa hàng', '2':'Đại lý'}"/>
                    </td>--%>
                    <%--
                    <td>Lý do</td>
                    <td>
                        <s:select name="reportRevenueForm.reasonId"
                                  id="reasonId"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn lý do--"
                                  list="%{#request.listReason!=null ? #request.listReason : #{}}"
                                  listKey="reasonId" listValue="reasonName"/>
                    </td>--%>
                </tr>
                <tr>
                    <td><tags:label key="MSG.ctv_db_dl.code" /></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.ownerCode" nameVariable="reportRevenueForm.ownerName"
                                       codeLabel="MSG.RET.094" nameLabel="MSG.RET.095"
                                       searchClassName="com.viettel.im.database.DAO.ReportRevenueDAO"
                                       searchMethodName="getListCTVDB"
                                       otherParam="reportRevenueForm.shopCode;reportRevenueForm.staffCode;reportCheckType"
                                       roleList=""/>
                    </td>
                    <td><tags:label key="MSG.GOD.027" /></td>
                    <td class="oddColumn">
                        <tags:imSelect name="reportRevenueForm.stockTypeId"
                                       id="stockTypeId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.160"
                                       list="listStockType"
                                       listKey="stockTypeId" listValue="name"/>
                    </td>
                    <td><tags:label key="MSG.goods_service.code" /></td>
                    <td>
                        <tags:imSearch codeVariable="reportRevenueForm.goodsCode" nameVariable="reportRevenueForm.goodsName"
                                       codeLabel="MSG.RET.088" nameLabel="MSG.RET.089"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListGoods"
                                       getNameMethod="getGoodsName"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.GOD.157" /></td>
                    <td class="oddColumn">
                        <tags:imSelect name="reportRevenueForm.payMethod"
                                       id="payMethod"
                                       cssClass="txtInputFull"
                                       list="listPayMethod"
                                       listKey="code" listValue="name"
                                       headerKey="" headerValue="MSG.RET.180"/>
                    </td>
                </tr>
            </table>
        </div>

        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <table><tr><td>
            <%--<s:radio name="reportRevenueForm.reportType"
                     list="#{'1':'Báo cáo theo CH', '2':'Báo cáo CTV/ĐB', '3':'Báo cáo ĐL'}" onchange="clickChange(this.value)"/>--%>
            <%--tags:imRadio name="reportRevenueForm.reportType"
                     list="1:MSG.report.follow.shop, 2:MSG.report.CTV_DB, 3:MSG.report.agent"
                     onchange="clickChange(this.value)"/--%>
            <s:checkbox name="reportRevenueForm.reportIncludeShop" onchange="onlyOneCheckBox(this)"/>
            <label>${fn:escapeXml(imDef:imGetText('msg.report.includeShop'))}</label>

                        <s:checkbox id="reportRevenueForm.reportIncludeCollaborator" name="reportRevenueForm.reportIncludeCollaborator" onchange="onlyOneCheckBox(this)"/>
            <label>${fn:escapeXml(imDef:imGetText('msg.report.includeCollaborator'))}</label>
                        <s:hidden name="reportRevenueForm.reportIncludePointOfSale" id="reportRevenueForm.reportIncludePointOfSale" value="false"/>
                        <s:hidden name="reportRevenueForm.reportIncludeAgent" value="false"/>
            <%--s:checkbox name="reportRevenueForm.reportIncludePointOfSale" onchange="onlyOneCheckBox(this)"/>
            <label>${fn:escapeXml(imDef:imGetText('msg.report.includePointOfSale'))}</label--%>

                        <%--s:checkbox name="reportRevenueForm.reportIncludeAgent" id="reportRevenueForm.reportIncludeAgent" onchange="onlyOneCheckBox(this)"/>
                        <label>${fn:escapeXml(imDef:imGetText('msg.report.includeIncludeAgent'))}</label--%>
                    </td><td>

                        <div  style="width: 186px" >
                            <s:if test="reportRevenueForm.subChannelTypeId == null || reportRevenueForm.subChannelTypeId * 1 < 1">
                                <tags:imSelectNoHidden name="reportRevenueForm.subChannelTypeId"
                                                       id="reportRevenueForm.subChannelTypeId"
                                                       cssClass="txtInputFull"
                                                       theme="simple" disabled="true"
                                                       headerKey="" headerValue="label.option"
                                                       list="listChannelType" listKey="channelTypeId" listValue="name" />
                            </s:if>
                            <s:else>
                                <tags:imSelectNoHidden name="reportRevenueForm.subChannelTypeId"
                                                       id="reportRevenueForm.subChannelTypeId"
                                                       cssClass="txtInputFull"
                                                       theme="simple"
                                                       headerKey="" headerValue="label.option"
                                                       list="listChannelType" listKey="channelTypeId" listValue="name" />
                            </s:else>
                        </div>
                    </td></tr></table>
                    <%--font color="red">Note: Current, system can only support 1 type </font--%>
        </div>
        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <s:checkbox name="reportRevenueForm.notBilledSaleTrans"/>
            <label>${fn:escapeXml(imDef:imGetText('MSG.not.billed'))}</label>
            <s:checkbox name="reportRevenueForm.billedSaleTrans"/>
            <label>${fn:escapeXml(imDef:imGetText('MSG.billed'))}</label>
            <s:checkbox name="reportRevenueForm.cancelSaleTrans"/>
            <label>${fn:escapeXml(imDef:imGetText('MSG.destroy'))}</label>
        </div>

        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <%--<s:radio name="reportRevenueForm.groupType"
                     list="#{'1':'Tổng hợp', '2':'Chi tiết cấp dưới', '3':'Chi tiết nhân viên/CTV/ĐB/ĐL', '4':'Chi tiết tất cả cấp dưới'}"/>--%>
            <tags:imRadio name="reportRevenueForm.groupType"
                          id="groupType"
                          list="1:MSG.RET.115,2:MSG.RET.143,3:MSG.RET.145, 4:MSG.RET.143a"/>
        </div>
        
            <%--s:hidden name="reportRevenueForm.hasMoney" value="true"/>
            <s:hidden name="reportRevenueForm.noMoney" value="true"/--%>

        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <s:checkbox name="reportRevenueForm.hasMoney"/>
            <label>${fn:escapeXml(imDef:imGetText('MSG.get.money'))}</label>
            <s:checkbox name="reportRevenueForm.noMoney"/>
            <label>${fn:escapeXml(imDef:imGetText('MSG.charge'))}</label>
        </div>

        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <%--<s:radio name="reportRevenueForm.groupBySaleTransType"
                     list="#{'1':'Phân nhóm loại giao dịch', '2':'Không phân nhóm loại giao dịch'}"/>--%>
            <tags:imRadio name="reportRevenueForm.groupBySaleTransType"
                          id="groupBySaleTransType"
                          list="1:MSG.RET.092,2:MSG.RET.093"/>
        </div>

    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="reportRevenueForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MSG.report"
                     preAction="reportRevenueAction!reportRevenue.do"/>
    </div>

    <!-- phan link download bao cao -->
    <%--
    <div>
        <s:if test="#request.reportRevenuePath != null">
            <script>
                 window.open('${fn:escapeXml(reportRevenuePath)}','','toolbar=yes,scrollbars=yes');
            </script>

            <a href="${fn:escapeXml(reportRevenuePath)}">
                <tags:displayResult id="reportRevenueMessage" property="reportRevenueMessage" type="key"/>
            </a>
        </s:if>
    </div>
    --%>
    <div>
        <s:if test="#request.reportRevenuePath != null">
            <script>
                    window.open('${contextPath}/download.do?${fn:escapeXml(reportRevenuePath)}', '', 'toolbar=yes,scrollbars=yes');
            </script>

            <a href="${contextPath}/download.do?${fn:escapeXml(reportRevenuePath)}">
                <tags:displayResult id="reportRevenueMessage" property="reportRevenueMessage" type="key"/>
            </a>
        </s:if>
    </div>

</tags:imPanel>

<script language="javascript">
    clickChange = function(value) {
        $('reportCheckType').value = value;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        var fromPrice = $('fromPrice').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        var toPrice = $('toPrice').value.replace(/\,/g,""); //loai bo dau , trong chuoi

        if(fromPrice != '' && !isInteger(trim(fromPrice))) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường Từ giá phải là số không âm";--%>
                $('message').innerHTML='<s:text name="ERR.RET.043"/>';
                $('fromPrice').select();
                $('fromPrice').focus();
                return false;
            }

            if(toPrice != '' && !isInteger(trim(toPrice))) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường Đến giá phải là số không âm";--%>
                $('message').innerHTML='<s:text name="ERR.RET.044"/>';
                $('toPrice').select();
                $('toPrice').focus();
                return false;
            }

            var fromIsdn = $('fromIsdn').value;
            var toIsdn = $('toIsdn').value;

            if(fromIsdn != '' && !isInteger(trim(fromIsdn))) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường Từ số isdn phải là số không âm";--%>
                $('message').innerHTML='<s:text name="ERR.RET.045"/>';
                $('fromIsdn').select();
                $('fromIsdn').focus();
                return false;
            }

            if(toIsdn != '' && !isInteger(trim(toIsdn))) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường Đến số isdn phải là số không âm";--%>
                $('message').innerHTML='<s:text name="ERR.RET.046"/>';
                $('toIsdn').select();
                $('toIsdn').focus();
                return false;
            }

            //trim cac truong can thiet
            $('fromPrice').value = trim($('fromPrice').value);
            $('toPrice').value = trim($('toPrice').value);

            return true;
        }

        //xu ly su kien sau khi chon 1 ma cua hang
        dojo.event.topic.subscribe("reportRevenueAction/getShopName", function(value, key, text, widget){
            $('shopName').value = "";
            $('staffName').value = "";
            var _myWidget = dojo.widget.byId("staffCode");
            _myWidget.textInputNode.value = "";
        
            if (key != undefined) {
                getInputText("reportRevenueAction!getShopName.do?target=shopName&shopId=" + key);
            } else {
            
            }
        });

        //xu ly su kien sau khi chon 1 ma nhan vien
        dojo.event.topic.subscribe("reportRevenueAction/getStaffName", function(value, key, text, widget){
            $('staffName').value = "";
        
            if (key != undefined) {
                getInputText("reportRevenueAction!getStaffName.do?target=staffName&staffId=" + key);
            } else {
            
            }
        });

        //cap nhat lai danh sach mat hang, danh sach tap luat
        changeStockType = function(stockTypeId) {
            updateData("${contextPath}/reportRevenueAction!changeStockType.do?target=stockModelId&stockTypeId=" + stockTypeId);
        }

        //han che ko cho check nhieu checkbox 1 luc
        onlyOneCheckBox = function(object) {
            $('reportRevenueForm.subChannelTypeId').disabled = !$('reportRevenueForm.reportIncludeCollaborator').checked;
            return;
            var chkReportIncludeShop = $('reportRevenueForm_reportRevenueForm_reportIncludeShop');
            var chkReportIncludeCollaborator = $('reportRevenueForm_reportRevenueForm_reportIncludeCollaborator');
            var chkReportIncludePointOfSale = $('reportRevenueForm_reportRevenueForm_reportIncludePointOfSale');
            var chkReportIncludeIncludeAgent = $('reportRevenueForm_reportRevenueForm_reportIncludeAgent');

            if(object.checked) {
                chkReportIncludeShop.checked = false;
                chkReportIncludeCollaborator.checked = false;
                chkReportIncludePointOfSale.checked = false;
                chkReportIncludeIncludeAgent.checked = false;
                object.checked = true;
            }

            //trung dh3
           
         
        }
        //        var reportIncludePointOfSale=document.getElementById("reportRevenueForm.reportIncludeCollaborator");
        //        reportIncludePointOfSale.checked = false;
        //        var objChannelTypeId = document.getElementById("reportRevenueForm.subChannelTypeId");
        //        objChannelTypeId.disabled = true;

</script>
