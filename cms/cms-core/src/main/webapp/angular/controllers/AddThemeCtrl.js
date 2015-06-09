function AddThemeCtrl($scope,ThemeService,$stateParams){
    $scope.theme = {};
    //FIXME: Определили ID курса к   которому нужно добавлять тему
    $scope.courseId=$stateParams.courseId;
    $scope.createTheme=function(){
        alert("УРА МЫ ДОБАВИЛИ ТЕМУ");
        //FIXME Сформировать объект темы и отправить его
        ThemeService.createTheme($scope.theme);
    }

}
