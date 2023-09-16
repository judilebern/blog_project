package blog_project.config;

import blog_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	private final UserService userService;

	public WebSecurityConfig(UserService userService) {
		this.userService = userService;
	}

	private final String[] staticFiles = {"/css/**", "/images/**", "/js/**"};


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception, RuntimeException {


		http.authorizeHttpRequests((authz) -> {
					try {
						authz
								.requestMatchers("/registration/**").permitAll()
								.requestMatchers("/custom/**").permitAll()
								.requestMatchers(staticFiles).permitAll()
								.requestMatchers("/mainPage/**").permitAll()
								.anyRequest()
								.authenticated()
								.and()
								.formLogin()
/*								.loginPage("/login")*/
								.defaultSuccessUrl("/mainPage", true)
								.and()
								.logout()
								.permitAll();
/*								.loginPage("/custom-login")// Specify the custom login page URL
								.permitAll();*/
								/*.and()
								.logout()
								.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
								.logoutUrl("/login");*/
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
		);

		http.csrf().disable();
		http.headers().frameOptions().disable();

		return http.build();
	}
	@Autowired
	public void configure(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
		auth.userDetailsService(userService)
				.passwordEncoder(NoOpPasswordEncoder.getInstance()); //.passwordEncoder(passwordEncoder);
	}
}