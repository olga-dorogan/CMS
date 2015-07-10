myApp.directive('gravatar', function() {
    return {
        restrict: 'AE',
        replace: true,
        scope: {
            name: '@',
            height: '@',
            width: '@',
            emailHash: '@'
        },
        link: function(scope, el, attr) {
            scope.defaultImage = 'https://cdn4.iconfinder.com/data/icons/e-commerce-icon-set/48/Username_2-32.png';
        },
        template: '<img class="circular" alt="{{name}}" src="https://secure.gravatar.com/avatar/{{emailHash}}.jpg?s={{width}}&d={{defaultImage}}">'
    };
});