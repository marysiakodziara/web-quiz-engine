package org.spring.webquizengine.facade;


import lombok.RequiredArgsConstructor;
import org.spring.webquizengine.PostedAnswer;
import org.spring.webquizengine.QuizAnswerBody;
import org.spring.webquizengine.dto.QuizGetDTO;
import org.spring.webquizengine.dto.QuizPostDTO;
import org.spring.webquizengine.dto.SolvedRegisterGetDTO;
import org.spring.webquizengine.service.QuizService;
import org.spring.webquizengine.service.SolvedRegisterService;
import org.spring.webquizengine.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class QuizFacade {

    private final QuizService quizService;

    private final UserService userService;
    private final SolvedRegisterService solvedRegisterService;

    public void saveQuiz(QuizPostDTO quizPostDTO, Authentication authentication) {
        quizService.saveQuiz(quizPostDTO, userService.getUser(authentication.getName()));
    }

    public QuizGetDTO getQuiz(Long id) {
        return quizService.getQuiz(id);
    }

    public Page<QuizGetDTO> getAllQuizzes(int pageNumber) {
        return quizService.getAllQuizzes(pageNumber);
    }

    public Page<SolvedRegisterGetDTO> getAllRecords(int pageNumber, String email) {
        return solvedRegisterService.getAllRecords(pageNumber, email);
    }

    public QuizAnswerBody checkAnswer(PostedAnswer postedAnswer, long id, Authentication auth) {
        if (quizService.isEqual(id, postedAnswer)) {
            LocalDateTime localDateTime = LocalDateTime.now();
            solvedRegisterService.addRecord(quizService.getById(id), localDateTime, userService.getUser(auth.getName()));
        }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   ///////////
        return QuizAnswerBody.create(quizService.isEqual(id, postedAnswer));
    }

    public void deleteQuiz(Authentication auth, long id) {
        quizService.deleteQuiz(auth, id);
    }

}
