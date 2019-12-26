package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.course.CourseEntity;
import cf.youngauthentic.consultant.model.course.CourseWithTeachers;
import cf.youngauthentic.consultant.model.course.CourseWithoutTeachers;
import cf.youngauthentic.consultant.repo.CourseRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepo courseRepo;

    private final LoginService loginService;

    public CourseService(CourseRepo courseRepo, LoginService loginService) {
        this.courseRepo = courseRepo;
        this.loginService = loginService;
    }

    public List<CourseWithoutTeachers> getCourses(int departmentId, String token, int page) throws AuthException {
        loginService.hasAuth(token, Auth.STUDENT);
        return courseRepo.findAllByDepartmentId(departmentId, PageRequest.of(page, 10, Sort.by("courseId").ascending()));
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

    @Transactional
    public Boolean deleteCourse(int departmentId, int courseId, String token) throws AuthException {
        loginService.hasAuth(token, Auth.ADMIN);
        courseRepo.deleteByDepartmentIdAndCourseId(departmentId, courseId);
        return true;
    }
}
