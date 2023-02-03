package com.example.validationTest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/test")
@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody @Valid UserDtoCustom userDto) {
        testService.addUser(userDto);
        return ResponseEntity.ok("The user has been added.");
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody @Valid  UserDtoCustom userDto) {
        testService.updateUser(userDto);
        return ResponseEntity.ok("User has been updated.");
    }
}
