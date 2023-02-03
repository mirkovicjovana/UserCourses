
package com.example.service;

import com.example.mapper.CourseMapper;
import com.example.model.CourseDto;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    public static MockWebServer mockWebServer;
    @InjectMocks
    private CourseService courseService;


    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    //PROVERI?????
    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        courseService = new CourseService(WebClient.create(baseUrl));
    }

    @Test
    void testingWebClient() throws Exception {
        CourseDto cd1 = prepareCourseDto("1", "Java", "Jovana Jovanovic",
                LocalDate.of(2022, 12, 12), LocalDate.of(2023, 03, 12));
        CourseDto cd2 = prepareCourseDto("2", "Java", "Marija Maric",
                LocalDate.of(2023, 12, 12), LocalDate.of(2024, 03, 12));
        List<CourseDto> courses = List.of(cd1, cd2);

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("[{\"name\":\"Java\",\"professor\":\"Jovana Jovanovic\",\"start\":\"2022-12-12\",\"end\":\"2023-03-12\"}," +
                                        "{\"name\":\"Java\",\"professor\":\"Marija Maric\",\"start\":\"2023-12-12\",\"end\":\"2024-03-12\"}]"));

        Mono<List<CourseDto>> actual = courseService.getCourses(List.of("1","2"));

        StepVerifier.create(actual)
                .expectNext(courses)
                .verifyComplete();
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
