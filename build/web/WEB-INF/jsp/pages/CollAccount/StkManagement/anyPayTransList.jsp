<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : anyPayTransList
    Created on : Oct 23, 2009, 11:34:09 AM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<div id="resultArea">
    <s:if test="#request.lstAnyPayTrans != null && #request.lstAnyPayTrans.size() != 0">
        <tags:imPanel title="Danh sách giao dịch">
                <inventoryDisplay:table targets="resultArea" id="anyPayTrans"
                                    name="lstAnyPayTrans" pagesize="1000"
                                    class="dataTable" cellpadding="1" cellspacing="1"
                                    requestURI="SaleToAnyPayAction!pageNavigator.do" >
                <display:column title="STT" sortable="false" headerClass="tct" style="text-align:center; width:50px" >
                ${fn:escapeXml(anyPayTrans_rowNum)}
                </display:column>
                <display:column escapeXml="true"  property="transId" title="Mã giao dịch" sortable="false" headerClass="tct" style="text-align:left;"/>
                <display:column escapeXml="true"  property="transTypeDes" title="Loại giao dịch" sortable="false" headerClass="tct" style="text-align:left;"/>
                <display:column escapeXml="true"  property="ownerCode" title="Mã CTV/Đại lý" sortable="false" style="text-align:left;" headerClass="tct"/>
                <display:column escapeXml="true"  property="isdnSent" title="Số ISDN gửi" sortable="false" style="text-align:right;" headerClass="tct"/>
                <display:column escapeXml="true"  property="isdnReceive" title="Số ISDN nhận" sortable="false" style="text-align:right;" headerClass="tct"/>
                <display:column property="amount" title="Số tiền" sortable="false" headerClass="tct" style="text-align:right;" format="{0}" />
                <display:column property="transDateTime" title="Ngày giao dịch" sortable="false"  format="{0,date,dd/MM/yyyy HH:mm:ss}"  headerClass="tct" style="text-align:left;"/>
                <display:column escapeXml="true"  property="statusDes" title="Trạng thái" sortable="false" style="text-align:left;" headerClass="tct"/>
                
                <display:column title="Hủy GD" sortable="false" headerClass="tct" style="text-align:center; width:50px" >                    
                    <s:if test = "#attr.anyPayTrans.status*1 == 1">                        
                        <s:a href="" onclick="aOnclick(%{#attr.anyPayTrans.transId})">
                            Huỷ GD
                        </s:a>                        
                    </s:if>
                    <s:else>-</s:else>                    
                </display:column>                
            </inventoryDisplay:table>
            
            
        </tags:imPanel>
    </s:if>
</div>

<script type="text/javascript" language="javascript">
    
    validateDelete = function(){
        if (!isCheckedBox()){
            $( 'returnMsgClient' ).innerHTML='Chưa chọn giao dịch nào!';
            return false;
        }
        if ($('pReasonId').value.trim() == ''){
            $( 'returnMsgClient' ).innerHTML='Chưa chọn lý do huỷ GD!';
            return false;
        }
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
    
    aOnclick = function(transId) {
        if (!confirm("Bạn có chắc chắn muốn huỷ giao dịch?")){
            return;
        }
        gotoAction('bodyContent','SaleToAnyPayAction!cancelOneAnyPayTrans.do?transId=' + transId +"&"+ token.getTokenParamString(),'anyPayTransForm');
    }

</script>
