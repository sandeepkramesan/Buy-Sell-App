package com.project.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.filter.JWTAuthenticationFilter;
import com.project.filter.JWTAuthorizationFilter;
import com.project.service.AuthenticationUserDetailService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationUserDetailService authenticationUserDetailService;
    
    @Override 
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
        	.antMatchers("/").permitAll()
        	.antMatchers("/user/register/**").permitAll()
            .antMatchers("/user/all").permitAll()
            .antMatchers("/user/{name}/products").hasAuthority("ADMIN")
            .antMatchers("/user/passwordupdate").permitAll()
            .antMatchers("/product/add").permitAll()
            .antMatchers("/product/all").hasAnyAuthority("USER", "ADMIN")
            .antMatchers("/product/{name}").hasAuthority("ADMIN")
            .antMatchers("/product/update").permitAll()
            .antMatchers("/product/buy").permitAll()
            .antMatchers("/orders/all").hasAuthority("ADMIN")
            .antMatchers("/orders/order/{id}").hasAuthority("ADMIN")
            .antMatchers("/orders/username/{name}").hasAuthority("ADMIN")
            .antMatchers("/orders/product/{name}").hasAuthority("ADMIN")
            .antMatchers("/orders/add/**").hasAnyAuthority("USER","ADMIN")
            .anyRequest().authenticated()
            .and()
            .addFilter(new JWTAuthenticationFilter(authenticationManager()))
            .addFilter(new JWTAuthorizationFilter(authenticationManager()))
            // this disables session creation on Spring Security
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationUserDetailService).passwordEncoder(bCryptPasswordEncoder);
    }
}