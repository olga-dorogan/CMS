function AddNewsCtrl($scope, $stateParams, NewsService ,$modal,$state) {
    $scope.news = {};

    $scope.createNews = function () {
        $scope.news.date = new Date();
        $scope.news.courseId =  $stateParams.courseId;
         NewsService.createNews($scope.news)
            .then(
            function (createdNews) {
                console.log("service: create newsCtrl 1");
                if (NewsService.isNewsSuccessfullyCreated(createdNews)) {
                     $state.go('news', {}, {reload: true});
                } else {
                    alertData.textAlert = createdNews;
                    showAlertWithError(alertData);
                }
            },
            function () {
                alertData.textAlert = 'Причина неизвестна';
                showAlertWithError(alertData);
            });
    };


    var alertData = {
        boldTextTitle: "Ошибка",
        mode: 'danger'
    };

    var showAlertWithError = function (alertData) {
        var modalInstance = $modal.open(
            {
                templateUrl: 'angular/templates/alertModal.html',
                controller: function ($scope, $modalInstance) {
                    $scope.data = alertData;
                    $scope.close = function () {
                        $modalInstance.close();
                    }
                },
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                size: 'lg'
            }
        );
    };
}
