
var app = angular.module('app',[ 'satellizer','ngRoute', 'ngResource', 'ui.router','ui.validate', 'app.login', 'app.logout', 'app.navbar', 'app.usuario', 'app.home', 'app.empresa', 'angular-loading-bar', 'ui.bootstrap', 'ngFileUpload'])

    .config(function($authProvider) {
        $authProvider.loginUrl = 'services/auth/login';
        $authProvider.signupUrl = 'services/auth/signup';
        $authProvider.loginRedirect = '/home';
        $authProvider.signupRedirect = '/home';
        $authProvider.logoutRedirect = '/login';
    });

app.config(['$stateProvider','$urlRouterProvider','$authProvider',
    function($stateProvider, $urlRouterProvider, $authProvider) {
        $stateProvider
            .state('home', {
                url: '/home',
                templateUrl: 'home/home.html',
                controller: 'homeController',
                resolve: {
                    authenticated: ['$location', '$auth', function($location, $auth) {
                        if (!$auth.isAuthenticated()) {
                            return $location.path('/login');
                        }
                    }]

                }

            })
            .state('login', {
                url: '/login',
                templateUrl: 'login/login.html',
                controller: 'loginController'
            })
            .state('logout', {
                url: '/logout',
                template: null,
                controller: 'logoutController'
            })
            .state('erro',{
                url:'/erro',
                templateUrl: 'error.html'
            })
            .state('registro',{
                url: '/registro',
                templateUrl: 'usuario/usuario.html',
                controller: 'registroController'
            });

        $urlRouterProvider.otherwise('/home');
    }
]);


