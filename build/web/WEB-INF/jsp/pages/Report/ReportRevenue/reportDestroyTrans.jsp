<%--
    Document   : reportDestroyTrans
    Created on : JUL 26, 2010, 4:31:48 PM
    Author     : NamLT
    Desc       : bao cao huy giao dich
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
                    <%--<td style="width:10%; ">Mã cửa hàng<font color="red">*</font></td>--%>
                    <td style="width:10%;" ><tags:label key="MSG.RET.028" required="true"/></td>
                    <td style="width:30%; " class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.shopCode" nameVariable="reportRevenueForm.shopName"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"
                                       elementNeedClearContent="reportRevenueForm.staffCode;reportRevenueForm.staffName;reportRevenueForm.ownerCode;reportRevenueForm.ownerName"
                                       roleList="reportRevenueShop"/>
                    </td>
                    <%--<td style="width:10%; ">Từ ngày<font color="red">*</font></td>--%>
                    <td style="width:10%;" ><tags:label key="MSG.RET.036" required="true"/></td>
                    <td style="width:20%; " class="oddColumn">
                        <tags:dateChooser property="reportRevenueForm.fromDate"/>
                    </td>
                    <%--<td style="width:10%; ">Đến ngày<font color="red">*</font></td>--%>
                    <td style="width:10%;" ><tags:label key="MSG.RET.037" required="true"/></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="reportRevenueForm.toDate"/>
                    </td>
                </tr>
                <tr>
                    <%--<td>Mã nhân viên</td>--%>
                    <td ><tags:label key="MSG.RET.047" /></td>
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
                    <%--<td>Loại giao dịch</td>--%>
                    <td ><tags:label key="MSG.RET.082" /></td>
                    <td class="oddColumn">
                        <%--<s:select name="reportRevenueForm.saleTransType"
                                  id="saleTransType"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn loại giao dịch--"
                                  list="#{'1':'Bán lẻ', '2':'Bán cho đại lý', '3':'Bán cho CTV', '4':'Làm dịch vụ'}"/>--%>
                        <tags:imSelect name="reportRevenueForm.saleTransType" id="saleTransType"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.170"
                                       list="1:MSG.RET.083,2:MSG.RET.084,3:MSG.RET.085,4:MSG.RET.086"
                                       />
                    </td>
                    <%--<td>Dịch vụ</td>--%>
                    <td ><tags:label key="MSG.RET.063" /></td>
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
                    <%--  <td>Mã CTV/ĐB/ĐL</td>
                      <td class="oddColumn">
                          <tags:imSearch codeVariable="reportRevenueForm.ownerCode" nameVariable="reportRevenueForm.ownerName"
                                         codeLabel="Mã CTV/ĐB/ĐL" nameLabel="Tên CTV/ĐB/ĐL"
                                         searchClassName="com.viettel.im.database.DAO.ReportRevenueDAO"
                                         searchMethodName="getListCTVDB"
                                         otherParam="reportRevenueForm.shopCode;reportRevenueForm.staffCode;reportCheckType"
                                         roleList=""/>
                      </td>--%>
<!--                    <td>Mặt hàng/ DV</td>-->
                    <td ><tags:label key="MSG.RET.087" /></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.goodsCode" nameVariable="reportRevenueForm.goodsName"
                                       codeLabel="MSG.RET.088" nameLabel="MSG.RET.089"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListGoods"
                                       getNameMethod="getGoodsName"/>
                    </td>
                      <td><tags:label key="MSG.payMethod" /></td>
                    <td>
                        <tags:imSelect name="reportRevenueForm.payMethod"
                                  id="payMethod"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="MSG.SAE.014"
                                  list="1:MSG.SAE.071, 2:MSG.SAE.072, 3:MSG.eCommerce, 10:MSG.SIK.247"
                                  />
                    </td>

                </tr>
            </table>
        </div>

        <%-- <div class="divHasBorder" style="margin-top:10px; padding:3px;">
             <s:radio name="reportRevenueForm.reportType"
                      list="#{'1':'Báo cáo theo CH', '2':'Báo cáo CTV/ĐB', '3':'Báo cáo ĐL'}" onchange="clickChange(this.value)"/>
         </div>--%>
        <%--  <div class="divHasBorder" style="margin-top:10px; padding:3px;">
              <s:checkbox name="reportRevenueForm.notBilledSaleTrans"/>
              <label>Chưa lập hóa đơn</label>
              <s:checkbox name="reportRevenueForm.billedSaleTrans"/>
              <label>Đã lập hóa đơn</label>
              <s:checkbox name="reportRevenueForm.cancelSaleTrans"/>
              <label>Hủy</label>
          </div>
        --%>
        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <%--<s:radio name="reportRevenueForm.groupType"
                     list="#{'1':'Tổng hợp', '2':'Chi tiết cấp dưới', '3':'Chi tiết nhân viên'}"/>--%>
            <tags:imRadio name="reportRevenueForm.groupType" id="groupType"
                           list="1:MSG.RET.115,2:MSG.RET.143,3:MSG.RET.144"
                           />
        </div>

        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <s:checkbox name="reportRevenueForm.hasMoney"/>
            <label>${fn:escapeXml(imDef:imGetText('MSG.RET.090'))}</label>
            <s:checkbox name="reportRevenueForm.noMoney"/>
            <label>${fn:escapeXml(imDef:imGetText('MSG.RET.091'))}</label>
        </div>

        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <%--<s:radio name="reportRevenueForm.groupBySaleTransType"
                     list="#{'1':'Phân nhóm loại giao dịch', '2':'Không phân nhóm loại giao dịch'}"/>--%>
            <tags:imRadio name="reportRevenueForm.groupBySaleTransType" id="groupBySaleTransType"
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
                     preAction="reportRevenueAction!reportDestroyTrans.do"/>
    </div>

    <!-- phan link download bao cao -->
    <%--
    <div>
        <s:if test="#request.reportDestroyPath != null">
            <script>
                window.open('${fn:escapeXml(reportDestroyPath)}','','toolbar=yes,scrollbars=yes');
            </script>

            <a href="${fn:escapeXml(reportDestroyPath)}">
                <tags:displayResult id="reportRevenueMessage" property="reportRevenueMessage" type="key"/>
            </a>
        </s:if>
    </div>
    --%>
    <div>
        <s:if test="#request.reportDestroyPath != null">
            <script>
                    window.open('${contextPath}/download.do?${fn:escapeXml(reportDestroyPath)}', '', 'toolbar=yes,scrollbars=yes');
            </script>

            <a href="${contextPath}/download.do?${fn:escapeXml(reportDestroyPath)}">
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
                $('message').innerHTML = "!!!Lỗi. Trường Đến số isdn phải là số không âm";
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

</script>
