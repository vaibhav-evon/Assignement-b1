package ai.budding.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import ai.budding.models.jpa.Section;

public interface SectionRepository extends JpaRepository<Section, UUID> {

}
