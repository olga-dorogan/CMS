function SettingCtrl($scope, $window, PersonService) {
    $scope.person = $scope.person || {};
    $scope.person.name = $window.localStorage['name'].split(" ")[0];
    $scope.person.surname = $window.localStorage['name'].split(" ")[1];

    $scope.isValidUser = function () {
        if(!this.isValidTextFields()){
            $scope.messages = 'Проверьте Ваши введённые ФИО. Поле не может быть пустым!';
            $scope.alertStatus = 'warning';
        }

        return !personForm.$invalid && !this.isValidTextFields;
    };

    $scope.isValidTextFields = function () {
        return $scope.person.name.length > 0 &&
            $scope.person.surname.length > 0 &&
            $scope.person.secondName.length > 0;
    };

    $scope.phoneValidator = function () {
        $scope.person.phoneNumber = $scope.person.phoneNumber.replace(/[^\d]/g, '');//замена символов на пустые, для ввода только цифр
    };

    $scope.updatePerson = function(){

    }
}