package com.example.validationTest;

import com.example.entity.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TestService {
    private final UserRepository userRepository;


    public Mono<Void> addUser(UserDtoCustom newUserDto) {
        User newUser = new User(newUserDto.getId(), newUserDto.getName(), newUserDto.getGender(), newUserDto.getAge(), newUserDto.getUsername(), newUserDto.getPassword(), newUserDto.getCourses());
        return userRepository.save(newUser)
                .then();
    }

    public Mono<Void> updateUser(UserDtoCustom newUserDto) {
        //User oldUser = userRepository.findUserByUsername(newUserDto.getUsername()).get(0);
        return userRepository.findUserByUsername(newUserDto.getUsername())
                        .map(u->{
                            User oldUser= new User();
                            oldUser.setName(u.getName());
                            oldUser.setAge(u.getAge());
                            oldUser.setGender(u.getGender());
                            oldUser.setCourses(u.getCourses());
                            oldUser.setPassword(u.getPassword());
                            return oldUser;
                        })
                .flatMap(userRepository::save)
                .then();
        /*oldUser.setUsername(newUserDto.getUsername());
        oldUser.setName(newUserDto.getName());
        oldUser.setAge(newUserDto.getAge());
        oldUser.setGender(newUserDto.getGender());
        oldUser.setCourses(newUserDto.getCourses());
        oldUser.setPassword(newUserDto.getPassword());
        return userRepository.save(oldUser)*/
    }


}
