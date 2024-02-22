package com.majed.authentication.repositories;

import com.majed.authentication.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer>, PagingAndSortingRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
}