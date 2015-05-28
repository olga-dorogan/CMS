angular.module('myApp.news', ['ui.router'])

    .config(['$stateProvider', '$urlRouterProvider', function($stateProvider) {
        $stateProvider.state('news', {
            url:'/news',
            views:{
                "":{
                    templateUrl: 'angular/views/home.html'
                },
                "content@news":{
                    templateUrl: 'angular/views/news.html',
                    controller: 'NewsCtrl'
                }
            }

        })
    }])
    .service('NewsService',NewsService);


