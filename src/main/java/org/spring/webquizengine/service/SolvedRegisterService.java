package org.spring.webquizengine.service;


import lombok.RequiredArgsConstructor;
import org.spring.webquizengine.dto.SolvedRegisterGetDTO;
import org.spring.webquizengine.entity.Quiz;
import org.spring.webquizengine.entity.SolvedRegister;
import org.spring.webquizengine.entity.UserQuiz;
import org.spring.webquizengine.mappers.SolvedRegisterGetMapper;
import org.spring.webquizengine.repository.SolvedRegisterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SolvedRegisterService {

    private final SolvedRegisterRepository solvedRegisterRepository;
    private final SolvedRegisterGetMapper solvedRegisterGetMapper;


    public Page<SolvedRegisterGetDTO> getAllRecords(int pageNumber, String email) {
        Pageable paging = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "completedAt"));
        return solvedRegisterRepository.findAllByUserQuizEmail(paging, email).map(solvedRegisterGetMapper::toDto);
    }

    public void addRecord(Quiz quiz, LocalDateTime localDateTime, UserQuiz userQuiz) {
        SolvedRegister solvedRegister = new SolvedRegister(quiz, localDateTime, userQuiz);
        solvedRegisterRepository.save(solvedRegister);
    }
}
