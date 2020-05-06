<%-- 
    Document   : reportNiceNumber
    Created on : Nov 21, 2009, 9:44:05 AM
    Author     : NamNX
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>
<tags:imPanel title="MSG.nice.number.report">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi thong tin can tra cuu -->
    <div class="divHasBorder">
        <s:form action="reportNiceNumberAction" theme="simple" method="post" id="reportNiceNumberForm">
<s:token/>

            <table class="inputTbl6Col">
                <tr>
                    <td><tags:label key="MSG.generates.service" /></td>
                    <td class="oddColumn">
                        <%--<s:select name="reportNiceNumberForm.stockTypeId"
                                  id="stockTypeId"
                                  cssClass="txtInputFull"
                                  list="%{#request.listStockType!=null ? #request.listStockType : #{}}"
                                  listKey="stockTypeId" listValue="name"
                                  onchange="changeStockType(this.value)"
                                  disabled="true"/>--%>
                        <tags:imSelect name="reportNiceNumberForm.stockTypeId"
                                       id="stockTypeId"
                                       cssClass="txtInputFull"
                                       disabled="true"
                                       list="listStockType"
                                       onchange="changeStockType(this.value)"
                                       listKey="stockTypeId" listValue="name"
                                       />
                </tr>
                <tr>
                    <td style="width:10%;"><tags:label key="MSG.isdn.stock" /></td>
                    <td class="oddColumn" style="width:30%;">
                        <tags:imSearch codeVariable="reportNiceNumberForm.shopCode" nameVariable="reportNiceNumberForm.shopName"
                                       codeLabel="MSG.RET.066" nameLabel="MSG.RET.067"
                                       searchClassName="com.viettel.im.database.DAO.ReportNiceNumberDAO"
                                       searchMethodName="getListShop"/>
                    </td>
                    <td style="width:10%;"><tags:label key="MSG.isdn_type" /></td>
                    <td class="oddColumn" style="width:20%;">
                        <%--<s:select name="reportNiceNumberForm.isdnType"
                                  id="isdnType"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn loại số--"
                                  list="#{'3':'Số đặc biệt', '2':'Số trả sau', '1':'Số trả trước'}"/>--%>
                        <tags:imSelect name="reportNiceNumberForm.isdnType" id="isdnType"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.165"
                                       list="3:MSG.RET.068,2:MSG.RET.069,1:MSG.RET.070"
                                       />
                    </td>
                    <td style="width:10%;"><tags:label key="MSG.RET.071" /></td>
                    <td>
                        <%--<s:select name="reportNiceNumberForm.groupFilterRuleCode"
                                  id="groupFilterRuleCode"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn tập luật--"
                                  list="%{#request.listGroupFilter != null ? #request.listGroupFilter : #{}}"
                                  listKey="groupFilterRuleCode" listValue="name"
                                  onchange="changeGroupFilterRuleCode(this.value);"/>--%>
                        <tags:imSelect name="reportNiceNumberForm.groupFilterRuleCode"
                                       id="groupFilterRuleCode"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.166"
                                       list="listGroupFilter"
                                       listKey="groupFilterRuleCode" listValue="name"
                                       onchange="changeGroupFilterRuleCode(this.value);"
                                       />
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.isdn.area" /></td>
                    <td class="oddColumn">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="reportNiceNumberForm.fromIsdn" id="fromIsdn" maxlength="25" cssClass="txtInputFull"/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="reportNiceNumberForm.toIsdn" id="toIsdn" maxlength="25" cssClass="txtInputFull"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td><tags:label key="MSG.generates.status" /></td>
                    <td class="oddColumn">
                        <%--<s:select name="reportNiceNumberForm.status"
                                  id="status"
                                  cssClass="txtInputFull"
                                  list="#{'1':'Số mới', '3':'Số ngưng sử dụng'}"/>--%>
                        <%--list="#{'4':'Số đã đấu KIT', '5':'Số đang bị khóa', '2':'Số đang sử dụng', '1':'Số mới', '3':'Số ngưng sử dụng'}"--%>
                        <tags:imSelect name="reportNiceNumberForm.status" id="status"
                                       cssClass="txtInputFull"                                       
                                       list="1:MSG.RET.073,3:MSG.RET.074"
                                       />
                    </td>
                    <td><tags:label key="MSG.RET.075" /></td>
                    <td>
                        <%--<s:select name="reportNiceNumberForm.filterTypeId"
                                  id="filterTypeId"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn nhóm luật--"
                                  list="%{#request.listFilterType != null ? #request.listFilterType : #{}}"
                                  listKey="filterTypeId" listValue="name"
                                  onchange="changeFilterType(this.value);"/>--%>
                        <tags:imSelect name="reportNiceNumberForm.filterTypeId"
                                       id="filterTypeId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.167"
                                       list="listFilterType"
                                       listKey="filterTypeId" listValue="name"
                                       onchange="changeFilterType(this.value);"
                                       />
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.interval.price" /></td>
                    <td class="oddColumn">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="reportNiceNumberForm.fromPrice" id="fromPrice" onkeyup="textFieldNF(this)" maxlength="15" cssClass="txtInputFull" cssStyle="text-align:right; "/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="reportNiceNumberForm.toPrice" id="toPrice" onkeyup="textFieldNF(this)" maxlength="15" cssClass="txtInputFull" cssStyle="text-align:right; "/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td><tags:label key="MSG.generates.goods" /></td>
                    <td class="oddColumn">
                        <%--<s:select name="reportNiceNumberForm.stockModelId"
                                  id="stockModelId"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn mặt hàng--"
                                  list="%{#request.listStockModel!=null ? #request.listStockModel : #{}}"
                                  listKey="stockModelId" listValue="name"/>--%>
                        <tags:imSelect name="reportNiceNumberForm.stockModelId"
                                       id="stockModelId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.168"
                                       list="listStockModel"
                                       listKey="stockModelId" listValue="name"                                       
                                       />
                    </td>
                    <td><tags:label key="MSG.RET.078" /></td>
                    <td>
                        <%--<s:select name="reportNiceNumberForm.ruleId"
                                  id="rulesId"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn luật--"
                                  list="%{#request.listRule != null ? #request.listRule : #{}}"
                                  listKey="rulesId" listValue="name"/>--%>
                        <tags:imSelect name="reportNiceNumberForm.ruleId"
                                       id="rulesId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.169"
                                       list="listRule"
                                       listKey="rulesId" listValue="name"
                                       />
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <div align="center" style="padding-top:3px;">
            <tags:submit formId="reportNiceNumberForm"
                         showLoadingText="true"
                         cssStyle="width:100px;"
                         targets="bodyContent"
                         validateFunction="checkValidFields()"
                         value="MSG.report.export"
                         preAction="reportNiceNumberAction!reportNiceNumber.do"/>
            <%--
            <s:if test="#session.listNiceNumber != null && #session.listNiceNumber.size() > 0">
                <tags:submit formId="reportNiceNumberForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="Excel..."
                             preAction="reportNiceNumberAction!expList2Excel.do"/>
            </s:if>
            <s:else>
                <input type="button" style="width:80px;" disabled value="Excel..."/>
            </s:else>
            --%>
        </div>
    </div>
    <div>
        <!--jsp:include page="listNiceNumber.jsp"/-->
    </div>

