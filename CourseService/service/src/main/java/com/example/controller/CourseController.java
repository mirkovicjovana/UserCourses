package com.example.controller;


import com.example.entity.Course;
import com.example.model.CourseBasic;
import com.example.model.CourseDto;
import com.example.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@RequestMapping(value = "/course")
@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public Mono<Void> addCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }

    @GetMapping("/name/")
    public Flux<CourseDto> getCourseByName(@RequestParam String name){
        return courseService.getCourseByName(name);
    }

    @GetMapping("/id/{id}")
    public Mono<CourseDto> getCourseById(@PathVariable String id){
        return courseService.getCourseById(id);
    }

    @GetMapping("/professor/{professor}")
    public Flux<CourseBasic> getCoursesByProfessor(@PathVariable String professor){
        return courseService.getCoursesByProfessor(professor);
    }
    @GetMapping("/ids/")
    public Flux<CourseDto> getCoursesByIds(@RequestParam List<String> ids){
        return courseService.getCoursesByIds(ids);
    }

    @DeleteMapping
    public Mono<Void> deleteCourse(@RequestBody CourseBasic course){
        return courseService.deleteCourse(course);
    }

    @PutMapping
    public Mono<Void> updateUser(@RequestBody CourseDto course) {
        return courseService.updateCourse(course);
    }


}

