// Declare app level module which depends on views, and components
var myApp = angular.module('myApp', [
    'ui.router',
    'myApp.home',
    'myApp.news',
    'myApp.about',
    'angular-google-gapi'
]);
myApp.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/home');
}]);
myApp.run(['GAuth','GApi','GData','$state','$rootScope',
    function(GAuth, GApi,GData, $state,$rootScope) {

        var CLIENT = '895405022160-pi238d0pi57fsmsov8khtpr4415hj5j5.apps.googleusercontent.com';
        var BASE = 'https://myGoogleAppEngine.appspot.com/_ah/api';

        GApi.load('AIzaSyDEVCJp5Hz_fSrHYeS24EcMM3FQV0GF8Do','v1',BASE);
        GAuth.setClient(CLIENT);
        GAuth.setScope('https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/calendar.readonly');

        GAuth.checkAuth().then(
            function () {
                //Если пользовательно не новый логиним его и отправляем куда нужно
            },
            function() {
                //Если пользовательно не залогинен отправляем его на главную страницу
                $state.go('home');
            }
        );

        $rootScope.doLogin=function(){
            GAuth.login().then(function(){
                console.log('doLogin');
            }, function() {
                $state.go("home");
                console.log('login fail');
            });
        };
        $rootScope.doLogOut=function(){
            GAuth.logout().then(function(){
                $state.go("home");
            });
        };
        $rootScope.isLogin=function(){
            return GData.isLogin();
        }
    }]);