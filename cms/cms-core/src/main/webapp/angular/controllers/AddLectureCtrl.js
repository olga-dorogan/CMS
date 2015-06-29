function AddLectureCtrl($scope, $stateParams, CourseContentService, FileUploader) {
    $scope.lecture = $scope.lecture || {};

    $scope.isValidLecture = function (lectureForm) {
        return !lectureForm.$invalid;
    };
    $scope.createLecture = function () {
        $scope.lecture.createDate = new Date();
        $scope.lecture.orderNum = 1;
        $scope.lecture.courseId = $stateParams.courseId;
        CourseContentService.createLecture($scope.lecture);
    };


    // file uploading
    var uploader = $scope.uploader = new FileUploader({
        url: 'resources/file/upload'
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
        console.log("newLink: " + JSON.stringify($scope.newLink));
        console.log("copy of new link: " + JSON.stringify(angular.copy($scope.newLink)));
        $scope.lectureLinks.push(angular.copy($scope.newLink));
        console.log("array: " + JSON.stringify($scope.lectureLinks));
    };
    $scope.removeLink = function(linkToRemove) {
        for(var i=0; i<$scope.lectureLinks.length; i++) {
            if(angular.equals($scope.lectureLinks[i], linkToRemove)){
                $scope.lectureLinks.splice(i, 1);
            }
        }
    };
}