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
                }
            })
            .state('person.course', {
                url: '/course/:courseId',
                views: {
                    "": {
                        templateUrl: 'angular/views/home.html'
                    },
                    "content@person": {
                        templateUrl: 'angular/views/courseContent.html',
                        controller: "CourseContentCtrl"
                    }
                }
            })
            .state('person.addLecture', {
                url: '/course/:courseId/addLecture',
                views: {
                    "": {
                        templateUrl: 'angular/views/home.html'
                    },
                    "content@person": {
                        templateUrl: 'angular/views/addLecture.html',
                        controller: "AddLectureCtrl"
                    }
                }
            })
            .state('person.settings', {
                url: '/settings',
                views: {
                    "": {
                        templateUrl: 'angular/views/home.html'
                    },
                    "content@person": {
                        templateUrl: 'angular/views/settings.html',
                        controller: "SettingCtrl"
                    }
                }
            })
    }])
    .service('CourseService', CourseService)
    .service('CourseContentService', CourseContentService)
    .controller('PersonCtrl', PersonCtrl)
    .controller('AddCourseCtrl', AddCourseCtrl)
    .controller('DatepickerCtrl', DatepickerCtrl)
    .controller('CourseContentCtrl', CourseContentCtrl)
    .controller("AddLectureCtrl", AddLectureCtrl)
    .controller("SettingCtrl", SettingCtrl);



