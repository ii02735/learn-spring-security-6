package com.ii02735.learn_spring_security_6.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final var username = authentication.getName();
        final var password = authentication.getCredentials().toString();
        final var userDetails = customUserDetailsService.loadUserByUsername(username);
        log.debug("Authenticating user {} with CustomerAuthenticationProvider", username);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            /*
             * Utilité de l'authenticationProvider custom : à ce stade on peut écrire n'importe quelle logique avant de retourner l'instance Authentication qui va terminer l'authentification
             */
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // On souhaite conserver la possibilité de s'authentifier par username / mot de passe
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }


}
