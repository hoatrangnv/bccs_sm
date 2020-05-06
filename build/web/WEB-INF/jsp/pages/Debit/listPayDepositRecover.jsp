<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listPayDepositRecover
    Created on : Sep 29, 2009, 11:02:24 AM
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@page  import="com.viettel.im.common.Constant" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
            if (request.getAttribute("listCommandExports") == null) {
                request.setAttribute("listCommandExports", request.getSession().getAttribute("listCommandExports"));
            }
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("delete", Constant.STATUS_DELETE);
%>


<sx:div id="displayTagFrame">
    <%--<fieldset style="width:95%; padding:10px 10px 10px 10px">
        <legend class="transparent">Danh sách phiếu nhập </legend>--%>

    <tags:imPanel title="MSG.list.import.note">
        <display:table name="listCommandExports" id="tblListCommandExports"
                       targets="displayTagFrame"
                       pagesize="10" class="dataTable"
                       cellpadding="1" cellspacing="1"
                       requestURI="PayDepositAction!pageNavigatorRecover.do">
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.010'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
                ${fn:escapeXml(tblListCommandExports_rowNum)}
            </display:column>
            <!--display:column title="Mã giấy nộp tiền" property="bankReceiptId" headerClass="tct" sortable="true"/-->
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.045'))}" property="actionCode" headerClass="tct" sortable="false"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.046'))}" property="createDatetime" headerClass="tct" sortable="false" format="{0,date,dd/MM/yyyy}" style="text-align:center" />
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.047'))}" property="userAction" headerClass="tct" sortable="false"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.009'))}"  headerClass="tct" sortable="false">
                <span><s:property escapeJavaScript="true"  value="#attr.tblListCommandExports.nameShopImport" /></span>
                <input type="hidden" id="tempNameShopImportId_<s:property escapeJavaScript="true"  value="%{#attr.tblListCommandExports_rowNum}"/>" value="<s:property escapeJavaScript="true"  value="#attr.tblListCommandExports.shopIdImport" />" />
            </display:column>

            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.025'))}" property="addressShopImport" headerClass="tct" sortable="false" style="text-align:left"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('L.100019'))}" headerClass="tct" sortable="false" style="text-align:center">
                <s:if test="(#attr.tblListCommandExports.depositStatus == 3)" >
                    ${fn:escapeXml(imDef:imGetText('MSG.DET.048'))}
                </s:if>
                <s:elseif test="(#attr.tblListCommandExports.depositStatus == 4)">
                    ${fn:escapeXml(imDef:imGetText('MSG.DET.049'))}
                </s:elseif>
            </display:column>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.029'))}" sortable="false" headerClass="tct" style="text-align:center">
                <s:url action="viewInformationOrderAction!viewInformationOrderDetailActionRecover.do" id="URL1">
                    <s:param name="stockTransId" value="#attr.tblListCommandExports.stockTransId"/>
                    <s:param name="depositStatus" value="#attr.tblListCommandExports.depositStatus"/>
                </s:url>
                <sx:a targets="listStockDetails" href="%{#URL1}" cssClass="cursorHand"
                      executeScripts="true" parseContent="true"
                      onclick="setDataTicket(this,'%{#attr.tblListCommandExports_rowNum}','%{#attr.tblListCommandExports.stockTransId}','%{#attr.tblListCommandExports.depositStatus}')" >
                    <s:if test="(#attr.tblListCommandExports.depositStatus == 3)" >
                        ${fn:escapeXml(imDef:imGetText('MSG.DET.029'))}
                    </s:if>
                    <s:elseif test="(#attr.tblListCommandExports.depositStatus == 4)">
                        ${fn:escapeXml(imDef:imGetText('MSG.DET.030'))}
                    </s:elseif>
                </sx:a>
                <input type="hidden" name="sTransId" id="stockTransIdHTML" />
            </display:column>
            <display:footer> <tr> <td colspan="8" style="color:green"> ${fn:escapeXml(imDef:imGetText('MSG.DET.031'))}<s:property escapeJavaScript="true"  value="%{#request.listCommandExports.size()}" /></td> <tr> </display:footer>
            </display:table>

            <%--</fieldset>--%>
        </tags:imPanel>
    </sx:div>
<script type="text/javascript">

</script>

