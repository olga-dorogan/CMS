angular.module('myApp.home', ['ui.router'])
    .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider) {
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
                },
                resolve: {
                    courseService: 'CourseService',
                    courses: function (courseService) {
                        return courseService.getCourses().then(function (result) {
                            return result;
                        });
                    }
                }
            });
    }])
    .service('CourseService', CourseService)
    .controller('HomeCtrl', HomeCtrl);



