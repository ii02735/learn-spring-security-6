package com.ii02735.learn_spring_security_6.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    /**
     * Reprise de la bean par défaut de Spring Security depuis SpringBootWebSecurityConfiguration
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
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/").permitAll()
                .requestMatchers("/hello").authenticated());

        // Permet d'afficher un formulaire d'authentification (pour utilisateurs) : déclenchera UsernamePasswordAuthenticationFilter
        http.formLogin(withDefaults());
        /*
         * Pour désactiver l'authentification via formulaire
         * http.formLogin(AbstractHttpConfigurer::disable);
         */
        // Permet d'accepter l'authentification basic (pour APIs) : déclenchera BasicAuthenticationFilter
        http.httpBasic(withDefaults());
        return http.build();
    }

    // Bean qui va contenir un inventaire de plusieurs utilisateurs en mémoire
    // Rappel : l'implémentation UserDetailsService est consulté pour connaître si un utilisateur existe ou pas
    @Bean
    public UserDetailsService userDetailsService() {
        // Une autorité désigne la permission d'un utilisateur
        // Les autorités saisies ici sont arbitraires
        // On précise que les mots de passe ont été chiffrés en bcrypt, et que donc, notre bean passwordEncoder va utiliser BCryptPasswordEncoder pour déchiffrer cela.
        UserDetails user = User.withUsername("user").password("{bcrypt}$2a$12$.eaRIKyqmV5OS6ycI5uW.O3iYfjeAyPk7DJwTekVGk3PbXxr3y3DS").authorities("read").build();
        UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$.eaRIKyqmV5OS6ycI5uW.O3iYfjeAyPk7DJwTekVGk3PbXxr3y3DS").authorities("admin").build();
        // Chaque user sera stocké dans une HashMap de InMemoryUserDetailsManager
        return new InMemoryUserDetailsManager(user, admin);
    }

    // On utilise l'algorithme bcrypt pour encoder et décoder les mots de passe
    @Bean
    public PasswordEncoder passwordEncoder() {
        /*
         * Par défaut, le chiffreur est BCryptPasswordEncoder, mais on peut préciser un autre algorithme
         * avec le bon préfixe, comme {noop}, {ldap} au niveau de la déclaration des mots de passe
         */
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
