function AddOrEditCourseCtrl($rootScope, $scope, $state, $modal, CourseService, allTeachers, coursePrototypes, mode, editedCourse) {

    $scope.isAddMode = function () {
        return mode == 'add';
    };

    $scope.course = $scope.isAddMode() ? ($scope.course || {}) : editedCourse;
    $scope.courseTeachers = [parseInt($rootScope.getUserId())];
    $scope.teachers = allTeachers;
    $scope.coursePrototypes = coursePrototypes;
    $scope.coursePrototypeId = -1;
    $scope.okLabel = $scope.isAddMode() ? 'Добавить курс' : 'Обновить курс';

    $scope.isValidDates = function () {
        if (!$scope.course.startDate && !$scope.course.endDate) {
            return true;
        }
        if ($scope.course.startDate && !$scope.course.endDate) {
            return true;
        }
        return $scope.course.startDate <= $scope.course.endDate;
    };
    $scope.isValidTeachers = function () {
        return $scope.courseTeachers.length > 0;
    };
    $scope.isValidCourse = function (courseForm) {
        return !courseForm.$invalid &&
            $scope.isValidTeachers() &&
            $scope.isValidDates();
    };

    $scope.disableAsPrototype = function () {
        return $scope.coursePrototypeId == -1;
    };
    var getFieldFromPrototype = function (field) {
        if ($scope.coursePrototypeId == -1) {
            return '';
        }
        for (var i = 0; i < $scope.coursePrototypes.length; i++) {
            if ($scope.coursePrototypes[i].id == $scope.coursePrototypeId) {
                switch (field) {
                    case 'name':
                        return $scope.coursePrototypes[i].name;
                    case 'description':
                        return $scope.coursePrototypes[i].description;
                }
            }
        }
        return '';
    };
    $scope.setTitleAsPrototype = function () {
        $scope.course.name = getFieldFromPrototype('name');
    };
    $scope.setDescriptionAsPrototype = function () {
        $scope.course.description = getFieldFromPrototype('description');
    };

    var fillTeachers = function () {
        $scope.course.teachers = [];
        for (var i = 0; i < $scope.courseTeachers.length; i++) {
            $scope.course.teachers[i] = {'id': $scope.courseTeachers[i]};
        }
    };
    var alertData = {
        boldTextTitle: "Ошибка",
        mode: 'danger'
    };

    $scope.createCourse = function () {
        fillTeachers();
        CourseService.createCourse($scope.course, $scope.coursePrototypeId)
            .then(
            function (createdCourse) {
                if (CourseService.isCourseSuccessfullyCreated(createdCourse)) {
                    $state.go('person', {}, {reload: true});
                } else {
                    alertData.textAlert = createdCourse;
                    showAlertWithError(alertData);
                }
            },
            function () {
                alertData.textAlert = 'Причина неизвестна';
                showAlertWithError(alertData);
            })
            .finally(function () {
                $scope.course.teachers = [];
            });
    };

    $scope.updateCourse = function () {
        CourseService.updateCourse($scope.course).then(
            function (updatedCourse) {
                if(updatedCourse.responseStatus / 100 == 2) {
                    $state.go('person.editCourses', {}, {reload: true});
                } else {
                    alertData.textAlert = updatedCourse;
                    showAlertWithError(alertData);
                }
            },
            function () {
                alertData.textAlert = 'Причина неизвестна';
                showAlertWithError(alertData);
            }
        )
    };

    $scope.cancel = function () {
        $scope.isAddMode() ? $state.go('home') : $state.go('person.editCourses');
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

}
