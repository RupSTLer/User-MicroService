package com.stl.rupam.SchoolWebApp.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.stl.rupam.SchoolWebApp.user.jwt.JwtAuthenticationEntryPoint;
import com.stl.rupam.SchoolWebApp.user.jwt.JwtRequestFilter;


@Configuration
@EnableWebSecurity
@EnableWebMvc                                        //for swagger 
@EnableGlobalMethodSecurity(prePostEnabled = true)   //role-based auth,this annotation will take care of those methods/api endpoints to be exposed only if a user has specific role
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	public static final String[] PUBLIC_URLS = {
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
		
	};
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private UserDetailsService jwtService;    //making child reference
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity.cors().disable();
		httpSecurity.csrf().disable()  //to disable CSRF attacks
					.authorizeRequests().antMatchers("/authenticate","/registerNewUser","/createNewRole","/student/addStudent","/teacher/addTeacher","/getNameOfUser/{username}", "/getAllDetailsByUserName/{username}", "/updateUserDetails/{userId}", "/{userId}").permitAll()
					.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()  //importance one to be specified
					.antMatchers(HttpHeaders.ALLOW).permitAll()
					.antMatchers(PUBLIC_URLS).permitAll()     //for swagger 
					.anyRequest().authenticated()
					.and()
					.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
					.and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	//for encrypting the password
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
	{
		//jwt service that handles jwt related business logic
		authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
	}

}

//https://stackoverflow.com/questions/63862048/getting-error-get-http-localhost8080-hello-variable-user-neterr-failed-on
