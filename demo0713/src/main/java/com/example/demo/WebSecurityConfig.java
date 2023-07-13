 package com.example.demo;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.demo.entities.Member;
import com.example.demo.repositories.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

       
       
        
        @Bean
        public PasswordEncoder encoder() {
                return new BCryptPasswordEncoder();
        }

        //private JwtTokenUtil jwtTokenUtil;

     
    
        // @Bean
        // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //         System.out.println("0000");
        //         return http
        //                 //.csrf(csrf -> csrf.disable()) 
        //                 .authorizeHttpRequests((authorize) -> authorize
        //                 .requestMatchers("/register").permitAll()
        //                 .requestMatchers("/Member/**").hasAuthority("user")
        //                 .anyRequest().authenticated()
                        
        //                 )
        //                 // .authorizeRequests( auth -> auth
                                
        //                 //         .anyRequest().authenticated() 
                                
        //                 // )
        //                 // .securityMatchers((matchers) -> matchers
        //                 // .requestMatchers("/register/")
        //                 // )
                        
        //                 .formLogin(formLogin -> formLogin.loginPage("/login").permitAll())
        //                 //.defaultSuccessUrl("/")
        //                 // .successHandler(new CustomAuthenticationSuccessHandler(){
        //                 //.addFilterBefore(new AuthorizationCheckFilter(), BasicAuthenticationFilter.class)     
                                
        //                 // })
        //                 //.formLogin(null)
        //                 .httpBasic(Customizer.withDefaults()) 
        //                 .build();
        //                 // .authorizeRequests()
        //                 // .anyRequest().authenticated()
        //                 // .and()
        //                 // .formLogin(formLogin -> formLogin.loginPage("/login").permitAll())
        //                 // .and()
        //                 // .csrf().disable();          
        // }

        // private final MemberRepository memberRepository;
        // private final AuthorizationCheckFilter jwtTokenFilter;
        // @Autowired
        // public WebSecurityConfig(MemberRepository userRepo,AuthorizationCheckFilter jwtTokenFilter) {
        //     this.memberRepository = userRepo;
        //     this.jwtTokenFilter = jwtTokenFilter;
        // }
    
       
       
        
         
        // @Autowired
        // public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //         // auth.userDetailsService(username -> memberRepository
        //         // .findByUsername(username))
        //         // .orElseThrow(
        //         //     () -> new UsernameNotFoundException(
        //         //         format("User: %s, not found", username)
        //         //     )
        //         // ));
    
        // }



        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
               
                return http
                        .cors()
                        .and()        
                        .csrf(csrf -> csrf.disable()) 
                        
                        // .authorizeHttpRequests((authorize) -> authorize
                        // .requestMatchers("/register").permitAll()
                        // .requestMatchers("/Member/**").hasAuthority("user")
                        // .anyRequest().authenticated())
                        //.formLogin(formLogin -> formLogin.loginPage("/login").permitAll())
                        
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        // .and()

                        .addFilterBefore(new AuthorizationCheckFilter(), BasicAuthenticationFilter.class)
                   
                       
                        //.httpBasic(Customizer.withDefaults()) 
                        .build();
                          
        }
      
     

    
 
    
}
