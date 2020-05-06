<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/config/vsa-defs.tld" prefix="vp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<html>   
    <head>
        <sx:head />
        <s:set name="key">
            <tiles:getAsString name='title' ignore="true"/>
        </s:set>
        <title><s:text name="%{#attr.key}"/></title>

        <tiles:insertTemplate template="/WEB-INF/jsp/pages/layout/external_CSS_JS.jsp" />


    </head>
    <%--body id="minwidth" topmargin="0" onbeforeunload="doBeforeCloseBrowser ();"--%>
    <body id="minwidth" topmargin="0">
        <script>
            
            var pageId = Math.floor(Math.random()*10000000);
        </script>
        <%--
        <div id="border-top"  topmargin="0">
            <!-- [BEGIN] Banner -->

            <div>
                <div>
                    <tiles:insertAttribute name="header"/>
                </div>
            </div>
        </div>
        --%>
        <div id="token">
        </div>
        <!-- [END] Banner -->
        <div id="maskLayer" class="mask" style="width:100%"></div>

        <tiles:insertAttribute name="menu"/>
        <div id="content-box">
            <div class="border">

                <div id="padding11" class="padding">
                    <div id="toolbar-box">
                        <div class="t">
                            <div class="t">
                                <div class="t"></div>
                            </div>
                        </div>
                        <div class="m">
                            <div class="toolbar" id="toolbar">
                                <table class="toolbar"><tbody><tr>
                                        </tr></tbody></table>
                            </div>
                            
                            <!-- [BEGIN] Header -->
                            <div class="header" id="myHeader" align="left">
                                <s:text name="%{#attr.key}"/>
                                <%--tiles:getAsString name="title" ignore="true"/--%>
                                
                            </div>
                            <!-- Them Canh Bao User Change Pass  -->
                            <s:if test="#session.isWarningLockUser || #session.isWarningLockUser">
                                
                                 <div style="font-weight: bold ;font-size: 200%;color: #FF0000; float: right; padding-top: 7;padding-right: 10" >
                                         
                                        <s:if test="#session.isLockUser || #session.isLockUser">
                                            <%--Your password is valid in <s:_property value="#session.vsaUserToken.timeToPasswordExpire"/> days. After <s:_property value="#session.vsaUserToken.timeToPasswordExpire"/> days, if you do not change your password, your user will be locked.--%>
                                            <font ><marquee direction="left" style="background:white"><s:property escapeJavaScript="true"  value="#session.msgLockUser"/></marquee></font>
                                            &nbsp&nbsp
                                            <a href="https://10.229.42.39:7001/passportv3/changePassword" style="color:#0000CD; float: right;"><font color="red"><blink>Click Here </blink></font> To Change PassWord</a>
                                            <script language="javascript">
                                                confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="#session.msgLockUser"/>'));
                                            </script>
                                        </s:if>
                                        <s:else>
                                            <%--Your password is valid in <s:_property value="#session.vsaUserToken.timeToPasswordExpire"/> days. After <s:_property value="#session.vsaUserToken.timeToPasswordExpire"/> days, if you do not change your password, your user will be locked.--%>
                                            <font ><marquee direction="left" style="background:white"><s:property escapeJavaScript="true"  value="#session.msgPasswordExpire"/></marquee></font>
                                            &nbsp&nbsp
                                            <a href="https://10.229.42.39:7001/passportv3/changePassword" style="color:#0000CD; float: right;"><font color="red"><blink>Click Here </blink></font> To Change PassWord</a>
                                           <script language="javascript">
                                                confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="#session.msgPasswordExpire"/>'));
                                           </script> 
                                        </s:else>        
                                 </div>
                              </s:if>
                              <s:else>
                                    <div style="font-weight: bold ;font-size: larger;color: #FF0000; float: right; padding-top: 7;padding-right: 10" >
<%--                                    Your password is valid in <s:_property value="#session.vsaUserToken.timeToPasswordExpire"/> days. After <s:_property value="#session.vsaUserToken.timeToPasswordExpire"/> days, if you do not change your password, your user will be locked.--%>
                                        <s:property escapeJavaScript="true"  value="#session.msgPasswordExpire"/>
                                         &nbsp&nbsp
                                         &nbsp&nbsp
                                         &nbsp&nbsp
                                        <a href="https://10.229.42.39:7001/passportv3/changePassword" style="color:#0000CD; float: right;"><font color="red">Click Here</font> To Change PassWord</a>
                                    </div>
                               </s:else>    
                            <!-- [END] Header-->
                            <div class="clr"></div>
                        </div>
                        <div class="b">
                            <div class="b">
                                <div class="b"></div>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>

                <div class="padding">
                    <!--tiles:insert page="/WEB-INF/jsp/pages/layout/toolbar.jsp"/-->
                    <div id="element-box">
                        <div class="t">
                            <div class="t">
                                <div class="t"><tags:processing/></div>
                            </div>
                        </div>
                        <div class="m" align="center">
                            <sx:div id="bodyContent" >
                                <div>
                                    <tiles:insertAttribute name="body"/>
                                </div>
                            </sx:div >
                        </div>
                        <div class="b">
                            <div class="b">
                                <div class="b"></div>
                            </div>
                        </div>
                    </div>
                    <noscript>Must be enable JavaScript</noscript>
                    <div class="clr"></div>
                </div>
                <div class="clr"></div>
            </div>
        </div>


        <!-- [BEGIN] Footer -->
        <div id="border-bottom">
            <div>
                <div>
                </div>
            </div>
        </div>
        <div id="footer">

            <tiles:insertAttribute name="footer"/>
        </div>

        <!-- [END] Footer -->
    </body>


</html>


