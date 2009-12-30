function findTargetWindow( targetName, theWindow )
{
	var theName = targetName.toLowerCase( );
	if( theName == "_blank" )
		return null;
	else if( theName == "_parent" )
		return theWindow.parent;
	else if( theName == "_self" )
		return theWindow;
	else if( theName == "_top" )
		return theWindow.top;
	else
	{
		// Jump to the top window and start looking there
		if( top.name == targetName )
			return top;
		theWind = findChildTarget( targetName, top );
		return theWind;
	}
	
	function findChildTarget( targetName, theWindow )
	{
		for( var i = 0; i < theWindow.frames.length; i++ )
		{
			if( theWindow.frames[i].name == targetName )
				return theWindow.frames[i];
			else
			{
				var theWind = findChildTarget( targetName, theWindow.frames[i] );
				if( theWind )
					return theWind;
			}
		}
		return null
	}
}

function NavMenuItem( name, linkParams, linkId )
{
	this.name = name;
	this.linkId = linkId;
	linkParams = linkParams.replace( /([a-zA-Z]+)=/g, "\n$1\t"  );
	paramArr = linkParams.split( /\n/ );
	for( var i=0; i < paramArr.length; i++ )
	{
		var keyValuePair = paramArr[i].split( /\t/ );
		if( keyValuePair && keyValuePair.length == 2 )
		{
			var key = keyValuePair[0].toLowerCase( );
			var value = keyValuePair[1].replace( /^ *"/, "" ).replace( /" *$/, "" );
			this[key] = value;
		}
	}

	this.exec = function(  )
	{
		if( this.href )
		{
			if( this.onclick )
				eval( this.onclick );
			else if( this.target )
			{
				var theWind = findTargetWindow( this.target, self );
				if( theWind )
				{
					theWind.location = this.href;
					theWind.focus( );
				}
				else
					window.open( this.href, this.target );
			}
			else
				document.location = this.href;
		}
	}
	this.drawEmptyLink = function( )	
	{
		document.write( '<a id="'+this.linkId+'" '+this.linkParams+'></a>' );
	}

	this.drawLink = function( linkClass )	
	{
		document.write( '<a id="'+this.linkId+'" '+this.linkParams+' class="'+linkClass+'">'+this.name+'</a>' );
	}

	return this;
}
function ClickMenu( name, title, menuId, titleClass, menuClass, itemClass, hiliteClass, titleTableParams, menuTableParams  )
{
	this.name = name;
	this.title = title;
	this.menuId = menuId;
	this.titleClass = titleClass;
	this.menuClass = menuClass;
	this.itemClass = itemClass;
	this.hiliteClass = hiliteClass;
	this.titleTableParams = titleTableParams;
	this.menuTableParams = menuTableParams;
	this.imgSrc = "";
	this.menuItems = new Array( );
	this.doClose = false;
	
	this.addMenuItem = function( linkName, linkParams )
	{
		var linkId = "link_"+this.name+"_"+this.menuItems.length;
		this.menuItems[this.menuItems.length]= new NavMenuItem( linkName, linkParams, linkId );
	}
	
	this.show = function( )
	{
		var theMenu = document.getElementById( this.menuId );
		if( theMenu )
		{
			if( this.doClose )
			{
				this.hide( );
			}
			else
			{
				clickMenuHide( );
				theMenu.style.visibility = "visible";
				this.doClose = false;
				clickMenuHide.menu = this
				document.onclick = clickMenuHide;
			}
		}
	}
	
	this.hide = function( )
	{
		var theMenu = document.getElementById( this.menuId );
		if( theMenu && this.doClose)
		{
			theMenu.style.visibility = "hidden";
			document.onclick = null;
			this.doClose = false;
		}
		else
		{
			this.doClose = true;
		}
	}
	
	this.setItem = function( itemNum )
	{
		var theItem = document.getElementById( this.menuId+"_"+itemNum );
		if( theItem )
		{
			this.selectedItem = itemNum;
			theItem.className = this.hiliteClass;
		}
	}
	
	this.clearItem = function( itemNum )
	{
		var theItem = document.getElementById( this.menuId+"_"+itemNum );
		if( theItem )
		{
			this.selectedItem = null;
			theItem.className = this.itemClass;
		}
	}
	this.hitItem = function( itemNum )
	{
		if( itemNum < this.menuItems.length )
		{
			this.hide( );
			window.event.cancelBubble = true;
			this.menuItems[itemNum].exec( );
		}
	}
	this.draw = function( )
	{
		document.write( '<table '+this.titleTableParams+'>' );
		document.write( '<tr>' );
		document.write( '<td class="'+this.titleClass+'" style="cursor:hand;" onClick="'+this.name+'.show()">' );
		document.write( this.title );
		if( this.imgSrc != "" )
			document.write('&nbsp;<img src="'+this.imgSrc+'">');
		document.write( '<br>' );
		document.write( '<span style="position:relative;top:0;left:0;">' );
		document.write( '<div id="'+this.menuId+'" class="'+this.menuClass+'" style="position:absolute;left:0;top:0;visibility:hidden;cursor:hand;">' );
		document.write( '<table '+this.menuTableParams+'>' );
		for( var i = 0; i < this.menuItems.length; i++ )
		{
			document.write( '<tr><td id="'+this.menuId+'_'+i+'" class="'+this.itemClass+'" ');
			document.write( 'onMouseOver="'+this.name+'.setItem( '+i+' )" onMouseOut="'+this.name+'.clearItem( '+i+' )" ' );
			document.write( 'onClick="'+this.name+'.hitItem( '+i+' )">'+this.menuItems[i].name );
//			this.menuItems[i].drawEmptyLink( );
			document.write( '</td></tr>' );
		}
		document.write( '</table></div></span></td></tr></table>' );
	}
	return this;
}

function clickMenuHide( )
{
	if( clickMenuHide.menu )
		clickMenuHide.menu.hide( );

}
