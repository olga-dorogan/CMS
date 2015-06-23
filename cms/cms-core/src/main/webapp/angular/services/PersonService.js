function PersonService(Restangular) {
    var Person = Restangular.all("resources/person");

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
        );
    };

    this.getTeachers = function () {
        return Person.getList({'role': 'teacher'}).$object;
    };
}