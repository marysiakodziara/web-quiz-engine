package org.spring.webquizengine.advice;


import org.spring.webquizengine.exceptions.NotAuthorizedOperationException;
import org.spring.webquizengine.exceptions.QuizNotFoundException;
import org.spring.webquizengine.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleInvalidArgument(UserAlreadyExistsException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<String> handleInvalidQuizId(QuizNotFoundException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(NotAuthorizedOperationException.class)
    public ResponseEntity<String> notAuthorizedOperation(NotAuthorizedOperationException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
