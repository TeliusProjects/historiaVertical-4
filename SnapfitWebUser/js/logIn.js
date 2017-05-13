//DECLARACIÓN DE CONSTANTES


//DECLARACIÓN DE VARIABLES
var user 	 = document.querySelector("#username"),
	pass 	 = document.querySelector("#password"),
	login 	 = document.querySelector("#login");

var expReg   = /^[\w-._]+$/;

//DECLARACIÓN DE FUNCIONES
function dataValidate(evento){
	var whatInput = evento.target;
	var validated = true,
		color;

	if(whatInput.id == "username" || whatInput.id == "password"){
		if(!expReg.exec(whatInput.value)){
			alert("Username and password field only accept characters, dots, hyphens and underscores.");
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
	pass.addEventListener("blur",dataValidate);
}

//ASIGNACIÓN DE EVENTOS
window.addEventListener("load",chargeDocument);