package com.excilys.computerdatabase;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	HikariDataSource dataSource;

	public SpringSecurityConfig(HikariDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
				"select username,password, enabled from users where username = ?")
		.authoritiesByUsernameQuery(
				"select username, role from user_roles where username=?")
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/AddComputer", "/addComputer", "/addcomputer" ).hasAuthority("ADMIN")
		.antMatchers("/editcomputer", "/editComputer", "/EditComputer").hasAuthority("ADMIN")
		.antMatchers("/deleteComputer", "/deletecomputer", "/Deletecomputer").hasAuthority("ADMIN")
		.antMatchers("/", "/dashboard", "/dashBoard", "/Dashboard", "/DashBoard").authenticated()
		.antMatchers("/loginProcess").permitAll()
		.and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/loginProcess")
		.usernameParameter("username")
		.passwordParameter("password")
		.defaultSuccessUrl("/dashboard")
		.failureForwardUrl("/login?error=true")
		.and()
		.logout()
		.logoutSuccessUrl("/login?logout=true")
		.logoutUrl("/logoutProcess");
	}
}