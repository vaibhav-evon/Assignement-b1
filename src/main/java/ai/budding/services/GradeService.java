package ai.budding.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ai.budding.dto.GradeDto;
import ai.budding.repositories.GradeRepository;
import ai.budding.models.jpa.Grade;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public List<Grade> getListOfGrades() {
        return gradeRepository.findAll();
    }

    public Grade saveOrUpdateGrade(GradeDto gradeDto) {
        Grade grade = new Grade();
        try {
            if (gradeDto != null && gradeDto.getId() != null) {
                Optional<Grade> optionalGrade = get(gradeDto.getId());
                if (optionalGrade.isPresent()) {
                    grade = optionalGrade.get();
                } else {
                    grade.setCreatedOn(new Date());
                }
            } else {
                grade.setCreatedOn(new Date());
            }

            grade.setTitle(gradeDto.getTitle());
            grade.setDescritpion(gradeDto.getDescritpion());
            grade.setModifiedOn(new Date());
            return gradeRepository.save(grade);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Optional<Grade> get(UUID Id) {
        return gradeRepository.findById(Id);
    }

    public Boolean deleteInstitution(UUID Id) {
        Optional<Grade> optionalGrade = get(Id);
        if (optionalGrade.isPresent()) {
            gradeRepository.delete(optionalGrade.get());
            return true;
        }
        return false;
    }
}
