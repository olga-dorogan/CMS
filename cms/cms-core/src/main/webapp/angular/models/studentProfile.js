angular.module('myApp.studentProfile', ['ui.router'])
    .config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('studentProfile', {
                url:'/profile',
                //FIXME Сделать страницу профиля для студента
                views: {
                    "":{
                        templateUrl: 'angular/views/home.html'
                    },
                    "content@studentProfile": {
                        template: '<h1>STUDENT</h1>'
                    }
                }

            })
    }]);