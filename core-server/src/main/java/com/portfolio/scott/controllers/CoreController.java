package com.portfolio.scott.controllers;

import com.portfolio.scott.controllers.doc.SwaggerController;
import com.portfolio.scott.controllers.dto.*;
import com.portfolio.scott.controllers.errors.LoginFailedException;
import com.portfolio.scott.controllers.errors.UserPasswordMismatchException;
import com.portfolio.scott.domains.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/core")
public class CoreController implements SwaggerController {

    private final UserService userService;

    public CoreController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@Valid @RequestBody SignupDTO signupDTO) {
        UserDTO savedUser = userService.signup(signupDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        TokenDTO tokenDTO;
        try {
            tokenDTO = userService.login(loginDTO);
        } catch (UserPasswordMismatchException e) {
            log.error("Error mismatched password - userId: {}", loginDTO.getUserId(), e);
            throw new LoginFailedException();
        }
        return ResponseEntity.ok(tokenDTO);
    }

}
