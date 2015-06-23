// Declare app level module which depends on views, and components
var myApp = angular.module('myApp', [
    'ui.router',
    'restangular',
    'angular-google-gapi',
    'myApp.home',
    'myApp.news',
    'myApp.about',
    'myApp.teacher',
    'myApp.student'
]);
myApp.service('sessionService', ['$window', '$rootScope', SessionService]);
myApp.service('PersonService', ['Restangular', PersonService]);
myApp.service('AuthService', ['PersonService', AuthService]);
myApp.factory('sessionInjector', ['$rootScope', 'sessionService', SessionInjector]);

myApp.config(function(RestangularProvider) {
    //Изменяем базовый Url для REST
    RestangularProvider.setBaseUrl('http://localhost:8080/cms-core-1.0/');
});

myApp.config(['$stateProvider', '$urlRouterProvider', '$httpProvider',
    function ($stateProvider, $urlRouterProvider, $httpProvider) {
        $urlRouterProvider.otherwise('/home');
        $httpProvider.interceptors.push('sessionInjector');
    }]);

myApp.run(['GAuth', 'GApi', 'GData', '$state', '$rootScope', '$window', '$http','AuthService',
    function (GAuth, GApi, GData, $state, $rootScope, $window, $http,AuthService) {

        var CLIENT = '895405022160-pi238d0pi57fsmsov8khtpr4415hj5j5.apps.googleusercontent.com';
        var BASE;
        if(window.location.hostname == 'localhost') {
            BASE = '//localhost:8080/_ah/api';
        } else {
            BASE = 'https://cloud-endpoints-gae.appspot.com/_ah/api';
        }
        GApi.load('AIzaSyDEVCJp5Hz_fSrHYeS24EcMM3FQV0GF8Do', 'v1', BASE);
        GAuth.setClient(CLIENT);
        GAuth.setScope('https://www.googleapis.com/auth/userinfo.email');

        $rootScope.doLogin = function () {
            GAuth.login().then(function () {
                //FIXME:  (Andrey) Добавил POST запрос на сервер для получения
                //FIXME: Раскомментировать для авторизации
                AuthService.goAuth(GData.getUser()).then(function(data) {
                    $window.localStorage['id']=data.id;
                    $window.localStorage['role']=data.personRole.toLowerCase();
                    console.log("Person in role: "+$window.localStorage['role']);
                });
                //Заглушка для определения роли
                //$window.localStorage['role'] = "teacher";
                //$window.localStorage['id'] = 1;

                console.log('doLogin');
            }, function () {
                $state.go("home");
                console.log('login fail');
            });
        };

        $rootScope.doLogOut = function () {
            $window.localStorage['role'] = undefined;
            $window.localStorage['id'] = undefined;
            GAuth.logout().then(function () {
                $state.go("home");
            });
        };
        $rootScope.isLogin = function () {
            return GData.isLogin();
        };
        $rootScope.getRole = function() {
            return $window.localStorage['role'];
        };
        // обработчик оповещения о попытке несанкционированного доступа
        $rootScope.$on('app.unauthorized', function () {
            $state.go("home");
            console.log('attempt to get secure data');
        });
    }]);


