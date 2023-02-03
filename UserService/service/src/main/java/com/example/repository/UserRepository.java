package com.example.repository;

import com.azure.core.annotation.QueryParam;
import com.azure.spring.data.cosmos.repository.Query;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import com.example.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCosmosRepository<User, String> {
    Flux<User> findUserByUsername(String username);

    Flux<User> findAllByName(String name);

    //Mono<Void> deleteById(String id, String partitionId);


   /* @Query("SELECT * WHERE NAME  LIKE %:letter%")
    Flux<User> findUserWithSpecialName(@Param("letter") String letter);*/
   @Query("SELECT * WHERE NAME  LIKE %:letter% AND AGE LIKE :age")
   Flux<User> findUserWithSpecialNameAndAge(@Param("letter") String letter, int age);


    Flux<User> findUserByAge(int age);

}
