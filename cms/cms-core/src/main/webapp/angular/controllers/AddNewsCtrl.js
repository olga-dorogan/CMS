function AddNewsCtrl($scope,$stateParams,NewsService, news) {

    var createNews = function () {
        $scope.news.date = new Date();
        $scope.news.courseId = $stateParams.courseId;

        NewsService.createCourse($scope.course).then(
            function (createdCourse) {
                if (CourseService.isCourseSuccessfullyCreated(createdCourse)) {
                    $scope.messages = 'Курс "' + createdCourse.name + '" успешно создан.';
                    $scope.alertStatus = 'success';
                } else {
                    $scope.messages = 'Курс не был создан по причине: ' + createdCourse;
                    $scope.alertStatus = 'warning';
                }
            },
            function () {
                $scope.messages = 'Курс не был создан по неизвестной причине.';
                $scope.alertStatus = 'warning';
            })


        return NewsService.createNews($scope.news);
    };
    $scope.news = news;
}
