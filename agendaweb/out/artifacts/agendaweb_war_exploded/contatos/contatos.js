var appContatos = angular.module('app.contatos', ['satellizer','ngResource', 'ui.router','ui.validate','ngDialog', 'ui.bootstrap']);

//FILTERS
appContatos.filter('startFrom', function() {
	return function(input, start) {
 		if(input) {
 			start = +start; //parse to int
 			return input.slice(start);
 		}
 		return [];
 	}
});


//services
appContatos.factory("contatosServices", ['$http','$location','$state','ngDialog', function($http,$location,$state,ngDialog) {
	var serviceBase = 'services/secure/contatos/';

	var obj = {};

	obj.inserirContato = function(contato){
		$http.post(serviceBase, contato)
			.success(function(data, status, headers, config) {
				ngDialog.open({
					template: '<span class="mif-checkmark on-right on-left" style="background: white; color: greenyellow; padding: 10px; border-radius: 50%"></span>  Contato adicionado com sucesso.',
					plain: true
				});
				$state.go('contatos.lista', null, { reload: true });
			}).error(function(data, status, headers, config) {
				console.log("Erro:"+data);
				ngDialog.open({
					template: '<span class="mif-cancel on-right on-left" style="background: white; color: red; padding: 10px; border-radius: 50%"></span>  Não foi adicionar o contato.',
					controller: ['$scope', function($scope) {
						$scope.erro = data.mensagem;
					}]
				});
			});
	};

	obj.alterarContato = function (contato) {


		$http.put(serviceBase, contato)
			.success(function(data, status, headers, config) {
				ngDialog.open({
					template: '<span class="mif-checkmark on-right on-left" style="background: white; color: greenyellow; padding: 10px; border-radius: 50%"></span>  Contato alterado com sucesso.',
					plain: true
				});
				$state.go('contatos.lista', null, { reload: true });

			}).error(function(data, status, headers, config) {
				console.log("Erro:"+data);
				ngDialog.open({
					template: '<span class="mif-cancel on-right on-left" style="background: white; color: red; padding: 10px; border-radius: 50%"></span>  Não foi possível alterar o contato.',
					plain: true,
					controller: ['$scope', function($scope) {
						$scope.erro = data.mensagem;
					}]
				});
			});
	};

	obj.removerContatoPorId = function(id){
		$http.delete(serviceBase+id)
			.success(function(data, status, headers, config) {
				ngDialog.open({
					template: '<span class="mif-checkmark on-right on-left" style="background: white; color: greenyellow; padding: 10px; border-radius: 50%"></span>  Contato removido com sucesso.',
					plain: true
				});
				$state.go('contatos.lista', null, { reload: true });
			}).error(function(data, status, headers, config) {
				console.log("Erro:"+data);
				ngDialog.open({
					template: '<span class="mif-cancel on-right on-left" style="background: white; color: red; padding: 10px; border-radius: 50%"></span>  Não foi possível remover o contato.',
					plain: true,
				});
			});
	};

	obj.obterContatoPorId = function(id){
		return $http.get(serviceBase+id)
			.success(function(data, status, headers, config) {

			}).error(function(data, status, headers, config) {
				console.log("Erro:"+data);
			});
	};

	obj.listarContatos = function(){
		return $http.get(serviceBase)
			.success(function(data, status, headers, config) {

			}).error(function(data, status, headers, config) {
				console.log("Erro:"+data);
			});
	};

    return obj;
	
}]);

appContatos.controller('listaContatoController', function ($scope, $http, $timeout, $location, $state, contatosServices, listaContatos, ngDialog) {

	$scope.list = listaContatos.data;
	$scope.currentPage = 1; //current page
	$scope.entryLimit = 10; //max no of items to display in a page
	$scope.filteredItems = $scope.list.length; //Initially for no filter
	$scope.totalItems = $scope.list.length;

	$scope.setPage = function(pageNo) {
		$scope.currentPage = pageNo;
	};

	$scope.filter = function() {
		$timeout(function() {
			$scope.filteredItems = $scope.filtered.length;
		}, 10);
	};

	$scope.sort_by = function(predicate) {
		$scope.predicate = predicate;
		$scope.reverse = !$scope.reverse;
	};

	$scope.confirmarRemover = function(id){
		ngDialog.openConfirm({
			template:' <p>Deseja remover este contato?</p> '+
		'<div class="ngdialog-buttons">'+
		   '<button type="button" class="ngdialog-button ngdialog-button-secondary" ng-click="closeThisDialog(0)">Não</button>'+
			'<button type="button" class="ngdialog-button ngdialog-button-primary" ng-click="confirm(1)">Sim</button>'+
		'</div>',
			plain: true
		}).then(
			function(value) {
				contatosServices.removerContatoPorId(id);
			},
			function(value) {
				//Cancel or do nothing
			}
		);
	}

});

appContatos.directive('ngConfirmClick', [
	function(){
		return {
			link: function (scope, element, attr) {
				var msg = attr.ngConfirmClick || "Deseja realmente remover este contato?";
				var clickAction = attr.confirmedClick;
				element.bind('click',function (event) {
					if ( window.confirm(msg) ) {
						scope.$eval(clickAction)
					}
				});
			}
		};
	}])


appContatos.controller('novoContatoController', function ($scope, $http, $timeout, $location, $state, contatosServices, ngDialog) {

	$scope.contato = {};
	$scope.salvarContato = function(contato){
		contatosServices.inserirContato(contato);
	};

});

appContatos.controller('editContatoController', function ($scope, $http, $timeout, $location, $state, contatosServices, dados){

	$scope.contato = dados.data;
	$scope.salvarContato = function(contato){
		contatosServices.alterarContato(contato);
	};
});


appContatos.config(['$stateProvider','$urlRouterProvider','$authProvider',
               	function($stateProvider, $urlRouterProvider, $authProvider) {
               	    $stateProvider
		               	 .state('contatos', {
		         	    	abstract: true,
		         	    	url: '/contatos',
		         	    	templateUrl: 'contatos/index.html',
		         	    	resolve: {
		         				authenticated: ['$location', '$auth', function($location, $auth) {
		         		            if (!$auth.isAuthenticated()) {
		         		            	return $location.path('/login');
		         		            }
		         		        }]
		         		          
		         	        }
		         	    })
		               	 .state('contatos.novo', {
		         	    	url: '/novo',
		         	    	templateUrl: 'contatos/novo.html',
		         	        controller: 'novoContatoController'
		         	    })
						.state('contatos.lista', {
							url: '/lista',
							templateUrl: 'contatos/lista.html',
							controller: 'listaContatoController',
							resolve: {
								listaContatos: function(contatosServices){
									return contatosServices.listarContatos();
								}
							}
						})
						.state('contatos.edit', {
							url: '/edit/:contatoId',
							templateUrl: 'contatos/edit.html',
							controller: 'editContatoController',
							resolve: {
								dados: function(contatosServices, $stateParams){
									return contatosServices.obterContatoPorId($stateParams.contatoId);
								}
							}
						})
						.state('contatos.home', {
							url: '/home',
							templateUrl: 'home/home.html',
							controller: 'null'

						});
               	 $urlRouterProvider.otherwise('/lista');
				}
]);
               	    
               	    
               	    