package boris.home.project.andromeda.configs;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {
  private final MyAccessDeniedHandler accessDeniedHandler;

  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  //  @Override
  //  protected void configure(final HttpSecurity http) throws Exception {
  //    http.csrf().disable()
  //        .formLogin().disable()
  //        .logout().disable()
  //        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  //        .and()
  //        .httpBasic();
  //  }
  @Override
  public void configure(WebSecurity security) {
    security.ignoring().antMatchers("/css/**", "/images/**", "/js/**", "/webjars/**");
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/", "/home", "/about", "/css/**").permitAll()
        .antMatchers("/admin/**").hasAnyRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
        .loginPage("/login")
        .permitAll()
        .and()
        .logout()
        .permitAll()
        .and()
        //          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //          .and()
        .exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler);
    //          .authenticationEntryPoint(new AuthenticationProcessingFilterEntryPoint("/login"));
  }

  //  public class AuthenticationProcessingFilterEntryPoint extends LoginUrlAuthenticationEntryPoint {
  //    public AuthenticationProcessingFilterEntryPoint(String loginFormUrl) {
  //      super(loginFormUrl);
  //    }
  //
  //    @Override
  //    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response,
  //        AuthenticationException exception) {
  //      String url = super.determineUrlToUseForThisRequest(request, response, exception);
  //
  //      final String queryString = request.getQueryString();
  //      if(queryString != null) {
  //        return url + "?" + queryString;
  //      } else {
  //        return url;
  //      }
  //    }
  //  }

  // create two users, admin and user
  //  @Autowired
  //  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  //
  //    auth.inMemoryAuthentication()
  //        .withUser("user").password("password").roles("USER")
  //        .and()
  //        .withUser("admin").password("password").roles("ADMIN");
  //  }
}
