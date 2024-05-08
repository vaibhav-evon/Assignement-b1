package ai.budding.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.budding.dto.SectionDto;
import ai.budding.models.jpa.Section;
import ai.budding.services.SectionService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequestMapping("/section")
@RestController
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Section> allInstitution(@PathVariable UUID Id) {
        try {
            Optional<Section> optionalData = sectionService.get(Id);
            return ResponseEntity.ok(optionalData.get());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<Section>> allInstitution() {
        try {
            List<Section> lInstitutions = sectionService.getListOfSections();
            return ResponseEntity.ok(lInstitutions);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Section> createInstitution(@RequestBody SectionDto sectionDto) {
        Section section = sectionService.saveOrUpdateSection(sectionDto);
        return ResponseEntity.ok(section);
    }

    @PutMapping()
    public ResponseEntity<Section> updateInstitution(@RequestBody SectionDto sectionDto) {
        Section section = sectionService.saveOrUpdateSection(sectionDto);
        return ResponseEntity.ok(section);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteInstitution(@PathVariable UUID Id) {
        try {
            Boolean deleteValue = sectionService.deleteInstitution(Id);
            return (deleteValue ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
