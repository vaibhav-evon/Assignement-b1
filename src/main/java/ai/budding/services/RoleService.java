package ai.budding.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ai.budding.dto.RoleDto;
import ai.budding.repositories.RoleRepository;
import ai.budding.models.jpa.Role;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getListOfRoles() {
        return roleRepository.findAll();
    }

    public Role saveOrUpdateSection(RoleDto roleDto) {
        Role role = new Role();
        try {
            if (roleDto != null && roleDto.getId() != null) {
                Optional<Role> optionalSection = get(roleDto.getId());
                if (optionalSection.isPresent()) {
                    role = optionalSection.get();
                } else {
                    role.setCreatedOn(new Date());
                }
            } else {
                role.setCreatedOn(new Date());
            }

            role.setTitle(roleDto.getTitle());
            role.setModifiedOn(new Date());
            return roleRepository.save(role);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Optional<Role> get(UUID Id) {
        return roleRepository.findById(Id);
    }

    public Boolean deleteInstitution(UUID Id) {
        Optional<Role> optionalSection = get(Id);
        if (optionalSection.isPresent()) {
            roleRepository.delete(optionalSection.get());
            return true;
        }
        return false;
    }
}
