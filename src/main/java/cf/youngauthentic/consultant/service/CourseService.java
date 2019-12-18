package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.course.CourseEntity;
import cf.youngauthentic.consultant.model.course.CourseWithTeachers;
import cf.youngauthentic.consultant.model.course.CourseWithoutTeachers;
import cf.youngauthentic.consultant.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private LoginService loginService;

    public List<CourseWithoutTeachers> getCourses(int departmentId, String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        return courseRepo.findAllByDepartmentId(departmentId);
    }

//    public List<UserEntity> getTeachers(int departmentId, int courseId) {
//        List<UserEntity> teachers = new ArrayList<>();
//        List<TeachesEntity> temp = teachesRepo.findAllByDepartmentId(departmentId);
//        temp.forEach(it -> teachers.add(it->));
//        return teachers;
//    }

    public CourseWithTeachers getCourseWithTeachers(int departmentId, int courseId, String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        return courseRepo.findByDepartmentIdAndCourseId(departmentId, courseId);
    }

    public Boolean addCourse(int departmentId, String courseName, String token) throws AuthException {
        loginService.hasAuth(token, Auth.ADMIN);
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCname(courseName);
        courseEntity.setDepartmentId(departmentId);
        return true;
    }

    public int countCourses(int departmentId, String token) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        return courseRepo.countAllByDepartmentId(departmentId);
    }
}
