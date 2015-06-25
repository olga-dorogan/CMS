function HomeCtrl($scope, CourseService) {
    //Нормальное поведение
    if (window.location.port == 8080) {
        CourseService.getCourses().then(function (result) {
            $scope.courses = result;
        });
    } else if (window.location.port == 8000) {
        $scope.courses = CourseService.getCoursesCap();
    }
}
