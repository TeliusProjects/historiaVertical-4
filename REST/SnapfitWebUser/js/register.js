//DECLARACIÓN DE CONSTANTES


//DECLARACIÓN DE VARIABLES
var user 		 = document.querySelector("#username"),
	mail 		 = document.querySelector("#mail"),
	mail2 		 = document.querySelector("#mail2"),
	pass 		 = document.querySelector("#password"),
	pass2 		 = document.querySelector("#password2"),
	register 	 = document.querySelector("#register");

var expReg   	= /^[\w-._]+$/;
var expRegMail	= /^[\w-._]+@[a-zA-Z_]+\.[a-zA-Z]{2,3}$/;
			   // /^[\w-._]+@[a-zA-Z_]+\.[a-zA-Z]{2,3}$/;
			   // /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

//DECLARACIÓN DE FUNCIONES
function dataValidate(evento){
	var whatInput = evento.target;
	var validated = true,
		color;

	if(whatInput.id == "username" || whatInput.id == "password" || whatInput.id == "password2"){
		if(!expReg.exec(whatInput.value)){
			alert("Username and password field only accept characters, dots, hyphens and underscores.");
			validated = false;
			whatInput.focus();
		}
	}
	else if(whatInput.id == "mail" || whatInput.id == "mail2"){
		if(!expRegMail.exec(whatInput.value)){
			alert("Email field only accept characters, dots, hyphens, at's and underscores.");
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
	mail2.addEventListener("blur",dataValidate);
	pass.addEventListener("blur",dataValidate);
	pass2.addEventListener("blur",dataValidate);
}

//ASIGNACIÓN DE EVENTOS
window.addEventListener("load",chargeDocument);