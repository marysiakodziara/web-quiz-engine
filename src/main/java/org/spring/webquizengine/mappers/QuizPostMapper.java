package org.spring.webquizengine.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.spring.webquizengine.dto.QuizPostDTO;
import org.spring.webquizengine.entity.Quiz;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class QuizPostMapper {
    @Mappings(
            {
                    @Mapping(source = "title", target = "title"),
                    @Mapping(source = "text", target = "text"),
                    @Mapping(source = "options", target = "options"),
                    @Mapping(source = "answer", target = "answer"),
                    @Mapping(source = "userQuiz", target = "userQuiz"),
                    @Mapping(target = "solvedRegisters", ignore = true)
            })

    public abstract Quiz postQuizDTOtoQuiz(QuizPostDTO quizPostDTO);
}
