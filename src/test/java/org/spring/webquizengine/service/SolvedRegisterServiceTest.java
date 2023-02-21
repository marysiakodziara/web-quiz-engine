package org.spring.webquizengine.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.webquizengine.dto.SolvedRegisterGetDTO;
import org.spring.webquizengine.entity.Quiz;
import org.spring.webquizengine.entity.SolvedRegister;
import org.spring.webquizengine.entity.UserQuiz;
import org.spring.webquizengine.mappers.SolvedRegisterGetMapper;
import org.spring.webquizengine.repository.SolvedRegisterRepository;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SolvedRegisterServiceTest {

    @Mock
    private SolvedRegisterRepository solvedRegisterRepository;

    @SuppressWarnings("unused")
    @Mock
    private SolvedRegisterGetMapper solvedRegisterGetMapper;

    @InjectMocks
    private SolvedRegisterService solvedRegisterService;

    @Test
    void addRecord_ShouldSaveSolvedRegister() {
        // given
        Quiz quiz = new Quiz();
        LocalDateTime localDateTime = LocalDateTime.now();
        UserQuiz userQuiz = new UserQuiz();

        // when
        solvedRegisterService.addRecord(quiz, localDateTime, userQuiz);

        // then
        verify(solvedRegisterRepository).save(any(SolvedRegister.class));
    }

    @Test
    void getAllRecords_ShouldReturnPageOfSolvedRegisterGetDTO() {
        // given
        int pageNumber = 0;
        String email = "test@example.com";
        Pageable paging = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "completedAt"));
        when(solvedRegisterRepository.findAllByUserQuizEmail(paging, email))
                .thenReturn(new PageImpl<>(Collections.singletonList(new SolvedRegister()), paging, 1L));

        // when
        Page<SolvedRegisterGetDTO> result = solvedRegisterService.getAllRecords(pageNumber, email);

        // then
        assertThat(result).isNotNull();
    }

}