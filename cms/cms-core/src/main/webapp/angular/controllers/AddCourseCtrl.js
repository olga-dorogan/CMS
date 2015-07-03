function AddCourseCtrl($rootScope, $scope, CourseService, allTeachers) {
    $scope.course = $scope.course || {};
    $scope.course.teachers = [parseInt($rootScope.getUserId())];
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
        return $scope.course.teachers.length > 0;
    };
    $scope.isValidCourse = function (courseForm) {
        return !courseForm.$invalid &&
            $scope.isValidTeachers() &&
            $scope.isValidDates();
    };

    $scope.createCourse = function () {
        for (var i = 0; i < $scope.course.teachers.length; i++) {
            $scope.course.teachers[i] = {'id': $scope.course.teachers[i]};
        }
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
                $scope.course.teachers = [];
            }
        );
    }
}
