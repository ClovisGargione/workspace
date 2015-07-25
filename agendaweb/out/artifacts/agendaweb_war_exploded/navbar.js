
var appNavbar = angular.module('app.navbar', ['ui.router','ui.bootstrap','ui.mask','ui.validate']);
	 
// CONTROLLERS
appNavbar.controller('navbarController', function ($scope, $auth ) {
	var payload = $auth.getPayload(); // decoded middle part of JSON Web Token
	$scope.usuarioLogado = payload.nome;
	$scope.usuarioId = payload.sub;
	
});