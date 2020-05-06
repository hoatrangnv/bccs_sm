/*
 *  @Author: Hoang Long
 */

JDataTableMod.prototype.tbl;
JDataTableMod.prototype.tbody;
JDataTableMod.prototype.rowIdPrefix = "row-";
JDataTableMod.prototype.rowIdMin = 0;
JDataTableMod.prototype.rowIdMax = 0;
JDataTableMod.prototype.currentTr = null;
JDataTableMod.prototype.isActive = false;

JDataTableMod.prototype.waitTime = null;
JDataTableMod.prototype.filterName = null;

JDataTableMod.prototype.rowEventDblClickCallback = null;

JDataTableMod.prototype.bindingStore = [];
JDataTableMod.prototype.rowStore = [];


function JDataTableMod() {
    this.waitTime = 0.5;
    this.reset();
}


JDataTableMod.prototype.setWaitTime = function( _waitTime ) {
    this.waitTime = _waitTime;
}

JDataTableMod.prototype.setDblClickCallback = function() {
    this.rowEventDblClickCallback = arguments[0];
    switch( arguments.length ) {
        case 1:
            this.rowEventDblClickCallbackParam = null;
            break;
        case 2:
            this.rowEventDblClickCallbackParam = arguments[1];
            break;
    }
}

JDataTableMod.prototype.setCurrentTr = function( _curTr ) {
    this.currentTr = _curTr;
}

JDataTableMod.prototype.setRowIdPrefix = function( _rowPrefix ) {
    this.rowIdPrefix = _rowPrefix;
}

JDataTableMod.prototype.setTable = function( _id ) {
    this.tbl = dom.obj( _id );
    this.tbody = this.tbl.getElementsByTagName( 'tbody' )[0];
    this.rowIdPrefix = this.tbl.id + "-" + this.rowIdPrefix;
}

JDataTableMod.prototype.filter = function( filterName ) {
    this.filterName = filterName;
}



// [ For hilight task
JDataTableMod.prototype.active = function( e ) {
    if( this.isActive == false ) return null;

    var evt = e;
    var keyID = evt.keyCode;



    if( keyID == 40 || keyID == 38 ) {
        var tr, scrollWrapper;
        var trId = this.currentTr.pos;
        var trY, scrollWrapperY;

        // Down
        if( keyID == 40 ) {
            tr = this.getNeighbourRow( trId, false );
        }
        // Up
        else if( keyID == 38 ) {
            tr = this.getNeighbourRow( trId, true );
        }

        if( tr ) {
            this.hilightTr( tr ) ;
            scrollWrapper = this.tbl.parentNode; // overflow-wrapper (div)
            if( scrollWrapper ) {

                // Down
                if( keyID == 40 ) {
                    trY = tr.offsetTop + tr.offsetHeight - scrollWrapper.scrollTop;
                    scrollWrapperY = scrollWrapper.offsetHeight;

                    if( trY > scrollWrapperY ) {
                        scrollWrapper.scrollTop += tr.offsetHeight;
                        //return true;
                    }
                }
                // Up
                else if( keyID == 38 ) {
                    trY = tr.offsetTop - scrollWrapper.scrollTop;
                    scrollWrapperY = 0;

                    if( trY < scrollWrapperY ) {
                        scrollWrapper.scrollTop -= tr.offsetHeight;
                        //return true;
                    }
                }
            }
        } else {
            scrollWrapper = this.tbl.parentNode; // overflow-wrapper (div)
            // Down
            if( keyID == 40 ) {
                scrollWrapper.scrollTop = scrollWrapper.scrollHeight;
            }
            // Up
            else if( keyID == 38 ) {
                scrollWrapper.scrollTop = 0;
            }
        }

        return false;
    }

    return null;
}

JDataTableMod.prototype.inactive = function() {
    this.isActive = false;
}

JDataTableMod.prototype.assignWholeTable = function() {
    try {
        var trs = this.tbody.getElementsByTagName( 'tr' );
        var i, j;

        this.rowIdMax = trs.length - 1;

        for( i = 0; i < trs.length; i++ ) {
            trs[i].id = this.rowIdPrefix + i;
            this.assignEach( trs[i], i );
        }
    } catch( e ) {
        alert( "assignWholeTable: " + e.message );
    }
}

JDataTableMod.prototype.assignEach = function( tr, trPosition ) {
    try {
        var _self = this;
        var tds, row;
        var j;

        row = {};
        row.tds = [];

        tr.pos = trPosition;
        tr.oldOnclick = tr.onclick;

        tr.onclick = function() {
            if( tr.oldOnclick != null && tr.oldOnclick != undefined ) {
                tr.oldOnclick.call();
            }
            _self.hilightTr( this );
            _self.isActive = true;
        }

        if( _self.rowEventDblClickCallback != null ) {
            tr.ondblclick = function() {
                eval( _self.rowEventDblClickCallback + "( this );" );
            }
        }

        row.tr = tr;

        tds = tr.getElementsByTagName( 'td' );
        for( j = 0; j < tds.length; j++ ) {
            row.tds[j] = tds[j].innerHTML;
        }
        
        this.rowStore.push( row );
    } catch( e ) {
        alert( "assignEach: " + e.message );
    }
}

