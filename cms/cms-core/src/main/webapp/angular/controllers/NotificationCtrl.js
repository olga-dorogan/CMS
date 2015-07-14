function NotificationCtrl(Restangular){
    var restBase = '';//FIXME write ws
    var Notification = Restangular.all(restBase);

    $scope.mailModel = {
        sms: false,
        email: false
    };

    $scope.doNotifying = function(){
        if($scope.mailModel.email){
            Notification.post({
                mailtype: "email",
                text: $scope.emailMessage,
                subject: $scope.subject
            })
        }
        if($scope.mailModel.sms){
            Notification.post({
                mailtype: "sms",
                text: $scope.smsMessage
            })
        }
    };
}