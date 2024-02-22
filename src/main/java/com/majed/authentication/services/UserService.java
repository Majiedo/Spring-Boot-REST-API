package com.majed.authentication.services;

import com.majed.authentication.domain.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    UserEntity createUser(UserEntity userEntity);

    Page<UserEntity> findAll(Pageable pageable);

    Optional<UserEntity> findOne(Integer id);

    Optional<UserEntity> findByEmail(String email);

    boolean isExists(Integer id);

    void delete(Integer id);

    UserEntity update(Integer id, UserEntity userEntity);

}
