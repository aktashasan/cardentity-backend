package com.example.cardentity;

import com.example.cardentity.user.authentication.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationService authenticationService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                //username and psw don't need any authentication (we provide this with OPTIONS)
                .antMatchers(HttpMethod.OPTIONS,"/api/**").permitAll()
                // but other requests need authentication
                .antMatchers("/api/**").authenticated()

                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationService);
    }
}
