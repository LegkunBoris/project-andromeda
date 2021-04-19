package boris.home.project.andromeda.controllers;

import boris.home.project.andromeda.controllers.dto.UserDto;
import boris.home.project.andromeda.models.AuthUser;
import boris.home.project.andromeda.models.Status;
import boris.home.project.andromeda.repositories.RoleRepository;
import boris.home.project.andromeda.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/oauth/users")
public class UserController {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final BCryptPasswordEncoder passwordEncoder;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AuthUser register(@RequestBody UserDto userDto) {
    final AuthUser authUser = new ObjectMapper().convertValue(userDto, AuthUser.class);
    authUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
    authUser.setRoles(Collections.singletonList(roleRepository.findByNameContaining("USER")));
    authUser.setStatus(Status.ACTIVE);
    final Optional<AuthUser> userOpt = userRepository
        .findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
    if (!userOpt.isPresent()) {
      return userRepository.save(authUser);
    }
    throw new RuntimeException(
        "User " + userDto.getUsername() + "[" + userDto.getEmail() + "] already exist.");
  }
}
