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
                url: '/addOrEditCourse',
                params: {mode: null, editedCourse: null},
                views: {
                    "body@main": {
                        templateUrl: 'angular/views/person-course/teacher/addCourse.html',
                        controller: "AddOrEditCourseCtrl"
                    }
                },
                resolve: {
                    personService: 'PersonService',
                    courseService: 'CourseService',
                    allTeachers: function (mode, personService) {
                        if (mode != 'add') {
                            return [];
                        }
                        var promise = personService.getTeachers();
                        promise = promise.then(function (teachers) {
                            return teachers;
                        });
                        return promise;
                    },
                    coursePrototypes: function (mode, courseService) {
                        if (mode != 'add') {
                            return [];
                        }
                        var promise = courseService.getCourses();
                        promise = promise.then(function (courses) {
                            courses.push({'id': -1, 'name': 'Отсутствует'});
                            return courses;
                        });
                        return promise;
                    },
                    mode: function ($stateParams) {
                        return $stateParams.mode;
                    },
                    editedCourse: function ($stateParams) {
                        return $stateParams.editedCourse;
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
                            if (courses.responseStatus / 100 != 2) {
                                return promise;
                            }
                            return courses;
                        });
                        return promise;
                    }
                }
            })
            .state('person.course', {
                url: '/course/:courseId',
                views: {
                    "body@main": {
                        templateUrl: 'angular/views/person-course/main.html'
                    },
                    "top@person.course": {
                        templateUrl: 'angular/views/person-course/top.html',
                        controller: function ($scope, courseName) {
                            $scope.courseName = courseName;
                        }
                    },
                    "menubar@person.course": {
                        templateUrl: 'angular/views/person-course/menu.html'
                    },
                    "content@person.course": {
                        templateUrl: 'angular/views/person-course/content.html'
                    }
                },
                resolve: {
                    courseService: 'CourseService',
                    courseName: function ($stateParams, courseService) {
                        var promise = courseService.getCourse($stateParams.courseId);
                        promise = promise.then(function (course) {
                            if (course.responseStatus != 200) {
                                return null;
                            }
                            return course.name;
                        });
                        return promise;
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
            .state('person.course.notification', {
                url: '/notification',
                templateUrl: ''
            })
            .state('person.course.addLecture', {
                url: '/addLecture',
                templateUrl: 'angular/views/person-course/teacher/addLecture.html',
                controller: 'AddLectureCtrl',
                resolve: {
                    courseContentService: 'CourseContentService',
                    lecturesCnt: function ($stateParams, courseContentService) {
                        var promise = courseContentService.getLectures($stateParams.courseId);
                        promise = promise.then(function (lectures) {
                            if (lectures.responseStatus != 200) {
                                return null;
                            }
                            return lectures.length;
                        });
                        return promise;
                    }
                }
            })
            .state('person.course.lecture', {
                url: '/lecture/:lectureId',
                templateUrl: 'angular/views/person-course/lectureContent.html',
                controller: 'LectureContentCtrl',
                resolve: {
                    courseContentService: 'CourseContentService',
                    lecture: function ($stateParams, courseContentService) {
                        var promise = courseContentService.getLecture($stateParams.courseId, $stateParams.lectureId);
                        promise = promise.then(function (lecture) {
                            if (lecture.responseStatus != 200) {
                                return null;
                            }
                            return lecture;
                        });
                        return promise;
                    },
                    lecturesCnt: function ($stateParams, courseContentService) {
                        var promise = courseContentService.getLectures($stateParams.courseId);
                        promise = promise.then(function (lectures) {
                            if (lectures.responseStatus != 200) {
                                return null;
                            }
                            return lectures.length;
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
                    "body@main": {
                        templateUrl: 'angular/views/settings/body.html'
                    },
                    "setting-top@person.settings": {
                        templateUrl: 'angular/views/settings/setting-top.html'
                    },
                    "setting-menubar@person.settings": {
                        templateUrl: 'angular/views/settings/menubar.html'
                    },
                    "setting-content@person.settings": {
                        templateUrl: 'angular/views/settings/settings.html'//FIXME CHANGE CONTENT
                    }
                }
            })
            .state('person.subcribe.modal', {
                url: '/subscribe',
                templateUrl: 'angular/views/addition.html'
            })
    }])
    .service('PersonService', PersonService)
    .service('CourseService', CourseService)
    .service('CourseContentService', CourseContentService)
    .controller('PersonCtrl', PersonCtrl)
    .controller('AddOrEditCourseCtrl', AddOrEditCourseCtrl)
    .controller("EditCoursesCtrl", EditCoursesCtrl)
    .controller('DatepickerCtrl', DatepickerCtrl)
    .controller('CourseContentCtrl', CourseContentCtrl)
    .controller("AddLectureCtrl", AddLectureCtrl)
    .controller("LectureContentCtrl", LectureContentCtrl)
    .controller("SettingCtrl", SettingCtrl);



