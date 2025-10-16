package com.lucasbarbosaalves.bank.repositories;

import com.lucasbarbosaalves.bank.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByDocument(String document);
    Optional<User> findByEmail(String email);
}