<%-- 
    Document   : ConfirmReceivedBills.jsp
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     : TungTV
    Desc       : xac nhan da nhan hoa don
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<tags:imPanel title="MSG.accept.imp.invoice">
    <!-- hien thi message -->
    <div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
    </div>

    <s:form action="confirmReceivedBillsAction" method="post" id="form" theme="simple">
        <!-- phan thong tin tim kiem dai hoa don can xac nhan -->
        <div class="divHasBorder">
            <table class="inputTbl7Col">
                <tr>
                    <td style="width:13%; "><tags:label key="MSG.consign.date.from"/></td>
                    <td class="oddColumn" style="width:20%; ">
                        <tags:dateChooser  property="form.fromDateB" id="fromDateB" readOnly="false" styleClass="txtInputFull"/>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.consign.date.to"/></td>
                    <td class="oddColumn" style="width:20%; ">
                        <tags:dateChooser  property="form.toDateB" id="toDateB" readOnly="false" styleClass="txtInputFull"/>
                    </td>
                    <td style="width:10%; "> <tags:label key="MSG.generates.status"/></td>
                    <td class="oddColumn" style="width:20%; ">
                        <tags:imSelect name="form.billSituation" id="billSituation"
                                  cssClass="txtInput"
                                  list="1:MSG.GOD.257,2:MSG.GOD.258"
                                  headerKey="" headerValue="MSG.GOD.260"/>
                    </td>
                    <td style="text-align: right; ">
                        <tags:submit targets="bodyContent" formId="form"
                                     value="MSG.find"
                                     cssStyle="width:80px;"
                                     preAction="confirmReceivedBillsAction!searchBills.do"
                                     showLoadingText="true" validateFunction="validateSearch();"/>
                    </td>
                </tr>
            </table>
        </div>

        <div>
            <jsp:include page="ConfirmReceivedBillsSearchResult.jsp"/>
        </div>

        <div class="divHasBorder" style="padding:3px; margin-top:5px; text-align:center;">
            <s:if test="#request.invoiceList != null && #request.invoiceList.size() != 0">
                <tags:submit targets="bodyContent" formId="form"
                             value="MSG.confirm" cssStyle="width:80px;"
                             preAction="confirmReceivedBillsAction!confirmReceived.do"
                             showLoadingText="true" validateFunction="validateRecevied();"/>
            </s:if>
            <s:else>
                <tags:submit targets="bodyContent" formId="form"
                             disabled="true"
                             value="MSG.confirm" cssStyle="width:80px;"
                             preAction="confirmReceivedBillsAction!confirmReceived.do"
                             showLoadingText="true" validateFunction="validateRecevied();"/>
            </s:else>
        </div>
        <s:token/>
    </s:form>
</tags:imPanel>

<script type="text/javascript" language="javascript">
    
    var _myWidget1 = dojo.widget.byId("fromDateB");
    if (    _myWidget1 != null)
        _myWidget1.domNode.childNodes[1].focus();

    validateSearch = function (){
        //MrSun
        var checkDate = true;
        var dateExported= dojo.widget.byId("fromDateB");
        if(dateExported.domNode.childNodes[1].value.trim() != "" &&
            !isDateFormat(dateExported.domNode.childNodes[1].value)){
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.017')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.017"/>';
            //$( 'displayResultMsgClient' ).innerHTML='Ngày giao từ không hợp lệ';
            dateExported.domNode.childNodes[1].focus();
            return;
        }
        if (dateExported.domNode.childNodes[1].value.trim() == "")
            checkDate = false;

        var dateExported1 = dojo.widget.byId("toDateB");
        if(dateExported1.domNode.childNodes[1].value.trim() != "" &&
            !isDateFormat(dateExported1.domNode.childNodes[1].value)){
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.018')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.018"/>';
            //$( 'displayResultMsgClient' ).innerHTML='Ngày giao đến không hợp lệ';
            dateExported1.domNode.childNodes[1].focus();
            return;
        }
        if (dateExported1.domNode.childNodes[1].value.trim() == "")
            checkDate = false;

        if (checkDate)
            if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
//                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.019')"/>';
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.019"/>';
                //$( 'displayResultMsgClient' ).innerHTML='Ngày giao từ phải nhỏ hơn Ngày giao đến';
                dateExported.domNode.childNodes[1].focus();
                dateExported.domNode.childNodes[1].select();
                return false;
            }
        //MrSun
        return true;
    }

</script>
