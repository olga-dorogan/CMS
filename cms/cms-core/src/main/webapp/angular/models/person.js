/**
 * Created by olga on 24.06.15.
 */
angular.module('myApp.person', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('person', {
                url: '/person',
                views: {
                    "": {
                        templateUrl: 'angular/views/home.html'
                    },
                    "content@person": {
                        templateUrl: 'angular/views/course.html',
                        controller: 'PersonCtrl'
                    }
                }

            })
            .state('person.addCourse', {
                url: '/addCourse',
                views: {
                    "": {
                        templateUrl: 'angular/views/home.html'
                    },
                    "content@person": {
                        templateUrl: 'angular/views/addCourse.html',
                        controller: "AddCourseCtrl"
                    }
                },
                resolve: {teacherAccess: teacherAccess}
            })
    }])
    .service('CourseService', CourseService)
    .controller('PersonCtrl', PersonCtrl)
    .controller('AddCourseCtrl', AddCourseCtrl)
    .controller('DatepickerCtrl', DatepickerCtrl);



