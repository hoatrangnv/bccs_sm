<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page  import="com.viettel.im.common.util.ResourceBundleUtils"%>


<% request.setAttribute("contextPath", request.getContextPath());%>
<%-- request.setAttribute("contextJSPath", ResourceBundleUtils.getResource("js.url"));--%>

<script type="text/javascript">
    var g_contextPath = "${contextPath}";
    var g_jsContextPath = "${fn:escapeXml(contextJSPath)}";

    //tamdt1, 14/11/2009, start
    var g_progressDivInterval; //bien luu tru id cua tien trinh cap nhat the div
    //tamdt1, 14/11/2009, end

</script>
<script type="text/javascript" src="${contextPath}/share/js/DOM.js"></script>
<script type="text/javascript" src="${contextPath}/share/js/dialog.js"></script>
<script type="text/javascript" src="${contextPath}/share/js/JValidator.js"></script>
<script type="text/javascript" src="${contextPath}/share/js/JDataTableMod.js"></script>
<script type="text/javascript" src="${contextPath}/share/js/JFilter.js"></script>




<script type="text/javascript" src="${contextPath}/share/js/scriptaculous/prototype.js"></script>
<script type="text/javascript" src="${contextPath}/share/js/scriptaculous/scriptaculous.js"></script>


<script type="text/javascript" src="${contextPath}/share/js/validate.js"></script>

<script type="text/javascript" src="${contextPath}/share/js/anylink.js"></script>
<link rel="stylesheet" href="${contextPath}/share/css/site.css" charset="UTF-8" type="text/css" />
<script type="text/javascript" src="${contextPath}/share/js/dolphin.js"></script>

<link rel="stylesheet" type="text/css" href="${contextPath}/share/css/ajaxtags.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/share/css/site_1.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/share/css/displaytag.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/share/img/aqua/theme.css" charset="UTF-8" />
<link rel="stylesheet" href="${contextPath}/share/css/style.css" charset="UTF-8" type="text/css"/>
<link rel="stylesheet" href="${contextPath}/share/css/custom.css" charset="UTF-8" type="text/css"/>
<link rel="stylesheet" href="${contextPath}/share/css/displaytagex.css" charset="UTF-8" type="text/css"/>
<link rel="stylesheet" href="${contextPath}/share/css/dtree.css'/>" charset="UTF-8" type="text/css"/>
<link rel="stylesheet" href="${contextPath}/share/css/anylink.css'/>" charset="UTF-8" type="text/css"/>

<link rel="stylesheet" href="${contextPath}/tpl/css/template.css" type="text/css"/>
<link rel="stylesheet" href="${contextPath}/tpl/css/form.css" type="text/css"/>
<link rel="stylesheet" href="${contextPath}/tpl/css/custom.css" type="text/css"/>

<%-- Extjs --%>
<script type="text/javascript" src="${contextPath}/extjs/ext-prototype-adapter.js"></script>
<script type="text/javascript" src="${contextPath}/extjs/ext-base.js"></script>
<script type="text/javascript" src="${contextPath}/extjs/ext-all.js"></script>
<script type="text/javascript" src="${contextPath}/share/js/common.js"></script>
<link rel="stylesheet" href="${contextPath}/extjs/css/ext-all.css" charset="UTF-8" type="text/css"/>

<link rel="stylesheet" href="${contextPath}/extjs/css/my-ext.css" charset="UTF-8" type="text/css"/>

<!-- style rieng cua du an -->
<script type="text/javascript" src="${contextPath}/share/js/imCommon.js"></script>
<link rel="stylesheet" href="${contextPath}/share/css/privateStyle.css" charset="UTF-8" type="text/css"/>
<link rel="stylesheet" href="${contextPath}/share/css/dialog.css" charset="UTF-8" type="text/css"/>
<script type="text/javascript" src="${contextPath}/share/js/menu.js"></script>




<!--Shortcut Key-->
<script type="text/javascript" src="${contextPath}/share/js/shortcut/manager_shortcut.js" type="text/javascript"></script>
<script type="text/javascript" src="${contextPath}/share/js/shortcut/shortcut.js" type="text/javascript"></script>
<script type="text/javascript" src="${contextPath}/share/js/shortcut/utils.js" type="text/javascript"></script>
<script type="text/javascript" src="${contextPath}/share/js/vt-token.js" ></script>

<!-- end by HaVn on 27032009-->


<!-- tamdt1, 06/04/2009 - start -->
<script type="text/javascript" src="${contextPath}/share/js/two-list.js"></script>


<!-- jsProgressBarHandler prerequisites : prototype.js -->
<!--script type="text/javascript" src="${contextPath}/share/js/progressbar/js/prototype/prototype.js"></script-->

