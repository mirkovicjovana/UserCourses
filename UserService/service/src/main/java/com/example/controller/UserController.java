package com.example.controller;

import com.example.dto.ResponseDto;
import com.example.model.UserBasic;
import com.example.model.UserDto;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequestMapping(value = "/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/username/{username}")
    public Flux<UserBasic> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/name/{name}")
    public Flux<UserBasic> getUsersByName(@PathVariable String name) {
        return userService.getUsersByName(name);
    }

    @PostMapping
    public Mono<Void> addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }

    @PutMapping
    public Mono<Void> updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @GetMapping("/id/{id}")
    public Mono<ResponseDto> getUserWithCourses(@PathVariable String id) {
        return userService.getUserWithCourses(id);
    }

    /*@DeleteMapping("delete2/{id}/{partition_id}")
    public Mono<ResponseEntity<Object>> deleteUser2(@PathVariable String id, @PathVariable String partition_id) {
        return userService.deleteUser2(id,partition_id);
    }*/

    @GetMapping("/special/{letter}/{age}")
    public Flux<UserBasic> getUserWithSpecialNameAndAge(@PathVariable String letter, @PathVariable int age){
        return userService.getUserWithSpecialNameAndAge(letter,age);
    }
}