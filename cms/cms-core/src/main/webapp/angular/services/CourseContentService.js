/**
 * Created by olga on 25.06.15.
 */
function CourseContentService(Restangular) {
    var createLectureRest = function (courseId) {
        return Restangular.one('resources/course', courseId).all('lesson');
    };

    this.getLectures = function (courseId) {
        if (courseId === undefined) {
            return [];
        }
        return createLectureRest(courseId).getList();
    };

    this.createLecture = function (newLecture) {
        return createLectureRest(newLecture.courseId).post(newLecture);
    };

    this.getLecturesCap = function (courseId) {
        return [
            {'id': 1, 'topic': 'Lection 1'},
            {'id': 2, 'topic': 'Lection 2'}
        ];
    };
}