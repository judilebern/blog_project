package blog_project.config;

import blog_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableGlobalMethodSecurity(
		prePostEnabled = true,
		securedEnabled = true,
		jsr250Enabled = true)
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

		http.authorizeRequests()
				.antMatchers("/registration").permitAll()
				.antMatchers("/mainPage/**").permitAll()
				.antMatchers(staticFiles).permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.defaultSuccessUrl("/mainPage")
				.and()
				.logout()
				.permitAll()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/mainPage");

		http.csrf().disable();
		http.headers().frameOptions().disable();

		return http.build();
	}
	@Autowired
	public void configure(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
		auth.userDetailsService(userService)
				.passwordEncoder(NoOpPasswordEncoder.getInstance());
				/*.passwordEncoder(passwordEncoder);*/
	}
}