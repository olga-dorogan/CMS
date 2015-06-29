/**
 * Created by olga on 25.06.15.
 */
function CourseContentCtrl($scope, $modal, $log, lectures) {
    $scope.lectures = lectures;

    $scope.createLecture = function () {
        var modalInstance = $modal.open({
            animation: true,
            templateUrl: 'addLectureModal.html',
            controller: 'AddLectureCtrl',
            size: 'lg'
        });
        modalInstance.result.then(function (newLecture) {
            $scope.lectures.push(newLecture);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
}