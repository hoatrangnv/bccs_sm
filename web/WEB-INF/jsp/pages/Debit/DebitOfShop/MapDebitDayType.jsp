<%-- 
    Document   : MapDebitDayType.jsp
    Created on : Dec 17, 2013, 3:08:22 PM
    Author     : thinhph2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<s:form id="inputForm" theme="simple" method="post">
    <fieldset class="imFieldset">
        <legend>${imDef:imGetText('MSG.find.info')}</legend>
        <s:hidden id="id" name="inputForm.id" />
        <table class="inputTbl6Col">
            <tr>
                <td class="label"><tags:label key="lbl.select_all_unit"/></td>
                <td class="text">
                    <s:if test="#request.preUpdate">
                        <s:if test="#request.checkAllShop == 1">
                            <s:checkbox name="inputForm.checkAll" disabled="true" value = "true" id="checkAll" onclick="checkAllShop()"/>
                            <s:hidden id="checkAllHidden" name="inputForm.checkAll" value="true"/>
                        </s:if>
                        <s:else>
                            <s:checkbox name="inputForm.checkAll" disabled="true" id="checkAll" onclick="checkAllShop()"/>
                            <s:hidden id="checkAllHidden" name="inputForm.checkAll" value="fasle"/>
                        </s:else>
                        
                    </s:if>
                    <s:else>
                        <s:if test="#request.checkAllShop == 1">
                            <s:checkbox name="inputForm.checkAll" value = "true" id="checkAll" onclick="checkAllShop()"/>
                        </s:if>
                        <s:else>
                            <s:checkbox name="inputForm.checkAll" id="checkAll" onclick="checkAllShop()"/>
                        </s:else>
                    </s:else>


                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MES.CHL.142" required="true"/></td>
                <td class="text">
                    <tags:imSearch codeVariable="inputForm.shopCode" 
                                       nameVariable="inputForm.shopName"
                                       codeLabel="MSG.SAE.109" nameLabel="MSG.SAE.110"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShopIsVTUnit"
                                       getNameMethod="getShopName"
                                       />
                    <s:if test="#request.preUpdate">
                        <s:hidden name="inputForm.shopCode" id="shopCodeHiden" />
                    </s:if>
                    

                </td>
                <td class="label"><tags:label key="lbl.promotion"  required="true"/></td>
                <td class="text">
                    <tags:imSelect name="inputForm.debitDayTypeId" 
                                   id="debitDayTypeId"
                                   cssClass="txtInputFull" 
                                   list="listDebitDayType"
                                   listKey="debitDayTypeId" listValue="debitDayType"
                                   headerKey="" headerValue="lbl.dot_km"/>
                </td>
                <td class="label"><tags:label key="MSG.generates.status" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="inputForm.status" id="status"
                                   cssClass="txtInputFull" disabled="false"
                                   list="1: MSG.GOD.002, 0: MSG.GOD.003"
                                   headerKey="" headerValue="MSG.SIK.262"/>
                </td>
            </tr>
        </table>
        <div align="center">
            <s:if test="#request.preUpdate">
                <tags:submit targets="bodyContent"
                             formId="inputForm"
                             cssStyle="width:100px"
                             value="lbl.sua"
                             showLoadingText="true"
                             preAction="mappingDebitDayWithShopAction!updateChannelGroup.do"
                             validateFunction="validate()"/>
                <tags:submit targets="bodyContent"
                             formId="inputForm"
                             cssStyle="width:100px"
                             value="lbl.bo_qua"
                             showLoadingText="true"
                             preAction="mappingDebitDayWithShopAction!cancel.do"
                             /> 
            </s:if>
            <s:else>
                <tags:submit targets="listChannelGroupDIV"
                             formId="inputForm"
                             cssStyle="width:100px"
                             value="TITLE.STOCK.CONFIGURATION.LIMITS.003"
                             showLoadingText="true"
                             preAction="mappingDebitDayWithShopAction!searchChannel.do"
                             />
                <tags:submit targets="bodyContent"
                             formId="inputForm" 
                             cssStyle="width:100px"
                             value="BTS.btn.AddNew"
                             showLoadingText="true"
                             preAction="mappingDebitDayWithShopAction!addChannelGroup.do"
                             validateFunction="validate()"/>
            </s:else>
        </div>
    </fieldset>
</s:form>

<script type="text/javascript">
        
    <s:if test="#request.checkAllShop == 1">
        jQuery("input[id='inputForm.shopCode']").val("");
        jQuery("input[id='inputForm.shopName']").val("");
        jQuery("input[id='inputForm.shopCode']").attr("disabled", "true");
        jQuery("input[id='inputForm.shopName']").attr("disabled", "true");
    </s:if>
    <s:else>
        jQuery("input[id='inputForm.shopCode']").attr("disabled", "");
        jQuery("input[id='inputForm.shopName']").attr("disabled", "");
    </s:else>
        
    <s:if test="#request.preUpdate">
        jQuery("input[id='inputForm\\.shopCode']").attr("disabled", "true");
        jQuery("input[id='inputForm\\.shopName']").attr("disabled", "true");
    </s:if>
    <s:else>
        jQuery("input[id='inputForm\\.shopCode']").attr("disabled", "");
        jQuery("input[id='inputForm\\.shopName']").attr("disabled", "");
    </s:else>    
</script>