//TODO: Добавить контроллер контроллирующий добавление курсов
function AddCourseCtrl($scope, CourseService, PersonService) {
    $scope.course = $scope.course || {};
    $scope.courseTeachers = $scope.courseTeachers || [];
    $scope.teachers = PersonService.getTeachers();

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
        CourseService.createCourse($scope.course)
            .then(
            function (createdCourse) {
                if (CourseService.isCourseReallyCreated(createdCourse)) {
                    $scope.messages = 'The course "' + createdCourse.name + '" has been created.';
                    $scope.alertStatus = 'success';
                } else {
                    $scope.messages = 'The course has not been created: ' + createdCourse;
                    $scope.alertStatus = 'warning';
                }
            },
            function () {
                $scope.messages = 'The course has not been created';
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
