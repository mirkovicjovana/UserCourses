package com.example.service;

import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import com.example.model.CourseBasic;
import com.example.model.CourseDto;
import com.example.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    Random random = new Random();

    @Override
    public Flux<CourseDto> getCourseByName(String name) {

        return courseRepository.findCourseByName(name)
                .filter(Objects::nonNull)
                .map(courseMapper::courseToCourseDto);
    }

    @Override
    public Flux<CourseDto> getCoursesByIds(List<String> ids) {
        return courseRepository.findCoursesFromIds(ids.toArray(new String[ids.size()]))
                .map(courseMapper::courseToCourseDto);
    }

    @Override
    public Mono<CourseDto> getCourseById(String id) {
        return courseRepository.findById(id)
                .filter(Objects::nonNull)
                .map(courseMapper::courseToCourseDto);
    }



    @Override
    public Flux<CourseBasic> getCoursesByProfessor(String name) {
        return courseRepository.findAllByProfessor(name)
                .filter(Objects::nonNull)
                .map(courseMapper::courseToCourseBasic);
    }

    @Override
    public Mono<Void> addCourse(Course course) {

        return Mono.just(course)
                //.map(courseMapper::courseDtoToCourse)
                .flatMap(courseRepository::save)
                .then();
    }

    @Override
    public Mono<Void> deleteCourse(CourseBasic course) {

        return Mono.just(course)
                .map(courseMapper::courseBasicToCourse)
                .flatMap(courseRepository::delete)
                .then();
    }

    @Override
    public Mono<Void> updateCourse(CourseDto newCourse) {
        return courseRepository.findCourseByName(newCourse.getName())
                .filter(Objects::nonNull)
                .map(course -> {
                    course.setName(newCourse.getName());
                    course.setProfessor(newCourse.getProfessor());
                    course.setStart(newCourse.getStart());
                    course.setEnd(newCourse.getEnd());
                    course.setMax_number_users(random.nextInt(6) + 45);
                    return course;
                })
                .flatMap(courseRepository::save)
                .then();
    }


}
