function PersonCtrl($state, $scope, $modal, PersonPersistenceService, courseService, personService, coursesGroups, oldCourses) {

    $scope.personCourses = coursesGroups.coursesEnrolled;
    $scope.newCourses = coursesGroups.coursesToSubscribe;
    $scope.oldCourses = oldCourses;

    $scope.getActionMsg = function (status, teacherRole) {
        var msg = 'Подписаться';
        if (teacherRole) {
            msg = 'Редактировать';
        } else {
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
            }
        }
        return msg;
    };

    $scope.removeCourse = function (courseId) {
        courseService.removeCourse(courseId).then(
            function (success) {
                if (success.responseStatus / 100 != 2) {
                    alertData.textAlert = success;
                    showAlertWithError(alertData);
                } else {
                    removeCourseFromView(courseId);
                }
            });
    };

    var alertData = {
        boldTextTitle: "Ошибка",
        textAlert: '',
        mode: 'danger'
    };
    var showAlertWithError = function (alertData) {
        var modalInstance = $modal.open(
            {
                templateUrl: 'angular/templates/alertModal.html',
                controller: function ($scope, $modalInstance) {
                    $scope.data = alertData;
                    $scope.close = function () {
                        $modalInstance.close();
                    }
                },
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                size: 'lg'
            }
        );
    };

    var removeCourseFromView = function (courseId) {
        var removed = false;
        removed = removeFromArray($scope.personCourses, courseId);
        if (!removed) {
            removed = removeFromArray($scope.newCourses, courseId);
        }
        if (!removed) {
            removed = removeFromArray($scope.oldCourses, courseId);
        }

    };
    var removeFromArray = function (array, courseId) {
        for (var i = 0; i < array.length; i++) {
            if (array[i].id == courseId) {
                array.splice(i, 1);
                return true;
            }
        }
        return false;
    };

    // subscribe person to the course
    var personPhoneNumber = undefined;

    var hasPersonPhoneNumber = function () {
        return personPhoneNumber != undefined && personPhoneNumber != null;
    };

    var getPersonPhoneNumber = function () {
        var modalInstance = $modal.open({
            animation: $scope.animationsEnabled,
            templateUrl: 'myModalContent.html',
            controller: 'SettingInstanceCtrl',
            size: 'lg'
        });
        modalInstance.result.then(function (number) {
            personPhoneNumber = number;
        });
    };

    var requestPersonPhoneNumber = function (callback) {
        alertData.textAlert = 'Ошибка при получении контактной информации';
        personService.getPersonDescription(PersonPersistenceService.getId()).then(
            function (description) {
                if (description.responseStatus / 100 == 2) {
                    callback(description.phoneNumber);
                } else {
                    showAlertWithError(alertData);
                }
            },
            function (error) {
                showAlertWithError(alertData);
            });
    };

    $scope.subscribePersonToCourse = function (courseId) {
        if (!hasPersonPhoneNumber()) {
            if (personPhoneNumber == undefined) {
                requestPersonPhoneNumber(function (phone) {
                    personPhoneNumber = phone;
                    if (personPhoneNumber != undefined) {
                        $scope.subscribePersonToCourse(courseId);
                    }
                });
            }
            else if (personPhoneNumber == null) {
                getPersonPhoneNumber();
                if (personPhoneNumber != null) {
                    $scope.subscribePersonToCourse(courseId);
                }
            }
        }
        else {
            courseService.subscribePersonToCourse(courseId, PersonPersistenceService.getId()).then(
                function (success) {
                    $state.go($state.current.name, $state.params, {reload: true});
                })
        }
    };

}
