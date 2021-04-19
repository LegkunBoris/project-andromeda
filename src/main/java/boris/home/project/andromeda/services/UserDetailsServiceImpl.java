package boris.home.project.andromeda.services;

import boris.home.project.andromeda.models.AuthUser;
import boris.home.project.andromeda.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final Optional<AuthUser> userOpt = userRepository.findByUsername(username);
    if (userOpt.isPresent()) {
      final AuthUser authUser = userOpt.get();
      final List<SimpleGrantedAuthority> roles = authUser.getRoles().stream()
          .map(role -> new SimpleGrantedAuthority(role.getName()))
          .collect(Collectors.toList());
      return new User(authUser.getUsername(), authUser.getPassword(), roles);
    }
    throw new UsernameNotFoundException("User " + username + " not found.");
  }
}
