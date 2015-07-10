function AddNewsCtrl($scope, $stateParams, NewsService) {
    $scope.news = {};

    $scope.createNews = function () {
        $scope.news.date = new Date();
        console.log("controller: create news");

        NewsService.createNews($scope.news, $stateParams.courseId);
    };
}
