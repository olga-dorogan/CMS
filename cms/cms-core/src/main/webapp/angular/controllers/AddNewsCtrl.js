function AddNewsCtrl($scope,$stateParams,NewsService, news) {

    this.createNews = function () {
        $scope.news.date = new Date();
        $scope.news.courseId = $stateParams.courseId;

        return NewsService.createNews($scope.news);
    };


    $scope.news = news;
}
