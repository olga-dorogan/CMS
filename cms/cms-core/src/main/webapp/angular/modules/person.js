/**
 * Created by olga on 24.06.15.
 */
angular.module('myApp.person', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('person', {
                parent: 'main',
                url: '/person',
                views: {
                    "body@main": {
                        templateUrl: 'angular/views/course.html',
                        controller: 'PersonCtrl'
                    }
                },
                resolve: {
                    courseService: 'CourseService',
                    personService: 'PersonService',
                    coursesGroups: function ($rootScope, courseService, personService) {
                        var promise = personService.getCoursesStatusesForPerson($rootScope.getUserId());
                        promise = promise.then(function (personCourses) {
                            if (personCourses.responseStatus != 200) {
                                return promise;
                            }
                            promise = courseService.getNewCourses();
                            promise = promise.then(function (newCourses) {
                                if (newCourses.responseStatus != 200) {
                                    return promise;
                                }
                                for (var i = 0; i < personCourses.length; i++) {
                                    for (var j = 0; j < newCourses.length; j++) {
                                        if (personCourses[i].id == newCourses[j].id) {
                                            newCourses.splice(j, 1);
                                            break;
                                        }
                                    }
                                }
                                return {
                                    'coursesEnrolled': personCourses,
                                    'coursesToSubscribe': newCourses
                                };
                            });
                            return promise;
                        });
                        return promise;
                    }
                }
            })
            .state('person.addOrEditCourse', {
                url: '/addCourse',
                views: {
                    "body@main": {
                        templateUrl: 'angular/views/person-course/teacher/addCourse.html',
                        controller: "AddCourseCtrl"
                    }
                },
                resolve: {
                    personService: 'PersonService',
                    courseService: 'CourseService',
                    allTeachers: function (personService) {
                        var promise = personService.getTeachers();
                        promise = promise.then(function (teachers) {
                            return teachers;
                        });
                        return promise;
                    },
                    coursePrototypes: function (courseService) {
                        var promise = courseService.getCourses();
                        promise = promise.then(function (courses) {
                            courses.push({'id': -1, 'name': 'Отсутствует'});
                            return courses;
                        });
                        return promise;
                    }
                }
            })
            .state('person.editCourses', {
                url: '/editCourses',
                views: {
                    "body@main": {
                        templateUrl: 'angular/views/person-course/teacher/editCourses.html',
                        controller: 'EditCoursesCtrl'
                    }
                },
                resolve: {
                    courseService: 'CourseService',
                    courses: function (courseService) {
                        var promise = courseService.getCourses();
                        promise = promise.then(function (courses) {
                            if(courses.responseStatus != 200) {
                                return promise;
                            }
                            return courses;
                        });
                        return promise;
                    }
                }
            })
            .state('person.subcribe', {
                url: "/person",
                views: {
                    "": {
                        templateUrl: 'angular/views/home.html'
                    },
                    "content@person": {
                        templateUrl: 'angular/views/course.html',
                        controller: 'PersonCtrl'//FIXME KAK PROHODIT VSE?
                    },
                    "avalaible@person": {
                        templateUrl: 'angular/views/settings',
                        controller: 'PersonCtrl'
                    }
                }
            })
            .state('person.course', {
                url: '/course/:courseId',
                params: {courseName: null},
                views: {
                    "body@main": {
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
            .state('person.course.addLecture', {
                url: '/addLecture',
                templateUrl: 'angular/views/person-course/teacher/addLecture.html',
                controller: 'AddLectureCtrl'
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
                    "content@person": {
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
    .controller("EditCoursesCtrl", EditCoursesCtrl)
    .controller('DatepickerCtrl', DatepickerCtrl)
    .controller('CourseContentCtrl', CourseContentCtrl)
    .controller("AddLectureCtrl", AddLectureCtrl)
    .controller("SettingCtrl", SettingCtrl);



