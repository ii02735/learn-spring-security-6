package com.ii02735.learn_spring_security_6.controller;

import com.ii02735.learn_spring_security_6.entity.Customer;
import com.ii02735.learn_spring_security_6.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Customer> register(/* Normalement on ne devrait pas injecter directement l'entité, on devrait passer par un DTO, mais pour des questions de rapidité, on va continuer comme ça */@RequestBody Customer customer) {
        final String password = this.passwordEncoder.encode(customer.getPassword());
        customer.setPassword(password);
        try {
            final Customer savedCustomer = this.customerRepository.save(customer);

            if (savedCustomer.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
