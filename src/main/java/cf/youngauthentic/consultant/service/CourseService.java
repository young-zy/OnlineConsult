package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.CourseEntity;
import cf.youngauthentic.consultant.model.CourseEntityPK;
import cf.youngauthentic.consultant.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepo courseRepo;

    public List<CourseEntity> getCourses(int departmentId) {
        return courseRepo.findAllByDepartmentId(departmentId);
    }

    public Optional<CourseEntity> getCourse(int did, int cid) {
        CourseEntityPK pk = new CourseEntityPK(did, cid);
        return courseRepo.findById(pk);
    }
}
