var appLogin = angular.module('app.login', ['satellizer','ngResource', 'ui.router','ui.validate','ngDialog']);

// CONTROLLERS
appLogin.controller('loginController', function ($scope, $auth, $window,$http, ngDialog) {

	$scope.login = function() {
      $auth.login({ login: $scope.loginUsuario, senha: $scope.senha })
        .then(function() {
          console.log("Login efetuado com sucesso");
        })
        .catch(function(response) {
        	console.log("Erro no login:"+response.data);
			ngDialog.open({
			    template: '<span class="mif-info on-right on-left" style="background: white; color: red; padding: 10px; border-radius: 50%"></span>  Usu&aacute;rio ou senha inv&aacute;lidos.',
			    plain: true
			});
        	
        });
    };


	    
    $scope.authenticate = function(provider) {
	    $auth.authenticate(provider)
	      .then(function() {
	    	 
	      })
	      .catch(function(response) {
	    	  
	    });
    };
});