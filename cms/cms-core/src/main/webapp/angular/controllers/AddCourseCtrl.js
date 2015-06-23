//TODO: Добавить контроллер контроллирующий добавление курсов
function AddCourseCtrl($scope, $rootScope, CourseService, PersonService) {
    $scope.course = {};
    $scope.courseTeachers = {};
    $scope.teachers = PersonService.getTeachers();
    $scope.createCourse = function () {
        console.log($scope.course);
        console.log(JSON.stringify($scope.courseTeachers));
        $scope.course.id = $rootScope.courses[$rootScope.courses.length - 1].id + 1;
        $rootScope.courses.push($scope.course);
        //FIXME Сформировать объект курса и отправить его сервису
        //CourseService.createTheme($scope.course);
    }
}
