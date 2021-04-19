package boris.home.project.andromeda.repositories;

import boris.home.project.andromeda.models.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AuthUser, Long> {

  Optional<AuthUser> findByUsername(String username);

  Optional<AuthUser> findByUsernameOrEmail(String username, String email);
}
