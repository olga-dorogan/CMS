function HomeCtrl(GAuth, GApi,$scope,CourseService) {
    $scope.courses = CourseService.getCourses();
}
