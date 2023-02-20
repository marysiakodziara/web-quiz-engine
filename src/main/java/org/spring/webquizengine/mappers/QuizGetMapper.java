package org.spring.webquizengine.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.spring.webquizengine.dto.QuizGetDTO;
import org.spring.webquizengine.entity.Quiz;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class QuizGetMapper {

    @Mappings(
            {
                    @Mapping(source = "id", target = "id"),
                    @Mapping(source = "title", target = "title"),
                    @Mapping(source = "text", target = "text"),
                    @Mapping(source = "options", target = "options")
            }
    )

    public abstract QuizGetDTO toDTO(Quiz quiz);
}
