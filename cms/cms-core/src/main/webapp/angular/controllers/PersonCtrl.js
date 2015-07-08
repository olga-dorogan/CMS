function PersonCtrl($scope, coursesGroups) {

    $scope.personCourses = coursesGroups.coursesEnrolled;
    $scope.newCourses = coursesGroups.coursesToSubscribe;

    $scope.getActionMsg = function (status) {
        var msg = 'Подписаться';
        switch (status) {
            case "REQUESTED":
                msg = 'Перейти к курсу';
                break;
            case "SIGNED":
                msg = 'Перейти к курсу';
                break;
            case "UNSIGNED":
                msg = 'Подписаться';
                break;
            default:
                msg = 'Действия запрещены';
        }
        return msg;
    };
}
