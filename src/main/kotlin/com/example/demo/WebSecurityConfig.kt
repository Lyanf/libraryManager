//package com.example.demo
//
//import com.example.demo.model.UserLog
//import org.apache.tomcat.jni.User
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.provisioning.InMemoryUserDetailsManager
//
//@Configuration
//@EnableWebSecurity
//class WebSecurityConfig: WebSecurityConfigurerAdapter() {
//    override fun configure(http: HttpSecurity) {
//        http.authorizeRequests()
//                .antMatchers("/index").permitAll()
//                .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/index")
//                .permitAll()
//                .and()
//            .logout()
//                .permitAll()
//
//    }
//
//    override fun userDetailsService(): UserDetailsService {
//        var myUserDetails =
//                org.springframework.security.core.userdetails.User
//                        .withUsername("id")
//                        .password("pw")
//                        .roles("user")
//                        .build()
//        return InMemoryUserDetailsManager(myUserDetails)
//    }
//}