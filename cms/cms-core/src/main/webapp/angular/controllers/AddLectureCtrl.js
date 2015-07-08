function AddLectureCtrl($scope, $stateParams, $state, $modal, CourseContentService, FileUploader, lecturesCnt) {
    var BASE_URL_FILE_TO_STORAGE = 'resources/file/upload';

    $scope.lecture = $scope.lecture || {};

    $scope.isValidLecture = function (lectureForm) {
        return !lectureForm.$invalid && areValidLinks();
    };

    var areValidLinks = function () {
        var valid = true;
        for (var i = 0; i < $scope.lecture.links.length; i++) {
            if ($scope.lecture.links[i].orderNum == undefined) {
                valid = false;
                break;
            }
        }
        if (valid) {
            for (var i = 0; i < $scope.lecture.links.length; i++) {
                for (var j = i + 1; j < $scope.lecture.links.length; j++) {
                    if ($scope.lecture.links[i].orderNum == $scope.lecture.links[j].orderNum) {
                        valid = false;
                        break;
                    }
                }
                if (!valid) {
                    break;
                }
            }
        }
        return valid;
    };

    var createLecture = function () {
        $scope.lecture.createDate = new Date();
        $scope.lecture.orderNum = lecturesCnt + 1;
        $scope.lecture.courseId = $stateParams.courseId;
        return CourseContentService.createLecture($scope.lecture);
    };

    $scope.alertData = {
        boldTextTitle: "Ошибка",
        mode: 'danger'
    };

    $scope.ok = function () {
        createLecture().then(
            function (successResult) {
                if (successResult.responseStatus == 200 || successResult.responseStatus == 201) {
                    $state.go('person.course.content');
                } else {
                    $scope.alertData.textAlert = successResult;
                    $scope.showAlertWithError();
                }
            },
            function (fail) {
                $scope.alertData.textAlert = fail;
            }
        );
    };

    $scope.cancel = function () {
        $state.go('person.course.content');
    };

    $scope.showAlertWithError = function () {

        var modalInstance = $modal.open(
            {
                templateUrl: 'angular/templates/alertModal.html',
                controller: function ($scope, $modalInstance, data) {
                    $scope.data = data;
                    $scope.close = function () {
                        $modalInstance.close(data);
                    }
                },
                backdrop: true,
                keyboard: true,
                backdropClick: true,
                size: 'lg',
                resolve: {
                    data: function () {
                        return $scope.alertData;
                    }
                }
            });
    };

    // file uploading
    var uploader = $scope.uploader = new FileUploader({
        url: BASE_URL_FILE_TO_STORAGE
    });
    // filters
    uploader.filters.push({
        name: 'customFilter',
        fn: function (item /*{File|FileLikeObject}*/, options) {
            return this.queue.length < 10;
        }
    });
    // callbacks
    uploader.onBeforeUploadItem = function (item) {
        item.isUploaded = true;
        console.info('onBeforeUploadItem', item);
    };
    uploader.onSuccessItem = function (fileItem, response, status, headers) {
        console.info('onSuccessItem', fileItem, response, status, headers);
        fileItem.isUploaded = false;
        response.orderNum = $scope.lecture.links.length + 1;
        $scope.lecture.links.push(response);
        $scope.newLink.orderNum = $scope.newLink.orderNum + 1;
    };
    uploader.onErrorItem = function (fileItem, response, status, headers) {
        fileItem.isUploaded = false;
        console.info('onErrorItem', fileItem, response, status, headers);
    };
    uploader.onCompleteItem = function (fileItem, response, status, headers) {
        console.info('onCompleteItem', fileItem, response, status, headers);
    };
    uploader.onCompleteAll = function () {
        console.info('onCompleteAll');
    };

    // lecture links operations
    $scope.lecture.links = $scope.lecture.links || [];
    $scope.newLink = $scope.newLink || {'orderNum': $scope.lecture.links.length + 1};
    $scope.addLink = function () {
        $scope.lecture.links.push(angular.copy($scope.newLink));
        $scope.newLink.orderNum = $scope.lecture.links.length + 1;
        $scope.newLink.description = '';
        $scope.newLink.link = '';
    };
    $scope.removeLink = function (linkToRemove) {
        for (var i = 0; i < $scope.lecture.links.length; i++) {
            if (angular.equals($scope.lecture.links[i], linkToRemove)) {
                $scope.lecture.links.splice(i, 1);
            }
        }
    };
}