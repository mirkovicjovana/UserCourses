package com.example.dto;

import com.example.model.CourseDto;
import com.example.model.UserDtoBasic;
import lombok.*;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ResponseDto {

    private UserDtoBasic user;
    private List<CourseDto> courses;

}