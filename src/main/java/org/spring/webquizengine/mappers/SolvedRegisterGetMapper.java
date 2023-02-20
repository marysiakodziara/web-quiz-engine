package org.spring.webquizengine.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.spring.webquizengine.dto.SolvedRegisterGetDTO;
import org.spring.webquizengine.entity.SolvedRegister;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class SolvedRegisterGetMapper {

    @Mappings(
            {
                    @Mapping(target = "completedAt", source = "completedAt"),
                    @Mapping(target = "title", source = "quiz.title")
            }
    )
    public abstract SolvedRegisterGetDTO toDto(SolvedRegister solvedRegister);

}
