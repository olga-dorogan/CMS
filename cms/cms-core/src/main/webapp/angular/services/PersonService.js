function PersonService(Restangular, $window) {
    var Person = Restangular.all("resources/person");
    var PersonDescription = Person.one($window.localStorage['id']).get("description");//person/:person_id/description

    this.getPersonDescription = function () {
        return PersonDescription;//Возвращение описание человека для личного кабинета
    };

    this.createPerson = function (user) {
        var name = user.name.split(" ");

        return Person.post(
            {
                "email": user.email,
                "id": null,
                "lastName": name[1],
                "name": name[0],
                "personRole": null,
                "secondName": null
            }
        )
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
        return Person.one($window.localStorage['id']).put(
            {
                "name": user.name,
                "surname": user.surname,
                "secondName": user.secondName
            }
        ) && PersonDescription.put({
            "experience": user.experience,
            "graduation": user.graduation,
            "phoneNumber": user.phoneNumber
        });
    };

    this.getTeachers = function () {
        return Person.getList({'role': 'teacher'});
    };

    this.getCoursesStatusesForPerson = function (personId) {
        if (personId === undefined) {
            return [];
        }
        return Restangular.one("resources/person", personId).all("course").getList();
    };

    this.getCoursesForPersonCap = function () {
        return [
            {id: 2, name: "Java SE", description: "Description for Java SE"}
        ];
    };

}