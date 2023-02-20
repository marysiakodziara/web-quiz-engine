package org.spring.webquizengine.controller;


import lombok.RequiredArgsConstructor;
import org.spring.webquizengine.PostedAnswer;
import org.spring.webquizengine.QuizAnswerBody;
import org.spring.webquizengine.dto.QuizGetDTO;
import org.spring.webquizengine.dto.QuizPostDTO;
import org.spring.webquizengine.dto.SolvedRegisterGetDTO;
import org.spring.webquizengine.exceptions.QuizNotFoundException;
import org.spring.webquizengine.facade.QuizFacade;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/quizzes")
public class QuizController {

    private final QuizFacade quizFacade;


    @GetMapping("/{id}")
    public ResponseEntity<QuizGetDTO> getQuiz(@PathVariable("id") long id) {
        return new ResponseEntity<>(quizFacade.getQuiz(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<QuizGetDTO>> getAllQuizzes(
            @RequestParam int page) {
        return new ResponseEntity<>(quizFacade.getAllQuizzes(page), HttpStatus.OK);
    }


    @PostMapping
    public void postQuiz(@Valid @RequestBody QuizPostDTO quizPostDTO, Authentication auth) {
        quizFacade.saveQuiz(quizPostDTO, auth);
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<QuizAnswerBody> postAnswer(
            @Positive @PathVariable long id,
            @RequestBody PostedAnswer answer,
            Authentication auth) throws QuizNotFoundException {
        return new ResponseEntity<>(quizFacade.checkAnswer(answer, id, auth), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable("id") long id, Authentication auth) {
        quizFacade.deleteQuiz(auth, id);
    }

    @GetMapping("/completed")
    public ResponseEntity<Page<SolvedRegisterGetDTO>> getCompletedQuizzes(
            @RequestParam int page,
            Authentication auth) {
        return new ResponseEntity<>(quizFacade.getAllRecords(page, auth.getName()), HttpStatus.OK);
    }
}