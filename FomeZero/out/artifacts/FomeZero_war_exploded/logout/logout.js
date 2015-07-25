var appLogout = angular.module('app.logout', ['satellizer']);


appLogout.controller('logoutController', function ($auth) {
    if (!$auth.isAuthenticated()) {
        return;
    }
    $auth.logout()
        .then(function() {
        });
});
