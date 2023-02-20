package org.spring.webquizengine.service;


import lombok.RequiredArgsConstructor;
import org.spring.webquizengine.PostedAnswer;
import org.spring.webquizengine.dto.QuizGetDTO;
import org.spring.webquizengine.dto.QuizPostDTO;
import org.spring.webquizengine.entity.Quiz;
import org.spring.webquizengine.entity.UserQuiz;
import org.spring.webquizengine.exceptions.NotAuthorizedOperationException;
import org.spring.webquizengine.exceptions.QuizNotFoundException;
import org.spring.webquizengine.mappers.QuizGetMapper;
import org.spring.webquizengine.mappers.QuizPostMapper;
import org.spring.webquizengine.repository.QuizRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    private final QuizPostMapper quizPostMapper;

    private final QuizGetMapper quizGetMapper;


    public void saveQuiz(QuizPostDTO quizPostDTO, UserQuiz userQuiz) {
        quizPostDTO.setUserQuiz(userQuiz);
        quizRepository.save(quizPostMapper.postQuizDTOtoQuiz(quizPostDTO));
    }

    public Page<QuizGetDTO> getAllQuizzes(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 10, Sort.by("id"));
        return quizRepository.findAll(paging).map(quizGetMapper::toDTO);
    }

    public QuizGetDTO getQuiz(long id) {
        return quizGetMapper.toDTO(quizRepository.findById(id).orElseThrow(
                () -> new QuizNotFoundException("Quiz with given Id does not exist")));
    }

    public boolean isEqual(long id, PostedAnswer postedAnswer) {
        return Arrays.equals(quizRepository.getById(id).getAnswer(), postedAnswer.answer());
    }

    public boolean isQualified(Authentication auth, long id) {
        if (quizRepository.findById(id).orElseThrow().getUserQuiz().getEmail().equals(auth.getName())) {
            return true;
        }
        throw new NotAuthorizedOperationException("Deleting quizzes of other Users is not supported by the service");
    }

    public void deleteQuiz(Authentication auth, long id) {
        if (quizRepository.existsById(id) && isQualified(auth, id)) {
            quizRepository.deleteById(id);
        } else {
            throw new QuizNotFoundException("Quiz with given Id does not exist");
        }
    }

    public Quiz getById(long id) {
        return quizRepository.findById(id)
                .orElseThrow(QuizNotFoundException::new);
    }
}
