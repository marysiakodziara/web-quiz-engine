package org.spring.webquizengine.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.spring.webquizengine.entity.UserQuiz;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class QuizPostDTO {

    @JsonIgnore
    UserQuiz userQuiz;
    @NotEmpty
    private String title;
    @NotEmpty
    private String text;
    @NotEmpty
    private String[] options;
    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;
}
