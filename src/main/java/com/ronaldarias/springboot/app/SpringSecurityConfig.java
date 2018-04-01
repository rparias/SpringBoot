package com.ronaldarias.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    //autorizaciones http
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //rutas publicas de acceso a todos los usuarios con permitAll
        //solo para USER con hasAnyRole("USER")
        //solo para ADMIN con hasAnyRole("ADMIN")
        http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
                .antMatchers("/ver/**").hasAnyRole("USER")
                .antMatchers("/uploads/**").hasAnyRole("USER")
                .antMatchers("/form/**").hasAnyRole("ADMIN")
                .antMatchers("/eliminar/**").hasAnyRole("ADMIN")
                .antMatchers("/factura/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();

    }

    @Autowired
    void configureGlobal(AuthenticationManagerBuilder build) throws Exception {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User.UserBuilder users = User.builder().passwordEncoder(encoder::encode);

        build.inMemoryAuthentication()
                .withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
                .withUser(users.username("invitado").password("12345").roles("USER"));
    }
}