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
                },
                resolve: {
                    courseService: 'CourseService',
                    personService: 'PersonService',
                    personalizedCourses: function ($rootScope, courseService, personService) {
                        var promise = courseService.getCourses();
                        promise = promise.then(function (courses) {
                            promise = personService.getCoursesForPerson($rootScope.getUserId());
                            promise = promise.then(function (personCourses) {
                                var personalizedCourses = courses;
                                for (var i = 0; i < personalizedCourses.length; i++) {
                                    personalizedCourses[i].isPersonEnrolled = false;
                                }
                                for (var i = 0; i < personCourses.length; i++) {
                                    for (var j = 0; j < personalizedCourses.length; j++) {
                                        if (personalizedCourses[i].id == personCourses[i].id) {
                                            personalizedCourses[i].isPersonEnrolled = true;
                                            break;
                                        }
                                    }
                                }
                                ;
                                return personalizedCourses;
                            });
                            return promise;
                        });
                        return promise;
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
                        templateUrl: 'angular/views/person-course/teacher/addCourse.html',
                        controller: "AddCourseCtrl"
                    }
                },
                resolve: {
                    personService: 'PersonService',
                    allTeachers: function (personService) {
                        var promise = personService.getTeachers();
                        promise = promise.then(function (teachers) {
                            return teachers;
                        });
                        return promise;
                    }
                }
            })
            .state('person.course', {
                url: '/course/:courseId',
                params: {courseName: null},
                views: {
                    "@": {
                        templateUrl: 'angular/views/person-course/main.html'
                    },
                    "top@person.course": {
                        templateUrl: 'angular/views/person-course/top.html',
                        controller: function ($scope, $stateParams) {
                            $scope.courseName = $stateParams.courseName;
                        }
                    },
                    "menubar@person.course": {
                        templateUrl: 'angular/views/person-course/menu.html'
                    },
                    "content@person.course": {
                        templateUrl: 'angular/views/person-course/content.html',
                        controller: function ($state) {
                            $state.go('person.course.content');
                        }
                    }
                }
            })
            .state('person.course.content', {
                url: '/content',
                templateUrl: 'angular/views/person-course/courseContent.html',
                controller: 'CourseContentCtrl',
                resolve: {
                    courseContentService: 'CourseContentService',
                    lectures: function ($stateParams, courseContentService) {
                        var promise = courseContentService.getLectures($stateParams.courseId);
                        promise = promise.then(function (lectures) {
                            return lectures;
                        });
                        return promise;
                    }
                }
            })
            .state('person.course.progress', {
                url: '/progress',
                templateUrl: 'angular/views/person-course/progressContent.html'
            })
            .state('person.settings', {
                url: '/settings',
                views: {
                    "": {
                        templateUrl: 'angular/views/home.html'
                    },
                    "contetnt@person": {
                        templateUrl: 'angular/views/settings.html',
                        controller: "SettingCtrl"
                    }
                }
            })
    }])
    .service('PersonService', PersonService)
    .service('CourseService', CourseService)
    .service('CourseContentService', CourseContentService)
    .controller('PersonCtrl', PersonCtrl)
    .controller('AddCourseCtrl', AddCourseCtrl)
    .controller('DatepickerCtrl', DatepickerCtrl)
    .controller('CourseContentCtrl', CourseContentCtrl)
    .controller("AddLectureCtrl", AddLectureCtrl)
    .controller("SettingCtrl", SettingCtrl);



