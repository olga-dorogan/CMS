function NewsCtrl($scope,NewsService){
    $scope.news = NewsService.getNews();
}