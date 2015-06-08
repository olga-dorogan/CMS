angular.module('myApp.teacher', ['ui.router'])
    .config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('teacher', {
                url:'/teacher',
                //FIXME Сделать страницу профиля для учителя
                views: {
                    "":{
                        templateUrl: 'angular/views/teacherMenu.html'
                    },
                    "content@teacher": {
                        templateUrl: 'angular/views/teacherIndex.html'
                    }
                },
                resolve: {teacherAccess:teacherAccess}

            })
            .state('teacher.addTheme', {
                url:'/addTheme',
                views: {
                    "":{
                        templateUrl: 'angular/views/teacherMenu.html'
                    },
                    "content@teacher": {
                        templateUrl: 'angular/views/addTheme.html',
                        controller: 'AddThemeCtrl'
                    }
                },
                resolve: {teacherAccess:teacherAccess}
            })
    }])
    .service('ThemeService',ThemeService)
    .controller("AddThemeCtrl",AddThemeCtrl);

