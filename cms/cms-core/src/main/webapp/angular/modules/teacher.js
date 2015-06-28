angular.module('myApp.teacher', ['ui.router'])
    .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('teacher', {
                url: '/Course/:courseId',
                views: {
                    "": {
                        templateUrl: 'angular/views/teacherMenu.html',
                        controller: 'HomeCtrl'
                    },
                    "content@teacher": {
                        templateUrl: 'angular/views/teacherIndex.html'
                    }
                },
                resolve: {
                    //Добавляем Id курса для того чтобы можно было получить его из потомков
                    courseId: ['$stateParams', function ($stateParams) {
                        return $stateParams.courseId;
                    }],
                    //Проверка доступа используется везде где нужны права учителя
                    teacherAccess: teacherAccess
                }

            })
            .state('teacher.addTheme', {
                url: '/addTheme',
                views: {
                    "content@teacher": {
                        templateUrl: 'angular/views/addTheme.html',
                        controller: 'AddLectureCtrl'
                    },
                    "uploadFile@teacher.addTheme": {
                        templateUrl: 'angular/views/addFile.html',
                        controller: "FileUploadCtrl"
                    }
                },
                resolve: {teacherAccess: teacherAccess}
            })
            .state('teacher.addCourse', {
                url: '/addCourse',
                views: {
                    "content@teacher": {
                        templateUrl: 'angular/views/addCourse.html',
                        controller: "AddCourseCtrl"
                    }
                },
                resolve: {teacherAccess: teacherAccess}
            })
    }])
    .service('CourseContentService', CourseContentService)
    .controller("AddLectureCtrl", AddLectureCtrl)
    .controller("AddCourseCtrl", AddCourseCtrl)
    .factory('UploadManager', ["$rootScope", UploadManager])
    .controller('FileUploadCtrl', ['$scope', '$rootScope', 'UploadManager', FileUploadCtrl])
    .directive('upload', ['UploadManager', uploadDirective])
