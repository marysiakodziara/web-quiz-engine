package org.spring.webquizengine.facade;


import lombok.RequiredArgsConstructor;
import org.spring.webquizengine.dto.UserDTO;
import org.spring.webquizengine.exceptions.UserAlreadyExistsException;
import org.spring.webquizengine.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Component
@Validated
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public void registerUser(@Valid UserDTO userDTO) throws UserAlreadyExistsException {
        if (userService.isEmailAvailable(userDTO)) {
            userService.saveUser(userDTO);
        }
    }

}
