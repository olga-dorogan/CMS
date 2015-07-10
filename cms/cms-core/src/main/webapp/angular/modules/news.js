angular.module('myApp.news', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider

            .state('noNews', {
                parent: 'main',
                url:'/nonews',
                templateUrl: 'angular/views/noNews.html',
                controller: 'NoNewsCtrl'
            })
            .state('news', {
                parent: 'main',
                url: '/news',
                views: {
                    "body@main": {
                        templateUrl: 'angular/views/news.html',
                        controller: 'NewsCtrl'
                    }
                },
                resolve: {

                    newsService: 'NewsService',
                    news: function ($rootScope, newsService) {
                        var promise;
                        $rootScope.redirectToDraftPage= function () {
                           $location.path('/nonews');
                        };


                        if ($rootScope.getUserId() == null||$rootScope.getUserId() == undefined) {
                            promise = newsService.getNews();

                        } else {
                           promise = newsService.getPersonNews($rootScope.getUserId());

                        }
                        return promise.then(function (news) {
                            return news;
                        });
                    }
                }
            })
    }])
    .service('NewsService', NewsService)
    .controller('NewsCtrl', NewsCtrl);





