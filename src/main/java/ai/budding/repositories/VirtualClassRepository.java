package ai.budding.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ai.budding.models.jpa.VirtualClass;
import java.util.Optional;

public interface VirtualClassRepository extends JpaRepository<VirtualClass, UUID> {
    Optional<VirtualClass> findById(UUID id);
}
