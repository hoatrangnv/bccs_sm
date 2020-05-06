<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : saleTransManagmentDetail.jsp
    Created on : 19/06/2009
    Author     : ThanhNC
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<div style="width:100%">

    <tags:displayResult id="resultViewSaleDetailClient" property="resultViewSaleDetail" type="key"/>

    <table style="width:100%">
        <tr>
            <td style="width:40%;vertical-align:top">
                <s:if test="saleManagmentForm.transDate!= null">
                    <fieldset class="imFieldset">
                        <legend><s:property escapeJavaScript="true"  value="getText('MSG.SAE.148')"/></legend>
                        <div style="width:100%;overflow:auto">
                            <table class="inputTbl4Col">
                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.123"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.custName" theme="simple" id="custName" cssClass="txtInputFull" readonly="true"/>
                                        <s:hidden name="saleManagmentForm.saleTransCode" id="saleTransCode" />
                                        <s:hidden name="saleManagmentForm.transStatus" id="transStatus" />
                                        <s:hidden name="saleManagmentForm.role" id="role" />
                                        <s:hidden name="saleManagmentForm.status" id="status" />
                                    </td>
                                    <td class="label"><tags:label key="MSG.SAE.149"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.telNumber" id="telNumber" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.006"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.company" id="company" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.SAE.150"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.tin" id="tin" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.125"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.contractNo" id="contractNo" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.SAE.151"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.isdn" id="isdn" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.099"/></td>
                                    <td colspan="3" class="text">
                                        <s:textfield name="saleManagmentForm.address" id="address" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label" ><tags:label key="MSG.SAE.020"/></td>
                                    <td colspan="3" class="text">
                                        <s:textarea name="saleManagmentForm.note" rows="2" id="note" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.044"/></td>
                                    <td>
                                        <!--                                        <_s:textfield name="saleManagmentForm.amountNotTaxMoney" cssStyle="text-align:right"  id="amountNotTax" theme="simple" cssClass="txtInputFull" readonly="true"/>-->
                                        <s:textfield name="saleManagmentForm.amountNotTaxMoney" cssStyle="text-align:right"  id="amountNotTaxMoney" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                        <!--                                        <_s:property value="saleManagmentForm.amountNotTax"/>-->
                                    </td>
                                    <td class="label"><tags:label key="MSG.SAE.045"/></td>
                                    <td>
                                        <!--                                        <_s:textfield name="saleManagmentForm.amountTaxMoney" id="amountTax" cssStyle="text-align:right"  theme="simple" cssClass="txtInputFull" readonly="true"/>-->
                                        <s:textfield name="saleManagmentForm.amountTaxMoney" id="amountTaxMoney" cssStyle="text-align:right"  theme="simple" cssClass="txtInputFull" readonly="true"/>
                                        <!--                                        <_s:property value="saleManagmentForm.amountTax"/>-->
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.152"/></td>
                                    <td>
                                        <!--                                        <_s:textfield name="saleManagmentForm.amountPromotionMoney" cssStyle="text-align:right"  id="amountPromotion" cssClass="txtInputFull" theme="simple" readonly="true"/>-->
                                        <s:textfield name="saleManagmentForm.amountPromotionMoney" cssStyle="text-align:right"  id="amountPromotionMoney" cssClass="txtInputFull" theme="simple" readonly="true"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.SAE.081"/></td>
                                    <td>                                        
                                        <!--                                        <_s:textfield name="saleManagmentForm.amountDiscountMoney" cssStyle="text-align:right"  id="amountDiscount" cssClass="txtInputFull" theme="simple" readonly="true"/>-->
                                        <s:textfield name="saleManagmentForm.amountDiscountMoney" cssStyle="text-align:right"  id="amountDiscountMoney" cssClass="txtInputFull" theme="simple" readonly="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.145"/></td>
                                    <td colspan="3">
                                        <!--                                        <_s:textfield name="saleManagmentForm.totalMoney" id="total" cssStyle="text-align:right"  cssClass="txtInputFull" theme="simple" readonly="true"/>-->
                                        <s:textfield name="saleManagmentForm.totalMoney" id="totalMoney" cssStyle="text-align:right"  cssClass="txtInputFull" theme="simple" readonly="true"/>
                                        <!--                                        <_s:property value="saleManagmentForm.total"/>-->
                                    </td>
                                </tr>

                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.153"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.payMethod" id="payMethod" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.SAE.154"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.referenceNo" id="referenceNo" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                   <tr >
                     <td class="label" >  <tags:label  key="MSG.ORDER.BANKNAME" required="true" /></td>
                        
                      <s:if test=" saleManagmentForm.status == 0 && saleManagmentForm.orderCode == null && saleManagmentForm.imageUrl == null &&  saleManagmentForm.role != null  && saleManagmentForm.role == 0" >
                            <td class="text">
                               <select name="saleManagmentForm.bankName" id="saleManagmentForm.bankName" >
                                    <option value="LETSHEGO" selected>LETSHEGO</option>
                                    <option value="BIM">BIM</option>
                                    <option value="MOZA">MOZA</option>
                                    <option value="BCI">BCI</option>
                                    <option value="BARCLAYS">BARCLAYS</option>
                                    <option value="STANDARDBANK">STANDARDBANK</option>
                                    <option value="UNICO">UNICO</option>
                                    <option value="UBA">UBA</option>
                                    <option value="ABC">ABC</option>
                                    <option value="ECO">ECO BANK</option>
                                    <option value="BTM">BTM</option>
                                    <option value="FNB">FNB</option>
                                </select>
                            </td>
                        </s:if>
                        <s:else>
                            
                            <td class="text">
                                 <s:textfield name="saleManagmentForm.bankName" id="saleManagmentForm.bankName" theme="simple" maxlength="8" readonly="true"  cssClass="txtInputFull"/>
                            </td>  
                        
                        </s:else>
                            
                        <td class="label">  <tags:label  key="MSG.ORDER.BANKAMOUNT" required="true" /></td>
                        
                        <s:if test=" saleManagmentForm.status == 0 && saleManagmentForm.orderCode == null && saleManagmentForm.imageUrl == null &&  saleManagmentForm.role != null  && saleManagmentForm.role == 0" >
                            <td class="text">
                                <s:textfield name="saleManagmentForm.amount" id="saleManagmentForm.amount" theme="simple" maxlength="8"   cssClass="txtInputFull"/>
                            </td>
                        </s:if>
                        <s:else>
                            
                            <td class="text">
                                 <s:textfield name="saleManagmentForm.amount" id="saleManagmentForm.amount" theme="simple" maxlength="8" readonly="true"  cssClass="txtInputFull"/>
                            </td>  
                        
                        </s:else>
                    </tr>            
                    <tr >
                        <td class="label"><tags:label key="MSG.ORDER.CODE" required="true" /></td>
                        
                        <s:if test=" saleManagmentForm.status == 0 && saleManagmentForm.orderCode == null && saleManagmentForm.imageUrl == null &&  saleManagmentForm.role != null  && saleManagmentForm.role == 0" >
                            
                            <td class="text">
                                <s:textfield name="saleManagmentForm.orderCode" id="saleManagmentForm.orderCode" theme="simple" maxlength="50"   cssClass="txtInputFull"/>
                            </td>
                        </s:if>
                        <s:else>
                            <td class="text">
                                 <s:textfield name="saleManagmentForm.orderCode" id="saleManagmentForm.orderCode" theme="simple" maxlength="50" readonly="true"  cssClass="txtInputFull"/>
                            </td>  
                        </s:else>
                        
                        
                        <td ></td>
                        <td ></td>
                    </tr>


                    <!--LamNT Upload file of channel-->
                    <tr >
                        <!--Neu la kenh dac biet thi hien thi-->
                        <%--<s:if test="#session.KenhDacBiet == 1">--%>
                        <s:form action="saleManagmentForm!imageUrl" theme="simple" method="post" id="exportStockForm">
                            <tr>
                                <td class="label"><tags:label key="MSG.bankDucument.file_data" required="true"/></td>

                                <td id="tagsImFileUpload" class="text" colspan="7" style="width:80%" >
                                    <tags:imFileUpload name="saleManagmentForm.imageUrl"  
                                                       id="saleManagmentForm.imageUrl"  extension="rar"
                                                       serverFileName="saleManagmentForm.imageUrl"
                                                       cssStyle="width:500px;"/>
                                </td>

                            </tr> 
                        </s:form> 

                    </tr>
                    <%--end--%>  
                    <tr >
                        <td ></td>
                        <td >
                            <s:if test="#attr.saleManagmentForm.imageUrl != null">
                                <s:url id="fileguide" namespace="/" action="accounrTransDownload" >
                                    <s:param name="scanPath" value="#attr.saleManagmentForm.imageUrl"/>
                                </s:url>
                                <s:a  href="%{fileguide}">Download</s:a>  
                            </s:if>
                        </td>

                        <td ></td>
                        <td ></td>
                    </tr>
                    <tr>
                        <td colspan="1"  >
                           List Bank Documents
                        </td>
                        <td class="text" colspan="4"  >
                                 <s:textfield name="saleManagmentForm.listBankDocuments" id="saleManagmentForm.listBankDocuments" theme="simple" maxlength="200" readonly="true"  cssClass="txtInputFull"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3"  >
                           
                         <sx:submit parseContent="true" executeScripts="true"
                                   cssStyle="width:200px;"
                                   targets="createInvoiceNotSaleDetailArea" formId="form" 
                                   key="MSG.ORDER.ADDMORE" beforeNotifyTopics="saleOrderManger/saveAddMoreBank"
                                   errorNotifyTopics="errorAction" />
                           
                         </td>
                    </tr>
                    <tr>
                         <td >
                            <s:if test="#attr.saleManagmentForm.imageUrlForm02 != null">
                                <s:url id="fileguide" namespace="/" action="form02Download" >
                                    <s:param name="fileDownloadForm02" value="#attr.saleManagmentForm.imageUrlForm02"/>
                                </s:url>
                                <s:a  href="%{fileguide}">Download Form 02</s:a>  
                            </s:if>
                        </td>
                    </tr>
                    
                    <tr  id="tdBankDocument2" style="visibility: hidden">
                      <td class="label" >  <tags:label  key="MSG.ORDER.BANKNAME2" required="true" /></td>
                        
                      <s:if test=" saleManagmentForm.status == 0 && saleManagmentForm.orderCode == null && saleManagmentForm.imageUrl == null &&  saleManagmentForm.role != null  && saleManagmentForm.role == 0" >
                            <td class="text">
                               <select name="saleManagmentForm.bankName2" id="saleManagmentForm.bankName2" >
                                    <option value="LETSHEGO" selected>LETSHEGO</option>
                                    <option value="BIM">BIM</option>
                                    <option value="MOZA">MOZA</option>
                                    <option value="BCI">BCI</option>
                                    <option value="BARCLAYS">BARCLAYS</option>
                                    <option value="STANDARDBANK">STANDARDBANK</option>
                                    <option value="UNICO">UNICO</option>
                                    <option value="UBA">UBA</option>
                                    <option value="ABC">ABC</option>
                                    <option value="ECO">ECO BANK</option>
                                    <option value="BTM">BTM</option>
                                    <option value="FNB">FNB</option>
                                </select>
                            </td>
                        </s:if>
                        <s:else>
                            
                            <td class="text">
                                 <s:textfield name="saleManagmentForm.bankName2" id="saleManagmentForm.bankName2" theme="simple" maxlength="8" readonly="true"  cssClass="txtInputFull"/>
                            </td>  
                        
                        </s:else>
                            
                        <td class="label">  <tags:label  key="MSG.ORDER.BANKAMOUNT2" required="true" /></td>
                        
                        <s:if test=" saleManagmentForm.status == 0 && saleManagmentForm.orderCode == null && saleManagmentForm.imageUrl == null &&  saleManagmentForm.role != null  && saleManagmentForm.role == 0" >
                            <td class="text">
                                <s:textfield name="saleManagmentForm.amount2" id="saleManagmentForm.amount2" theme="simple" maxlength="8"   cssClass="txtInputFull"/>
                            </td>
                        </s:if>
                        <s:else>
                            
                            <td class="text">
                                 <s:textfield name="saleManagmentForm.amount2" id="saleManagmentForm.amount2" theme="simple" maxlength="8" readonly="true"  cssClass="txtInputFull"/>
                            </td>  
                        
                        </s:else>
                    </tr>            
                   <tr  id="tdOrderCode2" style="visibility: hidden">
                        <td class="label"><tags:label key="MSG.ORDER.CODE2" required="true" /></td>
                        
                        <s:if test=" saleManagmentForm.status == 0 && saleManagmentForm.orderCode == null && saleManagmentForm.imageUrl == null &&  saleManagmentForm.role != null  && saleManagmentForm.role == 0" >
                            
                            <td class="text">
                                <s:textfield name="saleManagmentForm.orderCode2" id="saleManagmentForm.orderCode2" theme="simple" maxlength="50"   cssClass="txtInputFull"/>
                            </td>
                        </s:if>
                        <s:else>
                            <td class="text">
                                 <s:textfield name="saleManagmentForm.orderCode2" id="saleManagmentForm.orderCode2" theme="simple" maxlength="50" readonly="true"  cssClass="txtInputFull"/>
                            </td>  
                        </s:else>
                        
                        
                        <td ></td>
                        <td ></td>
                    </tr>
                   <tr  id="tdBankDocument3" style="visibility: hidden">
                      <td class="label" >  <tags:label  key="MSG.ORDER.BANKNAME3" required="true" /></td>
                        
                      <s:if test=" saleManagmentForm.status == 0 && saleManagmentForm.orderCode == null && saleManagmentForm.imageUrl == null &&  saleManagmentForm.role != null  && saleManagmentForm.role == 0" >
                            <td class="text">
                               <select name="saleManagmentForm.bankName3" id="saleManagmentForm.bankName" >
                                    <option value="LETSHEGO" selected>LETSHEGO</option>
                                    <option value="BIM">BIM</option>
                                    <option value="MOZA">MOZA</option>
                                    <option value="BCI">BCI</option>
                                    <option value="BARCLAYS">BARCLAYS</option>
                                    <option value="STANDARDBANK">STANDARDBANK</option>
                                    <option value="UNICO">UNICO</option>
                                    <option value="UBA">UBA</option>
                                    <option value="ABC">ABC</option>
                                    <option value="ECO">ECO BANK</option>
                                    <option value="BTM">BTM</option>
                                    <option value="FNB">FNB</option>
                                </select>
                            </td>
                        </s:if>
                        <s:else>
                            
                            <td class="text">
                                 <s:textfield name="saleManagmentForm.bankName3" id="saleManagmentForm.bankName3" theme="simple" maxlength="8" readonly="true"  cssClass="txtInputFull"/>
                            </td>  
                        
                        </s:else>
                            
                        <td class="label">  <tags:label  key="MSG.ORDER.BANKAMOUNT3" required="true" /></td>
                        
                        <s:if test=" saleManagmentForm.status == 0 && saleManagmentForm.orderCode == null && saleManagmentForm.imageUrl == null &&  saleManagmentForm.role != null  && saleManagmentForm.role == 0" >
                            <td class="text">
                                <s:textfield name="saleManagmentForm.amount3" id="saleManagmentForm.amount3" theme="simple" maxlength="8"   cssClass="txtInputFull"/>
                            </td>
                        </s:if>
                        <s:else>
                            
                            <td class="text">
                                 <s:textfield name="saleManagmentForm.amount3" id="saleManagmentForm.amount3" theme="simple" maxlength="8" readonly="true"  cssClass="txtInputFull"/>
                            </td>  
                        
                        </s:else>
                    </tr>            
                    <tr  id="tdOrderCode3" style="visibility: hidden">
                        <td class="label"><tags:label key="MSG.ORDER.CODE3" required="true" /></td>
                        
                        <s:if test=" saleManagmentForm.status == 0 && saleManagmentForm.orderCode == null && saleManagmentForm.imageUrl == null &&  saleManagmentForm.role != null  && saleManagmentForm.role == 0" >
                            
                            <td class="text">
                                <s:textfield name="saleManagmentForm.orderCode3" id="saleManagmentForm.orderCode3" theme="simple" maxlength="50"   cssClass="txtInputFull"/>
                            </td>
                        </s:if>
                        <s:else>
                            <td class="text">
                                 <s:textfield name="saleManagmentForm.orderCode3" id="saleManagmentForm.orderCode3" theme="simple" maxlength="50" readonly="true"  cssClass="txtInputFull"/>
                            </td>  
                        </s:else>
                        
                        
                        <td ></td>
                        <td ></td>
                    </tr>
                    
                  </table>

                                    
                        </div>
                    </fieldset>
                </s:if>

            </td>
            <td style="width:60%;vertical-align:top">
                <s:if test="#request.lstSaleTransDetail != null && #request.lstSaleTransDetail.size() != 0">
                    <fieldset class="imFieldset">
                        <legend><s:property escapeJavaScript="true"  value="getText('MSG.SAE.155')"/></legend>
                        <div style="width:100%;height: 245px; overflow:auto">
                            <sx:div id="listGoods" cssStyle="width:100%; height:380px; vertical-align:top">
                            <%int idx = 1;%>
                            <display:table name="lstSaleTransDetail" id="good"  class="dataTable" >
                                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" headerClass="tct">
                                    <div align="center">
                                        <s:if test="#attr.good.levels!=2">
                                            <%=StringEscapeUtils.escapeHtml(idx)%>
                                            <%idx++;%>
                                        </s:if>
                                    </div>
                                </display:column>
                                <display:column escapeXml="true" property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.074'))}" sortable="false" headerClass="sortable"/>
                                <display:column escapeXml="true" property="stockModelName" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.036'))}" sortable="false" headerClass="sortable"/>

                                <display:column property="price" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.039'))}"  format="{0,number,#,###.00}"  style="text-align:right" sortable="false" headerClass="sortable"/>

                                <display:column property="quantity" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.038'))}"  format="{0,number,#,###}" style="text-align:right"  sortable="false" headerClass="sortable"/>

                                <display:column property="amount" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.145'))}" format="{0,number,#,###.00}" style="text-align:right" sortable="false" headerClass="sortable"/>
                              <s:if test="#attr.good.saleTransId !=null ">
                                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.041'))}" sortable="false" headerClass="tct">
                                    <!--Chi nhung mat hang quan ly theo serial moi cho phep nhap chi tiet serial-->
                                    <s:if test="#attr.good.lstSerial != null && #attr.good.lstSerial.size()>0">
                                        <div align="center">
                                            <s:url action="InputSerialAction!prepareInputSerial" id="URLView" encode="true" escapeAmp="false">
                                                <s:param name="totalReq" value="#attr.good.quantity"/>
                                                <s:param name="stockTypeId" value="#attr.good.stockTypeId"/>
                                                <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                                <s:param name="ownerType" value="#attr.good.fromOwnerType"/>
                                                <s:param name="ownerId" value="#session.userToken.getUserID()"/>
                                                <s:param name="stateId" value="#attr.good.stateId"/>
                                                <s:param name="isView" value="true"/>
                                                <s:param name="purposeInputSerial" value="3"/>
                                                <s:param name="impChannelTypeId" value="#attr.good.channelTypeId"/>
                                            </s:url>
                                            <a href="javascript: void(0);" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URLView"/>',800,700)">
                                                ${fn:escapeXml(imDef:imGetText('MSG.SAE.041'))}
                                            </a>
                                        </div>
                                    </s:if>
                                    <s:else>
                                       <s:if test="saleManagmentForm.role != null && saleManagmentForm.role == 0 && saleManagmentForm.status == 1" >
                                        <div align="center">
                                            <s:url action="InputSerialAction!prepareInputSerial.do" id="URL" encode="true" escapeAmp="false">
                                                <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                                <s:param name="ownerType" value="2"/>
                                                <s:param name="totalReq" value="#attr.good.quantity"/>
                                                <s:param name="stateId" value="1"/>
                                                <s:param name="ownerId" value="#session.userToken.getUserID()"/>
                                                <s:param name="purposeInputSerial" value="3"/>
                                                <s:param name="impChannelTypeId" value="#attr.good.channelTypeId"/>
                                                <s:param name="saleTransDetailId" value="#attr.good.saleTransDetailId"/>
                                            </s:url>
                                            <a href="javascript:void(0)" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URL"/>',800,700)">
                                                ${fn:escapeXml(imDef:imGetText('MSG.SAE.043'))}
                                            </a>
                                           </div>
                                        </s:if>
                                    </s:else>
                                </display:column>
                              </s:if>
                              <s:else>
                                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.156'))}" sortable="false" headerClass="tct" style="width:70px">
                                    <%--<s:if test="#attr.trans.checkSerial==1">--%>
                               
                                        <div align="center">
                                            <s:url action="orderManagementAction!viewTransDetailSerial" id="URL" encode="true" escapeAmp="false">
                                                <s:param name="saleTransDetailId" value="#attr.good.saleTransDetailId"/>
                                                <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                            </s:url>
                                            <a href="javascript:void(0)" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URL"/>', 800, 700)">
                                                ${fn:escapeXml(imDef:imGetText('MSG.SAE.156'))}
                                            </a>
                                        </div>
                                        <%--</s:if>--%>
                                   
                                </display:column>    
                               </s:else>
                                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.156'))}" sortable="false" headerClass="tct" style="width:70px">
                                    <%--<s:if test="#attr.trans.checkSerial==1">--%>
                               
                                        <div align="center">
                                            <s:url action="orderManagementAction!viewTransDetailSerial" id="URL" encode="true" escapeAmp="false">
                                                <s:param name="saleTransDetailId" value="#attr.good.saleTransDetailId"/>
                                                <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                            </s:url>
                                            <a href="javascript:void(0)" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URL"/>', 800, 700)">
                                                ${fn:escapeXml(imDef:imGetText('MSG.SAE.156'))}
                                            </a>
                                        </div>
                                        <%--</s:if>--%>
                                   
                                </display:column>    
                                <display:footer> <tr> <td colspan="13" style="color:green">${fn:escapeXml(imDef:imGetText('MSG.SAE.147'))}:<s:property escapeJavaScript="true"  value="%{#request.lstSaleTransDetail.size()}" /></td> <tr> </display:footer>
                                </display:table>
                             </sx:div>
                        </div>
                    </fieldset>

                </s:if>

            </td>
        </tr>

        <s:hidden name="saleManagmentForm.invoiceId" />
        <s:if test="saleManagmentForm.transDate!= null">
            <tr>
                <td colspan="2" align="center">
                    <!--Khong cho phep huy giao dich da lap hoa don-->
                    <s:hidden id="saleManagmentForm.saleTransType" name="saleManagmentForm.saleTransType"/>
                    <s:hidden id="saleManagmentForm.cancelTrans" name="saleManagmentForm.cancelTrans"/>
                    
                    <s:if test="saleManagmentForm.status == 0 && saleManagmentForm.role != null && saleManagmentForm.role == 1" >
                        <tags:submit showLoadingText="true"
                                     formId="saleManagmentForm"
                                     value="MSG.SAE.304"
                                     targets="detailArea"
                                     confirm="true" 
                                     confirmText="MSG.SAE.307"
                                     preAction="orderManagementAction!approveTrans.do"/>

                     </s:if>
                    

                    <s:if test=" saleManagmentForm.status == 1 &&  saleManagmentForm.role != null  && saleManagmentForm.role == 0" >

                        <tags:submit showLoadingText="true"
                                     formId="saleManagmentForm"
                                     value="MSG.SAE.305"
                                     targets="detailArea"
                                     confirm="true" 
                                     confirmText="MSG.SAE.204"
                                     preAction="orderManagementAction!createTrans.do"/>
                        
                    </s:if>
                    
                    
                    <s:if test=" saleManagmentForm.status == 0 && saleManagmentForm.orderCode == null && saleManagmentForm.imageUrl == null &&  saleManagmentForm.role != null  && saleManagmentForm.role == 0" >

                        <tags:submit showLoadingText="true"
                                     formId="saleManagmentForm"
                                     value="MSG.SAE.316"
                                     targets="detailArea" 
                                     validateFunction="checkupdate()"
                                     preAction="orderManagementAction!updateOrder.do"/>
                        
                    </s:if>
                    <s:if test=" saleManagmentForm.status == 0 ||  saleManagmentForm.status == 1">
                        <tags:submit showLoadingText="true"
                                     formId="saleManagmentForm"
                                     value="MSG.SAE.157"
                                     targets="detailArea"
                                     confirm="true" 
                                     confirmText="MSG.SAE.205"
                                     preAction="orderManagementAction!cancelTrans.do"/>
                    </s:if>

                </td>
            </tr>
        </s:if>
    </table>
    <sx:div id="InvoicePrinterArea">
        <jsp:include page="printInvoiceResult.jsp"/>
    </sx:div>
