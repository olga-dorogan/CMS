function PersonCtrl($scope, $modal, courseService, coursesGroups, oldCourses) {

    $scope.personCourses = coursesGroups.coursesEnrolled;
    $scope.newCourses = coursesGroups.coursesToSubscribe;
    $scope.oldCourses = oldCourses;

    $scope.getActionMsg = function (status, teacherRole) {
        var msg = 'Подписаться';
        if (teacherRole) {
            msg = 'Редактировать';
        } else {
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
            }
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
                } else {
                    removeCourseFromView(courseId);
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

    var removeCourseFromView = function (courseId) {
        var removed = false;
        removed = removeFromArray($scope.personCourses, courseId);
        if (!removed) {
            removed = removeFromArray($scope.newCourses, courseId);
        }
        if (!removed) {
            removed = removeFromArray($scope.oldCourses, courseId);
        }

    };
    var removeFromArray = function (array, courseId) {
        for (var i = 0; i < array.length; i++) {
            if (array[i].id == courseId) {
                array.splice(i, 1);
                return true;
            }
        }
        return false;
    };
}
