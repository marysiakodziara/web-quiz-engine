package org.spring.webquizengine.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class UserDTO {

    @NotEmpty
    @Email(regexp = ".+@.+\\..+")
    private String email;
    @NotEmpty
    @Pattern(regexp = ".{5,}")
    private String password;
}
