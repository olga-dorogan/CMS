function CourseService(Restangular) {
    //Получаем все REST методы по адресу BaseUrl/rest/courses
    var Course = Restangular.all("resources/course");
    this.getCourses = function () {
        return Course.getList();
    };
    this.createCourse = function (newCourse) {
        // POST returns promise, in which successHandler is executed ALWAYS when response contains any text
        // so, it's necessary to check is response status equal 2xx or not
        // or another way --- any successfully returned object contains field 'fromServer' with value 'true'
        return Course.post(newCourse);
    };
    this.isCourseReallyCreated = function(returnedObject) {
        return (returnedObject.hasOwnProperty('fromServer') && returnedObject.fromServer);
    };
    //FIXME заглушка, для нормальной работы на без запуска WildFly
    this.getCoursesCap = function () {
        return [{id: 1, name: "Java EE", description: "Description for Java EE"},
            {id: 2, name: "Java SE", description: "Description for Java SE"},
            {id: 3, name: "Android", description: "Description for Android"}];
    }
}
