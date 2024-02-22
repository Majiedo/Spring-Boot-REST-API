package com.majed.authentication;

import com.majed.authentication.domain.entities.UserEntity;

public class TestDataUtil {

    private TestDataUtil(){

    }


    public static UserEntity createTestUserA(){
        return UserEntity.builder().email("Majed@skiff.com").name("Majed").build();
    }

    public static UserEntity createTestUserB(){
        return UserEntity.builder().email("Omar@skiff.com").name("Omar").build();
    }
}
