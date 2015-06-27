/**
 * Created by olga on 25.06.15.
 */
function CourseContentCtrl($scope, $stateParams, CourseContentService) {
    $scope.courseId = $stateParams.courseId;
    $scope.lectures = CourseContentService.getLectures($stateParams.courseId);
}