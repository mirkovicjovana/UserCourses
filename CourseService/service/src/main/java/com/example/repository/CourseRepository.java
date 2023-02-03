package com.example.repository;

import com.azure.spring.data.cosmos.repository.Query;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import com.example.entity.Course;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CourseRepository extends ReactiveCosmosRepository<Course,String> {
    Flux<Course> findCourseByName(String name);
    Flux<Course> findAllByProfessor(String name);

    @Query(value = "SELECT * FROM Course WHERE array_contains(@ids,Course.id)")
    Flux<Course> findCoursesFromIds (@Param("ids")String[] ids);

}
