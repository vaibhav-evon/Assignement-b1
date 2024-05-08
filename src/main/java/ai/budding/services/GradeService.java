package ai.budding.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ai.budding.dto.GradeDto;
import ai.budding.repositories.GradeRepository;
import ai.budding.repositories.InstitutionRepository;
import ai.budding.repositories.VirtualClassRepository;
import ai.budding.models.jpa.Grade;
import ai.budding.models.jpa.Institution;
import ai.budding.models.jpa.VirtualClass;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final InstitutionRepository institutionRepository;
    private final VirtualClassRepository virtualClassRepository;

    public GradeService(GradeRepository gradeRepository,
            InstitutionRepository institutionRepository,
            VirtualClassRepository virtualClassRepository) {
        this.gradeRepository = gradeRepository;
        this.institutionRepository = institutionRepository;
        this.virtualClassRepository = virtualClassRepository;
    }

    public List<GradeDto> getListOfGrades() {
        List<Grade> grades = gradeRepository.findAll();
        List<GradeDto> result = new ArrayList<>();
        grades.forEach(grade -> {
            GradeDto gradeDto = new GradeDto();
            gradeDto.setTitle(grade.getTitle());
            gradeDto.setDescritpion(grade.getDescritpion());
            gradeDto.setInstitutionId(grade.getInstitution().getId());
            gradeDto.setVirtualId(grade.getVirtualClass().getId());
            result.add(gradeDto);
        });
        return result;
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

            Optional<Institution> optionIns = institutionRepository.findById(gradeDto.getInstitutionId());
            grade.setInstitution(optionIns.isPresent() ? optionIns.get() : null);

            Optional<VirtualClass> optionVirtualClass = virtualClassRepository.findById(gradeDto.getVirtualId());
            grade.setVirtualClass(optionVirtualClass.isPresent() ? optionVirtualClass.get() : null);

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
