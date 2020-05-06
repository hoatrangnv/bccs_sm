/**
 * @author: Hoang Long
 * @class:  DOM
 */

Dialog.prototype.currentDialog = null;
Dialog.prototype.confirmCallback = null;

Dialog.seq = 0;
Dialog.divKey = "dialogVCL-key-";
Dialog.dynamicDialogDivKey = "dialogVCL-key-dynamic-";

Dialog.DYNAMIC_DIALOG_SIZE_DEFAULT_RATE = [0.55, 0.55];

Dialog.SYS_WARNING_DIALOG_TITLE = "Co loi xay ra";

Dialog.MESSAGE_DIALOG_DEFAULT_TITLE = "Thong diep";
Dialog.CONFIRM_DIALOG_DEFAULT_TITLE = "Xac nhan";

Dialog.MESSAGE_DIALOG_ID = "dialogVCL";
Dialog.CONFIRM_DIALOG_ID = "dialogVCL-confirm";
Dialog.LOADING_DIALOG_ID = "dialogVCL-loading";

Dialog.MODAL_DIALOG_MAGIC_DIV_ID = "magicDiv-modalDialog";
Dialog.LOADING_DIALOG_MAGIC_DIV_ID = "magicDiv-loadingDialog";

Dialog.MODAL_DIALOG_LOCKING_DIV_CSS = "loadingDialogLockingDiv";
Dialog.LOADING_DIALOG_LOCKING_DIV_CSS = "loadingDialogLockingDiv";

Dialog.MODAL_DIALOG_LOCKING_DIV_ALPHA = 0;
Dialog.LOADING_DIALOG_LOCKING_DIV_ALPHA = 0;


function Dialog() {
    
}


Dialog.prototype.open = function() {
    
    var anchorDivId, dialogModal;
    var dialog, dialogX, dialogY;

    anchorDivId = arguments[0];
    dom.showElements( anchorDivId );
    dialog = dom.$( anchorDivId );

    var pageSize = dom.getPageWH();
    var scrollCor = dom.getScrollXY();

    var divH = dialog.offsetHeight;
    var divW = dialog.offsetWidth;

    var pageW = pageSize[0];
    var pageH = pageSize[1];

    var scrollX = scrollCor[0];
    var scrollY = scrollCor[1];

    dialogX = dialogY = null;

    switch( arguments.length ) {
        case 1:
            dialogModal = true;
            calcPos( null );
            break;
        case 2:
            dialogModal = arguments[1];
            calcPos( null );
            break;
        case 4:
            dialogModal = arguments[1];
            dialogX = arguments[2];
            dialogY = arguments[3];
            calcPos( [dialogX, dialogY] );
            break;
        default:
            alert( 'Invalid invocation!' );
            return;
            break;
    }

    this.displayDialog( anchorDivId, dialogModal, [dialogX, dialogY] );

    // [ inner functions
    function calcPos( infoArr ) {
        if( infoArr != null ) {
            dialogX = infoArr[0];
            dialogY = infoArr[1];
        } else {
            
            dialogY = scrollY + Math.round( ( pageH - divH ) / 5 );
            dialogX = scrollX + Math.round( ( pageW - divW ) / 2 );
        }
    }
    // ] inner functions
}


Dialog.prototype.close = function( id ) {
    dom.hideElements( id );
    this.killDialog( dom.obj( id ) );
}


Dialog.prototype.title = function() {
    var dlg = dom.$( arguments[0] );
    var divTitle = dom.getElementsByClass( dlg, 'dialog-label' )[0];
    var title;

    switch( arguments.length ) {
        case 1:
            break;
        case 2:
            divTitle.innerHTML = arguments[1];
            break;
    }

    title = divTitle.innerHTML;
    return title;
}


