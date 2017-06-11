//DECLARACIÓN DE CONSTANTES


//DECLARACIÓN DE VARIABLES
//Variable para el menú desplegable
var menu = document.querySelector("#menu");

var modal = document.getElementById('myModal');
var img = document.getElementById('img');
var modalImg = document.getElementById("img01");
var captionText = document.getElementById("caption");
var span = document.getElementsByClassName("close")[0];

//DECLARACIÓN DE FUNCIONES
img.onclick = function(){
    modal.style.display = "block";
    modalImg.src = this.src;
    captionText.innerHTML = this.alt;
}

span.onclick = function() {
  modal.style.display = "none";
} 

function showMenu(){
}

function chargeDocument(){
	menu.addEventListener("click",showMenu);
}

//ASIGNACIÓN DE EVENTOS
window.addEventListener("load",ChargeDocument);