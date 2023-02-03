package com.example.service;

import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import com.example.model.CourseBasic;
import com.example.model.CourseDto;
import com.example.repository.CourseRepository;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @InjectMocks
    private CourseServiceImpl courseService;
    @Mock
    private CourseRepository courseRepository;

    @Spy
    private CourseMapper mapper;

    private final String TEST_NAME = "Java";
    private final String TEST_PROF = "Jana Janic";
    private final String TEST_ID = "1";

    private final Course course1 = prepareCourse("1","Java","Marko Markovic",30,
            LocalDate.of(2020, 1, 8),LocalDate.of(2021, 1, 8));

    private final Course course2 = prepareCourse("2","Java","Jana Janic",30,
            LocalDate.of(2021, 1, 8),LocalDate.of(2022, 1, 8));

    private final CourseDto cd1 = prepareCourseDto("1","Java","Marko Markovic",
            LocalDate.of(2020, 1, 8),LocalDate.of(2021, 1, 8));

    private final CourseDto cd2 = prepareCourseDto("1","Java","Jana Janic",
            LocalDate.of(2021, 1, 8),LocalDate.of(2022, 1, 8));

    private final CourseBasic cb1 = prepareCourseBasic("Java",TEST_PROF);
    private final CourseBasic cb2 = prepareCourseBasic("Java2",TEST_PROF);

    @Test
    public void getCourseByName() {

        when(courseRepository.findCourseByName(TEST_NAME)).thenReturn(Flux.just(course1,course2));

        when(mapper.courseToCourseDto(course1)).thenReturn(cd1);

        when(mapper.courseToCourseDto(course2)).thenReturn(cd2);

        Flux<CourseDto> courses = courseService.getCourseByName(TEST_NAME);

        StepVerifier.create(courses)
                .expectNext(cd1,cd2)
                .verifyComplete();
    }

    @Test
    public void getCourseByProfessor() {

        when(courseRepository.findAllByProfessor(TEST_PROF)).thenReturn(Flux.just(course1,course2));

        when(mapper.courseToCourseBasic(course1)).thenReturn(cb1);

        when(mapper.courseToCourseBasic(course2)).thenReturn(cb2);

        Flux<CourseBasic> courses = courseService.getCoursesByProfessor(TEST_PROF);

        StepVerifier.create(courses)
                .expectNext(cb1,cb2)
                .verifyComplete();
    }

    @Test
    public void getCourseById() {

        when(courseRepository.findById(TEST_ID)).thenReturn(Mono.just(course1));
        when(mapper.courseToCourseDto(course1)).thenReturn(cd1);

        Mono<CourseDto> course = courseService.getCourseById(TEST_ID);

        StepVerifier.create(course)
                .expectNext(cd1)
                .verifyComplete();
    }

    @Test
    public void add(){

        when(courseRepository.save(course1)).thenReturn(Mono.empty());

        StepVerifier
                .create(courseService.addCourse(course1))
                .expectNext()
                .verifyComplete();
    }

    @Test
    public void delete(){
        when(mapper.courseBasicToCourse(cb1)).thenReturn(course1);
        when(courseRepository.delete(course1)).thenReturn(Mono.empty());

        StepVerifier
                .create(courseService.deleteCourse(cb1))
                .expectNext()
                .verifyComplete();

    }

    @Test
    public void coursesIds(){
        String[] ids = new String[] {"1", "2"};

        when(courseRepository.findCoursesFromIds(ids)).thenReturn(Flux.just(course1,course2));
        when(mapper.courseToCourseDto(course1)).thenReturn(cd1);
        when(mapper.courseToCourseDto(course2)).thenReturn(cd2);

        StepVerifier
                .create(courseService.getCoursesByIds(List.of(ids)))
                .expectNext(cd1,cd2)
                .verifyComplete();
    }

    @Test
    public void update(){

        when(courseRepository.findCourseByName(cd1.getName())).thenReturn(Flux.just(course1));

        when(courseRepository.save(course1)).thenReturn(Mono.empty());

        StepVerifier
                .create(courseService.updateCourse(cd1))
                .expectNext()
                .verifyComplete();
    }

    private static Course prepareCourse(String id, String name, String professor, int max_num, LocalDate start, LocalDate end){
        return new Course(id,name,professor,max_num,start,end);
    }

    private static CourseBasic prepareCourseBasic(String name, String professor){
        CourseBasic course = new CourseBasic();
        course.setName(name);
        course.setProfessor(professor);
        return course;
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