Dialog.prototype.showLoadingDialog = function() {
    var x, y;
    var scrollX, scrollY, pageW, pageH, actualPageW, actualPageH, divW, divH;
    var scrollCor, pageSize;
    var id = Dialog.LOADING_DIALOG_ID;

    dom.showElements( id );

    scrollCor = dom.getScrollXY();
    pageSize = dom.getPageWH();

    scrollX = scrollCor[0];
    scrollY = scrollCor[1];

    pageW = pageSize[0];
    pageH = pageSize[1];

    actualPageW = pageSize[2];
    actualPageH = pageSize[3];

    divW = dom.obj( id ).offsetWidth;
    divH = dom.obj( id ).offsetHeight;

    x = scrollX + Math.round( ( pageW - divW ) / 2 );
    y = scrollY + Math.round( ( pageH - divH ) / 3 );

    var divId = Dialog.seq + 1;
    var div = document.createElement( 'div' );
    div.id = Dialog.divKey + divId;
    dom.obj( Dialog.LOADING_DIALOG_MAGIC_DIV_ID ).appendChild( div );

    dom.obj( div ).className = Dialog.LOADING_DIALOG_LOCKING_DIV_CSS;
    dom.style( div ).width = actualPageW + 'px';
    dom.style( div ).height = actualPageH + 'px';
    dom.setOpacity( div, Dialog.MODAL_DIALOG_LOCKING_DIV_ALPHA );

    // [ Locking div
    dom.style( div ).zIndex = dom.getMaxZIndex() + 1;
    dom.gotoXY( div, 0, 0 );
    // ]

    // [ Dialog
    dom.style( id ).zIndex = dom.getMaxZIndex() + 1;
    dom.gotoXY( id, x, y );
    // ]

    
}


Dialog.prototype.closeLoadingDialog = function() {
    try {
        var id = Dialog.LOADING_DIALOG_ID;
        dom.hideElements( id );

        var div = dom.obj( Dialog.LOADING_DIALOG_MAGIC_DIV_ID );

        if( div.firstChild ) {
            div.removeChild( div.firstChild );
        }
    } catch( e ) {

    }
}


Dialog.prototype.info = function() {
    var title, content;
    switch( arguments.length ) {
        case 1:
            title = Dialog.MESSAGE_DIALOG_DEFAULT_TITLE;
            content = arguments[0];
            break;
        case 2:
            title = arguments[0];
            content = arguments[1];
            break;
    }

    this.showDialogText( 'info', title, content );
}

Dialog.prototype.notice = function() {
    var title, content;
    switch( arguments.length ) {
        case 1:
            title = Dialog.MESSAGE_DIALOG_DEFAULT_TITLE;
            content = arguments[0];
            break;
        case 2:
            title = arguments[0];
            content = arguments[1];
            break;
    }

    this.showDialogText( 'notice', title, content );
}

Dialog.prototype.alert = function() {
    var title, content;
    switch( arguments.length ) {
        case 1:
            title = Dialog.MESSAGE_DIALOG_DEFAULT_TITLE;
            content = arguments[0];
            break;
        case 2:
            title = arguments[0];
            content = arguments[1];
            break;
    }

    this.showDialogText( 'alert', title, content );
}

Dialog.prototype.warning = function() {
    var title, content;
    switch( arguments.length ) {
        case 1:
            title = Dialog.MESSAGE_DIALOG_DEFAULT_TITLE;
            content = arguments[0];
            break;
        case 2:
            title = arguments[0];
            content = arguments[1];
            break;
    }

    this.showDialogText( 'warning', title, content );
}


Dialog.prototype.showDialogText = function( type, title, content ) { 
    var className = "";

    switch( type ) {
        case 'info':
            className = "messageIcon";
            break;
        case 'notice':
            className = "noticeIcon";
            break;
        case 'alert':
            className = "alertIcon";
            break;
        case 'warning':
            className = "warningIcon";
            break;
    }

    dom.obj( Dialog.MESSAGE_DIALOG_ID + '-content' ).className = className;
    dom._html( Dialog.MESSAGE_DIALOG_ID + '-title', title );
    dom._html( Dialog.MESSAGE_DIALOG_ID + '-content', content );
    this.displayDialog( Dialog.MESSAGE_DIALOG_ID );
}


