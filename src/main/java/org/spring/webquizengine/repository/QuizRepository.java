package org.spring.webquizengine.repository;

import org.spring.webquizengine.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    boolean existsById(Long id);
}
