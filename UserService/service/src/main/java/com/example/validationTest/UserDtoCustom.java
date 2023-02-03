package com.example.validationTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoCustom {
    private String id;
    @Size(min = 5)
    private String name;
    private String gender;
    private int age;
    private String username;
    @NotNull(groups = OnAdd.class)
    @Null(groups = OnUpdate.class)
    private String password;
    private List<String> courses;
}
