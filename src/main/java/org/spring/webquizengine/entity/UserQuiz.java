package org.spring.webquizengine.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserQuiz extends BaseEntity {
    private String email;
    private String password;
    @OneToMany(mappedBy = "userQuiz")
    private List<SolvedRegister> solvedRegisters;
}
