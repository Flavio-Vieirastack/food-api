package com.foodapi.foodapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
public class webSecurityConfig {
    //Configurações de segurança do spring security o metodo a baixo faz as configurações iniciais
    @Bean
    public DefaultSecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        //https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html
            httpSecurity.authorizeHttpRequests(
                    (authorize) -> authorize.requestMatchers("/kitchens/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated()
            ).httpBasic
                    (Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable).sessionManagement(
                    (police) -> police.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
            return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
