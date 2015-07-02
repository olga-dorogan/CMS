function AuthService(PersonService) {
    this.goAuth = PersonService.createPersonForAuth;
}