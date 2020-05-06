<%-- 
    Document   : ReportInvoiceDailyRemain
    Created on : May 7, 2010, 8:51:24 AM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("bookTypeList", request.getSession().getAttribute("bookTypeList"));
%>

<tags:imPanel title="MSG.criterion.report.create">
    
    <s:form action="reportInvoiceDailyRemainAction" theme="simple" method="post" id="reportInvoiceDailyRemainForm">
<s:token/>

            <table class="inputTbl4Col">
                <tr>
                    <td class="label"><tags:label key="MSG.shop.code" required="true"/>  </td>
                    <td class="text" colspan="3">
                        <tags:imSearch codeVariable="reportInvoiceDailyRemainForm.shopCode" nameVariable="reportInvoiceDailyRemainForm.shopName"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.ReportAccountAgentDAO"
                                       searchMethodName="getListShop"
                                       roleList=""/>
                    </td>
                </tr>
                <tr>
                    <td class ="label"><tags:label key="MSG.staff.code" /></td>
                    <td class="text" colspan="3">
                        <tags:imSearch codeVariable="reportInvoiceDailyRemainForm.staffCode" nameVariable="reportInvoiceDailyRemainForm.staffName"
                                       codeLabel="MSG.RET.047" nameLabel="MSG.RET.048"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStaff"
                                       getNameMethod="getStaffName"
                                       otherParam="reportInvoiceDailyRemainForm.shopCode"
                                       roleList=""/>
                        
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.sum.at.day" required="true"/> </td>
                    <td class="text">
                        <tags:dateChooser property="reportInvoiceDailyRemainForm.remainDate"/>
                    </td>
                    
                    <td class="label"><tags:label key="MSG.bill.type" /> </td>
                    <td class="text">
                        <%--<s:select name="reportInvoiceDailyRemainForm.serialCode"
                                  id="reportInvoiceDailyRemainForm.serialCode"
                                  cssClass="txtInputFull"
                                  list="%{#session.bookTypeList != null ? #session.bookTypeList : #{}}"
                                  listKey="serialCode" listValue="serialCode"
                                  headerKey="" headerValue="--Loại hoá đơn--"/>--%>
                        <tags:imSelect name="reportInvoiceDailyRemainForm.serialCode"
                                   id="reportInvoiceDailyRemainForm.serialCode"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.RET.163"
                                   list="bookTypeList"
                                   listKey="serialCode" listValue="serialCode"/>
                    </td>
                </tr>
                <tr>
                    <td  class="label"><tags:label key="MSG.report.type" /></td>
                    <td colspan="3" class="text">
                        <fieldset style="width:100%;">
                            <%--<s:radio name="reportInvoiceDailyRemainForm.reportType"
                                     list="#{'1':'Cấp hiện tại &nbsp;&nbsp;', '2':'Bao gồm cấp dưới &nbsp;&nbsp;', '3':'Tất cả các cấp dưới &nbsp;&nbsp;'}"/>--%>
                            <tags:imRadio name="reportInvoiceDailyRemainForm.reportType" id="reportType"
                          list="1:MSG.RET.139,2:MSG.RET.141,3:MSG.RET.142"/>

                        </fieldset>
                    </td>
                </tr>

                <tr>
                <td colspan="4" align="center">
                    <tags:displayResult id="displayResultMsgClient" property="displayResultMsgClient" propertyValue="returnMsgValue" type="key"/>
                </td>
            </tr>

            </table>

        <div style="margin-top:3px;">

            <tags:submit formId="reportInvoiceDailyRemainForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.report"
                             preAction="reportInvoiceDailyRemainAction!exportInvoiceDailyRemain.do"/>
            <%--
            <tags:submit formId="reportInvoiceDailyRemainForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="Tra cứu"
                         preAction="reportInvoiceDailyRemainAction!searchInvoiceDailyRemain.do"/>

            <s:if test="#request.reportResultList != null && #request.reportResultList.size() > 0 ">
                <tags:submit formId="reportInvoiceDailyRemainForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="Excel ..."
                             preAction="reportInvoiceDailyRemainAction!exportInvoiceDailyRemain.do"/>
            </s:if>
            <s:else>
                <input type="button" value="Excel ..." style="width: 80px;" disabled/>
            </s:else>
            --%>
            
        </div>
            <br/>
        <div align="center">
            <s:if test="#request.filePath !=null && #request.filePath!=''">
                <script>
                    window.open('${contextPath}/download.do?${fn:escapeXml(filePath)}', '', 'toolbar=yes,scrollbars=yes');
                    <%--window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                    window.open('<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>
                </script>
                <div>
                     <a href="${contextPath}/download.do?${fn:escapeXml(filePath)}">
                           <tags:displayResult id="reportRevenueMessage" property="reportRevenueMessage" type="key"/>
                     </a>
                    <%--<a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a>
                    <a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>' ><tags:label key="MSG.clickhere.to.download" /></a>--%>
                </div>
            </s:if>
        </div>

    </s:form>

            <%--
    <s:if test="#request.reportResultList != null && #request.reportResultList.size() > 0 ">
        <tags:imPanel title="Danh sách hoá đơn tồn">
            <jsp:include page="ReportInvoiceDailyRemainList.jsp"/>
        </tags:imPanel>
    </s:if>
            --%>

</tags:imPanel>
