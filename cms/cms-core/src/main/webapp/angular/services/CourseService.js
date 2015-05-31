function CourseService(Restangular){
    //Получаем все REST методы по адресу BaseUrl/rest/courses
    var Course=Restangular.all("rest/courses");
    this.getCourses=function(){
        //Запрос GET
        return Course.getList();

    };
    //FIXME заглушка, для нормальной работы на без запуска WildFly
    this.getCoursesCap=function(){
        return [{title:"Java EE",description:"Description for Java EE"},
            {title:"Java SE",description:"Description for Java SE"},
            {title:"Android",description:"Description for Android"}];
    }
}
