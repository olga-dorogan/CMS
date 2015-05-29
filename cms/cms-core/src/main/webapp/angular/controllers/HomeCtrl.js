function HomeCtrl(GAuth, GApi,$scope,CourseService) {
    CourseService.getCourses().success(function(data){
        $scope.courses=data;
    });
}
