<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : SaleAnyPayTransList
    Created on : Dec 26, 2009, 9:12:21 AM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("lstReasonDestroy", request.getSession().getAttribute("lstReasonDestroy"));
%>

<div id="resultArea">
    <s:if test="#request.lstAnyPayTrans != null && #request.lstAnyPayTrans.size() != 0">
        <tags:imPanel title="MSG.SIK.117">
            <inventoryDisplay:table targets="resultArea" id="anyPayTrans"
                                    name="lstAnyPayTrans" pagesize="1000"
                                    class="dataTable" cellpadding="1" cellspacing="1"
                                    requestURI="SaleToAnyPayAction!pageNavigator.do" >
                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.147'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px" >
                    ${fn:escapeXml(anyPayTrans_rowNum)}
                </display:column>
                <display:column escapeXml="true"  property="transId" title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.181'))}" sortable="false" headerClass="tct" style="text-align:left;"/>
                <display:column escapeXml="true"  property="transType" title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.182'))}" sortable="false" headerClass="tct" style="text-align:left;"/>
                <display:column escapeXml="true"  property="sourceIsdn" title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.183'))}" sortable="false" style="text-align:right;" headerClass="tct"/>
                <display:column escapeXml="true"  property="targetIsdn" title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.184'))}" sortable="false" style="text-align:right;" headerClass="tct"/>
                <display:column property="amount" title="${fn:escapeXml(imDef:imGetText('MSG.SIK.185'))}" sortable="false" headerClass="tct" style="text-align:right;" format="{0}" />
                <display:column property="createDate" title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.186'))}" sortable="false"  format="{0,date,dd/MM/yyyy HH:mm:ss}"  headerClass="tct" style="text-align:right;"/>
                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.002'))}" sortable="false" headerClass="tct" style="text-align:center; width:100px" >
                    <s:if test = "#attr.anyPayTrans.processStatus*1 == 3">
                        <!--                        <tags_:label key="MSG.SIK.187"/>-->
                        <%--Tạo giao dịch--%>
                        Created
                    </s:if>
                    <s:else>
                        <%--Hủy giao dịch--%>
                        <!--                        <tags_:label key="MSG.SIK.188"/>-->
                        Cancelled
                    </s:else>
                </display:column>
                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.189'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px" >
                    <s:if test = "#attr.anyPayTrans.processStatus*1 == 3">
                        <s:checkbox name="anyPayTransForm.pTransIdList" fieldValue="%{#attr.anyPayTrans.transId}" id="pTransIdList" theme="simple">
                        </s:checkbox>
                    </s:if>
                    <s:else>-</s:else>
                </display:column>
            </inventoryDisplay:table>
            <table>
                <tr><td colspan="3"></td></tr>
                <tr>
                    <td>&nbsp;</td>
                    <td style="text-align:right;">
                        <tags:submit targets="bodyContent" formId="anyPayTransForm"
                                     value="MSG.SIK.189" cssStyle="width:80px;"
                                     showLoadingText="true" validateFunction="validateDelete()"
                                     confirm="true" confirmText="MSG.STK.004"
                                     preAction="SaleAnyPayToAgentOrStaffAction!cancelAnyPayTrans.do"/>
                    </td>
                    <td>&nbsp;</td>
                </tr>



                <tr><td colspan="3"></td></tr>
            </table>
        </tags:imPanel>
    </s:if>
</div>

<script type="text/javascript" language="javascript">
    
    validateDelete = function(){
        if (!isCheckedBox()){
    <%--$( 'returnMsgClient' ).innerHTML='Chưa chọn giao dịch nào!';--%>
                $('returnMsgClient').innerHTML='<s:text name="MSG.SIK.191"/>';
                return false;
            }
            //            if ($('pReasonId').value.trim() == ''){
            //                $('returnMsgClient').innerHTML='<s_:text name="MSG.SIK.192"/>';
            //                return false;
            //            }
            return true;
        }
    
        isCheckedBox = function(){
            var tbl = $('anyPayTrans');
            inputs = tbl.getElementsByTagName( "input" );
            for( i = 0; i < inputs.length; i++ ) {
                if( inputs[i].type == "checkbox" && inputs[i].checked == true ) {
                    return true;
                }
            }
            return false;
        }
</script>
