package ai.budding.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ai.budding.models.jpa.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Role findByTitle(String title);
}
