
package com.example.controller;


import com.example.dto.ResponseDto;
import com.example.entity.User;
import com.example.model.CourseDto;
import com.example.model.UserBasic;
import com.example.model.UserDto;
import com.example.model.UserDtoBasic;
import com.example.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserServiceImpl userService;

    private final String TEST_USN = "mirkovicj";
    private final String name = "Jovana Mirkovic";
    private final String PATH = "/user/username/{username}";
    private final String ID = "1";

    private final User user1 = prepareUser("1", name, "female", 26, "mirkovicj", "jm", List.of("1", "2"));
    private final User user2 = prepareUser("2", name, "female", 25, "jovanam", "jm", List.of("1", "2","3"));

    private final UserDto userDto1 = prepareUserDto("jovana", "Jovana", 26, "f", "jdkjslmd", List.of("1", "2"));


    private final UserBasic userBasic = prepareUserBasic("Jovana Mirkovic","mirkovicj");
    UserBasic userBasic2 = prepareUserBasic("Jovana Mirkovic","jovanam");

    @Test
    public void getUserByUsername() {

        Flux<UserBasic> userbasic=Flux.just(userBasic);
        when(userService.getUserByUsername(any())).thenReturn(userbasic);

        Flux<UserBasic> responseBody = webTestClient.get().uri("/user/username/mirkovicj")
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserBasic.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p->p.getUsername().equals("mirkovicj"))
                .verifyComplete();
    }

    @Test
    public void getUserByName() throws Exception {

        Flux<UserBasic> userbasic=Flux.just(userBasic);
        when(userService.getUsersByName(anyString())).thenReturn(userbasic);

        Flux<UserBasic> responseBody = webTestClient.get().uri("/user/name/Jovana Mirkovic")
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserBasic.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p->p.getUsername().equals("mirkovicj"))
                .verifyComplete();
    }

    @Test
    public void getUserWithCourses() throws Exception {

        User user = prepareUser("1", "Jovana Mirkovic", "female", 26, "mirkovicj", "jm", List.of("1", "2"));
        CourseDto cd1 = prepareCourseDto("1", "Java", "Marko Markovic",
                LocalDate.of(2020, 1, 8), LocalDate.of(2021, 1, 8));
        CourseDto cd2 = prepareCourseDto("2", "Java", "Jana Janic",
                LocalDate.of(2021, 1, 8), LocalDate.of(2022, 1, 8));
        List<CourseDto> courses = List.of(cd1, cd2);
        UserDtoBasic udb = prepareUserDtoBasic("mirkovicj","Jovana Mirkovic",26,"female");
        ResponseDto rd = new ResponseDto(udb,courses);

        when(userService.getUserWithCourses(anyString())).thenReturn(Mono.just(rd));

        Flux<ResponseDto> responseBody = webTestClient.get().uri("/user/id/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ResponseDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p->p.getUser().getUsername().equals("mirkovicj"))
                .verifyComplete();
    }

    @Test
    public void updateUserTest(){

        when(userService.updateUser(userDto1)).thenReturn(Mono.empty());

        webTestClient.put().uri("/user")
                .body(Mono.just(userDto1),UserDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void addUserTest(){

        when(userService.addUser(userDto1)).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/user")
                .body(Mono.just(userDto1),UserDto.class)
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    public void deleteUserTest(){

        given(userService.deleteUser(anyString())).willReturn(Mono.empty());
        webTestClient.delete().uri("/user/1")
                .exchange()
                .expectStatus().isOk();//200
    }



    private static UserBasic prepareUserBasic(String name, String username) {
        UserBasic userBasic2 = new UserBasic();
        userBasic2.setName(name);
        userBasic2.setUsername(username);
        return userBasic2;
    }

    private static User prepareUser(String id, String name, String gender, int age, String usn, String password, List<String> courses) {
        return new User(id, name, gender, age, usn, password, courses);
    }

    private static UserDto prepareUserDto(String username, String name, int age, String gender, String password, List<String> courses) {
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setName(name);
        userDto.setAge(age);
        userDto.setGender(gender);
        userDto.setPassword(password);
        userDto.setCourses(courses);
        return userDto;
    }

    private static CourseDto prepareCourseDto(String id, String name, String professor, LocalDate start, LocalDate end) {
        CourseDto course = new CourseDto();
        course.setName(name);
        course.setProfessor(professor);
        course.setStart(start);
        course.setEnd(end);
        return course;
    }

    private static UserDtoBasic prepareUserDtoBasic(String username, String name, int age, String gender) {
        UserDtoBasic userDtoBasic = new UserDtoBasic();
        userDtoBasic.setUsername(username);
        userDtoBasic.setName(name);
        userDtoBasic.setAge(age);
        userDtoBasic.setGender(gender);
        return userDtoBasic;
    }

}