Dialog.prototype.closeTextDialog = function() {
    dom.hideElements( Dialog.MESSAGE_DIALOG_ID );
    this.killDialog( dom.obj( Dialog.MESSAGE_DIALOG_ID ) );
}


Dialog.prototype.confirm = function() {
    var title, content, yesCallback, noCallback;
    switch( arguments.length ) {
        case 3:
            title = Dialog.CONFIRM_DIALOG_DEFAULT_TITLE;
            content = arguments[0];
            yesCallback = arguments[1];
            noCallback = arguments[2];
            break;
        case 4:
            title = arguments[0];
            content = arguments[1];
            yesCallback = arguments[2];
            noCallback = arguments[3];
            break;
    }

    this.confirmCallback = [ yesCallback,noCallback ];

    dom._html( Dialog.CONFIRM_DIALOG_ID + '-title', title );
    dom._html( Dialog.CONFIRM_DIALOG_ID + '-content', content );
    this.displayDialog( Dialog.CONFIRM_DIALOG_ID );
}


Dialog.prototype.closeConfirmDialog = function( value ) {
    if( this.confirmCallback != null ) {
        if( value ) {
            if( this.confirmCallback[0] != null || this.confirmCallback[0] != "" ) {
                eval( this.confirmCallback[0] );
            }
        } else {
            if( this.confirmCallback[1] != null || this.confirmCallback[1] != "" ) {
                eval( this.confirmCallback[1] );
            }
        }
    }
    
    dom.hideElements( Dialog.CONFIRM_DIALOG_ID );
    this.confirmCallback = null;
    this.killDialog( dom.obj( Dialog.CONFIRM_DIALOG_ID ) );
}


Dialog.prototype.displayDialog = function() {
    var id, modal, x, y;
    var scrollX, scrollY, pageW, pageH, actualPageW, actualPageH, divW, divH;
    var scrollCor, pageSize;

    x = y = null;
    modal = true;

    switch( arguments.length ) {
        case 1:
            id = arguments[0];
            break;
        case 2:
            id = arguments[0];
            modal = arguments[1];
            break;
        case 3:
            id = arguments[0];
            modal = arguments[1];
            x = arguments[2][0];
            y = arguments[2][1];
            break;
    }

    dom.showElements( id );

    scrollCor = dom.getScrollXY();
    pageSize = dom.getPageWH();

    scrollX = scrollCor[0];
    scrollY = scrollCor[1];

    pageW = pageSize[0];
    pageH = pageSize[1];

    actualPageW = pageSize[2];
    actualPageH = pageSize[3];

    divW = dom.obj( id ).offsetWidth;
    divH = dom.obj( id ).offsetHeight;
    
    if( x == null && y == null ) {
        x = scrollX + Math.round( ( pageW - divW ) / 2 );
        y = scrollY + Math.round( ( pageH - divH ) / 3 );
    }

    if( modal ) {
        var divId = Dialog.seq + 1;
        var div = document.createElement( 'div' );
        div.id = Dialog.divKey + divId;
        dom.obj( Dialog.MODAL_DIALOG_MAGIC_DIV_ID ).appendChild( div );
        dom.obj( id ).lockingDiv = div;

        dom.obj( div ).className = Dialog.MODAL_DIALOG_LOCKING_DIV_CSS;
        dom.style( div ).width = actualPageW + 'px';
        dom.style( div ).height = actualPageH + 'px';
        dom.setOpacity( div, Dialog.MODAL_DIALOG_LOCKING_DIV_ALPHA );

        // [ Locking div
        dom.style( div ).zIndex = dom.getMaxZIndex() + 1;
        dom.gotoXY( div, 0, 0 );
        // ]
    }

    // [ Bg Iframe (for stupid IE)
    if( dom.brwInfo[0].toString().toLowerCase().indexOf( "msie 6.0" ) != -1 ||dom.brwInfo[0].toString().toLowerCase().indexOf( "msie 5.5" ) != -1 ) {
        var iFr = document.createElement( "iframe" )
        iFr.src = "";
        iFr.style.position = "absolute";
        iFr.style.left = x + "px";
        iFr.style.top = y + "px";
        iFr.style.width = divW + "px";
        iFr.style.height = divH + "px";
        iFr.style.zIndex = dom.getMaxZIndex() + 1;
        document.getElementsByTagName( "body" )[0].appendChild( iFr );
        dom.setOpacity( iFr, 0 );
        dom.obj( id ).iFr = iFr;
    } else {
        div.iFr = null;
    }
    // ] Bg Iframe

    // [ Dialog
    dom.style( id ).zIndex = dom.getMaxZIndex() + 1;
    dom.gotoXY( id, x, y );
    // ]
}


