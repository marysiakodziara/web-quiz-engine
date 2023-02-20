package org.spring.webquizengine.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Entity
@ToString
@NoArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SolvedRegister extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    private Quiz quiz;
    private LocalDateTime completedAt;
    @ManyToOne
    private UserQuiz userQuiz;
}
