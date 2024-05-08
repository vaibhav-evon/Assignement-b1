package ai.budding.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.budding.dto.GradeDto;
import ai.budding.models.jpa.Grade;
import ai.budding.services.GradeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequestMapping("/grade")
@RestController
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Grade> allInstitution(@PathVariable UUID Id) {
        try {
            Optional<Grade> optionalData = gradeService.get(Id);
            return ResponseEntity.ok(optionalData.get());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<Grade>> allInstitution() {
        try {
            List<Grade> lInstitutions = gradeService.getListOfGrades();
            return ResponseEntity.ok(lInstitutions);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Grade> createInstitution(@RequestBody GradeDto gradeDto) {
        Grade grade = gradeService.saveOrUpdateGrade(gradeDto);
        return ResponseEntity.ok(grade);
    }

    @PutMapping()
    public ResponseEntity<Grade> updateInstitution(@RequestBody GradeDto gradeDto) {
        Grade grade = gradeService.saveOrUpdateGrade(gradeDto);
        return ResponseEntity.ok(grade);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteInstitution(@PathVariable UUID Id) {
        try {
            Boolean deleteValue = gradeService.deleteInstitution(Id);
            return (deleteValue ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
