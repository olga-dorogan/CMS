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

myApp.run(['GAuth', 'GApi', 'GData', '$state', '$rootScope', '$window', '$http',
    function (GAuth, GApi, GData, $state, $rootScope, $window, $http) {

        var CLIENT = '895405022160-pi238d0pi57fsmsov8khtpr4415hj5j5.apps.googleusercontent.com';
        var BASE;
        if(window.location.hostname == 'localhost') {
            BASE = '//localhost:8080/_ah/api';
        } else {
            BASE = 'https://cloud-endpoints-gae.appspot.com/_ah/api';
        }
        GApi.load('AIzaSyDEVCJp5Hz_fSrHYeS24EcMM3FQV0GF8Do', 'v1', BASE);
        GAuth.setClient(CLIENT);
        GAuth.setScope('https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/calendar.readonly');

        // FIXME: (olga) возможно, этот код не нужен, т.к. при первом входе мы запоминаем пользователя и дальше не зависим от google
        //GAuth.checkAuth().then(
        //    function () {
        //        $rootScope.role="teacher";
        //        Если пользовательно не новый логиним его и отправляем куда нужно
            //},
            //function () {
            //    Если пользовательно не залогинен отправляем его на главную страницу
                //$state.go('home');
            //}
        //);

        //Заглушка для определения роли
        $rootScope.role = "teacher";

        $rootScope.doLogin = function () {
            GAuth.login().then(function () {
                // FIXME: (olga) add http PUT request to get user info: role and id
                $window.localStorage['role'] = "teacher";
                $window.localStorage['id'] = 1;
                // тест для проверки установились ли заголовки
                //$http.get("http://localhost:8080/cms-core-1.0/resources");

                console.log('doLogin');
            }, function () {
                $state.go("home");
                console.log('login fail');
            });
        };
        $rootScope.doLogOut = function () {
            $rootScope.role="";
            GAuth.logout().then(function () {
                $state.go("home");
            });
        };
        $rootScope.isLogin = function () {
            return GData.isLogin();
        };
        // обработчик оповещения о попытке несанкционированного доступа
        $rootScope.$on('app.unauthorized', function () {
            $state.go("home");
            console.log('attempt to get secure data');
        });
    }]);