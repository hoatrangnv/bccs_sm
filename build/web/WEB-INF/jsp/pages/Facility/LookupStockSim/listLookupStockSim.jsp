<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listLookupStockSim
    Created on : Dec 10, 2009, 3:45:46 PM
    Author     : AnDV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%              request.setAttribute("listStockSim", request.getAttribute("listStockSim"));
%>

<sx:div id="displayTagFrame" >
    <display:table  id="tblStockSim" name="listStockSim"
                    targets="displayTagFrame"
                    class="dataTable" cellpadding="1" cellspacing="1" >
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center" >
            ${fn:escapeXml(tblStockSim_rowNum)}
        </display:column>
        <display:column escapeXml="true" property="serial" title="${fn:escapeXml(imDef:imGetText('MSG.generates.imsi'))}" sortable="false" headerClass="tct" />
        <display:column escapeXml="true" property="imsi" title="${fn:escapeXml(imDef:imGetText('MSG.FAC.IMSI'))}" sortable="false" headerClass="tct" />
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.sim.type'))}"  headerClass="tct" style="width:100px; ">
            <s:if test="#attr.tblStockSim.simType == 4"><s:property escapeJavaScript="true"  value="getError('MSG.prepaid')"/></s:if>
            <s:if test="#attr.tblStockSim.simType == 5"><s:property escapeJavaScript="true"  value="getError('MSG.postpaid')"/> </s:if>
        </display:column>
        <display:column escapeXml="true" property="hlrId" title="${fn:escapeXml(imDef:imGetText('MSG.generates.switchboard'))}" sortable="false" headerClass="tct" />
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.FAC.HLRStatus'))}"  headerClass="tct" style="width:100px; ">

            <s:if test="#attr.tblStockSim.hlrStatus == 1"><s:property escapeJavaScript="true"  value="getError('MSG.not.registed')"/></s:if>
            <s:if test="#attr.tblStockSim.hlrStatus == 2"><s:property escapeJavaScript="true"  value="getError('MSG.registed')"/></s:if>
            <s:if test="#attr.tblStockSim.hlrStatus == 4"><s:property escapeJavaScript="true"  value="getError('MSG.removed')"/></s:if>

        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.AUCStatus'))}"  headerClass="tct" style="width:100px; ">

            <s:if test="#attr.tblStockSim.aucStatus == 0"><s:property escapeJavaScript="true"  value="getError('MSG.not.registed.auc')"/></s:if>
            <s:if test="#attr.tblStockSim.aucStatus == 1"><s:property escapeJavaScript="true"  value="getError('MSG.registed.auc')"/></s:if>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.choice'))}" sortable="false" headerClass="tct" style="text-align:center">
            <sx:a onclick="detail('%{#attr.tblStockSim.eki}','%{#attr.tblStockSim.adm1}','%{#attr.tblStockSim.a3a8}','%{#attr.tblStockSim.aucRegDate}','%{#attr.tblStockSim.aucRemoveDate}','%{#attr.tblStockSim.puk}'
                  ,'%{#attr.tblStockSim.puk2}','%{#attr.tblStockSim.pin}','%{#attr.tblStockSim.pin2}',
                  '%{#attr.tblStockSim.hlrRemoveDate}','%{#attr.tblStockSim.hlrRegDate}','%{#attr.tblStockSim.serial}')" >
                <tags:label key="MSG.generate.choice"/>


            </sx:a>
        </display:column>
    </display:table>
</sx:div>
<script type="text/javascript">
    detail = function(eki,adm1,a3a8,aucRegDate,aucRemoveDate,puk,puk2,pin,pin2,hlrRemoveDate,hlrRegDate,serial){
        $('eki').value=eki;
        $('adm1').value=adm1;
        $('a3a8').value=a3a8;
        $('aucRegDate').value=aucRegDate;
        $('aucRemoveDate').value=aucRemoveDate;
        $('puk').value=puk;
        $('puk2').value=puk2;
        $('pin').value=pin;
        $('pin2').value=pin2;
        $('hlrRemoveDate').value=hlrRemoveDate;
        $('hlrRegDate').value=hlrRegDate;
        $('serial').value=serial;

    }

</script>




