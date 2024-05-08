package ai.budding.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ai.budding.repositories.RoleRepository;
import ai.budding.repositories.UserRepository;
import ai.budding.repositories.UserRoleMappingRepository;
import ai.budding.models.jpa.Role;
import ai.budding.models.jpa.User;
import ai.budding.models.jpa.UserRoleMapping;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserRoleMappingRepository userRoleMappingRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            UserRoleMappingRepository userRoleMappingRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleMappingRepository = userRoleMappingRepository;
    }

    public List<User> getListOfUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveOrUpdateSection(com.ai.budding.models.User userDto) {
        if (userDto == null || userDto.getUserType() == null) {
            return null;
        }

        Role roleData = roleRepository.findByTitle(userDto.getUserType());

        if (roleData.getId() == null) {
            return null;
        }
        User user = new User();
        try {
            if (userDto.getId() != null) {
                Optional<User> optionalUser = get(userDto.getId());
                if (optionalUser.isPresent()) {
                    user = optionalUser.get();
                } else {
                    user.setCreatedOn(new Date());
                }
            } else {
                user.setCreatedOn(new Date());
            }
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setMobileNumber(userDto.getMobileNumber());
            user.setPassword(userDto.getPassword());
            user.setDob(userDto.getDob());
            user.setPhoto(userDto.getPhoto());
            user.setGender(userDto.getGender());
            user.setState(userDto.getState());
            user.setCity(userDto.getCity());
            user.setCountry(userDto.getCountry());
            user.setZip(userDto.getZip());
            user.setUserType(userDto.getUserType());
            user.setVerified(true);
            user.setModifiedOn(new Date());
            if (userRepository.save(user) != null) {
                UserRoleMapping userRMapping = userRoleMappingRepository
                        .findByUserId(user.getId());
                UserRoleMapping userRoleMapping = null;
                if (userRMapping != null) {
                    userRoleMapping = userRMapping;
                } else {
                    userRoleMapping = new UserRoleMapping();
                    userRoleMapping.setUser(user);
                }
                userRoleMapping.setRole(roleData);
                userRoleMapping.setModifiedOn(new Date());
                userRoleMappingRepository.save(userRoleMapping);
            }
            return user;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Optional<User> get(UUID Id) {
        return userRepository.findById(Id);
    }

    public Boolean deleteInstitution(UUID Id) {
        Optional<User> optionalUser = get(Id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return true;
        }
        return false;
    }
}
