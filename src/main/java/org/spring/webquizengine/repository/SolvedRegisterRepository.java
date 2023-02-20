package org.spring.webquizengine.repository;


import org.spring.webquizengine.entity.SolvedRegister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolvedRegisterRepository extends JpaRepository<SolvedRegister, Long> {
    Page<SolvedRegister> findAllByUserQuizEmail(Pageable pageable, String email);

    Iterable<? extends SolvedRegister> findAllByQuizId(long id);
}
