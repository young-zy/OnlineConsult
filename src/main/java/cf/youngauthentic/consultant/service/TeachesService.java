package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.teaches.TeachesEntity;
import cf.youngauthentic.consultant.model.user.UserEntity;
import cf.youngauthentic.consultant.repo.TeachesRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeachesService {

    private final
    UserService userService;

    private final
    TeachesRepo teachesRepo;

    public TeachesService(UserService userService, TeachesRepo teachesRepo) {
        this.userService = userService;
        this.teachesRepo = teachesRepo;
    }


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
