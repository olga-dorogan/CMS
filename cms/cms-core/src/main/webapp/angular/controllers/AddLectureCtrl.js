function AddLectureCtrl($scope, $stateParams, $modalInstance, CourseContentService, FileUploader) {
    var BASE_URL_FILE_TO_STORAGE = 'resources/file/upload';

    $scope.lecture = $scope.lecture || {};

    $scope.isValidLecture = function (lectureForm) {
        return !lectureForm.$invalid;
    };
    var createLecture = function () {
        $scope.lecture.createDate = new Date();
        $scope.lecture.orderNum = 1;
        $scope.lecture.courseId = $stateParams.courseId;
        CourseContentService.createLecture($scope.lecture);
    };


    $scope.ok = function () {
        createLecture();
        $modalInstance.close($scope.lecture);
    };
    $scope.cancel = function () {
        $modalInstance.dismiss();
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
    uploader.onSuccessItem = function (fileItem, response, status, headers) {
        console.info('onSuccessItem', fileItem, response, status, headers);
        $scope.lectureLinks.push(response);
    };
    uploader.onErrorItem = function (fileItem, response, status, headers) {
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
    $scope.newLink = $scope.newLink || {};
    $scope.addLink = function () {
        $scope.lectureLinks.push(angular.copy($scope.newLink));
    };
    $scope.removeLink = function (linkToRemove) {
        for (var i = 0; i < $scope.lectureLinks.length; i++) {
            if (angular.equals($scope.lectureLinks[i], linkToRemove)) {
                $scope.lectureLinks.splice(i, 1);
            }
        }
    };
}