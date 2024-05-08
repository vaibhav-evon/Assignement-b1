package ai.budding.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.budding.dto.RoleDto;
import ai.budding.models.jpa.Role;
import ai.budding.services.RoleService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequestMapping("/role")
@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Role> allInstitution(@PathVariable UUID Id) {
        try {
            Optional<Role> optionalData = roleService.get(Id);
            return ResponseEntity.ok(optionalData.get());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<Role>> allInstitution() {
        try {
            List<Role> lInstitutions = roleService.getListOfRoles();
            return ResponseEntity.ok(lInstitutions);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Role> createInstitution(@RequestBody RoleDto roleDto) {
        Role role = roleService.saveOrUpdateSection(roleDto);
        return ResponseEntity.ok(role);
    }

    @PutMapping()
    public ResponseEntity<Role> updateInstitution(@RequestBody RoleDto roleDto) {
        Role role = roleService.saveOrUpdateSection(roleDto);
        return ResponseEntity.ok(role);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteInstitution(@PathVariable UUID Id) {
        try {
            Boolean deleteValue = roleService.deleteInstitution(Id);
            return (deleteValue ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
