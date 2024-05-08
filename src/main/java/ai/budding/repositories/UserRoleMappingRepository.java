package ai.budding.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ai.budding.models.jpa.UserRoleMapping;

public interface UserRoleMappingRepository extends JpaRepository<UserRoleMapping, UUID> {
    UserRoleMapping findByUserId(UUID userId);
}
