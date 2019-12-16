package cf.youngauthentic.consultant.model.user;

import org.springframework.data.rest.core.config.Projection;

@Projection(types = UserEntity.class)
public interface SimpleUser {
    int getUid();

    String getUsername();
}
