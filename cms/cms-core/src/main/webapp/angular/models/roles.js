var teacherAccess=function($location, $q,$rootScope){
    if($rootScope.role=="teacher"){
        return true;
    }else{
        $location.path("/home");
    }
};
var studentAccess=function($location, $q,$rootScope){
    if($rootScope.role=="student"){
        return true;
    }else{
        $location.path("/home");
    }
};