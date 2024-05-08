package ai.budding.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.budding.models.jpa.User;
import ai.budding.services.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{Id}")
    public ResponseEntity<User> allInstitution(@PathVariable UUID Id) {
        try {
            Optional<User> optionalData = userService.get(Id);
            return ResponseEntity.ok(optionalData.get());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<User>> allInstitution() {
        try {
            List<User> lInstitutions = userService.getListOfUsers();
            return ResponseEntity.ok(lInstitutions);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<User> createInstitution(@RequestBody com.ai.budding.models.User userDto) {
        User user = userService.saveOrUpdateSection(userDto);
        return ResponseEntity.ok(user);
    }

    @PutMapping()
    public ResponseEntity<User> updateInstitution(@RequestBody com.ai.budding.models.User userDto) {
        User user = userService.saveOrUpdateSection(userDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteInstitution(@PathVariable UUID Id) {
        try {
            Boolean deleteValue = userService.deleteInstitution(Id);
            return (deleteValue ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
