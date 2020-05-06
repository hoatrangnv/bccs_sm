<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listBankReceipt
    Created on : May 14, 2009, 4:09:11 PM
    Author     : User one
--%>

<%--
    note: danh sach giay nop xien
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
        <legend class="transparent">Danh mục lệnh xuất </legend>--%>
    <tags:imPanel title="MSG.list.exp.cmd">
        <display:table name="listCommandExports" id="tblListCommandExports"
                       targets="displayTagFrame"
                       pagesize="10" class="dataTable"
                       cellpadding="1" cellspacing="1"
                       requestURI="PayDepositAction!pageNavigator.do">
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.010'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
                ${fn:escapeXml(tblListCommandExports_rowNum)}
            </display:column>
            <!--display:column title="Mã giấy nộp tiền" property="bankReceiptId" headerClass="tct" sortable="true"/-->
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.021'))}" property="actionCode" headerClass="tct" sortable="false"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.022'))}" property="createDatetime" headerClass="tct" sortable="false" format="{0,date,dd/MM/yyyy}" style="text-align:center" />
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.023'))}" property="userAction" headerClass="tct" sortable="false"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.024'))}"  headerClass="tct" sortable="false">
                <span><s:property escapeJavaScript="true"  value="#attr.tblListCommandExports.nameShopImport" /></span>
                <input type="hidden" id="tempNameShopImportId_<s:property escapeJavaScript="true"  value="%{#attr.tblListCommandExports_rowNum}"/>" value="<s:property escapeJavaScript="true"  value="#attr.tblListCommandExports.shopIdImport" />" />
            </display:column>>

            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.025'))}" property="addressShopImport" headerClass="tct" sortable="false" style="text-align:left"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('L.100019'))}" headerClass="tct" sortable="false" style="text-align:center">
                <s:if test="(#attr.tblListCommandExports.depositStatus == 0)" >
                    ${fn:escapeXml(imDef:imGetText('MSG.DET.027'))}
                </s:if>
                <s:elseif test="(#attr.tblListCommandExports.depositStatus == 1)">
                    ${fn:escapeXml(imDef:imGetText('MSG.DET.028'))}
                </s:elseif>
            </display:column>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.029'))}" sortable="false" headerClass="tct" style="text-align:center">
                <s:url action="viewInformationOrderAction!viewInformationOrderDetailAction.do" id="URL1">
                    <s:param name="stockTransId" value="#attr.tblListCommandExports.stockTransId"/>
                    <s:param name="depositStatus" value="#attr.tblListCommandExports.depositStatus"/>
                </s:url>
                <sx:a targets="listStockDetails" href="%{#URL1}" cssClass="cursorHand"
                      executeScripts="true" parseContent="true"
                      onclick="setDataTicket(this,'%{#attr.tblListCommandExports_rowNum}','%{#attr.tblListCommandExports.stockTransId}','%{#attr.tblListCommandExports.depositStatus}')" >
                    <s:if test="(#attr.tblListCommandExports.depositStatus == 0)" >
                         ${fn:escapeXml(imDef:imGetText('MSG.DET.029'))}
                    </s:if>
                    <s:elseif test="(#attr.tblListCommandExports.depositStatus == 1)">
                         ${fn:escapeXml(imDef:imGetText('MSG.DET.030'))}
                    </s:elseif>
                </sx:a>
                <input type="hidden" name="sTransId" id="stockTransIdHTML" />
            </display:column>
            <display:footer> <tr> <td colspan="8" style="color:green">${fn:escapeXml(imDef:imGetText('MSG.DET.031'))}<s:property escapeJavaScript="true"  value="%{#request.listCommandExports.size()}" /></td> <tr> </display:footer>
            </display:table>

        </tags:imPanel>
        <%--</fieldset>--%>
    </sx:div>
<script type="text/javascript">

</script>

<%--table class="dataTable">
<tr>
<th><div align="center">STT</div></th>
<th><div align="center">Mã lệnh xuất</div></th>
<th><div align="center">Ngày lập lệnh xuất</div></th>
<th><div align="center">Người lập lệnh</div></th>
<th><div align="center">Tên đại lý</div></th>
<th><div align="center">Địa chỉ</div></th>
<th><div align="center">Trạng thái</div></th>
<th><div align="center" >Chọn</div></th>
</tr>
<s:if test="listCommandExports != null and listCommandExports.size > 0">
<s:iterator value="listCommandExports" status="staCommand">
<tr class="<s:if test="#staCommand.odd == true">odd</s:if><s:else>even</s:else>">
<td><s:property escapeJavaScript="true"  value="#staCommand.count" /></td>
<td><s:property escapeJavaScript="true"  value="actionCode" /></td>
<td><s:date name="createDatetime" format="dd/MM/yyyy" /></td>
<td><s:property escapeJavaScript="true"  value="userAction" /></td>
<td><div id="nameShopId_<s:property escapeJavaScript="true"  value="#staCommand.count"/>"><s:property escapeJavaScript="true"  value="nameShopImport" /></div>
<input type="hidden" id="tempNameShopImportId_<s:property escapeJavaScript="true"  value="#staCommand.count"/>" value="<s:property escapeJavaScript="true"  value="shopIdImport" />" />
</td>
<td><s:property escapeJavaScript="true"  value="addressShopImport" /></td>
<td>Chưa lập phiếu thu</td>
<td>
<s:url action="viewInformationOrderAction!viewInformationOrderDetailAction.do" var="viewInformationOrder">
<s:param name="stockTransId" value="stockTransId" />
</s:url>
<sx:a targets="informationOrder" href="%{viewInformationOrder}" 
onclick="setDataTicket(%{#staCommand.count},%{stockTransId});"
cssClass="cursorHand" executeScripts="true" parseContent="true">Chọn</sx:a>
<input type="hidden" name="sTransId" id="stockTransIdHTML" />
</td>
</tr>
</s:iterator>
</s:if>
<s:else>
Không có dữ liệu
</s:else>
</table--%>

