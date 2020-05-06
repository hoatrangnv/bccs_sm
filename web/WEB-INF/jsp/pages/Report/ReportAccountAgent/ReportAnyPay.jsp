<%-- 
    Document   : ReportAnyPay
    Created on : Apr 2, 2010, 11:03:26 AM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>


<!-- phan hien thi tieu chi bao cao -->
<s:form action="reportAnyPayAction" theme="simple" method="post" id="form">
<s:token/>

    <tags:imPanel title="MSG.criterion.report.create">
        <table class="inputTbl4Col">

            <tr>
                <td style="width:10%; "><tags:label key="MSG.shop.code"  required="true"/></td>
                <td style="width:30%;">
                    <tags:imSearch codeVariable="form.shopCode" nameVariable="form.shopName"
                                   codeLabel="MSG.shop.code" nameLabel="MSG.shop.name"
                                   searchClassName="com.viettel.im.database.DAO.ReportAccountAgentDAO"
                                   searchMethodName="getListShop"
                                   roleList=""/>
                </td>

                <%--<td style="width:10%; ">Cửa hàng</td>
                <td style="width:30%; " class="oddColumn" colspan="1">
                    <tags:imSearch codeVariable="form.shopCode" nameVariable="form.shopName"
                                   codeLabel="Mã đơn vị" nameLabel="Tên đơn vị"
                                   searchClassName="com.viettel.im.database.DAO.ReportAnyPayDAO"
                                   searchMethodName="getListShop"/>
                </td>--%>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.channel" /></td>
                <td class="text">
                    <%--<s:select name="form.ownerType"
                              id="ownerType"
                              cssClass="txtInputFull"
                              theme="simple"
                              headerKey="" headerValue="--Kênh--"
                              list="#{'1':'Đại lý','2':'CTV/ĐB'}"
                              />--%>
                    <tags:imSelect name="form.ownerType" id="ownerType"
                                   cssClass="txtInputFull" disabled="false"
                                   list="1:MSG.RET.015,2:MSG.RET.018"
                                   headerKey="" headerValue="MSG.RET.150"/>
                </td>
                <td class="label"><tags:label key="MSG.money.change.type" /></td>
                <td class="text">
                    <%-- <s:select name="form.transType"
                               id="transType"
                               cssClass="txtInputFull"
                               theme="simple"
                               headerKey="" headerValue="--Hình thức--"
                               list="#{'LOAD':'Nạp tiền','TRANS':'Chuyển tiền'}"
                               />--%>
                    <tags:imSelect name="form.transType" id="transType"
                                   cssClass="txtInputFull" disabled="false"
                                   list="LOAD:MSG.RET.019,TRANS:MSG.RET.020"
                                   headerKey="" headerValue="MSG.RET.151"/>
                </td>
            </tr>
            <tr>
                <td  class="label"><tags:label key="MSG.fromDate" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser property="form.fromDate" id="fromDate"  cssStyle="txtInput"/>
                </td>
                <td  class="label"><tags:label key="MSG.generates.to_date" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser property="form.toDate" id="toDate" cssStyle="txtInput"/>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <div class="divHasBorder" style="margin-top:10px; padding:3px;">
                        <%--<s:radio name="form.reportType" id ="reportType" list="#{'1':'Tổng hợp', '2':'Chi tiết'}"/>--%>
                        <tags:imRadio name="form.reportType" id="reportType"
                                      list="1:MSG.RET.115,2:MSG.RET.116"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <tags:submit formId="form"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodyContent"  validateFunction="checkValidFields();"
                                 value="MSG.report" 
                                 preAction="reportAnyPayAction!reportAnyPay.do" />
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <tags:displayResult id="displayResultMsgClient" property="displayResultMsgClient" propertyValue="returnMsgValue" type="key"/>
                </td>

            </tr>
            <tr>
                <td colspan="4" align="center">
                    <s:if test="#request.filePath !=null && #request.filePath!=''">
                        <script>                            
                            <%--window.open(<'${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>
                                window.open('<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                        </script>
                        <div>                            
                            <%--<a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>'>Bấm vào đây để download nếu trình duyệt không tự động download về.</a>--%>
                            <a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>'><tags:label key="MSG.clickhere.to.download" /></a>
                        </div>
                    </s:if>
                </td>
            </tr>
        </table>
    </tags:imPanel>
</s:form>




<script language="javascript">

    checkValidFields = function() {
                var shopCode = $("form.shopCode");
        if(shopCode == null || shopCode.value==null || shopCode.value.trim()==""){
            shopCode.focus();
    <%--$( 'displayResultMsgClient' ).innerHTML = "Chưa nhập mã cửa hàng/đại lý";--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.010"/>';
                return false;
            }
			
		
            var dateExported= dojo.widget.byId("fromDate");
            if(dateExported.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo từ';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.002"/>';
                                                dateExported.domNode.childNodes[1].select();
            dateExported.domNode.childNodes[1].focus();
                return false;
            }
            if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ ngày không hợp lệ';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.003"/>';
                                                dateExported.domNode.childNodes[1].select();
            dateExported.domNode.childNodes[1].focus();
                return false;
            }

            var dateExported1 = dojo.widget.byId("toDate");
            if(dateExported1.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo đến';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.004"/>';
                dateExported1.domNode.childNodes[1].select();
            dateExported1.domNode.childNodes[1].focus();
                return false;
            }
            if(!isDateFormat(dateExported1.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo đến ngày không hợp lệ';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.005"/>';
                dateExported1.domNode.childNodes[1].select();
            dateExported1.domNode.childNodes[1].focus();
                return false;
            }
            if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ phải nhỏ hơn Ngày báo cáo đến';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.006"/>';
                                                dateExported.domNode.childNodes[1].select();
            dateExported.domNode.childNodes[1].focus();
                return false;
            }
            return true;
        }

        dojo.event.topic.subscribe("form/autoSelectShop", function(value, key, text, widget){
            if(key!=null && value!=null){
                $('form.shopName').value=key;
            }

        });

</script>
