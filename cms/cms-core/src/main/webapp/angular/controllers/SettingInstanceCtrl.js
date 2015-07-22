function SettingInstanceCtrl($scope, $modalInstance) {

    $scope.ok = function () {
        $modalInstance.close($scope.phoneNumber);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
}