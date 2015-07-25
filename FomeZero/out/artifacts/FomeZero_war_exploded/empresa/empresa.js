var appEmpresa = angular.module('app.empresa', ['satellizer','ngResource', 'ui.router','ui.validate','ngDialog', 'ngFileUpload']);

appEmpresa.factory('empresasServices', ['$http','$location','$state','ngDialog', function($http,$location,$state,ngDialog) {

    var serviceBase = 'services/secure/empresa/';
    var serviceBaseCargos = 'services/secure/empresa/cargos/';

    var obj = {};

    obj.cadastrarEmpresa = function(empresa){

            $http.post(serviceBase, empresa)
                .success(function(data, status, headers, config) {
                    ngDialog.open({
                        template: '<span class="mif-checkmark on-right on-left" style="background: white; color: greenyellow; padding: 10px; border-radius: 50%"></span>  Empresa adicionada com sucesso.',
                        plain: true
                    });
                    $state.go('empresa.home', null, { reload: true });
                }).error(function(data, status, headers, config) {
                    console.log("Erro:"+data);
                    ngDialog.open({
                        template: '<span class="mif-cancel on-right on-left" style="background: white; color: red; padding: 10px; border-radius: 50%"></span>  Não foi possível adicionar a empresa.',
                        plain: true
                    });
                });

    };


    obj.listarCargos = function () {
        return $http.get(serviceBaseCargos)
            .success(function(data, status, headers, config) {

            }).error(function(data, status, headers, config) {
                console.log("Erro:"+data);
            });
    };

    return obj;

}]);

appEmpresa.controller('cadastroEmpresaController', function ($scope, Upload, $http, $timeout, $location, $state, empresasServices, listaCargos, ngDialog) {

    $scope.cargos = listaCargos.data;
    $scope.salvarEmpresa = function(empresa){
        empresasServices.cadastrarEmpresa(empresa);
    };


});

appEmpresa.directive('numbersOnly', function(){
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


appEmpresa.config(['$stateProvider','$urlRouterProvider','$authProvider',
    function($stateProvider, $urlRouterProvider, $authProvider) {
        $stateProvider
            .state('empresa',{
                abstract: true,
                url: '/empresa',
                templateUrl: 'empresa/index.html',
                resolve: {
                    authenticated: ['$location', '$auth', function($location, $auth) {
                        if (!$auth.isAuthenticated()) {
                            return $location.path('/login');
                        }
                    }]

                }
            })
            .state('empresa.cadastro', {
                url: '/cadastro',
                templateUrl: 'empresa/empresa.html',
                controller: 'cadastroEmpresaController',
                resolve: {
                    listaCargos: function(empresasServices){
                        return empresasServices.listarCargos();
                    }
                }
            })
            .state('empresa.home', {
                url: '/home',
                templateUrl: 'home/home.html',
                controller: 'null'

            });

        $urlRouterProvider.otherwise('/home');
    }

]);