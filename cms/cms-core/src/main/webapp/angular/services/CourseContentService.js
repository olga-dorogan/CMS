/**
 * Created by olga on 25.06.15.
 */
function CourseContentService(Restangular) {
    var restBase = 'resources/course';

    var createLectureRest = function (courseId) {
        return Restangular.one(restBase, courseId).all('lesson');
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

    this.getLecture = function(courseId, lectureOrderNum) {
        return Restangular.one(restBase, courseId).one('lesson', lectureOrderNum).get();
    };
}