JDataTableMod.prototype.test = function( mXOnBrw, mYOnBrw ) {
    var isInsideByW = ( mXOnBrw >= dom.getLeft( this.tbl ) ) && ( mXOnBrw <= dom.getLeft( this.tbl ) + this.tbl.offsetWidth );
    var isInsideByH = ( mYOnBrw >= dom.getTop( this.tbl ) ) && ( mYOnBrw <= dom.getTop( this.tbl ) + this.tbl.offsetHeight );

    if( !( isInsideByW && isInsideByH ) ) {
        this.inactive();
    }
}

JDataTableMod.prototype.getNeighbourRow = function( curRowId, upOrDown ) {
    var i;
    var targetTr = null;

    // upOrDown : up=true, down=false

    // down
    if( !upOrDown ) {
        for( i = curRowId + 1; i <= this.rowIdMax; i++ ) {
            targetTr = this.$( this.rowIdPrefix + i );
            if( targetTr ) {
                break;
            }
        }
    }
    // up
    else {
        for( i = curRowId - 1; i >= this.rowIdMin; i-- ) {
            targetTr = this.$( this.rowIdPrefix + i );
            if( targetTr ) {
                break;
            }
        }
    }

    return targetTr;
}

JDataTableMod.prototype.hilightTr = function( tr ) {
    this.manupulateTdsInTr( this.currentTr, false );
    this.currentTr = tr;
    this.manupulateTdsInTr( this.currentTr, true );
}

JDataTableMod.prototype.manupulateTdsInTr = function( tr, isHilight ) {
    if( tr == null ) return;

    var bgImg;
    var i;

    if( isHilight ) {
        bgImg = "url( " + g_jsContextPath + "/share/images/datatable-chosen-row-bg.gif )";
    } else {
        bgImg = "none";
    }

    var tds = tr.getElementsByTagName( 'td' );
    for( i = 0; i < tds.length; i++ ) {
        tds[i].style.backgroundImage = bgImg;
    }
}
// ] For hilight task



// [ For client-based search task
JDataTableMod.prototype.search = function() {
    var cmdArr = [];
    var cmd = "";
    var i, item, dataType, pos, obj;

    this.restore();

    for( i = 0; i < this.bindingStore.length; i++ ) {
        item = this.bindingStore[i];
        obj = this.$( item[0] );
        dataType = item[1];
        pos = item[2];
        if( dataType != null && pos != null ) {
            //item.value = item.value.replace( /^\s+|\s+$/g, "" );

            if( obj.disabled != true && obj.value.length > 0 ) {
                cmd = " ( ";
                switch( dataType.toString().toLowerCase() ) {
                    case 's':
                        cmd += " this.rowStore[i].tds[" + pos + "].toString().toLowerCase().indexOf( this.$( '" + obj.id + "' ).value.toString().toLowerCase() ) != -1 ";
                        break;
                    case 'n':
                        cmd += " this.rowStore[i].tds[" + pos + "] == this.$( '" + obj.id + "' ).value ";
                        break;
                }
                cmd += " ) ";
                cmdArr.push( cmd );
            }
        }
    }

    var checkVar;
    if( cmdArr.length > 0 ) {
        for( i = 0; i < this.rowStore.length; i++ ) {
            eval( "checkVar = ( " + cmdArr.join( "&&" ) + " );" );
            if( !checkVar ) {
                this.tbody.removeChild( this.rowStore[i].tr );
            }
        }
    }
}

JDataTableMod.prototype.bindForm = function() {
    try {
        var _self = this;
        var i;
        this.bindingStore = [];

        function bindEvent( id ) {
            var obj = _self.$( id );
            obj.onkeyup = function( e ) {
                var evt = ( window.event ) ? window.event : e;
                setTimeout( _self.filterName + "();", _self.waitTime * 1000 );
            }
        }

        for( i = 0; i < arguments.length; i++ ) {
            // arguments[i] = [ formItemId, formItemDataType, formItemPositionInTable ]
            this.bindingStore.push( arguments[i] );
            if( this.waitTime != null ) {
                bindEvent( arguments[i][0] );
            }
        }
   
    } catch( e ) {
        alert( "bindForm: " + e.message );
    }
}

JDataTableMod.prototype.getBindInfo = function() {
    var id, pos;

    id = arguments[0];
    switch( arguments.length ) {
        case 1:
            pos = 0;
            break;
        case 2:
            pos = arguments[1];
            break;
    }

    var i;
    var resObj = { id:null, dataType:null, pos:null };
    for( i = 0; i < this.bindingStore.length; i++ ) {
        if( id == this.bindingStore[i][pos] ) {
            resObj.id = this.bindingStore[i][0];
            resObj.dataType = this.bindingStore[i][1];
            resObj.pos = this.bindingStore[i][2];
            break;
        }
    }
    return resObj;
}

JDataTableMod.prototype.restore = function() {
    var i;
    for( i = 0; i < this.rowStore.length; i++ ) {
        this.tbody.appendChild( this.rowStore[i].tr );
    }
}

JDataTableMod.prototype.truncate = function() {
    var i;
    for( i = 0; i < this.rowStore.length; i++ ) {
        try {
            this.tbody.removeChild( this.rowStore[i].tr );
        } catch ( e ) {

        }
    }
}

JDataTableMod.prototype.reset = function() {
    this.rowStore = [];
    this.bindingStore = [];
}

JDataTableMod.prototype.$ = function( id ) {
    return document.getElementById( id );
}
// ] For client-based search task
