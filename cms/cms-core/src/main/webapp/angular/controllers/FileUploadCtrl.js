function FileUploadCtrl($scope, $rootScope, UploadManager) {
    $scope.files = [];
    $scope.percentage = 0;
    $scope.upload = function () {
        UploadManager.upload();
        $scope.files = [];
    };

    $rootScope.$on('fileAdded', function (e, call) {
        $scope.files.push(call);
        $scope.$apply();
    });

    $rootScope.$on('uploadProgress', function (e, call) {
        $scope.percentage = call;
        $scope.$apply();
    });
}