Dialog.prototype.getCurrentDialog = function() {
    return this.currentDialog;
}


Dialog.prototype.chooseDialog = function( dlg ) {
    this.currentDialog = dlg;
}


Dialog.prototype.killDialog = function( dlg ) {
    try {
        this.currentDialog = null;

        if( dlg.lockingDiv != null && dlg.lockingDiv != undefined ) {
            dom.obj( Dialog.MODAL_DIALOG_MAGIC_DIV_ID ).removeChild( dlg.lockingDiv );
            dlg.lockingDiv = null;
        }

        if( dlg.iFr ) {
            document.getElementsByTagName( "body" )[0].removeChild( dlg.iFr );
        }
    } catch( e ) {

    }
}


// Event handlers

function dialogMouseUp( div ) {
    var dlg = div.parentNode;
    dlg.style.cursor = "default";

    dialog.chooseDialog( null );
}

function dialogMouseDown( div ) {
    var dlg = div.parentNode;
    if( dlg.style.display != 'none' ) {
        dlg.style.zIndex = dom.getMaxZIndex() + 1;
        dlg.style.cursor = "move";

        dlg.indentX = dom.getLeft( dlg ) - mouseXOnBrowser;
        dlg.indentY = dom.getTop( dlg ) - mouseYOnBrowser;

        dialog.chooseDialog( dlg );
    }
}

function dialogMouseEvent( evtType, btn, div ) {
    var xPos, yPos;
    var isClicked = false;

    switch( evtType ) {
        case 'down':
            isClicked = true;
            xPos = '-15px';
            break;
        case 'over':
            xPos = '-15px';
            break;
        case 'out':
            xPos = '0px';
            break;
    }

    var dlg = div.parentNode.parentNode.parentNode.parentNode;

    switch( btn ) {
        case 'close':
            if( evtType == 'down' ) {
                dlg.style.display = 'none';
                dialog.killDialog( dlg );
            }
            yPos = '0px';
            break;
        case 'minimize':
            if( isClicked ) {
                var divs = dlg.getElementsByTagName( 'div' );
                for( var i = 0; i < divs.length; i++ ) {
                    if(
                        ( divs[i].className && divs[i].className.toLowerCase() == 'body-left' ) ||
                        ( divs[i].className && divs[i].className.toLowerCase() == 'bottom-left' )
                        )
                        {
                        if( divs[i].style.display != 'none' ) {
                            divs[i].style.display = 'none';
                            div.isVisible = false;
                        } else {
                            divs[i].style.display = 'block';
                            div.isVisible = true;
                        }
                    }
                    /*
                    else if( divs[i].className && divs[i].className.toLowerCase().indexOf( 'bottom' ) == 0 ) {
                        if( div.isVisible == false ) {
                            divs[i].style.height = '1px';
                        } else {
                            divs[i].style.height = '6px';
                        }
                    }
                    */
                }
                
                if( dlg.iFr && div.isVisible == false ) {
                    dlg.iFr.style.height = div.parentNode.offsetHeight + 1;
                }
                else if( dlg.iFr && div.isVisible == true ) {
                    dlg.iFr.style.height = dlg.offsetHeight;
                }
            }
            yPos = ( div.isVisible == undefined || div.isVisible == null || div.isVisible == true ) ? '-45px' : '-30px';
            break;
    }

    div.style.cursor = "default";
    div.style.backgroundPosition = [ xPos, yPos ].join( ' ' );
}
