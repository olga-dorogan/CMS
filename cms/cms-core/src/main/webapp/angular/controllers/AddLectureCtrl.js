function AddLectureCtrl($scope, $stateParams, $state, $modal, CourseContentService, FileUploader) {
    var BASE_URL_FILE_TO_STORAGE = 'resources/file/upload';

    $scope.lecture = $scope.lecture || {};

    $scope.isValidLecture = function (lectureForm) {
        return !lectureForm.$invalid;
    };
    var createLecture = function () {
        $scope.lecture.createDate = new Date();
        $scope.lecture.orderNum = 1;
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
                if (successResult.status == 200 || successResult.status == 201) {
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
        response.orderNum = $scope.lectureLinks.length + 1;
        $scope.lectureLinks.push(response);
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
    $scope.lectureLinks = $scope.lectureLinks || [];
    $scope.newLink = $scope.newLink || {'orderNum': $scope.lectureLinks.length + 1};
    $scope.addLink = function () {
        $scope.lectureLinks.push(angular.copy($scope.newLink));
        $scope.newLink.orderNum = $scope.lectureLinks.length + 1;
        $scope.newLink.description = '';
        $scope.newLink.link = '';
    };
    $scope.removeLink = function (linkToRemove) {
        for (var i = 0; i < $scope.lectureLinks.length; i++) {
            if (angular.equals($scope.lectureLinks[i], linkToRemove)) {
                $scope.lectureLinks.splice(i, 1);
            }
        }
    };
}