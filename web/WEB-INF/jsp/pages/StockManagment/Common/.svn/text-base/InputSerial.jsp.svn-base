<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : InputSerial.jsp
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
    String pageId = request.getParameter("pageId");
        if (request.getSession().getAttribute("lstSerial" + pageId) != null) {
        request.setAttribute("lstSerial", request.getSession().getAttribute("lstSerial" + pageId));
    }
    if (request.getParameter("isView") != null && !request.getParameter("isView").equals("")) {
        request.setAttribute("isView", request.getParameter("isView"));
    }
%>


<s:form action="InputSerialAction.do"  theme="simple" id="serialGoods" enctype="multipart/form-data" method="post">
    <s:token/>

    <input type="hidden"  name="pageId" id="pageId"/>
    <s:hidden name="serialGoods.total" id="total"/>
    <s:hidden name="serialGoods.stockModelId" id="stockModelId"/>
    <s:hidden name="serialGoods.stockTypeId" id="stockTypeId"/>
    <s:hidden name="serialGoods.ownerId" id="ownerId"/>
    <s:hidden name="serialGoods.ownerCode" id="ownerCode"/>
    <s:hidden name="serialGoods.stateId" id="stateId"/>
    <s:hidden name="serialGoods.ownerType" id="ownerType"/>
    <s:hidden name="serialGoods.editable" id="editable"/>
    <s:hidden name="serialGoods.stockTransId" id="stockTransId"/>
    <s:hidden name="serialGoods.impChannelTypeId" id="impChannelTypeId"/>
    <s:hidden name="serialGoods.saleTransDetailId" id="saleTransDetailId"/>
    <s:hidden name="serialGoods.impOwnerId" id="impOwnerId"/>
    <s:hidden name="serialGoods.impOwnerType" id="impOwnerType"/>

    <s:hidden name="serialGoods.parentId" id="parentId"/>
    <s:hidden name="serialGoods.reasonId" id="reasonId"/>
    <s:hidden name="serialGoods.otp" id="otp"/>
    <s:hidden name="serialGoods.purposeInputSerial" id="purposeInputSerial"/>
    <s:hidden name="serialGoods.isRevokeGoodsAction" id="isRevokeGoodsAction"/>

    <s:if test="#request.isView ==null || #request.isView !='true'">

        <tags:imPanel title="MSG.serialDetail">
            <table class="inputTbl4Col" style="width:100%" id="tableImportSerial">
                <tr>
                    <td colspan="4" align="center">
                        <a href="${contextPath}/share/pattern/mau_file_nhap_serial.xls">${fn:escapeXml(imDef:imGetText('MSG.download.serial.template'))}</a>
                    </td>
                </tr>
                <tr>
                    <td class="label"> <tags:label key="MSG.storeName"/></td>
                    <td class="text">
                        <s:textfield name="serialGoods.ownerName" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <td class="label"> <tags:label key="MSG.generates.goods"/></td>
                    <td class="text">
                        <s:textfield name="serialGoods.stockModelName" cssClass="txtInputFull" readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td  class="label"> <tags:label key="MSG.generates.status"/></td>
                    <td  class="text">
                        <s:if test="serialGoods.stateId==1">
                            <!--                            <input type="text" value="Hàng mới" class="txtInputFull" readonly />-->
                            <input type="text" value="<s:text name="MSG.GOD.242"/>" class="txtInputFull" readonly />
                        </s:if>
                        <s:if test="serialGoods.stateId==2">
                            <!--                            <input type="text" value="Hàng cũ" class="txtInputFull" readonly />-->
                            <input type="text" value="<s:text name="MSG.GOD.285"/>" class="txtInputFull" readonly />
                            <!--                            <input type="text" value="'<1s:property value="MSG.GOD.285"/>'" class="txtInputFull" readonly />-->
                        </s:if>
                        <s:if test="serialGoods.stateId==3">
                            <!--                            <input type="text" value="Hàng hỏng" class="txtInputFull" readonly />-->
                            <input type="text" value="<s:text name="MSG.GOD.243"/>" class="txtInputFull" readonly />
                            <!--                            <input type="text" value="'<1s:property value="MSG.GOD.243"/>'" class="txtInputFull" readonly />-->
                        </s:if>
                    </td>
                    <td  class="label"> <tags:label key="MSG.quanlity"/></td>
                    <td class="text">
                        <s:textfield name="serialGoods.totalReq" id="totalReq" cssClass="txtInputFull" readonly="true"/>

                    </td>
                </tr>


                <tr id="trSerialStockType">
                    <td  class="label"> <tags:label key="MSG.import.type"/></td>
                    <td  class="text" colspan="3">
                        <s:if test="serialGoods.impType==1">
                            <input type="radio" id="serialGoods.impType1" name="serialGoods.impType"  value="1" onclick="radioClick()" checked>
                        </s:if> <s:else>
                            <input type="radio" id="serialGoods.impType1" name="serialGoods.impType"  value="1" onclick="radioClick()">
                        </s:else>
                        &nbsp; ${fn:escapeXml(imDef:imGetText('MSG.imByFile'))}
                        &nbsp;&nbsp;&nbsp;
                        <s:if test="serialGoods.impType==2">
                            <input type="radio" id="serialGoods.impType2" name="serialGoods.impType" value="2" onclick="radioClick()" checked>
                        </s:if><s:else>
                            <input type="radio" id="serialGoods.impType2" name="serialGoods.impType" value="2" onclick="radioClick()" >
                        </s:else>
                        &nbsp; ${fn:escapeXml(imDef:imGetText('MSG.imBySerial'))}
                    </td>
                </tr>
                <s:if test="serialGoods.impType==1">
                    <tr id="trImpByFile">
                        <td  class="label" > <tags:label key="MSG.urlFile"/></td>
                        <td  class="text" colspan="3">
                            <s:file id="serialGoods.impFile" name="serialGoods.impFile" size="80"/>
                        </td>
                    </tr>
                    <tr>
                        <td  class="text" colspan="4" align="center">
                            <!--input type ="submit" value="Chọn"/-->
                            <input type ="button" style="width:100px" onclick="inputSerialByFile()" value="${fn:escapeXml(imDef:imGetText('MSG.SAE.047'))}"/>
                        </td>
                    </tr>
                </s:if>
                <s:else>
                    <tr id="trImpByRange">
                        <td  class="label"> <tags:label key="MSG.stock.from.serial"/></td>
                        <td  class="text">
                            <s:textfield name="serialGoods.fromSerial" id="serialGoods.fromSerial" maxlength="20" cssClass="txtInputFull"/>
                            <script>$('serialGoods.fromSerial').focus();</script>
                        </td>
                        <td  class="label"> <tags:label key="MSG.stock.to.serial"/></td>
                        <td class="text">
                            <s:textfield name="serialGoods.toSerial" id="serialGoods.toSerial" maxlength="20" cssClass="txtInputFull"/>
                        </td>
                    </tr>
                    <tr>
                        <td  class="text" colspan="4" align="center">
                            <s:url action="ViewStockDetailAction!viewDetailSerial" id="viewSerialURL" encode="true" escapeAmp="false">
                                <s:param name="ownerType" value="serialGoods.ownerType"/>
                                <s:param name="ownerId" value="serialGoods.ownerId"/>
                                <s:param name="stockModelId" value="serialGoods.stockModelId"/>
                                <s:param name="stateId" value="serialGoods.stateId"/>
                                <s:param name="impChannelTypeId" value="serialGoods.impChannelTypeId"/>
                            </s:url>                            
                            <input type ="button" onclick="searchSerial()" style="width:100px" value="${fn:escapeXml(imDef:imGetText('MSG.SAE.047'))}"/>
                            <input type="button" onclick="viewDetailSerial('<s:property escapeJavaScript="true"  value="#attr.viewSerialURL"/>')" value="${fn:escapeXml(imDef:imGetText('MSG.view.serial.store'))}"/>
                        </td>
                    </tr>
                </s:else>

            </table>
        </tags:imPanel>

    </s:if>
    <s:else>
        <s:hidden name="serialGoods.ownerName"/>
        <s:hidden name="serialGoods.stockModelName" />
        <s:hidden name="serialGoods.totalReq" id="totalReq"/>
    </s:else>
    <div id="serialListArea" style="width:100%">
        <tags:displayResult  property="returnMsg" id="returnMsgClient" />


        <!-- phan hien thi danh sach cac dai serial thoa man dieu kien tim kiem-->
        <div style="width:100%; height:350px; overflow:auto;">
            <s:if test="#request.lstSerial != null && #request.lstSerial.size() > 0">
                <display:table id="tblLstSerial" name="lstSerial" class="dataTable" cellpadding="1" cellspacing="1">
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblLstSerial_rowNum)}</display:column>
                    <display:column escapeXml="true"  property="fromSerial" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.049'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true"  property="toSerial" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.050'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true"  property="quantity" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.051'))}" sortable="false" headerClass="tct"/>
                    <s:if test="#request.isView ==null || #request.isView !='true'">
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.052'))}" sortable="false" style="width:80px; text-align:center" headerClass="tct">
                            <s:a href="" onclick="deleteSerial('%{#attr.tblLstSerial.stockTransSerialId}');" cssClass="cursorHand" executeScripts="true" parseContent="true">
                                <img src="${contextPath}/share/img/delete_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}"/>
                            </s:a>
                        </display:column>
                    </s:if>
                </display:table>
                <s:if test="#request.isView ==null || #request.isView !='true'">
                    <div align="right">
                        ${fn:escapeXml(imDef:imGetText('MSG.SAE.053'))} <s:property escapeJavaScript="true"  value="serialGoods.total"/> / <s:property escapeJavaScript="true"  value="serialGoods.totalReq"/>
                    </div>
                </s:if>
            </s:if>
            <s:else>
                <table class="dataTable">
                    <tr>
                        <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}</th>
                        <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.SAE.049'))}</th>
                        <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.SAE.050'))}</th>
                        <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.SAE.051'))}</th>
                        <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.SAE.052'))}</th>
                    </tr>
                    <tr class="odd">
                        <td>&nbsp;</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
            </s:else>
        </div>


        <div style="width:100%" id="ok" align="center">
            <s:if test="serialGoods.total == serialGoods.totalReq">
                <input type="button" onclick="inputSerial();"  value="${fn:escapeXml(imDef:imGetText('MSG.SAE.054'))}"/>
            </s:if>
            <!--Truong hop la view cho phep in chi tiet serial-->
            <s:if test="#request.isView !=null || #request.isView =='true' || serialGoods.total == serialGoods.totalReq ">
                <input type="button" onclick="exportSerial(${fn:escapeXml(isView)});"  value="${fn:escapeXml(imDef:imGetText('MSG.SAE.055'))}"/>
            </s:if>
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
    </div>
