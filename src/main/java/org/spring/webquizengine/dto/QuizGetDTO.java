package org.spring.webquizengine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizGetDTO {
    private Long id;
    private String title;
    private String text;
    private String[] options;
}
