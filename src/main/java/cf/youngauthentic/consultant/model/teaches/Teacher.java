package cf.youngauthentic.consultant.model.teaches;

import cf.youngauthentic.consultant.model.user.SimpleUser;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = TeachesEntity.class)
public interface Teacher {
    SimpleUser getTeacher();
}
