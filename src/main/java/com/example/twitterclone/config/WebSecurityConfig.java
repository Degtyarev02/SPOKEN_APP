package com.example.twitterclone.config;

import com.example.twitterclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserService userService;

	@Autowired
	@Lazy
	PasswordEncoder passwordEncoder;

	@Bean
	public PasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder(8);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()//Отключаем поддержку csrf
				.authorizeRequests() //Запрос на вход
				.antMatchers("/", "/registration", "/static/**").permitAll() //Какие страницы могут быть доступны без авторизации
				.antMatchers("/admin/**").hasAuthority("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin() //Форма логина
				.loginPage("/login")//путь до страницы логина
				.defaultSuccessUrl("/main", false)
				.failureForwardUrl("/")
				.permitAll()
				.and()
				.logout()
				.logoutSuccessUrl("/")
				.permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}
}
