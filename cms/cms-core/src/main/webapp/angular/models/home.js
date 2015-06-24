angular.module('myApp.home', ['ui.router'])
    .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('home', {
                url: '/home',
                views: {
                    "": {
                        templateUrl: 'angular/views/home.html'
                    },
                    "content@home": {
                        templateUrl: 'angular/views/course.html',
                        controller: 'HomeCtrl'
                    }
                }

            })
    }])
    .service('CourseService', CourseService)
    .controller('HomeCtrl', HomeCtrl);



