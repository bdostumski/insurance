package com.insurance.application.config;

import com.insurance.application.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {




//    @Order(1)
//    @Configuration
//    public static class RestConfiguration extends WebSecurityConfigurerAdapter {
//
//        @Autowired
//        CustomUserDetailsService customUserDetailsService;
//
//        @Autowired
//        private JwtAuthenticationEntryPoint unauthorizedHandler;
//
//        @Bean
//        public JwtAuthenticationFilter jwtAuthenticationFilter() {
//            return new JwtAuthenticationFilter();
//        }
//
//        @Override
//        public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//            authenticationManagerBuilder
//                    .userDetailsService(customUserDetailsService)
//                    .passwordEncoder(passwordEncoder());
//        }
//
//        @Bean(BeanIds.AUTHENTICATION_MANAGER)
//        @Override
//        public AuthenticationManager authenticationManagerBean() throws Exception {
//            return super.authenticationManagerBean();
//        }
//
//        @Bean
//        public PasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .cors()
//                    .and()
//                    .csrf()
//                    .disable()
//                    .exceptionHandling()
//                    .authenticationEntryPoint(unauthorizedHandler)
//                    .and()
//                    .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers("/api/auth/**")
//                    .permitAll()
//                    .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
//                    .permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/users/**")
//                    .permitAll()
//                    .anyRequest()
//                    .authenticated();
//
//            // Add our custom JWT security filter
//            http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        }
//    }






    @Order(2)
    @Configuration
    public static class WebConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        CustomUserDetailsService customUserDetailsService;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customUserDetailsService);

        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/src/**", "/css/**", "/js/**").permitAll()
                    .antMatchers("/", "/sign-up", "/register/user", "/registrationconfirm", "/recoverpassword").permitAll()
                    .antMatchers("/policy").hasAnyRole("ADMIN", "USER")
                    .antMatchers("/total").hasAnyRole("ADMIN", "USER")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/login").permitAll().loginProcessingUrl("/authenticate")
                    .and()
                    .logout().permitAll().logoutUrl("/gologout").logoutSuccessUrl("/login")
                    .and()
                    .httpBasic();
        }

    }
}