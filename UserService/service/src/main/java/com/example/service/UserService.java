package com.example.service;

import com.example.dto.ResponseDto;
import com.example.model.UserBasic;
import com.example.model.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

     Mono<Void> addUser(UserDto userDto);
     Flux<UserBasic> getUserByUsername(String username);


     //Mono<Void> deleteUser(String id, String partition_id);
     Mono<Void> deleteUser(String id);
     Mono<Void> updateUser( UserDto  userDto);
     Flux<UserBasic> getUsersByName(String name);

     Mono<ResponseDto> getUserWithCourses(String id);

     //Mono<ResponseEntity<Object>> deleteUser2(String id, String partition_id);

     Flux<UserBasic> getUserWithSpecialNameAndAge(String letter,int age);

}
