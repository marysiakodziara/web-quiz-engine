package org.spring.webquizengine;

public record QuizAnswerBody(boolean success, String feedback) {
    public static QuizAnswerBody create(boolean success) {
        if (success)
            return new QuizAnswerBody(true, "Congratulations, you're right!");
        return new QuizAnswerBody(false, "Wrong answer! Please, try again.");
    }
}