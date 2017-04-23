//DECLARACIÓN DE CONSTANTES
var READY_STATE_COMPLETE = 4;
var OK = 200;

//DECLARACIÓN DE VARIABLES
var ajax=null;
var btnLogIn=document.querySelector("#logIn");

//DECLARACIÓN DE FUNCIONES
function objetoAJAX()
{
	if(window.XMLHttpRequest)
	{
		return new XMLHttpRequest();
	}
	else if(window.ActiveXObject)
	{
		return new ActiveXObject("Microsoft.XMLHTTP");
	}
}

function enviarDatos()
{
	if(ajax.readyState == READY_STATE_COMPLETE)
	{
		if(ajax.status == OK)
		{
			alert("wiii");
		}
		else
		{
			alert("nooo");
		}
	}
}

function ejecutarAJAX(datos)
{
	ajax = objetoAJAX();
	ajax.onreadystatechange=enviarDatos();
}

function logToApp()
{
	//alert("Funciona");
	var datos = "accion=log";
	ejecutarAJAX(datos);
}

function alCargarDocumento()
{
	btnLogIn.addEventListener("click",logToApp);
}

//ASIGNACIÓN DE EVENTOS
window.addEventListener("load",alCargarDocumento);