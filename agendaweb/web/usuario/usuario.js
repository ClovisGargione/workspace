var appUsuario = angular.module('app.usuario', ['satellizer','ngResource', 'ui.router','ui.validate','ngDialog', 'ngFileUpload']);

//services
appUsuario.factory("usuariosServices", ['$http','$location','$state','ngDialog', function($http,$location,$state,ngDialog) {
	var serviceBase = 'services/secure/usuarios/';
	var downloadService = 'services/filedownload/get-file/';
	var obj = {};

	obj.downloadFile = function(id){
		return $http.get(downloadService+id)
			.success(function(data, status, headers, config, retData) {
				var iframe = document.createElement("iframe");
				iframe.setAttribute("src", downloadService+id);
				iframe.setAttribute("style", "display: none");
				document.body.appendChild(iframe);

			}).error(function(data, status, headers, config) {
				console.log("Erro:"+data);
			});
	};

	obj.alterarUsuario = function (usuario) {


		$http.put(serviceBase, usuario)
			.success(function(data, status, headers, config) {
				ngDialog.open({
					template: '<span class="mif-checkmark on-right on-left" style="background: white; color: greenyellow; padding: 10px; border-radius: 50%"></span>  Usuário alterado com sucesso.',
					plain: true
				});
				$state.go('home', null, { reload: true });

			}).error(function(data, status, headers, config) {
				console.log("Erro:"+data);
				ngDialog.open({
					template: '<span class="mif-cancel on-right on-left" style="background: white; color: red; padding: 10px; border-radius: 50%"></span>  Não foi possível alterar o usuário.',
					plain: true,
					controller: ['$scope', function($scope) {
						$scope.erro = data.mensagem;
					}]
				});
			});
	};

	obj.obterUsuarioPorId = function(id){
		return $http.get(serviceBase+id)
			.success(function(data, status, headers, config) {

			}).error(function(data, status, headers, config) {
				console.log("Erro:"+data);
			});
	};

	return obj;

}]);


// CONTROLLERS
appUsuario.controller('registroController', function ($scope, Upload, $auth, $window, $http, ngDialog) {


	
	$scope.signup = function() {
		$auth.signup({
		nome: $scope.nome,
		login: $scope.login,
		email: $scope.email,
		senha: $scope.senha,
		localImagem: $scope.localImagem
		}).catch(function(response) {
			if (typeof response.data.message === 'object') {
				ngDialog.open({
					template: '<span class="mif-info on-right on-left" style="background: white; color: royalblue; padding: 10px; border-radius: 50%"></span>  Login já cadastrado.',
					plain: true
				});

			} else {
				ngDialog.open({
					template: '<span class="mif-info on-right on-left" style="background: white; color: royalblue; padding: 10px; border-radius: 50%"></span>  Login já cadastrado.',
					plain: true
				});
			}
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

appUsuario.controller('editUsuarioController', function ($scope, Upload, $http, $timeout, $location, $state, usuariosServices, dados, ngDialog) {
	$scope.progress = null;
	$scope.usuario = dados.data;
	$scope.onFileSelect = function(files) {
		//$files: an array of files selected, each file has name, size, and type.
		for (var i = 0; i < files.length; i++) {
			var file = files[i];
			$scope.upload = Upload.upload({
				url: '/services/secure/fileupload/upload', //upload  servlet url
				data: {
					myObj: $scope.myModelObj
				},
				file: file
			}).progress(function(evt) {
				$scope.progress = parseInt(100.0 * evt.loaded / evt.total);
				console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
				$scope.size = files[0].size/1000;
				$scope.name = files[0].name;
			}).success(function(data, status, headers, config) {
				ngDialog.open({
					template: '<span class="mif-checkmark on-right on-left" style="background: white; color: greenyellow; padding: 10px; border-radius: 50%"></span>  Upload de imagem realizado com sucesso.',
					plain: true,
					controller: ['$scope', function($scope) {
						$scope.erro = data.mensagem;
					}]
				});
				$state.go('usuarios.edita', null, { reload: false });
				console.log(data);
			});
		}
	};

	$scope.salvarUsuario = function(usuario){
		usuariosServices.alterarUsuario(usuario);
	};

	$scope.download = function(id){
		return usuariosServices.downloadFile(id);
	}



});




appUsuario.config(['$stateProvider','$urlRouterProvider','$authProvider',
	function($stateProvider, $urlRouterProvider, $authProvider) {
		$stateProvider
			.state('usuarios', {
				abstract: true,
				url: '/usuarios',
				templateUrl: 'usuario/index.html',
				resolve: {
					authenticated: ['$location', '$auth', function($location, $auth) {
						if (!$auth.isAuthenticated()) {
							return $location.path('/login');
						}
					}]

				}
			})
			.state('usuarios.edita', {
				url: '/edit/:usuarioId',
				templateUrl: 'usuario/edit.html',
				controller: 'editUsuarioController',
				resolve: {
					dados: function(usuariosServices, $stateParams){
						return usuariosServices.obterUsuarioPorId($stateParams.usuarioId);
					}
				}
			})
			.state('usuarios.home', {
				url: '/home',
				templateUrl: 'home/home.html',
				controller: 'null'

			});

		$urlRouterProvider.otherwise('/home');
	}
]);


