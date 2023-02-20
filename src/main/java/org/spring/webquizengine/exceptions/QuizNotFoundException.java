package org.spring.webquizengine.exceptions;

public class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException() {
    }

    public QuizNotFoundException(String message) {
        super(message);
    }
}
