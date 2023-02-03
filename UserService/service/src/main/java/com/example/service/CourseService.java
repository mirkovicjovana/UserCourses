package com.example.service;


import com.example.model.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final WebClient courseWebClient;


    public Mono<List<CourseDto>> getCourses(List<String> ids) {
        return courseWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/course/ids/")
                        .queryParam("ids",String.join(",",ids))
                        .build())
                .retrieve()
                .bodyToFlux(CourseDto.class)
                .collectList();
    }
}
