function ThemeService(Restangular){
    //FIXME Сделать оптавку новой темы на сервер через POST
    var Theme=Restangular.all("rest/theme");
    this.createTheme=function(newTheme){
        //Запрос POST
        return Theme.post(newTheme);

    };

}