package com.ii02735.learn_spring_security_6.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    /**
     * Reprise de la bean par défaut de Spring Security depuis {@link org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration}
     * Cela nous permettra de surcharger la configuration par défaut pour nos propres besoins
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        /* - N'accepter que les requêtes authentifiées (configuration par défaut)
         * http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
         *
         * - Refuser n'importe quelle requête (aboutira à une 403, utilisation très rare. Mais peut être utile s'il faut refuser des requêtes en provenance de certaines APIs)
         * http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
         *
         * - Accepter n'importe quelle requête (attention : plus besoin d'authentification / Spring Security) !
         * http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
         */

        // Ne permettre que certaines ressources accessibles publiquement
        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/").permitAll());
        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/hello").authenticated());

        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }
}
