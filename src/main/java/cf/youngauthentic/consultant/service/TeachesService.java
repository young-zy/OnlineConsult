package cf.youngauthentic.consultant.service;

import cf.youngauthentic.consultant.model.TeachesEntity;
import cf.youngauthentic.consultant.repo.TeachesRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class TeachesService {

    @Autowired
    UserService userService;

    @Autowired
    TeachesRepo teachesRepo;


    void addTeacher(int did, int cid, int uid) {
        TeachesEntity teachesEntity = new TeachesEntity();
        teachesEntity.setUid(uid);
        teachesEntity.setDepartmentId(did);
        teachesEntity.setCourseId(cid);
        teachesRepo.save(teachesEntity);
    }

    void addTeacher(int did, int cid, String username) {
        int uid = userService.getUser(username).getUid();
        addTeacher(did, cid, uid);
    }


}
