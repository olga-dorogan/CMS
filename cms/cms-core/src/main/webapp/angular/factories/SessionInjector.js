function SessionInjector($rootScope, sessionService) {
    var sessionInjector = {
        request: function (config) {
            if (!sessionService.isAnonymous()) {
                config.headers['x-session-token'] = sessionService.getAccessToken();
            }
            return config;
        },
        response: function (response, headers) {
            if (response.status === 204) {
                $rootScope.$broadcast('app.changeLocation', {location: response.headers('location')});
            }
            return response;
        },
        responseError: function (response) {
            if (response.status === 401) {
                $rootScope.$broadcast('app.unauthorized');
            }
            return response;
        }
    };
    return sessionInjector;
}