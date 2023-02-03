/*
package com.example.repository;

import com.example.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static reactor.core.publisher.Mono.when;


@ExtendWith(MockitoExtension.class)
//@DataJpaTest
class UserRepositoryTest {

    @MockBean
    private UserRepository userRepository;

    private final String letter = "a";
    private int age = 26;

    @Test
    public void findUserWithSpecialNameAndAge(){

        User user1 = new User("1","ana","f",26,"a","a", List.of("1"));
        User user2 = new User("1","jov","f",26,"a","a", List.of("1"));
        User user3 = new User("1","ana","f",16,"a","a", List.of("1"));
        User user4 = new User("1","jovana","f",26,"a","a", List.of("1"));


        Flux<User> users = userRepository.findUserWithSpecialNameAndAge(letter,age);

        StepVerifier.create(users)
                .expectNext(user1,user4)
                .verifyComplete();
    }
}*/
