package com.majed.authentication.controllers;

import com.majed.authentication.domain.dto.UserDto;
import com.majed.authentication.domain.entities.UserEntity;
import com.majed.authentication.mappers.Mapper;
import com.majed.authentication.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final Mapper<UserEntity, UserDto> userMapper;

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto user) {
        UserEntity userEntity = userMapper.mapFrom(user);
        Optional<UserEntity> userExists = userService.findByEmail(userEntity.getEmail());
        if (userExists.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity savedUserEntity = userService.createUser(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/users")
    public Page<UserDto> listUsers(Pageable pageable) {
        Page<UserEntity> users = userService.findAll(pageable);
        return users.map(userMapper::mapTo);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Integer id) {
        Optional<UserEntity> user = userService.findOne(id);
        return user.map(userEntity -> {
            UserDto userDto = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") Integer id) {
        if (!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Integer id, @RequestBody() @Valid UserDto userDto) {
        if (!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUser = userService.update(id, userEntity);
        return new ResponseEntity<>(userMapper.mapTo(updatedUser), HttpStatus.OK);
    }

}
