<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/vsa-defs.tld" prefix="vp" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!--<_%@ page import="java.util.ResourceBundle"%>-->
<!--<_%@ page import="java.util.Locale"%>-->
<%@ page import="com.viettel.im.database.DAO.AuthenticateDAO"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%@taglib prefix="imDef" uri="imDef" %>


<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<%
    int index = 0;
    List urls = new ArrayList();
%>

<%
    String isValidate = (String) session.getAttribute("isValidate");
    if (isValidate != null) {
%>
<%
    String prefix = request.getContextPath();


    String strRequest = null;
    String actionPage = null;
    String encryptedS = null;
    int iQuestionCharPos = 0;

%>


<style type="text/css">
    .Normal
    {
        border: dashed 1px #000000;
        background-color: #FFFFFF;
        cursor: auto;
        padding: 10px;
        width: 200px;
        top:100px;
        text-align: center;
    }

    .Progress
    {
        border: dashed 1px #000000;
        background-color: #EEEEEE;
        background-image: url(spinner.gif);
        background-position: center center;
        background-repeat: no-repeat;
        cursor: wait;
        padding: 10px;
        width: 200px;
        text-align: center;
    }
    *{margin:0;padding:0}
    #overlay{width:100%;height:100%;top:200;position:absolute;left:-9999em;background:#FFFFFF;opacity:.90;filter:alpha(opacity=70);z-index:2;}

    #content{margin:20px}

</style>

<div id="overlay" align="center" >
    <table align="center">
        <tr>
            <td height="100px" valign="bottom">
                <div id="Container" class="Normal" align="center">

                </div>
            </td>
        </tr>
    </table>

</div>



<script language="javascript">
    function calcLockingDivAppearance() {
        /*
        var div = $( 'overlay' );
        var bodyContent = document.getElementById('bodyContent');
        div.style.top = bodyContent.offsetTop + 'px';
        div.style.left = bodyContent.offsetLeft + 'px';
        div.style.width = bodyContent.offsetWidth + 'px';
        div.style.height = bodyContent.offsetHeight + 'px';
         */
    }

    var prevWindowOnresizeCallback = window.onresize;
    window.onresize = function() {
        if( prevWindowOnresizeCallback ) {
            prevWindowOnresizeCallback.call();
        }
        if( $('overlay' ).style.display !='none' ) {
            calcLockingDivAppearance();
        }
    }

    function disableStart(){
        /*
        var div = $( 'overlay' );
        var bodyContent = document.getElementById('bodyContent');
        div.style.top = bodyContent.offsetTop + 'px';
        div.style.left = bodyContent.offsetLeft + 'px';

        div.style.width = bodyContent.offsetWidth + 'px';
        div.style.height = bodyContent.offsetHeight + 'px';
        
        
        http://10.229.42.36:8807/passportv3/changepassword.htm
        
         */
    }
    initWidget();
    changePass=function(){
        
        var url = "http://10.229.42.36:8806/vsaadminv3/share/pageInfo.jsp?request_locale=en&userId="+ <s:property escapeJavaScript="true"  value="#session.vsaUserToken.userID"/>;
        openDialog(url,800,700,true);
    }



</script>
<sx:bind id="controlAction" indicator="overlay" listenTopics="gotoURL" separateScripts="true" executeScripts="true"/>

<sx:bind id="controlMenu" indicator="overlay" events="onclick"
         listenTopics="gotoMenu"
         afterNotifyTopics="updateBodyContent"
         errorNotifyTopics="errorAction"
         separateScripts="true"
         executeScripts="true"
         />
<s:if test="#session.vsaUserToken != null">
    <s:set name="menuTokenAll" value="#session.vsaUserToken.parentMenu"/>
</s:if>
<s:if test="#session.vsaUserToken != null">




    <!-- [BEGIN] Status -->

    <div id="module-status">

        <span>            

            <s:if test="#session.userToken != null">

                <%

                    java.util.ResourceBundle rb1 = null;

                    java.util.Locale locale = new java.util.Locale("en", "US");

                    rb1 = java.util.ResourceBundle.getBundle("cas", locale);

                    request.setAttribute("service_url", rb1.getString("service"));

                %>

                <!--                Thay Ä‘á»•i thÃ´ng tin ngÆ°á»�i dÃ¹ng-->

<!--                <a   title="<s_:property value="getText('MSG.HEADER.CHANGE.PASS')"/>" href="javascript:changePass();"><s_:property value="#session.userToken.staffName"/></a>|-->

                <a   title="<s:text name ="MSG.HEADER.CHANGE.PASS"/>" href="javascript:changePass();"><s:property escapeJavaScript="true"  value="#session.userToken.staffName"/></a>|

                <!--                ThoÃ¡t -->

<!--                <a  href="share/logout.jsp"><s_:property value="getText('MSG.HEADER.EXIT')"/>&nbsp;</a>-->

                <a  href="share/logout.jsp"><s:text name="MSG.HEADER.EXIT"/>&nbsp;</a>

            </s:if>

            <s:if test="#session.enableMenu==null || #session.enableMenu!=1">

                <a href="${fn:escapeXml(service_url)}">| <s:text name="MSG.HEADER.GO.HOME"/></a>

            </s:if>

            <a href="${fn:escapeXml(requestScope.contextPath)}/Authentication.do?request_locale=vi_VN" >
                <img src="${contextPath}/share/flags/vie.gif" title="Vietnammese" alt="Vietnammese"/>
            </a>
            <a href="${fn:escapeXml(requestScope.contextPath)}/Authentication.do?request_locale=en_US">
                <img src="${contextPath}/share/flags/eng.jpg" title="English" alt="English"/>
            </a>
            <a href="${fn:escapeXml(requestScope.contextPath)}/Authentication.do?request_locale=pt_PT">
                <img src="${contextPath}/share/flags/por.png" title="Português" alt="Português"/>
            </a>
        </span>

    </div>   



    <div id="module-menu"></div>
    <s:if test="#session.enableMenu!=null && #session.enableMenu==1">
        <script type="text/javascript">

            function clickHandler(item, e)
            {
                alert(item.text);
            }

            var tb = new Ext.Toolbar('module-menu');

            <s:iterator id="permission" value="#session.vsaUserToken.parentMenu">
                tb.add({
                    //                    text: '<s_:property value="getMenu(#permission.objectCode)"/>',
                    text: '<s:text name="%{#permission.objectCode}"/>',
                    //cls: 'x-btn-text-icon bmenu',
                    menu: p = new Ext.menu.Menu({

                        items: [
                <s:iterator id="childFunction" value="#permission.childObjects">
                    <s:set name="url" value="#childFunction.objectUrl" scope="request"/>

                    <%
                        String url = "";
                        if (request.getAttribute("url") != null) {
                            url = request.getAttribute("url").toString();
                        }
                    %>
                                    {
                                        //                                       text: '<s_:property value="getMenu(#childFunction.objectCode)"/>',
                                        text: '<s:text name="%{#childFunction.objectCode}"/>',
                    <s:if test="#childFunction.childObjects.size()>0">
                                        menu: p = new Ext.menu.Menu({
                                            items: [
                        <s:iterator id="child2Function" value="#childFunction.childObjects">
                            <s:if test="#child2Function.objectUrl != null">
                                <s:set name="url2" value="#child2Function.objectUrl" scope="request"/>
                                <%
                                    String url2 = request.getAttribute("url2").toString();
                                %>
                                                            {
                                                                //                                                                text: '<s_:property value="getMenu(#child2Function.objectCode)"/>',
                                                                text: '<s:text name="%{#child2Function.objectCode}"/>',
                                                                href:'#',
                                                                handler:function(){
                                                                    //Clear tat ca cac widget trong vung bodyContent
                                                                    clearWidget();
                                                                    initProgress();
                                                                    dojo.widget.byId("controlMenu").href = '<%=StringEscapeUtils.escapeHtml(request.getContextPath() + url2)%>';
                                                                    dojo.event.topic.publish('gotoMenu');
                                                                }
                                                            },
                            </s:if>
                        </s:iterator>
                                                    '-'
                                                ]})
                    </s:if>

                    <s:else>
                                        href:'javascript:void(    0)',
                                        handler:function(){
                                            clearWidget();
                                            initProgress();
                                            dojo.widget.byId("controlMenu").href = '<%=StringEscapeUtils.escapeHtml(request.getContextPath() + url)%>';
                                            dojo.event.topic.publish('gotoMenu');
                                        }
                    </s:else>
                                    },

                </s:iterator>
                                '-'
                            ]
                        })
                    });
                    tb.add(new Ext.Toolbar.Separator());
            </s:iterator>
                tb.addFill();
                tb.addElement('module-status');

        </script>
    </s:if>


    <s:else>
        <script type="text/javascript">

            function clickHandler(item, e)
            {
                alert(item.text);
            }

            var tb = new Ext.Toolbar('module-menu');
            <s:iterator id="permission" value="#session.vsaUserToken.parentMenu">
                tb.add({
                    text: '<s:text name="%{#permission.objectCode}"/>',
                    //text: '<s:text name="%{#permission.objectId}"/>',
                    //cls: 'x-btn-text-icon bmenu',
                    menu: p = new Ext.menu.Menu({

                        items: [
                <s:iterator id="childFunction" value="#permission.childObjects">
                    <s:set name="url" value="#childFunction.objectUrl" scope="request"/>

                    <%
                        String url = "";
                        if (request.getAttribute("url") != null) {
                            url = request.getAttribute("url").toString();
                        }
                    %>
                                    {
                        <s:if test="#childFunction.childObjects.size()>0">
                                            text: '<s:text name="%{#childFunction.objectCode}"/>',
                                            //text: '<s:text name="%{#childFunction.objectId}"/>',
                                            menu: p = new Ext.menu.Menu({
                                                items: [
                        <s:iterator id="child2Function" value="#childFunction.childObjects">
                            <s:if test="#child2Function.objectUrl != null">
                                <s:set name="url2" value="#child2Function.objectUrl" scope="request"/>
                                <%
                                    String url2 = request.getAttribute("url2").toString();
                                %>
                                                            {
                                <%
                                    if (new AuthenticateDAO().checkAllowUrl(url2)) {
                                %>
                                                                text: '<s:text name="%{#child2Function.objectCode}"/>',
                                                                //text: '<s:text name="%{#child2Function.objectId}"/>',
                                                                href:'#'
                                                                ,handler:function(){
                                                                    //Clear tat ca cac widget trong vung bodyContent
                                                                    clearWidget();
                                                                    dojo.widget.byId("controlMenu").href = '<%=StringEscapeUtils.escapeHtml(request.getContextPath() + url2)%>';
                                                                    dojo.event.topic.publish('gotoMenu');
                                                                }
                                <%} else {%>
                                                                //                                                            text: '<span style="color:gray"><s_:property value="getMenu(#child2Function.objectCode)"/></span>',
                                                                text: '<span style="color:gray"><s:text name="%{#child2Function.objectCode}"/></span>',
                                                                href:'#'
                                <%}%>
                                                            },
                            </s:if>
                        </s:iterator>
                                                    '-'
                                                ]})
                    </s:if>

                    <s:else>
                        <%
                            if (new AuthenticateDAO().checkAllowUrl(url)) {
                        %>
                                            //<s_:property value="getMenu(#childFunction.objectCode)"/>
                                            //
                                            //text: '<s:property escapeJavaScript="true"  value="#childFunction.objectId"/>',
                                            text: '<s:text name="%{#childFunction.objectCode}"/>',
                                            href:'javascript:void(0)'
                                            ,handler:function(){
                                                clearWidget();
                                                dojo.widget.byId("controlMenu").href = '<%=StringEscapeUtils.escapeHtml(request.getContextPath() + url)%>';
                                                dojo.event.topic.publish('gotoMenu');
                                            }
                        <%} else {%>
                                            text: '<span style="color:gray"><s:text name="%{#childFunction.objectCode}"/></span>',
                                            href:'javascript:void(0)'
                        <%}%>
                    </s:else>
                                    },

                </s:iterator>
                                '-'
                            ]
                        })
                    });
                    tb.add(new Ext.Toolbar.Separator());
            </s:iterator>
                tb.addFill();
                tb.addElement('module-status');

        </script>


    </s:else>


</s:if>
<%}%>
