package org.spring.webquizengine.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.webquizengine.dto.UserDTO;
import org.spring.webquizengine.entity.UserQuiz;
import org.spring.webquizengine.exceptions.UserAlreadyExistsException;
import org.spring.webquizengine.mappers.UserMapper;
import org.spring.webquizengine.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;


    @Test
    public void givenAvailableEmail_whenIsEmailAvailable_thenTrue() {
        // given
        UserDTO userDTO = UserDTO.builder().email("test@example.com").build();
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);

        // when
        boolean result = userService.isEmailAvailable(userDTO);

        // then
        assertThat(result).isTrue();
        verify(userRepository).existsByEmail(userDTO.getEmail());
    }

    @Test
    public void givenUnavailableEmail_whenIsEmailAvailable_thenThrowException() {
        // given
        UserDTO userDTO = UserDTO.builder().email("test@example.com").build();
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);

        // when and then
        assertThatThrownBy(() -> userService.isEmailAvailable(userDTO))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("Given Email is already registered");

        verify(userRepository).existsByEmail(userDTO.getEmail());
    }

    @Test
    public void givenUserDTO_whenSaveUser_thenUserSaved() {
        // given
        UserDTO userDTO = UserDTO.builder().email("test@example.com").build();
        UserQuiz userQuiz = new UserQuiz();
        when(userMapper.toEntity(userDTO)).thenReturn(userQuiz);

        // when
        userService.saveUser(userDTO);

        // then
        verify(userRepository).save(userQuiz);
        verify(userMapper).toEntity(userDTO);
    }

    @Test
    public void givenEmail_whenGetUser_thenUserFound() {
        // given
        String email = "test@example.com";
        UserQuiz userQuiz = UserQuiz.builder().email("test@example.com").build();
        when(userRepository.findByEmail(email)).thenReturn(userQuiz);

        // when
        userQuiz = userService.getUser(email);

        // then
        assertThat(userQuiz).isNotNull();
        assertThat(userQuiz.getEmail()).isEqualTo(email);
        verify(userRepository).findByEmail(email);
    }

    @Test
    public void givenNonexistentEmail_whenGetUser_thenUserNotFound() {
        // given
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        // when
        UserQuiz userQuiz = userService.getUser(email);

        // then
        assertThat(userQuiz).isNull();
        verify(userRepository).findByEmail(email);
    }
}