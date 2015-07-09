var teacherAccess = function ($location, $window) {
    if ($window.localStorage['role'] == "teacher") {
        return true;
    } else {
        $location.path("/home");
    }
};
var studentAccess = function ($location, $window) {
    if ($window.localStorage['role'] == "student") {
        return true;
    } else {
        $location.path("/home");
    }
};