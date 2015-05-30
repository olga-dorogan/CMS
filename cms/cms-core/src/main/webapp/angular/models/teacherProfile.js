angular.module('myApp.teacherProfile', ['ui.router'])
    .config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('teacherProfile', {
                url:'/profile',
                //FIXME Сделать страницу профиля для учителя
                views: {
                    "":{
                        templateUrl: 'angular/views/home.html'
                    },
                    "content@teacherProfile": {
                        template: '<h1>TEACHER</h1>'
                    }
                }

            })
    }]);