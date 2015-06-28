angular.module('myApp.about', ['ui.router'])
    .config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('about', {
                url:'/about',
                views: {
                    "":{
                        templateUrl: 'angular/views/home.html'
                    },
                    "content@about": {
                        templateUrl: 'angular/views/about.html'
                    }
                }

            })

    }]);




