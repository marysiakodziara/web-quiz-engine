package org.spring.webquizengine.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.webquizengine.entity.Quiz;
import org.spring.webquizengine.exceptions.QuizNotFoundException;
import org.spring.webquizengine.mappers.QuizGetMapper;
import org.spring.webquizengine.mappers.QuizPostMapper;
import org.spring.webquizengine.repository.QuizRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizServiceUnitTest {

    @Mock
    QuizRepository quizRepository;
    @InjectMocks
    QuizService quizService;
    @Mock
    private QuizPostMapper quizPostMapper;
    @Mock
    private QuizGetMapper quizGetMapper;

    @Test
    void saveQuiz() {
    }

    @Test
    void getAllQuizzes() {
        // given

        // when
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        when(quizRepository.findAll(captor.capture())).thenReturn(new PageImpl<>(List.of()));

        quizService.getAllQuizzes(0);

        // then
        verify(quizRepository).findAll(captor.capture());
    }


    @Test
    public void givenExistingQuizId_whenGetById_thenReturnsQuiz() {
        // given
        long quizId = 1L;
        Quiz quiz = new Quiz();

        given(quizRepository.findById(quizId)).willReturn(Optional.of(quiz));

        // when
        Quiz result = quizService.getById(quizId);

        // then
        assertEquals(quiz, result);
    }

    @Test
    public void givenNonexistentQuizId_whenGetById_thenThrowsQuizNotFoundException() {
        // given
        long quizId = 2L;
        given(quizRepository.findById(quizId)).willReturn(Optional.empty());

        // when, then
        assertThrows(QuizNotFoundException.class, () -> quizService.getById(quizId));
    }

    @Test
    void getQuiz() {


    }

    @Test
    void isEqual() {
    }

    @Test
    void isQualified() {
    }

    @Test
    void deleteQuiz() {
    }
}