</s:form>
<script>
    viewDetailSerial=function(path){
        openDialog(path, 900, 700,false);
    }
    
    deleteSerial =function(id){
        var parameter=getFormAsString("serialGoods");
    <%--document.getElementById("serialGoods").action="InputSerialAction!deleteSerialRange.do?selectedRangeId="+id+parameter;
    document.getElementById("serialGoods").submit();--%>
            setAction("serialGoods","InputSerialAction!deleteSerialRange.do?selectedRangeId="+id+parameter+"&"+ token.getTokenParamString());
        }
        inputSerialByFile = function(){
    <%--document.getElementById("serialGoods").action="InputSerialAction!searchListSerial.do";
    document.getElementById("serialGoods").submit();--%>
            setAction("serialGoods","InputSerialAction!searchListSerial.do");

        }
        searchSerial= function(){
            $('serialGoods.fromSerial').value =$('serialGoods.fromSerial').value.trim();
            $('serialGoods.toSerial').value =$('serialGoods.toSerial').value.trim();
            if(!isNum($('serialGoods.fromSerial').value) || $('serialGoods.fromSerial').value*1 <0){
                $('returnMsgClient').innerHTML= '<s:text name="ERR.STK.001"/>';
                //$('returnMsgClient').innerHTML="Trường từ số serial/IMEI phải là số nguyên dương";
                $('serialGoods.fromSerial').focus();
                return false;
            }
            if(!isNum($('serialGoods.toSerial').value) || $('serialGoods.toSerial').value*1 <0){
                $('returnMsgClient').innerHTML= '<s:text name="ERR.STK.002"/>';
                //$('returnMsgClient').innerHTML="Trường đến số serial/IMEI phải là số nguyên dương";
                $('serialGoods.toSerial').focus();
                return false;
            }
            if($('serialGoods.toSerial').value*1 <$('serialGoods.fromSerial').value*1){
                $('returnMsgClient').innerHTML= '<s:text name="ERR.STK.003"/>';
                //$('returnMsgClient').innerHTML="Trường từ số serial/IMEI phải nhỏ hơn trường đến số serial/IMEI";
                $('serialGoods.fromSerial').focus();
                return false;
            }
            setAction("serialGoods","InputSerialAction!searchListSerial.do");
    <%--document.getElementById("serialGoods").action="InputSerialAction!searchListSerial.do";
    document.getElementById("serialGoods").submit();--%>
        }
        inputSerial = function(){
    <%--document.getElementById("serialGoods").action="InputSerialAction!addSerial.do";
    document.getElementById("serialGoods").submit();--%>
            setAction("serialGoods","InputSerialAction!addSerial.do");
        }
        exportSerial= function(isView){
    <%--document.getElementById("serialGoods").action="InputSerialAction!exportSerial.do?isView="+isView;
    document.getElementById("serialGoods").submit();--%>
            setAction("serialGoods","InputSerialAction!exportSerial.do?isView="+isView);
        }
        radioClick = function(){
    <%--document.getElementById("serialGoods").action="InputSerialAction!changeMethodInputSerial.do";
    document.getElementById("serialGoods").submit();--%>
            setAction("serialGoods","InputSerialAction!changeMethodInputSerial.do");
        }

        radioClick2 = function(){
            var tableImportSerial = $('tableImportSerial');
            var trs = tableImportSerial.getElementsByTagName("tr");
            for( var i = 0; i < trs.length; i++ ) {
                if(trs[i].id  == "trImpByFile") {
                    trs[i].style.display = 'none';
                } else if(trs[i].id  == "trImpByRange") {
                    trs[i].style.display = '';
                }
            }
        }

</script>

