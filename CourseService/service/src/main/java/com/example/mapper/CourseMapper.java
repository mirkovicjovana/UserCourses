package com.example.mapper;

import com.example.entity.Course;

import com.example.model.CourseArrayResponse;
import com.example.model.CourseBasic;
import com.example.model.CourseDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
unmappedSourcePolicy = ReportingPolicy.WARN, componentModel = "spring")
public interface CourseMapper {

    Course courseDtoToCourse(CourseDto courseDto);
    CourseDto courseToCourseDto(Course course);

    CourseArrayResponse courseToCourseArrayResponse(Course course);

    Course courseBasicToCourse(CourseBasic courseBasic);
    CourseBasic courseToCourseBasic(Course course);

}
