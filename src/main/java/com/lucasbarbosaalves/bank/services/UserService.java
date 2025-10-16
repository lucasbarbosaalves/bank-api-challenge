package com.lucasbarbosaalves.bank.services;

import com.lucasbarbosaalves.bank.entities.users.CreateUserDTO;
import com.lucasbarbosaalves.bank.entities.account.Account;
import com.lucasbarbosaalves.bank.entities.users.User;
import com.lucasbarbosaalves.bank.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(CreateUserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.name());
        user.setDocument(userDTO.document());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());

        validadeUser(user);


        Account newAccount = createNewAccountForUser(user);
        user.setAccount(newAccount);

        return userRepository.save(user);
    }

    private void validadeUser(User user) {
        userRepository.findByDocument(user.getDocument()).ifPresent(u -> {
            throw new IllegalArgumentException("Já existe um usuário com este documento.");
        });
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
        });
    }

    private Account createNewAccountForUser(User user) {
        Account account = new Account();
        account.setUser(user);
        long accountNumber = 100000 + new Random().nextInt(900000);
        account.setAccountNumber(accountNumber);
        return account;
    }


}