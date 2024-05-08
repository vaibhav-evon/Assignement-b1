package ai.budding.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ai.budding.dto.SectionDto;
import ai.budding.repositories.SectionRepository;
import ai.budding.models.jpa.Section;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public List<Section> getListOfSections() {
        return sectionRepository.findAll();
    }

    public Section saveOrUpdateSection(SectionDto sectionDto) {
        Section section = new Section();
        try {
            if (sectionDto != null && sectionDto.getId() != null) {
                Optional<Section> optionalSection = get(sectionDto.getId());
                if (optionalSection.isPresent()) {
                    section = optionalSection.get();
                } else {
                    section.setCreatedOn(new Date());
                }
            } else {
                section.setCreatedOn(new Date());
            }

            section.setTitle(sectionDto.getTitle());
            section.setDescritpion(sectionDto.getDescritpion());
            section.setModifiedOn(new Date());
            return sectionRepository.save(section);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Optional<Section> get(UUID Id) {
        return sectionRepository.findById(Id);
    }

    public Boolean deleteInstitution(UUID Id) {
        Optional<Section> optionalSection = get(Id);
        if (optionalSection.isPresent()) {
            sectionRepository.delete(optionalSection.get());
            return true;
        }
        return false;
    }
}
