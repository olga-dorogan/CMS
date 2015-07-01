function SettingCtrl($scope, $window, PersonService) {
    $scope.person = $scope.person || {};
    $scope.person.name = $window.localStorage['name'].split(" ")[0];
    $scope.person.surname = $window.localStorage['name'].split(" ")[1];
    $scope.person.description = PersonService.getPersonDescription() || {};

    $scope.isValidUser = function () {
        if (!this.isValidTextFields()) {
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

    $scope.updatePerson = function () {
        PersonService.updatePerson($scope.person);//FIXME finally refresh fields of person
    };

    $scope.addImage = function () {//FIXME check read file for image extension
        var f = document.getElementById('image').files[0],
            r = new FileReader();
        r.onloadend = function (e) {
            $scope.person.avatar =  e.target.result;
        };
        r.readAsBinaryString(f);

        PersonService.updatePicture($scope.person.avatar);
    };
}