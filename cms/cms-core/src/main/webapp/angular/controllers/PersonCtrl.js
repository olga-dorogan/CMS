function PersonCtrl($scope, $window, CourseService, PersonService) {
    CourseService.getCourses().then(function (result) {
        $scope.courses = result;
    });
    $scope.getPersonCourses = function () {
        return PersonService.getCoursesForPerson($window.localStorage['id']);
    };
    $scope.personCourses = null;

    $scope.isPersonInCourse = function (course) {
        if ($scope.personCourses == null) {
            $scope.personCourses = $scope.getPersonCourses();
        }
        var found = false;
        for (var i = 0; i < $scope.personCourses.length; i++) {
            if ($scope.personCourses[i].id === course.id) {
                found = true;
                break;
            }
        }
        return found;
    };

}
