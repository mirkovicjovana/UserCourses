
package com.example.mapper;

import com.example.model.CourseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CourseMapperTest {

    @InjectMocks
    CourseMapper courseMapper;

    @Test
    public void courseDtoToString(){
        CourseDto course = new CourseDto();
        course.setName("Java");
        course.setProfessor("JJ");
        course.setStart(LocalDate.of(2020, 1, 8));
        course.setEnd(LocalDate.of(2020, 1, 18));

        String string = "Java JJ 2020-01-08 2020-01-18,";

        String actual = courseMapper.courseDtoToString(List.of(course));

        assertEquals(string, actual);


    }
}
