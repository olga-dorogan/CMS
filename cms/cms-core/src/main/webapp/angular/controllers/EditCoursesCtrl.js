/**
 * Created by olga on 06.07.15.
 */
function EditCoursesCtrl($scope, $modal, courseService, courses) {
    $scope.courses = courses;
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
    $scope.updateCourse = function (courseId) {
        console.log("course updating");
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
