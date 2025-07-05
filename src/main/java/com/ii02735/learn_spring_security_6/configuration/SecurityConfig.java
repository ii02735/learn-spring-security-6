package com.ii02735.learn_spring_security_6.configuration;

import com.ii02735.learn_spring_security_6.exception_handling.CustomerAuthenticationAccessDeniedHandler;
import com.ii02735.learn_spring_security_6.exception_handling.CustomerAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        // TODO: nécessité temporaire pour faire fonctionner les RestControllers (à supprimer)
        http.csrf(AbstractHttpConfigurer::disable);
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
                .requestMatchers("/", "/register").permitAll()
                .requestMatchers("/write").hasAuthority("write")
                .requestMatchers("/hello").authenticated());

        // Permet d'afficher un formulaire d'authentification (pour utilisateurs) : déclenchera UsernamePasswordAuthenticationFilter
        http.formLogin(withDefaults());
        /*
         * Pour désactiver l'authentification via formulaire
         * http.formLogin(AbstractHttpConfigurer::disable);
         */
        // Permet d'accepter l'authentification basic (pour APIs) : déclenchera BasicAuthenticationFilter
        http.httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.authenticationEntryPoint(new CustomerAuthenticationEntryPoint()));
        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(new CustomerAuthenticationAccessDeniedHandler()));
        return http.build();
    }

    // Bean qui va contenir un inventaire de plusieurs utilisateurs en mémoire
    // Rappel : l'implémentation UserDetailsService est consulté pour connaître si un utilisateur existe ou pas
    /*@Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    } --> Plus nécessaire de déclarer cette bean, car nous avons créé notre propre bean : CustomerUserDetailsService
    */

    // On utilise l'algorithme bcrypt pour encoder et décoder les mots de passe
    @Bean
    public PasswordEncoder passwordEncoder() {
        /*
         * Par défaut, le chiffreur est BCryptPasswordEncoder, mais on peut préciser un autre algorithme
         * avec le bon préfixe, comme {noop}, {ldap} au niveau de la déclaration des mots de passe.
         *
         * Comme BCryptPasswordEncoder est par défaut, il est aussi possible de retourner directement une nouvelle instance :
         *
         * return new BCryptPasswordEncoder();
         *
         * Mais pour des questions de flexibilité, on passe par le processus de délégation par le préfixe.
         */
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
