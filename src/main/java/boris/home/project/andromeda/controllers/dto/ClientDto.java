package boris.home.project.andromeda.controllers.dto;

import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import java.util.HashMap;
import java.util.HashSet;

public class ClientDto extends BaseClientDetails {

  public ClientDto() {
    setAdditionalInformation(new HashMap<>());
    setResourceIds(new HashSet<>());
  }
}
