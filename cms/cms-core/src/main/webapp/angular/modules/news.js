angular.module('myApp.news', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('news', {
            parent: 'main',
            url: '/news',
            views: {
                "body@main": {
                    templateUrl: 'angular/views/news.html',
                    controller: 'NewsCtrl'
                }
            }
        })
    }])
    .service('NewsService', NewsService);


