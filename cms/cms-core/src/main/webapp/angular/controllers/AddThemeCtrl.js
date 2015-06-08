function AddThemeCtrl($scope,ThemeService){
    $scope.theme = {};
    $scope.createTheme=function(){
        alert("УРА МЫ ДОБАВИЛИ ТЕМУ")
        //FIXME Сформировать объект темы и отправить его
        ThemeService.createTheme($scope.theme);
    }

}
