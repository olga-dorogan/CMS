function SessionService($window) {
    var service = this;
    service.isAnonymous = function () {
        return ($window.localStorage['token'] === undefined);
    };
    service.getAccessToken = function () {
        return $window.localStorage['token'];
    };
    service.getUserId = function () {
        return $window.localStorage['id'];
    };
}