</div>

<script language="javascript">
//    textFieldNF($('amountNotTax'));
//    textFieldNF($('amountTax'));
//    textFieldNF($('amountPromotion'));
//    textFieldNF($('amountDiscount'));
//    textFieldNF($('total'));
//    document.getElementById('test').className = 'hide';
     var orderCodeArray = new Array();
     var amountArray = new Array();
     var bankArray = new Array();
     dojo.event.topic.subscribe("saleOrderManger/saveAddMoreBank", function(event, widget){
          document.getElementById('tdBankDocument2').style.visibility = 'visible';
          document.getElementById('tdBankDocument3').style.visibility = 'visible';
          document.getElementById('tdOrderCode2').style.visibility = 'visible';
          document.getElementById('tdOrderCode3').style.visibility = 'visible';
     
     
     
     //bo
//          if (document.getElementById('saleManagmentForm.orderCode').value.trim() != "" ){
//               if(orderCodeArray.indexOf(document.getElementById('saleManagmentForm.orderCode').value.trim()) !== -1){
//                  alert("You have this bank document in list ") ;
//                  return;
//               }else{
//                  orderCodeArray.push(document.getElementById('saleManagmentForm.orderCode').value.trim());   
//               };
//               document.getElementById('saleManagmentForm.orderCode').value='' ; 
//            }
//            
//            if (document.getElementById('saleManagmentForm.amount').value.trim() != "" ){
//                amountArray.push(document.getElementById('saleManagmentForm.amount').value.trim()); 
//                document.getElementById('saleManagmentForm.amount').value='' ; 
//            }
//            
//            if (document.getElementById('saleManagmentForm.bankName').value.trim() != "" ){
//                bankArray.push(document.getElementById('saleManagmentForm.bankName').value.trim().substring(0,3)); 
//            }
//            
//            for (var i =0 ;i< orderCodeArray.length;i++){
//               var string  = bankArray[i] +"-"+ orderCodeArray[i]+"-"+amountArray[i] + ",";  
//            }
//            document.getElementById('saleManagmentForm.listBankDocuments').value += string;
//        widget.href = "searchSaleTransAction!createSaleTransInvoice.do";
     });  
        
      checkupdate = function() {
            if ($('saleManagmentForm.orderCode').value.trim() == "" && $('saleManagmentForm.listBankDocuments').value.trim() == ""){
                $('saleManagmentForm.orderCode').focus();
                $('saleManagmentForm.orderCode').select();
                $( 'resultViewSaleDetailClient' ).innerHTML = '<s:text name="saleRetail.warn.orderCode"/>';
                return false;
            }
            if ($('saleManagmentForm.amount').value.trim() == "" && $('saleManagmentForm.listBankDocuments').value.trim() == ""){
                $('saleManagmentForm.amount').focus();
                $('saleManagmentForm.amount').select();
                $( 'resultViewSaleDetailClient' ).innerHTML = 'saleRetail.warn.amount :Err. Pls input Amount';
                return false;
            }
            if ($('saleManagmentForm.bankName').value.trim() == "" && $('saleManagmentForm.listBankDocuments').value.trim() == ""){
                $('saleManagmentForm.bankName').focus();
                $('saleManagmentForm.bankName').select();
                $( 'resultViewSaleDetailClient' ).innerHTML = 'saleRetail.warn.bankName :Err. Pls input BankName';
                return false;
            }
            if ($('saleManagmentForm.imageUrl').value == null || $('saleManagmentForm.imageUrl').value == ""){
                $('saleManagmentForm.imageUrl').focus();
                $('saleManagmentForm.imageUrl').select();
                $( 'resultViewSaleDetailClient' ).innerHTML = 'saleRetail.warn.fileUrl :Err. Pls input file rar ';
                return false;
            }
            
            return true;
        }
        viewStockDetail=function(path){
            openPopup(path, 1000,500);
        }


</script>


