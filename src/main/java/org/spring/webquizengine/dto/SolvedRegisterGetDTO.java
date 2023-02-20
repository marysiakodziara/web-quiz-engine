package org.spring.webquizengine.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SolvedRegisterGetDTO {
    private String title;
    private LocalDateTime completedAt;
}
