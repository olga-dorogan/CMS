function NewsCtrl($scope, news) {
    for (var i = 0; i < news.length; i++) {
        var timestamp = news[i].date;
        news[i].date = timestampConvector(timestamp);
    }
        $scope.news = news;
}

var timestampConvector = function (timestamp) {
    var newsCreateDate = new Date(timestamp);
    var currentDate = new Date();
    var formattedDate
    if (currentDate.getDate() == newsCreateDate.getDate()) {
        formattedDate = "сегодня";
    } else if (currentDate.getDate() == newsCreateDate.getDate() + 1) {
        formattedDate = "вчера";
    } else {
        var date = ((newsCreateDate.getDate()) < 10) ? "0" + newsCreateDate.getDate() : newsCreateDate.getDate();
        var month = ((newsCreateDate.getMonth() + 1) < 10) ? "0" + (newsCreateDate.getMonth() + 1) : (newsCreateDate.getMonth() + 1);
        formattedDate = date + "-" + month + "-" + newsCreateDate.getFullYear();

    }

    var hours = (newsCreateDate.getHours() < 10) ? "0" + newsCreateDate.getHours() : newsCreateDate.getHours();
    var minutes = (newsCreateDate.getMinutes() < 10) ? "0" + newsCreateDate.getMinutes() : newsCreateDate.getMinutes();
    var formattedTime = hours + ":" + minutes;
    return formattedDate + " в " + formattedTime;

}