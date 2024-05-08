package ai.budding.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import ai.budding.models.jpa.Institution;

public interface InstitutionRepository extends JpaRepository<Institution, UUID> {

}
