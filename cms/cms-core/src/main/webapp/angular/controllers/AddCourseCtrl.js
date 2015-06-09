//TODO: Добавить контроллер контроллирующий добавление курсов
function AddCourseCtrl($scope,$rootScope,CourseService){
    $scope.course = {};
    $scope.createCourse=function(){
        console.log($scope.course);
        $scope.course.id=$rootScope.courses[$rootScope.courses.length-1].id+1;
        $rootScope.courses.push($scope.course);
        //FIXME Сформировать объект курса и отправить его сервису
        //CourseService.createTheme($scope.course);
    }
}
