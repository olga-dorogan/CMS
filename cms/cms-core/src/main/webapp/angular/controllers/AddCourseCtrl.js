function AddCourseCtrl($scope, CourseService, allTeachers) {
    $scope.course = $scope.course || {};
    $scope.courseTeachers = $scope.courseTeachers || [];
    $scope.teachers = allTeachers;

    $scope.isValidDates = function () {
        if (!$scope.course.startDate && !$scope.course.endDate) {
            return true;
        }
        if ($scope.course.startDate && !$scope.course.endDate) {
            return true;
        }
        return $scope.course.startDate <= $scope.course.endDate;
    };
    $scope.isValidTeachers = function () {
        return $scope.courseTeachers.length > 0;
    };
    $scope.isValidCourse = function (courseForm) {
        return !courseForm.$invalid &&
            $scope.isValidTeachers() &&
            $scope.isValidDates();
    };

    $scope.createCourse = function () {
        CourseService.createCourse($scope.course).then(
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
            .finally(
            function () {
                $scope.course = {};
                $scope.courseTeachers = [];
            }
        );
    }
}
