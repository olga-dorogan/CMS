function AuthService(PersonService, Restangular) {
    this.goAuth = PersonService.createPersonForAuth;
    this.getEmailHash = function (email) {
        //FIXME: do real request
        return '';//Restangular.all("resources/mailhash").post(email);
    };
}