function uploadDirective(UploadManager) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            $(element).fileupload({
                dataType: 'text',
                url: "resources/file/upload",
                add: function (e, data) {
                    UploadManager.add(data);
                },
                progressall: function (e, data) {
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    UploadManager.setProgress(progress);
                },
                done: function (e, data) {
                    UploadManager.setProgress(0);
                }
            });
        }
    };
}
