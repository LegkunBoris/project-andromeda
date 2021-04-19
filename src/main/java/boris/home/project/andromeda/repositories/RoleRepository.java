package boris.home.project.andromeda.repositories;

import boris.home.project.andromeda.models.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<AuthRole, Long> {

  AuthRole findByNameContaining(String name);
}
