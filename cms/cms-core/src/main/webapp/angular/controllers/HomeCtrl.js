function HomeCtrl($scope,CourseService) {
    //FIXME заглушка, для нормальной работы на без запуска WildFly
    if(window.location.port==63342) {
        $scope.courses=CourseService.getCoursesCap();
    }else{
        //Нормальное поведение
        CourseService.getCourses().then(function (result) {
            $scope.courses = result;
        });
    }
}
