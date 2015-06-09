function CourseService(Restangular){
    //Получаем все REST методы по адресу BaseUrl/rest/courses
    var Course=Restangular.all("rest/courses");
    this.getCourses=function(){
        //Запрос GET
        return Course.getList();

    };
    this.createCourses=function(newCourse){
        //FIXME POST Запрос на сервер для добавления курса в базу
    };
    //FIXME заглушка, для нормальной работы на без запуска WildFly
    this.getCoursesCap=function(){
        return [{id:1,name:"Java EE",description:"Description for Java EE"},
            {id:2,name:"Java SE",description:"Description for Java SE"},
            {id:3,name:"Android",description:"Description for Android"}];
    }
}
