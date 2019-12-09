package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.TeachesEntity;
import cf.youngauthentic.consultant.model.UserEntity;
import cf.youngauthentic.consultant.repo.TeachesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeachesService {

    @Autowired
    UserService userService;

    @Autowired
    TeachesRepo teachesRepo;


    public void addTeacher(int did, int cid, int uid) {
        TeachesEntity teachesEntity = new TeachesEntity();
        teachesEntity.setUid(uid);
        teachesEntity.setDepartmentId(did);
        teachesEntity.setCourseId(cid);
        teachesRepo.save(teachesEntity);
    }

    @Transactional
    public void addTeachers(int did, int cid, List<Integer> uids) {
        List<TeachesEntity> teachers = new ArrayList<>();
        for (int uid : uids) {
            teachers.add(new TeachesEntity(did, cid, uid));
        }
        teachesRepo.saveAll(teachers);
    }

    public void addTeacher(int did, int cid, String username) {
        int uid = userService.getUser(username).getUid();
        addTeacher(did, cid, uid);
    }

    public List<UserEntity> getTeachers(int did, int cid) {
        List<TeachesEntity> teachesEntities = teachesRepo.findAllByDepartmentIdAndCourseId(did, cid);
        List<UserEntity> teachers = new ArrayList<>();
        teachesEntities.forEach(it -> teachers.add(it.getTeacher()));
        return teachers;
    }
}
