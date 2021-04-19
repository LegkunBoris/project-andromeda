package boris.home.project.andromeda.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "oauth_roles")
public class AuthRole extends BaseEntity {
  private String name;

  private String description;
}
