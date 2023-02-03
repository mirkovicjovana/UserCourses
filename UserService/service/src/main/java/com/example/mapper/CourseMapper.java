package com.example.mapper;

import com.example.model.CourseDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.WARN, componentModel = "spring")
public class CourseMapper {
    public String courseDtoToString(List<CourseDto> courses) {
        StringBuilder result = new StringBuilder();
        for (CourseDto c: courses) {
            result.append(c.getName())
                    .append(" ")
                    .append(c.getProfessor())
                    .append(" ")
                    .append(c.getStart().toString())
                    .append(" ")
                    .append(c.getEnd().toString())
                    .append(",");
        }
        return result.toString();
    }
}
