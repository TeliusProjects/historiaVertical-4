//DECLARACIÓN DE CONSTANTES


//DECLARACIÓN DE VARIABLES
var user 	= document.querySelector("#username"),
	mail 	= document.querySelector("#mail"),
	name 	= document.querySelector("#name"),
	phone 	= document.querySelector("#phone");
	/*gender	= document.querySelector("#gender")*/

var expReg   	= /^[\w-._]+$/;
var expRegMail	= /^[\w-._]+@[a-zA-Z_]+\.[a-zA-Z]{2,3}$/;
			   // /^[\w-._]+@[a-zA-Z_]+\.[a-zA-Z]{2,3}$/;
			   // /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
var expRegPhone = /^[0-9]{9}$/;
var expRegName  = /^[\w]+$/;


//DECLARACIÓN DE FUNCIONES
function dataValidate(evento){
	var whatInput = evento.target;
	var validated = true,
		color;

	if(whatInput.id == "username"){
		if(!expReg.exec(whatInput.value)){
			alert("Username and password field only accept characters, dots, hyphens and underscores.");
			validated = false;
			whatInput.focus();
		}
	}
	else if(whatInput.id == "mail"){
		if(!expRegMail.exec(whatInput.value)){
			alert("Email field only accept characters, dots, hyphens, at's and underscores.");
			validated = false;
			whatInput.focus();
		}
	}
	else if(whatInput.id == "name"){
		if(!expRegName.exec(whatInput.value)){
			alert("Name only accept characters. You can use the spaces too.");
			validated = false;
			whatInput.focus();
		}
	}
	else if(whatInput.id == "phone"){
		if(!expRegPhone.exec(whatInput.value)){
			alert("Phone field only accept 9 numbers.");
			validated = false;
			whatInput.focus();
		}
	}

	color = (validated)?"green":"red";

	if(color == "red"){
		whatInput.style.background = "rgba(255, 0, 0, 0.2)";
		//whatInput.style.outline = "thin solid " + color;
	}
	else if (color == "green"){
		whatInput.style.background = "rgba(0, 230, 0, 0.2)";
		//whatInput.style.outline = "thin solid " + color;	
	}
}

function chargeDocument(){
	user.addEventListener("blur",dataValidate);
	mail.addEventListener("blur",dataValidate);
	name.addEventListener("blur",dataValidate);
	phone.addEventListener("blur",dataValidate);
}

//ASIGNACIÓN DE EVENTOS
window.addEventListener("load",chargeDocument);