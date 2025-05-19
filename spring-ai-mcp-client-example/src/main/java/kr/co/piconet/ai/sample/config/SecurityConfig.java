///**
// * Copyright 2025 Jaeik Shin
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package kr.co.piconet.ai.sample.config;
//
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import jakarta.servlet.DispatcherType;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(securedEnabled = true)
//public class SecurityConfig {
//
//	@Bean
//    WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//                //.requestMatchers("/smarteditor/**");
//    }
//
//	@Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//        	.authorizeHttpRequests((requests) -> requests
//        		.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//         		.requestMatchers("/login").permitAll()
//                .anyRequest().authenticated())
//        	.formLogin(form -> form
//            	.loginPage("/login")
//            	.defaultSuccessUrl("/", true)
//            	.permitAll()
//            )
//            //.httpBasic(Customizer.withDefaults())
//        	.httpBasic(basic -> basic.disable())
//        	.logout(logout -> logout
//        		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//        		.logoutSuccessUrl("/login")
//        		.deleteCookies("JSESSIONID")
//        		.permitAll());
//
//    	http
////    		.cors(cors -> cors.disable())
//    		.csrf(csrf -> csrf.disable());
//    	
//        return http.build();
//    }
//
//	@Bean
//    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails test = User
//        		.withUsername("test")
//        		.password(passwordEncoder.encode("1234"))
//        		.authorities("USER")
//        		.build();
//        return new InMemoryUserDetailsManager(test);
//    }
//
//	@Bean 
//	PasswordEncoder passwordEncoder() {
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	}
//
////	@Bean
////    CompromisedPasswordChecker compromisedPasswordChecker() {
////        return new HaveIBeenPwnedRestApiPasswordChecker();
////
////    }
//}