function AddLectureCtrl($scope, CourseContentService, $stateParams) {
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
}