</tags:imPanel>

<!--s:if test="#session.listNiceNumber != null && #session.listNiceNumber.size() > 0"-->
<s:if test="reportNiceNumberForm.pathExpFile!=null && reportNiceNumberForm.pathExpFile!=''">
    <script>
        <%--
        window.open('${contextPath}<s:property escapeJavaScript="true"  value="reportNiceNumberForm.pathExpFile"/>','','toolbar=yes,scrollbars=yes');
        --%>
            window.open('<s:property escapeJavaScript="true"  value="reportNiceNumberForm.pathExpFile"/>','','toolbar=yes,scrollbars=yes');
    </script>
    <div>
        <%--
        <a href='${contextPath}<s:property escapeJavaScript="true"  value="reportNiceNumberForm.pathExpFile"/>'
        --%>
        <a href='<s:property escapeJavaScript="true"  value="reportNiceNumberForm.pathExpFile"/>'
           ><tags:label key="MSG.clickhere.to.download" /></a></div>
    </s:if>
<!--/s:if-->

<script language="javascript">

    //focus vao truong dau tien
    $('fromIsdn').focus();

    //hien thi textbox price theo dinh dang tien
    textFieldNF($('fromPrice'));
    textFieldNF($('toPrice'));


    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        var fromPrice = trim($('fromPrice').value.replace(/\,/g,"")); //loai bo dau , trong chuoi
        var toPrice = trim($('toPrice').value.replace(/\,/g,"")); //loai bo dau , trong chuoi

        if(fromPrice != "" && !isInteger(trim(fromPrice))) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường Từ giá phải là số không âm";--%>
                $('message').innerHTML='<s:text name="ERR.RET.043"/>';
                $('fromPrice').select();
                $('fromPrice').focus();
                return false;
            }

            if(toPrice != "" && !isInteger(trim(toPrice))) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường Đến giá phải là số không âm";--%>
                $('message').innerHTML='<s:text name="ERR.RET.044"/>';
                $('toPrice').select();
                $('toPrice').focus();
                return false;
            }

            if(fromPrice != "" && toPrice != "" && (fromPrice * 1 > toPrice * 1)) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường Từ giá không được lớn hơn trường đến giá";--%>
                $('message').innerHTML='<s:text name="ERR.RET.047"/>';
                $('fromPrice').select();
                $('fromPrice').focus();
                return false;
            }

            var fromIsdn = trim($('fromIsdn').value);
            var toIsdn = trim($('toIsdn').value);

            if(fromIsdn != "" && !isInteger(trim(fromIsdn))) {
                $('message').innerHTML='<s:text name="ERR.RET.045"/>';
                //$('message').innerHTML = "!!!Lỗi. Trường Từ số isdn phải là số không âm";
                $('fromIsdn').select();
                $('fromIsdn').focus();
                return false;
            }

            if(toIsdn != "" && !isInteger(trim(toIsdn))) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường Đến số isdn phải là số không âm";--%>
                $('message').innerHTML='<s:text name="ERR.RET.046"/>';
                $('toIsdn').select();
                $('toIsdn').focus();
                return false;
            }

            if(fromIsdn != "" && toIsdn != "" && (fromIsdn * 1 > toIsdn * 1)) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường Từ số isdn không được lớn hơn trường đến số isdn";--%>
                $('message').innerHTML='<s:text name="ERR.RET.048"/>';
                $('fromIsdn').select();
                $('fromIsdn').focus();
                return false;
            }

            //trim cac truong can thiet
            $('fromPrice').value = trim($('fromPrice').value);
            $('toPrice').value = trim($('toPrice').value);
            $('fromIsdn').value = trim($('fromIsdn').value);
            $('toIsdn').value = trim($('toIsdn').value);

            return true;
        }


        //cap nhat lai danh sach mat hang, danh sach tap luat
        changeStockType = function(stockTypeId) {
            updateData("${contextPath}/lookupIsdnAction!changeStockType.do?target=stockModelId,groupFilterRuleCode,filterTypeId,rulesId&stockTypeId=" + stockTypeId);
        }

        //xu ly su kien khi GroupFilterRuleCode change
        changeGroupFilterRuleCode = function(groupFilterRuleCode) {
            updateData("${contextPath}/lookupIsdnAction!changeGroupFilterRuleCode.do?target=filterTypeId,rulesId&groupFilterRuleCode=" + groupFilterRuleCode);
        }

        //xu ly su kien khi FilterType change
        changeFilterType = function(filterId) {
            updateData("${contextPath}/lookupIsdnAction!changeFilterType.do?target=rulesId&filterId=" + filterId);
        }
</script>
