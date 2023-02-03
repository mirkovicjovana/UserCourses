package com.example.service;

import com.example.dto.ResponseDto;
import com.example.entity.User;
import com.example.mapper.UserDtoMapper;
import com.example.model.CourseDto;
import com.example.model.UserBasic;
import com.example.model.UserDto;
import com.example.model.UserDtoBasic;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private CourseService courseService;
    @Mock
    private UserRepository userRepository;

    @Spy
    private UserDtoMapper mapper;

    final String testUsername = "jm";
    final String name = "Jovana Mirkovic";

    final String id = "1";

    final int age = 26;
    final String letter = "a";


    @Test
    public void getUsersByName() {
        User user1 = prepareUser("1", name, "female", 26, "mirkovicj", "jm", List.of("1", "2"));
        User user2 = prepareUser("2", name, "female", 25, "jovanam", "jm", List.of("1", "2", "3"));

        UserBasic ub1 = prepareUserBasic(name, "mirkovicj");
        UserBasic ub2 = prepareUserBasic(name, "jovanam");


        when(userRepository.findAllByName(name)).thenReturn(Flux.just(user1, user2));
        Flux<UserBasic> actual = userService.getUsersByName(name);

        StepVerifier
                .create(actual)
                .expectNext(ub1, ub2)
                .verifyComplete();

        verify(userRepository, times(1)).findAllByName(name);
    }

    @Test
    public void getUserByUsername() {

        User user1 = prepareUser("1", name, "female", 26, "mirkovicj", "jm", List.of("1", "2"));
        User user2 = prepareUser("2", name, "female", 25, "jovanam", "jm", List.of("1", "2", "3"));

        UserBasic userBasic1 = prepareUserBasic("Jovana Mirkovic", "mirkovicj");
        UserBasic userBasic2 = prepareUserBasic("Jovana Mirkovic", "jovanam");

        when(userRepository.findUserByUsername(testUsername)).thenReturn(Flux.just(user1, user2));

        when(mapper.userToUserBasic(user1)).thenReturn(userBasic1);

        when(mapper.userToUserBasic(user2)).thenReturn(userBasic2);

        Flux<UserBasic> userBasic = userService.getUserByUsername(testUsername);

        StepVerifier.create(userBasic)
                .expectNext(userBasic1, userBasic2)
                .verifyComplete();
    }


    @Test
    public void update() {

        UserDto userDto = prepareUserDto("jovana", "Jovana", 26, "f", "jdkjslmd", List.of("1", "2"));

        User user = prepareUser("1", "Jovana", "f", 26, "jovana", "jdkjslmd", List.of("1", "2"));

        when(userRepository.findUserByUsername(userDto.getUsername())).thenReturn(Flux.just(user));

        when(userRepository.save(user)).thenReturn(Mono.empty());

        StepVerifier
                .create(userService.updateUser(userDto))
                .expectNext()
                .verifyComplete();
    }


    @Test
    public void add() {

        UserDto userDto = prepareUserDto("jovana", "Jovana", 26, "f", "jdkjslmd", List.of("1", "2"));

        User user = prepareUser("1", "Jovana", "f", 26, "jovana", "jdkjslmd", List.of("1", "2"));

        when(mapper.userDtoToUser(userDto)).thenReturn(user);

        when(userRepository.save(user)).thenReturn(Mono.empty());

        StepVerifier
                .create(userService.addUser(userDto))
                .expectNext()
                .verifyComplete();
    }


    @Test
    public void delete() {

        User user = prepareUser("1", name, "female", 26, "mirkovicj", "jm", List.of("1", "2"));

        when(userRepository.findById(id)).thenReturn(Mono.just(user));

        when(userRepository.delete(user))
                .thenReturn(Mono.empty());

        StepVerifier
                .create(userService.deleteUser(id))
                .expectNext()
                .verifyComplete();
    }

    @Test
    public void getUserWithSpecialNameAndAge() {
        User user1 = prepareUser("1", "Jovana Mirkovic", "female", 26, "mirkovicj", "jm", List.of("1", "2"));
        //User user2 = prepareUser("1", "Jovini Mirkovic", "female", 26, "mirkovicj", "jm", List.of("1", "2"));
        //User user3 = prepareUser("1", "Jovana Markovic", "female", 25, "mirkovicj", "jm", List.of("1", "2"));

        UserBasic userBasic1 = prepareUserBasic("Jovana Mirkovic", "mirkovicj");
        UserBasic userBasic2 = prepareUserBasic("Jovini Mirkovic", "mirkovicj");
        UserBasic userBasic3 = prepareUserBasic("Jovana Markovic", "mirkovicj");

        //when(userRepository.findUserByAge(age)).thenReturn(Flux.just(user1,user2));
        when(userRepository.findUserWithSpecialNameAndAge(letter, age)).thenReturn(Flux.just(user1));
        when(mapper.userToUserBasic(user1)).thenReturn(userBasic1);
       /* when(mapper.userToUserBasic(user2)).thenReturn(userBasic2);
        when(mapper.userToUserBasic(user3)).thenReturn(userBasic3);*/

        Flux<UserBasic> actual = userService.getUserWithSpecialNameAndAge(letter, age);

        StepVerifier
                .create(actual)
                .expectNext(userBasic1)
                .verifyComplete();

    }

    @Test
    public void userWithCourses() {
        User user = prepareUser("1", "Jovana Mirkovic", "female", 26, "mirkovicj", "jm", List.of("1", "2"));
        CourseDto cd1 = prepareCourseDto("1", "Java", "Marko Markovic",
                LocalDate.of(2020, 1, 8), LocalDate.of(2021, 1, 8));
        CourseDto cd2 = prepareCourseDto("2", "Java", "Jana Janic",
                LocalDate.of(2021, 1, 8), LocalDate.of(2022, 1, 8));
        List<CourseDto> courses = List.of(cd1, cd2);
        UserDtoBasic udb = prepareUserDtoBasic("mirkovicj","Jovana Mirkovic",26,"female");
        ResponseDto rd = new ResponseDto(udb,courses);

        when(userRepository.findById(id)).thenReturn(Mono.just(user));
        when(courseService.getCourses(user.getCourses())).thenReturn(Mono.just(courses));
        when(mapper.userToUserDtoBasic(user)).thenReturn(udb);

        Mono<ResponseDto> actual = userService.getUserWithCourses(id);

        StepVerifier
                .create(actual)
                .expectNext(rd)
                .verifyComplete();
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