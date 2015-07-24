// Declare app level module which depends on views, and components
var myApp = angular.module('myApp', [
    'ui.router',
    'restangular',
    'angular-google-gapi',
    'ui.bootstrap',
    'angularFileUpload',
    'angular-redactor',
    'myApp.main',
    'myApp.home',
    'myApp.person',
    'myApp.news',
    'myApp.about'

])

myApp.service('sessionService', ['$window', '$rootScope', SessionService]);
myApp.service('PersonService', ['Restangular', PersonService]);
myApp.service('AuthService', ['PersonService', AuthService]);
myApp.factory('sessionInjector', ['$rootScope', 'sessionService', SessionInjector]);

myApp.config(function (RestangularProvider) {
    //Изменяем базовый Url для REST
    RestangularProvider.setBaseUrl('http://localhost:8080/cms-core-1.0/');
});

myApp.config(['$stateProvider', '$urlRouterProvider', '$httpProvider',
    function ($stateProvider, $urlRouterProvider, $httpProvider) {
        $urlRouterProvider.otherwise('/home');
        $httpProvider.interceptors.push('sessionInjector');
    }]);

myApp.run(['GAuth', 'GApi', 'GData', '$state', '$rootScope', '$window', '$http', 'AuthService',
    function (GAuth, GApi, GData, $state, $rootScope, $window, $http, AuthService) {

        var CLIENT = '895405022160-pi238d0pi57fsmsov8khtpr4415hj5j5.apps.googleusercontent.com';
        GAuth.setClient(CLIENT);
        GAuth.setScope('https://www.googleapis.com/auth/userinfo.email');

        $rootScope.doLogin = function () {
            GAuth.login().then(function () {
                AuthService.goAuth(GData.getUser()).then(function (data) {
                    $window.localStorage['id'] = data.id;
                    $window.localStorage['role'] = data.personRole.toLowerCase();
                    $window.localStorage['name'] = data.name + " " + data.lastName;
                    $window.localStorage['token'] = $window.gapi.auth.getToken().access_token;
                    $window.localStorage['email'] = data.email.toLowerCase();
                    $state.go("person");
                });
                console.log('user is logined successfully');
            }, function () {
                $state.go("home");
                console.log('login fail');
            });
        };

        $rootScope.doLogOut = function () {
            $window.localStorage.clear();
            GAuth.logout().then(function () {
                $state.go("home");
            });
        };
        $rootScope.isLogin = function () {
            return !(($rootScope.getUserId() == undefined) || ($rootScope.getUserId() == null));
        };
        $rootScope.getEmail = function () {
            return $window.localStorage['email'];
        };
        $rootScope.getUserId = function () {
            return $window.localStorage['id'];
        };
        $rootScope.getUsername = function () {
            return $window.localStorage['name'];
        };
        $rootScope.getEmailHash = function () {
            var Hasher = Restangular.one("mailhash");
            return Hasher.post({
                "email": $window.localStorage['email']
            });
        };
        $rootScope.isTeacher = function () {
            return $window.localStorage['role'] == 'teacher';
        };
        // обработчик оповещения о попытке несанкционированного доступа
        $rootScope.$on('app.unauthorized', function () {
            $rootScope.doLogOut();
            $state.go("home");
            console.log('attempt to get secure data');
        });

        $rootScope.$on('$stateChangeStart', function (event, toState) {
            if ((toState.name == 'home') && $rootScope.isLogin()) {
                event.preventDefault();
                $state.go('person');
            }
        });
    }]);


