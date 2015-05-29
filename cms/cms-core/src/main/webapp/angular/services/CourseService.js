function CourseService($http){
    this.getCourses=function(){
        return $http({
            url: 'rest/courses',
            method: 'GET'
        });
        /*return [{title:"Java EE",description:"Description for Java EE"},
            {title:"Java SE",description:"Description for Java SE"},
            {title:"Android",description:"Description for Android"}];*/
    };
}
