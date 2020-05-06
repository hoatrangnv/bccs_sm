<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : AddSingleStockOwnerTmp
               : Gán hạn mức cho nhân viên đơn lẻ
    Created on : Jul 28, 2011, 10:39:16 AM
    Author     : kdvt_phuoctv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%
    if (request.getAttribute("listChannelType") == null) {
        request.setAttribute("listChannelType", request.getSession().getAttribute("listChannelType"));
    }
    request.setAttribute("contextPath", request.getContextPath());
%>
<tags:imPanel title="label.singleStaff.maxDebit">
    <s:form action="stockOwnerTmpAction" theme="simple" method="post" id="stockOwnerTmpForm">
<s:token/>

        <fieldset class="imFieldset">
            <%--<legend>
                <s:if test="#stockOwnerTmpForm.stockId > 0">
                    <s:text name="label.singleStaff.maxDebit.edit"/>
                </s:if>
                <s:else>
                    <s:text name="MSG.search.info"/>
                </s:else>
            </legend>--%>
            <s:hidden name="stockOwnerTmpForm.stockId" id="stockId"/>
            <s:hidden name="stockOwnerTmpForm.prevShopCode" id="prevShopCode"/>
            <s:hidden name="stockOwnerTmpForm.prevStaffCode" id="prevStaffCode"/>
            <s:hidden name="stockOwnerTmpForm.prevChannelTypeId" id="prevChannelTypeId"/>
            <s:hidden name="stockOwnerTmpForm.prevMaxDebit" id="prevMaxDebit"/>
            <table class="inputTbl6Col">
                <tr>
                    <td colspan="4" align="center">
                        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="paramMsg" type="key" />
                    </td>
                </tr>
                <tr>
                    <td class="label" style="width:10%">
                        <tags:label key="MSG.shop.code"/>
                    </td>
                    <td>
                        <tags:imSearch codeVariable="stockOwnerTmpForm.shopCode" nameVariable="stockOwnerTmpForm.shopName"
                                       codeLabel="MSG.shop.code" nameLabel="MSG.shop.name" readOnly="${fn:escapeXml(isUpdating)}"
                                       searchClassName="com.viettel.im.database.DAO.StockOwnerTmpDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName" elementNeedClearContent="stockOwnerTmpForm.staffCode;stockOwnerTmpForm.staffName"
                                       roleList=""/>
                    </td>
                    <td class="label" style="width:10%"><tags:label key="MES.CHL.058" /></td>
                    <td>
                        <tags:imSelect name="stockOwnerTmpForm.channelTypeId"
                           id="stockOwnerTmpForm.channelTypeId"
                           cssClass="txtInputFull" disabled="${fn:escapeXml(isUpdating)}"
                           theme="simple" onchange="changeChannelType();"
                           headerKey="" headerValue="label.option"
                           list="listChannelType" listKey="channelTypeId" listValue="name"
                           />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.staff.code"/></td>
                    <td>
                        <tags:imSearch codeVariable="stockOwnerTmpForm.staffCode" nameVariable="stockOwnerTmpForm.staffName"
                                       codeLabel="MSG.staff.code" nameLabel="MSG.staff.name" readOnly="${fn:escapeXml(isUpdating)}"
                                       searchClassName="com.viettel.im.database.DAO.StockOwnerTmpDAO"
                                       searchMethodName="getListStaff"
                                       getNameMethod="getStaffName"
                                       otherParam="stockOwnerTmpForm.shopCode;stockOwnerTmpForm.channelTypeId"
                                       roleList=""/>
                    </td>
                    <td class="label">
                        <s:if test="#request.isUpdating != null">
                            <tags:label key="label.maxDebit" required="true"/>
                        </s:if>
                        <s:else>
                            <tags:label key="label.maxDebit" />
                        </s:else>
                    </td>
                    <td>
                        <s:textfield name="stockOwnerTmpForm.maxDebit" id="stockOwnerTmpForm.maxDebit" maxlength="12" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
            <div align="center" style="vertical-align:top">
                <s:if test="#request.isUpdating != null">
                    <tags:submit targets="bodyContent" formId="stockOwnerTmpForm"
                                 cssStyle="width:80px;" value="MSG.update"
                                 validateFunction="validateInput();"
                                 confirm="true" confirmText="MSG.GOD.017"
                                 showLoadingText="true"
                                 preAction="stockOwnerTmpAction!updateStockOwnerTmp.do"
                                 />
                    <tags:submit targets="bodyContent" formId="stockOwnerTmpForm"
                                 cssStyle="width:80px;" value="MSG.GOD.018"
                                 showLoadingText="true"
                                 preAction="stockOwnerTmpAction!preparePage.do"
                                 />
                </s:if>
                <s:else>
                    <%-- <tags:submit targets="bodyContent" formId="stockOwnerTmpForm"
                                 validateFunction="validateInput();"
                                 cssStyle="width:80px;" value="MSG.GOD.010"
                                 confirm="true" confirmText="confirm.addStockOwnerTmp"
                                 showLoadingText="true"
                                 preAction="stockOwnerTmpAction!addStockOwnerTmp.do"
                                 /> --%>

                    <tags:submit targets="bodyContent" formId="stockOwnerTmpForm"
                                 cssStyle="width:80px;" value="MSG.GOD.009"
                                 showLoadingText="true" validateFunction="validateInputSearch();"
                                 preAction="stockOwnerTmpAction!searchStockOwnerTmp.do"
                                 />
                </s:else>
            </div>
        </fieldset>
    </s:form>
    <br>
    <fieldset class="imFieldset">
        <legend>
            <s:text name="MSG.search.result"/>
        </legend>
        <sx:div id="divSearchResult" theme="simple">
            <jsp:include page="ListStockOwnerTmp.jsp"/>
        </sx:div>
    </fieldset>
</tags:imPanel>
<script type="text/javascript">
    changeChannelType = function() {
        document.getElementById("stockOwnerTmpForm.staffCode").value = "";
        document.getElementById("stockOwnerTmpForm.staffName").value = "";
    }

    validateInput = function() {
        var staff = document.getElementById("stockOwnerTmpForm.staffCode");
        var maxDebit = document.getElementById("stockOwnerTmpForm.maxDebit");
        if(staff.value == null || staff.value == "") {
            /*Chưa nhập mã nhân viên*/
            $("displayResultMsgClient").innerHTML = "<s:text name="ERR.LIMIT.0003"/>";
            staff.focus();
            return false;
        }
        if(maxDebit.value == null || maxDebit.value == "") {
            /*Chưa nhập hạn mức*/
            $("displayResultMsgClient").innerHTML = "<s:text name="ERR.LIMIT.0004"/>";
            maxDebit.focus();
            return false;
        }
        if(!isNumeric(trim(maxDebit.value))) {
            /*nhap ko dung kieu double*/
            $("displayResultMsgClient").innerHTML = "<s:text name="ERR.LIMIT.0005"/>";
            maxDebit.focus();
            return false;
        }

        return true;
    }
    validateInputSearch = function() {
        var maxDebit = document.getElementById("stockOwnerTmpForm.maxDebit");
        if(maxDebit.value != null && trim(maxDebit.value) != "") {
            if(!isNumeric(trim(maxDebit.value))) {
                /*nhap ko dung kieu double*/
                $("displayResultMsgClient").innerHTML = "<s:text name="ERR.LIMIT.0005"/>";
                maxDebit.focus();
                return false;
            }
        }
        return true;
    }
</script>
