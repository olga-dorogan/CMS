function AuthService(Restangular,$http){
    var Auth=Restangular.all("resources/person");
    this.goAuth=function(user){
        //Запрос POST
        var name=user.name.split(" ");
        return Auth.post(
                    {
                        "course": null,
                        "email": user.email,
                        "id": user.id,
                        "lastName": name[1],
                        "marks": null,
                        "name": name[0],
                        "personRole": null,
                        "secondName": null
                    }
        );
    };
}