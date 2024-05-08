package ai.budding.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ai.budding.models.jpa.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
