function PersonCtrl($scope, $modal, courseService, coursesGroups, oldCourses) {

    $scope.personCourses = coursesGroups.coursesEnrolled;
    $scope.newCourses = coursesGroups.coursesToSubscribe;
    $scope.oldCourses = oldCourses;

    $scope.getActionMsg = function (status) {
        var msg = 'Подписаться';
        switch (status) {
            case "REQUESTED":
                msg = 'Перейти к курсу';
                break;
            case "SIGNED":
                msg = 'Перейти к курсу';
                break;
            case "UNSIGNED":
                msg = 'Подписаться';
                break;
            default:
                msg = 'Действия запрещены';
        }
        return msg;
    };

    $scope.removeCourse = function (courseId) {
        courseService.removeCourse(courseId).then(
            function (success) {
                if (success.responseStatus / 100 != 2) {
                    showAlertWithError(
                        {
                            boldTextTitle: "Ошибка",
                            textAlert: success,
                            mode: 'danger'
                        });
                }
            });
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
