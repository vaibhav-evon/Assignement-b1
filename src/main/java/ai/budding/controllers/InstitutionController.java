package ai.budding.controllers;

import org.springframework.web.bind.annotation.RestController;

import ai.budding.dto.InstitutionDto;
import ai.budding.models.jpa.Institution;
import ai.budding.services.InstitutionService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequestMapping("/institution")
@RestController
public class InstitutionController {

    private final InstitutionService institutionService;

    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Institution> allInstitution(@PathVariable UUID Id) {
        try {
            Optional<Institution> optionalData = institutionService.get(Id);
            return ResponseEntity.ok(optionalData.get());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<Institution>> allInstitution() {
        try {
            List<Institution> lInstitutions = institutionService.getALLInstitution();
            return ResponseEntity.ok(lInstitutions);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Institution> createInstitution(@RequestBody InstitutionDto institutionDto) {
        Institution institution = institutionService.saveOrUpdateInstitution(institutionDto);
        return ResponseEntity.ok(institution);
    }

    @PutMapping()
    public ResponseEntity<Institution> updateInstitution(@RequestBody InstitutionDto institutionDto) {
        Institution institution = institutionService.saveOrUpdateInstitution(institutionDto);
        return ResponseEntity.ok(institution);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteInstitution(@PathVariable UUID Id) {
        try {
            Boolean deleteValue = institutionService.deleteInstitution(Id);
            return (deleteValue ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
