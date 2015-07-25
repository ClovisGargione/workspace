var appEmail = angular.module('app.email', ['satellizer','ngResource', 'ui.router','ui.validate','ngDialog', 'ui.bootstrap']);

appEmail.factory("emailServices", ['$http','$location','$state','ngDialog', function($http, $location, $state, ngDialog){
    var serviceBase = 'services/secure/email/';

    var obj = {};

    obj.enviarEmail = function(email){
        $http.post(serviceBase, email)
            .success(function(data, status, headers, config){
                ngDialog.open({
                    template: '<span class="mif-checkmark on-right on-left" style="background: white; color: greenyellow; padding: 10px; border-radius: 50%"></span> E-mail enviado com sucesso.',
                    plain: true
                });
                $state.go('email.lista', null, { reload: true });
            }).error(function(data, status, headers, config){
                ngDialog.open({
                    template: '<span class="mif-cancel on-right on-left" style="background: white; color: red; padding: 10px; border-radius: 50%"></span>  Não foi possível enviar o e-mail.',
                    plain: true,
                    controller: ['$scope', function($scope) {
                        $scope.erro = data.mensagem;
                    }]
                });
            });
    };

    obj.removerEmailPorId = function(id){
        $http.delete(serviceBase+id)
            .success(function(data, status, headers, config) {
                ngDialog.open({
                    template: '<span class="mif-checkmark on-right on-left" style="background: white; color: greenyellow; padding: 10px; border-radius: 50%"></span>  E-mail removido com sucesso.',
                    plain: true
                });
                $state.go('email.lista', null, { reload: true });
            }).error(function(data, status, headers, config) {
                console.log("Erro:"+data);
                ngDialog.open({
                    template: '<span class="mif-cancel on-right on-left" style="background: white; color: red; padding: 10px; border-radius: 50%"></span>  Não foi possível remover o e-mail.',
                    plain: true,
                });
            });
    };

    obj.listarEmails = function(){
        return $http.get(serviceBase)
            .success(function(data, status, headers, config) {

            }).error(function(data, status, headers, config) {
                console.log("Erro:"+data);
                ngDialog.open({
                    template: '<span class="mif-cancel on-right on-left" style="background: white; color: red; padding: 10px; border-radius: 50%"></span>  Não foi possível carregar a lista de e-mails.',
                    plain: true,
                });
            });
    };

    obj.listarContatos = function(){
        return $http.get('services/secure/contatos/')
            .success(function(data, status, headers, config) {

            }).error(function(data, status, headers, config) {
                console.log("Erro:"+data);
            });
    };

    return obj;

}]);

appEmail.controller('enviaEmailController', function($scope, $http, $timeout, $location, $state, emailServices, listaContatos){

    $scope.email = {};

    $scope.enviar = function(email){
        emailServices.enviarEmail(email);
    };

    $scope.contatos = listaContatos.data;
});


appEmail.config(['$stateProvider','$urlRouterProvider','$authProvider',
    function($stateProvider, $urlRouterProvider){
        $stateProvider
            .state('email',{
                abstract: true,
                url: '/email',
                templateUrl: 'email/index.html',
                resolve: {
                    authenticated: ['$location', '$auth', function($location, $auth) {
                        if (!$auth.isAuthenticated()) {
                            return $location.path('/login');
                        }
                    }]
                }
            })
            .state('email.envia',{
                url: '/envia',
                templateUrl: 'email/envia.html',
                controller: 'enviaEmailController',
                resolve: {
                    listaContatos: function(emailServices){
                        return emailServices.listarContatos();
                    }
                }
            })
            .state('email.lista',{
                url: '/lista',
                templateUrl: 'email/lista.html',
                controller: 'listaEmailController',
                resolve: {
                    listaEmails: function(emailServices){
                        return emailServices.listarEmails();
                    }
                }
            });
        $urlRouterProvider.otherwise('/lista');
    }
]);