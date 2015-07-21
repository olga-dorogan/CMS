function AddNewsCtrl($scope, $stateParams, NewsService ,$modal) {
    $scope.news = {};
    $scope.noNews = ($scope.news.isEmpty());

    $scope.createNews = function () {
        $scope.news.date = new Date();

         NewsService.createNews($scope.news, $stateParams.courseId)
            .then(
            function (createdNews) {
                if (NewsService.isNewsSuccessfullyCreated(createdNews)) {
                    $state.go('person', {}, {reload: true});
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
