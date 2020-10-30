package com.insurance.application.config;

import com.insurance.application.security.CustomUserDetailsService;
import com.insurance.application.security.jwt.JwtAuthenticationEntryPoint;
import com.insurance.application.security.jwt.JwtRequestFilter;
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

import static com.insurance.application.security.SecurityConstants.SIGN_UP_URL;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }



    @Order(1)
    @Configuration
    public static class RestConfiguration extends WebSecurityConfigurerAdapter {


        @Autowired
        CustomUserDetailsService customUserDetailsService;

        @Autowired
        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


        @Autowired
        private JwtRequestFilter jwtRequestFilter;



        @Override
        public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
            authenticationManagerBuilder
                    .userDetailsService(customUserDetailsService);
        }

        @Bean(BeanIds.AUTHENTICATION_MANAGER)
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity restHttp) throws Exception {
            restHttp.antMatcher("/v.1.0/api/**");
            restHttp.cors().and().csrf().disable().authorizeRequests()
                    .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                    .antMatchers("/v.1.0/api/car/**").permitAll()
                    .and()
                    .authorizeRequests().antMatchers("/v.1.0/api/user/authentication").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


            // Add a filter to validate the tokens with every request
            restHttp.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        }

    }



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
                    .antMatchers("/", "/total", "/offer", "/sign-up", "/register/user", "/brandmodels",
                            "/registrationconfirm", "/recoverpassword", "/passwordreset/user", "/passress",
                            "/register", "/successful-registratoin", "/image/**").permitAll()
                    .antMatchers("/policy").hasAnyRole("ADMIN", "USER", "AGENT")
                    .antMatchers("/user-filter").hasAnyRole("USER")
                    .antMatchers("/agent-filter").hasAnyRole("AGENT")
                    .antMatchers("/admin-filter").hasAnyRole("ADMIN")

                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/login").permitAll().loginProcessingUrl("/authenticate").defaultSuccessUrl("/")
                    .and()
                    .logout().logoutUrl("/logout").logoutSuccessUrl("/").clearAuthentication(true).invalidateHttpSession(true)
                    .and()
                    .httpBasic();
        }

    }



}