function AuthService(PersonService, Restangular, PersonPersistenceService) {
    this.goAuth = PersonService.createPersonForAuth;
    this.getEmailHash = function (email) {
        //console.log(email);
        Restangular.all("resources").one("mailhash").get({"email": email}).then(function(result){
            return result.hash;
        })
    };
}