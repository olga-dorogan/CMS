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
                    },
                    oldCourses: function ($rootScope, courseService) {
                        if (!$rootScope.isTeacher()) {
                            return [];
                        }
                        var promise = courseService.getOldCourses();
                        promise = promise.then(function (oldCourses) {
                            if (oldCourses.responseStatus != 200) {
                                return promise;
                            }
                            return oldCourses;
                        });
                        return promise;
                    }
                }
            })
            .state('person.addCourse', {
                url: '/addCourse',
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
                        var promise = personService.getTeachers();
                        promise = promise.then(function (teachers) {
                            return teachers;
                        });
                        return promise;
                    },
                    coursePrototypes: function (mode, courseService) {
                        var promise = courseService.getCourses();
                        promise = promise.then(function (courses) {
                            courses.push({'id': -1, 'name': 'Новый курс'});
                            return courses;
                        });
                        return promise;
                    },
                    mode: function () {
                        return 'add';
                    },
                    course: function () {
                        return {};
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
                        controller: function ($scope, course) {
                            $scope.courseName = course.name;
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
                    course: function ($stateParams, courseService) {
                        var promise = courseService.getCourse($stateParams.courseId);
                        promise = promise.then(function (course) {
                            if (course.responseStatus != 200) {
                                return null;
                            }
                            return courseService.normalizeCourse(course);
                        });
                        return promise;
                    }
                }
            })
            .state('person.course.edit', {
                url: '/edit',
                templateUrl: 'angular/views/person-course/teacher/addCourse.html',
                controller: 'AddOrEditCourseCtrl',
                resolve: {
                    courseService: 'CourseService',
                    allTeachers: function () {
                        return [];
                    },
                    coursePrototypes: function () {
                        return [];
                    },
                    mode: function () {
                        return 'edit';
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
            .state('person.course.newsContent', {
                url: '/news-content',
                templateUrl: 'angular/views/person-course/newsContent.html',
                controller: 'NewsCtrl',
                resolve: {
                    newsService: 'NewsService',
                    news: function ($stateParams, newsService) {
                        var promise = newsService.getNewsFromCourse($stateParams.courseId);
                        promise = promise.then(function (news) {
                            return news;
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

            .state('person.course.addNews', {
                url: '/addNews',
                templateUrl: 'angular/views/person-course/teacher/addNewNews.html',
                controller: 'AddNewsCtrl'
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
    .service('NewsService', NewsService)
    .service('CourseService', CourseService)
    .controller('AddNewsCtrl', AddNewsCtrl)
    .service('CourseContentService', CourseContentService)
    .controller('NewsCtrl', NewsCtrl)
    .controller('PersonCtrl', PersonCtrl)
    .controller('AddOrEditCourseCtrl', AddOrEditCourseCtrl)
    .controller('DatepickerCtrl', DatepickerCtrl)
    .controller('CourseContentCtrl', CourseContentCtrl)
    .controller("AddLectureCtrl", AddLectureCtrl)
    .controller("LectureContentCtrl", LectureContentCtrl)
    .controller("SettingCtrl", SettingCtrl);



