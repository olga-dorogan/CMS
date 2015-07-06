function CourseService(Restangular) {
    var restBase = 'resources/course';
    var Course = Restangular.all(restBase);

    this.getCourses = function () {
        return Course.getList();
    };

    this.getNewCourses = function () {
        var today = new Date();
        var todayAsString = today.getUTCFullYear() + '-' + (today.getUTCMonth() + 1) + '-' + today.getUTCDay();
        return Course.getList({'date': todayAsString});
    };

    this.createCourse = function (newCourse, prototypeId) {
        // POST returns promise, in which successHandler is executed ALWAYS when response contains any text
        // so, it's necessary to check is response status equal 2xx or not
        // or another way --- any successfully returned object contains field 'fromServer' with value 'true'
        return Course.post(newCourse, {'prototypeId': prototypeId});
    };

    this.isCourseSuccessfullyCreated = function (returnedObject) {
        return returnedObject.responseStatus == 201;
    };

    this.removeCourse = function(id) {
        return Restangular.one(restBase, id).remove();
    };

    this.subscribePersonToCourse = function (courseId, personId) {
        if (courseId === undefined || personId === undefined) {
            return {};
        }
        return Restangular.one(restBase, courseId).all('subscribe').put({"person_id": personId});
    };
    //FIXME заглушка, для нормальной работы на без запуска WildFly
    this.getCoursesCap = function () {
        return [{id: 1, name: "Java EE", description: "Description for Java EE"},
            {id: 2, name: "Java SE", description: "Description for Java SE"},
            {id: 3, name: "Android", description: "Description for Android"}];
    }
}
