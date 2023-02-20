package org.spring.webquizengine.mappers;


import org.mapstruct.*;
import org.spring.webquizengine.dto.UserDTO;
import org.spring.webquizengine.entity.UserQuiz;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {


    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Mappings(
            @Mapping(
                    source = "password",
                    target = "password",
                    qualifiedByName = "passwordEncoder"
            )
    )
    public abstract UserQuiz toEntity(UserDTO userDTO);

    @Named("passwordEncoder")
    String encode(String password) {
        return encoder.encode(password);
    }
}
