function PersonService(Restangular, PersonPersistenceService) {
    var REST_BASE = 'resources/person';
    var Person = Restangular.all(REST_BASE);
    //person/:person_id/description

    this.getPersonPhone = function (userId) {
        return Restangular.one(REST_BASE, userId).customGET('description', {'field': 'phone'});
    };
    this.setPersonPhone = function (userId, phoneNumber) {
        phoneNumber = phoneNumber.replace(/[^\/\d]/g, '');
        return Restangular.one(REST_BASE, userId).all('phone').customPUT({'phoneNumber': phoneNumber});
    };
    this.getPersonDescription = function (userId) {
        return Restangular.one(REST_BASE, userId).customGET('description', {'field': 'all'});
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
        return Person.one(PersonPersistenceService.getId()).put(
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