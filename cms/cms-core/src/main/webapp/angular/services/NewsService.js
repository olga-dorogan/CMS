function NewsService(Restangular) {
    var restBase = 'resources/news';
    var News = Restangular.all(restBase);
    this.getNews = function () {
        return News.getList();
    };

    this.getPersonNews = function (personId) {
        if (personId === undefined) {
            return {};
        }
        return Restangular.one(restBase, personId).all('news-person').getList();
    };

    this.getNewsFromCourse = function (courseId) {
        if (courseId === undefined) {
            return {};
        }
        return Restangular.one(restBase, courseId).all('news').getList();
    };


    this.createNews = function (newNews) {
        return Restangular.one(restBase, newNews.courseId).all('news').getList().post(newNews);
    };



}