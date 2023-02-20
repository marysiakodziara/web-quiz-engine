package org.spring.webquizengine.controller;


import lombok.RequiredArgsConstructor;
import org.spring.webquizengine.dto.UserDTO;
import org.spring.webquizengine.facade.UserFacade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserController.ROOT_URL)
public class UserController {
    static final String ROOT_URL = "api/v1/user";
    private final UserFacade userFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void register(@RequestBody UserDTO userDTO) {
        userFacade.registerUser(userDTO);
    }
}
