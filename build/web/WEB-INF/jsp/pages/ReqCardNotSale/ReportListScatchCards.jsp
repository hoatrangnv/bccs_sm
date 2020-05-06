<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : Report Bill
    Created on : 17/04/2009, 10:51:45 AM
    Author     :
    desc       : tra cuu hoa don
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>


<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="title.ReportListScatchCards.page">
    <s:form action="reportListScatchCardsAction" method="POST" id="reqCardNotSaleFrom" theme="simple">
        <s:token/>
        
        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
        </div>
        <table class="inputTbl6Col">
            <tr>
                <td class="label" ><tags:label key="MSG.SAE.109" required="true" /></td>
                <td>
                    <tags:imSearch codeVariable="reqCardNotSaleFrom.shopCode" nameVariable="reqCardNotSaleFrom.shopName"
                                   codeLabel="MSG.RET.066" nameLabel="MSG.RET.067"
                                   searchClassName="com.viettel.im.database.DAO.ExportListScatchCards"
                                   searchMethodName="getListShop"
                                   getNameMethod="getShopName"
                                  />
                </td>
            </tr>
               
            <tr>
                 <td><tags:label key="MSG.generates.status" /></td>
                <td>
                    <tags:imSelect name="reqCardNotSaleFrom.status" theme="simple"
                                   id="status"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.GOD.189"
                                   list="   0:MSG.Not.Approved.Request,
                                   1:MSG.Approved.Request,
                                   2:MSG.LOST.CARD,
                                   3:MSG.unLock"/>
                </td>
                
                <td><tags:label key="MSG.Status.Sold" /></td>
                <td>
                    <tags:imSelect name="reqCardNotSaleFrom.statusSold" theme="simple"
                                   id="statusSold"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.GOD.189"
                                   list="   0:MSG.Sold,
                                   1:MSG.UnSold"/>
                </td>
            </tr>
                
            <tr>
                <td  class="label"> <tags:label key="MSG.order.date.from" required="true"/></td>
                <td colspan="3" >
                    <tags:dateChooser property="reqCardNotSaleFrom.fromDate" id="fromDate"  />
                </td>
                <td  class="label"> <tags:label key="MSG.order.date.to" required="true"/></td>
                <td>
                    <tags:dateChooser property="reqCardNotSaleFrom.toDate" styleClass="txtInputFull" id="toDate" />
                </td>
            </tr>
        </table>
                
                    <div>
                        <tags:submit targets="bodyContent" formId="reqCardNotSaleFrom"
                                     value="MSG.RET.079"
                                     cssStyle="width:80px;"
                                     preAction="reportListScatchCardsAction!ExportListScathCards.do"
                                     showLoadingText="true"
                                     validateFunction="validateForm()"/>
                    </div>
                    <%--
                    <div>
                        <s:if test="#request.reportAccountPath != null">
                            <script>
                            window.open('${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}', '', 'toolbar=yes,scrollbars=yes');</script>

                            <a href="${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}">
                                <tags:displayResult id="filePathMessage" property="filePathMessage" type="key"/>
                            </a>
                        </s:if>
                    </div>
                    --%>
                    <div>
                        <s:if test="#request.reportAccountPath != null">
                            <script>
                                window.open('${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}','','toolbar=yes,scrollbars=yes');
                            </script>
                            <a href="${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}">
                                <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
                            </a>
                        </s:if>
                    </div>
    </s:form>
</tags:imPanel>
        
        
<script type="text/javascript" language="javascript">

    validateForm = function(){
        
        var compShopCode = document.getElementById("reqCardNotSaleFrom.shopCode");
            if (trim(compShopCode.value).length == 0){
                <%--"Bạn chưa mã Shop"--%>
                $('message').innerHTML = '<s:property value="getText('error.not.select.unit.export.competitor')"/>';
                compShopCode.focus();
                return false;
            }
          var dateExported= dojo.widget.byId("fromDate");
            if(dateExported.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo từ ngày';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.002"/>';
                $('fromDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ ngày không hợp lệ';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.003"/>';
                $('fromDate').focus();
                return false;
            }        
          var dateExported1 = dojo.widget.byId("toDate");
            if(dateExported1.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo đến ngày';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.004"/>';
                $('toDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported1.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo đến ngày không hợp lệ';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.005"/>';
                $('toDate').focus();
                return false;
            }
            if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ phải nhỏ hơn Ngày báo cáo đến ngày';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.006"/>';
                $('fromDate').focus();
                return false;
            }   
                
        return true;
    }


</script>
