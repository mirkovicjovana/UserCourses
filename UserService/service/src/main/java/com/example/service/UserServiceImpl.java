package com.example.service;

import com.example.dto.ResponseDto;
import com.example.entity.User;
import com.example.mapper.UserDtoMapper;
import com.example.model.CourseDto;
import com.example.model.UserBasic;
import com.example.model.UserDto;
import com.example.model.UserDtoBasic;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    private final CourseService courseService;

    @Override
    public Flux<UserBasic> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .filter(Objects::nonNull)
                .map(userDtoMapper::userToUserBasic);
    }

    @Override
    public Flux<UserBasic> getUsersByName(String name) {

        return userRepository.findAllByName(name)
                .filter(Objects::nonNull)
                .map(u -> new UserBasic().name(u.getName()).username(u.getUsername()));
    }

    @Override
    public Mono<ResponseDto> getUserWithCourses(String id) {

         return userRepository.findById(id).zipWhen(u ->
                     courseService.getCourses(u.getCourses())
                 )
                .map(tuple -> new ResponseDto(userDtoMapper.userToUserDtoBasic(tuple.getT1()), tuple.getT2()));

    }

    @Override
    public Mono<Void> addUser(UserDto userDto) {
        return Mono.just(userDto)
                .map(userDtoMapper::userDtoToUser)
                .flatMap(userRepository::save)
                .then();
    }


    @Override
    public Mono<Void> deleteUser(String id) {

        return userRepository.findById(id)
                .flatMap(userRepository::delete);
        //return userRepository.deleteById(id, partitionId);
    }

    public Mono<Void> updateUser(UserDto newUserDto) {
        return userRepository.findUserByUsername(newUserDto.getUsername())
                .filter(Objects::nonNull)
                .map(u -> {
                    u.setName(newUserDto.getName());
                    u.setUsername(newUserDto.getUsername());
                    u.setGender(newUserDto.getGender());
                    u.setAge(newUserDto.getAge());
                    u.setPassword(newUserDto.getPassword());
                    u.setCourses(newUserDto.getCourses());
                    return u;
                })
                .flatMap(userRepository::save)
                .then();
    }



  /*  @Override
    public Mono<ResponseEntity<Object>> deleteUser2(String id, String partition_id) {
        return userRepository.deleteById(id, partition_id)
                .map(u -> ResponseEntity.status(HttpStatus.OK).build())
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid id"));
    }*/

    @Override
    public Flux<UserBasic> getUserWithSpecialNameAndAge(String letter, int age) {
        return userRepository.findUserWithSpecialNameAndAge(letter,age)
                .map(userDtoMapper::userToUserBasic);
       /* return userRepository.findUserByAge(age)
                .concatWith(userRepository.findUserWithSpecialName(letter))
                .map(userDtoMapper::userToUserBasic);*/
    }


}
