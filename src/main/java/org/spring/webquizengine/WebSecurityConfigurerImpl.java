package org.spring.webquizengine;

import lombok.RequiredArgsConstructor;
import org.spring.webquizengine.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
    private final UserService userService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService) // user store 1
                .passwordEncoder(getEncoder());

        auth
                .inMemoryAuthentication() // user store 2
                .withUser("Admin").password("hardcoded").roles("USER")
                .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("api/v1/user").permitAll()
                .mvcMatchers("api/quizzes").authenticated()
                .anyRequest().authenticated()// make remaining endpoints public (including POST /register)
                .and()
                .csrf().disable() // disabling CSRF will allow sending POST request using Postman
                .httpBasic();
        http.headers().frameOptions().disable();// enables basic auth.
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
