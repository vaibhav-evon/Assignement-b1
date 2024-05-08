package ai.budding.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ai.budding.dto.InstitutionDto;
import ai.budding.repositories.InstitutionRepository;
import ai.budding.models.jpa.Institution;

@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public List<Institution> getALLInstitution() {
        return institutionRepository.findAll();
    }

    public Institution saveOrUpdateInstitution(InstitutionDto institutionDto) {
        Institution institution = new Institution();
        try {
            if (institutionDto != null && institutionDto.getId() != null) {
                Optional<Institution> ins = get(institutionDto.getId());
                if (ins.isPresent()) {
                    institution = ins.get();
                } else {
                    institution.setCreatedOn(new Date());
                }
            } else {
                institution.setCreatedOn(new Date());
            }

            institution.setTitle(institutionDto.getTitle());
            institution.setDescritpion(institutionDto.getDescritpion());
            institution.setModifiedOn(new Date());
            return institutionRepository.save(institution);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Optional<Institution> get(UUID Id) {
        return institutionRepository.findById(Id);
    }

    public Boolean deleteInstitution(UUID Id) {
        Optional<Institution> optionalInstitution = get(Id);
        if (optionalInstitution.isPresent()) {
            institutionRepository.delete(optionalInstitution.get());
            return true;
        }
        return false;
    }
}
