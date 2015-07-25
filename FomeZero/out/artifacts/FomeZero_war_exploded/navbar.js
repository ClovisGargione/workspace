var appNavbar = angular.module('app.navbar', ['ui.router','ui.bootstrap','ui.mask','ui.validate']);

appNavbar.factory("navbarService", ['$http','$location','$state', function($http,$location,$state,ngDialog) {
    var serviceBase = 'services/navbar/';

    var obj = {};

    obj.obterModulos = function(){
        return $http.get(serviceBase)
            .success(function(data, status, headers, config) {

            }).error(function(data, status, headers, config) {
                console.log("Erro:"+data);
            });
    }

    return obj;

}]);

// CONTROLLERS
appNavbar.controller('navbarController', function ($scope, $auth, navbarService, $http) {
    var payload = $auth.getPayload();
    $scope.usuarioLogado = payload.login;
    $scope.usuarioId = payload.sub;
    $http.get('services/secure/navbar/').success(function(data){
        $scope.modulos = data;

    });

    $http.get('services/secure/navbar/link').success(function(data){
        $scope.link = data;

    });

});


