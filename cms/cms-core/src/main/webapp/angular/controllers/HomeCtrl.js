function HomeCtrl($rootScope,CourseService) {
    //FIXME заглушка, для нормальной работы на без запуска WildFly
    if(window.location.port==63342 && $rootScope.courses==undefined) {
        $rootScope.courses=CourseService.getCoursesCap();
    }else if(window.location.port==8080){
        //Нормальное поведение
        CourseService.getCourses().then(function (result) {
            $rootScope.courses = result;
        });
    }
}
