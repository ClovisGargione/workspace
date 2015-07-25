var appUsuario = angular.module('app.usuario', ['satellizer','ngResource', 'ui.router','ui.validate','ngDialog', 'ngFileUpload']);

appUsuario.factory('usuariosServices', ['$http','$location','$state','ngDialog', function($http,$location,$state,ngDialog) {

    var serviceBase = 'services/secure/usuario/';
    var obj = {};

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
            login: $scope.login,
            senha: $scope.senha
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
    $scope.salvarUsuario = function(usuario){
        usuariosServices.alterarUsuario(usuario);
    };

    $http.get('services/secure/empresa/cargos/').success(function(data){
        $scope.cargos = data;

    });

});

appUsuario.directive('numbersOnly', function(){
    return {
        require: 'ngModel',
        link: function(scope, element, attrs, modelCtrl) {
            modelCtrl.$parsers.push(function (inputValue) {
                // this next if is necessary for when using ng-required on your input.
                // In such cases, when a letter is typed first, this parser will be called
                // again, and the 2nd time, the value will be undefined
                if (inputValue == undefined) return ''
                var transformedInput = inputValue.replace(/[^0-9]/g, '');
                if (transformedInput!=inputValue) {
                    modelCtrl.$setViewValue(transformedInput);
                    modelCtrl.$render();
                }

                return transformedInput;
            });
        }
    };
});

appUsuario.directive('isNumber', function () {
    return {
        require: 'ngModel',
        link: function (scope) {
            scope.$watch('usuario.endereco.numero', function(newValue,oldValue) {
                var arr = String(newValue).split("");
                if (arr.length === 0) return;
                if (arr.length === 1 && (arr[0] == '-' || arr[0] === '.' )) return;
                if (arr.length === 2 && newValue === '-.') return;
                if (isNaN(newValue)) {
                    scope.usuario.endereco.numero = oldValue;
                }
            });
        }
    };
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
            .state('usuarios.cliente', {
                url: '/edit/:usuarioId',
                templateUrl: 'usuario/edit.html',
                controller: 'editUsuarioController',
                resolve: {
                    dados: function(usuariosServices, $stateParams){
                        return usuariosServices.obterUsuarioPorId($stateParams.usuarioId);
                    }
                }
            })
            .state('usuarios.empresa',{
                url: '/empresa/edit/:usuarioId',
                templateUrl: 'usuario/funcionarioedit.html',
                controller: 'editUsuarioController',
                resolve: {
                    dados: function(usuariosServices, $stateParams){
                        return usuariosServices.obterUsuarioPorId($stateParams.usuarioId);
                    }
                }

            })
            .state('usuarios.adm',{
                url: '/adm/edit/:usuarioId',
                templateUrl: 'usuario/admedit.html',
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



