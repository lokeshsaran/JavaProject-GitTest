package com.springbootassignment.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	PasswordEncoder passwordEncoder;

	/*
	 * @Autowired public void configAuthentication(AuthenticationManagerBuilder
	 * auth) throws Exception { auth.jdbcAuthentication().dataSource(dataSource); }
	 */

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * auth.inMemoryAuthentication().passwordEncoder(passwordEncoder).withUser(
		 * "user")
		 * .password(passwordEncoder.encode("123456")).roles("USER").and().withUser(
		 * "admin") .password(passwordEncoder.encode("123456")).roles("USER", "ADMIN");
		 */
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
				.usersByUsernameQuery(
						"select USER_NAME,PASSWORD, 'true' as enabled from USER_DETAIL where USER_NAME = ?")
				.authoritiesByUsernameQuery(
						"select USER_NAME, 'ADMIN' as authority from USER_DETAIL where USER_NAME = ?");
	}

	/*
	 * @Bean public PasswordEncoder passwordEncoder() { return new
	 * BCryptPasswordEncoder(); }
	 */

	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/**").hasAuthority("ADMIN").and()
				.formLogin().loginPage("/login").defaultSuccessUrl("/home").failureUrl("/login?error=true").permitAll()
				.and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll().and()
				.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-ui/**", "/api/**", "/signUp", "/signUpSubmit");
	}

}
