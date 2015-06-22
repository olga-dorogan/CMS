function AuthService(Restangular,$http){
    var Auth=Restangular.all("resources/person");
    this.goAuth=function(user){
        //Запрос POST
        var name=user.name.split(" ");
        return Auth.post(
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
}