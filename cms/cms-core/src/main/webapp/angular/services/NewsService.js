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


//FIXME заглушка, для нормальной работы на без запуска WildFly
    this.getNewsСap = function () {
        return [
            {title: "Начался 1 курс", description: "Начался 1 курс по Java SE", date: "25.02.2015"},
            {title: "Начался 2 курс", description: "Начался 2 курс по Java EE", date: "25.03.2015"},
            {title: "Начался 3 курс", description: "Начался 3 курс по Java SE", date: "25.04.2015"}
        ];

    }

}