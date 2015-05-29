function SessionService($window) {
    var service = this;
    service.isAnonymous = function () {
        return ($window.gapi === undefined || $window.gapi.auth.getToken() === null);
    };
    service.getAccessToken = function () {
        return $window.gapi.auth.getToken().access_token;
    };
}