<!-- jsProgressBarHandler core -->
<script type="text/javascript" src="${contextPath}/share/js/progressbar/js/bramus/jsProgressBarHandler.js"></script>


<!-- tamdt1 - end -->
<!-- js search-->
<!-- R499-->
<script type="text/javascript" src="${contextPath}/share/js/jquery/jquery-1.3.2.js"></script>
<script>
      jQuery.noConflict();
</script>


<!-- [ LongH -->
<script type="text/javascript">    
    var dom = new DOM();
    var dialog = new Dialog();    
    var mouseXOnBrowser, mouseYOnBrowser, mouseXOnScreen, mouseYOnScreen;
    var jTblStore = [];

    function documentMouseMoveHandler( evt ) {
        var event = ( window.event ) ? window.event : evt;

        mouseXOnBrowser = event.clientX;
        mouseYOnBrowser = event.clientY;
        mouseXOnScreen = event.screenX;
        mouseYOnScreen = event.screenY;

        if( dialog.getCurrentDialog() != null ) {
            var dlg = dialog.getCurrentDialog();
            var y = Math.round( mouseYOnBrowser ) + dlg.indentY;
            var x = Math.round( mouseXOnBrowser ) + dlg.indentX;

            dom.gotoXY( dlg, x, y );

            if( dlg.iFr ) {
                dom.gotoXY( dlg.iFr, x, y );
            }
        }

    }

    function windowResizeHandler( evt ) {
        var event = ( window.event ) ? window.event : evt;
        var divs = document.getElementsByTagName( 'div' );
        var i;

        var x, y;
        var actualPageW, actualPageH;
        var pageSize;

        pageSize = dom.getPageWH();

        x = '0px';
        y = '0px';

        actualPageW = pageSize[2];
        actualPageH = pageSize[3];

        for( i = 0; i < divs.length; i++ ) {
            if( divs[i].id.indexOf( Dialog.divKey ) != -1 && dom.isVisible( divs[i].id ) ) {
                dom.style( divs[i] ).top = y;
                dom.style( divs[i] ).left = x;
                dom.style( divs[i] ).height = actualPageH + 'px';
                dom.style( divs[i] ).width = actualPageW + 'px';
            }
        }

    }

    document.onmousemove = documentMouseMoveHandler;
    window.onresize = windowResizeHandler;

    /*
    document.onkeydown = function( e ) {
        var evt = ( window.event ) ? window.event : e;
        var prop;
        var needToScroll;

        for( prop in jTblStore ) {
            if( jTblStore[prop] && jTblStore[prop].active ) {
                needToScroll = jTblStore[prop].active( evt );
                if( needToScroll ) {
                    break;
                }
            }
        }

        if( needToScroll == false ) {
            evt.returnValue = false;
            evt.cancelBubble = true;
            return false;
        }
        else if( needToScroll == true ) {
            evt.returnValue = true;
            evt.cancelBubble = false;
            return true;
        }
    }
     */
    document.onmouseup = function( e ) {
        var event = ( window.event ) ? window.event : e;
        var prop;

        var mXOnBrowser = event.clientX;
        var mYOnBrowser = event.clientY;

        for( prop in jTblStore ) {
            if( jTblStore[prop] && jTblStore[prop].test ) {
                jTblStore[prop].test( mXOnBrowser, mYOnBrowser );
            }
        }
    }

    Dialog.SYS_WARNING_DIALOG_TITLE = "Có lỗi xảy ra";
    Dialog.MESSAGE_DIALOG_DEFAULT_TITLE = "Thông điệp";
    Dialog.CONFIRM_DIALOG_DEFAULT_TITLE = "Xác nhận";

    function calcLockingDivAppearance() {
        var div = $( 'overlay' );
        var bodyContent = document.getElementById('bodyContent');
        div.style.top = bodyContent.offsetTop + 'px';
        div.style.left = bodyContent.offsetLeft + 'px';
        div.style.width = bodyContent.offsetWidth + 'px';
        div.style.height = bodyContent.offsetHeight + 'px';
    }
    /*
    function doBeforeCloseBrowser(){
        var closeBrowserAction = "${contextPath}/Authentication!actionCloseBrowser.do";
        // Authentication để đúng với action Authentication của dự án
        bindObj = {
            url : closeBrowserAction,
            content : {},
            useCache: false,
            preventCache: true,
            error: null,
            handler: null,
            method: "post",
            mimetype: "text/html"
        };
        dojo.io.bind( bindObj );
    }
*/

</script>
<!-- [ LongH -->

