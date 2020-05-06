<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : viewSerialProcess
    Created on : Feb 25, 2010, 8:52:52 AM
    Author     : TheTM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>


<%
            request.setAttribute("contextPath", request.getContextPath());

%>
<s:form action="ViewStockDetailAction" id="serialGoods" theme="simple">
<s:token/>

    <sx:div id="detailSerial">
        <input type="hidden"  name="pageId" id="pageId"/>
        <s:hidden name="serialGoods.stockModelId" id="stockModelId"/>

        <s:hidden name="serialGoods.stockTypeId" id="stockTypeId"/>
        <s:hidden name="serialGoods.ownerId" id="ownerId"/>
        <s:hidden name="serialGoods.ownerType" id="ownerType"/>

        <s:hidden name="serialGoods.stateId" id="stateId"/>
        <s:hidden name="serialGoods.actionId" id="actionId"/>
        <!-- phan hien thi danh sach cac dai serial-->
        <div style="width:100%">
            <tags:imPanel title="MSG.store.infor">
                <table class="inputTbl4Col">
                    <tr>
                        <td class="label"> <tags:label key="MSG.store.code"/></td>
                        <td class="text"><s:textfield name="serialGoods.ownerCode" id="ownerCode" readonly="true" cssClass="txtInputFull"/></td>
                        <td class="label"> <tags:label key="MSG.storeName"/></td>
                        <td class="text"><s:textfield name="serialGoods.ownerName" id="ownerName" readonly="true" cssClass="txtInputFull"/></td>
                    </tr>
                    <tr>
                        <td class="label"> <tags:label key="MSG.generates.goods"/></td>
                        <td class="text"><s:textfield name="serialGoods.stockModelName" id="stockModelName" readonly="true" cssClass="txtInputFull"/></td>
                        <td class="label"> <tags:label key="MSG.generates.status"/></td>
                        <td class="text">
                            <s:if test="serialGoods.stateId==1"><input type="text" class="txtInputFull" readonly="true" value="Hàng mới"/></s:if>
                            <s:if test="serialGoods.stateId==3"><input type="text" class="txtInputFull" readonly="true" value="Hàng hỏng"/></s:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="label"> <tags:label key="MSG.quantityStoreAfterTrans"/></td>
                        <td class="text"><s:textfield name="serialGoods.total" id="total" readonly="true" cssClass="txtInputFull"/></td>
                        <td class="label"> <tags:label key="MSG.quantityResponse"/></td>
                        <td class="text"><s:textfield name="serialGoods.totalReq" id="totalReq" readonly="true" cssClass="txtInputFull"/></td>
                    </tr>
                    <tr>
                        <td colspan="4" align="center"><input type="button" onclick="balanceStockGoods();"  value="${fn:escapeXml(imDef:imGetText('MSG.balanceStock'))}"/></td>
                    </tr>
                    <tr>
                        <td colspan="4" align="center">
                            <tags:displayResult property="returnMsg" id="returnMsgClient" type="key" />
                        </td>
                    </tr>
                </table>
            </tags:imPanel>
            
            <br/>
            <s:url action="ViewStockDetailAction" method="viewDetailSerialProcess" id="viewDetailUrl" escapeAmp="false">
                <s:param name="ownerType" value="%{serialGoods.ownerType}"/>
                <s:param name="ownerId" value="%{serialGoods.ownerId}"/>
                <s:param name="stockModelId" value="%{serialGoods.stockModelId}"/>
                <s:param name="stateId" value="%{serialGoods.stateId}"/>
            </s:url>

            <display:table id="tblLstSerial" name="lstSerial" class="dataTable" cellpadding="1" cellspacing="1"  pagesize="100" requestURI="${fn:escapeXml(viewDetailUrl)}" >
                <display:column title="${fn:escapeXml(imDef:imGetText('tbl.th.viewSerial.orderNumber'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                    ${fn:escapeXml(tblLstSerial_rowNum)}
                </display:column>
                <display:column  escapeXml="true" property="fromSerial" title="${fn:escapeXml(imDef:imGetText('MSG.stock.from.serial'))}" sortable="false"  headerClass="tct"/>
                <display:column escapeXml="true" property="toSerial" title="${fn:escapeXml(imDef:imGetText('MSG.stock.to.serial'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="quantity" title="${fn:escapeXml(imDef:imGetText('MSG.quantity'))}" sortable="false" headerClass="tct"/>
            </display:table>


        </div>
        <br/>
        <div style="width:100%" align="center">
            <s:if test="serialGoods.exportUrl!=null && serialGoods.exportUrl!=''">
                <script>
                    window.open('${contextPath}<s:property escapeJavaScript="true"  value="serialGoods.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                </script>
                <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="serialGoods.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>

            </s:if>
        </div>
    </sx:div>
</s:form>
<script>
    exportSerial= function(){
  
        setAction("serialGoods","ViewStockDetailAction!exportSerial.do?exportViewDetail=true");
    }
    balanceStockGoods= function(){
        //if(confirm("Bạn có muốn cân kho cho tên hàng này ?"))
        var strConfirm = getUnicodeMsg('<s:text name="MSG.STK.003"/>');
        if(confirm(strConfirm)){
            setAction("serialGoods","ViewStockDetailAction!balanceStockGoods.do?" + token.getTokenParamString());
        }
    }
   
</script>
