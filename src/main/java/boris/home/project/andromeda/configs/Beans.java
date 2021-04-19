package boris.home.project.andromeda.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import java.util.Objects;

@Configuration
public class Beans {

  @Bean
  public TokenStore tokenStore(JdbcTemplate jdbcTemplate) {
    return new JdbcTokenStore(Objects.requireNonNull(jdbcTemplate.getDataSource()));
  }

  @Bean
  public JdbcClientDetailsService jdbcClientDetailsService(
      JdbcTemplate jdbcTemplate,
      BCryptPasswordEncoder encoder
  ) {
    final JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(
        jdbcTemplate.getDataSource());
    jdbcClientDetailsService.setPasswordEncoder(encoder);
    return jdbcClientDetailsService;
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DefaultTokenServices tokenServices(TokenStore tokenStore) {
    final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore);
    defaultTokenServices.setSupportRefreshToken(true);
    return defaultTokenServices;
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    jwtAccessTokenConverter.setSigningKey("<signedKey>");
    return jwtAccessTokenConverter;
  }
}
