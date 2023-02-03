package com.example.controller;

import com.example.entity.Course;
import com.example.model.CourseBasic;
import com.example.model.CourseDto;
import com.example.service.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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


@WebFluxTest(controllers = CourseController.class)
class CourseControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CourseServiceImpl courseService;

    private static final String PATH = "/course/name/{name}";

    private final String TEST_NAME = "Java";
    private final String TEST_PROF = "Jana Janic";


    private final CourseDto cd1 = prepareCourseDto("1","Java","Marko Markovic",
            LocalDate.of(2020, 1, 8),LocalDate.of(2021, 1, 8));

    private final CourseDto cd2 = prepareCourseDto("2","Java","Jana Janic",
            LocalDate.of(2021, 1, 8),LocalDate.of(2022, 1, 8));

    private final Course course1 = prepareCourse("1","Java","Marko Markovic",30,
            LocalDate.of(2020, 1, 8),LocalDate.of(2021, 1, 8));
    private final CourseBasic cb1 = prepareCourseBasic("Java",TEST_PROF);

    @Test
    public void getCourseByName() {

        Flux<CourseDto> courseDtoFlux=Flux.just(cd1);
        when(courseService.getCourseByName(anyString())).thenReturn(courseDtoFlux);

        Flux<CourseDto> responseBody = webTestClient.get()
                .uri(builder -> builder.path("/course/name/").queryParam("name","Java").build())
                .exchange()
                .expectStatus().isOk()
                .returnResult(CourseDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(c -> c.getName().equals("Java"))
                .verifyComplete();
    }

    @Test
    public void getCourseById() {

        Mono<CourseDto> courseDtoMono= Mono.just(cd1);
        when(courseService.getCourseById(anyString())).thenReturn(courseDtoMono);

        Flux<CourseDto> responseBody = webTestClient.get()
                .uri("/course/id/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(CourseDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(c -> c.getName().equals("Java"))
                .verifyComplete();
    }

    @Test
    public void getCourseByProf() {

        Flux<CourseBasic> courses = Flux.just(cb1);
        when(courseService.getCoursesByProfessor(anyString())).thenReturn(courses);

        Flux<CourseDto> responseBody = webTestClient.get()
                .uri("/course/professor/Jana Janic")
                .exchange()
                .expectStatus().isOk()
                .returnResult(CourseDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(c -> c.getProfessor().equals("Jana Janic"))
                .verifyComplete();
    }

    @Test
    public void getCoursesForUser() {

        Flux<CourseDto> courseDtoFlux=Flux.just(cd1);
        when(courseService.getCourseByName(anyString())).thenReturn(courseDtoFlux);

        Flux<CourseDto> responseBody = webTestClient.get()
                .uri(builder -> builder.path("/course/ids/").queryParam("ids", List.of("1")).build())
                .exchange()
                .expectStatus().isOk()
                .returnResult(CourseDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .verifyComplete();

    }

    @Test
    public void updateCourseTest() {
        when(courseService.updateCourse(cd1)).thenReturn(Mono.empty());

        webTestClient.put()
                .uri("/course")
                .body(Mono.just(cd1), CourseDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void addCourseTest(){

        when(courseService.addCourse(course1)).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/course")
                .body(Mono.just(course1),Course.class)
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    public void deleteCourseTest(){

        when(courseService.deleteCourse(cb1)).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/course")
                .body(Mono.just(cb1),CourseBasic.class)
                .exchange()
                .expectStatus().isOk();
    }

    private static CourseBasic prepareCourseBasic(String name, String professor){
        CourseBasic course = new CourseBasic();
        course.setName(name);
        course.setProfessor(professor);
        return course;
    }
    private static Course prepareCourse(String id, String name, String professor, int max_num, LocalDate start, LocalDate end){
        return new Course(id,name,professor,max_num,start,end);
    }

    private static CourseDto prepareCourseDto(String id, String name, String professor, LocalDate start, LocalDate end){
        CourseDto course = new CourseDto();
        course.setName(name);
        course.setProfessor(professor);
        course.setStart(start);
        course.setEnd(end);
        return course;
    }

}