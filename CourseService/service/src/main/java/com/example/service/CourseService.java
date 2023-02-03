package com.example.service;

import com.example.entity.Course;
import com.example.model.CourseBasic;
import com.example.model.CourseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CourseService {
    Mono<Void> addCourse(Course course);
    Flux<CourseDto> getCourseByName(String name);
    Mono<CourseDto> getCourseById(String id);
    Mono<Void> deleteCourse(CourseBasic course);
    Mono<Void> updateCourse(CourseDto course);
    Flux<CourseBasic> getCoursesByProfessor (String name);
    Flux<CourseDto> getCoursesByIds(List<String> ids);
}
