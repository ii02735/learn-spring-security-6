package com.ii02735.learn_spring_security_6.service;

import com.ii02735.learn_spring_security_6.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var customer = this.customerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Customer not found from email: " + username));
        var authorities = customer.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).toList();
        return new User(customer.getEmail(), customer.getPassword(), authorities);
    }
}
