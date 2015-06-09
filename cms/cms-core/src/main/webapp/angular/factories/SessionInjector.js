function SessionInjector($rootScope, sessionService) {
    var sessionInjector = {
        request: function (config) {
            if (!sessionService.isAnonymous()) {
                config.headers['x-session-token'] = sessionService.getAccessToken();
                config.headers['x-session-id'] = sessionService.getUserId();
            }
            return config;
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