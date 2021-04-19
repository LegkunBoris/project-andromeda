package boris.home.project.andromeda.configs;

import boris.home.project.andromeda.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  private final AuthenticationManager authenticationManager;

  private final JdbcTemplate jdbcTemplate;

  private final TokenStore tokenStore;

  private final JwtAccessTokenConverter jwtAccessTokenConverter;

  private final UserDetailsServiceImpl userDetailsService;

  @Override
  public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {
    security.tokenKeyAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
        .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
  }

  @Override
  public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
    clients.jdbc(jdbcTemplate.getDataSource());
  }

  @Override
  public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.tokenStore(tokenStore)
        .reuseRefreshTokens(false)
        .accessTokenConverter(jwtAccessTokenConverter)
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService);
  }
}
