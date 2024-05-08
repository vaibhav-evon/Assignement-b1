package ai.budding.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import ai.budding.models.jpa.Grade;

public interface GradeRepository extends JpaRepository<Grade, UUID> {

}
