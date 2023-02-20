package org.spring.webquizengine.repository;


import org.spring.webquizengine.entity.UserQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserQuiz, Long> {

    UserQuiz findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<UserQuiz> findAnyByEmail(String email);
}