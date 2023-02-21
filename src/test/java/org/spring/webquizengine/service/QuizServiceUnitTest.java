package org.spring.webquizengine.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.webquizengine.PostedAnswer;
import org.spring.webquizengine.dto.QuizPostDTO;
import org.spring.webquizengine.entity.Quiz;
import org.spring.webquizengine.entity.UserQuiz;
import org.spring.webquizengine.exceptions.NotAuthorizedOperationException;
import org.spring.webquizengine.exceptions.QuizNotFoundException;
import org.spring.webquizengine.mappers.QuizGetMapper;
import org.spring.webquizengine.mappers.QuizPostMapper;
import org.spring.webquizengine.repository.QuizRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceUnitTest {

    @Mock
    QuizRepository quizRepository;
    @InjectMocks
    QuizService quizService;
    @Mock
    private QuizPostMapper quizPostMapper;
    @SuppressWarnings("unused")
    @Mock
    private QuizGetMapper quizGetMapper;


    @Test
    void shouldSaveQuiz() {
        // given
        QuizPostDTO quizPostDTO = new QuizPostDTO();
        UserQuiz userQuiz = new UserQuiz();

        Quiz quizEntity = new Quiz();
        when(quizPostMapper.postQuizDTOtoQuiz(quizPostDTO)).thenReturn(quizEntity);

        // when
        quizService.saveQuiz(quizPostDTO, userQuiz);

        // then
        verify(quizRepository).save(any(Quiz.class));
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
        Long quizId = 1L;
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
    void shouldReturnTrueWhenAnswerIsCorrect() {
        // given
        Quiz quiz = Quiz.builder().answer(new int[]{1, 2, 3}).build();
        PostedAnswer postedAnswer = new PostedAnswer(new int[]{1, 2, 3});

        // when
        boolean result = quizService.isEqual(quiz, postedAnswer);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenAnswerIsIncorrect() {
        // given
        Quiz quiz = Quiz.builder().answer(new int[]{1, 2, 3}).build();
        PostedAnswer postedAnswer = new PostedAnswer(new int[]{3, 2, 1});

        // when
        boolean result = quizService.isEqual(quiz, postedAnswer);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void testIsQualifiedReturnsTrueWhenUserQuizEmailMatchesAuthName() {
        // given
        Long id = 1L;
        String email = "test@example.com";
        Authentication auth = new UsernamePasswordAuthenticationToken(email, null);
        UserQuiz userQuiz = new UserQuiz();
        userQuiz.setEmail(email);
        Quiz quiz = new Quiz();
        quiz.setUserQuiz(userQuiz);
        given(quizRepository.findById(id)).willReturn(Optional.of(quiz));

        // when
        boolean result = quizService.isQualified(auth, id);

        // then
        assertTrue(result);
    }

    @Test
    void testIsQualifiedThrowsNotAuthorizedOperationExceptionWhenUserQuizEmailDoesNotMatchAuthName() {
        // given
        Long id = 1L;
        String email = "test@example.com";
        Authentication auth = new UsernamePasswordAuthenticationToken("other@example.com", null);
        UserQuiz userQuiz = new UserQuiz();
        userQuiz.setEmail(email);
        Quiz quiz = new Quiz();
        quiz.setUserQuiz(userQuiz);
        given(quizRepository.findById(id)).willReturn(Optional.of(quiz));

        // when then
        NotAuthorizedOperationException exception = assertThrows(NotAuthorizedOperationException.class,
                () -> quizService.isQualified(auth, id));
        assertEquals("Deleting quizzes of other Users is not supported by the service", exception.getMessage());
    }

    @Test
    public void deleteQuiz_authorizedAndExistingQuiz_shouldDeleteQuiz() {
        // given
        when(quizRepository.existsById(anyLong())).thenReturn(true);

        // when
        quizService.deleteQuiz(true, 1L);

        // then
        verify(quizRepository).deleteById(1L);
    }

    @Test
    public void deleteQuiz_unauthorizedAndExistingQuiz_shouldThrowException() {
        // when
        when(quizRepository.existsById(anyLong())).thenReturn(true);

        // then
        assertThrows(QuizNotFoundException.class, () -> quizService.deleteQuiz(false, 1L));
    }

    @Test
    public void deleteQuiz_nonExistingQuiz_shouldThrowException() {
        // when
        when(quizRepository.existsById(anyLong())).thenReturn(false);

        // then
        assertThrows(QuizNotFoundException.class, () -> quizService.deleteQuiz(true, 1L));
    }
}