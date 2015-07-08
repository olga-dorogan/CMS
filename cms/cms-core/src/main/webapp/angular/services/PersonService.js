function PersonService(Restangular) {
    var Person = Restangular.all("resources/person");

    this.getPersonDescription = function () {
        var PersonDescription = Person.get($window.localStorage['id']).one("description");

        return PersonDescription.getList()[0];//Возвращение описание человека для личного кабинета
    };

    this.createPerson = function (user) {
        var name = user.name.split(" ");
        var PersonDescription = Person.one("description");
        return Person.post(
                {
                    "email": user.email,
                    "id": null,
                    "lastName": name[1],
                    "name": name[0],
                    "personRole": null,
                    "secondName": null
                }
            ) && PersonDescription.post({//FIXME maybe this could be removed
                "id": $window.localStorage['id'],
                "experience": null,
                "graduation": null,
                "phoneNumber": null
            });
    };

    this.createPersonForAuth = function (user) {
        var name = user.name.split(" ");
        return Person.post(
            {
                "email": user.email,
                "id": null,
                "lastName": name[1],
                "name": name[0],
                "personRole": null,
                "secondName": null
            })
    };

    this.updatePerson = function (user) {
        var PersonDescription = Person.get($window.localStorage['id']).one("description");
        return Person.get($window.localStorage['id']).put(
                {
                    "name": user.name,
                    "surname": user.surname,
                    "secondName": user.seconName
                }
            ) && PersonDescription.put({
                "experience": user.experience,
                "graduation": user.graduation,
                "phoneNumber": user.phoneNumber
            });
    };//FIXME проверка этого метода на правильность урлов

    this.getTeachers = function () {
        return Person.getList({'role': 'teacher'});
    };

    this.getCoursesStatusesForPerson = function (personId) {
        if (personId === undefined) {
            return [];
        }
        return Restangular.one("resources/person", personId).all("course").getList();
    };

    this.getLinkNameForStatus = function (status) {
        var msg = 'Подписаться';
        switch (status) {
            case "REQUESTED":
                msg = 'Перейти к курсу';
                break;
            case "SIGNED":
                msg = 'Перейти к курсу';
                break;
            case "UNSIGNED":
                msg = 'Подписаться';
                break;
            default:
                msg = 'Действия запрещены';
        }
        return msg;
    };

    this.getCoursesForPersonCap = function () {
        return [{id: 2, name: "Java SE", description: "Description for Java SE"}];
    };

}