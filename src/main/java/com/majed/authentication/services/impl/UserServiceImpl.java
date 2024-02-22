package com.majed.authentication.services.impl;

import com.majed.authentication.domain.entities.UserEntity;
import com.majed.authentication.repositories.UserRepository;
import com.majed.authentication.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserEntity createUser(UserEntity userEntity) {
        // lowerCase Email
        userEntity.setEmail(userEntity.getEmail().toLowerCase());
        return userRepository.save(userEntity);
    }

    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<UserEntity> findOne(Integer id) {
        return userRepository.findById(id);
    }

    public boolean isExists(Integer id) {
        return userRepository.existsById(id);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public UserEntity update(Integer id, UserEntity userEntity) {
        userEntity.setId(id);
        return userRepository.findById(id).map(existingUser -> {
            Optional.ofNullable(userEntity.getName()).ifPresent(existingUser::setName);
            Optional.ofNullable(userEntity.getEmail()).ifPresent(existingUser::setEmail);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User Doesn't Exists"));
    }


    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase());
    }


}
