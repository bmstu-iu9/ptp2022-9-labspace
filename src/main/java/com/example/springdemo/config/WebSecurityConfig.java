package com.example.springdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    DataSource dataSource;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/auth/**").not().fullyAuthenticated()
                    .antMatchers("/admin/**", "/main/**").hasAuthority("ADMIN")
                    .antMatchers("/user/**", "/main/**").hasAuthority("USER") // тут - для юзера
                    .antMatchers("/error").permitAll()
                .and()
                    .formLogin()
                    .loginPage("/auth/login")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/", true)
                    .failureUrl("/login-error")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll()
                    .logoutSuccessUrl("/auth/login")
                .and()
                    .cors()
                .and()
                    .csrf()
                    .disable();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
       return(web) -> web.ignoring()
                .antMatchers(
                        "/css/**", "/fonts/**", "/font-awesome/**", "/js/**",
                        "/img/**");
    }

      @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder)
                .usersByUsernameQuery("select email,password,active from users where email=?")
                .authoritiesByUsernameQuery("select users.email,role.roles from users  inner join role  on users.user_id=role.user_id where users.email=?");
    }

}

